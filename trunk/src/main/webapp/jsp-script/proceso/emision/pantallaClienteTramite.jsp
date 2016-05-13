<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script>
////// urls //////
var _p61_urlPantallaCliente = '<s:url namespace="/catalogos" action="includes/personasLoader" />';
////// urls //////

////// variables //////
////// variables //////

////// overrides //////
////// overrides //////

////// componentes dinamicos //////
////// componentes dinamicos //////

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
        renderTo  : '_p61_divpri'
        ,itemId   : '_p61_panelpri'
        ,title    : 'Cliente de tr\u00e1mite'
        ,defaults : { style : 'margin:5px;' }
        ,items    :
        [
            {
                xtype        : 'form'
                ,itemId      : '_p61_form'
                ,title       : 'Acciones'
                ,buttonAlign : 'left'
                ,buttons     :
                [
                    {
                        text  : 'Cambiar'
                        ,icon : '${icons}control_repeat_blue.png'
                    }
                ]
            }
            ,{
                xtype       : 'panel'
                ,itemId     : '_p61_panelCliente'
                ,title      : 'Cliente'
                ,height     : 600
                ,autoScroll : true
                ,loader     :
                {
                    url       : _p61_urlPantallaCliente
                    ,scripts  : true
                    ,autoLoad : true
                }
            }
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
<body>
<div id="_p61_divpri" style="height:800px;border:1px solid #CCCCCC"></div>
</body>
</html>