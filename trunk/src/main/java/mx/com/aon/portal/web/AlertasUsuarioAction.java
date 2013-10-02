package mx.com.aon.portal.web;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import mx.com.aon.portal.model.AlertaUsuarioVO;
import mx.com.aon.portal.service.AlertasUsuarioManager;
import mx.com.gseguros.exception.ApplicationException;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Action que atiende las peticiones de abm de la pantalla de Alertas de Usuarios.
 *
 */
@SuppressWarnings("serial")
public class AlertasUsuarioAction extends ActionSupport {
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(AlertasUsuarioAction.class);
	
	private transient AlertasUsuarioManager alertasUsuarioManager;
	private boolean success;
	private List<AlertaUsuarioVO> listaAlerta;
	private String codUsuario;
	private String codCliente;
	private String codAseguradora;
	private String codPoliza;
	private String codRecibo;
	private String codProceso;
	private String codTemporalidad;
	private String feUltimoEnvio;
	private String feSiguienteEnvio;
	private String feVencimiento;
	private String correo;
	private String mensaje;
	private String popUp;
	private String pantalla;
	private String codFrecuencia;
	private String codigoConfAlerta;
	private String cdIdUnico;
	private String cdIdConfAlerta;
	
	public String getCdIdConfAlerta() {
		return cdIdConfAlerta;
	}

	public void setCdIdConfAlerta(String cdIdConfAlerta) {
		this.cdIdConfAlerta = cdIdConfAlerta;
	}

	/**
	 * Metodo que inserta una nueva configuracion de alertas o actualiza una ya editada en pantalla.
	 *  
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdGuardarClick () throws Exception {
		String messageResult = "";
		try {
			String _popUp;
			String _pantalla;
			
			if (popUp == null || popUp.equals("")) {
				_popUp = "0";
			}else {
				_popUp = (popUp != null && !popUp.equals("") && popUp.equals("on"))?"1":"0";
			}

			if (pantalla == null || pantalla.equals("")) {
				_pantalla = "0";
			}else {
				_pantalla = (pantalla != null && !pantalla.equals("") && pantalla.equals("on"))?"1":"0";
			}
			messageResult = alertasUsuarioManager.guardarAlertaUsuario(cdIdUnico, codigoConfAlerta, codCliente, codUsuario, codAseguradora, codPoliza, codRecibo, codProceso, feUltimoEnvio, feSiguienteEnvio, feVencimiento, correo, mensaje, _popUp, _pantalla, codFrecuencia);
			success = true;
			addActionMessage(messageResult);
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
	
	/**
	 * Metodo que busca y obtiene una unica configuracion de alertas para mostrar en pantalla.
	 *  
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdGetAlerta () throws ApplicationException {
		try {
			listaAlerta = new ArrayList<AlertaUsuarioVO>();
			AlertaUsuarioVO alertaUsuarioVO = alertasUsuarioManager.getAlertaUsuario(cdIdUnico, codigoConfAlerta);
			listaAlerta.add(alertaUsuarioVO);
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
	
	/**
	 * Metodo que elimina una alerta seleccionada del grid de la pantalla
	 * Consulta de alertas.
	 * Se agrego esta funcionalidad el dia 10/09/2008 por pedido de oslen en el issue ACW-622
	 *
	 */
	@SuppressWarnings("unchecked")  
	public String cmdBorrarClick() throws Exception	{
		
		String messageResult = "";
		
		try
		{
			messageResult=alertasUsuarioManager.borrarAlerta(cdIdUnico, codigoConfAlerta);
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
	

	public boolean getSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getCodUsuario() {
		return codUsuario;
	}
	public void setCodUsuario(String codUsuario) {
		this.codUsuario = codUsuario;
	}
	public String getCodCliente() {
		return codCliente;
	}
	public void setCodCliente(String codCliente) {
		this.codCliente = codCliente;
	}
	public String getCodAseguradora() {
		return codAseguradora;
	}
	public void setCodAseguradora(String codAseguradora) {
		this.codAseguradora = codAseguradora;
	}
	public String getCodPoliza() {
		return codPoliza;
	}
	public void setCodPoliza(String codPoliza) {
		this.codPoliza = codPoliza;
	}
	public String getCodRecibo() {
		return codRecibo;
	}
	public void setCodRecibo(String codRecibo) {
		this.codRecibo = codRecibo;
	}
	public String getCodProceso() {
		return codProceso;
	}
	public void setCodProceso(String codProceso) {
		this.codProceso = codProceso;
	}
	public String getCodTemporalidad() {
		return codTemporalidad;
	}
	public void setCodTemporalidad(String codTemporalidad) {
		this.codTemporalidad = codTemporalidad;
	}
	public String getFeUltimoEnvio() {
		return feUltimoEnvio;
	}
	public void setFeUltimoEnvio(String feUltimoEnvio) {
		this.feUltimoEnvio = feUltimoEnvio;
	}
	public String getFeSiguienteEnvio() {
		return feSiguienteEnvio;
	}
	public void setFeSiguienteEnvio(String feSiguienteEnvio) {
		this.feSiguienteEnvio = feSiguienteEnvio;
	}
	public String getFeVencimiento() {
		return feVencimiento;
	}
	public void setFeVencimiento(String feVencimiento) {
		this.feVencimiento = feVencimiento;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getPopUp() {
		return popUp;
	}
	public void setPopUp(String popUp) {
		this.popUp = popUp;
	}
	public String getPantalla() {
		return pantalla;
	}
	public void setPantalla(String pantalla) {
		this.pantalla = pantalla;
	}
	public String getCodFrecuencia() {
		return codFrecuencia;
	}
	public void setCodFrecuencia(String codFrecuencia) {
		this.codFrecuencia = codFrecuencia;
	}
	public void setAlertasUsuarioManager(AlertasUsuarioManager alertasUsuarioManager) {
		this.alertasUsuarioManager = alertasUsuarioManager;
	}

	public String getCodigoConfAlerta() {
		return codigoConfAlerta;
	}

	public void setCodigoConfAlerta(String codigoConfAlerta) {
		this.codigoConfAlerta = codigoConfAlerta;
	}

	public List<AlertaUsuarioVO> getListaAlerta() {
		return listaAlerta;
	}

	public void setListaAlerta(List<AlertaUsuarioVO> listaAlerta) {
		this.listaAlerta = listaAlerta;
	}

	public String getCdIdUnico() {
		return cdIdUnico;
	}

	public void setCdIdUnico(String cdIdUnico) {
		this.cdIdUnico = cdIdUnico;
	}
}
