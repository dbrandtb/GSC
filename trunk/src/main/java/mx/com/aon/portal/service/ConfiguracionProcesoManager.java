package mx.com.aon.portal.service;

import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.ConfiguracionProcesoVO;
import mx.com.gseguros.exception.ApplicationException;

public interface ConfiguracionProcesoManager {

	public PagedList buscarProcesos (String dsUniEco, String dsRamo, String dsElemento, String dsProceso, int start, int limit) throws ApplicationException;

	public String borrarProceso (String cdUniEco, String cdRamo, String cdElemento, String cdProceso) throws ApplicationException;

	public ConfiguracionProcesoVO obtenerProceso (String cdUniEco, String cdRamo, String cdElemento, String cdProceso) throws ApplicationException;

	public String actualizarProceso (String cdUniEco, String cdRamo, String cdElemento, String cdProceso, String swEstado) throws ApplicationException;

	public String guardarProceso (String cdUniEco, String cdRamo, String cdElemento, String cdProceso, String swEstado) throws ApplicationException;

	public TableModelExport getModel (String dsUniEco, String dsRamo, String dsElemento, String dsProceso) throws ApplicationException;
}
