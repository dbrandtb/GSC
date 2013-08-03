package mx.com.aon.pdfgenerator.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import mx.com.aon.pdfgenerator.vo.TituloVO;
import mx.com.ice.kernel.dao.QuerysBd;
import oracle.jdbc.OracleTypes;

public class PruebasJDBC {
	private static Logger logger = Logger.getLogger(PruebasJDBC.class);

	public PruebasJDBC() {

	}
	
	
	
	
	public static void main(String []args){
		
		//obtenEndososB("1", "500", "M", "182", "245491412000000000", "1");
		
		//para el caso de AD con 6702 y 245490412000000001
		//obtieneEndososAD("1", "500", "M", "6696", "245487512000000000", "1");
		//de  Andrea
		//obtieneEndososAD("1", "500", "M", "6707", "245491612000000000", "1");
		int x;
		//pruebasJDBC(1,"","","","","I","","","","");
		
//		pruebasAsegu();
		pruebasJDBCokCursor();
		//obtieneMensaje();
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
	
	public static void pruebasJDBCa(){
		
		
			
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
			e.printStackTrace();
			}
			conn = DriverManager.getConnection("jdbc:oracle:thin:@" + host+ ":" + puerto + ":" + SID, user, pass);
			
			logger.debug(">>>>>>> antes de llamar a PKG_CONFIGURA_PANTALLA2.P_CARGA_ATRIBUTOS_PROPIEDADES");
			String nulo="";
			
			int j=1;
			cs = conn.prepareCall("{ call PKG_CONFIGURA_PANTALLA2.P_CARGA_ATRIBUTOS_PROPIEDADES(?,?,?,?,?,?,?,?) }");
			cs.setString(j++, "2");//Unieco
			cs.setString(j++, "1");//ramo
			cs.setString(j++, "3");//poliex
			cs.setString(j++, "500"	);//dsnombre
			cs.setString(j++, "1");//cia
			/*cs.setString(6, "I");//fecha
			cs.setString(7, nulo);//desde
			cs.setString(8, nulo);//hasta
			cs.setString(9, nulo);//Estado
			cs.setString(10, nulo);//inciso*/
			cs.registerOutParameter(j++, OracleTypes.CURSOR);
			cs.registerOutParameter(j++, OracleTypes.NUMERIC);
			cs.registerOutParameter(j, OracleTypes.VARCHAR);
			
			/*
			
			cs = conn.prepareCall("{ call PKG_ENDOSOS.P_TARIFICA_INCISOS_FALTANTES( ?,?,?,?,?,?,?,? ) }");
			cs.setString(1, "1");//Unieco
			cs.setString(2, "500");//ramo
			cs.setString(3, "M");//poliex
			cs.setInt(4, 6772);//dsnombre
			cs.setString(5, "245493712000000000");//cia
			
			
			cs.registerOutParameter(6, OracleTypes.NUMERIC);
			cs.registerOutParameter(7, OracleTypes.NUMERIC);
			cs.registerOutParameter(8, OracleTypes.VARCHAR);
			
			cs.execute();
			
			
			int numero=cs.getInt(6);*/
			cs.execute();
			
			String id= cs.getString(j-1);
			String msg= cs.getString(j);
			
			
			rs = (ResultSet) cs.getObject(j-2);
			
			logger.debug(">>>>>>> SALIDA ::: out :::");
			//logger.debug(">>>>>>> numero  ::: out ::: = "+numero);
			logger.debug(">>>>>>> id  ::: out ::: = "+id);
			logger.debug(">>>>>>> msg  ::: out ::: = "+msg);
			
			
			
			ResultSetMetaData metaData=cursorResultSetMetaData(rs, "PKG_CONFIGURA_PANTALLA2.P_CARGA_ATRIBUTOS_PROPIEDADES" );
			int numCols=metaData.getColumnCount();
			
			
			while(rs.next()){
				//logger.debug("para NMPOLIEX= "+rs.getString("NMPOLIEX")+" el valor de la PRIMA es: "+rs.getString("PRIMA"));
				for(int i=1 ;i<=numCols;i++){
					logger.debug("  Para un resultSet el valor correspondiente a la columna '"+metaData.getColumnName(i)+"' es: "+rs.getString(i));
				}
				logger.debug("//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////");
			} 
			logger.debug(">>>>>>> END");
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


	
		
		
	}
	
	
	public static void pruebasAsegu(){
		
		
		
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
		e.printStackTrace();
		}
		conn = DriverManager.getConnection("jdbc:oracle:thin:@" + host+ ":" + puerto + ":" + SID, user, pass);
		
		logger.debug(">>>>>>> antes de llamar a PKG_EMISION.P_CONSULTA_POLIZAS");
		String nulo="";
		
		
		cs = conn.prepareCall("{ call PKG_CATBO.P_OBTIENE_MMATRIZ_ASEG(?,?,?,?,?,?) }");
		cs.setString(1, "");//Unieco
		cs.setString(2, "6330");//ramo
		cs.setString(3, "");//poliex
		//cs.setString(4, nulo);//dsnombre
		//cs.setString(5, nulo);//cia
		/*cs.setString(6, "I");//fecha
		cs.setString(7, nulo);//desde
		cs.setString(8, nulo);//hasta
		cs.setString(9, nulo);//Estado
		cs.setString(10, nulo);//inciso*/
		cs.registerOutParameter(4, OracleTypes.CURSOR);
		cs.registerOutParameter(5, OracleTypes.NUMERIC);
		cs.registerOutParameter(6, OracleTypes.VARCHAR);
		
		/*
		
		cs = conn.prepareCall("{ call PKG_ENDOSOS.P_TARIFICA_INCISOS_FALTANTES( ?,?,?,?,?,?,?,? ) }");
		cs.setString(1, "1");//Unieco
		cs.setString(2, "500");//ramo
		cs.setString(3, "M");//poliex
		cs.setInt(4, 6772);//dsnombre
		cs.setString(5, "245493712000000000");//cia
		
		
		cs.registerOutParameter(6, OracleTypes.NUMERIC);
		cs.registerOutParameter(7, OracleTypes.NUMERIC);
		cs.registerOutParameter(8, OracleTypes.VARCHAR);
		
		cs.execute();
		
		
		int numero=cs.getInt(6);*/
		cs.execute();
		
		String id= cs.getString(5);
		String msg= cs.getString(6);
		
		
		rs = (ResultSet) cs.getObject(4);
		
		logger.debug(">>>>>>> SALIDA ::: out :::");
		//logger.debug(">>>>>>> numero  ::: out ::: = "+numero);
		logger.debug(">>>>>>> id  ::: out ::: = "+id);
		logger.debug(">>>>>>> msg  ::: out ::: = "+msg);
		
		
		
		ResultSetMetaData metaData=cursorResultSetMetaData(rs, "PKG_CATBO.P_OBTIENE_EMAIL( ?,?,?,? )" );
		int numCols=metaData.getColumnCount();
		
		
		while(rs.next()){
			//logger.debug("para NMPOLIEX= "+rs.getString("NMPOLIEX")+" el valor de la PRIMA es: "+rs.getString("PRIMA"));
			for(int i=1 ;i<=numCols;i++){
				logger.debug("  Para un resultSet el valor correspondiente a la columna '"+metaData.getColumnName(i)+"' es: "+rs.getString(i));
			}
			logger.debug("//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////");
		} 
		logger.debug(">>>>>>> END");
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



	
	
}
	
public static void pruebasJDBCok(){
		
		
		
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
		e.printStackTrace();
		}
		conn = DriverManager.getConnection("jdbc:oracle:thin:@" + host+ ":" + puerto + ":" + SID, user, pass);
		
		logger.debug(">>>>>>> antes de llamar a PKG_NOTIFICACIONES_CATBO.P_OBTIENE_TNOTPROC_PROCESOS( ?,?,?,?,?,? )");
		String nulo="";
		
		
		cs = conn.prepareCall("{ call P_EXEC_VALIDADOR (?,?,?,?,?,?,?,?,?) }");
		int j=1;
		cs.setString(j++, "1");//Notif
		cs.setString(j++, "41");//Notif
		cs.setString(j++, "W");//Notif
		cs.setString(j++, "1519");//Notif
		cs.setString(j++, "1");//Notif
		cs.setString(j++, "B5B");//Notif
		
		
		/*para cursores: 
		cs.registerOutParameter(j++, OracleTypes.CURSOR);
		cs.registerOutParameter(j++, OracleTypes.NUMERIC);
		cs.registerOutParameter(j, OracleTypes.VARCHAR);
		*/
		
		
		cs.registerOutParameter(j++, OracleTypes.VARCHAR);
		cs.registerOutParameter(j++, OracleTypes.NUMERIC);
		cs.registerOutParameter(j, OracleTypes.VARCHAR);
		cs.execute();
		
		
		String msgAux0 = cs.getString(j-2); // SIN CURSORES
		String id= cs.getString(j-1);
		String msg= cs.getString(j);
		
		
		/*
		 *PARA CURSORES
		 rs = (ResultSet) cs.getObject(j-2);*/
		
		logger.debug(">>>>>>> SALIDA ::: out :::");
		
		logger.debug(">>>>>>> msgAux0  ::: out ::: = "+msgAux0);
		logger.debug(">>>>>>> id  ::: out ::: = "+id);
		logger.debug(">>>>>>> msg/title  ::: out ::: = "+msg);
		/*
		 *PARA CURSORES 
		ResultSetMetaData metaData=cursorResultSetMetaData(rs, ".P_OBTIENE_MNOTIFIC_REG( ?,?,?,?,?,? )" );
		int numCols=metaData.getColumnCount();
		
		
		while(rs.next()){
			//logger.debug("para NMPOLIEX= "+rs.getString("NMPOLIEX")+" el valor de la PRIMA es: "+rs.getString("PRIMA"));
			for(int i=1 ;i<=numCols;i++){
				logger.debug("  Para un resultSet el valor correspondiente a la columna '"+metaData.getColumnName(i)+"' es: "+rs.getString(i));
			}
			logger.debug("//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////");
		} */
		logger.debug(">>>>>>> END");
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





	}
	
	
	public static void pruebasJDBCokCursor(){
		
		
		
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
				e.printStackTrace();
			}
			conn = DriverManager.getConnection("jdbc:oracle:thin:@" + host+ ":" + puerto + ":" + SID, user, pass);
			
			logger.debug(">>>>>>> antes de llamar a PKG_NOTIFICACIONES_CATBO.P_OBTIENE_TNOTPROC_PROCESOS( ?,?,?,?,?,? )");
			String nulo="";
			
			
			cs = conn.prepareCall("{ call PKG_COTIZA.P_GEN_TARIFICACION(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }");
			int j=1;
			cs.setString(j++, "PARAMETRIZA1");
			cs.setString(j++, "1");
			cs.setString(j++, "500");
			cs.setString(j++, "W");
			cs.setString(j++, "4518");
			cs.setString(j++, "6117");
			cs.setString(j++, "1");
			
			cs.registerOutParameter(j++, OracleTypes.CURSOR);
			cs.registerOutParameter(j++, OracleTypes.NUMERIC);
			cs.registerOutParameter(j, OracleTypes.VARCHAR);
			
			cs.execute();
		
			String id= cs.getString(j-1);
			String msg= cs.getString(j);
			 
			rs = (ResultSet) cs.getObject(j-2);
			
			logger.debug(">>>>>>> SALIDA ::: out :::");
			
			logger.debug(">>>>>>> id  ::: out ::: = "+id);
			logger.debug(">>>>>>> msg/title  ::: out ::: = "+msg);
			
			ResultSetMetaData metaData=cursorResultSetMetaData(rs, ".P_OBTIENE_MNOTIFIC_REG( ?,?,?,?,?,? )" );
			int numCols=metaData.getColumnCount();
			
			
			while(rs.next()){
				//logger.debug("para NMPOLIEX= "+rs.getString("NMPOLIEX")+" el valor de la PRIMA es: "+rs.getString("PRIMA"));
				for(int i=1 ;i<=numCols;i++){
					logger.debug("  Para un resultSet el valor correspondiente a la columna '"+metaData.getColumnName(i)+"' es: "+rs.getString(i));
				}
				logger.debug("//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////");
			}
			logger.debug(">>>>>>> END");
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
	
	
	
	
	
	}

public static void pruebasJDBCnotif(){
		
		
		
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		
		
		
		try {
		
		
		String host = "192.168.1.190", puerto = "1527", SID = "ACWD";
		String driver = "oracle.jdbc.driver.OracleDriver";
		String user = "ICE_CONFIGURING", pass = "ICE_CONFIGURING";
		try {
		Class.forName(driver);
		} catch (ClassNotFoundException e) {
		e.printStackTrace();
		}
		conn = DriverManager.getConnection("jdbc:oracle:thin:@" + host+ ":" + puerto + ":" + SID, user, pass);
		
		logger.debug(">>>>>>> antes de llamar a PKG_NOTIFICACIONES_CATBO.P_OBTIENE_TNOTPROC_PROCESOS( ?,?,?,?,?,? )");
		String nulo="";
		
		
		cs = conn.prepareCall("{ call PKG_NOTIFICACIONES_CATBO.P_GUARDA_MNOTIFIC (?,?,?,?) }");
		int j=1;
		cs.setString(j++, "VARNOTIFIC");//Notif
		
		cs.registerOutParameter(j++, OracleTypes.CURSOR);
		cs.registerOutParameter(j++, OracleTypes.NUMERIC);
		cs.registerOutParameter(j, OracleTypes.VARCHAR);
		
		
		cs.execute();
		
		String id= cs.getString(j-1);
		String msg= cs.getString(j);
		
		
		rs = (ResultSet) cs.getObject(j-2);
		
		logger.debug(">>>>>>> SALIDA ::: out :::");
		
		logger.debug(">>>>>>> id  ::: out ::: = "+id);
		logger.debug(">>>>>>> msg  ::: out ::: = "+msg);
		
		
		
		ResultSetMetaData metaData=cursorResultSetMetaData(rs, ".P_OBTIENE_MNOTIFIC_REG( ?,?,?,?,?,? )" );
		int numCols=metaData.getColumnCount();
		
		
		while(rs.next()){
			//logger.debug("para NMPOLIEX= "+rs.getString("NMPOLIEX")+" el valor de la PRIMA es: "+rs.getString("PRIMA"));
			for(int i=1 ;i<=numCols;i++){
				logger.debug("  Para un resultSet el valor correspondiente a la columna '"+metaData.getColumnName(i)+"' es: "+rs.getString(i));
			}
			logger.debug("//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////");
		} 
		logger.debug(">>>>>>> END");
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





	}
	
	public static void pruebasJDBCb(){
		
		
		
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
		e.printStackTrace();
		}
		conn = DriverManager.getConnection("jdbc:oracle:thin:@" + host+ ":" + puerto + ":" + SID, user, pass);
		
		logger.debug(">>>>>>> antes de llamar a P_OBTIENE_PERSONA");
		String nulo="";
		
		
		cs = conn.prepareCall("{ call PKG_PERSONA.P_OBTIENE_PERSONA(?,?,?,?,?,?,?,?,?) }");
		cs.setString(1, nulo);//Unieco
		cs.setString(2, nulo);//ramo
		cs.setString(3, nulo);//poliex
		cs.setString(4, nulo);//dsnombre
		cs.setString(5, nulo);//cia
		cs.setString(6, nulo);//fecha
		/*cs.setString(7, nulo);//desde
		cs.setString(8, nulo);//hasta
		cs.setString(9, nulo);//Estado
		cs.setString(10, nulo);//inciso*/
		cs.registerOutParameter(7, OracleTypes.CURSOR);
		cs.registerOutParameter(8, OracleTypes.NUMERIC);
		cs.registerOutParameter(9, OracleTypes.VARCHAR);
		
		/*
		
		cs = conn.prepareCall("{ call PKG_ENDOSOS.P_TARIFICA_INCISOS_FALTANTES( ?,?,?,?,?,?,?,? ) }");
		cs.setString(1, "1");//Unieco
		cs.setString(2, "500");//ramo
		cs.setString(3, "M");//poliex
		cs.setInt(4, 6772);//dsnombre
		cs.setString(5, "245493712000000000");//cia
		
		
		cs.registerOutParameter(6, OracleTypes.NUMERIC);
		cs.registerOutParameter(7, OracleTypes.NUMERIC);
		cs.registerOutParameter(8, OracleTypes.VARCHAR);
		
		cs.execute();
		
		
		int numero=cs.getInt(6);*/
		cs.execute();
		
		String id= cs.getString(8);
		String msg= cs.getString(9);
		
		
		rs = (ResultSet) cs.getObject(7);
		
		logger.debug(">>>>>>> SALIDA ::: out :::");
		//logger.debug(">>>>>>> numero  ::: out ::: = "+numero);
		logger.debug(">>>>>>> id  ::: out ::: = "+id);
		logger.debug(">>>>>>> msg  ::: out ::: = "+msg);
		
		
		
		ResultSetMetaData metaData=cursorResultSetMetaData(rs, "PKG_CATBO.P_OBTIENE_EMAIL( ?,?,?,? )" );
		int numCols=metaData.getColumnCount();
		
		
		while(rs.next()){
			//logger.debug("para NMPOLIEX= "+rs.getString("NMPOLIEX")+" el valor de la PRIMA es: "+rs.getString("PRIMA"));
			for(int i=1 ;i<=numCols;i++){
				logger.debug("  Para un resultSet el valor correspondiente a la columna '"+metaData.getColumnName(i)+"' es: "+rs.getString(i));
			}
			logger.debug("//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////");
		} 
		logger.debug(">>>>>>> END");
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



	
	
}

	
public static void pruebasJDBCc(){
		
		
		
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		
		
		
		try {
		
		
		String host = "192.168.1.190", puerto = "1527", SID = "ACWD";
		String driver = "oracle.jdbc.driver.OracleDriver";
		String user = "ICE_CONFIGURING", pass = "ICE_CONFIGURING";
		try {
		Class.forName(driver);
		} catch (ClassNotFoundException e) {
		e.printStackTrace();
		}
		conn = DriverManager.getConnection("jdbc:oracle:thin:@" + host+ ":" + puerto + ":" + SID, user, pass);
		
		logger.debug(">>>>>>> antes de llamar a PKG_CATBO.P_OBTIENE_CDMATRIZ");
		String nulo="";
		
		
		cs = conn.prepareCall("{ call PKG_CATBO.P_OBTIENE_CDMATRIZ(?,?,?,?,?,?) }");
		int j=1;
		cs.setString(j++, nulo);//Unieco
		cs.setString(j++, nulo);//Unieco
//		cs.setString(j++, "30");//Unieco
		cs.setString(j++, nulo);//ramo
		//cs.setString(j++, "6302");//poliex
		//cs.setString(j++, "9");//poliex
		/*cs.setString(4, nulo);//dsnombre
		cs.setString(5, nulo);//cia
		cs.setString(6, nulo);//fecha
		/*cs.setString(7, nulo);//desde
		cs.setString(8, nulo);//hasta
		cs.setString(9, nulo);//Estado
		cs.setString(10, nulo);//inciso*/
		cs.registerOutParameter(j++, OracleTypes.CURSOR);
		cs.registerOutParameter(j++, OracleTypes.NUMERIC);
		cs.registerOutParameter(j, OracleTypes.VARCHAR);
		
		/*
		
		cs = conn.prepareCall("{ call PKG_ENDOSOS.P_TARIFICA_INCISOS_FALTANTES( ?,?,?,?,?,?,?,? ) }");
		cs.setString(1, "1");//Unieco
		cs.setString(2, "500");//ramo
		cs.setString(3, "M");//poliex
		cs.setInt(4, 6772);//dsnombre
		cs.setString(5, "245493712000000000");//cia
		
		
		cs.registerOutParameter(6, OracleTypes.NUMERIC);
		cs.registerOutParameter(7, OracleTypes.NUMERIC);
		cs.registerOutParameter(8, OracleTypes.VARCHAR);
		
		cs.execute();
		
		
		int numero=cs.getInt(6);*/
		cs.execute();
		
		String id= cs.getString(j-1);
		String msg= cs.getString(j);
		
		
		rs = (ResultSet) cs.getObject(j-2);
		
		logger.debug(">>>>>>> SALIDA ::: out :::");
		//logger.debug(">>>>>>> numero  ::: out ::: = "+numero);
		logger.debug(">>>>>>> id  ::: out ::: = "+id);
		logger.debug(">>>>>>> msg  ::: out ::: = "+msg);
		
		
		
		ResultSetMetaData metaData=cursorResultSetMetaData(rs, "PKG_CATBO.P_OBTIENE_EMAIL( ?,?,?,? )" );
		int numCols=metaData.getColumnCount();
		
		
		while(rs.next()){
			//logger.debug("para NMPOLIEX= "+rs.getString("NMPOLIEX")+" el valor de la PRIMA es: "+rs.getString("PRIMA"));
			for(int i=1 ;i<=numCols;i++){
				logger.debug("  Para un resultSet el valor correspondiente a la columna '"+metaData.getColumnName(i)+"' es: "+rs.getString(i));
			}
			logger.debug("//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////");
		} 
		logger.debug(">>>>>>> END");
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



	
	
}


public static void pruebasJDBCcotiza(){
	
	
	
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
	e.printStackTrace();
	}
	conn = DriverManager.getConnection("jdbc:oracle:thin:@" + host+ ":" + puerto + ":" + SID, user, pass);
	
	logger.debug(">>>>>>> antes de llamar a PKG_COTIZA.PKG_CATBO.P_OBTIENE_COTIZA_CDCIA( ?,?,?,?,?,? )");
	String nulo="";
	
	
	cs = conn.prepareCall("{ call PKG_COTIZA.PKG_CATBO.P_OBTIENE_COTIZA_CDCIA( ?,?,?,?,?,?,? ) }");
	int j=1;
	cs.setString(j++, "1");//Unieco
	cs.setString(j++, "500");//Unieco
	cs.setString(j++, "W");//ramo
	cs.setString(j++, "581");//ramo
	//cs.setString(j++, "245495212000000000");//poliex
	//cs.setString(j++, "9");//poliex
	/*cs.setString(4, nulo);//dsnombre
	cs.setString(5, nulo);//cia
	cs.setString(6, nulo);//fecha
	/*cs.setString(7, nulo);//desde
	cs.setString(8, nulo);//hasta
	cs.setString(9, nulo);//Estado
	cs.setString(10, nulo);//inciso*/
	cs.registerOutParameter(j++, OracleTypes.CURSOR);
	cs.registerOutParameter(j++, OracleTypes.NUMERIC);
	cs.registerOutParameter(j, OracleTypes.VARCHAR);
	
	/*
	
	cs = conn.prepareCall("{ call PKG_ENDOSOS.P_TARIFICA_INCISOS_FALTANTES( ?,?,?,?,?,?,?,? ) }");
	cs.setString(1, "1");//Unieco
	cs.setString(2, "500");//ramo
	cs.setString(3, "M");//poliex
	cs.setInt(4, 6772);//dsnombre
	cs.setString(5, "245493712000000000");//cia
	
	
	cs.registerOutParameter(6, OracleTypes.NUMERIC);
	cs.registerOutParameter(7, OracleTypes.NUMERIC);
	cs.registerOutParameter(8, OracleTypes.VARCHAR);
	
	cs.execute();
	
	
	int numero=cs.getInt(6);*/
	cs.execute();
	
	String id= cs.getString(j-1);
	String msg= cs.getString(j);
	
	
	rs = (ResultSet) cs.getObject(j-2);
	
	logger.debug(">>>>>>> SALIDA ::: out :::");
	//logger.debug(">>>>>>> numero  ::: out ::: = "+numero);
	//logger.debug(">>>>>>> id  ::: out ::: = "+id);
	//logger.debug(">>>>>>> msg  ::: out ::: = "+msg);
	
	
	
	ResultSetMetaData metaData=cursorResultSetMetaData(rs, "PKG_COTIZA.PKG_CATBO.P_OBTIENE_COTIZA_CDCIA( ?,?,?,?,?,? )" );
	int numCols=metaData.getColumnCount();
	
	
	while(rs.next()){
		//logger.debug("para NMPOLIEX= "+rs.getString("NMPOLIEX")+" el valor de la PRIMA es: "+rs.getString("PRIMA"));
		for(int i=1 ;i<=numCols;i++){
			logger.debug("  Para un resultSet el valor correspondiente a la columna '"+metaData.getColumnName(i)+"' es: "+rs.getString(i));
		}
		logger.debug("//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////");
	} 
	logger.debug(">>>>>>> END");
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

}

public static void pruebasJDBCemision(){
	
	
	
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
	e.printStackTrace();
	}
	conn = DriverManager.getConnection("jdbc:oracle:thin:@" + host+ ":" + puerto + ":" + SID, user, pass);
	
	logger.debug(">>>>>>> antes de llamar a PKG_CARATULAS2.P_Obtiene_Datos_Poliza( ?,?,?,?,?,? )");
	String nulo="";
	
	
	cs = conn.prepareCall("{ call PKG_CARATULAS2.P_Obtiene_Datos_Poliza( ?,?,?,?,?,? ) }");
	int j=1;
	cs.setString(j++, "1");//Unieco
	cs.setString(j++, "500");//Unieco
	cs.setString(j++, "M");//ramo
	cs.setString(j++, "581");//ramo
	cs.setString(j++, "245495212000000000");//poliex
	//cs.setString(j++, "9");//poliex
	/*cs.setString(4, nulo);//dsnombre
	cs.setString(5, nulo);//cia
	cs.setString(6, nulo);//fecha
	/*cs.setString(7, nulo);//desde
	cs.setString(8, nulo);//hasta
	cs.setString(9, nulo);//Estado
	cs.setString(10, nulo);//inciso*/
	cs.registerOutParameter(j, OracleTypes.CURSOR);
	//cs.registerOutParameter(j++, OracleTypes.NUMERIC);
	//cs.registerOutParameter(j, OracleTypes.VARCHAR);
	
	/*
	
	cs = conn.prepareCall("{ call PKG_ENDOSOS.P_TARIFICA_INCISOS_FALTANTES( ?,?,?,?,?,?,?,? ) }");
	cs.setString(1, "1");//Unieco
	cs.setString(2, "500");//ramo
	cs.setString(3, "M");//poliex
	cs.setInt(4, 6772);//dsnombre
	cs.setString(5, "245493712000000000");//cia
	
	
	cs.registerOutParameter(6, OracleTypes.NUMERIC);
	cs.registerOutParameter(7, OracleTypes.NUMERIC);
	cs.registerOutParameter(8, OracleTypes.VARCHAR);
	
	cs.execute();
	
	
	int numero=cs.getInt(6);*/
	cs.execute();
	
	//String id= cs.getString(j-1);
	//String msg= cs.getString(j);
	
	
	rs = (ResultSet) cs.getObject(j);
	
	logger.debug(">>>>>>> SALIDA ::: out :::");
	//logger.debug(">>>>>>> numero  ::: out ::: = "+numero);
	//logger.debug(">>>>>>> id  ::: out ::: = "+id);
	//logger.debug(">>>>>>> msg  ::: out ::: = "+msg);
	
	
	
	ResultSetMetaData metaData=cursorResultSetMetaData(rs, "PKG_CARATULAS2.P_Obtiene_Datos_Poliza( ?,?,?,?,?,? )" );
	int numCols=metaData.getColumnCount();
	
	
	while(rs.next()){
		//logger.debug("para NMPOLIEX= "+rs.getString("NMPOLIEX")+" el valor de la PRIMA es: "+rs.getString("PRIMA"));
		for(int i=1 ;i<=numCols;i++){
			logger.debug("  Para un resultSet el valor correspondiente a la columna '"+metaData.getColumnName(i)+"' es: "+rs.getString(i));
		}
		logger.debug("//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////");
	} 
	logger.debug(">>>>>>> END");
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





}

	/**
	 * @param cdUnieco
	 * @param cdRamo
	 * @param cdEstado
	 * @param nmPoliza
	 * @param nmSuplem
	 * @param nmSituac
	 * @return
	 */
	public static ArrayList<HashMap<String,String>> pruebasJDBC(String cdUnieco, String cdRamo, String cdEstado, 
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
			String host = "192.168.1.190", puerto = "1527", SID = "ACWQA";
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
			
			
		// Se realiza la conexión
		//conn = QuerysBd.getConnection();
		
				
				cs = conn.prepareCall("{ call PKG_COTIZA.P_CONSULTA_COTIZA_MASIVA( ?,?,?,?,?,?,?,?,? ) }");
				cs.setString(1, "");
				cs.setString(2, "");
				cs.setString(3, "");
				cs.setString(4, "");
				cs.setString(5, "");
				cs.setString(6, "");
				cs.registerOutParameter(7, OracleTypes.CURSOR);
				cs.registerOutParameter(8, OracleTypes.NUMERIC);
				cs.registerOutParameter(9, OracleTypes.VARCHAR);
				
				logger.debug(">>>>>>> P_CONSULTA_COTIZA_MASIVA ::: in :::" + cs.toString());
				
				cs.execute();
				
				
				rs = (ResultSet) cs.getObject(7);
				
				String id= cs.getString(8);
				String msg= cs.getString(9);
				
				logger.debug(">>>>>>> SALIDA ::: out :::");
				//logger.debug(">>>>>>> numero  ::: out ::: = "+numero);
				logger.debug(">>>>>>> id  ::: out ::: = "+id);
				logger.debug(">>>>>>> msg  ::: out ::: = "+msg);
				
				
				
				ResultSetMetaData metaData=cursorResultSetMetaData(rs, "P_CONSULTA_COTIZA_MASIVA?,?,?,? )" );
				numCols=metaData.getColumnCount();
				
				
				while(rs.next()){
					//logger.debug("para NMPOLIEX= "+rs.getString("NMPOLIEX")+" el valor de la PRIMA es: "+rs.getString("PRIMA"));
					for(int i=1 ;i<=numCols;i++){
						logger.debug("  Para un resultSet el valor correspondiente a la columna '"+metaData.getColumnName(i)+"' es: "+rs.getString(i));
					}
					logger.debug("//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////");
					
				} 
				logger.debug(">>>>>>> END");
		
				
			

		
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
	
	
	
	public static void obtieneMensaje(){
		
		
		
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
		e.printStackTrace();
		}
		conn = DriverManager.getConnection("jdbc:oracle:thin:@" + host+ ":" + puerto + ":" + SID, user, pass);
		
		logger.debug(">>>>>>> antes de llamar a PKG_TRADUC.P_OBTIENE_MENSAJE");
		String nulo="";
		
		
		cs = conn.prepareCall("{ call PKG_TRADUC.P_OBTIENE_MENSAJE(?,?,?,?,?,?) }");
		cs.setInt(1, 100251);//Unieco
		cs.setString(2, "");//ramo
		cs.setString(3, "");//poliex
		cs.setString(4, ""	);//dsnombre
		//cs.setString(5, nulo);//cia
		/*cs.setString(6, "I");//fecha
		cs.setString(7, nulo);//desde
		cs.setString(8, nulo);//hasta
		cs.setString(9, nulo);//Estado
		cs.setString(10, nulo);//inciso*/
		
		cs.registerOutParameter(5, OracleTypes.VARCHAR);
		cs.registerOutParameter(6, OracleTypes.VARCHAR);
		
		/*
		
		cs = conn.prepareCall("{ call PKG_ENDOSOS.P_TARIFICA_INCISOS_FALTANTES( ?,?,?,?,?,?,?,? ) }");
		cs.setString(1, "1");//Unieco
		cs.setString(2, "500");//ramo
		cs.setString(3, "M");//poliex
		cs.setInt(4, 6772);//dsnombre
		cs.setString(5, "245493712000000000");//cia
		
		
		cs.registerOutParameter(6, OracleTypes.NUMERIC);
		cs.registerOutParameter(7, OracleTypes.NUMERIC);
		cs.registerOutParameter(8, OracleTypes.VARCHAR);
		
		cs.execute();
		
		
		int numero=cs.getInt(6);*/
		cs.execute();
		
		String id= cs.getString(5);
		String msg= cs.getString(6);
		
		
		
		logger.debug(">>>>>>> SALIDA ::: out :::");
		//logger.debug(">>>>>>> numero  ::: out ::: = "+numero);
		logger.debug(">>>>>>> id  ::: out ::: = "+id);
		logger.debug(">>>>>>> msg  ::: out ::: = "+msg);
	
		
		logger.debug(">>>>>>> END");
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


	}
	
	


}
