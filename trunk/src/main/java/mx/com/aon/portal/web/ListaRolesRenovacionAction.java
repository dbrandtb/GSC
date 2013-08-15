package mx.com.aon.portal.web;

import mx.com.aon.portal.model.RolRenovacionVO;
//import mx.com.aon.portal.model.ConfigurarAccionRenovacionVO;
import mx.com.aon.portal.service.RolesRenovacionManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.util.Util;


//import java.util.ArrayList;
import java.util.List;
import java.io.InputStream;

import org.apache.log4j.Logger;


/**
 *   Action que atiende la peticion de informacion para la consulta de datos de 
 *   la tabla con mecanismo de paginacion de la pantalla Roles de Renovacion.
 *   
 *   @extends AbstractListAction
 * 
 */

@SuppressWarnings("serial")
public class ListaRolesRenovacionAction extends AbstractListAction{




	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	private static Logger logger = Logger.getLogger(ListaRolesRenovacionAction.class);

	/**
	 * Manager con implementacion de Endpoint para la consulta a BD 
	 * Este objeto no es serializable
	 */
    private transient RolesRenovacionManager rolesRenovacionManager;


	private List<RolRenovacionVO> listaRoles; 

	private String cdRenova;

	/**
	 * Atributo inyectado por spring el cual direcciona a travez del tipo de formato para generar 
	 * el archivo a ser exportado
	 */
	private ExportMediator exportMediator;
	
    /**
	 * Atributo agregado por struts que contiene el tipo de formato a ser exportado
	 */
	private String formato;
	
	/**
	 * Atributo de respuesta interpretado por strust con el nombre del archivo generado 
	 */
	private String filename;
	
	/**
	 * Atributo de respuesta con el flujo de datos para regresar el archivo generado.
	 */
	private InputStream inputStream;
    
	/**
	 * Metodo que realiza la busqueda de datos de roles de renovacion
	 * en base a codigo de renovacion 
	 * 
	 * @param cdRenova
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String cmdObtenerRoles() throws Exception{
		try{

            PagedList pagedList = this.rolesRenovacionManager.obtenerRolesRenovacion(cdRenova, start, limit);
            listaRoles = pagedList.getItemsRangeList();
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
	 * Metodo que busca un conjunto de peridos de gracia  
	 * y exporta el resultado en Formato PDF, Excel, etc.
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
			
			filename = "RolesRenovacion." + exportFormat.getExtension();

			TableModelExport model = rolesRenovacionManager.getModel(cdRenova);

			inputStream = exportFormat.export(model);
			
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}

		return SUCCESS;

	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		ListaRolesRenovacionAction.logger = logger;
	}

	public RolesRenovacionManager obtenRolesRenovacionManager() {
		return rolesRenovacionManager;
	}

	public void setRolesRenovacionManager(
			RolesRenovacionManager rolesRenovacionManager) {
		this.rolesRenovacionManager = rolesRenovacionManager;
	}

	public List<RolRenovacionVO> getListaRoles() {
		return listaRoles;
	}

	public void setListaRoles(List<RolRenovacionVO> listaRoles) {
		this.listaRoles = listaRoles;
	}

	public String getCdRenova() {
		return cdRenova;
	}

	public void setCdRenova(String cdRenova) {
		this.cdRenova = cdRenova;
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

	public ExportMediator getExportMediator() {
		return exportMediator;
	}

	public void setExportMediator(ExportMediator exportMediator) {
		this.exportMediator = exportMediator;
	}

}
