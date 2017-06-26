<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script>
//////variables //////
var store;
var win;
var mesConUrlDocu                   = '<s:url namespace="/documentos"  action="ventanaDocumentosPoliza"     />';
var mesConUrlComGrupo               = '<s:url namespace="/emision"     action="cotizacionGrupo"             />';
var mesConUrlComGrupo2              = '<s:url namespace="/emision"     action="cotizacionGrupo2"            />';
var _obtieneDetalleTramiteClona     = '<s:url namespace="/endosos"     action="obtieneDetalleTramiteClonar" />';

var _p100_urlComplementoClonacion   = '<s:url namespace="/endosos"     action="obtieneDetalleTramiteClonar" />';

var _URL_CARGA_CATALOGO      = '<s:url namespace="/catalogos" action="obtieneCatalogo" />';
var _p_smap1 = <s:property value='%{convertToJSON("smap1")}' escapeHtml="false" />;

var _CONTEXT = '${ctx}';
////// variables //////
	
////// componentes dinamicos //////
var itemsFormulario = [<s:property value="imap1.itemsFormulario" escapeHtml="false" />];
var itemsGrid = [<s:property value="imap1.itemsGrid" escapeHtml="false" />];
var itemsGridModel = [<s:property value="imap1.itemsGridModel" escapeHtml="false" />];

////// componentes dinamicos //////

Ext.onReady(function()
{
 Ext.Ajax.timeout = 1000*60*60; // 1 HORA
 Ext.override(Ext.form.Basic, { timeout: Ext.Ajax.timeout / 1000 });
 Ext.override(Ext.data.proxy.Server, { timeout: Ext.Ajax.timeout });
 Ext.override(Ext.data.Connection, { timeout: Ext.Ajax.timeout });
    ////// modelos //////
     Ext.define('ModeloConvenio',
    {
        extend  : 'Ext.data.Model'
        ,fields : itemsGridModel
      }); 
    ////// modelos //////
    
    ////// stores //////
    store = Ext.create('Ext.data.Store',
    {
        model : 'ModeloConvenio'
    }); 
    
    var modeloTipoTramAseg = Ext.create('Ext.data.Store', {
        fields: ['key', 'value'],
        data : [
            {"key":"1", "value":"De 10 a 49 Asegurados"},
            {"key":"2", "value":"50 o más asegurados"}
        ]
    });
    
    ////// stores //////
    
    ////// contenido //////
    Ext.create('Ext.panel.Panel',
    {
        title     : 'Panel principal'
        ,panelPri : 'S'
        ,renderTo : '_p100_divpri'
        ,id		  : '_p100_formulario'
        ,defaults :
        {
            style : 'margin:5px;'
        }
        ,items    :
        [
		    Ext.create('Ext.form.Panel',
		    {
		        title     : 'Clonaci&oacute;n de tr&aacute;mites Salud Colectivo'
		        ,layout   :
		        {
		            type     : 'table'
		            ,columns : 3
		        }
		        ,defaults :
		        {
		            style : 'margin:5px;'
		        }
		        ,items : itemsFormulario
		        ,buttonAlign : 'center'
		        ,buttons :
		        [
		        	
		            {
                        text     : 'BUSCAR'
                        ,icon    : '${icons}find.png'
                        ,handler : function(me)
                        {
                            var values = me.up('form').getValues();
                            _mask('Buscando...');
                            Ext.Ajax.request(
                            {
                                url     : '<s:url namespace="/endosos" action="buscarTramites" />'
                                ,params :
                                {
                                    'smap1.pv_cdunieco_i'    : values.cdunieco
                                    ,'smap1.pv_cdramo_i'     : values.cdramo
                                    ,'smap1.pv_cdtipsit_i'   : values.cdtipsit
                                    ,'smap1.pv_estado_i' 	 : values.estado
                                    ,'smap1.pv_ntramite_i'   : values.nmtramite
                                    ,'smap1.pv_status_i' 	 : values.status
                                    ,'smap1.pv_fedesde_i' 	 : values.fecini
                                    ,'smap1.pv_fehasta_i' 	 : values.fecfin
                                    ,'smap1.pv_cdagente_i' 	 : values.cdagente
                                    ,'smap1.pv_contratante_i': values.contratante
                                    ,'smap1.pv_nmpoliza_i'   : values.nmpoliza
                                    
                                }
                                ,success : function(response)
                                {
                                    _unmask();
                                    //var json = Ext.decode(response.responseText);
                                    json = Ext.decode(response.responseText);
                                    if(json.success==true)
                                    {
                                    	store.removeAll();
                                        store.add(json.slist1);
                                        //debug('json',json);
                                    }
                                    else
                                    {
                                        mensajeError(json.message);
                                    }
                                }
                                ,failure : function()
                                {
                                    _unmask();
                                    errorComunicacion(null,'error de red al guardar');
                                }
                            });
                        }
                    },
 		            {
		                text     : 'LIMPIAR'
		                ,icon    : '${icons}arrow_refresh.png'
		                ,handler : function()
		                {
		                	this.up('form').getForm().reset();
		                }
		            } 
		        ]
		    })
 		    ,Ext.create('Ext.grid.Panel',
		    {
		        title    : 'Tr&aacute;mites. De dobe clic en alg&uacute;n tr&aacute;mite para clonar.'
		        //,width   : 900
		        ,height  : 200
		        ,store   : store
		        ,columns : itemsGrid
		        ,listeners: {
		        	celldblclick:  function ( tableVw, td, cellIndex, record, tr, rowIndex, e, eOpts ){
                   		ventanaClonarTramite(record,tableVw);
		        	}
		        }
		    }) 
        ],
        listeners: {
        	afterrender: function(panelp){
        		if(RolSistema.Agente == _p_smap1.cdsisrol){
        			panelp.down('[name=cdagente]').hide();	
        		}
        	}
        }
    });
    ////// contenido //////
    
    function ventanaClonarTramite(recordTramite, gridTramites){
		
    	var maskGuarda1 = _maskLocal('Cargando...');
    	var datosTram = recordTramite.getData();
        
    	var parametrosClonacion = {
          		 'params.ntramite' : recordTramite.get('ntramite'),
          		 'params.cdunieco' : recordTramite.get('cdunieco'),
          		 'params.cdramo'   : recordTramite.get('cdramo'),
          		 'params.estado'   : recordTramite.get('estado'),
          		 'params.nmpoliza' : recordTramite.get('nmpoliza')
           };
    	
        Ext.Ajax.request({
            url: _obtieneDetalleTramiteClona,
            params: parametrosClonacion,
            success  : function(response, options){
           	 maskGuarda1.close();
                var json = Ext.decode(response.responseText);
               
                if(json.success){
                	var _carga = json.smap1; 
                	var windowClonaTramites;

                	var panelClonarTramite = Ext.create('Ext.form.FieldSet',{
                		title: '<span style="font:bold 12px Calibri;">Datos generales del tr&aacute;mite.</span>',
                        defaults : {
                			style : 'margin : 6px;',
                			labelWidth: 70, 
                			width: 250
                		},
                        layout: {
                            type: 'column',
                            columns: 3 
                        },
                        items: [
                            {
                            	xtype      : 'textfield',
                            	name       : 'params.dsunieco',
                            	fieldLabel : 'Sucursal',
                            	value      : _carga.CDUNIECO +' - '+_carga.DSUNIECO,
                            	readOnly   : true
                            },
                            {
                            	xtype      : 'textfield',
                            	name       : 'params.nmtramite',
                            	fieldLabel : 'Tr&aacute;mite',
                            	value      : _carga.NTRAMITE,
                            	readOnly   : true
                            },
                            {
                            	xtype      : 'textfield',
                            	name       : 'params.contratante',
                            	fieldLabel : 'Contratante',
                            	value      : _carga.CONTRATANTE,
                            	readOnly   : true
                            },
                            {
                            	xtype      : 'textfield',
                            	name       : 'params.cdramo',
                            	fieldLabel : 'Ramo',
                            	value      : _carga.CDRAMO +' - '+_carga.DSRAMO,
                            	readOnly   : true
                            },
                            {
                            	xtype      : 'textfield',
                            	name       : 'params.plan',
                            	fieldLabel : 'Plan',
                            	value      : Ext.isEmpty(_carga.PLAN)? 'N/A. Multiples planes.': _carga.PLAN,
                            	readOnly   : true
                            },
                            {
                            	xtype      : 'textfield',
                            	name       : 'params.vigencia',
                            	fieldLabel : 'Vigencia',
                            	value      : _carga.VIGENCIA,
                            	readOnly   : true
                            },
                            {
                            	xtype      : 'textfield',
                            	name       : 'params.dsstatus',
                            	fieldLabel : 'Estatus',
                            	value      : _carga.DSESTATUS,
                            	readOnly   : true
                            },
                            {
                            	xtype      : 'textfield',
                            	name       : 'params.agente',
                            	fieldLabel : 'Agente',
                            	value      : _carga.AGENTE,
                            	readOnly   : true
                            },
                            {
                            	xtype      : 'textfield',
                            	name       : 'params.tiptra',
                            	fieldLabel : 'Tipo Tr&aacute;mite',
                            	value      : _carga.DSTIPTRA,
                            	readOnly   : true
                            },{
                            	xtype      : 'textfield',
                            	name       : 'params.cdtamtra',
                            	fieldLabel : 'Tama&ntilde;o de Tr&aacute;mite',
                            	value      : "2" == _carga.CDTAMTRA? '50 o más asegurados' : 'De 10 a 49 Asegurados',
                            	readOnly   : true
                            },{
                                xtype: 'button',
                            	text: 'Exportar Censo',
                            	width: 120,
                                icon:_CONTEXT+'/resources/fam3icons/icons/page_white_put.png',
                                handler: function(btn){
                                	
                                	
                                	if(_carga.PUEDE_EXPORTAR == 'S'){
                                    	var parametros = {
                                    			cdunieco: datosTram.cdunieco,
                                    			cdramo: datosTram.cdramo,
                                    			estado: datosTram.estado,
                                    			nmpoliza: datosTram.nmpoliza,
                                    			ntramite: datosTram.ntramite
                                    			
                                    	}
                                    	_exportarExcelCensoClonacion(parametros);
                                	}else{
                                		mensajeWarning('No hay censo a exportar para este tr&aacute;mite');
                                	}
                                	
                                }
                            }
                        ]
                    });
                	
                	var panelClonarTramite2 = Ext.create('Ext.form.Panel',{
                		title: 'Elija una Sucursal distinta y No. de Asegurados s&oacute;lo si requiere cambiarlos para el nuevo tr&aacute;mite:',
                        defaults : {
                			style : 'margin : 20px;'
                		},
                        layout: {
                            type: 'column',
                            columns: 3
                        },
                        items: [
                            {
                                xtype: 'combo',
                                name : 'params.nuevaSucursal',
                                fieldLabel : 'Sucursal Nueva',
                    			labelWidth: 60, 
                    			width: 220,
                                allowBlank: false,
                                typeAhead:true,
								anyMatch:true,
								displayField:'value',
								valueField:'key',
								forceSelection:true,
								editable:true,
								queryMode:'local',
								store:Ext.create('Ext.data.Store', {
									model:'Generic',
									autoLoad:true,
									proxy: {
                                    type:'ajax',
										url: _URL_CARGA_CATALOGO,
										reader:{
                                            type:'json',
                                            root:'lista',
                                            rootProperty:'lista'
                                        },
                                        extraParams:{
										    catalogo:'MC_SUCURSALES_SALUD'
                                        }
                                    },
                                    listeners: {
                                    	load: function(strSuc, records, successful){
                                    			if(successful){
                                    				panelClonarTramite2.down('[name=params.nuevaSucursal]').setValue(recordTramite.get('cdunieco'));
                                    			}else{
                                    				mensajeError('Error al cargar las sucursales, intente nuevamente.');
                                    			}
                                    		
                                    	}
                                    }
								})
                            },{
                                xtype: 'combo',
                                name : 'params.nuevaCantAseg',
                                fieldLabel : 'N&uacute;mero de Asegurados',
                    			labelWidth: 90, 
                    			width: 245,
                                allowBlank: false,
                                typeAhead:true,
								anyMatch:true,
								displayField:'value',
								valueField:'key',
								forceSelection:true,
								editable:true,
								queryMode:'local',
								value: _carga.CDTAMTRA,
								store: modeloTipoTramAseg
                            },
                            {
                                xtype: 'combo',
                                name : 'params.nvoTipoTramite',
                                fieldLabel : 'Tipo de tr&aacute;mite a generar',
                    			labelWidth: 90, 
                    			width: 215,
                    			hidden     : (_carga.CDTIPTRA == '1') ? true:false,
                                allowBlank : (_carga.CDTIPTRA == '1') ? true:false,
                                typeAhead:true,
								anyMatch:true,
								displayField:'value',
								valueField:'key',
								forceSelection:true,
								editable:true,
								queryMode:'local',
								store:Ext.create('Ext.data.Store', {
									model:'Generic',
									autoLoad:true,
									proxy: {
                                    type:'ajax',
										url: _URL_CARGA_CATALOGO,
										reader:{
                                            type:'json',
                                            root:'lista',
                                            rootProperty:'lista'
                                        },
                                        extraParams:{
										    catalogo:'MC_ESTATUS_TRAMITE_EMI_RENOV'
                                        }
                                    }
								})
                            }
                        ],
                        buttonAlign: 'center',
                        buttons: [
                            {
                                text: 'Clonar Tr&aacute;mite',
                                icon:_CONTEXT+'/resources/fam3icons/icons/page_copy.png',
                                handler: function(btn){
                                	var panelGuardar = panelClonarTramite2;
                                	
                                	if(panelGuardar.getForm().isValid()){   
                    		        	_mask('Procesando...');
                  		        	  	var values = recordTramite.getData();	
                    		        	  debug("rec",values);
                    		        	  
                    		        	  var nuevaSucur     = panelGuardar.down('[name=params.nuevaSucursal]').getValue();
                    		        	  var nuevoTipoTra   = Ext.isEmpty(panelGuardar.down('[name=params.nvoTipoTramite]').getValue())? 
                    		        			  				_carga.CDTIPTRA : panelGuardar.down('[name=params.nvoTipoTramite]').getValue();
                    		        	  var tamanioTramite = panelGuardar.down('[name=params.nuevaCantAseg]').getValue();
                    		        	  
                  		            	   Ext.Ajax.request({
                                              url     : '<s:url namespace="/endosos" action="generarCopiaCompleta" />'
                                              ,params :
                                              {
                                                  'params.cdunieco'    : values.cdunieco
                                                  ,'params.cdramo'     : values.cdramo
                                                  ,'params.cdtipsit'   : values.cdtipsit
                                                  ,'params.estado' 	   : values.estado
                                                  ,'params.nmpoliza'   : values.nmpoliza
                                                  ,'params.nmsolici'   : values.nmsolici
                                                  ,'params.ntramite'   : values.ntramite
                                                  ,'params.status' 	   : values.status
                                                  ,'params.ferecepc'   : Ext.Date.format(values.ferecepc,'d/m/Y')
                                                  ,'params.fecstatus'  : Ext.Date.format(values.fecstatus,'d/m/Y')
                                                  ,'params.TIPOFLOT'   : values.tipoflot
                                                  ,'params.nuevaSucursal': nuevaSucur
                                                  ,'params.tipoTramite'  : _carga.CDTIPTRA
                                                  ,'params.nuevoTipoTramite' : nuevoTipoTra
                                                  ,'params.tamanioTramite'   : tamanioTramite
                                              }
                                              ,success : function(response)
                                              {
                                                  _unmask();
                                                  var json = Ext.decode(response.responseText);
                                                  debug("json",json);
                                                  
                                                  if(json.success==true){
                                                	  
                                                	  windowClonaTramites.close();
                                                	  
                                                      mensajeCorrecto('Clonaci&oacute;n exitosa.',json.mensaje,function(){                                    		
                                                      Ext.create('Ext.window.Window',
                                                      	    {
                                                      	        title        : 'Documentaci&oacute;n'
                                                      	        ,modal       : true
                                                      	        ,buttonAlign : 'center'
                                                      	        ,width       : 600
                                                      	        ,height      : 400
                                                      	        ,autoScroll  : true
                                                      	        ,cls         : 'VENTANA_DOCUMENTOS_CLASS'
                                                      	        ,loader      :
                                                      	        {
                                                      	            url       : mesConUrlDocu
                                                      	            ,params   :
                                                      	            {
                                                      	                'smap1.nmpoliza'  : '0'
                                                      	                ,'smap1.cdunieco' : nuevaSucur
                                                      	                ,'smap1.cdramo'   : json.params.cdramo
                                                      	                ,'smap1.estado'   : json.params.estado
                                                      	                ,'smap1.nmsuplem' : '0'
                                                      	                ,'smap1.ntramite' : json.params.ntramite
                                                      	                ,'smap1.nmsolici' : '0'
                                                      	                ,'smap1.tipomov'  : '0'
                                                      	            }
                                                      	            ,scripts  : true
                                                      	            ,autoLoad : true
                                                      	        }
                                                      	        ,buttons : [
                                                      	            {
                                                      	            	text     : 'Aceptar y continuar'
                                                      	            	,icon    : '${icons}accept.png'
                                                      	            	,handler : function(){
                                                      	            		debug('json.params', json.params);
                                                      	            		
                                                      	            		//json.params.redireccion = 'N'; // para deshabilitar opcion de redirecion
                                                      	            		
                                                      	            		if(json.params.redireccion == 'S'){
                                                      	            			//alert('Redirigiendo a pantalla para ramo '+json.params.cdramo);
                  							                                	if(values.cdtipsit == 'MSC')
                  							                                	{
                  							                                        Ext.create('Ext.form.Panel').submit(
                  							                                        {
                  							                                            url             : mesConUrlComGrupo
                  							                                            ,standardSubmit : true
                  							                                            ,params         :
                  							                                            {
                  							                                                'smap1.cdunieco'  : nuevaSucur
                  							                                                ,'smap1.cdramo'   : json.params.cdramo
                  							                                                ,'smap1.cdtipsit' : values.cdtipsit
                  							                                                ,'smap1.estado'   : 'W'
                  							                                                ,'smap1.nmpoliza' : json.params.nmpoliza
                  							                                                ,'smap1.ntramite' : json.params.ntramite
                  							                                                ,'smap1.cdagente' : values.cdagente
                  							                                                ,'smap1.status'   : json.params.statusNuevo
                  							                                                ,'smap1.sincenso' : 'N'
                  							                                            }
                  							                                        });
                  							                                    }
                  							                                    else
                  							                                    {
                  							                                    	debug('entro al if de values cdtipsit');
                  							                                        Ext.create('Ext.form.Panel').submit(
                  							                                        {
                  							                                            url             : mesConUrlComGrupo2
                  							                                			,standardSubmit : true
                  							                                			,params         :
                  							                                            {
                  							                                				'smap1.cdunieco'  : nuevaSucur
                  							                                				,'smap1.cdramo'   : json.params.cdramo
                  							                                				,'smap1.cdtipsit' : values.cdtipsit
                  							                                				,'smap1.estado'   : 'W'
                  							                                				,'smap1.nmpoliza' : json.params.nmpoliza
                  							                                				,'smap1.ntramite' : json.params.ntramite
                  							                                				,'smap1.cdagente' : values.cdagente
                  							                                				,'smap1.status'   : json.params.statusNuevo
                  							                                				,'smap1.sincenso' : 'N'
                  							                                             }
                  							                                        });
                  							                                   }
                                                      	            		}else{
                                                      	            			this.up('window').close();
                                                      	            		}
                                                      	            	}
                                                      	            }
                                                      	        ]
                                                      	    }).show();
                  									});
                                                  }
                                                  else
                                                  {
                                                      mensajeError('Error al clonar. ' +json.message);
                                                  }
                                              }
                                              ,failure : function()
                                              {
                                                  _unmask();
                                                  errorComunicacion(null,'Error de red al clonar');
                                              }
                                          });
                                      }else{
                                		mensajeWarning('Capture todos los datos requeridos. <br/>'+
                                						'Para un tr&aacute;mite de origen Renovaci&oacute;n se require especificar si el tr&aacute;mite nuevo <br/>'+
                                						'ser&aacute; tratado como P&oacute;liza Nueva &oacute; como P&oacute;liza de Renovaci&oacute;n.');
                                	}
                           			
                                }
                            }
                        ]
                    });
                	
                	windowClonaTramites = Ext.create('Ext.window.Window',{
                		title   : 'Clonar Tr&aacute;mite.'
                		,bodyPadding: '10 8 0 8'
                		,modal  : true
                		,width  : 838
                		,items : [panelClonarTramite,panelClonarTramite2]
                        ,dockedItems: [
                            {
                                xtype: 'toolbar',
                                dock: 'bottom',
                                items: ['->',{
	                    				text:'Cancelar',
	                    				icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
	                    				handler:function()
                    				{
                    					windowClonaTramites.close();
                    				}
                    			}]
                            }
                        ]
                	}).show();
                	
                	centrarVentanaInterna(windowClonaTramites);
                	
                }else{
                    mensajeError('Error al cargar datos de tr&aacute;mite.' + json.error);
                }
            }
            ,failure  : function(response, options){
           	 	maskGuarda1.close();
                var json = Ext.decode(response.responseText);
                mensajeError(json.error);
            }
        });
    	
    }
 
});
//////funciones //////

function _exportarExcelCensoClonacion(_parametros){
    Ext.create('Ext.form.Panel').submit({
        standardSubmit : true,
        url: '<s:url namespace="/consultasPoliza" action="consultaIncisosPoliza" />',
        params: {
        	cdreporte : 'REPEXC021'
            ,'params.cdunieco'   : _parametros.cdunieco
            ,'params.cdramo'     : _parametros.cdramo
            ,'params.estado'     : _parametros.estado
            ,'params.nmpoliza'   : _parametros.nmpoliza
            ,'params.ntramite'   : _parametros.ntramite
            ,'params.exportar'   : true
        },
        success: function(form, action) {
            
        },
        failure: function(form, action){
            switch (action.failureType){
                case Ext.form.action.Action.CONNECT_FAILURE:
                    Ext.Msg.alert('Error', 'Error de comunicaci&oacute;n');
                    break;
                case Ext.form.action.Action.SERVER_INVALID:
                case Ext.form.action.Action.LOAD_FAILURE:
                    Ext.Msg.alert('Error', 'Error del servidor, consulte a soporte');
                    break;
           }
        }
    });
}
////// funciones //////
</script>
</head>
<body>
<div id="_p100_divpri" style="height:600px;border:1px solid #CCCCCC"></div>
</body>
</html>