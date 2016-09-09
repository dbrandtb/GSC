<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<style type="text/css">
			.ingresados .x-panel-header {
				background-color: #0B0B61;
			}
			
			.ingresados .x-panel-body {
				font: normal 40px courier !important;
			}
			
			.procesados .x-panel-header {
				background-color : #088A08;
			}
			
			.procesados .x-panel-body {
				font: normal 40px courier !important;
			}
			
			.eficacia {
				font-size: 40px;
			}
			
			
			.pendientes .x-panel-header {
				background-color : #DF0101;
			}
			
			.pendientes .x-panel-body {
				font: normal 40px courier !important;
			}
			
			.my-btn .x-btn-inner {
			    color: #000;
			    font-size: 40px;
				background-color : #FFF;
			    //font: normal 40px courier !important;
			}
		</style>
		
		<script>
		
		////// urls //////
		////// urls //////
		
		////// variables //////
		////// variables //////
		
		////// overrides //////
		////// overrides //////
		
		////// componentes dinamicos //////
		////// componentes dinamicos //////
		
		Ext.onReady(function() {
			
			
		    ////// modelos //////
			Ext.define('AseguradosModel', {
		        extend: 'Ext.data.Model',
		        fields: [
		            {type:'string', name:'ingresados'},
		            {type:'string', name:'procesados'},
		            {type:'string', name:'pendientes'},
		            {type:'string', name:'eficacia'}
		        ]
		    });
		    
		    Ext.define('PendientesModel', {
		        extend: 'Ext.data.Model',
		        fields: [
		            {type:'string', name:'undia'},
		            {type:'string', name:'dosdias'},
		            {type:'string', name:'tresdias'},
		            {type:'string', name:'cuatrodias'},
		            {type:'string', name:'cincodias'},
		            {type:'string', name:'mascincodias'},
		            {type:'string', name:'total'}
		        ]
		    });
		    
		    Ext.define('TramitesPorLineaNegocioModel', {
		        extend: 'Ext.data.Model',
		        fields: [
		            {type:'string', name:'CD_LINEA_NEGOCIO'},
		            {type:'string', name:'DS_LINEA_NEGOCIO'},
		            {type:'string', name:'CANTIDAD'}
		        ]
		    });
		    
		    Ext.define('DetalleLineaNegocioPorRamoModel', {
		        extend: 'Ext.data.Model',
		        fields: [
		            {type:'string', name:'CDRAMO'},
		            {type:'string', name:'DSRAMO'},
		            {type:'string', name:'CANTIDAD'}
		        ]
		    });
		    
		    Ext.define('TramitesLineaNegocioPorSucursalModel', {
		        extend: 'Ext.data.Model',
		        fields: [
		            {type:'string', name:'CDUNIECO'},
		            {type:'string', name:'DSUNIECO'},
		            {type:'string', name:'SALUD'},
		            {type:'string', name:'AUTOS'}
		        ]
		    });
		    
		    Ext.define('TramitesLineaNegocioPorUsuarioModel', {
		        extend: 'Ext.data.Model',
		        fields: [
		            {type:'string', name:'CDUSUARI_ACT'},
		            {type:'string', name:'DSUSUARI_ACT'},
		            {type:'string', name:'SALUD'},
		            {type:'string', name:'AUTOS'}		            
		        ]
		    });
		    
		    Ext.define('TramitesPendientesPorDiasModel', {
		        extend: 'Ext.data.Model',
		        fields: [
		            {type:'string', name:'CDETAPA'},
		            {type:'string', name:'ETAPA'},
		            {type:'string', name:'NTRAMITE'},
		            {type:'string', name:'FECHA_RECEP_TRAMITE'},
		            {type:'string', name:'FECHA_DESDE'},
		            {type:'string', name:'CDUNIECO'},
		            {type:'string', name:'DSUNIECO'},
		            {type:'string', name:'CD_TIPO_TRAMITE'},
		            {type:'string', name:'TIPO_TRAMITE'},
		            {type:'string', name:'CD_LINEA_NEGOCIO'},
		            {type:'string', name:'DS_LINEA_NEGOCIO'},
		            {type:'string', name:'STATUS_TRAMITE'},
		            {type:'string', name:'DS_STATUS_TRAMITE'},
		            {type:'string', name:'CDRAMO'},
		            {type:'string', name:'DSRAMO'},
		            {type:'string', name:'CDPERSON'},
		            {type:'string', name:'CLIENTE'},
		            {type:'string', name:'CDAGENTE'},
		            {type:'string', name:'NOMBRE_AGENTE'},
		            {type:'string', name:'NMPOLIZA'},
		            {type:'string', name:'NMSOLICI'},
		            {type:'string', name:'CDUSUARI_CREA'},
		            {type:'string', name:'CDUSUARI_ACT'},
		            {type:'string', name:'DSUSUARI_ACT'}
		        ]
		    });
		    
		    Ext.define('TramitesPorTipoModel', {
		        extend: 'Ext.data.Model',
		        fields: [
		            {type:'string', name:'CD_TIPO_TRAMITE'},
		            {type:'string', name:'TIPO_TRAMITE'},
		            {type:'int', name:'CANTIDAD'}	            
		        ]
		    });
		    
		    Ext.define('DetalleLineaNegocioModel', {
		        extend: 'Ext.data.Model',
		        fields: [
		        	{type:'string', name:'NTRAMITE'},
		            {type:'string', name:'CDAGENTE'},
		            {type:'string', name:'CDETAPA'},
		            {type:'string', name:'CDRAMO'},
		            {type:'string', name:'CDUNIECO'},
		            {type:'string', name:'NMPOLIZA'},
		            {type:'string', name:'NMSOLICI'},
		            {type:'string', name:'CDUSUARI_ACT'},
		            {type:'string', name:'DSUSUARI_ACT'},
		            {type:'string', name:'CDUSUARI_CREA'},
		            {type:'string', name:'DSUSUARI_CREA'},
		            {type:'string', name:'CD_LINEA_NEGOCIO'},
		            {type:'string', name:'DS_LINEA_NEGOCIO'},
		            {type:'string', name:'CD_TIPO_TRAMITE'},
		            {type:'string', name:'CLIENTE'},
		            {type:'string', name:'DSRAMO'},
		            {type:'string', name:'TIPO_TRAMITE'},
		            {type:'string', name:'DSUNIECO'},
		            {type:'string', name:'STATUS_TRAMITE'},
		            {type:'string', name:'DS_STATUS_TRAMITE'},
		            {type:'string', name:'ETAPA'},
		            {type:'string', name:'FECHA_DESDE'},
		            {type:'string', name:'FECHA_RECEP_TRAMITE'},
		            {type:'string', name:'NOMBRE_AGENTE'}
		        ]
		    });
		    
		    ////// modelos //////
		    
		    ////// stores //////
			
			var storeTramitesPorLineaNegocio = new Ext.data.Store({
				model: 'TramitesPorLineaNegocioModel',
				proxy: {
				    type: 'ajax',
				 	url : _GLOBAL_URL_RECUPERACION,
					reader: {
					    type: 'json',
					    root: 'list'//,
					    //totalProperty: 'totalCount',
					    //simpleSortMode: true
				    }
				}
			});
			
			var storeDetalleLineaNegocioPorRamo = new Ext.data.Store({
				model: 'DetalleLineaNegocioPorRamoModel',
				proxy: {
				    type: 'ajax',
				 	url : _GLOBAL_URL_RECUPERACION,
					reader: {
					    type: 'json',
					    root: 'list'//,
					    //totalProperty: 'totalCount',
					    //simpleSortMode: true
				    }
				}
			});
			
			
			var storeTramitesLineaNegocioPorSucursal = new Ext.data.Store({
				model: 'TramitesLineaNegocioPorSucursalModel',
				proxy: {
				    type: 'ajax',
				 	url : _GLOBAL_URL_RECUPERACION,
					reader: {
					    type: 'json',
					    root: 'list'//,
					    //totalProperty: 'totalCount',
					    //simpleSortMode: true
				    }
				}
			});
			
			
			var storeTramitesLineaNegocioPorUsuario = new Ext.data.Store({
				model: 'TramitesLineaNegocioPorUsuarioModel',
				proxy: {
				    type: 'ajax',
				 	url : _GLOBAL_URL_RECUPERACION,
					reader: {
					    type: 'json',
					    root: 'list'//,
					    //totalProperty: 'totalCount',
					    //simpleSortMode: true
				    }
				}
			});
			
			var storeTramitesPendientesPorDias = new Ext.data.Store({
				model: 'TramitesPendientesPorDiasModel',
				proxy: {
				    type: 'ajax',
				 	url : _GLOBAL_URL_RECUPERACION,
					reader: {
					    type: 'json',
					    root: 'list'//,
					    //totalProperty: 'totalCount',
					    //simpleSortMode: true
				    }
				}
			});
			
			var storeTramitesPorTipo = new Ext.data.Store({
				model: 'TramitesPorTipoModel',
				proxy: {
				    type: 'ajax',
				 	url : _GLOBAL_URL_RECUPERACION,
					reader: {
					    type: 'json',
					    root: 'list'//,
					    //totalProperty: 'totalCount',
					    //simpleSortMode: true
				    }
				}
			});
			
			var storeDetalleLineaNegocio = new Ext.data.Store({
				model: 'DetalleLineaNegocioModel',
				proxy: {
				    type: 'ajax',
				 	url : _GLOBAL_URL_RECUPERACION,
					reader: {
					    type: 'json',
					    root: 'list'//,
					    //totalProperty: 'totalCount',
					    //simpleSortMode: true
				    }
				}
			});
			
		    ////// stores //////
		    
		    ////// componentes //////
		    ////// componentes //////
		    
		    ////// contenido //////
			Ext.create('Ext.panel.Panel', {
				renderTo : 'dvEstadisticas',
				itemId: 'pnlEstadisticas',
				//width: 1000,
    			//height: 800,//TODO: ver si se elimina
				items : [{
					xtype : 'form',
					title : 'INDICADORES',
					collapsible: true,
					layout: {
		                type     : 'table',
		                columns  : 3//4//TODO: regresar valor
		        	},
		        	defaults: {
		        		labelAlign: 'right',
		        		margin: '5px'
		        	},
					items: [
//						{xtype: 'textfield', name: 'year', fieldLabel: 'A&ntilde;o'},
//						{xtype: 'textfield', name: 'zona', fieldLabel: 'Zona'}, 
//						{xtype: 'textfield', name: 'params.cdramo', fieldLabel: 'Ramo'}, 
//						{xtype: 'textfield', name: 'tipoDocumento', fieldLabel: 'Tipo de documento'}, 
//						{xtype: 'textfield', name: 'mes', fieldLabel: 'Mes'}, 
//						{xtype: 'textfield', name: 'params.cdunieco', fieldLabel: 'Sucursal emisora'}, 
//						{xtype: 'textfield', name: 'params.lineanegocio', fieldLabel: 'Tipo Seguro'}, 
//						{xtype: 'textfield', name: 'params.tipotramite', fieldLabel: 'Tipo de tr&aacute;mite'}, 
//						{xtype: 'textfield', name: 'semana', fieldLabel: 'Semana'}, 
//						{xtype: 'textfield', name: 'sucursal', fieldLabel: 'Suc'}, 
//						{xtype: 'textfield', name: 'tipo', fieldLabel: 'Tipo'}, 
//						{xtype: 'textfield', name: 'tipoMedio', fieldLabel: 'Tipo medio'}, 
//						{xtype: 'datefield', name: 'fechaDesde', fieldLabel: 'Periodo de'}, 
//						{xtype: 'datefield', name: 'fechaHasta', fieldLabel: 'Periodo hasta'}, 
//						{xtype: 'textfield', name: 'usuarioAutoriza', fieldLabel: 'UsrAut'}, 
//						{xtype: 'textfield', name: 'ramoEmisor', fieldLabel: 'Ramo Emisor'}, 
//						{xtype: 'textfield', name: 'priemi', fieldLabel: 'Pri Emi'}, 
//						{xtype: 'textfield', name: 'usrcap', fieldLabel: 'UsrCap'}, 
//						{xtype: 'textfield', name: 'cdtipsup', fieldLabel: 'TipEndoso'}, 
//						{xtype: 'textfield', name: 'area', fieldLabel: '&Aacute;rea'}, 
//						{xtype: 'textfield', name: 'promotoria', fieldLabel: 'Promotor&iacute;a'}, 
//						{xtype: 'textfield', name: 'params.cdagente', fieldLabel: 'Agente'}, 
//						{xtype: 'textfield', name: 'zona', fieldLabel: 'Zona'}, 
						{xtype: 'textfield', name: 'params.cdramo', fieldLabel: 'Ramo', value:711}, 
						{xtype: 'textfield', name: 'params.cdunieco', fieldLabel: 'Sucursal emisora', value:0}, 
						{xtype: 'textfield', name: 'params.lineanegocio', fieldLabel: 'Tipo Seguro'}, 
						{xtype: 'textfield', name: 'params.tipotramite', fieldLabel: 'Tipo de tr&aacute;mite'}, 
						{xtype: 'textfield', name: 'params.cdagente', fieldLabel: 'Agente'},
						{xtype: 'hiddenfield',name: 'params.cdetapa'},
						{
							xtype: 'button',
							text : 'Buscar',
							handler: function(btn) {
								mostrarPanelPrincipal(null);
								//Ext.ComponentQuery.query('#grdDetalleLOBPorRamo')[0].hide();
								buscar();
							}
					}]
				},{
					xtype : 'panel',
					itemId: 'pnlIndicadores',
					height: 100,
					layout: {
                        type : 'hbox',
                        align: 'stretch'
                    },
					items: [{
						xtype : 'panel',
						flex  : 0.3,
						title : 'INGRESADOS',
						cls   : 'ingresados',
						layout: {
	                        type : 'hbox',
	                        align:'stretch'
	                    },
						items : [{
							xtype  : 'button',
							itemId : 'btnIngresados',
							width  : '100%',
							cls    : 'my-btn',
							tooltip: 'Ver detalle',
							handler: function() {
								mostrarPanelPrincipal('tbDetalle');
								cargarDetalleTramites(1);
							}
						}]
					}, {
						xtype : 'panel',
						flex  : 0.3,
						title : 'ATENDIDOS / PROCESADOS',
						cls   : 'procesados',
						layout: {
	                        type : 'hbox',
	                        align:'stretch'
	                    },
						items : [{
							xtype  : 'button',
							itemId : 'btnProcesados',
							width  : '100%',
							cls    : 'my-btn',
							tooltip: 'Ver detalle',
							handler: function() {
								mostrarPanelPrincipal('tbDetalle');
								cargarDetalleTramites(3);
							}
						}]
					}, {
						xtype : 'panel',
						flex  : 0.4,
						title : 'EN PROCESO / PENDIENTES',
						cls   : 'pendientes',
						layout: {
	                        type : 'hbox',
	                        align:'stretch'
	                    },
						items : [{
							xtype  : 'button',
							itemId : 'btnPendientes',
							width  : '100%',
							cls    : 'my-btn',
							tooltip: 'Ver detalle',
							handler: function() {
								mostrarPanelPrincipal('tbDetalle');
								cargarDetalleTramites(2);
							}
						}]
					}]
				},{
					xtype : 'panel',
					itemId: 'pnlIndicadores2',
					height: 100,
					layout: {
                        type : 'hbox',
                        align: 'stretch'
                    },
					items: [{
						xtype : 'panel',
						flex  : 0.3,
						layout: {
	                        type : 'hbox',
	                        align:'stretch'
	                    }
					}, {
						xtype : 'panel',
						itemId: 'pnlEficacia',
						flex  : 0.3,
						title: 'Eficacia',
						cls: 'procesados',
						layout: {
	                        type : 'vbox',
	                        align:'center'
	                    },
						items : [{
							xtype: 'displayfield',
							name:  'dspEficacia',
							fieldCls: 'eficacia'
						}]
					}, {
						xtype: 'grid', // grid is already a panel
						itemId: 'grdEnProceso',
						flex  : 0.4,
						selType: 'cellmodel',
						title: 'En proceso:',
						layout: 'fit',
						store: Ext.create('Ext.data.ArrayStore', {
							fields: [
								{name: 'undia'},
								{name: 'dosdias'},
								{name: 'tresdias'},
								{name: 'cuatrodias'},
								{name: 'cincodias'},
								{name: 'mascincodias'},
								{name: 'total'}
							]
						}),
						columns: [
							{text: '1h',    flex: 1, dataIndex: 'undia'},
							{text: '2h',    flex: 1, dataIndex: 'dosdias'},
							{text: '3h',    flex: 1, dataIndex: 'tresdias'},
							{text: '4h',    flex: 1, dataIndex: 'cuatrodias'},
							{text: '5h',    flex: 1, dataIndex: 'cincodias'},
							{text: '+5h',   flex: 1, dataIndex: 'mascincodias'},
                           	{text: 'Total', flex: 1, dataIndex: 'total'}
						],
						listeners: {
							cellclick: function ( grd, td, cellIndex, record, tr, rowIndex, e, eOpts ) {
								
								setTituloDetalleProcesoPorDia(cellIndex+1);
								
								mostrarPanelPrincipal('grdDetalleProcesoPorDia');
								
								var par = Ext.ComponentQuery.query('form')[0].getForm().getValues();
								par['params.consulta'] = 'RECUPERAR_TRAMITES_PENDIENTES_POR_DIAS';
								par['params.numdias'] = cellIndex+1;
								storeTramitesPendientesPorDias.load({
									params : par
								});
							}
						}
					}]
				}, {
					xtype : 'panel',
					itemId: 'pnlDetalles',
					//height: 510,
					items: [{
						xtype: 'tabpanel',
						itemId: 'tbDetalle',
						hidden: true,
						margins: '5 5 5 0',
						layout: 'fit',
						items: [{
							title : 'Por Linea de Negocio',
							layout: 'hbox',
							items : [{
								xtype: 'grid',
								itemId: 'grdTramitesPorLineaNegocio',
								title: 'Total de tramites ingresados por LOB',
								header: {
								    autoEl: {
								        'data-qtip': 'Click en M\u00E1s Detalles -->'
								    }
								},
								flex: 1,
								//layout: 'fit',
								//height: 350,
								store: storeTramitesPorLineaNegocio,
								columns: [
//									{
//                                        text     : 'Periodo',
//                                        flex     : 2,
//                                        dataIndex: 'periodo'
//                                    },
                                  {
                                    text     : 'LOB', 
                                    flex     : 2,
                                    dataIndex: 'CD_LINEA_NEGOCIO'
                                },{
                                    text     : 'L\u00EDnea de negocio', 
                                    flex     : 3,
                                    dataIndex: 'DS_LINEA_NEGOCIO'
                                },{
                                    text     : 'Cantidad', 
                                    flex     : 3,
                                    dataIndex: 'CANTIDAD'
                                },{
						            xtype    :'actioncolumn',
						            text     : 'M\u00E1s Detalles',
						            flex     : 2,
						            items: [{
						                tooltip : 'Ver Detalle por Ramo',
						                icon    : '${ctx}/resources/fam3icons/icons/color_swatch.png',
						                handler : function(grid, rowIndex, colIndex, item, e, record, row) {
						                						                	
						                	mostrarPanelSubdetalles('grdDetalleLOBPorRamo');
						                	
						                	var par = Ext.ComponentQuery.query('form')[0].getForm().getValues();
											par['params.consulta'] = 'RECUPERAR_TRAMITES_LINEANEGOCIO_POR_RAMO';
											par['params.lineanegocio'] = record.get('CD_LINEA_NEGOCIO');
											par['params.cdetapa'] = Ext.ComponentQuery.query('[name=params.cdetapa]')[0].getValue();
											storeDetalleLineaNegocioPorRamo.load({
												params : par
											});
						                }
						            },{
						                tooltip : 'Ver Detalle por tipo de tr\u00E1mite',
						                icon    : '${ctx}/resources/fam3icons/icons/report_edit.png',
						                handler: function(grid, rowIndex, colIndex, item, e, record, row) {
						                	
						                	mostrarPanelSubdetalles('grdTramitesPorTipo');
						                	
											var par = Ext.ComponentQuery.query('form')[0].getForm().getValues();
											par['params.consulta']     = 'RECUPERAR_TRAMITES_POR_TIPO';
											par['params.lineanegocio'] = record.get('CD_LINEA_NEGOCIO');
											par['params.cdetapa']      = Ext.ComponentQuery.query('[name=params.cdetapa]')[0].getValue();
											storeTramitesPorTipo.load({
												params : par
											});
						                	
						                }
						            },{
						                tooltip : 'Ver Detalle Completo',
						                icon    : '${ctx}/resources/fam3icons/icons/text_columns.png',
						                handler: function(grid, rowIndex, colIndex, item, e, record, row) {
						                	
						                	mostrarPanelSubdetalles('grdDetalleLineaNegocio');
						                	
						                	var par = Ext.ComponentQuery.query('form')[0].getForm().getValues();
											par['params.consulta']     = 'RECUPERAR_DETALLE_LINEA_NEGOCIO';
											par['params.lineanegocio'] = record.get('CD_LINEA_NEGOCIO');
											par['params.cdetapa']      = Ext.ComponentQuery.query('[name=params.cdetapa]')[0].getValue();
											storeDetalleLineaNegocio.load({
												params : par
											});
						                }
						            }]
    							}],
								listeners: {
									cellclick: function ( grd, td, cellIndex, record, tr, rowIndex, e, eOpts ) {
										
										//setTituloDetalleProcesoPorDia(cellIndex+1);
										//mostrarPanelPrincipal('grdDetalleProcesoPorDia');
										/*
										var par = Ext.ComponentQuery.query('form')[0].getForm().getValues();
										par['params.consulta'] = 'RECUPERAR_TRAMITES_LINEANEGOCIO_POR_RAMO';
										par['params.lineanegocio'] = record.get('CD_LINEA_NEGOCIO');
										par['params.cdetapa'] = Ext.ComponentQuery.query('[name=params.cdetapa]')[0].getValue();
										
										storeDetalleLineaNegocioPorRamo.load({
											params : par
										});
										
										Ext.ComponentQuery.query('#grdDetalleLOBPorRamo')[0].show();
										*/
									}
								}
							}/*,{
								xtype: 'grid',
								itemId: 'grdDetalleLOBPorRamo',
								hidden: true,
								flex: 0.5,
								title: '--> Detalle LOB por Ramo',
								//layout: 'fit',
								store: storeDetalleLineaNegocioPorRamo,
								columns: [
									{
                                        text     : 'C\u00F3digo', 
                                        flex     : 2,
                                        dataIndex: 'CDRAMO'
                                    },{
                                        text     : 'Ramo', 
                                        flex     : 5,
                                        dataIndex: 'DSRAMO'
                                    },{
                                        text     : 'Total', 
                                        flex     : 3,
                                        dataIndex: 'CANTIDAD'
                                    }
								]
							}*/]
						},{
							title: 'Por Sucursal',
							layout: 'fit',
							items : [{
								xtype: 'grid',
								itemId: 'grdTramitesPorSucursal',
								title: 'Total de tramites ingresados por Sucursal',
								//layout: 'fit',
								height: 350,
								store: storeTramitesLineaNegocioPorSucursal,
								columns: [
									{
                                        text     : 'C\u00F3digo Sucursal',
                                        flex     : 2,
                                        dataIndex: 'CDUNIECO'
                                    },{
                                        text     : 'Sucursal', 
                                        flex     : 5,
                                        dataIndex: 'DSUNIECO'
                                    },{
                                        text     : 'Total Salud', 
                                        flex     : 3,
                                        dataIndex: 'SALUD'
                                    },{
                                        text     : 'Total Autos', 
                                        flex     : 3,
                                        dataIndex: 'AUTOS'
                                    }
								]
							}]
						},{
							title: 'Por Usuario',
							layout: 'fit',
							items : [{
								xtype: 'grid',
								itemId: 'grdTramitesPorUsuario',
								title: 'Total de tramites ingresados por Usuario',
								//layout: 'fit',
								height: 350,
								store: storeTramitesLineaNegocioPorUsuario,
								columns: [
									{text: 'Clave',       flex: 2, dataIndex: 'CDUSUARI_ACT'},
                                    {text: 'Nombre',      flex: 5, dataIndex: 'DSUSUARI_ACT'},
                                    {text: 'Total Salud', flex: 3, dataIndex: 'SALUD'},
                                    {text: 'Total Autos', flex: 3, dataIndex: 'AUTOS'}
								]
							}]
						}]
					},{
						xtype: 'grid',
						itemId: 'grdDetalleProcesoPorDia',
						hidden: true,
						//layout:'fit',
						height: 380,
						store: storeTramitesPendientesPorDias,
						columns: [
							{text: 'Tramite',         dataIndex: 'NTRAMITE'},
							{text: 'Sucursal',        dataIndex: 'CDUNIECO'},
							{text: 'Ramo',            dataIndex: 'DSRAMO'},
							{text: 'Usuario inicial', dataIndex: 'CDUSUARI_CREA'},
							{text: 'Usuario actual',  dataIndex: 'CDUSUARI_ACT'},
							{text: 'Prospecto',       dataIndex: 'CLIENTE'},
							{text: 'Estatus tramite', dataIndex: 'DS_STATUS_TRAMITE'},
							{text: 'Etapa',           dataIndex: 'ETAPA'},
							{text: 'Fecha Inicio',    dataIndex: 'FECHA_DESDE'},
							{text: 'Fecha Recepcion', dataIndex: 'FECHA_RECEP_TRAMITE'},
							{text: 'Agente',          dataIndex: 'NOMBRE_AGENTE'},
							{text: 'Tipo Tramite',    dataIndex: 'TIPO_TRAMITE'}
						]
					}]
				}, {
					xtype : 'panel',
					itemId: 'pnlSubDetalles',
					layout: {
                        type : 'hbox',
                        align: 'stretch'
                    },
					//height: 510,
					items: [{
						xtype: 'grid',
						itemId: 'grdDetalleLOBPorRamo',
						hidden: true,
						flex: 1,
						title: '--> Detalle LOB por Ramo',
						//layout: 'fit',
						store: storeDetalleLineaNegocioPorRamo,
						columns: [
							{text: 'C\u00F3digo', flex: 2, dataIndex: 'CDRAMO'},
							{text: 'Ramo',        flex: 5, dataIndex: 'DSRAMO'},
							{text: 'Total',       flex: 3, dataIndex: 'CANTIDAD'}
						]
					},{
						xtype: 'grid',
						itemId: 'grdTramitesPorTipo',
						hidden: true,
						flex: 1,
						title: '--> Detalle por tipo de Tr\u00E1mite',
						//layout: 'fit',
						store: storeTramitesPorTipo,
						columns: [
							{text: 'C\u00F3digo', flex: 2, dataIndex: 'CD_TIPO_TRAMITE'},
							{text: 'Tipo',        flex: 5, dataIndex: 'TIPO_TRAMITE'},
                            {text: 'Total',       flex: 3, dataIndex: 'CANTIDAD'}
						]
					},{
						xtype: 'grid',
						itemId: 'grdDetalleLineaNegocio',
						hidden: true,
						flex: 1,
						title: '--> Detalle del tr\u00E1mite',
						//autoScroll: true,
						height: 300,
						//layout: 'fit',
						store: storeDetalleLineaNegocio,
						columns: [
							{text: 'Tr\u00E1mite',      dataIndex: 'NTRAMITE'},
							{text: 'CD Agente',         dataIndex: 'CDAGENTE'},
							{text: 'Agente',            dataIndex: 'NOMBRE_AGENTE'},
							//{text: 'C\u00F3digo', dataIndex: 'CDETAPA'},
							//{text: 'C\u00F3digo', dataIndex: 'CDRAMO'},
							{text: 'Ramo',              dataIndex: 'DSRAMO'},
							{text: 'CD Sucursal',       dataIndex: 'CDUNIECO'},
							{text: 'Sucursal',          dataIndex: 'DSUNIECO'},
							{text: 'Poliza',            dataIndex: 'NMPOLIZA'},
							{text: 'No. Solicitud',     dataIndex: 'NMSOLICI'},
							{text: 'CD Usuario inicia', dataIndex: 'CDUSUARI_ACT'},
							{text: 'Usuario inicia',    dataIndex: 'DSUSUARI_ACT'},
							{text: 'CD Usuario crea',   dataIndex: 'CDUSUARI_CREA'},
							{text: 'Usuario inicia',    dataIndex: 'DSUSUARI_CREA'},
							//{text: 'Cd ', dataIndex: 'CD_LINEA_NEGOCIO'},
							{text: 'L\u00EDnea de negocio',   dataIndex: 'DS_LINEA_NEGOCIO'},
							//{text: 'C\u00F3digo', dataIndex: 'CD_TIPO_TRAMITE'},
							{text: 'Tipo de tr\u00E1mite',    dataIndex: 'TIPO_TRAMITE'},
							//{text: 'Es', dataIndex: 'STATUS_TRAMITE'},
							{text: 'Estatus tr\u00E1mite',    dataIndex: 'DS_STATUS_TRAMITE'},
							{text: 'Etapa',             dataIndex: 'ETAPA'},
							{text: 'Fecha inicio',      dataIndex: 'FECHA_DESDE'},
							{text: 'Fecha de recepci\u00F3n', dataIndex: 'FECHA_RECEP_TRAMITE'}
						]
					}]
				}]
			});
			
		    ////// contenido //////
		    
		    ////// custom //////
		    ////// custom //////
		    
		    ////// loaders //////
		    ////// loaders //////
			
			/*
			var task = Ext.TaskManager.start({
			    run: buscar,
			    interval: 60000
			});
			*/
			
		});
		
		////// funciones //////
		function setTituloDetalleProcesoPorDia(dia) {
			
			var titulo = 'Total en proceso ';
			
			switch(dia) {
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
					titulo += dia + ' d\u00EDa(s)';
					break;
				case 6:
					titulo += 'm\u00E1s de 5 d\u00EDas';
					break;
				default:
					break;
			}
			Ext.ComponentQuery.query('#grdDetalleProcesoPorDia')[0].setTitle(titulo);
		}
		
		
		/**
		 * Buqueda de datos de indicadores
		 */
		function buscar() {
			
			Ext.ComponentQuery.query('#pnlEstadisticas')[0].setLoading(true);
			
			var form = Ext.ComponentQuery.query('form')[0].getForm();
			
			Ext.Ajax.request({
	        	url       : _GLOBAL_URL_RECUPERACION,
	            params    : {
	            	"params.consulta"     : 'RECUPERAR_TABLERO_INDICADORES',
	            	"params.cdunieco"     : form.findField('params.cdunieco').getValue(),
	            	"params.lineanegocio" : form.findField('params.lineanegocio').getValue(),
	            	"params.cdramo"       : form.findField('params.cdramo').getValue(),
	            	"params.tipotramite"  : form.findField('params.tipotramite').getValue(),
	            	"params.cdagente"     : form.findField('params.cdagente').getValue()
	            },
	            callback  : function (options, success, response){
	            	
	            	Ext.ComponentQuery.query('#pnlEstadisticas')[0].setLoading(false);
	            	
	                if(success){
	                	
	                    var jsonResp = Ext.decode(response.responseText);
	                    debug("jsonResp", jsonResp);
	                    Ext.ComponentQuery.query('#btnIngresados')[0].setText(jsonResp.params.ingresados);
	                    Ext.ComponentQuery.query('#btnProcesados')[0].setText(jsonResp.params.procesados);
	                    Ext.ComponentQuery.query('#btnPendientes')[0].setText(jsonResp.params.pendientes);
	                    // Se crea record:
	                    var pend = Ext.create('PendientesModel', {
						    undia   :    jsonResp.params.undia,
						    dosdias :    jsonResp.params.dosdias,
						    tresdias:    jsonResp.params.tresdias,
						    cuatrodias:  jsonResp.params.cuatrodias,
						    cincodias:   jsonResp.params.cincodias,
						    mascincodias:jsonResp.params.mascincodias,
						    total:       jsonResp.params.total
						});
						
						Ext.ComponentQuery.query('#grdEnProceso')[0].getStore().removeAll();
						Ext.ComponentQuery.query('#grdEnProceso')[0].getStore().loadRecords([pend]);
						Ext.ComponentQuery.query('[name=dspEficacia]')[0].setValue(jsonResp.params.eficacia + ' %');
	                    
	                }else{
	                    showMessage('Error', 'Error al obtener los avisos.', Ext.Msg.OK, Ext.Msg.ERROR);
	                }
	            }
	        });
		}
		
		
		/**
		 * Oculta todos los elementos del panel de detalles y muestra el elemento cuyo itemId se envia
		 * @param {String} itemId
		 */
		function mostrarPanelPrincipal(itemId) {			
			var elements = Ext.ComponentQuery.query('#pnlDetalles > panel');
			
			Ext.Array.each(elements, function(elem, index, elements) {
				elem.hide();
			});
			
			if(!Ext.isEmpty(itemId)) {
				Ext.ComponentQuery.query('#'+itemId)[0].show();
			}
			
		}
		
		
		/**
		 * Oculta todos los elementos del panel de subdetalles y muestra el elemento cuyo itemId se envia
		 * @param {String} itemId
		 */
		function mostrarPanelSubdetalles(itemId) {			
			var elements = Ext.ComponentQuery.query('#pnlSubDetalles > panel');
			
			Ext.Array.each(elements, function(elem, index, elements) {
				elem.hide();
			});
			
			if(!Ext.isEmpty(itemId)) {
				Ext.ComponentQuery.query('#'+itemId)[0].show();
			}
			
		}
		
		
		/**
		 * Carga el detalle de los tramites
		 * @param {int} cdetapa 1 : Ingresados, 2 : En Proceso o Pendientes, 3: Atendidos o Procesados
		 */
		function cargarDetalleTramites(cdetapa) {
			
			// Cambiar estilos:
			var elements = Ext.ComponentQuery.query('#pnlDetalles panel');
			var estilo;
			switch(cdetapa) {
				case 1:
					estilo = 'ingresados';
					break;
				case 2:
					estilo = 'pendientes';
					break;
				case 3:
					estilo = 'procesados';
					break;
			}
			Ext.Array.each(elements, function(elem, index, elements) {
				debug('asignando estilo ' + estilo, elem);
				
				//TODO: mejorar el cambio de estilo:
				elem.removeCls('ingresados');
				elem.removeCls('pendientes');
				elem.removeCls('procesados');
				
				elem.addCls(estilo);
			});
			
			
			mostrarPanelPrincipal('tbDetalle');
								
			// Se setea al valor de cdetapa en parametros y en el campo hidden:
			var par = Ext.ComponentQuery.query('form')[0].getForm().getValues();
			par['params.cdetapa'] = cdetapa;
			Ext.ComponentQuery.query('[name=params.cdetapa]')[0].setValue(cdetapa);
			
			// Se obtiene la consulta de tramites por linea de negocio:
			par['params.consulta'] = 'RECUPERAR_TRAMITES_POR_LINEA_NEGOCIO';
			Ext.ComponentQuery.query('#grdTramitesPorLineaNegocio')[0].getStore().load({
				params : par
			});
			
			// Se obtiene la consulta de tramites por sucursal:
			par['params.consulta'] = 'RECUPERAR_LINEANEGOCIO_POR_SUCURSAL';
			Ext.ComponentQuery.query('#grdTramitesPorSucursal')[0].getStore().load({
				params : par
			});
			
			// Se obtiene la consulta de tramites por usuario:
			par['params.consulta'] = 'RECUPERAR_LINEANEGOCIO_POR_USUARIO';
			Ext.ComponentQuery.query('#grdTramitesPorUsuario')[0].getStore().load({
				params : par
			});
			
		}
		
		////// funciones //////
		</script>
	</head>
	<body>
		<div id="dvEstadisticas"></div>
	</body>
</html>
