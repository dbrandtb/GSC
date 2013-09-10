/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.gseguros.portal.cotizacion.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.flujos.cotizacion4.web.ResultadoCotizacion4Action;
import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.portal.cotizacion.model.DatosUsuario;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.general.util.ConstantesCatalogos;

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

	public String mostrarPantalla()
	/*
    
    */
	{
		return scrInt.intercept(this,
				ScreenInterceptor.PANTALLA_COMPLEMENTARIOS_GENERAL);
	}

	public String mostrarPantallaGeneral() {
		try {
			// List<Tatrisit>listaTatrisit=kernelManager.obtenerTatrisit("SL");
			// GeneradorCampos gc=new GeneradorCampos();
			// gc.genera(listaTatrisit);
			// items=gc.getItems();
			// fields=gc.getFields();
			fields = new Item("fields", null, Item.ARR);

			fields.add(Item.crear(null, null, Item.OBJ).add(
					new Item("name", "panel1.dsciaaseg")));
			fields.add(Item.crear(null, null, Item.OBJ).add(
					new Item("name", "panel1.nombreagente")));
			fields.add(Item.crear(null, null, Item.OBJ).add(
					new Item("name", "panel1.dsramo")));

			fields.add(Item.crear(null, null, Item.OBJ).add(
					new Item("name", "panel2.nmpoliza")));
			fields.add(Item.crear(null, null, Item.OBJ).add(
					new Item("name", "panel2.estado")));
			fields.add(Item.crear(null, null, Item.OBJ).add(
					new Item("name", "panel2.fesolici")));
			fields.add(Item.crear(null, null, Item.OBJ).add(
					new Item("name", "panel2.solici")));
			fields.add(Item.crear(null, null, Item.OBJ).add(
					new Item("name", "panel2.feefec")));
			fields.add(Item.crear(null, null, Item.OBJ).add(
					new Item("name", "panel2.ferenova")));
			fields.add(Item.crear(null, null, Item.OBJ).add(
					new Item("name", "panel2.cdtipopol")));
			fields.add(Item.crear(null, null, Item.OBJ).add(
					new Item("name", "panel2.cdperpag")));
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

			// ///////////////////////////////////
			// //// Cargar info de mpolizas //////
			/* ///////////////////////////////// */
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
			/* ///////////////////////////////// */
			// //// Cargar info de mpolizas //////
			// ///////////////////////////////////
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
			item1.add(Item.crear(null, null, Item.OBJ).add(new Item("name", "fenacimi")));
			item1.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("name", "sexo"))
					.add(new Item("type", "Generic"))
					);
			item1.add(Item.crear(null, null, Item.OBJ).add(new Item("name", "cdperson")));
			item1.add(Item.crear(null, null, Item.OBJ).add(new Item("name", "nombre")));
			item1.add(Item.crear(null, null, Item.OBJ).add(new Item("name", "segundo_nombre")));
			item1.add(Item.crear(null, null, Item.OBJ).add(new Item("name", "Apellido_Paterno")));
			item1.add(Item.crear(null, null, Item.OBJ).add(new Item("name", "Apellido_Materno")));
			item1.add(Item.crear(null, null, Item.OBJ).add(new Item("name", "cdrfc")));
			
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

			item2 = new Item("columns", null, Item.ARR);// para las columnas del grid
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
					.add(new Item("header", "Rol"))
					.add(new Item("dataIndex", "cdrol"))
					.add(new Item("flex", 1))
					.add(Item.crear("renderer","rendererRol").setQuotes(""))
					.add(Item.crear("editor","editorRoles").setQuotes(""))
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
					.add(Item.crear("editor",null,Item.OBJ)
							.add("xtype","datefield")
							.add("allowBlank",false)
							.add("format","d/m/Y")
						)
					.add(Item.crear("renderer","Ext.util.Format.dateRenderer('d M Y')").setQuotes(""))
					);
			item2.add(Item.crear(null, null, Item.OBJ)
					.add(new Item("header", "Sexo"))
					.add(new Item("dataIndex", "sexo"))
					.add(new Item("flex", 1))
					.add(Item.crear("renderer","rendererSexo").setQuotes(""))
					.add(Item.crear("editor","editorGeneros").setQuotes(""))
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
			list1=kernelManager.obtenerAsegurados(map1);
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
		success=true;
		return SUCCESS;
	}

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

}