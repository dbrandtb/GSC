package mx.com.aon.catbo.service;

import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.model.ClavesVO;
import mx.com.aon.catbo.model.FaxesVO;
import mx.com.aon.portal.service.PagedList;

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



