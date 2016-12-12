package mx.com.gseguros.portal.endosos.service.impl;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.mesacontrol.model.FlujoVO;
import mx.com.gseguros.portal.cancelacion.dao.CancelacionDAO;
import mx.com.gseguros.portal.catalogos.dao.PersonasDAO;
import mx.com.gseguros.portal.consultas.dao.ConsultasDAO;
import mx.com.gseguros.portal.consultas.dao.ConsultasPolizaDAO;
import mx.com.gseguros.portal.consultas.model.PolizaAseguradoVO;
import mx.com.gseguros.portal.consultas.model.PolizaDTO;
import mx.com.gseguros.portal.cotizacion.dao.CotizacionDAO;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaVoidVO;
import mx.com.gseguros.portal.cotizacion.model.ParametroEndoso;
import mx.com.gseguros.portal.cotizacion.model.SlistSmapVO;
import mx.com.gseguros.portal.documentos.model.Documento;
import mx.com.gseguros.portal.endosos.dao.EndososDAO;
import mx.com.gseguros.portal.endosos.model.PropiedadesDeEndosoParaWS;
import mx.com.gseguros.portal.endosos.service.EndososAutoManager;
import mx.com.gseguros.portal.endosos.service.EndososManager;
import mx.com.gseguros.portal.general.dao.PantallasDAO;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.model.ThreadCounter;
import mx.com.gseguros.portal.general.util.EstatusTramite;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.portal.general.util.Ramo;
import mx.com.gseguros.portal.general.util.RolSistema;
import mx.com.gseguros.portal.general.util.TipoEndoso;
import mx.com.gseguros.portal.general.util.TipoFlotilla;
import mx.com.gseguros.portal.general.util.TipoSituacion;
import mx.com.gseguros.portal.general.util.TipoTramite;
import mx.com.gseguros.portal.mesacontrol.dao.MesaControlDAO;
import mx.com.gseguros.portal.mesacontrol.service.MesaControlManager;
import mx.com.gseguros.portal.rehabilitacion.dao.RehabilitacionDAO;
import mx.com.gseguros.utils.Constantes;
import mx.com.gseguros.utils.HttpUtil;
import mx.com.gseguros.utils.Utils;
import mx.com.gseguros.ws.autosgs.dao.AutosSIGSDAO;
import mx.com.gseguros.ws.autosgs.emision.model.EmisionAutosVO;
import mx.com.gseguros.ws.autosgs.service.EmisionAutosService;
import mx.com.gseguros.ws.ice2sigs.service.Ice2sigsService;

@Service
public class EndososAutoManagerImpl implements EndososAutoManager
{
    private Map<String, String> iniciarEndosoResp = null;
    private Map<String, Object> resParams = null;
	private static Logger           logger       = Logger.getLogger(EndososAutoManagerImpl.class);
	private static SimpleDateFormat renderFechas = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat renderFechaHora = new SimpleDateFormat("ddMMyyyyHHss");
	
	@Autowired
	private PantallasDAO pantallasDAO;
	
	@Autowired
	private EndososManager endososManager;
	
	@Autowired
	private EndososDAO endososDAO;
	
	@Autowired
	private ConsultasDAO consultasDAO;
	
	@Autowired
	@Qualifier("consultasDAOICEImpl")
	private ConsultasPolizaDAO consultasPolizaDAO;
	
	@Autowired
	private CotizacionDAO cotizacionDAO;
	
	@Autowired
	private PersonasDAO personasDAO;
	
	@Autowired
	private MesaControlDAO mesaControlDAO;
	
	@Autowired
	private AutosSIGSDAO autosDAOSIGS;
	
	@Autowired
	private CancelacionDAO cancelacionDAO;
	
	@Autowired
	private RehabilitacionDAO rehabilitacionDAO;
	
	@Autowired
	private MesaControlManager mesaControlManager;
	
	@Autowired
	@Qualifier("emisionAutosServiceImpl")
	private EmisionAutosService emisionAutosService;

	@Autowired
	private transient Ice2sigsService ice2sigsService;
	
	
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

	@Value("${aeua.impresion.autos.url}")
	private String urlImpresionAeua;
	
	@Value("${ap.impresion.autos.url}")
	private String urlImpresionAp;
	
	@Value("${incisos.flotillas.impresion.autos.url}")
	private String urlImpresionIncisosFlotillas;
	
	@Value("${tarjeta.iden.impresion.autos.url}")
	private String urlImpresionTarjetaIdentificacion;

	@Value("${numero.incisos.reporte}")
	private String numIncisosReporte;
	
	@Value("${manual.agente.txtinfocobredgs}")
	private String urlImpresionCobReduceGS;
	
	@Value("${manual.agente.txtinfocobgesgs}")
	private String urlImpresionCobGestoriaGS;
	
	@Value("${manual.agente.condgralescobsegvida}")
	private String urlImpresionCondicionesSegVida;

	@Value("${rdf.emision.nombre.esp.cobvida}")
	private String rdfEspecSeguroVida;
	
	@Value("${rdf.endosos.nombre.auto.pymes}")
	private String rdfEndosoPreview;
	
	@Value("${rdf.endosos.nombre.auto.individual}")
	private String rdfEndosoPreviewIndi;

	//Datos para el servidor de Reportes
	@Value("${ruta.servidor.reports}")
	private String rutaServidorReportes;

	@Value("${pass.servidor.reports}")
	private String passServidorReportes;

	@Value("${ruta.documentos.poliza}")
	private String rutaDocumentosPoliza;
	
	@Value("${ruta.documentos.temporal}")
	private String rutaTempEndoso;
	
	
	@Override
	public Map<String,Object> construirMarcoEndosos(String cdusuari,String cdsisrol) throws Exception
	{
		logger.debug(Utils.log(
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
			logger.debug(paso);
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
			logger.debug(paso);
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
			logger.debug(paso);
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
			logger.debug(paso);
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
			logger.debug(paso);
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
			logger.debug(paso);
			GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gc.generaComponentes(componentesFiltro, true, false, true, false, false, false);
			items.put("formBusqItems" , gc.getItems());
			
			paso="Construyendo componentes del grid de polizas";
			logger.debug(paso);
			gc.generaComponentes(componentesGridPolizas, true, false, false, true, true, false);
			items.put("gridPolizasColumns" , gc.getColumns());
			
			paso="Construyendo componentes del grid de historico de poliza";
			logger.debug(paso);
			gc.generaComponentes(componentesGridHistoricoPoliza, true, false, false, true, false, false);
			items.put("gridHistoricoColumns" , gc.getColumns());
			
			paso="Construyendo componentes del grid de grupos";
			logger.debug(paso);
			gc.generaComponentes(componentesGridGrupos, true, false, false, true, false, false);
			items.put("gridGruposColumns" , gc.getColumns());
			
			paso="Construyendo componentes del grid de familias";
			logger.debug(paso);
			gc.generaComponentes(componentesGridFamilias, true, false, false, true, false, false);
			items.put("gridFamiliasColumns" , gc.getColumns());
			
			valores.put("items" , items);
			
			String cdagente="";
			if(cdsisrol.equals(RolSistema.AGENTE.getCdsisrol()))
			{
				paso = "Recuperando clave de agente";
				logger.debug(paso);
				cdagente = mesaControlDAO.cargarCdagentePorCdusuari(cdusuari);
			}
			valores.put("cdagente" , cdagente);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ valores=",valores
				,"\n@@@@@@ construirMarcoEndosos @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return valores;
	}
	
	@Override
	public String recuperarColumnasIncisoRamo(String cdramo)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ recuperarColumnasIncisoRamo @@@@@@"
				,"\n@@@@@@ cdramo=",cdramo
				));
		
		String cols = null;
		String paso = "";
		
		try
		{
			paso="Recuperando columnas de incisos para el producto";
			logger.debug(paso);
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
			logger.debug(paso);
			GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gc.generaComponentes(columnas, true, false, false, true, true, false);
			cols=gc.getColumns().toString();
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex,paso);
		}
		
		logger.debug(Utils.log(
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
			,String cdusuari
			,String cdtipsit
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ recuperarEndososClasificados @@@@@@"
				,"\n@@@@@@ cdramo="    , cdramo
				,"\n@@@@@@ nivel="     , nivel
				,"\n@@@@@@ multiple="  , multiple
				,"\n@@@@@@ tipoflot="  , tipoflot
				,"\n@@@@@@ incisos="   , incisos
				,"\n@@@@@@ cdsisrol="  , cdsisrol
				,"\n@@@@@@ cancelada=" , cancelada
				,"\n@@@@@@ cdusuari=" , cdusuari
				,"\n@@@@@@ cdtipsit=" , cdtipsit
				));

		SlistSmapVO resp = new SlistSmapVO();
		String      paso = null;
		
		try
		{
			String stamp  = Utils.generaTimestamp();
			
			logger.debug(Utils.log("stamp=",stamp));
			
			resp.getSmap().put("stamp" , stamp);
			
			if(incisos.size()>0)
			{
				paso="Insertando situaciones para evaluacion";
				logger.debug(paso);
				
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
			logger.debug(paso);
			
			if("N".equals(cancelada))
			{
				resp.setSlist(endososDAO.recuperarEndososClasificados(stamp,cdramo,nivel,multiple,tipoflot,cdsisrol, cdusuari,cdtipsit));
				//parchamos, si el DSTIPSUP2 tiene algun *, lo metemos como DSTIPSUP
				for(Map<String,String>endoso:resp.getSlist())
				{
					logger.debug(Utils.log("\nDSTIPSUP2=",endoso.get("DSTIPSUP2")));
					if(StringUtils.isNotBlank(endoso.get("DSTIPSUP2"))&&endoso.get("DSTIPSUP2").indexOf("*")!=-1)
					{
						endoso.put("DSTIPSUP",endoso.get("DSTIPSUP2"));
						logger.debug("\nreplace");
					}
				}
			}
			else if("POLIZA".equals(nivel))
			{
				List<Map<String,String>> lista = new ArrayList<Map<String,String>>();
				Map<String,String>       mapa  = new HashMap<String,String>();
				mapa.put("CDTIPSUP"        , "57");
				mapa.put("DSTIPSUP"        , "REHABILITACI\u00f3N DE P\u00f3LIZA");
				mapa.put("LIGA"            , "/endosos/includes/endosoRehabilitacionPolAuto.action");
				mapa.put("TIPO_VALIDACION" , "");
				lista.add(mapa);
				resp.setSlist(lista);
			}
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ ",resp
				,"\n@@@@@@ recuperarEndososClasificados @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return resp;
	}
	
	@Override
	public Map<String,Item>pantallaEndosoValosit(String cdtipsup, String cdramo, String cdsisrol) throws Exception 
	{
		logger.debug(Utils.log(
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
			logger.debug(paso);
			
			List<ComponenteVO>columnasLectura=pantallasDAO.obtenerComponentes(
					cdtipsup //cdtiptra
					,null    //cdunieco
					,cdramo
					,null    //cdtipsit
					,null    //estado
					,cdsisrol//cdsisrol
					,"ENDOSO_VALOSIT_AUTO"
					,"COLUMNAS_LECTURA"
					,null    //orden
					);
			
			paso="Recuperando columnas editables";
			logger.debug(paso);
			
			List<ComponenteVO>columnasEditables=pantallasDAO.obtenerComponentes(
					cdtipsup //cdtiptra
					,null    //cdunieco
					,cdramo
					,null    //cdtipsit
					,null    //estado
					,cdsisrol//cdsisrol
					,"ENDOSO_VALOSIT_AUTO"
					,"COLUMNAS_EDITABLES"
					,null    //orden
					);
			
			paso="Construyendo columnas de lectura";
			logger.debug(paso);
			
			GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gc.generaComponentes(columnasLectura, true, false, false, true, false, false);
			
			items.put("gridColumnsLectura"   , gc.getColumns());
			
			paso="Construyendo columnas editables";
			logger.debug(paso);
			
			gc.generaComponentes(columnasEditables, true, false, false, true, true, false);
			
			items.put("gridColumnsEditables" , gc.getColumns());
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
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
		logger.debug(Utils.log(
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
		

		logger.debug(Utils.log(
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
		logger.debug(Utils.log(
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
		
		String paso = "Guardando datos temporales";
		logger.debug(paso);
		
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
			logger.debug(paso);
			
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
			
			String nmsuplem        = (String)resParams.get("pv_nmsuplem_o");
			String ntramite        = (String)resParams.get("pv_ntramite_o");
			String tipoGrupoInciso = (String)resParams.get("pv_tipoflot_o");
			String nsuplogi        = (String)resParams.get("pv_nsuplogi_o");
			
			//para tramite
			Map<String,String> datosPoliza = consultasDAO.recuperarDatosPolizaParaDocumentos(cdunieco, cdramo, estado, nmpoliza);
			String ntramiteEmi = datosPoliza.get("ntramite");
			
			Map<String,String> valores = new HashMap<String,String>();
			valores.put("otvalor01" , ntramiteEmi);
			valores.put("otvalor02" , cdtipsup);
			valores.put("otvalor03" , consultasDAO.recuperarDstipsupPorCdtipsup(cdtipsup));
			valores.put("otvalor04" , nsuplogi);
			valores.put("otvalor05" , cdusuari);
			
			mesaControlDAO.movimientoMesaControl(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					,nmsuplem
					,cdunieco
					,cdunieco
					,TipoTramite.ENDOSO.getCdtiptra()
					,fechaEndoso
					,null //cdagente
					,null //referencia
					,null //nombre
					,fechaEndoso
					,EstatusTramite.ENDOSO_CONFIRMADO.getCodigo()
					,null //comments
					,null //nmsolici
					,null //cdtipsit
					,cdusuari
					,cdsisrol
					,null
					,null
					,null
					,valores, null
					);
			//para tramite
			
			/**
			 * PARA LLAMAR WS SEGUN TIPO DE ENDOSO
			 */
			if(TipoEndoso.BENEFICIARIO_AUTO.getCdTipSup().toString().equalsIgnoreCase(cdtipsup)){
				if(this.endosoBeneficiario(cdunieco, cdramo, estado, nmpoliza, nmsuplem, ntramite, cdtipsup)){
					logger.info("Endoso de Beneficiario exitoso...");
				}else{
					logger.error("Error al ejecutar los WS de endoso de Beneficiario");
					
					boolean endosoRevertido = endososManager.revierteEndosoFallido(cdunieco, cdramo, estado, nmpoliza, null, nmsuplem, 88888, "Error en endoso B tipo: "+TipoEndoso.findByKey(Integer.valueOf(cdtipsup)), true);
					if(endosoRevertido){
						logger.error("Endoso revertido exitosamente.");
						throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. Favor de volver a intentar.");
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
					boolean endosoRevertido = endososManager.revierteEndosoFallido(cdunieco, cdramo, estado, nmpoliza, null, nmsuplem, 88888, "Error en endoso B tipo: "+TipoEndoso.findByKey(Integer.valueOf(cdtipsup)), true);
					if(endosoRevertido){
						logger.error("Endoso revertido exitosamente.");
						throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. Favor de volver a intentar.");
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
					boolean endosoRevertido = endososManager.revierteEndosoFallido(cdunieco, cdramo, estado, nmpoliza, null, nmsuplem, 88888, "Error en endoso B tipo: "+TipoEndoso.findByKey(Integer.valueOf(cdtipsup)), true);
					if(endosoRevertido){
						logger.error("Endoso revertido exitosamente.");
						throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. Favor de volver a intentar.");
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
					
					boolean endosoRevertido = endososManager.revierteEndosoFallido(cdunieco, cdramo, estado, nmpoliza, null, nmsuplem, 88888, "Error en endoso B tipo: "+TipoEndoso.findByKey(Integer.valueOf(cdtipsup)), true);
					if(endosoRevertido){
						logger.error("Endoso revertido exitosamente.");
						throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. Favor de volver a intentar.");
					}else{
						logger.error("Error al revertir el endoso");
						throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. No se ha revertido el endoso.");
					}
				}
			}else{

				EmisionAutosVO aux = emisionAutosService.cotizaEmiteAutomovilWS(cdunieco, cdramo, estado, nmpoliza, nmsuplem, ntramite, null, usuarioSesion);
				if(aux == null || !aux.isExitoRecibos()){
					
					logger.error("Error al ejecutar los WS de endoso");
					boolean endosoRevertido = endososManager.revierteEndosoFallido(cdunieco, cdramo, estado, nmpoliza, null, nmsuplem, (aux == null)? Integer.valueOf(99999) : aux.getResRecibos(), "Error en endoso auto, tipo: "+TipoEndoso.findByKey(Integer.valueOf(cdtipsup)), false);
							
					if(endosoRevertido){
						logger.error("Endoso revertido exitosamente.");
						throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. Favor de volver a intentar.");
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
		
		logger.debug(Utils.log(
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
		logger.debug(Utils.log(
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
			logger.debug(paso);
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
					logger.debug(Utils.log("Es XPOLX",incisoBase));
					for(Entry<String,String>en:incisoBase.entrySet())
					{
						incisoPoliza.put("parametros.pv_"+en.getKey().toLowerCase(),en.getValue());
					}
				}
			}
			logger.debug(Utils.log("inciso poliza=",incisoPoliza));
			salida.put("incisoPoliza",incisoPoliza);
			
			paso = "Recuperando atributos adicionales de poliza";
			logger.debug(paso);
			Map<String,String> tvalopol    = new HashMap<String,String>();
			Map<String,String> tvalopolAux = cotizacionDAO.cargarTvalopol(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					);
			for(Entry<String,String>en:tvalopolAux.entrySet())
			{
				tvalopol.put(Utils.join("aux.",en.getKey().substring("parametros.pv_".length())),en.getValue());
			}
			logger.debug(Utils.log("tvalopol=",tvalopol));
			salida.put("tvalopol",tvalopol);
			
			paso = "Recuperando configuracion de incisos";
			logger.debug(paso);
			List<Map<String,String>> tconvalsit = Utils.concatenarParametros(consultasDAO.cargarTconvalsit(cdunieco,cdramo,estado,nmpoliza,nmsuplem),false);
			logger.debug(Utils.log("tconvalsit=",tconvalsit));
			salida.put("tconvalsit",tconvalsit);
			
			paso = "Recuperando relacion poliza-contratante";
			logger.debug(paso);
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
				logger.debug(paso);
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
			logger.debug(Utils.log("cdperson=",cdperson,", cdideper=",cdideper));
			salida.put("cdperson" , cdperson);
			salida.put("cdideper" , cdideper);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ ",salida
				,"\n@@@@@@ recuperarDatosEndosoAltaIncisoAuto @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return salida;
	}
	
	@Override
	public Map<String, Object> confirmarEndosoAltaIncisoAuto(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,List<Map<String,String>> incisos
			,String cdusuari
			,String cdelemen
			,String cdtipsup
			,String fecha
			,UserVO usuarioSesion
			,String cdsisrol
			,FlujoVO flujo
			,String confirmar
			,String cdperpag
			,String cdagente
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ confirmarEndosoAltaIncisoAuto @@@@@@"
				,"\n@@@@@@ cdunieco      = "  , cdunieco
				,"\n@@@@@@ cdramo        = " , cdramo
				,"\n@@@@@@ estado        = " , estado
				,"\n@@@@@@ nmpoliza      = " , nmpoliza
				,"\n@@@@@@ incisos       = " , incisos
				,"\n@@@@@@ cdusuari      = " , cdusuari
				,"\n@@@@@@ cdelemen      = " , cdelemen
				,"\n@@@@@@ cdtipsup      = " , cdtipsup
				,"\n@@@@@@ fecha         = " , fecha
				,"\n@@@@@@ usuarioSesion = " , usuarioSesion
				,"\n@@@@@@ cdsisrol      = " , cdsisrol
				,"\n@@@@@@ flujo         = " , flujo
				,"\n@@@@@@ confirmar     = " , confirmar
				,"\n@@@@@@ cdperpag      = " , cdperpag
                ,"\n@@@@@@ cdagente      = " , cdagente
				));
		
		String paso = null;
		
		try
		{
			Date fechaEfecto = renderFechas.parse(fecha);
			
			String tstamp = Utils.generaTimestamp();
			
			paso = "Guardando situaciones temporales";
			logger.debug(paso);
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
			logger.debug(paso);
			
			
			resParams = endososDAO.confirmarEndosoAltaIncisoAuto(
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
			
			String nmsuplem        = (String) resParams.get("pv_nmsuplem_o");
			String ntramiteEmi     = (String) resParams.get("pv_ntramite_o");
			String tipoGrupoInciso = (String) resParams.get("pv_tipoflot_o");
			String nsuplogi        = (String) resParams.get("pv_nsuplogi_o");
			
			Date fechaHoy = new Date();
			String ntramite = null;
			
			// JTEZVA - 11 NOV 2016 - SOLO CUANDO NO TENGO TRAMITE Y VOY A CONFIRMAR CREO UN TRAMITE
			if (flujo == null && "SI".equalsIgnoreCase(confirmar)) {
    			Map<String,String> valores = new HashMap<String,String>();
    			valores.put("otvalor01" , ntramiteEmi);
    			valores.put("otvalor02" , cdtipsup);
    			valores.put("otvalor03" , consultasDAO.recuperarDstipsupPorCdtipsup(cdtipsup));
    			valores.put("otvalor04" , nsuplogi);
    			valores.put("otvalor05" , cdusuari);
    			
    			Map<String, String> datosTipoTramite = consultasDAO.recuperarDatosFlujoEndoso(cdramo, cdtipsup);
    			String cdtipflu  = datosTipoTramite.get("cdtipflu"),
    			       cdflujomc = datosTipoTramite.get("cdflujomc");
    			
    			ntramite = mesaControlDAO.movimientoMesaControl(
    					cdunieco
    					,cdramo
    					,estado
    					,nmpoliza
    					,nmsuplem
    					,cdunieco
    					,cdunieco
    					,TipoTramite.ENDOSO.getCdtiptra()
    					,fechaHoy
    					,cdagente
    					,null //referencia
    					,null //nombre
    					,fechaHoy
    					,EstatusTramite.ENDOSO_CONFIRMADO.getCodigo()
    					,null //comments
    					,null //nmsolici
    					,TipoSituacion.AUTOS_RESIDENTES.getCdtipsit() // PARA PYMES Y FLOT SIEMPRE ES "AR"
    					,cdusuari
    					,usuarioSesion.getRolActivo().getClave()
    					,null
    					,cdtipflu
    					,cdflujomc
    					,valores
    					,cdtipsup
    					);
			}
			
			if("no".equals(confirmar)){
				paso = "Realizando PDF de Vista Previa de Autos";
				logger.debug(paso);
				
				String reporteEndosoPrevia = rdfEndosoPreview;
				String pdfEndosoNom = renderFechaHora.format(fechaHoy)+nmpoliza+"CotizacionPrevia.pdf";
				
				String url = rutaServidorReportes
						+ "?destype=cache"
						+ "&desformat=PDF"
						+ "&userid="+passServidorReportes
						+ "&report="+reporteEndosoPrevia
						+ "&paramform=no"
						+ "&ACCESSIBLE=YES" //parametro que habilita salida en PDF
						+ "&p_unieco="+cdunieco
						+ "&p_ramo="+cdramo
						+ "&p_estado="+estado
						+ "&p_poliza="+nmpoliza
						+ "&p_suplem="+nmsuplem
						+ "&p_perpag="+cdperpag
						+ "&desname="+rutaTempEndoso+"/"+pdfEndosoNom;
				
				paso = "Guardando PDF de Vista Previa de Autos en Temporal";
				logger.debug(paso);
				HttpUtil.generaArchivo(url,rutaTempEndoso+"/"+pdfEndosoNom);
				
				resParams.put("pdfEndosoNom_o",pdfEndosoNom);
				
			} else if ("si".equals(confirmar)) {
			    
			    if (StringUtils.isBlank(ntramite)) {
			        ntramite = flujo.getNtramite();
			    }
			    
				paso = "Realizando endoso en Web Service Autos";
				logger.debug(paso);
				
				EmisionAutosVO aux = emisionAutosService.cotizaEmiteAutomovilWS(cdunieco, cdramo, estado, nmpoliza, nmsuplem, ntramite, null, usuarioSesion);
				if(aux == null || !aux.isExitoRecibos()){
					logger.error("Error al ejecutar los WS de endoso para la Alta de inciso");
					
					boolean endosoRevertido = endososManager.revierteEndosoFallido(cdunieco, cdramo, estado, nmpoliza, null, nmsuplem, (aux == null)? Integer.valueOf(99999) : aux.getResRecibos(), "Error en endoso auto, tipo: "+TipoEndoso.findByKey(Integer.valueOf(cdtipsup)), false);
					
					if(aux!=null && aux.isEndosoSinRetarif()){
			    		throw new ApplicationException("Endoso sin Tarifa. "+(endosoRevertido?"Endoso revertido exitosamente.":"Error al revertir el endoso"));
			    	}
					
					if(endosoRevertido){
						logger.error("Endoso revertido exitosamente.");
						throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. Favor de volver a intentar.");
					}else{
						logger.error("Error al revertir el endoso");
						throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. No se ha revertido el endoso.");
					}
					
				}
				
				Map<String,String> incisosAfectados = new HashMap<String, String>();
				
				for(Map<String,String> coberturasIncisos : incisos){
				
					String inciso = coberturasIncisos.get("nmsituac");
					
					if(StringUtils.isNotBlank(inciso)){
						incisosAfectados.put(inciso,inciso);
					}
				}
				
				ejecutaCaratulaEndosoTarifaSigs(cdunieco, cdramo, estado, nmpoliza, nmsuplem, ntramite, cdtipsup, tipoGrupoInciso, aux);
			} else {
			    throw new ApplicationException("Falta el parametro confirmar");
			}
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				"\n@@@@@@ resParams => " , resParams
				, "\n@@@@@@ confirmarEndosoAltaIncisoAuto @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return resParams;
	}
	
	@Override
	public Map<String,Item> endosoBajaIncisos(
			String cdramo
			)throws Exception
	{
		logger.debug(Utils.log(
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
		
		logger.debug(Utils.log(
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
			,boolean devolver
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ confirmarEndosoBajaIncisos @@@@@@"
				,"\n@@@@@@ cdunieco      = " , cdunieco
				,"\n@@@@@@ cdramo        = " , cdramo
				,"\n@@@@@@ estado        = " , estado
				,"\n@@@@@@ nmpoliza      = " , nmpoliza
				,"\n@@@@@@ incisos       = " , incisos
				,"\n@@@@@@ cdusuari      = " , cdusuari
				,"\n@@@@@@ cdelemen      = " , cdelemen
				,"\n@@@@@@ cdtipsup      = " , cdtipsup
				,"\n@@@@@@ fecha         = " , fecha
				,"\n@@@@@@ devolver=" , devolver
				));
		
		String paso = null;
		
		try
		{
			Date fechaEfecto = renderFechas.parse(fecha);
			
			String tstamp = Utils.generaTimestamp();
			
			paso = "Guardando situaciones temporales";
			logger.debug(paso);
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
			logger.debug(paso);
			
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
			
			String nmsuplem        = (String)resParams.get("pv_nmsuplem_o");
			String ntramite        = (String)resParams.get("pv_ntramite_o");
			String tipoGrupoInciso = (String)resParams.get("pv_tipoflot_o");
			String nsuplogi        = (String)resParams.get("pv_nsuplogi_o");
			
			Map<String,String> datosPoliza = consultasDAO.recuperarDatosPolizaParaDocumentos(cdunieco, cdramo, estado, nmpoliza);
			String             ntramiteEmi = datosPoliza.get("ntramite");
			
			Map<String,String> valores = new HashMap<String,String>();
			valores.put("otvalor01" , ntramiteEmi);
			valores.put("otvalor02" , cdtipsup);
			valores.put("otvalor03" , consultasDAO.recuperarDstipsupPorCdtipsup(cdtipsup));
			valores.put("otvalor04" , nsuplogi);
			valores.put("otvalor05" , cdusuari);
			
			mesaControlDAO.movimientoMesaControl(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					,nmsuplem
					,cdunieco
					,cdunieco
					,TipoTramite.ENDOSO.getCdtiptra()
					,fechaEfecto
					,null //cdagente
					,null //referencia
					,null //nombre
					,fechaEfecto
					,EstatusTramite.ENDOSO_CONFIRMADO.getCodigo()
					,null //comments
					,null //nmsolici
					,null //cdtipsit
					,cdusuari
					,usuarioSesion.getRolActivo().getClave()
					,null
					,null
					,null
					,valores, null
					);

			
			EmisionAutosVO aux = emisionAutosService.cotizaEmiteAutomovilWS(cdunieco, cdramo, estado, nmpoliza, nmsuplem, ntramite, null, usuarioSesion);
			if(aux == null || !aux.isExitoRecibos()){
				logger.error("Error al ejecutar los WS de endoso para la Baja de Inciso");
				
				boolean endosoRevertido = endososManager.revierteEndosoFallido(cdunieco, cdramo, estado, nmpoliza, null, nmsuplem, (aux == null)? Integer.valueOf(99999) : aux.getResRecibos(), "Error en endoso auto, tipo: "+TipoEndoso.findByKey(Integer.valueOf(cdtipsup)), false);
				
				if(endosoRevertido){
					logger.error("Endoso revertido exitosamente.");
					throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. Favor de volver a intentar.");
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
		
		logger.debug(Utils.log(
				 "\n@@@@@@ confirmarEndosoBajaIncisos @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}

	@Override
	public void guardarEndosoDespago(
			 String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmrecibo
			,String nmimpres
			,String cdtipsup
			,UserVO usuarioSesion
			)throws Exception
	{
		logger.debug(Utils.log(
				"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ guardarEndosoDespago @@@@@@"
				,"\n@@@@@@ cdunieco      = " , cdunieco
				,"\n@@@@@@ cdramo        = " , cdramo
				,"\n@@@@@@ estado        = " , estado
				,"\n@@@@@@ nmpoliza      = " , nmpoliza
				,"\n@@@@@@ nmsuplem      = " , nmsuplem
				,"\n@@@@@@ nmrecibo      = " , nmrecibo
				,"\n@@@@@@ nmimpres      = " , nmimpres
				,"\n@@@@@@ cdtipsup      = " , cdtipsup
				));
		
		String paso = null;
		
		try
		{
			
			String tstamp = Utils.generaTimestamp();
			
			paso = "Guardando recibo despago";
			logger.debug(paso);
			
			Map<String,Object> resParams = endososDAO.guardaEndosoDespago(
					     cdunieco
						,cdramo
						,estado
						,nmpoliza
						,nmsuplem
						,nmrecibo
						,nmimpres
						,usuarioSesion.getUser()
						,cdtipsup
						);
			
			String nmsuplemGen = (String) resParams.get("pv_nmsuplem_o");
			String ntramite = (String) resParams.get("pv_ntramite_o");
			String tipoGrupoInciso = (String) resParams.get("pv_tipoflot_o");
			
			boolean esProductoSalud = consultasDAO.esProductoSalud(cdramo);
			
			if(esProductoSalud) {
				paso = "Enviando a Web Service para Recibos de Salud";
				logger.debug(paso);
				
				// Ejecutamos el Web Service de Recibos:
				ice2sigsService.ejecutaWSrecibos(cdunieco, cdramo, 
						estado, nmpoliza, 
						nmsuplemGen, null, 
						cdunieco, "0", ntramite, 
						true, cdtipsup, 
						usuarioSesion);
			}else{
				paso = "Enviando a Web Service Sigs";
				logger.debug(paso);
				
				EmisionAutosVO aux = emisionAutosService.cotizaEmiteAutomovilWS(cdunieco, cdramo, estado, nmpoliza, nmsuplemGen, ntramite, null, usuarioSesion);
				if(aux == null || !aux.isExitoRecibos()){
					logger.error("Error al ejecutar los WS de endoso para Despago");
					
					boolean endosoRevertido = endososManager.revierteEndosoFallido(cdunieco, cdramo, estado, nmpoliza, null, nmsuplemGen, (aux == null)? Integer.valueOf(99999) : aux.getResRecibos(), "Error en endoso auto, tipo: "+TipoEndoso.findByKey(Integer.valueOf(cdtipsup)), false);
					
					if(endosoRevertido){
						logger.error("Endoso revertido exitosamente.");
						throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. Favor de volver a intentar.");
					}else{
						logger.error("Error al revertir el endoso");
						throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. No se ha revertido el endoso.");
					}
					
				}
				
				paso = "Ejecutando caratula";
				logger.debug(paso);
				
				ejecutaCaratulaEndosoTarifaSigs(cdunieco, cdramo, estado, nmpoliza, nmsuplemGen, ntramite, cdtipsup, tipoGrupoInciso, aux);
			}
			
			
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ guardarEndosoDespago @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
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
			logger.error("Aviso, No se tienen datos de Cambio AseguradoAlterno");
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
			
			for(Map<String,String> datosEnd : datos){
			
				try{
					
					HashMap<String, Object> paramsEnd = new HashMap<String, Object>();
					
					paramsEnd.put("vIdMotivo"  , datosEnd.get("IdMotivo"));
					paramsEnd.put("vSucursal"  , datosEnd.get("Sucursal"));
					paramsEnd.put("vRamo"      , datosEnd.get("Ramo"));
					paramsEnd.put("vPoliza"    , datosEnd.get("Poliza"));
					paramsEnd.put("vInciso"    , datosEnd.get("Inciso"));
					paramsEnd.put("vTexto"     , datosEnd.get("vTexto"));
					paramsEnd.put("vEndoB"     , (endosoRecuperado==-1)?0:endosoRecuperado);
					paramsEnd.put("vFEndoso"   , datosEnd.get("FEndoso"));
					
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
			logger.error("Aviso, No se tienen datos de Cambio AdaptacionesRC");
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
					paramsEnd.put("vRecibo"     , datosEnd.get("Recibo"));
					paramsEnd.put("vFIniRec"    , datosEnd.get("FIniRec"));
					paramsEnd.put("vFFinRec"    , datosEnd.get("FFinRec"));
					paramsEnd.put("vFIniPol"    , datosEnd.get("FIniPol"));
					paramsEnd.put("vFFinPol"    , datosEnd.get("FFinPol"));
					paramsEnd.put("vFEndoso"    , datosEnd.get("FEndoso"));
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
			logger.error("Aviso, No se tienen datos de Cambio Vigencia");
			return false;
		}

		
		return true;
	}

	private boolean endosoTextoLibre(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsuplem, String ntramite, String cdtipsup, boolean nivelPoliza){
		
		logger.debug(">>>>> Entrando a metodo endosoTextoLibre");
		
		List<Map<String,String>> datos = null;
		int endosoRecuperado = -1;
		
		try{
			HashMap<String, String> params = new LinkedHashMap<String, String>();
			params.put("pv_cdunieco_i" , cdunieco);
			params.put("pv_cdramo_i" , cdramo);
			params.put("pv_estado_i" , estado);
			params.put("pv_nmpoliza_i" , nmpoliza);
			params.put("pv_nmsuplem_i" , nmsuplem);
			
			datos = endososDAO.obtieneDatosEndTextoLibre(params);
			
		} catch (Exception e1) {
			logger.error("Error en llamar al PL de obtencion de datos para endosoTextoLibre para SIGS",e1);
			return false;
		}	
		
		if(datos != null && !datos.isEmpty() && nivelPoliza){
			
			for(Map<String,String> datosEnd : datos){
				try{
					
					HashMap<String, Object> paramsEnd = new HashMap<String, Object>();
					paramsEnd.put("vIdMotivo"  , datosEnd.get("IdMotivo"));
					paramsEnd.put("vSucursal"  , datosEnd.get("Sucursal"));
					paramsEnd.put("vRamo"    , datosEnd.get("Ramo"));
					paramsEnd.put("vPoliza"   , datosEnd.get("Poliza"));
					paramsEnd.put("vTexto"    , datosEnd.get("Texto"));
					paramsEnd.put("vFEndoso"    , datosEnd.get("FEndoso"));
					
					Integer res = autosDAOSIGS.endosoTextoLibre(paramsEnd);
					
					logger.debug("Respuesta de endosoTextoLibre, numero de endoso: " + res);
					
					if(res == null || res == 0 || res == -1){
						logger.debug("Endoso endosoTextoLibre no exitoso");
						return false;
					}else{
						endosoRecuperado = res.intValue();
					}
					
				} catch (Exception e){
					logger.error("Error en Envio endosoTextoLibre Auto: " + e.getMessage(),e);
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
				logger.debug("Endoso endosoTextoLibre no exitoso, valor de endoso en -1");
				return false;
			}
			
		}else{
			logger.error("Aviso, No se tienen datos de endosoTextoLibre");
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
				paramsEnd.put("vClausula"   , datosEnIt.get("Clausula"));
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
			logger.error("Aviso, No se tienen datos de Cambio Beneficiario");
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
			logger.error("Aviso, No se tienen datos de Cambio Placas Motor");
			return false;
		}
		
		return true;
	}
	
	private boolean endosoTipoServicio(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsuplem, String ntramite, String cdtipsup){
		
		logger.debug(">>>>> Entrando a metodo Cambio Tipo de Sevicio Sin Tarificacion");
		
		List<Map<String,String>> datos = null;
		
		try{
			HashMap<String, String> params = new LinkedHashMap<String, String>();
			params.put("pv_cdunieco_i" , cdunieco);
			params.put("pv_cdramo_i" , cdramo);
			params.put("pv_estado_i" , estado);
			params.put("pv_nmpoliza_i" , nmpoliza);
			params.put("pv_nmsuplem_i" , nmsuplem);
			
			datos = endososDAO.obtieneDatosEndTipoServicio(params);
			
		} catch (Exception e1) {
			logger.error("Error en llamar al PL de obtencion de datos para Cambio Tipo de Servicio para SIGS",e1);
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
					paramsEnd.put("vServicio"    , datosEnd.get("Servicio"));
					paramsEnd.put("vTipoUso"    , datosEnd.get("TipoUso"));
					paramsEnd.put("vEndoB" , (endosoRecuperado==-1)?0:endosoRecuperado);
					paramsEnd.put("vFEndoso", datosEnd.get("FEndoso"));
					
					Integer res = autosDAOSIGS.endosoTipoServicio(paramsEnd);
					
					logger.debug("Respuesta de Cambio Tipo Servicio, numero de endoso: " + res);
					
					if(res == null || res == 0 || res == -1){
						logger.debug("Endoso Cambio Tipo Servicio no exitoso");
						return false;
					}else{
						endosoRecuperado = res.intValue();
					}
					
				} catch (Exception e){
					logger.error("Error en Envio Cambio Tipo Servicio: " + e.getMessage(),e);
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
					logger.error("Error en llamar al PL Para actualizar endoso Tipo Servicio de SIGS",e1);
					return false;
				}
			}else{
				logger.debug("Endoso Cambio Tipo Servicio no exitoso, valor de endoso en -1");
				return false;
			}
				
		}else{
			logger.error("Aviso, No se tienen datos de Cambio Tipo Servicio");
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
			logger.error("Aviso, No se tienen datos de Cambio Serie");
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
			parametros = "?"+polRes.getCduniext()+","+polRes.getCdramoext()+","+polRes.getNmpoliex()+",,0,"+ numEndoso+",0";
			logger.debug("URL Generada para Caratula: "+ urlImpresionCaratulaEndosoB + parametros);
			mesaControlDAO.guardarDocumento(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					,nmsuplem
					,new Date()
					,urlImpresionCaratulaEndosoB + parametros
					,"Car\u00e1tula Endoso B"
					,nmpoliza
					,nmtramite
					,cdtipsup
					,Constantes.SI
					,null
					,TipoTramite.POLIZA_NUEVA.getCdtiptra()
					,"0"
					,Documento.EXTERNO_CARATULA_B, null, null
					);
		
		}catch(Exception e){
			logger.error("Error al guardar la caratula de endoso B",e);
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
			,String cdsisrol
		)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ guardarEndosoAseguradoAlterno @@@@@@"
				,"\n@@@@@@ cdunieco="     , cdunieco
				,"\n@@@@@@ cdramo="       , cdramo
				,"\n@@@@@@ estado="       , estado
				,"\n@@@@@@ nmpoliza="     , nmpoliza
				,"\n@@@@@@ cdelemen="     , cdelemen
				,"\n@@@@@@ cdusuari="     , cdusuari
				,"\n@@@@@@ cdtipsup="     , cdtipsup
				,"\n@@@@@@ fechaEndoso="  , fechaEndoso
				,"\n@@@@@@ dFechaEndoso=" , dFechaEndoso
				,"\n@@@@@@ otvalores="    , otvalores
				,"\n@@@@@@ cdsisrol="     , cdsisrol
				));
		ManagerRespuestaVoidVO resp=new ManagerRespuestaVoidVO(true);
		String paso = "";
		
		try
		{
			paso ="Iniciando endoso";
			logger.debug(paso);
			
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
			logger.debug(paso);
			
			String nmsuplem = iniciarEndosoResp.get("pv_nmsuplem_o");
			String nsuplogi = iniciarEndosoResp.get("pv_nsuplogi_o");
			
			paso ="Registra los valores en TVALOPOL";
			logger.info(paso);
			cotizacionDAO.movimientoTvalopol(cdunieco, cdramo, estado, nmpoliza, nmsuplem, status, otvalores);
			paso ="Se confirma el endoso";
			logger.info(paso);
			endososDAO.confirmarEndosoB(cdunieco,cdramo,estado,nmpoliza,nmsuplem, nsuplogi, cdtipsup, null);
			
			Map<String,String> valores = new HashMap<String,String>();
			valores.put("otvalor01" , ntramite);
			valores.put("otvalor02" , cdtipsup);
			valores.put("otvalor03" , consultasDAO.recuperarDstipsupPorCdtipsup(cdtipsup));
			valores.put("otvalor04" , nsuplogi);
			valores.put("otvalor05" , cdusuari);
			
			mesaControlDAO.movimientoMesaControl(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					,nmsuplem
					,cdunieco
					,cdunieco
					,TipoTramite.ENDOSO.getCdtiptra()
					,dFechaEndoso
					,null //cdagente
					,null //referencia
					,null //nombre
					,dFechaEndoso
					,EstatusTramite.ENDOSO_CONFIRMADO.getCodigo()
					,null //comments
					,null //nmsolici
					,null //cdtipsit
					,cdusuari
					,cdsisrol
					,null
					,null
					,null
					,valores, null
					);
			
			if(this.endosoAseguradoAlterno(cdunieco, cdramo, estado, nmpoliza, nmsuplem, ntramite, cdtipsup)){
				logger.info("Endoso de endosoAseguradoAlterno exitoso...");
			}else{
				logger.error("Error al ejecutar los WS de endoso de endosoAseguradoAlterno");
				
				boolean endosoRevertido = endososManager.revierteEndosoFallido(cdunieco, cdramo, estado, nmpoliza, null, nmsuplem, 88888, "Error en endoso B tipo: "+TipoEndoso.findByKey(Integer.valueOf(cdtipsup)), true);
				if(endosoRevertido){
					logger.error("Endoso revertido exitosamente.");
					throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. Favor de volver a intentar.");
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
		
		logger.debug(Utils.log(
				 "\n@@@@@@ guardarEndosoAseguradoAlterno @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
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
			,String nmsuplemOriginal
			,String cdsisrol
		)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ guardarEndosoVigenciaPoliza @@@@@@"
				,"\n@@@@@@ cdunieco         = " , cdunieco
				,"\n@@@@@@ cdramo           = " , cdramo
				,"\n@@@@@@ estado           = " , estado
				,"\n@@@@@@ nmpoliza         = " , nmpoliza
				,"\n@@@@@@ cdelemen         = " , cdelemen
				,"\n@@@@@@ cdusuari         = " , cdusuari
				,"\n@@@@@@ cdtipsup         = " , cdtipsup
				,"\n@@@@@@ fechaEndoso      = " , fechaEndoso
				,"\n@@@@@@ dFechaEndoso     = " , dFechaEndoso
				,"\n@@@@@@ feefecto         = " , feefecto
				,"\n@@@@@@ feproren         = " , feproren
				,"\n@@@@@@ nmsuplemOriginal = " , nmsuplemOriginal
				,"\n@@@@@@ cdsisrol         = " , cdsisrol
				));
		ManagerRespuestaVoidVO resp=new ManagerRespuestaVoidVO(true);
		String paso = "";
		
		try
		{
			paso = "Modificar nmsuplem";
			logger.debug(paso);
			
			endososDAO.modificarNmsuplemSatelites(
				cdunieco
				,cdramo
				,estado
				,nmpoliza
				,nmsuplemOriginal
				,renderFechas.parse(feefecto)
				,renderFechas.parse(feproren)
			);
			
			paso = "Iniciando endoso";
			logger.debug(paso);
			
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
			
			paso = "Registrando valores variables de p\u00f3liza";
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
			
			paso = "Registrando recibos de nueva vigencia";
			logger.debug(paso);
			
			endososDAO.insertaRecibosNvaVigencia(params);
			
			paso = "Se confirma el endoso";
			logger.debug(paso);
			endososDAO.confirmarEndosoB(cdunieco,cdramo,estado,nmpoliza,nmsuplem, nsuplogi, cdtipsup, null);
			
			//para generar tramite
			Map<String,String> datosPoliza = consultasDAO.recuperarDatosPolizaParaDocumentos(cdunieco, cdramo, estado, nmpoliza);
			String ntramiteEmi = datosPoliza.get("ntramite");
			
			Map<String,String> valores = new HashMap<String,String>();
			valores.put("otvalor01" , ntramiteEmi);
			valores.put("otvalor02" , cdtipsup);
			valores.put("otvalor03" , consultasDAO.recuperarDstipsupPorCdtipsup(cdtipsup));
			valores.put("otvalor04" , nsuplogi);
			valores.put("otvalor05" , cdusuari);
			
			mesaControlDAO.movimientoMesaControl(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					,nmsuplem
					,cdunieco
					,cdunieco
					,TipoTramite.ENDOSO.getCdtiptra()
					,dFechaEndoso
					,null //cdagente
					,null //referencia
					,null //nombre
					,dFechaEndoso
					,EstatusTramite.ENDOSO_CONFIRMADO.getCodigo()
					,null //comments
					,null //nmsolici
					,null //cdtipsit
					,cdusuari
					,cdsisrol
					,null
					,null
					,null
					,valores, null
					);
			//para generar tramite
			
			if(this.endosoVigenciaPoliza(cdunieco, cdramo, estado, nmpoliza, nmsuplem, ntramite, cdtipsup)){
				logger.info("Endoso de Vigencia exitoso...");
			}else{
				logger.error("Error al ejecutar los WS de endoso de Vigencia de poliza");
				
				boolean endosoRevertido = endososManager.revierteEndosoFallido(cdunieco, cdramo, estado, nmpoliza, null, nmsuplem, 88888, "Error en endoso B tipo: "+TipoEndoso.findByKey(Integer.valueOf(cdtipsup)), true);
				if(endosoRevertido){
					logger.error("Endoso revertido exitosamente.");
					throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. Favor de volver a intentar.");
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
		
		logger.debug(Utils.log(
				 "\n@@@@@@ " , resp
				,"\n@@@@@@ guardarEndosoVigenciaPoliza @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}

	@Override
	public void guardarEndosoTextoLibre(
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
			,List<Map<String,String>> situaciones
			,String dslinea
			,String cdsisrol
			)throws Exception
	{
		logger.debug(Utils.log(
				"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ guardarEndosoTextoLibre @@@@@@"
				,"\n@@@@@@ cdunieco     = " , cdunieco
				,"\n@@@@@@ cdramo       = " , cdramo
				,"\n@@@@@@ estado       = " , estado
				,"\n@@@@@@ nmpoliza     = " , nmpoliza
				,"\n@@@@@@ cdelemen     = " , cdelemen
				,"\n@@@@@@ cdusuari     = " , cdusuari
				,"\n@@@@@@ cdtipsup     = " , cdtipsup
				,"\n@@@@@@ fechaEndoso  = " , fechaEndoso
				,"\n@@@@@@ dFechaEndoso = " , dFechaEndoso
				,"\n@@@@@@ feefecto     = " , feefecto
				,"\n@@@@@@ feproren     = " , feproren
				,"\n@@@@@@ situciones   = " , situaciones
				,"\n@@@@@@ dslinea      = " , dslinea
				));
		ManagerRespuestaVoidVO resp=new ManagerRespuestaVoidVO(true);
		String paso = "";
		try
		{
			paso = "Iniciando endoso";
			logger.debug(paso);
			
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
			
			Map<String,String>params = new HashMap<String,String>();
			
			params.put("pv_cdunieco_i" , cdunieco);
			params.put("pv_cdramo_i"   , cdramo);
			params.put("pv_estado_i"   , estado);
			params.put("pv_nmpoliza_i" , nmpoliza);
			
			params.put("pv_cdclausu_i" , "END500");
			params.put("pv_nmsuplem_i" , nmsuplem);
			params.put("pv_status_i"   , status);
			params.put("pv_cdtipcla_i" , "3");
			params.put("pv_swmodi_i"   , null);
			params.put("pv_dslinea_i"  , dslinea);
			params.put("pv_accion_i"   , Constantes.INSERT_MODE);
			
			for(Map<String,String> situacionIt : situaciones){
				params.put("pv_nmsituac_i", situacionIt.get("NMSITUAC"));
				logger.debug("EndososManager inserta MPOLICOT params: "+params);
				endososDAO.insertaTextoLibre(params);
			}
			
			boolean nivelPoliza = situaciones.get(0).containsKey("NIVEL_POLIZA");
			
			paso = "Se confirma el endoso";
			logger.debug(paso);
			endososDAO.confirmarEndosoB(cdunieco,cdramo,estado,nmpoliza,nmsuplem, nsuplogi, cdtipsup, null);
			
			Map<String,String> valores = new HashMap<String,String>();
			valores.put("otvalor01" , ntramite);
			valores.put("otvalor02" , cdtipsup);
			valores.put("otvalor03" , consultasDAO.recuperarDstipsupPorCdtipsup(cdtipsup));
			valores.put("otvalor04" , nsuplogi);
			valores.put("otvalor05" , cdusuari);
			
			mesaControlDAO.movimientoMesaControl(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					,nmsuplem
					,cdunieco
					,cdunieco
					,TipoTramite.ENDOSO.getCdtiptra()
					,dFechaEndoso
					,null //cdagente
					,null //referencia
					,null //nombre
					,dFechaEndoso
					,EstatusTramite.ENDOSO_CONFIRMADO.getCodigo()
					,null //comments
					,null //nmsolici
					,null //cdtipsit
					,cdusuari
					,cdsisrol
					,null
					,null
					,null
					,valores, null
					);
			
			if(this.endosoTextoLibre(cdunieco, cdramo, estado, nmpoliza, nmsuplem, ntramite, cdtipsup, nivelPoliza)){
				logger.info("Endoso de Vigencia exitoso...");
			}else{
				logger.error("Error al ejecutar los WS de endoso de TextoLibre");
				
				boolean endosoRevertido = endososManager.revierteEndosoFallido(cdunieco, cdramo, estado, nmpoliza, null, nmsuplem, 88888, "Error en endoso B tipo: "+TipoEndoso.findByKey(Integer.valueOf(cdtipsup)), true);
				if(endosoRevertido){
					logger.error("Endoso revertido exitosamente.");
					throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. Favor de volver a intentar.");
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
		
		logger.debug(Utils.log(
				"\n@@@@@@ " , resp
				,"\n@@@@@@ guardarEndosoTextoLibre @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}

	@Override
	public void validarEndosoAnterior(String cdunieco, String cdramo,
			String estado, String nmpoliza, String cdtipsup) throws Exception {
			logger.debug(Utils.log(
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
				logger.debug(paso);
				endososDAO.validaEndosoAnterior(cdunieco, cdramo, estado, nmpoliza, cdtipsup);
			}
			catch(Exception ex)
			{
				Utils.generaExcepcion(ex, paso);
			}
			
			logger.debug(Utils.log(
					 "\n@@@@@@ " , resp
					,"\n@@@@@@ 	  validaEndosoAnterior 	  @@@@@@"
					,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
					));
		}
	
	
	@Override
	public void validarEndosoPagados(String cdunieco, String cdramo,
			String estado, String nmpoliza,String cdtipsup) throws Exception {
			logger.debug(Utils.log(
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
				logger.debug(paso);
				endososDAO.validaEndosoPagados(cdunieco, cdramo, estado, nmpoliza, cdtipsup);
			}
			catch(Exception ex)
			{
				Utils.generaExcepcion(ex, paso);
			}
			
			logger.debug(Utils.log(
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
		logger.debug(Utils.log(
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
			logger.debug(paso);
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
			logger.debug(paso);
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
		
		logger.debug(Utils.log(
				 "\n@@@@@@ endosoClaveAuto @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return items;
	}
	
	@Override
	public Map<String,Object> guardarEndosoClaveAuto(
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
			,UserVO usuarioSesion
			,FlujoVO flujo
			,String confirmar
			,String p_plan
			,String cdperpag
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ guardarEndosoClaveAuto @@@@@@"
				,"\n@@@@@@ cdtipsup       = " , cdtipsup
				,"\n@@@@@@ cdusuari       = " , cdusuari
				,"\n@@@@@@ cdsisrol       = " , cdsisrol
				,"\n@@@@@@ cdelemen       = " , cdelemen
				,"\n@@@@@@ cdunieco       = " , cdunieco
				,"\n@@@@@@ cdramo         = " , cdramo
				,"\n@@@@@@ estado         = " , estado
				,"\n@@@@@@ nmpoliza       = " , nmpoliza
				,"\n@@@@@@ feefecto       = " , feefecto
				,"\n@@@@@@ valores        = " , valores
				,"\n@@@@@@ incisoAnterior = " , incisoAnterior
				,"\n@@@@@@ usuarioSesion  = " , usuarioSesion
				,"\n@@@@@@ flujo          = " , flujo
				,"\n@@@@@@ confirmar      = " , confirmar
				,"\n@@@@@@ p_plan         = " , p_plan
				,"\n@@@@@@ cdperpag       = " , cdperpag
				));
		
		String paso = null;
		try
		{
			paso = "Generando id operacion";
			logger.debug(paso);
			
			String tstamp = Utils.generaTimestamp();
			logger.debug(Utils.log("stamp=",tstamp));
			
			paso = "Guardando situacion temporal";
			logger.debug(paso);
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
			logger.debug(paso);
			
			resParams = endososDAO.guardarEndosoClaveAuto(cdunieco, cdramo, estado, nmpoliza, feefectoD, tstamp, cdusuari, cdelemen, cdtipsup);
			
			String nmsuplemGen     = (String)resParams.get("pv_nmsuplem_o");
			String ntramite        = (String)resParams.get("pv_ntramite_o");
			String tipoGrupoInciso = (String)resParams.get("pv_tipoflot_o");
			String nsuplogi        = (String)resParams.get("pv_nsuplogi_o");
			
			String mensajeDespacho = this.confirmarGuardandoDetallesTramiteEndoso(
					ntramite
					,cdunieco
					,cdramo
					,estado
					,nmpoliza
					,nmsuplemGen
					,cdtipsup
					,nsuplogi
					,null //dscoment
					,feefectoD
					,flujo
					,cdusuari
					,cdsisrol
					,false //confirmar
					);
			resParams.put("mensajeDespacho", mensajeDespacho);
			/*
			Map<String,String> datosPoliza = consultasDAO.recuperarDatosPolizaParaDocumentos(cdunieco, cdramo, estado, nmpoliza);
			String ntramiteEmi = datosPoliza.get("ntramite");
			
			Map<String,String> valoresTra = new HashMap<String,String>();
			valoresTra.put("otvalor01" , ntramiteEmi);
			valoresTra.put("otvalor02" , cdtipsup);
			valoresTra.put("otvalor03" , consultasDAO.recuperarDstipsupPorCdtipsup(cdtipsup));
			valoresTra.put("otvalor04" , nsuplogi);
			valoresTra.put("otvalor05" , cdusuari);
			
			mesaControlDAO.movimientoMesaControl(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					,nmsuplemGen
					,cdunieco
					,cdunieco
					,TipoTramite.ENDOSO.getCdtiptra()
					,feefectoD
					,null //cdagente
					,null //referencia
					,null //nombre
					,feefectoD
					,EstatusTramite.ENDOSO_CONFIRMADO.getCodigo()
					,null //comments
					,null //nmsolici
					,null //cdtipsit
					,cdusuari
					,usuarioSesion.getRolActivo().getClave()
					,null
					,null
					,null
					,valoresTra, null
					);
			*/
			
			if(("no").equals(confirmar)){
				Date fechaHoy = new Date();
				
				paso = "Realizando PDF de Vista Previa de Autos";
				logger.debug(paso);
				String reporteEndosoPrevia = rdfEndosoPreviewIndi;
				/*if(TipoFlotilla.Tipo_PyMES.getCdtipsit().equals(tipoGrupoInciso)){
					reporteEndosoPrevia = rdfEndosoPreview;
				}*/
				
				String pdfEndosoNom = renderFechaHora.format(fechaHoy)+nmpoliza+"CotizacionPrevia.pdf";
				
				String url = rutaServidorReportes
						+ "?destype=cache"
						+ "&desformat=PDF"
						+ "&userid="+passServidorReportes
						+ "&report="+reporteEndosoPrevia
						+ "&paramform=no"
						+ "&ACCESSIBLE=YES" //parametro que habilita salida en PDF
						+ "&p_unieco="+cdunieco
						+ "&p_ramo="+cdramo
						+ "&p_estado="+estado
						+ "&p_poliza="+nmpoliza
						+ "&p_suplem="+nmsuplemGen
						+ "&p_plan="+p_plan
						+ "&p_perpag="+cdperpag
						+ "&desname="+rutaTempEndoso+"/"+pdfEndosoNom;
				
				paso = "Guardando PDF de Vista Previa de Autos en Temporal";
				logger.debug(paso);
				paso = "Guardando PDF de Vista Previa de Autos en Temporal";
				logger.debug(paso);
				HttpUtil.generaArchivo(url,rutaTempEndoso+"/"+pdfEndosoNom);
				
				resParams.put("pdfEndosoNom_o",pdfEndosoNom);
				
			}
			if(("si").equals(confirmar)){
					paso = "Realizando endoso en Web Service Autos";
					logger.debug(paso);
					
					EmisionAutosVO aux = emisionAutosService.cotizaEmiteAutomovilWS(cdunieco, cdramo, estado, nmpoliza, nmsuplemGen, ntramite, null, usuarioSesion);
					if(aux == null || !aux.isExitoRecibos()){
						logger.error("Error al ejecutar los WS de endoso de clave auto o amis");
						
						boolean endosoRevertido = endososManager.revierteEndosoFallido(cdunieco, cdramo, estado, nmpoliza, null, nmsuplemGen, (aux == null)? Integer.valueOf(99999) : aux.getResRecibos(), "Error en endoso auto, tipo: "+TipoEndoso.findByKey(Integer.valueOf(cdtipsup)), false);
						
						if(aux!=null && aux.isEndosoSinRetarif()){
				    		throw new ApplicationException("Endoso sin Tarifa. "+(endosoRevertido?"Endoso revertido exitosamente.":"Error al revertir el endoso"));
				    	}
						
						if(endosoRevertido){
							logger.error("Endoso revertido exitosamente.");
							throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. Favor de volver a intentar.");
						}else{
							logger.error("Error al revertir el endoso");
							throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. No se ha revertido el endoso.");
						}
						
					}
					
					Map<String,String> incisosAfectados = new HashMap<String, String>();
					
					String inciso = incisoAnterior.get("NMSITUAC");
					
					if(StringUtils.isNotBlank(inciso)){
						incisosAfectados.put(inciso,inciso);
					}
					
					ejecutaCaratulaEndosoTarifaSigs(cdunieco, cdramo, estado, nmpoliza, nmsuplemGen, ntramite, cdtipsup, tipoGrupoInciso, aux);
			}	
			
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ guardarEndosoClaveAuto @@@@@@"
				,"\n@@@@@@ resParams=>> ", resParams
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return resParams;
	}
	
	@Override
	public List<Map<String,String>> obtenerRetroactividad(String cdsisrol, String cdramo,
			String cdtipsup, String fechaProceso) throws Exception {
		
		List<Map<String, String>> respRetroActividad = null;
		logger.debug(Utils.log(
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
			logger.debug(paso);
			respRetroActividad = endososDAO.obtenerRetroactividad(cdsisrol, cdramo, cdtipsup, fechaProceso);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ " , resp
				,"\n@@@@@@ 	  validaEndosoAnterior 	  @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		
		return respRetroActividad;
	}

	@Override
	public List<Map<String,String>> obtieneRecibosPagados(String cdunieco, String cdramo ,String estado ,String nmpoliza) throws Exception {
		return endososDAO.obtieneRecibosPagados(cdunieco, cdramo, estado, nmpoliza);
	}
	
	@Override
	public Map<String,Item> endosoDevolucionPrimas(
			String cdtipsup
			,String cdramo
			)throws Exception
	{
		logger.debug(Utils.log(
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
			logger.debug(paso);
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
			logger.debug(paso);
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
			logger.debug(paso);
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
		
		logger.debug(Utils.log(
				 "\n@@@@@@ endosoDevolucionPrimas @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return items;
	}
	
	private boolean ejecutaCaratulaEndosoTarifaSigs(String cdunieco,String cdramo,String estado,String nmpoliza,String nmsuplem, String ntramite, String cdtipsup, String tipoGrupoInciso, EmisionAutosVO emisionWS){
		/**
		 * Para Guardar URls de Caratula Recibos y documentos de Autos Externas
		 */
		
		try{
			
			String rutaCarpeta = Utils.join(rutaDocumentosPoliza,"/",ntramite);
			
			List<Map<String,String>> listaEndosos = emisionAutosService.obtieneEndososImprimir(cdunieco, cdramo, estado, nmpoliza, nmsuplem);
			
			if(listaEndosos == null || listaEndosos.isEmpty()){
				logger.error("Error al ejecutar caratula endoso con tarifa, Sin Datos de consulta. Para Tipo de endoso: "+ cdtipsup);
				return false;
			}
			
			for(Map<String,String> endosoIt : listaEndosos){
				if(StringUtils.isNotBlank(endosoIt.get("IMPRIMIR")) && Constantes.SI.equalsIgnoreCase(endosoIt.get("IMPRIMIR"))){
					
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
					String urlAeua = this.urlImpresionAeua;
					String urlAp = this.urlImpresionAp;
					
					String urlIncisosFlot = this.urlImpresionIncisosFlotillas;
					String urlTarjIdent = this.urlImpresionTarjetaIdentificacion;
					
					String parametros = null;
					
					/**
					 * Para Caratula
					 */
					
					parametros = "?"+emisionWS.getSucursal()+","+emisionWS.getSubramo()+","+emisionWS.getNmpoliex()+","+endosoIt.get("TIPOEND")+","+ (StringUtils.isBlank(endosoIt.get("NUMEND"))?"0":endosoIt.get("NUMEND"));
					logger.debug("URL Generada para Caratula: "+ urlCaratula + parametros);
					mesaControlDAO.guardarDocumento(
							cdunieco
							,cdramo
							,estado
							,nmpoliza
							,nmsuplem
							,new Date()
							,urlCaratula + parametros
							,"Car\u00e1tula de P\u00f3liza ("+endosoIt.get("TIPOEND")+" - "+endosoIt.get("NUMEND")+")"
							,nmpoliza
							,ntramite
							,cdtipsup
							,Constantes.SI
							,null
							,TipoTramite.POLIZA_NUEVA.getCdtiptra()
							,"0"
							,Documento.EXTERNO_CARATULA, null, null
							);
					
					/**
					 * Para Recibos
					 */
					String visible = null;
					HashMap<String,String> imprimir = new HashMap<String, String>(); 
					
					List<Map<String,String>> recibos = consultasPolizaDAO.obtieneRecibosPolizaAuto(cdunieco, cdramo, estado, nmpoliza, nmsuplem);
					
					if(recibos!= null && !recibos.isEmpty()){
						for(Map<String,String> reciboIt : recibos){
							
							/**
							 * Si el Recibo Tiene estatus 1 se guarda en tdocupol como documento de la poliza, excepto algunos endosos como el de forma de pago,
							 * donde se generan recibos negativos para cancelar y esos no deben de guardarse, estos casos el estatus es distinto de 1
							 */
							if(!"1".equals(reciboIt.get("CDESTADO"))) continue;
							
							String llave = reciboIt.get("TIPEND") + reciboIt.get("NUMEND");
							
							if(!imprimir.containsKey(llave)){
								visible = Constantes.SI;
								imprimir.put(llave, reciboIt.get("NUMREC"));
							}else{
								visible = Constantes.NO;
							}
							
							parametros = "?9999,0,"+emisionWS.getSucursal()+","+emisionWS.getSubramo()+","+emisionWS.getNmpoliex()+",0,"+(StringUtils.isBlank(endosoIt.get("NUMEND"))?"0":endosoIt.get("NUMEND"))+","+endosoIt.get("TIPOEND")+","+reciboIt.get("NUMREC");
							
							logger.debug("URL Generada para Recibo "+reciboIt.get("NUMREC")+": "+ urlRecibo + parametros);
							mesaControlDAO.guardarDocumento(
									cdunieco
									,cdramo
									,estado
									,nmpoliza
									,nmsuplem
									,new Date()
									,urlRecibo + parametros
									,"Recibo  "+reciboIt.get("NUMREC")+"  ("+endosoIt.get("TIPOEND")+" - "+endosoIt.get("NUMEND")+")"
									,nmpoliza
									,ntramite
									,cdtipsup
									,visible
									,null
									,((TipoEndoso.EMISION_POLIZA.getCdTipSup().intValue() == Integer.parseInt(cdtipsup)) || (TipoEndoso.RENOVACION.getCdTipSup().intValue() == Integer.parseInt(cdtipsup)))?TipoTramite.POLIZA_NUEVA.getCdtiptra() : TipoTramite.ENDOSO.getCdtiptra()
									,"0"
									,Documento.RECIBO, null, null
									);
						}
					}
					
					/**
					 * Para AP inciso 1
					 */
					if(StringUtils.isNotBlank(endosoIt.get("AP")) && Constantes.SI.equalsIgnoreCase(endosoIt.get("AP"))){
						parametros = "?"+emisionWS.getSucursal()+","+emisionWS.getSubramo()+","+emisionWS.getNmpoliex()+","+endosoIt.get("TIPOEND")+","+ (StringUtils.isBlank(endosoIt.get("NUMEND"))?"0":endosoIt.get("NUMEND"))+",0";
						logger.debug("URL Generada para AP Inciso 1: "+ urlAp + parametros);
						mesaControlDAO.guardarDocumento(
								cdunieco
								,cdramo
								,estado
								,nmpoliza
								,nmsuplem
								,new Date()
								,urlAp + parametros
								,"AP"
								,nmpoliza
								,ntramite
								,cdtipsup
								,Constantes.SI
								,null
								,TipoTramite.POLIZA_NUEVA.getCdtiptra()
								,"0"
								,Documento.EXTERNO_AP, null, null
								);
					}
					
					
					/**
					 * Para CAIC inciso 1
					 */
					if(StringUtils.isNotBlank(endosoIt.get("CAIC")) && Constantes.SI.equalsIgnoreCase(endosoIt.get("CAIC"))){
						parametros = "?"+emisionWS.getSucursal()+","+emisionWS.getSubramo()+","+emisionWS.getNmpoliex()+","+endosoIt.get("TIPOEND")+","+ (StringUtils.isBlank(endosoIt.get("NUMEND"))?"0":endosoIt.get("NUMEND"))+",0";
						logger.debug("URL Generada para CAIC Inciso 1: "+ urlCaic + parametros);
						mesaControlDAO.guardarDocumento(
								cdunieco
								,cdramo
								,estado
								,nmpoliza
								,nmsuplem
								,new Date()
								,urlCaic + parametros
								,"CAIC"
								,nmpoliza
								,ntramite
								,cdtipsup
								,Constantes.SI
								,null
								,TipoTramite.POLIZA_NUEVA.getCdtiptra()
								,"0"
								,Documento.EXTERNO_CAIC, null, null
								);
					}

					/**
					 * Para AEUA inciso 1
					 */
					if(StringUtils.isNotBlank(endosoIt.get("AEUA")) && Constantes.SI.equalsIgnoreCase(endosoIt.get("AEUA"))){
						parametros = "?"+emisionWS.getSucursal()+","+emisionWS.getSubramo()+","+emisionWS.getNmpoliex()+","+endosoIt.get("TIPOEND")+","+ (StringUtils.isBlank(endosoIt.get("NUMEND"))?"0":endosoIt.get("NUMEND"))+",0";
						logger.debug("URL Generada para AEUA Inciso 1: "+ urlAeua + parametros);
						mesaControlDAO.guardarDocumento(
								cdunieco
								,cdramo
								,estado
								,nmpoliza
								,nmsuplem
								,new Date()
								,urlAeua + parametros
								,"Asistencia en Estados Unidos y Canad\u00E1"
								,nmpoliza
								,ntramite
								,cdtipsup
								,Constantes.SI
								,null
								,TipoTramite.POLIZA_NUEVA.getCdtiptra()
								,"0"
								,Documento.EXTERNO_AEUA, null, null
								);
					}
					
					
					if(StringUtils.isNotBlank(tipoGrupoInciso)  && ("F".equalsIgnoreCase(tipoGrupoInciso) || "P".equalsIgnoreCase(tipoGrupoInciso))){
						/**
						 * Para Incisos Flotillas
						 */
						parametros = "?"+emisionWS.getSucursal()+","+emisionWS.getSubramo()+","+emisionWS.getNmpoliex()+","+endosoIt.get("TIPOEND")+","+ (StringUtils.isBlank(endosoIt.get("NUMEND"))?"0":endosoIt.get("NUMEND"));
						logger.debug("URL Generada para urlIncisosFlotillas: "+ urlIncisosFlot + parametros);
						mesaControlDAO.guardarDocumento(
								cdunieco
								,cdramo
								,estado
								,nmpoliza
								,nmsuplem
								,new Date()
								,urlIncisosFlot + parametros
								,"Incisos Flotillas"+" ("+endosoIt.get("TIPOEND")+" - "+endosoIt.get("NUMEND")+")"
								,nmpoliza
								,ntramite
								,cdtipsup
								,Constantes.SI
								,null
								,TipoTramite.POLIZA_NUEVA.getCdtiptra()
								,"0"
								,Documento.EXTERNO_INCISOS_FLOTILLAS, null, null
								);
						
						/**
						 * Para Tarjeta Identificacion
						 */
						parametros = "?"+emisionWS.getSucursal()+","+emisionWS.getSubramo()+","+emisionWS.getNmpoliex()+","+endosoIt.get("TIPOEND")+","+ (StringUtils.isBlank(endosoIt.get("NUMEND"))?"0":endosoIt.get("NUMEND"))+",0";
						logger.debug("URL Generada para Tarjeta Identificacion: "+ urlTarjIdent + parametros);
						mesaControlDAO.guardarDocumento(
								cdunieco
								,cdramo
								,estado
								,nmpoliza
								,nmsuplem
								,new Date()
								,urlTarjIdent + parametros
								,"Tarjeta de Identificacion"+" ("+endosoIt.get("TIPOEND")+" - "+endosoIt.get("NUMEND")+")"
								,nmpoliza
								,ntramite
								,cdtipsup
								,Constantes.SI
								,null
								,TipoTramite.POLIZA_NUEVA.getCdtiptra()
								,"0"
										,Documento.EXTERNO_TARJETA_IDENTIFICACION, null, null
								);
					}
					
					/**
					 * Para cobertura de reduce GS
					 */
					if(StringUtils.isNotBlank(endosoIt.get("REDUCEGS")) && Constantes.SI.equalsIgnoreCase(endosoIt.get("REDUCEGS"))){
						mesaControlDAO.guardarDocumento(
								cdunieco
								,cdramo
								,estado
								,nmpoliza
								,nmsuplem
								,new Date()
								,urlImpresionCobReduceGS
								,"Reduce GS"
								,nmpoliza
								,ntramite
								,cdtipsup
								,Constantes.SI
								,null
								,TipoTramite.POLIZA_NUEVA.getCdtiptra()
								,"0"
								,Documento.EXTERNO_REDUCE_GS, null, null
								);
					}
					
					/**
					 * Para cobertura de gestoria GS
					 */
					if(StringUtils.isNotBlank(endosoIt.get("GESTORIA")) && Constantes.SI.equalsIgnoreCase(endosoIt.get("GESTORIA"))){
						
						mesaControlDAO.guardarDocumento(
								cdunieco
								,cdramo
								,estado
								,nmpoliza
								,nmsuplem
								,new Date()
								,urlImpresionCobGestoriaGS
								,"Gestoria GS"
								,nmpoliza
								,ntramite
								,cdtipsup
								,Constantes.SI
								,null
								,TipoTramite.POLIZA_NUEVA.getCdtiptra()
								,"0"
								,Documento.EXTERNO_GESTORIA_GS, null, null
								);
					}

					/**
					 * Para cobertura de Seguro de Vida
					 */
					if(StringUtils.isNotBlank(endosoIt.get("COBVIDA")) && Constantes.SI.equalsIgnoreCase(endosoIt.get("COBVIDA"))){
						
						String reporteEspVida = rdfEspecSeguroVida;
						String pdfEspVidaNom = "SOL_VIDA_AUTO.pdf";
						
						String url = rutaServidorReportes
								+ "?destype=cache"
								+ "&desformat=PDF"
								+ "&userid="+passServidorReportes
								+ "&report="+reporteEspVida
								+ "&paramform=no"
								+ "&ACCESSIBLE=YES" //parametro que habilita salida en PDF
								+ "&p_unieco="+cdunieco
								+ "&p_ramo="+cdramo
								+ "&p_estado='M'"
								+ "&p_poliza="+nmpoliza
								+ "&p_suplem="+nmsuplem
								+ "&desname="+rutaCarpeta+"/"+pdfEspVidaNom;
						
						HttpUtil.generaArchivo(url,rutaCarpeta+"/"+pdfEspVidaNom);
						
						mesaControlDAO.guardarDocumento(
								cdunieco
								,cdramo
								,estado
								,nmpoliza
								,nmsuplem
								,new Date()
								,pdfEspVidaNom
								,"Especificaciones Seguro de Vida"
								,nmpoliza
								,ntramite
								,cdtipsup
								,Constantes.SI
								,null
								,TipoTramite.POLIZA_NUEVA.getCdtiptra()
								,"0"
								,Documento.EXTERNO_ESPECIF_SEGURO_VIDA, null, null
								);

						mesaControlDAO.guardarDocumento(
								cdunieco
								,cdramo
								,estado
								,nmpoliza
								,nmsuplem
								,new Date()
								,urlImpresionCondicionesSegVida
								,"Condiciones Generales Seguro de Vida"
								,nmpoliza
								,ntramite
								,cdtipsup
								,Constantes.SI
								,null
								,TipoTramite.POLIZA_NUEVA.getCdtiptra()
								,"0"
								,Documento.EXTERNO_CONDIC_GRALES_SEGURO_VIDA, null, null
								);
					}
				}
			}
			
			
		}catch(Exception ex){
			logger.error("Error al ejecutar caratula endoso con tarifa, para tipo de endoso: " + cdtipsup, ex);
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
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ endosoRehabilitacionAuto @@@@@@"
				,"\n@@@@@@ cdsisrol=" , cdsisrol
				,"\n@@@@@@ cdramo="   , cdramo
				));
		
		Map<String,Item> items = new HashMap<String,Item>();
		String           paso  = null;
		try
		{
			paso = "Recuperando elementos formulario de lectura";
			logger.debug(paso);
			
			List<ComponenteVO>formLectura = pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,cdramo
					,null //cdtipsit
					,null //estado
					,cdsisrol
					,"ENDOSO_REHABILITACION_AUTO"
					,"FORM_LECTURA"
					,null
					);
			
			paso = "Construyendo componentes del formulario de lectura";
			logger.debug(paso);
			
			GeneradorCampos gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gc.generaComponentes(formLectura, true, false, true, false, false, false);
			
			items.put("formLecturaItems" , gc.getItems());
			
			paso = "Recuperando elementos formulario de endoso";
			logger.debug(paso);
			
			List<ComponenteVO>formEndoso = pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,cdramo
					,null //cdtipsit
					,null //estado
					,cdsisrol
					,"ENDOSO_REHABILITACION_AUTO"
					,"FORM_ENDOSO"
					,null
					);
			
			paso = "Construyendo componentes del formulario de endoso";
			logger.debug(paso);
			
			gc.generaComponentes(formEndoso, true, false, true, false, false, false);
			
			items.put("formEndosoItems" , gc.getItems());
			
			paso = "Recuperando componentes de endoso";
			logger.debug(paso);
			
			List<ComponenteVO>endoso = pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,cdramo
					,null //cdtipsit
					,null //estado
					,cdsisrol
					,"ENDOSO_REHABILITACION_AUTO"
					,"MODELO_ENDOSO"
					,null
					);
			
			paso = "Construyendo componentes de endoso";
			logger.debug(paso);
			
			gc.generaComponentes(endoso, true, true, false, true, false, false);
			
			items.put("modeloEndosoFields" , gc.getFields());
			items.put("gridEndososColumns" , gc.getColumns());
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
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
			,String cdtipsup
			,String nsuplogi
			,String cddevcia
			,String cdgestor
			,Date   feemisio
			,Date   feinival
			,Date   fefinval
			,Date   feefecto
			,Date   feproren
			,String cdmoneda
			,String nmsuplem
			,String cdelemen
			,UserVO usuarioSesion
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ confirmarEndosoRehabilitacionAuto @@@@@@"
				,"\n@@@@@@ cdusuari      = " , cdusuari
				,"\n@@@@@@ cdsisrol      = " , cdsisrol
				,"\n@@@@@@ cdunieco      = " , cdunieco
				,"\n@@@@@@ cdramo        = " , cdramo
				,"\n@@@@@@ estado        = " , estado
				,"\n@@@@@@ nmpoliza      = " , nmpoliza
				,"\n@@@@@@ cdtipsup      = " , cdtipsup
				,"\n@@@@@@ nsuplogi      = " , nsuplogi
				,"\n@@@@@@ cddevcia      = " , cddevcia
				,"\n@@@@@@ cdgestor      = " , cdgestor
				,"\n@@@@@@ feemisio      = " , feemisio
				,"\n@@@@@@ feinival      = " , feinival
				,"\n@@@@@@ fefinval      = " , fefinval
				,"\n@@@@@@ feefecto      = " , feefecto
				,"\n@@@@@@ feproren      = " , feproren
				,"\n@@@@@@ cdmoneda      = " , cdmoneda
				,"\n@@@@@@ nmsuplem      = " , nmsuplem
				,"\n@@@@@@ cdelemen      = " , cdelemen
				));
		
		String paso = null;
		try
		{
			paso = "Confirmando endoso";
			Map<String,Object> resParams = endososDAO.confirmarEndosoRehabilitacion(
					cdusuari
					,cdsisrol
					,cdunieco
					,cdramo
					,estado
					,nmpoliza
					,cdtipsup
					,nsuplogi
					,cddevcia
					,cdgestor
					,feemisio
					,feinival
					,fefinval
					,feefecto
					,feproren
					,cdmoneda
					,nmsuplem
					,cdelemen
					);
			
			String nmsuplemGen = (String) resParams.get("pv_nmsuplem_o");
			String ntramite = (String) resParams.get("pv_ntramite_o");
			String tipoGrupoInciso = (String) resParams.get("pv_tipoflot_o");
			
			EmisionAutosVO aux = emisionAutosService.cotizaEmiteAutomovilWS(cdunieco, cdramo, estado, nmpoliza, nmsuplemGen, ntramite, null, usuarioSesion);
			if(aux == null || !aux.isExitoRecibos()){
				logger.error("Error al ejecutar los WS de endoso para Rehabilitacion Auto");
				
				boolean endosoRevertido = endososManager.revierteEndosoFallido(cdunieco, cdramo, estado, nmpoliza, null, nmsuplemGen, (aux == null)? Integer.valueOf(99999) : aux.getResRecibos(), "Error en endoso auto, tipo: "+TipoEndoso.findByKey(Integer.valueOf(cdtipsup)), false);
				
				if(endosoRevertido){
					logger.error("Endoso revertido exitosamente.");
					throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. Favor de volver a intentar.");
				}else{
					logger.error("Error al revertir el endoso");
					throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. No se ha revertido el endoso.");
				}
				
			}
			
			ejecutaCaratulaEndosoTarifaSigs(cdunieco, cdramo, estado, nmpoliza, nmsuplemGen, ntramite, cdtipsup, tipoGrupoInciso, aux);
			
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ confirmarEndosoRehabilitacionAuto @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}

	@Override
	public Map<String,Item> endosoRehabilitacionSalud(
			String cdsisrol
			,String cdramo
			)throws Exception
	{
		logger.debug(Utils.log(
				"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ endosoRehabilitacionSalud @@@@@@"
				,"\n@@@@@@ cdsisrol=" , cdsisrol
				,"\n@@@@@@ cdramo="   , cdramo
				));
		
		Map<String,Item> items = new HashMap<String,Item>();
		String           paso  = null;
		try
		{
			paso = "Recuperando elementos formulario de lectura";
			logger.debug(paso);
			
			List<ComponenteVO>formLectura = pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,cdramo
					,null //cdtipsit
					,null //estado
					,cdsisrol
					,"ENDOSO_REHABILITACION_SALUD"
					,"FORM_LECTURA"
					,null
					);
			
			paso = "Construyendo componentes del formulario de lectura";
			logger.debug(paso);
			
			GeneradorCampos gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gc.generaComponentes(formLectura, true, false, true, false, false, false);
			
			items.put("formLecturaItems" , gc.getItems());
			
			paso = "Recuperando elementos formulario de endoso";
			logger.debug(paso);
			
			List<ComponenteVO>formEndoso = pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,cdramo
					,null //cdtipsit
					,null //estado
					,cdsisrol
					,"ENDOSO_REHABILITACION_SALUD"
					,"FORM_ENDOSO"
					,null
					);
			
			paso = "Construyendo componentes del formulario de endoso";
			logger.debug(paso);
			
			gc.generaComponentes(formEndoso, true, false, true, false, false, false);
			
			items.put("formEndosoItems" , gc.getItems());
			
			paso = "Recuperando componentes de endoso";
			logger.debug(paso);
			
			List<ComponenteVO>endoso = pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,cdramo
					,null //cdtipsit
					,null //estado
					,cdsisrol
					,"ENDOSO_REHABILITACION_SALUD"
					,"MODELO_ENDOSO"
					,null
					);
			
			paso = "Construyendo componentes de endoso";
			logger.debug(paso);
			
			gc.generaComponentes(endoso, true, true, false, true, false, false);
			
			items.put("modeloEndosoFields" , gc.getFields());
			items.put("gridEndososColumns" , gc.getColumns());
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				"\n@@@@@@ items=",items
				,"\n@@@@@@ endosoRehabilitacionSalud @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		
		return items;
	}
	
	@Override
	public void confirmarEndosoRehabilitacionSalud(
			String cdusuari
			,String cdsisrol
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdtipsup
			,String nsuplogi
			,String cddevcia
			,String cdgestor
			,Date   feemisio
			,Date   feinival
			,Date   fefinval
			,Date   feefecto
			,Date   feproren
			,String cdmoneda
			,String nmsuplem
			,String cdelemen
			,UserVO usuarioSesion
			)throws Exception
	{
		logger.debug(Utils.log(
				"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ confirmarEndosoRehabilitacionSalud @@@@@@"
				,"\n@@@@@@ cdusuari=" , cdusuari
				,"\n@@@@@@ cdsisrol=" , cdsisrol
				,"\n@@@@@@ cdunieco=" , cdunieco
				,"\n@@@@@@ cdramo="   , cdramo
				,"\n@@@@@@ estado="   , estado
				,"\n@@@@@@ nmpoliza=" , nmpoliza
				,"\n@@@@@@ cdtipsup=" , cdtipsup
				,"\n@@@@@@ nsuplogi=" , nsuplogi
				,"\n@@@@@@ cddevcia=" , cddevcia
				,"\n@@@@@@ cdgestor=" , cdgestor
				,"\n@@@@@@ feemisio=" , feemisio
				,"\n@@@@@@ feinival=" , feinival
				,"\n@@@@@@ fefinval=" , fefinval
				,"\n@@@@@@ feefecto=" , feefecto
				,"\n@@@@@@ feproren=" , feproren
				,"\n@@@@@@ cdmoneda=" , cdmoneda
				,"\n@@@@@@ nmsuplem=" , nmsuplem
				,"\n@@@@@@ cdelemen=" , cdelemen
				));
		
		String paso = null;
		try
		{
			paso = "Confirmando endoso";
			Map<String,Object> resParams = endososDAO.confirmarEndosoRehabilitacion(
					cdusuari
					,cdsisrol
					,cdunieco
					,cdramo
					,estado
					,nmpoliza
					,cdtipsup
					,nsuplogi
					,cddevcia
					,cdgestor
					,feemisio
					,feinival
					,fefinval
					,feefecto
					,feproren
					,cdmoneda
					,nmsuplem
					,cdelemen
					);
			
			String nmsuplemGen = (String) resParams.get("pv_nmsuplem_o");
			String ntramite = (String) resParams.get("pv_ntramite_o");
//			String tipoGrupoInciso = (String) resParams.get("pv_tipoflot_o");
			
			ice2sigsService.ejecutaWSrecibos(cdunieco, cdramo, 
					estado, nmpoliza, 
					nmsuplemGen, null, 
					cdunieco, null, ntramite, 
					true, cdtipsup, 
					usuarioSesion);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				"\n@@@@@@ confirmarEndosoRehabilitacionSalud @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	
	@Override
	public Map<String,Item> endosoCancelacionAuto(
			String cdsisrol
			,String cdramo
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ endosoCancelacionAuto @@@@@@"
				,"\n@@@@@@ cdsisrol=" , cdsisrol
				,"\n@@@@@@ cdramo="   , cdramo
				));
		
		Map<String,Item> items = new HashMap<String,Item>();
		String           paso  = null;
		try
		{
			paso = "Recuperando elementos formulario de lectura";
			logger.debug(paso);
			
			List<ComponenteVO>formLectura = pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,cdramo
					,null //cdtipsit
					,null //estado
					,cdsisrol
					,"ENDOSO_CANCELACION_AUTO"
					,"FORM_LECTURA"
					,null
					);
			
			paso = "Construyendo componentes del formulario de lectura";
			logger.debug(paso);
			
			GeneradorCampos gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gc.generaComponentes(formLectura, true, false, true, false, false, false);
			
			items.put("formLecturaItems" , gc.getItems());
			
			paso = "Recuperando elementos formulario de endoso";
			logger.debug(paso);
			
			List<ComponenteVO>formEndoso = pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,cdramo
					,null //cdtipsit
					,null //estado
					,cdsisrol
					,"ENDOSO_CANCELACION_AUTO"
					,"FORM_ENDOSO"
					,null
					);
			
			paso = "Construyendo componentes del formulario de endoso";
			logger.debug(paso);
			
			gc.generaComponentes(formEndoso, true, false, true, false, false, false);
			
			items.put("formEndosoItems" , gc.getItems());
			
			paso = "Recuperando componentes de endoso";
			logger.debug(paso);
			
			List<ComponenteVO>endoso = pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,cdramo
					,null //cdtipsit
					,null //estado
					,cdsisrol
					,"ENDOSO_CANCELACION_AUTO"
					,"MODELO_ENDOSO"
					,null
					);
			
			paso = "Construyendo componentes de endoso";
			logger.debug(paso);
			
			gc.generaComponentes(endoso, true, true, false, true, false, false);
			
			items.put("modeloEndosoFields" , gc.getFields());
			items.put("gridEndososColumns" , gc.getColumns());
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ items=",items
				,"\n@@@@@@ endosoCancelacionAuto @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		
		return items;
	}
	
	@Override
	public Map<String,String> buscarError(String codigo,String rutaLogs,String archivo) throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ buscarError @@@@@@"
				,"\n@@@@@@ codigo="   , codigo
				,"\n@@@@@@ rutaLogs=" , rutaLogs
				,"\n@@@@@@ archivo="  , archivo
				));
		
		Map<String,String> mapa = new HashMap<String,String>();
		String             paso = null;
		
		try
		{
			paso = "Convirtiendo codigo a numero";
			logger.debug(paso);
			long timestamp;
			
			if(codigo.length()<12)
			{
				timestamp = Long.parseLong(codigo.toLowerCase(), 36);
			}
			else
			{
				timestamp = Long.parseLong(codigo);
			}
			
			mapa.put("timestamp" , String.format("%d",timestamp));
			mapa.put("fecha"     , new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timestamp)));
			
			paso = "Abriendo archivo de log";
			logger.debug(paso);
			DataInputStream in    = new DataInputStream(new FileInputStream(Utils.join(rutaLogs,Constantes.SEPARADOR_ARCHIVO,archivo)));
			String          linea = null;
			StringBuilder   sb    = new StringBuilder();
			int             i     = 0;
			try
			{
				while((linea=in.readLine())!=null)
				{
					if(i==0)//no se ha encontrado
					{
						if(linea.toLowerCase().contains(Utils.join("#",codigo.toLowerCase())))
						{
							sb.append(linea).append("<br/>");
							i=1;
						}
					}
					else if(i>0&&i<250)
					{
						sb.append(linea).append("<br/>");
						i++;
					}
					else
					{
						break;
					}
				}
			}
			catch(Exception ex)
			{
				in.close();
				throw ex;
			}
			in.close();
			if(i==0)
			{
				mapa.put("log","No se encuentra el codigo de error en el log");
			}
			else
			{
				mapa.put("log",sb.toString());
			}
			
			logger.debug(String.format("En long %d",timestamp));
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}

		logger.debug(Utils.log(
				 "\n@@@@@@ buscarError @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return mapa;
	}
	
	@Override
	public void confirmarEndosoCancelacionEndoso(
			String cdusuari
			,String cdsisrol
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdtipsup
			,String nsuplogi
			,String cddevcia
			,String cdgestor
			,Date   feemisio
			,Date   feinival
			,Date   fefinval
			,Date   feefecto
			,Date   feproren
			,String cdmoneda
			,String nmsuplem
			,String cdelemen
			,Date   feinicio
			,UserVO usuarioSesion
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ confirmarEndosoCancelacionEndoso @@@@@@"
				,"\n@@@@@@ cdusuari      = " , cdusuari
				,"\n@@@@@@ cdsisrol      = " , cdsisrol
				,"\n@@@@@@ cdunieco      = " , cdunieco
				,"\n@@@@@@ cdramo        = " , cdramo
				,"\n@@@@@@ estado        = " , estado
				,"\n@@@@@@ nmpoliza      = " , nmpoliza
				,"\n@@@@@@ cdtipsup      = " , cdtipsup
				,"\n@@@@@@ nsuplogi      = " , nsuplogi
				,"\n@@@@@@ cddevcia      = " , cddevcia
				,"\n@@@@@@ cdgestor      = " , cdgestor
				,"\n@@@@@@ feemisio      = " , feemisio
				,"\n@@@@@@ feinival      = " , feinival
				,"\n@@@@@@ fefinval      = " , fefinval
				,"\n@@@@@@ feefecto      = " , feefecto
				,"\n@@@@@@ feproren      = " , feproren
				,"\n@@@@@@ cdmoneda      = " , cdmoneda
				,"\n@@@@@@ nmsuplem      = " , nmsuplem
				,"\n@@@@@@ cdelemen      = " , cdelemen
				,"\n@@@@@@ feinicio      = " , feinicio
				));
		
		String paso = null;
		try
		{
			paso = "Confirmando endoso";
			Map<String,Object> resParams = endososDAO.confirmarEndosoCancelacionAuto(
					cdusuari
					,cdsisrol
					,cdunieco
					,cdramo
					,estado
					,nmpoliza
					,cdtipsup
					,nsuplogi
					,cddevcia
					,cdgestor
					,feemisio
					,feinival
					,fefinval
					,feefecto
					,feproren
					,cdmoneda
					,nmsuplem
					,cdelemen
					,feinicio
					);
			
			String nmsuplemGen = (String) resParams.get("pv_nmsuplem_o");
			String ntramite = (String) resParams.get("pv_ntramite_o");
			String tipoGrupoInciso = (String) resParams.get("pv_tipoflot_o");
			
			
			boolean esProductoSalud = consultasDAO.esProductoSalud(cdramo);
			
			if(esProductoSalud){
				paso = "Ejecutando Web Service de Recibos de Salud para endoso de cancelacion de endoso";
				logger.debug(paso);
				
				// Ejecutamos el Web Service de Recibos:
				ice2sigsService.ejecutaWSrecibos(cdunieco, cdramo, 
						estado, nmpoliza, 
						nmsuplemGen, null, 
						cdunieco, "0", ntramite, 
						true, cdtipsup, 
						usuarioSesion);
			}else{
				
				paso = "Ejecutando Web Service de Recibos SIGS autos para endoso de cancelacion de endoso";
				logger.debug(paso);
				
				EmisionAutosVO aux = emisionAutosService.cotizaEmiteAutomovilWS(cdunieco, cdramo, estado, nmpoliza, nmsuplemGen, ntramite, null, usuarioSesion);
				if(aux == null || !aux.isExitoRecibos()){
					logger.error("Error al ejecutar los WS de endoso para cancelacion de endoso");
					
					boolean endosoRevertido = endososManager.revierteEndosoFallido(cdunieco, cdramo, estado, nmpoliza, null, nmsuplemGen, (aux == null)? Integer.valueOf(99999) : aux.getResRecibos(), "Error en endoso auto, tipo: "+TipoEndoso.findByKey(Integer.valueOf(cdtipsup)), false);
					
					if(endosoRevertido){
						logger.error("Endoso revertido exitosamente.");
						throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. Favor de volver a intentar.");
					}else{
						logger.error("Error al revertir el endoso");
						throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. No se ha revertido el endoso.");
					}
					
				}
				
				ejecutaCaratulaEndosoTarifaSigs(cdunieco, cdramo, estado, nmpoliza, nmsuplemGen, ntramite, cdtipsup, tipoGrupoInciso, aux);
			}
			
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ confirmarEndosoCancelacionEndoso @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	
	public Map<String,Object> guardarEndosoDevolucionPrimas(
			String cdusuari
			,String cdsisrol
			,String cdelemen
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdtipsup
			,String tstamp
			,Date   feefecto
			,List<Map<String,String>> incisos
			,UserVO usuarioSesion
			,FlujoVO flujo
			,String confirmar
			,String cdperpag
			,String p_plan
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ guardarEndosoDevolucionPrimas @@@@@@"
				,"\n@@@@@@ cdusuari      = " , cdusuari
				,"\n@@@@@@ cdsisrol      = " , cdsisrol
				,"\n@@@@@@ cdelemen      = " , cdelemen
				,"\n@@@@@@ cdunieco      = " , cdunieco
				,"\n@@@@@@ cdramo        = " , cdramo
				,"\n@@@@@@ estado        = " , estado
				,"\n@@@@@@ nmpoliza      = " , nmpoliza
				,"\n@@@@@@ cdtipsup      = " , cdtipsup
				,"\n@@@@@@ tstamp        = " , tstamp
				,"\n@@@@@@ feefecto      = " , feefecto
				,"\n@@@@@@ incisos       = " , incisos
				,"\n@@@@@@ usuarioSesion = " , usuarioSesion
				,"\n@@@@@@ flujo         = " , flujo
				,"\n@@@@@@ confirmar     = " , confirmar
				,"\n@@@@@@ cdperpag      = " , cdperpag
				,"\n@@@@@@ p_plan        = " , p_plan
				));
		
		String paso = null;
		try
		{
			paso = "Guardando incisos temporales";
			logger.debug(paso);
			for(Map<String,String> inciso : incisos)
			{
				endososDAO.guardarTvalositEndoso(
						cdunieco
						,cdramo
						,estado
						,nmpoliza
						,inciso.get("nmsituac")
						,inciso.get("nmsuplem")
						,inciso.get("status")
						,inciso.get("cdtipsit")
						,"BAJA"  , null , null , null , null , null , null , null , null , null
						,null    , null , null , null , null , null , null , null , null , null
						,null    , null , null , null , null , null , null , null , null , null
						,null    , null , null , null , null , null , null , null , null , null
						,null    , null , null , null , null , null , null , null , null , null
						,null    , null , null , null , null , null , null , null , null , null
						,null    , null , null , null , null , null , null , null , null , null
						,null    , null , null , null , null , null , null , null , null , null
						,null    , null , null , null , null , null , null , null , null , null
						,null    , null , null , null , null , null , null , null , null
						,tstamp
						);
			}
			
			// Se genera el endoso, se confirma y se genera el tramite:
			paso = "Confirmando endoso";
			logger.debug(paso);
			resParams = endososDAO.guardarEndosoDevolucionPrimas(cdusuari, cdsisrol, cdelemen,
					cdunieco, cdramo, estado, nmpoliza, cdtipsup, tstamp, feefecto);
			
			String nmsuplemGen     = (String) resParams.get("pv_nmsuplem_o");
			String ntramite        = (String) resParams.get("pv_ntramite_o");
			String tipoGrupoInciso = (String) resParams.get("pv_tipoflot_o");
			String nsuplogi        = (String) resParams.get("pv_nsuplogi_o");
			
			String mensajeDespacho = this.confirmarGuardandoDetallesTramiteEndoso(
					ntramite
					,cdunieco
					,cdramo
					,estado
					,nmpoliza
					,nmsuplemGen
					,cdtipsup
					,nsuplogi
					,null //dscoment
					,feefecto
					,flujo
					,cdusuari
					,cdsisrol
					,false //confirmar
					);
			resParams.put("mensajeDespacho", mensajeDespacho);
			
			// Se envian los datos a traves del WS de autos:
			if(("no").equals(confirmar)){
				Date fechaHoy = new Date();
				logger.debug("tipoGrupoInciso: "+tipoGrupoInciso);
				paso = "Realizando PDF de Vista Previa de Autos";
				logger.debug(paso);
				String reporteEndosoPrevia = rdfEndosoPreviewIndi;
				if(TipoFlotilla.Tipo_PyMES.getCdtipsit().equals(tipoGrupoInciso)){
					reporteEndosoPrevia = rdfEndosoPreview;
				}
				
				String pdfEndosoNom = renderFechaHora.format(fechaHoy)+nmpoliza+"CotizacionPrevia.pdf";
				
				String url = rutaServidorReportes
						+ "?destype=cache"
						+ "&desformat=PDF"
						+ "&userid="+passServidorReportes
						+ "&report="+reporteEndosoPrevia
						+ "&paramform=no"
						+ "&ACCESSIBLE=YES" //parametro que habilita salida en PDF
						+ "&p_unieco="+cdunieco
						+ "&p_ramo="+cdramo
						+ "&p_estado="+estado
						+ "&p_poliza="+nmpoliza
						+ "&p_suplem="+nmsuplemGen
						+ "&p_plan="+p_plan
						+ "&p_perpag="+cdperpag
						+ "&desname="+rutaTempEndoso+"/"+pdfEndosoNom;
				
				paso = "Guardando PDF de Vista Previa de Autos en Temporal";
				logger.debug(paso);
				paso = "Guardando PDF de Vista Previa de Autos en Temporal";
				logger.debug(paso);
				HttpUtil.generaArchivo(url,rutaTempEndoso+"/"+pdfEndosoNom);
				
				resParams.put("pdfEndosoNom_o",pdfEndosoNom);
				
			}
			if("si".equals(confirmar)){
					paso = "Realizando endoso en Web Service Autos";
					logger.debug(paso);
					
					EmisionAutosVO aux = emisionAutosService.cotizaEmiteAutomovilWS(cdunieco, cdramo, estado, nmpoliza, nmsuplemGen, ntramite, null, usuarioSesion);
					if(aux == null || !aux.isExitoRecibos()){
						logger.error("Error al ejecutar los WS de endoso para devolucion de primas");
						
						boolean endosoRevertido = endososManager.revierteEndosoFallido(cdunieco, cdramo, estado, nmpoliza, null, nmsuplemGen, (aux == null)? Integer.valueOf(99999) : aux.getResRecibos(), "Error en endoso auto, tipo: "+TipoEndoso.findByKey(Integer.valueOf(cdtipsup)), false);
						
						if(aux!=null && aux.isEndosoSinRetarif()){
				    		throw new ApplicationException("Endoso sin Tarifa. "+(endosoRevertido?"Endoso revertido exitosamente.":"Error al revertir el endoso"));
				    	}
						
						if(endosoRevertido){
							logger.error("Endoso revertido exitosamente.");
							throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. Favor de volver a intentar.");
						}else{
							logger.error("Error al revertir el endoso");
							throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. No se ha revertido el endoso.");
						}
						
					}
					
					Map<String,String> incisosAfectados = new HashMap<String, String>();
					
					for(Map<String,String> coberturasIncisos : incisos){
					
						String inciso = coberturasIncisos.get("nmsituac");
						
						if(StringUtils.isNotBlank(inciso)){
							incisosAfectados.put(inciso,inciso);
						}
					}
					
					ejecutaCaratulaEndosoTarifaSigs(cdunieco, cdramo, estado, nmpoliza, nmsuplemGen, ntramite, cdtipsup, tipoGrupoInciso, aux);
					
			}
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ guardarEndosoDevolucionPrimas @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return resParams;
	}
	
	@Override
	public Map<String,Item> endosoCancelacionPolAuto(String cdsisrol, String cdramo) throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ endosoCancelacionPolAuto @@@@@@"
				,"\n@@@@@@ cdsisrol=" , cdsisrol
				,"\n@@@@@@ cdramo="   , cdramo
				));
		
		Map<String,Item> items = new HashMap<String,Item>();
		String           paso  = null;
		
		try
		{
			paso = "Construyendo componentes de pantalla";
			logger.debug(paso);
			ThreadCounter tc = new ThreadCounter(ServletActionContext.getServletContext().getServletContextName(),pantallasDAO);
			tc.agregarConstructor(new ConstructorComponentesAsync(
					"panelLectura" //llaveGenerador
					,null          //cdtiptra
					,null          //cdunieco
					,cdramo
					,null          //cdtipsit
					,null          //estado
					,cdsisrol
					,"ENDOSO_CANCELACION_POLIZA"
					,"PANEL_LECTURA"
					,null          //orden
					,true          //parcial
					,false         //fields
					,true          //items
					,false         //columns
					,false         //editor
					,false         //buttons
					)
			);
			
			tc.agregarConstructor(new ConstructorComponentesAsync(
					"formEndoso" //llaveGenerador
					,null        //cdtiptra
					,null        //cdunieco
					,cdramo
					,null        //cdtipsit
					,null        //estado
					,cdsisrol
					,"ENDOSO_CANCELACION_POLIZA"
					,"FORM_ENDOSO"
					,null        //orden
					,true        //parcial
					,false       //fields
					,true        //items
					,false       //columns
					,false       //editor
					,false       //buttons
					)
			);
			
			Map<String,GeneradorCampos>mapaGc = tc.run();
			
			items.put("panelLecturaItems" , mapaGc.get("panelLectura").getItems());
			items.put("formEndosoItems"   , mapaGc.get("formEndoso").getItems());
			
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ endosoCancelacionPolAuto @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return items;
	}
	
	@Override
	public Map<String,String> marcarPolizaCancelarPorEndoso(String cdunieco, String cdramo, String nmpoliza) throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ marcarPolizaCancelarPorEndoso @@@@@@"
				,"\n@@@@@@ cdunieco=" , cdunieco
				,"\n@@@@@@ cdramo="   , cdramo
				,"\n@@@@@@ nmpoliza=" , nmpoliza
				));
		
		Map<String,String> poliza = null;
		String             paso   = null;
		try
		{
			paso = "Seleccionando poliza";
			logger.debug(paso);
			cancelacionDAO.seleccionaPolizaUnica(
					cdunieco
					,cdramo
					,nmpoliza
					,null       //agencia
					,new Date() //fechapro
					);
			
			paso = "Recuperando poliza";
			logger.debug(paso);
			List<Map<String,String>> polizas = cancelacionDAO.obtenerPolizasCandidatas(
					null //asegurado
					,cdunieco
					,cdramo
					,nmpoliza
					,null //nmsituac
					);
			
			Utils.validate(polizas , "No se puede cancelar la poliza");
			if(polizas.size()>1)
			{
				throw new ApplicationException("Poliza repetida en los registros");
			}
			
			poliza = polizas.get(0);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ poliza=",poliza
				,"\n@@@@@@ marcarPolizaCancelarPorEndoso @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return poliza;
	}
	
	@Override
	public Map<String,Object> confirmarEndosoCancelacionPolAuto(
			String cdusuari
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdrazon
			,Date feefecto
			,Date fevencim
			,Date fecancel
			,String cdtipsup
			,UserVO usuarioSesion
			,String cdsisrol
			,FlujoVO flujo
			,String confirmar
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ confirmarEndosoCancelacionPolAuto @@@@@@"
				,"\n@@@@@@ cdusuari = " , cdusuari
				,"\n@@@@@@ cdunieco = " , cdunieco
				,"\n@@@@@@ cdramo   = " , cdramo
				,"\n@@@@@@ estado   = " , estado
				,"\n@@@@@@ nmpoliza = " , nmpoliza
				,"\n@@@@@@ cdrazon  = " , cdrazon
				,"\n@@@@@@ feefecto = " , feefecto
				,"\n@@@@@@ fevencim = " , fevencim
				,"\n@@@@@@ fecancel = " , fecancel
				,"\n@@@@@@ cdtipsup = " , cdtipsup
				,"\n@@@@@@ cdsisrol = " , cdsisrol
				,"\n@@@@@@ flujo    = " , flujo
				,"\n@@@@@@ confirmar= " , confirmar
				));
		
		String paso = null;
		
		try
		{
			paso = "Cancelando poliza";
			logger.debug(paso);
			
			resParams = cancelacionDAO.cancelaPoliza(
					cdunieco
					,cdramo
					,cdunieco //cduniage
					,estado
					,nmpoliza
					,null     //nmsituac
					,cdrazon
					,null     //comenta
					,feefecto
					,fevencim
					,fecancel
					,cdusuari
					,cdtipsup
					,usuarioSesion.getRolActivo().getClave()
					);
			
			String nmsuplemGen     = (String) resParams.get("pv_nmsuplem_o");
			String ntramite        = (String) resParams.get("pv_ntramite_o");
			String tipoGrupoInciso = (String) resParams.get("pv_tipoflot_o");
			String nsuplogi        = (String) resParams.get("pv_nsuplogi_o");
			
			String mensajeDespacho = this.confirmarGuardandoDetallesTramiteEndoso(
					ntramite
					,cdunieco
					,cdramo
					,estado
					,nmpoliza
					,nmsuplemGen
					,cdtipsup
					,nsuplogi
					,null //dscoment
					,fecancel
					,flujo
					,cdusuari
					,cdsisrol
					,false //confirmar
					);
			resParams.put("mensajeDespacho", mensajeDespacho);
			
			logger.debug(">>>nmsuplemGen retornado de cancelacion: " +nmsuplemGen);
			logger.debug(">>>ntramite retornado de cancelacion: " +ntramite);
			logger.debug(">>>tipoGrupoInciso retornado de cancelacion: " +tipoGrupoInciso);
			
			paso = "Realizando endoso en Web Service Autos";
			logger.debug(paso);
			
			EmisionAutosVO aux = emisionAutosService.cotizaEmiteAutomovilWS(cdunieco, cdramo, estado, nmpoliza, nmsuplemGen, ntramite, null, usuarioSesion);
			if(aux == null || !aux.isExitoRecibos()){
			    logger.error("Error al ejecutar los WS de endoso para cancelacion de poliza de auto");
				
			    boolean endosoRevertido = endososManager.revierteEndosoFallido(cdunieco, cdramo, estado, nmpoliza, null, nmsuplemGen, (aux == null)? Integer.valueOf(99999) : aux.getResRecibos(), "Error en endoso auto, tipo: "+TipoEndoso.findByKey(Integer.valueOf(cdtipsup)), false);
				
			    if(aux!=null && aux.isEndosoSinRetarif()){
		    		throw new ApplicationException("Endoso sin Tarifa. "+(endosoRevertido?"Endoso revertido exitosamente.":"Error al revertir el endoso"));
		    	    }
				
			    if(endosoRevertido){
			        logger.error("Endoso revertido exitosamente.");
				throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. Favor de volver a intentar.");
			    }else{
			        logger.error("Error al revertir el endoso");
				throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. No se ha revertido el endoso.");
			    }
			}
			
            ejecutaCaratulaEndosoTarifaSigs(cdunieco, cdramo, estado, nmpoliza, nmsuplemGen, ntramite, cdtipsup, tipoGrupoInciso, aux);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ confirmarEndosoCancelacionPolAuto @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return resParams;
	}
	
	@Override
	public Map<String,Item> endosoValositFormsAuto(
			String cdtipsup
			,String cdsisrol
			,String cdramo
			,List<Map<String,String>> incisos
			) throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ endosoValositFormsAuto @@@@@@"
				,"\n@@@@@@ cdtipsup=" , cdtipsup
				,"\n@@@@@@ cdsisrol=" , cdsisrol
				,"\n@@@@@@ cdramo="   , cdramo
				,"\n@@@@@@ incisos="  , Utils.size(incisos)
				));
		
		Map<String,Item> items = new LinkedHashMap<String,Item>();
		String           paso  = null;
		
		try
		{
			paso = "Creando hilos de lectores de componentes";
			logger.debug(paso);
			ThreadCounter hilos = new ThreadCounter(ServletActionContext.getServletContext().getServletContextName(), pantallasDAO);
			
			for(Map<String,String> inciso : incisos)
			{
				hilos.agregarConstructor(new ConstructorComponentesAsync(
						Utils.join("items",inciso.get("NMSITUAC"))
						,cdtipsup
						,null //cdunieco
						,cdramo
						,Utils.join("|",inciso.get("CDTIPSIT"),"|")
						,null //estado
						,cdsisrol
						,"ENDOSO_VALOSIT_FORMS_AUTO"
						,"ITEMS"
						,null  //orden
						,true  //esParcial
						,false //conField
						,true  //conItem
						,false //conColumn
						,false //conEditor
						,false //conButton
						));
			}
			
			paso = "Disparando hilos de constructores de componentes";
			logger.debug(paso);
			Map<String,GeneradorCampos> generadores = hilos.run();
			
			for(Map<String,String> inciso : incisos)
			{
				String key = Utils.join("items",inciso.get("NMSITUAC"));
				items.put(key,generadores.get(key).getItems());
			}
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ endosoValositFormsAuto @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return items;
	}
	
	@Override
	public void validaEndosoCambioVigencia(String cdunieco, String cdramo,
		String estado, String nmpoliza) throws Exception {
		logger.debug(Utils.log(
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
			paso = "Iniciando valida endoso cambio vigencia";
			logger.debug(paso);
			endososDAO.validaEndosoCambioVigencia(cdunieco, cdramo, estado, nmpoliza);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ " , resp
				,"\n@@@@@@ 	  validaEndosoAnterior 	  @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	
	@Override
	public Map<String, String> confirmarEndosoValositFormsAuto(
			String cdusuari
			,String cdsisrol
			,String cdelemen
			,String cdtipsup
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,Date feinival
			,List<Map<String,String>> incisos
			,UserVO usuarioSesion
			,FlujoVO flujo
			,String confirmar
			,String tipoflot
			,String cdperpag
			,String p_plan
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ confirmarEndosoValositFormsAuto @@@@@@"
				,"\n@@@@@@ cdusuari      = " , cdusuari
				,"\n@@@@@@ cdsisrol      = " , cdsisrol
				,"\n@@@@@@ cdelemen      = " , cdelemen
				,"\n@@@@@@ cdtipsup      = " , cdtipsup
				,"\n@@@@@@ cdunieco      = " , cdunieco
				,"\n@@@@@@ cdramo        = " , cdramo
				,"\n@@@@@@ estado        = " , estado
				,"\n@@@@@@ nmpoliza      = " , nmpoliza
				,"\n@@@@@@ feinival      = " , feinival
				,"\n@@@@@@ incisos       = " , incisos
				,"\n@@@@@@ usuarioSesion = " , usuarioSesion
				,"\n@@@@@@ flujo         = " , flujo
				,"\n@@@@@@ confirmar     = " ,confirmar
				,"\n@@@@@@ tipoflot      = " ,tipoflot
				,"\n@@@@@@ cdperpag      = " ,cdperpag
				,"\n@@@@@@ p_plan        = " ,p_plan
				));
		
		String paso = null;
		Map<String,String> valores = new HashMap<String,String>();
		try
		{
			String tstamp = Utils.generaTimestamp();
			
			paso = "Insertando situaciones temporales";
			logger.debug(paso);
			
			for(Map<String,String>inciso : incisos)
			{
				endososDAO.guardarTvalositEndoso(
						inciso.get("cdunieco")
						,inciso.get("cdramo")
						,inciso.get("estado")
						,inciso.get("nmpoliza")
						,inciso.get("nmsituac")
						,inciso.get("nmsuplem")
						,"V"
						,inciso.get("cdtipsit")
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
			logger.debug(paso);
			PropiedadesDeEndosoParaWS propWS = endososDAO.confirmarEndosoValositFormsAuto(
					cdusuari
					,cdsisrol
					,cdelemen
					,cdtipsup
					,cdunieco
					,cdramo
					,estado
					,nmpoliza
					,feinival
					,tstamp
					);
			
			Map<String,String> datosPoliza = consultasDAO.recuperarDatosPolizaParaDocumentos(cdunieco, cdramo, estado, nmpoliza);
			String ntramiteEmi = datosPoliza.get("ntramite");
			
			
			paso = "confirmarGuardandoDetallesTramiteEndoso";
			logger.debug(paso);
			String mensajeDespacho = this.confirmarGuardandoDetallesTramiteEndoso(
					ntramiteEmi
					,cdunieco
					,cdramo
					,estado
					,nmpoliza
					,propWS.getNmsuplem()
					,cdtipsup
					,propWS.getNsuplogi() //nsuplogi
					,null //dscoment
					,feinival
					,flujo
					,cdusuari
					,cdsisrol
					,false //confirmar
					);
			valores.put("mensajeDespacho", mensajeDespacho);
			
			/*Map<String,String> valores = new HashMap<String,String>();
			valores.put("otvalor01" , ntramiteEmi);
			valores.put("otvalor02" , cdtipsup);
			valores.put("otvalor03" , consultasDAO.recuperarDstipsupPorCdtipsup(cdtipsup));
			valores.put("otvalor04" , propWS.getNsuplogi());
			valores.put("otvalor05" , cdusuari);
			
			mesaControlDAO.movimientoMesaControl(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					,propWS.getNmsuplem()
					,cdunieco
					,cdunieco
					,TipoTramite.ENDOSO.getCdtiptra()
					,feinival
					,null //cdagente
					,null //referencia
					,null //nombre
					,feinival
					,EstatusTramite.ENDOSO_CONFIRMADO.getCodigo()
					,null //comments
					,null //nmsolici
					,null //cdtipsit
					,cdusuari
					,cdsisrol
					,null
					,null
					,null
					,valores, null
					);
			*/
			
			
					
					/**
					 * PARA LLAMAR WS SEGUN TIPO DE ENDOSO
					 */
					String nmsuplemGen = propWS.getNmsuplem();
					String ntramite = propWS.getNtramite();
					String tipoGrupoInciso = propWS.getTipoflot();
					
					Map<String,String> incisosAfectados = new HashMap<String, String>();
					
					for(Map<String,String> coberturasIncisos : incisos){
					
						String inciso = coberturasIncisos.get("nmsituac");
						
						if(StringUtils.isNotBlank(inciso)){
							incisosAfectados.put(inciso,inciso);
						}
					}
					
					if(("no").equals(confirmar)){
						Date fechaHoy = new Date();
						paso = "Realizando PDF de Vista Previa de Autos";
						logger.debug(paso);
						String reporteEndosoPrevia = rdfEndosoPreviewIndi;
						if(TipoFlotilla.Tipo_PyMES.getCdtipsit().equals(tipoflot)){
							reporteEndosoPrevia = rdfEndosoPreview;
						}
						
						String pdfEndosoNom = renderFechaHora.format(fechaHoy)+nmpoliza+"CotizacionPrevia.pdf";
						
						String url = rutaServidorReportes
								+ "?destype=cache"
								+ "&desformat=PDF"
								+ "&userid="+passServidorReportes
								+ "&report="+reporteEndosoPrevia
								+ "&paramform=no"
								+ "&ACCESSIBLE=YES" //parametro que habilita salida en PDF
								+ "&p_unieco="+cdunieco
								+ "&p_ramo="+cdramo
								+ "&p_estado="+estado
								+ "&p_poliza="+nmpoliza
								+ "&p_suplem="+nmsuplemGen
								+ "&p_plan="+p_plan
								+ "&p_perpag="+cdperpag
								+ "&desname="+rutaTempEndoso+"/"+pdfEndosoNom;
						
						paso = "Guardando PDF de Vista Previa de Autos en Temporal";
						logger.debug(paso);
						HttpUtil.generaArchivo(url,rutaTempEndoso+"/"+pdfEndosoNom);
						
						valores.put("pdfEndosoNom_o",pdfEndosoNom);
						
					}
					
					if(("si").equals(confirmar))
						{
						if(TipoEndoso.CAMBIO_TIPO_SERVICIO.getCdTipSup().toString().equalsIgnoreCase(cdtipsup)){
						
						paso = "Realizando endoso en Web Service Autos";
						logger.debug(paso);
						
						EmisionAutosVO aux = emisionAutosService.cotizaEmiteAutomovilWS(cdunieco, cdramo, estado, nmpoliza, nmsuplemGen, ntramite, null, usuarioSesion);
						if(aux == null || (StringUtils.isBlank(aux.getNmpoliex()) && !aux.isEndosoSinRetarif())){
							logger.error("Error al ejecutar los WS de endoso Cambio de Tipo Servicio");
							boolean endosoRevertido = endososManager.revierteEndosoFallido(cdunieco, cdramo, estado, nmpoliza, null, nmsuplemGen, (aux == null)? Integer.valueOf(99999) : aux.getResRecibos(), "Error en endoso auto, tipo: "+TipoEndoso.findByKey(Integer.valueOf(cdtipsup)), false);
							
							if(endosoRevertido){
								logger.error("Endoso revertido exitosamente.");
								throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. Favor de volver a intentar.");
							}else{
								logger.error("Error al revertir el endoso");
								throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. No se ha revertido el endoso.");
							}
						}
						
						if(aux.isEndosoSinRetarif()){
							
							if(this.endosoTipoServicio(cdunieco, cdramo, estado, nmpoliza, nmsuplemGen, ntramite, cdtipsup)){
								logger.info("Endoso de Tipo Servicio sin Tarificacion exitoso...");
							}else{
								logger.error("Error al ejecutar los WS de endoso de Tipo Servicio sin Tarificacion");
								boolean endosoRevertido = endososManager.revierteEndosoFallido(cdunieco, cdramo, estado, nmpoliza, null, nmsuplemGen, 88888, "Error en endoso B tipo: "+TipoEndoso.findByKey(Integer.valueOf(cdtipsup)), true);
								if(endosoRevertido){
									logger.error("Endoso revertido exitosamente.");
									throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. Favor de volver a intentar.");
								}else{
									logger.error("Error al revertir el endoso");
									throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. No se ha revertido el endoso.");
								}
							}
							
						}else if(aux.isExitoRecibos()){
							
							ejecutaCaratulaEndosoTarifaSigs(cdunieco, cdramo, estado, nmpoliza, nmsuplemGen, ntramite, cdtipsup, tipoGrupoInciso, aux);
						}else{
							logger.error("Error al ejecutar los WS de endoso Cambio de Tipo Servicio");
							boolean endosoRevertido = endososManager.revierteEndosoFallido(cdunieco, cdramo, estado, nmpoliza, null, nmsuplemGen, (aux == null)? Integer.valueOf(99999) : aux.getResRecibos(), "Error en endoso auto, tipo: "+TipoEndoso.findByKey(Integer.valueOf(cdtipsup)), false);
							
							if(endosoRevertido){
								logger.error("Endoso revertido exitosamente.");
								throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. Favor de volver a intentar.");
							}else{
								logger.error("Error al revertir el endoso");
								throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. No se ha revertido el endoso.");
							}
						}
						
					}else if(TipoEndoso.SUMA_ASEGURADA_INCREMENTO.getCdTipSup().toString().equalsIgnoreCase(cdtipsup) 
							|| TipoEndoso.SUMA_ASEGURADA_DECREMENTO.getCdTipSup().toString().equalsIgnoreCase(cdtipsup)
							|| TipoEndoso.DEDUCIBLE_MAS.getCdTipSup().toString().equalsIgnoreCase(cdtipsup) 
							|| TipoEndoso.DEDUCIBLE_MENOS.getCdTipSup().toString().equalsIgnoreCase(cdtipsup) ){
		
						paso = "Realizando endoso en Web Service Autos";
						logger.debug(paso);
						
						EmisionAutosVO aux = emisionAutosService.cotizaEmiteAutomovilWS(cdunieco, cdramo, estado, nmpoliza, nmsuplemGen, ntramite, null, usuarioSesion);
						
						Integer codigoErr = null;
						if(aux == null || !aux.isExitoRecibos()){
							
							if(aux == null){
								codigoErr = 99999;
							}else{
								codigoErr = aux.getResRecibos();
							}
							
							logger.error("Error al ejecutar los WS de endoso para tipo de endoso: " +  cdtipsup);
							boolean endosoRevertido = endososManager.revierteEndosoFallido(cdunieco, cdramo, estado, nmpoliza, null, nmsuplemGen, 
									codigoErr,
											"Error en endoso auto, tipo: "+
									TipoEndoso.findByKey(Integer.valueOf(cdtipsup)), false);
									
							if(aux!=null && aux.isEndosoSinRetarif()){
					    		throw new ApplicationException("Endoso sin Tarifa. "+(endosoRevertido?"Endoso revertido exitosamente.":"Error al revertir el endoso"));
					    	}
							
							if(endosoRevertido){
								logger.error("Endoso revertido exitosamente.");
								throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. Favor de volver a intentar.");
							}else{
								logger.error("Error al revertir el endoso");
								throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. No se ha revertido el endoso.");
							}
							
						}
						
						ejecutaCaratulaEndosoTarifaSigs(cdunieco, cdramo, estado, nmpoliza, nmsuplemGen, ntramite, cdtipsup, tipoGrupoInciso, aux);
					
					}
				}
					
					valores.put("pv_nmsuplem_o",propWS.getNmsuplem());
					valores.put("pv_nsuplogi_o",propWS.getNsuplogi());
					
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		
		logger.debug(Utils.log(
				 "\n@@@@@@ confirmarEndosoValositFormsAuto @@@@@@"
				,"\n@@@@@@ iniciarEndosoResp ==>", valores
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return valores;
	}
	
	public Map<String,Item> confirmarEndosoRehabilitacionPolAuto(String cdsisrol, String cdramo) throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ confirmarEndosoRehabilitacionPolAuto @@@@@@"
				,"\n@@@@@@ cdsisrol=" , cdsisrol
				,"\n@@@@@@ cdramo="   , cdramo
				));
		
		Map<String,Item> items = new HashMap<String,Item>();
		String           paso  = null;
		
		try
		{
			paso = "Construyendo componentes de pantalla";
			logger.debug(paso);
			ThreadCounter tc = new ThreadCounter(ServletActionContext.getServletContext().getServletContextName(),pantallasDAO);
			tc.agregarConstructor(new ConstructorComponentesAsync(
					"panelLectura" //llaveGenerador
					,null          //cdtiptra
					,null          //cdunieco
					,cdramo
					,null          //cdtipsit
					,null          //estado
					,cdsisrol
					,"ENDOSO_REHABILITACION_POLIZA"
					,"PANEL_LECTURA"
					,null          //orden
					,true          //parcial
					,false         //fields
					,true          //items
					,false         //columns
					,false         //editor
					,false         //buttons
					)
			);
			
			tc.agregarConstructor(new ConstructorComponentesAsync(
					"formEndoso" //llaveGenerador
					,null        //cdtiptra
					,null        //cdunieco
					,cdramo
					,null        //cdtipsit
					,null        //estado
					,cdsisrol
					,"ENDOSO_REHABILITACION_POLIZA"
					,"FORM_ENDOSO"
					,null        //orden
					,true        //parcial
					,false       //fields
					,true        //items
					,false       //columns
					,false       //editor
					,false       //buttons
					)
			);
			
			Map<String,GeneradorCampos>mapaGc = tc.run();
			
			items.put("panelLecturaItems" , mapaGc.get("panelLectura").getItems());
			items.put("formEndosoItems"   , mapaGc.get("formEndoso").getItems());
			
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ confirmarEndosoRehabilitacionPolAuto @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return items;
	}
	
	@Override
	public Map<String,String> marcarPolizaParaRehabilitar(String cdunieco,String cdramo,String nmpoliza) throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ marcarPolizaParaRehabilitar @@@@@@"
				,"\n@@@@@@ cdunieco=" , cdunieco
				,"\n@@@@@@ cdramo="   , cdramo
				,"\n@@@@@@ nmpoliza=" , nmpoliza
				));
		
		Map<String,String> poliza = null;
		String             paso   = "Marcando poliza para rehabilitar";
		
		logger.debug(paso);
		
		try
		{
			List<Map<String,String>> polizas = rehabilitacionDAO.buscarPolizas(
					null //asegurado
					,cdunieco
					,cdramo
					,nmpoliza
					,null //nmsituac
					);
			
			if(polizas.size()==0)
			{
				throw new ApplicationException("Esta poliza no se puede rehabilitar");
			}
			else if(polizas.size()>1)
			{
				throw new ApplicationException("Esta poliza se duplica al marcarse y no se puede rehabilitar");
			}
			
			poliza = polizas.get(0);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ poliza=",poliza
				,"\n@@@@@@ marcarPolizaParaRehabilitar @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return poliza;
	}
	
	@Override
	public void confirmarEndosoRehabilitacionPolAuto(
			String cdtipsup
			,String cdunieco
			,String cdramo 
			,String estado
			,String nmpoliza
			,Date feefecto
			,Date feproren
			,Date fecancel
			,Date feinival
			,String cdrazon
			,String cdperson
			,String cdmoneda
			,String nmcancel
			,String comments
			,String nmsuplem
			,UserVO usuarioSesion
			) throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ confirmarEndosoRehabilitacionPolAuto @@@@@@"
				,"\n@@@@@@ cdtipsup=" , cdtipsup
				,"\n@@@@@@ cdunieco=" , cdunieco
				,"\n@@@@@@ cdramo="   , cdramo
				,"\n@@@@@@ estado="   , estado
				,"\n@@@@@@ nmpoliza=" , nmpoliza
				,"\n@@@@@@ feefecto=" , feefecto
				,"\n@@@@@@ feproren=" , feproren
				,"\n@@@@@@ fecancel=" , fecancel
				,"\n@@@@@@ feinival=" , feinival
				,"\n@@@@@@ cdrazon="  , cdrazon
				,"\n@@@@@@ cdperson=" , cdperson
				,"\n@@@@@@ cdmoneda=" , cdmoneda
				,"\n@@@@@@ nmcancel=" , nmcancel
				,"\n@@@@@@ comments=" , comments
				,"\n@@@@@@ nmsuplem=" , nmsuplem
				));
		
		String paso = "Rehabilitando poliza";
		try
		{
			Map<String, Object> resParams = rehabilitacionDAO.rehabilitarPoliza(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					,feefecto
					,feproren
					,fecancel
					,feinival
					,cdrazon
					,cdperson
					,cdmoneda
					,nmcancel
					,comments
					,nmsuplem
					,usuarioSesion.getUser()
					,usuarioSesion.getRolActivo().getClave()
					);
			
			String nmsuplemGen = (String) resParams.get("pv_nmsuplem_o");
			String ntramite = (String) resParams.get("pv_ntramite_o");
			String tipoGrupoInciso = (String) resParams.get("pv_tipoflot_o");
			
			EmisionAutosVO aux = emisionAutosService.cotizaEmiteAutomovilWS(cdunieco, cdramo, estado, nmpoliza, nmsuplemGen, ntramite, null, usuarioSesion);
			if(aux == null || !aux.isExitoRecibos()){
				logger.error("Error al ejecutar los WS de endoso para rehabilitacion de poliza de auto");
				
				boolean endosoRevertido = endososManager.revierteEndosoFallido(cdunieco, cdramo, estado, nmpoliza, null, nmsuplemGen, (aux == null)? Integer.valueOf(99999) : aux.getResRecibos(), "Error en endoso auto, tipo: "+TipoEndoso.findByKey(Integer.valueOf(cdtipsup)), false);
				
				if(endosoRevertido){
					logger.error("Endoso revertido exitosamente.");
					throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. Favor de volver a intentar.");
				}else{
					logger.error("Error al revertir el endoso");
					throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. No se ha revertido el endoso.");
				}
				
			}
			
			ejecutaCaratulaEndosoTarifaSigs(cdunieco, cdramo, estado, nmpoliza, nmsuplemGen, ntramite, cdtipsup, tipoGrupoInciso, aux);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ confirmarEndosoRehabilitacionPolAuto @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	
	@Override
	public Map<String,String> guardarEndosoAmpliacionVigencia(
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
			,Date   feprorenOriginal
			,String nmsuplemOriginal
			,UserVO usuarioSesion
			,String tipoGrupoInciso
			,FlujoVO flujo
			,String cdsisrol
			,String confirmar
			,String tipoflot
			,String cdperpag
			,String p_plan
		)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ guardarEndosoAmpliacionVigencia @@@@@@"
				,"\n@@@@@@ cdunieco         = " , cdunieco
				,"\n@@@@@@ cdramo           = " , cdramo
				,"\n@@@@@@ estado           = " , estado
				,"\n@@@@@@ nmpoliza         = " , nmpoliza
				,"\n@@@@@@ cdelemen         = " , cdelemen
				,"\n@@@@@@ cdusuari         = " , cdusuari
				,"\n@@@@@@ cdtipsup         = " , cdtipsup
				,"\n@@@@@@ fechaEndoso      = " , fechaEndoso
				,"\n@@@@@@ dFechaEndoso     = " , dFechaEndoso
				,"\n@@@@@@ feefecto         = " , feefecto
				,"\n@@@@@@ feproren         = " , feproren
				,"\n@@@@@@ feprorenOriginal = " , feprorenOriginal
				,"\n@@@@@@ nmsuplemOriginal = " , nmsuplemOriginal
				,"\n@@@@@@ cdsisrol         = " , cdsisrol
				,"\n@@@@@@ flujo            = " , flujo
				,"\n@@@@@@ confirmar        = " , confirmar
				,"\n@@@@@@ TIPOFLOT         = " , tipoflot
				,"\n@@@@@@ cdperpag         = " , cdperpag
				,"\n@@@@@@ p_plan           = " , p_plan
				));
		
		String paso = "";
		
		try
		{
			paso = "Iniciando endoso";
			logger.debug(paso);
			
			iniciarEndosoResp=endososDAO.iniciarEndoso(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					,feprorenOriginal
					,cdelemen
					,cdusuari
					,"END"
					,cdtipsup);
			String nmsuplem = iniciarEndosoResp.get("pv_nmsuplem_o");
			String nsuplogi = iniciarEndosoResp.get("pv_nsuplogi_o");
			
			paso = "Registra los valores en mpolizas";
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
			
			paso = "Registra los valores en tworksup";
			logger.debug(paso);
			Map<String,String>mapaTworksupEnd=new LinkedHashMap<String,String>(0);
			mapaTworksupEnd.put("pv_cdunieco_i" , cdunieco);
			mapaTworksupEnd.put("pv_cdramo_i"   , cdramo);
			mapaTworksupEnd.put("pv_estado_i"   , estado);
			mapaTworksupEnd.put("pv_nmpoliza_i" , nmpoliza);
			mapaTworksupEnd.put("pv_cdtipsup_i" , cdtipsup);
			mapaTworksupEnd.put("pv_nmsuplem_i" , nmsuplem);
			endososDAO.insertarTworksupSitTodas(mapaTworksupEnd);
			
			paso = "Registra los valores de tarificacion sigsvalipolEnd";
			logger.debug(paso);
			endososDAO.sigsvalipolEnd(
					cdusuari
					,cdelemen
					,cdunieco
					,cdramo
					,estado
					,nmpoliza
					,"0"
					,nmsuplem
					,cdtipsup
					);
			
			paso = "Se calcula los valores de Endoso";
			logger.debug(paso);
			Map<String,Object>mapaValorEndoso=new LinkedHashMap<String,Object>(0);
			mapaValorEndoso.put("pv_cdunieco_i" , cdunieco);
			mapaValorEndoso.put("pv_cdramo_i"   , cdramo);
			mapaValorEndoso.put("pv_estado_i"   , estado);
			mapaValorEndoso.put("pv_nmpoliza_i" , nmpoliza);
			mapaValorEndoso.put("pv_nmsituac_i" , "1");
			mapaValorEndoso.put("pv_nmsuplem_i" , nmsuplem);
			mapaValorEndoso.put("pv_feinival_i" , dFechaEndoso);
			mapaValorEndoso.put("pv_cdtipsup_i" , cdtipsup);
			endososDAO.calcularValorEndoso(mapaValorEndoso);
			
			String mensajeDespacho = this.confirmarGuardandoDetallesTramiteEndoso(
					ntramite
					,cdunieco
					,cdramo
					,estado
					,nmpoliza
					,nmsuplem
					,cdtipsup
					,nsuplogi
					,null //dscoment
					,dFechaEndoso
					,flujo
					,cdusuari
					,cdsisrol
					,true
					);
			iniciarEndosoResp.put("mensajeDespacho", mensajeDespacho);

			String nmsuplemGen = nmsuplem;
			if(("no").equals(confirmar)){
				Date fechaHoy = new Date();
				paso = "Realizando PDF de Vista Previa de Autos";
				logger.debug(paso);
				String reporteEndosoPrevia = rdfEndosoPreviewIndi;
				if(TipoFlotilla.Tipo_PyMES.getCdtipsit().equals(tipoflot)){
					reporteEndosoPrevia = rdfEndosoPreview;
				}
				
				String pdfEndosoNom = renderFechaHora.format(fechaHoy)+nmpoliza+"CotizacionPrevia.pdf";
				
				String url = rutaServidorReportes
						+ "?destype=cache"
						+ "&desformat=PDF"
						+ "&userid="+passServidorReportes
						+ "&report="+reporteEndosoPrevia
						+ "&paramform=no"
						+ "&ACCESSIBLE=YES" //parametro que habilita salida en PDF
						+ "&p_unieco="+cdunieco
						+ "&p_ramo="+cdramo
						+ "&p_estado="+estado
						+ "&p_poliza="+nmpoliza
						+ "&p_suplem="+nmsuplemGen
						+ "&p_plan="+p_plan
						+ "&p_perpag="+cdperpag
						+ "&desname="+rutaTempEndoso+"/"+pdfEndosoNom;
				
				paso = "Guardando PDF de Vista Previa de Autos en Temporal";
				logger.debug(paso);
				HttpUtil.generaArchivo(url,rutaTempEndoso+"/"+pdfEndosoNom);
				
				iniciarEndosoResp.put("pdfEndosoNom_o",pdfEndosoNom);
				
			}
			
			if(("si").equals(confirmar)){
						
					paso = "Enviando a Web Service Sigs";
					logger.debug(paso);
					
					EmisionAutosVO aux = emisionAutosService.cotizaEmiteAutomovilWS(cdunieco, cdramo, estado, nmpoliza, nmsuplemGen, ntramite, null, usuarioSesion);
					if(aux == null || !aux.isExitoRecibos()){
						logger.error("Error al ejecutar los WS de endoso para Ampliacion de Vigencia");
						
						boolean endosoRevertido = endososManager.revierteEndosoFallido(cdunieco, cdramo, estado, nmpoliza, null, nmsuplemGen, (aux == null)? Integer.valueOf(99999) : aux.getResRecibos(), "Error en endoso auto, tipo: "+TipoEndoso.findByKey(Integer.valueOf(cdtipsup)), false);
						
						if(aux!=null && aux.isEndosoSinRetarif()){
				    		throw new ApplicationException("Endoso sin Tarifa. "+(endosoRevertido?"Endoso revertido exitosamente.":"Error al revertir el endoso"));
				    	}
						
						if(endosoRevertido){
							logger.error("Endoso revertido exitosamente.");
							throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. Favor de volver a intentar.");
						}else{
							logger.error("Error al revertir el endoso");
							throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. No se ha revertido el endoso.");
						}
						
					}
					
					paso = "Ejecutando caratula";
					logger.debug(paso);
					
					ejecutaCaratulaEndosoTarifaSigs(cdunieco, cdramo, estado, nmpoliza, nmsuplemGen, ntramite, cdtipsup, tipoGrupoInciso, aux);
				
			}		
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ guardarEndosoAmpliacionVigencia @@@@@@"
				,"\n@@@@ iniciarEndosoResp--> ",iniciarEndosoResp
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return iniciarEndosoResp;
	}
	
	@Override
	public List<Map<String,String>> obtieneRecibosDespagados(String cdunieco, String cdramo ,String estado ,String nmpoliza) throws Exception {
		return endososDAO.obtieneRecibosDespagados(cdunieco, cdramo, estado, nmpoliza);
	}
	
	@Override
	public void guardarEndosoRehabilitacionDespago(
			 String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmrecibo
			,String nmimpres
			,String cdtipsup
			,UserVO usuarioSesion
			)throws Exception
	{
		logger.debug(Utils.log(
				"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ guardarEndosoRehabilitacionDespago @@@@@@"
				,"\n@@@@@@ cdunieco      = " , cdunieco
				,"\n@@@@@@ cdramo        = " , cdramo
				,"\n@@@@@@ estado        = " , estado
				,"\n@@@@@@ nmpoliza      = " , nmpoliza
				,"\n@@@@@@ nmsuplem      = " , nmsuplem
				,"\n@@@@@@ nmrecibo      = " , nmrecibo
				,"\n@@@@@@ nmimpres      = " , nmimpres
				,"\n@@@@@@ cdtipsup      = " , cdtipsup
				));
		
		String paso = null;
		
		try
		{
			
			String tstamp = Utils.generaTimestamp();
			
			paso = "Guardando recibo despago";
			logger.debug(paso);
			
			Map<String,Object> resParams = endososDAO.guardaEndosoDespago(
					     cdunieco
						,cdramo
						,estado
						,nmpoliza
						,nmsuplem
						,nmrecibo
						,nmimpres
						,usuarioSesion.getUser()
						,cdtipsup
						);
			
			String nmsuplemGen     = (String) resParams.get("pv_nmsuplem_o");
			String ntramite        = (String) resParams.get("pv_ntramite_o");
			String tipoGrupoInciso = (String) resParams.get("pv_tipoflot_o");
			
			boolean esProductoSalud = consultasDAO.esProductoSalud(cdramo);
			
			if(esProductoSalud) {
				paso = "Enviando a Web Service para Recibos de Salud";
				logger.debug(paso);
				
				// Ejecutamos el Web Service de Recibos:
				ice2sigsService.ejecutaWSrecibos(cdunieco, cdramo, 
						estado, nmpoliza, 
						nmsuplemGen, null, 
						cdunieco, "0", ntramite, 
						true, cdtipsup, 
						usuarioSesion);
			}else{
				paso = "Enviando a Web Service Sigs";
				logger.debug(paso);
				
				EmisionAutosVO aux = emisionAutosService.cotizaEmiteAutomovilWS(cdunieco, cdramo, estado, nmpoliza, nmsuplemGen, ntramite, null, usuarioSesion);
				if(aux == null || !aux.isExitoRecibos()){
					logger.error("Error al ejecutar los WS de endoso para rehabilitcion de despago");
					
					boolean endosoRevertido = endososManager.revierteEndosoFallido(cdunieco, cdramo, estado, nmpoliza, null, nmsuplemGen, (aux == null)? Integer.valueOf(99999) : aux.getResRecibos(), "Error en endoso auto, tipo: "+TipoEndoso.findByKey(Integer.valueOf(cdtipsup)), false);
					
					if(endosoRevertido){
						logger.error("Endoso revertido exitosamente.");
						throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. Favor de volver a intentar.");
					}else{
						logger.error("Error al revertir el endoso");
						throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. No se ha revertido el endoso.");
					}
					
				}
				
				paso = "Ejecutando caratula";
				logger.debug(paso);
				
				ejecutaCaratulaEndosoTarifaSigs(cdunieco, cdramo, estado, nmpoliza, nmsuplemGen, ntramite, cdtipsup, tipoGrupoInciso, aux);
			}
			
			
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				"\n@@@@@@ guardarEndosoRehabilitacionDespago @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
			}

	@Override
	public void guardarEndosoNombreRFCFecha(String cdunieco, String cdramo, String estado, String nmpoliza,
			String cdperson, String cdtipide, String cdideper, String dsnombre, String cdtipper, String otfisjur,
			String otsexo, Date fechaNacimiento, String cdrfc, String dsemail, String dsnombre1, String dsapellido,
			String dsapellido1, String feingreso, String cdnacion, String canaling, String conducto, String ptcumupr,
			String residencia, String nongrata, String cdideext, String cdestciv, String cdsucemi, String cdusuari,
			String cdsisrol, String cdelemen, String cdtipsup, String fechaEndoso, Date dFechaEndoso) throws Exception {
		// TODO Auto-generated method stub
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ guardarEndosoNombreRFCFecha @@@@@@"
		));
		
		ManagerRespuestaVoidVO resp=new ManagerRespuestaVoidVO(true);
		String paso = "";
		
		try
		{
				paso = "Iniciando endoso";
				logger.debug(paso);
				
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
				
				paso = "Actualizamos los valores del contratante";
				logger.debug(paso);
				
				Map<String,Object>params=new HashMap<String,Object>();
				params.put("pv_cdperson_i"    , cdperson);
				params.put("pv_cdtipide_i"    , cdtipide);
				params.put("pv_cdideper_i"    , cdideper);
				params.put("pv_dsnombre_i"    , dsnombre);
				params.put("pv_cdtipper_i"    , cdtipper);
				params.put("pv_otfisjur_i"    , otfisjur);
				params.put("pv_otsexo_i"      , otsexo);
				params.put("pv_fenacimi_i"    , fechaNacimiento);
				params.put("pv_cdrfc_i"       , cdrfc);
				params.put("pv_dsemail_i"     , dsemail);
				params.put("pv_dsnombre1_i"   , dsnombre1);
				params.put("pv_dsapellido_i"  , dsapellido);
				params.put("pv_dsapellido1_i" , dsapellido1);
				params.put("pv_feingreso_i"   , feingreso);
				params.put("pv_cdnacion_i"    , cdnacion);
				params.put("pv_canaling_i"    , canaling);
				params.put("pv_conducto_i"    , conducto);
				params.put("pv_ptcumupr_i"    , ptcumupr);
				params.put("pv_residencia_i"  , residencia);
				params.put("pv_nongrata_i"    , nongrata);
				params.put("pv_cdideext_i"    , cdideext);
				params.put("pv_cdestciv_i"    , cdestciv);
				params.put("pv_cdsucemi_i"    , cdsucemi);
				params.put("pv_accion_i"      , "U" );
				logger.debug("EndososManager datos params: "+params);
			endososDAO.guardarEndosoNombreRFCFecha(params);

				paso = "Se confirma el endoso";
				logger.debug(paso);
				
				endososDAO.confirmarEndosoB(cdunieco,cdramo,estado,nmpoliza,nmsuplem, nsuplogi, cdtipsup, null);
				
				paso = "Obtenemos los datos a enviar al SP";
				logger.debug(paso);
				List<Map<String,String>>  informacionCliente = endososDAO.obtieneInformacionCliente(cdunieco,cdramo,estado,nmpoliza,nmsuplem,"79");
				
				if(informacionCliente != null && !informacionCliente.isEmpty()){
					HashMap<String, Object> paramsEnd = new HashMap<String, Object>();
						Map<String,String> datosEnIt = informacionCliente.get(0);
						paramsEnd.put("vNumSuc"  , datosEnIt.get("NUMSUC"));
						paramsEnd.put("vRamo"  , datosEnIt.get("RAMO"));
						paramsEnd.put("vPoliza"  , datosEnIt.get("POLIZA"));
						paramsEnd.put("vFEndoso"  , datosEnIt.get("FENDOSO"));
						paramsEnd.put("vCliente"  , datosEnIt.get("CLIENTE"));
						paramsEnd.put("vMotivo"  , datosEnIt.get("IDMOTIVO"));
						paramsEnd.put("vNomCli"  , datosEnIt.get("NOMCLI"));
						paramsEnd.put("vApePat"  , datosEnIt.get("APEPAT"));
						paramsEnd.put("vApeMat"  , datosEnIt.get("APEMAT"));
						paramsEnd.put("vRazSoc"  , datosEnIt.get("RASONSOCIAL"));
						paramsEnd.put("vFecNac"  , datosEnIt.get("FECNAC"));
						paramsEnd.put("vTipPer"  , datosEnIt.get("TIPPER"));
						paramsEnd.put("vRfcCli"  , datosEnIt.get("RFCCLI"));
						paramsEnd.put("vCveEle"  , datosEnIt.get("CVEELE"));
						paramsEnd.put("vCurpCli"  , datosEnIt.get("CURPCLI"));
						paramsEnd.put("vCalleCli"  , datosEnIt.get("CALLECLI"));
						paramsEnd.put("vNumCli"  , datosEnIt.get("NUMCLI"));
						paramsEnd.put("vNumInt"  , datosEnIt.get("NUMINT"));
						paramsEnd.put("vCodPos"  , datosEnIt.get("CODPOS"));
						paramsEnd.put("vColonia"  , datosEnIt.get("COLONIA"));
						paramsEnd.put("vMunicipio"  , datosEnIt.get("MUNICIPIO"));
						paramsEnd.put("vCveEdo"  , datosEnIt.get("CVEEDO"));
						paramsEnd.put("vPoblacion"  , datosEnIt.get("POBLACION"));
						paramsEnd.put("vTelefono1"  , datosEnIt.get("TELEFONO1"));
						paramsEnd.put("vTelefono2"  , datosEnIt.get("TELEFONO2"));
						paramsEnd.put("vTelefono3"  , datosEnIt.get("TELEFONO3"));
						
					try{
					int endosoRecuperado = -1;
					Integer res = autosDAOSIGS.CambioClientenombreRFCfechaNacimiento(paramsEnd);
					
					logger.debug("Respuesta de Cambio AseguradoAlterno numero de endoso =========> : " + res);
					
					if(res == null || res == 0 || res == -1){
						logger.debug("Endoso Cambio AseguradoAlterno no exitoso");
						boolean endosoRevertido = endososManager.revierteEndosoFallido(cdunieco, cdramo, estado, nmpoliza, nsuplogi, nmsuplem, res, "Error en endoso de actualizacion de nombre, rfc y fecha nacimiento", true);
							if(endosoRevertido){
								logger.error("Endoso revertido exitosamente.");
							}else{
								logger.error("Error al revertir el endoso");
							}
						}else{
						endosoRecuperado = res.intValue();
						
							Map<String,String> valores = new LinkedHashMap<String,String>();
							valores.put("otvalor01" , null);
							valores.put("otvalor02" , cdtipsup);
							valores.put("otvalor03" , null);
							valores.put("otvalor04" , nsuplogi);
							valores.put("otvalor05" , cdusuari);
							
							String ntramiteGenerado = mesaControlManager.movimientoTramite(
									cdunieco
									,cdramo
									,estado
									,nmpoliza
									,nmsuplem
									,cdunieco
									,cdunieco
									,TipoTramite.ENDOSO.getCdtiptra()+""
									,dFechaEndoso
									,""
									,""
									,""
									, dFechaEndoso
									,EstatusTramite.CONFIRMADO.getCodigo()+""
									,""
									,null
									,null
									,cdusuari
									,cdsisrol
									,null //swimpres
									,null //cdtipflu
									,null //cdflujomc
									,valores
									, null
									);
							
							logger.debug("Tramite Generado ====>"+ntramiteGenerado);
						
						}
						
					} catch (Exception e){
						logger.error("Error en Envio Cambio AseguradoAlterno Auto: " + e.getMessage(),e);
					}
				}else{
					logger.error("Aviso, No se tienen datos de Cambio AseguradoAlterno");
				}
			
			
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		logger.debug(Utils.log(
				"\n@@@@@@ " , resp
				,"\n@@@@@@ guardarEndosoNombreRFCFecha @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
		));
		
	}
	
	
	
	private String confirmarGuardandoDetallesTramiteEndoso(
			String ntramiteEmision
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplemEndoso
			,String cdtipsup
			,String nsuplogi
			,String dscoment
			,Date dFechaEndoso
			,FlujoVO flujo
			,String cdusuari
			,String cdsisrol
			,boolean confirmar
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ confirmarGuardandoDetallesTramiteEndoso @@@@@@"
				,"\n@@@@@@ ntramiteEmision = " , ntramiteEmision
				,"\n@@@@@@ cdunieco        = " , cdunieco
				,"\n@@@@@@ cdramo          = " , cdramo
				,"\n@@@@@@ estado          = " , estado
				,"\n@@@@@@ nmpoliza        = " , nmpoliza
				,"\n@@@@@@ nmsuplemEndoso  = " , nmsuplemEndoso
				,"\n@@@@@@ cdtipsup        = " , cdtipsup
				,"\n@@@@@@ nsuplogi        = " , nsuplogi
				,"\n@@@@@@ dscoment        = " , dscoment
				,"\n@@@@@@ dFechaEndoso    = " , dFechaEndoso
				,"\n@@@@@@ flujo           = " , flujo
				,"\n@@@@@@ cdusuari        = " , cdusuari
				,"\n@@@@@@ cdsisrol        = " , cdsisrol
				,"\n@@@@@@ confirmar       = " , confirmar
				));
		
		String paso = null;
		String mensaje = null;
		
		try
		{
		    Date fechaHoy = new Date();
			if(confirmar)
			{
				paso = "Confirmando endoso";
				logger.debug(paso);
				
				endososDAO.confirmarEndosoB(cdunieco,cdramo,estado,nmpoliza,nmsuplemEndoso, nsuplogi, cdtipsup, null);
			}
			
			if(flujo == null || consultasDAO.esProductoSalud(cdramo))
			{
				paso = "Generando tr\u00e1mite";
				logger.debug(paso);
				
				Map<String,String> valores = new HashMap<String,String>();
				valores.put("otvalor01" , ntramiteEmision);
				valores.put("otvalor02" , cdtipsup);
				valores.put("otvalor03" , consultasDAO.recuperarDstipsupPorCdtipsup(cdtipsup));
				valores.put("otvalor04" , nsuplogi);
				valores.put("otvalor05" , cdusuari);
				
				Map<String, String> datosTipoTramite = consultasDAO.recuperarDatosFlujoEndoso(cdramo, cdtipsup);
                String cdtipflu  = datosTipoTramite.get("cdtipflu"),
                       cdflujomc = datosTipoTramite.get("cdflujomc");
				
				String ntramite = mesaControlDAO.movimientoMesaControl(
						cdunieco
						,cdramo
						,estado
						,nmpoliza
						,nmsuplemEndoso
						,cdunieco
						,cdunieco
						,TipoTramite.ENDOSO.getCdtiptra()
						,fechaHoy
						,null //cdagente
						,null //referencia
						,null //nombre
						,fechaHoy
						,EstatusTramite.ENDOSO_CONFIRMADO.getCodigo()
						,dscoment
						,null //nmsolici
						,null //cdtipsit
						,cdusuari
						,cdsisrol
						,null
						,cdtipflu
						,cdflujomc
						,valores
						,cdtipsup
						);
                
			}
			else
			{
				paso = "Actualizando estatus de tr\u00e1mite de endoso";
				logger.debug(paso);
				/*
				paso = "Actualizar suplemento del tr\u00e1mite";
				logger.debug(paso);
				
				mesaControlDAO.actualizarNmsuplemTramite(flujo.getNtramite(),nmsuplemEndoso);
				
				paso = "Actualizando atributos variables de tr\u00e1mite";
				logger.debug(paso);
				
				mesaControlDAO.actualizarOtvalorTramitePorDsatribu(
						flujo.getNtramite()
						,"MITE%EMISI"
						,ntramiteEmision
						,Constantes.UPDATE_MODE
						);
				
				mesaControlDAO.actualizarOtvalorTramitePorDsatribu(
						flujo.getNtramite()
						,"CDTIPSUP"
						,cdtipsup
						,Constantes.UPDATE_MODE
						);
				
				mesaControlDAO.actualizarOtvalorTramitePorDsatribu(
						flujo.getNtramite()
						,"DSTIPSUP"
						,consultasDAO.recuperarDstipsupPorCdtipsup(cdtipsup)
						,Constantes.UPDATE_MODE
						);
				
				mesaControlDAO.actualizarOtvalorTramitePorDsatribu(
						flujo.getNtramite()
						,"NSUPLOGI"
						,nsuplogi
						,Constantes.UPDATE_MODE
						);
				
				mesaControlDAO.actualizarOtvalorTramitePorDsatribu(
						flujo.getNtramite()
						,"CDUSUARI"
						,cdusuari
						,Constantes.UPDATE_MODE
						);
				*/
			}
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
		        "\n@@@@@@ mensaje = ", mensaje,
				"\n@@@@@@ confirmarGuardandoDetallesTramiteEndoso @@@@@@",
				"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return mensaje;
	}
	
	
	   @Override
	    public Map<String,Item> pantallaBeneficiariosAutoVida(String cdunieco,String cdramo,String estado,String cdsisrol,String cdtipsup)throws Exception
            {
                logger.debug(Utils.log(""
                        ,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
                        ,"\n@@@@@@ pantallaBeneficiariosAutoVida @@@@@@"
                        ,"\n@@@@@@ cdunieco=" , cdunieco
                        ,"\n@@@@@@ cdramo="   , cdramo
                        ,"\n@@@@@@ estado="   , estado
                        ,"\n@@@@@@ cdsisrol=" , cdsisrol
                        ,"\n@@@@@@ cdtipsup=" , cdtipsup
                        ));
                
                Map<String,Item> items = new HashMap<String,Item>();
                
                String paso = null;
                
                try
                {
                    paso = "Validando suplemento permitido";
                    logger.debug(Utils.log("","paso=",paso));
                    
                    try
                    {
                        Map<String,String>permiso=endososDAO.obtenerParametrosEndoso(
                                ParametroEndoso.ENDOSO_PERMITIDO
                                ,cdramo
                                ,"x"
                                ,cdtipsup
                                ,null);
                        if(!permiso.get("P1VALOR").equals("SI"))
                        {
                            throw new ApplicationException("No esta permitido");
                        }
                    }
                    catch(Exception ex)
                    {
                        throw new ApplicationException("Suplemento no aplicable al producto");
                    }
                    
                    paso = "Recuperando componentes relacion poliza-persona";
                    logger.debug(Utils.log("","paso=",paso));
                    
                    List<ComponenteVO>componentesMpoliper=pantallasDAO.obtenerComponentes(
                            null                     //cdtiptra
                            ,cdunieco
                            ,"|"+cdramo+"|"
                            ,null                    //cdtipsit
                            ,estado
                            ,cdsisrol
                            ,"PANTALLA_BENEFICIARIOS"//pantalla
                            ,"PANEL_LECTURA"              //seccion
                            ,null                    //orden
                            );
                    
                    paso = "Recuperando componentes persona";
                    logger.debug(Utils.log("","paso=",paso));
                    
                    List<ComponenteVO>componentesMpersona=pantallasDAO.obtenerComponentes(
                            null                     //cdtiptra
                            ,cdunieco
                            ,cdramo
                            ,null                    //cdtipsit
                            ,estado
                            ,cdsisrol
                            ,"PANTALLA_BENEFICIARIOS"//pantalla
                            ,"MPERSONA"              //seccion
                            ,null                    //orden
                            );
                    
                    GeneradorCampos gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
                    
                    paso = "Construyendo componentes relacion poliza-persona";
                    logger.debug(Utils.log("","paso=",paso));
                    
                    gc.generaComponentes(componentesMpoliper
                            ,true  //parcial
                            ,true  //conField
                            ,false //conItem
                            ,true  //conColumn
                            ,true  //conEditor
                            ,false //conButton
                            );
                    
                    items.put("mpoliperFields"  , gc.getFields());
                    items.put("mpoliperColumns" , gc.getColumns());
                    
                }
                catch(Exception ex)
                {
                    Utils.generaExcepcion(ex, paso);
                }
                
                logger.debug(Utils.log(""
                        ,"\n@@@@@@ items=", items
                        ,"\n@@@@@@ pantallaBeneficiariosAutoVida @@@@@@"
                        ,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
                        ));
                return items;
}

	   @Override
	    public List<Map<String,String>> obtieneBeneficiariosVidaAuto(String cdunieco, String cdramo ,String estado ,String nmpoliza) throws Exception {
	       logger.debug(Utils.log(""
                   ,"\n cdunieco = ", cdunieco
                   ,"\n cdramo   = ", cdramo
                   ,"\n estado   = ", estado
                   ,"\n nmpoliza = ", nmpoliza
                   ,"\n pantallaBeneficiariosAutoVida "
                   ));
	       return endososDAO.obtieneBeneficiarioVidaAuto(cdunieco, cdramo, estado, nmpoliza);
	    }
	   
	    @Override
	    public ManagerRespuestaVoidVO confirmaEndosoBeneficiariosVidaAuto(
	            String cdtipsup
	            ,String tstamp
	            ,String cdunieco
	            ,String cdramo
	            ,String estado
	            ,String nmpoliza
	            ,String nmsituac
	            ,String feefecto
	            ,String cdusuari
	            ,String cdsisrol
	            ,String cdelemen
	            ,UserVO usuarioSesion
	            ,List<Map<String,String>> mpoliperMpersona
	            ,FlujoVO flujo
	            )throws Exception
	    {
	        logger.debug(Utils.log(
	                 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
	                ,"\n@@@@@@ confirmaEndosoBeneficiariosVidaAuto @@@@@@"
	                ,"\n@@@@@@ cdtipsup=" , cdtipsup
	                ,"\n@@@@@@ tstamp="   , tstamp
	                ,"\n@@@@@@ cdunieco=" , cdunieco
	                ,"\n@@@@@@ cdramo="   , cdramo
	                ,"\n@@@@@@ estado="   , estado
	                ,"\n@@@@@@ nmpoliza=" , nmpoliza
	                ,"\n@@@@@@ nmsituac=" , nmsituac
	                ,"\n@@@@@@ feefecto=" , feefecto
	                ,"\n@@@@@@ cdusuari=" , cdusuari
	                ,"\n@@@@@@ cdsisrol=" , cdsisrol
	                ,"\n@@@@@@ cdelemen=" , cdelemen
	                ,"\n@@@@@@ incisos="  , mpoliperMpersona
	                ,"\n@@@@@@ flujo="    , flujo
	                ));
	        
	        ManagerRespuestaVoidVO resp=new ManagerRespuestaVoidVO(true);
	        
	        String paso = null;
	        
	        try
	        {
	            String usuarioCaptura =  null;
	            
	            if(usuarioSesion!=null){
	                if(StringUtils.isNotBlank(usuarioSesion.getClaveUsuarioCaptura())){
	                    usuarioCaptura = usuarioSesion.getClaveUsuarioCaptura();
	                }else{
	                    usuarioCaptura = usuarioSesion.getCodigoPersona();
	                }
	                
	            }
	            
	            //Date fechaEndoso = new Date();
	            
	            paso = "Iniciando endoso";
	            logger.debug(paso);
	            
	            Map<String,Object> resParams = endososDAO.confirmarEndosoTvalositAuto(
                        cdtipsup
                        ,tstamp
                        ,cdunieco
                        ,cdramo
                        ,estado
                        ,nmpoliza
                        ,renderFechas.parse(feefecto)
                        ,cdusuari
                        ,cdsisrol
                        ,cdelemen
                        );
                
                String nmsuplem        = (String)resParams.get("pv_nmsuplem_o");
                String ntramite        = (String)resParams.get("pv_ntramite_o");
                String tipoGrupoInciso = (String)resParams.get("pv_tipoflot_o");
                String nsuplogi        = (String)resParams.get("pv_nsuplogi_o");
                
	            
	            paso = "Iterando registros";
	            logger.debug(paso);
	            
	            for(Map<String,String>rec:mpoliperMpersona)
	            {
	                String mov    = rec.get("mov");
	                int agregar   = 1;
	                int eliminar  = 2;
	                int operacion = 0;
	                if(StringUtils.isNotBlank(mov))
	                {
	                    if(mov.equals("+"))
	                    {
	                        operacion=agregar;
	                    }
	                    else if(mov.equals("-"))
	                    {
	                        operacion=eliminar;
	                    }
	                }
	                
	                if(operacion==agregar)
	                {
	                    personasDAO.movimientosMpersona(
	                            rec.get("CDPERSON")
	                            ,rec.get("CDTIPIDE")
	                            ,rec.get("CDIDEPER")
	                            ,rec.get("DSNOMBRE")
	                            ,rec.get("CDTIPPER")
	                            ,rec.get("OTFISJUR")
	                            ,rec.get("OTSEXO")
	                            ,StringUtils.isNotBlank(rec.get("FENACIMI"))?
	                                    Utils.parse(rec.get("FENACIMI"))
	                                    :null
	                            ,rec.get("CDRFC")
	                            ,rec.get("DSEMAIL")
	                            ,rec.get("DSNOMBRE1")
	                            ,rec.get("DSAPELLIDO")
	                            ,rec.get("DSAPELLIDO1")
	                            ,renderFechas.parse(feefecto)//fechaEndoso
	                            ,rec.get("CDNACION")
	                            ,rec.get("CANALING")
	                            ,rec.get("CONDUCTO")
	                            ,rec.get("PTCUMUPR")
	                            ,rec.get("RESIDENCIA")
	                            ,rec.get("NONGRATA")
	                            ,rec.get("CDIDEEXT")
	                            ,rec.get("CDESTCIV")
	                            ,rec.get("CDSUCEMI")
	                            ,Constantes.INSERT_MODE);
	                    
	                    endososDAO.movimientoMpoliperBeneficiario(
	                            cdunieco
	                            ,cdramo
	                            ,estado
	                            ,nmpoliza
	                            ,"0"//nmsituac
	                            ,"3"
	                            ,rec.get("CDPERSON")
	                            ,nmsuplem
	                            ,"V"
	                            ,rec.get("NMORDDOM")
	                            ,rec.get("SWRECLAM")
	                            ,"N" //swexiper
	                            ,rec.get("CDPARENT")
	                            ,rec.get("PORBENEF")
	                            ,"I"
	                            );
	                }
	                else if(operacion==eliminar)
	                {
	                    endososDAO.movimientoMpoliperBeneficiario(
	                            cdunieco
	                            ,cdramo
	                            ,estado
	                            ,nmpoliza
	                            ,"0"//nmsituac
	                            ,"3"//rec.get("CDROL")
	                            ,rec.get("CDPERSON")
	                            ,nmsuplem
	                            ,"V"//rec.get("STATUS")
	                            ,rec.get("NMORDDOM")
	                            ,rec.get("SWRECLAM")
	                            ,rec.get("SWEXIPER")
	                            ,rec.get("CDPARENT")
	                            ,rec.get("PORBENEF")
	                            ,"B"
	                            );
	                    
	                    personasDAO.movimientosMpersona(
	                            rec.get("CDPERSON")
	                            ,rec.get("CDTIPIDE")
	                            ,rec.get("CDIDEPER")
	                            ,rec.get("DSNOMBRE")
	                            ,rec.get("CDTIPPER")
	                            ,rec.get("OTFISJUR")
	                            ,rec.get("OTSEXO")
	                            ,StringUtils.isNotBlank(rec.get("FENACIMI"))?
	                                    Utils.parse(rec.get("FENACIMI"))
	                                    :null
	                            ,rec.get("CDRFC")
	                            ,rec.get("DSEMAIL")
	                            ,rec.get("DSNOMBRE1")
	                            ,rec.get("DSAPELLIDO")
	                            ,rec.get("DSAPELLIDO1")
	                            ,renderFechas.parse(feefecto)//fechaEndoso
	                            ,rec.get("CDNACION")
	                            ,rec.get("CANALING")
	                            ,rec.get("CONDUCTO")
	                            ,rec.get("PTCUMUPR")
	                            ,rec.get("RESIDENCIA")
	                            ,rec.get("NONGRATA")
	                            ,rec.get("CDIDEEXT")
	                            ,rec.get("CDESTCIV")
	                            ,rec.get("CDSUCEMI")
	                            ,"B");
	                }
	                else
	                {
	                    endososDAO.movimientoMpoliperBeneficiario(
	                            cdunieco
	                            ,cdramo
	                            ,estado
	                            ,nmpoliza
	                            ,"0"//nmsituac
	                            ,"3"//rec.get("CDROL")
	                            ,rec.get("CDPERSON")
	                            ,nmsuplem
	                            ,"V"
	                            ,rec.get("NMORDDOM")
	                            ,rec.get("SWRECLAM")
	                            ,rec.get("SWEXIPER")
	                            ,rec.get("CDPARENT")
	                            ,rec.get("PORBENEF")
	                            ,"U"
	                            );
	                    
	                    personasDAO.movimientosMpersona(
	                            rec.get("CDPERSON")
	                            ,rec.get("CDTIPIDE")
	                            ,rec.get("CDIDEPER")
	                            ,rec.get("DSNOMBRE")
	                            ,rec.get("CDTIPPER")
	                            ,rec.get("OTFISJUR")
	                            ,rec.get("OTSEXO")
	                            ,StringUtils.isNotBlank(rec.get("FENACIMI"))?
	                                    Utils.parse(rec.get("FENACIMI"))
	                                    :null
	                            ,rec.get("CDRFC")
	                            ,rec.get("DSEMAIL")
	                            ,rec.get("DSNOMBRE1")
	                            ,rec.get("DSAPELLIDO")
	                            ,rec.get("DSAPELLIDO1")
	                            ,renderFechas.parse(feefecto)//fechaEndoso
	                            ,rec.get("CDNACION")
	                            ,rec.get("CANALING")
	                            ,rec.get("CONDUCTO")
	                            ,rec.get("PTCUMUPR")
	                            ,rec.get("RESIDENCIA")
	                            ,rec.get("NONGRATA")
	                            ,rec.get("CDIDEEXT")
	                            ,rec.get("CDESTCIV")
	                            ,rec.get("CDSUCEMI")
	                            ,Constantes.UPDATE_MODE);
	                }
	            }
	            
	            paso = "Recuperando tr\u00e1mite de emisi\u00f3n";
	            logger.debug(paso);
	            
	            Map<String,String> datosPoliza = consultasDAO.recuperarDatosPolizaParaDocumentos(cdunieco, cdramo, estado, nmpoliza);
	            String ntramiteEmi = datosPoliza.get("ntramite");
	            
	            endososDAO.confirmarEndosoB(
                        cdunieco
                        ,cdramo
                        ,estado
                        ,nmpoliza
                        ,nmsuplem
                        ,nsuplogi
                        ,cdtipsup
                        ,""//dscoment
                        );
	            
	            String mensajeDespacho = this.confirmarGuardandoDetallesTramiteEndoso(
                        ntramiteEmi
                        ,cdunieco
                        ,cdramo
                        ,estado
                        ,nmpoliza
                        ,nmsuplem
                        ,cdtipsup
                        ,nsuplogi
                        ,null //dscoment
                        ,renderFechas.parse(feefecto)
                        ,flujo
                        ,cdusuari
                        ,cdsisrol
                        ,false //confirmar
                        );
	           /**
	             * PARA LLAMAR WS SEGUN TIPO DE ENDOSO
	             */
	            if(TipoEndoso.SEGURO_VIDA_AUTO.getCdTipSup().toString().equalsIgnoreCase(cdtipsup)){
	                if(this.endosoBeneficiarioVidaAuto(cdunieco, cdramo, estado, nmpoliza, nmsuplem, ntramite, cdtipsup, cdusuari ,renderFechas.parse(feefecto))){
	                    logger.info("Endoso de Beneficiario Vida Auto exitoso...");
	                }else{
	                    logger.error("Error al ejecutar los WS de endoso de Beneficiario Vida Auto");
	                    
	                    boolean endosoRevertido = endososManager.revierteEndosoFallido(cdunieco, cdramo, estado, nmpoliza, null, nmsuplem, 88888, "Error en endoso B tipo: "+TipoEndoso.findByKey(Integer.valueOf(cdtipsup)), true);
	                    if(endosoRevertido){
	                        logger.error("Endoso revertido exitosamente.");
	                        throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. Favor de volver a intentar.");
	                    }else{
	                        logger.error("Error al revertir el endoso");
	                        throw new ApplicationException("Error al generar el endoso, en WS. Consulte a Soporte. No se ha revertido el endoso.");
	                    }
	                }
	            }
	            
	        }
	        catch(Exception ex)
	        {
	            Utils.generaExcepcion(ex, paso);
	        }
	        
	        logger.debug(Utils.log(
	                 "\n@@@@@@ confirmaEndosoBeneficiariosVidaAuto @@@@@@"
	                ,"\n@@@@@@ resp ",resp
	                ,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
	                ));
            return resp;
	    }
	    
	    private boolean endosoBeneficiarioVidaAuto(String cdunieco, 
	                                               String cdramo,
	                                               String estado, 
	                                               String nmpoliza, 
	                                               String nmsuplem, 
	                                               String ntramite, 
	                                               String cdtipsup,
	                                               String cdusuari ,
	                                               Date fechaEndoso){
	        
	        logger.debug(">>>>> Entrando a metodo Cambio Beneficiario Vida Auto");
	        
	        List<Map<String,String>> datos = null; 
	        int endosoRecuperado = -1;
	        
	        try{
	            HashMap<String, String> params = new LinkedHashMap<String, String>();
	            params.put("pv_cdunieco_i" , cdunieco);
	            params.put("pv_cdramo_i" , cdramo);
	            params.put("pv_estado_i" , estado);
	            params.put("pv_nmpoliza_i" , nmpoliza);
	            params.put("pv_nmsuplem_i" , nmsuplem );

	            datos = endososDAO.endosoBeneficiariosVidaAuto(params);
	            
	             
	        } catch (Exception e1) {
	            logger.error("Error en llamar al PL de obtencion de datos para Cambio Beneficiario Vida Auto para SIGS",e1);
	            return false;
	        }   
	        
	        if(datos != null && !datos.isEmpty()){
	            
	            HashMap<String, Object> paramsEnd = new HashMap<String, Object>();
	            
	            for(Map<String,String> datosEnd : datos){
	                try{
	                    
	                    paramsEnd.put("vIdMotivo"   , datosEnd.get("IdMotivo"));
	                    paramsEnd.put("vSucursal"   , datosEnd.get("Sucursal"));
	                    paramsEnd.put("vRamo"       , datosEnd.get("Ramo"));
	                    paramsEnd.put("vPoliza"     , datosEnd.get("Poliza"));
	                    paramsEnd.put("vInciso"     , "1");
	                    paramsEnd.put("vNombreBen"  , datosEnd.get("NombreBen"));
	                    paramsEnd.put("vApePatBen"  , datosEnd.get("ApePatBen"));
	                    paramsEnd.put("vApeMatBen"  , datosEnd.get("ApeMatBen"));
	                    paramsEnd.put("vParentesco" , datosEnd.get("Parentesco"));
	                    paramsEnd.put("vPorcentaje" , datosEnd.get("Porcentaje"));
	                    paramsEnd.put("vFEndoso"    , fechaEndoso);
	                    paramsEnd.put("vUser"       , cdusuari);
	                    paramsEnd.put("vEndoB"      , (endosoRecuperado==-1)?0:endosoRecuperado);
	                    
	                    
	                    Integer res = autosDAOSIGS.EndoBeneficiarioVidaAuto(paramsEnd);
	                    
	                    logger.debug("Respuesta de Beneficiario Vida Auto, numero de endoso: " + res);
	                    
	                    if(res == null || res == 0 || res == -1){
	                        logger.debug("Endoso Beneficiario Vida Auto no exitoso");
	                        return false;
	                    }else{
	                        endosoRecuperado = res.intValue();
	                    }
	                    
	                } catch (Exception e){
	                    logger.error("Error en Envio Beneficiario Vida Auto: " + e.getMessage(),e);
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
	                    
	                    String reporteEspVida = rdfEspecSeguroVida;
                        String pdfEspVidaNom = "SOL_VIDA_AUTO.pdf";
                        
                        String url = rutaServidorReportes
                                + "?destype=cache"
                                + "&desformat=PDF"
                                + "&userid="+passServidorReportes
                                + "&report="+reporteEspVida
                                + "&paramform=no"
                                + "&ACCESSIBLE=YES" //parametro que habilita salida en PDF
                                + "&p_unieco="+cdunieco
                                + "&p_ramo="+cdramo
                                + "&p_estado='M'"
                                + "&p_poliza="+nmpoliza
                                + "&p_suplem="+nmsuplem
                                + "&desname="+rutaDocumentosPoliza+"/"+ntramite+"/"+pdfEspVidaNom;
                        
                        HttpUtil.generaArchivo(url,rutaDocumentosPoliza+"/"+ntramite+"/"+pdfEspVidaNom);
	                } catch (Exception e1) {
	                    logger.error("Error en llamar al PL Para actualizar endoso Beneficiario de SIGS",e1);
	                    return false;
	                }
	            }else{
	                logger.debug("Endoso Cambio Beneficiario no exitoso, valor de endoso en -1");
	                return false;
	            }
	            
	        }else{
	            logger.error("Aviso, No se tienen datos de Cambio Beneficiario");
	            return false;
	        }
	        
	        return true;
	    }	    
}
