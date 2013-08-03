package mx.com.aon.catbo.web;

import java.util.List;

import mx.com.aon.core.ApplicationException;

import mx.com.aon.catbo.model.TareaVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.catbo.service.TareasCatBoManager;
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
public class ListaTareasCatBoAction extends AbstractListAction{

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaTareasCatBoAction.class);

	
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient TareasCatBoManager tareasCatBoManager;
	
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo TareaVO
	 * con los valores de la consulta.
	 */
	private List<TareaVO> mEstructuraListTareas;
	private String dsProceso; 
	private String dsModulo; 
	private String cdPriord; 
	private String cdProceso;
	
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
            PagedList pagedList = this.tareasCatBoManager.buscarTareasCatBo(dsProceso, dsModulo, cdPriord, start, limit);
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
	}
	
	
	/**
	 * Metodo que busca un conjunto de Tareas de Cat Bo 
	 * y exporta el resultado en Formato PDF, Excel, etc.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdExportarTareaCatBo() throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );
		}
		
		try {
			contentType = Util.getContentType(formato);
            if (logger.isDebugEnabled()) {
                logger.debug( "content-type : " + contentType );
            }

			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			filename = "TareaCatBo." + exportFormat.getExtension();
			TableModelExport model =  tareasCatBoManager.getModel(dsProceso, dsModulo, cdPriord);
			inputStream = exportFormat.export(model);
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;
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

	public List<TareaVO> getMEstructuraListTareas() {
		return mEstructuraListTareas;
	}


	public void setMEstructuraListTareas(List<TareaVO> estructuraListTareas) {
		mEstructuraListTareas = estructuraListTareas;
	}


	public String getDsProceso() {
		return dsProceso;
	}

	public void setDsProceso(String dsProceso) {
		this.dsProceso = dsProceso;
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

	public void setTareasCatBoManager(TareasCatBoManager tareasCatBoManager) {
		this.tareasCatBoManager = tareasCatBoManager;
	}

	public TareasCatBoManager getTareasCatBoManager() {
		return tareasCatBoManager;
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
	
	
}