package mx.com.gseguros.portal.catalogos.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.catalogos.dao.PersonasDAO;
import mx.com.gseguros.portal.catalogos.service.PersonasManager;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.general.dao.PantallasDAO;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.portal.general.util.Ramo;
import mx.com.gseguros.portal.general.util.TipoSituacion;
import mx.com.gseguros.utils.Constantes;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

public class PersonasManagerImpl implements PersonasManager
{
	private final Logger logger = Logger.getLogger(PersonasManagerImpl.class);
	private PantallasDAO pantallasDAO;
	private PersonasDAO  personasDAO;
	
	/**
	 * Carga los componentes de la pantalla de personas
	 * @return exito,respuesta,respuestaOculta,itemMap
	 */
	@Override
	public Map<String,Object> pantallaPersonas(String cdsisrol,long timestamp) throws Exception
	{
		Map<String,Object>result=new HashMap<String,Object>();
		logger.info(timestamp
				+ "\n##############################"
				+ "\n###### pantallaPersonas ######"
				+ "\ncdsisrol: "+cdsisrol
				);
		boolean          exito           = true;
		String           respuesta       = null;
		String           respuestaOculta = null;
		String           pantalla        = "PANTALLA_PERSONAS";
		GeneradorCampos  gc              = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
		Map<String,Item> itemMap         = new HashMap<String,Item>();
		
		//componentes de busqueda
		if(exito)
		{
			try
			{
				String seccion = "BUSQUEDA";
				List<ComponenteVO>busqueda=pantallasDAO.obtenerComponentes(null,null,null,null,null,cdsisrol,pantalla,seccion,null);
				gc.generaComponentes(busqueda, true, false, true, false, false, false);
				itemMap.put(seccion,gc.getItems());
			}
			catch(Exception ex)
			{
				logger.error(timestamp+" error al obtener componentes de pantalla",ex);
				exito           = false;
				respuesta       = "Error al obtener componentes de pantalla #"+timestamp;
				respuestaOculta = ex.getMessage();
			}
		}
		
		//modelo y columnas del grid
		if(exito)
		{
			try
			{
				String seccion = "MODELO_Y_GRID";
				List<ComponenteVO>fieldsYColumns=pantallasDAO.obtenerComponentes(null,null,null,null,null,cdsisrol,pantalla,seccion,null);
				gc.generaComponentes(fieldsYColumns, true,true,true,true,false,false);
				itemMap.put("gridModelFields"     , gc.getFields());
				itemMap.put("gridColumns"         , gc.getColumns());
				itemMap.put("datosGeneralesItems" , gc.getItems());
			}
			catch(Exception ex)
			{
				logger.error(timestamp+" error al obtener componentes de pantalla",ex);
				exito           = false;
				respuesta       = "Error al obtener componentes de pantalla #"+timestamp;
				respuestaOculta = ex.getMessage();
			}
		}
		
		if(exito)
		{
			try
			{
				String seccion = "DOMICILIO";
				List<ComponenteVO>componentesDomicilio=pantallasDAO.obtenerComponentes(
						null,null,null,null,null,cdsisrol,pantalla,seccion,null);
				gc.generaComponentes(componentesDomicilio, true,true,true,false,false,false);
				itemMap.put("fieldsDomicilio" , gc.getFields());
				itemMap.put("itemsDomicilio"  , gc.getItems());
			}
			catch(Exception ex)
			{
				logger.error(timestamp+" error al obtener campos de domicilio",ex);
				exito           = false;
				respuesta       = "Error al obtener componentes de pantalla #"+timestamp;
				respuestaOculta = ex.getMessage();
			}
		}
		
		if(exito)
		{
			respuesta       = "Todo OK";
			respuestaOculta = "Todo OK";
		}
		
		result.put("exito"           , exito);
		result.put("respuesta"       , respuesta);
		result.put("respuestaOculta" , respuestaOculta);
		result.put("itemMap"         , itemMap);
		logger.info(timestamp
				+ "\nresult: "+result
				+ "\n###### pantallaPersonas ######"
				+ "\n##############################"
				);
		return result;
	}
	
	/**
	 * Buscar personas por RFC de PKG_CONSULTA.P_GET_MPERSONA
	 * @return exito,respuesta,respuestaOculta,listaPersonas
	 */
	@Override
	public Map<String,Object> obtenerPersonasPorRFC(String rfc,long timestamp) throws Exception
	{
		Map<String,Object>result=new HashMap<String,Object>();
		logger.info(timestamp
				+ "\n###################################"
				+ "\n###### obtenerPersonasPorRFC ######"
				+ "\nrfc: "+rfc
				);
		boolean exito                         = true;
		String  respuesta                     = null;
		String  respuestaOculta               = null;
		List<Map<String,String>>listaPersonas = null;
		
		if(exito)
		{
			try
			{
				listaPersonas=personasDAO.obtenerPersonasPorRFC(rfc);
			}
			catch(Exception ex)
			{
				logger.error(timestamp+" error al obtener personas por rfc",ex);
				exito           = false;
				respuesta       = "Error al obtener personas #"+timestamp;
				respuestaOculta = ex.getMessage();
				listaPersonas   = new ArrayList<Map<String,String>>();
			}
		}
		
		if(exito)
		{
			respuesta       = "Todo OK";
			respuestaOculta = "Todo OK";
		}
		
		result.put("exito"           , exito);
		result.put("respuesta"       , respuesta);
		result.put("respuestaOculta" , respuestaOculta);
		result.put("listaPersonas"   , listaPersonas);
		logger.info(timestamp+""
				+ "\nresult"+result
				+ "\n###### obtenerPersonasPorRFC ######"
				+ "\n###################################"
				);
		return result;
	}
	
	/**
	 * Guardar pantalla de personas
	 * @return exito,respuesta,respuestaOculta,cdpersonNuevo
	 */
	@Override
	public Map<String,Object> guardarPantallaPersonas(String cdperson
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
			,String nmorddom
			,String dsdomici
			,String nmtelefo
			,String cdpostal
			,String cdedo
			,String cdmunici
			,String cdcoloni
			,String nmnumero
			,String nmnumint
			,long   timestamp) throws Exception
	{
		Map<String,Object>result=new HashMap<String,Object>();
		logger.info(timestamp+""
				+ "\n#####################################"
				+ "\n###### guardarPantallaPersonas ######"
				+ "\n cdperson:"+cdperson
				+ "\n cdtipide:"+cdtipide
				+ "\n cdideper:"+cdideper
				+ "\n dsnombre:"+dsnombre
				+ "\n cdtipper:"+cdtipper
				+ "\n otfisjur:"+otfisjur
				+ "\n otsexo:"+otsexo
				+ "\n fenacimi:"+fenacimi
				+ "\n cdrfc:"+cdrfc
				+ "\n dsemail:"+dsemail
				+ "\n dsnombre1:"+dsnombre1
				+ "\n dsapellido:"+dsapellido
				+ "\n dsapellido1:"+dsapellido1
				+ "\n feingreso:"+feingreso
				+ "\n cdnacion:"+cdnacion
				+ "\n canaling:"+canaling
				+ "\n conducto:"+conducto
				+ "\n ptcumupr:"+ptcumupr
				+ "\n residencia:"+residencia
				+ "\n nmorddom:"+nmorddom
				+ "\n dsdomici:"+dsdomici
				+ "\n nmtelefo:"+nmtelefo
				+ "\n cdpostal:"+cdpostal
				+ "\n cdedo:"+cdedo
				+ "\n cdmunici:"+cdmunici
				+ "\n cdcoloni:"+cdcoloni
				+ "\n nmnumero:"+nmnumero
				+ "\n nmnumint:"+nmnumint
				);
		
		boolean exito           = true;
		String  respuesta       = null;
		String  respuestaOculta = null;
		String  cdpersonNuevo   = null;
		
		if(exito&&StringUtils.isBlank(cdperson))
		{
			try
			{
				cdperson      = personasDAO.obtenerNuevoCdperson();
				cdpersonNuevo = cdperson;
			}
			catch(Exception ex)
			{
				logger.error(timestamp+" error al obtener cdperson",ex);
				exito           = false;
				respuesta       = ex.getMessage()+" #"+timestamp;
				respuestaOculta = "sin respuesta oculta";
			}
		}
		
		if(exito)
		{
			try
			{
				personasDAO.movimientosMpersona(
						cdperson, cdtipide, cdideper,
						dsnombre, cdtipper, otfisjur,
						otsexo, fenacimi, cdrfc,
						dsemail, dsnombre1, dsapellido,
						dsapellido1, feingreso, cdnacion,
						canaling, conducto, ptcumupr,
						residencia, Constantes.INSERT_MODE);
			}
			catch(Exception ex)
			{
				logger.error(timestamp+" error en el movimiento de persona",ex);
				exito           = false;
				respuesta       = ex.getMessage()+" #"+timestamp;
				respuestaOculta = "sin respuesta oculta";
			}
		}
		
		if(exito)
		{
			try
			{
				personasDAO.movimientosMdomicil(
						cdperson, nmorddom, dsdomici,
						nmtelefo, cdpostal, cdedo,
						cdmunici, cdcoloni, nmnumero,
						nmnumint, Constantes.INSERT_MODE);
			}
			catch(Exception ex)
			{
				logger.error(timestamp+" error en el movimiento de domicilio",ex);
				exito           = false;
				respuesta       = ex.getMessage()+" #"+timestamp;
				respuestaOculta = "sin respuesta oculta";
			}
		}
		
		if(exito)
		{
			respuesta       = "Datos guardados correctamente";
			respuestaOculta = "Sin respuesta oculta";
		}
		
		result.put("exito"           , exito);
		result.put("respuesta"       , respuesta);
		result.put("respuestaOculta" , respuestaOculta);
		result.put("cdpersonNuevo"   , cdpersonNuevo);
		logger.info(timestamp+""
				+ "\nresult: "+result
				+ "\n###### guardarPantallaPersonas ######"
				+ "\n#####################################"
				);
		return result;
	}

	/**
	 * Obtener el domicilio de una persona por su cdperson de PKG_CONSULTA.P_GET_MDOMICIL
	 * @return exito,respuesta,respuestaOculta,domicilio
	 */
	public Map<String,Object> obtenerDomicilioPorCdperson(String cdperson,long timestamp) throws Exception
	{
		Map<String,Object>result = new HashMap<String,Object>();
		logger.info(timestamp+""
				+ "\n#########################################"
				+ "\n###### obtenerDomicilioPorCdperson ######"
				);
		boolean exito               = true;
		String  respuesta           = null;
		String  respuestaOculta     = null;
		Map<String,String>domicilio = null;
		
		if(exito)
		{
			try
			{
				domicilio = personasDAO.obtenerDomicilioPorCdperson(cdperson);
			}
			catch(Exception ex)
			{
				logger.error(timestamp+" error al obtener domicilio por cdperson",ex);
				exito           = false;
				respuesta       = "No se encontr&oacute; domicilio anterior #"+timestamp;
				respuestaOculta = ex.getMessage();
			}
		}
		
		if(exito)
		{
			respuesta       = "Todo OK";
			respuestaOculta = "Todo OK";
		}
		
		result.put("exito"           , exito);
		result.put("respuesta"       , respuesta);
		result.put("respuestaOculta" , respuestaOculta);
		result.put("domicilio"       , domicilio);
		logger.info(timestamp+""
				+ "\nresult: "+result
				+ "\n###### obtenerDomicilioPorCdperson ######"
				+ "\n#########################################"
				);
		return result;
	}
	
	/**
	 * Obtener los items de tatriper y los valores de tvaloper para un cdperson de PKG_LISTA.P_GET_ATRI_PER y PKG_CONSULTA.P_GET_TVALOPER
	 * @return exito,respuesta,respuestaOculta,itemsTatriper,fieldsTatriper,tvaloper
	 */
	public Map<String,Object> obtenerTatriperTvaloperPorCdperson(String cdperson,long timestamp) throws Exception
	{
		Map<String,Object>result=new HashMap<String,Object>();
		logger.info(timestamp+""
				+ "\n################################################"
				+ "\n###### obtenerTatriperTvaloperPorCdperson ######"
				+ "\n cdperson: "+cdperson
				);
		boolean            exito           = true;
		String             respuesta       = null;
		String             respuestaOculta = null;
		GeneradorCampos    gc              = null;
		Item               itemsTatriper   = null;
		Item               fieldsTatriper  = null;
		Map<String,String> tvaloper        = null;   
		
		if(exito)
		{
			try
			{
				List<ComponenteVO>atributos=personasDAO.obtenerAtributosPersona(cdperson);
				gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
				gc.setCdramo(Ramo.SALUD_VITAL.getCdramo());
				gc.setCdrol("1");
				gc.setCdtipsit(TipoSituacion.SALUD_VITAL.getCdtipsit());
				gc.generaComponentes(atributos, false, true, true, false, false, false);
				itemsTatriper=gc.getItems();
				fieldsTatriper=gc.getFields();
			}
			catch(Exception ex)
			{
				logger.error(timestamp+" error al obtener tatriper",ex);
				exito = false;
				respuesta = "Error al obtener datos adicionales #"+timestamp;
				respuestaOculta = ex.getMessage();
			}
		}
		
		if(exito)
		{
			try
			{
				tvaloper=personasDAO.obtenerTvaloper("1",cdperson);
			}
			catch(Exception ex)
			{
				logger.error(timestamp+" error al obtener tvaloper",ex);
				exito = false;
				respuesta = "Error al obtener datos adicionales #"+timestamp;
				respuestaOculta = ex.getMessage();
			}
		}
		
		if(exito)
		{
			respuesta       = "Todo OK";
			respuestaOculta = "Todo OK";
		}
		
		result.put("exito"           , exito);
		result.put("respuesta"       , respuesta);
		result.put("respuestaOculta" , respuestaOculta);
		result.put("itemsTatriper"   , itemsTatriper);
		result.put("fieldsTatriper"  , fieldsTatriper);
		result.put("tvaloper"        , tvaloper);
		logger.info(timestamp+""
				+ "\nresult: "+result
				+ "\n###### obtenerTatriperTvaloperPorCdperson ######"
				+ "\n################################################"
				);
		return result;
	}
	
	/**
	 * Guardar los datos de tvaloper por cdperson con PKG_CONSULTA.P_MOV_TVALOPER
	 * @return exito,respuesta,respuestaOculta
	 */
	public Map<String,Object> guardarDatosTvaloper(String cdperson
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
			,long timestamp
			) throws Exception
	{
		Map<String,Object>result=new HashMap<String,Object>();
		logger.info(timestamp+""
				+ "\n#####################################"
				+ "\n###### guardarPantallaPersonas ######"
				+ "\n cdperson:"+cdperson
				+ "\n otvalor01:"+otvalor01
				+ "\n otvalor02:"+otvalor02
				+ "\n otvalor03:"+otvalor03
				+ "\n otvalor04:"+otvalor04
				+ "\n otvalor05:"+otvalor05
				+ "\n otvalor06:"+otvalor06
				+ "\n otvalor07:"+otvalor07
				+ "\n otvalor08:"+otvalor08
				+ "\n otvalor09:"+otvalor09
				+ "\n otvalor10:"+otvalor10
				+ "\n otvalor11:"+otvalor11
				+ "\n otvalor12:"+otvalor12
				+ "\n otvalor13:"+otvalor13
				+ "\n otvalor14:"+otvalor14
				+ "\n otvalor15:"+otvalor15
				+ "\n otvalor16:"+otvalor16
				+ "\n otvalor17:"+otvalor17
				+ "\n otvalor18:"+otvalor18
				+ "\n otvalor19:"+otvalor19
				+ "\n otvalor20:"+otvalor20
				+ "\n otvalor21:"+otvalor21
				+ "\n otvalor22:"+otvalor22
				+ "\n otvalor23:"+otvalor23
				+ "\n otvalor24:"+otvalor24
				+ "\n otvalor25:"+otvalor25
				+ "\n otvalor26:"+otvalor26
				+ "\n otvalor27:"+otvalor27
				+ "\n otvalor28:"+otvalor28
				+ "\n otvalor29:"+otvalor29
				+ "\n otvalor30:"+otvalor30
				+ "\n otvalor31:"+otvalor31
				+ "\n otvalor32:"+otvalor32
				+ "\n otvalor33:"+otvalor33
				+ "\n otvalor34:"+otvalor34
				+ "\n otvalor35:"+otvalor35
				+ "\n otvalor36:"+otvalor36
				+ "\n otvalor37:"+otvalor37
				+ "\n otvalor38:"+otvalor38
				+ "\n otvalor39:"+otvalor39
				+ "\n otvalor40:"+otvalor40
				+ "\n otvalor41:"+otvalor41
				+ "\n otvalor42:"+otvalor42
				+ "\n otvalor43:"+otvalor43
				+ "\n otvalor44:"+otvalor44
				+ "\n otvalor45:"+otvalor45
				+ "\n otvalor46:"+otvalor46
				+ "\n otvalor47:"+otvalor47
				+ "\n otvalor48:"+otvalor48
				+ "\n otvalor49:"+otvalor49
				+ "\n otvalor50:"+otvalor50
				);
		boolean exito           = true;
		String  respuesta       = null;
		String  respuestaOculta = null;
		
		if(exito)
		{
			try
			{
				Map<String,String>paramsValidarDocumentos=new HashMap<String,String>();
				paramsValidarDocumentos.put("cdperson",cdperson);
				personasDAO.validarDocumentosPersona(paramsValidarDocumentos);
				
				personasDAO.movimientosTvaloper("1",cdperson,
						otvalor01, otvalor02, otvalor03, otvalor04, otvalor05,
						otvalor06, otvalor07, otvalor08, otvalor09, otvalor10,
						otvalor11, otvalor12, otvalor13, otvalor14, otvalor15,
						otvalor16, otvalor17, otvalor18, otvalor19, otvalor20,
						otvalor21, otvalor22, otvalor23, otvalor24, otvalor25,
						otvalor26, otvalor27, otvalor28, otvalor29, otvalor30,
						otvalor31, otvalor32, otvalor33, otvalor34, otvalor35,
						otvalor36, otvalor37, otvalor38, otvalor39, otvalor40,
						otvalor41, otvalor42, otvalor43, otvalor44, otvalor45,
						otvalor46, otvalor47, otvalor48, otvalor49, otvalor50
						);
			}
			catch(Exception ex)
			{
				logger.error(timestamp+" error en el movimiento de tvaloper",ex);
				exito           = false;
				respuesta       = ex.getMessage()+" #"+timestamp;
				respuestaOculta = "sin respuesta oculta";
			}
		}
		
		if(exito)
		{
			respuesta       = "Datos guardados correctamente";
			respuestaOculta = "Sin respuesta oculta";
		}
		
		result.put("exito"           , exito);
		result.put("respuesta"       , respuesta);
		result.put("respuestaOculta" , respuestaOculta);
		logger.info(timestamp+""
				+ "\nresult: "+result
				+ "\n###### guardarPantallaPersonas ######"
				+ "\n#####################################"
				);
		return result;
	}
	
	@Override
	public List<Map<String,String>>cargarDocumentosPersona(String cdperson)throws Exception
	{
		logger.info(""
				+ "\n#####################################"
				+ "\n###### cargarDocumentosPersona ######"
				+ "\ncdperson: "+cdperson
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdperson",cdperson);
		List<Map<String,String>>lista=personasDAO.cargarDocumentosPersona(params);
		if(lista==null)
		{
			lista=new ArrayList<Map<String,String>>();
		}
		logger.info("lista size: "+lista.size());
		logger.info(""
				+ "\n###### cargarDocumentosPersona ######"
				+ "\n#####################################"
				);
		return lista;
	}
	
	@Override
	public String cargarNombreDocumentoPersona(String cdperson,String codidocu)throws Exception
	{
		logger.info(""
				+ "\n##########################################"
				+ "\n###### cargarNombreDocumentoPersona ######"
				+ "\ncdperson "+cdperson
				+ "\ncodidocu "+codidocu
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdperson",cdperson);
		params.put("codidocu",codidocu);
		String nombre=personasDAO.cargarNombreDocumentoPersona(params);
		logger.info("nombre: "+nombre);
		logger.info(""
				+ "\n###### cargarNombreDocumentoPersona ######"
				+ "\n##########################################"
				);
		return nombre;
	}
	
	/*
	 * Getters y setters
	 */
	public void setPantallasDAO(PantallasDAO pantallasDAO) {
		this.pantallasDAO = pantallasDAO;
	}

	public void setPersonasDAO(PersonasDAO personasDAO) {
		this.personasDAO = personasDAO;
	}
	
}