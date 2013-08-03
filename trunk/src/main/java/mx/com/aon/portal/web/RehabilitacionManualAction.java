package mx.com.aon.portal.web;

import java.util.ArrayList;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.RehabilitacionManual_PolizaVO;
import mx.com.aon.portal.model.RehabilitacionManual_RequisitosVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.PolizasManager;
import mx.com.aon.portal.service.RehabilitacionManualManager;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class RehabilitacionManualAction extends ActionSupport {
	/**
	 * Logger para monitoreo de la clase
	 */
	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(RehabilitacionManualAction.class);
	
	/**
	 * Manager con implementacion de endpoints
	 */
	private transient RehabilitacionManualManager rehabilitacionManualManager;

    private transient PolizasManager polizasManager;

    private List<RehabilitacionManual_PolizaVO> listaPoliza;
	
	private List<RehabilitacionManual_RequisitosVO> listaRequisitos;

	private boolean success;

	private String dsAsegurado;
	private String dsAseguradora;
	private String nmPoliza;
	private String estado;
	private String dsRamo;
	private String nmSuplem;
	private String nmSituac;
	private String cdUniEco;
	private String cdUniage;

	private String feEfecto;
	private String feVencim; 
	private String feCancel;
	private String feReInst;
	private String cdRazon;
	private String cdPerson;
	private String cdMoneda;
	private String nmCancel;
	private String comentarios;
	private String cdRamo;
	private String feReHab;
	
	
	private String asegurado;
	private String inciso;
	private String dsRazon;
	private String dsUnieco;
	private String dssuPle;
	
	private String nmsuplem;
	private String nmPoliex;

	private int start;
	private int limit;
	private int totalCount;
	

	public String getInciso() {
		return inciso;
	}

	public void setInciso(String inciso) {
		this.inciso = inciso;
	}

	public String getAsegurado() {
		return asegurado;
	}

	public void setAsegurado(String asegurado) {
		this.asegurado = asegurado;
	}

	/**
	 * Obtiene datos de encabezado de la Poliza a Rehabilitar
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public String cmdObtenerPoliza () throws ApplicationException {
		RehabilitacionManual_PolizaVO temp = new RehabilitacionManual_PolizaVO();
		try {
			RehabilitacionManual_PolizaVO rehabilitacionManual_PolizaVO = rehabilitacionManualManager.obtenerInformacionPoliza(cdUniEco, cdRamo, estado, nmPoliza, nmSituac);
			/*temp = cmdObtenerDatosCancelacion();
			rehabilitacionManual_PolizaVO.setNmSuplem(temp.getNmSuplem());
			rehabilitacionManual_PolizaVO.setCdRazonCancelacion(temp.getCdRazonCancelacion());
			rehabilitacionManual_PolizaVO.setDsRazonCancelacion(temp.getDsRazonCancelacion());
			rehabilitacionManual_PolizaVO.setFechaCancelacion(temp.getFechaCancelacion());
			rehabilitacionManual_PolizaVO.setComentariosCancelacion(temp.getComentariosCancelacion());
			*/listaPoliza = new ArrayList();
			listaPoliza.add(rehabilitacionManual_PolizaVO);
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
	 * Obtiene Datos de Cancelacion de la Poliza a Rehabilitar
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public String cmdObtenerDatosCancelacion () throws ApplicationException {
		try {
			RehabilitacionManual_PolizaVO rehabilitacionManual_PolizaVO = rehabilitacionManualManager.obtenerDatosCancelacion(dsAsegurado, dsAseguradora, nmPoliza, estado, nmSuplem, nmSituac);
			listaPoliza = new ArrayList();
			listaPoliza.add(rehabilitacionManual_PolizaVO);
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
	 * Obtiene los requisitos de rehabilitacion
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public String cmdObtenerRequisitos () throws ApplicationException {
		try {
			PagedList pagedList = rehabilitacionManualManager.obtenerRequisitos(cdUniEco, nmPoliza, estado, nmSuplem, nmSituac, start, limit);
			listaRequisitos = pagedList.getItemsRangeList();
			totalCount = pagedList.getTotalItems();
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
	
	/**
	 * Rehabilita la Poliza seleccionada
	 *  
	 * @return
	 * @throws ApplicationException
	 */
	public String cmdRehabilitarPoliza () throws ApplicationException {
		try {
//            String msg = rehabilitacionManualManager.rehabilitarPoliza(cdUniEco, cdRamo, estado, nmPoliza, feEfecto, feVencim, feCancel, feReInst, cdRazon, cdPerson, cdMoneda, nmCancel, comentarios, nmSuplem);
            String msg = polizasManager.rehabilitarPoliza(cdUniEco, cdRamo, estado, nmPoliza, feEfecto, feVencim, feCancel, feReInst, cdRazon, cdPerson, cdMoneda, nmCancel, comentarios, nmsuplem);

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
	
	public String cmdValidarRecibosPagados () throws ApplicationException{
		try {
			String msg = rehabilitacionManualManager.validarRecibosPagados(cdUniEco, cdRamo, nmPoliza, estado, nmSuplem, feReHab);
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
	public String irRehabilitacionManual () {
		return "irRehabilitacionManual";
	}
	
	/*public String irPolizasCanceladas () {
		return "polizasCanceladas";
	}*/
	public String irConfiguracionRenovacion(){
		return "consultaConfiguracionRenovacion";
	}
	
	public List<RehabilitacionManual_PolizaVO> getListaPoliza() {
		return listaPoliza;
	}

	public void setListaPoliza(List<RehabilitacionManual_PolizaVO> listaPoliza) {
		this.listaPoliza = listaPoliza;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getDsAsegurado() {
		return dsAsegurado;
	}

	public void setDsAsegurado(String dsAsegurado) {
		this.dsAsegurado = dsAsegurado;
	}

	public String getDsAseguradora() {
		return dsAseguradora;
	}

	public void setDsAseguradora(String dsAseguradora) {
		this.dsAseguradora = dsAseguradora;
	}

	public String getNmPoliza() {
		return nmPoliza;
	}

	public void setNmPoliza(String nmPoliza) {
		this.nmPoliza = nmPoliza;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNmSuplem() {
		return nmSuplem;
	}

	public void setNmSuplem(String nmSuplem) {
		this.nmSuplem = nmSuplem;
	}

	public String getNmSituac() {
		return nmSituac;
	}

	public void setNmSituac(String nmSituac) {
		this.nmSituac = nmSituac;
	}

	public void setRehabilitacionManualManager(
			RehabilitacionManualManager rehabilitacionManualManager) {
		this.rehabilitacionManualManager = rehabilitacionManualManager;
	}


    public void setPolizasManager(PolizasManager polizasManager) {
        this.polizasManager = polizasManager;
    }

    public String getCdUniEco() {
		return cdUniEco;
	}

	public void setCdUniEco(String cdUniEco) {
		this.cdUniEco = cdUniEco;
	}

	public List<RehabilitacionManual_RequisitosVO> getListaRequisitos() {
		return listaRequisitos;
	}

	public void setListaRequisitos(
			List<RehabilitacionManual_RequisitosVO> listaRequisitos) {
		this.listaRequisitos = listaRequisitos;
	}

	public String getFeEfecto() {
		return feEfecto;
	}

	public void setFeEfecto(String feEfecto) {
		this.feEfecto = feEfecto;
	}

	public String getFeVencim() {
		return feVencim;
	}

	public void setFeVencim(String feVencim) {
		this.feVencim = feVencim;
	}

	public String getFeCancel() {
		return feCancel;
	}

	public void setFeCancel(String feCancel) {
		this.feCancel = feCancel;
	}

	public String getFeReInst() {
		return feReInst;
	}

	public void setFeReInst(String feReInst) {
		this.feReInst = feReInst;
	}

	public String getCdRazon() {
		return cdRazon;
	}

	public void setCdRazon(String cdRazon) {
		this.cdRazon = cdRazon;
	}

	public String getCdPerson() {
		return cdPerson;
	}

	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}

	public String getCdMoneda() {
		return cdMoneda;
	}

	public void setCdMoneda(String cdMoneda) {
		this.cdMoneda = cdMoneda;
	}

	public String getNmCancel() {
		return nmCancel;
	}

	public void setNmCancel(String nmCancel) {
		this.nmCancel = nmCancel;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public String getCdRamo() {
		return cdRamo;
	}

	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}

	public String getFeReHab() {
		return feReHab;
	}

	public void setFeReHab(String feReHab) {
		this.feReHab = feReHab;
	}

	public String getDsRamo() {
		return dsRamo;
	}

	public void setDsRamo(String dsRamo) {
		this.dsRamo = dsRamo;
	}

	public String getDsRazon() {
		return dsRazon;
	}

	public void setDsRazon(String dsRazon) {
		this.dsRazon = dsRazon;
	}

	public String getDsUnieco() {
		return dsUnieco;
	}

	public void setDsUnieco(String dsUnieco) {
		this.dsUnieco = dsUnieco;
	}


	public String getDssuPle() {
		return dssuPle;
	}

	public void setDssuPle(String dssuPle) {
		this.dssuPle = dssuPle;
	}

	public String getNmsuplem() {
		return nmsuplem;
	}

	public void setNmsuplem(String nmsuplem) {
		this.nmsuplem = nmsuplem;
	}

	public String getCdUniage() {
		return cdUniage;
	}

	public void setCdUniage(String cdUniage) {
		this.cdUniage = cdUniage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getNmPoliex() {
		return nmPoliex;
	}

	public void setNmPoliex(String nmPoliex) {
		this.nmPoliex = nmPoliex;
	}
	
}
