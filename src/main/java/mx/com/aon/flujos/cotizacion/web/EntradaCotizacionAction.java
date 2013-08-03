/**
 * 
 */
package mx.com.aon.flujos.cotizacion.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mx.com.aon.configurador.pantallas.model.PantallaVO;
import mx.com.aon.configurador.pantallas.model.components.ComboClearOnSelectVO;
import mx.com.aon.core.VariableKernel;
import mx.com.aon.flujos.cotizacion.service.CotizacionPrincipalManager;
import mx.com.aon.flujos.cotizacion.service.CotizacionService;
import mx.com.aon.flujos.endoso.service.EndosoManager;
import mx.com.aon.flujos.model.TatriParametrosVO;
import mx.com.aon.kernel.service.KernelManager;
import mx.com.aon.portal.model.BaseObjectVO;
import mx.com.aon.portal.model.UserVO;
import mx.com.ice.services.to.screen.GlobalVariableContainerVO;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.biosnet.ice.ext.elements.form.ComboControl;
import mx.com.aon.core.ApplicationException;

/**
 * 
 * Clase Action para obtener elementos necesarios antes de cargar
 * la pantalla de filtro
 * 
 * @author aurora.lozada
 * 
 */
public class EntradaCotizacionAction extends PrincipalCotizacionAction {

    /**
     * 
     */
    private static final long serialVersionUID = -5462761968868387436L;
    
    private static final String TIP_SUP_COBERTURAS = "90";

    private CotizacionService cotizacionManager;
    protected EndosoManager endosoManager;
    private CotizacionPrincipalManager cotizacionManagerJdbcTemplate;
    
    /**
     * Servicio inyectado por spring para llamar a los servicios de valores por defecto del kernel
     */
    private KernelManager kernelManager;
    
    private List<Map<String, TatriParametrosVO>> tatriParamsList = new ArrayList<Map<String, TatriParametrosVO>>();
    
    private String flujoCorrecto;
    
   
   
    @Override
    public void prepare() throws Exception {
        //TODO Se comento la siguiente linea para que al regresar a la pantalla
        //no se pierda la información seleccionada.
    	//session.remove("COTIZACION_INPUT");
    	//session.remove("GLOBAL_VARIABLE_CONTAINER");
    	if(session.containsKey("CCCDRAMO"))
    		session.remove("CCCDRAMO");
    	if(session.containsKey("CCCDTIPSIT"))
    		session.remove("CCCDTIPSIT");
    }
    
    /*
     * @param none
     * @return void
     * Método para cambiar la propiedad 'editable' de todos los elementos EXTJs combo 
     * de false a true.
     */
    @SuppressWarnings("unchecked")
    private void changePropertyEditableCombos (){
    	String pantalla = (String) session.get("CAPTURA_PANTALLA");
    	/* 
    	 * Primera parte: se buscan los elementos de tipo combo para encontrar
    	 * los indices de las propiedades editable.
    	 */     		
    	String tmp = pantalla;
    	List<Integer> indices = new ArrayList<Integer>();
    	boolean bandera = true;
    	while ( bandera ) {
    		int i = tmp.indexOf( "\"xtype\":\"combo\"" );
    		if (i != -1) {
    			int j = StringUtils.lastIndexOf( tmp, "\"editable\":false" );
    			indices.add( new Integer( j ) );
    			tmp = tmp.substring( i + "\"xtype\":\"combo\"".length() );
    		} else {
    			bandera = false;
    		}
    	} 
    	/*
    	 * Segunda parte: se reemplaza el valor de la propiedad editable para formar
    	 * la pantalla final.
    	 */
    	tmp = pantalla;
    	for (Integer c : indices){
    		int y = c.intValue();
    		String substring = tmp.substring( y );
			substring = substring.replaceFirst("\"editable\":false", "\"editable\":true");
			tmp = tmp.substring(0, y) + substring;
    	}
    	
    	session.put("CAPTURA_PANTALLA", (String)tmp);
    	
    	return;
    }

	/*
	 * @param actual
	 * @param combosC
	 * @param listaClearValue
	 * @param comboList
	 * @return lcv
	 * Mètodo que realiza una revisiòn sobre todos los combos para determinar si un combo debe 
	 * de incluir a ciertos combos en el evento onSelect segùn el mapa 'combos'.
	 */
	@SuppressWarnings("unchecked")
	private Map<String,String> finalOnSelect( String actual, Map<String,String> combosC, Map<String,String> listaClearValue, List<ComboControl> comboList ) {
		Map<String,String> lcv = listaClearValue;
		Map<String,String> combosCopia = combosC;
		
		for ( int i = 0; i < comboList.size(); i++ ) {
			ComboControl c = comboList.get(i);
			String padre = c.getBackupTable();
			logger.debug("|||| PADRE = " + padre);
			if ( actual.equals( combosC.get(padre) ) ) {
				logger.debug("|||| ENTRO IF CON = " + padre);
				lcv.put( " Ext.getCmp('" + c.getId() + "').clearValue(); ", "");
				//combosCopia.remove( actual );
				lcv = finalOnSelect( c.getBackupTable(), combosCopia, lcv, comboList );
			}
		}
		
		return lcv;
	}
    
    /*
     * @param cdtipsit
     * @return void
     * Método para modificar el evento onSelect de los combos para que filtre adecuadamente.
     * Anteriormente se limpiaba la selección de algunos combos que no tenían relación alguna cuando 
     * se seleccionaba un nuevo valor del combo 'padre'.
     */
    @SuppressWarnings("unchecked")
    private void modifyOnSelect(String cdtipsit){
    	logger.debug("##### Entrando a método modifyOnSelect");

    	StringBuilder res = new StringBuilder( (String) session.get("CAPTURA_PANTALLA") );
    	List<ComboControl> comboList = new ArrayList<ComboControl>();
    	Map<String,String> combos = new HashMap<String,String>();
    	/*
    	 * Se obtienen los combos de la pantalla final para asignarlos al objeto ComboControl
    	 */
    	try {
    		Map<String,String> storesNames = getStoresTables( (String) session.get("CAPTURA_PANTALLA") );
            Iterator It = storesNames.entrySet().iterator();
            while (It.hasNext()) {
            	ComboControl cc = new ComboControl();
            	Map.Entry entry = (Map.Entry) It.next();
            	// Se setean los valores Id y BackupTable en el objeto ComboControl
            	cc.setId( (String)entry.getKey() );
            	cc.setBackupTable( (String)entry.getValue() );
            	// Se agrega el objeto ComboControl  en la lista comboList
            	comboList.add(cc);
            	// Se agrega en el mapa el nombre de la tabla de cada combo
            	combos.put( cc.getBackupTable(), null );
            }
    	} catch(Exception e) {
    		logger.error("Error al obtener getStoresTables para comboList");
    	}

    	/*
    	 * Este bloque llena el mapa 'combos' para determinar quién es el padre de cada uno de los combos 
    	 */
    	try {
    		for ( int i = 0; i < comboList.size(); i++ ) {
				Map<String,String> parameters = new HashMap<String,String>();
				ComboControl c = comboList.get(i);
				parameters.put("pv_cdtipsit_i", cdtipsit);
				parameters.put("pv_ottabval_i", c.getBackupTable());
				List<ComboClearOnSelectVO> resultados = (List<ComboClearOnSelectVO>)cotizacionManagerJdbcTemplate.getComboPadre(parameters);
				if ( resultados.size() != 0 ) {
					ComboClearOnSelectVO ccosvo = resultados.get(0);
					combos.put(c.getBackupTable(), ccosvo.getOttabval());
				} else {
					combos.put(c.getBackupTable(), null);
				}
    		}
    	} catch(ApplicationException ae) {
    		logger.error("Error al invocar getComboPadre: " + ae);
    	}
    	
		Iterator it = combos.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			logger.debug("key = " + entry.getKey() + " value = " + entry.getValue());
		}

		/*
		 * Se modifica el evento onSelect de los combos para permitir a los combos que tienen  
		 * relación en el filtrado invocar el método clearValue.
		 */
		for ( int i = 0; i < comboList.size(); i++ ) {
			Map<String,String> lcv = new HashMap<String,String>();
			ComboControl c = comboList.get(i);
			lcv = finalOnSelect(c.getBackupTable(), combos, lcv, comboList);
			
			Iterator iter = lcv.entrySet().iterator();
			String listaCombosClearValue = "";

			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				listaCombosClearValue += entry.getKey();
			}

			logger.debug("@@@ listaCombosClearValue = " + listaCombosClearValue);
			int startOnSelect = res.indexOf( "Ext.getCmp('" + (String) c.getId() + "').on('select',function(combo,record,ind) { " );
			int endOnSelect = res.indexOf("}); });", startOnSelect);
			if ( startOnSelect != -1 ) {
				String id = (String)c.getId();
				startOnSelect += "Ext.getCmp('".length() + id.length() + "').on('select',function(combo,record,ind) { ".length();
				StringBuilder tmp = new StringBuilder ( res.substring(startOnSelect, endOnSelect) );
				int endClearValue = tmp.lastIndexOf(".clearValue();");
				// Si es distinto de -1 significa que el evento onSelect no ha sido modificado desde el 
				// Configurador de Pantallas.
				if ( endClearValue != -1 ) {
					 endClearValue += ".clearValue();".length() + 1;
					 tmp = tmp.replace( 0, endClearValue, listaCombosClearValue);
					 res = res.replace( startOnSelect, endOnSelect, tmp.toString() );
				}
			}
		}
		
		logger.debug("|||| COMPLETEJSON = " + res.toString());
		
		session.put( "CAPTURA_PANTALLA", res.toString() );
    	
    	logger.debug("#### SALIENDO DE modifyOnSelect");
    	
    	return;
    }
    
    
    /**
     * Metodo que obtiene los parametros necesarios en ejecucion de cada componente 
     * en pantalla
     * 
     * @return Cadena SUCCESS
     */
    @SuppressWarnings("unchecked")
    public String entrar() throws Exception {
    	
    	//UserVO emailUser=(UserVO) session.get("USUARIO");

    	if(session.containsKey("BACK")){
    		session.remove("BACK");
    	}
    	
    	
        boolean isDebugueable = logger.isDebugEnabled();

        if (isDebugueable) {
            logger.debug("### METODO entrar en EntradaCotizacionAction...");
        }
    	
    	if (session.containsKey("USUARIO")) {
    		logger.debug("USER ENTRADA!!!");
    	} else if (session.containsKey("CARGA_USUARIO_COMPLETO")) {
    		List<UserVO> user = (List<UserVO>)session.get("CARGA_USUARIO_COMPLETO");
    		logger.debug("USERS carga: " + user.size());
    	}
    	UserVO usuario = (UserVO)session.get("USUARIO");
        
    	//CDTITULO
        String cdTitulo = ServletActionContext.getRequest().getParameter("CDTITULO");
        if (StringUtils.isNotBlank(cdTitulo)) {
            session.put("CDTITULO", ServletActionContext.getRequest().getParameter("CDTITULO"));
        } else {
            cdTitulo = (String) session.get("CDTITULO");
        }
    	
    	if (logger.isDebugEnabled()) {
    		logger.debug("CDELEMENTO: " + usuario.getEmpresa().getElementoId());
    		logger.debug("CDPERSON: " + usuario.getCodigoPersona());
    		logger.debug("USER: " + usuario.getUser());
    		logger.debug("ROL ACTIVO: " + usuario.getRolActivo().getObjeto().getValue());
        	logger.debug("idSesion: " + ServletActionContext.getRequest().getSession().getId());
        	logger.debug("CDRAMO: " + ServletActionContext.getRequest().getParameter("CDRAMO"));
        	logger.debug("CDTIPSIT: " + ServletActionContext.getRequest().getParameter("CDTIPSIT"));
        	logger.debug("CDTITULO: " + ServletActionContext.getRequest().getParameter("CDTITULO"));
            logger.debug("cdTitulo: " + cdTitulo);
    	}
    	    	
    	//CARGAR VALORES POR DEFECTO:
    	String tipsit = "";
    	if (ServletActionContext.getRequest().getParameter("CDTIPSIT").equals("A ")){
    		tipsit = "A+";
    	} else {
    		tipsit = ServletActionContext.getRequest().getParameter("CDTIPSIT");
    	}	
    	
    	GlobalVariableContainerVO globalVarVo = new GlobalVariableContainerVO();
    	globalVarVo.addVariableGlobal(VariableKernel.UnidadEconomica(), "1");
    	globalVarVo.addVariableGlobal(VariableKernel.CodigoRamo(), ServletActionContext.getRequest().getParameter("CDRAMO"));
    	globalVarVo.addVariableGlobal(VariableKernel.Estado(), "W");
    	globalVarVo.addVariableGlobal(VariableKernel.NumeroSuplemento(), "0");
    	globalVarVo.addVariableGlobal(VariableKernel.UsuarioBD(), usuario.getUser());
    	globalVarVo.addVariableGlobal(VariableKernel.CodigoTipoSituacion(), tipsit);
    	globalVarVo.addVariableGlobal(VariableKernel.NumeroSituacion(),"1");
    	globalVarVo = kernelManager.cargaValoresPorDefecto(ServletActionContext.getRequest().getSession().getId(), usuario, globalVarVo, TIP_SUP_COBERTURAS);
    	
    	//Subimos a sesion la Global Variable Container
    	session.put("GLOBAL_VARIABLE_CONTAINER", globalVarVo);
    	
        if (isDebugueable) {
            logger.debug("### Obtener pantalla final...");
        }
        
        Map<String, String> parametersPantallaFinal = new HashMap<String, String>();
        parametersPantallaFinal.put("pv_cdelemento_i", usuario.getEmpresa().getElementoId());
        parametersPantallaFinal.put("pv_cdramo_i", globalVarVo.getValueVariableGlobal("vg.CdRamo"));
        parametersPantallaFinal.put("pv_cdtipsit_i",   globalVarVo.getValueVariableGlobal(VariableKernel.CodigoTipoSituacion()));
        parametersPantallaFinal.put("pv_cdtitulo_i", cdTitulo);
        parametersPantallaFinal.put("pv_cdsisrol_i", usuario.getRolActivo().getObjeto().getValue());
        PantallaVO pantalla = new PantallaVO ();
        
        pantalla = cotizacionManagerJdbcTemplate.getPantallaFinal(parametersPantallaFinal);
        session.put("CAPTURA_PANTALLA", StringEscapeUtils.unescapeHtml( pantalla.getDsArchivoSec()) );

        changePropertyEditableCombos();
        
        modifyOnSelect((String)globalVarVo.getValueVariableGlobal(VariableKernel.CodigoTipoSituacion()));
        
        if (isDebugueable){
        	logger.debug("@@CAPTURA_PANTALLA==> "+session.get("CAPTURA_PANTALLA"));
        	logger.debug("### pantalla.getDsArchivoSec()..." + pantalla.getDsArchivoSec());
           	logger.debug("CONTAIN GLOBAL: " + session.containsKey("GLOBAL_VARIABLE_CONTAINER"));
        }

        /*
         * Subir a sesion nombres en BD de tablas de los stores  
         */
        setNombresTablasStores(pantalla);
        
        session.put("CREGRESAR", "N");
        
        return INPUT;
    }
    
    /**
     * Para regresar a la pantalla inicial de Cotizacion con los valores elegidos previamente en caso de haber regresado de cualquier forma excepto click en el boton regresar 
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public String obtenerRegresar() throws Exception {
    	
    	
    	if(session.containsKey("BACK")){
    		this.flujoCorrecto=(String)session.get("BACK");
    		logger.debug("Valor tomado luego de session : "+this.flujoCorrecto);
    		
    		
    	}else {
    		flujoCorrecto="true";
    		logger.debug("no hay ningun valor en la sesion");
    	}
    	
    	return SUCCESS;
    }
    /**
     * Regresar a la pantalla inicial de Cotizacion con los valores elegidos previamente
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public String regresar() throws Exception {
    	
        if (logger.isDebugEnabled()) {
            logger.debug("### Metodo regresar() ...");
        }
        
        if(session.containsKey("BACK")){
    		session.remove("BACK");
    	}
    	

    	/*
    	 *  Copia de lineas de metodo entrar() para obtener un nuevo numero de poliza
    	 */
    	UserVO usuario = (UserVO)session.get("USUARIO");
    	GlobalVariableContainerVO globalVarVo = new GlobalVariableContainerVO();
        globalVarVo = (GlobalVariableContainerVO) session.get("GLOBAL_VARIABLE_CONTAINER");
        String numeroPolizaAnterior = globalVarVo.getValueVariableGlobal(VariableKernel.NumeroPoliza());//Se almacena el ANTERIOR numero de poliza
    	globalVarVo = kernelManager.cargaValoresPorDefecto(ServletActionContext.getRequest().getSession().getId(), usuario, globalVarVo, TIP_SUP_COBERTURAS);
    	String numeroPolizaNueva = globalVarVo.getValueVariableGlobal(VariableKernel.NumeroPoliza());//Se almacena el NUEVO numero de poliza
    	/*
    	 * Termina copia de lineas
    	 */
    	
    	
    	/*
    	 * Se clonan los objetos de la poliza anterior hacia la nueva poliza
    	 */
    	Map<String, String> paramsClonaObjetos = new HashMap<String, String>();
    	paramsClonaObjetos.put("CDUNIECO", globalVarVo.getValueVariableGlobal(VariableKernel.UnidadEconomica()));
    	paramsClonaObjetos.put("CDRAMO", globalVarVo.getValueVariableGlobal(VariableKernel.CodigoRamo()));
    	paramsClonaObjetos.put("ESTADO", globalVarVo.getValueVariableGlobal(VariableKernel.Estado()));
    	paramsClonaObjetos.put("NMPOLIZA", numeroPolizaAnterior);
    	paramsClonaObjetos.put("NMPOLNVA", numeroPolizaNueva);
    	cotizacionManager.clonaObjetos(paramsClonaObjetos);
    	/*
    	 * Termina clonar objetos de la poliza anterior hacia la nueva poliza
    	 */
    	

        /*
         * Se obtiene la pantalla final
         */
        String cdTitulo = ServletActionContext.getRequest().getParameter("CDTITULO");
        if (StringUtils.isNotBlank(cdTitulo)) {
            session.put("CDTITULO", ServletActionContext.getRequest().getParameter("CDTITULO"));
        } else {
            cdTitulo = (String) session.get("CDTITULO");
        }
        Map<String, String> parametersPantallaFinal = new HashMap<String, String>();
        parametersPantallaFinal.put("CD_ELEMENT", usuario.getEmpresa().getElementoId());
        parametersPantallaFinal.put("CD_RAMO", globalVarVo.getValueVariableGlobal("vg.CdRamo"));
        parametersPantallaFinal.put("CD_TIPSIT", globalVarVo.getValueVariableGlobal(VariableKernel.CodigoTipoSituacion()));
        parametersPantallaFinal.put("CD_TITULO", cdTitulo);
        parametersPantallaFinal.put("CD_SISROL", usuario.getRolActivo().getObjeto().getValue());
        PantallaVO pantalla = new PantallaVO ();
        
        pantalla = cotizacionManager.getPantallaFinal(parametersPantallaFinal);
        session.put("CAPTURA_PANTALLA", StringEscapeUtils.unescapeHtml( pantalla.getDsArchivoSec()) );
        logger.debug("CAPTURA_PANTALLA:"+session.get("CAPTURA_PANTALLA"));
        
        if (logger.isDebugEnabled()){
        	logger.debug("### pantalla.getDsArchivoSec()..." + pantalla.getDsArchivoSec());
           	logger.debug("CONTAIN GLOBAL: " + session.containsKey("GLOBAL_VARIABLE_CONTAINER"));
        }
        /*
         * Termina obtenciòn de pantalla final
         */
        
        /*
         * Si tiene la variable de session COTIZACION_INPUT viene de regresar
         */
        String pantallaInicialCot = StringEscapeUtils.unescapeHtml(pantalla.getDsArchivoSec());
        if(session.containsKey("COTIZACION_INPUT")){
        	logger.debug("COTIZACION_INPUT ENTRADA : "+session.get("COTIZACION_INPUT"));
        	if (session.get("COTIZACION_INPUT")!= null){
        		/*
        		 * Se coloca en session variable CREGRESAR con valor 'S' ya que en capturaCotizacion-script.jsp
        		 * se agregarà codigo para reestablecer valores ingresados anteriormente  		 
        		 */ 
        		session.put("CREGRESAR", "S");
        		String key = null;
        		String text[] = null;
        		Map<String,String> storesNames = new HashMap<String, String>();
        		Map<String, String> paramsCotInput = new HashMap<String, String>();
        		Map<String,String> comboLabelValue = new HashMap<String, String>();

        		/*
        		 * Inicia obtencion de valores elegidos de la pantalla incial
        		 */        		
        		paramsCotInput = (Map<String, String>) session.get("COTIZACION_INPUT");
        		logger.debug("paramsCotInput : "+paramsCotInput);
        		storesNames = getStoresTables(pantallaInicialCot);
        		logger.debug("storesNames : "+storesNames);

        		Iterator itcs = paramsCotInput.entrySet().iterator();
        		while (itcs.hasNext()) {
					Map.Entry e = (Map.Entry) itcs.next();
					key = (String) e.getKey();
					String temp = StringUtils.substringAfterLast(e.getKey().toString(), ".");
					if (StringUtils.isNotBlank(temp)) {
						text = e.getKey().toString().split("\\.");
						if (text.length >= 2) {
							if (text[0].equals("parameters")) {
								comboLabelValue.put(text[1].toString(), e.getValue().toString());
								logger.debug("----- Atributo Variable ==>" + text[1].toString() + "=" + e.getValue().toString());
							}
						}

						Iterator itsn = storesNames.entrySet().iterator();
						boolean valorEncontrado = false;
						while (itsn.hasNext() && !valorEncontrado ) {
							Map.Entry es = (Map.Entry) itsn.next();

							if (temp.contentEquals(es.getKey().toString())) {
								logger.debug("----- Atributo Variable tipo COMBO ==>"+ es.getKey()+ "="+ (String) e.getValue());
								comboLabelValue.put("" + es.getKey(),(String) e.getValue());
								valorEncontrado = true;
							}
						}
					}
				}
                logger.debug("comboLabelValue : "+comboLabelValue);
                /*
				 * Termina obtencion de valores elegidos de la pantalla inicial
				 */

                /*
                 * Se asignan valores a los elementos que NO son combo
                 */                
        		Iterator codeExt = comboLabelValue.entrySet().iterator();
        		String codeExtStrig = "";
        		while (codeExt.hasNext()) {
        			Map.Entry ce = (Map.Entry)codeExt.next();
        			codeExtStrig += "var comp = Ext.getCmp('"+ce.getKey()+"'); if(comp.getXType() != 'combo'){  comp.setValue('"+ce.getValue()+"'); }";
        		}
        		// Se sube a sesion la cadena que contiene la asignacion de los valores de los elementos que no son combos 
                session.put("ComboLabelValue", codeExtStrig);
                /*
                 * Termina la asignacion de valores a los elementos que NO son combo
                 */
                
                
                //Contemplamos los casos en que varios combos tengan el mismo nombre de tabla y creamos las sentencias para su callback
                HashMap<String, List<BaseObjectVO>> listaMapaCallback = obtenerMapaCallback(storesNames, comboLabelValue);
                logger.debug("listaMapaCallback=" + listaMapaCallback);
                
                
                /*
                 * Inicia asignacion de valores a elementos que son combo.
                 * Se obtiene el nombre del data store, se asignan parametros base y  
                 * se asigna el valor de la pantalla inicial.
                 */
                HashMap mapaParamsDatosVariables = (HashMap)session.get("PARAMS_DATOS_VARIABLES");
                logger.debug("PARAMS_DATOS_VARIABLES=" + mapaParamsDatosVariables);
            	//Iterar nombres de stores
                Iterator itStores = storesNames.entrySet().iterator();
                StringBuffer codeExtStores = new StringBuffer();
                while (itStores.hasNext()) {
                	Map.Entry entryStores = (Map.Entry)itStores.next();
                	String idCombo = entryStores.getKey().toString();
                	String nombreTabla = entryStores.getValue().toString();
                	logger.debug("clave (idCombo)="+ idCombo);
                	logger.debug("valor (nombreTabla)="+ nombreTabla);
                	if( mapaParamsDatosVariables.containsKey(nombreTabla) ){
                		//obtener los parametros valAnterior y valAntAnt
                		List listaParams = new ArrayList<String>();
                		listaParams = (ArrayList<String>)mapaParamsDatosVariables.get(nombreTabla);
                		String valorElegidoCombo = comboLabelValue.get( idCombo );
                		codeExtStores.append( "store"+nombreTabla+".baseParams['ottabval'] = '"+nombreTabla+"';" );
                		codeExtStores.append( "store"+nombreTabla+".baseParams['valAnterior'] = '"+listaParams.get(0)+"';" );
                		codeExtStores.append( "store"+nombreTabla+".baseParams['valAntAnt'] = '"+listaParams.get(1)+"';");
                		codeExtStores.append( "store"+nombreTabla+".load({ callback: function(r, options, success){ ");
                		
                		if(!listaMapaCallback.isEmpty() && listaMapaCallback.containsKey(nombreTabla)){
                			List<BaseObjectVO> listaClaveValor = listaMapaCallback.get(nombreTabla);
                			for(BaseObjectVO claveValorVO: listaClaveValor){
                				//Crear sentencias que asignan valores a componentes que utilizan el MISMO store (si se da el caso)
                				codeExtStores.append( "Ext.getCmp('"+claveValorVO.getLabel()+"').setValue('"+claveValorVO.getValue()+"'); " );
                			}
                		}else{
                			codeExtStores.append( "Ext.getCmp('"+idCombo+"').setValue('"+valorElegidoCombo+"'); " );
                		}
                		
                		codeExtStores.append( "} });" );
                	}
                }
                logger.debug("codeExtStores=" + codeExtStores.toString());
                session.put("storesCargados", codeExtStores.toString());
                /*
                 * Termina asignacion de valores a elementos que son combo
                 */
        	}else{
        		session.put("CREGRESAR", "N");
        	}        	
        }
    	        
        logger.debug("PARAMS_DATOS_VARIABLES=" + session.get("PARAMS_DATOS_VARIABLES"));
        
    	return INPUT;
    }
    
    
    /**
     * Obtener mapa con el nombre de las tablas que se repitan y los valores elegidos previamente para asignarlos en el callback del store.
     * Esto es para evitar que no se asignen correctamente los valores elegidos al 'Regresar'
     * @return mapa con el nombre de las tablas repetidas  y los valores de los combos 
     */
    @SuppressWarnings("unchecked")
    protected HashMap<String, List<BaseObjectVO>> obtenerMapaCallback(Map storeNames, Map comboLabelValue){
    	
    	HashMap<String, List<BaseObjectVO>> mapaCallBack = new HashMap<String, List<BaseObjectVO>>();
    	 
    	List<String> listaTablasRepetidas = new ArrayList<String>();
    	//Se itera los nombres de los stores para obtener el nombre de la listaTablasRepetidas
    	Iterator itStoreNames = storeNames.entrySet().iterator();
    	Map<String, String> mapaTemporal = new HashMap<String, String>();
    	while (itStoreNames.hasNext()) {
    		Map.Entry entryStoreNames = (Map.Entry)itStoreNames.next();
    		
    		if(mapaTemporal.containsKey(entryStoreNames.getValue())){
    			logger.debug("Tabla repetida:" + entryStoreNames.getValue());
    			if(listaTablasRepetidas.contains((String)entryStoreNames.getValue())){
    				logger.debug("La tabla " + (String)entryStoreNames.getValue() + " ya esta asignada, no agregarla de nuevo a la lista");
    			}
    			listaTablasRepetidas.add((String)entryStoreNames.getValue());
    		}
    		mapaTemporal.put((String)entryStoreNames.getValue(), (String)entryStoreNames.getValue());	
    	}
    	
    	//Llenar lista de sentencias para callback del store cuando los componentes usan el mismo store(si se da el caso)
    	for(String tablaRepetida: listaTablasRepetidas){
    		List<BaseObjectVO> listaValores = new ArrayList<BaseObjectVO>();
    		//iterar sobre los nombres de stores
    		Iterator itStores = storeNames.entrySet().iterator();
    		while (itStores.hasNext()) {
    			Map.Entry entryStores = (Map.Entry)itStores.next();
    			logger.debug("store id==>"+ entryStores.getKey());
	    		logger.debug("store tabla==>"+ entryStores.getValue());
    			if(entryStores.getValue().equals(tablaRepetida)){
    				logger.debug("agregar " + entryStores.getKey() + " - " + entryStores.getValue());
    				BaseObjectVO claveValorVO = new BaseObjectVO();
    				//Asignar id del componente:
    				claveValorVO.setLabel((String)entryStores.getKey());
    				//Obtener el Valor del componente a partir de su id, y asignarlo a claveValorVO
    				claveValorVO.setValue((String)comboLabelValue.get(entryStores.getKey()));
    				listaValores.add(claveValorVO);
    			}
    		}
    		if(!listaValores.isEmpty()){
    			mapaCallBack.put(tablaRepetida, listaValores);
    		}
    	}
    	
    	return mapaCallBack;
    }
    
    @SuppressWarnings("unchecked")
	protected Map<String, String> getStoresTables(String nuevaEtiqueta) throws Exception{
		String cadenaStore = null;
		String[] cadenaStoreSplit = null;
		String cadenaStoreClean = null;
		String[] cadenaNameSplit = null;
		String[] cadenaNameSplitId = null;
		String cadenaNameClean = null;
		boolean getStore = false;
		boolean getName = false;
		Map<String,String> storesNames = new HashMap<String, String>();
		//HashSet storesNames = new HashSet();
			
			String[] com = nuevaEtiqueta.split(",");
			logger.debug("Comienza obtención de lista de storesNames");

			
			for(int c=0; c<com.length; c++){
				cadenaStore = com[c];
				if(cadenaStore.contains("\"name\"")){
					//logger.debug("cadenaStore: "+cadenaStore);
					cadenaNameSplit = cadenaStore.split(":");
					if(cadenaNameSplit.length>=2){
						if(cadenaNameSplit[1].contains(".")){
							//logger.debug("cadenaStoreSplit0 : "+cadenaNameSplit[0]);
							//logger.debug("cadenaStoreSplit1 : "+cadenaNameSplit[1]);
							cadenaNameClean = cadenaNameSplit[1].replace("\"", "");
							//logger.debug("cadenaNameClean : "+cadenaNameClean);
							cadenaNameSplitId = cadenaNameClean.split("\\.");
							//logger.debug("cadenaNameSplitId length: "+cadenaNameSplitId.length);
							if(cadenaNameSplitId.length>=2){
								//logger.debug("cadenaNameSplitId1 : "+cadenaNameSplitId[0]);
								//logger.debug("cadenaNameSplitId1 : "+cadenaNameSplitId[1]);
								cadenaNameClean = cadenaNameSplitId[1];
								//logger.debug("cadenaNameClean : "+cadenaNameClean);
								getName = true;
							}
						}
					}
				}
				//Obtener el nombre de la tabla de BD a partir del nombre del store:
				if(cadenaStore.contains("\"store\"")){
					logger.debug("cadenaStore: "+cadenaStore);
					//
					String nombreStore = "";
					//quitar espacios en blanco
					nombreStore = StringUtils.deleteWhitespace(cadenaStore);
					//quitar todo lo anterior a ":"
					nombreStore = StringUtils.substringAfterLast(cadenaStore, ":");
					//quitarle la cadena "store"
					nombreStore = StringUtils.substringAfter(nombreStore, "store");
					//quitarle caracter "}" si viene
					nombreStore = StringUtils.remove(nombreStore, "}");
					//quitarle caracter "]" si viene
					nombreStore = StringUtils.remove(nombreStore, "]");
					cadenaStoreClean=nombreStore;
					logger.debug("cadenaStoreClean : "+cadenaStoreClean);
					getStore = true;
				}
				//logger.debug("getName - getStore: "+getName+"-"+getStore);
				if(getName && getStore){
					storesNames.put(cadenaNameClean, cadenaStoreClean);
					getStore = false;
					getName = false;
				}
		    }

        Iterator It = storesNames.entrySet().iterator();
        int n=0;
        while (It.hasNext()) {
        	logger.debug("Lista storesNames" + ++n +": "+It.next());
        }
		return storesNames;
    }
    
    @SuppressWarnings("unchecked")
	protected Map<String, String> getTextTables(String nuevaEtiqueta) throws Exception{
		String cadenaStore = null;
		String[] cadenaStoreSplit = null;
		String cadenaStoreClean = null;
		String[] cadenaNameSplit = null;
		String[] cadenaNameSplitId = null;
		String cadenaNameClean = null;
		boolean getStore = false;
		boolean getName = false;
		Map<String,String> storesNames = new HashMap<String, String>();
		//HashSet storesNames = new HashSet();
			
			String[] com = nuevaEtiqueta.split(",");
			logger.debug("com split ,: "+com);

			
			for(int c=0; c<com.length; c++){
				cadenaStore = com[c];
				if(cadenaStore.contains("\"name\"")){
					//logger.debug("cadenaStore: "+cadenaStore);
					cadenaNameSplit = cadenaStore.split(":");
					if(cadenaNameSplit.length>=2){
						if(cadenaNameSplit[1].contains(".")){
							//logger.debug("cadenaStoreSplit0 : "+cadenaNameSplit[0]);
							//logger.debug("cadenaStoreSplit1 : "+cadenaNameSplit[1]);
							cadenaNameClean = cadenaNameSplit[1].replace("\"", "");
							//logger.debug("cadenaNameClean : "+cadenaNameClean);
							cadenaNameSplitId = cadenaNameClean.split("\\.");
							//logger.debug("cadenaNameSplitId length: "+cadenaNameSplitId.length);
							if(cadenaNameSplitId.length>=2){
								//logger.debug("cadenaNameSplitId1 : "+cadenaNameSplitId[0]);
								//logger.debug("cadenaNameSplitId1 : "+cadenaNameSplitId[1]);
								cadenaNameClean = cadenaNameSplitId[1];
								//logger.debug("cadenaNameClean : "+cadenaNameClean);
								getName = true;
							}
						}
					}
				}
		    }

        Iterator It = storesNames.entrySet().iterator();
        int n=0;
        while (It.hasNext()) {
        	logger.debug("Lista storesNames" + ++n +": "+It.next());
        }
		return storesNames;
    }
    
    protected String getValorAtributo(String tabla, String clave) throws Exception{   
        if (logger.isDebugEnabled()) {
            logger.debug("---> getValorAtributo ");
            logger.debug("::::.. tabla : " + tabla);
            logger.debug("::::.. clave : " + clave);
        }
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        //tabla = "2DSMVADF";
        parameters.put("TABLA", tabla);
        parameters.put("CLAVE", clave);
        
        String atributos = endosoManager.getVariableAtributoCoberturas(tabla, clave);
        
        if (logger.isDebugEnabled()) {
            logger.debug(":::::.. atributos : " + atributos);
        }
        
        return atributos;
    }
    
    /**
     * Se guarda en la variable de session "PARAMS_DATOS_VARIABLES" el nombre en BD de las tablas de los stores y valores por default
     * @return
     */
    @SuppressWarnings("unchecked")
	protected void setNombresTablasStores(PantallaVO pantalla) throws Exception{
        
        /* *******************************************************************************
         * INICIA CARGA EN SESSION DE LOS NOMBRES DE LAS TABLAS DE LOS COMBOS CON VALORES POR DEFAULT
         * *******************************************************************************/
		
		/*
		 * Obtenemos todos los nombres de las tablas de los combos para cargar sus data store
		 */        		
        String pantallaInicialCot = StringEscapeUtils.unescapeHtml(pantalla.getDsArchivoSec());
        Map parametrosDatosVariables = new HashMap();
		Map<String,String> storesNames = new HashMap<String, String>();
		
		storesNames = getStoresTables(pantallaInicialCot);
        /*
         * Nos aseguramos que si existe PARAMS_DATOS_VARIABLES en session, lo asignamos al mapa parametrosDatosVariables
         */
		if(session.get("PARAMS_DATOS_VARIABLES")!= null){
        	parametrosDatosVariables = (HashMap)session.get("PARAMS_DATOS_VARIABLES");
        }
		/*
		 * Creamos lista con valores (0,0) por default para realizar carga inicial de los data store de los combos
		 */
        List params = new ArrayList();
        params.add("0");
        params.add("0");
        /*
         * Iteramos sobre el mapa storesNames que contiene en cada una de sus entradas el nombre de la tabla para los
         * Data Store de los combos 
         */
		Iterator varDataStore = storesNames.entrySet().iterator();
        while (varDataStore.hasNext()) {
        	Map.Entry nombreDataStoreActual = (Map.Entry)varDataStore.next();
            	parametrosDatosVariables.put(nombreDataStoreActual.getValue().toString(), params);
        }
        /*
         * Se carga en session el mapa que contiene el nombre de las tablas de los combos con valores por default
         */
        session.put("PARAMS_DATOS_VARIABLES", parametrosDatosVariables);
        logger.debug("@@PARAMS_DATOS_VARIABLES POR DEFAULT===>"+session.get("PARAMS_DATOS_VARIABLES"));
        /* *******************************************************************************
         * TERMINA CARGA EN SESSION DE LOS NOMBRES DE LAS TABLAS DE LOS COMBOS CON VALORES POR DEFAULT
         * *******************************************************************************/
    }

    /**
     * @param cotizacionManager the cotizacionManager to set
     */
    public void setCotizacionManager(CotizacionService cotizacionManager) {
        this.cotizacionManager = cotizacionManager;
    }

    /**
     * @return the tatriParamsList
     */
    public List<Map<String, TatriParametrosVO>> getTatriParamsList() {
        return tatriParamsList;
    }

    /**
     * @param tatriParamsList the tatriParamsList to set
     */
    public void setTatriParamsList(List<Map<String, TatriParametrosVO>> tatriParamsList) {
        this.tatriParamsList = tatriParamsList;
    }

    /**
     * 
     * @param kernelManager the kernelManager to set (method used by Spring) 
     */
	public void setKernelManager(KernelManager kernelManager) {
		this.kernelManager = kernelManager;
	}

	public String getLastDataCombos() throws Exception{
		
		return SUCCESS;
	}

	public void setEndosoManager(EndosoManager endosoManager) {
		this.endosoManager = endosoManager;
	}

	public String getFlujoCorrecto() {
		return flujoCorrecto;
	}

	public void setFlujoCorrecto(String flujoCorrecto) {
		this.flujoCorrecto = flujoCorrecto;
	}

	public void setCotizacionManagerJdbcTemplate(
			CotizacionPrincipalManager cotizacionManagerJdbcTemplate) {
		this.cotizacionManagerJdbcTemplate = cotizacionManagerJdbcTemplate;
	}
}
