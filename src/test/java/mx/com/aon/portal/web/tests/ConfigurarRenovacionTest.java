package mx.com.aon.portal.web.tests;

import mx.com.aon.portal.service.ConfiguracionRenovacionManager;
import mx.com.aon.portal.service.PagedList;

public class ConfigurarRenovacionTest extends AbstractTestCases{
	
	protected ConfiguracionRenovacionManager configuracionRenovacionManager;
	
	//*****************************************************************************
	@SuppressWarnings("unchecked")
	public void testObtieneClientesTipo() throws Exception {
    	try {
    		PagedList pagedList  =  configuracionRenovacionManager.obtenerClientesYTiposDeRenovacion("", "", "", "", 1, 10);
    		logger.info("Total Items: " + pagedList.getTotalItems());
    	} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }

}
