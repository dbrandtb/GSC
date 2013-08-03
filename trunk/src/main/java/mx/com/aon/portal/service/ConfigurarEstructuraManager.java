/**
 * 
 */
package mx.com.aon.portal.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.ConfigurarEstructuraVO;
import mx.com.aon.portal.model.PersonasVO;

import java.util.ArrayList;


/**
 *  
 * <pre>
 *    Implementacion de servicio para consulta de informacion para paginacion de la tabla
 *    de estructuras configurar estructura.
 * <Pre>
 * 
 */
public interface ConfigurarEstructuraManager  {
	/**
	 * Metodo que busca y obtiene un unico registro para configurar estructura.
	 * 
	 * @return ConfigurarEstructuraVO
	 * 
	 * @throws ApplicationException
	 */
    public ConfigurarEstructuraVO getConfigurarEstructura(String codigoElemento) throws ApplicationException;
    
    /**
     * Metodo que inserta una nueva estructura
     * 
     * @return Mensaje asociado en respuesta a la ejecucion del servicio.
     * 
     * @throws ApplicationException
     */
	public String agregarConfigurarEstructura(ConfigurarEstructuraVO configurarEstructuraVO) throws ApplicationException;

	/**
	 * Metodo que actualiza una estructura editada en pantalla.
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException 
	 */
	public String guardarConfigurarEstructura(ConfigurarEstructuraVO configurarEstructuraVO) throws ApplicationException;
	
	/**
	 * Metodo que elimina una estructura seleccionada en el grid de la pantalla.
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException 
	 */
	public String borrarConfigurarEstructura(String codigoEstructura,String codigoElemento ) throws ApplicationException;
	
	/**
	 * Metodo que copia una estructura seleccionada en el grid de la pantalla.
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException 
	 */
	public String copiarConfigurarEstructura(String codigoEstructura,String codigoElemento ) throws ApplicationException;
	
	/**
	 * Metodo que busca y obtiene un conjunto de registros para el grid de la pantalla.
	 * 
	 * @return PagedList.
	 * 
	 * @throws ApplicationException 
	 */
	public PagedList buscarConfigurarEstructuras(String codigoEstructura,String nombre, String vinculoPadre, String nomina, int start, int limit )throws ApplicationException;
	
	/**
	 * Metodo que busca y obtiene un conjunto de registros de clientes.
	 * 
	 * @return ArrayList.
	 * 
	 * @throws ApplicationException 
	 */
	public ArrayList<PersonasVO> obtenerClientes() throws ApplicationException;
	
	/**
	 * Obtiene un conjunto de registros de elementos de estructuras y 
	 * exporta el resultado en Formato PDF, Excel, CSV, etc.
	 * 
	 * @return TableModelExport
	 * 
	 * @throws ApplicationException
	 */
	public TableModelExport getModel(String codigoEstructura,String nombre, String vinculoPadre, String nomina) throws ApplicationException;
}
