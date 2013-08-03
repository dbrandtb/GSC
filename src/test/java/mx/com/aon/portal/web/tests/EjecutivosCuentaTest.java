/**
 * 
 */


/**
 * @author CIMA_USR
 *
 */

package mx.com.aon.portal.web.tests;

//import mx.com.aon.portal.service.CombosManager;
import java.util.List;

import mx.com.aon.portal.service.EjecutivosCuentaManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.model.EjecutivoCuentaVO;

public class EjecutivosCuentaTest extends AbstractTestCases {


	protected EjecutivosCuentaManager ejecutivosCuentaManager;
	

	public void testAgregarEjecutivosCuenta ()  {
		try {
			EjecutivoCuentaVO ejecutivoCuentaVO = new EjecutivoCuentaVO();
			
			 //todo setear los parametros de la agregacion
			ejecutivoCuentaVO.setCdAgente("CV1");
			ejecutivoCuentaVO.setCdPerson("37474");
			ejecutivoCuentaVO.setCdEstado("1");
			ejecutivoCuentaVO.setFeInicio("11/02/08");
			ejecutivoCuentaVO.setFeFin("11/02/08");
			ejecutivoCuentaVO.setCdTipRam("7");
			ejecutivoCuentaVO.setCdRamo("105");
			ejecutivoCuentaVO.setCdLinCta("1");
			ejecutivoCuentaVO.setSwNivelPosterior("1");
			ejecutivoCuentaVO.setCdGrupo("33");
			ejecutivoCuentaVO.setCdElemento("8");
				 
	    	 // invocar servicio para agregar datos a BD
	    	 this.ejecutivosCuentaManager.agregarGuardarEjecutivoCuenta(ejecutivoCuentaVO);
	    		
	    	//Recuperar el registro insertado	
	    //	 ejecutivoCuentaVO = ejecutivosCuentaManager.getEjecutivoCuenta("CV1506", "33965");
        
	        // Validar si se han guardado correctamente lo datos
            /*assertNotNull(ejecutivoCuentaVO);
            assertEquals("El valor de cdAgente debe ser CV1506 ",ejecutivoCuentaVO.getCdAgente(), "CV1506" );
            assertEquals("El valor de CdPerson debe ser  33965 ",ejecutivoCuentaVO.getCdPerson(), "33965" );
            assertEquals("El valor de CdEstado debe ser 1 ",ejecutivoCuentaVO.getCdAgente(), "1" );
            assertEquals("El valor de FeInicio debe ser  11/02/08 ",ejecutivoCuentaVO.getCdPerson(), "11/02/08" );
            assertEquals("El valor de FeFin debe ser 11/02/08 ",ejecutivoCuentaVO.getCdAgente(), "11/02/08" );
            assertEquals("El valor de CdTipRam debe ser  1 ",ejecutivoCuentaVO.getCdPerson(), "1" );
            assertEquals("El valor de CdRamo debe ser 911 ",ejecutivoCuentaVO.getCdAgente(), "911" );
            assertEquals("El valor de CdLinCta debe ser  2 ",ejecutivoCuentaVO.getCdPerson(), "2" );
            assertEquals("El valor de SwNivelPosterior debe ser 1 ",ejecutivoCuentaVO.getCdAgente(), "1" );
            assertEquals("El valor de CdGrupo debe ser  1 ",ejecutivoCuentaVO.getCdPerson(), "1" );
            assertEquals("El valor de CdElemento debe ser  2 ",ejecutivoCuentaVO.getCdElemento(), "2" );
            */
        }catch (Exception e) {
            fail("Falló en el agregar del Ejecutivos Cuenta!" + e.getMessage());
            e.printStackTrace();
		}
	}
 
	/*

	public void testGetEjecutivoCuenta() {
        try {
        	 EjecutivoCuentaVO ejecutivoCuentaVO = new EjecutivoCuentaVO();
            //obtener datos para cdconf de cliente = 4 el que use para el agregar
          
	    	 ejecutivoCuentaVO = ejecutivosCuentaManager.getEjecutivoCuenta("CV1", "8");
       
            
           //validar que se haya devuelto datos del get
            assertNotNull(ejecutivoCuentaVO);
           
            //todo hacer lo mismo con los otros campos leidos en el vo
           // assertEquals("El valor de ..debe ser CdCliente ",carritoComprasVO.getCdCliente(), "3" );
           // assertEquals("El valor de ..debe ser FgSiNo ",carritoComprasVO.getFgSiNo(), "Si" );
            
        }catch (Exception e) {
            fail("Falló en la lectura del registro de carrito de compras");
            e.printStackTrace();
        }
    }
    */
	
	/*
	
 public void testBorrarEjecutivosCuenta() throws Exception {
    	try {
    		this.ejecutivosCuentaManager.borrarEjecutivoCuenta("CV1506", "33965"); 
		} catch (Exception e) {
			e.printStackTrace();
			throw e; 
		}
    }

/*
	@SuppressWarnings("unchecked")
	public void testBuscarEjecutivosCuenta() throws Exception {
    	try {

    		PagedList salida = (PagedList) this.ejecutivosCuentaManager.buscarEjecutivosCuenta("JOSE VILLALON","ENRIQUE VAZQUEZ, INC.", "", 1, 5);
            List lista = salida.getItemsRangeList(); 
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
*/
	
	
}