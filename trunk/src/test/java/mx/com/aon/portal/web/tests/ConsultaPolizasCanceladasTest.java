package mx.com.aon.portal.web.tests;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import mx.com.aon.portal.service.ConfigurarEstructuraManager;
import mx.com.aon.portal.service.CombosManager;
import mx.com.aon.portal.service.AgrupacionPolizasManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.PolizasManager;
import mx.com.aon.portal.model.ConfigurarEstructuraVO;
import mx.com.aon.portal.model.AgrupacionPolizaVO;
import mx.com.aon.portal.model.ConsultaPolizasCanceladasVO;

public class ConsultaPolizasCanceladasTest extends AbstractTestCases {


	protected PolizasManager polizasManager;
/*
 public void testBuscarPolizasCanceladas() throws Exception {
    	try {
//            PagedList  pagedList = polizasManager.buscarPolizasCanceladas("H", "OFICINA MATRIZ AON PRINCIPAL","VIDA GRUPO", "11", "6", "TRADE-IN","03/04/07","03/04/07", 0, 10);
            PagedList  pagedList = polizasManager.buscarPolizasCanceladas("", "","", "", "", "","","", 0, 10);
			logger.error("Todo va bien en BuscarPolizas");
		} catch (Exception e) {
			logger.error("Exception en BuscarPolizas");
			e.printStackTrace();
			throw e;
		}
    }

*/
 public void testRevertirPolizasCanceladas() throws Exception {
    	try {
    		ConsultaPolizasCanceladasVO consultaPolizasCanceladasVO = new ConsultaPolizasCanceladasVO();
    		consultaPolizasCanceladasVO.setCdUnieco("1");
    		consultaPolizasCanceladasVO.setCdRamo("70");
    		consultaPolizasCanceladasVO.setEstado("M");
    		consultaPolizasCanceladasVO.setNmPoliza("1");
    		consultaPolizasCanceladasVO.setNmsuplem("245466612000000004");
    		consultaPolizasCanceladasVO.setNSupLogi("");
    		
             polizasManager.revertirPolizasCanceladas(consultaPolizasCanceladasVO);
			logger.error("Todo va bien en BuscarPolizas");
		} catch (Exception e) {
			logger.error("Exception en BuscarPolizas");
			e.printStackTrace();
			throw e;
		}
    }
 
/*
  public void testBuscarPolizasCancelar() throws Exception {
  	try {
//          PagedList  pagedList = polizasManager.buscarPolizasACancelar("AON", "QUALITAS", "INCENDIO", "24213", "1", 0, 5);
          PagedList  pagedList = polizasManager.buscarPolizasACancelar("", "", "", "", "", 0, 10);
			logger.error("Todo va bien en BuscarPolizas a Cancelar");
		} catch (Exception e) {
			logger.error("Exception en BuscarPolizas a Cancelar");
			e.printStackTrace();
			throw e;
		}
  }	*/
		
		/* public void testGuardarPolizasCancelar() throws Exception {
			  	try {
//			          PagedList  pagedList = polizasManager.buscarPolizasACancelar("AON", "QUALITAS", "INCENDIO", "24213", "1", 0, 5);
			  		 List<ConsultaPolizasCanceladasVO> polizasCanceladas = new ArrayList();
			  		ConsultaPolizasCanceladasVO consultaPolizasCanceladasVO = new ConsultaPolizasCanceladasVO();
			  		
			  		consultaPolizasCanceladasVO.setCdUnieco("1");
			  		consultaPolizasCanceladasVO.setCdRamo("100");
			  		consultaPolizasCanceladasVO.setEstado("M");
			  		consultaPolizasCanceladasVO.setNmPoliza("24213");
			  		consultaPolizasCanceladasVO.setNmsuplem("245362212000000000");
			  		consultaPolizasCanceladasVO.setCdagrupa("1");
			  		consultaPolizasCanceladasVO.setNmrecibo("28806");
			  		consultaPolizasCanceladasVO.setCdcancel("A");
			  		consultaPolizasCanceladasVO.setStatus("N");
			  		consultaPolizasCanceladasVO.setFeCancel("25/06/2008");
			  		consultaPolizasCanceladasVO.setSwcancela("N");
			  		
			  		polizasCanceladas.add(consultaPolizasCanceladasVO);
			         polizasManager.modificarCancelacionPoliza(polizasCanceladas);
						logger.error("Todo va bien en BuscarPolizas a Cancelar");
					} catch (Exception e) {
						logger.error("Exception en BuscarPolizas a Cancelar" + e.getMessage());
						e.printStackTrace();
						throw e;
					}
					
  }*/




}
