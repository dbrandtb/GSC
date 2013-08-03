package mx.com.aon.portal.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.BackBoneResultVO;
import mx.com.aon.portal.model.MotivoCancelacionVO;


/**
 * Interface de servicios para la configuracion de motivos de cancelacion.
 *
 */
public interface MotivosCancelacionManager {


	/**
	 *  Obtiene un conjunto de motivos de cancelacion.
	 *  
	 *  @param cdRazon
	 *  @param dsRazon
	 *  @param start
	 *  @param limit
	 *  
	 *  @return Objeto PagedList 
	 */	
	public PagedList buscarMotivosCancelacion(String cdRazon, String dsRazon, int start, int limit ) throws ApplicationException;


	/**
	 *  Salva la informacion de motivos de cancelacion.
	 * 
	 *  @param motivoCancelacionVO
	 *  
	 *  @return BackBoneResultVO
	 */		
	public BackBoneResultVO guardarMotivosCancelacion(MotivoCancelacionVO motivoCancelacionVO) throws ApplicationException;

    

	/**
	 *  Agrega nuevo mativos de cancelacion.
	 * 
	 *  @param motivoCancelacionVO
	 *  
	 *  @return BackBoneResultVO
	 */		
	public BackBoneResultVO insertarMotivosCancelacion(MotivoCancelacionVO motivoCancelacionVO) throws ApplicationException;



	/**
	 *  Realiza la baja de motivos de cancelacion.
	 * 
	 *  @param cdRazon
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
	public String borrarMotivosCancelacion(String cdRazon) throws ApplicationException;
    

	/**
	 *  Realiza la baja de requisito de cancelacion.
	 * 
	 *  @param cdRazon
	 *  @param cdAdvert
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
	public String borrarRequisitoCancelacion(String cdRazon, String cdAdvert) throws ApplicationException;


	
	/**
	 *  Obtiene motivos de cancelacion.
	 * 
	 *  @param cdRazon
	 *  
	 *  @return MotivoCancelacionVO
	 */		
	public MotivoCancelacionVO getMotivosCancelacion(String cdRazon) throws ApplicationException;
    
    
	/**
	 *  Obtiene requisitos de cancelacion.
	 * 
	 *  @param cdRazon
	 *  @param start
	 *  @param limit
	 *  
	 *  @return MotivoCancelacionVO
	 */		
	public PagedList getRequisitoCancelacion(String cdRazon, int start, int limit ) throws ApplicationException;


	/**
	  * Obtiene un conjunto de seciones y exporta el resultado en Formato PDF, Excel, CSV, etc.
	  *
	  * @param cdRazon
	  * @param dsRazon
	  * 
	  * @return TableModelExport
	  */
	public TableModelExport getModel(String cdRazon, String dsRazon) throws ApplicationException;
}
