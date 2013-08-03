package mx.com.aon.catbo.web.tests;

import java.util.ArrayList;



import java.util.List;


import mx.com.aon.portal.service.PagedList;

import mx.com.aon.catbo.model.CasoVO;
import mx.com.aon.catbo.model.FormatoOrdenVO;
import mx.com.aon.catbo.model.ListaEmisionCasoVO;
import mx.com.aon.catbo.service.AdministracionCasosManager;

import mx.com.aon.catbo.model.ArchivoVO;

public class ArchivosTest extends AbstractTestCases {
	protected mx.com.aon.catbo.service.ArchivosManager archivosManager;



 public void testBorrarAtributosArchivos () throws Exception {
     try {
         //borro el registro ingresado previamente
    	 
//    	   archivosManager.borraratributosArchivos("1","3 ");  
    	   System.out.println("sali");

     }catch (Exception e) {
         fail("Fallo en el borrar del registro de Archivo");
         e.printStackTrace();
         throw e;
     }
 }

/*	
 @SuppressWarnings("unchecked")
 public void testBuscarAtributosArchivos() throws Exception {
   try {
    
    PagedList pagedList =  (PagedList) archivosManager.BuscarAtributosArchivos("Formato Conmutador Terranova (SUM_VDN)", 0, 10); // Formato Conmutador Terranova (SUM_VDN
    
    //assertNotNull(pagedList);
             List lista = pagedList.getItemsRangeList();
             for (int i=0; i<lista.size(); i++){
              ArchivoVO archivoVO = (ArchivoVO)lista.get(i);
        System.out.println("CDTIPOAR:"+ archivoVO.getCdTipoar()+ "DSARCHIVO"+ archivoVO.getDsArchivo());
             }
             System.out.println("sali");           
    
   } catch (Exception e) {
    e.printStackTrace();
    throw e;
   }
  }*/
 /*

 public void testObtenerRegAtributos() throws Exception {
	   try {
	    
	    
	    //assertNotNull(pagedList);
	    
	    
		ArchivoVO archivoVO = archivosManager.ObtenerRegAtributosArchivos(pv_cdtipoar_i, pv_cdatribu_i);
		
        assertNotNull(archivoVO);
        assertEquals("pv_cdtipoar_i ",archivoVO.getCdTipoar("");
        assertEquals("pv_cdatribu_i ",archivoVO.getCdAtribu(""));
        
        
	
	}catch (Exception e) {
        fail("Fallo en la lectura del registro");
        e.printStackTrace();
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
 
    // Prueba BO120081061	778

     
	
	//obtenerNumerosCasos*/
 
 } //fin Test 
