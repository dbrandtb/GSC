package mx.com.aon.catbo.service.impl;

import java.util.HashMap;
import java.util.Map;

import mx.com.aon.catbo.service.ConfiguracionMailManager;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;
import mx.com.aon.portal.util.WrapperResultados;

import org.apache.commons.lang.StringUtils;

public class ConfiguracionMailManagerImpl extends AbstractManagerJdbcTemplateInvoke implements ConfiguracionMailManager{
	private static final String BUSCAR_PARAMETROS = "BUSCAR_PARAMETROS";

	public String[] obtieneConfMail() throws ApplicationException {
		String[] valores = null;
		Map<String, String> map = new HashMap<String, String>();
		map.put("pi_nbparam", "HOST_EMAIL|FROM_EMAIL|USER_EMAIL|PASS_EMAIL");
		WrapperResultados resultados = returnResult(map,BUSCAR_PARAMETROS);
		//WrapperResultados resultados = (WrapperResultados) getBackBoneInvoke(map, "BUSCAR_PARAMETROS");
		valores = StringUtils.split(resultados.getItemMap().get("pi_valor").toString(), "|");
		
		return valores;
	}
}
