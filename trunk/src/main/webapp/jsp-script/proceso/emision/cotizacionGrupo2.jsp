<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
.valorNoOriginal
{
    background : #FFFF99;
}
._p25_editorLectura
{
    visibility : hidden;
}
</style>
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
////// overrides //////

////// variables //////
var _p25_urlSubirCenso                  = '<s:url namespace="/emision"         action="subirCenso"                    />';
var _p25_urlVentanaDocumentos           = '<s:url namespace="/documentos"      action="ventanaDocumentosPoliza"       />';
var _p25_urlBuscarPersonas              = '<s:url namespace="/"                action="buscarPersonasRepetidas"       />';
var _p25_urlObtenerCoberturas           = '<s:url namespace="/emision"         action="obtenerCoberturasPlan"         />';
var _p25_urlObtenerHijosCobertura       = '<s:url namespace="/emision"         action="obtenerTatrigarCoberturas"     />';
var _p25_urlObtenerTarifaEdad           = '<s:url namespace="/emision"         action="cargarTarifasPorEdad"          />';
var _p25_urlObtenerTarifaCobertura      = '<s:url namespace="/emision"         action="cargarTarifasPorCobertura"     />';
var _p25_urlGenerarTramiteGrupo         = '<s:url namespace="/emision"         action="generarTramiteGrupo2"          />';
var _p25_urlVentanaDocumentos           = '<s:url namespace="/documentos"      action="ventanaDocumentosPoliza"       />';
var _p25_guardarReporteCotizacion       = '<s:url namespace="/emision"         action="guardarReporteCotizacionGrupo" />';
var _p25_urlActualizarStatus            = '<s:url namespace="/mesacontrol"     action="actualizarStatusTramite"       />';
var _p25_urlCargarParametros            = '<s:url namespace="/emision"         action="obtenerParametrosCotizacion"   />';
var _p25_urlMesaControl                 = '<s:url namespace="/mesacontrol"     action="mcdinamica"                    />';
var _p25_urlViewDoc                     = '<s:url namespace="/documentos"      action="descargaDocInline"             />';
var _p25_urlCargarDatosCotizacion       = '<s:url namespace="/emision"         action="cargarDatosCotizacionGrupo2"   />';
var _p25_urlCargarGrupos                = '<s:url namespace="/emision"         action="cargarGruposCotizacion2"       />';
var _p25_urlObtenerTvalogarsGrupo       = '<s:url namespace="/emision"         action="cargarTvalogarsGrupo"          />';
var _p25_urlCargarAseguradosExtraprimas = '<s:url namespace="/emision"         action="cargarAseguradosExtraprimas2"  />';
var _p25_urlGuardarSituaciones          = '<s:url namespace="/emision"         action="guardarValoresSituaciones"     />';
var _p25_urlSubirCensoCompleto          = '<s:url namespace="/emision"         action="subirCensoCompleto2"           />';
var _p25_urlCargarAseguradosGrupo       = '<s:url namespace="/emision"         action="cargarAseguradosGrupo"         />';
var _p25_urlRecuperarPersona            = '<s:url namespace="/"                action="buscarPersonasRepetidas"       />';
var _p25_urlPantallaPersonas            = '<s:url namespace="/catalogos"       action="includes/personasLoader"       />';
var _p25_urlEditarCoberturas            = '<s:url namespace="/"                action="editarCoberturas"              />';
var _p25_urlEditarExclusiones           = '<s:url namespace="/"                action="pantallaExclusion"             />';
var _p25_urlGuardarAsegurados           = '<s:url namespace="/emision"         action="guardarAseguradosCotizacion"   />';
var _p25_urlCargarConceptosGlobales     = '<s:url namespace="/emision"         action="cargarConceptosGlobalesGrupo"  />';
var _p25_urlEmitir                      = '<s:url namespace="/emision"         action="emitirColectivo"               />';
var _p25_urlVentanaDocumentosClon       = '<s:url namespace="/documentos"      action="ventanaDocumentosPolizaClon"   />';

var _p25_urlImprimirCotiza       = '<s:text name="ruta.servidor.reports" />';
var _p25_reportsServerUser       = '<s:text name="pass.servidor.reports" />';
var _p25_nombreReporteCotizacion = '<s:text name='%{"rdf.cotizacion.nombre."+smap1.cdtipsit.toUpperCase()}' />';

var _p25_smap1 = <s:property value='%{convertToJSON("smap1")}' escapeHtml="false" />;
debug('_p25_smap1:',_p25_smap1);

var _p25_TARIFA_LINEA       = 1;
var _p25_TARIFA_MODIFICADA  = 2;
var _p25_semaforoPlanChange = true;
var _p25_ntramite           = Ext.isEmpty(_p25_smap1.ntramite) ? false      : _p25_smap1.ntramite;
var _p25_ntramiteVacio      = Ext.isEmpty(_p25_smap1.ntramiteVacio) ? false : _p25_smap1.ntramiteVacio;
debug('_p25_ntramite:',_p25_ntramite,'_p25_ntramiteVacio:',_p25_ntramiteVacio);

var _p25_clasif;
var _p25_storeGrupos;
var _p25_tabGrupos;
var _p25_tabGruposLineal;
var _p25_tabGruposModifi;
var _p25_gridTarifas;

var _p25_filtroCobTimeout;

var _p22_parentCallback = false;

var _p25_editorNombreGrupo=
{
    xtype       : 'textfield'
    ,allowBlank : false
    ,minLength  : 3
};

var _p25_editorPlan = <s:property value="imap.editorPlanesColumn" />.editor;
_p25_editorPlan.on('change',_p25_editorPlanChange);
debug('_p25_editorPlan:',_p25_editorPlan);

var _p25_colsExtColumns =
[
    <s:if test='%{getImap().get("colsExtColumns")!=null}'>
        <s:property value="imap.colsExtColumns" escapeHtml="false" />
    </s:if>
];
debug('_p25_colsExtColumns:',_p25_colsExtColumns);

_p25_colsBaseColumns =
[
    <s:if test='%{getImap().get("colsBaseColumns")!=null}'>
        <s:property value="imap.colsBaseColumns" escapeHtml="false" />
    </s:if>
];
debug('_p25_colsBaseColumns:',_p25_colsBaseColumns);

for(var i=0;i<_p25_colsExtColumns.length;i++)
{
    var esExt = true;
    _p25_colsExtColumns[i]['flex']=0;
    _p25_colsExtColumns[i]['width']=150;
    for(var j=0;j<_p25_colsBaseColumns.length;j++)
    {
        if(i==0)
        {
            _p25_colsBaseColumns[j]['flex']=0;
            _p25_colsBaseColumns[j]['width']=150;
        }
        if(_p25_colsExtColumns[i].dataIndex==_p25_colsBaseColumns[j].dataIndex)
        {
            esExt = false;
        }
    }
    if(esExt)
    {
        _p25_colsExtColumns[i]['hidden']=_p25_smap1.LINEA_EXTENDIDA=='N';
    }
}
debug('_p25_colsExtColumns despues de marcar extendidas:',_p25_colsExtColumns);

var _p25_factoresColumns =
[
    <s:if test='%{getImap().get("factoresColumns")!=null}'>
        <s:property value="imap.factoresColumns" escapeHtml="false" />
    </s:if>
];
debug('_p25_factoresColumns:',_p25_factoresColumns);

var _p25_itemsRiesgo =
[
    <s:if test='%{getImap().get("itemsRiesgo")!=null}'>
        <s:property value="imap.itemsRiesgo" escapeHtml="false" />
    </s:if>
];

for(var i=0;i<_p25_itemsRiesgo.length;i++)
{
    _p25_itemsRiesgo[i].name='tvalopol_'+_p25_itemsRiesgo[i].name;
}
debug('_p25_itemsRiesgo:',_p25_itemsRiesgo);

////// variables //////

Ext.onReady(function()
{

    Ext.Ajax.timeout = 600000;

    ////// modelos //////
    var _p25_colsBaseFields =
    [
        <s:if test='%{getImap().get("colsBaseFields")!=null}'>
            <s:property value="imap.colsBaseFields" escapeHtml="false" />
        </s:if>
    ];
    debug('_p25_colsBaseFields:',_p25_colsBaseFields);
    
    var _p25_colsExtFields =
    [
        <s:if test='%{getImap().get("colsExtFields")!=null}'>
            <s:property value="imap.colsExtFields" escapeHtml="false" />
        </s:if>
    ];
    debug('_p25_colsExtFields:',_p25_colsExtFields);
    
    var _p25_factoresFields =
    [
        <s:if test='%{getImap().get("factoresFields")!=null}'>
            <s:property value="imap.factoresFields" escapeHtml="false" />
        </s:if>
    ];
    debug('_p25_factoresFields:',_p25_factoresFields);
    
    var _p25_modeloGrupoFields =
    [
        'letra'
        ,'nombre'
        ,'cdplan'
    ];
    
    for(var i=0;i<_p25_factoresFields.length;i++)
    {
        _p25_modeloGrupoFields.push(_p25_factoresFields[i]);
    }
    
    for(var i=0;i<_p25_colsBaseFields.length;i++)
    {
        _p25_modeloGrupoFields.push(_p25_colsBaseFields[i]);
    }
    
    for(var i=0;i<_p25_colsExtFields.length;i++)
    {
        var esBase = false;
        for(var j=0;j<_p25_colsBaseFields.length;j++)
        {
            if(_p25_colsBaseFields[j].name==_p25_colsExtFields[i].name)
            {
                esBase = true;
            }
        }
        if(!esBase)
        {
            _p25_modeloGrupoFields.push(_p25_colsExtFields[j]);
        }
    }
    
    debug('_p25_modeloGrupoFields:',_p25_modeloGrupoFields);
    
    Ext.define('_p25_modeloGrupo',
    {
        extend  : 'Ext.data.Model'
        ,fields : _p25_modeloGrupoFields
    });
    
    Ext.define('RFCPersona',
    {
        extend  : 'Ext.data.Model'
        ,fields : ["RFCCLI","NOMBRECLI","FENACIMICLI","DIRECCIONCLI","CLAVECLI","DISPLAY", "CDIDEPER"
        ,'CODPOSTAL','CDEDO','CDMUNICI','DSDOMICIL','NMNUMERO','NMNUMINT']
    });
    
    Ext.define('_p25_modeloTarifaEdad',
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
        ]
    });
    
    Ext.define('_p25_modeloTarifaCobertura',
    {
        extend  : 'Ext.data.Model'
        ,fields :
        [
            'DSGARANT'
            ,{type:'float',name:'PRIMA_PROMEDIO'}
        ]
    });
    
    var _p25_extraprimaFields =
    [
        'nmsituac'
        ,'nombre'
        ,'familia'
        ,'titular'
        ,'parentesco'
        ,'agrupador'
        <s:if test='%{getImap().containsKey("extraprimasFields")&&getImap().get("extraprimasFields")!=null}'>
            ,<s:property value="imap.extraprimasFields" escapeHtml="false" />
        </s:if>
    ];
    debug('_p25_extraprimaFields:',_p25_extraprimaFields);
    Ext.define('_p25_modeloExtraprima',
    {
        extend  : 'Ext.data.Model'
        ,fields : _p25_extraprimaFields
    });
    
    Ext.define('_p25_modeloAsegurados',
    {
        extend  : 'Ext.data.Model'
        ,fields : [ <s:property value='%{getImap().containsKey("aseguradosFields")?getImap().get("aseguradosFields").toString():""}' /> ]
    });
    
    Ext.define('_p25_modeloRecuperados',
    {
        extend  : 'Ext.data.Model'
        ,fields : [ <s:property value='%{getImap().containsKey("recuperadosFields")?getImap().get("recuperadosFields").toString():""}' /> ]
    });
    
    Ext.define('_p25_vpModelo',
    {
        extend  : 'Ext.data.Model'
        ,fields : [ 'concepto' , { name : 'importe',type :'float' }]
    });
    ////// modelos //////
    
    ////// stores //////
    _p25_storeGrupos = Ext.create('Ext.data.Store',
    {
        model : '_p25_modeloGrupo'
    });
    ////// stores //////
    
    ////// componentes //////
    var botoneslinea = [];
    if(_p25_smap1.DETALLE_LINEA=='S')
    {
        botoneslinea.push(
        {
            tooltip   : 'Editar'
            ,icon     : '${ctx}/resources/fam3icons/icons/pencil.png'
            ,handler  : _p25_editarGrupoClic
        });
    }
    if(!_p25_ntramite)
    {
        botoneslinea.push(
        {
            tooltip  : 'Borrar subgrupo'
            ,icon    : '${ctx}/resources/fam3icons/icons/delete.png'
            ,handler : _p25_borrarGrupoClic
        });
    }
    if(_p25_smap1.ASEGURADOS=='S')
    {
        botoneslinea.push(
        {
            tooltip  : 'Asegurados'
            ,icon    : '${ctx}/resources/fam3icons/icons/group.png'
            ,handler : _p25_aseguradosClic
        });
    }
    if(_p25_smap1.EXTRAPRIMAS=='S')
    {
        botoneslinea.push(
        {
            tooltip  : 'Revisar extraprimas'
            ,icon    : '${ctx}/resources/fam3icons/icons/group_error.png'
            ,handler : _p25_revisarAseguradosClic
        });
    }
    
    _p25_tabGruposLinealCols =
    [
        {
            header     : 'ID'
            ,dataIndex : 'letra'
            ,width     : 40
        }
        ,{
            header     : 'NOMBRE'
            ,dataIndex : 'nombre'
            ,width     : 150
            ,editor    : _p25_editorNombreGrupo
        }
        ,{
            header     : 'PLAN'
            ,dataIndex : 'cdplan'
            ,width     : 100
            ,editor    : _p25_editorPlan
            ,renderer  : function(v)
            {
                return rendererColumnasDinamico(v,'cdplan');
            }
        }
    ];
    
    Ext.Array.each(_p25_colsExtColumns,function(col)
    {
        _p25_tabGruposLinealCols.push(col);
    });
    
    _p25_tabGruposLinealCols.push(
    {
        xtype         : 'actioncolumn'
        ,sortable     : false
        ,menuDisabled : true
        ,width        : (botoneslinea.length*20) + 10
        ,items        : botoneslinea
    });
    
    _p25_tabGruposLineal =
    {
        title     : 'RESUMEN SUBGRUPOS'
        ,itemId   : '_p25_tabGruposLineal'
        ,defaults : { style : 'margin:5px' }
        ,border   : 0
        ,items    : Ext.create('Ext.grid.Panel',
        {
            tbar     :
            [
                {
                    text     : 'Agregar'
                    ,icon    : '${ctx}/resources/fam3icons/icons/add.png'
                    ,handler : _p25_agregarGrupoClic
                    ,hidden  : _p25_ntramite ? true : false
                }
            ]
            ,columns : _p25_tabGruposLinealCols
            ,store   : _p25_storeGrupos
            ,height  : 250
            ,plugins : Ext.create('Ext.grid.plugin.RowEditing',
            {
                clicksToEdit  : 1
                ,errorSummary : false
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
                    _p25_setActiveConcepto();
                }
            }
        ]
    };
    
    _p25_tabGruposModifiCols =
    [
        {
            header     : 'ID'
            ,dataIndex : 'letra'
            ,width     : 40
        }
        ,{
            header     : 'NOMBRE'
            ,dataIndex : 'nombre'
            ,width     : 150
            ,editor    : _p25_editorNombreGrupo
        }
        ,{
            header     : 'PLAN'
            ,dataIndex : 'cdplan'
            ,width     : 100
            ,editor    : _p25_editorPlan
            ,renderer  : function(v)
            {
                return rendererColumnasDinamico(v,'cdplan');
            }
        }
    ];
    
    Ext.Array.each(_p25_colsBaseColumns,function(col)
    {
        _p25_tabGruposModifiCols.push(col);
    });
    
    var botonesModificada =
    [
        {
            tooltip  : 'Editar'
            ,icon    : '${ctx}/resources/fam3icons/icons/pencil.png'
            ,handler : _p25_editarGrupoClic
        }
    ];
    if(!_p25_ntramite)
    {
        botonesModificada.push(
        {
            tooltip   : 'Borrar subgrupo'
            ,icon     : '${ctx}/resources/fam3icons/icons/delete.png'
            ,handler  : _p25_borrarGrupoClic
        });
    }
    if(_p25_smap1.ASEGURADOS=='S')
    {
        botonesModificada.push(
        {
            tooltip  : 'Asegurados'
            ,icon    : '${ctx}/resources/fam3icons/icons/group.png'
            ,handler : _p25_aseguradosClic
        });
    }
    if(_p25_smap1.EXTRAPRIMAS=='S')
    {
        botonesModificada.push(
        {
            tooltip  : 'Revisar extraprimas'
            ,icon    : '${ctx}/resources/fam3icons/icons/group_error.png'
            ,handler : _p25_revisarAseguradosClic
        });
    }
    
    _p25_tabGruposModifiCols.push(
    {
        xtype         : 'actioncolumn'
        ,sortable     : false
        ,menuDisabled : true
        ,width        : (botonesModificada.length*20) + 10
        ,items        : botonesModificada
    });
    
    _p25_tabGruposModifi =
    {
        title     : 'RESUMEN SUBGRUPOS'
        ,itemId   : '_p25_tabGruposModifi'
        ,defaults : { style : 'margin:5px' }
        ,border   : 0
        ,items    : Ext.create('Ext.grid.Panel',
        {
            tbar     :
            [
                {
                    text     : 'Agregar'
                    ,icon    : '${ctx}/resources/fam3icons/icons/add.png'
                    ,handler : _p25_agregarGrupoClic
                    ,hidden  : _p25_ntramite ? true : false
                }
            ]
            ,columns : _p25_tabGruposModifiCols
            ,store   : _p25_storeGrupos
            ,height  : 250
            ,plugins : Ext.create('Ext.grid.plugin.RowEditing',
            {
                clicksToEdit  : 1
                ,errorSummary : true
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
                    _p25_setActiveConcepto();
                }
            }
        ]
    };
    ////// componentes //////
    
    ////// contenido //////
    Ext.create('Ext.tab.Panel',
    {
        renderTo   : '_p25_divpri'
        ,itemId    : '_p25_tabpanel'
        ,border    : 0
        ,items     :
        [
            {
                title       : 'CONCEPTO'
                ,itemId     : '_p25_tabConcepto'
                ,border     : 0
                ,defaults   : { style : 'margin:5px' }
                ,items      :
                [
                    Ext.create('Ext.form.Panel',
                    {
                        border : 0
                        ,xtype : 'form'
                        ,url   : _p25_urlSubirCenso
                        ,items :
                        [
                            {
                                xtype   : 'fieldset'
                                ,title  : '<span style="font:bold 14px Calibri;">DATOS DEL CONTRATANTE</span>'
                                ,layout :
                                {
                                    type     : 'table'
                                    ,columns : 2
                                }
                                ,items  : [ <s:property value="imap.itemsContratante" /> ]
                            }
                            ,{
                                xtype     : 'fieldset'
                                ,title    : '<span style="font:bold 14px Calibri;">INFORMACIÓN DEL RIESGO</span>'
                                ,layout   :
                                {
                                    type     : 'table'
                                    ,columns : 2
                                }
                                ,defaults : {  style : 'margin : 5px;' }
                                ,items    : _p25_itemsRiesgo
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
                                    Ext.create('Ext.form.field.Number',
                                    {
                                        fieldLabel : 'TR&Aacute;MITE'
                                        ,itemId    : '_p25_fieldNtramite'
                                        ,readOnly  : true
                                        ,name      : 'ntramite'
                                    })
                                    ,Ext.create('Ext.form.field.Number',
                                    {
                                        fieldLabel : 'COTIZACI&Oacute;N'
                                        ,itemId    : '_p25_fieldNmpoliza'
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
                                ]
                            }
                            ,{
                                xtype   : 'fieldset'
                                ,title  : '<span style="font:bold 14px Calibri;">DATOS DEL AGENTE</span>'
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
                                ,hidden   : _p25_ntramite ? true : false
                                ,items    :
                                [
                                    {
                                        xtype    : 'button'
                                        ,text    : 'De 10 a 49 asegurados'
                                        ,scale   : 'medium'
                                        ,handler : _p25_construirLinea
                                    }
                                    ,{
                                        xtype    : 'button'
                                        ,text    : '50 o más asegurados'
                                        ,scale   : 'medium'
                                        ,handler : _p25_construirModificada
                                    }
                                ]
                            }
                            ,{
                                xtype     : 'fieldset'
                                ,title    : '<span style="font:bold 14px Calibri;">CENSO</span>'
                                ,defaults : { style : 'margin:5px;' }
                                ,hidden   : _p25_ntramite ? true : false
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
                                        ,allowBlank : _p25_ntramite ? true : false
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
                        ,buttons     :
                        [
                            <s:if test='%{getImap().get("botones")!=null}'>
                                <s:property value="imap.botones" />,
                            </s:if>
                            {
                                text     : 'Limpiar'
                                ,icon    : '${ctx}/resources/fam3icons/icons/arrow_refresh.png'
                                ,handler : _p25_cotizarNueva
                                ,hidden  : _p25_ntramite ? true : false
                            }
                        ]
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
                        if(
                            grid.up('panel').title!='RESUMEN SUBGRUPOS'
                            &&records.length>0
                            &&grid.title!='FACTORES DEL SUBGRUPO'
                            )
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
    
    if(_p25_smap1.VENTANA_DOCUMENTOS=='S')
    {
        Ext.create('Ext.window.Window',
        {
            title           : 'Documentos del tr&aacute;mite ' + _p25_ntramite
            ,ventanaDocu    : true
            ,closable       : false
            ,width          : 500
            ,height         : 300
            ,autoScroll     : true
            ,collapsible    : true
            ,titleCollapse  : true
            ,startCollapsed : true
            ,resizable      : false
            ,loader         :
            {
                scripts   : true
                ,autoLoad : true
                ,url      : _p25_urlVentanaDocumentos
                ,params   :
                {
                    'smap1.cdunieco'  : _p25_smap1.cdunieco
                    ,'smap1.cdramo'   : _p25_smap1.cdramo
                    ,'smap1.estado'   : 'W'
                    ,'smap1.nmpoliza' : '0'
                    ,'smap1.nmsuplem' : '0'
                    ,'smap1.nmsolici' : '0'
                    ,'smap1.ntramite' : _p25_ntramite
                    ,'smap1.tipomov'  : '0'
                }
            }
        }).showAt(500, 0);
    }
    ////// contenido //////
    
    ////// custom //////
    _fieldByLabel('RFC').on(
    {
        'blur'    : _p25_rfcBlur
        ,'change' : function()
        {
            _fieldByName('cdperson').reset();
        }
    });
    
    if(_p25_smap1.BLOQUEO_CONCEPTO=='S')
    {
        var items=Ext.ComponentQuery.query('[name]',_p25_tabConcepto());
        $.each(items,function(i,item)
        {
            item.setReadOnly(true);
        });
    }
    
    _fieldByName('cdreppag').on(
    {
        select : function(comp,arr)
        {
            var val=_fieldByName('cdreppag').getValue();
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
    ////// custom //////
    
    ////// loaders //////
    if(_p25_ntramiteVacio)
    {
        _fieldByName('ntramite').setValue(_p25_ntramiteVacio);
    }
    else if(_p25_ntramite)
    {
        _p25_tabpanel().setLoading(true);
        Ext.Ajax.request(
        {
            url     : _p25_urlCargarDatosCotizacion
            ,params :
            {
                'smap1.cdunieco'  : _p25_smap1.cdunieco
                ,'smap1.cdramo'   : _p25_smap1.cdramo
                ,'smap1.cdtipsit' : _p25_smap1.cdtipsit
                ,'smap1.estado'   : _p25_smap1.estado
                ,'smap1.nmpoliza' : _p25_smap1.nmpoliza
                ,'smap1.ntramite' : _p25_smap1.ntramite
            }
            ,success : function(response)
            {
                _p25_tabpanel().setLoading(false);
                var json=Ext.decode(response.responseText);
                debug('### cargar cotizacion response:',json);
                if(json.exito)
                {
                    for(var prop in json.params)
                    {
                        if(prop!='cdmunici'&&prop!='clasif')
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
                                var comp=_fieldByName(prop,null,true);
                                if(!Ext.isEmpty(comp))
                                {
                                    comp.setValue(json.params[prop]);
                                }
                            }
                        }
                    }
                    _fieldByName('cdmunici').setValue(json.params['cdmunici']);
                    _p25_clasif = json.params['clasif'];
                    debug('_p25_clasif:',_p25_clasif);
                    var auxCargarGrupos=function(callback)
                    {
                        _p25_tabpanel().setLoading(true);
                        Ext.Ajax.request(
                        {
                            url      : _p25_urlCargarGrupos
                            ,params  :
                            {
                                'smap1.cdunieco'  : _p25_smap1.cdunieco
                                ,'smap1.cdramo'   : _p25_smap1.cdramo
                                ,'smap1.estado'   : _p25_smap1.estado
                                ,'smap1.nmpoliza' : _p25_smap1.nmpoliza
                            }
                            ,success : function(response)
                            {
                                _p25_tabpanel().setLoading(false);
                                var json2=Ext.decode(response.responseText);
                                debug('### cargar grupos response:',json2);
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
                                _p25_tabpanel().setLoading(false);
                                errorComunicacion();
                            }
                        });
                    };
                    if(_p25_clasif==_p25_TARIFA_LINEA&&_p25_smap1.LINEA_EXTENDIDA=='S')
                    {
                        debug('>cargar linea');
                        auxCargarGrupos(function(resp)
                        {
                            debug('>callback linea');
                            _p25_construirLinea(0,0,true);
                            _p25_setActiveConcepto();
                            var aux=resp.slist1.length;
                            var aux2=0;
                            for(var i=0;i<aux;i++)
                            {
                                _p25_storeGrupos.add(new _p25_modeloGrupo(resp.slist1[i]));
                                _p25_storeGrupos.sort('letra','ASC');
                                _p25_storeGrupos.commitChanges();
                            }
                        });
                        debug('<cargar linea');
                    }
                    else
                    {
                        debug('>cargar modificada');
                        auxCargarGrupos(function(resp)
                        {
                            debug('>callback modificada');
                            if(_p25_clasif==_p25_TARIFA_LINEA)
                            {
                                _p25_construirLinea(0,0,true);
                                _p25_setActiveConcepto();
                            }
                            else
                            {
                                _p25_construirModificada(0,0,true);
                                _p25_setActiveConcepto();
                            }
                            var aux=resp.slist1.length;
                            var aux2=0;
                            debug('cargar:',aux);
                            _p25_tabpanel().setLoading(true);
                            if(aux==0)
                            {
                                mensajeError('No hay grupos para cargar');
                            }
                            for(var i=0;i<aux;i++)
                            {
                                Ext.Ajax.request(
                                {
                                    url      : _p25_urlObtenerTvalogarsGrupo
                                    ,params  :
                                    {
                                        'smap1.cdunieco'  : _p25_smap1.cdunieco
                                        ,'smap1.cdramo'   : _p25_smap1.cdramo
                                        ,'smap1.estado'   : _p25_smap1.estado
                                        ,'smap1.nmpoliza' : _p25_smap1.nmpoliza
                                        ,'smap1.letra'    : resp.slist1[i].letra
                                        ,'smap1.i'        : i
                                    }
                                    ,success : function(response)
                                    {
                                        var tvalogars=Ext.decode(response.responseText);
                                        debug('### tvalogars:',tvalogars);
                                        if(tvalogars.exito)
                                        {
                                            aux2=aux2+1;
                                            debug('cargadas:',aux2);
                                            var grupo=new _p25_modeloGrupo(resp.slist1[tvalogars.smap1.i]);
                                            grupo.tvalogars=tvalogars.slist1;
                                            grupo.valido=true;
                                            _p25_storeGrupos.add(grupo);
                                            _p25_storeGrupos.sort('letra','ASC');
                                            _p25_storeGrupos.commitChanges();
                                            if(aux2==aux)//tenemos todas las respuestas
                                            {
                                                _p25_tabpanel().setLoading(false);
                                            }
                                        }
                                        else
                                        {
                                            _p25_tabpanel().setLoading(false);
                                            mensajeError(tvalogars.respuesta);
                                        }
                                    }
                                    ,failure : function()
                                    {
                                        _p25_tabpanel().setLoading(false);
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
                _p25_tabpanel().setLoading(false);
                errorComunicacion();
            }
        });
    }
    
    _fieldByName('nmnumero').regex = /^[A-Za-z0-9-]*$/;
    _fieldByName('nmnumero').regexText = 'Solo d&iacute;gitos, letras y guiones';
    _fieldByName('nmnumint').regex = /^[A-Za-z0-9-]*$/;
    _fieldByName('nmnumint').regexText = 'Solo d&iacute;gitos, letras y guiones';
    ////// loaders //////
});

////// funciones //////
function _p25_construirLinea(button,event,confirmado)
{
    debug('>_p25_construirLinea confirmado:',confirmado,'-dummy');
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
                    _p25_construirLinea(button,event,true);
                }
            }));
        }
    }
    
    //agregar tab grupos
    if(valido)
    {
        _p25_quitarTabsDetalleGrupo();
        _p25_clasif = _p25_TARIFA_LINEA;
        _p25_agregarTabGrupos();
    }
    debug('<_p25_construirLinea');
}

function _p25_quitarTabsDetalleGrupo(letraGrupo)
{
    debug('>_p25_quitarTabsDetalleGrupo:',letraGrupo,'dummy');
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
    debug('<_p25_quitarTabsDetalleGrupo');
}

function _p25_agregarTabGrupos()
{
    debug('>_p25_agregarTabGrupos');
    _p25_storeGrupos.removeAll();
    
    if(_p25_tabGrupos)
    {
        debug('remove:',Ext.ComponentQuery.query('#'+_p25_tabGrupos.itemId)[0]);
        _p25_tabpanel().remove(Ext.ComponentQuery.query('#'+_p25_tabGrupos.itemId)[0],false);
    }
    
    if(_p25_clasif==_p25_TARIFA_LINEA)
    {
        _p25_tabGrupos = _p25_tabGruposLineal;
    }
    else
    {
        _p25_tabGrupos = _p25_tabGruposModifi;
    }
    
    _p25_agregarTab(_p25_tabGrupos);
    window.parent.scrollTo(0, 0);
    debug('<_p25_agregarTabGrupos');
}

function _p25_agregarTab(tab)
{
    debug('>_p25_agregarTab:',tab);
    _p25_tabpanel().add(tab);
    debug('active:',Ext.ComponentQuery.query('#'+tab.itemId)[0]);
    _p25_setActiveTab(tab.itemId);
    window.parent.scrollTo(0, 0);
    debug('<_p25_agregarTab');
}

function _p25_tabpanel()
{
    return Ext.ComponentQuery.query('#_p25_tabpanel')[0];
}

function _p25_editorPlanChange(combo,newValue,oldValue,eOpts)
{
    debug('>_p25_editorPlanChange new',newValue,'old',oldValue+'x');
    if(oldValue+'x'!='x'&&(_p25_clasif==_p25_TARIFA_MODIFICADA||_p25_smap1.LINEA_EXTENDIDA=='N')&&_p25_semaforoPlanChange)
    {
        centrarVentanaInterna(Ext.MessageBox.confirm('Confirmar', 'Al cambiar el plan se borrar&aacute; el detalle del subgrupo<br/>¿Desea continuar?', function(btn)
        {
            if(btn == 'yes')
            {
                var record = _p25_query('#'+_p25_tabGrupos.itemId)[0].items.items[0].getSelectionModel().getSelection()[0];
                debug('record:',record);
                var letra  = record.get('letra');
                debug('letra:',letra);
                record.valido = false;
                _p25_quitarTabsDetalleGrupo(letra);
            }
            else
            {
                _p25_semaforoPlanChange = false;
                combo.setValue(oldValue);
                _p25_semaforoPlanChange = true;
            }
        }));
    }
    debug('<_p25_editorPlanChange');
}

function _p25_query(regex)
{
    debug('>_p25_query<',regex);
    return Ext.ComponentQuery.query(regex);
}

function _p25_setActiveTab(itemId)
{
    debug('>_p25_setActiveTab:',itemId);
    _p25_tabpanel().setActiveTab(Ext.ComponentQuery.query('#'+itemId)[0]);
    window.parent.scrollTo(0, 0);
    debug('<_p25_setActiveTab');
}

function _p25_cotizarNueva()
{
    debug('>_p25_cotizarNueva');
    _p25_quitarTabsDetalleGrupo();
    _p25_storeGrupos.removeAll();
    if(_p25_tabGrupos)
    {
        debug('remove:',Ext.ComponentQuery.query('#'+_p25_tabGrupos.itemId)[0]);
        _p25_tabpanel().remove(Ext.ComponentQuery.query('#'+_p25_tabGrupos.itemId)[0],false);
    }
    _p25_tabConcepto().down('[xtype=form]').getForm().reset();
    _p25_editarClic();
    debug('<_p25_cotizarNueva');
}

function _p25_tabConcepto()
{
    debug('>_p25_tabConcepto<');
    return _p25_query('#_p25_tabConcepto')[0];
}

function _p25_editarClic()
{
    debug('>_p25_editarClic');
    _p25_tabConcepto().down('[xtype=form]').setDisabled(false);
    _p25_tabConcepto().remove(_p25_gridTarifas,true);
    window.parent.scrollTo(0, 0);
    debug('<_p25_editarClic');
}

function _p25_construirModificada(button,event,confirmado)
{
    debug('>_p25_construirModificada');
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
                    _p25_construirModificada(button,event,true);
                }
            }));
        }
    }
    debug('<_p25_construirModificada');
    
    //agregar tab grupos
    if(valido)
    {
        _p25_quitarTabsDetalleGrupo();
        _p25_clasif = _p25_TARIFA_MODIFICADA;
        _p25_agregarTabGrupos();
    }
}

function _p25_setActiveConcepto()
{
    debug('>_p25_setActiveConcepto');
    _p25_setActiveTab('_p25_tabConcepto');
    window.parent.scrollTo(0, 0);
    debug('<_p25_setActiveConcepto');
}

function _p25_agregarGrupoClic()
{
    debug('>_p25_agregarGrupoClic');
    _p25_storeGrupos.add(new _p25_modeloGrupo({letra:'99'}));
    _p25_storeGrupos.sort('letra','ASC');
    _p25_renombrarGrupos(true);
    _p25_storeGrupos.commitChanges();
    debug('<_p25_agregarGrupoClic');
}

function _p25_renombrarGrupos(sinBorrarPestañas)
{
    debug('>_p25_renombrarGrupos');
    if(!sinBorrarPestañas)
    {
        _p25_quitarTabsDetalleGrupo();
    }
    var letras=['1','2','3','4','5','6','7','8','9','91','92','93','94','95','96','97','98','99'];
    var i=0;
    _p25_storeGrupos.each(function(record)
    {
        record.set('letra',letras[i]);
        i=i+1;
    });
    debug('<_p25_renombrarGrupos');
}

function _p25_rfcBlur(field)
{
    debug('>_p25_rfcBlur:',field);
    var value=field.getValue();
    debug('value:',value);
    
    var valido = true;
    
    if(valido)
    {
        valido = _p25_smap1.BLOQUEO_CONCEPTO=='N';
    }
    
    if(valido)
    {
        valido = value&&value.length>8&&value.length<14;
    }
    
    if(valido)
    {
        _p25_tabpanel().setLoading(true);
        Ext.Ajax.request
        ({
            url     : _p25_urlBuscarPersonas
            ,params :
            {
                'map1.pv_rfc_i'       : value
                ,'map1.cdtipsit'      : _p25_smap1.cdtipsit
                ,'map1.pv_cdunieco_i' : _p25_smap1.cdunieco
                ,'map1.pv_cdramo_i'   : _p25_smap1.cdramo
                ,'map1.pv_estado_i'   : 'W'
                ,'map1.pv_nmpoliza_i' : _fieldByName('nmpoliza').getValue()
                ,'map1.esContratante' : 'S'
            }
            ,success:function(response)
            {
                _p25_tabpanel().setLoading(false);
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
                                                _fieldByName('cdrfc')    .setValue(record.get('RFCCLI'));
                                                _fieldByName('cdperson') .setValue(record.get('CLAVECLI'));
                                                _fieldByName('nombre')   .setValue(record.get('NOMBRECLI'));
                                                _fieldByName('codpostal').setValue(record.get('CODPOSTAL'));
                                                _fieldByName('cdedo')    .setValue(record.get('CDEDO'));
                                                _fieldByName('cdmunici') .setValue(record.get('CDMUNICI'));
                                                _fieldByName('cdmunici') .heredar(true);
                                                _fieldByName('dsdomici') .setValue(record.get('DSDOMICIL'));
                                                _fieldByName('nmnumero') .setValue(record.get('NMNUMERO'));
                                                _fieldByName('nmnumint') .setValue(record.get('NMNUMINT'));
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
                _p25_tabpanel().setLoading(false);
                errorComunicacion();
            }
        });
    }
    
    debug('<_p25_rfcBlur');
}

function _p25_borrarGrupoClic(grid,rowIndex)
{
    var record = grid.getStore().getAt(rowIndex);
    debug('>_p25_borrarGrupoClic:',record.data);
    centrarVentanaInterna(Ext.Msg.confirm('Borrar grupo','¿Desea borrar el grupo '+record.get('letra')+' y renombrar los grupos en orden alfabético?<br>Esta acci&oacute;n cerrará las pestañas de detalle de subgrupos y podría perder los cambios no guardados.',
    function(button){
        debug('clicked:',button);
        if(button=='yes')
        {
            _p25_storeGrupos.remove(record);
            _p25_renombrarGrupos();
        }
    }));
    debug('<_p25_borrarGrupoClic');
}

function _p25_editarGrupoClic(grid,rowIndex)
{
    var record = grid.getStore().getAt(rowIndex);
    debug('>_p25_editarGrupoClic:',record);
    
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
        _p25_quitarTabsDetalleGrupo(record.get('letra'));
        _p25_tabpanel().setLoading(true);
        Ext.Ajax.request(
        {
            url      : _p25_urlObtenerCoberturas
            ,params  :
            {
                'smap1.cdramo'    : _p25_smap1.cdramo
                ,'smap1.cdtipsit' : _p25_smap1.cdtipsit
                ,'smap1.cdplan'   : record.get('cdplan')
            }
            ,success : function(response)
            {
                _p25_tabpanel().setLoading(false);
                var json = Ext.decode(response.responseText);
                debug('json response:',json);
                if(json.exito)
                {
                    var numCoberturas=json.slist1.length;
                    debug('numCoberturas:',numCoberturas);
                    var contadorCoberturas=0;
                    _p25_tabpanel().setLoading(true);
                    for(var i=0;i<numCoberturas;i++)
                    {
                        Ext.Ajax.request(
                        {
                            url      : _p25_urlObtenerHijosCobertura
                            ,params  :
                            {
                                'smap1.cdramo'     : _p25_smap1.cdramo
                                ,'smap1.cdtipsit'  : _p25_smap1.cdtipsit
                                ,'smap1.cdplan'    : record.get('cdplan')
                                ,'smap1.cdgarant'  : json.slist1[i].CDGARANT
                                ,'smap1.indice'    : i
                                ,'smap1.cdatrivar' : record.get('parametros.pv_otvalor'+_p25_smap1.ATRIVAR_TATRIGAR)
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
                                                if(hijo.maxValue&&hijo.maxValue<999999999)
                                                {
                                                    hijo.on('change',function(comp,value)
                                                    {
                                                        debug('change:',value,comp.maxValue,comp);
                                                        if(value!=comp.maxValue)
                                                        {
                                                            comp.addCls('valorNoOriginal');
                                                        }
                                                        else
                                                        {
                                                            comp.removeCls('valorNoOriginal');
                                                        }
                                                    });
                                                }
                                            } 
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
                                                        ,checked    : true
                                                        ,disabled   : _p25_smap1.cdsisrol!='COTIZADOR'&&json.slist1[j].SWOBLIGA=='S'
                                                        ,style      : 'color:white;'
                                                        ,listeners  :
                                                        {
                                                            change : function(checkbox,value)
                                                            {
                                                                debug('checkbox change:',value);
                                                                var form = checkbox.up().up();
                                                                for(var l=0;l<form.items.items.length;l++)
                                                                {
                                                                    form.items.items[l].setDisabled(!value);
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
                                                                clearTimeout(_p25_filtroCobTimeout);
                                                                _p25_filtroCobTimeout = setTimeout(function()
                                                                {
                                                               Ext.Array.each(form.items.items,function(item)
                                                               {
                                                                   if(me.getValue()+'x'!='x')
                                                                   {
                                                                       var val=me.getValue().toLowerCase().replace(/s/g,'');
                                                                       if(item.fieldLabel.toLowerCase().replace(/s/g,'').lastIndexOf(val)!=-1)
                                                                       {
                                                                           item.show();
                                                                       }
                                                                       else
                                                                       {
                                                                           item.hide();
                                                                       }
                                                                   }
                                                                   else
                                                                   {
                                                                       item.show();
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
                                                Ext.define('_p25_modelo'+json.slist1[j].CDGARANT,
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
                                                var datosAnteriores = Ext.create('_p25_modelo'+json.slist1[j].CDGARANT,tvalogar);
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
                                        _p25_tabpanel().setLoading(false);
                                        grid.editingPlugin.cancelEdit();
                                        _p25_agregarTab(
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
                                                    ,hidden      : _p25_smap1.COBERTURAS=='N'
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
                                                    ,hidden  : _p25_smap1.FACTORES=='N'
                                                    ,plugins : Ext.create('Ext.grid.plugin.RowEditing',
                                                    {
                                                        clicksToEdit  : 1
                                                        ,errorSummary : false
                                                        /*
                                                        ,listeners    :
                                                        {
                                                            beforeedit : function(editor)
                                                            {
                                                                centrarVentanaInterna(Ext.MessageBox.confirm('Confirmar', 'Al cambiar el factor de incremento de inflaci&oacute;n<br/>y/o el factor de extraprima o renovaci&oacute;n entonces<br/>se cambiar&aacute; para todas las coberturas<br/>¿Desea continuar?', function(btn)
                                                                {
                                                                    if(btn=='yes')
                                                                    {
                                                                        _p25_incrinflAux = record.get('incrinfl');
                                                                        _p25_extrrenoAux = record.get('extrreno');
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
                                                                debug('_p25_incrinflAux:',_p25_incrinflAux,'incrinfl:',incrinfl);
                                                                debug('_p25_extrrenoAux:',_p25_extrrenoAux,'extrreno:',extrreno);
                                                                if(_p25_incrinflAux!=incrinfl)
                                                                {
                                                                    var pestania=Ext.ComponentQuery.query('[letraGrupo='+letraGrupo+']')[0];
                                                                    debug('pestania:',pestania);
                                                                    $.each(_p25_arrayNombresIncrinfl,function(i,nombre)
                                                                    {
                                                                        var componentes=Ext.ComponentQuery.query('[fieldLabel='+nombre+']',pestania);
                                                                        debug('componentes para poner factor incrinfl:',componentes);
                                                                        $.each(componentes,function(i,comp)
                                                                        {
                                                                            debug('poniendo valor en:',comp);
                                                                            comp.setValue(incrinfl);
                                                                        });
                                                                    });
                                                                }
                                                                if(_p25_extrrenoAux!=extrreno)
                                                                {
                                                                    var pestania=Ext.ComponentQuery.query('[letraGrupo='+letraGrupo+']')[0];
                                                                    debug('pestania:',pestania);
                                                                    $.each(_p25_arrayNombresExtrreno,function(i,nombre)
                                                                    {
                                                                        var componentes=Ext.ComponentQuery.query('[fieldLabel='+nombre+']',pestania);
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
                                                        */
                                                    })
                                                    ,columns : _p25_factoresColumns
                                                    ,store   : Ext.create('Ext.data.Store',
                                                    {
                                                        model : '_p25_modeloGrupo'
                                                        ,data : record
                                                    })
                                                })
                                                ,Ext.create('Ext.grid.Panel',
                                                {
                                                    title      : 'TARIFA POR EDADES ('
                                                         +(
                                                         _p25_ntramite?
                                                         (_fieldByName('cdperpag').findRecord('key',_fieldByName('cdperpag').getValue()).get('value'))
                                                         :''
                                                         )+')'
                                                    ,minHeight : 100
                                                    ,hidden    : _p25_ntramite ? false : true
                                                    ,maxHeight : 250
                                                    ,store     : Ext.create('Ext.data.Store',
                                                    {
                                                        model     : '_p25_modeloTarifaEdad'
                                                        ,autoLoad : _p25_ntramite ? true : false
                                                        ,proxy    :
                                                        {
                                                            type         : 'ajax'
                                                            ,extraParams :
                                                            {
                                                                'smap1.cdunieco'  : _p25_smap1.cdunieco
                                                                ,'smap1.cdramo'   : _p25_smap1.cdramo
                                                                ,'smap1.estado'   : _p25_smap1.estado
                                                                ,'smap1.nmpoliza' : _p25_smap1.nmpoliza
                                                                ,'smap1.nmsuplem' : '0'
                                                                ,'smap1.cdplan'   : record.get('cdplan')
                                                                ,'smap1.cdgrupo'  : record.get('letra')
                                                                ,'smap1.cdperpag' : _fieldByName('cdperpag').getValue()
                                                            }
                                                            ,url         : _p25_urlObtenerTarifaEdad
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
                                                    title      : 'PRIMA PROMEDIO ('
                                                         +(
                                                         _p25_ntramite?
                                                         (_fieldByName('cdperpag').findRecord('key',_fieldByName('cdperpag').getValue()).get('value'))
                                                         :''
                                                         )+')'
                                                    ,minHeight : 100
                                                    ,hidden    : _p25_ntramite ? false : true
                                                    ,maxHeight : 250
                                                    ,store     : Ext.create('Ext.data.Store',
                                                    {
                                                        model     : '_p25_modeloTarifaCobertura'
                                                        ,autoLoad : _p25_ntramite ? true : false
                                                        ,proxy    :
                                                        {
                                                            type         : 'ajax'
                                                            ,extraParams :
                                                            {
                                                                'smap1.cdunieco'  : _p25_smap1.cdunieco
                                                                ,'smap1.cdramo'   : _p25_smap1.cdramo
                                                                ,'smap1.estado'   : _p25_smap1.estado
                                                                ,'smap1.nmpoliza' : _p25_smap1.nmpoliza
                                                                ,'smap1.nmsuplem' : '0'
                                                                ,'smap1.cdplan'   : record.get('cdplan')
                                                                ,'smap1.cdgrupo'  : record.get('letra')
                                                                ,'smap1.cdperpag' : _fieldByName('cdperpag').getValue()
                                                            }
                                                            ,url         : _p25_urlObtenerTarifaCobertura
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
                                                    ,handler : function(button){_p25_guardarGrupo(button.up().up());}
                                                    ,hidden  : _p25_smap1.COBERTURAS=='N'
                                                }
                                            ]
                                        });
                                        var formsValidos = Ext.ComponentQuery.query('[valido=true]');
                                        debug('formsValidos:',formsValidos);
                                        for(var k=0;k<formsValidos.length;k++)
                                        {
                                            var form = formsValidos[k];
                                            debug('intento cargar:',form,'con:',form.datosAnteriores);
                                            form.loadRecord(form.datosAnteriores);
                                            if(form.datosAnteriores.raw.amparada=='N')
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
                                    _p25_tabpanel().setLoading(false);
                                    mensajeError(json2.respuesta);
                                }
                            }
                            ,failure : function()
                            {
                                _p25_tabpanel().setLoading(false);
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
                _p25_tabpanel().setLoading(false);
                errorComunicacion();
            }
        });
    }
    
    debug('<_p25_editarGrupoClic');
}

function _p25_guardarGrupo(panelGrupo)
{
     debug('>_p25_guardarGrupo:',panelGrupo);
     
     var letraGrupo  = panelGrupo.letraGrupo;
     debug('letraGrupo:',letraGrupo);
     
     var formsTatrigar = panelGrupo.down('[title=COBERTURAS DEL SUBGRUPO]').items.items;
     debug('formsTatrigar:',formsTatrigar);
     
     var tvalogars = [];
     var valido    = true;
     if(_p25_clasif==_p25_TARIFA_MODIFICADA||_p25_smap1.LINEA_EXTENDIDA=='N')
     {
         for(var i=0;i<formsTatrigar.length;i++)
         {
             var iFormTatrigar = formsTatrigar[i];
             valido            = valido && iFormTatrigar.isValid();
             var tvalogar      = iFormTatrigar.getValues();
             if(tvalogar.swobliga=='S'&&_p25_smap1.cdsisrol!='COTIZADOR')
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
         var recordGrupo=_p25_obtenerGrupoPorLetra(letraGrupo);
         recordGrupo['tvalogars'] = tvalogars;
         recordGrupo['valido']    = true;
         debug('recordGrupo:',recordGrupo);
         mensajeCorrecto('Se han guardado los datos','Se han guardado los datos',_p25_setActiveResumen);
     }
     
     debug('<_p25_guardarGrupo');
}

function _p25_obtenerGrupoPorLetra(letra)
{
    debug('>_p25_obtenerGrupoPorLetra:',letra);
    var recordGrupo = null;
    _p25_storeGrupos.each(function(record)
    {
        if(record.get('letra')==letra)
        {
            recordGrupo = record;
        }
    });
    debug('<_p25_obtenerGrupoPorLetra:',recordGrupo);
    return recordGrupo;
}

function _p25_setActiveResumen()
{
    debug('>_p25_setActiveResumen');
    _p25_setActiveTab(_p25_tabGrupos.itemId);
    window.parent.scrollTo(0, 0);
    debug('<_p25_setActiveResumen');
}

function _p25_generarTramiteClic(callback)
{
    debug('>_p25_generarTramiteClic');
    var valido = true;
    
    if(valido)
    {
        valido = _p25_tabConcepto().down('[xtype=form]').isValid();
        if(!valido)
        {
            mensajeWarning('Verificar los datos del concepto y el censo de asegurados',_p25_setActiveConcepto);
        }
    }
    
    if(valido)
    {    
        valido = _p25_storeGrupos.getCount()>0;
        debug('_p25_storeGrupos.getCount()>0:',_p25_storeGrupos.getCount()>0);
        if(!valido)
        {
            if(!_p25_tabGrupos)
            {
                mensajeWarning('Debe introducir el n&uacute;mero de asegurados',_p25_setActiveConcepto);
            }
            else
            {
                mensajeWarning('Debe introducir al menos un grupo',_p25_setActiveResumen);
            }
        }
    }
    
    if(valido&&_p25_clasif==_p25_TARIFA_LINEA)
    {
        var mensajeDeError = 'Falta definir los datos para el(los) grupo(s): ';
        _p25_storeGrupos.each(function(record)
        {
            if(Ext.isEmpty(record.get('cdplan')))
            {
                valido         = false;
                mensajeDeError = mensajeDeError + record.get('letra') + ' ';
            }
        });
        if(!valido)
        {
            mensajeWarning(mensajeDeError,_p25_setActiveResumen);
        }
    }
    
    if(valido&&(_p25_clasif==_p25_TARIFA_MODIFICADA||_p25_smap1.LINEA_EXTENDIDA=='N'))
    {
        var mensajeDeError = 'Falta definir o guardar el detalle para el(los) grupo(s): ';
        _p25_storeGrupos.each(function(record)
        {
            if(!record.valido)
            {
                valido         = false;
                mensajeDeError = mensajeDeError + record.get('letra') + ' ';
            }
        });
        if(!valido)
        {
            mensajeWarning(mensajeDeError,_p25_setActiveResumen);
        }
    }
    
    if(valido)
    {
        var form=_p25_tabConcepto().down('[xtype=form]');
        form.setLoading(true);
        var timestamp = new Date().getTime();
        form.submit(
        {
            params   :
            {
                'smap1.timestamp' : timestamp
                ,'smap1.ntramite' : _p25_ntramite ? _p25_ntramite : ''
            }
            ,success : function()
            {
                var conceptos = form.getValues();
                conceptos['timestamp']       = timestamp;
                conceptos['clasif']          = _p25_clasif;
                conceptos['LINEA_EXTENDIDA'] = _p25_smap1.LINEA_EXTENDIDA;
                conceptos['cdunieco']        = _p25_smap1.cdunieco;
                conceptos['cdramo']          = _p25_smap1.cdramo;
                conceptos['cdtipsit']        = _p25_smap1.cdtipsit;
                conceptos['ntramiteVacio']   = _p25_ntramiteVacio ? _p25_ntramiteVacio : ''
                var grupos = [];
                _p25_storeGrupos.each(function(record)
                {
                    var grupo = record.data;
                    grupo['tvalogars']=record.tvalogars;
                    grupos.push(grupo);
                });
                Ext.Ajax.request(
                {
                    url       : _p25_urlGenerarTramiteGrupo
                    ,jsonData :
                    {
                        smap1   : conceptos
                        ,olist1 : grupos
                    }
                    ,success  : function(response)
                    {
                        form.setLoading(false);
                        var json=Ext.decode(response.responseText);
                        debug('json response:',json);
                        if(json.exito)
                        {
                            if(_p25_ntramite||_p25_ntramiteVacio)
                            {
                                if(callback)
                                {
                                    callback(json);
                                }
                                else
                                {
                                    mensajeError(json.respuesta+'<br/>Se guard&oacute; la informaci&oacute;n pero no hay callback');
                                }
                            }
                            else
                            {
                                _p25_fieldNmpoliza().setValue(json.smap1.nmpoliza);
                                _p25_fieldNtramite().setValue(json.smap1.ntramite);
                                _p25_tabpanel().setDisabled(true);
                                
                                mensajeCorrecto('Tr&aacute;mite generado',json.respuesta+'<br/>Para subir la documentaci&oacute;n presiona aceptar',function()
                                {
                                    centrarVentanaInterna(Ext.create('Ext.window.Window',
                                    {
                                        width        : 600
                                        ,height      : 400
                                        ,title       : 'Subir documentos de tu tr&aacute;mite ('+json.smap1.ntramite+')'
                                        ,closable    : false
                                        ,modal       : true
                                        ,loadingMask : true
                                        ,loader      :
                                        {
                                            url       : _p25_urlVentanaDocumentos
                                            ,scripts  : true
                                            ,autoLoad : true
                                            ,params   :
                                            {
                                                'smap1.cdunieco'  : json.smap1.cdunieco
                                                ,'smap1.cdramo'   : json.smap1.cdramo
                                                ,'smap1.estado'   : 'W'
                                                ,'smap1.nmpoliza' : '0'
                                                ,'smap1.nmsuplem' : '0'
                                                ,'smap1.ntramite' : json.smap1.ntramite
                                                ,'smap1.tipomov'  : '0'
                                            }
                                        }
                                    }).show());
                                });
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
            ,failure : function()
            {
                form.setLoading(false);
                errorComunicacion();
            }
        });
    }
    
    debug('<_p25_generarTramiteClic');
}

function _p25_fieldNtramite()
{
    debug('>_p25_fieldNtramite<');
    return _p25_query('#_p25_fieldNtramite')[0];
}

function _p25_fieldNmpoliza()
{
    debug('>_p25_fieldNmpoliza<');
    return _p25_query('#_p25_fieldNmpoliza')[0];
}

function _p25_turnar(status,titulo,closable)
{
    debug('>_p25_turnar:',status);
    var ventana=Ext.create('Ext.window.Window',
    {
        title        : !Ext.isEmpty(titulo) ? titulo : 'Turnar tr&aacute;mite'
        ,width       : 500
        ,height      : 300
        ,modal       : true
        ,closable    : closable==undefined ? true : closable
        ,items       :
        [
            {
                xtype       : 'textarea'
                ,labelAlign : 'top'
                ,fieldLabel : 'Comentarios'
                ,itemId     : '_p25_turnarCommentsItem'
                ,width      : 480
                ,height     : 200
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
                    if(status+'x'=='17x')
                    {
                        Ext.Ajax.request(
                        {
                            url     : _p25_guardarReporteCotizacion
                            ,params :
                            {
                                'smap1.cdunieco'  : _p25_smap1.cdunieco
                                ,'smap1.cdramo'   : _p25_smap1.cdramo
                                ,'smap1.estado'   : _p25_smap1.estado
                                ,'smap1.nmpoliza' : _p25_smap1.nmpoliza
                                ,'smap1.cdtipsit' : _p25_smap1.cdtipsit
                                ,'smap1.ntramite' : _p25_smap1.ntramite
                            }
                        });
                    }
                    Ext.Ajax.request(
                    {
                        url     : _p25_urlActualizarStatus
                        ,params :
                        {
                            'smap1.status'    : status
                            ,'smap1.ntramite' : _p25_ntramite ? _p25_ntramite : _p25_ntramiteVacio
                            ,'smap1.comments' : Ext.ComponentQuery.query('#_p25_turnarCommentsItem')[0].getValue()
                        }
                        ,success : function(response)
                        {
                            ventana.setLoading(false);
                            var json=Ext.decode(response.responseText);
                            debug('json response:',json);
                            if(json.success)
                            {
                                ventana.setLoading(true);
                                Ext.Ajax.request(
                                {
                                    url      : _p25_urlCargarParametros
                                    ,params  :
                                    {
                                        'smap1.parametro' : 'MENSAJE_TURNAR'
                                        ,'smap1.cdramo'   : _p25_smap1.cdramo
                                        ,'smap1.cdtipsit' : _p25_smap1.cdtipsit
                                        ,'smap1.clave4'   : status
                                    }
                                    ,success : function(response)
                                    {
                                        ventana.setLoading(false);
                                        var json=Ext.decode(response.responseText);
                                        debug('### json response parametro mensaje turnar:',json);
                                        if(json.exito)
                                        {
                                            mensajeCorrecto('Tr&aacute;mite guardado',json.smap1.P1VALOR,function()
                                            {
                                                button.up().up().destroy();
                                                if(status+'x'=='19x')
                                                {
                                                    _p25_reload(null,19,_p25_smap1.nmpoliza);
                                                }
                                                else
                                                {
                                                    _p25_mesacontrol();
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
        ]
    }).show();
    centrarVentanaInterna(ventana);
    debug('<_p25_turnar');
}

function _p25_reload(json,status,nmpoliza)
{
    debug('>_p25_reload params:');
    debug(json,status,nmpoliza,'dummy');
    _p25_tabpanel().setLoading(true);
    Ext.create('Ext.form.Panel').submit(
    {
        standardSubmit : true
        ,params        :
        {
            'smap1.cdunieco'  : _p25_smap1.cdunieco
            ,'smap1.cdramo'   : _p25_smap1.cdramo
            ,'smap1.cdtipsit' : _p25_smap1.cdtipsit
            ,'smap1.estado'   : _p25_smap1.estado
            ,'smap1.nmpoliza' : Ext.isEmpty(nmpoliza) ? json.smap1.nmpoliza : nmpoliza
            ,'smap1.ntramite' : _p25_ntramite ? _p25_ntramite : _p25_ntramiteVacio
            ,'smap1.cdagente' : _fieldByName('cdagente').getValue()
            ,'smap1.status'   : Ext.isEmpty(status) ? _p25_smap1.status : status
        }
    });
    debug('<_p25_reload');
}

function _p25_mesacontrol(json)
{
    _p25_tabpanel().setLoading(true);
    Ext.create('Ext.form.Panel').submit(
    {
        standardSubmit : true
        ,url           : _p25_urlMesaControl
        ,params        :
        {
            'smap1.gridTitle'      : 'Tareas'
            ,'smap2.pv_cdtiptra_i' : 1
        }
    });
}

function _p25_imprimir()
{
    debug('>_p25_imprimir');
    var urlRequestImpCotiza = _p25_urlImprimirCotiza
            + '?p_unieco='      + _p25_smap1.cdunieco
            + '&p_ramo='        + _p25_smap1.cdramo
            + '&p_estado=W'
            + '&p_poliza='      + _p25_smap1.nmpoliza
            + '&p_suplem=0'
            + '&p_cdplan='
            + '&destype=cache'
            + "&desformat=PDF"
            + "&userid="        + _p25_reportsServerUser
            + "&ACCESSIBLE=YES"
            + "&report="        + _p25_nombreReporteCotizacion
            + "&paramform=no";
    debug('urlRequestImpCotiza:',urlRequestImpCotiza);
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
                + _p25_urlViewDoc
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
    debug('<_p25_imprimir');
}

function _p25_revisarAseguradosClic(grid,rowIndex)
{
    var record=grid.getStore().getAt(rowIndex);
    debug('>_p25_revisarAseguradosClic record:',record);
    _p25_quitarTabExtraprima(record.get('letra'));
    var timestamp = new Date().getTime();
    _p25_agregarTab(
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
                columns     :
                [
                    {
                        text       : 'NO.'
                        ,dataIndex : 'nmsituac'
                    }
                    ,{
                        text       : 'PARENTESCO'
                        ,dataIndex : 'parentesco'
                    }
                    ,{
                        text       : 'NOMBRE'
                        ,dataIndex : 'nombre'
                    }
                    <s:if test='%{getImap().containsKey("extraprimasColumns")&&getImap().get("extraprimasColumns")!=null}'>
                        ,<s:property value="imap.extraprimasColumns" escapeHtml='false' />
                    </s:if>
                ]
                ,minHeight  : 150
                ,maxHeight  : 500
                ,plugins    : _p25_smap1.EXTRAPRIMAS_EDITAR=='S' ? Ext.create('Ext.grid.plugin.RowEditing',
                {
                    clicksToEdit  : 1
                    ,errorSummary : false
                }) : null
                ,tbar       :
                [
                    {
                        xtype       : 'textfield'
                        ,fieldLabel : '<span style="color:white;">Buscar:</span>'
                        ,listeners  :
                        {
                            change : function(comp,val)
                            {
                                debug('extraprimas filtro change:',val);
                                var grid=comp.up().up();
                                debug('grid:',grid);
                                grid.getStore().filterBy(function(record, id)
                                {
                                    var nombre = record.get('nombre').toUpperCase().replace(/ /g,'');
                                    var filtro = val.toUpperCase().replace(/ /g,'');
                                    var posNombre = nombre.lastIndexOf(filtro);
                                    
                                    if(posNombre > -1)
                                    {
                                        return true;
                                    }
                                    else
                                    {
                                        return false;
                                    }
                                });
                            }
                        }
                    }
                ]
                ,store      : Ext.create('Ext.data.Store',
                {
                    model       : '_p25_modeloExtraprima'
                    ,groupField : 'agrupador'
                    ,autoLoad   : true
                    ,proxy      :
                    {
                        type         : 'ajax'
                        ,url         : _p25_urlCargarAseguradosExtraprimas
                        ,extraParams :
                        {
                            'smap1.cdunieco'   : _p25_smap1.cdunieco
                            ,'smap1.cdramo'    : _p25_smap1.cdramo
                            ,'smap1.estado'    : _p25_smap1.estado
                            ,'smap1.nmpoliza'  : _p25_smap1.nmpoliza
                            ,'smap1.nmsuplem'  : '0'
                            ,'smap1.cdgrupo'   : record.get('letra')
                            ,'smap1.timestamp' : timestamp
                        }
                        ,reader      :
                        {
                            type  : 'json'
                            ,root : 'slist1'
                        }
                    }
                    ,listeners :
                    {
                        load : function(me,records,success,e)
                        {
                            if(success)
                            {
                                debug('### records de extraprimas:',records);
                                me.removeAll();
                                Ext.each(records,function(record)
                                {
                                    me.add(new _p25_modeloExtraprima(record.raw));
                                });
                                me.commitChanges();
                            }
                            else
                            {
                                mensajeError('Error al cargar situaciones #'+timestamp);
                            }
                        }
                    }
                })
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
                ,buttonAlign : 'center'
                ,buttons     :
                [
                    {
                        text     : 'Guardar'
                        ,icon    : '${ctx}/resources/fam3icons/icons/disk.png'
                        ,hidden  : _p25_smap1.EXTRAPRIMAS_EDITAR=='N'
                        ,handler : function()
                        {
                            _p25_guardarExtraprimas(record.get('letra'));
                        }
                    }
                ]
            })
        ]
    });
    debug('<_p25_revisarAseguradosClic');
}

function _p25_quitarTabExtraprima(letra)
{
    debug('>_p25_quitarTabExtraprima letra:',letra);
    var tabsExtraprima = Ext.ComponentQuery.query('[extraprimaLetraGrupo='+letra+']');
    if(tabsExtraprima.length>0)
    {
        for(var i=0;i<tabsExtraprima.length;i++)
        {
            tabsExtraprima[i].destroy();
        }
    }
    debug('<_p25_quitarTabExtraprima');
}

function _p25_guardarExtraprimas(letra)
{
    debug('>_p25_guardarExtraprimas:',letra);
    var tab=Ext.ComponentQuery.query('[extraprimaLetraGrupo='+letra+']')[0];
    debug('tab a guardar:',tab);
    var grid=tab.down('[xtype=grid]');
    debug('grid a guardar:',grid);
    var store=grid.getStore();
    debug('store a guardar:',store);
    var records=store.getModifiedRecords();
    debug('records a guardar:',records);
    if(records.length==0)
    {
        mensajeWarning('No hay cambios');
    }
    else
    {
        var asegurados = [];
        $.each(records,function(i,record)
        {
            var asegurado =
            {
                cdunieco   : _p25_smap1.cdunieco
                ,cdramo    : _p25_smap1.cdramo
                ,estado    : _p25_smap1.estado
                ,nmpoliza  : _p25_smap1.nmpoliza
                ,nmsuplem  : '0'
                ,nmsituac  : record.get('nmsituac')
            };
            for(var i=1;i<=50;i++)
            {
                var valor = record.get('parametros.pv_otvalor'+(('00'+i).slice(-2)));
                debug('valor:',valor,'typeof valor:',typeof valor);
                if(typeof valor=='object')
                {
                    valor = Ext.Date.format(valor,'d/m/Y');
                }
                asegurado['otvalor'+(('00'+i).slice(-2))]=valor;
            }
            asegurados.push(asegurado);
        });
        debug('situaciones a guardar:',asegurados);
        tab.setLoading(true);
        Ext.Ajax.request(
        {
            url       : _p25_urlGuardarSituaciones
            ,jsonData :
            {
                slist1 : asegurados
            }
            ,success  : function(response)
            {
                tab.setLoading(false);
                var json=Ext.decode(response.responseText);
                debug('respuesta del guardado de extraprimas:',json);
                if(json.exito)
                {
                    store.commitChanges();
                    mensajeCorrecto('Datos guardados',json.respuesta);
                }
                else
                {
                    mensajeError(json.respuesta);
                }
            }
            ,failure  : function()
            {
                tab.setLoading(false);
                errorComunicacion();
            }
        });
    }
    debug('<_p25_guardarExtraprimas');
}

function _p25_subirDetallePersonas()
{
    debug('>_p25_subirDetallePersonas');
    centrarVentanaInterna(Ext.create('Ext.window.Window',
    {
        title   : 'Cargar archivo de personas'
        ,width  : 400
        ,modal  : true
        ,items  :
        [
            Ext.create('Ext.form.Panel',
            {
                url          : _p25_urlSubirCenso
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
                        ,handler : function(button)
                        {
                            var form=button.up().up();
                            
                            var valido=form.isValid();
                            if(!valido)
                            {
                                datosIncompletos();
                            }
                            
                            if(valido)
                            {
                                form.setLoading(true);
                                var timestamp = new Date().getTime();
                                form.submit(
                                {
                                    params   :
                                    {
                                        'smap1.timestamp' : timestamp
                                        ,'smap1.ntramite' : ''
                                    }
                                    ,success : function()
                                    {
                                        var conceptos = _p25_tabConcepto().down('[xtype=form]').getValues();
                                        conceptos['timestamp']       = timestamp;
                                        conceptos['clasif']          = _p25_clasif;
                                        conceptos['LINEA_EXTENDIDA'] = _p25_smap1.LINEA_EXTENDIDA;
                                        conceptos['cdunieco']        = _p25_smap1.cdunieco;
                                        conceptos['cdramo']          = _p25_smap1.cdramo;
                                        conceptos['cdtipsit']        = _p25_smap1.cdtipsit;
                                        conceptos['ntramiteVacio']   = _p25_ntramiteVacio ? _p25_ntramiteVacio : '';
                                        var grupos = [];
                                        _p25_storeGrupos.each(function(record)
                                        {
                                            var grupo = record.data;
                                            grupo['tvalogars']=record.tvalogars;
                                            grupos.push(grupo);
                                        });
                                        Ext.Ajax.request(
                                        {
                                            url       : _p25_urlSubirCensoCompleto
                                            ,jsonData :
                                            {
                                                smap1   : conceptos
                                                ,olist1 : grupos
                                            }
                                            ,success  : function(response)
                                            {
                                                form.setLoading(false);
                                                var json=Ext.decode(response.responseText);
                                                debug('subir censo completo response:',json);
                                                if(json.exito)
                                                {
                                                    mensajeCorrecto('Datos guardados','Los datos de asegurados se guardaron y ahora<br/>'
                                                           +'el tr&aacute;mite pasar&aacute; a status Completo'
                                                    ,function()
                                                    {
                                                        _p25_turnar(19,'Tr&aacute;mite completo',false);
                                                    });
                                                }
                                                else
                                                {
                                                    mensajeError(json.respuesta);
                                                }
                                            }
                                            ,failure  : function()
                                            {
                                                form.setLoading(false);
                                                errorComunicacion();
                                            }
                                        });
                                    }
                                    ,failure : function()
                                    {
                                        form.setLoading(false);
                                        errorComunicacion();
                                    }
                                });
                            }
                        }
                    }
                ]
            })
        ]
    }).show());
    debug('<_p25_subirDetallePersonas');
}

function _p25_emitir()
{
    _p25_generarTramiteClic(_p25_generarVentanaVistaPrevia);
}

function _p25_aseguradosClic(grid,rowIndex)
{
    var record=grid.getStore().getAt(rowIndex);
    debug('>_p25_aseguradosClic record:',record);
    _p25_quitarTabAsegurados(record.get('letra'));
    var columnas =
    [
        <s:property value='%{getImap().containsKey("aseguradosColumns")?getImap().get("aseguradosColumns").toString():""}' escapeHtml='false' />
    ];
    if(_p25_smap1.ASEGURADOS_EDITAR=='S')
    {
        columnas.push(
        {
            xtype         : 'actioncolumn'
            ,sortable     : false
            ,menuDisabled : true
            ,items        :
            [
                {
                    tooltip  : 'Recuperar'
                    ,icon    : '${ctx}/resources/fam3icons/icons/vcard_edit.png'
                    ,handler : _p25_recuperarAsegurado
                }
                ,{
                    tooltip  : 'Editar'
                    ,icon    : '${ctx}/resources/fam3icons/icons/pencil.png'
                    ,handler : _p25_editarAsegurado
                }
                ,{
                    tooltip  : 'Editar coberturas'
                    ,icon    : '${ctx}/resources/fam3icons/icons/text_list_bullets.png'
                    ,handler : _p25_editarCoberturas
                }
                ,{
                    tooltip  : 'Editar exclusiones'
                    ,icon    : '${ctx}/resources/fam3icons/icons/lock.png'
                    ,handler : _p25_editarExclusiones
                }
            ]
        });
    }
    _p25_agregarTab(
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
                columns     : columnas
                ,minHeight  : 150
                ,maxHeight  : 500
                ,plugins    : false && _p25_smap1.ASEGURADOS_EDITAR=='S' ? Ext.create('Ext.grid.plugin.RowEditing',
                {
                    clicksToEdit  : 1
                    ,errorSummary : false
                }) : null
                ,tbar       :
                [
                    {
                        xtype       : 'textfield'
                        ,fieldLabel : '<span style="color:white;">Buscar:</span>'
                        ,listeners  :
                        {
                            change : function(comp,val)
                            {
                                debug('asegurados filtro change:',val);
                                var grid=comp.up().up();
                                debug('grid:',grid);
                                grid.getStore().filterBy(function(record, id)
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
                                });
                            }
                        }
                    }
                ]
                ,store      : Ext.create('Ext.data.Store',
                {
                    model       : '_p25_modeloAsegurados'
                    ,groupField : 'AGRUPADOR'
                    ,autoLoad   : true
                    ,proxy      :
                    {
                        type         : 'ajax'
                        ,url         : _p25_urlCargarAseguradosGrupo
                        ,extraParams :
                        {
                            'smap1.cdunieco'  : _p25_smap1.cdunieco
                            ,'smap1.cdramo'   : _p25_smap1.cdramo
                            ,'smap1.estado'   : _p25_smap1.estado
                            ,'smap1.nmpoliza' : _p25_smap1.nmpoliza
                            ,'smap1.nmsuplem' : '0'
                            ,'smap1.cdgrupo'  : record.get('letra')
                        }
                        ,reader      :
                        {
                            type  : 'json'
                            ,root : 'slist1'
                        }
                    }
                })
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
                ,buttonAlign : 'center'
                ,buttons     :
                [
                    {
                        text     : 'Guardar'
                        ,icon    : '${ctx}/resources/fam3icons/icons/disk.png'
                        ,hidden  : _p25_smap1.ASEGURADOS_EDITAR!='S'
                        ,handler : function()
                        {
                            _p25_guardarAsegurados(this.up().up());
                        }
                    }
                ]
            })
        ]
    });
    debug('<_p25_aseguradosClic');
}

function _p25_quitarTabAsegurados(letra)
{
    debug('>_p25_quitarTabAsegurados letra:',letra);
    var tabsAsegurados = Ext.ComponentQuery.query('[aseguradosLetraGrupo='+letra+']');
    if(tabsAsegurados.length>0)
    {
        for(var i=0;i<tabsAsegurados.length;i++)
        {
            tabsAsegurados[i].destroy();
        }
    }
    debug('<_p25_quitarTabAsegurados');
}

function _p25_recuperarAsegurado(grid,rowIndex)
{
    var record=grid.getStore().getAt(rowIndex);
    debug('>_p25_recuperarAsegurado:',record.data);
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
                                    ,_p25_urlRecuperarPersona
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
                    model : '_p25_modeloRecuperados'
                })
            })
        ]
    }).show());
    debug('<_p25_recuperarAsegurado');
}

function _p25_editarAsegurado(grid,rowIndex)
{
    var record=grid.getStore().getAt(rowIndex);
    debug('>_p25_editarAsegurado:',record.data);
    var ventana = Ext.create('Ext.window.Window',
    {
        title   : 'Editar persona '+record.get('NOMBRE')
        ,width  : 900
        ,height : 500
        ,modal  : true
        ,loader :
        {
            url       : _p25_urlPantallaPersonas
            ,params   :
            {
                'smap1.cdperson' : record.get('CDPERSON')
            }
            ,scripts  : true
            ,autoLoad : true
        }
    }).show()
    centrarVentanaInterna(ventana);
    _p22_parentCallback = function(json)
    {
        record.set('RFC'              , json.smap1.CDRFC);
        record.set('NOMBRE'           , json.smap1.DSNOMBRE);
        record.set('SEGUNDO_NOMBRE'   , json.smap1.DSNOMBRE1);
        record.set('APELLIDO_PATERNO' , json.smap1.DSAPELLIDO);
        record.set('APELLIDO_MATERNO' , json.smap1.DSAPELLIDO1);
        record.set('APELLIDO_MATERNO' , json.smap1.DSAPELLIDO1);
        record.set('FECHA_NACIMIENTO' , json.smap1.FENACIMI);
        record.set('NACIONALIDAD'     , json.smap1.CDNACION);
        mensajeCorrecto('Datos guardados','Se actualiz&oacute; la persona');
    };
    debug('<_p25_editarAsegurado');
}

function _p25_editarCoberturas(grid,row)
{
    var record=grid.getStore().getAt(row);
    debug('>_p25_editarCoberturas record:',record.data);
    _p25_guardarAsegurados(grid,function()
    {
        centrarVentanaInterna(Ext.create('Ext.window.Window',
        {
            title   : 'Editar coberturas de '+record.get('NOMBRE')
            ,width  : 900
            ,height : 500
            ,modal  : true
            ,loader :
            {
                url       : _p25_urlEditarCoberturas
                ,params   :
                {
                    'smap1.pv_cdunieco'  : _p25_smap1.cdunieco
                    ,'smap1.pv_cdramo'   : _p25_smap1.cdramo
                    ,'smap1.pv_estado'   : _p25_smap1.estado
                    ,'smap1.pv_nmpoliza' : _p25_smap1.nmpoliza
                    ,'smap1.pv_nmsituac' : record.get('NMSITUAC')
                    ,'smap1.pv_cdperson' : record.get('CDPERSON')
                    ,'smap1.pv_cdtipsit' : _p25_smap1.cdtipsit
                }
                ,scripts  : true
                ,autoLoad : true
            }
        }).show());
    });
    debug('<_p25_editarCoberturas');
}

function _p25_editarExclusiones(grid,row)
{
    var record=grid.getStore().getAt(row);
    debug('>_p25_editarExclusiones record:',record.data);
    _p25_guardarAsegurados(grid,function()
    {
        var ventana=Ext.create('Ext.window.Window',
        {
            title   : 'Editar exclusiones de '+(record.get('NOMBRE')+' '+(record.get('SEGUNDO_NOMBRE')?record.get('SEGUNDO_NOMBRE')+' ':' ')+record.get('APELLIDO_PATERNO')+' '+record.get('APELLIDO_MATERNO'))
            ,width  : 900
            ,height : 500
            ,modal  : true
            ,loader :
            {
                url       : _p25_urlEditarExclusiones
                ,params   :
                {
                    'smap1.pv_cdunieco'      : _p25_smap1.cdunieco
                    ,'smap1.pv_cdramo'       : _p25_smap1.cdramo
                    ,'smap1.pv_estado'       : _p25_smap1.estado
                    ,'smap1.pv_nmpoliza'     : _p25_smap1.nmpoliza
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
    debug('<_p25_editarExclusiones');
}

function _p25_guardarAsegurados(grid,callback)
{
    debug('>_p25_guardarAsegurados grid:',grid);
    var store=grid.getStore();
    
    var valido = true;
    
    if(valido)
    {
        var error = '';
        store.each(function(record)
        {
            if(Ext.isEmpty(record.get('RFC')))
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
    
    var asegurados = [];
    
    if(valido)
    {
        asegurados = store.getModifiedRecords();
        valido     = asegurados.length>0||callback!=undefined;
        if(!valido)
        {
            mensajeWarning('No hay cambios');
        }
    }
    
    if(valido&&asegurados.length>0)
    {
        debug('guardar:',asegurados);
        var slist1 = [];
        store.each(function(irecord)
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
            url       : _p25_urlGuardarAsegurados
            ,jsonData :
            {
                slist1 : slist1
                ,smap1 :
                {
                    cdunieco  : _p25_smap1.cdunieco
                    ,cdramo   : _p25_smap1.cdramo
                    ,cdtipsit : _p25_smap1.cdtipsit
                    ,estado   : _p25_smap1.estado
                    ,nmpoliza : _p25_smap1.nmpoliza
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
                    grid.getStore().commitChanges();
                    if(callback!=undefined)
                    {
                        callback();
                    }
                    else
                    {
                        mensajeCorrecto('Datos guardados',json.respuesta);
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
    
    debug('<_p25_guardarAsegurados');
}

function _p25_generarVentanaVistaPrevia()
{
    var itemsVistaPrevia=[];
    
    var mostrarVentana= function()
    {
        var ventana = Ext.create('Ext.window.Window',
        {
            title     : 'Vista previa'
            ,width    : 800
            ,height   : 400
            ,closable : false
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
                    text     : 'Emitir'
                    ,icon    : '${ctx}/resources/fam3icons/icons/key.png'
                    ,handler : function(){_p25_emitir2(ventana,this);}
                }
                ,{
                    text     : 'Cancelar'
                    ,icon    : '${ctx}/resources/fam3icons/icons/cancel.png'
                    ,handler : function(){ventana.destroy();}
                }
            ]
        });
        centrarVentanaInterna(ventana.show());
    }
    
    itemsVistaPrevia.push(
    Ext.create('Ext.grid.Panel',
    {
        title     : 'CONCEPTOS GLOBALES'
        ,stores   : Ext.create('Ext.data.Store',
        {
            model : '_p25_vpModelo'
        })
        ,features :
        [
            {
                ftype: 'summary'
            }
        ]
        ,columns  :
        [
            {
                text             : 'CONCEPTO'
                ,dataIndex       : 'concepto'
                ,sortable        : false
                ,width           : 200
                ,summaryType     : 'count'
                ,summaryRenderer : function(value, summaryData, dataIndex)
                {
                    return Ext.String.format('TOTAL DE {0} CONCEPTOS', value);
                }
            }
            ,{
                text         : 'IMPORTE'
                ,dataIndex   : 'importe'
                ,sortable    : false
                ,renderer    : Ext.util.Format.usMoney
                ,width       : 200
                ,summaryType : 'sum'
            }
        ]
        ,listeners :
        {
            render : function(me)
            {
                Ext.Ajax.request(
                {
                    url     : _p25_urlCargarConceptosGlobales
                    ,params :
                    {
                        'smap1.cdunieco'  : _p25_smap1.cdunieco
                        ,'smap1.cdramo'   : _p25_smap1.cdramo
                        ,'smap1.estado'   : _p25_smap1.estado
                        ,'smap1.nmpoliza' : _p25_smap1.nmpoliza
                        ,'smap1.nmsuplem' : '0'
                        ,'smap1.cdperpag' : _fieldByName('cdperpag').getValue()
                    }
                    ,success : function(response)
                    {
                        var json=Ext.decode(response.responseText);
                        debug('### obtener conceptos globales:',json);
                        if(json.exito)
                        {
                            me.getStore().add(new _p25_vpModelo(
                            {
                                concepto : 'PRIMA NETA'
                                ,importe : json.smap1.PRIMA_NETA
                            }));
                            me.getStore().add(new _p25_vpModelo(
                            {
                                concepto : 'DERECHOS DE POLIZA'
                                ,importe : json.smap1.DERPOL
                            }));
                            me.getStore().add(new _p25_vpModelo(
                            {
                                concepto : 'RECARGOS'
                                ,importe : json.smap1.RECARGOS
                            }));
                            me.getStore().add(new _p25_vpModelo(
                            {
                                concepto : 'IVA'
                                ,importe : json.smap1.IVA
                            }));
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
    
    _p25_storeGrupos.each(function(record)
    {
        itemsVistaPrevia.push(
        Ext.create('Ext.grid.Panel',
        {
            title      : 'TARIFA SUBGRUPO '+record.get('letra')
            ,minHeight : 100
            ,maxHeight : 250
            ,store     : Ext.create('Ext.data.Store',
            {
                model     : '_p25_modeloTarifaEdad'
                ,autoLoad : true
                ,proxy    :
                {
                    type         : 'ajax'
                    ,extraParams :
                    {
                        'smap1.cdunieco'  : _p25_smap1.cdunieco
                        ,'smap1.cdramo'   : _p25_smap1.cdramo
                        ,'smap1.estado'   : _p25_smap1.estado
                        ,'smap1.nmpoliza' : _p25_smap1.nmpoliza
                        ,'smap1.nmsuplem' : '0'
                        ,'smap1.cdplan'   : record.get('cdplan')
                        ,'smap1.cdgrupo'  : record.get('letra')
                        ,'smap1.cdperpag' : _fieldByName('cdperpag').getValue()
                    }
                    ,url         : _p25_urlObtenerTarifaEdad
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
        );
    });
    
    mostrarVentana();
}

function _p25_emitir2(ventana,button)
{
    var params =
    {
        'smap1.cdunieco'  : _p25_smap1.cdunieco
        ,'smap1.cdramo'   : _p25_smap1.cdramo
        ,'smap1.cdtipsit' : _p25_smap1.cdtipsit
        ,'smap1.estado'   : _p25_smap1.estado
        ,'smap1.nmpoliza' : _p25_smap1.nmpoliza
        ,'smap1.cdperpag' : _fieldByName('cdperpag').getValue()
        ,'smap1.feini'    : Ext.Date.format(_fieldByName('feini').getValue(),'d/m/Y')
        ,'smap1.fefin'    : Ext.Date.format(_fieldByName('fefin').getValue(),'d/m/Y')
        ,'smap1.ntramite' : _p25_ntramite
    };
    debug('parametros para emitir:',params);
    ventana.setLoading(true);
    Ext.Ajax.request(
    {
        url      : _p25_urlEmitir
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
                        ,handler : function()
                        {
                            centrarVentanaInterna(Ext.create('Ext.window.Window',
                            {
                                title       : 'Documentos de la p&oacute;liza ' + json.smap1.nmpolizaEmi + ' [' + json.smap1.nmpoliexEmi + ']'
                                ,width      : 500
                                ,height     : 400
                                ,autoScroll : true
                                ,modal      : true
                                ,loader     :
                                {
                                    scripts   : true
                                    ,autoLoad : true
                                    ,url      : _p25_urlVentanaDocumentosClon
                                    ,params   :
                                    {
                                        'smap1.cdunieco'  : _p25_smap1.cdunieco
                                        ,'smap1.cdramo'   : _p25_smap1.cdramo
                                        ,'smap1.estado'   : 'M'
                                        ,'smap1.nmpoliza' : json.smap1.nmpolizaEmi
                                        ,'smap1.nmsuplem' : json.smap1.nmsuplemEmi
                                        ,'smap1.nmsolici' : json.smap1.nmpoliza
                                        ,'smap1.ntramite' : _p25_ntramite
                                        ,'smap1.tipomov'  : '0'
                                    }
                                }
                            }).show());
                        }
                    }
                    ,{
                        xtype    : 'button'
                        ,text    : 'Mesa de control'
                        ,icon    : '${ctx}/resources/fam3icons/icons/house.png'
                        ,handler : _p25_mesacontrol
                    }
                ]);
                
                if(_p25_smap1.VENTANA_DOCUMENTOS=='S')
                {
                    Ext.ComponentQuery.query('[ventanaDocu]')[0].destroy();
                    debug('ventana de documentos destruida');
                }
                mensajeCorrecto('P&oacute;liza emitida',json.respuesta);
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
////// funciones //////

</script>
</head>
<body><div id="_p25_divpri" style="height:1400px;"></div></body>
</html>