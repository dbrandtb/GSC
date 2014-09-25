package mx.com.gseguros.portal.renovacion.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface RenovacionDAO
{
	public List<Map<String,String>>buscarPolizasRenovables(String cdunieco,String cdramo,String anio,String mes)throws Exception;
	
	public void marcarPoliza(String anio
			,String mes
			,String cdtipopc
			,String cdtipacc
			,String cdunieco
			,String cdramo
			,String nmpoliza
			,Date   feemisio
			,String swrenova
			,String swaproba
			,String nmsituac)throws Exception;
}