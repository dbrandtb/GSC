package mx.com.aon.flujos.cotizacion.web;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import mx.com.aon.configurador.pantallas.model.PantallaVO;
import mx.com.aon.configurador.pantallas.model.components.ComboClearOnSelectVO;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.core.VariableKernel;
import mx.com.aon.flujos.cotizacion.model.CoberturaCotizacionVO;
import mx.com.aon.flujos.cotizacion.model.DatosEntradaCotizaVO;
import mx.com.aon.flujos.cotizacion.model.MPoliObjVO;
import mx.com.aon.flujos.cotizacion.model.ReciboVO;
import mx.com.aon.flujos.cotizacion.model.RolVO;
import mx.com.aon.flujos.cotizacion.service.CotizacionService;
import mx.com.aon.kernel.service.KernelManager;
import mx.com.aon.pdfgenerator.PDFGenerator;
import mx.com.aon.portal.model.AtributosVariablesInstPagoVO;
import mx.com.aon.portal.model.BaseObjectVO;
import mx.com.aon.portal.model.FourValueObjectVO;
import mx.com.aon.portal.model.InstrumentoPagoAtributosVO;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.service.CatalogService;
import mx.com.aon.portal.service.CatalogServiceJdbcTemplate;
import mx.com.aon.portal.service.InstrumentoPagoManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.utils.Constantes;
import mx.com.ice.services.business.ServiciosGeneralesSistema;
import mx.com.ice.services.to.screen.GlobalVariableContainerVO;
import net.sf.json.JSONFunction;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.biosnet.ice.ext.elements.form.AbstractMasterModelControl;
import com.biosnet.ice.ext.elements.form.SimpleCombo;
import com.biosnet.ice.ext.elements.form.TextFieldControl;

public class ComprarCotizacionAction  extends PrincipalCotizacionAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1942936702744067015L;
	protected final transient Logger log = Logger.getLogger(ComprarCotizacionAction.class);
	
	private static final String CDSISROL_ASEGURADO = "ASEGURADO";
	private static final String RAMO_POZOS_PETROLEROS = "6";
	@SuppressWarnings("unused")
	private static final String PAGO_POR_TARJETA_DEBITO = "1";
	@SuppressWarnings("unused")
	private static final String PAGO_POR_TARJETA_CREDITO = "2";
	
	private static final int TOTAL_ATRIBUTOS_INSTPAGO = 50;
	 
    /**
     * Servicio inyectado por spring para llamar a los servicios de valores por defecto del kernel
     */
    private KernelManager kernelManager;
    
	private CatalogService catalogosManager;
	private CotizacionService cotizacionManager;
	private ServiciosGeneralesSistema serviciosGeneralesSistema;
	private CatalogServiceJdbcTemplate catalogServiceJdbcTemplate;
	private InstrumentoPagoManager instrumentoPagoManagerJdbcTemplate;
	
	private boolean success;
	
	
	//parametros para los campos de instrumento de pago generado dinamicamente
	
	Map<String, String> paramsInstPag;
	
	//parametros de la pantalla
	private List<CoberturaCotizacionVO> listaCoberturaCompra;
	private List<MPoliObjVO> listaMPoliObj;
	
	private List<BaseObjectVO> listaDomicilio;
	private String codigoDomicilio;
	private String descripcionDomicilio;
	
	private List<BaseObjectVO> listaIntrumentoPago;
	private List <InstrumentoPagoAtributosVO> instrumentosClienteList;
	//private List <AtributosVariablesInstPagoVO> atributosInstrumentoPago;
	private String codigoInstrumentoPago;
	private String descripcionIntrumentoPago;
	
	private List<BaseObjectVO> listaBancos;
	private String codigoBanco;
	private String descripcionBanco;
	
	private List<BaseObjectVO> listaSucursal;
	private String codigoSucursal;
	private String descripcionSucursal;
	
	private List<BaseObjectVO> listaPersonasUsuario;
	private String codigoTipoTarjeta;
	private List<WrapperResultados> listaTipoTarjetaYCredito;
	private String descripcionTipoTarjeta;
	private String swCredito;
	
	private String codigoPersonaUsuario;
	private String descripcionPersonaUsuario;
	private String digitoVerificador;
	
	private List<BaseObjectVO> listaPersonasUsuarioMultiSelect;
	private List<String> funcionesPersona;
	private List<String> funcionesPersonaDescripciones;
	private List<RolVO> funcionesPoliza;
	
	private String descripcionAseguradora;
	private String descripcionPlan;
	private String descripcionFormaDePago;
	private String placa;
	private String noSerieMotor;
	private String vigenciaInicio;
	private String vigenciaFin;
	private String tipoAccesorio;
	private String montoAccesorio;
	private String totalPagar;
	private String reciboUnico;
	private String numRecibos;
	private String recibosSub;
	private String numeroTarjeta;
	private String codigoAseguradora;
	private String codigoPlan;
	private String codigoFormaDePago;
	private String vencimiento;
	
	//parametros de la patanlla anterios
	private String cdIdentifica;
	private String cdUnieco;
	private String cdCiaaseg;
	private String dsUnieco;
	private String cdPerpag;
	private String cdRamo;
	private String dsPerpag;
	private String numeroSituacion;
	private String codigoTipoSituacion;
	private String cdInsCte;
	
	private String limitadoasegurado;
	private String cdlimitadoasegurado;
	private String dslimitadoasegurado;
	private String ampliaplataplus;
	private String cdampliaplataplus;
	private String dsampliaplataplus;
	
	private String dsclave1;
	private String dsclave2;
	private String otclave1;
	private String otclave2;
	private String otclave3;
	private String row;
	private String ciaAseg;
	
	private String cdPlan;
	private String dsPlan;
	private String feEmisio;	
	private String feVensim;
	//private String cdRamo;	
	private String descripcion;
	
	private String mensaje;
	private String storesCombos;
	
	private String validacion;
	private String namePdf;
	private String namePdfRecibo;
	
	//funciones de la poliza
	private String rowFuncionPoliza;
	private String rolFuncionPoliza;
	private String descripcionRolFuncionPoliza;
	
	private List<BaseObjectVO> listaSexo;
	private List<BaseObjectVO> listaFuncionRol;
	private List<BaseObjectVO> listaFuncionRolReq;
	private Map<String, String> parametros;
	private String ordenTrabajo;
	
	//Clave de la nueva persona que fue insertada
	private String claveNuevaPersona;
	private String recibo;
	
	private String cdSisrol;
	
	private String errorMessage;
	
	private String mensajeValidacion;
	
	private List<FourValueObjectVO>listaIntrumentoPagos;
	
	/**
	 * @return the recibo
	 */
	public String getRecibo() {
		return recibo;
	}

	/**
	 * @param recibo the recibo to set
	 */
	public void setRecibo(String recibo) {
		this.recibo = recibo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	

	public String execute() throws Exception {
		return INPUT;
	}
	
	/**
	 * @author augusto.perez
	 * @param pantallaComprar
	 * @return void
	 * 
	 * Método que sube a sesión los elementos de la pantalla de comprar. Estos elementos aparecerán en la sección
	 * 'Datos Complementarios'. Siempre se subirá a sesión la variable ITEMS_PANTALLA_COMPRAR. En el caso que sean
	 * combos, se subirá a sesión la variable STORES_ITEMS_PANTALLA_COMPRAR que serán los stores para los combos. 
	 */
	@SuppressWarnings("unchecked")
	private void storesPantallaComprar( String pantallaComprar ) {
	  try {
		boolean bandera = true;
		String pc = pantallaComprar;

		session.put( "STORES_ITEMS_PANTALLA_COMPRAR", "" );
		
		while ( bandera ) {
			int i = StringUtils.indexOf( pc, "var store" );
			if ( i != -1 ) {
				int j = StringUtils.indexOf( pc, "}) ;" ) + "}) ;".length() + 1;
				String storesCombos = StringUtils.substring( pc, i, j ) + " ";
				session.put( "STORES_ITEMS_PANTALLA_COMPRAR", (String)session.get("STORES_ITEMS_PANTALLA_COMPRAR") + storesCombos );
				pc = StringUtils.substring( pc, j );
			} else {
				bandera = false;
			}
		}
		
		int i = StringUtils.indexOf( pc, "items:{" ) + "items:".length();
		int j = StringUtils.indexOf( pc, "\"items\":[{") + "\"items\":".length();
		StringBuilder sb = new StringBuilder( pc );
		if ( i < j )
			sb = sb.replace( i, j, "" );
		
		i = StringUtils.indexOf( sb.toString(), "\"renderTo\"" );
		j = StringUtils.indexOf( sb.toString(), "\"listeners\":{\"beforerender\"" );
		if ( i < j ) {
			sb = sb.replace(i, j, "");
			pc = StringUtils.substring( sb.toString(), 0, -1 );
		} else {
			// PRIMERA PARTE: Se obtienen nombre de los stores.
			Map<Integer,String> listaStores = new HashMap<Integer,String>();
			sb = new StringBuilder( pc );
			bandera = true;
			int x = 1;
			while ( bandera ) {
				i = StringUtils.indexOf( sb.toString(), "\"xtype\":\"combo\"" );
				if ( i != -1 ) {
					j = StringUtils.lastIndexOf( sb.toString(), "\"id\":\"" ) + "\"id\":\"".length() + 1;
					String id = StringUtils.substring( sb.toString(), j, StringUtils.indexOf( sb.toString(), "\"", j+1 ) );
					id = StringUtils.substring( id, StringUtils.indexOf( id , "|" ) + 1 );
					listaStores.put( new Integer( x ), id );
					sb = new StringBuilder( StringUtils.substring( sb.toString(), i + "\"xtype\":\"combo\"".length() ) );
					x++;
				} else {
					bandera = false;
				}
			}

			// SEGUNDA PARTE: Se inserta el nombre del store en cada definición de los combos.
			sb = new StringBuilder( pc );
			int t = 0;
			for (x = 1; x <= listaStores.size(); x++) {
				String id = listaStores.get( new Integer(x) );
				int u = sb.indexOf("\"xtype\":\"combo\"", t);
				sb = sb.insert( u + "\"xtype\":\"combo\"".length(), ",\"store\":store" + id);
				t = u + "\"xtype\":\"combo\"".length();
			}

			// TERCERA PARTE: Se inserta el evento beforerender para cargar los stores inicialmente.
			sb = sb.insert( sb.length()-1, ",\"listeners\":{\"beforerender\":function(){ " );
			for (x = 1; x <= listaStores.size(); x++) {
				String id = listaStores.get( new Integer(x) );
				sb = sb.insert( sb.length()-1 , 
						"store"+id+".baseParams['ottabval']='"+id+"'; " +
						"store"+id+".baseParams['valAnterior']='0'; " +
						"store"+id+".baseParams['valAntAnt']='0'; " +
						"store"+id+".load();  "
						); 
			}
			sb = sb.insert( sb.length()-1 , "} } ");
			pc = sb.toString();
		}

		session.put( "ITEMS_PANTALLA_COMPRAR" , pc );

		return;
	  } catch (Exception e) {
		  //log.error("Exception: " + e,e);
		  session.put( "ITEMS_PANTALLA_COMPRAR", pantallaComprar );
	  }
	}
	
	
	@SuppressWarnings("unchecked")
	public String comprarCotizaciones() throws Exception {
		if(session.containsKey("ACCESORIOS_COMPRA"))
			session.remove("ACCESORIOS_COMPRA");
		if(session.containsKey("LISTA_COMBOS_FUNCIONES_POLIZA"))
			session.remove("LISTA_COMBOS_FUNCIONES_POLIZA");
		if(session.containsKey("LISTA_FUNCIONES_PERSONA"))
			session.remove("LISTA_FUNCIONES_PERSONA");
		if(session.containsKey("GRID_LISTA_FUNCIONES_POLIZA"))
			session.remove("GRID_LISTA_FUNCIONES_POLIZA");
		if(session.containsKey("LISTA_FUNCIONES_POLIZA_REQUERIDAS"))
			session.remove("LISTA_FUNCIONES_POLIZA_REQUERIDAS");
				
		UserVO usuario = (UserVO) session.get("USUARIO");
		GlobalVariableContainerVO globalVariable = (GlobalVariableContainerVO) session.get("GLOBAL_VARIABLE_CONTAINER");
		
		logger.debug("empresa elemento id" +usuario.getEmpresa().getElementoId());
		logger.debug("codigo persona" +usuario.getCodigoPersona());
		logger.debug("numeroSituacion" +numeroSituacion);
		logger.debug("cdCiaaseg" +cdCiaaseg);
		logger.debug("cdPlan" +cdPlan);
		logger.debug("cdPlan" +cdPerpag);
		
		kernelManager.procIncisoDef(globalVariable.getValueVariableGlobal("vg.CdUnieco"), 
				globalVariable.getValueVariableGlobal("vg.CdRamo"),  
				globalVariable.getValueVariableGlobal("vg.Estado"), 
				globalVariable.getValueVariableGlobal("vg.NmPoliza"), numeroSituacion,
				usuario.getEmpresa().getElementoId(), usuario.getCodigoPersona(),cdCiaaseg,cdPlan,cdPerpag);
		if(log.isDebugEnabled()){
		log.debug("ComprarCotizacionAction ==> execute");
		
		log.debug("cdIdentifica = " + cdIdentifica);
		log.debug("cdUnieco = " + cdUnieco);
		log.debug("cdCiaaseg = " + cdCiaaseg);
		log.debug("dsUnieco = " + dsUnieco);
		log.debug("cdPerpag = " + cdPerpag);
		log.debug("cdRamo = " + cdRamo);				
		log.debug("dsPerpag = " + dsPerpag);	
		log.debug("numeroSituacion = " + numeroSituacion);	
		
		log.debug("limitadoasegurado = " + limitadoasegurado);		
		log.debug("cdlimitadoasegurado = " + cdlimitadoasegurado);		
		log.debug("dslimitadoasegurado = " + dslimitadoasegurado);
		log.debug("ampliaplataplus = " + ampliaplataplus);
		log.debug("cdampliaplataplus = " + cdampliaplataplus);
		log.debug("dsampliaplataplus = " + dsampliaplataplus);
		
		log.debug("dsclave1 = " + dsclave1);
		log.debug("dsclave2 = " + dsclave2);
		log.debug("otclave1 = " + otclave1);
		log.debug("otclave2 = " + otclave2);
		log.debug("otclave3 = " + otclave3);
		log.debug("row = " + row);
		log.debug("ciaAseg = " + ciaAseg);
		
		log.debug("cdPlan = " + cdPlan);		
		log.debug("dsPlan = " + dsPlan);
		log.debug("feEmisio = " + feEmisio);
		log.debug("feVensim = " + feVensim);
		
		log.debug("descripcion = " + descripcion);
		//log.debug("cotizacionManager.getRecibos()"+cotizacionManager.getRecibos());
		//log.debug("cotizacionManager.gerMPoliObj()"+cotizacionManager.gerMPoliObj());
		}
		globalVariable.addVariableGlobal("vg.cdCia", cdCiaaseg);
		globalVariable.addVariableGlobal("vg.cdPlan", cdPlan);
		globalVariable.addVariableGlobal("vg.cdPerpag", cdPerpag);
		globalVariable.addVariableGlobal("vg.NmSituac", numeroSituacion);
		
		session.put("GLOBAL_VARIABLE_CONTAINER", globalVariable);
		/*
		if(session.containsKey("GLOBAL_VARIABLE_CONTAINER")){
			GlobalVariableContainerVO globalVarVo = (GlobalVariableContainerVO) session.get("GLOBAL_VARIABLE_CONTAINER");
			globalVarVo = kernelManager.cargaValoresPorDefecto(ServletActionContext.getRequest().getSession().getId(), ((UserVO)session.get("USUARIO")).getName(), globalVarVo);    	
			session.put("GLOBAL_VARIABLE_CONTAINER", globalVarVo);
		}*/
		
		
		Map<String, String> parametersRecibos = new HashMap<String, String>();
	    parametersRecibos.put("CDUNIECO", 	globalVariable.getValueVariableGlobal("vg.CdUnieco"));
        parametersRecibos.put("ESTADO", 	globalVariable.getValueVariableGlobal("vg.Estado"));
        parametersRecibos.put("NMPOLIZA",	globalVariable.getValueVariableGlobal("vg.NmPoliza"));
        parametersRecibos.put("NMSITUAC",	numeroSituacion);
        parametersRecibos.put("NMSUPLEM",	globalVariable.getValueVariableGlobal("vg.NmSuplem"));
        parametersRecibos.put("CDRAMO", 	globalVariable.getValueVariableGlobal("vg.CdRamo"));    
        
        
        log.debug("CdUnieco= " + globalVariable.getValueVariableGlobal("vg.CdUnieco"));		
		log.debug("Estado = " + globalVariable.getValueVariableGlobal("vg.Estado"));
		log.debug("NMPOLIZA = " + globalVariable.getValueVariableGlobal("vg.NmPoliza"));
		log.debug("NMSITUAC = " + numeroSituacion);
		log.debug("NMSUPLEM = " + globalVariable.getValueVariableGlobal("vg.NmSuplem"));
		log.debug("CDRAMO = " + globalVariable.getValueVariableGlobal("vg.CdRamo"));		
		
        
        
		List<ReciboVO> recibos = cotizacionManager.getRecibos(parametersRecibos);
		log.debug("recibos debug = " + recibos);
		if(recibos!=null && !recibos.isEmpty()){			
			totalPagar = recibos.get(0).getImporteTotal();
			reciboUnico = recibos.get(1).getImporteTotal();
			log.debug("totalPagar = " + totalPagar);
			log.debug("reciboUnico = " + reciboUnico);
			if(recibos.size()>=3){
				//numRecibos = recibos.get(2).getImporteTotal();
				Integer numeroRecibosSubsecuentes = new Integer( NumberUtils.createInteger( recibos.get(2).getImporteTotal()) - 1 );
				numRecibos = numeroRecibosSubsecuentes.toString();
				log.debug("numRecibos = " + numRecibos);
			}
			if(recibos.size()==4){
				recibosSub = recibos.get(3).getImporteTotal();
				log.debug("recibosSub = " + recibosSub);
			}
		}
		
		Map<String, String> parametersMPoliObjVO = new HashMap<String, String>();
	    parametersMPoliObjVO.put("CDUNIECO", globalVariable.getValueVariableGlobal("vg.CdUnieco"));
	    parametersMPoliObjVO.put("CDRAMO", 	 globalVariable.getValueVariableGlobal("vg.CdRamo"));
        parametersMPoliObjVO.put("ESTADO", 	 globalVariable.getValueVariableGlobal("vg.Estado"));
        parametersMPoliObjVO.put("NMPOLIZA", globalVariable.getValueVariableGlobal("vg.NmPoliza"));
        parametersMPoliObjVO.put("NMSITUAC", numeroSituacion);
        parametersMPoliObjVO.put("NMOBJETO", null);	   
		listaMPoliObj = cotizacionManager.gerMPoliObj(parametersMPoliObjVO);
		
		if(listaMPoliObj!=null && !listaMPoliObj.isEmpty())	
			session.put("ACCESORIOS_COMPRA", "ok");
		
		Map<String, String> parametersRoles = new HashMap<String, String>();
		parametersRoles.put("CD_RAMO", 	globalVariable.getValueVariableGlobal("vg.CdRamo"));
		parametersRoles.put("CD_TIPSIT", 	globalVariable.getValueVariableGlobal("vg.CdTipSit"));
		parametersRoles.put("CD_PERSON", 	usuario.getCodigoPersona());
		parametersRoles.put("CD_ROLSIS", 	usuario.getRolActivo().getObjeto().getValue());
//		parametersRoles.put("CD_RAMO", 		"2");
//		parametersRoles.put("CD_TIPSIT", 	"A+");
//		parametersRoles.put("CD_PERSON", 	"1043");
//		parametersRoles.put("CD_ROLSIS", 	"ASEGURADO");
		List<RolVO> roles =cotizacionManager.gerRolesDC(parametersRoles);
		List<AbstractMasterModelControl> funcionesPoliza = new ArrayList<AbstractMasterModelControl>();
		if(roles!=null && !roles.isEmpty()){
			TextFieldControl hidden = null;
			int contador = 0;
			listaFuncionRolReq= new ArrayList<BaseObjectVO>();
			for(RolVO rol : roles){
//				Se quito la validación para que siempre sea comboBox				
/*				if(rol.getNumeroMaximo().equals("2")){
				//if(contador == 0){
					AbstractCombo combo = null;
					combo = new MultiSelectControl();
					combo.setXtype("multiselect");
					combo.setHeight(200);
					combo.setStore("storeComboPersonasUsuarioMultiSelect");
					combo.setName("funcionesPersona");
					combo.setHiddenName("funcionesPersona");
					//combo.setId("Id" +Integer.toString(contador));
					
					combo.setDisplayField("label");
					combo.setFieldLabel(rol.getDescripcionRol());
					combo.setLabelSeparator("");
					combo.setMode("local");
					combo.setTriggerAction("all");
					combo.setTypeAhead(true);
					combo.setValueField("value");
					combo.setWidth(300);
					funcionesPoliza.add(combo);
					contador++;
				}else{*/
					hidden = new TextFieldControl();
					hidden.setXtype("hidden");
					hidden.setId("id-combo-code-buy"+contador);
					hidden.setName("funcionesPersona");
					funcionesPoliza.add(hidden);
					SimpleCombo combo = null;
					combo = new SimpleCombo();
					combo.setEmptyText("Seleccion una persona..");
					combo.setEditable(true);	
					combo.setForceSelection(true);
					combo.setXtype("combo");
					combo.setStore("storeComboPersonasUsuario");
					combo.setName("funcionesPersonaDescripciones");
					combo.setHiddenName("funcionesPersonaDescripciones");
					//combo.setId("Id" +Integer.toString(contador));
					combo.setSelectOnFocus(true);
					combo.setOnSelect(new JSONFunction(new String[]{"record", "index", "skipCollapse"},"if(this.fireEvent('beforeselect', this, record, index) !== false){" +
					           "this.setValue(record.data[this.valueField || this.displayField]);" +
					           "if( !skipCollapse ) {" +
					           " this.collapse();" +
					           "}" +
					           "this.lastSelectedIndex = index + 1;" +
					           "this.fireEvent('select', this, record, index);" +
					         "}" +  
					         "Ext.getCmp(\"id-combo-code-buy"+contador+"\").setValue(record.get(\"value\"));"));
					
					combo.setDisplayField("label");
					combo.setFieldLabel(rol.getDescripcionRol());
					combo.setLabelSeparator("");
					combo.setMode("local");
					combo.setTriggerAction("all");
					combo.setTypeAhead(true);
					combo.setValueField("value");
					combo.setWidth(300);
					combo.setListWidth(300);
					
					
					if(rol.getRequerido()!=null && Constantes.SI.equals(rol.getRequerido())){
						combo.setAllowBlank(false);
						JSONObject listeners=new JSONObject();
						listeners.accumulate("render", "function(){this.setValue('');}");
						combo.setListeners(listeners);
						
						if(rol.getCodigoRol()!=null){
						BaseObjectVO rolReq=new BaseObjectVO();
						rolReq.setValue(rol.getCodigoRol());
						listaFuncionRolReq.add(rolReq);
						}
						
					}else{
						combo.setAllowBlank(true);
					}
					
					
					funcionesPoliza.add(combo);
					contador++;
					
					
//				}
			}
			log.debug("*******funciones de la poliza = " +funcionesPoliza);
			session.put("LISTA_COMBOS_FUNCIONES_POLIZA", funcionesPoliza);
			session.put("LISTA_FUNCIONES_PERSONA", roles);
			
			log.debug("LISTA_FUNCIONES_PERSONA"+ roles);
			log.debug("*******funciones de la poliza en session = " + session.get("LISTA_COMBOS_FUNCIONES_POLIZA"));
			
			session.put("LISTA_FUNCIONES_POLIZA_REQUERIDAS", listaFuncionRolReq);
			
			
		}
		
		
		
		mx.com.aon.portal.model.RolVO rolActivo= usuario.getRolActivo();
		
		if(rolActivo.getObjeto().getValue()!=null && CDSISROL_ASEGURADO.equalsIgnoreCase(rolActivo.getObjeto().getValue())){
			cdSisrol=rolActivo.getObjeto().getValue();
			
			if(usuario.getCodigoPersona()!=null){
				
				funcionesPersona = new ArrayList<String>();
				funcionesPersona.add(usuario.getCodigoPersona());
				//funcionesPolizaCotizacionCompra();
				Map<String, String> parametersAsegudaro = new HashMap<String, String>();
				parametersAsegudaro.put("cdRamo",   globalVariable.getValueVariableGlobal("vg.CdRamo"));
				parametersAsegudaro.put("cdSisrol", rolActivo.getObjeto().getValue());
				parametersAsegudaro.put("cdPerson", usuario.getCodigoPersona());
				
				RolVO rolAsegurado =cotizacionManager.obtieneAsegurado(parametersAsegudaro);
				if(rolAsegurado!=null){
					logger.debug("ROL ASEGURADO VO: "+rolAsegurado);
					List<RolVO> funcionesPolizaAsegurado = new ArrayList<RolVO>();
					rolAsegurado.setValue(rolAsegurado.getDescripcionNombre());
					rolAsegurado.setIdentificador(cdSisrol);
					rolAsegurado.setCode(usuario.getCodigoPersona());
					
					funcionesPolizaAsegurado.add(rolAsegurado);
					
					logger.debug("Valor de desNombre: "+rolAsegurado.getDescripcionNombre());
					session.put("GRID_LISTA_FUNCIONES_POLIZA", funcionesPolizaAsegurado);
					
				}
				
			}
			
			
		}
		
		
		Map<String, String> parametersPantallaFinal = new HashMap<String, String>();
	    parametersPantallaFinal.put("CD_ELEMENT",	usuario.getEmpresa().getElementoId());
	    parametersPantallaFinal.put("CD_RAMO", 		globalVariable.getValueVariableGlobal("vg.CdRamo"));
	    parametersPantallaFinal.put("CD_TITULO", 	"37");
	    parametersPantallaFinal.put("CD_TIPSIT",   globalVariable.getValueVariableGlobal(VariableKernel.CodigoTipoSituacion()));
	    parametersPantallaFinal.put("CD_SISROL",   usuario.getRolActivo().getObjeto().getValue());
	    log.debug("parametersPantallaFinal = " + parametersPantallaFinal);    
	    PantallaVO pantalla = new PantallaVO ();
	        
	    pantalla = cotizacionManager.getPantallaFinal(parametersPantallaFinal);
	    log.debug("pantalla.getDsArchivoSec() = " + pantalla.getDsArchivoSec());
	    
	    storesPantallaComprar( pantalla.getDsArchivoSec() );
	    
	    //session.put("ITEMS_PANTALLA_COMPRAR", pantalla.getDsArchivoSec());

	    /// /// ///kernelManager.cargarBloques(ServletActionContext.getRequest().getSession().getId(),globalVariable,
	    /// /// ///usuario);

	    log.debug("session.get('STORES_ITEMS_PANTALLA_COMPRAR') = " + session.get("STORES_ITEMS_PANTALLA_COMPRAR"));
	    
	    log.debug("session.get('ITEMS_PANTALLA_COMPRAR') = " + session.get("ITEMS_PANTALLA_COMPRAR"));
	    
	    ///Se ejecuta el cargar de bloque para la situacion.
	    
	     cdRamo = globalVariable.getValueVariableGlobal("vg.CdRamo");
	     codigoTipoSituacion = globalVariable.getValueVariableGlobal(VariableKernel.CodigoTipoSituacion());
	     //Agregar a sesion para poder utilizarlas en los links de la pantalla.
	     //session.put("CCCDRAMO", globalVariable.getValueVariableGlobal("vg.CdRamo"));
	     //session.put("CCCDTIPSIT", globalVariable.getValueVariableGlobal("vg.CdTipSit"));
	    
		return SUCCESS;
	}
	
	
	@SuppressWarnings("unchecked")
	/**
	 * Se ejecuta cuando vamos a Comprar desde la pantalla de COnsulta de Cotizaciones
	 */
	public String consultaCotizacionesComprar() throws Exception {
		
		logger.debug("Entrando a consultaCotizacionesComprar()" );
		
		session.remove("TEXT_FIELDS");
		
		serviciosGeneralesSistema.crearSesion(ServletActionContext.getRequest().getSession().getId());
		serviciosGeneralesSistema.crearEntidad(ServletActionContext.getRequest().getSession().getId());

		//Tomamos los valores de los parametros GET, ya que desde la pantalla anterior no debe tener datos GLOBAL_VARIABLE_CONTAINER
		GlobalVariableContainerVO globalVarVo = new GlobalVariableContainerVO();
		if ( session.containsKey("GLOBAL_VARIABLE_CONTAINER") ) {
			globalVarVo = (GlobalVariableContainerVO) session.get("GLOBAL_VARIABLE_CONTAINER");
		} else {
			globalVarVo.addVariableGlobal(VariableKernel.UnidadEconomica(), ServletActionContext.getRequest().getParameter("cdUnieco"));
			globalVarVo.addVariableGlobal(VariableKernel.CodigoRamo(), ServletActionContext.getRequest().getParameter("cdRamo"));
			globalVarVo.addVariableGlobal(VariableKernel.Estado(), ServletActionContext.getRequest().getParameter("estado"));
			globalVarVo.addVariableGlobal(VariableKernel.NumeroSituacion(), ServletActionContext.getRequest().getParameter("numeroSituacion"));
			globalVarVo.addVariableGlobal(VariableKernel.NumeroPoliza(), ServletActionContext.getRequest().getParameter("nmPoliza"));
		}
		
		logger.debug("Unidad Economica: " + globalVarVo.getValueVariableGlobal(VariableKernel.UnidadEconomica()));
		logger.debug("Codigo Ramo: " + globalVarVo.getValueVariableGlobal(VariableKernel.CodigoRamo()));
		logger.debug("Estado: " + globalVarVo.getValueVariableGlobal(VariableKernel.Estado()));
		logger.debug("Numero de Situacion: " + globalVarVo.getValueVariableGlobal(VariableKernel.NumeroSituacion()));
		logger.debug("Numero de la poliza: " + globalVarVo.getValueVariableGlobal(VariableKernel.NumeroPoliza()));
		//Agregar cdtipsit
		globalVarVo.addVariableGlobal(VariableKernel.CodigoTipoSituacion(), ServletActionContext.getRequest().getParameter("cdtipsit"));
		//Agregar UserBD
		UserVO usuario = (UserVO)session.get("USUARIO");
		globalVarVo.addVariableGlobal(VariableKernel.UsuarioBD(), usuario.getUser());
		//Agregar Suplemento =0
		globalVarVo.addVariableGlobal(VariableKernel.NumeroSuplemento(), "0");
		logger.debug("vg.UserBD=" + globalVarVo.getValueVariableGlobal(VariableKernel.UsuarioBD()));
		
		session.put("GLOBAL_VARIABLE_CONTAINER", globalVarVo);
		
		// Datos de entrada elegidos por el usuario:
        List<TextFieldControl> textFields = new ArrayList<TextFieldControl>();
        Map<String, String> paramsCotiza = new HashMap<String, String>();
        paramsCotiza.put("cdUnieco", cdUnieco);
        paramsCotiza.put("cdRamo", cdRamo);
        paramsCotiza.put("estado", ServletActionContext.getRequest().getParameter("estado"));
        paramsCotiza.put("nmPoliza", ServletActionContext.getRequest().getParameter("nmPoliza"));
        List<DatosEntradaCotizaVO> listaDatosEntradaCotiza = cotizacionManager.getDatosEntradaCotiza(paramsCotiza);
        for (DatosEntradaCotizaVO entrada : listaDatosEntradaCotiza) {
        	TextFieldControl textField = new TextFieldControl();
        	textField.setDisabled(true);
        	textField.setFieldLabel(entrada.getDsAtribu());
        	textField.setLabelSeparator(":");
        	textField.setName(entrada.getDsNombre());
        	textField.setXtype("textfield");
        	textField.setWidth(200);
        	textField.setValue(entrada.getDsValor());
        	textFields.add(textField);
        }
        if(!textFields.isEmpty()){
        	session.put("TEXT_FIELDS", textFields);
        }
        logger.debug("consultaCotizacionesComprar() TEXT_FIELDS=" + textFields);
        
        
		
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String obtieneCoberturasList() throws Exception{
		UserVO usuario = (UserVO) session.get("USUARIO");
		GlobalVariableContainerVO globalVariable = (GlobalVariableContainerVO) session.get("GLOBAL_VARIABLE_CONTAINER");
		
		
		HttpServletRequest servletReq = ServletActionContext.getRequest();

        Map params = servletReq.getParameterMap();

        Object ob = null;

        for (Object key : params.keySet()) {
            logger.debug(" key is " + key + " -- value is " + params.get(key.toString()).getClass());
            ob = params.get(key);

            if (ob instanceof String[]) {
                logger.debug("Array de Strings");

                for (String s : (String[]) ob) {
                    logger.debug("@@@@ s is ---" + s);
                }

            } else if (ob instanceof String) {
                logger.debug("Simple String");
            } else {
                logger.debug("class is " + ob.getClass());
            }

        }

        Map<String, String> parametersCoberturas = new HashMap<String, String>();

        String key = null;
        Enumeration<?> enumeration = servletReq.getParameterNames();

        while (enumeration.hasMoreElements()) {
            key = enumeration.nextElement().toString();
            logger.debug("### entering key is--- " + key);
            logger.debug("### value is----" + servletReq.getParameter(key));
            parametersCoberturas.put(key, servletReq.getParameter(key));
        }
        logger.debug("parametersCoberturas = " + parametersCoberturas);
        logger.debug("parametersCoberturas size is-- " + parametersCoberturas.size());
        parametersCoberturas.put("USUARIO",  usuario.getUser());
        parametersCoberturas.put("CDUNIECO", globalVariable.getValueVariableGlobal("vg.CdUnieco"));
        parametersCoberturas.put("ESTADO",   globalVariable.getValueVariableGlobal("vg.Estado"));
        parametersCoberturas.put("NMPOLIZA", globalVariable.getValueVariableGlobal("vg.NmPoliza"));
        parametersCoberturas.put("NMSITUAC", numeroSituacion);
        parametersCoberturas.put("NMSUPLEM", globalVariable.getValueVariableGlobal("vg.NmSuplem"));        
        parametersCoberturas.put("CDPLAN",   cdPlan);
        parametersCoberturas.put("CDRAMO",   globalVariable.getValueVariableGlobal("vg.CdRamo"));
        parametersCoberturas.put("CDCIA",    cdCiaaseg);
        parametersCoberturas.put("REGION",   "ME");
        parametersCoberturas.put("PAIS",     usuario.getPais().getValue());
        parametersCoberturas.put("IDIOMA",   usuario.getIdioma().getValue());
        
		listaCoberturaCompra =  (List<CoberturaCotizacionVO>) cotizacionManager.getCoberturas(parametersCoberturas);
		if(listaCoberturaCompra==null)
			listaCoberturaCompra = new ArrayList<CoberturaCotizacionVO>();
		success = true;
		return SUCCESS;
	}
	
	public String accesoriosList() throws Exception{
		GlobalVariableContainerVO globalVariable = (GlobalVariableContainerVO) session.get("GLOBAL_VARIABLE_CONTAINER");
		Map<String, String> parametersMPoliObjVO = new HashMap<String, String>();
	    parametersMPoliObjVO.put("CDUNIECO", globalVariable.getValueVariableGlobal("vg.CdUnieco"));
	    parametersMPoliObjVO.put("CDRAMO", 	 globalVariable.getValueVariableGlobal("vg.CdRamo"));
        parametersMPoliObjVO.put("ESTADO", 	 globalVariable.getValueVariableGlobal("vg.Estado"));
        parametersMPoliObjVO.put("NMPOLIZA", globalVariable.getValueVariableGlobal("vg.NmPoliza"));
        parametersMPoliObjVO.put("NMSITUAC", numeroSituacion);
        parametersMPoliObjVO.put("NMOBJETO", null);
//	    parametersMPoliObjVO.put("CDUNIECO", "1");
//	    parametersMPoliObjVO.put("CDRAMO", 	 "120");
//        parametersMPoliObjVO.put("ESTADO", 	 "M");
//        parametersMPoliObjVO.put("NMPOLIZA", "1");
//        parametersMPoliObjVO.put("NMSITUAC", "1");
//        parametersMPoliObjVO.put("NMOBJETO", null);
		listaMPoliObj = cotizacionManager.gerMPoliObj(parametersMPoliObjVO);
		log.debug("listaMPoliObj debug = " + listaMPoliObj);
		
		if(listaMPoliObj==null)			
			listaMPoliObj = new ArrayList<MPoliObjVO>();		
		
		success = true;
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String domicilioList() throws Exception{
		//UserVO usuario = (UserVO) session.get("USUARIO");
		log.debug("codigoPersonaUsuario :"+codigoPersonaUsuario);
		listaDomicilio = catalogosManager.getItemList("OBTIENE_DOMICILIO_PERSONA", codigoPersonaUsuario);
		if(listaDomicilio==null)
			listaDomicilio = new ArrayList<BaseObjectVO>();
		success = true;
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String intrumentoPagoList() throws Exception{

		listaIntrumentoPago = catalogosManager.getItemList("OBTIENE_INTRUMETOS_PAGO");
		if(listaIntrumentoPago==null)
			listaIntrumentoPago = new ArrayList<BaseObjectVO>();
		success = true;
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String intrumentoPagoLista() throws Exception{
        
        UserVO usuario = (UserVO) session.get("USUARIO");
        GlobalVariableContainerVO globalVariable = (GlobalVariableContainerVO) session.get("GLOBAL_VARIABLE_CONTAINER");
        
        if(logger.isDebugEnabled()){
	        logger.debug("into  intrumentoPagoLista");
	        logger.debug("CDELEMENTO "+ usuario.getEmpresa().getElementoId());
	        logger.debug("CDUNIECO "+ globalVariable.getValueVariableGlobal(VariableKernel.CodigoCompania()));
	        logger.debug("RAMO "+ globalVariable.getValueVariableGlobal(VariableKernel.CodigoRamo()));
        }
        try{
        	PagedList pagedList = instrumentoPagoManagerJdbcTemplate.getInstrumetosPagoCliente(usuario.getEmpresa().getElementoId(), null, globalVariable.getValueVariableGlobal(VariableKernel.CodigoCompania()), globalVariable.getValueVariableGlobal(VariableKernel.CodigoRamo()), 0 , -1);
        	instrumentosClienteList = pagedList.getItemsRangeList();
        	
        	if(logger.isDebugEnabled())logger.debug("COMBO FORMAS DE PAGO: " +instrumentosClienteList);
        }catch(Exception e){
        	logger.error("Error al obtener los instrumentos de pago"+ e.getMessage(),e);
        	success = false;
    		return SUCCESS;
        }
        
        
		/*listaIntrumentoPagos = catalogosManager.getItemList("OBTIENE_INTRUMETOS_DE_PAGO");
		if(listaIntrumentoPagos==null){
			listaIntrumentoPagos = new ArrayList<FourValueObjectVO>();
		   logger.debug( "listaIntrumentoPagos != null" );
		 
		}*/
			
		 success = true;
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String atributosVariablesInstrumetosPago()throws Exception{
    	
        if(logger.isDebugEnabled()){
        	logger.debug("******atributosVariablesInstrumetosPago******");
            logger.debug("cdInsCte: " + cdInsCte);
        }
        
    	ArrayList <AtributosVariablesInstPagoVO> atributosInstrumentoPago =  null;
        try{
        	PagedList pagedList = instrumentoPagoManagerJdbcTemplate.getAtributosInstrumetoPago(cdInsCte, null, 0 , -1);
        	atributosInstrumentoPago = (ArrayList<AtributosVariablesInstPagoVO>) pagedList.getItemsRangeList();
        	if(logger.isDebugEnabled()) logger.debug("ATRIBUTOS DINAMICOS PARA LA FORMA DE PAGO : "+cdInsCte+" SON:"+ atributosInstrumentoPago);
        }catch(Exception e){
        	//atributosInstrumentoPago = new ArrayList<AtributosVariablesInstPagoVO>();
        	logger.error("Exception obtenAtributosGridInstrumetosPago: " + e.getMessage(),e);
        	success = false;
        	return SUCCESS;	
        }
        
        mensaje = obtieneFieldsetElementosExt(atributosInstrumentoPago);
        	
        logger.debug("componentes para Instrumentos de Pago creados: " + mensaje);
        
        success = true;
		return SUCCESS;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public String obtieneFieldsetElementosExt(ArrayList <AtributosVariablesInstPagoVO> atributosInstrumentoPago){
		
		StringBuilder fieldset = new StringBuilder();
		try{
		fieldset.append("{layout: 'form', border: false , labelAlign:'right");
		//fieldset.append("', title: '");"); fieldset.append(descripcionFormaDePago);
		fieldset.append("' , id: 'fieldsetId', autoHeight: true, items: [");
		
		if(atributosInstrumentoPago !=null){
			Map<String, List<String>> listasValor =  new HashMap<String, List<String>>(); //Ottabval, cdAtribu
			Map<String, AtributosVariablesInstPagoVO> combos =  new HashMap<String, AtributosVariablesInstPagoVO>(); //cdAtribu, AtributosVariablesInstPagoVO
			
			for(AtributosVariablesInstPagoVO elemento : atributosInstrumentoPago){
				if(StringUtils.isNotBlank(elemento.getCdTabla())){
					ArrayList<String> tmp;
					/*para añadir todos los ids de los combos que usan una tabla de lista valor*/
					if(listasValor.containsKey(elemento.getCdTabla())){
						tmp = (ArrayList<String>) listasValor.get(elemento.getCdTabla());
						tmp.add(elemento.getCdAtribu());
						listasValor.put(elemento.getCdTabla(), tmp);
						
					}else{
						tmp = new ArrayList<String>();
						tmp.add(elemento.getCdAtribu());
						listasValor.put(elemento.getCdTabla(),tmp);
					}
					
					/*se añade a la lista que contiene todos los combos identificados por su numero de atributo*/
					combos.put(elemento.getCdAtribu(), elemento);
				}
			}
			
			if(!listasValor.isEmpty()){
				asignarPadres(listasValor, combos);
				if(logger.isDebugEnabled())logger.debug("LOS COMBOS LUEGO DE ASIGNAR PADRES SON: "+ combos);
				
			}
			
			int inc = 0;
			for(AtributosVariablesInstPagoVO elemento : atributosInstrumentoPago){
				String cdFormat = elemento.getSwformat();
				if(StringUtils.isNotBlank(cdFormat) && !StringUtils.isNotBlank(elemento.getCdTabla())){
					if(cdFormat.equals("A")){
						if(inc > 0)fieldset.append(",");
						fieldset.append(creaTextfield(elemento));
					}else if(cdFormat.equals("N") || cdFormat.equals("P")){
						if(inc > 0)fieldset.append(",");
						fieldset.append(creaNumberfield(elemento));
					}else if(cdFormat.equals("F")){
						if(inc > 0)fieldset.append(",");
						fieldset.append(creaDatefield(elemento));
					}
					inc++;
				}else if(StringUtils.isNotBlank(elemento.getCdTabla())){
					if(inc > 0)fieldset.append(",");
					fieldset.append(creaComboListaValor(elemento));
					inc++;
				}
			}
			
		}
		
		fieldset.append("]}");
		}catch(Exception e){
			logger.error("ERROR AL OBTENER LOS COMPONENTES EXT : "+e.getMessage(),e);
			return null;
		}
		return fieldset.toString();
	}
	
	public String creaTextfield(AtributosVariablesInstPagoVO atributo){
		StringBuilder text =  new StringBuilder();
		text.append("{xtype: 'textfield', fieldLabel: '"); text.append(atributo.getDsAtribu());
		text.append("', name: 'paramsInstPag.cdAtribu_"); text.append(atributo.getCdAtribu());
		text.append("', allowBlank: "); 
		
		if(StringUtils.isNotBlank(atributo.getSwemiobl()) && atributo.getSwemiobl().equals("S")){
			text.append("false");
		}else {
			 text.append("true");
		}
		
		text.append(", maxLength: "); text.append(atributo.getNmMax()); 
		text.append(", minLength: "); text.append(atributo.getNmMin()); 
		text.append(", maxLengthText: '"); text.append("M&aacute;ximo "); text.append(atributo.getNmMax()); text.append(" caracteres'");
		text.append(", minLengthText: '"); text.append("M&iacute;nimo "); text.append(atributo.getNmMin()); text.append(" caracteres'");
		text.append(", width: 250 }");
		
		return text.toString();
	}
	
	public String creaNumberfield(AtributosVariablesInstPagoVO atributo){
		StringBuilder text =  new StringBuilder();
		text.append("{xtype: 'numberfield', fieldLabel: '"); text.append(atributo.getDsAtribu());
		text.append("', name: 'paramsInstPag.cdAtribu_"); text.append(atributo.getCdAtribu());
		text.append("', allowBlank: "); 
		
		if(StringUtils.isNotBlank(atributo.getSwemiobl()) && atributo.getSwemiobl().equals("S")){
			text.append("false");
		}else {
			 text.append("true");
		}
		
		if(atributo.getSwformat().equals("N")){
			text.append(", maxLength: "); text.append(atributo.getNmMax()); 
			text.append(", minLength: "); text.append(atributo.getNmMin()); 
			text.append(", maxLengthText: '"); text.append("M&aacute;ximo "); text.append(atributo.getNmMax()); text.append(" d&iacute;gitos'");
			text.append(", minLengthText: '"); text.append("M&iacute;nimo "); text.append(atributo.getNmMin()); text.append(" d&iacute;gitos'");
		}
		text.append(", width: 250 }");
		
		return text.toString();
	}
	
	public String creaDatefield(AtributosVariablesInstPagoVO atributo){
		StringBuilder text =  new StringBuilder();
		text.append("{xtype: 'datefield', fieldLabel: '"); text.append(atributo.getDsAtribu());
		text.append("', name: 'paramsInstPag.cdAtribu_"); text.append(atributo.getCdAtribu());
		text.append("', allowBlank: "); 
		
		if(StringUtils.isNotBlank(atributo.getSwemiobl()) && atributo.getSwemiobl().equals("S")){
			text.append("false");
		}else {
			 text.append("true");
		}
		text.append(", width: 250 }");
		
		return text.toString();
	}

	public String creaComboListaValor(AtributosVariablesInstPagoVO atributo){
		
		StringBuilder text =  new StringBuilder();
		
		text.append("{xtype: 'combo', fieldLabel: '"); text.append(atributo.getDsAtribu());
		text.append("', hiddenName: 'paramsInstPag.cdAtribu_"); text.append(atributo.getCdAtribu());
		text.append("', id: 'combo"); text.append(atributo.getCdAtribu()); text.append("id");
		text.append("', allowBlank: "); 
		if(StringUtils.isNotBlank(atributo.getSwemiobl()) && atributo.getSwemiobl().equals("S")){
			text.append("false");
		}else {
			 text.append("true");
		}
		
		text.append(", store: ");
		text.append(" new Ext.data.Store({ proxy: new Ext.data.HttpProxy({ url: 'flujocotizacion/obtenerListaComboOttabval.action' })");
		text.append(", id: 'store"); text.append(atributo.getCdAtribu()); text.append("id', reader: new Ext.data.JsonReader({ root: 'itemList', id: 'value' }, [{ mapping: 'value', name: 'value', type: 'string'},{mapping: 'label', name: 'label', type: 'string'}] ), remoteSort: true , autoLoad: false , baseParams : {ottabval: '"); text.append(atributo.getCdTabla());
		text.append("', valAnterior : '0', valAntAnt : '0'}, sortInfo : [{field: 'value'},{ direction :'DESC'}] ");
		text.append(", listeners: { load: function(){");
		
		if(atributo.getChildren() != null && !atributo.getChildren().isEmpty()){
			for(int cont=0 ; cont < atributo.getChildren().size(); cont++){
				String cdTabHija = atributo.getChildren().get(cont);
				text.append(" Ext.getCmp('combo"); text.append(cdTabHija); text.append("id').setValue('');");
				text.append(" Ext.getCmp('combo"); text.append(cdTabHija); text.append("id').clearValue();");
				text.append(" Ext.getCmp('combo"); text.append(cdTabHija); text.append("id').store.load();");
				
			}
			
		}
		
		//FIN DEL LISTENER LOAD
		text.append("} ");
		//PARA EL LISTENER BEFORELOAD
		text.append(", beforeload: function(thisStore){");
		
			if(atributo.getParent() != null && !atributo.getParent().isEmpty()){
				for(int i=0 ; i<atributo.getParent().size() ; i++){
					String cdtablaPadre = atributo.getParent().get(i);
					if(i==0){
						text.append("var valAnt = Ext.getCmp('combo"); text.append(cdtablaPadre); text.append("id').getValue();");
						text.append("thisStore.baseParams['valAnterior'] = Ext.isEmpty(valAnt)? '0' : valAnt;");
					}
					else {
						text.append("thisStore.baseParams['"); text.append(cdtablaPadre); text.append("'] = Ext.getCmp('combo"); text.append(cdtablaPadre); text.append("id').getValue();");
					}
				}
				
			}
		
		//FIN DEL LISTENER BEFORELOAD
		text.append("} ");
		text.append("}");//cierre de los listeners del store del combo

		text.append("})"); //Cierre del store del combo
		
		text.append(", displayField: 'label'");
		text.append(", valueField: 'value'");  
		text.append(", editable: true");
		text.append(", emptyText: 'Seleccione...'");
		text.append(", forceSelection: true");
		text.append(", listWidth: 200");
		/*
		 *PARA EL LISTENER DE RENDER Y EL SELECT (render para que ya exista el componente) 
		 */
		text.append(", listeners: { render: function(){");
		
		if(atributo.getParent() == null || atributo.getParent().isEmpty()){
			text.append(" this.store.load(); ");
		}
			
		text.append("}, select: function(){  ");
		
		if(atributo.getChildren() != null && !atributo.getChildren().isEmpty()){
			for(int cont=0 ; cont < atributo.getChildren().size(); cont++){
				String cdTabHija = atributo.getChildren().get(cont);
				text.append(" Ext.getCmp('combo"); text.append(cdTabHija); text.append("id').setValue('');");
				text.append(" Ext.getCmp('combo"); text.append(cdTabHija); text.append("id').clearValue();");
				text.append(" Ext.getCmp('combo"); text.append(cdTabHija); text.append("id').store.load();");
				
			}
			
		}
		
		//FIN DEL LISTENER SELECT PARA COMBOS ANIDADOS
		text.append("}");
		text.append("}");//fin de los listeners del combo
		text.append(", mode: 'local'");
		text.append(", selectOnFocus: true");
		text.append(", triggerAction: 'all'");
		text.append(", typeAhead: true");
		text.append(", width: 250 }");
		
		return text.toString();
	}
	
	/**
	 * Metodo para obtener las tablas (combos de listas) padres 
	 * @param listasValor Hashmap con claves de las tablas y valores de cdatribu correspondiente a esas claves
	 * @param combos Hashmap con claves los cdAtribu y los valores de los VO correspondientes a esas claves
	 */
	@SuppressWarnings("unchecked")
	public void asignarPadres(Map<String, List<String>> listaTablas, Map<String, AtributosVariablesInstPagoVO> combos){
		Iterator It = listaTablas.entrySet().iterator();
        while (It.hasNext()) {
        	Map.Entry entry = (Map.Entry) It.next();
        	String cdTabla = (String)entry.getKey();  //clave de la tabla actual a verificar si tiene padre, este id representa en el hashmap las tablas hijas
        	//String cdAtr = (String)entry.getValue(); //clave del atributo actual 
        	
        	List<ComboClearOnSelectVO> tablaPadre = null;
        	Map<String,String> parameters = new HashMap<String,String>();
			parameters.put("cdunica", cdInsCte);
			parameters.put("ottabval", cdTabla);
			try{
				tablaPadre = (List<ComboClearOnSelectVO>)cotizacionManager.getComboPadreInstPago(parameters); 
			}catch(ApplicationException ae){
	    		logger.error("Error al invocar getComboPadre: " + ae);
	        }
        	
			if(tablaPadre != null && !tablaPadre.isEmpty()){
				String cdTablaPadre = tablaPadre.get(0).getOttabval();
				
				if(StringUtils.isNotBlank(cdTablaPadre)){
					
					if(listaTablas.containsKey(cdTablaPadre)){
						/*En caso de que si tenga padre y si se encuentra en la lista de tablas de los combos a agregar,
						 *se obtienen los combo padre para asignarle su combo hijo*/
						for(String cdAtribuPadre : listaTablas.get(cdTablaPadre)){
							if(combos.get(cdAtribuPadre).getChildren() == null)combos.get(cdAtribuPadre).setChildren(new ArrayList<String>());
							for(String cdAtribuHijo : listaTablas.get(cdTabla)){
								combos.get(cdAtribuPadre).getChildren().add(cdAtribuHijo);
							}
						}
						/*En caso de que si tenga padre y si se encuentra en la lista de tablas de los combos a agregar,
						 *se obtienen los combo hijos para asignarle su combo padre*/
//						if(combos.get(listaTablas.get(cdTabla)).getParent() == null)combos.get(listaTablas.get(cdTabla)).setParent(new ArrayList<String>());
//						combos.get(listaTablas.get(cdTabla)).getParent().add(cdTablaPadre);
						
						for(String cdAtribuHijo : listaTablas.get(cdTabla)){
							if(combos.get(cdAtribuHijo).getParent() == null)combos.get(cdAtribuHijo).setParent(new ArrayList<String>());
							for(String cdAtribuPadre : listaTablas.get(cdTablaPadre)){
								combos.get(cdAtribuHijo).getParent().add(cdAtribuPadre);
							}
						}
						
					}
					
				}
				
			}
        	
        }
		
	}
	
	
	@SuppressWarnings("unchecked")
	public String bancoList() throws Exception{

		listaBancos = catalogosManager.getItemList("CATALOGS_OBTIENE_BANCOS");		
		if(listaBancos==null)
			listaBancos = new ArrayList<BaseObjectVO>();
		success = true;
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String sucursalList() throws Exception{
		
		listaSucursal = catalogosManager.getItemList("OBTIENE_BANCOS_SUCURSAL", codigoBanco);
		if(listaSucursal==null)
			listaSucursal = new ArrayList<BaseObjectVO>();
		success = true;
		return SUCCESS;
	}
	
	/*@SuppressWarnings("unchecked")
	public String personasUsuarioList() throws Exception{
		UserVO usuario = (UserVO) session.get("USUARIO");
		if(logger.isDebugEnabled()){
			logger.debug("personasUsuarioList Usuario:" + usuario.getUser());
		}
		listaPersonasUsuario = catalogosManager.getItemList("OBTIENE_PERSONA_USUARIO", usuario.getUser());
		if(listaPersonasUsuario==null)
			listaPersonasUsuario = new ArrayList<BaseObjectVO>();
		success = true;
		return SUCCESS;
	}*/
	@SuppressWarnings("unchecked")
	public String personasUsuarioList() throws Exception{
		UserVO usuario = (UserVO) session.get("USUARIO");
		if(logger.isDebugEnabled()){
			logger.debug("personasUsuarioList Usuario:" + usuario.getUser());
		}
		listaPersonasUsuario = (List<BaseObjectVO>)catalogServiceJdbcTemplate.getItemList("COMBO_OBTIENE_PERSONA_USUARIO", usuario.getUser());
		if(listaPersonasUsuario==null)
			listaPersonasUsuario = new ArrayList<BaseObjectVO>();
		success = true;
		return SUCCESS;
	}
	@SuppressWarnings("unchecked")
	public String listaTipoTarjetaYCredito() throws Exception{
		UserVO usuario = (UserVO) session.get("USUARIO");
		listaTipoTarjetaYCredito = catalogosManager.getItemList("OBTIENE_TIPO_TARJETA_Y_SWCREDITO", usuario.getUser());
		if(listaTipoTarjetaYCredito==null)
			listaTipoTarjetaYCredito = new ArrayList<WrapperResultados>();
		success = true;
		return SUCCESS;
	}
	
	
	@SuppressWarnings("unchecked")
	public String personasUsuarioMultiSelectList() throws Exception{
		UserVO usuario = (UserVO) session.get("USUARIO");
		listaPersonasUsuarioMultiSelect = catalogosManager.getItemList("OBTIENE_PERSONA_USUARIO", usuario.getUser());
		if(listaPersonasUsuarioMultiSelect==null)
			listaPersonasUsuarioMultiSelect = new ArrayList<BaseObjectVO>();
		success = true;
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String funcionesPolizaList() throws Exception{
		log.debug("funcionesPolizaList()");
		//log.debug("funcionesPoliza = " + session.get("GRID_LISTA_FUNCIONES_POLIZA"));
		if(session.containsKey("GRID_LISTA_FUNCIONES_POLIZA"))
			funcionesPoliza = (List<RolVO>) session.get("GRID_LISTA_FUNCIONES_POLIZA");
		if(funcionesPoliza == null){
			funcionesPoliza = new ArrayList<RolVO>();			
		}/*
		if(funcionesPoliza!=null && !funcionesPoliza.isEmpty()){
			for(RolVO rol: funcionesPoliza){
				logger.debug("rol.getDescripcionRol() = " + rol.getDescripcionRol());
				logger.debug("rol.getValue() = " + rol.getValue());
				logger.debug("rol.getIdentificador() = " + rol.getIdentificador());
			}
		}*/
		success = true;
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String listaFuncionRol() throws Exception{
		GlobalVariableContainerVO globalVariable = (GlobalVariableContainerVO) session.get("GLOBAL_VARIABLE_CONTAINER");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("cdramo", globalVariable.getValueVariableGlobal("vg.CdRamo"));
		
		listaFuncionRol = catalogosManager.getItemList("OBTIENE_LISTA_FUNCION_ROL", param);
		success = true;
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String listaSexo() throws Exception{
		listaSexo = catalogosManager.getItemList("OBTIENE_LISTA_SEXO");
		success = true;
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String eliminarFuncionPoliza() throws Exception{
		if(session.containsKey("GRID_LISTA_FUNCIONES_POLIZA"))
			funcionesPoliza = (List<RolVO>) session.get("GRID_LISTA_FUNCIONES_POLIZA");
		if(row!=null && funcionesPoliza != null && !funcionesPoliza.isEmpty()){
			funcionesPoliza.remove(Integer.parseInt(row));
			session.put("GRID_LISTA_FUNCIONES_POLIZA", funcionesPoliza);
		}
		success = true;
		return SUCCESS;
	}
	
	
	
	/**
	 * Metodo que se ejecuta al dar click en el boton comprar, en la pagina de datos complementarios (ComprarCotizacion)
	 * Este valida si los roles asociados al grid, ejectuta lo requerido para hacer la compra, y crea las caratulas y los recibos. 
	 * @return String
	 * @throws Exception
	 */
	
	@SuppressWarnings("unchecked")
	public String comprarCotizacionCompra() throws Exception{
		logger.debug("Compra cotizacion compra");
		
		this.success=true;
		this.errorMessage="false";
		HttpServletRequest servletReq = ServletActionContext.getRequest();

        Map params = servletReq.getParameterMap();
        if(logger.isInfoEnabled()){
			logger.info("Entrando a Compra cotizacion compra()");
			if(params.keySet()!= null && !params.isEmpty()){
				for(Object key: params.keySet()){
					logger.debug("Key = " + key);
					logger.debug("Value = " + params.get(key));
				}
			}
		}
        Object ob = null;

        for (Object key : params.keySet()) {
            logger.debug(" key is " + key + " -- value is " + params.get(key.toString()).getClass());
            ob = params.get(key);

            if (ob instanceof String[]) {
                logger.debug("Array de Strings");

                for (String s : (String[]) ob) {
                    logger.debug("@@@@ s is ---" + s);
                }

            } else if (ob instanceof String) {
                logger.debug("Simple String");
            } else {
                logger.debug("class is " + ob.getClass());
            }

        }

        Map<String, String> parametersValidacion = new HashMap<String, String>();

        String key = null;
        Enumeration<?> enumeration = servletReq.getParameterNames();

        while (enumeration.hasMoreElements()) {
            key = enumeration.nextElement().toString();
            logger.debug("### entering key is--- " + key);
            logger.debug("### value is----" + servletReq.getParameter(key));
            parametersValidacion.put(key, servletReq.getParameter(key));
        }
        
        UserVO usuario = (UserVO) session.get("USUARIO");
        GlobalVariableContainerVO globalVariable = (GlobalVariableContainerVO) session.get("GLOBAL_VARIABLE_CONTAINER");
        
        
        
//***************************Para validar si se agregaron valores al grid de "Roles asociados a la poliza"*******************************//
        
    	if(session.containsKey("GRID_LISTA_FUNCIONES_POLIZA") && !((List)session.get("GRID_LISTA_FUNCIONES_POLIZA")).isEmpty()){
    		funcionesPoliza = (ArrayList<RolVO>) session.get("GRID_LISTA_FUNCIONES_POLIZA");
    		
    	//Para ver si se agregaron las funciones de poliza requeridas.
    		if(session.containsKey("LISTA_FUNCIONES_POLIZA_REQUERIDAS") && !((List)session.get("LISTA_FUNCIONES_POLIZA_REQUERIDAS")).isEmpty()){
    			
    			listaFuncionRolReq=(ArrayList<BaseObjectVO>)session.get("LISTA_FUNCIONES_POLIZA_REQUERIDAS");
    			
    			int totalRolesGrid;
    			
    			for(BaseObjectVO rolReq:listaFuncionRolReq){
    				if(this.success){
    				totalRolesGrid=funcionesPoliza.size();
    				
	    				for(RolVO rol: funcionesPoliza){
	    					totalRolesGrid--;
	    					
	    					//Para validar si el codigo del rol requerido no viene vacio 
		    				if(StringUtils.isNotBlank(rol.getCodigoRol())){
		    					//Para validar si es un rol requerido y ademas para validar si la descripcion del rol requerido en el grid no viene vacia
		    					if(rol.getCodigoRol().equals(rolReq.getValue()) && StringUtils.isNotBlank(rol.getValue())){
		    						this.errorMessage="false";
		    						break;
		    					}else {
		    						if((totalRolesGrid)==0) {
		    								this.success=false;
		    						}
		    					}
		    				}else{
		    					this.success=false;
		    				}
	    				}
    				}else{
    					break;
    				}
    			}
    			
    			
    			if(!this.success){
    				if(logger.isDebugEnabled()){
    					logger.debug("ComprarCotizacionCompra - No estan asociados todos los roles requeridos a la poliza");
    				}
    				errorMessage="No estan asociados todos los roles requeridos a la p&oacute;liza";
    				return SUCCESS;
    				
    			}
    			
    			session.remove("LISTA_FUNCIONES_POLIZA_REQUERIDAS");
    		}//No Existe un else para el cierre de este 'if' ya que si no hay funciones de poliza requeridas no habrá problema.
    			
    	
    	}else {
    		if(logger.isDebugEnabled()){
    			logger.debug("ComprarCotizacionCompra - No existen datos en el grid Roles de la Poliza (grid vacio)");
    		}
    		this.errorMessage="No estan asociados todos los roles requeridos a la p&oacute;liza";
    		this.success=false;
			return SUCCESS;
    		
    	} 
    	
    	
    	Map paramsFuncionesPoliza = new HashMap();
		paramsFuncionesPoliza.put("CDUNIECO", 	globalVariable.getValueVariableGlobal("vg.CdUnieco"));
		paramsFuncionesPoliza.put("CDRAMO", 	globalVariable.getValueVariableGlobal("vg.CdRamo"));
		paramsFuncionesPoliza.put("ESTADO", 	globalVariable.getValueVariableGlobal("vg.Estado"));
		paramsFuncionesPoliza.put("NMPOLIZA", 	globalVariable.getValueVariableGlobal("vg.NmPoliza"));
		paramsFuncionesPoliza.put("NMSITUAC", 	numeroSituacion);
		
		
		
		paramsFuncionesPoliza.put("NMSUPLEM",  globalVariable.getValueVariableGlobal("vg.NmSuplem"));
		paramsFuncionesPoliza.put("STATUS", 	"V");
		paramsFuncionesPoliza.put("ACCION", 	"I");
		paramsFuncionesPoliza.put("FUNCIONES_POLIZA", funcionesPoliza);
		cotizacionManager.pMovMPoliPer(paramsFuncionesPoliza);
		
		
		/*
    	//Guardar movMTARJETA
    	
		if(StringUtils.isNotBlank(codigoInstrumentoPago)){
			if(PAGO_POR_TARJETA_DEBITO.equals(codigoInstrumentoPago.trim()) || PAGO_POR_TARJETA_CREDITO.equals(codigoInstrumentoPago.trim())){
				Map paramsMTARJETA = new HashMap();
		    	paramsMTARJETA.put("NMTARJ", numeroTarjeta);
		    	paramsMTARJETA.put("CDTITARJ", codigoTipoTarjeta);
		    	paramsMTARJETA.put("CDPERSON", codigoPersonaUsuario);
		    	paramsMTARJETA.put("FEVENCE", vencimiento);
		    	paramsMTARJETA.put("CDBANCO", codigoBanco);
		    	//TODO:cambiar valor verdadero credito
		    	paramsMTARJETA.put("DEBCRED", swCredito);
		    	paramsMTARJETA.put("ACCION", "I");
		    	cotizacionManager.movMTARJETA(paramsMTARJETA);	
			}
		}
		*/
		
		
		/*
		 * PARA GUARDAR LOS ATRIBUTOS VARIABLES PARA EL INSTRUMENTO DE PAGO
		 */
		if(logger.isDebugEnabled())logger.debug("Parametros de los atributos para el intrumento de pago: " + paramsInstPag);
		
		if(paramsInstPag != null && !paramsInstPag.isEmpty()){
			try{
			Map<String, String> paramsInsPagoPoliza = new HashMap<String,String>();
			paramsInsPagoPoliza.put("pv_cdunieco", globalVariable.getValueVariableGlobal("vg.CdUnieco"));
			paramsInsPagoPoliza.put("pv_cdramo", globalVariable.getValueVariableGlobal("vg.CdRamo"));
			paramsInsPagoPoliza.put("pv_estado", globalVariable.getValueVariableGlobal("vg.Estado"));
			paramsInsPagoPoliza.put("pv_nmpoliza", globalVariable.getValueVariableGlobal("vg.NmPoliza"));
			paramsInsPagoPoliza.put("pv_cdunica", cdInsCte);
			paramsInsPagoPoliza.put("pv_nmsuplem", globalVariable.getValueVariableGlobal("vg.NmSuplem"));
			paramsInsPagoPoliza.put("pv_cdgrupa", null);
			paramsInsPagoPoliza.put("pv_status", "V");
			paramsInsPagoPoliza.put("pv_cdforpag", codigoInstrumentoPago);
			
			String numAtr;
			DecimalFormat formatter = new DecimalFormat("00");
			
			for(int i = 1 ; i <= TOTAL_ATRIBUTOS_INSTPAGO; i++){
				numAtr = "cdAtribu_"+Integer.toString(i);
				if(paramsInstPag.containsKey(numAtr)){
					paramsInsPagoPoliza.put("pv_otvalor"+formatter.format(i), paramsInstPag.get(numAtr));
				}else {
					paramsInsPagoPoliza.put("pv_otvalor"+formatter.format(i), null);
				}
				
				
			}
			
			if(logger.isDebugEnabled())logger.debug("Los parametros para instrumentos de pago para la poliza son:"+paramsInsPagoPoliza);
			instrumentoPagoManagerJdbcTemplate.guardaAtributosInstPagoPoliza(paramsInsPagoPoliza);
			
			}catch(Exception e){
				logger.error("Error al guardar los atributos del instrumento de pago " +e.getMessage(),e);
			}
			
		}
		
    	//Guardar movMPOLIAGR2
    	Map paramsMPOLIAGR2 = new HashMap();
    	paramsMPOLIAGR2.put("CD_UNIECO", "1");
    	paramsMPOLIAGR2.put("CD_RAMO", globalVariable.getValueVariableGlobal(VariableKernel.CodigoRamo() ));
    	paramsMPOLIAGR2.put("ESTADO", globalVariable.getValueVariableGlobal(VariableKernel.Estado() ));
    	paramsMPOLIAGR2.put("NMPOLIZA", globalVariable.getValueVariableGlobal(VariableKernel.NumeroPoliza() ));
    	
    
    	paramsMPOLIAGR2.put("NM_SUPLEM", globalVariable.getValueVariableGlobal(VariableKernel.NumeroSuplemento() ));
    	paramsMPOLIAGR2.put("CDPERSON", codigoPersonaUsuario);
    	paramsMPOLIAGR2.put("NMORDDOM", codigoDomicilio);
    	paramsMPOLIAGR2.put("CDFORPAG", codigoInstrumentoPago);
    	paramsMPOLIAGR2.put("CDBANCO", codigoBanco);
    	paramsMPOLIAGR2.put("CD_SUCURSAL", codigoSucursal);
    	paramsMPOLIAGR2.put("NMCUENTA", numeroTarjeta);
    	paramsMPOLIAGR2.put("NMDIGVER", digitoVerificador);
    	cotizacionManager.movMPOLIAGR2(paramsMPOLIAGR2);
    	
    	
    	//COMPRAR
    	GlobalVariableContainerVO global;
    	try {
    		global = kernelManager.comprar(ServletActionContext.getRequest().getSession().getId(), (GlobalVariableContainerVO)session.get("GLOBAL_VARIABLE_CONTAINER"), parametersValidacion,  usuario);
    	}catch(mx.com.ice.services.exception.ApplicationException appExcKernel){
			mensajeValidacion = appExcKernel.getMessage();
			logger.error("Error al ejecutar validaciones de producto: " + appExcKernel.toString(), appExcKernel);
			logger.error("mensajeValidacion : " + mensajeValidacion);
			
			return ERROR;
		}
    
        //Mensaje que será mostrado en un alert indicando el resultado del procedure de comprar()
        //meter aqui pdf
        session.put("cdUnieco", cdUnieco);
        
        String cdRamo= globalVariable.getValueVariableGlobal(VariableKernel.CodigoRamo() );
        session.put("cdRamo", cdRamo);
        String estado=globalVariable.getValueVariableGlobal(VariableKernel.Estado() );
        session.put("estado", estado);
        String polizaW=globalVariable.getValueVariableGlobal(VariableKernel.NumeroPoliza());
        session.put("polizaW", polizaW);
        
        if (log.isDebugEnabled()) {
            log.debug("ya confirmo poliza =" + polizaW);
            log.debug("ya confirmo poliza estadoNew=" + estado);
        }
        
        String []encabezado = {"AUTOS", "TARJETA DE IDENTIFICACION", "Automóviles/Camión  Flotilla"};
        
        mensaje = global.getValueVariableGlobal(VariableKernel.MessageProcess());
        
        
        if (StringUtils.isBlank(global.getValueVariableGlobal(VariableKernel.OT()))) {
        	
        	/*
        	 * TODO QUITAR LA SIGUENTE VALIDACION, CUANDO YA SE HAYA CONSIDERADO EL CASO DEL CDRAMO PARA LA 
        	 * 		COTIZACION DE POZOS PETROLEROS, Y TENER UN FORMATO ADECUADO
        	 */
        	if(StringUtils.isNotBlank(cdRamo)&& RAMO_POZOS_PETROLEROS.equals(cdRamo)){
        		
        		ordenTrabajo = "1";
        		success = true;
        		return SUCCESS;
        		
        	}
        		
        		
	        if (mensaje!=null && (!"".equals(mensaje))){
	        	String[] mensajeFinal=mensaje.split(":");
	            polizaW=mensajeFinal[2];
	            polizaW=polizaW.trim();
	            namePdf="reporte_caratula_poliza"+globalVariable.getValueVariableGlobal("vg.CdUnieco")+cdRamo+polizaW + ".pdf";
	            namePdfRecibo="reporte_recibo_pago"+globalVariable.getValueVariableGlobal("vg.CdUnieco")+cdRamo+polizaW + ".pdf";
	            
	            
	            Map paramsNmsuplem = new HashMap();
	            paramsNmsuplem.put("CDUNIECO", 	globalVariable.getValueVariableGlobal("vg.CdUnieco"));
	            paramsNmsuplem.put("CDRAMO", 	globalVariable.getValueVariableGlobal("vg.CdRamo"));
	            paramsNmsuplem.put("ESTADO", 	"M");
	            paramsNmsuplem.put("NMPOLIZA", 	polizaW);
	            
	            BaseObjectVO numeroSuplemento=cotizacionManager.obtieneNmsuplem(paramsNmsuplem);

	            PDFGenerator.genCaratulaPolizaPdf(globalVariable.getValueVariableGlobal("vg.CdUnieco"), cdRamo, "M", polizaW, encabezado, numeroSituacion,numeroSuplemento.getValue());
	            
	            logger.debug(" cdUnieco Recibo: " +globalVariable.getValueVariableGlobal("vg.CdUnieco"));
	            logger.debug(" cdRamo: " +cdRamo);
	            logger.debug(" polizaW Recibo: " +polizaW);
	            logger.debug(" suplem Recibo: " +numeroSuplemento.getValue());
	            
	            int reciboGenerado = PDFGenerator.generaReciboPagoPdf("", globalVariable.getValueVariableGlobal("vg.CdUnieco"), cdRamo, "M", polizaW, numeroSuplemento.getValue());
	            
	            
	            if (reciboGenerado == -1)
	            	recibo = "1";
	            
	            //TODO quitar este ejemplo de recibo cuando ya se esten guardando bn los recibos en la tabla 'mrecibos' ya que no le esta guardando nada en el campo 'nmimpres'
	            //por ello el recibo que se trata de generar con los datos correctos, no es generado.
	            //PDFGenerator.generaReciboPagoPdf("","1", "500", "M", "7122", "245490912000000000");
	        }	
        } else{
        	ordenTrabajo = "1";
        }
		success = true;
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String funcionesPolizaCotizacionCompra() throws Exception{
		
		UserVO usuario = (UserVO) session.get("USUARIO");
		
		log.debug("Entrando a funcionesPolizaCotizacionCompra()");
		List<RolVO> funcionesPoliza = new ArrayList<RolVO>();
		log.debug("funcionesPersona"+funcionesPersona);
		log.debug("session.containsKey('LISTA_FUNCIONES_PERSONA')"+session.containsKey("LISTA_FUNCIONES_PERSONA"));
		if(session.containsKey("LISTA_FUNCIONES_PERSONA") && funcionesPersona!=null && !funcionesPersona.isEmpty() ){
			List<RolVO> roles = (List<RolVO>) session.get("LISTA_FUNCIONES_PERSONA");
			
			listaPersonasUsuario = catalogosManager.getItemList("OBTIENE_PERSONA_USUARIO", usuario.getUser());
			Map<String,String> personas = null;
			if(listaPersonasUsuario!=null && !listaPersonasUsuario.isEmpty()){
				personas = new HashMap<String, String>();
				for(BaseObjectVO bovo : listaPersonasUsuario){
					personas.put(bovo.getValue(), bovo.getLabel());
				}
			}				
			RolVO nuevoRol = null;
			if(roles.size()>=funcionesPersona.size() && personas !=null){
				int index = 0;		
				int contador = 0;
				for(String s : funcionesPersona){
					logger.debug("logger funcion Poliza = "+s);
					if(s.contains(",")){
						String[] funcionesMultiSelect = s.split(",");
						if(funcionesMultiSelect.length>=1){
							for(String fMS: funcionesMultiSelect){
								nuevoRol = new RolVO();
								nuevoRol.setDescripcionRol(roles.get(index).getDescripcionRol());
								nuevoRol.setFechaNacimiento(roles.get(index).getFechaNacimiento());
								//nuevoRol.setCodigoPersona(roles.get(index).getCodigoPersona());
								nuevoRol.setCodigoRol(roles.get(index).getCodigoRol());
								nuevoRol.setIdentificador(Integer.toString(contador));
								nuevoRol.setCode(fMS);
								nuevoRol.setValue(personas.get(fMS));
								funcionesPoliza.add(nuevoRol);
								contador++;
								log.debug("fMS = " + fMS);
							}
						}
					}else{
						nuevoRol = new RolVO();
						nuevoRol.setDescripcionRol(roles.get(index).getDescripcionRol());
						
						nuevoRol.setFechaNacimiento(roles.get(index).getFechaNacimiento());
						
						nuevoRol.setCodigoRol(roles.get(index).getCodigoRol());
						
						nuevoRol.setIdentificador(Integer.toString(contador));
						
						nuevoRol.setCode(s);
						
						nuevoRol.setValue(personas.get(s));
						
						funcionesPoliza.add(nuevoRol);
						contador++;
						log.debug("s = " + s);
					}
					index++;
				}
			}
		}
		
			
			mx.com.aon.portal.model.RolVO rolActivo= usuario.getRolActivo();
			
			if(rolActivo.getObjeto().getValue()!=null && CDSISROL_ASEGURADO.equalsIgnoreCase(rolActivo.getObjeto().getValue())){
				if(session.containsKey("GRID_LISTA_FUNCIONES_POLIZA")){
					logger.debug("Si existe en sesion el rol Asegurado");
					List<RolVO> funcionesPolizaAsegurado=(List<RolVO>) session.get("GRID_LISTA_FUNCIONES_POLIZA");
					if(funcionesPolizaAsegurado!=null && !funcionesPolizaAsegurado.isEmpty()){
						for(RolVO rolAsegurado:funcionesPolizaAsegurado){
							if(rolAsegurado.getIdentificador()!=null &&CDSISROL_ASEGURADO.equals(rolAsegurado.getIdentificador())){
								funcionesPoliza.add(rolAsegurado);
								logger.debug("Se agrego el rol asegurado al grid de roles en la poliza");
								break;
							}
						}
					}
				}else {
					
					if(logger.isDebugEnabled())logger.debug("ERROR: el rol del usuario de sesion es ASEGURADO y no esta el mapa de session del grid con ese rol, el mapa ha sido borrado!");
				}
			}
			if(session.containsKey("GRID_LISTA_FUNCIONES_POLIZA"))session.remove("GRID_LISTA_FUNCIONES_POLIZA");
			session.put("GRID_LISTA_FUNCIONES_POLIZA", funcionesPoliza);
		//log.debug("funcionesPoliza = " + session.get("GRID_LISTA_FUNCIONES_POLIZA"));
		success = true;
		return SUCCESS;
	}
	
	public String guardaPersonaCotizacion() throws Exception{
		//parametros
		if(log.isDebugEnabled()){
			log.debug("***guardaPersonaCotizacion***");
			log.debug("parametros.rol       :"+parametros.get("rol"));
			log.debug("parametros.nombre    :"+parametros.get("nombre"));
			log.debug("parametros.apPaterno :"+parametros.get("apellidoPaterno"));
			log.debug("parametros.apMaterno :"+parametros.get("apellidoMaterno"));
			log.debug("parametros.Sexo      :"+parametros.get("sexo"));
			log.debug("parametros.fechaNac  :"+parametros.get("fechaNacimiento"));			
		}
		
		claveNuevaPersona = cotizacionManager.guardaPersonaCotizacion(parametros);
		
		success = true;
		return SUCCESS;
	}
	
	//Getters && Setters
	public void setCatalogosManager(CatalogService catalogosManager) { this.catalogosManager = catalogosManager; }	
	public void setCotizacionManager(CotizacionService cotizacionManager) { this.cotizacionManager = cotizacionManager; }
	public void setKernelManager(KernelManager kernelManager) { this.kernelManager = kernelManager; }
	public void setCatalogServiceJdbcTemplate(CatalogServiceJdbcTemplate catalogServiceJdbcTemplate) { this.catalogServiceJdbcTemplate = catalogServiceJdbcTemplate; }

	public boolean isSuccess() { return success; }
	public void setSuccess(boolean success) { this.success = success; }

	public List<CoberturaCotizacionVO> getListaCoberturaCompra() { return listaCoberturaCompra; }
	public void setListaCoberturaCompra( List<CoberturaCotizacionVO> listaCoberturaCompra) { this.listaCoberturaCompra = listaCoberturaCompra; }

	public List<BaseObjectVO> getListaIntrumentoPago() { return listaIntrumentoPago; }
	public void setListaIntrumentoPago(List<BaseObjectVO> listaIntrumentoPago) { this.listaIntrumentoPago = listaIntrumentoPago; }
	public String getCodigoInstrumentoPago() { return codigoInstrumentoPago; }
	public void setCodigoInstrumentoPago(String codigoInstrumentoPago) { this.codigoInstrumentoPago = codigoInstrumentoPago; }
	public String getDescripcionIntrumentoPago() { return descripcionIntrumentoPago; }
	public void setDescripcionIntrumentoPago(String descripcionIntrumentoPago) { this.descripcionIntrumentoPago = descripcionIntrumentoPago; }

	public List<BaseObjectVO> getListaBancos() { return listaBancos; }
	public void setListaBancos(List<BaseObjectVO> listaBancos) { this.listaBancos = listaBancos; }
	public String getCodigoBanco() { return codigoBanco; }
	public void setCodigoBanco(String codigoBanco) { this.codigoBanco = codigoBanco; }
	public String getDescripcionBanco() { return descripcionBanco; } 
	public void setDescripcionBanco(String descripcionBanco) { this.descripcionBanco = descripcionBanco; }

	public String getCdIdentifica() { return cdIdentifica; }
	public void setCdIdentifica(String cdIdentifica) { this.cdIdentifica = cdIdentifica; }
	public String getCdUnieco() { return cdUnieco; }
	public void setCdUnieco(String cdUnieco) { this.cdUnieco = cdUnieco; }
	public String getCdCiaaseg() { return cdCiaaseg; }
	public void setCdCiaaseg(String cdCiaaseg) { this.cdCiaaseg = cdCiaaseg; }
	public String getDsUnieco() { return dsUnieco; }
	public void setDsUnieco(String dsUnieco) { this.dsUnieco = dsUnieco; }
	public String getCdPerpag() { return cdPerpag; }
	public void setCdPerpag(String cdPerpag) { this.cdPerpag = cdPerpag; }
	public String getCdRamo() { return cdRamo; }
	public void setCdRamo(String cdRamo) { this.cdRamo = cdRamo; }
	public String getDsPerpag() { return dsPerpag; }
	public void setDsPerpag(String dsPerpag) { this.dsPerpag = dsPerpag; }
	public String getNumeroSituacion() { return numeroSituacion; }
	public void setNumeroSituacion(String numeroSituacion) { this.numeroSituacion = numeroSituacion; }
	public String getLimitadoasegurado() { return limitadoasegurado; }
	public void setLimitadoasegurado(String limitadoasegurado) { this.limitadoasegurado = limitadoasegurado; }
	public String getCdlimitadoasegurado() { return cdlimitadoasegurado; }
	public void setCdlimitadoasegurado(String cdlimitadoasegurado) { this.cdlimitadoasegurado = cdlimitadoasegurado; }
	public String getDslimitadoasegurado() { return dslimitadoasegurado; }
	public void setDslimitadoasegurado(String dslimitadoasegurado) { this.dslimitadoasegurado = dslimitadoasegurado; }
	public String getAmpliaplataplus() { return ampliaplataplus; }
	public void setAmpliaplataplus(String ampliaplataplus) { this.ampliaplataplus = ampliaplataplus; }
	public String getCdampliaplataplus() { return cdampliaplataplus; }
	public void setCdampliaplataplus(String cdampliaplataplus) { this.cdampliaplataplus = cdampliaplataplus; }
	public String getDsampliaplataplus() { return dsampliaplataplus; }
	public void setDsampliaplataplus(String dsampliaplataplus) { this.dsampliaplataplus = dsampliaplataplus; }
	public String getOtclave1() { return otclave1; }
	public void setOtclave1(String otclave1) { this.otclave1 = otclave1; }
	public String getOtclave2() { return otclave2; }
	public void setOtclave2(String otclave2) { this.otclave2 = otclave2; }
	public String getOtclave3() { return otclave3; }
	public void setOtclave3(String otclave3) { this.otclave3 = otclave3; }
	public String getRow() { return row; }
	public void setRow(String row) { this.row = row; }
	public String getCiaAseg() { return ciaAseg; }
	public void setCiaAseg(String ciaAseg) { this.ciaAseg = ciaAseg; }
	public String getCdPlan() { return cdPlan; }
	public void setCdPlan(String cdPlan) { this.cdPlan = cdPlan; }
	public String getDescripcion() { return descripcion; }
	public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
	public String getDsclave1() { return dsclave1; }
	public void setDsclave1(String dsclave1) { this.dsclave1 = dsclave1; }
	public String getDsclave2() { return dsclave2; }
	public void setDsclave2(String dsclave2) { this.dsclave2 = dsclave2; }

	public String getDescripcionAseguradora() { return descripcionAseguradora; }
	public void setDescripcionAseguradora(String descripcionAseguradora) { this.descripcionAseguradora = descripcionAseguradora; }
	public String getDescripcionPlan() { return descripcionPlan; }
	public void setDescripcionPlan(String descripcionPlan) { this.descripcionPlan = descripcionPlan; }
	public String getDescripcionFormaDePago() { return descripcionFormaDePago; }
	public void setDescripcionFormaDePago(String descripcionFormaDePago) { this.descripcionFormaDePago = descripcionFormaDePago; }
	public String getPlaca() { return placa; }
	public void setPlaca(String placa) { this.placa = placa; }
	public String getNoSerieMotor() { return noSerieMotor; }
	public void setNoSerieMotor(String noSerieMotor) { this.noSerieMotor = noSerieMotor; }
	public String getVigenciaInicio() { return vigenciaInicio; }
	public void setVigenciaInicio(String vigenciaInicio) { this.vigenciaInicio = vigenciaInicio; }
	public String getVigenciaFin() { return vigenciaFin; }
	public void setVigenciaFin(String vigenciaFin) { this.vigenciaFin = vigenciaFin; }
	public String getTipoAccesorio() { return tipoAccesorio; }
	public void setTipoAccesorio(String tipoAccesorio) { this.tipoAccesorio = tipoAccesorio; }
	public String getMontoAccesorio() { return montoAccesorio; }
	public void setMontoAccesorio(String montoAccesorio) { this.montoAccesorio = montoAccesorio; }
	public String getTotalPagar() { return totalPagar; }
	public void setTotalPagar(String totalPagar) { this.totalPagar = totalPagar; }
	public String getReciboUnico() { return reciboUnico; }
	public void setReciboUnico(String reciboUnico) { this.reciboUnico = reciboUnico; }
	public String getNumeroTarjeta() { return numeroTarjeta; }
	public void setNumeroTarjeta(String numeroTarjeta) { this.numeroTarjeta = numeroTarjeta; }
	public String getCodigoAseguradora() { return codigoAseguradora; }
	public void setCodigoAseguradora(String codigoAseguradora) { this.codigoAseguradora = codigoAseguradora; }
	public String getCodigoPlan() { return codigoPlan; }
	public void setCodigoPlan(String codigoPlan) { this.codigoPlan = codigoPlan; }
	public String getCodigoFormaDePago() { return codigoFormaDePago; }
	public void setCodigoFormaDePago(String codigoFormaDePago) { this.codigoFormaDePago = codigoFormaDePago; }
	public String getVencimiento() { return vencimiento; }
	public void setVencimiento(String vencimiento) { this.vencimiento = vencimiento; }
	public String getDsPlan() {	return dsPlan; }
	public void setDsPlan(String dsPlan) { this.dsPlan = dsPlan; }
	public String getFeEmisio() { return feEmisio; }
	public void setFeEmisio(String feEmisio) { this.feEmisio = feEmisio; }
	public String getFeVensim() { return feVensim; }
	public void setFeVensim(String feVensim) { this.feVensim = feVensim; }

	public List<BaseObjectVO> getListaPersonasUsuario() { return listaPersonasUsuario; }
	public void setListaPersonasUsuario(List<BaseObjectVO> listaPersonasUsuario) { this.listaPersonasUsuario = listaPersonasUsuario; }
	public List<String> getFuncionesPersona() { return funcionesPersona; }
	public void setFuncionesPersona(List<String> funcionesPersona) { this.funcionesPersona = funcionesPersona; }
	public List<BaseObjectVO> getListaPersonasUsuarioMultiSelect() { return listaPersonasUsuarioMultiSelect; }
	public void setListaPersonasUsuarioMultiSelect(	List<BaseObjectVO> listaPersonasUsuarioMultiSelect) { this.listaPersonasUsuarioMultiSelect = listaPersonasUsuarioMultiSelect; }
	public String getCodigoPersonaUsuario() { return codigoPersonaUsuario; }
	public void setCodigoPersonaUsuario(String codigoPersonaUsuario) { this.codigoPersonaUsuario = codigoPersonaUsuario; }
	public String getDescripcionPersonaUsuario() { return descripcionPersonaUsuario; }
	public void setDescripcionPersonaUsuario(String descripcionPersonaUsuario) { this.descripcionPersonaUsuario = descripcionPersonaUsuario; }
	public List<RolVO> getFuncionesPoliza() { return funcionesPoliza; }
	public void setFuncionesPoliza(List<RolVO> funcionesPoliza) { this.funcionesPoliza = funcionesPoliza; }
	
	public List<MPoliObjVO> getListaMPoliObj() { return listaMPoliObj; }
	public void setListaMPoliObj(List<MPoliObjVO> listaMPoliObj) { this.listaMPoliObj = listaMPoliObj; }
	
	public List<BaseObjectVO> getListaDomicilio() { return listaDomicilio; }
	public void setListaDomicilio(List<BaseObjectVO> listaDomicilio) { this.listaDomicilio = listaDomicilio; }
	public String getCodigoDomicilio() { return codigoDomicilio; }
	public void setCodigoDomicilio(String codigoDomicilio) { this.codigoDomicilio = codigoDomicilio; }
	public String getDescripcionDomicilio() { return descripcionDomicilio; }
	public void setDescripcionDomicilio(String descripcionDomicilio) { this.descripcionDomicilio = descripcionDomicilio; }
	
	public List<BaseObjectVO> getListaSucursal() { return listaSucursal; }
	public void setListaSucursal(List<BaseObjectVO> listaSucursal) { this.listaSucursal = listaSucursal; }
	public String getCodigoSucursal() { return codigoSucursal; }
	public void setCodigoSucursal(String codigoSucursal) { this.codigoSucursal = codigoSucursal; }
	public String getDescripcionSucursal() { return descripcionSucursal; }
	public void setDescripcionSucursal(String descripcionSucursal) { this.descripcionSucursal = descripcionSucursal; }

	public String getValidacion() { return validacion; }
	public void setValidacion(String validacion) { this.validacion = validacion; }

	public String getRowFuncionPoliza() { return rowFuncionPoliza; }
	public void setRowFuncionPoliza(String rowFuncionPoliza) { this.rowFuncionPoliza = rowFuncionPoliza; }
	public String getRolFuncionPoliza() { return rolFuncionPoliza; }
	public void setRolFuncionPoliza(String rolFuncionPoliza) { this.rolFuncionPoliza = rolFuncionPoliza; }
	public String getDescripcionRolFuncionPoliza() { return descripcionRolFuncionPoliza; }
	public void setDescripcionRolFuncionPoliza(String descripcionRolFuncionPoliza) { this.descripcionRolFuncionPoliza = descripcionRolFuncionPoliza; }

	public List<String> getFuncionesPersonaDescripciones() { return funcionesPersonaDescripciones; }
	public void setFuncionesPersonaDescripciones( List<String> funcionesPersonaDescripciones) { this.funcionesPersonaDescripciones = funcionesPersonaDescripciones; }

	public List<BaseObjectVO> getListaSexo() {return listaSexo;}
	public void setListaSexo(List<BaseObjectVO> listaSexo) {this.listaSexo = listaSexo;}
	public List<BaseObjectVO> getListaFuncionRol() {return listaFuncionRol;}
	public void setListaFuncionRol(List<BaseObjectVO> listaFuncionRol) {this.listaFuncionRol = listaFuncionRol;}
	public Map<String, String> getParametros() {return parametros;}
	public void setParametros(Map<String, String> parametros) {this.parametros = parametros;}
	
	//public List<BaseObjectVO> getListaTipoTarjeta() { return listaTipoTarjeta;	}
	//public void setListaTipoTarjeta(List<BaseObjectVO> listaTipoTarjeta) {	this.listaTipoTarjeta = listaTipoTarjeta; }

	public List<WrapperResultados> getListaTipoTarjetaYCredito() { return listaTipoTarjetaYCredito;	}
	public void setListaTipoTarjetaYCredito( List<WrapperResultados> listaTipoTarjetaYCredito) { this.listaTipoTarjetaYCredito = listaTipoTarjetaYCredito; }
	
	public String getDigitoVerificador() { return digitoVerificador; }
	public void setDigitoVerificador(String digitoVerificador) { this.digitoVerificador = digitoVerificador; }

	public String getCodigoTipoTarjeta() { return codigoTipoTarjeta; }
	public void setCodigoTipoTarjeta(String codigoTipoTarjeta) { this.codigoTipoTarjeta = codigoTipoTarjeta; }

	public String getDescripcionTipoTarjeta() { return descripcionTipoTarjeta; }
	public void setDescripcionTipoTarjeta(String descripcionTipoTarjeta) { this.descripcionTipoTarjeta = descripcionTipoTarjeta; }

	public String getSwCredito() { return swCredito; }
	public void setSwCredito(String swCredito) { this.swCredito = swCredito; }

	public String getNamePdf() {
		return namePdf;
	}

	public void setNamePdf(String namePdf) {
		this.namePdf = namePdf;
	}

	public String getNamePdfRecibo() {
		return namePdfRecibo;
	}

	public void setNamePdfRecibo(String namePdfRecibo) {
		this.namePdfRecibo = namePdfRecibo;
	}
	
	/**
	 * @return the codigoTipoSituacion
	 */
	public String getCodigoTipoSituacion() {
		return codigoTipoSituacion;
	}

	/**
	 * @param codigoTipoSituacion the codigoTipoSituacion to set
	 */
	public void setCodigoTipoSituacion(String codigoTipoSituacion) {
		this.codigoTipoSituacion = codigoTipoSituacion;
	}
	
	public String getNumRecibos() {
		return numRecibos;
	}

	public void setNumRecibos(String numRecibos) {
		this.numRecibos = numRecibos;
	}

	public String getRecibosSub() {
		return recibosSub;
	}

	public void setRecibosSub(String recibosSub) {
		this.recibosSub = recibosSub;
	}

	public String getClaveNuevaPersona() {
		return claveNuevaPersona;
	}

	public void setClaveNuevaPersona(String claveNuevaPersona) {
		this.claveNuevaPersona = claveNuevaPersona;
	}

	public ServiciosGeneralesSistema getServiciosGeneralesSistema() {
		return serviciosGeneralesSistema;
	}

	public void setServiciosGeneralesSistema(
			ServiciosGeneralesSistema serviciosGeneralesSistema) {
		this.serviciosGeneralesSistema = serviciosGeneralesSistema;
	}

	/**
	 * @return the ordenTrabajo
	 */
	public String getOrdenTrabajo() {
		return ordenTrabajo;
	}

	/**
	 * @param ordenTrabajo the ordenTrabajo to set
	 */
	public void setOrdenTrabajo(String ordenTrabajo) {
		this.ordenTrabajo = ordenTrabajo;
	}

	public List<BaseObjectVO> getListaFuncionRolReq() {
		return listaFuncionRolReq;
	}

	public void setListaFuncionRolReq(List<BaseObjectVO> listaFuncionRolReq) {
		this.listaFuncionRolReq = listaFuncionRolReq;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<FourValueObjectVO> getListaIntrumentoPagos() {
		return listaIntrumentoPagos;
	}

	public void setListaIntrumentoPagos(List<FourValueObjectVO> listaIntrumentoPagos) {
		this.listaIntrumentoPagos = listaIntrumentoPagos;
	}

	public String getCdSisrol() {
		return cdSisrol;
	}

	public void setCdSisrol(String cdSisrol) {
		this.cdSisrol = cdSisrol;
	}

	public String getMensajeValidacion() {
		return mensajeValidacion;
	}

	public void setMensajeValidacion(String mensajeValidacion) {
		this.mensajeValidacion = mensajeValidacion;
	}

	public void setInstrumentoPagoManagerJdbcTemplate(
			InstrumentoPagoManager instrumentoPagoManagerJdbcTemplate) {
		this.instrumentoPagoManagerJdbcTemplate = instrumentoPagoManagerJdbcTemplate;
	}

	public List<InstrumentoPagoAtributosVO> getInstrumentosClienteList() {
		return instrumentosClienteList;
	}

	public void setInstrumentosClienteList(
			List<InstrumentoPagoAtributosVO> instrumentosClienteList) {
		this.instrumentosClienteList = instrumentosClienteList;
	}

	/*public List<AtributosVariablesInstPagoVO> getAtributosInstrumentoPago() {
		return atributosInstrumentoPago;
	}

	public void setAtributosInstrumentoPago(
			List<AtributosVariablesInstPagoVO> atributosInstrumentoPago) {
		this.atributosInstrumentoPago = atributosInstrumentoPago;
	}*/

	public String getCdInsCte() {
		return cdInsCte;
	}

	public void setCdInsCte(String cdInsCte) {
		this.cdInsCte = cdInsCte;
	}

	public String getStoresCombos() {
		return storesCombos;
	}

	public void setStoresCombos(String storesCombos) {
		this.storesCombos = storesCombos;
	}

	public Map<String, String> getParamsInstPag() {
		return paramsInstPag;
	}

	public void setParamsInstPag(Map<String, String> paramsInstPag) {
		this.paramsInstPag = paramsInstPag;
	}
	
}