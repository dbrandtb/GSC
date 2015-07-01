package mx.com.gseguros.portal.endosos.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.SlistSmapVO;
import mx.com.gseguros.portal.endosos.service.EndososAutoManager;
import mx.com.gseguros.portal.general.util.TipoEndoso;
import mx.com.gseguros.utils.Utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class EndososAutoAction extends PrincipalCoreAction
{
	private static final long serialVersionUID = -7583914370456999908L;

	private static Logger logger = Logger.getLogger(EndososAutoAction.class);
	
	private boolean                  success;
	private String                   respuesta;
	private Map<String,String>       smap1;
	private Map<String,String>       smap2;
	private Map<String,Item>         imap;
	private List<Map<String,String>> slist1;
	
	private SimpleDateFormat renderFechas = new SimpleDateFormat("dd/MM/yyyy");
	
	private boolean exito           = false;
	private String  respuestaOculta = null;
	
	@Autowired
	private EndososAutoManager endososAutoManager;
	
	public EndososAutoAction()
	{
		this.session=ActionContext.getContext().getSession();
	}
	
	public String marcoEndosos()
	{
		logger.info(Utils.join(
				 "\n##########################"
				,"\n###### marcoEndosos ######"
				));
		
		String result = ERROR;
		
		try
		{
			Utils.validate(session                , "No hay sesion");
			Utils.validate(session.get("USUARIO") , "No hay usuario en la sesion");
			if(smap1==null)
			{
				smap1=new HashMap<String,String>();
			}
			String cdusuari = ((UserVO)session.get("USUARIO")).getUser();
			String cdsisrol = ((UserVO)session.get("USUARIO")).getRolActivo().getClave();
			smap1.put("cdusuari" , ((UserVO)session.get("USUARIO")).getUser());
			smap1.put("cdsisrol" , cdsisrol);
			
			Map<String,Object> valores = endososAutoManager.construirMarcoEndosos(cdusuari,cdsisrol);
			imap = (Map<String,Item>)valores.get("items");
			smap1.put("cdagente" , (String)valores.get("cdagente"));
			
			result = SUCCESS;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utils.join(
				 "\n###### result=",result
				,"\n###### marcoEndosos ######"
				,"\n##########################"
				));
		return result;
	}
	
	public String recuperarColumnasIncisoRamo()
	{
		logger.info(Utils.join(
				 "\n#########################################"
				,"\n###### recuperarColumnasIncisoRamo ######"
				,"\n###### smap1=",smap1
				));
		
		try
		{
			Utils.validate(smap1  , "No se recibieron datos");
			String cdramo=smap1.get("cdramo");
			Utils.validate(cdramo , "No se recibio el producto");
			
			smap1.put("columnas" , endososAutoManager.recuperarColumnasIncisoRamo(cdramo));
			
			success = true;
		}
		catch(Exception ex)
		{
			respuesta=Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utils.join(
				 "\n###### recuperarColumnasIncisoRamo ######"
				,"\n#########################################"
				));
		return SUCCESS;
	}
	
	public String recuperarEndososClasificados()
	{
		logger.info(Utils.join(
				 "\n##########################################"
				,"\n###### recuperarEndososClasificados ######"
				,"\n###### smap1="  , smap1
				,"\n###### slist1=" , slist1
				));
		
		try
		{
			Utils.validate(smap1, "No se recibieron datos de entrada");
			String cdramo    = smap1.get("cdramo");
			String nivel     = smap1.get("nivel");
			String multiple  = smap1.get("multiple");
			String tipoflot  = smap1.get("tipoflot");
			String cancelada = smap1.get("cancelada");
			
			Utils.validate(cdramo    , "No se recibio el producto");
			Utils.validate(nivel     , "No se recibio el nivel de endoso");
			Utils.validate(multiple  , "No se recibio el tipo de seleccion");
			Utils.validate(tipoflot  , "No se recibio el tipo de poliza");
			Utils.validate(cancelada , "No se recibio el status de vigencia de poliza");
			
			Utils.validate(session                , "No hay sesion");
			Utils.validate(session.get("USUARIO") , "No hay usuario en la sesion");
			String cdsisrol = ((UserVO)session.get("USUARIO")).getRolActivo().getClave();
			String cdusuari = ((UserVO)session.get("USUARIO")).getUser();
			
			if(slist1==null)
			{
				slist1=new ArrayList<Map<String,String>>();
			}
			
			SlistSmapVO resp = endososAutoManager.recuperarEndososClasificados(
					cdramo, nivel, multiple, tipoflot, slist1, cdsisrol,
					cancelada, cdusuari);
			
			smap1.putAll(resp.getSmap());
			slist1=resp.getSlist();
			
			success = true;
		}
		catch(Exception ex)
		{
			respuesta=Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utils.join(
				 "\n###### recuperarEndososClasificados ######"
				,"\n##########################################"
				));
		return SUCCESS;
	}
	
	public String pantallaEndosoValosit()
	{
		logger.info(Utils.join(
				 "\n###################################"
				,"\n###### pantallaEndosoValosit ######"
				,"\n###### smap1="  , smap1
				,"\n###### slist1=" , slist1
				));
		
		String result=ERROR;
		
		try
		{
			Utils.validate(smap1,"No se recibieron datos de poliza");
			
			String cdtipsup = smap1.get("cdtipsup");
			String cdramo   = smap1.get("CDRAMO");
			Utils.validate(cdramo , "No se recibio el tipo de endoso");
			Utils.validate(cdramo , "No se recibio el producto");
			
			//override del tstamp
			smap1.put("tstamp" , Utils.generaTimestamp());
			
			String cdsisrol = ((UserVO)session.get("USUARIO")).getRolActivo().getClave();
			imap = endososAutoManager.pantallaEndosoValosit(cdtipsup,cdramo, cdsisrol);
			
			result = SUCCESS;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utils.join(
				 "\n###### result="    , result
				,"\n###### respuesta=" , respuesta
				,"\n###### pantallaEndosoValosit ######"
				,"\n###################################"
				));
		return result;
	}
	
	public String guardarTvalositEndoso()
	{
		logger.info(Utils.join(
				 "\n###################################"
				,"\n###### guardarTvalositEndoso ######"
				,"\n###### smap1=",smap1
				));
		
		try
		{
			Utils.validate(smap1,"No se recibieron datos");
			
			endososAutoManager.guardarTvalositEndoso(
					smap1.get("CDUNIECO")
					,smap1.get("CDRAMO")
					,smap1.get("ESTADO")
					,smap1.get("NMPOLIZA")
					,smap1.get("NMSITUAC")
					,smap1.get("NMSUPLEM")
					,smap1.get("STATUS")
					,smap1.get("CDTIPSIT")
					,smap1.get("OTVALOR01"),smap1.get("OTVALOR02"),smap1.get("OTVALOR03"),smap1.get("OTVALOR04"),smap1.get("OTVALOR05")
					,smap1.get("OTVALOR06"),smap1.get("OTVALOR07"),smap1.get("OTVALOR08"),smap1.get("OTVALOR09"),smap1.get("OTVALOR10")
					,smap1.get("OTVALOR11"),smap1.get("OTVALOR12"),smap1.get("OTVALOR13"),smap1.get("OTVALOR14"),smap1.get("OTVALOR15")
					,smap1.get("OTVALOR16"),smap1.get("OTVALOR17"),smap1.get("OTVALOR18"),smap1.get("OTVALOR19"),smap1.get("OTVALOR20")
					,smap1.get("OTVALOR21"),smap1.get("OTVALOR22"),smap1.get("OTVALOR23"),smap1.get("OTVALOR24"),smap1.get("OTVALOR25")
					,smap1.get("OTVALOR26"),smap1.get("OTVALOR27"),smap1.get("OTVALOR28"),smap1.get("OTVALOR29"),smap1.get("OTVALOR30")
					,smap1.get("OTVALOR31"),smap1.get("OTVALOR32"),smap1.get("OTVALOR33"),smap1.get("OTVALOR34"),smap1.get("OTVALOR35")
					,smap1.get("OTVALOR36"),smap1.get("OTVALOR37"),smap1.get("OTVALOR38"),smap1.get("OTVALOR39"),smap1.get("OTVALOR40")
					,smap1.get("OTVALOR41"),smap1.get("OTVALOR42"),smap1.get("OTVALOR43"),smap1.get("OTVALOR44"),smap1.get("OTVALOR45")
					,smap1.get("OTVALOR46"),smap1.get("OTVALOR47"),smap1.get("OTVALOR48"),smap1.get("OTVALOR49"),smap1.get("OTVALOR50")
					,smap1.get("OTVALOR51"),smap1.get("OTVALOR52"),smap1.get("OTVALOR53"),smap1.get("OTVALOR54"),smap1.get("OTVALOR55")
					,smap1.get("OTVALOR56"),smap1.get("OTVALOR57"),smap1.get("OTVALOR58"),smap1.get("OTVALOR59"),smap1.get("OTVALOR60")
					,smap1.get("OTVALOR61"),smap1.get("OTVALOR62"),smap1.get("OTVALOR63"),smap1.get("OTVALOR64"),smap1.get("OTVALOR65")
					,smap1.get("OTVALOR66"),smap1.get("OTVALOR67"),smap1.get("OTVALOR68"),smap1.get("OTVALOR69"),smap1.get("OTVALOR70")
					,smap1.get("OTVALOR71"),smap1.get("OTVALOR72"),smap1.get("OTVALOR73"),smap1.get("OTVALOR74"),smap1.get("OTVALOR75")
					,smap1.get("OTVALOR76"),smap1.get("OTVALOR77"),smap1.get("OTVALOR78"),smap1.get("OTVALOR79"),smap1.get("OTVALOR80")
					,smap1.get("OTVALOR81"),smap1.get("OTVALOR82"),smap1.get("OTVALOR83"),smap1.get("OTVALOR84"),smap1.get("OTVALOR85")
					,smap1.get("OTVALOR86"),smap1.get("OTVALOR87"),smap1.get("OTVALOR88"),smap1.get("OTVALOR89"),smap1.get("OTVALOR90")
					,smap1.get("OTVALOR91"),smap1.get("OTVALOR92"),smap1.get("OTVALOR93"),smap1.get("OTVALOR94"),smap1.get("OTVALOR95")
					,smap1.get("OTVALOR96"),smap1.get("OTVALOR97"),smap1.get("OTVALOR98"),smap1.get("OTVALOR99")
					,smap1.get("tstamp")
					);
			
			success = true;
		}
		catch(Exception ex)
		{
			respuesta=Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utils.join(
				 "\n###### guardarTvalositEndoso ######"
				,"\n###################################"
				));
		return SUCCESS;
	}
	
	public String confirmarEndosoTvalositAuto()
	{
		logger.info(Utils.join(
				 "\n#########################################"
				,"\n###### confirmarEndosoTvalositAuto ######"
				,"\n###### smap1="  , smap1
				,"\n###### slist1=" , slist1
				));
		
		try
		{
			Utils.validate(session                , "No hay sesion");
			Utils.validate(session.get("USUARIO") , "No hay usuario en la sesion");
			
			UserVO usuarioSesion = (UserVO)session.get("USUARIO");
			String cdusuari = ((UserVO)session.get("USUARIO")).getUser();
			String cdsisrol = ((UserVO)session.get("USUARIO")).getRolActivo().getClave();
			String cdelemen = ((UserVO)session.get("USUARIO")).getEmpresa().getElementoId();
			
			Utils.validate(smap1  , "No se recibieron datos");
			Utils.validate(slist1 , "No se recibieron datos de inciso");
			
			String cdtipsup = smap1.get("cdtipsup");
			String tstamp   = smap1.get("tstamp");
			String cdunieco = smap1.get("cdunieco");
			String cdramo   = smap1.get("cdramo");
			String estado   = smap1.get("estado");
			String nmpoliza = smap1.get("nmpoliza");
			String feefecto = smap1.get("feefecto");
			
			Utils.validate(cdtipsup  , "No se recibio el tipo de endoso");
			Utils.validate(tstamp    , "No se recibio el id de proceso");
			Utils.validate(cdunieco  , "No se recibio la sucursal");
			Utils.validate(cdramo    , "No se recibio el producto");
			Utils.validate(estado    , "No se recibio el estado");
			Utils.validate(nmpoliza  , "No se recibio el numero de poliza");
			Utils.validate(feefecto  , "No se recibio la fecha de efecto");
			
			endososAutoManager.confirmarEndosoTvalositAuto(
					cdtipsup
					,tstamp
					,cdunieco
					,cdramo
					,estado
					,nmpoliza
					,feefecto
					,cdusuari
					,cdsisrol
					,cdelemen
					,usuarioSesion
					,slist1
					);
			
			success = true;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utils.join(
				 "\n###### success="   , success
				,"\n###### respuesta=" , respuesta
				,"\n###### confirmarEndosoTvalositAuto ######"
				,"\n#########################################"
				));
		return SUCCESS;
	}
	
	public String recuperarDatosEndosoAltaIncisoAuto()
	{
		logger.info(Utils.join(
				 "\n################################################"
				,"\n###### recuperarDatosEndosoAltaIncisoAuto ######"
				,"\n###### smap1=",smap1
				));
		
		try
		{
			Utils.validate(smap1 , "No se recibieron datos");
			
			String cdunieco = smap1.get("cdunieco");
			String cdramo   = smap1.get("cdramo");
			String estado   = smap1.get("estado");
			String nmpoliza = smap1.get("nmpoliza");
			String nmsuplem = smap1.get("nmsuplem");
			
			Utils.validate(cdunieco , "No se recibio la sucursal");
			Utils.validate(cdramo   , "No se recibio el producto");
			Utils.validate(estado   , "No se recibio el estado");
			Utils.validate(nmpoliza , "No se recibio el numero de poliza");
			Utils.validate(nmsuplem , "No se recibio el suplemento");
			
			Map<String,Object> valores = endososAutoManager.recuperarDatosEndosoAltaIncisoAuto(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					,nmsuplem
					);
			
			smap1 = (Map<String,String>)valores.get("incisoPoliza");
			smap1.putAll((Map<String,String>)valores.get("tvalopol"));
			smap1.put("CDPERSON" , (String)valores.get("cdperson"));
			smap1.put("CDIDEPER" , (String)valores.get("cdideper"));
			slist1 = (List<Map<String,String>>)valores.get("tconvalsit");
			
			success = true;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utils.join(
				 "\n###### success="   , success
				,"\n###### respuesta=" , respuesta
				,"\n###### recuperarDatosEndosoAltaIncisoAuto ######"
				,"\n################################################"
				));
		return SUCCESS;
	}
	
	public String confirmarEndosoAltaIncisoAuto()
	{
		logger.info(Utils.join(
				 "\n###########################################"
				,"\n###### confirmarEndosoAltaIncisoAuto ######"
				,"\n###### smap1="  , smap1
				,"\n###### slist1=" , slist1
				));
		
		try
		{
			Utils.validate(session                , "No hay sesion");
			Utils.validate(session.get("USUARIO") , "No hay usuario en la sesion");
			
			UserVO usuarioSesion = (UserVO)session.get("USUARIO");
			String cdusuari = ((UserVO)session.get("USUARIO")).getUser();
			String cdelemen = ((UserVO)session.get("USUARIO")).getEmpresa().getElementoId();
					
			Utils.validate(smap1  , "No se recibieron datos de poliza");
			Utils.validate(slist1 , "No se recibieron incisos");
			
			String cdunieco    = smap1.get("cdunieco");
			String cdramo      = smap1.get("cdramo");
			String estado      = smap1.get("estado");
			String nmpoliza    = smap1.get("nmpoliza");
			String cdtipsup    = smap1.get("cdtipsup");
			String fechaEndoso = smap1.get("fechaEndoso");
			
			Utils.validate(cdunieco    , "No se recibio la sucursal");
			Utils.validate(cdramo      , "No se recibio la sucursal");
			Utils.validate(estado      , "No se recibio el estado de la poliza");
			Utils.validate(nmpoliza    , "No se recibio el numero de poliza");
			Utils.validate(cdtipsup    , "No se recibio el codigo de endoso");
			Utils.validate(fechaEndoso , "No se recibio la fecha de efecto");
			
			endososAutoManager.confirmarEndosoAltaIncisoAuto(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					,slist1
					,cdusuari
					,cdelemen
					,cdtipsup
					,fechaEndoso
					,usuarioSesion
					);
			
			respuesta = "Endoso generado correctamente";
			success   = true;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utils.join(
				 "\n###### success="   , success
				,"\n###### respuesta=" , respuesta
				,"\n###### confirmarEndosoAltaIncisoAuto ######"
				,"\n###########################################"
				));
		return SUCCESS;
	}
	
	public String endosoBajaIncisos()
	{
		logger.info(Utils.join(
				 "\n###############################"
				,"\n###### endosoBajaIncisos ######"
				,"\n###### smap1(poliza)="   , smap1
				,"\n###### slist1(incisos)=" , slist1
				));
		
		String result = ERROR;
		try
		{
			Utils.validate(smap1  , "No se recibieron datos de poliza");
			Utils.validate(slist1 , "No se recibieron incisos");
			
			String cdramo = smap1.get("CDRAMO");
			Utils.validate(cdramo , "No se recibio la sucursal");
	
			imap = endososAutoManager.endosoBajaIncisos(cdramo);
			
			result = SUCCESS;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utils.join(
				 "\n###### success="   , success
				,"\n###### respuesta=" , respuesta
				,"\n###### endosoBajaIncisos ######"
				,"\n###############################"
				));
		return result;
	}
	
	public String confirmarEndosoBajaIncisos()
	{
		logger.info(Utils.join(
				 "\n########################################"
				,"\n###### confirmarEndosoBajaIncisos ######"
				,"\n###### smap1="  , smap1
				,"\n###### slist1=" , slist1
				));
		
		try
		{
			Utils.validate(session                , "No hay sesion");
			Utils.validate(session.get("USUARIO") , "No hay usuario en la sesion");
			
			UserVO usuarioSesion = (UserVO)session.get("USUARIO");
			String cdusuari = ((UserVO)session.get("USUARIO")).getUser();
			String cdelemen = ((UserVO)session.get("USUARIO")).getEmpresa().getElementoId();
					
			Utils.validate(smap1  , "No se recibieron datos de poliza");
			Utils.validate(slist1 , "No se recibieron incisos");
			
			String cdunieco    = smap1.get("CDUNIECO");
			String cdramo      = smap1.get("CDRAMO");
			String estado      = smap1.get("ESTADO");
			String nmpoliza    = smap1.get("NMPOLIZA");
			String cdtipsup    = smap1.get("cdtipsup");
			String fechaEndoso = smap1.get("fechaEndoso");
			String devolucionP = smap1.get("devoPrim");
			
			Utils.validate(cdunieco    , "No se recibio la sucursal");
			Utils.validate(cdramo      , "No se recibio la sucursal");
			Utils.validate(estado      , "No se recibio el estado de la poliza");
			Utils.validate(nmpoliza    , "No se recibio el numero de poliza");
			Utils.validate(cdtipsup    , "No se recibio el codigo de endoso");
			Utils.validate(fechaEndoso , "No se recibio la fecha de efecto");
			Utils.validate(devolucionP , "No se recibio el parametro de devolucion de prima");
			
			endososAutoManager.confirmarEndosoBajaIncisos(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					,slist1
					,cdusuari
					,cdelemen
					,cdtipsup
					,fechaEndoso
					,usuarioSesion
					,"S".equals(devolucionP)
					);
			
			respuesta = "Endoso generado correctamente";
			success   = true;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utils.join(
				 "\n###### success="   , success
				,"\n###### respuesta=" , respuesta
				,"\n###### confirmarEndosoBajaIncisos ######"
				,"\n########################################"
				));
		return SUCCESS;
	}

	public String endosoAseguradoAlterno()
	{
		
		smap1.put("pv_cdunieco", smap1.get("CDUNIECO"));
		smap1.put("pv_cdramo", smap1.get("CDRAMO"));
		smap1.put("pv_estado", smap1.get("ESTADO"));
		smap1.put("pv_nmpoliza", smap1.get("NMPOLIZA"));
		smap1.put("pv_cdperson", smap1.get("CDPERSON"));
		smap1.put("FEINIVAL", null);
		
		logger.debug(new StringBuilder()
		.append("\n#####################################")
		.append("\n#####################################")
		.append("\n###### endosoAseguradoAlterno ######")
		.append("\n######                         ######").toString());
		logger.debug(new StringBuilder("smap1: ").append(smap1).toString());
		logger.debug(new StringBuilder("session: ").append(session).toString());		
		logger.debug(new StringBuilder()
		.append("\n######                         ######")
		.append("\n###### endosoAseguradoAlterno  ######")
		.append("\n#####################################")
		.append("\n#####################################").toString());
		
		return SUCCESS;
	}

	public String endosoTextoLibre()
	{
		logger.info(Utils.join(
				"\n###########################################"
				,"\n###########################################"
				,"\n###### 		endosoTextoLibre 	     ######"
				,"\n###### smap1="  , smap1
				,"\n######                               ######"));
		try {
			String cdunieco = smap1.get("CDUNIECO");
			String cdramo   = smap1.get("CDRAMO");
			String estado   = smap1.get("ESTADO");
			String nmpoliza = smap1.get("NMPOLIZA");
			String cdtipsup = TipoEndoso.ENDOSO_B_LIBRE.getCdTipSup().toString();
			
			Utils.validate(session                , "No hay sesion");
			Utils.validate(session.get("USUARIO") , "No hay usuario en la sesion");
			String cdsisrol = ((UserVO)session.get("USUARIO")).getRolActivo().getClave();
			logger.debug("VALOR DEL DATO -->"+cdsisrol);
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String fechaProceso   = sdf.format(new Date());
			//1.Obtenemos los n�meros de d�as Maximo y Minimo
			List<Map<String,String>> retroactividad = endososAutoManager.obtenerRetroactividad(cdsisrol,cdramo,cdtipsup, fechaProceso);
			endososAutoManager.validarEndosoAnterior(cdunieco, cdramo, estado, nmpoliza, cdtipsup);
//			endososAutoManager.validarEndosoPagados(cdunieco, cdramo, estado, nmpoliza);
			success   = true;
			
			smap1.put("pv_cdunieco", smap1.get("CDUNIECO"));
			smap1.put("pv_cdramo", smap1.get("CDRAMO"));
			smap1.put("pv_estado", smap1.get("ESTADO"));
			smap1.put("pv_nmpoliza", smap1.get("NMPOLIZA"));
			smap1.put("pv_cdperson", smap1.get("CDPERSON"));
			smap1.put("pv_diasMinimo", retroactividad.get(0).get("DIASMINIMO"));
			smap1.put("pv_diasMaximo", retroactividad.get(0).get("DIASMAXIMO"));
			smap1.put("FEINIVAL", null);
			
			String FEEFECTO[] = smap1.get("FEEFECTO").toString().split("\\/");
			String FEPROREN[] = smap1.get("FEPROREN").toString().split("\\/");
			Calendar cal1 = Calendar.getInstance();
	        Calendar cal2 = Calendar.getInstance();
	        // Establecer las fechas
	        cal1.set(Integer.parseInt(FEEFECTO[2].toString()), Integer.parseInt(FEEFECTO[1].toString()) , Integer.parseInt(FEEFECTO[0].toString()));
	        cal2.set(Integer.parseInt(FEPROREN[2].toString()), Integer.parseInt(FEPROREN[1].toString()) , Integer.parseInt(FEPROREN[0].toString()));
	        long milis1 = cal1.getTimeInMillis();
	        long milis2 = cal2.getTimeInMillis();
	        long diff = milis2 - milis1;
	        long diffDays = diff / (24 * 60 * 60 * 1000);
	        
			smap1.put("pv_difDate",diffDays+"");
			logger.debug(new StringBuilder()
			.append("\n######                         ######")
			.append("\n######  endosoTextoLibre   ######")
			.append("\n#####################################")
			.append("\n#####################################").toString());
		} catch (Exception ex) {
			respuesta = Utils.manejaExcepcion(ex);
		}
		return isSuccess() ? SUCCESS : ERROR;
	}
	
	public String endosoVigenciaPoliza()
	{
		logger.info(Utils.join(
				"\n###########################################"
				,"\n###########################################"
				,"\n###### 		endosoVigenciaPoliza 	 ######"
				,"\n###### smap1="  , smap1
				,"\n######                               ######"));
		try {
			String cdunieco = smap1.get("CDUNIECO");
			String cdramo   = smap1.get("CDRAMO");
			String estado   = smap1.get("ESTADO");
			String nmpoliza = smap1.get("NMPOLIZA");
			String cdtipsup      = TipoEndoso.VIGENCIA_POLIZA.getCdTipSup().toString();
			
			Utils.validate(session                , "No hay sesion");
			Utils.validate(session.get("USUARIO") , "No hay usuario en la sesion");
			String cdsisrol = ((UserVO)session.get("USUARIO")).getRolActivo().getClave();
			logger.debug("VALOR DEL DATO -->"+cdsisrol);
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String fechaProceso   = sdf.format(new Date());
			//1.Obtenemos los n�meros de d�as Maximo y Minimo
			List<Map<String,String>> retroactividad = endososAutoManager.obtenerRetroactividad(cdsisrol,cdramo,cdtipsup, fechaProceso);
			endososAutoManager.validarEndosoAnterior(cdunieco, cdramo, estado, nmpoliza, cdtipsup);
			endososAutoManager.validarEndosoPagados(cdunieco, cdramo, estado, nmpoliza);
			endososAutoManager.validaEndosoCambioVigencia(cdunieco, cdramo, estado, nmpoliza);
			success   = true;
			
			smap1.put("pv_cdunieco", smap1.get("CDUNIECO"));
			smap1.put("pv_cdramo", smap1.get("CDRAMO"));
			smap1.put("pv_estado", smap1.get("ESTADO"));
			smap1.put("pv_nmpoliza", smap1.get("NMPOLIZA"));
			smap1.put("pv_cdperson", smap1.get("CDPERSON"));
			
			smap1.put("FEINIVAL", null);
			
			String FEEFECTO[] = smap1.get("FEEFECTO").toString().split("\\/");
			String FEPROREN[] = smap1.get("FEPROREN").toString().split("\\/");
			Calendar cal1 = Calendar.getInstance();
	        Calendar cal2 = Calendar.getInstance();
	        // Establecer las fechas
	        cal1.set(Integer.parseInt(FEEFECTO[2].toString()), Integer.parseInt(FEEFECTO[1].toString()) , Integer.parseInt(FEEFECTO[0].toString()));
	        cal2.set(Integer.parseInt(FEPROREN[2].toString()), Integer.parseInt(FEPROREN[1].toString()) , Integer.parseInt(FEPROREN[0].toString()));
	        long milis1 = cal1.getTimeInMillis();
	        long milis2 = cal2.getTimeInMillis();
	        long diff = milis2 - milis1;
	        long diffDays = diff / (24 * 60 * 60 * 1000);
	        
			smap1.put("pv_difDate",diffDays+"");
			
	        Date dateMin = java.sql.Date.valueOf(FEEFECTO[2].toString()+"-"+FEEFECTO[1].toString()+"-"+FEEFECTO[0].toString());
	        Calendar calMin = new GregorianCalendar();
	        calMin.setTime(dateMin);
	        //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	        calMin.add(Calendar.DATE, -Integer.parseInt(retroactividad.get(0).get("DIASMINIMO")));
	        String fechaMinima = sdf.format(calMin.getTime());
			smap1.put("pv_fechaMinima", fechaMinima);
			
			Date dateMax = java.sql.Date.valueOf(FEEFECTO[2].toString()+"-"+FEEFECTO[1].toString()+"-"+FEEFECTO[0].toString());
	        Calendar calMax = new GregorianCalendar();
	        calMax.setTime(dateMax);
	        calMax.add(Calendar.DATE, Integer.parseInt(retroactividad.get(0).get("DIASMAXIMO")));
	        String fechaMaxima = sdf.format(calMax.getTime());
	        smap1.put("pv_fechaMaxima", fechaMaxima);
			logger.debug(new StringBuilder()
			.append("\n######                         ######")
			.append("\n######  endosoVigenciaPoliza   ######")
			.append("\n#####################################")
			.append("\n#####################################").toString());
		} catch (Exception ex) {
			respuesta = Utils.manejaExcepcion(ex);
		}
		return isSuccess() ? SUCCESS : ERROR;
	}
	
	public String endosoAmpliacionVigencia()
	{
		logger.info(Utils.join(
				"\n###########################################"
				,"\n###########################################"
				,"\n###### 		endosoAmpliacionVigencia 	 ######"
				,"\n###### smap1="  , smap1
				,"\n######                               ######"));
		try {
			String cdunieco = smap1.get("CDUNIECO");
			String cdramo   = smap1.get("CDRAMO");
			String estado   = smap1.get("ESTADO");
			String nmpoliza = smap1.get("NMPOLIZA");
			String cdtipsup      = TipoEndoso.AMPLIACION_DE_VIGENCIA.getCdTipSup().toString();
			
			Utils.validate(session                , "No hay sesion");
			Utils.validate(session.get("USUARIO") , "No hay usuario en la sesion");
			String cdsisrol = ((UserVO)session.get("USUARIO")).getRolActivo().getClave();
			logger.debug("VALOR DEL DATO -->"+cdsisrol);
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String fechaProceso   = sdf.format(new Date());
			//1.Obtenemos los n�meros de d�as Maximo y Minimo
			List<Map<String,String>> retroactividad = endososAutoManager.obtenerRetroactividad(cdsisrol,cdramo,cdtipsup, fechaProceso);
			endososAutoManager.validarEndosoAnterior(cdunieco, cdramo, estado, nmpoliza, cdtipsup);
//			endososAutoManager.validarEndosoPagados(cdunieco, cdramo, estado, nmpoliza);
			success   = true;
			
			smap1.put("pv_cdunieco", smap1.get("CDUNIECO"));
			smap1.put("pv_cdramo", smap1.get("CDRAMO"));
			smap1.put("pv_estado", smap1.get("ESTADO"));
			smap1.put("pv_nmpoliza", smap1.get("NMPOLIZA"));
			smap1.put("pv_cdperson", smap1.get("CDPERSON"));
			smap1.put("pv_diasMinimo", retroactividad.get(0).get("DIASMINIMO"));
			smap1.put("pv_diasMaximo", retroactividad.get(0).get("DIASMAXIMO"));
			smap1.put("FEINIVAL", null);
			
			String FEEFECTO[] = smap1.get("FEEFECTO").toString().split("\\/");
			String FEPROREN[] = smap1.get("FEPROREN").toString().split("\\/");
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			// Establecer las fechas
			cal1.set(Integer.parseInt(FEEFECTO[2].toString()), Integer.parseInt(FEEFECTO[1].toString()) , Integer.parseInt(FEEFECTO[0].toString()));
			cal2.set(Integer.parseInt(FEPROREN[2].toString()), Integer.parseInt(FEPROREN[1].toString()) , Integer.parseInt(FEPROREN[0].toString()));
			long milis1 = cal1.getTimeInMillis();
			long milis2 = cal2.getTimeInMillis();
			long diff = milis2 - milis1;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			
			smap1.put("pv_difDate",diffDays+"");
			logger.debug(new StringBuilder()
			.append("\n######                            ######")
			.append("\n######  endosoAmpliacionVigencia  ######")
			.append("\n########################################")
			.append("\n########################################").toString());
		} catch (Exception ex) {
			respuesta = Utils.manejaExcepcion(ex);
		}
		return isSuccess() ? SUCCESS : ERROR;
	}
	
	public String endosoDespago()
	{
		logger.info(Utils.join(
				"\n##########################################"
				,"\n#########################################"
				,"\n###### 		endosoDespago 	       ######"
				,"\n###### smap1="  , smap1
				,"\n######                             ######"));
		try {
			
			Utils.validate(session                , "No hay sesion");
			Utils.validate(session.get("USUARIO") , "No hay usuario en la sesion");
			String cdsisrol = ((UserVO)session.get("USUARIO")).getRolActivo().getClave();
			logger.debug("VALOR DEL DATO -->"+cdsisrol);
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String fechaProceso   = sdf.format(new Date());
			
			
			smap1.put("pv_cdunieco", smap1.get("CDUNIECO"));
			smap1.put("pv_cdramo", smap1.get("CDRAMO"));
			smap1.put("pv_estado", smap1.get("ESTADO"));
			smap1.put("pv_nmpoliza", smap1.get("NMPOLIZA"));
			smap1.put("pv_cdperson", smap1.get("CDPERSON"));
			smap1.put("FEINIVAL", fechaProceso);
			
			success = true;
			
			logger.debug(new StringBuilder()
			.append("\n######                         ######")
			.append("\n######  endosoVigenciaPoliza   ######")
			.append("\n#####################################")
			.append("\n#####################################").toString());
		} catch (Exception ex) {
			respuesta = Utils.manejaExcepcion(ex);
		}
		return isSuccess() ? SUCCESS : ERROR;
	}
	
	
		public String guardarEndosoAseguradoAlterno() {
        
		logger.info(Utils.join(
				"\n###########################################"
				,"\n###########################################"
				,"\n###### guardarEndosoAseguradoAlterno ######"
				,"\n###### smap1="  , smap1
				,"\n######                               ######"));
		try
		{
			logger.debug("Validando datos de entrada");
			Utils.validate(smap1, "No se recibieron datos");
			
			String cdunieco = smap1.get("CDUNIECO");
			String cdramo   = smap1.get("CDRAMO");
			String estado   = smap1.get("ESTADO");
			String nmpoliza = smap1.get("NMPOLIZA");
			String status   = smap1.get("STATUS");
			String ntramite = smap1.get("NTRAMITE");
			
			Utils.validate(cdunieco , "No se recibio la sucursal");
			Utils.validate(cdramo   , "No se recibio el producto");
			Utils.validate(estado   , "No se recibio el estado de la poliza");
			Utils.validate(nmpoliza , "No se recibio el numero de poliza");
			Utils.validate(session                , "No hay sesion");
			Utils.validate(session.get("USUARIO") , "No hay usuario en la sesion");
			
			String cdusuari = ((UserVO)session.get("USUARIO")).getUser();
			String cdsisrol = ((UserVO)session.get("USUARIO")).getRolActivo().getClave();
			String cdelemen = ((UserVO)session.get("USUARIO")).getEmpresa().getElementoId();
			String cdtipsup = TipoEndoso.ASEGURADO_ALTERNO.getCdTipSup().toString();
			String fechaEndoso   = smap1.get("FEINIVAL");
			Date   dFechaEndoso  = renderFechas.parse(fechaEndoso);
			
			Map<String,String> otvalores = new HashMap<String,String>();
			for(int i = 1; i<= 50; i++){
				otvalores.put(new StringBuilder("otvalor").append(StringUtils.leftPad(String.valueOf(i), 2, "0")).toString(),smap1.get(new StringBuilder("OTVALOR").append(StringUtils.leftPad(String.valueOf(i), 2, "0")).toString()));
			}
			
			endososAutoManager.guardarEndosoAseguradoAlterno(
					cdunieco,
					cdramo,
					estado,
					nmpoliza,
					ntramite,
					cdelemen,
					cdusuari,
					cdtipsup,
					status,
					fechaEndoso,
					dFechaEndoso,
					otvalores);
			
			respuesta = "Endoso generado correctamente";
			success   = true;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utils.join(
				 "\n###### guardarEndosoAseguradoAlterno ######"
				,"\n###########################################"
				));
		return SUCCESS;
	}
	
	public String guardarEndosoVigenciaPoliza() {
        
		logger.info(Utils.join(
				"\n############################################"
				,"\n###########################################"
				,"\n######  guardarEndosoCambioVigencia  ######"
				,"\n###### smap1="  , smap1
				,"\n######                               ######"));
		try
		{
			logger.debug("Validando datos de entrada");
			Utils.validate(smap1, "No se recibieron datos");
			
			String cdunieco = smap1.get("CDUNIECO");
			String cdramo   = smap1.get("CDRAMO");
			String estado   = smap1.get("ESTADO");
			String nmpoliza = smap1.get("NMPOLIZA");
			String status   = smap1.get("STATUS");
			String feefecto = smap1.get("FEEFECTO");
			String feproren = smap1.get("FEPROREN");
			String ntramite = smap1.get("NTRAMITE");
			String nmsuplemOriginal = smap1.get("NMSUPLEM");
			
			Utils.validate(cdunieco , "No se recibio la sucursal");
			Utils.validate(cdramo   , "No se recibio el producto");
			Utils.validate(estado   , "No se recibio el estado de la poliza");
			Utils.validate(nmpoliza , "No se recibio el numero de poliza");
			Utils.validate(status   , "No se recibio el status");
			Utils.validate(feefecto , "No se recibio la fecha feproren");
			Utils.validate(feproren , "No se recibio la fecha feproren");
			
			
			Utils.validate(session                , "No hay sesion");
			Utils.validate(session.get("USUARIO") , "No hay usuario en la sesion");
			
			String cdusuari = ((UserVO)session.get("USUARIO")).getUser();
			String cdsisrol = ((UserVO)session.get("USUARIO")).getRolActivo().getClave();
			String cdelemen = ((UserVO)session.get("USUARIO")).getEmpresa().getElementoId();
			
			String cdtipsup      = TipoEndoso.VIGENCIA_POLIZA.getCdTipSup().toString();
			String fechaEndoso   = smap1.get("FEEFECTO");
			Date   dFechaEndoso  = renderFechas.parse(fechaEndoso);
			
			endososAutoManager.guardarEndosoVigenciaPoliza(
					cdunieco,
					cdramo,
					estado,
					nmpoliza,
					ntramite,
					cdelemen,
					cdusuari,
					cdtipsup,
					status,
					fechaEndoso,
					dFechaEndoso,
					feefecto,
					feproren,
					nmsuplemOriginal);
			respuesta = "Endoso generado correctamente";
			success   = true;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utils.join(
				 "\n###### guardarEndosoCambioVigencia ######"
				,"\n#########################################"
				));
		return SUCCESS;
	}
	
	public String guardarEndosoAmpliacionVigencia() {
		
		logger.info(Utils.join(
				"\n############################################"
				,"\n###########################################"
				,"\n######  guardarEndosoAmpliacionVigencia  ######"
				,"\n###### smap1="  , smap1
				,"\n######                               ######"));
		try
		{
			logger.debug("Validando datos de entrada");
			Utils.validate(smap1, "No se recibieron datos");
			
			String cdunieco = smap1.get("CDUNIECO");
			String cdramo   = smap1.get("CDRAMO");
			String estado   = smap1.get("ESTADO");
			String nmpoliza = smap1.get("NMPOLIZA");
			String status   = smap1.get("STATUS");
			String feefecto = smap1.get("FEEFECTO");
			String feproren = smap1.get("FEPROREN");
			String ntramite = smap1.get("NTRAMITE");
			String nmsuplemOriginal = smap1.get("NMSUPLEM");
			
			Utils.validate(cdunieco , "No se recibio la sucursal");
			Utils.validate(cdramo   , "No se recibio el producto");
			Utils.validate(estado   , "No se recibio el estado de la poliza");
			Utils.validate(nmpoliza , "No se recibio el numero de poliza");
			Utils.validate(status   , "No se recibio el status");
			Utils.validate(feefecto , "No se recibio la fecha feproren");
			Utils.validate(feproren , "No se recibio la fecha feproren");
			
			
			Utils.validate(session                , "No hay sesion");
			Utils.validate(session.get("USUARIO") , "No hay usuario en la sesion");
			
			UserVO usuarioSesion = (UserVO)session.get("USUARIO");
			String cdusuari = ((UserVO)session.get("USUARIO")).getUser();
			String cdsisrol = ((UserVO)session.get("USUARIO")).getRolActivo().getClave();
			String cdelemen = ((UserVO)session.get("USUARIO")).getEmpresa().getElementoId();
			
			String cdtipsup      = TipoEndoso.AMPLIACION_DE_VIGENCIA.getCdTipSup().toString();
			String fechaEndoso   = smap1.get("FEINIVAL");
			Date   dFechaEndoso  = renderFechas.parse(fechaEndoso);
			
			String tipoGrupoInciso = smap1.get("TIPOFLOT");
			
			endososAutoManager.guardarEndosoAmpliacionVigencia(
					cdunieco,
					cdramo,
					estado,
					nmpoliza,
					ntramite,
					cdelemen,
					cdusuari,
					cdtipsup,
					status,
					fechaEndoso,
					dFechaEndoso,
					feefecto,
					feproren,
					nmsuplemOriginal,
					usuarioSesion,
					tipoGrupoInciso
					);
			
			respuesta = "Endoso generado correctamente";
			success   = false;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utils.join(
				"\n###### guardarEndosoAmpliacionVigencia ######"
				,"\n#########################################"
				));
		return SUCCESS;
	}
	
	public String guardarEndosoDespago() {
		
		logger.info(Utils.join(
				"\n############################################"
				,"\n###########################################"
				,"\n######  guardarEndosoDespago         ######"
				,"\n###### smap1="  , smap1
				,"\n######                               ######"));
		try
		{
			logger.debug("Validando datos de entrada");
			Utils.validate(smap1, "No se recibieron datos");
			
			String cdunieco = smap1.get("CDUNIECO");
			String cdramo   = smap1.get("CDRAMO");
			String estado   = smap1.get("ESTADO");
			String nmpoliza = smap1.get("NMPOLIZA");
//			String status   = smap1.get("STATUS");
//			String ntramite = smap1.get("NTRAMITE");
			String nmsuplemOriginal = smap1.get("NMSUPLEM");
			String nmrecibo = smap1.get("NMRECIBO");
			String nmimpres = smap1.get("NMIMPRES");
			
			Utils.validate(cdunieco , "No se recibio la sucursal");
			Utils.validate(cdramo   , "No se recibio el producto");
			Utils.validate(estado   , "No se recibio el estado de la poliza");
			Utils.validate(nmpoliza , "No se recibio el numero de poliza");
//			Utils.validate(status   , "No se recibio el status");
			
			
			Utils.validate(session                , "No hay sesion");
			Utils.validate(session.get("USUARIO") , "No hay usuario en la sesion");
			
			UserVO usuarioSesion = (UserVO)session.get("USUARIO");
			
			String cdtipsup      = TipoEndoso.DESPAGO.getCdTipSup().toString();
			String fechaEndoso   = smap1.get("FEINIVAL");
//			Date   dFechaEndoso  = renderFechas.parse(fechaEndoso);
			
			endososAutoManager.guardarEndosoDespago(
					cdunieco,
					cdramo,
					estado,
					nmpoliza,
					nmsuplemOriginal,
					nmrecibo,
					nmimpres,
					cdtipsup,
					usuarioSesion
					);
			respuesta = "Endoso generado correctamente";
			success   = true;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utils.join(
				"\n###### guardarEndosoDespago ######"
				,"\n#########################################"
				));
		return SUCCESS;
	}

	public String guardarEndosoTextoLibre() {
		
		logger.info(Utils.join(
				"\n############################################"
				,"\n###########################################"
				,"\n######  guardarEndosoTextoLibre  ######"
				,"\n###### smap1 ="  , smap1
				,"\n###### slist1="  , slist1
				,"\n######                               ######"));
		try
		{
			logger.debug("Validando datos de entrada");
			Utils.validate(smap1, "No se recibieron datos");
			
			String cdunieco = smap1.get("CDUNIECO");
			String cdramo   = smap1.get("CDRAMO");
			String estado   = smap1.get("ESTADO");
			String nmpoliza = smap1.get("NMPOLIZA");
			String status   = smap1.get("STATUS");
			String feefecto = smap1.get("FEEFECTO");
			String feproren = smap1.get("FEPROREN");
			String ntramite = smap1.get("NTRAMITE");
			String dslinea  = smap1.get("TEXTOEND");
			List<Map<String,String>> situaciones = null;
			
			if(slist1 != null && !slist1.isEmpty() && slist1.get(0).containsKey("NMSITUAC") && StringUtils.isNotBlank(slist1.get(0).get("NMSITUAC"))){
				situaciones = slist1;
			}else{
				HashMap<String,String> nivelPoliza = new HashMap<String, String>();
				nivelPoliza.put("NIVEL_POLIZA", "NIVEL_POLIZA");
				nivelPoliza.put("NMSITUAC", "0");
				situaciones = new ArrayList<Map<String,String>>();
				situaciones.add(nivelPoliza);
			}
			
			
			Utils.validate(cdunieco , "No se recibio la sucursal");
			Utils.validate(cdramo   , "No se recibio el producto");
			Utils.validate(estado   , "No se recibio el estado de la poliza");
			Utils.validate(nmpoliza , "No se recibio el numero de poliza");
			Utils.validate(status   , "No se recibio el status");
			Utils.validate(feefecto , "No se recibio la fecha feproren");
			Utils.validate(feproren , "No se recibio la fecha feproren");
			
			
			Utils.validate(session                , "No hay sesion");
			Utils.validate(session.get("USUARIO") , "No hay usuario en la sesion");
			
			String cdusuari = ((UserVO)session.get("USUARIO")).getUser();
			String cdsisrol = ((UserVO)session.get("USUARIO")).getRolActivo().getClave();
			String cdelemen = ((UserVO)session.get("USUARIO")).getEmpresa().getElementoId();
			
			String cdtipsup      = TipoEndoso.ENDOSO_B_LIBRE.getCdTipSup().toString();
			String fechaEndoso   = smap1.get("FEINIVAL");
			Date   dFechaEndoso  = renderFechas.parse(fechaEndoso);
			
			endososAutoManager.guardarEndosoTextoLibre(
					cdunieco,
					cdramo,
					estado,
					nmpoliza,
					ntramite,
					cdelemen,
					cdusuari,
					cdtipsup,
					status,
					fechaEndoso,
					dFechaEndoso,
					feefecto,
					feproren,
					situaciones,
					dslinea);
			respuesta = "Endoso generado correctamente";
			success   = true;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utils.join(
				"\n###### guardarEndosoTextoLibre ######"
				,"\n#########################################"
				));
		return SUCCESS;
	}
	
	public String endosoClaveAuto()
	{
		logger.info(Utils.join(
				 "\n#############################"
				,"\n###### endosoClaveAuto ######"
				,"\n###### smap1="  , smap1
				,"\n###### slist1=" , slist1
				));

		String result = ERROR;
		
		try
		{
			Utils.validate(session                , "No hay sesion");
			Utils.validate(session.get("USUARIO") , "No hay usuario en la sesion");
			String cdsisrol = ((UserVO)session.get("USUARIO")).getRolActivo().getClave();
			smap1.put("cdsisrol" , cdsisrol);
			
			Utils.validate(smap1  , "No se recibieron datos de poliza");
			Utils.validate(slist1 , "No se recibieron datos de inciso");
			
			String cdramo   = slist1.get(0).get("CDRAMO");
			String cdtipsit = slist1.get(0).get("CDTIPSIT");
			
			imap = endososAutoManager.endosoClaveAuto(cdsisrol,cdramo,cdtipsit);
			
			result = SUCCESS;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utils.join(
				 "\n###### result="    , result
				,"\n###### respuesta=" , respuesta
				,"\n###### endosoClaveAuto ######"
				,"\n#############################"
				));
		return result;
	}
	
	public String guardarEndosoClaveAuto()
	{
		logger.info(Utils.join(
				 "\n####################################"
				,"\n###### guardarEndosoClaveAuto ######"
				,"\n###### smap1="  , smap1 
				,"\n###### smap2="  , smap2
				,"\n###### slist1=" , slist1
				));
		
		try
		{
			Utils.validate(session                , "No hay sesion");
			Utils.validate(session.get("USUARIO") , "No hay usuario en la sesion");
			
			String cdusuari = ((UserVO)session.get("USUARIO")).getUser();
			String cdelemen = ((UserVO)session.get("USUARIO")).getEmpresa().getElementoId();
			String cdsisrol = ((UserVO)session.get("USUARIO")).getRolActivo().getClave();
			UserVO usuarioSesion = (UserVO)session.get("USUARIO");
			
			Utils.validate(smap1  , "No se recibieron datos de poliza");
			Utils.validate(smap2  , "No se recibieron datos nuevos de inciso");
			Utils.validate(slist1 , "No se recibio el inciso");
			
			String cdtipsup = smap1.get("cdtipsup");
			String cdunieco = smap1.get("CDUNIECO");
			String cdramo   = smap1.get("CDRAMO");
			String estado   = smap1.get("ESTADO");
			String nmpoliza = smap1.get("NMPOLIZA");
			String feefecto = smap2.get("feefecto");
			
			Utils.validate(cdtipsup , "No se recibio el tipo de endoso");
			Utils.validate(cdunieco , "No se recibio la sucursal");
			Utils.validate(cdramo   , "No se recibio el producto");
			Utils.validate(estado   , "No se recibio el estado de la poliza");
			Utils.validate(nmpoliza , "No se recibio el numero de poliza");
			Utils.validate(feefecto , "No se recibio la fecha de efecto");
			
			endososAutoManager.guardarEndosoClaveAuto(
					cdtipsup
					,cdusuari
					,cdsisrol
					,cdelemen
					,cdunieco
					,cdramo
					,estado
					,nmpoliza
					,feefecto
					,smap2
					,slist1.get(0)
					,usuarioSesion
					);
			
			success = true;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utils.join(
				 "\n###### guardarEndosoClaveAuto ######"
				,"\n####################################"
				));
		return SUCCESS;
	}
	
	public String endosoDevolucionPrimas()
	{
		logger.info(Utils.join(
				 "\n####################################"
				,"\n###### endosoDevolucionPrimas ######"
				,"\n###### smap1="  , smap1
				,"\n###### slist1=" , slist1
				));

		String result = ERROR;
		
		try
		{
			Utils.validate(smap1  , "No se recibieron datos de poliza");
			Utils.validate(slist1 , "No se recibieron incisos");
			
			String cdramo   = smap1.get("CDRAMO");
			String cdtipsup = smap1.get("cdtipsup");
			
			Utils.validate(cdramo   , "No se recibio el producto");
			Utils.validate(cdtipsup , "No se recibio el codigo de endoso");
			
			smap1.put("tstamp" , Utils.generaTimestamp());
			
			imap = endososAutoManager.endosoDevolucionPrimas(cdtipsup, cdramo);
			
			result = SUCCESS;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utils.join(
				"\n###### endosoDevolucionPrimas ######"
				,"\n####################################"
				));
		return result;
	}
	
	public String guardarEndosoDevolucionPrimas()
	{
		logger.info(Utils.join(
				 "\n###########################################"
				,"\n###### guardarEndosoDevolucionPrimas ######"
				,"\n###### smap1="  , smap1
				,"\n###### smap2="  , smap2
				,"\n###### slist1=" , slist1
				));
		
		try
		{
			Utils.validate(smap1  , "No se recibieron datos de poliza");
			Utils.validate(smap2  , "No se recibieron datos de endoso");
			Utils.validate(slist1 , "No se recibieron incisos");
			
			UserVO user = Utils.validateSession(session);
			UserVO usuarioSesion = (UserVO)session.get("USUARIO");
			
			String cdunieco = smap1.get("CDUNIECO");
			String cdramo   = smap1.get("CDRAMO");
			String estado   = smap1.get("ESTADO");
			String nmpoliza = smap1.get("NMPOLIZA");
			String cdtipsup = smap1.get("cdtipsup");
			String tstamp   = smap1.get("tstamp");
			String fechaEnd = smap2.get("feefecto");
			
			Utils.validate(cdunieco , "No se recibio la sucursal");
			Utils.validate(cdramo   , "No se recibio el producto");
			Utils.validate(estado   , "No se recibio el estado de la poliza");
			Utils.validate(nmpoliza , "No se recibio el numero de poliza");
			Utils.validate(cdtipsup , "No se recibio el codigo de endoso");
			Utils.validate(tstamp   , "No se recibio el ID de proceso");
			Utils.validate(fechaEnd , "No se recibio la fecha de efecto");
			
			endososAutoManager.guardarEndosoDevolucionPrimas(
					user.getUser()
					,user.getRolActivo().getClave()
					,user.getEmpresa().getElementoId()
					,cdunieco
					,cdramo
					,estado
					,nmpoliza
					,cdtipsup
					,tstamp
					,renderFechas.parse(fechaEnd)
					,slist1
					,usuarioSesion
					);
			
			success = true;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utils.join(
				 "\n###### guardarEndosoDevolucionPrimas ######"
				,"\n###########################################"
				));
		return SUCCESS;
	}
	
	public String endosoRehabilitacionAuto()
	{
		logger.info(Utils.join(
				 "\n######################################"
				,"\n###### endosoRehabilitacionAuto ######"
				,"\n###### smap1=",smap1
				));
		
		String result = ERROR;
		try
		{
			UserVO user = Utils.validateSession(session);
			
			Utils.validate(smap1 , "No se recibieron datos");
			
			String cdramo = smap1.get("CDRAMO");
			
			Utils.validate(cdramo , "No se recibio el producto");
			
			imap = endososAutoManager.endosoRehabilitacionAuto(user.getRolActivo().getClave(),cdramo);
			
			result = SUCCESS;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utils.join(
				 "\n###### result=",result
				,"\n###### endosoRehabilitacionAuto ######"
				,"\n######################################"
				));
		return result;
	}
	
	public String confirmarEndosoRehabilitacionAuto()
	{
		logger.info(Utils.join(
				 "\n###############################################"
				,"\n###### confirmarEndosoRehabilitacionAuto ######"
				,"\n###### smap1=",smap1
				));
		
		try
		{
			UserVO user = Utils.validateSession(session);
			
			UserVO usuarioSesion = (UserVO)session.get("USUARIO");
			
			Utils.validate(smap1 , "No se recibieron datos");
			
			String cdunieco = smap1.get("cdunieco");
			String cdramo   = smap1.get("cdramo");
			String estado   = smap1.get("estado");
			String nmpoliza = smap1.get("nmpoliza");
			String cdtipsup = smap1.get("cdtipsup");
			String nsuplogi = smap1.get("nsuplogi");
			String cddevcia = smap1.get("cddevcia");
			String cdgestor = smap1.get("cdgestor");
			String feemisio = smap1.get("feemisio");
			String feinival = smap1.get("feinival");
			String fefinval = smap1.get("fefinval");
			String feefecto = smap1.get("feefecto");
			String feproren = smap1.get("feproren");
			String cdmoneda = smap1.get("cdmoneda");
			String nmsuplem = smap1.get("nmsuplem");
			
			Utils.validate(cdunieco , "No se recibio la sucursal");
			Utils.validate(cdramo   , "No se recibio el producto");
			Utils.validate(estado   , "No se recibio el estado de la poliza");
			Utils.validate(nmpoliza , "No se recibio el numero de poliza");
			Utils.validate(cdtipsup , "No se recibio el codigo de endoso");
			Utils.validate(nsuplogi , "No se recibio el consecutivo de endoso");
			Utils.validate(cddevcia , "No se recibio el tipo de endoso");
			Utils.validate(cdgestor , "No se recibio el numero de endoso");
			Utils.validate(feemisio , "No se recibio la fecha de emision de endoso");
			Utils.validate(feinival , "No se recibio la fecha de inicio de endoso");
			Utils.validate(fefinval , "No se recibio la fecha de fin de endoso");
			Utils.validate(feefecto , "No se recibio la fecha de efecto");
			Utils.validate(feproren , "No se recibio la fecha de proxima renovacion");
			Utils.validate(cdmoneda , "No se recibio la clave de moneda");
			Utils.validate(nmsuplem , "No se recibio el numero de suplemento");
			
			endososAutoManager.confirmarEndosoRehabilitacionAuto(
					user.getUser()
					,user.getRolActivo().getClave()
					,cdunieco
					,cdramo
					,estado
					,nmpoliza
					,cdtipsup
					,nsuplogi
					,cddevcia
					,cdgestor
					,renderFechas.parse(feemisio)
					,renderFechas.parse(feinival)
					,renderFechas.parse(fefinval)
					,renderFechas.parse(feefecto)
					,renderFechas.parse(fefinval)
					,cdmoneda
					,nmsuplem
					,user.getEmpresa().getElementoId()
					,usuarioSesion
					);
			
			success = true;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utils.join(
				 "\n###### confirmarEndosoRehabilitacionAuto ######"
				,"\n###############################################"
				));
		return SUCCESS;
	}
	
	public String endosoCancelacionAuto()
	{
		logger.info(Utils.join(
				 "\n###################################"
				,"\n###### endosoCancelacionAuto ######"
				,"\n###### smap1=",smap1
				));
		
		String result = ERROR;
		try
		{
			UserVO user = Utils.validateSession(session);
			
			Utils.validate(smap1 , "No se recibieron datos");
			
			String cdramo = smap1.get("CDRAMO");
			
			Utils.validate(cdramo , "No se recibio el producto");
			
			imap = endososAutoManager.endosoCancelacionAuto(user.getRolActivo().getClave(),cdramo);
			
			result = SUCCESS;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utils.join(
				 "\n###### result=",result
				,"\n###### endosoCancelacionAuto ######"
				,"\n###################################"
				));
		return result;
	}
	
	public String buscarError()
	{
		logger.info(Utils.join(
				 "\n#########################"
				,"\n###### buscarError ######"
				,"\n###### smap1=",smap1
				));
		
		try
		{
			Utils.validate(smap1                , "No se recibieron datos");
			Utils.validate(smap1.get("error")   , "No se recibio el numero de error");
			Utils.validate(smap1.get("archivo") , "No se recibio el nombre del archivo");
			
			smap1.putAll(endososAutoManager.buscarError(smap1.get("error"),getText("logs.ruta"),smap1.get("archivo")));
			
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utils.join(
				 "\n###### buscarError ######"
				,"\n#########################"
				));
		return SUCCESS;
	}
	
	public String confirmarEndosoCancelacionAuto()
	{
		logger.info(Utils.join(
				 "\n############################################"
				,"\n###### confirmarEndosoCancelacionAuto ######"
				,"\n###### smap1=",smap1
				));
		
		try
		{
			UserVO user = Utils.validateSession(session);
			UserVO usuarioSesion = (UserVO)session.get("USUARIO");
			
			Utils.validate(smap1 , "No se recibieron datos");
			
			String cdunieco = smap1.get("cdunieco");
			String cdramo   = smap1.get("cdramo");
			String estado   = smap1.get("estado");
			String nmpoliza = smap1.get("nmpoliza");
			String cdtipsup = smap1.get("cdtipsup");
			String nsuplogi = smap1.get("nsuplogi");
			String cddevcia = smap1.get("cddevcia");
			String cdgestor = smap1.get("cdgestor");
			String feemisio = smap1.get("feemisio");
			String feinival = smap1.get("feinival");
			String fefinval = smap1.get("fefinval");
			String feefecto = smap1.get("feefecto");
			String feproren = smap1.get("feproren");
			String cdmoneda = smap1.get("cdmoneda");
			String nmsuplem = smap1.get("nmsuplem");
			String feinicio = smap1.get("feinicio");
			
			Utils.validate(cdunieco , "No se recibio la sucursal");
			Utils.validate(cdramo   , "No se recibio el producto");
			Utils.validate(estado   , "No se recibio el estado de la poliza");
			Utils.validate(nmpoliza , "No se recibio el numero de poliza");
			Utils.validate(cdtipsup , "No se recibio el codigo de endoso");
			Utils.validate(nsuplogi , "No se recibio el consecutivo de endoso");
			Utils.validate(cddevcia , "No se recibio el tipo de endoso");
			Utils.validate(cdgestor , "No se recibio el numero de endoso");
			Utils.validate(feemisio , "No se recibio la fecha de emision de endoso");
			Utils.validate(feinival , "No se recibio la fecha de inicio de endoso");
			Utils.validate(fefinval , "No se recibio la fecha de fin de endoso");
			Utils.validate(feefecto , "No se recibio la fecha de efecto");
			Utils.validate(feproren , "No se recibio la fecha de proxima renovacion");
			Utils.validate(cdmoneda , "No se recibio la clave de moneda");
			Utils.validate(nmsuplem , "No se recibio el numero de suplemento");
			Utils.validate(feinicio , "No se recibio la fecha de inicio");
			
			endososAutoManager.confirmarEndosoCancelacionAuto(
					user.getUser()
					,user.getRolActivo().getClave()
					,cdunieco
					,cdramo
					,estado
					,nmpoliza
					,cdtipsup
					,nsuplogi
					,cddevcia
					,cdgestor
					,renderFechas.parse(feemisio)
					,renderFechas.parse(feinival)
					,renderFechas.parse(fefinval)
					,renderFechas.parse(feefecto)
					,renderFechas.parse(fefinval)
					,cdmoneda
					,nmsuplem
					,user.getEmpresa().getElementoId()
					,renderFechas.parse(feinicio)
					,usuarioSesion
					);
			
			success = true;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utils.join(
				 "\n###### confirmarEndosoCancelacionAuto ######"
				,"\n############################################"
				));
		return SUCCESS;
	}
	
	public String endosoCancelacionPolAuto()
	{
		logger.info(Utils.join(
				 "\n######################################"
				,"\n###### endosoCancelacionPolAuto ######"
				,"\n###### smap1=",smap1
				));
		
		String result = ERROR;
		try
		{
			UserVO user = Utils.validateSession(session);
			
			Utils.validate(smap1 , "No se recibieron datos");
			
			String cdramo = smap1.get("CDRAMO");
			
			Utils.validate(cdramo , "No se recibio el ramo");
			
			imap = endososAutoManager.endosoCancelacionPolAuto(user.getRolActivo().getClave(),cdramo);
			
			result = SUCCESS;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utils.join(
				 "\n###### endosoCancelacionPolAuto ######"
				,"\n######################################"
				));
		return result;
	}
	
	public String marcarPolizaCancelarPorEndoso()
	{
		logger.info(Utils.join(
				 "\n###########################################"
				,"\n###### marcarPolizaCancelarPorEndoso ######"
				,"\n###### smap1=",smap1
				));
		
		try
		{
			Utils.validate(smap1 , "No se recibieron datos");
			String cdunieco = smap1.get("cdunieco");
			String cdramo   = smap1.get("cdramo");
			String nmpoliza = smap1.get("nmpoliza");
			
			smap1.putAll(endososAutoManager.marcarPolizaCancelarPorEndoso(cdunieco,cdramo,nmpoliza));
			
			success = true;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utils.join(
				 "\n###### marcarPolizaCancelarPorEndoso ######"
				,"\n###########################################"
				));
		return SUCCESS;
	}
	
	public String confirmarEndosoCancelacionPolAuto()
	{
		logger.info(Utils.join(
				 "\n###############################################"
				,"\n###### confirmarEndosoCancelacionPolAuto ######"
				,"\n###### smap1=",smap1
				));
		
		try
		{
			String cdusuari = Utils.validateSession(session).getUser();
			
			UserVO usuarioSesion = (UserVO)session.get("USUARIO"); 
			
			Utils.validate(smap1 , "No se recibieron datos");
			
			String cdunieco = smap1.get("cdunieco");
			String cdramo   = smap1.get("cdramo");
			String estado   = smap1.get("estado");
			String nmpoliza = smap1.get("nmpoliza");
			String cdrazon  = smap1.get("cdrazon");
			String feefecto = smap1.get("feefecto");
			String fevencim = smap1.get("fevencim");
			String fecancel = smap1.get("fecancel");
			String cdtipsup = smap1.get("cdtipsup");
			
			Utils.validate(cdunieco  , "No se recibio la sucursal"
					       ,cdramo   , "No se recibio el producto"
					       ,estado   , "No se recibio el estado de la poliza"
					       ,nmpoliza , "No se recibio el numero de poliza"
					       ,cdrazon  , "No se recibio el motivo de cancelacion"
					       ,feefecto , "No se recibio la fecha de inicio de vigencia"
					       ,fevencim , "No se recibio la fecha de fin de vigencia"
					       ,fecancel , "No se recibio la fecha de cancelacion"
					       ,cdtipsup , "NO se recibio la clave de endoso");
			
			endososAutoManager.confirmarEndosoCancelacionPolAuto(
					cdusuari
					,cdunieco
					,cdramo
					,estado
					,nmpoliza
					,cdrazon
					,renderFechas.parse(feefecto)
					,renderFechas.parse(fevencim)
					,renderFechas.parse(fecancel)
					,cdtipsup
					,usuarioSesion
					);
			
			success = true;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utils.join(
				 "\n###### confirmarEndosoCancelacionPolAuto ######"
				,"\n###############################################"
				));
		return SUCCESS;
	}
	
	public String endosoValositFormsAuto()
	{
		logger.info(Utils.join(
				 "\n####################################"
				,"\n###### endosoValositFormsAuto ######"
				,"\n###### smap1="  , smap1
				,"\n###### slist1=" , Utils.size(slist1)
				));
		
		String result = ERROR;
		try
		{
			UserVO user = Utils.validateSession(session);
			
			Utils.validate(smap1  , "No se recibieron datos");
			Utils.validate(slist1 , "No se recibieron incisos");
			
			String cdramo   = smap1.get("CDRAMO");
			String cdtipsup = smap1.get("cdtipsup");
			
			Utils.validate(cdramo    , "No se recibio el producto"
					       ,cdtipsup , "No se recibio el codigo de endoso");
			
			imap = endososAutoManager.endosoValositFormsAuto(
					cdtipsup
					,user.getRolActivo().getClave()
					,cdramo
					,slist1
					);
			
			result = SUCCESS;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utils.join(
				 "\n###### endosoValositFormsAuto ######"
				,"\n####################################"
				));
		return result;
	}
	
	public String confirmarEndosoValositFormsAuto()
	{
		logger.info(Utils.join(
				 "\n#############################################"
				,"\n###### confirmarEndosoValositFormsAuto ######"
				,"\n###### smap1="  , smap1
				,"\n###### slist1=" , Utils.size(slist1)
				));
		
		try
		{
			UserVO user = Utils.validateSession(session);
			UserVO usuarioSesion = (UserVO)session.get("USUARIO");
			
			Utils.validate(smap1  , "No se recibieron datos de poliza");
			Utils.validate(slist1 , "No se recibieron incisos");
			
			String cdtipsup = smap1.get("cdtipsup");
			String cdunieco = smap1.get("CDUNIECO");
			String cdramo   = smap1.get("CDRAMO");
			String estado   = smap1.get("ESTADO");
			String nmpoliza = smap1.get("NMPOLIZA");
			String feinival = smap1.get("feinival");
			
			Utils.validate(cdtipsup  , "No se recibio el codigo de endoso"
					       ,cdunieco , "No se recibio la sucursal"
					       ,cdramo   , "No se recibio el producto"
					       ,estado   , "No se recibio el estado de la poliza"
					       ,nmpoliza , "No se recibio el numero de poliza"
					       ,feinival , "No se recibio la fecha de endoso"
					       );
			
			endososAutoManager.confirmarEndosoValositFormsAuto(
					user.getUser()
					,user.getRolActivo().getClave()
					,user.getEmpresa().getElementoId()
					,cdtipsup
					,cdunieco
					,cdramo
					,estado
					,nmpoliza
					,renderFechas.parse(feinival)
					,slist1
					,usuarioSesion
					);
			
			success = true;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utils.join(
				 "\n###### confirmarEndosoValositFormsAuto ######"
				,"\n#############################################"
				));
		return SUCCESS;
	}
	
	public String endosoRehabilitacionPolAuto()
	{
		logger.info(Utils.join(
				 "\n#########################################"
				,"\n###### endosoRehabilitacionPolAuto ######"
				,"\n###### smap1=",smap1
				));
		
		String result = ERROR;
		try
		{
			UserVO user = Utils.validateSession(session);
			
			Utils.validate(smap1 , "No se recibieron datos");
			
			String cdramo = smap1.get("CDRAMO");
			
			Utils.validate(cdramo , "No se recibio el producto");
			
			imap = endososAutoManager.confirmarEndosoRehabilitacionPolAuto(user.getRolActivo().getClave(),cdramo);
			
			result = SUCCESS;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utils.join(
				 "\n###### endosoRehabilitacionPolAuto ######"
				,"\n#########################################"
				));
		return result;
	}
	
	public String marcarPolizaParaRehabilitar()
	{
		logger.info(Utils.join(
				 "\n#########################################"
				,"\n###### marcarPolizaParaRehabilitar ######"
				,"\n###### smap1=",smap1
				));
		
		try
		{
			Utils.validate(smap1 , "No se recibieron datos");
			
			String cdunieco = smap1.get("cdunieco");
			String cdramo   = smap1.get("cdramo");
			String nmpoliza = smap1.get("nmpoliza");
			
			Utils.validate(cdunieco  , "No se recibio la sucursal"
					       ,cdramo   , "No se recibio el producto"
					       ,nmpoliza , "No se recibio el numero de poliza"
					       );
			
			smap1.putAll(endososAutoManager.marcarPolizaParaRehabilitar(cdunieco,cdramo,nmpoliza));
			
			success = true;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utils.join(
				 "\n###### marcarPolizaParaRehabilitar ######"
				,"\n#########################################"
				));
		return SUCCESS;
	}
	
	public String confirmarEndosoRehabilitacionPolAuto()
	{
		logger.info(Utils.join(
				 "\n##################################################"
				,"\n###### confirmarEndosoRehabilitacionPolAuto ######"
				,"\n###### smap1=",smap1
				));
		
		try
		{
			Utils.validate(smap1 , "No se recibieron datos");
			
			String cdtipsup = smap1.get("cdtipsup");
			String cdunieco = smap1.get("cdunieco");
			String cdramo   = smap1.get("cdramo"); 
			String estado   = smap1.get("estado");
			String nmpoliza = smap1.get("nmpoliza");
			String f_efecto = smap1.get("feefecto");
			String f_proren = smap1.get("feproren");
			String f_cancel = smap1.get("fecancel");
			String f_inival = smap1.get("feinival");
			String cdrazon  = smap1.get("cdrazon");
			String cdperson = smap1.get("cdperson");
			String cdmoneda = smap1.get("cdmoneda");
			String nmcancel = smap1.get("nmcancel");
			String comments = smap1.get("comments");
			String nmsuplem = smap1.get("nmsuplem");
			
			UserVO usuarioSesion = (UserVO)session.get("USUARIO");
			
			Utils.validate(
					cdtipsup  , "No se recibio el codigo de endoso"
					,cdunieco , "No se recibio la sucursal"
					,cdramo   , "No se recibio el producto"
					,estado   , "No se recibio el estado"
					,nmpoliza , "No se recibio el numero de poliza"
					,f_efecto , "No se recibio el inicio de vigencia de la poliza"
					,f_proren , "No se recibio el fin de vigencia de la poliza"
					,f_cancel , "No se recibio la fecha de cancelacion"
					,f_inival , "No se recibio la fecha de rehabilitacion"
					,cdrazon  , "No se recibio el motivo de cancelacion"
					,cdperson , "No se recibio la clave de person"
					,cdmoneda , "No se recibio la clave de moneda"
					,nmcancel , "No se recibio el consecutivo de cancelacion"
					,nmsuplem , "No se recibio el numero de suplemento"
					);
			
			endososAutoManager.confirmarEndosoRehabilitacionPolAuto(
					cdtipsup
					,cdunieco
					,cdramo
					,estado
					,nmpoliza
					,renderFechas.parse(f_efecto)
					,renderFechas.parse(f_proren)
					,renderFechas.parse(f_cancel)
					,renderFechas.parse(f_inival)
					,cdrazon
					,cdperson
					,cdmoneda
					,nmcancel
					,comments
					,nmsuplem
					,usuarioSesion
					);
			
			success = true;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utils.join(
				 "\n###### confirmarEndosoRehabilitacionPolAuto ######"
				,"\n##################################################"
				));
		return SUCCESS;
	}
	
	
	public String obtieneRecibosPagados()
	{
		logger.info(Utils.join(
				 "\n##########################################"
				,"\n###### obtieneRecibosPagados        ######"
				,"\n###### smap1="  , smap1
				));
		
		try
		{
			String cdunieco = smap1.get("CDUNIECO");
			String cdramo   = smap1.get("CDRAMO");
			String estado   = smap1.get("ESTADO");
			String nmpoliza = smap1.get("NMPOLIZA");
			
			slist1 = endososAutoManager.obtieneRecibosPagados(cdunieco, cdramo,estado, nmpoliza);
			
			success = true;
		}
		catch(Exception ex)
		{
			respuesta=Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utils.join(
				 "\n###### obtieneRecibosPagados ######"
				,"\n##########################################"
				));
		return SUCCESS;
	}

	/*
	 * Getters y setters
	 */
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public Map<String, String> getSmap1() {
		return smap1;
	}

	public void setSmap1(Map<String, String> smap1) {
		this.smap1 = smap1;
	}

	public List<Map<String, String>> getSlist1() {
		return slist1;
	}

	public void setSlist1(List<Map<String, String>> slist1) {
		this.slist1 = slist1;
	}

	public Map<String, Item> getImap() {
		return imap;
	}

	public void setImap(Map<String, Item> imap) {
		this.imap = imap;
	}

	public Map<String, String> getSmap2() {
		return smap2;
	}

	public void setSmap2(Map<String, String> smap2) {
		this.smap2 = smap2;
	}
}