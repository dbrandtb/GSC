package mx.com.aon.catbo.service.impl;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.model.ArchivosFaxesVO;
import mx.com.aon.catbo.model.CalendarioVO;
import mx.com.aon.catbo.model.FaxesVO;
import mx.com.aon.catbo.model.MatrizAsignacionVO;
import mx.com.aon.catbo.model.OperacionCATVO;
import mx.com.aon.catbo.model.ResponsablesVO;
import mx.com.aon.catbo.model.TiemposVO;
import mx.com.aon.catbo.service.OperacionCATManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;
import mx.com.aon.portal.util.ConvertUtil;
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
public class ArchivosFaxesImpl extends AbstractManagerJdbcTemplateInvoke implements  ArchivosFaxesManager {
	
    
    @SuppressWarnings("unchecked")
	public PagedList obtenerArchivos(String pv_dsarchivo_i, int start, int limit) throws ApplicationException {

	HashMap map = new HashMap();
	map.put("pv_dsarchivo_i",pv_dsarchivo_i);
		
	map.put("start",start);
	map.put("limit",limit);
    return pagedBackBoneInvoke(map, "OBTENER_ARCHIVOS", start, limit);
}
    
    
    @SuppressWarnings("unchecked")
	public String borrarArchivos(String pv_cdtipoar_i,String pv_cdatribu_i)throws ApplicationException {
    
    HashMap map = new HashMap();
	map.put("pv_cdtipoar_i",pv_cdtipoar_i);
	map.put("pv_cdatribu_i",pv_cdatribu_i);
	
    WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_ARCHIVOS");
    return res.getMsgText();
}
    
    public ArchivosFaxesVO getObtenerArchivo(String pv_cdtipoar_i, String pv_cdatribu_i) throws ApplicationException {
		   String endpointName = "GET_OBTENER_ARCHIVO";
		   HashMap map = new HashMap();
	       map.put("pv_cdtipoar_i", pv_cdtipoar_i);
	       map.put("pv_cdatribu_i", pv_cdatribu_i);     
	       
	       return (ArchivosFaxesVO)getBackBoneInvoke(map, endpointName);
	   
}        
        
    public String guardarArchivos(ArchivosFaxesVO archivosFaxesVO)throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdtipoar",archivosFaxesVO.getCdtipoar());
	    map.put("cdatribu",archivosFaxesVO.getCdatribu());
		map.put("dsatribu",archivosFaxesVO.getDsatribu());
		map.put("swformat",archivosFaxesVO.getSwformat());
		map.put("nmlmax",archivosFaxesVO.getNmlmax());
		map.put("nmlmin",archivosFaxesVO.getNmlmin());
		map.put("swobliga",archivosFaxesVO.getSwobliga());
		map.put("ottabval",archivosFaxesVO.getOttabval());
		map.put("status",archivosFaxesVO.getStatus());
		
WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_ARCHIVOS");
return res.getMsgText();
	           
}	    
   
    
    public FaxesVO getObtenerFax(String pv_nmfax_i) throws ApplicationException {
		   String endpointName = "GET_OBTENER_FAX";
		   HashMap map = new HashMap();
		   
	       map.put("pv_nmfax_i", pv_nmfax_i);	          
	       
	       return (FaxesVO)getBackBoneInvoke(map, endpointName);
	   
}     
     
  
      
    
    public String guardarFax(FaxesVO faxesVO)throws ApplicationException {
		HashMap map = new HashMap();
		
		map.put("nmcaso",faxesVO.getNmcaso());
		map.put("nmfax",faxesVO.getNmfax());
		map.put("cdtipoar",faxesVO.getCdtipoar());
	    map.put("feingreso",faxesVO.getFeingreso());
		map.put("ferecepcion",faxesVO.getFerecepcion());
		map.put("nmpoliex",faxesVO.getNmpoliex());
		map.put("cdusuari",faxesVO.getCdusuari());
		map.put("blarchivo",faxesVO.getBlarchivo());
		
		
WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_FAX");
return res.getMsgText();
	           
}	
    
    public String guardarValoresFax (FaxesVO faxesVO)throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_nmcaso_i",faxesVO.getNmcaso());
	    map.put("pv_nmfax_i",faxesVO.getNmfax());
		map.put("pv_cdtipoar_i",faxesVO.getCdtipoar());
		map.put("pv_cdatribu_i",faxesVO.getCdatribu());
		map.put("pv_otvalor_i",faxesVO.getOtvalor());
				
		
WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_VALORES_FAX");
return res.getMsgText();
	           
}	
    
    @SuppressWarnings("unchecked")
	public PagedList obtenerArchivosInterfaz(String pv_dsarchivo_i,String pv_nmtiparc_i, int start, int limit) throws ApplicationException {

	HashMap map = new HashMap();
	map.put("pv_dsarchivo_i",pv_dsarchivo_i);
	map.put("pv_nmtiparc_i",pv_nmtiparc_i);
	
		
	map.put("start",start);
	map.put("limit",limit);
    return pagedBackBoneInvoke(map, "OBTENER_ARCHIVOS_INTERFAZ", start, limit);
}
    
    
    public String validaNumeroCaso(String pv_nmcaso_i) throws ApplicationException {
		
		HashMap map = new HashMap();
		map.put("pv_nmcaso_i",pv_nmcaso_i);	
		
	    WrapperResultados res =  returnBackBoneInvoke(map,"VALIDAR_NUMERO_CASO");
	    return res.getMsgText();
		
		}
    
    @SuppressWarnings("unchecked")
	public PagedList obtenerFaxes (String pv_dsarchivo_i, String pv_nmcaso_i,String pv_nmfax_i, String pv_nmpoliex_i, int start, int limit) throws ApplicationException {

	HashMap map = new HashMap();
	map.put("pv_dsarchivo_i",pv_dsarchivo_i);
	map.put("pv_nmcaso_i",pv_nmcaso_i);
	map.put("pv_nmfax_i",pv_nmfax_i);
	map.put("pv_nmpoliex_i",pv_nmpoliex_i);
		
	map.put("start",start);
	map.put("limit",limit);
    return pagedBackBoneInvoke(map,"OBTENER_FAXES", start, limit);
}
    
    @SuppressWarnings("unchecked")
	public String BorrarFax(String pv_nmfax_i)throws ApplicationException {
    HashMap map = new HashMap();
	
    map.put("pv_nmfax_i",pv_nmfax_i);
		
    WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_FAX");
    return res.getMsgText();
}
    
    
    
    @SuppressWarnings("unchecked")
	public String BorrarValoresFax(String pv_nmfax_i)throws ApplicationException {
    HashMap map = new HashMap();
	
    map.put("pv_nmfax_i",pv_nmfax_i);
		
    WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_VALORES_FAX");
    return res.getMsgText();
}
    
    @SuppressWarnings("unchecked")
	public PagedList ObtenerValoresFaxes(String pv_nmcaso_i,String pv_nmfax_i,int start, int limit) throws ApplicationException {

	HashMap map = new HashMap();
	map.put("pv_nmcaso_i",pv_nmcaso_i);
	map.put("pv_nmfax_i",pv_nmfax_i);		
	map.put("start",start);
	map.put("limit",limit);
	
    return pagedBackBoneInvoke(map, "OBTENER_VALORES_FAX", start, limit);
}


	@SuppressWarnings("unchecked")
	public TableModelExport getModel(String cdtipoar, String nmcaso,String nmfax, String nmpoliex) throws ApplicationException {
		
		TableModelExport model = new TableModelExport();
		List lista = null;
		HashMap map = new HashMap();
		
		map.put("pv_dsarchivo_i",cdtipoar);
		map.put("pv_nmcaso_i",nmcaso);
		map.put("pv_nmfax_i",ConvertUtil.nvl(nmfax));
		map.put("pv_nmpoliex_i",nmpoliex);
		
		
		/*map.put("cdtipoar", cdtipoar);
		map.put("nmcaso", nmcaso);
		map.put("nmfax", nmfax);
		map.put("nmpoliex", nmpoliex);*/
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map,"OBTIENE_ARCHIVO_EXPORT");
        model.setInformation(lista);
		model.setColumnName(new String[]{"Tipo Archivo","Numero Fax","Poliza","Numero Caso","Usuario Registro","Fecha Ingreso"});

		return model;
	}   
	
	@SuppressWarnings("unchecked")
	public HashMap obtenerDetalleFaxParaExportar(String dsarchivo, String nmcaso, String nmfax, String nmpoliex, String archivoNombre)
			throws ApplicationException {
		HashMap map = new HashMap ();
		map.put("pv_dsarchivo_i", dsarchivo);
		map.put("pv_nmcaso_i", nmcaso);
		map.put("pv_nmfax_i", nmfax);
		map.put("pv_nmpoliex_i", nmpoliex);

		//Carga de datos del Caso
		FaxesVO faxesVO = (FaxesVO) getBackBoneInvoke(map, "OBTENER_FAXES");
		
		
		HashMap datosAExportar = new HashMap();
		
		datosAExportar.put("01NRO_CASO", faxesVO.getNmcaso());
		datosAExportar.put("02TIPOARCH", faxesVO.getDsarchivo());//faxesVO.getCdtipoar());
		//datosAExportar.put("03FECHAREGISTRO", faxesVO.getFeingreso());
		datosAExportar.put("03NUMFAX", faxesVO.getNmfax());
		datosAExportar.put("04FECHARECEPCION", faxesVO.getFerecepcion());
		datosAExportar.put("05NUMPOLIZA", (faxesVO.getNmpoliex()!= null)?faxesVO.getNmpoliex():"");
		datosAExportar.put("06CODDESCUSUARIO", faxesVO.getCdusuario());
		datosAExportar.put("07DESCARCHIVO", archivoNombre);
		
		return datosAExportar;
	}
	
	@SuppressWarnings("unchecked")
		public String BorrarDetalleFax(String pv_nmfax_i, String pv_nmcaso_i)throws ApplicationException {
	    HashMap map = new HashMap();
		
	    map.put("pv_nmfax_i",pv_nmfax_i);
	    map.put("pv_nmcaso_i",pv_nmcaso_i);
			
	    WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_DETALLE_FAX");
	    return res.getMsgText();
	}
	
    }

  
    
    
	