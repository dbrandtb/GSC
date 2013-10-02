package mx.com.aon.portal.service;

import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.FormaDeCalculoAtributoVO;
import mx.com.gseguros.exception.ApplicationException;

/**
 * Interface de servicios para Configurar Calculo de Atributo
 *
 */
public interface ConfigurarFormaCalculoAtributoManager  {

	/**
	 *  Obtiene configuracion de atributos de formas de calculo variable
	 * 
	 *  @param codigoElemento
	 *  
	 *  @return Objeto FormaDeCalculoAtributoVO
	 *  
	 *  @throws ApplicationException
	 */
    public FormaDeCalculoAtributoVO getConfigurarFormaCalculoAtributo(String codigoElemento) throws ApplicationException;

	/**
	 *  Inserta o actualiza atributos de formas de calculo variable
	 * 
	 *  @param formaDeCalculoAtributoVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */
	public String agregarConfigurarEstructura(FormaDeCalculoAtributoVO formaDeCalculoAtributoVO) throws ApplicationException;

	/**
	 *  Elimina atributos de formas de calculo variable
	 * 
	 *  @param cdIden
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */
	public String borrarConfigurarFormaCalculoAtributo(String cdIden ) throws ApplicationException;
	
	/**
	 *  Obtiene un conjunto de atributos de formas de calculo variable
	 * 
	 *  @param cliente
	 *  @param tipSituacion
	 *  @param aseguradora
	 *  @param producto
	 *  @param start comienzo o limite inferior de la paginacion de la grilla
	 *  @param limit final o limite superior de la paginacion la grilla
	 *  
	 *  @return Conjunto de objetos ConfigurarFormaCalculoAtributoVariableVO
	 *  
	 *  @throws ApplicationException
	 */
	public PagedList buscarConfigurarFormaCalculoAtributo(String cliente, String tipSituacion, String aseguradora, String producto, int start, int limit )throws ApplicationException;
	
    /**
	 *  Obtiene una lista de atributos formas de calculo variable
	 * 
	 *  @param cliente
	 *  @param tipSituacion
	 *  @param aseguradora
	 *  @param producto
	 *      
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */
	public TableModelExport getModel(String cliente, String tipSituacion, String aseguradora, String producto) throws ApplicationException;
}
