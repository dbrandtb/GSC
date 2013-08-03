package mx.com.aon.portal.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.ConfiguracionNumeracionEndososVO;

/**
 * Interface de servicios para Configurar Numeracion de Endosos  
 *
 */
public interface ConfigurarNumeracionEndososManager {
	
	/**
	 * Metodo que busca y obtiene un conjunto de registros de Numeracion de Endosos
	 * 
	 * @param start
	 * @param limit
	 * @param configuracionNumeracionEndososVO
	 * 
	 * @return PagedList
	 * 
	 * @throws ApplicationException
	 */
	public PagedList obtenerNumeracionEndosos(int start, int limit, ConfiguracionNumeracionEndososVO configuracionNumeracionEndososVO) throws ApplicationException;
	
    /**
     * Metodo que busca y obtiene un unico registro de Numeracion de Endosos
     * 
     * @param cdElemento
     * @param cdUniEco
     * @param cdRamo
     * 
     * @return ConfiguracionNumeracionEndososVO
     * 
     * @throws ApplicationException
     */
	public ConfiguracionNumeracionEndososVO getNumeracionEndosos(String cdElemento,String cdUniEco,String cdRamo) throws ApplicationException;
	
	/**
	 * Metodo que elimina un registro de Numeracion de Endosos seleccionado.
	 * 
	 * @param configuracionNumeracionEndososVO
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
	public String borrarNumeracionEndosos(ConfiguracionNumeracionEndososVO configuracionNumeracionEndososVO) throws ApplicationException;
	
	/**
	 * Metodo que inserta un nuevo registro o actualiza un registro editado en pantalla de Numeracion de Endosos.
	 * 
	 * @param configuracionNumeracionEndososVO
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
	public String guardarOActualizarNumeracionEndosos(ConfiguracionNumeracionEndososVO configuracionNumeracionEndososVO) throws ApplicationException;
	
	/**
	 * Obtiene un conjunto de ayudas de cobertura y exporta el resultado en Formato PDF, Excel, CSV, etc.
	 * 
	 * @param configuracionNumeracionEndososVO
	 * 
	 * @return TableModelExport
	 * 
	 * @throws Exception
	 */
	public TableModelExport getModel(ConfiguracionNumeracionEndososVO configuracionNumeracionEndososVO) throws ApplicationException;

}