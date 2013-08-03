package mx.com.aon.catbo.web;

import java.util.ArrayList;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.ArchivosFaxesVO;
import mx.com.aon.catbo.model.CalendarioVO;
import mx.com.aon.catbo.model.CasoVO;
import mx.com.aon.catbo.model.ConfigurarCompraTiempoVO;
import mx.com.aon.catbo.model.UsuariosVO;
import mx.com.aon.catbo.model.TareaVO;
import mx.com.aon.catbo.service.AdministrarUsuariosModuloManager;
import mx.com.aon.catbo.service.ConfigurarCompraTiempoManager;
import mx.com.aon.portal.service.PagedList;


import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

//import com.opensymphony.xwork2.ActionSupport;


public class AdministrarUsuariosModuloAction extends ActionSupport{//ActionSupport
	
	private static final long serialVersionUID = 19873215465454L;
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(NumeracionCasosAction.class);
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
		
	private transient AdministrarUsuariosModuloManager administrarUsuariosModuloManager;
	
	
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans 
	 * con los valores de la consulta.
	 */
		
	private String pv_dsmodulo_i;
	private String pv_dsusuario_i;
	private String cdModulo;
	private String cdUsuario;
	
	private int start;
	private int limit;


    private List<UsuariosVO> mLisAdministrarUsuariosModulo;
		
	private boolean success;
	
	
	public String cmdborrarUsuarios() throws Exception{
		String messageResult = "";
		try{
			messageResult = administrarUsuariosModuloManager.borrarUsuarios(cdModulo, cdUsuario);
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
     * Metodo que inserta un nuevo dia calendario y descripcion
     * 
     * @return success
     * 
     *@throws Exception
     */
   public String cmdGuardarClick()throws Exception
    {
		String messageResult = "";
        try
        {         	
        	UsuariosVO usuariosVO= new  UsuariosVO();   	
        	messageResult = administrarUsuariosModuloManager.guardarUsuarios(cdModulo, cdUsuario);
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
	
	
	
   	/*@SuppressWarnings("unchecked")
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
    */
    

    /**
	 * Metodo que obtiene un Mecanismo de Tooltip selccionado en el grid.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	/*public String cmdGetCalendarioClick()throws Exception
	{
		try
		{
			mCalendarioList=new ArrayList<CalendarioVO>();
			CalendarioVO calendarioVO=calendarioManager.getDiaMes(codPais, yearCabecera, codMes, dia);
			mCalendarioList.add(calendarioVO);
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
*/
	

	
	
	public boolean isSuccess() {	
			return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}


	public static Logger getLogger() {
		return logger;
	}


	public static void setLogger(Logger logger) {
		AdministrarUsuariosModuloAction.logger = logger;
	}


/*	public AdministrarUsuariosModuloManager getAdministrarUsuariosModuloManager() {
		return administrarUsuariosModuloManager;
	}


	public void setAdministrarUsuariosModuloManager(
			AdministrarUsuariosModuloManager administrarUsuariosModuloManager) {
		this.administrarUsuariosModuloManager = administrarUsuariosModuloManager;
	}
*/

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


	public int getStart() {
		return start;
	}


	public void setStart(int start) {
		this.start = start;
	}


	public int getLimit() {
		return limit;
	}


	public void setLimit(int limit) {
		this.limit = limit;
	}


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}



	public List<UsuariosVO> getMLisAdministrarUsuariosModulo() {
		return mLisAdministrarUsuariosModulo;
	}



	public void setMLisAdministrarUsuariosModulo(
			List<UsuariosVO> lisAdministrarUsuariosModulo) {
		mLisAdministrarUsuariosModulo = lisAdministrarUsuariosModulo;
	}




	public String getCdModulo() {
		return cdModulo;
	}


	public void setCdModulo(String cdModulo) {
		this.cdModulo = cdModulo;
	}


	public String getCdUsuario() {
		return cdUsuario;
	}


	public void setCdUsuario(String cdUsuario) {
		this.cdUsuario = cdUsuario;
	}

	public void setAdministrarUsuariosModuloManager(
			AdministrarUsuariosModuloManager administrarUsuariosModuloManager) {
		this.administrarUsuariosModuloManager = administrarUsuariosModuloManager;
	}


	



}