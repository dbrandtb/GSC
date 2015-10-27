<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<s:if test="false">
<script>
</s:if>
////// variables //////
////// variables //////

Ext.onReady(function()
{
    ////// modelos //////
    ////// modelos //////
    
    ////// stores //////
    ////// stores //////
    
    ////// componentes //////
    ////// componentes //////
    
    ////// contenido //////
    ////// contenido //////
});

////// funciones //////

function _4_calculaDetalleImpresion(v,md,rec)
{
    return 'calcular';
}

function _4_actualizarRemesaClic(bot)
{
    centrarVentanaInterna(Ext.create('Ext.window.Window',
    {
        title        : 'ACTUALIZAR REMESA'
        ,modal       : true
        ,defaults    : { style : 'margin:5px;' }
        ,closeAction : 'destroy'
        ,layout      :
        {
            type     : 'table'
            ,columns : 2
        }
        ,items    :
        [
            {
                xtype       : 'textfield'
                ,fieldLabel : 'Remesa'
                ,status     : 36
            }
            ,{
                xtype    : 'button'
                ,icon    : '${icons}sitemap_color.png'
                ,text    : 'Marcar como armada'
                ,status  : 36
                ,handler : _4_marcarRemesaClic
            }
            ,{
                xtype       : 'textfield'
                ,fieldLabel : 'Remesa'
                ,status     : 37
            }
            ,{
                xtype    : 'button'
                ,icon    : '${icons}user_comment.png'
                ,text    : 'Marcar como entrega f\u00EDsica'
                ,status  : 37
                ,handler : _4_marcarRemesaClic
            }
            ,{
                xtype       : 'textfield'
                ,fieldLabel : 'Remesa'
                ,status     : 38
            }
            ,{
                xtype    : 'button'
                ,icon    : '${icons}package.png'
                ,text    : 'Marcar como entraga paqueter\u00EDa'
                ,status  : 38
                ,handler : _4_marcarRemesaClic
            }
        ]
    }).show());
}

function _4_marcarRemesaClic(bot)
{
    alert(bot.status);
}
////// funciones //////
<s:if test="false">
</script>
</s:if>