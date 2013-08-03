package mx.com.aon.portal.web;

import org.apache.log4j.Logger;

import mx.com.aon.portal.service.MetodosCancelacionManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.model.MetodoCancelacionVO;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.util.Util;

import java.io.InputStream;
import java.util.List;


/**
 *   Action que atiende la peticion de informacion para la consulta de datos de 
 *   la tabla con mecanismo de paginacion de la pantalla Metodos Cancelacion.
 *   
 *   @extends AbstractListAction
 * 
 */
@SuppressWarnings("serial")
public class ListaMetodosCancelacionAction extends AbstractListAction{

	private static Logger logger = Logger.getLogger(ListaMetodosCancelacionAction.class);

	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */
	private transient MetodosCancelacionManager  metodosCancelacionManager;

	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo MetodoCancelacionVO
	 * con los valores de la consulta.
	 */
	private List<MetodoCancelacionVO> listaMetodosCancelacion;

	private String cdMetodo;
	private String dsMetodo;
	
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
	 *  Obtiene un conjunto de metodos de cancelacion y los mustra en el grid de la Pantalla.
	 *  
	 * @return success
	 *  
	 * @throws Exception
	 */	
	@SuppressWarnings("unchecked")
	public String cmdBuscarClick () {
		try {
    		PagedList pagedList = metodosCancelacionManager.buscarMetodosCancelacion(cdMetodo, dsMetodo, start, limit);
    		listaMetodosCancelacion = pagedList.getItemsRangeList();
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


    /**
	 * Exporta el contenido del grid en Formato PDF, Excel, etc.
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	public String cmdExportarClick() throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );
		}
		
		try {
			contentType = Util.getContentType(formato);
            if (logger.isDebugEnabled()) {
                logger.debug( "content-type : " + contentType );
            }
			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			
			filename = "AgrupacionPolizas." + exportFormat.getExtension();
			
			TableModelExport model = metodosCancelacionManager.getModel(cdMetodo, dsMetodo);
			
			inputStream = exportFormat.export(model);
			
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;

	}
	
	
	public MetodosCancelacionManager getMetodosCancelacionManager() {
		return metodosCancelacionManager;
	}
	public void setMetodosCancelacionManager(
			MetodosCancelacionManager metodosCancelacionManager) {
		this.metodosCancelacionManager = metodosCancelacionManager;
	}
	public List<MetodoCancelacionVO> getListaMetodosCancelacion() {
		return listaMetodosCancelacion;
	}
	public void setListaMetodosCancelacion(
			List<MetodoCancelacionVO> listaMetodosCancelacion) {
		this.listaMetodosCancelacion = listaMetodosCancelacion;
	}
	public String getCdMetodo() {
		return cdMetodo;
	}
	public void setCdMetodo(String cdMetodo) {
		this.cdMetodo = cdMetodo;
	}
	public String getDsMetodo() {
		return dsMetodo;
	}
	public void setDsMetodo(String dsMetodo) {
		this.dsMetodo = dsMetodo;
	}
	public String getFormato() {return formato;}
	public void setFormato(String formato) {this.formato = formato;}

	public InputStream getInputStream() {return inputStream;}
	public void setInputStream(InputStream inputStream) {this.inputStream = inputStream;}

	public String getFilename() {return filename;}
	public void setFilename(String filename) {this.filename = filename;}

	public void setExportMediator(ExportMediator exportMediator) {this.exportMediator = exportMediator;}
}
