package mx.com.aon.portal.web.tests;

import mx.com.aon.portal.service.MantenimientoPlanesManager;

import java.util.List;

import mx.com.aon.portal.service.DesglosePolizasManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.model.DesglosePolizasVO;

public class DesglosePolizasTest extends AbstractTestCases {
   
	protected MantenimientoPlanesManager mantenimientoPlanesManager;
    protected DesglosePolizasManager desglosePolizasManager;

    String cdPerson = "5000";
    String cdElemento = "9";
    String cdTipCon = "70P";
    String cdRamo = "70";

	public void testAgregarDesglosePolizas ()  {
		try {
			DesglosePolizasVO desglosePolizasVO = new DesglosePolizasVO();
			
			 //todo setear los parametros de la agregacion
			desglosePolizasVO.setCdElemento(cdElemento);
			desglosePolizasVO.setCdPerson(cdPerson);
			desglosePolizasVO.setCdTipCon(cdTipCon);
			desglosePolizasVO.setCdRamo(cdRamo);
			
	    	 // invocar servicio para agregar datos a BD
	    	 this.desglosePolizasManager.agregarDesglosePolizas(desglosePolizasVO);
	    		
	    	//Recuperar el registro insertado	
	    	 desglosePolizasVO = desglosePolizasManager.getDesglosePolizas(cdPerson, cdTipCon, cdRamo);
        
	        // Validar si se han guardado correctamente lo datos
            assertNotNull(desglosePolizasVO);
            assertEquals("El valor de CdPerson debe ser  ",desglosePolizasVO.getCdPerson(), cdPerson );
            assertEquals("El valor de cdTipCon debe ser  ",desglosePolizasVO.getCdTipCon(), cdTipCon );
            assertEquals("El valor de cdRamo debe ser  ",desglosePolizasVO.getCdRamo(), cdRamo );
        
        }catch (Exception e) {
            fail("Falló en el agregar del registro de carrito de compras!");
            e.printStackTrace();
		}
	}

	
	
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
    
   
   
    public void testAgregarDesglosePolizas() throws Exception {
      	try {
      		DesglosePolizasVO desglosePolizasVO = new DesglosePolizasVO();
      		desglosePolizasVO.setCdPerson("4500");
      		desglosePolizasVO.setCdElemento("5");
      		desglosePolizasVO.setCdTipCon("913-16");
      		desglosePolizasVO.setCdRamo("777");
      		 
      		this.desglosePolizasManager.agregarDesglosePolizas(desglosePolizasVO);
  			
  			logger.error("Todo va bien en Agregar DesglosePolizas");
  		} catch (Exception e) {
  			logger.error("Exception en Agregar DesglosePolizas");
  			e.printStackTrace();		
  			throw e;
  		
  		}
   
      }
   
   public void testBorrarDesglosePolizas() throws Exception {
      	try {
      		this.desglosePolizasManager.borrarDesglosePolizas("4500", "913-16", "777"); 
  		} catch (Exception e) {
  			e.printStackTrace();
  			throw e; 
  		}
      }
 

  	public void testGetModel()throws Exception{
  		try{
  			this.desglosePolizasManager.getModel("4500", "205P", "777");
  		}catch (Exception e) {
  		e.printStackTrace();
  		throw e;
  	}
  	
  	}
  
  	  public void testCopiarDesglosePolizas() throws Exception {
  	    	try {
  	    		
  	    		this.desglosePolizasManager.copiarDesglosePolizas("4500", "777", "4500","777");
  				
  				logger.error("Todo va bien en copiar DesglosePolizas");
  			} catch (Exception e) {
  				logger.error("Exception en copiar DesglosePolizas");
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

   
    @SuppressWarnings("unchecked")
    public void testBuscarDesglosePolizasManager() throws Exception {
        try {
            PagedList salida = (PagedList) this.desglosePolizasManager.buscarDesglosePolizas("4500", "205P", "777", "", 0,10);

            List lista = salida.getItemsRangeList();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
