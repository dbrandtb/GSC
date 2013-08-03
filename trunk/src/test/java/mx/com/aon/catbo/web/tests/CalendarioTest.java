package mx.com.aon.catbo.web.tests;

import java.util.List;

import mx.com.aon.catbo.model.CalendarioVO;
import mx.com.aon.catbo.service.CalendarioManager;
import mx.com.aon.portal.service.PagedList;

public class CalendarioTest extends AbstractTestCases {
	protected CalendarioManager calendarioManager;

    /*public void testSettings() throws Exception {
       try {
           System.out.println("sntre");
           System.out.println("sali");

       } catch (Exception e) {
           e.printStackTrace();
           throw e;
       }
    }
*/
    
/*	public void testBuscarCalendario() throws Exception {
		try {
			
			PagedList pagedList =  (PagedList)calendarioManager.buscarCalendario("AR", "2008", "12",0,10);
	        assertNotNull(pagedList);
	        List lista = pagedList.getItemsRangeList();
	        for (int i=0; i<lista.size(); i++){
	        	CalendarioVO calendarioVO = (CalendarioVO)lista.get(i);
				System.out.println("Descripcion: "+calendarioVO.getDescripcionDia());
	        }
	        System.out.println("sali");           
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}*/
	
/*	 public void testBorrarCalendario() throws Exception {
	     try {
	         //borro el registro ingresado previamente
	    	 calendarioManager.borrarCalendario("AR","2008","10","11");
	    	   System.out.println("sali");

	     }catch (Exception e) {
	         fail("Fallo en el borrar del registro de de Caso");
	         e.printStackTrace();
	         throw e;
	     }
	 }*/
	
 /*   public void testGetCalendar() throws Exception {
 		try {
 			//String CdFormato_b = "1";

 			CalendarioVO calendarioVO =calendarioManager.getDiaMes("AR", "2008", "10", "12");

 	        /*assertNotNull(calendarioVO);
 	        assertEquals("El valor de pais debe ser ",calendarioVO.getCodigoPais());
 	        assertEquals("El valor de  año debe ser ",calendarioVO.getAnio(),"retraso" );
 	        assertEquals("El valor de mes ",calendarioVO.getMes());
 	        assertEquals("El valor del dia  ",calendarioVO.getDia() );*/

 /*		}catch (Exception e) {
 	        fail("Fallo en la lectura del registro");
 	        e.printStackTrace();
 	    }
 	}*/
    
    public void testAgregarNumeracionCasos() throws Exception {
    try {
    	CalendarioVO calendarioVO = new CalendarioVO();
  	  
    	calendarioVO.setCodigoPais("AR");
    	calendarioVO.setAnio("2008");
    	calendarioVO.setMes("10");
    	calendarioVO.setDia("11");
    	calendarioVO.setDescripcionDia("");
        
    	calendarioManager.guardarCalendario(calendarioVO);				 
        logger.error("Todo va bien en agregar numeracion casos");
    } catch (Exception e) {
        logger.error("Exception en Agregar numeracion casos");
        e.printStackTrace();
        throw e;
    }
}
    
    

}
