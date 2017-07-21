package mx.com.gseguros.portal.general.service;

import java.util.Map;


public interface ProcesoEmisionManager {
	
	/**
	 * Flujo completo de emision de polizas
	 * @param cdunieco
	 * @param cdramo
	 * @param estado
	 * @param nmpoliza
	 * @param cdtipsit
	 * @param ntramite
	 * @param cdusuari
	 * @param cdsisrol
	 * @param cdelemento
	 * @param cveusuariocaptura Clave de usuario de captura de GS (opcional)
	 * @param esFlotilla
	 * @param tipoGrupoInciso
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> emitir(String cdunieco, String cdramo, String estado, String nmpoliza, 
			String cdtipsit, String ntramite, String cdusuari, String cdsisrol, String cdelemento,
			String cveusuariocaptura, boolean esFlotilla, String tipoGrupoInciso) throws Exception;
	
}