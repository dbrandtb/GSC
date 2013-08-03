package mx.com.aon.catbo.service.impl;

import java.util.HashMap;
import java.util.List;


import mx.com.aon.catbo.model.PreguntaEncVO;
import mx.com.aon.catbo.service.IngresarEncuestaManager;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.RespuestaEncVO;

import mx.com.aon.portal.model.ItemVO;
import mx.com.aon.portal.service.impl.AbstractManager;
import mx.com.aon.portal.util.WrapperResultados;


/**
 * Clase que implementa la interfaz PreguntaEncuestaManager.
 * 
 * @extends AbstractManager
 *
 */
public class IngresarEncuestaManagerImpl extends AbstractManager implements IngresarEncuestaManager {
    
	@SuppressWarnings("unchecked")
	public WrapperResultados obtenerPreguntaEncuesta(String cdEncuesta, String cdSecuencia) throws ApplicationException{
		
		HashMap map = new HashMap();
		map.put("cdEncuesta", cdEncuesta);
		map.put("cdSecuencia", cdSecuencia);
		WrapperResultados res= returnBackBoneInvoke(map, "OBTENER_PREGUNTA_ENCUESTA");
        return res;
	}
	
	@SuppressWarnings("unchecked")
	public PreguntaEncVO obtenerPreguntaEncuestaSig(String cdEncuesta, String cdPregunta, String otValor)throws ApplicationException{
		
		HashMap map = new HashMap();
		map.put("cdEncuesta", cdEncuesta);
		map.put("cdPregunta", cdPregunta);
		map.put("otValor", otValor);
		
		
        return (PreguntaEncVO)getBackBoneInvoke(map,"OBTENER_PREGUNTA_ENCUESTA_SIGUIENTE");
	}
	
	
	@SuppressWarnings("unchecked")
	public String guardarPreguntaEncuesta(RespuestaEncVO respuestaEncVO, List<ItemVO> respuesEnc ) throws ApplicationException{
		WrapperResultados res= new WrapperResultados();
		HashMap map = new HashMap();
		map.put("nmConfig", respuestaEncVO.getNmConfig());	
		map.put("cdUniEco", respuestaEncVO.getCdUniEco());	
		map.put("cdRamo", respuestaEncVO.getCdRamo());	
		map.put("estado", respuestaEncVO.getEstado());	
		map.put("nmPoliza", respuestaEncVO.getNmPoliza());	
		map.put("cdPerson", respuestaEncVO.getCdPerson());	
		map.put("cdEncuesta", respuestaEncVO.getCdEncuesta());	
		
		if (respuesEnc!=null)
		{
			for (int i=0; i<respuesEnc.size(); i++) {
				ItemVO itemVO=respuesEnc.get(i);
				map.put("cdPregunt",itemVO.getId());
				map.put("otValor",itemVO.getTexto());
				   
				res  =  returnBackBoneInvoke(map,"GUARDAR_RESPUESTA_ENCUESTA");
			}
		}else
		{
			return "No se puede realizar la operación";
		}
		return res.getMsgText();
	}

}