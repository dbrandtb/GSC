package mx.com.aon.portal.service.impl;

import java.util.HashMap;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.RehabilitacionManual_PolizaVO;
import mx.com.aon.portal.service.CancelacionManualPolizasManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;

public class CancelacionManualPolizasManagerImpl extends AbstractManager
		implements CancelacionManualPolizasManager {

	public RehabilitacionManual_PolizaVO obtenerDatosCancelacion()
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public PagedList obtenerDatosIncisos (String cdUniEco, String cdRamo, String cdEstado, String nmPoliza, int start, int limit) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdUniEco", cdUniEco);
		map.put("cdRamo", cdRamo);
		map.put("cdEstado", cdEstado);
		map.put("nmPoliza", nmPoliza);

		return pagedBackBoneInvoke(map, "CANCELACION_MANUAL_POLIZAS_OBTENER_INCISOS", start, limit);
	}

	@SuppressWarnings("unchecked")
	public RehabilitacionManual_PolizaVO obtenerEncabezado(String cdUniEco,
			String cdRamo, String cdEstado, String nmPoliza)
			throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdUniEco", cdUniEco);
		map.put("cdRamo", cdRamo);
		map.put("cdEstado", cdEstado);
		map.put("nmPoliza", nmPoliza);
		
		return (RehabilitacionManual_PolizaVO)getBackBoneInvoke(map, "CANCELACION_MANUAL_POLIZAS_BUSCAR");
	}

	@SuppressWarnings("unchecked")
	public String calcularPrima (String cdUniEco, String cdRamo, String nmPoliza, String  feEfecto, String feCancel, String feVencim, String cdRazon) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdUniEco", cdUniEco);
		map.put("cdRamo", cdRamo);
		map.put("nmPoliza", nmPoliza);
		map.put("feEfecto", feEfecto);
		map.put("feCancel", feCancel);
		map.put("feVencim", feVencim);
		map.put("cdRazon", cdRazon);

		WrapperResultados res = returnBackBoneInvoke(map, "CANCELACION_MANUAL_POLIZAS_CALCULAR_PRIMA");
		return res.getResultado();
	}

	@SuppressWarnings("unchecked")
	public String guardarCancelacion(List<RehabilitacionManual_PolizaVO> listaVO) throws ApplicationException {
		WrapperResultados res = new WrapperResultados();
		for (int i=0; i<listaVO.size(); i++) {
			HashMap map = new HashMap();
			RehabilitacionManual_PolizaVO rehabilitacionManual_PolizaVO = (RehabilitacionManual_PolizaVO)listaVO.get(i);
			map.put("cdUniEco", rehabilitacionManual_PolizaVO.getCdAseguradora());
			map.put("cdRamo", rehabilitacionManual_PolizaVO.getCdProducto());
			map.put("cdUniEcoAge", rehabilitacionManual_PolizaVO.getCdUniAge());
			map.put("cdEstado", rehabilitacionManual_PolizaVO.getEstado());
			map.put("nmPoliza", rehabilitacionManual_PolizaVO.getNmPoliza());
			map.put("nmSituac", rehabilitacionManual_PolizaVO.getNmSituac());
			map.put("cdRazon", rehabilitacionManual_PolizaVO.getCdRazonCancelacion());
			map.put("dsComent", rehabilitacionManual_PolizaVO.getComentariosCancelacion());
			map.put("feEfecto", rehabilitacionManual_PolizaVO.getFeEfecto());
			map.put("feVencim", rehabilitacionManual_PolizaVO.getFechaVencimiento());
			map.put("feCancel", rehabilitacionManual_PolizaVO.getFechaCancelacion());
			map.put("dsUsuario", ""); //Verificar
			
			res = returnBackBoneInvoke(map, "CANCELACION_MANUAL_POLIZAS_GUARDAR");
		}
		return res.getMsgText();
	}


}
