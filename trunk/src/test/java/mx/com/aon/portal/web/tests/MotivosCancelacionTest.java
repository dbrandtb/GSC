/**
 * 
 */


/**
 * @author CIMA_USR
 *
 */

package mx.com.aon.portal.web.tests;

//import mx.com.aon.portal.service.CombosManager;
import java.util.ArrayList;
import java.util.List;


import mx.com.aon.portal.model.MotivoCancelacionVO;
import mx.com.aon.portal.model.RequisitoCancelacionVO;
import mx.com.aon.portal.service.MotivosCancelacionManager;
import mx.com.aon.portal.service.PagedList;



public class MotivosCancelacionTest extends AbstractTestCases {


	protected MotivosCancelacionManager motivosCancelacionManager;
	//protected CombosManager combosManager;  //del combo sino
/*

  public void testActualizarMotivosCancelacionManager() throws Exception {
    	try {
    		
            System.out.println("sali");

			logger.error("Todo va bien en Actualizar formatoOrdenesTrabajo");
		} catch (Exception e) {
			logger.error("Exception en Actualizar formatoOrdenesTrabajo");
			e.printStackTrace();		
			throw e;
		}
 
    }


    public void testInsertarMotivosCancelacionManager() throws Exception {
          try {
        	  
        	  MotivoCancelacionVO mtmrVO = new MotivoCancelacionVO();
        	  mtmrVO.setCdRazon(null);
        	  mtmrVO.setDsRazon("Es una Prueba Mas ");
        	  mtmrVO.setSwReInst("S");
        	  mtmrVO.setSwVerPag("N");
        	  
        	  RequisitoCancelacionVO rVO=new RequisitoCancelacionVO();
        	  rVO.setCdRazon(mtmrVO.getCdRazon());
        	  rVO.setCdAdvert(null) ;
        	  rVO.setDsAdvert("Porque no se"); 
      		
        	  List<RequisitoCancelacionVO> lista=new ArrayList();
        	  lista.add(rVO);
        	  
        	  mtmrVO.setRequisitoCancelacionVOs(lista);
        	  
        	  motivosCancelacionManager.guardarMotivosCancelacion(mtmrVO); 
        	  

            System.out.println("sali");
            
            logger.error("Todo va bien en Insertar formatoOrdenesTrabajo");
          } catch (Exception e) {
              logger.error("Exception Insertar formatoOrdenesTrabajo");
              e.printStackTrace();
              throw e;

          }

      }
*/

	public void testGetModel()throws Exception{
		try{
			this.motivosCancelacionManager.getModel("", "E");
			}catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	}
	
	
	/*


	public void testBorrarMotivosCancelacionManager () throws Exception {
      try {
          //borro el registro ingresado previamente
    	  motivosCancelacionManager.borrarMotivosCancelacion("53");
      }catch (Exception e) {
          fail("Fallo en el borrar del registro de motivos");
          e.printStackTrace();
          throw e;
      }
  }

	public void testBorrarRequisitosCancelacionManager () throws Exception {
	      try {
	          //borro el registro ingresado previamente
	    	  motivosCancelacionManager.borrarRequisitoCancelacion("45","1");
	      }catch (Exception e) {
	          fail("Fallo en el borrar del registro de requisitos");
	          e.printStackTrace();
	          throw e;
	      }
	  }

 public void testBuscarMotivosCancelacionManager() throws Exception {
		try {
			
			PagedList pagedList =  (PagedList)motivosCancelacionManager.buscarMotivosCancelacion("","", 0, 30);  
            assertNotNull(pagedList);
            List lista = pagedList.getItemsRangeList();
            for (int i=0; i<lista.size(); i++){
            	MotivoCancelacionVO motivoCancelacionVO = (MotivoCancelacionVO)lista.get(i);
    			System.out.println("Codigo CD :"+motivoCancelacionVO.getCdRazon()+"  Descripcion DS: "+motivoCancelacionVO.getDsRazon()+" Codigo VERFEC :"+motivoCancelacionVO.getSwReInst()+"  Descripcion VERPAG: " +motivoCancelacionVO.getSwVerPag());
            }
            System.out.println("sali");           
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	 public void testGetsRequisitosCancelacionManager() throws Exception {
			try {
				PagedList pagedList =  (PagedList)motivosCancelacionManager.getRequisitoCancelacion("26", 0, 10);
	            assertNotNull(pagedList);
	            List lista = pagedList.getItemsRangeList();
	            for (int i=0; i<lista.size(); i++){
	            	RequisitoCancelacionVO requisitoCancelacionVO = (RequisitoCancelacionVO)lista.get(i);
	    			System.out.println("Codigo CD :"+requisitoCancelacionVO.getCdRazon()+"  Codigo CDADVERT: "+requisitoCancelacionVO.getCdAdvert()+"  Descripcion DSADVERT :"+requisitoCancelacionVO.getDsAdvert());
	            }
	            System.out.println("sali");           
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
	public void testGetMotivosCancelacionManager() throws Exception {
		try {
			String cdRazon_b = "26";
			
			MotivoCancelacionVO motivoCancelacionVO = motivosCancelacionManager.getMotivosCancelacion(cdRazon_b);
		
	       assertNotNull(motivoCancelacionVO);
	        assertEquals("El valor de codigo debe ser ", motivoCancelacionVO.getCdRazon(), "26" );
	        assertEquals("El valor de descripcion debe ser ",motivoCancelacionVO.getDsRazon(), "A peticion de financiera" );
	        assertEquals("El valor de SWREINST debe ser ", motivoCancelacionVO.getSwReInst(), "Si" );
	        assertEquals("El valor de SWVERPAG debe ser ",motivoCancelacionVO.getSwVerPag(), "No" );
			
		}catch (Exception e) {
	        fail("Fallo en la lectura del registro");
	        e.printStackTrace();
	    }
	}

*/

	
}