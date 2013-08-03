package mx.com.aon.catbo.web.tests;

import mx.com.aon.portal.service.ProcessResultManager;
import mx.com.aon.portal.util.WrapperResultados;

import java.util.ArrayList;

public class ProcessResultManagerTest extends AbstractTestCases {
	protected ProcessResultManager processResultManager;


	public void testGetMessageTipoUno ()  {
		try {
            WrapperResultados wrapperResultados = new WrapperResultados();
            wrapperResultados.setMsgId("100000");
            wrapperResultados.setItemList(new ArrayList());
            WrapperResultados resultado = processResultManager.processResultMessageId(wrapperResultados);
            assertNotNull(resultado);
//            assertEquals("El valor de ..debe ser ",ayudaCoberturasVO.getCdGarant(), "1" );
            //todo hacer lo mismo con los otros campos leidos en el vo


        }catch (Exception e) {
            System.out.println("Mensaje de salida "+ e.getMessage());
            e.printStackTrace();
		}
	}

    public void testGetMessageTipoDos ()  {
        try {
            WrapperResultados wrapperResultados = new WrapperResultados();
            wrapperResultados.setMsgId("200000");
            wrapperResultados.setItemList(new ArrayList());
            WrapperResultados resultado = processResultManager.processResultMessageId(wrapperResultados);
            assertNotNull(resultado);
            assertEquals("Mensaje retornado " , resultado.getMsgText(), "Operacion realizada con exito");

        }catch (Exception e) {
            System.out.println("Mensaje de salida "+ e.getMessage());
            e.printStackTrace();
        }
    }

    public void testGetMessageConIdinexistente ()  {
        try {
            WrapperResultados wrapperResultados = new WrapperResultados();
            wrapperResultados.setMsgId("999999");
            wrapperResultados.setItemList(new ArrayList());
            WrapperResultados resultado = processResultManager.processResultMessageId(wrapperResultados);


        }catch (Exception e) {
            System.out.println("Mensaje de salida "+ e.getMessage());
            e.printStackTrace();
        }
    }

}
