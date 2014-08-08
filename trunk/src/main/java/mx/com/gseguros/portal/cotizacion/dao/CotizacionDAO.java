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
	public void guardarCensoCompleto(Map<String,String>params)throws Exception;
}