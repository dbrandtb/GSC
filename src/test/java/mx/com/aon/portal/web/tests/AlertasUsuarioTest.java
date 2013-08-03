package mx.com.aon.portal.web.tests;

import java.util.List;

import com.opensymphony.module.sitemesh.Page;

import mx.com.aon.portal.model.AlertaUsuarioVO;
import mx.com.aon.portal.service.AlertasUsuarioManager;
import mx.com.aon.portal.service.MecanismosAlertasManager;
import mx.com.aon.portal.service.PagedList;

public class AlertasUsuarioTest extends AbstractTestCases {
	protected AlertasUsuarioManager alertasUsuarioManager;
	protected MecanismosAlertasManager mecanismosAlertasManager;
	
	
		public void testBuscarAlertasUsuario () throws Exception {
	try {
		PagedList pagedList = mecanismosAlertasManager.buscarMensajesAlertas("","", "", "", "", "", 0, 1);
		/*assertNotNull(pagedList);
		List res = pagedList.getItemsRangeList();
		for (int i=0; i<res.size(); i++) {
			System.out.println("DsNombre: " + (AlertaUsuarioVO)res.get(i));
		}*/
		} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}


/*	public void testBuscarAlertasUsuario () throws Exception {
		try {
			PagedList pagedList = alertasUsuarioManager.buscarAlertasUsuario("", "", "", "", "", "", 0, 10);
			assertNotNull(pagedList);
			/*List res = pagedList.getItemsRangeList();
			for (int i=0; i<res.size(); i++) {
				System.out.println("DsNombre: " + (AlertaUsuarioVO)res.get(i));
			}*/
/*		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	

/*	public void testGetAlertaUsuario () throws Exception {
		try {
			AlertaUsuarioVO alertaUsuarioVO = new AlertaUsuarioVO(); 
			alertaUsuarioVO = alertasUsuarioManager.getAlertaUsuario("11", "10000");
			System.out.println("Fecha: " + alertaUsuarioVO.getFeVencimiento());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
*/
	
	/* public void testBorrarAlertasUsuarioManager() throws Exception {
	    	try {
	    		this.alertasUsuarioManager.borrarAlerta("92","10540" ); 
			} catch (Exception e) {
				e.printStackTrace();
				throw e; 
			}
	    }*/

   /* public void testCadena() throws Exception {
           try {
               String cadena = "http://192.168.1.194:7778/AON_ARG/configurarCatalogosCompuestos/configuradorCatalogoCompuesto.action?cdPantalla=TCODIPOS";
                if (cadena.indexOf("?")>0) {
                 System.out.println("estoy aca");
                } else {
                    System.out.println("no esta");                      
                }
           } catch (Exception e) {
               e.printStackTrace();
               throw e;
           }
       }*/

}
