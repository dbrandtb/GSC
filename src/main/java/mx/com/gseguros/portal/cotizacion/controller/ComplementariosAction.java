/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.gseguros.portal.cotizacion.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mx.com.aon.configurador.pantallas.model.components.GridVO;
import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.externo.service.StoredProceduresManager;
import mx.com.gseguros.portal.consultas.service.ConsultasManager;
import mx.com.gseguros.portal.cotizacion.model.DatosUsuario;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.service.CatalogosManager;
import mx.com.gseguros.portal.general.service.PantallasManager;
import mx.com.gseguros.portal.general.util.EstatusTramite;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.portal.general.util.ObjetoBD;
import mx.com.gseguros.portal.general.util.Rango;
import mx.com.gseguros.portal.general.util.TipoEndoso;
import mx.com.gseguros.portal.general.util.TipoSituacion;
import mx.com.gseguros.portal.general.util.TipoTramite;
import mx.com.gseguros.portal.general.util.Validacion;
import mx.com.gseguros.utils.Constantes;
import mx.com.gseguros.utils.HttpUtil;
import mx.com.gseguros.ws.Autos.client.axis2.WsEmitirPolizaStub.SDTPoliza;
import mx.com.gseguros.ws.Autos.service.EmisionAutosService;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ClienteGeneral;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ClienteGeneralRespuesta;
import mx.com.gseguros.ws.ice2sigs.service.Ice2sigsService;
import mx.com.gseguros.ws.recibossigs.service.RecibosSigsService;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

/**
 * 
 * @author Jair
 */
public class ComplementariosAction extends PrincipalCoreAction
{

	private static final long serialVersionUID = -1269892388621564059L;
	private org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(ComplementariosAction.class);
	private Item items;
	private Item fields;
	
	private KernelManagerSustituto kernelManager;
	private transient Ice2sigsService ice2sigsService;
	private transient RecibosSigsService recibosSigsService;
	private CatalogosManager catalogosManager;
	private StoredProceduresManager storedProceduresManager;
	
	private Map<String, String> panel1;
	private Map<String, String> panel2;
	private Map<String, String> parametros;
	private String cdunieco;
	private String cdramo;
	private String estado;
	private String nmpoliza;
	private String cdtipsit;
	private boolean success = true;
	private ScreenInterceptor scrInt = new ScreenInterceptor();
	SimpleDateFormat renderFechas = new SimpleDateFormat("dd/MM/yyyy");
	Calendar calendarHoy = Calendar.getInstance();
	private Item item1;
	private Item item2;
	private Item item3;
	private Item item4;
	private Item item5;
	private Map<String, String> map1;
	private Map<String, String> map2;
	private Map<String, String> map3;
	private Map<String, String> map4;
	private List<Map<String,Object>> list1;
	private List<Map<String,Object>> list2;
	private List<Map<String,Object>> list3;
	private List<Map<String,Object>> list4;
	private Map<String,Object> omap1;
	private String cdperson;
	private List<Map<String,String>>slist1;
	private GridVO gridResultados;
	private String mensajeRespuesta;
	private String auxiliarProductoCdramo   = null;
	private String auxiliarProductoCdtipsit = null;
	private ConsultasManager consultasManager;
	private boolean exito = false;
	private PantallasManager pantallasManager;
	private EmisionAutosService emisionAutosService;
	private boolean clienteWS;

	public String mostrarPantalla()
	/*
    
    */
	{
		return scrInt.intercept(this,
				ScreenInterceptor.PANTALLA_COMPLEMENTARIOS_GENERAL);
	}

	public String mostrarPantallaGeneral() {
		log.debug("map1: "+map1);
		try {
			//List<Tatrisit>listaTatrisit=kernelManager.obtenerTatrisit("SL");
			
			UserVO usuario = (UserVO)session.get("USUARIO");
			
			List<ComponenteVO>listaTatrisit=kernelManager.obtenerTatripol(new String[]{cdramo});
			
			/*//// si es forma de pago dxn entonces los campos tatripol son obligatorios //////
			CatalogosAction catalogosAction = new CatalogosAction();
			catalogosAction.setCatalogosManager(catalogosManager);
			catalogosAction.setCatalogo("TIPOS_PAGO_POLIZA_SIN_DXN");
			catalogosAction.obtieneCatalogo();
			List<GenericVO> formasPagoSinDxn = catalogosAction.getLista();
			
			Map<String, Object> parameters = new HashMap<String, Object>(0);
			parameters.put("pv_cdunieco", cdunieco);
			parameters.put("pv_cdramo", cdramo);
			parameters.put("pv_estado", estado);
			parameters.put("pv_nmpoliza", nmpoliza);
			parameters.put("pv_cdusuari", usuario.getUser());
			
			Map<String, Object> select = kernelManager.getInfoMpolizas(parameters);
			String cdperpag            = (String) select.get("cdperpag");
			
			boolean esDxn = true;
			for(GenericVO formaNoDxnIte: formasPagoSinDxn)
			{
				if(formaNoDxnIte.getKey().equalsIgnoreCase(cdperpag))
				{
					esDxn = false;
				}
			}
			
			for(ComponenteVO comIte : listaTatrisit)
			{
				comIte.setObligatorio(esDxn);
			}
			*///// si es forma de pago dxn entonces los campos tatripol son obligatorios //////
			
			log.debug("ServletActionContext.getServletContext().getServletContextName()$$$$$ "+ServletActionContext.getServletContext().getServletContextName());
			GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gc.genera(listaTatrisit);
			items=gc.getItems();
			fields=gc.getFields();
			//fields = new Item("fields", null, Item.ARR);

			fields.add(Item.crear(null, null, Item.OBJ).add("name", "panel1.dsciaaseg"));
			fields.add(Item.crear(null, null, Item.OBJ).add("name", "panel1.nombreagente"));
			fields.add(Item.crear(null, null, Item.OBJ).add("name", "panel1.dsramo"));
			fields.add(Item.crear(null, null, Item.OBJ).add("name", "panel2.nmpoliza"));
			fields.add(Item.crear(null, null, Item.OBJ).add("name", "panel2.estado"));
			fields.add(Item.crear(null, null, Item.OBJ).add("name", "panel2.fesolici"));
			fields.add(Item.crear(null, null, Item.OBJ).add("name", "panel2.solici"));
			fields.add(Item.crear(null, null, Item.OBJ).add("name", "panel2.feefec"));
			fields.add(Item.crear(null, null, Item.OBJ).add("name", "panel2.ferenova"));
			fields.add(Item.crear(null, null, Item.OBJ).add("name", "panel2.cdtipopol"));
			fields.add(Item.crear(null, null, Item.OBJ).add("name", "panel2.cdperpag"));
			
			UserVO usu = (UserVO) session.get("USUARIO");
			String dsrol="";
			if(usu!=null
			    &&usu.getRolActivo()!=null
			    &&usu.getRolActivo().getObjeto()!=null
			    &&usu.getRolActivo().getObjeto().getValue()!=null)
			{
			    dsrol=usu.getRolActivo().getObjeto().getValue();
			}
			if(map1==null)
				map1=new LinkedHashMap<String,String>(0);
			map1.put("sesiondsrol",dsrol);
			
			LinkedHashMap<String,Object>paramsRetroactividad=new LinkedHashMap<String,Object>();
			paramsRetroactividad.put("param1",cdunieco);
			paramsRetroactividad.put("param2",cdramo);
			paramsRetroactividad.put("param3",TipoEndoso.EMISION_POLIZA.getCdTipSup()+"");
			Map<String,String>retroactividad=storedProceduresManager.procedureMapCall(
					ObjetoBD.OBTIENE_RETROACTIVIDAD_TIPSUP.getNombre(), paramsRetroactividad, null);
			int retroac=Integer.valueOf(retroactividad.get("RETROAC"));
			int diferi =Integer.valueOf(retroactividad.get("DIFERI"));
			Calendar calendarMin=Calendar.getInstance();
			Calendar calendarMax=Calendar.getInstance();
			calendarMin.add(Calendar.DAY_OF_YEAR, retroac*-1);
			calendarMax.add(Calendar.DAY_OF_YEAR, diferi);
			map1.put("fechamin",renderFechas.format(calendarMin.getTime()));
			map1.put("fechamax",renderFechas.format(calendarMax.getTime()));
			
		} catch (Exception ex) {
			log.error("error al obtener los campos dinamicos", ex);
			items = null;
			fields = null;
		}
		return SUCCESS;
	}

	/*
	 * json
	 * 
	 * @out success
	 */
	public String cargar() {
		UserVO usuarioSesion = (UserVO) session.get("USUARIO");
		panel1 = new HashMap<String, String>(0);
		panel2 = new HashMap<String, String>(0);
		try {
			// ////////////////
			// //// duro //////
			/*
			 * //////////////* panel1.put("dsciaaseg","#General de seguros");
			 * panel1.put("dsramo","#Salud Vital");
			 * panel1.put("nombreagente","#Alvaro");
			 * panel2.put("nmpoliza",nmpoliza); panel2.put("estado","#W");
			 * panel2.put("fesolici","06/09/1969");
			 * panel2.put("solici","696969"); panel2.put("feefec","06/09/1969");
			 * panel2.put("ferenova","06/09/1969");
			 * panel2.put("cdtipopol","#R"); panel2.put("cdperpag","#12"); /
			 */// ///////////*/
				// //// duro //////
				// ////////////////

			/////////////////////////////////////
			////// Cargar info de mpolizas //////
			/*/////////////////////////////////*/
			Map<String, Object> parameters = new HashMap<String, Object>(0);
			parameters.put("pv_cdunieco", cdunieco);
			parameters.put("pv_cdramo", cdramo);
			parameters.put("pv_estado", estado);
			parameters.put("pv_nmpoliza", nmpoliza);
			parameters.put("pv_cdusuari", usuarioSesion.getUser());
			
			Map<String, Object> select = kernelManager
					.getInfoMpolizas(parameters);
			panel1.put("dsciaaseg", (String) select.get("dsunieco"));
			panel1.put("dsramo", (String) select.get("dsramo"));
			panel1.put("nombreagente", (String) select.get("nombre"));
			panel2.put("nmpoliza", nmpoliza);
			panel2.put("estado", estado);
			panel2.put("fesolici",
					renderFechas.format((Date) select.get("fesolici")));
			panel2.put("solici", (String) select.get("nmsolici"));
			panel2.put("feefec",
					renderFechas.format((Date) select.get("feefecto")));
			panel2.put("ferenova",
					renderFechas.format((Date) select.get("feproren")));
			panel2.put("cdtipopol", (String) select.get("ottempot"));
			panel2.put("cdperpag", (String) select.get("cdperpag"));
			/*/////////////////////////////////*/
			////// Cargar info de mpolizas //////
			/////////////////////////////////////
			
			/////////////////////////////////
			//////   cargar tvalopol   //////
			////// can throw exception //////
			/*/////////////////////////////*/
			try
			{
				Map<String,String> paramGetValopol=new HashMap<String,String>(0);
				paramGetValopol.put("pv_cdunieco",cdunieco);
				paramGetValopol.put("pv_cdramo",cdramo);
				paramGetValopol.put("pv_estado",estado);
				paramGetValopol.put("pv_nmpoliza",nmpoliza);
				parametros=new HashMap<String,String>(0);
				Map<String,Object>parametrosCargados=kernelManager.pGetTvalopol(paramGetValopol);
				Iterator it=parametrosCargados.entrySet().iterator();
				while(it.hasNext())
				{
					Entry<String,Object> entry=(Map.Entry<String, Object>) it.next();
					parametros.put("pv_"+entry.getKey(), (String)entry.getValue());
				}
				log.debug("panel1: "+panel1);
				log.debug("panel2: "+panel2);
				log.debug("parametros: "+parametros);
			}
			catch(Exception ex)
			{
				log.error("No hubo valopol X(");
				parametros=null;
			}
			/*/////////////////////////////*/
			//////   cargar tvalopol   //////
			////// can throw exception //////
			/////////////////////////////////
			
			success = true;
		} catch (Exception ex) {
			panel1 = new HashMap<String, String>(0);
			panel2 = new HashMap<String, String>(0);
			log.error("error al obtener los datos de mpolizas", ex);
			success = false;
		}
		return SUCCESS;
	}

	public String guardar() {
		try
		{
			log.debug("### action:");
			log.debug("panel1: "+panel1);
			log.debug("panel2: "+panel2);
			log.debug("map1: "+map1);
			log.debug("parametros: "+parametros);
			
			UserVO usuarioSesion=(UserVO) session.get("USUARIO");
			
			map1.put("pv_cdusuari",usuarioSesion.getUser());
			Map<String,Object> anterior=kernelManager.getInfoMpolizasCompleta(map1);
			log.debug("anterior: "+anterior);
			/*String columnasLeidas[]=new String[]{"status","swestado","nmsolici","feautori","cdmotanu","feanulac",
    				"swautori","cdmoneda","feinisus","fefinsus",
    	            "ottempot","feefecto","hhefecto","feproren","fevencim","nmrenova","ferecibo","feultsin","nmnumsin","cdtipcoa",
    	            "swtarifi","swabrido","feemisio","cdperpag","nmpoliex","nmcuadro","porredau","swconsol","nmpolant","nmpolnva",
    	            "fesolici","cdramant","cdmejred","nmpoldoc","nmpoliza2","nmrenove","nmsuplee","ttipcamc","ttipcamv","swpatent"};
    	            
    	            
    	            {pv_nmrenove=null, pv_fesolici=09/09/2013, pv_swconsol=S, pv_porredau=100, pv_swtarifi=A,
    	            pv_nmsolici=6969, pv_feautori=null, pv_nmcuadro=VB, pv_nmrenova=0, pv_cdunieco=1, pv_nmpoliza2=null,
    	            pv_feefecto=09/09/2013, pv_hhefecto=12:00, pv_fevencim=null, pv_status=V, pv_feproren=09/09/2014,
    	            pv_feinisus=null, pv_nmpoldoc=null, pv_cdmoneda=001, pv_nmpolnva=null, pv_cdmotanu=null, pv_nmpolant=null,
    	            pv_ottempot=R, pv_ttipcamc=null, pv_estado=W, pv_swautori=N, pv_nmpoliza=1237, pv_accion=U, pv_nmsuplee=null,
    	            pv_swestado=0, pv_cdtipcoa=N, pv_ttipcamv=null, pv_feultsin=null, pv_nmsuplem=0, pv_cdperpag=12, pv_cdramant=null,
    	            pv_feemisio=2013-09-09 00:00:00.0, pv_swpatent=null, pv_nmpoliex=null, pv_fefinsus=null, pv_nmnumsin=0,
    	            pv_ferecibo=null, pv_swabrido=null, pv_cdmejred=null, pv_cdramo=2, pv_feanulac=null}
    	            
    	            
    	            */
			Map<String,String> nuevo=new HashMap<String,String>(0);
            nuevo.put("pv_cdunieco",     map1.get("pv_cdunieco"));
            nuevo.put("pv_cdramo",       map1.get("pv_cdramo"));
            nuevo.put("pv_estado",       panel2.get("estado"));
            nuevo.put("pv_nmpoliza",     panel2.get("nmpoliza"));
            nuevo.put("pv_nmsuplem",     "0");
            nuevo.put("pv_status",       (String)anterior.get("status"));
            nuevo.put("pv_swestado",     (String)anterior.get("swestado"));
            nuevo.put("pv_nmsolici",     panel2.get("solici"));
            nuevo.put("pv_feautori",     anterior.get("feautori")!=null?renderFechas.format(anterior.get("feautori")):null);
            nuevo.put("pv_cdmotanu",     (String)anterior.get("cdmotanu"));
            nuevo.put("pv_feanulac",     anterior.get("feanulac")!=null?renderFechas.format(anterior.get("feanulac")):null);
            nuevo.put("pv_swautori",     (String)anterior.get("swautori"));
            nuevo.put("pv_cdmoneda",     (String)anterior.get("cdmoneda"));
            nuevo.put("pv_feinisus",     anterior.get("feinisus")!=null?renderFechas.format(anterior.get("feinisus")):null);
            nuevo.put("pv_fefinsus",     anterior.get("fefinsus")!=null?renderFechas.format(anterior.get("fefinsus")):null);
            nuevo.put("pv_ottempot",     panel2.get("cdtipopol"));
            nuevo.put("pv_feefecto",     panel2.get("feefec"));//renderFechas.format(calendarHoy.getTime()));
            nuevo.put("pv_hhefecto",     (String)anterior.get("hhefecto"));
            nuevo.put("pv_feproren",     panel2.get("ferenova"));//renderFechas.format(fechaEnUnAnio.getTime()));
            nuevo.put("pv_fevencim",     anterior.get("fevencim")!=null?renderFechas.format(anterior.get("fevencim")):null);
            nuevo.put("pv_nmrenova",     (String)anterior.get("nmrenova"));
            nuevo.put("pv_ferecibo",     anterior.get("ferecibo")!=null?renderFechas.format(anterior.get("ferecibo")):null);
            nuevo.put("pv_feultsin",     anterior.get("feultsin")!=null?renderFechas.format(anterior.get("feultsin")):null);
            nuevo.put("pv_nmnumsin",     (String)anterior.get("nmnumsin"));
            nuevo.put("pv_cdtipcoa",     (String)anterior.get("cdtipcoa"));
            nuevo.put("pv_swtarifi",     (String)anterior.get("swtarifi"));
            nuevo.put("pv_swabrido",     (String)anterior.get("swabrido"));
            nuevo.put("pv_feemisio",     anterior.get("feemisio")!=null?renderFechas.format(anterior.get("feemisio")):null);
            nuevo.put("pv_cdperpag",     panel2.get("cdperpag"));
            nuevo.put("pv_nmpoliex",     (String)anterior.get("nmpoliex"));
            nuevo.put("pv_nmcuadro",     (String)anterior.get("nmcuadro"));
            nuevo.put("pv_porredau",     (String)anterior.get("porredau"));
            nuevo.put("pv_swconsol",     (String)anterior.get("swconsol"));
            nuevo.put("pv_nmpolant",     (String)anterior.get("nmpolant"));
            nuevo.put("pv_nmpolnva",     (String)anterior.get("nmpolnva"));
            nuevo.put("pv_fesolici",     panel2.get("fesolici"));
            nuevo.put("pv_cdramant",     (String)anterior.get("cdramant"));
            nuevo.put("pv_cdmejred",     (String)anterior.get("cdmejred"));
            nuevo.put("pv_nmpoldoc",     (String)anterior.get("nmpoldoc"));
            nuevo.put("pv_nmpoliza2",    (String)anterior.get("nmpoliza2"));
            nuevo.put("pv_nmrenove",     (String)anterior.get("nmrenove"));
            nuevo.put("pv_nmsuplee",     (String)anterior.get("nmsuplee"));
            nuevo.put("pv_ttipcamc",     (String)anterior.get("ttipcamc"));
            nuevo.put("pv_ttipcamv",     (String)anterior.get("ttipcamv"));
            nuevo.put("pv_swpatent",     (String)anterior.get("swpatent"));
            nuevo.put("pv_accion",       "U");
            kernelManager.insertaMaestroPolizas(nuevo);
            
            if(parametros==null){
            	parametros = new HashMap<String, String>();
            }
            parametros.putAll(map1);
            parametros.put("pv_status", "V");
            parametros.put("pv_nmsuplem", "0");
            kernelManager.pMovTvalopol(parametros);
            
			success = true;
		}
		catch(Exception ex)
		{
			log.error("error al guardar la poliza",ex);
			success=false;
		}
		return SUCCESS;
	}

	public String pantallaAsegurados() {
		log.info(""
				+ "\n################################"
				+ "\n###### pantallaAsegurados ######"
				);
		log.debug("map1: "+map1);
		if(map1!=null)
		{
			if(map1.get("cdtipsit")!=null)
			{
				auxiliarProductoCdtipsit = map1.get("cdtipsit");
			}
			if(map1.get("cdramo")!=null)
			{
				auxiliarProductoCdramo = map1.get("cdramo");
			}
		}
		return scrInt.intercept(this,
				ScreenInterceptor.PANTALLA_COMPLEMENTARIOS_ASEGURADOS);
	}

	public String mostrarPantallaAsegurados() {
		try {
			item1 = new Item("fields", null, Item.ARR);// para los atributos del
														// modelo usuario
			item1.add(Item.crear(null, null, Item.OBJ).add(new Item("name", "nmsituac")));
			item1.add(Item.crear(null, null, Item.OBJ)
					.add(Item.crear("name", "cdrol"))
					.add(Item.crear("type", "Generic"))
					);
			item1.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("name", "fenacimi"))
					.add(new Item("type", "date"))
					.add(new Item("dateFormat", "d/m/Y"))
					);
			item1.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("name", "sexo"))
					.add(new Item("type", "Generic"))
					);
			item1.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("name", "tpersona"))
					.add(new Item("type", "Generic"))
					);
			item1.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("name", "nacional"))
					.add(new Item("type", "Generic"))
					);
			item1.add(Item.crear(null, null, Item.OBJ).add(new Item("name", "cdperson")));
			item1.add(Item.crear(null, null, Item.OBJ).add(new Item("name", "nombre")));
			item1.add(Item.crear(null, null, Item.OBJ).add(new Item("name", "segundo_nombre")));
			item1.add(Item.crear(null, null, Item.OBJ).add(new Item("name", "Apellido_Paterno")));
			item1.add(Item.crear(null, null, Item.OBJ).add(new Item("name", "Apellido_Materno")));
			item1.add(Item.crear(null, null, Item.OBJ).add(new Item("name", "cdrfc")));
			item1.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("name", "estomador"))
					.add(new Item("type", "boolean"))
					);
			item1.add(Item.crear(null, null, Item.OBJ).add(new Item("name", "Parentesco")));
			item1.add(Item.crear(null, null, Item.OBJ).add(new Item("name", "swexiper")));
			item1.add(Item.crear(null, null, Item.OBJ).add(new Item("name", "cdideper")));
			log.debug("Modelo armado para persona: "+item1.toString());
			/*
			nmsituac
    		cdrol
    		fenacimi
    		sexo
    		cdperson
    		nombre
    		segundo_nombre
    		Apellido_Paterno
    		Apellido_Materno
    		cdrfc*/

			item3 = new Item("columns", null, Item.ARR);// para las columnas del grid
			item3.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("header", "Nombre"))
					.add(new Item("dataIndex", "nombre"))
					.add(new Item("flex", 1))
					//.add(Item.crear("editor",null,Item.OBJ)
					//		.add("xtype","textfield")
					//		.add("allowBlank",false)
					//	)
					.add(Item.crear("editor","editorNombreContratantep2").setQuotes(""))
					);
			item3.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("header", "Segundo nombre"))
					.add(new Item("dataIndex", "segundo_nombre"))
					.add(new Item("flex", 1))
					.add(Item.crear("editor",null,Item.OBJ)
							.add("xtype","textfield")
							.add("allowBlank",true)
						)
					);
			item3.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("header", "Apellido paterno"))
					.add(new Item("dataIndex", "Apellido_Paterno"))
					.add(new Item("flex", 1))
					.add(Item.crear("editor",null,Item.OBJ)
							.add("xtype","textfield")
							.add("allowBlank",true)
						)
					);
			item3.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("header", "Apellido materno"))
					.add(new Item("dataIndex", "Apellido_Materno"))
					.add(new Item("flex", 1))
					.add(Item.crear("editor",null,Item.OBJ)
							.add("xtype","textfield")
							.add("allowBlank",true)
						)
					);
			item3.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("header", "Fecha de nacimiento"))
					.add(new Item("dataIndex", "fenacimi"))
					.add(new Item("flex", 1))
					.add(Item.crear("editor","editorFechap2").setQuotes(""))
					.add(Item.crear("renderer","Ext.util.Format.dateRenderer('d M Y')").setQuotes(""))
					);
			item3.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("header", "Sexo"))
					.add(new Item("dataIndex", "sexo"))
					.add(new Item("flex", 1))
					.add(Item.crear("renderer","rendererSexop2").setQuotes(""))
					.add(Item.crear("editor","editorGenerosp2").setQuotes(""))
					);
			item3.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("header", "T. Persona"))
					.add(new Item("dataIndex", "tpersona"))
					.add(new Item("flex", 1))
					.add(Item.crear("renderer","rendererTpersonap2").setQuotes(""))
					.add(Item.crear("editor","editorTpersonap2").setQuotes(""))
					);
			item3.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("header", "Nacionalidad"))
					.add(new Item("dataIndex", "nacional"))
					.add(new Item("flex", 1))
					.add(Item.crear("renderer","rendererNacionesp2").setQuotes(""))
					.add(Item.crear("editor","editorNacionesp2").setQuotes(""))
					);
			item3.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("header", "RFC"))
					.add(new Item("dataIndex", "cdrfc"))
					.add(new Item("width", 120))
					.add(Item.crear("editor","editorRFCAp2").setQuotes(""))
					);
			item3.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("xtype", "actioncolumn"))
					.add(new Item("width", 80))
					.add(new Item("menuDisabled", true))
					.add(new Item("header", "Acciones"))
					.add(new Item("items", null,Item.ARR)
						.add(Item.crear(null,null,Item.OBJ)
								.add("icon","resources/fam3icons/icons/report_key.png")
								.add("tooltip","Editar domicilios")
								.add(Item.crear("scope","this").setQuotes(""))
								.add(Item.crear("handler","this.onDomiciliosClick").setQuotes(""))
								)
						));
			item3.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("header", "nmsituac"))
					.add(new Item("dataIndex", "nmsituac"))
					.add(new Item("flex", 1))
					.add(Item.crear("hidden",true))
					);
			item3.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("header", "cdperson"))
					.add(new Item("dataIndex", "cdperson"))
					.add(new Item("flex", 1))
					.add(Item.crear("hidden",true))
					);
			item3.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("header", "cdrol"))
					.add(new Item("dataIndex", "cdrol"))
					.add(new Item("flex", 1))
					.add(Item.crear("hidden",true))
					);
			item3.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("header", "swexiper"))
					.add(new Item("dataIndex", "swexiper"))
					.add(new Item("flex", 1))
					.add(Item.crear("hidden",true))
					);

			item3.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("header", "cdideper"))
					.add(new Item("dataIndex", "cdideper"))
					.add(new Item("flex", 1))
					.add(Item.crear("hidden",true))
					);
			
			item2 = new Item("columns", null, Item.ARR);// para las columnas del grid
			/*item2.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("header", "Rol"))
					.add(new Item("dataIndex", "cdrol"))
					.add(new Item("flex", 1))
					.add(Item.crear("renderer","rendererRolp2").setQuotes(""))
					.add(Item.crear("editor","editorRolesp2").setQuotes(""))
					);*/
			item2.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("xtype", "checkcolumn"))
					.add(new Item("header", "Contratante"))
					.add(new Item("dataIndex", "estomador"))
					.add(new Item("width", 90))
					);
			item2.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("header", "Nombre"))
					.add(new Item("dataIndex", "nombre"))
					.add(new Item("flex", 1))
					.add(Item.crear("editor",null,Item.OBJ)
							.add("xtype","textfield")
							.add("allowBlank",false)
						)
					);
			item2.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("header", "Segundo nombre"))
					.add(new Item("dataIndex", "segundo_nombre"))
					.add(new Item("flex", 1))
					.add(Item.crear("editor",null,Item.OBJ)
							.add("xtype","textfield")
							.add("allowBlank",true)
						)
					);
			item2.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("header", "Apellido paterno"))
					.add(new Item("dataIndex", "Apellido_Paterno"))
					.add(new Item("flex", 1))
					.add(Item.crear("editor",null,Item.OBJ)
							.add("xtype","textfield")
							.add("allowBlank",false)
						)
					);
			item2.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("header", "Apellido materno"))
					.add(new Item("dataIndex", "Apellido_Materno"))
					.add(new Item("flex", 1))
					.add(Item.crear("editor",null,Item.OBJ)
							.add("xtype","textfield")
							.add("allowBlank",false)
						)
					);
			item2.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("header", "Fecha de nacimiento"))
					.add(new Item("dataIndex", "fenacimi"))
					.add(new Item("flex", 1))
					.add(Item.crear("editor","editorFechap2").setQuotes(""))
					.add(Item.crear("renderer","Ext.util.Format.dateRenderer('d M Y')").setQuotes(""))
					);
			item2.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("header", "Sexo"))
					.add(new Item("dataIndex", "sexo"))
					.add(new Item("flex", 1))
					.add(Item.crear("renderer","rendererSexop2").setQuotes(""))
					.add(Item.crear("editor","editorGenerosBp2").setQuotes(""))
					);
			item2.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("header", "T. Persona"))
					.add(new Item("dataIndex", "tpersona"))
					.add(new Item("flex", 1))
					.add(Item.crear("renderer","rendererTpersonap2").setQuotes(""))
					.add(Item.crear("editor","editorTpersonaBp2").setQuotes(""))
					);
			item2.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("header", "Nacionalidad"))
					.add(new Item("dataIndex", "nacional"))
					.add(new Item("flex", 1))
					.add(Item.crear("renderer","rendererNacionesp2").setQuotes(""))
					.add(Item.crear("editor","editorNacionesBp2").setQuotes(""))
					);
			item2.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("header", "RFC"))
					.add(new Item("dataIndex", "cdrfc"))
					.add(new Item("width", 120))
					.add(Item.crear("editor","editorRFCBp2").setQuotes(""))
					);
			/*xtype: 'actioncolumn',
	                        width: 30,
	                        sortable: false,
	                        menuDisabled: true,
	                        items: [{
	                            icon:'resources/extjs4/resources/ext-theme-classic/images/icons/fam/delete.png',
	                            //iconCls: 'icon-delete',
	                            tooltip: 'Quitar inciso',
	                            scope: this,
	                            handler: this.onRemoveClick
	                        }]*/
			item2.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("xtype", "actioncolumn"))
					.add(new Item("width", 110))
					//.add(new Item("width", 80))
					.add(new Item("menuDisabled", true))
					.add(new Item("header", "Acciones"))
					.add(new Item("items", null,Item.ARR)
						.add(Item.crear(null,null,Item.OBJ)
								.add("icon","resources/fam3icons/icons/text_list_bullets.png")
								.add("tooltip","Editar coberturas")
								.add(Item.crear("scope","this").setQuotes(""))
								.add(Item.crear("handler","this.onEditarClick").setQuotes(""))
								)
						.add(Item.crear(null,null,Item.OBJ)
								.add("icon","resources/fam3icons/icons/report_key.png")
								.add("tooltip","Editar domicilios")
								.add(Item.crear("scope","this").setQuotes(""))
								.add(Item.crear("handler","this.onDomiciliosClick").setQuotes(""))
								)
						.add(Item.crear(null,null,Item.OBJ)
								.add("icon","resources/fam3icons/icons/lock.png")
								.add("tooltip","Editar exclusiones")
								.add(Item.crear("scope","this").setQuotes(""))
								.add(Item.crear("handler","this.onExclusionClick").setQuotes(""))
								)
						.add(Item.crear(null,null,Item.OBJ)
								.add("icon","resources/fam3icons/icons/user_edit.png")
								.add("tooltip","Datos de situaci&oacute;n asegurado")
								.add(Item.crear("scope","this").setQuotes(""))
								.add(Item.crear("handler","this.onValositClick").setQuotes(""))
								)
						/*
						.add(Item.crear(null,null,Item.OBJ)
								.add("icon","resources/fam3icons/icons/money.png")
								.add("tooltip","Es el contratante")
								.add(Item.crear("scope","this").setQuotes(""))
								.add(Item.crear("handler","this.onTomadorClick").setQuotes(""))
								)
						.add(Item.crear(null,null,Item.OBJ)
								.add("icon","resources/extjs4/resources/ext-theme-classic/images/icons/fam/delete.png")
								.add("tooltip","Quitar asegurado")
								.add(Item.crear("scope","this").setQuotes(""))
								.add(Item.crear("handler","this.onRemoveClick").setQuotes(""))
								)*/
					)
					.add(Item.crear("editor",null,Item.OBJ)
							.add("xtype","textfield")
							.add("allowBlank",false)
						)
					);
			item2.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("header", "nmsituac"))
					.add(new Item("dataIndex", "nmsituac"))
					.add(new Item("flex", 1))
					.add(Item.crear("hidden",true))
					);
			item2.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("header", "cdperson"))
					.add(new Item("dataIndex", "cdperson"))
					.add(new Item("flex", 1))
					.add(Item.crear("hidden",true))
					);
			item2.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("header", "cdrol"))
					.add(new Item("dataIndex", "cdrol"))
					.add(new Item("flex", 1))
					.add(Item.crear("hidden",true))
					);
			item2.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("header", "swexiper"))
					.add(new Item("dataIndex", "swexiper"))
					.add(new Item("flex", 1))
					.add(Item.crear("hidden",true))
					);
			item2.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("header", "cdideper"))
					.add(new Item("dataIndex", "cdideper"))
					.add(new Item("flex", 1))
					.add(Item.crear("hidden",true))
					);
			
			String maxLenContratante = null;
			try
			{
				maxLenContratante = catalogosManager.obtieneCantidadMaxima(
					map1.get("cdramo")
					,map1.get("cdtipsit")
					,TipoTramite.POLIZA_NUEVA
					,Rango.LONGITUD
					,Validacion.LONGITUD_MAX_CONTRATANTE);
			}
			catch(Exception ex)
			{
				log.error("error sin efectos en obtener cantidad maxima",ex);
				maxLenContratante = "0";
			}
			
			map1.put("maxLenContratante",maxLenContratante);
			log.debug("map1: "+map1);
			
		} catch (Exception ex) {
			log.error("error al generar los campos dinamicos", ex);
			item1 = null;
		}
		return SUCCESS;
	}
	
	public String pantallaAseguradosAuto()
	{
		log.info(""
				+ "\n####################################"
				+ "\n###### pantallaAseguradosAuto ######"
				);
		log.info("map1: "+map1);
		String cdunieco = map1.get("cdunieco");
		String cdramo   = map1.get("cdramo");
		String cdtipsit = map1.get("cdtipsit");
		String estado   = map1.get("estado");
		String nmpoliza = map1.get("nmpoliza");
		log.info("cdunieco: " + cdunieco);
		log.info("cdramo: "   + cdramo);
		log.info("cdtipsit: " + cdtipsit);
		log.info("estado: "   + estado);
		log.info("nmpoliza: " + nmpoliza);
		
		GeneradorCampos gc;
		
		boolean success = true;
		if(success)
		{
			try
			{
				UserVO usuario  = (UserVO)session.get("USUARIO");
				String cdsisrol = usuario.getRolActivo().getObjeto().getValue();
				String pantalla = "EDITAR_ASEGURADOS";
				String seccion  = "ASEGURADO";
				List<ComponenteVO>componenteAsegurado=pantallasManager.obtenerComponentes(
						null, cdunieco, cdramo, cdtipsit, estado, cdsisrol, pantalla, seccion, null);
				gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
				gc.generaComponentes(componenteAsegurado, true, true, true, true, true, false);
				item1=gc.getFields();
				item2=gc.getItems();
				item3=gc.getColumns();
				
				seccion = "VALIDA_BORRAR";
				List<ComponenteVO> botonBorrar=pantallasManager.obtenerComponentes(
						null, cdunieco, cdramo, cdtipsit, estado, cdsisrol, pantalla, seccion, null);
				if(botonBorrar.size()>0)
				{
					gc.generaComponentes(botonBorrar, true, false, false, false, false, true);
					item4=gc.getButtons();
				}
				else
				{
					item4=null;
				}
				
				seccion = "VALIDA_GUARDAR";
				List<ComponenteVO> botonGuardar=pantallasManager.obtenerComponentes(
						null, cdunieco, cdramo, cdtipsit, estado, cdsisrol, pantalla, seccion, null);
				if(botonGuardar.size()>0)
				{
					gc.generaComponentes(botonGuardar, true, false, false, false, false, true);
					item5=gc.getButtons();
				}
				else
				{
					item5=null;
				}
			}
			catch(Exception ex)
			{
				log.error("error al cargar la pantalla de asegurados de auto",ex);
			}
		}
		
		log.info(""
				+ "\n###### pantallaAseguradosAuto ######"
				+ "\n####################################"
				);
		return SUCCESS;
	}
	
	public String cargarPantallaAsegurados() {
		log.debug("cargarPantallaAsegurados map1: "+map1);
		try
		{
			list1=kernelManager.obtenerAsegurados(map1);/*
			Iterator it=list1.iterator();
			while(it.hasNext())
			{
				Map<String,Object>aseg=(Map<String, Object>) it.next();
				if(aseg.containsKey("fenacimi")&&aseg.get("fenacimi")!=null)
				{
					log.debug("DATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATE");
					log.debug("DATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATE");
					log.debug("DATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATE");
					log.debug("DATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATE");
					log.debug("DATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATE");
					log.debug("DATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATE");
					log.debug("DATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATEDATE");
					log.debug(aseg.get("fenacimi"));
					Date fenacimi=(Date)aseg.get("fenacimi");
					aseg.remove("fenacimi");
					aseg.put("fenacimi",(String)renderFechas.format(fenacimi));
				}
			}*/
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al cargar los asegurados",ex);
			list1=new ArrayList<Map<String,Object>>(0);
			success=false;
		}
		return SUCCESS;
	}
	
	public String guardarPantallaAsegurados()
	{
		log.debug(map1);
		log.debug(list1);
		try
		{
			///////////////////////////////////////////////
			////// para borrar los mpoliper anterior //////
			/*///////////////////////////////////////////*/
			try
			{
				Map<String,String> param=new LinkedHashMap<String,String>(0);
				param.put("pv_cdunieco_i",	map1.get("pv_cdunieco"));
				param.put("pv_cdramo_i",	map1.get("pv_cdramo"));
				param.put("pv_estado_i",	map1.get("pv_estado"));
				param.put("pv_nmpoliza_i",	map1.get("pv_nmpoliza"));
				kernelManager.borrarMpoliper(param);
			}
			catch(Exception ex)
			{
				log.error("error cachado, no hay personas que borrar, pero no afecta.",ex);
			}
			/*///////////////////////////////////////////*/
			////// para borrar los mpoliper anterior //////
			///////////////////////////////////////////////
			
			int i=1;
			for(Map<String,Object>aseg:list1)
			{
				Map<String,Object> parametros=new LinkedHashMap<String,Object>(0);
				String cdIdeperAseg = (String) aseg.get("cdideper");
				/* MODELO RECIBIDO
				name", "nmsituac")));
				name", "cdrol"
				name", "fenacimi")));
				name", "sexo"))
				name", "cdperson")));
				name", "nombre")));
				name", "segundo_nombre")));
				name", "Apellido_Paterno")));
				name", "Apellido_Materno")));
				name", "cdrfc")));*/
				parametros.put("pv_cdperson_i",(String)aseg.get("cdperson"));
				parametros.put("pv_cdtipide_i","1");// 												);// IN  MPERSONA.cdtipide%TYPE DEFAULT NULL, Valor por default 1
				parametros.put("pv_cdideper_i", cdIdeperAseg);//						);// IN  MPERSONA.cdideper%TYPE DEFAULT NULL, Valor de CDRFC
				parametros.put("pv_dsnombre_i",(String)aseg.get("nombre"));// 						);// IN  MPERSONA.dsnombre%TYPE DEFAULT NULL,
				parametros.put("pv_cdtipper_i","1");// 												);// IN  MPERSONA.cdtipper%TYPE DEFAULT NULL, Valor por default 1
				parametros.put("pv_otfisjur_i",(String)aseg.get("tpersona"));// 												);// IN  MPERSONA.otfisjur%TYPE DEFAULT NULL, Valor por default F
				parametros.put("pv_otsexo_i",(String)aseg.get("sexo"));// 							);// IN  MPERSONA.otsexo%TYPE DEFAULT NULL,
				parametros.put("pv_fenacimi_i",renderFechas.parse((String)aseg.get("fenacimi")));//	);// IN  MPERSONA.fenacimi%TYPE DEFAULT NULL,
				parametros.put("pv_cdrfc_i",(String)aseg.get("cdrfc"));// 							);// IN  MPERSONA.cdrfc%TYPE DEFAULT NULL,
				parametros.put("pv_dsemail_i","");// 		);// 				IN  MPERSONA.dsemail%TYPE DEFAULT NULL,  Valor de email o nulo,
				parametros.put("pv_dsnombre1_i",(String)aseg.get("segundo_nombre"));// 				);// IN  MPERSONA.dsnombre1%TYPE DEFAULT NULL,
				parametros.put("pv_dsapellido_i",(String)aseg.get("Apellido_Paterno"));// 			);// IN  MPERSONA.dsapellido%TYPE DEFAULT NULL,
				parametros.put("pv_dsapellido1_i",(String)aseg.get("Apellido_Materno"));// 			);// IN  MPERSONA.dsapellido1%TYPE DEFAULT NULL,
				parametros.put("pv_feingreso_i", calendarHoy.getTime());//		);// IN  MPERSONA.feingreso%TYPE DEFAULT NULL,  Valor por default SYSDATE
				parametros.put("pv_cdnacion_i",(String)aseg.get("nacional"));
				parametros.put("pv_accion_i", "I");//												);//
				log.debug("#iteracion mov mpersonas "+i);
				kernelManager.movMpersona(parametros);
				
				parametros=new LinkedHashMap<String,Object>(0);
				parametros.put("pv_cdunieco_i",	map1.get("pv_cdunieco"));
				parametros.put("pv_cdramo_i",	map1.get("pv_cdramo"));
				parametros.put("pv_estado_i",	map1.get("pv_estado"));
				parametros.put("pv_nmpoliza_i",	map1.get("pv_nmpoliza"));
				parametros.put("pv_nmsituac_i",	(String)aseg.get("nmsituac"));
				parametros.put("pv_cdrol_i", 	(String)aseg.get("cdrol"));
				parametros.put("pv_cdperson_i",	(String)aseg.get("cdperson"));
				parametros.put("pv_nmsuplem_i",	"0");
				parametros.put("pv_status_i",	"V");
				parametros.put("pv_nmorddom_i",	"1");
				parametros.put("pv_swreclam_i",	null);
				parametros.put("pv_accion_i",	"I");
				parametros.put("pv_swexiper_i", (String)aseg.get("swexiper"));
				log.debug("#iteracion mov mpoliper "+i);
				
				
				/**
				 * SOLO PARA EL CONTRATANTE
				 */
				if(((String)aseg.get("nmsituac")).equals("0"))
				{
					kernelManager.borraMpoliper(parametros);
					
//					/**
//					 * En caso  de que si tenga un cdIdeper y el domicilio aun no este guardado en BD, se buscan los datos en el WS y se insertan en BD
//					 */
//					if(StringUtils.isNotBlank(cdIdeperAseg) && !"0".equalsIgnoreCase(cdIdeperAseg) && !"0L".equalsIgnoreCase(cdIdeperAseg)){
//						try{
//							WrapperResultados result = kernelManager.existeDomicilioContratante(cdIdeperAseg);
//							
//							if(result != null && result.getItemMap() != null && result.getItemMap().containsKey("EXISTE_DOMICILIO")){
//								if(StringUtils.isBlank((String)result.getItemMap().get("EXISTE_DOMICILIO")) || !Constantes.SI.equalsIgnoreCase((String)result.getItemMap().get("EXISTE_DOMICILIO"))){
//									/**
//									 *  Si no existe Domicilio, Se va al WS por la informacion del mismo
//									 */
//							    	String cdtipsitGS = kernelManager.obtenCdtipsitGS(parametros);
//							    	
//							    	ClienteGeneral clienteGeneral = new ClienteGeneral();
//							    	//clienteGeneral.setRfcCli((String)aseg.get("cdrfc"));
//							    	clienteGeneral.setRamoCli(Integer.parseInt(cdtipsitGS));
//							    	clienteGeneral.setNumeroExterno(cdIdeperAseg);
//							    	
//							    	ClienteGeneralRespuesta clientesRes = ice2sigsService.ejecutaWSclienteGeneral(null, null, null, null, null, null, Ice2sigsService.Operacion.CONSULTA_GENERAL, clienteGeneral, null, false);
//							    	
//							    	if(clientesRes !=null && ArrayUtils.isNotEmpty(clientesRes.getClientesGeneral())){
//							    		ClienteGeneral cliDom = null;
//							    		
//							    		if(clientesRes.getClientesGeneral().length == 1){
//							    			logger.debug("Cliente unico encontrado en WS, guardando informacion del WS...");
//							    			cliDom = clientesRes.getClientesGeneral()[0];
//							    		}else {
//							    			logger.error("Error, No se pudo obtener el domicilio del WS. Se ha encontrado mas de Un Domicilio!");
//							    		}
//							    		
//							    		/*Cuando se encontraba el cliente de una lista
//							    		 * for(ClienteGeneral cliIter : clientesRes.getClientesGeneral()){
//							    			if(cdIdeperAseg.equalsIgnoreCase(cliIter.getNumeroExterno())){
//							    				cliDom = cliIter;
//							    			}
//							    		}*/
//							    		
//							    		if(cliDom != null){
//							    			HashMap<String,String> paramDomicil = new HashMap<String, String>();
//							    			paramDomicil.put("pv_cdperson_i", (String)aseg.get("cdperson"));
//							    			paramDomicil.put("pv_nmorddom_i", "1");
//							    			paramDomicil.put("pv_msdomici_i", cliDom.getCalleCli() +" "+ cliDom.getNumeroCli());
//							    			paramDomicil.put("pv_nmtelefo_i", cliDom.getTelefonoCli());
//							    			paramDomicil.put("pv_cdpostal_i", Integer.toString(cliDom.getCodposCli()));
//							    			paramDomicil.put("pv_cdedo_i",    null/*cliDom.getPoblacionCli()*/);
//							    			paramDomicil.put("pv_cdmunici_i", null/*cliDom.getMunicipioCli()*/);
//							    			paramDomicil.put("pv_cdcoloni_i", null/*cliDom.getColoniaCli()*/);
//							    			paramDomicil.put("pv_nmnumero_i", null);
//							    			paramDomicil.put("pv_nmnumint_i", null);
//							    			paramDomicil.put("pv_accion_i", "I");
//
//							    			kernelManager.pMovMdomicil(paramDomicil);
//							    			
//							    			HashMap<String,String> paramValoper = new HashMap<String, String>();
//							    			paramValoper.put("pv_cdunieco", "0");
//							    			paramValoper.put("pv_cdramo",   "0");
//							    			paramValoper.put("pv_estado",   null);
//							    			paramValoper.put("pv_nmpoliza", "0");
//							    			paramValoper.put("pv_nmsituac", null);
//							    			paramValoper.put("pv_nmsuplem", null);
//							    			paramValoper.put("pv_status",   null);
//							    			paramValoper.put("pv_cdrol",    "1");
//							    			paramValoper.put("pv_cdperson", (String)aseg.get("cdperson"));
//							    			paramValoper.put("pv_cdatribu", null);
//							    			paramValoper.put("pv_cdtipsit", null);
//							    			
//							    			paramValoper.put("pv_otvalor01", cliDom.getCveEle());
//							    			paramValoper.put("pv_otvalor02", cliDom.getPasaporteCli());
//							    			paramValoper.put("pv_otvalor03", null);
//							    			paramValoper.put("pv_otvalor04", null);
//							    			paramValoper.put("pv_otvalor05", null);
//							    			paramValoper.put("pv_otvalor06", null);
//							    			paramValoper.put("pv_otvalor07", null);
//							    			paramValoper.put("pv_otvalor08", cliDom.getOrirecCli());
//							    			paramValoper.put("pv_otvalor09", null);
//							    			paramValoper.put("pv_otvalor10", null);
//							    			paramValoper.put("pv_otvalor11", cliDom.getNacCli());
//							    			paramValoper.put("pv_otvalor12", null);
//							    			paramValoper.put("pv_otvalor13", null);
//							    			paramValoper.put("pv_otvalor14", null);
//							    			paramValoper.put("pv_otvalor15", null);
//							    			paramValoper.put("pv_otvalor16", null);
//							    			paramValoper.put("pv_otvalor17", null);
//							    			paramValoper.put("pv_otvalor18", null);
//							    			paramValoper.put("pv_otvalor19", null);
//							    			paramValoper.put("pv_otvalor20", Integer.toString(cliDom.getOcuPro()));
//							    			paramValoper.put("pv_otvalor21", null);
//							    			paramValoper.put("pv_otvalor22", null);
//							    			paramValoper.put("pv_otvalor23", null);
//							    			paramValoper.put("pv_otvalor24", null);
//							    			paramValoper.put("pv_otvalor25", cliDom.getCurpCli());
//							    			paramValoper.put("pv_otvalor26", null);
//							    			paramValoper.put("pv_otvalor27", null);
//							    			paramValoper.put("pv_otvalor28", null);
//							    			paramValoper.put("pv_otvalor29", null);
//							    			paramValoper.put("pv_otvalor30", null);
//							    			paramValoper.put("pv_otvalor31", null);
//							    			paramValoper.put("pv_otvalor32", null);
//							    			paramValoper.put("pv_otvalor33", null);
//							    			paramValoper.put("pv_otvalor34", null);
//							    			paramValoper.put("pv_otvalor35", null);
//							    			paramValoper.put("pv_otvalor36", null);
//							    			paramValoper.put("pv_otvalor37", null);
//							    			paramValoper.put("pv_otvalor38", null);
//							    			paramValoper.put("pv_otvalor39", cliDom.getMailCli());
//							    			paramValoper.put("pv_otvalor40", null);
//							    			paramValoper.put("pv_otvalor41", null);
//							    			paramValoper.put("pv_otvalor42", null);
//							    			paramValoper.put("pv_otvalor43", null);
//							    			paramValoper.put("pv_otvalor44", null);
//							    			paramValoper.put("pv_otvalor45", null);
//							    			paramValoper.put("pv_otvalor46", null);
//							    			paramValoper.put("pv_otvalor47", null);
//							    			paramValoper.put("pv_otvalor48", null);
//							    			paramValoper.put("pv_otvalor49", null);
//							    			paramValoper.put("pv_otvalor50", null);
//							    			
//							    			kernelManager.pMovTvaloper(paramValoper);
//							    			
//							    		}else{
//							    			logger.error("Error. Cliente no encontrado en WS !");
//							    		}
//							    	}else{
//							    		logger.error("Error, No se pudo obtener el domicilio del WS.");
//							    	}
//							    	
//								}else{
//									logger.debug("Ya Existe el domicilio del cdiper.");
//								}
//							}else{
//								logger.error("Error al verificar si hay datos en mdomicil!!");
//							}
//						}catch(Exception eWS){
//							logger.error("Error en obtencion de Domicilio para contratante.",eWS);
//						}
//					}
					
				}
				
				kernelManager.movMpoliper(parametros);
				
				//////////////////////////
				//para que cambie tvalosit
				try
				{
					Map<String,String>paramsGetValosit = new LinkedHashMap<String,String>(0);
					paramsGetValosit.put("pv_cdunieco_i" , map1.get("pv_cdunieco"));
					paramsGetValosit.put("pv_cdramo_i"   , map1.get("pv_cdramo"));
					paramsGetValosit.put("pv_estado_i"   , map1.get("pv_estado"));
					paramsGetValosit.put("pv_nmpoliza_i" , map1.get("pv_nmpoliza"));
					paramsGetValosit.put("pv_nmsituac_i" , (String)aseg.get("nmsituac"));
					log.debug("paramsValositAseguradoIterado: "+paramsGetValosit);
					Map<String,Object>valositAsegurado=kernelManager.obtieneValositSituac(paramsGetValosit);
					log.debug("valositAseguradoIterado: "+valositAsegurado);
					
					Map<String,Object>valositAseguradoIteradoTemp=new LinkedHashMap<String,Object>(0);
					//poner pv_ a los leidos
					Iterator it=valositAsegurado.entrySet().iterator();
					while(it.hasNext())
					{
						Entry en=(Entry)it.next();
						valositAseguradoIteradoTemp.put("pv_"+(String)en.getKey(),en.getValue());//agregar pv_ a los anteriores
					}
					valositAsegurado=valositAseguradoIteradoTemp;
					log.debug("se puso pv_");
					
					LinkedHashMap<String,Object>parametrosAtributos=new LinkedHashMap<String,Object>();
					parametrosAtributos.put("cdtipsit",map1.get("cdtipsit"));
					try
					{
						Map<String,String>atributos=consultasManager.consultaDinamica(ObjetoBD.OBTIENE_ATRIBUTOS, parametrosAtributos).get(0);
					
						String cdatribuFenacimi = atributos.get("FENACIMI");
						if(cdatribuFenacimi.length()==1)
						{
							cdatribuFenacimi = "0"+cdatribuFenacimi;
						}
						valositAsegurado.put("pv_otvalor"+cdatribuFenacimi, (String)aseg.get("fenacimi"));
						
						String cdatribusSexo = atributos.get("SEXO");
						if(cdatribusSexo.length()==1)
						{
							cdatribusSexo = "0"+cdatribusSexo;
						}
						valositAsegurado.put("pv_otvalor"+cdatribusSexo, (String)aseg.get("sexo"));
						log.debug("se agregaron los nuevos");
					}
					catch(Exception ex)
					{
						log.error("error en obtener los atributos",ex);
					}
					
					//convertir a string el total
					Map<String,String>paramsNuevos=new LinkedHashMap<String,String>(0);
					it=valositAsegurado.entrySet().iterator();
					while(it.hasNext())
					{
						Entry en=(Entry)it.next();
						paramsNuevos.put((String)en.getKey(),(String)en.getValue());
					}
					log.debug("se pasaron a string");
					
					paramsNuevos.put("pv_cdunieco" , map1.get("pv_cdunieco"));
					paramsNuevos.put("pv_cdramo"   , map1.get("pv_cdramo"));
					paramsNuevos.put("pv_estado"   , map1.get("pv_estado"));
					paramsNuevos.put("pv_nmpoliza" , map1.get("pv_nmpoliza"));
					paramsNuevos.put("pv_nmsituac" , (String)aseg.get("nmsituac"));
					log.debug("los actualizados seran: "+paramsNuevos);
					
					kernelManager.actualizaValoresSituaciones(paramsNuevos);
				}
				catch(Exception ex)
				{
					log.error("exception no lanzada a pantalla",ex);
				}
				//para que cambie tvalosit
				//////////////////////////
				
				i++;
			}
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al guardar asegurados",ex);
			success=false;
		}
		return SUCCESS;
	}

	public String generarCdperson()
	{
		try
		{
			cdperson=kernelManager.generaCdperson();
			if(session.get("cdpersonciclado")!=null&&((String)session.get("cdpersonciclado")).equals(cdperson))
			{
				log.debug("###############################################");
				log.debug("###############################################");
				log.debug("##################CICLADO######################");
				log.debug("###############################################");
				log.debug("###############################################");
				return generarCdperson();
			}
			else
			{
				session.put("cdpersonciclado",cdperson);
			}
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al generar cdperson",ex);
			success=false;
		}
		return SUCCESS;
	}
	
	public String detalleCotizacion()
	{
		try
		{
			log.debug("panel1: "+panel1);
			/*nmsituac,parentesco,orden,Codigo_Garantia, Nombre_garantia, cdtipcon, Importe*/
			slist1=kernelManager.obtenerDetallesCotizacion(panel1);
			success=true;
		}
		catch(Exception ex)
		{
			log.error("Error al obtener el detalle de cotizacion",ex);
			success=false;
		}
		return SUCCESS;
	}
	
	public String retarificar()
	{
		log.debug(""
				+ "\n#########################"
				+ "\n#########################"
				+ "\n######             ######"
				+ "\n###### retarificar ######"
				+ "\n######             ######"
				);
		try
		{
			
			try
			{
				HashMap<String, Object> paramsValida = new HashMap<String, Object>();
				paramsValida.put("pv_cdunieco_i", cdunieco);
				paramsValida.put("pv_cdramo_i", cdramo);
				paramsValida.put("pv_estado_i", "W");
				paramsValida.put("pv_nmpoliza_i", panel1.get("nmpoliza"));
				paramsValida.put("pv_nmsuplem_i", "0");
				
				kernelManager.validaDatosDxN(paramsValida);
				
			}
			catch(Exception ex)
			{
				log.error("Error al validar Datos de DxN",ex);
				mensajeRespuesta = ex.getMessage();
				return SUCCESS;
			}
			
			///////////////////////////////////
			////// validar la extraprima //////
			/*///////////////////////////////*/
			String statusValidacionExtraprimas="S";
			try
			{
				Map<String,String>paramValExtraprima=new LinkedHashMap<String,String>(0);
				paramValExtraprima.put("pv_cdunieco_i" , cdunieco);
				paramValExtraprima.put("pv_cdramo_i"   , cdramo);
				paramValExtraprima.put("pv_estado_i"   , "W");
				paramValExtraprima.put("pv_nmpoliza_i" , panel1.get("nmpoliza"));
				statusValidacionExtraprimas=(String) kernelManager.validarExtraprima(paramValExtraprima).getItemMap().get("status");
				log.debug("tiene status la extraprima: "+statusValidacionExtraprimas);
				if(statusValidacionExtraprimas==null)
				{
					statusValidacionExtraprimas="N";
				}
			}
			catch(Exception ex)
			{
				log.error("Error sin impacto funcional al validar extraprimas: ",ex);
				statusValidacionExtraprimas="S";
			}
			if(statusValidacionExtraprimas.equalsIgnoreCase("N"))
			{
				mensajeRespuesta="Favor de verificar las extraprimas y los endosos de extraprima";
				return SUCCESS;
			}
			/*///////////////////////////////*/
			////// validar la extraprima //////
			///////////////////////////////////
			
			//////////////////////////////////////////
			////// validar que tengan direccion //1548
			List<Map<String,String>>lisUsuSinDir=null;
			try
			{
				Map<String,String>paramValidar=new LinkedHashMap<String,String>(0);
				paramValidar.put("pv_cdunieco" , cdunieco);
				paramValidar.put("pv_cdramo"   , cdramo);
				paramValidar.put("pv_estado"   , "W");
				paramValidar.put("pv_nmpoliza" , panel1.get("nmpoliza"));
				lisUsuSinDir=kernelManager.PValInfoPersonas(paramValidar);
			}
			catch(Exception ex)
			{
				log.error("Error sin impacto funcional al validar domicilios: ",ex);
				lisUsuSinDir=null;
			}
			
			if(lisUsuSinDir!=null&&lisUsuSinDir.size()>0)
			{
				mensajeRespuesta="Favor de verificar la direcci&oacute;n de los siguientes asegurados:<br/>";
				// f a v o r
				//0 1 2 3 4 5
				if(lisUsuSinDir.get(0).get("nombre").substring(0,5).equalsIgnoreCase("favor"))
				{
					mensajeRespuesta=lisUsuSinDir.get(0).get("nombre");
				}
				else
				{
					for(int i=0;i<lisUsuSinDir.size();i++)
					{
						mensajeRespuesta+=lisUsuSinDir.get(i).get("nombre")+"<br/>";
					}					
				}
				log.debug("Se va a terminar el proceso porque faltan direcciones");
				return SUCCESS;
			}
			////// validar que tengan direccion //1548
			//////////////////////////////////////////
			
			UserVO usuario=(UserVO)session.get("USUARIO");
			
			//////////////////////////
			////// sigsvdef end //////
			/*//////////////////////*/
			Map<String,String> mapCoberturas=new HashMap<String,String>(0);
            //mapCoberturas.put("pv_cdunieco_i",   datosUsuario.getCdunieco());
            //mapCoberturas.put("pv_cdramo_i",     datosUsuario.getCdramo());
            mapCoberturas.put("pv_cdunieco_i",   cdunieco);//se agrega desde el formulario
            mapCoberturas.put("pv_cdramo_i",     cdramo);//se agrega desde el formulario
            mapCoberturas.put("pv_estado_i",     "W");
            mapCoberturas.put("pv_nmpoliza_i",   panel1.get("nmpoliza"));
            mapCoberturas.put("pv_nmsituac_i",   "0");
            mapCoberturas.put("pv_nmsuplem_i",   "0");//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
            mapCoberturas.put("pv_cdgarant_i",   "TODO");//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
            mapCoberturas.put("pv_cdtipsup_i",   "1");
            kernelManager.coberturasEnd(mapCoberturas);
			/*//////////////////////*/
			////// sigsvdef end //////
			//////////////////////////
			
			////////////////////////////////
			////// retarifica         //////
			/*////////////////////////////*/
			Map<String,String> mapaTarificacion=new HashMap<String,String>(0);
	        mapaTarificacion.put("pv_cdusuari_i",   usuario.getUser());
	        mapaTarificacion.put("pv_cdelemen_i",   usuario.getEmpresa().getElementoId());
	        //mapaTarificacion.put("pv_cdunieco_i",   datosUsuario.getCdunieco());
	        //mapaTarificacion.put("pv_cdramo_i",     datosUsuario.getCdramo());
	        mapaTarificacion.put("pv_cdunieco_i",   cdunieco);
	        mapaTarificacion.put("pv_cdramo_i",     cdramo);
	        mapaTarificacion.put("pv_estado_i",     "W");
	        mapaTarificacion.put("pv_nmpoliza_i",   panel1.get("nmpoliza"));
	        mapaTarificacion.put("pv_nmsituac_i",   "0");
	        mapaTarificacion.put("pv_nmsuplem_i",   "0");
	        //mapaTarificacion.put("pv_cdtipsit_i",   datosUsuario.getCdtipsit());
	        mapaTarificacion.put("pv_cdtipsit_i",   cdtipsit);
	        mx.com.aon.portal.util.WrapperResultados wr4=kernelManager.ejecutaASIGSVALIPOL_EMI(mapaTarificacion);
	        /*////////////////////////////*/
	        ////// retarifica         //////
	        ////////////////////////////////
	        
			///////////////////////////////////
			////// Generacion cotizacion //////
			/*///////////////////////////////*
			Map<String,String> mapaDuroResultados=new HashMap<String,String>(0);
			mapaDuroResultados.put("pv_cdusuari_i", usuario.getUser());
			mapaDuroResultados.put("pv_cdunieco_i", datosUsuario.getCdunieco());
			mapaDuroResultados.put("pv_cdramo_i",   datosUsuario.getCdramo());
			mapaDuroResultados.put("pv_estado_i",   "W");
			mapaDuroResultados.put("pv_nmpoliza_i", panel1.get("nmpoliza"));
			mapaDuroResultados.put("pv_cdelemen_i", usuario.getEmpresa().getElementoId());
			mapaDuroResultados.put("pv_cdtipsit_i", datosUsuario.getCdtipsit());
			List<ResultadoCotizacionVO> listaResultados=kernelManager.obtenerResultadosCotizacion(mapaDuroResultados);
			//utilizando logica anterior
			CotizacionManagerImpl managerAnterior=new CotizacionManagerImpl();
			gridResultados=managerAnterior.adaptarDatosCotizacion(listaResultados);
			log.debug("### session poniendo resultados con grid: "+listaResultados.size());
			session.put(ResultadoCotizacionAction.DATOS_GRID, gridResultados);
			/*///////////////////////////////*/
			////// Generacion cotizacion //////
			///////////////////////////////////
			
			////////////////////////////////
			////// obtener coberturas //////
	        /*////////////////////////////*/
			
			/**
			OBTENER INFORMACION DE POLIZAS
			pv_cdunieco
            pv_cdramo
            pv_estado
            pv_nmpoliza
            pv_cdusuari
            /**/
			Map<String,String>paramObtenerPoliza=new LinkedHashMap<String,String>(0);
			//paramObtenerPoliza.put("pv_cdunieco" , datosUsuario.getCdunieco());
			//paramObtenerPoliza.put("pv_cdramo"   , datosUsuario.getCdramo());
			paramObtenerPoliza.put("pv_cdunieco" , cdunieco);
			paramObtenerPoliza.put("pv_cdramo"   , cdramo);
			paramObtenerPoliza.put("pv_estado"   , "W");
			paramObtenerPoliza.put("pv_nmpoliza" , panel1.get("nmpoliza"));
			paramObtenerPoliza.put("pv_cdusuari" , usuario.getUser());
			Map<String,Object>polizaCompleta=kernelManager.getInfoMpolizasCompleta(paramObtenerPoliza);
			log.debug("poliza a emitir: "+polizaCompleta);
			/**/
			
			/**
			OBTENER COBERTURAS
			pv_cdunieco_i
			pv_cdramo_i
			pv_estado_i
			pv_nmpoliza_i
			pv_cdplan_i
			pv_cdperpag_i
			
			out:
			status","swestado","nmsolici","feautori","cdmotanu","feanulac",
			"swautori","cdmoneda","feinisus","fefinsus",
            "ottempot","feefecto","hhefecto","feproren","fevencim","nmrenova","ferecibo","feultsin","nmnumsin","cdtipcoa",
            "swtarifi","swabrido","feemisio","cdperpag","nmpoliex","nmcuadro","porredau","swconsol","nmpolant","nmpolnva",
            "fesolici","cdramant","cdmejred","nmpoldoc","nmpoliza2","nmrenove","nmsuplee","ttipcamc","ttipcamv","swpatent
			*/
			Map<String,String>paramDetallePoliza=new LinkedHashMap<String,String>(0);
			//paramDetallePoliza.put("pv_cdunieco_i" , datosUsuario.getCdunieco());
			//paramDetallePoliza.put("pv_cdramo_i"   , datosUsuario.getCdramo());
			paramDetallePoliza.put("pv_cdunieco_i" , cdunieco);
			paramDetallePoliza.put("pv_cdramo_i"   , cdramo);
			paramDetallePoliza.put("pv_estado_i"   , "W");
			paramDetallePoliza.put("pv_nmpoliza_i" , panel1.get("nmpoliza"));
			paramDetallePoliza.put("pv_cdplan_i"   , null);
			paramDetallePoliza.put("pv_cdperpag_i" , (String) polizaCompleta.get("cdperpag"));
			slist1=kernelManager.obtenerDetallesCotizacion(paramDetallePoliza);
			success=true; 
			/**/
			/*////////////////////////////*/
			////// obtener coberturas //////
			////////////////////////////////
			
	        success=true;
		}
		catch(Exception ex)
		{
			log.debug("error al retarificar",ex);
			success=false;
		}
		log.debug(""
				+ "\n######             ######"
				+ "\n###### retarificar ######"
				+ "\n######             ######"
				+ "\n#########################"
				+ "\n#########################"
				);
		return SUCCESS;
	}
	
	public String emitir()
	{
		log.debug(""
				+ "\n########################"
				+ "\n######   emitir   ######"
				+ "");
		log.debug("panel1"+panel1);
		log.debug("panel2"+panel2);
		
		////// variables 
		success                = true;
		String ntramite        = null;
		UserVO us              = null;
		String cdunieco        = null;
		String cdramo          = null;
		String cdtipsit        = null;
		String estado          = "W";
		String nmpoliza        = null;
		String cdpersonSesion  = null;
		String cdusuari        = null;
		String cdelemen        = null;
		String rutaCarpeta     = null;
		String cdperpag        = null;
		String nmpolizaEmitida = null;
		String nmpoliexEmitida = null;
		String nmsuplemEmitida = null;
		String esDxN           = null;
		String cdIdeperRes     = null;
		String tipoMov         = TipoTramite.POLIZA_NUEVA.getCdtiptra();
		
		////// obtener parametros
		if(success)
		{
			try
			{
				success = success && (ntramite = panel1.get("pv_ntramite")      )!=null;
				success = success && (us       = (UserVO)session.get("USUARIO") )!=null;
				success = success && (cdunieco = panel2.get("pv_cdunieco")      )!=null;
				success = success && (cdramo   = panel2.get("pv_cdramo")        )!=null;
				success = success && (cdtipsit = panel2.get("pv_cdtipsit")      )!=null;
				success = success && (nmpoliza = panel2.get("pv_nmpoliza")      )!=null;
				if(!success)
				{
					mensajeRespuesta="No se recibieron todos los datos";
				}
			}
			catch(Exception ex)
			{
				log.error("Error al procesar los datos",ex);
				mensajeRespuesta = "Error al procesar los datos";
				success          = false;
			}
		}
		
		////// datos de la sesion del usuario
		if(success)
		{
			try
			{
				DatosUsuario datUs = kernelManager.obtenerDatosUsuario(us.getUser(),cdtipsit);
				cdpersonSesion = datUs.getCdperson();
				cdusuari       = us.getUser();
				cdelemen       = us.getEmpresa().getElementoId();
			}
			catch(Exception ex)
			{
				log.error("error al obtener los datos de usuario",ex);
				mensajeRespuesta = ex.getMessage();
				success          = false;
			}
		}
		
		////// validar edad asegurados
		if(success)
		{
			try
			{
				LinkedHashMap<String,Object>paramValidaEdadAsegu=new LinkedHashMap<String,Object>();
				paramValidaEdadAsegu.put("param1", cdunieco);
				paramValidaEdadAsegu.put("param2", cdramo);
				paramValidaEdadAsegu.put("param3", estado);
				paramValidaEdadAsegu.put("param4", nmpoliza);
				paramValidaEdadAsegu.put("param5", "0");
				List<Map<String,String>> listaAseguradosEdadInvalida = consultasManager.consultaDinamica(
						ObjetoBD.VALIDA_EDAD_ASEGURADOS, paramValidaEdadAsegu);
				
				if(listaAseguradosEdadInvalida.size()>0)
				{
					mensajeRespuesta = "La p&oacute;liza se envi&oacute; a autorizaci&oacute;n debido a que:<br/>";
					for(Map<String,String>iAseguradoEdadInvalida:listaAseguradosEdadInvalida)
					{
						mensajeRespuesta = mensajeRespuesta + iAseguradoEdadInvalida.get("NOMBRE");
						if(iAseguradoEdadInvalida.get("SUPERAMINI").substring(0, 1).equals("-"))
						{
							mensajeRespuesta = mensajeRespuesta + " no llega a la edad de "+iAseguradoEdadInvalida.get("EDADMINI")+" a&ntilde;os<br/>";
						}
						else
						{
							mensajeRespuesta = mensajeRespuesta + " supera la edad de "+iAseguradoEdadInvalida.get("EDADMAXI")+" a&ntilde;os<br/>";
						}
					}
					
					Map<String,Object>paramsMesaControl=new HashMap<String,Object>();
					paramsMesaControl.put("pv_cdunieco_i"   , cdunieco);
					paramsMesaControl.put("pv_cdramo_i"     , cdramo);
					paramsMesaControl.put("pv_estado_i"     , estado);
					paramsMesaControl.put("pv_nmpoliza_i"   , nmpoliza);
					paramsMesaControl.put("pv_nmsuplem_i"   , "0");
					paramsMesaControl.put("pv_cdsucadm_i"   , cdunieco);
					paramsMesaControl.put("pv_cdsucdoc_i"   , cdunieco);
					paramsMesaControl.put("pv_cdtiptra_i"   , TipoTramite.EMISION_EN_ESPERA.getCdtiptra());
					paramsMesaControl.put("pv_ferecepc_i"   , new Date());
					paramsMesaControl.put("pv_cdagente_i"   , null);
					paramsMesaControl.put("pv_referencia_i" , null);
					paramsMesaControl.put("pv_nombre_i"     , null);
					paramsMesaControl.put("pv_festatus_i"   , new Date());
					paramsMesaControl.put("pv_status_i"     , EstatusTramite.EN_ESPERA_DE_AUTORIZACION.getCodigo());
					paramsMesaControl.put("pv_comments_i"   , mensajeRespuesta);
					paramsMesaControl.put("pv_nmsolici_i"   , null);
					paramsMesaControl.put("pv_cdtipsit_i"   , null);
					paramsMesaControl.put("pv_otvalor01"    , cdusuari);
					paramsMesaControl.put("pv_otvalor02"    , cdelemen);
					paramsMesaControl.put("pv_otvalor03"    , ntramite);
					paramsMesaControl.put("pv_otvalor04"    , cdpersonSesion);
					paramsMesaControl.put("pv_otvalor05"    , "EMISION");
					WrapperResultados wr=kernelManager.PMovMesacontrol(paramsMesaControl);
					String ntramiteAutorizacion=(String) wr.getItemMap().get("ntramite");
					mensajeRespuesta = mensajeRespuesta + "<br/>Tr&aacute;mite de autorizaci&oacute;n: "+ntramiteAutorizacion;
					
					Map<String,Object>parDmesCon=new LinkedHashMap<String,Object>(0);
		        	parDmesCon.put("pv_ntramite_i"   , ntramite);
		        	parDmesCon.put("pv_feinicio_i"   , new Date());
		        	parDmesCon.put("pv_cdclausu_i"   , null);
		        	parDmesCon.put("pv_comments_i"   , "El tr&aacute;mite se envi&oacute; a autorizaci&oacute;n ("+ntramiteAutorizacion+")");
		        	parDmesCon.put("pv_cdusuari_i"   , cdusuari);
		        	parDmesCon.put("pv_cdmotivo_i"   , null);
		        	kernelManager.movDmesacontrol(parDmesCon);
					
		        	kernelManager.mesaControlUpdateStatus(ntramite, EstatusTramite.EN_ESPERA_DE_AUTORIZACION.getCodigo());
		        	
					success = false;
				}
			}
			catch(Exception ex)
			{
				log.error("error al validar la edad de los asegurados",ex);
				mensajeRespuesta = ex.getMessage();
				success          = false;
			}
		}
		
		////// obtener forma de pago
		if(success)
		{
			try
			{
				Map<String,String>paramObtenerPoliza=new LinkedHashMap<String,String>(0);
				paramObtenerPoliza.put("pv_cdunieco" , cdunieco);
				paramObtenerPoliza.put("pv_cdramo"   , cdramo);
				paramObtenerPoliza.put("pv_estado"   , estado);
				paramObtenerPoliza.put("pv_nmpoliza" , nmpoliza);
				paramObtenerPoliza.put("pv_cdusuari" , cdusuari);
				Map<String,Object>polizaCompleta=kernelManager.getInfoMpolizasCompleta(paramObtenerPoliza);
				log.debug("poliza a emitir: "+polizaCompleta);
				cdperpag = (String)polizaCompleta.get("cdperpag");
			}
			catch(Exception ex)
			{
				log.error("error al obtener los datos completos de la poliza",ex);
				mensajeRespuesta = ex.getMessage();
				success          = false;
			}
		}
		
		////// emision
		if(success)
		{
			try
			{
				Map<String,Object>paramEmi=new LinkedHashMap<String,Object>(0);
				paramEmi.put("pv_cdusuari"  , cdusuari);
				paramEmi.put("pv_cdunieco"  , cdunieco);
				paramEmi.put("pv_cdramo"    , cdramo);
				paramEmi.put("pv_estado"    , estado);
				paramEmi.put("pv_nmpoliza"  , nmpoliza);
				paramEmi.put("pv_nmsituac"  , "1");
				paramEmi.put("pv_nmsuplem"  , "0");
				paramEmi.put("pv_cdelement" , cdelemen); 
				paramEmi.put("pv_cdcia"     , cdunieco);
				paramEmi.put("pv_cdplan"    , null);
				paramEmi.put("pv_cdperpag"  , cdperpag);
				paramEmi.put("pv_cdperson"  , cdpersonSesion);
				paramEmi.put("pv_fecha"     , new Date());
				paramEmi.put("pv_ntramite"  , ntramite);
				mx.com.aon.portal.util.WrapperResultados wr=kernelManager.emitir(paramEmi);
				log.debug("emision obtenida "+wr.getItemMap());
				
				nmpolizaEmitida = (String)wr.getItemMap().get("nmpoliza");
				nmpoliexEmitida = (String)wr.getItemMap().get("nmpoliex");
				nmsuplemEmitida = (String)wr.getItemMap().get("nmsuplem");
				esDxN           = (String)wr.getItemMap().get("esdxn");
				cdIdeperRes     = (String)wr.getItemMap().get("CDIDEPER");
				
				panel2.put("nmpoliza",nmpolizaEmitida);
				panel2.put("nmpoliex",nmpoliexEmitida);
			}
			catch(Exception ex)
			{
				log.error("error en el pl de emitir",ex);
				mensajeRespuesta = ex.getMessage();
				success          = false;
			}
		}
		
		////// crear carpeta para los documentos
		if(success)
		{
			rutaCarpeta=this.getText("ruta.documentos.poliza")+"/"+ntramite;
            File carpeta = new File(rutaCarpeta);
            if(!carpeta.exists())
            {
            	log.debug("no existe la carpeta::: "+rutaCarpeta);
            	carpeta.mkdir();
            	if(carpeta.exists())
            	{
            		log.debug("carpeta creada");
            	}
            	else
            	{
            		log.debug("carpeta NO creada");
            		success          = false;
            		mensajeRespuesta = "Error al crear la carpeta de documentos";
            	}
            }
            else
            {
            	log.debug("existe la carpeta   ::: "+rutaCarpeta);
            }
		}
		
		////// ws cliente y recibos
		if(success)
		{
			String _cdunieco = cdunieco;
			String _cdramo   = cdramo;
			String edoPoliza = "M";
			String _nmpoliza = nmpolizaEmitida;
			String _nmsuplem = nmsuplemEmitida;
			String sucursal = cdunieco;
			if("1".equals(sucursal))
			{
				sucursal = "1000";
			}
			logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>> Parametros para WS de cliente, Recibos y Autos: <<<<<<<<<<<<<<<<<<<<<<< ");
			logger.debug(">>>>>>>>>> cdunieco: "   + _cdunieco);
			logger.debug(">>>>>>>>>> cdramo: "     + _cdramo);
			logger.debug(">>>>>>>>>> estado: "     + edoPoliza);
			logger.debug(">>>>>>>>>> nmpoliza: "   + _nmpoliza);
			logger.debug(">>>>>>>>>> suplemento: " + _nmsuplem);
			logger.debug(">>>>>>>>>> sucursal: "   + sucursal);
			logger.debug(">>>>>>>>>> nmsolici: "   + nmpoliza);
			logger.debug(">>>>>>>>>> nmtramite: "  + ntramite);
			
			//ws cliente
			
			if(cdtipsit.equalsIgnoreCase(TipoSituacion.SALUD_VITAL.getCdtipsit())
					||cdtipsit.equalsIgnoreCase(TipoSituacion.SALUD_NOMINA.getCdtipsit())
					||cdtipsit.equalsIgnoreCase(TipoSituacion.MULTISALUD.getCdtipsit())
					)
			{
				ice2sigsService.ejecutaWSclienteGeneral(_cdunieco, _cdramo, edoPoliza, _nmpoliza, _nmsuplem, ntramite, Ice2sigsService.Operacion.INSERTA, null, us, true);
			}
			else if(cdtipsit.equalsIgnoreCase(TipoSituacion.AUTOS_FRONTERIZOS.getCdtipsit()))
			{
				if(StringUtils.isBlank(cdIdeperRes)){
					
					ClienteGeneralRespuesta resCli = ice2sigsService.ejecutaWSclienteGeneral(_cdunieco, _cdramo, edoPoliza, _nmpoliza, _nmsuplem, ntramite, Ice2sigsService.Operacion.INSERTA, null, us, false);
					if(resCli != null && Ice2sigsService.Estatus.EXITO.getCodigo() == resCli.getCodigo() && ArrayUtils.isNotEmpty(resCli.getClientesGeneral())){
						cdIdeperRes = resCli.getClientesGeneral()[0].getNumeroExterno();
						if(StringUtils.isNotBlank(cdIdeperRes) && !cdIdeperRes.equalsIgnoreCase("0") && !cdIdeperRes.equalsIgnoreCase("0L")){
						
							HashMap<String, String> paramsIdeper =  new HashMap<String, String>();
							paramsIdeper.put("pv_cdunieco_i", _cdunieco);
							paramsIdeper.put("pv_cdramo_i",   _cdramo);
							paramsIdeper.put("pv_estado_i",   edoPoliza);
							paramsIdeper.put("pv_nmpoliza_i", _nmpoliza);
							paramsIdeper.put("pv_nmsuplem_i", _nmsuplem);
							paramsIdeper.put("pv_cdideper_i", cdIdeperRes);
							
							kernelManager.actualizaCdIdeper(paramsIdeper);
									
						}else {
							success = false;
							mensajeRespuesta = "Error al crear Cliente en WS, no se pudo obtener el numero de Cliente, respondio: "+ cdIdeperRes;
							logger.error("Error al crear Cliente en WS, no se pudo obtener el numero de Cliente, respondio: "+ cdIdeperRes);
						} 
					}else{
						success = false;
						mensajeRespuesta = "Error al crear Cliente en WS.";
						logger.error("Error al Crear el cliente en WS!");
					}
				}
			}
				
			////// ws de cotizacion y emision para autos
			if(success && cdtipsit.equalsIgnoreCase(TipoSituacion.AUTOS_FRONTERIZOS.getCdtipsit()))
			{
				SDTPoliza aux = emisionAutosService.cotizaEmiteAutomovilWS(cdunieco, cdramo,
							estado, nmpolizaEmitida, nmsuplemEmitida, ntramite, us);
				success = aux!=null && aux.getNumpol()>0l;
				if(!success)
				{
					mensajeRespuesta = "Error en el Web Service de cotizaci&oacute;n. No se pudo emitir la p&oacute;liza";
				}
			}
			
			//ws recibos
			if( success && (cdtipsit.equalsIgnoreCase(TipoSituacion.SALUD_VITAL.getCdtipsit())
					||cdtipsit.equalsIgnoreCase(TipoSituacion.SALUD_NOMINA.getCdtipsit())
					||cdtipsit.equalsIgnoreCase(TipoSituacion.MULTISALUD.getCdtipsit())
					))
			{
				try
				{
					if(StringUtils.isNotBlank(esDxN) && "S".equalsIgnoreCase(esDxN))
					{	
						// Ejecutamos el Web Service de Recibos:
						ice2sigsService.ejecutaWSrecibos(_cdunieco, _cdramo,
								edoPoliza, _nmpoliza, 
								_nmsuplem, rutaCarpeta,
								sucursal, nmpoliza, ntramite, 
								false, tipoMov,
								us);
						// Ejecutamos el Web Service de Recibos DxN:
						recibosSigsService.generaRecibosDxN(_cdunieco, _cdramo, edoPoliza, _nmpoliza, _nmsuplem, sucursal, nmpoliza, ntramite, us);
					}
					else
					{
						// Ejecutamos el Web Service de Recibos:
						ice2sigsService.ejecutaWSrecibos(_cdunieco, _cdramo,
								edoPoliza, _nmpoliza, 
								_nmsuplem, rutaCarpeta,
								sucursal, nmpoliza,ntramite, 
								true, tipoMov,
								us);
					}
				}
				catch(Exception ex)
				{
					log.error("error al lanzar ws recibos",ex);
					mensajeRespuesta = ex.getMessage();
					success          = false;
				}
			}
		}
		
		////// documentos
		if(success)
		{
			try
			{
				Map<String,String>paramsGetDoc=new LinkedHashMap<String,String>(0);
				paramsGetDoc.put("pv_cdunieco_i" , cdunieco);
				paramsGetDoc.put("pv_cdramo_i"   , cdramo);
				paramsGetDoc.put("pv_estado_i"   , "M");
				paramsGetDoc.put("pv_nmpoliza_i" , nmpolizaEmitida);
				paramsGetDoc.put("pv_nmsuplem_i" , nmsuplemEmitida);
				paramsGetDoc.put("pv_ntramite_i" , ntramite);
				List<Map<String,String>>listaDocu=kernelManager.obtenerListaDocumentos(paramsGetDoc);
				
				//listaDocu contiene: nmsolici,nmsituac,descripc,descripl
				for(Map<String,String> docu:listaDocu)
				{
					log.debug("docu iterado: "+docu);
					String descripc=docu.get("descripc");
					String descripl=docu.get("descripl");
					String url=this.getText("ruta.servidor.reports")
							+ "?destype=cache"
							+ "&desformat=PDF"
							+ "&userid="+this.getText("pass.servidor.reports")
							+ "&report="+descripl
							+ "&paramform=no"
							+ "&ACCESSIBLE=YES" //parametro que habilita salida en PDF
							+ "&p_unieco="+cdunieco
							+ "&p_ramo="+cdramo
							+ "&p_estado='M'"
							+ "&p_poliza="+nmpolizaEmitida
							+ "&p_suplem="+nmsuplemEmitida
							+ "&desname="+rutaCarpeta+"/"+descripc;
					if(descripc.substring(0, 6).equalsIgnoreCase("CREDEN"))
					{
						// C R E D E N C I A L _ X X X X X X . P D F
						//0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
						url+="&p_cdperson="+descripc.substring(11, descripc.lastIndexOf("."));
					}
					log.debug(""
							+ "\n#################################"
							+ "\n###### Se solicita reporte ######"
							+ "\na "+url);
					HttpUtil.generaArchivo(url,rutaCarpeta+"/"+descripc);
					log.debug(""
							+ "\n######                    ######"
							+ "\n###### reporte solicitado ######"
							+ "\n################################"
							+ "");
				}
			}
			catch(Exception ex)
			{
				log.error("error al generar documentacion de emision",ex);
				mensajeRespuesta = ex.getMessage();
				success          = false;
			}
		}
		
		////// detalle emision
		if(success)
		{
			try
			{
				log.debug("se inserta detalle nuevo para emision");
	        	Map<String,Object>parDmesCon=new LinkedHashMap<String,Object>(0);
	        	parDmesCon.put("pv_ntramite_i"   , ntramite);
	        	parDmesCon.put("pv_feinicio_i"   , new Date());
	        	parDmesCon.put("pv_cdclausu_i"   , null);
	        	parDmesCon.put("pv_comments_i"   , "El tr&aacute;mite se emiti&oacute;");
	        	parDmesCon.put("pv_cdusuari_i"   , cdusuari);
	        	parDmesCon.put("pv_cdmotivo_i"   , null);
	        	kernelManager.movDmesacontrol(parDmesCon);
			}
			catch(Exception ex)
			{
				log.error("error al insertar detalle de emision",ex);
				mensajeRespuesta = ex.getMessage();
				success          = false;
			}
		}
		
		log.debug(""
				+ "\n######   emitir   ######"
				+ "\n########################"
				+ "");
		return SUCCESS;
	}
	
	public String autorizaEmision()
	{
		this.session=ActionContext.getContext().getSession();
		logger.debug(""
				+ "\n#############################"
				+ "\n###### autorizaEmision ######"
				);
		logger.debug("panel1"+panel1);
		
		////// variables
		success                 = true;
		String ntramiteAut      = null;
		String cdunieco         = null;
		String cdramo           = null;
		String estado           = "W";
		String nmpoliza         = null;
		String ntramite         = null;
		String cdusuari         = null;
		String cdelemen         = null;
		String cdperson         = null;
		String comentarios      = null;
		String fechaEmision     = null;
		Date   fechaEmisionDate = null;
		Date   fechaDia         = new Date();
		String cdperpag         = null;
		UserVO us               = null;
		String nmpolizaEmitida  = null;
		String nmpoliexEmitida  = null;
		String nmsuplemEmitida  = null;
		String esDxN            = null;
		String cdIdeperRes      = null;
		String tipoMov          = TipoTramite.POLIZA_NUEVA.getCdtiptra();
		String rutaCarpeta      = null;
		
		////// obtener parametros
		if(success)
		{
			try
			{
				success = success && ( ntramiteAut      = panel1.get("ntramite")           )!=null;
				success = success && ( cdunieco         = panel1.get("cdunieco")           )!=null;
				success = success && ( cdramo           = panel1.get("cdramo")             )!=null;
				success = success && ( nmpoliza         = panel1.get("nmpoliza")           )!=null;
				success = success && ( ntramite         = panel1.get("otvalor03")          )!=null;
				success = success && ( cdusuari         = panel1.get("otvalor01")          )!=null;
				success = success && ( cdelemen         = panel1.get("otvalor02")          )!=null;
				success = success && ( cdperson         = panel1.get("otvalor04")          )!=null;
				success = success && ( comentarios      = panel2.get("observaciones")      )!=null;
				success = success && ( fechaEmision     = panel1.get("ferecepc")           )!=null;
				success = success && ( fechaEmisionDate = renderFechas.parse(fechaEmision) )!=null;
				success = success && ( us               = (UserVO)session.get("USUARIO")   )!=null;
				if(!success)
				{
					mensajeRespuesta = "No se recibieron todos los datos";
				}
			}
			catch(Exception ex)
			{
				log.error("error al procesar los datos",ex);
				mensajeRespuesta = "Error al procesar los datos";
				success          = false;
			}
		}
		
		////// cdtener datos poliza
		if(success)
		{
			try
			{
				Map<String,String>paramObtenerPoliza=new LinkedHashMap<String,String>(0);
				paramObtenerPoliza.put("pv_cdunieco" , cdunieco);
				paramObtenerPoliza.put("pv_cdramo"   , cdramo);
				paramObtenerPoliza.put("pv_estado"   , estado);
				paramObtenerPoliza.put("pv_nmpoliza" , nmpoliza);
				paramObtenerPoliza.put("pv_cdusuari" , cdusuari);
				Map<String,Object>polizaCompleta=kernelManager.getInfoMpolizasCompleta(paramObtenerPoliza);
				log.debug("poliza a emitir: "+polizaCompleta);
				cdperpag = (String)polizaCompleta.get("cdperpag");
			}
			catch(Exception ex)
			{
				log.error("error al obtener los datos completos de la poliza",ex);
				mensajeRespuesta = ex.getMessage();
				success          = false;
			}
		}
		
		////// emision
		if(success)
		{
			try
			{
				Map<String,Object>paramEmi=new LinkedHashMap<String,Object>(0);
				paramEmi.put("pv_cdusuari"  , cdusuari);
				paramEmi.put("pv_cdunieco"  , cdunieco);
				paramEmi.put("pv_cdramo"    , cdramo);
				paramEmi.put("pv_estado"    , estado);
				paramEmi.put("pv_nmpoliza"  , nmpoliza);
				paramEmi.put("pv_nmsituac"  , "1");
				paramEmi.put("pv_nmsuplem"  , "0");
				paramEmi.put("pv_cdelement" , cdelemen); 
				paramEmi.put("pv_cdcia"     , cdunieco);
				paramEmi.put("pv_cdplan"    , null);
				paramEmi.put("pv_cdperpag"  , cdperpag);
				paramEmi.put("pv_cdperson"  , cdperson);
				paramEmi.put("pv_fecha"     , fechaEmisionDate);
				paramEmi.put("pv_ntramite"  , ntramite);
				mx.com.aon.portal.util.WrapperResultados wr=kernelManager.emitir(paramEmi);
				log.debug("emision obtenida "+wr.getItemMap());
				
				nmpolizaEmitida = (String)wr.getItemMap().get("nmpoliza");
				nmpoliexEmitida = (String)wr.getItemMap().get("nmpoliex");
				nmsuplemEmitida = (String)wr.getItemMap().get("nmsuplem");
				esDxN           = (String)wr.getItemMap().get("esdxn");
				cdIdeperRes     = (String)wr.getItemMap().get("CDIDEPER");
			}
			catch(Exception ex)
			{
				log.error("error en el pl de emision",ex);
				mensajeRespuesta = ex.getMessage();
				success          = false;
			}
		}
		
		////// carpeta documentos
		if(success)
		{
			try
			{
				rutaCarpeta=this.getText("ruta.documentos.poliza")+"/"+ntramite;
	            File carpeta = new File(rutaCarpeta);
	            if(!carpeta.exists())
	            {
	            	log.debug("no existe la carpeta::: "+rutaCarpeta);
	            	carpeta.mkdir();
	            	if(carpeta.exists())
	            	{
	            		log.debug("carpeta creada");
	            	}
	            	else
	            	{
	            		log.debug("carpeta NO creada");
	            		success          = false;
	            		mensajeRespuesta = "Error al crear la carpeta de documentos";
	            	}
	            }
	            else
	            {
	            	log.debug("existe la carpeta   ::: "+rutaCarpeta);
	            }
			}
			catch(Exception ex)
			{
				log.error("error al crear la carpeta",ex);
				mensajeRespuesta = ex.getMessage();
				success          = false;
			}
		}
		
		////// ws cliente y recibos
		if(success)
		{
			String _cdunieco = cdunieco;
			String _cdramo   = cdramo;
			String edoPoliza = "M";
			String _nmpoliza = nmpolizaEmitida;
			String _nmsuplem = nmsuplemEmitida;
			String sucursal = cdunieco;
			if("1".equals(sucursal))
			{
				sucursal = "1000";
			}
			logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>> Parametros para WS de cliente, Recibos y Autos: <<<<<<<<<<<<<<<<<<<<<<< ");
			logger.debug(">>>>>>>>>> cdunieco: "   + _cdunieco);
			logger.debug(">>>>>>>>>> cdramo: "     + _cdramo);
			logger.debug(">>>>>>>>>> estado: "     + edoPoliza);
			logger.debug(">>>>>>>>>> nmpoliza: "   + _nmpoliza);
			logger.debug(">>>>>>>>>> suplemento: " + _nmsuplem);
			logger.debug(">>>>>>>>>> sucursal: "   + sucursal);
			logger.debug(">>>>>>>>>> nmsolici: "   + nmpoliza);
			logger.debug(">>>>>>>>>> nmtramite: "  + ntramite);
			
			//ws cliente
			
			if(cdtipsit.equalsIgnoreCase(TipoSituacion.SALUD_VITAL.getCdtipsit())
					||cdtipsit.equalsIgnoreCase(TipoSituacion.SALUD_NOMINA.getCdtipsit())
					||cdtipsit.equalsIgnoreCase(TipoSituacion.MULTISALUD.getCdtipsit())
					)
			{
				ice2sigsService.ejecutaWSclienteGeneral(_cdunieco, _cdramo, edoPoliza, _nmpoliza, _nmsuplem, ntramite, Ice2sigsService.Operacion.INSERTA, null, us, true);
			}
			else if(cdtipsit.equalsIgnoreCase(TipoSituacion.AUTOS_FRONTERIZOS.getCdtipsit()))
			{
				if(StringUtils.isBlank(cdIdeperRes)){
					
					ClienteGeneralRespuesta resCli = ice2sigsService.ejecutaWSclienteGeneral(_cdunieco, _cdramo, edoPoliza, _nmpoliza, _nmsuplem, ntramite, Ice2sigsService.Operacion.INSERTA, null, us, false);
					if(resCli != null && Ice2sigsService.Estatus.EXITO.getCodigo() == resCli.getCodigo() && ArrayUtils.isNotEmpty(resCli.getClientesGeneral())){
						cdIdeperRes = resCli.getClientesGeneral()[0].getNumeroExterno();
						if(StringUtils.isNotBlank(cdIdeperRes) && !cdIdeperRes.equalsIgnoreCase("0") && !cdIdeperRes.equalsIgnoreCase("0L")){
						
							HashMap<String, String> paramsIdeper =  new HashMap<String, String>();
							paramsIdeper.put("pv_cdunieco_i", _cdunieco);
							paramsIdeper.put("pv_cdramo_i",   _cdramo);
							paramsIdeper.put("pv_estado_i",   edoPoliza);
							paramsIdeper.put("pv_nmpoliza_i", _nmpoliza);
							paramsIdeper.put("pv_nmsuplem_i", _nmsuplem);
							paramsIdeper.put("pv_cdideper_i", cdIdeperRes);
							
							kernelManager.actualizaCdIdeper(paramsIdeper);
									
						}else {
							success = false;
							mensajeRespuesta = "Error al crear Cliente en WS, no se pudo obtener el numero de Cliente, respondio: "+ cdIdeperRes;
							logger.error("Error al crear Cliente en WS, no se pudo obtener el numero de Cliente, respondio: "+ cdIdeperRes);
						} 
					}else{
						success = false;
						mensajeRespuesta = "Error al crear Cliente en WS.";
						logger.error("Error al Crear el cliente en WS!");
					}
				}
			}
				
			////// ws de cotizacion y emision para autos
			if(success && cdtipsit.equalsIgnoreCase(TipoSituacion.AUTOS_FRONTERIZOS.getCdtipsit()))
			{
				SDTPoliza aux = emisionAutosService.cotizaEmiteAutomovilWS(cdunieco, cdramo,
							estado, nmpolizaEmitida, nmsuplemEmitida, ntramite, us);
				success = aux!=null && aux.getNumpol()>0l;
				if(!success)
				{
					mensajeRespuesta = "Error en el Web Service de cotizaci&oacute;n. No se pudo emitir la p&oacute;liza";
				}
			}
			
			//ws recibos
			if( success && (cdtipsit.equalsIgnoreCase(TipoSituacion.SALUD_VITAL.getCdtipsit())
					||cdtipsit.equalsIgnoreCase(TipoSituacion.SALUD_NOMINA.getCdtipsit())
					||cdtipsit.equalsIgnoreCase(TipoSituacion.MULTISALUD.getCdtipsit())
					))
			{
				try
				{
					if(StringUtils.isNotBlank(esDxN) && "S".equalsIgnoreCase(esDxN))
					{	
						// Ejecutamos el Web Service de Recibos:
						ice2sigsService.ejecutaWSrecibos(_cdunieco, _cdramo,
								edoPoliza, _nmpoliza, 
								_nmsuplem, rutaCarpeta,
								sucursal, nmpoliza, ntramite, 
								false, tipoMov,
								us);
						// Ejecutamos el Web Service de Recibos DxN:
						recibosSigsService.generaRecibosDxN(_cdunieco, _cdramo, edoPoliza, _nmpoliza, _nmsuplem, sucursal, nmpoliza, ntramite, us);
					}
					else
					{
						// Ejecutamos el Web Service de Recibos:
						ice2sigsService.ejecutaWSrecibos(_cdunieco, _cdramo,
								edoPoliza, _nmpoliza, 
								_nmsuplem, rutaCarpeta,
								sucursal, nmpoliza,ntramite, 
								true, tipoMov,
								us);
					}
				}
				catch(Exception ex)
				{
					log.error("error al lanzar ws recibos",ex);
					mensajeRespuesta = ex.getMessage();
					success          = false;
				}
			}
		}
		
		////// documentacion
		if(success)
		{
			try
			{
				Map<String,String>paramsGetDoc=new LinkedHashMap<String,String>(0);
				paramsGetDoc.put("pv_cdunieco_i" , cdunieco);
				paramsGetDoc.put("pv_cdramo_i"   , cdramo);
				paramsGetDoc.put("pv_estado_i"   , "M");
				paramsGetDoc.put("pv_nmpoliza_i" , nmpolizaEmitida);
				paramsGetDoc.put("pv_nmsuplem_i" , nmsuplemEmitida);
				paramsGetDoc.put("pv_ntramite_i" , ntramite);
				List<Map<String,String>>listaDocu=kernelManager.obtenerListaDocumentos(paramsGetDoc);
				
				//listaDocu contiene: nmsolici,nmsituac,descripc,descripl
				for(Map<String,String> docu:listaDocu)
				{
					log.debug("docu iterado: "+docu);
					String descripc=docu.get("descripc");
					String descripl=docu.get("descripl");
					String url=this.getText("ruta.servidor.reports")
							+ "?destype=cache"
							+ "&desformat=PDF"
							+ "&userid="+this.getText("pass.servidor.reports")
							+ "&report="+descripl
							+ "&paramform=no"
							+ "&ACCESSIBLE=YES" //parametro que habilita salida en PDF
							+ "&p_unieco="+cdunieco
							+ "&p_ramo="+cdramo
							+ "&p_estado='M'"
							+ "&p_poliza="+nmpolizaEmitida
							+ "&p_suplem="+nmsuplemEmitida
							+ "&desname="+rutaCarpeta+"/"+descripc;
					if(descripc.substring(0, 6).equalsIgnoreCase("CREDEN"))
					{
						// C R E D E N C I A L _ X X X X X X . P D F
						//0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
						url+="&p_cdperson="+descripc.substring(11, descripc.lastIndexOf("."));
					}
					log.debug(""
							+ "\n#################################"
							+ "\n###### Se solicita reporte ######"
							+ "\na "+url);
					HttpUtil.generaArchivo(url,rutaCarpeta+"/"+descripc);
					log.debug(""
							+ "\n###### reporte solicitado ######"
							+ "\n################################"
							+ "");
				}
			}
			catch(Exception ex)
			{
				log.error("error al crear documentacion",ex);
				success          = false;
				mensajeRespuesta = ex.getMessage();
			}
		}
		
		////// detalle emision
		if(success)
		{
			try
			{
				log.debug("se inserta detalle nuevo para emision");
	        	Map<String,Object>parDmesCon=new LinkedHashMap<String,Object>(0);
	        	parDmesCon.put("pv_ntramite_i"   , ntramite);
	        	parDmesCon.put("pv_feinicio_i"   , fechaDia);
	        	parDmesCon.put("pv_cdclausu_i"   , null);
	        	parDmesCon.put("pv_comments_i"   , "La emisi&oacute;n del tr&aacute;mite se autoriz&oacute; con las siguientes observaciones:<br/>"+comentarios);
	        	parDmesCon.put("pv_cdusuari_i"   , us.getUser());
	        	parDmesCon.put("pv_cdmotivo_i"   , null);
	        	kernelManager.movDmesacontrol(parDmesCon);
				
	        	kernelManager.mesaControlUpdateStatus(ntramiteAut, EstatusTramite.CONFIRMADO.getCodigo());
	        	
	        	mensajeRespuesta = "P&oacute;liza emitida: "+nmpoliexEmitida;
			}
			catch(Exception ex)
			{
				log.error("error al guardar detalle de emision",ex);
				mensajeRespuesta = ex.getMessage();
				success          = false;
			}
		}
		
		logger.debug(""
				+ "\n###### autorizaEmision ######"
				+ "\n#############################"
				);
		return SUCCESS;
	}
	
	public String ejecutaWSManualCliente() {

		String cdunieco = map1.get("cdunieco");
		String cdramo = map1.get("cdramo");
		String estado = map1.get("estado");
		String nmpoliza = map1.get("nmpoliza");
		String nmsuplem = map1.get("nmsuplem");
		//String sucursal = map1.get("sucursal");

		String nmtramite = map1.get("nmtramite");
		
		String operacion = map1.get("operacion");
		if(StringUtils.isBlank(operacion)) operacion = "INSERTA";
		Ice2sigsService.Operacion operation = Ice2sigsService.Operacion.valueOf(operacion);

		// Ejecutamos el Web Service de Cliente Salud:
		//ice2sigsService.ejecutaWSclienteSalud(cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmtramite, operation, (UserVO) session.get("USUARIO"));
		ice2sigsService.ejecutaWSclienteGeneral(cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmtramite, operation, null, (UserVO) session.get("USUARIO"), true);
		
		success = true;
		return SUCCESS;
	}

	public String ejecutaWSManualRecibos() {

		String cdunieco = map1.get("cdunieco");
		String cdramo = map1.get("cdramo");
		String estado = map1.get("estado");
		String nmpoliza = map1.get("nmpoliza");
		String nmsuplem = map1.get("nmsuplem");
		String sucursal = map1.get("sucursal");

		String nmsolici = map1.get("nmsolici");
		String nmtramite = map1.get("nmtramite");

		String tipoMov = "1";
		
		// Ejecutamos el Web Service de Recibos:
		ice2sigsService.ejecutaWSrecibos(cdunieco, cdramo, estado, nmpoliza, nmsuplem, null, sucursal, nmsolici, nmtramite, true, tipoMov, (UserVO) session.get("USUARIO"));

		success = true;
		return SUCCESS;
	}
	
	
	public String ejecutaWSManualRecibosDxN() {
		
		String cdunieco = map1.get("cdunieco");
		String cdramo = map1.get("cdramo");
		String estado = map1.get("estado");
		String nmpoliza = map1.get("nmpoliza");
		String nmsuplem = map1.get("nmsuplem");
		String sucursal = map1.get("sucursal");
		String nmsolici = map1.get("nmsolici");
		String nmtramite = map1.get("nmtramite");

		// Ejecutamos el Web Service de Recibos DxN:
		recibosSigsService.generaRecibosDxN(cdunieco, cdramo, estado, nmpoliza, nmsuplem, sucursal, nmsolici, nmtramite, (UserVO) session.get("USUARIO"));
		success = true;
		return SUCCESS;
	}
	
	public String buscarPersonasRepetidas()
	{
		log.debug(""
				+ "#######################################\n"
				+ "#######################################\n"
				+ "###### buscar personas repetidas ######\n"
				+ "######                           ######"
				);
		log.debug("map1: "+map1);
		clienteWS = false;
		
		try
		{
		    slist1=kernelManager.buscarRFC(map1);
		    
		    /**
		     * Si no se encuentra el RFC en la BD se consulta a un WS de personas
		     */
		    if(TipoSituacion.AUTOS_FRONTERIZOS.getCdtipsit().equalsIgnoreCase(map1.get("cdtipsit")) && (slist1 == null || slist1.isEmpty())){
		    	logger.debug("Buscando RFC en WS...");
		    	
		    	slist1 = new ArrayList<Map<String, String>>();
		    	HashMap<String, Object> params =  new HashMap<String, Object>();
		    	params.putAll(map1);
		    	String cdtipsitGS = kernelManager.obtenCdtipsitGS(params);
		    	
		    	ClienteGeneral clienteGeneral = new ClienteGeneral();
		    	clienteGeneral.setRfcCli(map1.get("pv_rfc_i"));
		    	clienteGeneral.setRamoCli(Integer.parseInt(cdtipsitGS));
		    	
		    	ClienteGeneralRespuesta clientesRes = ice2sigsService.ejecutaWSclienteGeneral(null, null, null, null, null, null, Ice2sigsService.Operacion.CONSULTA_GENERAL, clienteGeneral, null, false);
		    	
		    	if(clientesRes == null){
		    		success = false;
		    		return SUCCESS;
		    	}
		    	
		    	ClienteGeneral[] listaClientesGS = clientesRes.getClientesGeneral();
		    	if(listaClientesGS != null && listaClientesGS.length > 0 ){
		    		logger.debug("A�adiendo Clientes de GS a Lista, " + listaClientesGS.length);
		    		clienteWS = true;
		    		
		    		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		    		Calendar calendar =  Calendar.getInstance();
			    	HashMap<String, String> agregar = null;
			    	
			    	for(ClienteGeneral cli: listaClientesGS){
			    		agregar = new HashMap<String,String>();
			    		
				    	agregar.put("RFCCLI",       cli.getRfcCli());
				    	agregar.put("NOMBRECLI",    cli.getNombreCli()+" "+cli.getApellidopCli()+" "+cli.getApellidomCli());
				    	if(cli.getFecnacCli()!= null){
				    		calendar.set(cli.getFecnacCli().get(Calendar.YEAR), cli.getFecnacCli().get(Calendar.MONTH), cli.getFecnacCli().get(Calendar.DAY_OF_MONTH));
							agregar.put("FENACIMICLI", sdf.format(calendar.getTime()));
				    	}else {
				    		agregar.put("FENACIMICLI", "");
				    	}
				    	agregar.put("DIRECCIONCLI", cli.getCalleCli()+" "+(StringUtils.isNotBlank(cli.getNumeroCli())?cli.getNumeroCli():"")+(cli.getCodposCli()!=0 ?" C.P. "+cli.getCodposCli():"")+" "+cli.getColoniaCli()+" "+cli.getMunicipioCli()+" "+cli.getPoblacionCli());
				    	agregar.put("CLAVECLI",     cli.getNumeroExterno());
				    	slist1.add(agregar);
			    	}
			    			
		    	}else {
		    		logger.debug("No se encontraron clientes en GS.");
		    	}
		    	
		    }
		    
		    for(Map<String,String>rfc:slist1)
		    {
		    	rfc.put("DISPLAY",rfc.get("RFCCLI")+"<br/>"+rfc.get("NOMBRECLI")+"<br/>"+rfc.get("DIRECCIONCLI"));
		    }
		    success=true;
		}
		catch(Exception ex)
		{
			log.error("Error al buscar RFC",ex);
			success=false;
		}
		log.debug(""
				+ "######                           ######\n"
				+ "###### buscar personas repetidas ######\n"
				+ "#######################################\n"
				+ "#######################################"
				);
		return SUCCESS;
	}
	
	public String obtenerRamos()
	{
		try
		{
			slist1=kernelManager.obtenerRamos(map1.get("cdunieco"));
		}
		catch(Exception ex)
		{
			log.error("error al obtener los ramos",ex);
		}
		success=true;
		return SUCCESS;
	}
	
	public String obtenerTipsit()
	{
		try
		{
			slist1=kernelManager.obtenerTipsit(map1.get("cdramo"));
		}
		catch(Exception ex)
		{
			log.error("error al obtener los tipsit",ex);
		}
		success=true;
		return SUCCESS;
	}
	
	/////////////////////////////////
	////// getters ans setters //////
	/*/////////////////////////////*/
	public Item getItems() {
		return items;
	}

	public void setItems(Item items) {
		this.items = items;
	}

	public void setKernelManager(KernelManagerSustituto kernelManager) {
		this.kernelManager = kernelManager;
	}

	public Map<String, String> getPanel1() {
		return panel1;
	}

	public void setPanel1(Map<String, String> panel1) {
		this.panel1 = panel1;
	}

	public Map<String, String> getPanel2() {
		return panel2;
	}

	public void setPanel2(Map<String, String> panel2) {
		this.panel2 = panel2;
	}

	public Map<String, String> getParametros() {
		return parametros;
	}

	public void setParametros(Map<String, String> parametros) {
		this.parametros = parametros;
	}

	public Item getFields() {
		return fields;
	}

	public void setFields(Item fields) {
		this.fields = fields;
	}

	public String getCdunieco() {
		return cdunieco;
	}

	public void setCdunieco(String cdunieco) {
		this.cdunieco = cdunieco;
	}

	public String getCdramo() {
		return cdramo;
	}

	public void setCdramo(String cdramo) {
		this.cdramo = cdramo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNmpoliza() {
		return nmpoliza;
	}

	public void setNmpoliza(String nmpoliza) {
		this.nmpoliza = nmpoliza;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Item getItem1() {
		return item1;
	}

	public void setItem1(Item item1) {
		this.item1 = item1;
	}

	public Item getItem2() {
		return item2;
	}

	public void setItem2(Item item2) {
		this.item2 = item2;
	}

	public Item getItem3() {
		return item3;
	}

	public void setItem3(Item item3) {
		this.item3 = item3;
	}

	public Item getItem4() {
		return item4;
	}

	public void setItem4(Item item4) {
		this.item4 = item4;
	}

	public Map<String, String> getMap1() {
		return map1;
	}

	public void setMap1(Map<String, String> map1) {
		this.map1 = map1;
	}

	public Map<String, String> getMap2() {
		return map2;
	}

	public void setMap2(Map<String, String> map2) {
		this.map2 = map2;
	}

	public Map<String, String> getMap3() {
		return map3;
	}

	public void setMap3(Map<String, String> map3) {
		this.map3 = map3;
	}

	public Map<String, String> getMap4() {
		return map4;
	}

	public void setMap4(Map<String, String> map4) {
		this.map4 = map4;
	}
	
	public String getCdatribuRol(String auxiliarProductoCdtipsit)
	{
		log.error("DEPRECATED getCdatribuRol",new Exception("DEPRECATED getCdatribuRol"));
		return null;
	}
	
	public String getCdatribuSexo(String auxiliarProductoCdtipsit)
	{
		log.error("DEPRECATED getCdatribuSexo",new Exception("DEPRECATED getCdatribuSexo"));
		return null;
	}
	
	public String pantallaJavaExterno()
	{
		log.info(""
				+ "\n#################################"
				+ "\n###### pantallaJavaExterno ######"
				);
		log.info(""
				+ "\n###### pantallaJavaExterno ######"
				+ "\n#################################"
				);
		return SUCCESS;
	}
	
	public String operacionJavaExterno()
	{
		log.info(""
				+ "\n##################################"
				+ "\n###### operacionJavaExterno ######"
				);
		log.info("panel1: "+panel1);
		String a = panel1.get("a");
		String b = panel1.get("b");
		String c = null;
		try
		{
			File carpeta         = new File("/opt/ice/gseguros");
			String clase         = "Probando";
			String metodo        = "prueba";
			Class<?>[]parametros = new Class[]
					{
					String.class
					,String.class
					};
			ClassLoader loader = new URLClassLoader(new URL[]{carpeta.toURI().toURL()},getClass().getClassLoader());
			Class<?>    cls    = loader.loadClass(clase);
			Method      m      = cls.getMethod(metodo, parametros);
			
			c=(String)m.invoke(cls.newInstance(),a,b);
			log.debug("c: "+c);
			panel1.put("c",c);
		}
		catch(Exception ex)
		{
			log.error("error:",ex);
		}
		log.info(""
				+ "\n###### operacionJavaExterno ######"
				+ "\n##################################"
				);
		return SUCCESS;
	}
	
	public String pantallaCompiladora()
	{
		log.info(""
				+ "\n#################################"
				+ "\n###### pantallaCompiladora ######"
				);
		log.info(""
				+ "\n###### pantallaCompiladora ######"
				+ "\n#################################"
				);
		return SUCCESS;
	}
	
	public String compilarProceso()
	{
		log.info(""
				+ "\n#############################"
				+ "\n###### compilarProceso ######"
				);
		//ENTRADA
		log.info("map1: "+map1);
		
		//VARIABLES DE ENTRADA OBTENIDAS DEL MAPA
		String archivo = map1.get("archivo");
		String codigo  = map1.get("codigo");
		log.info("archivo: "+archivo);
		log.info("codigo: "+codigo);
		
		//PROPIEDAD PARA EL SUBMIT DE EXT
		success = true;
		
		exito = true;
		
		//ESCRIBIR ARCHIVO JAVA
		if(exito)
		{
			try
			{
				File archivoJava = new File(archivo);
				PrintStream streamArchivoJava = new PrintStream(archivoJava);
				streamArchivoJava.print(codigo);
				streamArchivoJava.close();
			}
			catch(Exception ex)
			{
				log.error("error al crear archivo java",ex);
				mensajeRespuesta=ex.getMessage();
				exito=false;
			}
		}
		
		//RECONOCER SISTEMA OPERATIVO
		boolean esWindows = false;
		if(exito)
		{
			if(System.getProperty("os.name").toLowerCase().indexOf("windows") != -1)
			{
				esWindows = true;
			}
			log.info("es windows: "+esWindows);
		}
		
		//COMPILAR
		if(exito)
		{
			try
			{
				String[] finalCommand; 
				if(esWindows)
				{
					finalCommand = new String[4];
			        // Use the appropriate path for your windows version.
			        //finalCommand[0] = "C:\\winnt\\system32\\cmd.exe";    // Windows NT/2000
			        finalCommand[0] = "C:\\windows\\system32\\cmd.exe";    // Windows XP/2003
			        //finalCommand[0] = "C:\\windows\\syswow64\\cmd.exe";  // Windows 64-bit
			        finalCommand[1] = "/y";
			        finalCommand[2] = "/c";
			        finalCommand[3] = "javac "+archivo;
				}
				else
				{
					finalCommand = new String[3];
			        finalCommand[0] = "/bin/sh";
			        finalCommand[1] = "-c";
			        finalCommand[2] = "javac "+archivo;
				}
				
				//EJECUTAR COMANDO
				final Process pr = Runtime.getRuntime().exec(finalCommand);
				pr.waitFor();
				
				//ESCUCHAR ERRORES
				mensajeRespuesta="";
				BufferedReader br_err = null;
				try
				{
					br_err = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
					String buff = null;
					while ((buff = br_err.readLine()) != null)
					{
						log.error("Error :" + buff);
						mensajeRespuesta=mensajeRespuesta+(buff.replaceAll(" ", "&nbsp;"))+"<br/>";
						exito=false;
						try
						{
							Thread.sleep(1);
						} catch(Exception e) {}
					}
					br_err.close();
				}
				catch (Exception ex)
				{
					log.error("Error al imprimir errores de proceso",ex);
					mensajeRespuesta=mensajeRespuesta+ex;
					exito=false;
				}
				finally
				{
					try
					{
						br_err.close();
					} catch (Exception ex) {}
				}
			}
			catch(Exception ex)
			{
				log.error("error al compilar",ex);
				mensajeRespuesta=ex.getMessage();
				exito=false;				
			}
		}
		
		log.info(""
				+ "\n###### compilarProceso ######"
				+ "\n#############################"
				);
		return SUCCESS;
	}
	
	public String redireccion()
	{
		log.info(""
				+ "\n#########################"
				+ "\n###### redireccion ######"
				);
		log.info("map1: "+map1);
		log.info(""
				+ "\n###### redireccion ######"
				+ "\n#########################"
				);
		return SUCCESS;
	}

	public List<Map<String, Object>> getList1() {
		return list1;
	}

	public void setList1(List<Map<String, Object>> list1) {
		this.list1 = list1;
	}

	public List<Map<String, Object>> getList2() {
		return list2;
	}

	public void setList2(List<Map<String, Object>> list2) {
		this.list2 = list2;
	}

	public List<Map<String, Object>> getList3() {
		return list3;
	}

	public void setList3(List<Map<String, Object>> list3) {
		this.list3 = list3;
	}

	public List<Map<String, Object>> getList4() {
		return list4;
	}

	public void setList4(List<Map<String, Object>> list4) {
		this.list4 = list4;
	}

	public Map<String, Object> getOmap1() {
		return omap1;
	}

	public void setOmap1(Map<String, Object> omap1) {
		this.omap1 = omap1;
	}

	public String getCdperson() {
		return cdperson;
	}

	public void setCdperson(String cdperson) {
		this.cdperson = cdperson;
	}

	public List<Map<String, String>> getSlist1() {
		return slist1;
	}

	public void setSlist1(List<Map<String, String>> slist1) {
		this.slist1 = slist1;
	}

	public GridVO getGridResultados() {
		return gridResultados;
	}

	public void setGridResultados(GridVO gridResultados) {
		this.gridResultados = gridResultados;
	}

	public void setIce2sigsService(Ice2sigsService ice2sigsService) {
		this.ice2sigsService = ice2sigsService;
	}

	public void setRecibosSigsService(RecibosSigsService recibosSigsService) {
		this.recibosSigsService = recibosSigsService;
	}

	public String getMensajeRespuesta() {
		return mensajeRespuesta;
	}

	public void setMensajeRespuesta(String mensajeRespuesta) {
		this.mensajeRespuesta = mensajeRespuesta;
	}

	public String getCdtipsit() {
		return cdtipsit;
	}

	public void setCdtipsit(String cdtipsit) {
		this.cdtipsit = cdtipsit;
	}

	public void setCatalogosManager(CatalogosManager catalogosManager) {
		this.catalogosManager = catalogosManager;
	}

	public void setConsultasManager(ConsultasManager consultasManager) {
		this.consultasManager = consultasManager;
	}

	public boolean isExito() {
		return exito;
	}

	public void setExito(boolean exito) {
		this.exito = exito;
	}
	
	public void setPantallasManager(PantallasManager pantallasManager) {
		this.pantallasManager = pantallasManager;
	}

	public Item getItem5() {
		return item5;
	}

	public void setItem5(Item item5) {
		this.item5 = item5;
	}
	
	public void setEmisionAutosService(EmisionAutosService emisionAutosService) {
		this.emisionAutosService = emisionAutosService;
	}

	public boolean isClienteWS() {
		return clienteWS;
	}

	public void setClienteWS(boolean clienteWS) {
		this.clienteWS = clienteWS;
	}

	public void setStoredProceduresManager(
			StoredProceduresManager storedProceduresManager) {
		this.storedProceduresManager = storedProceduresManager;
	}

}