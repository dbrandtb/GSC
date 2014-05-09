package mx.com.gseguros.portal.general.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.MimeMessage;

import mx.com.gseguros.portal.general.service.MailService;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailServiceImpl implements MailService {
	
	private final static Log logger = LogFactory.getLog(MailServiceImpl.class);
	
	private JavaMailSender mailSender;
	
	private SimpleMailMessage defaultMailMessage;
	
	private String from;
	
	private String fromAlias;
	
	
	@Override
	public boolean enviaCorreo(String[] to, String[] cc, String[] bcc, String asunto, String mensaje, String[] rutasAdjuntos) {
		
		// Se genera una lista de objetos File a partir del arreglo rutasAdjuntos:
		List<File> archivos = new ArrayList<File>();
		for(String ruta : ArrayUtils.nullToEmpty(rutasAdjuntos)) {
			if( StringUtils.isNotBlank(ruta) ) {
				archivos.add(new File(ruta));
			}
		}
		// Se invoca el env�o de correo:
		return enviaCorreo(to, cc, bcc, asunto, mensaje, archivos);
	}

	
	@Override
	public boolean enviaCorreo(String[] to, String[] cc, String[] bcc, String asunto, String mensaje, List<File> adjuntos) {
		
		boolean exito = false;
		MimeMessage message = mailSender.createMimeMessage();
		
		try {
			
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			// Se asigna el remitente:
			helper.setFrom(from, fromAlias);
			// Se asignan los destinatarios:
			if(to != null && to.length > 0) {
				helper.setTo(to);				
			}
			if(cc != null && cc.length > 0) {
				helper.setCc(cc);
			}
			if(bcc != null && bcc.length > 0) {
				helper.setBcc(bcc);
			}
			// Se asigna el asunto:
			//TODO: Eliminar defaultMailMessage, cambiar funcionalidad para obtener asunto y mensaje de BD
			helper.setSubject( StringUtils.isBlank(asunto) ? defaultMailMessage.getSubject() : asunto );
			// Se asigna el mensaje:
			helper.setText( StringUtils.isBlank(mensaje) ? defaultMailMessage.getText() : mensaje );
			// Se asignan los adjuntos:
			for(File file : adjuntos) {
				if(file != null && file.exists()) {
					helper.addAttachment(file.getName(), file);
					logger.info( new StringBuffer("Se adjunt� el archivo ").append(file.getAbsolutePath()).toString() );
				} else {
					String fileName = (file != null) ? file.getName() : null;
					logger.warn( new StringBuffer("El archivo ").append(fileName).append(" no existe, no se adjuntar�").toString() );
				}
			}
			// Se envia el correo:
			mailSender.send(message);
			exito = true;
			logger.info("Email enviado");
		}catch(Exception e){
			logger.error("Error al enviar correo", e);
		}
		return exito;
	}

	
	/**
	 * 
	 * @param defaultMailMessage
	 */
	public void setDefaultMailMessage(SimpleMailMessage defaultMailMessage) {
		this.defaultMailMessage = defaultMailMessage;
	}

	/**
	 * 
	 * @param mailSender
	 */
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	/**
	 * 
	 * @param from
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * 
	 * @param fromAlias
	 */
	public void setFromAlias(String fromAlias) {
		this.fromAlias = fromAlias;
	}
	
}
