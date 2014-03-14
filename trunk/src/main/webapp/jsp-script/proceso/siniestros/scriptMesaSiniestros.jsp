<%@ include file="/taglibs.jsp"%>
<s:if test="false">
<script>
</s:if>
var _CONTEXT = '${ctx}';


/* ******************** CATALOGOS ******************** */

// Catalogo Tipos de pago a utilizar:
var _PAGO_DIRECTO = '<s:property value="@mx.com.gseguros.portal.general.util.TipoPago@PAGO_DIRECTO.codigo" />';
var _REEMBOLSO    = '<s:property value="@mx.com.gseguros.portal.general.util.TipoPago@REEMBOLSO.codigo" />';

// Catalogo Estatus de tramite a utilizar:
var _STATUS_TRAMITE_EN_REVISION_MEDICA      = '<s:property value="@mx.com.gseguros.portal.general.util.EstatusTramite@EN_REVISION_MEDICA.codigo" />';
var _STATUS_TRAMITE_RECHAZADO               = '<s:property value="@mx.com.gseguros.portal.general.util.EstatusTramite@RECHAZADO.codigo" />';
var _STATUS_TRAMITE_EN_CAPTURA              = '<s:property value="@mx.com.gseguros.portal.general.util.EstatusTramite@EN_CAPTURA.codigo" />';
var _STATUS_TRAMITE_EN_ESPERA_DE_ASIGNACION = '<s:property value="@mx.com.gseguros.portal.general.util.EstatusTramite@EN_ESPERA_DE_ASIGNACION.codigo" />';

// Catalogo Tipos de tramite a utilizar:
var _TIPO_TRAMITE_SINIESTRO = '<s:property value="@mx.com.gseguros.portal.general.util.TipoTramite@SINIESTRO.codigo" />';
/* *************************************************** */

// URLs:
var _UrlAltaDeTramite           = '<s:url namespace="/siniestros" action="includes/altaTramite"      />';
var _UrlRevisionDocsSiniestro   = '<s:url namespace="/siniestros" action="includes/revisionDocumentos"        />';
var _UrlValidaDocumentosCargados= '<s:url namespace="/siniestros" action="validaDocumentosCargados"        />';
var _UrlRechazarTramiteWindwow  = '<s:url namespace="/siniestros" action="includes/rechazoReclamaciones" />';
var _UrlDocumentosPoliza        = '<s:url namespace="/documentos" action="ventanaDocumentosPoliza"   />';
var _UrlGenerarContrarecibo     = '<s:url namespace="/siniestros" action="generarContrarecibo"       />';
var _UrlDetalleSiniestro        = '<s:url namespace="/siniestros" action="detalleSiniestro" />';
var _UrlDetalleSiniestroDirecto = '<s:url namespace="/siniestros" action="afiliadosAfectados"        />';
var _UrlSolicitarPago           = '<s:url namespace="/siniestros" action="solicitarPago"             />';
var _urlSeleccionCobertura      = '<s:url namespace="/siniestros" action="seleccionCobertura"        />';

var _UrlGeneraSiniestroTramite =      '<s:url namespace="/siniestros" action="generaSiniestroTramite" />';
var _URL_ActualizaStatusTramite =      '<s:url namespace="/mesacontrol" action="actualizarStatusTramite" />';

var panDocUrlViewDoc     = '<s:url namespace ="/documentos" action="descargaDocInline" />';

var windowLoader;
var msgWindow;

	_4_botonesGrid =
	[
        <s:property value="imap1.gridbuttons" />
	];
	
	function altaTramiteWindow(){
	    windowLoader = Ext.create('Ext.window.Window',{
	        modal       : true,
	        buttonAlign : 'center',
	        width       : 800,
	        height      : 730,
	        autoScroll  : true,
	        loader      : {
	            url     : _UrlAltaDeTramite,
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
	
	function complementarAltaWindow(){
	    windowLoader = Ext.create('Ext.window.Window',{
	        modal       : true,
	        buttonAlign : 'center',
	        width       : 800,
	        height      : 730,
	        autoScroll  : true,
	        loader      : {
	            url     : _UrlAltaDeTramite,
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
	
	
	function revDocumentosWindow(grid,rowIndex,colIndex){
	    var record = grid.getStore().getAt(rowIndex);
	    
	    windowLoader = Ext.create('Ext.window.Window',{
	        modal       : true,
	        buttonAlign : 'center',
	        width       : 600,
	        height      : 400,
	        autoScroll  : true,
	        loader      : {
	            url     : _UrlRevisionDocsSiniestro,
	            params  : {
	                'params.nmTramite'  : record.get('ntramite'),
	                'params.cdTipoPago' : record.get('parametros.pv_otvalor02'),
	                'params.cdTipoAtencion'  : record.get('parametros.pv_otvalor07'),
	                'params.tieneCR'  : !Ext.isEmpty(record.get('parametros.pv_otvalor01'))
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
	
	function documentosWindow(grid,rowIndex,colIndex){
		var record = grid.getStore().getAt(rowIndex);
	    debug('record',record);
	    
	    windowLoader = Ext.create('Ext.window.Window',{
	        modal       : true,
	        buttonAlign : 'center',
	        title       : 'Documentos del siniestro',
	        width       : 600,
	        height      : 400,
	        autoScroll  : true,
	        loader      : {
	            url     : _UrlDocumentosPoliza,
	            params  : {
	                'smap1.ntramite'  : record.get('ntramite')
	                ,'smap1.cdtippag' : record.get('parametros.pv_otvalor02')
	                ,'smap1.cdtipate' : record.get('parametros.pv_otvalor07')
	                ,'smap1.cdtiptra' : _TIPO_TRAMITE_SINIESTRO
                    ,'smap1.cdunieco' : record.get('cdsucdoc')
                    ,'smap1.cdramo'   : record.get('cdramo')
                    ,'smap1.estado'   : record.get('estado')
	                ,'smap1.nmpoliza' : record.get('nmpoliza')
                    ,'smap1.nmsuplem' : '0'
                    ,'smap1.nmsolici' : ''
                    ,'smap1.tipomov'  : record.get('parametros.pv_otvalor02')
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
	
	function generaContrareciboWindow(grid,rowIndex,colIndex){
		
		var record = grid.getStore().getAt(rowIndex);
		msgWindow = Ext.Msg.show({
	        title: 'Aviso',
	        msg: '&iquest;Esta seguro que desea generar el contrarecibo?',
	        buttons: Ext.Msg.YESNO,
	        icon: Ext.Msg.QUESTION,
	        fn: function(buttonId, text, opt){
	        	if(buttonId == 'yes'){
	        		
	        		Ext.Ajax.request({
						url: _UrlGenerarContrarecibo,
						params: {
					    		'paramsO.pv_cdunieco_i' : record.get('cdunieco'),
					    		'paramsO.pv_cdramo_i'   : record.get('cdramo'),
					    		'paramsO.pv_estado_i'   : record.get('estado'),
					    		'paramsO.pv_nmpoliza_i' : record.get('nmpoliza'),
					    		'paramsO.pv_nmsuplem_i' : record.get('nmsuplem'),
					    		'paramsO.pv_ntramite_i' : record.get('ntramite'),
					    		'paramsO.pv_nmsolici_i' : record.get('nmsolici'),
					    		'paramsO.pv_cdtippag_i' : record.get('parametros.pv_otvalor02'),
					    		'paramsO.pv_cdtipate_i' : record.get('parametros.pv_otvalor07'),
					    		'paramsO.pv_tipmov_i'   : record.get('parametros.pv_otvalor02')
					    	
						},
						success: function(response, opt) {
							var jsonRes=Ext.decode(response.responseText);

							if(jsonRes.success == true){
								loadMcdinStore();
								
								var numRand=Math.floor((Math.random()*100000)+1);
					        	debug('numRand a: ',numRand);
					        	var windowVerDocu=Ext.create('Ext.window.Window',
					        	{
					        		title          : 'Contrarecibo de Documentos del Siniestro'
					        		,width         : 700
					        		,height        : 500
					        		,collapsible   : true
					        		,titleCollapse : true
					        		,html          : '<iframe innerframe="'+numRand+'" frameborder="0" width="100" height="100"'
					        		                 +'src="'+panDocUrlViewDoc+'?idPoliza=' + record.get('ntramite') + '&filename=' + '<s:text name="siniestro.contrarecibo.nombre"/>' +'">'
					        		                 +'</iframe>'
					        		,listeners     :
					        		{
					        			resize : function(win,width,height,opt){
					                        debug(width,height);
					                        $('[innerframe="'+numRand+'"]').attr({'width':width-20,'height':height-60});
					                    }
					        		}
					        	}).show();
					        	windowVerDocu.center();
       						}else {
       							mensajeError(jsonRes.msgResult);
       						}
						},
						failure: function(){
							mensajeError('No se pudo generar contrarecibo.');
						}
					});
	        	}
	        	
	        }
	    });
		centrarVentana(msgWindow);
		
	}
	
	function turnarAreclamaciones(grid,rowIndex,colIndex){
		var record = grid.getStore().getAt(rowIndex);
		
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
	        			,height: 250
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
			        	            	    		
			        	            	    		if(record.get('parametros.pv_otvalor02') == _REEMBOLSO){
			        	            	    			Ext.Ajax.request({
			        	            						url: _UrlGeneraSiniestroTramite,
			        	            						params: {
			        	            							'params.pv_ntramite_i' : record.get('ntramite')
			        	            						},
			        	            						success: function(response, opt) {
			        	            							
			        	            							var jsonRes=Ext.decode(response.responseText);

			        	            							if(jsonRes.success == true){
				        	            							formPanel.form.submit({
							        	            		        	waitMsg:'Procesando...',
							        	            		        	params: {
							        	            		        		'smap1.ntramite' : record.get('ntramite'), 
							        	            		        		'smap1.status'   : _STATUS_TRAMITE_EN_ESPERA_DE_ASIGNACION
							        	            		        	},
							        	            		        	failure: function(form, action) {
							        	            		        		mensajeError('No se pudo turnar.');
							        	            					},
							        	            					success: function(form, action) {
							        	            						mensajeCorrecto('Aviso','Se ha turnado con exito.');
							        	            						loadMcdinStore();
							        	            						windowLoader.close();
							        	            						
							        	            					}
						        	            					});
			        	            							}else{
			        	            								mensajeError('Error al generar Siniestro para Area de Reclamaciones');
			        	            							}
			        	            						},
			        	            						failure: function(){
			        	            							mensajeError('Error al generar Siniestro para Area de Reclamaciones');
			        	            						}
			        	            					});
			        	            	    			
			        	            	    		}else{
			        	            	    			formPanel.form.submit({
				        	            		        	waitMsg:'Procesando...',
				        	            		        	params: {
				        	            		        		'smap1.ntramite' : record.get('ntramite'), 
				        	            		        		'smap1.status'   : _STATUS_TRAMITE_EN_ESPERA_DE_ASIGNACION
				        	            		        	},
				        	            		        	failure: function(form, action) {
				        	            		        		mensajeError('No se pudo turnar.');
				        	            					},
				        	            					success: function(form, action) {
				        	            						mensajeCorrecto('Aviso','Se ha turnado con exito.');
				        	            						loadMcdinStore();
				        	            						windowLoader.close();
				        	            						
				        	            					}
			        	            					});
			        	            	    		}
			        	            	    		
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
	        	
	}
	
	function detalleReclamacionWindow(grid,rowIndex,colIndex)
	{
		var record = grid.getStore().getAt(rowIndex);
		debug('record:',record.data);
		
		var formapago = record.get('parametros.pv_otvalor02');
		debug('formapago:',formapago);
		
		var esPagoDirecto = formapago == _PAGO_DIRECTO;
		debug('esPagoDirecto:',esPagoDirecto ? 'si' : 'no');
		
		var params = {};
		
		params['params.tipopago'] = formapago;
		
		var conCoberYSubcober = false;
		if(esPagoDirecto||true)
		{
			if(record.get('parametros.pv_otvalor12')&&record.get('parametros.pv_otvalor12').length>0)
			{
				conCoberYSubcober = true;
			}
		}
		debug('conCoberYSubcober:',conCoberYSubcober ? 'si' : 'no');
		
		var urlDestino;
		if(esPagoDirecto||true)
		{
			if(conCoberYSubcober)
			{
				if(esPagoDirecto)
				{
					urlDestino = _UrlDetalleSiniestroDirecto;
				}
				else
				{
					urlDestino = _UrlDetalleSiniestro;
				}
				params['params.ntramite'] = record.get('ntramite');
			}
			else
			{
				urlDestino = _urlSeleccionCobertura;
				params['params.ntramite']  = record.get('ntramite');
				params['params.cdunieco']  = record.get('cdsucdoc');
				params['params.cdramo']    = record.get('cdramo');
				params['params.cdtipsit']  = record.get('cdtipsit');
				params['params.otvalor02'] = record.get('parametros.pv_otvalor02');
			}
		}
		else
		{
			params['params.ntramite'] = record.get('ntramite');
		}
		debug('urlDestino:',urlDestino);
		
		debug('params:',params);
		
		Ext.create('Ext.form.Panel').submit(
		{
			url             : urlDestino
			,params         : params
		    ,standardSubmit : true
		    //,target         : '_parent'
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
        });
		
		windowLoader = Ext.create('Ext.window.Window',{
	        modal       : true,
	        buttonAlign : 'center',
	        width       : 663,
	        height      : 400,
	        autoScroll  : true,
	        items       : [
        	        		Ext.create('Ext.form.Panel', {
        	                title: 'Turnar al Area Médica',
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
        	            		        	},
        	            		        	failure: function(form, action) {
        	            		        		mensajeError('No se pudo turnar.');
        	            					},
        	            					success: function(form, action) {
        	            						mensajeCorrecto('Aviso','Se ha turnado con exito.');
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
		
	}
	
	function turnarAoperadorReclamaciones(grid,rowIndex,colIndex){
		var record = grid.getStore().getAt(rowIndex);
		var comentariosText = Ext.create('Ext.form.field.TextArea', {
        	fieldLabel: 'Observaciones'
    		,labelWidth: 150
    		,width: 600
    		,name:'smap1.comments'
			,height: 250
        });
		
		windowLoader = Ext.create('Ext.window.Window',{
	        modal       : true,
	        buttonAlign : 'center',
	        width       : 663,
	        height      : 400,
	        autoScroll  : true,
	        items       : [
        	        		Ext.create('Ext.form.Panel', {
        	                title: 'Turnar a Operador de Reclamaciones',
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
        	            		        		'smap1.status'   : _STATUS_TRAMITE_EN_CAPTURA
        	            		        	},
        	            		        	failure: function(form, action) {
        	            		        		mensajeError('No se pudo turnar.');
        	            					},
        	            					success: function(form, action) {
        	            						mensajeCorrecto('Aviso','Se ha turnado con exito.');
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
	}
	
	function solicitarPago(grid,rowIndex,colIndex){
		msgWindow = Ext.Msg.show({
	        title: 'Aviso',
	        msg: '&iquest;Esta seguro que desea solicitar el pago?',
	        buttons: Ext.Msg.YESNO,
	        icon: Ext.Msg.QUESTION,
	        fn: function(buttonId, text, opt){
	        	if(buttonId == 'yes'){
	        		
	        		Ext.Ajax.request({
						url: _UrlSolicitarPago,
						jsonData: {
							/*params: {
					    		'pv_ntramite_i' : _nmTramite,
					    		'pv_cdtippag_i' : _tipoPago,
					    		'pv_cdtipate_i' : _tipoAtencion
					    	}*/
						},
						success: function() {
							mensajeCorrecto('Aviso','El pago se ha solicitado con exito.');
						},
						failure: function(){
							mensajeError('No se pudo solicitar el pago.');
						}
					});
	        	}
	        	
	        }
	    });
		centrarVentana(msgWindow);
	}
	
<s:if test="false">
</script>
</s:if>