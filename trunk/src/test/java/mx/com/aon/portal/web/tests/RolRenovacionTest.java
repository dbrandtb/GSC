package mx.com.aon.portal.web.tests;

import mx.com.aon.portal.service.PeriodosGraciaManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.RolesRenovacionManager;
import mx.com.aon.portal.model.RolRenovacionVO;

public class RolRenovacionTest extends AbstractTestCases {
	
	private String cdRenova;
	private String cdRol;

	  

	protected RolesRenovacionManager rolesRenovacionManager;

/*  ***** TEST para el servicio Buscar *****  */
	/*
    public void testObtenerRolesRenovacion() throws Exception {
          try {
              PagedList pagedList = rolesRenovacionManager.obtenerRolesRenovacion("15",0,10);
              assertNotNull(pagedList);
              assertNotNull(pagedList.getItemsRangeList());
              logger.error("Todo va bien en Buscar Roles Renovacion");
          } catch (Exception e) {
              logger.error("Exception en Buscar Roles Renovacion");
              e.printStackTrace();
              throw e;

          }

      }
	*/

/*  ***** TEST para el servicio Agregar  *****  */	
/*
    public void testRolesRenovacionManager() throws Exception {
          try {
              RolRenovacionVO rolRenovacionVO = new RolRenovacionVO();
              rolRenovacionVO.setCdRenova("15");
              rolRenovacionVO.setCdRol("ASEGURADO");
              
              rolesRenovacionManager.agregarGuardarRolRenovacion(rolRenovacionVO);
              logger.error("Todo va bien en Agregar Roles Renovacion");
          } catch (Exception e) {
              logger.error("Exception en Agregar Roles Renovacion");
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
	
	public void testBorrarRolRenovacion() throws Exception {
        try {
        	
      	   
        	rolesRenovacionManager.borrarRolRenovacion("15", "ASEGURADO");
            logger.error("Todo va bien en el borrar RolRenovacion");
        } catch (Exception e) {
            logger.error("Exception en Borrar RolRenovacion");
            e.printStackTrace();
            throw e;

        }

    } 

}
