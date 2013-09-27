package mx.com.aon.pdfgenerator.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;

public class PDFServicesEndososJDBC {
	private static Logger logger = Logger.getLogger(PDFServicesJDBC.class);

	public PDFServicesEndososJDBC() {

	}
	
	
	
	public static String obtenTipoEndoso(String cdUnieco, String cdRamo, String cdEstado, 
													String nmPoliza, String  nmSuplem) {

		if (logger.isDebugEnabled()) {
			logger.debug(">>>>>>> HHH Inicia obtenOpcionCaratulaEndosos...");
			logger.debug("cdUnieco: " + cdUnieco);
			logger.debug("cdRamo: " + cdRamo);
			logger.debug("estado: " + cdEstado);
			logger.debug("nmpoliza: " + nmPoliza);
			logger.debug("nmsuplem: " + nmSuplem);
		}

		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;

		int wcdunieco = Integer.parseInt(cdUnieco.trim());
		int wcdramo = Integer.parseInt(cdRamo.trim());
		int wnmpoliza = Integer.parseInt(nmPoliza.trim());
		//long wnmsuplem =  Long.parseLong(nmSuplem.trim());

		logger.debug("wcdunieco: " + wcdunieco);
		logger.debug("wcdramo: " + wcdramo);
		logger.debug("estado: " + cdEstado);
		logger.debug("wnmpoliza: " + wnmpoliza);
		logger.debug("wnmsuplem: " + nmSuplem);

		// respuesta
		String tipoOpcion = "";
		
		try {
			
			/*
			String host = "192.168.1.190", puerto = "1527", SID = "ACWD";
			String driver = "oracle.jdbc.driver.OracleDriver";
			String user = "ICE_CONFIGURING", pass = "ICE_CONFIGURING";
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			conn = DriverManager.getConnection("jdbc:oracle:thin:@" + host+ ":" + puerto + ":" + SID, user, pass);
			
			
			// Se realiza la conexión
			//*/
			//conn = QuerysBd.getConnection();
		
			logger.debug(">>>>>>> antes de llamar a obtenOpcionCaratulaEndosos");
				
			cs = conn.prepareCall("{ call PKG_CARATULAS2.P_Obtiene_Tipo_Endoso( ?,?,?,?,?,? ) }");
			cs.setInt(1, wcdunieco);
			cs.setInt(2, wcdramo);
			cs.setString(3, cdEstado.trim());
			cs.setInt(4, wnmpoliza);
			cs.setString(5, nmSuplem);
			cs.registerOutParameter(6, OracleTypes.VARCHAR);

			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Tipo_Endoso ::: in :::" + cs.toString());
			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Tipo_Endoso ::: in :::" + cs.toString());
			cs.execute();
			
			tipoOpcion = cs.getString(6);
			logger.debug(">>>>>>> P_Obtiene_Tipo_Endoso ::: out :::" + tipoOpcion);
					
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (cs != null)
					cs.close();
				if (conn != null)
					conn.close();
			} catch (SQLException sql) {
				sql.printStackTrace();
			}
		}

		return tipoOpcion;
	}

	
	public static ArrayList<HashMap<String,String>> obtenEndososB(String cdUnieco, String cdRamo, String cdEstado, 
			String nmPoliza, String  nmSuplem, String nmSituac) {
		
		ArrayList<Long> nmOrdinas= new ArrayList<Long>();
		ArrayList<Long> nmTipsups= new ArrayList<Long>();
		ArrayList<HashMap<String,String>> endosos= new ArrayList<HashMap<String,String>>();
		int numCols=0;
		//endosos.keySet().
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		
		int wcdunieco = Integer.parseInt(cdUnieco.trim());
		int wcdramo = Integer.parseInt(cdRamo.trim());
		int wnmpoliza = Integer.parseInt(nmPoliza.trim());
		long wnmsituac = Long.parseLong(nmSituac.trim());
		//long wnmsuplem =  Long.parseLong(nmSuplem.trim());
		
		
		if (logger.isDebugEnabled()) {
			logger.debug(">>>>>>> Inicia obtenEndososB...");
			logger.debug("cdUnieco: " + cdUnieco);
			logger.debug("cdRamo: " + cdRamo);
			logger.debug("estado: " + cdEstado);
			logger.debug("nmpoliza: " + nmPoliza);
			logger.debug("nmsuplem: " + nmSuplem);
			}
		// respuesta
		
		
		try {
			/*String host = "192.168.1.190", puerto = "1527", SID = "ACWD";
			String driver = "oracle.jdbc.driver.OracleDriver";
			String user = "ICE_CONFIGURING", pass = "ICE_CONFIGURING";
			
			
			try{
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				logger.debug("Error:" + e.toString());
				logger.error(e, e);
				e.printStackTrace();
			}
				conn = DriverManager.getConnection("jdbc:oracle:thin:@" + host
						+ ":" + puerto + ":" + SID, user, pass);
				
				logger.debug("Conexion abierta");
			
			*/
		// Se realiza la conexión
		//conn = QuerysBd.getConnection();
		
				logger.debug(">>>>>>> antes de llamar a P_busca_cam_B");
				cs = conn.prepareCall("{ call PKG_CARATULAS2.P_busca_cam_B( ?,?,?,?,?,?,? ) }");
				cs.setInt(1, wcdunieco);
				cs.setInt(2, wcdramo);
				cs.setString(3, cdEstado.trim());
				cs.setInt(4, wnmpoliza);
				cs.setLong(5, wnmsituac);
				cs.setString(6, nmSuplem);
				cs.registerOutParameter(7, OracleTypes.CURSOR);
				
				logger.debug(">>>>>>> PKG_CARATULAS2.P_busca_cam_B ::: in :::" + cs.toString());
				
				cs.execute();
				
				rs = (ResultSet) cs.getObject(7);
				logger.debug(">>>>>>> PKG_CARATULAS2.P_busca_cam_B ::: out :::" + rs.toString());
				
				ResultSetMetaData metaData=cursorResultSetMetaData(rs, "PKG_CARATULAS2.P_busca_cam_B" );
				numCols=metaData.getColumnCount();
				
				while(rs.next()){
					for(int i=1 ;i<=numCols;i++){
						logger.debug("  Para un resultSet el valor correspondiente a la columna '"+metaData.getColumnName(i)+"' es: "+rs.getString(i));
					}
					nmOrdinas.add(new Long(rs.getLong("NMORDINA")));
					nmTipsups.add(new Long(rs.getLong("CDTIPSUP")));
				} 
		Long nmOrdina = new Long("0");
		Long nmTipsup = new Long("0");
		logger.debug("Tamaño de nmordinas es: "+nmOrdinas.size());
			for(int i=0;i<nmOrdinas.size();i++){
				nmOrdina=nmOrdinas.get(i);
				nmTipsup=nmTipsups.get(i);
				logger.debug("---->>>>>>> Antes de llamar a obtenEndososB para un nmOrdina= "+nmOrdina.toString()+" usando los parametros: <<<<<<<-----");
				logger.debug("wcdunieco="+wcdunieco);
				logger.debug("wcdramo="+wcdramo);
				logger.debug("cdEstado.trim()="+cdEstado.trim());
				logger.debug("wnmpoliza="+wnmpoliza);
				logger.debug("wnmsituac="+wnmsituac);
				logger.debug("wnmsuplem="+nmSuplem);
				logger.debug("nmTipsup.longValue()="+nmTipsup.longValue());
				logger.debug("nmOrdina.longValue()="+nmOrdina.longValue());
				if(nmOrdina!=null){
					if(cs!=null)cs.close();
						
					
						
						cs = conn.prepareCall("{ call PKG_CARATULAS2.P_Obtiene_Endosos_B( ?,?,?,?,?,?,?,?,? ) }");
						cs.setInt(1, wcdunieco);
						cs.setInt(2, wcdramo);
						cs.setString(3, cdEstado.trim());
						cs.setInt(4, wnmpoliza);
						cs.setLong(5, wnmsituac);
						cs.setString(6, nmSuplem);
						cs.setLong(7, nmTipsup.longValue());
						cs.setLong(8, nmOrdina.longValue());
						cs.registerOutParameter(9, OracleTypes.CURSOR);
						
						logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Endosos_B ::: in :::" + cs.toString());
						logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Endosos_B ::: in :::" + cs.toString());
						cs.execute();
						
						rs = (ResultSet) cs.getObject(9);
						logger.debug(">>>>>>> P_Obtiene_Endosos_B ::: out :::" + rs.toString());
						
						metaData=cursorResultSetMetaData(rs, "PKG_CARATULAS2.P_Obtiene_Endosos_B" );
						numCols=metaData.getColumnCount();
						int numRow=1;
						HashMap<String,String> endoso= new HashMap<String,String>();
						while(rs.next()){
							endoso= new HashMap<String,String>();
							endoso.put("CDTIPSUP", nmTipsup.toString());
							for(int j=1 ;j<=numCols;j++){
								
								logger.debug(" Para un resultSet num='"+numRow+"' con nmOrdina: "+nmOrdina.toString()+" en la col columna '"+metaData.getColumnName(j)+"' es: "+rs.getString(j));
								endoso.put(metaData.getColumnName(j),rs.getString(j));
							}
							
							endosos.add(endoso);
							numRow++;
						} 
				}
			}

		
		} catch (NumberFormatException e) {
		e.printStackTrace();
		} catch (SQLException e) {
		e.printStackTrace();
		} finally {
		try {
		if (rs != null)
		rs.close();
		if (cs != null)
		cs.close();
		if (conn != null)
		conn.close();
		} catch (SQLException sql) {
		sql.printStackTrace();
		}
		}
		logger.debug("Saliendo de ObieneEndosoB, los endosos son:"+endosos);
		return endosos;
	}
	
	
	public static ArrayList<HashMap<String,String>>  obtieneEndososAD(String cdUnieco, String cdRamo, String cdEstado, 
			String nmPoliza, String  nmSuplem, String nmSituac) {
		
		if (logger.isDebugEnabled()) {
			logger.debug(">>>>>>> Inicia obtieneEndososAD.");
		}
		ArrayList<HashMap<String,String>> coberturas= new ArrayList<HashMap<String,String>>();
		int numCols=0;
		
		NumberFormat decimalsFormat = NumberFormat.getNumberInstance();
	    decimalsFormat.setMaximumFractionDigits(2);

		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		
		int wcdunieco = Integer.parseInt(cdUnieco.trim());
		int wcdramo = Integer.parseInt(cdRamo.trim());
		int wnmpoliza = Integer.parseInt(nmPoliza.trim());
		long wnmsituac = Long.parseLong(nmSituac.trim());
		//long wnmsuplem =  Long.parseLong(nmSuplem.trim());
		
		
		if (logger.isDebugEnabled()) {
			logger.debug(">>>>>>> Inicia P_Obtiene_Endosos_AD...");
			logger.debug("cdUnieco: " + cdUnieco);
			logger.debug("cdRamo: " + cdRamo);
			logger.debug("estado: " + cdEstado);
			logger.debug("nmpoliza: " + nmPoliza);
			logger.debug("nmsuplem: " + nmSuplem);
			}
		// respuesta
		
		
		try {
			/*String host = "192.168.1.190", puerto = "1527", SID = "ACWD";
			String driver = "oracle.jdbc.driver.OracleDriver";
			String user = "ICE_CONFIGURING", pass = "ICE_CONFIGURING";
			
			
			try{
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				logger.debug("Error:" + e.toString());
				logger.error(e, e);
				e.printStackTrace();
			}
				conn = DriverManager.getConnection("jdbc:oracle:thin:@" + host
						+ ":" + puerto + ":" + SID, user, pass);
				
				logger.debug("Conexion abierta");
			
			*/
		// Se realiza la conexión
		//conn = QuerysBd.getConnection();
		
				logger.debug(">>>>>>> antes de llamar a P_Obtiene_Endosos_AD");
				cs = conn.prepareCall("{ call PKG_CARATULAS2.P_Obtiene_Endosos_AD( ?,?,?,?,?,?,? ) }");
				cs.setInt(1, wcdunieco);
				cs.setInt(2, wcdramo);
				cs.setString(3, cdEstado.trim());
				cs.setInt(4, wnmpoliza);
				cs.setLong(5, wnmsituac);
				cs.setString(6, nmSuplem);
				cs.registerOutParameter(7, OracleTypes.CURSOR);
				
				logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Endosos_AD ::: in :::" + cs.toString());
				
				cs.execute();
				
				rs = (ResultSet) cs.getObject(7);
				logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Endosos_AD ::: out :::" + rs.toString());
				
				ResultSetMetaData metaData=cursorResultSetMetaData(rs, "PKG_CARATULAS2.P_Obtiene_Endosos_AD" );
				numCols=metaData.getColumnCount();
				
				double sumaPrima = 0.0;
				String valorSumaString = "";
				HashMap<String,String> fila= new HashMap<String,String>();
				String celda= new String();
				while(rs.next()){
					fila= new HashMap<String,String>();
					for(int i=1 ;i<=numCols;i++){
						celda=rs.getString(i);
							if(celda!=null){
								celda=celda.trim();
							}
							else{
								celda=" ";
							}
						fila.put(Integer.toString(i), celda);
						logger.debug(" Para un resultSet el valor correspondiente a la columna '"+metaData.getColumnName(i)+"' es: "+celda);
					}
					
					sumaPrima += rs.getDouble("PRIMARECIBONUM");
					
					coberturas.add(fila);
				} 
				
				if(coberturas.isEmpty()){
					logger.debug("NO HAY DATOS EN LAS COBERTURAS!!! para PKG_CARATULAS2.P_Obtiene_Endosos_AD ");
					return coberturas;
				}
				
				logger.debug("Para la suma de la  prima");
				valorSumaString = decimalsFormat.format(sumaPrima);
				fila= new HashMap<String,String>();
				for(int i=1 ;i<=numCols;i++){
					if(i==numCols-1){
						fila.put(Integer.toString(i-1), "PRIMA NETA");
						fila.put(Integer.toString(i),valorSumaString);
					}else{
						fila.put(Integer.toString(i),"");
					}
					
				}
				
				coberturas.add(fila);
				
				logger.debug("Saliendo de Obtienecoberturas AD");
				logger.debug("El valor de la lista de las cobeturas es : "+coberturas);
						
					
		
		} catch (NumberFormatException e) {
		e.printStackTrace();
		} catch (SQLException e) {
		e.printStackTrace();
		} finally {
		try {
		if (rs != null)
		rs.close();
		if (cs != null)
		cs.close();
		if (conn != null)
		conn.close();
		} catch (SQLException sql) {
		sql.printStackTrace();
		}
		}
		
		return coberturas;
	}
	
	public static ResultSetMetaData cursorResultSetMetaData(ResultSet rs, String pl ){
		
		ResultSetMetaData metaData = null;
		int numeroCols = 0;
		String nameMetaData ="";
		String typeMetaData ="";
		
		try {
			metaData = rs.getMetaData();
			numeroCols = metaData.getColumnCount();
			logger.debug("");
			logger.debug(">>>>>>> ----------------------");
			logger.debug(">>>>>>> RESULTSET METADATA - PL ::: " + pl);
			logger.debug("número de columnas ::: " + numeroCols);
			
			for(int i=1; i< numeroCols+1; i++){
				nameMetaData = metaData.getColumnName(i);
				typeMetaData = metaData.getColumnTypeName(i);
				logger.debug("metaData["+i+"] ::: " +nameMetaData+"   type "+typeMetaData);
			}			
			logger.debug(">>>>>>> ----------------------");
			logger.debug("");
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return metaData;
	}
	
	public static void main(String []args){
		
		//obtenEndososB("1", "500", "M", "182", "245491412000000000", "1");
		
		//para el caso de AD con 6702 y 245490412000000001
		//obtieneEndososAD("1", "500", "M", "6696", "245487512000000000", "1");
		//de  Andrea
		//obtieneEndososAD("1", "500", "M", "6707", "245491612000000000", "1");
		
	}
	
}
