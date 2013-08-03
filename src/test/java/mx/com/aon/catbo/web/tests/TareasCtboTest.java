package mx.com.aon.catbo.web.tests;

import mx.com.aon.catbo.service.AdministracionCasosManager;
import mx.com.aon.catbo.service.ProcesoManager;
import mx.com.aon.catbo.model.ResultadoGeneraCasoVO;
import mx.com.aon.catbo.model.BackBoneResultVO;
import mx.com.aon.portal.service.PagedList;
import com.lowagie.text.List;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.catbo.service.TareasCatBoManager;
import mx.com.aon.catbo.model.TareaVO;



public class TareasCtboTest extends AbstractTestCases {
	protected TareasCatBoManager  tareasCatBoManager ;

	/*public void testObtenerTiempo()  {
			try {

            String idCaso = "BO100000100";
            String tiempo = procesoManager.obtenerTiempoCaso(idCaso);
            System.out.println("valor obtenido "+ tiempo);

			} catch (Exception e) {
				e.printStackTrace();

			}

	 }*/ 
	 
	
	
	 public void testGuardarIdTarea() throws Exception {
			try {
				//String CdFormato_b = "1";
				
				tareasCatBoManager.buscarTareasCatBoValidar("4");//.guardarIdTareaIdInstancia("BO300000200", "iuyytrrte4aju9oq33uur", "10003");	
			System.out.println("Cualquiera");
		       
				
			}catch (Exception e) {
		        fail("Fallo en la lectura del registro");
		        e.printStackTrace();
		    }
		}
	
	
	/*public void testGuardarEscalamiento() throws Exception {
		try {
			//String CdFormato_b = "1";
			
			procesoManager.guardarEscalamiento("BO300000200");	
		System.out.println("Cualquiera");
	       
			
		}catch (Exception e) {
	        fail("Fallo el guarada del registro");
	        e.printStackTrace();
	    }
	}
	
	*/
	
	/*public void testgetValidaNivelEscalamiento() throws Exception {
		try {
			//String CdFormato_b = "1";
			
			procesoManager.getvalidaNivelEscalamiento("BO300000200");
		System.out.println("Cualquiera");
	       			
		}catch (Exception e) {
	        fail("Fallo al guardar el registro");
	        e.printStackTrace();
	    }
	}
	
	
	*/
	
	/* public void testGetObtenerUsuarioProceso() throws Exception {
			try {
				//String CdFormato_b = "1";
				
				//ResultadoGeneraCasoVO procesoUsuaarioVO = procesoManager.obtenerUsuarioProceso("BO100000100");
			System.out.println("Cualquiera");
		       
				
			}catch (Exception e) {
		        fail("Fallo en la lectura del registro");
		        e.printStackTrace();
		    }*/
		
	
	 /*
	 public void testGuardarIdTarea() throws Exception {
			try {
				//String CdFormato_b = "1";
				
				procesoManager.guardarIdTareaIdInstancia("BO300000200", "iuyytrrte4aju9oq33uur", "10003");	
			System.out.println("Cualquiera");
		       
				
			}catch (Exception e) {
		        fail("Fallo en la lectura del registro");
		        e.printStackTrace();
		    }
		}*/
	
	/*public void testGetObtenerUsuarioProceso() throws Exception {
	try {
		//String CdFormato_b = "1";
		PagedList resultado =  (PagedList)procesoManager.obtenerUsuarioProceso("BO1219",1,3);
	System.out.println("Cualquiera");
       
		
	}catch (Exception e) {
        fail("Fallo en la lectura del registro");
        e.printStackTrace();
    }
}

*/
		
	/*
   public void testObtenerParametros() {
	try {

		PagedList pagedList = (PagedList)procesoManager.obtenerParametros("","", 1, 3);
		

	} catch (Exception e) {
		e.printStackTrace();

	  }
  }*/
	/*public void testObtenerTaskId()  {
		try {

        //String idCaso = "BO120080935";
        String task = procesoManager.obtieneTaskId("BO120080935");
        System.out.println("valor obtenido "+ task);

		} catch (Exception e) {
			e.printStackTrace();

		}	 
	
	
	}*/
	/*  public void testObtenerResponsablesEnvio() {
			try {

				Object obj = procesoManager.obtenerResponsablesEnvio();
				System.out.println("listo ");

				

			} catch (Exception e) {
				e.printStackTrace();

			  }
		  }*/
	
	/*public void testObtenerMailCliente() {
		try {

			PagedList pagedList = (PagedList)procesoManager.obtenerMailCliente("36621", 1, 3);
			

		} catch (Exception e) {
			e.printStackTrace();

		  }
	
	}*/
	/*
	public void testObtenerMailResponsables() {
		try {

			PagedList pagedList = (PagedList)procesoManager.obtenerMailResponsables("BDIAZ", 1, 3);
			

		} catch (Exception e) {
			e.printStackTrace();

		  }
		
	}*/
	
 }
