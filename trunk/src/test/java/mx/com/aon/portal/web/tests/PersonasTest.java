package mx.com.aon.portal.web.tests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONFunction;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONBuilder;

import mx.com.aon.portal.model.DomicilioVO;
import mx.com.aon.portal.model.ExtJSComboBoxVO;
import mx.com.aon.portal.model.ExtJSFieldVO;
import mx.com.aon.portal.model.ExtJSTextFieldVO;
import mx.com.aon.portal.model.PersonaVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.PersonasManager;

public class PersonasTest extends AbstractTestCases {
	protected PersonasManager personasManager;

/*	public void testBuscarPersonas () throws Exception {
		try {
			personasManager.buscarPersonas("", "", "", "", 0, 10);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
*/
/*	public void testActualizarPersona () throws Exception{
		try {
			personasManager.guardarDatosGenerales("37533", "F", "ALVARITO", "RAMIREZ", "MONGE", "H", "", "01/02/2003", "");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
*/
/*	public void testActualizaDomicilio () throws Exception {
		try {
			personasManager.guardarDomicilios("37533", "1", "1", "No se", "5400", "", "", "", "", "", "");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
*/
/*	public void testActualizaTelefono () throws Exception {
		try {
			personasManager.guardarTelefonos("37533", "1", "", "0264", "4222211", "120");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
*/
/*	public void testActualizarCorporativo () throws Exception {
		try {
			personasManager.guardarCorporativo("37533", "", "1", "1", "01/11/2007", "25/11/2008", "1");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
*/
/*	public void testObtenerPersona () throws Exception {
		try {
			PersonaVO personaVO = personasManager.obtenerPersona("F", "37533");
			System.out.println("Nombre: " + personaVO.getDsNombre());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
*/
/*	public void testObtenerDomicilios () throws Exception {
		try {
			PagedList pagedList = personasManager.getDomicilios("37533", 0, 10);
			List lista = pagedList.getItemsRangeList();
			for (int i=0; i<lista.size(); i++) {
				DomicilioVO domicilioVO = (DomicilioVO)lista.get(i);
				System.out.println("Calle: " + domicilioVO.getDsDomicilio());
			}			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
*/
	@SuppressWarnings("unchecked")
	public void testObtenerDatosAdicionales () throws Exception {
		try {
			Map map = new HashMap();
			String function = new String("crearStoreDatosAdicionales2('115TFOND')");
			map.put("func", new JSONBuilder(null).object().key("store").value(function).endObject());
			JSONObject jsObject = JSONObject.fromObject(map);
			//JSONFunction jsFunction = JSONFunction().parse(function);


			
			System.out.println("F@cking Object:\n" + jsObject);
			/*PagedList pagedList = personasManager.getDatosAdicionales("F", "", "6", 0, 10);
			List lista = pagedList.getItemsRangeList();
			for (int i=0; i<lista.size(); i++) {
				ExtJSFieldVO extJSFieldVO = (ExtJSFieldVO)lista.get(i);
				System.out.println(JSONObject.fromObject(extJSFieldVO).toString());
				//System.out.println("Objeto: " + extJSTextFieldVO.toString());
			}*/
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
