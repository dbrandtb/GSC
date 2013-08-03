package mx.com.aon.catbo.web;

import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.FormatoDocumentoVO;
import mx.com.aon.catbo.service.FormatosDocumentosManager;
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
public class ListaAdministracionMantenimientoFaxAction extends AbstractListAction{

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaAdministracionMantenimientoFaxAction.class);

	
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	//private transient AdministracionMantenimientoDeFaxManager administracionMantenimientoDeFaxManager;
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo 
	 * con los valores de la consulta.
	 */
	//private List<AdministraFaxVO> mEstructuraList;
    
	
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
            //PagedList pagedList = this.administracionMantenimientoDeFaxManager.buscarAdministracionFaxes(dsNomFormato, start, limit);
            //mEstructuraList = pagedList.getItemsRangeList();
            //totalCount = pagedList.getTotalItems();                                                    
            success = true;
            return SUCCESS;
       /* }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;*/

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}
	
	
	/**
	 * Metodo que busca un conjunto de formatos documentos  
	 * y exporta el resultado en Formato PDF, Excel, etc.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdExportarAdmAtrMntFaxClick() throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );
		}
		
		try {
			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			filename = "Formatos Documentos." + exportFormat.getExtension();
		//	TableModelExport model =  administracionMantenimientoDeFaxManager.getModel(dsNomFormato);
		//	inputStream = exportFormat.export(model);
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;
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
	
	
}