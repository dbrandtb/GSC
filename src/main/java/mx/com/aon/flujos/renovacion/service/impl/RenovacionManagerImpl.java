/**
 * 
 */
package mx.com.aon.flujos.renovacion.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import mx.com.aon.tmp.Endpoint;
import mx.com.aon.tmp.BackboneApplicationException;

import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.flujos.renovacion.service.RenovacionManager;
import mx.com.aon.portal.model.BaseObjectVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.AbstractManager;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.utils.Constantes;
import mx.com.gseguros.exception.ApplicationException;

/**
 * @author Alejandro García
 * @date 17/09/2008
 *
 */
public class RenovacionManagerImpl extends AbstractManager implements RenovacionManager {
    
	//Metodo generico para grid
    public PagedList getPagedList(Map<String, String> params, String EndPointName, int start, int limit) throws ApplicationException{
        return pagedBackBoneInvoke(params, EndPointName, start, limit);
    }
    
	@SuppressWarnings("unchecked")
	public List getItemList(String endpointName) throws ApplicationException{
		
		List itemList = null;
		
		try {
			Endpoint endpoint = endpoints.get(endpointName);
			logger.debug("endpoint :"+endpoint);
			itemList = (ArrayList) endpoint.invoke(null);
			logger.debug("itemList :"+itemList);
			
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke Consulta de catalogo ",bae);
			throw new ApplicationException("Error al consultar un catalogo");
		}
		
		return itemList;
	}
	
	public String getAction(Map<String, Object> params, String endPointName) throws ApplicationException{
		
		String resultado = "";
		
		try{
			Endpoint endpoint = endpoints.get(endPointName);
			resultado = (String) endpoint.invoke(params);
		}catch(Exception e){
			logger.error("Exception saveItems",e);
		}
		
		return resultado;
	}
	
	public WrapperResultados getActionWrapperResultados(Map<String, Object> params, String endPointName) throws ApplicationException{
		
		WrapperResultados resultado = null;
    	try {
			Endpoint endpoint = (Endpoint)endpoints.get(endPointName);
			resultado = (WrapperResultados) endpoint.invoke(params);
		} catch (BackboneApplicationException e) {
			e.getMessage();
			throw new ApplicationException("Error retrieving data");
		}
    	return resultado;
	}
	
	public WrapperResultados getActionWrapperResultadosMsgTxt(Map<String, Object> params, String endPointName) throws ApplicationException{
		WrapperResultados resultado = null;
		resultado= returnBackBoneInvoke(params, endPointName);
    	return resultado;
	}
	
	@SuppressWarnings("unchecked")
	public TableModelExport getModel(String Asegurado, String cdRamo, String cdElemento, String cdUnieco, String nmPoliEx ) throws ApplicationException {

		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();
		map.put("Asegurado",Asegurado);
		map.put("cdRamo",cdRamo);
		map.put("cdElemento",cdElemento);
		map.put("cdUnieco",cdUnieco);
		map.put("nmPoliEx",nmPoliEx);

		
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_POLIZA_RENOVAR_EXPORT");
		model.setInformation(lista);
		model.setColumnName(new String[]{"Asegurado", "Aseguradora", "Producto", "Poliza", "Inciso", "Fecha de Renovacion"});
		
		return model;
	}
	
    @SuppressWarnings("unchecked")
	public WrapperResultados getResultadoWrapper(Map<String, String> params, String endPointName) throws ApplicationException{
    	WrapperResultados resultado = null;
    	try {
			Endpoint endpoint = (Endpoint)endpoints.get(endPointName);
			resultado = (WrapperResultados) endpoint.invoke(params);
		} catch (BackboneApplicationException e) {
			e.getMessage();
			throw new ApplicationException("Error retrieving data");
		}
    	return (WrapperResultados)resultado;
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
    
}
