package mx.com.aon.catbo.web.tests;

import java.util.ArrayList;



import java.util.List;


import mx.com.aon.portal.service.PagedList;

import mx.com.aon.catbo.model.CasoVO;
import mx.com.aon.catbo.model.ElementoComboBoxVO;
import mx.com.aon.catbo.model.FaxesVO;
import mx.com.aon.catbo.model.FormatoOrdenVO;
import mx.com.aon.catbo.model.ListaEmisionCasoVO;
import mx.com.aon.catbo.service.AdministracionCasosManager;

import mx.com.aon.catbo.model.ArchivoVO;

public class ArchivosTest2 extends AbstractTestCases {
	protected mx.com.aon.catbo.service.ArchivosManager archivosManager;
	protected mx.com.aon.catbo.service.CombosManager2 combosManager2;



/*
 public void testBorrar () throws Exception {
     try {
         //borro el registro ingresado previamente
    	 archivosManager.borrarArchivos("BO12008092", "60", "2");//BO12008092	60	2
    	   System.out.println("sali");

     }catch (Exception e) {
         fail("Fallo en el borrar del registro de Archivo");
         e.printStackTrace();
         throw e;
     }
 }
 */
 
 /*
	public void testGuaradArchivos() throws Exception {
     try {
   	        	
     	 
     	archivosManager.guardaArchivos("BO120081061","778","3","Archivo de Entrada", "4",null,"SLIZARDI");
         logger.error("Todo va bien ");
         
     } catch (Exception e) {
         logger.error("Exception en Agregar archivos");
         e.printStackTrace();
         throw e;
     }
 }
 */
    // Prueba BO120081061	778

 /*//TEST OK!! 
   public void testObtieneAtributosFax () throws Exception {
    try {
    		PagedList pagedList =  (PagedList)archivosManager.obtieneAtributosFax("Formato", 0, 9);
 	        assertNotNull(pagedList);
 	        List lista = pagedList.getItemsRangeList();
 	        for (int i=0; i<lista.size(); i++){
 	        	FaxesVO faxesVO = (FaxesVO)lista.get(i);
 	        	assertNotNull(faxesVO);
 	        	System.out.println("El valor de CDATRIBU: "+ faxesVO.getCdatribu());
 		        System.out.println("El valor de DSATRIBU: "+ faxesVO.getDsAtribu() );
 		        System.out.println("El valor de CDTIPOAR: "+ faxesVO.getCdtipoar() );
 		        System.out.println("El valor de  DSARCHIVO: "+ faxesVO.getDsarchivo());
 	        }
 	        System.out.println("Saliendo");           
     }catch (Exception e) {
         fail("Fallo en el Obtener de Archivo");
         e.printStackTrace();
         throw e;
     }
   }
	*/
	
   /*//TEST OK
   public void testGetObtieneAtributosFax() throws Exception {
		try {
			FaxesVO faxesVO = archivosManager.getObtieneAtributosFax("2", "2");
						
	        //assertNotNull(faxesVO);
	        System.out.println("El valor de CDATRIBU: "+faxesVO.getCdatribu());
	        System.out.println("El valor de DSATRIBU: "+faxesVO.getDsAtribu());
	        System.out.println("El valor de CDTIPOAR: "+faxesVO.getCdtipoar());
	        System.out.println("El valor de  DSARCHIVO: "+faxesVO.getDsarchivo());
	        
		}catch (Exception e) {
	        fail("Fallo en la lectura del registro");
	        e.printStackTrace();
	    }
	}
	*/
	
   
	/*
    //TEST OK
   public void testGuardarAtributosFax() throws Exception {
	     try {
	   	        	
	     	 //con el ultimo en 0 anda, en 1 no anda!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	     	archivosManager.guardarAtributosFax("5", "14", "Aband calls", "N", "1", "7", "S", "210", "A");
	        logger.error("Todo va bien ");
	         
	     } catch (Exception e) {
	         logger.error("Exception en Agregar archivos");
	         e.printStackTrace();
	         throw e;
	     }
   }
  */
   /*
   public void testEditarGuardarAtributosFax() throws Exception {
	     try {
	   	        	
	     	 //con el ultimo en 0 anda, en 1 no anda!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	     	archivosManager.guardarAtributosFax("5", "14", "Aband calls", "N", "1", "7", "S", "210", "A");
	        logger.error("Todo va bien ");
	         
	     } catch (Exception e) {
	         logger.error("Exception en Agregar archivos");
	         e.printStackTrace();
	         throw e;
	     }
   }
   */
   
	/*   //SI LO BORRA PERO NO PUEDE PROCESAR EL MENSAJE DE ERROR ¿?¿?¿?
   public void testBorrarAtributosFax () throws Exception {
	     try {
	         //borro el registro ingresado previamente
	    	 archivosManager.borrarAtributosFax("5", "11");
	    	   System.out.println("sali");

	     }catch (Exception e) {
	         fail("Fallo en el borrar del registro de Archivo");
	         e.printStackTrace();
	         throw e;
	     }
	 }
   */
	
	public void testComboObtieneTablas() throws Exception {
		try {
			List combo = this.combosManager2.comboObtieneTablas();
						
	        //assertNotNull(faxesVO);
	        System.out.println("El valor de CDTABLA: "+ combo.toString());
	        
		        
		}catch (Exception e) {
	        fail("Fallo en la lectura del registro");
	        e.printStackTrace();
	    }
	}
	

 
 } //fin Test 


   