<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script>
////// urls //////
var _p54_urlCargar                    = '<s:url namespace="/flujomesacontrol" action="recuperarTramites"        />'
    ,_p54_urlRecuperarPoliza          = '<s:url namespace="/flujomesacontrol" action="recuperarPolizaUnica"     />'
    ,_p54_urlRegistrarTramite         = '<s:url namespace="/flujomesacontrol" action="registrarTramite"         />'
    ,_p54_urlCargarCduniecoAgenteAuto = '<s:url namespace="/emision"          action="cargarCduniecoAgenteAuto" />';
////// urls //////

////// variables //////
var _p54_params = <s:property value="%{convertToJSON('params')}"  escapeHtml="false" />;
debug('_p54_params:',_p54_params);

var _p54_grid;
var _p54_store;

var _p54_tamanioPag = 10;

var _p54_windowNuevo;
////// variables //////

////// overrides //////
////// overrides //////

////// componentes dinamicos //////
_p54_filtroItemsDin = [ <s:property value="items.filtroItems" escapeHtml="false" /> ];

_p54_filtroItems =
[
    {
        xtype       : 'textfield'
        ,fieldLabel : '_AGRUPAMC'
        ,name       : 'AGRUPAMC'
        ,value      : _p54_params.AGRUPAMC
        ,style      : 'margin:5px;'
        ,readOnly   : true
        ,hidden     : true
    }
];

for(var i in _p54_filtroItemsDin)
{
    _p54_filtroItems.push(_p54_filtroItemsDin[i]);
}

_p54_gridButtons = [ <s:property value="items.botonesGrid" escapeHtml="false" /> ];
_p54_gridButtons.push('->');
_p54_gridButtons.push(
{
    xtype       : 'textfield'
    ,itemId     : '_p54_filtroCmp'
    ,fieldLabel : '<span style="color:white;">Filtro:</span>'
    ,labelWidth : 60
    ,espera     : ''
    ,listeners  :
    {
        change : function(me,val)
        {
            clearTimeout(me.espera);
            me.espera=setTimeout(function()
            {
                Ext.ComponentQuery.query('[xtype=button][text=Buscar]')[0].handler(Ext.ComponentQuery.query('[xtype=button][text=Buscar]')[0]);
            },1500);
        }
    }
});

/*
/////////////////////////////
//No hay filtrado en paginado
/////////////////////////////

_p54_gridButtons.push('->');
_p54_gridButtons.push(
{
    xtype       : 'textfield'
    ,fieldLabel : '<span style="color:white;">Filtro (pend.):</span>'
    ,labelWidth : 60
});
*/
////// componentes dinamicos //////

Ext.onReady(function()
{
    ////// requires //////
    ////// requires //////
    
    ////// modelos //////
    ////// modelos //////
    
    ////// stores //////
    _p54_store = Ext.create('Ext.data.Store',
    {
        fields    : [ <s:property value="items.gridFields" escapeHtml="false" /> ]
        ,autoLoad : false
        ,pageSize : _p54_tamanioPag
        ,proxy    :
        {
            url          : _p54_urlCargar
            ,type        : 'ajax'
            ,extraParams :
            {
                'params.AGRUPAMC'  : _p54_params.AGRUPAMC
                ,'params.STATUS'   : '-1'
                ,'params.CDAGENTE' : _p54_params.CDAGENTE
            }
            ,reader      :
            {
                type           : 'json'
                ,root          : 'list'
                ,totalProperty : 'total'
            }
        }
    });
    ////// stores //////
    
    ////// componentes //////
    _p54_windowNuevo = Ext.create('Ext.window.Window',
    {
        title        : 'Agregar tr\u00e1mite'
        ,itemId      : '_p54_formNuevoTramite'
        ,icon        : '${icons}add.png'
        ,modal       : true
        ,closeAction : 'hide'
        ,items       :
        [
            Ext.create('Ext.form.Panel',
            {
                layout :
                {
                    type     : 'table'
                    ,columns : 2
                }
                ,items       : [ <s:property value="items.formItems" escapeHtml="false" /> ]
                ,buttonAlign : 'center'
                ,buttons     :
                [{
                    text     : 'Guardar'
                    ,icon    : '${icons}disk.png'
                    ,handler : function(me)
                    {
                        _p54_registrarTramite(me);
                    }
                }]
            })
        ]
        ,showNew : function()
        {
            var me = this;
            me.down('form').getForm().reset();
            
            var compConValorCargado = Ext.ComponentQuery.query('[valorCargado]',me);
            debug('compConValorCargado:',compConValorCargado,'.');
            for(var i=0;i<compConValorCargado.length;i++)
            {
                compConValorCargado[i].setValue(compConValorCargado[i].valorCargado);
                compConValorCargado[i].isValid();
            }
            
            var componentesAnidados = Ext.ComponentQuery.query('[heredar]',me);
            debug('componentesAnidados:',componentesAnidados,'.');
            for(var i=0;i<componentesAnidados.length;i++)
            {
                componentesAnidados[i].heredar(true);
            }
            
            centrarVentanaInterna(me.show());
        }
    });
    ////// componentes //////
    
    ////// contenido //////
    Ext.create('Ext.panel.Panel',
    {
        itemId    : '_p54_panelpri'
        ,renderTo : '_p54_divpri'
        ,border   : 0
        ,defaults : { style : 'margin:5px;' }
        ,items    :
        [
            Ext.create('Ext.form.Panel',
            {
                title   : 'Filtro'
                ,itemId : '_p54_filtroForm'
                ,icon   : '${icons}zoom.png'
                ,items  : _p54_filtroItems
                ,layout :
                {
                    type     : 'table'
                    ,columns : 3
                }
                ,buttonAlign : 'center'
                ,buttons     :
                [
                    {
                        text       : 'Buscar'
                        ,icon      : '${icons}zoom.png'
                        ,listeners :
                        {
                            afterrender : function(me)
                            {
                                _setLoading(true,me);
                                setTimeout(
                                    function()
                                    {
                                        _setLoading(false,me);
                                        me.handler(me);
                                    }
                                    ,1.5*1000
                                );
                            }
                        }
                        ,handler   : function(me)
                        {
                            var ck = 'Recuperando tr\u00e1mites';
                            try
                            {
                                var form = me.up('form');
                                if(!form.isValid())
                                {
                                    throw 'Favor de revisar los datos';
                                }
                                
                                var values = form.getValues();
                                values.FILTRO = _fieldById('_p54_filtroCmp').getValue();
                                
                                _p54_store.proxy.extraParams = _formValuesToParams(values);
                                _p54_grid.down('pagingtoolbar').moveFirst();
                            }
                            catch(e)
                            {
                                manejaException(e,ck);
                            }
                        }
                    }
                    ,{
                        text     : 'Limpiar'
                        ,icon    : '${icons}control_repeat_blue.png'
                        ,handler : function(me)
                        {
                            me.up('form').getForm().reset();
                        }
                    }
                    ,{
                        text     : 'Reporte (pendiente)'
                        ,icon    : '${icons}printer.png'
                        ,handler : function(me)
                        {}
                    }
                ]
            })
            ,Ext.create('Ext.grid.Panel',
            {
                title        : 'Tareas'
                ,itemId      : '_p54_grid'
                ,height      : 420
                ,tbar        : _p54_gridButtons
                ,columns     : [ <s:property value="items.gridColumns" escapeHtml="false" /> ]
                ,store       : _p54_store
                ,dockedItems :
                [
                    {
                        xtype        : 'pagingtoolbar'
                        ,store       : _p54_store
                        ,dock        : 'bottom'
                        ,displayInfo : true
                    }
                ]
                ,selModel  :
                {
                    type           : 'rowmodel'
                    ,allowDeselect : true
                }
                ,listeners :
                {
                    select : function(grid,record)
                    {
                        var ck = 'Invocando acciones de proceso';
                        try
                        {
                            centrarVentanaInterna(Ext.create('Ext.window.Window',
                            {
                                title        : 'ACCIONES'
                                ,itemId      : '_p54_windowAcciones'
                                ,modal       : true
                                ,width       : 800
                                ,height      : 300
                                ,border      : 0
                                ,closeAction : 'destroy'
                                ,defaults    :
                                {
                                    style  : 'margin:5px;border-bottom:1px solid #CCCCCC;'
                                    ,width : 350
                                }
                                ,layout      :
                                {
                                    type     : 'table'
                                    ,columns : 2
                                }
                                ,items       :
                                [
                                    {
                                        xtype       : 'displayfield'
                                        ,fieldLabel : 'NO. TR\u00c1MITE'
                                        ,value      : record.get('NTRAMITE')
                                    }
                                    ,{
                                        xtype       : 'displayfield'
                                        ,fieldLabel : 'TR\u00c1MITE'
                                        ,value      : record.get('DSTIPFLU')
                                    }
                                    ,{
                                        xtype       : 'displayfield'
                                        ,fieldLabel : 'PROCESO'
                                        ,value      : record.get('DSFLUJOMC')
                                    }
                                    ,{
                                        xtype       : 'displayfield'
                                        ,fieldLabel : 'ESTATUS'
                                        ,value      : record.get('DSSTATUS')
                                    }
                                    ,{
                                        xtype       : 'displayfield'
                                        ,fieldLabel : 'SUCURSAL'
                                        ,value      : record.get('CDUNIECO')
                                    }
                                    ,{
                                        xtype       : 'displayfield'
                                        ,fieldLabel : 'SUBRAMO'
                                        ,value      : record.get('DSTIPSIT')
                                    }
                                    ,{
                                        xtype       : 'displayfield'
                                        ,fieldLabel : 'P\u00d3LIZA'
                                        ,value      : record.get('NMPOLIZA')
                                    }
                                ]
                                ,buttonAlign : 'center'
                                ,buttons     : _cargarBotonesEntidad
                                (
                                    record.get('CDTIPFLU')
                                    ,record.get('CDFLUJOMC')
                                    ,'E'
                                    ,record.get('STATUS')
                                    ,''
                                    ,'_p54_windowAcciones'
                                    ,record.get('NTRAMITE')
                                    ,record.get('STATUS')
                                    ,record.get('CDUNIECO')
                                    ,record.get('CDRAMO')
                                    ,record.get('ESTADO')
                                    ,record.get('NMPOLIZA')
                                    ,record.get('NMSITUAC')
                                    ,record.get('NMSUPLEM')
                                    ,function()
                                    {
                                        _p54_store.reload();
                                    }
                                )
                            }).show());
                        }
                        catch(e)
                        {
                            manejaException(e,ck);
                        }
                    }
                }
            })
        ]
    });
    ////// contenido //////
    
    ////// custom //////
    var _p54_grid = _fieldById('_p54_grid');
    
    var cdtipfluCmp = _fieldByName('CDTIPFLU' , _p54_windowNuevo);
    var cdtiptraCmp = _fieldByName('CDTIPTRA' , _p54_windowNuevo);
    var cdtipsupCmp = _fieldByName('CDTIPSUP' , _p54_windowNuevo);
    var nmpolizaCmp = _fieldByName('NMPOLIZA' , _p54_windowNuevo); 
    var estadoCmp   = _fieldByName('ESTADO'   , _p54_windowNuevo);
    cdtipfluCmp.on(
    {
        select : function(me,records)
        {
            debug('select records[0]:',records[0]);
            cdtiptraCmp.setValue(records[0].get('aux'));
            cdtipsupCmp.setValue(records[0].get('aux2'));
            nmpolizaCmp.allowBlank = records[0].get('aux3') != 'S';
            nmpolizaCmp.setValue('0');
        }
    });
    
    cdtipsupCmp.on(
    {
        change : function(me,val)
        {
            if(!Ext.isEmpty(val)&&Number(val)==1)
            {
                nmpolizaCmp.setValue('0');
                nmpolizaCmp.setReadOnly(true);
                //nmpolizaCmp.disable();
            }
            else
            {
                //nmpolizaCmp.enable();
                nmpolizaCmp.setReadOnly(false);
            }
        }
    });
    
    nmpolizaCmp.on(
    {
        blur : function(me)
        {
            var val = me.getValue();
            if(!Ext.isEmpty(val)&&me.allowBlank==false)
            {
                var ck = 'Recuperando p\u00f3liza';
                try
                {
                
                    var cdunieco = _p54_windowNuevo.down('[name=CDSUCDOC]').getValue()
                        ,cdramo  = _p54_windowNuevo.down('[name=CDRAMO]').getValue();
                    
                    if(Ext.isEmpty(cdunieco)||Ext.isEmpty(cdramo))
                    {
                        throw 'Seleccione sucursal documento y producto antes de confirmar la p\u00f3liza';
                    }
                
                    _setLoading(true,_p54_windowNuevo);
                    Ext.Ajax.request(
                    {
                        url      : _p54_urlRecuperarPoliza
                        ,params  :
                        {
                            'params.CDUNIECO'  : cdunieco
                            ,'params.CDRAMO'   : cdramo
                            ,'params.ESTADO'   : 'M'
                            ,'params.NMPOLIZA' : val
                        }
                        ,success : function(response)
                        {
                            _setLoading(false,_p54_windowNuevo);
                            var ck = 'Decodificando respuesta al recuperar p\u00f3liza';
                            try
                            {
                                var json = Ext.decode(response.responseText);
                                debug('### poliza:',json);
                                if(json.success==true)
                                {
                                    centrarVentanaInterna(Ext.create('Ext.window.Window',
                                    {
                                        title     : 'P\u00f3liza'
                                        ,modal    : true
                                        ,closable : false
                                        ,width    : 400
                                        ,border   : 0
                                        ,defaults : { style : 'margin:5px;' }
                                        ,items    :
                                        [
                                            {
                                                xtype       : 'displayfield'
                                                ,fieldLabel : 'SUCURSAL'
                                                ,value      : json.params.CDUNIECO
                                            }
                                            ,{
                                                xtype       : 'displayfield'
                                                ,fieldLabel : 'P\u00d3LIZA'
                                                ,value      : json.params.NMPOLIZA
                                            }
                                            ,{
                                                xtype       : 'displayfield'
                                                ,fieldLabel : 'FECHA DE EMISI\u00d3N'
                                                ,value      : json.params.FEEMISIO
                                            }
                                            ,{
                                                xtype       : 'displayfield'
                                                ,fieldLabel : 'INICIO DE VIGENCIA'
                                                ,value      : json.params.FEEFECTO
                                            }
                                            ,{
                                                xtype       : 'displayfield'
                                                ,fieldLabel : 'CONTRATANTE'
                                                ,value      : json.params.CONTRATANTE
                                            }
                                            ,{
                                                xtype       : 'displayfield'
                                                ,fieldLabel : 'AGENTE'
                                                ,value      : json.params.AGENTE
                                            }
                                        ]
                                        ,buttonAlign : 'center'
                                        ,buttons     :
                                        [
                                            {
                                                text     : 'Aceptar'
                                                ,icon    : '${icons}accept.png'
                                                ,handler : function(bot)
                                                {
                                                    bot.up('window').destroy();
                                                    me.up('window').down('[name=CDAGENTE]').focus();
                                                    estadoCmp.setValue('M');
                                                }
                                            }
                                            ,{
                                                text     : 'Cancelar'
                                                ,icon    : '${icons}cancel.png'
                                                ,handler : function(bot)
                                                {
                                                    me.setValue('');
                                                    estadoCmp.setValue('');
                                                    me.isValid();
                                                    bot.up('window').destroy();
                                                }
                                            }
                                        ]
                                    }).show());
                                }
                                else
                                {
                                    mensajeError(json.message);
                                    me.setValue('');
                                    estadoCmp.setValue('');
                                    me.isValid();
                                }
                            }
                            catch(e)
                            {
                                me.setValue('');
                                estadoCmp.setValue('');
                                me.isValid();
                                manejaException(e,ck);
                            }
                        }
                        ,failure : function()
                        {
                            _setLoading(false,_p54_windowNuevo);
                            me.setValue('');
                            estadoCmp.setValue('');
                            me.isValid();
                            errorComunicacion(null,'Error Recuperando p\u00f3liza');
                        }
                    });
                }
                catch(e)
                {
                    _setLoading(false,_p54_windowNuevo);
                    me.setValue('');
                    estadoCmp.setValue('');
                    me.isValid();
                    manejaException(e,ck);
                }
            }
        }
    });
    
    try
    {
        var comboStatus = _fieldByLabel('ESTATUS',_fieldById('_p54_filtroForm'));
        
        debug('comboStatus:',comboStatus);
        
        comboStatus.store.padre = comboStatus;
        comboStatus.store.on(
        {
            load : function(me)
            {
                me.padre.forceSelection = true;
                me.padre.setEditable(true);
            }
        });
        
        if(comboStatus.store.getCount()>0)
        {
            comboStatus.forceSelection = true;
            comboStatus.setEditable(true);
        }
    }
    catch(e)
    {
        manejaException(e);
    }
    
    _p54_recuperaSucursalesTramiteAgente();
    
    _p54_agregaListenersPromotorSuscriptor();
    ////// custom //////
    
    ////// loaders //////
    ////// loaders //////
});

////// funciones //////
function _p54_registrarTramite(bot)
{
    debug('_p54_registrarTramite');
    var ck   = 'Registrando tr\u00e1mite';
    var form = bot.up('form');
    try
    {
        if(!form.isValid())
        {
            throw 'Favor de revisar los datos';
        }
        
        _setLoading(true,form);
        Ext.Ajax.request(
        {
            url      : _p54_urlRegistrarTramite
            ,params  : _formValuesToParams(form.getValues())
            ,success : function(response)
            {
                _setLoading(false,form);
                var ck = 'Decodificando respuesta al registrar tr\u00e1mite';
                try
                {
                    var json = Ext.decode(response.responseText);
                    debug('### +tramite:',json);
                    if(json.success == true)
                    {
                        mensajeCorrecto('Tr\u00e1mite generado','Se gener\u00f3 el tr\u00e1mite '+json.params.ntramite,function()
                        {
                            bot.up('window').hide();
                            _p54_store.reload();
                        });
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
                _setLoading(false,form);
                errorComunicacion(null,'Error al registrar tr\u00e1mite');
            }
        });
    }
    catch(e)
    {
        _setLoading(false,form);
        manejaException(e,ck);
    }
}

/*
 * Recupera la sucursal de un agente
 * ejecuta el callback enviandole ese cdunieco
 */
function _p54_recuperarSucursalAgente(cdagente,callback)
{
    debug('>_p54_recuperarSucursalAgente cdagente:',cdagente,'callback:',callback,'.');
    var ck = 'Recuperando sucursal de agente';
    try
    {
        if(Ext.isEmpty(cdagente) || Ext.isEmpty(callback))
        {
            throw 'No hay par\u00e1metros completos para recuperar sucursal de agente';
        }
        
        _mask(ck);
        Ext.Ajax.request(
        {
            url      : _p54_urlCargarCduniecoAgenteAuto
            ,params  :
            {
                'smap1.cdagente' : cdagente
            }
            ,success : function(response)
            {
                _unmask();
                var ck = 'Decodificando respuesta al recuperar sucursal de agente';
                try
                {
                    var json = Ext.decode(response.responseText);
                    debug('### sucursal agente:',json,'.');
                    if(json.exito === true)
                    {
                        callback(json.smap1.cdunieco);
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
            ,failure : function(response)
            {
                _unmask();
                errorComunicacion(null,'Error al recuperar sucursal de agente');
            }
        });
    }
    catch(e)
    {
        manejaException(e,ck);
    }
}

function _p54_recuperaSucursalesTramiteAgente()
{
    debug('>_p54_recuperaSucursalesTramiteAgente');
    var ck = 'Recuperando sucursal agente';
    try
    {
        if(_p54_params.CDSISROL === RolSistema.Agente )
        {
            _p54_recuperarSucursalAgente(
                _p54_params.CDAGENTE
                ,function(cdunieco)
                {
                    debug('callback al cargar sucursal de agente para los combos de sucursal, cdunieco:',cdunieco,'.');
                
                    var sucuAdminComp = _fieldByLabel('SUCURSAL ADMINISTRATIVA',_fieldById('_p54_formNuevoTramite'),true);
                    debug('sucuAdminComp:',sucuAdminComp,'.');
                    
                    if(!Ext.isEmpty(sucuAdminComp))
                    {
                        sucuAdminComp.valorCargado = cdunieco;
                    }
                    
                    var sucuDocuComp = _fieldByLabel('SUCURSAL DOCUMENTO',_fieldById('_p54_formNuevoTramite'),true);
                    debug('sucuDocuComp:',sucuDocuComp,'.');
                    
                    if(!Ext.isEmpty(sucuDocuComp))
                    {
                        sucuDocuComp.valorCargado = cdunieco;
                    }
                }
            );
        }
    }
    catch(e)
    {
        _unmask();
        manejaException(e,ck);
    }
}

function _p54_agregaListenersPromotorSuscriptor()
{
    debug('>_p54_agregaListenersPromotorSuscriptor');
    if([RolSistema.PromotorAuto,RolSistema.SuscriptorAuto].indexOf(_p54_params.CDSISROL) != -1 )
    {
        var agenteComp = _fieldByLabel('AGENTE',_fieldById('_p54_formNuevoTramite'),true);
        
        if(!Ext.isEmpty(agenteComp))
        {
            _p54_windowNuevo.on(
            {
                show : function(me)
                {
                    mensajeWarning('Seleccione un agente para recuperar las sucursales');
                }
            });
            
            agenteComp.on(
            {
                select : function(me,records)
                {
                    debug('agenteComp event select me:',me,'records:',records,'.');
                    _p54_recuperarSucursalAgente(
                        records[0].get('key')
                        ,function(cdunieco)
                        {
                            debug('callback recuperando sucursal de agente seleccionado, cdunieco:',cdunieco,'.');
                            var ck = 'Verificando sucursal de agente';
                            try
                            {
                                var sucuAdminComp = _fieldByLabel('SUCURSAL ADMINISTRATIVA',_fieldById('_p54_formNuevoTramite'),true);
			                    debug('sucuAdminComp:',sucuAdminComp,'.');
			                    
			                    if(!Ext.isEmpty(sucuAdminComp))
			                    {
			                        sucuAdminComp.setValue(cdunieco);
			                    }
			                    
			                    var sucuDocuComp = _fieldByLabel('SUCURSAL DOCUMENTO',_fieldById('_p54_formNuevoTramite'),true);
			                    debug('sucuDocuComp:',sucuDocuComp,'.');
			                    
			                    if(!Ext.isEmpty(sucuDocuComp))
			                    {
			                        sucuDocuComp.setValue(cdunieco);
			                    }
			                    
			                    var componentesAnidados = Ext.ComponentQuery.query('[heredar]',_p54_windowNuevo);
			                    debug('componentesAnidados:',componentesAnidados,'.');
			                    for(var i=0;i<componentesAnidados.length;i++)
			                    {
			                        componentesAnidados[i].heredar(true);
			                    }
                            }
                            catch(e)
                            {
                                manejaException(e,ck);
                            }
                        }
                    );
                }
            });
        }
    }
}
////// funciones //////
</script>
</head>
<body>
<div id="_p54_divpri" style="height:900px;border:0px solid #CCCCCC;"></div>
</body>
</html>