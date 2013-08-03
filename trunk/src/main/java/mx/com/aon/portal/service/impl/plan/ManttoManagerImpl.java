package mx.com.aon.portal.service.impl.plan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.plan.PlanVO;
import mx.com.aon.portal.model.plan.ProductoVO;
import mx.com.aon.portal.service.plan.ManttoManager;

import org.apache.log4j.Logger;

import com.wittyconsulting.backbone.endpoint.Endpoint;
import com.wittyconsulting.backbone.exception.BackboneApplicationException;
/**
 * 
 * @author sergio.ramirez
 *
 */
public class ManttoManagerImpl implements ManttoManager {

	private static Logger logger = Logger.getLogger(ManttoManagerImpl.class);

	@SuppressWarnings("unchecked")// Map Controlled
	private Map endpoints;

	@SuppressWarnings("unchecked")// Map Controlled
	public void setEndpoints(Map endpoints) {
		this.endpoints = endpoints;
	}
	/**
	 * Metodo que realiza la consulta a la base para obtener todos los planes existentes
	 */
	@SuppressWarnings("unchecked")// Map Controlled
	public List<PlanVO> getPlanes(String cdRamoB, String dsPlanB)
			throws ApplicationException {
		Map params = new HashMap<String, String>();
		params.put("cdRamoB", cdRamoB);
		params.put("dsPlanB", dsPlanB);

		List<PlanVO> planes = null;
		try {
			Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_PLANES");
			planes = (ArrayList<PlanVO>) endpoint.invoke(params);
		} catch (BackboneApplicationException e) {
			logger.error("Backbone exception", e);
			throw new ApplicationException("Error al recuperar los datos");

		}
		return (ArrayList<PlanVO>) planes;
	}
	/**
	 * Metodo que realiza la consulta a la base para obtener todos los productos existentes.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<ProductoVO> getListas() throws ApplicationException {
		Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_PRODUCTOS");
		List<ProductoVO> ListaProductos = null;
		try {
			ListaProductos = (List<ProductoVO>) endpoint.invoke(null);
			if (ListaProductos != null && !ListaProductos.isEmpty()) {
				logger.debug("la lista esta llena");
			} else {
				logger.debug("lista vacia");
			}
		} catch (BackboneApplicationException e) {
			throw new ApplicationException(
					"Error regresando lista de productos");
		}
		return (ArrayList<ProductoVO>) ListaProductos;

	}
	/**
	 * Metodo que inserta un nuevo plan creado por el usuario.
	 */
	public void insertaPlan(PlanVO plan) throws ApplicationException {

		Endpoint endpoint = (Endpoint) endpoints.get("INSERTA_PLAN");
		try {
			endpoint.invoke(plan);
		} catch (BackboneApplicationException e) {
			throw new ApplicationException(
					"Error intentando insertar un nuevo plan");
		}

	}
	/**
	 * Metodo que inserta los datos que han sido editados a la base de datos.
	 */
	public void editaPlan(PlanVO planEdit) throws ApplicationException {

		Endpoint endpoint = (Endpoint) endpoints.get("EDITA_PLAN");
		try {
			endpoint.invoke(planEdit);
		} catch (BackboneApplicationException e) {
			throw new ApplicationException("Error intentando editar  un plan");
		}
	}
	/**
	 * Metodo que regresa un solo objeto de la base tipo PlanVO.
	 */
	@SuppressWarnings( { "unchecked", "unchecked" })
	// Map Controlled
	public PlanVO getPlan(String cdPlan, String cdRamo)throws ApplicationException {
		
		Map params = new HashMap<String, String>();
		params.put("cdPlan", cdPlan);
		params.put("cdRamo", cdRamo);

		PlanVO cargaPlan = null;
		try {
			Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_PLAN");
			cargaPlan = (PlanVO) endpoint.invoke(params);
		} catch (BackboneApplicationException e) {
			throw new ApplicationException("Error intentando obtener un plan");

		}
		return cargaPlan;
	}
	/**
	 * Metodo que copia un plan existente.
	 */
	@SuppressWarnings("unchecked")
	public void copiarPlan(String cdRamo, String dsPlan)throws ApplicationException {
		Map params= new HashMap<String, String>();
		params.put("cdRamo", cdRamo);
		params.put("dsPlan", dsPlan);
		Endpoint endpoint = (Endpoint) endpoints.get("COPIA_PLAN");
		try{
			endpoint.invoke(params);
		}catch (BackboneApplicationException e){
			throw new ApplicationException("Error intentando copiar un plan");
		}
	}
	/**
	 * Metodo que borra un plan existente en la base de datos.
	 */
	@SuppressWarnings("unchecked")
	public void borrarPlan(String cdRamo, String cdPlan)throws ApplicationException {
		Map params= new HashMap<String, String>();
		params.put("cdRamo", cdRamo);
		params.put("cdPlan", cdPlan);
		Endpoint endpoint = (Endpoint) endpoints.get("BORRAR_PLAN");
		try{
			endpoint.invoke(params);
		}catch (BackboneApplicationException e) {
			throw new ApplicationException("Error intentando borrar un plan");
		}
		
	}
	
}
