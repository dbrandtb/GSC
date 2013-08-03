package mx.com.aon.catweb.configuracion.producto.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.catweb.configuracion.producto.expresiones.model.ClaveVO;
import mx.com.aon.catweb.configuracion.producto.expresiones.model.ExpresionVO;
import mx.com.aon.catweb.configuracion.producto.expresiones.model.HojaVO;
import mx.com.aon.catweb.configuracion.producto.expresiones.model.RamaVO;
import mx.com.aon.catweb.configuracion.producto.expresiones.model.VariableVO;
import mx.com.aon.catweb.configuracion.producto.model.LlaveValorVO;
import mx.com.aon.catweb.configuracion.producto.service.ExpresionesManager;
import mx.com.aon.portal.util.WrapperResultados;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;

import mx.com.aon.core.ApplicationException;
import com.wittyconsulting.backbone.endpoint.Endpoint;
import com.wittyconsulting.backbone.exception.BackboneApplicationException;

import static mx.com.aon.portal.dao.ExpresionesDAO.OBTIENE_TABLA_CINCO_CLAVES;

public class ExpresionesManagerImpl extends AbstractManagerJdbcTemplateInvoke implements ExpresionesManager {

	private static Logger logger = Logger
			.getLogger(ExpresionesManagerImpl.class);
	/**
	 * Mapa en el cual se introducen los Manager's para ser extraidos y
	 * utilizados como servicios.
	 */
	private Map<String, Endpoint> endpoints;

	@SuppressWarnings("unchecked")
	public boolean agregarVariables(List<VariableVO> listaVariables,
			int codigoExpresion, String ottiporg) throws ApplicationException {
		Map params = new HashMap();
		params.put("CDEXPRES", codigoExpresion);
		params.put("LISTA_VARIABLES", listaVariables);
		
		
		logger.debug(" HHHH Los parametros para mandar a agregarVariables() son :"+ params);
		WrapperResultados mensaje = null;
		try {

			Endpoint manager = (Endpoint) endpoints.get("INSERTAR_LISTA_VARIABLES");
			mensaje = (WrapperResultados) manager.invoke(params);
			logger.debug("agrego bien las variables");
			
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'INSERTAR_LISTA_VARIABLES'", bae);
		}
		
		if( mensaje!= null && mensaje.getMsgTitle() != null){
		logger.debug("HHH MSGSS: "+mensaje.getMsgTitle());
			if("1".equals(mensaje.getMsgTitle()))return false;
		}
		
		return true;
	}

	@SuppressWarnings("unchecked")
	public int insertarExpresion(ExpresionVO expresionVO, boolean success)
			throws ApplicationException {
		int id = 0;
		try {

			Endpoint manager = (Endpoint) endpoints.get("INSERTAR_EXPRESION");
			id = (Integer) manager.invoke(expresionVO);
			logger.debug("Este es el id que regresa del endpoint" + id);
			if (id == 0 && expresionVO.getCodigoExpresion() !=0 ) {
				id = expresionVO.getCodigoExpresion();
			}
		} catch (BackboneApplicationException bae) {
			success = false;
			logger.error("Exception in invoke 'INSERTAR_EXPRESION'", bae);
			throw new ApplicationException(
					"Error al insertar los datos de la expresion en el sistema");
		}
		return id;
	}
	
	@SuppressWarnings("unchecked")
	public int obtieneSecuenciaExpresion() throws ApplicationException {
		int id = 0;
		try {

			Endpoint manager = (Endpoint) endpoints.get("OBTIENE_SECUENCIA_EXPRESION");
			id = (Integer) manager.invoke(null);
			logger.debug("Este es el id que regresa del endpoint" + id);
			
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'OBTIENE_SECUENCIA_EXPRESION'", bae);
			throw new ApplicationException("Error al obtener la secuencia de la expresion del sistema");
		}
		return id;
	}

	@SuppressWarnings("unchecked")
	public boolean agregarClave(List<ClaveVO> listaClaves, int codigoExpresion,
			String codigoVariable, String ottiporg) throws ApplicationException {
		boolean success = true;
		logger.debug("Ese codigo es para cada variable");
		Map params = new HashMap();
		params.put("CDEXPRES", codigoExpresion);
		params.put("CDVARIABLE", codigoVariable);
		params.put("OTTIPORG", ottiporg);
		params.put("OTTIPEXP", "K");//Key. Claves de las variables
		
		
		for(ClaveVO clave : listaClaves){
			
			params.put("EXPRESION", clave.getExpresion());
			params.put("RECALCULAR", clave.getRecalcular());
			params.put("CODIGO_SECUENCIA", clave.getCodigoSecuencia());
			params.put("CODIGO_EXPRESION_KEY", clave.getCodigoExpresionKey());
			
			WrapperResultados mensaje = null;
			logger.debug(" HHHH Los parametros para mandar a agregarClave() son :"+ params);
			try {
	
				Endpoint manager = (Endpoint) endpoints.get("INSERTAR_LISTA_CLAVES");
				mensaje = (WrapperResultados) manager.invoke(params);
				logger.debug("agrego bien las claves");
	
			} catch (BackboneApplicationException bae) {
				success = false;
				logger.error("Exception in invoke 'INSERTAR_LISTA_CLAVES'", bae);
			}
			logger.debug("MESAJE para: "+clave.getCodigoSecuencia()+" es: "+mensaje.getMsgTitle());
			if( mensaje!= null && mensaje.getMsgTitle() != null){
				if("1".equals(mensaje.getMsgTitle())){
					success = false;
					break;
				}
			}
		}
		return success;
	}

	@SuppressWarnings("unchecked")
	public void eliminarVariableLocalExpresion(Map<String, String> params) throws ApplicationException {
		logger.debug("fuera del try");
		try {
			logger.debug("dentro del manager");
			Endpoint manager = (Endpoint) endpoints.get("ELIMINAR_VARIABLE_LOCAL_EXPRESION");
			manager.invoke(params);
			logger.debug("elimino bien");
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'ELIMINAR_VARIABLE_LOCAL_EXPRESION'", bae);
			throw new ApplicationException("Error al eliminar la variable local de la expresion del sistema");
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<HojaVO> funcionesArbol() throws ApplicationException {
		List<HojaVO> listaFuncionesJson = new ArrayList<HojaVO>();

		try {
			Endpoint manager = (Endpoint) endpoints.get("LISTA_FUNCIONES");
			listaFuncionesJson = (ArrayList<HojaVO>) manager.invoke(null);
		} catch (BackboneApplicationException e) {
			e.printStackTrace();
		}

		return listaFuncionesJson;
	}

	@SuppressWarnings("unchecked")
	public List<HojaVO> variablesTemporalesArbol() throws ApplicationException {
		List<HojaVO> listaVariablesTemporalesJson = new ArrayList<HojaVO>();

		try {
			Endpoint manager = (Endpoint) endpoints.get("LISTA_VARIABLES_TEMPORALES");
			listaVariablesTemporalesJson = (ArrayList<HojaVO>) manager.invoke(null);
		} catch (BackboneApplicationException e) {
			e.printStackTrace();
		}

		return listaVariablesTemporalesJson;
	}

	@SuppressWarnings("unchecked")
	public List<RamaVO> camposDelProductoArbol() throws ApplicationException {
		List<RamaVO> ramas = new ArrayList<RamaVO>();
		RamaVO rama = null;
		List<HojaVO> listaCamposDelProductoJson = null;
		for (int j = 0; j < 5; j++) {
			listaCamposDelProductoJson = new ArrayList<HojaVO>();
			rama = new RamaVO();
			rama.setAllowDelete(false);
			rama.setCls("file");
			rama.setExpanded(true);
			rama.setHref("../definicion/PrincipalProductos.action");
			rama.setHrefTarget("target");
			rama.setId("id"+j);
			rama.setQtip("hey"+j);
			rama.setQtipTitle("tile"+j);
			rama.setText("text"+j);
			
			HojaVO hojaVO = null;
			for (int i = 0; i < 10; i++) {
				hojaVO = new HojaVO();
				hojaVO.setCls("file");
				hojaVO.setId("id-" + Integer.toString(i));
				hojaVO.setLeaf(true);
				hojaVO.setText("Menu campos " + Integer.toString(i));
				hojaVO.setDescripcion("Descripcion de " + hojaVO.getText());
				hojaVO.setFuncion("campos" + Integer.toString(i));
				listaCamposDelProductoJson.add(hojaVO);
			}
			rama.setChildren(listaCamposDelProductoJson.toArray());
			ramas.add(rama);
		}
		return ramas;
	}

	/**
	 * Método para obtener los valores de las tablas en la pantalla Wizard de Parametrización, 
	 * pestaña Catálogos, opción Tablas de Apoyo, opción Tabla Cinco Valores. 
	 * @param start
	 * @param limit
	 * @return pagedBackBoneInvoke(map, endpointName, start, limit)
	 * @author augusto.perez
	 */
	@SuppressWarnings("unchecked")
	public PagedList listaTabla(int start, int limit) throws ApplicationException {
		Map map = new HashMap();
		
        String endpointName = OBTIENE_TABLA_CINCO_CLAVES;
        return pagedBackBoneInvoke(map, endpointName, start, limit);
	}

	@SuppressWarnings("unchecked")
	public List<LlaveValorVO> listaColumna(String idTabla)
			throws ApplicationException {
		List<LlaveValorVO> listaColumnaJson = null;
		try {
			Endpoint manager = (Endpoint) endpoints.get("LISTA_COLUMNA");
			listaColumnaJson = (List<LlaveValorVO>) manager.invoke(idTabla);
		} catch (BackboneApplicationException e) {
			e.printStackTrace();
		}

		return listaColumnaJson;
	}

	@SuppressWarnings("unchecked")
	public List<ClaveVO> listaClave(String idTabla) throws ApplicationException {
		logger.debug("Entro a expresionesManagerImpl ==>lista clave + idTabla="
				+ idTabla);
		List<ClaveVO> listaClaveJson = null;
		try {
			Endpoint manager = (Endpoint) endpoints.get("LISTA_CLAVE");
			listaClaveJson = (List<ClaveVO>) manager.invoke(idTabla);
			if(listaClaveJson != null && !listaClaveJson.isEmpty() ){
				//List<Iteger> clavesARemover = new ArrayList<Integer>();
				List<ClaveVO> claves = new ArrayList<ClaveVO>();
				for(ClaveVO cvo : listaClaveJson){
					if( !( cvo.getClave() != null && StringUtils.isNotBlank( cvo.getClave() ) ) ){
						claves.add(cvo);
						//clavesARemover.add(Integer.parseInt(cvo.getCodigoSecuencia()));
					}					
				}
				if(!claves.isEmpty())
					listaClaveJson.removeAll(claves);
			}

		} catch (BackboneApplicationException e) {
			e.printStackTrace();
		}

		logger.debug("listaClaveJson="+listaClaveJson);
		logger.debug("listaClaveJson.isEmpty()="+listaClaveJson.isEmpty());
		return listaClaveJson;
	}

	@SuppressWarnings("unchecked")
	public ClaveVO obtieneClave(String dsclave)
			throws BackboneApplicationException {
		ClaveVO cVO = null;

		if (cVO == null) {
			cVO = new ClaveVO();
			cVO.setClave(dsclave);
			cVO.setExpresion("");
			cVO.setRecalcular("N");
		}
		logger.debug("obtieneClave + cVO.getClave" + cVO.getClave());
		return cVO;
	}

	@SuppressWarnings("unchecked")
	public List<ClaveVO> obtieneClaves(VariableVO variableLocal)throws ApplicationException{
		List<ClaveVO> result =null;
		try{
			Endpoint manager=(Endpoint) endpoints.get("CLAVES_VARIABLE_LOCAL");
		 	 result= (List<ClaveVO>)manager.invoke(variableLocal);
		}catch(BackboneApplicationException bea){
			
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<VariableVO> listaComboVariables() throws ApplicationException {
		List<VariableVO> listaComboVariables = new ArrayList<VariableVO>();
		return listaComboVariables;
	}

	@SuppressWarnings("unchecked")
	public List<ClaveVO> listaPropertyGrid() throws ApplicationException {
		List<ClaveVO> listaPropertyGridJson = new ArrayList<ClaveVO>();

		ClaveVO variableVO = new ClaveVO();
		variableVO.setClave("1");
		variableVO.setExpresion("2");
		variableVO.setRecalcular("S");

		listaPropertyGridJson.add(variableVO);
		return listaPropertyGridJson;
	}

	@SuppressWarnings("unchecked")
	public List<ExpresionVO> obtieneExpresion(int codigoExpresion) {
		List<ExpresionVO> listaExpresion = new ArrayList<ExpresionVO>();
		
		try {
			Endpoint manager = (Endpoint) endpoints.get("OBTIENE_EXPRESION");
			listaExpresion = (ArrayList<ExpresionVO>) manager.invoke(codigoExpresion);
			if(listaExpresion!=null && !listaExpresion.isEmpty()){
				if(listaExpresion.get(0).getSwitchRecalcular()==null)
					listaExpresion.get(0).setSwitchRecalcular("N");
			}
		}catch (BackboneApplicationException e) {
			e.printStackTrace();
		}
		return listaExpresion;
	}
	
	@SuppressWarnings("unchecked")
	public List<VariableVO> obtieneVariableExpresion(int codigoExpresion) throws ApplicationException {
		List<VariableVO> listaVariableExpresion = new ArrayList<VariableVO>();
		
		try {
			Endpoint manager = (Endpoint) endpoints.get("OBTIENE_VARIABLE_EXPRESION");
			listaVariableExpresion = (ArrayList<VariableVO>) manager.invoke(codigoExpresion);
			if(listaVariableExpresion!=null && !listaVariableExpresion.isEmpty()){
				for(VariableVO v:listaVariableExpresion){
					List<LlaveValorVO> listaColumnas = listaColumna(v.getTabla());
					for(LlaveValorVO columna : listaColumnas){
						if(columna.getValue().equals(v.getDescripcionColumna())){
							v.setColumna(Integer.parseInt(columna.getKey()));
						}
					}
				}
			}
				
		}catch (BackboneApplicationException e) {
			e.printStackTrace();
		}
		return listaVariableExpresion;
	}

	/**
	 * 
	 * @param codigoExpresion
	 * @param expresion
	 * @param tipoExpresion
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public WrapperResultados validarExpresion(int codigoExpresion, String expresion, String tipoExpresion , String cdVariable) throws Exception {
		
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("expresion", expresion);
		params.put("codigoExpresion", codigoExpresion + "");
		params.put("tipoExpresion", tipoExpresion);
		params.put("cdVariable", cdVariable);
		
		WrapperResultados mensaje = null;
		try {
			
			Endpoint manager = (Endpoint) endpoints.get("VALIDA_EXPRESION");
			mensaje = (WrapperResultados)manager.invoke(params);
			
		} catch (BackboneApplicationException e) {
			logger.error("Error al validar la expresion");
			throw new Exception("Validacion de expresiones", e);
		}
		return mensaje;
	}
	
	@SuppressWarnings("unchecked")
	public boolean existeExpresion(String codigoExpresion) throws Exception {
		List<ExpresionVO> listaExpresion = new ArrayList<ExpresionVO>();
		boolean existeExpresion = false;
		try {
			Endpoint manager = (Endpoint) endpoints.get("OBTIENE_EXPRESION");
			listaExpresion = (ArrayList<ExpresionVO>) manager.invoke(codigoExpresion);
			if(listaExpresion!=null && !listaExpresion.isEmpty()){
				existeExpresion = true;
				logger.debug("La expresion con codigo " + codigoExpresion + " SI existe en BD");
			}else{
				logger.debug("La expresion con codigo " + codigoExpresion + " NO existe en BD");
			}
		}catch (BackboneApplicationException e) {
			e.printStackTrace();
		}
		return existeExpresion;
	}
	
	/**
	 * @param endpoints
	 *            the endpoints to set
	 */
	public void setEndpoints(Map<String, Endpoint> endpoints) {
		this.endpoints = endpoints;
	}

}