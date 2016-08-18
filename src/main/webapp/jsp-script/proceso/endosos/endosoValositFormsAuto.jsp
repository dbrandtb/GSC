<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
////// urls //////

var _p44_urlPreview            = '<s:url namespace="/endosos"      action="previewEndosoValositFormsAuto"   />';
var url_PantallaPreview        = '<s:url namespace="/endosos"      action="includes/previewEndosos"         />';
var _p44_urlConfirmar          = '<s:url namespace="/endosos"      action="confirmarEndosoValositFormsAuto" />';
var _p44_urlRecuperacionSimple = '<s:url namespace="/emision"      action="recuperacionSimple"              />';
////// urls //////

////// variables //////
var _p44_smap1 = <s:property value="%{convertToJSON('smap1')}" escapeHtml="false" />;
debug('_p44_smap1:',_p44_smap1);

var _p44_slist1 = <s:property value="%{convertToJSON('slist1')}" escapeHtml="false" />;
debug('_p44_slist1:',_p44_slist1);

var _p44_flujo = <s:property value="%{convertToJSON('flujo')}" escapeHtml="false" />;
debug('_p44_flujo:',_p44_flujo);
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
	
	Ext.Ajax.timeout = 1*60*60*1000; // 1 hora
	
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
                        ,text     : 'Emitir'
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
		                            var dis  = false;
		                            if(item.isDisabled())
		                            {
		                                dis=true;
		                                item.enable();
		                            }
		                            inciso['OTVALOR'+item.orden] = item.getValue();
		                            if(dis)
		                            {
		                                item.disable();
		                            }
		                        }
		                        
		                        json.slist1.push(inciso);
		                    }
		                    
		                    if(!Ext.isEmpty(_p44_flujo))
		                    {
		                        json.flujo = _p44_flujo;
		                    }
		                    
		                    debug('json a enviar:',json);
		                    
		                    me.disable();
		                    me.setText('Cargando...');
		                    Ext.Ajax.request(
		                    {
		                        url       : _p44_urlPreview
		                        ,jsonData : json
		                        ,success  : function(response)
		                        {
		                        	var json2 = Ext.decode(response.responseText);
		                        	debug('### response: ',response);
		                        	debug('### json2: ',json2);
									var win = Ext.create('Ext.window.Window',
								{
									title        : 'Tarifa final'
									,id          : 'tarifa'
									,autoScroll  : true
									,modal       : true
									,buttonAlign : 'center'
									,width       : 600
									,height      : 550
									,defaults    : { width: 650 }
									,closable    : false
									,autoScroll  : true
									,loader      :
										{
											url       : url_PantallaPreview
											,params   :
												{
													'smap4.nmpoliza'  : _p44_smap1.NMPOLIZA
		                                            ,'smap4.cdunieco' : _p44_smap1.CDUNIECO
		                                            ,'smap4.cdramo'   : _p44_smap1.CDRAMO
		                                            ,'smap4.estado'   : _p44_smap1.ESTADO
		                                            ,'smap4.nmsuplem' : json2.smap2.pv_nmsuplem_o
		                                            ,'smap4.nsuplogi' : json2.smap2.pv_nsuplogi_o
		                                        }
											,scripts  : true
											,autoLoad : true
									     }
									,buttons:[{
												text    : 'Confirmar endoso'
												,icon    : '${ctx}/resources/fam3icons/icons/award_star_gold_3.png'
												,handler : 
													function (me){
														me.up('window').destroy();
																				
														Ext.Ajax.request(
										                    {
										                        url       : _p44_urlConfirmar
										                        ,jsonData : json2
										                        ,success  : function(response)
										                        {
										                           // me.setText('Confirmar');
										                           // me.enable();
										                            var json3 = Ext.decode(response.responseText);
										                            debug('### confirmar:',json3);
										                            if(json3.success)
										                            {
										                                var callbackRemesa = function()
										                                {
										                                    marendNavegacion(2);
										                                };
										                                mensajeCorrecto('Endoso generado','Endoso generado',function()
										                                {
										                                    _generarRemesaClic(
										                                        true
										                                        ,_p44_smap1.CDUNIECO
										                                        ,_p44_smap1.CDRAMO
										                                        ,_p44_smap1.ESTADO
										                                        ,_p44_smap1.NMPOLIZA
										                                        ,callbackRemesa
										                                    );
										                                });
										                            }
										                            else
										                            {
										                                mensajeError(json3.respuesta);
										                            }
										                        }
										                        ,failure  : function()
										                        {
										                            //me.setText('Confirmar');
										                            //me.enable();
										                            errorComunicacion();
										                        }
										                    })
										           }
										           
											   },
											   {
												text    : 'Cancelar'
												,icon    : '${ctx}/resources/fam3icons/icons/cancel.png'
												,handler : function (me){
																me.up('window').destroy();
																marendNavegacion(2);
																}
											 } ]
							     });
							     win.show();
							     
							     								
								}
		                        ,failure  : function()
		                        {
		                            me.setText('Confirmar');
		                            me.enable();
		                            errorComunicacion();
		                        }
		                        
		                    }); 
		                    /**/
		                   
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
            item.valorInicial = inciso[item.name];
            
            if(item.hidden==false&&item.readOnly==false)
            {
	            if(_p44_smap1.TIPO_VALIDACION=='MENOR')
	            {
	                item.validator = function()
	                {
	                    var val = this.getValue();
	                    debug('validator-:',val);
	                    if(Number(this.valorInicial)<Number(val))
	                    {
	                        return 'Valor m&aacute;ximo '+this.valorInicial;
	                    }
	                    return true;
	                };
	            }
	            else if(_p44_smap1.TIPO_VALIDACION=='MAYOR')
	            {
	                item.validator = function()
	                {
	                    var val = this.getValue();
	                    debug('validator+:',val);
	                    if(Number(this.valorInicial)>Number(val))
	                    {
	                        return 'Valor m&iacute;nimo '+this.valorInicial;
	                    }
	                    return true;
	                };
	            }
            }
            
            item.extraParams =
            {
                'cdunieco'  : inciso.CDUNIECO
                ,'cdramo'   : inciso.CDRAMO
                ,'estado'   : inciso.ESTADO
                ,'nmpoliza' : inciso.NMPOLIZA
                ,'nmsituac' : inciso.NMSITUAC
                ,'nmsuplem' : inciso.NMSUPLEM
                ,'cdtipsit' : inciso.CDTIPSIT
            };
            
            if(item.param1=='amparado')
            {
                item.disable();
                try
                {
                    var params =
                    {
                        'smap1.procedimiento' : 'RECUPERAR_TATRISIT_AMPARADO'
                        ,'smap1.cdatribu'     : item.value1
                        ,'smap1.compId'       : item.id
                    };
                    for (var i in item.extraParams)
                    {
                        params['smap1.'+i] = item.extraParams[i];
                    } 
                    Ext.Ajax.request(
                    {
                        url      : _p44_urlRecuperacionSimple
                        ,params  : params
                        ,success : function(response)
                        {
                            try
                            {
                                var json = Ext.decode(response.responseText);
                                debug('### amparada:',json);
                                if(json.exito)
                                {
                                    if(json.smap1.CONTEO>0)
                                    {
                                        Ext.getCmp(json.smap1.compId).enable();
                                    }
                                }
                                else
                                {
                                    mensajeError(json.respuesta);
                                    _fieldById('_p44_botonConfirmar').disable();
                                    _fieldById('_p44_botonConfirmar').setText('Verificacion de cobertura no exitosa');
                                }
                            }
                            catch(e)
                            {
                                manejaException(e,'descodificando cobertura amparada');
                                _fieldById('_p44_botonConfirmar').disable();
                                _fieldById('_p44_botonConfirmar').setText('Error al decodificar verificacion de coberturas');
                            }
                        }
                        ,failure : function()
                        {
                            errorComunicacion();
                            _fieldById('_p44_botonConfirmar').disable();
                            _fieldById('_p44_botonConfirmar').setText('Error al solicitar verificacion de coberturas');
                        }
                    });
                }
                catch(e)
                {
                    manejaException(e,'Verificando cobertura amparada');
                    _fieldById('_p44_botonConfirmar').disable();
                    _fieldById('_p44_botonConfirmar').setText('Error al verificar coberturas');
                }
            }
            
            if(!Ext.isEmpty(item.store))
            {
                item.store.proxy.extraParams['params.cdunieco'] = inciso.CDUNIECO;
                item.store.proxy.extraParams['params.cdramo']   = inciso.CDRAMO;
                item.store.proxy.extraParams['params.estado']   = inciso.ESTADO;
                item.store.proxy.extraParams['params.nmpoliza'] = inciso.NMPOLIZA;
                item.store.proxy.extraParams['params.nmsituac'] = inciso.NMSITUAC;
                item.store.proxy.extraParams['params.nmsuplem'] = inciso.NMSUPLEM;
                item.store.proxy.extraParams['params.cdtipsit'] = inciso.CDTIPSIT;
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
                    
                    item.heredar(true);
                }
                //para todos los demas que requieren los atributos extras puestos arriba (deberan venir con autoload=false)
                else if(item.store.autoLoad==false)
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
<%@ include file="/jsp-script/proceso/documentos/scriptImpresionRemesaEmisionEndoso.jsp"%>
</script>
</head>
<body><div id="_p44_divpri" style="height:800px;border:1px solid #999999;"></div></body>
</html>