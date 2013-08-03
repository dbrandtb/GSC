package mx.com.aon.catbo.service.impl;

import org.apache.log4j.Logger;

import mx.com.aon.catbo.service.CombosManager2;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;
import mx.com.aon.portal.util.ConvertUtil;


import java.util.HashMap;
import java.util.List;

public class CombosManager2Impl extends AbstractManagerJdbcTemplateInvoke implements CombosManager2 {

    private static Logger logger = Logger.getLogger(CombosManager2Impl.class);

    
    public List comboObtieneFormatos () throws ApplicationException{
        HashMap map = new HashMap();

        map.put("pv_cdtabla_i","CFORMATO");
        map.put("pv_cdidioma_i","1");
        map.put("pv_cdregion_i","1");
        String endpointName = "COMBO_OBTIENE_FORMATOS";
        return  getAllBackBoneInvoke(map, endpointName);

    }

    @SuppressWarnings("unchecked")
	public List comboObtieneUsuarios (String pv_cdmatriz_i, String pv_cdmodulo_i,String pv_cdnivatn_i) throws ApplicationException{
        HashMap map = new HashMap();

        map.put("pv_cdmatriz_i", ConvertUtil.nvl(pv_cdmatriz_i));
        map.put("pv_cdmodulo_i",pv_cdmodulo_i);        
        map.put("pv_cdnivatn_i", ConvertUtil.nvl(pv_cdnivatn_i));
        
        String endpointName = "COMBO_USUARIO_REEMPLAZO";
        return  getAllBackBoneInvoke(map, endpointName);

    }
    
    
    
    public List comboObtieneTablas () throws ApplicationException{
        HashMap map = new HashMap();

        //map.put("pv_cdtabla_i","TTAPTABL");  //no se si es TTAPTABL
        map.put("pv_cdtabla_i","");  
        
        String endpointName = "COMBO_OBTIENE_TABLA";
        return  getAllBackBoneInvoke(map, endpointName);

    }
    
    public List comboObtieneAlgoritmos () throws ApplicationException{
        HashMap map = new HashMap();
   
        String endpointName = "COMBO_OBTIENE_ALGORITMOS";
        return  getAllBackBoneInvoke(map, endpointName);

    }

    public List comboObtieneTipoGuion () throws ApplicationException{
        HashMap map = new HashMap();
   
        String endpointName = "COMBO_OBTIENE_TIPO_GUION";
        return  getAllBackBoneInvoke(map, endpointName);

    }
    
    public List comboObtieneTipoFax () throws ApplicationException{
        HashMap map = new HashMap();
   
        String endpointName = "COMBO_OBTIENE_TIPO_FAX";
        return  getAllBackBoneInvoke(map, endpointName);

    }

	public List comboUsuarioResponsable(String pv_nmconfig_i)
			throws ApplicationException {
	    	 HashMap map = new HashMap();

	        map.put("pv_nmconfig_i",pv_nmconfig_i);
	        
	        String endpointName = "COMBO_USUARIO_RESPONSABLE";
	        return  getAllBackBoneInvoke(map, endpointName);
	}
	
	public List comboCargaArchivos()
	throws ApplicationException {
	 HashMap map = new HashMap();

       
    String endpointName = "COMBO_CARGA_ARCHIVOS";
    return  getAllBackBoneInvoke(map, endpointName);
}
	public List comboCargaArchivos2()
	throws ApplicationException {
	 HashMap map = new HashMap();

       
    String endpointName = "COMBO_CARGA_ARCHIVOS2";
    return  getAllBackBoneInvoke(map, endpointName);
}
	
	
	 public List comboRamos2(String pv_cdunieco_i, String pv_cdelemento_i, String pv_cdramo_i) throws ApplicationException {
    	 HashMap map = new HashMap();
         map.put("pv_cdunieco_i", pv_cdunieco_i);
         map.put("pv_cdelemento_i", pv_cdelemento_i);
         map.put("pv_cdramo_i", pv_cdramo_i);
       // return getAllBackBoneInvoke(map,"OBTIENE_RAMOS_2"); 
         
         String endpointName = "OBTIENE_RAMOS_2";
         
         return  getAllBackBoneInvoke(map, endpointName);
    } 
	
	 public List obtieneTabla()
		throws ApplicationException {
		 HashMap map = new HashMap();

	       
	    String endpointName = "OBTIENE_TABLA";
	    return  getAllBackBoneInvoke(map, endpointName);
	}
	 
	 public List obtienePoliza()
		throws ApplicationException {
		 HashMap map = new HashMap();

	       
	    String endpointName = "OBTIENE_TABLA";
	    return  getAllBackBoneInvoke(map, endpointName);
	}
	 
	 
	 public List ComboObtienePolizaAseguradora2 (String p_cve_aseguradora, String  pv_cdelemento ) throws ApplicationException{
	        HashMap map = new HashMap();
	        map.put("p_cve_aseguradora",p_cve_aseguradora);
	        map.put("pv_cdelemento", pv_cdelemento);
	        
	        logger.debug("ComboObtienePolizaAseguradora2");
	        logger.debug("p_cve_aseguradora="+p_cve_aseguradora );
	        logger.debug("pv_cdelemento="+ pv_cdelemento );
	        
	        String endpointName = "OBTIENE_POLIZA_POR_ASEGURADORA2";
	        return  getAllBackBoneInvoke(map, endpointName);
	 }
		
	


}
