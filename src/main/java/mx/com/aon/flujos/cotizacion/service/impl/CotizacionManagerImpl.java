package mx.com.aon.flujos.cotizacion.service.impl;

import static mx.com.aon.portal.dao.CotizacionDAO.OBTIENE_RESULTADOS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.configurador.pantallas.model.PantallaVO;
import mx.com.aon.configurador.pantallas.model.components.ColumnGridVO;
import mx.com.aon.configurador.pantallas.model.components.ComboClearOnSelectVO;
import mx.com.aon.configurador.pantallas.model.components.GridVO;
import mx.com.aon.configurador.pantallas.model.components.ItemVO;
import mx.com.aon.configurador.pantallas.model.components.RecordVO;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.flujos.cotizacion.model.ObjetoCotizacionVO;
import mx.com.aon.flujos.cotizacion.model.ResultadoCotizacionVO;
import mx.com.aon.flujos.cotizacion.service.CotizacionPrincipalManager;
import mx.com.aon.portal.model.BaseObjectVO;
import mx.com.aon.portal.model.CotizacionMasivaVO;
import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;
import mx.com.aon.portal.util.WrapperResultados;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class CotizacionManagerImpl extends AbstractManagerJdbcTemplateInvoke implements CotizacionPrincipalManager{
	
	protected final transient Logger log = Logger.getLogger(CotizacionManagerImpl.class);
	
	@SuppressWarnings("unchecked")
	public List<CotizacionMasivaVO> obtineTvalositCotiza(String cdunieco,String poliza,String cdramo,String estado )throws ApplicationException {
	
	Map map = new HashMap();
		map.put("pv_cdunieco_i", cdunieco);
		map.put("pv_nmpoliza_i", poliza);
		map.put("pv_cdramo_i", cdramo);
		map.put("pv_estado_i", estado);
	   log.debug("CotizacionManagerImpl.obtineTvalositCotiza params= " + map);
		
		String endpoindName = "P_OBTIENE_TVALOSIT_COTIZA";
		return getAllBackBoneInvoke(map, endpoindName );
	}
	
	public PantallaVO getPantallaFinal(Map<String, String> parameters) throws ApplicationException {

		PantallaVO pantallaVO = new PantallaVO();
		try {
			/*Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_PANTALLA_FINAL");
			pantallaVO = (PantallaVO) endpoint.invoke(parameters);*/
			
			pantallaVO = (PantallaVO) getBackBoneInvoke(parameters, "OBTIENE_PANTALLA_FINAL");
			if(logger.isDebugEnabled()){
				logger.debug("getPantallaFinal()   pantallaVO=" +pantallaVO);
			}
		} catch (Exception bae) {
			logger.error("Exception in invoke getPantallaFinal en cotizacion.. ",bae);
			throw new ApplicationException("Error intentando obtener pantalla final");
		}
		return pantallaVO;
	}
	
	@SuppressWarnings("unchecked")
	public List<ComboClearOnSelectVO> getComboPadre(Map<String, String> parameters) throws ApplicationException{
		List<ComboClearOnSelectVO> lccosvo = new ArrayList<ComboClearOnSelectVO>();
		try {
			/*Endpoint endpoint = (Endpoint) endpoints.get("GET_COMBO_PADRE");
			lccosvo = (List<ComboClearOnSelectVO>) endpoint.invoke(parameters);*/
			WrapperResultados res = returnBackBoneInvoke(parameters, "GET_COMBO_PADRE");
			lccosvo = res.getItemList();
			
		} catch (Exception bae) {
			logger.error("Exception en obtener combos padres... ", bae);
			throw new ApplicationException("Error en obtener combos padres");
		}
		return lccosvo;
		
	}
	
	
	@SuppressWarnings("unchecked")
	public BaseObjectVO getEtiqueta(String cdramo, String cdtipsit)throws ApplicationException {
		Map params= new HashMap<String, String>();
		params.put("PV_CDRAMO_I", cdramo);
		params.put("PV_CDTIPSIT_I", cdtipsit);
		BaseObjectVO etiqueta = new BaseObjectVO();
		etiqueta.setValue("");
		try {
			/*Endpoint endpoint = (Endpoint)endpoints.get("OBTIENE_ETIQUETA_PRODUCTO_ESPECIAL");
			etiqueta= (BaseObjectVO) endpoint.invoke(params);*/
			WrapperResultados result = returnBackBoneInvoke(params, "OBTIENE_ETIQUETA_PRODUCTO_ESPECIAL");
			if(result.getItemMap() != null){
				if(result.getItemMap().containsKey("ETIQUETA")) {
					String etiquetaTmp = (String)result.getItemMap().get("ETIQUETA");
					if(etiquetaTmp!=null) etiqueta.setLabel(etiquetaTmp);
				}
			}
			
		} catch (Exception e) {
			logger.error("ERROR getequipo: "+ e.getMessage(),e);
			throw new ApplicationException("Error retrieving data label");
		}
		return etiqueta;
	}
	
	@SuppressWarnings("unchecked")
	public List<ObjetoCotizacionVO> getListaEquipo(ObjetoCotizacionVO objetoCotizacionVO) throws ApplicationException {
		List<ObjetoCotizacionVO> resultadoObjeto = null;
		Map params= new HashMap<String, String>();
		params.put("PV_CDUNIECO", objetoCotizacionVO.getCdUnieco());
		params.put("PV_CDRAMO", objetoCotizacionVO.getCdRamo());
		params.put("PV_ESTADO", objetoCotizacionVO.getEstado());
		params.put("PV_NMPOLIZA", objetoCotizacionVO.getNmPoliza());
		params.put("PV_NMSITUAC", objetoCotizacionVO.getNmSituac());
		params.put("PV_NMOBJETO", null);
		
		try {
			/*Endpoint endpoint = (Endpoint)endpoints.get("OBTIENE_LISTA_COTIZACION_ESPECIAL");
			resultadoObjeto = (List<ObjetoCotizacionVO>) endpoint.invoke(objetoCotizacionVO);*/
			WrapperResultados res = returnBackBoneInvoke(params, "OBTIENE_LISTA_COTIZACION_ESPECIAL");
			resultadoObjeto = res.getItemList();
			
		} catch (Exception bae) {
			logger.error("ERROR getequipo: "+ bae.getMessage(),bae);
			throw new ApplicationException("Error retrieving data");			
		}
		return (List<ObjetoCotizacionVO>)resultadoObjeto;
	}
	
	@SuppressWarnings("unchecked")
	public List<BaseObjectVO> getTipos(String cdramo, String cdtipsit)throws ApplicationException {
		Map params= new HashMap<String, String>();
		params.put("PV_CDRAMO_I", cdramo);
		params.put("PV_CDTIPSIT_I", cdtipsit);
		List<BaseObjectVO> tipos= null;
		try {
			/*Endpoint endpoint = (Endpoint)endpoints.get("OBTIENE_lISTA_TIPO_PRODUCTO_ESPECIAL");
			tipos= (List<BaseObjectVO>) endpoint.invoke(params);*/
			
			WrapperResultados res = returnBackBoneInvoke(params, "OBTIENE_lISTA_TIPO_PRODUCTO_ESPECIAL");
			tipos = res.getItemList();
			if(logger.isDebugEnabled())logger.debug("Tipos producto especial: "+ tipos);
			
		} catch (Exception e) {
			logger.error("ERROR get tipos: "+ e.getMessage(),e);
			throw new  ApplicationException("Error retrieving data list");
		}
		return (List<BaseObjectVO>)tipos;
	}
	
	@SuppressWarnings("unchecked")
	public GridVO getResultados(Map<String, String> parameters) throws ApplicationException {
		List <ResultadoCotizacionVO> resultList = null;
		GridVO gridVo = null;
		try {
		    if(logger.isDebugEnabled())logger.debug("OBTIENE_RESULTADOS parameters=" + parameters);
		    
			WrapperResultados res = returnBackBoneInvoke(parameters, OBTIENE_RESULTADOS);
		    resultList = res.getItemList();
		    
		    gridVo = adaptarDatosCotizacion(resultList);
		    
		} catch (Exception e) {
			logger.error("Exception in invoke obtener resultados en cotizacion.. ",e);
			throw new ApplicationException("Error intentando obtener resultados en cotizacion");
		}
		return gridVo;
	}
	
	public GridVO adaptarDatosCotizacion(List <ResultadoCotizacionVO> resultList) throws Exception {
		GridVO gridVo = new GridVO();
		if(resultList == null) return gridVo;
		
		List<RecordVO> recordListFinal = null;
		List<ColumnGridVO> columnListFinal = null;
		//List<ResultadoCotizacionVO> resultListFinal = resultList;
		List<ItemVO> itemListPlanFinal = null;
		List<ItemVO> itemListAseguradoraFinal = null;
		List<ItemVO> itemListPagoFinal = null;
		
		LinkedHashMap<String,String> planes = new LinkedHashMap<String, String>();
		HashMap<String,String> asegs = new HashMap<String, String>();
		HashMap<String,String> perpags = new HashMap<String, String>();
		
		for(ResultadoCotizacionVO result : resultList){
			ItemVO item = null;
			
			/*Para llenar la lista de planes*/
			if(result.getCdPlan() !=null && !planes.containsKey(result.getCdPlan())){
				planes.put(result.getCdPlan(), result.getDsPlan());
			}
			/*Para llenar la lista de aseguradoras*/ 
			if(result.getCdCiaaseg() !=null && !asegs.containsKey(result.getCdCiaaseg())){
				asegs.put(result.getCdCiaaseg(), result.getDsUnieco());
				
				if(itemListAseguradoraFinal == null) itemListAseguradoraFinal = new ArrayList<ItemVO>();
				
				item = new ItemVO();
				item.setClave(result.getCdCiaaseg());
				item.setDescripcion(result.getDsUnieco()); 
				
				itemListAseguradoraFinal.add(item);
			}
			
			/*Para llenar la lista de las formas de pago*/ 
			if(result.getCdPerpag() !=null && !perpags.containsKey(result.getCdPerpag())){
				perpags.put(result.getCdPerpag(), result.getDsPerpag());

				if(itemListPagoFinal == null) itemListPagoFinal = new ArrayList<ItemVO>();
				
				item = new ItemVO();
				item.setClave(result.getCdPerpag());
				item.setDescripcion(result.getDsPerpag()); 
				
				itemListPagoFinal.add(item);
			}
		}
		
		log.debug("viendo orden hhh: "+planes);
		
		
		Iterator It = planes.entrySet().iterator();
        while (It.hasNext()) {
        	Map.Entry entry = (Map.Entry) It.next();
        	String cdPlan = (String)entry.getKey();
        	String dsPlan = (String)entry.getValue();
        	String trimDsPlan = StringUtils.deleteWhitespace(dsPlan);
        	String CDtrimDsPlan = "CD"+trimDsPlan;
        	String DStrimDsPlan = "DS"+trimDsPlan;
        	String NMtrimDsPlan = "NM"+trimDsPlan;
        	
        	
        	/*PARA LLENAR EL List <ItemVO> itemListPlan */
        	if(itemListPlanFinal == null) itemListPlanFinal = new ArrayList<ItemVO>();
        	
        	ItemVO item = new ItemVO();
			item.setClave(cdPlan);
			item.setDescripcion(trimDsPlan); //al pasar el xsl a este metodo se encontro que se esta mandando el la descripcion sin espacios 
			
			itemListPlanFinal.add(item);
        	
        	/*PARA FORMAR EL List<ColumnGridVO> columnList */
        	
        	if(columnListFinal == null ) columnListFinal = new ArrayList<ColumnGridVO>();
        	
        	ColumnGridVO column = new ColumnGridVO();
        	column.setHeader(dsPlan);
        	column.setDataIndex(trimDsPlan);
        	column.setWidth(100);
        	column.setSortable(false);
        	column.setId(trimDsPlan);
        	column.setHidden(false);

        	columnListFinal.add(column);
        	
        	column = new ColumnGridVO();
        	column.setHeader(CDtrimDsPlan);
        	column.setDataIndex(CDtrimDsPlan);
        	column.setWidth(100);
        	column.setSortable(false);
        	column.setId(CDtrimDsPlan);
        	column.setHidden(true);
        	
        	columnListFinal.add(column);
        	
        	column = new ColumnGridVO();
        	column.setHeader(DStrimDsPlan);
        	column.setDataIndex(DStrimDsPlan);
        	column.setWidth(100);
        	column.setSortable(false);
        	column.setId(DStrimDsPlan);
        	column.setHidden(true);
        	
        	columnListFinal.add(column);
        	
        	column = new ColumnGridVO();
        	column.setHeader(NMtrimDsPlan);
        	column.setDataIndex(NMtrimDsPlan);
        	column.setWidth(100);
        	column.setSortable(false);
        	column.setId(NMtrimDsPlan);
        	column.setHidden(true);
        	
        	columnListFinal.add(column);
        	
        	
        	/*PARA FORMAR EL List<RecordVO> recordList; */
        	
        	if(recordListFinal == null ) recordListFinal = new ArrayList<RecordVO>();
        	
        	RecordVO record = new RecordVO();
        	record.setName(trimDsPlan);
        	record.setType("string");
        	record.setMapping(trimDsPlan);
        	
        	recordListFinal.add(record);
        	
        	record = new RecordVO();
        	record.setName(CDtrimDsPlan);
        	record.setType("string");
        	record.setMapping(CDtrimDsPlan);
        	
        	recordListFinal.add(record);
        	
        	record = new RecordVO();
        	record.setName(DStrimDsPlan);
        	record.setType("string");
        	record.setMapping(DStrimDsPlan);
        	
        	recordListFinal.add(record);
        	
        	record = new RecordVO();
        	record.setName(NMtrimDsPlan);
        	record.setType("string");
        	record.setMapping(NMtrimDsPlan);
        	
        	recordListFinal.add(record);
        }
		
        gridVo.setRecordList(recordListFinal);
        gridVo.setColumnList(columnListFinal);
		gridVo.setItemListPlan(itemListPlanFinal);
		gridVo.setItemListAseguradora(itemListAseguradoraFinal);
		gridVo.setItemListPago(itemListPagoFinal);
		gridVo.setResultList(resultList);
		
		return gridVo;
	} 
	
}