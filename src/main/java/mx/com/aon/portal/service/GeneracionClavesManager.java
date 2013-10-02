package mx.com.aon.portal.service;

import java.util.List;

import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.ClavesVO;
import mx.com.gseguros.exception.ApplicationException;


/**
 * Created by IntelliJ IDEA.
 * User: gabrielforradellas
 * Date: Oct 16, 2008
 * Time: 5:32:50 PM
 * To change this template use File | Settings | File Templates.
 */
public interface  GeneracionClavesManager {

    
    public PagedList obtieneValores(String pv_idegenerador_i, int start, int limit)throws ApplicationException;
	public String actualizarValores(List<ClavesVO> grillaGeneracionClavesList)throws ApplicationException;
    
}	



