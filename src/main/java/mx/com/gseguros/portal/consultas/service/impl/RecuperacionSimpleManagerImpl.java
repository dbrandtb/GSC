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
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSlistVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSmapVO;
import mx.com.gseguros.portal.endosos.dao.EndososDAO;
import mx.com.gseguros.utils.Utilerias;

import org.apache.log4j.Logger;

public class RecuperacionSimpleManagerImpl implements RecuperacionSimpleManager
{
	private Map<String,Object>session;
	
	private static Logger logger=Logger.getLogger(RecuperacionSimpleManagerImpl.class);
	
	private ConsultasDAO  consultasDAO;
	private CotizacionDAO cotizacionDAO;
	private EndososDAO    endososDAO;
	
	/*
	 * Utilerias
	 */
	private void setCheckpoint(String checkpoint)
	{
		logger.debug(Utilerias.join("checkpoint-->",checkpoint));
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
		
		if(ex.getClass().equals(ApplicationException.class))
		{
			resp.setRespuesta(Utilerias.join(ex.getMessage()," #",timestamp));
		}
		else
		{
			resp.setRespuesta(Utilerias.join("Error ",getCheckpoint().toLowerCase()," #",timestamp));
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
		logger.info(Utilerias.join(
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
				resp.getSmap().put("dsclausu" , Utilerias.join("Texto de la clausula ",cdclausu));
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
						sb.append(Utilerias.join("CVE_",nombre));
						sb.append(Utilerias.join("DES_",nombre));
						primero = false;
					}
					else
					{
						sb.append(Utilerias.join("@#@CVE_",nombre));
						sb.append(Utilerias.join("@#@DES_",nombre));
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
			
			setCheckpoint("0");
		}
		catch(Exception ex)
		{
			manejaException(ex, resp);
		}
		
		logger.info(Utilerias.join(
				 "\n@@@@@@ " , resp
				,"\n@@@@@@ recuperacionSimple @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return resp;
	}
	
	@Override
	public ManagerRespuestaSlistVO recuperacionSimpleLista(
			RecuperacionSimple proc
			,Map<String,String>params
			,String cdsisrol
			,String cdusuari
			)
	{
		logger.info(Utilerias.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ recuperacionSimpleLista @@@@@@"
				,"\n@@@@@@ proc="     , proc
				,"\n@@@@@@ params="   , params
				,"\n@@@@@@ cdsisrol=" , cdsisrol
				,"\n@@@@@@ cdusuari=" , cdusuari
				));
		
		ManagerRespuestaSlistVO resp=new ManagerRespuestaSlistVO(true);
		
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
				resp.setSlist(Utilerias.concatenarParametros(consultasDAO.cargarTvalosit(cdunieco, cdramo, estado, nmpoliza, nmsuplem),false));
			}
			else if(proc.equals(RecuperacionSimple.RECUPERAR_CONFIGURACION_VALOSIT_FLOTILLAS))
			{
				String cdramo   = params.get("cdramo");
				String cdtipsit = params.get("cdtipsit");
				String negocio  = params.get("negocio");
				resp.setSlist(cotizacionDAO.cargarConfiguracionTvalositFlotillas(cdramo, cdtipsit, negocio));
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
				resp.setSlist(consultasDAO.recuperarPolizasEndosables(cdunieco,cdramo,estado,nmpoliza,nmpoliex,ramo,cdagente,statusVig));
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
			
			setCheckpoint("0");
		}
		catch(Exception ex)
		{
			manejaException(ex, resp);
		}
		
		logger.info(Utilerias.join(
				 "\n@@@@@@ " , resp
				,"\n@@@@@@ recuperacionSimpleLista @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return resp;
	}
	
	/*
	 * Getters y setters
	 */
	public void setConsultasDAO(ConsultasDAO consultasDAO) {
		this.consultasDAO = consultasDAO;
	}

	public void setCotizacionDAO(CotizacionDAO cotizacionDAO) {
		this.cotizacionDAO = cotizacionDAO;
	}

	public void setEndososDAO(EndososDAO endososDAO) {
		this.endososDAO = endososDAO;
	}
	
}