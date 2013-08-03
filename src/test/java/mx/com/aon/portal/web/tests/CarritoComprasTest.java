/**
 * 
 */


/**
 * @author CIMA_USR
 *
 */

package mx.com.aon.portal.web.tests;

//import mx.com.aon.portal.service.CombosManager;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.service.CarritoComprasManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.portal.model.CarritoComprasDireccionOrdenVO;
import mx.com.aon.portal.model.CarritoComprasGuardarVO;
import mx.com.aon.portal.model.CarritoComprasProductosVO;
import mx.com.aon.portal.model.CarritoComprasRolesVO;
import mx.com.aon.portal.model.CarritoComprasVO;

public class CarritoComprasTest extends AbstractTestCases {


	protected CarritoComprasManager carritoComprasManager;
	//protected CombosManager combosManager;

 //campo setCdIdUnico=0 inserta, setCdIdUnico>0 update

/*  public void testActualizarConfiguracionAlertasAutomaticoManager() throws Exception {
    	try {
    		CarritoComprasVO carritoComprasVO = new CarritoComprasVO();
    		
    	    carritoComprasVO.setCdConfiguracion("5");
    		carritoComprasVO.setFgSiNo("0");
    		 
    		this.carritoComprasManager.agregarGuardarCarritoCompras(carritoComprasVO);
			
			logger.error("Todo va bien en Agregar CarritoCompras");
		} catch (Exception e) {
			logger.error("Exception en Agregar CarritoCompras");
			e.printStackTrace();		
			throw e;
		}
 
    }

    public void testInsertarConfiguracionAlertasAutomaticoManager() throws Exception {
          try {
              CarritoComprasVO carritoComprasVO = new CarritoComprasVO();

              carritoComprasVO.setCdCliente("6");
              carritoComprasVO.setFgSiNo("0");

              this.carritoComprasManager.agregarGuardarCarritoCompras(carritoComprasVO);

              logger.error("Todo va bien en Agregar CarritoCompras");
          } catch (Exception e) {
              logger.error("Exception en Agregar CarritoCompras");
              e.printStackTrace();
              throw e;

          }

      }


 public void testBorrarConfigurarEstructuraManager() throws Exception {
    	try {
    		this.carritoComprasManager.borrarCarritoCompras("22"); 
		} catch (Exception e) {
			e.printStackTrace();
			throw e; 
		}
    }


	@SuppressWarnings("unchecked")
	public void testBuscarCarritoComprasManager() throws Exception {
    	try {
    		PagedList salida = (PagedList) this.carritoComprasManager.buscarCarritoCompras("OLGA L.", "1", 0,10 );

			List lista = salida.getItemsRangeList(); 
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }


 public void testGetBuscarCarritoComprasManager() throws Exception {
	try {
		CarritoComprasVO salida = (CarritoComprasVO) this.carritoComprasManager.getCarritoCompras("25");
		logger.info("cli: "+salida.getCdCliente()+ " sino: " + salida.getFgSiNo());
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}

 public void testBuscarCarritoComprasProductosTest() throws Exception {
		try {
			PagedList pagedList =  (PagedList)carritoComprasManager.obtenerProductosCarritoCompras("1", "RZ", 1, 10);
         assertNotNull(pagedList);
         List lista = pagedList.getItemsRangeList();
         for (int i=0; i<lista.size(); i++){
        	 CarritoComprasProductosVO carritoComprasProductosVO = (CarritoComprasProductosVO)lista.get(i);
 			System.out.println("CDUNIECO: "+carritoComprasProductosVO.getCdUniEco()+"  DSUNIECO: " +carritoComprasProductosVO.getDsUniEco()+" CDRAMO: "+carritoComprasProductosVO.getCdRamo()+" DSRAMO: "+carritoComprasProductosVO.getDsRamo()+"  CDPLAN: " +carritoComprasProductosVO.getCdPlan()+" DSPLAN: "+carritoComprasProductosVO.getDsPlan()+" FEINICIO: "+carritoComprasProductosVO.getFeInicio()+"  FEESTADO: " +carritoComprasProductosVO.getFeEstado()+" MNTOTALP: "+carritoComprasProductosVO.getMnTotalp());
         }
         System.out.println("sali");           
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}	


 @SuppressWarnings("unchecked")
	public void testObtenerCarritoComprasProductosTotalesTest () throws Exception {
		try {
			@SuppressWarnings("unused")
			List result = carritoComprasManager.obtenerMontosCarrito("1","RZ","1000");
			logger.info("Se ejecutó Obtener Encabezados");
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}


 public void testBuscarCarritoComprasRolesTest() throws Exception {
		try {
			PagedList pagedList =  (PagedList)carritoComprasManager.buscarRolesCarritoCompras("1", "75", "403", "1", "1", "2", 1, 10);
      assertNotNull(pagedList);
      List lista = pagedList.getItemsRangeList();
      for (int i=0; i<lista.size(); i++){
    	  CarritoComprasRolesVO carritoComprasRolesVO = (CarritoComprasRolesVO)lista.get(i);
			System.out.println("CDROL: "+carritoComprasRolesVO.getCdRol()+"  DSROL: " +carritoComprasRolesVO.getDsRol() +" NOMBRE_CONTRATANTE: "+carritoComprasRolesVO.getNombreContratante());
      }
      System.out.println("sali");           
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}	
*/
/*	
	public void testGuardarCarritoComprasTest() throws Exception{
		CarritoComprasGuardarVO carritoComprasGuardarVO = new CarritoComprasGuardarVO();
		carritoComprasGuardarVO.setCdCarro("1");
		carritoComprasGuardarVO.setCdUsuari("RZ");
		carritoComprasGuardarVO.setFeInicio("");
		carritoComprasGuardarVO.setNmTarj("548");
		carritoComprasGuardarVO.setCdContra("90365");
		carritoComprasGuardarVO.setCdAsegur("1");
		carritoComprasGuardarVO.setNmSubtot("40300");
		carritoComprasGuardarVO.setNmDsc("0");
		carritoComprasGuardarVO.setNmTotal("40300");
		carritoComprasGuardarVO.setCdEstado("2");
		carritoComprasGuardarVO.setFeEstado("16/03/2008");
		carritoComprasGuardarVO.setCdTipDom("1");
		carritoComprasGuardarVO.setNmOrdDom("1");
		carritoComprasGuardarVO.setCdClient("100");
		carritoComprasGuardarVO.setCdForPag("2");
		carritoComprasGuardarVO.setCdUniEco("1");
		carritoComprasGuardarVO.setCdRamo("120");
		carritoComprasGuardarVO.setNmPoliza("2");
		carritoComprasGuardarVO.setNmSuplem("2");
		carritoComprasGuardarVO.setMnTotalP("7500");
		carritoComprasGuardarVO.setCdTipSit("2");
		carritoComprasGuardarVO.setCdPlan("1");
		carritoComprasGuardarVO.setFgDscapli("2");
		carritoComprasGuardarVO.setFeIngres("");
		carritoComprasGuardarVO.setCdEstadoD("2");

 		
		 carritoComprasManager.guardarCarrito(carritoComprasGuardarVO);
		// PagedList pagedList = carritoComprasManager.getCarritoCompras("");
		
		
	}
*/
/*
	public void testObtenerCarritoComprasMontosOrdenTest() throws Exception {
        try {
            
        	PagedList pagedList =  (PagedList)carritoComprasManager.montosOrdenCarritoCompras("1", 1, 10);
        	assertNotNull(pagedList);
            List lista = pagedList.getItemsRangeList();
            for (int i=0; i<lista.size(); i++){
            	CarritoComprasProductosVO carritoComprasProductosVO = (CarritoComprasProductosVO)lista.get(i);
      			System.out.println("DSUNIECO: "+carritoComprasProductosVO.getDsUniEco()+"  DSRAMO: " +carritoComprasProductosVO.getDsRamo()+" DSRAZSOC: "+carritoComprasProductosVO.getDsRazSoc() +" FEINISUS: "+carritoComprasProductosVO.getFeIniSus()+" MNTOTALP: "+carritoComprasProductosVO.getMnTotalp());
            }
            System.out.println("sali"); 
        	

        }catch (Exception e) {
            fail("Fallo en la lectura del registro");
            e.printStackTrace();
        }
    }
    
/*	public void ObtenerCarritoComprasDescuento() throws Exception {
        try {
          
            WrapperResultados wrapperResultados = carritoComprasManager.calculaDescuentoCarritoCompras("1","RZ","1000");
       }catch (Exception e) {
            fail("Fallo en la lectura del registro");
            e.printStackTrace();
        }
    }	

	public void testObtenerCarritoComprasDescuento() throws Exception {
        try {
          
           carritoComprasManager.calculaDescuentoCarritoCompras("1","RZ","1000");
       }catch (Exception e) {
            fail("Fallo en la lectura del querido registro");
            e.printStackTrace();
        }
    }	
*/
	
	/*public void testObtenerCarritoComprasDomicilio() throws Exception {
        try {
          
        	CarritoComprasDireccionOrdenVO carritoComprasDireccionOrdenVO = carritoComprasManager.obtieneDireccionOrdenCarritoCompras("1000", "1");
       }catch (Exception e) {
            fail("Fallo en la lectura del querido registro");
            e.printStackTrace();
        }
    }*/	
	
	public void testGuardarCarritoComprasMTarjetaTest() throws Exception{
		CarritoComprasGuardarVO carritoComprasGuardarVO = new CarritoComprasGuardarVO();
		
		carritoComprasGuardarVO.setNmTarj("5485451244584140");
		carritoComprasGuardarVO.setCdTiTarj("VIS");
		carritoComprasGuardarVO.setCdPerson("14038");
		carritoComprasGuardarVO.setFeVence("");
		carritoComprasGuardarVO.setCdBanco("B064");
		carritoComprasGuardarVO.setDebCred("C");
 		
		 carritoComprasManager.guardarFormaPago(carritoComprasGuardarVO);
		// PagedList pagedList = carritoComprasManager.getCarritoCompras("");
		
		
	}
	
}
 
 
