package mx.com.aon.catbo.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.model.AsignarEncuestaVO;
import mx.com.aon.catbo.model.CatboTimecVO;
import mx.com.aon.catbo.model.ConfigurarEncuestaVO;
import mx.com.aon.portal.service.PagedList;

public interface DatosArchivosManager {
	
	
	public PagedList ObtenerArchivos(String pv_dsarchivo_i, String pv_nmtiparc_i, int start, int limit) throws ApplicationException;
	   
	public TableModelExport getModel(String pv_dsarchivo_i, String pv_nmtiparc_i) throws ApplicationException;
	 
}
