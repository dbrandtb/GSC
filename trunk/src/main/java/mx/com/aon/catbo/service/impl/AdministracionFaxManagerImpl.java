package mx.com.aon.catbo.service.impl;

/*import mx.biosnet.procesobobpelclient.proxy.ProcesoBOBPELPortClient;
import mx.biosnet.procesobobpelclient.proxy.SvcRequest;
import mx.biosnet.procesobobpelclient.proxy.SvcResponse;*/
import mx.com.aon.catbo.model.BackBoneResultVO;
import mx.com.aon.catbo.model.CalendarioVO;
import mx.com.aon.catbo.model.CasoDetalleVO;
import mx.com.aon.catbo.model.CasoVO;
import mx.com.aon.catbo.model.ExtJSAtributosFaxVO;
import mx.com.aon.catbo.model.ExtJSAtributosVO;
import mx.com.aon.catbo.model.FaxesVO;
import mx.com.aon.catbo.model.PolizaFaxVO;
import mx.com.aon.catbo.model.FormatoOrdenFaxVO;
import mx.com.aon.catbo.model.FormatoOrdenVO;
import mx.com.aon.catbo.model.ItemVO;
import mx.com.aon.catbo.model.OperacionCATVO;
import mx.com.aon.catbo.model.ReasignacionCasoVO;
import mx.com.aon.catbo.model.ResultadoGeneraCasoVO;
import mx.com.aon.catbo.model.StatusCasoVO;
import mx.com.aon.catbo.service.AdministracionCasosManager;
import mx.com.aon.catbo.service.AdministracionFaxManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.AbstractManager;
import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;
import mx.com.aon.portal.util.ConvertUtil;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.core.ApplicationException;

import mx.com.aon.export.model.TableModelExport;
import mx.biosnet.procesobobpelclient.proxy.ProcesoBOBPELPortClient;
import mx.biosnet.procesobobpelclient.proxy.SvcRequest;
import mx.biosnet.procesobobpelclient.proxy.SvcResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.math.BigInteger;


/**
 * Created by IntelliJ IDEA.
 * User: gabrielforradellas
 * Date: Sep 2, 2008
 * Time: 12:19:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class AdministracionFaxManagerImpl extends AbstractManagerJdbcTemplateInvoke implements AdministracionFaxManager {

	

    /**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unchecked")
	public PagedList obtenerAdministracionFax(String pv_dsarchivo_i,String pv_nmcaso_i,String pv_nmfax_i,String pv_nmpoliex_i,int start, int limit) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pv_dsarchivo_i", pv_dsarchivo_i);
        map.put("pv_nmcaso_i", pv_nmcaso_i);
        map.put("pv_nmfax_i", pv_nmfax_i);
        map.put("pv_nmpoliex_i", pv_nmpoliex_i);
        

        String endpointName = "BUSCAR_ADMINISTRACION_FAXES";
        return pagedBackBoneInvoke(map, endpointName, start, limit);
    }

	@SuppressWarnings("unchecked")
	public PagedList obtenerAdministracionValorFax(String pv_nmcaso_i,String pv_nmfax_i,int start, int limit) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pv_nmcaso_i", pv_nmcaso_i);
        map.put("pv_nmfax_i", pv_nmfax_i);
       

        String endpointName = "BUSCAR_ADMINISTRACION_VALOR_FAXES";
        return pagedBackBoneInvoke(map, endpointName, start, limit);
    }
	
	
	@SuppressWarnings("unchecked")
	public WrapperResultados guardarAdministracionFax(String pv_nmcaso_i, String pv_nmfax_i,String  pv_cdtipoar_i,String  pv_feingreso_i, String  pv_ferecepcion_i,String pv_nmpoliex_i , String pv_cdusuari_i ,String pv_blarchivo_i )throws ApplicationException{
		
		WrapperResultados wrapperResultados = new WrapperResultados();
		
		HashMap map = new HashMap();
		map.put("pv_nmcaso_i",pv_nmcaso_i);
		map.put("pv_nmfax_i",ConvertUtil.nvl(pv_nmfax_i));
	    map.put("pv_cdtipoar_i",pv_cdtipoar_i);
		map.put("pv_feingreso_i",ConvertUtil.convertToDate(pv_feingreso_i));
		map.put("pv_ferecepcion_i",ConvertUtil.convertToDate(pv_ferecepcion_i));
		map.put("pv_nmpoliex_i",pv_nmpoliex_i);
		map.put("pv_cdusuari_i",pv_cdusuari_i);
		map.put("pv_blarchivo_i", pv_blarchivo_i);
		
		WrapperResultados res = returnBackBoneInvoke(map, "GUARDA_ADMINISTRACION_FAXES");
		wrapperResultados.setMsgText(res.getMsgText());
		//backBoneResultVO.setOutParam(res.getResultado());
		//backBoneResultVO.setMsgText(res.getMsgText());
		return res;
	
   }
	
	@SuppressWarnings("unchecked")
	public String guardarAdministracionValorFax(String pv_nmcaso_i, String pv_nmfax_i,String  pv_cdtipoar_i,String  pv_cdatribu_i, String  pv_otvalor_i)throws ApplicationException {
		    
			HashMap map = new HashMap();
		    map.put("pv_nmcaso_i", pv_nmcaso_i);
		    map.put("pv_nmfax_i", ConvertUtil.nvl(pv_nmfax_i));
            map.put("pv_cdtipoar_i", pv_cdtipoar_i);
            map.put("pv_cdatribu_i", ConvertUtil.nvl(pv_cdatribu_i));
            map.put("pv_otvalor_i", pv_otvalor_i);
           
            String endpointName = "GUARDA_ADMINISTRACION_VALOR_FAXES";
	        WrapperResultados res =  returnBackBoneInvoke(map,endpointName);
	        return res.getMsgText();
	}

    public String borrarAdministracionFax(String pv_nmfax_i, String pv_nmcaso_i) throws ApplicationException {
    HashMap map = new HashMap();
	map.put("pv_nmfax_i",pv_nmfax_i);
	map.put("pv_nmcaso_i", pv_nmcaso_i);
	
    WrapperResultados res =  returnBackBoneInvoke(map,"BORRA_ADMINISTRACION_FAXES");
    return res.getMsgText();
	
}
    @SuppressWarnings("unchecked")
	public PagedList obtenerTatriarcFax(String pv_cdtipoar_i,int start, int limit) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pv_cdtipoar_i", pv_cdtipoar_i);
        

        String endpointName = "OBTIENE_TATRIARC_FAXES";
        return pagedBackBoneInvoke(map, endpointName, start, limit);
    }

    @SuppressWarnings("unchecked")
	public PolizaFaxVO obtenerPolizaFax(String pv_nmcaso_i) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pv_nmcaso_i", pv_nmcaso_i);
        
        return (PolizaFaxVO)getBackBoneInvoke(map,"OBTIENE_POLIZA_FAXES");
  
    }
    
    @SuppressWarnings("unchecked")
	public PagedList obtenerValoresFax(String pv_cdtipoar_i,String pv_nmcaso_i,String pv_nmfax_i,int start, int limit) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pv_cdtipoar_i", pv_cdtipoar_i);
        map.put("pv_nmcaso_i", pv_nmcaso_i);
        map.put("pv_nmfax_i", pv_nmfax_i);
        
        String endpointName = "OBTIENE_VALORES_FAXES";
        return pagedBackBoneInvoke(map, endpointName, start, limit);
        

    }
    
    @SuppressWarnings("unchecked")
	public String validaNmcasoFaxes(String pv_nmcaso_i) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pv_nmcaso_i", pv_nmcaso_i);
        
        WrapperResultados res =  returnBackBoneInvoke(map,"VALIDA_NMCASO_FAXES_CIMA");
        return res.getMsgText();
    }
    
	public WrapperResultados guardarNuevoCasoFax(FaxesVO faxesVO, FormatoOrdenFaxVO formatoOrdenFaxVO)throws Exception {


		try {
			
			WrapperResultados res = this.guardarAdministracionFax(faxesVO.getNmcaso(), faxesVO.getNmfax(), faxesVO.getCdtipoar(), faxesVO.getFeingreso(),faxesVO.getFerecepcion(), faxesVO.getNmpoliex(), faxesVO.getCdusuari(),faxesVO.getBlarchivo());
			
			
			if (res.getResultado()!= null && res.getResultado()!= ""){
				
				//WrapperResultados wres = new WrapperResultados();
				for(int i=0; i<formatoOrdenFaxVO.getListaFaxesVO().size();i++){
					
					HashMap map = new HashMap();
					map.put("pv_nmcaso_i",formatoOrdenFaxVO.getListaFaxesVO().get(i).getNmcaso());
					map.put("pv_nmfax_i",res.getResultado());//formatoOrdenFaxVO.getListaFaxesVO().get(i).getNmfax());
				    map.put("pv_cdtipoar_i",formatoOrdenFaxVO.getListaFaxesVO().get(i).getCdtipoar());
					map.put("pv_cdatribu_i",formatoOrdenFaxVO.getListaFaxesVO().get(i).getCdAtribu());
					map.put("pv_otvalor_i",formatoOrdenFaxVO.getListaFaxesVO().get(i).getOtvalor());
					
					
			    WrapperResultados resul =  returnBackBoneInvoke(map,"GUARDA_ADMINISTRACION_VALOR_FAXES");
			    return resul;
											
				}
			}
		return res;
				
			
		}
	 catch (Exception e) {
		logger.error("No se puedo ejecutar el guardar fax", e);
		throw new ApplicationException("No se puedo ejecutar el fax  " ,e);
	}
		
    
	}
	
	/**************Exportar****************/
	/*@SuppressWarnings("unchecked")
	public HashMap obtenerDetalleFaxParaExportar(String dsarchivo, String nmcaso, String nmfax, String nmpoliex)
			throws ApplicationException {
		HashMap map = new HashMap ();
		map.put("pv_dsarchivo_i", dsarchivo);
		map.put("pv_nmcaso_i", nmcaso);
		map.put("pv_nmfax_i", nmfax);
		map.put("pv_nmpoliex_i", nmpoliex);

		//Carga de datos del Caso
		FaxesVO faxesVO = (FaxesVO) getBackBoneInvoke(map, "BUSCAR_ADMINISTRACION_FAXES");//"OBTENER_FAXES");
		
		
		HashMap datosAExportar = new HashMap();
		
		datosAExportar.put("01NRO_CASO", faxesVO.getNmcaso());
		datosAExportar.put("02TIPOARCH", faxesVO.getCdtipoar());
		datosAExportar.put("03FECHAREGISTRO", faxesVO.getFeingreso());
		datosAExportar.put("04FECHARECEPCION", faxesVO.getFerecepcion());
		datosAExportar.put("05NUMPOLIZA", faxesVO.getNmpoliex());
		datosAExportar.put("06CODDESCUSUARIO", faxesVO.getCdusuario());
		datosAExportar.put("07DESCARCHIVO", faxesVO.getDsarchivo());
		
		return datosAExportar;
	}*/
	
	public HashMap<String, ItemVO> obtenerAtributosVariablesFaxParaExportar(String cdtipoar, String nmcaso, String nmfax)throws ApplicationException {
		HashMap map = new HashMap();
		HashMap<String, ItemVO> mapaAtributos = new HashMap<String, ItemVO>();
		//Carga de atributos variables del caso
		map.put("pv_cdtipoar_i", cdtipoar);
		map.put("pv_nmcaso_i", nmcaso);
		map.put("pv_nmfax_i", nmfax);
		
		PagedList pagedList = obtenerValoresFax(cdtipoar,nmcaso,nmfax,0,9999);//pagedBackBoneInvoke(map, "OBTIENE_VALORES_FAXES", 0, 9999);
		List<ExtJSAtributosFaxVO> listaAtributos = pagedList.getItemsRangeList();
		int i = 0;
		while (i < listaAtributos.size()) {
			ExtJSAtributosFaxVO atributosVO = (ExtJSAtributosFaxVO)listaAtributos.get(i);
			String key = "Atributo_" + atributosVO.getSwFormat() + "_" + atributosVO.getCdAtribu();
			ItemVO itemVO = new ItemVO();
			//itemVO.setModulo(atributosVO.getDsSeccion());
			itemVO.setId(atributosVO.getDsAtribu());
			itemVO.setTexto(((atributosVO.getOtTabVal()== "")||(atributosVO.getOtTabVal()== null))? atributosVO.getOtvalor():atributosVO.getDsvalor());
			if (itemVO.getTexto() == null){itemVO.setTexto("");};
			mapaAtributos.put(key, itemVO);
			i++;
		}
		return mapaAtributos;
	}
    
}