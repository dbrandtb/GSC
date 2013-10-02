package mx.com.aon.portal.web;

import mx.com.aon.portal.service.AccesoEstructuraRolUsuarioManager;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

public class AccesoEstructuraRolUsuarioAction extends ActionSupport{

	private static final long serialVersionUID = -3458680789943432654L;

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(AccesoEstructuraRolUsuarioAction.class);

	private transient AccesoEstructuraRolUsuarioManager accesoEstructuraRolUsuarioManager;

	private String cdNivel;
	private String cdRol;
	private String cdUsuario;
	private String dsNombre;
	private String cdEstado;
	private String cdsisrol;
	
	private boolean success;

	public String cmdGuardarAcceso () throws ApplicationException {
		try {
			String msg = accesoEstructuraRolUsuarioManager.guardarAcceso(cdNivel, cdRol, cdUsuario, cdEstado);
			addActionMessage(msg);
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

	public String cmdBorrarAcceso () throws ApplicationException {
		try {
			String msg = accesoEstructuraRolUsuarioManager.borrarAcceso(cdNivel, cdUsuario,cdsisrol);
			addActionMessage(msg);
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

	public String getCdNivel() {
		return cdNivel;
	}

	public void setCdNivel(String cdNivel) {
		this.cdNivel = cdNivel;
	}

	public String getCdRol() {
		return cdRol;
	}

	public void setCdRol(String cdRol) {
		this.cdRol = cdRol;
	}

	public String getCdUsuario() {
		return cdUsuario;
	}

	public void setCdUsuario(String cdUsuario) {
		this.cdUsuario = cdUsuario;
	}

	public String getCdEstado() {
		return cdEstado;
	}

	public void setCdEstado(String cdEstado) {
		this.cdEstado = cdEstado;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setAccesoEstructuraRolUsuarioManager(
			AccesoEstructuraRolUsuarioManager accesoEstructuraRolUsuarioManager) {
		this.accesoEstructuraRolUsuarioManager = accesoEstructuraRolUsuarioManager;
	}

	public String getCdsisrol() {
		return cdsisrol;
	}

	public void setCdsisrol(String cdsisrol) {
		this.cdsisrol = cdsisrol;
	}

	public String getDsNombre() {
		return dsNombre;
	}

	public void setDsNombre(String dsNombre) {
		this.dsNombre = dsNombre;
	}
}
