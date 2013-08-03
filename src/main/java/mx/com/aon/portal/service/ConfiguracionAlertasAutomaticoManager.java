/**
 * 
 */
package mx.com.aon.portal.service;

import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.ConfiguracionAlertasAutomaticoVO;

/**
 * Interface para atender requerimientos del action ConfiguracionAlertasAutomaticoAction.
 *
 */
public interface ConfiguracionAlertasAutomaticoManager  {
	
	/**
	 * Metodo que obtiene un unico registro de configuracion de alertas automatico.
	 * 
	 * @param cdIdUnico
	 * 
	 * @return objeto ConfiguracionAlertasAutomaticoVO
	 * 
	 * @throws ApplicationException
	 */
    public ConfiguracionAlertasAutomaticoVO getConfiguracionAlertasAutomatico(String cdIdUnico) throws ApplicationException;
   
    /**
     * Metodo que realiza la insercion de una configuracion de alertas automatico.
     * 
     * @param configuracionAlertasAutomaticoVO
     * 
     * @return Mensaje asociado en respuesta a la ejecucion del servicio.
     * 
     * @throws ApplicationException
     */
    public String agregarConfiguracionAlertasAutomatico(ConfiguracionAlertasAutomaticoVO configuracionAlertasAutomaticoVO) throws ApplicationException;
    
    /**
     * Metodo que realiza la actualizacion de una configuracion de alertas automatico editada.
     * 
     * @param configuracionAlertasAutomaticoVO
     * 
     * @return Mensaje asociado en respuesta a la ejecucion del servicio.
     * 
     * @throws ApplicationException
     */
	//public String guardarConfiguracionAlertasAutomatico(ConfiguracionAlertasAutomaticoVO configuracionAlertasAutomaticoVO) throws ApplicationException;
	
	/**
     * Metodo que realiza la eliminacion de una configuracion de alertas automatico seleccionada.
     * 
     * @param configuracionAlertasAutomaticoVO
     * 
     * @return Mensaje asociado en respuesta a la ejecucion del servicio.
     * 
     * @throws ApplicationException
     */
	public String borrarConfiguracionAlertasAutomatico(String cdIdUnico) throws ApplicationException;
	
	/**
     * Metodo que realiza la busqueda de un conjunto de registros de configuracion de alerta automatico.
     * 
     * @param configuracionAlertasAutomaticoVO
     * 
     * @return PagedList.
     * 
     * @throws ApplicationException
     */
	public PagedList buscarConfiguracionAlertasAutomatico(String dsUsuario, String dsCliente, String dsProceso, String dsRamo, String dsAseguradora, String dsRol, int start, int limit )throws ApplicationException;
	
	/**
	 * Realiza una busqueda y obtiene un conjunto de configuracion de alerta automatico y
	 *  exporta el resultado en Formato PDF, Excel, CSV, etc.
	 * 
	 * @return TableModelExport
	 * 
	 * @throws Exception
	 */
	public TableModelExport getModel(String dsUsuario,String dsCliente,String dsProceso,String dsRamo,String dsAseguradora,String dsRol) throws ApplicationException;


	/**
	 * Obtiene las variables a utilizar en el campo de Mensaje de Agregar/Editar
	 * @return
	 * @throws ApplicationException
	 */
	public List obtieneVariables () throws ApplicationException;
}
