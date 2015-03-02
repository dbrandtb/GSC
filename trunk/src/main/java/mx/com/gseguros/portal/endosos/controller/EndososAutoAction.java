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
}