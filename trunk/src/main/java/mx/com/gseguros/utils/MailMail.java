package mx.com.gseguros.utils;

import java.util.List;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
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
	 */
	public void sendMail(String to, String cc, String bcc, String asunto, String mensaje, List<String>rutaArchivo) {
		
		MimeMessage message = mailSender.createMimeMessage();
		
		try {
			
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(simpleMailMessage.getFrom(), fromAlias);
			helper.setTo(to);
			//helper.setTo(simpleMailMessage.getTo());
			if(StringUtils.isNotBlank(cc)) {
				helper.setCc(cc);
			}
			if(StringUtils.isNotBlank(bcc)) {
				helper.setBcc(bcc);
			}
			helper.setSubject( StringUtils.isBlank(asunto) ? simpleMailMessage.getSubject() : asunto );
			helper.setText( StringUtils.isBlank(mensaje) ? simpleMailMessage.getText() : mensaje );
			for(String ruta: rutaArchivo) {
				FileSystemResource file = new FileSystemResource(ruta);
				logger.debug("before attaching the document " + ruta);
				helper.addAttachment(file.getFilename(), file);
				logger.debug("after attaching the document " + ruta);
			}
			mailSender.send(message);
			
		//}catch (MessagingException e) {
		//	throw new MailParseException(e);
		}catch(Exception e){
			logger.error("Error al enviar correo", e);
		}
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
