package mx.com.aon.catbo.web;

import java.util.ArrayList;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.service.NotificacionesManager;
import mx.com.aon.catbo.model.BackBoneResultVO;
import mx.com.aon.catbo.model.NotificacionVO;

import org.apache.log4j.Logger;
import com.opensymphony.xwork2.ActionSupport;

/**
 *   Action que atiende las peticiones de abm que vienen de la pantalla Ayuda de Coberturas.
 * 
 */
public class NotificacionesAction extends ActionSupport {

	private static final long serialVersionUID = 19873215465454L;
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(NotificacionesAction.class);
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
	private String cdNotificacionNew;
	private String dsNotificacion;
	private String cdRegion;
	private String dsRegion;
	private String dsProcesoBO;
	private String dsEdoCaso;
	
	private String dsMensaje;
	private String cdFormatoOrden;
	private String cdMetEnv;
	private List<NotificacionVO> csoGrillaListAtr;
	private List<NotificacionVO> listaNotificacionVO;
	
	private String cdProceso;


	private boolean success;

		
	/**
	 * Metodo que elimina un registro de tabla Notificaciones.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarNtfcClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = notificacionesManager.borrarNotificaciones(cdNotificacion);
            success = true;
            addActionMessage(messageResult);
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
	 * Metodo que elimina un registro de tabla Notificaciones por Proceso.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarNtfcPrcsClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = notificacionesManager.borrarNotificaciones(cdNotificacion);
            success = true;
            addActionMessage(messageResult);
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
	 * Metodo que obtiene una notificacion.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdGetNtfcClick()throws Exception
	{
		try
		{
			mEstructuraList=new ArrayList<NotificacionVO>();
			NotificacionVO notificacionVO=notificacionesManager.getNotificaciones(cdNotificacion);
			
			if(notificacionVO!=null && notificacionVO.getDsMensaje()!=null)
			this.dsMensaje=notificacionVO.getDsMensaje();
			logger.debug("HHHH el bar"+ this.dsMensaje);
			
			
			mEstructuraList.add(notificacionVO);
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
	* Metodo que atiende una peticion de insertar o actualizar una notificacion
	* 
	* @return success
	* 
	* @throws Exception
	*/
	public String cmdGuardarNtfcClick()throws Exception{
		String messageResult = "";
	    try
	    {          	
	    	NotificacionVO notificacionVO = new NotificacionVO();
	    	notificacionVO.setCdNotificacion(cdNotificacion);
	    	notificacionVO.setDsNotificacion(dsNotificacion);
	    	notificacionVO.setDsMensaje(dsMensaje);
	    	notificacionVO.setCdRegion(cdRegion);
	    	notificacionVO.setCdMetEnv(cdMetEnv);
	    	
	    	BackBoneResultVO backBoneResultVO = new BackBoneResultVO();
        	backBoneResultVO = notificacionesManager.guardarNotificaciones(notificacionVO);
	    	cdNotificacionNew = backBoneResultVO.getOutParam();
	    	//addActionMessage(messageResult);
	    	addActionMessage(backBoneResultVO.getMsgText());
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
	* Metodo que atiende una peticion de insertar una notificacion por proceso
	* 
	* @return success
	* 
	* @throws Exception
	*/
	public String cmdGuardarNtfcPrcsClick()throws Exception{

		String messageResult = "";
	    try
	    {          	
	    /*	NotificacionVO notificacionVO = new NotificacionVO();
	    	
	    	for (int i=0; i<csoGrillaListAtr.size(); i++) {
	    		
	    		NotificacionVO notificacionVO_grid=csoGrillaListAtr.get(i);	    		
	    		notificacionVO.setCdNotificacion(notificacionVO_grid.getCdNotificacion());
		    	notificacionVO.setCdProceso(notificacionVO_grid.getCdProceso());*/
		    	
		    	messageResult = notificacionesManager.guardarNotificacionesProc(cdNotificacion, listaNotificacionVO);
	    //}
	    	addActionMessage(messageResult);
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


	
	public List<NotificacionVO> getMEstructuraList() {return mEstructuraList;}
	
	public void setMEstructuraList(List<NotificacionVO> estructuraList) {
		mEstructuraList = estructuraList;
	}


    public boolean getSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCdNotificacion() {
		return cdNotificacion;
	}

	public void setCdNotificacion(String cdNotificacion) {
		this.cdNotificacion = cdNotificacion;
	}

	public String getCdProceso() {
		return cdProceso;
	}

	public void setCdProceso(String cdProceso) {
		this.cdProceso = cdProceso;
	}


	public String getDsNotificacion() {
		return dsNotificacion;
	}

	public void setDsNotificacion(String dsNotificacion) {
		this.dsNotificacion = dsNotificacion;
	}

	public String getDsMensaje() {
		return dsMensaje;
	}

	public void setDsMensaje(String dsMensaje) {
		this.dsMensaje = dsMensaje;
	}

	public String getCdFormatoOrden() {
		return cdFormatoOrden;
	}

	public void setCdFormatoOrden(String cdFormatoOrden) {
		this.cdFormatoOrden = cdFormatoOrden;
	}

	public String getCdMetEnv() {
		return cdMetEnv;
	}

	public void setCdMetEnv(String cdMetEnv) {
		this.cdMetEnv = cdMetEnv;
	}

	/*public NotificacionesManager obtenNotificacionesManager() {
		return notificacionesManager;
	}*/

	public void setNotificacionesManager(NotificacionesManager notificacionesManager) {
		this.notificacionesManager = notificacionesManager;
	}

	public List<NotificacionVO> getCsoGrillaListAtr() {
		return csoGrillaListAtr;
	}


	public void setCsoGrillaListAtr(List<NotificacionVO> csoGrillaListAtr) {
		this.csoGrillaListAtr = csoGrillaListAtr;
	}


	public List<NotificacionVO> getListaNotificacionVO() {
		return listaNotificacionVO;
	}

	public void setListaNotificacionVO(List<NotificacionVO> listaNotificacionVO) {
		this.listaNotificacionVO = listaNotificacionVO;
	}

	public String getCdNotificacionNew() {
		return cdNotificacionNew;
	}

	public void setCdNotificacionNew(String cdNotificacionNew) {
		this.cdNotificacionNew = cdNotificacionNew;
	}

	public String getDsRegion() {
		return dsRegion;
	}

	public void setDsRegion(String dsRegion) {
		this.dsRegion = dsRegion;
	}

	public String getDsProcesoBO() {
		return dsProcesoBO;
	}

	public void setDsProcesoBO(String dsProcesoBO) {
		this.dsProcesoBO = dsProcesoBO;
	}

	public String getDsEdoCaso() {
		return dsEdoCaso;
	}

	public void setDsEdoCaso(String dsEdoCaso) {
		this.dsEdoCaso = dsEdoCaso;
	}

	public String getCdRegion() {
		return cdRegion;
	}

	public void setCdRegion(String cdRegion) {
		this.cdRegion = cdRegion;
	}

}