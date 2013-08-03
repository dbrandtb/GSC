package mx.com.aon.portal.web.tests;

import mx.com.aon.portal.model.FormulaMetodoCancelacionVO;
import mx.com.aon.portal.service.FormulaMetodoCancelacionManager;

public class FormulaMetodoCancelacionTest extends AbstractTestCases {

	protected FormulaMetodoCancelacionManager formulaMetodoCancelacionManager;

	public void testObtenerEncabezado () throws Exception {
		try {
			FormulaMetodoCancelacionVO formulaMetodoCancelacionVO = new FormulaMetodoCancelacionVO();
			formulaMetodoCancelacionVO = formulaMetodoCancelacionManager.getEncabezado("FLAT");
			System.out.println("DSMETODO: " + formulaMetodoCancelacionVO.getDsMetodo());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
