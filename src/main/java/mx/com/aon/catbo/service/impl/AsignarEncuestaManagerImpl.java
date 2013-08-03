package mx.com.aon.catbo.service.impl;

import mx.com.aon.catbo.service.AsignarEncuestaManager;
import mx.com.aon.catbo.service.ConfigurarEncuestaManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.AbstractManager;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AsignarEncuestaManagerImpl extends AbstractManager implements AsignarEncuestaManager {

	@SuppressWarnings("unchecked")

public PagedList obtenerEncuesta(String pv_dsunieco_i,String pv_dsramo_i,String pv_estado_i,String  pv_nmpoliza_i, String pv_dsperson_i, String pv_dsusuario_i, int start, int limit) throws ApplicationException{
		HashMap map = new HashMap();
        map.put("pv_dsunieco_i", pv_dsunieco_i);
		map.put("pv_dsramo_i", pv_dsramo_i);
		map.put("pv_estado_i", pv_estado_i);
		map.put("pv_nmpoliza_i", pv_nmpoliza_i);
		map.put("pv_dsperson_i", pv_dsperson_i);
		map.put("pv_dsusuario_i", pv_dsusuario_i);
        
        String endpointName = "ASIGNA_ENCUESTA_OBTIENE_ENCUESTA";
        return pagedBackBoneInvoke(map, endpointName, start, limit);
	}	
		
}
	

	

	
	
	
	