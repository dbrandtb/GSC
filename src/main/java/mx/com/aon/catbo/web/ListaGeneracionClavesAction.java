package mx.com.aon.catbo.web;

import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.ClavesVO;
import mx.com.aon.catbo.service.GeneracionClavesManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;


import org.apache.log4j.Logger;

import java.io.InputStream;
/**
 *   Action que atiende las peticiones de que vienen de la pantalla Notificaciones
 * 
 */
@SuppressWarnings("serial")
public class ListaGeneracionClavesAction extends AbstractListAction{

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaGeneracionClavesAction.class);

	
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient GeneracionClavesManager generacionClavesManager;
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo
	 * con los valores de la consulta.
	 */
	private List<ClavesVO> mGeneracionClavesList;
	
	private String idGenerador;
    
	
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
	 * Metodo que realiza la busqueda de notificaciones en base a
	 * en base a codigo notificacion, descripcion notificacion,
	 * descripcion mensaje, codigo formato, codigo metodo Envio 
	 * 
	 * @param dsFormato
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarClick() throws Exception{
		try{
            PagedList pagedList = this.generacionClavesManager.obtieneValores(idGenerador,start,limit);
            this.mGeneracionClavesList = pagedList.getItemsRangeList();
            totalCount = pagedList.getTotalItems();                                                    
            success = true;
            return SUCCESS;
       }catch(ApplicationException e)
		{   success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }catch(Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        	}
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



	public ExportMediator getExportMediator() {
		return exportMediator;
	}




	public GeneracionClavesManager obtenGeneracionClavesManager() {
		return generacionClavesManager;
	}

	public void setGeneracionClavesManager(
			GeneracionClavesManager generacionClavesManager) {
		this.generacionClavesManager = generacionClavesManager;
	}
	
	public List<ClavesVO> getMGeneracionClavesList() {
		return mGeneracionClavesList;
	}
	
	public void setMGeneracionClavesList(List<ClavesVO> generacionClavesList) {
		mGeneracionClavesList = generacionClavesList;
	}
	
	public String getIdGenerador() {
		return idGenerador;
	}

	public void setIdGenerador(String idGenerador) {
		this.idGenerador = idGenerador;
	}
	
	
}