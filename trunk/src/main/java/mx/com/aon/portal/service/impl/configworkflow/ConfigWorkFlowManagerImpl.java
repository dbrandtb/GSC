/**
 * 
 */
package mx.com.aon.portal.service.impl.configworkflow;

import java.util.ArrayList;
import java.util.Map;

import mx.com.aon.portal.model.configworkflow.WorkFlowPs1VO;
import mx.com.aon.portal.model.configworkflow.WorkFlowPs2VO;
import mx.com.aon.portal.service.configworkflow.ConfigWorkFlowManager;
import mx.com.aon.portal.service.impl.AbstractManager;
import mx.com.aon.tmp.Endpoint;
import mx.com.gseguros.exception.ApplicationException;

/**
 * @author Alejandro Garcia
 * @date   18/07/2008
 * @see    com.aon.catweb.portal.service.configworkflow.ConfigWorkFlowManager
 */
public class ConfigWorkFlowManagerImpl extends AbstractManager implements ConfigWorkFlowManager{
	
	public String getMessageNumber(String endpoint, Map<String, Object> parameters, String tipo) throws ApplicationException{
		
		Endpoint endPoint = (Endpoint) endpoints.get(endpoint);
		WorkFlowPs2VO vo = new WorkFlowPs2VO();
		String msj = null;
		
        try {
        	vo = (WorkFlowPs2VO)endPoint.invoke(parameters);
        	msj = vo.getOutParamText();
        } catch (Exception e) {
        	logger.debug("Exception getMessageNumber:"+e);
        }
        return msj;
        
	}
	
	public String getMessageNumber(String endpoint, Map<String, Object> parameters) throws ApplicationException{
		
		Endpoint endPoint = (Endpoint) endpoints.get(endpoint);
		WorkFlowPs1VO vo = new WorkFlowPs1VO();
		String msj = null;
		
        try {
        	vo = (WorkFlowPs1VO)endPoint.invoke(parameters);
        	msj = vo.getOutParamText();
        } catch (Exception e) {
        	logger.debug("Exception getMessageNumber:"+e);
        }
        return msj;
        
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<WorkFlowPs1VO> getProcesos(String endpoint, Map<String, Object> parameters) throws ApplicationException{
		
		ArrayList<WorkFlowPs1VO> procesos = null;
		Endpoint endPoint = (Endpoint) endpoints.get(endpoint);
		
		try {
			procesos = (ArrayList<WorkFlowPs1VO>) endPoint.invoke(parameters);
		}catch (Exception e){
			logger.debug("Exception getProcesos: "+e);
		}
		return (ArrayList<WorkFlowPs1VO>) procesos;
	}

}
