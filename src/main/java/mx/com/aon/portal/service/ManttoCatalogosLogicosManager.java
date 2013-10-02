package mx.com.aon.portal.service;

import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.CatalogoLogicoVO;
import mx.com.gseguros.exception.ApplicationException;

public interface ManttoCatalogosLogicosManager {

	public PagedList obtenerListadoCatalogosLogicos (String cdTabla, String cdRegion, String cdIdioma, String codigo,  String descripcion, int start, int limit) throws ApplicationException;

	public CatalogoLogicoVO obtenerRegistroCatalogosLogicos (String cdTabla, String cdRegion, String cdIdioma, String codigo, String descripcion) throws ApplicationException;

	public String insertarOActualizarRegistroCatalogoLogico (String cdTabla, String cdRegion,String cdIdioma, String codigo, String descripcion,String descripl,String dsLabel) throws ApplicationException;

	public String borrarRegistroCatalogoLogico (String cdTabla, String cdRegion,String cdIdioma, String codigo) throws ApplicationException;
	
	public TableModelExport getModel(String cdTabla, String cdRegion, String cdIdioma, String codigo, String descripcion) throws ApplicationException;
}
