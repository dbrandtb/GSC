package mx.com.aon.portal.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.NumeroIncisoVO;

/**
 * Interface de servicios para la configuracion de Numeros de Incisos.
 *
 */
public interface NumeroIncisosManager {


	/**
	 *  Obtiene un conjunto de numeros de incisos.
	 * 
	 *  @param dsUniEco
	 *  @param dsRamo
	 *  @param dsElemen
	 *  @param dsIndSitSubSit
	 *  @param start
	 *  @param limit
	 *  
	 *  @return Objeto PagedList 
	 */	
	public PagedList buscarNumerosInciso(String dsUniEco, String dsRamo, String dsElemen, String dsIndSitSubSit, int start, int limit ) throws ApplicationException;

    
	
	/**
	 *  Agrega un nuevo numero de inciso.
	 * 
	 *  @param numeroIncisoVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */			
	public String insertarNumerosInciso(NumeroIncisoVO numeroIncisoVO) throws ApplicationException;

 
	/**
	 *  Salva la informacion de numero de inciso.
	 * 
	 *  @param numeroIncisoVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */			
	public String guardarNumerosInciso(NumeroIncisoVO numeroIncisoVO) throws ApplicationException;


	/**
	 *  Relaiza la baja de numero de inciso.
	 * 
	 *  @param cdUniEco
	 *  @param cdElemento
	 *  @param cdRamo
	 *  @param indSituac
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */			
	public String borrarNumeroInciso(String cdUniEco,String cdElemento,String cdRamo, String indSituac) throws ApplicationException;

    
	/**
	 *  Obtiene la informacion de numeros de incisos.
	 * 
	 *  @param cdUniEco
	 *  @param cdElemento
	 *  @param cdRamo
	 *  @param indSituac
	 *  
	 *  @return NumeroIncisoVO
	 */		
	public NumeroIncisoVO getNumeroInciso(String cdUniEco,String cdElemento,String cdRamo, String indSituac) throws ApplicationException;


	/**
	 *  Obtiene informacion sobre tramos.
	 *   
	 *  @param cdRamo
	 *  
	 *  @return NumeroIncisoVO
	 */		
	public NumeroIncisoVO getTramos(String cdRamo) throws ApplicationException;
    

	/**
	  * Obtiene un conjunto de numeros de incisos y exporta el resultado en Formato PDF, Excel, CSV, etc.
	  * 
	  * param dsUniEco
	  * param cdRamo
	  * param dsElemen
	  * param dsIndSitSubSit
	  * 
	  * @return TableModelExport
	  */
	public TableModelExport getModel(String dsUniEco, String dsRamo, String dsElemen, String dsIndSitSubSit) throws ApplicationException;
}
