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

import mx.com.aon.portal.service.ConfiguracionAlertasAutomaticoManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.model.ConfiguracionAlertasAutomaticoVO;

public class ConfiguracionAlertasAutomaticoTest extends AbstractTestCases {


	protected ConfiguracionAlertasAutomaticoManager configuracionAlertasAutomaticoManager;
	//protected CombosManager combosManager;

 //campo setCdIdUnico=0 inserta, setCdIdUnico>0 update
	/*
  public void testAgregarConfiguracionAlertasAutomaticoManager() throws Exception {
    	try {
    		ConfiguracionAlertasAutomaticoVO configuracionAlertasAutomaticoVO = new ConfiguracionAlertasAutomaticoVO();
    		configuracionAlertasAutomaticoVO.setCdIdUnico("");
    		 configuracionAlertasAutomaticoVO.setDsAlerta("alerta");
    		 configuracionAlertasAutomaticoVO.setDsUsuario("HOL");
    		 configuracionAlertasAutomaticoVO.setCdCliente("37824");
    		 
    		this.configuracionAlertasAutomaticoManager.agregarConfiguracionAlertasAutomatico(configuracionAlertasAutomaticoVO);
			
			logger.error("Todo va bien en Agregar AgregarConfiguracionAlertasAutomatico");
		} catch (Exception e) {
			logger.error("Exception en Agregar AgregarConfiguracionAlertasAutomatico");
			e.printStackTrace();		
			throw e;
		
		}
 
    }
 */

   /*
 public void testBorrarConfigurarEstructuraManager() throws Exception {
    	try {
    		this.configuracionAlertasAutomaticoManager.borrarConfiguracionAlertasAutomatico("5"); 
		} catch (Exception e) {
			e.printStackTrace();
			throw e; 
		}
    }

  */
	
	/*
	@SuppressWarnings("unchecked")
	public void testBuscarConfigurarEstructuraManager() throws Exception {
    	try {
    		PagedList salida = (PagedList) this.configuracionAlertasAutomaticoManager.buscarConfiguracionAlertasAutomatico("HOL", "", "", "", "", "", 1, 3);

			List lista = salida.getItemsRangeList(); 
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
    */
/*
	public void testGetModel()throws Exception{
		try{
			this.configuracionAlertasAutomaticoManager.getModel("HOL", "", "", "", "", "");
		}catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	
	}
*/	
/*	
 public void testRegConfigurarEstructuraManager() throws Exception {
	try {
		ConfiguracionAlertasAutomaticoVO salida = (ConfiguracionAlertasAutomaticoVO) this.configuracionAlertasAutomaticoManager.getConfiguracionAlertasAutomatico("2");
		logger.info("xx: "+salida.getCdIdUnico()+ " yy: " + salida.getDsAlerta()+ "zz: " + salida.getDsUsuario());
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}
	*/
/*	public void testConfAlertasAutoBuscar () throws Exception {
		try {
			PagedList pagedList = configuracionAlertasAutomaticoManager.buscarConfiguracionAlertasAutomatico("", "", "", "", "", "", 0, 10);
			List lista = pagedList.getItemsRangeList();
			for (int i=0; i<lista.size(); i++){
				ConfiguracionAlertasAutomaticoVO configuracionAlertasAutomaticoVO = (ConfiguracionAlertasAutomaticoVO)lista.get(i);
				System.out.println("Usuario: " + configuracionAlertasAutomaticoVO.getDsUsuario());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
*/
    public void testGetConfAlertasAutomatico () throws Exception {
        try {
            ConfiguracionAlertasAutomaticoVO  configuracionAlertasAutomaticoVO  = configuracionAlertasAutomaticoManager.getConfiguracionAlertasAutomatico("57");
            System.out.println(configuracionAlertasAutomaticoVO.getDsRegion());
            assertNotNull(configuracionAlertasAutomaticoVO);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

/*	public void testGuardarConfAlertasAuto () throws Exception {
		try {
        	ConfiguracionAlertasAutomaticoVO configuracionAlertasAutomaticoVO = new ConfiguracionAlertasAutomaticoVO();
            //cargar campos a estructura
           configuracionAlertasAutomaticoVO.setCdCliente("20");
    	   configuracionAlertasAutomaticoVO.setCdIdUnico("7");
    	   configuracionAlertasAutomaticoVO.setCdProceso("2");
           configuracionAlertasAutomaticoVO.setCdProducto("100");
           configuracionAlertasAutomaticoVO.setCdRol("14");
    	   configuracionAlertasAutomaticoVO.setCdTemporalidad("1");
    	   configuracionAlertasAutomaticoVO.setCdUniEco("42");
    	   configuracionAlertasAutomaticoVO.setCdTipRam("7");
           configuracionAlertasAutomaticoVO.setDsAlerta("Test Alertas");
           configuracionAlertasAutomaticoVO.setDsColumnaAlerta("");
    	   configuracionAlertasAutomaticoVO.setDsMensaje("Mensajito");
    	   configuracionAlertasAutomaticoVO.setDsRegion("2");
    	   configuracionAlertasAutomaticoVO.setDsTablaAlerta("");
           configuracionAlertasAutomaticoVO.setDsUsuario("CCRUZ");
           configuracionAlertasAutomaticoVO.setFeInicio("15/05/2008");
    	   configuracionAlertasAutomaticoVO.setFgMandaEmail("1");
           configuracionAlertasAutomaticoVO.setFgMandaPantalla("1");
           configuracionAlertasAutomaticoVO.setFgPermPantalla("1");
    	   configuracionAlertasAutomaticoVO.setNmDiasAnt("2");
    	   configuracionAlertasAutomaticoVO.setNmDuracion("5");
    	   configuracionAlertasAutomaticoVO.setNmFrecuencia("3");

    	   configuracionAlertasAutomaticoManager.guardarConfiguracionAlertasAutomatico(configuracionAlertasAutomaticoVO);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
*/
}