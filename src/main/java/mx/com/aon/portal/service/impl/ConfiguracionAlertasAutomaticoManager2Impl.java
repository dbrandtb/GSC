
package mx.com.aon.portal.service.impl;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.ConfiguracionAlertasAutomaticoVO;
import mx.com.aon.portal.service.ConfiguracionAlertasAutomaticoManager2;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.*;
import oracle.jdbc.driver.OracleTypes;

import org.apache.commons.beanutils.Converter;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.SqlParameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Clase que sirve para dar servicios al action que lo invoca.
 * 
 * @implements ConfiguracionAlertasAutomaticoManager
 * 
 * @extends AbstractManager
 */

public class ConfiguracionAlertasAutomaticoManager2Impl extends AbstractManagerJdbcTemplateInvoke implements ConfiguracionAlertasAutomaticoManager2 {

	/**
	 * Tabla lógica desde la cual se leerán datos para obtener los valores de Variables para
	 * la pantalla de Agregar/Editar
	 */
	private static String TABLA_VARALERTA = "VARALERTA";

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(EstructuraManagerImpl.class);
	
	
	
	/**
     * Metodo que realiza la actualizacion de una configuracion de alertas automatico editada.
     * Invoca el Store Procedure PKG_ALERTAS.P_GUARDA_CONFALERTA.
     * 
     * @param configuracionAlertasAutomaticoVO
     * 
     * @return Mensaje asociado en respuesta a la ejecucion del servicio.
     * 
     * @throws ApplicationException
     */
    @SuppressWarnings("unchecked")
	public String guardarConfiguracionAlertasAutomatico(ConfiguracionAlertasAutomaticoVO configuracionAlertasAutomaticoVO) throws ApplicationException{

    	Converter converter = new UserSQLDateConverter("");	
    	HashMap map = new HashMap();
	   
	        map.put("pv_cdidunico_i", ConvertUtil.nvl(configuracionAlertasAutomaticoVO.getCdIdUnico()));
	        map.put("pv_usuario_i",configuracionAlertasAutomaticoVO.getDsUsuario());
	        map.put("pv_cve_cliente_i",ConvertUtil.nvl(configuracionAlertasAutomaticoVO.getCdCliente()));
	        map.put("pv_nombre_alerta_i",configuracionAlertasAutomaticoVO.getDsAlerta());
			map.put("pv_cve_proceso_i",ConvertUtil.nvl(configuracionAlertasAutomaticoVO.getCdProceso()));
			map.put("pv_cve_temporalidad_i",configuracionAlertasAutomaticoVO.getCdTemporalidad());
			map.put("pv_mensaje_i",configuracionAlertasAutomaticoVO.getDsMensaje());
			map.put("pv_frecuencia_i",configuracionAlertasAutomaticoVO.getNmFrecuencia());
			map.put("pv_fec_inic_i", converter.convert(java.util.Date.class, configuracionAlertasAutomaticoVO.getFeInicio()));
			//map.put("pv_fec_inic_i",ConvertUtil.convertToDate(configuracionAlertasAutomaticoVO.getFeInicio()));  //como es fecha hay que convertirla a DATE
			map.put("pv_dias_antic_i",ConvertUtil.nvl(configuracionAlertasAutomaticoVO.getNmDiasAnt()));			
			map.put("pv_duracion_i",ConvertUtil.nvl(configuracionAlertasAutomaticoVO.getNmDuracion()));
			map.put("pv_mandar_email_i",ConvertUtil.nvl(configuracionAlertasAutomaticoVO.getFgMandaEmail()));
			map.put("pv_popup_i",ConvertUtil.nvl(configuracionAlertasAutomaticoVO.getFgMandaPantalla()));
			map.put("pv_pantalla_i",ConvertUtil.nvl(configuracionAlertasAutomaticoVO.getFgPermPantalla()));
			map.put("pv_rol_i",configuracionAlertasAutomaticoVO.getCdRol());
			map.put("pv_cve_ramo_i",configuracionAlertasAutomaticoVO.getCdTipRam());
			map.put("pv_cve_aseguradora_i",ConvertUtil.nvl(configuracionAlertasAutomaticoVO.getCdUniEco()));
			map.put("pv_cve_producto_i",ConvertUtil.nvl(configuracionAlertasAutomaticoVO.getCdProducto()));
			map.put("pv_region_i",configuracionAlertasAutomaticoVO.getDsRegion());

            WrapperResultados res =  returnBackBoneInvoke(map,"INSERTA_CONF_ALERTA_AUTO");
            return res.getMsgText();

    }
    
  
}
