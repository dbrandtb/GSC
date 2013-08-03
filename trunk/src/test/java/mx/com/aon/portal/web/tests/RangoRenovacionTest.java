package mx.com.aon.portal.web.tests;


import mx.com.aon.portal.model.RangoRenovacionReporteVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.RangoRenovacionReporteManager;

public class RangoRenovacionTest extends AbstractTestCases{
	
	protected RangoRenovacionReporteManager rangoRenovacionReporteManager;
	
	//*****************************************************************************
	@SuppressWarnings("unchecked")
	public void testAgragarGuardarRangoRenovacion() throws Exception {
		try {
			
			RangoRenovacionReporteVO  rangoRenovacionReporteVO =  new RangoRenovacionReporteVO();
			
			rangoRenovacionReporteVO.setCdRenova("1");
			rangoRenovacionReporteVO.setCdRango("11");
			rangoRenovacionReporteVO.setCdInicioRango("3");
			rangoRenovacionReporteVO.setCdFinRango("3");
			rangoRenovacionReporteVO.setDsRango("3");
			
			rangoRenovacionReporteManager.agregarGuardarRangoRenovacion(rangoRenovacionReporteVO);
	        
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
	
	
	/*
	 public void testBuscarAgrupaciones() throws Exception {
	    	try {
	            @SuppressWarnings("unused")
				PagedList  pagedList = rangoRenovacionReporteManager.buscarRangosRenovacion("2", 1, 10);
				
			} catch (Exception e) {
				logger.error("Exception en Agregar AgrupacionPolizas");
				e.printStackTrace();
				throw e;

			}

	    }
	 
*/
}
