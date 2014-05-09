package mx.com.gseguros.portal.general.service;

import java.io.File;
import java.util.List;



public interface MailService {

	
	/**
	 * Env&iacute;a un e-mail
	 * @param to      Arreglo de E-mails "Para"
	 * @param cc      Arreglo de E-mails "Con Copia"
	 * @param bcc     Arreglo de E-mails destino "Con Copia Oculta"
	 * @param asunto  T&iacute;tulo del e-mail
	 * @param mensaje Mensaje/cuerpo del e-mail
	 * @param rutasAdjuntos Rutas completas de los archivos a adjuntar
	 * @return true si el e-mail se envi&oacute; exitosamente, false si no 
	 */
	public boolean enviaCorreo(String[] to, String[] cc, String[] bcc, String asunto, String mensaje, String[] rutasAdjuntos);
	
	
	/**
	 * Env&iacute;a un e-mail
	 * @param to      Arreglo de E-mails "Para"
	 * @param cc      Arreglo de E-mails "Con Copia"
	 * @param bcc     Arreglo de E-mails destino "Con Copia Oculta"
	 * @param asunto  T&iacute;tulo del e-mail
	 * @param mensaje Mensaje/cuerpo del e-mail
	 * @param adjuntos Lista de archivos a adjuntar
	 * @return true si el e-mail se envi&oacute; exitosamente, false si no
	 */
	public boolean enviaCorreo(String[] to, String[] cc, String[] bcc, String asunto, String mensaje, List<File> adjuntos);
	
}