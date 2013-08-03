package mx.com.aon.catbo.web;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeMessage.RecipientType;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import mx.com.aon.catbo.model.MailVO;
import mx.com.aon.catbo.model.NotificacionVO;
import mx.com.aon.catbo.service.ConfiguracionMailManager;
import mx.com.aon.catbo.service.EnvioMailManager;
import mx.com.aon.catbo.service.NotificacionMailManager;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.util.ConnectionCallInterceptor;

import com.opensymphony.xwork2.ActionSupport;


public class EnvioMailAction extends ActionSupport implements SessionAware{
	
	private static Logger logger = Logger.getLogger(EnvioMailAction.class);
	
	//private transient EnvioMailManager envioMailManager; 
	private transient ConfiguracionMailManager configuracionMailManager;
	private transient NotificacionMailManager notificacionMailManager;
    
	protected boolean success;

   	 private Session session;
   	 private Map session2;
	 private Properties config;
	 
	 private String correo;
	 private List<String> cc;

	 private String numeroCaso;
	 
	 private String strUsuariosSeg;
	 
	 private String[] valores;
	 
	 private String option = "false";
	 
	 private String cdProceso;
	 
	 private String mailUser = "";
	 
	 private String messageError = "";

	 /**
	  * Indica si el envío es manual o automático
	  * Valores:
	  * 	M: Manual (Alta de Caso)
	  * 	A: Automático
	  * 
	  */
	 private String cdTipoEnvio;
	 /**
	  * Carga la configuracion incializa la session
	  * 
	  */

	 /**
	  * Setea las propiedades necesarias para el envío del mail
	  */
	 private void cmdSetProperties () {		 
		 config = new Properties();
	     
		 config.setProperty("mail.smtp.host", valores[0]);
		 config.setProperty("mail.smtp.starttls.enable", "true");
		 config.setProperty("mail.smtp.port", "25");	     
		 config.setProperty("mail.smtp.user", valores[2]);
	     config.setProperty("mail.smtp.auth", "true");	     
	     config.setProperty("mail.address.from", correo);
	     config.setProperty("mail.transport", "smtp");	     
	     config.setProperty("mail.smtp.user.pass", valores[3]);
	     
	     session = session.getDefaultInstance(config);
	 }
	 
	 public String obtenerDatosMail () throws ApplicationException {
		 logger.debug("Obteniendo datos de la configuracion de smpt desde la db ...");
		 try{
			 valores = configuracionMailManager.obtieneConfMail();
			 logger.debug("Configuracion obtenida: "+valores[0]+", "+valores[2]+", "+valores[3]); 
		 }catch(Exception e){
			 logger.debug("Excepcion en la obtencion de la configuracion: "+e.getMessage());
		 }		 
		 success = true;
		 return SUCCESS;
	 }


	public String cmdEnviaMailClick() throws ApplicationException{
		
		obtenerDatosMail();
		cmdSetProperties();
		
		try{							
			String mailUser = "";
			if(option.equals("true")){
				//obtener aca la direccion de correo del usuario logueado*/											
				UserVO userMail = (UserVO) session2.get("USUARIO");
				mailUser=userMail.getEmail();
				logger.debug("userMail.getEmail() "+userMail.getEmail());
				if((mailUser==null || mailUser.equals("")) && correo.equals("")){						
					 success = false;
					 String errorCode;
					 //messageError=("Introduzca una dirección de correo, ya que no cuenta con una direcci&oacute;n registrada");
					 errorCode = "400095";
					 messageError = "Introduzca una dirección de correo, ya que no cuenta con una dirección registrada.";
					 addActionError(messageError); 
					 return SUCCESS;					
				}
				
				
				
				logger.debug("Dentro del if. Mail del usuario logueado mailUser: "+mailUser);								
			}			
			NotificacionVO notificacionVO = new NotificacionVO(); 
			try{
				logger.debug("Obteniendo el dsmensaje (cuerpo del mail) y la dsnotificacion (el asunto)...");							
				notificacionVO = notificacionMailManager.obtieneNotificacionMail(cdProceso);		
			}catch(Exception e){
	            logger.debug("Quemal. No se encontro una notificacion en la db");
				if(notificacionVO.getDsMensaje()==null || notificacionVO.getDsMensaje().equals("")){						
					 success = false;
					 String errorCode;					
					 errorCode = "400119";					
					 messageError = "No existe notificación asociada para la tarea.";
					 addActionError(messageError); 
					 return SUCCESS;					
				}
			logger.debug("dsMensaje: "+notificacionVO.getDsMensaje().toString()+ " y dsNotificacion (el asunto) "+notificacionVO.getDsNotificacion());								
				
			}		
	        MimeMessage message = new MimeMessage(session);
	        message.setFrom(new InternetAddress( config.getProperty("mail.smtp.user") ));
	        logger.debug("Correo ingresado por pantallaBO: "+correo);
	        if(correo!=null && !correo.equals("")){
	        	message.addRecipient(Message.RecipientType.TO, new InternetAddress(correo));
	        }
	        message.setSubject("Caso BO: " + numeroCaso + " - " + notificacionVO.getDsNotificacion());
	        try{
	        	if(mailUser!=null && !mailUser.equals("")){
	        		message.setRecipient(RecipientType.CC, new InternetAddress(mailUser));
	        	}	        	
	        }catch(Exception e){
	        	logger.debug("Excepcion en mail de usuario logueado. setRecipient: " + e.getMessage());
	        }	        	       
	        Multipart mp = new MimeMultipart();
	        MimeBodyPart mbp = new MimeBodyPart();	        
	        mbp.setContent(notificacionVO.getDsMensaje().toString(), "text/html");
	        mp.addBodyPart(mbp);
	        logger.debug("Seteando el objeto multipart: "+mp);
	        message.setContent(mp);
	        
	        logger.debug("obteniendo session.getTransport(smtp) ...");
	        Transport t = session.getTransport("smtp");
	        
	        logger.debug("Conectando con la configuracion obtenida ...");
	        t.connect(config.getProperty("mail.smtp.host"),Integer.parseInt(config.getProperty("mail.smtp.port")), config.getProperty("mail.smtp.user"), config.getProperty("mail.smtp.user.pass"));
	        
	        logger.debug("Enviando mensaje ...");
	        t.sendMessage(message, message.getAllRecipients());	        
	        logger.debug("Mensaje enviado ...");
	        
	        t.close();
	        logger.debug("Transporte cerrado ...");
            success = true;
            addActionMessage("Envío exitoso");
            return SUCCESS;
        }catch( Exception e){
        	
        	
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }

	}
	
	
	public boolean getSuccess() {
        return success;
    }


    public void setSuccess(boolean success) {
        this.success = success;
    }

	public void setSession(Session session) {
		this.session = session;
	}

	public void setConfig(Properties config) {
		this.config = config;
	}
	public void setCc(List<String> cc) {
		this.cc = cc;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getNumeroCaso() {
		return numeroCaso;
	}

	public void setNumeroCaso(String numeroCaso) {
		this.numeroCaso = numeroCaso;
	}

	public String getCdTipoEnvio() {
		return cdTipoEnvio;
	}

	public void setCdTipoEnvio(String cdTipoEnvio) {
		this.cdTipoEnvio = cdTipoEnvio;
	}

	public void setStrUsuariosSeg(String strUsuariosSeg) {
		this.strUsuariosSeg = strUsuariosSeg;
	}

	public void setNotificacionMailManager(
			NotificacionMailManager notificacionMailManager) {
		this.notificacionMailManager = notificacionMailManager;
	}

	/*public void setEnvioMailManager(EnvioMailManager envioMailManager) {
		this.envioMailManager = envioMailManager;
	}*/

	public void setConfiguracionMailManager(
			ConfiguracionMailManager configuracionMailManager) {
		this.configuracionMailManager = configuracionMailManager;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getCdProceso() {
		return cdProceso;
	}

	public void setCdProceso(String cdProceso) {
		this.cdProceso = cdProceso;
	}

	public String getMessageError() {
		return messageError;
	}

	public void setMessageError(String messageError) {
		this.messageError = messageError;
	}

	public void setSession(Map map) {
		session2 = map;
		
	}

}
