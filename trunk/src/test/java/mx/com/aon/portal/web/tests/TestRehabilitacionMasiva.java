package mx.com.aon.portal.web.tests;

import java.util.List;

import mx.com.aon.portal.model.RehabilitacionMasivaVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.RehabilitacionMasivaManager;

public class TestRehabilitacionMasiva extends AbstractTestCases {
	protected RehabilitacionMasivaManager rehabilitacionMasivaManager;

	@SuppressWarnings("unchecked")
	public void testBuscarPolizasARehabilitar () throws Exception{
		try {
			PagedList pagedList = rehabilitacionMasivaManager.buscarPolizasCanceladasARehabilitar("", "", "", "", "", 0, 10);
			List<RehabilitacionMasivaVO> lista = pagedList.getItemsRangeList();
			for (int i=0; i<lista.size(); i++) {
				RehabilitacionMasivaVO rehabilitacionMasivaVO = (RehabilitacionMasivaVO)lista.get(i);
				System.out.println("Producto: " + rehabilitacionMasivaVO.getDsProducto());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
