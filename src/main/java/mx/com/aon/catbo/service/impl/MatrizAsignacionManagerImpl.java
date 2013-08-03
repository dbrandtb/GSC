package mx.com.aon.catbo.service.impl;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.model.BackBoneResultVO;
import mx.com.aon.catbo.model.CalendarioVO;
import mx.com.aon.catbo.model.CasoVO;
import mx.com.aon.catbo.model.MatrizAsignacionVO;
import mx.com.aon.catbo.model.OperacionCATVO;
import mx.com.aon.catbo.model.ResponsablesVO;
import mx.com.aon.catbo.model.TiemposVO;
import mx.com.aon.catbo.service.OperacionCATManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.AbstractManager;
import mx.com.aon.portal.util.WrapperResultados;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import mx.com.aon.catbo.service.*;
/**
 * Clase que sirve para dar servicios al action que lo invoca.
 * 
 * @implements NotificacionesManager
 * 
 * @extends AbstractManager
 */
public class MatrizAsignacionManagerImpl extends AbstractManager implements MatrizAsignacionManager {


	/**
	 *  Obtiene un conjunto de Notificaciones
	 *  Hace uso del Store Procedure PKG_CATBO.P_OBTIENE_TGUIONES
	 * 
	 *  @param dsUniEco
	 *  @param dsElemento
	 *  @param dsGuion
	 *  @param dsProceso
	 *  @param dsTipGuion
	 *  @param dsRamo
	 *  
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */	
    @SuppressWarnings("unchecked")
  	   	public PagedList buscarMatrices(String pv_dsproceso_i, String pv_dsformatoorden_i,
			String pv_dselemen_i, String pv_dsunieco_i, String pv_dsramo_i, int start, int limit) throws ApplicationException {
   
    	HashMap map = new HashMap();
    	map.put("pv_dsproceso_i",pv_dsproceso_i);
		map.put("pv_dsformatoorden_i", pv_dsformatoorden_i);
		map.put("pv_dselemen_i",pv_dselemen_i);
		map.put("pv_dsunieco_i",pv_dsunieco_i);
		map.put("pv_dsramo_i",pv_dsramo_i);
		
		
		
		map.put("start",start);
		map.put("limit",limit);
        return pagedBackBoneInvoke(map, "OBTENER_MATRIZ", start, limit);
	}
    //ya probado en el test
    @SuppressWarnings("unchecked")
	public String borrarMatriz(String pv_cdmatriz_i)
		throws ApplicationException {
    HashMap map = new HashMap();
	map.put("pv_cdmatriz_i",pv_cdmatriz_i);
	
    WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_MATRIZ");
    return res.getMsgText();
}
    //ya probado en el test
    public MatrizAsignacionVO getMatrizAsignacion(String codigoMatriz) throws ApplicationException {
		   String endpointName = "OBTENER_REGISTRO";
		   HashMap map = new HashMap();
	       map.put("pv_cdmatriz_i", codigoMatriz);
	       	       
	       return (MatrizAsignacionVO)getBackBoneInvoke(map, endpointName);
	   
    
}

    //ya probado en es test    
    @SuppressWarnings("unchecked")
	public PagedList obtieneNivAtencion(String pv_cdmatriz_i, int start, int limit) throws ApplicationException {

	HashMap map = new HashMap();
	map.put("pv_cdmatriz_i",pv_cdmatriz_i);
		
	map.put("start",start);
	map.put("limit",limit);
    return pagedBackBoneInvoke(map, "OBTENER_NIVATENCION", start, limit);
}
    
    //ya probado en el test
    @SuppressWarnings("unchecked")
	public PagedList obtieneResponsables(String pv_cdmatriz_i,String pv_cdnivatn_i, int start, int limit) throws ApplicationException {

	HashMap map = new HashMap();
	map.put("pv_cdmatriz_i",pv_cdmatriz_i);
	map.put("pv_cdnivatn_i",pv_cdnivatn_i);
	
	
	map.put("start",start);
	map.put("limit",limit);
    return pagedBackBoneInvoke(map, "OBTENER_RESPONSABLES", start, limit);
}
    
    //ver el test
    public TiemposVO getTiempoResolucion(String pv_cdmatriz_i, String pv_cdnivatn_i) throws ApplicationException {
		   String endpointName = "OBTENER_TIEMPORESOLUCION";
		   HashMap map = new HashMap();
	       map.put("pv_cdmatriz_i", pv_cdmatriz_i);
	       map.put("pv_cdnivatn_i", pv_cdnivatn_i);     
	       
	       return (TiemposVO)getBackBoneInvoke(map, endpointName);
	   
}  //ya probado       
    @SuppressWarnings("unchecked")
	public BackBoneResultVO guardarMatriz(MatrizAsignacionVO matrizAsignacionVO)
	throws ApplicationException {
    	
    	BackBoneResultVO backBoneResultVO = new BackBoneResultVO();
    	
    	
		HashMap map = new HashMap();
		map.put("pv_cdmatriz_i",matrizAsignacionVO.getcdmatriz());
	    map.put("pv_cdunieco_i",matrizAsignacionVO.getCdunieco());
		map.put("pv_cdramo_i",matrizAsignacionVO.getCdramo());
		map.put("pv_cdelemento_i",matrizAsignacionVO.getCdelemento());
		map.put("pv_cdproceso_i",matrizAsignacionVO.getCdproceso());
		map.put("pv_cdformatoorden_i",matrizAsignacionVO.getCdformatoorden());
		
		WrapperResultados res = returnBackBoneInvoke(map, "GUARDAR_MATRIZ");
		backBoneResultVO.setOutParam(res.getResultado());
		backBoneResultVO.setMsgText(res.getMsgText());
		return backBoneResultVO;
		
        //WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_MATRIZ");
        //return res.getMsgText();
	           
}	
    
    @SuppressWarnings("unchecked")
	public String borrarResponsablesMatriz(String pv_cdmatriz_i, String pv_cdnivatn_i, String pv_cdusuario_i)
		throws ApplicationException {
    HashMap map = new HashMap();
	map.put("pv_cdmatriz_i",pv_cdmatriz_i);
	map.put("pv_cdnivatn_i",pv_cdnivatn_i);
	map.put("pv_cdusuario_i",pv_cdusuario_i);
	
    WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_RESPONSABLE");
    return res.getMsgText();
}
    
    @SuppressWarnings("unchecked")
	public String borrarTiemposMatriz(String pv_cdmatriz_i, String pv_cdnivatn_i)
		throws ApplicationException {
    HashMap map = new HashMap();
	map.put("pv_cdmatriz_i",pv_cdmatriz_i);
	map.put("pv_cdnivatn_i",pv_cdnivatn_i);
		
    WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_TIEMPOS");
    return res.getMsgText();
}
    
    @SuppressWarnings("unchecked")
	public String guardarResponsables(ResponsablesVO responsablesVO)
	throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdmatriz",responsablesVO.getCdmatriz());
	    map.put("cdnivatn",responsablesVO.getCdnivatn());
		map.put("cdrolmat",responsablesVO.getCdrolmat());
		map.put("cdusuari",responsablesVO.getCdusuari());
		map.put("pv_email_i",responsablesVO.getEmail());
		map.put("status",responsablesVO.getStatus());
		map.put("operacion",responsablesVO.getOperacion());
		
WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_RESPONSABLES");
return res.getMsgText();
	           
}
    
    @SuppressWarnings("unchecked")
	public String guardarTiempos(TiemposVO tiemposVO)
	throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdmatriz",tiemposVO.getCdmatriz());
		map.put("pv_cdnivatn_i",tiemposVO.getCdnivatn());
		map.put("pv_tresolucion_i",tiemposVO.getTresolucion());
		map.put("pv_tresunidad_i",tiemposVO.getTresunidad());
		map.put("pv_talarma_i",tiemposVO.getTalarma());
		map.put("pv_talaunidad_i",tiemposVO.getTalaunidad());
		map.put("pv_tescalamiento_i",tiemposVO.getTescalamiento());
		map.put("pv_tescaunidad_i",tiemposVO.getTescaunidad());
		map.put("pv_operacion_i",tiemposVO.getOperacion());
		
		
		
WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_TIEMPOS");
return res.getMsgText();
	           
}
    
    @SuppressWarnings("unchecked")
	public String guardarSuplente(CasoVO casoVO)	throws ApplicationException {
		HashMap map = new HashMap();
		
		map.put("pv_cdmatriz_i", casoVO.getCdMatriz());
		map.put("pv_cdusr_old_i", casoVO.getCdUsuarioOld());
		map.put("pv_cdusr_new_i", casoVO.getCdUsuarioNew());
	    map.put("pv_cdusuario_i", casoVO.getCdUsuario());
		map.put("pv_cdnivatn_i", casoVO.getCdNivatn());
				
WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_SUPLENTES");
return res.getMsgText();
	           
}
    
    @SuppressWarnings("unchecked")
	public ResponsablesVO getObtieneResponsables(String pv_cdmatriz_i, String pv_cdnivatn_i, String pv_cdusuario_i) throws ApplicationException {
		   String endpointName = "GET_OBTENER_RESPONSABLES";
		   HashMap map = new HashMap();
	       map.put("pv_cdmatriz_i", pv_cdmatriz_i);
	       map.put("pv_cdnivatn_i", pv_cdnivatn_i);     
	       map.put("pv_cdusuario_i", pv_cdusuario_i); 
	       
	       return (ResponsablesVO)getBackBoneInvoke(map, endpointName);
	   
}
	@SuppressWarnings("unchecked")
	public TableModelExport getModelMatrizAsignacion(String pv_dsproceso_i,
			String pv_dsformatoorden_i, String pv_dselemen_i,
			String pv_dsunieco_i, String pv_dsramo_i)
			throws ApplicationException {
	    TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();

		map.put("pv_dsproceso_i",pv_dsproceso_i);
		map.put("pv_dsformatoorden_i", pv_dsformatoorden_i);
		map.put("pv_dselemen_i",pv_dselemen_i);
		map.put("pv_dsunieco_i",pv_dsunieco_i);
		map.put("pv_dsramo_i",pv_dsramo_i);
		
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_MATRICES_ASIGNACION_EXPORT");

		model.setInformation(lista);
		model.setColumnName(new String[]{"Proceso","Formato de Orden","Cliente","Aseguradora","Producto"});
		
		return model;
	}        
	
	
	@SuppressWarnings("unchecked")
	public String validaResponsable(String pv_cdmatriz_i,String pv_cdnivatn_i, String pv_cdusuari_i ) throws ApplicationException {
		
		HashMap map = new HashMap();
		map.put("pv_cdmatriz_i",pv_cdmatriz_i);
		map.put("pv_cdnivatn_i",pv_cdnivatn_i);
		map.put("pv_cdusuari_i",pv_cdusuari_i);		
		
	    WrapperResultados res =  returnBackBoneInvoke(map,"VALIDA_RESPONSABLE");
	    return res.getResultado();
		}    
    	
    }
  
    
    
	