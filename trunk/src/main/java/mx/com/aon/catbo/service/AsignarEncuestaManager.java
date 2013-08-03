package mx.com.aon.catbo.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.model.ConfigurarEncuestaVO;
import mx.com.aon.portal.service.PagedList;

public interface AsignarEncuestaManager {
	
	public PagedList obtenerEncuesta(String pv_dsunieco_i,String pv_dsramo_i,String pv_estado_i,String  pv_nmpoliza_i, String pv_dsperson_i, String pv_dsusuario_i, int start, int limit) throws ApplicationException;
}
