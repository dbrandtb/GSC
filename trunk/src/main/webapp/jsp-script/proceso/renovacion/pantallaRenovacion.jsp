<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
////// variables //////
var _p24_urlBuscarPolizas = '<s:url namespace="/renovacion" action="buscarPolizasRenovables" />';

var _p24_storePolizas;
////// variables //////

Ext.onReady(function()
{
    ////// modelos //////
    Ext.define('_p24_modeloPoliza',
    {
        extend : 'Ext.data.Model'
    });
    ////// modelos //////
    
    ////// stores //////
    _p24_storePolizas = Ext.create('Ext.data.Store',
    {
        model     : '_p24_modeloPoliza'
        ,autoLoad : false
        ,proxy    :
        {
            type    : 'ajax'
            ,url    : _p24_urlBuscarPolizas
            ,reader :
            {
                type             : 'json'
                ,root            : 'slist1'
                ,messageProperty : 'respuesta'
            }
        }
    });
    ////// stores //////
    
    ////// componentes //////
    ////// componentes //////
    
    ////// contenido //////
    
    Ext.create('Ext.panel.Panel',
    {
        renderTo  : '_p24_divpri'
        ,defaults : { style : 'margin : 5px;' }
        ,border   : 0
        ,items    :
        [
            Ext.create('Ext.form.Panel',
            {
                title     : 'Buscar p&oacute;lizas a renovar'
                ,defaults : { style : 'margin : 5px;' }
                ,layout   : 'hbox'
                ,items    :
                [
                    <s:property value="imap.busquedaItems" escapeHtml="false" />
                    ,Ext.create('Ext.button.Button',
                    {
                        text     : 'Buscar'
                        ,icon    : '${ctx}/resources/fam3icons/icons/zoom.png'
                        ,handler : _p24_buscarClic
                    })
                ]
            })
            ,Ext.create('Ext.grid.Panel',
            {
                title      : 'Resultados'
                ,selType   : 'checkboxmodel'
                ,store     : _p24_storePolizas
                ,minHeight : 150
                ,maxHeight : 400
                ,columns   : []
            })
        ]
    });
    ////// contenido //////
    
    ////// custom //////
    ////// custom //////
    
    ////// loaders //////
    ////// loaders //////
});

////// funciones //////
function _p24_buscarClic(button,e)
{
    debug('>_p24_buscarClic');
    _p24_storePolizas.load(
    {
        params :
        {
            'smap1.anio' : _fieldByName('anio').getValue()
            ,'smap1.mes' : _fieldByName('mes').getValue()
        }
        ,callback : _p24_storePolizasLoadCallback
    });
    debug('<_p24_buscarClic');
}

function _p24_storePolizasLoadCallback(records,op,success)
{
    debug('>_p24_storePolizasLoad',records,success,'dummy');
    if(!success)
    {
        var error=op.getError();
        debug('error:',error);
        if(typeof error=='object')
        {
            errorComunicacion();
        }
        else
        {
            mensajeError(error);
        }
    }
    debug('>_p24_storePolizasLoad');
}
////// funciones //////
</script>
</head>
<body><div id="_p24_divpri" style="height:600px;"></div></body>
</html>