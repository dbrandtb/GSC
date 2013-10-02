/**
 * 
 */
package mx.com.aon.portal.service.configworkflow;

import java.util.ArrayList;
import java.util.Map;

import mx.com.aon.portal.model.configworkflow.WorkFlowPs1VO;
import mx.com.gseguros.exception.ApplicationException;

/**
 * @author Alejandro Garcia
 * @date 18/07/2008
 */
public interface ConfigWorkFlowManager {

	/**
	 * @param 	endpoint
	 * @param 	parameters
	 * @param 	tipo
	 * @return 	id message
	 */
	public String getMessageNumber(String endpoint, Map<String, Object> parameters, String tipo) throws ApplicationException;

	/**
	 * @param 	endpoint
	 * @param 	parameters
	 * @return	id message
	 */
	public String getMessageNumber(String endpoint, Map<String, Object> parameters) throws ApplicationException;

	/**
	 * @param 	endpoint
	 * @param 	parameters
	 * @return
	 */
	public ArrayList<WorkFlowPs1VO> getProcesos(String endpoint,	Map<String, Object> parameters) throws ApplicationException;

}
