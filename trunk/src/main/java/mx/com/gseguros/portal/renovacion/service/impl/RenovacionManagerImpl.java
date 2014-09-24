package mx.com.gseguros.portal.renovacion.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaImapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSlistVO;
import mx.com.gseguros.portal.general.dao.PantallasDAO;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.portal.renovacion.dao.RenovacionDAO;
import mx.com.gseguros.portal.renovacion.service.RenovacionManager;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

public class RenovacionManagerImpl implements RenovacionManager
{
	private static final Logger logger = Logger.getLogger(RenovacionManagerImpl.class);
	
	//Dependencias inyectadas
	private RenovacionDAO renovacionDAO;
	private PantallasDAO  pantallasDAO;
	
	@Override
	public ManagerRespuestaImapVO pantallaRenovacion(String cdsisrol)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ pantallaRenovacion @@@@@@")
				.append("\n@@@@@@ cdsisrol=").append(cdsisrol)
				.toString());
		ManagerRespuestaImapVO resp = new ManagerRespuestaImapVO(true);
		
		//obtener componentes
		try
		{
			List<ComponenteVO>componentesBusqueda=pantallasDAO.obtenerComponentes(
					null,null,null,null,null,cdsisrol,"PANTALLA_RENOVACION","BUSQUEDA",null);
			
			GeneradorCampos gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			
			gc.generaComponentes(componentesBusqueda, true, false, true, false, false, false);
			
			Map<String,Item> imap = new HashMap<String,Item>();
			
			imap.put("busquedaItems" , gc.getItems());
			resp.setImap(imap);
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(
					new StringBuilder()
					.append("Error al obtener componentes de busqueda #")
					.append(timestamp)
					.toString()
					);
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ pantallaRenovacion @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString());
		return resp;
	}
	
	@Override
	public ManagerRespuestaSlistVO buscarPolizasRenovables(String anio,String mes)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ buscarPolizasRenovables @@@@@@")
				.append("\n@@@@@@ anio=").append(anio)
				.append("\n@@@@@@ mes=").append(mes)
				.toString()
				);
		ManagerRespuestaSlistVO resp = new ManagerRespuestaSlistVO(true);
		
		//obtener polizas
		try
		{
			List<Map<String,String>>listaPolizasRenovables = renovacionDAO.buscarPolizasRenovables(anio,mes);
			resp.setSlist(listaPolizasRenovables);
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(new StringBuilder("Error al buscar las polizas renovables #").append(timestamp).toString());
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ buscarPolizasRenovables @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}

	//Getters y setters
	public void setRenovacionDAO(RenovacionDAO renovacionDAO) {
		this.renovacionDAO = renovacionDAO;
	}

	public void setPantallasDAO(PantallasDAO pantallasDAO) {
		this.pantallasDAO = pantallasDAO;
	}
}