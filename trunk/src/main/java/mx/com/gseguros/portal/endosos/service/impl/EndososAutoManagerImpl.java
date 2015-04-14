package mx.com.gseguros.portal.endosos.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.catalogos.dao.PersonasDAO;
import mx.com.gseguros.portal.consultas.dao.ConsultasDAO;
import mx.com.gseguros.portal.consultas.dao.ConsultasPolizaDAO;
import mx.com.gseguros.portal.consultas.model.PolizaAseguradoVO;
import mx.com.gseguros.portal.consultas.model.PolizaDTO;
import mx.com.gseguros.portal.cotizacion.dao.CotizacionDAO;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaVoidVO;
import mx.com.gseguros.portal.cotizacion.model.SlistSmapVO;
import mx.com.gseguros.portal.endosos.dao.EndososDAO;
import mx.com.gseguros.portal.endosos.service.EndososAutoManager;
import mx.com.gseguros.portal.general.dao.PantallasDAO;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.portal.general.util.Ramo;
import mx.com.gseguros.portal.general.util.RolSistema;
import mx.com.gseguros.portal.general.util.TipoEndoso;
import mx.com.gseguros.portal.general.util.TipoTramite;
import mx.com.gseguros.portal.mesacontrol.dao.MesaControlDAO;
import mx.com.gseguros.utils.Constantes;
import mx.com.gseguros.utils.Utilerias;
import mx.com.gseguros.utils.Utils;
import mx.com.gseguros.ws.autosgs.dao.AutosSIGSDAO;
import mx.com.gseguros.ws.autosgs.emision.model.EmisionAutosVO;
import mx.com.gseguros.ws.autosgs.service.EmisionAutosService;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EndososAutoManagerImpl implements EndososAutoManager
{
	private static Logger           logger       = Logger.getLogger(EndososAutoManagerImpl.class);
	private static SimpleDateFormat renderFechas = new SimpleDateFormat("dd/MM/yyyy");
	
	@Autowired
	private PantallasDAO        pantallasDAO;
	
	@Autowired
	private EndososDAO          endososDAO;
	
	@Autowired
	private ConsultasDAO        consultasDAO;
	
	@Autowired
	@Qualifier("consultasDAOICEImpl")
	private ConsultasPolizaDAO  consultasPolizaDAO;
	
	@Autowired
	private CotizacionDAO       cotizacionDAO;
	
	@Autowired
	private PersonasDAO         personasDAO;
	
	@Autowired
	private MesaControlDAO      mesaControlDAO;
	
	@Autowired
	private AutosSIGSDAO        autosDAOSIGS;
	
	@Autowired
	@Qualifier("emisionAutosServiceImpl")
	private EmisionAutosService emisionAutosService;
	
	@Value("${caratula.impresion.autos.url}")
	private String urlImpresionCaratula;

	@Value("${caratula.impresion.autos.endosob.url}")
	private String urlImpresionCaratulaEndosoB;
	
	@Value("${caratula.impresion.autos.serviciopublico.url}")
	private String urlImpresionCaratulaServicioPublico;
	
	@Value("${caratula.impresion.autos.flotillas.url}")
	private String urlImpresionCaratulaServicioFotillas;
	
	@Value("${recibo.impresion.autos.url}")
	private String urlImpresionRecibos;
	
	@Value("${caic.impresion.autos.url}")
	private String urlImpresionCaic;
	
	@Value("${ap.impresion.autos.url}")
	private String urlImpresionAp;
	
	@Value("${incisos.flotillas.impresion.autos.url}")
	private String urlImpresionIncisosFlotillas;
	
	@Value("${tarjeta.iden.impresion.autos.url}")
	private String urlImpresionTarjetaIdentificacion;
	
	private Map<String,Object> session;
	@Override
	public Map<String,Object> construirMarcoEndosos(String cdusuari,String cdsisrol) throws Exception
	{
		logger.info(Utilerias.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ construirMarcoEndosos @@@@@@"
				,"\n@@@@@@ cdusuari=" , cdusuari
				,"\n@@@@@@ cdsisrol=" , cdsisrol
				));
		
		Map<String,Object> valores = new HashMap<String,Object>();
		Map<String,Item>   items   = new HashMap<String,Item>();
		
		String paso="";
		
		try
		{
			paso="Recuperando componentes del formulario de busqueda";
			logger.info(paso);
			List<ComponenteVO>componentesFiltro=pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,null //cdramo
					,null //cdtipsit
					,null //estado
					,cdsisrol
					,"MARCO_ENDOSOS_AUTO" //pantalla
					,"FORM_BUSQUEDA"      //seccion
					,null //orden
					);
			
			paso="Recuperando componentes del grid de polizas";
			logger.info(paso);
			List<ComponenteVO>componentesGridPolizas=pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,null //cdramo
					,null //cdtipsit
					,null //estado
					,cdsisrol
					,"MARCO_ENDOSOS_AUTO" //pantalla
					,"GRID_POLIZAS"       //seccion
					,null //orden
					);
			
			paso="Recuperando componentes del grid de historico de poliza";
			logger.info(paso);
			List<ComponenteVO>componentesGridHistoricoPoliza=pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,null //cdramo
					,null //cdtipsit
					,null //estado
					,cdsisrol
					,"MARCO_ENDOSOS_AUTO"    //pantalla
					,"GRID_HISTORICO_POLIZA" //seccion
					,null //orden
					);
			
			paso="Recuperando componentes del grid de grupos";
			logger.info(paso);
			List<ComponenteVO>componentesGridGrupos=pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,null //cdramo
					,null //cdtipsit
					,null //estado
					,cdsisrol
					,"MARCO_ENDOSOS_AUTO" //pantalla
					,"GRID_GRUPOS"        //seccion
					,null //orden
					);
			
			paso="Recuperando componentes del grid de familias";
			logger.info(paso);
			List<ComponenteVO>componentesGridFamilias=pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,null //cdramo
					,null //cdtipsit
					,null //estado
					,cdsisrol
					,"MARCO_ENDOSOS_AUTO" //pantalla
					,"GRID_FAMILIAS"        //seccion
					,null //orden
					);
			
			paso="Construyendo componentes del formulario de busqueda";
			logger.info(paso);
			GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gc.generaComponentes(componentesFiltro, true, false, true, false, false, false);
			items.put("formBusqItems" , gc.getItems());
			
			paso="Construyendo componentes del grid de polizas";
			logger.info(paso);
			gc.generaComponentes(componentesGridPolizas, true, false, false, true, true, false);
			items.put("gridPolizasColumns" , gc.getColumns());
			
			paso="Construyendo componentes del grid de historico de poliza";
			logger.info(paso);
			gc.generaComponentes(componentesGridHistoricoPoliza, true, false, false, true, false, false);
			items.put("gridHistoricoColumns" , gc.getColumns());
			
			paso="Construyendo componentes del grid de grupos";
			logger.info(paso);
			gc.generaComponentes(componentesGridGrupos, true, false, false, true, false, false);
			items.put("gridGruposColumns" , gc.getColumns());
			
			paso="Construyendo componentes del grid de familias";
			logger.info(paso);
			gc.generaComponentes(componentesGridFamilias, true, false, false, true, false, false);
			items.put("gridFamiliasColumns" , gc.getColumns());
			
			valores.put("items" , items);
			
			String cdagente="";
			if(cdsisrol.equals(RolSistema.AGENTE.getCdsisrol()))
			{
				paso = "Recuperando clave de agente";
				logger.info(paso);
				Map<String,String>params=new LinkedHashMap<String,String>();
				params.put("cdusuari" , cdusuari);
				cdagente = mesaControlDAO.cargarCdagentePorCdusuari(params);
			}
			valores.put("cdagente" , cdagente);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.info(Utilerias.join(
				 "\n@@@@@@ valores=",valores
				,"\n@@@@@@ construirMarcoEndosos @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return valores;
	}
	
	@Override
	public String recuperarColumnasIncisoRamo(String cdramo)throws Exception
	{
		logger.info(Utilerias.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ recuperarColumnasIncisoRamo @@@@@@"
				,"\n@@@@@@ cdramo=",cdramo
				));
		
		String cols = null;
		String paso = "";
		
		try
		{
			paso="Recuperando columnas de incisos para el producto";
			logger.info(paso);
			List<ComponenteVO>columnas=pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,cdramo
					,null //cdtipsit
					,null //estado
					,null //cdsisrol
					,"MARCO_ENDOSOS_AUTO" //pantalla
					,"COLUMNAS_INCISO"    //seccion
					,null //orden
					);
			
			paso="Construyendo columnas de incisos para el producto";
			logger.info(paso);
			GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gc.generaComponentes(columnas, true, false, false, true, true, false);
			cols=gc.getColumns().toString();
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex,paso);
		}
		
		logger.info(Utilerias.join(
				 "\n@@@@@@ columnas=",cols
				,"\n@@@@@@ recuperarColumnasIncisoRamo @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return cols;
	}
	
	@Override
	public SlistSmapVO recuperarEndososClasificados(
			String cdramo
			,String nivel
			,String multiple
			,String tipoflot
			,List<Map<String,String>>incisos
			,String cdsisrol
			,String cancelada
			)throws Exception
	{
		logger.info(Utilerias.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ recuperarEndososClasificados @@@@@@"
				,"\n@@@@@@ cdramo="    , cdramo
				,"\n@@@@@@ nivel="     , nivel
				,"\n@@@@@@ multiple="  , multiple
				,"\n@@@@@@ tipoflot="  , tipoflot
				,"\n@@@@@@ incisos="   , incisos
				,"\n@@@@@@ cdsisrol="  , cdsisrol
				,"\n@@@@@@ cancelada=" , cancelada
				));

		SlistSmapVO resp = new SlistSmapVO();
		String      paso = null;
		
		try
		{
			double millis = System.currentTimeMillis();
			double random = 1000d*Math.random();
			logger.debug(millis);
			logger.debug(random);
			String stamp  = String.format("%.0f.%.0f",millis,random);
			
			logger.debug(Utilerias.join("stamp=",stamp));
			
			resp.getSmap().put("stamp" , stamp);
			
			if(incisos.size()>0)
			{
				paso="Insertando situaciones para evaluacion";
				logger.info(paso);
				
				for(Map<String,String>inciso:incisos)
				{
					endososDAO.insertarIncisoEvaluacion(
							stamp
							,inciso.get("CDUNIECO")
							,inciso.get("CDRAMO")
							,inciso.get("ESTADO")
							,inciso.get("NMPOLIZA")
							,inciso.get("NMSUPLEM_TVAL")
							,inciso.get("NMSITUAC")
							,inciso.get("CDTIPSIT")
							);
				}
			}
			
			paso="Recuperando lista de endosos";
			logger.info(paso);
			
			if("N".equals(cancelada))
			{
				resp.setSlist(endososDAO.recuperarEndososClasificados(stamp,cdramo,nivel,multiple,tipoflot,cdsisrol));
			}
			else if("POLIZA".equals(nivel))
			{
				List<Map<String,String>> lista = new ArrayList<Map<String,String>>();
				Map<String,String>       mapa  = new HashMap<String,String>();
				mapa.put("CDTIPSUP"        , "57");
				mapa.put("DSTIPSUP"        , "REHABILITACI&Oacute;N");
				mapa.put("LIGA"            , "/endosos/includes/endosoRehabilitacionAuto.action");
				mapa.put("TIPO_VALIDACION" , "");
				lista.add(mapa);
				resp.setSlist(lista);
			}
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.info(Utilerias.join(
				 "\n@@@@@@ ",resp
				,"\n@@@@@@ recuperarEndososClasificados @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return resp;
	}
	
	@Override
	public Map<String,Item>pantallaEndosoValosit(
			String cdtipsup
			,String cdramo
			)throws Exception
	{
		logger.info(Utilerias.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ pantallaEndosoValosit @@@@@@"
				,"\n@@@@@@ cdtipsup=" , cdtipsup
				,"\n@@@@@@ cdramo="   , cdramo
				));
		
		Map<String,Item>items = new HashMap<String,Item>();
		String          paso  = null;
		
		try
		{
			paso="Recuperando columnas de lectura";
			logger.info(paso);
			
			List<ComponenteVO>columnasLectura=pantallasDAO.obtenerComponentes(
					cdtipsup //cdtiptra
					,null    //cdunieco
					,cdramo
					,null    //cdtipsit
					,null    //estado
					,null    //cdsisrol
					,"ENDOSO_VALOSIT_AUTO"
					,"COLUMNAS_LECTURA"
					,null    //orden
					);
			
			paso="Recuperando columnas editables";
			logger.info(paso);
			
			List<ComponenteVO>columnasEditables=pantallasDAO.obtenerComponentes(
					cdtipsup //cdtiptra
					,null    //cdunieco
					,cdramo
					,null    //cdtipsit
					,null    //estado
					,null    //cdsisrol
					,"ENDOSO_VALOSIT_AUTO"
					,"COLUMNAS_EDITABLES"
					,null    //orden
					);
			
			paso="Construyendo columnas de lectura";
			logger.info(paso);
			
			GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gc.generaComponentes(columnasLectura, true, false, false, true, false, false);
			
			items.put("gridColumnsLectura"   , gc.getColumns());
			
			paso="Construyendo columnas editables";
			logger.info(paso);
			
			gc.generaComponentes(columnasEditables, true, false, false, true, true, false);
			
			items.put("gridColumnsEditables" , gc.getColumns());
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.info(Utilerias.join(
				 "\n@@@@@@ pantallaEndosoValosit @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return items;
	}
	
	@Override
	public void guardarTvalositEndoso(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String status
			,String cdtipsit
			,String otvalor01,String otvalor02,String otvalor03,String otvalor04,String otvalor05
			,String otvalor06,String otvalor07,String otvalor08,String otvalor09,String otvalor10
			,String otvalor11,String otvalor12,String otvalor13,String otvalor14,String otvalor15
			,String otvalor16,String otvalor17,String otvalor18,String otvalor19,String otvalor20
			,String otvalor21,String otvalor22,String otvalor23,String otvalor24,String otvalor25
			,String otvalor26,String otvalor27,String otvalor28,String otvalor29,String otvalor30
			,String otvalor31,String otvalor32,String otvalor33,String otvalor34,String otvalor35
			,String otvalor36,String otvalor37,String otvalor38,String otvalor39,String otvalor40
			,String otvalor41,String otvalor42,String otvalor43,String otvalor44,String otvalor45
			,String otvalor46,String otvalor47,String otvalor48,String otvalor49,String otvalor50
			,String otvalor51,String otvalor52,String otvalor53,String otvalor54,String otvalor55
			,String otvalor56,String otvalor57,String otvalor58,String otvalor59,String otvalor60
			,String otvalor61,String otvalor62,String otvalor63,String otvalor64,String otvalor65
			,String otvalor66,String otvalor67,String otvalor68,String otvalor69,String otvalor70
			,String otvalor71,String otvalor72,String otvalor73,String otvalor74,String otvalor75
			,String otvalor76,String otvalor77,String otvalor78,String otvalor79,String otvalor80
			,String otvalor81,String otvalor82,String otvalor83,String otvalor84,String otvalor85
			,String otvalor86,String otvalor87,String otvalor88,String otvalor89,String otvalor90
			,String otvalor91,String otvalor92,String otvalor93,String otvalor94,String otvalor95
			,String otvalor96,String otvalor97,String otvalor98,String otvalor99
			,String tstamp)throws Exception
	{
		logger.info(Utilerias.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ guardarTvalositEndoso @@@@@@"
				,"\n@@@@@@ cdunieco="  , cdunieco
				,"\n@@@@@@ cdramo="    , cdramo
				,"\n@@@@@@ estado="    , estado
				,"\n@@@@@@ nmpoliza="  , nmpoliza
				,"\n@@@@@@ nmsituac="  , nmsituac
				,"\n@@@@@@ nmsuplem="  , nmsuplem
				,"\n@@@@@@ status="    , status
				,"\n@@@@@@ cdtipsit="  , cdtipsit
				,"\n@@@@@@ otvalor01=" , otvalor01
				,"\n@@@@@@ otvalor02=" , otvalor02
				,"\n@@@@@@ otvalor03=" , otvalor03
				,"\n@@@@@@ otvalor04=" , otvalor04
				,"\n@@@@@@ otvalor05=" , otvalor05
				,"\n@@@@@@ otvalor06=" , otvalor06
				,"\n@@@@@@ otvalor07=" , otvalor07
				,"\n@@@@@@ otvalor08=" , otvalor08
				,"\n@@@@@@ otvalor09=" , otvalor09
				,"\n@@@@@@ otvalor10=" , otvalor10
				,"\n@@@@@@ otvalor11=" , otvalor11
				,"\n@@@@@@ otvalor12=" , otvalor12
				,"\n@@@@@@ otvalor13=" , otvalor13
				,"\n@@@@@@ otvalor14=" , otvalor14
				,"\n@@@@@@ otvalor15=" , otvalor15
				,"\n@@@@@@ otvalor16=" , otvalor16
				,"\n@@@@@@ otvalor17=" , otvalor17
				,"\n@@@@@@ otvalor18=" , otvalor18
				,"\n@@@@@@ otvalor19=" , otvalor19
				,"\n@@@@@@ otvalor20=" , otvalor20
				,"\n@@@@@@ otvalor21=" , otvalor21
				,"\n@@@@@@ otvalor22=" , otvalor22
				,"\n@@@@@@ otvalor23=" , otvalor23
				,"\n@@@@@@ otvalor24=" , otvalor24
				,"\n@@@@@@ otvalor25=" , otvalor25
				,"\n@@@@@@ otvalor26=" , otvalor26
				,"\n@@@@@@ otvalor27=" , otvalor27
				,"\n@@@@@@ otvalor28=" , otvalor28
				,"\n@@@@@@ otvalor29=" , otvalor29
				,"\n@@@@@@ otvalor30=" , otvalor30
				,"\n@@@@@@ otvalor31=" , otvalor31
				,"\n@@@@@@ otvalor32=" , otvalor32
				,"\n@@@@@@ otvalor33=" , otvalor33
				,"\n@@@@@@ otvalor34=" , otvalor34
				,"\n@@@@@@ otvalor35=" , otvalor35
				,"\n@@@@@@ otvalor36=" , otvalor36
				,"\n@@@@@@ otvalor37=" , otvalor37
				,"\n@@@@@@ otvalor38=" , otvalor38
				,"\n@@@@@@ otvalor39=" , otvalor39
				,"\n@@@@@@ otvalor40=" , otvalor40
				,"\n@@@@@@ otvalor41=" , otvalor41
				,"\n@@@@@@ otvalor42=" , otvalor42
				,"\n@@@@@@ otvalor43=" , otvalor43
				,"\n@@@@@@ otvalor44=" , otvalor44
				,"\n@@@@@@ otvalor45=" , otvalor45
				,"\n@@@@@@ otvalor46=" , otvalor46
				,"\n@@@@@@ otvalor47=" , otvalor47
				,"\n@@@@@@ otvalor48=" , otvalor48
				,"\n@@@@@@ otvalor49=" , otvalor49
				,"\n@@@@@@ otvalor50=" , otvalor50
				,"\n@@@@@@ otvalor51=" , otvalor51
				,"\n@@@@@@ otvalor52=" , otvalor52
				,"\n@@@@@@ otvalor53=" , otvalor53
				,"\n@@@@@@ otvalor54=" , otvalor54
				,"\n@@@@@@ otvalor55=" , otvalor55
				,"\n@@@@@@ otvalor56=" , otvalor56
				,"\n@@@@@@ otvalor57=" , otvalor57
				,"\n@@@@@@ otvalor58=" , otvalor58
				,"\n@@@@@@ otvalor59=" , otvalor59
				,"\n@@@@@@ otvalor60=" , otvalor60
				,"\n@@@@@@ otvalor61=" , otvalor61
				,"\n@@@@@@ otvalor62=" , otvalor62
				,"\n@@@@@@ otvalor63=" , otvalor63
				,"\n@@@@@@ otvalor64=" , otvalor64
				,"\n@@@@@@ otvalor65=" , otvalor65
				,"\n@@@@@@ otvalor66=" , otvalor66
				,"\n@@@@@@ otvalor67=" , otvalor67
				,"\n@@@@@@ otvalor68=" , otvalor68
				,"\n@@@@@@ otvalor69=" , otvalor69
				,"\n@@@@@@ otvalor70=" , otvalor70
				,"\n@@@@@@ otvalor71=" , otvalor71
				,"\n@@@@@@ otvalor72=" , otvalor72
				,"\n@@@@@@ otvalor73=" , otvalor73
				,"\n@@@@@@ otvalor74=" , otvalor74
				,"\n@@@@@@ otvalor75=" , otvalor75
				,"\n@@@@@@ otvalor76=" , otvalor76
				,"\n@@@@@@ otvalor77=" , otvalor77
				,"\n@@@@@@ otvalor78=" , otvalor78
				,"\n@@@@@@ otvalor79=" , otvalor79
				,"\n@@@@@@ otvalor80=" , otvalor80
				,"\n@@@@@@ otvalor81=" , otvalor81
				,"\n@@@@@@ otvalor82=" , otvalor82
				,"\n@@@@@@ otvalor83=" , otvalor83
				,"\n@@@@@@ otvalor84=" , otvalor84
				,"\n@@@@@@ otvalor85=" , otvalor85
				,"\n@@@@@@ otvalor86=" , otvalor86
				,"\n@@@@@@ otvalor87=" , otvalor87
				,"\n@@@@@@ otvalor88=" , otvalor88
				,"\n@@@@@@ otvalor89=" , otvalor89
				,"\n@@@@@@ otvalor90=" , otvalor90
				,"\n@@@@@@ otvalor91=" , otvalor91
				,"\n@@@@@@ otvalor92=" , otvalor92
				,"\n@@@@@@ otvalor93=" , otvalor93
				,"\n@@@@@@ otvalor94=" , otvalor94
				,"\n@@@@@@ otvalor95=" , otvalor95
				,"\n@@@@@@ otvalor96=" , otvalor96
				,"\n@@@@@@ otvalor97=" , otvalor97
				,"\n@@@@@@ otvalor98=" , otvalor98
				,"\n@@@@@@ otvalor99=" , otvalor99
				,"\n@@@@@@ tsmap="     , tstamp
				));
		
		String paso = "Guardando atributos";
		
		try
		{
			endososDAO.guardarTvalositEndoso(cdunieco
					,cdramo
					,estado
					,nmpoliza
					,nmsituac
					,nmsuplem
					,status
					,cdtipsit
					,otvalor01 , otvalor02 , otvalor03 , otvalor04 , otvalor05
					,otvalor06 , otvalor07 , otvalor08 , otvalor09 , otvalor10
					,otvalor11 , otvalor12 , otvalor13 , otvalor14 , otvalor15
					,otvalor16 , otvalor17 , otvalor18 , otvalor19 , otvalor20
					,otvalor21 , otvalor22 , otvalor23 , otvalor24 , otvalor25
					,otvalor26 , otvalor27 , otvalor28 , otvalor29 , otvalor30
					,otvalor31 , otvalor32 , otvalor33 , otvalor34 , otvalor35
					,otvalor36 , otvalor37 , otvalor38 , otvalor39 , otvalor40
					,otvalor41 , otvalor42 , otvalor43 , otvalor44 , otvalor45
					,otvalor46 , otvalor47 , otvalor48 , otvalor49 , otvalor50
					,otvalor51 , otvalor52 , otvalor53 , otvalor54 , otvalor55
					,otvalor56 , otvalor57 , otvalor58 , otvalor59 , otvalor60
					,otvalor61 , otvalor62 , otvalor63 , otvalor64 , otvalor65
					,otvalor66 , otvalor67 , otvalor68 , otvalor69 , otvalor70
					,otvalor71 , otvalor72 , otvalor73 , otvalor74 , otvalor75
					,otvalor76 , otvalor77 , otvalor78 , otvalor79 , otvalor80
					,otvalor81 , otvalor82 , otvalor83 , otvalor84 , otvalor85
					,otvalor86 , otvalor87 , otvalor88 , otvalor89 , otvalor90
					,otvalor91 , otvalor92 , otvalor93 , otvalor94 , otvalor95
					,otvalor96 , otvalor97 , otvalor98 , otvalor99
					,tstamp);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		

		logger.info(Utilerias.join(
				 "\n@@@@@@ guardarTvalositEndoso @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	
	@Override
	public void confirmarEndosoTvalositAuto(
			String cdtipsup
			,String tstamp
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String feefecto
			,String cdusuari
			,String cdsisrol
			,String cdelemen
			,UserVO usuarioSesion
			,List<Map<String,String>> incisos
			)throws Exception
	{
		logger.info(Utilerias.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ confirmarEndosoTvalositAuto @@@@@@"
				,"\n@@@@@@ cdtipsup=" , cdtipsup
				,"\n@@@@@@ tstamp="   , tstamp
				,"\n@@@@@@ cdunieco=" , cdunieco
				,"\n@@@@@@ cdramo="   , cdramo
				,"\n@@@@@@ estado="   , estado
				,"\n@@@@@@ nmpoliza=" , nmpoliza
				,"\n@@@@@@ feefecto=" , feefecto
				,"\n@@@@@@ cdusuari=" , cdusuari
				,"\n@@@@@@ cdsisrol=" , cdsisrol
				,"\n@@@@@@ cdelemen=" , cdelemen
				,"\n@@@@@@ incisos="  , incisos
				));
		
		String paso="Guardando datos temporales";
		
		try
		{
			Date fechaEndoso = renderFechas.parse(feefecto);
			
			for(Map<String,String>inciso:incisos)
			{
				endososDAO.guardarTvalositEndoso(
						cdunieco
						,cdramo
						,estado
						,nmpoliza
						,inciso.get("NMSITUAC")
						,inciso.get("NMSUPLEM")
						,inciso.get("STATUS")
						,inciso.get("CDTIPSIT")
						,inciso.get("OTVALOR01")
						,inciso.get("OTVALOR02")
						,inciso.get("OTVALOR03")
						,inciso.get("OTVALOR04")
						,inciso.get("OTVALOR05")
						,inciso.get("OTVALOR06")
						,inciso.get("OTVALOR07")
						,inciso.get("OTVALOR08")
						,inciso.get("OTVALOR09")
						,inciso.get("OTVALOR10")
						,inciso.get("OTVALOR11")
						,inciso.get("OTVALOR12")
						,inciso.get("OTVALOR13")
						,inciso.get("OTVALOR14")
						,inciso.get("OTVALOR15")
						,inciso.get("OTVALOR16")
						,inciso.get("OTVALOR17")
						,inciso.get("OTVALOR18")
						,inciso.get("OTVALOR19")
						,inciso.get("OTVALOR20")
						,inciso.get("OTVALOR21")
						,inciso.get("OTVALOR22")
						,inciso.get("OTVALOR23")
						,inciso.get("OTVALOR24")
						,inciso.get("OTVALOR25")
						,inciso.get("OTVALOR26")
						,inciso.get("OTVALOR27")
						,inciso.get("OTVALOR28")
						,inciso.get("OTVALOR29")
						,inciso.get("OTVALOR30")
						,inciso.get("OTVALOR31")
						,inciso.get("OTVALOR32")
						,inciso.get("OTVALOR33")
						,inciso.get("OTVALOR34")
						,inciso.get("OTVALOR35")
						,inciso.get("OTVALOR36")
						,inciso.get("OTVALOR37")
						,inciso.get("OTVALOR38")
						,inciso.get("OTVALOR39")
						,inciso.get("OTVALOR40")
						,inciso.get("OTVALOR41")
						,inciso.get("OTVALOR42")
						,inciso.get("OTVALOR43")
						,inciso.get("OTVALOR44")
						,inciso.get("OTVALOR45")
						,inciso.get("OTVALOR46")
						,inciso.get("OTVALOR47")
						,inciso.get("OTVALOR48")
						,inciso.get("OTVALOR49")
						,inciso.get("OTVALOR50")
						,inciso.get("OTVALOR51")
						,inciso.get("OTVALOR52")
						,inciso.get("OTVALOR53")
						,inciso.get("OTVALOR54")
						,inciso.get("OTVALOR55")
						,inciso.get("OTVALOR56")
						,inciso.get("OTVALOR57")
						,inciso.get("OTVALOR58")
						,inciso.get("OTVALOR59")
						,inciso.get("OTVALOR60")
						,inciso.get("OTVALOR61")
						,inciso.get("OTVALOR62")
						,inciso.get("OTVALOR63")
						,inciso.get("OTVALOR64")
						,inciso.get("OTVALOR65")
						,inciso.get("OTVALOR66")
						,inciso.get("OTVALOR67")
						,inciso.get("OTVALOR68")
						,inciso.get("OTVALOR69")
						,inciso.get("OTVALOR70")
						,inciso.get("OTVALOR71")
						,inciso.get("OTVALOR72")
						,inciso.get("OTVALOR73")
						,inciso.get("OTVALOR74")
						,inciso.get("OTVALOR75")
						,inciso.get("OTVALOR76")
						,inciso.get("OTVALOR77")
						,inciso.get("OTVALOR78")
						,inciso.get("OTVALOR79")
						,inciso.get("OTVALOR80")
						,inciso.get("OTVALOR81")
						,inciso.get("OTVALOR82")
						,inciso.get("OTVALOR83")
						,inciso.get("OTVALOR84")
						,inciso.get("OTVALOR85")
						,inciso.get("OTVALOR86")
						,inciso.get("OTVALOR87")
						,inciso.get("OTVALOR88")
						,inciso.get("OTVALOR89")
						,inciso.get("OTVALOR90")
						,inciso.get("OTVALOR91")
						,inciso.get("OTVALOR92")
						,inciso.get("OTVALOR93")
						,inciso.get("OTVALOR94")
						,inciso.get("OTVALOR95")
						,inciso.get("OTVALOR96")
						,inciso.get("OTVALOR97")
						,inciso.get("OTVALOR98")
						,inciso.get("OTVALOR99")
						,tstamp
						);
			}
			
			paso = "Confirmando endoso";
			Map<String,Object> resParams = endososDAO.confirmarEndosoTvalositAuto(
					cdtipsup
					,tstamp
					,cdunieco
					,cdramo
					,estado
					,nmpoliza
					,fechaEndoso
					,cdusuari
					,cdsisrol
					,cdelemen
					);
			
			String nmsuplem = (String) resParams.get("pv_nmsuplem_o");
			String ntramite = (String) resParams.get("pv_ntramite_o");
			String tipoGrupoInciso = (String) resParams.get("pv_tipoflot_o");
			
			
			/**
			 * PARA LLAMAR WS SEGUN TIPO DE ENDOSO
			 */
			if(TipoEndoso.BENEFICIARIO_AUTO.getCdTipSup().toString().equalsIgnoreCase(cdtipsup)){
				if(this.endosoBeneficiario(cdunieco, cdramo, estado, nmpoliza, nmsuplem, ntramite, cdtipsup)){
					logger.info("Endoso de Beneficiario exitoso...");
				}else{
					logger.error("Error al ejecutar los WS de endoso de Beneficiario");
					
					boolean endosoRevertido = endososDAO.revierteEndosoFallido(cdunieco, cdramo, estado, nmpoliza, null, nmsuplem);
					if(endosoRevertido){
						logger.error("Endoso revertido exitosamente.");
						throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. Favor de volver a itentar.");
					}else{
						logger.error("Error al revertir el endoso");
						throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. No se ha revertido el endoso.");
					}
				}
			}else if(TipoEndoso.PLACAS_Y_MOTOR.getCdTipSup().toString().equalsIgnoreCase(cdtipsup)){
				if(this.endosoPlacasMotor(cdunieco, cdramo, estado, nmpoliza, nmsuplem, ntramite, cdtipsup)){
					logger.info("Endoso de Placas y motor exitoso...");
				}else{
					logger.error("Error al ejecutar los WS de endoso de Placas y Motor");
					boolean endosoRevertido = endososDAO.revierteEndosoFallido(cdunieco, cdramo, estado, nmpoliza, null, nmsuplem);
					if(endosoRevertido){
						logger.error("Endoso revertido exitosamente.");
						throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. Favor de volver a itentar.");
					}else{
						logger.error("Error al revertir el endoso");
						throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. No se ha revertido el endoso.");
					}
				}
			}else if(TipoEndoso.SERIE_AUTO.getCdTipSup().toString().equalsIgnoreCase(cdtipsup)){
				if(this.endosoSerie(cdunieco, cdramo, estado, nmpoliza, nmsuplem, ntramite, cdtipsup)){
					logger.info("Endoso de Serie exitoso...");
				}else{
					logger.error("Error al ejecutar los WS de endoso de Serie");
					boolean endosoRevertido = endososDAO.revierteEndosoFallido(cdunieco, cdramo, estado, nmpoliza, null, nmsuplem);
					if(endosoRevertido){
						logger.error("Endoso revertido exitosamente.");
						throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. Favor de volver a itentar.");
					}else{
						logger.error("Error al revertir el endoso");
						throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. No se ha revertido el endoso.");
					}
				}
			}else if(TipoEndoso.ADAPTACIONES_EFECTO_RC.getCdTipSup().toString().equalsIgnoreCase(cdtipsup)){
				if(this.endosoAdaptacionesRC(cdunieco, cdramo, estado, nmpoliza, nmsuplem, ntramite, cdtipsup)){
					logger.info("Endoso de AdaptacionesRC exitoso...");
				}else{
					logger.error("Error al ejecutar los WS de endoso de AdaptacionesRC");
					
					boolean endosoRevertido = endososDAO.revierteEndosoFallido(cdunieco, cdramo, estado, nmpoliza, null, nmsuplem);
					if(endosoRevertido){
						logger.error("Endoso revertido exitosamente.");
						throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. Favor de volver a itentar.");
					}else{
						logger.error("Error al revertir el endoso");
						throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. No se ha revertido el endoso.");
					}
				}
			}else{

				EmisionAutosVO aux = emisionAutosService.cotizaEmiteAutomovilWS(cdunieco, cdramo, estado, nmpoliza, nmsuplem, ntramite, null, usuarioSesion);
				if(aux == null || !aux.isExitoRecibos()){
					
					logger.error("Error al ejecutar los WS de endoso");
					boolean endosoRevertido = endososDAO.revierteEndosoFallido(cdunieco, cdramo, estado, nmpoliza, null, nmsuplem);
							
					if(endosoRevertido){
						logger.error("Endoso revertido exitosamente.");
						throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. Favor de volver a itentar.");
					}else{
						logger.error("Error al revertir el endoso");
						throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. No se ha revertido el endoso.");
					}
					
				}
				
				ejecutaCaratulaEndosoTarifaSigs(cdunieco, cdramo, estado, nmpoliza, nmsuplem, ntramite, cdtipsup, tipoGrupoInciso, aux);
			
			}
			
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.info(Utilerias.join(
				 "\n@@@@@@ confirmarEndosoTvalositAuto @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	
	@Override
	public Map<String,Object> recuperarDatosEndosoAltaIncisoAuto(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			)throws Exception
	{
		logger.info(Utilerias.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ recuperarDatosEndosoAltaIncisoAuto @@@@@@"
				,"\n@@@@@@ cdunieco=" , cdunieco
				,"\n@@@@@@ cdramo="   , cdramo
				,"\n@@@@@@ estado="   , estado
				,"\n@@@@@@ nmpoliza=" , nmpoliza
				,"\n@@@@@@ nmsuplem=" , nmsuplem
				));
		
		Map<String,Object> salida = new HashMap<String,Object>();
		String             paso   = null;
		
		try
		{
			paso = "Recuperando inciso de poliza";
			logger.info(paso);
			List<Map<String,String>> incisosBase  = consultasDAO.cargarTbasvalsit(cdunieco,cdramo,estado,nmpoliza,nmsuplem);
			Map<String,String>       incisoPoliza = new HashMap<String,String>();
			for(Map<String,String>incisoBase:incisosBase)
			{
				String nmsituac    = incisoBase.get("NMSITUAC");
				String cdtipsitAnt = incisoBase.get("CDTIPSIT");
				if(!"-1".equals(nmsituac))
				{
					incisoBase.putAll(consultasDAO.cargarMpolisitSituac(cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmsituac));
				}
				incisoBase.put("CDTIPSIT" , cdtipsitAnt);
				incisoBase.put("cdtipsit" , cdtipsitAnt);
				incisoBase.put("cdplan"   , incisoBase.get("CDPLAN"));
				incisoBase.put("nmsituac" , incisoBase.get("NMSITUAC"));
				if(incisoBase.get("CDTIPSIT").equals("XPOLX"))
				{
					logger.debug(Utilerias.join("Es XPOLX",incisoBase));
					for(Entry<String,String>en:incisoBase.entrySet())
					{
						incisoPoliza.put("parametros.pv_"+en.getKey().toLowerCase(),en.getValue());
					}
				}
			}
			logger.debug(Utilerias.join("inciso poliza=",incisoPoliza));
			salida.put("incisoPoliza",incisoPoliza);
			
			paso = "Recuperando atributos adicionales de poliza";
			logger.info(paso);
			Map<String,String> tvalopol    = new HashMap<String,String>();
			Map<String,String> tvalopolAux = cotizacionDAO.cargarTvalopol(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					);
			for(Entry<String,String>en:tvalopolAux.entrySet())
			{
				tvalopol.put(Utilerias.join("aux.",en.getKey().substring("parametros.pv_".length())),en.getValue());
			}
			logger.debug(Utilerias.join("tvalopol=",tvalopol));
			salida.put("tvalopol",tvalopol);
			
			paso = "Recuperando configuracion de incisos";
			logger.info(paso);
			List<Map<String,String>> tconvalsit = Utilerias.concatenarParametros(consultasDAO.cargarTconvalsit(cdunieco,cdramo,estado,nmpoliza,nmsuplem),false);
			logger.debug(Utilerias.join("tconvalsit=",tconvalsit));
			salida.put("tconvalsit",tconvalsit);
			
			paso = "Recuperando relacion poliza-contratante";
			logger.info(paso);
			String cdperson = "";
			String cdideper = "";
			Map<String,String>relContratante0=consultasDAO.cargarMpoliperSituac(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					,nmsuplem
					,"0"//nmsituac
					);
			Map<String,String>relContratante1=consultasDAO.cargarMpoliperSituac(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					,nmsuplem
					,"1"//nmsituac
					);
			
			if(relContratante0!=null||relContratante1!=null)
			{
				paso = "Recuperando contratante";
				logger.info(paso);
				if(relContratante0!=null)
				{
					cdperson = relContratante0.get("CDPERSON");
				}
				else
				{
					cdperson = relContratante1.get("CDPERSON");
				}
				Map<String,String>contratante = personasDAO.cargarPersonaPorCdperson(cdperson);
				cdideper = contratante.get("CDIDEPER");
			}
			logger.debug(Utilerias.join("cdperson=",cdperson,", cdideper=",cdideper));
			salida.put("cdperson" , cdperson);
			salida.put("cdideper" , cdideper);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.info(Utilerias.join(
				 "\n@@@@@@ ",salida
				,"\n@@@@@@ recuperarDatosEndosoAltaIncisoAuto @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return salida;
	}
	
	@Override
	public void confirmarEndosoAltaIncisoAuto(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,List<Map<String,String>>incisos
			,String cdusuari
			,String cdelemen
			,String cdtipsup
			,String fecha
			,UserVO usuarioSesion
			)throws Exception
	{
		logger.info(Utilerias.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ confirmarEndosoAltaIncisoAuto @@@@@@"
				,"\n@@@@@@ cdunieco=" , cdunieco
				,"\n@@@@@@ cdramo="   , cdramo
				,"\n@@@@@@ estado="   , estado
				,"\n@@@@@@ nmpoliza=" , nmpoliza
				,"\n@@@@@@ incisos="  , incisos
				,"\n@@@@@@ cdusuari=" , cdusuari
				,"\n@@@@@@ cdelemen=" , cdelemen
				,"\n@@@@@@ cdtipsup=" , cdtipsup
				,"\n@@@@@@ fecha="    , fecha
				));
		
		String paso = null;
		
		try
		{
			Date fechaEfecto = renderFechas.parse(fecha);
			
			String tstamp = String.format("%.0f.%.0f",(double)System.currentTimeMillis(),1000d*Math.random());
			
			paso = "Guardando situaciones temporales";
			logger.info(paso);
			for(Map<String,String>inciso : incisos)
			{
				endososDAO.guardarTvalositEndoso(
						cdunieco
						,cdramo
						,estado
						,nmpoliza
						,inciso.get("nmsituac")
						,"0"
						,"V"
						,inciso.get("cdtipsit")
						,inciso.get("parametros.pv_otvalor01")
						,inciso.get("parametros.pv_otvalor02")
						,inciso.get("parametros.pv_otvalor03")
						,inciso.get("parametros.pv_otvalor04")
						,inciso.get("parametros.pv_otvalor05")
						,inciso.get("parametros.pv_otvalor06")
						,inciso.get("parametros.pv_otvalor07")
						,inciso.get("parametros.pv_otvalor08")
						,inciso.get("parametros.pv_otvalor09")
						,inciso.get("parametros.pv_otvalor10")
						,inciso.get("parametros.pv_otvalor11")
						,inciso.get("parametros.pv_otvalor12")
						,inciso.get("parametros.pv_otvalor13")
						,inciso.get("parametros.pv_otvalor14")
						,inciso.get("parametros.pv_otvalor15")
						,inciso.get("parametros.pv_otvalor16")
						,inciso.get("parametros.pv_otvalor17")
						,inciso.get("parametros.pv_otvalor18")
						,inciso.get("parametros.pv_otvalor19")
						,inciso.get("parametros.pv_otvalor20")
						,inciso.get("parametros.pv_otvalor21")
						,inciso.get("parametros.pv_otvalor22")
						,inciso.get("parametros.pv_otvalor23")
						,inciso.get("parametros.pv_otvalor24")
						,inciso.get("parametros.pv_otvalor25")
						,inciso.get("parametros.pv_otvalor26")
						,inciso.get("parametros.pv_otvalor27")
						,inciso.get("parametros.pv_otvalor28")
						,inciso.get("parametros.pv_otvalor29")
						,inciso.get("parametros.pv_otvalor30")
						,inciso.get("parametros.pv_otvalor31")
						,inciso.get("parametros.pv_otvalor32")
						,inciso.get("parametros.pv_otvalor33")
						,inciso.get("parametros.pv_otvalor34")
						,inciso.get("parametros.pv_otvalor35")
						,inciso.get("parametros.pv_otvalor36")
						,inciso.get("parametros.pv_otvalor37")
						,inciso.get("parametros.pv_otvalor38")
						,inciso.get("parametros.pv_otvalor39")
						,inciso.get("parametros.pv_otvalor40")
						,inciso.get("parametros.pv_otvalor41")
						,inciso.get("parametros.pv_otvalor42")
						,inciso.get("parametros.pv_otvalor43")
						,inciso.get("parametros.pv_otvalor44")
						,inciso.get("parametros.pv_otvalor45")
						,inciso.get("parametros.pv_otvalor46")
						,inciso.get("parametros.pv_otvalor47")
						,inciso.get("parametros.pv_otvalor48")
						,inciso.get("parametros.pv_otvalor49")
						,inciso.get("parametros.pv_otvalor50")
						,inciso.get("parametros.pv_otvalor51")
						,inciso.get("parametros.pv_otvalor52")
						,inciso.get("parametros.pv_otvalor53")
						,inciso.get("parametros.pv_otvalor54")
						,inciso.get("parametros.pv_otvalor55")
						,inciso.get("parametros.pv_otvalor56")
						,inciso.get("parametros.pv_otvalor57")
						,inciso.get("parametros.pv_otvalor58")
						,inciso.get("parametros.pv_otvalor59")
						,inciso.get("parametros.pv_otvalor60")
						,inciso.get("parametros.pv_otvalor61")
						,inciso.get("parametros.pv_otvalor62")
						,inciso.get("parametros.pv_otvalor63")
						,inciso.get("parametros.pv_otvalor64")
						,inciso.get("parametros.pv_otvalor65")
						,inciso.get("parametros.pv_otvalor66")
						,inciso.get("parametros.pv_otvalor67")
						,inciso.get("parametros.pv_otvalor68")
						,inciso.get("parametros.pv_otvalor69")
						,inciso.get("parametros.pv_otvalor70")
						,inciso.get("parametros.pv_otvalor71")
						,inciso.get("parametros.pv_otvalor72")
						,inciso.get("parametros.pv_otvalor73")
						,inciso.get("parametros.pv_otvalor74")
						,inciso.get("parametros.pv_otvalor75")
						,inciso.get("parametros.pv_otvalor76")
						,inciso.get("parametros.pv_otvalor77")
						,inciso.get("parametros.pv_otvalor78")
						,inciso.get("parametros.pv_otvalor79")
						,inciso.get("parametros.pv_otvalor80")
						,inciso.get("parametros.pv_otvalor81")
						,inciso.get("parametros.pv_otvalor82")
						,inciso.get("parametros.pv_otvalor83")
						,inciso.get("parametros.pv_otvalor84")
						,inciso.get("parametros.pv_otvalor85")
						,inciso.get("parametros.pv_otvalor86")
						,inciso.get("parametros.pv_otvalor87")
						,inciso.get("parametros.pv_otvalor88")
						,inciso.get("parametros.pv_otvalor89")
						,inciso.get("parametros.pv_otvalor90")
						,inciso.get("parametros.pv_otvalor91")
						,inciso.get("parametros.pv_otvalor92")
						,inciso.get("parametros.pv_otvalor93")
						,inciso.get("parametros.pv_otvalor94")
						,inciso.get("parametros.pv_otvalor95")
						,inciso.get("parametros.pv_otvalor96")
						,inciso.get("parametros.pv_otvalor97")
						,inciso.get("parametros.pv_otvalor98")
						,inciso.get("cdplan")
						,tstamp
						);
			}
			
			paso="Confirmando endoso";
			logger.info(paso);
			
			
			Map<String,Object> resParams = endososDAO.confirmarEndosoAltaIncisoAuto(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					,tstamp
					,cdusuari
					,cdelemen
					,cdtipsup
					,fechaEfecto
					);
			
			String nmsuplem = (String) resParams.get("pv_nmsuplem_o");
			String ntramite = (String) resParams.get("pv_ntramite_o");
			String tipoGrupoInciso = (String) resParams.get("pv_tipoflot_o");
			
			EmisionAutosVO aux = emisionAutosService.cotizaEmiteAutomovilWS(cdunieco, cdramo, estado, nmpoliza, nmsuplem, ntramite, null, usuarioSesion);
			if(aux == null || !aux.isExitoRecibos()){
				logger.error("Error al ejecutar los WS de endoso");
				
				boolean endosoRevertido = endososDAO.revierteEndosoFallido(cdunieco, cdramo, estado, nmpoliza, null, nmsuplem);
				
				if(endosoRevertido){
					logger.error("Endoso revertido exitosamente.");
					throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. Favor de volver a itentar.");
				}else{
					logger.error("Error al revertir el endoso");
					throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. No se ha revertido el endoso.");
				}
				
			}
			
			ejecutaCaratulaEndosoTarifaSigs(cdunieco, cdramo, estado, nmpoliza, nmsuplem, ntramite, cdtipsup, tipoGrupoInciso, aux);
		
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.info(Utilerias.join(
				 "\n@@@@@@ confirmarEndosoAltaIncisoAuto @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	
	@Override
	public Map<String,Item> endosoBajaIncisos(
			String cdramo
			)throws Exception
	{
		logger.info(Utilerias.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ endosoBajaIncisos @@@@@@"
				,"\n@@@@@@ cdramo=" , cdramo
				));
		
		Map<String,Item> items = new HashMap<String,Item>();
		String           paso  = null;
		
		try
		{
			paso = "Recuperando columnas de inciso";
			List<ComponenteVO> colsComps = pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,cdramo
					,null //cdtipsit
					,null //estado
					,null //cdsisrol
					,"ENDOSO_BAJA_INCISOS"
					,"COLUMNAS_INCISO"
					,null //orden
					);
			
			paso = "Construyendo componentes de pantalla";
			GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gc.generaComponentes(colsComps, true, false, false, true, true, false);
			items.put("gridColumns" , gc.getColumns());
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.info(Utilerias.join(
				 "\n@@@@@@ items=" , items
				,"\n@@@@@@ endosoBajaIncisos @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return items;
	}
	
	@Override
	public void confirmarEndosoBajaIncisos(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,List<Map<String,String>>incisos
			,String cdusuari
			,String cdelemen
			,String cdtipsup
			,String fecha
			,UserVO usuarioSesion
			)throws Exception
	{
		logger.info(Utilerias.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ confirmarEndosoBajaIncisos @@@@@@"
				,"\n@@@@@@ cdunieco=" , cdunieco
				,"\n@@@@@@ cdramo="   , cdramo
				,"\n@@@@@@ estado="   , estado
				,"\n@@@@@@ nmpoliza=" , nmpoliza
				,"\n@@@@@@ incisos="  , incisos
				,"\n@@@@@@ cdusuari=" , cdusuari
				,"\n@@@@@@ cdelemen=" , cdelemen
				,"\n@@@@@@ cdtipsup=" , cdtipsup
				,"\n@@@@@@ fecha="    , fecha
				));
		
		String paso = null;
		
		try
		{
			Date fechaEfecto = renderFechas.parse(fecha);
			
			String tstamp = String.format("%.0f.%.0f",(double)System.currentTimeMillis(),1000d*Math.random());
			
			paso = "Guardando situaciones temporales";
			logger.info(paso);
			for(Map<String,String>inciso : incisos)
			{
				endososDAO.guardarTvalositEndoso(
						cdunieco
						,cdramo
						,estado
						,nmpoliza
						,inciso.get("NMSITUAC")
						,inciso.get("NMSUPLEM")
						,inciso.get("STATUS")
						,inciso.get("CDTIPSIT")
						,inciso.get("OTVALOR01")
						,inciso.get("OTVALOR02")
						,inciso.get("OTVALOR03")
						,inciso.get("OTVALOR04")
						,inciso.get("OTVALOR05")
						,inciso.get("OTVALOR06")
						,inciso.get("OTVALOR07")
						,inciso.get("OTVALOR08")
						,inciso.get("OTVALOR09")
						,inciso.get("OTVALOR10")
						,inciso.get("OTVALOR11")
						,inciso.get("OTVALOR12")
						,inciso.get("OTVALOR13")
						,inciso.get("OTVALOR14")
						,inciso.get("OTVALOR15")
						,inciso.get("OTVALOR16")
						,inciso.get("OTVALOR17")
						,inciso.get("OTVALOR18")
						,inciso.get("OTVALOR19")
						,inciso.get("OTVALOR20")
						,inciso.get("OTVALOR21")
						,inciso.get("OTVALOR22")
						,inciso.get("OTVALOR23")
						,inciso.get("OTVALOR24")
						,inciso.get("OTVALOR25")
						,inciso.get("OTVALOR26")
						,inciso.get("OTVALOR27")
						,inciso.get("OTVALOR28")
						,inciso.get("OTVALOR29")
						,inciso.get("OTVALOR30")
						,inciso.get("OTVALOR31")
						,inciso.get("OTVALOR32")
						,inciso.get("OTVALOR33")
						,inciso.get("OTVALOR34")
						,inciso.get("OTVALOR35")
						,inciso.get("OTVALOR36")
						,inciso.get("OTVALOR37")
						,inciso.get("OTVALOR38")
						,inciso.get("OTVALOR39")
						,inciso.get("OTVALOR40")
						,inciso.get("OTVALOR41")
						,inciso.get("OTVALOR42")
						,inciso.get("OTVALOR43")
						,inciso.get("OTVALOR44")
						,inciso.get("OTVALOR45")
						,inciso.get("OTVALOR46")
						,inciso.get("OTVALOR47")
						,inciso.get("OTVALOR48")
						,inciso.get("OTVALOR49")
						,inciso.get("OTVALOR50")
						,inciso.get("OTVALOR51")
						,inciso.get("OTVALOR52")
						,inciso.get("OTVALOR53")
						,inciso.get("OTVALOR54")
						,inciso.get("OTVALOR55")
						,inciso.get("OTVALOR56")
						,inciso.get("OTVALOR57")
						,inciso.get("OTVALOR58")
						,inciso.get("OTVALOR59")
						,inciso.get("OTVALOR60")
						,inciso.get("OTVALOR61")
						,inciso.get("OTVALOR62")
						,inciso.get("OTVALOR63")
						,inciso.get("OTVALOR64")
						,inciso.get("OTVALOR65")
						,inciso.get("OTVALOR66")
						,inciso.get("OTVALOR67")
						,inciso.get("OTVALOR68")
						,inciso.get("OTVALOR69")
						,inciso.get("OTVALOR70")
						,inciso.get("OTVALOR71")
						,inciso.get("OTVALOR72")
						,inciso.get("OTVALOR73")
						,inciso.get("OTVALOR74")
						,inciso.get("OTVALOR75")
						,inciso.get("OTVALOR76")
						,inciso.get("OTVALOR77")
						,inciso.get("OTVALOR78")
						,inciso.get("OTVALOR79")
						,inciso.get("OTVALOR80")
						,inciso.get("OTVALOR81")
						,inciso.get("OTVALOR82")
						,inciso.get("OTVALOR83")
						,inciso.get("OTVALOR84")
						,inciso.get("OTVALOR85")
						,inciso.get("OTVALOR86")
						,inciso.get("OTVALOR87")
						,inciso.get("OTVALOR88")
						,inciso.get("OTVALOR89")
						,inciso.get("OTVALOR90")
						,inciso.get("OTVALOR91")
						,inciso.get("OTVALOR92")
						,inciso.get("OTVALOR93")
						,inciso.get("OTVALOR94")
						,inciso.get("OTVALOR95")
						,inciso.get("OTVALOR96")
						,inciso.get("OTVALOR97")
						,inciso.get("OTVALOR98")
						,inciso.get("CDPLAN")
						,tstamp
						);
			}
			
			paso="Confirmando endoso";
			logger.info(paso);

			Map<String,Object> resParams = endososDAO.confirmarEndosoBajaIncisos(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					,tstamp
					,cdusuari
					,cdelemen
					,cdtipsup
					,fechaEfecto
					);
			
			String nmsuplem = (String) resParams.get("pv_nmsuplem_o");
			String ntramite = (String) resParams.get("pv_ntramite_o");
			String tipoGrupoInciso = (String) resParams.get("pv_tipoflot_o");
			

			EmisionAutosVO aux = emisionAutosService.cotizaEmiteAutomovilWS(cdunieco, cdramo, estado, nmpoliza, nmsuplem, ntramite, null, usuarioSesion);
			if(aux == null || !aux.isExitoRecibos()){
				logger.error("Error al ejecutar los WS de endoso");
				
				boolean endosoRevertido = endososDAO.revierteEndosoFallido(cdunieco, cdramo, estado, nmpoliza, null, nmsuplem);
				
				if(endosoRevertido){
					logger.error("Endoso revertido exitosamente.");
					throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. Favor de volver a itentar.");
				}else{
					logger.error("Error al revertir el endoso");
					throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. No se ha revertido el endoso.");
				}
				
			}
			
			ejecutaCaratulaEndosoTarifaSigs(cdunieco, cdramo, estado, nmpoliza, nmsuplem, ntramite, cdtipsup, tipoGrupoInciso, aux);
			
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.info(Utilerias.join(
				 "\n@@@@@@ confirmarEndosoBajaIncisos @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	

	private boolean endosoAseguradoAlterno(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsuplem, String ntramite, String cdtipsup){
		
		logger.debug(">>>>> Entrando a metodo Cambio AseguradoAlterno");
		
		List<Map<String,String>> datos = null;
		int endosoRecuperado = -1;
		
		try{
			HashMap<String, String> params = new LinkedHashMap<String, String>();
			params.put("pv_cdunieco_i" , cdunieco);
			params.put("pv_cdramo_i" , cdramo);
			params.put("pv_estado_i" , estado);
			params.put("pv_nmpoliza_i" , nmpoliza);
			params.put("pv_nmsuplem_i" , nmsuplem);
			
			datos = endososDAO.obtieneDatosEndAseguradoAlterno(params);
			
		} catch (Exception e1) {
			logger.error("Error en llamar al PL de obtencion de datos para Cambio AseguradoAlterno para SIGS",e1);
			return false;
		}	
		
		if(datos != null && !datos.isEmpty()){
			HashMap<String, Object> paramsEnd = new HashMap<String, Object>();
				Map<String,String> datosEnIt = datos.get(0);
			
				paramsEnd.put("vIdMotivo"  , datosEnIt.get("IdMotivo"));
				paramsEnd.put("vSucursal"  , datosEnIt.get("Sucursal"));
				paramsEnd.put("vRamo"      , datosEnIt.get("Ramo"));
				paramsEnd.put("vPoliza"    , datosEnIt.get("Poliza"));
				paramsEnd.put("vAsegAlterno", datosEnIt.get("AsegAlterno"));
				paramsEnd.put("vFEndoso"   , datosEnIt.get("FEndoso"));
				
			
			try{
				
				Integer res = autosDAOSIGS.endosoAseguradoAlterno(paramsEnd);
				
				logger.debug("Respuesta de Cambio AseguradoAlterno numero de endoso: " + res);
				
				if(res == null || res == 0 || res == -1){
					logger.debug("Endoso Cambio AseguradoAlterno no exitoso");
					return false;
				}else{
					endosoRecuperado = res.intValue();
				}
				
			} catch (Exception e){
				logger.error("Error en Envio Cambio AseguradoAlterno Auto: " + e.getMessage(),e);
			}
			
			
			if(endosoRecuperado != -1){
				try{
					HashMap<String, String> params = new LinkedHashMap<String, String>();
					params.put("pv_cdunieco_i" , cdunieco);
					params.put("pv_cdramo_i" , cdramo);
					params.put("pv_estado_i" , estado);
					params.put("pv_nmpoliza_i" , nmpoliza);
					params.put("pv_nmsuplem_i" , nmsuplem);
					params.put("pv_numend_sigs_i", Integer.toString(endosoRecuperado));
					
					endososDAO.actualizaNumeroEndosSigs(params);
					
					this.generaCaratulasSigs(cdunieco, cdramo, estado, nmpoliza, nmsuplem, ntramite, cdtipsup, Integer.toString(endosoRecuperado));
					
				} catch (Exception e1) {
					logger.error("Error en llamar al PL Para actualizar endoso AseguradoAlterno de SIGS",e1);
					return false;
				}
			}else{
				logger.debug("Endoso Cambio AseguradoAlterno no exitoso, valor de endoso en -1");
				return false;
			}
			
		}else{
			logger.warn("Aviso, No se tienen datos de Cambio AseguradoAlterno");
			return false;
		}
		
		return true;
	}

	private boolean endosoAdaptacionesRC(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsuplem, String ntramite, String cdtipsup){
		
		logger.debug(">>>>> Entrando a metodo Cambio AdaptacionesRC");
		
		List<Map<String,String>> datos = null;
		int endosoRecuperado = -1;
		
		try{
			HashMap<String, String> params = new LinkedHashMap<String, String>();
			params.put("pv_cdunieco_i" , cdunieco);
			params.put("pv_cdramo_i" , cdramo);
			params.put("pv_estado_i" , estado);
			params.put("pv_nmpoliza_i" , nmpoliza);
			params.put("pv_nmsuplem_i" , nmsuplem);
			
			datos = endososDAO.obtieneDatosEndAdaptacionesRC(params);
			
		} catch (Exception e1) {
			logger.error("Error en llamar al PL de obtencion de datos para Cambio AdaptacionesRC para SIGS",e1);
			return false;
		}	
		
		if(datos != null && !datos.isEmpty()){
			HashMap<String, Object> paramsEnd = new HashMap<String, Object>();
			Map<String,String> datosEnIt = datos.get(0);
			
			paramsEnd.put("vIdMotivo"  , datosEnIt.get("IdMotivo"));
			paramsEnd.put("vSucursal"  , datosEnIt.get("Sucursal"));
			paramsEnd.put("vRamo"      , datosEnIt.get("Ramo"));
			paramsEnd.put("vPoliza"    , datosEnIt.get("Poliza"));
			paramsEnd.put("vInciso"    , datosEnIt.get("Inciso"));
			paramsEnd.put("vTexto"     , datosEnIt.get("vTexto"));
			paramsEnd.put("vFEndoso"   , datosEnIt.get("FEndoso"));
			
			
			try{
				
				Integer res = autosDAOSIGS.endosoAdaptacionesRC(paramsEnd);
				
				logger.debug("Respuesta de Cambio AdaptacionesRC numero de endoso: " + res);
				
				if(res == null || res == 0 || res == -1){
					logger.debug("Endoso Cambio AdaptacionesRC no exitoso");
					return false;
				}else{
					endosoRecuperado = res.intValue();
				}
				
			} catch (Exception e){
				logger.error("Error en Envio Cambio AdaptacionesRC Auto: " + e.getMessage(),e);
			}
			
			
			if(endosoRecuperado != -1){
				try{
					HashMap<String, String> params = new LinkedHashMap<String, String>();
					params.put("pv_cdunieco_i" , cdunieco);
					params.put("pv_cdramo_i" , cdramo);
					params.put("pv_estado_i" , estado);
					params.put("pv_nmpoliza_i" , nmpoliza);
					params.put("pv_nmsuplem_i" , nmsuplem);
					params.put("pv_numend_sigs_i", Integer.toString(endosoRecuperado));
					
					endososDAO.actualizaNumeroEndosSigs(params);
					
					this.generaCaratulasSigs(cdunieco, cdramo, estado, nmpoliza, nmsuplem, ntramite, cdtipsup, Integer.toString(endosoRecuperado));
					
				} catch (Exception e1) {
					logger.error("Error en llamar al PL Para actualizar endoso AdaptacionesRC de SIGS",e1);
					return false;
				}
			}else{
				logger.debug("Endoso Cambio AdaptacionesRC no exitoso, valor de endoso en -1");
				return false;
			}
			
		}else{
			logger.warn("Aviso, No se tienen datos de Cambio AdaptacionesRC");
			return false;
		}
		
		return true;
	}

	
	private boolean endosoVigenciaPoliza(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsuplem, String ntramite, String cdtipsup){
		
		logger.debug(">>>>> Entrando a metodo Cambio Vigencia");
		
		List<Map<String,String>> datos = null;
		int endosoRecuperado = -1;
		
		try{
			HashMap<String, String> params = new LinkedHashMap<String, String>();
			params.put("pv_cdunieco_i" , cdunieco);
			params.put("pv_cdramo_i" , cdramo);
			params.put("pv_estado_i" , estado);
			params.put("pv_nmpoliza_i" , nmpoliza);
			params.put("pv_nmsuplem_i" , nmsuplem);
			
			datos = endososDAO.obtieneDatosEndVigenciaPol(params);
			
		} catch (Exception e1) {
			logger.error("Error en llamar al PL de obtencion de datos para Cambio Vigencia para SIGS",e1);
			return false;
		}	
		
		if(datos != null && !datos.isEmpty()){
			
			for(Map<String,String> datosEnd : datos){
				try{
					
					HashMap<String, Object> paramsEnd = new HashMap<String, Object>();
					paramsEnd.put("vIdMotivo"  , datosEnd.get("IdMotivo"));
					paramsEnd.put("vSucursal"  , datosEnd.get("Sucursal"));
					paramsEnd.put("vRamo"    , datosEnd.get("Ramo"));
					paramsEnd.put("vPoliza"   , datosEnd.get("Poliza"));
					paramsEnd.put("vTEndoso"    , StringUtils.isBlank(datosEnd.get("TEndoso"))?" " : datosEnd.get("TEndoso"));
					paramsEnd.put("vEndoso"  , datosEnd.get("Endoso"));
					paramsEnd.put("vRecibo"     , datosEnd.get("asd"));
					paramsEnd.put("vFIniRec"    , datosEnd.get("asd"));
					paramsEnd.put("vFFinRec"    , datosEnd.get("asd"));
					paramsEnd.put("vFIniPol"    , datosEnd.get("asd"));
					paramsEnd.put("vFEndoso"    , datosEnd.get("asd"));
					paramsEnd.put("vEndoB" , (endosoRecuperado==-1)?0:endosoRecuperado);
					
					Integer res = autosDAOSIGS.endosoVigenciaPol(paramsEnd);
					
					logger.debug("Respuesta de Cambio Vigencia, numero de endoso: " + res);
					
					if(res == null || res == 0 || res == -1){
						logger.debug("Endoso Cambio Vigencia no exitoso");
						return false;
					}else{
						endosoRecuperado = res.intValue();
					}
					
				} catch (Exception e){
					logger.error("Error en Envio Cambio Vigencia Auto: " + e.getMessage(),e);
				}
			
			}
			
			if(endosoRecuperado != -1){
				try{
					HashMap<String, String> params = new LinkedHashMap<String, String>();
					params.put("pv_cdunieco_i" , cdunieco);
					params.put("pv_cdramo_i" , cdramo);
					params.put("pv_estado_i" , estado);
					params.put("pv_nmpoliza_i" , nmpoliza);
					params.put("pv_nmsuplem_i" , nmsuplem);
					params.put("pv_numend_sigs_i", Integer.toString(endosoRecuperado));
					
					endososDAO.actualizaNumeroEndosSigs(params);
					
					this.generaCaratulasSigs(cdunieco, cdramo, estado, nmpoliza, nmsuplem, ntramite, cdtipsup, Integer.toString(endosoRecuperado));
					
				} catch (Exception e1) {
					logger.error("Error en llamar al PL Para actualizar endoso Vigencia de SIGS",e1);
					return false;
				}
			}else{
				logger.debug("Endoso Cambio Vigencia no exitoso, valor de endoso en -1");
				return false;
			}
				
		}else{
			logger.warn("Aviso, No se tienen datos de Cambio Vigencia");
			return false;
		}

		
		return true;
	}
	
	
	private boolean endosoBeneficiario(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsuplem, String ntramite, String cdtipsup){
		
		logger.debug(">>>>> Entrando a metodo Cambio Beneficiario");
		
		List<Map<String,String>> datos = null;
		int endosoRecuperado = -1;
		
		try{
			HashMap<String, String> params = new LinkedHashMap<String, String>();
			params.put("pv_cdunieco_i" , cdunieco);
			params.put("pv_cdramo_i" , cdramo);
			params.put("pv_estado_i" , estado);
			params.put("pv_nmpoliza_i" , nmpoliza);
			params.put("pv_nmsuplem_i" , nmsuplem);
			
			datos = endososDAO.obtieneDatosEndBeneficiario(params);
			
		} catch (Exception e1) {
			logger.error("Error en llamar al PL de obtencion de datos para Cambio Beneficiario para SIGS",e1);
			return false;
		}	
		
		if(datos != null && !datos.isEmpty()){
			
			String incisos = "";
			HashMap<String, Object> paramsEnd = new HashMap<String, Object>();
			
			for(Map<String,String> datosEnIt : datos){
				paramsEnd.put("vIdMotivo"  , datosEnIt.get("IdMotivo"));
				paramsEnd.put("vSucursal"  , datosEnIt.get("Sucursal"));
				paramsEnd.put("vRamo"    , datosEnIt.get("Ramo"));
				paramsEnd.put("vPoliza"   , datosEnIt.get("Poliza"));
				paramsEnd.put("vBeneficiario"  , datosEnIt.get("Beneficiario"));
				
				if(StringUtils.isNotBlank(incisos)){
					incisos = incisos +",";
				}
				
				incisos = incisos + datosEnIt.get("Inciso");
				
				paramsEnd.put("vFEndoso"   , datosEnIt.get("FEndoso"));
			}
			
			paramsEnd.put("vListaIncisos"  , incisos);
			
			try{
				
				Integer res = autosDAOSIGS.endosoBeneficiario(paramsEnd);
				
				logger.debug("Respuesta de Cambio Beneficiario numero de endoso: " + res);
				
				if(res == null || res == 0 || res == -1){
					logger.debug("Endoso Cambio Beneficiario no exitoso");
					return false;
				}else{
					endosoRecuperado = res.intValue();
				}
				
			} catch (Exception e){
				logger.error("Error en Envio Cambio Beneficiario Auto: " + e.getMessage(),e);
			}
			
			
			if(endosoRecuperado != -1){
				try{
					HashMap<String, String> params = new LinkedHashMap<String, String>();
					params.put("pv_cdunieco_i" , cdunieco);
					params.put("pv_cdramo_i" , cdramo);
					params.put("pv_estado_i" , estado);
					params.put("pv_nmpoliza_i" , nmpoliza);
					params.put("pv_nmsuplem_i" , nmsuplem);
					params.put("pv_numend_sigs_i", Integer.toString(endosoRecuperado));
					
					endososDAO.actualizaNumeroEndosSigs(params);
					
					this.generaCaratulasSigs(cdunieco, cdramo, estado, nmpoliza, nmsuplem, ntramite, cdtipsup, Integer.toString(endosoRecuperado));
					
				} catch (Exception e1) {
					logger.error("Error en llamar al PL Para actualizar endoso Beneficiario de SIGS",e1);
					return false;
				}
			}else{
				logger.debug("Endoso Cambio Beneficiario no exitoso, valor de endoso en -1");
				return false;
			}
			
		}else{
			logger.warn("Aviso, No se tienen datos de Cambio Beneficiario");
			return false;
		}
		
		return true;
	}
	
	private boolean endosoPlacasMotor(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsuplem, String ntramite, String cdtipsup){
		
		logger.debug(">>>>> Entrando a metodo Cambio Placas Motor");
		
		List<Map<String,String>> datos = null;
		
		try{
			HashMap<String, String> params = new LinkedHashMap<String, String>();
			params.put("pv_cdunieco_i" , cdunieco);
			params.put("pv_cdramo_i" , cdramo);
			params.put("pv_estado_i" , estado);
			params.put("pv_nmpoliza_i" , nmpoliza);
			params.put("pv_nmsuplem_i" , nmsuplem);
			
			datos = endososDAO.obtieneDatosEndPlacasMotor(params);
			
		} catch (Exception e1) {
			logger.error("Error en llamar al PL de obtencion de datos para Cambio Placas Motor para SIGS",e1);
			return false;
		}	
		
		if(datos != null && !datos.isEmpty()){
			int endosoRecuperado = -1;
			for(Map<String,String> datosEnd : datos){
				try{
					
					HashMap<String, Object> paramsEnd = new HashMap<String, Object>();
					paramsEnd.put("vIdMotivo"  , datosEnd.get("IdMotivo"));
					paramsEnd.put("vSucursal"  , datosEnd.get("Sucursal"));
					paramsEnd.put("vRamo"    , datosEnd.get("Ramo"));
					paramsEnd.put("vPoliza"   , datosEnd.get("Poliza"));
					paramsEnd.put("vTEndoso"    , StringUtils.isBlank(datosEnd.get("TEndoso"))?" " : datosEnd.get("TEndoso"));
					paramsEnd.put("vEndoso"  , datosEnd.get("Endoso"));
					paramsEnd.put("vInciso"     , datosEnd.get("Inciso"));
					paramsEnd.put("vPlacas"    , datosEnd.get("Placas"));
					paramsEnd.put("vMotor"   , datosEnd.get("Motor"));
					paramsEnd.put("vEndoB" , (endosoRecuperado==-1)?0:endosoRecuperado);
					paramsEnd.put("vFEndoso", datosEnd.get("FEndoso"));
					
					Integer res = autosDAOSIGS.endosoPlacasMotor(paramsEnd);
					
					logger.debug("Respuesta de Cambio Placas Motor, numero de endoso: " + res);
					
					if(res == null || res == 0 || res == -1){
						logger.debug("Endoso Cambio Placas Motor no exitoso");
						return false;
					}else{
						endosoRecuperado = res.intValue();
					}
					
				} catch (Exception e){
					logger.error("Error en Envio Cambio Placas Motor Auto: " + e.getMessage(),e);
				}
			
			}
			
			if(endosoRecuperado != -1){
				try{
					HashMap<String, String> params = new LinkedHashMap<String, String>();
					params.put("pv_cdunieco_i" , cdunieco);
					params.put("pv_cdramo_i" , cdramo);
					params.put("pv_estado_i" , estado);
					params.put("pv_nmpoliza_i" , nmpoliza);
					params.put("pv_nmsuplem_i" , nmsuplem);
					params.put("pv_numend_sigs_i", Integer.toString(endosoRecuperado));
					
					endososDAO.actualizaNumeroEndosSigs(params);
					
					this.generaCaratulasSigs(cdunieco, cdramo, estado, nmpoliza, nmsuplem, ntramite, cdtipsup, Integer.toString(endosoRecuperado));
					
				} catch (Exception e1) {
					logger.error("Error en llamar al PL Para actualizar endoso Placas Motor de SIGS",e1);
					return false;
				}
			}else{
				logger.debug("Endoso Cambio Placas Motor no exitoso, valor de endoso en -1");
				return false;
			}
				
		}else{
			logger.warn("Aviso, No se tienen datos de Cambio Placas Motor");
			return false;
		}
		
		return true;
	}

	private boolean endosoSerie(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsuplem, String ntramite, String cdtipsup){
		
		logger.debug(">>>>> Entrando a metodo Cambio Serie");
		
		List<Map<String,String>> datos = null;
		
		try{
			HashMap<String, String> params = new LinkedHashMap<String, String>();
			params.put("pv_cdunieco_i" , cdunieco);
			params.put("pv_cdramo_i" , cdramo);
			params.put("pv_estado_i" , estado);
			params.put("pv_nmpoliza_i" , nmpoliza);
			params.put("pv_nmsuplem_i" , nmsuplem);
			
			datos = endososDAO.obtieneDatosEndSerie(params);
			
		} catch (Exception e1) {
			logger.error("Error en llamar al PL de obtencion de datos para Cambio Serie para SIGS",e1);
			return false;
		}	
		
		if(datos != null && !datos.isEmpty()){
			int endosoRecuperado = -1;
			for(Map<String,String> datosEnd : datos){
				try{
					
					HashMap<String, Object> paramsEnd = new HashMap<String, Object>();
					paramsEnd.put("vIdMotivo"  , datosEnd.get("IdMotivo"));
					paramsEnd.put("vSucursal"  , datosEnd.get("Sucursal"));
					paramsEnd.put("vRamo"    , datosEnd.get("Ramo"));
					paramsEnd.put("vPoliza"   , datosEnd.get("Poliza"));
					paramsEnd.put("vTEndoso"    , StringUtils.isBlank(datosEnd.get("TEndoso"))?" " : datosEnd.get("TEndoso"));
					paramsEnd.put("vEndoso"  , datosEnd.get("Endoso"));
					paramsEnd.put("vInciso"     , datosEnd.get("Inciso"));
					paramsEnd.put("vSerie"    , datosEnd.get("Serie"));
					paramsEnd.put("vEndoB" , (endosoRecuperado==-1)?0:endosoRecuperado);
					paramsEnd.put("vFEndoso"   , datosEnd.get("FEndoso"));
					
					Integer res = autosDAOSIGS.endosoSerie(paramsEnd);
					
					logger.debug("Respuesta de Cambio Serie numero de endoso: " + res);
					
					if(res == null || res == 0 || res == -1){
						logger.debug("Endoso Cambio Serie no exitoso");
						return false;
					}else{
						endosoRecuperado = res.intValue();
					}
					
				} catch (Exception e){
					logger.error("Error en Envio Cambio Serie Auto: " + e.getMessage(),e);
				}
				
			}
			
			if(endosoRecuperado != -1){
				try{
					HashMap<String, String> params = new LinkedHashMap<String, String>();
					params.put("pv_cdunieco_i" , cdunieco);
					params.put("pv_cdramo_i" , cdramo);
					params.put("pv_estado_i" , estado);
					params.put("pv_nmpoliza_i" , nmpoliza);
					params.put("pv_nmsuplem_i" , nmsuplem);
					params.put("pv_numend_sigs_i", Integer.toString(endosoRecuperado));
					
					endososDAO.actualizaNumeroEndosSigs(params);
					
					this.generaCaratulasSigs(cdunieco, cdramo, estado, nmpoliza, nmsuplem, ntramite, cdtipsup, Integer.toString(endosoRecuperado));
					
				} catch (Exception e1) {
					logger.error("Error en llamar al PL Para actualizar endoso Serie de SIGS",e1);
					return false;
				}
			}else{
				logger.debug("Endoso Cambio Serie no exitoso, valor de endoso en -1");
				return false;
			}
			
		}else{
			logger.warn("Aviso, No se tienen datos de Cambio Serie");
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * 
	 * @param cdunieco
	 * @param cdramo
	 * @param estado
	 * @param nmpoliza
	 * @param nmsuplem
	 * @param nmtramite
	 * @param cdtipsup
	 * @param numEndoso
	 * @return
	 */
	private boolean generaCaratulasSigs(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsuplem, String nmtramite, String cdtipsup, String numEndoso){
		/**
		 * Para Caratula Endoso B
		 */
		try{
			PolizaAseguradoVO datosPol = new PolizaAseguradoVO();
			
			datosPol.setCdunieco(cdunieco);
			datosPol.setCdramo(cdramo);
			datosPol.setEstado(estado);
			datosPol.setNmpoliza(nmpoliza);
			
			List<PolizaDTO> listaPolizas = consultasPolizaDAO.obtieneDatosPoliza(datosPol);
			PolizaDTO polRes = listaPolizas.get(0);
			
			String parametros = null;
			parametros = "?"+polRes.getCduniext()+","+polRes.getCdramoext()+","+polRes.getNmpoliex()+","+ numEndoso;
			logger.debug("URL Generada para Caratula: "+ urlImpresionCaratulaEndosoB + parametros);
			mesaControlDAO.guardarDocumento(cdunieco, cdramo, estado, nmpoliza,
					nmsuplem, new Date(), urlImpresionCaratulaEndosoB + parametros, 
					"Car&aacute;tula Endoso B", nmpoliza,
					nmtramite, cdtipsup, Constantes.SI, null, TipoTramite.POLIZA_NUEVA.getCdtiptra());
		
		}catch(Exception e){
			logger.debug("Error al guardar la caratula de endoso B");
			return false;
		}
		return true;
	}

	@Override
	public void guardarEndosoAseguradoAlterno(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String ntramite
			,String cdelemen
			,String cdusuari
			,String cdtipsup
			,String status
			,String fechaEndoso
			,Date dFechaEndoso
			,Map<String, String> otvalores
		)throws Exception
	{
		logger.info(Utilerias.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ guardarEndosoBeneficiarios @@@@@@"
				,"\n@@@@@@ cdunieco="         , cdunieco
				,"\n@@@@@@ cdramo="           , cdramo
				,"\n@@@@@@ estado="           , estado
				,"\n@@@@@@ nmpoliza="         , nmpoliza
				,"\n@@@@@@ cdelemen="         , cdelemen
				,"\n@@@@@@ cdusuari="         , cdusuari
				,"\n@@@@@@ cdtipsup="         , cdtipsup
				,"\n@@@@@@ fechaEndoso="      , fechaEndoso
				,"\n@@@@@@ dFechaEndoso="     , dFechaEndoso
				,"\n@@@@@@ otvalores="        , otvalores
				));
		ManagerRespuestaVoidVO resp=new ManagerRespuestaVoidVO(true);
		String paso = "";
		try
		{
			paso ="Iniciando endoso";
			logger.info(paso);
			
			
			Map<String,String>iniciarEndosoResp=endososDAO.iniciarEndoso(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					,dFechaEndoso
					,cdelemen
					,cdusuari
					,"END"
					,cdtipsup);
			paso = "finaliza Inicio Endoso";
			logger.info(paso);
			
			String nmsuplem = iniciarEndosoResp.get("pv_nmsuplem_o");
			String nsuplogi = iniciarEndosoResp.get("pv_nsuplogi_o");
			
			paso ="Registra los valores en TVALOPOL";
			logger.info(paso);
			cotizacionDAO.movimientoTvalopol(cdunieco, cdramo, estado, nmpoliza, nmsuplem, status, otvalores);
			paso ="Se confirma el endoso";
			logger.info(paso);
			endososDAO.confirmarEndosoB(cdunieco,cdramo,estado,nmpoliza,nmsuplem, nsuplogi, cdtipsup, null);
			
			
			if(this.endosoAseguradoAlterno(cdunieco, cdramo, estado, nmpoliza, nmsuplem, ntramite, cdtipsup)){
				logger.info("Endoso de endosoAseguradoAlterno exitoso...");
			}else{
				logger.error("Error al ejecutar los WS de endoso de endosoAseguradoAlterno");
				
				boolean endosoRevertido = endososDAO.revierteEndosoFallido(cdunieco, cdramo, estado, nmpoliza, null, nmsuplem);
				if(endosoRevertido){
					logger.error("Endoso revertido exitosamente.");
					throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. Favor de volver a itentar.");
				}else{
					logger.error("Error al revertir el endoso");
					throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. No se ha revertido el endoso.");
				}
			}
		
			
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.info(Utilerias.join(
				 "\n@@@@@@ " , resp
				,"\n@@@@@@ guardarEndosoBeneficiarios @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	

	@Override
	public void guardarEndosoVigenciaPoliza(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String ntramite
			,String cdelemen
			,String cdusuari
			,String cdtipsup
			,String status
			,String fechaEndoso
			,Date dFechaEndoso
			,String feefecto
			,String feproren
		)throws Exception
	{
		logger.info(Utilerias.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ guardarEndosoBeneficiarios @@@@@@"
				,"\n@@@@@@ cdunieco="         , cdunieco
				,"\n@@@@@@ cdramo="           , cdramo
				,"\n@@@@@@ estado="           , estado
				,"\n@@@@@@ nmpoliza="         , nmpoliza
				,"\n@@@@@@ cdelemen="         , cdelemen
				,"\n@@@@@@ cdusuari="         , cdusuari
				,"\n@@@@@@ cdtipsup="         , cdtipsup
				,"\n@@@@@@ fechaEndoso="      , fechaEndoso
				,"\n@@@@@@ dFechaEndoso="     , dFechaEndoso
				,"\n@@@@@@ feefecto="         , feefecto
				,"\n@@@@@@ feproren="         , feproren
				));
		ManagerRespuestaVoidVO resp=new ManagerRespuestaVoidVO(true);
		String paso = "";
		try
		{
			paso = "Iniciando endoso";
			logger.info(paso);
			Map<String,String>iniciarEndosoResp=endososDAO.iniciarEndoso(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					,dFechaEndoso
					,cdelemen
					,cdusuari
					,"END"
					,cdtipsup);
			String nmsuplem = iniciarEndosoResp.get("pv_nmsuplem_o");
			String nsuplogi = iniciarEndosoResp.get("pv_nsuplogi_o");
			
			paso = "Registra los valores en TVALOPOL";
			logger.debug(paso);
			Map<String,String>params=new HashMap<String,String>();
			params.put("pv_cdunieco_i"  , cdunieco);
			params.put("pv_cdramo_i"    , cdramo);
			params.put("pv_estado_i"    , estado);
			params.put("pv_nmpoliza_i"  , nmpoliza);
			params.put("pv_nmsuplem_i"  , nmsuplem);
			params.put("pv_feefecto_i"  , feefecto);
			params.put("pv_feproren_i"  , feproren);
			logger.debug("EndososManager actualizaDeducibleValosit params: "+params);
			endososDAO.actualizaVigenciaPoliza(params);
			paso = "Se confirma el endoso";
			logger.debug(paso);
			endososDAO.confirmarEndosoB(cdunieco,cdramo,estado,nmpoliza,nmsuplem, nsuplogi, cdtipsup, null);
			
			if(this.endosoVigenciaPoliza(cdunieco, cdramo, estado, nmpoliza, nmsuplem, ntramite, cdtipsup)){
				logger.info("Endoso de Vigencia exitoso...");
			}else{
				logger.error("Error al ejecutar los WS de endoso de Vigencia");
				
				boolean endosoRevertido = endososDAO.revierteEndosoFallido(cdunieco, cdramo, estado, nmpoliza, null, nmsuplem);
				if(endosoRevertido){
					logger.error("Endoso revertido exitosamente.");
					throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. Favor de volver a itentar.");
				}else{
					logger.error("Error al revertir el endoso");
					throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. No se ha revertido el endoso.");
				}
			}
		
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.info(Utilerias.join(
				 "\n@@@@@@ " , resp
				,"\n@@@@@@ guardarEndosoBeneficiarios @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}

	@Override
	public void validarEndosoAnterior(String cdunieco, String cdramo,
			String estado, String nmpoliza, String cdtipsup) throws Exception {
			logger.info(Utilerias.join(
					 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
					,"\n@@@@@@ 	 validaEndosoAnterior  	  @@@@@@"
					,"\n@@@@@@ cdunieco="         , cdunieco
					,"\n@@@@@@ cdramo="           , cdramo
					,"\n@@@@@@ estado="           , estado
					,"\n@@@@@@ nmpoliza="         , nmpoliza
					,"\n@@@@@@ cdtipsup="         , cdtipsup
					));
			ManagerRespuestaVoidVO resp=new ManagerRespuestaVoidVO(true);
			String paso = "";
			try
			{
				paso = "Iniciando valida endoso anterior";
				logger.info(paso);
				endososDAO.validaEndosoAnterior(cdunieco, cdramo, estado, nmpoliza, cdtipsup);
			}
			catch(Exception ex)
			{
				Utils.generaExcepcion(ex, paso);
			}
			
			logger.info(Utilerias.join(
					 "\n@@@@@@ " , resp
					,"\n@@@@@@ 	  validaEndosoAnterior 	  @@@@@@"
					,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
					));
		}
	
	
	@Override
	public void validarEndosoPagados(String cdunieco, String cdramo,
			String estado, String nmpoliza) throws Exception {
			logger.info(Utilerias.join(
					 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
					,"\n@@@@@@ 	 validaEndosoAnterior  	  @@@@@@"
					,"\n@@@@@@ cdunieco="         , cdunieco
					,"\n@@@@@@ cdramo="           , cdramo
					,"\n@@@@@@ estado="           , estado
					,"\n@@@@@@ nmpoliza="         , nmpoliza
					));
			ManagerRespuestaVoidVO resp=new ManagerRespuestaVoidVO(true);
			String paso = "";
			try
			{
				paso = "Iniciando valida endoso anterior";
				logger.info(paso);
				endososDAO.validaEndosoPagados(cdunieco, cdramo, estado, nmpoliza);
			}
			catch(Exception ex)
			{
				Utils.generaExcepcion(ex, paso);
			}
			
			logger.info(Utilerias.join(
					 "\n@@@@@@ " , resp
					,"\n@@@@@@ 	  validaEndosoAnterior 	  @@@@@@"
					,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
					));
		}
	@Override
	public Map<String,Item>endosoClaveAuto(
			String cdsisrol
			,String cdramo
			,String cdtipsit
			)throws Exception
	{
		logger.info(Utilerias.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ endosoClaveAuto @@@@@@"
				,"\n@@@@@@ cdsisrol=" , cdsisrol
				,"\n@@@@@@ cdramo="   , cdramo
				,"\n@@@@@@ cdtipsit=" , cdtipsit
				));
		
		Map<String,Item> items = new HashMap<String,Item>();
		String           paso  = null;
		
		try
		{
			paso = "Obteniendo componentes de situacion";
			logger.info(paso);
			List<ComponenteVO> listaItems = pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,cdramo
					,cdtipsit
					,null //estado
					,cdsisrol
					,"ENDOSO_CLAVE_AUTO"
					,"ITEMS"
					,null //orden
					);
			
			paso = "Construyendo componentes de situacion";
			logger.info(paso);
			GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gc.setCdramo(cdramo);
			gc.setCdtipsit(cdtipsit);
			gc.generaComponentes(listaItems, true, false, true, false, false, false);
			
			items.put("items" , gc.getItems());
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.info(Utilerias.join(
				 "\n@@@@@@ endosoClaveAuto @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return items;
	}
	
	@Override
	public void guardarEndosoClaveAuto(
			String cdtipsup
			,String cdusuari
			,String cdsisrol
			,String cdelemen
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String feefecto
			,Map<String,String> valores
			,Map<String,String> incisoAnterior
			)throws Exception
	{
		logger.info(Utilerias.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ guardarEndosoClaveAuto @@@@@@"
				,"\n@@@@@@ cdtipsup="       , cdtipsup
				,"\n@@@@@@ cdusuari="       , cdusuari
				,"\n@@@@@@ cdsisrol="       , cdsisrol
				,"\n@@@@@@ cdelemen="       , cdelemen
				,"\n@@@@@@ cdunieco="       , cdunieco
				,"\n@@@@@@ cdramo="         , cdramo
				,"\n@@@@@@ estado="         , estado
				,"\n@@@@@@ nmpoliza="       , nmpoliza
				,"\n@@@@@@ feefecto="       , feefecto
				,"\n@@@@@@ valores="        , valores
				,"\n@@@@@@ incisoAnterior=" , incisoAnterior
				));
		
		String paso = null;
		try
		{
			paso = "Generando id operacion";
			logger.info(paso);
			
			double millis = System.currentTimeMillis();
			double random = 1000d*Math.random();
			String tstamp = String.format("%.0f.%.0f",millis,random);
			logger.debug(Utilerias.join("stamp=",tstamp));
			
			paso = "Guardando situacion temporal";
			logger.info(paso);
			endososDAO.guardarTvalositEndoso(cdunieco
					,cdramo
					,estado
					,nmpoliza
					,incisoAnterior.get("NMSITUAC")
					,"0"
					,"V" //status
					,incisoAnterior.get("CDTIPSIT")
					,incisoAnterior.get("OTVALOR01"),incisoAnterior.get("OTVALOR02"),incisoAnterior.get("OTVALOR03")
					,incisoAnterior.get("OTVALOR04"),incisoAnterior.get("OTVALOR05"),incisoAnterior.get("OTVALOR06")
					,incisoAnterior.get("OTVALOR07"),incisoAnterior.get("OTVALOR08"),incisoAnterior.get("OTVALOR09")
					,incisoAnterior.get("OTVALOR10")
					,incisoAnterior.get("OTVALOR11"),incisoAnterior.get("OTVALOR12"),incisoAnterior.get("OTVALOR13")
					,incisoAnterior.get("OTVALOR14"),incisoAnterior.get("OTVALOR15"),incisoAnterior.get("OTVALOR16")
					,incisoAnterior.get("OTVALOR17"),incisoAnterior.get("OTVALOR18"),incisoAnterior.get("OTVALOR19")
					,incisoAnterior.get("OTVALOR20")
					,incisoAnterior.get("OTVALOR21"),incisoAnterior.get("OTVALOR22"),incisoAnterior.get("OTVALOR23")
					,incisoAnterior.get("OTVALOR24"),incisoAnterior.get("OTVALOR25"),incisoAnterior.get("OTVALOR26")
					,incisoAnterior.get("OTVALOR27"),incisoAnterior.get("OTVALOR28"),incisoAnterior.get("OTVALOR29")
					,incisoAnterior.get("OTVALOR30")
					,incisoAnterior.get("OTVALOR31"),incisoAnterior.get("OTVALOR32"),incisoAnterior.get("OTVALOR33")
					,incisoAnterior.get("OTVALOR34"),incisoAnterior.get("OTVALOR35"),incisoAnterior.get("OTVALOR36")
					,incisoAnterior.get("OTVALOR37"),incisoAnterior.get("OTVALOR38"),incisoAnterior.get("OTVALOR39")
					,incisoAnterior.get("OTVALOR40")
					,incisoAnterior.get("OTVALOR41"),incisoAnterior.get("OTVALOR42"),incisoAnterior.get("OTVALOR43")
					,incisoAnterior.get("OTVALOR44"),incisoAnterior.get("OTVALOR45"),incisoAnterior.get("OTVALOR46")
					,incisoAnterior.get("OTVALOR47"),incisoAnterior.get("OTVALOR48"),incisoAnterior.get("OTVALOR49")
					,incisoAnterior.get("OTVALOR50")
					,incisoAnterior.get("OTVALOR51"),incisoAnterior.get("OTVALOR52"),incisoAnterior.get("OTVALOR53")
					,incisoAnterior.get("OTVALOR54"),incisoAnterior.get("OTVALOR55"),incisoAnterior.get("OTVALOR56")
					,incisoAnterior.get("OTVALOR57"),incisoAnterior.get("OTVALOR58"),incisoAnterior.get("OTVALOR59")
					,incisoAnterior.get("OTVALOR60")
					,incisoAnterior.get("OTVALOR61"),incisoAnterior.get("OTVALOR62"),incisoAnterior.get("OTVALOR63")
					,incisoAnterior.get("OTVALOR64"),incisoAnterior.get("OTVALOR65"),incisoAnterior.get("OTVALOR66")
					,incisoAnterior.get("OTVALOR67"),incisoAnterior.get("OTVALOR68"),incisoAnterior.get("OTVALOR69")
					,incisoAnterior.get("OTVALOR70")
					,incisoAnterior.get("OTVALOR71"),incisoAnterior.get("OTVALOR72"),incisoAnterior.get("OTVALOR73")
					,incisoAnterior.get("OTVALOR74"),incisoAnterior.get("OTVALOR75"),incisoAnterior.get("OTVALOR76")
					,incisoAnterior.get("OTVALOR77"),incisoAnterior.get("OTVALOR78"),incisoAnterior.get("OTVALOR79")
					,incisoAnterior.get("OTVALOR80")
					,incisoAnterior.get("OTVALOR81"),incisoAnterior.get("OTVALOR82"),incisoAnterior.get("OTVALOR83")
					,incisoAnterior.get("OTVALOR84"),incisoAnterior.get("OTVALOR85"),incisoAnterior.get("OTVALOR86")
					,incisoAnterior.get("OTVALOR87"),incisoAnterior.get("OTVALOR88"),incisoAnterior.get("OTVALOR89")
					,incisoAnterior.get("OTVALOR90")
					,valores.get("OTVALOR91"),valores.get("OTVALOR92"),valores.get("OTVALOR93")
					,valores.get("OTVALOR94"),valores.get("OTVALOR95"),valores.get("OTVALOR96")
					,valores.get("OTVALOR97"),valores.get("OTVALOR98"),valores.get("OTVALOR99")
					,tstamp);
			
			paso = "Procesando fecha de efecto";
			Date feefectoD = renderFechas.parse(feefecto);
			
			paso = "Confirmando endoso";
			logger.info(paso);
			endososDAO.guardarEndosoClaveAuto(cdunieco, cdramo, estado, nmpoliza, feefectoD, tstamp, cdusuari, cdelemen, cdtipsup);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.info(Utilerias.join(
				 "\n@@@@@@ guardarEndosoClaveAuto @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	
	@Override
	public List<Map<String,String>> obtenerRetroactividad(String cdsisrol, String cdramo,
			String cdtipsup, String fechaProceso) throws Exception {
		// TODO Auto-generated method stub
		List<Map<String, String>> respRetroActividad = null;
		logger.info(Utilerias.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ 	 validaEndosoAnterior  	  @@@@@@"
				,"\n@@@@@@ cdsisrol="         , cdsisrol
				,"\n@@@@@@ cdramo="           , cdramo
				,"\n@@@@@@ cdtipsup="           , cdtipsup
				,"\n@@@@@@ fechaProceso="         , fechaProceso
				));
		ManagerRespuestaVoidVO resp=new ManagerRespuestaVoidVO(true);
		String paso = "";
		try
		{
			paso = "Iniciando valida endoso anterior";
			logger.info(paso);
			respRetroActividad = endososDAO.obtenerRetroactividad(cdsisrol, cdramo, cdtipsup, fechaProceso);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.info(Utilerias.join(
				 "\n@@@@@@ " , resp
				,"\n@@@@@@ 	  validaEndosoAnterior 	  @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		
		return respRetroActividad;
	}
	
	@Override
	public Map<String,Item> endosoDevolucionPrimas(
			String cdtipsup
			,String cdramo
			)throws Exception
	{
		logger.info(Utilerias.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ endosoDevolucionPrimas @@@@@@"
				,"\n@@@@@@ cdtipsup=" , cdtipsup
				,"\n@@@@@@ cdramo="   , cdramo
				));
		
		Map<String,Item> items = new HashMap<String,Item>();
		String           paso  = null;
		
		try
		{
			paso = "Recuperando columnas de inciso";
			logger.info(paso);
			List<ComponenteVO> columnasInciso = pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,cdramo
					,null //cdtipsit
					,null //estado
					,null //cdsisrol
					,"ENDOSO_DEVOLUCION_PRIMAS"
					,"COLUMNAS_INCISO"
					,null //orden
					);
			
			paso = "Recuperando columnas de cobertura";
			logger.info(paso);
			List<ComponenteVO> columnasCobertura = pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,cdramo
					,null //cdtipsit
					,null //estado
					,null //cdsisrol
					,"ENDOSO_DEVOLUCION_PRIMAS"
					,"COLUMNAS_COBERTURA"
					,null //orden
					);
			
			paso = "Construyendo componentes";
			logger.info(paso);
			GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gc.generaComponentes(columnasInciso, true, false, false, true, false, false);
			items.put("incisoColumns" , gc.getColumns());
			
			gc.generaComponentes(columnasCobertura, true, false, false, true, true, false);
			items.put("coberturaColumns" , gc.getColumns());
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.info(Utilerias.join(
				 "\n@@@@@@ endosoDevolucionPrimas @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return items;
	}
	
	private boolean ejecutaCaratulaEndosoTarifaSigs(String cdunieco,String cdramo,String estado,String nmpoliza,String nmsuplem, String ntramite, String cdtipsup, String tipoGrupoInciso, EmisionAutosVO emisionWS){
		/**
		 * Para Caratula
		 */
		
		try{
			
			/**
			 * Para Guardar URls de Caratula Recibos y documentos de Autos Externas
			 */
			
			String urlCaratula = null;
			if(Ramo.AUTOS_FRONTERIZOS.getCdramo().equalsIgnoreCase(cdramo) 
		    		|| Ramo.AUTOS_RESIDENTES.getCdramo().equalsIgnoreCase(cdramo)
		    	){
				urlCaratula = this.urlImpresionCaratula;
			}else if(Ramo.SERVICIO_PUBLICO.getCdramo().equalsIgnoreCase(cdramo)){
				urlCaratula = this.urlImpresionCaratulaServicioPublico;
			}
			
			if(StringUtils.isNotBlank(tipoGrupoInciso)  && ("F".equalsIgnoreCase(tipoGrupoInciso) || "P".equalsIgnoreCase(tipoGrupoInciso))){
				urlCaratula = this.urlImpresionCaratulaServicioFotillas;
			}
			
			String urlRecibo = this.urlImpresionRecibos;
			String urlCaic = this.urlImpresionCaic;
			String urlAp = this.urlImpresionAp;
			
			String urlIncisosFlot = this.urlImpresionIncisosFlotillas;
			String urlTarjIdent = this.urlImpresionTarjetaIdentificacion;
			
			String parametros = null;
			
			parametros = "?"+emisionWS.getSucursal()+","+emisionWS.getSubramo()+","+emisionWS.getNmpoliex()+","+emisionWS.getTipoEndoso()+","+ (StringUtils.isBlank(emisionWS.getNumeroEndoso())?"0":emisionWS.getNumeroEndoso());
			logger.debug("URL Generada para Caratula: "+ urlCaratula + parametros);
			mesaControlDAO.guardarDocumento(cdunieco, cdramo, estado,nmpoliza, nmsuplem, 
					new Date(), urlCaratula + parametros,
					"Car&aacute;tula de P&oacute;liza", nmpoliza, ntramite,
					cdtipsup, Constantes.SI, null, TipoTramite.POLIZA_NUEVA.getCdtiptra());
			
			/**
			 * Para Recibo 1
			 */
			parametros = "?9999,0,"+emisionWS.getSucursal()+","+emisionWS.getSubramo()+","+emisionWS.getNmpoliex()+",0,"+(StringUtils.isBlank(emisionWS.getNumeroEndoso())?"0":emisionWS.getNumeroEndoso())+","+emisionWS.getTipoEndoso()+",1";
			logger.debug("URL Generada para Recibo 1: "+ urlRecibo + parametros);
			mesaControlDAO.guardarDocumento(
					cdunieco, cdramo, estado, nmpoliza, nmsuplem, 
					new Date(), urlRecibo + parametros, "Recibo 1", nmpoliza, 
					ntramite, cdtipsup, Constantes.SI, null, TipoTramite.POLIZA_NUEVA.getCdtiptra());
			
			/**
			 * Para AP inciso 1
			 */
			parametros = "?14,0,"+emisionWS.getSucursal()+","+emisionWS.getSubramo()+","+emisionWS.getNmpoliex()+",1";
			logger.debug("URL Generada para AP Inciso 1: "+ urlAp + parametros);
			mesaControlDAO.guardarDocumento(
					cdunieco, cdramo, estado, nmpoliza, nmsuplem, 
					new Date(), urlAp + parametros, "AP", nmpoliza, 
					ntramite, cdtipsup, Constantes.SI, null, TipoTramite.POLIZA_NUEVA.getCdtiptra());
			
			/**
			 * Para CAIC inciso 1
			 */
			parametros = "?"+emisionWS.getSucursal()+","+emisionWS.getSubramo()+","+emisionWS.getNmpoliex()+","+emisionWS.getTipoEndoso()+","+ (StringUtils.isBlank(emisionWS.getNumeroEndoso())?"0":emisionWS.getNumeroEndoso())+",1";
			logger.debug("URL Generada para CAIC Inciso 1: "+ urlCaic + parametros);
			mesaControlDAO.guardarDocumento(
					cdunieco, cdramo, estado, nmpoliza, nmsuplem, 
					new Date(), urlCaic + parametros, "CAIC", nmpoliza, 
					ntramite, cdtipsup, Constantes.SI, null, TipoTramite.POLIZA_NUEVA.getCdtiptra());
			
			if(StringUtils.isNotBlank(tipoGrupoInciso)  && ("F".equalsIgnoreCase(tipoGrupoInciso) || "P".equalsIgnoreCase(tipoGrupoInciso))){
				/**
				 * Para Incisos Flotillas
				 */
				parametros = "?"+emisionWS.getSucursal()+","+emisionWS.getSubramo()+","+emisionWS.getNmpoliex()+","+emisionWS.getTipoEndoso()+","+ (StringUtils.isBlank(emisionWS.getNumeroEndoso())?"0":emisionWS.getNumeroEndoso());
				logger.debug("URL Generada para urlIncisosFlotillas: "+ urlIncisosFlot + parametros);
				mesaControlDAO.guardarDocumento(
						cdunieco, cdramo, estado, nmpoliza, nmsuplem, 
						new Date(), urlIncisosFlot + parametros, "Incisos Flotillas", nmpoliza, 
						ntramite, cdtipsup, Constantes.SI, null, TipoTramite.POLIZA_NUEVA.getCdtiptra());
				
				/**
				 * Para Tarjeta Identificacion
				 */
				parametros = "?"+emisionWS.getSucursal()+","+emisionWS.getSubramo()+","+emisionWS.getNmpoliex()+","+emisionWS.getTipoEndoso()+","+ (StringUtils.isBlank(emisionWS.getNumeroEndoso())?"0":emisionWS.getNumeroEndoso())+",0";
				logger.debug("URL Generada para Tarjeta Identificacion: "+ urlTarjIdent + parametros);
				mesaControlDAO.guardarDocumento(
						cdunieco, cdramo, estado, nmpoliza, nmsuplem, 
						new Date(), urlTarjIdent + parametros, "Tarjeta de Identificacion", nmpoliza, 
						ntramite, cdtipsup, Constantes.SI, null, TipoTramite.POLIZA_NUEVA.getCdtiptra());
			}
		}catch(Exception ex){
			logger.error("error al guardar el endoso de coberturas", ex);
			return false;
		}
		return true;
	}
	
	@Override
	public Map<String,Item> endosoRehabilitacionAuto(
			String cdsisrol
			,String cdramo
			)throws Exception
	{
		logger.info(Utilerias.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ endosoRehabilitacionAuto @@@@@@"
				,"\n@@@@@@ cdsisrol=" , cdsisrol
				,"\n@@@@@@ cdramo="   , cdramo
				));
		
		Map<String,Item> items = new HashMap<String,Item>();
		String           paso  = null;
		try
		{
			paso = "Recuperando elementos formulario";
			logger.info(paso);
			
			List<ComponenteVO>form = pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,cdramo
					,null //cdtipsit
					,null //estado
					,cdsisrol
					,"ENDOSO_REHABILITACION_AUTO"
					,"FORMULARIO"
					,null
					);
			
			paso = "Construyendo componentes del formulario";
			logger.info(paso);
			
			GeneradorCampos gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gc.generaComponentes(form, true, false, true, false, false, false);
			
			items.put("itemsFormulario" , gc.getItems());
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.info(Utilerias.join(
				 "\n@@@@@@ items=",items
				,"\n@@@@@@ endosoRehabilitacionAuto @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		
		return items;
	}
	
	@Override
	public void confirmarEndosoRehabilitacionAuto(
			String cdusuari
			,String cdsisrol
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,Date   feefecto
			)throws Exception
	{
		logger.info(Utilerias.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ confirmarEndosoRehabilitacionAuto @@@@@@"
				,"\n@@@@@@ cdusuari=" , cdusuari
				,"\n@@@@@@ cdsisrol=" , cdsisrol
				,"\n@@@@@@ cdunieco=" , cdunieco
				,"\n@@@@@@ cdramo="   , cdramo
				,"\n@@@@@@ estado="   , estado
				,"\n@@@@@@ nmpoliza=" , nmpoliza
				,"\n@@@@@@ date="     , feefecto
				));
		
		String paso = null;
		try
		{
			paso = "Confirmando endoso";
			endososDAO.confirmarEndosoRehabilitacionAuto(
					cdusuari
					,cdsisrol
					,cdunieco
					,cdramo
					,estado
					,nmpoliza
					,feefecto
					);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.info(Utilerias.join(
				 "\n@@@@@@ confirmarEndosoRehabilitacionAuto @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
}