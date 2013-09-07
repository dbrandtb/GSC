package mx.com.aon.flujos.cotizacion4.web;

import com.opensymphony.xwork2.ActionContext;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;

/**
 *
 * @author Jair
 */
public class ResultadoCotizacion4Action extends PrincipalCoreAction{
    
    private Logger log= Logger.getLogger(ResultadoCotizacion4Action.class);
    
    //beans
    private KernelManagerSustituto kernelManagerSustituto;
    
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
    
    //Datos de cotizacion
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
    private String fechaInicioVigencia;
    private String fechaFinVigencia;
    
    private List<IncisoSaludVO> incisos;
    private boolean success;
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
    
    //utilitarios
    SimpleDateFormat renderFechas = new SimpleDateFormat("dd/MM/yyyy");
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
    
    public String entrar()
    {
        UserVO usuario=(UserVO) session.get("USUARIO");
        log.debug("### usuario name: "+usuario.getName());
        log.debug("### usuario user: "+usuario.getUser());
        log.debug("### usuario empresa cdelemento id: "+usuario.getEmpresa().getElementoId());
        log.debug("### usuario codigopersona: "+usuario.getCodigoPersona());
        return SUCCESS;
    }
    
    public String cotizar()
    {
        try
        {
            /////////////////////////////////////////////
            ////// Obtener el usuario de la sesion //////
            /////////////////////////////////////////////
            this.session=ActionContext.getContext().getSession();//porque se uso SMD en struts.xml y eso pierde la sesion
            UserVO usuario=(UserVO) session.get("USUARIO");
            log.debug("### usuario name: "+usuario.getName());
            log.debug("### usuario user: "+usuario.getUser());
            log.debug("### usuario empresa cdelemento id: "+usuario.getEmpresa().getElementoId());
            log.debug("### usuario codigopersona: "+usuario.getCodigoPersona());
            /////////////////////////////////////////////
            
            ///////////////////////////////////////////////////
            ////// Calcular un anio a partir de la fecha //////
            /*///////////////////////////////////////////////*
            Calendar fechaEnUnAnio=Calendar.getInstance();
            fechaEnUnAnio.add(Calendar.YEAR, 1);
            log.debug("### fecha "+renderFechas.format(calendarHoy.getTime()));
            log.debug("### fecha en un anio: "+renderFechas.format(fechaEnUnAnio.getTime()));*/
            /*///////////////////////////////////////////////*/
            ////// Calcular un anio a partir de la fecha //////
            ///////////////////////////////////////////////////
            
            ///////////////////////////////////////////////////
            ////// obtener datos del usuario //////////////////
            /*///////////////////////////////////////////////*/
            DatosUsuario datosUsuario=kernelManagerSustituto.obtenerDatosUsuario(usuario.getUser());
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
                WrapperResultados wrapperNumeroPoliza=kernelManagerSustituto
                        .calculaNumeroPoliza(datosUsuario.getCdunieco(),datosUsuario.getCdramo(),"W");
                numeroPoliza=(String) wrapperNumeroPoliza.getItemMap().get("NUMERO_POLIZA");
            }
            ///////////////////////////////////////////
            ////// fin Crear un numero de poliza //////
            ///////////////////////////////////////////
            
            ///////////////////////////////////////////////////////
            ////// ini Guardar el maestro de poliza nmpoliza //////
            ///////////////////////////////////////////////////////
            Map<String,String>mapa=new HashMap<String,String>(0);
            mapa.put("pv_cdunieco",     datosUsuario.getCdunieco());
            mapa.put("pv_cdramo",       datosUsuario.getCdramo());
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
            mapa.put("pv_feefecto",     fechaFinVigencia);//renderFechas.format(calendarHoy.getTime()));
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
            mapa.put("pv_accion",       "U");
            log.debug("### Invocacion de insercion de maestro de poliza map: "+mapa);
            WrapperResultados wr=kernelManagerSustituto.insertaMaestroPolizas(mapa);
            log.debug("### response id "+wr.getMsgId());
            log.debug("### response text "+wr.getMsgText());
            ///////////////////////////////////////////////////////
            ////// fin Guardar el maestro de poliza nmpoliza //////
            ///////////////////////////////////////////////////////
            
            ///////////////////////////////////////////////////////////////////////////////
            ////// ini Guardar las situaciones (mpolisit y un mvalosit por cada una) //////
            ///////////////////////////////////////////////////////////////////////////////
            int contador=1;
            for(IncisoSaludVO i : incisos)
            {
                log.debug("### Iteracion de polisit y valosit #"+contador);
                
                //////////////////////////////////
                ////// ini mpolisit iterado //////
                //////////////////////////////////
                Map<String,Object>mapaPolisitIterado=new HashMap<String,Object>(0);
                mapaPolisitIterado.put("pv_cdunieco_i",    datosUsuario.getCdunieco());
                mapaPolisitIterado.put("pv_cdramo_i",      datosUsuario.getCdramo());
                mapaPolisitIterado.put("pv_estado_i",      "W");
                mapaPolisitIterado.put("pv_nmpoliza_i",    numeroPoliza);
                mapaPolisitIterado.put("pv_nmsituac_i",    contador+"");
                mapaPolisitIterado.put("pv_nmsuplem_i",    "0");
                mapaPolisitIterado.put("pv_status_i",      "V");
                mapaPolisitIterado.put("pv_cdtipsit_i",    datosUsuario.getCdtipsit());
                mapaPolisitIterado.put("pv_swreduci_i",    null);
                mapaPolisitIterado.put("pv_cdagrupa_i",    "1");
                mapaPolisitIterado.put("pv_cdestado_i",    "0");
                mapaPolisitIterado.put("pv_fefecsit_i",    calendarHoy.getTime());
                mapaPolisitIterado.put("pv_fecharef_i",    calendarHoy.getTime());
                mapaPolisitIterado.put("pv_cdgrupo_i",     null);
                mapaPolisitIterado.put("pv_nmsituaext_i",  null);
                mapaPolisitIterado.put("pv_nmsitaux_i",    null);
                mapaPolisitIterado.put("pv_nmsbsitext_i",  null);
                mapaPolisitIterado.put("pv_cdplan_i",      "1");
                mapaPolisitIterado.put("pv_cdasegur_i",    "30");
                mapaPolisitIterado.put("pv_accion_i",      "I");
                kernelManagerSustituto.insertaPolisit(mapaPolisitIterado);
                //////////////////////////////////
                ////// fin mpolisit iterado //////
                //////////////////////////////////
                
                //////////////////////////////////
                ////// ini mvalosit iterado //////
                //////////////////////////////////
                Map<String,String>mapaValositIterado=new HashMap<String,String>(0);
                mapaValositIterado.put("pv_cdunieco",    datosUsuario.getCdunieco());
                mapaValositIterado.put("pv_cdramo",      datosUsuario.getCdramo());
                mapaValositIterado.put("pv_estado",      "W");
                mapaValositIterado.put("pv_nmpoliza",    numeroPoliza);
                mapaValositIterado.put("pv_nmsituac",    contador+"");
                mapaValositIterado.put("pv_nmsuplem",    "0");
                mapaValositIterado.put("pv_status",      "V");
                mapaValositIterado.put("pv_cdtipsit",    datosUsuario.getCdtipsit());
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
                kernelManagerSustituto.insertaValoresSituaciones(mapaValositIterado);
                //////////////////////////////////
                ////// fin mvalosit iterado //////
                //////////////////////////////////
                
                log.debug("### Fin de iteracion de polisit y valosit #"+contador);
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
                log.debug("### Iteracion de clonar personas #"+contador);
                Map<String,Object> mapaClonPersonaIterado=new HashMap<String,Object>(0);
                mapaClonPersonaIterado.put("pv_cdelemen_i",     usuario.getEmpresa().getElementoId());
                mapaClonPersonaIterado.put("pv_cdunieco_i",     datosUsuario.getCdunieco());
                mapaClonPersonaIterado.put("pv_cdramo_i",       datosUsuario.getCdramo());
                mapaClonPersonaIterado.put("pv_estado_i",       "W");
                mapaClonPersonaIterado.put("pv_nmpoliza_i",     numeroPoliza);
                mapaClonPersonaIterado.put("pv_nmsituac_i",     contador+"");
                mapaClonPersonaIterado.put("pv_cdtipsit_i",     datosUsuario.getCdtipsit());
                mapaClonPersonaIterado.put("pv_fecha_i",        calendarHoy.getTime());
                mapaClonPersonaIterado.put("pv_cdusuario_i",    usuario.getUser());
                mapaClonPersonaIterado.put("pv_p_nombre",       i.getNombre());
                mapaClonPersonaIterado.put("pv_s_nombre",       i.getSegundoNombre());
                mapaClonPersonaIterado.put("pv_apellidop",      i.getApellidoPaterno());
                mapaClonPersonaIterado.put("pv_apellidom",      i.getApellidoMaterno());
                mapaClonPersonaIterado.put("pv_sexo",           i.getSexo().getKey());
                mapaClonPersonaIterado.put("pv_fenacimi",       i.getFechaNacimiento());
                kernelManagerSustituto.clonaPersonas(mapaClonPersonaIterado);
                contador++;
            }
            /////////////////////////////////
            ////// fin clonar personas //////
            /////////////////////////////////
            
            ////////////////////////
            ////// coberturas //////
            /*////////////////////*/
            Map<String,String> mapCoberturas=new HashMap<String,String>(0);
            mapCoberturas.put("pv_cdunieco_i",   datosUsuario.getCdunieco());
            mapCoberturas.put("pv_cdramo_i",     datosUsuario.getCdramo());
            mapCoberturas.put("pv_estado_i",     "W");
            mapCoberturas.put("pv_nmpoliza_i",   numeroPoliza);
            mapCoberturas.put("pv_nmsituac_i",   "0");
            mapCoberturas.put("pv_nmsuplem_i",   "0");//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
            mapCoberturas.put("pv_cdgarant_i",   "TODO");//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
            kernelManagerSustituto.coberturas(mapCoberturas);
            /*////////////////////*/
            ////// coberturas //////
            ////////////////////////
            
            //////////////////////////
            ////// TARIFICACION //////
            /*//////////////////////*/
            Map<String,String> mapaTarificacion=new HashMap<String,String>(0);
            mapaTarificacion.put("pv_cdusuari_i",   usuario.getUser());
            mapaTarificacion.put("pv_cdelemen_i",   usuario.getEmpresa().getElementoId());
            mapaTarificacion.put("pv_cdunieco_i",   datosUsuario.getCdunieco());
            mapaTarificacion.put("pv_cdramo_i",     datosUsuario.getCdramo());
            mapaTarificacion.put("pv_estado_i",     "W");
            mapaTarificacion.put("pv_nmpoliza_i",   numeroPoliza);
            mapaTarificacion.put("pv_nmsituac_i",   "0");
            mapaTarificacion.put("pv_nmsuplem_i",   "0");
            mapaTarificacion.put("pv_cdtipsit_i",   datosUsuario.getCdtipsit());
            WrapperResultados wr4=kernelManagerSustituto.ejecutaASIGSVALIPOL(mapaTarificacion);
            /*//////////////////////*/
            ////// TARIFICACION //////
            //////////////////////////
            
            ///////////////////////////////////
            ////// Generacion cotizacion //////
            /*///////////////////////////////*/
            Map<String,String> mapaDuroResultados=new HashMap<String,String>(0);
            mapaDuroResultados.put("pv_cdusuari_i", usuario.getUser());
            mapaDuroResultados.put("pv_cdunieco_i", datosUsuario.getCdunieco());
            mapaDuroResultados.put("pv_cdramo_i",   datosUsuario.getCdramo());
            mapaDuroResultados.put("pv_estado_i",   "W");
            mapaDuroResultados.put("pv_nmpoliza_i", numeroPoliza);
            mapaDuroResultados.put("pv_cdelemen_i", usuario.getEmpresa().getElementoId());
            mapaDuroResultados.put("pv_cdtipsit_i", datosUsuario.getCdtipsit());
            List<ResultadoCotizacionVO> listaResultados=kernelManagerSustituto.obtenerResultadosCotizacion(mapaDuroResultados);
            //utilizando logica anterior
            CotizacionManagerImpl managerAnterior=new CotizacionManagerImpl();
            gridResultados=managerAnterior.adaptarDatosCotizacion(listaResultados);
            log.debug("### session poniendo resultados con grid: "+listaResultados.size());
            session.put(ResultadoCotizacionAction.DATOS_GRID, gridResultados);
            /*///////////////////////////////*/
            ////// Generacion cotizacion //////
            ///////////////////////////////////
            
            id=numeroPoliza;
            
            success=true;
        }
        catch(Exception ex)
        {
            log.error(ex.getMessage(), ex);
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
            log.error("error al obtener datos del grid",ex);
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
                UserVO usuario=(UserVO) session.get("USUARIO");
                Map<String,String>mapaCoberturas=new HashMap<String,String>(0);
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
                this.listaCoberturas=this.kernelManagerSustituto.obtenerCoberturas(mapaCoberturas);
                session.put(SK_COBERTURAS_COTIZACION,listaCoberturas);
            }
            else//cuando dan refresh se obtiene calculo anterior
            {
                listaCoberturas=(List<CoberturaCotizacionVO>) session.get(SK_COBERTURAS_COTIZACION);
            }
            success=true;
        }
        catch(Exception ex)
        {
            log.error("Error al obtener las coberturas",ex);
            listaCoberturas=new ArrayList<CoberturaCotizacionVO>(0);
            success=false;
        }
        return SUCCESS;
    }
    
    public String comprarCotizacion()
    {
        log.debug(comprarNmpoliza+","+comprarCdplan+","+comprarCdperpag);
        success=true;
        return SUCCESS;
    }
    
    public String obtenerAyudaCoberturas()
    {
        try
        {
            log.debug("### obtener ayuda de cobertura para cobertura: "+idCobertura+","+idRamo+","+idCiaAseguradora);
            ayudaCobertura=kernelManagerSustituto.obtenerAyudaCobertura(idCobertura,idRamo,idCiaAseguradora);
            success=true;
        }
        catch(Exception ex)
        {
            log.error("error al obtener la ayuda de coberturas",ex);
            success=false;
        }
        return SUCCESS;
    }

    public void setKernelManagerSustituto(KernelManagerSustituto kernelManagerSustituto) {
        this.kernelManagerSustituto = kernelManagerSustituto;
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
    
}