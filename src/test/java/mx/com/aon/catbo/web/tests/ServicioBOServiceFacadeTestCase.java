package mx.com.aon.catbo.web.tests;

import junit.framework.TestCase;
import mx.com.aon.catbo.facade.ServicioBOServiceFacade;
import mx.com.aon.catbo.model.ResultadoGeneraCasoVO;

/**
 * Created by IntelliJ IDEA.
 * User: gabrielforradellas
 * Date: Sep 23, 2008
 * Time: 12:03:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServicioBOServiceFacadeTestCase extends TestCase {

    public void testGuardarCasos() {
        try {

            ServicioBOServiceFacade servicioBOServiceFacade = new ServicioBOServiceFacade();
            ResultadoGeneraCasoVO res = servicioBOServiceFacade.guardarCasos("1","DARROYO","42","1","M", "ABC-000100","ABC-000100",null, "20","FD00475L","33871",null,null,"3","10","Ingreso de Caso");
            System.out.println("Salio");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
