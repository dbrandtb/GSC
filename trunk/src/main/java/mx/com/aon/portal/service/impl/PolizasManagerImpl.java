package mx.com.aon.portal.service.impl;

import mx.com.aon.portal.service.PolizasManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.UserSQLDateConverter;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.portal.util.ConvertUtil;
import mx.com.aon.portal.model.ConsultaPolizasCanceladasVO;
import mx.com.aon.portal.model.RehabilitacionManual_PolizaVO;
import mx.com.aon.procesos.emision.model.PolizaMaestraVO;
import mx.com.aon.export.model.TableModelExport;

import mx.com.aon.portal.util.UserSQLDateConverter;
import mx.com.gseguros.exception.ApplicationException;
import oracle.jdbc.driver.OracleTypes;

import org.apache.commons.beanutils.Converter;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.SqlParameter;
import org.apache.commons.beanutils.Converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PolizasManagerImpl extends AbstractManagerJdbcTemplateInvoke implements PolizasManager {

    @SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(PolizasManagerImpl.class);



    @SuppressWarnings("unchecked")
	public PagedList buscarPolizasCanceladas(String pv_asegurado_i, String pv_dsuniage_i, String pv_dsramo_i, String pv_nmpoliza_i, String pv_nmsituac_i, String pv_dsrazon_i,
    		String pv_fecancel_ini_i, String pv_fecancel_fin_i, int start, int limit) throws ApplicationException {
        HashMap map = new HashMap();

        map.put("pv_asegurado_i", pv_asegurado_i);
        map.put("pv_dsuniage_i", pv_dsuniage_i);
        map.put("pv_dsramo_i", pv_dsramo_i);
        map.put("pv_nmpoliza_i", pv_nmpoliza_i);
        map.put("pv_nmsituac_i", pv_nmsituac_i);
        map.put("pv_cdrazon_i", pv_dsrazon_i);
        map.put("pv_fecancel_ini_i", ConvertUtil.convertToDate(pv_fecancel_ini_i));
        map.put("pv_fecancel_fin_i", ConvertUtil.convertToDate(pv_fecancel_fin_i));

        String endpointName = "BUSCAR_POLIZAS_CANCELADAS";
        return pagedBackBoneInvoke(map, endpointName, start, limit);
    }

	public PagedList buscarPolizasCanceladas(int start, int limit)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public PagedList buscarPolizasACancelar(String pv_asegurado_i,
			String pv_dsuniage_i, String pv_dsramo_i, String pv_nmpoliza_i,
			String pv_nmsituac_i, int start, int limit)
			throws ApplicationException {
		HashMap map = new HashMap();
        //setear los parametros de entrada
        map.put("pv_asegurado_i", pv_asegurado_i);
        map.put("pv_dsuniage_i", pv_dsuniage_i);
        map.put("pv_dsramo_i", pv_dsramo_i);
        map.put("pv_nmpoliza_i", pv_nmpoliza_i);
        map.put("pv_nmsituac_i", pv_nmsituac_i);

        String endpointName = "BUSCAR_POLIZAS_CANCELAR";
        return pagedBackBoneInvoke(map, endpointName, start, limit);
	}


    public String modificarCancelacionPoliza(List<ConsultaPolizasCanceladasVO> polizasCanceladas) throws ApplicationException {
        String resultado = "";
        for (int i = 0; i < polizasCanceladas.size(); i++) {
        	if (polizasCanceladas.get(i)!=null)
        	{
            ConsultaPolizasCanceladasVO vo =  polizasCanceladas.get(i);
            resultado =  modificarCancelacionPoliza(vo.getCdUnieco(),
            		vo.getCdRamo(),
                    vo.getEstado(),
                    vo.getNmPoliza(),
                    vo.getNmsuplem(),
                    vo.getCdagrupa(),
                    vo.getNmrecibo(),
                    vo.getCdcancel(),
                    vo.getStatus(),
                    vo.getFeCancel(),
                    vo.getSwcancela());
        	}

        }
        return resultado;
    }

    @SuppressWarnings("unchecked")
	private String modificarCancelacionPoliza(String pv_cdunieco_i,
			String pv_cdramo_i, String pv_estado_i, String pv_nmpoliza_i,
			String pv_nmsuplem_i, String pv_cdagrupa_i, String pv_nmrecibo_i,
			String pv_cdcancel_i, String pv_status_i, String pv_fecancel_i,String pv_swcancela_i)
			throws ApplicationException {
		    HashMap map = new HashMap();
		    map.put("pv_cdunieco_i", ConvertUtil.nvl(pv_cdunieco_i));
		    map.put("pv_cdramo_i", ConvertUtil.nvl(pv_cdramo_i));
            map.put("pv_estado_i", pv_estado_i);
            map.put("pv_nmpoliza_i", ConvertUtil.nvl(pv_nmpoliza_i));
            map.put("pv_nmsuplem_i", ConvertUtil.nvl(pv_nmsuplem_i));
            map.put("pv_cdagrupa_i", ConvertUtil.nvl(pv_cdagrupa_i));
            map.put("pv_nmrecibo_i", ConvertUtil.nvl(pv_nmrecibo_i));
            map.put("pv_cdcancel_i", pv_cdcancel_i);
            map.put("pv_status_i", pv_status_i);
            map.put("pv_fecancel_i", ConvertUtil.convertToDate(pv_fecancel_i));
            map.put("pv_swcancela_i", pv_swcancela_i);

            String endpointName = "MODIFICA_CANCELACION_POLIZAS";
	        WrapperResultados res =  returnBackBoneInvoke(map,endpointName);
	        return res.getMsgText();
	}

	public PagedList buscarPolizasACancelar(int start, int limit)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public String modificarCancelacionPoliza() throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public String revertirPolizasCanceladas(ConsultaPolizasCanceladasVO consultaPolizasCanceladasVO)throws ApplicationException {
		    HashMap map = new HashMap();
		    map.put("p_CDUNIECO", ConvertUtil.nvl(consultaPolizasCanceladasVO.getCdUnieco()));
		    map.put("p_CDRAMO", ConvertUtil.nvl(consultaPolizasCanceladasVO.getCdRamo()));
            map.put("p_ESTADO", consultaPolizasCanceladasVO.getEstado());
            map.put("p_NMPOLIZA", ConvertUtil.nvl(consultaPolizasCanceladasVO.getNmPoliza()));
            map.put("p_NSUPLOGI", ConvertUtil.nvl(consultaPolizasCanceladasVO.getNSupLogi()));
	        map.put("p_NMSUPLEM", ConvertUtil.nvl(consultaPolizasCanceladasVO.getNmsuplem()));


         String endpointName = "REVERTIR_POLIZAS_CANCELADAS";
	        WrapperResultados res =  returnBackBoneInvoke(map,endpointName);
	        return res.getMsgText();
	}

	@SuppressWarnings("unchecked")
	public TableModelExport getModelPolizasCanceladas(String pv_asegurado_i, String pv_dsuniage_i, String pv_dsramo_i, String pv_nmpoliza_i,
			   String pv_nmsituac_i, String pv_dsrazon_i,String pv_fecancel_ini_i, String pv_fecancel_fin_i) throws ApplicationException {

		TableModelExport model = new TableModelExport();

		List lista = null;
		HashMap map = new HashMap();

		 map.put("pv_asegurado_i", pv_asegurado_i);
	        map.put("pv_dsuniage_i", pv_dsuniage_i);
	        map.put("pv_dsramo_i", pv_dsramo_i);
	        map.put("pv_nmpoliza_i", pv_nmpoliza_i);
	        map.put("pv_nmsituac_i", pv_nmsituac_i);
	        map.put("pv_cdrazon_i", pv_dsrazon_i);
	        map.put("pv_fecancel_ini_i", ConvertUtil.convertToDate(pv_fecancel_ini_i));
	        map.put("pv_fecancel_fin_i", ConvertUtil.convertToDate(pv_fecancel_fin_i));

		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_POLIZAS_CANCELADAS_EXPORT");
        model.setInformation(lista);
        model.setColumnName(new String[]{"ASEGURADO","ASEGURADORA","PRODUCTO","POLIZA","INCISO","FECHA CANCELACION","MOTIVO DE CANCELACION"});

		return model;
	}



    @SuppressWarnings("unchecked")
	public String calcularPrima (String cdUniEco, String cdRamo, String nmPoliza, String  feEfecto, String feCancel, String feVencim, String cdRazon) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pv_cdunieco_i", ConvertUtil.nvl(cdUniEco));
        map.put("pv_cdramo_i", ConvertUtil.nvl(cdRamo));
        map.put("pv_nmpoliza_i", ConvertUtil.nvl(nmPoliza));
        map.put("pv_feefecto_i", ConvertUtil.convertToDate(feEfecto));
        map.put("pv_fecancel_i", ConvertUtil.convertToDate(feCancel));
        map.put("pv_fevencim_i", ConvertUtil.convertToDate(feVencim));
        map.put("pv_cdrazon_i", ConvertUtil.nvl(cdRazon));


        WrapperResultados res = returnBackBoneInvoke(map, "CANCELACION_MANUAL_POLIZAS_CALCULAR_PRIMA");
        return res.getResultado();
    }


    @SuppressWarnings("unchecked")
	public String guardarCancelacion(List<RehabilitacionManual_PolizaVO> listaVO) throws ApplicationException {
        WrapperResultados res = new WrapperResultados();
        for (int i=0; i<listaVO.size(); i++) {
            HashMap map = new HashMap();
            RehabilitacionManual_PolizaVO rehabilitacionManual_PolizaVO = (RehabilitacionManual_PolizaVO)listaVO.get(i);
            map.put("pv_cdunieco_i", ConvertUtil.nvl(rehabilitacionManual_PolizaVO.getCdAseguradora()));
            map.put("pv_cdramo_i", ConvertUtil.nvl(rehabilitacionManual_PolizaVO.getCdProducto()));
            map.put("pv_cduniage_i", ConvertUtil.nvl(rehabilitacionManual_PolizaVO.getCdUniAge()));
            map.put("pv_estado_i", rehabilitacionManual_PolizaVO.getEstado());
            map.put("pv_nmpoliza_i", ConvertUtil.nvl(rehabilitacionManual_PolizaVO.getNmPoliza()));
            map.put("pv_nmsituac_i", ConvertUtil.nvl(rehabilitacionManual_PolizaVO.getNmSituac()));
            map.put("pv_cdrazon_i", rehabilitacionManual_PolizaVO.getCdRazonCancelacion());
            map.put("pv_comenta_i", rehabilitacionManual_PolizaVO.getComentariosCancelacion());
            map.put("pv_feefecto_i", ConvertUtil.convertToDate(rehabilitacionManual_PolizaVO.getFeEfecto()));
            map.put("pv_fevencim_i", ConvertUtil.convertToDate(rehabilitacionManual_PolizaVO.getFechaVencimiento()));
            map.put("pv_fecancel_i", ConvertUtil.convertToDate(rehabilitacionManual_PolizaVO.getFechaCancelacion()));
            map.put("pv_usuario_i", ""); //Verificar

            res = returnBackBoneInvoke(map, "CANCELACION_MANUAL_POLIZAS_GUARDAR");
        }
        return res.getMsgText();
    }



	@SuppressWarnings("unchecked")
	public TableModelExport getModelPolizasACancelar(String pv_asegurado_i,
			String pv_dsuniage_i, String pv_dsramo_i, String pv_nmpoliza_i,
			String pv_nmsituac_i) throws ApplicationException {

		TableModelExport model = new TableModelExport();

		List lista = null;

		HashMap map = new HashMap();
        //setear los parametros de entrada
        map.put("pv_asegurado_i", pv_asegurado_i);
        map.put("pv_dsuniage_i", pv_dsuniage_i);
        map.put("pv_dsramo_i", pv_dsramo_i);
        map.put("pv_nmpoliza_i", pv_nmpoliza_i);
        map.put("pv_nmsituac_i", pv_nmsituac_i);

        lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_POLIZAS_CANCELAR_EXPORT");
        model.setInformation(lista);
		model.setColumnName(new String[]{"ASEGURADO","ASEGURADORA","PRODUCTO","POLIZA","INCISO","FECHA CANCELACION"});

		return model;
	}

    @SuppressWarnings("unchecked")
	public String rehabilitarPoliza (String cdUniEco, String cdRamo, String estado, String nmPoliza, String feEfecto, String feVencim, String feCancel,
            String feReInst, String cdRazon, String cdPerson, String cdMoneda, String nmCancel, String comentarios, String nmSuplem) throws ApplicationException {

    	Converter converter = new UserSQLDateConverter("");
    	HashMap map = new HashMap ();
        map.put("pv_cdunieco_i", ConvertUtil.nvl(cdUniEco));
        map.put("pv_cdramo_i", ConvertUtil.nvl(cdRamo));
        map.put("pv_estado_i", estado);
        map.put("pv_nmpoliza_i", ConvertUtil.nvl(nmPoliza));
        //map.put("pv_feefecto_i", ConvertUtil.convertToDate(feEfecto));
        map.put("pv_feefecto_i", converter.convert(java.util.Date.class, feEfecto));
        //map.put("pv_fevencim_i", ConvertUtil.convertToDate(feVencim));
        map.put("pv_fevencim_i", converter.convert(java.util.Date.class, feVencim));
        //map.put("pv_fecancel_i", ConvertUtil.convertToDate(feCancel));
		map.put("pv_fecancel_i", converter.convert(java.util.Date.class, feCancel));
        //map.put("pv_fereinst_i", ConvertUtil.convertToDate(feReInst));
		map.put("pv_fereinst_i", converter.convert(java.util.Date.class, feReInst));
        map.put("pv_cdrazon_i", ConvertUtil.nvl(cdRazon));
        map.put("pv_cdperson_i", ConvertUtil.nvl(cdPerson));
        map.put("pv_cdmoneda_i", cdMoneda);
        map.put("pv_nmcancel_i", ConvertUtil.nvl(nmCancel));
        map.put("pv_comments_i", comentarios);
        map.put("pv_nmsuplem_i", ConvertUtil.nvl(nmSuplem));

        WrapperResultados res = returnBackBoneInvoke(map, "REHABILITACION_MANUAL_REHABILITAR_POLIZA");

        return res.getMsgText();
    }
    
    @SuppressWarnings("unchecked")
	public String guardarPolizaMaestra(Map<String,String> param, String endPointName) throws ApplicationException {
        WrapperResultados res = new WrapperResultados();
        //for (int i=0; i<param.size(); i++) {
            HashMap map = new HashMap();
           // PolizaMaestraVO polizaMaestraVO = (PolizaMaestraVO);
            map.put("pv_cdpolmtra_i", ConvertUtil.nvl(param.get("cdpolmtra")));
            map.put("pv_cdelemento_i", ConvertUtil.nvl(param.get("cdelemento")));
            map.put("pv_cdcia_i", ConvertUtil.nvl(param.get("cdcia")));
            map.put("pv_cdramo_i", ConvertUtil.nvl(param.get("cdramo")));
            map.put("pv_cdtipo_i", param.get("cdtipo"));
            map.put("pv_nmpoliex_i", param.get("nmpoliex"));
            map.put("pv_nmpoliza_i", ConvertUtil.nvl(param.get("nmpoliza")));
            map.put("pv_feinicio_i", ConvertUtil.convertToDate(param.get("feinicio")));
            map.put("pv_fefin_i", ConvertUtil.convertToDate(param.get("fefin")));
            map.put("pv_cdnumpol_i", ConvertUtil.nvl(param.get("cdnumpol")));
            map.put("pv_cdnumren_i", ConvertUtil.nvl(param.get("cdnumren")));
            map.put("pv_cdtipsit_i", param.get("cdtipsit") );

            res = returnBackBoneInvoke(map, endPointName);
            if(logger.isDebugEnabled()){
            	logger.debug("res guardaPolizaMaestra=" + res);
            }
        //}
        return res.getMsgText();
    }
    
    /**
	 * Permite rehabilitar una poliza
	 * Usa el sp PKG_CANCELA.P_REHABILITA_POLIZA
	 * 
	 * @return Mensaje de exito/error
	 */
	@SuppressWarnings("unchecked")
	public String rehabilitarPolizaMasiva (List<ConsultaPolizasCanceladasVO> rehabGrillaList) throws ApplicationException {
		
		 WrapperResultados res = new WrapperResultados();
		
		 for (int i=0; i<rehabGrillaList.size(); i++) {
			 if (rehabGrillaList.get(i)!=null)
	         { 
			   HashMap map = new HashMap();
			   ConsultaPolizasCanceladasVO rehabilitaVO = rehabGrillaList.get(i);
				
				map.put("pv_cdunieco_i", rehabilitaVO.getCdUnieco());
				map.put("pv_cdramo_i", rehabilitaVO.getCdRamo());
				map.put("pv_estado_i", rehabilitaVO.getEstado());
				map.put("pv_nmpoliza_i", rehabilitaVO.getNmPoliza());
				map.put("pv_feefecto_i", ConvertUtil.convertToDate(rehabilitaVO.getFeefecto()));
				map.put("pv_fevencim_i", ConvertUtil.convertToDate(rehabilitaVO.getFevencim()));
				map.put("pv_fecancel_i", ConvertUtil.convertToDate(rehabilitaVO.getFeCancel()));
				map.put("pv_fereinst_i", ConvertUtil.convertToDate(rehabilitaVO.getFeproren()));
				map.put("pv_cdrazon_i", rehabilitaVO.getCdRazon());
				map.put("pv_cdperson_i", rehabilitaVO.getCdPerson());
				map.put("pv_cdmoneda_i", rehabilitaVO.getCdMoneda());
				map.put("pv_nmcancel_i", rehabilitaVO.getNmCancel());
				map.put("pv_comments_i", rehabilitaVO.getComentarios());
				map.put("pv_nmsuplem_i", rehabilitaVO.getNmsuplem());
			   
			   res = returnBackBoneInvoke(map, "REHABILITACION_MASIVA_REHABILITAR_POLIZA");
		     }
		 }
		return res.getMsgText();
	}
	
	public PagedList buscarPolizasCanceladasRecibosDetalle( String pv_cdUnieco_i,String pv_cdRamo_i ,
			String pv_Estado_i ,String  pv_NmPoliza_i,String pv_nmRecibo_i, int start,int  limit)
			throws ApplicationException {
		
		HashMap map = new HashMap();
        //setear los parametros de entrada
        map.put("pv_cdUnieco_i",pv_cdUnieco_i );
        map.put("pv_cdRamo_i",  pv_cdRamo_i);
        map.put("pv_Estado_i",  pv_Estado_i);
        map.put("pv_NmPoliza_i", pv_NmPoliza_i);
        map.put("pv_nmRecibo_i", pv_nmRecibo_i);
        
        logger.debug(" map>>>" +   map.toString());

        String endpointName = "POLIZAS_CANCELADAS_OBTIENE_RECIBOS_DETALLE";
        return pagedBackBoneInvoke(map, endpointName, start, limit);
	}
	
	
}
