package mx.com.gseguros.portal.despachador.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface DespachadorDAO {
	public Map<String, String> recuperarDatosClasificacionSucursal (String cdunieco) throws Exception;
	
	public String recuperarRolTrabajoEstatus (String cdtipflu, String cdflujomc, String estatus) throws Exception;
	
	public List<Map<String, String>> recuperarPermisosTramite (String cdtipflu, String cdflujomc, String cdramo, String cdtipsit) throws Exception;
	
	public String recuperarTipoTurnadoEstatus (String cdtipflu, String cdflujomc, String estatus) throws Exception;
	
	public boolean esStatusFinal (String ntramite, String status) throws Exception;
	
	public void borrarUsuarioYSucursalEncargado (String ntramite) throws Exception;
	
	public int recuperarConteoTramitesSucursal (String cdunidspch) throws Exception;
	
	public String recuperarUsuarioParaRegresarTramite (String ntramite, String cdunidspch, String cdsisrol) throws Exception;
	
	public String recuperarSiguienteUsuarioCarrusel (String cdunidspch, String cdsisrol, boolean soloUsuariomatriz, String ntramite) throws Exception;
	
	public String recuperarSiguienteUsuarioCarga (String cdunidspch, String cdsisrol, boolean soloUsuariomatriz, String ntramite) throws Exception;
	
	public List<Map<String, String>> recuperarSucursalesParaEscalamiento (String cdtipflu, String cdflujomc, String zona, int nivel,
			String cdsisrol) throws Exception;
	
	public Map<String, String> recuperarCualquierSucursalNivel (String cdtipflu, String cdflujomc, String zona, int nivel) throws Exception;
	
	public String recuperarCdtipramFlujo (String cdtipflu, String cdflujomc) throws Exception;
	
	public List<Map<String, String>> recuperarSucursalesParaDespachar (String cdtipram, String zona, String nivel, String cdunieco,
	        String cdsisrol, String ntramite, String cdtipflu, String cdflujomc, String cdramo, String cdtipsit) throws Exception;
	
	public Map<String, String> recuperarUsuarioRegreso (String cdtipram, String cdsisrol, String ntramite) throws Exception;
	
	public String recuperarStatusSustituto (String cdtipflu, String cdflujomc, String zona, String status) throws Exception;
	
	public void cerrarHistorialTramite (String ntramite, Date fecha, String cdusuari, String cdsisrol, String status) throws Exception;
	
	public String recuperarSucursalUsuarioPorTramite (String cdusuari, String ntramite) throws Exception;
	
	public String recuperarNombreUsuario (String cdusuari) throws Exception;
    
    public List<Map<String, String>> recuperarHistorialMesa (String ntramite) throws Exception;
    
    public List<Map<String, String>> recuperarHistorialMesaHora (String ntramite) throws Exception;
    
    public Map<String, String> recuperarAgenteDestino (String ntramite) throws Exception;
    
    public String recuperarSucursalUsuarioPorTipoTramite (String cdusuari, String cdflujomc) throws Exception;
    
    public List<Map<String, String>> recuperarPermisosEndosos (String cdusuari, String cdsisrol) throws Exception;
    
    public List<Map<String, String>> recuperarLogDespachadorZona (String ntramite, String cdunieco, String estatus) throws Exception;
    
    public List<Map<String, String>> recuperarDetallesMesaHora (String ntramite) throws Exception;
    
    public List<Map<String, String>> recuperarClaveAutoFlujo(String ntramite) throws Exception;
    
    public void guardaClaveAutosFlujo(String ntramite, List<Map<String, String>>slist1) throws Exception;
}