package mx.com.gseguros.portal.general.service.impl;

import java.util.List;

import mx.com.gseguros.portal.general.service.MailService;

public class MailServiceImpl implements MailService {

	@Override
	public boolean enviaCorreo(String to, List<String> cc, List<String> bcc,
			String asunto, String mensaje, List<String> rutaArchivos) {

		return false;
	}

	
}
