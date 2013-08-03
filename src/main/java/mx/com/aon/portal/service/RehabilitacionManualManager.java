package mx.com.aon.portal.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.RehabilitacionManual_PolizaVO;
import mx.com.aon.portal.model.RehabilitacionManual_RequisitosVO;

public interface RehabilitacionManualManager {
	/**
	 * Obtiene datos de la poliza seleccionada
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	public RehabilitacionManual_PolizaVO obtenerInformacionPoliza(String cdUniEco, String cdRamo, String estado, String nmPoliza,
			String nmSituac) throws ApplicationException;

	/**
	 * Obtiene Datos de Cancelacion de la Poliza a Rehabilitar
	 * 
	 * @param dsAsegurado
	 * @param dsAseguradora
	 * @param nmPoliza
	 * @param estado
	 * @param nmSuplem
	 * @param nmSituac
	 * @return
	 * @throws ApplicationException
	 */
	public RehabilitacionManual_PolizaVO obtenerDatosCancelacion (String dsAsegurado, String dsAseguradora, String nmPoliza,
			String estado, String nmSuplem, String nmSituac) throws ApplicationException;

	/**
	 * Obtiene los Requisitos para rehabilitar
	 * 
	 * @param cdCliente
	 * @param cdUniEco
	 * @param cdRamo
	 * @return
	 * @throws ApplicationException
	 */
	public PagedList obtenerRequisitos(String cdUniEco, String nmPoliza, String estado, String nmSuplem, String nmSituac, int start, int limit) throws ApplicationException;
	
	/**
	 * Verifica que los recibos anteriores estén pagados
	 * 
	 * @param cdUniEco
	 * @param cdRamo
	 * @param nmPoliza
	 * @param estado
	 * @param nmSuplem
	 * @param fechaRehab
	 * @return
	 * @throws ApplicationException
	 */
	public String validarRecibosPagados (String cdUniEco, String cdRamo, String nmPoliza, String estado, String nmSuplem, String fechaRehab) throws ApplicationException;

	/**
	 * Guarda Requisitos para la Poliza a Rehabilitar
	 * 
	 * @param cdUniEco
	 * @param cdRamo
	 * @param estado
	 * @param nmPoliza
	 * @param nmSuplem
	 * @param nmSituac
	 * @param cdElemento
	 * @param cdDocumen
	 * @param inDenEntrega
	 * @return
	 * @throws ApplicationException
	 */
	public String guardarRequisitos (String cdUniEco, String cdRamo, String estado, String nmPoliza, String nmSuplem, String nmSituac, String cdElemento, String cdDocumen, String inDenEntrega) throws ApplicationException;

	/**
	 * Rehabilita una Poliza.
	 * 
	 * @param cdUniEco
	 * @param cdRamo
	 * @param estado
	 * @param nmPoliza
	 * @param feEfecto
	 * @param feVencim
	 * @param feCancel
	 * @param feReInst
	 * @param cdRazon
	 * @param cdPerson
	 * @param cdMoneda
	 * @param nmCancel
	 * @param comentarios
	 * @param nmSuplem
	 * @return
	 * @throws ApplicationException
	 */
	public String rehabilitarPoliza (String cdUniEco, String cdRamo, String estado, String nmPoliza, String feEfecto, String feVencim, String feCancel,
			String feReInst, String cdRazon, String cdPerson, String cdMoneda, String nmCancel, String comentarios, String nmSuplem) throws ApplicationException;
}