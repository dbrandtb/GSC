<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
////// urls //////
var _p29_urlPantallaCliente                = '<s:url namespace="/catalogos" action="includes/personasLoader"              />';
var _p29_urlCotizacionAutoIndividual       = '<s:url namespace="/emision"   action="cotizacionAutoIndividual"             />';
var _p29_urlCargarDatosComplementarios     = '<s:url namespace="/emision"   action="cargarDatosComplementariosAutoInd"    />';
var _p29_urlCargarTvalosit                 = '<s:url namespace="/emision"   action="cargarValoresSituacion"               />';
var _p29_urlCargarRetroactividadSuplemento = '<s:url namespace="/emision"   action="cargarRetroactividadSuplemento"       />';
var _p29_urlMovimientoMpoliper             = '<s:url namespace="/emision"   action="movimientoMpoliper"                   />';
var _p29_urlGuardar                        = '<s:url namespace="/emision"   action="guardarComplementariosAutoIndividual" />';
var _p29_urlRecotizar                      = '<s:url namespace="/"          action="recotizar"                            />';
var _p29_urlEmitir                         = '<s:url namespace="/"          action="emitir"                               />';
////// urls //////

////// variables //////
var _p29_smap1 = <s:property value="%{convertToJSON('smap1')}" escapeHtml="false" />;
debug('_p29_smap1:',_p29_smap1);

var _p29_polizaAdicionalesItems = null;
var _p29_adicionalesItems       = null;
var _p22_parentCallback         = false;
////// variables //////

Ext.onReady(function()
{
	////// modelos //////
	Ext.define('_p29_polizaModelo',
	{
	    extend  : 'Ext.data.Model'
	    ,fields :
	    [
	        {
	            type        : 'date'
	            ,dateFormat : 'd/m/Y'
	            ,name       : 'feini'
	        }
	        ,{
                type        : 'date'
                ,dateFormat : 'd/m/Y'
                ,name       : 'fefin'
            }
	        ,<s:property value="imap.polizaFields" />
	        ,<s:property value="imap.agenteFields" />
	        <s:if test='%{getImap().get("polizaAdicionalesFields")!=null}'>
                ,<s:property value="imap.polizaAdicionalesFields" />
            </s:if>
	    ]
	});
	
	Ext.define('ModeloDetalleCotizacion',
	{
	    extend  : 'Ext.data.Model'
	    ,fields :
	    [
	        {name  : 'Codigo_Garantia'}
	        ,{name : 'Importe',type : 'float'}
	        ,{name : 'Nombre_garantia'}
	        ,{name : 'cdtipcon'}
	        ,{name : 'nmsituac'}
	        ,{name : 'orden'}
	        ,{name : 'parentesco'}
	        ,{name : 'orden_parentesco'}
	    ]
	});
	////// modelos //////
	
	////// stores //////
	////// stores //////
	
	////// componentes //////
	_p29_polizaAdicionalesItems =
	[
	    <s:property value="imap.agenteItems" />
	    ,{
	        xtype       : 'datefield'
	        ,format     : 'd/m/Y'
	        ,fieldLabel : 'INICIO DE VIGENCIA'
	        ,name       : 'feini'
	        ,listeners  : { change : _p29_fefinChange }
	        ,style      : 'margin:5px;'
	    }
	    ,{
	        xtype       : 'datefield'
	        ,format     : 'd/m/Y'
	        ,fieldLabel : 'FIN DE VIGENCIA'
	        ,name       : 'fefin'
	        ,readOnly   : true
            ,style      : 'margin:5px;'
	    }
	];
	<s:if test='%{getImap().get("polizaAdicionalesItems")!=null}'>
	    var aux=[<s:property value="imap.polizaAdicionalesItems" />];
	    for(var i=0;i<_p29_polizaAdicionalesItems.length;i++)
	    {
	        aux.push(_p29_polizaAdicionalesItems[i]);
	    }
	    _p29_polizaAdicionalesItems = aux;
	</s:if>
	
	_p29_adicionalesItems = [];
	<s:if test='%{getImap().get("adicionalesItems")!=null}'>
	    _p29_adicionalesItems = [<s:property value="imap.adicionalesItems" />];
	</s:if>
	////// componentes //////
	
	////// contenido //////
	Ext.create('Ext.panel.Panel',
	{
	    itemId    : '_p29_panelpri'
        ,renderTo : '_p29_divpri'
        ,border   : 0
	    ,defaults : { style : 'margin:5px;' }
	    ,items    :
	    [
	        Ext.create('Ext.form.Panel',
	        {
	            itemId    : '_p29_polizaForm'
	            ,title    : 'DATOS DE P&Oacute;LIZA'
	            ,defaults : { style : 'margin:5px;' }
	            ,layout   :
	            {
	                type     : 'table'
	                ,columns : 2
	                ,tdAttrs : { valign : 'top' }
	            }
	            ,items    :
	            [
	                {
	                    xtype  : 'fieldset'
	                    ,title : '<span style="font:bold 14px Calibri;">DATOS GENERALES</span>'
	                    ,items : [<s:property value="imap.polizaItems" />]
	                }
	                ,{
	                    xtype  : 'fieldset'
	                    ,title : '<span style="font:bold 14px Calibri;">DATOS ADICIONALES</span>'
	                    ,items : _p29_polizaAdicionalesItems
	                }
	            ]
	        })
	        ,Ext.create('Ext.form.Panel',
	        {
	            itemId    : '_p29_adicionalesForm'
	            ,title    : 'DATOS ADICIONALES DE INCISO'
	            ,defaults : { style : 'margin:5px;' }
	            ,layout   :
	            {
	                type     : 'table'
	                ,columns : 2
	            }
	            ,items    : _p29_adicionalesItems
	        })
	        ,Ext.create('Ext.panel.Panel',
	        {
	            itemId      : '_p29_clientePanel'
	            ,title      : 'CLIENTE'
	            ,height     : 300
	            ,autoScroll : true
	            ,loader     :
	            {
	                url       : _p29_urlPantallaCliente
	                ,scripts  : true
	                ,autoLoad : false
	            }
	        })
	        ,Ext.create('Ext.panel.Panel',
	        {
	            itemId       : '_p29_panelBotones'
	            ,border      : 0
	            ,buttonAlign : 'center'
	            ,buttons     :
	            [
	                {
	                    itemId   : '_p29_botonEmitir'
	                    ,text    : 'Emitir'
	                    ,icon    : '${ctx}/resources/fam3icons/icons/key.png'
	                    ,handler : _p29_emitirClic
	                }
	                ,{
	                    itemId   : '_p29_botonGuardar'
	                    ,text    : 'Guardar'
	                    ,icon    : '${ctx}/resources/fam3icons/icons/disk.png'
	                    ,handler : function(){ _p29_guardar(); }
	                }
	                ,{
	                    text     : 'Nueva'
	                    ,icon    : '${ctx}/resources/fam3icons/icons/arrow_refresh.png'
	                    ,handler : _p29_nuevaClic
	                }
	            ]
	        })
	    ]
	});
	////// contenido //////
	
	////// custom //////
	_fieldByLabel('AGENTE').hide();
	_fieldByName('porparti').setMaxValue(99);
	////// custom //////
	
	////// loaders //////
	Ext.Ajax.request(
	{
	    url     : _p29_urlCargarDatosComplementarios
	    ,params :
	    {
	        'smap1.cdunieco'  : _p29_smap1.cdunieco
	        ,'smap1.cdramo'   : _p29_smap1.cdramo
	        ,'smap1.estado'   : _p29_smap1.estado
	        ,'smap1.nmpoliza' : _p29_smap1.nmpoliza
	    }
	    ,success : function(response)
	    {
	        var json = Ext.decode(response.responseText);
	        debug('### cargar datos complementarios:',json);
	        if(json.exito)
	        {
	            var form   = _fieldById('_p29_polizaForm');
	            var record = new _p29_polizaModelo(json.smap1);
	            debug('record:',record);
	            form.loadRecord(record);
	            
	            if(json.smap1.agente_sec+'x'!='x')
	            {
	                var age2 = _fieldByName('agente_sec');
	                age2.getStore().load(
	                {
	                    params :
	                    {
	                        'params.agente' : json.smap1.agente_sec
	                    }
	                    ,callback : function()
	                    {
	                        age2.setValue(age2.findRecord('key',json.smap1.agente_sec));
	                    }
	                });
	            }
	            
	            _fieldById('_p29_clientePanel').loader.load(
	            {
	                params:
	                {
	                    'smap1.cdperson' : json.smap1.cdperson
	                }
	            });
	            
	            _p22_parentCallback = _p29_personaSaved;
	        }
	        else
	        {
	            mensajeError(json.respuesta);
	        }
	    }
	    ,failure : errorComunicacion
	});
	
	Ext.Ajax.request(
	{
	    url     : _p29_urlCargarTvalosit
	    ,params :
	    {
	        'smap1.cdunieco'  : _p29_smap1.cdunieco
	        ,'smap1.cdramo'   : _p29_smap1.cdramo
	        ,'smap1.estado'   : _p29_smap1.estado
	        ,'smap1.nmpoliza' : _p29_smap1.nmpoliza
	        ,'smap1.nmsituac' : '1'
	    }
	    ,success : function(response)
	    {
	        var json=Ext.decode(response.responseText);
	        debug('### tvalosit:',json);
	        if(json.exito)
	        {
	            var form = _fieldById('_p29_adicionalesForm');
	            for(var i in json.smap1)
	            {
	                var item = _fieldByName(i,form,true);
	                if(item)
	                {
	                    item.setValue(json.smap1[i]);
	                }
	            }

	            _p29_loadCallback();
	        }
	        else
	        {
	            mensajeError(json.respuesta);
	        }
	    }
	    ,failure : errorComunicacion
	});
	////// loaders //////
});

////// funciones //////
function _p29_fefinChange(me,newVal,oldVal)
{
    debug('>_p29_fefinChange:',newVal,oldVal,'DUMMY');
    debug('<_p29_fefinChange');
}

function _p29_nuevaClic()
{
    _fieldById('_p29_panelpri').setLoading(true);
    Ext.create('Ext.form.Panel').submit(
    {
        url     : _p29_urlCotizacionAutoIndividual
        ,params :
        {
            'smap1.cdramo'    : _p29_smap1.cdramo
            ,'smap1.cdtipsit' : _p29_smap1.cdtipsit
        }
        ,standardSubmit : true
    });
}

function _p29_loadCallback()
{
    var vigen = _fieldByLabel('VIGENCIA');
    vigen.hide();
    var feini = _fieldByName('feini');
    var fefin = _fieldByName('fefin');
    feini.on(
    {
        change : function(me,val)
        {
            try
            {
                fefin.setValue(Ext.Date.add(val,Ext.Date.DAY,vigen.getValue()))
            }
            catch(e)
            {
                debug(e);
            }
        }
    });
    
    Ext.Ajax.request(
    {
        url     : _p29_urlCargarRetroactividadSuplemento
        ,params :
        {
            'smap1.cdunieco'  : _p29_smap1.cdunieco
            ,'smap1.cdramo'   : _p29_smap1.cdramo
            ,'smap1.cdtipsup' : 1
            ,'smap1.cdusuari' : _p29_smap1.cdusuari
            ,'smap1.cdtipsit' : _p29_smap1.cdtipsit
        }
        ,success : function(response)
        {
            var json = Ext.decode(response.responseText);
            debug('### obtener retroactividad:',json);
            if(json.exito)
            {
                feini.setMinValue(Ext.Date.add(new Date(),Ext.Date.DAY,(json.smap1.retroac-0)*-1));
                feini.setMaxValue(Ext.Date.add(new Date(),Ext.Date.DAY,json.smap1.diferi-0));
                feini.isValid();
            }
            else
            {
                mensajeError(json.respuesta);
            }
        }
        ,failure : errorComunicacion
    });
}

function _p29_personaSaved()
{
    debug('>_p29_personaSaved');
    Ext.Ajax.request(
    {
        url     : _p29_urlMovimientoMpoliper
        ,params :
        {
            'smap1.cdunieco'  : _p29_smap1.cdunieco
            ,'smap1.cdramo'   : _p29_smap1.cdramo
            ,'smap1.estado'   : _p29_smap1.estado
            ,'smap1.nmpoliza' : _p29_smap1.nmpoliza
            ,'smap1.nmsituac' : '0'
            ,'smap1.cdrol'    : '1'
            ,'smap1.cdperson' : _p22_fieldCdperson().getValue()
            ,'smap1.nmsuplem' : '0'
            ,'smap1.status'   : 'V'
            ,'smap1.nmorddom' : '1'
            ,'smap1.accion'   : 'I'
            ,'smap1.swexiper' : 'S'
        }
        ,success : function(response)
        {
            var json=Ext.decode(response.responseText);
            debug('### mpoliper:',json);
            if(json.exito==false)
            {
                mensajeError(json.respuesta);
            }
        }
        ,failure : errorComunicacion
    });
    debug('<_p29_personaSaved');
}

function _p29_emitirClic()
{
    debug('>_p29_emitirClic');
    
    _p29_guardar(_p29_mostrarVistaPrevia);
    
    debug('<_p29_emitirClic');
}

function _p29_guardar(callback)
{
    debug('>_p29_guardar');
    var form1  = _fieldById('_p29_polizaForm');
    var form2  = _fieldById('_p29_adicionalesForm');
    var valido = form1.isValid()&&form2.isValid();
    if(!valido)
    {
        datosIncompletos();
    }
    
    if(valido)
    {
        valido = !Ext.isEmpty(_p22_fieldCdperson().getValue());
        if(!valido)
        {
            mensajeWarning('Debe buscar o agregar el cliente');
        }
    }
    
    if(valido)
    {
        var json =
        {
            smap1  : form1.getValues()
            ,smap2 : form2.getValues()
        };
        
        json.smap1['cdunieco'] = _p29_smap1.cdunieco;
        json.smap1['cdramo']   = _p29_smap1.cdramo;
        json.smap1['estado']   = _p29_smap1.estado;
        json.smap1['ntramite'] = _p29_smap1.ntramite;
        json.smap1['cdagente'] = _fieldByLabel('AGENTE').getValue();
        
        debug('json para guardar:',json);
        var panelPri = _fieldById('_p29_panelpri');
        panelPri.setLoading(true);
        Ext.Ajax.request(
        {
            url       : _p29_urlGuardar
            ,jsonData : json
            ,success  : function(response)
            {
                panelPri.setLoading(false);
                var json = Ext.decode(response.responseText);
                debug('### guardar:',json);
                if(json.exito)
                {
                    if(callback)
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
            ,failure  : function()
            {
                panelPri.setLoading(false);
                errorComunicacion();
            }
        });
    }
    
    debug('<_p29_guardar');
}

function _p29_mostrarVistaPrevia()
{
    debug('>_p29_mostrarVistaPrevia');
    var panelpri = _fieldById('_p29_panelpri');
    panelpri.setLoading(true);
    Ext.Ajax.request(
    {
        url     : _p29_urlRecotizar
        ,params :
        {
            cdunieco           : _p29_smap1.cdunieco
            ,cdramo            : _p29_smap1.cdramo
            ,cdtipsit          : _p29_smap1.cdtipsit
            ,'panel1.nmpoliza' : _p29_smap1.nmpoliza
        }
        ,success : function(response)
        {
            panelpri.setLoading(false);
            var json = Ext.decode(response.responseText);
            debug('### retarificar:',json);
            if(json.mensajeRespuesta&&json.mensajeRespuesta.length>0)
            {
                centrarVentanaInterna(Ext.Msg.show(
                {
                    title    :'Verificar datos'
                    ,msg     : json.mensajeRespuesta
                    ,buttons : Ext.Msg.OK
                    ,icon    : Ext.Msg.WARNING
                }));
            }
            else
            {
                var orden=0;
                var parentescoAnterior='qwerty';
                for(var i=0;i<json.slist1.length;i++)
                {
                    if(json.slist1[i].parentesco!=parentescoAnterior)
                    {
                        orden++;
                        parentescoAnterior=json.slist1[i].parentesco;
                    }
                    json.slist1[i].orden_parentesco=orden+'_'+json.slist1[i].parentesco;
                }
                centrarVentanaInterna(Ext.create('Ext.window.Window',
                {
                    title          : 'Tarifa final'
                    ,autoScroll    : true
                    ,width         : 660
                    ,height        : 400
                    ,defaults      : { width: 650 }
                    ,modal         : false
                    ,closable      : false
                    ,collapsible   : true
                    ,titleCollapse : true
                    ,items         :
                    [
                        Ext.create('Ext.grid.Panel',
                        {
                            width  : 600
                            ,store : Ext.create('Ext.data.Store',
                            {
                                model       : 'ModeloDetalleCotizacion'
                                ,groupField : 'orden_parentesco'
                                ,sorters    :
                                [
                                    {
                                        sorterFn: function(o1, o2)
                                        {
                                            if (o1.get('orden') === o2.get('orden'))
                                            {
                                                return 0;
                                            }
                                            return o1.get('orden') < o2.get('orden') ? -1 : 1;
                                        }
                                    }
                                ]
                                ,proxy :
                                {
                                    type    : 'memory'
                                    ,reader : 'json'
                                }
                                ,data:json.slist1
                            })
                            ,columns :
                            [
                                {
                                    header           : 'Nombre de la cobertura'
                                    ,dataIndex       : 'Nombre_garantia'
                                    ,flex            : 2
                                    ,summaryType     : 'count'
                                    ,summaryRenderer : function(value)
                                    {
                                        return Ext.String.format('Total de {0} cobertura{1}', value, value !== 1 ? 's' : '');
                                    }
                                }
                                ,{
                                    header       : 'Importe por cobertura'
                                    ,dataIndex   : 'Importe'
                                    ,flex        : 1
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
                                            formatName:function(name)
                                            {
                                                return name.split("_")[1];
                                            }
                                        }
                                    ]
                                    ,ftype          : 'groupingsummary'
                                    ,startCollapsed : true
                                }
                            ]
                        })
                        ,Ext.create('Ext.toolbar.Toolbar',
                        {
                            buttonAlign : 'right'
                            ,items      :
                            [
                                '->'
                                ,Ext.create('Ext.form.Label',
                                {
                                    style          : 'color:white;'
                                    ,initComponent : function()
                                    {
                                        var sum=0;
                                        for(var i=0;i<json.slist1.length;i++)
                                        {
                                            sum+=parseFloat(json.slist1[i].Importe);
                                        }
                                        this.setText('Total: '+Ext.util.Format.usMoney(sum));
                                        this.callParent();
                                    }
                                })
                            ]
                        })
                        ,Ext.create('Ext.form.Panel',
                        {
                            layout :
                            {
                                type     : 'table'
                                ,columns : 5
                            }
                            ,defaults : { style : 'margin:5px;' }
                            ,items    :
                            [
                                {
                                    xtype       : 'textfield'
                                    ,itemId     : '_p29_numerofinalpoliza'
                                    ,fieldLabel : 'N&uacute;mero de poliza'
                                    ,readOnly   : true
                                    ,colspan    : 4
                                }
                                ,{
                                    itemId   : '_p29_botonEmitirPolizaFinal'
                                    ,xtype   : 'button'
                                    ,text    : 'Emitir'
                                    ,icon    : '${ctx}/resources/fam3icons/icons/award_star_gold_3.png'
                                    ,handler : _p29_emitirFinal
                                }
                                ,{
                                    xtype    : 'button'
                                    ,itemId  : '_p29_botonCancelarEmision'
                                    ,text    : 'Cancelar'
                                    ,icon    : '${ctx}/resources/fam3icons/icons/cancel.png'
                                    ,handler : function()
                                    {
                                        var me=this;
                                        me.up().up().destroy();
                                    }
                                }
                                ,{ xtype : 'label' }
                                ,{ xtype : 'label' }
                                ,{ xtype : 'label' }
                            ]
                        })
                    ]
                }).show());
            }
        }
        ,failure : function()
        {
            panelpri.setLoading(false);
            errorComunicacion();
        }
    });
    debug('<_p29_mostrarVistaPrevia');
}

function _p29_emitirFinal(me)
{
    debug('>_p29_emitirFinal');
    var panelpri = me.up().up();
    panelpri.setLoading(true);
    Ext.Ajax.request(
    {
        url      : _p29_urlEmitir
        ,timeout : 240000
        ,params  :
        {
            'panel1.pv_ntramite'  : _p29_smap1.ntramite
            ,'panel2.pv_cdunieco' : _p29_smap1.cdunieco
            ,'panel2.pv_cdramo'   : _p29_smap1.cdramo
            ,'panel2.pv_estado'   : _p29_smap1.estado
            ,'panel1.pv_nmpoliza' : _p29_smap1.nmpoliza
            ,'panel2.pv_nmpoliza' : _p29_smap1.nmpoliza
            ,'panel2.pv_cdtipsit' : _p29_smap1.cdtipsit
        }
        ,success:function(response)
        {
            panelpri.setLoading(false);
            var json=Ext.decode(response.responseText);
            debug('### emitir:',json);
            if(json.success==true)
            {
                //datComPolizaMaestra=json.panel2.nmpoliza;
                //debug("datComPolizaMaestra",datComPolizaMaestra);
                _fieldById('_p29_numerofinalpoliza').setValue(json.panel2.nmpoliex);
                _fieldById('_p29_botonEmitirPolizaFinal').setDisabled(true);
                //Ext.getCmp('_p29_botonEmitirPolizaFinalPreview').hide();
                //Ext.getCmp('botonImprimirPolizaFinal').setDisabled(false);
                //Ext.getCmp('botonReenvioWS').hide();
                if(_p29_smap1.SITUACION == 'AUTO')
                {
                    //_mensajeEmail = json.mensajeEmail;
                    //Ext.getCmp('botonEnvioEmail').enable();
                }
                else
                {
                    //Ext.getCmp('botonEnvioEmail').hide();
                }
                if(_p29_smap1.SITUACION=='AUTO')
                {
                    //Ext.getCmp('venDocVenEmiBotIrCotiza').show();
                }
                else
                {
                    //Ext.getCmp('venDocVenEmiBotNueCotiza').show();
                }
                _fieldById('_p29_botonCancelarEmision').setDisabled(true);
                if(json.mensajeRespuesta&&json.mensajeRespuesta.length>0)
                {
                    var ventanaTmp = Ext.Msg.show(
                    {
                        title    : 'Aviso del sistema'
                        ,msg     : json.mensajeRespuesta
                        ,buttons : Ext.Msg.OK
                        ,icon    : Ext.Msg.WARNING
                        ,fn      : function()
                        {
                            if(!Ext.isEmpty(json.nmpolAlt))
                            {
                                mensajeCorrecto("Aviso","P&oacute;liza Emitida: " + json.nmpolAlt);
                            }
                        }
                    });
                    centrarVentanaInterna(ventanaTmp);
                }
                else
                {
                    if(!Ext.isEmpty(json.nmpolAlt))
                    {
                        mensajeCorrecto("Aviso","P&oacute;liza Emitida: " + json.nmpolAlt);
                    }
                }
            }
            else
            {
                /*if(json.retryWS)
                {
                    datComPolizaMaestra=json.panel2.nmpoliza;
                    debug("datComPolizaMaestra",datComPolizaMaestra);
                    Ext.getCmp('_p29_botonEmitirPolizaFinal').hide();
                    Ext.getCmp('_p29_botonEmitirPolizaFinalPreview').hide();
                    if(_p29_smap1.SITUACION=='AUTO')
                    {
                        Ext.getCmp('venDocVenEmiBotIrCotiza').show();
                    }
                    else
                    {
                        Ext.getCmp('venDocVenEmiBotNueCotiza').show();
                    }
                    Ext.getCmp('_p29_botonCancelarEmision').setDisabled(true);
                }*/
                Ext.Msg.show(
                {
                    title    :'Aviso'
                    ,msg     : json.mensajeRespuesta
                    ,buttons : Ext.Msg.OK
                    ,icon    : Ext.Msg.WARNING
                    /*,fn      : function()
                    {
                        if(json.retryWS)
                        {
                            var paramsWS =
                            {
                                'panel1.pv_nmpoliza'  : inputNmpoliza
                                ,'panel1.pv_ntramite' : inputNtramite
                                ,'panel2.pv_cdramo'   : inputCdramo
                                ,'panel2.pv_cdunieco' : inputCdunieco
                                ,'panel2.pv_estado'   : inputEstado
                                ,'panel2.pv_nmpoliza' : inputNmpoliza
                                ,'panel2.pv_cdtipsit' : inputCdtipsit
                                ,'nmpoliza'           : json.nmpoliza
                                ,'nmsuplem'           : json.nmsuplem                                                                       
                                ,'cdIdeper'           : json.cdIdeper
                            };
                            reintentarWSAuto(me.up().up(), paramsWS);
                        }
                    }*/
                });
            }
        }
        ,failure:function()
        {
            panelpri.setLoading(false);
            errorComunicacion();
        }
    });
    debug('<_p29_emitirFinal');
}
////// funciones //////
</script>
</head>
<body><div id="_p29_divpri" style="height:1000px;"></div></body>
</html>