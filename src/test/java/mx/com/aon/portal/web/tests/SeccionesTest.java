/*package mx.com.aon.portal.web.tests;

import mx.com.aon.portal.service.AgrupacionPolizasManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.SeccionesManager;
import mx.com.aon.portal.model.AgrupacionPolizaVO;
import mx.com.aon.portal.model.SeccionVO;

public class SeccionesTest extends AbstractTestCases {


	protected SeccionesManager seccionesManager;

  public void testBuscarAgrupaciones() throws Exception {
    	try {
            PagedList pagedList = seccionesManager.buscarSecciones("",0,10);
			logger.error("Todo va bien en AgrupacionPolizas");
		} catch (Exception e) {
			logger.error("Exception en Agregar AgrupacionPolizas");
			e.printStackTrace();
			throw e;

		}

    }

    public void testBuscarAgrupaciones2() throws Exception {
          try {
              PagedList pagedList = seccionesManager.buscarSecciones("Algo",0,10);
              assertNotNull(pagedList);
              assertNotNull(pagedList.getItemsRangeList());
              logger.error("Todo va bien en AgrupacionPolizas");
          } catch (Exception e) {
              logger.error("Exception en Agregar AgrupacionPolizas");
              e.printStackTrace();
              throw e;

          }

      }



    public void testGetSeccion() throws Exception {
          try {
              SeccionVO seccionVO  =  seccionesManager.getSeccion("1");
              assertNotNull(seccionVO);
              assertEquals("Valor esperado de cdSeccion",seccionVO.getCdSeccion(),"1");
              logger.error("Todo va bien en getSeccion");
          } catch (Exception e) {
              logger.error("Exception en Agregar AgrupacionPolizas");
              e.printStackTrace();
              throw e;

          }

      }

    public void testAgregarSeccion() throws Exception {
          try {
              SeccionVO seccionVO = new SeccionVO();
              seccionVO.setCdBloque("1");
              seccionVO.setCdSeccion("1");
              seccionVO.setDsSeccion("Seccion Uno");
              seccionesManager.agregarGuardarSeccion(seccionVO);
              logger.error("Todo va bien en AgrupacionSeccion");
          } catch (Exception e) {
              logger.error("Exception en Agregar AgrupacionPolizas");
              e.printStackTrace();
              throw e;

          }
      }

    public void testAgregarSeccion2() throws Exception {
          try {
              SeccionVO seccionVO = new SeccionVO();
              seccionVO.setCdBloque("2");
              seccionVO.setCdSeccion("2");
              seccionVO.setDsSeccion("Seccion Dos");
              seccionesManager.agregarGuardarSeccion(seccionVO);
              logger.error("Todo va bien en AgrupacionSeccion");
          } catch (Exception e) {
              logger.error("Exception en Agregar AgrupacionPolizas");
              e.printStackTrace();
              throw e;

          }
      }

    public void testBorrarSeccion() throws Exception {
          try {
              logger.error("Todo va bien en el borrarSeccion");
          } catch (Exception e) {
              logger.error("Exception en Agregar AgrupacionPolizas");
              e.printStackTrace();
              throw e;

          }

      }

}
*/