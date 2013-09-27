package mx.com.aon.catbo.service.impl;

/*import mx.biosnet.procesobobpelclient.proxy.ProcesoBOBPELPortClient;
import mx.biosnet.procesobobpelclient.proxy.SvcRequest;
import mx.biosnet.procesobobpelclient.proxy.SvcResponse;*/
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mx.com.aon.catbo.service.AdministracionCasosManager2;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;
import mx.com.aon.portal.util.ConvertUtil;
import mx.com.aon.portal.util.UserSQLDateConverter;
import mx.com.aon.portal.util.WrapperResultados;

import org.apache.commons.beanutils.Converter;


/**
 * Created by IntelliJ IDEA.
 * User: gabrielforradellas
 * Date: Sep 2, 2008
 * Time: 12:19:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class AdministracionCasosManagerFechaImpl extends AbstractManagerJdbcTemplateInvoke implements AdministracionCasosManager2 {
    /**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	
	@SuppressWarnings("unchecked")
	public PagedList obtenerCasos(String pv_nmcaso_i, String pv_cdorden_i, String pv_dsproceso_i, String pv_feingreso_i,
			String pv_cdpriord_i, String pv_cdperson_i, String pv_dsperson_i,  String pv_cdusuario_i,int start, int limit)
			throws ApplicationException {
		
	    Converter converter = new UserSQLDateConverter("");
		HashMap map = new HashMap();
        map.put("pv_nmcaso_i", pv_nmcaso_i);
        map.put("pv_cdorden_i", pv_cdorden_i);
        map.put("pv_dsproceso_i",pv_dsproceso_i);
        //map.put("pv_feingreso_i",pv_feingreso_i);
        map.put("pv_feingreso_i", converter.convert(java.util.Date.class, pv_feingreso_i));
        map.put("pv_cdpriord_i",pv_cdpriord_i);
        map.put("pv_cdperson_i", ConvertUtil.nvl(pv_cdperson_i));
        map.put("pv_dsperson_i", pv_dsperson_i);
        map.put("pv_cdusuario_i", pv_cdusuario_i);
        
        String endpointName = "OBTENER_CASOS";
        return pagedBackBoneInvoke(map, endpointName, start, limit);
	}

	@SuppressWarnings("unchecked")
	public TableModelExport getModelObtenerCasos(String pv_nmcaso_i, String pv_cdorden_i, String pv_dsproceso_i, String pv_feingreso_i, String pv_cdpriord_i, String pv_cdperson_i, String pv_dsperson_i, String pv_cdusuario_i)throws ApplicationException {
		TableModelExport model = new TableModelExport();

		Converter converter = new UserSQLDateConverter("");
		 
		List lista = null;
		HashMap map = new HashMap();

        map.put("pv_nmcaso_i", pv_nmcaso_i);
        map.put("pv_cdorden_i", pv_cdorden_i);
        map.put("pv_dsproceso_i",pv_dsproceso_i);
        map.put("pv_feingreso_i", converter.convert(java.util.Date.class, pv_feingreso_i));
        map.put("pv_cdpriord_i",pv_cdpriord_i);
        map.put("pv_cdperson_i", ConvertUtil.nvl(pv_cdperson_i));
        map.put("pv_dsperson_i", pv_dsperson_i);
        map.put("pv_cdusuario_i", pv_cdusuario_i);
        
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_BUSCAR_CASOS_EXPORT");
        model.setInformation(lista);
		model.setColumnName(new String[]{"Numero de Caso","Numero de Orden","Tarea","Fecha de Ingreso","Cliente Solicitante","Prioridad","Vigencia"});

		return model;
	}

	@SuppressWarnings("unchecked")
	public String guardarModulosUsuariosReasignacion(String cdModulo, String nmCaso)
			throws ApplicationException {
		Converter converter = new UserSQLDateConverter("");
		HashMap map = new HashMap();
        map.put("pv_cdmodulo_i", cdModulo);
        map.put("pv_nmcaso_i", nmCaso);       
        
        String endpointName = "GUARDA_CDMODULO";
        
        WrapperResultados res =  returnBackBoneInvoke(map,endpointName);
	    return res.getMsgText();
	}
	@SuppressWarnings("unchecked")
	public TableModelExport obtenerMovimientoCasoExportar(String pv_nmcaso_i)throws ApplicationException {
		TableModelExport model = new TableModelExport();

		Converter converter = new UserSQLDateConverter("");
		 List lista = null;
		
		HashMap map = new HashMap();

        map.put("pv_nmcaso_i", pv_nmcaso_i);
        
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_MOVIMIENTOS_CASOS_EXPORT");
		
        model.setInformation(lista);
		model.setColumnName(new String[]{"Movimiento","Estado","Descripción","Fecha de Ingreso"});
		return model;
	}

	 @SuppressWarnings("unchecked")
		public String validarTiempoMatriz(String pv_cdmatriz_i) throws ApplicationException {
	        HashMap map = new HashMap();
	        map.put("pv_cdmatriz_i", pv_cdmatriz_i);
	        
	        WrapperResultados res =  returnBackBoneInvoke(map,"VALIDA_TEIMPO_MATRIZ");
	        return res.getMsgText();
	    }

	
	
}