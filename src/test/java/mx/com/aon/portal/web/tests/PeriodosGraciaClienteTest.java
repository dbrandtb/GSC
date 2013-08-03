package mx.com.aon.portal.web.tests;

import mx.com.aon.portal.service.MantenimientoPlanesManager;
import mx.com.aon.portal.service.PeriodosGraciaClienteManager;


import java.util.List;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.model.PeriodoGraciaClienteVO;



public class PeriodosGraciaClienteTest extends AbstractTestCases {
   
	
    protected PeriodosGraciaClienteManager periodosGraciaClienteManager;

 	public void testInsertarNumeroInciso ()  {
		try {
			PeriodoGraciaClienteVO periodoGraciaClienteVO = new PeriodoGraciaClienteVO();
			
			 //todo setear los parametros de la agregacion
			periodoGraciaClienteVO.setCdElemento("15");
			periodoGraciaClienteVO.setCdPerson("12");
			periodoGraciaClienteVO.setCveAseguradora("47");
			periodoGraciaClienteVO.setCveProducto("777");
			periodoGraciaClienteVO.setCdTramo("3");
	    	 // invocar servicio para agregar datos a BD
	    	 this.periodosGraciaClienteManager.insertarPeriodoGraciaCliente(periodoGraciaClienteVO);
	    		
	    	//Recuperar el registro insertado	
	    	 //desglosePolizasVO = desglosePolizasManager.getDesglosePolizas(cdPerson, cdTipCon, cdRamo);
        
	        // Validar si se han guardado correctamente lo datos
            //assertNotNull(desglosePolizasVO);
            //assertEquals("El valor de CdPerson debe ser  ",desglosePolizasVO.getCdPerson(), cdPerson );
            //assertEquals("El valor de cdTipCon debe ser  ",desglosePolizasVO.getCdTipCon(), cdTipCon );
            //assertEquals("El valor de cdRamo debe ser  ",desglosePolizasVO.getCdRamo(), cdRamo );
        
        }catch (Exception e) {
            fail("Falló en el agregar del registro");
            e.printStackTrace();
		}
	}

/*
 
    public void testBorrarPeriodoGracia () throws Exception {
        try {
        	
            
        	 //setear los parametros de la agregacion
			//desglosePolizasVO.setCdElemento(cdElemento);
			//desglosePolizasVO.setCdPerson(cdPerson);
			//desglosePolizasVO.setCdTipCon(cdTipCon);
			//desglosePolizasVO.setCdRamo(cdRamo);
			
	    	 // invocar servicio para agregar datos a BD
	    	// this.desglosePolizasManager.agregarDesglosePolizas(desglosePolizasVO);
	    	 
	    	 //borro el registro ingresado previamente
	    	 //this.desglosePolizasManager.borrarDesglosePolizas(desglosePolizasVO.getCdPerson(), desglosePolizasVO.getCdTipCon(), desglosePolizasVO.getCdRamo());
        	
        	PeriodoGraciaClienteVO periodoGraciaClienteVO = new PeriodoGraciaClienteVO();
			
			 //todo setear los parametros de la agregacion
			periodoGraciaClienteVO.setCdElemento("15");
			periodoGraciaClienteVO.setCveAseguradora("47");
			periodoGraciaClienteVO.setCveProducto("777");
			periodoGraciaClienteVO.setCdTramo("3");
			
	    	 this.periodosGraciaClienteManager.borrarPeriodoGraciaCliente(periodoGraciaClienteVO);
	    	 
	    	 
        }catch (Exception e) {
            fail("Fallo en el borrar del registro");
            e.printStackTrace();
            throw e;
        }
    }
    */

    /*
    public void testCopiarDesglosePolizas()  {
	    	try {
	    		
	    		DesglosePolizasVO desglosePolizasVO = new DesglosePolizasVO();
	    		
	    		//obtener cantidad actual de ocurrencias del registro a copiar
	    		PagedList salida = (PagedList) this.desglosePolizasManager.buscarDesglosePolizas(cdPerson, cdTipCon, cdRamo, 1,20);
	  			List lista = salida.getItemsRangeList(); 
	  			size1 += salida.getTotalItems();
	  			
	  			logger.info("size1 : " + size1);
	  			
	    		//realizo copia del registro
	    		this.desglosePolizasManager.copiarDesglosePolizas(cdPerson, cdRamo, cdPersonDestino,cdRamoDestino);
				
	    		//Recuperar la nueva cantidad de ocurrencias para el registro copiado	
	    		PagedList salida2 = (PagedList) this.desglosePolizasManager.buscarDesglosePolizas(cdPerson, cdTipCon, cdRamo, 1, 20);
	  			List lista2 = salida.getItemsRangeList(); 
	  			size2 += salida2.getTotalItems();
		    	 
	  			logger.info("size2 : " + size2);
		    	 
		        // Validar si se han guardado correctamente lo datos
		       assertEquals("El valor de size debe ser  ",size1*2, size2 );
		         
		    	 
			} catch (Exception e) {
				  fail("Falló en la copia del registro.");
		          e.printStackTrace();
			}
	 
	    }	
    */
   
    
   /*
    @SuppressWarnings("unchecked")
  	public void testBuscarNumerosInciso() throws Exception {
      	try {
      		PagedList salida = (PagedList) this.numeroIncisosManager.buscarNumerosInciso("", "", "", "", 1, 10);
  			@SuppressWarnings("unused")
			List lista = salida.getItemsRangeList(); 
  		} catch (Exception e) {
  			e.printStackTrace();
  			throw e;
  		}
      }
   
    */
    
    
 /*
	public void testBuscarPeriodosGraciaCliente () throws Exception {
		try {
			periodosGraciaClienteManager.buscarPeriodosGraciaCliente("", "", "", 1, 10);
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}*/
/*
   

    public void testGuardarDesglosePoliza () throws Exception {
        try {
            mantenimientoPlanesManager.insertarPlan(null);
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void testBorrarDesglosePoliza () throws Exception {
        try {
            mantenimientoPlanesManager.insertarPlan(null);
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

  */

  	/*public void testGetModel()throws Exception{
  		try{
  			this.periodosGraciaClienteManager.getModel("EX", "", "");
  		}catch (Exception e) {
  		e.printStackTrace();
  		throw e;
  	}
  	
  	}
  */
  	
  
  	
 
  	
 	
}
