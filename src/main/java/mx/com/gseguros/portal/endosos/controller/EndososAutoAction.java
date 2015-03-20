package mx.com.gseguros.portal.endosos.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.SlistSmapVO;
import mx.com.gseguros.portal.endosos.service.EndososAutoManager;
import mx.com.gseguros.utils.Utilerias;
import mx.com.gseguros.utils.Utils;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionContext;

public class EndososAutoAction extends PrincipalCoreAction
{
	private static Logger logger = Logger.getLogger(EndososAutoAction.class);
	
	private boolean                  success;
	private String                   respuesta;
	private Map<String,String>       smap1;
	private Map<String,String>       smap2;
	private Map<String,Item>         imap;
	private List<Map<String,String>> slist1;
	
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
			String cdsisrol = ((UserVO)session.get("USUARIO")).getRolActivo().getClave();
			smap1.put("cdusuari" , ((UserVO)session.get("USUARIO")).getUser());
			smap1.put("cdsisrol" , cdsisrol);
			
			imap = endososAutoManager.construirMarcoEndosos(cdsisrol);
			
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
			
			endososAutoManager.confirmarEndosoAltaIncisoAuto(cdunieco,cdramo,estado,nmpoliza,slist1,cdusuari,cdelemen,cdtipsup);
			
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
			
			endososAutoManager.confirmarEndosoBajaIncisos(cdunieco,cdramo,estado,nmpoliza,slist1,cdusuari,cdelemen,cdtipsup);
			
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

	public void setEndososAutoManager(EndososAutoManager endososAutoManager) {
		this.endososAutoManager = endososAutoManager;
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