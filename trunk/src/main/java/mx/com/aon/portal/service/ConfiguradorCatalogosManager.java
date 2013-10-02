package mx.com.aon.portal.service;

import java.util.List;

import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.ElementoComboBoxVO;
import mx.com.aon.portal.model.ExtJSFieldVO;
import mx.com.aon.portal.model.ValoresCamposCatalogosSimplesVO;
import mx.com.gseguros.exception.ApplicationException;

public interface ConfiguradorCatalogosManager {

	public String obtenerTituloPantalla (String cdPantalla) throws ApplicationException;

	public PagedList obtenerCamposBusquedaSimple (String cdPantalla) throws ApplicationException;

	public PagedList obtenerValoresBusqueda (String cdPantalla, String Clave, String Descripcion, String Valor1, String Valor2, String Valor3, int start, int limit) throws ApplicationException;

	public String guardarRegistro (String cdPantalla, List<ValoresCamposCatalogosSimplesVO>listaValores) throws ApplicationException;

	public String actualizarRegistro (String cdPantalla, List<ValoresCamposCatalogosSimplesVO>listaValores) throws ApplicationException;

	public String borrarRegistro (String cdPantalla, String valorLlave) throws ApplicationException;

	public ExtJSFieldVO obtenerRegistro (String cdTabla, String cdClave) throws ApplicationException;

	public PagedList obtenerColumnasGrilla (String cdPantalla, List<ValoresCamposCatalogosSimplesVO> listaValores, int start, int limit) throws ApplicationException;

	public TableModelExport getModel(String cdPantalla, List<ValoresCamposCatalogosSimplesVO> listaValores) throws ApplicationException;
}
