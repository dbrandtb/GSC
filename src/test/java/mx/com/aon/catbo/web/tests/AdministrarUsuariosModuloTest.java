package mx.com.aon.catbo.web.tests;

import java.util.ArrayList;



import java.util.List;


import mx.com.aon.catbo.service.AdministrarUsuariosModuloManager;
import mx.com.aon.portal.service.PagedList;

import mx.com.aon.catbo.model.CasoVO;
import mx.com.aon.catbo.model.FormatoOrdenVO;
import mx.com.aon.catbo.model.ListaEmisionCasoVO;
import mx.com.aon.catbo.service.AdministracionCasosManager;

import mx.com.aon.catbo.model.ResultadoGeneraCasoVO;

public class AdministrarUsuariosModuloTest extends AbstractTestCases {
	protected AdministrarUsuariosModuloManager administrarUsuariosModuloManager;

	/*
 public void testBorrar () throws Exception {
     try {
         //borro el registro ingresado previamente
    	 administrarUsuariosModuloManager.borrarUsuarios("1","CCRUZ");
    	   System.out.println("sali");

     }catch (Exception e) {
         fail("Fallo en el borrar del registro de de Caso");
         e.printStackTrace();
         throw e;
     }
 }
*/
/*
 public void testObtiene_Usuario() throws Exception {
			try {


				PagedList pagedList = (PagedList)administrarUsuariosModuloManager.ObtenerUsuarios("","",1,5);
				//assertNotNull(pagedList);

				//List lista = pagedList.getItemsRangeList();

		        /*for (int i=0; i<lista.size(); i++){
		        	CasoVO casoVO = (CasoVO)lista.get(i);
					//System.out.println("Descripcion: "+notificacionVO.getDsNotificacion()+"  Formato: " +notificacionVO.getDsFormatoOrden()+" Met Env: "+notificacionVO.getDsMetEnv());
		       // }
		       
		        
		       System.out.println("sali");




		           // List lista = pagedList.getItemsRangeList();

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		 }
	 */

	
	

 

 public void testObtiene_Usuario_Asigna() throws Exception {
		try {


			PagedList pagedList = (PagedList)administrarUsuariosModuloManager.obtenerUsuariosAsignar("",1,5);
			//assertNotNull(pagedList);

			//List lista = pagedList.getItemsRangeList();

	        /*for (int i=0; i<lista.size(); i++){
	        	CasoVO casoVO = (CasoVO)lista.get(i);
				//System.out.println("Descripcion: "+notificacionVO.getDsNotificacion()+"  Formato: " +notificacionVO.getDsFormatoOrden()+" Met Env: "+notificacionVO.getDsMetEnv());
	       // }
	       
	        /*/
	       System.out.println("sali");




	           // List lista = pagedList.getItemsRangeList();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	 }
 
 /*
 
 public void testGuardar() throws Exception {
		try {


			administrarUsuariosModuloManager.guardarUsuarios("", "");
			//assertNotNull(pagedList);

			//List lista = pagedList.getItemsRangeList();

	        /*for (int i=0; i<lista.size(); i++){
	        	CasoVO casoVO = (CasoVO)lista.get(i);
				//System.out.println("Descripcion: "+notificacionVO.getDsNotificacion()+"  Formato: " +notificacionVO.getDsFormatoOrden()+" Met Env: "+notificacionVO.getDsMetEnv());
	       // }
	       
	        
	       System.out.println("sali");




	           // List lista = pagedList.getItemsRangeList();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	 }
*/

 } //fin Test
