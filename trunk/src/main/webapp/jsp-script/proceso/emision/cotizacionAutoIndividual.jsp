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

////// urls //////
var _p28_urlCargarCduniecoAgenteAuto       = '<s:url namespace="/emision" action="cargarCduniecoAgenteAuto"       />';
var _p28_urlCotizar                        = '<s:url namespace="/emision" action="cotizar"                        />';
var _p28_urlRecuperarCliente               = '<s:url namespace="/"        action="buscarPersonasRepetidas"        />';
var _p28_urlCargarRetroactividadSuplemento = '<s:url namespace="/emision" action="cargarRetroactividadSuplemento" />';
////// urls //////

////// variables //////
var _p28_smap1 = <s:property value="%{convertToJSON('smap1')}" escapeHtml="false" />;
debug('_p28_smap1:',_p28_smap1);

var _p28_panel1Fields = [ <s:property value="imap.panel1Fields" /> ];
var _p28_panel2Fields = [ <s:property value="imap.panel2Fields" /> ];
var _p28_panel2Items  = [ <s:property value="imap.panel2Items"  /> ];
var _p28_panel3Fields = [ <s:property value="imap.panel3Fields" /> ];
var _p28_panel3Items  = [ <s:property value="imap.panel3Items"  /> ];
var _p28_panel4Fields = [ <s:property value="imap.panel4Fields" /> ];
var _p28_panel4Items  = [ <s:property value="imap.panel4Items"  /> ];
var _p28_panel5Fields = [ <s:property value="imap.panel5Fields" /> ];
var _p28_panel5Items  = [ <s:property value="imap.panel5Items"  /> ];
var _p28_panel6Fields = [ <s:property value="imap.panel6Fields" /> ];
var _p28_panel6Items  = [ <s:property value="imap.panel6Items"  /> ];

var _p28_recordClienteRecuperado = null;
////// variables //////

Ext.onReady(function()
{
	////// modelos //////
	Ext.define('_p28_modeloRecuperado',
    {
        extend  : 'Ext.data.Model'
        ,fields :
        [
            'NOMBRECLI'
            ,'DIRECCIONCLI'
        ]
    });
	////// modelos //////
	
	////// stores //////
	////// stores //////
	
	////// componentes //////
	////// componentes //////
	
	////// contenido //////
	Ext.create('Ext.panel.Panel',
	{
	    itemId    : '_p28_panelpri'
	    ,renderTo : '_p28_divpri'
	    ,defaults : { style : 'margin : 5px;' }
	    ,items    :
	    [
	        Ext.create('Ext.form.Panel',
	        {
	            itemId    : '_p28_form'
	            ,border   : 0
	            ,defaults : { style : 'margin : 5px;' }
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
	                    ,items :
	                    [
	                        {
                                xtype       : 'numberfield'
                                ,itemId     : '_p28_nmpolizaItem'
                                ,fieldLabel : 'FOLIO'
                                ,name       : 'nmpoliza'
                                ,style      : 'margin : 5px;'
                            }
                            ,{
                                xtype       : 'button'
                                ,text       : 'BUSCAR'
                                ,style      : 'margin-left:335px;'
                                ,icon       : '${ctx}/resources/fam3icons/icons/zoom.png'
                            }
                            <s:property value='","+imap.panel1Items' />
                            ,{
                                xtype  : 'fieldset'
                                ,width : 435
                                ,title : '<span style="font:bold 14px Calibri;">VEH&Iacute;CULO</span>'
                                ,items : _p28_panel2Items
                            }
                            ,{
                                xtype  : 'fieldset'
                                ,width : 435
                                ,title : '<span style="font:bold 14px Calibri;">CLIENTE</span>'
                                ,items : _p28_panel3Items
                            }
                            ,{
                                xtype       : 'datefield'
                                ,itemId     : '_p28_feiniItem'
                                ,name       : 'feini'
                                ,fieldLabel : 'INICIO DE VIGENCIA'
                                ,value      : new Date()
                            }
                            ,{
                                xtype       : 'datefield'
                                ,itemId     : '_p28_fefinItem'
                                ,name       : 'fefin'
                                ,fieldLabel : 'FIN DE VIGENCIA'
                                ,value      : Ext.Date.add(new Date(),Ext.Date.YEAR,1)
                                ,minValue   : Ext.Date.add(new Date(),Ext.Date.DAY,1)
                            }
	                    ]
	                }
	                ,{
	                    xtype  : 'fieldset'
	                    ,title : '<span style="font:bold 14px Calibri;">COBERTURAS</span>'
	                    ,items : _p28_panel4Items
	                }
	            ]
	        })
	        ,Ext.create('Ext.panel.Panel',
	        {
	            itemId       : '_p28_botonera'
	            ,buttonAlign : 'center'
	            ,border      : 0
	            ,buttons     :
	            [
	                {
	                    itemId   : '_p28_cotizarButton'
	                    ,text    : 'Cotizar'
	                    ,icon    : '${ctx}/resources/fam3icons/icons/calculator.png'
	                    ,handler : _p28_cotizar
	                }
	                ,{
                        itemId   : '_p28_limpiarButton'
                        ,text    : 'Limpiar'
                        ,icon    : '${ctx}/resources/fam3icons/icons/arrow_refresh.png'
                        ,handler : _p28_limpiar
                    }
	            ]
	        })
	    ]
	});
	////// contenido //////
	
	////// custom //////
	
	//fechas
	_fieldByName('feini').on(
	{
        change : function(me,val)
	    {
	        debug('val:',val);
	        var fefin = _fieldByName('fefin');
	        fefin.setMinValue(Ext.Date.add(val,Ext.Date.DAY,1));
	        fefin.isValid();
	    }
	});
	
	_fieldByName('feini').on(
    {
        change : _p28_calculaVigencia
    });
    
    _fieldByName('fefin').on(
    {
        change : _p28_calculaVigencia
    });
    
    _p28_calculaVigencia();
    //fechas
	
	//ramo 5
	if(_p28_smap1.cdramo+'x'=='5x')
	{
	    //agente
	    var agente = _fieldByLabel('AGENTE');
	    
	    if(_p28_smap1.cdsisrol=='EJECUTIVOCUENTA')
	    {
	        agente.setValue(_p28_smap1.cdagente);
	        agente.setReadOnly(true);
	        _p28_ramo5AgenteSelect(agente,_p28_smap1.cdagente);
	    }
	    else if(_p28_smap1.cdsisrol=='PROMOTORAUTO'
            ||_p28_smap1.cdsisrol=='SUSCRIAUTO')
        {
            agente.on(
            {
                'select' : _p28_ramo5AgenteSelect
            });
        }
	    //agente
	    
	    //cliente nuevo
	    var combcl = _fieldLikeLabel('CLIENTE NUEVO');
	    
	    combcl.on(
	    {
	        change : _p28_ramo5ClienteChange
        });
	    
	    combcl.getStore().on('load',function()
        {
            debug('combo cliente nuevo store load');
            combcl.setValue('S');
        });
	    //cliente nuevo
	}
	//ramo 5
	
	////// custom //////
	
	////// loaders //////
	////// loaders //////
});

////// funciones //////
function _p28_cotizar()
{
    debug('>_p28_cotizar');
    
    var panelpri = _fieldById('_p28_panelpri');
    var form     = _fieldById('_p28_form');
    var valido   = form.isValid();
    if(!valido)
    {
        datosIncompletos();
    }
    
    if(valido)
    {
        var json=
        {
            slist1 :
            [
                form.getValues()
            ]
            ,smap1 : _p28_smap1 
        };
        panelpri.setLoading(true);
        Ext.Ajax.request(
        {
            url       : _p28_urlCotizar
            ,jsonData : json
            ,success  : function(response)
            {
                _p28_bloquear(true);
                panelpri.setLoading(false);
                json=Ext.decode(response.responseText);
                if(json.success==true)
                {
                    debug(Ext.decode(json.smap1.fields));
                    debug(Ext.decode(json.smap1.columnas));
                    debug(json.slist2);
                    
                    _fieldByName('nmpoliza').setValue(json.smap1.nmpoliza);
                    
                    Ext.define('_p28_modeloTarifa',
                    {
                        extend  : 'Ext.data.Model'
                        ,fields : Ext.decode(json.smap1.fields)
                    });
                    
                    var gridTarifas=Ext.create('Ext.grid.Panel',
                    {
                        title             : 'Resultados'
                        ,store            : Ext.create('Ext.data.Store',
                        {
                            model : '_p28_modeloTarifa'
                            ,data : json.slist2
                        })
                        ,columns          : Ext.decode(json.smap1.columnas)
                        ,selType          : 'cellmodel'
                        ,minHeight        : 100
                        ,enableColumnMove : false
                        ,buttonAlign      : 'center'
                        ,buttons          :
                        [
                            /*
                            new _0_BotComprar()
                            ,new _0_BotDetalles()
                            ,new _0_BotCoberturas()
                            ,new _0_BotEditar()
                            ,new _0_BotClonar()
                            ,new _0_BotNueva()
                            ,new _0_BotMail()
                            ,new _0_BotImprimir()
                            */
                        ]
                        ,listeners        :
                        {
                            //select : _0_tarifaSelect
                        }
                    });
                    
                    /*
                    if(_0_smap1.cdramo+'x'=='6x')
                    {
                        Ext.getCmp('_0_botDetallesId').setDisabled(true);
                        Ext.getCmp('_0_botCoberturasId').setDisabled(true);
                    }
                    */
                    
                    var panelPri = _fieldById('_p28_panelpri');
                    
                    panelPri.add(gridTarifas);
                    panelPri.doLayout();
                    
                    try {
                       gridTarifas.down('button[disabled=false]').focus(false, 1000);
                    } catch(e) {
                        debug(e);
                    }
                }
                else
                {
                    _p28_bloquear(false);
                    mensajeError('Error al cotizar:<br/>'+json.error);
                }
            }
            ,failure  : function(response)
            {
                panelpri.setLoading(false);
                errorComunicacion();
            }
        });
    }
    
    debug('<_p28_cotizar');
}

function _p28_bloquear(b)
{
    debug('>_p28_bloquear:',b);
    var comps=Ext.ComponentQuery.query('[fieldLabel]',_fieldById('_p28_form'));
    
    for(var i=0;i<comps.length;i++)
    {
        comps[i].setReadOnly(b);
    }
    
    _fieldById('_p28_botonera').setDisabled(b);
    
    if(b)
    {
        /*
        try {
           _0_gridTarifas.down('button[disabled=false]').focus(false, 1000);
        } catch(e) {
            debug(e);
        }
        */
    }
    else
    {
        try {
            _fieldByName('nmpoliza').focus();
        } catch(e) {
            debug(e);
        }
    }
    debug('<_p28_bloquear');
}

function _p28_ramo5AgenteSelect(comp,records)
{
    cdagente = typeof records == 'string' ? records : records[0].get('key');
    debug('>_p28_ramo5AgenteSelect cdagente:',cdagente);
    Ext.Ajax.request(
    {
        url     : _p28_urlCargarCduniecoAgenteAuto
        ,params :
        {
            'smap1.cdagente' : cdagente
        }
        ,success : function(response)
        {
            var json=Ext.decode(response.responseText);
            debug('#### obtener cdunieco agente response:',json);
            if(json.exito)
            {
                _p28_smap1.cdunieco=json.smap1.cdunieco;
                debug('_p28_smap1:',_p28_smap1);
                Ext.Ajax.request(
                {
                    url     : _p28_urlCargarRetroactividadSuplemento
                    ,params :
                    {
                        'smap1.cdunieco'  : _p28_smap1.cdunieco
                        ,'smap1.cdramo'   : _p28_smap1.cdramo
                        ,'smap1.cdtipsup' : 1
                        ,'smap1.cdusuari' : _p28_smap1.cdusuari
                        ,'smap1.cdtipsit' : _p28_smap1.cdtipsit
                    }
                    ,success : function(response)
                    {
                        var json = Ext.decode(response.responseText);
                        debug('### obtener retroactividad:',json);
                        if(json.exito)
                        {
                            var feini = _fieldByName('feini');
                            var fefin = _fieldByName('fefin');
                            
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
            else
            {
                mensajeError(json.respuesta);
            }
        }
        ,failure : errorComunicacion
    });
    debug('<_p28_ramo5AgenteSelect');
}

function _p28_ramo5ClienteChange(combcl)
{
    debug('>_p28_ramo5ClienteChange value:',combcl.getValue());
    
    var nombre  = _fieldLikeLabel('NOMBRE CLIENTE');
    var tipoper = _fieldByLabel('TIPO PERSONA');
    var codpos  = _fieldLikeLabel('CP CIRCULACI');
    
    //cliente nuevo
    if(combcl.getValue()=='S')
    {
        nombre.reset();
        tipoper.reset();
        codpos.reset();
        
        nombre.setReadOnly(false);
        tipoper.setReadOnly(false);
        codpos.setReadOnly(false);
        
        _p28_recordClienteRecuperado=null;
    }
    //recuperar cliente
    else if(combcl.getValue()=='N')
    {
        nombre.reset();
        tipoper.reset();
        codpos.reset();
        
        nombre.setReadOnly(true);
        tipoper.setReadOnly(true);
        codpos.setReadOnly(true);
        
        var ventana=Ext.create('Ext.window.Window',
        {
            title      : 'Recuperar cliente'
            ,modal     : true
            ,width     : 600
            ,height    : 400
            ,items     :
            [
                {
                    layout    : 'hbox'
                    ,defaults : { style : 'margin : 5px;' }
                    ,items    :
                    [
                        {
                            xtype       : 'textfield'
                            ,name       : '_p28_recuperaRfc'
                            ,fieldLabel : 'RFC'
                            ,minLength  : 9
                            ,maxLength  : 13
                        }
                        ,{
                            xtype    : 'button'
                            ,text    : 'Buscar'
                            ,icon    : '${ctx}/resources/fam3icons/icons/zoom.png'
                            ,handler : function(button)
                            {
                                debug('recuperar cliente buscar');
                                var rfc=_fieldByName('_p28_recuperaRfc').getValue();
                                var valido=true;
                                if(valido)
                                {
                                    valido = !Ext.isEmpty(rfc)
                                             &&rfc.length>8
                                             &&rfc.length<14;
                                    if(!valido)
                                    {
                                        mensajeWarning('Introduza un RFC v&aacute;lido');
                                    }
                                }
                                
                                if(valido)
                                {
                                    button.up('window').down('grid').getStore().load(
                                    {
                                        params :
                                        {
                                            'map1.pv_rfc_i'       : rfc
                                            ,'map1.cdtipsit'      : _p28_smap1.cdtipsit
                                            ,'map1.pv_cdtipsit_i' : _p28_smap1.cdtipsit
                                            ,'map1.pv_cdunieco_i' : _p28_smap1.cdunieco
                                            ,'map1.pv_cdramo_i'   : _p28_smap1.cdramo
                                            ,'map1.pv_estado_i'   : 'W'
                                            ,'map1.pv_nmpoliza_i' : _fieldByName('nmpoliza').getValue()
                                        }
                                    });
                                }
                            }
                        }
                    ]
                }
                ,Ext.create('Ext.grid.Panel',
                {
                    title    : 'Resultados'
                    ,columns :
                    [
                        {
                            xtype    : 'actioncolumn'
                            ,width   : 30
                            ,icon    : '${ctx}/resources/fam3icons/icons/accept.png'
                            ,handler : function(view,row,col,item,e,record)
                            {
                                debug('recuperar cliente handler record:',record);
                                _p28_recordClienteRecuperado=record;
                                nombre.setValue(record.raw.NOMBRECLI);
                                tipoper.setValue(record.raw.TIPOPERSONA);
                                codpos.setValue(record.raw.CODPOSTAL);
                                ventana.destroy();
                            }
                        }
                        ,{
                            text       : 'Nombre'
                            ,dataIndex : 'NOMBRECLI'
                            ,width     : 200
                        }
                        ,{
                            text       : 'Direcci&oacute;n'
                            ,dataIndex : 'DIRECCIONCLI'
                            ,flex      : 1
                        }
                    ]
                    ,store : Ext.create('Ext.data.Store',
                    {
                        model     : '_p28_modeloRecuperado'
                        ,autoLoad : false
                        ,proxy    :
                        {
                            type    : 'ajax'
                            ,url    : _p28_urlRecuperarCliente
                            ,reader :
                            {
                                type  : 'json'
                                ,root : 'slist1'
                            }
                        }
                    })
                })
            ]
            ,listeners :
            {
                close : function()
                {
                    combcl.setValue('S');
                }
            }
        }).show();
        centrarVentanaInterna(ventana);
    }
    debug('<_p28_ramo5ClienteChange');
}

function _p28_limpiar()
{
    debug('>_p28_limpiar');
    _fieldById('_p28_form').getForm().reset();
    _fieldByName('nmpoliza').focus();
    
    _p28_calculaVigencia();
    _fieldLikeLabel('CLIENTE NUEVO').setValue('S');
    
    debug('<_p28_limpiar');
}

function _p28_calculaVigencia(comp,val)
{
    var feini = _fieldByName('feini');
    var fefin = _fieldByName('fefin');
    
    if(feini.isValid()&&fefin.isValid())
    {
        var milisDif = Ext.Date.getElapsed(feini.getValue(),fefin.getValue());
        var diasDif  = (milisDif/1000/60/60/24).toFixed(0);
        debug('milisDif:',milisDif,'diasDif:',diasDif);
        _fieldByLabel('VIGENCIA').setValue(diasDif);
    }
}
////// funciones //////
</script>
</head>
<body><div id="_p28_divpri" style="height:1000px;"></div></body>
</html>