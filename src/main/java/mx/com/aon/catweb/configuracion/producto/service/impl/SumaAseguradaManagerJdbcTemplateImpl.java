package mx.com.aon.catweb.configuracion.producto.service.impl;

import java.util.HashMap;

import mx.com.aon.catweb.configuracion.producto.service.SumaAseguradaManagerJdbcTemplate;
import mx.com.aon.catweb.configuracion.producto.sumaAsegurada.model.SumaAseguradaIncisoVO;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;
import mx.com.aon.portal.util.ConvertUtil;
import mx.com.aon.portal.util.WrapperResultados;

public class SumaAseguradaManagerJdbcTemplateImpl extends AbstractManagerJdbcTemplateInvoke implements SumaAseguradaManagerJdbcTemplate {

	@SuppressWarnings("unchecked")
	public void agregaSumaAseguradaInciso(SumaAseguradaIncisoVO sumaAseguradaInciso) throws ApplicationException {
		
		HashMap map = new HashMap();
		map.put("PV_CDRAMO_I", ConvertUtil.nvl(sumaAseguradaInciso.getCodigoRamo()));
		map.put("PV_CDCAPITA_I", ConvertUtil.nvl(sumaAseguradaInciso.getCodigoCapital()));
		map.put("PV_CDTIPCAP_I", ConvertUtil.nvl(sumaAseguradaInciso.getCodigoTipoCapital()));
		map.put("PV_DSCAPITA_I", ConvertUtil.nvl(sumaAseguradaInciso.getDescripcionCapital()));
		map.put("PV_CDGARANT_I", ConvertUtil.nvl(sumaAseguradaInciso.getCodigoCobertura()));
		map.put("PV_CDPRESEN_I", ConvertUtil.nvl(sumaAseguradaInciso.getCodigoLeyenda()));
		map.put("PV_CDTIPSIT_I", ConvertUtil.nvl(sumaAseguradaInciso.getCodigoTipoSituacion()));
		map.put("PV_OTTABVAL_I", ConvertUtil.nvl(sumaAseguradaInciso.getCodigoListaValor()));
		map.put("PV_SWREAUTO_I", ConvertUtil.nvl(sumaAseguradaInciso.getSwitchReinstalacion()));
		map.put("pv_cdexpdef_i", ConvertUtil.nvl(sumaAseguradaInciso.getCodigoExpresion()));

		WrapperResultados res = returnBackBoneInvoke(map, "AGREGAR_SUMA_ASEGURADA_INCISO_JDBC_TMPL");
		//return res.getMsgText();
	}


}
