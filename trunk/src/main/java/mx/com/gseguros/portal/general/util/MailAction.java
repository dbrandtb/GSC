package mx.com.gseguros.portal.general.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import mx.com.gseguros.utils.HttpUtil;
import mx.com.gseguros.utils.MailUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionSupport;

public class MailAction extends ActionSupport {

	private static final long serialVersionUID = 3608545898806750390L;
	
	private final static Log logger = LogFactory.getLog(MailAction.class);
	
	private boolean success;
	
	private String to;
	
	private String cc;
	
	private String bcc;
	
	private List<String> archivos;
	
	
	public String enviaCorreo() throws Exception {
		
		try{
			/*
			String asunto = "Cotización General de Seguros";
			String mensaje = "Estimado usuario, <br/><br/>anexamos la cotizaci&oacute;n realizada.";
			
			List<String> listaURLs = new ArrayList<String>();
			List<String> listaDocumentos = new ArrayList<String>();
			String RUTA_DOUMENTOS = "E:\\tmp";
			for(String urlOrigen: archivos) {
				if(StringUtils.isNotBlank(urlOrigen)) {
					//Obtenemos el nombre del archivo:
					String [] aux = urlOrigen.split("/");
					String nombreArchivo = aux[aux.length-1];
					String nombreCompletoArchivo = RUTA_DOUMENTOS+ File.separator + nombreArchivo;
					System.out.println("nombreArchivo=" + nombreArchivo);
					System.out.println("nombreCompletoArchivo=" + nombreCompletoArchivo);
					
					boolean isArchivoGenerado = HttpUtil.generaArchivo(urlOrigen, nombreCompletoArchivo);
					//Si el arhivo fue generado, almacenamos la ruta y nombre del archivo:
					if(isArchivoGenerado) {
						listaDocumentos.add(nombreCompletoArchivo);
					}
				}
			}
			
			
			success = MailUtil.sendEmail("ricardo.bautista@biosnettcs.com", "Prueba", "Prueba", listaDocumentos);
			*/
			/*
			List<String> listaDocumentos = new ArrayList<String>();
			String RUTA_DOUMENTOS = "E:\\tmp";
			if( archivos != null ) {
				logger.debug("archivos=" + archivos);
				logger.debug("# archivos:" + archivos.size());
			}
			
			for(String urlOrigen: archivos) {
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
			
			logger.debug("listaDocumentos=" + listaDocumentos);
			
			MailUtil.sendEmail("ricardo.bautista@biosnettcs.com", "Prueba", "Prueba", listaDocumentos);
			*/
			
			
				List<String> listaURLs = new ArrayList<String>();
				List<String> listaDocumentos = new ArrayList<String>();
				String RUTA_DOUMENTOS = "/tmp";
				
				listaURLs.add("http://201.122.160.245:7777/reports/rwservlet?destype=cache&desformat=PDF&userid=ice/ice@acwqa&report=CARATULA.rdf&paramform=no&p_unieco=1&p_ramo=2&p_estado='M'&p_poliza=241&desname=/opt/ice/gseguros/documentos/2128/CARATULA.pdf");
				//listaURLs.add("http://201.122.160.245:7777/reports/rwservlet?destype=cache&desformat=PDF&userid=ice/ice@acwqa&report=CREDENCIAL.rdf&paramform=no&p_unieco=1&p_ramo=2&p_estado='M'&p_poliza=241&desname=/opt/ice/gseguros/documentos/2128/CREDENCIAL.pdf");
				//listaURLs.add("http://201.122.160.245:7777/reports/rwservlet?destype=cache&desformat=PDF&userid=ice/ice@acwqa&report=ENDOSOS_EPZ.rdf&paramform=no&p_unieco=1&p_ramo=2&p_estado='M'&p_poliza=241&desname=/opt/ice/gseguros/documentos/2128/ENDOSOS_EPZ.pdf");
				//listaURLs.add("http://201.122.160.245:7777/reports/rwservlet?destype=cache&desformat=PDF&userid=ice/ice@acwqa&report=ENDOSOS_EXC.rdf&paramform=no&p_unieco=1&p_ramo=2&p_estado='M'&p_poliza=241&desname=/opt/ice/gseguros/documentos/2128/ENDOSOS_EXC.pdf");
				//listaURLs.add("http://201.122.160.245:7777/reports/rwservlet?destype=cache&desformat=PDF&userid=ice/ice@acwqa&report=ENDOSOS_POL_100.rdf&paramform=no&p_unieco=1&p_ramo=2&p_estado='M'&p_poliza=241&desname=/opt/ice/gseguros/documentos/2128/ENDOSOS_POL_100.pdf");			
				
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
				
				
				new MailUtil().sendEmail("ricardo.bautista@biosnettcs.com", "Prueba", "Prueba", listaDocumentos);
			
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


	public List<String> getArchivos() {
		return archivos;
	}


	public void setArchivos(List<String> archivos) {
		this.archivos = archivos;
	}
		

}
