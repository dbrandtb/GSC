package mx.com.aon.portal.web.tests;

import java.util.List;

import mx.com.aon.portal.model.MensajeErrorVO;
import mx.com.aon.portal.service.MensajesErrorManager;
import mx.com.aon.portal.service.PagedList;

public class MensajesErrorTest extends AbstractTestCases {
	protected MensajesErrorManager mensajesErrorManager;

/*	@SuppressWarnings("unchecked")
	public void testBuscarMensajesError () throws Exception{
		try {
			PagedList pagedList = mensajesErrorManager.buscarMensajes("", "", 0, 10);
			List lista = pagedList.getItemsRangeList();
			for (int i=0; i<lista.size(); i++) {
				MensajeErrorVO mensajeErrorVO = (MensajeErrorVO)lista.get(i);
				System.out.println("Mensaje: " + mensajeErrorVO.getMsgText());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
*/
/*	public void testGuardarMensaje () throws Exception {
		try {
			mensajesErrorManager.guardarMensajeError("", "Mi mensajito", "1");
			System.out.println("Guardado");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
*/
	public void testGetMensaje () throws Exception {
		try {
			MensajeErrorVO mensajeErrorVO = mensajesErrorManager.getMensajeError("100020");
			System.out.println("Mensaje: " + mensajeErrorVO.getMsgText());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
