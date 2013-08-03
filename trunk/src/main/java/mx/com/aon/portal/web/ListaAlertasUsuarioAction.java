package mx.com.aon.portal.web;

import java.util.List;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.AlertaUsuarioVO;
import mx.com.aon.portal.service.AlertasUsuarioManager;
import mx.com.aon.portal.service.PagedList;

/**
 *   Action que atiende la peticion de informacion para la consulta de datos de 
 *   la tabla con mecanismo de paginacion de la pantalla Alertas de Usuario.
 *   
 *   @extends AbstractListAction
 * 
 */
@SuppressWarnings("serial")
public class ListaAlertasUsuarioAction extends AbstractListAction {
	
	private transient AlertasUsuarioManager alertasUsuarioManager;
	private List<AlertaUsuarioVO> mAlertasUsuario;
	private boolean success;
	private String dsUsuario;
	private String dsNombre;
	private	String dsProceso;
	private String dsTipRam;
	private String dsUniEco;
	private String dsRol;
	private String codigoConfAlerta;
	
	/**
	 * Metodo que busca y obtiene un conjunto de registros con datos de alertas de usuarios.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String buscarAlertasUsuario () throws Exception {
		try {
			PagedList pagedList = alertasUsuarioManager.buscarAlertasUsuario(dsUsuario, dsNombre, dsProceso, dsTipRam, dsUniEco, dsRol, start, limit);
			mAlertasUsuario = pagedList.getItemsRangeList();
			totalCount = pagedList.getTotalItems();
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
	
	public String irConsultarAlertas () {
		return "buscarAlertas";
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getDsUsuario() {
		return dsUsuario;
	}
	public void setDsUsuario(String dsUsuario) {
		this.dsUsuario = dsUsuario;
	}
	public String getDsNombre() {
		return dsNombre;
	}
	public void setDsNombre(String dsNombre) {
		this.dsNombre = dsNombre;
	}
	public String getDsProceso() {
		return dsProceso;
	}
	public void setDsProceso(String dsProceso) {
		this.dsProceso = dsProceso;
	}
	public String getDsTipRam() {
		return dsTipRam;
	}
	public void setDsTipRam(String dsTipRam) {
		this.dsTipRam = dsTipRam;
	}
	public String getDsUniEco() {
		return dsUniEco;
	}
	public void setDsUniEco(String dsUniEco) {
		this.dsUniEco = dsUniEco;
	}
	public String getDsRol() {
		return dsRol;
	}
	public void setDsRol(String dsRol) {
		this.dsRol = dsRol;
	}
	public void setAlertasUsuarioManager(AlertasUsuarioManager alertasUsuarioManager) {
		this.alertasUsuarioManager = alertasUsuarioManager;
	}

	public List<AlertaUsuarioVO> getMAlertasUsuario() {
		return mAlertasUsuario;
	}

	public void setMAlertasUsuario(List<AlertaUsuarioVO> alertasUsuario) {
		mAlertasUsuario = alertasUsuario;
	}

	public String getCodigoConfAlerta() {
		return codigoConfAlerta;
	}

	public void setCodigoConfAlerta(String codigoConfAlerta) {
		this.codigoConfAlerta = codigoConfAlerta;
	}
	

}
