package mx.com.aon.portal.util;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * sergio.ramirez
 */
public class ImageUtils  extends ActionSupport{
	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = -8210311730107683971L;
	private static final transient Log log = LogFactory.getLog(ImageUtils.class);
	//private static final String DIR_SALIDA = "resources/imagesUser";
	//private static final String DIR_SALIDA = "resources/images/imagesUser";
	

	//ruta de carpeta externa   para QA
	//private static final String DIR_SALIDA = "/opt/oracle/product/1013/soa/Apache/Apache/htdocs/resources/";
	
	// ruta de carpeta externa para Prepod
	private static final String DIR_SALIDA = "/opt/oracle/product/10.1.3.1/OracleAS_1/Apache/Apache/htdocs/resources/";
	
	
	//	private static final String DIR_SALIDA = PropertyReader.readProperty("img.url.confpagimage");
	
   // private static String pruebaRuta = PropertyReader.readProperty("xml.url.file.test");
	
	
	private static String REAL_PATH_NAME;
	private static boolean success;
	private static String id;
	private static String nombreFile;
	private static String extension;
	
	
	/**
	 * Metodo que comprueba la extension del archivo
	 * @param cadena
	 * @return
	 */
	public static boolean compruebaExtencion(String cadena){

		String [] stringFinal = cadena.split("\\.");
		String extension = stringFinal[1];
		log.debug("extension:"+ extension);
		if(extension.equalsIgnoreCase("jpg")|| extension.equalsIgnoreCase("pdf") || extension.equalsIgnoreCase("gif")){
			log.debug("EXTENSION CORRECTA");
			success=true;
			return true;
		}else{
			log.debug("EXTENSION INCORRECTA");
			success=false;
			return false;
		}
	}
	
	
	public static String getExtension(String tipo){
		
		if(tipo.equals("IMAGEN")){
			tipo=".JPG";
		}else{
			tipo=".PDF";
		}
		return tipo;
	}
	
	
	
	/**
	 * Metodo que sirve para subir un archivo al servidor.
	 * @param fileToUpload
	 * @param path
	 * @return
	 */
	
	public static String subeArchivo(File fileToUpload) {
		id=(String) ActionContext.getContext().getSession().get("CLAVE_ELEMENTO");
		nombreFile= (String) ActionContext.getContext().getSession().get("NOMBRE_SECCION");
		extension=".JPG";
		
		log.debug("id:"+ id);
		REAL_PATH_NAME = ServletActionContext.getServletContext().getRealPath(DIR_SALIDA);
		log.debug("REAL_PATH_NAME"+REAL_PATH_NAME);
		try {
			String nombreArchivo;
			nombreArchivo = id+nombreFile+extension;
			log.debug("NAME_FILE:"+ nombreArchivo);
			
			log.debug("nombreArchivo:"+ nombreArchivo);
			
			String thePath = REAL_PATH_NAME + System.getProperty("file.separator") ;
			File theFile = new File(thePath,nombreArchivo);			
			
			FileUtils.copyFile(fileToUpload, theFile);
			log.debug("theFile:" + theFile);
			String ruta = theFile.getAbsolutePath();
			log.debug("ruta:"+ ruta);
			success = true;
			return ruta;
		
			} catch (Exception e) {			
				log.debug("Error:" + e);
				success = false;
				return null;
			}

		}
		
		
		
	/**
	 * 
	 * @param file
	 * @return
	 */
	public static String subeFile(File file){
		id=(String) ActionContext.getContext().getSession().get("CLAVE_ELEMENTO");
		nombreFile= (String) ActionContext.getContext().getSession().get("NOMBRE_SECCION");
		extension=".PDF";
		
		log.debug("id:"+ id);
		REAL_PATH_NAME = ServletActionContext.getServletContext().getRealPath(DIR_SALIDA);
		log.debug("REAL_PATH_NAME"+REAL_PATH_NAME);
		try {
			String nombreArchivo;
			nombreArchivo = id+nombreFile+extension;
			log.debug("NAME_FILE:"+ nombreArchivo);
			
			log.debug("nombreArchivo:"+ nombreArchivo);
			
			String thePath = REAL_PATH_NAME + System.getProperty("file.separator");
			log.debug("thePath=" + thePath);
			
			
			File theFile = new File(thePath,nombreArchivo);			
			
			FileUtils.copyFile(file, theFile);
			log.debug("theFile:" + theFile);
			String ruta = theFile.getAbsolutePath();
			log.debug("ruta:"+ ruta);
			success = true;
			return ruta;
		
			} catch (Exception e) {			
				log.debug("Error:" + e);
				success = false;
				return null;
			}

	}
	
	/**
	 * @param File file
	 * @param String extension
	 * @return String
	 */
	
	/*
	public static String uploadAnyFile(File file, String fileName){
		id=(String) ActionContext.getContext().getSession().get("CLAVE_ELEMENTO");
		nombreFile= (String) ActionContext.getContext().getSession().get("NOMBRE_SECCION");
		//extension=".PDF";
		
		log.debug("id:"+ id);
		REAL_PATH_NAME = ServletActionContext.getServletContext().getRealPath(DIR_SALIDA);
		log.debug("REAL_PATH_NAME"+REAL_PATH_NAME);
		try {
			String nombreArchivo;
			//nombreArchivo = id+nombreFile+ext;
			nombreArchivo = fileName;
			log.debug("NAME_FILE:"+ nombreArchivo);			
			log.debug("nombreArchivo:"+ nombreArchivo);
			
			String thePath = REAL_PATH_NAME + System.getProperty("file.separator") ;
			File theFile = new File(thePath,nombreArchivo);			
			
			FileUtils.copyFile(file, theFile);
			log.debug("theFile:" + theFile);
			String ruta = theFile.getAbsolutePath();
			log.debug("ruta:"+ ruta);
			success = true;
			return ruta;
		
			} catch (Exception e) {			
				log.debug("Error:" + e);
				success = false;
				return null;
			}

	}
	*/
	
	/* crea archivo en una ruta especifica */
	
	public static String uploadAnyFile(File file, String fileName){
		id=(String) ActionContext.getContext().getSession().get("CLAVE_ELEMENTO");
		nombreFile= (String) ActionContext.getContext().getSession().get("NOMBRE_SECCION");
		//extension=".PDF";
		
		//log.debug("pruebaRuta=" + pruebaRuta );
		log.debug("DIR_SALIDA=" + DIR_SALIDA);
		
		
		log.debug("id:"+ id);
		REAL_PATH_NAME = DIR_SALIDA;
		log.debug("REAL_PATH_NAME"+REAL_PATH_NAME);
		try {
			String nombreArchivo;
			//nombreArchivo = id+nombreFile+ext;
			nombreArchivo = fileName;
			log.debug("nombreArchivo:"+ nombreArchivo);
			
			String thePath = REAL_PATH_NAME;
			File theFile = new File(thePath,nombreArchivo);			
			
			FileUtils.copyFile(file, theFile);
			log.debug("theFile:" + theFile);
			String ruta = theFile.getAbsolutePath();
			log.debug("getAbsolutePath"+ ruta);
			log.debug("RUTA="+ ruta);
			success = true;
			return ruta;
		
			} catch (Exception e) {			
				log.debug("Error:" + e);
				success = false;
				return null;
			}

	}
	
	

	/**
	 * 
	 * @return
	 */
	public static String getREAL_PATH_NAME() {
	return REAL_PATH_NAME;
	}

	/**
	 * 
	 * @param real_path_name
	 */
	public static void setREAL_PATH_NAME(String real_path_name) {
	REAL_PATH_NAME = real_path_name;
	}
	/**
	 * 
	 * @return
	 */
	public static String getDIR_SALIDA() {
	return DIR_SALIDA;
	}

	/**
	 * 
	 * @return
	 */
	public static boolean isSuccess() {
		return success;
	}

	/**
	 * 
	 * @param success
	 */
	public static void setSuccess(boolean success) {
		ImageUtils.success = success;
	}


	/**
	 * 
	 * @return
	 */
	public static String getId() {
		return id;
	}


	/**
	 * 
	 * @param id
	 */
	public static void setId(String id) {
		ImageUtils.id = id;
	}
	/**
	 * 
	 * @return
	 */
	public static String getNombreFile() {
		return nombreFile;
	}
	/**
	 * 
	 * @param nombreFile
	 */
	public static void setNombreFile(String nombreFile) {
		ImageUtils.nombreFile = nombreFile;
	}
	/**
	 * 
	 * @return
	 */
	public static String getExtension() {
		return extension;
	}
	/**
	 * 
	 * @param extension
	 */
	public static void setExtension(String extension) {
		ImageUtils.extension = extension;
	}

	
}	



