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





import mx.com.aon.portal.model.RequisitoRehabilitacionVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.RequisitosRehabilitacionManager;



public class RequisitosRehabilitacionTest extends AbstractTestCases {


	protected RequisitosRehabilitacionManager requisitosRehabilitacionManager;
	
	//protected CombosManager combosManager;

/*
  public void testActualizarRequisitosRehabilitacion() throws Exception {
    	try {
    		
    		
    		RequisitoRehabilitacionVO requisitoRehabilitacionVO = new RequisitoRehabilitacionVO();
    		requisitoRehabilitacionVO.setCdRequisito("");
    		requisitoRehabilitacionVO.setDsRequisito("seguro");
    		requisitoRehabilitacionVO.setCdElemento("998");
    		requisitoRehabilitacionVO.setCdUnieco("14");
    		requisitoRehabilitacionVO.setCdRamo("11");
    		requisitoRehabilitacionVO.setCdPerson("1024");
    		requisitoRehabilitacionVO.setCdDocXcta("800");
    		
    		requisitosRehabilitacionManager.agregarGuardarRequisitoRehabilitacion(requisitoRehabilitacionVO);
 
 
		    System.out.println("sali");

			logger.error("Todo va bien en Actualizar requisitos de Rehabilitacion");
		} catch (Exception e) {
			logger.error("Exception en Actualizar requisitos de Rehabilitacion");
			e.printStackTrace();		
			throw e;
		}
 
    }*/

/*
    public void testInsertarRequisitosRehabilitacion() throws Exception {
          try {
        	  FormatoOrdenesTrabajoVO formatoOrdenesTrabajoVO = new FormatoOrdenesTrabajoVO();
      		  formatoOrdenesTrabajoVO.setCdFormatoOrden("2");
      		  formatoOrdenesTrabajoVO.setDsFormatoOrden("nose02");
        	  
      		//todo setear los parametros de la agregacion
      		formatoOrdenesTrabajoManager.guardarFormatoOrdenesTrabajo(formatoOrdenesTrabajoVO);
      		formatoOrdenesTrabajoVO = formatoOrdenesTrabajoManager.getFormatoOrdenesTrabajo("2");

			assertNotNull(requisitoRehabilitacionVO);
			//assertEquals("El valor de cdrequisito debe ser ",requisitoRehabilitacionVO.getCdRequisito(), "2" );
		    assertEquals("El valor de dsrequisito debe ser ",requisitoRehabilitacionVO.getDsRequisito(), "no funciona" );
			assertEquals("El valor de cdelemento debe ser ",requisitoRehabilitacionVO.getCdElemento(), "125" );
		    assertEquals("El valor de cdunieco debe ser ",requisitoRehabilitacionVO.getCdUnieco(), "6" );
			assertEquals("El valor de cdramo debe ser ",requisitoRehabilitacionVO.getCdRamo(), "3" );
		    assertEquals("El valor de cdperson debe ser ",requisitoRehabilitacionVO.getCdPerson(), "165" );
			assertEquals("El valor de cddocxcta debe ser ",requisitoRehabilitacionVO.getCdDocXcta(), "300" );           System.out.println("sali");
            
            logger.error("Todo va bien en Insertar formatoOrdenesTrabajo");
          } catch (Exception e) {
              logger.error("Exception Insertar formatoOrdenesTrabajo");
              e.printStackTrace();
              throw e;

          }

      }
*/	
/*	

	public void testBorrarRequisitosRehabilitacion () throws Exception {
      try {
          //borro el registro ingresado previamente
    	  requisitosRehabilitacionManager.borrarRequisitoRehabilitacion("5","47","10","5001","800");
      }catch (Exception e) {
          fail("Fallo en el borrar del registro");
          e.printStackTrace();
          throw e;
      }
  }
*/


 public void testGetRequisitosRehabilitacion() throws Exception {
	try {
		String CdRequisito_b = "2";
		
		RequisitoRehabilitacionVO requisitoRehabilitacionVO = requisitosRehabilitacionManager.getRequisitoRehabilitacion(CdRequisito_b);
	
       //assertNotNull(requisitoRehabilitacionVO);
       //assertEquals("El valor de cdrequisito debe ser ",requisitoRehabilitacionVO.getCdRequisito(), "2" );
	   //assertEquals("El valor de cdperson debe ser ",requisitoRehabilitacionVO.getCdPerson(), "" );
	   //assertEquals("El valor de cdunieco debe ser ",requisitoRehabilitacionVO.getCdUnieco(), "44" );
	   //assertEquals("El valor de cdramo debe ser ",requisitoRehabilitacionVO.getCdRamo(), "777" );
	  // assertEquals("El valor de dsrequisito debe ser ",requisitoRehabilitacionVO.getDsRequisito(), "tampoco" );
	   //assertEquals("El valor de cdelemento debe ser ",requisitoRehabilitacionVO.getCdElemento(), "5017" );
	   //assertEquals("El valor de cddocxcta debe ser ",requisitoRehabilitacionVO.getCdDocXcta(), "400" ); 
		
	}catch (Exception e) {
        fail("Fallo en la lectura del registro");
        e.printStackTrace();
    }
}


/*
 public void testBuscarRequisitosRehabilitacion() throws Exception {
		try {
			
			PagedList pagedList =  (PagedList)requisitosRehabilitacionManager.buscarRequisitosRehabilitacion("", "", "", 0, 10);
            /*assertNotNull(pagedList);
            List lista = pagedList.getItemsRangeList();
            for (int i=0; i<lista.size(); i++){
            	RequisitoRehabilitacionVO requisitoRehabilitacionVO = (RequisitoRehabilitacionVO)lista.get(i);
    			System.out.println("cdrequisito :"+requisitoRehabilitacionVO.getCdElemento()+"  cdpreson: " +requisitoRehabilitacionVO.getCdPerson()+" cdunieco :"+requisitoRehabilitacionVO.getCdUnieco() +"  dsunieco: " +requisitoRehabilitacionVO.getDsUnieco()+" cdramo :"+requisitoRehabilitacionVO.getCdRamo()+"  dsrano: " +requisitoRehabilitacionVO.getDsRamo()+" dsrequisito :"+requisitoRehabilitacionVO.getDsRequisito()+"  cdelemento: " +requisitoRehabilitacionVO.getCdElemento()+" dselemen :"+requisitoRehabilitacionVO.getDsElemen()+"  cddocxcta: " +requisitoRehabilitacionVO.getCdDocXcta());
            }
         
            System.out.println("sali");
            
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

 
 public void testGetModel()throws Exception{
		try{
			this.requisitosRehabilitacionManager.getModel("", "","");
			}catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}

*/

	
}