package mx.com.gseguros.utils;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailMail {
	
	private final static Log logger = LogFactory.getLog(MailMail.class);
	
	private JavaMailSender mailSender;
	
	private SimpleMailMessage simpleMailMessage;
	
	private String fromAlias;
	
	/**
	 * 
	 * @param to
	 * @param cc
	 * @param bcc
	 * @param asunto
	 * @param mensaje
	 * @param rutaArchivo
	 * @return
	 */
	public boolean sendMail(String[] to, String[] cc, String[] bcc, String asunto, String mensaje, String[] rutasAdjuntos) {
		
		boolean exito = false;
		MimeMessage message = mailSender.createMimeMessage();
		
		try {
			
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(simpleMailMessage.getFrom(), fromAlias);
			if(to != null && to.length > 0) {
				helper.setTo(to);				
			}
			if(cc != null && cc.length > 0) {
				helper.setCc(cc);
			}
			if(bcc != null && bcc.length > 0) {
				helper.setBcc(bcc);
			}
			helper.setSubject( StringUtils.isBlank(asunto) ? simpleMailMessage.getSubject() : asunto );
			helper.setText( StringUtils.isBlank(mensaje) ? simpleMailMessage.getText() : mensaje );
			if(rutasAdjuntos != null && rutasAdjuntos.length > 0){
				for(String rutaAdjunto: rutasAdjuntos){
					FileSystemResource file = new FileSystemResource(rutaAdjunto);
					logger.debug("before attaching the document " + rutaAdjunto);
					helper.addAttachment(file.getFilename(), file);
					logger.debug("after attaching the document " + rutaAdjunto);
				}
			}
			mailSender.send(message);
			exito = true;
			
		}catch(Exception e){
			logger.error("Error al enviar correo", e);
		}
		return exito;
	}

	
	public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
		this.simpleMailMessage = simpleMailMessage;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public void setFromAlias(String fromAlias) {
		this.fromAlias = fromAlias;
	}
	
	
}
