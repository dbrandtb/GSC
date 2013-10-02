/**
 * 
 */
package mx.com.aon.catweb.configuracion.producto.service.impl;

import static mx.com.aon.catweb.configuracion.producto.dao.ProductoDAO.BUSCAR_PRODUCTOS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.catweb.configuracion.producto.expresiones.model.RamaVO;
import mx.com.aon.catweb.configuracion.producto.service.TreeManager;
import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;
import mx.com.aon.tmp.BackboneApplicationException;
import mx.com.aon.tmp.Endpoint;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.log4j.Logger;

/**
 * @author Adolfo
 * 
 */
public class TreeManagerImpl extends AbstractManagerJdbcTemplateInvoke implements TreeManager {

	/**
	 * Logger de la clase.
	 */
	private static Logger logger = Logger.getLogger(TreeManagerImpl.class);

	private Map<String, Endpoint> endpoints;

	/**
	 * @return the endpoints
	 */
	public Map<String, Endpoint> getEndpoints() {
		return endpoints;
	}

	/**
	 * @param endpoints
	 *            the endpoints to set
	 */
	public void setEndpoints(Map<String, Endpoint> endpoints) {
		this.endpoints = endpoints;
	}

	private List<RamaVO> crearObjetos(int nivel, String tipoObjeto,
			List<RamaVO> ramas) {

		List<RamaVO> resultado = new ArrayList<RamaVO>();
		int contador = 0;
		for (RamaVO ramaVO : ramas) {

			ramaVO.setQtip(ramaVO.getCodigoObjeto());
			ramaVO.setQtipTitle(ramaVO.getText());
			ramaVO.setNivel(nivel);
			ramaVO.setTipoObjeto(tipoObjeto);
			ramaVO.setChildren(new RamaVO[0]);
			ramaVO.setPosicion(contador);
			resultado.add(ramaVO);
			contador++;
		}

		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.biosnet.ice.wizard.productos.service.TreeManager#obtenerProductos()
	 */
	public List<RamaVO> obtenerProductos() throws ApplicationException {
		List<RamaVO> resultado = new ArrayList<RamaVO>();

		resultado = getAllBackBoneInvoke(new HashMap(), BUSCAR_PRODUCTOS);
		
		return crearObjetos(1, "Producto", resultado);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.biosnet.ice.wizard.productos.service.TreeManager#obtenerNiveles(java.lang.String,
	 *      java.util.ArrayList)
	 */
	public List<RamaVO> obtenerNiveles(String tipoObjeto,
			ArrayList<String> parametros) throws ApplicationException {
		int nivel = 3;
		List<RamaVO> resultado = new ArrayList<RamaVO>();

		Map<String, String> params = new HashMap<String, String>();
		params.put("codigoProducto", parametros.get(0));
		String vm = null;
		try {
			if ("AtributosVariables".equals(tipoObjeto)) {
				if (parametros.size() == 1)
					vm = "OBTENER_RAMA_ATRIBUTOS_PRODUCTO";
				else if (parametros.size() == 2) {
					vm = "OBTENER_ATRIBUTOS_SITUACION";
					params.put("codigoSituacion", parametros.get(1));
					nivel = 5;
				}
				else if (parametros.size() == 4) {
					vm = "OBTENER_ATRIBUTOS_GARANTIA";
					params.put("codigoSituacion", parametros.get(1));
//					params.put("codigoPlan", parametros.get(2));
					params.put("codigoGarantia", parametros.get(3));
					
					logger.debug("SITU: " + parametros.get(1));
					logger.debug("GARA: " + parametros.get(3));
					
					
					nivel = 9;
					/*resultado = new ArrayList<RamaVO>();
					RamaVO rama= null;
					for(int i = 0; i<5 ; i++){
						rama= new RamaVO();
						rama.setText("Atributo Variable"+i);
						rama.setCodigoObjeto(Integer.toString(i));
						resultado.add(rama);
					}
					nivel = 9;
					*/
				}
			}else
			if ("ConceptoGlobal".equals(tipoObjeto)) {
				vm = "OBTENER_RAMA_CONCEPTOS_GLOBALES_PRODUCTO";
			}else
			if ("Inciso".equals(tipoObjeto)) {
				vm = "OBTENER_RAMA_SITUACION";
			}else
			if ("DatosFijos".equals(tipoObjeto)) {
				vm = "OBTENER_RAMA_DATOS_FIJOS_PRODUCTO";
				
			}else
			if ("Rol".equals(tipoObjeto)) {

				if (parametros.size() == 1)
					params.put("nivel", "0");
				else if (parametros.size() == 2) {
					params.put("nivel", "1");
					params.put("codigoSituacion", parametros.get(1));
					nivel = 5;
				} /*else if (parametros.size() == 3)
					params.put("nivel", "2");*/

				vm = "OBTENER_RAMA_ROLES_PRODUCTO";
			}else
			if ("Cobertura".equals(tipoObjeto)) {
				logger.debug("ENTRA A COBERTURA");
				params.put("codigoSituacion", parametros.get(1));
				params.put("codigoPlan", parametros.get(2));
				vm = "OBTENER_GARANTIA";
				//nivel = 5;
				/*resultado = new ArrayList<RamaVO>();
				RamaVO rama= null;
				for(int i = 0; i<5 ; i++){
					rama= new RamaVO();
					rama.setText("cobertura"+i);
					rama.setCodigoObjeto(Integer.toString(i));
					resultado.add(rama);
				}*/
				nivel = 7;
			}else if("Planes".equals(tipoObjeto)){	
				logger.debug("entro al if de planes");
				vm = "OBTIENE_LISTA_PLANES_RAMAS";
				params.put("codigoSituacion", parametros.get(1));				
				nivel = 5;
			} else if ("Objeto".equals(tipoObjeto)) {
				vm = "OBTENER_OBJETOS";
				params.put("codigoSituacion", parametros.get(1));
				nivel = 5;
			}
			
			
			if (vm != null) {
				Endpoint manager = (Endpoint) endpoints.get(vm);
				resultado = (ArrayList<RamaVO>) manager.invoke(params);
			}

		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'OBTENER_RAMA_SITUACION'", bae);
			throw new ApplicationException(
					"Error al cargar los tipos de seguro desde el sistema");
		}

		return crearObjetos(nivel, tipoObjeto, resultado);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.biosnet.ice.wizard.productos.service.TreeManager#obtenerEstructuraProducto()
	 */
	public List<RamaVO> obtenerEstructuraProducto() throws ApplicationException {
		List<RamaVO> resultado = new ArrayList<RamaVO>();

		RamaVO ramaVO = new RamaVO();

		ramaVO.setId("");
		ramaVO.setCodigoObjeto("");
		ramaVO.setText("Riesgo");
		ramaVO.setQtip(ramaVO.getCodigoObjeto());
		ramaVO.setQtipTitle(ramaVO.getText());
		ramaVO.setNivel(2);
		ramaVO.setPosicion(0);
		ramaVO.setTipoObjeto("Inciso");
		ramaVO.setChildren(new RamaVO[0]);
		resultado.add(ramaVO);

		ramaVO = new RamaVO();
		ramaVO.setId("");
		ramaVO.setCodigoObjeto("");
		ramaVO.setText("Datos Variables");
		ramaVO.setQtip(ramaVO.getCodigoObjeto());
		ramaVO.setQtipTitle(ramaVO.getText());
		ramaVO.setNivel(2);
		ramaVO.setPosicion(1);
		ramaVO.setTipoObjeto("AtributosVariables");
		ramaVO.setChildren(new RamaVO[0]);
		resultado.add(ramaVO);

		ramaVO = new RamaVO();
		ramaVO.setId("");
		ramaVO.setCodigoObjeto("");
		ramaVO.setText("Datos Fijos");
		ramaVO.setQtip(ramaVO.getCodigoObjeto());
		ramaVO.setQtipTitle(ramaVO.getText());
		ramaVO.setNivel(2);
		ramaVO.setPosicion(2);
		ramaVO.setTipoObjeto("DatosFijos");
		ramaVO.setChildren(new RamaVO[0]);
		resultado.add(ramaVO);

		ramaVO = new RamaVO();
		ramaVO.setId("");
		ramaVO.setCodigoObjeto("");
		ramaVO.setText("Conceptos Globales");
		ramaVO.setQtip(ramaVO.getCodigoObjeto());
		ramaVO.setQtipTitle(ramaVO.getText());
		ramaVO.setNivel(2);
		ramaVO.setPosicion(3);
		ramaVO.setTipoObjeto("ConceptoCobertura");
		ramaVO.setChildren(new RamaVO[0]);
		resultado.add(ramaVO);

		ramaVO = new RamaVO();
		ramaVO.setId("");
		ramaVO.setCodigoObjeto("");
		ramaVO.setText("Roles");
		ramaVO.setQtip(ramaVO.getCodigoObjeto());
		ramaVO.setQtipTitle(ramaVO.getText());
		ramaVO.setNivel(2);
		ramaVO.setPosicion(4);
		ramaVO.setTipoObjeto("Rol");
		ramaVO.setChildren(new RamaVO[0]);
		resultado.add(ramaVO);
		
		/*ramaVO = new RamaVO();
		ramaVO.setId("");
		ramaVO.setCodigoObjeto("");
		ramaVO.setText("Suma Asegurada");
		ramaVO.setQtip(ramaVO.getCodigoObjeto());
		ramaVO.setQtipTitle(ramaVO.getText());
		ramaVO.setNivel(2);
		ramaVO.setPosicion(5);
		ramaVO.setTipoObjeto("SumaAsegurada");
		ramaVO.setChildren(new RamaVO[0]);
		resultado.add(ramaVO);
		*/
		
		ramaVO = new RamaVO();
		ramaVO.setId("");
		ramaVO.setCodigoObjeto("");
		ramaVO.setText("Variables Temporales Por Producto");
		ramaVO.setQtip(ramaVO.getCodigoObjeto());
		ramaVO.setQtipTitle(ramaVO.getText());
		ramaVO.setNivel(2);
		ramaVO.setPosicion(6);
		ramaVO.setTipoObjeto("VariableTemporalProducto");
		ramaVO.setChildren(new RamaVO[0]);
		resultado.add(ramaVO);
		
		ramaVO = new RamaVO();
		ramaVO.setId("");
		ramaVO.setCodigoObjeto("");
		ramaVO.setText("Reglas De Validaci\u00F3n");
		ramaVO.setQtip(ramaVO.getCodigoObjeto());
		ramaVO.setQtipTitle(ramaVO.getText());
		ramaVO.setNivel(2);
		ramaVO.setPosicion(7);
		ramaVO.setTipoObjeto("ReglaValidacion");
		ramaVO.setChildren(new RamaVO[0]);
		resultado.add(ramaVO);
		
		return resultado;
	}

	public List<RamaVO> obtenerEstructuraSituacion()
			throws ApplicationException {
		List<RamaVO> resultado = new ArrayList<RamaVO>();

		RamaVO ramaVO = new RamaVO();
/*
		ramaVO.setId("");
		ramaVO.setCodigoObjeto("");
		ramaVO.setText("Coberturas");
		ramaVO.setQtip(ramaVO.getCodigoObjeto());
		ramaVO.setQtipTitle(ramaVO.getText());
		ramaVO.setNivel(4);
		ramaVO.setPosicion(0);
		ramaVO.setTipoObjeto("Cobertura");
		ramaVO.setChildren(new RamaVO[0]);
		resultado.add(ramaVO);

		ramaVO = new RamaVO();
		*/
		ramaVO.setId("");
		ramaVO.setCodigoObjeto("");
		ramaVO.setText("Datos Variables");
		ramaVO.setQtip(ramaVO.getCodigoObjeto());
		ramaVO.setQtipTitle(ramaVO.getText());
		ramaVO.setNivel(4);
		ramaVO.setPosicion(1);
		ramaVO.setTipoObjeto("AtributosVariables");
		ramaVO.setChildren(new RamaVO[0]);
		resultado.add(ramaVO);
		
		ramaVO = new RamaVO();
		ramaVO.setId("");
		ramaVO.setCodigoObjeto("");
		ramaVO.setText("Planes");
		ramaVO.setQtip(ramaVO.getCodigoObjeto());
		ramaVO.setQtipTitle(ramaVO.getText());
		ramaVO.setNivel(4);
		ramaVO.setPosicion(2);
		ramaVO.setTipoObjeto("Planes");
		ramaVO.setChildren(new RamaVO[0]);
		resultado.add(ramaVO);
		
		ramaVO = new RamaVO();
		ramaVO.setId("");
		ramaVO.setCodigoObjeto("");
		ramaVO.setText("Roles");
		ramaVO.setQtip(ramaVO.getCodigoObjeto());
		ramaVO.setQtipTitle(ramaVO.getText());
		ramaVO.setNivel(4);
		ramaVO.setPosicion(2);
		ramaVO.setTipoObjeto("Rol");
		ramaVO.setChildren(new RamaVO[0]);
		resultado.add(ramaVO);

		ramaVO = new RamaVO();
		ramaVO.setId("");
		ramaVO.setCodigoObjeto("");
		ramaVO.setText("Objetos");
		ramaVO.setQtip(ramaVO.getCodigoObjeto());
		ramaVO.setQtipTitle(ramaVO.getText());
		ramaVO.setNivel(4);
		ramaVO.setPosicion(2);
		ramaVO.setTipoObjeto("Objeto");
		ramaVO.setChildren(new RamaVO[0]);
		resultado.add(ramaVO);

		/*ramaVO = new RamaVO();
		ramaVO.setId("");
		ramaVO.setCodigoObjeto("");
		ramaVO.setText("Suma Asegurada");
		ramaVO.setQtip(ramaVO.getCodigoObjeto());
		ramaVO.setQtipTitle(ramaVO.getText());
		ramaVO.setNivel(4);
		ramaVO.setPosicion(2);
		ramaVO.setTipoObjeto("SumaAsegurada");
		ramaVO.setChildren(new RamaVO[0]);
		resultado.add(ramaVO);
		*/
		
		return resultado;
	}

	public List<RamaVO> obtenerEstructuraObjetos()
			throws ApplicationException {
		List<RamaVO> resultado = new ArrayList<RamaVO>();

		RamaVO ramaVO = new RamaVO();

		ramaVO = new RamaVO();
		ramaVO.setId("");
		ramaVO.setCodigoObjeto("");
		ramaVO.setText("Datos Variables");
		ramaVO.setQtip(ramaVO.getCodigoObjeto());
		ramaVO.setQtipTitle(ramaVO.getText());
		ramaVO.setNivel(6);
		ramaVO.setPosicion(0);
		ramaVO.setTipoObjeto("AtributosVariables");
		ramaVO.setChildren(new RamaVO[0]);
		resultado.add(ramaVO);

		return resultado;
	}

	public List<RamaVO> obtenerEstructuraCobertura()
			throws ApplicationException {
		List<RamaVO> resultado = new ArrayList<RamaVO>();

		RamaVO ramaVO = new RamaVO();

		ramaVO = new RamaVO();
		ramaVO.setId("");
		ramaVO.setCodigoObjeto("");
		ramaVO.setText("Datos Variables");
		ramaVO.setQtip(ramaVO.getCodigoObjeto());
		ramaVO.setQtipTitle(ramaVO.getText());
		ramaVO.setNivel(8);
		ramaVO.setPosicion(0);
		ramaVO.setTipoObjeto("AtributosVariables");
		ramaVO.setChildren(new RamaVO[0]);
		resultado.add(ramaVO);

		ramaVO = new RamaVO();
		ramaVO.setId("");
		ramaVO.setCodigoObjeto("");
		ramaVO.setText("Suma Asegurada");
		ramaVO.setQtip(ramaVO.getCodigoObjeto());
		ramaVO.setQtipTitle(ramaVO.getText());
		ramaVO.setNivel(8);
		ramaVO.setPosicion(2);
		ramaVO.setTipoObjeto("SumaAsegurada");
		ramaVO.setChildren(new RamaVO[0]);
		resultado.add(ramaVO);
				
		ramaVO = new RamaVO();
		ramaVO.setId("");
		ramaVO.setCodigoObjeto("");
		ramaVO.setText("Conceptos Por Cobertura");
		ramaVO.setQtip(ramaVO.getCodigoObjeto());
		ramaVO.setQtipTitle(ramaVO.getText());
		ramaVO.setNivel(8);
		ramaVO.setPosicion(1);
		ramaVO.setTipoObjeto("ConceptoCobertura");
		ramaVO.setChildren(new RamaVO[0]);
		resultado.add(ramaVO);
		
		return resultado;
	}

	public List<RamaVO> obtenerEstructuraPlanes()
			throws ApplicationException {
		List<RamaVO> resultado = new ArrayList<RamaVO>();

		RamaVO ramaVO = new RamaVO();

		ramaVO = new RamaVO();
		ramaVO.setId("");
		ramaVO.setCodigoObjeto("");
		ramaVO.setText("Coberturas");
		ramaVO.setQtip(ramaVO.getCodigoObjeto());
		ramaVO.setQtipTitle(ramaVO.getText());
		ramaVO.setNivel(6);
		ramaVO.setPosicion(0);
		ramaVO.setTipoObjeto("Cobertura");
		ramaVO.setChildren(new RamaVO[0]);
		resultado.add(ramaVO);

		return resultado;
	}

}