package mx.com.gseguros.portal.catalogos.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.portal.catalogos.service.PersonasManager;
import mx.com.gseguros.portal.cotizacion.model.Item;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class PersonasAction extends PrincipalCoreAction
{

	private boolean                  exito            = false;
	private Map<String,Item>         imap             = null;
	private final Logger             logger           = Logger.getLogger(PersonasAction.class); 
	private PersonasManager          personasManager;
	private SimpleDateFormat         renderFechas     = new SimpleDateFormat("dd/MM/yyyy");
	private String                   respuesta        = null;
	private String                   respuestaOculta  = null;
	private static final long        serialVersionUID = -5438595581905207477L;
	private List<Map<String,String>> slist1;
	private Map<String,String>       smap1;
	private Map<String,String>       smap2;
	private Map<String,String>       smap3;
	
	/**
	 * Carga los elementos de la pantalla de asegurados
	 * @return SUCCESS/ERROR
	 */
	public String pantallaPersonas()
	{
		long timestamp = System.currentTimeMillis();
		String result  = null;
		logger.info(timestamp
				+ "\n##############################"
				+ "\n###### pantallaPersonas ######"
				);
		try
		{
			Map<String,Object>managerResult=personasManager.pantallaPersonas(obtenerCdsisrolSesion(),timestamp);
			exito           = (Boolean)managerResult.get("exito");
			respuesta       = (String)managerResult.get("respuesta");
			respuestaOculta = (String)managerResult.get("respuestaOculta");
			imap            = (Map<String,Item>)managerResult.get("itemMap");
			result          = SUCCESS;
		}
		catch(Exception ex)
		{
			logger.error(timestamp+" error inesperado al cargar pantalla de personas",ex);
			exito           = false;
			respuesta       = "Error inesperado #"+timestamp;
			respuestaOculta = ex.getMessage();
			result          = ERROR;
		}
		logger.info(timestamp
				+ "\n###### pantallaPersonas ######"
				+ "\n##############################"
				);
		return result;
	}
	
	/**
	 * Carga personas por rfc
	 * @return SUCCESS
	 */
	public String obtenerPersonasPorRFC()
	{
		long timestamp=System.currentTimeMillis();
		logger.info(timestamp
				+ "\n###################################"
				+ "\n###### obtenerPersonasPorRFC ######"
				+ "\nsmap1: "+smap1
				);
		try
		{
			Map<String,Object>managerResult=personasManager.obtenerPersonasPorRFC(smap1.get("rfc"),timestamp);
			exito           = (Boolean)managerResult.get("exito");
			respuesta       = (String)managerResult.get("respuesta");
			respuestaOculta = (String)managerResult.get("respuestaOculta");
			slist1          = (List<Map<String,String>>)managerResult.get("listaPersonas");
		}
		catch(Exception ex)
		{
			logger.error(timestamp+" error inesperado al obtener personas por RFC",ex);
			exito           = false;
			respuesta       = "Error inesperado #"+timestamp;
			respuestaOculta = ex.getMessage();
		}
		logger.info(timestamp
				+ "\n###### obtenerPersonasPorRFC ######"
				+ "\n###################################"
				);
		return SUCCESS;
	}
	
	/**
	 * Guarda los datos de la pantalla de personas
	 * @return SUCCESS
	 */
	public String guardarPantallaPersonas()
	{
		long timestamp = System.currentTimeMillis();
		logger.info(timestamp+""
				+ "\n#####################################"
				+ "\n###### guardarPantallaPersonas ######"
				+ "\nsmap1: "+smap1
				+ "\nsmap2: "+smap2
				+ "\nsmap2: "+smap3
				);
		try
		{
			Map<String,Object>managerResult=personasManager.guardarPantallaPersonas(
					smap1.get("CDPERSON")
					,null
					,smap1.get("CDIDEPER")
					,smap1.get("DSNOMBRE")
					,null
					,smap1.get("OTFISJUR")
					,smap1.get("OTSEXO")
					,renderFechas.parse(smap1.get("FENACIMI"))
					,smap1.get("CDRFC")
					,null
					,smap1.get("DSNOMBRE1")
					,smap1.get("DSAPELLIDO")
					,smap1.get("DSAPELLIDO1")
					,renderFechas.parse(smap1.get("FEINGRESO"))
					,smap1.get("CDNACION")
					,smap1.get("CANALING")
					,smap1.get("CONDUCTO")
					,smap1.get("PTCUMUPR")
					,smap1.get("RESIDENTE")
					,smap2.get("NMORDDOM")
					,smap2.get("DSDOMICI")
					,smap2.get("NMTELEFO")
					,smap2.get("CODPOSTAL")
					,smap2.get("CDEDO")
					,smap2.get("CDMUNICI")
					,smap2.get("CDCOLONI")
					,smap2.get("NMNUMERO")
					,smap2.get("NMNUMINT")
					,timestamp
					);
			exito                = (Boolean)managerResult.get("exito");
			respuesta            = (String)managerResult.get("respuesta");
			respuestaOculta      = (String)managerResult.get("respuestaOculta");
			String cdpersonNuevo = (String)managerResult.get("cdpersonNuevo");
			if(StringUtils.isNotBlank(cdpersonNuevo))
			{
				smap1.put("CDPERSON",cdpersonNuevo);
			}
		}
		catch(Exception ex)
		{
			logger.error(timestamp+" error inesperado al guardar pantalla de personas",ex);
			exito           = false;
			respuesta       = "Error inesperado #"+timestamp;
			respuestaOculta = ex.getMessage();
		}
		logger.info(timestamp+""
				+ "\n###### guardarPantallaPersonas ######"
				+ "\n#####################################"
				);
		return SUCCESS;
	}
	
	/**
	 * Obtener la direccion de una persona por su CDPERSON
	 * @return SUCCESS
	 */
	public String obtenerDomicilioPorCdperson()
	{
		long timestamp=System.currentTimeMillis();
		logger.info(timestamp+""
				+ "\n#########################################"
				+ "\n###### obtenerDomicilioPorCdperson ######"
				+ "\nsmap1: "+smap1
				);
		try
		{
			Map<String,Object> managerResult=personasManager.obtenerDomicilioPorCdperson(smap1.get("cdperson"),timestamp);
			exito           = (Boolean)managerResult.get("exito");
			respuesta       = (String)managerResult.get("respuesta");
			respuestaOculta = (String)managerResult.get("respuestaOculta");
			smap1           = (Map<String,String>)managerResult.get("domicilio");
		}
		catch(Exception ex)
		{
			logger.error(timestamp+" error inesperado al obtener domicilio por cdperson",ex);
			exito           = false;
			respuesta       = "Error inesperado #"+timestamp;
			respuestaOculta = ex.getMessage();
		}
		logger.info(timestamp+""
				+ "\n###### obtenerDomicilioPorCdperson ######"
				+ "\n#########################################"
				);
		return SUCCESS;
	}
	
	/**
	 * Obtener los items de tatriper y los valores de tvaloper para un cdperson
	 * @return SUCCESS
	 */
	public String obtenerTatriperTvaloperPorCdperson()
	{
		long timestamp=System.currentTimeMillis();
		logger.info(timestamp+""
				+ "\n################################################"
				+ "\n###### obtenerTatriperTvaloperPorCdperson ######"
				+ "\nsmap1: "+smap1
				);
		try
		{
			Map<String,Object> managerResult=personasManager.obtenerTatriperTvaloperPorCdperson(smap1.get("cdperson"),timestamp);
			exito           = (Boolean)managerResult.get("exito");
			respuesta       = (String)managerResult.get("respuesta");
			respuestaOculta = (String)managerResult.get("respuestaOculta");
			smap1.put("itemsTatriper"  , ((Item)managerResult.get("itemsTatriper")).toString());
			smap1.put("fieldsTatriper" , ((Item)managerResult.get("fieldsTatriper")).toString());
			smap2=(Map<String,String>)managerResult.get("tvaloper");
			
			Map<String,String>aux=new HashMap<String,String>();
			for(Entry<String,String>en:smap2.entrySet())
			{
				if(en.getKey().substring(0, 3).equalsIgnoreCase("OTV"))
				{
					aux.put("parametros.pv_otvalor"+en.getKey().substring("OTVALOR".length(), en.getKey().length()),en.getValue());
				}
			}
			smap2.putAll(aux);
		}
		catch(Exception ex)
		{
			logger.error(timestamp+" error inesperado al obtener tatriper y tvaloper por cdperson",ex);
			exito           = false;
			respuesta       = "Error inesperado #"+timestamp;
			respuestaOculta = ex.getMessage();
		}
		logger.info(timestamp+""
				+ "\n###### obtenerTatriperTvaloperPorCdperson ######"
				+ "\n################################################"
				);
		return SUCCESS;
	}
	
	/**
	 * Guarda los datos de tvaloper para un cdperson
	 * @return SUCCESS
	 */
	public String guardarDatosTvaloper()
	{
		long timestamp = System.currentTimeMillis();
		logger.info(timestamp+""
				+ "\n##################################"
				+ "\n###### guardarDatosTvaloper ######"
				+ "\nsmap1: "+smap1
				);
		try
		{
			Map<String,Object>managerResult=personasManager.guardarDatosTvaloper(
					smap1.get("cdperson")
					,smap1.get("parametros.pv_otvalor01")
					,smap1.get("parametros.pv_otvalor02")
					,smap1.get("parametros.pv_otvalor03")
					,smap1.get("parametros.pv_otvalor04")
					,smap1.get("parametros.pv_otvalor05")
					,smap1.get("parametros.pv_otvalor06")
					,smap1.get("parametros.pv_otvalor07")
					,smap1.get("parametros.pv_otvalor08")
					,smap1.get("parametros.pv_otvalor09")
					,smap1.get("parametros.pv_otvalor10")
					,smap1.get("parametros.pv_otvalor11")
					,smap1.get("parametros.pv_otvalor12")
					,smap1.get("parametros.pv_otvalor13")
					,smap1.get("parametros.pv_otvalor14")
					,smap1.get("parametros.pv_otvalor15")
					,smap1.get("parametros.pv_otvalor16")
					,smap1.get("parametros.pv_otvalor17")
					,smap1.get("parametros.pv_otvalor18")
					,smap1.get("parametros.pv_otvalor19")
					,smap1.get("parametros.pv_otvalor20")
					,smap1.get("parametros.pv_otvalor21")
					,smap1.get("parametros.pv_otvalor22")
					,smap1.get("parametros.pv_otvalor23")
					,smap1.get("parametros.pv_otvalor24")
					,smap1.get("parametros.pv_otvalor25")
					,smap1.get("parametros.pv_otvalor26")
					,smap1.get("parametros.pv_otvalor27")
					,smap1.get("parametros.pv_otvalor28")
					,smap1.get("parametros.pv_otvalor29")
					,smap1.get("parametros.pv_otvalor30")
					,smap1.get("parametros.pv_otvalor31")
					,smap1.get("parametros.pv_otvalor32")
					,smap1.get("parametros.pv_otvalor33")
					,smap1.get("parametros.pv_otvalor34")
					,smap1.get("parametros.pv_otvalor35")
					,smap1.get("parametros.pv_otvalor36")
					,smap1.get("parametros.pv_otvalor37")
					,smap1.get("parametros.pv_otvalor38")
					,smap1.get("parametros.pv_otvalor39")
					,smap1.get("parametros.pv_otvalor40")
					,smap1.get("parametros.pv_otvalor41")
					,smap1.get("parametros.pv_otvalor42")
					,smap1.get("parametros.pv_otvalor43")
					,smap1.get("parametros.pv_otvalor44")
					,smap1.get("parametros.pv_otvalor45")
					,smap1.get("parametros.pv_otvalor46")
					,smap1.get("parametros.pv_otvalor47")
					,smap1.get("parametros.pv_otvalor48")
					,smap1.get("parametros.pv_otvalor49")
					,smap1.get("parametros.pv_otvalor50")
					,timestamp
					);
			exito                = (Boolean)managerResult.get("exito");
			respuesta            = (String)managerResult.get("respuesta");
			respuestaOculta      = (String)managerResult.get("respuestaOculta");
		}
		catch(Exception ex)
		{
			logger.error(timestamp+" error inesperado al guardar datos de tvaloper",ex);
			exito           = false;
			respuesta       = "Error inesperado #"+timestamp;
			respuestaOculta = ex.getMessage();
		}
		logger.info(timestamp+""
				+ "\n###### guardarDatosTvaloper ######"
				+ "\n##################################"
				);
		return SUCCESS;
	}
	
	public String pantallaDocumentosPersona()
	{
		logger.info(""
				+ "\n###########################################"
				+ "\n######## pantallaDocumentosPersona ########"
				);
		logger.info(""
				+ "\n######## pantallaDocumentosPersona ########"
				+ "\n###########################################"
				);
		return SUCCESS;
	}
	
	public String cargarDocumentosPersona()
	{
		logger.info(""
				+ "\n#####################################"
				+ "\n###### cargarDocumentosPersona ######"
				+ "\nsmap1 "+smap1
				);
		try
		{
		    slist1=personasManager.cargarDocumentosPersona(smap1.get("cdperson"));
		}
		catch(Exception ex)
		{
			logger.error("error al obtener documentos de persona",ex);
			slist1=new ArrayList<Map<String,String>>();
		}
		logger.info(""
				+ "\n###### cargarDocumentosPersona ######"
				+ "\n#####################################"
				);
		return SUCCESS;
	}
	
	public String cargarNombreDocumentoPersona()
	{
		logger.info(""
				+ "\n##########################################"
				+ "\n###### cargarNombreDocumentoPersona ######"
				+ "\nsmap1 "+smap1
				);
		try
		{
			String nombre = personasManager.cargarNombreDocumentoPersona(smap1.get("cdperson"),smap1.get("codidocu"));
			if(StringUtils.isNotBlank(nombre))
			{
				smap1.put("cddocume",nombre);
				exito           = true;
				respuesta       = "Todo OK";
				respuestaOculta = "Todo OK";
			}
			else
			{
				exito           = false;
				respuesta       = "No hay documento";
				respuestaOculta = "Sin respuesta oculta";
			}
		}
		catch(Exception ex)
		{
			long timestamp=System.currentTimeMillis();
			logger.error("error al obtener nombre de archivo para persona #"+timestamp,ex);
			exito           = false;
			respuesta       = "Error al obtener archivo #"+timestamp;
			respuestaOculta = ex.getMessage();
		}
		logger.info(""
				+ "\n###### cargarNombreDocumentoPersona ######"
				+ "\n##########################################"
				);
		return SUCCESS;
	}
	
	private String obtenerCdsisrolSesion()
	{
		UserVO usuario=(UserVO)session.get("USUARIO");
		return usuario.getRolActivo().getObjeto().getValue();
	}
	
	/*
	 * GETTERS Y SETTERS
	 */
	public boolean isSuccess() {
		return true;
	}

	public boolean isExito() {
		return exito;
	}

	public void setExito(boolean exito) {
		this.exito = exito;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getRespuestaOculta() {
		return respuestaOculta;
	}

	public void setRespuestaOculta(String respuestaOculta) {
		this.respuestaOculta = respuestaOculta;
	}

	public void setPersonasManager(PersonasManager personasManager) {
		this.personasManager = personasManager;
	}

	public Map<String, Item> getImap() {
		return imap;
	}

	public void setImap(Map<String, Item> imap) {
		this.imap = imap;
	}

	public List<Map<String, String>> getSlist1() {
		return slist1;
	}

	public void setSlist1(List<Map<String, String>> slist1) {
		this.slist1 = slist1;
	}

	public Map<String, String> getSmap1() {
		return smap1;
	}

	public void setSmap1(Map<String, String> smap1) {
		this.smap1 = smap1;
	}

	public Map<String, String> getSmap2() {
		return smap2;
	}

	public void setSmap2(Map<String, String> smap2) {
		this.smap2 = smap2;
	}

	public Map<String, String> getSmap3() {
		return smap3;
	}

	public void setSmap3(Map<String, String> smap3) {
		this.smap3 = smap3;
	}
	
}