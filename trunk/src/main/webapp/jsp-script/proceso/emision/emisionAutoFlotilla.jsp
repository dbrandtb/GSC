<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
////// urls //////
var _p31_urlPantallaCliente                = '<s:url namespace="/catalogos"  action="includes/personasLoader"              />';
var _p31_urlCotizacionAutoFlotilla         = '<s:url namespace="/emision"    action="cotizacionAutoFlotilla"             />';
var _p31_urlCargarDatosComplementarios     = '<s:url namespace="/emision"    action="cargarDatosComplementariosAutoInd"    />';
var _p31_urlCargarRetroactividadSuplemento = '<s:url namespace="/emision"    action="cargarRetroactividadSuplemento"       />';
var _p31_urlMovimientoMpoliper             = '<s:url namespace="/emision"    action="movimientoMpoliper"                   />';
var _p31_urlGuardar                        = '<s:url namespace="/emision"    action="guardarComplementariosAutoIndividual" />';
var _p31_urlRecotizar                      = '<s:url namespace="/"           action="recotizar"                            />';
var _p31_urlEmitir                         = '<s:url namespace="/"           action="emitir"                               />';
var _p31_urlDocumentosPoliza               = '<s:url namespace="/documentos" action="ventanaDocumentosPoliza"              />';
var _p31_urlRecuperacionSimple             = '<s:url namespace="/emision"    action="recuperacionSimple"                   />';
var _p31_urlRecuperacionSimpleLista        = '<s:url namespace="/emision"    action="recuperacionSimpleLista"              />';

var urlReintentarWS  = '<s:url namespace="/"        action="reintentaWSautos" />';
var _urlEnviarCorreo = '<s:url namespace="/general" action="enviaCorreo"             />';


////// urls //////

////// variables //////
var _p31_smap1 = <s:property value="%{convertToJSON('smap1')}" escapeHtml="false" />;
debug('_p31_smap1:',_p31_smap1);

var _p31_polizaAdicionalesItems = null;
var _p31_adicionalesItems       = null;
var _p22_parentCallback         = false;

var _SWexiper = _p31_smap1.swexiper;

var _paramsRetryWS;
var _mensajeEmail;
            
////// variables //////

Ext.onReady(function()
{
    ////// modelos //////
    Ext.define('_p31_polizaModelo',
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
    _p31_polizaAdicionalesItems =
    [
        <s:property value="imap.agenteItems" />
        ,{
            xtype       : 'datefield'
            ,format     : 'd/m/Y'
            ,fieldLabel : 'INICIO DE VIGENCIA'
            ,name       : 'feini'
            ,listeners  : { change : _p31_fefinChange }
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
        for(var i=0;i<_p31_polizaAdicionalesItems.length;i++)
        {
            aux.push(_p31_polizaAdicionalesItems[i]);
        }
        _p31_polizaAdicionalesItems = aux;
    </s:if>
    for(var i=0;i<_p31_polizaAdicionalesItems.length;i++)
    {
        _p31_polizaAdicionalesItems[i].labelWidth=295;
    }
    
    _p31_adicionalesItems = [];
    <s:if test='%{getImap().get("adicionalesItems")!=null}'>
        _p31_adicionalesItems = [<s:property value="imap.adicionalesItems" />];
    </s:if>
    
    var _p31_datosGeneralesItems = [<s:property value="imap.polizaItems" />];
    for(var i=0;i<_p31_datosGeneralesItems.length;i++)
    {
        _p31_datosGeneralesItems[i].labelWidth=295;
    }
    ////// componentes //////
    
    ////// contenido //////
    Ext.create('Ext.panel.Panel',
    {
        itemId    : '_p31_panelpri'
        ,renderTo : '_p31_divpri'
        ,border   : 0
        ,defaults : { style : 'margin:5px;' }
        ,items    :
        [
            Ext.create('Ext.form.Panel',
            {
                itemId    : '_p31_polizaForm'
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
                        ,items : _p31_datosGeneralesItems
                    }
                    ,{
                        xtype  : 'fieldset'
                        ,title : '<span style="font:bold 14px Calibri;">DATOS ADICIONALES</span>'
                        ,items : _p31_polizaAdicionalesItems
                    }
                ]
            })
            ,Ext.create('Ext.form.Panel',
            {
                itemId    : '_p31_adicionalesForm'
                ,title    : 'DATOS ADICIONALES DE INCISO'
                ,defaults : { style : 'margin:5px;' }
                ,layout   :
                {
                    type     : 'table'
                    ,columns : 2
                }
                ,items    : _p31_adicionalesItems
            })
            ,Ext.create('Ext.panel.Panel',
            {
                itemId      : '_p31_clientePanel'
                ,title      : 'CLIENTE'
                ,height     : 300
                ,autoScroll : true
                ,loader     :
                {
                    url       : _p31_urlPantallaCliente
                    ,scripts  : true
                    ,autoLoad : false
                }
            })
            ,Ext.create('Ext.panel.Panel',
            {
                itemId       : '_p31_panelBotones'
                ,border      : 0
                ,buttonAlign : 'center'
                ,buttons     :
                [
                    {
                        itemId    : '_p31_botonEmitir'
                        ,text     : 'Emitir'
                        ,icon     : '${ctx}/resources/fam3icons/icons/key.png'
                        ,handler  : _p31_emitirClic
                        ,disabled : true
                    }
                    ,{
                        itemId    : '_p31_botonGuardar'
                        ,text     : 'Guardar'
                        ,icon     : '${ctx}/resources/fam3icons/icons/disk.png'
                        ,handler  : function(){ _p31_guardar(); }
                        ,disabled : true
                    }
                    ,{
                        text     : 'Nueva'
                        ,icon    : '${ctx}/resources/fam3icons/icons/arrow_refresh.png'
                        ,handler : _p31_nuevaClic
                    }
                ]
            })
        ]
    });
    ////// contenido //////
    
    ////// custom //////
    /*_fieldByLabel('AGENTE').hide();*/
    _fieldByName('porparti').setMaxValue(99);
    ////// custom //////
    
    ////// loaders //////
    Ext.Ajax.request(
    {
        url     : _p31_urlCargarDatosComplementarios
        ,params :
        {
            'smap1.cdunieco'  : _p31_smap1.cdunieco
            ,'smap1.cdramo'   : _p31_smap1.cdramo
            ,'smap1.estado'   : _p31_smap1.estado
            ,'smap1.nmpoliza' : _p31_smap1.nmpoliza
        }
        ,success : function(response)
        {
            var json = Ext.decode(response.responseText);
            debug('### cargar datos complementarios:',json);
            if(json.exito)
            {
                var form   = _fieldById('_p31_polizaForm');
                var record = new _p31_polizaModelo(json.smap1);
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
                
                _fieldById('_p31_clientePanel').loader.load(
                {
                    params:
                    {
                        'smap1.cdperson' : json.smap1.cdperson,
                        'smap1.cdideper' : json.smap1.cdideper,
                        'smap1.cdideext' : json.smap1.cdideext,
                        'smap1.esSaludDanios' : 'D',
                        'smap1.esCargaClienteNvo' :(Ext.isEmpty(json.smap1.cdperson)? 'S' : 'N' ),
                        'smap1.cargaCP' : json.smap1.cdpostal,
                        'smap1.cargaTipoPersona' : json.smap1.otfisjur
                    }
                });
                
                _p22_parentCallback = _p31_personaSaved;
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
        url     : _p31_urlRecuperacionSimpleLista
        ,params :
        {
            'smap1.procedimiento' : 'RECUPERAR_TVALOSIT'
            ,'smap1.cdunieco'     : _p31_smap1.cdunieco
            ,'smap1.cdramo'       : _p31_smap1.cdramo
            ,'smap1.estado'       : _p31_smap1.estado
            ,'smap1.nmpoliza'     : _p31_smap1.nmpoliza
            ,'smap1.nmsuplem'     : '0'
        }
        ,success : function(response)
        {
            var json=Ext.decode(response.responseText);
            debug('### tvalosit:',json);
            if(json.exito)
            {
                /*
                var form = _fieldById('_p31_adicionalesForm');
                for(var i in json.smap1)
                {
                    var item = _fieldByName(i,form,true);
                    if(item)
                    {
                        item.setValue(json.smap1[i]);
                        if(_p31_smap1.cdramo+'x'=='5x')
                        {
                            if(item.fieldLabel=='CONDUCTOR'&&Ext.isEmpty(json.smap1[i]))
                            {
                                item.setValue(json.smap1['parametros.pv_otvalor15']);
                            }
                        }
                    }
                }

                _p31_loadCallback();
                */
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
function _p31_fefinChange(me,newVal,oldVal)
{
    debug('>_p31_fefinChange:',newVal,oldVal,'DUMMY');
    debug('<_p31_fefinChange');
}

function _p31_nuevaClic()
{
    _fieldById('_p31_panelpri').setLoading(true);
    Ext.create('Ext.form.Panel').submit(
    {
        url     : _p31_urlCotizacionAutoFlotilla
        ,params :
        {
            'smap1.cdramo'    : _p31_smap1.cdramo
            ,'smap1.cdtipsit' : _p31_smap1.cdtipsit
        }
        ,standardSubmit : true
    });
}

function _p31_loadCallback()
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
        url     : _p31_urlCargarRetroactividadSuplemento
        ,params :
        {
            'smap1.cdunieco'  : _p31_smap1.cdunieco
            ,'smap1.cdramo'   : _p31_smap1.cdramo
            ,'smap1.cdtipsup' : 1
            ,'smap1.cdusuari' : _p31_smap1.cdusuari
            ,'smap1.cdtipsit' : _p31_smap1.cdtipsit
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
    
    if(_p31_smap1.cdramo+'x'=='5x')
    {
        Ext.Ajax.request(
        {
            url     : _p31_urlRecuperacionSimple
            ,params :
            {
                'smap1.procedimiento' : 'RECUPERAR_DATOS_VEHICULO_RAMO_5'
                ,'smap1.cdunieco'     : _p31_smap1.cdunieco
                ,'smap1.cdramo'       : _p31_smap1.cdramo
                ,'smap1.estado'       : _p31_smap1.estado
                ,'smap1.nmpoliza'     : _p31_smap1.nmpoliza 
            }
            ,success : function(response)
            {
                var json = Ext.decode(response.responseText);
                debug('### recuperar datos vehiculo ramo 5:',json);
                if(json.exito)
                {
                    var _f1_aux=
                    [
                        {
                            xtype       : 'displayfield'
                            ,fieldLabel : 'VEH&Iacute;CULO'
                            ,value      : json.smap1.datos
                        }
                    ];
                    var form=_fieldById('_p31_adicionalesForm');
                    var anteriores=form.removeAll(false);
                    form.add(
                    {
                        xtype       : 'displayfield'
                        ,fieldLabel : 'VEH&Iacute;CULO'
                        ,value      : json.smap1.datos
                    });
                    for(var i=0;i<anteriores.length;i++)
                    {
                        form.add(anteriores[i]);
                    }
                }
                else
                {
                    mensajeError(json.respuesta);
                }
            }
            ,failure : function(){ errorComunicacion(); }
        });
    }
}

function _p31_personaSaved()
{
    debug('>_p31_personaSaved');
    Ext.Ajax.request(
    {
        url     : _p31_urlMovimientoMpoliper
        ,params :
        {
            'smap1.cdunieco'  : _p31_smap1.cdunieco
            ,'smap1.cdramo'   : _p31_smap1.cdramo
            ,'smap1.estado'   : _p31_smap1.estado
            ,'smap1.nmpoliza' : _p31_smap1.nmpoliza
            ,'smap1.nmsituac' : '1'
            ,'smap1.cdrol'    : '1'
            ,'smap1.cdperson' : _p22_fieldCdperson().getValue()
            ,'smap1.nmsuplem' : '0'
            ,'smap1.status'   : 'V'
            ,'smap1.nmorddom' : '1'
            ,'smap1.accion'   : 'I'
            ,'smap1.swexiper' : _SWexiper
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
    debug('<_p31_personaSaved');
}

function _p31_emitirClic()
{
    debug('>_p31_emitirClic');
    
    _p31_guardar(_p31_mostrarVistaPrevia);
    
    debug('<_p31_emitirClic');
}

function _p31_guardar(callback)
{
    debug('>_p31_guardar');
    var form1  = _fieldById('_p31_polizaForm');
    var form2  = _fieldById('_p31_adicionalesForm');
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
        
        json.smap1['cdunieco'] = _p31_smap1.cdunieco;
        json.smap1['cdramo']   = _p31_smap1.cdramo;
        json.smap1['estado']   = _p31_smap1.estado;
        json.smap1['ntramite'] = _p31_smap1.ntramite;
        json.smap1['cdagente'] = _fieldByLabel('AGENTE').getValue();
        
        debug('json para guardar:',json);
        var panelPri = _fieldById('_p31_panelpri');
        panelPri.setLoading(true);
        Ext.Ajax.request(
        {
            url       : _p31_urlGuardar
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
    
    debug('<_p31_guardar');
}

function _p31_mostrarVistaPrevia()
{
    debug('>_p31_mostrarVistaPrevia');
    var panelpri = _fieldById('_p31_panelpri');
    panelpri.setLoading(true);
    Ext.Ajax.request(
    {
        url     : _p31_urlRecotizar
        ,params :
        {
            cdunieco             : _p31_smap1.cdunieco
            ,cdramo              : _p31_smap1.cdramo
            ,cdtipsit            : _p31_smap1.cdtipsit
            ,'panel1.nmpoliza'   : _p31_smap1.nmpoliza
            ,'panel1.notarifica' : 'si'
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
                                    ,itemId     : '_p31_numerofinalpoliza'
                                    ,fieldLabel : 'N&uacute;mero de poliza'
                                    ,readOnly   : true
                                }
                                ,{
                                    itemId   : '_p31_botonEmitirPolizaFinal'
                                    ,xtype   : 'button'
                                    ,text    : 'Emitir'
                                    ,icon    : '${ctx}/resources/fam3icons/icons/award_star_gold_3.png'
                                    ,handler : _p31_emitirFinal
                                },{
                                    itemId : 'botonEnvioEmail'
                                    ,xtype : 'button'
                                    ,text  : 'Enviar Email'
                                    ,icon  : '${ctx}//resources/fam3icons/icons/email.png'
                                    ,disabled: true
                                    ,handler:function()
                                    {
                                        Ext.Msg.prompt('Envio de Email', 'Escriba los correos que recibir&aacute;n la documentaci&oacute;n (separados por ;)', 
                                        function(buttonId, text){
                                            if(buttonId == "ok" && !Ext.isEmpty(text)){
                                                
                                                if(Ext.isEmpty(_mensajeEmail)){
                                                    mensajeError('Mensaje de Email sin contenido. Consulte a Soporte T&eacute;cnico');
                                                    return;
                                                }
                                                
                                                Ext.Ajax.request(
                                                        {
                                                            url : _urlEnviarCorreo,
                                                            params :
                                                            {
                                                                to     : text,
                                                                asunto : 'Documentación de póliza de Autos',
                                                                mensaje: _mensajeEmail,
                                                                html   : true
                                                            },
                                                            callback : function(options,success,response)
                                                            {
                                                                if (success)
                                                                {
                                                                    var json = Ext.decode(response.responseText);
                                                                    if (json.success == true)
                                                                    {
                                                                        Ext.Msg.show(
                                                                        {
                                                                            title : 'Correo enviado'
                                                                            ,msg : 'El correo ha sido enviado'
                                                                            ,buttons : Ext.Msg.OK
                                                                        });
                                                                    }
                                                                    else
                                                                    {
                                                                        mensajeError('Error al enviar el correo');
                                                                    }
                                                                }
                                                                else
                                                                {
                                                                    errorComunicacion();
                                                                }
                                                            }
                                                        });
                                            
                                            }else {
                                                mensajeWarning('Introduzca al menos una direcci&oacute;n de email');    
                                            }
                                        })
                                    }
                                }
                                ,{
                                    itemId : 'botonReenvioWS'
                                    ,xtype : 'button'
                                    ,text  : 'Reintentar Emisi&oacute;n'
                                    ,icon  : '${ctx}/resources/fam3icons/icons/award_star_gold_3.png'
                                    ,disabled: true
                                    ,handler:function()
                                    {
                                        var me=this;
                                        reintentarWSAuto(me.up().up(), _paramsRetryWS);
                                    }
                                }
                                ,{
                                    xtype     : 'button'
                                    ,itemId   : '_p31_botonDocumentosPolizaEmitida'
                                    ,text     : 'Imprimir'
                                    ,icon     : '${ctx}/resources/fam3icons/icons/printer.png'
                                    ,disabled : true
                                    ,handler  : function()
                                    {
                                        centrarVentanaInterna(Ext.create('Ext.window.Window',
                                        {
                                            title        : 'Documentos del tr&aacute;mite'
                                            ,modal       : true
                                            ,buttonAlign : 'center'
                                            ,width       : 600
                                            ,height      : 400
                                            ,autoScroll  : true
                                            ,loader      :
                                            {
                                                url       : _p31_urlDocumentosPoliza
                                                ,params   :
                                                {
                                                    'smap1.nmpoliza'  : _p31_smap1.nmpolizaEmitida
                                                    ,'smap1.cdunieco' : _p31_smap1.cdunieco
                                                    ,'smap1.cdramo'   : _p31_smap1.cdramo
                                                    ,'smap1.estado'   : 'M'
                                                    ,'smap1.nmsuplem' : '0'
                                                    ,'smap1.ntramite' : ''
                                                    ,'smap1.nmsolici' : _p31_smap1.nmpoliza
                                                    ,'smap1.tipomov'  : '0'
                                                }
                                                ,scripts  : true
                                                ,autoLoad : true
                                            }
                                        }).show());
                                    }
                                }
                                ,{
                                    xtype    : 'button'
                                    ,itemId  : '_p31_botonCancelarEmision'
                                    ,text    : 'Cancelar'
                                    ,icon    : '${ctx}/resources/fam3icons/icons/cancel.png'
                                    ,handler : function()
                                    {
                                        var me=this;
                                        me.up().up().destroy();
                                    }
                                }
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
    debug('<_p31_mostrarVistaPrevia');
}

function _p31_emitirFinal(me)
{
    debug('>_p31_emitirFinal');
    var panelpri = me.up().up();
    panelpri.setLoading(true);
    Ext.Ajax.request(
    {
        url      : _p31_urlEmitir
        ,timeout : 240000
        ,params  :
        {
            'panel1.pv_ntramite'  : _p31_smap1.ntramite
            ,'panel2.pv_cdunieco' : _p31_smap1.cdunieco
            ,'panel2.pv_cdramo'   : _p31_smap1.cdramo
            ,'panel2.pv_estado'   : _p31_smap1.estado
            ,'panel1.pv_nmpoliza' : _p31_smap1.nmpoliza
            ,'panel2.pv_nmpoliza' : _p31_smap1.nmpoliza
            ,'panel2.pv_cdtipsit' : _p31_smap1.cdtipsit
        }
        ,success:function(response)
        {
            panelpri.setLoading(false);
            var json=Ext.decode(response.responseText);
            debug('### emitir:',json);
            if(json.success==true)
            {
                _p31_smap1.nmpolizaEmitida=json.panel2.nmpoliza;
                debug("_p31_smap1.nmpolizaEmitida:",_p31_smap1.nmpolizaEmitida);
                
                _fieldById('_p31_numerofinalpoliza').setValue(json.panel2.nmpoliex);
                _fieldById('_p31_botonEmitirPolizaFinal').setDisabled(true);
                _fieldById('_p31_botonDocumentosPolizaEmitida').setDisabled(false);
                
                
                _fieldById('botonReenvioWS').hide();
                _mensajeEmail = json.mensajeEmail;
                _fieldById('botonEnvioEmail').enable();
                
                /*
                _fieldById('_p31_botonEmitirPolizaFinalPreview').hide();
                
                if(_p31_smap1.SITUACION == 'AUTO')
                {
                    
                }
                else
                {
                    _fieldById('botonEnvioEmail').hide();
                }
                if(_p31_smap1.SITUACION=='AUTO')
                {
                    _fieldById('venDocVenEmiBotIrCotiza').show();
                }
                else
                {
                    _fieldById('venDocVenEmiBotNueCotiza').show();
                }
                */
                
                _fieldById('_p31_botonCancelarEmision').setDisabled(true);
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
                if(json.retryWS){
                    _fieldById('_p31_botonEmitirPolizaFinal').hide();
//                    _fieldById('_p31_botonEmitirPolizaFinalPreview').hide();
//                    if(_p31_smap1.SITUACION=='AUTO')
//                    {
//                        _fieldById('venDocVenEmiBotIrCotiza').show();
//                    }
//                    else
//                    {
//                        _fieldById('venDocVenEmiBotNueCotiza').show();
//                    }
                    _fieldById('_p31_botonCancelarEmision').setDisabled(true);
                }
                Ext.Msg.show(
                {
                    title    :'Aviso'
                    ,msg     : json.mensajeRespuesta
                    ,buttons : Ext.Msg.OK
                    ,icon    : Ext.Msg.WARNING
                    ,fn      : function()
                    {
                        if(json.retryWS)
                        {
                            var paramsWS =
                            {
                                'panel1.pv_nmpoliza'  : _p31_smap1.nmpoliza
                                ,'panel1.pv_ntramite' : _p31_smap1.ntramite
                                ,'panel2.pv_cdramo'   : _p31_smap1.cdramo
                                ,'panel2.pv_cdunieco' : _p31_smap1.cdunieco
                                ,'panel2.pv_estado'   : _p31_smap1.estado
                                ,'panel2.pv_nmpoliza' : _p31_smap1.nmpoliza
                                ,'panel2.pv_cdtipsit' : _p31_smap1.cdtipsit
                                ,'nmpoliza'           : json.nmpoliza
                                ,'nmsuplem'           : json.nmsuplem                                                                       
                                ,'cdIdeper'           : json.cdIdeper
                            };
                            reintentarWSAuto(me.up().up(), paramsWS);
                        }
                    }
                });
            }
        }
        ,failure:function()
        {
            panelpri.setLoading(false);
            errorComunicacion();
        }
    });
    debug('<_p31_emitirFinal');
}

//funcion para reintentar WS auto
                
function reintentarWSAuto(loading, params){

    Ext.Msg.show({
       title    :'Confirmaci&oacute;n'
       ,msg     : '&iquest;Desea Reenviar los Web Services de Autos?'
       ,buttons : Ext.Msg.YESNO
       ,icon    : Ext.Msg.QUESTION
       ,fn      : function(boton, text, opt){
        if(boton == 'yes'){
            
            loading.setLoading(true);
            
            Ext.Ajax.request(
                    {
                        url     : urlReintentarWS
                        ,timeout: 240000
                        ,params :params
                        ,success:function(response)
                        {
                            loading.setLoading(false);
                            var json=Ext.decode(response.responseText);
                            debug(json);
                            if(json.success==true)
                            {
                                mensajeCorrecto('Aviso', 'Ejecuci&oacute;n Correcta de Web Services. P&oacute;liza Emitida: ' + json.nmpolAlt);
                                _fieldById('_p31_numerofinalpoliza').setValue(json.nmpolAlt);
                                _fieldById('_p31_botonDocumentosPolizaEmitida').setDisabled(false);
                                _fieldById('botonReenvioWS').setDisabled(true);
                                _fieldById('botonReenvioWS').hide();
                                
                                _mensajeEmail = json.mensajeEmail;
                                _fieldById('botonEnvioEmail').enable();
                            }
                            else
                            {
                                Ext.Msg.show({
                                    title    :'Aviso'
                                    ,msg     : json.mensajeRespuesta
                                    ,buttons : Ext.Msg.OK
                                    ,icon    : Ext.Msg.WARNING
                                    ,fn      : function(){
                                        reintentarWSAuto(loading, params);
                                    }
                                });
                            }
                        }
                        ,failure:function()
                        {
                            loading.setLoading(false);
                            Ext.Msg.show({
                                title:'Error',
                                msg: 'Error de comunicaci&oacute;n',
                                buttons: Ext.Msg.OK,
                                icon: Ext.Msg.ERROR
                                ,fn      : function(){
                                    reintentarWSAuto(loading, params);
                                }
                            });
                        }
                    });
        }else{
            _paramsRetryWS = params;
            debug("Habilitando Boton Reenvio WS");
            _fieldById('botonReenvioWS').setDisabled(false);
        }
       }
    });
                        
}

////// funciones //////
</script>
</head>
<body><div id="_p31_divpri" style="height:1000px;"></div></body>
</html>