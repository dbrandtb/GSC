package mx.com.aon.portal.service;

import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.EjecutivoCuentaVO;
import mx.com.gseguros.exception.ApplicationException;

public interface MantenimientoEjecutivosCuentaManager {
	/**
	 * Obtiene listado de Ejecutivos de acuerdo a los criterios seleccionados
	 * 
	 * @param cdEjecutivo
	 * @param cdPerson
	 * @param fechaInicial
	 * @param fechaFinal
	 * @param status
	 * @return
	 * @throws ApplicationException
	 */
	public PagedList buscarEjecutivosCuenta(String cdEjecutivo, String cdPerson, String fechaInicial, String fechaFinal, String status, int start, int limit) throws ApplicationException;

	public EjecutivoCuentaVO obtenerEjecutivo(String cdEjecutivo) throws ApplicationException;

	public String guardarEjecutivo (String cdEjecutivo, String cdPerson, String fechaInicial, String fechaFinal, String status) throws ApplicationException;

	public String borrarEjecutivo (String cdEjecutivo) throws ApplicationException;

	public PagedList obtenerAtributos (String cdEjecutivo, int start, int limit) throws ApplicationException;

	//public String guardarAtributos (String cdEjecutivo, String cdAtribu, String otValor) throws ApplicationException;

	public TableModelExport getModelEjecutivos(String cdEjecutivo, String cdPerson, String fechaInicial, String fechaFinal, String status) throws ApplicationException;

	public TableModelExport getModelAtributosEjecutivos(String cdEjecutivo) throws ApplicationException;
}
