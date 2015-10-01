package mx.com.gseguros.portal.consultas.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ recuperarMapa @@@@@@"
				,"\n@@@@@@ cdusuari=" , cdusuari
				,"\n@@@@@@ cdsisrol=" , cdsisrol
				,"\n@@@@@@ consulta=" , consulta
				,"\n@@@@@@ params="   , params
				));
		Map<String,String> mapa = new HashMap<String,String>();
		String             paso = "Recuperando datos";
		try
		{
			
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
	)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ recuperarLista @@@@@@"
				,"\n@@@@@@ cdusuari=" , cdusuari
				,"\n@@@@@@ cdsisrol=" , cdsisrol
				,"\n@@@@@@ consulta=" , consulta
				,"\n@@@@@@ params="   , params
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
				
				Map<String,String>poliza = new HashMap<String,String>();
				poliza.put("cdtipram" , "10");
				poliza.put("dstipram" , "SALUD");
				poliza.put("cdunieco" , "1000");
				poliza.put("cdramo"   , "4");
				poliza.put("dsramo"   , "MULTISALUD");
				poliza.put("ramo"     , "209");
				poliza.put("estado"   , "W");
				poliza.put("nmpoliza" , "30012");
				poliza.put("nmsuplem" , "123456789012345678");
				poliza.put("tipoflot" , "");
				poliza.put("nsuplogi" , "0");
				poliza.put("cdtipsup" , "1");
				poliza.put("cdgestor" , "123");
				poliza.put("dssuplog" , "0 - 123");
				poliza.put("cddevcia" , "EMISION ICE");
				poliza.put("dstipsup" , "EMISION");
				poliza.put("feinival" , "01/01/2015");
				poliza.put("ntramite" , "1560");
				
				Map<String,String>poliza2 = new HashMap<String,String>();
				poliza2.put("cdtipram" , "10");
				poliza2.put("dstipram" , "SALUD");
				poliza2.put("cdunieco" , "1000");
				poliza2.put("cdramo"   , "4");
				poliza2.put("dsramo"   , "MULTISALUD");
				poliza2.put("ramo"     , "209");
				poliza2.put("estado"   , "W");
				poliza2.put("nmpoliza" , "30012");
				poliza2.put("nmsuplem" , "123456789012345678");
				poliza2.put("tipoflot" , "");
				poliza2.put("nsuplogi" , "1");
				poliza2.put("cdtipsup" , "14");
				poliza2.put("cdgestor" , "124");
				poliza2.put("dssuplog" , "1 - 124");
				poliza2.put("cddevcia" , "ENDOSO EDAD INC.ICE");
				poliza2.put("dstipsup" , "MODIFICACION DE EDAD ASEGURADOS");
				poliza2.put("feinival" , "02/01/2015");
				poliza2.put("ntramite" , "1561");
				
				Map<String,String>poliza3 = new HashMap<String,String>();
				poliza3.put("cdtipram" , "10");
				poliza3.put("dstipram" , "SALUD");
				poliza3.put("cdunieco" , "1000");
				poliza3.put("cdramo"   , "4");
				poliza3.put("dsramo"   , "MULTISALUD");
				poliza3.put("ramo"     , "209");
				poliza3.put("estado"   , "W");
				poliza3.put("nmpoliza" , "30012");
				poliza3.put("nmsuplem" , "123456789012345678");
				poliza3.put("tipoflot" , "");
				poliza3.put("nsuplogi" , "2");
				poliza3.put("cdtipsup" , "9");
				poliza3.put("cdgestor" , "456");
				poliza3.put("dssuplog" , "2 - 456");
				poliza3.put("cddevcia" , "ENDOSO MOD. DOMICILIO ICE");
				poliza3.put("dstipsup" , "CAMBIO DE DOMICILIO DEL ASEGURADO TITULAR");
				poliza3.put("feinival" , "03/01/2015");
				poliza3.put("ntramite" , "1562");
				
				Map<String,String>poliza4 = new HashMap<String,String>();
				poliza4.put("cdtipram" , "10");
				poliza4.put("dstipram" , "SALUD");
				poliza4.put("cdunieco" , "1000");
				poliza4.put("cdramo"   , "4");
				poliza4.put("dsramo"   , "MULTISALUD");
				poliza4.put("ramo"     , "209");
				poliza4.put("estado"   , "W");
				poliza4.put("nmpoliza" , "30013");
				poliza4.put("nmsuplem" , "123456789012345678");
				poliza4.put("tipoflot" , "F");
				poliza4.put("nsuplogi" , "0");
				poliza4.put("cdtipsup" , "1");
				poliza4.put("cdgestor" , "456");
				poliza4.put("dssuplog" , "0 - 456");
				poliza4.put("cddevcia" , "EMISION ICE");
				poliza4.put("dstipsup" , "EMISION");
				poliza4.put("feinival" , "02/02/2015");
				poliza4.put("ntramite" , "4567");
				
				Map<String,String>poliza5 = new HashMap<String,String>();
				poliza5.put("cdtipram" , "10");
				poliza5.put("dstipram" , "SALUD");
				poliza5.put("cdunieco" , "1000");
				poliza5.put("cdramo"   , "4");
				poliza5.put("dsramo"   , "MULTISALUD");
				poliza5.put("ramo"     , "209");
				poliza5.put("estado"   , "W");
				poliza5.put("nmpoliza" , "30013");
				poliza5.put("nmsuplem" , "123456789012345678");
				poliza5.put("tipoflot" , "F");
				poliza5.put("nsuplogi" , "1");
				poliza5.put("cdtipsup" , "1");
				poliza5.put("cdgestor" , "457");
				poliza5.put("dssuplog" , "1 - 457");
				poliza5.put("cddevcia" , "CAMBIO CONTRA. ICE");
				poliza5.put("dstipsup" , "CAMBIO DE CONTRATANTE");
				poliza5.put("feinival" , "05/02/2015");
				poliza5.put("ntramite" , "6789");
				
				lista.add(poliza);
				lista.add(poliza2);
				lista.add(poliza3);
				lista.add(poliza4);
				lista.add(poliza5);
			}
			else if(consulta.equals(RecuperacionSimple.RECUPERAR_IMPRESORAS))
			{
				paso = "Recuperando impresoras por papel";
				logger.debug("Paso: {}",paso);
				
				Map<String,String> imp1 = new HashMap<String,String>();
				imp1.put("cdunieco" , "1205");
				imp1.put("ip"       , "12.12.12.12");
				imp1.put("puerto"   , "1234");
				imp1.put("dsimpres" , "Compaq HJK54 - Primer piso");
				imp1.put("cdpapel"  , "");
				imp1.put("dspapel"  , "CREDENCIALES");
				lista.add(imp1);
				
				Map<String,String> imp2 = new HashMap<String,String>();
				imp2.put("cdunieco" , "1205");
				imp2.put("ip"       , "1.1.1.1");
				imp2.put("puerto"   , "1234");
				imp2.put("dsimpres" , "Lexmark H25 - Recepcion");
				imp2.put("cdpapel"  , "C");
				imp2.put("dspapel"  , "CREDENCIALES");
				lista.add(imp2);
				
				Map<String,String> imp3 = new HashMap<String,String>();
				imp3.put("cdunieco" , "1000");
				imp3.put("ip"       , "3.3.3.3");
				imp3.put("puerto"   , "1234");
				imp3.put("dsimpres" , "Lexmark H25 - Principal");
				imp3.put("cdpapel"  , "C");
				imp3.put("dspapel"  , "CREDENCIALES");
				lista.add(imp3);
				
				Map<String,String> imp4 = new HashMap<String,String>();
				imp4.put("cdunieco" , "1001");
				imp4.put("ip"       , "2.2.2.2");
				imp4.put("puerto"   , "1234");
				imp4.put("dsimpres" , "HP LaserJet 2200 - Papeleria");
				imp4.put("cdpapel"  , "P");
				imp4.put("dspapel"  , "PAPELERIA");
				lista.add(imp4);
				
				Map<String,String> imp5 = new HashMap<String,String>();
				imp5.put("cdunieco" , "1000");
				imp5.put("ip"       , "1.1.1.1");
				imp5.put("puerto"   , "1234");
				imp5.put("dsimpres" , "HP LaserJet 1100 - Papeleria 2");
				imp5.put("cdpapel"  , "P");
				imp5.put("dspapel"  , "PAPELERIA");
				lista.add(imp5);
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