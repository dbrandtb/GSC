/**
 * 
 */
package mx.com.aon.portal.service;

import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.AyudaCoberturasVO;
import mx.com.gseguros.exception.ApplicationException;


/**
 * Interface de servicios para AyudaCoberturas.
 *
 */
public interface AyudaCoberturasManager{
	
	/**
	 *  Obtiene un conjunto de ayudas de coberturas.
	 * 
	 *  @param dsUnieco
	 *  @param dsSubram
	 *  @param dsGarant
	 *  @param dsTipram
	 *  @param dsRamo
	 *  @param start
	 *  @param limit
	 *  
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */
	public PagedList buscarAyudaCoberturas(String dsUnieco, String dsSubram, String dsGarant, String dsTipram, String dsRamo, String pv_lang_code_i,int start, int limit )throws ApplicationException;
   
	/**
	 *  Inserta una nueva ayuda de coberturas.
	 * 
	 *  @param AyudaCoberturasVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
	public String insertarAyudaCoberturas(AyudaCoberturasVO ayudaCoberturasVO) throws ApplicationException;
	
	/**
	 *  Actualiza una ayuda de coberturas modificada.
	 * 
	 *  @param AyudaCoberturasVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
	public String guardarAyudaCoberturas(AyudaCoberturasVO ayudaCoberturasVO) throws ApplicationException;
	
	/**
	 *  Elimina una ayuda de cobertura seleccionada.
	 * 
	 *  @param cdGarantiaxCia
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
	public String borrarAyudaCoberturas(String cdGarantiaxCia) throws ApplicationException;

	/**
	 *  Copia una ayuda de cobertura seleccionada.
	 * 
	 *  @param objeto AyudaCoberturasVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
	public String copiarAyudaCoberturas(AyudaCoberturasVO ayudaCoberturasVO) throws ApplicationException;
	
	/**
	 *  Obtiene una configuracion de ayuda de cobertura especifica en base a un parametro de entrada.
	 * 
	 *  @param cdGarantiaxCia
	 *  
	 *  @return Objeto EstructuraVO
	 *  
	 *  @throws ApplicationException
	 */
	public AyudaCoberturasVO getAyudaCoberturas(String cdGarantiaxCia) throws ApplicationException;
	
	/**
	 *  Obtiene un conjunto de ayudas de coberturas para la exportacion a un formato predeterminado.
	 * 
	 *  Parametros de busqueda:
	 *  @param dsUniEco
	 *  @param dsSubRam
	 *  @param dsGarant
	 *  @param dsTipram
	 *  @param dsRamo
	 *  
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */
	public TableModelExport getModel(String dsUnieco, String dsSubram, String dsGarant, String dsTipram, String dsRamo, String pv_lang_code_i) throws ApplicationException;

	
}
