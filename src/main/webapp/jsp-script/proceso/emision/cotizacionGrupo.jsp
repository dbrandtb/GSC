<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
.valorNoOriginal {
    background: #FFFF99;
}
.valorRenovacionColec {
    background: #FFD299;
}
._p21_editorLectura {
    visibility: hidden;
}
.tatrigarHide {
    display: none;
}
</style>
<!-- Paging Persistence library -->
<script type="text/javascript" src="${ctx}/resources/extjs4/plugins/pagingpersistence/pagingselectionpersistence2.js?${now}"></script>
<script>
////// overrides //////
Ext.override(Ext.form.TextField,
{
    initComponent:function()
    {
        Ext.apply(this,
        {
            labelWidth : 250
        });
        return this.callParent();
    }
});

Ext.override(Ext.form.NumberField,
{
    initComponent:function()
    {
        Ext.apply(this,
        {
            decimalPrecision : 20
        });
        return this.callParent();
    }
});
////// overrides //////

////// variables //////
var _p21_urlObtenerCoberturas            = '<s:url namespace="/emision"         action="obtenerCoberturasPlan"            />';
var _p21_urlObtenerCoberturasColec       = '<s:url namespace="/emision"         action="obtenerCoberturasPlanColec"       />';
var _p21_urlObtienePlanDefinitivo        = '<s:url namespace="/emision"         action="obtienePlanDefinitivo"       />';
var _p21_urlObtenerSumaAseguradaDefault  = '<s:url namespace="/emision"         action="obtenSumaAseguradosMedicamentos"  />';
var _p21_urlObtenerHijosCobertura        = '<s:url namespace="/emision"         action="obtenerTatrigarCoberturas"        />';
var _p21_urlSubirCenso                   = '<s:url namespace="/emision"         action="subirCenso"                       />';
var _p21_urlSubirCensoCompleto           = '<s:url namespace="/emision"         action="subirCensoCompleto"               />';
var _p21_urlGenerarTramiteGrupo          = '<s:url namespace="/emision"         action="generarTramiteGrupo"              />';
var _p21_urlObtenerDetalle               = '<s:url namespace="/emision"         action="obtenerDetalleCotizacionGrupo"    />';
var _p21_urlComprar                      = '<s:url namespace="/flujocotizacion" action="comprarCotizacion4"               />';
var _p21_urlVentanaDocumentos            = '<s:url namespace="/documentos"      action="ventanaDocumentosPoliza"          />';
var _p21_urlVentanaDocumentosClon        = '<s:url namespace="/documentos"      action="ventanaDocumentosPolizaClon"      />';
var _p21_urlBuscarPersonas               = '<s:url namespace="/"                action="buscarPersonasRepetidas"          />';
var _p21_urlCargarDatosCotizacion        = '<s:url namespace="/emision"         action="cargarDatosCotizacionGrupo"       />';
var _p21_urlCargarGrupos                 = '<s:url namespace="/emision"         action="cargarGruposCotizacion"           />';
var _p21_urlObtenerDatosAdicionalesGrupo = '<s:url namespace="/emision"         action="cargarDatosGrupoLinea"            />';
var _p21_urlObtenerTvalogarsGrupo        = '<s:url namespace="/emision"         action="cargarTvalogarsGrupo"             />';
var _p21_urlObtenerTarifaEdad            = '<s:url namespace="/emision"         action="cargarTarifasPorEdad"             />';
var _p21_urlObtenerTarifaCobertura       = '<s:url namespace="/emision"         action="cargarTarifasPorCobertura"        />';
var _p21_urlMesaControl                  = '<s:url namespace="/flujomesacontrol" action="mesaControl"                     />';
var _p21_urlActualizarStatus             = '<s:url namespace="/mesacontrol"     action="actualizarStatusTramite"          />';
var _p21_urlEmitir                       = '<s:url namespace="/emision"         action="emitirColectivo"                  />';
var _p21_urlViewDoc                      = '<s:url namespace="/documentos"      action="descargaDocInline"                />';
var _p21_urlCargarAseguradosExtraprimas  = '<s:url namespace="/emision"         action="cargarAseguradosExtraprimas"      />';
var _p21_urlGuardarExtraprimas           = '<s:url namespace="/emision"         action="guardarExtraprimasAsegurados"     />';
var _p21_urlGuardarSituacionesTitulares  = '<s:url namespace="/emision"         action="guardarValoresSituacionesTitular" />';
var _p21_urlSigsvalipol                  = '<s:url namespace="/emision"         action="ejecutaSigsvalipol"               />';
var _p21_urlCargarAseguradosGrupo        = '<s:url namespace="/emision"         action="cargarAseguradosGrupo"            />';
var _p21_urlCargarAseguradosGrupoPag     = '<s:url namespace="/emision"         action="cargarAseguradosGrupoPag"		  />';
var _p21_urlRecuperarPersona             = '<s:url namespace="/"                action="buscarPersonasRepetidas"          />';
var _p25_urlPantallaPersonas             = '<s:url namespace="/catalogos"       action="includes/personasLoader"          />';
var _p25_urlPantallaDomicilio            = '<s:url namespace="/catalogos"       action="includes/editarDomicilioAsegurado"/>';
var _p21_urlEditarCoberturas             = '<s:url namespace="/"                action="editarCoberturas"                 />';
var _p21_urlGuardarAsegurados            = '<s:url namespace="/emision"         action="guardarAseguradosCotizacion"      />';
var _p21_urlEditarExclusiones            = '<s:url namespace="/"                action="pantallaExclusion"                />';
var _p21_guardarReporteCotizacion        = '<s:url namespace="/emision"         action="guardarReporteCotizacionGrupo"    />';
var _p21_urlCargarParametros             = '<s:url namespace="/emision"         action="obtenerParametrosCotizacion"      />';
var _p21_urlCargarConceptosGlobales      = '<s:url namespace="/emision"         action="cargarConceptosGlobalesGrupo"     />';
var _p21_urlGuardarContratanteColectivo  = '<s:url namespace="/emision"         action="guardarContratanteColectivo"      />';
var _p21_urlRecuperarProspecto           = '<s:url namespace="/emision"         action="cargarTramite"                    />';
var _p21_urlPantallaClausulasPoliza      = '<s:url namespace="/emision"         action="includes/pantallaClausulasPoliza" />';
var _p21_urlRecuperacionSimple           = '<s:url namespace="/emision"         action="recuperacionSimple"               />';
var _p21_urlRecuperacionSimpleLista      = '<s:url namespace="/emision"         action="recuperacionSimpleLista"          />';
var _p21_urlPantallaAgentes              = '<s:url namespace="/flujocotizacion" action="principal"                        />';
var _p21_urlComplementoCotizacion        = '<s:url namespace="/emision"         action="complementoSaludGrupo"            />';
var _p21_urlRecuperacion                 = '<s:url namespace="/recuperacion"    action="recuperar"                        />';
var _p21_urlRestaurarRespaldoCenso       = '<s:url namespace="/emision"         action="restaurarRespaldoCenso"           />';
var _p21_urlBorrarRespaldoCenso          = '<s:url namespace="/emision"         action="borrarRespaldoCenso"              />';
var _p21_urlReporte                 	 = '<s:url namespace="/reportes"    	action="procesoObtencionReporte" />';
//estas url se declaran con cotcol para ser usadas desde funcionesCotizacionGrupo.js en comun con cotizacionGrupo2.jsp
var _cotcol_urlPantallaEspPersona   = '<s:url namespace="/persona"  action="includes/pantallaEspPersona"  />'
    ,_cotcol_urlPantallaActTvalosit = '<s:url namespace="/tvalosit" action="includes/pantallaActTvalosit" />'
    ,_cotcol_urlBorrarAsegurado     = '<s:url namespace="/emision"  action="borrarIncisoCotizacion"       />';
//estas url se declaran con cotcol para ser usadas desde funcionesCotizacionGrupo.js en comun con cotizacionGrupo2.jsp 

var _p21_urlMarcarTramitePendienteVistaPrevia = '<s:url namespace="/mesacontrol" action="marcarTramiteVistaPrevia" />';
var _p21_nombreReporteCotizacion        = '<s:text name='%{"rdf.cotizacion.nombre."+smap1.cdtipsit.toUpperCase()}' />';
var _p21_nombreReporteCotizacionDetalle = '<s:text name='%{"rdf.cotizacion2.nombre."+smap1.cdtipsit.toUpperCase()}' />';

var _p21_urlImprimirCotiza = '<s:text name="ruta.servidor.reports" />';
var _p21_reportsServerUser = '<s:text name="pass.servidor.reports" />';
var _TIPO_SITUACION_RENOVACION 			= '<s:property value="@mx.com.gseguros.portal.general.util.TipoEndoso@RENOVACION.cdTipSup" />';
var _EN_ESPERA_DE_COTIZACION 			= '<s:property value="@mx.com.gseguros.portal.general.util.EstatusTramite@EN_ESPERA_DE_COTIZACION.codigo" />';

var _p21_clasif             = null;
var _p21_storeGrupos        = null;
var _p21_tabGrupos          = null;
var _p21_tabGruposLineal    = null;
var _p21_tabGruposModifi    = null;
var _p21_semaforoPlanChange = true;
var _p21_gridTarifas        = null;
var _p21_selectedCdperpag   = null;
var _p21_selectedCdplan     = null;
var _p21_selectedDsplan     = null;
var _p21_selectedNmsituac   = null;
var _p21_semaforo           = true;
var _p21_incrinflAux        = null;
var _p21_extrrenoAux        = null;
var _p21_valoresFactores    = null;
var _p21_resubirCenso       = 'N';

var _p21_filtroCobTimeout;

var _p22_parentCallback     = false;
var _callbackContPrincipal = false;
var _contratanteSaved = false;
var _callbackDomicilioAseg = false;

var _ventanaPersonas;
var _numIDpantalla;

var _ventanaClausulas;
var _ventanaGridAgentesSuperior;

var _callbackAseguradoExclusiones =  function (){
   	_ventanaClausulas.close();
};

var _p_21_panelPrincipal;

var codpostalDefinitivo;
var cdedoDefinitivo;
var cdmuniciDefinitivo;

_defaultNmordomProspecto = undefined;// valor default del numero de domicilio del prospecto
var nmorddomProspecto = _defaultNmordomProspecto; 

var expande                 = function(){};

var _p21_arrayNombresFactores =
[
     'FACTOR RENOVACION'
    ,'FACTOR RENOVACIÓN'
    ,'FACTOR INFLACION'
    ,'FACTOR INFLACIÓN'
];

var _p21_arrayNombresIncrinfl =
[
    'FACTOR INFLACION'
    ,'FACTOR INFLACIÓN'
];

var _p21_arrayNombresExtrreno =
[
   'FACTOR RENOVACIÓN'
   ,'FACTOR RENOVACION'
];

var _p21_smap1 = <s:property value='%{convertToJSON("smap1")}' escapeHtml="false" />;
debug('_p21_smap1:',_p21_smap1);
var _p21_cdtipsup = _p21_smap1.cdtipsup;

_p21_smap1.modificarTodo = false;

if (Number(_p21_smap1.status) === 24
    && [RolSistema.Agente, RolSistema.EjecutivoVenta, RolSistema.MesaControl].indexOf(_p21_smap1.cdsisrol) != -1
    ) {
    _p21_smap1.modificarTodo = true;
}

//se declara el mapa como cotcol para el archivo comun funcionesCotizacionGrupo.js
var _cotcol_smap1 = _p21_smap1;
debug('_cotcol_smap1:',_cotcol_smap1);
//se declara el mapa como cotcol para el archivo comun funcionesCotizacionGrupo.js

//Para la pantalla de agentes
var inputCdunieco = _p21_smap1.cdunieco;
var inputCdramo   = _p21_smap1.cdramo;
var inputEstado   = _p21_smap1.estado;
var inputNmpoliza = _p21_smap1.nmpoliza;
//Para la pantalla de agentes

var _p21_ntramite = Ext.isEmpty(_p21_smap1.ntramite) ? false : _p21_smap1.ntramite;
var inputNtramite = _p21_ntramite;
debug('_p21_ntramite:',_p21_ntramite);

var _p21_ntramiteVacio = Ext.isEmpty(_p21_smap1.ntramiteVacio) ? false : _p21_smap1.ntramiteVacio;
debug('_p21_ntramiteVacio:',_p21_ntramiteVacio);

var _p21_editorNombreGrupo=
{
    xtype       : 'textfield'
    ,allowBlank : false
    ,minLength  : 3
};

var _p21_editorNombrePlan=
{
    xtype       : 'textfield'
    ,allowBlank : true
    ,minLength  : 3
    ,readOnly   : ([RolSistema.SupervisorTecnico].indexOf(_p21_smap1.cdsisrol) != -1 ) ? false : true
};

var _p21_editorPlan = <s:property value="imap.editorPlanesColumn" />.editor;
_p21_editorPlan.on('change',_p21_editorPlanChange);
debug('_p21_editorPlan:',_p21_editorPlan);

var _p21_editorSumaAseg = <s:property value="imap.editorSumaAsegColumn" />.editor;
debug('_p21_editorSumaAseg:',_p21_editorSumaAseg);
_p21_editorSumaAseg.forceSelection = false;
_p21_editorSumaAseg.heredar        = function(cdplanIn)
{
    if(
        (
            !Ext.isEmpty(_p21_editorPlan.getValue())
            &&_p21_editorPlan.findRecordByValue(_p21_editorPlan.getValue())!=false
        )
        ||!Ext.isEmpty(cdplanIn)
    )
    {
        _p21_editorSumaAseg.getStore().load(
        {
            params :
            {
                'params.cdplan' : Ext.isEmpty(cdplanIn)?_p21_editorPlan.getValue():cdplanIn
            }
            ,callback : function()
            {
                _p21_editorSumaAseg.forceSelection=true;
                if(_p21_editorSumaAseg.findRecordByValue(_p21_editorSumaAseg.getValue())!=false)
                {
                    _p21_editorSumaAseg.reset();
                }
            }
        });
    }
}
_p21_editorPlan.on(
{
    change : function()
    {
        _p21_editorSumaAseg.heredar();
    }
});

var _p21_editorAyudaMater = <s:property value="imap.editorAyudaMaterColumn" />.editor;
debug('_p21_editorAyudaMater:',_p21_editorAyudaMater);

var _p21_editorAsisInter = <s:property value="imap.editorAsisInterColumn" />.editor;
debug('_p21_editorAsisInter:',_p21_editorAsisInter);

var _p21_editorEmerextr = <s:property value="imap.editorEmerextrColumn" />.editor;
debug('_p21_editorEmerextr:',_p21_editorEmerextr);

var _p21_editorDeducible = <s:property value="imap.editorDeducibleColumn" />.editor;
debug('_p21_editorDeducible:',_p21_editorDeducible);

var _p21_editorPaquete = <s:property value="imap.editorPaqueteColumn" />.editor;
debug('_p21_editorPaquete:',_p21_editorPaquete);

var _p21_TARIFA_LINEA      = 1;
var _p21_TARIFA_MODIFICADA = 2;

var _p82_callback;
var _p47_callback;

var _cotcol_flujo = <s:property value="%{convertToJSON('flujo')}" escapeHtml="false" />;
debug('_cotcol_flujo:', _cotcol_flujo, '.');

var listaSinPadre = [];
////// variables //////

Ext.onReady(function()
{

    _grabarEvento('COTIZACION','ACCCOTIZA'
                  ,_p21_ntramiteVacio?_p21_ntramiteVacio:(_p21_ntramite?_p21_ntramite:''),_p21_smap1.cdunieco,_p21_smap1.cdramo);

    Ext.Ajax.timeout = 60*60*1000;
    Ext.override(Ext.form.Basic, { timeout: Ext.Ajax.timeout / 1000 });
    Ext.override(Ext.data.proxy.Server, { timeout: Ext.Ajax.timeout });
    Ext.override(Ext.data.Connection, { timeout: Ext.Ajax.timeout });

    ////// modelos //////
    Ext.define('_p21_modeloGrupo',
    {
        extend  : 'Ext.data.Model'
        ,fields :
        [
            'letra'
            ,'nombre'
            ,'cdplan'
            ,'dsplanl'
            ,'ptsumaaseg'
            ,'ayudamater'
            ,'asisinte'
            ,'emerextr'
            ,'deducible'
            ,'incrinfl'
            ,'extrreno'
            ,'cesicomi'
            ,'pondubic'
            ,'descbono'
            ,'gastadmi'
            ,'utilidad'
            ,'comiagen'
            ,'comiprom'
            ,'bonoince'
            ,'otrogast'
            ,'paquete'
        ]
    });
    
    Ext.define('_p21_modeloDetalleCotizacion',
    {
        extend  : 'Ext.data.Model'
        ,fields :
        [
            'NMSITAUX'
            ,'NMSITUAC'
            ,'PARENT'
            ,'PARENTESCO'
            ,'NOMBRE'
            ,'ORDEN'
            ,'CDGARANT'
            ,'DSGARANT'
            ,{name:'IMPORTE',type:'float'}
            ,'GRUPO'
        ]
    });
    
    Ext.define('_p21_modeloTarifaEdad',
    {
        extend  : 'Ext.data.Model'
        ,fields :
        [
            'EDAD'
            ,'HOMBRES'
            ,'MUJERES'
            ,'TARIFA_UNICA_HOMBRES'
            ,'TARIFA_UNICA_MUJERES'
            ,'TARIFA_TOTAL_HOMBRES'
            ,'TARIFA_TOTAL_MUJERES'
            ,'DERPOL_TOTAL_GENERAL'
            ,'RECARGOS_TOTAL_GENERAL'
            ,'IVA_TOTAL_GENERAL'
        ]
    });
    
    Ext.define('_p21_modeloTarifaCobertura',
    {
        extend  : 'Ext.data.Model'
        ,fields :
        [
            'DSGARANT'
            ,{type:'float',name:'PRIMA_PROMEDIO'}
        ]
    });
    
    Ext.define('RFCPersona',
    {
        extend  : 'Ext.data.Model'
        ,fields : ["RFCCLI","NOMBRECLI","FENACIMICLI","DIRECCIONCLI","CLAVECLI","DISPLAY", "CDIDEPER"
        ,'CODPOSTAL','CDEDO','CDMUNICI','DSDOMICIL','NMNUMERO','NMNUMINT','CDIDEEXT','NMORDDOM']
    });
    
    Ext.define('_p21_modeloExtraprima',
    {
        extend  : 'Ext.data.Model'
        ,idProperty : 'NMSITUAC'
        ,fields : [ <s:property value='%{getImap().containsKey("extraprimasFields")?getImap().get("extraprimasFields").toString():""}' /> ]
        ,mode       : 'MULTI'
    });
    
    Ext.define('_p21_modeloAsegurados',
    {
        extend  : 'Ext.data.Model'
        ,fields : [ <s:property value='%{getImap().containsKey("aseguradosFields")?getImap().get("aseguradosFields").toString():""}' /> ]
    });
    
    Ext.define('_p21_modeloRecuperados',
    {
        extend  : 'Ext.data.Model'
        ,fields : [ <s:property value='%{getImap().containsKey("recuperadosFields")?getImap().get("recuperadosFields").toString():""}' /> ]
    });
    
    Ext.define('_p21_vpModelo',
    {
        extend  : 'Ext.data.Model'
        ,fields :
        [
            'concepto'
            ,{ name : 'importe'   , type :'float' }
            ,{ name : 'subgrupo1' , type :'float' }
            ,{ name : 'subgrupo2' , type :'float' }
            ,{ name : 'subgrupo3' , type :'float' }
            ,{ name : 'subgrupo4' , type :'float' }
            ,{ name : 'subgrupo5' , type :'float' }
            ,{ name : 'subgrupo6' , type :'float' }
            ,{ name : 'subgrupo7' , type :'float' }
            ,{ name : 'subgrupo8' , type :'float' }
            ,{ name : 'subgrupo9' , type :'float' }
        ]
    });
    
    Ext.define('_p21_modeloRevisionAsegurado',
    {
        extend  : 'Ext.data.Model'
        ,fields :
        [
            'CDUNIECO'
            ,'CDRAMO'
            ,'ESTADO'
            ,'NMPOLIZA'
            ,'CDGRUPO'
            ,'NMSITUAC'
            ,'PARENTESCO'
            ,'NOMBRE'
            ,'EDAD'
            ,'SEXO'
        ]
    });
    ////// modelos //////
    
    ////// stores //////
    _p21_storeGrupos = Ext.create('Ext.data.Store',
    {
        model : '_p21_modeloGrupo'
    });
    ////// stores //////
    
    ////// componentes //////
    var botoneslinea = [];
    if(_p21_smap1.DETALLE_LINEA=='S')
    {
        botoneslinea.push(
        {
            tooltip   : 'Editar'
            ,icon     : '${ctx}/resources/fam3icons/icons/pencil.png'
            ,handler  : _p21_editarGrupoClic
        });
    }
    if(!_p21_ntramite || _cotcol_smap1.modificarTodo === true)
    {
        botoneslinea.push(
        {
            tooltip  : 'Borrar subgrupo'
            ,icon    : '${ctx}/resources/fam3icons/icons/delete.png'
            ,handler : _p21_borrarGrupoClic
        });
    }
    if(_p21_smap1.ASEGURADOS=='S')
    {
        botoneslinea.push(
        {
            tooltip  : 'Asegurados'
            ,icon    : '${ctx}/resources/fam3icons/icons/group.png'
            ,handler : _cotcol_aseguradosClic
        });
    }
    if(_p21_smap1.EXTRAPRIMAS=='S')
    {
        botoneslinea.push(
        {
            tooltip  : 'Revisar extraprimas'
            ,icon    : '${ctx}/resources/fam3icons/icons/group_error.png'
            ,handler : _p21_revisarAseguradosClic
        });
    }
    
    _p21_tabGruposLineal =
    {
        title     : 'RESUMEN SUBGRUPOS'
        ,itemId   : '_p21_tabGruposLineal'
        ,defaults : { style : 'margin:5px' }
        ,border   : 0
        ,items    : Ext.create('Ext.grid.Panel',
        {
            tbar     :
            [
                {
                    text     : 'Agregar'
                    ,icon    : '${ctx}/resources/fam3icons/icons/add.png'
                    ,handler : _p21_agregarGrupoClic
                    ,hidden  : _p21_ntramite && _cotcol_smap1.modificarTodo === false ? true : false
                }
            ]
            ,columns :
            [
                {
                    header     : 'ID'
                    ,dataIndex : 'letra'
                    ,width     : 40
                }
                ,{
                    header     : 'Nombre'
                    ,dataIndex : 'nombre'
                    ,width     : 150
                    ,editor    : _p21_editorNombreGrupo
                }
                ,{
                    header     : 'Plan'
                    ,dataIndex : 'cdplan'
                    ,width     : 100
                    ,editor    : _p21_editorPlan
                    ,renderer  : function(v)
                    {
                        return rendererColumnasDinamico(v,'cdplan');
                    }
                }
                ,{
                    header     : 'Paquete'
                    ,dataIndex : 'paquete'
                    ,width     : 120
                    ,editor    : _p21_editorPaquete
                    ,hidden    : _p21_smap1.cdramo!='1'
                    ,renderer  : function(v)
                    {
                        return rendererColumnasDinamico(v,'paquete');
                    }
                }
                ,{
                    header     : 'Suma asegurada'
                    ,dataIndex : 'ptsumaaseg'
                    ,width     : 120
                    ,editor    : _p21_editorSumaAseg
                    ,renderer  : function(v)
                    {
                        return rendererColumnasDinamico(v,'ptsumaaseg');
                    }
                }
                ,{
                    header     : 'Deducible'
                    ,dataIndex : 'deducible'
                    ,width     : 100
                    ,editor    : _p21_editorDeducible
                    ,hidden    : _p21_smap1.cdramo!='4'||_p21_smap1.LINEA_EXTENDIDA=='N'
                    ,renderer  : function(v)
                    {
                        return rendererColumnasDinamico(v,'deducible');
                    }
                }
                ,{
                    header     : 'Ayuda Maternidad'
                    ,dataIndex : 'ayudamater'
                    ,width     : 140
                    ,editor    : _p21_editorAyudaMater
                    ,hidden    : _p21_smap1.cdramo!='4'||_p21_smap1.LINEA_EXTENDIDA=='N'
                    ,renderer  : function(v)
                    {
                        return rendererColumnasDinamico(v,'ayudamater');
                    }
                }
                ,{
                    header     : 'Medicamentos'
                    ,dataIndex : 'asisinte'
                    ,width     : 140
                    ,editor    : _p21_editorAsisInter
                    ,hidden    : _p21_smap1.cdramo!='4'||_p21_smap1.LINEA_EXTENDIDA=='N'
                    ,renderer  : function(v)
                    {
                        return rendererColumnasDinamico(v,'asisinte');
                    }
                }
                ,{
                    header     : 'Emergencia extranjero'
                    ,dataIndex : 'emerextr'
                    ,width     : 140
                    ,editor    : _p21_editorEmerextr
                    ,hidden    : _p21_smap1.cdramo!='4'||_p21_smap1.LINEA_EXTENDIDA=='N'
                    ,renderer  : function(v)
                    {
                        return rendererColumnasDinamico(v,'emerextr');
                    }
                }
                ,{
                    xtype         : 'actioncolumn'
                    ,sortable     : false
                    ,menuDisabled : true
                    ,width        : (botoneslinea.length*20) + 20
                    ,items        : botoneslinea
                }
            ]
            ,store   : _p21_storeGrupos
            ,height  : 250
            ,plugins : Ext.create('Ext.grid.plugin.RowEditing',
            {
                clicksToEdit  : 1
                ,errorSummary : false
                ,listeners    :
                {
                    beforeedit : function(editor,context)
                    {
                        if(!(
                            !_p21_ntramite||_p21_ntramiteVacio||(!Ext.isEmpty(_p21_smap1.sincenso)&&_p21_smap1.sincenso=='S')
                        ))
                        {
                            if (_cotcol_smap1.modificarTodo === false) {
                                if ([
                                        RolSistema.SuscriptorTecnico,
                                        RolSistema.SupervisorTecnico,
                                        RolSistema.SubdirectorSalud,
                                        RolSistema.DirectorSalud
                                    ].indexOf(_cotcol_smap1.cdsisrol) == -1) {
                                    return false;
                                }
                            }
                        }
                        debug('beforeedit:',context.record.get('cdplan'));
                        _p21_editorSumaAseg.forceSelection=false;
                        _p21_editorSumaAseg.heredar(context.record.get('cdplan'));
                        if(context.record.get('cdplan')+'x'!='x'&&_p21_clasif==_p21_TARIFA_LINEA&&_p21_smap1.LINEA_EXTENDIDA=='S')
                        {
                            _p21_estiloEditores(context.record.get('cdplan'));
                        }
                    }
                }
            })
        })
        ,buttonAlign : 'center'
        ,buttons     :
        [
            {
                text     : 'Continuar'
                ,icon    : '${ctx}/resources/fam3icons/icons/accept.png'
                ,handler : function()
                {
                	debug('_p21_editorAsisInter '+_p21_editorAsisInter.getValue());
                    _p21_setActiveConcepto();
                }
            }
        ]
    };
    
    var botonesModificada =
    [
        {
            tooltip  : 'Editar'
            ,icon    : '${ctx}/resources/fam3icons/icons/pencil.png'
            ,handler : _p21_editarGrupoClic
        }
    ];
    if(!_p21_ntramite || _cotcol_smap1.modificarTodo === true)
    {
        botonesModificada.push(
        {
            tooltip   : 'Borrar subgrupo'
            ,icon     : '${ctx}/resources/fam3icons/icons/delete.png'
            ,handler  : _p21_borrarGrupoClic
        });
    }
    if(_p21_smap1.ASEGURADOS=='S')
    {
        botonesModificada.push(
        {
            tooltip  : 'Asegurados'
            ,icon    : '${ctx}/resources/fam3icons/icons/group.png'
            ,handler : _cotcol_aseguradosClic
        });
    }
    if(_p21_smap1.EXTRAPRIMAS=='S')
    {
        botonesModificada.push(
        {
            tooltip  : 'Revisar extraprimas'
            ,icon    : '${ctx}/resources/fam3icons/icons/group_error.png'
            ,handler : _p21_revisarAseguradosClic
        });
    }
    _p21_tabGruposModifi =
    {
        title     : 'RESUMEN SUBGRUPOS'
        ,itemId   : '_p21_tabGruposModifi'
        ,defaults : { style : 'margin:5px' }
        ,border   : 0
        ,items    : Ext.create('Ext.grid.Panel',
        {
            tbar     :
            [
                {
                    text     : 'Agregar'
                    ,icon    : '${ctx}/resources/fam3icons/icons/add.png'
                    ,handler : _p21_agregarGrupoClic
                    ,hidden  : _p21_ntramite && _cotcol_smap1.modificarTodo === false ? true : false
                }
            ]
            ,columns :
            [
                {
                    header     : 'ID'
                    ,dataIndex : 'letra'
                    ,width     : 40
                }
                ,{
                    header     : 'Nombre'
                    ,dataIndex : 'nombre'
                    ,width     : 150
                    ,editor    : _p21_editorNombreGrupo
                }
                ,{
                    header     : 'Plan'
                    ,dataIndex : 'cdplan'
                    ,width     : 100
                    ,editor    : _p21_editorPlan
                    ,renderer  : function(v)
                    {
                        return rendererColumnasDinamico(v,'cdplan');
                    }
                }
                ,{
                    header     : 'Nombre del Plan'
                    ,dataIndex : 'dsplanl'
                    ,width     : 380
                    ,editor    : _p21_editorNombrePlan
                }
                ,{
                    header     : 'Paquete'
                    ,dataIndex : 'paquete'
                    ,width     : 120
                    ,editor    : _p21_editorPaquete
                    ,hidden    : _p21_smap1.cdramo!='1'
                    ,renderer  : function(v)
                    {
                        return rendererColumnasDinamico(v,'paquete');
                    }
                }
                ,{
                    header     : 'Suma asegurada'
                    ,dataIndex : 'ptsumaaseg'
                    ,width     : 120
                    ,editor    : _p21_editorSumaAseg
                    ,renderer  : function(v)
                    {
                        return rendererColumnasDinamico(v,'ptsumaaseg');
                    }
                }
                ,{
                    xtype         : 'actioncolumn'
                    ,sortable     : false
                    ,menuDisabled : true
                    ,width        : (botonesModificada.length*20) + 20
                    ,items        : botonesModificada
                }
            ]
            ,store   : _p21_storeGrupos
            ,height  : 250
            ,plugins : Ext.create('Ext.grid.plugin.RowEditing',
            {
                clicksToEdit  : 1
                ,errorSummary : true
                ,listeners    :
                {
                    beforeedit : function(editor,context)
                    {
                        if(!(
                            !_p21_ntramite||_p21_ntramiteVacio||(!Ext.isEmpty(_p21_smap1.sincenso)&&_p21_smap1.sincenso=='S')
                        ))
                        {
                            if (_cotcol_smap1.modificarTodo === false) {
                                if ([
                                        RolSistema.SuscriptorTecnico,
                                        RolSistema.SupervisorTecnico,
                                        RolSistema.SubdirectorSalud,
                                        RolSistema.DirectorSalud
                                    ].indexOf(_cotcol_smap1.cdsisrol) == -1) {
                                    return false;
                                }
                            }
                        }
                        debug('beforeedit clasif modif:',context.record.get('cdplan'));
                        _p21_editorSumaAseg.forceSelection=false;
                        _p21_editorSumaAseg.heredar(context.record.get('cdplan'));
                        if(context.record.get('cdplan')+'x'!='x'&&_p21_clasif==_p21_TARIFA_LINEA&&_p21_smap1.LINEA_EXTENDIDA=='S')
                        {
                            _p21_estiloEditores(context.record.get('cdplan'));
                        }
                    }/*,
                    edit: function(editor,context){
                    	var editedRecord = context.record;
                    	alert(editedRecord.get('cdplan'));
                    }*/
                }
            })
        })
        ,buttonAlign : 'center'
        ,buttons     :
        [
            {
                text     : 'Continuar'
                ,icon    : '${ctx}/resources/fam3icons/icons/accept.png'
                ,handler : function()
                {
                    _p21_setActiveConcepto();
                }
            }
        ]
    };
    ////// componentes //////
    
    ////// contenido //////
    _p_21_panelPrincipal = Ext.create('Ext.tab.Panel',
    {
        itemId     : '_p21_tabpanel'
        ,border    : 0
        ,items     :
        [
            {
                title       : 'CONCEPTO'
                ,itemId     : '_p21_tabConcepto'
                ,border     : 0
                ,defaults   : { style : 'margin:5px' }
                ,items      :
                [
                    Ext.create('Ext.form.Panel',
                    {
                        border : 0
                        ,xtype : 'form'
                        ,url   : _p21_urlSubirCenso
                        ,items :
                        [
                            {
                                xtype   : 'fieldset'
                                ,title  : '<span style="font:bold 14px Calibri;">DATOS DEL CONTRATANTE</span>'
                                ,hidden: (_p21_smap1.cdsisrol=='SUSCRIPTOR'&& (_p21_smap1.status-0==19 || _p21_smap1.status-0==21 || _p21_smap1.status-0==23) ) ? true :false
                                ,disabled : ( !Ext.isEmpty(_p21_smap1.estado) && _p21_smap1.estado == 'M' )   
                                ,layout :
                                {
                                    type     : 'table'
                                    ,columns : 2
                                }
                                ,items  : [ <s:property value="imap.itemsContratante" /> ]
                            },
                            Ext.create('Ext.panel.Panel',
					        {
					            itemId      : '_p29_clientePanel'
					            ,title      : 'DATOS DEL CONTRATANTE'
					            ,hidden: (_p21_smap1.cdsisrol=='SUSCRIPTOR'&& (_p21_smap1.status-0==19 || _p21_smap1.status-0==21 || _p21_smap1.status-0==23) ) ? false :true
					            ,height     : 400
					            ,autoScroll : true
					            ,tbar     :
					            [
					                {
					                    text     : 'Cambiar Contratante'
					                    ,icon    : '${ctx}/resources/fam3icons/icons/user_go.png'
					                    ,handler : function(){
											destruirContLoaderPersona();	 
											
				                            _fieldById('_p29_clientePanel').getLoader().destroy();
				                            
				                            _fieldById('_p29_clientePanel').loader = new Ext.ComponentLoader({
							                    url       : _p25_urlPantallaPersonas
							                    ,scripts  : true
							                    ,autoLoad : false
							                    ,ajaxOptions: {
							                            method: 'POST'
							                     }
							                });
							                
				                            _fieldById('_p29_clientePanel').getLoader().load({
										            params: {
										                'smap1.cdperson' : '',
										                'smap1.cdideper' : '',
										                'smap1.cdideext' : '',
										                'smap1.esSaludDanios' : 'S',
										                'smap1.polizaEnEmision': 'S',
										                'smap1.esCargaClienteNvo' : 'N' ,
										                'smap1.ocultaBusqueda' : 'S' ,
										                'smap1.cargaCP' : '',
										                'smap1.cargaTipoPersona' : '',
										                'smap1.cargaSucursalEmi' : _p21_smap1.cdunieco,
										                'smap1.activaCveFamiliar': 'N',
										                'smap1.modoRecuperaDanios': 'N',
										                'smap1.modoSoloEdicion': 'N',
										                'smap1.contrantantePrincipal': 'S',
										                'smap1.tomarUnDomicilio' : 'S'
										            }
										     });
										     
										     _fieldByName('cdperson').setValue('');
										     
										     _contratanteSaved = false;
										     
										     _callbackContPrincipal = function(json){
    	
										    	debug( 'Datos a cargar', json.smap1);
										    	
										    	_fieldByName('cdrfc')    .setValue(json.smap1.CDRFC);
										        _fieldByName('cdperson') .setValue(json.smap1.CDPERSON);
										        
										        codpostalDefinitivo = json.smap1.CDPOSTAL;
												cdedoDefinitivo = json.smap1.CDEDO;
												cdmuniciDefinitivo = json.smap1.CDMUNICI;
										        
												var params = {
												    smap1 :
												    {
												        cdunieco : _p21_smap1.cdunieco
												        ,cdramo  : _p21_smap1.cdramo
												        ,estado  : _p21_smap1.estado
												        ,nmpoliza: _p21_smap1.nmpoliza
												        ,nmsuplem: Ext.isEmpty(_p21_smap1.nmsuplem)?'0':_p21_smap1.nmsuplem
												        ,cdperson: json.smap1.CDPERSON
												        ,codpostal: json.smap1.CDPOSTAL
												        ,cdedo: json.smap1.CDEDO
												        ,cdmunici: json.smap1.CDMUNICI
												        ,nmorddom: json.smap1.NMORDDOM
												        ,confirmaEmision: 'S'
												    }
												};
											
												Ext.Ajax.request(
											        {
										            url       : _p21_urlGuardarContratanteColectivo
										            ,jsonData : params
										            ,success  : function(response)
										            {
										                var jsonCont = Ext.decode(response.responseText);
										                if(jsonCont.exito){
										                	mensajeCorrecto('Aviso','Contratante Guardado Correctamente.');
										                	_contratanteSaved = true;
										                }
										                else
										                {
										                    mensajeWarning(jsonCont.respuesta);
										                }
										            }
										            ,failure  : function()
										            {
										                errorComunicacion();
										            }
										        });
										        
											};
										     
							            }
					                    
					                }
					            ]
					            ,loader     :
					            {
					                url       : _p25_urlPantallaPersonas
				                    ,scripts  : true
				                    ,autoLoad : false
				                    ,ajaxOptions: {
				                            method: 'POST'
				                     }
				                }
					        })
                            ,{
                                xtype   : 'fieldset'
                                ,title  : '<span style="font:bold 14px Calibri;">INFORMACI&Oacute;N DEL RIESGO</span>'
                                ,itemId : '_p21_fieldsetRiesgo'
                                ,items  : [ <s:property value="imap.itemsRiesgo" /> ]
                            }
                            ,{
                                xtype     : 'fieldset'
                                ,title    : '<span style="font:bold 14px Calibri;">INFORMACI&Oacute;N DE LA P&Oacute;LIZA</span>'
                                ,defaults : { style : 'margin:5px;' }
                                ,layout   :
                                {
                                    type     : 'table'
                                    ,columns : 2
                                }
                                ,items    :
                                [
                                    Ext.create('Ext.form.field.Display',
                                    {
                                        fieldLabel  : 'PRODUCTO'
                                        ,labelWidth : 250
                                        ,value      : 'Cargando...'
                                    })
                                    ,Ext.create('Ext.form.field.Number',
                                    {
                                        fieldLabel : 'TR&Aacute;MITE'
                                        ,itemId    : '_p21_fieldNtramite'
                                        ,readOnly  : true
                                        ,name      : 'ntramite'
                                    })
                                    ,Ext.create('Ext.form.field.Number',
                                    {
                                        fieldLabel : 'COTIZACI&Oacute;N'
                                        ,itemId    : '_p21_fieldNmpoliza'
                                        ,readOnly  : true
                                        ,name      : 'nmpoliza'
                                    })
                                    ,Ext.create('Ext.form.field.Date',
                                    {
                                        format      : 'd/m/Y'
                                        ,fieldLabel : 'FECHA INICIO VIGENCIA'
                                        ,name       : 'feini'
                                        ,allowBlank : false
                                        ,value      : new Date()
                                        ,listeners  :
                                        {
                                             change : function(field,value)
                                             {
                                                 try
                                                 {
                                                     Ext.ComponentQuery.query('#fechaFinVigencia')[0].setValue(Ext.Date.add(value,Ext.Date.YEAR,1));
                                                 }
                                                 catch (e) {}
                                             }
                                        }
                                    })
                                    ,Ext.create('Ext.form.field.Date',
                                    {
                                        format      : 'd/m/Y'
                                        ,fieldLabel : 'FECHA FIN VIGENCIA'
                                        ,itemId     : 'fechaFinVigencia'
                                        ,name       : 'fefin'
                                        ,allowBlank : false
                                        ,readOnly   : true
                                        ,value      : Ext.Date.add(new Date(),Ext.Date.YEAR,1)
                                    })
                                    ,<s:property value="imap.comboFormaPago" />
                                    ,<s:property value="imap.comboRepartoPago" />
                                    ,{
                                        xtype       : 'numberfield'
                                        ,fieldLabel : 'PORCENTAJE A CARGO DEL CLIENTE'
                                        ,name       : 'pcpgocte'
                                        ,allowBlank : false
                                        ,minValue   : 0
                                        ,maxValue   : 100
                                        ,listeners  :
                                        {
                                            change : function(comp,val)
                                            {
                                                _fieldByName('pcpgotit').setValue(100-val);
                                            }
                                        }
                                    },{
                                        xtype       : 'numberfield'
                                        ,fieldLabel : 'PORCENTAJE A CARGO DEL TITULAR'
                                        ,name       : 'pcpgotit'
                                        ,readOnly   : true
                                    }
                                    ,<s:property value="imap.comboPool" />
                                    ,<s:property value="imap.datosPoliza" />
                                    ,{
                                        xtype    : 'button'
                                        ,text    : 'Exclusiones/Extraprimas (Cl&aacute;usulas)'
                                        ,icon    : '${ctx}/resources/fam3icons/icons/lock.png'
                                        ,hidden  : Ext.isEmpty(_p21_smap1.nmpoliza)
                                        ,handler : function(){ _p21_crearVentanaClausulas(); }
                                    }
                                ]
                            }
                            ,{
                                xtype   : 'fieldset'
                                ,title  : '<span style="font:bold 14px Calibri;">DATOS DEL AGENTE</span>'
                                ,itemId : '_p21_fieldsetDatosAgente'
                                ,items  : [ <s:property value="imap.itemsAgente" /> ]
                                ,layout :
                                {
                                    type     : 'table'
                                    ,columns : 2
                                }
                            }                        
                            ,{
                                xtype     : 'fieldset'
                                ,title    : '<span style="font:bold 14px Calibri;">NÚMERO DE ASEGURADOS</span>'
                                ,defaults : { style : 'margin:5px;' }
                                ,hidden   : _p21_ntramite ? true : false
                                ,items    :
                                [
                                    {
                                        xtype    : 'button'
                                        ,text    : 'De 10 a 49 asegurados'
                                        ,scale   : 'medium'
                                        ,handler : _p21_construirLinea
                                    }
                                    ,{
                                        xtype    : 'button'
                                        ,text    : '50 o más asegurados'
                                        ,scale   : 'medium'
                                        ,handler : _p21_construirModificada
                                    }
                                ]
                            }
                            ,{
                                xtype     : 'fieldset'
                                ,title    : '<span style="font:bold 14px Calibri;">CENSO</span>'
                                ,defaults : { style : 'margin:5px;' }
                                ,hidden   : _p21_ntramite&&_p21_smap1.sincenso!='S' && _cotcol_smap1.modificarTodo === false ? true : false
                                ,items    :
                                [
                                    {
                                        xtype        : 'fieldcontainer'
                                        ,fieldLabel  : 'Tipo de censo'
                                        ,defaultType : 'radiofield'
                                        ,defaults    : { style : 'margin : 5px;' }
                                        ,layout      : 'hbox'
                                        ,items       :
                                        [
                                            {
                                                boxLabel    : 'Por asegurado'
                                                ,name       : 'tipoCenso'
                                                ,inputValue : 'solo'
                                                ,checked    : true
                                            }
                                            ,{
                                                boxLabel    : 'Agrupado por edad'
                                                ,name       : 'tipoCenso'
                                                ,inputValue : 'grupo'
                                            }
                                        ]
                                    }
                                    ,{
                                        xtype       : 'filefield'
                                        ,fieldLabel : 'Censo de asegurados'
                                        ,name       : 'censo'
                                        ,buttonText : 'Examinar...'
                                        ,allowBlank : _p21_ntramite&&_p21_smap1.sincenso!='S' && _cotcol_smap1.modificarTodo === false ? true : false
                                        ,buttonOnly : false
                                        ,width      : 450
                                        ,cAccept    : ['xls','xlsx']
                                        ,msgTarget  : 'side'
                                        ,listeners  :
                                        {
                                            change : function(me)
                                            {
                                                var indexofPeriod = me.getValue().lastIndexOf("."),
                                                uploadedExtension = me.getValue().substr(indexofPeriod + 1, me.getValue().length - indexofPeriod).toLowerCase();
                                                if (!Ext.Array.contains(this.cAccept, uploadedExtension))
                                                {
                                                    centrarVentanaInterna(Ext.MessageBox.show(
                                                    {
                                                        title   : 'Error de tipo de archivo',
                                                        msg     : 'Extensiones permitidas: ' + this.cAccept.join(),
                                                        buttons : Ext.Msg.OK,
                                                        icon    : Ext.Msg.WARNING
                                                    }));
                                                    me.reset();
                                                }
                                            }
                                        }
                                    }
                                ]
                            }
                        ]
                        ,buttonAlign : 'center'
                        ,buttons:[{
                            xtype :'panel',
                            defaults : { style : 'margin:5px' },
                            border   : 0,
                            width    : 950,
                            ui       :'footer',
                            items    : [
                                <s:if test='%{getImap().get("botones")!=null}'>
                                    <s:property value="imap.botones" />,
                                </s:if>
                                {
                                    text     : 'Limpiar'
                                    ,icon    : '${ctx}/resources/fam3icons/icons/arrow_refresh.png'
                                    ,handler : _p21_cotizarNueva
                                    ,hidden  : _p21_ntramite ? true : false
                                }
                            ]
                        }]
                        /*,buttons     :
                        [
                            <s:if test='%{getImap().get("botones")!=null}'>
                                <s:property value="imap.botones" />,
                            </s:if>
                            {
                                text     : 'Limpiar'
                                ,icon    : '${ctx}/resources/fam3icons/icons/arrow_refresh.png'
                                ,handler : _p21_cotizarNueva
                                ,hidden  : _p21_ntramite ? true : false
                            }
                        ]*/
                    })
                ]
            }
        ]
        ,listeners :
        {
            tabchange : function(panel,newtab,oldtab,e)
            {
                var grids=Ext.ComponentQuery.query('[xtype=grid]',oldtab);
                debug('grids:',grids);
                if(grids.length>0)
                {
                    var conCambios = false;
                    $.each(grids,function(i,grid)
                    {
                        var records = grid.getStore().getModifiedRecords();
                        debug('modificados:',records.length);
                        if(grid.up('panel').title != 'RESUMEN SUBGRUPOS' && records.length > 0)
                        {
                            conCambios = true;
                            debug('cambios:',records);
                        }
                    });
                    if(conCambios)
                    {
                        mensajeWarning('Hay cambios pendientes sin guardar');
                    }
                }
            }
        }
    });
    
    Ext.create('Ext.panel.Panel',{
        renderTo : '_p21_divpri',
        itemId   : '_cotcol_paneltop',
        border   : 0,
        items    : [
            Ext.create('Ext.panel.Panel',
            {
                itemId       : '_cotcol_panelFlujo'
                ,title       : 'ACCIONES'
                ,hidden      : Ext.isEmpty(_cotcol_flujo)
                ,buttonAlign : 'left'
                ,buttons     : []
                ,style       : 'margin-bottom: 5px;'
                ,listeners   :
                {
                    afterrender : function(me)
                    {
                        if(!Ext.isEmpty(_cotcol_flujo))
                        {
                            _cargarBotonesEntidad(
                                _cotcol_flujo.cdtipflu
                                ,_cotcol_flujo.cdflujomc
                                ,_cotcol_flujo.tipoent
                                ,_cotcol_flujo.claveent
                                ,_cotcol_flujo.webid
                                ,me.itemId//callback
                                ,_cotcol_flujo.ntramite
                                ,_cotcol_flujo.status
                                ,_cotcol_flujo.cdunieco
                                ,_cotcol_flujo.cdramo
                                ,_cotcol_flujo.estado
                                ,_cotcol_flujo.nmpoliza
                                ,_cotcol_flujo.nmsituac
                                ,_cotcol_flujo.nmsuplem
                                ,null//callbackDespuesProceso
                            );
                        }
                    }
                }
            }),
            _p_21_panelPrincipal
        ]
    });
    
    _callbackContPrincipal = function(json){
    	
    	debug( 'Datos a cargar', json.smap1);
    	
    	_fieldByName('cdrfc')    .setValue(json.smap1.CDRFC);
        _fieldByName('cdperson') .setValue(json.smap1.CDPERSON);
        
		codpostalDefinitivo = json.smap1.CDPOSTAL;
		cdedoDefinitivo = json.smap1.CDEDO;
		cdmuniciDefinitivo = json.smap1.CDMUNICI;
		
		var params = {
		    smap1 :
		    {
		        cdunieco : _p21_smap1.cdunieco
		        ,cdramo  : _p21_smap1.cdramo
		        ,estado  : _p21_smap1.estado
		        ,nmpoliza: _p21_smap1.nmpoliza
		        ,nmsuplem: Ext.isEmpty(_p21_smap1.nmsuplem)?'0':_p21_smap1.nmsuplem
		        ,cdperson: json.smap1.CDPERSON
		        ,codpostal: json.smap1.CDPOSTAL
		        ,cdedo: json.smap1.CDEDO
		        ,cdmunici: json.smap1.CDMUNICI
		        ,nmorddom: json.smap1.NMORDDOM
		        ,confirmaEmision: 'S'
		    }
		};
	
		Ext.Ajax.request(
	        {
            url       : _p21_urlGuardarContratanteColectivo
            ,jsonData : params
            ,success  : function(response)
            {
                var jsonCont = Ext.decode(response.responseText);
                if(jsonCont.exito){
                	mensajeCorrecto('Aviso','Contratante Guardado Correctamente.');
                	_contratanteSaved = true;
                }
                else
                {
                    mensajeWarning(jsonCont.respuesta);
                }
            }
            ,failure  : function()
            {
                errorComunicacion();
            }
        });
        
	};
    
    if(_p21_smap1.VENTANA_DOCUMENTOS=='S'&&(_p21_ntramite||_p21_ntramiteVacio))
    {
        Ext.create('Ext.window.Window',
        {
            title           : 'Documentos del tr&aacute;mite ' + (_p21_ntramite||_p21_ntramiteVacio)
            ,ventanaDocu    : true
            ,closable       : false
            ,width          : 500
            ,height         : 300
            ,autoScroll     : true
            ,collapsible    : true
            ,titleCollapse  : true
            ,startCollapsed : true
            ,resizable      : false
            ,cls            : 'VENTANA_DOCUMENTOS_CLASS'
            ,loader         :
            {
                scripts   : true
                ,autoLoad : true
                ,url      : _p21_urlVentanaDocumentosClon
                ,params   :
                {
                    'smap1.cdunieco'  : _p21_smap1.cdunieco
                    ,'smap1.cdramo'   : _p21_smap1.cdramo
                    ,'smap1.estado'   : 'W'
                    ,'smap1.nmpoliza' : '0'
                    ,'smap1.nmsuplem' : '0'
                    ,'smap1.nmsolici' : '0'
                    ,'smap1.ntramite' : (_p21_ntramite||_p21_ntramiteVacio)
                    ,'smap1.tipomov'  : '0'
                }
            }
        }).showAt(500, 0);
    }
    ////// contenido //////
    
    ////// custom //////
    
    //recargo fraccionado
    var recargoCmp=_fieldByName('recargoPago');
    debug('@CUSTOM recargo:',recargoCmp);
    recargoCmp.bloqueo = false;
    recargoCmp.heredar = function()
    {
        var me             = _fieldByName('recargoPago');
        var cdperpagCmp    = _fieldByName('cdperpag');
        var recargoPersCmp = _fieldByName('recargoPers');
        if(recargoPersCmp.getValue()+'x'=='Nx')
        {
            me.hide();
            me.setValue(0);
        }
        else
        {
            me.show();
            if(!Ext.isEmpty(cdperpagCmp.getValue())&&me.bloqueo==false)
            {
                me.setLoading(true);
                Ext.Ajax.request(
                {
                    url     : _p21_urlRecuperacionSimple
                    ,params :
                    {
                        'smap1.procedimiento' : 'RECUPERAR_PORCENTAJE_RECARGO_POR_PRODUCTO'
                        ,'smap1.cdramo'       : _p21_smap1.cdramo
                        ,'smap1.cdperpag'     : cdperpagCmp.getValue()
                    }
                    ,success : function(response)
                    {
                        me.setLoading(false);
                        var json=Ext.decode(response.responseText);
                        debug('### pago frac:',json);
                        if(json.exito)
                        {
                            me.setValue(json.smap1.recargo);
                        }
                        else
                        {
                            mensajeError(json.respuesta);
                        }
                    }
                    ,failure : function()
                    {
                        me.setLoading(false);
                        errorComunicacion();
                    }
                });
            }
        }
    };
    
    //triggers
    _fieldByName('cdperpag').on(
    {
        change : function(){ _fieldByName('recargoPago').heredar(); }
    });
    _fieldByName('recargoPers').on(
    {
        change : function(){ _fieldByName('recargoPago').heredar(); }
    });
    //triggers
    //recargo fraccionado
    
    try
    {
        var botonAgentes = Ext.ComponentQuery.query('button[text=Agentes]')[0];
        botonAgentes.up().remove(botonAgentes,false);
        _fieldById('_p21_fieldsetDatosAgente').add(Ext.create('Ext.button.Button',
        {
            text     : botonAgentes.text
            ,icon    : botonAgentes.icon
            ,handler : botonAgentes.handler
            ,style   : 'margin:5px;'
        }));
        _fieldById('_p21_fieldsetDatosAgente').doLayout();
    }
    catch(e)
    {
        debugError('error inofensivo al querer mover boton de agentes',e);
    }
    
    
    try
    {
    	if(_p21_cdtipsup  == _TIPO_SITUACION_RENOVACION){
    		Ext.ComponentQuery.query('button[text=Exportar Censo]')[0].show();
    		Ext.ComponentQuery.query('button[text=Actualizar Censo]')[0].show();
    		Ext.ComponentQuery.query('button[text=Complementar]')[0].hide();
    	}else{
    		Ext.ComponentQuery.query('button[text=Exportar Censo]')[0].hide();
    		Ext.ComponentQuery.query('button[text=Actualizar Censo]')[0].hide();
    		Ext.ComponentQuery.query('button[text=Complementar]')[0].show();
    	}
    }
    catch(e)
    {
        debugError('error para la renovacion de la Poliza',e);
    }
    //codigo dinamico recuperado de la base de datos
    <s:property value="smap1.customCode" escapeHtml="false" />
    
    //_iceMostrar();
    ////// custom //////
    
    ////// loaders //////
    _fieldByName('cdrfc',_p_21_panelPrincipal).on(
    {
        'blur'    : _p21_rfcBlur
        ,'change' : function()
        {
            _fieldByName('cdperson',_p_21_panelPrincipal).reset();
            nmorddomProspecto = _defaultNmordomProspecto;
        }
    });
    
    _fieldByName('nombre',_p_21_panelPrincipal).on(
    {
        'select' : function()
        {
        	_fieldByName('cdperson',_p_21_panelPrincipal).setValue('');
            nmorddomProspecto = _defaultNmordomProspecto;
        }
    });
    
    _fieldByName('codpostal',_p_21_panelPrincipal).on(
    {
        'select' : function()
        {
        	_fieldByName('cdperson',_p_21_panelPrincipal).setValue('');
            nmorddomProspecto = _defaultNmordomProspecto;
        }
    });
    
    _fieldByName('cdedo',_p_21_panelPrincipal).on(
    {
        'select' : function()
        {
        	_fieldByName('cdperson',_p_21_panelPrincipal).setValue('');
            nmorddomProspecto = _defaultNmordomProspecto;
        }
    });
    
    _fieldByName('cdmunici',_p_21_panelPrincipal).on(
    {
        'select' : function()
        {
        	_fieldByName('cdperson',_p_21_panelPrincipal).setValue('');
            nmorddomProspecto = _defaultNmordomProspecto;
        }
    });
    
    if(_p21_smap1.BLOQUEO_CONCEPTO=='S')
    {
        var items  = Ext.ComponentQuery.query('[name]',_p21_tabConcepto());
        if(_p21_smap1.cdsisrol=='SUSCRIPTOR'&&_p21_smap1.status-0==18)
        {
            var items2 = [];
            for(var i in items)
            {
                if(items[i].name!='cdrfc'
                    &&items[i].name!='nombre'
//                  &&items[i].name!='codpostal' //Tampoco se permite editar el codigo postal para ser homologo con estado y municipio
                    &&items[i].name!='dsdomici'
                    &&items[i].name!='nmnumero'
                    &&items[i].name!='nmnumint'
                    )
                {
                    items2.push(items[i]);
                }
            }
            items = items2;
        }
        $.each(items,function(i,item)
        {
            item.setReadOnly(true);
        });
    }
    
    if(_p21_smap1.cdsisrol=='SUSCRIPTOR')
    {
        _fieldByName('cdreppag').setReadOnly(_p21_smap1.status-0>18);
        _fieldByName('pcpgocte').setReadOnly(_p21_smap1.status-0>18);
    }
    
    _fieldByName('cdformaseg').on(
    {
        select : function()
        {
            var val=Number(_fieldByName('cdformaseg').getValue());
            if(val==1)
            {
                _fieldByName('cdreppag').select('3');
                _fieldByName('cdreppag').fireEvent('select');
            }
            else if(val==2)
            {
                _fieldByName('cdreppag').select('1');
                _fieldByName('cdreppag').fireEvent('select');
            }
            else if(val==3)
            {
                _fieldByName('cdreppag').select('2');
                _fieldByName('cdreppag').fireEvent('select');
            }
        }
    });
    
    _fieldByName('cdreppag').on(
    {
        select : function()
        {
            var val=_fieldByName('cdreppag').getValue()-0;
            debug('reparto pago select valor:',val);
            if(val==1)
            {
                _fieldByName('pcpgocte').hide();
                _fieldByName('pcpgocte').setValue(100);
                _fieldByName('pcpgotit').hide();
                _fieldByName('pcpgotit').setValue(0);
            }
            else if(val==2)
            {
                _fieldByName('pcpgocte').show();
                _fieldByName('pcpgocte').setValue(50);
                _fieldByName('pcpgotit').show();
                _fieldByName('pcpgotit').setValue(50);
            }
            else
            {
                _fieldByName('pcpgocte').hide();
                _fieldByName('pcpgocte').setValue(0);
                _fieldByName('pcpgotit').hide();
                _fieldByName('pcpgotit').setValue(100);
            }
        }
    });
    
    if(_p21_smap1.BLOQUEO_EDITORES=='S')
    {
        _p21_editorNombreGrupo.readOnly=true;
        _p21_editorPlan.setReadOnly(true);
        _p21_editorSumaAseg.setReadOnly(true);
        _p21_editorAyudaMater.setReadOnly(true);
        _p21_editorAsisInter.setReadOnly(true);
        _p21_editorEmerextr.setReadOnly(true);
        _p21_editorDeducible.setReadOnly(true);
    }
    
    if(_p21_ntramiteVacio)
    {
        _fieldByName('ntramite').setValue(_p21_ntramiteVacio);
        _p21_tabpanel().setLoading(true);
        Ext.Ajax.request(
        {
            url     : _p21_urlRecuperarProspecto
            ,params :
            {
                'smap1.ntramite' : _p21_ntramiteVacio
            }
            ,success : function(response)
            {
                _p21_tabpanel().setLoading(false);
                var json = Ext.decode(response.responseText);
                debug('prospecto:',json);
                _fieldByName('nombre').setValue(json.smap1.NOMBRE);
            }
            ,failure : function()
            {
                _p21_tabpanel().setLoading(false);
                errorComunicacion();
            }
        });
    }
    else if(_p21_ntramite)
    {
        _p21_tabpanel().setLoading(true);
        _fieldByName('recargoPago').bloqueo=true;
        Ext.Ajax.request(
        {
            url     : _p21_urlCargarDatosCotizacion
            ,params :
            {
                'smap1.cdunieco'  : _p21_smap1.cdunieco
                ,'smap1.cdramo'   : _p21_smap1.cdramo
                ,'smap1.cdtipsit' : _p21_smap1.cdtipsit
                ,'smap1.estado'   : _p21_smap1.estado
                ,'smap1.nmpoliza' : _p21_smap1.nmpoliza
                ,'smap1.ntramite' : _p21_smap1.ntramite
            }
            ,success : function(response)
            {
                _p21_tabpanel().setLoading(false);
                var json=Ext.decode(response.responseText);
                debug('json response:',json);
                if(json.exito)
                {
                    for(var prop in json.params)
                    {
                        if(prop!='cdedo'&&prop!='cdmunici'&&prop!='clasif'&&prop!='swexiper'&&prop!='nmorddom')
                        {
                            if(prop=='pcpgocte')
                            {
                                var porc = json.params[prop];
                                debug('porcentaje pago cliente:',porc);
                                if(porc-0==100)
                                {
                                    _fieldByName('cdreppag').setValue('1');
                                    _fieldByName('pcpgocte').setValue('100');
                                    _fieldByName('pcpgocte').hide();
                                    _fieldByName('pcpgotit').hide();
                                    _fieldByName('cdreppag').getStore().on(
                                    {
                                        'load' : function()
                                        {
                                            _fieldByName('cdreppag').setValue('1');
                                            _fieldByName('pcpgocte').setValue('100');
                                            _fieldByName('pcpgocte').hide();
                                            _fieldByName('pcpgotit').hide();
                                            _fieldByName('pcpgocte').allowBlank=true;
                                            _fieldByName('pcpgocte').isValid();
                                        }
                                    });
                                }
                                else if(porc-0==0)
                                {
                                    _fieldByName('cdreppag').setValue('3');
                                    _fieldByName('pcpgocte').setValue('0');
                                    _fieldByName('pcpgocte').hide();
                                    _fieldByName('pcpgotit').hide();
                                    _fieldByName('cdreppag').getStore().on(
                                    {
                                        'load' : function()
                                        {
                                            _fieldByName('cdreppag').setValue('3');
                                            _fieldByName('pcpgocte').setValue('0');
                                            _fieldByName('pcpgocte').hide();
                                            _fieldByName('pcpgotit').hide();
                                        }
                                    });
                                }
                                else
                                {
                                    _fieldByName('cdreppag').setValue('2');
                                    _fieldByName('pcpgocte').setValue(porc);
                                    _fieldByName('cdreppag').getStore().on(
                                    {
                                        'load' : function()
                                        {
                                            _fieldByName('cdreppag').setValue('2');
                                            _fieldByName('pcpgocte').setValue(porc);
                                        }
                                    });
                                }
                            }
                            else
                            {
                                var item=_fieldByName(prop);
                                if(Ext.isEmpty(item.store))
                                {
                                    item.setValue(json.params[prop]);
                                }
                                else
                                {
                                    if(item.store.getCount()==0)
                                    {
                                        item.store.comboName = prop+'';
                                        item.forceSelection  = false;
                                        item.store.on(
                                        {
                                            load : function(store)
                                            {
                                                _fieldByName(store.comboName).forceSelection=true;
                                            }
                                        });
                                    }
                                    item.setValue(json.params[prop]);
                                }
                            }
                        }
                    }
                    _fieldByName('cdedo').heredar(true,function()
                    {
                        _fieldByName('cdedo').setValue(json.params['cdedo']);
                        _fieldByName('cdmunici').heredar(true,function()
                        {
                            _fieldByName('cdmunici').setValue(json.params['cdmunici']);
                        });
                    });
                    
                    
                    if(_p21_smap1.cdsisrol=='SUSCRIPTOR'&& (_p21_smap1.status-0==19 || _p21_smap1.status-0==21 || _p21_smap1.status-0==23)){
                    	var cargacdperson = _fieldByName('cdperson').getValue();
                    	
                    	_fieldById('_p29_clientePanel').loader.load(
			            {
			                params:
			                {
				                'smap1.cdperson' : (!Ext.isEmpty(json.params['swexiper']) && (json.params['swexiper'] == 'S' || json.params['swexiper'] == 's') && !Ext.isEmpty(cargacdperson))? cargacdperson : '',
				                'smap1.cdideper' : '',
				                'smap1.cdideext' : '',
				                'smap1.esSaludDanios' : 'S',
				                'smap1.polizaEnEmision': 'S',
				                'smap1.esCargaClienteNvo' : 'N' ,
				                'smap1.ocultaBusqueda' : 'S' ,
				                'smap1.cargaCP' : '',
				                'smap1.cargaTipoPersona' : '',
				                'smap1.cargaSucursalEmi' : _p21_smap1.cdunieco,
				                'smap1.activaCveFamiliar': 'N',
				                'smap1.modoRecuperaDanios': 'N',
				                'smap1.modoSoloEdicion': 'N',
				                'smap1.contrantantePrincipal': 'S',
				                'smap1.tomarUnDomicilio' : 'S',
	                    		'smap1.cargaOrdDomicilio' : (!Ext.isEmpty(json.params['swexiper']) && (json.params['swexiper'] == 'S' || json.params['swexiper'] == 's') && !Ext.isEmpty(cargacdperson))? json.params['nmorddom'] : ''
				            }
			            });
                    }
                    
                    _p21_clasif = json.params['clasif'];
                    debug('_p21_clasif:',_p21_clasif);
                    var auxCargarGrupos=function(callback)
                    {
                        _p21_tabpanel().setLoading(true);
                        Ext.Ajax.request(
                        {
                            url      : _p21_urlCargarGrupos
                            ,params  :
                            {
                                'smap1.cdunieco'  : _p21_smap1.cdunieco
                                ,'smap1.cdramo'   : _p21_smap1.cdramo
                                ,'smap1.estado'   : _p21_smap1.estado
                                ,'smap1.nmpoliza' : _p21_smap1.nmpoliza
                            }
                            ,callback : function()
                            {
                                _fieldByName('recargoPago').bloqueo=false;
                            }
                            ,success : function(response)
                            {
                                _p21_tabpanel().setLoading(false);
                                var json2=Ext.decode(response.responseText);
                                debug('json response:',json2);
                                if(json2.exito)
                                {
                                    callback(json2);
                                }
                                else
                                {
                                    mensajeError(json2.respuesta);
                                }
                            }
                            ,failure : function()
                            {
                                _p21_tabpanel().setLoading(false);
                                errorComunicacion();
                            }
                        });
                    };
                    if(_p21_clasif==_p21_TARIFA_LINEA&&_p21_smap1.LINEA_EXTENDIDA=='S')
                    {
                        debug('>cargar linea');
                        auxCargarGrupos(function(resp)
                        {
                            debug('>callback linea');
                            _p21_construirLinea(0,0,true);
                            _p21_setActiveConcepto();
                            var aux=resp.slist1.length;
                            var aux2=0;
                            _p21_tabpanel().setLoading(true);
                            for(var i=0;i<aux;i++)
                            {
                                Ext.Ajax.request(
                                {
                                    url      : _p21_urlObtenerDatosAdicionalesGrupo
                                    ,params  :
                                    {
                                        'smap1.cdunieco'  : _p21_smap1.cdunieco
                                        ,'smap1.cdramo'   : _p21_smap1.cdramo
                                        ,'smap1.estado'   : _p21_smap1.estado
                                        ,'smap1.nmpoliza' : _p21_smap1.nmpoliza
                                        ,'smap1.letra'    : resp.slist1[i].letra
                                        ,'smap1.i'        : i
                                    }
                                    ,success : function(response)
                                    {
                                        var datosAdic=Ext.decode(response.responseText);
                                        debug('datosAdic:',datosAdic);
                                        if(datosAdic.exito)
                                        {
                                            aux2=aux2+1;
                                            var grupo=new _p21_modeloGrupo(resp.slist1[datosAdic.smap1.i]);
                                            grupo.set('deducible' , datosAdic.params.DEDUCIBLE);
                                            grupo.set('asisinte'  , datosAdic.params.ASISINTE);
                                            grupo.set('emerextr'  , datosAdic.params.EMEREXTR);
                                            _p21_storeGrupos.add(grupo);
                                            _p21_storeGrupos.sort('letra','ASC');
                                            _p21_storeGrupos.commitChanges();
                                            if(aux2==aux)//tenemos todas las respuestas
                                            {
                                                _p21_tabpanel().setLoading(false);
                                            }
                                        }
                                        else
                                        {
                                            _p21_tabpanel().setLoading(false);
                                            mensajeError(datosAdic.respuesta);
                                        }
                                    }
                                    ,failure : function()
                                    {
                                        _p21_tabpanel().setLoading(false);
                                        errorComunicacion();
                                    }
                                });
                            }
                            debug('<callback linea');
                        });
                        debug('<cargar linea');
                    }
                    else
                    {
                        debug('>cargar modificada');
                        auxCargarGrupos(function(resp)
                        {
                            debug('>callback modificada');
                            if(_p21_clasif==_p21_TARIFA_LINEA)
                            {
                                _p21_construirLinea(0,0,true);
                                _p21_setActiveConcepto();
                            }
                            else
                            {
                                _p21_construirModificada(0,0,true);
                                _p21_setActiveConcepto();
                            }
                            var aux=resp.slist1.length;
                            var aux2=0;
                            debug('cargar:',aux);
                            _p21_tabpanel().setLoading(true);
                            if(aux==0)
                            {
                                mensajeError('No hay grupos para cargar');
                            }
                            for(var i=0;i<aux;i++)
                            {
                                Ext.Ajax.request(
                                {
                                    url      : _p21_urlObtenerTvalogarsGrupo
                                    ,params  :
                                    {
                                        'smap1.cdunieco'  : _p21_smap1.cdunieco
                                        ,'smap1.cdramo'   : _p21_smap1.cdramo
                                        ,'smap1.estado'   : _p21_smap1.estado
                                        ,'smap1.nmpoliza' : _p21_smap1.nmpoliza
                                        ,'smap1.letra'    : resp.slist1[i].letra
                                        ,'smap1.i'        : i
                                    }
                                    ,success : function(response)
                                    {
                                        var tvalogars=Ext.decode(response.responseText);
                                        debug('tvalogars:',tvalogars);
                                        if(tvalogars.exito)
                                        {
                                            aux2=aux2+1;
                                            debug('cargadas:',aux2);
                                            var grupo=new _p21_modeloGrupo(resp.slist1[tvalogars.smap1.i]);
                                            grupo.tvalogars=tvalogars.slist1;
                                            grupo.valido=true;
                                            _p21_storeGrupos.add(grupo);
                                            _p21_storeGrupos.sort('letra','ASC');
                                            _p21_storeGrupos.commitChanges();
                                            if(aux2==aux)//tenemos todas las respuestas
                                            {
                                                _p21_tabpanel().setLoading(false);
                                            }
                                        }
                                        else
                                        {
                                            _p21_tabpanel().setLoading(false);
                                            mensajeError(tvalogars.respuesta);
                                        }
                                    }
                                    ,failure : function()
                                    {
                                        _p21_tabpanel().setLoading(false);
                                        errorComunicacion();
                                    }
                                });
                            }
                            debug('<callback modificada');
                        });
                        debug('<cargar modificada');
                    }
                }
                else
                {
                     mensajeError(json.respuesta);
                }
            }
            ,failure : function()
            {
                _p21_tabpanel().setLoading(false);
                errorComunicacion();
            }
        });
        
        _p21_tbloqueo(false);
    }
    
    _fieldByName('nmnumero').regex = undefined;
    _fieldByName('nmnumint').regex = undefined;
    
    if(!_p21_ntramite)
    {
        Ext.Ajax.request(
        {
            url     : _p21_urlRecuperacionSimpleLista
            ,params :
            {
                'smap1.procedimiento' : 'RECUPERAR_VALORES_PANTALLA'
                ,'smap1.pantalla'     : 'COTIZACION_COLECTIVO'
                ,'smap1.cdramo'       : _p21_smap1.cdramo
                ,'smap1.cdtipsit'     : _p21_smap1.cdtipsit
            }
            ,success : function(response)
            {
                var json=Ext.decode(response.responseText);
                debug('### valores pantalla:',json);
                if(json.exito)
                {
                    for(var i in json.slist1)
                    {
                        var comp=_fieldByName(json.slist1[i].NAME,_fieldById('_p21_tabConcepto'));
                        if(comp)
                        {
                            if(Ext.isEmpty(comp.store))
                            {
                                comp.setValue(json.slist1[i].VALOR);
                            }
                            else
                            {
                                if(comp.store.getCount()==0)
                                {
                                    comp.valorInicial = json.slist1[i].VALOR;
                                    comp.store.padre  = comp;
                                    comp.store.on(
                                    {
                                        load : function(me)
                                        {
                                            me.padre.select(me.padre.findRecordByValue(me.padre.valorInicial));
                                            me.padre.fireEvent('select');
                                        }
                                    });
                                }
                                else
                                {
                                    comp.select(comp.findRecordByValue(json.slist1[i].VALOR));
                                    comp.fireEvent('select');
                                }
                            }
                        }
                    }
                }
                else
                {
                    mensajeError(json.respuesta);
                }
            }
            ,failure : function()
            {
                errorComunicacion();
            }
        });
    }
    
    try
    {
        if(_p21_ntramite&&_p21_smap1.sincenso!='S')
        {
            Ext.ComponentQuery.query('[text=Guardar sin censo]')[0].hide();
        }
    }
    catch(e)
    {}
    
    Ext.Ajax.request(
    {
        url      : _p21_urlCargarParametros
        ,params  :
        {
            'smap1.parametro' : 'TITULO_COTIZACION'
            ,'smap1.cdramo'   : _p21_smap1.cdramo
            ,'smap1.cdtipsit' : _p21_smap1.cdtipsit
        }
        ,success : function(response)
        {
            try
            {
                var json=Ext.decode(response.responseText);
                debug('### obtener rango años response:',json);
                if(json.exito)
                {
                    _fieldByLabel('PRODUCTO').setValue(json.smap1.P1VALOR);
                }
                else
                {
                    _fieldByLabel('PRODUCTO').hide();
                    debugError('No se encontro titulo de la cotizacion:',json.respuesta);
                }
            }
            catch(e)
            {
                debugError('error al obtener titulo de cotizacion:',e);
            }
        }
        ,failure : function()
        {
            _fieldByLabel('PRODUCTO').hide();
            errorComunicacion();
        }
    });
    
    Ext.Ajax.request(
    {
        url     : _p21_urlCargarParametros
        ,params :
        {
            'smap1.parametro' : 'COMP_LECT_RIESGO_COT_GRUP'
            ,'smap1.cdramo'   : _p21_smap1.cdramo
            ,'smap1.cdtipsit' : _p21_smap1.cdtipsit
            ,'smap1.clave4'   : _p21_smap1.cdsisrol
        }
        ,success : function(response)
        {
            var ck = 'Decodificando permisos de componentes de riesgo';
            try
            {
                var json = Ext.decode(response.responseText);
                debug('### permisos:',json);
                if(json.exito)
                {
                    for(var i=1;i<=13;i++)
                    {
                        var indice = 'P'+i+'VALOR';
                        var label  = json.smap1[indice];
                        if(!Ext.isEmpty(label))
                        {
                            //_fieldLikeLabel(json.smap1[indice],_fieldById('_p21_fieldsetRiesgo')).readOnly = true;
                            _fieldLikeLabel(json.smap1[indice],_fieldById('_p21_fieldsetRiesgo')).hide();
                        }
                    }
                }
                else
                {
                    debugError('Error inofensivo al obtener permisos de componentes de riesgo',json.respuesta);
                }
            }
            catch(e)
            {
                manejaException(e,ck);
            }
        }
        ,failure : function()
        {
            errorComunicacion(null,'Error al obtener permisos de componentes de riesgo');
        }
    });
    
    _fieldByName('cdpool').on(
    {
        focus : function(me)
        {
            me.forceSelection = false;
        }
    });
    ////// loaders //////
});

////// funciones //////
function _p21_agregarTabGrupos()
{
    debug('>_p21_agregarTabGrupos');
    _p21_storeGrupos.removeAll();
    
    if(_p21_tabGrupos)
    {
        debug('remove:',Ext.ComponentQuery.query('#'+_p21_tabGrupos.itemId)[0]);
        _p21_tabpanel().remove(Ext.ComponentQuery.query('#'+_p21_tabGrupos.itemId)[0],false);
    }
    
    if(_p21_clasif==_p21_TARIFA_LINEA)
    {
        _p21_tabGrupos = _p21_tabGruposLineal;
    }
    else
    {
        _p21_tabGrupos = _p21_tabGruposModifi;
    }
    
    _p21_agregarTab(_p21_tabGrupos);
    window.parent.scrollTo(0, 0);
    debug('<_p21_agregarTabGrupos');
}

function _p21_agregarTab(tab)
{
    debug('>_p21_agregarTab:',tab);
    _p21_tabpanel().add(tab);
    debug('active:',Ext.ComponentQuery.query('#'+tab.itemId)[0]);
    _p21_setActiveTab(tab.itemId);
    window.parent.scrollTo(0, 0);
    debug('<_p21_agregarTab');
}

function _p21_construirLinea(button,event,confirmado)
{
    debug('>_p21_construirLinea confirmado:',confirmado,'-dummy');
    var valido = true;
    
    //validar confirmacion
    if(valido)
    {
        valido=confirmado==true;
        if(!valido)
        {
            centrarVentanaInterna(Ext.MessageBox.confirm('Confirmar', 'Se borrarán los subgrupos existentes<br>¿Desea continuar?', function(btn)
            {
                if(btn === 'yes')
                {
                    _p21_construirLinea(button,event,true);
                }
            }));
        }
    }
    
    //agregar tab grupos
    if(valido)
    {
        _p21_quitarTabsDetalleGrupo();
        _p21_clasif = _p21_TARIFA_LINEA;
        _p21_agregarTabGrupos();
    }
    debug('<_p21_construirLinea');
}

function _p21_construirModificada(button,event,confirmado)
{
    debug('>_p21_construirModificada');
    var valido = true;
    
    //validar confirmacion
    if(valido)
    {
        valido=confirmado==true;
        if(!valido)
        {
            centrarVentanaInterna(Ext.MessageBox.confirm('Confirmar', 'Se borrarán los subgrupos existentes<br>¿Desea continuar?', function(btn)
            {
                if(btn === 'yes')
                {
                    _p21_construirModificada(button,event,true);
                }
            }));
        }
    }
    debug('<_p21_construirModificada');
    
    //agregar tab grupos
    if(valido)
    {
        _p21_quitarTabsDetalleGrupo();
        _p21_clasif = _p21_TARIFA_MODIFICADA;
        _p21_agregarTabGrupos();
    }
}

function _p21_tabpanel()
{
    return Ext.ComponentQuery.query('#_p21_tabpanel')[0];
}

function _p21_renombrarGrupos(sinBorrarPestanias)
{
    debug('>_p21_renombrarGrupos');
    if(!sinBorrarPestanias)
    {
        _p21_quitarTabsDetalleGrupo();
    }
    var letras=['1','2','3','4','5','6','7','8','9','91','92','93','94','95','96','97','98','99'];
    var i=0;
    _p21_storeGrupos.each(function(record)
    {
        record.set('letra',letras[i]);
        i=i+1;
    });
    debug('<_p21_renombrarGrupos');
}

function _p21_agregarGrupoClic()
{
    debug('>_p21_agregarGrupoClic');
    _p21_storeGrupos.add(new _p21_modeloGrupo({letra:'99'}));
    _p21_storeGrupos.sort('letra','ASC');
    _p21_renombrarGrupos(true);
    _p21_storeGrupos.commitChanges();
    debug('<_p21_agregarGrupoClic');
}

function _p21_borrarGrupoClic(grid,rowIndex)
{
    var record = grid.getStore().getAt(rowIndex);
    debug('>_p21_borrarGrupoClic:',record.data);
    centrarVentanaInterna(Ext.Msg.confirm('Borrar grupo','¿Desea borrar el grupo '+record.get('letra')+' y renombrar los grupos en orden alfabético?<br>Esta acci&oacute;n cerrará las pestañas de detalle de subgrupos y podría perder los cambios no guardados.',
    function(button){
        debug('clicked:',button);
        if(button=='yes')
        {
            _p21_storeGrupos.remove(record);
            _p21_renombrarGrupos();
        }
    }));
    debug('<_p21_borrarGrupoClic');
}

function _p21_editarGrupoClic(grid,rowIndex)
{
	var gridGrupos  = grid.up();
	var record      = grid.getStore().getAt(rowIndex);
	var recordGrupo = gridGrupos.getStore().getAt(rowIndex);
    
    
    debug('>_p21_editarGrupoClic:',record);
    
// var cdPlanParaQuitarPrim = record.get('cdplan');
// var quitarPrim =
// _p21_smap1.cdsisrol=='EJECUTIVOCUENTA'&&(cdPlanParaQuitarPrim=='PR'||cdPlanParaQuitarPrim=='PA');
    
    var valido = true;
    
    if(valido)
    {
        valido = !Ext.isEmpty(record.get('cdplan'));
        if(!valido)
        {
            mensajeWarning('Favor de seleccionar un plan para el subgrupo');
        }
    }

    if(valido)
    {
        valido = !Ext.isEmpty(record.get('ptsumaaseg'));
        if(!valido)
        {
            mensajeWarning('Favor de seleccionar una Suma Asegurada para el subgrupo');
        }
    }
    
    if(valido)
    {
        _p21_quitarTabsDetalleGrupo(record.get('letra'));
        _p21_tabpanel().setLoading(true);
        Ext.Ajax.request(
        {
            url      : _p21_urlObtenerCoberturasColec
            ,params  :
            {
                'smap1.cdramo'    : _p21_smap1.cdramo
                ,'smap1.cdtipsit' : _p21_smap1.cdtipsit
                ,'smap1.cdplan'   : record.get('cdplan')
                ,'smap1.cdsisrol' : _p21_smap1.cdsisrol
            }
            ,success : function(response)
            {
                _p21_tabpanel().setLoading(false);
                var json = Ext.decode(response.responseText);
                debug('json response:',json);
                if(json.exito)
                {
                    var numCoberturas=json.slist1.length;
                    debug('numCoberturas:',numCoberturas);
                    var contadorCoberturas=0;
                    _p21_tabpanel().setLoading(true);
                    for(var i=0;i<numCoberturas;i++)
                    {
                        Ext.Ajax.request(
                        {
                            url      : _p21_urlObtenerHijosCobertura
                            ,params  :
                            {
                                'smap1.cdramo'    : _p21_smap1.cdramo
                                ,'smap1.cdtipsit' : _p21_smap1.cdtipsit
                                ,'smap1.cdplan'   : record.get('cdplan')
                                ,'smap1.cdgarant' : json.slist1[i].CDGARANT
                                ,'smap1.indice'   : i
                                ,'smap1.cdtipsup' : _p21_cdtipsup
                                ,'smap1.nmpolant' : _p21_smap1.nmpolant
                                ,'smap1.cdgrupo'  : record.get('letra')
                            }
                            ,success : function(response)
                            {
                                var json2=Ext.decode(response.responseText);
                                debug('json response ['+json2.smap1.indice+']:',json2);
                                if(json2.exito)
                                {
                                    contadorCoberturas=contadorCoberturas+1;
                                    json.slist1[json2.smap1.indice]['hijos']       = Ext.decode(json2.respuesta.substr("items:".length));
                                    json.slist1[json2.smap1.indice]['modeloHijos'] = Ext.decode(json2.respuestaOculta.substr("fields:".length));
                                    if(contadorCoberturas==numCoberturas)
                                    {
                                        var items = [];
                                        for(var j=0;j<numCoberturas;j++)
                                        {
                                            var hijos = json.slist1[j].hijos;
                                            for(var k=0;k<hijos.length;k++)
                                            {
                                                var hijo = hijos[k];
                                                hijo.fieldLabel='['+hijo.cdatribu+'] '+hijo.fieldLabel;
                                                if(hijo.maxValue&&hijo.maxValue<999999999)
                                                {
                                                    var compara = function(me)
                                                    {
                                                        debug("Valor cdtipsup ==>",_p21_cdtipsup);
                                                        if(me.isDisabled())
                                                        {
                                                            if(_p21_cdtipsup == _TIPO_SITUACION_RENOVACION){
                                                            	me.removeCls('valorRenovacionColec');
                                                            }else{
                                                            	me.removeCls('valorNoOriginal');
                                                            }
                                                        }
                                                        else
                                                        {
                                                        	if(me.getValue()!=me.valorInicial)
                                                            {
                                                                if(_p21_cdtipsup == _TIPO_SITUACION_RENOVACION){
	                                                            	me.addCls('valorRenovacionColec');
	                                                            }else{
	                                                            	me.addCls('valorNoOriginal');
	                                                            }
                                                            }
                                                            else
                                                            {
                                                                if(_p21_cdtipsup == _TIPO_SITUACION_RENOVACION){
	                                                            	me.removeCls('valorRenovacionColec');
	                                                            }else{
	                                                            	me.removeCls('valorNoOriginal');
	                                                            }
                                                            }
                                                        }
                                                    };
                                                    hijo.on(
                                                    {
                                                        change   : compara
                                                        ,disable : compara
                                                        ,enable  : compara
                                                    });
                                                }
                                            }
                                            if(_p21_smap1.FACTORES_EN_COBERTURAS=='N'&&hijos&&hijos.length>0)
                                            {
                                                debug('se quitaran los factores de la cobertura');
                                                for(var k=0;k<hijos.length;k++)
                                                {
                                                    var hijo=hijos[k];
                                                    debug('revisando hijo:',"'"+hijo.fieldLabel+"'");
                                                    $.each(_p21_arrayNombresFactores,function(a,nombre)
                                                    {
                                                        if(hijo.fieldLabel.indexOf(nombre)>-1)
                                                        {
                                                            debug('ocultar:',"'"+hijo.fieldLabel+"'");
                                                            hijo.hidden     = true;
                                                            hijo.allowBlank = true;
                                                        }
                                                    });
                                                }
                                            }
                                            //para factores menor a cero
                                            if(hijos&&hijos.length>0)
                                            {
                                                debug('se pondran los factores menor a cero');
                                                for(var k=0;k<hijos.length;k++)
                                                {
                                                    var hijo=hijos[k];
                                                    debug('revisando hijo:',"'"+hijo.fieldLabel+"'");
                                                    $.each(_p21_arrayNombresFactores,function(a,nombre)
                                                    {
                                                        if(hijo.fieldLabel.indexOf(nombre)>-1)
                                                        {
                                                            debug('factor < 0:',hijo);
                                                            hijo.minValue=-100;
                                                        }
                                                    });
                                                }
                                            }
                                            debug('json.slist1[j]::', json.slist1[j]);
                                            debug('SWOBLIGA=' + json.slist1[j].SWOBLIGA + ', SWSELECCIONADO=' + json.slist1[j].SWSELECCIONADO + ', SWEDITABLE=' + json.slist1[j].SWEDITABLE);
                                            //para factores menor a cero
                                            var item = Ext.create('Ext.form.Panel',
                                            {
                                                width       : 470
                                                ,frame      : true
                                                ,height     : 140
                                                ,autoScroll : true
                                                ,defaults   : { style : 'margin:5px;', labelWidth : 100 }
                                                ,items      : hijos
                                                ,cdgarant   : json.slist1[j].CDGARANT
                                                ,tbar       :
                                                [
                                                    {
                                                        xtype       : 'displayfield'
                                                        ,fieldLabel : '<span style="color:white;">'+json.slist1[j].DSGARANT+'</span>'
                                                        ,labelWidth : 220
                                                    }
                                                    ,{
                                                        xtype       : 'checkbox' 
                                                        ,boxLabel   : 'Amparada'
                                                        ,name       : 'amparada'
                                                        ,inputValue : 'S'
                                                        ,checked    : json.slist1[j].SWOBLIGA == 'S' && (json.slist1[j].SWSELECCIONADO == 'S')
                                                        ,readOnly   : json.slist1[j].SWEDITABLE == 'N'
                                                        ,style      : 'color:white;'
                                                        ,listeners  :
                                                        {
                                                            boxready : function(checkbox)
                                                            {
                                                                var value=checkbox.getValue();
                                                                debug('checkbox boxready:',value);
                                                                var form = checkbox.up().up();
                                                                for(var l=0;l<form.items.items.length;l++)
                                                                {
                                                                    form.items.items[l].setDisabled(!value);
                                                                }
                                                            }
                                                            ,change : function(me,value)
                                                            {
                                                                debug('checkbox change:',value,me.up('form').cdgarant);
                                                                var form       = me.up('form');
                                                                var miCdgarant = form.cdgarant;
                                                                for(var l=0;l<form.items.items.length;l++)
                                                                {
                                                                    form.items.items[l].setDisabled(!value);
                                                                    if(value
                                                                        &&(Ext.isEmpty(me.flagPuedesBorrar)||me.flagPuedesBorrar==true)
                                                                    )
                                                                    {
                                                                        try
                                                                        {
                                                                            form.items.items[l].reset();
                                                                        }
                                                                        catch(e)
                                                                        {
                                                                            debugError('Error cachado:',e);
                                                                        }
                                                                    }
                                                                }
                                                                
                                                                //4MAT y 4AYM
                                                                if(_p21_smap1.cdsisrol!='COTIZADOR'&&value)
                                                                {
                                                                    try
                                                                    {
                                                                        if(miCdgarant=='4AYM'||miCdgarant=='4MAT')
                                                                        {
                                                                            var cmpAym;
                                                                            var cmpMat;
                                                                            if(miCdgarant=='4AYM')
                                                                            {
                                                                                cmpAym = me;
                                                                                cmpMat = me.up('form').up('panel').down('[cdgarant=4MAT]').down('checkbox');
                                                                            }
                                                                            else
                                                                            {
                                                                                cmpMat = me;
                                                                                cmpAym = me.up('form').up('panel').down('[cdgarant=4AYM]').down('checkbox');
                                                                            }
                                                                            debug('cmpAym:',cmpAym,'cmpMat:',cmpMat);
                                                                            if(cmpAym.getValue()&&cmpMat.getValue())
                                                                            {
                                                                                me.setValue(false);
                                                                                mensajeWarning('No se pueden amparar ambas coberturas (maternidad y ayuda en maternidad).');
                                                                            }
                                                                        }
                                                                    }
                                                                    catch(e)
                                                                    {
                                                                        debugError('error inofensivo al validar 4mat contra 4aym',e);
                                                                    }
                                                                }
                                                                
                                                                //4MED
                                                                if(_p21_smap1.cdsisrol!='COTIZADOR')
                                                                {
                                                                    try
                                                                    {
                                                                        if(miCdgarant=='4MED'||miCdgarant=='4MS')
                                                                        {
                                                                            var cmpMed;
                                                                            var cmpMs;
                                                                            if(miCdgarant=='4MED')
                                                                            {
                                                                                cmpMed = me;
                                                                                cmpMs  = me.up('form').up('panel').down('[cdgarant=4MS]').down('checkbox');
                                                                            }
                                                                            else
                                                                            {
                                                                                cmpMed = me.up('form').up('panel').down('[cdgarant=4MED]').down('checkbox');
                                                                                cmpMs  = me;
                                                                            }
                                                                            if(!cmpMs.getValue()&&cmpMed.getValue())
                                                                            {
                                                                                cmpMed.setValue(false);
                                                                                mensajeWarning('Se marc&oacute; como no amparada la cobertura MEDICAMENTOS porque depende de la cobertura MANTENIMIENTO DE LA SALUD');
                                                                            }
                                                                        }
                                                                    }
                                                                    catch(e)
                                                                    {
                                                                        debugError('error al validar dependencia de 4med ante 4ms',e);
                                                                    }
                                                                }
                                                                
                                                                //4SAD
                                                                if(_p21_smap1.cdsisrol!='COTIZADOR')
                                                                {
                                                                    try
                                                                    {
                                                                        if(miCdgarant=='4SAD'||miCdgarant=='4MS')
                                                                        {
                                                                            var cmpSad;
                                                                            var cmpMs;
                                                                            if(miCdgarant=='4SAD')
                                                                            {
                                                                                cmpSad = me;
                                                                                cmpMs  = me.up('form').up('panel').down('[cdgarant=4MS]').down('checkbox');
                                                                            }
                                                                            else
                                                                            {
                                                                                cmpSad = me.up('form').up('panel').down('[cdgarant=4SAD]').down('checkbox');
                                                                                cmpMs  = me;
                                                                            }
                                                                            if(!cmpMs.getValue()&&cmpSad.getValue())
                                                                            {
                                                                                cmpSad.setValue(false);
                                                                                mensajeWarning('Se marc&oacute; como no amparada la cobertura SERVICIOS AUXILIARES DIAGN&Oacute;STICO porque depende de la cobertura MANTENIMIENTO DE LA SALUD');
                                                                            }
                                                                        }
                                                                    }
                                                                    catch(e)
                                                                    {
                                                                        debugError('error al validar dependencia de 4sad ante 4ms',e);
                                                                    }
                                                                }
                                                                
                                                                //4EE
                                                                if(_p21_smap1.cdsisrol!='COTIZADOR')
                                                                {
                                                                    try
                                                                    {
                                                                        if(miCdgarant=='4HOS'||miCdgarant=='4EE')
                                                                        {
                                                                            var cmpHos;
                                                                            var cmpEe;
                                                                            if(miCdgarant=='4HOS')
                                                                            {
                                                                                cmpHos = me;
                                                                                cmpEe  = me.up('form').up('panel').down('[cdgarant=4EE]').down('checkbox');
                                                                            }
                                                                            else
                                                                            {
                                                                                cmpHos = me.up('form').up('panel').down('[cdgarant=4HOS]').down('checkbox');
                                                                                cmpEe  = me;
                                                                            }
                                                                            if(!cmpHos.getValue()&&cmpEe.getValue())
                                                                            {
                                                                                cmpEe.setValue(false);
                                                                                mensajeWarning('Se marc&oacute; como no amparada la cobertura EMERGENCIA EN EL EXTRANJERO porque depende de la cobertura HOSPITALIZACI&Oacute;N');
                                                                            }
                                                                        }
                                                                    }
                                                                    catch(e)
                                                                    {
                                                                        debugError('error al validar dependencia de 4ee ante 4hos',e);
                                                                    }
                                                                }
                                                                
                                                                //4MAT
                                                                if(_p21_smap1.cdsisrol!='COTIZADOR')
                                                                {
                                                                    try
                                                                    {
                                                                        if(miCdgarant=='4HOS'||miCdgarant=='4MAT')
                                                                        {
                                                                            var cmpHos;
                                                                            var cmpMat;
                                                                            if(miCdgarant=='4HOS')
                                                                            {
                                                                                cmpHos = me;
                                                                                cmpMat = me.up('form').up('panel').down('[cdgarant=4MAT]').down('checkbox');
                                                                            }
                                                                            else
                                                                            {
                                                                                cmpHos = me.up('form').up('panel').down('[cdgarant=4HOS]').down('checkbox');
                                                                                cmpMat = me;
                                                                            }
                                                                            if(!cmpHos.getValue()&&cmpMat.getValue())
                                                                            {
                                                                                cmpMat.setValue(false);
                                                                                mensajeWarning('Se marc&oacute; como no amparada la cobertura MATERNIDAD porque depende de la cobertura HOSPITALIZACI&Oacute;N');
                                                                            }
                                                                        }
                                                                    }
                                                                    catch(e)
                                                                    {
                                                                        debugError('error inofensivo al validar dependencia de 4mat ante 4hos',e);
                                                                    }
                                                                }
                                                                
                                                                //4AYM
                                                                if(_p21_smap1.cdsisrol!='COTIZADOR')
                                                                {
                                                                    try
                                                                    {
                                                                        if(miCdgarant=='4HOS'||miCdgarant=='4AYM')
                                                                        {
                                                                            var cmpHos;
                                                                            var cmpAym;
                                                                            if(miCdgarant=='4HOS')
                                                                            {
                                                                                cmpHos = me;
                                                                                cmpAym = me.up('form').up('panel').down('[cdgarant=4AYM]').down('checkbox');
                                                                            }
                                                                            else
                                                                            {
                                                                                cmpHos = me.up('form').up('panel').down('[cdgarant=4HOS]').down('checkbox');
                                                                                cmpAym = me;
                                                                            }
                                                                            if(!cmpHos.getValue()&&cmpAym.getValue())
                                                                            {
                                                                                cmpAym.setValue(false);
                                                                                mensajeWarning('Se marc&oacute; como no amparada la cobertura AYUDA EN MATERNIDAD porque depende de la cobertura HOSPITALIZACI&Oacute;N');
                                                                            }
                                                                        }
                                                                    }
                                                                    catch(e)
                                                                    {
                                                                        debugError('error inofensivo al validar dependencia de 4aym ante 4hos',e);
                                                                    }
                                                                }
                                                                
                                                                //4EAC
                                                                if(_p21_smap1.cdsisrol!='COTIZADOR')
                                                                {
                                                                    try
                                                                    {
                                                                        if(miCdgarant=='4HOS'||miCdgarant=='4EAC')
                                                                        {
                                                                            var cmpHos;
                                                                            var cmpEac;
                                                                            if(miCdgarant=='4HOS')
                                                                            {
                                                                                cmpHos = me;
                                                                                cmpEac = me.up('form').up('panel').down('[cdgarant=4EAC]').down('checkbox');
                                                                            }
                                                                            else
                                                                            {
                                                                                cmpHos = me.up('form').up('panel').down('[cdgarant=4HOS]').down('checkbox');
                                                                                cmpEac = me;
                                                                            }
                                                                            if(!cmpHos.getValue()&&cmpEac.getValue())
                                                                            {
                                                                                cmpEac.setValue(false);
                                                                                mensajeWarning('Se marc&oacute; como no amparada la cobertura EVENTO DE ALTO COSTO porque depende de la cobertura HOSPITALIZACI&Oacute;N');
                                                                            }
                                                                        }
                                                                    }
                                                                    catch(e)
                                                                    {
                                                                        debugError('error inofensivo al validar dependencia de 4eac ante 4hos',e);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    ,{
                                                        xtype       : 'displayfield'
                                                        ,fieldLabel : '<span style="color:white;">Buscar</span>'
                                                        ,labelWidth : 35
                                                    }
                                                    ,{
                                                        xtype       : 'textfield'
                                                        ,width      : 85
                                                        ,listeners  :
                                                        {
                                                            change : function(me)
                                                            {
                                                                var form=me.up('form');
                                                                debug('form:',form);
                                                                clearTimeout(_p21_filtroCobTimeout);
                                                                _p21_filtroCobTimeout = setTimeout(function()
                                                                {
                                                               Ext.Array.each(form.items.items,function(item)
                                                               {
                                                                   if(me.getValue()+'x'!='x')
                                                                   {
                                                                       var val=me.getValue().toLowerCase().replace(/s/g,'');
                                                                       if(item.fieldLabel.toLowerCase().replace(/s/g,'').lastIndexOf(val)!=-1)
                                                                       {
                                                                           //item.show();
                                                                           item.removeCls('tatrigarHide');
                                                                       }
                                                                       else
                                                                       {
                                                                           //item.hide();
                                                                           item.addCls('tatrigarHide');
                                                                       }
                                                                   }
                                                                   else
                                                                   {
                                                                       //item.show();
                                                                       item.removeCls('tatrigarHide');
                                                                   }
                                                               });
                                                               },1000); 
                                                            }
                                                        }
                                                    }
                                                    ,{
                                                        xtype  : 'hidden'
                                                        ,name  : 'swobliga'
                                                        ,value : json.slist1[j].SWOBLIGA
                                                    }
                                                    ,{
                                                        xtype  : 'hidden'
                                                        ,name  : 'cdgarant'
                                                        ,value : json.slist1[j].CDGARANT
                                                    }
                                                ]
                                            });
                                            if(record.valido)
                                            {
                                                debug('GRUPO VALIDO');
                                                Ext.define('_p21_modelo'+json.slist1[j].CDGARANT,
                                                {
                                                    extend  : 'Ext.data.Model'
                                                    ,fields : json.slist1[j].modeloHijos
                                                });
                                                tvalogars = record.tvalogars;
                                                tvalogar  = null;
                                                for(var k=0;k<tvalogars.length;k++)
                                                {
                                                    if(tvalogars[k].cdgarant==json.slist1[j].CDGARANT)
                                                    {
                                                        tvalogar = tvalogars[k];
                                                    }
                                                }
                                                var datosAnteriores = Ext.create('_p21_modelo'+json.slist1[j].CDGARANT,tvalogar);
                                                debug('Datos a cargar para '+json.slist1[j].CDGARANT+':',datosAnteriores,'con:',tvalogar);
                                                item.valido          = true;
                                                item.datosAnteriores = datosAnteriores;
                                            }
                                            else
                                            {
                                                debug('GRUPO NO VALIDO');
                                                item.valido = false;
                                            }
                                            items.push(item);
                                        }
                                        _p21_tabpanel().setLoading(false);
                                        grid.editingPlugin.cancelEdit();
                                        _p21_agregarTab(
                                        {
                                            title       : 'DETALLE DE SUBGRUPO '+record.get('letra')
                                            ,itemId     : 'id'+(new Date().getTime())
                                            ,letraGrupo : record.get('letra')
                                            ,tipo       : 'tabDetalleGrupo'
                                            ,defaults   : { style : 'margin:5px;' }
                                            ,items      :
                                            [
                                                Ext.create('Ext.panel.Panel',
                                                {
                                                    title        : 'COBERTURAS DEL SUBGRUPO'
                                                    ,maxHeight   : 490
                                                    ,hidden      : _p21_smap1.COBERTURAS=='N'
                                                    ,autoScroll  : true
                                                    ,collapsible : true
                                                    ,layout      :
                                                    {
                                                        type     : 'table'
                                                        ,columns : 2
                                                    }
                                                    ,defaults    : { style : 'margin:5px' }
                                                    ,items       : items
                                                })
                                                ,Ext.create('Ext.grid.Panel',
                                                {
                                                    title    : 'FACTORES DEL SUBGRUPO'
                                                    ,height  : 150
                                                    ,hidden  : _p21_smap1.FACTORES=='N'
                                                    ,plugins : Ext.create('Ext.grid.plugin.RowEditing',
                                                    {
                                                        clicksToEdit  : 1
                                                        ,errorSummary : false
                                                        ,listeners    :
                                                        {
                                                            beforeedit : function(editor)
                                                            {
                                                                centrarVentanaInterna(Ext.MessageBox.confirm('Confirmar', 'Al cambiar el factor de incremento de inflaci&oacute;n<br/>y/o el factor de extraprima o renovaci&oacute;n entonces<br/>se cambiar&aacute; para todas las coberturas<br/>¿Desea continuar?', function(btn)
                                                                {
                                                                    if(btn=='yes')
                                                                    {
                                                                        _p21_incrinflAux = record.get('incrinfl');
                                                                        _p21_extrrenoAux = record.get('extrreno');
                                                                    }
                                                                    else
                                                                    {
                                                                        editor.cancelEdit();
                                                                    }
                                                                }));
                                                            }
                                                            ,edit : function(editor,e)
                                                            {
                                                                var letraGrupo  = e.record.get('letra');
                                                                var incrinfl    = e.record.get('incrinfl');
                                                                var extrreno    = e.record.get('extrreno');
                                                                debug('letraGrupo:',letraGrupo);
                                                                debug('_p21_incrinflAux:',_p21_incrinflAux,'incrinfl:',incrinfl);
                                                                debug('_p21_extrrenoAux:',_p21_extrrenoAux,'extrreno:',extrreno);
                                                                if(_p21_incrinflAux!=incrinfl)
                                                                {
                                                                    var pestania=Ext.ComponentQuery.query('[letraGrupo='+letraGrupo+']')[0];
                                                                    debug('pestania:',pestania);
                                                                    $.each(_p21_arrayNombresIncrinfl,function(i,nombre)
                                                                    {
                                                                        var componentes=Ext.ComponentQuery.query('[fieldLabel*='+nombre+']',pestania);
                                                                        debug('componentes para poner factor incrinfl:',componentes);
                                                                        $.each(componentes,function(i,comp)
                                                                        {
                                                                            debug('poniendo valor en:',comp);
                                                                            comp.setValue(incrinfl);
                                                                        });
                                                                    });
                                                                }
                                                                if(_p21_extrrenoAux!=extrreno)
                                                                {
                                                                    var pestania=Ext.ComponentQuery.query('[letraGrupo='+letraGrupo+']')[0];
                                                                    debug('pestania:',pestania);
                                                                    $.each(_p21_arrayNombresExtrreno,function(i,nombre)
                                                                    {
                                                                        var componentes=Ext.ComponentQuery.query('[fieldLabel*='+nombre+']',pestania);
                                                                        debug('componentes para poner factor extrreno:',componentes);
                                                                        $.each(componentes,function(i,comp)
                                                                        {
                                                                            debug('poniendo valor en:',comp);
                                                                            comp.setValue(extrreno);
                                                                        });
                                                                    });
                                                                }
                                                            }
                                                        }
                                                    })
                                                    ,columns :
                                                    [
                                                        {
                                                            header     : 'Extraprima o<br/>renovaci&oacute;n'
                                                            ,dataIndex : 'extrreno'
                                                            ,flex      : 1
                                                            ,editor    :
                                                            {
                                                                xtype             : 'numberfield'
                                                                ,allowBlank       : false
                                                                ,allowDecimals    : true
                                                                ,decimalSeparator : '.'
                                                            }
                                                        }
                                                        ,{
                                                            header     : 'Incremento<br/>inflaci&oacute;n'
                                                            ,dataIndex : 'incrinfl'
                                                            ,flex      : 1
                                                            ,editor    :
                                                            {
                                                                xtype             : 'numberfield'
                                                                ,allowBlank       : false
                                                                ,allowDecimals    : true
                                                                ,decimalSeparator : '.'
                                                            }
                                                        }
                                                        ,{
                                                            header     : 'Ponderaci&oacute;n ubicaci&oacute;n<br/>geogr&aacute;fica'
                                                            ,dataIndex : 'pondubic'
                                                            ,flex      : 1
                                                            ,editor    :
                                                            {
                                                                xtype             : 'numberfield'
                                                                ,allowBlank       : false
                                                                ,allowDecimals    : true
                                                                ,decimalSeparator : '.'
                                                            }
                                                        }
                                                        ,{
                                                            header     : 'Descuento bonos<br/>incentivos'
                                                            ,dataIndex : 'descbono'
                                                            ,flex      : 1
                                                            ,editor    :
                                                            {
                                                                xtype             : 'numberfield'
                                                                ,allowBlank       : false
                                                                ,allowDecimals    : true
                                                                ,decimalSeparator : '.'
                                                            }
                                                        }
                                                        ,{
                                                            header     : 'Gastos de<br/>administraci\u00f3n'
                                                            ,dataIndex : 'gastadmi'
                                                            ,flex      : 1
                                                            ,editor    :
                                                            {
                                                                xtype             : 'numberfield'
                                                                ,allowBlank       : false
                                                                ,allowDecimals    : true
                                                                ,decimalSeparator : '.'
                                                            }
                                                        }
                                                        ,{
                                                            header     : 'Utilidad'
                                                            ,dataIndex : 'utilidad'
                                                            ,flex      : 1
                                                            ,editor    :
                                                            {
                                                                xtype             : 'numberfield'
                                                                ,allowBlank       : false
                                                                ,allowDecimals    : true
                                                                ,decimalSeparator : '.'
                                                            }
                                                        }
                                                        ,{
                                                            header     : 'Comisi\u00f3n<br/>del agente'
                                                            ,dataIndex : 'comiagen'
                                                            ,flex      : 1
                                                            ,editor    :
                                                            {
                                                                xtype             : 'numberfield'
                                                                ,allowBlank       : false
                                                                ,allowDecimals    : true
                                                                ,decimalSeparator : '.'
                                                            }
                                                        }
                                                        ,{
                                                            header     : 'Comisi\u00f3n<br/>del promotor'
                                                            ,dataIndex : 'comiprom'
                                                            ,flex      : 1
                                                            ,editor    :
                                                            {
                                                                xtype             : 'numberfield'
                                                                ,allowBlank       : false
                                                                ,allowDecimals    : true
                                                                ,decimalSeparator : '.'
                                                            }
                                                        }
                                                        ,{
                                                            header     : 'Bonos e<br/>incentivos'
                                                            ,dataIndex : 'bonoince'
                                                            ,flex      : 1
                                                            ,editor    :
                                                            {
                                                                xtype             : 'numberfield'
                                                                ,allowBlank       : false
                                                                ,allowDecimals    : true
                                                                ,decimalSeparator : '.'
                                                            }
                                                        }
                                                        ,{
                                                            header     : 'Otros gastos de<br/>adquisici\u00f3n'
                                                            ,dataIndex : 'otrogast'
                                                            ,flex      : 1
                                                            ,editor    :
                                                            {
                                                                xtype             : 'numberfield'
                                                                ,allowBlank       : false
                                                                ,allowDecimals    : true
                                                                ,decimalSeparator : '.'
                                                            }
                                                        }
                                                    ]
                                                    ,store : Ext.create('Ext.data.Store',
                                                    {
                                                        model : '_p21_modeloGrupo'
                                                        ,data : record
                                                    })
                                                    ,listeners :
                                                    {
                                                        afterrender : function()
                                                        {
                                                            //2016-01-06a se cargan factores siempre
                                                            //if(_p21_smap1.FACTORES=='S'&&Ext.isEmpty(record.get('extrreno')))
                                                            if(Ext.isEmpty(record.get('extrreno')))
                                                            //2016-01-06a se cargan factores siempre
                                                            {
                                                                var ponerValoresFactores=function()
                                                                {
                                                                    debug('_p21_valoresFactores:',_p21_valoresFactores);
                                                                    var letraGrupo = record.get('letra');
                                                                    var pestania   = Ext.ComponentQuery.query('[letraGrupo='+letraGrupo+']')[0];
                                                                    for(var i in _p21_valoresFactores)
                                                                    {
                                                                        var elem  = _p21_valoresFactores[i];
                                                                        var name  = elem.NAME;
                                                                        var valor = elem.VALOR;
                                                                        record.set(name,valor);
                                                                    }
                                                                };
                                                                if(Ext.isEmpty(_p21_valoresFactores))
                                                                {
                                                                    Ext.Ajax.request(
                                                                    {
                                                                        url     : _p21_urlRecuperacionSimpleLista
                                                                        ,params :
                                                                        {
                                                                            'smap1.procedimiento' : 'RECUPERAR_VALORES_ATRIBUTOS_FACTORES'
                                                                            ,'smap1.cdramo'       : _p21_smap1.cdramo
                                                                            ,'smap1.cdtipsit'     : _p21_smap1.cdtipsit
                                                                        }
                                                                        ,success : function(response)
                                                                        {
                                                                            var jsonValfac=Ext.decode(response.responseText);
                                                                            debug('### valores factores:',jsonValfac);
                                                                            if(jsonValfac.exito)
                                                                            {
                                                                                _p21_valoresFactores=jsonValfac.slist1;
                                                                                ponerValoresFactores();
                                                                            }
                                                                            else
                                                                            {
                                                                                mensajeError(jsonValfac.respuesta);
                                                                            }
                                                                        }
                                                                        ,failure : function()
                                                                        {
                                                                            errorComunicacion();
                                                                        }
                                                                    });
                                                                }
                                                                else
                                                                {
                                                                    ponerValoresFactores();
                                                                }
                                                            }
                                                        }
                                                    }
                                                })
                                                ,Ext.create('Ext.grid.Panel',
                                                {
                                                    title      : 'TARIFA POR EDADES'
                                                    ,minHeight : 100
                                                    ,hidden    : (_p21_ntramite && _cotcol_smap1.modificarTodo === false ? false : true)
                                                        || RolSistema.Agente === _p21_smap1.cdsisrol
                                                    ,maxHeight : 250
                                                    ,tbar      :
                                                    [
                                                        {
                                                            text     : 'Ver conceptos globales'
                                                            ,icon    : '${ctx}/resources/fam3icons/icons/money_dollar.png'
                                                            ,hidden  : '|COTIZADOR|SUPTECSALUD|SUBDIRSALUD|DIRECSALUD|'.indexOf('|'+_p21_smap1.cdsisrol+'|')==-1
                                                            ,handler : function(){ _p21_generarVentanaVistaPrevia(true); }
                                                        }
                                                    ]
                                                    ,store     : Ext.create('Ext.data.Store',
                                                    {
                                                        model     : '_p21_modeloTarifaEdad'
                                                        ,autoLoad : _p21_ntramite ? true : false
                                                        ,proxy    :
                                                        {
                                                            type         : 'ajax'
                                                            ,timeout     : 1000*60*2
                                                            ,extraParams :
                                                            {
                                                                'smap1.cdunieco'  : _p21_smap1.cdunieco
                                                                ,'smap1.cdramo'   : _p21_smap1.cdramo
                                                                ,'smap1.estado'   : _p21_smap1.estado
                                                                ,'smap1.nmpoliza' : _p21_smap1.nmpoliza
                                                                ,'smap1.nmsuplem' : '0'
                                                                ,'smap1.cdplan'   : record.get('cdplan')
                                                                ,'smap1.cdgrupo'  : record.get('letra')
                                                                ,'smap1.cdperpag' : _fieldByName('cdperpag').getValue()
                                                            }
                                                            ,url         : _p21_urlObtenerTarifaEdad
                                                            ,reader      :
                                                            {
                                                                type  : 'json'
                                                                ,root : 'slist1'
                                                            }
                                                        }
                                                    })
                                                    ,columns   :
                                                    [
                                                        {
                                                            header     : 'Edad'
                                                            ,width     : 60
                                                            ,dataIndex : 'EDAD'
                                                        }
                                                        ,{
                                                            header     : 'No. Hombres'
                                                            ,width     : 100
                                                            ,dataIndex : 'HOMBRES'
                                                        }
                                                        ,{
                                                            header     : 'No. Mujeres'
                                                            ,width     : 100
                                                            ,dataIndex : 'MUJERES'
                                                        }
                                                        ,{
                                                            header     : 'Tarifa por hombre'
                                                            ,flex      : 1
                                                            ,dataIndex : 'TARIFA_UNICA_HOMBRES'
                                                            ,renderer  : Ext.util.Format.usMoney
                                                        }
                                                        ,{
                                                            header     : 'Tarifa por mujer'
                                                            ,flex      : 1
                                                            ,dataIndex : 'TARIFA_UNICA_MUJERES'
                                                            ,renderer  : Ext.util.Format.usMoney
                                                        }
                                                        ,{
                                                            header     : 'Total hombres'
                                                            ,flex      : 1
                                                            ,dataIndex : 'TARIFA_TOTAL_HOMBRES'
                                                            ,renderer  : Ext.util.Format.usMoney
                                                        }
                                                        ,{
                                                            header     : 'Total mujeres'
                                                            ,flex      : 1
                                                            ,dataIndex : 'TARIFA_TOTAL_MUJERES'
                                                            ,renderer  : Ext.util.Format.usMoney
                                                        }
                                                    ]
                                                })
                                                ,Ext.create('Ext.grid.Panel',
                                                {
                                                    title      : 'PRIMA PROMEDIO'
                                                    ,minHeight : 100
                                                    ,hidden    : (_p21_ntramite && _cotcol_smap1.modificarTodo === false ? false : true)
                                                        || RolSistema.Agente === _p21_smap1.cdsisrol
                                                    ,maxHeight : 250
                                                    ,store     : Ext.create('Ext.data.Store',
                                                    {
                                                        model     : '_p21_modeloTarifaCobertura'
                                                        ,autoLoad : _p21_ntramite ? true : false
                                                        ,proxy    :
                                                        {
                                                            type         : 'ajax'
                                                            ,extraParams :
                                                            {
                                                                'smap1.cdunieco'  : _p21_smap1.cdunieco
                                                                ,'smap1.cdramo'   : _p21_smap1.cdramo
                                                                ,'smap1.estado'   : _p21_smap1.estado
                                                                ,'smap1.nmpoliza' : _p21_smap1.nmpoliza
                                                                ,'smap1.nmsuplem' : '0'
                                                                ,'smap1.cdplan'   : record.get('cdplan')
                                                                ,'smap1.cdgrupo'  : record.get('letra')
                                                                ,'smap1.cdperpag' : _fieldByName('cdperpag').getValue()
                                                            }
                                                            ,url         : _p21_urlObtenerTarifaCobertura
                                                            ,reader      :
                                                            {
                                                                type  : 'json'
                                                                ,root : 'slist1'
                                                            }
                                                        }
                                                    })
                                                    ,columns   :
                                                    [
                                                        {
                                                            header           : 'Cobertura'
                                                            ,dataIndex       : 'DSGARANT'
                                                            ,flex            : 3
                                                            ,summaryType     : 'count'
                                                            ,summaryRenderer : function(value, summaryData, dataIndex) 
                                                            {
                                                                return Ext.String.format('Total de {0} cobertura{1}', value, value !== 1 ? 's' : '');
                                                            }
                                                        }
                                                        ,{
                                                            header       : 'Prima'
                                                            ,flex        : 1
                                                            ,dataIndex   : 'PRIMA_PROMEDIO'
                                                            ,renderer    : Ext.util.Format.usMoney
                                                            ,summaryType : 'sum'
                                                        }
                                                    ]
                                                    ,features  :
                                                    [
                                                        {
                                                            ftype: 'summary'
                                                        }
                                                    ]
                                                })
                                            ]
                                            ,buttonAlign : 'center'
                                            ,buttons     :
                                            [
                                                {
                                                    text     : 'Guardar'
                                                    ,icon    : '${ctx}/resources/fam3icons/icons/disk.png'
                                                    ,handler : function(button){
                                                    	
                                                    	_p21_guardarGrupo(button.up().up(), gridGrupos, recordGrupo, rowIndex);
                                                    	}
                                                    ,hidden  : _p21_smap1.COBERTURAS=='N'||_p21_smap1.COBERTURAS_BOTON=='N'
                                                }
                                            ]
                                        });
                                        var formsValidos = Ext.ComponentQuery.query('[valido=true]');
                                        debug('formsValidos:',formsValidos);
                                        for(var k=0;k<formsValidos.length;k++)
                                        {
                                            var form = formsValidos[k];
                                            debug('intento cargar:',form,'con:',form.datosAnteriores);
                                            //form.loadRecord(form.datosAnteriores);
                                            
                                            for(var attCampo in form.datosAnteriores.data)
                                            {
                                                var attCampoCampo = form.down('[name='+attCampo+']');
                                                if(!Ext.isEmpty(attCampoCampo))
                                                {
                                                    attCampoCampo.setValue(form.datosAnteriores.get(attCampo));
                                                    if(attCampoCampo.xtype=='combobox')
                                                    {
                                                        attCampoCampo.store.valorParaCargar = ''+form.datosAnteriores.get(attCampo);
                                                        attCampoCampo.store.padre = attCampoCampo;
                                                        attCampoCampo.store.on(
                                                        {
                                                            load : function(me)
                                                            {
                                                                me.padre.setValue(me.valorParaCargar);
                                                            }
                                                        });
                                                    }
                                                }
                                            }
                                            
                                            if(form.datosAnteriores.raw&&form.datosAnteriores.raw.amparada=='S')
                                            {
                                                form.down('[name=amparada]').flagPuedesBorrar = false;
                                                form.down('[name=amparada]').setValue(true);
                                                debug('se "checkeo" el box');
                                                form.down('[name=amparada]').flagPuedesBorrar = true;
                                            }
                                            else
                                            {
                                                form.down('[name=amparada]').setValue(false);
                                                debug('se "descheckeo" el box');
                                            }
                                            debug('cargado:',form);
                                        }
                                    }
                                }
                                else
                                {
                                    _p21_tabpanel().setLoading(false);
                                    mensajeError(json2.respuesta);
                                }
                            }
                            ,failure : function()
                            {
                                _p21_tabpanel().setLoading(false);
                                errorComunicacion();
                            }
                        });
                    }
                }
                else
                {
                    mensajeError(json.respuesta);
                }
            }
            ,failure : function()
            {
                _p21_tabpanel().setLoading(false);
                errorComunicacion();
            }
        });
    }
    
    debug('<_p21_editarGrupoClic');
}

function _p21_quitarTabExtraprima(letra)
{
    debug('>_p21_quitarTabExtraprima letra:',letra);
    var tabsExtraprima = Ext.ComponentQuery.query('[extraprimaLetraGrupo='+letra+']');
    if(tabsExtraprima.length>0)
    {
        for(var i=0;i<tabsExtraprima.length;i++)
        {
            tabsExtraprima[i].destroy();
        }
    }
    debug('<_p21_quitarTabExtraprima');
}

function _p21_quitarTabAsegurados(letra)
{
    debug('>_p21_quitarTabAsegurados letra:',letra);
    var tabsAsegurados = Ext.ComponentQuery.query('[aseguradosLetraGrupo='+letra+']');
    if(tabsAsegurados.length>0)
    {
        for(var i=0;i<tabsAsegurados.length;i++)
        {
            tabsAsegurados[i].destroy();
        }
    }
    debug('<_p21_quitarTabAsegurados');
}

function _p21_quitarTabsDetalleGrupo(letraGrupo)
{
    debug('>_p21_quitarTabsDetalleGrupo:',letraGrupo,'dummy');
    if(!letraGrupo)
    {
        var tabsDetalleGrupo=Ext.ComponentQuery.query('[tipo=tabDetalleGrupo]');
    }
    else
    {
        var tabsDetalleGrupo=Ext.ComponentQuery.query('[letraGrupo='+letraGrupo+']');
    }
    if(tabsDetalleGrupo.length>0)
    {
        for(var i=0;i<tabsDetalleGrupo.length;i++)
        {
            tabsDetalleGrupo[i].destroy();
        }
    }
    debug('<_p21_quitarTabsDetalleGrupo');
}

function _p21_guardarGrupo(panelGrupo, gridGrupos, recordGrupoEdit, rowIndex)
{
     debug('>_p21_guardarGrupo:',panelGrupo);
     
     var letraGrupo  = panelGrupo.letraGrupo;
     debug('letraGrupo:',letraGrupo);
     
     var formsTatrigar = panelGrupo.down('[title=COBERTURAS DEL SUBGRUPO]').items.items;
     debug('formsTatrigar:',formsTatrigar);
     
     var tvalogars = [];
     var valido    = true;
     if(_p21_clasif==_p21_TARIFA_MODIFICADA||_p21_smap1.LINEA_EXTENDIDA=='N')
     {
         for(var i=0;i<formsTatrigar.length;i++)
         {
             var iFormTatrigar = formsTatrigar[i];
             valido            = valido && iFormTatrigar.isValid();
             var tvalogar      = iFormTatrigar.getValues();
             if(false)//tvalogar.swobliga=='S'&&_p21_smap1.cdsisrol!='COTIZADOR')
             {
                 tvalogar['amparada']='S';
             }
             else
             {
                 if(!tvalogar.amparada)
                 {
                     tvalogar['amparada']='N';
                 }
             }
             tvalogars.push(tvalogar);
         }
         if(!valido)
         {
             datosIncompletos();
         }
     }
     
     if(valido)
     {
         debug('tvalogars:',tvalogars);
         var recordGrupo=_p21_obtenerGrupoPorLetra(letraGrupo);
         recordGrupo['tvalogars'] = tvalogars;
         recordGrupo['valido']    = true;
         debug('recordGrupo:',recordGrupo);
         if(_p21_smap1.FACTORES=='S')
         {
             var storeFactores = panelGrupo.down('grid[title=FACTORES DEL SUBGRUPO]').getStore();
             debug('storeFactores:',storeFactores);
             storeFactores.commitChanges();
         }
         
         if(_p21_clasif == _p21_TARIFA_MODIFICADA){
         
	         var smap1p = {
	        		 cdramo  : _p21_smap1.cdramo,
	        		 cdtipsit: _p21_smap1.cdtipsit,
	        		 cdplan  : recordGrupoEdit.get('cdplan')
	         };
	         
	         var params = {
	        		 slist1 : tvalogars,
	                 smap1  : smap1p
	         };
	         
	         _mask('Espere un momento...');
	         
	         Ext.Ajax.request(
				        {
			            url       : _p21_urlObtienePlanDefinitivo
			            ,jsonData : params
			            ,success  : function(response)
			            {
			            	_unmask();
	
			            	var jsonCont = Ext.decode(response.responseText);
			            	
			            	var cdplanAnt = recordGrupoEdit.get('cdplan');
			            	var dsplanAnt = recordGrupoEdit.get('dsplanl');
			            	
			            	if(Ext.isEmpty(dsplanAnt)){
			            		dsplanAnt = jsonCont.smap1.DSPLAN_ORIG;
			            	}
			            	
			            	var nvoCdplan = jsonCont.smap1.NVO_CDPLAN;
		                	var nvoDSplan = jsonCont.smap1.NVO_DSPLAN;
		                	var nvoNombrePlan = jsonCont.smap1.NVO_NOMBRE;
		                	
			                if(jsonCont.exito){
			                		/**
				                      * Se calcula el plan y las descripcion
				                      **/
				                    
				                      recordGrupoEdit.set('cdplan',nvoCdplan);
				                      recordGrupoEdit.set('dsplanl',nvoNombrePlan);
				                    
				                    mensajeCorrecto('Se han guardado los datos','Se han guardado los datos',function(){         	
				                   	 
				                    	setTimeout(function(){
				                    		_p21_setActiveResumen();
				                    		
				                    		if( cdplanAnt != nvoCdplan || dsplanAnt != nvoNombrePlan){
				                    			
				                    			recordGrupoEdit.set('ptsumaaseg','');
				                    			
					                    		mensajeWarning('El plan del grupo '+recordGrupoEdit.get('letra')+ ' ha cambido. Seleccione una Suma asegurada');
					                    		gridGrupos.editingPlugin.startEdit(recordGrupoEdit,0);
					                    		
					                    		var indexSumAseg = 0;
					                    		
					                    		Ext.Array.each(gridGrupos.columns,function(columnGpo, indexCol){
						                       		if(columnGpo.dataIndex == "ptsumaaseg"){
						                       			indexSumAseg = indexCol;
						                       			return false;
						                       		}
						                       	});
					                    		
					                    		var comboPlan = gridGrupos.columns[indexSumAseg].getEditor(recordGrupoEdit);
					                    		setTimeout(function(){
					                    			comboPlan.setValue('');
					                        	},500);
				                    		}
				                    	},250);
				                    });
			                	
			                }
			                else
			                {
			                    mensajeWarning(jsonCont.respuesta);
			                }
			            }
			            ,failure  : function()
			            {
			            	_unmask();
			                errorComunicacion();
			            }
			        });
         }else{
        	 mensajeCorrecto('Se han guardado los datos','Se han guardado los datos',_p21_setActiveResumen);
         }
     }
     
     debug('<_p21_guardarGrupo');
}

function _p21_obtenerGrupoPorLetra(letra)
{
    debug('>_p21_obtenerGrupoPorLetra:',letra);
    var recordGrupo = null;
    _p21_storeGrupos.each(function(record)
    {
        if(record.get('letra')==letra)
        {
            recordGrupo = record;
        }
    });
    debug('<_p21_obtenerGrupoPorLetra:',recordGrupo);
    return recordGrupo;
}

function _p21_setActiveResumen()
{
    debug('>_p21_setActiveResumen');
    _p21_setActiveTab(_p21_tabGrupos.itemId);
    window.parent.scrollTo(0, 0);
    debug('<_p21_setActiveResumen');
}

function _p21_setActiveConcepto()
{
    debug('>_p21_setActiveConcepto');
    _p21_setActiveTab('_p21_tabConcepto');
    window.parent.scrollTo(0, 0);
    debug('<_p21_setActiveConcepto');
}

function _p21_tabConcepto()
{
    debug('>_p21_tabConcepto<');
    return _p21_query('#_p21_tabConcepto')[0];
}

function _p21_setActiveTab(itemId)
{
    debug('>_p21_setActiveTab:',itemId);
    _p21_tabpanel().setActiveTab(Ext.ComponentQuery.query('#'+itemId)[0]);
    window.parent.scrollTo(0, 0);
    debug('<_p21_setActiveTab');
}

function _p21_editorPlanChange(combo,newValue,oldValue,eOpts)
{
    debug('>_p21_editorPlanChange new',newValue,'old',oldValue+'x');
    if(!Ext.isEmpty(oldValue)&&(_p21_clasif==_p21_TARIFA_MODIFICADA||_p21_smap1.LINEA_EXTENDIDA=='N')&&_p21_semaforoPlanChange)
    {
        centrarVentanaInterna(Ext.MessageBox.confirm('Confirmar', 'Al cambiar el plan se borrar&aacute; el detalle del subgrupo<br/>¿Desea continuar?', function(btn)
        {
            if(btn == 'yes')
            {
                var record = _p21_query('#'+_p21_tabGrupos.itemId)[0].items.items[0].getSelectionModel().getSelection()[0];
                debug('record:',record);
                var letra  = record.get('letra');
                debug('letra:',letra);
                record.valido = false;
                _p21_quitarTabsDetalleGrupo(letra);
            }
            else
            {
                _p21_semaforoPlanChange = false;
                combo.setValue(oldValue);
                _p21_semaforoPlanChange = true;
            }
        }));
    }
    if(newValue+'x'!='x'&&_p21_clasif==_p21_TARIFA_LINEA&&_p21_smap1.LINEA_EXTENDIDA=='S')
    {
        _p21_estiloEditores(newValue);
    }
    debug('<_p21_editorPlanChange');
}

function _p21_query(regex)
{
    debug('>_p21_query<',regex);
    return Ext.ComponentQuery.query(regex);
}

function _p21_generarTramiteClic(callback,sincenso,revision,complemento,nombreCensoParaConfirmar,asincrono)
{
    debug('>_p21_generarTramiteClic callback?:'                , !Ext.isEmpty(callback)   , ',sincenso:'    , sincenso    , '.');
    debug('>_p21_generarTramiteClic revision:'                 , revision                 , ',complemento:' , complemento , '.');
    debug('>_p21_generarTramiteClic nombreCensoParaConfirmar:' , nombreCensoParaConfirmar , ',asincrono:'   , asincrono   , '.');
    var valido = true;
    
    if(valido){
		 if((_p21_smap1.cdsisrol=='SUSCRIPTOR'&& (_p21_smap1.status-0==19 || _p21_smap1.status-0==21 || _p21_smap1.status-0==23) ) && !_contratanteSaved){
		 	valido = false;
		 	mensajeWarning('Debe Guardar el Contratante.');
		 }
    }
    
    if(valido)
    {
        //parche para sin censo>
        if(
            (!Ext.isEmpty(sincenso)&&sincenso==true)
            ||!Ext.isEmpty(nombreCensoParaConfirmar)
        )
        {
            _fieldByName('censo').allowBlank=true;
        }
        //<parche para sin censo
        
        valido = _p21_tabConcepto().down('[xtype=form]').isValid();
        
        //parche para sin censo>
        if(
            (!Ext.isEmpty(sincenso)&&sincenso==true)
            ||!Ext.isEmpty(nombreCensoParaConfirmar)
        )
        {
            _fieldByName('censo').allowBlank=_p21_ntramite&&_p21_smap1.sincenso!='S' ? true : false;
        }
        //<parche para sin censo
        if(!valido)
        {
            mensajeWarning('Verificar los datos del concepto y el censo de asegurados',_p21_setActiveConcepto);
        }
    }
    
    if(valido)
    {    
        valido = _p21_storeGrupos.getCount()>0;
        debug('_p21_storeGrupos.getCount()>0:',_p21_storeGrupos.getCount()>0);
        if(!valido)
        {
            if(!_p21_tabGrupos)
            {
                mensajeWarning('Debe introducir el n&uacute;mero de asegurados',_p21_setActiveConcepto);
            }
            else
            {
                mensajeWarning('Debe introducir al menos un grupo',_p21_setActiveResumen);
            }
        }
    }
    
    if(valido&&_p21_clasif==_p21_TARIFA_LINEA)
    {
        var mensajeDeError = 'Falta definir los datos para el(los) grupo(s): ';
        _p21_storeGrupos.each(function(record)
        {
            if(Ext.isEmpty(record.get('cdplan')))
            {
                valido         = false;
                mensajeDeError = mensajeDeError + record.get('letra') + ' ';
            }
        });
        if(!valido)
        {
            mensajeWarning(mensajeDeError,_p21_setActiveResumen);
        }
    }
    
    if(valido&&(_p21_clasif==_p21_TARIFA_MODIFICADA||_p21_smap1.LINEA_EXTENDIDA=='N'))
    {
        var mensajeDeError = 'Falta definir la Suma Asegurada para el(los) grupo(s): ';
        _p21_storeGrupos.each(function(record)
        {
            if(Ext.isEmpty(record.get('ptsumaaseg')))
            {
                valido         = false;
                mensajeDeError = mensajeDeError + record.get('letra') + '  ';
            }
        });
        if(!valido)
        {
            mensajeWarning(mensajeDeError,_p21_setActiveResumen);
        }
    }
    
    if(valido&&(_p21_clasif==_p21_TARIFA_MODIFICADA||_p21_smap1.LINEA_EXTENDIDA=='N'))
    {
        var mensajeDeError = 'Falta definir o guardar el detalle para el(los) grupo(s): ';
        _p21_storeGrupos.each(function(record)
        {
            if(!record.valido)
            {
                valido         = false;
                mensajeDeError = mensajeDeError + record.get('letra') + ' ';
            }
        });
        if(!valido)
        {
            mensajeWarning(mensajeDeError,_p21_setActiveResumen);
        }
    }
    
    if(valido)
    {
        var form=_p21_tabConcepto().down('[xtype=form]');
        form.setLoading(true);
        var timestamp = new Date().getTime();
        
        var micallback=function()
        {
                var form=_p21_tabConcepto().down('[xtype=form]');
                var disabled = _p21_tabpanel().isDisabled();
                if(disabled)
                {
                    _p21_tabpanel().setDisabled(false);
                }
                var conceptos = form.getValues();
                if(disabled)
                {
                    _p21_tabpanel().setDisabled(true);
                }
                conceptos['timestamp']             = timestamp;
                conceptos['clasif']                = _p21_clasif;
                conceptos['LINEA_EXTENDIDA']       = _p21_smap1.LINEA_EXTENDIDA;
                conceptos['cdunieco']              = _p21_smap1.cdunieco;
                conceptos['cdramo']                = _p21_smap1.cdramo;
                conceptos['cdtipsit']              = _p21_smap1.cdtipsit;
                conceptos['ntramiteVacio']         = _p21_ntramiteVacio ? _p21_ntramiteVacio : '';
                conceptos['sincenso']              = !Ext.isEmpty(sincenso)&&sincenso==true?'S':'N';
                conceptos['censoAtrasado']         = !Ext.isEmpty(_p21_smap1.sincenso)&&_p21_smap1.sincenso=='S'?'S':'N';
                conceptos['resubirCenso']          = _p21_resubirCenso;
                conceptos['complemento']           = true==complemento?'S':'N';
                conceptos['nombreCensoConfirmado'] = nombreCensoParaConfirmar;
                conceptos['asincrono']             = asincrono;
                conceptos['duplicar']              = _cotcol_smap1.modificarTodo === true ? 'S' : 'N';
                
                if(_p21_smap1.cdsisrol=='SUSCRIPTOR'&& (_p21_smap1.status-0==19 || _p21_smap1.status-0==21 || _p21_smap1.status-0==23)){
                	
                	conceptos.swexiper = 'S';
                	
                	conceptos.codpostal = codpostalDefinitivo; 
                	conceptos.cdedo     = cdedoDefinitivo; 
                	conceptos.cdmunici  = cdmuniciDefinitivo;
                	
                }else{
                	conceptos.swexiper = 'N';
                }
                
//                alert('exiper' + conceptos.swexiper);
                
                var grupos = [];
                _p21_storeGrupos.each(function(record)
                {
                    var grupo = record.data;
                    grupo['tvalogars']=record.tvalogars;
                    grupos.push(grupo);
                });
                Ext.Ajax.request(
                {
                    url       : _p21_urlGenerarTramiteGrupo
                    ,jsonData :
                    {
                        smap1   : conceptos
                        ,olist1 : grupos
                    }
                    ,success  : function(response)
                    {
                        form.setLoading(false);
                        var json=Ext.decode(response.responseText);
                        debug('### generar tramite:',json);
                        if(json.exito)
                        {
                            if((_p21_ntramite||_p21_ntramiteVacio) && _cotcol_smap1.modificarTodo === false)
                            {
                                if(callback)
                                {
                                    if(!_p21_ntramite)
                                    {
                                        _p21_smap1.ntramite = _p21_ntramiteVacio;
                                        _p21_smap1.nmpoliza = json.smap1.nmpoliza;
                                    }
                                    debug('revision:',revision,'_p21_ntramite:',_p21_ntramite,'.');
                                    debug('_p21_smap1.sincenso:',_p21_smap1.sincenso,'.');
                                    if((true==revision||_p21_ntramiteVacio)
                                        &&!(_p21_ntramite&&_p21_smap1.sincenso!='S')
                                        &&Ext.isEmpty(nombreCensoParaConfirmar)
                                    )
                                    {
                                        _p21_tabpanel().setDisabled(true);
	                                    var ck = 'Recuperando asegurados para revision';
	                                    try
	                                    {
	                                        _p21_tabpanel().setLoading(true);
	                                        Ext.Ajax.request(
	                                        {
	                                            url      : _p21_urlRecuperacionSimpleLista
	                                            ,params  :
	                                            {
	                                                'smap1.procedimiento' : 'RECUPERAR_REVISION_COLECTIVOS'
	                                                ,'smap1.cdunieco'     : _p21_smap1.cdunieco
	                                                ,'smap1.cdramo'       : _p21_smap1.cdramo
	                                                ,'smap1.estado'       : 'W'
	                                                ,'smap1.nmpoliza'     : json.smap1.nmpoliza
	                                            }
	                                            ,success : function(response)
	                                            {
	                                                var ck = 'Decodificando datos de asegurados para revision';
	                                                try
	                                                {
	                                                    _p21_tabpanel().setLoading(false);
	                                                    var json2 = Ext.decode(response.responseText);
	                                                    debug('### asegurados:',json2);
	                                                    var store = Ext.create('Ext.data.Store',
	                                                    {
	                                                        model : '_p21_modeloRevisionAsegurado'
	                                                        ,data : json2.slist1
	                                                    });
	                                                    debug('store.getRange():',store.getRange());
	                                                    centrarVentanaInterna(Ext.create('Ext.window.Window',
	                                                    {
	                                                        width   : 600
	                                                        ,height : 500
	                                                        ,title  : 'Revisar asegurados del censo'
	                                                        ,closable : false
	                                                        ,listeners :
	                                                        {
	                                                            afterrender : function()
	                                                            {
	                                                                if(json2.slist1.length==0)
	                                                                {
	                                                                    setTimeout(function(){mensajeError('No se registraron asegurados, favor de revisar a detalle los errores');},1000);
	                                                                }
	                                                            }
	                                                        }
	                                                        ,items  :
	                                                        [
	                                                            Ext.create('Ext.panel.Panel',
	                                                            {
	                                                                layout    : 'hbox'
	                                                                ,border   : 0
	                                                                ,defaults : { style : 'margin:5px;' }
	                                                                ,height   : 40
	                                                                ,items    :
	                                                                [
	                                                                    {
	                                                                        xtype       : 'displayfield'
	                                                                        ,fieldLabel : 'Filas leidas'
	                                                                        ,value      : json.smap1.filasLeidas
	                                                                    }
	                                                                    ,{
	                                                                        xtype       : 'displayfield'
	                                                                        ,fieldLabel : 'Filas procesadas'
	                                                                        ,value      : json.smap1.filasProcesadas
	                                                                    }
	                                                                    ,{
	                                                                        xtype       : 'displayfield'
	                                                                        ,fieldLabel : 'Filas con error'
	                                                                        ,value      : json.smap1.filasErrores
	                                                                    }
	                                                                    ,{
	                                                                        xtype    : 'button'
	                                                                        ,text    : 'Ver errores'
	                                                                        ,hidden  : Number(json.smap1.filasErrores)==0
	                                                                        ,handler : function()
	                                                                        {
	                                                                            centrarVentanaInterna(Ext.create('Ext.window.Window',
	                                                                            {
	                                                                                modal        : true
	                                                                                ,closeAction : 'destroy'
	                                                                                ,title       : 'Errores al procesar censo'
	                                                                                ,width       : 800
	                                                                                ,height      : 500
	                                                                                ,items       :
	                                                                                [
	                                                                                    {
	                                                                                        xtype       : 'textarea'
	                                                                                        ,fieldStyle : 'font-family: monospace'
	                                                                                        ,value      : json.smap1.erroresCenso
	                                                                                        ,readOnly   : true
	                                                                                        ,width      : 780
	                                                                                        ,height     : 440
	                                                                                    }
	                                                                                ]
	                                                                            }).show());
	                                                                        }
	                                                                    }
	                                                                ]
	                                                            })
	                                                            ,Ext.create('Ext.grid.Panel',
	                                                            {
	                                                                height   : 350
	                                                                ,columns :
	                                                                [
	                                                                    {
	                                                                        text       : 'Grupo'
	                                                                        ,dataIndex : 'CDGRUPO'
	                                                                        ,width     : 60
	                                                                    }
	                                                                    ,{
	                                                                        text       : 'No.'
	                                                                        ,dataIndex : 'NMSITUAC'
	                                                                        ,width     : 40
	                                                                    }
	                                                                    ,{
	                                                                        text       : 'Parentesco'
	                                                                        ,dataIndex : 'PARENTESCO'
	                                                                        ,width     : 120
	                                                                    }
	                                                                    ,{
	                                                                        text       : 'Nombre'
	                                                                        ,dataIndex : 'NOMBRE'
	                                                                        ,width     : 200
	                                                                    }
	                                                                    ,{
	                                                                        text       : 'Sexo'
	                                                                        ,dataIndex : 'SEXO'
	                                                                        ,width     : 80
	                                                                    }
	                                                                    ,{
	                                                                        text       : 'Edad'
	                                                                        ,dataIndex : 'EDAD'
	                                                                        ,width     : 60
	                                                                    }
	                                                                ]
	                                                                ,store : store
	                                                            })
	                                                        ]
	                                                        ,buttonAlign : 'center'
	                                                        ,buttons     :
	                                                        [
	                                                            {
	                                                                text      : 'Aceptar y continuar'
	                                                                ,icon     : '${ctx}/resources/fam3icons/icons/accept.png'
	                                                                ,disabled : Number(json.smap1.filasErrores)>0
	                                                                ,handler  : function(me)
	                                                                {
	                                                                    me.up('window').destroy();
	                                                                    _p21_generarTramiteClic(
                                                                            callback
                                                                            ,sincenso
                                                                            ,false //revision
                                                                            ,complemento
                                                                            ,json.smap1.nombreCensoParaConfirmar
                                                                        );
	                                                                }
	                                                                ,listeners :
	                                                                {
	                                                                    afterrender : function(me)
	                                                                    {
	                                                                        if(Number(json.smap1.filasErrores)>0)
	                                                                        {
	                                                                            _p21_desbloqueoBotonRol(me);
	                                                                        }
	                                                                    }
	                                                                }
	                                                            }
	                                                            ,{
	                                                                text     : 'Modificar datos'
	                                                                ,icon    : '${ctx}/resources/fam3icons/icons/pencil.png'
	                                                                ,handler : function(me)
	                                                                {
	                                                                    me.up('window').destroy();
	                                                                    _p21_tabpanel().setDisabled(false);
	                                                                    _p21_resubirCenso = 'S';
	                                                                }
	                                                            }
	                                                        ]
	                                                    }).show());
	                                                }
	                                                catch(e)
	                                                {
	                                                    manejaException(e,ck);
	                                                }
	                                            }
	                                            ,failure : function()
	                                            {
	                                                _p21_tabpanel().setLoading(false);
	                                                errorComunicacion(ck);
	                                            }
	                                        });
	                                    }
	                                    catch(e)
	                                    {
	                                        manejaException(e,ck);
	                                    }
                                    }
                                    else
                                    {
                                        callback(json);
                                    }
                                }
                                else
                                {
                                    mensajeError(json.respuesta+'<br/>Se guard&oacute; la informaci&oacute;n pero no hay callback');
                                }
                            }
                            else
                            {
                                _p21_fieldNmpoliza().setValue(json.smap1.nmpoliza);
                                _p21_fieldNtramite().setValue(json.smap1.ntramite);
                                _p21_tabpanel().setDisabled(true);
                                
                                if(Ext.isEmpty(nombreCensoParaConfirmar))
                                {
	                                mensajeCorrecto('Censo revisado',json.respuesta+'<br/>Para revisar los datos presiona aceptar',function()
	                                {
	                                    var ck = 'Recuperando asegurados para revision';
	                                    try
	                                    {
	                                        _p21_tabpanel().setLoading(true);
	                                        Ext.Ajax.request(
	                                        {
	                                            url      : _p21_urlRecuperacionSimpleLista
	                                            ,params  :
	                                            {
	                                                'smap1.procedimiento' : 'RECUPERAR_REVISION_COLECTIVOS'
	                                                ,'smap1.cdunieco'     : _p21_smap1.cdunieco
	                                                ,'smap1.cdramo'       : _p21_smap1.cdramo
	                                                ,'smap1.estado'       : 'W'
	                                                ,'smap1.nmpoliza'     : json.smap1.nmpoliza
	                                            }
	                                            ,success : function(response)
	                                            {
	                                                var ck = 'Decodificando datos de asegurados para revision';
	                                                try
	                                                {
	                                                    _p21_tabpanel().setLoading(false);
	                                                    var json2 = Ext.decode(response.responseText);
	                                                    debug('### asegurados:',json2);
	                                                    var store = Ext.create('Ext.data.Store',
		                                                {
		                                                    model : '_p21_modeloRevisionAsegurado'
		                                                    ,data : json2.slist1
		                                                });
		                                                debug('store.getRange():',store.getRange());
		                                                centrarVentanaInterna(Ext.create('Ext.window.Window',
				                                        {
				                                            width   : 600
				                                            ,height : 500
				                                            ,title  : 'Revisar asegurados del censo'
				                                            ,closable : false
                                                            ,listeners :
                                                            {
                                                                afterrender : function()
                                                                {
                                                                    if(json2.slist1.length==0)
                                                                    {
                                                                        setTimeout(function(){mensajeError('No se registraron asegurados, favor de revisar a detalle los errores');},1000);
                                                                    }
                                                                }
                                                            }
				                                            ,items  :
				                                            [
				                                                Ext.create('Ext.panel.Panel',
				                                                {
				                                                    layout    : 'hbox'
				                                                    ,border   : 0
				                                                    ,defaults : { style : 'margin:5px;' }
				                                                    ,height   : 40
				                                                    ,items    :
				                                                    [
				                                                        {
				                                                            xtype       : 'displayfield'
				                                                            ,fieldLabel : 'Filas leidas'
				                                                            ,value      : json.smap1.filasLeidas
				                                                        }
				                                                        ,{
	                                                                        xtype       : 'displayfield'
	                                                                        ,fieldLabel : 'Filas procesadas'
	                                                                        ,value      : json.smap1.filasProcesadas
	                                                                    }
	                                                                    ,{
	                                                                        xtype       : 'displayfield'
	                                                                        ,fieldLabel : 'Filas con error'
	                                                                        ,value      : json.smap1.filasErrores
	                                                                    }
	                                                                    ,{
	                                                                        xtype    : 'button'
	                                                                        ,text    : 'Ver errores'
	                                                                        ,hidden  : Number(json.smap1.filasErrores)==0
	                                                                        ,handler : function()
	                                                                        {
	                                                                            centrarVentanaInterna(Ext.create('Ext.window.Window',
	                                                                            {
	                                                                                modal        : true
	                                                                                ,closeAction : 'destroy'
	                                                                                ,title       : 'Errores al procesar censo'
	                                                                                ,width       : 800
	                                                                                ,height      : 500
	                                                                                ,items       :
	                                                                                [
	                                                                                    {
	                                                                                        xtype       : 'textarea'
	                                                                                        ,fieldStyle : 'font-family: monospace'
	                                                                                        ,value      : json.smap1.erroresCenso
	                                                                                        ,readOnly   : true
	                                                                                        ,width      : 780
	                                                                                        ,height     : 440
	                                                                                    }
	                                                                                ]
	                                                                            }).show());
	                                                                        }
	                                                                    }
				                                                    ]
				                                                })
				                                                ,Ext.create('Ext.grid.Panel',
				                                                {
				                                                    height   : 350
				                                                    ,columns :
				                                                    [
				                                                        {
				                                                            text       : 'Grupo'
				                                                            ,dataIndex : 'CDGRUPO'
				                                                            ,width     : 60
				                                                        }
				                                                        ,{
				                                                            text       : 'No.'
				                                                            ,dataIndex : 'NMSITUAC'
				                                                            ,width     : 40
				                                                        }
				                                                        ,{
				                                                            text       : 'Parentesco'
				                                                            ,dataIndex : 'PARENTESCO'
				                                                            ,width     : 120
				                                                        }
	                                                                    ,{
	                                                                        text       : 'Nombre'
	                                                                        ,dataIndex : 'NOMBRE'
	                                                                        ,width     : 200
	                                                                    }
	                                                                    ,{
	                                                                        text       : 'Sexo'
	                                                                        ,dataIndex : 'SEXO'
	                                                                        ,width     : 80
	                                                                    }
	                                                                    ,{
	                                                                        text       : 'Edad'
	                                                                        ,dataIndex : 'EDAD'
	                                                                        ,width     : 60
	                                                                    }
				                                                    ]
				                                                    ,store : store
				                                                })
				                                            ]
				                                            ,buttonAlign : 'center'
	                                                        ,buttons     :
	                                                        [
	                                                            {
	                                                                text      : 'Aceptar y subir documentos'
	                                                                ,icon     : '${ctx}/resources/fam3icons/icons/accept.png'
	                                                                ,disabled : Number(json.smap1.filasErrores)>0
	                                                                ,handler  : function(me)
	                                                                {
	                                                                    me.up('window').destroy();
	                                                                    _p21_generarTramiteClic(
	                                                                        null
	                                                                        ,sincenso
	                                                                        ,false //revision
	                                                                        ,complemento
	                                                                        ,json.smap1.nombreCensoParaConfirmar
	                                                                    );
	                                                                }
	                                                                ,listeners :
	                                                                {
	                                                                    afterrender : function(me)
	                                                                    {
	                                                                        if(Number(json.smap1.filasErrores)>0)
	                                                                        {
	                                                                            _p21_desbloqueoBotonRol(me);
	                                                                        }
	                                                                    }
	                                                                }
	                                                            }
	                                                            ,{
	                                                                text     : 'Modificar datos'
	                                                                ,icon    : '${ctx}/resources/fam3icons/icons/pencil.png'
	                                                                ,handler : function(me)
	                                                                {
	                                                                    me.up('window').destroy();
	                                                                    _p21_tabpanel().setDisabled(false);
	                                                                    _p21_resubirCenso = 'S';
	                                                                }
	                                                            }
	                                                        ]
				                                        }).show());
	                                                }
	                                                catch(e)
	                                                {
	                                                    manejaException(e,ck);
	                                                }
	                                            }
	                                            ,failure : function()
	                                            {
	                                                _p21_tabpanel().setLoading(false);
	                                                errorComunicacion(null,'Recuperando asegurados para revision');
	                                            }
	                                        });
	                                    }
	                                    catch(e)
	                                    {
	                                        manejaException(e,ck);
	                                    }
	                                });
                                }
                                else
                                {
                                    var callbackConfirmado = function(callback)
                                    {
                                        centrarVentanaInterna(Ext.create('Ext.window.Window',
                                        {
                                            width        : 600
                                            ,height      : 440
                                            ,title       : 'Subir documentos de tu tr&aacute;mite ('+json.smap1.ntramite+')'
                                            ,closable    : false
                                            ,modal       : true
                                            ,loadingMask : true
                                            ,loader      :
                                            {
                                                url       : _p21_urlVentanaDocumentos
                                                ,scripts  : true
                                                ,autoLoad : true
                                                ,params   :
                                                {
                                                    'smap1.cdunieco'  : json.smap1.cdunieco
                                                    ,'smap1.cdramo'   : json.smap1.cdramo
                                                    ,'smap1.estado'   : 'W'
                                                    ,'smap1.nmpoliza' : '0'
                                                    ,'smap1.nmsolici' : '0'
                                                    ,'smap1.nmsuplem' : '0'
                                                    ,'smap1.ntramite' : json.smap1.ntramite
                                                    ,'smap1.tipomov'  : '0'
                                                }
                                            }
                                            ,buttonAlign : 'center'
                                            ,buttons     :
                                            [
                                                {
                                                    text     : 'Continuar'
                                                    ,icon    : '${ctx}/resources/fam3icons/icons/accept.png'
                                                    ,handler : function(me)
                                                    {
                                                        location.reload();
                                                    }
                                                }
                                            ]
                                        }).show());
                                        if(!Ext.isEmpty(json.smap1.nombreUsuarioDestino))
                                        {
                                            mensajeCorrecto(
                                                'Tr&aacute;mite asignado',
                                                'El tr&aacute;mite '+json.smap1.ntramite+' fue asignado a '+json.smap1.nombreUsuarioDestino,
                                                function () {
                                                    if (_cotcol_smap1.modificarTodo === true) {
                                                        _p21_mesacontrol();
                                                    }
                                                }
                                            );
                                        } else {
                                            mensajeCorrecto(
                                                'Tr\u00e1mite generado',
                                                'Se ha generado el tr\u00e1mite ' + json.smap1.ntramite +
                                                    ', favor de revisar los requisitos y subir sus documentos antes de turnar ' +
                                                    'al \u00e1rea t\u00e9cnica',
                                                callback
                                            );
                                        }
                                    };
                                    var callbackNormal = callbackConfirmado; 
                                    var mask, ck = 'Recuperando lista de requisitos';
			                        try {
			                            var ntramite = json.smap1.ntramite;
			                            ck = 'Recuperando validaci\u00f3n ligada a requisitos';
			                            mask = _maskLocal(ck);
			                            Ext.Ajax.request({
			                                url     : _GLOBAL_URL_RECUPERACION,
			                                params  : {
			                                    'params.consulta' : 'RECUPERAR_VALIDACION_POR_CDVALIDAFK',
			                                    'params.ntramite' : ntramite,
			                                    'params.clave'    : '_CONFCOT'
			                                },
			                                success : function (response) {
			                                    mask.close();
			                                    var ck = 'Decodificando respuesta al recuperar validaci\u00f3n ligada a requisitos';
			                                    try {
			                                        var valida = Ext.decode(response.responseText);
			                                        debug('### validacion ligada a checklist:', valida);
			                                        if (valida.success === true) {
			                                            if (valida.list.length > 0) {
			                                                _cargarAccionesEntidad(
			                                                    valida.list[0].CDTIPFLU,
			                                                    valida.list[0].CDFLUJOMC,
			                                                    valida.list[0].TIPOENT,
			                                                    valida.list[0].CDENTIDAD,
			                                                    valida.list[0].WEBID,
			                                                    function (acciones) {
			                                                        if (acciones.length > 0) {
			                                                            debug('acciones:', acciones);
			                                                            callbackNormal(function () {
			                                                                _procesaAccion(
			                                                                    acciones[0].CDTIPFLU,
			                                                                    acciones[0].CDFLUJOMC,
			                                                                    acciones[0].TIPODEST,
			                                                                    acciones[0].CLAVEDEST,
			                                                                    acciones[0].WEBIDDEST,
			                                                                    acciones[0].AUX,
			                                                                    valida.params.ntramite,
			                                                                    valida.list[0].STATUS,
			                                                                    null, //cdunieco
			                                                                    null, //cdramo
			                                                                    null, //estado
			                                                                    null, //nmpoliza
			                                                                    null, //nmsituac
			                                                                    null, //nmsuplem
			                                                                    valida.list[0].cdusuari,
			                                                                    valida.list[0].cdsisrol,
			                                                                    null // callback
			                                                                );
			                                                            });
			                                                        } else {
			                                                            callbackNormal();
			                                                        }
			                                                    }
			                                                );
			                                            } else {
			                                                callbackNormal();
			                                            }
			                                        } else {
			                                            mensajeError(json.message);
			                                        }
			                                    } catch (e) {
			                                        manejaException(e, ck);
			                                    }
			                                },
			                                failure : function () {
			                                    mask.close();
			                                    errorComunicacion(null, 'Error al recuperar validaci\u00f3n ligada a requisitos');
			                                }
			                            });
			                        } catch (e) {
			                            manejaException(e, ck, mask);
			                            callbackNormal();
			                        }
                                }
                            }
                        }
                        else
                        {
                            mensajeError(json.respuesta,function(){});
                        }
                    }
                    ,failure  : function()
                    {
                        form.setLoading(false);
                        errorComunicacion();
                    }
                });
        }
        
        if(
            (!Ext.isEmpty(sincenso)&&sincenso==true)
            ||!Ext.isEmpty(nombreCensoParaConfirmar)
        )
        {
            micallback();
        }
        else
        {
            form.submit(
            {
                params   :
                {
                    'smap1.timestamp' : timestamp
                    ,'smap1.ntramite' : _p21_ntramite && _p21_smap1.sincenso != 'S' && _cotcol_smap1.modificarTodo !== true
                        ? _p21_ntramite
                        : ''
                }
                ,success : function()
                {
                    micallback();
                }
                ,failure : function()
                {
                    form.setLoading(false);
                    errorComunicacion();
                }
            });
        }
    }
    
    debug('<_p21_generarTramiteClic');
}

function _p21_reload(json,status,nmpoliza)
{
    debug('>_p21_reload params:');
    debug(json,status,nmpoliza,'dummy');
    _p21_tabpanel().setLoading(true);
    Ext.create('Ext.form.Panel').submit(
    {
        standardSubmit : true
        ,params        :
        {
            'smap1.cdunieco'  : _p21_smap1.cdunieco
            ,'smap1.cdramo'   : _p21_smap1.cdramo
            ,'smap1.cdtipsit' : _p21_smap1.cdtipsit
            ,'smap1.estado'   : _p21_smap1.estado
            ,'smap1.nmpoliza' : Ext.isEmpty(nmpoliza) ? json.smap1.nmpoliza : nmpoliza
            ,'smap1.ntramite' : _p21_ntramite ? _p21_ntramite : _p21_ntramiteVacio
            ,'smap1.cdagente' : _fieldByName('cdagente').getValue()
            ,'smap1.status'   : Ext.isEmpty(status) ? _p21_smap1.status : status
            ,'smap1.cdtipsup' : _p21_smap1.cdtipsup
            ,'smap1.nmpolant' : _p21_smap1.nmpolant
        }
    });
    debug('<_p21_reload');
}

function _p21_mesacontrol(json)
{
    _mask('Redireccionando...');
    Ext.create('Ext.form.Panel').submit(
    {
        standardSubmit : true
        ,url           : _p21_urlMesaControl
        ,params        :
        {
            'params.AGRUPAMC' : 'PRINCIPAL'
        }
    });
}

function _p21_turnar(status,titulo,closable)
{
    debug('>_p21_turnar:',status);
    var ventana=Ext.create('Ext.window.Window',
    {
        title        : !Ext.isEmpty(titulo) ? titulo : 'Turnar tr&aacute;mite'
        ,width       : 500
        ,height      : 330
        ,modal       : true
        ,closable    : closable==undefined ? true : closable
        ,items       :
        [
            {
                xtype       : 'textarea'
                ,labelAlign : 'top'
                ,fieldLabel : 'Comentarios'
                ,itemId     : '_p21_turnarCommentsItem'
                ,width      : 480
                ,height     : 200
            }
            ,{
                xtype       : 'radiogroup'
                ,fieldLabel : 'Mostrar al agente'
                ,columns    : 2
                ,width      : 250
                ,style      : 'margin:5px;'
                ,hidden     : _GLOBAL_CDSISROL===RolSistema.Agente
                ,items      :
                [
                    {
                        boxLabel    : 'Si'
                        ,itemId     : 'SWAGENTE'
                        ,name       : 'SWAGENTE'
                        ,inputValue : 'S'
                        ,checked    : _GLOBAL_CDSISROL===RolSistema.Agente
                    }
                    ,{
                        boxLabel    : 'No'
                        ,name       : 'SWAGENTE'
                        ,inputValue : 'N'
                        ,checked    : _GLOBAL_CDSISROL!==RolSistema.Agente
                    }
                ]
            }
        ]
        ,buttonAlign : 'center'
        ,buttons     :
        [
            {
                text     : 'Aceptar'
                ,icon    : '${ctx}/resources/fam3icons/icons/accept.png'
                ,handler : function(button)
                {
                    ventana.setLoading(true);
                    Ext.Ajax.request(
                    {
                        url     : _p21_urlActualizarStatus
                        ,params :
                        {
                            'smap1.status'    : status
                            ,'smap1.ntramite' : _p21_ntramite ? _p21_ntramite : _p21_ntramiteVacio
                            ,'smap1.comments' : Ext.ComponentQuery.query('#_p21_turnarCommentsItem')[0].getValue()
                            ,'smap1.swagente' : _fieldById('SWAGENTE').getGroupValue()
                        }
                        ,success : function(response)
                        {
                            ventana.setLoading(false);
                            var json=Ext.decode(response.responseText);
                            debug('json response:',json);
                            if(json.success)
                            {
                                status = json.smap1.status;
                                ventana.setLoading(true);
                                
                                //se llaman los documentos, en Java se condiciona si se generan o no
                                Ext.Ajax.request(
			                    {
			                        url     : _p21_guardarReporteCotizacion
			                        ,params :
			                        {
			                            'smap1.cdunieco'  : _p21_smap1.cdunieco
			                            ,'smap1.cdramo'   : _p21_smap1.cdramo
			                            ,'smap1.estado'   : _p21_smap1.estado
			                            ,'smap1.nmpoliza' : _p21_smap1.nmpoliza
			                            ,'smap1.cdperpag' : _fieldByName('cdperpag').getValue()
			                            ,'smap1.cdtipsit' : _p21_smap1.cdtipsit
			                            ,'smap1.ntramite' : _p21_smap1.ntramite
			                            ,'smap1.nGrupos'  : _p21_storeGrupos.getCount()
			                            ,'smap1.status'   : status
			                        }
			                    });
			                    
                                Ext.Ajax.request(
                                {
                                    url      : _p21_urlCargarParametros
                                    ,params  :
                                    {
                                        'smap1.parametro' : 'MENSAJE_TURNAR'
                                        ,'smap1.cdramo'   : _p21_smap1.cdramo
                                        ,'smap1.cdtipsit' : _p21_smap1.cdtipsit
                                        ,'smap1.clave4'   : status
                                    }
                                    ,success : function(response)
                                    {
                                        ventana.setLoading(false);
                                        var json2=Ext.decode(response.responseText);
                                        debug('### json response parametro mensaje turnar:',json2);
                                        if(json2.exito)
                                        {
                                            var mensajeTurnado = json2.smap1.P1VALOR
                                                    +(!Ext.isEmpty(json.smap1.nombreUsuarioDestino)?
                                                        '<br/>El tr&aacute;mite '+_p21_smap1.ntramite+' fue asignado a '+json.smap1.nombreUsuarioDestino:
                                                        ''
                                                    );
                                            
                                            if(json.smap1.ASYNC=='S')
                                            {
                                                mensajeTurnado = 'El tr\u00e1mite '+_p21_smap1.ntramite+' qued\u00f3 en espera y ser\u00e1 procesado posteriormente';
                                            }
                                        
                                            mensajeCorrecto('Tr&aacute;mite guardado'
                                                ,mensajeTurnado
                                                ,function()
                                            {
                                                button.up().up().destroy();
                                                if(status+'x'=='19x')
                                                {
                                                    _p21_reload(null,19,_p21_smap1.nmpoliza);
                                                }
                                                else
                                                {
                                                    _p21_mesacontrol();
                                                }
                                            });
                                        }
                                        else
                                        {
                                            mensajeWarning(json.respuesta);
                                        }
                                    }
                                    ,failure : function()
                                    {
                                        ventana.setLoading(false);
                                        errorComunicacion();
                                    }
                                });
                            }
                            else
                            {
                                mensajeError(json.mensaje);
                            }
                        }
                        ,failure : function()
                        {
                            ventana.setLoading(false);
                            errorComunicacion();
                        }
                    });
                }
            }
            ,{
                text     : 'Cancelar'
                ,icon    : '${ctx}/resources/fam3icons/icons/cancel.png'
                ,handler : function(me)
                {
                    me.up('window').destroy();
                }
            }
        ]
    }).show();
    centrarVentanaInterna(ventana);
    debug('<_p21_turnar');
}

function _p21_fieldNtramite()
{
    debug('>_p21_fieldNtramite<');
    return _p21_query('#_p21_fieldNtramite')[0];
}

function _p21_fieldNmpoliza()
{
    debug('>_p21_fieldNmpoliza<');
    return _p21_query('#_p21_fieldNmpoliza')[0];
}

function _p21_botonCotizar()
{
    debug('>_p21_botonCotizar<');
    return _p21_query('#_p21_botonCotizar')[0];
}

function _p21_botonDetalles()
{
    debug('>_p21_botonDetalles<');
    return _p21_query('#_p21_botonDetalles')[0];
}

function _p21_botonComprar()
{
    debug('>_p21_botonComprar<');
    return _p21_query('#_p21_botonComprar')[0];
}

function _p21_botonEditar()
{
    debug('>_p21_botonEditar<');
    return _p21_query('#_p21_botonEditar')[0];
}

function _p21_editarClic()
{
    debug('>_p21_editarClic');
    _p21_tabConcepto().down('[xtype=form]').setDisabled(false);
    _p21_tabConcepto().remove(_p21_gridTarifas,true);
    window.parent.scrollTo(0, 0);
    debug('<_p21_editarClic');
}

function _p21_tarifaSelect(selModel, record, row, column, eOpts)
{
    debug('>_p21_tarifaSelect');
    debug('column:',column);
    if(column>0)
    {
        column = (column * 2) -1;
    }
    debug('( column * 2 )-1:',column);
    var columnName=_p21_gridTarifas.columns[column].dataIndex;
    debug('record',record);
    debug('columnName',columnName);
    if(columnName=='DSPERPAG')
    {
        debug('DSPERPAG');
        _p21_botonDetalles().setDisabled(true);
        _p21_botonComprar().setDisabled(true);
    }
    else
    {
        // M N P R I M A X
        //0 1 2 3 4 5 6 7
        _p21_selectedCdperpag = record.get("CDPERPAG");
        _p21_selectedCdplan   = columnName.substr(7);
        _p21_selectedDsplan   = record.get("DSPLAN"+_p21_selectedCdplan);
        _p21_selectedNmsituac = record.get("NMSITUAC");
        debug('_p21_selectedCdperpag' , _p21_selectedCdperpag);
        debug('_p21_selectedCdplan'   , _p21_selectedCdplan);
        debug('_p21_selectedDsplan'   , _p21_selectedDsplan);
        debug('_p21_selectedNmsituac' , _p21_selectedNmsituac);
        
        _p21_botonDetalles().setDisabled(false);
        _p21_botonComprar().setDisabled(false);
    }
}

function _p21_detallesClic()
{
    debug('>_p21_detallesClic');
    _p21_tabpanel().setLoading(true);
    Ext.Ajax.request(
    {
        url      : _p21_urlObtenerDetalle
        ,params  :
        {
            'smap1.cdunieco'  : _p21_smap1.cdunieco
            ,'smap1.cdramo'   : _p21_smap1.cdramo
            ,'smap1.estado'   : 'W'
            ,'smap1.nmpoliza' : _p21_fieldNmpoliza().getValue()
            ,'smap1.cdplan'   : _p21_selectedCdplan
            ,'smap1.cdperpag' : _p21_selectedCdperpag
        }
        ,success : function(response)
        {
            _p21_tabpanel().setLoading(false);
            var json=Ext.decode(response.responseText);
            debug('json response:',json);
            var wndDetalleCotizacion = Ext.create('Ext.window.Window',
                {
                    title       : 'Detalles de cotizaci&oacute;n'
                    ,maxHeight  : 500
                    ,width      : 650
                    ,autoScroll : true
                    ,modal      : true
                    ,bbar       :
                    {
                        buttonAlign : 'right'
                        ,width      : 620
                        ,items      :
                        [
                            '->'
                            ,Ext.create('Ext.form.Label',
                            {
                                style          : 'color:white;'
                                ,initComponent : function()
                                {
                                    var sum = 0;
                                    for ( var i = 0; i < json.slist1.length; i++)
                                    {
                                        sum += parseFloat(json.slist1[i].IMPORTE);
                                    }
                                    this.setText('Total: '+ Ext.util.Format.usMoney(sum));
                                    this.callParent();
                                }
                            })
                        ]
                    }
                    ,items      :
                    [
                        Ext.create('Ext.grid.Panel',
                        {
                            width      : 620
                            ,store     : Ext.create('Ext.data.Store',
                            {
                                model       : '_p21_modeloDetalleCotizacion'
                                ,groupField : 'GRUPO'
                                ,sorters    :
                                [
                                    {
                                        sorterFn : function(o1,o2)
                                        {
                                            //debug('sorting:',o1,o2);
                                            if (o1.get('ORDEN') === o2.get('ORDEN'))
                                            {
                                                return 0;
                                            }
                                            return o1.get('ORDEN')-0 < o2.get('ORDEN')-0 ? -1 : 1;
                                        }
                                    }
                                ]
                                ,proxy      :
                                {
                                    type    : 'memory'
                                    ,reader : 'json'
                                }
                                ,data : json.slist1
                            })
                            ,columns :
                            [
                                {
                                    header           : 'Nombre de la cobertura'
                                    ,dataIndex       : 'DSGARANT'
                                    ,flex            : 3
                                    ,summaryType     : 'count'
                                    ,sortable        : false
                                    ,summaryRenderer : function(value)
                                    {
                                        return Ext.String.format('Total de {0} cobertura{1}',value,value !== 1 ? 's': '');
                                    }
                                }
                                ,{
                                    header       : 'Importe por cobertura'
                                    ,dataIndex   : 'IMPORTE'
                                    ,sortable    : false
                                    ,width       : 150
                                    ,renderer    : Ext.util.Format.usMoney
                                    ,align       : 'right'
                                    ,summaryType : 'sum'
                                } 
                            ]
                            ,features :
                            [
                                {
                                    groupHeaderTpl :
                                    [
                                        '{name:this.formatName}'
                                        ,{
                                            formatName : function(name)
                                            {
                                                return name.split("_")[2];
                                            }
                                        }
                                    ]
                                ,ftype          : 'groupingsummary'
                                ,startCollapsed : true
                                }
                            ]
                        })
                    ]
                }).show();
                centrarVentanaInterna(wndDetalleCotizacion);
        }
        ,failure : function()
        {
            _p21_tabpanel().setLoading(false);
            errorComunicacion();
        }
    });
    debug('<_p21_detallesClic');
}

function _p21_cotizarClonar()
{
    debug('>_p21_cotizarClonar');
    _p21_fieldNmpoliza().setValue('');
    _p21_editarClic();
    debug('<_p21_cotizarClonar');
}

function _p21_cotizarNueva()
{
    debug('>_p21_cotizarNueva');
    _p21_quitarTabsDetalleGrupo();
    _p21_storeGrupos.removeAll();
    if(_p21_tabGrupos)
    {
        debug('remove:',Ext.ComponentQuery.query('#'+_p21_tabGrupos.itemId)[0]);
        _p21_tabpanel().remove(Ext.ComponentQuery.query('#'+_p21_tabGrupos.itemId)[0],false);
    }
    _p21_tabConcepto().down('[xtype=form]').getForm().reset();
    _p21_editarClic();
    debug('<_p21_cotizarNueva');
}

function _p21_comprarClic()
{
    debug('>_p21_comprarClic');
    _p21_tabpanel().setLoading(true);
    Ext.Ajax.request(
    {
        url      : _p21_urlComprar
        ,params  :
        {
            comprarNmpoliza        : _p21_fieldNmpoliza().getValue()
            ,comprarCdplan         : _p21_selectedCdplan
            ,comprarCdperpag       : _p21_selectedCdperpag
            ,comprarCdramo         : _p21_smap1.cdramo
            ,comprarCdciaaguradora : '20'
            ,comprarCdunieco       : _p21_smap1.cdunieco
            ,cdtipsit              : _p21_smap1.cdtipsit
            ,'smap1.fechaInicio'   : Ext.Date.format(_p21_query('[name=feini]')[0].getValue(),'d/m/Y')
            ,'smap1.fechaFin'      : Ext.Date.format(_p21_query('[name=fefin]')[0].getValue(),'d/m/Y')
            ,'smap1.nombreTitular' : ''
            ,'smap1.ntramite'      : ''
            ,'smap1.parche'        : 'si'
        }
        ,success : function(response,opts)
        {
            _p21_tabpanel().setLoading(false);
            var json = Ext.decode(response.responseText);
            if (json.success == true)
            {
                centrarVentanaInterna(Ext.Msg.show(
                {
                    title    : 'Solicitud enviada'
                    ,msg     : 'Se ha guardado y enviado la solicitud a mesa de control'
                    ,buttons : Ext.Msg.OK
                    ,fn      : function()
                    {
                        _p21_botonComprar().hide();
                        _p21_botonDetalles().hide();
                        _p21_botonEditar().hide();
                        centrarVentanaIntera(Ext.create('Ext.window.Window',
                        {
                            width        : 600
                            ,height      : 400
                            ,title       : 'Subir documentos de tu tr&aacute;mite'
                            ,closable    : false
                            ,modal       : true
                            ,buttonAlign : 'center'
                            ,loadingMask : true
                            ,loader      :
                            {
                                url       : _p21_urlVentanaDocumentos
                                ,scripts  : true
                                ,autoLoad : true
                                ,params   :
                                {
                                    'smap1.cdunieco'  : _p21_smap1.cdunieco
                                    ,'smap1.cdramo'   : _p21_smap1.cdramo
                                    ,'smap1.estado'   : 'W'
                                    ,'smap1.nmpoliza' : _p21_fieldNmpoliza().getValue()
                                    ,'smap1.nmsuplem' : '0'
                                    ,'smap1.ntramite' : json.comprarNmpoliza
                                    ,'smap1.tipomov'  : '0'
                                }
                            }
                            ,buttons     :
                            [
                                {
                                    text     : 'Aceptar'
                                    ,icon    : '${ctx}/resources/fam3icons/icons/accept.png'
                                    ,handler : function()
                                    {
                                        this.up().up().destroy();
                                    }
                                }
                            ]
                        }).show());
                    }
                }));
            }
            else
            {
                
            }
        }
        ,failure : function()
        {
            _p21_tabpanel().setLoading(false);
            errorComunicacion();
        }
    });
    debug('<_p21_comprarClic');
}

function _p21_fieldRfc()
{
    return Ext.ComponentQuery.query('[name=cdrfc]')[0];
}

function _p21_rfcBlur(field)
{
    debug('>_p21_rfcBlur:',field);
    var value=field.getValue();
    debug('value:',value);
    
    var valido = true;
    
    if(valido)
    {
        valido = _p21_smap1.BLOQUEO_CONCEPTO=='N';
        if(!valido)
        {
            valido = _p21_smap1.cdsisrol=='SUSCRIPTOR'&&_p21_smap1.status-0==18;
        }
    }
    
    if(valido)
    {
        valido = value&&value.length>8&&value.length<14;
    }
    
    if(valido)
    {
        _p21_tabpanel().setLoading(true);
        Ext.Ajax.request
        ({
            url     : _p21_urlBuscarPersonas
            ,timeout: 240000
            ,params :
            {
                'map1.pv_rfc_i'       : value
                ,'map1.cdtipsit'      : _p21_smap1.cdtipsit
                ,'map1.pv_cdunieco_i' : _p21_smap1.cdunieco
                ,'map1.pv_cdramo_i'   : _p21_smap1.cdramo
                ,'map1.pv_estado_i'   : 'W'
                ,'map1.pv_nmpoliza_i' : _p21_fieldNmpoliza().getValue()
                ,'map1.esContratante' : 'S'
            }
            ,success:function(response)
            {
                _p21_tabpanel().setLoading(false);
                var json=Ext.decode(response.responseText);
                debug('json response:',json);
                if(json&&json.slist1&&json.slist1.length>0)
                {
                    centrarVentanaInterna(Ext.create('Ext.window.Window',
                    {
                        width        : 600
                        ,height      : 400
                        ,modal       : true
                        ,autoScroll  : true
                        ,title       : 'Coincidencias'
                        ,items       : Ext.create('Ext.grid.Panel',
                        {
                            store    : Ext.create('Ext.data.Store',
                            {
                                model     : 'RFCPersona'
                                ,autoLoad : true
                                ,proxy :
                                {
                                    type    : 'memory'
                                    ,reader : 'json'
                                    ,data   : json['slist1']
                                }
                            })
                            ,columns :
                            [
                                {
                                    xtype         : 'actioncolumn'
                                    ,menuDisabled : true
                                    ,width        : 30
                                    ,items        :
                                    [
                                        {
                                            icon     : '${ctx}/resources/fam3icons/icons/accept.png'
                                            ,tooltip : 'Seleccionar usuario'
                                            ,handler : function(grid, rowIndex, colIndex)
                                            {
                                                var record = grid.getStore().getAt(rowIndex);
                                                debug('record:',record);
                                                _fieldByName('cdrfc',_p_21_panelPrincipal).setValue(record.get('RFCCLI'));
                                                
//                                                _fieldByName('cdperson',_p_21_panelPrincipal).setValue(record.get('CLAVECLI'));
                                                //se obtiene cdperson temporal para prospecto
                                                _fieldByName('cdperson',_p_21_panelPrincipal).setValue('');
                                                
                                                _fieldByName('cdideper_',_p_21_panelPrincipal).setValue(record.get('CDIDEPER'));
                                                _fieldByName('cdideext_',_p_21_panelPrincipal).setValue(record.get('CDIDEEXT'));
                                                
                                                _fieldByName('nombre',_p_21_panelPrincipal).setValue(record.get('NOMBRECLI'));
                                                _fieldByName('codpostal',_p_21_panelPrincipal).setValue(record.get('CODPOSTAL'));
                                                
                                                _fieldByName('cdedo',_p_21_panelPrincipal).heredar(true,function()
                                                {
                                                    _fieldByName('cdedo',_p_21_panelPrincipal).setValue(record.get('CDEDO'));
                                                    _fieldByName('cdmunici',_p_21_panelPrincipal).heredar(true,function()
                                                    {
                                                        _fieldByName('cdmunici',_p_21_panelPrincipal).setValue(record.get('CDMUNICI'));
                                                    });
                                                });
                                                
                                                _fieldByName('dsdomici',_p_21_panelPrincipal).setValue(record.get('DSDOMICIL'));
//                                                _fieldByName('nmnumero',_p_21_panelPrincipal).setValue(record.get('NMNUMERO'));
//                                                _fieldByName('nmnumint',_p_21_panelPrincipal).setValue(record.get('NMNUMINT'));
                                                
                                                nmorddomProspecto = record.get('NMORDOM');
                                                
                                                /*debug('cliente obtenido de WS? ', json.clienteWS);
                                                gridTomadorp2.getView().getSelectionModel().getSelection()[0].set("cdrfc",record.get("RFCCLI"));
                                                if(json.clienteWS)
                                                {
                                                    gridTomadorp2.getView().getSelectionModel().getSelection()[0].set("cdideper",record.get("CDIDEPER"));
                                                }
                                                else
                                                {
                                                    gridTomadorp2.getView().getSelectionModel().getSelection()[0].set("cdperson",record.get("CLAVECLI"));
                                                    gridTomadorp2.getView().getSelectionModel().getSelection()[0].set("cdideper",record.get("CDIDEPER"));
                                                    gridTomadorp2.getView().getSelectionModel().getSelection()[0].set("swexiper",'S');
                                                }
                                                */
                                                grid.up().up().destroy();
                                            }
                                        }
                                    ]
                                }
                                ,{
                                    header     : 'RFC'
                                    ,dataIndex : 'RFCCLI'
                                    ,flex      : 1
                                }
                                ,{
                                    header     : 'Nombre'
                                    ,dataIndex : 'NOMBRECLI'
                                    ,flex      : 1
                                }
                                ,{
                                    header     : 'Direcci&oacute;n'
                                    ,dataIndex : 'DIRECCIONCLI'
                                    ,flex      : 3
                                }
                            ]
                        })
                    }).show());
                }
                else
                {
                    mensajeWarning('No hay coincidencias de RFC');
                }
            }
            ,failure:function()
            {
                _p21_tabpanel().setLoading(false);
                errorComunicacion();
            }
        });
    }
    
    debug('<_p21_rfcBlur');
}

function _p21_estiloEditores(cdplan)
{
    debug('>_p21_estiloEditores:',cdplan);
    Ext.Ajax.request(
    {
        url      : _p21_urlObtenerCoberturas
        ,params  :
        {
            'smap1.cdramo'    : _p21_smap1.cdramo
            ,'smap1.cdtipsit' : _p21_smap1.cdtipsit
            ,'smap1.cdplan'   : cdplan
        }
        ,success : function(response)
        {
            var json=Ext.decode(response.responseText);
            debug('*json',json);
            debug('params',_p21_smap1.cdramo, _p21_smap1.cdtipsit);
            
            var cobertura;
            
            Ext.Array.each(json.slist1,function(name,index,lista){
            		if(name.CDGARANT == "4MED"){
            			cobertura = name.CDGARANT;
            			debug('*cobertura',cobertura);
            			return false;
            		}
            });
            
            if(json.exito)
            {
            	debug('*Exito');
            	
            	//Peticion que devuelve la suma asegurada por default para la cobertura de Medicamentos en 10 a 49 Asegurados. ELP
            	Ext.Ajax.request(
				{
					url     : _p21_urlObtenerSumaAseguradaDefault
					,params :
					{
						'smap1.cdramo'     : _p21_smap1.cdramo
		                 ,'smap1.cdtipsit' : _p21_smap1.cdtipsit
		                 ,'smap1.cdgarant' : cobertura
					}
					,success : function (response)
					{
						var json2=Ext.decode(response.responseText);
						debug('**json2',json2);
						if(json.exito){
							var key = 0;
							var size = _p21_editorAsisInter.getStore().data.items.length;
							
							debug('**size',size);
							
							debug('**saMed',Ext.decode(response.responseText).saMed);
							
							for(var i=0; i < size; i++){
								if(_p21_editorAsisInter.getStore().getAt(i).data.key === Ext.decode(response.responseText).saMed){
									key = _p21_editorAsisInter.getStore().getAt(i).index;
									debug('**key',key);
									break;
								}
							}
							
							var _4HOS = false;
			                var _4AYM = false;
			                var _4AIV = false;
			                var _4EE  = false;
			                var _4MAT = false;
			                var _4MS  = false;
			                
			                debug(json.slist1);
			                
			                $.each(json.slist1,function(i,cob)
			                {
			                    debug('iterando:',cob.CDGARANT);
			                    if(cob.CDGARANT=='4HOS')
			                    {
			                        debug('_4HOS found');
			                        _4HOS=true;
			                    }
			                    if(cob.CDGARANT=='4AYM')
			                    {
			                        debug('_4AYM found');
			                        _4AYM=true;
			                        if(_p21_smap1.cdsisrol=='EJECUTIVOCUENTA'&&_p21_clasif==_p21_TARIFA_LINEA)
			                        {
			                            _4AYM=false;
			                        }
			                    }
			                    if(cob.CDGARANT=='4MED')
			                    {
			                        debug('_4AIV found');
			                        _4AIV=true;
			                    }
			                    if(cob.CDGARANT=='4EE')
			                    {
			                        debug('_4EE found');
			                        _4EE=true;
			                    }
			                    if(cob.CDGARANT=='4MAT')
			                    {
			                        debug('_4MAT found');
			                        _4MAT=true;
			                    }
			                    if(cob.CDGARANT=='4MS')
			                    {
			                        debug('_4MS found');
			                        _4MS=true;
			                    }
			                });
			                if(!_4HOS)
			                {
			                    _p21_editorDeducible.setValue('0');
			                    _p21_editorDeducible.addCls('_p21_editorLectura');
			                }
			                else
			                {
			                    _p21_editorDeducible.removeCls('_p21_editorLectura');
			                }
			                _p21_editorDeducible.setReadOnly(!_4HOS);
			                if(_p21_smap1.cdsisrol!='COTIZADOR')
			                {
			                    if(_p21_smap1.cdsisrol=='EJECUTIVOCUENTA'&&(_p21_clasif==_p21_TARIFA_LINEA||(cdplan=='PR'||cdplan=='PA')))	
			                    {
			                        _p21_editorAyudaMater.setValue('0');
			                        _p21_editorAyudaMater.addCls('_p21_editorLectura');
			                    }
			                    else
			                    {
			                        //if(!_4AYM||!_4HOS||_4MAT)
			                        if(_4MAT || _4HOS)
			                        {
			                            _p21_editorAyudaMater.setValue('0');
			                             debug('>~Oculto Ayuda Maternidad');
			                            _p21_editorAyudaMater.addCls('_p21_editorLectura');
			                             
			                        }
			                        //else if(_4AYM&&_4HOS&&!_4MAT)
			                        else if(!_4MAT)
			                        {
			                        	if(!_4HOS)
			                            {
			                            	_p21_editorAyudaMater.setValue('0');
			                            	_p21_editorAyudaMater.addCls('_p21_editorLectura');
			                            }else if(cdplan!='E'){
			                            	_p21_editorAyudaMater.removeCls('_p21_editorLectura');
			                            }
			                            
			                        }
			                    }
			                    //_p21_editorAyudaMater.setReadOnly(!_4AYM);
			                }
			                if(_p21_smap1.cdsisrol!='COTIZADOR')
			                {
			                    if(_p21_smap1.cdsisrol=='EJECUTIVOCUENTA'&&(_p21_clasif==_p21_TARIFA_LINEA||(cdplan=='PR'||cdplan=='PA')))
			                    {
			                    	if(cdplan == 'PA'){
			                    		//Se selecciona el segundo elemento, es decir, el siguiente depues de cero:
			                    		if(!Ext.isEmpty(key)){
			                    			_p21_editorAsisInter.setValue(_p21_editorAsisInter.getStore().getAt(key).data.key); 
			                            	_p21_editorAsisInter.addCls('_p21_editorLectura');                    			
			                    		}
			                    	}else{
			                    		if(!Ext.isEmpty(key)){
			                    			_p21_editorAsisInter.setValue(_p21_editorAsisInter.getStore().getAt(key).data.key); 
			                            	_p21_editorAsisInter.addCls('_p21_editorLectura');
			                    		}
			                    	}
			                    }
			                    else
			                    {
			                    	debug('!ejecutivo de cuenta');
			                        //if(!_4AIV||!_4MS)
			                    	_4MS=false;
			                        if(!_4MS)
			                        {
			                        	debug('Era 0 _4MS');
			                        	if(cdplan == 'PA'){
			                        		//Se selecciona el segundo elemento, es decir, el siguiente depues de cero:
			                        		if(!Ext.isEmpty(key)){
				                            	_p21_editorAsisInter.setValue(_p21_editorAsisInter.getStore().getAt(key).data.key); 
				                            	_p21_editorAsisInter.addCls('_p21_editorLectura');
			                        		}
			                        	}else{
			                        		_p21_editorAsisInter.setValue('0'); 
				                            _p21_editorAsisInter.addCls('_p21_editorLectura');
			                        	}
			                        }
			                        //else if(_4AIV&&_4MS)
			                        else if(_4MS)
			                        {
			                        	if(!_4HOS)
			                            {
			                            	debug('No encontro 4HOS');
			                            	_p21_editorEmerextr.setValue('0');
			                            	_p21_editorEmerextr.addCls('_p21_editorLectura');
			                            	if(!Ext.isEmpty(key)){
			                            		_p21_editorAsisInter.setValue(_p21_editorAsisInter.getStore().getAt(key).data.key); //Era 0
			                                	_p21_editorAsisInter.addCls('_p21_editorLectura');
			                            	}
			                            }else {
				                            	_p21_editorAsisInter.removeCls('_p21_editorLectura');
				                            	_p21_editorEmerextr.removeCls('_p21_editorLectura');
			                            }
			                        }
			                    }
			                }
			                //_p21_editorAsisInter.setReadOnly(!_4AIV);
			                if(_p21_smap1.cdsisrol!='COTIZADOR')
			                {
			                    if(!_4EE||cdplan=='PR'||!_4HOS)
			                    {
			                        _p21_editorEmerextr.setValue('N');
			                        _p21_editorEmerextr.addCls('_p21_editorLectura');
			                    }
			                    else if(_4EE&&cdplan!='PR'&&_4HOS)
			                    {
			                        _p21_editorEmerextr.removeCls('_p21_editorLectura');
			                    }
			                    //_p21_editorEmerextr.setReadOnly(!_4EE);
			                }
			                
			                /* pidieron que no se pueda ver EE si el plan es primario para los agentes */
			                if(_p21_smap1.cdsisrol=='EJECUTIVOCUENTA'&&_p21_clasif==_p21_TARIFA_LINEA&&(cdplan=='PR'||cdplan=='PA'))
			                {
			                    _p21_editorEmerextr.setValue('N');
			                    _p21_editorEmerextr.addCls('_p21_editorLectura');
			                }
			                /* pidieron que no se pueda ver EE si el plan es primario para los agentes */
						}else{
							debug('**fallo');
							debug('**json',json);
							mensajeError(json.respuesta);
						}
					}
					,failure : function(response){
						debug('**fallo');
						debug('**response',response);
					}
					//errorComunicacion
				});
            	debug('termina request key'); 
            }
            else
            {
                mensajeError(json.respuesta);
            }
        }
        ,failure : errorComunicacion
    });
    debug('<_p21_estiloEditores');
}


function _p21_subirCensoActualizado()
{
    debug('>_p21_subirCensoActualizado');
    
    var form = _p21_tabConcepto().down('[xtype=form]');
    
    var valido=form.isValid();
    if(!valido)
    {
        mensajeWarning('Verificar datos del contratante');
    }
    
    if(valido)
    {
        var data =
        {
            smap1 : form.getValues()
        };
        
        debug(">>>>>>>>> DATA <<<<<<<<<",data);
        
        
        data.smap1['cdunieco'] = _p21_smap1.cdunieco;
        data.smap1['cdramo']   = _p21_smap1.cdramo;
        data.smap1['estado']   = _p21_smap1.estado;
        data.smap1['nmpoliza'] = _p21_smap1.nmpoliza;
        data.smap1['nmsuplem'] = Ext.isEmpty(_p21_smap1.nmsuplem)?'0':_p21_smap1.nmsuplem;
        data.smap1['confirmaEmision'] = 'N';
        data.smap1['nmorddom'] = nmorddomProspecto;
        debug(">>>>>>>>> DATA final<<<<<<<<<",data);
        
        form.setLoading(true);
        Ext.Ajax.request(
        {
            url       : _p21_urlGuardarContratanteColectivo
            ,jsonData : data
            ,success  : function(response)
            {
                form.setLoading(false);
                var json = Ext.decode(response.responseText);
                if(json.exito)
                {
                	if(Ext.isEmpty(_fieldByName('cdperson',_p_21_panelPrincipal).getValue())){
	            		_fieldByName('cdperson',_p_21_panelPrincipal).setValue(json.smap1.cdperson);
	            	}
	            	
                    centrarVentanaInterna(Ext.create('Ext.window.Window',
                    {
                        title   : 'Cargar archivo de personas'
                        ,width  : 400
                        ,modal  : true
                        ,items  :
                        [
                            Ext.create('Ext.form.Panel',
                            {
                                url          : _p21_urlSubirCenso
                                ,items       :
                                [
                                    {
                                        xtype       : 'filefield'
                                        ,fieldLabel : 'Archivo'
                                        ,buttonText : 'Examinar...'
                                        ,buttonOnly : false
                                        ,name       : 'censo'
                                        ,labelAlign : 'top'
                                        ,width      : 330
                                        ,style      : 'margin:5px;'
                                        ,allowBlank : false
                                        ,msgTarget  : 'side'
                                        ,cAccept    : ['xls','xlsx']
                                        ,listeners  :
                                        {
                                            change : function(me)
                                            {
                                                var indexofPeriod = me.getValue().lastIndexOf("."),
                                                uploadedExtension = me.getValue().substr(indexofPeriod + 1, me.getValue().length - indexofPeriod).toLowerCase();
                                                if (!Ext.Array.contains(this.cAccept, uploadedExtension))
                                                {
                                                    centrarVentanaInterna(Ext.MessageBox.show(
                                                    {
                                                        title   : 'Error de tipo de archivo',
                                                        msg     : 'Extensiones permitidas: ' + this.cAccept.join(),
                                                        buttons : Ext.Msg.OK,
                                                        icon    : Ext.Msg.WARNING
                                                    }));
                                                    me.reset();
                                                }
                                            }
                                        }
                                    }
                                ]
                                ,buttonAlign : 'center'
                                ,buttons     :
                                [
                                    {
                                        text     : 'Cargar archivo'
                                        ,icon    : '${ctx}/resources/fam3icons/icons/group_edit.png'
                                        ,handler : function(me){ 
                                        	debug("Valor de me completo  :P ",me);
                                        	_p21_subirArchivoCompleto(me); 
                                        }
                                    }
                                ]
                            })
                        ]
                    }).show());
                }
                else
                {
                    mensajeWarning(json.respuesta);
                }
            }
            ,failure  : function()
            {
                form.setLoading(false);
                errorComunicacion();
            }
        });
    }
    debug('<_p21_subirCensoActualizado');
}

function _p21_subirDetallePersonas()
{
    debug('>_p21_subirDetallePersonas');
    
    var form = _p21_tabConcepto().down('[xtype=form]');
    
    var valido=form.isValid();
    if(!valido)
    {
        mensajeWarning('Verificar datos del contratante');
    }
    
    if(valido)
    {
        var data =
        {
            smap1 : form.getValues()
        };
        
        debug(">>>>>>>>> DATA <<<<<<<<<",data);
        
        
        data.smap1['cdunieco'] = _p21_smap1.cdunieco;
        data.smap1['cdramo']   = _p21_smap1.cdramo;
        data.smap1['estado']   = _p21_smap1.estado;
        data.smap1['nmpoliza'] = _p21_smap1.nmpoliza;
        data.smap1['nmsuplem'] = Ext.isEmpty(_p21_smap1.nmsuplem)?'0':_p21_smap1.nmsuplem;
        data.smap1['confirmaEmision'] = 'N';
        data.smap1['nmorddom'] = nmorddomProspecto;
        
        debug(">>>>>>>>> DATA final<<<<<<<<<",data);
        
        form.setLoading(true);
        Ext.Ajax.request(
        {
            url       : _p21_urlGuardarContratanteColectivo
            ,jsonData : data
            ,success  : function(response)
            {
                form.setLoading(false);
                var json = Ext.decode(response.responseText);
                if(json.exito)
                {
                	if(Ext.isEmpty(_fieldByName('cdperson',_p_21_panelPrincipal).getValue())){
	            		_fieldByName('cdperson',_p_21_panelPrincipal).setValue(json.smap1.cdperson);
	            	}
	            	
                    centrarVentanaInterna(Ext.create('Ext.window.Window',
                    {
                        title   : 'Cargar archivo de personas'
                        ,width  : 400
                        ,modal  : true
                        ,items  :
                        [
                            Ext.create('Ext.form.Panel',
                            {
                                url          : _p21_urlSubirCenso
                                ,items       :
                                [
                                    {
                                        xtype       : 'filefield'
                                        ,fieldLabel : 'Archivo'
                                        ,buttonText : 'Examinar...'
                                        ,buttonOnly : false
                                        ,name       : 'censo'
                                        ,labelAlign : 'top'
                                        ,width      : 330
                                        ,style      : 'margin:5px;'
                                        ,allowBlank : false
                                        ,msgTarget  : 'side'
                                        ,cAccept    : ['xls','xlsx']
                                        ,listeners  :
                                        {
                                            change : function(me)
                                            {
                                                var indexofPeriod = me.getValue().lastIndexOf("."),
                                                uploadedExtension = me.getValue().substr(indexofPeriod + 1, me.getValue().length - indexofPeriod).toLowerCase();
                                                if (!Ext.Array.contains(this.cAccept, uploadedExtension))
                                                {
                                                    centrarVentanaInterna(Ext.MessageBox.show(
                                                    {
                                                        title   : 'Error de tipo de archivo',
                                                        msg     : 'Extensiones permitidas: ' + this.cAccept.join(),
                                                        buttons : Ext.Msg.OK,
                                                        icon    : Ext.Msg.WARNING
                                                    }));
                                                    me.reset();
                                                }
                                            }
                                        }
                                    }
                                ]
                                ,buttonAlign : 'center'
                                ,buttons     :
                                [
                                    {
                                        text     : 'Cargar archivo'
                                        ,icon    : '${ctx}/resources/fam3icons/icons/group_edit.png'
                                        ,handler : function(me){ _p21_subirArchivoCompleto(me); }
                                    }
                                ]
                            })
                        ]
                    }).show());
                }
                else
                {
                    mensajeWarning(json.respuesta);
                }
            }
            ,failure  : function()
            {
                form.setLoading(false);
                errorComunicacion();
            }
        });
    }
    debug('<_p21_subirDetallePersonas');
}

function _p21_emitir()
{
    _p21_generarTramiteClic(_p21_generarVentanaVistaPrevia);
}

function _p21_tbloqueo(closable,callback,retry)
{
    Ext.Ajax.request(
    {
        url     : _p21_urlRecuperacionSimple
        ,params :
        {
            'smap1.procedimiento' : 'RECUPERAR_CONTEO_BLOQUEO'
            ,'smap1.cdunieco'     : _p21_smap1.cdunieco
            ,'smap1.cdramo'       : _p21_smap1.cdramo
            ,'smap1.estado'       : _p21_smap1.estado
            ,'smap1.nmpoliza'     : _p21_smap1.nmpoliza
        }
        ,success : function(response)
        {
            var ck = 'Decodificando conteo de bloqueos';
            try
            {
                var json=Ext.decode(response.responseText);
                debug('### conteo bloqueo:',json);
                if(json.exito)
                {
                    if(Number(json.smap1.CONTEO)>0)
                    {
                        centrarVentanaInterna(Ext.create('Ext.window.Window',
                        {
                            title     : 'Cotizaci\u00F3n en proceso...'
                            ,width    : 400
                            ,height   : 150
                            ,modal    : true
                            ,closable : false
                            ,html     : '<div style="padding:5px;border:0px solid black;">'
                                        +'Su cotizaci\u00F3n a\u00FAn se encuentra generando tarifa ('
                                        +json.smap1.CONTEO
                                        +').<br/>Intente m\u00E1s tarde</div>'
                            ,buttonAlign : 'center'
                            ,buttons     :
                            [
                                {
                                    text     : 'Regresar'
                                    ,icon    : '${icons}arrow_undo.png'
                                    ,handler : function(){ _p21_mesacontrol(); }
                                    ,hidden  : closable
                                }
                                ,{
                                    text     : 'Recargar'
                                    ,icon    : '${icons}arrow_refresh.png'
                                    ,handler : function(me){ me.up('window').setLoading(true); location.reload(); }
                                    ,hidden  : closable
                                }
                                ,{
                                    text     : 'Reintentar'
                                    ,icon    : '${icons}control_repeat_blue.png'
                                    ,handler : function(me){ me.up('window').destroy(); retry(); }
                                    ,hidden  : !(closable&&!Ext.isEmpty(retry))
                                }
                                ,{
                                    text     : 'Consultar m\u00e1s tarde'
                                    ,icon    : '${icons}accept.png'
                                    ,hidden  : !closable
                                    ,handler : function(me)
                                    {
                                        var ck = 'Marcando como pendiente de vista previa';
                                        try
                                        {
                                            _mask(ck);
                                            Ext.Ajax.request(
                                            {
                                                url      : _p21_urlMarcarTramitePendienteVistaPrevia
                                                ,params  :
                                                {
                                                    'params.ntramite' : _p21_ntramite
                                                }
                                                ,success : function(response)
                                                {
                                                    _unmask();
                                                    var ck = 'Decodificando respuesta al marcar como pendiente de vista previa';
                                                    try
                                                    {
                                                        var json = Ext.decode(response.responseText);
                                                        debug('### marcar swvispre:',json);
                                                        if(json.success==true)
                                                        {
                                                            _p21_mesacontrol();
                                                        }
                                                        else
                                                        {
                                                            mensajeError(json.message);
                                                        }
                                                    }
                                                    catch(e)
                                                    {
                                                        manejaException(e,ck);
                                                    }
                                                }
                                                ,failure : function()
                                                {
                                                    _unmask();
                                                    errorComunicacion(null,'Error al marcar como pendiente de vista previa');
                                                }
                                            });
                                        }
                                        catch(e)
                                        {
                                            manejaException(e,ck);
                                        }
                                    }
                                }
                            ]
                        }).show());
                    }
                    else
                    {
                        if(!Ext.isEmpty(callback))
                        {
                            callback();
                        }
                        else
                        {
                            _mask('Verificando vista previa');
                            Ext.Ajax.request(
                            {
                                url     : _p21_urlRecuperacion
                                ,params :
                                {
                                    'params.consulta'  : 'RECUPERAR_SWVISPRE_TRAMITE'
                                    ,'params.ntramite' : false == _p21_ntramite ? '' : _p21_ntramite
                                }
                                ,success : function(response)
                                {
                                    _unmask();
                                    var ck = 'Decodificando respuesta al verificar vista previa';
                                    try
                                    {
                                        var json = Ext.decode(response.responseText);
                                        debug('### swvispre:',json);
                                        if(json.success==true)
                                        {
                                            if(json.params.SWVISPRE=='S')
                                            {
                                                _p21_generarVentanaVistaPrevia(false);
                                            }
                                        }
                                        else
                                        {
                                            mensajeError(json.message);
                                        }
                                    }
                                    catch(e)
                                    {
                                        manejaException(e,ck);
                                    }
                                }
                                ,failure : function()
                                {
                                    _unmask();
                                    errorComunicacion(null,'Error al verificar vista previa');
                                }
                            });
                        }
                    }
                }
                else
                {
                    mensajeError(json.respuesta);
                }
            }
            catch(e)
            {
                manejaException(e,ck);
            }
        }
        ,failure : function()
        {
            me.setLoading(false);
            errorComunicacion(null,'Error al contar bloqueos');
        }
    });
}

function _p21_generarVentanaVistaPrevia(sinBotones)
{
    _mask('Cargando...');
    setTimeout(
	    function()
	    {
	        _unmask();
		    _p21_tbloqueo(
		        true
		        ,function()
		        {
		            _p21_generarVentanaVistaPrevia2(sinBotones);
		        }
		        ,function()
		        {
		            _p21_generarVentanaVistaPrevia(sinBotones);
		        }
		    );
        }
        ,2000
    );
}

function _p21_generarVentanaVistaPrevia2(sinBotones)
{
    var itemsVistaPrevia=[];
    
    var mostrarVentana= function()
    {
        var ventana = Ext.create('Ext.window.Window',
        {
            title     : 'Vista previa'
            ,width    : 800
            ,height   : 400
            ,closable : !Ext.isEmpty(sinBotones)&&sinBotones==true
            ,modal    : true
            ,items    :
            [
                Ext.create('Ext.tab.Panel',
                {
                    height : 300
                    ,items : itemsVistaPrevia
                })
            ]
            ,buttonAlign : 'center'
            ,buttons     :
            [
                {
                    text     : 'Vista previa'
                    ,icon    : '${ctx}/resources/fam3icons/icons/zoom.png'
                    ,hidden  : !Ext.isEmpty(sinBotones)&&sinBotones==true
                    ,handler : function(){_p21_imprimir2();}
                }
                ,{
                    text     : 'Emitir'
                    ,icon    : '${ctx}/resources/fam3icons/icons/key.png'
                    ,hidden  : !Ext.isEmpty(sinBotones)&&sinBotones==true
                    ,handler : function(){_p21_emitir2(ventana,this);}
                }
                ,{
                    text     : 'Cancelar'
                    ,icon    : '${ctx}/resources/fam3icons/icons/cancel.png'
                    ,hidden  : !Ext.isEmpty(sinBotones)&&sinBotones==true
                    ,handler : function(){ventana.destroy();}
                }
            ]
        });
        centrarVentanaInterna(ventana.show());
    }
    
    var rendererVP = function(val,md,rec)
    {
        if(rec.get('concepto')=='TOTAL DE HOMBRES'
           ||rec.get('concepto')=='TOTAL DE MUJERES')
        {
            return val;
        }
        else
        {
            return Ext.util.Format.usMoney(val);
        }
    };
    
    itemsVistaPrevia.push(
    Ext.create('Ext.grid.Panel',
    {
        title    : 'CONCEPTOS GLOBALES'
        ,itemId  : '_p21_gridConceptosGlobales'
        ,stores  : Ext.create('Ext.data.Store',
        {
            model : '_p21_vpModelo'
        })
        ,columns :
        [
            {
                text       : 'CONCEPTO'
                ,dataIndex : 'concepto'
                ,sortable  : false
                ,width     : 200
            }
            ,{
                text       : 'IMPORTE<br/>SUBGRUPO 1'
                ,dataIndex : 'subgrupo1'
                ,sortable  : false
                ,renderer  : rendererVP
                ,width     : 140
                ,hidden    : _p21_storeGrupos.getCount()<1
            }
            ,{
                text       : 'IMPORTE<br/>SUBGRUPO 2'
                ,dataIndex : 'subgrupo2'
                ,sortable  : false
                ,renderer  : rendererVP
                ,width     : 140
                ,hidden    : _p21_storeGrupos.getCount()<2
            }
            ,{
                text       : 'IMPORTE<br/>SUBGRUPO 3'
                ,dataIndex : 'subgrupo3'
                ,sortable  : false
                ,renderer  : rendererVP
                ,width     : 140
                ,hidden    : _p21_storeGrupos.getCount()<3
            }
            ,{
                text       : 'IMPORTE<br/>SUBGRUPO 4'
                ,dataIndex : 'subgrupo4'
                ,sortable  : false
                ,renderer  : rendererVP
                ,width     : 140
                ,hidden    : _p21_storeGrupos.getCount()<4
            }
            ,{
                text       : 'IMPORTE<br/>SUBGRUPO 5'
                ,dataIndex : 'subgrupo5'
                ,sortable  : false
                ,renderer  : rendererVP
                ,width     : 140
                ,hidden    : _p21_storeGrupos.getCount()<5
            }
            ,{
                text       : 'IMPORTE<br/>SUBGRUPO 6'
                ,dataIndex : 'subgrupo6'
                ,sortable  : false
                ,renderer  : rendererVP
                ,width     : 140
                ,hidden    : _p21_storeGrupos.getCount()<6
            }
            ,{
                text       : 'IMPORTE<br/>SUBGRUPO 7'
                ,dataIndex : 'subgrupo7'
                ,sortable  : false
                ,renderer  : rendererVP
                ,width     : 140
                ,hidden    : _p21_storeGrupos.getCount()<7
            }
            ,{
                text       : 'IMPORTE<br/>SUBGRUPO 8'
                ,dataIndex : 'subgrupo8'
                ,sortable  : false
                ,renderer  : rendererVP
                ,width     : 140
                ,hidden    : _p21_storeGrupos.getCount()<8
            }
            ,{
                text       : 'IMPORTE<br/>SUBGRUPO 9'
                ,dataIndex : 'subgrupo9'
                ,sortable  : false
                ,renderer  : rendererVP
                ,width     : 140
                ,hidden    : _p21_storeGrupos.getCount()<9
            }
            ,{
                text       : 'IMPORTE<br/>P&Oacute;LIZA'
                ,dataIndex : 'importe'
                ,sortable  : false
                ,renderer  : rendererVP
                ,width     : 140
            }
        ]
        ,listeners :
        {
            afterrender : function(me)
            {
                Ext.Ajax.request(
                {
                    url     : _p21_urlCargarConceptosGlobales
                    ,params :
                    {
                        'smap1.cdunieco'  : _p21_smap1.cdunieco
                        ,'smap1.cdramo'   : _p21_smap1.cdramo
                        ,'smap1.estado'   : _p21_smap1.estado
                        ,'smap1.nmpoliza' : _p21_smap1.nmpoliza
                        ,'smap1.nmsuplem' : '0'
                        ,'smap1.cdperpag' : _fieldByName('cdperpag').getValue()
                    }
                    ,success : function(response)
                    {
                        var json=Ext.decode(response.responseText);
                        debug('### obtener conceptos globales:',json);
                        if(json.exito)
                        {
                            me.getStore().removeAll();
                            me.getStore().add(new _p21_vpModelo(
                            {
                                concepto : 'PRIMA NETA'
                                ,importe : json.smap1.PRIMA_NETA
                            }));
                            me.getStore().add(new _p21_vpModelo(
                            {
                                concepto : 'DERECHOS DE POLIZA'
                                ,importe : json.smap1.DERPOL
                            }));
                            me.getStore().add(new _p21_vpModelo(
                            {
                                concepto : 'RECARGOS'
                                ,importe : json.smap1.RECARGOS
                            }));
                            me.getStore().add(new _p21_vpModelo(
                            {
                                concepto : 'IVA'
                                ,importe : json.smap1.IVA
                            }));
                            me.getStore().add(new _p21_vpModelo(
                            {
                                concepto : 'TOTAL DE 4 CONCEPTOS'
                                ,importe : 0
                            }));
                            me.getStore().add(new _p21_vpModelo(
                            {
                                concepto : 'TOTAL DE HOMBRES'
                                ,importe : 0
                            }));
                            me.getStore().add(new _p21_vpModelo(
                            {
                                concepto : 'TOTAL DE MUJERES'
                                ,importe : 0
                            }));
                            
                            _p21_storeGrupos.each(function(record)
						    {
						        itemsVistaPrevia.push(
						        Ext.create('Ext.grid.Panel',
						        {
						            title      : 'TARIFA SUBGRUPO '+record.get('letra')
						            ,minHeight : 100
						            ,maxHeight : 250
						            ,store     : Ext.create('Ext.data.Store',
						            {
						                model     : '_p21_modeloTarifaEdad'
						                ,grupo    : record.get('letra')
						                ,autoLoad : true
						                ,proxy    :
						                {
						                    type         : 'ajax'
						                    ,timeout     : 1000*60*2
						                    ,extraParams :
						                    {
						                        'smap1.cdunieco'  : _p21_smap1.cdunieco
						                        ,'smap1.cdramo'   : _p21_smap1.cdramo
						                        ,'smap1.estado'   : _p21_smap1.estado
						                        ,'smap1.nmpoliza' : _p21_smap1.nmpoliza
						                        ,'smap1.nmsuplem' : '0'
						                        ,'smap1.cdplan'   : record.get('cdplan')
						                        ,'smap1.cdgrupo'  : record.get('letra')
						                        ,'smap1.cdperpag' : _fieldByName('cdperpag').getValue()
						                    }
						                    ,url         : _p21_urlObtenerTarifaEdad
						                    ,reader      :
						                    {
						                        type  : 'json'
						                        ,root : 'slist1'
						                    }
						                }
						                ,listeners :
						                {
						                    load : function(me,records,success)
						                    {
						                        if(success)
						                        {
						                            var prima  = 0;
						                            var derpol = 0;
						                            var recar  = 0;
						                            var iva    = 0;
						                            var hom    = 0;
						                            var muj    = 0;
						                            var pTot   = 0;
						                            
						                            for(var ij in records)
						                            {
						                                var primaPaso  = Number(records[ij].get('TARIFA_TOTAL_HOMBRES')) + Number(records[ij].get('TARIFA_TOTAL_MUJERES'));
						                                var derpolPaso = Number(records[ij].get('DERPOL_TOTAL_GENERAL'));
						                                var recarPaso  = Number(records[ij].get('RECARGOS_TOTAL_GENERAL'));
						                                var ivaPaso    = Number(records[ij].get('IVA_TOTAL_GENERAL'));
						                                
						                                prima  += primaPaso;
						                                derpol += derpolPaso;
						                                recar  += recarPaso;
						                                iva    += ivaPaso;
						                                
						                                pTot   += primaPaso+derpolPaso+recarPaso+ivaPaso;
						                                
						                                hom    += Number(records[ij].get('HOMBRES'));
						                                muj    += Number(records[ij].get('MUJERES'));
						                            }
						                            
						                            _fieldById('_p21_gridConceptosGlobales').store.getAt(0).set('subgrupo'+me.grupo , prima);
						                            _fieldById('_p21_gridConceptosGlobales').store.getAt(1).set('subgrupo'+me.grupo , derpol);
						                            _fieldById('_p21_gridConceptosGlobales').store.getAt(2).set('subgrupo'+me.grupo , recar);
						                            _fieldById('_p21_gridConceptosGlobales').store.getAt(3).set('subgrupo'+me.grupo , iva);
						                            
						                            _fieldById('_p21_gridConceptosGlobales').store.getAt(4).set('subgrupo'+me.grupo , pTot);
						                            _fieldById('_p21_gridConceptosGlobales').store.getAt(4).set('importe',
						                                Number(_fieldById('_p21_gridConceptosGlobales').store.getAt(4).get('importe'))+pTot);
						                                
						                            _fieldById('_p21_gridConceptosGlobales').store.getAt(5).set('subgrupo'+me.grupo , hom);
						                            _fieldById('_p21_gridConceptosGlobales').store.getAt(5).set('importe',
						                                Number(_fieldById('_p21_gridConceptosGlobales').store.getAt(5).get('importe'))+hom);
						                            
						                            _fieldById('_p21_gridConceptosGlobales').store.getAt(6).set('subgrupo'+me.grupo , muj);
						                            _fieldById('_p21_gridConceptosGlobales').store.getAt(6).set('importe',
						                                Number(_fieldById('_p21_gridConceptosGlobales').store.getAt(6).get('importe'))+muj);
						                            
						                            _fieldById('_p21_gridConceptosGlobales').store.commitChanges();
						                        }
						                    }
						                }
						            })
						            ,columns   :
						            [
						                {
						                    header     : 'Edad'
						                    ,width     : 60
						                    ,dataIndex : 'EDAD'
						                }
						                ,{
						                    header     : 'No. Hombres'
						                    ,width     : 100
						                    ,dataIndex : 'HOMBRES'
						                }
						                ,{
						                    header     : 'No. Mujeres'
						                    ,width     : 100
						                    ,dataIndex : 'MUJERES'
						                }
						                ,{
						                    header     : 'Tarifa por hombre'
						                    ,flex      : 1
						                    ,dataIndex : 'TARIFA_UNICA_HOMBRES'
						                    ,renderer  : Ext.util.Format.usMoney
						                }
						                ,{
						                    header     : 'Tarifa por mujer'
						                    ,flex      : 1
						                    ,dataIndex : 'TARIFA_UNICA_MUJERES'
						                    ,renderer  : Ext.util.Format.usMoney
						                }
						                ,{
						                    header     : 'Total hombres'
						                    ,flex      : 1
						                    ,dataIndex : 'TARIFA_TOTAL_HOMBRES'
						                    ,renderer  : Ext.util.Format.usMoney
						                }
						                ,{
						                    header     : 'Total mujeres'
						                    ,flex      : 1
						                    ,dataIndex : 'TARIFA_TOTAL_MUJERES'
						                    ,renderer  : Ext.util.Format.usMoney
						                }
						            ]
						        })
						        );
						    });
                            
                        }
                        else
                        {
                            mensajeError(json.respuesta);
                        }
                    }
                    ,failure : errorComunicacion
                });
            }
        }
    }));
    
    mostrarVentana();
}

function _p21_emitir2(ventana,button)
{
    var params =
    {
        'smap1.cdunieco'  : _p21_smap1.cdunieco
        ,'smap1.cdramo'   : _p21_smap1.cdramo
        ,'smap1.cdtipsit' : _p21_smap1.cdtipsit
        ,'smap1.estado'   : _p21_smap1.estado
        ,'smap1.nmpoliza' : _p21_smap1.nmpoliza
        ,'smap1.cdperpag' : _fieldByName('cdperpag').getValue()
        ,'smap1.feini'    : Ext.Date.format(_fieldByName('feini').getValue(),'d/m/Y')
        ,'smap1.fefin'    : Ext.Date.format(_fieldByName('fefin').getValue(),'d/m/Y')
        ,'smap1.ntramite' : _p21_ntramite
    };
    debug('parametros para emitir:',params);
    ventana.setLoading(true);
    Ext.Ajax.request(
    {
        url      : _p21_urlEmitir
        ,params  : params
        ,success : function(response)
        {
            ventana.setLoading(false);
            var json=Ext.decode(response.responseText);
            debug('json response emitir:',json);
            if(json.exito)
            {
                button.hide();
                button.up().down('[text=Cancelar]').hide();
                button.up().add(
                [
                    {
                        xtype  : 'displayfield'
                        ,value : 'P&Oacute;LIZA '+json.smap1.nmpoliexEmi
                    }
                    ,{
                        xtype    : 'button'
                        ,text    : 'Documentos'
                        ,icon    : '${ctx}/resources/fam3icons/icons/printer.png'
                        ,handler : function(me)
                        {
                            mensajeWarning('Los documentos se est\u00e1n generando, pueden consultarse m\u00e1s tarde desde mesa de control cuando el tr\u00e1mite se encuentre en estatus Confirmado');
                            /*
                            var callbackRemesa = function()
                            {
	                            centrarVentanaInterna(Ext.create('Ext.window.Window',
	                            {
	                                title       : 'Documentos de la p&oacute;liza ' + json.smap1.nmpolizaEmi + ' [' + json.smap1.nmpoliexEmi + ']'
	                                ,width      : 500
	                                ,height     : 400
	                                ,autoScroll : true
	                                ,modal      : true
	                                ,cls        : 'VENTANA_DOCUMENTOS_CLASS'
	                                ,loader     :
	                                {
	                                    scripts   : true
	                                    ,autoLoad : true
	                                    ,url      : _p21_urlVentanaDocumentos
	                                    ,params   :
	                                    {
	                                        'smap1.cdunieco'  : _p21_smap1.cdunieco
	                                        ,'smap1.cdramo'   : _p21_smap1.cdramo
	                                        ,'smap1.estado'   : 'M'
	                                        ,'smap1.nmpoliza' : json.smap1.nmpolizaEmi
	                                        ,'smap1.nmsuplem' : json.smap1.nmsuplemEmi
	                                        ,'smap1.nmsolici' : json.smap1.nmpoliza
	                                        ,'smap1.ntramite' : _p21_ntramite
	                                        ,'smap1.tipomov'  : '0'
	                                    }
	                                }
	                            }).show());
                            };
                            _generarRemesaClic(
                                false
                                ,_p21_smap1.cdunieco
                                ,_p21_smap1.cdramo
                                ,'M'
                                ,json.smap1.nmpolizaEmi
                                ,callbackRemesa
                            );
                            */
                        }
                    }
                    ,{
                        xtype    : 'button'
                        ,text    : 'Mesa de control'
                        ,icon    : '${ctx}/resources/fam3icons/icons/house.png'
                        ,handler : _p21_mesacontrol
                    }
                ]);
                
                if(_p21_smap1.VENTANA_DOCUMENTOS=='S')
                {
                    Ext.ComponentQuery.query('[ventanaDocu]')[0].destroy();
                    debug('ventana de documentos destruida');
                }
                mensajeCorrecto('P&oacute;liza emitida'
                    ,json.respuesta+'<br/>Los documentos se est\u00e1n generando, pueden consultarse m\u00e1s tarde desde mesa de control cuando el tr\u00e1mite se encuentre en estatus Confirmado'
                );
            }
            else
            {
                mensajeError(json.respuesta);
            }
        }
        ,failure:function()
        {
            ventana.setLoading(false);
            errorComunicacion();
        }
    });
}

function _p21_imprimir()
{
    debug('>_p21_imprimir');
    var urlRequestImpCotiza = _p21_urlImprimirCotiza
            + '?p_unieco='      + _p21_smap1.cdunieco
            + '&p_ramo='        + _p21_smap1.cdramo
            + '&p_estado=W'
            + '&p_poliza='      + _p21_smap1.nmpoliza
            + '&p_suplem=0'
            + '&p_cdperpag='    + _fieldByName('cdperpag').getValue()
            + '&p_perpag='      + _fieldByName('cdperpag').getValue()
            + '&p_usuari='      + _p21_smap1.cdusuari
            + '&p_cdplan='
            + '&destype=cache'
            + "&desformat=PDF"
            + "&userid="        + _p21_reportsServerUser
            + "&ACCESSIBLE=YES"
            + "&report="        + _p21_nombreReporteCotizacion
            + "&paramform=no";
    debug(urlRequestImpCotiza);
    var numRand = Math.floor((Math.random() * 100000) + 1);
    debug(numRand);
    centrarVentanaInterna(Ext.create('Ext.window.Window',
    {
        title          : 'Cotizaci&oacute;n'
        ,width         : 700
        ,height        : 500
        ,collapsible   : true
        ,titleCollapse : true
        ,html : '<iframe innerframe="'
                + numRand
                + '" frameborder="0" width="100" height="100"'
                + 'src="'
                + _p21_urlViewDoc
                + "?contentType=application/pdf&url="
                + encodeURIComponent(urlRequestImpCotiza)
                + "\">"
                + '</iframe>'
        ,listeners :
        {
            resize : function(win,width,height,opt)
            {
                debug(width,height);
                $('[innerframe="'+ numRand+ '"]').attr(
                {
                    'width'   : width - 20
                    ,'height' : height - 60
                });
            }
        }
    }).show());
    debug('<_p21_imprimir');
}

function _p21_imprimir2()
{
    debug('>_p21_imprimir2');
    var urlRequestImpCotiza = _p21_urlImprimirCotiza
            + '?p_unieco='      + _p21_smap1.cdunieco
            + '&p_ramo='        + _p21_smap1.cdramo
            + '&p_estado=W'
            + '&p_poliza='      + _p21_smap1.nmpoliza
            + '&p_suplem=0'
            + '&p_cdperpag='    + _fieldByName('cdperpag').getValue()
            + '&p_perpag='      + _fieldByName('cdperpag').getValue()
            + '&p_usuari='      + _p21_smap1.cdusuari
            + '&p_cdplan='
            + '&destype=cache'
            + "&desformat=PDF"
            + "&userid="        + _p21_reportsServerUser
            + "&ACCESSIBLE=YES"
            + "&report="        + _p21_nombreReporteCotizacionDetalle
            + "&paramform=no";
    debug(urlRequestImpCotiza);
    var numRand = Math.floor((Math.random() * 100000) + 1);
    debug(numRand);
    centrarVentanaInterna(Ext.create('Ext.window.Window',
    {
        title          : 'Cotizaci&oacute;n'
        ,width         : 700
        ,height        : 500
        ,collapsible   : true
        ,titleCollapse : true
        ,html : '<iframe innerframe="'
                + numRand
                + '" frameborder="0" width="100" height="100"'
                + 'src="'
                + _p21_urlViewDoc
                + "?contentType=application/pdf&url="
                + encodeURIComponent(urlRequestImpCotiza)
                + "\">"
                + '</iframe>'
        ,listeners :
        {
            resize : function(win,width,height,opt)
            {
                debug(width,height);
                $('[innerframe="'+ numRand+ '"]').attr(
                {
                    'width'   : width - 20
                    ,'height' : height - 60
                });
            }
        }
    }).show());
    debug('<_p21_imprimir2');
}

function _p21_agentes()
{
    debug('>_p21_agentes');
    centrarVentanaInterna(Ext.create('Ext.window.Window',
    {
        title        : 'AGENTES'
        ,modal       : true
        ,buttonAlign : 'center'
        ,width       : 800
        ,height      : 400
        ,autoScroll  : true
        ,closeAction : 'destroy'
        ,loader      :
        {
            url       : _p21_urlPantallaAgentes
            ,scripts  : true
            ,autoLoad : true
        },
        listeners: {
        	close: function (){
        		try{
        			_ventanaGridAgentesSuperior.destroy();
        		}catch (e){
        			debugError('Error al destruir ventana de agentes.',e);
        		}
        	}
        }
    }).show());
    debug('<_p21_agentes');
}

function _p21_revisarAseguradosClic(grid,rowIndex)
{
    var record=grid.getStore().getAt(rowIndex);
    debug('>_p21_revisarAseguradosClic record:',record);
    var cdgrupo = record.get('letra')-0;
    _p21_quitarTabExtraprima(record.get('letra'));
    var plugins = [];
    plugins.push({
    	ptype       : 'pagingselectpersist'
    	,pluginId   : 'pagingselect'+cdgrupo
    	});
    if (_p21_smap1.EXTRAPRIMAS_EDITAR =='S'){
    	plugins.push(Ext.create('Ext.grid.plugin.RowEditing',{
    		clicksToEdit  : 1
    		,errorSummary : true
    		,pluginId     : 'rowedit'+cdgrupo
    		,cdgrupo      : cdgrupo
    		,listeners: {
    			edit: checkEdit
    			}
    	}));
    }
    _p21_agregarTab(
    {
        title                 : 'EXTRAPRIMAS DE SUBGRUPO '+record.get('letra')
        ,itemId               : 'id'+(new Date().getTime())
        ,extraprimaLetraGrupo : record.get('letra')
        ,defaults             : { style : 'margin:5px;' }
        ,border               : 0
         ,items                :
        [
            Ext.create('Ext.grid.Panel',
            {
                itemId		  :	'gridAseg'+cdgrupo
                ,cdgrupo      : cdgrupo
                ,selModel     : Ext.create('Ext.selection.CheckboxModel', 
                		{
                	mode: 				'MULTI',
                	showHeaderCheckbox: false,
                	cdgrupo           : cdgrupo
                	})
				,columns     : [ <s:property value='%{getImap().containsKey("extraprimasColumns")?getImap().get("extraprimasColumns").toString():""}' escapeHtml='false' /> ]
                ,width      : 980
                ,height     : 500
                ,plugins    : plugins
                ,tbar       :
                [
                    {
                        xtype       : 'textfield'
                        ,fieldLabel : '<span style="color:white;">Buscar:</span>'
                        ,timeoutFn  : ''
                        ,listeners  :
                        {
                            change : function(comp,val)
                            {
                                var timeoutFn = function()
                                {
                                    debug('extraprimas filtro change:',val);
                                    var grid = comp.up('grid');
                                    debug('grid:',grid);
                                    var filterFn = '';
                                    if(Ext.isEmpty(val))
                                    {
                                        filterFn = function(rec)
                                        {
                                            debug('funcion true');
                                            return true;
                                        };
                                    }
                                    else
                                    {
                                        filterFn = function(record)
	                                    {
	                                        
											var nombre = record.get('NOMBRE').toUpperCase().replace(/ /g,'');
	                                        var filtro = val.toUpperCase().replace(/ /g,'');
	                                        var posNombre = nombre.lastIndexOf(filtro);
	                                        
	                                        debug('filtro result:',posNombre > -1);
	                                        
	                                        if(posNombre > -1)
	                                        {
	                                            return true;
	                                        }
	                                        else
	                                        {
	                                            return false;
	                                        }
	                                    };
	                                }
	                                                                   
		                        };		                        
		                        clearTimeout(comp.timeoutFn);
		                        comp.timeoutFn = setTimeout(timeoutFn,3000);
                            }
                        }
                    }
                    ,{
                        xtype             : 'numberfield'
                        ,fieldLabel       : '<span style="color:white;">Extraprima ocupaci&oacute;n titulares:</span>'
                        ,name             : 'extrtitu'
                        ,grupo            : record.get('letra')
                        ,allowBlank       : false
                        ,allowDecimals    : true
                        ,decimalSeparator : '.'
                    }
                    ,{
                        xtype    : 'button'
                        ,text    : 'Aplicar'
                        ,icon    : '${ctx}/resources/fam3icons/icons/bell_link.png'
                        ,grupo   : cdgrupo
                        ,handler : _p21_guardarExtraprimasTitulares
                        ,listeners : { afterrender : function(me){ debug('boton afterrender cdgrupo', me.grupo) }}
                    }
                ]
                ,store      : Ext.create('Ext.data.Store',
                {
                    model       : '_p21_modeloExtraprima'
                    ,groupField : 'AGRUPADOR'
                    ,autoLoad   : false
                    ,pageSize   : 10
                    ,storeId    : '_p21_storeExtraprimas'+record.get('letra')
                    ,proxy      :
			        {
						type         : 'ajax'
   						,url         : _p21_urlCargarAseguradosExtraprimas
   						,callbackKey : 'callback'
   						,extraParams :
   						{
   							'smap1.cdunieco'  : _p21_smap1.cdunieco
   							,'smap1.cdramo'   : _p21_smap1.cdramo
   							,'smap1.estado'   : _p21_smap1.estado
   							,'smap1.nmpoliza' : _p21_smap1.nmpoliza
   							,'smap1.nmsuplem' : '0'
   							,'smap1.cdgrupo'  : record.get('letra')
   						}
   						,reader      :
   						{
   							type             : 'json'
   							,root            : 'slist1'
   							,successProperty : 'success'
   							,messageProperty : 'respuesta'
   							,totalProperty   : 'total'
   						}
   						,simpleSortMode: true
			        }
                	,listeners : {
                		'load' :  {
                			fn : function(store,records,successful) {
                				debug('reseteando los datos');
                				var selection = _fieldById('gridAseg'+cdgrupo).getPlugin('pagingselect'+cdgrupo).selection;
                 			    var mapselection = {};
                 			    for (var i = 0; i < selection.length; i++){
                 			    	debug('metiendo llave ',selection[i].data['nmsituac']);
                 			    	mapselection[selection[i].data['NMSITUAC']] = selection[i].data; 
                 			    }
                 			    debug('mapselection',mapselection);
                				for(var s in _fieldById('gridAseg'+cdgrupo).store.data.items){
                					var rec = _fieldById('gridAseg'+cdgrupo).store.getAt(s);
                					debug('nmsituac',rec.data['NMSITUAC']);
                					if (rec.data['NMSITUAC'] in mapselection){
                						debug('entro al map');
                						var obj = mapselection[rec.data['NMSITUAC']];
                						for(var y in rec.data){
                							rec.set(y, obj[y]);
                						}
                					}else{
                						for(var y in rec.data){
                							debug('rec',rec);
                							rec.set(y,rec.raw[y]);
                						}
                						rec.commit();
                						debug('termino commit');
                					}
                				}
                			}
                		}
                	}
                	})
                ,bbar :
                {
                    displayInfo : true
                    ,store      : Ext.getStore('_p21_storeExtraprimas'+record.get('letra'))
                    ,xtype      : 'pagingtoolbar'
                }
                ,viewConfig :
                {
                    listeners :
                    {
                        refresh : function(dataview)
                        {
                            Ext.each(dataview.panel.columns, function(column)
                            {
                                if(column.text=='Peso')
                                {
                                    column.flex  = 0
                                    column.width = 70;
                                }
                                else if(column.text=='Estatura')
                                {
                                    column.flex  = 0
                                    column.width = 70;
                                }
                                else if(column.text=='Ocupación')
                                {
                                    column.flex  = 0
                                    column.width = 140;
                                }
                                else if(column.text=='Nombre')
                                {
                                    column.flex  = 0
                                    column.width = 200;
                                }
                                else
                                {
                                    column.autoSize();
                                }
                            });
                        }
                    }
                }
                ,features   :
                [
                    {
                        groupHeaderTpl :
                        [
                            '{name:this.formatName}'
                            ,{
                                formatName : function(name)
                                {
                                    return name.split("_")[1];
                                }
                            }
                        ]
                        ,ftype          : 'groupingsummary'
                        ,startCollapsed : false
                    }
                ]
                ,listeners   :
                {
                    afterrender : function(me)
                    {
                    	debug('afterrender');
                        Ext.getStore('_p21_storeExtraprimas'+record.get('letra')).sort('NMSITUAC','ASC');
                    }
					,beforedeselect : beforedesel
                	,beforeedit	    : beforeed
                	,canceledit		: function(me){
                		_fieldById('btnguardar'+record.get('letra')).enable();
                	}
                }
                ,buttonAlign : 'center'
                ,buttons     :
                [
                    {
                        text     : 'Guardar'
						,itemId  : 'btnguardar'+record.get('letra')
                        ,icon    : '${ctx}/resources/fam3icons/icons/disk.png'
                        ,hidden  : _p21_smap1.EXTRAPRIMAS_EDITAR=='N'
                        ,handler : function()
                        {
                            _p21_guardarExtraprimas(record.get('letra'));
                            _p21_setActiveResumen();
                            _fieldById('gridAseg'+cdgrupo).store.commitChanges();
                        }
                    }
                ] 
            })
        ]
    });
    _fieldById('gridAseg'+cdgrupo).store.loadPage(1);
    debug('<_p21_revisarAseguradosClic');
}

function _cotcol_aseguradosClic(gridSubgrupo,rowIndexSubgrupo)
{
    var record=gridSubgrupo.getStore().getAt(rowIndexSubgrupo);
    debug('>_cotcol_aseguradosClic record:',record);
    _p21_quitarTabAsegurados(record.get('letra'));
    var columnas =
    [
        <s:property value='%{getImap().containsKey("aseguradosColumns")?getImap().get("aseguradosColumns").toString():""}' escapeHtml='false' />
    ];
    if(_p21_smap1.ASEGURADOS_EDITAR=='S')
    {
        columnas.push(
        {
            xtype         : 'actioncolumn'
            ,sortable     : false
            ,menuDisabled : true
            ,items        :
            [
                {
                    tooltip  : 'Quitar asegurado'
                    ,icon    : '${ctx}/resources/fam3icons/icons/delete.png'
                    ,handler : _cotcol_quitarAsegurado
                }
                ,{
                    tooltip  : 'Recuperar por RFC'
                    ,icon    : '${ctx}/resources/fam3icons/icons/vcard_edit.png'
                    ,handler : _p21_recuperarAsegurado
                }
                ,{
                    tooltip  : 'Editar datos b\u00E1sicos de persona'
                    ,icon    : '${icons}user_edit.png'
                    ,handler : _cotcol_editarDatosBaseAsegurado
                }
                ,{
                    tooltip  : 'Editar datos b\u00E1sicos de inciso'
                    ,icon    : '${icons}tag_red.png'
                    ,handler : _cotcol_mostrarVentanaActTvalosit
                }
                ,{
                    tooltip  : 'Editar persona/domicilio'
                    ,icon    : '${icons}report_key.png'
                    ,handler : _p21_editarAsegurado
                }
                ,{
                    tooltip  : 'Editar coberturas'
                    ,icon    : '${ctx}/resources/fam3icons/icons/text_list_bullets.png'
                    ,handler : _p21_editarCoberturas
                }
                ,{
                    tooltip  : 'Editar exclusiones'
                    ,icon    : '${ctx}/resources/fam3icons/icons/lock.png'
                    ,handler : _p21_editarExclusiones
                }
            ]
        });
    }
    
    var pluginTabAsegurados = [
        {
            ptype    : 'pagingselectpersist',
            pluginId : 'pagingselectasegurados'+record.get('letra')
        }
    ];
    
    if (_p21_smap1.ASEGURADOS_EDITAR === 'S') {
        pluginTabAsegurados.push(Ext.create('Ext.grid.plugin.RowEditing', {
            clicksToEdit  : 1,
            errorSummary : false
        }));
    }
    
    _p21_agregarTab(
    	    {
    	        title                 : 'ASEGURADOS DE SUBGRUPO '+record.get('letra')
    	        ,itemId               : 'id'+(new Date().getTime())
    	        ,aseguradosLetraGrupo : record.get('letra')
    	        ,defaults             : { style : 'margin:5px;' }
    	        ,border               : 0
    	        ,items                :
    	        [
    	            Ext.create('Ext.grid.Panel',
    	            {
    	            	itemId		: 'gridAsegurados'+record.get('letra')
    	            	,columns    : columnas
    	                ,width      : 980
    	                ,height     : 500
    	                ,plugins    : pluginTabAsegurados
    	                ,tbar       :
    	                [
    	                    {
    	                        xtype       : 'textfield'
    	                        ,fieldLabel : '<span style="color:white;">Buscar:</span>'
    	                        ,timeoutFn  : ''
    	                        ,listeners  :
    	                        {
    	                            change : function(comp,val)
    	                            {
    	                                var timeoutFn = function()
    	                                {
    	                                    debug('asegurados filtro change:',val);
    	                                    var grid = comp.up('grid');
    	                                    debug('grid:',grid);
    	                                    var filterFn = '';
    	                                    if(Ext.isEmpty(val))
    	                                    {
    	                                        filterFn = function(rec)
    	                                        {
    	                                            debug('funcion true');
    	                                            return true;
    	                                        };
    	                                    }
    	                                    else
    	                                    {
    	                                        filterFn = function(record, id)
    			                                {
    			                                    var nombre  = record.get('NOMBRE').toUpperCase().replace(/ /g,'');
    			                                    var nombre2 = record.get('SEGUNDO_NOMBRE').toUpperCase().replace(/ /g,'');
    			                                    var apat    = record.get('APELLIDO_PATERNO').toUpperCase().replace(/ /g,'');
    			                                    var amat    = record.get('APELLIDO_MATERNO').toUpperCase().replace(/ /g,'');
    			                                    
    			                                    var filtro = val.toUpperCase().replace(/ /g,'');
    			                                    var posNombre = (nombre+nombre2+apat+amat).lastIndexOf(filtro);
    			                                    
    			                                    if(posNombre > -1)
    			                                    {
    			                                        return true;
    			                                    }
    			                                    else
    			                                    {
    			                                        return false;
    			                                    }
    			                                };
    			                            }		                            
    	                                };                                
    	                                clearTimeout(comp.timeoutFn);
    	                                comp.timeoutFn = setTimeout(timeoutFn,3000);
    	                            }
    	                        }
    	                    }
    	                ]
    	                ,store      : Ext.create('Ext.data.Store',
    	                {
    	                    model       : '_p21_modeloAsegurados'
    	                    ,groupField : 'AGRUPADOR'
    	                    ,autoLoad   : false
    	                    ,pageSize   : 10
    	                    ,storeId    : '_p21_storeAsegurados'+record.get('letra')
    	                    ,gridSubgrupo     : gridSubgrupo
    	                    ,rowIndexSubgrupo : rowIndexSubgrupo
    	                    ,proxy      :
    	                    {
    	                        type         : 'ajax'
    	   						/* ,url         : _p21_urlCargarAseguradosGrupo */
    	   						,url         : _p21_urlCargarAseguradosGrupoPag
    	   						,callbackKey : 'callback'
    	   						,extraParams :
    	   						{
    	   							'smap1.cdunieco'  : _p21_smap1.cdunieco
    	   							,'smap1.cdramo'   : _p21_smap1.cdramo
    	   							,'smap1.estado'   : _p21_smap1.estado
    	   							,'smap1.nmpoliza' : _p21_smap1.nmpoliza
    	   							,'smap1.nmsuplem' : '0'
    	   							,'smap1.cdgrupo'  : record.get('letra')
    	   						}
    	   						,reader      :
    	   						{
    	   							type             : 'json'
    	   							,root            : 'slist1'
    	   							,successProperty : 'success'
    	   							,messageProperty : 'respuesta'
    	   							,totalProperty   : 'total'
    	   						}
    	   						,simpleSortMode: true
    	                    }
    	                })
    	                ,bbar :
    	                {
    	                    displayInfo : true
    	                    ,store      : Ext.getStore('_p21_storeAsegurados'+record.get('letra'))
    	                    ,xtype      : 'pagingtoolbar'
    	                }
    	                ,viewConfig : viewConfigAutoSize
    	                ,features   :
    	                [
    	                    {
    	                        groupHeaderTpl :
    	                        [
    	                            '{name:this.formatName}'
    	                            ,{
    	                                formatName : function(name)
    	                                {
    	                                    return name.split("_")[1];
    	                                }
    	                            }
    	                        ]
    	                        ,ftype          : 'groupingsummary'
    	                        ,startCollapsed : false
    	                    }
    	                ]
    	                ,listeners :
    	                {
    	                    afterrender : function(me)
    	                    {
    							debug('despues de editar registro');
    	                        Ext.getStore('_p21_storeAsegurados'+record.get('letra')).sort('NMSITUAC','ASC');
    	                    }
    	                }
    	                ,buttonAlign : 'center'
    	                ,buttons     :
    	                [
    	                    {
    	                        text     : 'Guardar'
    	                        ,icon    : '${ctx}/resources/fam3icons/icons/disk.png'
    	                        ,hidden  : _p21_smap1.ASEGURADOS_EDITAR!='S'
    	                        ,handler : function()
    	                        {
    	                            _p21_guardarAsegurados(this.up().up());
    	                        }
    	                    }
    	                ]
    	            })
    	        ]
    	    });
    _fieldById('gridAsegurados'+record.get('letra')).store.loadPage(1);
    debug('<_cotcol_aseguradosClic');
}



function _p21_sigsvalipol(callback)
{
    debug('>_p21_sigsvalipol');
    _p21_tabpanel().setLoading(true);
    Ext.Ajax.request(
    {
        url      : _p21_urlSigsvalipol
        ,params  :
        {
            'smap1.cdunieco'  : _p21_smap1.cdunieco
            ,'smap1.cdramo'   : _p21_smap1.cdramo
            ,'smap1.estado'   : _p21_smap1.estado
            ,'smap1.nmpoliza' : _p21_smap1.nmpoliza
            ,'smap1.nmsituac' : '0'
            ,'smap1.nmsuplem' : '0'
            ,'smap1.cdtipsit' : _p21_smap1.cdtipsit
        }
        ,success : function(response)
        {
            _p21_tabpanel().setLoading(false);
            var json=Ext.decode(response.responseText);
            debug('respuesta json sigsvalipol:',json);
            if(json.exito)
            {
                callback();
            }
            else
            {
                mensajeError(json.respuesta);
            }
        }
        ,failure : function()
        {
            _p21_tabpanel().setLoading(false);
            errorComunicacion();
        }
    });
    debug('<_p21_sigsvalipol');
}

function _p21_recuperarAsegurado(grid,rowIndex)
{
    var record=grid.getStore().getAt(rowIndex);
    debug('>_p21_recuperarAsegurado:',record.data);
    centrarVentanaInterna(Ext.create('Ext.window.Window',
    {
        title     : 'Recuperar asegurado'
        ,width    : 600
        ,height   : 370
        ,modal    : true
        ,defaults : { style : 'margin:5px;' }
        ,items    :
        [
            Ext.create('Ext.form.Panel',
            {
                border    : 0
                ,layout   : 'hbox'
                ,defaults : { style : 'margin:5px;' }
                ,items    :
                [
                    {
                        xtype       : 'textfield'
                        ,name       : 'rfc'
                        ,fieldLabel : 'RFC'
                        ,allowBlank : false
                    }
                    ,{
                        xtype    : 'button'
                        ,text    : 'Buscar'
                        ,icon    : '${ctx}/resources/fam3icons/icons/zoom.png'
                        ,handler : function(button)
                        {
                            var form=button.up('[xtype=form]');
                            var valido = true;
                            
                            if(valido)
                            {
                                valido = form.isValid();
                                if(!valido)
                                {
                                    datosIncompletos();
                                }
                            }
                            
                            if(valido)
                            {
                                cargaStorePaginadoLocal(form.up('[xtype=window]').down('[xtype=grid]').getStore()
                                    ,_p21_urlRecuperarPersona
                                    ,'slist1'
                                    ,{
                                        'map1.pv_rfc_i' : form.down('[name=rfc]').getValue()
                                    });
                            }
                        }
                    }
                ]
            })
            ,Ext.create('Ext.grid.Panel',
            {
                height      : 250
                ,viewConfig : viewConfigAutoSize
                ,columns    :
                [
                    {
                        xtype         : 'actioncolumn'
                        ,width        : 20
                        ,menuDisabled : true
                        ,sortable     : false
                        ,items        :
                        [
                            {
                                tooltip  : 'Recuperar'
                                ,icon    : '${ctx}/resources/fam3icons/icons/accept.png'
                                ,handler : function(grid2,row)
                                {
                                    grid2.up('[xtype=window]').setLoading(true);
                                    var record2=grid2.getStore().getAt(row);
                                    debug('recuperar a:',record2.data);
                                    record.set('RFC',record2.get('RFCCLI'));
                                    record.set('NOMBRE',record2.get('NOMBRE'));
                                    record.set('SEGUNDO_NOMBRE',record2.get('SNOMBRE'));
                                    record.set('APELLIDO_PATERNO',record2.get('APPAT'));
                                    record.set('APELLIDO_MATERNO',record2.get('APMAT'));
                                    record.set('CDIDEPER',record2.get('CDIDEPER'));
                                    record.set('CDPERSON',record2.get('CLAVECLI'));
                                    record.set('SWEXIPER','S');
                                    grid.refresh();
                                    grid2.up('[xtype=window]').destroy();
                                }
                            }
                        ]
                    }
                    <s:property value='%{getImap().containsKey("recuperadosColumns")?","+getImap().get("recuperadosColumns").toString():""}' />
                ]
                ,store      : Ext.create('Ext.data.Store',
                {
                    model : '_p21_modeloRecuperados'
                })
            })
        ]
    }).show());
    debug('<_p21_recuperarAsegurado');
}

/*
se mueve funcion a /js/proceso/emision/funcionesCotizacionGrupo.js
function _cotcol_editarDatosBaseAsegurado;
se mueve funcion a /js/proceso/emision/funcionesCotizacionGrupo.js
*/

function _p21_editarAsegurado(grid,rowIndex)
{
    var record=grid.getStore().getAt(rowIndex);
    debug('>_p21_editarAsegurado:',record.data);
    
    try{
    	_p22_parentCallback = false;
    	_callbackDomicilioAseg = false;

		var funcionEjecutar = new Function('destruirLoaderContratante'+_numIDpantalla+'();'); // alternativa de: eval('destruirLoaderContratante'+_numIDpantalla+'();');
		funcionEjecutar();
    	
    }catch(e){
    	debug('No se elimina loader de cliente, destruirLoaderContratante'+_numIDpantalla+'();',e);
    }
    
    try{
		_ventanaPersonas.destroy();    	
    }catch(e){
    	debug('No se elimina ventana contenedora de loader Persona, _ventanaPersonas.destroy();',e);	
    }
    
    var titularComoContratante = false;
    
    if(_fieldByName('cdreppag').getValue() == '2' || _fieldByName('cdreppag').getValue() == '3'){
    	if(!Ext.isEmpty(record.get('PARENTESCO')) && 'T' == record.get('PARENTESCO')){
			titularComoContratante = true;
    	}
    }
    
    
    if(titularComoContratante){
    	
    	_numIDpantalla = new Date().getTime(); 
    	
    	_ventanaPersonas = Ext.create('Ext.window.Window',
					        {
					            title      : 'Editar persona '+record.get('NOMBRE') + ' ' +record.get('APELLIDO_PATERNO')
					            ,modal     : true
					            ,autoScroll: true
					            ,width     : 900
					            ,height    : 900
					            ,loader    : 
					            {
				                    url       : _p25_urlPantallaPersonas
				                    ,scripts  : true
				                    ,autoLoad : true
				                    ,ajaxOptions: {
				                            method: 'POST'
				                     },
				                     params: {
									                'smap1.cdperson' : record.get('CDPERSON'),
									                'smap1.cdideper' : '',
									                'smap1.cdideext' : '',
									                'smap1.esSaludDanios' : 'S',
									                'smap1.esCargaClienteNvo' : 'N' ,
									                'smap1.ocultaBusqueda' : 'S' ,
									                'smap1.cargaCP' : '',
									                'smap1.cargaTipoPersona' : '',
									                'smap1.cargaSucursalEmi' : _p21_smap1.cdunieco,
									                'smap1.activaCveFamiliar': 'N',
									                'smap1.modoRecuperaDanios': 'N',
									                'smap1.modoSoloEdicion': 'S',
									                'smap1.idPantalla': _numIDpantalla
									            }
				                }
					        }).show();
					        centrarVentanaInterna(_ventanaPersonas);
					        
					        _p22_parentCallback = function(json){
							        record.set('RFC'              , json.smap1.CDRFC);
							        record.set('NOMBRE'           , json.smap1.DSNOMBRE);
							        record.set('SEGUNDO_NOMBRE'   , json.smap1.DSNOMBRE1);
							        record.set('APELLIDO_PATERNO' , json.smap1.DSAPELLIDO);
							        record.set('APELLIDO_MATERNO' , json.smap1.DSAPELLIDO1);
							        record.set('APELLIDO_MATERNO' , json.smap1.DSAPELLIDO1);
							        record.set('FECHA_NACIMIENTO' , json.smap1.FENACIMI);
							        record.set('NACIONALIDAD'     , json.smap1.CDNACION);
							        
							        _ventanaPersonas.close();
							};
    }else{
    	_ventanaPersonas = Ext.create('Ext.window.Window',
			{
		        title   : 'Editar persona '+record.get('NOMBRE') + ' ' +record.get('APELLIDO_PATERNO')
		        ,width  : 450
		        ,height : 400
		        ,modal  : true
		        ,loader : {
	            url       : _p25_urlPantallaDomicilio
	            ,params   :
	            {
	                'params.cdperson' : record.get('CDPERSON')
	            }
	            ,scripts  : true
	            ,autoLoad : true
        	}
		    }).show();
		    
		centrarVentanaInterna(_ventanaPersonas);
		
		_callbackDomicilioAseg = function(){
		        _ventanaPersonas.close();
		};
    }
    
    debug('<_p21_editarAsegurado');
}

function _p21_guardarAsegurados(grid,callback)
{
    debug('>_p21_guardarAsegurados grid:',grid);
    var store=grid.getStore();
    
    var valido = true;
    
    /*
    if(valido)
    {
        var error = '';
        $.each(store.datos,function(i,record)
        {
            if(Ext.isEmpty(record.get('NOMBRE')))
            {
                valido = false;
                error = error + 'Faltan los datos de la situaci&oacute;n '+record.get('NMSITUAC')+'<BR/>';
            }
        });
        if(!valido)
        {
            mensajeError(error);
        }
    }
    */
    
    var asegurados = [];
    
    if(valido)
    {
        for(var i in store.datos)
        {
            var record = store.datos[i];
            if(record.dirty)
            {
                asegurados.push(record);
            }
        }
        
        valido     = asegurados.length>0||callback!=undefined;
        if(!valido)
        {
            mensajeWarning('No hay cambios');
            _p21_setActiveResumen();
        }
    }
    
    debug('asegurados:',asegurados);
    
    if(valido&&asegurados.length>0)
    {
        debug('guardar:',asegurados);
        var slist1 = [];
        $.each(store.datos,function(i,irecord)
        {
            debug('iterando para guardar:',irecord);
            slist1.push(
            {
                nmsituac  : irecord.get('NMSITUAC')
                ,cdrol    : irecord.get('CDROL')
                ,cdperson : irecord.get('CDPERSON')
                ,swexiper : Ext.isEmpty(irecord.get('SWEXIPER'))?'N':irecord.get('SWEXIPER')
                ,cdideper : irecord.get('CDIDEPER')
            });
        });
        debug('slist1:',slist1);
        grid.setLoading(true);
        Ext.Ajax.request(
        {
            url       : _p21_urlGuardarAsegurados
            ,jsonData :
            {
                slist1 : slist1
                ,smap1 :
                {
                    cdunieco  : _p21_smap1.cdunieco
                    ,cdramo   : _p21_smap1.cdramo
                    ,cdtipsit : _p21_smap1.cdtipsit
                    ,estado   : _p21_smap1.estado
                    ,nmpoliza : _p21_smap1.nmpoliza
                    ,cdgrupo  : grid.up('[aseguradosLetraGrupo]').aseguradosLetraGrupo
                }
            }
            ,success : function(response)
            {
                grid.setLoading(false);
                var json=Ext.decode(response.responseText);
                debug('json response guardar asegurados:',json);
                if(json.exito)
                {
                    for(var i=0;i<asegurados.length;i++)
                    {
                        asegurados[i].commit();
                    }
                    if(callback!=undefined)
                    {
                        callback();
                    }
                    else
                    {
                        mensajeCorrecto('Datos guardados',json.respuesta);
                        _p21_setActiveResumen();
                    }
                }
                else
                {
                    mensajeError(json.respuesta);
                }
            }
            ,failure : function()
            {
                grid.setLoading(false);
            }
        });
    }
    
    if(valido&&asegurados.length==0)
    {
        callback();
    }
    
    debug('<_p21_guardarAsegurados');
}

function _p21_editarCoberturas(grid,row)
{
    var record=grid.getStore().getAt(row);
    debug('>_p21_editarCoberturas record:',record.data);
    _p21_guardarAsegurados(grid,function()
    {
        centrarVentanaInterna(Ext.create('Ext.window.Window',
        {
            title   : 'Editar coberturas de '+record.get('NOMBRE')
            ,width  : 900
            ,height : 500
            ,modal  : true
            ,loader :
            {
                url       : _p21_urlEditarCoberturas
                ,params   :
                {
                    'smap1.pv_cdunieco'  : _p21_smap1.cdunieco
                    ,'smap1.pv_cdramo'   : _p21_smap1.cdramo
                    ,'smap1.pv_estado'   : _p21_smap1.estado
                    ,'smap1.pv_nmpoliza' : _p21_smap1.nmpoliza
                    ,'smap1.pv_nmsituac' : record.get('NMSITUAC')
                    ,'smap1.pv_cdperson' : record.get('CDPERSON')
                    ,'smap1.pv_cdtipsit' : _p21_smap1.cdtipsit
                }
                ,scripts  : true
                ,autoLoad : true
            }
        }).show());
    });
    debug('<_p21_editarCoberturas');
}

function _p21_editarExclusiones(grid,row)
{
    var record=grid.getStore().getAt(row);
    debug('>_p21_editarExclusiones record:',record.data);
    _p21_guardarAsegurados(grid,function()
    {
        var ventana=Ext.create('Ext.window.Window',
        {
            title   : 'Editar exclusiones de '+(record.get('NOMBRE')+' '+(record.get('SEGUNDO_NOMBRE')?record.get('SEGUNDO_NOMBRE')+' ':' ')+record.get('APELLIDO_PATERNO')+' '+record.get('APELLIDO_MATERNO'))
            ,width  : 900
            ,height : 500
            ,modal  : true
            ,loader :
            {
                url       : _p21_urlEditarExclusiones
                ,params   :
                {
                    'smap1.pv_cdunieco'      : _p21_smap1.cdunieco
                    ,'smap1.pv_cdramo'       : _p21_smap1.cdramo
                    ,'smap1.pv_estado'       : _p21_smap1.estado
                    ,'smap1.pv_nmpoliza'     : _p21_smap1.nmpoliza
                    ,'smap1.pv_nmsituac'     : record.get('NMSITUAC')
                    ,'smap1.pv_nmsuplem'     : '0'
                    ,'smap1.pv_cdperson'     : record.get('CDPERSON')
                    ,'smap1.pv_cdrol'        : record.get('CDROL')
                    ,'smap1.nombreAsegurado' : record.get('NOMBRE')+' '+(record.get('SEGUNDO_NOMBRE')?record.get('SEGUNDO_NOMBRE')+' ':' ')+record.get('APELLIDO_PATERNO')+' '+record.get('APELLIDO_MATERNO')
                    ,'smap1.cdrfc'           : record.get('RFC')
                }
                ,scripts  : true
                ,autoLoad : true
            }
        }).show()
        centrarVentanaInterna(ventana);
        expande = function()
        {
            ventana.destroy();
        }
    });
    debug('<_p21_editarExclusiones');
}

function _p21_crearVentanaClausulas()
{
    debug('>_p21_crearVentanaClausulas<');
    
    _ventanaClausulas = Ext.create('Ext.window.Window',
    {
        title   : 'Exclusiones/Extraprimas (Cl&aacute;usulas)'
        ,width  : 800
        ,height : 500
        ,modal  : true
        ,loader : {
                url       : _p21_urlEditarExclusiones
                ,params   :
                {
                    'smap1.pv_cdunieco'      : _p21_smap1.cdunieco
                    ,'smap1.pv_cdramo'       : _p21_smap1.cdramo
                    ,'smap1.pv_estado'       : _p21_smap1.estado
                    ,'smap1.pv_nmpoliza'     : _p21_smap1.nmpoliza
                    ,'smap1.pv_nmsituac'     : '0'
                    ,'smap1.pv_nmsuplem'     : Ext.isEmpty(_p21_smap1.nmsuplem)?'0':_p21_smap1.nmsuplem
                    ,'smap1.pv_cdperson'     : ''
                    ,'smap1.pv_cdrol'        : ''
                    ,'smap1.nombreAsegurado' : 'POLIZA'
                    ,'smap1.cdrfc'           : ''
                }
                ,scripts  : true
                ,autoLoad : true
            }
        /*,loader :
        {
            scripts   : true
            ,autoLoad : true
            ,url      : _p21_urlPantallaClausulasPoliza
            ,params   :
            {
                'smap1.cdunieco'  : _p21_smap1.cdunieco
                ,'smap1.cdramo'   : _p21_smap1.cdramo
                ,'smap1.estado'   : _p21_smap1.estado
                ,'smap1.nmpoliza' : _p21_smap1.nmpoliza
                ,'smap1.nmsuplem' : Ext.isEmpty(_p21_smap1.nmsuplem)?'0':_p21_smap1.nmsuplem
            }
        }*/
    }).show();
    
    centrarVentanaInterna(_ventanaClausulas);
}

/*
se paso al archivo funcionesCotizacionGrupo.js por exceso de tamanio
function _p21_subirArchivoCompleto
se paso al archivo funcionesCotizacionGrupo.js por exceso de tamanio
*/

/*
se paso al archivo funcionesCotizacionGrupo.js por exceso de tamanio
function _p21_desbloqueoBotonRol(boton)
se paso al archivo funcionesCotizacionGrupo.js por exceso de tamanio
*/

////// funciones //////
<%-- include file="/jsp-script/proceso/documentos/scriptImpresionRemesaEmisionEndoso.jsp" --%>
</script>
<script src="${ctx}/js/proceso/emision/funcionesCotizacionGrupo.js?now=${now}"></script>
</head>
<body>
<div id="_p21_divpri" style="height:1400px;"></div>
</body>
</html>