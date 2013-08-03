package mx.com.aon.portal.web.tests;

import mx.com.aon.portal.service.ConfigurarFormaCalculoAtributosVariablesManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.model.ConfigurarFormaCalculoAtributoVariableVO;

public class ConfigurarFormaCalculoAtributosVariablesTest extends AbstractTestCases {


	protected ConfigurarFormaCalculoAtributosVariablesManager configurarFormaCalculoAtributosVariablesManager;

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

/*  ***** TEST para el servicio Agregar Guardar  *****  */	

    public void testconfigFormaCalAtribVar() throws Exception {
          try {
              ConfigurarFormaCalculoAtributoVariableVO configFormaCalAtribVarVO = new ConfigurarFormaCalculoAtributoVariableVO();
              configFormaCalAtribVarVO.setCdIden("6");
              configFormaCalAtribVarVO.setCdElemento("5017");
              configFormaCalAtribVarVO.setCdUnieco("46");
              configFormaCalAtribVarVO.setCdRamo("777");
              configFormaCalAtribVarVO.setCdTipSit("CA");
              configFormaCalAtribVarVO.setCdTabla("15");
              configFormaCalAtribVarVO.setSwFormaCalculo("1");
              
              configurarFormaCalculoAtributosVariablesManager.guardarConfigurarFormaCalculoAtributo(configFormaCalAtribVarVO);
              logger.error("Todo va bien en Configurar Atributos");
          } catch (Exception e) {
              logger.error("Exception en Configurar Atributos");
              e.printStackTrace();
              throw e;
          }
      }


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
/*	
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
*/
}
