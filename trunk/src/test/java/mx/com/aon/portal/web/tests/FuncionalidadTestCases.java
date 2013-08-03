package mx.com.aon.portal.web.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import mx.com.aon.portal.model.EstructuraVO;
import mx.com.aon.portal.model.FuncionalidadVO;
import mx.com.aon.portal.service.EstructuraManager;
import mx.com.aon.portal.service.FuncionalidadManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;

public class FuncionalidadTestCases extends AbstractTestCases {


	protected FuncionalidadManager funcionalidadManager;
	
	
	//*****************************************************************************
    //
	
    @SuppressWarnings("unchecked")
	public void testGetFuncionalidad() throws Exception {
    	try {
    		PagedList listaFuncionalidad = new PagedList(); 
    		//FuncionalidadVO funcionalidadVO = funcionalidadManager.getFuncionalidad("3005", "EJECUTIVOCUENTA", "HERBE", "",0,10);
            //pv_nivel_i, pv_sisrol_i, pv_usuario_i, pv_funciona_i
    		/*List listaResultado = listaFuncionalidad.getItemsRangeList();
            for (int i = 0; i < listaResultado.size(); i++) {
                FuncionalidadVO o =  (FuncionalidadVO)listaResultado.get(i);
               logger.debug("codigo "+ funcionalidadVO.getCdElemento()+ "descripcion "+ funcionalidadVO.getDsElemen());
            }*/
    		logger.info("Sali ");
    	} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
	
	
	//*****************************************************************************
    //FUNCIONA CORRECTAMENTE, PERO FALTA LA DEVOLUCIÓN DE LOS DOS PARAMETROS DE RETORNO
   /*@SuppressWarnings("unchecked")
	public void testObtieneEstructuras() throws Exception {
    	try {
    		funcionalidadManager.getFuncionalidad("3005", "Ejecutivo de cuenta", "Herbe Osorio Landeros", "Checklist");
    		//pv_nivel_i, pv_sisrol_i, pv_usuario_i, pv_funciona_i
    		//List listaResultado = pagedList.getItemsRangeList();
            for (int i = 0; i < listaResultado.size(); i++) {
                FuncionalidadVO o =  (FuncionalidadVO)listaResultado.get(i);
                logger.debug("codigo "+ o.getCdElemento()+"descripcion "+ o.getDsElemen());
                

            }
    		logger.info("Total Items: " + pagedList.getTotalItems());
    	logger.debug("Listo");
    	} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }*/

  //*****************************************************************************
    //
	
   /* @SuppressWarnings("unchecked")
	public void testGetEstructura() throws Exception {
    	try {
    		FuncionalidadVO funcionalidadVO =  funcionalidadManager.getFuncionalidad("","","","");
    		//pv_nivel_i, pv_sisrol_i, pv_usuario_i, pv_funciona_i
            logger.info("Código: "+ funcionalidadVO.getCdElemento()+ " Descripción: " + funcionalidadVO.getDsElemen());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
    */
    
	//*****************************************************************************
	//FUNCIONA CORRECTAMENTE BAJO 10 PRUEBAS
	
  /*  public void testGuardarFuncionalidad() throws Exception {
    	try {
    		
    		FuncionalidadVO funcionalidadVO = new FuncionalidadVO();
    		
    		List listaFuncionalidad = null;
    		
    		
    		funcionalidadVO.setCdElemento("3005");
    		funcionalidadVO.setCdSisRol("EJECUTIVOCUENTA");
    		funcionalidadVO.setCdUsuario("HERBE");
    		funcionalidadVO.setCdFunciona("2");
    		funcionalidadVO.setCdOpera("3");
    		funcionalidadVO.setSwEstado("1");
    	   
    		listaFuncionalidad = new ArrayList();
    		listaFuncionalidad.add(funcionalidadVO);
    		
    		String Final = funcionalidadManager.guardarFuncionalidades(listaFuncionalidad);
			
    		logger.error("Todo va bien en Agregar Funcionalidades");
    		
    		if(wrapperResultados.getMsg().equals("1")){
    			logger.info("--->"+wrapperResultados.getMsgId() + " ERROR: " + wrapperResultados.getMsg()+ " PROBLEMA!! NO SE ACTUALIZÓ EL REGISTRO ");
    		}else{	
    			logger.info("--->"+wrapperResultados.getMsgId() + " EXITO: "+wrapperResultados.getMsg()+" SE ACTUALIZÓ EL REGISTRO ");
    		
    		}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }*/
   
	
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
	
  /*  public void testBorraEstructura() throws Exception {
    	try {
			funcionalidadManager.borrarFuncionalidades("3005", "EJECUTIVOCUENTA", "HERBE", "1", "2");
			//pv_cdelemento_i, pv_cdsisrol_i, pv_cdusuario_i, pv_cdfunciona_i, pv_cdopera_i
			logger.debug("se Borro con exito");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
   */
    
  //*****************************************************************************
  //FUNCIONA CORRECTAMENTE BAJO 2 PRUEBAS
	/*
	public void testCopiaEstructura() throws Exception {
    	try {
    		EstructuraVO estructuraVO = new EstructuraVO();
    		estructuraVO.setCodigo("19");
			WrapperResultados wrapperResultados =  estructuraManager.copiaEstructura(estructuraVO);
			if(wrapperResultados.getMsg().equals("1")){
    			logger.info("--->"+wrapperResultados.getMsgId() + " ERROR: " + wrapperResultados.getMsg()+ " PROBLEMA!! NO SE PUDO COPIAR EL REGISTRO");
    		}else{	
    			logger.info("--->"+wrapperResultados.getMsgId() + " EXITO: "+wrapperResultados.getMsg()+" EL REGISTRO SE COPIÓ SATISFACTORIAMENTE");
    		}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
   */
}
