package mx.com.aon.pdfgenerator.dao;

import java.sql.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import oracle.jdbc.OracleTypes;
import mx.com.aon.pdfgenerator.util.Campo;
import mx.com.aon.pdfgenerator.util.UtilCampo;
import mx.com.aon.pdfgenerator.vo.AseguradoVO;
import mx.com.aon.pdfgenerator.vo.DataAdicionalVO;
import mx.com.aon.pdfgenerator.vo.DataCoberturasVO;
import mx.com.aon.pdfgenerator.vo.DataPolizaVO;
import mx.com.aon.pdfgenerator.vo.DataVehiculoVO;
import mx.com.aon.pdfgenerator.vo.DatoLiquidacionVO;
import mx.com.aon.pdfgenerator.vo.TituloVO;
import mx.com.ice.kernel.dao.QuerysBd;

import org.apache.log4j.Logger;

public class PDFServicesJDBC {

	private static Logger logger = Logger.getLogger(PDFServicesJDBC.class);

	private static String host = "192.168.1.190";
	private static String puerto = "1527";
	//private String SID = "avspro";
	private static String SID = "acwqa";
	private static String driver = "oracle.jdbc.driver.OracleDriver";
	
	private static String user = "ice_configuring";
	private static String pass = "ice_configuring";
	
	
	public PDFServicesJDBC() {

	}

	// **************************************************************************
	// Principal Caratula TITULO
	// **************************************************************************
	public static TituloVO obtenDataTitulo(String cdUnieco, String cdRamo, String estado, String nmPoliza, String nmsuplem) {

		if (logger.isDebugEnabled()) {
			logger.debug(">>>>>>> Inicia obtenDataTitulo...");
			logger.debug("cdUnieco: " + cdUnieco);
			logger.debug("cdRamo: " + cdRamo);
			logger.debug("estado: " + estado);
			logger.debug("nmpoliza: " + nmPoliza);
			logger.debug("nmsuplem: " + nmsuplem);
		}

		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		int wcdunieco = Integer.parseInt(cdUnieco.trim());
		int wcdramo = Integer.parseInt(cdRamo.trim());
		int wnmpoliza = Integer.parseInt(nmPoliza.trim());
		//long wnmsuplem =  Long.parseLong(nmsuplem.trim());

		// respuesta
		TituloVO titulo = new TituloVO();

		try {
			logger.debug("Antes de obtener la conexion");
			try {
				conn = QuerysBd.getConnection();
			}catch (Exception e){
				logger.debug(e.getMessage());
			}
			logger.debug("antes de llamar al obtener titulo");
			cs = conn.prepareCall("{ call PKG_CARATULAS2.P_Obtiene_Titulo( ?,?,?,?,?,? ) }");

			cs.setInt(1, wcdunieco);
			cs.setInt(2, wcdramo);
			cs.setString(3, estado.trim());
			cs.setInt(4, wnmpoliza);
			cs.setString(5,nmsuplem);
			cs.registerOutParameter(6, OracleTypes.CURSOR);

			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Titulo ::: in :::" + cs.toString());
			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Titulo ::: in :::" + cs.toString());
			cs.execute();
			rs = (ResultSet) cs.getObject(6);
			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Titulo ::: out :::" + rs.toString());

			rs = (ResultSet) cs.getObject(6);

			logger.debug(">>>>>>>antes de rs titulo");
			cursorResultSetMetaData(rs, "PKG_CARATULAS2.P_Obtiene_Titulo");
			if (rs.next()) {
				titulo.setCdRamo(String.valueOf(rs.getInt(1)));
				logger.debug(">> " + titulo.getCdRamo());
				titulo.setDsRamo(rs.getString(2));
				logger.debug(">> " + titulo.getDsRamo());
				titulo.setCdMoneda(String.valueOf(rs.getString(3)));
				logger.debug(">> " + titulo.getCdMoneda());
				titulo.setDsMoneda(rs.getString(4));
				logger.debug(">> " + titulo.getDsMoneda());
				titulo.setNmpoliex(rs.getString(5));
				logger.debug(">> " + titulo.getNmpoliex());

				titulo.setTituloCaratula(rs.getString(6));
				logger.debug(">> " + titulo.getTituloCaratula());

			}
			logger.debug(">>>>>>>despues  de rs titulo");

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} /*catch (ClassNotFoundException e) {

			e.printStackTrace();
		} */catch (SQLException e) {
				e.printStackTrace();
		}	finally {
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
		return titulo;
	}

	// **************************************************************************
	// Principal Caratula: DATA ASEGURADO
	// **************************************************************************
	public static AseguradoVO obtenDataAsegurado(String cdUnieco, String cdRamo, String estado, String nmPoliza,String nmsuplem) {

		if (logger.isDebugEnabled()) {
			logger.debug(">>>>>>> Inicia obtenDataAsegurado.");
		}

		AseguradoVO asegurado = new AseguradoVO();
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		String[] res = new String[9];

		try {
			conn = QuerysBd.getConnection();
			
			/*			try {
							Class.forName(driver);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						conn = DriverManager.getConnection("jdbc:oracle:thin:@" + host
							     + ":" + puerto + ":" + SID, user, pass);
			*/

			int wcdunieco = Integer.parseInt(cdUnieco);
			int wcdramo = Integer.parseInt(cdRamo);
			int wnmpoliza = Integer.parseInt(nmPoliza);
			//long wnmsuplem =  Long.parseLong(nmsuplem.trim());

			// respuesta
			String nombre = "";
			String cdRFC = "";
			String asegSecundario="";
			String direccion = "";
			String telefono = "";
			String benefPref = "";

			logger.debug("wcdunieco=" + wcdunieco);
			logger.debug("wcdramo=" + wcdramo);
			logger.debug("wnmpoliza=" + wnmpoliza);

			cs = conn.prepareCall("{ call PKG_CARATULAS2.P_Obtiene_Datos_Asegurado( ?,?,?,?,?,? ) }");
			cs.setInt(1, wcdunieco);
			cs.setInt(2, wcdramo);
			cs.setString(3, estado.trim());
			cs.setInt(4, wnmpoliza);
			cs.setString(5, nmsuplem);
			cs.registerOutParameter(6, OracleTypes.CURSOR);
			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Datos_Asegurado ::: in :::" + cs.toString());
			cs.execute();
			rs = (ResultSet) cs.getObject(6);
			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Datos_Asegurado ::: out :::" + rs.toString());
			rs = (ResultSet) cs.getObject(6);

			cursorResultSetMetaData(rs, "P_Obtiene_Datos_Asegurado" );
			if (rs.next()) {
				nombre = rs.getString(1) != "" ? rs.getString(1) : " - ";
				logger.debug(" nombre = " + nombre);
				cdRFC = rs.getString(2) != "" ? rs.getString(2) : " - ";
				logger.debug(" cdRFC = " + cdRFC);
				direccion = rs.getString(3) != "" ? rs.getString(3) : " - ";
				logger.debug(" direccion = " + direccion);
				telefono = rs.getString(4) != "" ? rs.getString(4) : " - ";
				if (telefono == null) {
					telefono = "";
				}
				logger.debug(" telefono=" + telefono);
			}else{
				nombre ="";// "sin informacion de DB.";
				logger.debug(" nombre = " + nombre);
				cdRFC ="";//  "sin informacion de DB.";
				logger.debug(" cdRFC = " + cdRFC);
				direccion ="";//  "sin informacion de DB.";
				logger.debug(" direccion = " + direccion);
				telefono ="";// "sin informacion de DB.";				
				logger.debug(" telefono=" + telefono);
			}
			
			//******************************************************************
			//Y/O
			cs = conn.prepareCall("{ call PKG_CARATULAS2.P_Obtiene_Asegurado_YO( ?,?,?,?,?,? ) }");
			cs.setInt(1, wcdunieco);
			cs.setInt(2, wcdramo);
			cs.setString(3, estado);
			cs.setInt(4, wnmpoliza);
			cs.setString(5, nmsuplem);
			cs.registerOutParameter(6, OracleTypes.CURSOR);

			
			logger.debug(" >>>>>>> PKG_CARATULAS2.P_Obtiene_Asegurado_YO ::: in :::" + cs.toString());
			cs.execute();
			rs = (ResultSet) cs.getObject(6);
			
			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Asegurado_YO ::: out :::" + rs.toString());
			
			cursorResultSetMetaData(rs, "PKG_CARATULAS2.P_Obtiene_Asegurado_YO" );
			
			if (rs.next()) {
				
				//asegSecundario = rs.getString(1) != "" ? rs.getString(1) : "-";
				asegSecundario = rs.getString("NOMBRE") != "" ? rs.getString("NOMBRE") : "-";
				
				logger.debug("Y/O = " + asegSecundario);
			}
			
			//******************************************************************
			//--- Muestra el Beneficiario Preferente			
			cs = conn.prepareCall("{ call PKG_CARATULAS2.P_Obtiene_Beneficiario_Pref( ?,?,?,?,?,? ) }");
			cs.setInt(1, wcdunieco);
			cs.setInt(2, wcdramo);
			cs.setString(3, estado);
			cs.setInt(4, wnmpoliza);
			cs.setString(5, nmsuplem);
			cs.registerOutParameter(6, OracleTypes.CURSOR);

			logger.debug(" >>>>>>> PKG_CARATULAS2P_Obtiene_Beneficiario_Pref ::: in :::" + cs.toString());
			cs.execute();
			rs = (ResultSet) cs.getObject(6);

			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Beneficiario_Pref ::: out :::" + rs.toString());
			
			cursorResultSetMetaData(rs, "PKG_CARATULAS2.P_Obtiene_Beneficiario_Pref" );
			
			if (rs.next()) {
				
				benefPref = rs.getString(1) != "" ? rs.getString(1) : "-";
				logger.debug("benefPref = " + benefPref);
			}
			
			//******************************************************************
			asegurado.setNombre(nombre);
			asegurado.setAsegSecundario(asegSecundario);
			asegurado.setDireccion(direccion);
			asegurado.setCdRFC(cdRFC);
			asegurado.setTelefono(telefono);
			asegurado.setBenefPref(benefPref);

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

		return asegurado;
	}

	// **************************************************************************
	// Principal Caratula: DATA VEHICULO
	// **************************************************************************
	public static DataVehiculoVO obtenDataVehiculo(String cdUnieco, String cdRamo, String estado, String nmPoliza,String nmsuplem) {

		if (logger.isDebugEnabled()) {
			logger.debug(">>>>>>> Inicia obtenDataVehiculo.");
		}

		DataVehiculoVO vehiculo_vo = new DataVehiculoVO();
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		String[] res = new String[16];

		try {
			conn = QuerysBd.getConnection();
			
			/*			try {
							Class.forName(driver);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						conn = DriverManager.getConnection("jdbc:oracle:thin:@" + host
							     + ":" + puerto + ":" + SID, user, pass);
			*/
			int poliza = 0;
			int wcdunieco = Integer.parseInt(cdUnieco);
			int wcdramo = Integer.parseInt(cdRamo);
			int wnmpoliza = Integer.parseInt(nmPoliza);
			//long wnmsuplem =  Long.parseLong(nmsuplem.trim());
			// respuesta


			// P_Obtiene_Datos_Riesgo
			cs = conn.prepareCall("{ call PKG_CARATULAS2.P_Obtiene_Datos_Riesgo( ?,?,?,?,?,? ) }");
			cs.setInt(1, wcdunieco);
			cs.setInt(2, wcdramo);
			cs.setString(3, estado);
			cs.setInt(4, wnmpoliza);
			cs.setString(5, nmsuplem);
			cs.registerOutParameter(6, OracleTypes.CURSOR);

			logger.debug("2 >>>>>>> PKG_CARATULAS2.P_Obtiene_Datos_Riesgo ::: in :::" + cs.toString());
			cs.execute();
			rs = (ResultSet) cs.getObject(6);
			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Datos_Riesgo ::: out :::" + rs.toString());

			/*
			 * d.cdatribu --- Codigo de Atributo, d.dsatribu --- Descripción de
			 * Atributo, d.ottabval --- Lista de valores asociada al campo,
			 * e.otvalor --- Valor del Atributo, Valor --- Descripcion desde una
			 * Lista de valores de una Clave
			 */

			cursorResultSetMetaData(rs, "PKG_CARATULAS2.P_Obtiene_Datos_Riesgo" );
			int con = 0;
			String marca = "";
			String marcaDs = "";
			while (rs.next()) {
				logger.debug("rs tiene datos ");
				if (con == 0) {					
					marca = rs.getString(5) != "" ? rs.getString(5) : "";
					logger.debug("marca = " + marca);
				}
				if (con == 1) {
					vehiculo_vo.setModelo(rs.getString(5) != "" ? rs.getString(5) : "");
					logger.debug("modelo = " + vehiculo_vo.getModelo());
				}
				if (con == 2) {
					vehiculo_vo.setDescripcion(rs.getString(5) != "" ? rs.getString(5) : "");
					logger.debug("descripcion = " + vehiculo_vo.getVehiculo());
				}
				if (con == 3) {
					// vehiculo_vo.setVehiculo(rs.getString(5) != "" ?
					// rs.getString(5) : " - ");					
				}
				if (con == 4) {
					vehiculo_vo.setSerie(rs.getString(5) != "" ? rs.getString(5) : "");
					logger.debug("serie = " + vehiculo_vo.getSerie());
				}
				if (con == 5) {
					vehiculo_vo.setMotor(rs.getString(5) != "" ? rs.getString(5) : "");
					logger.debug("motor = " + vehiculo_vo.getMotor());
				}
				if (con == 6) {
					vehiculo_vo.setPlacas(rs.getString(5) != "" ? rs.getString(5) : "");
					logger.debug("placas = " + vehiculo_vo.getPlacas());
				}
				if (con == 9) {
					vehiculo_vo.setUso(rs.getString(5) != "" ? rs.getString(5) : "");
					logger.debug("uso = " + vehiculo_vo.getUso());
				}
				if (con == 10) {
					vehiculo_vo.setServicio(rs.getString(5) != "" ? rs.getString(5) : "");
					logger.debug("servicio = " + vehiculo_vo.getServicio());
				}
				
				con++;
			}
			vehiculo_vo.setVehiculo(marca + " "+ vehiculo_vo.getDescripcion());

			logger.debug("1 PKG_CARATULAS2.P_Obtiene_Capacidad( ?,?,?,?,?,? )");
			cs = conn.prepareCall("{ call PKG_CARATULAS2.P_Obtiene_Capacidad( ?,?,?,?,?,? ) }");
			cs.setInt(1, wcdunieco);
			cs.setInt(2, wcdramo);
			cs.setString(3, estado);
			cs.setInt(4, wnmpoliza);
			cs.setString(5, nmsuplem);
			cs.registerOutParameter(6, OracleTypes.CURSOR);

			logger.debug("2 >>>>>>> PKG_CARATULAS2.P_Obtiene_Capacidad ::: in :::" + cs.toString());
			cs.execute();
			rs = (ResultSet) cs.getObject(6);
			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Capacidad ::: out :::" + rs.toString());

			logger.debug(">>>>>>> antes de rs vehiculo");
			while (rs.next()) {
				vehiculo_vo.setCapacidad(rs.getString(1) != "" ? rs.getString(1) : " - ");
				logger.debug("CAPACIDAD =" + vehiculo_vo.getCapacidad());
			}
			// CARGA
			logger.debug("1 PKG_CARATULAS2.P_Obtiene_Carga( ?,?,?,?,?,? )");
			cs = conn.prepareCall("{ call PKG_CARATULAS2.P_Obtiene_Carga( ?,?,?,?,?,? ) }");
			cs.setInt(1, wcdunieco);
			cs.setInt(2, wcdramo);
			cs.setString(3, estado);
			cs.setInt(4, wnmpoliza);
			cs.setString(5, nmsuplem);
			cs.registerOutParameter(6, OracleTypes.CURSOR);
			
			logger.debug("2 >>>>>>> PKG_CARATULAS2.P_Obtiene_Carga ::: in :::" + cs.toString());
			cs.execute();
			rs = (ResultSet) cs.getObject(6);		
			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Carga ::: out :::" + rs.toString());

			logger.debug(">>>>>>> antes de rs vehiculo");
			while (rs.next()) {
				vehiculo_vo.setCarga(rs.getString(1) != "" ? rs.getString(1) : "");
				logger.debug("CARGA =" + vehiculo_vo.getCarga());
			}

			// REMOLQUE

			logger.debug("1 PKG_CARATULAS2.P_Obtiene_Remolque ( ?,?,?,?,?,? )");
			cs = conn.prepareCall("{ call PKG_CARATULAS2.P_Obtiene_Remolque ( ?,?,?,?,?,? ) }");
			cs.setInt(1, wcdunieco);
			cs.setInt(2, wcdramo);
			cs.setString(3, estado);
			cs.setInt(4, wnmpoliza);
			cs.setString(5, nmsuplem);
			cs.registerOutParameter(6, OracleTypes.CURSOR);

			logger.debug("2 >>>>>>> PKG_CARATULAS2.P_Obtiene_Remolque  ::: in :::" + cs.toString());
			cs.execute();
			rs = (ResultSet) cs.getObject(6);		
			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Remolque  ::: out :::" + rs.toString());

			logger.debug(">>>>>>> antes de rs vehiculo");
			while (rs.next()) {
				vehiculo_vo.setRemolque(rs.getString(1) != "" ? rs.getString(1) : "");
				logger.debug("REMOLQUE =" + vehiculo_vo.getRemolque());
			}
			// 2o REMOLQUE

			logger.debug("1 PKG_CARATULAS2.P_Obtiene_2DoRemolque( ?,?,?,?,?,? )");
			cs = conn.prepareCall("{ call PKG_CARATULAS2.P_Obtiene_2DoRemolque( ?,?,?,?,?,? ) }");
			cs.setInt(1, wcdunieco);
			cs.setInt(2, wcdramo);
			cs.setString(3, estado);
			cs.setInt(4, wnmpoliza);
			cs.setString(5, nmsuplem);
			cs.registerOutParameter(6, OracleTypes.CURSOR);


			logger.debug("2 >>>>>>> PKG_CARATULAS2.P_Obtiene_2DoRemolque  ::: in :::" + cs.toString());
			cs.execute();
			rs = (ResultSet) cs.getObject(6);

			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_2DoRemolque  ::: out :::" + rs.toString());

			while (rs.next()) {
				vehiculo_vo.setSegundoRemol(rs.getString(1) != "" ? rs.getString(1) : "");
				logger.debug("2do REMOLQUE =" + vehiculo_vo.getSegundoRemol());
			}

			// TARIFA

			logger.debug("1 PKG_CARATULAS2.P_Obtiene_Tarifa ( ?,?,?,?,?,? )");
			cs = conn.prepareCall("{ call PKG_CARATULAS2.P_Obtiene_Tarifa ( ?,?,?,?,?,? ) }");
			cs.setInt(1, wcdunieco);
			cs.setInt(2, wcdramo);
			cs.setString(3, estado);
			cs.setInt(4, wnmpoliza);
			cs.setString(5, nmsuplem);
			cs.registerOutParameter(6, OracleTypes.CURSOR);

			
			logger.debug("2 >>>>>>> PKG_CARATULAS2.P_Obtiene_Tarifa  ::: in :::" + cs.toString());
			cs.execute();
			rs = (ResultSet) cs.getObject(6);			
			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Tarifa  ::: out :::" + rs.toString());

			logger.debug(">>>>>>> antes de rs vehiculo");
			while (rs.next()) {
				vehiculo_vo.setTarifa(rs.getString(1) != "" ? rs.getString(1) : "");
				logger.debug("TARIFA =" + vehiculo_vo.getTarifa());
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

		return vehiculo_vo;
	}

	// **************************************************************************
	// Principal Caratula: DATA POLIZA firma ok, sin datos retorno
	// **************************************************************************
	public static DataPolizaVO obtenDataPoliza(String cdUnieco, String cdRamo, String estado, String nmPoliza,String nmsuplem) {

		if (logger.isDebugEnabled()) {
			logger.debug(">>>>>>> HHH Inicia obtenDataPoliza.");
			logger.debug("cdUnieco: " + cdUnieco);
			logger.debug("cdRamo: " + cdRamo);
			logger.debug("estado: " + estado);
			logger.debug("nmpoliza: " + nmPoliza);
		}

		DataPolizaVO poliza_vo = new DataPolizaVO();

		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		String[] res = new String[10];

		try {
			conn = QuerysBd.getConnection();
			
			/*			try {
							Class.forName(driver);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						conn = DriverManager.getConnection("jdbc:oracle:thin:@" + host
							     + ":" + puerto + ":" + SID, user, pass);
			*/

			int poliza = 0;
			int wcdunieco = Integer.parseInt(cdUnieco);
			int wcdramo = Integer.parseInt(cdRamo);
			int wnmpoliza = Integer.parseInt(nmPoliza);
			//long wnmsuplem =  Long.parseLong(nmsuplem.trim());

			// respuesta
			String nombre = "";
			String cdRFC = "";
			String direccion = "";
			String telefono = "";

			logger.debug("wcdunieco= " + wcdunieco);
			logger.debug("wcdramo= " + wcdramo);
			logger.debug("wnmpoliza= " + wnmpoliza);

			logger.debug("PKG_CARATULAS2.P_Obtiene_Datos_Poliza( ");
			cs = conn.prepareCall("{ call PKG_CARATULAS2.P_Obtiene_Datos_Poliza( ?,?,?,?,?,? ) }");
			cs.setInt(1, wcdunieco);
			cs.setInt(2, wcdramo);
			cs.setString(3, estado);
			cs.setInt(4, wnmpoliza);
			cs.setString(5, nmsuplem);
			cs.registerOutParameter(6, OracleTypes.CURSOR);
			logger.debug("1 PKG_CARATULAS2.P_Obtiene_Datos_Poliza ::: in :::" + cs.toString());
			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Datos_Poliza ::: in :::" + cs.toString());
			cs.execute();
			rs = (ResultSet) cs.getObject(6);
			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Datos_Poliza ::: out :::" + rs.toString());
			logger.debug("2 PKG_CARATULAS2.P_Obtiene_Datos_Poliza ::: in :::" + rs.toString());
			// /rs = (ResultSet) cs.getObject(5);

			/*
			 * regresa : 1 CodigoPeriodoPago, 2 PeriodoPago, (es forma de pago)
			 * 3 FechaInicio, 4 HoraInicio, 5 FechaFin, 6 HoraFin, 7
			 * FechaEmsion, 8 PolizAnterior
			 */
			logger.debug("antes de obtiene datos de poliza");
			if (rs.next()) {

				poliza_vo.setFormaPago(rs.getString(2) != "" ? rs.getString(2) : " - ");// ok
				logger.debug("poliza_vo =" + poliza_vo.getFormaPago());
				poliza_vo.setVigencia_del(rs.getString(3) != "" ? rs.getString(3) : " - ");// ok
				logger.debug("poliza_vo =" + poliza_vo.getVigencia_al());
				poliza_vo.setVigencia_al(rs.getString(5) != "" ? rs.getString(5) : " - ");// ok
				logger.debug("poliza_vo =" + poliza_vo.getVigencia_al());
				poliza_vo.setFechaEmision(rs.getString(7) != "" ? rs.getString(7) : " - ");
				logger.debug("poliza_vo =" + poliza_vo.getFechaEmision());
				poliza_vo.setPolizaAnt(rs.getString(8) != "" ? rs.getString(8) : " - ");
				logger.debug("poliza_vo =" + poliza_vo.getPolizaAnt());
				// falta el endoso
				poliza_vo.setEndoso(rs.getString(9) != "" ? rs.getString(9) : " - ");
				logger.debug("poliza_vo =" + poliza_vo.getEndoso());
			}
			logger.debug("despues de obtiene datos de poliza");

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

		return poliza_vo;
	}

	// **************************************************************************
	// Principal Caratula: DATA ADICIONAL AGENTE
	// **************************************************************************
	public static DataAdicionalVO obtenDataAdiAgente(String cdUnieco, String cdRamo, String estado, String nmPoliza,String nmsuplem) {

		if (logger.isDebugEnabled()) {
			logger.debug(">>>>>>> Inicia obtenDataAdiAgente.");
			logger.debug("cdUnieco: " + cdUnieco);
			logger.debug("cdRamo: " + cdRamo);
			logger.debug("estado: " + estado);
			logger.debug("nmpoliza: " + nmPoliza);
		}

		DataAdicionalVO dataAdicional_vo = new DataAdicionalVO();

		NumberFormat decimalsFormat = NumberFormat.getNumberInstance();
		decimalsFormat.setMaximumFractionDigits(2);

		// String double
		// valorPDF = decimalsFormat.format(valorDouble);

		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		String[] res = new String[12];

		try {

			conn = QuerysBd.getConnection();
			
			/*			try {
							Class.forName(driver);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						conn = DriverManager.getConnection("jdbc:oracle:thin:@" + host
							     + ":" + puerto + ":" + SID, user, pass);
			*/
			// parametros de entrada
			int pi_wcdunieco = Integer.parseInt(cdUnieco);
			int pi_wcdramo = Integer.parseInt(cdRamo);
			int pi_wnmpoliza = Integer.parseInt(nmPoliza);
			//long wnmsuplem =  Long.parseLong(nmsuplem.trim());

			// parametros de salida
			String agente = "";
			String ordenTrabajo = "";
			String contrato = "";
			String otAgente = "";
			double primaNeta = 0.00;
			double tasaFinanciamiento = 0.00;
			double gastosExpedicion = 0.00;
			double iva = 0.00;
			double primaTotal = 0.00;

			logger.debug(" PKG_CARATULAS2.P_Obtiene_Datos_Adicional_Age( ?,?,?,?,?,? )");
			cs = conn.prepareCall("{ call PKG_CARATULAS2.P_Obtiene_Datos_Adicional_Age( ?,?,?,?,?,? ) }");
			cs.setInt(1, pi_wcdunieco);
			cs.setInt(2, pi_wcdramo);
			cs.setString(3, estado);
			cs.setInt(4, pi_wnmpoliza);
			cs.setString(5, nmsuplem);
			cs.registerOutParameter(6, OracleTypes.CURSOR);

			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Datos_Adicional_Age ::: in :::" + cs.toString());
			cs.execute();
			rs = (ResultSet) cs.getObject(6);
			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Datos_Adicional_Age ::: out :::" + rs.toString());

			// rs = (ResultSet) cs.getObject(5);
			logger.debug(" antes de datos adicionales");
			String tmp = "";
			if (rs.next()) {
				tmp = rs.getString(1) != "" ? rs.getString(1) : " - ";
				tmp = tmp + rs.getString(2) != "" ? rs.getString(2) : " - ";
				dataAdicional_vo.setAgente(tmp);
				logger.debug(" dataAdicional_vo.setAgente =" + dataAdicional_vo.getAgente());

			}

			// ---------------------------------------------------------------------------------------------

			cs = conn.prepareCall("{ call PKG_CARATULAS2.P_Obtiene_Orden_Trabajo( ?,?,?,?,?,? ) }");
			cs.setInt(1, pi_wcdunieco);
			cs.setInt(2, pi_wcdramo);
			cs.setString(3, estado.trim());
			cs.setInt(4, pi_wnmpoliza);
			cs.setInt(5, 12);

			cs.registerOutParameter(6, OracleTypes.CURSOR);

			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Orden_Trabajo ::: in :::" + cs.toString());
			cs.execute();
			// rs = (ResultSet) cs.getObject(5);
			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Orden_Trabajo ::: out :::" + rs.toString());

			rs = (ResultSet) cs.getObject(6);
			logger.debug("antes de ciclo orden de trabajo");
			if (rs.next()) {

				dataAdicional_vo.setOrdenTrabajo(rs.getString(1) != "" ? rs.getString(1) : " - ");
				logger.debug("despues de " + dataAdicional_vo.getOrdenTrabajo());
				dataAdicional_vo.setContrato(rs.getString(2) != "" ? rs.getString(2) : " - ");
				logger.debug("contrato de " + dataAdicional_vo.getContrato());
				dataAdicional_vo.setOtAgente(rs.getString(3) != "" ? rs.getString(3) : " - ");
				logger.debug("setOtAgente " + dataAdicional_vo.getOtAgente());
			}

			// ---------------------------------------------------------------------------------------------
			cs = conn.prepareCall("{ call PKG_CARATULAS2.P_Obtiene_Concept_Prima_Poliza( ?,?,?,?,?,? ) }");
			cs.setInt(1, pi_wcdunieco);
			cs.setInt(2, pi_wcdramo);
			cs.setString(3, estado.trim());
			cs.setInt(4, pi_wnmpoliza);
			cs.setString(5, nmsuplem);
			cs.registerOutParameter(6, OracleTypes.CURSOR);

			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Concept_Prima_Poliza ::: in :::" + cs.toString());
			cs.execute();
			rs = (ResultSet) cs.getObject(6);
			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Concept_Prima_Poliza ::: out :::" + rs.toString());

			rs = (ResultSet) cs.getObject(6);

			if (rs.next()) {
				dataAdicional_vo.setPrimaNeta(rs.getString(1));
				dataAdicional_vo.setTasaFinan(rs.getString(2));
				dataAdicional_vo.setGastosExp(rs.getString(3));
				dataAdicional_vo.setIva(rs.getString(4));
				dataAdicional_vo.setPrimaTotal(rs.getString(5));
			}else{
				
				dataAdicional_vo.setPrimaNeta("0.00");
				dataAdicional_vo.setTasaFinan("0.00");
				dataAdicional_vo.setGastosExp("0.00");
				dataAdicional_vo.setIva("0.00");
				dataAdicional_vo.setPrimaTotal("0.00");
				
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

		return dataAdicional_vo;
	}
	
	
	// **************************************************************************
	// Principal Caratula: DATA ADICIONAL AGENTE PARA ENDOSO
	// **************************************************************************
	public static DataAdicionalVO obtenDataAdiAgenteEndosos(String cdUnieco, String cdRamo, String estado, String nmPoliza,String nmsuplem) {

		if (logger.isDebugEnabled()) {
			logger.debug(">>>>>>> Inicia obtenDataAdiAgente.");
			logger.debug("cdUnieco: " + cdUnieco);
			logger.debug("cdRamo: " + cdRamo);
			logger.debug("estado: " + estado);
			logger.debug("nmpoliza: " + nmPoliza);
		}

		DataAdicionalVO dataAdicional_vo = new DataAdicionalVO();

		NumberFormat decimalsFormat = NumberFormat.getNumberInstance();
		decimalsFormat.setMaximumFractionDigits(2);

		// String double
		// valorPDF = decimalsFormat.format(valorDouble);

		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		String[] res = new String[12];

		try {

			conn = QuerysBd.getConnection();
			
			/*			try {
							Class.forName(driver);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						conn = DriverManager.getConnection("jdbc:oracle:thin:@" + host
							     + ":" + puerto + ":" + SID, user, pass);
			*/
			// parametros de entrada
			int pi_wcdunieco = Integer.parseInt(cdUnieco);
			int pi_wcdramo = Integer.parseInt(cdRamo);
			int pi_wnmpoliza = Integer.parseInt(nmPoliza);
			//long wnmsuplem =  Long.parseLong(nmsuplem.trim());

			// parametros de salida
			String agente = "";
			String ordenTrabajo = "";
			String contrato = "";
			String otAgente = "";
			double primaNeta = 0.00;
			double tasaFinanciamiento = 0.00;
			double gastosExpedicion = 0.00;
			double iva = 0.00;
			double primaTotal = 0.00;

			logger.debug(" PKG_CARATULAS2.P_Obtiene_Datos_Adicional_Age( ?,?,?,?,?,? )");
			cs = conn.prepareCall("{ call PKG_CARATULAS2.P_Obtiene_Datos_Adicional_Age( ?,?,?,?,?,? ) }");
			cs.setInt(1, pi_wcdunieco);
			cs.setInt(2, pi_wcdramo);
			cs.setString(3, estado);
			cs.setInt(4, pi_wnmpoliza);
			cs.setString(5, nmsuplem);
			cs.registerOutParameter(6, OracleTypes.CURSOR);

			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Datos_Adicional_Age ::: in :::" + cs.toString());
			cs.execute();
			rs = (ResultSet) cs.getObject(6);
			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Datos_Adicional_Age ::: out :::" + rs.toString());

			// rs = (ResultSet) cs.getObject(5);
			logger.debug(" antes de datos adicionales");
			String tmp = "";
			if (rs.next()) {
				tmp = rs.getString(1) != "" ? rs.getString(1) : " - ";
				tmp = tmp + rs.getString(2) != "" ? rs.getString(2) : " - ";
				dataAdicional_vo.setAgente(tmp);
				logger.debug(" dataAdicional_vo.setAgente =" + dataAdicional_vo.getAgente());

			}

			// ---------------------------------------------------------------------------------------------

			cs = conn.prepareCall("{ call PKG_CARATULAS2.P_Obtiene_Orden_Trabajo( ?,?,?,?,?,? ) }");
			cs.setInt(1, pi_wcdunieco);
			cs.setInt(2, pi_wcdramo);
			cs.setString(3, estado.trim());
			cs.setInt(4, pi_wnmpoliza);
			cs.setInt(5, 12);

			cs.registerOutParameter(6, OracleTypes.CURSOR);

			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Orden_Trabajo ::: in :::" + cs.toString());
			cs.execute();
			// rs = (ResultSet) cs.getObject(5);
			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Orden_Trabajo ::: out :::" + rs.toString());

			rs = (ResultSet) cs.getObject(6);
			logger.debug("antes de ciclo orden de trabajo");
			if (rs.next()) {

				dataAdicional_vo.setOrdenTrabajo(rs.getString(1) != "" ? rs.getString(1) : " - ");
				logger.debug("despues de " + dataAdicional_vo.getOrdenTrabajo());
				dataAdicional_vo.setContrato(rs.getString(2) != "" ? rs.getString(2) : " - ");
				logger.debug("contrato de " + dataAdicional_vo.getContrato());
				dataAdicional_vo.setOtAgente(rs.getString(3) != "" ? rs.getString(3) : " - ");
				logger.debug("setOtAgente " + dataAdicional_vo.getOtAgente());
			}

			// ---------------------------------------------------------------------------------------------
			cs = conn.prepareCall("{ call PKG_CARATULAS2.P_Obt_Concept_Prima_Poliza_end( ?,?,?,?,?,? ) }");
			cs.setInt(1, pi_wcdunieco);
			cs.setInt(2, pi_wcdramo);
			cs.setString(3, estado.trim());
			cs.setInt(4, pi_wnmpoliza);
			cs.setString(5, nmsuplem);
			cs.registerOutParameter(6, OracleTypes.CURSOR);

			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obt_Concept_Prima_Poliza_end ::: in :::" + cs.toString());
			cs.execute();
			rs = (ResultSet) cs.getObject(6);
			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obt_Concept_Prima_Poliza_end ::: out :::" + rs.toString());

			rs = (ResultSet) cs.getObject(6);

			if (rs.next()) {
				dataAdicional_vo.setPrimaNeta(rs.getString(1));
				dataAdicional_vo.setTasaFinan(rs.getString(2));
				dataAdicional_vo.setGastosExp(rs.getString(3));
				dataAdicional_vo.setIva(rs.getString(4));
				dataAdicional_vo.setPrimaTotal(rs.getString(5));
			}else{
				
				dataAdicional_vo.setPrimaNeta("0.00");
				dataAdicional_vo.setTasaFinan("0.00");
				dataAdicional_vo.setGastosExp("0.00");
				dataAdicional_vo.setIva("0.00");
				dataAdicional_vo.setPrimaTotal("0.00");
				
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

		return dataAdicional_vo;
	}

	// **************************************************************************
	// Principal Caratula: DATA COBERTURAS
	// **************************************************************************
	public static ArrayList<HashMap<String, String>> obtenDataCoberturas(String cdUnieco, String cdRamo, String estado,
			String nmPoliza,String nmsuplem) {

		if (logger.isDebugEnabled()) {
			logger.debug(">>>>>>> Inicia obtenDataCoberturas.");
		}

		NumberFormat decimalsFormat = NumberFormat.getNumberInstance();
	    decimalsFormat.setMaximumFractionDigits(2);
	    
		DataCoberturasVO coberturas_vo = null;

		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		ArrayList<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> mapa = null;
		String valorSumaString = "";
		String[] res = new String[9];

		try {
			// Se realiza la conexión
			conn = QuerysBd.getConnection();
			
			/*			try {
							Class.forName(driver);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						conn = DriverManager.getConnection("jdbc:oracle:thin:@" + host
							     + ":" + puerto + ":" + SID, user, pass);
			*/

			int wcdunieco = Integer.parseInt(cdUnieco);
			int wcdramo = Integer.parseInt(cdRamo);
			int wnmpoliza = Integer.parseInt(nmPoliza);
			//long wnmsuplem =  Long.parseLong(nmsuplem.trim());

			logger.debug("wcdunieco =" + wcdunieco);
			logger.debug("wcdramo =" + wcdramo);
			logger.debug("wnmpoliza =" + wnmpoliza);
			logger.debug("ESTADO =" + estado);

			cs = conn.prepareCall("{ call PKG_CARATULAS2.P_Obtiene_Coberturas( ?,?,?,?,?,? ) }");
			cs.setInt(1, wcdunieco);
			cs.setInt(2, wcdramo);
			cs.setString(3, estado);
			cs.setInt(4, wnmpoliza);
			cs.setString(5, nmsuplem);
			cs.registerOutParameter(6, OracleTypes.CURSOR);

			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Coberturas ::: in :::" + cs.toString());
			cs.execute();
			rs = (ResultSet) cs.getObject(6);
			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Coberturas ::: out :::" + rs.toString());
			
			cursorResultSetMetaData(rs, "PKG_CARATULAS2.P_Obtiene_Coberturas" );
			
			double sumaPrima = 0.0;
			while (rs.next()) {

				mapa = new HashMap();

				mapa.put("cobertura", rs.getString(1));
				logger.debug("cobertura ::: " + rs.getString(1));
				
				mapa.put("lmaxRespon", rs.getString(2));
				logger.debug("lmaxRespon ::: " + rs.getString(2));
				
				mapa.put("deducible", rs.getString(3));
				logger.debug("deducible ::: " + rs.getString(3));
				
				mapa.put("prima", rs.getString(4));
				logger.debug("prima ::: " + rs.getString(4));

				sumaPrima = sumaPrima + rs.getDouble(5);

				lista.add(mapa);
			}
			logger.debug("despues  de prima");
			valorSumaString = decimalsFormat.format(sumaPrima);
			mapa = new HashMap();
			mapa.put("cobertura", "");
			mapa.put("lmaxRespon", "");
			mapa.put("deducible", "PRIMA NETA");
			mapa.put("prima", valorSumaString);

			lista.add(mapa);			
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
		return lista;
	}

	// **************************************************************************
	// Principal Caratula: DATA ORDEN DE TRABAJO
	// **************************************************************************
	public static String[] obtenDataOrdenTrabajo(String cdUnieco, String cdRamo, String estado, String nmPoliza, String cdProceso) {

		if (logger.isDebugEnabled()) {
			logger.debug(">>>>>>> Inicia obtenDataOrdenTrabajo.");
		}

		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		String[] res = new String[10];

		try {
			// Se realiza la conexión
			conn = QuerysBd.getConnection();

			int poliza = 0;
			int wcdunieco = Integer.parseInt(cdUnieco);
			int wcdramo = Integer.parseInt(cdRamo);
			int wnmpoliza = Integer.parseInt(nmPoliza);

			// respuesta
			String nombre = "";
			String cdRFC = "";
			String direccion = "";
			String telefono = "";

			cs = conn.prepareCall("{ call PKG_CARATULAS2.P_Obtiene_Orden_Trabajo( ?,?,?,?,? ) }");
			cs.setInt(1, wcdunieco);
			cs.setInt(2, wcdramo);
			cs.setString(3, estado.trim());
			cs.setInt(4, wnmpoliza);
			cs.registerOutParameter(5, OracleTypes.CURSOR);

			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Orden_Trabajo ::: in :::" + cs.toString());
			cs.execute();
			rs = (ResultSet) cs.getObject(5);
			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Orden_Trabajo ::: out :::" + rs.toString());

			rs = (ResultSet) cs.getObject(5);

			while (rs.next()) {
				nombre = rs.getString(1);
				cdRFC = rs.getString(2);
				direccion = rs.getString(3);
				telefono = rs.getString(4);
			}

			res[0] = nombre;
			res[1] = "";
			res[2] = direccion;
			res[3] = cdRFC;
			res[4] = telefono;
			res[5] = "";
			res[6] = "";
			res[7] = "";
			res[8] = "";
			res[9] = "";

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				rs.close();
				cs.close();
				conn.close();
			} catch (SQLException sql) {
				sql.printStackTrace();
			}
		}
		return res;
	}

	public static DataAdicionalVO conceptoRecibo(String cdUnieco, String cdRamo, String estado, String nmPoliza) {

		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		String montoCadena = "";
		DataAdicionalVO dataAdicional_vo = new DataAdicionalVO();
		try {

			// Se realiza la conexión
			 conn = QuerysBd.getConnection();

			int wcdunieco = Integer.parseInt(cdUnieco);
			int wcdramo = Integer.parseInt(cdRamo);
			int wnmpoliza = Integer.parseInt(nmPoliza);

			cs = conn.prepareCall("{ call PKG_CARATULAS2.P_Obtiene_Concept_Prima_Recibo( ?,?,?,?,? ) }");
			cs.setInt(1, wcdunieco);
			cs.setInt(2, wcdramo);
			cs.setString(3, estado.trim());
			cs.setInt(4, wnmpoliza);
			cs.registerOutParameter(5, OracleTypes.CURSOR);

			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Concept_Prima_Poliza ::: in :::" + cs.toString());
			cs.execute();
			rs = (ResultSet) cs.getObject(5);
			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Concept_Prima_Poliza ::: out :::" + rs.toString());

			rs = (ResultSet) cs.getObject(5);

			if (rs.next()) {
				dataAdicional_vo.setPrimaNeta(rs.getString(1));
				dataAdicional_vo.setTasaFinan(rs.getString(2));
				dataAdicional_vo.setGastosExp(rs.getString(3));
				dataAdicional_vo.setIva(rs.getString(4));
				dataAdicional_vo.setPrimaTotal(rs.getString(5));
				dataAdicional_vo.setPrimaTotalCalculo(rs.getString(6));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				rs.close();
				cs.close();
				conn.close();
			} catch (SQLException sql) {
				sql.printStackTrace();
			}
		}
		return dataAdicional_vo;
	}

	public static String montoEscrito(double monto,String moneda) {

		// P_Obtiene_Monto_Escrito
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		String montoCadena = "";
		try {
			// Se realiza la conexión
			 conn = QuerysBd.getConnection();
	
			logger.debug("monto="+monto);
			cs = conn.prepareCall("{ call PKG_CARATULAS2.P_Obtiene_Monto_Escrito( ?,?,? ) }");
			cs.setString(1, moneda);
			cs.setDouble(2, monto);
			logger.debug("moneda="+moneda);
			cs.registerOutParameter(3, OracleTypes.CURSOR);

			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Monto_Escrito ::: in :::" + cs.toString());
			cs.execute();
			rs = (ResultSet) cs.getObject(3);
			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Monto_Escrito ::: out :::" + rs.toString());

			while (rs.next()) {
				montoCadena = rs.getString(1);
			}

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				rs.close();
				cs.close();
				conn.close();
			} catch (SQLException sql) {
				sql.printStackTrace();
			}

		}

		return montoCadena;

	}

	/*
	 * P_Obtiene_Datos_Liquidacion (wcdunieco in mpolizas.cdunieco%type ,
	 * wcdramo in mpolizas.cdramo%type , westado in mpolizas.estado%type ,
	 * wnmpoliza in mpolizas.nmpoliza%type , cur_datos out cursor_datos) Is
	 * 
	 */

	public static DatoLiquidacionVO datoLiquidacion(String cdUnieco, String cdRamo, String estado, 
													String nmPoliza, String nmRecibo, String serieLiq,
													String cdMoneda) {

		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		
		DatoLiquidacionVO datoLiquidacionVO = new DatoLiquidacionVO();
		try {

			// Se realiza la conexión
			conn = QuerysBd.getConnection();
			
			int wcdunieco = Integer.parseInt(cdUnieco);
			int wcdramo = Integer.parseInt(cdRamo);
			int wnmpoliza = Integer.parseInt(nmPoliza);						
			int wnmrecibo = Integer.parseInt(nmRecibo);
			double primaTotal = 0.0;

			cs = conn.prepareCall("{ call PKG_CARATULAS2.P_Obtiene_Datos_Liquidacion( ?,?,?,?,?,? ) }");
			cs.setInt(1, wcdunieco);
			cs.setInt(2, wcdramo);
			cs.setString(3, estado.trim());
			cs.setInt(4, wnmpoliza);
			cs.setInt(5, wnmrecibo);
			cs.registerOutParameter(6, OracleTypes.CURSOR);

			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Datos_Liquidacion ::: in :::" + cs.toString());
			cs.execute();
			rs = (ResultSet) cs.getObject(6);
			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Datos_Liquidacion ::: out :::" + rs.toString());

			rs = (ResultSet) cs.getObject(6);

			cursorResultSetMetaData(rs, "PKG_CARATULAS2.P_Obtiene_Datos_Liquidacion");
			if (rs.next()) {
				datoLiquidacionVO.setFolioFiscal(rs.getString(1));
				datoLiquidacionVO.setFolio(rs.getString(2));
				datoLiquidacionVO.setPeriodo(rs.getString(3));
				datoLiquidacionVO.setSerieLiquidacion(rs.getString(4));
				datoLiquidacionVO.setFechaLimitePago(rs.getString(5));
				
				logger.debug(">>>>>>> FolioFiscal ::: " + datoLiquidacionVO.getFolioFiscal());
				logger.debug(">>>>>>> Folio ::: " + datoLiquidacionVO.getFolio());
				logger.debug(">>>>>>> Periodo :::" + datoLiquidacionVO.getPeriodo());
				logger.debug(">>>>>>> SerieLiquidacion ::: " + datoLiquidacionVO.getSerieLiquidacion());
				logger.debug(">>>>>>> setFechaLimitePago ::: " + datoLiquidacionVO.getFechaLimitePago());
			}
			// CONCEPTO PRIMA
			logger.debug("PKG_CARATULAS2.P_Obtiene_Concept_Prima_Recibo( ?,?,?,?,?,? )");
			cs = conn.prepareCall("{ call PKG_CARATULAS2.P_Obtiene_Concept_Prima_Recibo( ?,?,?,?,?,? ) }");
			cs.setInt(1, wcdunieco);
			cs.setInt(2, wcdramo);
			cs.setString(3, estado);
			cs.setInt(4, wnmpoliza);
			cs.setInt(5, wnmrecibo);
			cs.registerOutParameter(6, OracleTypes.CURSOR);

			logger.debug("2 >>>>>>> PKG_CARATULAS2.P_Obtiene_Concept_Prima_Recibo ::: in :::" + cs.toString());
			cs.execute();
			rs = (ResultSet) cs.getObject(6);
			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Concept_Prima_Recibo ::: out :::" + rs.toString());
			
			cursorResultSetMetaData(rs, "PKG_CARATULAS2.P_Obtiene_Concept_Prima_Recibo");

			if(rs.next()) {
				datoLiquidacionVO.setPrimaNeta(rs.getString("PRIMANETA")!= "" ? rs.getString("PRIMANETA") : "");
				datoLiquidacionVO.setTasaFinanciamiento(rs.getString("TASAFINANCIAMIENTO")!= "" ? rs.getString("TASAFINANCIAMIENTO") : "");
				datoLiquidacionVO.setGastosExpedicion(rs.getString("GASTOEXPEDICION")!= "" ? rs.getString("GASTOEXPEDICION") : "");
				datoLiquidacionVO.setIva(rs.getString("IVA")!= "" ? rs.getString("IVA") : "");
				datoLiquidacionVO.setTotalPagar(rs.getString("PRIMATOTAL")!= "" ? rs.getString("PRIMATOTAL") : "");
				datoLiquidacionVO.setPrimaTotalNum(rs.getString("PRIMATOTALNUM")!= "" ? rs.getString("PRIMATOTALNUM") : "");
			}
			
			// PRIMA TOTAL EN LETRA
			primaTotal = Double.parseDouble(datoLiquidacionVO.getPrimaTotalNum());
			logger.debug("PKG_CARATULAS2.P_Obtiene_Monto_Escrito( ?,?,? )");
			cs = conn.prepareCall("{ call PKG_CARATULAS2.P_Obtiene_Monto_Escrito( ?,?,? ) }");
			cs.setString(1, cdMoneda);
			cs.setDouble(2, primaTotal);			
			cs.registerOutParameter(3, OracleTypes.CURSOR);
			
			logger.debug("2 >>>>>>> PKG_CARATULAS2.P_Obtiene_Monto_Escrito ::: in :::" + cs.toString());
			cs.execute();
			rs = (ResultSet) cs.getObject(3);
			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Monto_Escrito ::: out :::" + rs.toString());
			
			cursorResultSetMetaData(rs, "PKG_CARATULAS2.P_Obtiene_Monto_Escrito");

			if(rs.next()) {				
				datoLiquidacionVO.setPrimaTotalNum(rs.getString(1)!= "" ? rs.getString(1) : "");
			}
						
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
				rs.close();
				cs.close();
				conn.close();
			} catch (SQLException sql) {
				sql.printStackTrace();
			}
		}
		return datoLiquidacionVO;

	}
	
	// **************************************************************************
	// RECIBO DE PAGO ::: DATOS LIQUIDACION ::: indica si la poliza tiene pago ref. 
	// **************************************************************************
	public static String obtenMetodoPago(String cdUnieco, String cdRamo, String estado, String nmPoliza, String nmsuplem) {

		if (logger.isDebugEnabled()) {
			logger.debug(">>>>>>> Inicia obtenMetodoPago.");
		}

		String metodoPago = "0";
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;


		try {
			// Se realiza la conexión
			conn = QuerysBd.getConnection();
					
			int wcdunieco = Integer.parseInt(cdUnieco);
			int wcdramo = Integer.parseInt(cdRamo);
			int wnmpoliza = Integer.parseInt(nmPoliza);
			//long wnmsuplem =  Long.parseLong(nmsuplem.trim());

			logger.debug("PKG_CARATULAS2.P_Obtiene_Metodo_Pago( ?,?,?,?,?,? )");
			cs = conn.prepareCall("{ call PKG_CARATULAS2.P_Obtiene_Metodo_Pago( ?,?,?,?,?,? ) }");
			cs.setInt(1, wcdunieco);
			cs.setInt(2, wcdramo);
			cs.setString(3, estado);
			cs.setInt(4, wnmpoliza);
			cs.setString(5, nmsuplem);
			cs.registerOutParameter(6, OracleTypes.CURSOR);
			
			logger.debug(" >>>>>>> PKG_CARATULAS2.P_Obtiene_Metodo_Pago ::: in :::" + cs.toString());
			cs.execute();
			rs = (ResultSet) cs.getObject(6);		
			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Metodo_Pago ::: out :::" + rs.toString());
			
			cursorResultSetMetaData(rs, "PKG_CARATULAS2.P_Obtiene_Metodo_Pago" );
			
			if (rs.next()) {
				metodoPago = rs.getString(1) != "" ? rs.getString(1) : "-";				
				logger.debug("metodoPago = " + metodoPago);
			}else{
				metodoPago = " "; //"sin informacion, en construcción.";
				logger.debug("metodoPago = " + metodoPago);
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
		return metodoPago;
	}
	
	// **************************************************************************
	// RECIBO DE PAGO ::: DATOS LIQUIDACION ::: indica la la cantidad de recibos. 
	// **************************************************************************
	public static Campo[] obtenCantidadRecibos(String cdUnieco, String cdRamo, String estado, String nmPoliza) {

		if (logger.isDebugEnabled()) {
			logger.debug(">>>>>>> Inicia obtenCantidadRecibos.");			
		}

		Campo [] camposRecibos = null;
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;

		try {
			// Se realiza la conexión
			conn = QuerysBd.getConnection();

			int wcdunieco = Integer.parseInt(cdUnieco);
			int wcdramo = Integer.parseInt(cdRamo);
			int wnmpoliza = Integer.parseInt(nmPoliza);

			logger.debug("PKG_CARATULAS2.P_Obtiene_Cantidad_Recibos( ?,?,?,?,? )");
			cs = conn.prepareCall("{ call PKG_CARATULAS2.P_Obtiene_Cantidad_Recibos( ?,?,?,?,? ) }");
			cs.setInt(1, wcdunieco);
			cs.setInt(2, wcdramo);
			cs.setString(3, estado);
			cs.setInt(4, wnmpoliza);
			cs.registerOutParameter(5, OracleTypes.CURSOR);
			
			logger.debug(" >>>>>>> PKG_CARATULAS2.P_Obtiene_Cantidad_Recibos ::: in :::" + cs.toString());
			cs.execute();
			rs = (ResultSet) cs.getObject(5);		
			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Cantidad_Recibos ::: out :::" + rs.toString());
			
			cursorResultSetMetaData(rs, "PKG_CARATULAS2.P_Obtiene_Cantidad_Recibos" );
			
			while(rs.next()) {
				
				if(camposRecibos == null) camposRecibos = new Campo[0];
				
				camposRecibos = UtilCampo.addCampo(camposRecibos, String.valueOf(rs.getInt("NMRECIBO")), rs.getString("SERIELIQUIDACION"));		
				logger.debug(">>>>>>> NMRECIBO ::: "+rs.getInt("NMRECIBO"));
				logger.debug(">>>>>>> SERIELIQUIDACION ::: "+rs.getString("SERIELIQUIDACION"));
				
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
		return camposRecibos;
	}
	
	
	// **************************************************************************
	// RECIBO DE PAGO ::: FORMATO PAGO
	// **************************************************************************
	public static ArrayList obtenArrayFormatoPago(String cdUnieco, String cdRamo, String estado, 
												String nmPoliza, String nmRecibo) {

		if (logger.isDebugEnabled()) {
			logger.debug(">>>>>>> Inicia obtenArrayFormatoPago.");
		}
		
		String banco = "";
		String convenio = "";
		String linea = "";
		String concepto = "";
		
		ArrayList data = new ArrayList();
		Campo [] camposRecibos= null;
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;

		try {
			// Se realiza la conexión
			conn = QuerysBd.getConnection();

			int wcdunieco = Integer.parseInt(cdUnieco);
			int wcdramo = Integer.parseInt(cdRamo);
			int wnmpoliza = Integer.parseInt(nmPoliza);
			int wnmrecibo = Integer.parseInt(nmRecibo);
						
			cs = conn.prepareCall("{ call PKG_CARATULAS2.P_Obtiene_Formato_Pago( ?,?,?,?,?,? ) }");
			cs.setInt(1, wcdunieco);
			cs.setInt(2, wcdramo);
			cs.setString(3, estado);
			cs.setInt(4, wnmpoliza);
			cs.setInt(5, wnmrecibo);			
			cs.registerOutParameter(6, OracleTypes.CURSOR);
			
			logger.debug(" >>>>>>> PKG_CARATULAS2.P_Obtiene_Formato_Pago ::: in :::" + cs.toString());
			cs.execute();
			rs = (ResultSet) cs.getObject(6);		
			logger.debug(">>>>>>> PKG_CARATULAS2.P_Obtiene_Formato_Pago ::: out :::" + rs.toString());
			
			cursorResultSetMetaData(rs, "PKG_CARATULAS2.P_Obtiene_Formato_Pago" );
			
			int j=0;
			while(rs.next()) {
				
				logger.debug(">>>>>>> hash ::: "+(j+1) );
				
				banco = rs.getString("BANCO") != null ? rs.getString("BANCO") : " ";
				logger.debug(">>>>>>> banco ::: "+ banco );
				convenio = rs.getString("CONVENIO") != null ? rs.getString("CONVENIO") : " ";
				logger.debug(">>>>>>> convenio ::: "+ convenio );
				linea = rs.getString("LINEACAPTURABANCO") != null ? rs.getString("LINEACAPTURABANCO") : " ";
				logger.debug(">>>>>>> linea ::: "+ linea );
				//concepto = rs.getString("CONCEPTO");
				concepto = " ";
				logger.debug(">>>>>>> concepto ::: "+ concepto );
				                
				HashMap map = new HashMap();	  
	        	map.put("po_banco", banco);
	        	map.put("po_convenio", convenio);
	        	map.put("po_linea", linea);
	  	      	map.put("po_concepto", concepto);
	  	        
	  	      	data.add(map);
	  	      logger.debug(">>>>>>> data en map ok");
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
		return data;
	}
	
	
	public static void cursorResultSetMetaData(ResultSet rs, String pl ){
		
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
	}

	public static void cursorDataAtribu(ResultSet rs, String pl ){
		ResultSetMetaData metaData = null;
		int numeroCols = 0;
		String mData ="";

		try {
			while( rs.next() ) {
				logger.debug(rs.getString(1)+":"+rs.getString(2)+":"+rs.getString(5));				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}