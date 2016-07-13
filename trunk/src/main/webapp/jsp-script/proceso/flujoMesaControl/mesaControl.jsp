<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<style>
.green {
    border-left: 2px solid green;
    border-right: 2px solid green;
}

.red {
    border-left: 2px solid red;
    border-right: 2px solid red;
}
</style>
<script>
////// urls //////
var _p54_urlCargar                    = '<s:url namespace="/flujomesacontrol" action="recuperarTramites"          />'
    ,_p54_urlRecuperarPoliza          = '<s:url namespace="/flujomesacontrol" action="recuperarPolizaUnica"       />'
    ,_p54_urlRegistrarTramite         = '<s:url namespace="/flujomesacontrol" action="registrarTramite"           />'
    ,_p54_urlCargarCduniecoAgenteAuto = '<s:url namespace="/emision"          action="cargarCduniecoAgenteAuto"   />'
    ,_p54_urlRecuperarPolizaDanios    = '<s:url namespace="/flujomesacontrol" action="recuperarPolizaUnicaDanios" />'
    ,_p54_urlRecuperarPolizaSIGS      = '<s:url namespace="/emision"          action="cargarPoliza"               />';
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
    Ext.Ajax.timeout = 1000*60*2; //2 minutos
    Ext.override(Ext.form.Basic, { timeout: Ext.Ajax.timeout / 1000 });
    Ext.override(Ext.data.proxy.Server, { timeout: Ext.Ajax.timeout });
    Ext.override(Ext.data.Connection, { timeout: Ext.Ajax.timeout });

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
                [
                    {
                        text     : 'Guardar'
                        ,icon    : '${icons}disk.png'
                        ,handler : function(me)
                        {
                            _p54_registrarTramite(me);
                        }
                    }
                    ,{
                        text     : 'Limpiar'
                        ,icon    : '${icons}control_repeat_blue.png'
                        ,handler : function()
                        {
                            _p54_windowNuevo.showNew();
                        } 
                    }
                ]
            })
        ]
        ,showNew : function()
        {
            var me = this;
            me.down('form').getForm().reset();
            
            me.triggerHerencia();
            
            centrarVentanaInterna(me.show());
            
            me.onLevelChange(1);
        }
        ,triggerHerencia : function()
        {
            debug('triggerHerencia');
            
            var me = this;
            
            var componentesAnidados = Ext.ComponentQuery.query('[heredar]',me);
            debug('componentesAnidados:',componentesAnidados,'.');
            for(var i=0;i<componentesAnidados.length;i++)
            {
                componentesAnidados[i].heredar(true);
            }
        }
        ,onLevelChange : function(level)
        {
            var me = this;
            debug('onLevelChange level:',level,'.');
            
            var ck = 'Navegando ventana emergente de tr\u00e1mite nuevo';
            try
            {
                if(level === 1) // solo se muestra tipo trámite y proceso
                {
                    var comps = Ext.ComponentQuery.query('[name]',me);
                    debug('comps:',comps,'.');
                
                    for(var i = 0 ; i < comps.length ; i++ )
                    {
                        _hide(comps[i]);
                    }
                
                    _show(me.down('[name=CDTIPFLU]'));
                    _show(me.down('[name=CDFLUJOMC]'));
                    
                    me.down('[name=CDTIPFLU]').setReadOnly(false);
                    me.down('[name=CDFLUJOMC]').setReadOnly(false);
                }
                else if(level === 2) 
                {
                    me.down('[name=CDTIPFLU]').setReadOnly(true);
                    me.down('[name=CDFLUJOMC]').setReadOnly(true);
                    
                    ck = 'Recuperando tipo de tr\u00e1mite y tipo de producto';
                    
                    var cdtiptra = me.down('form').getValues().CDTIPTRA;
                    debug('cdtiptra:',cdtiptra,'.');
                    
                    var cdtipram = me.down('[name=CDFLUJOMC]').findRecordByValue(me.down('[name=CDFLUJOMC]').getValue()).get('aux');
                    debug('cdtipram:',cdtipram,'.');
                    
                    ck = 'Encendiendo atributos por proceso';
                    
                    var comps = Ext.ComponentQuery.query('[name]:not([fieldLabel^=_])',me);
                    
                    for(var i = 0 ; i < comps.length ; i++ )
                    {
                        _show(comps[i]);
                    }
                    
                    if(Number(cdtiptra) === 1) // para emision
                    {
                        // indistinto salud y danios
                        
                        // mostrar
                        
                        _fieldByName('CDSUCADM',me).allowBlank = false;
                        _show(_fieldByName('CDSUCADM',me));
                        
                        _fieldByName('CDSUCDOC',me).allowBlank = false;
                        _show(_fieldByName('CDSUCDOC',me));
                        
                        me.down('[name=CDRAMO]').allowBlank = false;
                        _show(me.down('[name=CDRAMO]'));
                        
                        me.down('[name=CDTIPSIT]').allowBlank = false;
                        _show(me.down('[name=CDTIPSIT]'));
                        
                        me.down('[name=NMPOLIZA]').allowBlank = false;
                        _show(me.down('[name=NMPOLIZA]'));
                        
                        _fieldByName('REFERENCIA',me).allowBlank = false;
                        _show(_fieldByName('REFERENCIA',me));
                        
                        _fieldByName('NOMBRE',me).allowBlank = false;
                        _show(_fieldByName('NOMBRE',me));
                        
                        _fieldByName('COMMENTS',me).allowBlank = false;
                        _show(_fieldByName('COMMENTS',me));
                        
                        // ocultar
                        
                        me.down('[name=CDUNIEXT]').allowBlank = true;
                        _hide(me.down('[name=CDUNIEXT]'));
                        
                        me.down('[name=RAMO]').allowBlank = true;
                        _hide(me.down('[name=RAMO]'));
                        
                        me.down('[name=NMPOLIEX]').allowBlank = true;
                        _hide(me.down('[name=NMPOLIEX]'));
                        
                        me.down('[name=CDTIPSUPEND]').allowBlank = true;
                        _hide(me.down('[name=CDTIPSUPEND]'));
                    }
                    else if(Number(cdtiptra) === 15 || Number(cdtiptra) === 21) // para endoso o renovacion
                    {
                        // indistinto salud y danios
                        
                        //ocultar
                        
                        _fieldByName('REFERENCIA',me).allowBlank = true;
                        _hide(_fieldByName('REFERENCIA',me));
                        
                        _fieldByName('NOMBRE',me).allowBlank = true;
                        _hide(_fieldByName('NOMBRE',me));
                        
                        _fieldByName('COMMENTS',me).allowBlank = true;
                        _hide(_fieldByName('COMMENTS',me));
                        
                        if(Number(cdtiptra) === 15)
                        {
                            _fieldByName('CDTIPSUPEND',me).allowBlank = false;
                            _show(_fieldByName('CDTIPSUPEND',me));
                        }
                        else
                        {
                            _fieldByName('CDTIPSUPEND',me).allowBlank = true;
                            _hide(_fieldByName('CDTIPSUPEND',me));
                        }
                        
                        // salud
                        
                        if(Number(cdtipram) === Number(TipoRamo.Salud)) 
                        {
                            // mostrar
                            
                            _fieldByName('CDSUCADM',me).allowBlank = false;
	                        _show(_fieldByName('CDSUCADM',me));
	                        
	                        _fieldByName('CDSUCDOC',me).allowBlank = false;
	                        _show(_fieldByName('CDSUCDOC',me));
	                        
	                        me.down('[name=CDRAMO]').allowBlank = false;
	                        _show(me.down('[name=CDRAMO]'));
	                        
	                        me.down('[name=CDTIPSIT]').allowBlank = false;
	                        _show(me.down('[name=CDTIPSIT]'));
	                        
	                        me.down('[name=NMPOLIZA]').allowBlank = false;
	                        _show(me.down('[name=NMPOLIZA]'));
	                        
	                        // ocultar
	                        
	                        me.down('[name=CDUNIEXT]').allowBlank = true;
	                        _hide(me.down('[name=CDUNIEXT]'));
	                        
	                        me.down('[name=RAMO]').allowBlank = true;
	                        _hide(me.down('[name=RAMO]'));
	                        
	                        me.down('[name=NMPOLIEX]').allowBlank = true;
	                        _hide(me.down('[name=NMPOLIEX]'));
                            
                        }
                        
                        // autos
                        
                        else if(Number(cdtipram) === Number(TipoRamo.Autos))
                        {
                            // mostrar
                            
                            me.down('[name=CDUNIEXT]').allowBlank = false;
	                        _show(me.down('[name=CDUNIEXT]'));
	                        
	                        // la sucursal queda abierta para los endosos de autos
                            me.down('[name=CDUNIEXT]').setReadOnly(false);
	                        
	                        me.down('[name=RAMO]').allowBlank = false;
	                        _show(me.down('[name=RAMO]'));
	                        
	                        me.down('[name=NMPOLIEX]').allowBlank = false;
	                        _show(me.down('[name=NMPOLIEX]'));
	                        
	                        // ocultar
	                        
	                        me.down('[name=CDRAMO]').allowBlank = true;
	                        _hide(me.down('[name=CDRAMO]'));
	                        
	                        me.down('[name=CDTIPSIT]').allowBlank = true;
	                        _hide(me.down('[name=CDTIPSIT]'));
	                        
	                        me.down('[name=NMPOLIZA]').allowBlank = true;
	                        _hide(me.down('[name=NMPOLIZA]'));
	                        
	                        _hide(me.down('[name=CDSUCADM]'));
	                        
	                        _hide(me.down('[name=CDSUCDOC]'));
                        }
                    }/*
                    else if(Number(cdtiptra) === 21) // para renovacion
                    {
                        // indistinto salud y danios
                        
                        //ocultar
                        
                        _fieldByName('REFERENCIA',me).allowBlank = true;
                        _hide(_fieldByName('REFERENCIA',me));
                        
                        _fieldByName('NOMBRE',me).allowBlank = true;
                        _hide(_fieldByName('NOMBRE',me));
                        
                        _fieldByName('COMMENTS',me).allowBlank = true;
                        _hide(_fieldByName('COMMENTS',me));
                        
                        // salud
                        
                        if(Number(cdtipram) === Number(TipoRamo.Salud)) 
                        {
                            // mostrar
                            
                            _fieldByName('CDSUCADM',me).allowBlank = false;
                            _show(_fieldByName('CDSUCADM',me));
                            
                            _fieldByName('CDSUCDOC',me).allowBlank = false;
                            _show(_fieldByName('CDSUCDOC',me));
                            
                            me.down('[name=CDRAMO]').allowBlank = false;
                            _show(me.down('[name=CDRAMO]'));
                            
                            me.down('[name=CDTIPSIT]').allowBlank = false;
                            _show(me.down('[name=CDTIPSIT]'));
                            
                            me.down('[name=NMPOLIZA]').allowBlank = false;
                            _show(me.down('[name=NMPOLIZA]'));
                            
                            // ocultar
                            
                            me.down('[name=CDUNIEXT]').allowBlank = true;
                            _hide(me.down('[name=CDUNIEXT]'));
                            
                            me.down('[name=RAMO]').allowBlank = true;
                            _hide(me.down('[name=RAMO]'));
                            
                            me.down('[name=NMPOLIEX]').allowBlank = true;
                            _hide(me.down('[name=NMPOLIEX]'));
                            
                        }
                        
                        // autos
                        
                        else if(Number(cdtipram) === Number(TipoRamo.Autos))
                        {
                            // mostrar
                            
                            me.down('[name=CDUNIEXT]').allowBlank = false;
                            _show(me.down('[name=CDUNIEXT]'));
                            
                            // la sucursal queda abierta para los endosos de autos
                            me.down('[name=CDUNIEXT]').setReadOnly(false);
                            
                            me.down('[name=RAMO]').allowBlank = false;
                            _show(me.down('[name=RAMO]'));
                            
                            me.down('[name=NMPOLIEX]').allowBlank = false;
                            _show(me.down('[name=NMPOLIEX]'));
                            
                            // ocultar
                            
                            me.down('[name=CDRAMO]').allowBlank = true;
                            _hide(me.down('[name=CDRAMO]'));
                            
                            me.down('[name=CDTIPSIT]').allowBlank = true;
                            _hide(me.down('[name=CDTIPSIT]'));
                            
                            me.down('[name=NMPOLIZA]').allowBlank = true;
                            _hide(me.down('[name=NMPOLIZA]'));
                            
                            _hide(me.down('[name=CDSUCADM]'));
                            
                            _hide(me.down('[name=CDSUCDOC]'));
                        }
                    }*/
                }
            }
            catch(e)
            {
                manejaException(e,ck);
            }
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
    
    var cdtipfluCmp = _fieldByName('CDTIPFLU' , _p54_windowNuevo)
        ,cdtiptraCmp = _fieldByName('CDTIPTRA' , _p54_windowNuevo)
        ,cdtipsupCmp = _fieldByName('CDTIPSUP' , _p54_windowNuevo)
        ,nmpolizaCmp = _fieldByName('NMPOLIZA' , _p54_windowNuevo)
        ,nmpoliexCmp = _fieldByName('NMPOLIEX' , _p54_windowNuevo)
        ,estadoCmp   = _fieldByName('ESTADO'   , _p54_windowNuevo)
        ,cduniextCmp = _fieldByName('CDUNIEXT' , _p54_windowNuevo)
        ,estatusCmp  = _fieldByName('STATUS'   , _p54_windowNuevo);
    
    cdtipfluCmp.on(
    {
        select : function(me,records)
        {
            debug('select data:',records[0].data,'.');
            
            cdtiptraCmp.setValue(records[0].get('aux'));
            
            cdtipsupCmp.setValue(records[0].get('aux2'));
            
            if(records[0].get('aux3') === 'S')
            {
                nmpolizaCmp.allowBlank      = false;
                nmpolizaCmp.verificarPoliza = true;
                
                nmpoliexCmp.allowBlank      = false;
                nmpoliexCmp.verificarPoliza = true;
            }
            else
            {
                nmpolizaCmp.allowBlank      = true;
                nmpolizaCmp.verificarPoliza = false;
                
                nmpoliexCmp.allowBlank      = true;
                nmpoliexCmp.verificarPoliza = false;
            }
        }
    });
    
    cdtiptraCmp.on(
    {
        change : function(me,val)
        {
            if(_p54_params.CDSISROL === 'SUSCRIPTOR')
            {
                if(Number(val)===1)
                {
                    estatusCmp.setValue('2'); // para SUSCRIPTOR para EMISION el status es PENDIENTE
                }
                else
                {
                    estatusCmp.reset();
                }
            }
        }
    });
    
    cdtipsupCmp.on(
    {
        change : function(me,val)
        {
            _p54_recuperaSucursalesTramiteAgente();
        
            if(!Ext.isEmpty(val) && Number(val) === 1) // para emision no se pueden modificar los numeros de poliza 0
            {
                nmpolizaCmp.setReadOnly(true);
                nmpolizaCmp.setValue('0');
                
                nmpoliexCmp.setReadOnly(true);
                nmpoliexCmp.setValue('0');
            }
            else // para endoso se pueden modificar los numeros de poliza
            {
                nmpolizaCmp.setReadOnly(false);
                nmpolizaCmp.setValue('');
                nmpolizaCmp.isValid();
                
                nmpoliexCmp.setReadOnly(false);
                nmpoliexCmp.setValue('');
                nmpoliexCmp.isValid();                
            }
        }
    });
    
    nmpolizaCmp.on(
    {
        blur : function(me)
        {
            var val = me.getValue();
            if(!Ext.isEmpty(me) && me.verificarPoliza === true )
            {
                var ck = 'Recuperando p\u00f3liza';
                try
                {
                
                    var cdunieco = _p54_windowNuevo.down('[name=CDSUCDOC]').getValue()
                        ,cdramo  = _p54_windowNuevo.down('[name=CDRAMO]').getValue();
                    
                    if(Ext.isEmpty(cdunieco)||Ext.isEmpty(cdramo))
                    {
                        throw 'Seleccione sucursal y producto antes de confirmar la p\u00f3liza';
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
                                    if(json.params.CDTIPSIT !== _fieldByName('CDTIPSIT' , _p54_windowNuevo).getValue())
                                    {
                                        throw 'La p\u00f3liza no corresponde al subramo seleccionado';
                                    }
                                    
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
                                                    
                                                    var agenteCmp = me.up('window').down('[name=CDAGENTE]');
                                                    
                                                    _setValueCampoAgente(agenteCmp,json.params.CDAGENTE);
                                                    
                                                    estadoCmp.setValue('M');
                                                    
                                                    _fieldByName('CDTIPSUPEND' , _p54_windowNuevo).getStore().load(
                                                    {
                                                        params :
                                                        {
                                                            'params.cdramo'    : json.params.CDRAMO
                                                            ,'params.cdtipsit' : json.params.CDTIPSIT
                                                        }
                                                    });
                                                }
                                            }
                                            ,{
                                                text     : 'Cancelar'
                                                ,icon    : '${icons}cancel.png'
                                                ,handler : function(bot)
                                                {
                                                    me.setValue('');
                                                    estadoCmp.reset();
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
                                    estadoCmp.reset();
                                    me.isValid();
                                }
                            }
                            catch(e)
                            {
                                me.setValue('');
                                estadoCmp.reset();
                                me.isValid();
                                manejaException(e,ck);
                            }
                        }
                        ,failure : function()
                        {
                            _setLoading(false,_p54_windowNuevo);
                            me.setValue('');
                            estadoCmp.reset();
                            me.isValid();
                            errorComunicacion(null,'Error Recuperando p\u00f3liza');
                        }
                    });
                }
                catch(e)
                {
                    _setLoading(false,_p54_windowNuevo);
                    me.setValue('');
                    estadoCmp.reset();
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
    
    var ck = 'Agregando comportamiento adicional a componente de nivel de ventana emergente';
    try
    {
        var cdflujoComp = _p54_windowNuevo.down('[name=CDFLUJOMC]');
        cdflujoComp.on(
        {
            select : function(me,records)
            {
                _p54_windowNuevo.down('[name=CDRAMO]').store.proxy.extraParams['params.tipogrupo'] = records[0].get('aux2');
                
                _p54_windowNuevo.down('[name=CDTIPSIT]').store.proxy.extraParams['params.tipogrupo'] = records[0].get('aux2');
                
                debug('extraParams cdramo:',_p54_windowNuevo.down('[name=CDRAMO]').store.proxy.extraParams);
                
                debug('extraParams cdtipsit:',_p54_windowNuevo.down('[name=CDTIPSIT]').store.proxy.extraParams);
                            
                _p54_windowNuevo.onLevelChange(2);
            }
        });
    }
    catch(e)
    {
        manejaException(e,ck);
    }
    
    nmpoliexCmp.on(
    {
        blur : function(me)
        {
            var val = me.getValue();
            if(!Ext.isEmpty(val) && me.verificarPoliza === true)
            {
                var ck = 'Recuperando p\u00f3liza';
                try
                {
                    var cduniext  = _p54_windowNuevo.down('[name=CDUNIEXT]').getValue()
                        ,ramo     = _p54_windowNuevo.down('[name=RAMO]').getValue()
                        ,cdtiptra = _p54_windowNuevo.down('[name=CDTIPTRA]').getValue()
                        ,agente   = _p54_windowNuevo.down('[name=CDAGENTE]').getValue();
                    
                    if(Ext.isEmpty(cduniext))
                    {
                        throw 'Seleccione la sucursal antes de confirmar la p\u00f3liza';
                    }
                    
                    if(Ext.isEmpty(ramo))
                    {
                        throw 'Seleccione el ramo antes de confirmar la p\u00f3liza';
                    }
                    
                    if(Ext.isEmpty(agente))
                    {
                        throw 'Seleccione el agente antes de confirmar la p\u00f3liza';
                    }
                    
                    if(Number(cdtiptra) === 15) //endoso
                    {
                        ck = 'Recuperando p\u00f3liza ICE';
                        
	                    _setLoading(true,_p54_windowNuevo);
	                    Ext.Ajax.request(
	                    {
	                        url      : _p54_urlRecuperarPolizaDanios
	                        ,params  :
	                        {
	                            'params.CDUNIEXT'  : cduniext
	                            ,'params.RAMO'     : ramo
	                            ,'params.NMPOLIEX' : val
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
	                                                ,value      : json.params.CDUNIEXT
	                                            }
	                                            ,{
	                                                xtype       : 'displayfield'
	                                                ,fieldLabel : 'RAMO'
	                                                ,value      : json.params.RAMO
	                                            }
	                                            ,{
	                                                xtype       : 'displayfield'
	                                                ,fieldLabel : 'P\u00d3LIZA'
	                                                ,value      : json.params.NMPOLIEX
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
	                                                    
	                                                    var setear = function(name,val,cb) // funcion que setea, simple o lanzando herencia
	                                                    {
	                                                        debug('setear args:',name,val,'.');
	                                                        var comp = me.up('window').down('[name='+name+']');
	                                                        if(typeof comp.heredar === 'function')
	                                                        {
	                                                            comp.heredar(
	                                                                true
	                                                                ,function()
	                                                                {
	                                                                    comp.setValue(val);
	                                                                    cb();
	                                                                }
	                                                            );
	                                                        }
	                                                        else
	                                                        {
	                                                            comp.setValue(val);
	                                                            cb();
	                                                        }
	                                                    };
	                                                    
	                                                    _mask('Recuperando propiedades...');
	                                                    setear(
	                                                        'CDSUCADM'
	                                                        ,json.params.CDUNIECO
	                                                        ,function()
	                                                        {
	                                                            setear(
	                                                                'CDSUCDOC'
	                                                                ,json.params.CDUNIECO
	                                                                ,function()
	                                                                {
	                                                                    setear(
	                                                                        'CDRAMO'
	                                                                        ,json.params.CDRAMO
	                                                                        ,function()
	                                                                        {
	                                                                            setear(
	                                                                                'CDTIPSIT'
	                                                                                ,json.params.CDTIPSIT
	                                                                                ,function()
	                                                                                {
	                                                                                    setear(
	                                                                                        'NMPOLIZA'
	                                                                                        ,json.params.NMPOLIZA
	                                                                                        ,function()
	                                                                                        {
	                                                                                            _fieldByName('CDTIPSUPEND' , _p54_windowNuevo).getStore().load(
	                                                                                            {
	                                                                                                params :
	                                                                                                {
	                                                                                                    'params.cdramo'    : json.params.CDRAMO
	                                                                                                    ,'params.cdtipsit' : json.params.CDTIPSIT
	                                                                                                }
	                                                                                            });
	                                                                                            _unmask();
	                                                                                        }
	                                                                                    );
	                                                                                }
	                                                                            );
	                                                                        }
	                                                                    );
	                                                                }
	                                                            );
	                                                        }
	                                                    );
	                                                    
	                                                }
	                                            }
	                                            ,{
	                                                text     : 'Cancelar'
	                                                ,icon    : '${icons}cancel.png'
	                                                ,handler : function(bot)
	                                                {
	                                                    me.setValue('');
	                                                    estadoCmp.reset();
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
	                                    estadoCmp.reset();
	                                    me.isValid();
	                                }
	                            }
	                            catch(e)
	                            {
	                                me.setValue('');
	                                estadoCmp.reset();
	                                me.isValid();
	                                manejaException(e,ck);
	                            }
	                        }
	                        ,failure : function()
	                        {
	                            _setLoading(false,_p54_windowNuevo);
	                            me.setValue('');
	                            estadoCmp.reset();
	                            me.isValid();
	                            errorComunicacion(null,'Error Recuperando p\u00f3liza');
	                        }
	                    });
                    }
                    else if(Number(cdtiptra) === 21)//renovacion
                    {
                        ck = 'Recuperando p\u00f3liza SIGS';
                        
                        var cdflujoCmp = _p54_windowNuevo.down('[name=CDFLUJOMC]');
                        debug('cdflujoCmp:',cdflujoCmp,'.');
                        
                        var cdflujoRec = cdflujoCmp.findRecord('key',cdflujoCmp.getValue());
                        debug('cdflujoRec:',cdflujoRec,'.');
                        
                        var dsflujo = cdflujoRec.get('value');
                        
                        debug('dsflujo:',dsflujo,'.');
                        
                        debug("dsflujo.toUpperCase().indexOf('PYME'):",dsflujo.toUpperCase().indexOf('PYME'),'.');
                        
                        debug("dsflujo.toUpperCase().indexOf('FLOTILLA'):",dsflujo.toUpperCase().indexOf('FLOTILLA'),'.');
                        
                        var tipoflot = 'I';
                        
                        if(dsflujo.toUpperCase().indexOf('PYME')!=-1)
                        {
                            tipoflot = 'P';
                        }
                        else if(dsflujo.toUpperCase().indexOf('FLOTILLA')!=-1)
                        {
                            tipoflot = 'F';
                        }
                        
                        debug('tipoflot:',tipoflot,'.');
                        
                        _setLoading(true,_p54_windowNuevo);
                        Ext.Ajax.request(
                        {
                            url      : _p54_urlRecuperarPolizaSIGS
                            ,params  :
                            {
                                'smap1.cdsucursal' : cduniext
                                ,'smap1.cdramo'    : ramo
                                ,'smap1.cdpoliza'  : val
                                ,'smap1.cdusuari'  : _p54_params.CDUSUARI
                                ,'smap1.tipoflot'  : tipoflot
                            }
                            ,success : function(response)
                            {
                                _setLoading(false,_p54_windowNuevo);
                                var ck = 'Decodificando respuesta al recuperar p\u00f3liza SIGS';
                                try
                                {
                                    var json = Ext.decode(response.responseText);
                                    debug('### poliza SIGS:',json);
                                    if(json.success==true && !Ext.isEmpty(json.smap1.valoresCampos))
                                    {
                                        var jsonSIGS = Ext.decode(json.smap1.valoresCampos);
                                        
                                        debug('jsonSIGS:',jsonSIGS);
                                        
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
                                                    ,value      : json.smap1.cdsucursal
                                                }
                                                ,{
                                                    xtype       : 'displayfield'
                                                    ,fieldLabel : 'RAMO'
                                                    ,value      : json.smap1.cdramo
                                                }
                                                ,{
                                                    xtype       : 'displayfield'
                                                    ,fieldLabel : 'P\u00d3LIZA'
                                                    ,value      : json.smap1.cdpoliza
                                                }
                                                ,{
                                                    xtype       : 'displayfield'
                                                    ,fieldLabel : 'FECHA DE EMISI\u00d3N'
                                                    ,value      : jsonSIGS.smap1.FESOLICI
                                                }
                                                ,{
                                                    xtype       : 'displayfield'
                                                    ,fieldLabel : 'INICIO DE VIGENCIA'
                                                    ,value      : jsonSIGS.smap1.FEEFECTO
                                                }
                                                ,{
                                                    xtype       : 'displayfield'
                                                    ,fieldLabel : 'CONTRATANTE'
                                                    ,value      : jsonSIGS.smap1.nombreContratante
                                                    
                                                }
                                                ,{
                                                    xtype       : 'displayfield'
                                                    ,fieldLabel : 'AGENTE'
                                                    ,value      : jsonSIGS.smap1.cdagente+' - '+jsonSIGS.smap1.nombreAgente
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
                                                        
                                                        var setear = function(name,val,cb) // funcion que setea, simple o lanzando herencia
                                                        {
                                                            debug('setear args:',name,val,'.');
                                                            var comp = me.up('window').down('[name='+name+']');
                                                            if(typeof comp.heredar === 'function')
                                                            {
                                                                comp.heredar(
                                                                    true
                                                                    ,function()
                                                                    {
                                                                        comp.setValue(val);
                                                                        cb();
                                                                    }
                                                                );
                                                            }
                                                            else
                                                            {
                                                                comp.setValue(val);
                                                                cb();
                                                            }
                                                        };
                                                        
                                                        _mask('Recuperando propiedades...');
                                                        setear(
                                                            'CDSUCADM'
                                                            ,me.up('window').down('[name=CDSUCADM]').getValue()
                                                            ,function()
                                                            {
                                                                setear(
                                                                    'CDSUCDOC'
                                                                    ,me.up('window').down('[name=CDSUCDOC]').getValue()
                                                                    ,function()
                                                                    {
                                                                        setear(
                                                                            'CDRAMO'
                                                                            ,jsonSIGS.smap1.cdramo
                                                                            ,function()
                                                                            {
                                                                                setear(
                                                                                    'CDTIPSIT'
                                                                                    ,jsonSIGS.smap1.cdtipsit
                                                                                    ,function()
                                                                                    {
                                                                                        setear(
                                                                                            'NMPOLIZA'
                                                                                            ,'0'
                                                                                            ,function()
                                                                                            {
                                                                                                _unmask();
                                                                                                
                                                                                                var agente = _fieldLikeLabel('AGENTE').getValue();
                                                                                                
                                                                                                if(Number(agente) !== Number(jsonSIGS.smap1.cdagente) )
                                                                                                {
                                                                                                    _fieldByName('NMPOLIEX' , _p54_windowNuevo).reset();
                                                                                                    mensajeError('La p\u00f3liza no pertenece al agente<br/>Por favor seleccione a '+jsonSIGS.smap1.cdagente+' - '+jsonSIGS.smap1.nombreAgente+' e intente de nuevo');
                                                                                                }
                                                                                            }
                                                                                        );
                                                                                    }
                                                                                );
                                                                            }
                                                                        );
                                                                    }
                                                                );
                                                            }
                                                        );
                                                        
                                                    }
                                                }
                                                ,{
                                                    text     : 'Cancelar'
                                                    ,icon    : '${icons}cancel.png'
                                                    ,handler : function(bot)
                                                    {
                                                        me.setValue('');
                                                        estadoCmp.reset();
                                                        me.isValid();
                                                        bot.up('window').destroy();
                                                    }
                                                }
                                            ]
                                        }).show());
                                    }
                                    else
                                    {
                                        mensajeError(json.message || 'No se encuentra la p\u00f3liza '+cduniext+' - '+ramo+' - '+val);
                                        me.setValue('');
                                        estadoCmp.reset();
                                        me.isValid();
                                    }
                                }
                                catch(e)
                                {
                                    me.setValue('');
                                    estadoCmp.reset();
                                    me.isValid();
                                    manejaException(e,ck);
                                }
                            }
                            ,failure : function()
                            {
                                _setLoading(false,_p54_windowNuevo);
                                me.setValue('');
                                estadoCmp.reset();
                                me.isValid();
                                errorComunicacion(null,'Error Recuperando p\u00f3liza');
                            }
                        });
                    }
                }
                catch(e)
                {
                    _setLoading(false,_p54_windowNuevo);
                    me.setValue('');
                    estadoCmp.reset();
                    me.isValid();
                    manejaException(e,ck);
                }
            }
        }
    });
    
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
        
        var values = form.getValues();
        if(Number(values.CDTIPTRA) === 15 && Number(values.CDTIPSUP) === 0)
        {
            debug('se reemplaza el cdtipsup 0 por ',values.CDTIPSUPEND,'.');
            values.CDTIPSUP = values.CDTIPSUPEND;
        }
        
        debug('values:',values,'.');
        
        _setLoading(true,form);
        Ext.Ajax.request(
        {
            url      : _p54_urlRegistrarTramite
            ,params  : _formValuesToParams(values)
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
    debug('>_p54_recuperarSucursalAgente cdagente:',cdagente,'callback?:',!Ext.isEmpty(callback),'.');
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
        if(_p54_params.CDSISROL === RolSistema.Agente)
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
                        sucuAdminComp.setValue(cdunieco);
                    }
                    
                    var sucuDocuComp = _fieldByLabel('SUCURSAL DOCUMENTO',_fieldById('_p54_formNuevoTramite'),true);
                    debug('sucuDocuComp:',sucuDocuComp,'.');
                    
                    if(!Ext.isEmpty(sucuDocuComp))
                    {
                        sucuDocuComp.setValue(cdunieco);
                    }
                    
                    var sucuExtComp = _fieldByName('CDUNIEXT',_fieldById('_p54_formNuevoTramite'),true);
                    debug('sucuExtComp:',sucuExtComp,'.');
                    
                    if(!Ext.isEmpty(sucuExtComp))
                    {
                        sucuExtComp.setValue(cdunieco);
                    }
                    
                    _p54_windowNuevo.triggerHerencia();
                    
                    _fieldByName('NMPOLIEX',_fieldById('_p54_formNuevoTramite')).reset();
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
    if(
        [
            RolSistema.PromotorAuto
            ,RolSistema.SuscriptorAuto
            ,RolSistema.TecnicoSuscripcionDanios
            ,RolSistema.JefeSuscripcionDanios
            ,RolSistema.GerenteSuscripcionDanios
            ,RolSistema.EmisorSuscripcionDanios
            ,RolSistema.SubdirectorSuscripcionDanios
        ].indexOf(_p54_params.CDSISROL) != -1
    )
    {
        var agenteComp = _fieldByLabel('AGENTE',_fieldById('_p54_formNuevoTramite'),true);
        
        if(!Ext.isEmpty(agenteComp))
        {
            _p54_windowNuevo.on(
            {
                show : function(me)
                {
                    mensajeWarning('Al seleccionar un agente se recuperar\u00e1 su sucursal');
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
                    
                                var sucuExtComp = _fieldByName('CDUNIEXT',_fieldById('_p54_formNuevoTramite'),true);
                                debug('sucuExtComp:',sucuExtComp,'.');
                    
                                if(!Ext.isEmpty(sucuExtComp))
                                {
                                    sucuExtComp.setValue(cdunieco);
                                }
			                    
			                    _p54_windowNuevo.triggerHerencia();
			                    
			                    _fieldByName('NMPOLIEX',_fieldById('_p54_formNuevoTramite')).reset();
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

function _hide(comp)
{
    debug('_hide comp:',comp,'.');
    if(!Ext.isEmpty(comp) && typeof comp === 'object')
    {
        //comp.addCls('red');
        //comp.removeCls('green');
        comp.hide();
    }
}

function _show(comp)
{
    debug('_show comp:',comp,'.');
    if(!Ext.isEmpty(comp) && typeof comp === 'object')
    {
        //comp.addCls('green');
        //comp.removeCls('red');
        comp.show();
    }
}
////// funciones //////
</script>
</head>
<body>
<div id="_p54_divpri" style="height:900px;border:0px solid #CCCCCC;"></div>
</body>
</html>