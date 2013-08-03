package mx.com.aon.portal.web;

import mx.com.aon.portal.model.RazonesCancelacionProductoVO;
import mx.com.aon.portal.service.RazonesCancelacionProductoManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.util.Util;



import org.apache.log4j.Logger;

import java.util.List;
import java.io.InputStream;


/**
 *   Action que atiende la peticion de informacion para la consulta de datos de 
 *   la tabla con mecanismo de paginacion de la pantalla Razones de Cancelacion.
 *   
 *   @extends AbstractListAction
 * 
 */
public class ListaRazonesCancelacionProductoAction extends AbstractListAction{

	private static final long serialVersionUID = 1934759475234543L;

	private static Logger logger = Logger.getLogger(ListaRazonesCancelacionProductoAction.class);

	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */
	private transient RazonesCancelacionProductoManager razonesCancelacionProductoManager;

	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo
	 * con los valores de la consulta.
	 */
	private List<RazonesCancelacionProductoVO> rcEstructuraList;

	private String dsRamo;
	private String dsRazon;
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
	 * Obtiene un conjunto de razones de cancelacion y los muestra en el grid de la Pantalla.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarClick() throws Exception{
		try{
            PagedList pagedList = razonesCancelacionProductoManager.obtenerRazonesCancelacionProducto(dsRamo, dsRazon, dsMetodo, start, limit);
            rcEstructuraList = pagedList.getItemsRangeList();
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
	 * @return success
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
			
			filename = "Razones de cancelacion." + exportFormat.getExtension();
			
			TableModelExport model = razonesCancelacionProductoManager.getModel(dsRamo, dsRazon, dsMetodo);
			
			inputStream = exportFormat.export(model);
			
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;

	}
	
	
	public List<RazonesCancelacionProductoVO> getRcEstructuraList() {
		return rcEstructuraList;
	}

	public void setRcEstructuraList(
			List<RazonesCancelacionProductoVO> rcEstructuraList) {
		this.rcEstructuraList = rcEstructuraList;
	}

	public String getDsRamo() {
		return dsRamo;
	}

	public void setDsRamo(String dsRamo) {
		this.dsRamo = dsRamo;
	}

	public String getDsRazon() {
		return dsRazon;
	}

	public void setDsRazon(String dsRazon) {
		this.dsRazon = dsRazon;
	}

	public String getDsMetodo() {
		return dsMetodo;
	}

	public void setDsMetodo(String dsMetodo) {
		this.dsMetodo = dsMetodo;
	}

	public void setRazonesCancelacionProductoManager(
			RazonesCancelacionProductoManager razonesCancelacionProductoManager) {
		this.razonesCancelacionProductoManager = razonesCancelacionProductoManager;
	}
	
	public String getFormato() {return formato;}
	public void setFormato(String formato) {this.formato = formato;}

	public InputStream getInputStream() {return inputStream;}
	public void setInputStream(InputStream inputStream) {this.inputStream = inputStream;}

	public String getFilename() {return filename;}
	public void setFilename(String filename) {this.filename = filename;}

	public void setExportMediator(ExportMediator exportMediator) {this.exportMediator = exportMediator;}
	
}