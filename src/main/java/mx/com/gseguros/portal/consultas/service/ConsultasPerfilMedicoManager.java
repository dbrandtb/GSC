package mx.com.gseguros.portal.consultas.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.consultas.model.PerfilAseguradoVO;

public interface ConsultasPerfilMedicoManager {
	
	public List<Map<String, String>> consultaPerfilAsegurados(Map<String,String> params) throws Exception;
	
	public PerfilAseguradoVO consultaICDSAsegurado(Map<String,String> params) throws Exception;

}
