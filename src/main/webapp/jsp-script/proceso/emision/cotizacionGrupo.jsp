<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
var _p21_urlObtenerCoberturas     = '<s:url namespace="/emision"         action="obtenerCoberturasPlan"         />';
var _p21_urlObtenerHijosCobertura = '<s:url namespace="/emision"         action="obtenerTatrigarCoberturas"     />';
var _p21_urlSubirCenso            = '<s:url namespace="/emision"         action="subirCenso"                    />';
var _p21_urlCotizar               = '<s:url namespace="/emision"         action="cotizarGrupo"                  />';
var _p21_urlObtenerDetalle        = '<s:url namespace="/emision"         action="obtenerDetalleCotizacionGrupo" />';
var _p21_urlComprar               = '<s:url namespace="/flujocotizacion" action="comprarCotizacion4"            />';
var _p21_urlVentanaDocumentos     = '<s:url namespace="/documentos"      action="ventanaDocumentosPoliza"       />';

var _p21_clasif                   = null;
var _p21_storeGrupos              = null;
var _p21_tabGrupos                = null;
var _p21_tabGruposLineal          = null;
var _p21_tabGruposModifi          = null;
var _p21_semaforoPlanChange       = true;
var _p21_gridTarifas              = null;
var _p21_selectedCdperpag         = null;
var _p21_selectedCdplan           = null;
var _p21_selectedDsplan           = null;
var _p21_selectedNmsituac         = null;

var _p21_smap1 = <s:property value='%{convertToJSON("smap1")}' escapeHtml="false" />;

var _p21_fieldsGrupo =
[
    'letra'
    ,'nombre'
    ,'cdplan'
    ,'ptsumaaseg'
    ,'cdperpag'
    ,'emerextr'
    ,'deducible'
];

var _p21_editorPlan = <s:property value="imap.editorPlanesColumn" />.editor;
_p21_editorPlan.on('change',_p21_editorPlanChange);
debug('_p21_editorPlan:',_p21_editorPlan);

var _p21_editorSumaAseg = <s:property value="imap.editorSumaAsegColumn" />.editor;
debug('_p21_editorSumaAseg:',_p21_editorSumaAseg);

var _p21_editorCdperpag = <s:property value="imap.editorCdperpagColumn" />.editor;
debug('_p21_editorCdperpag:',_p21_editorCdperpag);

var _p21_editorEmerextr = <s:property value="imap.editorEmerextrColumn" />.editor;
debug('_p21_editorEmerextr:',_p21_editorEmerextr);

var _p21_editorDeducible = <s:property value="imap.editorDeducibleColumn" />.editor;
debug('_p21_editorDeducible:',_p21_editorDeducible);

var _p21_TARIFA_LINEA      = 1;
var _p21_TARIFA_MODIFICADA = 2;
////// variables //////

Ext.onReady(function()
{
    ////// modelos //////
    Ext.define('_p21_modeloGrupo',
    {
        extend  : 'Ext.data.Model'
        ,fields : _p21_fieldsGrupo
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
    ////// modelos //////
    
    ////// stores //////
    _p21_storeGrupos = Ext.create('Ext.data.Store',
    {
        model : '_p21_modeloGrupo'
    });
    ////// stores //////
    
    ////// componentes //////
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
                }
            ]
            ,columns :
            [
                {
                    header     : 'ID'
                    ,dataIndex : 'letra'
                    ,width     : 100
                }
                ,{
                    header     : 'Nombre'
                    ,dataIndex : 'nombre'
                    ,width     : 150
                    ,editor    : 'textfield'
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
                    header     : 'Deducible'
                    ,dataIndex : 'deducible'
                    ,width     : 100
                    ,editor    : _p21_editorDeducible
                    ,renderer  : function(v)
                    {
                        return rendererColumnasDinamico(v,'deducible');
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
                    header     : 'Emergencia extranjero'
                    ,dataIndex : 'emerextr'
                    ,width     : 140
                    ,editor    : _p21_editorEmerextr
                    ,renderer  : function(v)
                    {
                        return rendererColumnasDinamico(v,'emerextr');
                    }
                }
                ,{
                    header     : 'Forma de pago'
                    ,dataIndex : 'cdperpag'
                    ,width     : 100
                    ,editor    : _p21_editorCdperpag
                    ,renderer  : function(v)
                    {
                        return rendererColumnasDinamico(v,'cdperpag');
                    }
                }
                ,{
                    xtype  : 'actioncolumn'
                    ,width : 30
                    ,items :
                    [
                        {
                            tooltip  : 'Borrar subgrupo'
                            ,icon    : '${ctx}/resources/fam3icons/icons/delete.png'
                            ,handler : _p21_borrarGrupoClic
                        }
                    ]
                }
            ]
            ,store   : _p21_storeGrupos
            ,height  : 250
            ,plugins : Ext.create('Ext.grid.plugin.RowEditing',
            {
                clicksToEdit  : 1
                ,errorSummary : true
            })
        })
    };
    
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
                }
            ]
            ,columns :
            [
                {
                    header     : 'ID'
                    ,dataIndex : 'letra'
                    ,width     : 100
                }
                ,{
                    header     : 'Nombre'
                    ,dataIndex : 'nombre'
                    ,width     : 150
                    ,editor    : 'textfield'
                }
                ,{
                    header     : 'Plan M'
                    ,dataIndex : 'cdplan'
                    ,width     : 100
                    ,editor    : _p21_editorPlan
                    ,renderer  : function(v)
                    {
                        return rendererColumnasDinamico(v,'cdplan');
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
                    header     : 'Forma de pago'
                    ,dataIndex : 'cdperpag'
                    ,width     : 100
                    ,editor    : _p21_editorCdperpag
                    ,renderer  : function(v)
                    {
                        return rendererColumnasDinamico(v,'cdperpag');
                    }
                }
                ,{
                    xtype  : 'actioncolumn'
                    ,width : 50
                    ,items :
                    [
                        {
                            tooltip  : 'Editar'
                            ,icon    : '${ctx}/resources/fam3icons/icons/pencil.png'
                            ,handler : _p21_editarGrupoClic
                        }
                        ,{
                            tooltip  : 'Borrar subgrupo'
                            ,icon    : '${ctx}/resources/fam3icons/icons/delete.png'
                            ,handler : _p21_borrarGrupoClic
                        }
                    ]
                }
            ]
            ,store   : _p21_storeGrupos
            ,height  : 250
            ,plugins : Ext.create('Ext.grid.plugin.RowEditing',
            {
                clicksToEdit  : 1
                ,errorSummary : true
            })
        })
    };
    ////// componentes //////
    
    ////// contenido //////
    Ext.create('Ext.tab.Panel',
    {
        renderTo   : '_p21_divpri'
        ,itemId    : '_p21_tabpanel'
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
                                xtype  : 'fieldset'
                                ,title : '<span style="font:bold 14px Calibri;">DATOS DEL CONTRATANTE</span>'
                                ,items : [ <s:property value="imap.itemsContratante" /> ]
                            }
                            ,{
                                xtype  : 'fieldset'
                                ,title : '<span style="font:bold 14px Calibri;">INFORMACIÓN DEL RIESGO</span>'
                                ,items : [ <s:property value="imap.itemsRiesgo" /> ]
                            }
                            ,{
                                xtype     : 'fieldset'
                                ,title    : '<span style="font:bold 14px Calibri;">INFORMACI&Oacute;N DE LA P&Oacute;LIZA</span>'
                                ,defaults : { style : 'margin:5px;' }
                                ,items    :
                                [
                                    Ext.create('Ext.form.field.Number',
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
                                ]
                            }
                            ,{
                                xtype  : 'fieldset'
                                ,title : '<span style="font:bold 14px Calibri;">DATOS DEL AGENTE</span>'
                                ,items : [ <s:property value="imap.itemsAgente" /> ]
                            }                        
                            ,{
                                xtype     : 'fieldset'
                                ,title    : '<span style="font:bold 14px Calibri;">NÚMERO DE ASEGURADOS</span>'
                                ,defaults : { style : 'margin:5px;' }
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
                                ,items    :
                                [
                                    {
                                        xtype       : 'filefield'
                                        ,fieldLabel : 'Censo de asegurados'
                                        ,name       : 'censo'
                                        ,buttonText : 'Examinar...'
                                        ,allowBlank : false
                                        ,buttonOnly : false
                                        ,width      : 450
                                        ,cAccept    : ['csv']
                                        ,msgTarget  : 'side'
                                        ,listeners  :
                                        {
                                            change : function(me)
                                            {
                                                var indexofPeriod = me.getValue().lastIndexOf("."),
                                                uploadedExtension = me.getValue().substr(indexofPeriod + 1, me.getValue().length - indexofPeriod).toLowerCase();
                                                if (!Ext.Array.contains(this.cAccept, uploadedExtension))
                                                {
                                                    Ext.MessageBox.show(
                                                    {
                                                        title   : 'Error de tipo de archivo',
                                                        msg     : 'Extensiones permitidas: ' + this.cAccept.join(),
                                                        buttons : Ext.Msg.OK,
                                                        icon    : Ext.Msg.WARNING
                                                    });
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
                            {
                                text     : 'Cotizar'
                                ,itemId  : '_p21_botonCotizar'
                                ,icon    : '${ctx}/resources/fam3icons/icons/calculator.png'
                                ,handler : _p21_cotizarClic
                            }
                            ,{
                                text     : 'Nueva'
                                ,icon    : '${ctx}/resources/fam3icons/icons/arrow_refresh.png'
                                ,handler : _p21_cotizarNueva
                            }
                        ]
                    })
                ]
            }
        ]
    });
    ////// contenido //////
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
    debug('<_p21_agregarTabGrupos');
}

function _p21_agregarTab(tab)
{
    debug('>_p21_agregarTab:',tab);
    _p21_tabpanel().add(tab);
    debug('active:',Ext.ComponentQuery.query('#'+tab.itemId)[0]);
    _p21_setActiveTab(tab.itemId);
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
            Ext.MessageBox.confirm('Confirmar', 'Se borrarán los subgrupos existentes<br>¿Desea continuar?', function(btn)
            {
                if(btn === 'yes')
                {
                    _p21_construirLinea(button,event,true);
                }
            });
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
            Ext.MessageBox.confirm('Confirmar', 'Se borrarán los subgrupos existentes<br>¿Desea continuar?', function(btn)
            {
                if(btn === 'yes')
                {
                    _p21_construirModificada(button,event,true);
                }
            });
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

function _p21_renombrarGrupos(sinBorrarPestañas)
{
    debug('>_p21_renombrarGrupos');
    if(!sinBorrarPestañas)
    {
        _p21_quitarTabsDetalleGrupo();
    }
    var letras=['A','B','C','D','E','F','G','H','I','J','K','L',
    'M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
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
    _p21_storeGrupos.add(new _p21_modeloGrupo());
    _p21_renombrarGrupos(true);
    debug('<_p21_agregarGrupoClic');
}

function _p21_borrarGrupoClic(grid,rowIndex)
{
    var record = grid.getStore().getAt(rowIndex);
    debug('>_p21_borrarGrupoClic:',record.data);
    Ext.Msg.confirm('Borrar grupo','¿Desea borrar el grupo '+record.get('letra')+' y renombrar los grupos en orden alfabético?<br>Esta acci&oacute;n cerrará las pestañas de detalle de subgrupos y podría perder los cambios no guardados.',
    function(button){
        debug('clicked:',button);
        if(button=='yes')
        {
            _p21_storeGrupos.remove(record);
            _p21_renombrarGrupos();
        }
    });
    debug('<_p21_borrarGrupoClic');
}

function _p21_editarGrupoClic(grid,rowIndex)
{
    var record = grid.getStore().getAt(rowIndex);
    debug('>_p21_editarGrupoClic:',record);
    
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
        _p21_quitarTabsDetalleGrupo(record.get('letra'));
        _p21_tabpanel().setLoading(true);
        Ext.Ajax.request(
        {
            url      : _p21_urlObtenerCoberturas
            ,params  :
            {
                'smap1.cdramo'    : _p21_smap1.cdramo
                ,'smap1.cdtipsit' : _p21_smap1.cdtipsit
                ,'smap1.cdplan'   : record.get('cdplan')
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
                                            var item = Ext.create('Ext.form.Panel',
                                            {
                                                width       : 440
                                                ,frame      : true
                                                ,height     : 140
                                                ,autoScroll : true
                                                ,defaults   : { style : 'margin:5px;', labelWidth : 100 }
                                                ,items      : json.slist1[j].hijos
                                                ,cdgarant   : json.slist1[j].CDGARANT
                                                ,tbar       :
                                                [
                                                    {
                                                        xtype       : 'displayfield'
                                                        ,fieldLabel : '<span style="color:white;">'+json.slist1[j].DSGARANT+'</span>'
                                                        ,labelWidth : 300
                                                    }
                                                    ,{
                                                        xtype       : 'checkbox' 
                                                        ,boxLabel   : 'Amparada'
                                                        ,name       : 'amparada'
                                                        ,inputValue : 'S'
                                                        ,checked    : true
                                                        ,disabled   : json.slist1[j].SWOBLIGA=='S'
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
                                                debug('Datos a cargar para '+json.slist1[j].CDGARANT+':',datosAnteriores);
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
                                                    title     : 'COBERTURAS DEL SUBGRUPO'
                                                    ,layout   :
                                                    {
                                                        type     : 'table'
                                                        ,columns : 2
                                                    }
                                                    ,defaults : { style : 'margin:5px' }
                                                    ,items    : items
                                                })
                                            ]
                                            ,buttonAlign : 'center'
                                            ,buttons     :
                                            [
                                                {
                                                    text     : 'Guardar'
                                                    ,icon    : '${ctx}/resources/fam3icons/icons/disk.png'
                                                    ,handler : function(button){_p21_guardarGrupo(button.up().up());}
                                                }
                                            ]
                                        });
                                        var formsValidos = Ext.ComponentQuery.query('[valido=true]');
                                        debug('formsValidos:',formsValidos);
                                        for(var k=0;k<formsValidos.length;k++)
                                        {
                                            var form = formsValidos[k];
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

function _p21_guardarGrupo(panelGrupo)
{
     debug('>_p21_guardarGrupo:',panelGrupo);
     
     var letraGrupo  = panelGrupo.letraGrupo;
     debug('letraGrupo:',letraGrupo);
     
     var formsTatrigar = panelGrupo.down('[title=COBERTURAS DEL SUBGRUPO]').items.items;
     debug('formsTatrigar:',formsTatrigar);
     
     var tvalogars = [];
     var valido    = true;
     for(var i=0;i<formsTatrigar.length;i++)
     {
         var iFormTatrigar = formsTatrigar[i];
         valido            = valido && iFormTatrigar.isValid();
         var tvalogar      = iFormTatrigar.getValues();
         if(tvalogar.swobliga=='S')
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
     
     if(valido)
     {
         debug('tvalogars:',tvalogars);
         var recordGrupo=_p21_obtenerGrupoPorLetra(letraGrupo);
         recordGrupo['tvalogars'] = tvalogars;
         recordGrupo['valido']    = true;
         debug('recordGrupo:',recordGrupo);
         mensajeCorrecto('Se han guardado los datos','Se han guardado los datos',_p21_setActiveResumen);
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
    debug('<_p21_setActiveResumen');
}

function _p21_setActiveConcepto()
{
    debug('>_p21_setActiveConcepto');
    _p21_setActiveTab('_p21_tabConcepto');
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
    debug('<_p21_setActiveTab');
}

function _p21_editorPlanChange(combo,newValue,oldValue,eOpts)
{
    debug('>_p21_editorPlanChange new',newValue,'old',oldValue+'x');
    if(oldValue+'x'!='x'&&_p21_clasif==_p21_TARIFA_MODIFICADA&&_p21_semaforoPlanChange)
    {
        Ext.MessageBox.confirm('Confirmar', 'Al cambiar el plan se borrar&aacute; el detalle del subgrupo<br/>¿Desea continuar?', function(btn)
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
        });
    }
    debug('<_p21_editorPlanChange');
}

function _p21_query(regex)
{
    debug('>_p21_query<',regex);
    return Ext.ComponentQuery.query(regex);
}

function _p21_cotizarClic()
{
    debug('>_p21_cotizarClic');
    var valido = true;
    
    if(valido)
    {
        valido = _p21_tabConcepto().down('[xtype=form]').isValid();
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
    
    if(valido&&_p21_clasif==_p21_TARIFA_MODIFICADA)
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
        form.submit(
        {
            params   :
            {
                'smap1.timestamp' : timestamp
            }
            ,timeout : 180000
            ,success : function()
            {
                var conceptos = form.getValues();
                conceptos['timestamp'] = timestamp;
                conceptos['clasif']    = _p21_clasif;
                conceptos['cdunieco']  = _p21_smap1.cdunieco;
                conceptos['cdramo']    = _p21_smap1.cdramo;
                conceptos['cdtipsit']  = _p21_smap1.cdtipsit;
                var grupos = [];
                if(_p21_clasif==_p21_TARIFA_LINEA)
                {
                    _p21_storeGrupos.each(function(record)
                    {
                        var grupo = record.data;
                        grupos.push(grupo);
                    });
                }
                else
                {
                    _p21_storeGrupos.each(function(record)
                    {
                        var grupo = record.data;
                        grupo['tvalogars']=record.tvalogars;
                        grupos.push(grupo);
                    });
                }
                Ext.Ajax.request(
                {
                    url       : _p21_urlCotizar
                    ,timeout  : 180000
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
                            _p21_fieldNmpoliza().setValue(json.smap1.nmpoliza);
                            
                            _p21_tabConcepto().down('[xtype=form]').setDisabled(true);
                        
                            Ext.define('_p21_modeloTarifa',
                            {
                                extend  : 'Ext.data.Model'
                                ,fields : Ext.decode(json.smap1.fields)
                            });
                    
                            _p21_gridTarifas=Ext.create('Ext.grid.Panel',
                            {
                                title             : 'Resultados'
                                ,store            : Ext.create('Ext.data.Store',
                                {
                                    model : '_p21_modeloTarifa'
                                    ,data : json.slist2
                                })
                                ,columns          : Ext.decode(json.smap1.columnas)
                                ,selType          : 'cellmodel'
                                ,minHeight        : 100
                                ,enableColumnMove : false
                                ,buttonAlign      : 'center'
                                ,buttons          :
                                [
                                    {
                                        text      : 'Generar tr&aacute;mite'
                                        ,itemId   : '_p21_botonComprar'
                                        ,icon     : '${ctx}/resources/fam3icons/icons/book_next.png'
                                        ,handler  : _p21_comprarClic
                                        ,disabled : true
                                    }
                                    ,{
                                        text      : 'Detalles'
                                        ,itemId   : '_p21_botonDetalles'
                                        ,icon     : '${ctx}/resources/fam3icons/icons/text_list_numbers.png'
                                        ,handler  : _p21_detallesClic
                                        ,disabled : true
                                    }
                                    ,{
                                        text     : 'Editar'
                                        ,itemId  : '_p21_botonEditar'
                                        ,icon    : '${ctx}/resources/fam3icons/icons/pencil.png'
                                        ,handler : _p21_editarClic
                                    }
                                    ,{
                                        text     : 'Clonar'
                                        ,itemId  : '_p21_botonClonar'
                                        ,icon    : '${ctx}/resources/fam3icons/icons/calculator.png'
                                        ,handler : _p21_cotizarClonar
                                    }
                                    ,{
                                        text     : 'Nueva'
                                        ,icon    : '${ctx}/resources/fam3icons/icons/arrow_refresh.png'
                                        ,handler : _p21_cotizarNueva
                                    }
                                ]
                                ,listeners :
                                {
                                    select : _p21_tarifaSelect
                                }
                            });
                            
                            _p21_tabConcepto().add(_p21_gridTarifas);
                            setTimeout(function(){debug('timeout 1000');window.parent.scrollTo(0, 99999);},1000);
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
    
    debug('<_p21_cotizarClic');
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
////// funciones //////
</script>
</head>
<body>
<div id="_p21_divpri" style="height:1200px;"></div>
</body>
</html>