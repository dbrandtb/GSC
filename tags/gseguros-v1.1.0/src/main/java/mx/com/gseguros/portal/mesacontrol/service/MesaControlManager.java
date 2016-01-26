package mx.com.gseguros.portal.mesacontrol.service;

import java.util.Date;
import java.util.Map;

public interface MesaControlManager
{
    public String cargarCdagentePorCdusuari(String cdusuari)throws Exception;
    
    public void guardarRegistroContrarecibo(String ntramite,String cdusuari)throws Exception;
    
    public void actualizarNombreDocumento(String ntramite,String cddocume,String nuevo)throws Exception;
    
    public void borrarDocumento(String ntramite,String cddocume)throws Exception;
    
    public void movimientoDetalleTramite(
			String ntramite
			,Date feinicio
			,String cdclausu
			,String comments
			,String cdusuari
			,String cdmotivo
			,String cdsisrol
			,String swagente
			)throws Exception;
    
    public void validarAntesDeTurnar(
    		String ntramite
    		,String status
    		,String cdusuari
    		,String cdsisrol
    		)throws Exception;
    
    @Deprecated
	public String movimientoTramite(
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
			,String cdusuari
			,String cdsisrol
			,String swimpres
			,String cdtipflu
			,String cdflujomc
			,Map<String,String>valores, String cdtipsup
			)throws Exception;
    
    public void marcarTramiteVistaPrevia(String ntramite) throws Exception;
}