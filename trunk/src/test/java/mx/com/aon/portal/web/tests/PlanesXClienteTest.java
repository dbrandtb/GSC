package mx.com.aon.portal.web.tests;

import java.util.List;

import mx.com.aon.portal.model.DetallePlanXClienteVO;
import mx.com.aon.portal.service.DetallePlanXClienteManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.model.TiposSituacionVO;

public class PlanesXClienteTest extends AbstractTestCases {


	protected DetallePlanXClienteManager detallePlanXClienteManager;
	
    
    @SuppressWarnings("unchecked")
	public void testPlanesXCliente() throws Exception {
    	try {
    		PagedList pagedlist = new PagedList();
			pagedlist = detallePlanXClienteManager.buscarDetallePlanes(0, 10, "", "m", "777", "2", "", "");
    		List lista = pagedlist.getItemsRangeList();
    		System.out.println("Valor de size: " + lista.size());
    		for (int i=0; i<lista.size(); i++) {
    			DetallePlanXClienteVO detallePlanXClienteVO = (DetallePlanXClienteVO)lista.get(i);
    			System.out.println("DsElemen: " + detallePlanXClienteVO.getDsElemen() + "  " + detallePlanXClienteVO.getDsUniEco() + "  " + detallePlanXClienteVO.getCdUniEco());
    		}
			logger.info("Se ejecuto la busqueda");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
	
/*	public void testGetModel()throws Exception{
		try{
			this.detallePlanXClienteManager.getModel("", "777", "2", "", "", "");
		}catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
		
	}
*/	
  /*public void testGetPlanXCliente () throws Exception {
    	try {
    		DetallePlanXClienteVO detallePlanXClienteVO = new DetallePlanXClienteVO();
            detallePlanXClienteVO.setCdPerson("300");
            detallePlanXClienteVO.setCdRamo("777");
    		detallePlanXClienteVO.setCdPlan("2");
    		detallePlanXClienteVO.setCdTipSit("CB");
    		detallePlanXClienteVO.setCdGarant("ROBO");
    		detallePlanXClienteVO.setCdUniEco("44");
    		detallePlanXClienteVO.setCdElemento("6");
    		DetallePlanXClienteVO detallePlanXClienteVO2 = new DetallePlanXClienteVO();
    		detallePlanXClienteVO2 = detallePlanXClienteManager.getPlanXCliente(detallePlanXClienteVO);
    		System.out.println("Resultados: " + detallePlanXClienteVO2.getDsGarant());
    	}catch (Exception e) {
    		e.printStackTrace();
    		throw e;
    	}
    }*/
	/*public void testEdicionPlanesXCliente () throws Exception {
		DetallePlanXClienteVO detallePlanXClienteVO = new DetallePlanXClienteVO();
		try{
			detallePlanXClienteVO.setCDPerson("200");
			detallePlanXClienteVO.setCDRamo("70");
			detallePlanXClienteVO.setCDElemento("1");
			detallePlanXClienteVO.setCDGarant("1");
			detallePlanXClienteVO.setCDPlan("2");
			detallePlanXClienteVO.setCDTipSit("1");
			detallePlanXClienteVO.setSWOblig("1");
			detallePlanXClienteVO.setCdUniEco("0");
			this.detallePlanXClienteManager.setPlanXCliente(detallePlanXClienteVO);
		}catch (Exception e) {
			e.printStackTrace();
			//throw new Exception("imposible guardar!!");
		}
	}
*/
/*	public void testInsertarPlanesXCliente () throws Exception {
		DetallePlanXClienteVO detallePlanXClienteVO = new DetallePlanXClienteVO();
		try{
			detallePlanXClienteVO.setCdPerson("300");
			detallePlanXClienteVO.setCdRamo("70");
			detallePlanXClienteVO.setCdElemento("1");
			detallePlanXClienteVO.setCdGarant("1");
			detallePlanXClienteVO.setCdPlan("2");
			detallePlanXClienteVO.setCdTipSit("1");
			detallePlanXClienteVO.setSwOblig("0");
			detallePlanXClienteVO.setCdUniEco("1");
			this.detallePlanXClienteManager.addPlanXCliente(detallePlanXClienteVO);
		}catch (Exception e) {
			e.printStackTrace();
			//throw new Exception("imposible guardar!!");
		}
	}
*/
	/*public void testComboSituacion () throws Exception {
		try {
			this.detallePlanXClienteManager.comboTipoSituacion();
			logger.info("Se ejecutó el combo de Tipos de Situación");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
*//*	public void testComboCoberturas () throws Exception {
		try {
			detallePlanXClienteManager.comboCoberturas();
			logger.info("Se ejecutó el combo de Coberturas");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}*/
/*  public void testComboAseguradoras () throws Exception {
    	try {
    		detallePlanXClienteManager.comboAseguradora();
    		logger.info("Se ejecuto el combo de Aseguradoras");
    	}catch (Exception e) {
    		e.printStackTrace();
    	}
    }*/
}