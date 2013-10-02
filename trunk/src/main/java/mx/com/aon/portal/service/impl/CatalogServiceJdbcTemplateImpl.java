package mx.com.aon.portal.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.portal.service.CatalogServiceJdbcTemplate;
import mx.com.gseguros.exception.ApplicationException;

public class CatalogServiceJdbcTemplateImpl extends AbstractManagerJdbcTemplateInvoke implements CatalogServiceJdbcTemplate {

	@SuppressWarnings("unchecked")
	public List getItemList(String endpointName, String itemId) throws ApplicationException {
		HashMap map = new HashMap();
        map.put("pv_cdusuario", itemId);
        return  getAllBackBoneInvoke(map, endpointName);
	}
	
	@SuppressWarnings("unchecked")
	public List getItemList( String endpointName, Map<String,Object> parameters)throws ApplicationException {
		return  getAllBackBoneInvoke(parameters, endpointName);
	}

}
