<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script>
////// urls //////
var _p51_urlRecuperacion = '<s:url namespace="/recuperacion" action="recuperar" />';
////// urls //////

////// variables //////
var _p51_params = <s:property value="%{convertToJSON('params')}" escapeHtml="false" />;
debug('_p51_params:',_p51_params);

var _p51_storeIncSuc
    ,_p51_storeIncAge
    ,_p51_storeExcSuc
    ,_p51_storeExcAge;
////// variables //////

////// overrides //////
////// overrides //////

////// componentes dinamicos /////
var _p51_formBusqItems = [<s:property value="items.itemsFormBusq"     escapeHtml="false" />];
////// componentes dinamicos //////

Ext.onReady(function()
{
    ////// requires //////
    ////// requires //////

    ////// modelos //////
    Ext.define('_p51_modelo',
    {
        extend  : 'Ext.data.Model'
        ,fields :
        [
            'tipo' //[S (sucursal) / A (agente)]
            ,'cdusuari'
            ,'cdunieco'
            ,'cdtipram'
            ,'cduniecoPer'
            ,'cdagentePer'
            ,'funcion' //[S/N]
        ]
    });
    ////// modelos //////
    
    ////// stores //////
    _p51_storeIncSuc = Ext.create('Ext.data.Store',
    {
        model : '_p51_modelo'
        ,data : []
    });
    _p51_storeIncAge = Ext.create('Ext.data.Store',
    {
        model : '_p51_modelo'
        ,data : []
    });
    _p51_storeExcSuc = Ext.create('Ext.data.Store',
    {
        model : '_p51_modelo'
        ,data : []
    });
    _p51_storeExcAge = Ext.create('Ext.data.Store',
    {
        model : '_p51_modelo'
        ,data : []
    });
    ////// stores //////
    
    ////// componentes //////
    ////// componentes //////
    
    ////// contenido //////
    Ext.create('Ext.panel.Panel',
    {
        renderTo  : '_p51_divpri'
        ,itemId   : '_p51_panelpri'
        ,defaults : { style : 'margin:5px;' }
        ,border   : 0
        ,layout   :
        {
            type     : 'table'
            ,columns : 2
        }
        ,items    :
        [
            Ext.create('Ext.form.Panel',
            {
                itemId       : '_p51_formBusq'
                ,title       : 'B\u00DASQUEDA '
                ,defaults    : { style : 'margin:5px;' }
                ,items       : _p51_formBusqItems
                ,colspan     : 2
                ,width       : 800
                ,layout      :
                {
                    type     : 'table'
                    ,columns : 2
                }
                ,buttonAlign : 'center'
                ,buttons     :
                [
                    {
                        text     : 'Buscar'
                        ,name    : 'botonbuscar'
                        ,icon    : '${icons}zoom.png'
                        ,handler : function(me)
                        {
                            var ck = 'Buscando recibos';
                            try
                            {
                                var form = me.up('form');
                                
                                if(!form.isValid())
                                {
                                    throw 'Favor de capturar todos los campos requeridos';
                                }
                            }
                            catch(e)
                            {
                                manejaException(e,ck);
                            }
                        }
                    }
                    ,{
                        text     : 'Limpiar'
                        ,name    : 'botonlimpiar'
                        ,icon    : '${icons}arrow_refresh.png'
                        ,handler : function(me)
                        {
                            me.up('form').getForm().reset();
                            _p51_storeIncSuc.removeAll();
                            _p51_storeIncAge.removeAll();
                            _p51_storeExcSuc.removeAll();
                            _p51_storeExcAge.removeAll();
                        }
                    }
                ]
            })
            ,Ext.create('Ext.grid.Panel',
            {
                itemId   : '_p51_gridIncSuc'
                ,title   : 'SOLO IMPRIMIR SUCURSALES:'
                ,store   : _p51_storeIncSuc
                ,width   : 350
                ,height  : 200
                ,columns :
                [
                    {
                        text       : 'SUCURSAL'
                        ,dataIndex : 'cduniecoPer'
                        ,flex      : 1
                    }
                ]
            })
            ,Ext.create('Ext.grid.Panel',
            {
                itemId   : '_p51_gridIncAge'
                ,title   : 'SOLO IMPRIMIR AGENTES:'
                ,store   : _p51_storeIncAge
                ,width   : 350
                ,height  : 200
                ,columns :
                [
                    {
                        text       : 'AGENTE'
                        ,dataIndex : 'cdagentePer'
                        ,flex      : 1
                    }
                ]
            })
            ,Ext.create('Ext.grid.Panel',
            {
                itemId   : '_p51_gridExtSuc'
                ,title   : 'DESCARTAR SUCURSALES:'
                ,store   : _p51_storeExcSuc
                ,width   : 350
                ,height  : 200
                ,columns :
                [
                    {
                        text       : 'SUCURSAL'
                        ,dataIndex : 'cduniecoPer'
                        ,flex      : 1
                    }
                ]
            })
            ,Ext.create('Ext.grid.Panel',
            {
                itemId   : '_p51_gridExtAge'
                ,title   : 'DESCARTAR AGENTES:'
                ,store   : _p51_storeExcAge
                ,width   : 350
                ,height  : 200
                ,columns :
                [
                    {
                        text       : 'AGENTE'
                        ,dataIndex : 'cdagentePer'
                        ,flex      : 1
                    }
                ]
            })
        ]
    });
    ////// contenido //////
    
    ////// custom //////
    _fieldByName('cdusuari').on(
    {
        select : function(me,selected)
        {
            var records = selected[0];
            debug('rec.',record);
            _fieldByName('cdunieco').setValue(rec.get('aux'));
        }
    });
    ////// custom //////
    
    ////// loaders //////
    ////// loaders //////
});

////// funciones //////
////// funciones //////
</script>
</head>
<body>
<div id="_p51_divpri" style="height:600px;border:1px solid #CCCCCC;"></div>
</body>
</html>