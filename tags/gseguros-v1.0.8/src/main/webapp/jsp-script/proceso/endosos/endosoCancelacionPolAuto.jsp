<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
////// urls //////
var _p43_urlMarcarPolizaCancelar = '<s:url namespace="/endosos"     action="marcarPolizaCancelarPorEndoso"     />';
var _p43_urlConfirmar            = '<s:url namespace="/endosos"     action="confirmarEndosoCancelacionPolAuto" />';
var _p43_urlValidaCancProrrata   = '<s:url namespace="/cancelacion" action="validaCancelacionAProrrata"        />';
////// urls //////

////// variables //////
var _p43_smap1 = <s:property value="%{convertToJSON('smap1')}"  escapeHtml="false" />;
debug('_p43_smap1:',_p43_smap1);
////// variables //////

////// overrides //////
////// overrides //////

////// componentes dinamicos //////
var _p43_panelLecturaItems = [ <s:property value="imap.panelLecturaItems" escapeHtml="false" /> ];
var _p43_formEndosoItems   = [ <s:property value="imap.formEndosoItems"   escapeHtml="false" /> ];
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
        renderTo  : '_p43_divpri'
        ,itemId   : '_p43_panelpri'
        ,border   : 0
        ,defaults : { style : 'margin:5px;' }
        ,items    :
        [
            Ext.create('Ext.panel.Panel',
            {
                title     : 'Datos de p&oacute;liza'
                ,defaults : { style : 'margin:5px;' }
                ,layout   :
                {
                    type     : 'table'
                    ,columns : 2
                }
                ,items    : _p43_panelLecturaItems
            })
            ,Ext.create('Ext.form.Panel',
            {
                title     : 'Datos de endoso'
                ,defaults : { style : 'margin:5px;' }
                ,layout   :
                {
                    type     : 'table'
                    ,columns : 2
                }
                ,items       : _p43_formEndosoItems
                ,buttonAlign : 'center'
                ,buttons     :
                [
                    {
                        itemId   : '_p43_botonConfirmar'
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
                                url      : _p43_urlConfirmar
                                ,params  :
                                {
                                    'smap1.cdunieco'  : _p43_smap1.CDUNIECO
                                    ,'smap1.cdramo'   : _p43_smap1.CDRAMO
                                    ,'smap1.estado'   : _p43_smap1.ESTADO
                                    ,'smap1.nmpoliza' : _p43_smap1.NMPOLIZA
                                    ,'smap1.cdrazon'  : _fieldByName('cdrazon').getValue()
                                    ,'smap1.feefecto' : _p43_smap1.FEEFECTO
                                    ,'smap1.fevencim' : _p43_smap1.FEVENCIM
                                    ,'smap1.fecancel' : Ext.Date.format(_fieldByName('fecancel').getValue(),'d/m/Y')
                                    ,'smap1.cdtipsup' : _p43_smap1.cdtipsup
                                }
                                ,success : function(response)
                                {
                                    me.setText('Confirmar');
                                    me.enable();
                                    var json = Ext.decode(response.responseText);
                                    debug('### confirmar:',json);
                                    if(json.success)
                                    {
                                        mensajeCorrecto('Endoso generado','Endoso generado');
                                        marendNavegacion(2);
                                    }
                                    else
                                    {
                                        mensajeError(json.respuesta);
                                    }
                                }
                                ,failure : function()
                                {
                                    me.setText('Confirmar');
                                    me.enable();
                                }
                            });
                        }
                    }
                ]
            })
        ]
    })
    ////// contenido //////
    
    ////// custom //////
    _fieldByName('cdrazon').on(
    {
        select : function(me,rec)
        {
            debug('rec:',rec);
            if(Number(rec[0].get('key'))==25)
            {
	            Ext.Ajax.request(
	            {
	                url     : _p43_urlValidaCancProrrata
	                ,params :
	                {
	                    'smap1.cdunieco'  : _p43_smap1.CDUNIECO
	                    ,'smap1.cdramo'   : _p43_smap1.CDRAMO
	                    ,'smap1.estado'   : _p43_smap1.ESTADO
	                    ,'smap1.nmpoliza' : _p43_smap1.NMPOLIZA
	                }
	                ,success : function(response)
	                {
	                    var json = Ext.decode(response.responseText);
	                    if(json.success)
	                    {
	                    }
	                    else
	                    {
	                        me.reset();
	                        mensajeError(json.respuesta);
	                    }
	                }
	                ,failure : function()
	                {
	                    me.reset();
	                    errorComunicacion();
	                }
	            });
            }
        }
    });
    ////// custom //////
    
    ////// loaders //////
    Ext.Ajax.request(
    {
        url      : _p43_urlMarcarPolizaCancelar
        ,params  :
        {
            'smap1.cdunieco'  : _p43_smap1.CDUNIECO
            ,'smap1.cdramo'   : _p43_smap1.CDRAMO
            ,'smap1.nmpoliza' : _p43_smap1.NMPOLIZA
        }
        ,success : function(response)
        {
            var json = Ext.decode(response.responseText);
            debug('### marcar poliza:',json);
            if(json.success)
            {
                _fieldByName('fecancel').setValue(json.smap1.FECANCEL);
                _p43_smap1['FEEFECTO'] = json.smap1.FEEFECTO;
                _p43_smap1['FEVENCIM'] = json.smap1.FEVENCIM;
                debug('_p43_smap1:',_p43_smap1);
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
<body><div id="_p43_divpri" style="height:250px;border:1px solid #999999;"></div></body>
</html>