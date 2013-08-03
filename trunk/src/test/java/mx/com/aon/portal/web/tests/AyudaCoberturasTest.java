package mx.com.aon.portal.web.tests;

import mx.com.aon.portal.service.AyudaCoberturasManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.model.AyudaCoberturasVO;

public class AyudaCoberturasTest extends AbstractTestCases {
	protected AyudaCoberturasManager ayudaCoberturasManager;


/*	public void testAgregarAyudaCoberturas ()  {
		try {
            AyudaCoberturasVO ayudaCoberturasVO = new AyudaCoberturasVO();
            ayudaCoberturasVO.setCdGarantiaxCia(null);
            ayudaCoberturasVO.setCdUnieco("44");
            ayudaCoberturasVO.setCdTipram("9");
            ayudaCoberturasVO.setCdRamo("777");
            ayudaCoberturasVO.setCdGarant("FIC");
            ayudaCoberturasVO.setDsAyuda("prueba gaf con 44,9,777,FIC");

            
            //todo setear los parametros de la agregacion
            ayudaCoberturasManager.insertarAyudaCoberturas(ayudaCoberturasVO);
            ayudaCoberturasVO = ayudaCoberturasManager.getAyudaCoberturas("13");
            assertNotNull(ayudaCoberturasVO);
            assertEquals("El valor de ..debe ser ",ayudaCoberturasVO.getCdGarant(), "FIC" );
            //todo hacer lo mismo con los otros campos leidos en el vo


        }catch (Exception e) {
            fail("Fallo en el agregar del registro");
            e.printStackTrace();
		}
	}
*/
    public void testBuscarAyudaCoberturas ()  {
        try {
            //PagedList pagedList =  ayudaCoberturasManager.buscarAyudaCoberturas("OFICINA MATRIZ","OTROS (PRUEBAS)","TERREMOTO","DIVERSOS","PRODUCTO PRUEBA",0,10);
            PagedList pagedList =  ayudaCoberturasManager.buscarAyudaCoberturas("", "", "", "", "", "3", 0, 10);
            assertNotNull(pagedList);
            //todo hacer lo mismo con los otros campos leidos en el vo


        }catch (Exception e) {
            fail("Fallo en el agregar del registro");
            e.printStackTrace();
        }
    }
/*
    public void testGetAyudaCobertura() {
        try {
            String cdGarantiaxCia = "1";
            AyudaCoberturasVO ayudaCoberturasVO = ayudaCoberturasManager.getAyudaCoberturas(cdGarantiaxCia);
            assertNotNull(ayudaCoberturasVO);
            assertEquals("El valor de ..debe ser ",ayudaCoberturasVO.getCdGarant(), "valor esperado" );
            //todo hacer lo mismo con los otros campos leidos en el vo
        }catch (Exception e) {
            fail("Fallo en la lectura del registro");
            e.printStackTrace();
        }
    }

    public void testActualizarAyudaCoberturas (){
        try {
            AyudaCoberturasVO ayudaCoberturasVO = new AyudaCoberturasVO();
            ayudaCoberturasVO.setCdGarantiaxCia("13");
            ayudaCoberturasVO.setCdUnieco("44");
            ayudaCoberturasVO.setCdTipram("9");
            ayudaCoberturasVO.setCdRamo("777");
            ayudaCoberturasVO.setCdGarant("FIC");
            ayudaCoberturasVO.setLangCode("1");
            ayudaCoberturasVO.setCdSubram("100");
            ayudaCoberturasVO.setDsAyuda("prueba gaf con 44,9,777,FIC-MOIFICADO");

            ayudaCoberturasManager.guardarAyudaCoberturas(ayudaCoberturasVO);

//            ayudaCoberturasVO = ayudaCoberturasManager.getAyudaCoberturas(cdGarantiaxCia);
//            assertNotNull(ayudaCoberturasVO);
//            assertEquals("El valor de ..debe ser ",ayudaCoberturasVO.getCdGarant(), "valor esperado" );
            //todo hacer lo mismo con los otros campos leidos en el vo

        }catch (Exception e) {
            fail("Fallo en el guardar del registro");
            e.printStackTrace();
        }
    }

    public void testBorrarAyudaCoberturas () throws Exception {
        try {
            //borro el registro ingresado previamente
            ayudaCoberturasManager.borrarAyudaCoberturas("12");
        }catch (Exception e) {
            fail("Fallo en el borrar del registro");
            e.printStackTrace();
            throw e;
        }
    }

*/
}
