package mx.com.aon.portal.web.tests;

import mx.com.aon.portal.model.RehabilitacionManual_PolizaVO;
import mx.com.aon.portal.model.RehabilitacionManual_RequisitosVO;
import mx.com.aon.portal.service.RehabilitacionManualManager;
import mx.com.aon.portal.service.RehabilitacionMasivaManager;

public class RehabilitacionManualTest extends AbstractTestCases {
	protected RehabilitacionManualManager rehabilitacionManualManager;

	public void testObtenerPoliza () throws Exception {
		try {
			//RehabilitacionManual_PolizaVO rehabilitacionManual_PolizaVO = rehabilitacionManualManager.obtenerInformacionPoliza("", "", "", "M", "", "1");
			//RehabilitacionManual_PolizaVO rehabilitacionManual_PolizaVO = rehabilitacionManualManager.obtenerDatosCancelacion("", "", "", "M", "", "1");
			//System.out.println("Asegurado: " + rehabilitacionManual_PolizaVO.getDsAsegurado());
			//RehabilitacionManual_RequisitosVO rehabilitacionManual_RequisitosVO = rehabilitacionManualManager.obtenerRequisitos("", "", "");
			//System.out.println("Requisito: " + rehabilitacionManual_RequisitosVO.getDsRequisito());
			String error = rehabilitacionManualManager.validarRecibosPagados("1", "115", "12", "M", "2.45321512E17", "28/07/2004");
			System.out.println(error);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
