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
var _p28_urlCargarSumaAseguradaRamo5       = '<s:url namespace="/emision" action="cargarSumaAseguradaRamo5"       />';
var _p28_urlCargar                         = '<s:url namespace="/emision" action="cargarCotizacion"               />';
var _p28_urlDatosComplementarios           = '<s:url namespace="/"        action="datosComplementarios"           />';
var _p28_urlCargarParametros               = '<s:url namespace="/emision" action="obtenerParametrosCotizacion"    />';
////// urls //////

////// variables //////
var _p28_smap1 = <s:property value="%{convertToJSON('smap1')}" escapeHtml="false" />;
debug('_p28_smap1:',_p28_smap1);

var _p28_formFields   = [ <s:property value="imap.formFields"   /> ];
var _p28_panel2Items  = [ <s:property value="imap.panel2Items"  /> ];
var _p28_panel3Items  = [ <s:property value="imap.panel3Items"  /> ];
var _p28_panel4Items  = [ <s:property value="imap.panel4Items"  /> ];
var _p28_panel5Items  = [ <s:property value="imap.panel5Items"  /> ];
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
    
    Ext.define('_p28_formModel',
    {
        extend  : 'Ext.data.Model'
        ,fields : _p28_formFields
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
                                ,listeners  :
                                {
                                    change : _p28_nmpolizaChange
                                }
                            }
                            ,{
                                xtype    : 'button'
                                ,itemId  : '_p28_botonCargar'
                                ,text    : 'BUSCAR'
                                ,style   : 'margin-left:335px;'
                                ,icon    : '${ctx}/resources/fam3icons/icons/zoom.png'
                                ,handler : _p28_cargar
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
	    var agente    = _fieldByLabel('AGENTE');
	    var clave     = _fieldByName('parametros.pv_otvalor06');
	    var marca     = _fieldByName('parametros.pv_otvalor07');
        var submarca  = _fieldByName('parametros.pv_otvalor08');
        var modelo    = _fieldByName('parametros.pv_otvalor09');
        var version   = _fieldByName('parametros.pv_otvalor10');
        var tipoValor = _fieldByLabel('TIPO VALOR');
        var sumaAsegu = _fieldLikeLabel('VALOR VEH');
	    var combcl    = _fieldLikeLabel('CLIENTE NUEVO');
	    
	    //agente
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
	    
	    //version
	    version.anidado = true;
	    version.heredar = function()
        {
            version.getStore().load(
            {
                params :
                {
                    'params.submarca' : submarca.getValue()
                    ,'params.modelo'  : modelo.getValue()
                }
            });
        };
	    
	    submarca.on(
	    {
	        select : function(){ version.reset(); }
	    });
	    
	    modelo.on(
	    {
	        select : function()
	        {
	            version.getStore().load(
	            {
	                params :
	                {
	                    'params.submarca' : submarca.getValue()
	                    ,'params.modelo'  : modelo.getValue()
	                }
	            });
	        }
	    });
	    //version
	    
	    //clave
	    clave.on(
	    {
	        select : function(){ _p28_herenciaDescendiente(clave,marca,submarca,modelo,version); }
	    });
	    
	    modelo.on(
	    {
	        select : function() { _p28_herenciaAscendente(clave,marca,submarca,modelo,version); }
	    });
	    
	    version.on(
        {
            select : function() { _p28_herenciaAscendente(clave,marca,submarca,modelo,version); }
        });
	    //clave
	    
	    //tipovalor
	    tipoValor.on(
	    {
	        select : function(){ _p28_cargarRangoValorRamo5(); }
	    });
	    //tipovalor
	    
	    //sumaAsegurada
	    sumaAsegu.on(
	    {
	        change : function(){ _p28_limitarCoberturasDependientesSumasegRamo5(); }
	    });
	    //sumaAsegurada
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
        valido = _fieldByName('nmpoliza').sucio==false;
        if(!valido)
        {
            _fieldByName('nmpoliza').semaforo = true;
            _fieldByName('nmpoliza').setValue('');
            _fieldByName('nmpoliza').semaforo = false;
            valido = true;
        }
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
                    
                    _fieldByName('nmpoliza').semaforo=true;
                    _fieldByName('nmpoliza').setValue(json.smap1.nmpoliza);
                    _fieldByName('nmpoliza').semaforo=false;
                    
                    Ext.define('_p28_modeloTarifa',
                    {
                        extend  : 'Ext.data.Model'
                        ,fields : Ext.decode(json.smap1.fields)
                    });
                    
                    var gridTarifas=Ext.create('Ext.grid.Panel',
                    {
                        itemId            : '_p28_gridTarifas'
                        ,title            : 'Resultados'
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
                            {
                                itemId   : '_p28_botonEditar'
                                ,text    : 'Editar'
                                ,icon    : '${ctx}/resources/fam3icons/icons/pencil.png'
                                ,handler : _p28_editar
                            }
                            ,{
                                itemId   : '_p28_botonNueva'
                                ,text    : 'Nueva'
                                ,icon    : '${ctx}/resources/fam3icons/icons/arrow_refresh.png'
                                ,handler : _p28_nueva
                            }
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
    _fieldById('_p28_botonCargar').setDisabled(b);
    
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
    _fieldByName('nmpoliza').semaforo=true;
    _fieldById('_p28_form').getForm().reset();
    _fieldByName('nmpoliza').semaforo=false;
    _fieldByName('nmpoliza').focus();
    
    _p28_calculaVigencia();
    _fieldLikeLabel('CLIENTE NUEVO').setValue('S');
    
    debug('<_p28_limpiar');
}

function _p28_calculaVigencia(comp,val)
{
    debug('>_p28_calculaVigencia');
    var feini = _fieldByName('feini');
    var fefin = _fieldByName('fefin');
    
    if(feini.isValid()&&fefin.isValid())
    {
        var milisDif = Ext.Date.getElapsed(feini.getValue(),fefin.getValue());
        var diasDif  = (milisDif/1000/60/60/24).toFixed(0);
        debug('milisDif:',milisDif,'diasDif:',diasDif);
        _fieldByLabel('VIGENCIA').setValue(diasDif);
    }
    debug('<_p28_calculaVigencia');
}

function _p28_editar()
{
    debug('>_p28_editar');
    var panelPri    = _fieldById('_p28_panelpri');
    var gridTarifas = _fieldById('_p28_gridTarifas');
    
    panelPri.remove(gridTarifas);
    panelPri.doLayout();
    
    _p28_bloquear(false);
    debug('<_p28_editar');
}

function _p28_nueva()
{
    debug('>_p28_nueva');
    _p28_editar();
    _p28_limpiar();
    debug('<_p28_nueva');
}

function _p28_herenciaDescendiente(clave,marca,submarca,modelo,version)
{
    debug('>_p28_herenciaDescendiente');
    var record = clave.findRecord('key',clave.getValue());
    debug('record:',record);
    var splitted=record.get('value').split(' - ');
    debug('splitted:',splitted);
    var clavev    = splitted[0];
    var marcav    = splitted[1];
    var submarcav = splitted[2];
    var modelov   = splitted[3];
    var versionv  = splitted[4];
    
    marca.setValue(marca.findRecord('value',marcav));
    submarca.heredar(true,function()
    {
        submarca.setValue(submarca.findRecord('value',submarcav));
        modelo.heredar(true,function()
        {
            modelo.setValue(modelo.findRecord('value',modelov));
            version.getStore().load(
            {
                params :
                {
                    'params.submarca' : submarca.getValue()
                    ,'params.modelo'  : modelo.getValue()
                }
                ,callback : function()
                {
                    version.setValue(version.findRecord('value',versionv));
                    _p28_cargarSumaAseguradaRamo5(clave,modelo);
                }
            });
        });
    });
    
    debug('<_p28_herenciaDescendiente');
}

function _p28_herenciaAscendente(clave,marca,submarca,modelo,version)
{
    debug('>_p28_herenciaAscendente');
    var versionval = version.getValue();
    
    if(!Ext.isEmpty(versionval))
    {
        var versiondes = version.findRecord('key',versionval).get('value');
        clave.getStore().load(
        {
            params :
            {
                'params.cadena' : versiondes
            }
            ,callback : function()
            {
                var valor = versionval
                            +' - '
                            +marca.findRecord('key',marca.getValue()).get('value')
                            +' - '
                            +submarca.findRecord('key',submarca.getValue()).get('value')
                            +' - '
                            +modelo.findRecord('key',modelo.getValue()).get('value')
                            +' - '
                            +version.findRecord('key',versionval).get('value');
                debug('>valor:',valor);
                clave.setValue(clave.findRecord('value',valor));
                _p28_cargarSumaAseguradaRamo5(clave,modelo);
            }
        });
    }
    
    debug('<_p28_herenciaAscendente');
}

function _p28_cargarSumaAseguradaRamo5(clave,modelo)
{
    debug('>_p28_cargarSumaAseguradaRamo5');
    var form=_fieldById('_p28_form');
    form.setLoading(true);
    Ext.Ajax.request(
    {
        url      : _p28_urlCargarSumaAseguradaRamo5
        ,params  :
        {
            'smap1.cdtipsit'  : _p28_smap1.cdtipsit
            ,'smap1.clave'    : clave.getValue()
            ,'smap1.modelo'   : modelo.getValue()
            ,'smap1.cdsisrol' : _p28_smap1.cdsisrol
        }
        ,success : function(response)
        {
            form.setLoading(false);
            var json = Ext.decode(response.responseText);
            debug('### cargar suma asegurada:',json);
            if(json.exito)
            {
                var sumaseg = _fieldByName('parametros.pv_otvalor13');
                sumaseg.setValue(json.smap1.sumaseg);
                sumaseg.valorCargado=json.smap1.sumaseg;
                _p28_cargarRangoValorRamo5();
            }
            else
            {
                mensajeError(json.respuesta);
            }           
        }
        ,failure : function()
        {
            form.setLoading(false);
            errorComunicacion();
        }
    });
    debug('<_p28_cargarSumaAseguradaRamo5');
}

function _p28_cargar(boton)
{
    var nmpoliza = _fieldByName('nmpoliza').getValue();
    var valido   = !Ext.isEmpty(nmpoliza);
    if(!valido)
    {
        mensajeWarning('Introduce un n&uacute;mero v&aacute;lido');
    }
    
    if(valido)
    {
        var panelpri = _fieldById('_p28_panelpri');
        panelpri.setLoading(true);
        Ext.Ajax.request(
        {
            url      : _p28_urlCargar
            ,params  :
            {
                'smap1.nmpoliza'  : nmpoliza
                ,'smap1.cdramo'   : _p28_smap1.cdramo
                ,'smap1.cdunieco' : _p28_smap1.cdunieco
                ,'smap1.cdtipsit' : _p28_smap1.cdtipsit
            }
            ,success : function(response)
            {
                panelpri.setLoading(false);
                var json=Ext.decode(response.responseText);
                debug('### cargar cotizacion:',json);
                if(json.success)
                {
                    if(!json.smap1.CDUNIECO)
                    {
                        _p28_limpiar();
                        _fieldByName('nmpoliza').semaforo=true;
                        _fieldByName('nmpoliza').setValue(nmpoliza);
                        _fieldByName('nmpoliza').semaforo=false;
                        var primerInciso = new _p28_formModel(json.slist1[0]);
                        if(_p28_smap1.cdramo=='5')
                        {
                            primerInciso.set('parametros.pv_otvalor14','S');
                        }
                        debug('primerInciso:',primerInciso);
                        //leer elementos anidados
                        var form      = _fieldById('_p28_form');
                        var formItems = Ext.ComponentQuery.query('[fieldLabel]',form);
                        debug('formItems:' , formItems);
                        var numBlurs  = 0;
                        for(var i=0;i<formItems.length;i++)
                        {
                            var item=formItems[i];
                            if(item.anidado == true)
                            {
                                var numBlursSeguidos = 1;
                                debug('contando blur:',item);
                                for(var j=i+1;j<formItems.length;j++)
                                {
                                    if(formItems[j].anidado == true)
                                    {
                                        numBlursSeguidos=numBlursSeguidos+1;
                                    }
                                }
                                if(numBlursSeguidos>numBlurs)
                                {
                                    numBlurs=numBlursSeguidos;
                                }
                            }
                        }
                        debug('numBlurs:',numBlurs);
                        var i=0;
                        var renderiza=function()
                        {
                            debug('renderiza',i);
                            form.loadRecord(primerInciso);
                            if(i<numBlurs)
                            {
                                i=i+1;
                                for(var j=0;j<formItems.length;j++)
                                {
                                    var iItem  = formItems[j]; 
                                    var iItem2 = formItems[j+1];
                                    debug('iItem2:',iItem2,'store:',iItem2?iItem2.store:'iItem2 no');
                                    if(iItem2&&iItem2.anidado==true)
                                    {
                                        debug('tiene blur y lo hacemos heredar',formItems[j]);
                                        iItem2.heredar(true);
                                    }
                                }
                                setTimeout(renderiza,1000);
                            }
                            else
                            {
                                panelpri.setLoading(false);
                                if(_p28_smap1.cdramo=='5')
                                {
                                    var clave    = _fieldByName('parametros.pv_otvalor06');
                                    var marca    = _fieldByName('parametros.pv_otvalor07');
                                    var submarca = _fieldByName('parametros.pv_otvalor08');
                                    var modelo   = _fieldByName('parametros.pv_otvalor09');
                                    var version  = _fieldByName('parametros.pv_otvalor10');
                                    
                                    _p28_herenciaAscendente(clave,marca,submarca,modelo,version);
                                    
                                    if(_p28_smap1.cdsisrol=='SUSCRIAUTO')
                                    {
                                        var agente = _fieldByName('parametros.pv_otvalor01');
                                        agente.getStore().load(
                                        {
                                            params :
                                            {
                                                'params.agente' : primerInciso.get('parametros.pv_otvalor01')
                                            }
                                            ,callback : function()
                                            {
                                                agente.setValue(agente.findRecord('key',primerInciso.get('parametros.pv_otvalor01')));
                                            }
                                        });
                                    }
                                }
                            }
                        };
                        panelpri.setLoading(true);
                        renderiza();
                    }
                    else
                    {
                        Ext.create('Ext.form.Panel').submit(
                        {
                            url             : _p28_urlDatosComplementarios
                            ,standardSubmit : true
                            ,params         :
                            {
                                cdunieco         : json.smap1.CDUNIECO
                                ,cdramo          : json.smap1.cdramo
                                ,estado          : 'W'
                                ,nmpoliza        : json.smap1.nmpoliza
                                ,'map1.ntramite' : json.smap1.NTRAMITE
                                ,cdtipsit        : json.smap1.cdtipsit
                            }
                        });
                    }
                }
                else
                {
                    mensajeError(json.error);
                }
            }
            ,failure : function()
            {
                panelpri.setLoading(false);
                errorComunicacion();
            }
        });
    }
}

function _p28_cargarRangoValorRamo5()
{
    debug('>_p28_cargarRangoValorRamo5');
    var tipovalor = _fieldByLabel('TIPO VALOR');
    var valor     = _fieldLikeLabel('VALOR VEH');
    
    var tipovalorval = tipovalor.getValue();
    var valorval     = valor.getValue();
    var valorCargado = valor.valorCargado;
    
    var valido = !Ext.isEmpty(tipovalorval)&&!Ext.isEmpty(valorval)&&!Ext.isEmpty(valorCargado);
    
    if(valido)
    {
        var panelpri = _fieldById('_p28_panelpri');
        panelpri.setLoading(true);
        Ext.Ajax.request(
        {
            url      : _p28_urlCargarParametros
            ,params  :
            {
                'smap1.parametro' : 'RANGO_VALOR'
                ,'smap1.cdramo'   : _p28_smap1.cdramo
                ,'smap1.cdtipsit' : _p28_smap1.cdtipsit
                ,'smap1.clave4'   : tipovalorval
                ,'smap1.clave5'   : _p28_smap1.cdsisrol
            }
            ,success : function(response)
            {
                panelpri.setLoading(false);
                var json = Ext.decode(response.responseText);
                debug('### obtener rango valor:',json);
                if(json.exito)
                {
                    valormin = valorCargado*(1+(json.smap1.P1VALOR-0));
                    valormax = valorCargado*(1+(json.smap1.P2VALOR-0));
                    valor.setMinValue(valormin);
                    valor.setMaxValue(valormax);
                    valor.isValid();
                    debug('valor:',valorCargado);
                    debug('valormin:',valormin);
                    debug('valormax:',valormax);
                }
                else
                {
                    mensajeError(json.respuesta);
                }
            } 
            ,failure : function()
            {
                panelpri.setLoading(false);
                errorComunicacion();
            }
        });
    }
    debug('<_p28_cargarRangoValorRamo5');
}

function _p28_limitarCoberturasDependientesSumasegRamo5()
{
    var sumaAsegu = _fieldLikeLabel('VALOR VEH');
    var suma      = sumaAsegu.getValue();
    
    if(!Ext.isEmpty(suma))
    {
        Ext.Ajax.request(
        {
            url      : _p28_urlCargarParametros
            ,params  :
            {
                'smap1.parametro' : 'RANGO_COBERTURAS_DEPENDIENTES'
                ,'smap1.cdramo'   : _p28_smap1.cdramo
                ,'smap1.cdtipsit' : _p28_smap1.cdtipsit
                ,'smap1.clave4'   : _p28_smap1.cdsisrol
            }
            ,success : function(response)
            {
                var json=Ext.decode(response.responseText);
                debug('### obtener rangos coberturas dependientes:',json);
                if(json.exito)
                {
                    var equipoEspecial = _fieldByLabel('SUMA ASEGURADA EQUIPO ESPECIAL');
                    var min            = suma*(1+(json.smap1.P1VALOR-0));
                    var max            = suma*(1+(json.smap1.P2VALOR-0));
                    debug('min:',min,'max:',max);
                    equipoEspecial.setMinValue(min);
                    equipoEspecial.setMaxValue(max);
                    equipoEspecial.isValid();
                    
                    var adaptaciones = _fieldByLabel('SUMA ASEGURADA ADAPTACIONES Y CONVERSIONES');
                    min              = suma*(1+(json.smap1.P3VALOR-0));
                    max              = suma*(1+(json.smap1.P4VALOR-0));
                    debug('min:',min,'max:',max);
                    adaptaciones.setMinValue(min);
                    adaptaciones.setMaxValue(max);
                    adaptaciones.isValid();
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

function _p28_nmpolizaChange(me)
{
    var sem = me.semaforo;
    if(Ext.isEmpty(sem)||sem==false)
    {
        me.sucio = true;
    }
    else
    {
        me.sucio = false;
    }
}
////// funciones //////
</script>
</head>
<body><div id="_p28_divpri" style="height:1000px;"></div></body>
</html>