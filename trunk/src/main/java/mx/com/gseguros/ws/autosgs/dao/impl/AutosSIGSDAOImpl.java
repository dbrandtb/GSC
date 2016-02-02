package mx.com.gseguros.ws.autosgs.dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.ws.autosgs.dao.AutosSIGSDAO;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContextException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.object.StoredProcedure;

public class AutosSIGSDAOImpl extends AbstractManagerDAO implements AutosSIGSDAO {

	private static Logger logger = Logger.getLogger(AutosSIGSDAOImpl.class);
	
	@Override
	public Integer cambioDomicilioCP(Map<String, Object> params) throws Exception {
		Integer resp = null;
		Map<String, Object> mapResult = ejecutaSP(new CambioDomicilioCP(getDataSource()), params);
		resp = (Integer) mapResult.get("rs");
		
		return resp;
	}
	
	public class CambioDomicilioCP extends StoredProcedure{
		protected CambioDomicilioCP(DataSource dataSource){
			super(dataSource, "sp_ActualizaDirCliente");
			
			declareParameter(new SqlParameter("vSucursal", Types.SMALLINT));
			declareParameter(new SqlParameter("vRamo", Types.SMALLINT));
			declareParameter(new SqlParameter("vPoliza", Types.INTEGER));
			declareParameter(new SqlParameter("vTEndoso", Types.VARCHAR));
			declareParameter(new SqlParameter("vEndoso", Types.INTEGER));
			declareParameter(new SqlParameter("vCPostal", Types.INTEGER));
			declareParameter(new SqlParameter("vCveEdo", Types.SMALLINT));
			declareParameter(new SqlParameter("vDesMun", Types.VARCHAR));
			declareParameter(new SqlParameter("vMunCepomex", Types.SMALLINT));
			declareParameter(new SqlParameter("vColonia", Types.VARCHAR));
			declareParameter(new SqlParameter("vTelefono", Types.VARCHAR));
			declareParameter(new SqlParameter("vCalle", Types.VARCHAR));
			declareParameter(new SqlParameter("vNumero", Types.VARCHAR));
			declareParameter(new SqlParameter("vNumInt", Types.VARCHAR));
			declareParameter(new SqlParameter("vNumDir", Types.SMALLINT));
			declareParameter(new SqlParameter("vUSER", Types.VARCHAR));
			
			declareParameter(new SqlReturnResultSet("rs", new ResultSetExtractor<Integer>(){  
				@Override  
				public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {  
					Integer result = null;
					while(rs.next()){  
						result = rs.getInt(1);
					}  
					return result;  
				}
			}));
			
			compile();
		}
	}
	
	
	@Override
	public Integer endosoDomicilio(Map<String, Object> params) throws Exception {
		Integer resp = null;
		Map<String, Object> mapResult = ejecutaSP(new EndosoDomicilio(getDataSource()), params);
		resp = (Integer) mapResult.get("rs");
		
		return resp;
	}
	
	public class EndosoDomicilio extends StoredProcedure{
		protected EndosoDomicilio(DataSource dataSource){
			super(dataSource, "sp_EndosoBCamDomicilio");
			
			declareParameter(new SqlParameter("vIdMotivo", Types.SMALLINT));
			declareParameter(new SqlParameter("vSucursal", Types.SMALLINT));
			declareParameter(new SqlParameter("vRamo", Types.SMALLINT));
			declareParameter(new SqlParameter("vPoliza", Types.INTEGER));
			declareParameter(new SqlParameter("vTEndoso", Types.VARCHAR));
			declareParameter(new SqlParameter("vEndoso", Types.INTEGER));
			declareParameter(new SqlParameter("vCalle", Types.VARCHAR));
			declareParameter(new SqlParameter("vNumero", Types.VARCHAR));
			
			declareParameter(new SqlParameter("vNumInt", Types.VARCHAR));
			
			declareParameter(new SqlParameter("vTelefono1", Types.VARCHAR));
			declareParameter(new SqlParameter("vTelefono2", Types.VARCHAR));
			declareParameter(new SqlParameter("vTelefono3", Types.VARCHAR));
			declareParameter(new SqlParameter("vFEndoso", Types.DATE));
			declareParameter(new SqlParameter("vNumDir", Types.SMALLINT));
			declareParameter(new SqlParameter("vUSER", Types.VARCHAR));
			
			declareParameter(new SqlReturnResultSet("rs", new ResultSetExtractor<Integer>(){  
				@Override  
				public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {  
					Integer result = null;
					while(rs.next()){  
						result = rs.getInt(1);
					}  
					return result;  
				}
			}));
			
			compile();
		}
	}
	
	@Override
	public Integer insertaReciboAuto(Map<String, Object> params) throws Exception {
		Integer resp = null;
		Map<String, Object> mapResult = ejecutaSP(new InsertaReciboAuto(getDataSource()), params);
		resp = (Integer) mapResult.get("rs");
	
		return resp;
	}
	
	public class InsertaReciboAuto extends StoredProcedure{
		protected InsertaReciboAuto(DataSource dataSource){
			super(dataSource, "spReciboSigs");
			
			declareParameter(new SqlParameter("Sucursal", Types.SMALLINT));
			declareParameter(new SqlParameter("Ramo", Types.SMALLINT));
			declareParameter(new SqlParameter("Poliza", Types.INTEGER));
			declareParameter(new SqlParameter("TipoEndoso", Types.VARCHAR));
			declareParameter(new SqlParameter("NumeroEndoso", Types.INTEGER));
			declareParameter(new SqlParameter("Recibo", Types.SMALLINT));
			declareParameter(new SqlParameter("TotalRecibos", Types.SMALLINT));
			declareParameter(new SqlParameter("PrimaNeta", Types.DECIMAL));
			declareParameter(new SqlParameter("Iva", Types.DECIMAL));
			declareParameter(new SqlParameter("Recargo", Types.DECIMAL));
			declareParameter(new SqlParameter("Derechos", Types.DECIMAL));
			declareParameter(new SqlParameter("CesionComision", Types.DECIMAL));
			declareParameter(new SqlParameter("ComisionPrima", Types.DECIMAL));
			declareParameter(new SqlParameter("ComisionRecargo", Types.DECIMAL));
			declareParameter(new SqlParameter("FechaInicio", Types.DATE));
			declareParameter(new SqlParameter("FechaTermino", Types.DATE));
			declareParameter(new SqlParameter("Modo", Types.SMALLINT));
			declareParameter(new SqlParameter("Estatus", Types.VARCHAR));
			
			declareParameter(new SqlReturnResultSet("rs", new ResultSetExtractor<Integer>(){  
			@Override  
		     public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {  
				Integer result = null;
		        while(rs.next()){  
		        	result = rs.getInt(1);
		        }  
		        return result;  
		        }
		    }));
			
			compile();
		}
	}
	
	@Override
	public Integer confirmaRecibosAuto(Map<String, Object> params) throws Exception {
		Integer resp = null;
		Map<String, Object> mapResult = ejecutaSP(new ConfirmaRecibosAuto(getDataSource()), params);
		resp = (Integer) mapResult.get("rs");
	
		return resp;
	}
	
	public class ConfirmaRecibosAuto extends StoredProcedure{
		protected ConfirmaRecibosAuto(DataSource dataSource){
			super(dataSource, "spValidaEmisionSigs");
			
			declareParameter(new SqlParameter("Sucursal", Types.SMALLINT));
			declareParameter(new SqlParameter("Ramo", Types.SMALLINT));
			declareParameter(new SqlParameter("Poliza", Types.INTEGER));
			declareParameter(new SqlParameter("TipoEndoso", Types.VARCHAR));
			declareParameter(new SqlParameter("NumeroEndoso", Types.INTEGER));
			declareParameter(new SqlReturnResultSet("rs", new ResultSetExtractor<Integer>(){  
				@Override  
			     public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {  
					Integer result = null;
			        while(rs.next()){  
			        	result = rs.getInt(1);
			        }  
			        return result;  
			        }
			    }));
						
			compile();
		}
	}

	@Override
	public Integer ejecutaVidaPorRecibo(Map<String, Object> params) throws Exception {
		Integer resp = null;
		Map<String, Object> mapResult = ejecutaSP(new EjecutaVidaPorRecibo(getDataSource()), params);
		resp = (Integer) mapResult.get("rs");
		
		return resp;
	}
	
	public class EjecutaVidaPorRecibo extends StoredProcedure{
		protected EjecutaVidaPorRecibo(DataSource dataSource){
			super(dataSource, "spVidaxRecibo");
			
			declareParameter(new SqlParameter("Sucursal", Types.SMALLINT));
			declareParameter(new SqlParameter("Ramo", Types.SMALLINT));
			declareParameter(new SqlParameter("Poliza", Types.INTEGER));
			declareParameter(new SqlParameter("TipoEndoso", Types.VARCHAR));
			declareParameter(new SqlParameter("NumeroEndoso", Types.INTEGER));
			declareParameter(new SqlReturnResultSet("rs", new ResultSetExtractor<Integer>(){  
				@Override  
				public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {  
					Integer result = null;
					while(rs.next()){  
						result = rs.getInt(1);
					}  
					return result;  
				}
			}));
			
			compile();
		}
	}
	
	@Override
	public Integer endosoPlacasMotor(Map<String, Object> params) throws Exception {
		Integer resp = null;
		Map<String, Object> mapResult = ejecutaSP(new EndosoPlacasMotor(getDataSource()), params);
		resp = (Integer) mapResult.get("rs");
		
		return resp;
	}
	
	public class EndosoPlacasMotor extends StoredProcedure{
		protected EndosoPlacasMotor(DataSource dataSource){
			super(dataSource, "sp_EndosoBPlacasMotor");
			
			declareParameter(new SqlParameter("vIdMotivo", Types.SMALLINT));
			declareParameter(new SqlParameter("vSucursal", Types.SMALLINT));
			declareParameter(new SqlParameter("vRamo",     Types.SMALLINT));
			declareParameter(new SqlParameter("vPoliza",   Types.INTEGER));
			declareParameter(new SqlParameter("vTEndoso",  Types.VARCHAR));
			declareParameter(new SqlParameter("vEndoso",   Types.INTEGER));
			declareParameter(new SqlParameter("vInciso",   Types.SMALLINT));
			declareParameter(new SqlParameter("vPlacas",   Types.VARCHAR));
			declareParameter(new SqlParameter("vMotor",    Types.VARCHAR));
			declareParameter(new SqlParameter("vEndoB",    Types.INTEGER));
			declareParameter(new SqlParameter("vFEndoso", Types.DATE));
			
			declareParameter(new SqlReturnResultSet("rs", new ResultSetExtractor<Integer>(){  
				@Override  
				public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {  
					Integer result = null;
					while(rs.next()){  
						result = rs.getInt(1);
					}  
					return result;  
				}
			}));
			
			compile();
		}
	}
	
	@Override
	public Integer endosoTipoServicio(Map<String, Object> params) throws Exception {
		Integer resp = null;
		Map<String, Object> mapResult = ejecutaSP(new EndosoTipoServicio(getDataSource()), params);
		resp = (Integer) mapResult.get("rs");
		
		return resp;
	}
	
	public class EndosoTipoServicio extends StoredProcedure{
		protected EndosoTipoServicio(DataSource dataSource){
			super(dataSource, "sp_EndosoBTipoServicio");
			
			declareParameter(new SqlParameter("vIdMotivo", Types.SMALLINT));
			declareParameter(new SqlParameter("vSucursal", Types.SMALLINT));
			declareParameter(new SqlParameter("vRamo",     Types.SMALLINT));
			declareParameter(new SqlParameter("vPoliza",   Types.INTEGER));
			declareParameter(new SqlParameter("vTEndoso",  Types.VARCHAR));
			declareParameter(new SqlParameter("vEndoso",   Types.INTEGER));
			declareParameter(new SqlParameter("vInciso",   Types.SMALLINT));
			declareParameter(new SqlParameter("vServicio", Types.SMALLINT));
			declareParameter(new SqlParameter("vTipoUso",  Types.SMALLINT));
			declareParameter(new SqlParameter("vEndoB",    Types.INTEGER));
			declareParameter(new SqlParameter("vFEndoso", Types.DATE));
			
			declareParameter(new SqlReturnResultSet("rs", new ResultSetExtractor<Integer>(){  
				@Override  
				public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {  
					Integer result = null;
					while(rs.next()){  
						result = rs.getInt(1);
					}  
					return result;  
				}
			}));
			
			compile();
		}
	}

	@Override
	public Integer endosoVigenciaPol(Map<String, Object> params) throws Exception {
		Integer resp = null;
		Map<String, Object> mapResult = ejecutaSP(new EndosoVigenciaPol(getDataSource()), params);
		resp = (Integer) mapResult.get("rs");
		
		return resp;
	}
	
	public class EndosoVigenciaPol extends StoredProcedure{
		protected EndosoVigenciaPol(DataSource dataSource){
			super(dataSource, "sp_EndosoBCamVigencia");
			
			declareParameter(new SqlParameter("vIdMotivo", Types.SMALLINT));
			declareParameter(new SqlParameter("vSucursal", Types.SMALLINT));
			declareParameter(new SqlParameter("vRamo",     Types.SMALLINT));
			declareParameter(new SqlParameter("vPoliza",   Types.INTEGER));
			declareParameter(new SqlParameter("vTEndoso",  Types.VARCHAR));
			declareParameter(new SqlParameter("vEndoso",   Types.INTEGER));
			declareParameter(new SqlParameter("vRecibo",   Types.SMALLINT));
			declareParameter(new SqlParameter("vFIniRec",  Types.DATE));
			declareParameter(new SqlParameter("vFFinRec",  Types.DATE));
			declareParameter(new SqlParameter("vFIniPol",  Types.DATE));
			declareParameter(new SqlParameter("vFFinPol",  Types.DATE));
			declareParameter(new SqlParameter("vFEndoso",  Types.DATE));
			declareParameter(new SqlParameter("vEndoB",    Types.INTEGER));
			
			declareParameter(new SqlReturnResultSet("rs", new ResultSetExtractor<Integer>(){  
				@Override  
				public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {  
					Integer result = null;
					while(rs.next()){  
						result = rs.getInt(1);
					}  
					return result;  
				}
			}));
			
			compile();
		}
	}

	@Override
	public Integer endosoTextoLibre(Map<String, Object> params) throws Exception {
		Integer resp = null;
		Map<String, Object> mapResult = ejecutaSP(new EndosoTextoLibre(getDataSource()), params);
		resp = (Integer) mapResult.get("rs");
		
		return resp;
	}
	
	public class EndosoTextoLibre extends StoredProcedure{
		protected EndosoTextoLibre(DataSource dataSource){
			super(dataSource, "sp_EndosoBTextoLibre");
			
			declareParameter(new SqlParameter("vIdMotivo", Types.SMALLINT));
			declareParameter(new SqlParameter("vSucursal", Types.SMALLINT));
			declareParameter(new SqlParameter("vRamo",     Types.SMALLINT));
			declareParameter(new SqlParameter("vPoliza",   Types.INTEGER));
			declareParameter(new SqlParameter("vTexto",  Types.VARCHAR));
			declareParameter(new SqlParameter("vFEndoso",  Types.DATE));
			
			declareParameter(new SqlReturnResultSet("rs", new ResultSetExtractor<Integer>(){  
				@Override  
				public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {  
					Integer result = null;
					while(rs.next()){  
						result = rs.getInt(1);
					}  
					return result;  
				}
			}));
			
			compile();
		}
	}

	@Override
	public Integer endosoSerie(Map<String, Object> params) throws Exception {
		Integer resp = null;
		Map<String, Object> mapResult = ejecutaSP(new EndosoSerie(getDataSource()), params);
		resp = (Integer) mapResult.get("rs");
		
		return resp;
	}
	
	public class EndosoSerie extends StoredProcedure{
		protected EndosoSerie(DataSource dataSource){
			super(dataSource, "sp_EndosoBNumSerie");
			
			declareParameter(new SqlParameter("vIdMotivo", Types.SMALLINT));
			declareParameter(new SqlParameter("vSucursal", Types.SMALLINT));
			declareParameter(new SqlParameter("vRamo", Types.SMALLINT));
			declareParameter(new SqlParameter("vPoliza", Types.INTEGER));
			declareParameter(new SqlParameter("vTEndoso", Types.VARCHAR));
			declareParameter(new SqlParameter("vEndoso", Types.INTEGER));
			declareParameter(new SqlParameter("vInciso", Types.SMALLINT));
			declareParameter(new SqlParameter("vSerie", Types.VARCHAR));
			declareParameter(new SqlParameter("vEndoB", Types.INTEGER));
			declareParameter(new SqlParameter("vFEndoso", Types.DATE));
			
			declareParameter(new SqlReturnResultSet("rs", new ResultSetExtractor<Integer>(){  
				@Override  
				public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {  
					Integer result = null;
					while(rs.next()){  
						result = rs.getInt(1);
					}  
					return result;  
				}
			}));
			
			compile();
		}
	}
	
	@Override
	public Integer endosoBeneficiario(Map<String, Object> params) throws Exception {
		Integer resp = null;
		Map<String, Object> mapResult = ejecutaSP(new EndosoBeneficiario(getDataSource()), params);
		resp = (Integer) mapResult.get("rs");
		
		return resp;
	}
	
	public class EndosoBeneficiario extends StoredProcedure{
		protected EndosoBeneficiario(DataSource dataSource){
			super(dataSource, "sp_EndosoBBeneficiario");
			
			declareParameter(new SqlParameter("vIdMotivo", Types.SMALLINT));
			declareParameter(new SqlParameter("vSucursal", Types.SMALLINT));
			declareParameter(new SqlParameter("vRamo", Types.SMALLINT));
			declareParameter(new SqlParameter("vPoliza", Types.INTEGER));
			declareParameter(new SqlParameter("vBeneficiario", Types.VARCHAR));
			declareParameter(new SqlParameter("vListaIncisos", Types.VARCHAR));
			declareParameter(new SqlParameter("vFEndoso", Types.DATE));
			declareParameter(new SqlParameter("vClausula", Types.SMALLINT));
			
			declareParameter(new SqlReturnResultSet("rs", new ResultSetExtractor<Integer>(){  
				@Override  
				public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {  
					Integer result = null;
					while(rs.next()){  
						result = rs.getInt(1);
					}  
					return result;  
				}
			}));
			
			compile();
		}
	}
	
	
	@Override
	public Integer endosoAseguradoAlterno(Map<String, Object> params) throws Exception {
		Integer resp = null;
		Map<String, Object> mapResult = ejecutaSP(new EndosoAseguradoAlterno(getDataSource()), params);
		resp = (Integer) mapResult.get("rs");
		
		return resp;
	}
	
	public class EndosoAseguradoAlterno extends StoredProcedure{
		protected EndosoAseguradoAlterno(DataSource dataSource){
			super(dataSource, "sp_EndosoBAsegAlterno");
			
			declareParameter(new SqlParameter("vIdMotivo", Types.SMALLINT));
			declareParameter(new SqlParameter("vSucursal", Types.SMALLINT));
			declareParameter(new SqlParameter("vRamo", Types.SMALLINT));
			declareParameter(new SqlParameter("vPoliza", Types.INTEGER));
			declareParameter(new SqlParameter("vAsegAlterno", Types.VARCHAR));
			declareParameter(new SqlParameter("vFEndoso", Types.DATE));
			
			declareParameter(new SqlReturnResultSet("rs", new ResultSetExtractor<Integer>(){  
				@Override  
				public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {  
					Integer result = null;
					while(rs.next()){  
						result = rs.getInt(1);
					}  
					return result;  
				}
			}));
			
			compile();
		}
	}
	
	
	@Override
	public Integer endosoAdaptacionesRC(Map<String, Object> params) throws Exception {
		Integer resp = null;
		Map<String, Object> mapResult = ejecutaSP(new EndosoAdaptacionesRC(getDataSource()), params);
		resp = (Integer) mapResult.get("rs");
		
		return resp;
	}
	
	public class EndosoAdaptacionesRC extends StoredProcedure{
		protected EndosoAdaptacionesRC(DataSource dataSource){
			super(dataSource, "sp_EndosoBAdaptacionRC");
			
			declareParameter(new SqlParameter("vIdMotivo", Types.SMALLINT));
			declareParameter(new SqlParameter("vSucursal", Types.SMALLINT));
			declareParameter(new SqlParameter("vRamo", Types.SMALLINT));
			declareParameter(new SqlParameter("vPoliza", Types.INTEGER));
			declareParameter(new SqlParameter("vInciso", Types.SMALLINT));
			declareParameter(new SqlParameter("vTexto", Types.VARCHAR));
			declareParameter(new SqlParameter("vEndoB", Types.INTEGER));
			declareParameter(new SqlParameter("vFEndoso", Types.DATE));
			
			declareParameter(new SqlReturnResultSet("rs", new ResultSetExtractor<Integer>(){  
				@Override  
				public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {  
					Integer result = null;
					while(rs.next()){  
						result = rs.getInt(1);
					}  
					return result;  
				}
			}));
			
			compile();
		}
	}
	
//	@Override
//	public Integer endosoVigencia(Map<String, Object> params) throws Exception {
//		Integer resp = null;
//		Map<String, Object> mapResult = ejecutaSP(new EndosoVigencia(getDataSource()), params);
//		resp = (Integer) mapResult.get("rs");
//		
//		return resp;
//	}
//	
//	public class EndosoVigencia extends StoredProcedure{
//		protected EndosoVigencia(DataSource dataSource){
//			super(dataSource, "sp_EndosoBCamVigencia");
//			
//			declareParameter(new SqlParameter("vIdMotivo", Types.SMALLINT));
//			declareParameter(new SqlParameter("vSucursal", Types.SMALLINT));
//			declareParameter(new SqlParameter("vRamo", Types.SMALLINT));
//			declareParameter(new SqlParameter("vPoliza", Types.INTEGER));
//			declareParameter(new SqlParameter("vTEndoso", Types.VARCHAR));
//			declareParameter(new SqlParameter("vEndoso", Types.INTEGER));
//			declareParameter(new SqlParameter("vRecibo", Types.SMALLINT));
//			declareParameter(new SqlParameter("vFInicio", Types.DATE));
//			declareParameter(new SqlParameter("vFTermino", Types.DATE));
//			declareParameter(new SqlParameter("vEndoB", Types.INTEGER));
//			
//			declareParameter(new SqlReturnResultSet("rs", new ResultSetExtractor<Integer>(){  
//				@Override  
//				public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {  
//					Integer result = null;
//					while(rs.next()){  
//						result = rs.getInt(1);
//					}  
//					return result;  
//				}
//			}));
//			
//			compile();
//		}
//	}

	@Override
	public Integer endosoNombreCliente(Map<String, Object> params) throws Exception {
		Integer resp = null;
		Map<String, Object> mapResult = ejecutaSP(new EndosoNombreCliente(getDataSource()), params);
		resp = (Integer) mapResult.get("rs");
		
		return resp;
	}
	
	public class EndosoNombreCliente extends StoredProcedure{
		protected EndosoNombreCliente(DataSource dataSource){
			super(dataSource, "sp_EndosBCamNomCliente");
			
			declareParameter(new SqlParameter("vIdMotivo", Types.SMALLINT));
			declareParameter(new SqlParameter("vSucursal", Types.SMALLINT));
			declareParameter(new SqlParameter("vRamo", Types.SMALLINT));
			declareParameter(new SqlParameter("vPoliza", Types.INTEGER));
			declareParameter(new SqlParameter("vNombre", Types.VARCHAR));
			declareParameter(new SqlParameter("vAPaterno", Types.VARCHAR));
			declareParameter(new SqlParameter("vAMaterno", Types.VARCHAR));
			declareParameter(new SqlParameter("vRasonSocial", Types.VARCHAR));
			declareParameter(new SqlParameter("vFEndoso", Types.DATE));
			
			declareParameter(new SqlReturnResultSet("rs", new ResultSetExtractor<Integer>(){  
				@Override  
				public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {  
					Integer result = null;
					while(rs.next()){  
						result = rs.getInt(1);
					}  
					return result;  
				}
			}));
			
			compile();
		}
	}

	@Override
	public Integer endosoRfcCliente(Map<String, Object> params) throws Exception {
		Integer resp = null;
		Map<String, Object> mapResult = ejecutaSP(new EndosoRfcCliente(getDataSource()), params);
		resp = (Integer) mapResult.get("rs");
		
		return resp;
	}
	
	public class EndosoRfcCliente extends StoredProcedure{
		protected EndosoRfcCliente(DataSource dataSource){
			super(dataSource, "sp_EndosoBCambioRFC");
			
			declareParameter(new SqlParameter("vIdMotivo", Types.SMALLINT));
			declareParameter(new SqlParameter("vSucursal", Types.SMALLINT));
			declareParameter(new SqlParameter("vRamo", Types.SMALLINT));
			declareParameter(new SqlParameter("vPoliza", Types.INTEGER));
			declareParameter(new SqlParameter("vRFC", Types.VARCHAR));
			declareParameter(new SqlParameter("vFEndoso", Types.DATE));
			
			declareParameter(new SqlReturnResultSet("rs", new ResultSetExtractor<Integer>(){  
				@Override  
				public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {  
					Integer result = null;
					while(rs.next()){  
						result = rs.getInt(1);
					}  
					return result;  
				}
			}));
			
			compile();
		}
	}

	@Override
	public Integer endosoCambioCliente(Map<String, Object> params) throws Exception {
		Integer resp = null;
		Map<String, Object> mapResult = ejecutaSP(new EndosoCambioCliente(getDataSource()), params);
		resp = (Integer) mapResult.get("rs");
		
		return resp;
	}
	
	public class EndosoCambioCliente extends StoredProcedure{
		protected EndosoCambioCliente(DataSource dataSource){
			super(dataSource, "sp_EndosoBCamNumeroCliente");
			
			declareParameter(new SqlParameter("vIdMotivo", Types.SMALLINT));
			declareParameter(new SqlParameter("vSucursal", Types.SMALLINT));
			declareParameter(new SqlParameter("vRamo", Types.SMALLINT));
			declareParameter(new SqlParameter("vPoliza", Types.INTEGER));
			declareParameter(new SqlParameter("vCveCliente", Types.INTEGER));
			declareParameter(new SqlParameter("vFEndoso", Types.DATE));
			
			declareParameter(new SqlReturnResultSet("rs", new ResultSetExtractor<Integer>(){  
				@Override  
				public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
					Integer result = null;
					while(rs.next()){  
						result = rs.getInt(1);
					}  
					return result;  
				}
			}));
			
			compile();
		}
	}
	
	@Override
	public Integer cambioDomicilioSinCPColonia(Map<String, Object> params) throws Exception {
		Integer resp = null;
		Map<String, Object> mapResult = ejecutaSP(new CambioDomicilioSinCPColonia(getDataSource()), params);
		resp = (Integer) mapResult.get("rs");
		
		return resp;
	}
	
	public class CambioDomicilioSinCPColonia extends StoredProcedure{
		protected CambioDomicilioSinCPColonia(DataSource dataSource){
			super(dataSource, "sp_EndosoBDomicilioColonia");
			
			declareParameter(new SqlParameter("vIdMotivo", Types.SMALLINT));
			declareParameter(new SqlParameter("vSucursal", Types.SMALLINT));
			declareParameter(new SqlParameter("vRamo", Types.SMALLINT));
			declareParameter(new SqlParameter("vPoliza", Types.INTEGER));
			declareParameter(new SqlParameter("vTEndoso", Types.VARCHAR));
			declareParameter(new SqlParameter("vEndoso", Types.INTEGER));
			declareParameter(new SqlParameter("vCodpostal", Types.INTEGER));
			declareParameter(new SqlParameter("vColonia", Types.VARCHAR));
			declareParameter(new SqlParameter("vCalle", Types.VARCHAR));
			declareParameter(new SqlParameter("vNumero", Types.VARCHAR));
			declareParameter(new SqlParameter("vTelefono1", Types.VARCHAR));
			declareParameter(new SqlParameter("vTelefono2", Types.VARCHAR));
			declareParameter(new SqlParameter("vTelefono3", Types.VARCHAR));
			
			declareParameter(new SqlParameter("vCveEdo", Types.SMALLINT));
			declareParameter(new SqlParameter("vMpioSPM", Types.SMALLINT));
			declareParameter(new SqlParameter("vNumInt", Types.VARCHAR));
			
			declareParameter(new SqlParameter("vFEndoso", Types.DATE));
			declareParameter(new SqlParameter("vNumDir", Types.SMALLINT));
			declareParameter(new SqlParameter("vUSER", Types.VARCHAR));
			
			declareParameter(new SqlReturnResultSet("rs", new ResultSetExtractor<Integer>(){  
				@Override  
				public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {  
					Integer result = null;
					while(rs.next()){  
						result = rs.getInt(1);
					}  
					return result;  
				}
			}));
			
			compile();
		}
	}

	@Override
	public void revierteEndosoFallidoSigs(Map<String, Object> params) throws Exception {
		String resp = null;
		Map<String, Object> mapResult = ejecutaSP(new RevierteEndosoFallidoSigs(getDataSource()), params);
		resp = (String) mapResult.get("rs");
		
		logger.info("Mensaje de respuesta de sprevierteemision: " + resp);
		
//		return resp;
	}
	
	public class RevierteEndosoFallidoSigs extends StoredProcedure{
		protected RevierteEndosoFallidoSigs(DataSource dataSource){
			super(dataSource, "sprevierteemision");
			
			declareParameter(new SqlParameter("vSucursal", Types.SMALLINT));
			declareParameter(new SqlParameter("vRamo", Types.SMALLINT));
			declareParameter(new SqlParameter("vPoliza", Types.INTEGER));
			declareParameter(new SqlParameter("vTipoEndoso", Types.VARCHAR));
			declareParameter(new SqlParameter("vNumEndoso", Types.INTEGER));
			declareParameter(new SqlParameter("vError", Types.SMALLINT));
			declareParameter(new SqlParameter("vDesError", Types.VARCHAR));
			
			declareParameter(new SqlReturnResultSet("rs", new ResultSetExtractor<String>(){
				@Override  
				public String extractData(ResultSet rs) throws SQLException, DataAccessException {  
					String result = null;
					while(rs.next()){  
						result = rs.getString(1);
					}  
					return result;  
				}
			}));
			
			compile();
		}
	}
	
	@Override
	public void revierteEndosoBFallidoSigs(Map<String, Object> params) throws Exception {
		ejecutaSP(new RevierteEndosoBFallidoSigs(getDataSource()), params);
	}
	
	public class RevierteEndosoBFallidoSigs extends StoredProcedure{
		protected RevierteEndosoBFallidoSigs(DataSource dataSource){
			super(dataSource, "sp_RevierteEndosoB");
			
			declareParameter(new SqlParameter("vSucursal", Types.SMALLINT));
			declareParameter(new SqlParameter("vRamo", Types.SMALLINT));
			declareParameter(new SqlParameter("vPoliza", Types.INTEGER));
			declareParameter(new SqlParameter("vEndosoB", Types.INTEGER));
						
			compile();
		}
	}


	@Override
	public String CambioClientenombreRFCfechaNacimiento(Map<String, Object> params) throws Exception {
		String resp = null;
		Map<String, Object> mapResult = ejecutaSP(new CambioClientenombreRFCfechaNacimiento(getDataSource()), params);
		resp = (String) mapResult.get("rs");
		
		return resp;
	}
	
	public class CambioClientenombreRFCfechaNacimiento extends StoredProcedure{
		protected CambioClientenombreRFCfechaNacimiento(DataSource dataSource){
			super(dataSource, "sp_GuardaDatosCliente");
			declareParameter(new SqlParameter("vNumSuc", Types.SMALLINT));
			declareParameter(new SqlParameter("vRamo", Types.SMALLINT));
			declareParameter(new SqlParameter("vPoliza", Types.INTEGER));
			declareParameter(new SqlParameter("vFEndoso", Types.DATE));
			declareParameter(new SqlParameter("vCliente", Types.INTEGER));
			declareParameter(new SqlParameter("vMotivo", Types.SMALLINT));
			declareParameter(new SqlParameter("vNomCli", Types.VARCHAR));
			declareParameter(new SqlParameter("vApePat", Types.VARCHAR));
			declareParameter(new SqlParameter("vApeMat", Types.VARCHAR));
			declareParameter(new SqlParameter("vRazSoc", Types.VARCHAR));
			declareParameter(new SqlParameter("vFecNac", Types.DATE));
			declareParameter(new SqlParameter("vTipPer", Types.VARCHAR));
			declareParameter(new SqlParameter("vRfcCli", Types.VARCHAR));
			declareParameter(new SqlParameter("vCveEle", Types.VARCHAR));
			declareParameter(new SqlParameter("vCurpCli", Types.VARCHAR));
			declareParameter(new SqlParameter("vCalleCli", Types.VARCHAR));
			declareParameter(new SqlParameter("vNumCli", Types.VARCHAR));
			declareParameter(new SqlParameter("vNumInt", Types.VARCHAR));
			declareParameter(new SqlParameter("vCodPos", Types.INTEGER));
			declareParameter(new SqlParameter("vColonia", Types.VARCHAR));
			declareParameter(new SqlParameter("vMunicipio", Types.SMALLINT));
			declareParameter(new SqlParameter("vCveEdo", Types.SMALLINT));
			declareParameter(new SqlParameter("vPoblacion", Types.VARCHAR));
			declareParameter(new SqlParameter("vTelefono1", Types.VARCHAR));
			declareParameter(new SqlParameter("vTelefono2", Types.VARCHAR));
			declareParameter(new SqlParameter("vTelefono3", Types.VARCHAR));
			declareParameter(new SqlParameter("vUSER", Types.VARCHAR));
			
			declareParameter(new SqlReturnResultSet("rs", new ResultSetExtractor<String>(){  
				@Override  
				public String extractData(ResultSet rs) throws SQLException, DataAccessException {  
					String result = null;
					while(rs.next()){  
						result = rs.getString(1)+"|"+rs.getString(2);
					}  
					return result;  
				}
			}));
			
			compile();
		}
	}

	@Override
	public Integer obtieneTipoCliWS(String codigoExterno, String compania) throws Exception {
		
		if(StringUtils.isBlank(codigoExterno) || StringUtils.isBlank(compania)){
			throw new ApplicationException("Sin datos para tipo cliente Sigs");
		}
		
		int numeroCliente = 0;
		int tipoCliente = 0;
		try{
			numeroCliente = Integer.valueOf(codigoExterno.substring(codigoExterno.length()-10));
			tipoCliente = "D".equalsIgnoreCase(compania)?1:2;
		}catch(Exception ex){
			throw new ApplicationException("Error en conversion de datos para tipo cliente Sigs");
		}
		
		Integer resp = null;
		
		Map<String,Object>params=new LinkedHashMap<String,Object>();
		params.put("vNumCliente", numeroCliente);
		params.put("vTipoCliente", tipoCliente);
		
		Map<String, Object> mapResult  = ejecutaSP(new ObtieneTipoCliWS(getDataSource()), params);
		resp = (Integer) mapResult.get("rs");
		
		return resp;
	}
	
	public class ObtieneTipoCliWS extends StoredProcedure{
		protected ObtieneTipoCliWS(DataSource dataSource){
			super(dataSource, "sp_PolizasXCliente");
			
			declareParameter(new SqlParameter("vNumCliente", Types.INTEGER));
			declareParameter(new SqlParameter("vTipoCliente", Types.SMALLINT));
			
			declareParameter(new SqlReturnResultSet("rs", new ResultSetExtractor<Integer>(){
				@Override  
				public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {  
					Integer result = null;
					while(rs.next()){  
						result = rs.getInt(1);
					}  
					return result;  
				}
			}));
			
			compile();
		}
	}
}
