package mx.com.aon.catbo.web;


import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.service.NotificacionesManager;
import mx.com.aon.portal.model.TreeViewVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.catbo.model.NotificacionVO;


import org.apache.log4j.Logger;

/**
 *   Action que atiende las peticiones de que vienen de la pantalla Notificaciones
 * 
 */
@SuppressWarnings("serial")
public class ListaNotificacionesAction extends AbstractListAction{

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaNotificacionesAction.class);

	
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient NotificacionesManager notificacionesManager;
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo AyudaCoberturasVO
	 * con los valores de la consulta.
	 */
	private List<NotificacionVO> mEstructuraList;
	private String cdNotificacion;
    private String dsNotificacion;
    private String dsFormatoOrden;
    private String dsMetEnv;
    private String dsProceso;
    private String dsRegion;
	private String dsEdoCaso;
	
	/**
	 * Almacena la lista de elementos de Variables necesarios en el TreeView de las variables de las notificaciones
	 */
	private List<TreeViewVO> elementosTreeView;
	
	private boolean success;

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
	public String cmdBuscarClick() throws Exception{
		try{
            PagedList pagedList = this.notificacionesManager.buscarNotificaciones(dsNotificacion, dsRegion, dsProceso, dsEdoCaso, dsMetEnv, start, limit);
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
	 * Metodo que realiza la busqueda de notificaciones por proceso
	 * en base a codigo notificacion
	 * 
	 * @param cdNotificacion
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdGetNtfcPrcsClick()throws Exception	{
		try{
            PagedList pagedList = this.notificacionesManager.getNotificacionesProceso(cdNotificacion, start, limit);
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
	 * Metodo que realiza la busqueda de notificaciones por proceso
	 * en base a codigo notificacion
	 * 
	 * @param cdNotificacion
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdGetPrcsNtfcClick()throws Exception	{
		try{
            PagedList pagedList = this.notificacionesManager.getProcesoNotificaciones(dsProceso, cdNotificacion, start, limit);
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
	 * Metodo que realiza la busqueda de estados
	 * en base a codigo notificacion
	 * 
	 * @param cdNotificacion
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdGetEdosNotifi()throws Exception	{
		try{
            PagedList pagedList = this.notificacionesManager.getEstadosNotificaciones(cdNotificacion, start, limit);
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
	 * Metodo que realiza la busqueda de estados
	 * en base a codigo notificacion
	 * 
	 * @param cdNotificacion
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdGetEdosCaso()throws Exception	{
		try{
            PagedList pagedList = this.notificacionesManager.getEstadosCaso(cdNotificacion, start, limit);
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
	
	public String cmdGetListaVariablesNotificaciones () {
		try {
			elementosTreeView = (List<TreeViewVO>)notificacionesManager.obtieneVariables();
			for (TreeViewVO treeViewVO : elementosTreeView) {
				treeViewVO.setType("location");
				treeViewVO.setCls("leaf");
				treeViewVO.setLeaf(true);
				treeViewVO.setId(treeViewVO.getText());
			}
			success = true;
			return SUCCESS;
		} catch (ApplicationException ae) {
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		} catch (Exception e) {
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
	}
	/*public NotificacionesManager getNotificacionesManager() {
		return notificacionesManager;
	}*/

	public void setNotificacionesManager(NotificacionesManager notificacionesManager) {
		this.notificacionesManager = notificacionesManager;
	}

	public List<NotificacionVO> getMEstructuraList() {
		return mEstructuraList;
	}

	public void setMEstructuraList(List<NotificacionVO> estructuraList) {
		mEstructuraList = estructuraList;
	}

	public String getCdNotificacion() {
		return cdNotificacion;
	}

	public void setCdNotificacion(String cdNotificacion) {
		this.cdNotificacion = cdNotificacion;
	}

	public String getDsNotificacion() {
		return dsNotificacion;
	}

	public void setDsNotificacion(String dsNotificacion) {
		this.dsNotificacion = dsNotificacion;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getDsFormatoOrden() {
		return dsFormatoOrden;
	}

	public void setDsFormatoOrden(String dsFormatoOrden) {
		this.dsFormatoOrden = dsFormatoOrden;
	}

	public String getDsMetEnv() {
		return dsMetEnv;
	}

	public void setDsMetEnv(String dsMetEnv) {
		this.dsMetEnv = dsMetEnv;
	}

	public String getDsProceso() {
		return dsProceso;
	}

	public void setDsProceso(String dsProceso) {
		this.dsProceso = dsProceso;
	}


	public String getDsRegion() {
		return dsRegion;
	}


	public void setDsRegion(String dsRegion) {
		this.dsRegion = dsRegion;
	}


	public String getDsEdoCaso() {
		return dsEdoCaso;
	}


	public void setDsEdoCaso(String dsEdoCaso) {
		this.dsEdoCaso = dsEdoCaso;
	}


	public List<TreeViewVO> getElementosTreeView() {
		return elementosTreeView;
	}


	public void setElementosTreeView(List<TreeViewVO> elementosTreeView) {
		this.elementosTreeView = elementosTreeView;
	}
	
}