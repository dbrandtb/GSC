package mx.com.gseguros.portal.general.util;


import java.text.SimpleDateFormat;
import java.util.Date;

import mx.com.gseguros.utils.HttpUtil;
import mx.com.gseguros.utils.MailMail;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.UrlValidator;

import com.opensymphony.xwork2.ActionSupport;

public class MailAction extends ActionSupport {

	private static final long serialVersionUID = 3608545898806750390L;
	
	private final static Log logger = LogFactory.getLog(MailAction.class);
	
	private boolean success;
	
	private String to;
	
	private String cc;
	
	private String bcc;
	
	private String asunto;
	
	private String mensaje;
	
	private String archivos;
	
	private MailMail mailMail;
	
	
	public String enviaCorreo() throws Exception {
		
		try{
			UrlValidator urlValidator = new UrlValidator();
			//Obtenemos el nombre del archivo:
			//String [] aux = archivos.split("/");
			//String nombreArchivo = aux[aux.length-1];
			
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_hmmss");
			String nombreArchivo = "cotizacion_" + sdf.format(date) + ".pdf";
			String nombreCompletoArchivo = this.getText("ruta.documentos.poliza")+ "/" + nombreArchivo;
			if(HttpUtil.generaArchivo(archivos, nombreCompletoArchivo)){
				//Se realiza el envío de correo:
				mailMail.sendMail(to, cc, bcc, asunto, mensaje, nombreCompletoArchivo);
				success = true;
			}
				
			//Si el arhivo fue generado, almacenamos la ruta y nombre del archivo:
			/*
			for(String ruta : archivos) {
				if( StringUtils.isNotBlank(ruta) ) {
					//Si la referencia al archivo es una URL, entonces lo creamos en el servidor:
					if(urlValidator.isValid(ruta)) {
						//TODO: arreglar para que sea independiente del SO
						//Obtenemos el nombre del archivo:
						String [] aux = ruta.split("/");
						String nombreArchivo = aux[aux.length-1];
						String nombreCompletoArchivo = this.getText("ruta.documentos.poliza")+ "/" + nombreArchivo;
						boolean isArchivoGenerado = HttpUtil.generaArchivo(ruta, nombreCompletoArchivo);
						//Si el arhivo fue generado, almacenamos la ruta y nombre del archivo:
						if(isArchivoGenerado) {
							listaDocumentos.add(nombreCompletoArchivo);
						}
					} else {
						listaDocumentos.add(ruta);
					}
				}
			}
			*/
			
			
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return SUCCESS;
	}


	public boolean isSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}


	public String getTo() {
		return to;
	}


	public void setTo(String to) {
		this.to = to;
	}


	public String getCc() {
		return cc;
	}


	public void setCc(String cc) {
		this.cc = cc;
	}


	public String getBcc() {
		return bcc;
	}


	public void setBcc(String bcc) {
		this.bcc = bcc;
	}


	public String getAsunto() {
		return asunto;
	}


	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}


	public String getMensaje() {
		return mensaje;
	}


	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
	public String getArchivos() {
		return archivos;
	}


	public void setArchivos(String archivos) {
		this.archivos = archivos;
	}


	public void setMailMail(MailMail mailMail) {
		this.mailMail = mailMail;
	}
	
}
