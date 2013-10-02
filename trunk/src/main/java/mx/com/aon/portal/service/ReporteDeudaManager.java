package mx.com.aon.portal.service;

import mx.com.aon.portal.model.EstructuraVO;
import mx.com.aon.export.model.TableModelExport;
import mx.com.gseguros.exception.ApplicationException;

/**
 * Interface de servicios para estructura.
 *
 */
public interface ReporteDeudaManager {


	public PagedList buscarReportesDeuda(int start, int limit, String cdAsegurado,String cdAseguradora, String nmPoliza, String fechaDesde )throws ApplicationException;
	public TableModelExport getModel(String cdAsegurado,String cdAseguradora, String nmPoliza, String fechaDesde ) throws ApplicationException;

}
