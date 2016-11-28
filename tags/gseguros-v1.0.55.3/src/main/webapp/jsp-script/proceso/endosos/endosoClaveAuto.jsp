<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
////// urls //////
var _p38_urlCargarSumaAseguradaRamo5 = '<s:url namespace="/emision" action="cargarSumaAseguradaRamo5"    />';
var _p38_urlCargarParametros         = '<s:url namespace="/emision" action="obtenerParametrosCotizacion" />';
var _p38_urlConfirmarEndoso          = '<s:url namespace="/endosos" action="guardarEndosoClaveAuto"      />';
var _p38_urlRecuperacionSimple       = '<s:url namespace="/emision" action="recuperacionSimple"          />';
var url_PantallaPreview            = '<s:url namespace="/endosos"         action="includes/previewEndosos"  />';
var _p30_urlViewDoc                = '<s:url namespace="/documentos"      action="descargaDocInline"        />';
var _RUTA_DOCUMENTOS_TEMPORAL      = '<s:text name="ruta.documentos.temporal"                               />';
////// urls //////

////// variables //////
var _p38_smap1  = <s:property value="%{convertToJSON('smap1')}"  escapeHtml="false" />;
var _p38_slist1 = <s:property value="%{convertToJSON('slist1')}" escapeHtml="false" />;
debug('_p38_smap1:'  , _p38_smap1);
debug('_p38_slist1:' , _p38_slist1);
////// variables //////

////// overrides //////
////// overrides //////

////// dinamicos //////
var _p38_items = [ <s:property value="imap.items" escapeHtml="false" /> ];
////// dinamicos //////

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
    Ext.create('Ext.form.Panel',
    {
        renderTo  : '_p38_divpri'
        ,itemId   : '_p38_form'
        ,border   : 0
        ,defaults : { style : 'margin:5px;' }
        ,items    :
        [
            {
                xtype  : 'fieldset'
                ,title : '<span style="font:bold 14px Calibri;">Datos del veh&iacute;culo</span>'
                ,items : _p38_items
            }
            ,{
                xtype  : 'fieldset'
                ,title : '<span style="font:bold 14px Calibri;">Datos del endoso</span>'
                ,items :
                [
                    {
                        xtype       : 'datefield'
                        ,format     : 'd/m/Y'
                        ,value      : new Date()
                        ,fieldLabel : 'Fecha de efecto'
                        ,name       : 'feefecto'
                    }
                ]
            }
        ]
        ,buttonAlign : 'center'
        ,buttons     :
        [
            {
                itemId   : '_p38_botonConfirmar'
                ,text    : 'Emitir'
                ,icon    : '${ctx}/resources/fam3icons/icons/key.png'
                ,handler : function(me)
                {
                	
                    if(!me.up('form').getForm().isValid())
                    {
                        datosIncompletos();
                        return;
                    }
                    _mask();
                    me.setDisabled(true);
                    me.setText('Cargando...');
                    
                    var jsonConfirmar =
                    {
                        smap1   : _p38_smap1
                        ,smap2  : me.up('form').getValues()
                        ,slist1 : _p38_slist1
                    };
                    
                    if(!Ext.isEmpty(_p38_flujo))
                    {
                        jsonConfirmar.flujo = _p38_flujo;
                    }
                    jsonConfirmar.smap1['confirmar'] = 'no';
                    debug('jsonConfirmar:',jsonConfirmar);
                    
                    Ext.Ajax.request(
                    {
                        url       : _p38_urlConfirmarEndoso
                        ,jsonData : jsonConfirmar
                        ,success  : function(response)
                        {
                        	_unmask();
                            me.setText('Emitir');
                            me.setDisabled(false);
                            var jsonResp1 = Ext.decode(response.responseText);
                            debug('***jsonResp1==>> ', jsonResp1);
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
																		'smap4.nmpoliza'  : _p38_smap1.NMPOLIZA
							                                            ,'smap4.cdunieco' : _p38_smap1.CDUNIECO
							                                            ,'smap4.cdramo'   : _p38_smap1.CDRAMO
							                                            ,'smap4.estado'   : _p38_smap1.ESTADO
							                                            ,'smap4.nmsuplem' : jsonResp1.omap1.pv_nmsuplem_o
							                                            ,'smap4.nsuplogi' : jsonResp1.omap1.pv_nsuplogi_o
							                                        }
																,scripts  : true
																,autoLoad : true
														     }
														,buttons:[{
																	text    : 'Confirmar endoso'
																	,icon    : '${ctx}/resources/fam3icons/icons/award_star_gold_3.png'
																	,handler : function (me)
																					{
																						_mask();
																						me.up('window').destroy();
																						jsonConfirmar.smap1['confirmar'] = 'si';
																						 Ext.Ajax.request(
																			                    {
																			                        url       : _p38_urlConfirmarEndoso
																			                        ,jsonData : jsonConfirmar
																			                        ,success  : function(response)
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
																			                                    _generarRemesaClic(
																			                                        true
																			                                        ,_p38_smap1.CDUNIECO
																			                                        ,_p38_smap1.CDRAMO
																			                                        ,_p38_smap1.ESTADO
																			                                        ,_p38_smap1.NMPOLIZA
																			                                        ,callbackRemesa
																			                                    );
																			                                });
																			                                _unmask();
																			                            }
																			                            else
																			                            {
																			                                mensajeError(json.respuesta);
																			                            }
																			                        }
																			                        ,failure  : function()
																			                        {
																			                            me.setText('Confirmar');
																			                            me.setDisabled(false);
																			                            errorComunicacion();
																			                            _unmask();
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
																  },{
																	text    : 'Documentos'
																	,icon    : '${ctx}/resources/fam3icons/icons/printer.png'
																	,handler  :function(){
											 var numRand=Math.floor((Math.random()*100000)+1);
	                                         debug(numRand);
											 centrarVentanaInterna(Ext.create('Ext.window.Window', {
											 	title          : 'Vista previa'
										        ,width         : 700
										        ,height        : 500
										        ,collapsible   : true
										        ,titleCollapse : true
										        ,html          : '<iframe innerframe="'+numRand+'" frameborder="0" width="100" height="100"'
										                         +'src="'+_p30_urlViewDoc+"?&path="+_RUTA_DOCUMENTOS_TEMPORAL+"&filename="+jsonResp1.omap1.pdfEndosoNom_o+"\">"
										                         +'</iframe>'
										        ,listeners     : {
										        	resize : function(win,width,height,opt){
										                debug(width,height);
										                $('[innerframe="'+numRand+'"]').attr({'width':width-20,'height':height-60});
										        }
										      }}).show());
										}
																	,hidden   : _p38_smap1.TIPOFLOT!= TipoFlotilla.Flotilla? false :true
							                                        } 
 ]
												     }).show();
                        }
                        ,failure  : function()
                        {
                            me.setText('Confirmar');
                            me.setDisabled(false);
                            errorComunicacion();
                        }
                    });
                }
            }
        ]
    });
    ////// contenido //////
    
    ////// custom //////
	    _fieldLikeLabel('CLAVE').on(
	    {
	        select : function(me,records)
	        {
	            var marcaCmp    = _fieldByLabel('MARCA');
	            var submarcaCmp = _fieldByLabel('SUBMARCA');
	            var modeloCmp   = _fieldByLabel('MODELO');
	            var versionCmp  = _fieldLikeLabel('VERSI');
	            var record      = records[0];
	            
	            debug('marcaCmp:'            , marcaCmp);
	            debug('submarcaCmp:'         , submarcaCmp);
	            debug('submarcaCmp.heredar:' , submarcaCmp.heredar , '.');
	            debug('record.data'          , record.data);
	            
	            var cadena = record.get('value');
	            debug('cadena:',cadena);
	            
	            
	            var tok = cadena.split(' - ');
	            
	            
            marcaCmp.setValue(marcaCmp.findRecordByDisplay(tok[1]));
            submarcaCmp.heredar(true,function()
            {
                submarcaCmp.setValue(submarcaCmp.findRecordByDisplay(tok[2]));
            });
            modeloCmp.setValue(tok[3]);
            versionCmp.setValue(tok[4]);
            _p38_cargarSumaAseguradaRamo5();
        }
    });
    ////// custom //////
    
    ////// loaders //////
    for(var key in _p38_slist1[0])
    {
        var cmp = _fieldByName(key,_fieldById('_p38_form'),true);
        if(cmp)
        {
            debug('encontrado:',key,_p38_slist1[0][key]);
            if(!Ext.isEmpty(cmp.store))
            {
                if(cmp.store.getCount()>0)
                {
                    cmp.setValue(_p38_slist1[0][key]);
                }
                else
                {
                    cmp.store.valor = _p38_slist1[0][key];
                    cmp.store.padre = cmp;
                    cmp.store.on(
                    {
                        load : function(me)
                        {
                            me.padre.setValue(me.valor);
                        }
                    });
                }
            }
            else
            {
                cmp.setValue(_p38_slist1[0][key]);
            }
        }
        else
        {
            debug('no encontrado:',key);
        }
    }
    
    _fieldByName('feefecto').setValue(_p38_smap1.FEEFECTO);
    _fieldByName('feefecto').allowBlank = false;
    _fieldByName('feefecto').setReadOnly(true);
    _fieldByName('feefecto').isValid();
    
    /**
     * SE QUITA CODIGO PARA TOMAR FEEFECTO DE POLIZA
    Ext.Ajax.request(
    {
        url      : _p38_urlRecuperacionSimple
        ,params  :
        {
            'smap1.procedimiento' : 'RECUPERAR_FECHAS_LIMITE_ENDOSO'
            ,'smap1.cdunieco'     : _p38_smap1.CDUNIECO
            ,'smap1.cdramo'       : _p38_smap1.CDRAMO
            ,'smap1.estado'       : _p38_smap1.ESTADO
            ,'smap1.nmpoliza'     : _p38_smap1.NMPOLIZA
            ,'smap1.cdtipsup'     : _p38_smap1.cdtipsup
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
    
    */
    
    ////// loaders //////
});

////// funciones //////
function _p38_cargarSumaAseguradaRamo5()
{
    debug('>_p38_cargarSumaAseguradaRamo5');
    var form=_fieldById('_p38_form');
    form.setLoading(true);
    Ext.Ajax.request(
    {
        url      : _p38_urlCargarSumaAseguradaRamo5
        ,params  :
        {
            'smap1.cdtipsit'  : _p38_slist1[0].CDTIPSIT
            ,'smap1.clave'    : _fieldLikeLabel('CLAVE').getValue()
            ,'smap1.modelo'   : _fieldByLabel('MODELO').getValue()
            ,'smap1.cdsisrol' : _p38_smap1.cdsisrol
        }
        ,success : function(response)
        {
            form.setLoading(false);
            var json = Ext.decode(response.responseText);
            debug('### cargar suma asegurada:',json);
            if(json.exito)
            {
                /*
                Se comenta porque no quieren el aviso de error en pantalla
                if(!Ext.isEmpty(json.respuesta))
                {
                    mensajeWarning(json.respuesta);
                }
                */
                var sumaseg = _fieldByLabel('VALOR');
                sumaseg.setValue(json.smap1.sumaseg);
                sumaseg.valorCargado=json.smap1.sumaseg;
                _p38_cargarRangoValorRamo5();
            }
            else
            {
                mensajeError(json.respuesta);
            }
        }
        ,failure : function()
        {
            form.setLoading(false);
            errorComunicacion();
        }
    });
    debug('<_p38_cargarSumaAseguradaRamo5');
}

function _p38_cargarRangoValorRamo5()
{
    debug('>_p38_cargarRangoValorRamo5');
    var tipovalor = _fieldByLabel('TIPO VALOR');
    var valor     = _fieldByLabel('VALOR');
    
    var tipovalorval = tipovalor.getValue();
    var valorval     = valor.getValue();
    var valorCargado = valor.valorCargado;
    
    var valido = !Ext.isEmpty(tipovalorval)&&!Ext.isEmpty(valorval)&&!Ext.isEmpty(valorCargado);
    
    if(valido)
    {
        var panelpri = _fieldById('_p38_form');
        panelpri.setLoading(true);
        Ext.Ajax.request(
        {
            url      : _p38_urlCargarParametros
            ,params  :
            {
                'smap1.parametro' : 'RANGO_VALOR'
                ,'smap1.cdramo'   : _p38_slist1[0].CDRAMO
                ,'smap1.cdtipsit' : _p38_slist1[0].CDTIPSIT
                ,'smap1.clave4'   : tipovalorval
                ,'smap1.clave5'   : _p38_smap1.cdsisrol
            }
            ,success : function(response)
            {
                panelpri.setLoading(false);
                var json = Ext.decode(response.responseText);
                debug('### obtener rango valor:',json);
                if(json.exito)
                {
                    valormin = valorCargado*(1+(json.smap1.P1VALOR-0));
                    valormax = valorCargado*(1+(json.smap1.P2VALOR-0));
                    valor.setMinValue(valormin);
                    valor.setMaxValue(valormax);
                    valor.isValid();
                    debug('valor:',valorCargado);
                    debug('valormin:',valormin);
                    debug('valormax:',valormax);
                }
                else
                {
                    mensajeError(json.respuesta);
                }
            }
            ,failure : function()
            {
                panelpri.setLoading(false);
                errorComunicacion();
            }
        });
    }
    debug('<_p38_cargarRangoValorRamo5');
}
////// funciones //////
</script>
</head>
<body><div id="_p38_divpri" style="height:500px;border:1px solid #999999;"></div></body>
</html>
