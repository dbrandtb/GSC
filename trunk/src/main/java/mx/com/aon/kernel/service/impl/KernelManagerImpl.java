package mx.com.aon.kernel.service.impl;

import static mx.com.aon.portal.dao.WrapperResultadosGeneric.MSG_ID_OK;

import static mx.com.aon.portal.dao.ProcesoDAO.GENERAR_ORDEN_TRABAJO;
import static mx.com.aon.portal.dao.ProcesoDAO.PERMISO_EJECUCION_PROCESO;
import static mx.com.aon.portal.dao.ProcesoDAO.PROCESO_EMISION_DAO;
import static mx.com.aon.portal.dao.ProcesoDAO.BUSCAR_MATRIZ_ASIGNACION;

import static mx.com.aon.portal.dao.ProcesoDAO.CALCULA_NUMERO_POLIZA;
import static mx.com.aon.portal.dao.ProcesoDAO.GENERA_SUPLEMENTO_FISICO;
import static mx.com.aon.portal.dao.ProcesoDAO.GENERA_SUPLEMENTO_LOGICO;
import static mx.com.aon.portal.dao.ProcesoDAO.MOV_T_DESC_SUP;
import static mx.com.aon.portal.dao.ProcesoDAO.MOV_M_SUPLEME;
import static mx.com.aon.portal.dao.ProcesoDAO.MOV_MPOLIAGR;
import static mx.com.aon.portal.dao.ProcesoDAO.MOV_MPOLIAGE;
import static mx.com.aon.portal.dao.ProcesoDAO.EXEC_VALIDADOR;
import static mx.com.aon.portal.dao.ProcesoDAO.OBTIENE_TVALOSIT_COTIZA;
import static mx.com.aon.portal.dao.ProcesoDAO.CLONAR_SITUACION;
import static mx.com.aon.portal.dao.ProcesoDAO.EJECUTA_P_EXEC_SIGSVDEF;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mx.biosnet.procesobobpelclient.proxy.ProcesoBOBPELPortClient;
import mx.biosnet.procesobobpelclient.proxy.SvcRequest;
import mx.biosnet.procesobobpelclient.proxy.SvcResponse;
import mx.com.aon.catbo.model.ResultadoGeneraCasoVO;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.core.VariableKernel;
import mx.com.aon.flujos.cotizacion.model.DatosEntradaCotizaVO;
import mx.com.aon.flujos.cotizacion.model.SituacionVO;
import mx.com.aon.flujos.endoso.model.SuplemVO;
import mx.com.aon.kernel.model.ValoresXDefectoCoberturaVO;
import mx.com.aon.kernel.service.KernelManager;
import mx.com.aon.pdfgenerator.PDFGeneratorFormatoOT;
import mx.com.aon.portal.model.BaseObjectVO;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.utils.Constantes;
import mx.com.ice.kernel.constants.ConstantsKernel;
import mx.com.ice.kernel.core.Global;
import mx.com.ice.kernel.utileriaskernel.UtileriasKernel;
import mx.com.ice.services.UtilsServices;
import mx.com.ice.services.business.ManejadorSession;
import mx.com.ice.services.business.ServiciosCargarBloques;
import mx.com.ice.services.business.ServiciosGeneralesBloques;
import mx.com.ice.services.business.ServiciosGeneralesNegocio;
import mx.com.ice.services.business.ServiciosGeneralesSistema;
import mx.com.ice.services.business.dao.ServiciosGeneralesDAO;
import mx.com.ice.services.exception.manager.ExceptionManager;
import mx.com.ice.services.to.Campo;
import mx.com.ice.services.to.GuardarBloqueResultadoDAO;
import mx.com.ice.services.to.ResultadoTransaccion;
import mx.com.ice.services.to.screen.GlobalVariableContainerVO;

import oracle.jdbc.driver.OracleTypes;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.SqlParameter;

import com.wittyconsulting.backbone.endpoint.Endpoint;
import com.wittyconsulting.backbone.exception.BackboneApplicationException;

public class KernelManagerImpl extends AbstractManagerJdbcTemplateInvoke implements KernelManager {

	protected final transient Logger logger = Logger.getLogger(KernelManagerImpl.class);

	private static final String STATUS_ERROR = "O";

	/**
	 * Atributo inyectado vía spring para utilizar los servicios de negocio
	 */
	private ServiciosGeneralesNegocio serviciosGeneralesNegocio;

	private ServiciosGeneralesBloques serviciosGeneralesBloques;

	private ServiciosCargarBloques serviciosGeneralesCargarBloques;

	private ServiciosGeneralesDAO serviciosGeneralesDAO;

	private ServiciosGeneralesSistema serviciosGeneralesSistema;

	private PDFGeneratorFormatoOT formatoOT;
	
	private Map<String, Endpoint> endpoints;
	
	private static final String PROCESO_EMISION = "2";
	private static final String PROCESO_COTIZACION = "1";

	public GlobalVariableContainerVO cargaValoresPorDefecto(String idSesion, UserVO user, GlobalVariableContainerVO globalVarVo, String tipSup) throws ApplicationException {

		long t0 = System.currentTimeMillis();

		String cdUnieco = globalVarVo.getValueVariableGlobal(VariableKernel.UnidadEconomica());
		String cdRamo = globalVarVo.getValueVariableGlobal(VariableKernel.CodigoRamo());
		String estado = globalVarVo.getValueVariableGlobal(VariableKernel.Estado());
		String idProducto = globalVarVo.getValueVariableGlobal(VariableKernel.CodigoRamo());
		if (logger.isDebugEnabled()) {
			logger.debug("cdUnieco=" + cdUnieco);
			logger.debug("cdRamo=" + cdRamo);
			logger.debug("estado=" + estado);
			logger.debug("idProducto=" + idProducto);
			logger.debug("USUARIO=" + user);
            logger.debug("tipSup=" + tipSup);
		}

		/* ***** Servicios de BD ***** */
		
		// Servicio Calcuar Numero Poliza
		String nmpoliza = calculaNumeroPoliza(cdUnieco, cdRamo, estado);
		// Agregamos el numero de poliza a las variables globales
		globalVarVo.addVariableGlobal(VariableKernel.NumeroPoliza(), nmpoliza);

		// Servicio Generar Suplemento Fisico
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date hoy = new Date();
		String fechaActual = dateFormat.format(hoy).toString();
		String nmSuplem = String.valueOf(generaSuplFisico(cdUnieco, cdRamo, estado, nmpoliza, fechaActual));
		globalVarVo.addVariableGlobal(VariableKernel.NumeroSuplemento(), nmSuplem);

		// Servicio Generar Suplemento Logico
		String nSupLogi = String.valueOf(generaSuplLogico(cdUnieco, cdRamo, estado, nmpoliza));

		// calculamos un año a la fecha actual
		String fechaEnUnAnnio = dateFormat.format(DateUtils.addYears(hoy, 1)).toString();

		// Servicio MOV_TDESCSUP
		movTdescSup(cdUnieco, cdRamo, estado, nmpoliza, nSupLogi, fechaActual, fechaActual, user.getUser(), tipSup);

		// Servicio MOV_MSUPLEME
		movMSupleme(cdUnieco, cdRamo, estado, nmpoliza, nmSuplem, fechaActual, fechaEnUnAnnio, nSupLogi);

		// Servicio MOV_MPOLIAGR
		movMPoliagr(cdUnieco, cdRamo, estado, nmpoliza, nmSuplem);

		// Servicio MOV_MPOLIAGE
		movMPoliage(cdUnieco, cdRamo, estado, nmpoliza, nmSuplem);

		long tIni = System.currentTimeMillis();
		// Se crea la session de negocio o donde almaceno la informacion de los Bloques
		serviciosGeneralesSistema.crearSesion(idSesion);
		serviciosGeneralesSistema.crearEntidad(idSesion);

		ResultadoTransaccion rt = new ResultadoTransaccion();
		// Servicios de Valores por Defecto:
		try {
			// Crear Bloque B1
			serviciosGeneralesSistema.crearBloque(idSesion, ConstantsKernel.BLOQUE_B1);
			
			// Valores por Defecto Bloque B1
			rt = ExceptionManager.manage(serviciosGeneralesNegocio.valoresDefectoCamposBXX(idSesion, idProducto, ConstantsKernel.BLOQUE_B1, globalVarVo));
			serviciosGeneralesSistema.imprimirBloque(idSesion, ConstantsKernel.BLOQUE_B1);
			
			// Asignar Datos Bloque B1
			Campo[] campos = new Campo[9];
			campos[0] = new Campo("CDUNIECO", globalVarVo.getValueVariableGlobal(VariableKernel.UnidadEconomica()));
			campos[1] = new Campo("CDRAMO", globalVarVo.getValueVariableGlobal(VariableKernel.CodigoRamo()));
			campos[2] = new Campo("ESTADO", globalVarVo.getValueVariableGlobal(VariableKernel.Estado()));
			campos[3] = new Campo("NMPOLIZA", globalVarVo.getValueVariableGlobal(VariableKernel.NumeroPoliza()));
			campos[4] = new Campo("NMSUPLEM", globalVarVo.getValueVariableGlobal(VariableKernel.NumeroSuplemento()));
			campos[5] = new Campo("NMSITUAC", globalVarVo.getValueVariableGlobal(VariableKernel.NumeroSituacion()));
			campos[6] = new Campo("STATUS", "V");
			campos[7] = new Campo("SWESTADO", "0");
			campos[8] = new Campo("NMSOLICI", globalVarVo.getValueVariableGlobal(VariableKernel.NumeroPoliza()));
			rt = ExceptionManager.manage(serviciosGeneralesSistema.asignarDatosBloque(idSesion, ConstantsKernel.BLOQUE_B1, campos, ""));
			serviciosGeneralesSistema.imprimirBloque(idSesion, ConstantsKernel.BLOQUE_B1);
			
			// Guardar Bloque B1 (insertar)
			Campo camposId[] = new Campo[2];
			camposId[0] = new Campo("ROWID", "");
			camposId[1] = new Campo("ERROR", "");
			ExceptionManager.manage(serviciosGeneralesBloques.guardarBloqueB1ICE(idSesion, camposId, "I", globalVarVo));
			
			// Crear Bloque B1B
			serviciosGeneralesSistema.crearBloque(idSesion, ConstantsKernel.BLOQUE_B1B);
			
			// Valores por Defecto Bloque B1B
			Campo[] camposPoliza = new Campo[5];
			camposPoliza[0] = new Campo("CDUNIECO", cdUnieco);
			camposPoliza[1] = new Campo("CDRAMO", cdRamo);
			camposPoliza[2] = new Campo("ESTADO", estado);
			camposPoliza[3] = new Campo("NMPOLIZA", nmpoliza);
			camposPoliza[4] = new Campo("NMSUPLEM", nmSuplem);
			rt = ExceptionManager.manage(serviciosGeneralesNegocio.valoresDefectoAtributosVariablesPolizaB1B(idSesion, idProducto, camposPoliza, globalVarVo));
			serviciosGeneralesSistema.imprimirBloque(idSesion, ConstantsKernel.BLOQUE_B1B);

			// Crear Bloque B5
			serviciosGeneralesSistema.crearBloque(idSesion, ConstantsKernel.BLOQUE_B5);
			
			// Obtener el valor de fechaSit:
			//campos = new Campo[1];
			//campos[0] = new Campo("FEEFECTO", "");
			//String fechaSit = serviciosGeneralesSistema.obtenerDatosBloque(idSesion, ConstantsKernel.BLOQUE_B1, campos).getCampos()[0].getValor();
			
			//Valores por Defecto Bloque B5
			ExceptionManager.manage(serviciosGeneralesNegocio.valoresDefectoCamposBXX(idSesion,idProducto,ConstantsKernel.BLOQUE_B5, globalVarVo));
			
			serviciosGeneralesSistema.imprimirBloque(idSesion, ConstantsKernel.BLOQUE_B5);
			
			// Asignar Datos Bloque B5
			campos = new Campo[13];
			campos[0] = new Campo("CDUNIECO", globalVarVo.getValueVariableGlobal(VariableKernel.UnidadEconomica()));
			campos[1] = new Campo("CDRAMO", globalVarVo.getValueVariableGlobal(VariableKernel.CodigoRamo()));
			campos[2] = new Campo("ESTADO", globalVarVo.getValueVariableGlobal(VariableKernel.Estado()));
			campos[3] = new Campo("NMPOLIZA", globalVarVo.getValueVariableGlobal(VariableKernel.NumeroPoliza()));
			campos[4] = new Campo("NMSUPLEM", globalVarVo.getValueVariableGlobal(VariableKernel.NumeroSuplemento()));
			campos[5] = new Campo("NMSITUAC", globalVarVo.getValueVariableGlobal(VariableKernel.NumeroSituacion()));
			campos[6] = new Campo("CDTIPSIT", globalVarVo.getValueVariableGlobal(VariableKernel.CodigoTipoSituacion()));
			campos[7] = new Campo("STATUS", "V");
			campos[8] = new Campo("CDAGRUPA", "1");
			campos[9] = new Campo("CDESTADO", "0");
			//campos[10] = new Campo("FEFECSIT", fechaSit);
			//campos[11] = new Campo("CDPLAN", "");
			//campos[12] = new Campo("CDASEGUR", "30");
			//campos[10] = new Campo("FEFECSIT", fechaSit);
			campos[10] = new Campo("CDPLAN", "");
			campos[11] = new Campo("CDASEGUR", "30");
			rt = ExceptionManager.manage(serviciosGeneralesSistema.asignarDatosBloque(idSesion, ConstantsKernel.BLOQUE_B5, campos, ""));

			// Guardar Bloque B5 (insertar)
			Campo params[] = new Campo[2];
			params[0] = new Campo("ROWID", "");
			params[1] = new Campo("ERROR", "");
			ExceptionManager.manage(serviciosGeneralesBloques.guardarBloqueB5ICE(idSesion, params, "I", globalVarVo));
			
			// serviciosGeneralesSistema.imprimirBloque(idSesion, ConstantsKernel.BLOQUE_B5);

			// Crear Bloque B5B
			serviciosGeneralesSistema.crearBloque(idSesion, ConstantsKernel.BLOQUE_B5B);
			
			//Valores por Defecto Bloque B5B
			campos = new Campo[7];
			campos[0] = new Campo(VariableKernel.UnidadEconomica(), globalVarVo.getValueVariableGlobal(VariableKernel.UnidadEconomica()));
			campos[1] = new Campo(VariableKernel.CodigoRamo(), globalVarVo.getValueVariableGlobal(VariableKernel.CodigoRamo()));
			campos[2] = new Campo(VariableKernel.Estado(), globalVarVo.getValueVariableGlobal(VariableKernel.Estado()));
			campos[3] = new Campo(VariableKernel.NumeroPoliza(), globalVarVo.getValueVariableGlobal(VariableKernel.NumeroPoliza()));
			campos[4] = new Campo(VariableKernel.NumeroSuplemento(), globalVarVo.getValueVariableGlobal(VariableKernel.NumeroSuplemento()));
			campos[5] = new Campo(VariableKernel.NumeroSituacion(), globalVarVo.getValueVariableGlobal(VariableKernel.NumeroSituacion()));
			campos[6] = new Campo(VariableKernel.CodigoTipoSituacion(), globalVarVo.getValueVariableGlobal(VariableKernel.CodigoTipoSituacion()));
			
			// Valores por Defecto Bloque B5B
			rt = ExceptionManager.manage(serviciosGeneralesNegocio.valoresDefectoAtributosVariablesSituacionesB5B(idSesion, idProducto, campos, globalVarVo));
			
			//serviciosGeneralesSistema.imprimirBloque(idSesion, ConstantsKernel.BLOQUE_B5B);

		} catch (mx.com.ice.services.exception.ApplicationException appExc) {
			logger.error(appExc, appExc);
		}

		if (logger.isDebugEnabled()) {
			long tFin = System.currentTimeMillis();
			logger.debug("Tiempo en Crear Bloque B1 (y guardarlo), B1B , B5 (y guardarlo) y B5B; y Valores por Defecto de B1,B1B,B5 y B5B= " + ((tFin - tIni)) + " mseg");

			long t1 = System.currentTimeMillis();
			logger.debug("Tiempo total del metodo cargaValoresPorDefecto()= " + ((t1 - t0)) + " mseg");
		}
		return globalVarVo;
	}

	/**
	 * guardaDatosAdicionalesEndosos
	 * 
	 * @param idSesion
	 * @param globalVarVo
	 * @param parametrosPantalla
	 * @throws mx.com.ice.services.exception.ApplicationException
	 */
	public void guardaDatosAdicionalesEndosos(String idSesion, GlobalVariableContainerVO globalVarVo,
			Map<String, String> parametrosPantalla) throws mx.com.ice.services.exception.ApplicationException {
		if (logger.isInfoEnabled()) {
			logger.info("-----> guardaDatosAdicionalesEndosos");
		}

		String idProducto = globalVarVo.getValueVariableGlobal(VariableKernel.CodigoRamo());
		ResultadoTransaccion rt = null;
		Campo[] camposModificados = null;
		String cdCapita = null;

		try {
			camposModificados = obtieneParametrosValidos(parametrosPantalla, ConstantsKernel.BLOQUE_B5B);
			if (logger.isInfoEnabled()) {
				logger.info("::: camposModificados :: " + camposModificados);
				logger.info("::: camposModificados length :: " + camposModificados.length);
				if (camposModificados != null && camposModificados.length > 0) {
					for (int i = 0; i < camposModificados.length; i++) {
						Campo campo = camposModificados[i];
						logger.info("::: Nombre :: " + campo.getNombre());
						logger.info("::: Valor :: " + campo.getValor());
						logger.info("::: Tipo :: " + campo.getTipo());
					}
				}
			}

			// Asignación de bloque
			serviciosGeneralesSistema.imprimirBloque(idSesion, ConstantsKernel.BLOQUE_B5B);
			rt = ExceptionManager.manage(serviciosGeneralesSistema.asignarDatosBloque(idSesion, ConstantsKernel.BLOQUE_B5B,
					camposModificados, ""));
			serviciosGeneralesSistema.imprimirBloque(idSesion, ConstantsKernel.BLOQUE_B5B);

			// Se guardan datos
			Campo[] camposIdE = new Campo[1];
			camposIdE[0] = new Campo("Identificador_Error", "");
			rt = ExceptionManager.manage(serviciosGeneralesBloques.guardarBloqueB5BSelectivoICE(idSesion, camposIdE,
					Constantes.UPDATE_MODE, camposModificados, globalVarVo));

			// TODO Se disparan validaciones para B5
			// ExceptionManager.manage(serviciosGeneralesNegocio.validacionesBloqueSeccionCampo(idSesion,
			// idProducto, ConstantsKernel.BLOQUE_B5, "1", null, globalVarVo));

			// TODO Se disparan validaciones para B5B
			// ExceptionManager.manage(serviciosGeneralesNegocio.validacionesBloqueSeccionCampo(idSesion,
			// idProducto, ConstantsKernel.BLOQUE_B5B, "1", null, globalVarVo));

			// ////////////
			/*
			 * Operaciones de B5B
			 */
			/*
			 * Campo[] camposIdE = new Campo[1]; camposIdE[0] = new
			 * Campo("Identificador_Error", "");
			 * 
			 * camposModificados = obtieneParametrosValidos(parametrosPantalla,
			 * "B5B"); rt =
			 * ExceptionManager.manage(serviciosGeneralesSistema.asignarDatosBloque(idSesion,
			 * ConstantsKernel.BLOQUE_B5B, camposModificados, ""));
			 * serviciosGeneralesSistema.imprimirBloque(idSesion,
			 * ConstantsKernel.BLOQUE_B5B); rt =
			 * ExceptionManager.manage(serviciosGeneralesBloques.guardarBloqueB5BSelectivoICE(idSesion,
			 * camposIdE, "U", camposModificados, globalVarVo));
			 */
			// ////////////
			if (logger.isInfoEnabled()) {
				logger.info("::::::: Se guardo " + ConstantsKernel.BLOQUE_B5B);
			}
		} catch (mx.com.ice.services.exception.ApplicationException appExc) {
			logger.error("Error al ejecutar validaciones de producto: " + appExc.toString(), appExc);
			throw appExc;
		}
	}

	/**
	 * 
	 * @param idSesion
	 * @param globalVarVo
	 * @param parametrosPantalla
	 * @param cdGarant
	 * @param sumaAsegurada
	 * @throws mx.com.ice.services.exception.ApplicationException
	 */
	public void guardarCoberturasAtributos(String idSesion, GlobalVariableContainerVO globalVarVo,
			Map<String, String> parametrosPantalla, String cdGarant, String cdElemento, String sumaAsegurada)
			throws mx.com.ice.services.exception.ApplicationException {
		if (logger.isInfoEnabled()) {
			logger.info("-----> guardarCoberturasAtributos");
		}

		String idProducto = globalVarVo.getValueVariableGlobal(VariableKernel.CodigoRamo());
		ResultadoTransaccion rt = null;
		Campo[] camposModificados = null;
		String cdCapita = null;

		try {
			// Guardar b18
			Campo[] capital = new Campo[1];
			capital[0] = new Campo("CDCAPITA|" + cdGarant, "");
			rt = ExceptionManager.manage(serviciosGeneralesSistema.obtenerDatosBloque(idSesion, ConstantsKernel.BLOQUE_B19,
					capital));
			cdCapita = capital[0].getValor();
			if (logger.isDebugEnabled()) {
				logger.debug("::: cdCapita :: " + cdCapita);
			}
			parametrosPantalla.put(new StringBuffer().append(ConstantsKernel.BLOQUE_B18).append("_PTCAPITA_").append(cdCapita)
					.toString(), sumaAsegurada);
			if (logger.isDebugEnabled()) {
				logger.debug("::: parametrosPantalla :: " + parametrosPantalla);
			}
			// ///////////////////////////////////////////////////
			camposModificados = obtieneParametrosValidos(parametrosPantalla, ConstantsKernel.BLOQUE_B18);
			if (logger.isInfoEnabled()) {
				logger.info("::: camposModificados :: " + camposModificados);
				logger.info("::: camposModificados length :: " + camposModificados.length);
				if (camposModificados != null && camposModificados.length > 0) {
					for (int i = 0; i < camposModificados.length; i++) {
						Campo campo = camposModificados[i];
						logger.info("::: Nombre :: " + campo.getNombre());
						logger.info("::: Valor :: " + campo.getValor());
						logger.info("::: Tipo :: " + campo.getTipo());
					}
				}
			}
			serviciosGeneralesSistema.imprimirBloque(idSesion, ConstantsKernel.BLOQUE_B18);
			rt = ExceptionManager.manage(serviciosGeneralesSistema.asignarDatosBloque(idSesion, ConstantsKernel.BLOQUE_B18,
					camposModificados, ""));
			serviciosGeneralesSistema.imprimirBloque(idSesion, ConstantsKernel.BLOQUE_B18);
			serviciosGeneralesNegocio.guardarSumasAseguradasB18(idSesion, idProducto, Constantes.UPDATE_MODE, globalVarVo);

			// TODO descomentar
			// rt =
			// ExceptionManager.manage(serviciosGeneralesNegocio.validacionesBloqueSeccionCampo(idSesion,
			// idProducto, ConstantsKernel.BLOQUE_B18, "1", null, globalVarVo));

			if (logger.isInfoEnabled()) {
				logger.info("::::::: Se guardo " + ConstantsKernel.BLOQUE_B18);
			}

			// Guardar b19
			Campo[] camposIdE = new Campo[1];
			camposIdE[0] = new Campo("Identificador_Error", "");
			/*
			 * rt =
			 * ExceptionManager.manage(serviciosGeneralesBloques.guardarBloqueB19(
			 * idSesion, camposIdE, "U", globalVarVo)); if
			 * (logger.isDebugEnabled()) { logger.debug(":: rt : " + rt);
			 * logger.debug("::::::: Se guardo " + ConstantsKernel.BLOQUE_B19); }
			 */

			// Guardar b19b
			camposModificados = obtieneParametrosValidos(parametrosPantalla, ConstantsKernel.BLOQUE_B19B);
			serviciosGeneralesSistema.imprimirBloque(idSesion, ConstantsKernel.BLOQUE_B19B);
			rt = ExceptionManager.manage(serviciosGeneralesSistema.asignarDatosBloque(idSesion, ConstantsKernel.BLOQUE_B19B,
					camposModificados, ""));
			serviciosGeneralesSistema.imprimirBloque(idSesion, ConstantsKernel.BLOQUE_B19B);

			camposIdE = new Campo[1];
			camposIdE[0] = new Campo("Identificador_Error", "");
			rt = ExceptionManager.manage(serviciosGeneralesBloques.guardarBloqueB19BSelectivoICE(idSesion, camposIdE, "U",
					camposModificados, globalVarVo));
			logger.debug("::: despues de  guardar  bloque b19b");
			/*rt = ExceptionManager.manage(serviciosGeneralesNegocio.validacionesBloqueSeccionCampo(idSesion, idProducto,
					ConstantsKernel.BLOQUE_B19B, "1", null, globalVarVo));*/

			if (logger.isInfoEnabled()) {
				logger.info("::::::: Se guardo " + ConstantsKernel.BLOQUE_B19B);
			}
		} catch (mx.com.ice.services.exception.ApplicationException appExc) {
			logger.error("Error al ejecutar validaciones de producto: " + appExc.toString(), appExc);
			throw appExc;
		}
	}

	/**
	 * 
	 * @param idSesion
	 * @param globalVarVo
	 * @param parametrosPantalla
	 * @param cdGarant
	 * @param sumaAsegurada
	 * @throws mx.com.ice.services.exception.ApplicationException
	 */
	public void guardarCoberturasAtributosEndosos(String idSesion, GlobalVariableContainerVO globalVarVo,
			Map<String, String> parametrosPantalla, String cdGarant, String cdElemento, String sumaAsegurada, String modo)
			throws mx.com.ice.services.exception.ApplicationException {
		if (logger.isInfoEnabled()) {
			logger.info("-----> guardarCoberturasAtributosEndosos");
		}

		String idProducto = globalVarVo.getValueVariableGlobal(VariableKernel.CodigoRamo());
		ResultadoTransaccion rt = null;
		Campo[] camposModificados = null;
		String cdCapita = null;

		try {
			// Guardar b18
			serviciosGeneralesSistema.imprimirBloque(idSesion, ConstantsKernel.BLOQUE_B19);
			Campo[] capital = new Campo[1];
			capital[0] = new Campo("CDCAPITA|" + cdGarant, "");
			rt = ExceptionManager.manage(serviciosGeneralesSistema.obtenerDatosBloque(idSesion, ConstantsKernel.BLOQUE_B19,
					capital));
			cdCapita = capital[0].getValor();
			if (logger.isDebugEnabled()) {
				logger.debug("::: cdCapita :: " + cdCapita);
			}
			parametrosPantalla.put(new StringBuffer().append(ConstantsKernel.BLOQUE_B18).append("_PTCAPITA_").append(cdCapita)
					.toString(), sumaAsegurada);
			if (logger.isDebugEnabled()) {
				logger.debug("::: parametrosPantalla :: " + parametrosPantalla);
			}
			// ///////////////////////////////////////////////////
			camposModificados = obtieneParametrosValidos(parametrosPantalla, ConstantsKernel.BLOQUE_B18);
			if (logger.isInfoEnabled()) {
				logger.info("::: camposModificados :: " + camposModificados);
				logger.info("::: camposModificados length :: " + camposModificados.length);
				if (camposModificados != null && camposModificados.length > 0) {
					for (int i = 0; i < camposModificados.length; i++) {
						Campo campo = camposModificados[i];
						logger.info("::: Nombre :: " + campo.getNombre());
						logger.info("::: Valor :: " + campo.getValor());
						logger.info("::: Tipo :: " + campo.getTipo());
					}
				}
			}
			serviciosGeneralesSistema.imprimirBloque(idSesion, ConstantsKernel.BLOQUE_B18);
			rt = ExceptionManager.manage(serviciosGeneralesSistema.asignarDatosBloque(idSesion, ConstantsKernel.BLOQUE_B18,
					camposModificados, ""));
			serviciosGeneralesSistema.imprimirBloque(idSesion, ConstantsKernel.BLOQUE_B18);
			serviciosGeneralesNegocio.guardarSumasAseguradasB18Endosos(idSesion, idProducto, modo, globalVarVo);

			// TODO descomentar
			// rt =
			// ExceptionManager.manage(serviciosGeneralesNegocio.validacionesBloqueSeccionCampo(idSesion,
			// idProducto, ConstantsKernel.BLOQUE_B18, "1", null, globalVarVo));

			if (logger.isInfoEnabled()) {
				logger.info("::::::: Se guardo " + ConstantsKernel.BLOQUE_B18);
			}

			// Guardar b19
			Campo[] camposIdE = new Campo[1];
			camposIdE[0] = new Campo("Identificador_Error", "");
			/*
			 * rt =
			 * ExceptionManager.manage(serviciosGeneralesBloques.guardarBloqueB19(
			 * idSesion, camposIdE, "U", globalVarVo)); if
			 * (logger.isDebugEnabled()) { logger.debug(":: rt : " + rt);
			 * logger.debug("::::::: Se guardo " + ConstantsKernel.BLOQUE_B19); }
			 */

			// Guardar b19b
			camposModificados = obtieneParametrosValidos(parametrosPantalla, ConstantsKernel.BLOQUE_B19B);
			serviciosGeneralesSistema.imprimirBloque(idSesion, ConstantsKernel.BLOQUE_B19B);
			rt = ExceptionManager.manage(serviciosGeneralesSistema.asignarDatosBloque(idSesion, ConstantsKernel.BLOQUE_B19B,
					camposModificados, ""));
			serviciosGeneralesSistema.imprimirBloque(idSesion, ConstantsKernel.BLOQUE_B19B);

			camposIdE = new Campo[1];
			camposIdE[0] = new Campo("Identificador_Error", "");
			rt = ExceptionManager.manage(serviciosGeneralesBloques.guardarBloqueB19BSelectivoICEEndosos(idSesion, camposIdE, "U",
					camposModificados, globalVarVo));
			logger.debug("::: despues de  guardar  bloque b19b");
			/*rt = ExceptionManager.manage(serviciosGeneralesNegocio.validacionesBloqueSeccionCampo(idSesion, idProducto,
					ConstantsKernel.BLOQUE_B19B, "1", null, globalVarVo));*/

			if (logger.isInfoEnabled()) {
				logger.info("::::::: Se guardo " + ConstantsKernel.BLOQUE_B19B);
			}
		} catch (mx.com.ice.services.exception.ApplicationException appExc) {
			logger.error("Error al ejecutar validaciones de producto: " + appExc.toString(), appExc);
			throw appExc;
		}
	}

	/**
	 * 
	 * @param idSesion
	 * @param globalVarVo
	 * @param parametrosPantalla
	 * @param claveTipoObj
	 * @param montoCotizar
	 * @param descripcionTipo
	 * @param modoAcceso
	 * @param cdObjeto
	 * @throws mx.com.ice.services.exception.ApplicationException
	 * @throws ApplicationException
	 */
	public void guardarAccesoriosAtributos(String idSesion, GlobalVariableContainerVO globalVarVo,
			Map<String, String> parametrosPantalla, String claveTipoObj, String montoCotizar, String descripcionTipo,
			String modoAcceso, String cdObjeto) throws mx.com.ice.services.exception.ApplicationException, ApplicationException {
		if (logger.isInfoEnabled()) {
			logger.info("-----> guardarAccesoriosAtributos");
			logger.debug("::: parametrosPantalla :: " + parametrosPantalla);
			logger.debug("::: claveTipoObj       :: " + claveTipoObj);
			logger.debug("::: montoCotizar       :: " + montoCotizar);
			logger.debug("::: descripcionTipo    :: " + descripcionTipo);
			logger.debug("::: modoAcceso         :: " + modoAcceso);
			logger.debug("::: cdObjeto           :: " + cdObjeto);
		}

		String idProducto = globalVarVo.getValueVariableGlobal(VariableKernel.CodigoRamo());
		ResultadoTransaccion rt = null;
		Campo[] camposModificados = null;

		try {
			// Guardar b6
			/*
			 * Campo[] capital = new Campo[1]; capital[0] = new
			 * Campo("CDTIPOOBJ|" + claveTipoObj, ""); rt =
			 * ExceptionManager.manage(serviciosGeneralesSistema.obtenerDatosBloque(idSesion,
			 * ConstantsKernel.BLOQUE_B6, capital)); cdObjeto =
			 * capital[0].getValor();
			 */

			// se obtiene el nmObjeto
			if (Constantes.INSERT_MODE.equals(modoAcceso) || StringUtils.isBlank(cdObjeto)) {
				cdObjeto = obtieneNmObjetoService(globalVarVo.getValueVariableGlobal(VariableKernel.UnidadEconomica()),
						globalVarVo.getValueVariableGlobal(VariableKernel.CodigoRamo()), globalVarVo
								.getValueVariableGlobal(VariableKernel.Estado()), globalVarVo
								.getValueVariableGlobal(VariableKernel.NumeroPoliza()));
			}

			if (logger.isDebugEnabled()) {
				logger.debug("::: cdObjeto :: " + cdObjeto);
			}
			parametrosPantalla.put(new StringBuffer().append(ConstantsKernel.BLOQUE_B6).append("_CDTIPOBJ_").append(cdObjeto)
					.toString(), claveTipoObj);
			parametrosPantalla.put(new StringBuffer().append(ConstantsKernel.BLOQUE_B6).append("_PTOBJETO_").append(cdObjeto)
					.toString(), montoCotizar);
			if (logger.isDebugEnabled()) {
				logger.debug("::: parametrosPantalla :: " + parametrosPantalla);
			}
			// ///////////////////////////////////////////////////
			camposModificados = obtieneParametrosValidos(parametrosPantalla, ConstantsKernel.BLOQUE_B6);
			if (logger.isInfoEnabled()) {
				logger.info(":::: camposModificados :: " + camposModificados);
				logger.info(":::: camposModificados length :: " + camposModificados.length);
				if (camposModificados != null && camposModificados.length > 0) {
					for (int i = 0; i < camposModificados.length; i++) {
						Campo campo = camposModificados[i];
						logger.info(":::: Nombre :: " + campo.getNombre());
						logger.info(":::: Valor :: " + campo.getValor());
						logger.info(":::: Tipo :: " + campo.getTipo());
					}
				}
			}
			camposModificados = agregarAtributosBloque(camposModificados, globalVarVo, cdObjeto, descripcionTipo);
			if (logger.isInfoEnabled()) {
				logger.info(":::: camposModificados despues de agregarAtributosBloque :: " + camposModificados);
				logger.info(":::: camposModificados length :: " + camposModificados.length);
				if (camposModificados != null && camposModificados.length > 0) {
					for (int i = 0; i < camposModificados.length; i++) {
						Campo campo = camposModificados[i];
						logger.info(":::: Nombre :: " + campo.getNombre());
						logger.info(":::: Valor :: " + campo.getValor());
						logger.info(":::: Tipo :: " + campo.getTipo());
					}
				}
			}
			serviciosGeneralesSistema.imprimirBloque(idSesion, ConstantsKernel.BLOQUE_B6);
			rt = ExceptionManager.manage(serviciosGeneralesSistema.asignarDatosBloque(idSesion, ConstantsKernel.BLOQUE_B6,
					camposModificados, cdObjeto));
			serviciosGeneralesSistema.imprimirBloque(idSesion, ConstantsKernel.BLOQUE_B6);

			Campo camposId[] = new Campo[2];
			camposId[0] = new Campo("IDERROR", "");
			camposId[1] = new Campo("ROWID", "");

			// String modoAcceso = INSERT_MODE;
			globalVarVo.addVariableGlobal("vg.NmObjeto", cdObjeto);
			logger.debug(":::: Se agrego vg.NmObjeto = " + cdObjeto);
			rt = ExceptionManager.manage(serviciosGeneralesBloques.guardarBloqueB6(idSesion, camposId, modoAcceso, globalVarVo),
					logger, "### --> FALLO LA INSERCION EN GUARDAR B6 ");

			if (logger.isInfoEnabled()) {
				logger.info("::::::::::::::::::::: Se guardo " + ConstantsKernel.BLOQUE_B6);
			}
			// ////////////////////////////////////////////////////
			// Guardar b6b
			camposModificados = obtieneParametrosValidos(parametrosPantalla, ConstantsKernel.BLOQUE_B6B);
			if (logger.isInfoEnabled()) {
				logger.info(":::: camposModificados :: " + camposModificados);
				logger.info(":::: camposModificados length :: " + camposModificados.length);
				if (camposModificados != null && camposModificados.length > 0) {
					for (int i = 0; i < camposModificados.length; i++) {
						Campo campo = camposModificados[i];
						logger.info(":::: Nombre :: " + campo.getNombre());
						logger.info(":::: Valor :: " + campo.getValor());
						logger.info(":::: Tipo :: " + campo.getTipo());
					}
				}
			}
			serviciosGeneralesSistema.imprimirBloque(idSesion, ConstantsKernel.BLOQUE_B6B);
			rt = ExceptionManager.manage(serviciosGeneralesSistema.asignarDatosBloque(idSesion, ConstantsKernel.BLOQUE_B6B,
					camposModificados, cdObjeto));
			serviciosGeneralesSistema.imprimirBloque(idSesion, ConstantsKernel.BLOQUE_B6B);

			Campo[] camposAGuardar = new Campo[camposModificados.length];
			for (int i = 0; i < camposModificados.length; i++) {
				camposAGuardar[i] = new Campo(new StringBuilder().append(camposModificados[i].getNombre()).append('|').append(
						cdObjeto).toString(), camposModificados[i].getValor(), camposModificados[i].getTipo());
			} // Fin del for

			rt = ExceptionManager.manage(serviciosGeneralesBloques.guardarBloqueB6BSelectivo(idSesion, camposId, "U",
					camposAGuardar, globalVarVo));

			/*rt = ExceptionManager.manage(serviciosGeneralesNegocio.validacionesBloqueSeccionCampo(idSesion, idProducto,
					ConstantsKernel.BLOQUE_B6B, null, null, globalVarVo));

			logger.debug("::: despues de  guardar  bloque b6b");
			rt = ExceptionManager.manage(serviciosGeneralesNegocio.validacionesBloqueSeccionCampo(idSesion, idProducto,
					ConstantsKernel.BLOQUE_B6B, "1", null, globalVarVo));*/

			if (logger.isInfoEnabled()) {
				logger.info("::::::::::::::::::::: Se guardo " + ConstantsKernel.BLOQUE_B6B);
			}

			serviciosGeneralesSistema.imprimirBloque(idSesion, ConstantsKernel.BLOQUE_B6);
			serviciosGeneralesSistema.imprimirBloque(idSesion, ConstantsKernel.BLOQUE_B6B);
		} catch (mx.com.ice.services.exception.ApplicationException appExc) {
			logger.error("Error al ejecutar validaciones de producto: " + appExc.toString(), appExc);
			throw appExc;
		}
	}

	/**
	 * @param valueVariableGlobal
	 * @param valueVariableGlobal2
	 * @param valueVariableGlobal3
	 * @param valueVariableGlobal4
	 * @return
	 * @throws ApplicationException
	 */
	private String obtieneNmObjetoService(String cdunieco, String cdramo, String estado, String nmpoliza)
			throws ApplicationException {
		if (logger.isInfoEnabled()) {
			logger.info("-----> obtieneNmObjetoService");
		}
		String nmobjeto = null;
		Map<String, String> params = new HashMap<String, String>();
		params.put("pi_CDUNIECO", cdunieco);
		params.put("pi_CDRAMO", cdramo);
		params.put("pi_ESTADO", estado);
		params.put("pi_NMPOLIZA", nmpoliza);

		try {
			Endpoint manager = (Endpoint) endpoints.get("CALCULA_NUMERO_OBJETO");
			GuardarBloqueResultadoDAO resultadoDAO = (GuardarBloqueResultadoDAO) manager.invoke(params);
			if (logger.isInfoEnabled()) {
				logger.info("-----> resultadoDAO : " + resultadoDAO);
			}
			nmobjeto = resultadoDAO.getNmObj();
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'CALCULA_NUMERO_OBJETO'", bae);
			throw new ApplicationException("Error al obtener el numero de Objeto");
		}

		return nmobjeto;
	}

	/**
	 * @param camposModificados
	 * @param descripcionTipo
	 * @param cdObjeto
	 * @param globalVarVo
	 * @return
	 */
	private Campo[] agregarAtributosBloque(Campo[] camposModificados, GlobalVariableContainerVO globalVarVO, String cdObjeto,
			String descripcionTipo) {
		if (logger.isInfoEnabled()) {
			logger.info("-----> agregarAtributosBloque");
		}
		Campo[] camposModificadosAtributos = null;
		int numCamposB6 = camposModificados.length;
		if (logger.isInfoEnabled()) {
			logger.info(".. numCamposB6 : " + numCamposB6);
		}
		camposModificadosAtributos = new Campo[8 + numCamposB6];
		camposModificadosAtributos[0] = new Campo("CDUNIECO", globalVarVO
				.getValueVariableGlobal(VariableKernel.UnidadEconomica()), null);
		camposModificadosAtributos[1] = new Campo("CDRAMO", globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo()), null);
		camposModificadosAtributos[2] = new Campo("ESTADO", globalVarVO.getValueVariableGlobal(VariableKernel.Estado()), null);
		camposModificadosAtributos[3] = new Campo("NMPOLIZA", globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza()),
				null);
		camposModificadosAtributos[4] = new Campo("NMSUPLEM", globalVarVO.getValueVariableGlobal(VariableKernel
				.NumeroSuplemento()), null);
		camposModificadosAtributos[5] = new Campo("STATUS", "V", null);
		camposModificadosAtributos[6] = new Campo("NMOBJETO", cdObjeto, null);
		camposModificadosAtributos[7] = new Campo("DSOBJETO", descripcionTipo, null);

		for (int i = 0; i < camposModificados.length; i++) {
			camposModificadosAtributos[8 + i] = camposModificados[i];
		}

		return camposModificadosAtributos;
	}

	public GlobalVariableContainerVO comprar(String idSesion, GlobalVariableContainerVO globalVarVo,
			Map<String, String> parametrosPantalla, UserVO usuario) throws ApplicationException, mx.com.ice.services.exception.ApplicationException {
		long t0 = System.currentTimeMillis();

		if (logger.isInfoEnabled()) {
			logger.info("a Comprar()");
			logger.info("vg.UserBD=" + globalVarVo.getValueVariableGlobal(VariableKernel.UsuarioBD()));
		}

		String cdUnieco = globalVarVo.getValueVariableGlobal(VariableKernel.UnidadEconomica());
		String cdRamo = globalVarVo.getValueVariableGlobal(VariableKernel.CodigoRamo());
		String estado = globalVarVo.getValueVariableGlobal(VariableKernel.Estado());
		String idProducto = globalVarVo.getValueVariableGlobal(VariableKernel.CodigoRamo());
		String cdTipsit = globalVarVo.getValueVariableGlobal(VariableKernel.CodigoTipoSituacion());
		String nmpoliza = globalVarVo.getValueVariableGlobal(VariableKernel.NumeroPoliza());
		String nmSuplem = globalVarVo.getValueVariableGlobal(VariableKernel.NumeroSuplemento());
		String cdCia = globalVarVo.getValueVariableGlobal(VariableKernel.CodigoCompania());
		String cdPlan = globalVarVo.getValueVariableGlobal(VariableKernel.CodigoPlan());
		String cdPerpag = globalVarVo.getValueVariableGlobal(VariableKernel.PeriodicidadPago());
		String nmSituac = globalVarVo.getValueVariableGlobal(VariableKernel.NumeroSituacion());

		ResultadoTransaccion rt = new ResultadoTransaccion();

		// Se disparan las validaciones
			// Tomar los datos de pantalla y meter en bloque
			// luego meter en objetos
			Campo[] camposModificados = null;
			// B1
			camposModificados = obtieneParametrosValidos(parametrosPantalla, "B1");
			rt = ExceptionManager.manage(serviciosGeneralesSistema.asignarDatosBloque(idSesion, ConstantsKernel.BLOQUE_B1,
					camposModificados, ""));

			// B1 guardar
			Campo camposId[] = new Campo[2];
			camposId[0] = new Campo("ROWID", "");
			camposId[1] = new Campo("ERROR", "");
			ExceptionManager.manage(serviciosGeneralesBloques.guardarBloqueB1ICE(idSesion, camposId, "U", globalVarVo));
			if (logger.isDebugEnabled()) {
				logger.debug("despues de guardarBloqueB1 ");
			}

			// B1B
			camposModificados = obtieneParametrosValidos(parametrosPantalla, "B1B");
			rt = ExceptionManager.manage(serviciosGeneralesSistema.asignarDatosBloque(idSesion, ConstantsKernel.BLOQUE_B1B, camposModificados, ""));
			serviciosGeneralesNegocio.guardarAtributosVariablesPolizaB1B(idSesion, globalVarVo);

			// B5
			camposModificados = obtieneParametrosValidos(parametrosPantalla, "B5");
			logger.debug("Num de campos modificados en B5:" + camposModificados.length);
			for (int i = 0; i < camposModificados.length; i++) {
				logger.debug("campo[" + i + "]= Nombre: " + camposModificados[i].getNombre() + " Tipo: "
						+ camposModificados[i].getTipo() + " Valor:" + camposModificados[i].getValor());
			}
			if (camposModificados != null && camposModificados.length != 0) {

				rt = ExceptionManager.manage(serviciosGeneralesSistema.asignarDatosBloque(idSesion, ConstantsKernel.BLOQUE_B5,
						camposModificados, ""));

				// B5 guardar
				Campo params[] = new Campo[2];
				params[0] = new Campo("ROWID", "");
				params[1] = new Campo("ERROR", "");
				ExceptionManager.manage(serviciosGeneralesBloques.guardarBloqueB5ICE(idSesion, params, "I", globalVarVo));
			}

			/*
			 * Operaciones de B5B
			 */
			Campo[] camposIdE = new Campo[1];
			camposIdE[0] = new Campo("Identificador_Error", "");

			camposModificados = obtieneParametrosValidos(parametrosPantalla, "B5B");
			rt = ExceptionManager.manage(serviciosGeneralesSistema.asignarDatosBloque(idSesion, ConstantsKernel.BLOQUE_B5B, camposModificados, ""));
			serviciosGeneralesSistema.imprimirBloque(idSesion, ConstantsKernel.BLOQUE_B5B);
			rt = ExceptionManager.manage(serviciosGeneralesBloques.guardarBloqueB5BSelectivoICE(idSesion, camposIdE, "U", camposModificados, globalVarVo));
			
			//Validacion de B5B (Anterior mecanismo de validacion)
			//rt = ExceptionManager.manage(serviciosGeneralesNegocio.validacionesBloqueSeccionCampo(idSesion, idProducto, ConstantsKernel.BLOQUE_B5B, "1", null, globalVarVo));
			
			//Validacion de B14 (Nuevo mecanismo de validacion)
			//Se lanza una ApplicationException cuando no se cumplen las validaciones
			WrapperResultados resultadoValidaciones = null;
			resultadoValidaciones = ejecutaPExecValidador(cdUnieco, cdRamo, estado, nmpoliza, nmSituac, ConstantsKernel.BLOQUE_B14);
			logger.debug("resultadoValidaciones ID=" + resultadoValidaciones.getMsgId());
			logger.debug("resultadoValidaciones TEXT=" + resultadoValidaciones.getMsgText());
			logger.debug("resultadoValidaciones TITLE=" + resultadoValidaciones.getMsgTitle());
			if(StringUtils.isNotBlank(resultadoValidaciones.getMsgId())){
				
				//Lanzar excepcion con el contenido de msgText, si éste viene vacío, se enviará el msgID
				String mensajeDeValidacion = resultadoValidaciones.getMsgText();
				if( StringUtils.isBlank( resultadoValidaciones.getMsgText() ) ){
					mensajeDeValidacion = resultadoValidaciones.getMsgId();
				}
				throw new mx.com.ice.services.exception.ApplicationException (mensajeDeValidacion);
			}
			

			/*
			 * Operaciones de B19
			 */
			/// /// ///serviciosGeneralesSistema.imprimirBloque(idSesion, ConstantsKernel.BLOQUE_B19);
			
			 /* 
			 * bloquye b6
			 */
			rt = ExceptionManager.manage(serviciosGeneralesSistema.existeBloque(idSesion, ConstantsKernel.BLOQUE_B6));
			if ("FALSE".equalsIgnoreCase(rt.getCampos()[0].getValor())) {
				rt = ExceptionManager.manage(serviciosGeneralesSistema.crearBloque(idSesion, ConstantsKernel.BLOQUE_B6));
			}

			rt = ExceptionManager.manage(serviciosGeneralesSistema.existeBloque(idSesion, ConstantsKernel.BLOQUE_B6B));
			if ("FALSE".equalsIgnoreCase(rt.getCampos()[0].getValor())) {
				rt = ExceptionManager.manage(serviciosGeneralesSistema.crearBloque(idSesion, ConstantsKernel.BLOQUE_B6B));
			}
			Campo[] camposB6 = new Campo[0];

			// Paso 8)
			camposId = new Campo[0];
			camposId = UtilsServices.addCampo(camposId, "IDERROR", "");
			camposId = UtilsServices.addCampo(camposId, "ROWID", "");


		WrapperResultados resultado = procesoEmision(usuario.getUser(), cdUnieco, cdRamo,
				estado, nmpoliza, nmSituac, nmSuplem, usuario.getEmpresa().getElementoId(), cdCia, cdPlan, cdPerpag, usuario
						.getCodigoPersona(), null);
		
		String numeroExterno = (String)resultado.getItemMap().get("NUMERO_EXTERNO");
		String numeroInterno = (String)resultado.getItemMap().get("NUMERO_INTERNO");
		
		globalVarVo.addVariableGlobal(VariableKernel.MessageProcess(), resultado.getResultado());
		ejecutarCaso(globalVarVo, usuario, cdUnieco, cdRamo, cdTipsit,
				numeroInterno, nmSuplem, cdCia, nmSituac, "M", PROCESO_EMISION, numeroExterno);
		
		return globalVarVo;
	}

	private String getMatriz(String cdUniage, String cdRamo, String cdElemento, String cdProceso) throws ApplicationException {

		Map<String, String> map = new HashMap<String, String>();
		
		map.put("pv_cdunieco_i", cdUniage);
		map.put("pv_cdramo_i", cdRamo);
		map.put("pv_cdelemento_i", cdElemento);
		map.put("pv_cdproceso_i", cdProceso);
		
		if(logger.isDebugEnabled()){
			logger.debug("Valores para llamar a PKG_CATBO.P_OBTIENE_CDMATRIZ");
			logger.debug("pv_cdunieco_i"+ cdUniage);
			logger.debug("pv_cdramo_i"+ cdRamo);
			logger.debug("pv_cdelemento_i"+ cdElemento);
			logger.debug("pv_cdproceso_i"+ cdProceso);
		}
		
		return (String)getBackBoneInvoke(map, BUSCAR_MATRIZ_ASIGNACION);
	}
	
	
	public void ejecutarCaso(GlobalVariableContainerVO globalVarVo,
			UserVO usuario, String cdUnieco, String cdRamo, String cdTipsit,
			String nmpoliza, String nmSuplem, String cdCia, String nmSituac, String estado, String proceso, String numeroExterno)
			throws ApplicationException {
		if (permisoEjecucionProceso(cdCia, cdRamo, usuario.getEmpresa().getElementoId(), proceso)) {
			
			if (proceso.equals(PROCESO_EMISION)) {
				formatoOT.generaFormatoOTpdf(cdUnieco, cdRamo, estado, nmpoliza);
			}
			SvcResponse response = null;
		    SvcRequest req = new SvcRequest();
			try {
			ProcesoBOBPELPortClient myPort = new ProcesoBOBPELPortClient();
		    logger.debug("Invocando servicio: " + myPort.getEndpoint());

		    if(cdCia==null)cdCia="";
		    if(nmSituac==null)nmSituac="1";

	                req.setCdmatriz(new BigInteger(getMatriz(cdCia, cdRamo, usuario.getEmpresa().getElementoId(), proceso)));
	                req.setCdperson(usuario.getCodigoPersona());
	                req.setCdramo(cdRamo);
	                req.setCdunieco(cdCia);
	                req.setCdusuario(usuario.getUser());
	                req.setDsmetcontac("");
	                req.setEstado(estado);
		            req.setIndPoliza("false");
		            req.setNmpoliex(numeroExterno);
	                req.setNmpoliza(nmpoliza);
	                req.setNmsbsitext("");
		            req.setNmsituaext("");
	                req.setNmsituac(nmSituac);
	                req.setCdprioridad("1");
	                req.setCdproceso(proceso);
	                req.setDsobservacion("Automatico");
	                
	                
	                if(logger.isDebugEnabled()){
	        			logger.debug("Valores fijados para llamar al WebService: ");
	                	logger.debug("(bigInteger) req.getCdmatriz()="+ req.getCdmatriz());
	        			logger.debug("usuario.getCodigoPersona()="+ usuario.getCodigoPersona());
	        			logger.debug("usuario.getUser()"+ usuario.getUser());
	        			logger.debug("estado"+ estado);
	        			logger.debug("numeroExterno"+ numeroExterno);
	        			logger.debug("nmpoliza (interno)"+ nmpoliza);
	        			logger.debug("nmSituac"+ nmSituac);
	        			
	        		}


				response = myPort.process(req);

			} catch (RemoteException remoteException) {
				
				logger.error(remoteException.getMessage());
				throw new ApplicationException(remoteException.getMessage());
				
			} catch (Exception exception) {
				logger.error(exception.getMessage());
				throw new ApplicationException(exception.getMessage());
			}
				logger.info("esta es la response: numero de caso generado: " +
		                response.getNmCaso() + " numero de orden: " +
		                response.getNmOrden());

				if (logger.isDebugEnabled())
	                logger.debug("Caso creado via iniciar caso");
	ResultadoGeneraCasoVO res = new ResultadoGeneraCasoVO();
	res.setNumCaso(response.getNmCaso());
	res.setCdOrdenTrabajo(Integer.toString(response.getNmOrden()));
			
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("pv_CDELEMENTO_i", usuario.getEmpresa().getElementoId());
			map.put("pv_CDUNIECO_i", cdUnieco);
			map.put("pv_CDUNIAGE_i", cdCia);
			map.put("pv_CDRAMO_i", cdRamo);
			map.put("pv_ESTADO_i", estado);
			map.put("pv_NMPOLIZA_i", nmpoliza);
			map.put("pv_NMSUPLEM_i", nmSuplem);
			map.put("pv_CDTIPSIT_i", cdTipsit);
			map.put("pv_NMSITUAC_i", nmSituac);
			map.put("pv_CDPROCESO_i", proceso);
			map.put("pv_CDUSUARI_i", usuario.getUser());
			map.put("pv_CDPERSON_i", usuario.getCodigoPersona());
			map.put("pv_NMCASO_i", response.getNmCaso());
			
			if (logger.isDebugEnabled())
                logger.debug("Los parametros para generar la otden de trabajo para cdCia: "+cdCia+" son: "+map);
			
			globalVarVo.addVariableGlobal(VariableKernel.MessageProcess(), generarOrdenTrabajo(map));
			globalVarVo.addVariableGlobal(VariableKernel.OT(), "OT");
				
		}
	}	
	
	private boolean permisoEjecucionProceso(String cdUnieco, String cdRamo, String cdElemento, String cdProceso) throws ApplicationException {
		
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("pv_CDUNIECO_i", cdUnieco);
		map.put("pv_CDRAMO_i", cdRamo);
		map.put("pv_CDELEMENTO_i", cdElemento);
		map.put("pv_CDPROCESO_i", cdProceso);
		
		WrapperResultados resultados = returnBackBoneInvoke(map, PERMISO_EJECUCION_PROCESO);
		
		if (resultados.getResultado().equals("S"))
			return true;
		return false;
	}
	
	
	private String generarOrdenTrabajo(Map<String, String> parametros) throws ApplicationException {
		
		WrapperResultados resultados = returnResult(parametros, GENERAR_ORDEN_TRABAJO);
		
		return resultados.getMsgTitle();
	}

	public GlobalVariableContainerVO ejecutaValidaciones(String idSesion, GlobalVariableContainerVO globalVarVo,
			Map<String, String> parametrosPantalla, UserVO usuario) throws ApplicationException, mx.com.ice.services.exception.ApplicationException {
		long t0 = System.currentTimeMillis();

		if (logger.isInfoEnabled()) {
			logger.info("Entrando al metodo ejecutaValidaciones()");
		}

		String cdUnieco = globalVarVo.getValueVariableGlobal(VariableKernel.UnidadEconomica());
		String cdRamo = globalVarVo.getValueVariableGlobal(VariableKernel.CodigoRamo());
		String estado = globalVarVo.getValueVariableGlobal(VariableKernel.Estado());
		String idProducto = globalVarVo.getValueVariableGlobal(VariableKernel.CodigoRamo());
		String cdTipsit = globalVarVo.getValueVariableGlobal(VariableKernel.CodigoTipoSituacion());
		String nmpoliza = globalVarVo.getValueVariableGlobal(VariableKernel.NumeroPoliza());
		String nmSuplem = globalVarVo.getValueVariableGlobal(VariableKernel.NumeroSuplemento());
		String nmSituac = globalVarVo.getValueVariableGlobal(VariableKernel.NumeroSituacion());

		ResultadoTransaccion rt = new ResultadoTransaccion();
		
		// obtener parametros para B1
		Campo[] camposModificados = null;
		camposModificados = obtieneParametrosValidos(parametrosPantalla, "B1");
		
		//Asignar Datos Bloque B1
		rt = ExceptionManager.manage(serviciosGeneralesSistema.asignarDatosBloque(idSesion, ConstantsKernel.BLOQUE_B1, camposModificados, ""));
		
		// serviciosGeneralesSistema.imprimirBloque(idSesion, ConstantsKernel.BLOQUE_B1);

		// Guardar Bloque B1 (actualizar)
		Campo camposId[] = new Campo[2];
		camposId[0] = new Campo("ROWID", "");
		camposId[1] = new Campo("ERROR", "");
		ExceptionManager.manage(serviciosGeneralesBloques.guardarBloqueB1ICE(idSesion, camposId, "U", globalVarVo));
			
		/// /// /// //Validaciones Bloque B1
		/// /// ///rt = ExceptionManager.manage(serviciosGeneralesNegocio.validacionesBloqueSeccionCampo(idSesion, idProducto, ConstantsKernel.BLOQUE_B1, "1", null, globalVarVo));

		// obtener parametros para B5
		camposModificados = obtieneParametrosValidos(parametrosPantalla, "B5");
		logger.debug("Num de campos modificados en B5:" + camposModificados.length);
		for (int i = 0; i < camposModificados.length; i++) {
			logger.debug("campo[" + i + "]= Nombre: " + camposModificados[i].getNombre() + " Tipo: "
					+ camposModificados[i].getTipo() + " Valor:" + camposModificados[i].getValor());
		}
		
		if (camposModificados != null && camposModificados.length != 0) {
			
			//Asignar Datos Bloque B5
			rt = ExceptionManager.manage(serviciosGeneralesSistema.asignarDatosBloque(idSesion, ConstantsKernel.BLOQUE_B5, camposModificados, ""));
			
			//Guardar bloque B5 (actualizar)
			Campo params[] = new Campo[2];
			params[0] = new Campo("ROWID", "");
			params[1] = new Campo("ERROR", "");
			ExceptionManager.manage(serviciosGeneralesBloques.guardarBloqueB5ICE(idSesion, params, "U", globalVarVo));
		}
		
		// Obtenemos solo los parametros validos (que contienen el formato especificado 'nombreParametro_Cx') para B5B
		camposModificados = obtieneParametrosValidos(parametrosPantalla, ConstantsKernel.BLOQUE_B5B);
		
		// Asignar Datos Bloque B5B
		rt = ExceptionManager.manage(serviciosGeneralesSistema.asignarDatosBloque(idSesion, ConstantsKernel.BLOQUE_B5B, camposModificados, ""));
		
		// Guardar Bloque B5B (actualizar)
		Campo[] camposIdE = new Campo[1];
		camposIdE[0] = new Campo("Identificador_Error", "");
		rt = ExceptionManager.manage(serviciosGeneralesBloques.guardarBloqueB5BSelectivoICE(idSesion, camposIdE, "U", camposModificados, globalVarVo));
		
		
		/// /// /// // Validaciones Bloque B5B
		/// /// ///rt = ExceptionManager.manage(serviciosGeneralesNegocio.validacionesBloqueSeccionCampo(idSesion, idProducto,ConstantsKernel.BLOQUE_B5B, "1", null, globalVarVo));
		
		//Validacion de B5B (Nuevo mecanismo de validacion)
		//Se lanza una ApplicationException cuando no se cumplen las validaciones
		WrapperResultados resultadoValidaciones = null;
		resultadoValidaciones = ejecutaPExecValidador(cdUnieco, cdRamo, estado, nmpoliza, nmSituac, ConstantsKernel.BLOQUE_B5B);
		logger.debug("ExecValidador ID=" + resultadoValidaciones.getMsgId());
		logger.debug("ExecValidador TEXT=" + resultadoValidaciones.getMsgText());
		if(StringUtils.isNotBlank(resultadoValidaciones.getMsgId())){
			//Lanzar excepcion con el contenido de msgText, si éste viene vacío, se enviará el msgID
			String mensajeDeValidacion = resultadoValidaciones.getMsgText();
			if( StringUtils.isBlank( resultadoValidaciones.getMsgText() ) ){
				mensajeDeValidacion = resultadoValidaciones.getMsgId();
			}
			throw new mx.com.ice.services.exception.ApplicationException (mensajeDeValidacion);
		}
		
		
		// Servicio Clonar Situacion
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date hoy = new Date();
		String fechaActual = dateFormat.format(hoy).toString();
		String cdElement = usuario.getEmpresa().getElementoId();

		Campo[] campos =  new Campo[1];
		campos[0] = new Campo("FEFECSIT", "");
		String fechaSit = serviciosGeneralesSistema.obtenerDatosBloque(idSesion, ConstantsKernel.BLOQUE_B5, campos).getCampos()[0].getValor();
		if (fechaSit!=null && !"".equals(fechaSit)){
			fechaActual=fechaSit;
		}
		
		// Obtenemos los datos de entrada elegidos por el Usuario
		Map<String, String> paramsCotiza = new HashMap<String, String>();
		paramsCotiza.put("pv_cdunieco_i", globalVarVo.getValueVariableGlobal(VariableKernel.UnidadEconomica()));
		paramsCotiza.put("pv_cdramo_i", globalVarVo.getValueVariableGlobal(VariableKernel.CodigoRamo()));
		paramsCotiza.put("pv_estado_i", globalVarVo.getValueVariableGlobal(VariableKernel.Estado()));
		paramsCotiza.put("pv_nmpoliza_i", globalVarVo.getValueVariableGlobal(VariableKernel.NumeroPoliza()));
		List<DatosEntradaCotizaVO> listaDatosEntradaCotiza = getDatosEntradaCotiza(paramsCotiza);
		
		// Obtenemos la cadena con un formato especifico para solicitud de tarifas
		String cadenaSolicitudTarifas = "";
		if(listaDatosEntradaCotiza != null && !listaDatosEntradaCotiza.isEmpty()){
			cadenaSolicitudTarifas = crearCadenaSolicitudTarifas(listaDatosEntradaCotiza);
		}

		//Clonar Situacion TODO: cambiar después la firma para que devuelva void
		clonarSituacion(cdElement, cdUnieco, cdRamo, estado, nmpoliza, globalVarVo.getValueVariableGlobal(VariableKernel.NumeroSituacion()),
				cdTipsit, usuario.getUser(), fechaActual, cadenaSolicitudTarifas);

		//Lanzar valores por defecto Bloques B19 , B19B y B18
		ejecutaValoresXDefectoB19B18B19B(cdUnieco, cdRamo, estado, nmpoliza, "0",  nmSuplem);

		// Servicio ejecutaSIGSVALIPOL
		ejecutaSIGSVALIPOL(usuario.getUser(), cdElement, cdUnieco, cdRamo, estado, nmpoliza, globalVarVo.getValueVariableGlobal(VariableKernel.NumeroSituacion()), cdTipsit, nmSuplem);

		long t1 = System.currentTimeMillis();
		logger.debug("tiempo en ejecutaValidaciones()= " + ((t1 - t0)) + " mseg ESTO INCLUYE: " + 
			"Asignar datos y actualizar en B1, asignar datos e insertar en B5, asignar datos y actualizar en B5B, clonarSituacion, ejecutaValoresXDefectoB19B18B19B y ejecutaSIGSVALIPOL");

		return globalVarVo;
	}

	/**
	 * Metodo utilizado por Spring para inyectar el atributo
	 * serviciosGeneralesNegocio
	 * 
	 * @param serviciosGeneralesNegocio
	 */
	public void setServiciosGeneralesNegocio(ServiciosGeneralesNegocio serviciosGeneralesNegocio) {
		this.serviciosGeneralesNegocio = serviciosGeneralesNegocio;
	}

	/**
	 * Metodo utilizado por Spring para inyectar el atributo
	 * serviciosGeneralesSistema
	 * 
	 * @param serviciosGeneralesSistema
	 */
	public void setServiciosGeneralesSistema(ServiciosGeneralesSistema serviciosGeneralesSistema) {
		this.serviciosGeneralesSistema = serviciosGeneralesSistema;
	}

	/**
	 * Metodo utilizado por Spring para inyectar el atributo
	 * serviciosGeneralesBloques
	 * 
	 * @param serviciosGeneralesBloques
	 */
	public void setServiciosGeneralesBloques(ServiciosGeneralesBloques serviciosGeneralesBloques) {
		this.serviciosGeneralesBloques = serviciosGeneralesBloques;
	}

	/**
	 * @param endpoints
	 *            the endpoints to set
	 */
	public void setEndpoints(Map<String, Endpoint> endpoints) {
		this.endpoints = endpoints;
	}

	public String calculaNumeroPoliza(String cdUnieco, String cdRamo, String estado) throws ApplicationException {
		long t0 = System.currentTimeMillis();

		//BaseObjectVO baseObjectVO = null;
		String numPoliza = "";
		try {
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("pv_cdunieco_i", cdUnieco);
			parameters.put("pv_cdramo_i", cdRamo);
			parameters.put("pv_estado_i", estado);
			
			
			WrapperResultados result = returnBackBoneInvoke(parameters, CALCULA_NUMERO_POLIZA);
			
			if(result.getItemMap() != null){
				if(result.getItemMap().containsKey("NUMERO_POLIZA")) numPoliza = (String) result.getItemMap().get("NUMERO_POLIZA");
			}
			
			if (logger.isDebugEnabled()) logger.debug("Numero de poliza calculado: " + numPoliza);
			/*
			Endpoint manager = (Endpoint) endpoints.get("CALCULA_NUMERO_POLIZA");
			baseObjectVO = (BaseObjectVO) manager.invoke(parameters);
			numPoliza = baseObjectVO.getValue();
			*/
			
		} catch (Exception ae) {
			logger.error("Exception in invoke 'CALCULA_NUMERO_POLIZA'", ae);
			throw new ApplicationException("Error al obtener el numero de Poliza");
		}
		long t1 = System.currentTimeMillis();
		logger.debug("tiempo en calculaNumeroPoliza()= " + ((t1 - t0)) + " mseg");
		return numPoliza;
	}

	public String generaSuplFisico(String cdUnieco, String cdRamo, String estado, String nmPoliza, String fecha)
			throws ApplicationException {
		long t0 = System.currentTimeMillis();

		logger.debug("***generaSuplFisico***");
		logger.debug("cdUnieco: " + cdUnieco);
		logger.debug("cdRamo: " + cdRamo);
		logger.debug("estado: " + estado);
		logger.debug("nmPoliza: " + nmPoliza);
		logger.debug("fecha: " + fecha);
		//BaseObjectVO baseObjectVO = null;
		String numSuplFisico = "";
		try {
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("pv_cdunieco_i", cdUnieco);
			parameters.put("pv_cdramo_i", cdRamo);
			parameters.put("pv_estado_i", estado);
			parameters.put("pv_nmpoliza_i", nmPoliza);
			parameters.put("pv_fecha_i", fecha);
			parameters.put("pv_hhinival_i", "12:00");

			WrapperResultados result = returnBackBoneInvoke(parameters, GENERA_SUPLEMENTO_FISICO);
			if(result.getItemMap() != null){
				if(result.getItemMap().containsKey("SUPLEMENTO_FISICO")) numSuplFisico = (String) result.getItemMap().get("SUPLEMENTO_FISICO");
			}
			/*Endpoint manager = (Endpoint) endpoints.get("GENERA_SUPLEMENTO_FISICO");
			baseObjectVO = (BaseObjectVO) manager.invoke(parameters);
			numSuplFisico = baseObjectVO.getLabel();
			*/
			if (logger.isDebugEnabled()) logger.debug("Numero de suplemento fisico: " + numSuplFisico);
			
		} catch (Exception bae) {
			logger.error("Exception in invoke 'GENERA_SUPLEMENTO_FISICO'", bae);
			throw new ApplicationException("Error al obtener el numero de Suplemento Fisico");
		}
		long t1 = System.currentTimeMillis();
		logger.debug("tiempo en generaSuplFisico()= " + ((t1 - t0)) + " mseg");
		return numSuplFisico;
	}

	public int generaSuplLogico(String cdUnieco, String cdRamo, String estado, String nmPoliza) throws ApplicationException {
		long t0 = System.currentTimeMillis();

		int numSuplLogico = 0;
		try {
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("pv_cdunieco_i", cdUnieco);
			parameters.put("pv_cdramo_i", cdRamo);
			parameters.put("pv_estado_i", estado);
			parameters.put("pv_nmpoliza_i", nmPoliza);

			WrapperResultados result = returnBackBoneInvoke(parameters, GENERA_SUPLEMENTO_LOGICO);
			if(result.getItemMap() != null){
				if(result.getItemMap().containsKey("SUPLEMENTO_LOGICO")) {
					String suplLogicoTmp = (String)result.getItemMap().get("SUPLEMENTO_LOGICO");
					if(StringUtils.isNotBlank(suplLogicoTmp))numSuplLogico = Integer.parseInt(suplLogicoTmp);
				}
			}
			/*Endpoint manager = (Endpoint) endpoints.get("GENERA_SUPLEMENTO_LOGICO");
			numSuplLogico = (Integer) manager.invoke(parameters);
			*/
			if (logger.isDebugEnabled()) {
				logger.debug("Numero de suplemento logico: " + numSuplLogico);
			}
		} catch (Exception bae) {
			logger.error("Exception in invoke 'GENERA_SUPLEMENTO_LOGICO'", bae);
			throw new ApplicationException("Error al obtener el numero de Suplemento Logico");
		}
		long t1 = System.currentTimeMillis();
		logger.debug("tiempo en generaSuplLogico()= " + ((t1 - t0)) + " mseg");
		return numSuplLogico;
	}
    
    /**
     * 
     * @param cdUnieco
     * @param cdRamo
     * @param estado
     * @param nmPoliza
     * @param fecha
     * @param cdElemento
     * @param usuario
     * @return
     * @throws ApplicationException
     */
    public SuplemVO generaSuplems(String cdUnieco, String cdRamo, String estado, String nmPoliza, String fecha,
            String cdElemento, String usuario, String cdProceso) throws ApplicationException {
        long t0 = System.currentTimeMillis();
        
        logger.debug("-> generaSuplems");
        logger.debug("cdUnieco  : " + cdUnieco);
        logger.debug("cdRamo    : " + cdRamo);
        logger.debug("estado    : " + estado);
        logger.debug("nmPoliza  : " + nmPoliza);
        logger.debug("fecha     : " + fecha);
        logger.debug("cdElemento: " + cdElemento);
        logger.debug("usuario   : " + usuario);
        logger.debug("cdProceso : " + cdProceso);
        SuplemVO suplemVO = null;
        
        try {
            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("CD_UNIECO", cdUnieco);
            parameters.put("CD_RAMO", cdRamo);
            parameters.put("ESTADO", estado);
            parameters.put("NMPOLIZA", nmPoliza);
            parameters.put("FECHA", fecha);
            parameters.put("CD_ELEMENT", cdElemento);
            parameters.put("USUARIO", usuario);
            parameters.put("CD_PROCESO", cdProceso);
        
            Endpoint manager = (Endpoint) endpoints.get("GENERA_SUPLEMENTOS");
            suplemVO = (SuplemVO) manager.invoke(parameters);
            if (logger.isDebugEnabled()) {
                logger.debug("Numero de suplemento: " + suplemVO);
            }
        } catch (BackboneApplicationException bae) {
            logger.error("Exception in invoke 'GENERA_SUPLEMENTOS'", bae);
            throw new ApplicationException("Error al obtener el Suplemento");
        }
        long t1 = System.currentTimeMillis();
        logger.debug("tiempo en generaSuplems()= " + ((t1 - t0)) + " mseg");
        return suplemVO;
    }

	public void movTdescSup(String cdUnieco, String cdRamo, String estado, String nmpoliza, String nSupLogi, String feEfecto,
			String feEmisio, String user, String tipSup) throws ApplicationException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("pv_cdunieco_i", cdUnieco);
		parameters.put("pv_cdramo_i", cdRamo);
		parameters.put("pv_estado_i", estado);
		parameters.put("pv_nmpoliza_i", nmpoliza);
		parameters.put("pv_nsuplogi_i", nSupLogi);
		parameters.put("pv_cdtipsup_i", tipSup);
		parameters.put("pv_feemisio_i", feEfecto);
		parameters.put("pv_nmsolici_i", "0");
		parameters.put("pv_fesolici_i", feEmisio);
		parameters.put("pv_ferefere_i", null);
		parameters.put("pv_cdseqpol_i", null);
		parameters.put("pv_cduser_i", user);
		parameters.put("pv_nusuasus_i", null);
		parameters.put("pv_nlogisus_i", null);
		parameters.put("pv_cdperson_i", null);
		parameters.put("pv_accion_i", "I");
		
		try {
			/*Endpoint endpoint = (Endpoint) endpoints.get("MOV_T_DESC_SUP");
			endpoint.invoke(parameters);*/
			
			returnBackBoneInvoke(parameters, MOV_T_DESC_SUP);
		} catch (Exception bae) {
			logger.error("Exception in invoke PKG_SATELITES.P_MOV_T_DESC_SUP.. ", bae);
			throw new ApplicationException("Error en ... MOV_T_DESC_SUP");
		}
	}

	public void movMSupleme(String cdUnieco, String cdRamo, String estado, String nmpoliza, String nmSuplem, String feIniVig,
			String feFinVig, String nSupLogi) throws ApplicationException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("pv_cdunieco_i", cdUnieco);
		parameters.put("pv_cdramo_i", cdRamo);
		parameters.put("pv_estado_i", estado);
		parameters.put("pv_nmpoliza_i", nmpoliza);
		parameters.put("pv_nmsuplem_i", nmSuplem);
		parameters.put("pv_feINival_i", feIniVig);
		parameters.put("pv_hhinival_i", "12:00");
		parameters.put("pv_fefINval_i", feFinVig);
		parameters.put("pv_hhfinval_i", "12:00");
		parameters.put("pv_swanula_i", "N");
		parameters.put("pv_nsuplogi_i", nSupLogi);
		parameters.put("pv_nsupusua_i", null);
		parameters.put("pv_nsupsess_i", null);
		parameters.put("pv_fesessio_i", null);
		parameters.put("pv_swconfir_i", "N");
		parameters.put("pv_nmrenova_i", "0");
		parameters.put("pv_nsuplori_i", "0");
		parameters.put("pv_cdorddoc_i", null);
		parameters.put("pv_swpolfro_i", "N");
		parameters.put("pv_pocofron_i", "0");
		parameters.put("pv_swpoldec_i", "N");
		parameters.put("pv_tippodec_i", null);
		parameters.put("pv_accion_i", "I");
		
		try {
			/*Endpoint endpoint = (Endpoint) endpoints.get("MOV_M_SUPLEME");
			endpoint.invoke(parameters);*/
			returnBackBoneInvoke(parameters, MOV_M_SUPLEME);
		} catch (Exception bae) {
			logger.error("Exception in invoke PKG_SATELITES.P_MOV_M_SUPLEME.. ", bae);
			throw new ApplicationException("Error en ... MOV_M_SUPLEME");
		}
	}

	public void movMPolagen(String cdUnieco, String cdRamo, String estado, String nmpoliza, String nmSuplem, String cdUniage,
			String cdElemento) throws ApplicationException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("CD_UNIECO", cdUnieco);
		parameters.put("CD_RAMO", cdRamo);
		parameters.put("ESTADO", estado);
		parameters.put("NMPOLIZA", nmpoliza);
		parameters.put("NM_SUPLEM", nmSuplem);
		parameters.put("CD_UNIAGE", cdUniage);
		parameters.put("CD_ELEMENTO", cdElemento);
		try {
			Endpoint endpoint = (Endpoint) endpoints.get("MOV_MPOLAGEN");
			endpoint.invoke(parameters);
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke PKG_SATELITES.P_MOV_MPOLAGEN.. ", bae);
			throw new ApplicationException("Error en ... MOV_MPOLAGEN");
		}
	}

	public void movMPoliagr(String cdUnieco, String cdRamo, String estado, String nmpoliza, String nmSuplem)
			throws ApplicationException {
		Map<String, String> parameters = new HashMap<String, String>();
		
		parameters.put("pv_cdunieco_i", cdUnieco);
		parameters.put("pv_cdramo_i", cdRamo);
		parameters.put("pv_estado_i", estado);
		parameters.put("pv_nmpoliza_i", nmpoliza);
		parameters.put("pv_cdagrupa_i", "1");
		parameters.put("pv_nmsuplem_i", nmSuplem);
		parameters.put("pv_status_i", "V");
		parameters.put("pv_cdperson_i", "1");
		parameters.put("pv_nmorddom_i", "1");
		parameters.put("pv_cdforpag_i", "4");
		parameters.put("pv_cdbanco_i", null);
		parameters.put("pv_cdsucurs_i", null);
		parameters.put("pv_cdcuenta_i", null);
		parameters.put("pv_cdrazon_i", null);
		parameters.put("pv_swregula_i", null);
		parameters.put("pv_cdperreg_i", "1");
		parameters.put("pv_feultreg_i", null);
		parameters.put("pv_cdgestor_i", null);
		parameters.put("pv_cdrol_i", null);
		parameters.put("pv_cdbanco2_i", null);
		parameters.put("pv_cdsucurs2_i", null);
		parameters.put("pv_cdcuenta2_i", null);
		parameters.put("pv_cdtipcta_i", null);
		parameters.put("pv_cdtipcta2_i", null);
		parameters.put("pv_cdpagcom_i", "1");
		parameters.put("pv_nmpresta_i", null);
		parameters.put("pv_nmpresta2_i", null);
		parameters.put("pv_cdbanco3_i", null);
		parameters.put("pv_cdsucurs3_i", null);
		parameters.put("pv_cdcuenta3_i", null);
		parameters.put("pv_cdtipcta3_i", null);
		parameters.put("pv_nmpresta3_i", null);
		parameters.put("pv_nmcuenta_i", null);
		parameters.put("pv_accion_i", "I");

		try {
			/*Endpoint endpoint = (Endpoint) endpoints.get("MOV_MPOLIAGR");
			endpoint.invoke(parameters);*/
			returnBackBoneInvoke(parameters, MOV_MPOLIAGR);
		} catch (Exception bae) {
			logger.error("Exception in invoke PKG_SATELITES.P_MOV_MPOLIAGR.. ", bae);
			throw new ApplicationException("Error en ... MOV_MOV_MPOLIAGR");
		}
	}

	public void movMPoliage(String cdUnieco, String cdRamo, String estado, String nmpoliza, String nmSuplem)
			throws ApplicationException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("pv_cdunieco_i", cdUnieco);
		parameters.put("pv_cdramo_i", cdRamo);
		parameters.put("pv_estado_i", estado);
		parameters.put("pv_nmpoliza_i", nmpoliza);
		parameters.put("pv_cdagente_i", "AON");
		parameters.put("pv_nmsuplem_i", nmSuplem);
		parameters.put("pv_status_i", "V");
		parameters.put("pv_cdtipoag_i", "1");
		parameters.put("pv_porredau_i", "100");
		parameters.put("pv_nmcuadro_i", "DP-6");
		parameters.put("pv_cdsucurs_i", null);
		parameters.put("pv_accion_i", "I");

		try {
			/*Endpoint endpoint = (Endpoint) endpoints.get("MOV_MPOLIAGE");
			endpoint.invoke(parameters);*/
			
			returnBackBoneInvoke(parameters, MOV_MPOLIAGE);
		} catch (Exception bae) {
			logger.error("Exception in invoke PKG_SATELITES.P_MOV_MPOLIAGE.. ", bae);
			throw new ApplicationException("Error en ... MOV_MOV_MPOLIAGE");
		}
	}

	/**
	 * 
	 * @param idSesion
	 * @param nmsituac
	 * @param globalVarVo
	 * @param idProducto
	 * @param cdElemento
	 * @param creaBloques
	 * @param cdGarant
	 * @return Map<String, ValoresXDefectoCoberturaVO>
	 */
	public Map<String, ValoresXDefectoCoberturaVO> valoresXDefectoIncisoCobertura(String idSesion, String nmsituac,
			GlobalVariableContainerVO globalVarVo, String idProducto, String cdElemento, boolean creaBloques, String cdGarant) {
		if (logger.isDebugEnabled()) {
			logger.debug("----> valoresXDefectoIncisoCobertura");
			logger.debug("::: cdGarant : " + cdGarant);
		}

		Map<String, ValoresXDefectoCoberturaVO> valoresXDefectoCoberturaVOMap = new HashMap<String, ValoresXDefectoCoberturaVO>();
		String ptCapita = "0.0";
		boolean excluirGarantias = false;
		ResultadoTransaccion rt = new ResultadoTransaccion();
		try {
			globalVarVo.addVariableGlobal(VariableKernel.NumeroSituacion(), nmsituac);

			// B19
			if (creaBloques) {
				serviciosGeneralesSistema.crearBloque(idSesion, ConstantsKernel.BLOQUE_B19);
			}
			Campo[] camposPoliza = new Campo[9];
			camposPoliza[0] = new Campo(VariableKernel.UnidadEconomica(), globalVarVo.getValueVariableGlobal(VariableKernel
					.UnidadEconomica()));
			camposPoliza[1] = new Campo(VariableKernel.CodigoRamo(), globalVarVo.getValueVariableGlobal(VariableKernel
					.CodigoRamo()));
			camposPoliza[2] = new Campo(VariableKernel.Estado(), globalVarVo.getValueVariableGlobal(VariableKernel.Estado()));
			camposPoliza[3] = new Campo(VariableKernel.NumeroPoliza(), globalVarVo.getValueVariableGlobal(VariableKernel
					.NumeroPoliza()));
			camposPoliza[4] = new Campo(VariableKernel.NumeroSituacion(), globalVarVo.getValueVariableGlobal(VariableKernel
					.NumeroSituacion()));
			camposPoliza[5] = new Campo(VariableKernel.NumeroSuplemento(), globalVarVo.getValueVariableGlobal(VariableKernel
					.NumeroSuplemento()));
			camposPoliza[6] = new Campo(VariableKernel.CodigoTipoSituacion(), globalVarVo.getValueVariableGlobal(VariableKernel
					.CodigoTipoSituacion()));
			if (excluirGarantias) {
				camposPoliza[7] = new Campo(VariableKernel.CodigoTipoSituacion(), globalVarVo
						.getValueVariableGlobal(VariableKernel.CodigoTipoSituacion()));
			}
			camposPoliza[8] = new Campo("vg.CdElemento", cdElemento);

			// //////serviciosGeneralesSistema.imprimirBloque(idSesion,
			// ConstantsKernel.BLOQUE_B19);
			// B18
			String garantiasInsertadas = "";
			if (creaBloques) {
				rt = ExceptionManager.manage(serviciosGeneralesNegocio.valoresDefectoGarantiasB19(idSesion, idProducto,
						camposPoliza, globalVarVo, cdElemento));
				serviciosGeneralesSistema.crearBloque(idSesion, ConstantsKernel.BLOQUE_B18);
				if (rt != null && rt.getCampos() != null && rt.getCampos()[0] != null) {
					garantiasInsertadas = rt.getCampos()[0].getValor();
				}
			} else {
				rt = ExceptionManager.manage(serviciosGeneralesNegocio.valoresDefectoSumasAseguradasB18(idSesion, idProducto,
						camposPoliza, cdGarant, globalVarVo));
				ptCapita = rt.getValor("PTCAPITA");

				// public ResultadoTransaccion
				// valoresDefectoSumasAseguradasB18(String idSesion, String
				// idProducto, Campo[] parametros,
				// String codigoGarantia, GlobalVariableContainerVO globalVarVo)
				// {
			}

			if (!"".equals(garantiasInsertadas)) {
				rt = ExceptionManager.manage(serviciosGeneralesNegocio.valoresDefectoB19BCompleto(idSesion, idProducto,
						camposPoliza, garantiasInsertadas, excluirGarantias, globalVarVo));
			} else {
				if (logger.isDebugEnabled())
					logger.debug(Global.SEPARADOR_TAB + "NO SE LANZA VXD B19B COMPLETO...NO SE INSERTARON NUEVAS GARANTIAS");
				camposPoliza = new Campo[8];
				camposPoliza[0] = new Campo("vg.CdUnieco", globalVarVo.getValueVariableGlobal("vg.CdUnieco"));
				camposPoliza[1] = new Campo("vg.CdRamo", globalVarVo.getValueVariableGlobal("vg.CdRamo"));
				camposPoliza[2] = new Campo("vg.Estado", globalVarVo.getValueVariableGlobal("vg.Estado"));
				camposPoliza[3] = new Campo("vg.NmPoliza", globalVarVo.getValueVariableGlobal("vg.NmPoliza"));
				camposPoliza[4] = new Campo("vg.NmSituac", globalVarVo.getValueVariableGlobal("vg.NmSituac"));
				camposPoliza[5] = new Campo("vg.NmSuplem", globalVarVo.getValueVariableGlobal("vg.NmSuplem"));
				camposPoliza[6] = new Campo("vg.CdTipSit", globalVarVo.getValueVariableGlobal("vg.CdTipSit"));
				camposPoliza[7] = new Campo("vg.CdGarant", cdGarant);
				rt = ExceptionManager.manage(serviciosGeneralesNegocio.valoresDefectoAtributosVariablesGarantiasB19B(idSesion,
						camposPoliza[1].getValor(), camposPoliza, ManejadorSession.obtenerEntidad(idSesion), globalVarVo));

				// ////////////////////////////////////////////////////////////////////
				serviciosGeneralesSistema.imprimirBloque(idSesion, ConstantsKernel.BLOQUE_B19B);
				List listGaran = serviciosGeneralesSistema.obtenerAtributosVariablesGarantia(idSesion, cdGarant);
				Campo[] coberturasN = new Campo[0];
				Campo[] coberturasC = new Campo[0];
				int length = listGaran.size();
				String cob = null;
				for (int i = 0; i < length; i++) {
					cob = new StringBuffer().append("C").append(listGaran.get(i)).append('|').append(cdGarant).toString();
					logger.debug("::::::::: cob : " + cob);
					coberturasN = UtilsServices.addCampo(coberturasN, cob, "");
				}

				for (int i = 0; i < coberturasN.length; i++) {
					logger.debug("::: coberturasN Nombre : " + ((Campo) coberturasN[i]).getNombre());
					logger.debug("::: coberturasN Valor : " + ((Campo) coberturasN[i]).getValor());
					logger.debug("::: coberturasN Tipo : " + ((Campo) coberturasN[i]).getTipo());
				}

				String valorCob = "";
				String tipo = "";
				String nombre = "";
				rt = serviciosGeneralesSistema.obtenerDatosBloque(idSesion, ConstantsKernel.BLOQUE_B19B, coberturasN);

				int rtLength = rt.getCampos().length;
				ValoresXDefectoCoberturaVO valoresXDefectoCoberturaVO = null;

				for (int j = 0; j < rtLength; j++) {
					valoresXDefectoCoberturaVO = new ValoresXDefectoCoberturaVO();
					valorCob = rt.getCampos()[j].getValor();
					tipo = UtileriasKernel.getType(valorCob);
					String nombreCompleto = rt.getCampos()[j].getNombre();
					logger.debug("::: nombreCompleto : " + nombreCompleto);
					String[] cadena = nombreCompleto.split("\\|");
					logger.debug("::: cadena[0] : " + cadena[0]);
					logger.debug("::: cadena[1] : " + cadena[1]);
					nombre = cadena[0].replaceAll("C", "") + '|' + cadena[1];
					// nombre = rt.getCampos()[j].getNombre().replaceAll("C",
					// "");
					coberturasC = UtilsServices.addCampo(coberturasC, nombre, valorCob, tipo);

					if (logger.isDebugEnabled()) {
						logger.debug("::: valorCob : " + valorCob);
						logger.debug("::: tipo : " + tipo);
						logger.debug("::: nombre : " + nombre);
						logger.debug("::: coberturasC : " + coberturasC);
						logger.debug(":::::::::::::::::::::::::::::");
					}

					valoresXDefectoCoberturaVO.setValorCob(valorCob);
					valoresXDefectoCoberturaVO.setTipo(tipo);
					valoresXDefectoCoberturaVO.setNombre(nombre);
					valoresXDefectoCoberturaVO.setPtCapita(ptCapita);
					valoresXDefectoCoberturaVO.setNombreAtributo(cadena[0]);
					valoresXDefectoCoberturaVO.setCobertura(cadena[1]);

					valoresXDefectoCoberturaVOMap.put(cadena[0], valoresXDefectoCoberturaVO);
				}

				Campo[] camposId = new Campo[1];
				camposId[0] = new Campo("Identificador_Error", "");
				for (int i = 0; i < coberturasC.length; i++) {
					logger.debug("::: coberturasC Nombre : " + ((Campo) coberturasC[i]).getNombre());
					logger.debug("::: coberturasC Valor : " + ((Campo) coberturasC[i]).getValor());
					logger.debug("::: coberturasC Tipo : " + ((Campo) coberturasC[i]).getTipo());
				}
				serviciosGeneralesBloques.guardarBloqueB19BSelectivo(idSesion, camposId, "U", coberturasC, globalVarVo);
			}
		} catch (mx.com.ice.services.exception.ApplicationException appExc) {
			logger.error(appExc, appExc);
		}

		logger.debug("::::::: valoresXDefectoCoberturaVOMap : " + valoresXDefectoCoberturaVOMap);
		return valoresXDefectoCoberturaVOMap;
	}

	/**
	 * 
	 * @param idSesion
	 * @param nmsituac
	 * @param globalVarVo
	 * @param idProducto
	 * @param cdElemento
	 * @param creaBloques
	 * @param cdGarant
	 * @return Map<String, ValoresXDefectoCoberturaVO>
	 */
	public Map<String, ValoresXDefectoCoberturaVO> valoresXDefectoIncisoCoberturaEndosos(String idSesion, String nmsituac,
			GlobalVariableContainerVO globalVarVo, String idProducto, String cdElemento, boolean creaBloques, String cdGarant) {
		if (logger.isDebugEnabled()) {
			logger.debug("----> valoresXDefectoIncisoCoberturaEndosos");
			logger.debug("::: cdGarant : " + cdGarant);
		}

		Map<String, ValoresXDefectoCoberturaVO> valoresXDefectoCoberturaVOMap = new HashMap<String, ValoresXDefectoCoberturaVO>();
		String ptCapita = "0.0";
		boolean excluirGarantias = false;
		ResultadoTransaccion rt = new ResultadoTransaccion();
		try {
			globalVarVo.addVariableGlobal(VariableKernel.NumeroSituacion(), nmsituac);

			// B19
			if (creaBloques) {
				serviciosGeneralesSistema.crearBloque(idSesion, ConstantsKernel.BLOQUE_B19);
			}
			Campo[] camposPoliza = new Campo[9];
			camposPoliza[0] = new Campo(VariableKernel.UnidadEconomica(), globalVarVo.getValueVariableGlobal(VariableKernel
					.UnidadEconomica()));
			camposPoliza[1] = new Campo(VariableKernel.CodigoRamo(), globalVarVo.getValueVariableGlobal(VariableKernel
					.CodigoRamo()));
			camposPoliza[2] = new Campo(VariableKernel.Estado(), globalVarVo.getValueVariableGlobal(VariableKernel.Estado()));
			camposPoliza[3] = new Campo(VariableKernel.NumeroPoliza(), globalVarVo.getValueVariableGlobal(VariableKernel
					.NumeroPoliza()));
			camposPoliza[4] = new Campo(VariableKernel.NumeroSituacion(), globalVarVo.getValueVariableGlobal(VariableKernel
					.NumeroSituacion()));
			camposPoliza[5] = new Campo(VariableKernel.NumeroSuplemento(), globalVarVo.getValueVariableGlobal(VariableKernel
					.NumeroSuplemento()));
			camposPoliza[6] = new Campo(VariableKernel.CodigoTipoSituacion(), globalVarVo.getValueVariableGlobal(VariableKernel
					.CodigoTipoSituacion()));
			if (excluirGarantias) {
				camposPoliza[7] = new Campo(VariableKernel.CodigoTipoSituacion(), globalVarVo
						.getValueVariableGlobal(VariableKernel.CodigoTipoSituacion()));
			}
			camposPoliza[8] = new Campo("vg.CdElemento", cdElemento);

			// //////serviciosGeneralesSistema.imprimirBloque(idSesion,
			// ConstantsKernel.BLOQUE_B19);
			// B18
			String garantiasInsertadas = "";
			if (creaBloques) {
				rt = ExceptionManager.manage(serviciosGeneralesNegocio.valoresDefectoGarantiasB19Endosos(idSesion, idProducto,
						camposPoliza, globalVarVo, cdElemento, cdGarant));
				serviciosGeneralesSistema.crearBloque(idSesion, ConstantsKernel.BLOQUE_B18);
				if (rt != null && rt.getCampos() != null && rt.getCampos()[0] != null) {
					garantiasInsertadas = rt.getCampos()[0].getValor();
				}
			} else {
				rt = ExceptionManager.manage(serviciosGeneralesNegocio.valoresDefectoSumasAseguradasB18Endosos(idSesion,
						idProducto, camposPoliza, cdGarant, globalVarVo));
				ptCapita = rt.getValor("PTCAPITA");

				// public ResultadoTransaccion
				// valoresDefectoSumasAseguradasB18(String idSesion, String
				// idProducto, Campo[] parametros,
				// String codigoGarantia, GlobalVariableContainerVO globalVarVo)
				// {
			}

			if (!"".equals(garantiasInsertadas)) {
				rt = ExceptionManager.manage(serviciosGeneralesNegocio.valoresDefectoB19BCompletoEndosos(idSesion, idProducto,
						camposPoliza, garantiasInsertadas, excluirGarantias, globalVarVo));
			} else {
				if (logger.isDebugEnabled())
					logger.debug(Global.SEPARADOR_TAB + "NO SE LANZA VXD B19B COMPLETO...NO SE INSERTARON NUEVAS GARANTIAS");
				camposPoliza = new Campo[8];
				camposPoliza[0] = new Campo("vg.CdUnieco", globalVarVo.getValueVariableGlobal("vg.CdUnieco"));
				camposPoliza[1] = new Campo("vg.CdRamo", globalVarVo.getValueVariableGlobal("vg.CdRamo"));
				camposPoliza[2] = new Campo("vg.Estado", globalVarVo.getValueVariableGlobal("vg.Estado"));
				camposPoliza[3] = new Campo("vg.NmPoliza", globalVarVo.getValueVariableGlobal("vg.NmPoliza"));
				camposPoliza[4] = new Campo("vg.NmSituac", globalVarVo.getValueVariableGlobal("vg.NmSituac"));
				camposPoliza[5] = new Campo("vg.NmSuplem", globalVarVo.getValueVariableGlobal("vg.NmSuplem"));
				camposPoliza[6] = new Campo("vg.CdTipSit", globalVarVo.getValueVariableGlobal("vg.CdTipSit"));
				camposPoliza[7] = new Campo("vg.CdGarant", cdGarant);
				rt = ExceptionManager.manage(serviciosGeneralesNegocio.valoresDefectoAtributosVariablesGarantiasB19BEndosos(
						idSesion, camposPoliza[1].getValor(), camposPoliza, ManejadorSession.obtenerEntidad(idSesion),
						globalVarVo));

				// ////////////////////////////////////////////////////////////////////
				serviciosGeneralesSistema.imprimirBloque(idSesion, ConstantsKernel.BLOQUE_B19B);
				List listGaran = serviciosGeneralesSistema.obtenerAtributosVariablesGarantia(idSesion, cdGarant);
				Campo[] coberturasN = new Campo[0];
				Campo[] coberturasC = new Campo[0];
				int length = listGaran.size();
				String cob = null;
				for (int i = 0; i < length; i++) {
					cob = new StringBuffer().append("C").append(listGaran.get(i)).append('|').append(cdGarant).toString();
					logger.debug("::::::::: cob : " + cob);
					coberturasN = UtilsServices.addCampo(coberturasN, cob, "");
				}

				for (int i = 0; i < coberturasN.length; i++) {
					logger.debug("::: coberturasN Nombre : " + ((Campo) coberturasN[i]).getNombre());
					logger.debug("::: coberturasN Valor : " + ((Campo) coberturasN[i]).getValor());
					logger.debug("::: coberturasN Tipo : " + ((Campo) coberturasN[i]).getTipo());
				}

				String valorCob = "";
				String tipo = "";
				String nombre = "";
				rt = serviciosGeneralesSistema.obtenerDatosBloque(idSesion, ConstantsKernel.BLOQUE_B19B, coberturasN);

				int rtLength = rt.getCampos().length;
				ValoresXDefectoCoberturaVO valoresXDefectoCoberturaVO = null;

				for (int j = 0; j < rtLength; j++) {
					valoresXDefectoCoberturaVO = new ValoresXDefectoCoberturaVO();
					valorCob = rt.getCampos()[j].getValor();
					tipo = UtileriasKernel.getType(valorCob);
					String nombreCompleto = rt.getCampos()[j].getNombre();
					logger.debug("::: nombreCompleto : " + nombreCompleto);
					String[] cadena = nombreCompleto.split("\\|");
					logger.debug("::: cadena[0] : " + cadena[0]);
					logger.debug("::: cadena[1] : " + cadena[1]);
					nombre = cadena[0].replaceAll("C", "") + '|' + cadena[1];
					// nombre = rt.getCampos()[j].getNombre().replaceAll("C",
					// "");
					coberturasC = UtilsServices.addCampo(coberturasC, nombre, valorCob, tipo);

					if (logger.isDebugEnabled()) {
						logger.debug("::: valorCob : " + valorCob);
						logger.debug("::: tipo : " + tipo);
						logger.debug("::: nombre : " + nombre);
						logger.debug("::: coberturasC : " + coberturasC);
						logger.debug(":::::::::::::::::::::::::::::");
					}

					valoresXDefectoCoberturaVO.setValorCob(valorCob);
					valoresXDefectoCoberturaVO.setTipo(tipo);
					valoresXDefectoCoberturaVO.setNombre(nombre);
					valoresXDefectoCoberturaVO.setPtCapita(ptCapita);
					valoresXDefectoCoberturaVO.setNombreAtributo(cadena[0]);
					valoresXDefectoCoberturaVO.setCobertura(cadena[1]);

					valoresXDefectoCoberturaVOMap.put(cadena[0], valoresXDefectoCoberturaVO);
				}

				Campo[] camposId = new Campo[1];
				camposId[0] = new Campo("Identificador_Error", "");
				for (int i = 0; i < coberturasC.length; i++) {
					logger.debug("::: coberturasC Nombre : " + ((Campo) coberturasC[i]).getNombre());
					logger.debug("::: coberturasC Valor : " + ((Campo) coberturasC[i]).getValor());
					logger.debug("::: coberturasC Tipo : " + ((Campo) coberturasC[i]).getTipo());
				}
				serviciosGeneralesBloques.guardarBloqueB19BSelectivoEndosos(idSesion, camposId, "U", coberturasC, globalVarVo);
			}
		} catch (mx.com.ice.services.exception.ApplicationException appExc) {
			logger.error(appExc, appExc);
		}

		logger.debug("::::::: valoresXDefectoCoberturaVOMap : " + valoresXDefectoCoberturaVOMap);
		return valoresXDefectoCoberturaVOMap;
	}

	/**
	 * 
	 */
	public void valoresXDefectoInciso(String idSesion, String nmsituac, GlobalVariableContainerVO globalVarVo, String idProducto,
			String cdElemento, boolean creaBloques, String cdGarant) {
		long t0 = System.currentTimeMillis();

		boolean excluirGarantias = false;
		ResultadoTransaccion rt = new ResultadoTransaccion();
		try {
			globalVarVo.addVariableGlobal(VariableKernel.NumeroSituacion(), nmsituac);

			// B19
			if (creaBloques) {
				serviciosGeneralesSistema.crearBloque(idSesion, ConstantsKernel.BLOQUE_B19);
			}
			Campo[] camposPoliza = new Campo[9];
			camposPoliza[0] = new Campo(VariableKernel.UnidadEconomica(), globalVarVo.getValueVariableGlobal(VariableKernel
					.UnidadEconomica()));
			camposPoliza[1] = new Campo(VariableKernel.CodigoRamo(), globalVarVo.getValueVariableGlobal(VariableKernel
					.CodigoRamo()));
			camposPoliza[2] = new Campo(VariableKernel.Estado(), globalVarVo.getValueVariableGlobal(VariableKernel.Estado()));
			camposPoliza[3] = new Campo(VariableKernel.NumeroPoliza(), globalVarVo.getValueVariableGlobal(VariableKernel
					.NumeroPoliza()));
			camposPoliza[4] = new Campo(VariableKernel.NumeroSituacion(), globalVarVo.getValueVariableGlobal(VariableKernel
					.NumeroSituacion()));
			camposPoliza[5] = new Campo(VariableKernel.NumeroSuplemento(), globalVarVo.getValueVariableGlobal(VariableKernel
					.NumeroSuplemento()));
			camposPoliza[6] = new Campo(VariableKernel.CodigoTipoSituacion(), globalVarVo.getValueVariableGlobal(VariableKernel
					.CodigoTipoSituacion()));
			logger.debug("globalVarVo.getValueVariableGlobal(VariableKernel.CodigoTipoSituacion()="
					+ globalVarVo.getValueVariableGlobal(VariableKernel.CodigoTipoSituacion()));
			if (excluirGarantias) {
				camposPoliza[7] = new Campo(VariableKernel.CodigoTipoSituacion(), globalVarVo
						.getValueVariableGlobal(VariableKernel.CodigoTipoSituacion()));
			}
			camposPoliza[8] = new Campo("vg.CdElemento", cdElemento);

			// //////serviciosGeneralesSistema.imprimirBloque(idSesion,
			// ConstantsKernel.BLOQUE_B19);

			// B18
			String garantiasInsertadas = "";
			if (creaBloques) {
				rt = ExceptionManager.manage(serviciosGeneralesNegocio.valoresDefectoGarantiasB19(idSesion, idProducto,
						camposPoliza, globalVarVo, cdElemento));
				serviciosGeneralesSistema.crearBloque(idSesion, ConstantsKernel.BLOQUE_B18);
				if (rt != null && rt.getCampos() != null && rt.getCampos()[0] != null) {
					garantiasInsertadas = rt.getCampos()[0].getValor();
				}
			} else {

				rt = ExceptionManager.manage(serviciosGeneralesNegocio.valoresDefectoSumasAseguradasB18(idSesion, idProducto,
						camposPoliza, cdGarant, globalVarVo));
				String ptCapita = rt.getValor("PTCAPITA");

				// public ResultadoTransaccion
				// valoresDefectoSumasAseguradasB18(String idSesion, String
				// idProducto, Campo[] parametros,
				// String codigoGarantia, GlobalVariableContainerVO globalVarVo)
				// {
			}

			if (logger.isInfoEnabled()) {
				logger.info(Global.SEPARADOR_SUBPROCESO_TAB + "FIN ::: VXD B19");
				logger.info(Global.SEPARADOR_SUBPROCESO + "INICIO ::: VXD B18");
			}
			/*
			 * public ResultadoTransaccion
			 * valoresDefectoSumasAseguradasB18(String idSesion, String
			 * idProducto,Campo[] parametros, String garantiasInsertadas,boolean
			 * filtrarbloque, GlobalVariableContainerVO globalVarVo){
			 * 
			 * public ResultadoTransaccion
			 * valoresDefectoSumasAseguradasB18(String idSesion, String
			 * idProducto,Campo[] parametros, String codigoGarantia,
			 * GlobalVariableContainerVO globalVarVo){
			 * 
			 */

			if (!"".equals(garantiasInsertadas)) {
				rt = ExceptionManager.manage(serviciosGeneralesNegocio.valoresDefectoSumasAseguradasB18(idSesion, idProducto,
						camposPoliza, garantiasInsertadas, excluirGarantias, globalVarVo));
			} else {
				if (logger.isDebugEnabled())
					logger.debug(Global.SEPARADOR_TAB + "NO SE LANZA VXD B18...NO SE INSERTARON NUEVAS GARANTIAS");

			}
			// //////serviciosGeneralesSistema.imprimirBloque(idSesion,
			// ConstantsKernel.BLOQUE_B18);

			// B19B
			if (creaBloques) {
				serviciosGeneralesSistema.crearBloque(idSesion, ConstantsKernel.BLOQUE_B19B);
			}
			// serviciosGeneralesNegocio.valoresDefectoAtributosVariablesGarantiasB19B(idSesion,
			// idProducto, new Campo[1], new Entidad(), globalVarVo);

			if (!"".equals(garantiasInsertadas)) {
				rt = ExceptionManager.manage(serviciosGeneralesNegocio.valoresDefectoB19BCompleto(idSesion, idProducto,
						camposPoliza, garantiasInsertadas, excluirGarantias, globalVarVo));
			} else {
				if (logger.isDebugEnabled())
					logger.debug(Global.SEPARADOR_TAB + "NO SE LANZA VXD B19B COMPLETO...NO SE INSERTARON NUEVAS GARANTIAS");
			}
			// //////serviciosGeneralesSistema.imprimirBloque(idSesion,
			// ConstantsKernel.BLOQUE_B19B);

		} catch (mx.com.ice.services.exception.ApplicationException appExc) {
			logger.error(appExc, appExc);
		}
		long t1 = System.currentTimeMillis();
		logger.debug("tiempo en valoresXDefectoInciso()= " + ((t1 - t0)) + " mseg");
	}

	@SuppressWarnings("unchecked")
	public List<SituacionVO> clonarSituacion(String cdElement, String cdUnieco, String cdRamo, String estado, String nmpoliza,
			String nmSituac, String cdTipsit, String cdUsuario, String fecha, String cadenaSolicitudTarifas)
			throws ApplicationException {
		long t0 = System.currentTimeMillis();

		List<SituacionVO> clonarSituacList = null;

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("pv_cdelemen_i", cdElement);
		parameters.put("pv_cdunieco_i", cdUnieco);
		parameters.put("pv_cdramo_i", cdRamo);
		parameters.put("pv_estado_i", estado);
		parameters.put("pv_nmpoliza_i", nmpoliza);
		parameters.put("pv_nmsituac_i", nmSituac);
		parameters.put("pv_cdtipsit_i", cdTipsit);
		parameters.put("pv_cdusuario_i", cdUsuario);
		parameters.put("pv_fecha_i", fecha);
		parameters.put("pv_cadena_i", "");
		try {
			/*Endpoint endpoint = (Endpoint) endpoints.get("CLONAR_SITUACION");
			clonarSituacList = (ArrayList<SituacionVO>) endpoint.invoke(parameters);*/
			WrapperResultados resultado = returnBackBoneInvoke(parameters, CLONAR_SITUACION);
			clonarSituacList = resultado.getItemList();
			
		} catch (Exception bae) {
			logger.error("Exception in invoke PKG_COTIZA.P_CLONAR_SITUACION ", bae);
			throw new ApplicationException("Error al Clonar Situacion");
		}
		long t1 = System.currentTimeMillis();
		logger.debug("tiempo en clonarSituacion()= " + ((t1 - t0)) + " mseg");
		return clonarSituacList;
	}

	
	
		
	
	public void ejecutaValoresXDefectoB19B18B19B( String cdUnieco, String cdRamo, String estado,
			String nmpoliza, String nmSituac,  String nmSuplem) throws ApplicationException {
		long t0 = System.currentTimeMillis();

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("pv_cdunieco_i", cdUnieco);
		parameters.put("pv_cdramo_i", cdRamo);
		parameters.put("pv_estado_i", estado);
		parameters.put("pv_nmpoliza_i", nmpoliza);
		parameters.put("pv_nmsituac_i", nmSituac);
		parameters.put("pv_nmsuplem_i", nmSuplem);
		parameters.put("pv_cdgarant_i", "TODO");
		
		mx.com.aon.portal.util.WrapperResultados msj = null;
		
		try {
			/*Endpoint endpoint = (Endpoint) endpoints.get("EJECUTA_P_EXEC_SIGSVDEF");
			msj = (mx.com.aon.portal.util.WrapperResultados)endpoint.invoke(parameters);*/
			msj = returnBackBoneInvoke(parameters, EJECUTA_P_EXEC_SIGSVDEF);
			
		} catch (Exception bae) {
			logger.error("Exception in invoke PKG_COTIZA.EJECUTA_P_EXEC_SIGSVDEF.. ", bae);
			throw new ApplicationException("Error en ... EJECUTA_P_EXEC_SIGSVDEF");
		}
		long t1 = System.currentTimeMillis();
		logger.debug("tiempo en EJECUTA_P_EXEC_SIGSVDEF()= " + ((t1 - t0)) + " mseg");
	}
	
	
	
	public void ejecutaSIGSVALIPOL(String usuario, String cdElement, String cdUnieco, String cdRamo, String estado,
			String nmpoliza, String nmSituac, String cdTipsit, String nmSuplem) throws ApplicationException {
		long t0 = System.currentTimeMillis();

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("pv_cdusuari_i", usuario);
		parameters.put("pv_cdelemen_i", cdElement);
		parameters.put("pv_cdunieco_i", cdUnieco);
		parameters.put("pv_cdramo_i", cdRamo);
		parameters.put("pv_estado_i", estado);
		parameters.put("pv_nmpoliza_i", nmpoliza);
		parameters.put("pv_nmsituac_i", nmSituac);
		parameters.put("pv_cdtipsit_i", cdTipsit);
		parameters.put("pv_nmsuplem_i", nmSuplem);
		try {
			voidReturnBackBoneInvoke(parameters,"EJECUTA_SIGSVALIPOL");
		} catch (ApplicationException ae) {
			logger.error("Exception in invoke PKG_COTIZA.EJECUTA_SIGSVALIPOL.. ", ae);
			throw new ApplicationException("Error en ... EJECUTA_SIGSVALIPOL");
		}
		long t1 = System.currentTimeMillis();
		logger.debug("tiempo en ejecutaSIGSVALIPOL()= " + ((t1 - t0)) + " mseg");
	}

	public void procIncisoDef(String cdUnieco, String cdRamo, String estado, String nmpoliza, String nmSituac, String cdElement,
			String cdPerson, String cdCiaaseg, String cdPlan, String cdPerpag) throws ApplicationException {
		long t0 = System.currentTimeMillis();

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("CD_UNIECO", cdUnieco);
		parameters.put("CD_RAMO", cdRamo);
		parameters.put("ESTADO", estado);
		parameters.put("NMPOLIZA", nmpoliza);
		parameters.put("NMSITUAC", nmSituac);
		parameters.put("CD_ELEMENT", cdElement);
		parameters.put("CD_PERSON", cdPerson);
		parameters.put("pv_cdasegur", cdCiaaseg);
		parameters.put("pv_cdplan", cdPlan);
		parameters.put("pv_cdperpag", cdPerpag);
		try {
			Endpoint endpoint = (Endpoint) endpoints.get("PROC_INCISO_DEF");
			endpoint.invoke(parameters);
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke PKG_COTIZA.PROC_INCISO_DEF .. ", bae);
			throw new ApplicationException("Error en ... PROC_INCISO_DEF");
		}
		long t1 = System.currentTimeMillis();
		logger.debug("tiempo en procIncisoDef()= " + ((t1 - t0)) + " mseg");

	}

	private WrapperResultados procesoEmision(String usuario, String cdUnieco, String cdRamo, String estado, String nmpoliza,
			String nmSituac, String nmSuplem, String cdElement, String cdCia, String cdPlan, String cdPerpag, String cdPerson,
			String fecha) throws ApplicationException {
		

		Map<String, String> parameters = new HashMap<String, String>();
		
		parameters.put("pv_cdusuari", usuario);
		parameters.put("pv_cdunieco", cdUnieco);
		parameters.put("pv_cdramo", cdRamo);
		parameters.put("pv_estado", estado);
		parameters.put("pv_nmpoliza", nmpoliza);
		parameters.put("pv_nmsituac", nmSituac);
		parameters.put("pv_nmsuplem", nmSuplem);
		parameters.put("pv_cdelement", cdElement);
		parameters.put("pv_cdcia", cdCia);
		parameters.put("pv_cdplan", cdPlan);
		parameters.put("pv_cdperpag", cdPerpag);
		parameters.put("pv_cdperson", cdPerson);
		parameters.put("pv_fecha", fecha);
		
		//mx.com.aon.portal.util.WrapperResultados mensaje = null;
		
		
		 return returnResult(parameters, PROCESO_EMISION_DAO);
		
		
		/*try {
			Endpoint endpoint = (Endpoint) endpoints.get("PROCESO_EMISION");
			mensaje = (mx.com.aon.portal.util.WrapperResultados) endpoint.invoke(parameters);

		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke PKG_EMISION.P_PROCESO_EMISION .. ", bae);
			throw new ApplicationException("Error en ... PROCESO_EMISION");
		}
		long t1 = System.currentTimeMillis();
		logger.debug("tiempo en procesoEmision()= " + ((t1 - t0)) + " mseg");
		 */
		
		//return mensaje.getMsgText();
	}

	/**
	 * Tomar los valores de la pantalla y llenar Campo[]. Pueden llegarnos mas
	 * parametros de los necesarios, solo tomaremos los parametros que contengan
	 * el formato 'nombreParametro_Cx'
	 * 
	 * @param parametrosMap
	 * @return
	 */
	private Campo[] obtieneParametrosValidos(Map<String, String> parametrosPantalla, String bloque) {
		long t0 = System.currentTimeMillis();
		Campo[] camposModificados = null;

		// Tomar los valores de los parametros validos(ya que pueden venir otros
		// que no queramos) para asignarlos a mapaParamsValidos
		Map<String, String> mapaParamsValidos = new HashMap<String, String>();
		logger.debug("obteniendo parametros de ////=" + bloque);
		if ("B5B".equals(bloque) || "B1B".equals(bloque)) {
			if (parametrosPantalla != null) {
				for (Iterator<String> it = parametrosPantalla.keySet().iterator(); it.hasNext();) {
					String clave = it.next();
					if (clave.contains("_") && (clave.contains("B5B") || clave.contains("B1B"))) {
						// Si contienen '_' dividimos el arreglo y tambien
						// quitamos las 'C'. C1 -> 1
						String[] arrClave = clave.replaceAll("C", "").split("_");
						if (arrClave.length > 1) {
							// Tomamos el ultimo elemento en donde se supone que
							// debe estar el numero que queremos
							String claveNew = arrClave[arrClave.length - 1];
							if (NumberUtils.isNumber(claveNew)) {
								// Si es un numero, añadimos un elemento al mapa
								logger.debug("Clave ====" + clave);
								String valor = (String) parametrosPantalla.get(clave);
								logger.debug("valor ====" + valor);
								mapaParamsValidos.put(claveNew, valor);
							}
						}
					}
				}
			}
			// Llenar el arreglo camposModificados con el contenido del mapa
			// mapaParamsValidos
			camposModificados = new Campo[mapaParamsValidos.size()];
			int i = 0;
			for (Iterator<String> it = mapaParamsValidos.keySet().iterator(); it.hasNext();) {
				String parametro = it.next();
				camposModificados[i] = new Campo(parametro, mapaParamsValidos.get(parametro));
				i++;
			}
		} else if (ConstantsKernel.BLOQUE_B6.equals(bloque) || ConstantsKernel.BLOQUE_B6B.equals(bloque)) {
			String claveBloque = "";
			if (parametrosPantalla != null) {
				for (Iterator<String> it = parametrosPantalla.keySet().iterator(); it.hasNext();) {
					String clave = it.next();
					logger.debug("::: clave : " + clave);
					if (clave.contains("_")
							&& (clave.contains(ConstantsKernel.BLOQUE_B6) || clave.contains(ConstantsKernel.BLOQUE_B6B))) {
						// Si contienen '_' dividimos el arreglo y tambien
						// quitamos las 'C'. C1 -> 1
						String[] arrClave = null;
						String[] cadena = null;
						String nuevaClave = null;
						if (ConstantsKernel.BLOQUE_B6B.equals(bloque)) {
							cadena = clave.split("\\_");
							logger.debug("clave: " + clave);
							logger.debug("cadena: " + cadena + "   Longitud: " + cadena.length);
							logger.debug("::: cadena[0] : " + cadena[0]);
							logger.debug("::: cadena[1] : " + cadena[1]);
							logger.debug("::: cadena[2] : " + cadena[2]);
							nuevaClave = new StringBuffer().append(cadena[0]).append('_').append(cadena[1])// .append(cadena[1].replaceAll("C",
																											// ""))
									.append('_').append(cadena[2]).toString();
							logger.debug("::: nueva clave : " + nuevaClave);
							arrClave = nuevaClave.split("_");
							arrClave = clave.replaceAll("C", "").split("_");
						} else {
							arrClave = clave.split("_");
						}

						if (arrClave.length > 2) {
							// Tomamos el ultimo elemento en donde se supone que
							// debe estar el numero que queremos
							String tmp = arrClave[arrClave.length - 1];
							String claveMonto = "";
							String claveAccesorio = "";
							String campo = arrClave[arrClave.length - 2];
							if (ConstantsKernel.BLOQUE_B6B.equals(bloque)) {
								claveAccesorio = tmp;
								claveBloque = new StringBuffer().append(campo).append("|").append(claveAccesorio).toString();
							} else if (ConstantsKernel.BLOQUE_B6.equals(bloque)) {
								claveMonto = tmp;
								claveBloque = new StringBuffer().append(campo).append("|").append(claveMonto).toString();
							}

							if (clave.contains("B6_") && ConstantsKernel.BLOQUE_B6.equals(bloque)) {
								logger.debug("... parametros para el Bloque " + ConstantsKernel.BLOQUE_B6);
								String valor = (String) parametrosPantalla.get(clave);
								logger.debug("metiendo claveBloque=" + campo);
								logger.debug("metiendo valor=" + valor);
								mapaParamsValidos.put(campo, valor);
							} else if (clave.contains(ConstantsKernel.BLOQUE_B6B) && ConstantsKernel.BLOQUE_B6B.equals(bloque)) {
								logger.debug("... parametros para el Bloque " + ConstantsKernel.BLOQUE_B6B);
								String valor = (String) parametrosPantalla.get(clave);
								logger.debug("metiendo claveBloque=" + campo);
								logger.debug("metiendo valor=" + valor);
								mapaParamsValidos.put(campo, valor);
							}

							// }
						}
					}
				}
			}
			// Llenar el arreglo camposModificados con el contenido del mapa
			// mapaParamsValidos
			camposModificados = new Campo[mapaParamsValidos.size()];
			int i = 0;
			for (Iterator<String> it = mapaParamsValidos.keySet().iterator(); it.hasNext();) {
				String parametro = it.next();
				camposModificados[i] = new Campo(parametro, mapaParamsValidos.get(parametro));
				i++;
			}
		} else if ("B18".equals(bloque) || "B19B".equals(bloque)) {
			String claveBloque = "";
			if (parametrosPantalla != null) {
				for (Iterator<String> it = parametrosPantalla.keySet().iterator(); it.hasNext();) {
					String clave = it.next();
					logger.debug("::: clave : " + clave);
					if (clave.contains("_") && (clave.contains("B18") || clave.contains("B19B"))) {
						// Si contienen '_' dividimos el arreglo y tambien
						// quitamos las 'C'. C1 -> 1
						String[] arrClave = null;
						String[] cadena = null;
						String nuevaClave = null;
						if ("B19B".equals(bloque)) {
							cadena = clave.split("\\_");
							logger.debug("clave: " + clave);
							logger.debug("cadena: " + cadena + "   Longitud: " + cadena.length);
							logger.debug("::: cadena[0] : " + cadena[0]);
							logger.debug("::: cadena[1] : " + cadena[1]);
							logger.debug("::: cadena[2] : " + cadena[2]);
							nuevaClave = new StringBuffer().append(cadena[0]).append('_').append(cadena[1].replaceAll("C", ""))
									.append('_').append(cadena[2]).toString();
							logger.debug("::: nueva clave : " + nuevaClave);
							arrClave = nuevaClave.split("_");
							// arrClave = clave.replaceAll("C", "").split("_");
						} else {
							arrClave = clave.split("_");
						}

						logger.debug("::: arrClave.length : " + arrClave.length);
						if (arrClave.length > 2) {
							// Tomamos el ultimo elemento en donde se supone que
							// debe estar el numero que queremos
							String tmp = arrClave[arrClave.length - 1];
							String claveCapital = "";
							String claveCobertura = "";
							String campo = arrClave[arrClave.length - 2];
							logger.debug("::: bloque : " + bloque);
							if ("B19B".equals(bloque)) {
								claveCobertura = tmp;
								logger.debug("::: claveCobertura : " + claveCobertura);
								claveBloque = new StringBuffer().append(campo).append("|").append(claveCobertura).toString();
							} else if ("B18".equals(bloque)) {
								claveCapital = tmp;
								logger.debug("::: claveCapital : " + claveCapital);
								claveBloque = new StringBuffer().append(campo).append("|").append(claveCapital).toString();
							}

							// if(NumberUtils.isNumber(claveNew)){
							// Si es un numero, añadimos un elemento al mapa
							// if ((clave.startsWith("B19B") &&
							// "B19B".equals(bloque))|| (clave.startsWith("B18")
							// && "B18".equals(bloque))){
							logger.debug("::::: clave : " + clave);
							logger.debug("::::: bloque : " + bloque);
							if ((clave.contains("B19B") && "B19B".equals(bloque))
									|| (clave.contains("B18") && "B18".equals(bloque))) {
								String valor = (String) parametrosPantalla.get(clave);
								logger.debug("metiendo claveBloque=" + claveBloque);
								logger.debug("metiendo valor=" + valor);
								mapaParamsValidos.put(claveBloque, valor);
							}

							// }
						}
					}
				}
			}
			// Llenar el arreglo camposModificados con el contenido del mapa
			// mapaParamsValidos
			camposModificados = new Campo[mapaParamsValidos.size()];
			int i = 0;
			for (Iterator<String> it = mapaParamsValidos.keySet().iterator(); it.hasNext();) {
				String parametro = it.next();
				camposModificados[i] = new Campo(parametro, mapaParamsValidos.get(parametro));
				i++;
			}
		} else if ("B1".equals(bloque)) {
			if (parametrosPantalla != null) {
				logger.debug("antes de b1 parametro");
				for (Iterator<String> it = parametrosPantalla.keySet().iterator(); it.hasNext();) {
					String clave = it.next();
					logger.debug("clave b1 =" + clave);

					if (clave.contains("_") && (clave.contains("B1"))) {
						// Si contienen '_' dividimos el arreglo y tambien
						// quitamos las 'C'. C1 -> 1
						String[] arrClave = clave.split("_");
						logger.debug("arrClave.length =" + arrClave.length);
						if (arrClave.length > 1) {
							// Tomamos el ultimo elemento en donde se supone que
							// debe estar el numero que queremos
							String claveNew = arrClave[arrClave.length - 1];
							// if (NumberUtils.isNumber(claveNew)) {
							// Si es un numero, añadimos un elemento al mapa
							logger.debug("Clave ====" + clave);
							logger.debug("claveNew ====" + claveNew);
							String valor = (String) parametrosPantalla.get(clave);
							logger.debug("valor ====" + valor);
							mapaParamsValidos.put(claveNew, valor);
							// }
						}
					}
				}
				// Llenar el arreglo camposModificados con el contenido del mapa
				// mapaParamsValidos
				camposModificados = new Campo[mapaParamsValidos.size()];
				int i = 0;
				for (Iterator<String> it = mapaParamsValidos.keySet().iterator(); it.hasNext();) {
					String parametro = it.next();
					camposModificados[i] = new Campo(parametro, mapaParamsValidos.get(parametro));
					i++;
				}
			}

		} else if ("B5".equals(bloque)) {
			if (parametrosPantalla != null) {
				logger.debug("antes de b5 parametro");
				for (Iterator<String> it = parametrosPantalla.keySet().iterator(); it.hasNext();) {
					String clave = it.next();
					logger.debug("clave b5 =" + clave);

					if (clave.contains("_") && (clave.contains("B5") && (!clave.contains("B5B")))) {
						// Si contienen '_' dividimos el arreglo y tambien
						// quitamos las 'C'. C1 -> 1
						String[] arrClave = clave.split("_");
						logger.debug("arrClave.length =" + arrClave.length);
						if (arrClave.length > 1) {
							// Tomamos el ultimo elemento en donde se supone que
							// debe estar el numero que queremos
							String claveNew = arrClave[arrClave.length - 1];
							// if (NumberUtils.isNumber(claveNew)) {
							// Si es un numero, añadimos un elemento al mapa
							logger.debug("Clave ====" + clave);
							logger.debug("claveNew ====" + claveNew);
							String valor = (String) parametrosPantalla.get(clave);
							logger.debug("valor ====" + valor);
							mapaParamsValidos.put(claveNew, valor);
							// }
						}
					}
				}
				// Llenar el arreglo camposModificados con el contenido del mapa
				// mapaParamsValidos
				camposModificados = new Campo[mapaParamsValidos.size()];
				int i = 0;
				for (Iterator<String> it = mapaParamsValidos.keySet().iterator(); it.hasNext();) {
					String parametro = it.next();
					camposModificados[i] = new Campo(parametro, mapaParamsValidos.get(parametro));
					i++;
				}
			}

		} else {
			// Sino, camposModificados tendrá cero elementos
			camposModificados = new Campo[mapaParamsValidos.size()];
		}

		if (logger.isDebugEnabled()) {
			for (int j = 0; j < camposModificados.length; j++) {
				logger.debug("camposModificados[" + j + "]= " + camposModificados[j]);
			}
			logger.debug("camposModificados.length: " + camposModificados.length);
			for (Iterator<String> it = mapaParamsValidos.keySet().iterator(); it.hasNext();) {
				String clave = it.next();
				String valor = mapaParamsValidos.get(clave);
				logger.debug("Clave: " + clave);
				logger.debug("Valor: " + valor);
			}
		}
		long t1 = System.currentTimeMillis();
		logger.debug("tiempo en obtieneParametrosValidos()= " + ((t1 - t0)) + " mseg");
		return camposModificados;
	}

	public void cargarBloques(String idSesion, GlobalVariableContainerVO globalVarVo, UserVO usuario) throws ApplicationException {
		long t0 = System.currentTimeMillis();

		cargarBloquesPoliza(idSesion, globalVarVo, usuario);
		cargarBloquesIncisos(idSesion, globalVarVo, usuario);

		long t1 = System.currentTimeMillis();
		logger.debug("tiempo en cargarBloques()= " + ((t1 - t0)) + " mseg");
	}

	public void cargarBloquesPoliza(String idSesion, GlobalVariableContainerVO globalVarVo, UserVO usuario)
			throws ApplicationException {
		long t0 = System.currentTimeMillis();

		// Cargar B1
		// ExceptionManager.manage(serviciosGeneralesBloques.cargarBloqueB1(idSesion,
		// campos));
		logger.debug("Cargando bloques=" + serviciosGeneralesCargarBloques);
		serviciosGeneralesCargarBloques.cargarBloqueB1(idSesion, globalVarVo);
		logger.debug("Cargado bloque b1");
		serviciosGeneralesSistema.imprimirBloque(idSesion, "B1");

		// Cargar B1B
		serviciosGeneralesCargarBloques.cargarBloqueB1B(idSesion, globalVarVo);
		logger.debug("Cargado bloque b1b");
		serviciosGeneralesSistema.imprimirBloque(idSesion, "B1B");

		// bloque B2
		// ExceptionManager.manage(serviciosCargarBloques.cargarBloqueB2(idSesion,
		// globalVarVo));
		// serviciosGeneralesCargarBloques.cargarBloqueB2(idSesion,
		// globalVarVo);
		// logger.debug("Cargado bloque b2");
		// serviciosGeneralesSistema.imprimirBloque(idSesion, "B2");

		long t1 = System.currentTimeMillis();
		logger.debug("tiempo en cargarBloquesPoliza()= " + ((t1 - t0)) + " mseg");
	}

	/**
	 * @param idSesion
	 * @param globalVarVo
	 * @param usuario
	 * @exception ApplicationException
	 */
	public void cargarBloquesIncisos(String idSesion, GlobalVariableContainerVO globalVarVo, UserVO usuario)
			throws ApplicationException {
		logger.debug("-----> cargarBloquesIncisos");
		long t0 = System.currentTimeMillis();

		// B5
		// ExceptionManager.manage(serviciosCargarBloques.cargarBloqueB5(idSesion,
		// globalVarVo));
		serviciosGeneralesCargarBloques.cargarBloqueB5(idSesion, globalVarVo);
		serviciosGeneralesSistema.imprimirBloque(idSesion, "B5");
		logger.debug("Cargado bloque b5");

		// B5B
		serviciosGeneralesCargarBloques.cargarBloqueB5B(idSesion, globalVarVo);// revisar
		logger.debug("Cargado bloque b5b");
		serviciosGeneralesSistema.imprimirBloque(idSesion, "B5B");

		// B19
		serviciosGeneralesCargarBloques.cargarBloqueB19(idSesion, globalVarVo);
		logger.debug("Cargado bloque b19");
		serviciosGeneralesSistema.imprimirBloque(idSesion, "B19");

		// B18
		// ExceptionManager.manage(serviciosCargarBloques.cargarBloqueB18(idSesion,
		// globalVarVo));
		serviciosGeneralesCargarBloques.cargarBloqueB18(idSesion, globalVarVo);
		logger.debug("Cargado bloque b18");
		serviciosGeneralesSistema.imprimirBloque(idSesion, "B18");

		// b19b
		// ExceptionManager.manage(getServiciosGeneralesBloques().cargarTodoB19B(idSession,
		// camposB19B, vgCdTipSit));
		/*
		 * 		 */

		//String codigoGarantia = "";
		// Entidad entidad =ManejadorSession.obtenerEntidad(idSesion);
		/*
		 * ManejadorSession man=null; Bloque bloque=null; try{
		 * 
		 * 
		 * if (entidad.containBloque("B19")) { bloque =
		 * entidad.getBloque("B19"); } else {
		 * 
		 * if (logger.isDebugEnabled()) { logger.debug("Error ocurrido en
		 * servicio obtener datos bloque B19: obtener bloque b19"); }
		 *  }
		 * 
		 * }catch (BloqueException e){ logger.error(e,e); }
		 * 
		 * 
		 * HashMap listaCampos = bloque.getListaDeCampos(); //
		 * logger.debug("listaCampos="+listaCampos.size()); ArrayList
		 * listaCodGarantia = new
		 * ServiciosUtileriasBloques().obtenerCodigosGarantias(listaCampos); //
		 * logger.debug("listaCodGarantia="+listaCodGarantia.size()); Iterator
		 * iterador = listaCodGarantia.iterator(); while (iterador.hasNext()) {
		 * codigoGarantia = (String) iterador.next(); //parametrosB19B[7] = new
		 * Campo("CDGARANT", codigoGarantia);
		 */
		serviciosGeneralesCargarBloques.cargarTodoB19B(idSesion, globalVarVo, globalVarVo.getValueVariableGlobal("vg.CdTipSit"));
		// }

		logger.debug("Cargado bloque b19b");
		serviciosGeneralesSistema.imprimirBloque(idSesion, "B19B");

		// ////////////////////////////////
		logger.debug("::::::::::: Cargando bloque b6");
		// Campo[] campos = new Campo[1];

		ResultadoTransaccion rt = serviciosGeneralesSistema.bloqueCargado(idSesion, "B6");
		// cargadoB6 = rt.getCampos()[0].getValor();

		// Paso 6)
		if ("FALSE".equalsIgnoreCase(rt.getCampos()[0].getValor())) {
			Campo[] parametrosB6B = new Campo[8];

			parametrosB6B[0] = new Campo("NMOBJETO", "");
			logger.debug("::: parametrosB6B[0] :: " + parametrosB6B[0]);
			// rt =
			// ExceptionManager.manage(serviciosGeneralesBloques.cargarBloqueB6(idSession,
			// campos));
			serviciosGeneralesCargarBloques.cargarBloqueB6(idSesion, parametrosB6B, globalVarVo);
		}
		logger.debug("Cargado bloque b6");
		serviciosGeneralesSistema.imprimirBloque(idSesion, "B6");

		// ///////////////////////////////////////////////////////
		logger.debug("::::::::::: Cargando bloque b6b");
		// serviciosGeneralesCargarBloques.cargarBloqueB6B(idSesion,
		// parametrosB6B, globalVarVo);
		try {
			rt = ExceptionManager.manage(serviciosGeneralesCargarBloques.cargarTodoB6B(idSesion, null, globalVarVo));
		} catch (mx.com.ice.services.exception.ApplicationException appExc) {
			logger.error("Error al cargar B6B: " + appExc.toString(), appExc);
			// throw new ApplicationException(appExc.toString());
		}
		logger.debug("::::::::::: Cargado bloque b6b");
		serviciosGeneralesSistema.imprimirBloque(idSesion, "B6B");
		// ////////////////////////////////

		long t1 = System.currentTimeMillis();
		logger.debug("tiempo en cargarBloquesIncisos()= " + ((t1 - t0)) + " mseg");
	}

	public ResultadoTransaccion guardaDatosAdicionales(String idSesion, GlobalVariableContainerVO globalVarVo,
			Map<String, String> parametrosPantalla) throws ApplicationException {

		String idProducto = globalVarVo.getValueVariableGlobal(VariableKernel.CodigoRamo());
		ResultadoTransaccion rt = null;
		Campo[] camposModificados = null;

		// Obtenemos solo los parametros validos (que contienen el formato
		// especificado 'nombreParametro_Cx'
		// B1B
		camposModificados = obtieneParametrosValidos(parametrosPantalla, ConstantsKernel.BLOQUE_B1B);
		try {
			// Asignar Datos al bloque
			rt = ExceptionManager.manage(serviciosGeneralesSistema.asignarDatosBloque(idSesion, ConstantsKernel.BLOQUE_B1B,
					camposModificados, ""));
			// Guardar B1B
			serviciosGeneralesNegocio.guardarAtributosVariablesPolizaB1B(idSesion, globalVarVo);
			// Lanzar validaciones B1B
			rt = ExceptionManager.manage(serviciosGeneralesNegocio.validacionesBloqueSeccionCampo(idSesion, idProducto,
					ConstantsKernel.BLOQUE_B1B, "1", null, globalVarVo));
			if (logger.isDebugEnabled()) {
				serviciosGeneralesSistema.imprimirBloque(idSesion, ConstantsKernel.BLOQUE_B1B);
			}
		} catch (mx.com.ice.services.exception.ApplicationException appExc) {
			logger.error("Error al guardar datos Adicionales: " + appExc.toString(), appExc);
			throw new ApplicationException(appExc.toString());
		}
		return rt;
	}

	public void crearBloques(String idSesion, String bloque) throws ApplicationException {

		long t0 = System.currentTimeMillis();

		// Creo la session de negocio o donde almaceno la informacion de los
		// bloques
		serviciosGeneralesSistema.crearSesion(idSesion);
		serviciosGeneralesSistema.crearEntidad(idSesion);

		// Se crean TODOS los bloques
		ResultadoTransaccion rt = new ResultadoTransaccion();
		try {

			if (ConstantsKernel.BLOQUE_B1.equalsIgnoreCase(bloque)) {
				// Crear B1
				rt = ExceptionManager.manage(serviciosGeneralesSistema.crearBloque(idSesion, ConstantsKernel.BLOQUE_B1));	
			} else if (ConstantsKernel.BLOQUE_B1B.equalsIgnoreCase(bloque)) {
				// Crear B1B
				rt = ExceptionManager.manage(serviciosGeneralesSistema.crearBloque(idSesion, ConstantsKernel.BLOQUE_B1B));	
			} else if (ConstantsKernel.BLOQUE_B5.equalsIgnoreCase(bloque)) {
				// Crear B5
				rt = ExceptionManager.manage(serviciosGeneralesSistema.crearBloque(idSesion, ConstantsKernel.BLOQUE_B5));	
			} else if (ConstantsKernel.BLOQUE_B5B.equalsIgnoreCase(bloque)) {
				// Crear B5B
				rt = ExceptionManager.manage(serviciosGeneralesSistema.crearBloque(idSesion, ConstantsKernel.BLOQUE_B5B));				
			} else if (ConstantsKernel.BLOQUE_B19.equalsIgnoreCase(bloque)) {
				// Crear B19
				rt = ExceptionManager.manage(serviciosGeneralesSistema.crearBloque(idSesion, ConstantsKernel.BLOQUE_B19));				
			} else if (ConstantsKernel.BLOQUE_B18.equalsIgnoreCase(bloque)) {
				// Crear B18
				rt = ExceptionManager.manage(serviciosGeneralesSistema.crearBloque(idSesion, ConstantsKernel.BLOQUE_B18));				
			} else if (ConstantsKernel.BLOQUE_B19B.equalsIgnoreCase(bloque)) {
				// Crear B19B
				rt = ExceptionManager.manage(serviciosGeneralesSistema.crearBloque(idSesion, ConstantsKernel.BLOQUE_B19B));				
			} else if (ConstantsKernel.BLOQUE_B6.equalsIgnoreCase(bloque)) {
				// Crear B6
				rt = ExceptionManager.manage(serviciosGeneralesSistema.existeBloque(idSesion, ConstantsKernel.BLOQUE_B6));
				
				if ("FALSE".equalsIgnoreCase(rt.getCampos()[0].getValor())) {
					rt = ExceptionManager.manage(serviciosGeneralesSistema.crearBloque(idSesion, ConstantsKernel.BLOQUE_B6));
				}
			} else if (ConstantsKernel.BLOQUE_B6.equalsIgnoreCase(bloque)) {
				// Crear B6B
				rt = ExceptionManager.manage(serviciosGeneralesSistema.existeBloque(idSesion, ConstantsKernel.BLOQUE_B6B));
				if ("FALSE".equalsIgnoreCase(rt.getCampos()[0].getValor())) {
					rt = ExceptionManager.manage(serviciosGeneralesSistema.crearBloque(idSesion, ConstantsKernel.BLOQUE_B6B));
				}	
			}			
		} catch (mx.com.ice.services.exception.ApplicationException appExc) {
			logger.error(appExc, appExc);
		}

		long t1 = System.currentTimeMillis();
		logger.debug("tiempo en crearBloques()= " + ((t1 - t0)) + " mseg");
	}
	
	public void crearBloques(String idSesion) throws ApplicationException {

		long t0 = System.currentTimeMillis();

		// Creo la session de negocio o donde almaceno la informacion de los
		// bloques
		serviciosGeneralesSistema.crearSesion(idSesion);
		serviciosGeneralesSistema.crearEntidad(idSesion);

		// Se crean TODOS los bloques
		ResultadoTransaccion rt = new ResultadoTransaccion();
		try {

			// Crear B1
			rt = ExceptionManager.manage(serviciosGeneralesSistema.crearBloque(idSesion, ConstantsKernel.BLOQUE_B1));
			// Crear B1B
			rt = ExceptionManager.manage(serviciosGeneralesSistema.crearBloque(idSesion, ConstantsKernel.BLOQUE_B1B));
			// Crear B5
			rt = ExceptionManager.manage(serviciosGeneralesSistema.crearBloque(idSesion, ConstantsKernel.BLOQUE_B5));
			// Crear B5B
			rt = ExceptionManager.manage(serviciosGeneralesSistema.crearBloque(idSesion, ConstantsKernel.BLOQUE_B5B));
			// Crear B19
			rt = ExceptionManager.manage(serviciosGeneralesSistema.crearBloque(idSesion, ConstantsKernel.BLOQUE_B19));
			// Crear B18
			rt = ExceptionManager.manage(serviciosGeneralesSistema.crearBloque(idSesion, ConstantsKernel.BLOQUE_B18));
			// Crear B19B
			rt = ExceptionManager.manage(serviciosGeneralesSistema.crearBloque(idSesion, ConstantsKernel.BLOQUE_B19B));
			// Crear B6
			rt = ExceptionManager.manage(serviciosGeneralesSistema.existeBloque(idSesion, ConstantsKernel.BLOQUE_B6));
			if ("FALSE".equalsIgnoreCase(rt.getCampos()[0].getValor())) {
				rt = ExceptionManager.manage(serviciosGeneralesSistema.crearBloque(idSesion, ConstantsKernel.BLOQUE_B6));
			}
			// Crear B6B
			rt = ExceptionManager.manage(serviciosGeneralesSistema.existeBloque(idSesion, ConstantsKernel.BLOQUE_B6B));
			if ("FALSE".equalsIgnoreCase(rt.getCampos()[0].getValor())) {
				rt = ExceptionManager.manage(serviciosGeneralesSistema.crearBloque(idSesion, ConstantsKernel.BLOQUE_B6B));
			}

		} catch (mx.com.ice.services.exception.ApplicationException appExc) {
			logger.error(appExc, appExc);
		}

		long t1 = System.currentTimeMillis();
		logger.debug("tiempo en crearBloques()= " + ((t1 - t0)) + " mseg");
	}

	public ResultadoTransaccion obtenerDatosBloque(String idSesion, String bloque, Campo[] campos) throws ApplicationException {
		ResultadoTransaccion rt = null;
		try {
			rt = ExceptionManager.manage(serviciosGeneralesSistema.obtenerDatosBloque(idSesion, bloque, campos));
		} catch (mx.com.ice.services.exception.ApplicationException appExc) {
			logger.error(appExc, appExc);
		}
		return rt;
	}

	// TODO: Generar un xsl específico para obtener un String
	public String obtieneDescripcion(String tabla, String valor1) throws ApplicationException {
		long t0 = System.currentTimeMillis();

		String descripcion = null;
		WrapperResultados mensaje = null;
		try {
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("pi_TABLA", tabla);
			parameters.put("pi_Value1", valor1);
			Endpoint manager = (Endpoint) endpoints.get("OBTIENE_DESCRIPCION");
			mensaje = (WrapperResultados) manager.invoke(parameters);
			descripcion = mensaje.getMsgText();
			if (logger.isDebugEnabled()) {
				logger.debug("Descripcion de " + valor1 + ": " + descripcion);
			}
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'OBTIENE_DESCRIPCION'", bae);
			throw new ApplicationException("Error al obtener la descripcion de " + valor1);
		}
		long t1 = System.currentTimeMillis();
		logger.debug("tiempo en obtieneDescripcion()= " + ((t1 - t0)) + " mseg");
		return descripcion;
	}

	/**
	 * @throws ApplicationException
	 * @throws mx.com.ice.services.exception.ApplicationException
	 * 
	 */
	public void borrarCoberturasBloques(GlobalVariableContainerVO globalVarVo, String cdGarant, String idSession)
			throws ApplicationException, mx.com.ice.services.exception.ApplicationException {
		if (logger.isDebugEnabled()) {
			logger.debug("---> borrarCoberturasBloques");
		}

		Campo[] camposPoliza = new Campo[10];
		camposPoliza[0] = new Campo(VariableKernel.UnidadEconomica(), globalVarVo.getValueVariableGlobal(VariableKernel
				.UnidadEconomica()));
		camposPoliza[1] = new Campo(VariableKernel.CodigoRamo(), globalVarVo.getValueVariableGlobal(VariableKernel.CodigoRamo()));
		camposPoliza[2] = new Campo(VariableKernel.Estado(), globalVarVo.getValueVariableGlobal(VariableKernel.Estado()));
		camposPoliza[3] = new Campo(VariableKernel.NumeroPoliza(), globalVarVo.getValueVariableGlobal(VariableKernel
				.NumeroPoliza()));
		camposPoliza[4] = new Campo(VariableKernel.NumeroSituacion(), globalVarVo.getValueVariableGlobal(VariableKernel
				.NumeroSituacion()));
		camposPoliza[5] = new Campo(VariableKernel.NumeroSuplemento(), globalVarVo.getValueVariableGlobal(VariableKernel
				.NumeroSuplemento()));
		camposPoliza[8] = new Campo(VariableKernel.FechaVencimiento(), globalVarVo.getValueVariableGlobal(VariableKernel
				.FechaVencimiento()));

		// cdGarant = coberturasVo.getCdGarant();
		Campo[] capital = new Campo[1];
		capital[0] = new Campo("CDCAPITA|" + cdGarant, "");
		ExceptionManager.manage(serviciosGeneralesSistema.obtenerDatosBloque(idSession, ConstantsKernel.BLOQUE_B19, capital));
		String cdCapita = capital[0].getValor();

		camposPoliza[6] = new Campo("vg.CdGarant", cdGarant);
		camposPoliza[7] = new Campo("vg.CdCapita", cdCapita);
		camposPoliza[9] = new Campo("vg.Accion", "D");

		// ///////////////////// Se borran los bloques
		/*
		 * serviciosGeneralesSistema.eliminarRegistroBloque( idSession,
		 * ConstantsKernel.BLOQUE_B19, cdGarant, globalVarVo);
		 * serviciosGeneralesSistema.eliminarRegistroBloque( idSession,
		 * ConstantsKernel.BLOQUE_B18, cdCapita, globalVarVo);
		 * serviciosGeneralesSistema.eliminarRegistroBloque( idSession,
		 * ConstantsKernel.BLOQUE_B19B, cdGarant, globalVarVo);
		 */
		// /////////////////////
		// borra b18
		/*
		 * String idProducto =
		 * globalVarVo.getValueVariableGlobal(VariableKernel.CodigoRamo());
		 * serviciosGeneralesNegocio.guardarSumasAseguradasB18(idSession,
		 * idProducto, DELETE_MODE, globalVarVo);
		 */

		// borra b19
		/*
		 * Campo[] camposIdE = new Campo[1]; camposIdE[0] = new
		 * Campo("Identificador_Error", ""); ResultadoTransaccion rt =
		 * ExceptionManager.manage(serviciosGeneralesBloques.guardarBloqueB19(
		 * idSession, camposIdE, "D", globalVarVo)); if
		 * (logger.isDebugEnabled()) { logger.debug(":: rt : " + rt); }
		 */

		// borra b19b
		/*
		 * camposIdE = new Campo[1]; camposIdE[0] = new
		 * Campo("Identificador_Error", ""); Campo[] camposModificados = null;
		 * rt =
		 * ExceptionManager.manage(serviciosGeneralesBloques.guardarBloqueB19BSelectivoICE(
		 * idSession, camposIdE, DELETE_MODE, camposModificados , globalVarVo));
		 * logger.debug("::: despues de guardar bloque b19b");
		 */

		GuardarBloqueResultadoDAO resultado = serviciosGeneralesDAO.guardarDatosBloqueB19_2(camposPoliza, null, null);

		if (logger.isDebugEnabled()) {
			logger.debug(":: resultado : " + resultado);
		}

		if (STATUS_ERROR.equals(resultado.getEstado())) {
			throw new ApplicationException("MENSAJEGARANTIA: " + "Hubo un error al eliminar la garantía. " + "Mensaje = "
					+ resultado.getDsError());
		}
	}

	/**
	 * @throws ApplicationException
	 * @throws mx.com.ice.services.exception.ApplicationException
	 * 
	 */
	public void borrarAccesoriosBloques(GlobalVariableContainerVO globalVarVo, String nmobjeto, String idSession)
			throws ApplicationException, mx.com.ice.services.exception.ApplicationException {
		if (logger.isDebugEnabled()) {
			logger.debug("---> borrarAccesoriosBloques");
			logger.debug(":::: nmobjeto = " + nmobjeto);
		}

		String cdUnieco = globalVarVo.getValueVariableGlobal(VariableKernel.UnidadEconomica());
		String cdRamo = globalVarVo.getValueVariableGlobal(VariableKernel.CodigoRamo());
		String estado = globalVarVo.getValueVariableGlobal(VariableKernel.Estado());
		String nmPoliza = globalVarVo.getValueVariableGlobal(VariableKernel.NumeroPoliza());
		String nmSuplem = globalVarVo.getValueVariableGlobal(VariableKernel.NumeroSuplemento());
		String nmSituac = globalVarVo.getValueVariableGlobal(VariableKernel.NumeroSituacion());

		// UserVO usuario = (UserVO)session.get("USUARIO");

		if (logger.isDebugEnabled()) {
			logger.debug("cdUnieco          :" + cdUnieco);
			logger.debug("cdRamo            :" + cdRamo);
			logger.debug("estado            :" + estado);
			logger.debug("nmPoliza          :" + nmPoliza);
			logger.debug("nmSituac          :" + nmSituac);
			logger.debug("nmSuplem          :" + nmSuplem);
		}

		globalVarVo.addVariableGlobal("vg.NmObjeto", nmobjeto);

		// Se guarda el bloque B6B
		Campo[] camposId = new Campo[0];
		camposId = UtilsServices.addCampo(camposId, "IDERROR", "");
		camposId = UtilsServices.addCampo(camposId, "ROWID", "");

		ResultadoTransaccion rt = null;
		rt = ExceptionManager.manage(serviciosGeneralesBloques.guardarBloqueB6B(idSession, camposId, Constantes.DELETE_MODE,
				globalVarVo));

		rt = ExceptionManager.manage(serviciosGeneralesBloques.guardarBloqueB6(idSession, camposId, Constantes.DELETE_MODE,
				globalVarVo));

		rt = ExceptionManager.manage(serviciosGeneralesSistema.eliminarRegistroBloque(idSession, ConstantsKernel.BLOQUE_B6,
				nmobjeto, globalVarVo));

		rt = ExceptionManager.manage(serviciosGeneralesSistema.eliminarRegistroBloque(idSession, ConstantsKernel.BLOQUE_B6B,
				nmobjeto, globalVarVo));
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
			//Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_TVALOSIT_COTIZA");
			logger.debug("parameters::=" + parameters);
			//listaDatosEntradaCotiza = (ArrayList<DatosEntradaCotizaVO>) endpoint.invoke(parameters);
			
			WrapperResultados resultado = returnBackBoneInvoke(parameters, OBTIENE_TVALOSIT_COTIZA);
			listaDatosEntradaCotiza = resultado.getItemList();
			if (logger.isDebugEnabled()) {
				logger.debug("listaDatosEntradaCotiza size=" + listaDatosEntradaCotiza.size() + "   listaDatosEntradaCotiza="
						+ listaDatosEntradaCotiza);
			}
		} catch (Exception bae) {
			logger.error("Exception in invoke OBTIENE_TVALOSIT_COTIZA ", bae);
			throw new ApplicationException("Error en PKG_COTIZA.OBTIENE_TVALOSIT_COTIZA");
		}
		return listaDatosEntradaCotiza;
	}

	/**
	 * 
	 * @param listaDatosEntradaCotiza
	 * @return cadena con el formato necesario para hacer una solicitud de
	 *         tarifas
	 */
	private String crearCadenaSolicitudTarifas(List<DatosEntradaCotizaVO> listaDatosEntradaCotiza) {

		// formar cadena con formato
		// BLOQUE_CAMPO1|ETIQUETA|VALOR|DESCRIPCION*BLOQUE_CAMPO2|ETIQUETA|VALOR|DESCRIPCION|
		StringBuffer cadena = new StringBuffer();

		for (DatosEntradaCotizaVO in : listaDatosEntradaCotiza) {

			// quitarle los caracteres "parameters."
			String dsNombreTemp = StringUtils.remove(in.getDsNombre(), "parameters.");
			// Validacion Prevenir si viene doble '_' por ej. B5B_C1_1 tomar
			// solo B5B_C1
			if (StringUtils.countMatches(dsNombreTemp, "_") > 1) {
				dsNombreTemp = StringUtils.substringBeforeLast(dsNombreTemp, "_");
			}
			cadena.append(dsNombreTemp).append("|");

			cadena.append(in.getDsAtribu()).append("|");
			cadena.append(in.getOtValor()).append("|");
			cadena.append(in.getDsValor());
			cadena.append("*");
		}
		cadena.deleteCharAt(cadena.length() - 1);// quitamos el ultimo
													// caracter, que debe ser
													// '*'
		cadena.append("|");

		if (logger.isDebugEnabled()) {
			logger.debug("cadenaSolicitudTarifas=" + cadena.toString());
		}
		return cadena.toString();
	}
	
	
	public WrapperResultados ejecutaPExecValidador(String cdUnieco, String cdRamo, String estado, String nmpoliza, String nmsituac, String cdBloque) throws ApplicationException {
		WrapperResultados resultado = null;
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("pv_cdunieco_i", cdUnieco);
		parameters.put("pv_cdramo_i", cdRamo);
		parameters.put("pv_estado_i", estado);
		parameters.put("pv_nmpoliza_i", nmpoliza);
		parameters.put("pv_nmsituac_i", nmsituac);
		parameters.put("pv_cdbloque_i", cdBloque);
		try {
			/*Endpoint endpoint = (Endpoint) endpoints.get("EXEC_VALIDADOR");
			resultado = (WrapperResultados) endpoint.invoke(parameters);*/
			resultado = returnResult(parameters, EXEC_VALIDADOR);
		} catch (Exception bae) {
			logger.error("Exception in invoke EXEC_VALIDADOR", bae);
			throw new ApplicationException("Error al ejecutar EXEC_VALIDADOR");
		}
		if(resultado!= null && resultado.getMsgId() !=null && MSG_ID_OK.equals(resultado.getMsgId()))resultado.setMsgId("");
		return resultado;
	}

	public ServiciosCargarBloques getServiciosGeneralesCargarBloques() {
		return serviciosGeneralesCargarBloques;
	}

	public void setServiciosGeneralesCargarBloques(ServiciosCargarBloques serviciosGeneralesCargarBloques) {
		this.serviciosGeneralesCargarBloques = serviciosGeneralesCargarBloques;
	}

	/**
	 * @return the endpoints
	 */
	public Map<String, Endpoint> getEndpoints() {
		return endpoints;
	}

	/**
	 * @param serviciosGeneralesDAO
	 *            the serviciosGeneralesDAO to set
	 */
	public void setServiciosGeneralesDAO(ServiciosGeneralesDAO serviciosGeneralesDAO) {
		this.serviciosGeneralesDAO = serviciosGeneralesDAO;
	}

	/**
	 * @return the formatoOT
	 */
	public PDFGeneratorFormatoOT getFormatoOT() {
		return formatoOT;
	}

	/**
	 * @param formatoOT the formatoOT to set
	 */
	public void setFormatoOT(PDFGeneratorFormatoOT formatoOT) {
		this.formatoOT = formatoOT;
	}

}