package mx.com.aon.catbo.service.impl;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.model.ArchivosFaxesVO;
import mx.com.aon.catbo.model.AsigEncuestaVO;
import mx.com.aon.catbo.model.CalendarioVO;
import mx.com.aon.catbo.model.FaxesVO;
import mx.com.aon.catbo.model.MatrizAsignacionVO;
import mx.com.aon.catbo.model.OperacionCATVO;
import mx.com.aon.catbo.model.ResponsablesVO;
import mx.com.aon.catbo.model.TiemposVO;
import mx.com.aon.catbo.service.OperacionCATManager;
import mx.com.aon.portal.model.AgrupacionPolizaVO;
import mx.com.aon.portal.model.AlertaUsuarioVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;
import mx.com.aon.portal.util.WrapperResultados;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.SqlParameter;

import mx.com.aon.catbo.service.*;
/**
 * Clase que sirve para dar servicios al action que lo invoca.
 * 
 * @implements NotificacionesManager
 * 
 * @extends AbstractManager
 */
public class ConsultarAsigEncuestaManagerImpl extends AbstractManagerJdbcTemplateInvoke implements  ConsultarAsigEncuestaManager {
	
    
    @SuppressWarnings("unchecked")
	public PagedList obtenerAsigEncuesta(String pv_nmpoliex_i, String pv_dsperson_i, String pv_dsusuario_i, int start, int limit) throws ApplicationException {

	HashMap map = new HashMap();
	
	map.put("pv_nmpoliex_i",pv_nmpoliex_i);
	map.put("pv_dsperson_i",pv_dsperson_i);
	map.put("pv_dsusuario_i",pv_dsusuario_i);
		
	
    return pagedBackBoneInvoke(map, "OBTENER_ASIGNACION_ENCUESTA", start, limit);
} 
    
    @SuppressWarnings("unchecked")
	public String borrarAsigEncuesta(String pv_nmconfig_i,String pv_cdunieco_i,String pv_cdramo_i, String pv_estado_i)throws ApplicationException {
    
    HashMap map = new HashMap();
	map.put("pv_nmconfig_i",pv_nmconfig_i);
	map.put("pv_cdunieco_i",pv_cdunieco_i);
	map.put("pv_cdramo_i",pv_cdramo_i);
	map.put("pv_estado_i",pv_estado_i);
	
    WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_ASIGNACION_ENCUESTA");
    return res.getMsgText();
}
    
  
    
    @SuppressWarnings("unchecked")
	public String guardarAsigEncuesta(AsigEncuestaVO asigEncuestaVO) throws ApplicationException {
        // Se crea un mapa para pasar los parametros de ejecucion al endpoint
        HashMap map = new HashMap();
        map.put("pv_nmconfig_i",asigEncuestaVO.getNmConfig());
        map.put("pv_cdunieco_i",asigEncuestaVO.getCdUnieco());
        map.put("pv_cdramo_i",asigEncuestaVO.getCdRamo());
        map.put("pv_estado_i",asigEncuestaVO.getEstado());
        map.put("pv_nmpoliza_i",asigEncuestaVO.getNmPoliza());
        map.put("pv_cdperson_i",asigEncuestaVO.getCdPerson());
        map.put("pv_status_i",asigEncuestaVO.getStatus());
        map.put("pv_cdusuario_i",asigEncuestaVO.getCdUsuario());

        WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_ASIGNACION_ENCUESTA");
        return res.getMsgText();
    }
    
        
    @SuppressWarnings("unchecked")
	public TableModelExport obtieneArchivoExport(String pv_nmpoliex_i, String pv_dsperson_i, String pv_dsusuario_i) throws ApplicationException {
		
		TableModelExport model = new TableModelExport();
		List lista = null;
		HashMap map = new HashMap();
		
		
		map.put("pv_nmpoliex_i",pv_nmpoliex_i);
		map.put("pv_dsperson_i",pv_dsperson_i);
		map.put("pv_dsusuario_i",pv_dsusuario_i);
		
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map,"OBTIENE_ARCHIVO_EXPORT");
        model.setInformation(lista);
		model.setColumnName(new String[]{"Poliza","Cliente","Usuario Responsable"});

		return model;
	}


	public TableModelExport getModel(String cdtipoar, String nmcaso,
			String nmfax, String nmpoliex) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public AsigEncuestaVO getObtenerAsigEncuesta(String pv_nmconfig_i,String pv_nmpoliza_i, String pv_cdperson_i, String pv_cdusuario_i) throws ApplicationException {

		HashMap map = new HashMap();
		map.put("pv_nmconfig_i", pv_nmconfig_i);
		map.put("pv_nmpoliza_i", pv_nmpoliza_i);
		map.put("pv_cdperson_i", pv_cdperson_i);
		map.put("pv_cdusuario_i", pv_cdusuario_i);
		
        return (AsigEncuestaVO)getBackBoneInvoke(map, "OBTENER_ASIGNACION_ENCUESTA_REG");
	}
		
    }

  
    
    
	