package mx.com.aon.portal.service.impl;

import java.util.HashMap;

import org.apache.commons.beanutils.Converter;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.AlertaUsuarioVO;
import mx.com.aon.portal.model.EjecutivoCuentaVO;
import mx.com.aon.portal.service.AlertasUsuarioManager;
import mx.com.aon.portal.service.EjecutivoManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.UserSQLDateConverter;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.portal.util.ConvertUtil;

/**
 * Clase que implementa la interface AlertasUsuarioManager para los servicios de Alertas de usuarios.
 *
 */
public class EjecutivoManagerImpl extends AbstractManagerJdbcTemplateInvoke implements EjecutivoManager {
	
	
	@SuppressWarnings("unchecked")
	public String agregarEjecutivo(String cdEjecutivo, String cdPerson,	String fechaInicial, String fechaFinal, String status) throws ApplicationException {
		HashMap map = new HashMap ();
		map.put("pv_cdagente_i", cdEjecutivo);
		map.put("pv_cdperson_i", cdPerson);
		map.put("pv_fedesde_i", ConvertUtil.convertToDate(fechaInicial));
		map.put("pv_fehasta_i", ConvertUtil.convertToDate(fechaFinal));
		map.put("pv_swactivo_i", status);

		WrapperResultados res = returnBackBoneInvoke(map, "MANTENIMIENTO_EJECUTIVOS_CUENTA_GUARDAR_NUEVO");
		return res.getMsgText();
	}

	@SuppressWarnings("unchecked")
	public String agregarGuardarEjecutivoCuenta(EjecutivoCuentaVO ejecutivoCuentaVO)throws ApplicationException {
		HashMap map = new HashMap();
		
		map.put("p_cve_agente",ejecutivoCuentaVO.getCdAgente());
        map.put("p_cve_cliente",ejecutivoCuentaVO.getCdPerson());
        map.put("p_cve_estado",ejecutivoCuentaVO.getCdEstado());
       
        Converter converter = new UserSQLDateConverter("");
        map.put("p_fecha_inic",converter.convert(java.util.Date.class, ejecutivoCuentaVO.getFeInicio())); 
		map.put("p_fecha_fin",converter.convert(java.util.Date.class, ejecutivoCuentaVO.getFeFin()));
        
		//map.put("p_fecha_inic",ConvertUtil.convertToDate(ejecutivoCuentaVO.getFeInicio()));
		//map.put("p_fecha_fin",ConvertUtil.convertToDate(ejecutivoCuentaVO.getFeFin()));
        map.put("p_cve_tipram",ejecutivoCuentaVO.getCdTipRam());
        map.put("p_cve_ramo",ejecutivoCuentaVO.getCdRamo());
        map.put("p_linea_agen",ejecutivoCuentaVO.getCdLinCta());
		map.put("p_niv_post",ejecutivoCuentaVO.getSwNivelPosterior());
        map.put("p_grupo",ejecutivoCuentaVO.getCdGrupo());
        map.put("p_cdelemento",ejecutivoCuentaVO.getCdElemento());
        map.put("p_cdtipage",ejecutivoCuentaVO.getCdTipage());
        map.put("p_accion", ejecutivoCuentaVO.getAccion());
        
        WrapperResultados res =  returnBackBoneInvoke(map,"INSERTA_GUARDA_EJECUTIVO_CUENTA");
        return res.getMsgText();
	}
	
	public String guardarAtributos(String cdEjecutivo, String cdAtribu,String otValor) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_cdagente_i", cdEjecutivo);
		map.put("pv_cdatribu_i", cdAtribu);
		map.put("pv_otvalor_i", otValor);

		WrapperResultados res = returnBackBoneInvoke(map, "MANTENIMIENTO_EJECUTIVOS_CUENTA_ATRIBUTOS_GUARDAR");

		return res.getMsgText();
	

}}
