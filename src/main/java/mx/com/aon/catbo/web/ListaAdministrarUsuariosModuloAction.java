package mx.com.aon.catbo.web;


import java.io.InputStream;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.service.AdministrarUsuariosModuloManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.catbo.model.UsuariosVO;
import mx.com.aon.portal.util.Util;


import org.apache.log4j.Logger;

/**
 *   Action que atiende las peticiones de que vienen de la pantalla Notificaciones
 * 
 */
@SuppressWarnings("serial")
public class ListaAdministrarUsuariosModuloAction extends AbstractListAction{

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaAdministrarUsuariosModuloAction.class);

	
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient AdministrarUsuariosModuloManager administrarUsuariosModuloManager;
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo AyudaCoberturasVO
	 * con los valores de la consulta.
	 */
	private List<UsuariosVO> mLisAdministrarUsuariosModulo;
	
	private String pv_dsmodulo_i;
    private String pv_dsusuario_i;
    
	
	private boolean success;
    private String formato;
	
	/**
	 * Atributo de respuesta con el flujo de datos para regresar el archivo generado.
	 */
	private InputStream inputStream;
	private String filename;
	
	private ExportMediator exportMediator;
	/**
	 * Metodo que realiza la busqueda de notificaciones en base a
	 * en base a codigo notificacion, descripcion notificacion,
	 * descripcion mensaje, codigo formato, codigo metodo Envio 
	 * 
	 * @param cdNotificacion
	 * @param dsNotificacion
	 * @param cdMetenv
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String cmdbuscarUsuarios()throws Exception
	{
		try
		{
			
			PagedList pagedList = this.administrarUsuariosModuloManager.ObtenerUsuarios(pv_dsmodulo_i, pv_dsusuario_i, start, limit);
			mLisAdministrarUsuariosModulo = pagedList.getItemsRangeList();
            int totalCount = pagedList.getTotalItems();      
            
			
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
	
	public String cmdExportarFrmDocClick() throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug( "Formato: " + formato );
		}
		
		try {
			contentType = Util.getContentType(formato);
            if (logger.isDebugEnabled()) {
                logger.debug( "content-type : " + contentType );
            }

			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			filename = "UsuariosModulo." + exportFormat.getExtension();
			TableModelExport model =  this.administrarUsuariosModuloManager.getModel(pv_dsmodulo_i, pv_dsusuario_i);//matrizAsignacionManager.getModelMatrizAsignacion(pv_dsproceso_i, pv_dsformatoorden_i, pv_dselemen_i, pv_dsunieco_i, pv_dsramo_i);
			inputStream = exportFormat.export(model);
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;
	}
	
	
	public String cmdbuscarUsuariosAsigna()throws Exception
	{
		try
		{
			
			PagedList pagedList = this.administrarUsuariosModuloManager.obtenerUsuariosAsignar( pv_dsusuario_i, start, limit);
			mLisAdministrarUsuariosModulo = pagedList.getItemsRangeList();
            int totalCount = pagedList.getTotalItems();      
            
			
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
	



	public AdministrarUsuariosModuloManager getAdministrarUsuariosModuloManager() {
		return administrarUsuariosModuloManager;
	}

	public void setAdministrarUsuariosModuloManager(
			AdministrarUsuariosModuloManager administrarUsuariosModuloManager) {
		this.administrarUsuariosModuloManager = administrarUsuariosModuloManager;
	}

	public List<UsuariosVO> getMLisAdministrarUsuariosModulo() {
		return mLisAdministrarUsuariosModulo;
	}

	public void setMLisAdministrarUsuariosModulo(
			List<UsuariosVO> lisAdministrarUsuariosModulo) {
		mLisAdministrarUsuariosModulo = lisAdministrarUsuariosModulo;
	}

	public String getPv_dsmodulo_i() {
		return pv_dsmodulo_i;
	}

	public void setPv_dsmodulo_i(String pv_dsmodulo_i) {
		this.pv_dsmodulo_i = pv_dsmodulo_i;
	}

	public String getPv_dsusuario_i() {
		return pv_dsusuario_i;
	}

	public void setPv_dsusuario_i(String pv_dsusuario_i) {
		this.pv_dsusuario_i = pv_dsusuario_i;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
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
    

	
	/**
	 * Metodo que realiza la busqueda de notificaciones por proceso
	 * en base a codigo notificacion
	 * 
	 * @param cdNotificacion
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	
}