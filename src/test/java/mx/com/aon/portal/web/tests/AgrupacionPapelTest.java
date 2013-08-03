package mx.com.aon.portal.web.tests;

import mx.com.aon.portal.model.AgrupacionPapel_AgrupacionVO;
import mx.com.aon.portal.service.AgrupacionPapelManager;

public class AgrupacionPapelTest extends AbstractTestCases {
	protected AgrupacionPapelManager agrupacionPapelManager;

/*	public void testGetAgrupacion () throws Exception {
		try {
			AgrupacionPapel_AgrupacionVO agrupacionPapel_AgrupacionVO = new AgrupacionPapel_AgrupacionVO();
			agrupacionPapel_AgrupacionVO = agrupacionPapelManager.getAgrupacionPapel("1");
			System.out.println("Nombre: " + agrupacionPapel_AgrupacionVO.getDsNombre());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void testGuardarAgrupacion () throws Exception {
		try {
			agrupacionPapelManager.guardarAgrupacionPapel("653", "", "1", "56", "1", "105");
			logger.info("Se ejecuto el guardado....");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void testBuscarAgrupacion () throws Exception {
		try {
			agrupacionPapelManager.buscarPapeles("669", 0, 10);
			//agrupacionPapelManager.getAgrupacionPapel("653");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}*/
	
	
	public void testBorrarRol() throws Exception {
    	try {
    		this.agrupacionPapelManager.borrarRol("979", "1"); 
		} catch (Exception e) {
			e.printStackTrace();
			throw e; 
		}
    }

}
