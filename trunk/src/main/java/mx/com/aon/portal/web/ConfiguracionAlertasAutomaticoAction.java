
package mx.com.aon.portal.web;


import mx.com.aon.portal.model.ConfiguracionAlertasAutomaticoVO;
import mx.com.aon.portal.service.ConfiguracionAlertasAutomaticoManager;
import mx.com.aon.portal.service.ConfiguracionAlertasAutomaticoManager2;
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
public class ConfiguracionAlertasAutomaticoAction extends ActionSupport {

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ConfiguracionAlertasAutomaticoAction.class);
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient ConfiguracionAlertasAutomaticoManager configuracionAlertasAutomaticoManager;
	private transient ConfiguracionAlertasAutomaticoManager2 configuracionAlertasAutomaticoManager2;
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo ReciboVO
	 * con los valores de la consulta.
	 */
	@SuppressWarnings("unchecked")
	private List mConfiguracionAlertasAutomaticoList;
	private String cdIdUnico;
	private String dsAlerta;
	private String dsUsuario;
	private String cdRol;
	private String cdNivCorp;
	private String cdCliente;
	private String cdTipRam;
	private String cdUniEco;
	private String cdPerson;
	private String dsRegion;
	private String cdProducto;
	private String cdProceso;
	private String cdTemporalidad;
	private String dsMensaje;
	private String fgMandaEmail;
	private String fgMandaPantalla;
	private String fgPermPantalla;
	private String nmDiasAnt;
	private String nmDuracion;
	private String nmFrecuencia;
	private String feInicio;
	private String dsTablaAlerta;
	private String dsColumnaAlerta;
	private String cdUsuario;
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
			messageResult = configuracionAlertasAutomaticoManager.borrarConfiguracionAlertasAutomatico(cdIdUnico);	
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
     * Metodo que inserta una nueva configuracion de alertas automatico.
     * 
     * @return success
     * 
     * @throws Exception
     *
     */
    public String cmdAgregarClick()throws Exception
    {
		String messageResult = "";
       try
        {
    	   ConfiguracionAlertasAutomaticoVO configuracionAlertasAutomaticoVO = new ConfiguracionAlertasAutomaticoVO();
    	   
    	   configuracionAlertasAutomaticoVO.setCdCliente(cdCliente);
    	   configuracionAlertasAutomaticoVO.setCdIdUnico(cdIdUnico);
    	   configuracionAlertasAutomaticoVO.setCdProceso(cdProceso);
           configuracionAlertasAutomaticoVO.setCdProducto(cdProducto);
           configuracionAlertasAutomaticoVO.setCdRol(cdRol);
    	   configuracionAlertasAutomaticoVO.setCdTemporalidad(cdTemporalidad);
    	   configuracionAlertasAutomaticoVO.setCdUniEco(cdUniEco);
    	   configuracionAlertasAutomaticoVO.setCdTipRam(cdTipRam);
           configuracionAlertasAutomaticoVO.setDsAlerta(dsAlerta);
           configuracionAlertasAutomaticoVO.setDsColumnaAlerta(dsColumnaAlerta);
    	   configuracionAlertasAutomaticoVO.setDsMensaje(dsMensaje);
    	   configuracionAlertasAutomaticoVO.setDsRegion(dsRegion);
    	   configuracionAlertasAutomaticoVO.setDsTablaAlerta(dsTablaAlerta);
           configuracionAlertasAutomaticoVO.setDsUsuario(dsUsuario);
           configuracionAlertasAutomaticoVO.setFeInicio(feInicio);
    	   configuracionAlertasAutomaticoVO.setFgMandaEmail(fgMandaEmail);
           configuracionAlertasAutomaticoVO.setFgMandaPantalla(fgMandaPantalla);
           configuracionAlertasAutomaticoVO.setFgPermPantalla(fgPermPantalla);
    	   configuracionAlertasAutomaticoVO.setNmDiasAnt(nmDiasAnt);
    	   configuracionAlertasAutomaticoVO.setNmDuracion(nmDuracion);
    	   configuracionAlertasAutomaticoVO.setNmFrecuencia(nmFrecuencia);
        	
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
    }


	/**
	 * Metodo que actualiza una configuracion de alertas automatico modificados por el usuario.
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
        	ConfiguracionAlertasAutomaticoVO configuracionAlertasAutomaticoVO = new ConfiguracionAlertasAutomaticoVO();
           configuracionAlertasAutomaticoVO.setCdCliente(cdPerson);
    	   configuracionAlertasAutomaticoVO.setCdIdUnico(cdIdUnico);
    	   configuracionAlertasAutomaticoVO.setCdProceso(cdProceso);
           configuracionAlertasAutomaticoVO.setCdProducto(cdProducto);
           configuracionAlertasAutomaticoVO.setCdRol(cdRol);
    	   configuracionAlertasAutomaticoVO.setCdTemporalidad(cdTemporalidad);
    	   configuracionAlertasAutomaticoVO.setCdUniEco(cdUniEco);
    	   configuracionAlertasAutomaticoVO.setCdTipRam(cdTipRam);
           configuracionAlertasAutomaticoVO.setDsAlerta(dsAlerta);
           configuracionAlertasAutomaticoVO.setDsColumnaAlerta(dsColumnaAlerta);
    	   configuracionAlertasAutomaticoVO.setDsMensaje(dsMensaje);
    	   configuracionAlertasAutomaticoVO.setDsRegion(dsRegion);
    	   configuracionAlertasAutomaticoVO.setDsTablaAlerta(dsTablaAlerta);
           configuracionAlertasAutomaticoVO.setDsUsuario(cdUsuario);
           configuracionAlertasAutomaticoVO.setFeInicio(feInicio);
           String _fgMandaEmail = (fgMandaEmail !=null && !fgMandaEmail.equals("") && fgMandaEmail.equals("on"))?"1":"0";
           if (fgMandaEmail == null) _fgMandaEmail = "0";
    	   configuracionAlertasAutomaticoVO.setFgMandaEmail(_fgMandaEmail);
    	   
    	   String _fgMandaPantalla = (fgMandaPantalla != null && !fgMandaPantalla.equals("") && fgMandaPantalla.equals("on")) ? "1":"0";
           if (fgMandaPantalla == null) _fgMandaPantalla = "0";
    	   configuracionAlertasAutomaticoVO.setFgMandaPantalla(_fgMandaPantalla);
    	   
    	   String _fgPermPantalla = (fgPermPantalla != null && !fgPermPantalla.equals("") & fgPermPantalla.equals("on"))?"1":"0";
    	   if (fgPermPantalla == null) _fgPermPantalla = "0";
           configuracionAlertasAutomaticoVO.setFgPermPantalla(_fgPermPantalla);
    	   configuracionAlertasAutomaticoVO.setNmDiasAnt(nmDiasAnt);
    	   configuracionAlertasAutomaticoVO.setNmDuracion(nmDuracion);
    	   configuracionAlertasAutomaticoVO.setNmFrecuencia(nmFrecuencia);
    	   // Creamos otro manager para poder trabajar con el esquema de DAO que reemplaza al .vm
    	   messageResult = configuracionAlertasAutomaticoManager2.guardarConfiguracionAlertasAutomatico(configuracionAlertasAutomaticoVO);
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
	 * Metodo que busca y obtiene un unico registro de configuracion de alertas automatico.
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
			mConfiguracionAlertasAutomaticoList = new ArrayList<ConfiguracionAlertasAutomaticoVO>();
			ConfiguracionAlertasAutomaticoVO configurarEstructuraVO=configuracionAlertasAutomaticoManager.getConfiguracionAlertasAutomatico(cdIdUnico);
			mConfiguracionAlertasAutomaticoList.add(configurarEstructuraVO);
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
	public List getMConfiguracionAlertasAutomaticoList() {return mConfiguracionAlertasAutomaticoList;}

	public boolean getSuccess() {return success;}
	public void setSuccess(boolean success) {this.success = success;}
	@SuppressWarnings("unchecked")
	public void setMConfiguracionAlertasAutomaticoList(
			List configuracionAlertasAutomaticoList) {
		mConfiguracionAlertasAutomaticoList = configuracionAlertasAutomaticoList;
	}
	public String getCdIdUnico() {
		return cdIdUnico;
	}
	public void setCdIdUnico(String cdIdUnico) {
		this.cdIdUnico = cdIdUnico;
	}
	public String getDsAlerta() {
		return dsAlerta;
	}
	public void setDsAlerta(String dsAlerta) {
		this.dsAlerta = dsAlerta;
	}
	public String getDsUsuario() {
		return dsUsuario;
	}
	public void setDsUsuario(String dsUsuario) {
		this.dsUsuario = dsUsuario;
	}
	public String getCdRol() {
		return cdRol;
	}
	public void setCdRol(String cdRol) {
		this.cdRol = cdRol;
	}
	public String getCdNivCorp() {
		return cdNivCorp;
	}
	public void setCdNivCorp(String cdNivCorp) {
		this.cdNivCorp = cdNivCorp;
	}
	public String getCdCliente() {
		return cdCliente;
	}
	public void setCdCliente(String cdCliente) {
		this.cdCliente = cdCliente;
	}
	public String getCdTipRam() {
		return cdTipRam;
	}
	public void setCdTipRam(String cdTipRam) {
		this.cdTipRam = cdTipRam;
	}
	public String getCdUniEco() {
		return cdUniEco;
	}
	public void setCdUniEco(String cdUniEco) {
		this.cdUniEco = cdUniEco;
	}
	public String getDsRegion() {
		return dsRegion;
	}
	public void setDsRegion(String dsRegion) {
		this.dsRegion = dsRegion;
	}
	public String getCdProducto() {
		return cdProducto;
	}
	public void setCdProducto(String cdProducto) {
		this.cdProducto = cdProducto;
	}
	public String getCdProceso() {
		return cdProceso;
	}
	public void setCdProceso(String cdProceso) {
		this.cdProceso = cdProceso;
	}
	public String getCdTemporalidad() {
		return cdTemporalidad;
	}
	public void setCdTemporalidad(String cdTemporalidad) {
		this.cdTemporalidad = cdTemporalidad;
	}
	public String getDsMensaje() {
		return dsMensaje;
	}
	public void setDsMensaje(String dsMensaje) {
		this.dsMensaje = dsMensaje;
	}
	public String getFgMandaEmail() {
		return fgMandaEmail;
	}
	public void setFgMandaEmail(String fgMandaEmail) {
		this.fgMandaEmail = fgMandaEmail;
	}
	public String getFgMandaPantalla() {
		return fgMandaPantalla;
	}
	public void setFgMandaPantalla(String fgMandaPantalla) {
		this.fgMandaPantalla = fgMandaPantalla;
	}
	public String getFgPermPantalla() {
		return fgPermPantalla;
	}
	public void setFgPermPantalla(String fgPermPantalla) {
		this.fgPermPantalla = fgPermPantalla;
	}
	public String getNmDiasAnt() {
		return nmDiasAnt;
	}
	public void setNmDiasAnt(String nmDiasAnt) {
		this.nmDiasAnt = nmDiasAnt;
	}
	public String getNmDuracion() {
		return nmDuracion;
	}
	public void setNmDuracion(String nmDuracion) {
		this.nmDuracion = nmDuracion;
	}
	public String getNmFrecuencia() {
		return nmFrecuencia;
	}
	public void setNmFrecuencia(String nmFrecuencia) {
		this.nmFrecuencia = nmFrecuencia;
	}
	public String getFeInicio() {
		return feInicio;
	}
	public void setFeInicio(String feInicio) {
		this.feInicio = feInicio;
	}
	public String getDsTablaAlerta() {
		return dsTablaAlerta;
	}
	public void setDsTablaAlerta(String dsTablaAlerta) {
		this.dsTablaAlerta = dsTablaAlerta;
	}
	public String getDsColumnaAlerta() {
		return dsColumnaAlerta;
	}
	public void setDsColumnaAlerta(String dsColumnaAlerta) {
		this.dsColumnaAlerta = dsColumnaAlerta;
	}
	public void setConfiguracionAlertasAutomaticoManager(
			ConfiguracionAlertasAutomaticoManager configuracionAlertasAutomaticoManager) {
		this.configuracionAlertasAutomaticoManager = configuracionAlertasAutomaticoManager;
	}

	public void setConfiguracionAlertasAutomaticoManager2(
			ConfiguracionAlertasAutomaticoManager2 configuracionAlertasAutomaticoManager2) {
		this.configuracionAlertasAutomaticoManager2 = configuracionAlertasAutomaticoManager2;
	}

	public String getCdPerson() {
		return cdPerson;
	}

	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}

	public String getCdUsuario() {
		return cdUsuario;
	}

	public void setCdUsuario(String cdUsuario) {
		this.cdUsuario = cdUsuario;
	}

	
	

}