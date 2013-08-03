package mx.com.aon.portal.web.tests;
import java.util.List;

import mx.com.aon.portal.model.PlanesMPlanProVO;
import mx.com.aon.portal.service.MantenimientoPlanesManager;
import mx.com.aon.portal.service.PagedList;

public class MantenimientoPlanes_ObtenerPlanesTest extends AbstractTestCases{

	protected MantenimientoPlanesManager mantenimientoPlanesManager;
	
	
/*	public void testGetModel()throws Exception{
		try{
			this.mantenimientoPlanesManager.getModel("777","2","aut","robo");
		}catch (Exception e) {
		e.printStackTrace();
		throw e;
	}

	}
*/	
	
	/*public void testMantenimientoPlanes () throws Exception {
		try {
			PlanesMPlanProVO planesMPlanProVO = new PlanesMPlanProVO ();
			planesMPlanProVO.setCdRamo("777");
			planesMPlanProVO.setCdPlan("2");
			planesMPlanProVO.setCdTipSit("aut");
			planesMPlanProVO.setCdGarant("robo");
			PagedList pagedList = new PagedList();
			pagedList = mantenimientoPlanesManager.buscarPlanes(0, 10, planesMPlanProVO);
			List lista = pagedList.getItemsRangeList();
			for (int i=0; i<lista.size(); i++) {
				PlanesMPlanProVO planProVO = (PlanesMPlanProVO)lista.get(i);
				System.out.println("Registro: " + planProVO.getDsGarant());
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}*/
/*
	public void testGetMantenimientoPlanes () throws Exception {
		try {
			PlanesMPlanProVO planesMPlanProVO = new PlanesMPlanProVO ();
			planesMPlanProVO.setCdRamo("777");
			planesMPlanProVO.setCdPlan("2");
			planesMPlanProVO.setCdTipSit("CB");
			planesMPlanProVO.setCdGarant("ROBO");

			PlanesMPlanProVO planesMPlanProVO2 = new PlanesMPlanProVO();
			planesMPlanProVO2 = mantenimientoPlanesManager.getPlan(planesMPlanProVO);
			System.out.println("Garantia: " + planesMPlanProVO2.getDsGarant());
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
*/
}
