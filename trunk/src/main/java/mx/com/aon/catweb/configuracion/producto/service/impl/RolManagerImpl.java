package mx.com.aon.catweb.configuracion.producto.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.catweb.configuracion.producto.model.LlaveValorVO;
import mx.com.aon.catweb.configuracion.producto.rol.model.RolAtributoVariableVO;
import mx.com.aon.catweb.configuracion.producto.rol.model.RolVO;
import mx.com.aon.catweb.configuracion.producto.service.RolManager;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.MensajesVO;
import mx.com.aon.portal.service.impl.AbstractManager;
import mx.com.aon.portal.util.WrapperResultados;

import org.apache.log4j.Logger;

import com.wittyconsulting.backbone.endpoint.Endpoint;
import com.wittyconsulting.backbone.exception.BackboneApplicationException;

public class RolManagerImpl extends AbstractManager implements RolManager {

	private static Logger logger = Logger.getLogger(ProductoManagerImpl.class);

	public List<RolAtributoVariableVO> atributosVariablesJson(int codigoRamo,
			String codigoTipoSituacion, String codigoRol, int codigoNivel)
			throws ApplicationException {

		Endpoint endpoint = (Endpoint) endpoints
				.get("OBTIENE_ATRIBUTOS_VARIABLES_ROL");
		Map params = new HashMap<String, String>();
		params.put("codigoRamo", codigoRamo);
		params.put("codigoTipoSituacion", codigoTipoSituacion);
		params.put("codigoRol", codigoRol);
		params.put("codigoNivel", codigoNivel);

		List<RolAtributoVariableVO> listaAtributos = null;
		try {
			listaAtributos = (List<RolAtributoVariableVO>) endpoint
					.invoke(params);

		} catch (BackboneApplicationException e) {
			throw new ApplicationException(
					"Error regresando lista de atributos variables");
		}
		return listaAtributos;
	}

	public List<LlaveValorVO> catalogoAtributosVariablesJson()
			throws ApplicationException {
		List<LlaveValorVO> catalogo = null;
		try {
			Endpoint manager = (Endpoint) endpoints
					.get("CATALOGO_ATRIBUTOS_VARIABLES");
			catalogo = (List<LlaveValorVO>) manager.invoke(null);

			// List<ComboVO> item = new ArrayList<ComboVO>();
			// ComboVO cvo = null;
			// for (int i = 0; i < 5; i++) {
			// cvo = new ComboVO();
			// cvo.setKey("1");
			// cvo.setValue("Hallo");
			//	
			// item.add(cvo);
			// }
			// catalogos = item;

			if (catalogo == null) {
				throw new ApplicationException(
						"No exite ningun atributo variable");
			} else {
				logger
						.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! catalogoAtributosVariables size"
								+ catalogo.size());
			}

		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'CATALOGO_ATRIBUTOS_VARIABLES'",
					bae);
			throw new ApplicationException(
					"Error al cargar el catalogo de atributos variables desde el sistema");
		}
		return catalogo;
	}

	public List<LlaveValorVO> catalogoObligatorioJson()
			throws ApplicationException {
		
		List<LlaveValorVO> catalogo = null;

		catalogo = new ArrayList<LlaveValorVO>();

		LlaveValorVO obligatoriedad = new LlaveValorVO();
		obligatoriedad.setKey("O");
		obligatoriedad.setValue("OPCIONAL");
		catalogo.add(obligatoriedad);

/*		obligatoriedad = new LlaveValorVO();
		obligatoriedad.setKey("P");
		obligatoriedad.setValue("PROHIBIDO");
		catalogo.add(obligatoriedad);
*/
		obligatoriedad = new LlaveValorVO();
		obligatoriedad.setKey("R");
		obligatoriedad.setValue("REQUERIDO");
		catalogo.add(obligatoriedad);

		if (catalogo == null) {
			throw new ApplicationException(
					"No exite ningun tipo de Obligatoriedad");
		} else {
			logger
					.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! catalogo Obligatorio size"
							+ catalogo.size());
		}

		return catalogo;
	}

	public List<LlaveValorVO> catalogoRolesJson() throws ApplicationException {
		List<LlaveValorVO> catalogo = null;
		try {
			Endpoint manager = (Endpoint) endpoints.get("CATALOGO_ROLES");
			catalogo = (List<LlaveValorVO>) manager.invoke(null);

			// List<ComboVO> item = new ArrayList<ComboVO>();
			// ComboVO cvo = null;
			// for (int i = 0; i < 5; i++) {
			// cvo = new ComboVO();
			// cvo.setKey("1");
			// cvo.setValue("Hallo");
			//	
			// item.add(cvo);
			// }
			// catalogo = item;

			if (catalogo == null) {
				throw new ApplicationException("No exite ningun Rol");
			} else {
				logger.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!catalogoRoles size"
						+ catalogo.size());
			}

		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'CATALOGO_ROLES'", bae);
			throw new ApplicationException(
					"Error al cargar los roles desde el sistema");
		}
		return catalogo;
	}

	public void agregaRolCatalogo(LlaveValorVO rol) throws ApplicationException {
		Endpoint manager = (Endpoint) endpoints.get("AGREGAR_ROL_CATALOGO");
		try {
			manager.invoke(rol);
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke AGREGAR_ROL_CATALOGO", bae);
			throw new ApplicationException(
					"Error al insertar nuevo rol al catalogo");
		}
	}

	public void agregaAtributoVariableRol(
			List<RolAtributoVariableVO> listaAtributos)
			throws ApplicationException {
		Endpoint manager = (Endpoint) endpoints
				.get("AGREGAR_ATRIBUTO_VARIABLE_ROL");
		try {
			if (logger.isDebugEnabled()) {
				for (RolAtributoVariableVO atribu : listaAtributos) {

					logger.debug("RAMOLISTA//" + atribu.getCdRamo());
					logger.debug("NIVELLISTA//" + atribu.getCodigoNivel());
					logger.debug("ROLLISTA//" + atribu.getCodigoRol());
					logger.debug("CODATRIBUVARIABLELISTA//"
							+ atribu.getCodigoAtributoVariable());
					logger.debug("CODVALORESLISTA//"
							+ atribu.getCodigoListaDeValores());
					logger.debug("OBLIGATORIOLISTA//"
							+ atribu.getSwitchObligatorio());
					logger.debug("TIPSITLISTA//" + atribu.getCodigoTipsit());

				}
			}
			manager.invoke(listaAtributos);
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke AGREGAR_ATRIBUTO_VARIABLE_ROL",
					bae);
			throw new ApplicationException(
					"Error al insertar atributo variable al rol");
		}

	}
	
	public MensajesVO eliminaAtributoVariableRol(RolAtributoVariableVO atributoRol) throws ApplicationException {
		Endpoint manager = (Endpoint) endpoints.get("ELIMINAR_ATRIBUTO_VARIABLE_ROL");
		MensajesVO mensajeVO = null;
		try {
			mensajeVO = (MensajesVO)manager.invoke(atributoRol);
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke ELIMINAR_ATRIBUTO_VARIABLE_ROL", bae);
			throw new ApplicationException("Error al eliminar rol al inciso");
		}
		return mensajeVO;
	}

	public void agregarRolInciso(RolVO rol) throws ApplicationException {
		Endpoint manager = (Endpoint) endpoints.get("AGREGAR_ROL_INCISO");
		try {
			manager.invoke(rol);
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke AGREGAR_ROL_INCISO", bae);
			throw new ApplicationException("Error al insertar rol al inciso");
		}

	}

	public void agregarAtributoVariableCatalogo(RolAtributoVariableVO atributo)
			throws ApplicationException {
		Endpoint manager = (Endpoint) endpoints
				.get("AGREGAR_ATRIBUTO_VARIABLE_CATALOGO");
		try {
			manager.invoke(atributo);
		} catch (BackboneApplicationException bae) {
			logger.error(
					"Exception in invoke AGREGAR_ATRIBUTO_VARIABLE_CATALOGO",
					bae);
			throw new ApplicationException(
					"Error al insertar atributo variable al catalogo");
		}

	}

	public RolVO obtieneRolAsociado(int codigoRamo, String codigoTipoSituacion,
			String codigoRol, int codigoNivel) throws ApplicationException {
		Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_ROL_ASOCIADO");
		Map params = new HashMap<String, String>();
		params.put("codigoRamo", codigoRamo);
		params.put("codigoTipoSituacion", codigoTipoSituacion);
		params.put("codigoRol", codigoRol);
		params.put("codigoNivel", codigoNivel);

		RolVO rol = null;
		try {
			rol = (RolVO) endpoint.invoke(params);

		} catch (BackboneApplicationException e) {
			throw new ApplicationException("Error regresando rol asociado");
		}
		return rol;
	}

	public WrapperResultados eliminaRol(int codigoRamo, String codigoRol, int codigoNivel)
			throws ApplicationException {
		Map params = new HashMap<String, String>();
		params.put("cdRamo", codigoRamo);
		params.put("cdRol", codigoRol);
		params.put("cdNivel", codigoNivel);
		
		WrapperResultados resultado = null;
		try{
			resultado = returnBackBoneInvoke(params, "ELIMINAR_ROL");
		}catch(Exception e){
			logger.error("ERROR" + e, e);
			throw new ApplicationException("Error al eliminar rol");
		}
		return resultado;
	}

	public boolean tieneHijosAtributoVariableRol(RolAtributoVariableVO atributoRol) throws ApplicationException {
		
		MensajesVO mensaje = null;
		boolean tieneHijos = false;
		Endpoint endpoint = (Endpoint) endpoints.get("VALIDA_HIJOS_ATRIB_VAR_ROL");
		try {
			mensaje = (MensajesVO) endpoint.invoke(atributoRol);
		} catch (BackboneApplicationException bae) {
			logger.error("Se origino un error: " + bae.getMessage(), bae);
			throw new ApplicationException("Error intentando validar hijos de atributos variables de Rol");
		} catch (Exception exc) {
			logger.error("Error: " + exc.getMessage(), exc);
			throw new ApplicationException("Error intentando validar hijos de atributos variables de Rol");
		}
		if(mensaje.getMsgText().equals("1")){
			tieneHijos = true;
		}else{
			tieneHijos = false;
		}
		return tieneHijos;
	}

}