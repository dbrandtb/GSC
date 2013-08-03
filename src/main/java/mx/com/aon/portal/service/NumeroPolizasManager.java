package mx.com.aon.portal.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.NumeroPolizaVO;


/**
 * Interface de servicios para la configuracion de descuentos.
 *
 */
public interface NumeroPolizasManager {


	/**
	 *  Obtiene un conjunto de numeros de polizas.
	 * 
	 *  @param dsUniEco
	 *  @param dsRamo
	 *  @param dsElemen
	 *  @param start
	 *  @param limit
	 *  
	 *  @return Objeto PagedList 
	 */	
    public PagedList buscarNumerosPoliza(String dsUniEco, String dsRamo, String dsElemen, int start, int limit ) throws ApplicationException;


	/**
	 *  Inserta un nuevo de numeros de polizas.
	 * 
	 *  @param numeroPolizaVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio. 
	 */	
    public String insertarNumerosPoliza(NumeroPolizaVO numeroPolizaVO) throws ApplicationException;

    
	/**
	 *  Salva la informacion de numeros de polizas.
	 * 
	 *  @param numeroPolizaVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio. 
	 */	
    public String guardarNumerosPoliza(NumeroPolizaVO numeroPolizaVO) throws ApplicationException;


	/**
	 *  Relaiza la baja de numeros de polizas.
	 * 
	 *  @param cdUniEco
	 *  @param cdElemento
	 *  @param cdRamo
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio. 
	 */	
    public String borrarNumeroPoliza(String cdUniEco,String cdElemento,String cdRamo) throws ApplicationException;
    
    
    /**
	 *  Relaiza la baja de numeros de polizas.
	 * 
	 *  @param cdNumPol
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio. 
	 */	
    public String borrarNumPolEmision(String cdNumPol) throws ApplicationException;


	/**
	 *  Obtiene un numero de poliza.
	 * 
	 *  @param cdUniEco
	 *  @param cdElemento
	 *  @param cdRamo
	 *  
	 *  @return NumeroPolizaVO 
	 */	
    public NumeroPolizaVO getNumeroPoliza(String cdUniEco,String cdElemento,String cdRamo) throws ApplicationException;


	/**
	  * Obtiene un conjunto de numeros de polizas y exporta el resultado en Formato PDF, Excel, CSV, etc.
	  * 
	  * @return success
	  * 
	  * @throws Exception
	  */
    public TableModelExport getModel(String dsUniEco, String dsRamo, String dsElemen) throws ApplicationException;
}
