<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script>
////// urls //////
var _p54_urlCargar = '<s:url namespace="/flujomesacontrol" action="recuperarTramites" />';
////// urls //////

////// variables //////
var _p54_params = <s:property value="%{convertToJSON('params')}"  escapeHtml="false" />;
debug('_p54_params:',_p54_params);

var _p54_grid;
var _p54_store;

var _p54_tamanioPag = 10;
////// variables //////

////// overrides //////
////// overrides //////

////// componentes dinamicos //////
_p54_filtroItemsDin = [ <s:property value="items.filtroItems" escapeHtml="false" /> ];

_p54_filtroItems =
[
    {
        xtype       : 'textfield'
        ,fieldLabel : '_CDTIPTRA'
        ,name       : 'CDTIPTRA'
        ,value      : _p54_params.CDTIPTRA
        ,style      : 'margin:5px;'
        ,readOnly   : true
        ,hidden     : true
    }
];

for(var i in _p54_filtroItemsDin)
{
    _p54_filtroItems.push(_p54_filtroItemsDin[i]);
}
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
                'params.CDTIPTRA' : _p54_params.CDTIPTRA
                ,'params.STATUS'  : '-1'
            }
            ,reader      :
            {
                type           : 'json'
                ,root          : 'list'
                ,totalProperty : 'total'
            }
        }
    });
    _p54_store.load(
    {
        params :
        {
            page   : 1
            ,start : 0
            ,limit : _p54_tamanioPag
        }
    });
    ////// stores //////
    
    ////// componentes //////
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
                        text     : 'Buscar'
                        ,icon    : '${icons}zoom.png'
                        ,handler : function(me)
                        {
                            var ck = 'Recuperando tr\u00e1mites';
                            try
                            {
                                var form = me.up('form');
                                if(!form.isValid())
                                {
                                    throw 'Favor de revisar los datos';
                                }
                                
                                _p54_store.proxy.extraParams = _formValuesToParams(form.getValues());
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
                        text     : 'Reporte'
                        ,icon    : '${icons}printer.png'
                        ,handler : function(me)
                        {}
                    }
                ]
            })
            ,Ext.create('Ext.grid.Panel',
            {
                title   : 'Tareas'
                ,itemId : '_p54_grid'
                ,height : 420
                ,tbar   :
                [
                    {
                        text     : 'Nuevo tr\u00e1mite'
                        ,icon    : '${icons}add.png'
                        ,handler : function(me)
                        {}
                    }
                    ,'->'
                    ,{
                        xtype       : 'textfield'
                        ,fieldLabel : '<span style="color:white;">Filtro:</span>'
                        ,labelWidth : 60
                    }
                ]
                ,columns     : [ <s:property value="items.gridColumns" escapeHtml="false" /> ]
                ,store       : _p54_store
                ,dockedItems :
                [{
                    xtype        : 'pagingtoolbar'
                    ,store       : _p54_store
                    ,dock        : 'bottom'
                    ,displayInfo : true
                }]
            })
        ]
    });
    ////// contenido //////
    
    ////// custom //////
    var _p54_grid = _fieldById('_p54_grid');
    ////// custom //////
    
    ////// loaders //////
    ////// loaders //////
});
////// funciones //////
////// funciones //////
</script>
</head>
<body>
<div id="_p54_divpri" style="height:900px;border:1px solid #CCCCCC;"></div>
</body>
</html>