package mx.com.aon.portal.service.impl;

import java.util.HashMap;

import mx.com.aon.portal.model.RehabilitacionManual_PolizaVO;
import mx.com.aon.portal.model.RehabilitacionManual_RequisitosVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.RehabilitacionManualManager;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;

public class RehabilitacionManualManagerImpl extends AbstractManager implements
		RehabilitacionManualManager {

	/**
	 * Obtiene informacion de la Poliza.
	 * Usa el sp PKG_CANCELA.P_OBTIENE_POLIZA_CANCELA
	 * 
	 * @return Objeto de tipo Poliza
	 */
	@SuppressWarnings("unchecked")
	public RehabilitacionManual_PolizaVO obtenerInformacionPoliza(String cdUniEco, String cdRamo, String estado, String nmPoliza,
			String nmSituac)
			throws ApplicationException {

		HashMap map = new HashMap();
		map.put("cdUniEco", cdUniEco);
		map.put("cdRamo", cdRamo);
		map.put("estado", estado);
		map.put("nmPoliza", nmPoliza);
		map.put("nmSituac", nmSituac);

		return (RehabilitacionManual_PolizaVO)getBackBoneInvoke(map, "REHABILITACION_MANUAL_OBTENER_POLIZA");
	}

	/**
	 * Obtiene Datos de Cancelacion de la Poliza.
	 * Usa el sp PKG_CANCELA.P_OBTIENE_POLIZA_CANCELA
	 * 
	 * @return Objeto de tipo Poliza
	 */
	@SuppressWarnings("unchecked")
	public RehabilitacionManual_PolizaVO obtenerDatosCancelacion (String dsAsegurado, String dsAseguradora, String nmPoliza,
			String estado, String nmSuplem, String nmSituac) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("dsAsegurado", dsAsegurado);
		map.put("dsAseguradora", dsAseguradora);
		map.put("nmPoliza", nmPoliza);
		map.put("estado", estado);
		map.put("nmSuplem", nmSuplem);
		map.put("nmSituac", nmSituac);

		return (RehabilitacionManual_PolizaVO)getBackBoneInvoke(map, "REHABILITACION_MANUAL_OBTENER_DATOS_CANCELACION");
	}

	/**
	 * Obtiene los Requisitos para rehabilitar
	 * 
	 * @param cdCliente
	 * @param cdUniEco
	 * @param cdRamo
	 * @return
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public PagedList obtenerRequisitos(String cdUniEco, String nmPoliza, String estado, String nmSuplem, String nmSituac, int start, int limit) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdUniEco", cdUniEco);
		map.put("nmPoliza", nmPoliza);
		map.put("estado", estado);
		map.put("nmSuplem", nmSuplem);
		map.put("nmSituac", nmSituac);

		PagedList pagedList = pagedBackBoneInvoke(map, "REHABILITACION_MANUAL_OBTENER_REQUISITOS", start, limit);
		return pagedList;
	}

	/**
	 * Verifica que los recibos anteriores estén pagados
	 * Usa el sp PKG_CANCELA.P_RECIBOS_PAGADOS
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String validarRecibosPagados(String cdUniEco, String cdRamo,
			String nmPoliza, String estado, String nmSuplem, String fechaRehab)
			throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdUniEco", cdUniEco);
		map.put("cdRamo", cdRamo);
		map.put("nmPoliza", nmPoliza);
		map.put("estado", estado);
		map.put("nmSuplem", nmSuplem);
		map.put("fechaRehab", fechaRehab);

		WrapperResultados res = returnBackBoneInvoke(map, "VERIFICAR_RECIBOS_ANTERIORES_PAGADOS");
		return res.getMsg();
	}

	/**
	 * Guarda los Requisitos para la Poliza a Rehabilitar
	 * Usa el Sp PKG_CANCELA.P_REQUISITOS_POLIZA
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String guardarRequisitos(String cdUniEco, String cdRamo,
			String estado, String nmPoliza, String nmSuplem, String nmSituac,
			String cdElemento, String cdDocumen, String inDenEntrega) throws ApplicationException {

		HashMap map = new HashMap ();
		map.put("cdUniEco", cdUniEco);
		map.put("cdRamo", cdRamo);
		map.put("estado", estado);
		map.put("nmPoliza", nmPoliza);
		map.put("nmSuplem", nmSuplem);
		map.put("nmSituac", nmSituac);
		map.put("cdElemento", cdElemento);
		map.put("cdDocumen", cdDocumen);
		map.put("inDenEntrega", inDenEntrega);

		WrapperResultados res = returnBackBoneInvoke(map, "REHABILITACION_MANUAL_GUARDAR_REQUISITOS");
		return res.getMsgText();
	}

	/**
	 * Permite rehabilitar una poliza
	 * Usa el sp PKG_CANCELA.P_REHABILITA_POLIZA
	 * 
	 * @return Mensaje de exito/error
	 */
	@SuppressWarnings("unchecked")
	public String rehabilitarPoliza (String cdUniEco, String cdRamo, String estado, String nmPoliza, String feEfecto, String feVencim, String feCancel,
			String feReInst, String cdRazon, String cdPerson, String cdMoneda, String nmCancel, String comentarios, String nmSuplem) throws ApplicationException {
		HashMap map = new HashMap ();
		map.put("cdUniEco", cdUniEco);
		map.put("cdRamo", cdRamo);
		map.put("estado", estado);
		map.put("nmPoliza", nmPoliza);
		map.put("feEfecto", feEfecto);
		map.put("feVencim", feVencim);
		map.put("feCancel", feCancel);
		map.put("feReInst", feReInst);
		map.put("cdRazon", cdRazon);
		map.put("cdPerson", cdPerson);
		map.put("cdMoneda", cdMoneda);
		map.put("nmCancel", nmCancel);
		map.put("comentarios", comentarios);
		map.put("nmSuplem", nmSuplem);

		WrapperResultados res = returnBackBoneInvoke(map, "REHABILITACION_MANUAL_REHABILITAR_POLIZA");

		return res.getMsgText();
	}

}
