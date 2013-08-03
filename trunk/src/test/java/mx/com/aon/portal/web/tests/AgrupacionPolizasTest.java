package mx.com.aon.portal.web.tests;

import mx.com.aon.portal.service.ConfigurarEstructuraManager;
import mx.com.aon.portal.service.CombosManager;
import mx.com.aon.portal.service.AgrupacionPolizasManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.model.ConfigurarEstructuraVO;
import mx.com.aon.portal.model.AgrupacionPolizaVO;

public class AgrupacionPolizasTest extends AbstractTestCases {


	protected AgrupacionPolizasManager agrupacionPolizasManager;

  public void testBuscarAgrupaciones() throws Exception {
    	try {
            PagedList  pagedList = agrupacionPolizasManager.buscarAgrupacionPolizas("","","","","",0,10);
			logger.error("Todo va bien en AgrupacionPolizas");
		} catch (Exception e) {
			logger.error("Exception en Agregar AgrupacionPolizas");
			e.printStackTrace();
			throw e;

		}

    }

    public void testGetAgrupacion() throws Exception {
          try {
              AgrupacionPolizaVO agrupacionPolizaVO =  agrupacionPolizasManager.getAgrupacionPoliza("653");
              logger.error("Todo va bien en AgrupacionPolizas");
          } catch (Exception e) {
              logger.error("Exception en Agregar AgrupacionPolizas");
              e.printStackTrace();
              throw e;

          }

      }

    public void testAgregarAgrupacionPoliza() throws Exception {
          try {
              AgrupacionPolizaVO agrupacionPolizaVO = new AgrupacionPolizaVO();
              agrupacionPolizaVO.setCdPerson("37474");
              agrupacionPolizaVO.setCdTipram("7");
              agrupacionPolizaVO.setCdUnieco("46");
              agrupacionPolizaVO.setCdRamo("105");
              agrupacionPolizaVO.setCdTipo("1");
              agrupacionPolizaVO.setCdEstado("1");
              agrupacionPolizaVO.setCdElemento("8");
              agrupacionPolizasManager.agregarAgrupacionPoliza(agrupacionPolizaVO);
              logger.error("Todo va bien en AgrupacionPolizas");
          } catch (Exception e) {
              logger.error("Exception en Agregar AgrupacionPolizas");
              e.printStackTrace();
              throw e;

          }
      }


    public void testAgregarAgrupacionPoliza2() throws Exception {
          try {
              AgrupacionPolizaVO agrupacionPolizaVO = new AgrupacionPolizaVO();
              agrupacionPolizaVO.setCdPerson("37474");
              agrupacionPolizaVO.setCdTipram("7");
              agrupacionPolizaVO.setCdUnieco("46");
              agrupacionPolizaVO.setCdRamo("105");
              agrupacionPolizaVO.setCdTipo("2");
              agrupacionPolizaVO.setCdEstado("1");
              agrupacionPolizaVO.setCdElemento("8");
              agrupacionPolizasManager.agregarAgrupacionPoliza(agrupacionPolizaVO);
              logger.error("Todo va bien en AgrupacionPolizas");
          } catch (Exception e) {
              logger.error("Exception en Agregar AgrupacionPolizas");
              e.printStackTrace();
              throw e;

          }
      }

    public void testBorrarAgrupacion() throws Exception {
          try {
              agrupacionPolizasManager.borrarAgrupacionPoliza("2");
              logger.error("Todo va bien en AgrupacionPolizas");
          } catch (Exception e) {
              logger.error("Exception en Agregar AgrupacionPolizas");
              e.printStackTrace();
              throw e;

          }

      }

}
