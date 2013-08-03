package mx.com.aon.portal.web.tests;

import mx.com.aon.portal.model.DescuentoProductoVO;
import mx.com.aon.portal.model.DescuentoVO;
import mx.com.aon.portal.model.RazonesCancelacionProductoVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.RazonesCancelacionProductoManager;
import mx.com.aon.portal.util.WrapperResultados;

public class RazonesCancelacionProductoTest extends AbstractTestCases{
	
	protected RazonesCancelacionProductoManager razonesCancelacionProductoManager;
	
  //*****************************************************************************
	
	/*public void testObtieneRazonesCancelacionProducto () throws Exception {
		try {
			razonesCancelacionProductoManager.obtenerRazonesCancelacionProducto(null, null, null, 0, 10);
            System.out.println("**********PASE EL OBTENER RAZON***************");
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	*/
	/*public void testBorrarRazonesCancelacion () throws Exception {
		try {
			razonesCancelacionProductoManager.borrarRazonesCancelacionProducto("1", "1");
            System.out.println("**********PASE EL BORRAR RAZON***************");
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}*/

	
	public void testGuardarRazonesCancelacion () throws Exception {
	try {
		razonesCancelacionProductoManager.guardarConfiguracionRazonCancelacionProducto("553", "21", "162");
        System.out.println("**********PASE EL BORRAR RAZON***************");
	}catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	}
}
