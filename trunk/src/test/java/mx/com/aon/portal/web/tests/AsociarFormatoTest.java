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





import mx.com.aon.portal.model.AsociarFormatoVO;
import mx.com.aon.portal.service.AsociarFormatosManager;
import mx.com.aon.portal.service.PagedList;



public class AsociarFormatoTest extends AbstractTestCases {


	protected AsociarFormatosManager asociarFormatosManager;
	//protected CombosManager combosManager;


/*	
 
	public void testBorrarAyudaCoberturas () throws Exception {
      try {
          //borro el registro ingresado previamente
    	  asociarFormatosManager.borrarAsociarFormatos("");
      }catch (Exception e) {
          fail("Fallo en el borrar del registro");
          e.printStackTrace();
          throw e;
      }
  }
*/

/*
 public void testBuscarFormatoOrdenesTrabajoManager() throws Exception {
		try {
			
			PagedList pagedList =  (PagedList)asociarFormatosManager.buscarAsociarFormatos("", "", "", "", "", 0, 10);
            assertNotNull(pagedList);
            List lista = pagedList.getItemsRangeList();
            for (int i=0; i<lista.size(); i++){
            	AsociarFormatoVO asociarFormatoVO = (AsociarFormatoVO)lista.get(i);
    			System.out.println("Proceso: "+asociarFormatoVO.getDsProceso()+"  Formato: " +asociarFormatoVO.getDsFormatoOrden()+" Cliente: "+asociarFormatoVO.getDsElemen()+"  Aseguradora: " +asociarFormatoVO.getDsUnieco()+" Producto: "+asociarFormatoVO.getDsRamo());
            }
            System.out.println("sali");
            
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
*/
	public void testGetModel()throws Exception{
		try{
			this.asociarFormatosManager.getModel("","","","","");
			}catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	}

	
}