package mx.com.aon.catbo.web.tests;

import mx.com.aon.catbo.service.GuionLlamadasManager;
import mx.com.aon.catbo.service.TareasCatBoManager;

public class TestGuionLlamadas {
	protected GuionLlamadasManager  guionLlamadasManager ;

	/*public void testObtenerTiempo()  {
			try {

            String idCaso = "BO100000100";
            String tiempo = procesoManager.obtenerTiempoCaso(idCaso);
            System.out.println("valor obtenido "+ tiempo);

			} catch (Exception e) {
				e.printStackTrace();1

			}

	 }*/ 
	 
	
	
	 public void testGuardarIdTarea() throws Exception {
			try {
				//String CdFormato_b = "1";
				
			guionLlamadasManager.guardarGuionLlamadas("9", "1","6244", "CAMBIOMAS", "001","Primero","1","1","A");
				//pv_cdunieco_i, pv_cdramo_i, pv_cdelemento_i, pv_cdproceso_i, pv_cdguion_i, pv_dsguion_i, pv_cdtipguion_i, pv_indinicial_i, pv_status_i	
			System.out.println("sali");
		       
				
			}catch (Exception e) {
				System.out.println("Exception Insertar formatosDocumentos");
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
