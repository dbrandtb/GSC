/**
 * 
 */
package mx.com.aon.portal.service;

import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.MecanismoDeTooltipVO;
import mx.com.gseguros.exception.ApplicationException;

import java.util.List;


/**
 * Interface de servicios para Mecanismo de Tooltip.
 *
 */
public interface MecanismoDeTooltipManager{
	
	/**
	 *  Obtiene un conjunto de Mecanismo de Tooltip.
	 * 
	 *  @param nbObjeto
	 *  @param lang_Code
	 *  @param dsTitulo
	 *  @param start
	 *  @param limit
	 *  
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */
	public PagedList buscarMecanismoDeTooltip(String nbObjeto, String lang_Code, String dsTitulo, int start, int limit )throws ApplicationException;
   

	/**
	 *  Agrega un nuevo Mecanismo de Tootip.
	 * 
	 *  @param mecanismoDeTooltipVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
	public String agregarGuardarMecanismoDeTooltip(MecanismoDeTooltipVO mecanismoDeTooltipVO) throws ApplicationException;
	

	/**
	 *  Da de baja informacion de un Mecanismo de Tooltip.
	 * 
	 *  @param idObjedo
	 *  @param lang_Code
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
	public String borrarMecanismoDeTooltip(String idObjedo, String lang_Code) throws ApplicationException;


	/**
	 *  Obtiene un Mecanismo de Tooltip especifica en base a un parametro de entrada.
	 * 
	 *  @param idObjedo
	 *  @param lang_Code
	 *  
	 *  @return MecanismoDeTooltipVO
	 *  
	 *  @throws ApplicationException
	 */
	public MecanismoDeTooltipVO getMecanismoDeTooltipVO(String idObjedo, String lang_Code) throws ApplicationException;
	

	/**
	 *  Copia un Mecanismo de Tooltip.
	 * 
	 *  @param idobjedo
	 *  @param lang_Code
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
	public String copiarMecanismoDeTooltipVO(MecanismoDeTooltipVO mecanismoDeTooltipVO) throws ApplicationException;
	

	/**
	 *  Obtiene un conjunto de Mecanismo de Tooltip para la exportacion a un formato predeterminado.
	 * 
	 *  @param dstitulo
	 *  @param nbEtiqueta
	 *  
	 *  @return TableModelExport.
	 *  
	 *  @throws ApplicationException
	 */
	public TableModelExport getModel(String nbObjeto, String lang_Code, String dsTitulo) throws ApplicationException;


	/**
	 *  Obtiene la lista de tooltips asociado a una pantalla.
	 *
	 *  @param cdTitulo codigo de la pantalla
	 *  @param langCode lenguaje 
	 *
	 *  @return Lista de tooltips asociados a una pagina.
	 *
	 *  @throws ApplicationException
	 */
    public List getToolTipsForPage(String cdTitulo,String langCode) throws ApplicationException;

    /**
     * Obtiene la lista de mensajes para el usuario (Error, Confirmación, etc)
     * 
     * @param cdTitle	Tipo de Mensaje
     * @param langCode	Lenguaje predeterminado
     * @return			Lista de mensajes
     * @throws ApplicationException
     */
    public List getGB_Messages (String cdTitle, String langCode) throws ApplicationException;
}
