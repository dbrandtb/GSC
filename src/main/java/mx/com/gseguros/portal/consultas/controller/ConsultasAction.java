package mx.com.gseguros.portal.consultas.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.portal.consultas.service.ConsultasManager;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.service.PantallasManager;
import mx.com.gseguros.portal.general.util.GeneradorCampos;

public class ConsultasAction extends PrincipalCoreAction
{
	private String                       mensaje;
	private String                       error;
	private Map<String,String>           mapaStringEntrada;
	private Map<String,String>           mapaStringSalida;
	private Map<String,Object>           mapaObjetoEntrada;
	private Map<String,Object>           mapaObjetoSalida;
	private LinkedHashMap<String,Object> mapaLigadoObjetoEntrada;
	private List<Map<String,String>>     listaMapasStringEntrada;
	private List<Map<String,String>>     listaMapasStringSalida;
	private ConsultasManager             consultasManager;
	private PantallasManager             pantallasManager;
	private Map<String,Item>             mapaItem;
	
	private boolean       success = false;
	private static Logger logger  = Logger.getLogger(ConsultasAction.class);
	
	@Override
	public String execute()
	{
		return SUCCESS;
	}
	
	public String consultarProveedores()
	{
		logger.info(""
				+ "\n##################################"
				+ "\n###### consultarProveedores ######"
				);
		logger.info("mapaLigadoObjetoEntrada: "+mapaLigadoObjetoEntrada);
		try
		{
			listaMapasStringSalida = consultasManager.consultaDinamica("PKG_CONSULTA.P_GET_DATOS_PROVEEDORES", mapaLigadoObjetoEntrada);
		}
		catch(Exception ex)
		{
			listaMapasStringSalida = new ArrayList<Map<String,String>>();
			success = false;
			error = ex.getMessage();
			logger.error("error al consultar proveedores",ex);
		}
		logger.info(""
				+ "\n###### consultarProveedores ######"
				+ "\n##################################"
				);
		return SUCCESS;
	}
	
	public String pantallaConsultaProveedores()
	{
		logger.info(""
				+ "\n#########################################"
				+ "\n###### pantallaConsultaProveedores ######"
				);
		try
		{
			UserVO usuario     = (UserVO)session.get("USUARIO");
			String cdsisrol    = usuario.getRolActivo().getObjeto().getValue();
			String pantalla    = "CONSULTA_PROVEEDORES";
			String seccion     = null;
			GeneradorCampos gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			mapaItem           = new HashMap<String,Item>();
			
			//items del formulario
			seccion = "FILTRO";
			List<ComponenteVO> componentesFormulario = pantallasManager.obtenerComponentes(
					null, null, null, null, null, cdsisrol, pantalla, seccion, null);
			gc.generaComponentes(componentesFormulario, true, false, true, false, false, false);
			mapaItem.put("itemsFormulario",gc.getItems());
			
			//fields del modelo y columnas del grid
			seccion = "GRID";
			List<ComponenteVO> componentesGrid = pantallasManager.obtenerComponentes(
					null, null, null, null, null, cdsisrol, pantalla, seccion, null);
			gc.generaComponentes(componentesGrid, true, true, false, true, false, false);
			mapaItem.put("fieldsModelo",gc.getFields());
			mapaItem.put("columnasGrid",gc.getColumns());
		}
		catch(Exception ex)
		{
			logger.error("error al desplegar pantalla de consulta de proveedores",ex);
		}
		logger.info(""
				+ "\n###### pantallaConsultaProveedores ######"
				+ "\n#########################################"
				);
		return SUCCESS;
	}
	
	public String consultarFacturas()
	{
		logger.info(""
				+ "\n###############################"
				+ "\n###### consultarFacturas ######"
				);
		logger.info("mapaLigadoObjetoEntrada: "+mapaLigadoObjetoEntrada);
		try
		{
			listaMapasStringSalida = consultasManager.consultaDinamica("PKG_CONSULTA.P_GET_DATOS_FACTURAS", mapaLigadoObjetoEntrada);
		}
		catch(Exception ex)
		{
			listaMapasStringSalida = new ArrayList<Map<String,String>>();
			success = false;
			error = ex.getMessage();
			logger.error("error al consultar proveedores",ex);
		}
		logger.info(""
				+ "\n###### consultarFacturas ######"
				+ "\n###############################"
				);
		return SUCCESS;
	}
	
	public String pantallaConsultaFacturas()
	{
		logger.info(""
				+ "\n######################################"
				+ "\n###### pantallaConsultaFacturas ######"
				);
		try
		{
			UserVO usuario     = (UserVO)session.get("USUARIO");
			String cdsisrol    = usuario.getRolActivo().getObjeto().getValue();
			String pantalla    = "CONSULTA_FACTURAS";
			String seccion     = null;
			GeneradorCampos gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			mapaItem           = new HashMap<String,Item>();
			
			//items del formulario
			seccion = "FILTRO";
			List<ComponenteVO> componentesFormulario = pantallasManager.obtenerComponentes(
					null, null, null, null, null, cdsisrol, pantalla, seccion, null);
			gc.generaComponentes(componentesFormulario, true, false, true, false, false, false);
			mapaItem.put("itemsFormulario",gc.getItems());
			
			//fields del modelo y columnas del grid
			seccion = "GRID";
			List<ComponenteVO> componentesGrid = pantallasManager.obtenerComponentes(
					null, null, null, null, null, cdsisrol, pantalla, seccion, null);
			gc.generaComponentes(componentesGrid, true, true, false, true, false, false);
			mapaItem.put("fieldsModelo",gc.getFields());
			mapaItem.put("columnasGrid",gc.getColumns());
		}
		catch(Exception ex)
		{
			logger.error("error al desplegar pantalla de consulta de facturas",ex);
		}
		logger.info(""
				+ "\n###### pantallaConsultaFacturas ######"
				+ "\n######################################"
				);
		return SUCCESS;
	}

	///////////////////////////////
	////// getters y setters //////
	///////////////////////////////
	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Map<String, String> getMapaStringEntrada() {
		return mapaStringEntrada;
	}

	public void setMapaStringEntrada(Map<String, String> mapaStringEntrada) {
		this.mapaStringEntrada = mapaStringEntrada;
	}

	public Map<String, String> getMapaStringSalida() {
		return mapaStringSalida;
	}

	public void setMapaStringSalida(Map<String, String> mapaStringSalida) {
		this.mapaStringSalida = mapaStringSalida;
	}

	public List<Map<String, String>> getListaMapasStringEntrada() {
		return listaMapasStringEntrada;
	}

	public void setListaMapasStringEntrada(
			List<Map<String, String>> listaMapasStringEntrada) {
		this.listaMapasStringEntrada = listaMapasStringEntrada;
	}

	public List<Map<String, String>> getListaMapasStringSalida() {
		return listaMapasStringSalida;
	}

	public void setListaMapasStringSalida(
			List<Map<String, String>> listaMapasStringSalida) {
		this.listaMapasStringSalida = listaMapasStringSalida;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setConsultasManager(ConsultasManager consultasManager) {
		this.consultasManager = consultasManager;
	}

	public void setPantallasManager(PantallasManager pantallasManager) {
		this.pantallasManager = pantallasManager;
	}

	public Map<String, Item> getMapaItem() {
		return mapaItem;
	}

	public void setMapaItem(Map<String, Item> mapaItem) {
		this.mapaItem = mapaItem;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Map<String, Object> getMapaObjetoEntrada() {
		return mapaObjetoEntrada;
	}

	public void setMapaObjetoEntrada(Map<String, Object> mapaObjetoEntrada) {
		this.mapaObjetoEntrada = mapaObjetoEntrada;
	}

	public Map<String, Object> getMapaObjetoSalida() {
		return mapaObjetoSalida;
	}

	public void setMapaObjetoSalida(Map<String, Object> mapaObjetoSalida) {
		this.mapaObjetoSalida = mapaObjetoSalida;
	}

	public LinkedHashMap<String, Object> getMapaLigadoObjetoEntrada() {
		return mapaLigadoObjetoEntrada;
	}

	public void setMapaLigadoObjetoEntrada(
			LinkedHashMap<String, Object> mapaLigadoObjetoEntrada) {
		this.mapaLigadoObjetoEntrada = mapaLigadoObjetoEntrada;
	}
	
}