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




import mx.com.aon.portal.model.FormatoOrdenesTrabajoVO;
import mx.com.aon.portal.service.FormatoOrdenesTrabajoManager;
import mx.com.aon.portal.service.PagedList;



public class FormatoOrdenesTrabajoTest extends AbstractTestCases {


	protected FormatoOrdenesTrabajoManager formatoOrdenesTrabajoManager;
	//protected CombosManager combosManager;


  public void testActualizarFormatoOrdenesTrabajoManager() throws Exception {
    	try {
    		FormatoOrdenesTrabajoVO formatoOrdenesTrabajoVO = new FormatoOrdenesTrabajoVO();
    		formatoOrdenesTrabajoVO.setCdFormatoOrden("2");
    		formatoOrdenesTrabajoVO.setDsFormatoOrden("nosed");
    		
    		//todo setear los parametros de la agregacion
    		formatoOrdenesTrabajoManager.guardarFormatoOrdenesTrabajo(formatoOrdenesTrabajoVO);
    		formatoOrdenesTrabajoVO = formatoOrdenesTrabajoManager.getFormatoOrdenesTrabajo("2");
			assertNotNull(formatoOrdenesTrabajoVO);
			 assertEquals("El valor de codigo debe ser ",formatoOrdenesTrabajoVO.getCdFormatoOrden(), "2" );
		     assertEquals("El valor de descripcion debe ser ",formatoOrdenesTrabajoVO.getDsFormatoOrden(), "nosed" );
            System.out.println("sali");

			logger.error("Todo va bien en Actualizar formatoOrdenesTrabajo");
		} catch (Exception e) {
			logger.error("Exception en Actualizar formatoOrdenesTrabajo");
			e.printStackTrace();		
			throw e;
		}
 
    }

/*
    public void testInsertarFormatoOrdenesTrabajoManager() throws Exception {
          try {
        	  FormatoOrdenesTrabajoVO formatoOrdenesTrabajoVO = new FormatoOrdenesTrabajoVO();
      		  formatoOrdenesTrabajoVO.setCdFormatoOrden("2");
      		  formatoOrdenesTrabajoVO.setDsFormatoOrden("nose02");
        	  
      		//todo setear los parametros de la agregacion
      		formatoOrdenesTrabajoManager.guardarFormatoOrdenesTrabajo(formatoOrdenesTrabajoVO);
      		formatoOrdenesTrabajoVO = formatoOrdenesTrabajoManager.getFormatoOrdenesTrabajo("2");
  			assertNotNull(formatoOrdenesTrabajoVO);
  			assertEquals("El valor de codigo debe ser ",formatoOrdenesTrabajoVO.getCdFormatoOrden(), "1" );
  		    assertEquals("El valor de descripcion debe ser ",formatoOrdenesTrabajoVO.getDsFormatoOrden(), "nose" );
            System.out.println("sali");
            
            logger.error("Todo va bien en Insertar formatoOrdenesTrabajo");
          } catch (Exception e) {
              logger.error("Exception Insertar formatoOrdenesTrabajo");
              e.printStackTrace();
              throw e;

          }

      }
*/	
	
/* 
	public void testBorrarAyudaCoberturas () throws Exception {
      try {
          //borro el registro ingresado previamente
    	  formatoOrdenesTrabajoManager.borrarFormatoOrdenesTrabajo("00");
      }catch (Exception e) {
          fail("Fallo en el borrar del registro");
          e.printStackTrace();
          throw e;
      }
  }
*/

/*

 public void testGetFormatoOrdenesTrabajoManager() throws Exception {
	try {
		String CdFormatoOrden_b = "1";
		
		FormatoOrdenesTrabajoVO formatoOrdenesTrabajoVO = formatoOrdenesTrabajoManager.getFormatoOrdenesTrabajo(CdFormatoOrden_b);
	
       assertNotNull(formatoOrdenesTrabajoVO);
        assertEquals("El valor de codigo debe ser ",formatoOrdenesTrabajoVO.getCdFormatoOrden(), "1" );
        assertEquals("El valor de descripcion debe ser ",formatoOrdenesTrabajoVO.getDsFormatoOrden(), "nose" );
		
	}catch (Exception e) {
        fail("Fallo en la lectura del registro");
        e.printStackTrace();
    }
}
*/


/*
 public void testBuscarFormatoOrdenesTrabajoManager() throws Exception {
		try {
			
			PagedList pagedList =  (PagedList)formatoOrdenesTrabajoManager.buscarFormatoOrdenesTrabajo("nose", 0, 1);
            assertNotNull(pagedList);
            List lista = pagedList.getItemsRangeList();
            for (int i=0; i<lista.size(); i++){
            	FormatoOrdenesTrabajoVO formatoOrdenesTrabajoVO = (FormatoOrdenesTrabajoVO)lista.get(i);
    			System.out.println("Codigo CD :"+formatoOrdenesTrabajoVO.getCdFormatoOrden()+"  Descripcion DS: " +formatoOrdenesTrabajoVO.getCdFormatoOrden());
            }
            System.out.println("sali");
            
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
 */
 
/*
	public void testCopiarConfigurarEstructuraManager() throws Exception {
		try {
    	 this.formatoOrdenesTrabajoManager.copiarFormatoOrdenesTrabajo("1"); 
    	 
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	*/

	
}