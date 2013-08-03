package mx.com.aon.catweb.configuracion.producto.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import mx.com.aon.catweb.configuracion.producto.model.ClavesVO;
import mx.com.aon.catweb.configuracion.producto.model.ListaDeValoresVO;
import mx.com.aon.catweb.configuracion.producto.model.LlaveValorVO;
import mx.com.aon.catweb.configuracion.producto.service.ListaDeValoresManager;
import mx.com.aon.catweb.configuracion.producto.tablaCincoClaves.model.DatosClaveAtributoVO;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;


public class ListaDeValoresManagerJdbcTemplateImpl extends AbstractManagerJdbcTemplateInvoke implements ListaDeValoresManager {
	
	private static Logger logger = Logger.getLogger(ListaDeValoresManagerJdbcTemplateImpl.class);
	
	/**
	 * Implementacion que extrae los catalogos de la lista de valores.
	 * 
	 * 
	 * @return catalogo List<ListaDeValoresVO> - Lista con la informacion de los
	 *         catalogos asociadas a la lista de valores.
	 *         
	 * @throws ApplicationException -
	 *             Es lanzada en errores de configuracion de aplicacion error en
	 *             las consultas a BD.
	 */
	@SuppressWarnings("unchecked")
	public List<ListaDeValoresVO> listaDeValoresCatalogo(String valor)
			throws ApplicationException {
		
		HashMap params = new HashMap();
		params.put("PV_OTTIPOTB_I", valor);
		 
		List<ListaDeValoresVO> catalogo = null;
	    try {
	    		catalogo = (List<ListaDeValoresVO>) getAllBackBoneInvoke( params, "OBTIENE_CATALOGO_LISTA_VALORES");
	        } catch (ApplicationException e) {
	        	throw new ApplicationException("Error regresando lista de valores del catalogo");
	        }
	   return catalogo;
	}

	public List<DatosClaveAtributoVO> consultaClaves(String nmTabla)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<DatosClaveAtributoVO> consultaDescripciones(String nmTabla)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public ListaDeValoresVO consultaTabla(String nmTabla)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public ClavesVO convierteAClaveVO(List<DatosClaveAtributoVO> dca)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<DatosClaveAtributoVO> convierteAListaDCAVO(ClavesVO clavesVO)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public void eliminarTablaClave(Map<String, String> params)
			throws ApplicationException {
		// TODO Auto-generated method stub
		
	}

	public void guardaClaveListaDeValores(ClavesVO clave,
			String tipoTransaccion, String nmTabla) throws ApplicationException {
		// TODO Auto-generated method stub
		
	}

	public void guardaDescripcionListaDeValores(
			List<ListaDeValoresVO> descripcion, String tipoTransaccion,
			String nmTabla) throws ApplicationException {
		// TODO Auto-generated method stub
		
	}

	public String guardaListaValores(ListaDeValoresVO lista)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<LlaveValorVO> guardaValoresCargaManual(List<LlaveValorVO> listaGrid, String nmTabla, List<LlaveValorVO> listaClavesDependencia , String cdTabla) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<LlaveValorVO> listaClavesDependencias(String codigoTabla,
			String langCode) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public PagedList listaValoresCargaManual(String tabla , int start , int limit)
			throws ApplicationException {

		HashMap map = new HashMap();
		map.put("PI_TABLA", tabla);
		map.put("PI_ATRIB_DESC", null);
		map.put("PI_NOM_CLAVE01", null);
		map.put("PI_NOM_CLAVE02", null);
		map.put("PI_VAL_CLAVE02", null);
		map.put("PI_NOM_CLAVE03", null);
		map.put("PI_VAL_CLAVE03", null);
		map.put("PI_NOM_CLAVE04", null);
		map.put("PI_VAL_CLAVE04", null);
		map.put("PI_NOM_CLAVE05", null);
		map.put("PI_VAL_CLAVE05", null);
		
        return pagedBackBoneInvoke(map, "OBTIENE_LISTA_CARGA_MANUAL", start, limit);
	}

	public ListaDeValoresVO obtieneCabeceraListaDeValores(String nmTabla)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public ListaDeValoresVO obtieneClaveListaDeValores(String nmTabla)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public ListaDeValoresVO obtieneDescripcionListaDeValores(String nmTabla)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public void eliminaValoresCargaManual(LlaveValorVO llaveEliminar, String cdTabla) throws ApplicationException {
		// TODO Auto-generated method stub
		
	}

}
