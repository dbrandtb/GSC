package mx.com.gseguros.portal.consultas.controller;

import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.consultas.model.RecuperacionSimple;
import mx.com.gseguros.portal.consultas.service.RecuperacionSimpleManager;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSlist2VO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSmapVO;
import mx.com.gseguros.utils.Utils;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionContext;

public class RecuperacionSimpleAction extends PrincipalCoreAction
{
	
	private static Logger logger = Logger.getLogger(RecuperacionSimpleAction.class);
	
	private boolean                   success;
	private boolean                   exito;
	private String                    respuesta;
	private Map<String,String>        smap1;
	private List<Map<String,String>>  slist1;
	private List<Map<String,String>>  slist2;
	private RecuperacionSimpleManager recuperacionSimpleManager;
	
	public RecuperacionSimpleAction()
	{
		this.session=this.session=ActionContext.getContext().getSession();
	}
	
	/*
	 * Utilidades
	 */
	private void setCheckpoint(String checkpoint)
	{
		logger.debug(Utils.log("checkpoint-->",checkpoint));
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
	
	private void manejaException(Exception ex)
	{
		long timestamp  = System.currentTimeMillis();
		exito           = false;
		
		if(ex instanceof ApplicationException)
		{
			respuesta = Utils.join(ex.getMessage()," #",timestamp);
		}
		else
		{
			respuesta = Utils.join("Error ",getCheckpoint().toLowerCase()," #",timestamp);
		}
		
		logger.error(respuesta,ex);
		setCheckpoint("0");
	}
	/*
	 * Utilidades
	 */
	
	public String recuperacionSimple()
	{
		logger.debug(Utils.log(
				 "\n################################"
				,"\n###### recuperacionSimple ######"
				,"\n###### smap1=",smap1
				));
		
		try
		{
			setCheckpoint("Validando datos de entrada");
			checkNull(smap1                  , "No se recibieron datos");
			String procedimiento = smap1.get("procedimiento");
			checkNull(procedimiento          , "No se recibio el procedimiento");
			checkNull(session                , "No hay sesion");
			checkNull(session.get("USUARIO") , "No hay usuario en la sesion");
			String cdsisrol = ((UserVO)session.get("USUARIO")).getRolActivo().getClave();
			String cdusuari = ((UserVO)session.get("USUARIO")).getUser();
			
			try
			{
				RecuperacionSimple rec      = RecuperacionSimple.valueOf(procedimiento);
				ManagerRespuestaSmapVO resp = recuperacionSimpleManager.recuperacionSimple(rec,smap1,cdsisrol,cdusuari);
				exito           = resp.isExito();
				respuesta       = resp.getRespuesta();
				if(exito)
				{
					smap1.putAll(resp.getSmap());
				}
			}
			catch(Exception ex)
			{
				logger.error("Error al intentar obtener el catalogo del enum",ex);
				throw new ApplicationException("El procedimiento no existe");
			}
		}
		catch(Exception ex)
		{
			manejaException(ex);
		}
		
		logger.debug(Utils.log(
				 "\n###### recuperacionSimple ######"
				,"\n################################"
				));
		return SUCCESS;
	}
	
	public String recuperacionSimpleLista()
	{
		logger.debug(Utils.log(
				 "\n#####################################"
				,"\n###### recuperacionSimpleLista ######"
				,"\n###### smap1=" , smap1
				));
		
		try
		{
			setCheckpoint("Validando datos de entrada");
			checkNull(smap1                  , "No se recibieron datos");
			String procedimiento = smap1.get("procedimiento");
			checkNull(procedimiento          , "No se recibio el procedimiento");
			checkNull(session                , "No hay sesion");
			checkNull(session.get("USUARIO") , "No hay usuario en la sesion");
			String cdsisrol = ((UserVO)session.get("USUARIO")).getRolActivo().getClave();
			String cdusuari = ((UserVO)session.get("USUARIO")).getUser();
			
			try
			{
				RecuperacionSimple rec        = RecuperacionSimple.valueOf(procedimiento);
				ManagerRespuestaSlist2VO resp = recuperacionSimpleManager.recuperacionSimpleLista(rec,smap1,cdsisrol,cdusuari);
				exito     = resp.isExito();
				respuesta = resp.getRespuesta();
				if(exito)
				{
					slist1 = resp.getSlist();
					slist2 = resp.getSlist2();
				}
			}
			catch(Exception ex)
			{
				logger.error("Error al intentar obtener el catalogo del enum",ex);
				throw new ApplicationException("El procedimiento no existe");
			}
		}
		catch(Exception ex)
		{
			manejaException(ex);
		}
		
		logger.debug(Utils.log(
				 "\n###### recuperacionSimpleLista ######"
				,"\n#####################################"
				));
		return SUCCESS;
	}
	
	/*
	 * Getters y setters
	 */
	public boolean isSuccess()
	{
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

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setRecuperacionSimpleManager(RecuperacionSimpleManager recuperacionSimpleManager) {
		recuperacionSimpleManager.setSession(this.session);
		this.recuperacionSimpleManager = recuperacionSimpleManager;
	}

	public List<Map<String, String>> getSlist2() {
		return slist2;
	}

	public void setSlist2(List<Map<String, String>> slist2) {
		this.slist2 = slist2;
	}
}