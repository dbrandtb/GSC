/**
 * 
 */


/**
 * @author CIMA_USR
 *
 */

package mx.com.aon.portal.web.tests;

import java.util.List;


import mx.com.aon.portal.model.CotizacionMasivaVO;
import mx.com.aon.portal.service.CotizacionManager;
import mx.com.aon.portal.service.PagedList;

public class CotizacionDAOTest extends AbstractTestCases {


	protected CotizacionManager cotizacionManager;

	/*
	public void testBorrarConfigurarEstructuraManager() throws Exception {
	try {
		this.cotizacionManager.borrarCotizacion("", "", "", "", "", "", "", "");
		
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	}
   */
	

 	public void testBuscarCotizacionMasivaManager() throws Exception {
    	try {
    		PagedList salida = (PagedList) this.cotizacionManager.buscarCotizacionesMasivas("", "", "", "", "", "", 0, 9);
			List lista = salida.getItemsRangeList(); 
			assertNotNull(salida);
			assertNotNull(lista);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
	
 	
 /*
	public void testAprobarCotizacionMasivaManager() throws Exception {
    	try {
    		//CotizacionMasivaVO cotizacionMasivaVO = new CotizacionMasivaVO();
    		
    		//cotizacionMasivaVO.setAsegura("");
    		//cotizacionMasivaVO.setCdElemento("");
    		//cotizacionMasivaVO.setCdlayout("");
    		//cotizacionMasivaVO.setCdRamo("");
    		//cotizacionMasivaVO.setFedesde("");
    		//cotizacionMasivaVO.setFehasta("");
    		
    		this.cotizacionManager.aprobarCotizacion("", "", "", "", "", "", "", "", "", "", "", "", "");
    							
			logger.error("Todo va bien en AprobarCotizacionMasiva");
		} catch (Exception e) {
			logger.error("Exception en AprobarCotizacionMasiva");
			e.printStackTrace();		
			throw e;
		
		}
 
    }
*/ 	

	
	

	
	
	
	
	//ESTO ES COPY PASTE DE OTRO TEST, LO DEJO PARA REUTILIZAR CODIGO PARA OTROS TESTS
	
	
	
	
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
  
/*
    @SuppressWarnings("unchecked")
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