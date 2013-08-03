package mx.com.aon.catbo.service.impl;

import mx.com.aon.catbo.service.CalendarioManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.AbstractManager;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;

import org.apache.log4j.Logger;
import mx.com.aon.catbo.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gabrielforradellas
 * Date: Sep 2, 2008
 * Time: 12:19:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class CalendarioManagerImpl extends AbstractManager implements CalendarioManager {

    /**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	protected static Logger logger = Logger.getLogger(CalendarioManagerImpl.class);

    public PagedList buscarCalendario(String cdPais, String nmAnio, String nmMes, int start, int limit) throws ApplicationException {
        HashMap map = new HashMap();
        
        map.put("pv_cdpais_i", cdPais);
        map.put("pv_nmanio_i", nmAnio);
        map.put("pv_nmmes_i", nmMes);

        String endpointName = "OBTENER_CALENDARIO";
        return pagedBackBoneInvoke(map, endpointName, start, limit);
    }
    
 
	public CalendarioVO getDiaMes(String codigoPais, String anio, String mes, String dia) throws ApplicationException {
		   HashMap map = new HashMap();
		   
	       map.put("pv_cdpais_i", codigoPais);
	       map.put("pv_nmanio_i", anio );
	       map.put("pv_nmmes_i", mes );
	       map.put("pv_nmdia_i", dia );
	       
	       String endpointName = "OBTENER_DIA_MES";
	       return (CalendarioVO)getBackBoneInvoke(map, endpointName);
		
	}
	
	public String guardarCalendario(CalendarioVO calendarioVO)
	throws ApplicationException {
		HashMap map = new HashMap();
		
		map.put("cdpais",calendarioVO.getCodigoPais());
	    map.put("nmanio",calendarioVO.getAnio());
		map.put("nmmes",calendarioVO.getMes());
		map.put("nmdia",calendarioVO.getDia());
		map.put("dsdia",calendarioVO.getDescripcionDia());
		
		WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_CALENDARIO");
		return res.getMsgText();
			           
		}

	public String borrarCalendario(String codigoPais, String anio, String mes, String dia) throws ApplicationException {
	    HashMap map = new HashMap();
	    
		map.put("pv_cdpais_i",codigoPais);
		map.put("pv_nmanio_i",anio);
		map.put("pv_nmmes_i",mes);
		map.put("pv_nmdia_i",dia);
		
	    WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_CALENDARIO");
	    return res.getMsgText();
	
	}
	
    /**
	 *  Obtiene un un conjunto de dias y descripcion de un  calendarios para 
	 *  la exportacion a un formato predeterminado
	 *  Hace uso del Store Procedure PKG_CATBO.P_OBTIENE_TCALENDA
	 *  
	 *  @param dsStatus
	 *    
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */
    @SuppressWarnings("unchecked")
	public TableModelExport getModel(String cdPais, String nmAnio, String nmMes) throws ApplicationException {
		
		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();

        map.put("pv_cdpais_i", cdPais);
        map.put("pv_nmanio_i", nmAnio);
        map.put("pv_nmmes_i", nmMes);
		
		
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTENER_CALENDARIO_EXPORT");

		model.setInformation(lista);
		model.setColumnName(new String[]{"País","Año","Mes","Día","Descripción"});
		
		return model;
	}
    @SuppressWarnings("unchecked")
	public String editarCalendario(CalendarioVO calendarioVO)
	throws ApplicationException {
		HashMap map = new HashMap();
		
		map.put("cdpais",calendarioVO.getCodigoPais());
	    map.put("nmanio",calendarioVO.getAnio());
		map.put("nmmes",calendarioVO.getMes());
		map.put("nmdia",calendarioVO.getDia());
		map.put("dsdia",calendarioVO.getDescripcionDia());
		
		WrapperResultados res =  returnBackBoneInvoke(map,"EDITAR_CALENDARIO");
		return res.getMsgText();
			           
		}
    
	
}
	

	

	
	
	
	