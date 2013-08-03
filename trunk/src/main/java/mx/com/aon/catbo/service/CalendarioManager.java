package mx.com.aon.catbo.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.model.*;
import mx.com.aon.portal.service.PagedList;

/**
 * Created by IntelliJ IDEA.
 * User: gabrielforradellas
 * Date: Sep 2, 2008
 * Time: 12:12:28 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CalendarioManager {

    public PagedList buscarCalendario(String cdPais, String nmAnio, String nmMes, int start, int limit) throws ApplicationException;

    public CalendarioVO getDiaMes(String codigoPais, String anio, String mes, String dia) throws ApplicationException;

    public String guardarCalendario(CalendarioVO calendarioVO) throws ApplicationException;
    
    public String borrarCalendario(String codigoPais, String anio, String mes, String dia) throws ApplicationException;
    
    public TableModelExport getModel(String cdPais, String nmAnio, String nmMes) throws ApplicationException;
    
    public String editarCalendario(CalendarioVO calendarioVO)throws ApplicationException;
    
}
