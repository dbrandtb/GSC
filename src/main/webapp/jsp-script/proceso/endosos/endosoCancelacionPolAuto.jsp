<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
////// urls //////
var _p43_urlMarcarPolizaCancelar   = '<s:url namespace="/endosos"     action="marcarPolizaCancelarPorEndoso"        />';
var _p43_urlConfirmar              = '<s:url namespace="/endosos"     action="confirmarEndosoCancelacionPolAuto"    />';
var _p43_urlValidaRazonCancelacion = '<s:url namespace="/cancelacion" action="validaRazonCancelacion"               />';
var url_PantallaPreview            = '<s:url namespace="/endosos"     action="includes/previewEndosos"              />';
////// urls //////

////// variables //////
var _p43_smap1 = <s:property value="%{convertToJSON('smap1')}"  escapeHtml="false" />;
debug('_p43_smap1:',_p43_smap1);

var _p43_flujo = <s:property value="%{convertToJSON('flujo')}"  escapeHtml="false" />;
debug('_p43_flujo:',_p43_flujo);
////// variables //////

////// overrides //////
////// overrides //////

////// componentes dinamicos //////
var _p43_panelLecturaItems = [ <s:property value="imap.panelLecturaItems" escapeHtml="false" /> ];
var _p43_formEndosoItems   = [ <s:property value="imap.formEndosoItems"   escapeHtml="false" /> ];
////// componentes dinamicos //////

var panCanForm;
var comboMotivoCanc;
var panCanInputFecha;

Ext.onReady(function()
{
	Ext.Ajax.timeout = 1*60*60*1000; // 1 hora
	
    ////// modelos //////
    ////// modelos //////
    
    ////// stores //////
    ////// stores //////
    
    ////// componentes //////
    ////// componentes //////
    
    ////// contenido //////
    panCanForm = Ext.create('Ext.panel.Panel',
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
                        ,text    : 'Emitir'
                        ,icon    : '${ctx}/resources/fam3icons/icons/key.png'
                        ,handler : function(me)
                        {
                        	var myMask = _maskLocal();
                            if(!me.up('form').getForm().isValid())
                            {
                                datosIncompletos();
                                return;
                            }
                            
                            try
                            {
                                var form = me.up('form');
                                var ck   = 'Validando motivo';
                                _setLoading(true,form);
                                Ext.Ajax.request(
                                {
                                    url     : _p43_urlValidaRazonCancelacion
                                    ,params :
                                    {
                                        'smap1.cdunieco'  : _p43_smap1.CDUNIECO
                                        ,'smap1.cdramo'   : _p43_smap1.CDRAMO
                                        ,'smap1.estado'   : _p43_smap1.ESTADO
                                        ,'smap1.nmpoliza' : _p43_smap1.NMPOLIZA
                                        ,'smap1.cdrazon'  : _fieldByName('cdrazon').getValue()
                                    }
                                    ,success : function(response)
                                    {
                                        _setLoading(false,form);
                                        var ck = 'Decodificando respuesta al validar motivo';
                                        try
                                        {
                                            var json = Ext.decode(response.responseText);
                                            debug('### validar:',json);
                                            if(json.success==true)
                                            {
                                                var paramsConfirmar =
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
                                                    ,'smap1.confirmar': 'no'
                                                };
                                                
                                                if(!Ext.isEmpty(_p43_flujo))
                                                {
                                                    paramsConfirmar['flujo.ntramite']  = _p43_flujo.ntramite;
                                                    paramsConfirmar['flujo.status']    = _p43_flujo.status;
                                                    paramsConfirmar['flujo.cdtipflu']  = _p43_flujo.cdtipflu;
                                                    paramsConfirmar['flujo.cdflujomc'] = _p43_flujo.cdflujomc;
                                                    paramsConfirmar['flujo.webid']     = _p43_flujo.webid;
                                                    paramsConfirmar['flujo.tipoent']   = _p43_flujo.tipoent;
                                                    paramsConfirmar['flujo.claveent']  = _p43_flujo.claveent;
                                                    paramsConfirmar['flujo.cdunieco']  = _p43_flujo.cdunieco;
                                                    paramsConfirmar['flujo.estado']    = _p43_flujo.estado;
                                                    paramsConfirmar['flujo.nmpoliza']  = _p43_flujo.nmpoliza;
                                                    paramsConfirmar['flujo.nmsituac']  = _p43_flujo.nmsituac;
                                                    paramsConfirmar['flujo.nmsuplem']  = _p43_flujo.nmsuplem;
                                                    paramsConfirmar['flujo.aux']       = _p43_flujo.aux;
                                                }
                                                debug('paramsConfirmar--> ',paramsConfirmar);
                                            
                                                _setLoading(true,form);
                                                Ext.Ajax.request(
					                            {
					                                url      : _p43_urlConfirmar
					                                ,params  : paramsConfirmar
					                                ,success : function(response)
					                                {
					                                	myMask.close();
					                                    _setLoading(false,form);
					                                    var jsonconfir = Ext.decode(response.responseText);
            											debug('### jsonpreview:',jsonconfir);
					                                    Ext.create('Ext.window.Window',
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
																	   				'smap4.nmpoliza'  : _p43_smap1.NMPOLIZA
										                                            ,'smap4.cdunieco' : _p43_smap1.CDUNIECO
										                                            ,'smap4.cdramo'   : _p43_smap1.CDRAMO
										                                            ,'smap4.estado'   : _p43_smap1.ESTADO
										                                            ,'smap4.nmsuplem' : jsonconfir.omap1.pv_nmsuplem_o
										                                            ,'smap4.nsuplogi' : jsonconfir.omap1.pv_nsuplogi_o
										                                        }
																			,scripts  : true
																			,autoLoad : true
																	     }
																	,buttons:[{
																				text    : 'Confirmar endoso'
																				,icon    : '${ctx}/resources/fam3icons/icons/award_star_gold_3.png'
																				,handler : 
																					function (me){
																						 debug('paramsConfirmar--> ',paramsConfirmar);
																						paramsConfirmar['smap1.confirmar']= 'si';
																						 debug('paramsConfirmar--> ',paramsConfirmar);
																						var myMask = _maskLocal();
																						me.up('window').destroy();
																						Ext.Ajax.request(
																	                            {
																	                                url      : _p43_urlConfirmar
																	                                ,params  : paramsConfirmar
																	                                ,success : function(response)
																	                                {
																	                                	myMask.close();
																	                                    _setLoading(false,form);
																	                                    var ck = 'Decodificando respuesta al cancelar';
																	                                    try
																	                                    {
																	                                        var json = Ext.decode(response.responseText);
																	                                        debug('### confirmar:',json);
																	                                        if(json.success)
																	                                        {
																	                                            var callbackRemesa = function()
																	                                            {
																	                                                marendNavegacion(2);
																	                                            };
																	                                            mensajeCorrecto('Endoso generado','Endoso generado',function()
																	                                            {
																	                                            	if(_p43_smap1.cdtipsup != '53'){
																														_generarRemesaClic(
																		                                                    true
																		                                                    ,_p43_smap1.CDUNIECO
																		                                                    ,_p43_smap1.CDRAMO
																		                                                    ,_p43_smap1.ESTADO
																		                                                    ,_p43_smap1.NMPOLIZA
																		                                                    ,callbackRemesa
																		                                                );					                                            	
																	                                            	}else{
																	                                            		callbackRemesa();
																	                                            	}
																	                                            });
																	                                        }
																	                                        else
																	                                        {
																	                                            mensajeError(json.respuesta);
																	                                        }
																	                                    }
																	                                    catch(e)
																	                                    {
																	                                        manejaException(e,ck);
																	                                    }
																	                                }
																	                                ,failure : function()
																	                                {
																	                                	myMask.close();
																	                                    _setLoading(false,form);
																	                                    errorComunicacion(null,'Error cancelando');
																	                                }
																	                            });
																						
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
															     }).show();
					                                    
					                                }
					                                ,failure : function()
					                                {
					                                    _setLoading(false,form);
					                                    errorComunicacion(null,'Error cancelando');
					                                }
					                            });
                                            }
                                            else
                                            {
                                                mensajeError(json.respuesta);
                                            }
                                        }
                                        catch(e)
                                        {
                                            manejaException(e,ck);
                                        }
                                    }
                                    ,failure : function()
                                    {
                                        _setLoading(false,form);
                                        errorComunicacion(null,'Error al validar motivo');
                                    }
                                });
                            }
                            catch(e)
                            {
                                manejaException(e,ck);
                            }
                        }
                    }
                ]
            })
        ]
    })
    ////// contenido //////
    
    
    _fieldByName('cdunieco',panCanForm).forceSelection =  false;
    _fieldByName('cdunieco',panCanForm).setValue(_p43_smap1.CDUNIECO);
    
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
//                _fieldByName('fecancel').setValue(json.smap1.FECANCEL);
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
    
    comboMotivoCanc = _fieldByName('cdrazon',panCanForm);
    debug('comboMotivoCanc:',comboMotivoCanc);
    panCanInputFecha = _fieldByName('fecancel',panCanForm);
    panCanInputFecha.setEditable(true);
    debug('panCanInputFecha:',panCanInputFecha);
    
    
    comboMotivoCanc.addListener('select',function(combo,records){
    	var nue = records[0].get('key');
    	comboMotivocambio(comboMotivoCanc,nue);
    	
    });
    comboMotivocambio(comboMotivoCanc,'9');
    
    
    function comboMotivocambio(combo,nue){
	
    	debug('>comboMotivocambio:', nue);
    	
    	if(nue=='9'){
            combo.setValue('9');
            panCanInputFecha.setValue(new Date());
            panCanInputFecha.setReadOnly(true);
            _p43_smap1.cdtipsup = '53';
    	}
    	else if(nue=='25'){
    		combo.setValue('25');
    		panCanInputFecha.setValue(_p43_smap1.FEEFECTO);
            panCanInputFecha.setReadOnly(false);
            _p43_smap1.cdtipsup = '52';
    	}
    	else if(nue=='31'){
            combo.setValue('31');
            panCanInputFecha.setValue(_p43_smap1.FEEFECTO);
            panCanInputFecha.setReadOnly(false);
            _p43_smap1.cdtipsup = '52';
        }
        
        debug('<comboMotivocambio');
}
	
});

	

////// funciones //////
////// funciones //////
<%@ include file="/jsp-script/proceso/documentos/scriptImpresionRemesaEmisionEndoso.jsp"%>
</script>
</head>
<body><div id="_p43_divpri" style="height:250px;border:1px solid #999999;"></div></body>
</html>