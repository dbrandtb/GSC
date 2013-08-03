package mx.com.aon.catbo.web.tests;

import java.util.ArrayList;



import java.util.List;


import mx.com.aon.portal.service.PagedList;

import mx.com.aon.catbo.model.CasoVO;
import mx.com.aon.catbo.model.FormatoOrdenVO;
import mx.com.aon.catbo.model.ListaEmisionCasoVO;
import mx.com.aon.catbo.service.AdministracionCasosManager;

import mx.com.aon.catbo.model.ResultadoGeneraCasoVO;

public class AdministracionCasosTest extends AbstractTestCases {
	protected AdministracionCasosManager administracionCasosManager;

/*
 public void testSettings() throws Exception {
	try {


		PagedList pagedList = (PagedList)administracionCasosManager.getObtenerResponsable("BO300000200", 1, 5);
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
 }


 public void testBorrarNotificaciones () throws Exception {
     try {
         //borro el registro ingresado previamente
    	 administracionCasosManager.borrarNumCasos("2");
    	   System.out.println("sali");

     }catch (Exception e) {
         fail("Fallo en el borrar del registro de de Caso");
         e.printStackTrace();
         throw e;
     }

     public void testGetobtenerTare() throws Exception {
 		try {
 			//String CdFormato_b = "1";

 			CasoVO numCasosVO =administracionCasosManager.obtieneTareas("PROCESO PARA REVALORIZACIONES", "Back Office 1","2",1, 3));

 	        /*assertNotNull(numCasosVO);
 	        assertEquals("El valor de Cdindnumer debe ser ",numCasosVO.getCdDindNumero(),"1" );
 	        assertEquals("El valor de  Cdmodulo ser ",numCasosVO.getCdModulo(),"retraso" );
 	        assertEquals("El valor de nmmodulo ",numCasosVO.getNmModulo(), "1" );
 	        assertEquals("El valor del nmdesde  ",numCasosVO.getNmDesde(), "Formal" );
 	        assertEquals("El valor de nmhasta ",numCasosVO.getNmHasta(), "1" );
 	        assertEquals("El valor de  nmactual",numCasosVO.getNmActual(), "Mail" );
 	        //cdindnumer, cdmodulo, nmmodulo, nmdesde, nmhasta, nmactual

 		}catch (Exception e) {
 	        fail("Fallo en la lectura del registro");
 	        e.printStackTrace();
 	    }
 	}*/

	/*
	public void testObtiene_Tareas() throws Exception {
			try {


				PagedList pagedList = (PagedList)administracionCasosManager.obtenerMovimientos("BO1200811106", 1, 3);
				//assertNotNull(pagedList);

				//List lista = pagedList.getItemsRangeList();

		        /*for (int i=0; i<lista.size(); i++){
		        	CasoVO casoVO = (CasoVO)lista.get(i);
					//System.out.println("Descripcion: "+notificacionVO.getDsNotificacion()+"  Formato: " +notificacionVO.getDsFormatoOrden()+" Met Env: "+notificacionVO.getDsMetEnv());
		        }*/ /*
		       System.out.println("sali");




		           // List lista = pagedList.getItemsRangeList();

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		 } */
	 
	//Obtener TnumCasos
	/* public void testSettings() throws Exception {
			try {


				PagedList pagedList = (PagedList)administracionCasosManager.getObtenerResponsable("BO300000200", 1, 5);
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
			}*/
		 //}

	
	/*  ***** TEST para el servicio Agregar en numeracion de casos *****  */	
	
	    /*public void testAgregarNumeracionCasos() throws Exception {
	          try {
	        	  CasoVO casoVO = new CasoVO();
	        	  
	        	  casoVO.setCdNumCaso("");
	        	  casoVO.setIndNumer("M");
	        	  casoVO.setCdModulo("3");
	        	  casoVO.setDssufpre("");
	        	  casoVO.setNmCaso("3");
	              casoVO.setNmDesde("1");
	              casoVO.setNmHasta("9");
	              
	              administracionCasosManager.guardarNumerosCaso("","M","3","","3","1","9");				 
	              logger.error("Todo va bien en agregar numeracion casos");
	          } catch (Exception e) {
	              logger.error("Exception en Agregar numeracion casos");
	              e.printStackTrace();
	              throw e;
	          }
	      }
	    */
	    /*  ***** FIN TEST para el servicio Agregar en numeracion de casos *****  */
	
	/*  ***** TEST para el servicio Obtener *****  */	
	
	 /*   public void testGetNumeracionCasos() throws Exception {
	        try {
	        	CasoVO  casoVO  =  administracionCasosManager.getObtenerNumeroCaso("1");
	            assertNotNull(casoVO);
	            assertEquals("Valor esperado de numeracion casos",casoVO.getCdNumCaso(),"1");
	            logger.error("Todo va bien en numeracion casos");
	        } catch (Exception e) {
	            logger.error("Exception en numeracion casos");
	            e.printStackTrace();
	            throw e;
	         }
	       }
	*/

		
		/*funcione bien*/
	/*	public void testGetNumeracionCasos() throws Exception {
		try {
			String CdFormato_b = "4";
			
			CasoVO  casoVO  =  administracionCasosManager.getObtenerNumeroCaso(CdFormato_b);
		
	       assertNotNull(casoVO);
	        assertEquals("El valor de Codigo debe ser ",casoVO.getCdNumCaso(),"4" );
	      /*  assertEquals("El valor de la descripcion de Notificacion ser ",notificacionVO.getDsNotificacion(),"retraso" );
	        assertEquals("El valor de del codigo de formato debe ser ",notificacionVO.getCdFormatoOrden(), "1" );
	        assertEquals("El valor del nombre de formato debe ser ",notificacionVO.getDsFormatoOrden(), "Formal" );
	        assertEquals("El valor de Descripcion debe ser ",notificacionVO.getCdMetEnv() , "1" );
	        assertEquals("El valor de Descripcion debe ser ",notificacionVO.getDsMetEnv(), "Mail" );*/
			
	/*	}catch (Exception e) {	
	        fail("Fallo en la lectura del registro");
	        e.printStackTrace();
	    	}	
		}
		*/
	
	    
/*
	  public void testobtenerSufijoPrefijo() throws Exception {
	try {
		
		PagedList pagedList = (PagedList)administracionCasosManager.obtenerSufijoPrefijo(" ", 1, 3);   //assertNotNull(pagedList);
		
		//List lista = pagedList.getItemsRangeList();
        
        /*for (int i=0; i<lista.size(); i++){
        	CasoVO casoVO = (CasoVO)lista.get(i);
			//System.out.println("Descripcion: "+notificacionVO.getDsNotificacion()+"  Formato: " +notificacionVO.getDsFormatoOrden()+" Met Env: "+notificacionVO.getDsMetEnv());
        }*/
		
        /*System.out.println("sali"); 
		
		
			
           //List lista = pagedList.getItemsRangeList();
         
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}

}
	    
*/
	
	/*public void testGuaradCasos() throws Exception {
        try {
      	        	
        	 
        	ResultadoGeneraCasoVO res= administracionCasosManager.guardarCasos("1","DARROYO","42","1","M", "ABC-000100","ABC-000100",null, "20","FD00475L","33871",null,null,"3","10","Ingreso de Caso");
            logger.error("Todo va bien en agregar numeracion casos");
            
        } catch (Exception e) {
            logger.error("Exception en Agregar casos");
            e.printStackTrace();
            throw e;
        }
    }*/

	/*public void testGuardarNuevoCaso() throws Exception {
    try {  	        	    	
    	CasoVO casoVO = new CasoVO();    	
    	
    	casoVO.setCdMatriz("3");//con cdMatriz = 5 no guarda
    	casoVO.setCdUsuario("SLIZARDI");
    	casoVO.setCdUnieco("1");
    	casoVO.setCdRamo("105");
    	casoVO.setEstado("M");
    	casoVO.setNmsituac("ABC-000200");
    	casoVO.setNmsituaext("ABC-00020");
    	casoVO.setNmsbsitext(null);
    	casoVO.setNmpoliza("17");
    	casoVO.setNmpoliex("FD00473M");
    	casoVO.setCdPerson("35270");
    	casoVO.setDsmetcontac("Gritando");
    	casoVO.setIndPoliza("NO");
    	casoVO.setCdPrioridad("2");
    	casoVO.setCdProceso("10");
    	casoVO.setDsObservacion("Estos datos desde la pantalla no se pudieron guardar");
    	
    	FormatoOrdenVO formatoOrdenVO = new FormatoOrdenVO();
    	
    	ResultadoGeneraCasoVO res= administracionCasosManager.guardarNuevoCaso(casoVO, formatoOrdenVO);
        logger.error("SE GUARDO EL CASOOOOOOO!!!!!. Resultado: cdOrdenTrabajo: "+res.getCdOrdenTrabajo()+", - cdNumeroCaso: "+res.getNumCaso());
        
    } catch (Exception e) {
        logger.error("Exception en Agregar casos");
        e.printStackTrace();
        throw e;
    }
	}*/
	
	/*public void testValidaEscalamiento() throws Exception {
        try {
      	        	
        	 
        	 administracionCasosManager.validaEscalamieno("BO100000100");
            logger.error("Todo va bien en agregar numeracion casos");
            
        } catch (Exception e) {
            logger.error("Exception en Agregar casos");
            e.printStackTrace();
            throw e;
        }
    }*/
	
	 
	/*public void testGuaradCasos() throws Exception {
    try {
    	CasoVO casoVO = new CasoVO();
    	FormatoOrdenVO formatoOrdenVO = new FormatoOrdenVO();
    	ListaEmisionCasoVO listaEmisionCasoVO = new ListaEmisionCasoVO();    	
    	
    	java.util.List<ListaEmisionCasoVO> listaEmisionVO = new ArrayList<ListaEmisionCasoVO>();
    	
    	listaEmisionCasoVO.setCdAtribu("1");
    	listaEmisionCasoVO.setCdformatoorden("10");
    	listaEmisionCasoVO.setCdordentrabajo("1");
    	listaEmisionCasoVO.setCdSeccion("110");
    	listaEmisionCasoVO.setOtValor("fgh");
    	
		formatoOrdenVO.setListaEmisionVO(listaEmisionVO);
    	ResultadoGeneraCasoVO res= administracionCasosManager.guardarNuevoCaso(casoVO, formatoOrdenVO);
        logger.error("Todo va bien en agregar numeracion casos");
        
    } catch (Exception e) {
        logger.error("Exception en Agregar casos");
        e.printStackTrace();
        throw e;
    }
	}*/
	
	/*
	 public void testobtenerUsuariosResponsablesMovCaso() throws Exception {
	try {
		
		PagedList pagedList = (PagedList)administracionCasosManager.obtenerUsuariosResponsablesMovCaso("BO1102", "93", 1, 3);		//assertNotNull(pagedList);
		
		//List lista = pagedList.getItemsRangeList();
        
        /*for (int i=0; i<lista.size(); i++){
        	CasoVO casoVO = (CasoVO)lista.get(i);
			//System.out.println("Descripcion: "+notificacionVO.getDsNotificacion()+"  Formato: " +notificacionVO.getDsFormatoOrden()+" Met Env: "+notificacionVO.getDsMetEnv());
        }*/
		
        //System.out.println("sali"); 
		
		
			
           // List lista = pagedList.getItemsRangeList();
         
	/*} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}

}*/

/*	public void testGetModel()throws Exception{
		try{
			this.administracionCasosManager.getModel("");
			}catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	}
*/
	
/*	public void testBuscarNumerosCasos() throws Exception {
		try {
			
			PagedList pagedList =  (PagedList)administracionCasosManager.obtenerNumerosCasos("C", 0, 10);
	        assertNotNull(pagedList);
	        List lista = pagedList.getItemsRangeList();
	        for (int i=0; i<lista.size(); i++){
	        	CasoVO casoVO = (CasoVO)lista.get(i);
				System.out.println("Descripcion: "+casoVO.getDesModulo());
	        }
	        System.out.println("sali");           
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
*/	
	/*public void testBuscarReasignacionCaso() throws Exception {
		try {
			
			List lista = administracionCasosManager.obtenerReasignacionCaso("1");
	       /* for (int i=0; i<lista.size(); i++){
	        	CasoVO casoVO = (CasoVO)lista.get(i);
				System.out.println("Descripcion: "+casoVO.getDesUsuario());
	        }*/
	       /* System.out.println("sali");           
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	//obtenerNumerosCasos

	public void testGuardarMovimientoGenerico() throws Exception {
		try {

/*

		map.put("numeroCaso", numeroCaso);
		map.put("cdUsuario", cdusuario);
		map.put("cdNivAtn", cdnivatn);
		map.put("cdStatus", cdstatus);
		map.put("cdModulo", cdmodulo);
		map.put("cdPriord", cdpriord);
		map.put("nmCompra", nmcompra);
		map.put("unidad", tunidad);
		map.put("dsObservacion", dsobservacion);
            
             */
          /*  administracionCasosManager.guardaMovimientoGenerico("BO1200810197",null,null,null,null,null,null,null,"cualqueir cosa gaby");
	        System.out.println("sali");

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
*/
	
	 public void testValidaCompraTiempo() throws Exception {
			try {
				
				String resultado =this.administracionCasosManager.validaCompraTiempo("11",1, " BO1200812187");
				if (Integer.parseInt(resultado) == 1)
					System.out.println("Es 1, ");
				else System.out.println("Es 0, ");
				//assertNotNull(pagedList);
	            
	            System.out.println("sali");           
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
 } //fin Test
