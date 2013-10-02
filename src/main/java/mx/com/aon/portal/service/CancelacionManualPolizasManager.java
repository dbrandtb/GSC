package mx.com.aon.portal.service;

import java.util.List;

import mx.com.aon.portal.model.CancelacionManualPolizaIncisoVO;
import mx.com.aon.portal.model.RehabilitacionManual_PolizaVO;
import mx.com.gseguros.exception.ApplicationException;

public interface CancelacionManualPolizasManager {
	public RehabilitacionManual_PolizaVO obtenerEncabezado (String cdUniEco, String cdRamo, String cdEstado, String nmPoliza) throws ApplicationException;

	public RehabilitacionManual_PolizaVO obtenerDatosCancelacion () throws ApplicationException;

	public PagedList obtenerDatosIncisos (String cdUniEco, String cdRamo, String cdEstado, String nmPoliza, int start, int limit) throws ApplicationException;

	public String calcularPrima (String cdUniEco, String cdRamo, String nmPoliza, String  feEfecto, String feCancel, String feVencim, String cdRazon) throws ApplicationException;

	public String guardarCancelacion (List<RehabilitacionManual_PolizaVO> listaVO) throws ApplicationException;
}
