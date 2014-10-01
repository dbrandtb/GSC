package mx.com.gseguros.portal.cotizacion.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.cotizacion.dao.CotizacionDAO;
import mx.com.gseguros.portal.cotizacion.model.DatosUsuario;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaImapSmapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSlistVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSmapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaVoidVO;
import mx.com.gseguros.portal.cotizacion.model.ParametroCotizacion;
import mx.com.gseguros.portal.cotizacion.service.CotizacionManager;
import mx.com.gseguros.portal.general.dao.PantallasDAO;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.utils.Constantes;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

public class CotizacionManagerImpl implements CotizacionManager 
{

	private static final Logger logger = Logger.getLogger(CotizacionManagerImpl.class);
	private CotizacionDAO cotizacionDAO;
	private PantallasDAO  pantallasDAO;
	
	@Override
	public void movimientoTvalogarGrupo(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdtipsit
			,String cdgrupo
			,String cdgarant
			,String status
			,String cdatribu
			,String valor)throws Exception
	{
		logger.info(""
				+ "\n#####################################"
				+ "\n###### movimientoTvalogarGrupo ######"
				+ "\ncdunieco "+cdunieco
				+ "\ncdramo "+cdramo
				+ "\nestado "+estado
				+ "\nnmpoliza "+nmpoliza
				+ "\nnmsuplem "+nmsuplem
				+ "\ncdtipsit "+cdtipsit
				+ "\ncdgrupo "+cdgrupo
				+ "\ncdgarant "+cdgarant
				+ "\nstatus "+status
				+ "\ncdatribu "+cdatribu
				+ "\nvalor "+valor
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("cdtipsit" , cdtipsit);
		params.put("cdgrupo"  , cdgrupo);
		params.put("cdgarant" , cdgarant);
		params.put("status"   , status);
		params.put("cdatribu" , cdatribu);
		params.put("valor"    , valor);
		cotizacionDAO.movimientoTvalogarGrupo(params);
		logger.info(""
				+ "\n###### movimientoTvalogarGrupo ######"
				+ "\n#####################################"
				);
	}
	
	@Override
	public void movimientoMpolisitTvalositGrupo(
			String  cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdgrupo
			,String otvalor06
			,String otvalor07
			,String otvalor08
			,String otvalor09
			,String otvalor10
			,String otvalor11
			,String otvalor12
			,String otvalor13
			,String otvalor16)throws Exception
	{
		logger.info(""
				+ "\n#############################################"
				+ "\n###### movimientoMpolisitTvalositGrupo ######"
				+ "\ncdunieco "+cdunieco
				+ "\ncdramo "+cdramo
				+ "\nestado "+estado
				+ "\nnmpoliza "+nmpoliza
				+ "\ncdgrupo "+cdgrupo
				+ "\notvalor06 "+otvalor06
				+ "\notvalor07 "+otvalor07
				+ "\notvalor08 "+otvalor08
				+ "\notvalor09 "+otvalor09
				+ "\notvalor10 "+otvalor10
				+ "\notvalor11 "+otvalor11
				+ "\notvalor12 "+otvalor12
				+ "\notvalor13 "+otvalor13
				+ "\notvalor16 "+otvalor16
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdunieco"  , cdunieco);
		params.put("cdramo"    , cdramo);
		params.put("estado"    , estado);
		params.put("nmpoliza"  , nmpoliza);
		params.put("cdgrupo"   , cdgrupo);
		params.put("otvalor06" , otvalor06);
		params.put("otvalor07" , otvalor07);
		params.put("otvalor08" , otvalor08);
		params.put("otvalor09" , otvalor09);
		params.put("otvalor10" , otvalor10);
		params.put("otvalor11" , otvalor11);
		params.put("otvalor12" , otvalor12);
		params.put("otvalor13" , otvalor13);
		params.put("otvalor16" , otvalor16);
		cotizacionDAO.movimientoMpolisitTvalositGrupo(params);
		logger.info("" 
				+ "\n###### movimientoMpolisitTvalositGrupo ######"
				+ "\n#############################################"
				);
	}

	@Override
	public void movimientoMpoligarGrupo(
			String  cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdtipsit
			,String cdgrupo
			,String cdgarant
			,String status
			,String cdmoneda
			,String accion)throws Exception
	{
		logger.info(""
				+ "\n#####################################"
				+ "\n###### movimientoMpoligarGrupo ######"
				+ "\n cdunieco : "+cdunieco
				+ "\n cdramo : "+cdramo
				+ "\n estado : "+estado
				+ "\n nmpoliza : "+nmpoliza
				+ "\n nmsuplem : "+nmsuplem
				+ "\n cdtipsit : "+cdtipsit
				+ "\n cdgrupo : "+cdgrupo
				+ "\n cdgarant : "+cdgarant
				+ "\n status : "+status
				+ "\n cdmoneda : "+cdmoneda
				+ "\n accion : "+accion
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("cdtipsit" , cdtipsit);
		params.put("cdgrupo"  , cdgrupo);
		params.put("cdgarant" , cdgarant);
		params.put("status"   , status);
		params.put("cdmoneda" , cdmoneda);
		params.put("accion"   , accion);
		cotizacionDAO.movimientoMpoligarGrupo(params);
		logger.info(""
				+ "\n###### movimientoMpoligarGrupo ######"
				+ "\n#####################################"
				);
	}
	
	@Override
	public Map<String,String> cargarDatosCotizacionGrupo(
			String cdunieco
			,String cdramo
			,String cdtipsit
			,String estado
			,String nmpoliza
			,String ntramite) throws Exception
	{
		logger.info(""
				+ "\n########################################"
				+ "\n###### cargarDatosCotizacionGrupo ######"
				+ "\n cdunieco "+cdunieco
				+ "\n cdramo "+cdramo
				+ "\n cdtipsit "+cdtipsit
				+ "\n estado "+estado
				+ "\n nmpoliza "+nmpoliza
				+ "\n ntramite "+ntramite
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("cdtipsit" , cdtipsit);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("ntramite" , ntramite);
		Map<String,String>datos=cotizacionDAO.cargarDatosCotizacionGrupo(params);
		if(datos==null)
		{
			datos=new HashMap<String,String>();
		}
		logger.info(""
				+ "\n###### cargarDatosCotizacionGrupo ######"
				+ "\n########################################"
				);
		return datos;
	}
	
	@Override
	public List<Map<String,String>>cargarGruposCotizacion(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza)throws Exception
	{
		logger.info(""
				+ "\n####################################"
				+ "\n###### cargarGruposCotizacion ######"
				+ "\n cdunieco "+cdunieco
				+ "\n cdramo "+cdramo
				+ "\n estado "+estado
				+ "\n nmpoliza "+nmpoliza
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		List<Map<String,String>>listaGrupos=cotizacionDAO.cargarGruposCotizacion(params);
		if(listaGrupos==null)
		{
			listaGrupos=new ArrayList<Map<String,String>>();
		}
		logger.info("lista size: "+listaGrupos.size());
		logger.info(""
				+ "\n###### cargarGruposCotizacion ######"
				+ "\n####################################"
				);
		return listaGrupos;
	}
	
	@Override
	public Map<String,String>cargarDatosGrupoLinea(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdgrupo)throws Exception
	{
		logger.info(""
				+ "\n###################################"
				+ "\n###### cargarDatosGrupoLinea ######"
				+ "\n cdunieco "+cdunieco
				+ "\n cdramo "+cdramo
				+ "\n estado "+estado
				+ "\n nmpoliza "+nmpoliza
				+ "\n cdgrupo "+cdgrupo
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("cdgrupo"  , cdgrupo);
		Map<String,String>datos=cotizacionDAO.cargarDatosGrupoLinea(params);
		if(datos==null)
		{
			datos=new HashMap<String,String>();
		}
		logger.info(""
				+ "\n###### cargarDatosGrupoLinea ######"
				+ "\n###################################"
				);
		return datos;
	}
	
	@Override
	public List<Map<String,String>>cargarTvalogarsGrupo(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdgrupo)throws Exception
	{
		logger.info(""
				+ "\n##################################"
				+ "\n###### cargarTvalogarsGrupo ######"
				+ "\n cdunieco "+cdunieco
				+ "\n cdramo "+cdramo
				+ "\n estado "+estado
				+ "\n nmpoliza "+nmpoliza
				+ "\n cdgrupo "+cdgrupo
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("cdgrupo"  , cdgrupo);
		List<Map<String,String>>listaTvalogars=cotizacionDAO.cargarTvalogarsGrupo(params);
		if(listaTvalogars==null)
		{
			listaTvalogars=new ArrayList<Map<String,String>>();
		}
		logger.debug("lista size: "+listaTvalogars.size());
		logger.info(""
				+ "\n###### cargarTvalogarsGrupo ######"
				+ "\n##################################"
				);
		return listaTvalogars;
	}
	
	@Override
	public List<Map<String,String>>cargarTarifasPorEdad(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdplan
			,String cdgrupo
			,String cdperpag)throws Exception
	{
		logger.info(""
				+ "\n##################################"
				+ "\n###### cargarTarifasPorEdad ######"
				+ "\n cdunieco "+cdunieco
				+ "\n cdramo "+cdramo
				+ "\n estado "+estado
				+ "\n nmpoliza "+nmpoliza
				+ "\n nmsuplem "+nmsuplem
				+ "\n cdplan "+cdplan
				+ "\n cdgrupo "+cdgrupo
				+ "\n cdperpag "+cdperpag
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("cdplan"   , cdplan);
		params.put("cdgrupo"  , cdgrupo);
		params.put("cdperpag" , cdperpag);
		List<Map<String,String>>lista=cotizacionDAO.cargarTarifasPorEdad(params);
		if(lista==null)
		{
			lista=new ArrayList<Map<String,String>>();
		}
		logger.info("cargarTarifasPorEdad lista size: "+lista.size());
		logger.info(""
				+ "\n###### cargarTarifasPorEdad ######"
				+ "\n##################################"
				);
		return lista;
	}
	
	@Override
	public List<Map<String,String>>cargarTarifasPorCobertura(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdplan
			,String cdgrupo
			,String cdperpag)throws Exception
	{
		logger.info(""
				+ "\n#######################################"
				+ "\n###### cargarTarifasPorCobertura ######"
				+ "\n cdunieco "+cdunieco
				+ "\n cdramo "+cdramo
				+ "\n estado "+estado
				+ "\n nmpoliza "+nmpoliza
				+ "\n nmsuplem "+nmsuplem
				+ "\n cdplan "+cdplan
				+ "\n cdgrupo "+cdgrupo
				+ "\n cdperpag "+cdperpag
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("cdplan"   , cdplan);
		params.put("cdgrupo"  , cdgrupo);
		params.put("cdperpag"  , cdperpag);
		List<Map<String,String>>lista=cotizacionDAO.cargarTarifasPorCobertura(params);
		if(lista==null)
		{
			lista=new ArrayList<Map<String,String>>();
		}
		logger.info("cargarTarifasPorCobertura lista size: "+lista.size());
		logger.info(""
				+ "\n###### cargarTarifasPorCobertura ######"
				+ "\n#######################################"
				);
		return lista;
	}
	
	@Override
	public String cargarNombreAgenteTramite(String ntramite)throws Exception
	{
		logger.info(""
				+ "\n#######################################"
				+ "\n###### cargarNombreAgenteTramite ######"
				+ "\nntramite "+ntramite
				);
		String nombre=cotizacionDAO.cargarNombreAgenteTramite(ntramite);
		logger.info("cargarNombreAgenteTramite nombre: "+nombre);
		logger.info(""
				+ "\n###### cargarNombreAgenteTramite ######"
				+ "\n#######################################"
				);
		return nombre;
	}
	
	@Override
	public Map<String,String>cargarPermisosPantallaGrupo(String cdsisrol,String status)throws Exception
	{
		logger.info(""
				+ "\n#########################################"
				+ "\n###### cargarPermisosPantallaGrupo ######"
				+ "\ncdsisrol "+cdsisrol
				+ "\nstatus "+status
				);
		Map<String,String>res=cotizacionDAO.cargarPermisosPantallaGrupo(cdsisrol,status);
		logger.info(""
				+ "\nresponse "+res
				+ "\n###### cargarPermisosPantallaGrupo ######"
				+ "\n#########################################"
				);
		return res;
	}
	
	@Override
	public void guardarCensoCompleto(
			String  nombreCenso
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdedo
			,String cdmunici
			,String cdplan1
			,String cdplan2
			,String cdplan3
			,String cdplan4
			,String cdplan5)throws Exception
	{
		logger.info(""
				+ "\n##################################"
				+ "\n###### guardarCensoCompleto ######"
				+ "\nnombreCenso "+nombreCenso
				+ "\ncdunieco "+cdunieco
				+ "\ncdramo "+cdramo
				+ "\nestado "+estado
				+ "\nmpoliza "+nmpoliza
				+ "\ncdedo "+cdedo
				+ "\ncdmunici "+cdmunici
				+ "\ncdplan1 "+cdplan1
				+ "\ncdplan2 "+cdplan2
				+ "\ncdplan3 "+cdplan3
				+ "\ncdplan4 "+cdplan4
				+ "\ncdplan5 "+cdplan5
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("censo"     , nombreCenso);
		params.put("cdunieco"  , cdunieco);
		params.put("cdramo"    , cdramo);
		params.put("estado"    , estado);
		params.put("nmpoliza"  , nmpoliza);
		params.put("otvalor04" , cdedo);
		params.put("otvalor05" , cdmunici);
		params.put("cdplan1"   , cdplan1);
		params.put("cdplan2"   , cdplan2);
		params.put("cdplan3"   , cdplan3);
		params.put("cdplan4"   , cdplan4);
		params.put("cdplan5"   , cdplan5);
		cotizacionDAO.guardarCensoCompleto(params);
		logger.info(""
				+ "\n###### guardarCensoCompleto ######"
				+ "\n##################################"
				);
	}
	
	
	public int obtieneTipoValorAutomovil(String codigoPostal, String tipoVehiculo)throws Exception{
		Map<String,String>params=new HashMap<String,String>();
		params.put("pv_cdpostal_i"     , codigoPostal);
		params.put("pv_cdtipveh_i"  , tipoVehiculo);
		Map<String,String>res = cotizacionDAO.obtieneTipoValorAutomovil(params);
		return Integer.parseInt(res.get("pv_etiqueta_o"));
	}
	
	@Override
	public List<Map<String,String>>cargarAseguradosExtraprimas(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdgrupo)throws Exception
	{
		logger.debug(""
				+ "\n#########################################"
				+ "\n###### cargarAseguradosExtraprimas ######"
				+ "\ncdunieco "+cdunieco
				+ "\ncdramo "+cdramo
				+ "\nestado "+estado
				+ "\nnmpoliza "+nmpoliza
				+ "\nnmsuplem "+nmsuplem
				+ "\ncdgrupo "+cdgrupo
				);
		
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("cdgrupo"  , cdgrupo);
		List<Map<String,String>>lista=cotizacionDAO.cargarAseguradosExtraprimas(params);
		
		logger.debug(""
				+ "\nlista size "+lista.size()
				+ "\n###### cargarAseguradosExtraprimas ######"
				+ "\n#########################################"
				);
		return lista;
	}
	
	@Override
	public void guardarExtraprimaAsegurado(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsituac
			,String ocupacion
			,String extraprimaOcupacion
			,String peso
			,String estatura
			,String extraprimaSobrepeso
			)throws Exception
	{
		logger.info(""
				+ "\n########################################"
				+ "\n###### guardarExtraprimaAsegurado ######"
				+ "\ncdunieco "+cdunieco
				+ "\ncdramo "+cdramo
				+ "\nestado "+estado
				+ "\nnmpoliza "+nmpoliza
				+ "\nnmsuplem "+nmsuplem
				+ "\nnmsituac "+nmsituac
				+ "\nocupacion "+ocupacion
				+ "\nextraprimaOcupacion "+extraprimaOcupacion
				+ "\npeso "+peso
				+ "\nestatura "+estatura
				+ "\nextraprimaSobrepeso "+extraprimaSobrepeso
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdunieco"            , cdunieco);
		params.put("cdramo"              , cdramo);
		params.put("estado"              , estado);
		params.put("nmpoliza"            , nmpoliza);
		params.put("nmsuplem"            , nmsuplem);
		params.put("nmsituac"            , nmsituac);
		params.put("ocupacion"           , ocupacion);
		params.put("extraprimaOcupacion" , extraprimaOcupacion);
		params.put("peso"                , peso);
		params.put("estatura"            , estatura);
		params.put("extraprimaSobrepeso" , extraprimaSobrepeso);
		cotizacionDAO.guardarExtraprimaAsegurado(params);
		logger.info(""
				+ "\n###### guardarExtraprimaAsegurado ######"
				+ "\n########################################"
				);
	}
	
	@Override
	public List<Map<String,String>>cargarAseguradosGrupo(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdgrupo)throws Exception
	{
		logger.info(""
				+ "\n###################################"
				+ "\n###### cargarAseguradosGrupo ######"
				+ "\ncdunieco "+cdunieco
				+ "\ncdramo "+cdramo
				+ "\nestado "+estado
				+ "\nnmpoliza "+nmpoliza
				+ "\nnmsuplem "+nmsuplem
				+ "\ncdgrupo "+cdgrupo
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("cdgrupo"  , cdgrupo);
		List<Map<String,String>>lista=cotizacionDAO.cargarAseguradosGrupo(params);
		logger.info(""
				+ "\nlista size "+lista.size()
				+ "\n###### cargarAseguradosGrupo ######"
				+ "\n###################################"
				);
		return lista;
	}

	@Override
	public void borrarMpoliperGrupo(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdgrupo)throws Exception
	{
		logger.info(""
				+ "\n#################################"
				+ "\n###### borrarMpoliperGrupo ######"
				+ "\ncdunieco "+cdunieco
				+ "\ncdramo "+cdramo
				+ "\nestado "+estado
				+ "\nnmpoliza "+nmpoliza
				+ "\ncdgrupo "+cdgrupo
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("cdgrupo"  , cdgrupo);
		cotizacionDAO.borrarMpoliperGrupo(params);
		logger.info(""
				+ "\n###### borrarMpoliperGrupo ######"
				+ "\n#################################"
				);
	}
	
	@Override
	public Map<String,String>cargarTipoSituacion(String cdramo,String cdtipsit)throws Exception
	{
		logger.info(""
				+ "\n#################################"
				+ "\n###### cargarTipoSituacion ######"
				+ "\ncdramo "+cdramo
				+ "\ncdtipsit "+cdtipsit
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdramo",cdramo);
		params.put("cdtipsit",cdtipsit);
		Map<String,String>respuesta=cotizacionDAO.cargarTipoSituacion(params);
		logger.info(""
				+ "\nrespuesta "+respuesta
				+ "\n###### cargarTipoSituacion ######"
				+ "\n#################################"
				);
		return respuesta;
	}
	
	@Override
	public String cargarCduniecoAgenteAuto(String cdagente)throws Exception
	{
		logger.info(
				new StringBuilder()
				.append("\n######################################")
				.append("\n###### cargarCduniecoAgenteAuto ######")
				.append("\ncdagente ")
				.append(cdagente)
				.toString()
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdagente",cdagente);
		String cdunieco=cotizacionDAO.cargarCduniecoAgenteAuto(params);
		logger.info(
				new StringBuilder()
				.append("\ncdunieco ")
				.append(cdunieco)
				.append("\n###### cargarCduniecoAgenteAuto ######")
				.append("\n######################################")
				.toString()
				);
		return cdunieco;
	}
	
	@Override
	public Map<String,String>obtenerDatosAgente(String cdagente,String cdramo)throws Exception
	{
		logger.info(new StringBuilder()
		.append("\n################################")
		.append("\n###### obtenerDatosAgente ######")
		.append("\ncdagente=").append(cdagente)
		.append("\ncdramo=").append(cdramo)
		.toString()
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdagente" , cdagente);
		params.put("cdramo"   , cdramo);
		Map<String,String>datos=cotizacionDAO.obtenerDatosAgente(params);
		logger.info(new StringBuilder()
		.append("\ndatos=").append(datos)
		.append("\n###### obtenerDatosAgente ######")
		.append("\n################################")
		.toString()
				);
		return datos;
	}
	
	@Override
	public ManagerRespuestaSmapVO obtenerParametrosCotizacion(
			ParametroCotizacion parametro
			,String cdramo
			,String cdtipsit
			,String clave4
			,String clave5)throws Exception
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ obtenerParametrosCotizacion @@@@@@")
				.append("\n@@@@@@ parametro=").append(parametro.getParametro())
				.append("\n@@@@@@ cdramo=")   .append(cdramo)
				.append("\n@@@@@@ cdtipsit=") .append(cdtipsit)
				.append("\n@@@@@@ clave4=")   .append(clave4)
				.append("\n@@@@@@ clave5=")   .append(clave5)
				.toString()
				);
		
		ManagerRespuestaSmapVO resp = new ManagerRespuestaSmapVO(true);
		
		try
		{
			Map<String,String>params=new HashMap<String,String>();
			params.put("parametro" , parametro.getParametro());
			params.put("cdramo"    , cdramo);
			params.put("cdtipsit"  , cdtipsit);
			params.put("clave4"    , clave4);
			params.put("clave5"    , clave5);
			Map<String,String>valores=cotizacionDAO.obtenerParametrosCotizacion(params);
			resp.setSmap(valores);
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(
					new StringBuilder()
					.append("No existe el par&aacute;metro #").append(timestamp)
					.toString()
					);
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ obtenerParametrosCotizacion @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaSmapVO cargarAutoPorClaveGS(String cdramo,String clavegs,String cdtipsit) throws Exception
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ cargarAutoPorClaveGS @@@@@@")
				.append("\n@@@@@@ cdramo=")  .append(cdramo)
				.append("\n@@@@@@ clave gs=").append(clavegs)
				.append("\n@@@@@@ cdtipsit=").append(cdtipsit)
				.toString()
				);
		ManagerRespuestaSmapVO resp = new ManagerRespuestaSmapVO(true);
		
		try
		{
			Map<String,String>params=new HashMap<String,String>();
			params.put("cdramo"   , cdramo);
			params.put("clavegs"  , clavegs);
			params.put("cdtipsit" , cdtipsit);
			Map<String,String>valores=cotizacionDAO.cargarAutoPorClaveGS(params);
			resp.setSmap(valores);
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(
					new StringBuilder()
					.append("Error al recuperar datos del auto #").append(timestamp)
					.toString()
					);
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ cargarAutoPorClaveGS @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaSmapVO cargarClaveGSPorAuto(String cdramo,String modelo) throws Exception
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ cargarClaveGSPorAuto @@@@@@")
				.append("\n@@@@@@ cdramo=").append(cdramo)
				.append("\n@@@@@@ modelo=").append(modelo)
				.toString()
				);
		ManagerRespuestaSmapVO resp = new ManagerRespuestaSmapVO(true);
		
		try
		{
			Map<String,String>params=new HashMap<String,String>();
			params.put("cdramo" , cdramo);
			params.put("modelo" , modelo);
			Map<String,String>valores=cotizacionDAO.cargarClaveGSPorAuto(params);
			resp.setSmap(valores);
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(
					new StringBuilder()
					.append("Error al recuperar clave gs del auto #").append(timestamp)
					.toString()
					);
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ cargarClaveGSPorAuto @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaSmapVO cargarSumaAseguradaAuto(String cdsisrol,String modelo,String version,String cdramo,String cdtipsit)throws Exception
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ cargarSumaAseguradaAuto @@@@@@")
				.append("\n@@@@@@ cdsisrol=").append(cdsisrol)
				.append("\n@@@@@@ modelo=")  .append(modelo)
				.append("\n@@@@@@ version=") .append(version)
				.toString()
				);
		ManagerRespuestaSmapVO resp = new ManagerRespuestaSmapVO(true);
		
		try
		{
			Map<String,String>params=new HashMap<String,String>();
			params.put("cdsisrol" , cdsisrol);
			params.put("modelo"   , modelo);
			params.put("version"  , version);
			Map<String,String>valores=cotizacionDAO.cargarSumaAseguradaAuto(params);
			logger.debug(
					new StringBuilder()
					.append("suma asegurada=")
					.append(valores)
					.toString());
			
			String claveSuma = "SUMASEG";
			String suma      = valores.get(claveSuma);
			Double dSuma     = Double.valueOf(suma);
			
			Map<String,String>paramsDeprecio=new HashMap<String,String>();
			paramsDeprecio.put("parametro" , ParametroCotizacion.DEPRECIACION.getParametro());
			paramsDeprecio.put("cdramo"    , cdramo);
			paramsDeprecio.put("cdtipsit"  , cdtipsit);
			paramsDeprecio.put("clave4"    , cdsisrol);
			paramsDeprecio.put("clave5"    , null);
			Map<String,String>valoresDeprecio=cotizacionDAO.obtenerParametrosCotizacion(paramsDeprecio);
			logger.debug(
					new StringBuilder()
					.append("depreciacion=")
					.append(valoresDeprecio)
					.toString());
			
			Double deprecio=Double.valueOf(valoresDeprecio.get("P1VALOR"));
			dSuma = dSuma*(1d-deprecio);
			
			valores.put(claveSuma,String.format("%.2f", dSuma));
			
			resp.setSmap(valores);

			logger.debug(
					new StringBuilder()
					.append("suma asegurada nueva=")
					.append(valores)
					.toString());
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(
					new StringBuilder()
					.append("Error al cargar valor comercial del auto #").append(timestamp)
					.toString()
					);
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ cargarSumaAseguradaAuto @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaVoidVO agregarClausulaICD(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String cdclausu
			,String nmsuplem
			,String icd)throws Exception
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ agregarClausulaICD @@@@@@")
				.append("\n@@@@@@ cdunieco=").append(cdunieco)
				.append("\n@@@@@@ cdramo=")  .append(cdramo)
				.append("\n@@@@@@ estado=")  .append(estado)
				.append("\n@@@@@@ nmpoliza=").append(nmpoliza)
				.append("\n@@@@@@ nmsituac=").append(nmsituac)
				.append("\n@@@@@@ cdclausu=").append(cdclausu)
				.append("\n@@@@@@ nmsuplem=").append(nmsuplem)
				.append("\n@@@@@@ icd=")     .append(icd)
				.toString()
				);
		ManagerRespuestaVoidVO resp = new ManagerRespuestaVoidVO(true);
		
		try
		{
			Map<String,String>params=new HashMap<String,String>();
			params.put("cdunieco" , cdunieco);
			params.put("cdramo"   , cdramo);
			params.put("estado"   , estado);
			params.put("nmpoliza" , nmpoliza);
			params.put("nmsituac" , nmsituac);
			params.put("cdclausu" , cdclausu);
			params.put("nmsuplem" , nmsuplem);
			params.put("icd"      , icd);
			params.put("accion"   , Constantes.INSERT_MODE);
			cotizacionDAO.movimientoMpolicotICD(params);
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(
					new StringBuilder()
					.append("Error al relacionar ICD #").append(timestamp)
					.toString()
					);
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ agregarClausulaICD @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaSlistVO cargarClausulaICD(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String cdclausu
			,String nmsuplem)throws Exception
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ cargarClausulaICD @@@@@@")
				.append("\n@@@@@@ cdunieco=").append(cdunieco)
				.append("\n@@@@@@ cdramo=")  .append(cdramo)
				.append("\n@@@@@@ estado=")  .append(estado)
				.append("\n@@@@@@ nmpoliza=").append(nmpoliza)
				.append("\n@@@@@@ nmsituac=").append(nmsituac)
				.append("\n@@@@@@ cdclausu=").append(cdclausu)
				.append("\n@@@@@@ nmsuplem=").append(nmsuplem)
				.toString()
				);
		ManagerRespuestaSlistVO resp = new ManagerRespuestaSlistVO(true);
		
		try
		{
			Map<String,String>params=new HashMap<String,String>();
			params.put("cdunieco" , cdunieco);
			params.put("cdramo"   , cdramo);
			params.put("estado"   , estado);
			params.put("nmpoliza" , nmpoliza);
			params.put("nmsituac" , nmsituac);
			params.put("cdclausu" , cdclausu);
			params.put("nmsuplem" , nmsuplem);
			resp.setSlist(cotizacionDAO.cargarMpolicotICD(params));
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(
					new StringBuilder()
					.append("Error al obtener los ICD #").append(timestamp)
					.toString()
					);
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
			
			resp.setSlist(new ArrayList<Map<String,String>>());
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ cargarClausulaICD @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaVoidVO borrarClausulaICD(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String cdclausu
			,String nmsuplem
			,String icd)throws Exception
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ borrarClausulaICD @@@@@@")
				.append("\n@@@@@@ cdunieco=").append(cdunieco)
				.append("\n@@@@@@ cdramo=")  .append(cdramo)
				.append("\n@@@@@@ estado=")  .append(estado)
				.append("\n@@@@@@ nmpoliza=").append(nmpoliza)
				.append("\n@@@@@@ nmsituac=").append(nmsituac)
				.append("\n@@@@@@ cdclausu=").append(cdclausu)
				.append("\n@@@@@@ nmsuplem=").append(nmsuplem)
				.append("\n@@@@@@ icd=")     .append(icd)
				.toString()
				);
		ManagerRespuestaVoidVO resp = new ManagerRespuestaVoidVO(true);
		
		try
		{
			Map<String,String>params=new HashMap<String,String>();
			params.put("cdunieco" , cdunieco);
			params.put("cdramo"   , cdramo);
			params.put("estado"   , estado);
			params.put("nmpoliza" , nmpoliza);
			params.put("nmsituac" , nmsituac);
			params.put("cdclausu" , cdclausu);
			params.put("nmsuplem" , nmsuplem);
			params.put("icd"      , icd);
			params.put("accion"   , Constantes.DELETE_MODE);
			cotizacionDAO.movimientoMpolicotICD(params);
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(
					new StringBuilder()
					.append("Error al borrar ICD #").append(timestamp)
					.toString()
					);
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ borrarClausulaICD @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public List<Map<String,String>>cargarConfiguracionGrupo(String cdramo,String cdtipsit)throws Exception
	{
		logger.info(
				new StringBuilder()
				.append("\n######################################")
				.append("\n###### cargarConfiguracionGrupo ######")
				.append("\n###### cdramo=")  .append(cdramo)
				.append("\n###### cdtipsit=").append(cdtipsit)
				.toString()
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdramo"   , cdramo);
		params.put("cdtipsit" , cdtipsit);
		List<Map<String,String>>lista=cotizacionDAO.cargarConfiguracionGrupo(params);
		logger.info(
				new StringBuilder()
				.append("\n###### lista=").append(lista)
				.append("\n###### cargarConfiguracionGrupo ######")
				.append("\n######################################")
				.toString()
				);
		return lista;
	}
	
	@Override
	public ComponenteVO cargarComponenteTatrisit(String cdtipsit,String cdusuari,String cdatribu)throws Exception
	{
		logger.info(
				new StringBuilder()
				.append("\n######################################")
				.append("\n###### cargarComponenteTatrisit ######")
				.append("\n###### cdtipsit=").append(cdtipsit)
				.append("\n###### cdusuari=").append(cdusuari)
				.append("\n###### cdatribu=").append(cdatribu)
				.toString()
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdtipsit" , cdtipsit);
		params.put("cdusuari" , cdusuari);
		params.put("cdatribu" , cdatribu);
		ComponenteVO comp = cotizacionDAO.cargarComponenteTatrisit(params);
		logger.info(
				new StringBuilder()
				.append("\n###### componente=").append(comp)
				.append("\n###### cargarComponenteTatrisit ######")
				.append("\n######################################")
				.toString()
				);
		return comp;
	}
	
	@Override
	public ComponenteVO cargarComponenteTatrigar(String cdramo,String cdtipsit,String cdgarant,String cdatribu)throws Exception
	{
		logger.info(
				new StringBuilder()
				.append("\n######################################")
				.append("\n###### cargarComponenteTatrigar ######")
				.append("\n###### cdramo=")  .append(cdramo)
				.append("\n###### cdtipsit=").append(cdtipsit)
				.append("\n###### cdgarant=").append(cdgarant)
				.append("\n###### cdatribu=").append(cdatribu)
				.toString()
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdramo"   , cdramo);
		params.put("cdtipsit" , cdtipsit);
		params.put("cdgarant" , cdgarant);
		params.put("cdatribu" , cdatribu);
		ComponenteVO comp = cotizacionDAO.cargarComponenteTatrigar(params);
		logger.info(
				new StringBuilder()
				.append("\n###### componente=").append(comp)
				.append("\n###### cargarComponenteTatrigar ######")
				.append("\n######################################")
				.toString()
				);
		return comp;
	}
	
	@Override
	public ManagerRespuestaVoidVO validarDescuentoAgente(
			String  tipoUnidad
			,String uso
			,String zona
			,String promotoria
			,String cdagente
			,String descuento)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ validarDescuentoAgente @@@@@@")
				.append("\n@@@@@@ tipoUnidad=").append(tipoUnidad)
				.append("\n@@@@@@ uso=").append(uso)
				.append("\n@@@@@@ zona=").append(zona)
				.append("\n@@@@@@ promotoria=").append(promotoria)
				.append("\n@@@@@@ cdagente=").append(cdagente)
				.append("\n@@@@@@ descuento=").append(descuento)
				.toString()
				);
		ManagerRespuestaVoidVO resp = new ManagerRespuestaVoidVO(true);
		
		//procedure
		try
		{
			cotizacionDAO.validarDescuentoAgente(tipoUnidad,uso,zona,promotoria,cdagente,descuento);
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(new StringBuilder(ex.getMessage()).append(" #").append(timestamp).toString());
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ validarDescuentoAgente @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public void movimientoTdescsup(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nsuplogi
			,String cdtipsup
			,Date feemisio
			,String nmsolici
			,Date fesolici
			,Date ferefere
			,String cdseqpol
			,String cdusuari
			,String nusuasus
			,String nlogisus
			,String cdperson
			,String accion)throws Exception
	{
		cotizacionDAO.movimientoTdescsup(
				cdunieco
				,cdramo
				,estado
				,nmpoliza
				,nsuplogi
				,cdtipsup
				,feemisio
				,nmsolici
				,fesolici
				,ferefere
				,cdseqpol
				,cdusuari
				,nusuasus
				,nlogisus
				,cdperson
				,accion);
	}
	
	@Override
	public ManagerRespuestaImapSmapVO pantallaCotizacionGrupo(
			String cdramo
			,String cdtipsit
			,String ntramite
			,String ntramiteVacio
			,String status
			,String cdusuari
			,String cdsisrol
			,String nombreUsuario
			,String cdagente
			)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ pantallaCotizacionGrupo @@@@@@")
				.append("\n@@@@@@ cdramo=")       .append(cdramo)
				.append("\n@@@@@@ cdtipsit=")     .append(cdtipsit)
				.append("\n@@@@@@ ntramite=")     .append(ntramite)
				.append("\n@@@@@@ ntramiteVacio=").append(ntramiteVacio)
				.append("\n@@@@@@ status=")       .append(status)
				.append("\n@@@@@@ cdusuari=")     .append(cdusuari)
				.append("\n@@@@@@ cdsisrol=")     .append(cdsisrol)
				.append("\n@@@@@@ nombreUsuario=").append(nombreUsuario)
				.append("\n@@@@@@ cdagente=")     .append(cdagente)
				.toString()
				);
		
		ManagerRespuestaImapSmapVO resp = new ManagerRespuestaImapSmapVO(true);

		//retocar datos de entrada
		try
		{
			resp.setSmap(new HashMap<String,String>());
			if(StringUtils.isBlank(status))
			{
				status = "0";
			}
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(new StringBuilder("Error al procesar datos #").append(timestamp).toString());
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
		}
		//retocar datos de entrada
		
		String nombreAgente = null;
		
		//datos del agente
		if(resp.isExito())
		{
			try
			{
				//si entran como agente
				if(StringUtils.isBlank(ntramite)&&StringUtils.isBlank(ntramiteVacio))
				{
				    DatosUsuario datUsu = cotizacionDAO.cargarInformacionUsuario(cdusuari,cdtipsit);
				    String cdunieco     = datUsu.getCdunieco();
	        		
				    resp.getSmap().put("cdunieco",cdunieco);
				    
	        		cdagente     = datUsu.getCdagente();
	        		nombreAgente = nombreUsuario;
				}
				//si entran por tramite o tramite vacio
				else if(StringUtils.isNotBlank(ntramite)||StringUtils.isNotBlank(ntramiteVacio))
				{
					nombreAgente = cotizacionDAO.cargarNombreAgenteTramite(StringUtils.isNotBlank(ntramite)?ntramite:ntramiteVacio);
				}
			}
			catch(Exception ex)
			{
				long timestamp = System.currentTimeMillis();
				resp.setExito(false);
				resp.setRespuesta(new StringBuilder("Error al obtener datos del agente #").append(timestamp).toString());
				resp.setRespuestaOculta(ex.getMessage());
				logger.error(resp.getRespuesta(),ex);
			}
		}
		//datos del agente
		
		GeneradorCampos gc = null;
		
		//componentes
		if(resp.isExito())
		{
			try
			{
				gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
				gc.setCdramo(cdramo);
				gc.setCdtipsit(cdtipsit);
				resp.setImap(new HashMap<String,Item>());
				
				//columnas base
				List<ComponenteVO>tatrisitBase = cotizacionDAO.cargarTatrisit(cdtipsit, cdusuari);
				List<ComponenteVO>aux          = new ArrayList<ComponenteVO>();
				for(ComponenteVO iTatri:tatrisitBase)
				{
					if(StringUtils.isNotBlank(iTatri.getSwsuscri())
							&&iTatri.getSwsuscri().equals("N")
							&&iTatri.getSwGrupo().equals("S")
							)
					{
						aux.add(iTatri);
					}
				}
				tatrisitBase = aux;
				if(tatrisitBase.size()>0)
				{
					gc.generaComponentes(tatrisitBase, true, true, false, true, true, false);
					resp.getImap().put("colsBaseFields"  , gc.getFields());
					resp.getImap().put("colsBaseColumns" , gc.getColumns());
				}
				else
				{
					resp.getImap().put("colsBaseFields"  , null);
					resp.getImap().put("colsBaseColumns" , null);
				}
				
				//columnas extendidas
				List<ComponenteVO>tatrisitExt = cotizacionDAO.cargarTatrisit(cdtipsit, cdusuari);
				aux = new ArrayList<ComponenteVO>();
				for(ComponenteVO iTatri:tatrisitExt)
				{
					if(StringUtils.isNotBlank(iTatri.getSwsuscri())
							&&iTatri.getSwsuscri().equals("N")
							&&iTatri.getSwGrupoLinea().equals("S")
							)
					{
						aux.add(iTatri);
					}
				}
				tatrisitExt = aux;
				aux         = null;
				if(tatrisitExt.size()>0)
				{
					gc.generaComponentes(tatrisitBase, true, true, false, true, true, false);
					resp.getImap().put("colsExtFields"  , gc.getFields());
					resp.getImap().put("colsExtColumns" , gc.getColumns());
				}
				else
				{
					resp.getImap().put("colsExtFields"  , null);
					resp.getImap().put("colsExtColumns" , null);
				}
				
				gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
				
				List<ComponenteVO>componentesContratante=pantallasDAO.obtenerComponentes(
						null, null, null,
						null, null, null,
						"COTIZACION_GRUPO", "CONTRATANTE", null);
				gc.generaComponentes(componentesContratante, true,true,true,false,false,false);
				resp.getImap().put("itemsContratante"  , gc.getItems());
				resp.getImap().put("fieldsContratante" , gc.getFields());
				
				List<ComponenteVO>componentesRiesgo=pantallasDAO.obtenerComponentes(
						null, null, null,
						null, null, null,
						"COTIZACION_GRUPO", "RIESGO", null);
				gc.generaComponentes(componentesRiesgo, true,true,true,false,false,false);
				resp.getImap().put("itemsRiesgo"  , gc.getItems());
				resp.getImap().put("fieldsRiesgo" , gc.getFields());
				
				List<ComponenteVO>componentesAgente=pantallasDAO.obtenerComponentes(
						null, null, null,
						null, null, null,
						"COTIZACION_GRUPO", "AGENTE", null);
				componentesAgente.get(0).setDefaultValue(nombreAgente);
				componentesAgente.get(1).setDefaultValue(cdagente);
				gc.generaComponentes(componentesAgente, true,false,true,false,false,false);
				resp.getImap().put("itemsAgente"  , gc.getItems());
				
				List<ComponenteVO>columnaEditorPlan=pantallasDAO.obtenerComponentes(
						null, null, null,
						null, null, null,
						"COTIZACION_GRUPO", "EDITOR_PLANES2", null);
				gc.generaComponentes(columnaEditorPlan, true, false, false, true, true, false);
				resp.getImap().put("editorPlanesColumn",gc.getColumns());
				
				List<ComponenteVO>comboFormaPago=pantallasDAO.obtenerComponentes(
						null, null, null,
						null, null, null,
						"COTIZACION_GRUPO", "COMBO_FORMA_PAGO", null);
				gc.generaComponentes(comboFormaPago, true,false,true,false,false,false);
				resp.getImap().put("comboFormaPago"  , gc.getItems());
				
				List<ComponenteVO>comboRepartoPago=pantallasDAO.obtenerComponentes(
						null, null, null,
						null, null, null,
						"COTIZACION_GRUPO", "COMBO_REPARTO_PAGO", null);
				gc.generaComponentes(comboRepartoPago, true,false,true,false,false,false);
				resp.getImap().put("comboRepartoPago"  , gc.getItems());
				
				List<ComponenteVO>botones=pantallasDAO.obtenerComponentes(
						null, null, "|"+status+"|",
						null, null, cdsisrol,
						"COTIZACION_GRUPO", "BOTONES", null);
				if(botones!=null&&botones.size()>0)
				{
					gc.generaComponentes(botones, true, false, false, false, false, true);
					resp.getImap().put("botones" , gc.getButtons());
				}
				else
				{
					resp.getImap().put("botones" , null);
				}
			}
			catch(Exception ex)
			{
				long timestamp = System.currentTimeMillis();
				resp.setExito(false);
				resp.setRespuesta(new StringBuilder("Error al obtener componentes #").append(timestamp).toString());
				resp.setRespuestaOculta(ex.getMessage());
				logger.error(resp.getRespuesta(),ex);
			}
		}
		//componentes
		
		//permisos
		if(resp.isExito())
		{
			try
			{
				resp.getSmap().put("status" , status);
				resp.getSmap().putAll(cotizacionDAO.cargarPermisosPantallaGrupo(cdsisrol,status));
			}
			catch(Exception ex)
			{
				long timestamp = System.currentTimeMillis();
				resp.setExito(false);
				resp.setRespuesta(new StringBuilder("Error al obtener permisos #").append(timestamp).toString());
				resp.setRespuestaOculta(ex.getMessage());
				logger.error(resp.getRespuesta(),ex);
			}
		}
		//permisos
		
		//campos de asegurados
		if(resp.isExito() && resp.getSmap().containsKey("ASEGURADOS")
				 && StringUtils.isNotBlank(resp.getSmap().get("ASEGURADOS"))
				 && resp.getSmap().get("ASEGURADOS").equals("S"))
		{
			try
			{
				
				List<ComponenteVO>componentesExtraprimas=pantallasDAO.obtenerComponentes(
						null  , null , null
						,null , null , cdsisrol
						,"COTIZACION_GRUPO", "ASEGURADOS", null);
				gc.generaComponentes(componentesExtraprimas, true, true, false, true, false, false);
				resp.getImap().put("aseguradosColumns" , gc.getColumns());
				resp.getImap().put("aseguradosFields"  , gc.getFields());
			}
			catch(Exception ex)
			{
				long timestamp = System.currentTimeMillis();
				resp.setExito(false);
				resp.setRespuesta(new StringBuilder("Error al obtener componentes de asegurados #").append(timestamp).toString());
				resp.setRespuestaOculta(ex.getMessage());
				logger.error(resp.getRespuesta(),ex);
			}
		}
		//campos de asegurados
		
		//campos para extraprimas
		if(resp.isExito() && resp.getSmap().containsKey("EXTRAPRIMAS")
				 && StringUtils.isNotBlank(resp.getSmap().get("EXTRAPRIMAS"))
				 && resp.getSmap().get("EXTRAPRIMAS").equals("S"))
		{
			try
			{
				List<ComponenteVO>componentesExtraprimas=pantallasDAO.obtenerComponentes(
						null  , null , null
						,null , null , cdsisrol
						,"COTIZACION_GRUPO", "EXTRAPRIMAS", null);
				gc.generaComponentes(componentesExtraprimas, true, true, false, true, true, false);
				resp.getImap().put("extraprimasColumns" , gc.getColumns());
				resp.getImap().put("extraprimasFields"  , gc.getFields());
			}
			catch(Exception ex)
			{
				long timestamp = System.currentTimeMillis();
				resp.setExito(false);
				resp.setRespuesta(new StringBuilder("Error al obtener componentes de extraprimas #").append(timestamp).toString());
				resp.setRespuestaOculta(ex.getMessage());
				logger.error(resp.getRespuesta(),ex);
			}
		}
		//campos para extraprimas
		
		//campos para recuperados
		if(resp.isExito() && resp.getSmap().containsKey("ASEGURADOS_EDITAR")
				 && StringUtils.isNotBlank(resp.getSmap().get("ASEGURADOS_EDITAR"))
				 && resp.getSmap().get("ASEGURADOS_EDITAR").equals("S"))
		{
			try
			{
				List<ComponenteVO>componentesRecuperados=pantallasDAO.obtenerComponentes(
						null  , null , null
						,null , null , cdsisrol
						,"COTIZACION_GRUPO", "RECUPERADOS", null);
				gc.generaComponentes(componentesRecuperados, true, true, false, true, true, false);
				resp.getImap().put("recuperadosColumns" , gc.getColumns());
				resp.getImap().put("recuperadosFields"  , gc.getFields());
			}
			catch(Exception ex)
			{
				long timestamp = System.currentTimeMillis();
				resp.setExito(false);
				resp.setRespuesta(new StringBuilder("Error al obtener componentes de recuperados #").append(timestamp).toString());
				resp.setRespuestaOculta(ex.getMessage());
				logger.error(resp.getRespuesta(),ex);
			}
		}
		//campos para recuperados
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ pantallaCotizacionGrupo @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaSmapVO cargarClienteCotizacion(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ cargarClienteCotizacion @@@@@@")
				.append("\n@@@@@@ cdunieco=").append(cdunieco)
				.append("\n@@@@@@ cdramo=")  .append(cdramo)
				.append("\n@@@@@@ estado=")  .append(estado)
				.append("\n@@@@@@ nmpoliza=").append(nmpoliza)
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		
		ManagerRespuestaSmapVO resp = new ManagerRespuestaSmapVO(true);
		
		try
		{
			resp.setSmap(cotizacionDAO.cargarClienteCotizacion(cdunieco,cdramo,estado,nmpoliza));
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(new StringBuilder("Error al obtener cliente de cotizacion #").append(timestamp).toString());
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ cargarClienteCotizacion @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	///////////////////////////////
	////// getters y setters //////
	public void setCotizacionDAO(CotizacionDAO cotizacionDAO) {
		this.cotizacionDAO = cotizacionDAO;
	}

	public void setPantallasDAO(PantallasDAO pantallasDAO) {
		this.pantallasDAO = pantallasDAO;
	}
	
}