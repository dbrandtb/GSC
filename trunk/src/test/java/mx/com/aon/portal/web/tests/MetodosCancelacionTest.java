package mx.com.aon.portal.web.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import mx.com.aon.portal.model.MetodoCancelacionVO;
import mx.com.aon.portal.service.MetodosCancelacionManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;

public class MetodosCancelacionTest extends AbstractTestCases {


	protected MetodosCancelacionManager metodosCancelacionManager;
	
	
	//*****************************************************************************
    //
	/*
    @SuppressWarnings("unchecked")
	public void testGetEstructura() throws Exception {
    	try {
    		PagedList listaEstructuras = new PagedList(); 
    		listaEstructuras = estructuraManager.getEstructura("5");
            List listaResultado = listaEstructuras.getItemsRangeList();
            for (int i = 0; i < listaResultado.size(); i++) {
                EstructuraVO o =  (EstructuraVO)listaResultado.get(i);
                System.out.println("codigo "+ o.getCodigo());
                System.out.println("descripcion "+ o.getDescripcion());
            }
    		logger.info("Total Items: " + listaEstructuras.getTotalItems());
    	} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
	*/
	
	
    /*@SuppressWarnings("unchecked")
	public void testObtieneEstructuras() throws Exception {
    	try {
    		PagedList pagedList  =  estructuraManager.buscarEstructuras(0, 10, "Lista");
            List listaResultado = pagedList.getItemsRangeList();
            for (int i = 0; i < listaResultado.size(); i++) {
                EstructuraVO o =  (EstructuraVO)listaResultado.get(i);
                System.out.println("codigo "+ o.getCodigo());
                System.out.println("descripcion "+ o.getDescripcion());

            }
    		logger.info("Total Items: " + pagedList.getTotalItems());
    	} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
*/
  //*****************************************************************************
    //
	/*
    @SuppressWarnings("unchecked")
	public void testGetEstructura() throws Exception {
    	try {
    		EstructuraVO estructuraVO =  estructuraManager.getEstructura("123455");
            
            logger.info("Código: "+estructuraVO.getCodigo()+ " Descripción: " + estructuraVO.getDescripcion());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
   */ 
    

    public void testAgregarGuardarMetodoCancelacion() throws Exception {
    	try {
    		MetodoCancelacionVO metodoCancelacionVO = new MetodoCancelacionVO();
    		metodoCancelacionVO.setCdMetodo("");
    		metodoCancelacionVO.setDsMetodo("FA");
    		metodosCancelacionManager.agregarGuardarMetodoCancelacion(metodoCancelacionVO);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }

	
	//*****************************************************************************
	//FUNCIONA CORRECTAMENTE BAJO 20 PRUEBAS
    /*
    public void testInsertaEstructura() throws Exception {
    	try {
    		EstructuraVO estructuraVO = new EstructuraVO();
    		estructuraVO.setDescripcion("Lista corporativa 10000");
    		WrapperResultados wrapperResultados =  estructuraManager.saveOrUpdateEstructura(estructuraVO, "INSERTA_ESTRUCT");
    		if(wrapperResultados.getMsg().equals("1")){
    			logger.info("--->"+wrapperResultados.getMsgId() + " ERROR: " + wrapperResultados.getMsg()+ " PROBLEMA!! DESCRIPCIÓN DUPLICADA. NO SE INSERTO EL REGISTRO ");
    		}else{	
    			logger.info("--->"+wrapperResultados.getMsgId() + " EXITO: "+wrapperResultados.getMsg()+" SE INSERTÓ EL REGISTRO ");
    		}
    	} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
    */
	
	//*****************************************************************************
  //FUNCIONA CORRECTAMENTE BAJO 7 PRUEBAS
	/*
    public void testBorraEstructura() throws Exception {
    	try {
			WrapperResultados wrapperResultados =  estructuraManager.borraEstructura("16");
			if(wrapperResultados.getMsg().equals("1")){
    			logger.info("--->"+wrapperResultados.getMsgId() + " ERROR: " + wrapperResultados.getMsg()+ " PROBLEMA!! NO SE PUDO BORRAR EL REGISTRO, EXISTEN ELEMENTOS DEPENDIENTES ");
    		}else{	
    			logger.info("--->"+wrapperResultados.getMsgId() + " EXITO: "+wrapperResultados.getMsg()+" EL REGISTRO SE BORRÓ SATISFACTORIAMENTE");
    		}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
  */
}
