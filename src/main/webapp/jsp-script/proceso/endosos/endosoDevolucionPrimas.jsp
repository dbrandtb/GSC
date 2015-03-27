<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
////// urls //////
////// urls //////

////// variables //////
var _p39_smap1  = <s:property value="%{convertToJSON('smap1')}"  escapeHtml="false" />;
var _p39_slist1 = <s:property value="%{convertToJSON('slist1')}" escapeHtml="false" />;
debug('_p39_smap1:'  , _p39_smap1);
debug('_p39_slist1:' , _p39_slist1);
////// variables //////

////// overrides //////
////// overrides //////

////// dinamicos //////
var _p39_incisoColumns    = [ <s:property value="imap.incisoColumns"    escapeHtml="false" /> ];
var _p39_coberturaColumns = [ <s:property value="imap.coberturaColumns" escapeHtml="false" /> ];
////// dinamicos //////

Ext.onReady(function()
{
    ////// modelos //////
    ////// modelos //////
    
    ////// stores //////
    ////// stores //////
    
    ////// componentes //////
    ////// componentes //////
    
    ////// contenido //////
    Ext.create('Ext.panel.Panel',
    {
        renderTo  : '_p39_divpri'
        ,itemId   : '_p39_panelpri'
        ,border   : 0
        ,defaults : { style : 'margin:5px;' }
        ,items    :
        [
            Ext.create('Ext.grid.Panel',
            {
                itemId     : '_p39_gridIncisos'
                ,title     : 'Incisos'
                ,minHeight : 150
                ,columns   : _p39_incisoColumns
            })
            ,Ext.create('Ext.grid.Panel',
            {
                itemId     : '_p39_gridCoberturas'
                ,title     : 'Coberturas'
                ,minHeight : 150
                ,columns   : _p39_coberturaColumns
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
////// funciones //////
</script>
</head>
<body><div id="_p39_divpri" style="height:500px;border:1px solid #999999;"></div></body>
</html>