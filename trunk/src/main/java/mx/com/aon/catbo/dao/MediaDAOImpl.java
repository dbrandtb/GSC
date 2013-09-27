package mx.com.aon.catbo.dao;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mx.com.aon.catbo.model.BackBoneResultVO;
import mx.com.aon.catbo.model.MediaTO;
import mx.com.aon.catbo.service.MediaDAO;
import mx.com.aon.portal.dao.AbstractDAO;
import mx.com.aon.portal.service.ProcessResultManager;
import mx.com.aon.portal.util.WrapperResultados;
import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.lob.LobHandler;

public class MediaDAOImpl extends AbstractDAO implements MediaDAO {

	@SuppressWarnings("unused")
	private LobHandler lobHandler;

    @SuppressWarnings("unused")
	private AbstractDAO abstractDAO;
    
    private ProcessResultManager processResultManager;
    
    private static Logger logger = Logger.getLogger(MediaDAOImpl.class);
    
    private String nmArchivoSalida;
    private String nMovimientoSalida;

    public BackBoneResultVO guardarArchivo(final MediaTO mediaTO, final InputStream inputStream, final int contentLength) throws Exception {
    			
    		 BackBoneResultVO backBoneResultVO = (BackBoneResultVO)getJdbcTemplate().execute(
    			 new CallableStatementCreator () {
    			  public CallableStatement createCallableStatement (java.sql.Connection conn) throws SQLException{
	    		  CallableStatement cs = conn.prepareCall("call PKG_CATBO.P_GUARDA_MCASOARC(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
	    		  cs.setString(1, mediaTO.getIdCaso());
	    		  if(mediaTO.getNmMovimiento()!=null && !mediaTO.getNmMovimiento().equals("")){
	    			   cs.setString(2, mediaTO.getNmMovimiento());
	    		   }else{
	    			   cs.setString(2, null);
	    		   }	    		   
            	  cs.setString(3, mediaTO.getIdDocumento());
            	  cs.setString(4, mediaTO.getDsArchivo());
            	  cs.setString(5, mediaTO.getCdTipoArchivo());
            	  //cs.setBlob(6, null);
            	  try{
            	  InputStream inputStream2 = ServletActionContext.getRequest().getInputStream();
            	  //InputStream inputStream2 = new FileInputStream(file);
                  cs.setBinaryStream(6, inputStream2, 0);
            	  }catch(Exception exec){
            		  logger.debug("Excepcion en setear flujo de bytes: "+exec.getMessage());
            		  
            	  }
                  cs.setString(7, mediaTO.getUser());
            	  cs.registerOutParameter(8, OracleTypes.NUMERIC);
             	  cs.registerOutParameter(9, OracleTypes.NUMERIC);
            	  cs.registerOutParameter(10, OracleTypes.NUMERIC);
            	  cs.registerOutParameter(11, OracleTypes.VARCHAR);
            	  cs.setString(12, mediaTO.getFilename());
            	  return cs;
	    	   }
	       }, new CallableStatementCallback () {
	    	   public Object doInCallableStatement(CallableStatement cs) throws SQLException{
	    		   String msg_Id;
	    		   try{
	    			   cs.execute();
	    			   nmArchivoSalida = cs.getString(8);
	    			   nMovimientoSalida = cs.getString(9);
	    			   /**************update*********/
	    			   getJdbcTemplate().execute(new PreparedStatementCreator() {
	    				   public PreparedStatement createPreparedStatement(java.sql.Connection conn) throws SQLException {
	    					 PreparedStatement ps =
	    					  	conn.prepareStatement("UPDATE MCASOARC SET blarchivo = ? "  +  
	    					  					   		"WHERE  nmcaso  = ? " +
	    					  					   		"	AND nmovimiento = ? " +
	    					  					   		"	AND nmarchivo = ?" +
	    					  					   	"	AND dsnomarc = ?");
	    							  try{            		   
	    			            		   logger.debug("valor de inputStream "+inputStream);
	    			            		   logger.debug("valor de inputStream "+contentLength);
	    			            		   ps.setBinaryStream(1, inputStream, contentLength);
	    			            		   
	    			            	   }catch(Exception e){
	    			            		   logger.debug("Excepcion en setear flujo de bytes: "+e.getMessage());
	    			            	   }          
	    			            	   ps.setString(2, mediaTO.getIdCaso());
	    			            	   ps.setString(3, nMovimientoSalida);
	    			            	   ps.setString(4, nmArchivoSalida);
	    			            	   ps.setString(5, mediaTO.getFilename());
	    			            	   return ps;
	    							        }
	    							    },
	    							    new PreparedStatementCallback(){
	    							    	public Object doInPreparedStatement(PreparedStatement ps) throws SQLException{
	    							    		 try{
	    							    			ps.execute(); 
	    							    		 }catch(Exception exc){
	    							    			   logger.debug("Excepcion en el execute de PreparedStatementCallback: "+exc.getMessage());
	    							    		 }
	    							    		 
	    							    		 Object object = new Object();
	    						 	    		 return object;	 
	    							    		 
	    							    	 }
	    						}); 
	    					   
	    			   
	    		   }catch(Exception e){
	    			   logger.debug("Excepcion en el execute de CallableStatementCallback: "+e.getMessage());
	    		   }
	    		   
	    		   msg_Id = cs.getString(10);
	    		   BackBoneResultVO backBoneResultVO = new BackBoneResultVO();
 	    		   backBoneResultVO.setOutParam(cs.getString(9));
 	    		   backBoneResultVO.setMsgText(msg_Id);
 	    		   return backBoneResultVO;
	    	   }
	       });
	       //ProcessResultManager processResultManager = new ProcessResultManager();
	       WrapperResultados res = new WrapperResultados();
	       res.setMsgId(backBoneResultVO.getMsgText());
	       res = processResultManager.processResultMessageId(res);
	       backBoneResultVO.setMsgText(res.getMsgText());
	       
	       return backBoneResultVO;
	    //Es para cuando existe el registro, se actualiza nada más   
    	
	}
    
    
    
    public MediaTO descargarArchivoJdbcPuro(final String nmcaso, final String nmovimiento, final String nmarchivo) throws Exception{
    	MediaTO descarga = null;
    	
    	if(logger.isDebugEnabled()){
    		logger.debug("JDBC PURO Los parametros de entrada para descargar el archivo son:");
    		logger.debug("nmcaso: "+ nmcaso);
    		logger.debug("nmovimiento: "+ nmovimiento);
    		logger.debug("nmarchivo: "+ nmarchivo);
    		
    	}
    	Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		
		
		try {
			
			String host = "192.168.1.190", puerto = "1527", SID = "ACWQA";
			String driver = "oracle.jdbc.driver.OracleDriver";
			String user = "ICE_CONFIGURING", pass = "ICE_CONFIGURING";

			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				logger.debug(e);
			}
			conn = DriverManager.getConnection("jdbc:oracle:thin:@" + host+ ":" + puerto + ":" + SID, user, pass);
			
			//conn = QuerysBd.getConnection();
			logger.debug(">>>>>>> antes de llamar a PKG_CATBO.P_OBTIENE_MCASOARC_REG( ?, ?, ?, ?, ?, ? )");
			
			
	    	cs = conn.prepareCall("call PKG_CATBO.P_OBTIENE_MCASOARC_REG( ?, ?, ?, ?, ?, ? )");
	
		  			cs.setString(1, nmcaso);
		  			cs.setString(2, nmovimiento);
		  			cs.setString(3, nmarchivo);
		  			cs.registerOutParameter(4, OracleTypes.CURSOR);
		  			cs.registerOutParameter(5, OracleTypes.NUMBER);
		  			cs.registerOutParameter(6, OracleTypes.VARCHAR);

	    			cs.execute();
	    			   
	    			
	    			String id= cs.getString(5);
	    			String msg= cs.getString(6);
	    				if(logger.isDebugEnabled()){
	    					logger.debug(">>>>>>> SALIDA PKG_CATBO.P_OBTIENE_MCASOARC_REG ::: out :::");
	    					logger.debug(">>>>>>> id  ::: out ::: = "+id);
	    					logger.debug(">>>>>>> msg  ::: out ::: = "+msg);
	    				}
	    				
	    			rs = (ResultSet) cs.getObject(4);
	    				
	    			if(rs.next()){
	    					descarga = new MediaTO();
	    					Blob blobArch= rs.getBlob("BLARCHIVO"); 
	    					

	    					String tmpBlob = new String(blobArch.getBytes(1L,(int)blobArch.length()));
	    					
	    					logger.debug("Bytes descargados con el metodo getblob: "+ tmpBlob);
	    					
	    					byte[] flujo = rs.getBytes("BLARCHIVO");
	    					String tmp= new String(flujo);
	    					logger.debug("Bytes descargados: "+ tmp);
	    					
	    					descarga.setContenidoBytes(new ByteArrayInputStream(flujo));
	    					
	    					descarga.setDsArchivo(rs.getString("DSNOMARC"));
	    					
	    			}
	    			   
	    				
	    				logger.debug(">>>>>>> END");
		} catch (NumberFormatException e) {
			logger.debug(e);
		} catch (SQLException e) {
			logger.debug(e);
		} finally {
			try {
					if (rs != null)
							rs.close();
					if (cs != null)
							cs.close();
					if (conn != null)
						conn.close();
			} catch (SQLException sql) {
					logger.debug(sql);
			}
		}
    	return descarga;
    	
    }
    
    
    public MediaTO descargarArchivo(final String nmcaso, final String nmovimiento, final String nmarchivo) throws Exception{
    	
    	if(logger.isDebugEnabled()){
    		logger.debug("Los parametros de entrada para descargar el archivo son:");
    		logger.debug("nmcaso: "+ nmcaso);
    		logger.debug("nmovimiento: "+ nmovimiento);
    		logger.debug("nmarchivo: "+ nmarchivo);
    		
    	}
    	
    	MediaTO descarga = (MediaTO)getJdbcTemplate().execute(
   			 new CallableStatementCreator () {
   			  public CallableStatement createCallableStatement (java.sql.Connection conn) throws SQLException{
	    		    CallableStatement cs = conn.prepareCall("call PKG_CATBO.P_OBTIENE_MCASOARC_REG( ?, ?, ?, ?, ?, ? )");
	
		  			cs.setString(1, nmcaso);
		  			cs.setString(2, nmovimiento);
		  			cs.setString(3, nmarchivo);
		  			cs.registerOutParameter(4, OracleTypes.CURSOR);
		  			cs.registerOutParameter(5, OracleTypes.NUMBER);
		  			cs.registerOutParameter(6, OracleTypes.VARCHAR);

		  			return cs;
	    	   }
	       }, new CallableStatementCallback () {
	    	   public Object doInCallableStatement(CallableStatement cs) throws SQLException{
	    		   MediaTO download= null;
	    		   ResultSet rs = null;
	    		   try{
	    			   cs.execute();
	    			   String id= cs.getString(5);
	    				String msg= cs.getString(6);
	    				if(logger.isDebugEnabled()){
	    					logger.debug(">>>>>>> SALIDA PKG_CATBO.P_OBTIENE_MCASOARC_REG ::: out :::");
	    					logger.debug(">>>>>>> id  ::: out ::: = "+id);
	    					logger.debug(">>>>>>> msg  ::: out ::: = "+msg);
	    				}
	    				
	    				rs = (ResultSet) cs.getObject(4);
	    				
	    				if(rs.next()){
	    					download=new MediaTO();
	    					Blob blobArch= rs.getBlob("BLARCHIVO");
	    					download.setContenidoBytes(new ByteArrayInputStream(blobArch.getBytes(1L,(int)blobArch.length())));
	    					download.setDsArchivo(rs.getString("DSNOMARC"));
	    					
	    					String tmp= new String(blobArch.getBytes(1L,(int)blobArch.length()));
	    					logger.debug("Bytes descargados: "+ tmp);
	    					
	    				}
	    			   
	    		   }catch(Exception e){
	    			   logger.error("Excepcion en el execute de CallableStatementCallback: "+e.getMessage());
	    		   }
	    		   
	    		   return download;
	    	   }
	       });
    
    	return descarga;
    	
    }

	public void setLobHandler(LobHandler lobHandler) {
		this.lobHandler = lobHandler;
	}

	public void setAbstractDAO(AbstractDAO abstractDAO) {
		this.abstractDAO = abstractDAO;
	}

	public void setProcessResultManager(ProcessResultManager processResultManager) {
		this.processResultManager = processResultManager;
	}

	public String getNmArchivoSalida() {
		return nmArchivoSalida;
	}

	public void setNmArchivoSalida(String nmArchivoSalida) {
		this.nmArchivoSalida = nmArchivoSalida;
	}

	public String getNMovimientoSalida() {
		return nMovimientoSalida;
	}

	public void setNMovimientoSalida(String movimientoSalida) {
		nMovimientoSalida = movimientoSalida;
	}

	


}
