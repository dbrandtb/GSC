<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>

var _CONTEXT = '${ctx}';
	var paramsEntrada          = <s:property value="%{convertToJSON('smap1')}" escapeHtml="false" />;
	
	paramsEntrada.FEPROREN_ORIG = paramsEntrada.FEPROREN; 
		
	var guarda_Vigencia_Poliza   = '<s:url namespace="/endosos"      action="guardarEndosoAmpliacionVigencia"       />';
	var url_PantallaPreview      = '<s:url namespace="/endosos"      action="includes/previewEndosos"                />';
	var _p30_urlViewDoc          = '<s:url namespace="/documentos"      action="descargaDocInline"      />';
    var _RUTA_DOCUMENTOS_TEMPORAL = '<s:text name="ruta.documentos.temporal" />';
	
	var endAmpVigFlujo = <s:property value="%{convertToJSON('flujo')}" escapeHtml="false" />;
	
	debug('paramsEntrada  -->:',paramsEntrada);
	
	debug('endAmpVigFlujo:',endAmpVigFlujo);
	
	Ext.onReady(function() {
		
		Ext.Ajax.timeout = 1*60*60*1000; // 1 hora
		
		var myMask = new Ext.LoadMask('maindivAmpVig', {msg:"Cargando..."});
		
		var panelInicialPral = Ext.create('Ext.form.Panel', {
		    title: 'Apliaci&oacute;n Vigencia',
		    renderTo  : 'maindivAmpVig',
		    bodyPadding: 5,
		    defaultType: 'textfield',
		    layout     :
			{
				type     : 'table'
				,columns : 2
			}
			,defaults 	:
			{
				style : 'margin:5px;'
			}
			,
		    items: [
		    	{	xtype		: 'datefield', 	fieldLabel	: 'Fecha Original',			name	: 'feEfecto',			hidden		: true,
					format		:  'm/d/Y', 	editable	: true,						value	: paramsEntrada.FEEFECTO
				},
				{	xtype		: 'datefield',	fieldLabel	: 'Fecha Minimo',			name	: 'feMin',				hidden		: true,
					format		: 'd/m/Y',		editable	: true,						value	: paramsEntrada.FEEFECTO
				},
				{	xtype		: 'datefield',	fieldLabel	: 'Fecha Maximo',			name	: 'feMax',				hidden		: true,
					format		: 'd/m/Y',		editable	: true,						value	: paramsEntrada.FEEFECTO
				},
				{
					xtype		: 'datefield',	fieldLabel	: 'Fecha Efecto',			name	: 'feIngreso',			labelWidth	: 150,
					format		: 'd/m/Y',		editable	: true,						value	: paramsEntrada.FEEFECTO,	allowBlank	: false
					,readOnly  	: true
					
				},
		    	{	xtype		: 'datefield',	fieldLabel	: 'Fecha Fin',				name	: 'feFin',				labelWidth	: 150,
					format		: 'd/m/Y',		editable	: true,						value	: paramsEntrada.FEPROREN, readOnly  	: true
				},
				{	xtype		: 'datefield',	fieldLabel	: 'Fecha Ampliaci&oacute;n Vigencia', name	: 'feAmpliacion', labelWidth	: 150,
					format		: 'd/m/Y',		editable	: true, 					value   : paramsEntrada.FEPROREN,			allowBlank	: false,
					colspan		:2, minValue : new Date(),
					minText:'La fecha de Ampliaci&oacute;n Vigencia debe ser mayor a Fecha Fin'
				}
	    	]
			,buttonAlign:'center'
			,buttons: [{
				text: 'Emitir'
				,icon:_CONTEXT+'/resources/fam3icons/icons/key.png'
				,buttonAlign : 'center',
				handler: function() {
					var myMask1 = _maskLocal();
					var formPanel = this.up().up();
					myMask.show();
					
					if (formPanel.form.isValid()) {
                        //1.- Verificamos la información de las fechas
						
						var feAmpli  = new Date(panelInicialPral.down('[name="feAmpliacion"]').getValue());
						var feProren = new Date(panelInicialPral.down('[name="feFin"]').getValue());
						paramsEntrada.FEINIVAL = Ext.Date.format(panelInicialPral.down('[name="feIngreso"]').getValue(),'d/m/Y');
						paramsEntrada.FEPROREN = Ext.Date.format(panelInicialPral.down('[name="feAmpliacion"]').getValue(),'d/m/Y');
						
						if(feAmpli > feProren){
						    //Exito
							var submitValues={};
							paramsEntrada.confirmar = 'no';
	        				submitValues['smap1']= paramsEntrada;
	        				
	        				if(!Ext.isEmpty(endAmpVigFlujo))
	        				{
	        				    submitValues['flujo'] = endAmpVigFlujo;
	        				}
	        				
	        				Ext.Ajax.request( {
	   						    url: guarda_Vigencia_Poliza,
	   						    jsonData:  Ext.encode(submitValues)
	   						    ,success:function(response,opts){
	   						    	 myMask.hide();
	   						    	 myMask1.close();
					                 var jsonResp1 = Ext.decode(response.responseText);
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
												   				'smap4.nmpoliza'  : paramsEntrada.NMPOLIZA
					                                            ,'smap4.cdunieco' : paramsEntrada.CDUNIECO
					                                            ,'smap4.cdramo'   : paramsEntrada.CDRAMO
					                                            ,'smap4.estado'   : paramsEntrada.ESTADO
					                                            ,'smap4.nmsuplem' : jsonResp1.smap2.pv_nmsuplem_o
					                                            ,'smap4.nsuplogi' : jsonResp1.smap2.pv_nsuplogi_o
					                                        }
														,scripts  : true
														,autoLoad : true
												     }
												,buttons:[{
                                                			itemId    : '_p3_botonEnviar'
					                                        ,xtype    : 'button'
					                                        ,text     : 'Enviar'
					                                        ,icon     : '${ctx}/resources/fam3icons/icons/email.png'
					                                        ,hidden    : Ext.isEmpty(endAmpVigFlujo)
					                                        ,handler  : function(){
					                                        	
																_p3_cargarCorreos(endAmpVigFlujo.ntramite)
					                                        	
					                                        	_p3_enviar(endAmpVigFlujo.ntramite
                    														,jsonResp1.smap2.pdfEndosoNom_o);
					                                        } 
                                                			
                                                		},{
															text    : 'Confirmar endoso'
															,name    : 'endosoButton'
															,icon    : '${ctx}/resources/fam3icons/icons/award_star_gold_3.png'
															,handler : 
																function (me){
																	var myMask1 = _maskLocal();
																	me.up('window').destroy();
																	// Se crea variable para turnar cuando sea un endoso con autorizacion
													            	var endAmpVigFlujoAux = {};
													            	
													            	if(!Ext.isEmpty(endAmpVigFlujo)
																	    &&!Ext.isEmpty(endAmpVigFlujo.aux)){
																	    	//
																		    try{
																		    	//
																		        endAmpVigFlujoAux = Ext.decode(endAmpVigFlujo.aux);
																		    }
																		    catch(e) {
																		    	//
																		        manejaException(e);
																		    }
																	}
																	myMask.show();
																	paramsEntrada.confirmar = 'no';
																	if(Ext.isEmpty(endAmpVigFlujo)
												                    ||Ext.isEmpty(endAmpVigFlujo.aux)
												                    ||endAmpVigFlujo.aux.indexOf('onComprar')==-1){
												                    	paramsEntrada.confirmar = 'si';
														            }
														            
																	//submitValues['smap1.confirmar']='si' ;
															        //paramsEntrada.confirmar = 'si';
                                                                    submitValues['smap1']= paramsEntrada;
																	
																	if(!Ext.isEmpty(endAmpVigFlujo))
                                                                    {
                                                                        submitValues['flujo'] = endAmpVigFlujo;
                                                                    }
                                                                    
																	Ext.Ajax.request(
																		{
												   						    url: guarda_Vigencia_Poliza,
												   						    jsonData: Ext.encode(submitValues)
												   						    ,success:function(response,opts){
												   						    	//
												   						    	var jsonResp = Ext.decode(response.responseText);
												   						    	debug('**** jsonResp: ' ,jsonResp);
												   						    	if(Ext.isEmpty(endAmpVigFlujo)
																		                    ||Ext.isEmpty(endAmpVigFlujo.aux)
																		                    ||endAmpVigFlujo.aux.indexOf('onComprar')==-1){
													   						    	 //myMask.hide();
													   						       //  var jsonResp = Ext.decode(response.responseText);
													   						         
													   						         var callbackRemesa = function()
													   						         {
													   						             marendNavegacion(2);
													   						         };
													   						         
													   						      	 mensajeCorrecto("Endoso",jsonResp.respuesta,function()
													   						      	 {
													   						      	     _generarRemesaClic(
													   						      	         true
													   						      	         ,paramsEntrada.CDUNIECO
													   						      	         ,paramsEntrada.CDRAMO
													   						      	         ,paramsEntrada.ESTADO
													   						      	         ,paramsEntrada.NMPOLIZA
													   						      	         ,callbackRemesa
													   						      	     );
													   						      	 });
															                    }else{
															                    	//
															                    	debug('endAmpVigFlujoAux.endosoAutorizar: ',endAmpVigFlujoAux.endosoAutorizar);
																                    var ck = 'Turnando tr\u00e1mite';
																                    try
																                    {
																                        var status = endAmpVigFlujoAux.endosoAutorizar.split('_')[1];
																                        debug('status para turnar onComprar:',status,'.');
																                        
																                        _mask(ck);
																                        debug(' Turnando tr\u00e1mite endAmpVigFlujoAux: ',endAmpVigFlujoAux);
																                        Ext.Ajax.request(
																                        {
																                            url      : _GLOBAL_COMP_URL_TURNAR
																                            ,params  :
																                            {
																                                'params.CDTIPFLU'   : endAmpVigFlujo.cdtipflu
																                                ,'params.CDFLUJOMC' : endAmpVigFlujo.cdflujomc
																                                ,'params.NTRAMITE'  : endAmpVigFlujo.ntramite
																                                ,'params.STATUSOLD' : endAmpVigFlujo.status
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
																	                    
																                    sacaEndoso(paramsEntrada.CDUNIECO,
																                               paramsEntrada.CDRAMO,
																                               paramsEntrada.ESTADO,
																                               paramsEntrada.NMPOLIZA,
																                               jsonResp1.smap2.pv_nmsuplem_o,
																                               jsonResp1.smap2.pv_nsuplogi_o);
															                    }
												   						    	
												   						    },
												   						    failure:function(response,opts){
												   						        myMask.hide();
												   						        Ext.Msg.show({
												   						            title:'Error',
												   						            msg: 'Error de comunicaci&oacute;n',
												   						            buttons: Ext.Msg.OK,
												   						            icon: Ext.Msg.ERROR
												   						        });
												   						    }
												   						});
												   						myMask1.close();
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
															                         +'src="'+_p30_urlViewDoc+"?&path="+_RUTA_DOCUMENTOS_TEMPORAL+"&filename="+jsonResp1.smap2.pdfEndosoNom_o+"\">"
															                         +'</iframe>'
															        ,listeners     : {
															        	resize : function(win,width,height,opt){
															                debug(width,height);
															                $('[innerframe="'+numRand+'"]').attr({'width':width-20,'height':height-60});
															        }
															      }}).show());
															}
															,hidden   : paramsEntrada.TIPOFLOT!= TipoFlotilla.Flotilla? false :true
					                                        } ]
										     }).show();
										     
	   						    },
	   						    failure:function(response,opts){
	   						        myMask.hide();
	   						        Ext.Msg.show({
	   						            title:'Error',
	   						            msg: 'Error de comunicaci&oacute;n',
	   						            buttons: Ext.Msg.OK,
	   						            icon: Ext.Msg.ERROR
	   						        });
	   						    }
	   						});
						    
						    
						}else{
							
							myMask.hide();
						    //Error
							centrarVentanaInterna(Ext.Msg.show({
		                        title:'Error',
		                        msg: 'La fecha de Ampliaci&oacute;n de Vigencia debe ser mayor a la Fecha Fin',
		                        buttons: Ext.Msg.OK,
		                        icon: Ext.Msg.WARNING
		                    }));
						}
					}else {
						
						myMask.hide();
						
						Ext.Msg.show({
							title: 'Aviso',
							msg: 'Complete la informaci&oacute;n requerida',
							buttons: Ext.Msg.OK,
							icon: Ext.Msg.WARNING
						});
					}
				}
			}]
		});

		
    });
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
<%@ include file="/jsp-script/proceso/documentos/scriptImpresionRemesaEmisionEndoso.jsp"%>
</script>
<div id="maindivAmpVig" style="height:1000px;"></div>