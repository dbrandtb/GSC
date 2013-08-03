/**
 * 
 */


/**
 * @author CIMA_USR
 *
 */

package mx.com.aon.portal.web.tests;

import java.util.List;

import mx.com.aon.portal.service.CombosManager;
import mx.com.aon.portal.service.ConfigurarEstructuraManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.model.ConfigurarEstructuraVO;

public class ConfigurarEstructuraTest extends AbstractTestCases {


	protected ConfigurarEstructuraManager configurarEstructuraManager;
	protected CombosManager combosManager;

  public void testAgregarConfigurarEstructuraManager() throws Exception {
    	try {
    		ConfigurarEstructuraVO configurarEstructuraVO = new ConfigurarEstructuraVO();
    		configurarEstructuraVO.setCodigoEstructura("1");
    		configurarEstructuraVO.setNombre("ZONA SUROESTE NORTE");
    		configurarEstructuraVO.setVinculoPadre("4");
    		configurarEstructuraVO.setTipoNivel("");
    		configurarEstructuraVO.setPosicion("1");
    		configurarEstructuraVO.setNomina("1");
    		configurarEstructuraVO.setCodigoElemento("1");
			//this.configurarEstructuraManager.agregarConfigurarEstructura(configurarEstructuraVO);
    		configurarEstructuraManager.guardarConfigurarEstructura(configurarEstructuraVO);
			
			logger.error("Todo va bien en Agregar ConfigurarEstructura");
		} catch (Exception e) {
			logger.error("Exception en Agregar ConfigurarEstructura");
			e.printStackTrace();		
			throw e;
		
		}
 
    }
 

 /*   public void testEditarConfigurarEstructuraManager() throws Exception {
    	try {
    		ConfigurarEstructuraVO configurarEstructuraVO = new ConfigurarEstructuraVO();
    		configurarEstructuraVO.setCodigoEstructura("1");
    		configurarEstructuraVO.setCodigoElemento("1");
    		configurarEstructuraVO.setNombre("ZONA NORTE");
    		configurarEstructuraVO.setVinculoPadre("");
    		configurarEstructuraVO.setTipoNivel("");
    		configurarEstructuraVO.setPosicion("1");
    		configurarEstructuraVO.setNomina("0");
			this.configurarEstructuraManager.guardarConfigurarEstructura(configurarEstructuraVO);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }*/
  
 /* public void testBorrarConfigurarEstructuraManager() throws Exception {
    	try {
    		this.configurarEstructuraManager.borrarConfigurarEstructura("1","25"); 
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }*/
/*
    @SuppressWarnings("unchecked")
	public void testBuscarConfigurarEstructuraManager() throws Exception {
    	try {
    		PagedList salida = (PagedList) this.configurarEstructuraManager.buscarConfigurarEstructuras("1","ZONA NORTE","","", 0,10);

			List lista = salida.getItemsRangeList(); 
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }*/
/*
	public void testCopiarConfigurarEstructuraManager() throws Exception {
		try {
    	 this.configurarEstructuraManager.copiarConfigurarEstructura("1","25"); 
    	 
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
*/
  /*
	public void testObtenerCliente()throws Exception{
		try {
			
			this.combosManager.comboClientes(); 
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}	
		
	}
	*/
	
/*	public void testGetModel()throws Exception{
		try{
			this.configurarEstructuraManager.getModel("1","ZONA SUROESTE","","");
		}catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	
	}
*/	
	/* public void testRegConfigurarEstructuraManager() throws Exception {
	    	try {
	    		ConfigurarEstructuraVO salida = (ConfigurarEstructuraVO) this.configurarEstructuraManager.getConfigurarEstructura("1");
    			logger.info("xx: "+salida.getNombre()+ " yy: " + salida.getNomina()+ "zz: " +salida.getVinculoPadre());

	    		//ConfigurarEstructuraVO lista = salida; 
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
	    }*/
}