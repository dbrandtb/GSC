package mx.com.aon.portal.web.tests;

import mx.com.aon.portal.service.MantenimientoPlanesManager;
import mx.com.aon.portal.service.NumeroIncisosManager;
import java.util.List;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.model.NumeroIncisoVO;


public class NumeroIncisosTest extends AbstractTestCases {
   
	
    protected NumeroIncisosManager numeroIncisosManager;

 	public void testInsertarNumeroInciso ()  {
		try {
			NumeroIncisoVO numeroIncisoVO = new NumeroIncisoVO();
			
			 //todo setear los parametros de la agregacion
			numeroIncisoVO.setCdElemento("15");
			numeroIncisoVO.setCdPerson("6");
			numeroIncisoVO.setCdRamo("777");
			numeroIncisoVO.setCdUniEco("44");
			numeroIncisoVO.setIndCalc("1");
			numeroIncisoVO.setDsCalculo("10");
			numeroIncisoVO.setIndSituac("I");
			numeroIncisoVO.setIndSufPre("S");
			numeroIncisoVO.setDsSufPre("10");
			numeroIncisoVO.setNmFolioIni("1");
			numeroIncisoVO.setNmFolioFin("1");
			
	    	 // invocar servicio para agregar datos a BD
	    	 this.numeroIncisosManager.insertarNumerosInciso(numeroIncisoVO);
	    		
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
 
    public void testBorrarDesglosePolizas () throws Exception {
        try {
        	//DesglosePolizasVO desglosePolizasVO = new DesglosePolizasVO();
            
        	 //setear los parametros de la agregacion
			//desglosePolizasVO.setCdElemento(cdElemento);
			//desglosePolizasVO.setCdPerson(cdPerson);
			//desglosePolizasVO.setCdTipCon(cdTipCon);
			//desglosePolizasVO.setCdRamo(cdRamo);
			
	    	 // invocar servicio para agregar datos a BD
	    	// this.desglosePolizasManager.agregarDesglosePolizas(desglosePolizasVO);
	    	 
	    	 //borro el registro ingresado previamente
	    	 //this.desglosePolizasManager.borrarDesglosePolizas(desglosePolizasVO.getCdPerson(), desglosePolizasVO.getCdTipCon(), desglosePolizasVO.getCdRamo());
	    	 this.desglosePolizasManager.borrarDesglosePolizas("10","205P","50");
	    	 
	    	 
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
    public void testGetDesglosePolizas() {
        try {
           
            DesglosePolizasVO desglosePolizasVO = desglosePolizasManager.getDesglosePolizas(cdPerson, cdTipCon, cdRamo);
          
           //validar que se haya devuelto datos del get
            assertNotNull(desglosePolizasVO);
           
            //todo hacer lo mismo con los otros campos leidos en el vo
            assertEquals("El valor de CdPerson debe ser  ",desglosePolizasVO.getCdPerson(), cdPerson );
            assertEquals("El valor de cdTipCon debe ser  ",desglosePolizasVO.getCdTipCon(), cdTipCon );
            assertEquals("El valor de cdRamo debe ser  ",desglosePolizasVO.getCdRamo(), cdRamo );
           
        }catch (Exception e) {
            fail("Falló en la lectura del registro.");
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
	public void testBuscarDesglosesPoliza () throws Exception {
		try {
			mantenimientoPlanesManager.insertarPlan(null);
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

    public void testGetDesglosePoliza () throws Exception {
        try {
            mantenimientoPlanesManager.insertarPlan(null);
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

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

    public void testCopiarDesglosePoliza () throws Exception {
        try {
            mantenimientoPlanesManager.insertarPlan(null);
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
   
   
   
   
 
<<<<<<< .mine
  	
    
=======

>>>>>>> .r804
  	public void testGetModel()throws Exception{
  		try{
  			this.desglosePolizasManager.getModel("4500", "205P", "777");
  		}catch (Exception e) {
  		e.printStackTrace();
  		throw e;
  	}
  	
  	}
  
  	
  
  	
   public void testRegConfigurarEstructuraManager() throws Exception {
  	try {
  		DesglosePolizasVO salida = (DesglosePolizasVO) this.desglosePolizasManager.getDesglosePolizas("5000", "70P", "70"); 
  		logger.info("xx: "+salida.getCdPerson() + " yy: " + salida.getCdElemento() + "zz: " + salida.getCdTipCon());
  	} catch (Exception e) {
  		e.printStackTrace();
  		throw e;
  	}
  }
  	
  */


}
