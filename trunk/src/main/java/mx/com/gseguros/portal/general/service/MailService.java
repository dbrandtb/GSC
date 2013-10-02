package mx.com.gseguros.portal.general.service;

import java.util.List;

public interface MailService {
	
	/**
	 * Envio de correo
	 * 
	 * @param rutaArchivos Lista con las rutas locales o rutas http de los archivos
	 * @param to
	 * @param cc
	 * @param bcc
	 * @param asunto
	 * @param mensaje
	 * @param rutaArchivos
	 * @return true si envió el correo, false si no lo envió
	 */
	public boolean enviaCorreo(String to, List<String> cc, List<String> bcc, String asunto, String mensaje, List<String> rutaArchivos);

}
