package mx.com.aon.catbo.web;

import java.util.ArrayList;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.CalendarioVO;
import mx.com.aon.catbo.model.ConfigurarCompraTiempoVO;
import mx.com.aon.catbo.model.TareaVO;
import mx.com.aon.catbo.service.ConfigurarCompraTiempoManager;


import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

//import com.opensymphony.xwork2.ActionSupport;


public class ConfigurarCompraTiempoAction extends ActionSupport{
	
	private static final long serialVersionUID = 19873215465454L;
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(NumeracionCasosAction.class);
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
		
	private transient ConfigurarCompraTiempoManager configurarCompraTiempoManager;
	
	
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans 
	 * con los valores de la consulta.
	 */
		
	private String tarea;
	private String tdesde;
	private String thasta;
	private String compra;
	private String codigoh;
	private String codigounidadh;


    private List<ConfigurarCompraTiempoVO> mConfigurarCompraTiempoVOList;
		
	private boolean success;
	
	
	public String cmdBorrarClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = configurarCompraTiempoManager.borrarCompraTiempo(tarea, codigoh);
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
        	     	
        	messageResult = configurarCompraTiempoManager.guardarCompraTiempo(tarea,codigoh,tdesde,thasta,codigounidadh,compra);
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
	
	
	
    
    public String cmdObtieneConfigCompraTiempo()throws Exception
    {
    	ConfigurarCompraTiempoVO configurarCompraTiempoVO; 
    try
    {   
    	mConfigurarCompraTiempoVOList=new ArrayList<ConfigurarCompraTiempoVO>();
    	configurarCompraTiempoVO = configurarCompraTiempoManager.obtieneConfigCompraTiempo(tarea, codigoh);
    	mConfigurarCompraTiempoVOList.add(configurarCompraTiempoVO);
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

	
	public ConfigurarCompraTiempoManager getConfigurarCompraTiempoManager() {
		return configurarCompraTiempoManager;
	}

	

	public String getTarea() {
		return tarea;
	}

	public void setTarea(String tarea) {
		this.tarea = tarea;
	}

	public String getTdesde() {
		return tdesde;
	}

	public void setTdesde(String tdesde) {
		this.tdesde = tdesde;
	}

	public String getThasta() {
		return thasta;
	}

	public void setThasta(String thasta) {
		this.thasta = thasta;
	}

	public String getCompra() {
		return compra;
	}

	public void setCompra(String compra) {
		this.compra = compra;
	}


	public String getCodigoh() {
		return codigoh;
	}


	public void setCodigoh(String codigoh) {
		this.codigoh = codigoh;
	}


	public String getCodigounidadh() {
		return codigounidadh;
	}


	public void setCodigounidadh(String codigounidadh) {
		this.codigounidadh = codigounidadh;
	}


	public List<ConfigurarCompraTiempoVO> getMConfigurarCompraTiempoVOList() {
		return mConfigurarCompraTiempoVOList;
	}


	public void setMConfigurarCompraTiempoVOList(
			List<ConfigurarCompraTiempoVO> configurarCompraTiempoVOList) {
		mConfigurarCompraTiempoVOList = configurarCompraTiempoVOList;
	}


	public void setConfigurarCompraTiempoManager(
			ConfigurarCompraTiempoManager configurarCompraTiempoManager) {
		this.configurarCompraTiempoManager = configurarCompraTiempoManager;
	}


	

}