package mx.com.aon.portal.web;

import java.util.Properties;
/*
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;*/

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.service.EnvioMailManager;
import com.opensymphony.xwork2.ActionSupport;


public class EnvioMailAction extends ActionSupport {

	private transient EnvioMailManager envioMailManager; 
    
	// Respuesta para JSON
	protected boolean success;

   	 /*private Session session;
	    Properties config;*/
	
	 /**
	  * Carga la configuracion incializa la session
	  * 
	  */
	 public EnvioMailAction() {
	  /* config = new Properties();
	     
	     // Propiedades de la conexión
	     config.setProperty("mail.smtp.host", "smtp.gmail.com");
	     config.setProperty("mail.smtp.starttls.enable", "true");
	     config.setProperty("mail.smtp.port", "587");
	     config.setProperty("mail.smtp.user", "antoniopar@gmail.com");
	     config.setProperty("mail.smtp.auth", "true");
	
	     //propiedades del email puestas por mi
	     config.setProperty("mail.address.from", "antoniopar@gmail.com");
	     config.setProperty("mail.transport", "smtp");
	     config.setProperty("mail.smtp.user.pass", "pswd");
	     
	     session = session.getDefaultInstance(config);*/
	 }

//String recipient , String subject, String body
	public String cmdEnviaMailClick() throws ApplicationException{
		String messageResult = "";
		/*try{
			
			 // Construimos el mensaje
	        MimeMessage message = new MimeMessage(session);
	        message.setFrom(new InternetAddress( config.getProperty("mail.smtp.user") ));
	        message.addRecipient(
	            Message.RecipientType.TO,
	            new InternetAddress( "antoniopar@gmail.com" ));//recipient
	        message.setSubject( "prueba" );//subject
	        message.setText("prueba body");//body
	
	        // Lo enviamos.
	        Transport t = session.getTransport("smtp");
	        // Transport t = session.getTransport(config.getProperty("mail.transport"));
	        
	        
	        t.connect(config.getProperty("mail.smtp.user"), config.getProperty("mail.smtp.user.pass"));
	        t.sendMessage(message, message.getAllRecipients());
	
	        // Cierre.
	        t.close();
	        
			//messageResult = envioMailManager.enviarMail();
            success = true;
            addActionMessage(messageResult);
            return SUCCESS;
        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }*/
		return "";

	}
	
	
    public boolean isSuccess() {
        return success;
    }

    public EnvioMailManager obtenEnvioMailManager() {
		return envioMailManager;
	}

	public void setEnvioMailManager(EnvioMailManager envioMailManager) {
		this.envioMailManager = envioMailManager;
	}

	public boolean getSuccess() {
        return success;
    }


    public void setSuccess(boolean success) {
        this.success = success;
    }

	/*public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Properties getConfig() {
		return config;
	}

	public void setConfig(Properties config) {
		this.config = config;
	}*/
}
