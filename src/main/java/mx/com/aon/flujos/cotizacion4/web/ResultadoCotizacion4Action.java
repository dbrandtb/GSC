package mx.com.aon.flujos.cotizacion4.web;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.configurador.pantallas.model.components.GridVO;
import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.flujos.cotizacion.model.AyudaCoberturaCotizacionVO;
import mx.com.aon.flujos.cotizacion.model.CoberturaCotizacionVO;
import mx.com.aon.flujos.cotizacion.model.ResultadoCotizacionVO;
import mx.com.aon.flujos.cotizacion.service.impl.CotizacionManagerImpl;
import mx.com.aon.flujos.cotizacion.web.ResultadoCotizacionAction;
import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.portal.web.model.IncisoSaludVO;
import mx.com.gseguros.portal.cotizacion.model.DatosUsuario;
import mx.com.gseguros.portal.cotizacion.service.CotizacionManager;
import mx.com.gseguros.portal.general.service.CatalogosManager;
import mx.com.gseguros.portal.general.util.Rango;
import mx.com.gseguros.portal.general.util.TipoSituacion;
import mx.com.gseguros.portal.general.util.TipoTramite;
import mx.com.gseguros.portal.general.util.Validacion;
import mx.com.gseguros.utils.Constantes;
import mx.com.gseguros.utils.HttpUtil;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ClienteGeneral;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ClienteGeneralRespuesta;
import mx.com.gseguros.ws.ice2sigs.service.Ice2sigsService;
import net.sf.json.JSONArray;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionContext;

/**
 *
 * @author Jair
 */
public class ResultadoCotizacion4Action extends PrincipalCoreAction{
    
    /**
     * 
     */
    private static final long serialVersionUID = 468349054462501325L;

    private final static Logger logger= Logger.getLogger(ResultadoCotizacion4Action.class);
    
    //beans
    private KernelManagerSustituto kernelManagerSustituto;
    
    private CatalogosManager catalogosManager;
    
    private CotizacionManager cotizacionManager;
    
    private transient Ice2sigsService ice2sigsService;
    
    //Constantes de catalogos
    public static final String cdatribuSexo                         ="1";
    //                         fecha nacimiento                       2
    //                         codigoPostal                           3
    public static final String cdatribuEstado                       ="4";
    public static final String cdatribuDeducible                    ="5";
    public static final String cdatribuCopago                       ="6";
    public static final String cdatribuSumaAsegurada                ="7";
    public static final String cdatribuCirculoHospitalario          ="8";
    public static final String cdatribuCoberturaVacunas             ="9";
    public static final String cdatribuCoberturaPrevEnfAdultos      ="10";
    public static final String cdatribuMaternidad                   ="11";
    public static final String cdatribuSumaAseguradaMatenidad       ="12";
    public static final String cdatribuBaseTabuladorRembolso        ="13";
    public static final String cdatribuCostoEmergenciaExtranjero    ="14";
    public static final String cdatribuCobEliPenCamZona             ="15";
    public static final String cdatribuRol                          ="16";
    public static final String SK_COBERTURAS_COTIZACION             ="SK_COBERTURAS_COTIZACION";//llave de sesion
    public static final String cdatribuMunicipio                    ="17";
    
    //Datos de cotizacion
    private String ntramite;
    private String id;                                            //0
    //sexo (inciso)                                               1
    //fecha nacimiento (inciso)                                   2
    private String codigoPostal;                                //3
    private String estado;                                      //4
    //private String ciudad;                                    //4 X(
    private BigDecimal deducible;                               //5
    private String copago;                                      //6
    private String sumaSegurada;                                //7
    private String circuloHospitalario;                         //8
    private String coberturaVacunas;                            //9
    private String coberturaPrevencionEnfermedadesAdultos;      //10
    private String maternidad;                                  //11
    private String sumaAseguradaMaternidad;                     //12
    private String baseTabuladorReembolso;                      //13
    private String costoEmergenciaExtranjero;                   //14
    private String coberturaEliminacionPenalizacionCambioZona;  //15
    //rol (inciso)                                                16
    private String municipio;                                   //17
    private String fechaInicioVigencia;
    private String fechaFinVigencia;
    
    private List<IncisoSaludVO> incisos;
    private boolean success;
    private boolean exito;
    private GridVO gridResultados;
    private JSONArray dataResult = new JSONArray();
    private List<CoberturaCotizacionVO>listaCoberturas;
    private String jsonCober_unieco;
    private String jsonCober_estado;
    private String jsonCober_nmpoiza;
    private String jsonCober_cdplan;
    private String jsonCober_cdramo;
    private String jsonCober_cdcia;
    private String jsonCober_situa;
    private String respuesta;
    private String respuestaOculta;
    
    //utilitarios
    SimpleDateFormat renderFechas = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat renderHora = new SimpleDateFormat  ("HH:mm");
    Calendar calendarHoy=Calendar.getInstance();
    
    //para obtener la ayuda de cobertura
    private String idCobertura;
    private String idRamo;
    private String idCiaAseguradora;
    private AyudaCoberturaCotizacionVO ayudaCobertura;
    
    //para comprar cotizacion
    private String comprarNmpoliza;
    private String comprarCdplan;
    private String comprarCdperpag;
    private String comprarCdramo;
    private String comprarCdciaaguradora;
    private String comprarCdunieco;
    private Map<String,String> smap1;

    //para los hilos
    private int incisosProcesados;
    private int contador;
    private DatosUsuario datosUsuario;
    private String numeroPoliza;
    private IncisoSaludVO incisoActualIterado;
    long t;
    private UserVO usuario;
    private boolean errorHilos=false;
    private Exception exceptionHilo;
    
    private String cdunieco;
    private String cdramo;
    private String cdtipsit;
    private List<Map<String,String>>slist1;
    
    private String edadMaximaCotizacion;
    
    public String entrar()
    {
        UserVO usuario=(UserVO) session.get("USUARIO");
        if(ntramite==null)
        //cuando no hay ntramite es porque esta cotizando un agente por fuera,
        //y se obtiene cdunieco cdramo y cdtipsit por medio de ese agente
        {
        	try
        	{
        		DatosUsuario datUsu=kernelManagerSustituto.obtenerDatosUsuario(usuario.getUser(),cdtipsit);//cdunieco,cdramo
        		cdunieco=datUsu.getCdunieco();
        		cdramo=datUsu.getCdramo();
        		//cdtipsit=datUsu.getCdtipsit();//ahora viene en la URL
        	}
        	catch(Exception ex)
        	{
        		logger.error("error al obtener los datos del agente",ex);
        	}
        }
        logger.debug("### usuario name: "+usuario.getName());
        logger.debug("### usuario user: "+usuario.getUser());
        logger.debug("### usuario empresa cdelemento id: "+usuario.getEmpresa().getElementoId());
        logger.debug("### usuario codigopersona: "+usuario.getCodigoPersona());
        logger.debug("### ntramite: "+ntramite);
        logger.debug("### cdunieco: "+cdunieco);
        logger.debug("### cdramo: "+cdramo);
        logger.debug("### cdtipsit: "+cdtipsit);
        if(cdtipsit.equalsIgnoreCase("GB")) {
        	return "gb";
        }
        
        //Obtenemos la edad m�xima para la cotizacion:
        try {
        	edadMaximaCotizacion = catalogosManager.obtieneCantidadMaxima(cdramo, cdtipsit, TipoTramite.POLIZA_NUEVA, Rango.ANIOS, Validacion.EDAD_MAX_COTIZACION);
        } catch(Exception e) {
        	logger.error("Error al obtener la edad m�xima de cotizaci�n", e);
        	edadMaximaCotizacion = "0";
        }
        return SUCCESS;
    }
    
    public String cotizar()
    {
    	long t=System.currentTimeMillis();
    	logger.debug(""
    			+ "\n######$ Tiempo de inicio: "+t);
        try
        {
            /////////////////////////////////////////////
            ////// Obtener el usuario de la sesion //////
            /////////////////////////////////////////////
            this.session=ActionContext.getContext().getSession();//porque se uso SMD en struts.xml y eso pierde la sesion
            UserVO usuario=(UserVO) session.get("USUARIO");
            logger.debug("### usuario name: "+usuario.getName());
            logger.debug("### usuario user: "+usuario.getUser());
            logger.debug("### usuario empresa cdelemento id: "+usuario.getEmpresa().getElementoId());
            logger.debug("### usuario codigopersona: "+usuario.getCodigoPersona());
            logger.debug("### cdunieco: "+cdunieco);
            logger.debug("### cdramo: "+cdramo);
            logger.debug("### cdtipsit: "+cdtipsit);
            /////////////////////////////////////////////
            
            ///////////////////////////////////////////////////
            ////// Calcular un anio a partir de la fecha //////
            /*///////////////////////////////////////////////*
            Calendar fechaEnUnAnio=Calendar.getInstance();
            fechaEnUnAnio.add(Calendar.YEAR, 1);
            logger.debug("### fecha "+renderFechas.format(calendarHoy.getTime()));
            logger.debug("### fecha en un anio: "+renderFechas.format(fechaEnUnAnio.getTime()));*/
            /*///////////////////////////////////////////////*/
            ////// Calcular un anio a partir de la fecha //////
            ///////////////////////////////////////////////////
            
            ///////////////////////////////////////////////////
            ////// obtener datos del usuario //////////////////
            /*///////////////////////////////////////////////*/
            long t1=System.currentTimeMillis();
            //logger.debug("######$ antes de pedir datos usuario: "+t1);
            long t2=System.currentTimeMillis();
            /*logger.debug("######$ despues de pedir datos usuario: "+t2);
            logger.debug("######$ tiempo pidiendo datos usuario: "+(t2-t1));
            logger.debug("######$ tiempo total: "+(t2-t));*/
            /*///////////////////////////////////////////////*/
            ////// obtener datos del usuario //////////////////
            ///////////////////////////////////////////////////
            
            ///////////////////////////////////////////
            ////// ini Crear un numero de poliza //////
            ///////////////////////////////////////////
            String numeroPoliza;
            if(id!=null&&id.length()>0)
            {
                numeroPoliza=id;
            }
            else
            {
            	t1=System.currentTimeMillis();
            	logger.debug("######$ antes de pedir numero de poliza: "+t1);
            	
            	////// se quita porque ahora viene del formulario
                //WrapperResultados wrapperNumeroPoliza=kernelManagerSustituto.calculaNumeroPoliza(datosUsuario.getCdunieco(),datosUsuario.getCdramo(),"W");
            	
            	////// se pone desde el formulario
            	WrapperResultados wrapperNumeroPoliza=kernelManagerSustituto.calculaNumeroPoliza(cdunieco,cdramo,"W");
            	
                numeroPoliza=(String) wrapperNumeroPoliza.getItemMap().get("NUMERO_POLIZA");
                t2=System.currentTimeMillis();
            	logger.debug("######$ despues de pedir numero de poliza: "+t2);
            	logger.debug("######$ tiempo pidiendo numero de poliza: "+(t2-t1));
            	logger.debug("######$ tiempo total: "+(t2-t));
            }
            ///////////////////////////////////////////
            ////// fin Crear un numero de poliza //////
            ///////////////////////////////////////////
            
            ///////////////////////////////////////////////////////
            ////// ini Guardar el maestro de poliza nmpoliza //////
            ///////////////////////////////////////////////////////
            Map<String,String>mapa=new HashMap<String,String>(0);
            //mapa.put("pv_cdunieco",     datosUsuario.getCdunieco());//se kita por cdunieco de formulario
            //mapa.put("pv_cdramo",       datosUsuario.getCdramo());//se kita por cdramo de formulario
            mapa.put("pv_cdunieco",     cdunieco);
            mapa.put("pv_cdramo",       cdramo);
            
            mapa.put("pv_estado",       "W");
            mapa.put("pv_nmpoliza",     numeroPoliza);
            mapa.put("pv_nmsuplem",     "0");
            mapa.put("pv_status",       "V");
            mapa.put("pv_swestado",     "0");
            mapa.put("pv_nmsolici",     null);
            mapa.put("pv_feautori",     null);
            mapa.put("pv_cdmotanu",     null);
            mapa.put("pv_feanulac",     null);
            mapa.put("pv_swautori",     "N");
            mapa.put("pv_cdmoneda",     "001");
            mapa.put("pv_feinisus",     null);
            mapa.put("pv_fefinsus",     null);
            mapa.put("pv_ottempot",     "R");
            mapa.put("pv_feefecto",     fechaInicioVigencia);//renderFechas.format(calendarHoy.getTime()));
            mapa.put("pv_hhefecto",     "12:00");
            mapa.put("pv_feproren",     fechaFinVigencia);//renderFechas.format(fechaEnUnAnio.getTime()));
            mapa.put("pv_fevencim",     null);
            mapa.put("pv_nmrenova",     "0");
            mapa.put("pv_ferecibo",     null);
            mapa.put("pv_feultsin",     null);
            mapa.put("pv_nmnumsin",     "0");
            mapa.put("pv_cdtipcoa",     "N");
            mapa.put("pv_swtarifi",     "A");
            mapa.put("pv_swabrido",     null);
            mapa.put("pv_feemisio",     renderFechas.format(calendarHoy.getTime()));
            mapa.put("pv_cdperpag",     "12");
            mapa.put("pv_nmpoliex",     null);
            mapa.put("pv_nmcuadro",     "P1");
            mapa.put("pv_porredau",     "100");
            mapa.put("pv_swconsol",     "S");
            mapa.put("pv_nmpolant",     null);
            mapa.put("pv_nmpolnva",     null);
            mapa.put("pv_fesolici",     renderFechas.format(calendarHoy.getTime()));
            mapa.put("pv_cdramant",     null);
            mapa.put("pv_cdmejred",     null);
            mapa.put("pv_nmpoldoc",     null);
            mapa.put("pv_nmpoliza2",    null);
            mapa.put("pv_nmrenove",     null);
            mapa.put("pv_nmsuplee",     null);
            mapa.put("pv_ttipcamc",     null);
            mapa.put("pv_ttipcamv",     null);
            mapa.put("pv_swpatent",     null);
            mapa.put("pv_pcpgocte",     "100");
            mapa.put("pv_accion",       "U");
            logger.debug("### Invocacion de insercion de maestro de poliza map: "+mapa);
            t1=System.currentTimeMillis();
            logger.debug("######$ tiempo antes de insertar maestro poliza "+t1);
            WrapperResultados wr=kernelManagerSustituto.insertaMaestroPolizas(mapa);
            t2=System.currentTimeMillis();
            logger.debug("######$ tiempo despues de insertar maestro poliza "+t2);
            logger.debug("######$ tiempo consumido en insertar maestro poliza "+(t2-t1));
            logger.debug("######$ tiempo total consumido "+(t2-t));
            logger.debug("### response id "+wr.getMsgId());
            logger.debug("### response text "+wr.getMsgText());
            ///////////////////////////////////////////////////////
            ////// fin Guardar el maestro de poliza nmpoliza //////
            ///////////////////////////////////////////////////////
            
            if(cdtipsit.equalsIgnoreCase("SL")||cdtipsit.equalsIgnoreCase("SN"))
            {
	            ////////////////////////////////////////
	            ////// ordenar primero al titular //////
	            /*////////////////////////////////////*/
	            int indiceTitular=0;
	            for(int i=0;i<incisos.size();i++)
	            {
	            	if(incisos.get(i).getRol().getKey().equals("T"))
	            	{
	            		indiceTitular=i;
	            	}
	            }
	            List<IncisoSaludVO>listaOrdenada=new ArrayList<IncisoSaludVO>(0);
	            IncisoSaludVO titular=incisos.get(indiceTitular);
	            listaOrdenada.add(titular);
	            incisos.remove(indiceTitular);
	            listaOrdenada.addAll(incisos);
	            incisos=listaOrdenada;
	            /*////////////////////////////////////*/
				////// ordenar primero al titular //////
	            ////////////////////////////////////////
	            
	            ///////////////////////////////////////////////////////////////////////////////
	            ////// ini Guardar las situaciones (mpolisit y un mvalosit por cada una) //////
	            ///////////////////////////////////////////////////////////////////////////////
	            int contador=1;
	            for(IncisoSaludVO i : incisos)
	            {
	                logger.debug("### Iteracion de polisit y valosit #"+contador);
	                
	                //////////////////////////////////
	                ////// ini mpolisit iterado //////
	                //////////////////////////////////
	                Map<String,Object>mapaPolisitIterado=new HashMap<String,Object>(0);
	                //mapaPolisitIterado.put("pv_cdunieco_i",    datosUsuario.getCdunieco());
	                //mapaPolisitIterado.put("pv_cdramo_i",      datosUsuario.getCdramo());
	                mapaPolisitIterado.put("pv_cdunieco_i",    cdunieco);//se agrega desde el formulario
	                mapaPolisitIterado.put("pv_cdramo_i",      cdramo);//se agrega desde el formulario
	                mapaPolisitIterado.put("pv_estado_i",      "W");
	                mapaPolisitIterado.put("pv_nmpoliza_i",    numeroPoliza);
	                mapaPolisitIterado.put("pv_nmsituac_i",    contador+"");
	                mapaPolisitIterado.put("pv_nmsuplem_i",    "0");
	                mapaPolisitIterado.put("pv_status_i",      "V");
	                //mapaPolisitIterado.put("pv_cdtipsit_i",    datosUsuario.getCdtipsit());
	                mapaPolisitIterado.put("pv_cdtipsit_i",    cdtipsit);
	                mapaPolisitIterado.put("pv_swreduci_i",    null);
	                mapaPolisitIterado.put("pv_cdagrupa_i",    "1");
	                mapaPolisitIterado.put("pv_cdestado_i",    "0");
	                mapaPolisitIterado.put("pv_fefecsit_i",    renderFechas.parse(fechaInicioVigencia));
	                mapaPolisitIterado.put("pv_fecharef_i",    renderFechas.parse(fechaInicioVigencia));
	                mapaPolisitIterado.put("pv_cdgrupo_i",     null);
	                mapaPolisitIterado.put("pv_nmsituaext_i",  null);
	                mapaPolisitIterado.put("pv_nmsitaux_i",    null);
	                mapaPolisitIterado.put("pv_nmsbsitext_i",  null);
	                mapaPolisitIterado.put("pv_cdplan_i",      "1");
	                mapaPolisitIterado.put("pv_cdasegur_i",    "30");
	                mapaPolisitIterado.put("pv_accion_i",      "I");
	                t1=System.currentTimeMillis();
	                logger.debug("######$ tiempo antes de insertar polisit iterado "+t1);
	                kernelManagerSustituto.insertaPolisit(mapaPolisitIterado);
	                t2=System.currentTimeMillis();
	                logger.debug("######$ tiempo despues de insertar polisit iterado "+t2);
	                logger.debug("######$ tiempo consumido en insertar polisit iterado "+(t2-t1));
	                logger.debug("######$ tiempo total consumido "+(t2-t));
	                //////////////////////////////////
	                ////// fin mpolisit iterado //////
	                //////////////////////////////////
	                
	                //////////////////////////////////
	                ////// ini mvalosit iterado //////
	                //////////////////////////////////
	                Map<String,String>mapaValositIterado=new HashMap<String,String>(0);
	                //mapaValositIterado.put("pv_cdunieco",    datosUsuario.getCdunieco());
	                //mapaValositIterado.put("pv_cdramo",      datosUsuario.getCdramo());
	                mapaValositIterado.put("pv_cdunieco",    cdunieco);//se agrega desde el formulario
	                mapaValositIterado.put("pv_cdramo",      cdramo);//se agrega desde el formulario
	                mapaValositIterado.put("pv_estado",      "W");
	                mapaValositIterado.put("pv_nmpoliza",    numeroPoliza);
	                mapaValositIterado.put("pv_nmsituac",    contador+"");
	                mapaValositIterado.put("pv_nmsuplem",    "0");
	                mapaValositIterado.put("pv_status",      "V");
	                //mapaValositIterado.put("pv_cdtipsit",    datosUsuario.getCdtipsit());
	                mapaValositIterado.put("pv_cdtipsit",    cdtipsit);
	                mapaValositIterado.put("pv_otvalor01",   i.getSexo().getKey());//sexo
	                mapaValositIterado.put("pv_otvalor02",   renderFechas.format(i.getFechaNacimiento()));//f nacimiento
	                mapaValositIterado.put("pv_otvalor03",   codigoPostal);//codigoPostal
	                mapaValositIterado.put("pv_otvalor04",   estado);//estado
	                mapaValositIterado.put("pv_otvalor05",   deducible.toString());//deducible
	                mapaValositIterado.put("pv_otvalor06",   copago);//copago
	                mapaValositIterado.put("pv_otvalor07",   sumaSegurada);//suma asegurada
	                mapaValositIterado.put("pv_otvalor08",   circuloHospitalario);//circulo hospitalario
	                mapaValositIterado.put("pv_otvalor09",   coberturaVacunas);//cobetura vacunas
	                mapaValositIterado.put("pv_otvalor10",   coberturaPrevencionEnfermedadesAdultos);//cob prev enf adultos
	                mapaValositIterado.put("pv_otvalor11",   maternidad);//maternidad
	                mapaValositIterado.put("pv_otvalor12",   sumaAseguradaMaternidad);//suma aeguarada maternidad
	                mapaValositIterado.put("pv_otvalor13",   baseTabuladorReembolso);//base tabulador reembolso
	                mapaValositIterado.put("pv_otvalor14",   costoEmergenciaExtranjero);//emergencia extranjero
	                mapaValositIterado.put("pv_otvalor15",   coberturaEliminacionPenalizacionCambioZona);//cob elim pen cambio zona
	                mapaValositIterado.put("pv_otvalor16",   i.getRol().getKey());//parentesco
	                mapaValositIterado.put("pv_otvalor17",   municipio);
	                mapaValositIterado.put("pv_otvalor18",   null);
	                mapaValositIterado.put("pv_otvalor19",   null);
	                mapaValositIterado.put("pv_otvalor20",   null);
	                mapaValositIterado.put("pv_otvalor21",   null);
	                mapaValositIterado.put("pv_otvalor22",   null);
	                mapaValositIterado.put("pv_otvalor23",   null);
	                mapaValositIterado.put("pv_otvalor24",   null);
	                mapaValositIterado.put("pv_otvalor25",   null);
	                mapaValositIterado.put("pv_otvalor26",   null);
	                mapaValositIterado.put("pv_otvalor27",   null);
	                mapaValositIterado.put("pv_otvalor28",   null);
	                mapaValositIterado.put("pv_otvalor29",   null);
	                mapaValositIterado.put("pv_otvalor30",   null);
	                mapaValositIterado.put("pv_otvalor31",   null);
	                mapaValositIterado.put("pv_otvalor32",   null);
	                mapaValositIterado.put("pv_otvalor33",   null);
	                mapaValositIterado.put("pv_otvalor34",   null);
	                mapaValositIterado.put("pv_otvalor35",   null);
	                mapaValositIterado.put("pv_otvalor36",   null);
	                mapaValositIterado.put("pv_otvalor37",   null);
	                mapaValositIterado.put("pv_otvalor38",   null);
	                mapaValositIterado.put("pv_otvalor39",   null);
	                mapaValositIterado.put("pv_otvalor40",   null);
	                mapaValositIterado.put("pv_otvalor41",   null);
	                mapaValositIterado.put("pv_otvalor42",   null);
	                mapaValositIterado.put("pv_otvalor43",   null);
	                mapaValositIterado.put("pv_otvalor44",   null);
	                mapaValositIterado.put("pv_otvalor45",   null);
	                mapaValositIterado.put("pv_otvalor46",   null);
	                mapaValositIterado.put("pv_otvalor47",   null);
	                mapaValositIterado.put("pv_otvalor48",   null);
	                mapaValositIterado.put("pv_otvalor49",   null);
	                mapaValositIterado.put("pv_otvalor50",   null);
	                mapaValositIterado.put("pv_accion_i",   "I");
	                t1=System.currentTimeMillis();
	                logger.debug("######$ tiempo antes de insertar valosit iterado "+t1);
	                kernelManagerSustituto.insertaValoresSituaciones(mapaValositIterado);
	                t2=System.currentTimeMillis();
	                logger.debug("######$ tiempo despues de insertar valosit iterado "+t2);
	                logger.debug("######$ tiempo consumido en insertar valosit iterado "+(t2-t1));
	                logger.debug("######$ tiempo total consumido "+(t2-t));
	                //////////////////////////////////
	                ////// fin mvalosit iterado //////
	                //////////////////////////////////
	                
	                logger.debug("### Fin de iteracion de polisit y valosit #"+contador);
	                contador++;
	            }
	            ///////////////////////////////////////////////////////////////////////////////
	            ////// fin Guardar las situaciones (mpolisit y un mvalosit por cada una) //////
	            ///////////////////////////////////////////////////////////////////////////////
	            
	            /////////////////////////////////
	            ////// ini clonar personas //////
	            /////////////////////////////////
	            contador=1;
	            for(IncisoSaludVO i : incisos)
	            {
	                logger.debug("### Iteracion de clonar personas #"+contador);
	                Map<String,Object> mapaClonPersonaIterado=new HashMap<String,Object>(0);
	                mapaClonPersonaIterado.put("pv_cdelemen_i",     usuario.getEmpresa().getElementoId());
	                //mapaClonPersonaIterado.put("pv_cdunieco_i",     datosUsuario.getCdunieco());
	                //mapaClonPersonaIterado.put("pv_cdramo_i",       datosUsuario.getCdramo());
	                mapaClonPersonaIterado.put("pv_cdunieco_i",     cdunieco);//se agrega desde el formulario
	                mapaClonPersonaIterado.put("pv_cdramo_i",       cdramo);//se agrega desde el formulario
	                mapaClonPersonaIterado.put("pv_estado_i",       "W");
	                mapaClonPersonaIterado.put("pv_nmpoliza_i",     numeroPoliza);
	                mapaClonPersonaIterado.put("pv_nmsituac_i",     contador+"");
	                //mapaClonPersonaIterado.put("pv_cdtipsit_i",     datosUsuario.getCdtipsit());
	                mapaClonPersonaIterado.put("pv_cdtipsit_i",     cdtipsit);
	                mapaClonPersonaIterado.put("pv_fecha_i",        calendarHoy.getTime());
	                mapaClonPersonaIterado.put("pv_cdusuario_i",    usuario.getUser());
	                mapaClonPersonaIterado.put("pv_p_nombre",       i.getNombre());
	                mapaClonPersonaIterado.put("pv_s_nombre",       i.getSegundoNombre());
	                mapaClonPersonaIterado.put("pv_apellidop",      i.getApellidoPaterno());
	                mapaClonPersonaIterado.put("pv_apellidom",      i.getApellidoMaterno());
	                mapaClonPersonaIterado.put("pv_sexo",           i.getSexo().getKey());
	                mapaClonPersonaIterado.put("pv_fenacimi",       i.getFechaNacimiento());
	                mapaClonPersonaIterado.put("pv_parentesco",     i.getRol().getKey());
	                t1=System.currentTimeMillis();
	                logger.debug("######$ tiempo antes de clonar personas iterado "+t1);
	                kernelManagerSustituto.clonaPersonas(mapaClonPersonaIterado);
	                t2=System.currentTimeMillis();
	                logger.debug("######$ tiempo despues de clonar personas iterado "+t2);
	                logger.debug("######$ tiempo consumido en clonar personas iterado "+(t2-t1));
	                logger.debug("######$ tiempo total consumido "+(t2-t));
	                contador++;
	            }
	            /////////////////////////////////
	            ////// fin clonar personas //////
	            /////////////////////////////////
            }
            else if(cdtipsit.equalsIgnoreCase("GB"))
            {
            	////////////////////////////////////////
	            ////// ordenar primero al titular //////
	            /*////////////////////////////////////*/
	            int indiceTitular=0;
	            for(int i=0;i<slist1.size();i++)
	            {
	            	if(slist1.get(i).get("rol").equals("T"))
	            	{
	            		indiceTitular=i;
	            	}
	            }
	            List<Map<String,String>>listaOrdenada=new ArrayList<Map<String,String>>(0);
	            Map<String,String> titular=slist1.get(indiceTitular);
	            listaOrdenada.add(titular);
	            slist1.remove(indiceTitular);
	            listaOrdenada.addAll(slist1);
	            slist1=listaOrdenada;
	            /*////////////////////////////////////*/
				////// ordenar primero al titular //////
	            ////////////////////////////////////////
	            
	            ///////////////////////////////////////////////////////////////////////////////
	            ////// ini Guardar las situaciones (mpolisit y un mvalosit por cada una) //////
	            ///////////////////////////////////////////////////////////////////////////////
	            int contador=1;
	            for(Map<String,String> i : slist1)
	            {
	                logger.debug("### Iteracion de polisit y valosit #"+contador);
	                
	                //////////////////////////////////
	                ////// ini mpolisit iterado //////
	                //////////////////////////////////
	                Map<String,Object>mapaPolisitIterado=new HashMap<String,Object>(0);
	                //mapaPolisitIterado.put("pv_cdunieco_i",    datosUsuario.getCdunieco());
	                //mapaPolisitIterado.put("pv_cdramo_i",      datosUsuario.getCdramo());
	                mapaPolisitIterado.put("pv_cdunieco_i",    cdunieco);//se agrega desde el formulario
	                mapaPolisitIterado.put("pv_cdramo_i",      cdramo);//se agrega desde el formulario
	                mapaPolisitIterado.put("pv_estado_i",      "W");
	                mapaPolisitIterado.put("pv_nmpoliza_i",    numeroPoliza);
	                mapaPolisitIterado.put("pv_nmsituac_i",    contador+"");
	                mapaPolisitIterado.put("pv_nmsuplem_i",    "0");
	                mapaPolisitIterado.put("pv_status_i",      "V");
	                //mapaPolisitIterado.put("pv_cdtipsit_i",    datosUsuario.getCdtipsit());
	                mapaPolisitIterado.put("pv_cdtipsit_i",    cdtipsit);
	                mapaPolisitIterado.put("pv_swreduci_i",    null);
	                mapaPolisitIterado.put("pv_cdagrupa_i",    "1");
	                mapaPolisitIterado.put("pv_cdestado_i",    "0");
	                mapaPolisitIterado.put("pv_fefecsit_i",    renderFechas.parse(fechaInicioVigencia));
	                mapaPolisitIterado.put("pv_fecharef_i",    renderFechas.parse(fechaInicioVigencia));
	                mapaPolisitIterado.put("pv_cdgrupo_i",     null);
	                mapaPolisitIterado.put("pv_nmsituaext_i",  null);
	                mapaPolisitIterado.put("pv_nmsitaux_i",    null);
	                mapaPolisitIterado.put("pv_nmsbsitext_i",  null);
	                mapaPolisitIterado.put("pv_cdplan_i",      "1");
	                mapaPolisitIterado.put("pv_cdasegur_i",    "30");
	                mapaPolisitIterado.put("pv_accion_i",      "I");
	                t1=System.currentTimeMillis();
	                logger.debug("######$ tiempo antes de insertar polisit iterado "+t1);
	                kernelManagerSustituto.insertaPolisit(mapaPolisitIterado);
	                t2=System.currentTimeMillis();
	                logger.debug("######$ tiempo despues de insertar polisit iterado "+t2);
	                logger.debug("######$ tiempo consumido en insertar polisit iterado "+(t2-t1));
	                logger.debug("######$ tiempo total consumido "+(t2-t));
	                //////////////////////////////////
	                ////// fin mpolisit iterado //////
	                //////////////////////////////////
	                
	                //////////////////////////////////
	                ////// ini mvalosit iterado //////
	                //////////////////////////////////
	                Map<String,String>mapaValositIterado=new HashMap<String,String>(0);
	                //mapaValositIterado.put("pv_cdunieco",    datosUsuario.getCdunieco());
	                //mapaValositIterado.put("pv_cdramo",      datosUsuario.getCdramo());
	                mapaValositIterado.put("pv_cdunieco",    cdunieco);//se agrega desde el formulario
	                mapaValositIterado.put("pv_cdramo",      cdramo);//se agrega desde el formulario
	                mapaValositIterado.put("pv_estado",      "W");
	                mapaValositIterado.put("pv_nmpoliza",    numeroPoliza);
	                mapaValositIterado.put("pv_nmsituac",    contador+"");
	                mapaValositIterado.put("pv_nmsuplem",    "0");
	                mapaValositIterado.put("pv_status",      "V");
	                //mapaValositIterado.put("pv_cdtipsit",    datosUsuario.getCdtipsit());
	                mapaValositIterado.put("pv_cdtipsit",    cdtipsit);
	                mapaValositIterado.put("pv_otvalor01",   i.get("rol"));//sexo
	                mapaValositIterado.put("pv_otvalor02",   i.get("fechaNacimiento"));//f nacimiento
	                mapaValositIterado.put("pv_otvalor03",   estado);//codigoPostal
	                mapaValositIterado.put("pv_otvalor04",   deducible.toString());//estado
	                mapaValositIterado.put("pv_otvalor05",   coberturaVacunas);//deducible
	                mapaValositIterado.put("pv_otvalor06",   coberturaPrevencionEnfermedadesAdultos);//copago
	                mapaValositIterado.put("pv_otvalor07",   costoEmergenciaExtranjero);//suma asegurada
	                mapaValositIterado.put("pv_otvalor08",   null);//circulo hospitalario
	                mapaValositIterado.put("pv_otvalor09",   null);//cobetura vacunas
	                mapaValositIterado.put("pv_otvalor10",   null);//cob prev enf adultos
	                mapaValositIterado.put("pv_otvalor11",   null);//maternidad
	                mapaValositIterado.put("pv_otvalor12",   null);//suma aeguarada maternidad
	                mapaValositIterado.put("pv_otvalor13",   null);//base tabulador reembolso
	                mapaValositIterado.put("pv_otvalor14",   null);//emergencia extranjero
	                mapaValositIterado.put("pv_otvalor15",   null);//cob elim pen cambio zona
	                mapaValositIterado.put("pv_otvalor16",   i.get("rol"));//parentesco
	                mapaValositIterado.put("pv_otvalor17",   null);
	                mapaValositIterado.put("pv_otvalor18",   null);
	                mapaValositIterado.put("pv_otvalor19",   null);
	                mapaValositIterado.put("pv_otvalor20",   null);
	                mapaValositIterado.put("pv_otvalor21",   null);
	                mapaValositIterado.put("pv_otvalor22",   null);
	                mapaValositIterado.put("pv_otvalor23",   null);
	                mapaValositIterado.put("pv_otvalor24",   null);
	                mapaValositIterado.put("pv_otvalor25",   null);
	                mapaValositIterado.put("pv_otvalor26",   null);
	                mapaValositIterado.put("pv_otvalor27",   null);
	                mapaValositIterado.put("pv_otvalor28",   null);
	                mapaValositIterado.put("pv_otvalor29",   null);
	                mapaValositIterado.put("pv_otvalor30",   null);
	                mapaValositIterado.put("pv_otvalor31",   null);
	                mapaValositIterado.put("pv_otvalor32",   null);
	                mapaValositIterado.put("pv_otvalor33",   null);
	                mapaValositIterado.put("pv_otvalor34",   null);
	                mapaValositIterado.put("pv_otvalor35",   null);
	                mapaValositIterado.put("pv_otvalor36",   null);
	                mapaValositIterado.put("pv_otvalor37",   null);
	                mapaValositIterado.put("pv_otvalor38",   null);
	                mapaValositIterado.put("pv_otvalor39",   null);
	                mapaValositIterado.put("pv_otvalor40",   null);
	                mapaValositIterado.put("pv_otvalor41",   null);
	                mapaValositIterado.put("pv_otvalor42",   null);
	                mapaValositIterado.put("pv_otvalor43",   null);
	                mapaValositIterado.put("pv_otvalor44",   null);
	                mapaValositIterado.put("pv_otvalor45",   null);
	                mapaValositIterado.put("pv_otvalor46",   null);
	                mapaValositIterado.put("pv_otvalor47",   null);
	                mapaValositIterado.put("pv_otvalor48",   null);
	                mapaValositIterado.put("pv_otvalor49",   null);
	                mapaValositIterado.put("pv_otvalor50",   null);
	                mapaValositIterado.put("pv_accion_i",   "I");
	                t1=System.currentTimeMillis();
	                logger.debug("######$ tiempo antes de insertar valosit iterado "+t1);
	                kernelManagerSustituto.insertaValoresSituaciones(mapaValositIterado);
	                t2=System.currentTimeMillis();
	                logger.debug("######$ tiempo despues de insertar valosit iterado "+t2);
	                logger.debug("######$ tiempo consumido en insertar valosit iterado "+(t2-t1));
	                logger.debug("######$ tiempo total consumido "+(t2-t));
	                //////////////////////////////////
	                ////// fin mvalosit iterado //////
	                //////////////////////////////////
	                
	                logger.debug("### Fin de iteracion de polisit y valosit #"+contador);
	                contador++;
	            }
	            ///////////////////////////////////////////////////////////////////////////////
	            ////// fin Guardar las situaciones (mpolisit y un mvalosit por cada una) //////
	            ///////////////////////////////////////////////////////////////////////////////
	            
	            /////////////////////////////////
	            ////// ini clonar personas //////
	            /////////////////////////////////
	            contador=1;
	            for(Map<String,String> i : slist1)
	            {
	                logger.debug("### Iteracion de clonar personas #"+contador);
	                Map<String,Object> mapaClonPersonaIterado=new HashMap<String,Object>(0);
	                mapaClonPersonaIterado.put("pv_cdelemen_i",     usuario.getEmpresa().getElementoId());
	                //mapaClonPersonaIterado.put("pv_cdunieco_i",     datosUsuario.getCdunieco());
	                //mapaClonPersonaIterado.put("pv_cdramo_i",       datosUsuario.getCdramo());
	                mapaClonPersonaIterado.put("pv_cdunieco_i",     cdunieco);//se agrega desde el formulario
	                mapaClonPersonaIterado.put("pv_cdramo_i",       cdramo);//se agrega desde el formulario
	                mapaClonPersonaIterado.put("pv_estado_i",       "W");
	                mapaClonPersonaIterado.put("pv_nmpoliza_i",     numeroPoliza);
	                mapaClonPersonaIterado.put("pv_nmsituac_i",     contador+"");
	                //mapaClonPersonaIterado.put("pv_cdtipsit_i",     datosUsuario.getCdtipsit());
	                mapaClonPersonaIterado.put("pv_cdtipsit_i",     cdtipsit);
	                mapaClonPersonaIterado.put("pv_fecha_i",        calendarHoy.getTime());
	                mapaClonPersonaIterado.put("pv_cdusuario_i",    usuario.getUser());
	                mapaClonPersonaIterado.put("pv_p_nombre",       i.get("nombre"));
	                mapaClonPersonaIterado.put("pv_s_nombre",       i.get("segundoNombre"));
	                mapaClonPersonaIterado.put("pv_apellidop",      i.get("apellidoPaterno"));
	                mapaClonPersonaIterado.put("pv_apellidom",      i.get("apellidoMaterno"));
	                mapaClonPersonaIterado.put("pv_sexo",           "H");
	                mapaClonPersonaIterado.put("pv_fenacimi",       calendarHoy.getTime());
	                mapaClonPersonaIterado.put("pv_parentesco",     i.get("rol"));
	                t1=System.currentTimeMillis();
	                logger.debug("######$ tiempo antes de clonar personas iterado "+t1);
	                kernelManagerSustituto.clonaPersonas(mapaClonPersonaIterado);
	                t2=System.currentTimeMillis();
	                logger.debug("######$ tiempo despues de clonar personas iterado "+t2);
	                logger.debug("######$ tiempo consumido en clonar personas iterado "+(t2-t1));
	                logger.debug("######$ tiempo total consumido "+(t2-t));
	                contador++;
	            }
	            /////////////////////////////////
	            ////// fin clonar personas //////
	            /////////////////////////////////
            }
            
            ////////////////////////
            ////// coberturas //////
            /*////////////////////*/
            Map<String,String> mapCoberturas=new HashMap<String,String>(0);
            //mapCoberturas.put("pv_cdunieco_i",   datosUsuario.getCdunieco());
            //mapCoberturas.put("pv_cdramo_i",     datosUsuario.getCdramo());
            mapCoberturas.put("pv_cdunieco_i",   cdunieco);//se agrega desde el formulario
            mapCoberturas.put("pv_cdramo_i",     cdramo);//se agrega desde el formulario
            mapCoberturas.put("pv_estado_i",     "W");
            mapCoberturas.put("pv_nmpoliza_i",   numeroPoliza);
            mapCoberturas.put("pv_nmsituac_i",   "0");
            mapCoberturas.put("pv_nmsuplem_i",   "0");//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
            mapCoberturas.put("pv_cdgarant_i",   "TODO");//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
            mapCoberturas.put("pv_cdtipsup_i",   "1");
            t1=System.currentTimeMillis();
            logger.debug("######$ tiempo antes de coberturas "+t1);
            kernelManagerSustituto.coberturas(mapCoberturas);
            t2=System.currentTimeMillis();
            logger.debug("######$ tiempo despues de coberturas "+t2);
            logger.debug("######$ tiempo consumido en coberturas "+(t2-t1));
            logger.debug("######$ tiempo total consumido "+(t2-t));
            /*////////////////////*/
            ////// coberturas //////
            ////////////////////////
            
            //////////////////////////
            ////// TARIFICACION //////
            /*//////////////////////*/
            Map<String,String> mapaTarificacion=new HashMap<String,String>(0);
            mapaTarificacion.put("pv_cdusuari_i",   usuario.getUser());
            mapaTarificacion.put("pv_cdelemen_i",   usuario.getEmpresa().getElementoId());
            //mapaTarificacion.put("pv_cdunieco_i",   datosUsuario.getCdunieco());
            //mapaTarificacion.put("pv_cdramo_i",     datosUsuario.getCdramo());
            mapaTarificacion.put("pv_cdunieco_i",   cdunieco);//se agrega desde el formulario
            mapaTarificacion.put("pv_cdramo_i",     cdramo);//se agrega desde el formulario
            mapaTarificacion.put("pv_estado_i",     "W");
            mapaTarificacion.put("pv_nmpoliza_i",   numeroPoliza);
            mapaTarificacion.put("pv_nmsituac_i",   "0");
            mapaTarificacion.put("pv_nmsuplem_i",   "0");
            //mapaTarificacion.put("pv_cdtipsit_i",   datosUsuario.getCdtipsit());
            mapaTarificacion.put("pv_cdtipsit_i",   cdtipsit);
            t1=System.currentTimeMillis();
            logger.debug("######$ tiempo antes de asigsvalipol "+t1);
            WrapperResultados wr4=kernelManagerSustituto.ejecutaASIGSVALIPOL(mapaTarificacion);
            t2=System.currentTimeMillis();
            logger.debug("######$ tiempo despues de asigsvalipol "+t2);
            logger.debug("######$ tiempo consumido en asigsvalipol "+(t2-t1));
            logger.debug("######$ tiempo total consumido "+(t2-t));
            /*//////////////////////*/
            ////// TARIFICACION //////
            //////////////////////////
            
            ///////////////////////////////////
            ////// Generacion cotizacion //////
            /*///////////////////////////////*/
            Map<String,String> mapaDuroResultados=new HashMap<String,String>(0);
            mapaDuroResultados.put("pv_cdusuari_i", usuario.getUser());
            //mapaDuroResultados.put("pv_cdunieco_i", datosUsuario.getCdunieco());
            //mapaDuroResultados.put("pv_cdramo_i",   datosUsuario.getCdramo());
            mapaDuroResultados.put("pv_cdunieco_i", cdunieco);//se agrega desde el formulario
            mapaDuroResultados.put("pv_cdramo_i",   cdramo);//se agrega desde el formulario
            mapaDuroResultados.put("pv_estado_i",   "W");
            mapaDuroResultados.put("pv_nmpoliza_i", numeroPoliza);
            mapaDuroResultados.put("pv_cdelemen_i", usuario.getEmpresa().getElementoId());
            //mapaDuroResultados.put("pv_cdtipsit_i", datosUsuario.getCdtipsit());
            mapaDuroResultados.put("pv_cdtipsit_i", cdtipsit);
            t1=System.currentTimeMillis();
            logger.debug("######$ tiempo antes de obtener resultados cotizacion "+t1);
            List<ResultadoCotizacionVO> listaResultados=kernelManagerSustituto.obtenerResultadosCotizacion(mapaDuroResultados);
            t2=System.currentTimeMillis();
            logger.debug("######$ tiempo despues de obtener resultados cotizacion "+t2);
            logger.debug("######$ tiempo consumido en obtener resultados cotizacion "+(t2-t1));
            logger.debug("######$ tiempo total consumido "+(t2-t));
            //utilizando logica anterior
            CotizacionManagerImpl managerAnterior=new CotizacionManagerImpl();
            gridResultados=managerAnterior.adaptarDatosCotizacion(listaResultados);
            logger.debug("### session poniendo resultados con grid: "+listaResultados.size());
            session.put(ResultadoCotizacionAction.DATOS_GRID, gridResultados);
            /*///////////////////////////////*/
            ////// Generacion cotizacion //////
            ///////////////////////////////////
            
            id=numeroPoliza;
            
            success=true;
        }
        catch(Exception ex)
        {
            logger.error(ex.getMessage(), ex);
            success=false;
        }
        return SUCCESS;
    }
    
    public String obtenerResultadosCotizacion()
    {
        try
        {
            ResultadoCotizacionAction actionAnterior=new ResultadoCotizacionAction();
            actionAnterior.setSession(session);
            actionAnterior.obtenerResultado();
            dataResult=actionAnterior.getDataResult();
            success=true;
        }
        catch(Exception ex)
        {
            logger.error("error al obtener datos del grid",ex);
            success=false;
            dataResult=new JSONArray();
        }
        return SUCCESS;
    }
    
    public String obtenerCoberturas()
    {
        try
        {
            if(jsonCober_unieco!=null&&jsonCober_unieco.length()>0
                    &&jsonCober_cdplan!=null&&jsonCober_cdplan.length()>0
                    )//cuando se reciben parametros se calcula
            {
                Map<String,String>mapaCoberturas=new HashMap<String,String>(0);
                UserVO usuario=(UserVO) session.get("USUARIO");
                mapaCoberturas.put("pv_usuario_i",   usuario.getUser());
                mapaCoberturas.put("pv_cdunieco_i",  jsonCober_unieco);
                mapaCoberturas.put("pv_estado_i",    jsonCober_estado);
                mapaCoberturas.put("pv_nmpoliza_i",  jsonCober_nmpoiza);
                mapaCoberturas.put("pv_nmsituac_i",  jsonCober_situa);
                mapaCoberturas.put("pv_nmsuplem_i",  "0");
                mapaCoberturas.put("pv_cdplan_i",    jsonCober_cdplan);
                mapaCoberturas.put("pv_cdramo_i",    jsonCober_cdramo);
                mapaCoberturas.put("pv_cdcia_i",     jsonCober_cdcia);
                mapaCoberturas.put("pv_region_i",    "ME");
                mapaCoberturas.put("pv_pais_i",      usuario.getPais().getValue());
                mapaCoberturas.put("pv_idioma_i",    usuario.getIdioma().getValue());
                
                listaCoberturas=this.kernelManagerSustituto.obtenerCoberturas(mapaCoberturas);
            }
            /*else//cuando dan refresh se obtiene calculo anterior
            {
                listaCoberturas=(List<CoberturaCotizacionVO>) session.get(SK_COBERTURAS_COTIZACION);
            }*/
            success=true;
        }
        catch(Exception ex)
        {
            logger.error("Error al obtener las coberturas",ex);
            listaCoberturas=new ArrayList<CoberturaCotizacionVO>(0);
            success=false;
        }
        return SUCCESS;
    }
    
    public String comprarCotizacion()
    /*pv_cdunieco   input
    --pv_cdramo     input
    --pv_estado     W
    --pv_nmpoliza   input
    --pv_nmsituac   0
    --pv_cdelement  Usuario sesion
    --pv_cdperson   Usuario sesion
    --pv_cdasegur   input
    --pv_cdplan     input
    --pv_cdperpag   input
    */
    {
    	logger.debug(""
    			+ "\n################################"
    			+ "\n###### comprar cotizacion ######"
    			);
    	logger.debug("smap1: "+smap1);
    	
    	exito   = true;
    	success = true;
    	
    	UserVO usuario     = null;
    	String cdusuari    = null;
    	String cdperson    = null;
    	String fechaInicio = null;
    	String fechaFin    = null;
    	String ntramite    = null;
    	String cdagente    = null;
    	String nmcuadro    = null;
    	String cdelemen    = null;
    	String cdpersonCli = null;
    	String cdideperCli = null;
    	String cdagenteExt = null;
    	
    	//sesion valida
    	if(exito)
    	{
    		try
    		{
    			usuario  = (UserVO)session.get("USUARIO");
    			cdusuari = usuario.getUser();
    			cdelemen = usuario.getEmpresa().getElementoId();
    		}
    		catch(Exception ex)
    		{
    			long timestamp  = System.currentTimeMillis();
    			exito           = false;
    			respuesta       = "Sesi&oacute;n inv&aacute;lida #"+timestamp;
    			respuestaOculta = ex.getMessage();
    			logger.error(respuesta,ex);
    		}
    	}
    	
    	//datos completos
    	if(exito)
    	{
    		try
    		{
    			boolean completos = StringUtils.isNotBlank(comprarCdunieco)
    					&&StringUtils.isNotBlank(comprarCdramo)
    					&&StringUtils.isNotBlank(cdtipsit)
    					&&StringUtils.isNotBlank(comprarNmpoliza)
    					&&StringUtils.isNotBlank(comprarCdciaaguradora)
    					&&StringUtils.isNotBlank(comprarCdplan)
    					&&StringUtils.isNotBlank(comprarCdperpag)
    					&&smap1!=null;
    			
    			if(completos)
    			{
    				ntramite    = smap1.get("ntramite");
    				cdpersonCli = smap1.get("cdpersonCli");
    				cdideperCli = smap1.get("cdideperCli");
    				cdagenteExt = smap1.get("cdagenteExt");
    				fechaInicio = smap1.get("fechaInicio");
    				fechaFin    = smap1.get("fechaFin");
    				completos   = StringUtils.isNotBlank(fechaInicio)
    						&&StringUtils.isNotBlank(fechaFin);
    			}
    			
    			if(!completos)
    			{
    				throw new Exception("Datos incompletos");
    			}
    		}
    		catch(Exception ex)
    		{
    			long timestamp  = System.currentTimeMillis();
    			exito           = false;
    			respuesta       = "Datos incompletos #"+timestamp;
    			respuestaOculta = ex.getMessage();
    			logger.error(respuesta,ex);
    		}
    	}
    	
    	//datos de usuario
    	if(exito)
    	{
    		try
    		{
    			DatosUsuario userData = kernelManagerSustituto.obtenerDatosUsuario(cdusuari,cdtipsit);
    			cdperson              = userData.getCdperson();
    			cdagente              = userData.getCdagente();
    			nmcuadro              = userData.getNmcuadro();
    		}
    		catch(Exception ex)
    		{
    			long timestamp  = System.currentTimeMillis();
    			exito           = false;
    			respuesta       = "Usted no tiene permisos para este producto #"+timestamp;
    			respuestaOculta = ex.getMessage();
    			logger.error(respuesta,ex);
    		}
    	}
    	
    	//datos de agente
    	if(exito&&StringUtils.isNotBlank(cdagenteExt))
    	{
    		try
    		{
    			Map<String,String>datosAgenteExterno=cotizacionManager.obtenerDatosAgente(cdagenteExt,comprarCdramo);
    			cdagente=datosAgenteExterno.get("CDAGENTE");
    			nmcuadro=datosAgenteExterno.get("NMCUADRO");
    		}
    		catch(Exception ex)
    		{
    			long timestamp  = System.currentTimeMillis();
    			exito           = false;
    			respuesta       = "Error al recuperar los datos del agente #"+timestamp;
    			respuestaOculta = ex.getMessage();
    			logger.error(respuesta,ex);
    		}
    	}
    	
    	//detalle suplemento
    	if(exito)
    	{
    		try
    		{
    			Map<String,Object>parameters=new HashMap<String,Object>(0);
	            parameters.put("pv_cdunieco_i" , comprarCdunieco);
	            parameters.put("pv_cdramo_i"   , comprarCdramo);
	            parameters.put("pv_estado_i"   , "W");
	            parameters.put("pv_nmpoliza_i" , comprarNmpoliza);
	            parameters.put("pv_nsuplogi_i" , "0");
	            parameters.put("pv_cdtipsup_i" , "1");
	            parameters.put("pv_feemisio_i" , calendarHoy.getTime());
	            parameters.put("pv_nmsolici_i" , null);
	            parameters.put("pv_fesolici_i" , calendarHoy.getTime());
	            parameters.put("pv_ferefere_i" , null);
	            parameters.put("pv_cdseqpol_i" , null);
	            parameters.put("pv_cduser_i"   , cdusuari);
	            parameters.put("pv_nusuasus_i" , null);
	            parameters.put("pv_nlogisus_i" , null);
	            parameters.put("pv_cdperson_i" , cdperson);
	            parameters.put("pv_accion_i"   , Constantes.INSERT_MODE);
	            kernelManagerSustituto.movDetalleSuplemento(parameters);
    		}
    		catch(Exception ex)
    		{
    			long timestamp  = System.currentTimeMillis();
    			exito           = false;
    			respuesta       = "Error al insertar suplemento #"+timestamp;
    			respuestaOculta = ex.getMessage();
    			logger.error(respuesta,ex);
    		}
    	}
    	
    	//maestro historico poliza
    	if(exito)
    	{
    		try
    		{
                Map<String,Object>map2=new LinkedHashMap<String,Object>(0);
                map2.put("pv_cdunieco_i"  , comprarCdunieco);
                map2.put("pv_cdramo_i"    , comprarCdramo);
                map2.put("pv_estado_i"    , "W");
                map2.put("pv_nmpoliza_i"  , comprarNmpoliza);
                map2.put("pv_nmsuplem_i"  , "0");
                map2.put("pv_feINival_i"  , renderFechas.parse(fechaInicio));//date
                map2.put("pv_hhinival_i"  , renderHora.format(calendarHoy.getTime()));
                map2.put("pv_fefINval_i"  , renderFechas.parse(fechaFin));//date
                map2.put("pv_hhfinval_i"  , renderHora.format(calendarHoy.getTime()));
                map2.put("pv_swanula_i"   , null);
                map2.put("pv_nsuplogi_i"  , "0");
                map2.put("pv_nsupusua_i"  , null);
                map2.put("pv_nsupsess_i"  , null);
                map2.put("pv_fesessio_i"  , null);
                map2.put("pv_swconfir_i"  , null);
                map2.put("pv_nmrenova_i"  , null);
                map2.put("pv_nsuplori_i"  , null);
                map2.put("pv_cdorddoc_i"  , null);
                map2.put("pv_swpolfro_i"  , null);
                map2.put("pv_pocofron_i"  , null);
                map2.put("pv_swpoldec_i"  , null);
                map2.put("pv_tippodec_i"  , null);
                map2.put("pv_accion_i"    , "I");
                kernelManagerSustituto.insertaMaestroHistoricoPoliza(map2);
    		}
    		catch(Exception ex)
    		{
    			long timestamp  = System.currentTimeMillis();
    			exito           = false;
    			respuesta       = "Error al insertar hist&oacute;rico de p&oacute;liza #"+timestamp;
    			respuestaOculta = ex.getMessage();
    			logger.error(respuesta,ex);
    		}
    	}
    	
    	//mpoliage
    	if(exito)
    	{
	    	try
	    	{
	            Map<String,Object>map3=new LinkedHashMap<String,Object>(0);
	            map3.put("pv_cdunieco_i" , comprarCdunieco);
	            map3.put("pv_cdramo_i"   , comprarCdramo);
	            map3.put("pv_estado_i"   , "W");
	            map3.put("pv_nmpoliza_i" , comprarNmpoliza);
	            map3.put("pv_cdagente_i" , cdagente);
	            map3.put("pv_nmsuplem_i" , "0");
	            map3.put("pv_status_i"   , "V");
	            map3.put("pv_cdtipoag_i" , "1");
	            map3.put("pv_porredau_i" , "0");
	            map3.put("pv_nmcuadro_i" , nmcuadro);
	            map3.put("pv_cdsucurs_i" , null);
	            map3.put("pv_accion_i"   , "I");
	            if(StringUtils.isNotBlank(ntramite))
	            {
	            	map3.put("pv_ntramite_i" , ntramite);
	            }
	            else
	            {
	            	map3.put("pv_ntramite_i" , null);
	            }
	            map3.put("pv_porparti_i" , "100");
	            kernelManagerSustituto.movMPoliage(map3);
	    	}
    		catch(Exception ex)
    		{
    			long timestamp  = System.currentTimeMillis();
    			exito           = false;
    			respuesta       = "Error al ligar la p&oacute;liza al agente #"+timestamp;
    			respuestaOculta = ex.getMessage();
    			logger.error(respuesta,ex);
    		}
    	}
    	
    	//comprar
    	if(exito)
    	{
    		try
    		{
	            Map<String,String>parameters2=new HashMap<String,String>(0);
	            parameters2.put("pv_cdunieco"  , comprarCdunieco);
	            parameters2.put("pv_cdramo"    , comprarCdramo);
	            parameters2.put("pv_estado"    , "W"); 
	            parameters2.put("pv_nmpoliza"  , comprarNmpoliza);
	            parameters2.put("pv_nmsituac"  , "0");
	            parameters2.put("pv_cdelement" , cdelemen);
	            parameters2.put("pv_cdperson"  , cdperson);
	            parameters2.put("pv_cdasegur"  , comprarCdciaaguradora);
	            parameters2.put("pv_cdplan"    , comprarCdplan);
	            parameters2.put("pv_cdperpag"  , comprarCdperpag);
	            kernelManagerSustituto.comprarCotizacion(parameters2);
    		}
    		catch(Exception ex)
    		{
    			long timestamp  = System.currentTimeMillis();
    			exito           = false;
    			respuesta       = "Error al generar p&oacute;liza #"+timestamp;
    			respuestaOculta = ex.getMessage();
    			logger.error(respuesta,ex);
    		}
    	}
    	
    	//acutalizar/generar tramite
    	if(exito)
    	{
    		//actualizar tramite
    		if(StringUtils.isNotBlank(ntramite))
    		{
	    		try
	    		{
	            	WrapperResultados mesaContWr=kernelManagerSustituto.mesaControlUpdateSolici(ntramite, comprarNmpoliza);
	            	
	            	logger.debug("se inserta detalle nuevo");
	            	Map<String,Object>parDmesCon=new LinkedHashMap<String,Object>(0);
	            	parDmesCon.put("pv_ntramite_i"   , ntramite);
	            	parDmesCon.put("pv_feinicio_i"   , new Date());
	            	parDmesCon.put("pv_cdclausu_i"   , null);
	            	parDmesCon.put("pv_comments_i"   , "Se guard&oacute; una cotizaci&oacute;n nueva para el tr&aacute;mite");
	            	parDmesCon.put("pv_cdusuari_i"   , cdusuari);
	            	parDmesCon.put("pv_cdmotivo_i"   , null);
	            	kernelManagerSustituto.movDmesacontrol(parDmesCon);
	            }
	    		catch(Exception ex)
	    		{
	    			long timestamp  = System.currentTimeMillis();
	    			exito           = false;
	    			respuesta       = "Error al actualizar el tr&aacute;mite #"+timestamp;
	    			respuestaOculta = ex.getMessage();
	    			logger.error(respuesta,ex);
	    		}
    		}
    		//se genera un tramite
            else
            {
            	try
            	{
	            	Map<String,Object>parMesCon=new LinkedHashMap<String,Object>(0);
	            	parMesCon.put("pv_cdunieco_i"   , comprarCdunieco);
	            	parMesCon.put("pv_cdramo_i"     , comprarCdramo);
	            	parMesCon.put("pv_estado_i"     , "W");
	            	parMesCon.put("pv_nmpoliza_i"   , "0");
	            	parMesCon.put("pv_nmsuplem_i"   , "0");
	            	parMesCon.put("pv_cdsucadm_i"   , null);
	            	parMesCon.put("pv_cdsucdoc_i"   , null);
	            	parMesCon.put("pv_cdtiptra_i"   , "1");
	            	parMesCon.put("pv_ferecepc_i"   , new Date());
	            	parMesCon.put("pv_cdagente_i"   , cdagente);
	            	parMesCon.put("pv_referencia_i" , null);
	            	parMesCon.put("pv_nombre_i"     , "");
	            	parMesCon.put("pv_festatus_i"   , new Date());
	            	parMesCon.put("pv_status_i"     , "2");
	            	parMesCon.put("pv_comments_i"   , "");
	            	parMesCon.put("pv_nmsolici_i"   , comprarNmpoliza);
	            	parMesCon.put("pv_cdtipsit_i"   , cdtipsit);
	            	WrapperResultados mesaContWr = kernelManagerSustituto.PMovMesacontrol(parMesCon);
	            	ntramite                     = (String) mesaContWr.getItemMap().get("ntramite");
	            	smap1.put("ntramite",ntramite);
	            	
	            	Map<String,Object>parDmesCon=new LinkedHashMap<String,Object>(0);
	            	parDmesCon.put("pv_ntramite_i"   , ntramite);
	            	parDmesCon.put("pv_feinicio_i"   , new Date());
	            	parDmesCon.put("pv_cdclausu_i"   , null);
	            	parDmesCon.put("pv_comments_i"   , "Se guard&oacute; un nuevo tr&aacute;mite en mesa de control desde cotizaci&oacute;n de agente");
	            	parDmesCon.put("pv_cdusuari_i"   , cdusuari);
	            	parDmesCon.put("pv_cdmotivo_i"   , null);
	            	kernelManagerSustituto.movDmesacontrol(parDmesCon);
            	}
	    		catch(Exception ex)
	    		{
	    			long timestamp  = System.currentTimeMillis();
	    			exito           = false;
	    			respuesta       = "Error al generar el tr&aacute;mite #"+timestamp;
	    			respuestaOculta = ex.getMessage();
	    			logger.error(respuesta,ex);
	    		}
            }
    	}
    	
    	//generar cotizacion
    	if(exito)
    	{
            try
            {
	            File carpeta=new File(getText("ruta.documentos.poliza")+"/"+ntramite);
	            if(!carpeta.exists())
	            {
	            	if(!carpeta.mkdir())
	            	{
	            		throw new Exception("Error al crear la carpeta");
	            	}
	            }
	            
	            String urlReporteCotizacion=new StringBuilder()
	                   .append(getText("ruta.servidor.reports"))
	                   .append("?p_unieco=")  .append(comprarCdunieco)
	                   .append("&p_ramo=")    .append(comprarCdramo)
	                   .append("&p_subramo=") .append(cdtipsit)
	                   .append("&p_estado=")  .append("'W'")
	                   .append("&p_poliza=")  .append(comprarNmpoliza)
	                   .append("&p_suplem=")  .append("0")
	                   .append("&p_cdplan=")  .append(comprarCdplan)
	                   .append("&p_plan=")    .append(comprarCdplan)
	                   .append("&p_perpag=")  .append(comprarCdperpag)
	                   .append("&p_ntramite=").append(ntramite)
	                   .append("&p_cdusuari=").append(cdusuari)
	                   .append("&userid=")    .append(getText("pass.servidor.reports"))
	                   .append("&report=")    .append(getText("rdf.cotizacion.nombre."+cdtipsit))
	                   .append("&destype=cache")
	                   .append("&desformat=PDF")
	                   .append("&ACCESSIBLE=YES")
	                   .append("&paramform=no")
	                   .toString();
	            
	            String nombreArchivoCotizacion="cotizacion.pdf";
	            String pathArchivoCotizacion=new StringBuilder()
            					.append(getText("ruta.documentos.poliza"))
            					.append("/").append(ntramite)
            					.append("/").append(nombreArchivoCotizacion)
            					.toString();
	            HttpUtil.generaArchivo(urlReporteCotizacion, pathArchivoCotizacion);
            
	            Map<String,Object>mapArchivo=new LinkedHashMap<String,Object>(0);
	            mapArchivo.put("pv_cdunieco_i"  , comprarCdunieco);
	            mapArchivo.put("pv_cdramo_i"    , comprarCdramo);
	            mapArchivo.put("pv_estado_i"    , "W");
	            mapArchivo.put("pv_nmpoliza_i"  , "0");
	            mapArchivo.put("pv_nmsuplem_i"  , "0");
	            mapArchivo.put("pv_feinici_i"   , new Date());
	            mapArchivo.put("pv_cddocume_i"  , nombreArchivoCotizacion);
	            mapArchivo.put("pv_dsdocume_i"  , "COTIZACI&Oacute;N");
	            mapArchivo.put("pv_ntramite_i"  , ntramite);
	            mapArchivo.put("pv_nmsolici_i"  , comprarNmpoliza);
	            mapArchivo.put("pv_tipmov_i"    , "1");
	            mapArchivo.put("pv_swvisible_i" , null);
	            kernelManagerSustituto.guardarArchivo(mapArchivo);
            }
    		catch(Exception ex)
    		{
    			long timestamp  = System.currentTimeMillis();
    			exito           = false;
    			respuesta       = "Error al generar la cotizaci&oacute;n #"+timestamp;
    			respuestaOculta = ex.getMessage();
    			logger.error(respuesta,ex);
    		}
        }
    	
    	if(exito)
    	{
    		if(StringUtils.isNotBlank(cdpersonCli)){
    			try
        		{
    				LinkedHashMap<String,Object> parametros=new LinkedHashMap<String,Object>(0);
    				parametros.put("pv_cdunieco_i" , comprarCdunieco);
    				parametros.put("pv_cdramo_i"   , comprarCdramo);
    				parametros.put("pv_estado_i"   , "W");
    				parametros.put("pv_nmpoliza_i" , comprarNmpoliza);
    				parametros.put("pv_nmsituac_i" , "0");
    				parametros.put("pv_cdrol_i"    , "1");
    				parametros.put("pv_cdperson_i" , cdpersonCli);
    				parametros.put("pv_nmsuplem_i" , "0");
    				parametros.put("pv_status_i"   , "V");
    				parametros.put("pv_nmorddom_i" , "1");
    				parametros.put("pv_swreclam_i" , null);
    				parametros.put("pv_accion_i"   , "I");
    				parametros.put("pv_swexiper_i" , "S");
    				kernelManagerSustituto.movMpoliper(parametros);
        		}
        		catch(Exception ex)
        		{
        			long timestamp  = System.currentTimeMillis();
        			exito           = false;
        			respuesta       = "Error al guardar el cliente #"+timestamp;
        			respuestaOculta = ex.getMessage();
        			logger.error(respuesta,ex);
        		}
    			
    		}else if(StringUtils.isNotBlank(cdideperCli)){
				logger.debug("Persona proveniente de WS, Valor de cdperson en blanco, valor de cdIdeper: " + cdideperCli);
				/**
		    	 * PARA GUARDAR CLIENTE EN BASE DE DATOS DEL WS 
		    	 */
				try
        		{
			    	if(TipoSituacion.SERVICIO_PUBLICO_AUTO.getCdtipsit().equalsIgnoreCase(cdtipsit)
				    		|| TipoSituacion.SERVICIO_PUBLICO_MICRO.getCdtipsit().equalsIgnoreCase(cdtipsit)){
			    		
			    		
			    		HashMap<String, Object> paramsTip =  new HashMap<String, Object>();
			    		paramsTip.put("pv_cdramo_i", comprarCdramo);
			    		paramsTip.put("pv_cdtipsit_i",   cdtipsit);
						
				    	String cdtipsitGS = kernelManagerSustituto.obtenSubramoGS(paramsTip);
				    	
				    	ClienteGeneral clienteGeneral = new ClienteGeneral();
				    	//clienteGeneral.setRfcCli((String)aseg.get("cdrfc"));
				    	clienteGeneral.setRamoCli(Integer.parseInt(cdtipsitGS));
				    	clienteGeneral.setNumeroExterno(cdideperCli);
				    	
				    	ClienteGeneralRespuesta clientesRes = ice2sigsService.ejecutaWSclienteGeneral(null, null, null, null, null, null, Ice2sigsService.Operacion.CONSULTA_GENERAL, clienteGeneral, null, false);
				    	
				    	if(clientesRes !=null && ArrayUtils.isNotEmpty(clientesRes.getClientesGeneral())){
				    		ClienteGeneral cli = null;
				    		
				    		if(clientesRes.getClientesGeneral().length == 1){
				    			logger.debug("Cliente unico encontrado en WS, guardando informacion del WS...");
				    			cli = clientesRes.getClientesGeneral()[0];
				    		}else {
				    			logger.error("Error, No se pudo obtener el cliente del WS. Se ha encontrado mas de Un elemento!");
				    		}
				    		
				    		if(cli != null){
				    			
				    			//IR POR NUEVO CDPERSON:
					    		String newCdPerson = kernelManagerSustituto.generaCdperson();
					    		
					    		logger.debug("Insertando nueva persona, cdperson generado: " +newCdPerson);
					    		
					    		
					    		String apellidoPat = "";
						    	if(StringUtils.isNotBlank(cli.getApellidopCli()) && !cli.getApellidopCli().trim().equalsIgnoreCase("null")){
						    		apellidoPat = cli.getApellidopCli();
						    	}
						    	
						    	String apellidoMat = "";
						    	if(StringUtils.isNotBlank(cli.getApellidomCli()) && !cli.getApellidomCli().trim().equalsIgnoreCase("null")){
						    		apellidoMat = cli.getApellidomCli();
						    	}
						    	
					    		Calendar calendar =  Calendar.getInstance();
					    		
					    		String sexo = "H"; //Hombre
						    	if(cli.getSexoCli() > 0){
						    		if(cli.getSexoCli() == 2) sexo = "M";
						    	}
						    	
						    	String tipoPersona = "F"; //Fisica
						    	if(cli.getFismorCli() > 0){
						    		if(cli.getFismorCli() == 2){
						    			tipoPersona = "M";
						    		}else if(cli.getFismorCli() == 3){
						    			tipoPersona = "S";
						    		}
						    	}
						    	
						    	String nacionalidad = "001";// Nacional
						    	if(StringUtils.isNotBlank(cli.getNacCli()) && !cli.getNacCli().equalsIgnoreCase("1")){
						    		nacionalidad = "002";
						    	}
						    	
						    	if(cli.getFecnacCli()!= null){
						    		calendar.set(cli.getFecnacCli().get(Calendar.YEAR), cli.getFecnacCli().get(Calendar.MONTH), cli.getFecnacCli().get(Calendar.DAY_OF_MONTH));
						    	}
						    	
					    		//GUARDAR MPERSONA
					    		
					    		Map<String,Object> parametros=new LinkedHashMap<String,Object>(0);
								parametros.put("pv_cdperson_i"    , newCdPerson);
								parametros.put("pv_cdtipide_i"    , "1");
								parametros.put("pv_cdideper_i"    , cli.getNumeroExterno());
								parametros.put("pv_dsnombre_i"    , (cli.getFismorCli() == 1) ? cli.getNombreCli() : cli.getRazSoc());
								parametros.put("pv_cdtipper_i"    , "1");
								parametros.put("pv_otfisjur_i"    , tipoPersona);
								parametros.put("pv_otsexo_i"      , sexo);
								parametros.put("pv_fenacimi_i"    , calendar.getTime());
								parametros.put("pv_cdrfc_i"       , cli.getRfcCli());
								parametros.put("pv_dsemail_i"     , "");
								parametros.put("pv_dsnombre1_i"   , (cli.getFismorCli() == 1) ? cli.getNombreCli() : cli.getRazSoc());
								parametros.put("pv_dsapellido_i"  , apellidoPat);
								parametros.put("pv_dsapellido1_i" , apellidoMat);
								parametros.put("pv_feingreso_i"   , calendarHoy.getTime());
								parametros.put("pv_cdnacion_i"    , "001");
								parametros.put("pv_canaling_i"    , null);
								parametros.put("pv_conducto_i"    , null);
								parametros.put("pv_ptcumupr_i"    , null);
								parametros.put("pv_residencia_i"  , null);
								parametros.put("pv_accion_i"      , "I");
								kernelManagerSustituto.movMpersona(parametros);
					    		
					    		//GUARDAR DOMICILIO
					    		HashMap<String,String> paramDomicil = new HashMap<String, String>();
				    			paramDomicil.put("pv_cdperson_i", newCdPerson);
				    			paramDomicil.put("pv_nmorddom_i", "1");
				    			paramDomicil.put("pv_msdomici_i", cli.getCalleCli() +" "+ cli.getNumeroCli());
				    			paramDomicil.put("pv_nmtelefo_i", cli.getTelefonoCli());
				    			paramDomicil.put("pv_cdpostal_i", cli.getCodposCli());
				    			
				    			String edoAdosPos2 = Integer.toString(cli.getEstadoCli());
				    			if(edoAdosPos2.length() ==  1){
				    				edoAdosPos2 = "0"+edoAdosPos2;
				    			}
				    			
				    			paramDomicil.put("pv_cdedo_i",    cli.getCodposCli()+edoAdosPos2);
				    			paramDomicil.put("pv_cdmunici_i", null/*cliDom.getMunicipioCli()*/);
				    			paramDomicil.put("pv_cdcoloni_i", null/*cliDom.getColoniaCli()*/);
				    			paramDomicil.put("pv_nmnumero_i", cli.getNumeroCli());
				    			paramDomicil.put("pv_nmnumint_i", null);
				    			paramDomicil.put("pv_accion_i", "I");
			
				    			kernelManagerSustituto.pMovMdomicil(paramDomicil);
				    			
				    			HashMap<String,String> paramValoper = new HashMap<String, String>();
				    			paramValoper.put("pv_cdunieco", "0");
				    			paramValoper.put("pv_cdramo",   "0");
				    			paramValoper.put("pv_estado",   null);
				    			paramValoper.put("pv_nmpoliza", "0");
				    			paramValoper.put("pv_nmsituac", null);
				    			paramValoper.put("pv_nmsuplem", null);
				    			paramValoper.put("pv_status",   null);
				    			paramValoper.put("pv_cdrol",    "1");
				    			paramValoper.put("pv_cdperson", newCdPerson);
				    			paramValoper.put("pv_cdatribu", null);
				    			paramValoper.put("pv_cdtipsit", null);
				    			
				    			paramValoper.put("pv_otvalor01", cli.getCveEle());
				    			paramValoper.put("pv_otvalor02", cli.getPasaporteCli());
				    			paramValoper.put("pv_otvalor03", null);
				    			paramValoper.put("pv_otvalor04", null);
				    			paramValoper.put("pv_otvalor05", null);
				    			paramValoper.put("pv_otvalor06", null);
				    			paramValoper.put("pv_otvalor07", null);
				    			paramValoper.put("pv_otvalor08", cli.getOrirecCli());
				    			paramValoper.put("pv_otvalor09", null);
				    			paramValoper.put("pv_otvalor10", null);
				    			paramValoper.put("pv_otvalor11", cli.getNacCli());
				    			paramValoper.put("pv_otvalor12", null);
				    			paramValoper.put("pv_otvalor13", null);
				    			paramValoper.put("pv_otvalor14", null);
				    			paramValoper.put("pv_otvalor15", null);
				    			paramValoper.put("pv_otvalor16", null);
				    			paramValoper.put("pv_otvalor17", null);
				    			paramValoper.put("pv_otvalor18", null);
				    			paramValoper.put("pv_otvalor19", null);
				    			paramValoper.put("pv_otvalor20", (cli.getOcuPro() > 0) ? Integer.toString(cli.getOcuPro()) : "0");
				    			paramValoper.put("pv_otvalor21", null);
				    			paramValoper.put("pv_otvalor22", null);
				    			paramValoper.put("pv_otvalor23", null);
				    			paramValoper.put("pv_otvalor24", null);
				    			paramValoper.put("pv_otvalor25", cli.getCurpCli());
				    			paramValoper.put("pv_otvalor26", null);
				    			paramValoper.put("pv_otvalor27", null);
				    			paramValoper.put("pv_otvalor28", null);
				    			paramValoper.put("pv_otvalor29", null);
				    			paramValoper.put("pv_otvalor30", null);
				    			paramValoper.put("pv_otvalor31", null);
				    			paramValoper.put("pv_otvalor32", null);
				    			paramValoper.put("pv_otvalor33", null);
				    			paramValoper.put("pv_otvalor34", null);
				    			paramValoper.put("pv_otvalor35", null);
				    			paramValoper.put("pv_otvalor36", null);
				    			paramValoper.put("pv_otvalor37", null);
				    			paramValoper.put("pv_otvalor38", null);
				    			paramValoper.put("pv_otvalor39", cli.getMailCli());
				    			paramValoper.put("pv_otvalor40", null);
				    			paramValoper.put("pv_otvalor41", null);
				    			paramValoper.put("pv_otvalor42", null);
				    			paramValoper.put("pv_otvalor43", null);
				    			paramValoper.put("pv_otvalor44", null);
				    			paramValoper.put("pv_otvalor45", null);
				    			paramValoper.put("pv_otvalor46", null);
				    			paramValoper.put("pv_otvalor47", null);
				    			paramValoper.put("pv_otvalor48", null);
				    			paramValoper.put("pv_otvalor49", null);
				    			paramValoper.put("pv_otvalor50", null);
				    			
				    			kernelManagerSustituto.pMovTvaloper(paramValoper);
				    			
				    			LinkedHashMap<String,Object> paramsMpoliper=new LinkedHashMap<String,Object>(0);
				    			paramsMpoliper.put("pv_cdunieco_i" , comprarCdunieco);
				    			paramsMpoliper.put("pv_cdramo_i"   , comprarCdramo);
				    			paramsMpoliper.put("pv_estado_i"   , "W");
				    			paramsMpoliper.put("pv_nmpoliza_i" , comprarNmpoliza);
				    			paramsMpoliper.put("pv_nmsituac_i" , "0");
								paramsMpoliper.put("pv_cdrol_i"    , "1");
								paramsMpoliper.put("pv_cdperson_i" , newCdPerson);
								paramsMpoliper.put("pv_nmsuplem_i" , "0");
								paramsMpoliper.put("pv_status_i"   , "V");
								paramsMpoliper.put("pv_nmorddom_i" , "1");
								paramsMpoliper.put("pv_swreclam_i" , null);
								paramsMpoliper.put("pv_accion_i"   , "I");
								paramsMpoliper.put("pv_swexiper_i" , "N");//N por ser de WS
								kernelManagerSustituto.movMpoliper(paramsMpoliper);
				    			
				    		}
				    	}
			    	}
	    		}
    		catch(Exception ex)
    		{
    			long timestamp  = System.currentTimeMillis();
    			exito           = false;
    			respuesta       = "Error al guardar el cliente #"+timestamp;
    			respuestaOculta = ex.getMessage();
    			logger.error(respuesta,ex);
    		}
				
			}
    		
    	}
    	
        logger.info(""
    			+ "\n###### comprar cotizacion ######"
    			+ "\n################################"
    			);
        return SUCCESS;
    }
    
    public String obtenerAyudaCoberturas()
    {
        try
        {
            logger.debug("### obtener ayuda de cobertura para cobertura: "+idCobertura+","+idRamo+","+idCiaAseguradora);
            ayudaCobertura=kernelManagerSustituto.obtenerAyudaCobertura(idCobertura,idRamo,idCiaAseguradora);
            success=true;
        }
        catch(Exception ex)
        {
            logger.error("error al obtener la ayuda de coberturas",ex);
            success=false;
        }
        return SUCCESS;
    }

    
    //Getters and setters:
    
    public void setKernelManagerSustituto(KernelManagerSustituto kernelManagerSustituto) {
        this.kernelManagerSustituto = kernelManagerSustituto;
    }
    
    public void setCatalogosManager(CatalogosManager catalogosManager) {
		this.catalogosManager = catalogosManager;
	}

	public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public BigDecimal getDeducible() {
        return deducible;
    }

    public void setDeducible(BigDecimal deducible) {
        this.deducible = deducible;
    }

    public String getCopago() {
        return copago;
    }

    public void setCopago(String copago) {
        this.copago = copago;
    }

    public String getSumaSegurada() {
        return sumaSegurada;
    }

    public void setSumaSegurada(String sumaSegurada) {
        this.sumaSegurada = sumaSegurada;
    }

    public String getCirculoHospitalario() {
        return circuloHospitalario;
    }

    public void setCirculoHospitalario(String circuloHospitalario) {
        this.circuloHospitalario = circuloHospitalario;
    }

    public String getCoberturaVacunas() {
        return coberturaVacunas;
    }

    public void setCoberturaVacunas(String coberturaVacunas) {
        this.coberturaVacunas = coberturaVacunas;
    }

    public String getCoberturaPrevencionEnfermedadesAdultos() {
        return coberturaPrevencionEnfermedadesAdultos;
    }

    public void setCoberturaPrevencionEnfermedadesAdultos(String coberturaPrevencionEnfermedadesAdultos) {
        this.coberturaPrevencionEnfermedadesAdultos = coberturaPrevencionEnfermedadesAdultos;
    }

    public String getMaternidad() {
        return maternidad;
    }

    public void setMaternidad(String maternidad) {
        this.maternidad = maternidad;
    }

    public String getSumaAseguradaMaternidad() {
        return sumaAseguradaMaternidad;
    }

    public void setSumaAseguradaMaternidad(String sumaAseguradaMaternidad) {
        this.sumaAseguradaMaternidad = sumaAseguradaMaternidad;
    }

    public String getBaseTabuladorReembolso() {
        return baseTabuladorReembolso;
    }

    public void setBaseTabuladorReembolso(String baseTabuladorReembolso) {
        this.baseTabuladorReembolso = baseTabuladorReembolso;
    }

    public String getCostoEmergenciaExtranjero() {
        return costoEmergenciaExtranjero;
    }

    public void setCostoEmergenciaExtranjero(String costoEmergenciaExtranjero) {
        this.costoEmergenciaExtranjero = costoEmergenciaExtranjero;
    }

    public List<IncisoSaludVO> getIncisos() {
        return incisos;
    }

    public void setIncisos(List<IncisoSaludVO> incisos) {
        this.incisos = incisos;
    }

    public String getCdatribuSexo() {
        return cdatribuSexo;
    }

    public String getCdatribuCopago() {
        return cdatribuCopago;
    }

    public String getCdatribuSumaAsegurada() {
        return cdatribuSumaAsegurada;
    }

    public String getCdatribuCirculoHospitalario() {
        return cdatribuCirculoHospitalario;
    }

    public String getCdatribuCoberturaVacunas() {
        return cdatribuCoberturaVacunas;
    }

    public String getCdatribuCoberturaPrevEnfAdultos() {
        return cdatribuCoberturaPrevEnfAdultos;
    }

    public String getCdatribuMaternidad() {
        return cdatribuMaternidad;
    }

    public String getCdatribuSumaAseguradaMatenidad() {
        return cdatribuSumaAseguradaMatenidad;
    }

    public String getCdatribuBaseTabuladorRembolso() {
        return cdatribuBaseTabuladorRembolso;
    }

    public String getCdatribuCostoEmergenciaExtranjero() {
        return cdatribuCostoEmergenciaExtranjero;
    }
    
    public String getCdatribuMunicipio()
    {
    	return cdatribuMunicipio;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCdatribuRol() {
        return cdatribuRol;
    }

    public String getCoberturaEliminacionPenalizacionCambioZona() {
        return coberturaEliminacionPenalizacionCambioZona;
    }

    public void setCoberturaEliminacionPenalizacionCambioZona(String coberturaEliminacionPenalizacionCambioZona) {
        this.coberturaEliminacionPenalizacionCambioZona = coberturaEliminacionPenalizacionCambioZona;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCdatribuCobEliPenCamZona() {
        return cdatribuCobEliPenCamZona;
    }
    
    public String getCdatribuEstado()
    {
        return cdatribuEstado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GridVO getGridResultados() {
        return gridResultados;
    }

    public void setGridResultados(GridVO gridResultados) {
        this.gridResultados = gridResultados;
    }

    public JSONArray getDataResult() {
        return dataResult;
    }

    public void setDataResult(JSONArray dataResult) {
        this.dataResult = dataResult;
    }

    public List<CoberturaCotizacionVO> getListaCoberturas() {
        return listaCoberturas;
    }

    public void setListaCoberturas(List<CoberturaCotizacionVO> listaCoberturas) {
        this.listaCoberturas = listaCoberturas;
    }

    public void setJsonCober_unieco(String jsonCober_unieco) {
        this.jsonCober_unieco = jsonCober_unieco;
    }

    public void setJsonCober_estado(String jsonCober_estado) {
        this.jsonCober_estado = jsonCober_estado;
    }

    public void setJsonCober_nmpoiza(String jsonCober_nmpoiza) {
        this.jsonCober_nmpoiza = jsonCober_nmpoiza;
    }

    public void setJsonCober_cdplan(String jsonCober_cdplan) {
        this.jsonCober_cdplan = jsonCober_cdplan;
    }

    public void setJsonCober_cdramo(String jsonCober_cdramo) {
        this.jsonCober_cdramo = jsonCober_cdramo;
    }

    public void setJsonCober_cdcia(String jsonCober_cdcia) {
        this.jsonCober_cdcia = jsonCober_cdcia;
    }

    public String getJsonCober_situa() {
        return jsonCober_situa;
    }

    public void setJsonCober_situa(String jsonCober_situa) {
        this.jsonCober_situa = jsonCober_situa;
    }

    public String getFechaInicioVigencia() {
        return fechaInicioVigencia;
    }

    public void setFechaInicioVigencia(String fechaInicioVigencia) {
        this.fechaInicioVigencia = fechaInicioVigencia;
    }

    public String getFechaFinVigencia() {
        return fechaFinVigencia;
    }

    public void setFechaFinVigencia(String fechaFinVigencia) {
        this.fechaFinVigencia = fechaFinVigencia;
    }

    public String getIdCobertura() {
        return idCobertura;
    }

    public void setIdCobertura(String idCobertura) {
        this.idCobertura = idCobertura;
    }

    public AyudaCoberturaCotizacionVO getAyudaCobertura() {
        return ayudaCobertura;
    }

    public void setAyudaCobertura(AyudaCoberturaCotizacionVO ayudaCobertura) {
        this.ayudaCobertura = ayudaCobertura;
    }
    
    public String getCdatribuDeducible()
    {
        return cdatribuDeducible;
    }

    public String getIdRamo() {
        return idRamo;
    }

    public void setIdRamo(String idRamo) {
        this.idRamo = idRamo;
    }

    public String getIdCiaAseguradora() {
        return idCiaAseguradora;
    }

    public void setIdCiaAseguradora(String idCiaAseguradora) {
        this.idCiaAseguradora = idCiaAseguradora;
    }

    public String getComprarNmpoliza() {
        return comprarNmpoliza;
    }

    public void setComprarNmpoliza(String comprarNmpoliza) {
        this.comprarNmpoliza = comprarNmpoliza;
    }

    public String getComprarCdplan() {
        return comprarCdplan;
    }

    public void setComprarCdplan(String comprarCdplan) {
        this.comprarCdplan = comprarCdplan;
    }

    public String getComprarCdperpag() {
        return comprarCdperpag;
    }

    public void setComprarCdperpag(String comprarCdperpag) {
        this.comprarCdperpag = comprarCdperpag;
    }

    public String getComprarCdramo() {
        return comprarCdramo;
    }

    public void setComprarCdramo(String comprarCdramo) {
        this.comprarCdramo = comprarCdramo;
    }

    public String getComprarCdciaaguradora() {
        return comprarCdciaaguradora;
    }

    public void setComprarCdciaaguradora(String comprarCdciaaguradora) {
        this.comprarCdciaaguradora = comprarCdciaaguradora;
    }

    public String getComprarCdunieco() {
        return comprarCdunieco;
    }

    public void setComprarCdunieco(String comprarCdunieco) {
        this.comprarCdunieco = comprarCdunieco;
    }

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public Map<String, String> getSmap1() {
		return smap1;
	}

	public void setSmap1(Map<String, String> smap1) {
		this.smap1 = smap1;
	}

	public String getNtramite() {
		return ntramite;
	}

	public void setNtramite(String ntramite) {
		this.ntramite = ntramite;
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

	public String getCdtipsit() {
		return cdtipsit;
	}

	public void setCdtipsit(String cdtipsit) {
		this.cdtipsit = cdtipsit;
	}

	public List<Map<String, String>> getSlist1() {
		return slist1;
	}

	public void setSlist1(List<Map<String, String>> slist1) {
		this.slist1 = slist1;
	}

	public String getEdadMaximaCotizacion() {
		return edadMaximaCotizacion;
	}

	public void setEdadMaximaCotizacion(String edadMaximaCotizacion) {
		this.edadMaximaCotizacion = edadMaximaCotizacion;
	}

	public boolean isExito() {
		return exito;
	}

	public void setExito(boolean exito) {
		this.exito = exito;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getRespuestaOculta() {
		return respuestaOculta;
	}

	public void setRespuestaOculta(String respuestaOculta) {
		this.respuestaOculta = respuestaOculta;
	}

	public void setCotizacionManager(CotizacionManager cotizacionManager) {
		this.cotizacionManager = cotizacionManager;
	}

	public void setIce2sigsService(Ice2sigsService ice2sigsService) {
		this.ice2sigsService = ice2sigsService;
	}
    
}