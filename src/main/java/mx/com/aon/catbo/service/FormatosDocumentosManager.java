package mx.com.aon.catbo.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.model.FormatoDocumentoVO;
import mx.com.aon.portal.service.PagedList;




/**
 * Interface de servicios para AyudaCoberturas.
 *
 */
public interface FormatosDocumentosManager{
	
	/**
	 *  Obtiene un conjunto de Formatos de Documentos
	 *   
	 * @param dsNomFormato
	 * 
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */
	public PagedList buscarFormatosDocumentos(String dsNomFormato, int start, int limit )throws ApplicationException;

	/**
	 *  Elimina un Formato de Documento.
	 * 
	 *  @param cdNotificacion
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
	public String borrarFormatosDocumentos(String cdFormato) throws ApplicationException;
	
	/**
	 *  Obtiene detalle de persona de orden de compra
	 * 
	 *  @param cdCarro
	 *  
	 *  @return Objeto OrdenDeCompraEncOrdenVO
	 *  
	 *  @throws ApplicationException
	 */	    
	public FormatoDocumentoVO getFormatosDocumentos(String cdFormato) throws ApplicationException;
	
	/**
	 *  Inserta o Actualiza formatos de documentos.
	 * 
	 *  @param AyudaCoberturasVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
	public String guardarFormatosDocumentos(FormatoDocumentoVO formatoDocumentoVO) throws ApplicationException;	
	
	/**
	 *  Obtiene una lista de formatos de documentos para la exportacion a un formato predeterminado.
	 * 
	 *  @param descripcion: parametro con el que se realiza la busqueda.
	 *  
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */
	public TableModelExport getModel(String dsNomFormato) throws ApplicationException;

}
