package mx.com.aon.portal.service.impl;

import mx.com.aon.portal.service.AsociarOrdenTrabajoManager;
import mx.com.aon.portal.model.AsociarOrdenTrabajoVO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.log4j.Logger;
import java.util.HashMap;

/**
 * Clase que implementa la interfaz AsociarOrdenTrabajoManager.
 * 
 * @extends AbstractManager
 *
 */
public class AsociarOrdenTrabajoManagerImpl extends AbstractManager implements AsociarOrdenTrabajoManager {

    @SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(AsociarOrdenTrabajoManagerImpl.class);
    
    /**Metodo que busca y obtiene un unico registro con datos de formato por cuenta.
     * Invoca al Store Procedure PKG_ORDENT.P_OBTIENE_FORMATOXCTA.
	 * 
	 * @param cdAsocia
	 * 
	 * @return AsociarOrdenTrabajoVO
	 * 
	 * @throws ApplicationException
	 */
    @SuppressWarnings("unchecked")
	public AsociarOrdenTrabajoVO obtenerFormatoxCuenta(String cdAsocia)	throws ApplicationException {
    	HashMap map = new HashMap();
    	map.put("pv_cdasocia_i", cdAsocia);
    	return   (AsociarOrdenTrabajoVO) getBackBoneInvoke(map,"OBTIENE_FORMATOXCTA");
	}
    
    /**
	 * Metodo que realiza la actualizacion o insercion de una asociacion de orden de trabajo.
	 * Invoca al Store Procedure PKG_ORDENT.P_GUARDA_ASOCIACION.
	 * 
	 * @param asociarOrdenTrabajoVO
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
    @SuppressWarnings("unchecked")
	public String guardarAsociacionOrdenTrabajo(AsociarOrdenTrabajoVO asociarOrdenTrabajoVO) throws ApplicationException {
       
    	HashMap map = new HashMap();       	
       
        map.put("pv_cdasocia_i",asociarOrdenTrabajoVO.getCdasocia());
        map.put("pv_cdforord_i",asociarOrdenTrabajoVO.getCdforord());
        map.put("pv_cdproces_i",asociarOrdenTrabajoVO.getCdproces());       
        map.put("pv_cdelemento_i",asociarOrdenTrabajoVO.getCdelemento());
        map.put("pv_cdperson_i",asociarOrdenTrabajoVO.getCdperson());
        map.put("pv_cdunieco_i",asociarOrdenTrabajoVO.getCdunieco());        
        map.put("pv_cdramo_i",asociarOrdenTrabajoVO.getCdramo());
        map.put("pv_cdfolioaon_i",asociarOrdenTrabajoVO.getCdfolioaon());
        map.put("pv_cdfoliocia_i",asociarOrdenTrabajoVO.getCdfoliocia());
        map.put("pv_dsformaon_i",asociarOrdenTrabajoVO.getDsformaon());
        map.put("pv_dsformcia_i",asociarOrdenTrabajoVO.getDsformcia());        
        map.put("pv_cdfolaonini_i",asociarOrdenTrabajoVO.getCdfolaonini());
        map.put("pv_cdfolaonfin_i",asociarOrdenTrabajoVO.getCdfolaonfin());
        map.put("pv_cdfolciaini_i",asociarOrdenTrabajoVO.getCdfolciaini());
        map.put("pv_cdfolciafin_i",asociarOrdenTrabajoVO.getCdfolciafin());
        
        WrapperResultados res =  returnBackBoneInvoke(map,"GUARDA_ASOCIACION_ORDEN_TRABAJO");
        return res.getMsgText();
    }
    
}
