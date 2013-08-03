/**
 * 
 */
package mx.com.aon.procesos.emision.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.flujos.cotizacion.model.AyudaCoberturaCotizacionVO;
import mx.com.aon.kernel.model.MpoliagrVO;
import mx.com.aon.portal.model.BaseObjectVO;
import mx.com.aon.portal.model.MensajesVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.AbstractManager;
import mx.com.aon.procesos.emision.model.DatosAdicionalesVO;
import mx.com.aon.procesos.emision.model.DatosRolVO;
import mx.com.aon.procesos.emision.model.EmisionVO;
import mx.com.aon.procesos.emision.model.PolizaDetVO;
import mx.com.aon.procesos.emision.model.PolizaMaestraVO;
import mx.com.aon.procesos.emision.service.EmisionManager;
import mx.com.aon.utils.Constantes;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.biosnet.ice.ext.elements.form.TextFieldControl;
import com.sun.corba.se.impl.orbutil.closure.Constant;
import com.wittyconsulting.backbone.endpoint.Endpoint;
import com.wittyconsulting.backbone.exception.BackboneApplicationException;

/**
 * @author Cesar Hernandez
 *
 */
public class EmisionManagerImpl extends AbstractManager implements EmisionManager{

	/**
	 * Busca las Polizas con Paginacion
	 * @param params Parametros de busqueda
	 * @start donde empieza la busqueda (parametro de paginacion)
	 * @limit donde termina la busqueda (parametro de paginacion)
	 */
	@SuppressWarnings("unchecked")
	public PagedList buscaPolizas(Map params, int start, int limit) throws ApplicationException{
		String endpointName = "OBTIENE_POLIZAS";
		
        return pagedBackBoneInvoke(params, endpointName, start, limit);
	}
	
	/**
	 * Busca las Polizas sin Paginacion
	 * @param params Parametros de busqueda
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<EmisionVO> buscaPolizas(Map params)throws ApplicationException{
		ArrayList<EmisionVO> emision = null;
		
		try {
			Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_POLIZAS");
			emision = (ArrayList<EmisionVO>) endpoint.invoke(params);
			
		} catch (Exception e) {
			logger.error("Backbone exception", e);
			//throw new ApplicationException("Error al recuperar los datos");

		}
		return (ArrayList<EmisionVO>) emision;
	}
	
	/**
	 * Consulta el detalle de las polizas - Datos Generales de la Poliza
	 * @param params Parametros de busqueda
	 */
	@SuppressWarnings("unchecked")
	public List<PolizaDetVO> consultaPolizaDetalle(Map params) throws ApplicationException{
		List<PolizaDetVO> list = null;
		try{
			Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_POLIZA_DETALLE");
			list = (List<PolizaDetVO>) endpoint.invoke(params);
		}
		catch(BackboneApplicationException ex){
			logger.debug("consultaPolizaDetalle EXCEPTION:: " + ex);
		}
		
		return list;
	}
	
	/**
	 * Consulta el Objeto Asegurable de la Poliza
	 * @param params Parametros de busqueda
	 * @start donde empieza la busqueda (parametro de paginacion)
	 * @limit donde termina la busqueda (parametro de paginacion)
	 */
	@SuppressWarnings("unchecked")
	public PagedList consultaObjetoAsegurable(Map params, int start, int limit) throws ApplicationException{
		String endpointName = "OBTIENE_OBJETO_ASEGURABLE";
		
        return pagedBackBoneInvoke(params, endpointName, start, limit);
	}
	
	/**
	 * Consulta Funcion de Poliza
	 * @param params Parametros de busqueda
	 * @start donde empieza la busqueda (parametro de paginacion)
	 * @limit donde termina la busqueda (parametro de paginacion)
	 */
	@SuppressWarnings("unchecked")
	public PagedList consultaFuncionPoliza(Map params, int start, int limit) throws ApplicationException{
		String endpointName = "OBTIENE_FUNCION_POLIZA";
		
        return pagedBackBoneInvoke(params, endpointName, start, limit);
	}
	
	/**
	 * Consulta los recibos de poliza
	 * @param params Parametros de busqueda
	 * @start donde empieza la busqueda (parametro de paginacion)
	 * @limit donde termina la busqueda (parametro de paginacion)
	 */
	@SuppressWarnings("unchecked")
	public PagedList consultaRecibosDetalle(Map params, int start, int limit) throws ApplicationException{
		String endpointName = "EMISION_OBTIENE_RECIBOS";
		return pagedBackBoneInvoke(params, endpointName, start, limit);
	}
	
	/**
	 * Obtiene parte de la pantalla a pintar (atributos variables)
	 * @param params Parametros de busqueda
	 * @start donde empieza la busqueda (parametro de paginacion)
	 * @limit donde termina la busqueda (parametro de paginacion)
	 */
	@SuppressWarnings("unchecked")
	public String obtienePantalla(Map<String, String> params) throws ApplicationException{
		ArrayList<BaseObjectVO> list = null;
		
		try{
			Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_PANTALLA_POLIZA");
			list = (ArrayList<BaseObjectVO>) endpoint.invoke(params);
		}
		catch(BackboneApplicationException ex){
			logger.debug("obtienePantalla EXCEPTION:: " + ex);
		}
		
		BaseObjectVO base = list.get(0);
		
		return (String)base.getLabel();
	}
	
	/**
	 * Consulta los datos adicionales
	 * @param params Parametros de busqueda
	 * @start donde empieza la busqueda (parametro de paginacion)
	 * @limit donde termina la busqueda (parametro de paginacion)
	 */
	@SuppressWarnings("unchecked")
	public List<DatosAdicionalesVO> obtieneDatosAdicionales(Map params) throws ApplicationException{
		List<DatosAdicionalesVO> list = null;
		try{
			Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_DATOS_ADICIONALES");
			list = (List<DatosAdicionalesVO>) endpoint.invoke(params);
		}
		catch(BackboneApplicationException ex){
			logger.debug("obtieneDatosAdicionales EXCEPTION:: " + ex);
		}
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public DatosRolVO getDatosRol(Map<String,String> params, String EndPointName) throws ApplicationException{
		DatosRolVO list = null;
		
		try{
			Endpoint endpoint = (Endpoint) endpoints.get(EndPointName);
			list = (DatosRolVO) endpoint.invoke(params);
		}
		catch(BackboneApplicationException ex){
			logger.debug("getDatosRol EXCEPTION:: " + ex);
		}
		
		return list;
		
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TextFieldControl> getDatosRolExt(Map<String,String> params, String EndPointName) throws ApplicationException{
		ArrayList<TextFieldControl> list = null;
		
		try{
			logger.debug("params: " + params);
			logger.debug("EndPointName: " + EndPointName);
			Endpoint endpoint = (Endpoint) endpoints.get(EndPointName);
			list =  (ArrayList<TextFieldControl>) endpoint.invoke(params);
		}
		catch(BackboneApplicationException ex){
			logger.debug("getDatosRol EXCEPTION:: " + ex);
		}
		
		return list;
		
	}
	
	public PagedList getPagedList(Map<String, String> params, String EndPointName, int start, int limit) throws ApplicationException{
		return pagedBackBoneInvoke(params, EndPointName, start, limit);
	}
	
    /**
     * @param params
     * @param EndPointName
     * @return ArrayList<MpoliagrVO>
     * @exception ApplicationException
     */
	@SuppressWarnings("unchecked")
    public ArrayList<MpoliagrVO> getAgrupador(Map<String,String> params, String EndPointName) throws ApplicationException{
        ArrayList<MpoliagrVO> list = null;
        
        try{
            Endpoint endpoint = (Endpoint) endpoints.get(EndPointName);
            list =  (ArrayList<MpoliagrVO>) endpoint.invoke(params);
        }
        catch(BackboneApplicationException ex){
            logger.debug("getAgrupador EXCEPTION:: " + ex);
        }
        
        return list;
        
    }
	
	public AyudaCoberturaCotizacionVO getAyudaCobertura(Map<String, String> parameters) throws ApplicationException{

		AyudaCoberturaCotizacionVO ayudaVO = new AyudaCoberturaCotizacionVO();

		try {
			
			Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_AYUDA_COBERTURA");
			ayudaVO = (AyudaCoberturaCotizacionVO) endpoint.invoke(parameters);
			
		}catch(BackboneApplicationException bae){
			logger.error("Exception in invoke get Ayuda Cobertura en cotizacion.. ",bae);
			throw new ApplicationException("Error al get Ayuda Cobertura en cotizacion");
		}

		return ayudaVO;
	}
	
	@SuppressWarnings("unchecked")
	public PagedList buscaPolizasMaestras(Map<String,String> params, int start, int limit) throws ApplicationException{
		String endpointName = "OBTIENE_POLIZA_MAESTRA";		
        return pagedBackBoneInvoke(params, endpointName, start, limit);
	}
	
	public String getAction(Map<String, String> params, String endPointName) throws ApplicationException{
		String resultado = "";
		MensajesVO msg = new MensajesVO();
		
		try{
			Endpoint endpoint = endpoints.get(endPointName);
			msg = (MensajesVO) endpoint.invoke(params);
			resultado = msg.getMsgText();
		}catch(Exception e){
			logger.error("Exception getAction :",e);
		}		
		return resultado;
	}
	
	public PolizaMaestraVO getEndpoint(Map<String, String> params, String endPointName)throws ApplicationException{
		PolizaMaestraVO result = new PolizaMaestraVO();
		try{
			Endpoint endpoint = endpoints.get(endPointName);
			result = (PolizaMaestraVO) endpoint.invoke(params);
		}catch(Exception e){
			logger.error("Exception getEndpoint :",e);
		}
		return result;
	}
	

	/**
	 * Método encargado de validar la póliza en endosos
	 * @param param
	 * @param EndPointName
	 * @return boolean
	 * @throws ApplicationException
	 */
	public boolean validaEndosoPoliza(String param, String EndPointName) throws ApplicationException {
		if (logger.isDebugEnabled()) {
        	logger.debug(": param :: " + param);
        }
		BaseObjectVO validaVo = null;
        
        try {
            Endpoint endpoint = (Endpoint) endpoints.get(EndPointName);            
            validaVo = (BaseObjectVO) endpoint.invoke(param);
            
            if (logger.isDebugEnabled()) {
            	logger.debug(": validaVo :: " + validaVo);
            }
        } catch(BackboneApplicationException ex) {
            logger.error("getAgrupador EXCEPTION:: " + ex);
        }
        
        if (validaVo != null && Constantes.SI.equals(validaVo.getValue())) {
        	return true;
        } else {
        	return false;
        }
        
    }
	
	@SuppressWarnings("unchecked")
	public TableModelExport getModel(String dsElemen, String dsUnieco, String dsRamo) throws ApplicationException {

		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();
		map.put("dsUnieco",dsElemen);
		map.put("dsSubram",dsUnieco);
		map.put("dsGarant",dsRamo);

		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_POLIZA_MAESTRA_EXPORT");
		model.setInformation(lista);
		model.setColumnName(new String[]{"Nivel", "Aseguradora", "Producto", "Tipo", "Poliza Aseguradora", "Poliza Catweb", "Inicio", "Fin"});
		
		return model;
	}
}
