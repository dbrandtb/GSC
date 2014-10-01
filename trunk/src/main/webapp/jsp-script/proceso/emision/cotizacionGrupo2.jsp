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
var _p25_urlSubirCenso        = '<s:url namespace="/emision"         action="subirCenso"                    />';
var _p25_urlVentanaDocumentos = '<s:url namespace="/documentos"      action="ventanaDocumentosPoliza"       />';

var _p25_smap1 = <s:property value='%{convertToJSON("smap1")}' escapeHtml="false" />;
debug('_p25_smap1:',_p25_smap1);

var _p25_TARIFA_LINEA       = 1;
var _p25_TARIFA_MODIFICADA  = 2;
var _p25_semaforoPlanChange = true;
var _p25_ntramite           = Ext.isEmpty(_p25_smap1.ntramite) ? false : _p25_smap1.ntramite;

var _p25_clasif;
var _p25_storeGrupos;
var _p25_tabGrupos;
var _p25_tabGruposLineal;
var _p25_gridTarifas;

var _p25_editorNombreGrupo=
{
    xtype       : 'textfield'
    ,allowBlank : false
    ,minLength  : 3
};

var _p25_editorPlan = <s:property value="imap.editorPlanesColumn" />.editor;
_p25_editorPlan.on('change',_p25_editorPlanChange);
debug('_p25_editorPlan:',_p25_editorPlan);

////// variables //////

Ext.onReady(function()
{
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
    
    var _p25_modeloGrupoFields =
    [
        'letra'
        ,'nombre'
        ,'cdplan'
    ];
    
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
    ////// modelos //////
    
    ////// stores //////
    _p25_storeGrupos = Ext.create('Ext.data.Store',
    {
        model : '_p25_modeloGrupo'
    });
    ////// stores //////
    
    ////// componentes //////
    _p25_tabGruposLinealCols =
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
            ,editor    : _p25_editorNombreGrupo
        }
        ,{
            header     : 'Plan'
            ,dataIndex : 'cdplan'
            ,width     : 100
            ,editor    : _p25_editorPlan
            ,renderer  : function(v)
            {
                return rendererColumnasDinamico(v,'cdplan');
            }
        }
    ];
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
                    //,handler : _p25_agregarGrupoClic
                    ,hidden  : _p25_ntramite ? true : false
                }
            ]
            ,columns : _p25_tabGruposLinealCols
            /*
            [
                {
                    header     : 'Suma asegurada'
                    ,dataIndex : 'ptsumaaseg'
                    ,width     : 120
                    ,editor    : _p25_editorSumaAseg
                    ,renderer  : function(v)
                    {
                        return rendererColumnasDinamico(v,'ptsumaaseg');
                    }
                }
                ,{
                    header     : 'Deducible'
                    ,dataIndex : 'deducible'
                    ,width     : 100
                    ,editor    : _p25_editorDeducible
                    ,hidden    : _p25_smap1.cdramo!='4'||_p25_smap1.LINEA_EXTENDIDA=='N'
                    ,renderer  : function(v)
                    {
                        return rendererColumnasDinamico(v,'deducible');
                    }
                }
                ,{
                    header     : 'Ayuda Maternidad'
                    ,dataIndex : 'ayudamater'
                    ,width     : 140
                    ,editor    : _p25_editorAyudaMater
                    ,hidden    : _p25_smap1.cdramo!='4'||_p25_smap1.LINEA_EXTENDIDA=='N'
                    ,renderer  : function(v)
                    {
                        return rendererColumnasDinamico(v,'ayudamater');
                    }
                }
                ,{
                    header     : 'Asis. Inter. Viajes'
                    ,dataIndex : 'asisinte'
                    ,width     : 140
                    ,editor    : _p25_editorAsisInter
                    ,hidden    : _p25_smap1.cdramo!='4'||_p25_smap1.LINEA_EXTENDIDA=='N'
                    ,renderer  : function(v)
                    {
                        return rendererColumnasDinamico(v,'asisinte');
                    }
                }
                ,{
                    header     : 'Emergencia extranjero'
                    ,dataIndex : 'emerextr'
                    ,width     : 140
                    ,editor    : _p25_editorEmerextr
                    ,hidden    : _p25_smap1.cdramo!='4'||_p25_smap1.LINEA_EXTENDIDA=='N'
                    ,renderer  : function(v)
                    {
                        return rendererColumnasDinamico(v,'emerextr');
                    }
                }
                ,{
                    xtype         : 'actioncolumn'
                    ,sortable     : false
                    ,menuDisabled : true
                    ,width        : (botoneslinea.length*20) + 10
                    ,items        : botoneslinea
                }
            ]
            */
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
                                xtype  : 'fieldset'
                                ,title : '<span style="font:bold 14px Calibri;">INFORMACIÓN DEL RIESGO</span>'
                                ,items : [ <s:property value="imap.itemsRiesgo" /> ]
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
                                        //,handler : _p25_construirModificada
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
                            &&records.length>0)
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
    ////// custom //////
    
    ////// loaders //////
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
////// funciones //////

</script>
</head>
<body><div id="_p25_divpri" style="height:1400px;"></div></body>
</html>