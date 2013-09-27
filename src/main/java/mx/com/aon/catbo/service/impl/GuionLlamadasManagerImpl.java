package mx.com.aon.catbo.service.impl;


import java.util.HashMap;

import mx.com.aon.catbo.service.GuionLlamadasManager;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;
import mx.com.aon.portal.util.ConvertUtil;
import mx.com.aon.portal.util.WrapperResultados;


/**
 * 
 */
public class GuionLlamadasManagerImpl extends AbstractManagerJdbcTemplateInvoke implements GuionLlamadasManager {

	

    /**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unchecked")
	public PagedList obtenerGuionLlamadas(String pv_dsunieco_i, String pv_dselemento_i, String pv_dsguion_i, String pv_dsproceso_i, String pv_dstipgui_i,String pv_dsramo_i,int start, int limit) throws ApplicationException {
       HashMap map = new HashMap();
        map.put("pv_dsunieco_i", pv_dsunieco_i);
        map.put("pv_dselemento_i", pv_dselemento_i);
        map.put("pv_dsguion_i", pv_dsguion_i);
        map.put("pv_dsproceso_i", pv_dsproceso_i);
        map.put("pv_dstipgui_i", pv_dstipgui_i);
        map.put("pv_dsramo_i", pv_dsramo_i);

        String endpointName = "OBTENER_GUION_LLAMADAS";
        return pagedBackBoneInvoke(map, endpointName, start, limit);
    }
	

	@SuppressWarnings("unchecked")
	public String guardarGuionLlamadas(String pv_cdunieco_i, String pv_cdramo_i, String pv_cdelemento_i, String pv_cdproceso_i, String pv_cdguion_i, String pv_dsguion_i, String pv_cdtipguion_i, String pv_indinicial_i, String pv_status_i) throws ApplicationException {

		int _pv_indinicial_i= (pv_indinicial_i=="true")?0:1;
        HashMap map = new HashMap();
        map.put("pv_cdunieco_i", ConvertUtil.nvl(pv_cdunieco_i));
        map.put("pv_cdramo_i", ConvertUtil.nvl(pv_cdramo_i));
        map.put("pv_cdelemento_i", ConvertUtil.nvl(pv_cdelemento_i));
        map.put("pv_cdproceso_i", ConvertUtil.nvl(pv_cdproceso_i));
        map.put("pv_cdguion_i", ConvertUtil.nvl(pv_cdguion_i));
        map.put("pv_dsguion_i", pv_dsguion_i);
        map.put("pv_cdtipguion_i", ConvertUtil.nvl(pv_cdtipguion_i));
        map.put("pv_indinicial_i", _pv_indinicial_i);
        map.put("pv_status_i", pv_status_i);
        WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_GUION_LLAMADAS");
        return res.getMsgText();
    }
	
	@SuppressWarnings("unchecked")
	public PagedList obtenerDialogoLlamadas(String pv_cdunieco_i, String pv_cdramo_i, String pv_cdelemento_i, String pv_cdproceso_i, String pv_cdguion_i,String pv_cddialogo_i,int start, int limit) throws ApplicationException {
	       HashMap map = new HashMap();
	        map.put("pv_cdunieco_i", pv_cdunieco_i);
	        map.put("pv_cdramo_i", pv_cdramo_i);
	        map.put("pv_cdelemento_i", pv_cdelemento_i);
	        map.put("pv_cdproceso_i", pv_cdproceso_i);
	        map.put("pv_cdguion_i", pv_cdguion_i);
	        map.put("pv_cddialogo_i", pv_cddialogo_i);

	        String endpointName = "OBTENER_DIALOGO_LLAMADAS";
	        return pagedBackBoneInvoke(map, endpointName, start, limit);
	    }
	
	@SuppressWarnings("unchecked")
	public String guardarDialogoLlamadas(String pv_cdunieco_i, String pv_cdramo_i, String pv_cdelemento_i, String pv_cdproceso_i, String pv_cdguion_i, String pv_cddialogo_i, String pv_dsdialogo_i, String pv_cdsecuencia_i, String pv_ottapval_i) throws ApplicationException {

        HashMap map = new HashMap();
        map.put("pv_cdunieco_i", pv_cdunieco_i);
        map.put("pv_cdramo_i", pv_cdramo_i);
        map.put("pv_cdelemento_i", pv_cdelemento_i);
        map.put("pv_cdproceso_i", pv_cdproceso_i);
        map.put("pv_cdguion_i", pv_cdguion_i);
        map.put("pv_cddialogo_i", pv_cddialogo_i);
        map.put("pv_dsdialogo_i", pv_dsdialogo_i);
        map.put("pv_cdsecuencia_i", pv_cdsecuencia_i);
        map.put("pv_ottapval_i", pv_ottapval_i);
        WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_DIALOGO_LLAMADAS");
        return res.getMsgText();
    }
	
}