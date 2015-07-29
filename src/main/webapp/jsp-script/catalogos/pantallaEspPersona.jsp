<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
////// urls //////
var _p47_urlGuardarPersona      = '<s:url namespace="/persona" action="guardarPantallaEspPersona" />';
var _p47_urlRecuperarEspPersona = '<s:url namespace="/persona" action="recuperarEspPersona"       />';
////// urls //////

////// variables //////
var _p47_params = <s:property value="%{convertToJSON('params')}" escapeHtml="false" />;
debug('_p47_params:',_p47_params);
////// variables //////

////// overrides //////
////// overrides //////

////// componentes dinamicos //////
var _p47_items = [<s:property value="items.items" escapeHtml="false" />];
debug('_p47_items:',_p47_items);

var _p47_buttons = [<s:property value="items.buttons" escapeHtml="false" />];
debug('_p47_buttons:',_p47_buttons);
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
    Ext.create('Ext.form.Panel',
    {
        title        : 'DATOS DE PERSONA'
        ,itemId      : '_p47_form'
        ,renderTo    : '_p47_divpri'
        ,defaults    : { style : 'margin:5px;' }
        ,layout      :
        {
            type     : 'table'
            ,columns : 2
        }
        ,items       : _p47_items
        ,buttonAlign : 'center'
        ,border      : 0
        ,buttons     : _p47_buttons
        ,height      : 295
        ,width       : 855
        ,autoScroll  : true
    });
    ////// contenido //////
    
    ////// custom //////
    ////// custom //////
    
    ////// loaders //////
    _p47_recuperarPersona(_p47_params.cdperson);
    ////// loaders //////
});

////// funciones //////
function _p47_recuperarPersona(cdperson)
{
    debug('_p47_recuperarPersona cdperson:',cdperson);
    var ck = 'Recuperando datos de persona';
    try
    {
        var form = _fieldById('_p47_form');
        form.setLoading(true);
        Ext.Ajax.request(
        {
            url     : _p47_urlRecuperarEspPersona
            ,params :
            {
                'params.cdperson' : cdperson
            }
            ,success : function(response)
            {
                form.setLoading(false);
                var ck = 'Decodificando datos de persona';
                try
                {
                    var json = Ext.decode(response.responseText);
                    debug('### recuperar persona:',json);
                    if(json.success)
                    {
                        _cargarForm(form,json.params);
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
            ,failure : function(response)
            {
                form.setLoading(false);
                errorComunicacion(null,'Error cargando datos de persona');
            }
        });
    }
    catch(e)
    {
        manejaException(e,ck);
    }
}

function _p47_guardarYCerrarVentana(me)
{
    debug('_p47_guardarYCerrarVentana');
    _p47_guardar(me,function()
    {
        _fieldById('_p47_contenedor').destroy();
    });
}

function _p47_guardar(me,callback)
{
    debug('_p47_guardar callback?',!Ext.isEmpty(callback));
    
    var ck = 'Validando datos';
    try
    {
        var form = me.up('form');
        if(!form.isValid())
        {
            throw 'Favor de revisar los datos';
        }
        
        var params = {};
        var values = form.getValues();
        for(var prop in values)
        {
            params['params.'+prop] = values[prop];
        }
        
        form.setLoading(true);
        Ext.Ajax.request(
        {
            url      : _p47_urlGuardarPersona
            ,params  : params
            ,success : function(response)
            {
                form.setLoading(false);
                var ck = 'Decodificando respuesta al guardar';
                try
                {
                    var json = Ext.decode(response.responseText);
                    if(json.success)
                    {
                        mensajeCorrecto('Datos guardados',json.message,callback);
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
                form.setLoading(false);
            }
        });
    }
    catch(e)
    {
        manejaException(e,ck);
    }
}
////// funciones //////
</script>
</head>
<body>
<div id="_p47_divpri" style="height:300px;width:850px;border:1px solid #CCCCCC;"></div>
</body>
</html>