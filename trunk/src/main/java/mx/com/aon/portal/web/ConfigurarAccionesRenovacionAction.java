package mx.com.aon.portal.web;

import com.opensymphony.xwork2.ActionSupport;

import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;


import mx.com.aon.portal.model.ConfigurarAccionRenovacionVO;
import mx.com.aon.portal.model.ConsultaConfiguracionRenovacionVO;
import mx.com.aon.portal.service.ConfigurarAccionesRenovacionManager;
import mx.com.aon.core.ApplicationException;

/**
 *   Action que atiende las peticiones de que vienen de la pantalla Configurar Acciones de Renovacion
 * 
 */
@SuppressWarnings("serial")
public class ConfigurarAccionesRenovacionAction extends ActionSupport {

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */	
    @SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ConfigurarAccionesRenovacionAction.class);
    
    /**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 * Este objeto no es serializable
	 */
    private transient ConfigurarAccionesRenovacionManager configurarAccionesRenovacionManager;
    
    private List<ConsultaConfiguracionRenovacionVO> aotEstructuraList;
    private List<ConfigurarAccionRenovacionVO> accEstructuraList;

  
	private String cdRenova;
    private String cdRol;
    private String cdTitulo;
    private String cdAccion;
    private String cdCampo;
	private boolean success;


    /**
     * Metodo que atiende una peticion de obtener un encabezado de acciones de renovacion
	 * 
	 * @return success
	 * 
	 * @throws Exception
     */
	public String cmdGetClick()throws Exception
	{
		try
		{
			aotEstructuraList=new ArrayList<ConsultaConfiguracionRenovacionVO>();
			ConsultaConfiguracionRenovacionVO consultaConfiguracionRenovacionVO=configurarAccionesRenovacionManager.getEncabezadoConfigurarAccionesRenovacion(cdRenova);
			aotEstructuraList.add(consultaConfiguracionRenovacionVO);
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
     * Metodo que atiende una peticion de obtener acciones de renovacion
	 * 
	 * @return success
	 * 
	 * @throws Exception
     */
	public String cmdGetClickAccion()throws Exception
	{
		try
		{
			accEstructuraList=new ArrayList<ConfigurarAccionRenovacionVO>();
			ConfigurarAccionRenovacionVO configurarAccionRenovacionVO=configurarAccionesRenovacionManager.getConfigurarAccionesRenovacionAccion(cdRenova);
			accEstructuraList.add(configurarAccionRenovacionVO);
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
     * Metodo que atiende una peticion de insertar o actualizar acciones de renovacion
	 * 
	 * @return success
	 * 
	 * @throws Exception
     */
	public String cmdGuardarClick()throws Exception
	{
		String messageResult = "";
		try
		{
			ConfigurarAccionRenovacionVO configurarAccionRenovacionVO = new ConfigurarAccionRenovacionVO();
            
			configurarAccionRenovacionVO.setCdRenova(cdRenova);
			configurarAccionRenovacionVO.setCdRol(cdRol);
			configurarAccionRenovacionVO.setCdTitulo(cdTitulo);
			configurarAccionRenovacionVO.setCdAccion(cdAccion);   
			configurarAccionRenovacionVO.setCdCampo(cdCampo);

            messageResult = configurarAccionesRenovacionManager.guardarConfigurarAccionesRenovacion(configurarAccionRenovacionVO);
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
	 * Elimina un registro de la grilla previamente seleccionado
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = getConfigurarAccionesRenovacionManager().borrarConfigurarAccionesRenovacion(cdRenova, cdTitulo, cdRol, cdCampo);	
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
	
	public String cmdRegresarConsultaConfiguracionRenovacion(){
		return "consultaConfiguracionRenovacion";
	}
	
	
    public boolean getSuccess() {return success;}

	public void setSuccess(boolean success) {this.success = success;}

	public ConfigurarAccionesRenovacionManager getConfigurarAccionesRenovacionManager() {
		return configurarAccionesRenovacionManager;
	}


	public List<ConsultaConfiguracionRenovacionVO> getAotEstructuraList() {
		return aotEstructuraList;
	}

	public void setAotEstructuraList(
			List<ConsultaConfiguracionRenovacionVO> aotEstructuraList) {
		this.aotEstructuraList = aotEstructuraList;
	}

	public String getCdRenova() {
		return cdRenova;
	}

	public void setCdRenova(String cdRenova) {
		this.cdRenova = cdRenova;
	}

	public String getCdRol() {
		return cdRol;
	}

	public void setCdRol(String cdRol) {
		this.cdRol = cdRol;
	}

	public String getCdTitulo() {
		return cdTitulo;
	}

	public void setCdTitulo(String cdTitulo) {
		this.cdTitulo = cdTitulo;
	}

	public String getCdAccion() {
		return cdAccion;
	}

	public void setCdAccion(String cdAccion) {
		this.cdAccion = cdAccion;
	}

	public String getCdCampo() {
		return cdCampo;
	}

	public void setCdCampo(String cdCampo) {
		this.cdCampo = cdCampo;
	}

	public List<ConfigurarAccionRenovacionVO> getAccEstructuraList() {
		return accEstructuraList;
	}

	public void setAccEstructuraList(
			List<ConfigurarAccionRenovacionVO> accEstructuraList) {
		this.accEstructuraList = accEstructuraList;
	}

	public void setConfigurarAccionesRenovacionManager(
			ConfigurarAccionesRenovacionManager configurarAccionesRenovacionManager) {
		this.configurarAccionesRenovacionManager = configurarAccionesRenovacionManager;
	}

}
