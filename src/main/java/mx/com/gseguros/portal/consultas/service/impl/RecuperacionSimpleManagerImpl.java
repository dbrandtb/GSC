package mx.com.gseguros.portal.consultas.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.consultas.dao.ConsultasDAO;
import mx.com.gseguros.portal.consultas.model.RecuperacionSimple;
import mx.com.gseguros.portal.consultas.service.RecuperacionSimpleManager;
import mx.com.gseguros.portal.cotizacion.dao.CotizacionDAO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaBaseVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSlist2VO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSmapVO;
import mx.com.gseguros.portal.endosos.dao.EndososDAO;
import mx.com.gseguros.utils.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RecuperacionSimpleManagerImpl implements RecuperacionSimpleManager
{
	private Map<String,Object>session;
	
	private final static Logger logger=LoggerFactory.getLogger(RecuperacionSimpleManagerImpl.class);
	
	private ConsultasDAO  consultasDAO;
	private CotizacionDAO cotizacionDAO;
	private EndososDAO    endososDAO;
	
	/*
	 * Utilerias
	 */
	private void setCheckpoint(String checkpoint)
	{
		logger.debug(Utils.log("checkpoint-->",checkpoint));
		session.put("checkpoint",checkpoint);
	}
	
	private String getCheckpoint()
	{
		return (String)session.get("checkpoint");
	}
	
	private void manejaException(Exception ex,ManagerRespuestaBaseVO resp)
	{
		long timestamp = System.currentTimeMillis();
		resp.setExito(false);
		resp.setRespuestaOculta(ex.getMessage());
		
		if(ex instanceof ApplicationException)
		{
			resp.setRespuesta(Utils.join(ex.getMessage()," #",timestamp));
		}
		else
		{
			resp.setRespuesta(Utils.join("Error ",getCheckpoint().toLowerCase()," #",timestamp));
		}
		
		logger.error(resp.getRespuesta(),ex);
		setCheckpoint("0");
	}
	/*
	 * Utilerias
	 */
	
	@Override
	public void setSession(Map<String,Object>session)
	{
		this.session=session;
	}
	
	@Override
	public ManagerRespuestaSmapVO recuperacionSimple(
			RecuperacionSimple proc
			,Map<String,String>params
			,String cdsisrol
			,String cdusuari
			)
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ recuperacionSimple @@@@@@"
				,"\n@@@@@@ procedimiento=" , proc
				,"\n@@@@@@ parametros="    , params
				,"\n@@@@@@ cdsisrol="      , cdsisrol
				,"\n@@@@@@ cdusuari="      , cdusuari
				));
		
		ManagerRespuestaSmapVO resp=new ManagerRespuestaSmapVO(true);
		
		try
		{
			setCheckpoint("Recuperando datos");
			if(proc.equals(RecuperacionSimple.RECUPERAR_DESCUENTO_RECARGO_RAMO_5))
			{
				setCheckpoint("Recuperando rango de descuento/recargo");
				String cdtipsit = params.get("cdtipsit");
				String cdagente = params.get("cdagente");
				String negocio  = params.get("negocio");
				String tipocot  = params.get("tipocot");
				
				if(tipocot.equals("I"))
				{
				    resp.setSmap(cotizacionDAO.cargarRangoDescuentoRamo5(cdtipsit,cdagente,negocio,cdsisrol,cdusuari));
				}
				else if(tipocot.equals("P")||tipocot.equals("F"))
				{
					resp.setSmap(cotizacionDAO.cargarRangoDescuentoRamo5TodasSituaciones(cdagente,negocio,cdsisrol,cdusuari));
				}
			}
			else if(proc.equals(RecuperacionSimple.RECUPERAR_DATOS_VEHICULO_RAMO_5))
			{
				setCheckpoint("Recuperando datos del vehiculo");
				String cdunieco = params.get("cdunieco");
				String cdramo   = params.get("cdramo");
				String estado   = params.get("estado");
				String nmpoliza = params.get("nmpoliza");
				resp.setSmap(cotizacionDAO.cargarDatosVehiculoRamo5(cdunieco,cdramo,estado,nmpoliza));
			}
			else if(proc.equals(RecuperacionSimple.RECUPERAR_ULTIMO_NMSUPLEM))
			{
				String cdunieco = params.get("cdunieco");
				String cdramo   = params.get("cdramo");
				String estado   = params.get("estado");
				String nmpoliza = params.get("nmpoliza");
				resp.setSmap(consultasDAO.cargarUltimoNmsuplemPoliza(cdunieco,cdramo,estado,nmpoliza));
			}
			else if(proc.equals(RecuperacionSimple.VERIFICAR_CODIGO_POSTAL_FRONTERIZO))
			{
				String cdpostal = params.get("cdpostal");
				resp.setSmap(new HashMap<String,String>());
				resp.getSmap().put("fronterizo",consultasDAO.verificarCodigoPostalFronterizo(cdpostal)?"S":"N");
			}
			else if(proc.equals(RecuperacionSimple.RECUPERAR_TEXTO_CLAUSULA_POLIZA))
			{
				String cdclausu = params.get("cdclausu");
				resp.setSmap(new HashMap<String,String>());
				resp.getSmap().put("dsclausu" , Utils.join("Texto de la clausula ",cdclausu));
			}
			else if(proc.equals(RecuperacionSimple.RECUPERAR_PORCENTAJE_RECARGO_POR_PRODUCTO))
			{
				String cdramo   = params.get("cdramo");
				String cdperpag = params.get("cdperpag");
				resp.setSmap(new HashMap<String,String>());
				resp.getSmap().put("recargo" , consultasDAO.recuperarPorcentajeRecargoPorProducto(cdramo,cdperpag));
			}
			else if(proc.equals(RecuperacionSimple.RECUPERAR_VALOR_ATRIBUTO_UNICO))
			{
				String cdtipsit = params.get("cdtipsit");
				String cdatribu = params.get("cdatribu");
				String otclave  = params.get("otclave");
				resp.setSmap(new HashMap<String,String>());
				resp.getSmap().put("otvalor",consultasDAO.recuperarValorAtributoUnico(cdtipsit,cdatribu,otclave));
			}
			else if(proc.equals(RecuperacionSimple.RECUPERAR_DSATRIBUS_TATRISIT))
			{
				String        cdramo       = params.get("cdramo");
				List<String>  listaNombres = consultasDAO.recuperarDescripcionAtributosSituacionPorRamo(cdramo);
				StringBuilder sb           = new StringBuilder();
				boolean       primero      = true;
				for(String nombre:listaNombres)
				{
					if(primero)
					{
						sb.append(Utils.join("CVE_"    , nombre));
						sb.append(Utils.join("@#@DES_" , nombre));
						primero = false;
					}
					else
					{
						sb.append(Utils.join("@#@CVE_" , nombre));
						sb.append(Utils.join("@#@DES_" , nombre));
					}
				}
				resp.setSmap(new HashMap<String,String>());
				resp.getSmap().put("listaNombres",sb.toString());
			}
			else if(proc.equals(RecuperacionSimple.RECUPERAR_FECHAS_LIMITE_ENDOSO))
			{
				String cdunieco = params.get("cdunieco");
				String cdramo   = params.get("cdramo");
				String estado   = params.get("estado");
				String nmpoliza = params.get("nmpoliza");
				String cdtipsup = params.get("cdtipsup");
				resp.setSmap(new HashMap<String,String>());
				
				resp.getSmap().putAll(consultasDAO.recuperarFechasLimiteEndoso(
						cdunieco
						,cdramo
						,estado
						,nmpoliza
						,cdsisrol
						,cdusuari
						,cdtipsup
						));
			}
			else if(proc.equals(RecuperacionSimple.RECUPERAR_PERMISO_USUARIO_DEVOLUCION_PRIMAS))
			{
				resp.setSmap(new HashMap<String,String>());
				resp.getSmap().put("permiso" , consultasDAO.recuperarPermisoDevolucionPrimasUsuario(cdusuari) ? "S" : "N");
			}
			else if(proc.equals(RecuperacionSimple.RECUPERAR_VALOR_MAXIMO_SITUACION_POR_ROL))
			{
				String cdtipsit = params.get("cdtipsit");
				resp.setSmap(new HashMap<String,String>());
				resp.getSmap().put("VALOR" , consultasDAO.recuperarValorMaximoSituacionPorRol(cdtipsit,cdsisrol));
			}
			else if(proc.equals(RecuperacionSimple.RECUPERAR_TATRISIT_AMPARADO))
			{
				String cdunieco = params.get("cdunieco");
				String cdramo   = params.get("cdramo");
				String estado   = params.get("estado");
				String nmpoliza = params.get("nmpoliza");
				String nmsituac = params.get("nmsituac");
				String nmsuplem = params.get("nmsuplem");
				String cdtipsit = params.get("cdtipsit");
				String cdatribu = params.get("cdatribu");
				resp.setSmap(new HashMap<String,String>());
				resp.getSmap().put("CONTEO" , consultasDAO.obtieneConteoSituacionCoberturaAmparada(
						cdunieco
						,cdramo
						,estado
						,nmpoliza
						,nmsituac
						,nmsuplem
						,cdtipsit
						,cdatribu
						));
			}
			else if(proc.equals(RecuperacionSimple.RECUPERAR_DERECHOS_POLIZA_POR_PAQUETE_RAMO_1))
			{
				String paquete = params.get("paquete");
				resp.setSmap(new HashMap<String,String>());
				resp.getSmap().put("DERECHOS" , consultasDAO.recuperarDerechosPolizaPorPaqueteRamo1(paquete));
			}
			else if(proc.equals(RecuperacionSimple.RECUPERAR_CONTEO_BLOQUEO))
			{
				String cdunieco = params.get("cdunieco");
				String cdramo   = params.get("cdramo");
				String estado   = params.get("estado");
				String nmpoliza = params.get("nmpoliza");
				resp.setSmap(new HashMap<String,String>());
				resp.getSmap().put("CONTEO" , consultasDAO.recuperarConteoTbloqueo(cdunieco,cdramo,estado,nmpoliza));
			}
			
			setCheckpoint("0");
		}
		catch(Exception ex)
		{
			manejaException(ex, resp);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ " , resp
				,"\n@@@@@@ recuperacionSimple @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return resp;
	}
	
	@Override
	public ManagerRespuestaSlist2VO recuperacionSimpleLista(
			RecuperacionSimple proc
			,Map<String,String>params
			,String cdsisrol
			,String cdusuari
			)
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ recuperacionSimpleLista @@@@@@"
				,"\n@@@@@@ proc="     , proc
				,"\n@@@@@@ params="   , params
				,"\n@@@@@@ cdsisrol=" , cdsisrol
				,"\n@@@@@@ cdusuari=" , cdusuari
				));
		
		ManagerRespuestaSlist2VO resp=new ManagerRespuestaSlist2VO(true);
		
		try
		{
			setCheckpoint("Recuperando datos");
			if(proc.equals(RecuperacionSimple.RECUPERAR_DETALLES_COTIZACION_AUTOS_FLOTILLA))
			{
				String cdunieco = params.get("cdunieco");
				String cdramo   = params.get("cdramo");
				String estado   = params.get("estado");
				String nmpoliza = params.get("nmpoliza");
				String cdperpag = params.get("cdperpag");
				resp.setSlist(cotizacionDAO.cargarDetallesCotizacionAutoFlotilla(cdunieco, cdramo, estado, nmpoliza, cdperpag));
			}
			else if(proc.equals(RecuperacionSimple.RECUPERAR_DETALLES_COBERTURAS_COTIZACION_AUTOS_FLOTILLA))
			{
				String cdunieco = params.get("cdunieco");
				String cdramo   = params.get("cdramo");
				String estado   = params.get("estado");
				String nmpoliza = params.get("nmpoliza");
				String cdperpag = params.get("cdperpag");
				resp.setSlist(cotizacionDAO.cargarDetallesCoberturasCotizacionAutoFlotilla(cdunieco, cdramo, estado, nmpoliza, cdperpag));
			}
			else if(proc.equals(RecuperacionSimple.RECUPERAR_TVALOSIT))
			{
				String cdunieco = params.get("cdunieco");
				String cdramo   = params.get("cdramo");
				String estado   = params.get("estado");
				String nmpoliza = params.get("nmpoliza");
				String nmsuplem = params.get("nmsuplem");
				resp.setSlist(Utils.concatenarParametros(consultasDAO.cargarTvalosit(cdunieco, cdramo, estado, nmpoliza, nmsuplem),false));
			}
			else if(proc.equals(RecuperacionSimple.RECUPERAR_CONFIGURACION_VALOSIT_FLOTILLAS))
			{
				String cdramo   = params.get("cdramo");
				String cdtipsit = params.get("cdtipsit");
				String negocio  = params.get("negocio");
				Map<String,List<Map<String,String>>> res = cotizacionDAO.cargarConfiguracionTvalositFlotillas(cdramo, cdtipsit, negocio);
				resp.setSlist(res.get("config"));
				resp.setSlist2(res.get("rangos"));
			}
			else if(proc.equals(RecuperacionSimple.RECUPERAR_MPOLIPER_OTROS_ROLES_POR_NMSITUAC))
			{
				String cdunieco = params.get("cdunieco");
				String cdramo   = params.get("cdramo");
				String estado   = params.get("estado");
				String nmpoliza = params.get("nmpoliza");
				String nmsuplem = params.get("nmsuplem");
				String nmsituac = params.get("nmsituac");
				String roles    = params.get("roles");
				resp.setSlist(consultasDAO.cargarMpoliperOtrosRolesPorNmsituac(cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmsituac, roles));
			}
			else if(proc.equals(RecuperacionSimple.RECUPERAR_CLAUSULAS_POLIZA))
			{
				String cdunieco = params.get("cdunieco");
				String cdramo   = params.get("cdramo");
				String estado   = params.get("estado");
				String nmpoliza = params.get("nmpoliza");
				String nmsuplem = params.get("nmsuplem");
				
				List<Map<String,String>>lista=new ArrayList<Map<String,String>>();
				resp.setSlist(lista);
				
				Map<String,String>cla1=new HashMap<String,String>();
				lista.add(cla1);
				cla1.put("cdunieco" , cdunieco);
				cla1.put("cdramo"   , cdramo);
				cla1.put("estado"   , estado);
				cla1.put("nmpoliza" , nmpoliza);
				cla1.put("nmsuplem" , nmsuplem);
				cla1.put("status"   , "V");
				cla1.put("cdclausu" , "1");
				cla1.put("dsclausu" , "uno");
				cla1.put("dslinea"  , "Texto de la primer clausula");
				
				Map<String,String>cla2=new HashMap<String,String>();
				lista.add(cla2);
				cla2.put("cdunieco" , cdunieco);
				cla2.put("cdramo"   , cdramo);
				cla2.put("estado"   , estado);
				cla2.put("nmpoliza" , nmpoliza);
				cla2.put("nmsuplem" , nmsuplem);
				cla2.put("status"   , "V");
				cla2.put("cdclausu" , "2");
				cla2.put("dsclausu" , "dos");
				cla2.put("dslinea"  , "Texto de la segunda clausula");
			}
			else if(proc.equals(RecuperacionSimple.RECUPERAR_VALORES_PANTALLA))
			{
				String pantalla = params.get("pantalla");
				String cdramo   = params.get("cdramo");
				String cdtipsit = params.get("cdtipsit");
				resp.setSlist(consultasDAO.recuperarValoresPantalla(pantalla,cdramo,cdtipsit));
			}
			else if(proc.equals(RecuperacionSimple.RECUPERAR_VALORES_ATRIBUTOS_FACTORES))
			{
				String cdramo   = params.get("cdramo");
				String cdtipsit = params.get("cdtipsit");
				resp.setSlist(consultasDAO.recuperarValoresAtributosFactores(cdramo,cdtipsit));
			}
			else if(proc.equals(RecuperacionSimple.RECUPERAR_POLIZAS_ENDOSABLES))
			{
				String cdunieco  = params.get("cdunieco");
				String cdramo    = params.get("cdramo");
				String estado    = params.get("estado");
				String nmpoliza  = params.get("nmpoliza");
				String nmpoliex  = params.get("nmpoliex");
				String ramo      = params.get("ramo");
				String cdagente  = params.get("cdagente");
				String statusVig = params.get("statusVig");
				//Se agregan campos para Filtrado por Fecha de Inicio y Fecha de fin
				String finicio   = params.get("finicio");
				String ffin      = params.get("ffin");
				resp.setSlist(consultasDAO.recuperarPolizasEndosables(cdunieco,cdramo,estado,nmpoliza,nmpoliex,ramo,cdagente,statusVig,finicio,ffin));
			}
			else if(proc.equals(RecuperacionSimple.RECUPERAR_HISTORICO_POLIZA))
			{
				String cdunieco = params.get("cdunieco");
				String cdramo   = params.get("cdramo");
				String estado   = params.get("estado");
				String nmpoliza = params.get("nmpoliza");
				resp.setSlist(consultasDAO.recuperarHistoricoPoliza(cdunieco,cdramo,estado,nmpoliza));
			}
			else if(proc.equals(RecuperacionSimple.RECUPERAR_INCISOS_POLIZA_GRUPO_FAMILIA))
			{
				String cdunieco = params.get("cdunieco");
				String cdramo   = params.get("cdramo");
				String estado   = params.get("estado");
				String nmpoliza = params.get("nmpoliza");
				String cdgrupo  = params.get("cdgrupo");
				String nmfamili = params.get("nmfamili");
				String nivel    = params.get("nivel");
				resp.setSlist(consultasDAO.recuperarIncisosPolizaGrupoFamilia(
						cdunieco
						,cdramo
						,estado
						,nmpoliza
						,cdgrupo
						,nmfamili
						,nivel
						));
			}
			else if(proc.equals(RecuperacionSimple.RECUPERAR_GRUPOS_POLIZA))
			{
				String cdunieco = params.get("cdunieco");
				String cdramo   = params.get("cdramo");
				String estado   = params.get("estado");
				String nmpoliza = params.get("nmpoliza");
				resp.setSlist(consultasDAO.recuperarGruposPoliza(cdunieco,cdramo,estado,nmpoliza));
			}
			else if(proc.equals(RecuperacionSimple.RECUPERAR_FAMILIAS_POLIZA))
			{
				String cdunieco = params.get("cdunieco");
				String cdramo   = params.get("cdramo");
				String estado   = params.get("estado");
				String nmpoliza = params.get("nmpoliza");
				resp.setSlist(consultasDAO.recuperarFamiliasPoliza(cdunieco,cdramo,estado,nmpoliza));
			}
			else if(proc.equals(RecuperacionSimple.RECUPERAR_COBERTURAS_ENDOSO_DEVOLUCION_PRIMAS))
			{
				String cdunieco = params.get("cdunieco");
				String cdramo   = params.get("cdramo");
				String estado   = params.get("estado");
				String nmpoliza = params.get("nmpoliza");
				String nmsituac = params.get("nmsituac");
				String tstamp   = params.get("tstamp");
				resp.setSlist(endososDAO.recuperarCoberturasEndosoDevolucionPrimas(cdunieco, cdramo, estado, nmpoliza, nmsituac, tstamp));
			}
			else if(proc.equals(RecuperacionSimple.RECUPERAR_ENDOSOS_REHABILITABLES))
			{
				String cdunieco = params.get("cdunieco");
				String cdramo   = params.get("cdramo");
				String estado   = params.get("estado");
				String nmpoliza = params.get("nmpoliza");
				resp.setSlist(consultasDAO.recuperarEndososRehabilitables(cdunieco, cdramo, estado, nmpoliza));
			}
			else if(proc.equals(RecuperacionSimple.RECUPERAR_ENDOSOS_CANCELABLES))
			{
				String cdunieco = params.get("cdunieco");
				String cdramo   = params.get("cdramo");
				String estado   = params.get("estado");
				String nmpoliza = params.get("nmpoliza");
				resp.setSlist(consultasDAO.recuperarEndososCancelables(cdunieco, cdramo, estado, nmpoliza));
			}
			else if(proc.equals(RecuperacionSimple.RECUPERAR_REVISION_COLECTIVOS))
			{
				String cdunieco = params.get("cdunieco");
				String cdramo   = params.get("cdramo");
				String estado   = params.get("estado");
				String nmpoliza = params.get("nmpoliza");
				resp.setSlist(consultasDAO.recuperarRevisionColectivos(cdunieco, cdramo, estado, nmpoliza));
			}
			else if(proc.equals(RecuperacionSimple.RECUPERAR_USUARIOS_REASIGNACION_TRAMITE))
			{
				String ntramite = params.get("ntramite");
				resp.setSlist(consultasDAO.recuperarUsuariosReasignacionTramite(ntramite));
			}
			setCheckpoint("0");
		}
		catch(Exception ex)
		{
			manejaException(ex, resp);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ " , resp
				,"\n@@@@@@ recuperacionSimpleLista @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return resp;
	}
	
	@Override
	public Map<String,String> recuperarMapa(
			String cdusuari
			,String cdsisrol
			,RecuperacionSimple consulta
			,Map<String,String> params
			,UserVO usuario
	)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ recuperarMapa @@@@@@"
				,"\n@@@@@@ cdusuari=" , cdusuari
				,"\n@@@@@@ cdsisrol=" , cdsisrol
				,"\n@@@@@@ consulta=" , consulta
				,"\n@@@@@@ params="   , params
				,"\n@@@@@@ usuario="  , usuario
				));
		Map<String,String> mapa = new HashMap<String,String>();
		String             paso = "Recuperando datos";
		try
		{
			if(consulta.equals(RecuperacionSimple.RECUPERAR_IMPRESIONES_DISPONIBLES))
			{
				paso = "Recuperando impresiones disponibles";
				logger.debug("@@@@@@ paso: {}",paso);
				
				String cdtipram = params.get("cdtipram");
				String tipolote = params.get("tipolote");
				
				Utils.validate(
						cdtipram  , "No se recibi\u00F3 el tipo de ramo"
						,tipolote , "No se recibi\u00F3 el tipo de lote"
						);
				
				String impdisp = consultasDAO.recuperarImpresionesDisponiblesPorTipoRamo(cdtipram, tipolote);
				
				mapa.put("imprdisp" , impdisp);
			}
			else if(consulta.equals(RecuperacionSimple.RECUPERAR_DETALLE_IMPRESION_LOTE))
			{
				paso = "Recuperando detalle de impresiones de lote";
				logger.debug("@@@@@@ paso: {}",paso);
				
				String lote = params.get("lote");
				
				Utils.validate(lote, "No se recibi\u00F3 el lote");
				
				Map<String,String>requeridasYEjecutadas = consultasDAO.recuperarDetalleImpresionLote(lote);
				
				mapa.putAll(requeridasYEjecutadas);
			}
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		logger.debug(Utils.log(
				 "\n@@@@@@ mapa=",mapa
				,"\n@@@@@@ recuperarMapa @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return mapa;
	}
	
	@Override
	public List<Map<String,String>> recuperarLista(
			String cdusuari
			,String cdsisrol
			,RecuperacionSimple consulta
			,Map<String,String> params
			,UserVO usuario
	)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ recuperarLista @@@@@@"
				,"\n@@@@@@ cdusuari=" , cdusuari
				,"\n@@@@@@ cdsisrol=" , cdsisrol
				,"\n@@@@@@ consulta=" , consulta
				,"\n@@@@@@ params="   , params
				,"\n@@@@@@ usuario="  , usuario
				));
		List<Map<String,String>> lista = new ArrayList<Map<String,String>>();
		String                   paso  = "Recuperando datos";
		try
		{
			if(consulta.equals(RecuperacionSimple.RECUPERAR_INCISOS_POLIZA_GRUPO_FAMILIA))
			{
				paso = "Recuperando incisos de p\u00F3liza/grupo/familia";
				logger.debug("Paso: {}",paso);
				String cdunieco = params.get("cdunieco");
				String cdramo   = params.get("cdramo");
				String estado   = params.get("estado");
				String nmpoliza = params.get("nmpoliza");
				String cdgrupo  = params.get("cdgrupo");
				String nmfamili = params.get("nmfamili");
				String nivel    = params.get("nivel");
				lista = consultasDAO.recuperarIncisosPolizaGrupoFamilia(
						cdunieco
						,cdramo
						,estado
						,nmpoliza
						,cdgrupo
						,nmfamili
						,nivel
						);
			}
			else if(consulta.equals(RecuperacionSimple.RECUPERAR_MOVIMIENTOS_ENDOSO_ALTA_BAJA_ASEGURADO))
			{
				paso = "Recuperando movimientos de endoso de alta/baja de asegurados";
				logger.debug("Paso: {}",paso);
				String cdunieco = params.get("cdunieco");
				String cdramo   = params.get("cdramo");
				String estado   = params.get("estado");
				String nmpoliza = params.get("nmpoliza");
				String nmsuplem  = params.get("nmsuplem");
				lista = consultasDAO.recuperarMovimientosEndosoAltaBajaAsegurados(
						cdunieco
						,cdramo
						,estado
						,nmpoliza
						,nmsuplem
						);
			}
			else if(consulta.equals(RecuperacionSimple.RECUPERAR_POLIZAS_PARA_EXPLOTAR_DOCS))
			{
				paso = "Recuperando polizas para explotar documentos";
				logger.debug("Paso: {}",paso);
				
				String cdtipram     = params.get("cdtipram");
				String cduniecos    = params.get("cduniecos");
				String cdramo       = params.get("cdramo");
				String ramo         = params.get("ramo");
				String nmpoliza     = params.get("nmpoliza");
				String fefecha      = params.get("fefecha");
				String cdusuariLike = params.get("cdusuari");
				String cdagente     = params.get("cdagente");
				
				Utils.validate(
						cdtipram   , "No se recibi\u00F3 el tipo de ramo"
						,cduniecos , "No se recibieron sucursales"
						,fefecha   , "No se recibi\u00F3 la fecha"
						);
				
				lista = consultasDAO.recuperarPolizasParaImprimir(
						cdtipram
						,cduniecos
						,cdramo
						,ramo
						,nmpoliza
						,Utils.parse(fefecha)
						,cdusuariLike
						,cdagente
						,cdusuari
						,usuario.getCdUnieco()
						);
			}
			else if(consulta.equals(RecuperacionSimple.RECUPERAR_IMPRESORAS))
			{
				paso = "Recuperando impresoras por papel";
				logger.debug("Paso: {}",paso);
				
				String papel    = params.get("papel");
				String cdunieco = usuario.getCdUnieco();
				String swactivo = params.get("swactivo");
				
				Utils.validate(
						papel     , "No se recibi\u00F3 el tipo de papel"
						,cdunieco , "No hay sucursal de usuario"
						);
				
				lista = consultasDAO.recuperarImpresorasPorPapelYSucursal(cdunieco,papel,swactivo);
			}
			else if(consulta.equals(RecuperacionSimple.RECUPERAR_PERMISOS_IMPRESION))
			{
				paso = "Recuperando permisos por sucursal";
				logger.debug("@@@@@@ paso: {}",paso);
				
				String cdusuariPer = params.get("cdusuari");
				String cdunieco    = params.get("cdunieco");
				String cdtipram    = params.get("cdtipram");
				
				Utils.validate(
						cdusuariPer , "No se recibi\u00F3 el usuario"
						,cdunieco   , "No se recibi\u00F3 la sucursal"
						,cdtipram   , "No se recibi\u00F3 el tipo de ramo"
						);
				
				List<Map<String,String>> sucursales = consultasDAO.recuperarConfigImpresionSucursales(cdusuariPer,cdunieco,cdtipram);
				
				paso = "Recuperando permisos por agente";
				logger.debug("@@@@@@ paso: {}",paso);
				
				List<Map<String,String>> agentes = consultasDAO.recuperarConfigImpresionAgentes(cdusuariPer,cdunieco,cdtipram);
				
				paso = "Recuperando permisos por usuario";
				logger.debug("@@@@@@ paso: {}",paso);
				
				List<Map<String,String>> usuarios = consultasDAO.recuperarConfigImpresionUsuarios(cdusuariPer,cdunieco,cdtipram);
				
				paso = "Integrando permisos";
				logger.debug("@@@@@@ paso: {}",paso);
				
				List<Map<String,String>> resumen = new ArrayList<Map<String,String>>();
				for(Map<String,String>sucursal:sucursales)
				{
					Map<String,String>nuevo = new HashMap<String,String>();
					nuevo.put("tipo"        , "S");
					nuevo.put("cdusuari"    , sucursal.get("COD_USUARIO"));
					nuevo.put("cdunieco"    , sucursal.get("SUC_USUARIO"));
					nuevo.put("cdtipram"    , sucursal.get("TIPO_RAMO"));
					nuevo.put("cduniecoPer" , sucursal.get("SUC_PERMITIDA"));
					nuevo.put("funcion"     , sucursal.get("SWAPLICA"));
					nuevo.put("descrip"     , sucursal.get("DESCRIP"));
					resumen.add(nuevo);
				}
				
				for(Map<String,String> agente:agentes)
				{
					Map<String,String>nuevo = new HashMap<String,String>();
					nuevo.put("tipo"        , "A");
					nuevo.put("cdusuari"    , agente.get("COD_USUARIO"));
					nuevo.put("cdunieco"    , agente.get("SUC_USUARIO"));
					nuevo.put("cdtipram"    , agente.get("TIPO_RAMO"));
					nuevo.put("cdagentePer" , agente.get("AGENTE"));
					nuevo.put("funcion"     , agente.get("SWAPLICA"));
					nuevo.put("descrip"     , agente.get("DESCRIP"));
					resumen.add(nuevo);
				}
				
				for(Map<String,String> usuarioPer:usuarios)
				{
					Map<String,String>nuevo = new HashMap<String,String>();
					nuevo.put("tipo"        , "U");
					nuevo.put("cdusuari"    , usuarioPer.get("COD_USUARIO"));
					nuevo.put("cdunieco"    , usuarioPer.get("SUC_USUARIO"));
					nuevo.put("cdtipram"    , usuarioPer.get("TIPO_RAMO"));
					nuevo.put("cdusuariPer" , usuarioPer.get("CDUSUARI_PERMISO"));
					nuevo.put("funcion"     , usuarioPer.get("SWAPLICA"));
					nuevo.put("descrip"     , usuarioPer.get("DESCRIP"));
					resumen.add(nuevo);
				}
				
				logger.debug(Utils.log("@@@@@@ lista permisos=",resumen));
				lista = resumen;
			}
			else if(consulta.equals(RecuperacionSimple.RECUPERAR_RECIBOS_PARA_EXPLOTAR_DOCS))
			{
				paso = "Recuperando recibos en lote";
				logger.debug("@@@@@@ paso: {}",paso);
				
				String cdtipram  = params.get("cdtipram");
				String cduniecos = params.get("cduniecos");
				String feproces  = params.get("feproces");
				String feimpres  = params.get("feimpres");
				
				lista = consultasDAO.recuperarRecibosLote(
						cdtipram
						,cduniecos
						,Utils.parse(feproces)
						,Utils.parse(feimpres)
						,cdusuari
						,usuario.getCdUnieco()
						);
			}
			else if(consulta.equals(RecuperacionSimple.RECUPERAR_DETALLE_REMESA))
			{
				paso = "Recuperando detalle de remesa";
				logger.debug("@@@@@@ paso: {}",paso);
				
				String ntramite = params.get("ntramite");
				String tipolote = params.get("tipolote");
				
				lista = consultasDAO.recuperarDetalleRemesa(ntramite,tipolote);
			}
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		logger.debug(Utils.log(
				 "\n@@@@@@ lista=",lista==null ? "null" : lista.size()
				,"\n@@@@@@ recuperarLista @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return lista;
	}
	
	//////////////////////////////////////////////////////////////
	// GETTERS Y SETTERS /////////////////////////////////////////
	                                                            //
	public void setConsultasDAO(ConsultasDAO consultasDAO) {    //
		this.consultasDAO = consultasDAO;                       //
	}                                                           //
                                                                //
	public void setCotizacionDAO(CotizacionDAO cotizacionDAO) { //
		this.cotizacionDAO = cotizacionDAO;                     //
	}                                                           //
                                                                //
	public void setEndososDAO(EndososDAO endososDAO) {          //
		this.endososDAO = endososDAO;                           //
	}                                                           //
	//////////////////////////////////////////////////////////////
	
}