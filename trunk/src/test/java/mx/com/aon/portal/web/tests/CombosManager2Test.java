package mx.com.aon.portal.web.tests;

import mx.com.aon.portal.service.CombosManager;
import mx.com.aon.portal.service.CombosManager2;

import java.util.List;

public class CombosManager2Test extends AbstractTestCases {



	protected CombosManager2 comboManagerJdbcTemplateImpl;

    /*public void testComboRolFuncionalidades() throws Exception {
    	try {
            List lista = comboManagerJdbcTemplateImpl.comboRolFuncionalidades("3002");
            System.out.println("sali");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }*/

    @SuppressWarnings("unchecked")
	public void testComboUsuarioFuncionalidades() throws Exception {
    	try {
            @SuppressWarnings("unused")
			List lista = comboManagerJdbcTemplateImpl.comboUsuarioFuncionalidades("3002", "EJECUTIVOCUENTA");
            System.out.println("sali");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }

    public void testTipoActividad() throws Exception {
        try {
            @SuppressWarnings("unused")
            List lista = comboManagerJdbcTemplateImpl.comboTiposActividad();
            System.out.println("sali");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


}
