package mx.com.aon.portal.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.ConfigurarFormaCalculoAtributoVariableVO;
import mx.com.aon.portal.util.WrapperResultados;


/**
 * Interface de servicios para configurar forma de calculo de atributos variables.
 *
 */
public interface ConfigurarFormaCalculoAtributosVariablesManager {
	
    
	/**
	 * Obtiene una configuracion de forma de calculo de atributos variables. 
	 *
	 * @param cdIden 
	 * 
	 * @return ConfigurarFormaCalculoAtributoVariableVO
	 *			
	 */
	public ConfigurarFormaCalculoAtributoVariableVO getConfigurarFormaCalculoAtributo(String cdIden) throws ApplicationException;
	

	/**
	 * Obtiene una copia de configuracion de forma de calculo de atributos variables. 
	 *
	 * @param cdIden 
	 * 
	 * @return ConfigurarFormaCalculoAtributoVariableVO
	 *			
	 */
	public ConfigurarFormaCalculoAtributoVariableVO getConfigurarFormaCalculoAtributoCopia(String cdIden) throws ApplicationException;    


	/**
	 * Agrega una nueva forma de calculo de atributos variables.
	 * 
	 * @param  configurarFormaCalculoAtributoVariableVO
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *
	 */
	public String agregarConfigurarFormaCalculoAtributo(ConfigurarFormaCalculoAtributoVariableVO configurarFormaCalculoAtributoVariableVO) throws ApplicationException;


	/**
	 * Salva la informacion de configuracion forma de calculo atributos variables.
	 *  
	 *  @param configurarFormaCalculoAtributoVariableVO
	 *  
	 *	@return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */
	public String guardarConfigurarFormaCalculoAtributo(ConfigurarFormaCalculoAtributoVariableVO configurarFormaCalculoAtributoVariableVO) throws ApplicationException;
    

	/**
	 *  Da de baja a informacion de configuracion forma de calculo atributos variables.
	 *  
	 *  @param cdIden
	 *
	 *	@return Mensaje asociado en respuesta a la ejecucion del servicio. 
	 */
	public String borrarConfigurarFormaCalculoAtributo(String cdIden) throws ApplicationException;
	

	/**
	 *  Da de baja a informacion de configuracion forma de calculo atributos variables.
	 *  
	 *  @param configurarFormaCalculoAtributoVariableVO
	 *
	 *	@return  WrapperResultados
	 */
	public WrapperResultados copiarConfigurarFormaCalculoAtributo(ConfigurarFormaCalculoAtributoVariableVO configurarFormaCalculoAtributoVariableVO) throws ApplicationException;
	
	
	/**
	 * Obtiene un conjunto de configuracion forma de calculo atributos variables.
	 *
	 * @param dsUnieco 
	 * @param dsRamo
	 * @param dsElemen 
	 * @param dsTipSit
	 * @param start
	 * @param limit
	 * 
	 * @return Objeto PagedList 
	 *			
	 */
	public PagedList buscarConfigurarFormaCalculoAtributo(String dsUnieco, String dsRamo, String dsElemen, String dsTipSit, int start, int limit )throws ApplicationException;

	
	/**
	 * Obtiene una configuracion forma de calculo atributos variables y los exporta en Formato PDF, Excel, CSV, etc.
	 *  
	 * @param dsUnieco 
	 * @param dsRamo
	 * @param dsElemen 
	 * @param dsTipSit
	 *	
	 *	@return TableModelExport
	 */
	public TableModelExport getModel(String dsUnieco, String dsRamo, String dsElemen, String dsTipSit) throws ApplicationException;
	
}
