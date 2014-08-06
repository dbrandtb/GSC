package mx.com.gseguros.portal.mesacontrol.dao.impl;

import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.mesacontrol.dao.MesaControlDAO;

public class MesaControlDAOImpl extends AbstractManagerDAO implements MesaControlDAO {

	/*
	@Override
	public TramiteVO insertaTramite(Map<String, Object> params) throws Exception {
		//TODO: terminar
//		Map<String, Object> result = ejecutaSP(new PMovMesacontrol(getDataSource()), params);
//		BaseVO baseVO = new BaseVO();
//		baseVO.setKey(result.get("msg_id").toString());
//		baseVO.setValue(result.get("msg_title").toString());
//    	return baseVO;
		return null;
	}
	
	protected class PMovMesacontrol extends StoredProcedure {
		protected PMovMesacontrol(DataSource dataSource) {
			super(dataSource,"PKG_SATELITES.P_MOV_MESACONTROL");
			declareParameter(new SqlParameter("pv_cdunieco_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdsucadm_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdsucdoc_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtiptra_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_ferecepc_i", 		OracleTypes.DATE));
			declareParameter(new SqlParameter("pv_cdagente_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_referencia_i", 	OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nombre_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_festatus_i",      OracleTypes.DATE));
			declareParameter(new SqlParameter("pv_status_i",        OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_comments_i",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsolici_i",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipsit_i",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor01"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor02"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor03"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor04"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor05"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor06"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor07"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor08"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor09"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor10"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor11"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor12"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor13"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor14"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor15"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor16"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor17"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor18"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor19"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor20"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor21"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor22"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor23"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor24"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor25"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor26"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor27"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor28"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor29"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor30"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor31"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor32"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor33"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor34"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor35"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor36"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor37"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor38"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor39"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor40"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor41"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor42"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor43"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor44"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor45"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor46"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor47"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor48"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor49"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor50"    , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_tramite_o", 	OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o", 	OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o", 		OracleTypes.VARCHAR));
			compile();
		}
	
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			WrapperResultados wrapperResultados = mapper.build(map);
			
			wrapperResultados.setItemMap(new HashMap<String, Object>());
			wrapperResultados.getItemMap().put("ntramite", map.get("pv_tramite_o"));

			return wrapperResultados;
		}
	}
	*/
	
	
	
}