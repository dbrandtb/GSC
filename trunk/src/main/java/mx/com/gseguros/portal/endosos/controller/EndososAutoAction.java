package mx.com.gseguros.portal.endosos.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.SlistSmapVO;
import mx.com.gseguros.portal.endosos.service.EndososAutoManager;
import mx.com.gseguros.portal.general.util.TipoEndoso;
import mx.com.gseguros.utils.Utilerias;
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
	private SimpleDateFormat         renderFechas     = new SimpleDateFormat("dd/MM/yyyy");
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
		logger.info(Utilerias.join(
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
		
		logger.info(Utilerias.join(
				 "\n###### result=",result
				,"\n###### marcoEndosos ######"
				,"\n##########################"
				));
		return result;
	}
	
	public String recuperarColumnasIncisoRamo()
	{
		logger.info(Utilerias.join(
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
		
		logger.info(Utilerias.join(
				 "\n###### recuperarColumnasIncisoRamo ######"
				,"\n#########################################"
				));
		return SUCCESS;
	}
	
	public String recuperarEndososClasificados()
	{
		logger.info(Utilerias.join(
				 "\n##########################################"
				,"\n###### recuperarEndososClasificados ######"
				,"\n###### smap1="  , smap1
				,"\n###### slist1=" , slist1
				));
		
		try
		{
			Utils.validate(smap1, "No se recibieron datos de entrada");
			String cdramo   = smap1.get("cdramo");
			String nivel    = smap1.get("nivel");
			String multiple = smap1.get("multiple");
			String tipoflot = smap1.get("tipoflot");
			
			Utils.validate(cdramo   , "No se recibio el producto");
			Utils.validate(nivel    , "No se recibio el nivel de endoso");
			Utils.validate(multiple , "No se recibio el tipo de seleccion");
			Utils.validate(tipoflot , "No se recibio el tipo de poliza");
			
			Utils.validate(session                , "No hay sesion");
			Utils.validate(session.get("USUARIO") , "No hay usuario en la sesion");
			String cdsisrol = ((UserVO)session.get("USUARIO")).getRolActivo().getClave();
			
			if(slist1==null)
			{
				slist1=new ArrayList<Map<String,String>>();
			}
			
			SlistSmapVO resp=endososAutoManager.recuperarEndososClasificados(
					cdramo
					,nivel
					,multiple
					,tipoflot
					,slist1
					,cdsisrol
					);
			
			smap1.putAll(resp.getSmap());
			slist1=resp.getSlist();
			
			success = true;
		}
		catch(Exception ex)
		{
			respuesta=Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utilerias.join(
				 "\n###### recuperarEndososClasificados ######"
				,"\n##########################################"
				));
		return SUCCESS;
	}
	
	public String pantallaEndosoValosit()
	{
		logger.info(Utilerias.join(
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
			smap1.put("tstamp" , String.format("%.0f.%.0f",(double)System.currentTimeMillis(),1000d*Math.random()));
			
			imap = endososAutoManager.pantallaEndosoValosit(cdtipsup,cdramo);
			
			result = SUCCESS;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utilerias.join(
				 "\n###### result="    , result
				,"\n###### respuesta=" , respuesta
				,"\n###### pantallaEndosoValosit ######"
				,"\n###################################"
				));
		return result;
	}
	
	public String guardarTvalositEndoso()
	{
		logger.info(Utilerias.join(
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
		
		logger.info(Utilerias.join(
				 "\n###### guardarTvalositEndoso ######"
				,"\n###################################"
				));
		return SUCCESS;
	}
	
	public String confirmarEndosoTvalositAuto()
	{
		logger.info(Utilerias.join(
				 "\n#########################################"
				,"\n###### confirmarEndosoTvalositAuto ######"
				,"\n###### smap1=",smap1
				));
		
		try
		{
			Utils.validate(smap1,"No se recibieron datos");
			
			String cdtipsup = smap1.get("cdtipsup");
			String tstamp   = smap1.get("tstamp");
			String cdunieco = smap1.get("cdunieco");
			String cdramo   = smap1.get("cdramo");
			String estado   = smap1.get("estado");
			String nmpoliza = smap1.get("nmpoliza");
			
			Utils.validate(cdtipsup  , "No se recibio el tipo de endoso");
			Utils.validate(tstamp    , "No se recibio el id de proceso");
			Utils.validate(cdunieco  , "No se recibio la sucursal");
			Utils.validate(cdramo    , "No se recibio el producto");
			Utils.validate(estado    , "No se recibio el estado");
			Utils.validate(nmpoliza  , "No se recibio el numero de poliza");
			
			Utils.validate(session                , "No hay sesion");
			Utils.validate(session.get("USUARIO") , "No hay usuario en la sesion");
			
			UserVO usuarioSesion = (UserVO)session.get("USUARIO");
			String cdusuari = ((UserVO)session.get("USUARIO")).getUser();
			String cdsisrol = ((UserVO)session.get("USUARIO")).getRolActivo().getClave();
			String cdelemen = ((UserVO)session.get("USUARIO")).getEmpresa().getElementoId();
			
			endososAutoManager.confirmarEndosoTvalositAuto(
					cdtipsup
					,tstamp
					,cdunieco
					,cdramo
					,estado
					,nmpoliza
					,cdusuari
					,cdsisrol
					,cdelemen
					,usuarioSesion
					);
			
			success = true;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utilerias.join(
				 "\n###### success="   , success
				,"\n###### respuesta=" , respuesta
				,"\n###### confirmarEndosoTvalositAuto ######"
				,"\n#########################################"
				));
		return SUCCESS;
	}
	
	public String recuperarDatosEndosoAltaIncisoAuto()
	{
		logger.info(Utilerias.join(
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
		
		logger.info(Utilerias.join(
				 "\n###### success="   , success
				,"\n###### respuesta=" , respuesta
				,"\n###### recuperarDatosEndosoAltaIncisoAuto ######"
				,"\n################################################"
				));
		return SUCCESS;
	}
	
	public String confirmarEndosoAltaIncisoAuto()
	{
		logger.info(Utilerias.join(
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
			
			String cdunieco = smap1.get("cdunieco");
			String cdramo   = smap1.get("cdramo");
			String estado   = smap1.get("estado");
			String nmpoliza = smap1.get("nmpoliza");
			String cdtipsup = smap1.get("cdtipsup");
			
			Utils.validate(cdunieco , "No se recibio la sucursal");
			Utils.validate(cdramo   , "No se recibio la sucursal");
			Utils.validate(estado   , "No se recibio el estado de la poliza");
			Utils.validate(nmpoliza , "No se recibio el numero de poliza");
			Utils.validate(cdtipsup , "No se recibio el codigo de endoso");
			
			endososAutoManager.confirmarEndosoAltaIncisoAuto(cdunieco,cdramo,estado,nmpoliza,slist1,cdusuari,cdelemen,cdtipsup, usuarioSesion);
			
			respuesta = "Endoso generado correctamente";
			success   = true;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utilerias.join(
				 "\n###### success="   , success
				,"\n###### respuesta=" , respuesta
				,"\n###### confirmarEndosoAltaIncisoAuto ######"
				,"\n###########################################"
				));
		return SUCCESS;
	}
	
	public String endosoBajaIncisos()
	{
		logger.info(Utilerias.join(
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
		
		logger.info(Utilerias.join(
				 "\n###### success="   , success
				,"\n###### respuesta=" , respuesta
				,"\n###### endosoBajaIncisos ######"
				,"\n###############################"
				));
		return result;
	}
	
	public String confirmarEndosoBajaIncisos()
	{
		logger.info(Utilerias.join(
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
			
			String cdunieco = smap1.get("CDUNIECO");
			String cdramo   = smap1.get("CDRAMO");
			String estado   = smap1.get("ESTADO");
			String nmpoliza = smap1.get("NMPOLIZA");
			String cdtipsup = smap1.get("cdtipsup");
			
			Utils.validate(cdunieco , "No se recibio la sucursal");
			Utils.validate(cdramo   , "No se recibio la sucursal");
			Utils.validate(estado   , "No se recibio el estado de la poliza");
			Utils.validate(nmpoliza , "No se recibio el numero de poliza");
			Utils.validate(cdtipsup , "No se recibio el codigo de endoso");
			
			endososAutoManager.confirmarEndosoBajaIncisos(cdunieco,cdramo,estado,nmpoliza,slist1,cdusuari,cdelemen,cdtipsup,usuarioSesion);
			
			respuesta = "Endoso generado correctamente";
			success   = true;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utilerias.join(
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
	
	public String endosoVigenciaPoliza()
	{
		logger.info(Utilerias.join(
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
			success   = true;
			
			smap1.put("pv_cdunieco", smap1.get("CDUNIECO"));
			smap1.put("pv_cdramo", smap1.get("CDRAMO"));
			smap1.put("pv_estado", smap1.get("ESTADO"));
			smap1.put("pv_nmpoliza", smap1.get("NMPOLIZA"));
			smap1.put("pv_cdperson", smap1.get("CDPERSON"));
			smap1.put("pv_diasMinimo", retroactividad.get(0).get("DIASMINIMO"));
			smap1.put("pv_diasMaximo", retroactividad.get(0).get("DIASMAXIMO"));
			
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
			.append("\n######  endosoVigenciaPoliza   ######")
			.append("\n#####################################")
			.append("\n#####################################").toString());
		} catch (Exception ex) {
			respuesta = Utils.manejaExcepcion(ex);
		}
		return isSuccess() ? SUCCESS : ERROR;
	}
	
	
	
		public String guardarEndosoAseguradoAlterno() {
        
		logger.info(Utilerias.join(
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
			
			Utils.validate(cdunieco , "No se recibio la sucursal");
			Utils.validate(cdramo   , "No se recibio el producto");
			Utils.validate(estado   , "No se recibio el estado de la poliza");
			Utils.validate(nmpoliza , "No se recibio el numero de poliza");
			Utils.validate(session                , "No hay sesion");
			Utils.validate(session.get("USUARIO") , "No hay usuario en la sesion");
			
			String cdusuari = ((UserVO)session.get("USUARIO")).getUser();
			String cdsisrol = ((UserVO)session.get("USUARIO")).getRolActivo().getClave();
			String cdelemen = ((UserVO)session.get("USUARIO")).getEmpresa().getElementoId();
			
			String cdtipsup      = TipoEndoso.ASEGURADO_ALTERNO.getCdTipSup().toString();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String fechaEndoso   = sdf.format(new Date());
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
		
		logger.info(Utilerias.join(
				 "\n###### guardarEndosoAseguradoAlterno ######"
				,"\n###########################################"
				));
		return SUCCESS;
	}
	
	public String guardarEndosoVigenciaPoliza() {
        
		logger.info(Utilerias.join(
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
			
			String cdtipsup      = TipoEndoso.ASEGURADO_ALTERNO.getCdTipSup().toString();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String fechaEndoso   = sdf.format(new Date());
			Date   dFechaEndoso  = renderFechas.parse(fechaEndoso);
			
			endososAutoManager.guardarEndosoVigenciaPoliza(
					cdunieco,
					cdramo,
					estado,
					nmpoliza,
					cdelemen,
					cdusuari,
					cdtipsup,
					status,
					fechaEndoso,
					dFechaEndoso,
					feefecto,
					feproren);
			respuesta = "Endoso generado correctamente";
			success   = true;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utilerias.join(
				 "\n###### guardarEndosoCambioVigencia ######"
				,"\n#########################################"
				));
		return SUCCESS;
	}
	
	public String endosoClaveAuto()
	{
		logger.info(Utilerias.join(
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
		
		logger.info(Utilerias.join(
				 "\n###### result="    , result
				,"\n###### respuesta=" , respuesta
				,"\n###### endosoClaveAuto ######"
				,"\n#############################"
				));
		return result;
	}
	
	public String guardarEndosoClaveAuto()
	{
		logger.info(Utilerias.join(
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
					);
			
			success = true;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.info(Utilerias.join(
				 "\n###### guardarEndosoClaveAuto ######"
				,"\n####################################"
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