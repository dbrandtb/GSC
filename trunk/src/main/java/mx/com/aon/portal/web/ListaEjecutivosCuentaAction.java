package mx.com.aon.portal.web;


import mx.com.aon.portal.service.EjecutivosCuentaManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.util.Util;



import org.apache.log4j.Logger;

import java.io.InputStream;

import java.util.List;

/**
 *   Action que atiende la peticion de informacion para la consulta de datos de 
 *   la tabla con mecanismo de paginacion de la pantalla Ejecutivos por cuenta.
 *   
 *   @extends AbstractListAction
 * 
 */

public class ListaEjecutivosCuentaAction extends AbstractListAction{

	private static final long serialVersionUID = -2161929117098084652L;


	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaEjecutivosCuentaAction.class);



	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */
	private transient EjecutivosCuentaManager ejecutivosCuentaManager;


	private List<EjecutivosCuentaManager> mEjecutivosCuentaList;

	    private String dsNombre;
	 	private String nomAgente;
	    private String desGrupo;
	    
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
	 * Ejecuta la busqueda para el llenado de la grilla de ejecutivos por cuenta
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarClick() throws Exception{
		try{
			PagedList pagedList = this.ejecutivosCuentaManager.buscarEjecutivosCuenta(dsNombre, nomAgente, desGrupo, start, limit);
			mEjecutivosCuentaList = pagedList.getItemsRangeList();
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
			filename = "Ejecutivos por Cuenta." + exportFormat.getExtension();
			TableModelExport model =  ejecutivosCuentaManager.getModel(dsNombre, nomAgente, desGrupo);
			inputStream = exportFormat.export(model);
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;
	}

	public EjecutivosCuentaManager obtenEjecutivosCuentaManager() {
		return ejecutivosCuentaManager;
	}

	public void setEjecutivosCuentaManager(
			EjecutivosCuentaManager ejecutivosCuentaManager) {
		this.ejecutivosCuentaManager = ejecutivosCuentaManager;
	}

	public List<EjecutivosCuentaManager> getMEjecutivosCuentaList() {
		return mEjecutivosCuentaList;
	}

	public void setMEjecutivosCuentaList(
			List<EjecutivosCuentaManager> ejecutivosCuentaList) {
		mEjecutivosCuentaList = ejecutivosCuentaList;
	}

	public String getDsNombre() {
		return dsNombre;
	}

	public void setDsNombre(String dsNombre) {
		this.dsNombre = dsNombre;
	}

	public String getNomAgente() {
		return nomAgente;
	}

	public void setNomAgente(String nomAgente) {
		this.nomAgente = nomAgente;
	}

	public String getDesGrupo() {
		return desGrupo;
	}

	public void setDesGrupo(String desGrupo) {
		this.desGrupo = desGrupo;
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
