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
var _p28_urlCargarCduniecoAgenteAuto       = '<s:url namespace="/emision"         action="cargarCduniecoAgenteAuto"       />';
var _p28_urlCotizar                        = '<s:url namespace="/emision"         action="cotizar"                        />';
var _p28_urlRecuperarCliente               = '<s:url namespace="/"                action="buscarPersonasRepetidas"        />';
var _p28_urlCargarRetroactividadSuplemento = '<s:url namespace="/emision"         action="cargarRetroactividadSuplemento" />';
var _p28_urlCargarSumaAseguradaRamo5       = '<s:url namespace="/emision"         action="cargarSumaAseguradaRamo5"       />';
var _p28_urlCargar                         = '<s:url namespace="/emision"         action="cargarCotizacion"               />';
var _p28_urlDatosComplementarios           = '<s:url namespace="/"                action="datosComplementarios"           />';
var _p28_urlCargarParametros               = '<s:url namespace="/emision"         action="obtenerParametrosCotizacion"    />';
var _p28_urlCoberturas                     = '<s:url namespace="/flujocotizacion" action="obtenerCoberturas4"             />';
////// urls //////

////// variables //////
var _p28_smap1 = <s:property value="%{convertToJSON('smap1')}" escapeHtml="false" />;
debug('_p28_smap1:',_p28_smap1);

var _p28_formFields   = [ <s:property value="imap.formFields"   /> ];
var _p28_panel2Items  = [ <s:property value="imap.panel2Items"  /> ];
var _p28_panel3Items  = [ <s:property value="imap.panel3Items"  /> ];
var _p28_panel4Items  = [ <s:property value="imap.panel4Items"  /> ];

var _p28_recordClienteRecuperado = null;
var _p28_storeCoberturas         = null;
var _p28_windowCoberturas        = null;
var _p28_selectedCdperpag        = null;
var _p28_selectedCdplan          = null;
var _p28_selectedDsplan          = null;
var _p28_selectedNmsituac        = null;
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
	_p28_storeCoberturas=Ext.create('Ext.data.Store',
    {
        autoLoad : false
        ,model   : 'RowCobertura'
        ,sorters    :
        [
            {
                sorterFn : function(o1,o2)
                {
                    debug('sorting:',o1,o2);
                    if (o1.get('orden') === o2.get('orden'))
                    {
                        return 0;
                    }
                    return o1.get('orden')-0 < o2.get('orden')-0 ? -1 : 1;
                }
            }
        ]
        ,proxy   :
        {
            type    : 'ajax'
            ,url    : _p28_urlCoberturas
            ,reader :
            {
                type  : 'json'
                ,root : 'listaCoberturas'
            }
        }
    });
	////// stores //////
	
	////// componentes //////
	_p28_windowCoberturas = new Ext.Window(
    {
        plain        : true
        ,width       : 500
        ,height      : 400
        ,modal       : true
        ,autoScroll  : true
        ,title       : 'Coberturas'
        ,layout      : 'fit'
        ,bodyStyle   : 'padding:5px;'
        ,buttonAlign : 'center'
        ,closeAction : 'hide'
        ,closable    : true
        ,items       :
        [
            Ext.create('Ext.grid.Panel',
            {
                itemId       : '_p28_gridCoberturas'
                ,title       : 'Sin plan'
                ,store       : _p28_storeCoberturas
                ,height      : 300
                ,selType     : 'cellmodel'
                ,buttonAlign : 'center'
                ,columns     :
                [
                    {
                        dataIndex : 'dsGarant'
                        ,text : 'Cobertura'
                        ,flex : 3
                    }
                    ,{
                        dataIndex : 'sumaAsegurada'
                        ,text : 'Suma asegurada'
                        ,flex : 1
                    }
                    ,{
                        dataIndex : 'deducible'
                        ,text : 'Deducible'
                        ,flex : 1
                    }
                ]
            })
        ] 
        ,buttons     :
        [
            {
                text     : 'Regresar'
                ,icon    : '${ctx}/resources/fam3icons/icons/arrow_left.png'
                ,handler : function()
                {
                    this.up().up().hide();
                }
            }
        ]
    });
	////// componentes //////
	
	////// contenido //////
	var _p28_formOcultoItems = [];
	<s:if test='%{getImap().get("panel5Items")!=null}'>
	    _p28_formOcultoItems.push(<s:property value="imap.panel5Items" />);
	</s:if>
	<s:if test='%{getImap().get("panel6Items")!=null}'>
        _p28_formOcultoItems.push(<s:property value="imap.panel6Items" />);
    </s:if>
	
	Ext.create('Ext.panel.Panel',
	{
	    itemId    : '_p28_panelpri'
	    ,renderTo : '_p28_divpri'
	    ,defaults : { style : 'margin : 5px;' }
	    ,items    :
	    [
	        Ext.create('Ext.form.Panel',
	        {
	            itemId      : '_p28_form'
	            ,border     : 0
	            ,defaults   : { style : 'margin : 5px;' }
	            ,formOculto : Ext.create('Ext.form.Panel',{ items : _p28_formOcultoItems })
	            ,layout     :
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
    //fechas
	
	//ramo 5
	if(_p28_smap1.cdramo+'x'=='5x')
	{
	    //fechas
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
        //copiar paneles a oculto
        var arr = Ext.ComponentQuery.query('#_p28_gridTarifas');
        if(arr.length>0)
        {
            var formDescuentoActual = _fieldById('_p28_formDescuento');
            var recordPaneles       = new _p28_formModel(formDescuentoActual.getValues());
            form.formOculto.loadRecord(recordPaneles);
            debug('form.formOculto.getValues():',form.formOculto.getValues());
        }
    
        var json=
        {
            slist1 :
            [
                form.getValues()
            ]
            ,smap1 : _p28_smap1 
        };
        var valuesFormOculto = form.formOculto.getValues();
        for(var att in valuesFormOculto)
        {
            json.slist1[0][att]=valuesFormOculto[att];
            debug('Agregado a cotizacion:',att,':',valuesFormOculto[att]);
        }
        debug('json a enviar para cotizar:',json);
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
                    
                    var itemsDescuento =
                    [
                        {
                            xtype  : 'displayfield'
                            ,value : '¿Desea usar su descuento de agente?'
                        }
                        ,{
                            xtype  : 'displayfield'
                            ,value : 'Si desea aplicar un DESCUENTO seleccione el porcentaje mayor a 0%'
                        }
                        ,{
                            xtype  : 'displayfield'
                            ,value : 'Si desea aplicar un RECARGO seleccione el porcentaje menor a 0%'
                        }
                    ];
                    
                    <s:if test='%{getImap().get("panel5Items")!=null}'>
                        itemsDescuento.push(<s:property value="imap.panel5Items" />);
                        for(var i=3;i<itemsDescuento.length;i++)
                        {
                            itemsDescuento[i].setMinValue(-100);
                            itemsDescuento[i].setMaxValue(100);
                        }
                    </s:if>
                    
                    var itemsComision =
                    [
                        {
                            xtype  : 'displayfield'
                            ,value : 'Indique el porcentaje de comisi&oacute;n que desea ceder'
                        }
                    ];
                    
                    <s:if test='%{getImap().get("panel6Items")!=null}'>
                        itemsComision.push(<s:property value="imap.panel6Items" />);
                        for(var i=1;i<itemsComision.length;i++)
                        {
                            itemsComision[i].setMaxValue(100);
                        }
                    </s:if>
                    
                    var arr = Ext.ComponentQuery.query('#_p28_gridTarifas');
                    if(arr.length>0)
                    {
                        panelpri.remove(arr[arr.length-1]);
                    }
                    
                    var _p28_formDescuento = Ext.create('Ext.form.Panel',
                    {
                        itemId       : '_p28_formDescuento'
                        ,defaults    : { style : 'margin:5px;' }
                        ,layout      :
                        {
                            type     : 'table'
                            ,columns : 2
                            ,tdAttrs : { valign : 'top' }
                        }
                        ,items       :
                        [
                            {
                                xtype  : 'fieldset'
                                ,title : '<span style="font:bold 14px Calibri;">DESCUENTO DE AGENTE</span>'
                                ,items : itemsDescuento
                            }
                            ,{
                                xtype  : 'fieldset'
                                ,title : '<span style="font:bold 14px Calibri;">CESI&Oacute;N DE COMISI&Oacute;N</span>'
                                ,items : itemsComision
                            }
                        ]
                        ,buttonAlign : 'right'
                        ,buttons     :
                        [
                            {
                                itemId   : '_p28_botonAplicarDescuento'
                                ,text    : 'Aplicar'
                                ,icon    : '${ctx}/resources/fam3icons/icons/accept.png'
                                ,handler : _p28_cotizar
                            }
                        ]
                    });
                    
                    _p28_formDescuento.loadRecord(new _p28_formModel(form.formOculto.getValues()));
                    
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
                        ,bbar             :
                        [
                            '->'
                            ,_p28_formDescuento
                        ]
                        ,buttonAlign      : 'center'
                        ,buttons          :
                        [
                            {
                                itemId    : '_p28_botonCoberturas'
                                ,text     : 'Coberturas'
                                ,icon     : '${ctx}/resources/fam3icons/icons/table.png'
                                ,disabled : true
                                ,handler  : _p28_coberturas
                            }
                            ,{
                                itemId   : '_p28_botonEditar'
                                ,text    : 'Editar'
                                ,icon    : '${ctx}/resources/fam3icons/icons/pencil.png'
                                ,handler : _p28_editar
                            }
                            ,{
                                itemId   : '_p28_botonClonar'
                                ,text    : 'Clonar'
                                ,icon    : '${ctx}/resources/fam3icons/icons/control_repeat_blue.png'
                                ,handler : _p28_clonar
                            }
                            ,{
                                itemId   : '_p28_botonNueva'
                                ,text    : 'Nueva'
                                ,icon    : '${ctx}/resources/fam3icons/icons/arrow_refresh.png'
                                ,handler : _p28_nueva
                            }
                            /*
                            new _0_BotComprar()
                            --,new _0_BotCoberturas()
                            --,new _0_BotEditar()
                            --,new _0_BotClonar()
                            --,new _0_BotNueva()
                            */
                        ]
                        ,listeners        :
                        {
                            select : _p28_tarifaSelect
                        }
                    });
                    
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
    }
    else
    {
        try {
            _fieldByName('nmpoliza').focus();
        } catch(e) {
            debug(e);
        }
    }
    
    if(_p28_smap1.cdramo+'x'=='5x'&&_p28_smap1.cdsisrol=='EJECUTIVOCUENTA')
    {
        var agente = _fieldByLabel('AGENTE');
        agente.setValue(_p28_smap1.cdagente);
        agente.setReadOnly(true);
        _p28_ramo5AgenteSelect(agente,_p28_smap1.cdagente);
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
    
    
    if(_p28_smap1.cdramo+'x'=='5x')
    {
        _p28_calculaVigencia();
        _fieldLikeLabel('CLIENTE NUEVO').setValue('S');    
        
        if(_p28_smap1.cdsisrol=='EJECUTIVOCUENTA')
        {
            var agente = _fieldByLabel('AGENTE');
            agente.setValue(_p28_smap1.cdagente);
            agente.setReadOnly(true);
            _p28_ramo5AgenteSelect(agente,_p28_smap1.cdagente);
        }
    }
    
    debug('<_p28_limpiar');
}

function _p28_calculaVigencia(comp,val)
{
    debug('>_p28_calculaVigencia');
    var feini = _fieldByName('feini');
    var fefin = _fieldByName('fefin');
    
    var itemVigencia=_fieldByLabel('VIGENCIA');
    itemVigencia.hide();
    
    if(feini.isValid()&&fefin.isValid())
    {
        var milisDif = Ext.Date.getElapsed(feini.getValue(),fefin.getValue());
        var diasDif  = (milisDif/1000/60/60/24).toFixed(0);
        debug('milisDif:',milisDif,'diasDif:',diasDif);
        itemVigencia.setValue(diasDif);
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
                            form.formOculto.loadRecord(primerInciso);
                            debug('form oculto values:',form.formOculto.getValues());
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

function _p28_clonar()
{
    debug('>_p28_clonar');
    _p28_editar();
    _fieldByName('nmpoliza').setValue('');
    debug('<_p28_clonar');
}

function _p28_coberturas()
{
    _p28_storeCoberturas.load(
    {
        params :
        {
            jsonCober_unieco   : _p28_smap1.cdunieco
            ,jsonCober_cdramo  : _p28_smap1.cdramo
            ,jsonCober_estado  : 'W'
            ,jsonCober_nmpoiza : _fieldByName('nmpoliza').getValue()
            ,jsonCober_cdplan  : _p28_selectedCdplan
            ,jsonCober_cdcia   : '20'
            ,jsonCober_situa   : _p28_selectedNmsituac
        }
    });
    _fieldById('_p28_gridCoberturas').setTitle('Plan ' + _p28_selectedDsplan);
    _p28_windowCoberturas.show();
    centrarVentanaInterna(_p28_windowCoberturas);
}

function _p28_tarifaSelect(selModel, record, row, column, eOpts)
{
    var gridTarifas = _fieldById('_p28_gridTarifas');
    debug('column:',column);
    if(column>0)
    {
        column = (column * 2) -1;
    }
    debug('( column * 2 )-1:',column);
    var columnName=gridTarifas.columns[column].dataIndex;
    debug('record',record);
    debug('columnName',columnName);
    if(columnName=='DSPERPAG')
    {
        debug('DSPERPAG');
        _fieldById('_p28_botonCoberturas').setDisabled(true);
        /*Ext.getCmp('_0_botComprarId').setDisabled(true);*/
    }
    else
    {
        // M N P R I M A X
        //0 1 2 3 4 5 6 7
        _p28_selectedCdperpag = record.get("CDPERPAG");
        _p28_selectedCdplan   = columnName.substr(7);
        _p28_selectedDsplan   = record.get("DSPLAN"+_p28_selectedCdplan);
        _p28_selectedNmsituac = record.get("NMSITUAC");
        debug('_p28_selectedCdperpag' , _p28_selectedCdperpag);
        debug('_p28_selectedCdplan'   , _p28_selectedCdplan);
        debug('_p28_selectedDsplan'   , _p28_selectedDsplan);
        debug('_p28_selectedNmsituac' , _p28_selectedNmsituac);
        
        _fieldById('_p28_botonCoberturas').setDisabled(false);
        /*Ext.getCmp('_0_botComprarId').setDisabled(false);*/
    }
}
////// funciones //////
</script>
</head>
<body><div id="_p28_divpri" style="height:1500px;"></div></body>
</html>