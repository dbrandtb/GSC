package mx.com.aon.portal.web;

import org.apache.log4j.Logger;

import mx.com.aon.portal.service.FuncionalidadManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.AgrupacionPolizasManager;
import mx.com.aon.portal.model.AgrupacionPolizaVO;
import mx.com.aon.portal.model.ConfigurarEstructuraVO;
import mx.com.aon.portal.model.FuncionalidadVO;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.util.Util;
import mx.com.gseguros.exception.ApplicationException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *   Action que atiende la peticion de informacion para la consulta de datos de 
 *   la tabla con mecanismo de paginacion de la pantalla Agrupacion de polizas.
 *   
 *   @extends AbstractListAction
 * 
 */
public class ListaFuncionalidadesPrivilegiosAction extends AbstractListAction{

	private static final long serialVersionUID = 1644454515546L;
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	private static Logger logger = Logger.getLogger(ListaConfigurarEstructuraAction.class);
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */
	private transient FuncionalidadManager  funcionalidadManager;
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo AgrupacionPolizaVO
	 * con los valores de la consulta.
	 */
	private List<FuncionalidadVO> listaFuncionalidades;
	
	private String pv_nivel_i;
	private String pv_sisrol_i;
	private String pv_usuario_i;
	private String pv_funciona_i;
	
	
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
	 * Metodo que realiza la busqueda de un conjunto de registros de agrupacion de polizas.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
    @SuppressWarnings("unchecked")
	public String cmdBuscarClick () {
		try {
    		PagedList pagedList = funcionalidadManager.buscarFuncionalidades(pv_nivel_i, pv_sisrol_i, pv_usuario_i, pv_funciona_i, start, limit);
    		listaFuncionalidades = pagedList.getItemsRangeList();
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

    
    
    @SuppressWarnings("unchecked")
	public String cmdGetFuncionalidadClick()throws Exception
	{
 		try
		{
 			PagedList pagedList = funcionalidadManager.getFuncionalidad(pv_nivel_i, pv_sisrol_i, pv_usuario_i, pv_funciona_i, start, limit);
 			listaFuncionalidades = pagedList.getItemsRangeList();
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
	 * Obtiene un conjunto de agrupacion de polizas y exporta el resultado en Formato PDF, Excel, CSV, etc.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdExportarFuncionalidadesClick() throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );
		}
		try {
			  contentType = Util.getContentType(formato);
	            if (logger.isDebugEnabled()) {
	                logger.debug( "content-type : " + contentType );
	            }
			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			
			filename = "Funcionalidades." + exportFormat.getExtension();
			
			TableModelExport model = funcionalidadManager.getModelFuncionalidades(pv_nivel_i, pv_sisrol_i, pv_usuario_i, pv_funciona_i);
			
			inputStream = exportFormat.export(model);
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;

	}
	
	
	public String getFormato() {return formato;}
	public void setFormato(String formato) {this.formato = formato;}

	public InputStream getInputStream() {return inputStream;}
	public void setInputStream(InputStream inputStream) {this.inputStream = inputStream;}

	public String getFilename() {return filename;}
	public void setFilename(String filename) {this.filename = filename;}

	public void setExportMediator(ExportMediator exportMediator) {this.exportMediator = exportMediator;}

	public FuncionalidadManager obtenFuncionalidadManager() {
		return funcionalidadManager;
	}

	public void setFuncionalidadManager(FuncionalidadManager funcionalidadManager) {
		this.funcionalidadManager = funcionalidadManager;
	}

	public List<FuncionalidadVO> getListaFuncionalidades() {
		return listaFuncionalidades;
	}

	public void setListaFuncionalidades(List<FuncionalidadVO> listaFuncionalidades) {
		this.listaFuncionalidades = listaFuncionalidades;
	}

	public String getPv_nivel_i() {
		return pv_nivel_i;
	}

	public void setPv_nivel_i(String pv_nivel_i) {
		this.pv_nivel_i = pv_nivel_i;
	}

	public String getPv_sisrol_i() {
		return pv_sisrol_i;
	}

	public void setPv_sisrol_i(String pv_sisrol_i) {
		this.pv_sisrol_i = pv_sisrol_i;
	}

	public String getPv_usuario_i() {
		return pv_usuario_i;
	}

	public void setPv_usuario_i(String pv_usuario_i) {
		this.pv_usuario_i = pv_usuario_i;
	}

	public String getPv_funciona_i() {
		return pv_funciona_i;
	}

	public void setPv_funciona_i(String pv_funciona_i) {
		this.pv_funciona_i = pv_funciona_i;
	}

	public ExportMediator getExportMediator() {
		return exportMediator;
	}
}
