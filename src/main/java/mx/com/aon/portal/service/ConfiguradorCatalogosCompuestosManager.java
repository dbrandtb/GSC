package mx.com.aon.portal.service;

import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.ExtJSFieldVO;
import mx.com.aon.portal.model.ValoresCamposCatalogosCompuestosVO;
import mx.com.aon.portal.model.ValoresCamposCatalogosSimplesVO;

public interface ConfiguradorCatalogosCompuestosManager {
	
	public String obtenerTituloPantalla (String cdPantalla) throws ApplicationException;

	public PagedList obtenerCamposBusqueda (String cdPantalla) throws ApplicationException;

	public PagedList obtenerValoresBusqueda (String cdPantalla, String Clave, String Descripcion, String Valor1, String Valor2, String Valor3, int start, int limit) throws ApplicationException;

	public String guardarRegistro (String cdPantalla, List<ValoresCamposCatalogosCompuestosVO>listaValores) throws ApplicationException;

	public String actualizarRegistro (String cdPantalla, List<ValoresCamposCatalogosCompuestosVO>listaValores) throws ApplicationException;

	public String borrarRegistro (String cdPantalla, String valorLlave1, String valorLlave2, String valorLlave3, String valorLlave4) throws ApplicationException;

	public ExtJSFieldVO obtenerRegistro (String cdTabla, String cdClave) throws ApplicationException;

	public PagedList obtenerColumnasGrilla (String cdPantalla, List<ValoresCamposCatalogosCompuestosVO> listaValores, int start, int limit) throws ApplicationException;

	public TableModelExport getModel(String cdPantalla, List<ValoresCamposCatalogosCompuestosVO> listaValores) throws ApplicationException;

}