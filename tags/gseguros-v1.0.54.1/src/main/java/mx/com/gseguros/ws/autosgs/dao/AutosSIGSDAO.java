package mx.com.gseguros.ws.autosgs.dao;

import java.util.Map;


public interface AutosSIGSDAO {
		
	public Integer endosoDomicilio(Map<String, Object> params) throws Exception;

	public Integer cambioDomicilioCP(Map<String, Object> params) throws Exception;
	
	public Integer cambioDomicilioSinCPColonia(Map<String, Object> params) throws Exception;

	public Integer insertaReciboAuto(Map<String, Object> params) throws Exception;

	public Integer confirmaRecibosAuto(Map<String, Object> params) throws Exception;

	public Integer ejecutaVidaPorRecibo(Map<String, Object> params) throws Exception;
	
	public Integer endosoPlacasMotor(Map<String, Object> params) throws Exception;

	public Integer endosoTipoServicio(Map<String, Object> params) throws Exception;

	public Integer endosoVigenciaPol(Map<String, Object> params) throws Exception;

	public Integer endosoTextoLibre(Map<String, Object> params) throws Exception;

	public Integer endosoSerie(Map<String, Object> params) throws Exception;
	
	public Integer endosoBeneficiario(Map<String, Object> params) throws Exception;
	
	public Integer endosoAseguradoAlterno(Map<String, Object> params) throws Exception;
	
	public Integer endosoAdaptacionesRC(Map<String, Object> params) throws Exception;
	
//	public Integer endosoVigencia(Map<String, Object> params) throws Exception;
	
	public Integer endosoNombreCliente(Map<String, Object> params) throws Exception;
	
	public Integer endosoRfcCliente(Map<String, Object> params) throws Exception;
	
	public Integer endosoCambioCliente(Map<String, Object> params) throws Exception;
	
	public void revierteEndosoFallidoSigs(Map<String, Object> params) throws Exception;
	
	public void revierteEndosoBFallidoSigs(Map<String, Object> params) throws Exception;

	public Integer CambioClientenombreRFCfechaNacimiento(Map<String, Object> params) throws Exception;
	
}
