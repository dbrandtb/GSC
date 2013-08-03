package mx.com.aon.catbo.web;

import java.util.List;

import mx.com.aon.core.ApplicationException;

import mx.com.aon.catbo.model.FaxesVO;
import mx.com.aon.catbo.model.TareaVO;
import mx.com.aon.catbo.model.ArchivoVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.catbo.service.TareasCatBoManager;
import mx.com.aon.catbo.service.AdministrarTipoArchivoManager;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.util.Util;


import org.apache.log4j.Logger;

import java.io.InputStream;
/**
 *   Action que atiende las peticiones de que vienen de la pantalla Formatos Documentos
 * 
 */
@SuppressWarnings("serial")
public class ListaAdministracionTipoArchivoAction extends AbstractListAction{

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaAdministracionTipoArchivoAction.class);

	
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient AdministrarTipoArchivoManager administrarTipoArchivoManager;
	
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo TareaVO
	 * con los valores de la consulta.
	 */
	private List<ArchivoVO> estructuraListArchivo;
	private String dsArchivo; 
	private String dsModulo; 
	private String cdPriord; 
	private String cdProceso;
	private String cdTipoar;
	private String indarchivo;

	
	private boolean success;
	
	/**
	 * Atributo agregado por struts que contiene el tipo de formato a ser exportado
	 */
	private String formato;
	
	/**
	 * Atributo de respuesta con el flujo de datos para regresar el archivo generado.
	 */
	private InputStream inputStream;
	
	/**
	 * Atributo de respuesta interpretado por strust con el nombre del archivo generado 
	 */
	private String filename;
		
	/**
	 * Atributo inyectado por spring el cual direcciona a travez del tipo de formato para generar 
	 * el archivo a ser exportado
	 */
	private ExportMediator exportMediator;
	

	/**
	 * Metodo que realiza la busqueda de Tareas de Cat Bo
	 * sin parametros de entrada
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarClick() throws Exception{
		try{
            PagedList pagedList = this.administrarTipoArchivoManager.obtenerArchivo(dsArchivo, start, limit);
            estructuraListArchivo = pagedList.getItemsRangeList();
            totalCount = pagedList.getTotalItems();                                                    
            success = true;
            return SUCCESS;
        }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}
	
	
	public String cmdGuardarClick()throws Exception
	{ 
		String messageResult = "";
        try
        {	 
            if(cdTipoar==null){
            	cdTipoar="";
            }
            messageResult = this.administrarTipoArchivoManager.guardaArchivos(cdTipoar, dsArchivo, indarchivo);
            success = true;
            addActionMessage(messageResult);
            return SUCCESS;
        }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}	
	
	
	
	/**
	 * Metodo que busca un conjunto de Tareas de Cat Bo 
	 * y exporta el resultado en Formato PDF, Excel, etc.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdExportarClick() throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug( "Archivo : " + formato );   
		}
		try {
			contentType = Util.getContentType(formato);
            if (logger.isDebugEnabled()) {
                logger.debug( "content-type : " + contentType );
            }
            
			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			
			filename = "Archivos." + exportFormat.getExtension();
			TableModelExport model = administrarTipoArchivoManager.getModel2(dsArchivo);

			inputStream = exportFormat.export(model);
			
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;
	}

	
	public String cmdBorrarClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = this.administrarTipoArchivoManager.borraArchivo(cdTipoar);
            success = true;
            addActionMessage(messageResult);
            return SUCCESS;
          }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}
	
	
	public String cmdIrAdministracArchivoClick() throws Exception {
		return "irPantallaAdministracionTipoArchivo";
		
	}
	
	//Buscar tarea para validar
	/*public String cmdBuscarClickValidar() throws Exception{
		try{
            PagedList pagedList = this.tareasCatBoManager.buscarTareasCatBoValidar(cdProceso, start, limit);
            mEstructuraListTareas = pagedList.getItemsRangeList();
            totalCount = pagedList.getTotalItems();                                                    
            success = true;
            return SUCCESS;
        }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}*/

	 /**
	 * Metodo que atiende una peticion para dirigirse a la pagina Adminitracion Atributos Fax
	 * 
	 * @return String
	 *  
     */
	public String cmdAdministracionTipoFax(){
		return "administracionTiposFax";
	}	
	
	

	
	public String getDsModulo() {
		return dsModulo;
	}

	public void setDsModulo(String dsModulo) {
		this.dsModulo = dsModulo;
	}

	public String getCdPriord() {
		return cdPriord;
	}

	public void setCdPriord(String cdPriord) {
		this.cdPriord = cdPriord;
	}

	


	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}


	public String getFormato() {return formato;}
	public void setFormato(String formato) {this.formato = formato;}

	public InputStream getInputStream() {return inputStream;}
	public void setInputStream(InputStream inputStream) {this.inputStream = inputStream;}

	public String getFilename() {return filename;}
	public void setFilename(String filename) {this.filename = filename;}

	public void setExportMediator(ExportMediator exportMediator) {this.exportMediator = exportMediator;}


	public String getCdProceso() {
		return cdProceso;
	}


	public void setCdProceso(String cdProceso) {
		this.cdProceso = cdProceso;
	}


	public AdministrarTipoArchivoManager getAdministrarTipoArchivoManager() {
		return administrarTipoArchivoManager;
	}


	public void setAdministrarTipoArchivoManager(
			AdministrarTipoArchivoManager administrarTipoArchivoManager) {
		this.administrarTipoArchivoManager = administrarTipoArchivoManager;
	}


	


	public List<ArchivoVO> getEstructuraListArchivo() {
		return estructuraListArchivo;
	}


	public void setEstructuraListArchivo(List<ArchivoVO> estructuraListArchivo) {
		this.estructuraListArchivo = estructuraListArchivo;
	}


	public String getDsArchivo() {
		return dsArchivo;
	}


	public void setDsArchivo(String dsArchivo) {
		this.dsArchivo = dsArchivo;
	}


	public String getCdTipoar() {
		return cdTipoar;
	}


	public void setCdTipoar(String cdTipoar) {
		this.cdTipoar = cdTipoar;
	}


	public String getIndarchivo() {
		return indarchivo;
	}


	public void setIndarchivo(String indarchivo) {
		this.indarchivo = indarchivo;
	}
	
	
}