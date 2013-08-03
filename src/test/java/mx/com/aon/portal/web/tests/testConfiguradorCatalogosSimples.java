package mx.com.aon.portal.web.tests;



import java.util.ArrayList;
import java.util.List;

import mx.com.aon.portal.model.ColumnVO;
import mx.com.aon.portal.model.DynamicBeanVO;
import mx.com.aon.portal.model.ElementoComboBoxVO;
import mx.com.aon.portal.model.ValoresCamposCatalogosSimplesVO;
import mx.com.aon.portal.service.ConfiguradorCatalogosManager;
import mx.com.aon.portal.service.ManttoCatalogosLogicosManager;
import mx.com.aon.portal.service.PagedList;

public class testConfiguradorCatalogosSimples extends AbstractTestCases {
	protected ConfiguradorCatalogosManager configuradorCatalogosManager;
	protected ManttoCatalogosLogicosManager manttoCatalogosLogicosManager;

	public void testColumnas () throws Exception {
		try {
			ValoresCamposCatalogosSimplesVO valoresCamposCatalogosSimplesVO = new ValoresCamposCatalogosSimplesVO();
			List<ValoresCamposCatalogosSimplesVO> lista = new ArrayList<ValoresCamposCatalogosSimplesVO>();
			PagedList pagedList = configuradorCatalogosManager.obtenerColumnasGrilla("TPERPAG", lista, 0, 15);
			
			List listaDynamicBeans = pagedList.getItemsRangeList();
			for (int i=0; i<listaDynamicBeans.size(); i++) {
				DynamicBeanVO dynamicBeanVO = (DynamicBeanVO)listaDynamicBeans.get(i);
				
				List<ColumnVO> listaColumnas = dynamicBeanVO.getListaColumnas();
				for (int j=0; j<listaColumnas.size(); j++){
					ColumnVO columnVO = listaColumnas.get(j);
					System.out.println("name: " + columnVO.getName() + "\n valor " + columnVO.getValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*public void testObtenerRegistrosCatalogosLogicos () throws Exception {
		try {
			manttoCatalogosLogicosManager.obtenerListadoCatalogosLogicos("CAT", "1", "1", "1", "B", 0, 10);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	/*public void testObtenerRegistroCatalogoLogico () throws Exception {
		try {
			manttoCatalogosLogicosManager.obtenerRegistroCatalogosLogicos("CATBOMODUL", "1", "1", "3");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
}
