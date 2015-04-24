<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
////// urls //////
var _p44_urlConfirmar          = '<s:url namespace="/endosos" action="confirmarEndosoValositFormsAuto" />';
var _p44_urlRecuperacionSimple = '<s:url namespace="/emision" action="recuperacionSimple"              />';
////// urls //////

////// variables //////
var _p44_smap1 = <s:property value="%{convertToJSON('smap1')}"  escapeHtml="false" />;
debug('_p44_smap1:',_p44_smap1);

var _p44_slist1 = <s:property value="%{convertToJSON('slist1')}"  escapeHtml="false" />;
debug('_p44_slist1:',_p44_slist1);
////// variables //////

////// overrides //////
////// overrides //////

////// componentes dinamicos //////
var _p44_items      = [];
var _p44_itemsArray = [];
<s:set var="contador" value="0" />
<s:iterator value="slist1">
    _p44_itemsArray['i0000<s:property value="slist1[#contador].NMSITUAC" />']=[ <s:property value='imap.get("items"+slist1[#contador].NMSITUAC)' /> ];
    <s:set var="contador" value="#contador+1" />
</s:iterator>
////// componentes dinamicos //////

Ext.onReady(function()
{
    ////// modelos //////
    ////// modelos //////
    
    ////// stores //////
    ////// stores //////
    
    ////// componentes //////
    for(var nmsituac in _p44_itemsArray)
    {
        var indice = Number(nmsituac.slice(-4));
        _p44_items.push(Ext.create('Ext.form.Panel',
        {
            title     : 'INCISO '+indice
            ,itemId   : '_p44_formInciso'+indice
            ,nmsituac : indice
            ,defaults : { style : 'margin:5px;' }
            ,layout   :
            {
                type     : 'table'
                ,columns : 3
            }
            ,items : _p44_itemsArray[nmsituac]
        }));
    }
    debug('_p44_items:',_p44_items);
    ////// componentes //////
    
    ////// contenido //////
    Ext.create('Ext.panel.Panel',
    {
        renderTo    : '_p44_divpri'
        ,itemId     : '_p44_panelpri'
        ,defaults   : { style : 'margin:5px;' }
        ,maxHeight  : 750
        ,autoScroll : true
        ,items      : _p44_items
        ,bbar       :
        [
            '->'
            ,{
                xtype     : 'form'
                ,layout   : 'hbox'
                ,defaults : { style : 'margin:5px;' }
                ,items    :
                [
                    {
		                xtype       : 'datefield'
		                ,itemId     : '_p44_fechaCmp'
		                ,format     : 'd/m/Y'
		                ,fieldLabel : 'Fecha de efecto'
		                ,value      : new Date()
		                ,allowBlank : false
                    }
                    ,{
                        xtype : 'button'
                        ,text     : 'Confirmar'
		                ,itemId  : '_p44_botonConfirmar'
		                ,icon    : '${ctx}/resources/fam3icons/icons/key.png'
		                ,handler : function(me)
		                {
		                    var forms = Ext.ComponentQuery.query('form[nmsituac]',_fieldById('_p44_panelpri'));
		                    debug('forms:',forms);
		                    
		                    for(var i in forms)
		                    {
		                        if(!forms[i].getForm().isValid())
		                        {
		                            mensajeWarning('Verificar inciso '+forms[i].nmsituac);
		                            return;
		                        }
		                    }
		                    
		                    if(!_fieldById('_p44_fechaCmp').isValid())
		                    {
		                        mensajeWarning('Verificar fecha');
		                        return;
		                    }
		                    
		                    var json =
		                    {
		                        smap1   : _p44_smap1
		                        ,slist1 : []
		                    };
		                    
		                    json.smap1['feinival'] = Ext.Date.format(_fieldById('_p44_fechaCmp').getValue(),'d/m/Y');
		                    
		                    for(var i in forms)
		                    {
		                        var form   = forms[i];
		                        var inciso =
		                        {
		                            cdunieco  : form.down('[name=CDUNIECO]').getValue()
		                            ,cdramo   : form.down('[name=CDRAMO]').getValue()
		                            ,estado   : form.down('[name=ESTADO]').getValue()
		                            ,nmpoliza : form.down('[name=NMPOLIZA]').getValue()
		                            ,nmsituac : form.down('[name=NMSITUAC]').getValue()
		                            ,nmsuplem : form.down('[name=NMSUPLEM]').getValue()
		                            ,cdtipsit : form.down('[name=CDTIPSIT]').getValue()
		                        };
		                        var items = Ext.ComponentQuery.query('[name][hidden=false][readOnly=false]',form);
		                        debug('items:',items);
		                        for(var j in items)
		                        {
		                            var item = items[j];
		                            inciso['OTVALOR'+item.orden] = item.getValue();
		                        }
		                        
		                        json.slist1.push(inciso);
		                    }
		                    
		                    debug('json a enviar:',json);
		                    
		                    me.disable();
		                    me.setText('Cargando...');
		                    Ext.Ajax.request(
		                    {
		                        url       : _p44_urlConfirmar
		                        ,jsonData : json
		                        ,success  : function(response)
		                        {
		                            me.setText('Confirmar');
		                            me.enable();
		                            var json2 = Ext.decode(response.responseText);
		                            debug('### confirmar:',json2);
		                            if(json2.success)
		                            {
		                                mensajeCorrecto('Endoso generado','Endoso generado');
		                            }
		                            else
		                            {
		                                mensajeError(json2.respuesta);
		                            }
		                        }
		                        ,failure  : function()
		                        {
		                            me.setText('Confirmar');
		                            me.enable();
		                            errorComunicacion();
		                        }
		                    });
		                }
                    }
                ]
            }
            ,'->'
        ]
    });
    ////// contenido //////
    
    ////// custom //////
    ////// custom //////
    
    ////// loaders //////
    var forms = Ext.ComponentQuery.query('form[nmsituac]',_fieldById('_p44_panelpri'));
    debug('forms:',forms);
    for(var i in _p44_slist1)
    {
        var inciso   = _p44_slist1[i];
        var nmsituac = inciso.NMSITUAC;
        var form     = _fieldById('_p44_formInciso'+nmsituac);
        debug('form:',form);
        var items    = Ext.ComponentQuery.query('[name]',form);
        debug('items:',items);
        
        if(items.length==0)
        {
            mensajeError('El endoso no aplica para el inciso '+form.nmsituac);
            _fieldById('_p44_botonConfirmar').disable();
            _fieldById('_p44_botonConfirmar').setText('No aplica');
        }
        
        for(var j in items)
        {
            var item = items[j];
            item.setValue(inciso[item.name]);
            if(!Ext.isEmpty(item.store))
            {
                item.store.proxy.extraParams['params.cdunieco'] = inciso.CDUNIECO;
                item.store.proxy.extraParams['params.cdramo']   = inciso.CDRAMO;
                item.store.proxy.extraParams['params.estado']   = inciso.ESTADO;
                item.store.proxy.extraParams['params.nmpoliza'] = inciso.NMPOLIZA;
                item.store.proxy.extraParams['params.nmsituac'] = inciso.NMSITUAC;
                item.store.proxy.extraParams['params.nmsuplem'] = inciso.NMSUPLEM;
                debug('item con store:',item);
                
                if(Number(_p44_smap1.cdtipsup)==43
                    &&'|AR|CR|PC|PP|'.lastIndexOf('|'+inciso.CDTIPSIT+'|')!=-1
                    &&item.name=='CVE_TIPO_USO'
                    )
                {
                    debug('@CUSTOM TIPO USO:',item);
                    item.anidado                                     = true;
                    item.store.proxy.extraParams['params.cdnegocio'] = inciso.CVE_NEGOCIO+'';
                    item.store.padre                                 = item;
                    item.padre                                       = items[j-1];
                    item.heredar                                     = function()
                    {
                        this.store.load(
                        {
                            params :
                            {
                                'params.servicio' : this.padre.getValue()
                            }
                        });
                    };
                    
                    item.store.on(
                    {
                        load : function(me)
                        {
                            var padre = me.padre;
                            var value = padre.getValue();
                            if(!Ext.isEmpty(value))
                            {
                                var dentro = false;
                                me.each(function(record)
                                {
                                    if(value==record.get('key'))
                                    {
                                        dentro=true;
                                    }
                                });
                                if(!dentro)
                                {
                                    padre.clearValue();
                                }
                            }
                        }
                    });
                    
                    item.padre.hijo = item;
                    item.padre.on(
                    {
                        select : function(me)
                        {
                            me.hijo.heredar();
                        }
                    });
                    debug('item:',item);
                }
                
                if(item.anidado==true)
                {
                    item.heredar(true);
                }
                else
                {
                    item.store.load();
                }
            }
        }
    }
    
    Ext.Ajax.request(
    {
        url      : _p44_urlRecuperacionSimple
        ,params  :
        {
            'smap1.procedimiento' : 'RECUPERAR_FECHAS_LIMITE_ENDOSO'
            ,'smap1.cdunieco'     : _p44_smap1.CDUNIECO
            ,'smap1.cdramo'       : _p44_smap1.CDRAMO
            ,'smap1.estado'       : _p44_smap1.ESTADO
            ,'smap1.nmpoliza'     : _p44_smap1.NMPOLIZA
            ,'smap1.cdtipsup'     : _p44_smap1.cdtipsup
        }
        ,success : function(response)
        {
            var json = Ext.decode(response.responseText);
            debug('### fechas:',json);
            if(json.exito)
            {
                _fieldById('_p44_fechaCmp').setMinValue(json.smap1.FECHA_MINIMA);
                _fieldById('_p44_fechaCmp').setMaxValue(json.smap1.FECHA_MAXIMA);
                _fieldById('_p44_fechaCmp').setReadOnly(json.smap1.EDITABLE=='N');
                _fieldById('_p44_fechaCmp').isValid();
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
<body><div id="_p44_divpri" style="height:800px;border:1px solid #999999;"></div></body>
</html>