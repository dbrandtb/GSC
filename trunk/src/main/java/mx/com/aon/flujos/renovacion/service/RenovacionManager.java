/**
 * 
 */
package mx.com.aon.flujos.renovacion.service;

import java.util.List;
import java.util.Map;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;

/**
 * @author Alejandro García
 * @date 17/09/2008
 *
 */
public interface RenovacionManager {
    
    public PagedList getPagedList(Map<String, String> params, String EndPointName, int start, int limit) throws ApplicationException;
    
    @SuppressWarnings("unchecked")
	public List getItemList(String endpointName) throws ApplicationException;
    
    public String getAction(Map<String, Object> params, String endPointName) throws ApplicationException;
    
   	public TableModelExport getModel(String Asegurado, String cdRamo, String cdElemento, String cdUnieco, String nmPoliEx ) throws ApplicationException;

   	public WrapperResultados getResultadoWrapper(Map<String, String> params, String endPointName) throws ApplicationException;

	public WrapperResultados getActionWrapperResultados(Map<String, Object> params,String string) throws ApplicationException;
	
	/**
	 * Método encargado de validar la póliza en endosos
	 * @param param
	 * @param EndPointName
	 * @return boolean
	 * @throws ApplicationException
	 */
	public boolean validaEndosoPoliza(String param, String EndPointName) throws ApplicationException;
	
	/**
	 * Método para llamar a la aprobacion de las polizas a renovar y retornar el mensaje de respuesta
	 * @param params
	 * @param endPointName
	 * @return
	 * @throws ApplicationException
	 */
	public WrapperResultados getActionWrapperResultadosMsgTxt(Map<String, Object> params, String endPointName) throws ApplicationException;
}
