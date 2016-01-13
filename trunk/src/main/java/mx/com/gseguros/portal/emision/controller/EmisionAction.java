package mx.com.gseguros.portal.emision.controller;

import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaImapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaVoidVO;
import mx.com.gseguros.portal.emision.service.EmisionManager;
import mx.com.gseguros.utils.Utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.opensymphony.xwork2.ActionContext;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

public class EmisionAction extends PrincipalCoreAction
{
	private static Logger logger = Logger.getLogger(EmisionAction.class);
	
	private EmisionManager emisionManager;
	
	private boolean                  success   = true;
	private boolean                  exito     = false;
	private String                   respuesta = null;
	private Map<String,String>       smap1     = null;
	private Map<String,Item>         imap      = null;
	private List<Map<String,String>> slist1    = null;
	
	/*
	 * Utilitarios
	 */
	private void manejaException(Exception ex)
	{
		long timestamp  = System.currentTimeMillis();
		exito           = false;
		
		if(ex instanceof ApplicationException)
		{
			respuesta = new StringBuilder(ex.getMessage()).append(" #").append(timestamp).toString();
		}
		else
		{
			respuesta = new StringBuilder("Error ").append(getCheckpoint().toLowerCase()).append(" #").append(timestamp).toString();
		}
		
		logger.error(respuesta,ex);
		setCheckpoint("0");
	}
	
	private void setCheckpoint(String checkpoint)
	{
		logger.debug(new StringBuilder("checkpoint-->").append(checkpoint).toString());
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
	
	private void checkList(List<?> lista,String mensaje)throws ApplicationException
	{
		checkNull(lista,mensaje);
		if(lista.size()==0)
		{
			throw new ApplicationException(mensaje);
		}
	}
	/*
	 * Utilitarios
	 */
	
	public EmisionAction()
	{
		logger.debug("new EmisionAction");
		this.session=ActionContext.getContext().getSession();
	}
	
	public String pantallaClausulasPoliza()
	{
		logger.debug(Utils.log(
				 "\n#####################################"
				,"\n###### pantallaClausulasPoliza ######"
				,"\n###### smap1=",smap1
				));
		
		try
		{
			setCheckpoint("Validando datos de entrada");
			checkNull(smap1, "No se recibieron datos de entrada");
			
			checkBlank(smap1.get("cdunieco") , "No se recibio la sucursal");
			checkBlank(smap1.get("cdramo")   , "No se recibio el producto");
			checkBlank(smap1.get("estado")   , "No se recibio el estado");
			checkBlank(smap1.get("nmpoliza") , "No se recibio el numero de poliza");
			checkBlank(smap1.get("nmsuplem") , "No se recibio el numero de suplemento");
			
			ManagerRespuestaImapVO resp=emisionManager.construirPantallaClausulasPoliza();
			exito     = resp.isExito();
			respuesta = resp.getRespuesta();
			imap      = resp.getImap();
		}
		catch(Exception ex)
		{
			manejaException(ex);
		}
		
		String result = SUCCESS;
		if(!exito)
		{
			result=ERROR;
		}
		
		logger.debug(Utils.log(
				 "\n###### result=",result
				,"\n###### pantallaClausulasPoliza ######"
				,"\n#####################################"
				));
		return result;
	}
	
	public String guardarClausulasPoliza()
	{
		logger.debug(Utils.log(
				 "\n####################################"
				,"\n###### guardarClausulasPoliza ######"
				,"\n###### slist1=",slist1
				));
		
		try
		{
			setCheckpoint("Validando datos de entrada");
			checkList(slist1, "No se recibieron las clausulas");
			
			ManagerRespuestaVoidVO resp=emisionManager.guardarClausulasPoliza(slist1);
			exito     = resp.isExito();
			respuesta = resp.getRespuesta();
		}
		catch(Exception ex)
		{
			manejaException(ex);
		}
		
		logger.debug(Utils.log(
				 "\n###### guardarClausulasPoliza ######"
				,"\n####################################"
				));
		return SUCCESS;
	}
	
	public String obtieneIdUsu()
	{		
		logger.debug(Utils.log(
				 "\n >>> guardarClausulasPoliza"
				,"\n >>> smap1=",smap1
				));
		
		String login = smap1.get("cdusuari");
		String respInd = null;
		try
		{
    		Client restclient = Client.create();
	    	WebResource webResource = restclient.resource("http://10.1.21.117:9080/wsCAS/rest/usuarios/getIdUsuByLogin");

	    	logger.debug(Utils.log(" >>>>>>>>>>> Request Obtener IdUsu: " + login));
      		
      		ClientResponse response = webResource.type("application/x-www-form-urlencoded").post(ClientResponse.class, "login="+login);

			String output;
			if (response.getStatus() > 299 || response.getStatus() < 200) {
				logger.debug(Utils.log(response.getEntity(String.class)));
			   output = "Failed : HTTP error code : "  + response.getStatus();
			}        
			else{
			    output = response.getEntity(String.class);            
			}

			logger.debug(Utils.log("Response Usuario Obtenido: " + output));
		respInd = "['idusu':" + output + "]";

		ObjectMapper mapper = new ObjectMapper();

		logger.debug(Utils.log("respInd: " + respInd.toString()));
		
		exito = true;
		respuesta = respInd;
		smap1.put("idusu", output);
						
		} catch (UniformInterfaceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientHandlerException e) {
			// TODO Auto-gerated catch block
			e.printStackTrace();
				}
		
		return SUCCESS;
	}
	
	/**
	 * Getters y setters
	 */

	public Map<String, String> getSmap1() {
		return smap1;
	}

	public void setSmap1(Map<String, String> smap1) {
		this.smap1 = smap1;
	}

	public boolean isSuccess() {
		return success;
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

	public List<Map<String, String>> getSlist1() {
		return slist1;
	}

	public void setSlist1(List<Map<String, String>> slist1) {
		this.slist1 = slist1;
	}

	public void setEmisionManager(EmisionManager emisionManager) {
		emisionManager.setSession(session);
		this.emisionManager = emisionManager;
	}

	public Map<String, Item> getImap() {
		return imap;
	}

	public void setImap(Map<String, Item> imap) {
		this.imap = imap;
	}
	
}