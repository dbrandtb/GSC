package mx.com.gseguros.portal.catalogos.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.aon.portal.dao.ObtieneTatriperMapper;
import mx.com.gseguros.portal.catalogos.dao.PersonasDAO;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.dao.impl.DinamicMapper;
import mx.com.gseguros.portal.dao.impl.GenericMapper;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import oracle.jdbc.driver.OracleTypes;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class PersonasDAOImpl extends AbstractManagerDAO implements PersonasDAO
{
	
	private final Logger logger = Logger.getLogger(PersonasDAOImpl.class);
	
	/**
	 * Obtiene personas por RFC de PKG_CONSULTA.P_GET_MPERSONA
	 * @param rfc
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Map<String,String>>obtenerPersonasPorRFC(Map<String,String>params) throws Exception
	{
		Map<String, Object> resultado = ejecutaSP(new ObtenerPersonasPorRFC(getDataSource()), params);
		List<Map<String,String>>listaPersonas=(List<Map<String,String>>)resultado.get("pv_registro_o");
		if(listaPersonas==null)
		{
			listaPersonas=new ArrayList<Map<String,String>>();
		}
		logger.debug("listaPersonas size: "+listaPersonas.size());
		return listaPersonas;
	}
	
	protected class ObtenerPersonasPorRFC extends StoredProcedure
	{
    	protected ObtenerPersonasPorRFC(DataSource dataSource) {
            super(dataSource,"PKG_CONSULTA.P_GET_MPERSONA");
            declareParameter(new SqlParameter("pv_cdrfc_i",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_dsnombre_i",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_dsnombre1_i",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_dsapellido_i",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_dsapellido1_i",OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
	@Override
	public Map<String,String>obtenerPersonaPorCdperson(Map<String,String>params) throws Exception
	{
		Map<String, Object> resultado         = ejecutaSP(new ObtenerPersonaPorCdperson(getDataSource()), params);
		List<Map<String,String>>listaPersonas = (List<Map<String,String>>)resultado.get("pv_registro_o");
		if(listaPersonas==null||listaPersonas.size()==0)
		{
			throw new Exception("No se encuentra la persona");
		}
		logger.debug("listaPersonas size: "+listaPersonas.size());
		return listaPersonas.get(0);
	}
	
	protected class ObtenerPersonaPorCdperson extends StoredProcedure
	{
    	protected ObtenerPersonaPorCdperson(DataSource dataSource) {
            super(dataSource,"PKG_CONSULTA.P_GET_MPERSONA_X_CDPERSON");
            declareParameter(new SqlParameter("pv_cdperson_i" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
	
	/**
	 * Guarda mpersona con PKG_SATELITES.P_MOV_MPERSONA
	 */
	@Override
	public void movimientosMpersona(String cdperson
			,String cdtipide
			,String cdideper
			,String dsnombre
			,String cdtipper
			,String otfisjur
			,String otsexo
			,Date   fenacimi
			,String cdrfc
			,String dsemail
			,String dsnombre1
			,String dsapellido
			,String dsapellido1
			,Date   feingreso
			,String cdnacion
			,String canaling
			,String conducto
			,String ptcumupr
			,String residencia
			,String accion) throws Exception
	{
		Map<String,Object>params=new HashMap<String,Object>();
		params.put("cdperson"    , cdperson);
		params.put("cdtipide"    , cdtipide);
		params.put("cdideper"    , cdideper);
		params.put("dsnombre"    , dsnombre);
		params.put("cdtipper"    , cdtipper);
		params.put("otfisjur"    , otfisjur);
		params.put("otsexo"      , otsexo);
		params.put("fenacimi"    , fenacimi);
		params.put("cdrfc"       , cdrfc);
		params.put("dsemail"     , dsemail);
		params.put("dsnombre1"   , dsnombre1);
		params.put("dsapellido"  , dsapellido);
		params.put("dsapellido1" , dsapellido1);
		params.put("feingreso"   , feingreso);
		params.put("cdnacion"    , cdnacion);
		params.put("canaling"    , canaling);
		params.put("conducto"    , conducto);
		params.put("ptcumupr"    , ptcumupr);
		params.put("residencia"  , residencia);
		params.put("accion"      , accion);
		ejecutaSP(new MovimientosMpersona(getDataSource()), params);
	}
	
	protected class MovimientosMpersona extends StoredProcedure
	{
    	protected MovimientosMpersona(DataSource dataSource) {
            super(dataSource,"PKG_SATELITES.P_MOV_MPERSONA");
    		declareParameter(new SqlParameter("cdperson"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipide"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdideper"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("dsnombre"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipper"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otfisjur"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otsexo"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("fenacimi"    , OracleTypes.DATE));
			declareParameter(new SqlParameter("cdrfc"       , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("dsemail"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("dsnombre1"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("dsapellido"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("dsapellido1" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("feingreso"   , OracleTypes.DATE));
			declareParameter(new SqlParameter("cdnacion"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("canaling"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("conducto"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("ptcumupr"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("residencia"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("accion"      , OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
	
	/**
	 * obtener domicilio por cdperson desde PKG_CONSULTA.P_GET_MDOMICIL
	 */
	@Override
	public Map<String,String> obtenerDomicilioPorCdperson(String cdperson) throws Exception
	{
		Map<String,String>domicilio = null;
		Map<String,String>params    = new HashMap<String,String>();
		params.put("cdperson",cdperson);
		Map<String, Object> resultado = ejecutaSP(new ObtenerDomicilioPorCdperson(getDataSource()), params);
		List<Map<String,String>>listaDomicilios=(List<Map<String,String>>)resultado.get("pv_registro_o");
		if(listaDomicilios==null)
		{
			listaDomicilios=new ArrayList<Map<String,String>>();
		}
		if(listaDomicilios.size()>0)
		{
			domicilio=listaDomicilios.get(0);
		}
		logger.debug("domicilio: "+domicilio);
		return domicilio;
	}
	
	protected class ObtenerDomicilioPorCdperson extends StoredProcedure
	{
    	protected ObtenerDomicilioPorCdperson(DataSource dataSource) {
            super(dataSource,"PKG_CONSULTA.P_GET_MDOMICIL");
    		declareParameter(new SqlParameter("cdperson"    , OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
    		declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
	
	/**
	 * Obtener nuevo cdperson de PKG_SATELITES.P_GEN_CDPERSON
	 */
	@Override
	public String obtenerNuevoCdperson() throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		Map<String, Object> resultado = ejecutaSP(new ObtenerNuevoCdperson(getDataSource()), params);
		String cdperson = (String)resultado.get("pv_cdperson_o");
		if(StringUtils.isBlank(cdperson))
		{
			throw new Exception("Error al generar clave de persona");
		}
		return cdperson;
	}
	protected class ObtenerNuevoCdperson extends StoredProcedure
	{
    	protected ObtenerNuevoCdperson(DataSource dataSource) {
            super(dataSource,"PKG_SATELITES.P_GEN_CDPERSON");
            declareParameter(new SqlOutParameter("pv_cdperson_o" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
	
	/**
	 * movimientos de domicilio por cdperson de PKG_SATELITES.P_MOV_MDOMICIL
	 */
	@Override
	public void movimientosMdomicil(String cdperson
			,String nmorddom
			,String dsdomici
			,String nmtelefo
			,String cdpostal
			,String cdedo
			,String cdmunici
			,String cdcoloni
			,String nmnumero
			,String nmnumint
			,String accion) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdperson" , cdperson);
		params.put("nmorddom" , nmorddom);
		params.put("dsdomici" , dsdomici);
		params.put("nmtelefo" , nmtelefo);
		params.put("cdpostal" , cdpostal);
		params.put("cdedo"    , cdedo);
		params.put("cdmunici" , cdmunici);
		params.put("cdcoloni" , cdcoloni);
		params.put("nmnumero" , nmnumero);
		params.put("nmnumint" , nmnumint);
		params.put("accion"   , accion);
		ejecutaSP(new MovimientosMdomicil(getDataSource()), params);
	}
	
	protected class MovimientosMdomicil extends StoredProcedure
	{
    	protected MovimientosMdomicil(DataSource dataSource) {
            super(dataSource,"PKG_SATELITES.P_MOV_MDOMICIL");
    		declareParameter(new SqlParameter("cdperson"       , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmorddom"       , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("dsdomici"       , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmtelefo"       , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdpostal"       , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdedo"          , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdmunici"       , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdcoloni"       , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmnumero"       , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmnumint"       , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("accion"         , OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
            compile();
    	}
    }
	
	/**
	 * Obtener los componentes de tatriper por cdrol y cdperson de PKG_LISTAS.P_GET_ATRI_PER
	 */
	@Override
	public List<ComponenteVO> obtenerAtributosPersona(String cdperson) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdperson" , cdperson);
		Map<String,Object>resultado = ejecutaSP(new ObtenerAtributosPersona(getDataSource()), params);
		List<ComponenteVO>atributos=(List<ComponenteVO>) resultado.get("pv_registro_o");
		if(atributos==null)
		{
			atributos=new ArrayList<ComponenteVO>();
		}
		logger.info("obtenerAtributosPersona lista size: "+atributos.size());
		return atributos;
	}
	
	protected class ObtenerAtributosPersona extends StoredProcedure
	{
    	protected ObtenerAtributosPersona(DataSource dataSource) {
            super(dataSource,"PKG_LISTAS.P_GET_ATRIPER");
			declareParameter(new SqlParameter("cdperson" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new ObtieneTatriperMapper()));
    		declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
	
	/**
	 * Obtiene los valores de tvaloper para un cdperson y un cdrol de PKG_CONSULTA.P_GET_TVALOPER
	 */
	@Override
	public Map<String,String> obtenerTvaloper(String cdrol,String cdperson) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdrol"    , cdrol);
		params.put("cdperson" , cdperson);
		Map<String,Object>respuesta=ejecutaSP(new ObtenerTvaloper(getDataSource()), params);
		List<Map<String,String>>listaTvaloper=(List<Map<String,String>>)respuesta.get("pv_registro_o");
		Map<String,String>tvaloper=new HashMap<String,String>();
		if(listaTvaloper!=null&&listaTvaloper.size()>0)
		{
			tvaloper=listaTvaloper.get(0);
		}
		logger.info("obtenerTvaloper map: "+tvaloper);
		return tvaloper;
	}
	
	protected class ObtenerTvaloper extends StoredProcedure
	{
    	protected ObtenerTvaloper(DataSource dataSource) {
            super(dataSource,"PKG_CONSULTA.P_GET_TVALOPER");
			declareParameter(new SqlParameter("cdrol"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdperson" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
    		declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
	
	/**
	 * Movimientos de tvaloper por cdperson de PKG_SATELITES.P_MOV_TVALOPER_NUEVO
	 */
	@Override
	public void movimientosTvaloper(String cdrol, String cdperson
			,String otvalor01,String otvalor02,String otvalor03,String otvalor04,String otvalor05
			,String otvalor06,String otvalor07,String otvalor08,String otvalor09,String otvalor10
			,String otvalor11,String otvalor12,String otvalor13,String otvalor14,String otvalor15
			,String otvalor16,String otvalor17,String otvalor18,String otvalor19,String otvalor20
			,String otvalor21,String otvalor22,String otvalor23,String otvalor24,String otvalor25
			,String otvalor26,String otvalor27,String otvalor28,String otvalor29,String otvalor30
			,String otvalor31,String otvalor32,String otvalor33,String otvalor34,String otvalor35
			,String otvalor36,String otvalor37,String otvalor38,String otvalor39,String otvalor40
			,String otvalor41,String otvalor42,String otvalor43,String otvalor44,String otvalor45
			,String otvalor46,String otvalor47,String otvalor48,String otvalor49,String otvalor50
			)throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdrol"        , cdrol);
		params.put("cdperson"     , cdperson);
		params.put("pv_otvalor01" , otvalor01);
		params.put("pv_otvalor02" , otvalor02);
		params.put("pv_otvalor03" , otvalor03);
		params.put("pv_otvalor04" , otvalor04);
		params.put("pv_otvalor05" , otvalor05);
		params.put("pv_otvalor06" , otvalor06);
		params.put("pv_otvalor07" , otvalor07);
		params.put("pv_otvalor08" , otvalor08);
		params.put("pv_otvalor09" , otvalor09);
		params.put("pv_otvalor10" , otvalor10);
		params.put("pv_otvalor11" , otvalor11);
		params.put("pv_otvalor12" , otvalor12);
		params.put("pv_otvalor13" , otvalor13);
		params.put("pv_otvalor14" , otvalor14);
		params.put("pv_otvalor15" , otvalor15);
		params.put("pv_otvalor16" , otvalor16);
		params.put("pv_otvalor17" , otvalor17);
		params.put("pv_otvalor18" , otvalor18);
		params.put("pv_otvalor19" , otvalor19);
		params.put("pv_otvalor20" , otvalor20);
		params.put("pv_otvalor21" , otvalor21);
		params.put("pv_otvalor22" , otvalor22);
		params.put("pv_otvalor23" , otvalor23);
		params.put("pv_otvalor24" , otvalor24);
		params.put("pv_otvalor25" , otvalor25);
		params.put("pv_otvalor26" , otvalor26);
		params.put("pv_otvalor27" , otvalor27);
		params.put("pv_otvalor28" , otvalor28);
		params.put("pv_otvalor29" , otvalor29);
		params.put("pv_otvalor30" , otvalor30);
		params.put("pv_otvalor31" , otvalor31);
		params.put("pv_otvalor32" , otvalor32);
		params.put("pv_otvalor33" , otvalor33);
		params.put("pv_otvalor34" , otvalor34);
		params.put("pv_otvalor35" , otvalor35);
		params.put("pv_otvalor36" , otvalor36);
		params.put("pv_otvalor37" , otvalor37);
		params.put("pv_otvalor38" , otvalor38);
		params.put("pv_otvalor39" , otvalor39);
		params.put("pv_otvalor40" , otvalor40);
		params.put("pv_otvalor41" , otvalor41);
		params.put("pv_otvalor42" , otvalor42);
		params.put("pv_otvalor43" , otvalor43);
		params.put("pv_otvalor44" , otvalor44);
		params.put("pv_otvalor45" , otvalor45);
		params.put("pv_otvalor46" , otvalor46);
		params.put("pv_otvalor47" , otvalor47);
		params.put("pv_otvalor48" , otvalor48);
		params.put("pv_otvalor49" , otvalor49);
		params.put("pv_otvalor50" , otvalor50);
		ejecutaSP(new MovimientosTvaloper(getDataSource()), params);
	}
	
	protected class MovimientosTvaloper extends StoredProcedure
	{
    	protected MovimientosTvaloper(DataSource dataSource)
    	{
            super(dataSource,"PKG_SATELITES.P_MOV_TVALOPER_NUEVO");
            declareParameter(new SqlParameter("cdrol"        , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdperson"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor01" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor02" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor03" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor04" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor05" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor06" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor07" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor08" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor09" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor10" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor11" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor12" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor13" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor14" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor15" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor16" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor17" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor18" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor19" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor20" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor21" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor22" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor23" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor24" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor25" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor26" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor27" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor28" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor29" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor30" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor31" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor32" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor33" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor34" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor35" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor36" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor37" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor38" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor39" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor40" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor41" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor42" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor43" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor44" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor45" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor46" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor47" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor48" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor49" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor50" , OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
            compile();
    	}
    }
	
	@Override
	public List<Map<String,String>>cargarDocumentosPersona(Map<String,String> params)throws Exception
	{
		Map<String,Object>resultado=ejecutaSP(new CargarDocumentosPersona(getDataSource()), params);
		return (List<Map<String,String>>)resultado.get("pv_registro_o");
	}
	
	protected class CargarDocumentosPersona extends StoredProcedure
	{
		private String columnas[]=new String[]{
				"cddocume"
				,"dsdocume"
				,"cdperson"
				,"feinici"
				,"liga"
		};
		
    	protected CargarDocumentosPersona(DataSource dataSource)
    	{
            super(dataSource,"PKG_CONSULTA.P_GET_DOCUMENTOS_PERSONA");
            declareParameter(new SqlParameter("cdperson" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(columnas)));
    		declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
	
	
	@Override
	public List<Map<String,String>>obtieneAccionistas(Map<String,String> params)throws Exception
	{
		Map<String,Object>resultado=ejecutaSP(new ObtieneAccionistas(getDataSource()), params);
		return (List<Map<String,String>>)resultado.get("pv_registro_o");
	}
	
	protected class ObtieneAccionistas extends StoredProcedure
	{
		
		protected ObtieneAccionistas(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_ESTRUC_CORPORATIVA");
			declareParameter(new SqlParameter("pv_cdperson_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdatribu_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtpesco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void validarDocumentosPersona(Map<String,String> params)throws Exception
	{
		ejecutaSP(new ValidarDocumentosPersona(getDataSource()), params);
	}
	
	protected class ValidarDocumentosPersona extends StoredProcedure
	{
    	protected ValidarDocumentosPersona(DataSource dataSource)
    	{
            super(dataSource,"PKG_SATELITES.P_VALIDA_DOCTOS_OBLIGATORIOS");
            declareParameter(new SqlParameter("cdperson" , OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
            compile();
    	}
    }
	
	@Override
	public String cargarNombreDocumentoPersona(Map<String,String>params)throws Exception
	{
		Map<String,Object>resultado=ejecutaSP(new CargarNombreDocumentoPersona(getDataSource()), params);
		return (String)resultado.get("pv_cddocume_o");
	}
	
	protected class CargarNombreDocumentoPersona extends StoredProcedure
	{
    	protected CargarNombreDocumentoPersona(DataSource dataSource)
    	{
            super(dataSource,"PKG_CONSULTA.P_GET_NOMBRE_CDDOCUME_PERSONA");
            declareParameter(new SqlParameter("cdperson" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("codidocu" , OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_cddocume_o" , OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }

	@Override
	public String guardaAccionista(Map<String,String>params)throws Exception
	{
		Map<String,Object>resultado=ejecutaSP(new GuardaAccionista(getDataSource()), params);
		return (String)resultado.get("pv_msg_id_o");
	}
	
	protected class GuardaAccionista extends StoredProcedure
	{
		protected GuardaAccionista(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES.P_MOV_TESCOPER");
			declareParameter(new SqlParameter("pv_cdperson_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdatribu_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtpesco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmordina_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_dsnombre_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdnacion_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_porparti_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_accion_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public String eliminaAccionistas(Map<String,String>params)throws Exception
	{
		Map<String,Object>resultado=ejecutaSP(new EliminaAccionistas(getDataSource()), params);
		return (String)resultado.get("pv_msg_id_o");
	}
	
	protected class EliminaAccionistas extends StoredProcedure
	{
		protected EliminaAccionistas(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES.P_ELIMINA_TESCOPER");
			declareParameter(new SqlParameter("pv_cdperson_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdatribu_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public String actualizaStatusPersona(Map<String,String>params)throws Exception
	{
		Map<String,Object>resultado=ejecutaSP(new ActualizaStatusPersona(getDataSource()), params);
		return (String)resultado.get("pv_dsstatus_o");
	}
	
	protected class ActualizaStatusPersona extends StoredProcedure
	{
		protected ActualizaStatusPersona(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES.P_ACTUALIZA_STATUS_PERSONA");
			declareParameter(new SqlParameter("pv_cdperson_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_dsstatus_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
}