package mx.com.aon.portal.web.tests;

import mx.com.aon.portal.service.MecanismoDeTooltipManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.model.MecanismoDeTooltipVO;;

public class MecanismoDeTooltipTest extends AbstractTestCases {
	protected MecanismoDeTooltipManager mecanismoDeTooltipManager;

//MB
	public void testAgregarGuardarMecanismoDeTooltip ()  {
		try {
			MecanismoDeTooltipVO mecanismoDeTooltipVO = new MecanismoDeTooltipVO();
						
			mecanismoDeTooltipVO.setIdObjeto("89");
			mecanismoDeTooltipVO.setNbObjeto("grid2ButtonExportarId");
			mecanismoDeTooltipVO.setIdTitulo("7");
			mecanismoDeTooltipVO.setFgTipoObjeto("10");
			mecanismoDeTooltipVO.setNbEtiqueta("dcfcsz");
			mecanismoDeTooltipVO.setDsTooltip("dvvx");
			mecanismoDeTooltipVO.setFgAyuda("1");
			mecanismoDeTooltipVO.setDsAyuda("");
			mecanismoDeTooltipVO.setLang_Code("2");

			mecanismoDeTooltipManager.agregarGuardarMecanismoDeTooltip(mecanismoDeTooltipVO);
						
            assertNotNull(mecanismoDeTooltipVO);
            //assertEquals("El valor de ..debe ser ",mecanismoDeTooltipVO.getCdGarant(), "FIC" );
            //todo hacer lo mismo con los otros campos leidos en el vo

        }catch (Exception e) {
            fail("Fallo en el agregar del registro");
            e.printStackTrace();
		}

	}

/*
	//MB
	public void testBuscarMecanismoDeTooltip ()  {
        try {
            PagedList pagedList =  mecanismoDeTooltipManager.buscarMecanismoDeTooltip("","1","",0,10);		//TEXT BOX 1  /winEditPlanId  /1
            assertNotNull(pagedList);

        }catch (Exception e) {
            fail("Fallo en el BUSCAR mecanismo tooltip");
            e.printStackTrace();
        }
    }
*/
/*MB
    public void testGetMecanismoDeTooltip() {
        try {
            String idObjedo = "196";
            String lang_Code = "1";
            
            MecanismoDeTooltipVO mecanismoDeTooltipVO = mecanismoDeTooltipManager.getMecanismoDeTooltipVO(idObjedo, lang_Code);
            assertNotNull(mecanismoDeTooltipVO);
            //assertEquals("El valor de ..debe ser ",mecanismoDeTooltipVO.getCdGarant(), "valor esperado" );
            //todo hacer lo mismo con los otros campos leidos en el vo
        }catch (Exception e) {
            fail("Fallo en la lectura del registro");
            e.printStackTrace();
        }
    }
*/    
    
/*
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
*/

/*	//MB
    public void testBorrarAyudaCoberturas () throws Exception {
        try {
            //borro el registro ingresado previamente
        	mecanismoDeTooltipManager.borrarMecanismoDeTooltip("196","1");
        }catch (Exception e) {
            fail("Fallo en el borrar del registro");
            e.printStackTrace();
            throw e;
        }
    }
*/
	
}//fin del test
