/**
 * 
 */
package mx.com.aon.portal.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.DesglosePolizasVO;

/**
 * Interface de servicios para Desglose de Polizas.
 *
 */
public interface DesglosePolizasManager  {
	
	/**
	 * Metodo que obtiene un unico registro de un desglose de poliza.
	 * 
	 * @param cdPerson
	 * @param cdTipCon
	 * @param cdRamo
	 * 
	 * @return DesglosePolizasVO
	 * 
	 * @throws ApplicationException
	 */
    public DesglosePolizasVO getDesglosePolizas(String cdPerson,String cdTipCon, String cdRamo) throws ApplicationException;
   
    /**
	 * Metodo que realiza la insercion de un nuevo registro de desglose de poliza.
	 * 
	 * @param desglosePolizasVO
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio
	 * 
	 * @throws ApplicationException
	 */
    public String agregarDesglosePolizas(DesglosePolizasVO desglosePolizasVO) throws ApplicationException;
    
    /**
	 * Metodo que realiza la copia de un registro de desglose de poliza seleccionado.
	 * 
	 * @param cdPerson
	 * @param cdRamo
	 * @param dsPerson
	 * @param dsRamo
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio
	 * 
	 * @throws ApplicationException
	 */
    public String copiarDesglosePolizas(String cdPerson, String cdRamo, String dsPerson, String dsRamo) throws ApplicationException;
    
    /**
     * Metodo que realiza la eliminacion de un registro de desglose de poliza seleccionado.
     * 
     * @param cdPerson
     * @param cdTipCon
     * @param cdRamo
     * 
     * @return Mensaje asociado en respuesta a la ejecucion del servicio
     * 
     * @throws ApplicationException
     */
	public String borrarDesglosePolizas(String cdPerson,String cdTipCon, String cdRamo) throws ApplicationException;
	
	/**
	 * Metodo que realiza una busqueda y obtiene un conjunto de  registros de desglose de poliza.
	 * 
	 * @param dsCliente
	 * @param dsConcepto
	 * @param dsProducto
	 * @param cdEstruct
	 * @param start
	 * @param limit
	 * 
	 * @return PagedList
	 * 
	 * @throws ApplicationException
	 */
	public PagedList buscarDesglosePolizas(String dsCliente, String dsConcepto, String dsProducto, String cdEstruct, int start, int limit )throws ApplicationException;
	
	/**
	 * Metodo que busca y obtiene un conjunto de registros para realizar
	 *  la exportacion del resultado a un formato PDF, XSL, TXT, etc.
	 *  
	 * @param dsCliente
	 * @param dsConcepto
	 * @param dsProducto
	 * 
	 * @return TableModelExport
	 * 
	 * @throws ApplicationException
	 */
	public TableModelExport getModel(String dsCliente, String dsConcepto, String dsProducto, String cdEstruct) throws ApplicationException;
}
