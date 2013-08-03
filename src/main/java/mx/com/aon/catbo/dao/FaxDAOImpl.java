package mx.com.aon.catbo.dao;

import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import oracle.jdbc.driver.OracleTypes;

import org.apache.commons.beanutils.Converter;
import org.apache.struts2.ServletActionContext;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.lob.LobHandler;

import mx.com.aon.catbo.model.BackBoneResultVO;
import mx.com.aon.catbo.model.FaxesVO;
import mx.com.aon.catbo.model.MediaTO;

import mx.com.aon.catbo.service.FaxDAO;

import mx.com.aon.portal.dao.AbstractDAO;
import mx.com.aon.portal.service.ProcessResultManager;
import mx.com.aon.portal.util.ConvertUtil;
import mx.com.aon.portal.util.UserSQLDateConverter;
import mx.com.aon.portal.util.WrapperResultados;

public class FaxDAOImpl extends AbstractDAO implements FaxDAO {

	@SuppressWarnings("unused")
	private LobHandler lobHandler;

    @SuppressWarnings("unused")
	private AbstractDAO abstractDAO;
    
    private ProcessResultManager processResultManager;
    
    private String nmFaxAux;

   /* public BackBoneResultVO guardarAdministracionFax(final FaxesVO faxesVO, final InputStream inputStream,
 			final int contentLength) throws Exception {

    	 BackBoneResultVO backBoneResultVO = (BackBoneResultVO)getJdbcTemplate().execute(new CallableStatementCreator () {
 	    	   public CallableStatement createCallableStatement (java.sql.Connection conn) throws SQLException{
 	    		 
 	    		   CallableStatement cs = conn.prepareCall("call PKG_CATBO.P_GUARDA_MFAXES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
 	    		  try {
 	    		   Converter converter = new UserSQLDateConverter("");
 	    		 
 	    		   cs.setString(1, faxesVO.getNmcaso());
 	    		   cs.registerOutParameter(2, OracleTypes.NUMERIC);
 	    		   cs.setString(3, faxesVO.getCdtipoar());
 	    		   cs.setDate(4, (java.sql.Date)converter.convert(java.util.Date.class, faxesVO.getFeingreso()));
 	    		   cs.setDate(5, (java.sql.Date)converter.convert(java.util.Date.class, faxesVO.getFerecepcion())); 
	    		   //cs.setDate(4, ConvertUtil.convertToDate(faxesVO.getFeingreso()));
 	    		   //cs.setDate(5, ConvertUtil.convertToDate(faxesVO.getFerecepcion()));
 	    		   cs.setString(6, faxesVO.getNmpoliex());
 	    		   cs.setString(7, faxesVO.getCdusuari());
 	    		   cs.setBinaryStream(8, inputStream, contentLength);
 	    		   cs.registerOutParameter(9, OracleTypes.NUMERIC);
             	   cs.registerOutParameter(10, OracleTypes.VARCHAR);
 	    		  }catch (Exception e) {
	    			   
	    		   }
             	   return cs;
 	    	   }
 	       }, new CallableStatementCallback () {
 	    	   public Object doInCallableStatement(CallableStatement cs) throws SQLException{
 	    		   String msg_Id;
 	    		   cs.execute();
 	    		   msg_Id = cs.getString(10);
 	    		  BackBoneResultVO backBoneResultVO = new BackBoneResultVO();
 	    		  backBoneResultVO.setOutParam(cs.getString(2));
 	    		  backBoneResultVO.setMsgText(cs.getString(9));
 	    		   return backBoneResultVO;
 	    	   }
 	       });
 	       //ProcessResultManager processResultManager = new ProcessResultManager();
 	       WrapperResultados res = new WrapperResultados();
 	       res.setMsgId(backBoneResultVO.getMsgText());
 	       res = processResultManager.processResultMessageId(res);
 	       backBoneResultVO.setMsgText(res.getMsgText());
 	       
 	       return backBoneResultVO;
 	}*/
    
   /* public BackBoneResultVO guardarAdministracionFaxEditar(final FaxesVO faxesVO, final InputStream inputStream,
 			final int contentLength) throws Exception {

    	 BackBoneResultVO backBoneResultVO = (BackBoneResultVO)getJdbcTemplate().execute(new CallableStatementCreator () {
 	    	   public CallableStatement createCallableStatement (java.sql.Connection conn) throws SQLException{
 	    		 
 	    		   CallableStatement cs = conn.prepareCall("call PKG_CATBO.P_GUARDA_MFAXES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
 	    		  try {
 	    		   Converter converter = new UserSQLDateConverter("");
 	    		   cs.setString(1, faxesVO.getNmcaso());
 	    		   cs.setLong(2, Long.parseLong(faxesVO.getNmfax()));
  	    		   cs.setString(3, faxesVO.getCdtipoar());
        		   cs.setDate(4, (java.sql.Date)converter.convert(java.util.Date.class, faxesVO.getFeingreso()));
	    		   cs.setDate(5, (java.sql.Date)converter.convert(java.util.Date.class, faxesVO.getFerecepcion()));
 	    		   //cs.setDate(4, ConvertUtil.convertToDate(faxesVO.getFeingreso()));
 	    		   //cs.setDate(5, ConvertUtil.convertToDate(faxesVO.getFerecepcion()));
 	    		   cs.setString(6, faxesVO.getNmpoliex());
 	    		   cs.setString(7, faxesVO.getCdusuari());
 	    		   cs.setBinaryStream(8, inputStream, contentLength);
 	    		   cs.registerOutParameter(9, OracleTypes.NUMERIC);
             	   cs.registerOutParameter(10, OracleTypes.VARCHAR);
 	    		  }catch (Exception e) {
	    			   
	    		   }
             	   return cs;
 	    	   }
 	       }, new CallableStatementCallback () {
 	    	   public Object doInCallableStatement(CallableStatement cs) throws SQLException{
 	    		   String msg_Id;
 	    		   cs.execute();
 	    		   msg_Id = cs.getString(10);
 	    		  BackBoneResultVO backBoneResultVO = new BackBoneResultVO();
 	    		  //backBoneResultVO.setOutParam(cs.getString(2));
 	    		  backBoneResultVO.setMsgText(cs.getString(9));
 	    		   return backBoneResultVO;
 	    	   }
 	       });
 	       //ProcessResultManager processResultManager = new ProcessResultManager();
 	       WrapperResultados res = new WrapperResultados();
 	       res.setMsgId(backBoneResultVO.getMsgText());
 	       res = processResultManager.processResultMessageId(res);
 	      backBoneResultVO.setMsgText(res.getMsgText());
 	       return backBoneResultVO;
 	      
 	}*/
    
    /**************************************Guardar nuevo con el arreglo de guardar Archivos Grandes***********************************************/
    
    public BackBoneResultVO guardarAdministracionFax(final FaxesVO faxesVO, final InputStream inputStream,
 			final int contentLength) throws Exception {	
		 BackBoneResultVO backBoneResultVO = (BackBoneResultVO)getJdbcTemplate().execute(
			 new CallableStatementCreator () {
			  public CallableStatement createCallableStatement (java.sql.Connection conn) throws SQLException{
   		  CallableStatement cs = conn.prepareCall("call PKG_CATBO.P_GUARDA_MFAXES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
   		 
   		  Converter converter = new UserSQLDateConverter("");
   		   cs.setString(1, faxesVO.getNmcaso());
   		   cs.registerOutParameter(2, OracleTypes.NUMERIC);
   		   //cs.setLong(2, Long.parseLong(faxesVO.getNmfax()));
   		   cs.setString(3, faxesVO.getCdtipoar());
  		   cs.setDate(4, (java.sql.Date)converter.convert(java.util.Date.class, faxesVO.getFeingreso()));
  		   cs.setDate(5, (java.sql.Date)converter.convert(java.util.Date.class, faxesVO.getFerecepcion()));
   		   cs.setString(6, faxesVO.getNmpoliex());
   		   cs.setString(7, faxesVO.getCdusuari());
   		 try {
   			 InputStream inputStream2 = ServletActionContext.getRequest().getInputStream();
   			cs.setBinaryStream(8, inputStream2, 0);
   		}catch(Exception exec){
       		  logger.debug("Excepcion en setear flujo de bytes: "+exec.getMessage());
       	 }
        cs.registerOutParameter(9, OracleTypes.NUMERIC);
   		cs.registerOutParameter(10, OracleTypes.VARCHAR);
   		return cs;
   	   }
      }, new CallableStatementCallback () {
   	   public Object doInCallableStatement(CallableStatement cs) throws SQLException{
   		   String msg_Id;
   		   try{
   			   cs.execute();
   			   nmFaxAux = cs.getString(2);
   			   /**************update*********/
   			   getJdbcTemplate().execute(new PreparedStatementCreator() {
   				   public PreparedStatement createPreparedStatement(java.sql.Connection conn) throws SQLException {
   					 PreparedStatement ps =
   					  	conn.prepareStatement("UPDATE MFAXES SET blarchivo = ? "  +  
   					  					   		"WHERE  nmcaso  = ? " +
   					  					   		"	AND nmfax = ? ");
   					  					   
   							  try{            		   
   			            		   logger.debug("valor de inputStream "+inputStream);
   			            		   logger.debug("valor de inputStream "+contentLength);
   			            		   ps.setBinaryStream(1, inputStream, contentLength);
   			            		   
   			            	   }catch(Exception e){
   			            		   logger.debug("Excepcion en setear flujo de bytes: "+e.getMessage());
   			            	   }          
   			            	   ps.setString(2, faxesVO.getNmcaso());
   			            	   ps.setString(3, nmFaxAux);
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
   		   
   		//String msg_Id;
		   
		   msg_Id = cs.getString(10);
		  BackBoneResultVO backBoneResultVO = new BackBoneResultVO();
		  backBoneResultVO.setOutParam(cs.getString(2));
		  backBoneResultVO.setMsgText(cs.getString(9));
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
    
    
    /********************Editar**************/
    
    public BackBoneResultVO guardarAdministracionFaxEditar(final FaxesVO faxesVO, final InputStream inputStream,
 			final int contentLength) throws Exception {	
		 BackBoneResultVO backBoneResultVO = (BackBoneResultVO)getJdbcTemplate().execute(
			 new CallableStatementCreator () {
			  public CallableStatement createCallableStatement (java.sql.Connection conn) throws SQLException{
   		  CallableStatement cs = conn.prepareCall("call PKG_CATBO.P_GUARDA_MFAXES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
   		 
   		  Converter converter = new UserSQLDateConverter("");
   		   cs.setString(1, faxesVO.getNmcaso());
   		   cs.setLong(2, Long.parseLong(faxesVO.getNmfax()));
   		   cs.setString(3, faxesVO.getCdtipoar());
  		   cs.setDate(4, (java.sql.Date)converter.convert(java.util.Date.class, faxesVO.getFeingreso()));
  		   cs.setDate(5, (java.sql.Date)converter.convert(java.util.Date.class, faxesVO.getFerecepcion()));
   		   cs.setString(6, faxesVO.getNmpoliex());
   		   cs.setString(7, faxesVO.getCdusuari());
   		 try {
   			 InputStream inputStream2 = ServletActionContext.getRequest().getInputStream();
   			cs.setBinaryStream(8, inputStream2, 0);
   		}catch(Exception exec){
       		  logger.debug("Excepcion en setear flujo de bytes: "+exec.getMessage());
       	 }
        cs.registerOutParameter(9, OracleTypes.NUMERIC);
   		cs.registerOutParameter(10, OracleTypes.VARCHAR);
   		return cs;
   	   }
      }, new CallableStatementCallback () {
   	   public Object doInCallableStatement(CallableStatement cs) throws SQLException{
   		   String msg_Id;
   		   try{
   			   cs.execute();
   			   /**************update*********/
   			   getJdbcTemplate().execute(new PreparedStatementCreator() {
   				   public PreparedStatement createPreparedStatement(java.sql.Connection conn) throws SQLException {
   					 PreparedStatement ps =
   					  	conn.prepareStatement("UPDATE MFAXES SET blarchivo = ? "  +  
   					  					   		"WHERE  nmcaso  = ? " +
   					  					   		"	AND nmfax = ? ");
   					  					   
   							  try{            		   
   			            		   logger.debug("valor de inputStream "+inputStream);
   			            		   logger.debug("valor de inputStream "+contentLength);
   			            		   ps.setBinaryStream(1, inputStream, contentLength);
   			            		   
   			            	   }catch(Exception e){
   			            		   logger.debug("Excepcion en setear flujo de bytes: "+e.getMessage());
   			            	   }          
   			            	   ps.setString(2, faxesVO.getNmcaso());
   			            	   ps.setString(3, faxesVO.getNmfax());
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
   		   
   		//String msg_Id;
		   
		   msg_Id = cs.getString(10);
		  BackBoneResultVO backBoneResultVO = new BackBoneResultVO();
		  //backBoneResultVO.setOutParam(cs.getString(2));
		  backBoneResultVO.setMsgText(cs.getString(9));
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
    
    
    
    /***********************************Fin guardar nuevo con el arreglo de guardar Archivos Grandes***********************************************/
    
   
	public void setLobHandler(LobHandler lobHandler) {
		this.lobHandler = lobHandler;
	}

	public void setAbstractDAO(AbstractDAO abstractDAO) {
		this.abstractDAO = abstractDAO;
	}

	public void setProcessResultManager(ProcessResultManager processResultManager) {
		this.processResultManager = processResultManager;
	}

	public String getNmFaxAux() {
		return nmFaxAux;
	}

	public void setNmFaxAux(String nmFaxAux) {
		this.nmFaxAux = nmFaxAux;
	}


}
