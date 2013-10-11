/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.gseguros.portal.cotizacion.controller;

import java.io.File;
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
import mx.com.aon.flujos.cotizacion4.web.ResultadoCotizacion4Action;
import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.cotizacion.model.DatosUsuario;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.Tatri;
import mx.com.gseguros.portal.general.util.ConstantesCatalogos;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.utils.HttpUtil;
import mx.com.gseguros.ws.client.Ice2sigsWebServices;
import mx.com.gseguros.ws.client.Ice2sigsWebServices.Operacion;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.ClienteSalud;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.ClienteSaludRespuesta;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.Recibo;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.ReciboRespuesta;

import org.apache.struts2.ServletActionContext;

/**
 * 
 * @author Jair
 */
public class ComplementariosAction extends PrincipalCoreAction implements
		ConstantesCatalogos {

	private static final long serialVersionUID = -1269892388621564059L;
	private org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(ComplementariosAction.class);
	private Item items;
	private Item fields;
	
	private KernelManagerSustituto kernelManager;
	private transient Ice2sigsWebServices ice2sigsWebServices;
	
	private Map<String, String> panel1;
	private Map<String, String> panel2;
	private Map<String, String> parametros;
	private String cdunieco;
	private String cdramo;
	private String estado;
	private String nmpoliza;
	private boolean success = true;
	private ScreenInterceptor scrInt = new ScreenInterceptor();
	SimpleDateFormat renderFechas = new SimpleDateFormat("dd/MM/yyyy");
	Calendar calendarHoy = Calendar.getInstance();
	private Item item1;
	private Item item2;
	private Item item3;
	private Item item4;
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
			List<Tatri>listaTatrisit=kernelManager.obtenerTatripol(new String[]{cdramo});
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
            
            parametros.putAll(map1);
            parametros.put("pv_status", "W");
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
		log.debug("map1: "+map1);
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
					.add(Item.crear("editor",null,Item.OBJ)
							.add("xtype","textfield")
							.add("allowBlank",false)
						)
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
							.add("allowBlank",false)
						)
					);
			item3.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("header", "Apellido materno"))
					.add(new Item("dataIndex", "Apellido_Materno"))
					.add(new Item("flex", 1))
					.add(Item.crear("editor",null,Item.OBJ)
							.add("xtype","textfield")
							.add("allowBlank",false)
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
					.add(new Item("flex", 1))
					.add(Item.crear("editor",null,Item.OBJ)
							.add("xtype","textfield")
							.add("allowBlank",false)
							)
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
					.add(Item.crear("editor","editorGenerosp2").setQuotes(""))
					);
			item2.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("header", "T. Persona"))
					.add(new Item("dataIndex", "tpersona"))
					.add(new Item("flex", 1))
					.add(Item.crear("renderer","rendererTpersonap2").setQuotes(""))
					.add(Item.crear("editor","editorTpersonap2").setQuotes(""))
					);
			item2.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("header", "Nacionalidad"))
					.add(new Item("dataIndex", "nacional"))
					.add(new Item("flex", 1))
					.add(Item.crear("renderer","rendererNacionesp2").setQuotes(""))
					.add(Item.crear("editor","editorNacionesp2").setQuotes(""))
					);
			item2.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("header", "RFC"))
					.add(new Item("dataIndex", "cdrfc"))
					.add(new Item("flex", 1))
					.add(Item.crear("editor",null,Item.OBJ)
							.add("xtype","textfield")
							.add("allowBlank",false)
						)
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
					.add(new Item("width", 80))
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
		} catch (Exception ex) {
			log.error("error al generar los campos dinamicos", ex);
			item1 = null;
		}
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
			int i=1;
			for(Map<String,Object>aseg:list1)
			{
				Map<String,Object>parametros=new LinkedHashMap<String,Object>(0);
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
				parametros.put("pv_cdtipide_i","1");// 												OracleTypes.VARCHAR));// IN  MPERSONA.cdtipide%TYPE DEFAULT NULL, Valor por default 1
				parametros.put("pv_cdideper_i", (String)aseg.get("cdrfc"));//						OracleTypes.VARCHAR));// IN  MPERSONA.cdideper%TYPE DEFAULT NULL, Valor de CDRFC
				parametros.put("pv_dsnombre_i",(String)aseg.get("nombre"));// 						OracleTypes.VARCHAR));// IN  MPERSONA.dsnombre%TYPE DEFAULT NULL,
				parametros.put("pv_cdtipper_i","1");// 												OracleTypes.VARCHAR));// IN  MPERSONA.cdtipper%TYPE DEFAULT NULL, Valor por default 1
				parametros.put("pv_otfisjur_i",(String)aseg.get("tpersona"));// 												OracleTypes.VARCHAR));// IN  MPERSONA.otfisjur%TYPE DEFAULT NULL, Valor por default F
				parametros.put("pv_otsexo_i",(String)aseg.get("sexo"));// 							OracleTypes.VARCHAR));// IN  MPERSONA.otsexo%TYPE DEFAULT NULL,
				parametros.put("pv_fenacimi_i",renderFechas.parse((String)aseg.get("fenacimi")));//	OracleTypes.VARCHAR));// IN  MPERSONA.fenacimi%TYPE DEFAULT NULL,
				parametros.put("pv_cdrfc_i",(String)aseg.get("cdrfc"));// 							OracleTypes.VARCHAR));// IN  MPERSONA.cdrfc%TYPE DEFAULT NULL,
				parametros.put("pv_dsemail_i","");// 		OracleTypes.VARCHAR));// 				IN  MPERSONA.dsemail%TYPE DEFAULT NULL,  Valor de email o nulo,
				parametros.put("pv_dsnombre1_i",(String)aseg.get("segundo_nombre"));// 				OracleTypes.VARCHAR));// IN  MPERSONA.dsnombre1%TYPE DEFAULT NULL,
				parametros.put("pv_dsapellido_i",(String)aseg.get("Apellido_Paterno"));// 			OracleTypes.VARCHAR));// IN  MPERSONA.dsapellido%TYPE DEFAULT NULL,
				parametros.put("pv_dsapellido1_i",(String)aseg.get("Apellido_Materno"));// 			OracleTypes.VARCHAR));// IN  MPERSONA.dsapellido1%TYPE DEFAULT NULL,
				parametros.put("pv_feingreso_i", calendarHoy.getTime());//		OracleTypes.VARCHAR));// IN  MPERSONA.feingreso%TYPE DEFAULT NULL,  Valor por default SYSDATE
				parametros.put("pv_cdnacion_i",(String)aseg.get("nacional"));
				parametros.put("pv_accion_i", "I");//												OracleTypes.VARCHAR));//
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
				log.debug("#iteracion mov mpoliper "+i);
				if(((String)aseg.get("nmsituac")).equals("0"))
				{
					kernelManager.borraMpoliper(parametros);
				}
				kernelManager.movMpoliper(parametros);
				
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
			
			UserVO usuario=(UserVO)session.get("USUARIO");
			DatosUsuario datosUsuario=kernelManager.obtenerDatosUsuario(usuario.getUser());
			
			////////////////////////////////
			////// retarifica         //////
			/*////////////////////////////*/
			Map<String,String> mapaTarificacion=new HashMap<String,String>(0);
	        mapaTarificacion.put("pv_cdusuari_i",   usuario.getUser());
	        mapaTarificacion.put("pv_cdelemen_i",   usuario.getEmpresa().getElementoId());
	        mapaTarificacion.put("pv_cdunieco_i",   datosUsuario.getCdunieco());
	        mapaTarificacion.put("pv_cdramo_i",     datosUsuario.getCdramo());
	        mapaTarificacion.put("pv_estado_i",     "W");
	        mapaTarificacion.put("pv_nmpoliza_i",   panel1.get("nmpoliza"));
	        mapaTarificacion.put("pv_nmsituac_i",   "0");
	        mapaTarificacion.put("pv_nmsuplem_i",   "0");
	        mapaTarificacion.put("pv_cdtipsit_i",   datosUsuario.getCdtipsit());
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
			paramObtenerPoliza.put("pv_cdunieco" , datosUsuario.getCdunieco());
			paramObtenerPoliza.put("pv_cdramo"   , datosUsuario.getCdramo());
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
			paramDetallePoliza.put("pv_cdunieco_i" , datosUsuario.getCdunieco());
			paramDetallePoliza.put("pv_cdramo_i"   , datosUsuario.getCdramo());
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
		/**
		 * TODO: Poner variable el cdTipSit de la poliza y la sucursal
		 */
		String cdtipsit = "213";
		String sucursal = "1000";
		
		log.debug(""
				+ "\n########################"
				+ "\n########################"
				+ "\n######            ######"
				+ "\n######   emitir   ######"
				+ "\n######            ######"
				+ "");
		try
		{
			log.debug("panel1"+panel1);
			log.debug("panel2"+panel2);
			
			UserVO usu=(UserVO)session.get("USUARIO");
			DatosUsuario datUsu=kernelManager.obtenerDatosUsuario(usu.getUser());
			
			/*list1=kernelManager.obtenerAsegurados(panel2);
			log.debug("asegurados para iterar reportes:");
			log.debug(list1);*/
			
			String rutaCarpeta=this.getText("ruta.documentos.poliza")+"/"+panel1.get("pv_ntramite");
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
            	}
            }
            else
            {
            	log.debug("existe la carpeta   ::: "+rutaCarpeta);
            }
			
			//obtener usuario y datos de ususario
			UserVO us=(UserVO)session.get("USUARIO");
			DatosUsuario datUs=kernelManager.obtenerDatosUsuario(us.getUser());
			
			//obtener poliza completa
            /**/
			Map<String,String>paramObtenerPoliza=new LinkedHashMap<String,String>(0);
			paramObtenerPoliza.put("pv_cdunieco" , datUs.getCdunieco());
			paramObtenerPoliza.put("pv_cdramo"   , datUs.getCdramo());
			paramObtenerPoliza.put("pv_estado"   , "W");
			paramObtenerPoliza.put("pv_nmpoliza" , panel1.get("pv_nmpoliza"));
			paramObtenerPoliza.put("pv_cdusuari" , us.getUser());
			Map<String,Object>polizaCompleta=kernelManager.getInfoMpolizasCompleta(paramObtenerPoliza);
			log.debug("poliza a emitir: "+polizaCompleta);
			/**/
			
			/*
			pv_cdusuari
            pv_cdunieco
            pv_cdramo
            pv_estado
            pv_nmpoliza
            pv_nmsituac
            pv_nmsuplem
            pv_cdelement
            pv_cdcia
            pv_cdplan
            pv_cdperpag
            pv_cdperson
            pv_fecha
            */
			Map<String,Object>paramEmi=new LinkedHashMap<String,Object>(0);
			paramEmi.put("pv_cdusuari"  , us.getUser());
			paramEmi.put("pv_cdunieco"  , datUs.getCdunieco());
			paramEmi.put("pv_cdramo"    , datUs.getCdramo());
			paramEmi.put("pv_estado"    , "W");
			paramEmi.put("pv_nmpoliza"  , panel1.get("pv_nmpoliza"));
			paramEmi.put("pv_nmsituac"  , "1");
			paramEmi.put("pv_nmsuplem"  , "0");
			paramEmi.put("pv_cdelement" , us.getEmpresa().getElementoId()); 
			paramEmi.put("pv_cdcia"     , datUs.getCdunieco());
			paramEmi.put("pv_cdplan"    , null);
			paramEmi.put("pv_cdperpag"  , (String)polizaCompleta.get("cdperpag"));
			paramEmi.put("pv_cdperson"  , datUs.getCdperson());
			paramEmi.put("pv_fecha"     , new Date());
			paramEmi.put("pv_ntramite"  , panel1.get("pv_ntramite"));
			mx.com.aon.portal.util.WrapperResultados wr=kernelManager.emitir(paramEmi);
			log.debug("emision obtenida "+wr.getItemMap());
			panel2=new HashMap<String,String>(0);
			panel2.put("nmpoliza",(String)wr.getItemMap().get("nmpoliza"));
			panel2.put("nmpoliex",(String)wr.getItemMap().get("nmpoliex"));
			/**/
			
			Map<String,String>paramsGetDoc=new LinkedHashMap<String,String>(0);
			paramsGetDoc.put("pv_cdunieco_i" , datUs.getCdunieco());
			paramsGetDoc.put("pv_cdramo_i"   , datUs.getCdramo());
			paramsGetDoc.put("pv_estado_i"   , "M");
			paramsGetDoc.put("pv_nmpoliza_i" , panel2.get("nmpoliza"));
			paramsGetDoc.put("pv_nmsuplem_i" , "0");
			paramsGetDoc.put("pv_ntramite_i" , panel1.get("pv_ntramite"));
			List<Map<String,String>>listaDocu=kernelManager.obtenerListaDocumentos(paramsGetDoc);
			//listaDocu contiene: nmsolici,nmsituac,descripc,descripl
			for(Map<String,String> docu:listaDocu)
			{
				log.debug("docu iterado: "+docu);
				String nmsolici=docu.get("nmsolici");
				String nmsituac=docu.get("nmsituac");
				String descripc=docu.get("descripc");
				String descripl=docu.get("descripl");
				String url=this.getText("ruta.servidor.reports")
						+ "?destype=cache"
						+ "&desformat=PDF"
						+ "&userid="+this.getText("pass.servidor.reports")
						+ "&report="+descripl
						+ "&paramform=no"
						+ "&p_unieco=1"
						+ "&p_ramo=2"
						+ "&p_estado='M'"
						+ "&p_poliza="+panel2.get("nmpoliza")
						+ "&desname="+rutaCarpeta+"/"+descripc;
				log.debug(""
						+ "\n#################################"
						+ "\n###### Se solicita reporte ######"
						+ "\na "+url+""
						+ "\n#################################");
				HttpUtil.generaArchivo(url,rutaCarpeta+"/"+descripc);
				log.debug(""
						+ "\n######                    ######"
						+ "\n###### reporte solicitado ######"
						+ "\na "+url+""
						+ "\n################################"
						+ "\n################################"
						+ "");
			}
			
			
			if(!ejecutaWSclienteSalud(datUs.getCdunieco(), datUs.getCdramo(), "M", (String)wr.getItemMap().get("nmpoliza"), (String)wr.getItemMap().get("nmsuplem"))){
				logger.error("NO SE HA EJECUTADO CORRECTAMENTE EL WS DE CLIENTE SALUD!!! PRIMER INTENTO, POLIZA:" + (String)wr.getItemMap().get("nmpoliza"));
			
				if(!ejecutaWSclienteSalud(datUs.getCdunieco(), datUs.getCdramo(), "M", (String)wr.getItemMap().get("nmpoliza"), (String)wr.getItemMap().get("nmsuplem"))){
					logger.error("NO SE HA EJECUTADO CORRECTAMENTE EL WS DE CLIENTE SALUD!!! SEGUNDO INTENTO, POLIZA:" + (String)wr.getItemMap().get("nmpoliza"));
					mensajeRespuesta = "No se ha ejecutado correctamente el WS para el guardado del ClienteSalud";
				}else{
					if(!ejecutaWSrecibos(datUs.getCdunieco(), datUs.getCdramo(),
							"M", (String)wr.getItemMap().get("nmpoliza"),
							(String)wr.getItemMap().get("nmsuplem"), rutaCarpeta,
							cdtipsit, sucursal, panel1.get("pv_nmpoliza"),panel1.get("pv_ntramite")
							)){
						logger.error("NO SE HAN INSERTADO TODOS LOS RECIBOS!!! EN ICE2SIGS, DE LA POLIZA: " + (String)wr.getItemMap().get("nmpoliza"));
						mensajeRespuesta = "No se han ejecutado correctamente los WS para el guardado de los recibos";
					}
				}
			
			}else{
				
				if(!ejecutaWSrecibos(datUs.getCdunieco(), datUs.getCdramo(),
						"M", (String)wr.getItemMap().get("nmpoliza"),
						(String)wr.getItemMap().get("nmsuplem"), rutaCarpeta,
						cdtipsit, sucursal, panel1.get("pv_nmpoliza"),panel1.get("pv_ntramite")
						)){
					logger.error("NO SE HAN INSERTADO TODOS LOS RECIBOS!!! EN ICE2SIGS, DE LA POLIZA: " + (String)wr.getItemMap().get("nmpoliza"));
					mensajeRespuesta = "No se han ejecutado correctamente los WS para el guardado de los recibos";
				}
			}
				
			log.debug("se inserta detalle nuevo para emision");
        	Map<String,Object>parDmesCon=new LinkedHashMap<String,Object>(0);
        	parDmesCon.put("pv_ntramite_i"   , panel1.get("pv_ntramite"));
        	parDmesCon.put("pv_feinicio_i"   , new Date());
        	parDmesCon.put("pv_cdclausu_i"   , null);
        	parDmesCon.put("pv_comments_i"   , "El tr&aacute;mite se emiti&oacute;");
        	parDmesCon.put("pv_cdusuari_i"   , datUsu.getCdusuari());
        	kernelManager.movDmesacontrol(parDmesCon);
			
			success=true;
		}
		catch(Exception ex)
		{
			log.debug("Error al emitir",ex);
			success=false;
		}
		log.debug(""
				+ "\n######            ######"
				+ "\n######   emitir   ######"
				+ "\n######            ######"
				+ "\n########################"
				+ "\n########################"
				+ "");
		return SUCCESS;
	}
	
	private boolean ejecutaWSrecibos(String cdunieco, String cdramo, String estado, String nmpoliza,
			String nmsuplem, String rutaPoliza, String cdtipsit, String sucursal, String nmsolici,String ntramite){
		boolean allInserted = true;
		
		logger.debug("*** Entrando a metodo Inserta Recibos WS ice2sigs, para la poliza: " + nmpoliza + "***");
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdunieco_i", cdunieco);
		params.put("pv_cdramo_i", cdramo);
		params.put("pv_estado_i", estado);
		params.put("pv_nmpoliza_i", nmpoliza);
		params.put("pv_nmsuplem_i", nmsuplem);//0
		
		WrapperResultados result = null;
		ArrayList<Recibo> recibos =  null;
		try {
			result = kernelManager.obtenDatosRecibos(params);
			recibos = (ArrayList<Recibo>) result.getItemList();
		} catch (Exception e1) {
			logger.error("Error en llamar al PL de obtencion de RECIBOS",e1);
			return false;
		}
		 
		for(Recibo recibo: recibos){
			try{
				ReciboRespuesta resultadoR = ice2sigsWebServices.ejecutaReciboGS(Operacion.INSERTA, recibo, this.getText("url.ws.ice2sigs"));
				logger.debug("WS Resultado de insertar el recibo: " + recibo.getNumRec()+ " - " + resultadoR.getMensaje());
			}catch(Exception e){
				logger.error("WS PRIMER INTENTO Error al insertar el recibo: " + recibo.getNumRec(), e);
				try{
					ReciboRespuesta resultadoR = ice2sigsWebServices.ejecutaReciboGS(Operacion.INSERTA, recibo, this.getText("url.ws.ice2sigs"));
					logger.debug("WS Resultado de insertar el recibo: " + recibo.getNumRec()+ " - " + resultadoR.getMensaje());
				}catch(Exception e2){
					logger.error("WS SEGUNDO INTENTO Error al insertar el recibo: " + recibo.getNumRec(), e2);
					allInserted = false;
				}
			}
		}
		
		/**
		 * PARA EL GUARDADO CADA PDF DE RECIBO
		 */
		logger.debug("*** Empieza generacion de URLs para Recibos ***");
		for(Recibo recibo: recibos){
			try{
//				Parametro1:  9999: Recibo
//				Parametro2:  Siempre va en 0
//				Parametro3:  Sucursal
//				Parametro4:  Ramo (213 o 214)
//				Parametro5:  Poliza
//				Parametro6:  Tramite(poner 0)
//				Parametro7:  Numero de endoso (Cuando es poliza nueva poner 0)
//				Parametro8:  Tipo de endoso (Si es vacio no enviar nada en otro caso poner A o D segun sea el caso)
//				Parametro9:  Numero de recibo (1,2,3..segun la forma de pago) Para nuestro caso es siempre el 1
				//if( 1 == recibo.getNumRec()){
					String parametros = "?9999,0,"+sucursal+","+cdtipsit+","+nmpoliza+",0,0,,"+recibo.getNumRec();
					logger.debug("URL Generada para Recibo: "+ this.getText("url.imp.recibos")+parametros);
					//HttpRequestUtil.generaReporte(this.getText("url.imp.recibos")+parametros, rutaPoliza+"/Recibo_"+recibo.getRmdbRn()+"_"+recibo.getNumRec()+".pdf");
					
					HashMap<String, Object> paramsR =  new HashMap<String, Object>();
					paramsR.put("pv_cdunieco_i", cdunieco);
					paramsR.put("pv_cdramo_i", cdramo);
					paramsR.put("pv_estado_i", estado);
					paramsR.put("pv_nmpoliza_i", nmpoliza);
					paramsR.put("pv_nmsuplem_i", nmsuplem);
					paramsR.put("pv_feinici_i", new Date());
					paramsR.put("pv_cddocume_i", this.getText("url.imp.recibos")+parametros);
					paramsR.put("pv_dsdocume_i", "Recibo "+recibo.getNumRec());
					paramsR.put("pv_nmsolici_i", nmsolici);
					paramsR.put("pv_ntramite_i", ntramite);
					
					kernelManager.guardarArchivo(paramsR);
				//}
			}catch(Exception e){
				logger.error("Error al guardar indexaxion de recibo: " + recibo.getRmdbRn(), e);
			}
		}

		return allInserted;
	} 

	private boolean ejecutaWSclienteSalud(String cdunieco, String cdramo, String estado, String nmpoliza, String nmsuplem){
		boolean exito = true;
		
		logger.debug("*** Entrando a metodo ejecutaWSclienteSalud ***");
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdunieco_i", cdunieco);
		params.put("pv_cdramo_i", cdramo);
		params.put("pv_estado_i", estado);
		params.put("pv_nmpoliza_i", nmpoliza);
//		params.put("pv_nmsuplem_i", nmsuplem);
		
		WrapperResultados result = null;
		ClienteSalud cliente =  null;
		try {
			result = kernelManager.obtenDatosClienteWS(params);
			cliente = ((ArrayList<ClienteSalud>) result.getItemList()).get(0);
		} catch (Exception e1) {
			logger.error("Error en llamar al PL de obtencion de ejecutaWSclienteSalud",e1);
			return false;
		}
		
		try{
			ClienteSaludRespuesta resultadoR = ice2sigsWebServices.ejecutaClienteSaludGS(Operacion.INSERTA, cliente, this.getText("url.ws.ice2sigs"));
			logger.debug("Resultado de insertar el cliente salud: " + cliente.getClaveCli()+ " - " + resultadoR.getMensaje());
		}catch(Exception e){
			logger.error("Error al insertar el cliente: " + cliente.getClaveCli(), e);
			exito = false;
		}

		return exito;
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

	public String getCON_CAT_POL_ESTADO() {
		return CON_CAT_POL_ESTADO;
	}

	public String getCON_CAT_POL_TIPO_POLIZA() {
		return CON_CAT_POL_TIPO_POLIZA;
	}

	public String getCON_CAT_POL_TIPO_PAGO() {
		return CON_CAT_POL_TIPO_PAGO;
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
	
	public String getCdatribuRol()
	{
		return ResultadoCotizacion4Action.cdatribuRol;
	}
	
	public String getCdatribuSexo()
	{
		return ResultadoCotizacion4Action.cdatribuSexo;
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

	public String getCON_CAT_POL_ROL() {
		return CON_CAT_POL_ROL;
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

	public String getCON_CAT_MESACONTROL_SUCUR_ADMIN() {
		return CON_CAT_MESACONTROL_SUCUR_ADMIN;
	}

	public String getCON_CAT_MESACONTROL_SUCUR_DOCU() {
		return CON_CAT_MESACONTROL_SUCUR_DOCU;
	}

	public String getCON_CAT_MESACONTROL_TIP_TRAMI() {
		return CON_CAT_MESACONTROL_TIP_TRAMI;
	}

	public String getCON_CAT_MESACONTROL_ESTAT_TRAMI() {
		return CON_CAT_MESACONTROL_ESTAT_TRAMI;
	}

	public void setIce2sigsWebServices(Ice2sigsWebServices ice2sigsWebServices) {
		this.ice2sigsWebServices = ice2sigsWebServices;
	}

	public String getCON_CAT_TPERSONA() {
		return CON_CAT_TPERSONA;
	}

	public String getCON_CAT_NACIONALIDAD() {
		return CON_CAT_NACIONALIDAD;
	}

	public String getMensajeRespuesta() {
		return mensajeRespuesta;
	}

	public void setMensajeRespuesta(String mensajeRespuesta) {
		this.mensajeRespuesta = mensajeRespuesta;
	}

}