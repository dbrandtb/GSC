package mx.com.aon.catbo.web;

import java.util.List;

import mx.com.aon.core.ApplicationException;

import mx.com.aon.catbo.model.StatusCasoVO;
import mx.com.aon.catbo.model.TareaVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.catbo.service.StatusCasosManager;
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
public class ListaStatusCasosAction extends AbstractListAction{

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaStatusCasosAction.class);

	
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient StatusCasosManager statusCasosManager;
	
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo AyudaCoberturasVO
	 * con los valores de la consulta.
	 */
	private List<StatusCasoVO> mEstructuraList;
	private List<TareaVO> mEstructuraListTrsStsCs;
	private String dsStatus; 
	private String cdStatus; 
	
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
	 * Metodo que realiza la busqueda de Estatus de Caso en base a
	 * en base a descripcion del status
	 * 
	 * @param dsStatus
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarClick() throws Exception{
		try{
            PagedList pagedList = this.statusCasosManager.buscarStatusCasos(dsStatus, start, limit);
            mEstructuraList = pagedList.getItemsRangeList();
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
	 * Metodo que realiza la busqueda de Tareas deEstatus de Casos
	 * sin parametros de entrada
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarStatusCasosTareasClick() throws Exception{
		try{
            List pagedList = this.statusCasosManager.getStatusCasosTareas(cdStatus);
            mEstructuraListTrsStsCs = pagedList;
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
	 * Metodo que realiza la busqueda de Tareas deEstatus de Casos
	 * sin parametros de entrada
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarStatusCasosTareasProcesosClick() throws Exception{
		try{
            List pagedList = statusCasosManager.getStatusCasosTareasProcesos(cdStatus);
            mEstructuraListTrsStsCs = pagedList;
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
	 * Metodo que busca un conjunto de Estatus de Caso 
	 * y exporta el resultado en Formato PDF, Excel, etc.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdExportarSttCsClick() throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );
		}
		
		try {
			  contentType = Util.getContentType(formato);
	            if (logger.isDebugEnabled()) {
	                logger.debug( "content-type : " + contentType );
	            }

			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			filename = "StatusDeCasos." + exportFormat.getExtension();
			TableModelExport model =  statusCasosManager.getModel(dsStatus);
			inputStream = exportFormat.export(model);
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;
	}

	public List<StatusCasoVO> getMEstructuraList() {
		return mEstructuraList;
	}

	public void setMEstructuraList(List<StatusCasoVO> estructuraList) {
		mEstructuraList = estructuraList;
	}

	public StatusCasosManager getStatusCasosManager() {
		return statusCasosManager;
	}

	public void setStatusCasosManager(StatusCasosManager statusCasosManager) {
		this.statusCasosManager = statusCasosManager;
	}

	public String getDsStatus() {
		return dsStatus;
	}

	public void setDsStatus(String dsStatus) {
		this.dsStatus = dsStatus;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<TareaVO> getMEstructuraListTrsStsCs() {
		return mEstructuraListTrsStsCs;
	}

	public void setMEstructuraListTrsStsCs(List<TareaVO> estructuraListTrsStsCs) {
		mEstructuraListTrsStsCs = estructuraListTrsStsCs;
	}

	public String getCdStatus() {
		return cdStatus;
	}

	public void setCdStatus(String cdStatus) {
		this.cdStatus = cdStatus;
	}

	public String getFormato() {return formato;}
	public void setFormato(String formato) {this.formato = formato;}

	public InputStream getInputStream() {return inputStream;}
	public void setInputStream(InputStream inputStream) {this.inputStream = inputStream;}

	public String getFilename() {return filename;}
	public void setFilename(String filename) {this.filename = filename;}

	public void setExportMediator(ExportMediator exportMediator) {this.exportMediator = exportMediator;}
	
	
}