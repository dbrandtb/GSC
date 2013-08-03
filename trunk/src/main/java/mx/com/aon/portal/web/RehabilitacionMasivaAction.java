package mx.com.aon.portal.web;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.ConsultaPolizasCanceladasVO;
import mx.com.aon.portal.service.RehabilitacionMasivaManager;
import mx.com.aon.portal.service.PolizasManager;


public class RehabilitacionMasivaAction extends AbstractListAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7245919190304833568L;

	/**
	 * Logger para monitoreo de la clase
	 */
	private static Logger logger = Logger.getLogger(RehabilitacionMasivaAction.class);

	/**
	 * Manager con implementacion de endpoints para la conexion a la BD
	 */
	@SuppressWarnings("unused")
	private transient RehabilitacionMasivaManager rehabilitacionMasivaManager;
	private transient PolizasManager polizasManager;
	
	private boolean success;
	
	private String cdUniEco;
	private String cdRamo;
	private String estado;
	private String nmPoliza;
	private String feEfecto;
	private String feVencim;
	private String feCancel;
	private String feReInst;
	private String cdRazon;
	private String cdPerson;
	private String cdMoneda;
	private String nmCancel;
	private String comentarios;
	private String nmSuplem;

	/**
	 * Atributo con el(los) dato(s) que identificará(n) el objeto Ext.data.Record que se seleccionó
	 * desde una pantalla de origen. Una vez que se desee regresar, se utilizará este atributo. 
	 */
	private String idRegresar;
	
	private List<ConsultaPolizasCanceladasVO> rehabGrillaList;
	
	public String obtienePantallaRehabilitacionMasiva(){
		logger.debug("***** Entrando a obtienePantallaRehabilitacionMasiva");
    	if ( StringUtils.isBlank( idRegresar ) )
    		super.updateParametrosRegresar();
		
		return INPUT;
	}
	
	public String cmdRehabilitarPoliza() throws ApplicationException {
		try {
			String _result = polizasManager.rehabilitarPolizaMasiva(rehabGrillaList);
			success = true;
			addActionMessage(_result);
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

	//SETTERS y GETTERS

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNmPoliza() {
		return nmPoliza;
	}

	public void setNmPoliza(String nmPoliza) {
		this.nmPoliza = nmPoliza;
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

	public String getNmSuplem() {
		return nmSuplem;
	}

	public void setNmSuplem(String nmSuplem) {
		this.nmSuplem = nmSuplem;
	}

	public void setRehabilitacionMasivaManager(
			RehabilitacionMasivaManager rehabilitacionMasivaManager) {
		this.rehabilitacionMasivaManager = rehabilitacionMasivaManager;
	}

	public void setPolizasManager(PolizasManager polizasManager) {
		this.polizasManager = polizasManager;
	}

	public List<ConsultaPolizasCanceladasVO> getRehabGrillaList() {
		return rehabGrillaList;
	}

	public void setRehabGrillaList(List<ConsultaPolizasCanceladasVO> rehabGrillaList) {
		this.rehabGrillaList = rehabGrillaList;
	}

	public String getIdRegresar() {
		return idRegresar;
	}

	public void setIdRegresar(String idRegresar) {
		this.idRegresar = idRegresar;
	}
	
	
}
