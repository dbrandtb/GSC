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






import mx.com.aon.portal.model.OrdenDeCompraVO;
import mx.com.aon.portal.service.OrdenesDeComprasManager;
import mx.com.aon.portal.service.PagedList;



public class OrdenesDeComprasTest extends AbstractTestCases {


	protected OrdenesDeComprasManager ordenesDeComprasManager;
	//protected CombosManager combosManager;


	
 /*
 
	public void testFinalizarOrdenesDeCompras () throws Exception {
      try {
    	  ordenesDeComprasManager.finalizarOrdenesDeCompras("1");
      }catch (Exception e) {
          fail("Fallo en el finalizar el registro");
          e.printStackTrace();
          throw e;
      }
  }

*/


 public void testBuscarOrdenesDeCompras() throws Exception {
		try {
			
			PagedList pagedList =  (PagedList)ordenesDeComprasManager.buscarOrdenesDeCompras("", "", "", 0, 10);
            assertNotNull(pagedList);
            List lista = pagedList.getItemsRangeList();
            for (int i=0; i<lista.size(); i++){
            	OrdenDeCompraVO ordenDeCompraVO = (OrdenDeCompraVO)lista.get(i);
    			System.out.println("Orden: "+ordenDeCompraVO.getNmOrden()+"  Fecha: " +ordenDeCompraVO.getFeInicio()+" Estado: "+ordenDeCompraVO.getCdEstado());
            }
            System.out.println("sali");           
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}	
 /*public void testObtenerOrdenesDeCompras() throws Exception {
		try {
			
			
		}*/
}