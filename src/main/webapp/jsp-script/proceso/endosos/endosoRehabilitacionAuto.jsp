<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
////// urls //////
var _p40_urlConfirmarEndoso    = '<s:url namespace="/endosos" action="confirmarEndosoRehabilitacionAuto" />';
var _p40_urlRecuperacionSimple = '<s:url namespace="/emision" action="recuperacionSimple"                />';
////// urls //////

////// variables //////
var _p40_smap1  = <s:property value="%{convertToJSON('smap1')}"  escapeHtml="false" />;
debug('_p40_smap1:',_p40_smap1);
////// variables //////

////// componentes dinamicos //////
var _p40_formItems = [ <s:property value='imap.itemsFormulario' escapeHtml='false' /> ];
////// componentes dinamicos //////

Ext.onReady(function()
{
    ////// overrides //////
    ////// overrides //////
    
    ////// modelos //////
    ////// modelos //////
    
    ////// stores //////
    ////// stores //////
    
    ////// componentes //////
    ////// componentes //////
    
    ////// contenido //////
    Ext.create('Ext.form.Panel',
    {
        renderTo : '_p40_divpri'
        ,itemId  : '_p40_form'
        ,layout  :
        {
            type     : 'table'
            ,columns : 2
        }
        ,border  : 0
        ,items   : _p40_formItems
        ,buttonAlign : 'center'
        ,buttons     :
        [
            {
                itemId   : '_p40_botonConfirmar'
                ,text    : 'Confirmar'
                ,icon    : '${ctx}/resources/fam3icons/icons/key.png'
                ,handler : function(me)
                {
                    if(!me.up('form').getForm().isValid())
                    {
                        datosIncompletos();
                        return;
                    }
                    
                    me.disable();
                    me.setText('Cargando...');
                    Ext.Ajax.request(
                    {
                        url       : _p40_urlConfirmarEndoso
                        ,jsonData :
                        {
                            smap1 : me.up('form').getForm().getValues()
                        }
                        ,success  : function(response)
                        {
                            me.enable();
                            me.setText('Confirmar');
                            var json = Ext.decode(response.responseText);
                            debug('### confirmar:',json);
                            if(json.success)
                            {
                                mensajeCorrecto('Endoso generado','Endoso generado');
                            }
                            else
                            {
                                mensajeWarning(json.respuesta);
                            }
                        }
                        ,failure  : function()
                        {
                            errorComunicacion();
                            me.enable();
                            me.setText('Confirmar');
                        }
                    });
                }
            }
        ]
    });
    ////// contenido //////
    
    ////// custom //////
    ////// custom //////
    
    ////// loaders //////
    Ext.Ajax.request(
    {
        url      : _p40_urlRecuperacionSimple
        ,params  :
        {
            'smap1.procedimiento' : 'RECUPERAR_FECHAS_LIMITE_ENDOSO'
            ,'smap1.cdunieco'     : _p40_smap1.CDUNIECO
            ,'smap1.cdramo'       : _p40_smap1.CDRAMO
            ,'smap1.estado'       : _p40_smap1.ESTADO
            ,'smap1.nmpoliza'     : _p40_smap1.NMPOLIZA
            ,'smap1.cdtipsup'     : _p40_smap1.cdtipsup
        }
        ,success : function(response)
        {
            var json = Ext.decode(response.responseText);
            debug('### fechas:',json);
            if(json.exito)
            {
                _fieldByName('feefecto').setMinValue(json.smap1.FECHA_MINIMA);
                _fieldByName('feefecto').setMaxValue(json.smap1.FECHA_MAXIMA);
                _fieldByName('feefecto').setReadOnly(json.smap1.EDITABLE=='N');
                _fieldByName('feefecto').isValid();
            }
            else
            {
                mensajeError(json.respuesta);
            }
        }
        ,failure : function()
        {
            errorComunicacion();
        }
    });
    ////// loaders //////
});

////// funciones //////
////// funciones //////
</script>
</head>
<body><div id="_p40_divpri" style="height:300px;border:1px solid #999999;"></div></body>
</html>