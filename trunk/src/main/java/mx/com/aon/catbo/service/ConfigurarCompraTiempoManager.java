package mx.com.aon.catbo.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.CalendarioVO;
import mx.com.aon.catbo.model.ConfigurarCompraTiempoVO;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.service.PagedList;

/**
 * Created by IntelliJ IDEA.
 * User: gabrielforradellas
 * Date: Sep 2, 2008
 * Time: 12:12:28 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ConfigurarCompraTiempoManager {

    public String guardarCompraTiempo(String pv_cdproceso_i, String pv_cdnivatn_i, String pv_nmcant_desde_i , String pv_nmcant_hasta_i,String pv_tunidad_i , String pv_nmvecescompra_i) throws ApplicationException;

    public String borrarCompraTiempo(String pv_cdproceso_i, String pv_cdnivatn_i) throws ApplicationException;
    
    public PagedList obtieneCompraTiempoDisponible(String pv_cdproceso_i, String pv_cdnivatn_i,String pv_nmcaso_i, int start, int limit) throws ApplicationException;
    
       
    public ConfigurarCompraTiempoVO obtieneConfigCompraTiempo(String pv_cdproceso_i, String pv_cdnivatn_i) throws ApplicationException;
    
    public String validaSatatusCaso(String pv_nmcaso_i) throws ApplicationException;  
 
    
}
