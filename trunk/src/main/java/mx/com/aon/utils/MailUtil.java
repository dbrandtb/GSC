package mx.com.aon.utils;

import static mx.com.aon.portal.dao.ParametroGeneralDAO.BUSCAR_PARAMETROS;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.EmailVO;
import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;
import mx.com.aon.portal.util.WrapperResultados;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * 
 * @author Adolfo
 * 
 */
public class MailUtil extends AbstractManagerJdbcTemplateInvoke {

	private static Logger logger = Logger.getLogger(MailUtil.class);

	private JavaMailSenderImpl sender;

	private EmailVO email;

	/**
	 * @return the email
	 */
	public EmailVO getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(EmailVO email) {
		this.email = email;
	}

	/**
	 * @param sender
	 *            the sender to set
	 */
	public void setSender(JavaMailSenderImpl sender) {
		this.sender = sender;
	}

	/**
	 * Metodo que se utiliza para el envio de emails; este metodo verifica si
	 * existe en la instancia de la clase el Objeto {@link EmailVO}
	 * 
	 * @throws ApplicationException
	 * @throws MessagingException
	 */
	public void send() throws ApplicationException, MessagingException {
		logger.info("Entrando a envio de email");

		if (email == null)
			throw new ApplicationException(
					"No se encontro el objeto de tipo EmailVO. " +
					"Debe administrar un objeto de tipo EmailVO " +
					"que contiene los parametros del envio de email");

		logger.info("Buscando valores de envio de email");
		String[] valores = null;
		Map<String, String> map = new HashMap<String, String>();
		map.put("pi_nbparam", "HOST_EMAIL|FROM_EMAIL|USER_EMAIL|PASS_EMAIL");

		try {
			WrapperResultados resultados = returnResult(map,
					BUSCAR_PARAMETROS);

			valores = StringUtils.split(resultados.getItemMap().get("pi_valor")
					.toString(), "|");

			if (StringUtils.isNotBlank(valores[0]))
				sender.setHost(valores[0]);
			else
				throw new MessagingException(
						"No es posible enviar email, debido a que no existe el valor del Host de email");
			
			if (StringUtils.isNotBlank(valores[2])) {
				logger.debug("USERNAME: " + valores[2]);
				sender.setUsername(valores[2]);
			}
			if (StringUtils.isNotBlank(valores[3])) {
				logger.debug("PASS: " + valores[3]);
				sender.setPassword(valores[3]);
			}
			sender.setPort(25);
			
		} catch (ApplicationException e) {
			logger
					.error("Error al buscar los datos del servidor de email en la base de datos");
			throw new ApplicationException(
					"Error al buscar los datos del servidor de email en la base de datos: "
							+ e.getMessage());
		}

		MimeMessage message = sender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		if (StringUtils.isNotBlank(valores[1]))
			helper.setFrom(valores[1]);
		else
			throw new MessagingException(
					"No es posible enviar email, debido a que no existe el valor del 'From'");

		helper.setTo(email.getAllTo());
		helper.setCc(email.getAllCc());
		helper.setText(email.getMensaje());
		helper.setSubject(email.getAsunto());

		if (email.getFile() != null ) {
			FileSystemResource archivo = new FileSystemResource(email.getFile());
			helper.addAttachment(email.getFileName(), archivo);
		}

		sender.send(message);
		logger.info("Saliendo de envio de email");
	}
}