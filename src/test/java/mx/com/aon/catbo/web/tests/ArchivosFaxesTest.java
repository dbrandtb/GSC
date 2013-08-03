package mx.com.aon.catbo.web.tests;

import mx.com.aon.catbo.service.AdministracionCasosManager;
import mx.com.aon.catbo.service.ArchivosFaxesManager;
import mx.com.aon.portal.service.PagedList;

public class ArchivosFaxesTest extends AbstractTestCases {
	protected ArchivosFaxesManager archivosFaxesManager;


/* public void testSettings() throws Exception {
	try {
		PagedList pagedList = (PagedList)archivosFaxesManager.obtenerFaxes("", "", "", "", 0, 10);
		//assertNotNull(pagedList);
		
		//List lista = pagedList.getItemsRangeList();
        
        /*for (int i=0; i<lista.size(); i++){
        	CasoVO casoVO = (CasoVO)lista.get(i);
			//System.out.println("Descripcion: "+notificacionVO.getDsNotificacion()+"  Formato: " +notificacionVO.getDsFormatoOrden()+" Met Env: "+notificacionVO.getDsMetEnv());
        }*/
       // System.out.println("sali"); 
		
		
			
           // List lista = pagedList.getItemsRangeList();
            
	/*} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
 }*/
 
 
 public void testSettings() throws Exception {
		try {
			PagedList pagedList = (PagedList)archivosFaxesManager.obtenerFaxes("Formato Conmutador Terranova", "BO120081106", "2", "1", 0, 5);
			//assertNotNull(pagedList);//Formato Conmutador Terranova (SUM_VDN)	2	1	BO120081106
			//BO1200810103 	2
			//List lista = pagedList.getItemsRangeList();
	        
	        /*for (int i=0; i<lista.size(); i++){
	        	CasoVO casoVO = (CasoVO)lista.get(i);
				//System.out.println("Descripcion: "+notificacionVO.getDsNotificacion()+"  Formato: " +notificacionVO.getDsFormatoOrden()+" Met Env: "+notificacionVO.getDsMetEnv());
	        }*/
	        System.out.println("sali"); 
			
			
				
	           // List lista = pagedList.getItemsRangeList();
	            
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	 }

}
