package mx.com.aon.portal.service.impl;

import mx.com.aon.core.ApplicationException;

import mx.com.aon.portal.model.PolizasRenovacionVO;
import mx.com.aon.portal.service.SeleccionPolizasRenovacionManager;
import mx.com.aon.portal.util.WrapperResultados;

import org.apache.log4j.Logger;

import java.util.HashMap;

/**
 * Clase que implementa los servicios de la interface SeleccionPolizasRenovacionManager.
 * 
 * @extends AbstractManager
 *
 */
public class SeleccionPolizasRenovacionManagerImpl extends AbstractManager implements SeleccionPolizasRenovacionManager {
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(SeleccionPolizasRenovacionManagerImpl.class);
	
	/**
	 * Metodo que realiza la seleccion las polizas que son candidatas para el proceso de renovacion.
	 * Invoca al Store Procedure PKG_RENOVA.P_SELECCION_POLIZAS.
	 * 
	 * @param polizasVO
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public String ejecutarSeleccionPolizasParaRenovacion(PolizasRenovacionVO polizasVO) throws ApplicationException{
		HashMap map = new HashMap();
		
		map.put("p_CdElemento",polizasVO.getCdElemento());
        map.put("p_CdTipRam",polizasVO.getCdTipRam());
        map.put("p_CDUniEco",polizasVO.getCdUniEco());
		map.put("p_CDRamo",polizasVO.getCdRamo());
		map.put("p_NmPolde",polizasVO.getRpDesde());
        map.put("p_NmPolHasta",polizasVO.getRpHasta());
        map.put("p_DiasVen",polizasVO.getDiasVencim());     
        
        WrapperResultados res =  returnBackBoneInvoke(map,"EJECUTAR_SELECCION_POLIZAS_RENOVACION");
        return res.getMsgText();
	}
}
