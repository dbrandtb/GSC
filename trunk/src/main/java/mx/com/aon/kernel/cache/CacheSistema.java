/*
 * New Ice
 * Creado el 12/05/2005 14:16:00 (mm/dd/aaaa hh:mm:ss)
 *
 * Copyright (C) Biosnet & TCS Mexico.    
 * DF, Mexico. 
 * All rights reserved. Todos los derechos reservados.
 * http://www.biosnetmx.com
 */

package mx.com.aon.kernel.cache;

import org.apache.log4j.Logger;

public class CacheSistema {

//  Logger del sistema
    private static Logger  logger = Logger.getLogger(CacheSistema.class);


    /**
     * Constructor sin argumentos.
     */ 
    public CacheSistema(){
    }


    /**
     *  Este método carga la data del cache de producto. Si idProducto=TOTAL,
     *  realiza la carga de  todos los productos definidos en el cache, 
     *  en caso contrario, se carga la data del producto con id  correspondiente. 
     * 
     * @param idProducto Identificador del producto.
     */
    public static void cargarProducto(String idProducto) throws Exception{

        logger.info("Cargando data inicial de los productos: " + idProducto);

        if ( "TOTAL".equals(idProducto) ){
            try {
//                CacheProducto.cargaTodosLosProductos();            
            }catch (Exception ex) {
                logger.debug("### exception es " +  ex ); 
                logger.error(ex.getMessage());
                //throw new Exception("la carga de los productos" ); 
                throw new Exception(ex.getMessage()); 
            }          
        } else {

            try {
//                CacheProducto.cargaCacheProducto( idProducto );     
                logger.info("Se ha cargado el producto " + idProducto );
//            }catch(JAXBException jxbe){
//                logger.error("error "+jxbe);
//                throw new JAXBException(" la carga de la definición del producto al leer el archivo");            
            } catch (Exception ex) {
                logger.error(ex.getMessage()); 
                //throw new Exception("la carga del producto" );       
                throw new Exception(ex.getMessage());       


            }       
        }      
    }

    /**
     *  Este método carga la data del cache de producto. Si idProducto=TOTAL,
     *  realiza la carga de  todos los productos definidos en el cache, 
     *  en caso contrario, se carga la data del producto con id  correspondiente. 
     * 
     * @param idProducto Identificador del producto.
     */
    public static void generarProducto(String idProducto) throws Exception{

        logger.info("Generacion XML de los productos: " + idProducto);

        if ( "TOTAL".equals(idProducto) ){
            try {
//                CacheProducto.generaXMLTodosLosProductos();
            } catch (Exception ex) {
                logger.error(ex.getMessage());
                throw new Exception(ex.getMessage());            
            }          
        } else {
            try {
//                CacheProducto.generarXMLProducto(idProducto);
                if( logger.isDebugEnabled() ){
                    logger.info("Se ha generado XML del producto " + idProducto );
                }
//            }catch(JAXBException jxbe){
//                logger.error("error "+jxbe);
//                throw new JAXBException(" la generacion de la definición del producto al leer el archivo");            
            } catch (Exception ex) {
                logger.error(ex.getMessage()); 
                throw new Exception(ex.getMessage() );            
            }       
        }      
    }

    /**
     * Realiza la carga de toda la data del cache de mensajes.
     */
    public static void cargarMensajes() throws Exception{
        try{
//            CacheMensajes mensajes = new CacheMensajes();   
//            mensajes.cargaCacheMensajes();
            logger.info("Se cargó cache de mensajes.");
        } catch (Exception ex) {
            logger.error(ex);
            throw new Exception(ex.getMessage());            
        }            
    }

    /**
     * Realiza la carga de toda la data del cache de mensajes.
     */
    public static void generarMensajes() throws Exception{
        try{
//            CacheMensajes mensajes = new CacheMensajes();   
//            mensajes.creaXMLMensaje();
            logger.info("Se genero xml del cache de mensajes.");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new Exception(ex.getMessage());            
        }          
    }




  /**
	 * Realiza la carga de la data del componente Tablas de Apoyo. Si
	 * idTabla=TOTAL carga todas las tablas definidas en el componente; en otro
	 * caso solo carga la tabla definida en idTabla.
	 * 
	 * @param idTabla
	 *            Identificador de la tabla de valores
	 */
	public static void cargarTablaApoyo(String idTabla) throws Exception {
		if (logger.isInfoEnabled()) {
			logger.info("Cargando data  de la tabla apoyo : " + idTabla);
		}
		if ("TOTAL".equals(idTabla)) {
			try {
//				CacheDataTabladeApoyo.loadAll(); // esto momentaneamente
				// CacheDataTabladeApoyo.load("19");
				if (logger.isInfoEnabled()) {
					logger.info("Se han cargado todas las tablas de apoyo");
				}
			} catch (Exception ex) {
				logger.error("cargarTablaApoyo", ex);
				throw new Exception(ex.getMessage());
			}
		} else {
			try {
//				CacheDataTabladeApoyo.load(idTabla);
				if (logger.isInfoEnabled()) {
					logger.info("Se ha cargado la tabla " + idTabla);
				}
				// System.out.println("Se ha cargado la tabla " + idTabla );
			} catch (Exception ex) {
				logger.error("cargarTablaApoyo", ex);
				throw new Exception("La carga de la tabla de apoyo " + idTabla+ ' ' + ex.getMessage());
			}
		}

    }

	/**
	 * Realiza la carga de la data del componente Tablas de Apoyo. Si
	 * idTabla=TOTAL carga todas las tablas definidas en el componente; en otro
	 * caso solo carga la tabla definida en idTabla.
	 * 
	 * @param idTabla
	 *            Identificador de la tabla de valores
	 */
	public static void generarTablaApoyo(String idTabla) throws Exception {
		if (logger.isInfoEnabled()) {
			logger.info("Generando  inicial de la tabla: " + idTabla);
		}
//		GeneradorDataTabaladeApoyo gDataTabApoyo = new GeneradorDataTabaladeApoyo();
		if ("TOTAL".equals(idTabla)) {
			try {
//				gDataTabApoyo.generarXMLDataTabladeApoyo();
				// GeneradorDataFormatoTabladeApoyo.generarXMLFormatoTabladeApoyo();
				if (logger.isInfoEnabled()) {
					logger.info("Se han generado todas las tablas de apoyo");
				}
			} catch (Exception ex) {
				logger.error("generarTablaApoyo", ex);
				throw new Exception(ex.getMessage());
			}
		} else {
			try {
				// GeneradorDataFormatoTabladeApoyo.generarXMLFormatoTabladeApoyo();
//				gDataTabApoyo.generarXMLTabladeApoyo(idTabla);
				if (logger.isInfoEnabled()) {
					logger.info("Se ha generado la tabla " + idTabla);
				}
				// System.out.println("Se ha generado la tabla " + idTabla );
			} catch (Exception ex) {
				logger.error("generarTablaApoyo", ex);
				throw new Exception("La generacion de la tabla de apoyo "+ idTabla + ' ' + ex.getMessage());
			}
		}
	}



    /**
     * Realiza la carga de  la definición de bloques en New ALEA.
     */
    public static void cargarDefinicionBloques() throws Exception{
        logger.info("Cargando definicion de bloques: ");
        try {
//            CacheBloques.load();   
            logger.info("Se ha cargado la definición de los bloques " );

//        }catch(JAXBException jxbe){
//            logger.error("error "+jxbe,jxbe);
//            throw new JAXBException("  la carga de la definición de los bloques al leer el archivo");            
        }catch (Exception ex) {
            logger.error(ex);
            throw new Exception(" la carga de la definición de los bloques por " +ex);            
        }       

    }


    /**
     * Realiza la carga de todas las funciones definidas en el kernel de New ALEA.
     */
    public static void cargarFunciones() throws Exception{
        logger.info("Cargando lista de funciones: ");
        try {
//            ListaFunciones.cargarfunciones();  
            logger.info("Se ha cargado la lista de funciones " );
//        }catch(JAXBException jxbe){
//            logger.error("error "+jxbe);
//            throw new JAXBException(" la carga de la definición de los bloques al leer el archivo");            
        }catch (Exception ex) {

            logger.error(ex);
            throw new Exception("la carga de la lista de funciones por " + ex.getMessage());            
        }     
    }



    /**
     * Realiza la carga de la definición de las variables  globales del kernel de New ALEA.
     */
    public static void cargarDefinicionVariablesGlobales() throws Exception{
        logger.info("Cargando definicion de las variables globales: ");
        try {
//            CacheVariablesGlobales.load();   
            logger.info("Se ha cargado la definición de las variables globales " );

//        }catch(JAXBException jxbe){
//            logger.error("error "+jxbe);
//            throw new JAXBException(" la carga de la definición de las variables globales al leer el archivo de variables globales");            
        }catch (Exception ex) {
            logger.error(ex);
            throw new Exception("la carga de la definicion de las variables globales");            
        }   
    }


    /**
     * Realiza la carga en memoria de la precedencia de operadores del kernel de New ALEA.
     */
    public static void cargarPrecedencia() throws Exception{
        logger.info("Cargando precedencia de operadores: ");
        try {
//            ListaPrioridades.cargarPrioridades();  
            logger.info("Se ha cargado la precedencia de operadores " );
//        }catch(JAXBException jxbe){
//            logger.error("error "+jxbe);
//            throw new JAXBException(" La carga de la definición de los bloques al leer el archivo");            
        } catch (Exception ex) {
            logger.error(ex);
            throw new Exception("La carga de la precedencia de operadores" +
                    ex.getMessage());            
        }     
    }



    /**
     * Realiza la carga de Operadores del kernel de New ALEA.
     */
    public static void cargarOperadores() throws Exception{
        logger.info("Cargando operadores: ");
        try {
//            ListaOperadores.cargarOperadores();  
            logger.info("Se han cargado los operadores " );
//        }catch(JAXBException jxbe){
//            logger.error("error "+jxbe);
//            throw new JAXBException("La carga de la definición de los bloques al leer el archivo");            
        }  catch (Exception ex) {
            logger.error(ex);
            throw new Exception("La carga de los operadores" + ex.getMessage());            
        }   
    }


    /**
     * Realiza la  carga inicial de la data durante la inicialización del 
     * sistema New ALEA, por parte del servidor de aplicaciones IAS. 
     * Este método será llamado en el Listener de inicialización de New ALEA.
     * 
     */
    public static void cargarDataInicial(){
        logger.info("Cargando data inicial de New ALEA...");

        try{
            if(logger.isDebugEnabled())
                logger.debug("INICIO Carga data Kernel MEMORIA EN USO=  " + Runtime.getRuntime().totalMemory());

            CacheSistema.cargarDefinicionBloques();
            CacheSistema.cargarDefinicionVariablesGlobales();
            CacheSistema.cargarFunciones();
            CacheSistema.cargarOperadores();
            CacheSistema.cargarTablaApoyo("TOTAL");
            CacheSistema.cargarPrecedencia();

            if(logger.isDebugEnabled())
                logger.debug("FIN Carga data Kernel MEMORIA EN USO=  " + Runtime.getRuntime().totalMemory());

        } catch(Exception e){
            logger.error("error en carga inicial cache sistema.. ",e);          
            logger.error("Fallo la carga de bloques, funciones, operadores tabla de apoyo y precedencia de operadores");
            
        }
        try{

            if(logger.isDebugEnabled())
                logger.debug("INICIO Carga data Productos MEMORIA EN USO=  " + Runtime.getRuntime().totalMemory());

            CacheSistema.cargarProducto("TOTAL");
//          CacheSistema.cargarProducto("201");

            if(logger.isDebugEnabled())
                logger.debug("FIN Carga data Productos MEMORIA EN USO=  " + Runtime.getRuntime().totalMemory());


        }catch(Exception e){
            logger.error("Exception en carga de productos",e);
            logger.info("Error al cargar productos: "+e.getMessage());
            if(logger.isDebugEnabled())
                logger.debug("Error al cargar productos: "+e.getMessage());      
        }
    }




    /**
     * Realiza la  carga inicial de la data durante la inicialización del 
     * sistema New ALEA, por parte del servidor de aplicaciones IAS. 
     * Este método será llamado en el Listener de inicialización de New ALEA.
     * 
     */
    public static void generarXMLDataInicial(){
        logger.info("Generando XML de data inicial de New ALEA...");

        //TODO: SE COMENTA BLOQUE HASTA QUE ESTE TERMINADA ESTA CLASE... :p
//      try{
//      CacheSistema.generarMensajes();
//      }catch(Exception e){
//      logger.error(e);
//      logger.info("Error al generar XML de Mensajes : "+e.getMessage());
//      if(logger.isDebugEnabled())
//      logger.debug("Error al generar XML de Mensajes : "+e.getMessage());      
//      }



//        String generarXMLTablaApoyo=PropertyReader.readProperty("CacheSistema.generarXMLTablasApoyo").trim().toLowerCase();
        if(logger.isDebugEnabled())
//            logger.debug("generarXMLTablaApoyo="+Boolean.getBoolean(generarXMLTablaApoyo));

//        if(generarXMLTablaApoyo.equals("true")){
            try{
                logger.info("Generando XML de Tablas de Apoyo...");
                CacheSistema.generarTablaApoyo("TOTAL");
            } catch(Exception e){
                logger.error(e,e);
                logger.info("Error al generar XML de Tabla de Apoyo: "+e.getMessage());
                if(logger.isDebugEnabled()){
                    logger.debug("Error al generar XML de Tabla de Apoyo: "+e.getMessage());
                }
            }
//        }


//        String generarXMLProductos=PropertyReader.readProperty("CacheSistema.generarXMLProductos").trim().toLowerCase();
        if(logger.isDebugEnabled())
//            logger.debug("generarXMLProductos="+generarXMLProductos);
//        if(generarXMLProductos.equals("true")){
            try{

                logger.info("Generando XML de Productos...");
                CacheSistema.cargarDefinicionVariablesGlobales();
                CacheSistema.cargarFunciones();
                CacheSistema.generarProducto("TOTAL");
            }catch(Exception e){
                logger.error(e);
                logger.info("Error al generar XML de Productos: "+e.getMessage());
                if(logger.isDebugEnabled())
                    logger.debug("Error al generar XML de Productos: "+e.getMessage());      
            }
//        }

        System.gc();
    } 

    /**
     * 
     * @param args
     */
    public static void main(String[] args)
    {

        try 
        {
            CacheSistema.cargarDefinicionBloques();
            CacheSistema.cargarDefinicionVariablesGlobales();
            CacheSistema.cargarFunciones();
            CacheSistema.cargarOperadores();
            CacheSistema.cargarTablaApoyo("TOTAL");
            CacheSistema.cargarPrecedencia();
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
    }
}
