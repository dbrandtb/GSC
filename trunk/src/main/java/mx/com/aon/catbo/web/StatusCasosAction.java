package mx.com.aon.catbo.web;

import java.util.ArrayList;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.StatusCasoVO;
import mx.com.aon.catbo.model.StatusProcesoVO;
import mx.com.aon.catbo.service.StatusCasosManager;



import org.apache.log4j.Logger;
import com.opensymphony.xwork2.ActionSupport;

/**
 *   Action que atiende las peticiones de abm que vienen de la pantalla Ayuda de Coberturas.
 * 
 */
@SuppressWarnings("serial")
public class StatusCasosAction extends ActionSupport {

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(StatusCasosAction.class);
	
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient StatusCasosManager statusCasosManager;
	
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo AyudaCoberturasVO
	 * con los valores de la consulta.
	 */
	private List<StatusCasoVO> mEstructuraList;
	private String cdStatus;
	private String dsStatus; 
	private String indAviso;
	private List<StatusCasoVO> listaStatusCasoVO;
	
	private List<StatusProcesoVO> csoGrillaListStatusProceso;

	private boolean success;

		
	/**
	 * Metodo que Elimina un Estatus de Caso
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarClick() throws Exception{
		String messageResult = "";
		try{
			messageResult =  statusCasosManager.borrarStatusCasos(cdStatus);
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
	 * Metodo que obtiene un Estatus de Caso
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdGetClick()throws Exception
	{
		try
		{
			mEstructuraList=new ArrayList<StatusCasoVO>();
			StatusCasoVO statusCasoVO=statusCasosManager.getStatusCasos(cdStatus);
			mEstructuraList.add(statusCasoVO);
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
	 * Metodo que actualiza o genera un Estatus de Caso
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 *
	 */
	public String cmdGuardarClick()throws Exception
	{
		String messageResult = "";
        try
        {
        	StatusCasoVO statusCasoVO= new StatusCasoVO();
        	statusCasoVO.setCdStatus(cdStatus);
        	statusCasoVO.setDsStatus(dsStatus);
        	statusCasoVO.setIndAviso(indAviso);
        	messageResult = statusCasosManager.guardarStatusCasos(statusCasoVO);
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
	* Metodo que atiende una peticion de insertar administracion status de casos
	* 
	* @return success
	* 
	* @throws Exception
	*/
	public String cmdGuardarStatusCasosTareaClick()throws Exception{

		String messageResult = "";
	    try
	    {    
	    	StatusProcesoVO statusProcesoVO = new StatusProcesoVO();
	    	
	    	for (int i=0; i<csoGrillaListStatusProceso.size(); i++) {
	    		
	    		StatusProcesoVO statusProcesoVO_grid=csoGrillaListStatusProceso.get(i);
	    		statusProcesoVO.setCdStatus(statusProcesoVO_grid.getCdStatus());
	    		statusProcesoVO.setCdProceso(statusProcesoVO_grid.getCdProceso());
    	
		    	messageResult = statusCasosManager.guardarStatusCasosTareas(statusProcesoVO);
	    	}
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
	
	/**
	* Metodo que atiende una peticion de borrar administracion status de casos
	* 
	* @return success
	* 
	* @throws Exception
	*/
	public String cmdBorrarStatusCasosTareaClick()throws Exception{

		String messageResult = "";
	    try
	    {    
	    	StatusProcesoVO statusProcesoVO = new StatusProcesoVO();
	    	
	    	for (int i=0; i<csoGrillaListStatusProceso.size(); i++) {
	    		
	    		StatusProcesoVO statusProcesoVO_grid=csoGrillaListStatusProceso.get(i);
	    		statusProcesoVO.setCdStatus(statusProcesoVO_grid.getCdStatus());
	    		statusProcesoVO.setCdProceso(statusProcesoVO_grid.getCdProceso());
    	
		    	messageResult = statusCasosManager.borrarStatusCasosTareas(statusProcesoVO);
	    	}
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
	

	public List<StatusCasoVO> getMEstructuraList() {
		return mEstructuraList;
	}

	public void setMEstructuraList(List<StatusCasoVO> estructuraList) {
		mEstructuraList = estructuraList;
	}

	public boolean getSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCdStatus() {
		return cdStatus;
	}

	public void setCdStatus(String cdStatus) {
		this.cdStatus = cdStatus;
	}

	public String getDsStatus() {
		return dsStatus;
	}

	public void setDsStatus(String dsStatus) {
		this.dsStatus = dsStatus;
	}

	public String getIndAviso() {
		return indAviso;
	}

	public void setIndAviso(String indAviso) {
		this.indAviso = indAviso;
	}

	public void setStatusCasosManager(StatusCasosManager statusCasosManager) {
		this.statusCasosManager = statusCasosManager;
	}

	public List<StatusCasoVO> getListaStatusCasoVO() {
		return listaStatusCasoVO;
	}

	public void setListaStatusCasoVO(List<StatusCasoVO> listaStatusCasoVO) {
		this.listaStatusCasoVO = listaStatusCasoVO;
	}

	public List<StatusProcesoVO> getCsoGrillaListStatusProceso() {
		return csoGrillaListStatusProceso;
	}

	public void setCsoGrillaListStatusProceso(
			List<StatusProcesoVO> csoGrillaListStatusProceso) {
		this.csoGrillaListStatusProceso = csoGrillaListStatusProceso;
	}

}