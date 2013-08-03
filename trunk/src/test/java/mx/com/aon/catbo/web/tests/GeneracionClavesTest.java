package mx.com.aon.catbo.web.tests;

import java.util.ArrayList;



import java.util.List;


import mx.com.aon.portal.service.PagedList;

import mx.com.aon.catbo.model.ElementoComboBoxVO;
import mx.com.aon.catbo.model.ClavesVO;
import mx.com.aon.catbo.service.GeneracionClavesManager;



public class GeneracionClavesTest extends AbstractTestCases {
	protected mx.com.aon.catbo.service.GeneracionClavesManager generacionClavesManager;
	protected mx.com.aon.catbo.service.CombosManager2 combosManager2;



/*
//TEST OK!! 
   public void testObtieneValores () throws Exception {
    try {
    	PagedList pagedList =  (PagedList)generacionClavesManager.obtieneValores("0", 0, 9);    		
    	   assertNotNull(pagedList);
 	        List lista = pagedList.getItemsRangeList();
 	        for (int i=0; i<lista.size(); i++){
 	        	ClavesVO clavesVO = (ClavesVO)lista.get(i);
 	        	assertNotNull(clavesVO);
 	        	System.out.println("El valor de IDGENERADOR: "+ clavesVO.getIdGenerador());
 		        System.out.println("El valor de DESCRIPCION: "+ clavesVO.getDescripcion() );
 		        System.out.println("El valor de IDPARAM: "+ clavesVO.getIdParam() );
 		        System.out.println("El valor de  VALOR: "+ clavesVO.getValor());
 	        }
 	        System.out.println("Saliendo");           
     }catch (Exception e) {
         fail("Fallo en el Obtener Valores");
         e.printStackTrace();
         throw e;
     }
   }
*/	
  
	/*
    //TEST OK
   public void testActualizarValores() throws Exception {
	     try {
	   	        	
	     	 //con el ultimo en 0 anda, en 1 no anda!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	    	 generacionClavesManager.actualizarValores("5","0");
	    	 logger.error("Todo va bien ");
	         
	     } catch (Exception e) {
	         logger.error("Exception en actualizas varlores");
	         e.printStackTrace();
	         throw e;
	     }
   }
  //*/
  
	/*
	// TEST OK 
	public void testComboObtieneFormato() throws Exception {
		try {
			List combo = this.combosManager2.comboObtieneAlgoritmos();
			for (int i=0; i<combo.size(); i++){
			ElementoComboBoxVO elementoComboBoxVO = (ElementoComboBoxVO)combo.get(i);
			assertNotNull(combo);
	        System.out.println("El valor de CODIGO: "+ elementoComboBoxVO.getCodigo());
	        System.out.println("El valor de DESCRIPCION: "+ elementoComboBoxVO.getDescripcion());
			}
	        
		        
		}catch (Exception e) {
	        fail("Fallo en la lectura del registro");
	        e.printStackTrace();
	    }
	}
	*/
 
 } //fin Test 


   