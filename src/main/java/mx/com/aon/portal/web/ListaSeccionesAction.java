package mx.com.aon.portal.web;

import org.apache.log4j.Logger;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.SeccionesManager;
import mx.com.aon.portal.model.SeccionVO;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.util.Util;


import java.io.InputStream;
import java.util.List;

/**
 * Action que atiende las peticiones de la pantalla de secciones.
 * 
 */
@SuppressWarnings("serial")
public class ListaSeccionesAction extends AbstractListAction{


	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */

	private static Logger logger = Logger.getLogger(ListaConfigurarEstructuraAction.class);

	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */
	private transient SeccionesManager  seccionesManager;

	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo AgrupacionPolizaVO
	 * con los valores de la consulta.
	 */
	private List<SeccionVO> listaSecciones;

	private String seccion;

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
	 *  Obtiene un conjunto de secciones y los mustra en el grid de secciones.
	 *  
	 * @return success
	 *  
	 * @throws Exception
	 */	
    @SuppressWarnings("unchecked")
	public String cmdBuscarClick () {
		try {
    		PagedList pagedList = seccionesManager.buscarSecciones(seccion,start,limit);
			listaSecciones = pagedList.getItemsRangeList();
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
	 * Exporta el las seciones mostradas en el grid de secciones en Formato PDF, Excel, etc.
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
			
			filename = "Seccion." + exportFormat.getExtension();
			
			TableModelExport model = seccionesManager.getModel(seccion);
			
			inputStream = exportFormat.export(model);
			
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;
	}

    public void setSeccionesManager(SeccionesManager seccionesManager) {
        this.seccionesManager = seccionesManager;
    }
	  		
	public String getFormato() {return formato;}
	public void setFormato(String formato) {this.formato = formato;}

	public InputStream getInputStream() {return inputStream;}
	public void setInputStream(InputStream inputStream) {this.inputStream = inputStream;}

	public String getFilename() {return filename;}
	public void setFilename(String filename) {this.filename = filename;}

	public void setExportMediator(ExportMediator exportMediator) {this.exportMediator = exportMediator;}
    
    public List<SeccionVO> getListaSecciones() {
        return listaSecciones;
    }

    public void setListaPolizas(List<SeccionVO> listaSecciones) {
        this.listaSecciones = listaSecciones;
    }

	public String getSeccion() {
		return seccion;
	}

	public void setSeccion(String cdSeccion) {
		this.seccion = cdSeccion;
	}
}