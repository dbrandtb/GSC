package mx.com.aon.portal.web;

import java.util.ArrayList;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.EjecutivoCuentaVO;
import mx.com.aon.portal.service.MantenimientoEjecutivosCuentaManager;
import mx.com.aon.portal.service.EjecutivoManager;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

public class MantenimientoEjecutivosCuentaAction extends ActionSupport {
	private static Logger logger = Logger.getLogger(MantenimientoEjecutivosCuentaAction.class);

	private transient MantenimientoEjecutivosCuentaManager mantenimientoEjecutivosCuentaManager;

	private transient EjecutivoManager ejecutivoManager;
	private boolean success;
	
	private List<EjecutivoCuentaVO> listaEjecutivo;
	
	private List<EjecutivoCuentaVO> datosAtributos;

	/**
	 * Parametros recibidos desde el cliente
	 */
	private String codEjecutivo;
	private String codPersona;
	private String fechaIni;
	private String fechaFin;
	private String estatus;
	private String cdAtribu;
	private String otValor;
	private String feInicio;

	public String getFeInicio() {
		return feInicio;
	}

	public void setFeInicio(String feInicio) {
		this.feInicio = feInicio;
	}

	public String cmdBorrarEjecutivo () throws ApplicationException {
		try {
			String msg = mantenimientoEjecutivosCuentaManager.borrarEjecutivo(codEjecutivo);
			addActionMessage(msg);
			success = true;
			return SUCCESS;
		} catch (ApplicationException ae) {
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		} catch (Exception e) {
			success = true;
			addActionError(e.getMessage());
			return SUCCESS;
		}
	}

	
	public String cmdGuardarEjecutivo () throws ApplicationException {
		try {
			String msg = ejecutivoManager.agregarEjecutivo(codEjecutivo, codPersona, feInicio, fechaFin, estatus);
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

	public String cmdObtenerEjecutivo () throws ApplicationException {
		try {
			listaEjecutivo = new ArrayList();
			EjecutivoCuentaVO ejecutivoCuentaVO = mantenimientoEjecutivosCuentaManager.obtenerEjecutivo(codEjecutivo);
			listaEjecutivo.add(ejecutivoCuentaVO);
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
	
	public String cmdGuardarAtributo () throws ApplicationException {
		String msg = "";
		try {
			if (datosAtributos.size() > 0) {
				for (int i=0; i<datosAtributos.size(); i++) {
					EjecutivoCuentaVO ejecutivoCuentaVO = datosAtributos.get(i);
					//msg = mantenimientoEjecutivosCuentaManager.guardarAtributos(ejecutivoCuentaVO.getCdAgente(), ejecutivoCuentaVO.getCdAtribu(), ejecutivoCuentaVO.getOtValor());
					/*implmentado con DAO para solucionar el problemas cuando se quiere guardar una ñ*/ 
					msg = ejecutivoManager.guardarAtributos(ejecutivoCuentaVO.getCdAgente(), ejecutivoCuentaVO.getCdAtribu(), ejecutivoCuentaVO.getOtValor()); 
				}
			}
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

	public void setMantenimientoEjecutivosCuentaManager(
			MantenimientoEjecutivosCuentaManager mantenimientoEjecutivosCuentaManager) {
		this.mantenimientoEjecutivosCuentaManager = mantenimientoEjecutivosCuentaManager;
	}

	public List<EjecutivoCuentaVO> getListaEjecutivo() {
		return listaEjecutivo;
	}

	public void setListaEjecutivo(List<EjecutivoCuentaVO> listaEjecutivo) {
		this.listaEjecutivo = listaEjecutivo;
	}

	public String getCodEjecutivo() {
		return codEjecutivo;
	}

	public void setCodEjecutivo(String codEjecutivo) {
		this.codEjecutivo = codEjecutivo;
	}

	public String getCodPersona() {
		return codPersona;
	}

	public void setCodPersona(String codPersona) {
		this.codPersona = codPersona;
	}

	public String getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(String fechaIni) {
		this.fechaIni = fechaIni;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getCdAtribu() {
		return cdAtribu;
	}

	public void setCdAtribu(String cdAtribu) {
		this.cdAtribu = cdAtribu;
	}

	public String getOtValor() {
		return otValor;
	}

	public void setOtValor(String otValor) {
		this.otValor = otValor;
	}

	public void setDatosAtributos(List<EjecutivoCuentaVO> datosAtributos) {
		this.datosAtributos = datosAtributos;
	}

	public List<EjecutivoCuentaVO> getDatosAtributos() {
		return datosAtributos;
	}

	public void setEjecutivoManager(EjecutivoManager ejecutivoManager) {
		this.ejecutivoManager = ejecutivoManager;
	}

}
