package mx.com.aon.portal.web.tests;

import java.util.ArrayList;
import java.util.Iterator;

import mx.com.aon.portal.model.ClienteVO;
import mx.com.aon.portal.model.ConfigurarEstructuraVO;
import mx.com.aon.portal.model.EstructuraVO;
import mx.com.aon.portal.service.EstructuraManager;
import mx.com.aon.portal.service.EstructuraManagerTareasChecklist;
import mx.com.aon.portal.service.ManagerCuentaChecklist;
import mx.com.aon.portal.util.WrapperResultados;

public class CopiaConfiguraCuentaTestCase extends AbstractTestCases {


	protected ManagerCuentaChecklist managerCuentaChecklist;
	
    public void testCopiaConfigura() throws Exception {
    	try {
    		managerCuentaChecklist.copiarConfiguracionCuenta("34", "6", "374775");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
	
}

