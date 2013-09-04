/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.gseguros.portal.general.util;

import java.io.File;
import javax.servlet.http.HttpServletRequest;
import mx.com.aon.core.web.PrincipalCoreAction;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 *
 * @author Jair
 */
public class SubirArchivoAction extends PrincipalCoreAction implements ServletRequestAware{

    private HttpServletRequest servletRequest;
    private File file;
    private String fileFileName;
    private String fileContentType;
    private String targetId;
    private org.apache.log4j.Logger log=org.apache.log4j.Logger.getLogger(SubirArchivoAction.class);
    private String uploadKey;
    
    public String subirArchivo()
    {
        log.debug("servletRequest "+servletRequest);
        log.debug("file "+file);
        log.debug("fileFileName "+fileFileName);
        log.debug("fileContentType "+fileContentType);
        log.debug("targetId "+targetId);
        log.debug("uploadKey "+uploadKey);
        return SUCCESS;
    }
    
    public void setServletRequest(HttpServletRequest hsr) {
        this.servletRequest=hsr;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getUploadKey() {
        return uploadKey;
    }

    public void setUploadKey(String uploadKey) {
        this.uploadKey = uploadKey;
    }
    
}
