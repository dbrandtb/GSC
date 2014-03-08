<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<s:if test="false">
<script>
</s:if>
debug('#################################################');
debug('###### scriptMesaAutorizacionServicios.jsp ######');
debug('#################################################');

/* ******************** CATALOGOS ******************** */


//Catalogo Tipos de tramite a utilizar:
var _TIPO_TRAMITE_AUTORIZACION_SERVICIOS = '<s:property value="@mx.com.gseguros.portal.general.util.TipoTramite@AUTORIZACION_SERVICIOS.codigo" />';
/* *************************************************** */
///////////////////////
////// variables //////
var _4_urlAutorizarEndoso = '<s:url namespace="/endosos" action="autorizarEndoso" />';
var _4_authEndUrlDoc      = '<s:url namespace="/documentos" action="ventanaDocumentosPolizaClon" />';

var _4_urlPantallaAutServ = '<s:url namespace="/siniestros" action="autorizacionServicios" />';

var _UrlGenerarAutoServicio     = '<s:url namespace="/siniestros" action="generarAutoriServicio"       />';
var _UrlAutorizacionServicio    = '<s:url namespace="/siniestros" action="autorizacionServicios" />';

var _UrlValidaAutoProceso    = '<s:url namespace="/siniestros" action="validaAutorizacionProceso" />';
var panDocUrlViewDoc     = '<s:url namespace ="/documentos" action="descargaDocInline" />';
var _URL_ActualizaStatusTramite =      '<s:url namespace="/mesacontrol" action="actualizarStatusTramite" />';

var _4_selectedRecordEndoso;
var _4_windowAutorizarEndoso;
var _4_fieldComentAuthEndoso;

_4_botonesGrid.push(
{
    text     : 'Alta de tr&aacute;mite'
    ,icon    : '${ctx}/resources/fam3icons/icons/add.png'
    ,handler : function altaTramiteWindow(){
    	Ext.create("Ext.form.Panel").submit({url     : _4_urlPantallaAutServ,standardSubmit:true});
        /*windowLoader = Ext.create('Ext.window.Window',{
            modal       : true,
            buttonAlign : 'center',
            width       : 800,
            height      : 730,
            autoScroll  : true,
            loader      : {
                url     : _4_urlPantallaAutServ,
                scripts  : true,
                loadMask : true,
                autoLoad : true,
                ajaxOptions: {
                    method: 'POST'
                }
            }
        }).show();
        centrarVentana(windowLoader);*/
    }
});
////// variables //////
///////////////////////

///////////////////////
////// funciones //////
function _4_authEndosoDocumentos(grid,rowIndex,colIndex)
{
	var store=grid.getStore();
    var record=store.getAt(rowIndex);
    debug('record seleccionado',record);
    
    Ext.create('Ext.window.Window',
    {
        title        : 'Documentos del tr&aacute;mite '+record.get('ntramite')
        ,modal       : true
        ,buttonAlign : 'center'
        ,width       : 600
        ,height      : 400
        ,autoScroll  : true
        ,loader      :
        {
            url       : _4_authEndUrlDoc
            ,params   :
            {
                'smap1.nmpoliza'  : record.get('nmpoliza')
                ,'smap1.cdunieco' : record.get('cdunieco')
                ,'smap1.cdramo'   : record.get('cdramo')
                ,'smap1.estado'   : record.get('estado')
                ,'smap1.nmsuplem' : record.get('nmsuplem')
                ,'smap1.ntramite' : record.get('ntramite')
                ,'smap1.nmsolici' : ''
                ,'smap1.tipomov'  : 14
                ,'smap1.cdtiptra' : _TIPO_TRAMITE_AUTORIZACION_SERVICIOS
                //,'smap1.readOnly' : 'si'
            }
            ,scripts  : true
            ,autoLoad : true
        }
    }).show();
}


function generaAutoriServicioWindow(grid,rowIndex,colIndex){
	
	var record = grid.getStore().getAt(rowIndex);
	
	msgWindow = Ext.Msg.show({
        title: 'Aviso',
        msg: '&iquest;Esta seguro que desea generar la Autorizaci&oacute;n de Servicio ?',
        buttons: Ext.Msg.YESNO,
        icon: Ext.Msg.QUESTION,
        fn: function(buttonId, text, opt){
        	if(buttonId == 'yes'){
        		Ext.Ajax.request({
					url: _UrlGenerarAutoServicio,
					params: {
							'paramsO.pv_ntramite_i' : record.get('ntramite'),
				    		'paramsO.pv_cdunieco_i' : record.get('cdunieco'),
				    		'paramsO.pv_cdramo_i'   : record.raw.cdramo,
				    		'paramsO.pv_estado_i'   : record.raw.estado,
				    		'paramsO.pv_nmpoliza_i' : record.get('nmpoliza'),
				    		'paramsO.pv_nmAutSer_i' : record.get('parametros.pv_otvalor01'),
				    		'paramsO.pv_cdperson_i' : record.get('parametros.pv_otvalor05'),
				    		'paramsO.pv_nmsuplem_i' : record.raw.nmsuplem,
				    	
					},
					success: function(response, opt) {
						var jsonRes=Ext.decode(response.responseText);

						if(jsonRes.success == true){
							var numRand=Math.floor((Math.random()*100000)+1);
				        	debug('numRand a: ',numRand);
				        	var windowVerDocu=Ext.create('Ext.window.Window',
				        	{
				        		title          : 'Autorizaci&oacute;n de Sevicio Siniestro'
				        		,width         : 700
				        		,height        : 500
				        		,collapsible   : true
				        		,titleCollapse : true
				        		,html          : '<iframe innerframe="'+numRand+'" frameborder="0" width="100" height="100"'
				        		                 +'src="'+panDocUrlViewDoc+'?idPoliza=' + record.get('ntramite') + '&filename=' + '<s:text name="siniestro.autorizacionServicio.nombre"/>' +'">'
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


function _4_onComplementariosClick(grid,rowIndex)
{
	var record = grid.getStore().getAt(rowIndex);
    Ext.create('Ext.form.Panel').submit(
    {
        url             : _UrlAutorizacionServicio
        ,params   :
        {
        	'params.nmAutSer': record.get('parametros.pv_otvalor01'),
        	'params.ntramite' : record.get('ntramite')
        }
        ,standardSubmit : true
    });
}

function _4_generarAutorizacion(grid,rowIndex)
{
	var record = grid.getStore().getAt(rowIndex);
	debug('_4_generarAutorizacion:',record.raw);
}
////// funciones //////
///////////////////////

// TURNAR AL COORDINADOR MULTIREGIONAL
function turnarCoordinaMedMultiregional(grid,rowIndex,colIndex){
    var record = grid.getStore().getAt(rowIndex);
	
	Ext.Ajax.request(
	{
	    url     : _UrlValidaAutoProceso
	    ,params:{
	         'params.nmAutSer': record.get('parametros.pv_otvalor01')
	    }
	    ,success : function (response)
	    {
	        if(Ext.decode(response.responseText).autorizarProceso =="N")
        	{
	        	 Ext.Msg.show({
	 	            title:'Error',
	 	            msg: 'No se puede turnar debido a que no esta completo la autorizaci&oacute;n',
	 	            buttons: Ext.Msg.OK,
	 	            icon: Ext.Msg.ERROR
	 	        });
        	}else{
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
                                    title: 'Turnar al coordinador m&eacute;dico multiregional',
                                    width: 650,
                                    url: _URL_ActualizaStatusTramite,
                                    bodyPadding: 5,
                                    items: [comentariosText],
                                    buttonAlign:'center',
                                    buttons: [{
                                        text: 'Turnar',
                                        icon    : '${ctx}/resources/fam3icons/icons/accept.png',
                                        buttonAlign : 'center',
                                        handler: function() {
                	            	    	if (this.up().up().form.isValid()) {
                	            	    		this.up().up().form.submit({
                	            		        	waitMsg:'Procesando...',
                	            		        	params: {
                	            		        		'smap1.ntramite' : record.get('ntramite'), 
                	            		        		'smap1.status'   : 2,
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
                                        icon    : '${ctx}/resources/fam3icons/icons/cancel.png',
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
}


//TURNAR AL COORDINADOR MULTIREGIONAL
function turnarGerenteMedMultiregional(grid,rowIndex,colIndex){
    var record = grid.getStore().getAt(rowIndex);
	
	Ext.Ajax.request(
	{
	    url     : _UrlValidaAutoProceso
	    ,params:{
	         'params.nmAutSer': record.get('parametros.pv_otvalor01')
	    }
	    ,success : function (response)
	    {
	       /* if(Ext.decode(response.responseText).autorizarProceso =="N")
        	{
	        	 Ext.Msg.show({
	 	            title:'Error',
	 	            msg: 'No se puede turnar debido a que no esta completo la autorizaci&oacute;n',
	 	            buttons: Ext.Msg.OK,
	 	            icon: Ext.Msg.ERROR
	 	        });
        	}else{*/
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
                                    title: 'Turnar al gerente m&eacute;dico multiregional',
                                    width: 650,
                                    url: _URL_ActualizaStatusTramite,
                                    bodyPadding: 5,
                                    items: [comentariosText],
                                    buttonAlign:'center',
                                    buttons: [{
                                        text: 'Turnar',
                                        icon    : '${ctx}/resources/fam3icons/icons/accept.png',
                                        buttonAlign : 'center',
                                        handler: function() {
                	            	    	if (this.up().up().form.isValid()) {
                	            	    		this.up().up().form.submit({
                	            		        	waitMsg:'Procesando...',
                	            		        	params: {
                	            		        		'smap1.ntramite' : record.get('ntramite'), 
                	            		        		'smap1.status'   : 11,
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
                                        icon    : '${ctx}/resources/fam3icons/icons/cancel.png',
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
        		
        	//}
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
}

// TURNAR AL GERENTE MEDICO MULTIREGIONAL
function turnarGenerenteMedMultiregional(grid,rowIndex,colIndex){
    var record = grid.getStore().getAt(rowIndex);
	
	Ext.Ajax.request(
	{
	    url     : _UrlValidaAutoProceso
	    ,params:{
	         'params.nmAutSer': record.get('parametros.pv_otvalor01')
	    }
	    ,success : function (response)
	    {
	        if(Ext.decode(response.responseText).autorizarProceso =="N")
        	{
	        	 Ext.Msg.show({
	 	            title:'Error',
	 	            msg: 'No se puede turnar debido a que no esta completo la autorizaci&oacute;n',
	 	            buttons: Ext.Msg.OK,
	 	            icon: Ext.Msg.ERROR
	 	        });
        	}else{
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
                                    title: 'Turnar al coordinador m&eacute;dico multiregional',
                                    width: 650,
                                    url: _URL_ActualizaStatusTramite,
                                    bodyPadding: 5,
                                    items: [comentariosText],
                                    buttonAlign:'center',
                                    buttons: [{
                                        text: 'Turnar',
                                        icon    : '${ctx}/resources/fam3icons/icons/accept.png',
                                        buttonAlign : 'center',
                                        handler: function() {
                	            	    	if (this.up().up().form.isValid()) {
                	            	    		this.up().up().form.submit({
                	            		        	waitMsg:'Procesando...',
                	            		        	params: {
                	            		        		'smap1.ntramite' : record.get('ntramite'), 
                	            		        		'smap1.status'   : 11,
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
                                        icon    : '${ctx}/resources/fam3icons/icons/cancel.png',
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
}
Ext.onReady(function()
{
	/////////////////////////
	////// componentes //////
	_4_fieldComentAuthEndoso=Ext.create('Ext.form.field.TextArea',
	{
		width   : 280
		,height : 160
	});
	
	Ext.define('_4_WindowAutorizarEndoso',
	{
		extend         : 'Ext.window.Window'
		,initComponent : function()
		{
			debug('_4_WindowAutorizarEndoso initComponent');
			Ext.apply(this,
			{
				title        : 'Observaciones'
				,items       : _4_fieldComentAuthEndoso
				,modal       : true
				,buttonAlign : 'center'
				,width       : 300
				,height      : 250
				,closeAction : 'hide'
				,buttons     :
				[
				    {
				    	text     : 'Autorizar'
				    	,icon    : '${ctx}/resources/fam3icons/icons/key.png'
				    	,handler : _4_autorizarEndoso
				    }
				]
			});
			this.callParent();
		}
	});
    ////// componentes //////
	/////////////////////////
	
	///////////////////////
	////// contenido //////
	_4_windowAutorizarEndoso=new _4_WindowAutorizarEndoso();
    ////// contenido //////
    ///////////////////////
});
<s:if test="false">
</script>
</s:if>