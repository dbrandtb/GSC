package mx.com.aon.catbo.web.tests;

import com.lowagie.text.List;
import mx.com.aon.catbo.service.MatrizAsignacionManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.catbo.model.BackBoneResultVO;
import mx.com.aon.catbo.model.MatrizAsignacionVO;
import mx.com.aon.catbo.model.ResponsablesVO;
import mx.com.aon.catbo.model.TiemposVO;

public class MatrizAsignacionTest extends AbstractTestCases {
	protected MatrizAsignacionManager matrizAsignacionManager;

   /* public void testSettings() throws Exception {
       try {
           System.out.println("sntre");
           System.out.println("sali");

       } catch (Exception e) {
           e.printStackTrace();
           throw e;
       }
    }*/
	

		
  /* public void testborraMatriz() throws Exception {
	     try {
	         //borro el registro ingresado previamente
	    	 matrizAsignacionManager.borrarMatriz("2");
	    	   System.out.println("sali"); 
	    	 
	     }catch (Exception e) {
	         fail("Fallo en el borrar del registro de Matriz");
	         e.printStackTrace();
	         throw e;
	     }   
	 }*/

   
      /* public void testSettings() throws Exception {
		try {
			PagedList pagedList = (PagedList)matrizAsignacionManager.obtieneNivAtencion("", 0, 10);
			//assertNotNull(pagedList);
			
			//List lista = pagedList.getItemsRangeList();
	        
	        /*for (int i=0; i<lista.size(); i++){
	        	CasoVO casoVO = (CasoVO)lista.get(i);
				//System.out.println("Descripcion: "+notificacionVO.getDsNotificacion()+"  Formato: " +notificacionVO.getDsFormatoOrden()+" Met Env: "+notificacionVO.getDsMetEnv());
	        }
	        System.out.println("sali"); 
			
			
				
	           // List lista = pagedList.getItemsRangeList();
	         
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	
       }*/
       

   //public void testSettings() throws Exception {
	//	try {
			//PagedList pagedList = (PagedList)matrizAsignacionManager.buscarMatrices("PROCESO PARA TARIFICACION DE CESIONES", "PRUEBAS_2 COPIA 2 COPIA 3", "ZONA SUROESTE NORTE", "OFICINA MATRIZ", "VIDA OTRO", 1, 3 );			//assertNotNull(pagedList);
			
			//PagedList pagedList = (PagedList)matrizAsignacionManager.buscarMatrices("", "", "", "", "", 1, 3 );			//assertNotNull(pagedList);
			
			//List lista = pagedList.getItemsRangeList();
	        
	        /*for (int i=0; i<lista.size(); i++){
	        	CasoVO casoVO = (CasoVO)lista.get(i);
				//System.out.println("Descripcion: "+notificacionVO.getDsNotificacion()+"  Formato: " +notificacionVO.getDsFormatoOrden()+" Met Env: "+notificacionVO.getDsMetEnv());
	        }
	        System.out.println("sali"); 
			
			
				
	           // List lista = pagedList.getItemsRangeList();
	         */
		//} catch (Exception e) {
//			e.printStackTrace();
	//		throw e;
		//}
	
    //}
	
	
	
  /*
	public void testBorrarMatriz () throws Exception {
	      try {
	          //borro el registro ingresado previamente
	    	  matrizAsignacionManager.borrarMatriz("4");
	      }catch (Exception e) {
	          fail("Fallo en el borrar del registro de motivos");
	          e.printStackTrace();
	          throw e;
	      }
	  }  
	
	
	 public void testGetMatrizAsignacion() throws Exception {
			try {
				//String CdFormato_b = "1";
				
				MatrizAsignacionVO notificacionVO = matrizAsignacionManager.getMatrizAsignacion("1");
			System.out.println("Cualquiera");
		       
				
			}catch (Exception e) {
		        fail("Fallo en la lectura del registro");
		        e.printStackTrace();
		    }
		}
	
*/
	   public void testObtieneReponsables() throws Exception {
			try {
				
				PagedList pagedList = (PagedList)matrizAsignacionManager.obtieneResponsables("7", "1", 1, 30);		//assertNotNull(pagedList);
				
				//PagedList pagedList = (PagedList)matrizAsignacionManager.obtieneNivAtencion("1", 1, 3);
				
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
	
	 /* public void testObtieneNivAtencion() throws Exception {
			try {
				
				PagedList pagedList = (PagedList)matrizAsignacionManager.obtieneNivAtencion("1", 1, 3);		//assertNotNull(pagedList);
				
				//List lista = pagedList.getItemsRangeList();
		        
		        /*for (int i=0; i<lista.size(); i++){
		        	CasoVO casoVO = (CasoVO)lista.get(i);
					//System.out.println("Descripcion: "+notificacionVO.getDsNotificacion()+"  Formato: " +notificacionVO.getDsFormatoOrden()+" Met Env: "+notificacionVO.getDsMetEnv());
		        }*/
				
		        /*System.out.println("sali"); 
				
				
					
		           // List lista = pagedList.getItemsRangeList();
		         
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		
	    }*/
	
/*
	 public void testGetMatrizAsignacion() throws Exception {
			try {
				//String CdFormato_b = "1";
				
			TiemposVO notificacionVO = matrizAsignacionManager.getTiempoResolucion("2","1");
			System.out.println("Cualquiera");
		       
				
			}catch (Exception e) {
		        fail("Fallo en la lectura del registro");
		        e.printStackTrace();
		    }
		}
	*/
	
	 /*public void testInsertarNotificaciones() throws Exception {
	        try {
	        	MatrizAsignacionVO notificacionVO = new MatrizAsignacionVO();
	        	notificacionVO.setcdmatriz("");
	        	notificacionVO.setCdunieco("42"); 
	        	notificacionVO.setCdramo("2");
	        	notificacionVO.setCdelemento("5227");
	        	notificacionVO.setCdproceso("1");
	        	notificacionVO.setCdformatoorden("10");
	    		//todo setear los parametros de la agregacion
	        	BackBoneResultVO backBoneResultVO = new BackBoneResultVO();
	        	
	        	backBoneResultVO=matrizAsignacionManager.guardarMatriz(notificacionVO);
	        	
	        	System.out.println("cdMatriz = " + backBoneResultVO.getOutParam());
	        } catch (Exception e) {
	            logger.error("Exception Insertar formatosDocumentos");
	            e.printStackTrace();
	            throw e;
	        }
	
       
	 }*/
	   
	   
	//VERLO
	  /*public void testBorrarResponsablesMatriz () throws Exception {
		      try {
		          //borro el registro ingresado previamente
		    	  matrizAsignacionManager.borrarResponsablesMatriz("3","1","NORTIZ");
		      }catch (Exception e) {
		          fail("Fallo en el borrar del registro de motivos");
		          e.printStackTrace();
		          throw e;
		      }
		  }  */
	   
	  /* public void testBorrarTiemposMatriz () throws Exception {
		      try {
		          //borro el registro ingresado previamente
		    	  matrizAsignacionManager.borrarTiemposMatriz("3", "2");
		    	  }catch (Exception e) {
		          fail("Fallo en el borrar Tiempos Matriz");
		          e.printStackTrace();
		          throw e;
		      }
		  }  
	
	/*public void testGuaradarResponsables() throws Exception {
        try {
        	ResponsablesVO responsablesVO = new ResponsablesVO();
        	responsablesVO.setCdmatriz("3");
        	responsablesVO.setCdnivatn("1");
        	responsablesVO.setCdrolmat("AP");
        	responsablesVO.setCdusuari("NORTIZ");
        	responsablesVO.setEmail("nn@hotmail.com");
        	responsablesVO.setStatus("1");
        	matrizAsignacionManager.guardarResponsables(responsablesVO);
        	        	
        	/*PagedList pagedList =  (PagedList)notificacionesManager.buscarNotificaciones("", "", "", 0, 10);
            assertNotNull(pagedList);
            List lista = pagedList.getItemsRangeList();
            for (int i=0; i<lista.size(); i++){
            	NotificacionVO notificacionVOut = (NotificacionVO)lista.get(i);
    			System.out.println("Descripcion: "+notificacionVOut.getDsNotificacion()+"  Formato: " +notificacionVOut.getDsFormatoOrden()+" Met Env: "+notificacionVOut.getDsMetEnv()+ " ");
            }*/
       /* } catch (Exception e) {
            logger.error("Exception Insertar Responsables");
            e.printStackTrace();
            throw e;
        }*/

        
        

    	/*public void testGuaradarTiempos() throws Exception {
            try {
            	TiemposVO tiemposVO = new TiemposVO();
            	tiemposVO.setCdmatriz("3");
            	tiemposVO.setCdnivatn("3");
            	tiemposVO.setTresolucion("40");
            	tiemposVO.setTresunidad("H");
            	tiemposVO.setTalarma("4");
            	tiemposVO.setTalaunidad("H");
            	tiemposVO.setTescalamiento("5");
            	tiemposVO.setTescaunidad("H");
            	          	
            	
            	matrizAsignacionManager.guardarTiempos(tiemposVO);
            	        	
            	/*PagedList pagedList =  (PagedList)notificacionesManager.buscarNotificaciones("", "", "", 0, 10);
                assertNotNull(pagedList);
                List lista = pagedList.getItemsRangeList();
                for (int i=0; i<lista.size(); i++){
                	NotificacionVO notificacionVOut = (NotificacionVO)lista.get(i);
        			System.out.println("Descripcion: "+notificacionVOut.getDsNotificacion()+"  Formato: " +notificacionVOut.getDsFormatoOrden()+" Met Env: "+notificacionVOut.getDsMetEnv()+ " ");
                }*/
           /* } catch (Exception e) {
                logger.error("Exception Insertar Tiempos");
                e.printStackTrace();
                throw e;
            }
 
   
 }*/
	
	//VELOS a los dos ultimos
	
	/*public void testGetObtieneResponsables() throws Exception {
		try {
			//String CdFormato_b = "1";
			
			ResponsablesVO responsableVO = matrizAsignacionManager.getObtieneResponsables("5","1","BDIAZ");
		System.out.println("Cualquiera");
	       
			
		}catch (Exception e) {
	        fail("Fallo en la lectura del registro");
	        e.printStackTrace();
	    }
	}
*/
	  /*
	public void testGetObtieneTiempoResolucion() throws Exception {
		try {
			//String CdFormato_b = "1";
			
			TiemposVO tiemposVO = matrizAsignacionManager.getTiempoResolucion("5", "1");
		System.out.println("Cualquiera");
	       
			
		}catch (Exception e) {
	        fail("Fallo en la lectura del registro");
	        e.printStackTrace();
	    }
	}
	   */

			
}
