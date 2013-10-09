package mx.com.gseguros.portal.general.service;


public interface MailService {
	
	/**
	 * Envio de correo
	 * @param to
	 * @param cc
	 * @param bcc
	 * @param asunto
	 * @param mensaje
	 * @param rutasAdjuntos rutas de los archivos a adjuntar
	 * @return true si envió el correo, false si no lo envió
	 */
	public boolean enviaCorreo(String[] to, String[] cc, String[] bcc,
			String asunto, String mensaje, String[] rutasAdjuntos);
	

}
