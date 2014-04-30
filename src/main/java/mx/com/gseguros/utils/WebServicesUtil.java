package mx.com.gseguros.utils;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
/**
 * Clase para Invocar los servicios del Anexo4F
 * @version 1.0.0
 */
public class WebServicesUtil {
	
	private final static Log logger = LogFactory.getLog(WebServicesUtil.class);
    
	/**
	 * M�todo para invocar Servicio Web
	 * @param  direccionWS URL del servicio web
	 * @param  actionWS    Acci�n del servicio web a invocar 
	 * @param  mensaje     PayLoad del servicio web para invocar
	 * @param  timeout     Tiempo de espera para la llamada
	 * @param  asincrono true = s�ncrono, false = as�ncrono
	 * 
	 * @return respuesta OMElement con el mensaje de respuesta 
	 * @throws Exception Si ocurre error en la invocaci�n del servicio
	 */
	public static OMElement invocaServicio(String direccionWS, String actionWS, OMElement mensaje, Long timeout, Options options, boolean asincrono) throws Exception {
		
	    logger.debug("Inicia Invoca Servicio [ " + actionWS + " ]");
        /**Declaraci�n de variables*/
		OMElement respuesta = null;
		
		/**Create the service client*/
		logger.debug(" ***** Parametros Servicio ***** ");
		logger.debug(" Action    = " + actionWS);
		logger.debug(" Direccion = " + direccionWS);
		logger.debug(" Asincrono = " + asincrono);
		logger.debug(" Mensaje   = " + mensaje);
		logger.debug(" Timeout   = " + timeout);
		
		ServiceClient serviceClient = new ServiceClient();
		
		if(options != null){
			if(timeout != null){
		       	options.setTimeOutInMilliSeconds(timeout);
		       }
			options.setTo(new EndpointReference(direccionWS));
			options.setAction(actionWS);
			serviceClient.setOptions(options);
		}else {
			Options defaultOptions = new Options();
		       if(timeout != null){
		       	defaultOptions.setTimeOutInMilliSeconds(timeout);
		       }
		       defaultOptions.setTo(new EndpointReference(direccionWS));
		       defaultOptions.setAction(actionWS);
//		       defaultOptions.setProperty(HTTPConstants.CHUNKED, Boolean.FALSE);
//		       defaultOptions.setProperty(HTTPConstants.REUSE_HTTP_CLIENT, Boolean.TRUE);
//		       defaultOptions.setCallTransportCleanup(true);
	           serviceClient.setOptions(defaultOptions);
		}
	        
	       /**invoke service*/
	       if(asincrono) {
	       	serviceClient.fireAndForget(mensaje);
	       } else {
	       	respuesta = serviceClient.sendReceive(mensaje);
	       	
	       }
	       logger.debug(" Respuesta invocaServicio " +  actionWS + " = " + respuesta);
	        
		logger.debug("Termina Invoca Servicio [ " + actionWS + " ]");
		return respuesta;
	}
	
	/**
	 * Metodo que formatea un xml en linea a un xml con formato identado
	 * @param xml
	 * @return
	 */
	public static String formatXml(String xml){
		String xmlString = null;
        try{
        	
        	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xml)));
            
            Transformer serializer= SAXTransformerFactory.newInstance().newTransformer();
            serializer.setOutputProperty(OutputKeys.METHOD, "xml");
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            serializer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

            StringWriter stringWtr = new StringWriter();
            Source source = new DOMSource(document);
            StreamResult result = new StreamResult(stringWtr);
            
            serializer.transform(source, result);
            xmlString = stringWtr.toString();
            
        }catch(Exception e){
            logger.error(e);
        }
		return xmlString;
    }
	
	
	public static void main(String[] args) {
		System.out.println(formatXml("<?xml version='1.0' encoding='utf-8'?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Body><ns1:clienteSaludGS xmlns:ns1=\"http://ws.ice2sigs.gs.com/\"><arg0>1</arg0><arg1><agrupaCli>0</agrupaCli><apellidomCli>BARRUETA</apellidomCli><apellidopCli>ALONSO</apellidopCli><calleCli>PRUEBAS</calleCli><canconCli>0</canconCli><celularCli> </celularCli><cheqdevCli>0</cheqdevCli><claveCli>517990</claveCli><codcarCli>0</codcarCli><codposCli>3610</codposCli><coloniaCli>AMERICAS UNIDAS</coloniaCli><edocarCli>0</edocarCli><edocivilCli>2</edocivilCli><estadoCli>9</estadoCli><faxCli> </faxCli><fecaltaCli>2014-04-23T16:19:06.059-05:00</fecaltaCli><fecnacCli>1969-08-26T16:19:06.060-06:00</fecnacCli><fismorCli>1</fismorCli><giroCli>0</giroCli><municipioCli>BENITO JU�REZ</municipioCli><nombreCli>ALEJANDRO</nombreCli><numeroCli>44 INT </numeroCli><poblacionCli>BENITO JU�REZ</poblacionCli><rfcCli>AOBA690826</rfcCli><rmdbRn>0</rmdbRn><sexoCli>1</sexoCli><sinocurCli>0</sinocurCli><sucursalCli>1002</sucursalCli></arg1></ns1:clienteSaludGS></soapenv:Body></soapenv:Envelope>"));
	}
	
    
}