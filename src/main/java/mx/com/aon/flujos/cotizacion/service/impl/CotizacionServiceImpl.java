package mx.com.aon.flujos.cotizacion.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.catbo.model.AseguradoraVO;
import mx.com.aon.configurador.pantallas.model.PantallaVO;
import mx.com.aon.configurador.pantallas.model.components.ComboClearOnSelectVO;
import mx.com.aon.configurador.pantallas.model.components.GridVO;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.core.VariableKernel;
import mx.com.aon.flujos.cotizacion.model.AyudaCoberturaCotizacionVO;
import mx.com.aon.flujos.cotizacion.model.ConsultaCotizacionVO;
import mx.com.aon.flujos.cotizacion.model.DatosEntradaCotizaVO;
import mx.com.aon.flujos.cotizacion.model.DesgloseCoberturaVO;
import mx.com.aon.flujos.cotizacion.model.MPoliObjVO;
import mx.com.aon.flujos.cotizacion.model.ObjetoCotizacionVO;
import mx.com.aon.flujos.cotizacion.model.ObjetoValorVO;
import mx.com.aon.flujos.cotizacion.model.ReciboVO;
import mx.com.aon.flujos.cotizacion.model.ResultadoCotizacionVO;
import mx.com.aon.flujos.cotizacion.model.RolVO;
import mx.com.aon.portal.service.impl.AbstractManager;
import mx.com.aon.flujos.cotizacion.service.CotizacionService;
import mx.com.aon.flujos.model.TatriParametrosVO;
import mx.com.aon.portal.model.BaseObjectVO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.procesos.emision.model.DatosRolVO;
import mx.com.aon.portal.service.PagedList;

import org.apache.log4j.Logger;

import com.biosnet.ice.ext.elements.form.ComboControl;
import com.biosnet.ice.ext.elements.form.ExtElement;
import com.biosnet.ice.ext.elements.form.TextFieldControl;
import com.wittyconsulting.backbone.endpoint.Endpoint;
import com.wittyconsulting.backbone.exception.BackboneApplicationException;

/**
 * 
 * Implementacion de la Interfaz Manager para el control y visualizacion de
 * datos
 * 
 * @author aurora.lozada
 * 
 */

public class CotizacionServiceImpl extends AbstractManager implements CotizacionService {

	private final transient Logger logger = Logger
			.getLogger(CotizacionServiceImpl.class);

	@SuppressWarnings("unchecked")
	public List<ComboClearOnSelectVO> getComboPadre(Map<String, String> parameters) throws ApplicationException{
		List<ComboClearOnSelectVO> lccosvo = new ArrayList<ComboClearOnSelectVO>();
		try {
			Endpoint endpoint = (Endpoint) endpoints.get("GET_COMBO_PADRE");
			lccosvo = (List<ComboClearOnSelectVO>) endpoint.invoke(parameters);
		} catch (BackboneApplicationException bae) {
			logger.error("Exception en obtener combos padres... ", bae);
			throw new ApplicationException("Error en obtener combos padres");
		}
		return lccosvo;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<ComboClearOnSelectVO> getComboPadreInstPago(Map<String, String> parameters) throws ApplicationException{
		List<ComboClearOnSelectVO> lccosvo = new ArrayList<ComboClearOnSelectVO>();
		try {
			Endpoint endpoint = (Endpoint) endpoints.get("GET_COMBO_PADRE_INSTPAGO");
			lccosvo = (List<ComboClearOnSelectVO>) endpoint.invoke(parameters);
		} catch (BackboneApplicationException bae) {
			logger.error("Exception en obtener combos padres... ", bae);
			throw new ApplicationException("Error en obtener combos padres");
		}
		return lccosvo;
		
	}
	
	@Deprecated
	public TatriParametrosVO getTatriParams(Map<String, String> parameters)
			throws ApplicationException {

		TatriParametrosVO tatriParams = new TatriParametrosVO();

		try {
			Endpoint endpoint = (Endpoint) endpoints
					.get("OBTIENE_TATRI_PARAMS");
			tatriParams = (TatriParametrosVO) endpoint.invoke(parameters);
		} catch (BackboneApplicationException bae) {
			logger.error(
					"Exception in invoke get tatri params en cotizacion.. ",
					bae);
			throw new ApplicationException(
					"Error al obtener tatri params en cotizacion");
		}

		return tatriParams;
	}

	public GridVO getResultados(Map<String, String> parameters)
			throws ApplicationException {
		GridVO gridVo = null;
		try {
		    if(logger.isDebugEnabled()){
		        logger.debug("OBTIENE_RESULTADOS parameters=" + parameters);
		    }
			Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_RESULTADOS");
			gridVo = (GridVO) endpoint.invoke(parameters);
			if(logger.isDebugEnabled())logger.debug("REsultado de cotizacion GRIDVO: " +gridVo);
		} catch (BackboneApplicationException e) {
			logger.error(
					"Exception in invoke obtener resultados en cotizacion.. ",
					e);
			throw new ApplicationException(
					"Error intentando obtener resultados en cotizacion");

		}
		return gridVo;
	}

	public List<?> getCoberturas(Map<String, String> parameters)
			throws ApplicationException {

		List<?> itemList = null;

		try {
			Endpoint endpoint = (Endpoint) endpoints
					.get("OBTIENE_COBERTURAS_CONFIGURADOR");
			itemList = (ArrayList<?>) endpoint.invoke(parameters);
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke get Coberturas en cotizacion.. ",
					bae);
			throw new ApplicationException(
					"Error al get Coberturas en cotizacion");
		}

		return itemList;
	}

	public AyudaCoberturaCotizacionVO getAyudaCobertura(
			Map<String, String> parameters) throws ApplicationException {

		AyudaCoberturaCotizacionVO ayudaVO = new AyudaCoberturaCotizacionVO();

		try {
			Endpoint endpoint = (Endpoint) endpoints
					.get("OBTIENE_AYUDA_COBERTURA");
			ayudaVO = (AyudaCoberturaCotizacionVO) endpoint.invoke(parameters);
		} catch (BackboneApplicationException bae) {
			logger.error(
					"Exception in invoke get Ayuda Cobertura en cotizacion.. ",
					bae);
			throw new ApplicationException(
					"Error al get Ayuda Cobertura en cotizacion");
		}

		return ayudaVO;
	}

	public PantallaVO getPantallaFinal(Map<String, String> parameters) throws ApplicationException {

		PantallaVO pantallaVO = new PantallaVO();
		try {
			Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_PANTALLA_FINAL");
			pantallaVO = (PantallaVO) endpoint.invoke(parameters);
			if(logger.isDebugEnabled()){
				logger.debug("getPantallaFinal()   pantallaVO=" +pantallaVO);
			}
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke getPantallaFinal en cotizacion.. ",bae);
			throw new ApplicationException("Error intentando obtener pantalla final");
		}
		return pantallaVO;
	}

	public String getBotonGuardaCotizacion() throws ApplicationException {

		List<String> ls = new ArrayList<String>();
		String result = null;
		try {
			Endpoint endpoint = (Endpoint) endpoints
					.get("BOTON_GUARDA_COTIZACION");
			ls = (ArrayList<String>) endpoint.invoke(3002);
			if (ls != null && !ls.isEmpty())
				result = ls.get(0);
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke buscar cotizacion.. ", bae);
			throw new ApplicationException("Error al buscar cotizacion");
		}
		return "S";
	}

	public void saveCotizacionResultado(String cdusuari, ResultadoCotizacionVO cotizacionCell)
			throws ApplicationException {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("CDUSUARI", cdusuari);
			parameters.put("CCVO", cotizacionCell);
			try {
				Endpoint endpoint = (Endpoint) endpoints.get("GUARDA_COTIZACION_RESULTADO");
				endpoint.invoke(parameters);
			} catch (BackboneApplicationException bae) {
				logger.error("Exception in invoke PKG_COTIZA.P_MOV_ZWPOLREC.. ", bae);
				throw new ApplicationException("Error al guardar cotizacion");
			}
	}
	
	public List<AseguradoraVO> getAseguradoras(ResultadoCotizacionVO cotizacionCell) throws ApplicationException{
		
		logger.debug(">< Entrando al metetodo getAseguradoras()");
		ArrayList<AseguradoraVO> aseguradoras=null;
		Map<String,String> parameters = new HashMap<String, String>();
		parameters.put("CDUNIECO", cotizacionCell.getCdUnieco());
		parameters.put("CDRAMO", cotizacionCell.getCdRamo());
		parameters.put("ESTADO", cotizacionCell.getEstado());
		parameters.put("NMPOLIZA", cotizacionCell.getNmPoliza());
		try {
			Endpoint endpoint = (Endpoint) endpoints.get("OBTEN_ASEGURADORAS");
			WrapperResultados res = (WrapperResultados) endpoint.invoke(parameters);
			aseguradoras = (ArrayList<AseguradoraVO>) res.getItemList();
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke PKG_COTIZA.PKG_CATBO.P_OBTIENE_COTIZA_CDCIA... ", bae);
			throw new ApplicationException("Error obtener las aseguradoras");
		}
		
		
		return aseguradoras;
		
		
	}

	public List<ConsultaCotizacionVO> getCotizacion(
			Map<String, String> parameters) throws ApplicationException {
		List<ConsultaCotizacionVO> lccvo = new ArrayList<ConsultaCotizacionVO>();
		try {
			Endpoint endpoint = (Endpoint) endpoints.get("OBTIEN_COTIZACIONES");
			lccvo = (ArrayList<ConsultaCotizacionVO>) endpoint.invoke(parameters);
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke buscar cotizacion.. ", bae);
			throw new ApplicationException("Error al buscar cotizacion");
		}
		return lccvo;
	}
	
	/**
	 * Consulta las cotizaciones existentes
	 * @params params Parametros de busqueda
	 * @start donde empieza la busqueda (parametro de paginacion)
	 * @limit donde termina la busqueda (parametro de paginacion)
	 */
	@SuppressWarnings("unchecked")
	public PagedList getCotizacion(Map<String, String> params, int start, int limit) throws ApplicationException {
		String endpointName = "OBTIEN_COTIZACIONES";

		return pagedBackBoneInvoke(params, endpointName, start, limit);
	}

	public void deleteCotizacionesConsulta(String cdusuari,
			List<ConsultaCotizacionVO> listaConsultaCotizaciones)
			throws ApplicationException {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("CDUSUARI", cdusuari);
		parameters.put("COTIZACIONES", listaConsultaCotizaciones);
		try {
			Endpoint endpoint = (Endpoint) endpoints.get("ELIMINA_COTIZACIONES_CONSULTA");
			endpoint.invoke(parameters);
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke PKG_COTIZA.P_ELIMINA_COTIZACIONES_CONSULTA.. ", bae);
			throw new ApplicationException("Error al borrar cotizacion");
		}
	}
	
	public List<MPoliObjVO> gerMPoliObj(Map<String, String> parameters) throws ApplicationException {

		List<MPoliObjVO> mpoliobj = null;
		try {
			Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_MPOLIOBJ");
			mpoliobj = (List<MPoliObjVO>) endpoint.invoke(parameters);
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke PKG_COTIZA.P_OBTIENE_MPOLIOBJA.. ", bae);
			throw new ApplicationException("Error al obetener accesorios");
		}
		return mpoliobj;
	}
	
	public List<ReciboVO> getRecibos(Map<String, String> parameters) throws ApplicationException {

		List<ReciboVO> recibos = null;
		try {
			Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_RECIBOS_COTIZACION");
			recibos = (ArrayList<ReciboVO>) endpoint.invoke(parameters);
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke PKG_COTIZA.OBTIENE_RECIBOS_COTIZACION.. ", bae);
			throw new ApplicationException("Error al obetener recibos");
		}
		return recibos;
	}
	
	public List<RolVO> gerRolesDC(Map<String, String> parameters) throws ApplicationException {
		List<RolVO> roles = null;
		try {
			Endpoint endpoint = (Endpoint) endpoints.get("GET_ROLES_DC");
			roles = (ArrayList<RolVO>) endpoint.invoke(parameters);
			//endpoint.invoke(parameters);
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke PKG_COTIZA.GET_ROLES_DC.. ", bae);
			throw new ApplicationException("Error al obetener recibos");
		}
		return roles;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ResultadoCotizacionVO> getResultadoCotizacion(
			DesgloseCoberturaVO parametrosEntrada) throws ApplicationException {
			
			List<ResultadoCotizacionVO> resultadoCotizacionLista =null;
			try{
				Endpoint endpoint =(Endpoint) endpoints.get("OBTIENE_DESGLOSE_COBERTURAS");
				resultadoCotizacionLista=(List<ResultadoCotizacionVO>) endpoint.invoke(parametrosEntrada); 
			}catch (BackboneApplicationException bae) {
				logger.error(bae.getMessage());
				throw new ApplicationException("Error al recuperar datos Desglose de Coberturas");
			}
			
			return (ArrayList<ResultadoCotizacionVO>) resultadoCotizacionLista;
		}
	
	public void pMovMPoliPer(Map<String, String> parameters) throws ApplicationException {
		try{
			Endpoint endpoint =(Endpoint) endpoints.get("P_MOV_M_POLI_PER");
			endpoint.invoke(parameters); 
		}catch (BackboneApplicationException bae) {
			logger.error(bae.getMessage());
			throw new ApplicationException("Error al insertar roles");
		}
	}
	
	//TODO: QUITAR
	public PantallaVO getPantallaTest(Map<String, String> parameters)
			throws ApplicationException {
		
		PantallaVO pantalla = null;
		try{
			logger.debug("entrando a obtener pantalla...");
			
			Endpoint endpoint = endpoints.get("OBTIENE_PANTALLA_CONFIGURADA_TEST");
			
			
			pantalla = (PantallaVO)  endpoint.invoke(parameters);
		}catch(BackboneApplicationException bae){
			logger.error(bae.getMessage());
			throw new ApplicationException("error en pantalla test");
		}
		
		logger.debug("pantalla obtenida es " + pantalla);
		
		return pantalla;
	}
	@SuppressWarnings("unchecked")
	public List<ObjetoCotizacionVO> getListaEquipo(ObjetoCotizacionVO objetoCotizacionVO) throws ApplicationException {
		List<ObjetoCotizacionVO> resultadoObjeto =null;
		try {
			Endpoint endpoint = (Endpoint)endpoints.get("OBTIENE_LISTA_COTIZACION_ESPECIAL");
			resultadoObjeto = (List<ObjetoCotizacionVO>) endpoint.invoke(objetoCotizacionVO);
		} catch (BackboneApplicationException bae) {
			throw new ApplicationException("Error retrieving data");			
		}
		return (List<ObjetoCotizacionVO>)resultadoObjeto;
	}
	/**
	 *@param cdramo
	 *@param cdtipsit
	 *@throws BackboneApplicationException
	 *@return etiqueta
	 */
	@SuppressWarnings("unchecked")
	public BaseObjectVO getEtiqueta(String cdramo, String cdtipsit)throws ApplicationException {
		Map params= new HashMap<String, String>();
		params.put("cdramo", cdramo);
		params.put("cdtipsit", cdtipsit);
		BaseObjectVO etiqueta = null;
		try {
			Endpoint endpoint = (Endpoint)endpoints.get("OBTIENE_ETIQUETA_PRODUCTO_ESPECIAL");
			etiqueta= (BaseObjectVO) endpoint.invoke(params);
		} catch (BackboneApplicationException e) {
			e.getMessage();
			throw new ApplicationException("Error retrieving data label");
		}
		return etiqueta;
	}
	/**
	 * @param cdramo
	 * @param cdtipsit
	 * @throws BackboneApplicationException
	 * @return tipos
	 */
	@SuppressWarnings("unchecked")
	public List<BaseObjectVO> getTipos(String cdramo, String cdtipsit)throws ApplicationException {
		Map params= new HashMap<String, String>();
		params.put("cdRamo", cdramo);
		params.put("cdTipsit", cdtipsit);
		List<BaseObjectVO> tipos= null;
		try {
			Endpoint endpoint = (Endpoint)endpoints.get("OBTIENE_lISTA_TIPO_PRODUCTO_ESPECIAL");
			tipos= (List<BaseObjectVO>) endpoint.invoke(params);
		} catch (BackboneApplicationException e) {
			e.getMessage();
			throw new  ApplicationException("Error retrieving data list");
		}
		return (List<BaseObjectVO>)tipos;
	}
	/**
	 * @param ObjetoCotizacionVO
	 * @throws ApplicationException 
	 * 
	 */
	public void servicioSatelite(ObjetoCotizacionVO principalObjeto)throws ApplicationException {
		
		Endpoint endpoint= (Endpoint) endpoints.get("CONSULTA_SERVICIO_SATELITE_MPOLIOBJ");
		try {
			endpoint.invoke(principalObjeto);
		} catch (BackboneApplicationException e) {
			e.getMessage();
			throw new ApplicationException("Error while insert from data base");
		}					
	}



	/**
	 * @param ObjetoValorVO
	 * @throws ApplicationException
	 * 
	 */
	public void servicioEquipoEspecial(ObjetoValorVO objetoValorVO)throws ApplicationException {
		Endpoint endpoint= (Endpoint) endpoints.get("SERVICIO_EQUIPO_ESPECIAL_COTIZACION");
		try {
			endpoint.invoke(objetoValorVO);
		} catch (BackboneApplicationException e) {
			e.getMessage();
			throw new ApplicationException("Error while insert from data base");
		}
	}
	
	/**
	 * @param cdramo
	 * @throws BackboneApplicationException
	 * @thows {@link ApplicationException} 
	 * @return (List<ObjetoValorVO>) items
	 */
	@SuppressWarnings("unchecked")	
	public List<ExtElement> getItems(String claveRamo)throws ApplicationException {
		Map params= new HashMap<String, String>();
		params.put("cdRamo",claveRamo);
		List<ExtElement> itemLista = null;
		try {
			Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_TIPOS_ITEMS_ATRIBUTOS_EQUIPO");
			itemLista = (List<ExtElement>) endpoint.invoke(params);
		} catch (BackboneApplicationException e) {
			e.getMessage();
			throw new ApplicationException("Error retrieving data");
		}
		return (List<ExtElement>) itemLista;
	
	}

	@SuppressWarnings("unchecked")
	public List<ComboControl> getCombos(String claveObjeto) throws ApplicationException {
		Map params= new HashMap<String, String>();
		params.put("cdRamo",claveObjeto);
		List<ComboControl> combosList = null;
		try {
			Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_COMBOS_ITEMS_ATRIBUTOS_EQUIPO");
			combosList = (List<ComboControl>) endpoint.invoke(params);
		} catch (BackboneApplicationException e) {
			e.getMessage();
			throw new ApplicationException("Error retrieving data");
		}
		return (List<ComboControl>) combosList;
	}

    public void movMPOLIAGR2(Map<String,String> parameters) throws ApplicationException {
    	try {
			Endpoint endpoint = (Endpoint) endpoints.get("MOV_MPOLIAGR2");
			endpoint.invoke(parameters);
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke PKG_EMISION.P_MOV_MPOLIAGR.. ", bae);
			throw new ApplicationException("Error en ... MOV_MPOLIAGR2");
		}
    }
    
    public void movMTARJETA(Map<String,String> parameters) throws ApplicationException {
    	try {
			Endpoint endpoint = (Endpoint) endpoints.get("GUARDA_DATOS_ENDOSO_TARJETA");
			endpoint.invoke(parameters);
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke PKG_EMISION.P_MOV_MTARJETA.. ", bae);
			throw new ApplicationException("Error en ... GUARDA_DATOS_ENDOSO_TARJETA");
		}
    }
    
    public BaseObjectVO obtieneNmsuplem(Map<String,String> parameters)throws ApplicationException {
    	BaseObjectVO nmsuplem;
    		try {
    			Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_NMSUPLEM");
    			nmsuplem=(BaseObjectVO)endpoint.invoke(parameters);
    		} catch (BackboneApplicationException bae) {
    			logger.error("Error en ... OBTIENE_NMSUPLEM", bae);
    			throw new ApplicationException("Error en ... OBTIENE_NMSUPLEM");
    		}
    	return nmsuplem;
    }

	public int getNumeroInsertaServicio(ObjetoCotizacionVO insertaObjeto) throws ApplicationException {
		Endpoint endpoint = endpoints.get("OBTIENE_NUMERO_OBJETO_EQUIPO_ESPECIAL");
		int numeroObjeto= 0;
		try {
			numeroObjeto = (Integer)endpoint.invoke(insertaObjeto);
			if(logger.isDebugEnabled()){
				logger.debug("NUMERO OBJETO IMPLEMENTACION"+numeroObjeto);
			}
		} catch (BackboneApplicationException bae) {
			bae.getMessage();
			throw new ApplicationException("error retrieving data");
		}			
		return numeroObjeto;
	}

	@SuppressWarnings("unchecked")
	public String guardaPersonaCotizacion(Map<String, String> parameters) throws ApplicationException {
		
		BaseObjectVO baseVO = null;
		String result = null;
		try{
			Endpoint endpoint =(Endpoint) endpoints.get("GRABA_PERSONA_COT");
			baseVO = (BaseObjectVO)endpoint.invoke(parameters);
			if (baseVO != null){
				result = baseVO.getLabel();
			}
		}catch (BackboneApplicationException bae) {
			logger.error(bae.getMessage());
			throw new ApplicationException("Error al agregar persona");
		}
		return result;
	}
	
	/**
	 * 
	 * @param ObjetoCotizacionVO
	 * @throws ApplicationException
	 * @author sergio.ramirez
	 * @return List<ExtElement>
	 */
	@SuppressWarnings("unchecked")
	public List<ExtElement> getAtributosEditables(ObjetoCotizacionVO objetoCotizacionVO) throws ApplicationException {
		List<ExtElement> itemEdicion = null;
		try {
			Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_TIPOS_ITEMS_ATRIBUTOS_EQUIPO_EDICION");
			itemEdicion = (List<ExtElement>) endpoint.invoke(objetoCotizacionVO);
		} catch (BackboneApplicationException e) {
			e.getMessage();
			throw new ApplicationException("Error retrieving data");
		}
		return (List<ExtElement>) itemEdicion;
	}
	
	/**
	 * @param ObjetoCotizacionVO
	 * @throws ApplicationException
	 * @author sergio.ramirez
	 * @return List<ComboControl>
	 */
	@SuppressWarnings("unchecked")
	public List<ComboControl> getCombosEdicion(ObjetoCotizacionVO objetoCotizacionVO) throws ApplicationException {
		List<ComboControl> combosList = null;
		try {
			Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_COMBOS_ATRIBUTOS_EQUIPO_EDICION");
			combosList = (List<ComboControl>) endpoint.invoke(objetoCotizacionVO);
		} catch (BackboneApplicationException e) {
			e.getMessage();
			throw new ApplicationException("error retrieving data");
		}
		return (List<ComboControl>) combosList;
	}
	
	/**
	 * 
	 * @param parameters
	 * @return
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
    public List<TextFieldControl> getTVALOSIT3(Map<String, String> parameters) throws ApplicationException {
        List<TextFieldControl> listaDatosExt = new ArrayList<TextFieldControl>();
        try {
            Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_TVALOSIT3");
            listaDatosExt = (ArrayList<TextFieldControl>) endpoint.invoke(parameters);
        } catch (BackboneApplicationException bae) {
            logger.error("Exception in invoke OBTIENE_TVALOSIT3 ", bae);
            throw new ApplicationException("Error en PKG_SATELITES.P_OBTIENE_TVALOSIT3");
        }
        return listaDatosExt;
    }
	
	/**
	 * 
	 * @param parameters
	 * @return
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
    public List<DatosEntradaCotizaVO> getDatosEntradaCotiza(Map<String, String> parameters) throws ApplicationException {
        List<DatosEntradaCotizaVO> listaDatosEntradaCotiza = null;
        try {
            Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_TVALOSIT_COTIZA");
            listaDatosEntradaCotiza = (ArrayList<DatosEntradaCotizaVO>) endpoint.invoke(parameters);
        } catch (BackboneApplicationException bae) {
            logger.error("Exception in invoke OBTIENE_TVALOSIT_COTIZA ", bae);
            throw new ApplicationException("Error en PKG_COTIZA.OBTIENE_TVALOSIT_COTIZA");
        }
        return listaDatosEntradaCotiza;
    }
	
	public void clonaObjetos(Map<String, String> parameters) throws ApplicationException {
		try {
			Endpoint endpoint = (Endpoint) endpoints.get("CLONA_OBJETOS");
			endpoint.invoke(parameters);
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke PKG_COTIZA.P_CLONA_OBJETOS... ", bae);
			throw new ApplicationException("Error al clonar objetos");
		}
	}
	
	/**
	 * 
	 * @param parameters
	 * @return
	 * @throws ApplicationException
	 */
	public RolVO obtieneAsegurado(Map<String, String> parameters) throws ApplicationException {
		RolVO rol = null;
		try {
			Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_ASEGURADO");
			rol = (RolVO) endpoint.invoke(parameters);
			
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke OBTIENE _ASEGURADO", bae);
			throw new ApplicationException("Error al obetener el Asegurado");
		}
		return rol;
	}
}
