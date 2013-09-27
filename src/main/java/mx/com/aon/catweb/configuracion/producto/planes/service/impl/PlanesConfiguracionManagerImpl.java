package mx.com.aon.catweb.configuracion.producto.planes.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.catweb.configuracion.producto.expresiones.model.RamaVO;
import mx.com.aon.catweb.configuracion.producto.model.LlaveValorVO;
import mx.com.aon.catweb.configuracion.producto.planes.service.PlanesConfiguracionManager;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.MensajesVO;
import mx.com.aon.portal.model.PlanesMPlanProVO;
import mx.com.aon.portal.service.impl.AbstractManager;
import mx.com.aon.portal.util.ConvertUtil;
import mx.com.aon.portal.util.WrapperResultados;

import mx.com.aon.tmp.Endpoint;
import mx.com.aon.tmp.BackboneApplicationException;
/**
 * 
 * @author sergio.ramirez
 *
 */
public class PlanesConfiguracionManagerImpl extends AbstractManager implements PlanesConfiguracionManager{
	
	/**
	 *@param cdRamo
	 *@return List<LlaveValorVO>lista
	 *@exception BackboneApplicationException
	 *
	 */
	@SuppressWarnings("unchecked")
	public List<LlaveValorVO> getListaPlanes(String cdRamo, String cdTipSit)throws ApplicationException {
		Map params= new HashMap<String, String>();
		params.put("cdRamo", cdRamo);
		params.put("cdTipSit", cdTipSit);
		List<LlaveValorVO> lista= null;
		try {
			Endpoint endpoint = (Endpoint)endpoints.get("OBTIENE_LISTA_PLANES_CONFIGURACION");
			lista=(List<LlaveValorVO>) endpoint.invoke(params);
		} catch (BackboneApplicationException e) {
			e.getMessage();
			throw new ApplicationException("Error retrieving data");
		}	
		return (List<LlaveValorVO>)lista;
	}
	/**
	 * @param cdRamo
	 * @param cdPlan
	 * @param cdTipSit
	 * @return List<LlaveValorVO>planes
	 * @throws BackboneApplicationException
	 */
	@SuppressWarnings("unchecked")
	public List<LlaveValorVO> getCoberturasPlanes(String cdRamo, String cdPlan,String cdTipSit) throws ApplicationException {
		Map parameters = new HashMap<String, String>();
		parameters.put("cdRamo", cdRamo);
		parameters.put("cdPlan", cdPlan);
		parameters.put("cdTipSit", cdTipSit);
		List<LlaveValorVO> planes= null;
		try {
			Endpoint endpoint = endpoints.get("OBTIENE_LISTA_COBERTURAS_PLANES_CONFIGURACION");
			planes= (List<LlaveValorVO>) endpoint.invoke(parameters);
		} catch (BackboneApplicationException e) {
			e.getMessage();
			throw new ApplicationException("Error retrieving data");
		}
		return (List<LlaveValorVO>)planes;
	}
	
	/**
	 *@param cdTipSit
	 *@param cdRamo
	 *@param cdPlan
	 *@throws BackboneApplicationException
	 *
	 */
	@SuppressWarnings("unchecked")
	public void guardaPlanesConfiguracion(String cdRamo, String cdTipsit, String cdPlan,String dsPlan) throws ApplicationException {
		Map params = new HashMap<String, String>();
		params.put("cdRamo", cdRamo);
		params.put("cdPlan", cdPlan);
		params.put("dsPlan", dsPlan);
		params.put("cdTipsit", cdTipsit);
		
		logger.debug("PlanConfiguracionManagerImpl.guardaPlanesConfiguracion()" );
		logger.debug("params=" + params );
		
		Endpoint endpoint = endpoints.get("GUARDA_PLAN_CONFIGURACION");
		try{
			endpoint.invoke(params);			
		}catch (BackboneApplicationException e) {
			e.getMessage();
			throw new ApplicationException("Error during transaction");
		}
	
	}
	
	public void editaPlan(String cdRamo, String cdPlan,String dsPlan) throws ApplicationException {
		Map params = new HashMap<String, String>();
		params.put("cdRamo", cdRamo);
		params.put("cdPlan", cdPlan);
		params.put("dsPlan", dsPlan);
		
		logger.debug("PlanConfiguracionManagerImpl.editaPlan()" );
		logger.debug("params=" + params );
		
		Endpoint endpoint = endpoints.get("EDITA_PLAN");
		try{
			endpoint.invoke(params);			
		}catch (BackboneApplicationException e) {
			e.getMessage();
			throw new ApplicationException("Error during transaction");
		}
	
	}
	
	/**
	 *
	 */
	@SuppressWarnings("unchecked")
	public MensajesVO guardaCoberturaPlanes(String cdRamo, String cdPlanTmp,String cdTipSit, String codigoCondicion, String obligatorio)throws ApplicationException {
		MensajesVO mensajeVO = null;
		Map parameters = new HashMap<String, String>();
		parameters.put("cdRamo", cdRamo);
		parameters.put("cdPlan", cdPlanTmp);
		parameters.put("cdTipSit", cdTipSit);
		parameters.put("cdgarant", codigoCondicion);
		parameters.put("obligatorio", obligatorio);
		Endpoint endpoint= endpoints.get("GUARDA_COBERTURA_PLAN_CONFIGURACION");
		try {
			mensajeVO = (MensajesVO)endpoint.invoke(parameters);
		} catch (BackboneApplicationException e) {
			e.getMessage();
			throw new ApplicationException("Error during transaction");
		}
		return mensajeVO;
	}
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<RamaVO> getRamaPlanes(String cdRamo, String cdTipSit)throws ApplicationException {
		Map params= new HashMap<String, String>();
		params.put("codigoProducto", cdRamo);
		params.put("codigoSituacion", cdTipSit);
		List<RamaVO> ramas = null;
		try {
			Endpoint endpoint = (Endpoint)endpoints.get("OBTIENE_LISTA_PLANES_RAMAS");
			ramas=(List<RamaVO>) endpoint.invoke(params);
		} catch (BackboneApplicationException e) {
			e.getMessage();
			throw new ApplicationException("Error retrieving data");
		}	
		return (List<RamaVO>)ramas;
	}
	
	
	@SuppressWarnings("unchecked")
	public void eliminaPlan(String cdRamo, String cdTipSit, String cdPlan) throws ApplicationException {
		Map params = new HashMap<String, String>();
		params.put("cdRamo", cdRamo);
		params.put("cdPlan", cdPlan);
		params.put("cdTipSit", cdTipSit);
		Endpoint endpoint = endpoints.get("ELIMINA_PLAN");
		try{
			endpoint.invoke(params);			
		}catch (BackboneApplicationException e) {
			e.getMessage();
			throw new ApplicationException("Error during transaction");
		}
	}
	
	/**
	 * Metodo que realiza la eliminacion d un plan seleccionado.
	 * Usa el Store Procedure PKG_CONFG_CUENTA.P_BORRAR_PLANPRO
	 * 
	 * @param PlanesMPlanProVO planesMPlanProVO
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public String borrarCoberturasPlan(PlanesMPlanProVO planesMPlanProVO) throws ApplicationException {

			HashMap map = new HashMap ();
			map.put("cdramo", planesMPlanProVO.getCdRamo());
			map.put("cdplan", planesMPlanProVO.getCdPlan());
			map.put("cdtipsit", planesMPlanProVO.getCdTipSit());
			map.put("cdgarant", planesMPlanProVO.getCdGarant());
			map.put("swoblig", planesMPlanProVO.getSwOblig());

            WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_PLANPRO");
            return res.getMsgText();
	}
	


    public  void modificaCoberturaPlan(  String p_cdgarant_i ,String  p_dsgarant_i   )throws ApplicationException {
	    
    	Map params = new HashMap<String, String>();
    	params.put("p_cdgarant_i", ConvertUtil.nvl(  p_cdgarant_i  ));
    	params.put("p_dsgarant_i", ConvertUtil.nvl( p_dsgarant_i));
      
	    Endpoint endpoint = endpoints.get("MODIFICA_COBERTURA_PLAN");
		try{
			endpoint.invoke(params);			
		}catch (BackboneApplicationException e) {
			e.getMessage();
			throw new ApplicationException("Error "  + e.getMessage() );
		}
    }
	
	


}