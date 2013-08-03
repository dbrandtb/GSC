package mx.com.aon.catbo.service;


import java.util.List;

import mx.com.aon.catbo.model.PreguntaEncVO;
import mx.com.aon.catbo.model.RespuestaEncVO;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.ItemVO;
import mx.com.aon.portal.util.WrapperResultados;


public interface IngresarEncuestaManager {
	
	public WrapperResultados obtenerPreguntaEncuesta(String cdEncuesta, String cdSecuencia) throws ApplicationException;
	
	public PreguntaEncVO obtenerPreguntaEncuestaSig(String cdEncuesta, String cdPregunta, String otValor) throws ApplicationException;

	public String guardarPreguntaEncuesta(RespuestaEncVO respuestaEncVO, List<ItemVO> respuesEnc ) throws ApplicationException;
}
