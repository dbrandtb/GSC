package mx.com.aon.portal.service;

import mx.com.aon.export.model.TableModelExport;
import mx.com.gseguros.exception.ApplicationException;

/**
 * Interface de servicios para Rehabilitacion Masiva
 * 
 * @author Usuario
 *
 */
public interface RehabilitacionMasivaManager {

	public PagedList buscarPolizasCanceladasARehabilitar(String dsAsegurado, String dsAseguradora, String dsProducto, String nmPoliza, String nmInciso, int start, int limit) throws ApplicationException;

	public String rehabilitarPoliza (String cdUniEco, String cdRamo, String estado, String nmPoliza, String feEfecto, String feVencim, String feCancel,
									String feReInst, String cdRazon, String cdPerson, String cdMoneda, String nmCancel, String comentarios, String nmSuplem) throws ApplicationException;

	public TableModelExport getModel(String dsAsegurado, String dsAseguradora, String dsProducto, String nmPoliza, String nmInciso) throws ApplicationException;
}