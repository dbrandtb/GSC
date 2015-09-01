<%@ include file="/taglibs.jsp"%>
<s:if test="false">
<script>
</s:if>
var _CONTEXT = '${ctx}';


/* ******************** CATALOGOS ******************** */

// Catalogo Tipos de pago a utilizar:
var _PAGO_DIRECTO = '<s:property value="@mx.com.gseguros.portal.general.util.TipoPago@DIRECTO.codigo" />';
var _REEMBOLSO    = '<s:property value="@mx.com.gseguros.portal.general.util.TipoPago@REEMBOLSO.codigo" />';
var _INDEMNIZACION= '<s:property value="@mx.com.gseguros.portal.general.util.TipoPago@INDEMNIZACION.codigo" />';
var _URL_CATALOGOS                      = '<s:url namespace="/catalogos"   action="obtieneCatalogo" />';
// Catalogo Estatus de tramite a utilizar:
var _STATUS_TRAMITE_EN_REVISION_MEDICA      = '<s:property value="@mx.com.gseguros.portal.general.util.EstatusTramite@EN_REVISION_MEDICA.codigo" />';
var _STATUS_TRAMITE_RECHAZADO               = '<s:property value="@mx.com.gseguros.portal.general.util.EstatusTramite@RECHAZADO.codigo" />';
var _STATUS_TRAMITE_EN_ESPERA_DE_ASIGNACION = '<s:property value="@mx.com.gseguros.portal.general.util.EstatusTramite@EN_ESPERA_DE_ASIGNACION.codigo" />';
var _STATUS_TRAMITE_CONFIRMADO              = '<s:property value="@mx.com.gseguros.portal.general.util.EstatusTramite@CONFIRMADO.codigo" />';
var _CAT_DESTINOPAGO                        = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@DESTINOPAGO"/>';
var _CAT_CONCEPTO                           = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@CATCONCEPTO"/>';
var _CATALOGO_CONCEPTOPAGO					= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@CONCEPTOPAGO"/>';
var _URL_LISTADO_ASEGURADO_POLIZA			= '<s:url namespace="/siniestros"       action="consultaListaAseguradoPoliza" />';
var _URL_CONSULTA_BENEFICIARIO				= '<s:url namespace="/siniestros"		action="consultaDatosBeneficiario" />';

/* *************************************************** */

// URLs:
var _URL_CATALOGOS              		= '<s:url namespace="/catalogos"   	action="obtieneCatalogo" />';
var _URL_ActualizaStatusTramite 		= '<s:url namespace="/mesacontrol" 	action="actualizarStatusTramite" />';
//AUTORIZACION DE SERVICIO
var _UrlValidaDocumentosCargados		= '<s:url namespace="/siniestros" 	action="validaDocumentosCargados"        />';
var _URL_CONSULTA_TRAMITE       		= '<s:url namespace="/siniestros"   action="consultaListadoMesaControl" />';
var _URL_NOMBRE_TURNADO   				= '<s:url namespace="/siniestros" 	action="obtieneUsuarioTurnado" />';

//SINIESTROS
var _UrlRechazarTramiteWindwow  		= '<s:url namespace="/siniestros" 	action="includes/rechazoReclamaciones" />';
var _UrlDetalleSiniestro        		= '<s:url namespace="/siniestros" 	action="detalleSiniestro" />';
var _UrlDetalleSiniestroDirecto 		= '<s:url namespace="/siniestros" 	action="afiliadosAfectados"        />';
var _UrlSolicitarPago           		= '<s:url namespace="/siniestros" 	action="solicitarPago"             />';
var _URL_CONCEPTODESTINO        		= '<s:url namespace="/siniestros"   action="guardarConceptoDestino" />';
var _selCobUrlAvanza              		= '<s:url namespace="/siniestros" 	action="afiliadosAfectados"/>';
var _urlSeleccionCobertura      		= '<s:url namespace="/siniestros" 	action="seleccionCobertura"        />';
var _URL_VAL_AJUSTADOR_MEDICO			= '<s:url namespace="/siniestros" 	action="consultaDatosValidacionAjustadorMed"/>';
var _URL_INF_ASEGURADO					= '<s:url namespace="/siniestros" 	action="consultaDatosAseguradoSiniestro"/>';
var _URL_POLIZA_UNICA					= '<s:url namespace="/siniestros"	action="consultaPolizaUnica"/>';
var _URL_MONTO_PAGO_SINIESTRO			= '<s:url namespace="/siniestros"	action="obtieneMontoPagoSiniestro"/>';
var _URL_P_MOV_MAUTSINI					= '<s:url namespace="/siniestros"	action="obtieneMensajeMautSini"/>';
var windowLoader;
var msgWindow;

	_4_botonesGrid =
	[
        <s:property value="imap1.gridbuttons" />
	];
	
	function rechazarTramiteWindow(grid,rowIndex,colIndex){
		
		var record = grid.getStore().getAt(rowIndex);
		if(record.get('status') == _STATUS_TRAMITE_RECHAZADO){
			mensajeWarning('Este tr&aacute;mite ya se encuentra rechazado!');
			return;
		}
		
		windowLoader = Ext.create('Ext.window.Window',{
	        modal       : true,
	        buttonAlign : 'center',
	        width       : 700,
	        height      : 500,
	        autoScroll  : true,
	        loader      : {
	            url     : _UrlRechazarTramiteWindwow,
	            params  : {
	            	'params.cdunieco' : record.get('cdunieco'),
		    		'params.cdramo'   : record.get('cdramo'),
		    		'params.estado'   : record.get('estado'),
		    		'params.nmpoliza' : record.get('nmpoliza'),
		    		'params.nmsuplem' : record.get('nmsuplem'),
		    		'params.nmsolici' : record.get('nmsolici'),
	                'params.nmTramite'  : record.get('ntramite'),
	                'params.tipopago'   : record.get('parametros.pv_otvalor02')
	            },
	            scripts  : true,
	            loadMask : true,
	            autoLoad : true,
	            ajaxOptions: {
	            	method: 'POST'
	            }
	        }
	    }).show();
		
		centrarVentana(windowLoader);
	}
	
	function detalleReclamacionWindow(grid,rowIndex,colIndex)
	{
		var record = grid.getStore().getAt(rowIndex);
		debug('record Valor de respuesta :',record.data);
		debug("Valor de Respuesta -->",record.get('cdunieco'));
		Ext.Ajax.request({
			url	 : _URL_INF_ASEGURADO
			,params:{
				'params.ntramite': record.get('ntramite')
			}
			,success : function (response)
			{
				if(Ext.decode(response.responseText).datosValidacion != null){
					var json = Ext.decode(response.responseText).datosValidacion[0];
					debug("Valor del Json --> ",json);
					//
					
					var formapago = json.OTVALOR02;
					debug('formapago:',formapago);
					
					var esPagoDirecto = formapago == _PAGO_DIRECTO;
					debug('esPagoDirecto:',esPagoDirecto ? 'si' : 'no');
					
					var params = {};
					
					params['params.tipopago'] = formapago;
					params['params.ntramite'] = json.NTRAMITE;
					
					var conCoberYSubcober = false;
					
					debug('conCoberYSubcober:',conCoberYSubcober ? 'si' : 'no');
					
					var urlDestino;
					if(esPagoDirecto||true) {
						if(true){
							if(esPagoDirecto){
								urlDestino = _UrlDetalleSiniestroDirecto;
								debug('urlDestino_1 :',urlDestino);
								debug('params_1 :',params);
								Ext.create('Ext.form.Panel').submit({
									url             : urlDestino
									,params         : params
								    ,standardSubmit : true
								});
							}
							else{
								// Pago diferente a Directo
								if(json.OTVALOR12 && json.OTVALOR12.length>0){
									conCoberYSubcober = true;
								}
								
								if(conCoberYSubcober){ // true
									urlDestino = _selCobUrlAvanza;
									debug('urlDestino_2 :',urlDestino);
									debug('params_2:',params);
									Ext.create('Ext.form.Panel').submit({
										url             : urlDestino
										,params         : params
									    ,standardSubmit : true
									});
								}else{
									//PASAMOS LOS VALORES PARA SELECCIONAR LA COBERTURA Y SUBCOBERTURA
									Ext.Ajax.request( {
										url     : _URL_POLIZA_UNICA
										,params : {
											'params.cdunieco': record.get('cdunieco'),
											'params.cdramo'  : json.CDRAMO,
											'params.estado'  : json.ESTADO,
											'params.nmpoliza': json.NMPOLIZA,
											'params.cdperson': json.CDPERSON
										}
										,success : function (response){
											if(Ext.decode(response.responseText).polizaUnica != null) {
												var jsonValorAsegurado = Ext.decode(response.responseText).polizaUnica[0];
												debug("Valor de respuesta ---> : ",jsonValorAsegurado);
												
												urlDestino = _urlSeleccionCobertura;
												params['params.cdunieco']  = record.get('cdunieco');//record.get('cdsucdoc');
												params['params.otvalor02'] = json.OTVALOR02;//record.get('parametros.pv_otvalor02');
												params['params.cdramo']    = json.CDRAMO;//record.get('cdramo');
												params['params.cdtipsit']  = json.CDTIPSIT;//record.get('cdtipsit');
												params['params.nmpoliza']  = json.NMPOLIZA;
												params['params.nmsituac']  = json.NMSITUAC;
												params['params.estado']    = json.ESTADO;
												params['params.periodoEspera']    = jsonValorAsegurado.diasAsegurado;
												params['params.feocurre']    = json.FEOCURRE;
												debug('urlDestino_4 :',urlDestino);
												debug('params_4 :',params);
												Ext.create('Ext.form.Panel').submit(
												{
													url             : urlDestino
													,params         : params
												    ,standardSubmit : true
												});
											}
										},
										failure : function (){
											me.up().up().setLoading(false);
											centrarVentanaInterna(Ext.Msg.show({
												title:'Error',
												msg: 'Error de comunicaci&oacute;n',
												buttons: Ext.Msg.OK,
												icon: Ext.Msg.ERROR
											}));
										}
									});
								}
							}
						}
					}
				}
			},
			failure : function (){
				me.up().up().setLoading(false);
				Ext.Msg.show({
					title:'Error',
					msg: 'Error de comunicaci&oacute;n',
					buttons: Ext.Msg.OK,
					icon: Ext.Msg.ERROR
				});
			}
		});
	}
	
	function turnarAareaMedica(grid,rowIndex,colIndex){
		var record = grid.getStore().getAt(rowIndex);
		 var comentariosText = Ext.create('Ext.form.field.TextArea', {
        	fieldLabel: 'Observaciones'
    		,labelWidth: 150
    		,width: 600
    		,name:'smap1.comments'
			,height: 250
			,allowBlank : false
        });
		
		windowLoader = Ext.create('Ext.window.Window',{
	        modal       : true,
	        buttonAlign : 'center',
	        width       : 663,
	        height      : 400,
	        autoScroll  : true,
	        items       : [
        	        		Ext.create('Ext.form.Panel', {
        	                title: 'Turnar al Area M&eacute;dica',
        	                width: 650,
        	                url: _URL_ActualizaStatusTramite,
        	                bodyPadding: 5,
        	                items: [comentariosText],
        	        	    buttonAlign:'center',
        	        	    buttons: [{
        	            		text: 'Turnar'
        	            		,icon:_CONTEXT+'/resources/fam3icons/icons/accept.png'
        	            		,buttonAlign : 'center',
        	            		handler: function() {
        	            	    	if (this.up().up().form.isValid()) {
        	            	    		this.up().up().form.submit({
        	            		        	waitMsg:'Procesando...',
        	            		        	params: {
        	            		        		'smap1.ntramite' : record.get('ntramite'), 
        	            		        		'smap1.status'   : _STATUS_TRAMITE_EN_REVISION_MEDICA
        	            		        		,'smap1.rol_destino'     : 'medajustador'
        	            		        		,'smap1.usuario_destino' : colIndex.length>3 ? colIndex : ''
        	            		        	},
        	            		        	failure: function(form, action) {
        	            		        		debug(action);
        	            		        		switch (action.failureType)
                                                {
                                                    case Ext.form.action.Action.CONNECT_FAILURE:
                                                        errorComunicacion();
                                                        break;
                                                    case Ext.form.action.Action.SERVER_INVALID:
                                                    	mensajeError(action.result.mensaje);
                                                    	break;
                                                }
        	            		        		//mensajeError('No se pudo turnar.');
        	            					},
        	            					success: function(form, action) {
        	            						Ext.Ajax.request(
								    	        {
								    	            url     : _URL_NOMBRE_TURNADO
								    	            ,params : 
								    	            {           
								    	                'params.ntramite': record.get('ntramite'),
								    	                'params.rolDestino': 'medajustador'
								    	            }
								    	            ,success : function (response)
								    	            {
								    	                var usuarioTurnadoSiniestro = Ext.decode(response.responseText).usuarioTurnadoSiniestro;
								    	                mensajeCorrecto('Aviso','Se ha turnado con &eacute;xito a: '+usuarioTurnadoSiniestro);
		        	            						loadMcdinStore();
		        	            						windowLoader.close();
								    	            },
								    	            failure : function ()
								    	            {
								    	                me.up().up().setLoading(false);
								    	                centrarVentanaInterna(Ext.Msg.show({
								    	                    title:'Error',
								    	                    msg: 'Error de comunicaci&oacute;n',
								    	                    buttons: Ext.Msg.OK,
								    	                    icon: Ext.Msg.ERROR
								    	                }));
								    	            }
								    	        });
        	            					}
        	            				});
        	            			} else {
        	            				Ext.Msg.show({
        	            	                   title: 'Aviso',
        	            	                   msg: 'Complete la informaci&oacute;n requerida',
        	            	                   buttons: Ext.Msg.OK,
        	            	                   icon: Ext.Msg.WARNING
        	            	               });
        	            			}
        	            		}
        	            	},{
        	            	    text: 'Cancelar',
        	            	    icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
        	            	    buttonAlign : 'center',
        	            	    handler: function() {
        	            	        windowLoader.close();
        	            	    }
        	            	}
        	            	]
        	            })  
	            	]
	    }).show();
		centrarVentana(windowLoader);
	}
	
	function turnarAreclamacionesMedAjustador(grid,rowIndex,colIndex){
		var record = grid.getStore().getAt(rowIndex);
		Ext.Ajax.request({
			url     : _URL_P_MOV_MAUTSINI
			,params : {
				'params.ntramite': record.get('ntramite')
			}
			,success : function (response) {
				var respuestaMensaje = Ext.decode(response.responseText).mensaje;
				Ext.Ajax.request({
					url: _UrlValidaDocumentosCargados,
					params: {
						'params.PV_NTRAMITE_I' : record.get('ntramite'),
						'params.PV_CDRAMO_I'   : record.get('cdramo'),
						'params.PV_cdtippag_I' : record.get('parametros.pv_otvalor02'),
						'params.PV_CDTIPATE_I' : record.get('parametros.pv_otvalor07')
					},
					success: function(response, opt) {
						var jsonRes=Ext.decode(response.responseText);

						if(jsonRes.success == true){
							var comentariosText = Ext.create('Ext.form.field.TextArea', {
			                	fieldLabel: 'Observaciones'
			            		,labelWidth: 150
			            		,width: 600
			            		,name:'smap1.comments'
			            		, value : respuestaMensaje
			        			,height: 250
			        			,allowBlank : false
			                });
			        		
			        		windowLoader = Ext.create('Ext.window.Window',{
			        	        modal       : true,
			        	        buttonAlign : 'center',
			        	        width       : 663,
			        	        height      : 400,
			        	        autoScroll  : true,
			        	        items       : [
					        	        		Ext.create('Ext.form.Panel', {
					        	                title: 'Turnar a Coordinador de Reclamaciones',
					        	                width: 650,
					        	                url: _URL_ActualizaStatusTramite,
					        	                bodyPadding: 5,
					        	                items: [comentariosText],
					        	        	    buttonAlign:'center',
					        	        	    buttons: [{
					        	            		text: 'Turnar'
					        	            		,icon:_CONTEXT+'/resources/fam3icons/icons/accept.png'
					        	            		,buttonAlign : 'center',
					        	            		handler: function() {
					        	            			var formPanel = this.up().up();
					        	            	    	if (formPanel.form.isValid()) {
					        	            	    		formPanel.form.submit({
									        	            		        	waitMsg:'Procesando...',
									        	            		        	params: {
									        	            		        		'smap1.ntramite' : record.get('ntramite'), 
									        	            		        		'smap1.status'   : _STATUS_TRAMITE_EN_ESPERA_DE_ASIGNACION
									        	            		        	},
									        	            		        	failure: function(form, action)
									        	            		        	{
									        	            		        		debug(action);
									        	            		        		switch (action.failureType)
									        	            		        		{
									        	            		        		    case Ext.form.action.Action.CONNECT_FAILURE:
									        	            		        		    	errorComunicacion();
										        	            		                    break;
										        	            		                case Ext.form.action.Action.SERVER_INVALID:
										        	            		                	mensajeError(action.result.mensaje);
										        	            		                	break;
										        	            		            }
									        	            		        		//mensajeError('No se pudo turnar.');
									        	            					},
									        	            					success: function(form, action) {
									        	            						mensajeCorrecto('Aviso','Se ha turnado con &eacute;xito.');
									        	            						loadMcdinStore();
									        	            						windowLoader.close();
									        	            						
									        	            					}
								        	            					});
					        	            			} else {
					        	            				Ext.Msg.show({
					        	            	                   title: 'Aviso',
					        	            	                   msg: 'Complete la informaci&oacute;n requerida',
					        	            	                   buttons: Ext.Msg.OK,
					        	            	                   icon: Ext.Msg.WARNING
					        	            	               });
					        	            			}
					        	            		}
					        	            	},{
					        	            	    text: 'Cancelar',
					        	            	    icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
					        	            	    buttonAlign : 'center',
					        	            	    handler: function() {
					        	            	        windowLoader.close();
					        	            	    }
					        	            	}
					        	            	]
					        	            })  
			        	            	]
			        	    	}).show();
			        		
			        			centrarVentana(windowLoader);
							}else {
								mensajeError(jsonRes.msgResult);
							}
					},
					failure: function(){
						mensajeError('Error al turnar.');
					}
				});
			},
			failure : function (){
				centrarVentanaInterna(Ext.Msg.show({
					title:'Error',
					msg: 'Error de comunicaci&oacute;n',
					buttons: Ext.Msg.OK,
					icon: Ext.Msg.ERROR
				}));
			}
		});
	}
	
	function solicitarPago(grid,rowIndex,colIndex){
		var record = grid.getStore().getAt(rowIndex);
		debug("Valor del record :",record);
		if(record.get('status') == _STATUS_TRAMITE_CONFIRMADO){
			mensajeWarning('Ya se ha solicitado el pago para este tr&aacute;mite.');	
			return;
		}else{
			//validamos los montos 
			Ext.Ajax.request({
				url	: _URL_MONTO_PAGO_SINIESTRO
				,params:{
					'params.ntramite' : record.get('ntramite'),
					'params.cdramo'   : record.get('cdramo'),
					'params.tipoPago' : record.get('parametros.pv_otvalor02')
				}
				,success : function (response){
					var jsonRespuesta =Ext.decode(response.responseText);//.datosInformacionAdicional[0];
					debug("Valor de Respuesta", jsonRespuesta);
					
					if(jsonRespuesta.success == true){
						if( record.get('parametros.pv_otvalor02') ==_PAGO_DIRECTO){
							mostrarSolicitudPago(grid,rowIndex,colIndex);
						}else{
							Ext.Ajax.request({
								url	 : _URL_VAL_AJUSTADOR_MEDICO
								,params:{
									'params.ntramite': record.get('ntramite')
								}
								,success : function (response)
								{
									if(Ext.decode(response.responseText).datosValidacion != null){
										var autAM = null;
										var result ="";
										banderaValidacion = "0";
										var json = Ext.decode(response.responseText).datosValidacion;
										if(json.length > 0){
											for(var i = 0; i < json.length; i++){
												if(json[i].AREAAUTO =="ME"){
													var valorValidacion = json[i].SWAUTORI+"";
													if(valorValidacion == null || valorValidacion == ''|| valorValidacion == 'null'){
														banderaValidacion = "1";
														result = result + 'El m&eacute;dico no autoriza la factura ' + json[i].NFACTURA + '<br/>';
													}
												}
											}
											if(banderaValidacion == "1"){
												centrarVentanaInterna(mensajeWarning(result));
											}else{
												mostrarSolicitudPago(grid,rowIndex,colIndex);
											}
										}else{
											centrarVentanaInterna(mensajeWarning('El m&eacute;dico no ha autizado la factura'));
										}
									}
								},
								failure : function (){
									me.up().up().setLoading(false);
									Ext.Msg.show({
										title:'Error',
										msg: 'Error de comunicaci&oacute;n',
										buttons: Ext.Msg.OK,
										icon: Ext.Msg.ERROR
									});
								}
							});
						}
					}else {
						centrarVentanaInterna(mensajeWarning(jsonRespuesta.mensaje));
					}
				},
				failure : function (){
					Ext.Msg.show({
						title:'Error',
						msg: 'Error de comunicaci&oacute;n',
						buttons: Ext.Msg.OK,
						icon: Ext.Msg.ERROR
					});
				}
			});
		}
	}
	
	function mostrarSolicitudPago(grid,rowIndex,colIndex){
		storeDestinoPago = Ext.create('Ext.data.JsonStore', {
		    model:'Generic',
	        proxy:
	        {
	            type: 'ajax',
	            url: _URL_CATALOGOS,
	            extraParams : {catalogo:_CAT_DESTINOPAGO},
	            reader:
	            {
	                type: 'json',
	                root: 'lista'
	            }
	        }
	    });
		storeDestinoPago.load();
		
		storeConceptoPago = Ext.create('Ext.data.JsonStore', {
			model:'Generic',
			autoLoad:true,
			proxy: {
				type: 'ajax',
				url: _URL_CATALOGOS,
				extraParams : {catalogo:_CATALOGO_CONCEPTOPAGO},
				reader: {
					type: 'json',
					root: 'lista'
				}
			}
		});
		
		storeAsegurados2 = Ext.create('Ext.data.Store', {
			model:'Generic',
			autoLoad:false,
			proxy: {
				type: 'ajax',
				url : _URL_LISTADO_ASEGURADO_POLIZA,
				reader: {
					type: 'json',
					root: 'listaAsegurado'
				}
			}
		});
		
		msgWindow = Ext.Msg.show({
	        title: 'Aviso',
	        msg: '&iquest;Esta seguro que desea solicitar el pago?',
	        buttons: Ext.Msg.YESNO,
	        icon: Ext.Msg.QUESTION,
	        fn: function(buttonId, text, opt){
	        	if(buttonId == 'yes'){
	        		var record = grid.getStore().getAt(rowIndex);
	        		var recordAdicional = grid.getStore().getAt(rowIndex);
	        		
	        		debug("VALOR DEL RECORD :",record);
	        		debug("VALOR DEL RECORD :",record.raw);
	        		
	        		storeConceptoPago.load({
						params : {
							'params.cdramo': record.get('cdramo')
						}
					});
	        		
	        		storeAsegurados2.load({
						params:{
							'params.cdunieco': record.raw.cdunieco,
							'params.cdramo': record.raw.cdramo,
							'params.estado': record.raw.estado,
							'params.nmpoliza': record.raw.nmpoliza
						}
					});
	        		
	        		var pagocheque = Ext.create('Ext.form.field.ComboBox',
   		    	    {
   		    	        colspan	   :2,				fieldLabel   	: 'Destino Pago', 	name			:'destinoPago',
   		    	        allowBlank : false,			editable     	: true,			displayField    : 'value',
   		    	        valueField:'key',			forceSelection  : true,			width			:350,
   		    	        queryMode    :'local',		store 			: storeDestinoPago
   		    	    });
   		    		
   		    		var concepPago = Ext.create('Ext.form.field.ComboBox',
   		    	    {
   		    	        colspan	   :2,				fieldLabel   	: 'Concepto Pago', 	name			:'concepPago',
   		    	        allowBlank : false,			editable     	: true,			displayField    : 'value',
   		    	        valueField:'key',			forceSelection  : true,			width			:350,
   		    	        queryMode    :'local',		store 			: storeConceptoPago
   		    	    });
	        		
   		    		var cmbBeneficiario= Ext.create('Ext.form.ComboBox',{
						name:'cmbBeneficiario',			fieldLabel: 'Beneficiario',			queryMode: 'local'/*'remote'*/,			displayField: 'value',
						valueField: 'key',				editable:true,						forceSelection : true,		matchFieldWidth: false,
						queryParam: 'params.cdperson',	minChars  : 2, 						store : storeAsegurados2,	triggerAction: 'all',
						width		 : 350,
						allowBlank: record.get('parametros.pv_otvalor02') == _PAGO_DIRECTO,
						hidden : record.get('parametros.pv_otvalor02') == _PAGO_DIRECTO,
						listeners : {
							'select' : function(e) {
								Ext.Ajax.request({
									url     : _URL_CONSULTA_BENEFICIARIO
									,params:{
										'params.cdunieco'  : record.raw.cdunieco,
										'params.cdramo'    : record.raw.cdramo,
										'params.estado'    : record.raw.estado,
										'params.nmpoliza'  : record.raw.nmpoliza,
										'params.cdperson'  : e.getValue()
									}
									,success : function (response) {
										json = Ext.decode(response.responseText);
										if(json.success==false){
											Ext.Msg.show({
												title:'Beneficiario',
												msg: json.mensaje,
												buttons: Ext.Msg.OK,
												icon: Ext.Msg.WARNING
											});
											panelModificacion.query('combo[name=cmbBeneficiario]')[0].setValue('')
										}
									},
									failure : function (){
										me.up().up().setLoading(false);
										centrarVentanaInterna(Ext.Msg.show({
											title:'Error',
											msg: 'Error de comunicaci&oacute;n',
											buttons: Ext.Msg.OK,
											icon: Ext.Msg.ERROR
										}));
									}
								});
							}
						}
					});
   		    		
   		    		var cdramoTramite="";
   		    		var cdtipsitTramite ="";
	        		//LLAMADA A LA MESA DE CONTROL PARA VERIFICAR LOS CAMPOS OTVALOR18 Y OTVALOR19
	        		Ext.Ajax.request({
	        			url     : _URL_CONSULTA_TRAMITE
	        			,params:{
	        				'params.ntramite': record.get('ntramite')
	                    }
	        			,success : function (response)
	        			{
		        			 	if(Ext.decode(response.responseText).listaMesaControl != null)
		        		    	{
		        			    	var json=Ext.decode(response.responseText).listaMesaControl[0];
		        			    	cdramoTramite = json.cdramomc;
		           		    		cdtipsitTramite = json.cdtipsitmc;
		           		    		panelModificacion.query('combo[name=cmbBeneficiario]')[0].setValue(json.otvalor04mc);
		        			    	if(json.otvalor18mc !=null)
	        			    		{
		        			    		panelModificacion.query('combo[name=destinoPago]')[0].setValue(json.otvalor18mc);
	        			    		}
		        			    	
		        			    	if(json.otvalor19mc !=null)
	        			    		{
		        			    		panelModificacion.query('combo[name=concepPago]')[0].setValue(json.otvalor19mc);
	        			    		}
	        		    		}
	        			    },
	        			    failure : function ()
	        			    {
	        			        me.up().up().setLoading(false);
	        			        Ext.Msg.show({
	        			            title:'Error',
	        			            msg: 'Error de comunicaci&oacute;n',
	        			            buttons: Ext.Msg.OK,
	        			            icon: Ext.Msg.ERROR
	        			        });
	        			    }
	        		});
	        		
		    		
			        windowCvePago = Ext.create('Ext.window.Window',{
				        modal       : true,
				        buttonAlign : 'center',
				        width       : 550,
				        autoScroll  : true,
				        items       : [
			        	        		panelModificacion = Ext.create('Ext.form.Panel', {
			        	                title: 'Destino de Pago',
			        	                bodyPadding: 5,
			        	                items: [pagocheque,
			        	                        concepPago,
												cmbBeneficiario],
			        	        	    buttonAlign:'center',
			        	        	    buttons: [{
			        	            		text: 'Solicitar'
			        	            		,icon:_CONTEXT+'/resources/fam3icons/icons/accept.png'
			        	            		,buttonAlign : 'center',
			        	            		handler: function() { 
			        	            			if (panelModificacion.form.isValid()) {
			        	            				var datos=panelModificacion.form.getValues();
			        	            				
			        	            				Ext.Ajax.request({
			        	        	        			url     : _URL_CONCEPTODESTINO
			        	        	        			,jsonData: {
			        	        	        				params:{
			        	        	        					ntramite:record.get('ntramite'),
			        	        	        					cdtipsit:cdtipsitTramite,
			        	        	        					destinoPago:datos.destinoPago,
			        	        	        					concepPago:datos.concepPago,
			        	        	        					beneficiario : datos.cmbBeneficiario,
																tipoPago : record.get('parametros.pv_otvalor02')
			        	        	        				}
	        	                                        }
			        	        	        			,success : function (response)
			        	        	        			{
			        	        		        			 	
			        	        	        				windowCvePago.close();
			        	        	        				mcdinGrid.setLoading(true);
			        	        	     	        		Ext.Ajax.request({
			        	        	     						url: _UrlSolicitarPago,
			        	        	     						params: {
			        	        	     				    		'params.pv_ntramite_i' : record.get('ntramite'),
			        	        	     				    		'params.pv_tipmov_i'   : record.get('parametros.pv_otvalor02')
			        	        	     				    	},
			        	        	     						success: function(response, opts) {
			        	        	     							mcdinGrid.setLoading(false);
			        	        	     							var respuesta = Ext.decode(response.responseText);
			        	        	     							if(respuesta.success){
			        	        	     								mensajeCorrecto('Aviso','El pago se ha solicitado con &eacute;xito.');	
			        	        	     							}else {
			        	        	     								mensajeError(respuesta.mensaje);
			        	        	     							}
			        	        	     							
			        	        	     						},
			        	        	     						failure: function(){
			        	        	     							mcdinGrid.setLoading(false);
			        	        	     							mensajeError('No se pudo solicitar el pago.');
			        	        	     						}
			        	        	     					});
			        	        	        			    },
			        	        	        			    failure : function ()
			        	        	        			    {
			        	        	        			        me.up().up().setLoading(false);
			        	        	        			        Ext.Msg.show({
			        	        	        			            title:'Error',
			        	        	        			            msg: 'Error de comunicaci&oacute;n',
			        	        	        			            buttons: Ext.Msg.OK,
			        	        	        			            icon: Ext.Msg.ERROR
			        	        	        			        });
			        	        	        			    }
			        	        	        		});
			        	            			}else {
			        	                            Ext.Msg.show({
			        	                                   title: 'Aviso',
			        	                                   msg: 'Complete la informaci&oacute;n requerida',
			        	                                   buttons: Ext.Msg.OK,
			        	                                   icon: Ext.Msg.WARNING
			        	                               });
			        	                        }
			        	            		}
			        	            	},{
			        	            	    text: 'Cancelar',
			        	            	    icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
			        	            	    buttonAlign : 'center',
			        	            	    handler: function() {
			        	            	        windowCvePago.close();
			        	            	    }
			        	            	}
			        	            	]
			        	            })  
				            	]
				    }).show();
					centrarVentana(windowCvePago);

	        	}
	        	
	        }
	    });
		centrarVentana(msgWindow);
	}
	
Ext.onReady(function()
		{
			
			/////////////////////
			////// modelos //////
			Ext.define('DetalleMC',{
		        extend:'Ext.data.Model',
		        fields:
		        [
		            "NTRAMITE"
		            ,"NMORDINA"
		            ,"CDTIPTRA"
		            ,"CDCLAUSU"
		            ,{name:"FECHAINI",type:'date',dateFormat:'d/m/Y'}
		            ,{name:"FECHAFIN",type:'date',dateFormat:'d/m/Y'}
		            ,"COMMENTS"
		            ,"CDUSUARI_INI"
		            ,"CDUSUARI_FIN"
		            ,"usuario_ini"
		            ,"usuario_fin"
		        ]
		    });
		    ////// modelos //////
			/////////////////////
		});
<s:if test="false">
</script>
</s:if>