package mx.com.aon.portal.web;

import java.util.ArrayList;
import java.util.List;

import mx.com.aon.portal.model.ConfiguracionProcesoVO;
import mx.com.aon.portal.service.ConfiguracionProcesoManager;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;


public class ConfiguracionProcesoAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1393866267617565026L;

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ConfiguracionProcesoAction.class);

	private transient ConfiguracionProcesoManager configuracionProcesoManager;

	private boolean success;

	private List<ConfiguracionProcesoVO> listaProceso;

	/**
	 * Parametros recibidos desde el cliente
	 */
	private String cdUniEco;
	private String cdRamo;
	private String cdProceso;
	private String cdElemento;
	private String swEstado;

	public String cdObtenerProceso () throws ApplicationException {
		try {
			ConfiguracionProcesoVO configuracionProcesoVO = configuracionProcesoManager.obtenerProceso(cdUniEco, cdRamo, cdElemento, cdProceso);
			listaProceso = new ArrayList<ConfiguracionProcesoVO>();
			listaProceso.add(configuracionProcesoVO);
			success = true;
			return SUCCESS;
		} catch (ApplicationException ae) {
			addActionError(ae.getMessage());
			success = false;
			return SUCCESS;
		} catch (Exception e) {
			addActionError(e.getMessage());
			success = false;
			return SUCCESS;
		}
	}

	public String cmdActualizarProceso () throws ApplicationException {
		try {
			String msg = configuracionProcesoManager.actualizarProceso(cdUniEco, cdRamo, cdElemento, cdProceso, swEstado);
			addActionMessage(msg);
			success = true;
			return SUCCESS;
		} catch (ApplicationException ae) {
			addActionError(ae.getMessage());
			success = false;
			return SUCCESS;
		} catch (Exception e) {
			addActionError(e.getMessage());
			success = false;
			return SUCCESS;
		}
	}

	public String cmdGuardarProceso () throws ApplicationException {
		try {
			String msg = configuracionProcesoManager.guardarProceso(cdUniEco, cdRamo, cdElemento, cdProceso, swEstado);
			addActionMessage(msg);
			success = true;
			return SUCCESS;
		} catch (ApplicationException ae) {
			addActionError(ae.getMessage());
			success = false;
			return SUCCESS;
		} catch (Exception e) {
			addActionError(e.getMessage());
			success = false;
			return SUCCESS;
		}
	}

	public String cmdBorrarProceso () throws ApplicationException {
		try {
			String msg = configuracionProcesoManager.borrarProceso(cdUniEco, cdRamo, cdElemento, cdProceso);
			addActionMessage(msg);
			success = true;
			return SUCCESS;
		} catch (ApplicationException ae) {
			addActionError(ae.getMessage());
			success = false;
			return SUCCESS;
		} catch (Exception e) {
			addActionError(e.getMessage());
			success = false;
			return SUCCESS;
		}
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<ConfiguracionProcesoVO> getListaProceso() {
		return listaProceso;
	}

	public void setListaProceso(List<ConfiguracionProcesoVO> listaProceso) {
		this.listaProceso = listaProceso;
	}

	public String getCdUniEco() {
		return cdUniEco;
	}

	public void setCdUniEco(String cdUniEco) {
		this.cdUniEco = cdUniEco;
	}

	public String getCdRamo() {
		return cdRamo;
	}

	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}

	public String getCdProceso() {
		return cdProceso;
	}

	public void setCdProceso(String cdProceso) {
		this.cdProceso = cdProceso;
	}

	public String getCdElemento() {
		return cdElemento;
	}

	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}

	public String getSwEstado() {
		return swEstado;
	}

	public void setSwEstado(String swEstado) {
		this.swEstado = swEstado;
	}

	public void setConfiguracionProcesoManager(
			ConfiguracionProcesoManager configuracionProcesoManager) {
		this.configuracionProcesoManager = configuracionProcesoManager;
	}
}
