package mx.com.gseguros.portal.mesacontrol.dao;

import java.util.Date;
import java.util.Map;

public interface MesaControlDAO
{
	public String cargarCdagentePorCdusuari(Map<String,String>params)throws Exception;
	public String movimientoMesaControl(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdsucadm
			,String cdsucdoc
			,String cdtiptra
			,Date ferecepc
			,String cdagente
			,String referencia
			,String nombre
			,Date festatus
			,String status
			,String comments
			,String nmsolici
			,String cdtipsit
			,Map<String,String>valores
			)throws Exception;
	public void movimientoDetalleTramite(
			String ntramite
			,Date feinicio
			,String cdclausu
			,String comments
			,String cdusuari
			,String cdmotivo)throws Exception;
	public void actualizarNmsoliciTramite(String ntramite,String nmsolici)throws Exception;
	public void actualizaValoresTramite(
			String ntramite
			,String cdramo
			,String cdtipsit
			,String cdsucadm
			,String cdsucdoc
			,String comments
			,Map<String,String>valores)throws Exception;
}