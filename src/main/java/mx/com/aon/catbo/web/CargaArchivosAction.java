package mx.com.aon.catbo.web;

import com.opensymphony.xwork2.ActionContext;
import org.apache.log4j.Logger;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import mx.com.aon.catbo.model.MediaTO;
import mx.com.aon.catbo.service.CargaArchivosManager;
import mx.com.aon.catbo.service.MediaDAO;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.core.ApplicationException;

import java.io.File;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;

public class CargaArchivosAction extends AbstractListAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(CargaArchivosAction.class);
	private transient CargaArchivosManager cargaArchivosManager;

    private String directorio;
    private String fechaDesde;
    private String fechaHasta;
    private String descripcion;
    private String nombre;
    private String pathCompletoArchivoBajada;
    
    @SuppressWarnings("unchecked")
	private List mCargaArchivosList;
    private String cmd;
	private String archivo;
	private String resultadoUpload;

	public void setCargaArchivosManager(CargaArchivosManager cargaArchivosManager) {
		this.cargaArchivosManager = cargaArchivosManager;
	}
	   
     public String cmdClickBuscarArchivos() throws Exception{
        
    	 try{    		     		  
    		 PagedList pagedList = cargaArchivosManager.obtenerArchivos(directorio,fechaDesde, fechaHasta, start, limit);                     
             mCargaArchivosList = pagedList.getItemsRangeList();
             totalCount = pagedList.getTotalItems();           
             success = true;
             return SUCCESS;
            
        }catch(ApplicationException e)
        {
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
            
        }
    }

   private InputStream inputStream;

   public InputStream getInputStream()
   {
   return inputStream;
   }
    
    public String cmdClickBajarArchivo() throws Exception{
    	try{
    	HttpServletResponse response = ServletActionContext.getResponse();
    	

    	//String filePath = "c:/";
    	String fileName = "descargaLog.txt";  	 
 
    	inputStream = (InputStream) new FileInputStream(pathCompletoArchivoBajada);

//    	ServletOutputStream out = response.getOutputStream();
    	
    	response.setContentType("application/octet-stream");
    	response.setHeader("Content-Disposition","attachment; filename="+pathCompletoArchivoBajada);
    	response.setContentLength(inputStream.available());

/*    	int c;
    	while((c=inputStream.read()) != -1){
    	out.write(c);
    	}
    	out.flush();
    	out.close();
    	fileToDownload.close();
*/    	
        success = true;
        return SUCCESS;
            
        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
            
        }
    }

   public String cmdGuardaArchivos() throws Exception{

		String nomParam = null, msg = "";
		try {
			logger.debug("cmd: " + cmd);	
		if (cmd != null && !cmd.equals("")) {
	    	logger.debug("Estamos por subir el archivo en el directorio: " + directorio);
			logger.debug("cmd: " + cmd);
	    	MultiPartRequestWrapper multiPartRequestWrapper = ((MultiPartRequestWrapper)ServletActionContext.getRequest());
			Enumeration<String> enumParams = multiPartRequestWrapper.getFileParameterNames();
				while (enumParams.hasMoreElements()) {
					nomParam = enumParams.nextElement();
					String [] nombreArchivo = multiPartRequestWrapper.getFileNames(nomParam);						
					logger.debug(enumParams);
					logger.debug("Parametro: " + nomParam);
					File[] files = multiPartRequestWrapper.getFiles(nomParam);	
					FileInputStream fileInputStream = new FileInputStream(files[0]);		
					if ((nombreArchivo[0].contains(".csv")))
						msg = this.cargaArchivosManager.guardaArchivos(fileInputStream, directorio, nombreArchivo[0]);
					else { success = false;
		            msg="El tipo de archivo debe ser con extensión .csv";
		            resultadoUpload = "{'success':false,'errors':{'Error':'" + msg + "'}, actionErrors:['" + msg + "']}";
		            return SUCCESS;
		            }
				
				}
			//}
		}else {
			InputStream inputStream = new ByteArrayInputStream("".getBytes());
			
			addActionMessage(msg);
		}
		resultadoUpload = "{'success':true, actionMessages:['" + msg + "']}";
		success = true;
		return SUCCESS;
		} catch (Exception e) {
			logger.error("Error al subir el archivo en el directorio: " + directorio );
			resultadoUpload = "{'success':false,'errors':{'Error':'" + e.getMessage() + "'}, actionErrors:['" + e.getMessage() + "']}";
			addActionError(e.getMessage());
			success = false;
			return SUCCESS;
		}
	}

public String getCmd() {
	return cmd;
}

public void setCmd(String cmd) {
	this.cmd = cmd;
}

public String getArchivo() {
	return archivo;
}

public void setArchivo(String archivo) {
	this.archivo = archivo;
}



public String getResultadoUpload() {
	return resultadoUpload;
}

public void setResultadoUpload(String resultadoUpload) {
	this.resultadoUpload = resultadoUpload;
}

public String getDirectorio() {
	return directorio;
}

public void setDirectorio(String directorio) {
	this.directorio = directorio;
}

public String getFechaDesde() {
	return fechaDesde;
}

public void setFechaDesde(String fechaDesde) {
	this.fechaDesde = fechaDesde;
}

public String getFechaHasta() {
	return fechaHasta;
}

public void setFechaHasta(String fechaHasta) {
	this.fechaHasta = fechaHasta;
}

public List getMCargaArchivosList() {
	return mCargaArchivosList;
}

public void setMCargaArchivosList(List cargaArchivosList) {
	mCargaArchivosList = cargaArchivosList;
}

public String getDescripcion() {
	return descripcion;
}

public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
}

public String getNombre() {
	return nombre;
}

public void setNombre(String nombre) {
	this.nombre = nombre;
}

public String getPathCompletoArchivoBajada() {
	return pathCompletoArchivoBajada;
}

public void setPathCompletoArchivoBajada(String pathCompletoArchivoBajada) {
	this.pathCompletoArchivoBajada = pathCompletoArchivoBajada;
}







}
