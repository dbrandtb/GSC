package mx.com.gseguros.utils;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MailUtil {

	private final static Log logger = LogFactory.getLog(MailUtil.class);

	private String dir = new String();

	/**
	 * TODO: Mandar variables a properties
	 * */
	private static String host = "smtp.bizmail.yahoo.com";
	private static String from = "ventasavs.sza@biosnettcs.com";
	private static int port = 465;
	private static String user = "ventasavs.sza@biosnettcs.com";
	private static String pass = "avsbiosnet2013";
	private static boolean isTLS = false;
	private static boolean isSSL = true;
	
	public MailUtil() {
		
	}
	
	public MailUtil(Map<String,String> map) {
		dir = map.get("ruta.pdfs");
	}
	
	/**
	 * Metodo que se utiliza para el envio de Email con archivo adjunto.
	 * 
	 * @param to
	 *            Parametro que identifica a donde se enviara el email.
	 * @param asunto
	 *            Parametro que indica el asunto del mail que se enviara.
	 * @param mensaje
	 *            Parametro que indica el mensaje de mail.
	 * @param nombreArchivo
	 *            Parametro que indica el nombre del archivo que se enviara
	 *            adjunto.
	 * @throws EmailException
	 */
	/*
	public void send(String to, String asunto,
			String mensaje, String [] nombreArchivo)
			throws EmailException {

		EmailAttachment attachment = new EmailAttachment();
		attachment.setPath(dir + nombreArchivo[0]);
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment.setDescription(nombreArchivo[0]);
		attachment.setName(nombreArchivo[0]);
		
		EmailAttachment attachment2 = new EmailAttachment();
		if ( !nombreArchivo[1].equals("") ) {
			attachment2.setPath(dir + nombreArchivo[1]);
			attachment2.setDisposition(EmailAttachment.ATTACHMENT);
			attachment2.setDescription(nombreArchivo[1]);
			attachment2.setName(nombreArchivo[1]);
		}

		// Create the email message
		HtmlEmail email =  new HtmlEmail();
		email.setHostName(host);
		email.setAuthentication(user, pass);
		try {
			email.addTo(to);
			email.setFrom(from);
			email.setSubject(asunto);
			//email.setHtmlMsg(mensaje);
			email.setMsg(mensaje);
			
			// add the attachment
			//TODO: descomentaremail.attach(attachment);
			if ( !nombreArchivo[1].equals("") )
				email.attach(attachment2);

			// send the email
			email.send();
		} catch (EmailException emailException) {
			logger.error("Error enviando email: " + emailException.getMessage(), emailException);
		throw new EmailException(
					emailException.getMessage());
		} catch (Exception e) {
			logger.error("SE PRODUJO UN ERROR ENVIANDO EMAIL");
		}
		

	}
	
	public void sendSimpleEmailBCC(String to, String asunto, String mensaje)
	throws EmailException {
		
		// Create the email message
		HtmlEmail email =  new HtmlEmail();
		email.setHostName(host);
		email.setAuthentication(user, pass);
		try {
			
			String[] mails = to.split(",");
			for (String mail : mails) {
				if(StringUtils.isNotBlank(mail)) email.addBcc(mail);
			}
			
			email.setFrom(from);
			email.setSubject(asunto);
			email.setHtmlMsg(mensaje);
			//email.setMsg(mensaje);
			
			// send the email
			email.send();
		} catch (EmailException emailException) {
			logger.error("Error enviando email: " + emailException.getMessage());
			throw new EmailException(
					emailException.getMessage());
		} catch (Exception e) {
			logger.error("SE PRODUJO UN ERROR ENVIANDO EMAIL");
		}
		
		
	}
	*/
	
	/**
	 * Metodo que envia un mail html el cual puede llevar un fichero adjunto
	 * @param to
	 * @param asunto
	 * @param mensaje
	 * @param rutasAdjuntos Rutas donde cada fichero a adjuntar
	 * @throws EmailException
	 */
	/*
	public static boolean sendEmail(String to, String asunto, String mensaje, List<String> rutasAdjuntos)
	throws EmailException {
		
		boolean exito = false;
		
		// Create the email message
		MultiPartEmail email =  new MultiPartEmail();
		email.setHostName(host);
		email.setAuthentication(user, pass);
		//email.setSmtpPort(port);
		//email.setTLS(isTLS);
		//email.setSSL(isSSL);
		try {
			
			String[] mails = to.split(",");
			for (String mail : mails) {
				if(StringUtils.isNotBlank(mail)) email.addTo(mail);
			}
			
			email.setFrom(from);
			email.setSubject(asunto);
			email.setMsg(mensaje);
			//email.setMsg(mensaje);
			
			String nombreFichero;
			String[] aux;
			EmailAttachment attachment;
			if(rutasAdjuntos!=null){
				for(String fichero : rutasAdjuntos){
					if(StringUtils.isNotBlank(fichero)){
						attachment = new EmailAttachment();
						attachment.setPath(fichero);
						attachment.setDisposition(EmailAttachment.ATTACHMENT);
						
						aux = fichero.split("/");
						nombreFichero = aux[aux.length-1];
						System.out.println("nombreFichero="+ nombreFichero);
						
						attachment.setDescription(nombreFichero);
						attachment.setName(nombreFichero);	
						email.attach(attachment);
					}
				}
			}
			// send the email
			email.send();
			exito = true;
		} catch (Exception e) {
			logger.error("Error al enviar email: "  + e.getMessage(), e);
		}
		return exito;
	}
	

	
	
	public static void main(String arg[]) {
		try {
			List<String> listaURLs = new ArrayList<String>();
			List<String> listaDocumentos = new ArrayList<String>();
			String RUTA_DOUMENTOS = "/tmp";
			
			listaURLs.add("http://201.122.160.245:7777/reports/rwservlet?destype=cache&desformat=PDF&userid=ice/ice@acwqa&report=CARATULA.rdf&paramform=no&p_unieco=1&p_ramo=2&p_estado='M'&p_poliza=241&desname=/opt/ice/gseguros/documentos/2128/CARATULA.pdf");
			listaURLs.add("http://201.122.160.245:7777/reports/rwservlet?destype=cache&desformat=PDF&userid=ice/ice@acwqa&report=CREDENCIAL.rdf&paramform=no&p_unieco=1&p_ramo=2&p_estado='M'&p_poliza=241&desname=/opt/ice/gseguros/documentos/2128/CREDENCIAL.pdf");
			listaURLs.add("http://201.122.160.245:7777/reports/rwservlet?destype=cache&desformat=PDF&userid=ice/ice@acwqa&report=ENDOSOS_EPZ.rdf&paramform=no&p_unieco=1&p_ramo=2&p_estado='M'&p_poliza=241&desname=/opt/ice/gseguros/documentos/2128/ENDOSOS_EPZ.pdf");
			listaURLs.add("http://201.122.160.245:7777/reports/rwservlet?destype=cache&desformat=PDF&userid=ice/ice@acwqa&report=ENDOSOS_EXC.rdf&paramform=no&p_unieco=1&p_ramo=2&p_estado='M'&p_poliza=241&desname=/opt/ice/gseguros/documentos/2128/ENDOSOS_EXC.pdf");
			listaURLs.add("http://201.122.160.245:7777/reports/rwservlet?destype=cache&desformat=PDF&userid=ice/ice@acwqa&report=ENDOSOS_POL_100.rdf&paramform=no&p_unieco=1&p_ramo=2&p_estado='M'&p_poliza=241&desname=/opt/ice/gseguros/documentos/2128/ENDOSOS_POL_100.pdf");			
			
			for(String urlOrigen: listaURLs) {
				//Obtenemos el nombre del archivo:
				String [] aux = urlOrigen.split("/");
				String nombreArchivo = aux[aux.length-1];
				String nombreCompletoArchivo = RUTA_DOUMENTOS+ File.separator + nombreArchivo;
				
				boolean isArchivoGenerado = HttpUtil.generaArchivo(urlOrigen, nombreCompletoArchivo);
				//Si el arhivo fue generado, almacenamos la ruta y nombre del archivo:
				if(isArchivoGenerado) {
					listaDocumentos.add(nombreCompletoArchivo);
				}
			}
			
			
			MailUtil.sendEmail("ricardo.bautista@biosnettcs.com", "Prueba", "Prueba", listaDocumentos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
	
	
	/**
	 * 
	 * @param to
	 * @param asunto
	 * @param mensaje
	 * @param rutasAdjuntos
	 * @return
	 * @throws EmailException
	 */
	/*
	public static boolean enviaCorreo(String to, String cc, String bcc, String asunto, String mensaje, List<String> rutasAdjuntos) throws ApplicationException {
				
		boolean exito = false;
		
		
		if(to == null && cc == null && bcc == null){
			throw new ApplicationException("Error al enviar el correo, debe agregar la menos un destinatarios");
		}
		
		// Create the email message
		HtmlEmail email =  new HtmlEmail();
		email.setHostName(host);
		email.setAuthentication(user, pass);
		//email.setSmtpPort(port);
		//email.setTLS(isTLS);
		//email.setSSL(isSSL);
		try {
			
			if(to != null) {
				String[] mailsTo = to.split(";");
				for (String mail : mailsTo) {
					if(StringUtils.isNotBlank(mail)) email.addTo(mail);
				}
			}
			if(cc != null) {
				String[] mailsCC = cc.split(";");
				for (String mail : mailsCC) {
					if(StringUtils.isNotBlank(mail)) email.addCc(mail);
				}
			}
			if(bcc != null) {
				String[] mailsBCC = bcc.split(";");
				for (String mail : mailsBCC) {
					if(StringUtils.isNotBlank(mail)) email.addBcc(mail);
				}
			}
			
			email.setFrom(from);
			email.setSubject(asunto);
			email.setHtmlMsg(mensaje);
			//email.setMsg(mensaje);
			
			String nombreFichero;
			String[] aux;
			EmailAttachment attachment;
			
			if(rutasAdjuntos!=null){
				for(String rutaAdjunto : rutasAdjuntos){
					if(StringUtils.isNotBlank(rutaAdjunto)){
						
						aux = rutaAdjunto.split("/");
						nombreFichero = aux[aux.length-1];
						logger.debug("nombreFichero="+ nombreFichero);
						
						String RUTA_DOUMENTOS = "E:\\tmp";
						String nombreCompletoArchivo = RUTA_DOUMENTOS+ File.separator + nombreFichero;
						logger.debug("nombreCompletoArchivo="+ nombreCompletoArchivo);
						
						if( HttpUtil.generaArchivo(rutaAdjunto, nombreCompletoArchivo) ) {
							attachment = new EmailAttachment();
							attachment.setPath(nombreCompletoArchivo);
							attachment.setDisposition(EmailAttachment.ATTACHMENT);
							attachment.setDescription(nombreCompletoArchivo);
							attachment.setName(nombreCompletoArchivo);	
							email.attach(attachment);
						}
						
					}
				}
			}
			
			// send the email
			email.send();
			exito = true;
		} catch (Exception e) {
			logger.error("Error al enviar email: "  + e.getMessage(), e);
		}
		return exito;
	}*/
	
}