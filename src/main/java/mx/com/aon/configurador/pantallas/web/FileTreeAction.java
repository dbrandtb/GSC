package mx.com.aon.configurador.pantallas.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Enumeration;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

public class FileTreeAction extends PrincipalConfPantallaAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -312785278466788481L;
	
	protected final transient Logger logger = Logger.getLogger(FileTreeAction.class);

	/**
	 * Nombre del directorio raíz que se tomará para el componente FileTreeMenu
	 */
	private static final String DIR_ROOT_NAME = "resources/images";
	
	private String REAL_PATH_ROOT_NAME;
	
	private static final String RESULT_FILE_TREE = "RESULT_FILE_TREE";
	
	//Parametros que envia el componente FileTreeMenu de ext js 2 al Action
	private String cmd;
	//get
	private String path;
	//rename
	private String newname;
	private String oldname;
	//delete
	private String file;
	//newdir
	private String dir;
	
	/**
	 * Atributo que contiene la lista de Archivos en formato json que se van a mostrar en el FileTreeMenu (comando get)
	 */
	private String listaArchivos;
	
	/**
	 * String con contenido en formato json con resultado tomado en el metodo comandoRename()
	 */
	private String resultadoRename;
	
	/**
	 * String con contenido en formato json con resultado tomado en el metodo comandoDelete()
	 */
	private String resultadoDelete;
	
	/**
	 * String con contenido en formato json con resultado tomado en el metodo comandoNewDir()
	 */
	private String resultadoNewDir;
	
	/**
	 * String con contenido en formato json con resultado tomado en el metodo comandoUpload()
	 */
	private String resultadoUpload;

	// private List<Archivo> fileList;

	public String execute() throws Exception {
		/*
		// ServletContext ctx = this.getServletContext();
		ServletContext ctx = ServletActionContext.getServletContext();
		String dirRoot = ctx.getRealPath(DIR_ROOT_NAME);
		*/
		//logger.debug("dirDestino: " + dirDestino);
		// String dirDestino = request.getRealPath("./Filetree");//deprecated
		
		
		/**
		 * Ruta completa del directorio raíz DIR_ROOT_NAME
		 */
		REAL_PATH_ROOT_NAME = ServletActionContext.getServletContext().getRealPath(DIR_ROOT_NAME); 
		
		if (cmd.equals("get")) {
			return this.comandoGet();
		} else if (cmd.equals("rename")) {
			return this.comandoRename();
		} else if (cmd.equals("newdir")) {
			return this.comandoNewDir();
		} else if (cmd.equals("delete")) {
			return this.comandoDelete();
		} else if (cmd.equals("upload")) {
			return this.comandoUpload();
		}else{
			return ERROR;
		}
	}


	// Generar String con el listado de archivos en formato json
	public String comandoGet() {

		StringBuffer buffJsonResponse = new StringBuffer();
		buffJsonResponse.append("[");

		String rutaCompleta = REAL_PATH_ROOT_NAME;
		if (path != null) {
			rutaCompleta += "/" + path;
		}

		File dir = new File(rutaCompleta);

		logger.debug("dir:" + dir);
		
		/* Archivos del directorio */
		String text = null;
		String iconCls = null;
		boolean disabled = false;
		boolean leaf = false;
		String qtip = null;

		// String[] nombreArchivos = dir.list();
		File[] archivos = dir.listFiles();
		if(archivos != null){
			for (int i = 0; i < archivos.length; i++) {
				text = archivos[i].getName();
				// Si el archivo es de solo lectura, disabled = true
				if (!archivos[i].canWrite()) {
					disabled = true;
				}
				// Si es directorio:
				if (archivos[i].isDirectory()) {
					leaf = false;
					iconCls = "'iconcls':'folder'";
					qtip = "";
				} else {// Si es un archivo:
					leaf = true;

					// Obtener la extension del archivo
					String[] nombreArch = archivos[i].getName().trim().split("\\.");
					String extension = nombreArch[nombreArch.length - 1];
					iconCls = "'iconCls':'file-" + extension + "'";
					qtip = ",'qtip':'Size: " + archivos[i].length() + " Bytes'";
				}

				buffJsonResponse.append("{'text':'" + text + "'," + iconCls
						+ ",'disabled':" + disabled + ",'leaf':" + leaf + qtip
						+ "}");
				// si no es el ultimo elemento, le agregamos una coma
				if (i != (archivos.length - 1)) {
					buffJsonResponse.append(",");
				}
			}
		}
		
		buffJsonResponse.append("]");
		listaArchivos = buffJsonResponse.toString();
		logger.debug("listaArchivos:" + listaArchivos);
		return RESULT_FILE_TREE;
	}
	
	

	public String comandoRename() {
		
		try{
			//copiar archivo a la nueva ruta
			copiaArchivo(REAL_PATH_ROOT_NAME + System.getProperty("file.separator") + oldname, REAL_PATH_ROOT_NAME + System.getProperty("file.separator") + newname);
			//borrar el archivo antiguo
			File fileToDelete = new File(REAL_PATH_ROOT_NAME + System.getProperty("file.separator") + oldname);
			fileToDelete.delete();
			resultadoRename = "{'success':true}";
		}catch(IOException io){
			resultadoRename = "{'success':false,'error':'Cannot rename file " +oldname+ " to " +newname+ "'}";
			////TODO tratar excepcion
			//io.printStackTrace();
		}
		
		return RESULT_FILE_TREE;
	}

	public String comandoNewDir() {
		
		 File directorio = new File(REAL_PATH_ROOT_NAME + System.getProperty("file.separator") + dir);
		 
		 if(directorio.mkdir()){
			  //Creado exitosamente
			 resultadoNewDir = "{'success':true}";
		 }else{
			 //No se pudo crear
			 resultadoNewDir = "{'success':false,'error':'Cannot create directory: " +dir+ "'}";
		 }
		
		return RESULT_FILE_TREE;
	}

	public String comandoDelete() {
		
		File fileToDelete = new File(REAL_PATH_ROOT_NAME + System.getProperty("file.separator") + file);
		
		if (fileToDelete.delete()){
			//logger.debug("El archivo ha sido borrado satisfactoriamente");
			resultadoDelete = "{'success':true}";
		}else{
			//logger.debug("El archivo no puede ser borrado");
			resultadoDelete = "{'success':false,'error':'Cannot delete: " +file+ "'}";
		}
		return RESULT_FILE_TREE;
	}

	//Sube un archivo a la vez
	public String comandoUpload() {
		
		if(subeArchivo()){
			resultadoUpload = "{'success':true}";
		}else{
			//TODO agregarle el nombre del archivo que no se pudo subir
			resultadoUpload = "{'success':false,'errors':{'Error':'File upload error.'}}";
		}
		
		return RESULT_FILE_TREE;
	}
	
	private void copiaArchivo(String nombreFuente, String nombreDestino) throws IOException {
		
		FileInputStream fis = new FileInputStream(nombreFuente);
		FileOutputStream fos = new FileOutputStream(nombreDestino);
		FileChannel canalFuente = fis.getChannel();
		FileChannel canalDestino = fos.getChannel();
		canalFuente.transferTo(0, canalFuente.size(), canalDestino);
		fis.close();
		fos.close();
	}
	
	//Sube un archivo a la vez (ya que el componente de ext maneja varios archivos, pero los envía uno a uno)
	private boolean subeArchivo() {
		
		MultiPartRequestWrapper multipartRequest = ((MultiPartRequestWrapper)ServletActionContext.getRequest());
		
		String nombreParametro = null; 
		
		Enumeration<String> enumNombreParametros = multipartRequest.getFileParameterNames();
		while( enumNombreParametros.hasMoreElements() ){
			//Solo tomaremos un solo nombre de parametro, suponiendo que se envió solo uno
			nombreParametro = enumNombreParametros.nextElement();
		}
		
		String nombreArchivo = "";
		String []nombres = multipartRequest.getFileNames(nombreParametro);
		for(int i=0; i<nombres.length;i++){
			nombreArchivo = nombres[i];
		}

		//getFiles
		File[] files = multipartRequest.getFiles(nombreParametro);
		for(int i=0; i<files.length;i++){
			logger.debug("archivos:" + files[i].getAbsolutePath());
		}
		
		//Solo tomaremos un archivo
		File fileToUpload = files[0];
		
		// Following code can be used to save the uploaded file
		try {
			
			String rutaRelativa = "";
			//si el archivo no estará en la raíz, se agrega el path y un separador
			if(path.trim().length() > 0){
				rutaRelativa = path + System.getProperty("file.separator"); 
			}
			
			String fullFileName = REAL_PATH_ROOT_NAME + System.getProperty("file.separator") + rutaRelativa + nombreArchivo;
			File theFile = new File(fullFileName);
			
			//subir el archivo
			FileUtils.copyFile(fileToUpload, theFile);
			logger.debug("nombreArchivo:" + nombreParametro);
			logger.debug("fullFileName:" + fullFileName);
			return true;
			
		} catch (Exception e) {
			logger.debug("Error:" + e);
			return false;
		}
	}
	
	
	// GETTERS AND SETTERS
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getListaArchivos() {
		return listaArchivos;
	}

	public void setListaArchivos(String listaArchivos) {
		this.listaArchivos = listaArchivos;
	}


	public String getNewname() {
		return newname;
	}


	public void setNewname(String newname) {
		this.newname = newname;
	}


	public String getOldname() {
		return oldname;
	}


	public void setOldname(String oldname) {
		this.oldname = oldname;
	}


	public String getResultadoRename() {
		return resultadoRename;
	}


	public void setResultadoRename(String resultadoRename) {
		this.resultadoRename = resultadoRename;
	}


	public String getFile() {
		return file;
	}


	public void setFile(String file) {
		this.file = file;
	}


	public String getResultadoDelete() {
		return resultadoDelete;
	}


	public void setResultadoDelete(String resultadoDelete) {
		this.resultadoDelete = resultadoDelete;
	}


	public String getDir() {
		return dir;
	}


	public void setDir(String dir) {
		this.dir = dir;
	}


	public String getResultadoNewDir() {
		return resultadoNewDir;
	}


	public void setResultadoNewDir(String resultadoNewDir) {
		this.resultadoNewDir = resultadoNewDir;
	}


	public String getResultadoUpload() {
		return resultadoUpload;
	}


	public void setResultadoUpload(String resultadoUpload) {
		this.resultadoUpload = resultadoUpload;
	}
}