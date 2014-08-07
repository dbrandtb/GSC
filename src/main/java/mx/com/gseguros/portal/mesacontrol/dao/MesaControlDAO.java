package mx.com.gseguros.portal.mesacontrol.dao;

import java.util.Map;

public interface MesaControlDAO
{
	public String cargarCdagentePorCdusuari(Map<String,String>params)throws Exception;
}