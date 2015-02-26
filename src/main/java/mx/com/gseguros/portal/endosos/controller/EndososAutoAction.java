package mx.com.gseguros.portal.endosos.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaImapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSlistSmapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSmapVO;
import mx.com.gseguros.portal.endosos.service.EndososAutoManager;
import mx.com.gseguros.utils.Utilerias;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionContext;

public class EndososAutoAction extends PrincipalCoreAction
{
	private static Logger logger = Logger.getLogger(EndososAutoAction.class);
	
	private boolean                  success;
	private boolean                  exito;
	private String                   respuesta;
	private Map<String,String>       smap1;
	private Map<String,Item>         imap;
	private List<Map<String,String>> slist1;
	
	private EndososAutoManager endososAutoManager;
	
	public EndososAutoAction()
	{
		this.session=ActionContext.getContext().getSession();
	}
	
	/*
	 * Utilerias
	 */
	private void setCheckpoint(String checkpoint)
	{
		logger.debug(Utilerias.join("checkpoint-->",checkpoint));
		session.put("checkpoint",checkpoint);
	}
	
	private String getCheckpoint()
	{
		return (String)session.get("checkpoint");
	}
	
	private void checkNull(Object objeto,String mensaje)throws ApplicationException
	{
		if(objeto==null)
		{
			throw new ApplicationException(mensaje);
		}
	}
	
	private void checkBlank(String cadena,String mensaje)throws ApplicationException
	{
		if(StringUtils.isBlank(cadena))
		{
			throw new ApplicationException(mensaje);
		}
	}
	
	private void manejaException(Exception ex)
	{
		long timestamp  = System.currentTimeMillis();
		exito           = false;
		
		if(ex.getClass().equals(ApplicationException.class))
		{
			respuesta = Utilerias.join(ex.getMessage()," #",timestamp);
		}
		else
		{
			respuesta = Utilerias.join("Error ",getCheckpoint().toLowerCase()," #",timestamp);
		}
		
		logger.error(respuesta,ex);
		setCheckpoint("0");
	}
	/*
	 * Utilerias
	 */
	
	public String marcoEndosos()
	{
		logger.info(Utilerias.join(
				 "\n##########################"
				,"\n###### marcoEndosos ######"
				));
		
		ManagerRespuestaImapVO resp = endososAutoManager.construirMarcoEndosos();
		exito                       = resp.isExito();
		respuesta                   = resp.getRespuesta();
		if(exito)
		{
			imap=resp.getImap();
		}
		
		logger.info(Utilerias.join(
				"\n###### marcoEndosos ######"
				,"\n##########################"
				));
		return SUCCESS;
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
			setCheckpoint("Validando datos de entrada");
			checkNull(smap1, "No se recibieron datos");
			String cdramo=smap1.get("cdramo");
			checkBlank(cdramo, "No se recibio el producto");
			
			ManagerRespuestaSmapVO resp=endososAutoManager.recuperarColumnasIncisoRamo(cdramo);
			exito     = resp.isExito();
			respuesta = resp.getRespuesta();
			if(exito)
			{
				smap1.putAll(resp.getSmap());
			}
		}
		catch(Exception ex)
		{
			manejaException(ex);
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
			setCheckpoint("Validando datos de entrada");
			checkNull(smap1, "No se recibieron datos de entrada");
			String cdramo   = smap1.get("cdramo");
			String nivel    = smap1.get("nivel");
			String multiple = smap1.get("multiple");
			String tipoflot = smap1.get("tipoflot");
			
			checkBlank(cdramo   , "No se recibio el producto");
			checkBlank(nivel    , "No se recibio el nivel de endoso");
			checkBlank(multiple , "No se recibio el tipo de seleccion");
			checkBlank(tipoflot , "No se recibio el tipo de poliza");
			
			if(slist1==null)
			{
				slist1=new ArrayList<Map<String,String>>();
			}
			
			ManagerRespuestaSlistSmapVO resp=endososAutoManager.recuperarEndososClasificados(
					cdramo
					,nivel
					,multiple
					,tipoflot
					,slist1
					);
			
			exito = resp.isExito();
			respuesta = resp.getRespuesta();
			if(exito)
			{
				smap1.putAll(resp.getSmap());
				slist1=resp.getSlist();
			}
		}
		catch(Exception ex)
		{
			manejaException(ex);
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
		return true;
	}

	public void setSuccess(boolean success) {
		this.success = success;
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
		endososAutoManager.setSession(this.session);
		this.endososAutoManager = endososAutoManager;
	}

	public Map<String, Item> getImap() {
		return imap;
	}

	public void setImap(Map<String, Item> imap) {
		this.imap = imap;
	}
}