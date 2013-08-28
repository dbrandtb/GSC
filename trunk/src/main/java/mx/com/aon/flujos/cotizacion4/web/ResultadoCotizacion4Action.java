package mx.com.aon.flujos.cotizacion4.web;

import com.opensymphony.xwork2.ActionContext;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.portal.web.model.IncisoSaludVO;
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
    public static final String cdatribuEstado                       ="3";
    public static final String cdatribuCiudad                       ="4";
    //                         deducible                              5
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
    
    //Datos de cotizacion
    //sexo (inciso)                                               1
    //fecha nacimiento (inciso)                                   2
    private String estado;                                      //3
    private String ciudad;                                      //4
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
    
    private List<IncisoSaludVO> incisos;
    private boolean success;
    
    //utilitarios
    SimpleDateFormat renderFechas = new SimpleDateFormat("dd/MM/yyyy");
    Calendar calendarHoy=Calendar.getInstance();
    
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
            ///////////////////////////////////////////////////
            Calendar fechaEnUnAnio=Calendar.getInstance();
            fechaEnUnAnio.add(Calendar.YEAR, 1);
            log.debug("### fecha "+renderFechas.format(calendarHoy.getTime()));
            log.debug("### fecha en un anio: "+renderFechas.format(fechaEnUnAnio.getTime()));
            ///////////////////////////////////////////////////
            
            ///////////////////////////////////////////
            ////// ini Crear un numero de poliza //////
            ///////////////////////////////////////////
            WrapperResultados wrapperNumeroPoliza=kernelManagerSustituto.calculaNumeroPoliza("1", "2", "W");
            String numeroPoliza=(String) wrapperNumeroPoliza.getItemMap().get("NUMERO_POLIZA");
            ///////////////////////////////////////////
            ////// fin Crear un numero de poliza //////
            ///////////////////////////////////////////
            
            ///////////////////////////////////////////////////////
            ////// ini Guardar el maestro de poliza nmpoliza //////
            ///////////////////////////////////////////////////////
            Map<String,String>mapa=new HashMap<String,String>(0);
            mapa.put("pv_cdunieco",     "1");
            mapa.put("pv_cdramo",       "2");
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
            mapa.put("pv_feefecto",     renderFechas.format(calendarHoy.getTime()));
            mapa.put("pv_hhefecto",     "12:00");
            mapa.put("pv_feproren",     renderFechas.format(fechaEnUnAnio.getTime()));
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
                Map<String,String>mapaPolisitIterado=new HashMap<String,String>(0);
                mapaPolisitIterado.put("pv_cdunieco_i",    "1");
                mapaPolisitIterado.put("pv_cdramo_i",      "2");
                mapaPolisitIterado.put("pv_estado_i",      "W");
                mapaPolisitIterado.put("pv_nmpoliza_i",    numeroPoliza);
                mapaPolisitIterado.put("pv_nmsituac_i",    contador+"");
                mapaPolisitIterado.put("pv_nmsuplem_i",    "0");
                mapaPolisitIterado.put("pv_status_i",      "V");
                mapaPolisitIterado.put("pv_cdtipsit_i",    "SL");
                mapaPolisitIterado.put("pv_swreduci_i",    null);
                mapaPolisitIterado.put("pv_cdagrupa_i",    "1");
                mapaPolisitIterado.put("pv_cdestado_i",    "0");
                mapaPolisitIterado.put("pv_fefecsit_i",    renderFechas.format(calendarHoy.getTime()));
                mapaPolisitIterado.put("pv_fecharef_i",    renderFechas.format(calendarHoy.getTime()));
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
                mapaValositIterado.put("pv_cdunieco",    "1");
                mapaValositIterado.put("pv_cdramo",      "2");
                mapaValositIterado.put("pv_estado",      "W");
                mapaValositIterado.put("pv_nmpoliza",    numeroPoliza);
                mapaValositIterado.put("pv_nmsituac",    contador+"");
                mapaValositIterado.put("pv_nmsuplem",    "0");
                mapaValositIterado.put("pv_status",      "V");
                mapaValositIterado.put("pv_cdtipsit",    "SL");
                mapaValositIterado.put("pv_otvalor01",   i.getSexo().getKey());//sexo
                mapaValositIterado.put("pv_otvalor02",   renderFechas.format(i.getFechaNacimiento()));//f nacimiento
                mapaValositIterado.put("pv_otvalor03",   estado);//estado
                mapaValositIterado.put("pv_otvalor04",   ciudad);//ciudad
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
                Map<String,String> mapaClonPersonaIterado=new HashMap<String,String>(0);
                mapaClonPersonaIterado.put("pv_cdelemen_i",     usuario.getEmpresa().getElementoId());
                mapaClonPersonaIterado.put("pv_cdunieco_i",     "1");
                mapaClonPersonaIterado.put("pv_cdramo_i",       "2");
                mapaClonPersonaIterado.put("pv_estado_i",       "W");
                mapaClonPersonaIterado.put("pv_nmpoliza_i",     numeroPoliza);
                mapaClonPersonaIterado.put("pv_nmsituac_i",     contador+"");
                mapaClonPersonaIterado.put("pv_cdtipsit_i",     "SL");
                mapaClonPersonaIterado.put("pv_fecha_i",        renderFechas.format(calendarHoy.getTime()));
                mapaClonPersonaIterado.put("pv_cdusuario_i",    usuario.getName());
                mapaClonPersonaIterado.put("pv_p_nombre",       i.getNombre());
                mapaClonPersonaIterado.put("pv_s_nombre",       i.getSegundoNombre());
                mapaClonPersonaIterado.put("pv_apellidop",      i.getApellidoPaterno());
                mapaClonPersonaIterado.put("pv_apellidom",      i.getApellidoMaterno());
                mapaClonPersonaIterado.put("pv_sexo",           i.getSexo().getKey());
                mapaClonPersonaIterado.put("pv_fenacimi",       renderFechas.format(i.getFechaNacimiento()));
                kernelManagerSustituto.clonaPersonas(mapaClonPersonaIterado);
                contador++;
            }
            /////////////////////////////////
            ////// fin clonar personas //////
            /////////////////////////////////
            
            /* ini *
            Map<String,String> map3=new HashMap<String,String>(0);
            map3.put("pv_cdelemen_i",   usuario.getEmpresa().getElementoId());
            map3.put("pv_cdunieco_i",   "1");
            map3.put("pv_cdramo_i",     "2");
            map3.put("pv_estado_i",     "W");
            map3.put("pv_nmpoliza_i",   numeroPoliza);
            map3.put("pv_nmsituac_i",   "0");
            map3.put("pv_cdtipsit_i",   "SL");
            map3.put("pv_fecha_i",      renderFechas.format(calendarHoy.getTime()));
            map3.put("pv_cdusuario_i",  usuario.getUser());
            map3.put("pv_cadena_i",     null);//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
            log.debug("### Invocacion de clonar situaciones map:" +map3);
            List<SituacionVO> listaSituacionesClonadas=kernelManagerSustituto.clonaSituaciones(map3);
            listaSituacionesClonadas=listaSituacionesClonadas!=null?listaSituacionesClonadas:new ArrayList<SituacionVO>(0);
            log.debug("### listaSituacionesClonadas size() "+listaSituacionesClonadas.size());
            /* fin */
            
            ////////////////////////
            ////// coberturas //////
            /*////////////////////*/
            Map<String,String> mapCoberturas=new HashMap<String,String>(0);
            mapCoberturas.put("pv_cdunieco_i",   "1");
            mapCoberturas.put("pv_cdramo_i",     "2");
            mapCoberturas.put("pv_estado_i",     "W");
            mapCoberturas.put("pv_nmpoliza_i",   numeroPoliza);
            mapCoberturas.put("pv_nmsituac_i",   "0");
            mapCoberturas.put("pv_nmsuplem_i",   "0");//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
            mapCoberturas.put("pv_cdgarant_i",   "TODO");//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
            kernelManagerSustituto.coberturas(mapCoberturas);
            /*////////////////////*/
            ////// coberturas //////
            ////////////////////////
            
            /* ini *
            Map<String,String> map5=new HashMap<String,String>(0);
            map5.put("pv_cdusuari_i",   usuario.getUser());
            map5.put("pv_cdelemen_i",   usuario.getEmpresa().getElementoId());
            map5.put("pv_cdunieco_i",   "1");
            map5.put("pv_cdramo_i",     "2");
            map5.put("pv_estado_i",     "W");
            map5.put("pv_nmpoliza_i",   numeroPoliza);
            map5.put("pv_nmsituac_i",   "0");
            map5.put("pv_nmsuplem_i",   null);//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
            map5.put("pv_cdtipsit_i",   "SL");
            log.debug("### Invocacion de ejecutaASIGSVALIPOL (tarificacion) map:" +map5);
            WrapperResultados wr4=kernelManagerSustituto.ejecutaASIGSVALIPOL(map5);
            log.debug("### response id "+wr4.getMsgId());
            log.debug("### response text "+wr4.getMsgText());
            /* fin */
            
            success=true;
        }
        catch(Exception ex)
        {
            log.error(ex.getMessage(), ex);
            success=false;
        }
        return SUCCESS;
    }

    public void setKernelManagerSustituto(KernelManagerSustituto kernelManagerSustituto) {
        this.kernelManagerSustituto = kernelManagerSustituto;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
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

    public String getCdatribuCiudad() {
        return cdatribuCiudad;
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
    
}