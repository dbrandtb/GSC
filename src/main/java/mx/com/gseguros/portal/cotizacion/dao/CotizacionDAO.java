package mx.com.gseguros.portal.cotizacion.dao;

import java.util.Map;

public interface CotizacionDAO
{
	public void movimientoTvalogarGrupo(Map<String,String>params)throws Exception;
	public void movimientoMpolisitTvalositGrupo(Map<String,String>params)throws Exception;
	public void movimientoMpoligarGrupo(Map<String,String>params)throws Exception;
}