package mx.com.aon.portal.web.tests;

import mx.com.aon.portal.model.PlanesMPlanProVO;
import mx.com.aon.portal.service.MantenimientoPlanesManager;

public class ConfigurarPlanesTest extends AbstractTestCases {
	protected MantenimientoPlanesManager mantenimientoPlanesManager;
		
/*	public void testGetConfiguracionPlanes () throws Exception{
		try {
			PlanesMPlanProVO planesMPlanProVO = new PlanesMPlanProVO ();
			planesMPlanProVO.setCdRamo("777");
			planesMPlanProVO.setCdPlan("1");
			planesMPlanProVO.setCdTipSit("a");
			planesMPlanProVO.setCdGarant("d");
			mantenimientoPlanesManager.buscarPlanes(0, 10, planesMPlanProVO);
			logger.info("Se ejecuto la busqueda");
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
*/
/*    public void testGetConfiguracionPlane () throws Exception{
        try {
            PlanesMPlanProVO planesMPlanProVO = new PlanesMPlanProVO ();
            planesMPlanProVO.setCdRamo("781");
            planesMPlanProVO.setCdPlan("2");
            planesMPlanProVO.setCdTipSit("CB");
            planesMPlanProVO.setCdGarant("ROBO");
            mantenimientoPlanesManager.getPlan(planesMPlanProVO);
            logger.info("Se ejecuto la busqueda");
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
*/
/*
	public void testGuardarPlan () throws Exception {
		try {
			PlanesMPlanProVO planesMPlanProVO = new PlanesMPlanProVO();
			planesMPlanProVO.setCdRamo("786");
			planesMPlanProVO.setCdPlan("2");
			planesMPlanProVO.setCdTipSit("CB");
			planesMPlanProVO.setCdGarant("ROBO");
			planesMPlanProVO.setSwOblig("1");
			mantenimientoPlanesManager.insertarPlan(planesMPlanProVO);
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
*/

/*	public void testActualizaPlan () throws Exception {
		try {
			PlanesMPlanProVO planesMPlanProVO = new PlanesMPlanProVO();
			planesMPlanProVO.setCdRamo("780");
			planesMPlanProVO.setCdPlan("1");
			planesMPlanProVO.setCdTipSit("CB");
			planesMPlanProVO.setCdGarant("ROBO");
			planesMPlanProVO.setSwOblig("1");
			mantenimientoPlanesManager.actualizarPlan(planesMPlanProVO);
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
*/
/*	public void testBorraPlan () throws Exception {
	try {
		PlanesMPlanProVO planesMPlanProVO = new PlanesMPlanProVO();
		planesMPlanProVO.setCdRamo("779");
		planesMPlanProVO.setCdPlan("1");
		planesMPlanProVO.setCdTipSit("A1");
		planesMPlanProVO.setCdGarant("ROBO");
		planesMPlanProVO.setSwOblig("0");
		mantenimientoPlanesManager.borrarPlan(planesMPlanProVO);
	}catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}
*/
	
	public void testGetModel()throws Exception{
		try{
			this.mantenimientoPlanesManager.getModel("", "", "", "");
			}catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	}
	
}
