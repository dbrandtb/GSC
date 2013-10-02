package mx.com.aon.portal.service.impl;

import org.apache.log4j.Logger;

import mx.com.aon.portal.model.ClavesVO;


import mx.com.aon.portal.service.GeneracionClavesManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GeneracionClavesManagerImpl extends AbstractManagerJdbcTemplateInvoke implements GeneracionClavesManager {

    protected static Logger logger = Logger.getLogger(GeneracionClavesManagerImpl.class);

  
	

	public PagedList obtieneValores(String pv_idegenerador_i, int start, int limit) throws ApplicationException{
    	HashMap map = new HashMap();
        map.put("pv_idegenerador_i", pv_idegenerador_i);
                
        String endpointName = "OBTIENE_VALORES";
      
        return pagedBackBoneInvoke(map, endpointName, start, limit);
    }
	
	
	public String actualizarValores(List <ClavesVO> grillaGeneracionClavesList)throws ApplicationException {
			
			ClavesVO clavesVO = new ClavesVO(); 
			
			WrapperResultados res=null;
			for (int i=0; i<grillaGeneracionClavesList.size(); i++) {
	    		
				HashMap map = new HashMap();
    			ClavesVO clavesVO_grid=grillaGeneracionClavesList.get(i);
    		
    			clavesVO.setValor(clavesVO_grid.getValor()); 
    			clavesVO.setIdGenerador(clavesVO_grid.getIdGenerador());
    			clavesVO.setIdParam(clavesVO_grid.getIdParam());
    			//clavesVO.setDescripcion(clavesVO_grid.getDescripcion());
    			
    			map.put("pv_valor_i", clavesVO.getValor()); 
    	        map.put("pv_idegenerador_i", clavesVO.getIdGenerador());
    	        map.put("pv_idparam_i", clavesVO.getIdParam());
    	        
    	        res = returnBackBoneInvoke(map,"ACTUALIZA_VALORES");
    	        
    	}
			return res.getMsgText();			        	        	        
	        
	}
	
}
