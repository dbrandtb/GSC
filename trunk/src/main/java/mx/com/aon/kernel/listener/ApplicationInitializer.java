package mx.com.aon.kernel.listener;

import java.util.TimeZone;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.parsers.FactoryConfigurationError;

import org.apache.log4j.Logger;
public class ApplicationInitializer implements ServletContextListener{

	 //Logger
    private static Logger logger=org.apache.log4j.Logger.getLogger(ApplicationInitializer.class);

    /**
     * Rutina de inicialización del Sistemna New ALEA,
     * se ejecuta cuando New ALEA es deplegada en IAS.
     *
     * @param event Evento a responder.
     */
    public void contextInitialized(ServletContextEvent event) {
    	ServletContext context = event.getServletContext();
    	 try {
             TimeZone.setDefault(TimeZone.getTimeZone("America/Mexico_City"));/*
             //Obteniendo propiedades para archivo de propiedades NewALEA
             //Se carga por OAS
             //Global.PROPERTIES=System.getProperty("NewALEA.run.properties","/opt/ice/acw/NewALEA-Config/NewALEA-run.properties");
             Global.PROPERTIES=System.getProperty("NewALEA.run.properties","c:\\NewALEA-Config\\NewALEA-run.properties");
             //Global.CATWEB_PROPERTIES=System.getProperty("aon-catweb.properties","/opt/ice/acw/NewALEA-Config/aon-catweb.properties");
             Global.CATWEB_PROPERTIES=System.getProperty("aon.catweb.properties","C:\\NewALEA-Config\\aon-catweb.properties");
             //Cargar archivo de propiedades de New ALEA.
             PropertyReader.load(Global.PROPERTIES);
             PropertyReader.load(Global.CATWEB_PROPERTIES);
             //Configuracion de logs del sistema
             Global.LOG4J_CONFIGURATION_FILE_PATH=PropertyReader.readProperty("config.LOG4J_CONFIGURATION_FILE_PATH").trim();
             //Cargar configuración del log4j desde xml
             DOMConfigurator.configure(Global.LOG4J_CONFIGURATION_FILE_PATH);
             if (logger.isInfoEnabled()) {
                 logger.info("Inicializando AON.....");
             }            
             if(logger.isDebugEnabled()){
                 logger.debug("Path de archivo log4j.xml = "+PropertyReader.readProperty("config.LOG4J_CONFIGURATION_FILE_PATH").trim());
             }
             //Suscripcion al topico de mensajes
             //context.setAttribute(Global.MESSAGEDEALER, new MessageDealer());
             //Suscripcion al topico de caches
             //context.setAttribute(Global.MESSAGEDEALER_CACHE, new MessageDealerCache());
             //Porcentaje Maximo permitido para la memoria en el manejador de Session
             //Global.PORCENTAJE_MEMORIA_SESSION=Double.valueOf(PropertyReader.readProperty("config.PORCENTAJE_MEMORIA_SESSION")).doubleValue();  
             //Ruta donde sera generada la bitacora de expresiones
             Global.RUTA_LOG_EXPRESIONES=PropertyReader.readProperty("log.RUTA_LOG_EXPRESIONES").trim();
             //Archivos de configuracion del kernel 
             if(logger.isDebugEnabled()){
                 logger.debug("log.RUTA_LOG_EXPRESIONES = "+Global.RUTA_LOG_EXPRESIONES);
             }
             Global.XML_CONFIGURATION_KERNEL_FILE=PropertyReader.readProperty("config.XML_CONFIGURATION_KERNEL_FILE").trim();
             
             //Archivos de data del Componente Tablas de apoyo
             if(logger.isDebugEnabled()){
                 logger.debug("Global.XML_CONFIGURATION_KERNEL_FILE= = "+Global.XML_CONFIGURATION_KERNEL_FILE);
             }
             Global.XML_DATA_TABLA_DE_APOYO_FILE=PropertyReader.readProperty("data.XML_DATA_TABLA_DE_APOYO_FILE").trim();     
             if(logger.isDebugEnabled()){
                 logger.debug("Global.XML_DATA_TABLA_DE_APOYO_FILE= "+Global.XML_DATA_TABLA_DE_APOYO_FILE);
             }
             Global.XML_NOMBRE_ARCHIVO_INFO_TABLA_DE_APOYO_FILE=PropertyReader.readProperty("data.XML_NOMBRE_ARCHIVO_INFO_TABLA_DE_APOYO_FILE").trim();      
             if(logger.isDebugEnabled()){
                 logger.debug("Global.XML_DATA_TABLA_DE_APOYO_FILE= "+Global.XML_DATA_TABLA_DE_APOYO_FILE);
             }
             Global.PATH_XML_DATA_TABLADE_APOYO=PropertyReader.readProperty("data.PATH_XML_DATA_TABLADE_APOYO").trim();
             if(logger.isDebugEnabled()){
                 logger.debug("Global.PATH_XML_DATA_TABLADE_APOYO= "+Global.PATH_XML_DATA_TABLADE_APOYO);
             }            
             Global.XML_DATA_FORMATO_TABLA_DE_APOYO_FILE=PropertyReader.readProperty("data.XML_DATA_FORMATO_TABLA_DE_APOYO_FILE").trim();
             
             if(logger.isDebugEnabled()){
                 logger.debug("Global.XML_DATA_FORMATO_TABLA_DE_APOYO_FILE= "+Global.XML_DATA_FORMATO_TABLA_DE_APOYO_FILE);
             }
             //Componente Listas de valores
             //Global.ARCHIVO_CACHELISTAS=PropertyReader.readProperty("data.ARCHIVO_CACHELISTAS").trim();
             //Cache de mensaje
             //Global.ARCHIVO_CACHE_MENSAJES=PropertyReader.readProperty("data.ARCHIVO_CACHE_MENSAJES").trim();
             //Cache del Producto.
             Global.CACHE_PRODUCTO_ARCHIVOS_XML=PropertyReader.readProperty("data.CACHE_PRODUCTO_ARCHIVOS_XML").trim();
             if(logger.isDebugEnabled()){
                 logger.debug("Global.CACHE_PRODUCTO_ARCHIVOS_XML ="+Global.CACHE_PRODUCTO_ARCHIVOS_XML);
             }
             Global.CACHE_PRODUCTO_ARCHIVOS_LOG=PropertyReader.readProperty("log.CACHE_PRODUCTO_ARCHIVOS_LOG").trim();
             
             if(logger.isDebugEnabled()){
                 logger.debug("Global.CACHE_PRODUCTO_ARCHIVOS_LOG="+Global.CACHE_PRODUCTO_ARCHIVOS_LOG);
             }
             //Manejador de Sesiones.
             //Global.DATA_SESSION_CLIENTE_FILE=PropertyReader.readProperty("data.DATA_SESSION_CLIENTE_FILE").trim();
             //listas de Valores    
             //Global.XML_DATA_LISTA_VALORES_FILES=PropertyReader.readProperty("data.XML_DATA_LISTA_VALORES_FILES").trim();
             //Generar XML de Data inicial de New ALEA
             //TODO: Estas lineas se comentaran si no se requiere generar producto                       
             //String generarXML=PropertyReader.readProperty("CacheSistema.generarXML").trim();
             //if(generarXML.equalsIgnoreCase("true")){
               //CacheSistema.generarXMLDataInicial();
             //}
             //TODO: DESCOMENTAR PARA PRUEBAS            
             //Carga de Data inicial de New ALEA.
             
             //CacheSistema.generarProducto("2");
             CacheSistema.cargarDataInicial();*/
             
             
         } catch (FactoryConfigurationError e) {
             logger.error("contextInitialized", e);            

         } catch (Exception e) {
             logger.error("contextInitialized", e);
         }
    }
        
    
    /**
     * Rutina de finalización de Sistema New ALEA,
     *se ejecuta cuando New ALEA es destruida de IAS.
     * 
     * @param event Evento a responder.
     */
    public void contextDestroyed(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        //MessageDealer messageDealer = (MessageDealer) context.getAttribute(Global.MESSAGEDEALER);
        //messageDealer.close();
 
    }
        
}
