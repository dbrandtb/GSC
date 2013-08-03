package mx.com.aon.catbo.service.impl;
import java.util.HashMap;
import mx.com.aon.catbo.model.PersonaVO;
import mx.com.aon.catbo.service.AltaCasosManager;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;


public class AltaCasosManagerImpl extends AbstractManagerJdbcTemplateInvoke implements AltaCasosManager {

	@SuppressWarnings("unchecked")
	public PersonaVO obtenerPersonCaso(String pv_cdperson_i)throws ApplicationException {
		HashMap map= new HashMap();
		map.put("pv_cdperson_i",pv_cdperson_i);
		String endpoindName = "OBTIENE_PERSONA_CASO";
		
		return (PersonaVO)getBackBoneInvoke(map, endpoindName );
	}
	
}