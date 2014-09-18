package mx.com.gseguros.portal.cotizacion.dao;

import java.util.List;
import java.util.Map;

public interface CotizacionDAO
{
	public void movimientoTvalogarGrupo(Map<String,String>params)throws Exception;
	public void movimientoMpolisitTvalositGrupo(Map<String,String>params)throws Exception;
	public void movimientoMpoligarGrupo(Map<String,String>params)throws Exception;
	public Map<String,String>cargarDatosCotizacionGrupo(Map<String,String>params)throws Exception;
	public List<Map<String,String>>cargarGruposCotizacion(Map<String,String>params)throws Exception;
	public Map<String,String>cargarDatosGrupoLinea(Map<String,String>params)throws Exception;
	public List<Map<String,String>>cargarTvalogarsGrupo(Map<String,String>params)throws Exception;
	public List<Map<String,String>>cargarTarifasPorEdad(Map<String,String>params)throws Exception;
	public List<Map<String,String>>cargarTarifasPorCobertura(Map<String,String>params)throws Exception;
	public String cargarNombreAgenteTramite(Map<String,String>params)throws Exception;
	public Map<String,String>cargarPermisosPantallaGrupo(Map<String,String>params)throws Exception;
	public Map<String,String>obtieneTipoValorAutomovil(Map<String,String>params)throws Exception;
	public void guardarCensoCompleto(Map<String,String>params)throws Exception;
	public List<Map<String,String>>cargarAseguradosExtraprimas(Map<String,String>params)throws Exception;
	public void guardarExtraprimaAsegurado(Map<String,String>params)throws Exception;
	public List<Map<String,String>>cargarAseguradosGrupo(Map<String,String>params)throws Exception;
	public void borrarMpoliperGrupo(Map<String,String>params)throws Exception;
	public Map<String,String>cargarTipoSituacion(Map<String,String>params)throws Exception;
	public String cargarCduniecoAgenteAuto(Map<String,String>params)throws Exception;
	public Map<String,String>obtenerDatosAgente(Map<String,String>params)throws Exception;
	public Map<String,String>cargarNumeroPasajerosPorTipoUnidad(Map<String,String>params)throws Exception;
	public Map<String,String>obtenerParametrosCotizacion(Map<String,String>params)throws Exception;
	public Map<String,String>cargarAutoPorClaveGS(Map<String,String>params)throws Exception;
	public Map<String,String>cargarClaveGSPorAuto(Map<String,String>params)throws Exception;
	public Map<String,String>cargarSumaAseguradaAuto(Map<String,String>params)throws Exception;
	public void movimientoMpolicotICD(Map<String,String>params)throws Exception;
	public List<Map<String,String>>cargarMpolicotICD(Map<String,String>params)throws Exception;
	public void movimientoMpoliagr(Map<String,String>params)throws Exception;
}