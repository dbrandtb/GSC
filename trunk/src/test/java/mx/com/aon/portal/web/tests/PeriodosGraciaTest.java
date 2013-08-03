package mx.com.aon.portal.web.tests;

import mx.com.aon.portal.service.PeriodosGraciaManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.model.PeriodosGraciaVO;

public class PeriodosGraciaTest extends AbstractTestCases {


	protected PeriodosGraciaManager periodosGraciaManager;

/*  ***** TEST para el servicio Buscar *****  */
/*	
    public void testBuscarPeriodosGracia() throws Exception {
          try {
              PagedList pagedList = periodosGraciaManager.buscarPeriodosGracia("PRUEBAS",0,10);
              assertNotNull(pagedList);
              assertNotNull(pagedList.getItemsRangeList());
              logger.error("Todo va bien en BuscarPreriodos de Gracia");
          } catch (Exception e) {
              logger.error("Exception en Buscar Periodos de Gracia");
              e.printStackTrace();
              throw e;

          }

      }
*/	

/*  ***** TEST para el servicio Agregar  *****  */	
/*
    public void testAgregarPeriodosGracia() throws Exception {
          try {
              PeriodosGraciaVO periodoGraciaVO = new PeriodosGraciaVO();
              periodoGraciaVO.setCdTramo("");
              periodoGraciaVO.setDsTramo("BETA");
              periodoGraciaVO.setNmMinimo("50");
              periodoGraciaVO.setNmMaximo("100");
              periodoGraciaVO.setDiasGrac("7");
              periodoGraciaVO.setDiasCanc("15");
              
              periodosGraciaManager.agregarGuardarPeriodosGracia(periodoGraciaVO);
              logger.error("Todo va bien en Agregar Periodo de Gracia");
          } catch (Exception e) {
              logger.error("Exception en Agregar Periodo de Gracia");
              e.printStackTrace();
              throw e;
          }
      }
*/	

/*  ***** TEST para el servicio Obtener *****  */	
/*
    public void testGetPeriodosGracia() throws Exception {
        try {
        	PeriodosGraciaVO  periodoGraciaVO  =  periodosGraciaManager.getPeriodosGracia("1");
            assertNotNull(periodoGraciaVO);
            assertEquals("Valor esperado de periodos de gracia",periodoGraciaVO.getCdTramo(),"1");
            logger.error("Todo va bien en getPeriodosGracia");
        } catch (Exception e) {
            logger.error("Exception en Obtener Periodo de Gracia");
            e.printStackTrace();
            throw e;
         }
       }
*/	    

	
/*  ***** TEST para el servicio Borrar *****  */
	
	public void testBorrarPeriodosGracia() throws Exception {
        try {
      	  periodosGraciaManager.borrarPeriodosGracia("1");
            logger.error("Todo va bien en el borrar Periodos de Gracia");
        } catch (Exception e) {
            logger.error("Exception en Borrar Periodos de Gracia");
            e.printStackTrace();
            throw e;

        }

    } 

}
