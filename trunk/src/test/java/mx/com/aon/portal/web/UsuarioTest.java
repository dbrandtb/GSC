package mx.com.aon.portal.web;

import mx.com.aon.portal.service.UsuarioManager;
import mx.com.aon.portal.web.tests.AbstractTestCases;

import java.util.List;


public class UsuarioTest extends AbstractTestCases {


	protected UsuarioManager usuarioManager;

    

    @SuppressWarnings("unchecked")
	public void testGetAttributesUser() throws Exception {
    	try {
            @SuppressWarnings("unused")
            List lista = usuarioManager.getAttributesUser("herbe");
            System.out.println("sali");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }

    public void testTipoActividad() throws Exception {
        try {
            @SuppressWarnings("unused")
            List lista = usuarioManager.getClientesRoles("herbe");
            System.out.println("sali");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
