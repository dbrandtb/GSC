package mx.com.aon.portal.web;

import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.MensajeErrorVO;
import mx.com.aon.portal.service.MensajesErrorManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.Util;


/**
 *   Action que atiende la peticion de informacion para la consulta de datos de 
 *   la tabla con mecanismo de paginacion de la pantalla Mensajes de Error.
 *   
 *   @extends AbstractListAction
 * 
 */
public class ListaMensajesErrorAction extends AbstractListAction {
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaMensajesErrorAction.class);

	private static final long serialVersionUID = 1L;

	private transient MensajesErrorManager mensajesErrorManager;

	private String cdError;

	private String dsMensaje;
	
	private List<MensajeErrorVO> listaMensajes;
	
	private ExportMediator exportMediator;
	
	private String formato;
	
	private String filename;
	
	private InputStream inputStream;
	
	/**
	 * Metodo que busca y obtiene un conjunto de registros de Mensajes de error
	 * para listar en el grid de la pantalla.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarMensajesError () throws Exception {
		try {
			PagedList pagedList = new PagedList();
			pagedList = mensajesErrorManager.buscarMensajes(cdError, dsMensaje, start, limit);
			listaMensajes = pagedList.getItemsRangeList();
			totalCount = pagedList.getTotalItems();
			success = true;
			return SUCCESS;
		} catch (ApplicationException ae) {
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		} catch (Exception e) {
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
	}
	
	/**
	 * Obtiene un conjunto de registros de mensajes de error y exporta el resultado en Formato PDF, Excel, CSV, etc.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */

	public String cmdExportarClick () throws Exception {
		try {

            contentType = Util.getContentType(formato);
            if (logger.isDebugEnabled()) {
                logger.debug( "content-type : " + contentType );
            }
			ExportView exportFormat = (ExportView)exportMediator.getView(formato);
			filename = "MantenimientoErrores." + exportFormat.getExtension();
			TableModelExport model = mensajesErrorManager.getModel(cdError, dsMensaje);
			inputStream = exportFormat.export(model);
		} catch (Exception e) {
			success = false;
		}
		return SUCCESS;
	}

	public String getCdError() {
		return cdError;
	}

	public void setCdError(String cdError) {
		this.cdError = cdError;
	}

	public String getDsMensaje() {
		return dsMensaje;
	}

	public void setDsMensaje(String dsMensaje) {
		this.dsMensaje = dsMensaje;
	}

	public List<MensajeErrorVO> getListaMensajes() {
		return listaMensajes;
	}

	public void setListaMensajes(List<MensajeErrorVO> listaMensajes) {
		this.listaMensajes = listaMensajes;
	}

	public void setMensajesErrorManager(MensajesErrorManager mensajesErrorManager) {
		this.mensajesErrorManager = mensajesErrorManager;
	}

	public void setExportMediator(ExportMediator exportMediator) {
		this.exportMediator = exportMediator;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}
