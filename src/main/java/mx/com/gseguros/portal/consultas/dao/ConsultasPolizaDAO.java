package mx.com.gseguros.portal.consultas.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.aon.catweb.configuracion.producto.tablaCincoClaves.model.DescripcionCincoClavesVO;
import mx.com.aon.catweb.configuracion.producto.util.WizardUtils;
import mx.com.aon.portal.dao.AbstractDAO;
import mx.com.aon.portal.dao.CustomStoredProcedure;
import mx.com.aon.portal.dao.WrapperResultadosGeneric;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosCoberturasVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosPolizaVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosSituacionVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosSuplementoVO;
import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

public class ConsultasPolizaDAO extends AbstractDAO {

	private static Logger logger = Logger.getLogger(ConsultasPolizaDAO.class);

	public static final String OBTIENE_DATOS_POLIZA = "OBTIENE_DATOS_POLIZA";
	public static final String OBTIENE_DATOS_SUPLEMENTO = "OBTIENE_DATOS_SUPLEMENTO";
	public static final String OBTIENE_DATOS_SITUACION = "OBTIENE_DATOS_SITUACION";
	public static final String OBTIENE_DATOS_COBERTURAS = "OBTIENE_DATOS_COBERTURAS";
	
	protected void initDao() throws Exception {
		addStoredProcedure(OBTIENE_DATOS_POLIZA, new ObtieneDatosPoliza(getDataSource()));
		addStoredProcedure(OBTIENE_DATOS_SUPLEMENTO, new ObtieneDatosSuplemento(getDataSource()));
		addStoredProcedure(OBTIENE_DATOS_SITUACION, new ObtieneDatosSituacion(getDataSource()));
		addStoredProcedure(OBTIENE_DATOS_COBERTURAS, new ObtieneDatosCoberturas(getDataSource()));
	}

	protected class ObtieneDatosPoliza extends CustomStoredProcedure {

		protected ObtieneDatosPoliza(DataSource dataSource) {
			super(dataSource, "PKG_CONSULTA.P_Get_Datos_Poliza");

			declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdideper_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmclient_i", OracleTypes.VARCHAR));
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
        	
	        //logger.debug("Entrando a debug DAO HHHHHH: "+rs.getString("feemisio"));
        	consulta.setNmsolici(rs.getString("nmsolici"));
        	consulta.setFeefecto(rs.getString("feefecto"));
//        	consulta.setFeinival(rs.getString("feinival"));
//        	consulta.setNsuplogi(rs.getString("nsuplogi"));
//        	consulta.setFeemisio(rs.getString("feemisio"));
//        	consulta.setNlogisus(rs.getString("nlogisus"));
//        	consulta.setDstipsup(rs.getString("dstipsup"));
        	consulta.setCdmoneda(rs.getString("cdmoneda"));
        	consulta.setDsmoneda(rs.getString("dsmoneda"));
        	consulta.setOttempot(rs.getString("ottempot"));
        	consulta.setDstempot(rs.getString("dstempot"));
        	consulta.setFeproren(rs.getString("feproren"));
        	consulta.setFevencim(rs.getString("fevencim"));
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
        	
            return consulta;
        }
    }
    protected class ObtieneDatosSuplemento extends CustomStoredProcedure {
    	
    	protected ObtieneDatosSuplemento(DataSource dataSource) {
    		super(dataSource, "PKG_CONSULTA.p_get_datos_suplem");
    		
    		declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_estado_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.VARCHAR));
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
    		
    		consulta.setNmsuplem(rs.getString("nmsuplem"));
    		consulta.setFeinival(rs.getString("feinival"));
    		consulta.setNsuplogi(rs.getString("nsuplogi"));
    		consulta.setFeemisio(rs.getString("feemisio"));
    		consulta.setNlogisus(rs.getString("nlogisus"));
    		consulta.setDstipsup(rs.getString("dstipsup"));
    		consulta.setPtpritot(rs.getString("ptpritot"));
    		
    		
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
    
}
