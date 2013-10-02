package mx.com.aon.portal.web;

import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.List;

import mx.com.aon.portal.model.CancelacionManualPolizaIncisoVO;
import mx.com.aon.portal.model.RehabilitacionManual_PolizaVO;
import mx.com.aon.portal.service.CancelacionManualPolizasManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.PolizasManager;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

public class CancelacionManualPolizasAction extends AbstractListAction {
	private static Logger logger = Logger.getLogger(CancelacionManualPolizasAction.class);

	//TODO Implementar el manager asociado
	private transient CancelacionManualPolizasManager cancelacionManualPolizasManager;
    private transient PolizasManager polizasManager;

    //private boolean success;

	/**
	 * Parámetros recibidos desde el cliente
	 */
	private String cdUniEco;
	private String cdRamo;
	private String cdEstado;
	private String nmPoliza;
	private String feEfecto;
	private String feCancel;
	private String feVencim;
	private String cdRazon;
	/**
	 * Listas que contendran los datos a ser enviados a la pantalla
	 */
	private List<RehabilitacionManual_PolizaVO> listaDatosPoliza;
	private List<CancelacionManualPolizaIncisoVO> listaIncisos;

	public String cmdObtenerEncabezado () throws ApplicationException {
		try {
			listaDatosPoliza = new ArrayList<RehabilitacionManual_PolizaVO>();
			RehabilitacionManual_PolizaVO rehabilitacionManual_PolizaVO = cancelacionManualPolizasManager.obtenerEncabezado(cdUniEco, cdRamo, cdEstado, nmPoliza);
			listaDatosPoliza.add(rehabilitacionManual_PolizaVO);
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
	
	public String cmdObtenerDatosCancelacion () throws ApplicationException {
		//TODO llenar el mismo objeto RehabilitacionManual_PolizaVO de cmdObtenerEncabezado
		return SUCCESS;
	}

	public String cmdObtenerDatosIncisos () throws ApplicationException {
		try {
			PagedList pagedList = cancelacionManualPolizasManager.obtenerDatosIncisos(cdUniEco, cdRamo, cdEstado, nmPoliza, start, limit);
			listaIncisos = pagedList.getItemsRangeList();
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

	public String cmdGuardarClick () throws ApplicationException {
		try {
//            String msg = cancelacionManualPolizasManager.guardarCancelacion(listaDatosPoliza);
            String msg = polizasManager.guardarCancelacion(listaDatosPoliza);
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

	public String cmdCalcularPrima () throws ApplicationException {
		try {
            //String msg = cancelacionManualPolizasManager.calcularPrima(cdUniEco, cdRamo, nmPoliza, feEfecto, feCancel, feVencim, cdRazon);
            String msg = polizasManager.calcularPrima(cdUniEco, cdRamo, nmPoliza, feEfecto, feCancel, feVencim, cdRazon);
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
	
	public String cmdIrCancelacionManualPolizas () {
		return "cancelacionManualPolizas";
	}
	//SETTERS y GETTERS


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

	public List<RehabilitacionManual_PolizaVO> getListaDatosPoliza() {
		return listaDatosPoliza;
	}

	public void setListaDatosPoliza(
			List<RehabilitacionManual_PolizaVO> listaDatosPoliza) {
		this.listaDatosPoliza = listaDatosPoliza;
	}

	public List<CancelacionManualPolizaIncisoVO> getListaIncisos() {
		return listaIncisos;
	}

	public void setListaIncisos(List<CancelacionManualPolizaIncisoVO> listaIncisos) {
		this.listaIncisos = listaIncisos;
	}

	public void setCancelacionManualPolizasManager(
			CancelacionManualPolizasManager cancelacionManualPolizasManager) {
		this.cancelacionManualPolizasManager = cancelacionManualPolizasManager;
	}


    public void setPolizasManager(PolizasManager polizasManager) {
        this.polizasManager = polizasManager;
    }

    public String getFeEfecto() {
		return feEfecto;
	}

	public void setFeEfecto(String feEfecto) {
		this.feEfecto = feEfecto;
	}

	public String getFeCancel() {
		return feCancel;
	}

	public void setFeCancel(String feCancel) {
		this.feCancel = feCancel;
	}

	public String getFeVencim() {
		return feVencim;
	}

	public void setFeVencim(String feVencim) {
		this.feVencim = feVencim;
	}

	public String getCdRazon() {
		return cdRazon;
	}

	public void setCdRazon(String cdRazon) {
		this.cdRazon = cdRazon;
	}
}
