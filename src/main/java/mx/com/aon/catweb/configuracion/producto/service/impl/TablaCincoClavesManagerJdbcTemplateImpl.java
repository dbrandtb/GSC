package mx.com.aon.catweb.configuracion.producto.service.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import mx.com.aon.catweb.configuracion.producto.service.TablaCincoClavesManagerJdbcTemplate;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.util.ConvertUtil;
import mx.com.aon.portal.util.WrapperResultados;


/**
 * 
 * Implementacion de TablaCincoClavesManagerJdbcTemplate para petición de información valores para la tabla de cinco claves para el uso de JdbcTemplate
 *
 */
public class TablaCincoClavesManagerJdbcTemplateImpl extends AbstractManagerJdbcTemplateInvoke implements TablaCincoClavesManagerJdbcTemplate {
	
	@SuppressWarnings("unchecked")
	public PagedList obtieneValoresClave(String nmTabla , int start , int limit) throws ApplicationException {

		HashMap map = new HashMap();
		map.put("PV_NMTABLA_I", nmTabla);
		
        return pagedBackBoneInvoke(map, "OBTIENE_VALORES_CLAVE", start, limit);
	}
	
	public String borraValoresClave(String nmTabla , String clave1, String clave2, String clave3, String clave4, String clave5) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("pi_tabla", nmTabla);
		map.put("pi_clave01", clave1);
		map.put("pi_clave02", clave2);
		map.put("pi_clave03", clave3);
		map.put("pi_clave04", clave4);
		map.put("pi_clave05", clave5);
		
		WrapperResultados res = returnBackBoneInvoke(map, "BORRA_VALORES_CLAVE");
		if(res==null || StringUtils.isBlank(res.getMsgText()))return "Error al intentar borrar el registro, consulte a su soporte";
        return res.getMsgText();
	
	}
}


