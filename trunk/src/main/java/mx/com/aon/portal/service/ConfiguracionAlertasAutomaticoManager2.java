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
public interface ConfiguracionAlertasAutomaticoManager2  {
	
	
    /**
     * Metodo que realiza la actualizacion de una configuracion de alertas automatico editada.
     * 
     * @param configuracionAlertasAutomaticoVO
     * 
     * @return Mensaje asociado en respuesta a la ejecucion del servicio.
     * 
     * @throws ApplicationException
     */
	public String guardarConfiguracionAlertasAutomatico(ConfiguracionAlertasAutomaticoVO configuracionAlertasAutomaticoVO) throws ApplicationException;
	
	
}
