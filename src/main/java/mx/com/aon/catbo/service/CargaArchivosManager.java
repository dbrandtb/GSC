package mx.com.aon.catbo.service;

import java.io.InputStream;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.model.MediaTO;
import mx.com.aon.portal.service.PagedList;
import java.io.File;



/**
 * Created by IntelliJ IDEA.
 * User: gabrielforradellas
 * Date: Oct 16, 2008
 * Time: 5:32:50 PM
 * To change this template use File | Settings | File Templates.
 */
public interface  CargaArchivosManager {

	public PagedList obtenerArchivos(String directorio, String fechaDesde, String fechaHasta,int start, int limit) throws ApplicationException;
    public String guardaArchivos(final InputStream inputStream, String directorio, String nombre) throws ApplicationException, Exception;

    
}	



