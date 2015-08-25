package mx.com.gseguros.portal.general.service;

import mx.com.gseguros.portal.general.model.RespuestaEmisionIndividualVO;


public interface ProcesoEmisionManager {
	
	public RespuestaEmisionIndividualVO emitir(String cdunieco, String cdramo, String estado, String nmpoliza, 
			String cdtipsit, String ntramite, String cdusuari, String cdsisrol, String cdelemento,
			boolean esFlotilla, String tipoGrupoInciso) throws Exception;
	
}