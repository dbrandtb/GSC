<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
///////////////////////
////// variables //////

/*
smap1:
    CDRAMO: "2"
    CDTIPSIT: "SL"
    CDUNIECO: "1006"
    DSCOMENT: ""
    DSTIPSIT: "SALUD VITAL"
    ESTADO: "M"
    FEEMISIO: "22/01/2014"
    FEINIVAL: "22/01/2014"
    NMPOLIEX: "1006213000024000000"
    NMPOLIZA: "24"
    NMSUPLEM: "245668019180000000"
    NSUPLOGI: "1"
    NTRAMITE: "678"
    PRIMA_TOTAL: "12207.37"
    formapago : 12
    fechaInicio : 10/10/2014
*/
//Obtenemos el contenido en formato JSON de la propiedad solicitada:
var _9_smap1 = <s:property value="%{convertToJSON('smap1')}" escapeHtml="false" />;

var _9_flujo = <s:property value="%{convertToJSON('flujo')}" escapeHtml="false" />;

var _9_formLectura;
var _9_formFormaPago;
var _9_panelPri;
var _9_panelEndoso;
var _9_fieldFechaEndoso;

var url_PantallaPreview      = '<s:url namespace="/endosos"         action="includes/previewEndosos"/>';
var _9_urlGuardar            = '<s:url namespace="/endosos"         action="guardarEndosoFormaPago" />';
var _9_urlLoaderLectura      = '<s:url namespace="/consultasPoliza" action="consultaDatosPoliza"    />';
var _9_urlRecuperacionSimple = '<s:url namespace="/emision"         action="recuperacionSimple"     />';
var _p30_urlViewDoc          = '<s:url namespace="/documentos"      action="descargaDocInline"      />';
var _RUTA_DOCUMENTOS_TEMPORAL = '<s:text name="ruta.documentos.temporal" />';
debug('_9_smap1:',_9_smap1);
debug('_9_flujo:',_9_flujo);
////// variables //////
///////////////////////

Ext.onReady(function()
{
	
	Ext.Ajax.timeout = 1*60*60*1000; // 1 hora
	
    /////////////////////
    ////// modelos //////
    ////// modelos //////
    /////////////////////
    
    ////////////////////
    ////// stores //////
    ////// stores //////
    ////////////////////
    
    /////////////////////////
    ////// componentes //////
    Ext.define('_9_PanelEndoso',
    {
        extend         : 'Ext.form.Panel'
        ,initComponent : function()
        {
            debug('_9_PanelEndoso initComponent');
            Ext.apply(this,
            {
                title     : 'Datos del endoso'
                ,defaults :
                {
                    style : 'margin : 5px;'
                }
                ,items    :
                [
                    _9_fieldFechaEndoso
                ]
            });
            this.callParent();
        }
    });
    
    Ext.define('_9_FormFormaPago',
    {
        extend         : 'Ext.form.Panel'
        ,initComponent : function()
        {
            debug('_9_FormFormaPago initComponent');
            Ext.apply(this,
            {
                title     : 'Forma de pago'
                ,defaults :
                {
                    style : 'margin : 5px;'
                }
                ,layout   :
                {
                    type     : 'table'
                    ,columns : 2
                }
                ,items    :
                [
                    <s:property value="imap1.itemCambioOld" />
                    ,<s:property value="imap1.itemCambioNew" />
                ]
            });
            this.callParent();
        }
    });
    
    Ext.define('_9_FormLectura',
    {
        extend         : 'Ext.form.Panel'
        ,initComponent : function()
        {
            debug('_9_FormLectura initComponent');
            Ext.apply(this,
            {
                title      : 'Datos de la p&oacute;liza'
                ,defaults  :
                {
                    style : 'margin : 5px;'
                }
                ,layout    :
                {
                    type     : 'table'
                    ,columns : 2
                }
                ,listeners :
                {
                    afterrender : function(me){heredarPanel(me);}
                }
                ,items     : [ <s:property value="imap1.itemsPanelLectura" /> ]
            });
            this.callParent();
        }
    });
    ////// componentes //////
    /////////////////////////
    
    ///////////////////////
    ////// contenido //////
    _9_fieldFechaEndoso=Ext.create('Ext.form.field.Date',
    {
        format      : 'd/m/Y'
        ,fieldLabel : 'Fecha de efecto'
        ,allowBlank : false
        ,name       : 'fecha_endoso'
        //,readOnly   : true
    });
    _9_panelEndoso   = new _9_PanelEndoso();
    _9_formFormaPago = new _9_FormFormaPago();
    _9_formLectura   = new _9_FormLectura();
    
    _9_panelPri=Ext.create('Ext.panel.Panel',
    {
        renderTo     : '_9_divPri'
        ,defaults    :
        {
            style : 'margin : 5px;'
        }
        ,buttonAlign : 'center'
        ,buttons     :
        [
            {
                text     : 'Emitir'
                ,icon    : '${ctx}/resources/fam3icons/icons/key.png'
                ,handler : _9_confirmar
            }
        ]
        ,items       :
        [
            _9_formLectura
            ,_9_formFormaPago
            ,_9_panelEndoso
        ]
    });
    ////// contenido //////
    ///////////////////////
    
    ////////////////////
    ////// loader //////
    _setLoading(true,_9_panelPri);
    Ext.Ajax.request(
    {
        url      : _9_urlLoaderLectura
        ,params  :
        {
            'params.cdunieco'  : _9_smap1.CDUNIECO
            ,'params.cdramo'   : _9_smap1.CDRAMO
            ,'params.estado'   : _9_smap1.ESTADO
            ,'params.nmpoliza' : _9_smap1.NMPOLIZA
        }
        ,success : function(response)
        {
            _setLoading(false,_9_panelPri);
            var json = Ext.decode(response.responseText);
            debug('respuesta:',json);
            if(json.success==true)
            {
            	var comboOriginal  = _9_formFormaPago.items.items[0];
                var comboNuevo     = _9_formFormaPago.items.items[1];
                _9_smap1['perpag'] = json.datosPoliza.cdperpag;
                
                comboOriginal.setValue(_9_smap1['perpag']);
                _9_panelEndoso.items.items[0].setValue(_9_smap1.fechaInicio);
                try{
					if(_9_smap1.CDRAMO==Ramo.ServicioPublico)
						comboNuevo.getStore().filter([{filterFn: function(item) {
        					return item.get("key")==FormaPago.ANUAL || item.get("key")==FormaPago.CONTADO || item.get("key")==FormaPago.SEMESTRAL;
        				}}])
					
				}catch(e){
					debugError(e)
				}
                
                comboNuevo.on('change',function(combo,newVal,oldVal)
                {
                    var perpagOrigin = _9_smap1['perpag'];
                    
                    debug('comparando',perpagOrigin,newVal);
                    
                    if(newVal==perpagOrigin)
                    {
                   		combo.setValue(oldVal);
                        mensajeError('No hay cambio de forma de pago');
                    }
                });
                
            }
        }
        ,failure : function()
        {
            _setLoading(false,_9_panelPri);
            mensajeError('Error al cargar los datos de la p&oacute;liza');
        }
    });
    
    Ext.Ajax.request(
    {
        url      : _9_urlRecuperacionSimple
        ,params  :
        {
            'smap1.procedimiento' : 'RECUPERAR_FECHAS_LIMITE_ENDOSO'
            ,'smap1.cdunieco'     : _9_smap1.CDUNIECO
            ,'smap1.cdramo'       : _9_smap1.CDRAMO
            ,'smap1.estado'       : _9_smap1.ESTADO
            ,'smap1.nmpoliza'     : _9_smap1.NMPOLIZA
            ,'smap1.cdtipsup'     : _9_smap1.cdtipsup
        }
        ,success : function(response)
        {
            var json = Ext.decode(response.responseText);
            debug('### fechas:',json);
            if(json.exito)
            {
                _fieldByName('fecha_endoso').setMinValue(json.smap1.FECHA_MINIMA);
                _fieldByName('fecha_endoso').setMaxValue(json.smap1.FECHA_MAXIMA);
                _fieldByName('fecha_endoso').setReadOnly(json.smap1.EDITABLE=='N');
                _fieldByName('fecha_endoso').isValid();
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
    ////// loader //////
    ////////////////////
});

///////////////////////
////// funciones //////
function _9_confirmar()
{
    debug('_9_confirmar');
    
    var valido=true;
    
    if(valido)
    {
        valido=_9_formFormaPago.isValid()&&_9_panelEndoso.isValid();
        if(!valido)
        {
            datosIncompletos();
        }
    }
    
    if(valido)
    {
        var json=
        {
            smap1  : _9_smap1
            ,smap2 :
            {
                fecha_endoso : Ext.Date.format(_9_fieldFechaEndoso.getValue(),'d/m/Y')
                ,cdperpag    : _9_formFormaPago.items.items[1].getValue()
                ,confirmar   : 'no'
            }
        }
        
        if(!Ext.isEmpty(_9_flujo))
        {
            json.flujo = _9_flujo;
        }
        
        debug('datos que se enviaran:',json);
        
        var panelMask = new Ext.LoadMask('_9_divPri', {msg:"Confirmando..."});
		panelMask.show();
        if(   _9_smap1.CDRAMO == Ramo.AutosFronterizos
           || _9_smap1.CDRAMO == Ramo.ServicioPublico
           || _9_smap1.CDRAMO == Ramo.AutosResidentes){
        Ext.Ajax.request(
        {
            url       : _9_urlGuardar
            ,jsonData : json
            ,success  : function(response)
            {
            	json1=Ext.decode(response.responseText);
				debug('datosjson1windowsa:',json1);
				
				//VERIFICAMOS LA RESPUESTA DEL SERVIDOR
				try{
					if(_9_smap1.CDRAMO==Ramo.ServicioPublico)
						if(json1.success==false){
							mensajeError(json1.error);
							panelMask.hide();
							return ;
						}
					
				}catch(e){
					debugError(e)
				}
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
											'smap4.nmpoliza'  : _9_smap1.NMPOLIZA
                                            ,'smap4.cdunieco' : _9_smap1.CDUNIECO
                                            ,'smap4.cdramo'   : _9_smap1.CDRAMO
                                            ,'smap4.estado'   : _9_smap1.ESTADO
                                            ,'smap4.nmsuplem' : json1.smap3.pv_nmsuplem_o 
                                            ,'smap4.nsuplogi' : json1.smap3.pv_nsuplogi_o 
                                        }
									,scripts  : true
									,autoLoad : true
							     }
							,buttons:[{
                            			itemId    : '_p3_botonEnviar'
                                        ,xtype    : 'button'
                                        ,text     : 'Enviar'
                                        ,icon     : '${ctx}/resources/fam3icons/icons/email.png'
                                        //,disabled : true
                                        ,handler  : function(){
                                        	
											_p3_cargarCorreos(_9_flujo.ntramite)
                                        	
                                        	_p3_enviar(_9_flujo.ntramite
														,json1.smap3.pdfEndosoNom_o);
                                        } 
                            			
                            		},{
										text    : 'Confirmar endoso'
										,name    : 'endosoButton'
										,icon    : '${ctx}/resources/fam3icons/icons/award_star_gold_3.png'
										,handler : function(me){
																me.up('window').destroy();
																// Se crea variable para turnar cuando sea un endoso con autorizacion
												            	var _p9_flujoAux = {};
												            	
												            	if(!Ext.isEmpty(_9_flujo)
																    &&!Ext.isEmpty(_9_flujo.aux)){
																    	//
																	    try{
																	    	//
																	        _p9_flujoAux = Ext.decode(_9_flujo.aux);
																	    }
																	    catch(e) {
																	    	//
																	        manejaException(e);
																	    }
																}
													            
													            
																 var json1=
																        {
																            smap1  : _9_smap1
																            ,smap2 :
																            {
																                fecha_endoso : Ext.Date.format(_9_fieldFechaEndoso.getValue(),'d/m/Y')
																                ,cdperpag    : _9_formFormaPago.items.items[1].getValue()
																                ,confirmar   : 'no'
																            }
																        }
																        
														        //Validacion para cuando es un endsoso con autorizacion.
													            if(Ext.isEmpty(_9_flujo)
												                    ||Ext.isEmpty(_9_flujo.aux)
												                    ||_9_flujo.aux.indexOf('onComprar')==-1){
													            	
													            	json1.smap2.confirmar = 'si';
        															//confirmar = 'si';
													            }
																        
																        if(!Ext.isEmpty(_9_flujo)) {
																            json1.flujo = _9_flujo;
																        }
																        
																Ext.Ajax.request(
																	        {
																	            url       : _9_urlGuardar
																	            ,jsonData : json1
																	            ,success  : function(response)
																	            {
																	                
																	                json=Ext.decode(response.responseText);
																	                debug('datos recibidos:',json);
																	                if(json.success==true) {
																	                	//
																	                	if(Ext.isEmpty(_9_flujo)
																		                    ||Ext.isEmpty(_9_flujo.aux)
																		                    ||_9_flujo.aux.indexOf('onComprar')==-1){
																		                    	//
																			                    var callbackRemesa = function()
																			                    {
																			                        //////////////////////////////////
																			                        ////// usa codigo del padre //////
																			                        /*//////////////////////////////*/
																			                        marendNavegacion(2);
																			                        /*//////////////////////////////*/
																			                        ////// usa codigo del padre //////
																			                        //////////////////////////////////
																			                    };
																			                    panelMask.hide();
																			                    mensajeCorrecto('Endoso generado',json.mensaje,function()
																			                    {
																			                        var cadena= json.mensaje;
																			                        var palabra="guardado";
																			                    	if (cadena.indexOf(palabra)==-1){
																			                    		_generarRemesaClic2(
																			                                    false
																			                                    ,_9_smap1.CDUNIECO
																			                                    ,_9_smap1.CDRAMO
																			                                    ,_9_smap1.ESTADO
																			                                    ,_9_smap1.NMPOLIZA
																			                                    ,callbackRemesa
																			                                );
																			                    	}else{
																			                    		_generarRemesaClic(
																			                                    true
																			                                    ,_9_smap1.CDUNIECO
																			                                    ,_9_smap1.CDRAMO
																			                                    ,_9_smap1.ESTADO
																			                                    ,_9_smap1.NMPOLIZA
																			                                    ,callbackRemesa
																			                                );
																			                    	}
																			                    });
																	                
																		                    }else{//if(_p3_flujoAux.endosoAutorizar==='onComprar_160'){
																			                    //si el flujo tiene este comodin ejecutaremos un turnado con el status indicado
																		                    	debug('_p9_flujoAux.endosoAutorizar: ',_p9_flujoAux.endosoAutorizar);
																			                    var ck = 'Turnando tr\u00e1mite';
																			                    try
																			                    {
																			                        var status = _p9_flujoAux.endosoAutorizar.split('_')[1];
																			                        debug('status para turnar onComprar:',status,'.');
																			                        
																			                        _mask(ck);
																			                        Ext.Ajax.request(
																			                        {
																			                            url      : _GLOBAL_COMP_URL_TURNAR
																			                            ,params  :
																			                            {
																			                                'params.CDTIPFLU'   : _9_flujo.cdtipflu
																			                                ,'params.CDFLUJOMC' : _9_flujo.cdflujomc
																			                                ,'params.NTRAMITE'  : _9_flujo.ntramite
																			                                ,'params.STATUSOLD' : _9_flujo.status
																			                                ,'params.STATUSNEW' : status
																			                                ,'params.COMMENTS'  : 'Tr\u00e1mite cotizado'
																			                                ,'params.SWAGENTE'  : 'S'
																			                            }
																			                            ,success : function(response)
																			                            {
																			                                _unmask();
																			                                var ck = '';
																			                                try
																			                                {
																			                                    var json = Ext.decode(response.responseText);
																			                                    debug('### turnar:',json);
																			                                    if(json.success)
																			                                    {
																			                                        mensajeCorrecto
																			                                        (
																			                                            'Tr\u00e1mite turnado'
																			                                            //,json.message
																			                                            ,'El tr\u00e1mite fue turnado para aprobaci\u00f3n del agente/promotor'
																			                                            ,function()
																			                                            {
																			                                                _mask('Redireccionando');
																			                                                Ext.create('Ext.form.Panel').submit(
																			                                                {
																			                                                    url             : _GLOBAL_COMP_URL_MCFLUJO
																			                                                    ,standardSubmit : true
																			                                                });
																			                                            }
																			                                        );
																			                                       
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
																			                                _unmask();
																			                                errorComunicacion(null,'Error al turnar tr\u00e1mite');
																			                            }
																			                        });
																			                    }
																			                    catch(e)
																			                    {
																			                        manejaException(e,ck);
																			                    }
																			               
																				               //Sacaendoso
																				                    
																			                    sacaEndoso(_9_smap1.CDUNIECO,
																			                               _9_smap1.CDRAMO,
																			                               _9_smap1.ESTADO,
																			                               _9_smap1.NMPOLIZA,
																			                               json.smap3.pv_nmsuplem_o,
																			                               json.smap3.pv_nsuplogi_o);
						                
						                    
																		                    }
																	                }
																	                else
																	                {
																	                    mensajeError(json.error);
																	                }
																	            }
																	            ,failure  : function()
																	            {
																	                panelMask.hide();
																	                errorComunicacion();
																	            }
																	        });
																
															   }
									   },
									   {
										text    : 'Cancelar'
										,icon    : '${ctx}/resources/fam3icons/icons/cancel.png'
										,handler : function (me){
														me.up('window').destroy();
														panelMask.hide();
														marendNavegacion(2);
														}
									   },{
										text    : 'Documentos'
										,name    : 'documentoButton'
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
										                         +'src="'+_p30_urlViewDoc+"?&path="+_RUTA_DOCUMENTOS_TEMPORAL+"&filename="+json1.smap2.pdfEndosoNom_o+"\">"
										                         +'</iframe>'
										        ,listeners     : {
										        	resize : function(win,width,height,opt){
										                debug(width,height);
										                $('[innerframe="'+numRand+'"]').attr({'width':width-20,'height':height-60});
										        }
										      }}).show());
										}
										,hidden   : _9_smap1.TIPOFLOT!= TipoFlotilla.Flotilla? false :true
                                        } ]
					     }).show();
            
            }
            ,failure  : function()
            {
                panelMask.hide();
                errorComunicacion();
            }
        });
    }else{
    	 var json1=
	        {
	            smap1  : _9_smap1
	            ,smap2 :
	            {
	                fecha_endoso : Ext.Date.format(_9_fieldFechaEndoso.getValue(),'d/m/Y')
	                ,cdperpag    : _9_formFormaPago.items.items[1].getValue()
	                ,confirmar   : 'si'
	            }
	        }
	    Ext.Ajax.request(
	        {
	            url       : _9_urlGuardar
	            ,jsonData : json1
	            ,success  : function(response)
	            {
	                panelMask.hide();
	                json=Ext.decode(response.responseText);
	                debug('datos recibidos:',json);
	                if(json.success==true)
	                {
	                    var callbackRemesa = function()
	                    {
	                        //////////////////////////////////
	                        ////// usa codigo del padre //////
	                        /*//////////////////////////////*/
	                        marendNavegacion(2);
	                        /*//////////////////////////////*/
	                        ////// usa codigo del padre //////
	                        //////////////////////////////////
	                    };
	                    
	                    mensajeCorrecto('Endoso generado',json.mensaje,function()
	                    {
	                        var cadena= json.mensaje;
	                        var palabra="guardado";
	                    	if (cadena.indexOf(palabra)==-1){
	                    		_generarRemesaClic2(
	                                    false
	                                    ,_9_smap1.CDUNIECO
	                                    ,_9_smap1.CDRAMO
	                                    ,_9_smap1.ESTADO
	                                    ,_9_smap1.NMPOLIZA
	                                    ,callbackRemesa
	                                );
	                    	}else{
	                    		_generarRemesaClic(
	                                    true
	                                    ,_9_smap1.CDUNIECO
	                                    ,_9_smap1.CDRAMO
	                                    ,_9_smap1.ESTADO
	                                    ,_9_smap1.NMPOLIZA
	                                    ,callbackRemesa
	                                );
	                    	}
	                    });
	                }
	                else
	                {
	                    mensajeError(json.error);
	                }
	            }
	            ,failure  : function()
	            {
	                panelMask.hide();
	                errorComunicacion();
	            }
	        });
        }
    
    
    }
};
////// funciones //////
////// funciones //////}


function _p3_cargarCorreos(ntramite)
{
    debug('>_p3_idInputCorreo');
    Ext.Ajax.request(
    {
        url     : _GLOBAL_RECUPERA_CORREO
        ,params :
        {
            'smap1.ntramite'    : ntramite
        }
        ,success : function(response) {
            var json = Ext.decode(response.responseText);
            debug('### json cargarCorreos:',json);
            
            if(json.exito)
            {
            	  debug('>_p3_idInputCorreo 1 ', json.respuesta);
            	  _fieldById('_p3_idInputCorreo').setValue(json.respuesta);
            }
            else{
            	  debug('>_p3_idInputCorreo 2');
            }
         }
         ,failure : function(){
         	me.setLoading(false);
            errorComunicacion();
         }
    })
}

function _p3_enviar(ntramite
                    ,nomArchivo)
{
    debug('>_p28_enviar');
    
    centrarVentanaInterna(Ext.create('Ext.window.Window',
    {
        title        : 'Enviar cotizaci&oacute;n'
        ,width       : 550
        ,modal       : true
        ,height      : 150
        ,buttonAlign : 'center'
        ,bodyPadding : 5
        ,items       :
        [
            {
                xtype       : 'textfield'
                ,itemId     : '_p3_idInputCorreo'
                ,id         : '_p3_idInputCorreos'
                ,fieldLabel : 'Correo(s)'
                ,emptyText  : 'Correo(s) separados por ;'
                ,labelWidth : 100
                ,allowBlank : false
                ,blankText  : 'Introducir correo(s) separados por ;'
                ,width      : 500
                ,listeners  : {
                	boxready : function(){
                		
                		debug('Saliendo de la funcion ', _fieldById('_p3_idInputCorreo').getValue());
                	}
                }		
                	
            }
        ]
        ,buttons :
        [
            {
                text     : 'Enviar'
                ,icon    : '${ctx}/resources/fam3icons/icons/accept.png'
                ,handler : function()
                {
                    var me = this;
                    if (_fieldById('_p3_idInputCorreo').getValue().length > 0
                            &&_fieldById('_p3_idInputCorreo').getValue() != 'Correo(s) separados por ;')
                    {
                        debug('Se va a enviar cotizacion');
                        //me.up().up().setLoading(true);
                        
                        envioCorreo(_RUTA_DOCUMENTOS_TEMPORAL
				                    ,ntramite
				                    ,nomArchivo
				                    ,_fieldById('_p3_idInputCorreo').getValue());
				                    
				        //
				        this.up().up().destroy();
                        
                    }
                    else
                    {
                        mensajeWarning('Introduzca al menos un correo');
                    }
                }
            }
            ,{
                text     : 'Cancelar'
                ,icon    : '${ctx}/resources/fam3icons/icons/cancel.png'
                ,handler : function()
                {
                    this.up().up().destroy();
                }
            }
        ]
    }).show());
    _fieldById('_p3_idInputCorreo').focus();
    debug('<_p3_enviar');
}    
///////////////////////
<%@ include file="/jsp-script/proceso/documentos/scriptImpresionRemesaEmisionEndoso.jsp"%>
</script>
<div id="_9_divPri" style="height:1000px;"></div>