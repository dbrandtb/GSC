package mx.com.gseguros.portal.cotizacion.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.cotizacion.dao.CotizacionDAO;
import mx.com.gseguros.portal.cotizacion.model.DatosUsuario;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaBaseVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaImapSmapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSmapVO;
import mx.com.gseguros.portal.cotizacion.service.CotizacionAutoManager;
import mx.com.gseguros.portal.general.dao.PantallasDAO;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.portal.general.util.RolSistema;
import mx.com.gseguros.portal.general.util.TipoTramite;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

public class CotizacionAutoManagerImpl implements CotizacionAutoManager
{
	private static final Logger logger = Logger.getLogger(CotizacionAutoManagerImpl.class);
	
	private CotizacionDAO cotizacionDAO;
	private PantallasDAO  pantallasDAO;
	
	private Map<String,Object> session;
	
	/**
	 * Guarda el estado actual en sesion
	 */
	private void setCheckpoint(String checkpoint)
	{
		logger.debug(new StringBuilder("checkpoint-->").append(checkpoint).toString());
		session.put("checkpoint",checkpoint);
	}
	
	/**
	 * Obtiene el estado actual de sesion
	 */
	private String getCheckpoint()
	{
		return (String)session.get("checkpoint");
	}
	
	/**
	 * Da valor a los atributos exito, respuesta y respuestaOculta de resp.
	 * Tambien guarda el checkpoint en 0
	 */
	private void manejaException(Exception ex,ManagerRespuestaBaseVO resp)
	{
		long timestamp = System.currentTimeMillis();
		resp.setExito(false);
		resp.setRespuestaOculta(ex.getMessage());
		
		if(ex.getClass().equals(ApplicationException.class))
		{
			resp.setRespuesta(
					new StringBuilder()
					.append(ex.getMessage())
					.append(" #")
					.append(timestamp)
					.toString()
					);
		}
		else
		{
			resp.setRespuesta(
					new StringBuilder()
					.append("Error ")
					.append(getCheckpoint().toLowerCase())
					.append(" #")
					.append(timestamp)
					.toString()
					);
		}
		
		logger.error(resp.getRespuesta(),ex);
		setCheckpoint("0");
	}
	
	/**
	 * Atajo a StringUtils.isBlank
	 */
	private boolean isBlank(String mensaje)
	{
		return StringUtils.isBlank(mensaje);
	}
	
	/**
	 * Arroja una ApplicationException
	 */
	private void throwExc(String mensaje) throws ApplicationException
	{
		throw new ApplicationException(mensaje);
	}
	
	@Override
	public ManagerRespuestaImapSmapVO cotizacionAutoIndividual(
			String ntramite
			,String cdunieco
			,String cdramo
			,String cdtipsit
			,String cdusuari
			,String cdsisrol
			)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ cotizacionAutoIndividual @@@@@@")
				.append("\n@@@@@@ ntramite=").append(ntramite)
				.append("\n@@@@@@ cdunieco=").append(cdunieco)
				.append("\n@@@@@@ cdramo=")  .append(cdramo)
				.append("\n@@@@@@ cdtipsit=").append(cdtipsit)
				.append("\n@@@@@@ cdusuari=").append(cdusuari)
				.append("\n@@@@@@ cdsisrol=").append(cdsisrol)
				.toString()
				);
		
		ManagerRespuestaImapSmapVO resp = new ManagerRespuestaImapSmapVO(true);
		resp.setSmap(new HashMap<String,String>());
		resp.setImap(new HashMap<String,Item>());
		
		try
		{
			String cdagente = null;
			
			setCheckpoint("Obteniendo trámite y sucursal");//////
			//cuando no hay tramite es porque cotiza un agente desde afuera
			if(isBlank(ntramite))
			{
				try
				{
					DatosUsuario datUsu = cotizacionDAO.cargarInformacionUsuario(cdusuari,cdtipsit);
					cdunieco            = datUsu.getCdunieco();
					resp.getSmap().put("cdunieco" , cdunieco);
					resp.getSmap().put("ntramite" , null);
					
					if(cdsisrol.equals(RolSistema.AGENTE.getCdsisrol()))
					{
						cdagente = datUsu.getCdagente();
						resp.getSmap().put("cdagente" , cdagente);
					}
				}
				catch(Exception ex)
				{
					throwExc("Usted no puede cotizar este producto");
				}
			}
			
			setCheckpoint("Recuperando tipo de situación");//////
			Map<String,String>tipoSituacion=cotizacionDAO.cargarTipoSituacion(cdramo,cdtipsit);
			if(tipoSituacion!=null)
			{
				resp.getSmap().putAll(tipoSituacion);
			}
			else
			{
				throwExc("No se ha parametrizado la situación en TTIPRAM");
			}
			
			setCheckpoint("Recuperando atributos variables");//////
			List<ComponenteVO>panel1=new ArrayList<ComponenteVO>();
			List<ComponenteVO>panel2=new ArrayList<ComponenteVO>();
			List<ComponenteVO>panel3=new ArrayList<ComponenteVO>();
			List<ComponenteVO>panel4=new ArrayList<ComponenteVO>();
			List<ComponenteVO>panel5=new ArrayList<ComponenteVO>();
			List<ComponenteVO>panel6=new ArrayList<ComponenteVO>();
			
			List<ComponenteVO>tatrisit = cotizacionDAO.cargarTatrisit(cdtipsit, cdusuari);
			List<ComponenteVO>aux      = new ArrayList<ComponenteVO>();
			
			//obtener los que se muestran
			for(ComponenteVO tatri:tatrisit)
			{
				if(tatri.getSwpresen().equals("S"))
				{
					tatri.setComboVacio(true);
					aux.add(tatri);
				}
			}
			
			tatrisit = aux;
			
			setCheckpoint("Obteniendo componentes sustitutos");//////
			List<ComponenteVO>sustitutos = pantallasDAO.obtenerComponentes(
					TipoTramite.POLIZA_NUEVA.getCdtiptra()
					,cdunieco
					,cdramo
					,cdtipsit
					,"W"
					,cdsisrol
					,"COTIZACION_CUSTOM"
					,"SUSTITUTOS"
					,null
					);
			if(sustitutos.size()>0)
			{
				aux=new ArrayList<ComponenteVO>();
				for(ComponenteVO tatri : tatrisit)
				{
					String cdatribuTatri = tatri.getNameCdatribu();
					boolean sustituido   = false;
					for(ComponenteVO sustituto : sustitutos)
					{
						String cdatribuSustituto = sustituto.getNameCdatribu();
						logger.debug(new StringBuilder("tatri=").append(cdatribuTatri).append(" vs susti=").append(cdatribuSustituto).toString());
						if(cdatribuSustituto.equals(cdatribuTatri))
						{
							sustituto.setNmpanelcoti(tatri.getNmpanelcoti());
							sustituido = true;
							aux.add(sustituto);
						}
					}
					if(!sustituido)
					{
						aux.add(tatri);
					}
				}
				tatrisit = aux;
			}
			
			//separar por panel
			for(ComponenteVO tatri : tatrisit)
			{
				String nmpanelcoti = tatri.getNmpanelcoti();
				if(nmpanelcoti.equals("1"))
				{
					panel1.add(tatri);
				}
				else if(nmpanelcoti.equals("2"))
				{
					panel2.add(tatri);
				}
				else if(nmpanelcoti.equals("3"))
				{
					panel3.add(tatri);
				}
				else if(nmpanelcoti.equals("4"))
				{
					panel4.add(tatri);
				}
				else if(nmpanelcoti.equals("5"))
				{
					panel5.add(tatri);
				}
				else if(nmpanelcoti.equals("6"))
				{
					panel6.add(tatri);
				}
			}
			
			setCheckpoint("Construyendo componentes");//////
			GeneradorCampos gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gc.setCdramo(cdramo);
			gc.setCdtipsit(cdtipsit);
			
			gc.generaComponentes(panel1, true, true, true, false, false, false);
			resp.getImap().put("panel1Fields" , gc.getFields());
			resp.getImap().put("panel1Items"  , gc.getItems());
			
			gc.generaComponentes(panel2, true, true, true, false, false, false);
			resp.getImap().put("panel2Fields" , gc.getFields());
			resp.getImap().put("panel2Items"  , gc.getItems());
			
			gc.generaComponentes(panel3, true, true, true, false, false, false);
			resp.getImap().put("panel3Fields" , gc.getFields());
			resp.getImap().put("panel3Items"  , gc.getItems());
			
			gc.generaComponentes(panel4, true, true, true, false, false, false);
			resp.getImap().put("panel4Fields" , gc.getFields());
			resp.getImap().put("panel4Items"  , gc.getItems());
			
			gc.generaComponentes(panel5, true, true, true, false, false, false);
			resp.getImap().put("panel5Fields" , gc.getFields());
			resp.getImap().put("panel5Items"  , gc.getItems());
			
			gc.generaComponentes(panel6, true, true, true, false, false, false);
			resp.getImap().put("panel6Fields" , gc.getFields());
			resp.getImap().put("panel6Items"  , gc.getItems());
			
			setCheckpoint("0");
		}
		catch(Exception ex)
		{
			manejaException(ex,resp);
		}

		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ cotizacionAutoIndividual @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaSmapVO cargarRetroactividadSuplemento(
			String cdunieco
			,String cdramo
			,String cdtipsup
			,String cdusuari
			,String cdtipsit)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ cargarRetroactividadSuplemento @@@@@@")
				.append("\n@@@@@@ cdunieco=").append(cdunieco)
				.append("\n@@@@@@ cdramo=").append(cdramo)
				.append("\n@@@@@@ cdtipsup=").append(cdtipsup)
				.append("\n@@@@@@ cdusuari=").append(cdusuari)
				.append("\n@@@@@@ cdtipsit=").append(cdtipsit)
				.toString()
				);
		
		ManagerRespuestaSmapVO resp = new ManagerRespuestaSmapVO(true);
		
		try
		{
			setCheckpoint("Recuperando retroactividad");//////
			resp.setSmap(cotizacionDAO.cargarRetroactividadSuplemento(cdunieco,cdramo,cdtipsup,cdusuari,cdtipsit));
			
			setCheckpoint("0");
		}
		catch(Exception ex)
		{
			manejaException(ex, resp);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ cargarRetroactividadSuplemento @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	/*
	 * Getters y setters
	 */
	public void setCotizacionDAO(CotizacionDAO cotizacionDAO) {
		this.cotizacionDAO = cotizacionDAO;
	}

	public void setPantallasDAO(PantallasDAO pantallasDAO) {
		this.pantallasDAO = pantallasDAO;
	}
	
	@Override
	public void setSession(Map<String,Object>session){
		logger.debug("setSession");
		this.session=session;
	}
}