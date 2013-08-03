package mx.com.aon.catbo.web;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;

import mx.com.aon.catbo.model.BackBoneResultVO;
import mx.com.aon.catbo.model.MediaTO;
import mx.com.aon.catbo.service.ArchivosManager;
import mx.com.aon.catbo.service.MediaDAO;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.utils.Constantes;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import com.opensymphony.xwork2.ActionContext;

public class ArchivoAction extends AbstractListAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(ArchivoAction.class);

    private transient ArchivosManager archivosManager;
    @SuppressWarnings("unused")
	private transient ArchivosManager archivosManagerJdbcTemplate;
    private transient MediaDAO mediaDAOJdbc;

    private String nmcaso;
    private String nmovimiento;
    private String nmarchivo;
    @SuppressWarnings("unused")
	private String direcc;
    private String tipoArchivo;
    private String dsArchivo;
    @SuppressWarnings("unchecked")
	private List mArchivosList;
    private String cmd;
	private String archivo;
	private String resultadoUpload;
	private String dsnomarc; // para el nombre real del archivo
	private InputStream inputStream;
	private String filename;

    public void setArchivosManager(ArchivosManager archivosManager) {
        this.archivosManager = archivosManager;
    }

    public String getNmcaso() {
        return nmcaso;
    }

    public void setNmcaso(String nmcaso) {
        this.nmcaso = nmcaso;
    }

    public String getNmovimiento() {
        return nmovimiento;
    }

    public void setNmovimiento(String nmovimiento) {
        this.nmovimiento = nmovimiento;
    }

    public String getNmarchivo() {
        return nmarchivo;
    }

    public void setNmarchivo(String nmarchivo) {
        this.nmarchivo = nmarchivo;
    }


    @SuppressWarnings("unchecked")
	public List getMArchivosList() {
        return mArchivosList;
    }

    @SuppressWarnings("unchecked")
	public void setMArchivosList(List mArchivosList) {
        this.mArchivosList = mArchivosList;
    }

    public String cmdClickBuscarArchivos() throws Exception{
        try{
            PagedList pagedList = archivosManager.obtenerArchivos(nmcaso,nmovimiento, start, limit);
            mArchivosList = pagedList.getItemsRangeList();
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
    
   public String cmdGuardaArchivos() throws Exception{

		String nomParam = null, msg = "";
		try {
		if (cmd != null && !cmd.equals("")) {
	    	MultiPartRequestWrapper multiPartRequestWrapper = ((MultiPartRequestWrapper)ServletActionContext.getRequest());
			Enumeration<String> enumParams = multiPartRequestWrapper.getFileParameterNames();
			while (enumParams.hasMoreElements()) {
				nomParam = enumParams.nextElement();
				logger.debug("Parametro: " + nomParam);
				File[] files = multiPartRequestWrapper.getFiles(nomParam);
				FileInputStream fileInputStream = new FileInputStream(files[0]);
				//String archivosReturn = ImageUtils.uploadAnyFile(files[0], multiPartRequestWrapper.getFileNames(nomParam)[0]);
				MediaTO mediaTO = new MediaTO ();
				mediaTO.setCdTipoArchivo(tipoArchivo);
				mediaTO.setDsArchivo(dsArchivo);
				//mediaTO.setFilename(files[0].getName());
				mediaTO.setFilename(dsnomarc);
				mediaTO.setIdCaso(nmcaso);
				mediaTO.setIdDocumento(nmarchivo);
				//mediaTO.setNmMovimiento((String)ConvertUtil.nvl(nmovimiento));
				mediaTO.setNmMovimiento(nmovimiento);
				UserVO userVO = (UserVO)ActionContext.getContext().getSession().get("USUARIO");
				mediaTO.setUser(userVO.getUser());
				
				
				logger.debug("HHHH el nombre del archivo es:" +mediaTO.getFilename());
				
				logger.debug("tipoArchivo: "+tipoArchivo);
				logger.debug("dsArchivo: "+dsArchivo);
				logger.debug("files[0].getName(): "+files[0].getName());
				logger.debug("nmcaso: "+nmcaso);
				logger.debug("nmarchivo: "+nmarchivo);
				logger.debug("nmovimiento: "+nmovimiento);
				logger.debug("userVO.getUser(): "+userVO.getUser());
				//archivosManagerJdbcTemplate.guardaArchivosCaso(mediaTO, fileInputStream, (int)files[0].length());
				//msg = mediaDAOJdbc.guardarArchivo(mediaTO, fileInputStream, (int)files[0].length());
				BackBoneResultVO backBoneResultVO = mediaDAOJdbc.guardarArchivo(mediaTO, fileInputStream, (int)files[0].length());
				nmovimiento = backBoneResultVO.getOutParam();
				//addActionMessage(backBoneResultVO.getMsgText());
				msg = backBoneResultVO.getMsgText();
			}
		}else {
			MediaTO mediaTO = new MediaTO();
			mediaTO.setCdTipoArchivo(tipoArchivo);
			mediaTO.setDsArchivo(dsArchivo);
			mediaTO.setFilename(dsnomarc);
			mediaTO.setIdCaso(nmcaso);
			mediaTO.setIdDocumento(nmarchivo);
			mediaTO.setNmMovimiento(nmovimiento);
			InputStream inputStream = new ByteArrayInputStream("".getBytes());
			UserVO userVO = (UserVO)ActionContext.getContext().getSession().get("USUARIO");
			mediaTO.setUser(userVO.getUser());
			//msg = archivosManagerJdbcTemplate.guardaArchivosCaso(mediaTO, inputStream, 0);
			//msg = mediaDAOJdbc.guardarArchivo(mediaTO, inputStream, 0);
			BackBoneResultVO backBoneResultVO = mediaDAOJdbc.guardarArchivo(mediaTO, inputStream, 0);
			nmovimiento = backBoneResultVO.getOutParam();
			addActionMessage(backBoneResultVO.getMsgText());
			msg = backBoneResultVO.getMsgText();
		}
		resultadoUpload = "{'success':true, actionMessages:['" + msg + "'], nmovimiento:'"+nmovimiento+"'}";
		success = true;
		return SUCCESS;
		} catch (Exception e) {
			resultadoUpload = "{'success':false,'errors':{'Error':'" + e.getMessage() + "'}, actionErrors:['" + e.getMessage() + "']}";
			addActionError(e.getMessage());
			success = false;
			return SUCCESS;
		}
	}
   

   /**
    * Metodo para la descarga de los archivos de los Movimientos en los casos de BO
    * @return
    */
   public String cmdDescargarArchivo(){
	   		if(logger.isDebugEnabled()){
	   			
	   			logger.debug("Parametros de entrada para la descarga del archivo");
	   			logger.debug("nmcaso: "+ nmcaso);
	   			logger.debug("nmovimiento: "+ nmovimiento);
	   			logger.debug("nmarchivo: "+ nmarchivo);
	   			
	   		}
			/*HttpServletResponse response = ServletActionContext.getResponse();
			response.reset();*/
			
			MediaTO descarga = null;
			try{
				descarga = mediaDAOJdbc.descargarArchivo(nmcaso, nmovimiento, nmarchivo);
				mediaDAOJdbc.descargarArchivoJdbcPuro(nmcaso, nmovimiento, nmarchivo);
				
				if(descarga == null){
					success = false;
					addActionError("No se encontraron datos para el archivo solicitado");
					if(logger.isDebugEnabled())logger.debug("No se encontraron datos para el archivo solicitado");
					
		            return SUCCESS;
					
				}
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
			
	        
	        filename= descarga.getDsArchivo();
	        if(logger.isDebugEnabled())logger.debug("El nombre del archivo descargado es: "+ filename);
	        
	        //response.setHeader ("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
	        
	        if(StringUtils.isBlank(filename)){
	        	success = false;
	            addActionError("Error al obtener el nombre del archivo a descargar");
	            return SUCCESS;
	        }
	        
	        String fileType=filename.substring(filename.lastIndexOf(".")+1,filename.length());
	        fileType = fileType.trim();
			
			if (Constantes.FORMAT_TXT.equalsIgnoreCase(fileType)) {
					contentType = "text/plain"; //response.setContentType( "text/plain" );
				} else if (Constantes.FORMAT_HTM.equalsIgnoreCase(fileType)) {
					contentType = "text/html";//response.setContentType( "text/html" );
				} else if (Constantes.FORMAT_HTML.equalsIgnoreCase(fileType)) {
					contentType = "text/html";//response.setContentType( "text/html" );
				} else if (Constantes.FORMAT_DOC.equalsIgnoreCase(fileType)) {
					contentType = "application/msword";//response.setContentType( "application/msword" );
				} else if (Constantes.FORMAT_XLS.equalsIgnoreCase(fileType)) {
					contentType = "application/vnd.ms-excel";//response.setContentType( "application/vnd.ms-excel" );
				} else if (Constantes.FORMAT_PDF.equalsIgnoreCase(fileType)) {
					contentType = "application/pdf";//response.setContentType( "application/pdf" );
				} else if (Constantes.FORMAT_PPT.equalsIgnoreCase(fileType)) {
					contentType = "application/ppt";//response.setContentType( "application/ppt" );
				} else if (Constantes.FORMAT_GIF.equalsIgnoreCase(fileType)) {
					contentType = "image/gif";//response.setContentType( "image/gif" );
				} else if (Constantes.FORMAT_BMP.equalsIgnoreCase(fileType)) {
					contentType = "image/bmp";//response.setContentType( "image/bmp" );
				} else if (Constantes.FORMAT_JPG.equalsIgnoreCase(fileType)) {
					contentType = "image/jpeg";//response.setContentType( "image/jpeg" );
				} else if (Constantes.FORMAT_JPEG.equalsIgnoreCase(fileType)) {
					contentType = "image/jpeg";//response.setContentType( "image/jpeg" );
				} else if (Constantes.FORMAT_TIF.equalsIgnoreCase(fileType)) {
					contentType = "image/tiff";//response.setContentType( "image/tiff" );
				} else {
					contentType = "application/octet-stream";//response.setContentType( "application/octet-stream" );
				}
		    
		//byte[] contenidoEnBytes = descarga.getContenidoBytes();
		//response.setContentLength(contenidoEnBytes.length);
			
		inputStream = descarga.getContenidoBytes();//new ByteArrayInputStream(contenidoEnBytes);
	
		/*ServletOutputStream ouputStream;
		
		try {
		    ouputStream = response.getOutputStream();
		    ouputStream.write(contenidoEnBytes);
		    ouputStream.flush();
		    ouputStream.close();
		} catch (IOException e) {
		    logger.error("Error obteniendo el archivo a descargar", e);
		}*/
		
	   success= true;
	   return SUCCESS;
   }

	public String cmdBorrarClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = archivosManager.borrarArchivos(nmcaso, nmovimiento, nmarchivo);
            success = true;
            addActionMessage(messageResult);
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

public String getTipoArchivo() {
	return tipoArchivo;
}

public void setTipoArchivo(String tipoArchivo) {
	this.tipoArchivo = tipoArchivo;
}

public String getDsArchivo() {
	return dsArchivo;
}

public void setDsArchivo(String dsArchivo) {
	this.dsArchivo = dsArchivo;
}

public String getResultadoUpload() {
	return resultadoUpload;
}

public void setResultadoUpload(String resultadoUpload) {
	this.resultadoUpload = resultadoUpload;
}

public void setArchivosManagerJdbcTemplate(
		ArchivosManager archivosManagerJdbcTemplate) {
	this.archivosManagerJdbcTemplate = archivosManagerJdbcTemplate;
}

public void setMediaDAOJdbc(MediaDAO mediaDAOJdbc) {
	this.mediaDAOJdbc = mediaDAOJdbc;
}

public String getDsnomarc() {
	return dsnomarc;
}

public void setDsnomarc(String dsnomarc) {
	this.dsnomarc = dsnomarc;
}

public InputStream getInputStream() {
	return inputStream;
}

public void setInputStream(InputStream inputStream) {
	this.inputStream = inputStream;
}

public String getFilename() {
	return filename;
}

public void setFilename(String filename) {
	this.filename = filename;
}
   



}
