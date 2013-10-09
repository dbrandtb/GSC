package mx.com.gseguros.portal.consultas.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.aon.portal.dao.AbstractDAO;
import mx.com.aon.portal.dao.CustomStoredProcedure;
import mx.com.aon.portal.dao.WrapperResultadosGeneric;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosAgenteVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosAseguradoVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosCoberturasVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosPolizaVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosSituacionVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosSuplementoVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosTarifaVO;
import mx.com.gseguros.portal.consultas.model.ConsultaPolizaAgenteVO;
import mx.com.gseguros.portal.consultas.model.ConsultaPolizaAseguradoVO;
import mx.com.gseguros.portal.consultas.model.ConsultaReciboAgenteVO;
import mx.com.gseguros.utils.Utilerias;
import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

public class ConsultasPolizaDAO extends AbstractDAO {

	private static Logger logger = Logger.getLogger(ConsultasPolizaDAO.class);

	public static final String OBTIENE_DATOS_AGENTE =      "OBTIENE_DATOS_AGENTE";
	public static final String OBTIENE_DATOS_ASEGURADO =   "OBTIENE_DATOS_ASEGURADO";
	public static final String OBTIENE_DATOS_COBERTURAS =  "OBTIENE_DATOS_COBERTURAS";
	public static final String OBTIENE_DATOS_POLIZA =      "OBTIENE_DATOS_POLIZA";
	public static final String OBTIENE_DATOS_SITUACION =   "OBTIENE_DATOS_SITUACION";
	public static final String OBTIENE_DATOS_SUPLEMENTO =  "OBTIENE_DATOS_SUPLEMENTO";
	public static final String OBTIENE_DATOS_TARIFA =      "OBTIENE_DATOS_TARIFA";
	public static final String OBTIENE_POLIZAS_AGENTE =    "OBTIENE_POLIZAS_AGENTE";
	public static final String OBTIENE_POLIZAS_ASEGURADO = "OBTIENE_POLIZAS_ASEGURADO";
	public static final String OBTIENE_RECIBOS_AGENTE =    "OBTIENE_RECIBOS_AGENTE";

	public static final String OBTIENE_CLAUSULAS =    "OBTIENE_CLAUSULAS";
	
	
	protected void initDao() throws Exception {
		addStoredProcedure(OBTIENE_DATOS_AGENTE,      new ObtieneDatosAgente(getDataSource()));
		addStoredProcedure(OBTIENE_DATOS_ASEGURADO,   new ObtieneDatosAsegurado(getDataSource()));
		addStoredProcedure(OBTIENE_DATOS_COBERTURAS,  new ObtieneDatosCoberturas(getDataSource()));
		addStoredProcedure(OBTIENE_DATOS_POLIZA,      new ObtieneDatosPoliza(getDataSource()));
		addStoredProcedure(OBTIENE_DATOS_SITUACION,   new ObtieneDatosSituacion(getDataSource()));
		addStoredProcedure(OBTIENE_DATOS_SUPLEMENTO,  new ObtieneDatosSuplemento(getDataSource()));
		addStoredProcedure(OBTIENE_DATOS_TARIFA,      new ObtieneDatosTarifa(getDataSource()));
		addStoredProcedure(OBTIENE_POLIZAS_AGENTE,    new ObtienePolizasAgente(getDataSource()));
		addStoredProcedure(OBTIENE_POLIZAS_ASEGURADO, new ObtienePolizasAsegurado(getDataSource()));
		addStoredProcedure(OBTIENE_RECIBOS_AGENTE,    new ObtieneRecibosAgente(getDataSource()));

		addStoredProcedure(OBTIENE_CLAUSULAS,    new ObtieneClausulas(getDataSource()));
	}

	
	protected class ObtieneDatosPoliza extends CustomStoredProcedure {

		protected ObtieneDatosPoliza(DataSource dataSource) {
			
			super(dataSource, "PKG_CONSULTA.P_Get_Datos_Poliza");
			
			declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new DatosPolizaMapper()));
	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			compile();

		}

		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            WrapperResultados wrapperResultados = mapper.build(map);
            List result = (List) map.get("pv_registro_o");
            wrapperResultados.setItemList(result);
            return wrapperResultados;
		}
	}

	
    protected class DatosPolizaMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	
        	ConsultaDatosPolizaVO consulta = new ConsultaDatosPolizaVO();
        	
        	consulta.setNmsolici(rs.getString("nmsolici"));
        	consulta.setFeefecto(Utilerias.formateaFecha(rs.getString("feefecto")));
        	consulta.setNmpoliex(rs.getString("nmpoliex"));
        	consulta.setFeemisio(Utilerias.formateaFecha(rs.getString("feemisio")));
        	consulta.setCdmoneda(rs.getString("cdmoneda"));
        	consulta.setDsmoneda(rs.getString("dsmoneda"));
        	consulta.setOttempot(rs.getString("ottempot"));
        	consulta.setDstempot(rs.getString("dstempot"));
        	consulta.setFeproren(Utilerias.formateaFecha(rs.getString("feproren")));
        	consulta.setFevencim(Utilerias.formateaFecha(rs.getString("fevencim")));
        	consulta.setNmrenova(rs.getString("nmrenova"));
        	consulta.setCdperpag(rs.getString("cdperpag"));
        	consulta.setDsperpag(rs.getString("dsperpag"));
        	consulta.setSwtarifi(rs.getString("swtarifi"));
        	consulta.setDstarifi(rs.getString("dstarifi"));
        	consulta.setCdtipcoa(rs.getString("cdtipcoa"));
        	consulta.setDstipcoa(rs.getString("dstipcoa"));
        	consulta.setNmcuadro(rs.getString("nmcuadro"));
        	consulta.setDscuadro(rs.getString("dscuadro"));
        	consulta.setPorredau(rs.getString("porredau"));
        	consulta.setPtpritot(rs.getString("ptpritot"));
        	consulta.setCdmotanu(rs.getString("cdmotanu"));
        	consulta.setDsmotanu(rs.getString("dsmotanu"));
        	consulta.setCdperson(rs.getString("cdperson"));
        	consulta.setTitular(rs.getString("titular"));
        	consulta.setCdrfc(rs.getString("cdrfc"));
        	consulta.setCdagente(rs.getString("cdagente"));
        	
            return consulta;
        }
    }
    
    
    protected class ObtieneDatosSuplemento extends CustomStoredProcedure {
    	
    	protected ObtieneDatosSuplemento(DataSource dataSource) {
    		
    		super(dataSource, "PKG_CONSULTA.p_get_datos_suplem");
    		
    		declareParameter(new SqlParameter("pv_nmpoliex_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new DatosSuplementoMapper()));
    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
    		compile();
    	}
    	
    	public WrapperResultados mapWrapperResultados(Map map) throws Exception {
    		WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
    		WrapperResultados wrapperResultados = mapper.build(map);
    		List result = (List) map.get("pv_registro_o");
    		wrapperResultados.setItemList(result);
    		return wrapperResultados;
    	}
    }
    
    protected class DatosSuplementoMapper  implements RowMapper {
    	
    	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    		
    		ConsultaDatosSuplementoVO consulta = new ConsultaDatosSuplementoVO();
    		consulta.setCdunieco(rs.getString("cdunieco"));
    		consulta.setCdramo(rs.getString("cdramo"));
    		consulta.setEstado(rs.getString("estado"));
    		consulta.setEstado(rs.getString("estado"));
    		consulta.setNmpoliza(rs.getString("nmpoliza"));
    		consulta.setFeinival(Utilerias.formateaFecha(rs.getString("feinival")));
    		consulta.setNsuplogi(rs.getString("nsuplogi"));
    		consulta.setFeemisio(Utilerias.formateaFecha(rs.getString("feemisio")));
    		consulta.setNlogisus(rs.getString("nlogisus"));
    		consulta.setDstipsup(rs.getString("dstipsup"));
    		consulta.setPtpritot(rs.getString("ptpritot"));
    		consulta.setNmsuplem(rs.getString("nmsuplem"));
    		return consulta;
    	}
    }
    
    
    protected class ObtieneDatosSituacion extends CustomStoredProcedure {
    	
    	protected ObtieneDatosSituacion(DataSource dataSource) {
    		
    		super(dataSource, "PKG_CONSULTA.p_get_datos_situac");
    		
    		declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_estado_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_nmsuplem_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_nmsituac_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new DatosSituacionMapper()));
    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
    		compile();
    	}
    	
    	public WrapperResultados mapWrapperResultados(Map map) throws Exception {
    		WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
    		WrapperResultados wrapperResultados = mapper.build(map);
    		List result = (List) map.get("pv_registro_o");
    		wrapperResultados.setItemList(result);
    		return wrapperResultados;
    	}
    }
    
    
    protected class DatosSituacionMapper  implements RowMapper {
    	
    	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    		
    		ConsultaDatosSituacionVO consulta = new ConsultaDatosSituacionVO();
    		consulta.setNmsituac(rs.getString("nmsituac"));
    		consulta.setNmsuplem(rs.getString("nmsuplem"));
    		consulta.setCdtipsit(rs.getString("cdtipsit"));
    		consulta.setCdplan(rs.getString("cdplan"));
    		consulta.setFecharef(rs.getString("fecharef"));
    		consulta.setFefecsit(rs.getString("fefecsit"));
    		consulta.setStatus(rs.getString("status"));
    		consulta.setSwreduci(rs.getString("swreduci"));
    		consulta.setCdagrupa(rs.getString("cdagrupa"));
    		consulta.setCdestado(rs.getString("cdestado"));
    		consulta.setCdgrupo(rs.getString("cdgrupo"));
    		consulta.setNmsituaext(rs.getString("nmsituaext"));
    		consulta.setNmsitaux(rs.getString("nmsitaux"));
    		consulta.setNmsbsitext(rs.getString("nmsbsitext"));
    		consulta.setDsplan(rs.getString("dsplan"));
    		consulta.setInciso(rs.getString("inciso"));
    		consulta.setCduniage(rs.getString("cduniage"));
    		consulta.setCdelemento(rs.getString("cdelemento"));
    		consulta.setDstipsit(rs.getString("dstipsit"));
    		return consulta;
    	}
    }
    
    
    protected class ObtieneDatosCoberturas extends CustomStoredProcedure {
    	
    	protected ObtieneDatosCoberturas(DataSource dataSource) {
    
    		super(dataSource, "PKG_CONSULTA.P_Get_Datos_Cobert");
    		
    		declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_estado_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_nmsuplem_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_nmsituac_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new DatosCoberturasMapper()));
    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
    		compile();
    	}
    	
    	public WrapperResultados mapWrapperResultados(Map map) throws Exception {
    		WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
    		WrapperResultados wrapperResultados = mapper.build(map);
    		List result = (List) map.get("pv_registro_o");
    		wrapperResultados.setItemList(result);
    		return wrapperResultados;
    	}
    }
    
    
    protected class DatosCoberturasMapper  implements RowMapper {

    	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    	
    		ConsultaDatosCoberturasVO consulta = new ConsultaDatosCoberturasVO();
    		consulta.setNmsituac(rs.getString("nmsituac"));
    		consulta.setCdgarant(rs.getString("cdgarant"));
    		consulta.setDsgarant(rs.getString("dsgarant"));
    		consulta.setPtcapita(rs.getString("ptcapita"));
    		consulta.setCdcapita(rs.getString("cdcapita"));
    		consulta.setDscapita(rs.getString("dscapita"));
    		consulta.setCdtipfra(rs.getString("cdtipfra"));
    		consulta.setDstipfra(rs.getString("dstipfra"));
    		consulta.setPttasa(rs.getString("pttasa"));
    		return consulta;
    	}
    }

    
    protected class ObtienePolizasAsegurado extends CustomStoredProcedure {
    	
    	protected ObtienePolizasAsegurado(DataSource dataSource) {
    		
    		super(dataSource, "PKG_CONSULTA.P_Get_Polizas_Asegurado");
    		
    		declareParameter(new SqlParameter("pv_cdrfc", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new PolizaAseguradoMapper()));
    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
    		compile();
    	}
    	
    	public WrapperResultados mapWrapperResultados(Map map) throws Exception {
    		WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
    		WrapperResultados wrapperResultados = mapper.build(map);
    		List result = (List) map.get("pv_registro_o");
    		wrapperResultados.setItemList(result);
    		return wrapperResultados;
    	}
    }
    
    
    protected class PolizaAseguradoMapper  implements RowMapper {
    	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    		
    		ConsultaPolizaAseguradoVO polizaAsegurado = new ConsultaPolizaAseguradoVO();
    		polizaAsegurado.setCdramo(rs.getString("codigo_ramo"));
    		polizaAsegurado.setCdunieco(rs.getString("compania"));
    		polizaAsegurado.setDsramo(rs.getString("descripcion_ramo"));
    		polizaAsegurado.setDsunieco(rs.getString("descripcion"));
    		polizaAsegurado.setEstado(rs.getString("estado"));
    		polizaAsegurado.setNmpoliex(rs.getString("nmpoliex"));
    		polizaAsegurado.setNmpoliza(rs.getString("nmpoliza"));
    		polizaAsegurado.setNombreAsegurado(rs.getString("nombre"));
    		return polizaAsegurado;
    	}
    }
    

    protected class ObtieneDatosTarifa extends CustomStoredProcedure {
    	
    	protected ObtieneDatosTarifa(DataSource dataSource) {

    		super(dataSource, "PKG_CONSULTA.P_Get_Datos_Tarifa_Pol");
    		
    		declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_estado_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_nmsuplem_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new DatosTarifaMapper()));
    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
    		compile();
    	}
    	
    	public WrapperResultados mapWrapperResultados(Map map) throws Exception {
    		WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
    		WrapperResultados wrapperResultados = mapper.build(map);
    		List result = (List) map.get("pv_registro_o");
    		wrapperResultados.setItemList(result);
    		return wrapperResultados;
    	}
    }
    
    
    protected class DatosTarifaMapper  implements RowMapper {
    	
    	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    		
    		ConsultaDatosTarifaVO consulta = new ConsultaDatosTarifaVO();
    		consulta.setCdgarant(rs.getString("GARANTIA"));
    		consulta.setDsgarant(rs.getString("NOMBRE_GARANTIA"));
    		consulta.setSumaAsegurada(rs.getString("SUMA_ASEGURADA"));
    		consulta.setMontoPrima(rs.getString("MONTO_PRIMA"));
    		consulta.setMontoComision(rs.getString("MONTO_COMISION"));
    		return consulta;
    	}
    }

    
    protected class ObtienePolizasAgente extends CustomStoredProcedure {
    	
    	protected ObtienePolizasAgente(DataSource dataSource) {
    		
    		super(dataSource, "PKG_CONSULTA.P_Get_polizas_Agente");
    		
    		declareParameter(new SqlParameter("pv_cdagente_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new PolizasAgenteMapper()));
    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
    		compile();
    	}
    	
    	public WrapperResultados mapWrapperResultados(Map map) throws Exception {
    		WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
    		WrapperResultados wrapperResultados = mapper.build(map);
    		List result = (List) map.get("pv_registro_o");
    		wrapperResultados.setItemList(result);
    		return wrapperResultados;
    	}
    }

    
    protected class PolizasAgenteMapper  implements RowMapper {

    	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    		
    		ConsultaPolizaAgenteVO consulta = new ConsultaPolizaAgenteVO();
    		consulta.setCdunieco(rs.getString("cdunieco"));
    		consulta.setDsunieco(rs.getString("dsunieco"));
    		consulta.setCdramo(rs.getString("cdramo"));
    		consulta.setDsramo(rs.getString("dsramo"));
    		consulta.setNmpoliza(rs.getString("nmpoliza"));
    		consulta.setNmcuadro(rs.getString("nmcuadro"));
    		return consulta;
    	}
    }

    
    protected class ObtieneRecibosAgente extends CustomStoredProcedure {
    	
    	protected ObtieneRecibosAgente(DataSource dataSource) {
    
    		super(dataSource, "PKG_CONSULTA.P_Get_recibos_Agente");
    		
    		declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_estado_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new RecibosAgenteMapper()));
    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
    		compile();
    	}
    	
    	public WrapperResultados mapWrapperResultados(Map map) throws Exception {
    		WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
    		WrapperResultados wrapperResultados = mapper.build(map);
    		List result = (List) map.get("pv_registro_o");
    		wrapperResultados.setItemList(result);
    		return wrapperResultados;
    	}
    }
    
    
    protected class RecibosAgenteMapper  implements RowMapper {

    	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    		
    		ConsultaReciboAgenteVO consulta = new ConsultaReciboAgenteVO();
    		consulta.setNmrecibo(rs.getString("NMRECIBO"));
    		consulta.setFeinicio(Utilerias.formateaFecha(rs.getString("Fecha_inicio")));
    		consulta.setFefin(Utilerias.formateaFecha(rs.getString("Fecha_fin")));
    		consulta.setDsgarant(rs.getString("DSGARANT"));
    		consulta.setPtimport(rs.getString("PTIMPORT"));
    		return consulta;
    	}
    }
    
    
	protected class ObtieneDatosAgente extends CustomStoredProcedure {
		
		protected ObtieneDatosAgente(DataSource dataSource) {
			super(dataSource, "PKG_CONSULTA.P_Get_Datos_Agente");

			declareParameter(new SqlParameter("pv_cdagente_i", OracleTypes.VARCHAR));			
			declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new DatosAgenteMapper()));
	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
	        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			compile();
		}

		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            WrapperResultados wrapperResultados = mapper.build(map);
            List result = (List) map.get("pv_registro_o");
            wrapperResultados.setItemList(result);
            return wrapperResultados;
		}
	}

	
    protected class DatosAgenteMapper  implements RowMapper {
    
    	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	ConsultaDatosAgenteVO consulta = new ConsultaDatosAgenteVO();	        	
        	consulta.setCdagente(rs.getString("cdagente"));
        	consulta.setCdideper(rs.getString("cdideper"));
        	consulta.setNombre(rs.getString("nombre"));
        	consulta.setFedesde(Utilerias.formateaFecha(rs.getString("fedesde")));
        	return consulta;
        }
    }
    
    protected class ObtieneDatosAsegurado extends CustomStoredProcedure {
    	
    	protected ObtieneDatosAsegurado(DataSource dataSource) {
    		super(dataSource, "PKG_CONSULTA.P_Get_Datos_Aseg");
    		
    		declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_estado_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_nmsuplem_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new DatosDatosAsegurado()));
    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
    		compile();
    	}
    	
    	public WrapperResultados mapWrapperResultados(Map map) throws Exception {
    		WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
    		WrapperResultados wrapperResultados = mapper.build(map);
    		List result = (List) map.get("pv_registro_o");
    		wrapperResultados.setItemList(result);
    		return wrapperResultados;
    	}
    }

    
    protected class DatosDatosAsegurado  implements RowMapper {
    	
    	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    		
    		ConsultaDatosAseguradoVO consulta = new ConsultaDatosAseguradoVO();
    		consulta.setCdperson(rs.getString("cdperson"));
    		consulta.setCdrfc(rs.getString("cdrfc"));
    		consulta.setCdrol(rs.getString("cdrol"));
    		consulta.setDsrol(rs.getString("dsrol"));
    		consulta.setNmsituac(rs.getString("nmsituac"));
    		consulta.setTitular(rs.getString("titular"));
    		consulta.setFenacimi(Utilerias.formateaFecha(rs.getString("fenacimi")));
    		consulta.setSexo(rs.getString("Sexo"));
    		return consulta;
    	}
    }

    protected class ObtieneClausulas extends CustomStoredProcedure {
    	
    	protected ObtieneClausulas(DataSource dataSource) {
    		super(dataSource, "PKG_CONSULTA.P_obtiene_clausulas");
    		
    		declareParameter(new SqlParameter("pv_cdcla_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_descrip_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ClausulaMapper()));
    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
    		compile();
    	}
    	
    	public WrapperResultados mapWrapperResultados(Map map) throws Exception {
    		WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
    		WrapperResultados wrapperResultados = mapper.build(map);
    		List result = (List) map.get("pv_registro_o");
    		wrapperResultados.setItemList(result);
    		return wrapperResultados;
    	}
    }
    
    
    protected class ClausulaMapper  implements RowMapper {
    	
    	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    		
    		GenericVO consulta = new GenericVO();
    		consulta.setKey(rs.getString("cdclausu"));
    		consulta.setValue(rs.getString("dsclausu"));
    		
    		return consulta;
    	}
    }
    
}