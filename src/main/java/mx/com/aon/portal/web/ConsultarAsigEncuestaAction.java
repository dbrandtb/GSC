
package mx.com.aon.portal.web;


import mx.com.aon.portal.model.AgrupacionPolizaVO;
import mx.com.aon.portal.model.ConfiguracionAlertasAutomaticoVO;
import mx.com.aon.portal.service.ConfiguracionAlertasAutomaticoManager;
import mx.com.aon.portal.service.ConfiguracionAlertasAutomaticoManager2;
import mx.com.aon.catbo.model.AsigEncuestaVO;
import mx.com.aon.catbo.service.ConsultarAsigEncuestaManager;
import mx.com.aon.core.ApplicationException;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

/**
 *   Action que atiende las peticiones de abm que vienen de la pantalla Configuracion de alertas automatico.
 * 
 */
@SuppressWarnings("serial")
public class ConsultarAsigEncuestaAction extends ActionSupport {

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ConsultarAsigEncuestaAction.class);
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient ConsultarAsigEncuestaManager consultarAsigEncuestaManager;
	
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo ReciboVO
	 * con los valores de la consulta.
	 */
	
	
	/*private transient ConsultarAsigEncuestaManager consultarAsigEncuestaManager;
	private List<ConsultarAsigEncuestaManager> mConsularAsignacionEncuestaList;*/
	@SuppressWarnings("unchecked")
	private List mConsularAsignacionEncuestaList;
	private String dsUnieco;
	private String dsRamo;
	private String cdEstado;
	private String nmPoliza;
	private String dsPerson;
	private String nmConfig;
	private String cdUnieco;
	private String cdRamo;
	private String estado;
	private String cdPerson;
	private String dsUsuari;
	private String cdUsuario;
	private String status;
	private String nombreCliente;
	private String nombreUsuario;
	
	
    private boolean success;

	/**
	 * Metodo que elimina un registro seleccionado de la grilla.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = consultarAsigEncuestaManager.borrarAsigEncuesta(nmConfig, cdUnieco, cdRamo, cdEstado);	
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

    
    
    public String cmdGuardarClick()throws Exception
    {
        try
        {
        	AsigEncuestaVO asigEncuestaVO = new AsigEncuestaVO();
        	
        	
        	asigEncuestaVO.setNmConfig(nmConfig);
        	asigEncuestaVO.setCdUnieco(cdUnieco);
        	asigEncuestaVO.setCdRamo(cdRamo);
        	asigEncuestaVO.setEstado(cdEstado);        	
        	asigEncuestaVO.setNmPoliza(nmPoliza);
        	
        	asigEncuestaVO.setCdPerson(cdPerson);
        	
        	asigEncuestaVO.setStatus(status);
        	asigEncuestaVO.setCdUsuario(cdUsuario);
        	
            String messageResult = consultarAsigEncuestaManager.guardarAsigEncuesta(asigEncuestaVO);
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
    
  /*  public String cmdAgregarClick()throws Exception
    {
		String messageResult = "";
       try
        {
    	   AsigEncuestaVO asigEncuestaVO = new AsigEncuestaVO();
    	   
    	  
    	   configuracionAlertasAutomaticoVO.setCdIdUnico(cdIdUnico);
    	 
           configuracionAlertasAutomaticoVO.setCdRol(cdRol);
    	 
           configuracionAlertasAutomaticoVO.setDsAlerta(dsAlerta);
           
           configuracionAlertasAutomaticoVO.setDsUsuario(dsUsuario);
         
        	
    	   messageResult = configuracionAlertasAutomaticoManager.agregarConfiguracionAlertasAutomatico(configuracionAlertasAutomaticoVO);
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
    }*/


	/**
	 * Metodo que actualiza una configuracion de alertas automatico modificados por el usuario.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 *
	 */
   
	/**
	 * Metodo que busca y obtiene un unico registro de configuracion de alertas automatico.
	 * 
	 * @return success 
	 * 
	 * @throws Exception
	 */
    
	/*@SuppressWarnings("unchecked")
	public String cmdGetClick()throws Exception
	{
		try
		{
			mConsularAsignacionEncuestaList = new ArrayList<AsigEncuestaVO>();
			AsigEncuestaVO asigEncuestaVO=consultarAsigEncuestaManager.getObtenerAsigEncuesta(nmConfig, nmPoliza, nombreCliente, nombreUsuario);
			mConsularAsignacionEncuestaList.add(asigEncuestaVO);
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
	
	@SuppressWarnings("unchecked")
	public List getMConsularAsignacionEncuestaList() {return mConsularAsignacionEncuestaList;}

	public boolean getSuccess() {return success;}
	public void setSuccess(boolean success) {this.success = success;}
	@SuppressWarnings("unchecked")
	public void setMConsularAsignacionEncuestaList(
			List consularAsignacionEncuestaList) {
		mConsularAsignacionEncuestaList = consularAsignacionEncuestaList;
	}
	
	
	public void setConsultarAsigEncuestaManager(
			ConsultarAsigEncuestaManager consultarAsigEncuestaManager) {
		this.consultarAsigEncuestaManager = consultarAsigEncuestaManager;
	}

	

	public String getDsUnieco() {
		return dsUnieco;
	}

	public void setDsUnieco(String dsUnieco) {
		this.dsUnieco = dsUnieco;
	}

	public String getDsRamo() {
		return dsRamo;
	}

	public void setDsRamo(String dsRamo) {
		this.dsRamo = dsRamo;
	}

	public String getCdEstado() {
		return cdEstado;
	}

	public void setCdEstado(String cdEstado) {
		this.cdEstado = cdEstado;
	}

	public String getNmPoliza() {
		return nmPoliza;
	}

	public void setNmPoliza(String nmPoliza) {
		this.nmPoliza = nmPoliza;
	}

	public String getDsPerson() {
		return dsPerson;
	}

	public void setDsPerson(String dsPerson) {
		this.dsPerson = dsPerson;
	}

	public String getDsUsuari() {
		return dsUsuari;
	}

	public void setDsUsuari(String dsUsuari) {
		this.dsUsuari = dsUsuari;
	}

	
	

	
	

}