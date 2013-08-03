package mx.com.aon.portal.service.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.Converter;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.MecanismoAlertaVO;
import mx.com.aon.portal.service.MecanismosAlertasManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.ConvertUtil;
import mx.com.aon.portal.util.UserSQLDateConverter;

/**
 * Clase que implementa la interface AlertasUsuarioManager para los servicios de Alertas de usuarios.
 *
 */
public class MecanismosAlertasManagerImpl extends AbstractManagerJdbcTemplateInvoke implements MecanismosAlertasManager {
	
	/**
	 * Metodo que busca y obtiene un conjunto de registros de alertas de usuarios.
	 * Invoca al Store Procedure PKG_ALERTAS.P_ALERTAS_USUARIO
	 * 
	 * @param dsUsuario
	 * @param dsNombre
	 * @param dsProceso
	 * @param dsTipRam
	 * @param dsUniEco
	 * @param dsRol
	 * @param start
	 * @param limit
	 * 
	 * @return PagedList
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public PagedList buscarMensajesAlertas(String cdUsuario, String cdRol,
			String cdElemento, String cdProceso, String fecha, String cdRamo, int start, int limit)
			throws ApplicationException {
		Converter converter = new UserSQLDateConverter("");
		HashMap map = new HashMap();
		map.put("PV_CDUSUARIO_i", cdUsuario);
		map.put("pv_CDSISROL_i", cdRol);
		map.put("pv_CDELEMENTO_i", ConvertUtil.nvl(cdElemento));
		map.put("pv_CDPROCESO_i", cdProceso);
		map.put("pv_FEFECHA_i", converter.convert(java.util.Date.class, fecha));
		//map.put("pv_FEFECHA_i", ConvertUtil.convertToDate(fecha));
		map.put("pv_CDRAMO_i", cdRamo);


        return pagedBackBoneInvoke(map, "OBTIENE_MENSAJES_ALERTA_USUARIO", start, limit);
	}
	

	@SuppressWarnings("unchecked")
	public List<MecanismoAlertaVO> buscarMensajesAlertasPantalla(String cdUsuario, String cdRol,
			String cdElemento, String cdProceso, String fecha, String cdRamo)
			throws ApplicationException {

		HashMap map = new HashMap();
		map.put("PV_CDUSUARIO_i", cdUsuario);
		map.put("pv_CDSISROL_i", cdRol);
		map.put("pv_CDELEMENTO_i", cdElemento);
		map.put("pv_CDPROCESO_i", cdProceso);
		map.put("pv_FEFECHA_i", fecha);
		map.put("pv_CDRAMO_i", cdRamo);

        return getAllBackBoneInvoke(map, "OBTIENE_MENSAJES_ALERTA_PANTALLA_USUARIO");
	}
	
	
}
