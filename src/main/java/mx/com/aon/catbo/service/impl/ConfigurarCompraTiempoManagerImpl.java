package mx.com.aon.catbo.service.impl;

import mx.com.aon.catbo.model.ArchivosFaxesVO;
import mx.com.aon.catbo.model.ConfigurarCompraTiempoVO;
import mx.com.aon.catbo.service.ConfigurarCompraTiempoManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;
import mx.com.aon.portal.util.WrapperResultados;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: gabrielforradellas
 * Date: Oct 15, 2008
 * Time: 3:24:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConfigurarCompraTiempoManagerImpl extends AbstractManagerJdbcTemplateInvoke implements ConfigurarCompraTiempoManager {


    public String guardarCompraTiempo(String pv_cdproceso_i, String pv_cdnivatn_i, String pv_nmcant_desde_i, String pv_nmcant_hasta_i, String pv_tunidad_i, String pv_nmvecescompra_i) throws ApplicationException {

        HashMap map = new HashMap();
        map.put("pv_cdproceso_i", pv_cdproceso_i);
        map.put("pv_cdnivatn_i", pv_cdnivatn_i);
        map.put("pv_nmcant_desde_i", pv_nmcant_desde_i);
        map.put("pv_nmcant_hasta_i", pv_nmcant_hasta_i);
        map.put("pv_tunidad_i", pv_tunidad_i);
        map.put("pv_nmvecescompra_i", pv_nmvecescompra_i);
        WrapperResultados res =  returnBackBoneInvoke(map,"GUARDA_COMPRA_TIEMPO");
        return res.getMsgText();
    }

    public String borrarCompraTiempo(String pv_cdproceso_i, String pv_cdnivatn_i) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pv_cdproceso_i", pv_cdproceso_i);
        map.put("pv_cdnivatn_i", pv_cdnivatn_i);
        WrapperResultados res =  returnBackBoneInvoke(map,"BORRA_COMPRA_TIEMPO");
        return res.getMsgText();
    }
    
    @SuppressWarnings("unchecked")
	public PagedList obtieneCompraTiempoDisponible(String pv_cdproceso_i, String pv_cdnivatn_i,String pv_nmcaso_i, int start, int limit) throws ApplicationException{
    	HashMap map = new HashMap();
        map.put("pv_cdproceso_i", pv_cdproceso_i);
        map.put("pv_cdnivatn_i", pv_cdnivatn_i);
        map.put("pv_nmcaso_i", pv_nmcaso_i);
        //WrapperResultados res =  returnBackBoneInvoke(map,"OBTIENE_COMPRA_TIEMPO_DISPONIBLE");
        //return res.getMsgText();
        
        String endpointName = "OBTIENE_COMPRA_TIEMPO_DISPONIBLE";
        return pagedBackBoneInvoke(map, endpointName, start, limit);
    }
    
 
    
    @SuppressWarnings("unchecked")
	public ConfigurarCompraTiempoVO obtieneConfigCompraTiempo(String pv_cdproceso_i, String pv_cdnivatn_i) throws ApplicationException{
    	HashMap map = new HashMap();
        map.put("pv_cdproceso_i", pv_cdproceso_i);
        map.put("pv_cdnivatn_i", pv_cdnivatn_i);
        
        //WrapperResultados res =  returnBackBoneInvoke(map,"OBTIENE_COMPRA_TIEMPO");
        //return res.getMsgText();
        
        String endpointName = "OBTIENE_COMPRA_TIEMPO";
      
        return (ConfigurarCompraTiempoVO)getBackBoneInvoke(map, endpointName);
    }
    
    public String validaSatatusCaso(String pv_nmcaso_i) throws ApplicationException {

        HashMap map = new HashMap();
        map.put("pv_nmcaso_i", pv_nmcaso_i);
        
        WrapperResultados res =  returnBackBoneInvoke(map,"VALIDA_STATUS_CASO");
        return res.getMsgText();
    }
    
   

}
