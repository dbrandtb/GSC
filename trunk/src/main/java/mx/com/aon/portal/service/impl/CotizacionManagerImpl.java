package mx.com.aon.portal.service.impl;

import static mx.com.aon.portal.dao.CotizacionDAO.GENERAR_COTIZACION_MASIVA;
import static mx.com.aon.portal.dao.CotizacionDAO.NUMERO_PROCESO_COTIZACION;
import static mx.com.aon.portal.dao.CotizacionDAO.RUTA_COTIZACION;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.service.CotizacionManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.ConvertUtil;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.utils.FTPUtil;
import mx.com.aon.utils.ServerConfigurationException;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;

public class CotizacionManagerImpl extends AbstractManagerJdbcTemplateInvoke implements CotizacionManager {

    private static Logger logger = Logger.getLogger(CotizacionManagerImpl.class);

    private FTPUtil ftp;
    
    
    public PagedList buscarCotizacionesMasivas (String pv_cdelement,String pv_asegura , String pv_cdramo_i, String pv_cdlayout, String pv_fedesde_i, String pv_fehasta_i ,int start, int limit) throws ApplicationException {

        HashMap map = new HashMap();
        map.put("pv_cdelement", pv_cdelement);
        map.put("pv_asegura",pv_asegura );
        map.put("pv_cdramo_i", pv_cdramo_i);
        map.put("pv_cdlayout", pv_cdlayout);
        map.put("pv_fedesde_i", pv_fedesde_i);
        map.put("pv_fehasta_i", pv_fehasta_i);
        
        String endpointName = "BUSCAR_COTIZACIONES_MASIVAS";
        return pagedBackBoneInvoke(map, endpointName, start, limit);
    }

    public String aprobarCotizacion(String pv_cdusuari,String pv_cdunieco, String pv_cdramo, String pv_estado, String pv_nmpoliza, 
    		String pv_nmsituac, String pv_nmsuplem, String pv_cdelement, String pv_cdcia, String pv_cdplan, String pv_cdperpag, String pv_cdperson, String pv_fecha) throws ApplicationException {
        HashMap map = new HashMap();
        
        map.put("pv_cdusuari", pv_cdusuari);
        map.put("pv_cdunieco", pv_cdunieco);
        map.put("pv_cdramo", pv_cdramo);
        map.put("pv_estado", pv_estado);
        map.put("pv_nmpoliza", pv_nmpoliza);
        map.put("pv_nmsituac", pv_nmsituac);
        map.put("pv_nmsuplem", pv_nmsuplem);
        map.put("pv_cdelement", pv_cdelement);
        map.put("pv_cdcia", pv_cdcia);
        map.put("pv_cdplan", pv_cdplan);
        map.put("pv_cdperpag", pv_cdperpag);
        map.put("pv_cdperson", pv_cdperson);
        map.put("pv_fecha", pv_fecha);
                
        String endpointName = "APROBAR_COTIZACION";
        WrapperResultados res =  returnBackBoneInvoke(map,endpointName);
        return res.getMsgText();
    }

    public String borrarCotizacion(String pv_cdusuari, String pv_cdunieco, String pv_cdramo, String pv_estado, String pv_nmpoliza, 
    		String pv_nmsituac, String pv_nmsuplem, String pv_cdelement) throws ApplicationException {
        HashMap map = new HashMap();
        
        map.put("pv_cdusuari", pv_cdusuari);
        map.put("pv_cdunieco", pv_cdunieco);
        map.put("pv_cdramo", pv_cdramo);
        map.put("pv_estado", pv_estado);
        map.put("pv_nmpoliza", pv_nmpoliza);
        map.put("pv_nmsituac", pv_nmsituac);
        map.put("pv_nmsuplem", pv_nmsuplem);
        map.put("pv_cdelement", pv_cdelement);
        
        String endpointName = "BORRAR_COTIZACION";
        WrapperResultados res =  returnBackBoneInvoke(map,endpointName);
        return res.getMsgText();
    }

    /*
     * (non-Javadoc)
     * @see mx.com.aon.portal.service.CotizacionManager#cotizarMasiva(java.lang.String, java.lang.String, java.lang.String, java.io.File)
     */
    public String cotizarMasiva(String codigoElemento, String codigoProducto, String fileName, File file) throws ApplicationException, ServerConfigurationException {
    	
    	WrapperResultados respuesta = returnResult(new HashMap<String, String>(), RUTA_COTIZACION);
    	
    	logger.debug("RUTA: " + respuesta.getItemMap().get("RUTA").toString());
    	
    	ftp.storeFTP(respuesta.getItemMap().get("RUTA").toString(), fileName, file);
    	
    	WrapperResultados respuestaProceso = returnResult(new HashMap<String, String>(), NUMERO_PROCESO_COTIZACION);
    	
    	
    	Map map = new HashMap();
    	
    	map.put("pv_cdelemen_i", codigoElemento);
    	map.put("pv_idproces_i", respuestaProceso.getItemMap().get("ID_PROCESO"));
    	map.put("pv_filename_i", fileName);
    	map.put("pv_producto_i", codigoProducto);
    	
    	Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();

		map.put("pv_fechapro_i", ConvertUtil.convertToDate(DateFormatUtils.format(date, "dd/MM/yyyy")));
    	
		voidReturnBackBoneInvoke(map, GENERAR_COTIZACION_MASIVA);
		
		if (respuestaProceso.getItemMap().get("ID_PROCESO") == null)
			return "";
		else
			return (String)respuestaProceso.getItemMap().get("ID_PROCESO");
    }

	/**
	 * @return the ftp
	 */
	public FTPUtil getFtp() {
		return ftp;
	}

	/**
	 * @param ftp the ftp to set
	 */
	public void setFtp(FTPUtil ftp) {
		this.ftp = ftp;
	}
    
}
