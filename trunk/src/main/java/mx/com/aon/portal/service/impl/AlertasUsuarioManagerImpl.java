package mx.com.aon.portal.service.impl;

import static mx.com.aon.portal.dao.AlertaDAO.OBTIENE_ALERTAS_EMAIL;
import static mx.com.aon.portal.util.UserSQLDateConverter.MASCARA_DATE_ddMMYYYY_B;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.AlertaUsuarioVO;
import mx.com.aon.portal.model.EmailVO;
import mx.com.aon.portal.service.AlertasUsuarioManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.ConvertUtil;
import mx.com.aon.portal.util.UserSQLDateConverter;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.utils.MailUtil;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.time.DateFormatUtils;

/**
 * Clase que implementa la interface AlertasUsuarioManager para los servicios de Alertas de usuarios.
 *
 */
public class AlertasUsuarioManagerImpl extends AbstractManagerJdbcTemplateInvoke implements AlertasUsuarioManager {
	
	private MailUtil mailUtil;
	
	/**
	 * @return the mailUtil
	 */
	public MailUtil getMailUtil() {
		return mailUtil;
	}

	/**
	 * @param mailUtil the mailUtil to set
	 */
	public void setMailUtil(MailUtil mailUtil) {
		this.mailUtil = mailUtil;
	}

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
	public PagedList buscarAlertasUsuario(String dsUsuario, String dsNombre,
			String dsProceso, String dsTipRam, String dsUniEco, String dsRol, int start, int limit)
			throws ApplicationException {

		HashMap map = new HashMap();
		map.put("pv_dsusuario_i", dsUsuario);
		map.put("pv_dsnombre_i", dsNombre);
		map.put("pv_dsproces_i", dsProceso);
		map.put("pv_dstipram_i", dsTipRam);
		map.put("pv_dsunieco_i", dsUniEco);
		map.put("pv_dsrol_i", dsRol);


        return pagedBackBoneInvoke(map, "OBTIENE_ALERTAS_USUARIO", start, limit);
	}
	
	/**
	 * Metodo que busca y obtiene un unico registro de alertas de usuarios.
	 * Invoca al Store Procedure PKG_ALERTAS.P_OBTIENE_ALERTA_USUARIO_REG
	 * 
	 * @param cdIdUnico
	 * @param cdIdConfAlerta
	 * 
	 * @return AlertaUsuarioVO
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public AlertaUsuarioVO getAlertaUsuario(String cdIdUnico,
			String cdIdConfAlerta) throws ApplicationException {

		HashMap map = new HashMap();
		map.put("pv_cdidunico_i", cdIdUnico);
		map.put("pv_cdidconfalerta_i", cdIdConfAlerta);
		
        return (AlertaUsuarioVO)getBackBoneInvoke(map, "OBTIENE_ALERTA_USUARIO_REG");
	}
	
	/**
	 * Metodo que permite insertar una nueva configuracion de alertas automatico o actualizar
	 *  una editada por el usuario.
	 *  Invoca al Store Procedure PKG_ALERTAS.P_GUARDA_ALERTA.
	 *  
	 * @param cdIdUnico
	 * @param cdIdConfAlerta
	 * @param cdElemento
	 * @param cdUsuario
	 * @param cdUniEco
	 * @param nmPoliza
	 * @param nmRecibo
	 * @param cdProceso
	 * @param feUltimoEvento
	 * @param feSiguienteEvento
	 * @param feVencimiento
	 * @param dsCorreo
	 * @param dsMensaje
	 * @param fgMandarPantalla
	 * @param fgPermanecePantalla
	 * @param nmFrecuencia
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public String guardarAlertaUsuario(String cdIdUnico, String cdIdConfAlerta, String cdElemento, 
			String cdUsuario, String cdUniEco, String nmPoliza,
			String nmRecibo, String cdProceso, String feUltimoEvento,
			String feSiguienteEvento, String feVencimiento, String dsCorreo,
			String dsMensaje, String fgMandarPantalla,
			String fgPermanecePantalla, String nmFrecuencia)
			throws ApplicationException {
        
        HashMap map = new HashMap();
		map.put("pv_cdidunico_i", ConvertUtil.nvl(cdIdUnico));
		map.put("pv_cdidconfalerta_i", ConvertUtil.nvl(cdIdConfAlerta));
		map.put("pv_cve_aseguradora_i", ConvertUtil.nvl(cdUniEco));
		map.put("pv_poliza_i", ConvertUtil.nvl(nmPoliza));
		map.put("pv_recibo_i", ConvertUtil.nvl(nmRecibo));
		map.put("pv_cve_proceso_i", ConvertUtil.nvl(cdProceso));
		map.put("pv_fec_ult_envio_i", ConvertUtil.convertToDate(feUltimoEvento));
		map.put("pv_fec_sig_envio_i", ConvertUtil.convertToDate(feSiguienteEvento));
		map.put("pv_fec_vencimiento_i", ConvertUtil.convertToDate(feVencimiento));
		map.put("pv_correo_i", dsCorreo);
		map.put("pv_mensaje_i", dsMensaje);
		map.put("pv_mandar_pantalla_i", fgMandarPantalla);
		map.put("pv_perm_pantalla_i", fgPermanecePantalla);
		map.put("pv_cve_frecuencia_i", ConvertUtil.nvl(nmFrecuencia));
		map.put("pv_cdelemento_i", cdElemento);
		map.put("pv_cdusuario_i", cdUsuario);


        WrapperResultados res = returnBackBoneInvoke(map, "GUARDA_ALERTA_USUARIO");
		return res.getMsgText();
	}
	
	@SuppressWarnings("unchecked")
	public String borrarAlerta(String cdIdUnico, String cdIdConfAlerta) throws ApplicationException {
		HashMap map = new HashMap();
        map.put("pv_cdidunico_i", cdIdUnico);
		map.put("pv_cdidconfalerta_i", cdIdConfAlerta);
        WrapperResultados res =  returnBackBoneInvoke(map,"BORRA_ALERTA");	
        return res.getMsgText();
	}

	/*
	 * (non-Javadoc)
	 * @see mx.com.aon.portal.service.AlertasUsuarioManager#getAlertasEmail()
	 */
	public void getAlertasEmail() throws ApplicationException {
		
		/*Map<String, Object> map = new HashMap<String, Object>();
		
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		
		Converter convert = new UserSQLDateConverter(MASCARA_DATE_ddMMYYYY_B);

		map.put("pv_FEFECHA_i", convert.convert(java.sql.Date.class,DateFormatUtils.format(date, MASCARA_DATE_ddMMYYYY_B)));
		
		
		List<EmailVO> alertas = getAllBackBoneInvoke(map, OBTIENE_ALERTAS_EMAIL);
		
		EmailVO emal = new EmailVO();
		emal.setAsunto("ASDAD");
		emal.setTo("adolfo.gonzalez@biosnettcs.com");
		emal.setMensaje("sfasfsaf");
		mailUtil.setEmail(emal);
		try {
			mailUtil.send();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (EmailVO email : alertas) {
			mailUtil.setEmail(email);
			try {
				mailUtil.send();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		System.out.println("EXECUTING QUARTZ JOB");*/
	}	
}