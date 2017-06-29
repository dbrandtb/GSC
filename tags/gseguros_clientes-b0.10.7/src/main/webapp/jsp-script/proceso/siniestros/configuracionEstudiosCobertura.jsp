<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Configuraci&oacute;n de Estudios de Concepto, Subcobertura</title>
<script>

///////////////////////
////// Overrides //////
/*///////////////////*/

///////////////////////
////// variables //////
/*///////////////////*/
var _CONTEXT = '${ctx}';
extjs_custom_override_mayusculas = false;

Ext.grid.plugin.RowEditing.prototype.saveBtnText = "Aceptar";

var _URL_CARGA_CATALOGO      = '<s:url namespace="/catalogos" action="obtieneCatalogo" />';

var _Cat_ProductosSalud = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@RAMOSALUD" />';
var _Cat_SubramosSalud  = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TIPSIT" />';
var _Cat_Coberturas     = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@COBERTURAS" />';
var _Cat_SubCoberturas     = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@SUBCOBERTURAS_X_PRODUCTO_COBERTURA" />';


var _UrlConsultaConceptos     = '<s:url namespace="/siniestros"    action="obtieneConceptosSubcob" />';
var _UrlConsultaResultadosEst = '<s:url namespace="/siniestros"    action="obtieneResultadosEst" />';
var _UrlConsultaEstudiosMed   = '<s:url namespace="/siniestros"    action="obtieneEstudiosMed" />';
var _UrlConsultaConfEstudiosMedCob = '<s:url namespace="/siniestros"    action="obtieneConfEstudiosMedCob" />';

var _UrlActualizaConceptos     = '<s:url namespace="/siniestros"    action="actualizaEliminaConceptos" />';
var _UrlActualizaResultadosEst = '<s:url namespace="/siniestros"    action="actualizaEliminaResultadosEstudios" />';
var _UrlActualizaEstudiosMed   = '<s:url namespace="/siniestros"    action="actualizaEliminaConfEstudios" />';
var _UrlActualizaConfEstudiosMedCob = '<s:url namespace="/siniestros"    action="actualizaEliminaConfEstudiosCobertura" />';

/*///////////////////*/
////// variables //////
///////////////////////

Ext.onReady(function()
{
	
	var panelPrincipalConfResEstudios;
	var numWindowAgregar = 0;
	
	/////////////////////
	////// modelos //////
	/*/////////////////*/
	//Modelo de configuracion de Sucursal
	
	Ext.define('modelGridConceptos',
			{
				extend : 'Ext.data.Model'
				,fields :
				['CDCONCEP_ORIG','CDCONCEP','DSCONCEP']
	});

	Ext.define('modelGridResultadosEst',
			{
				extend : 'Ext.data.Model'
				,fields :
				['CDTABRES_ORIG','CDRESEST_ORIG','CDRESEST','DSRESEST','CDTABRES']
	});

	Ext.define('modelGridEstudios',
			{
				extend : 'Ext.data.Model'
				,fields :
				['CDEST_ORIG','CDEST','DSEST','CDTABRES']
	});
	
	Ext.define('modelGridConfEstCob',
			{
				extend : 'Ext.data.Model'
				,fields :
				['CDRAMO','CDTIPSIT','CDGARANT','CDCONVAL','CDCONCEP','CDEST','DSRAMO','DSTIPSIT','DSGARANT','DSCONVAL','DSCONCEP','DSEST',
					'CDRAMO_ORIG','CDTIPSIT_ORIG','CDGARANT_ORIG','CDCONVAL_ORIG','CDCONCEP_ORIG','CDEST_ORIG','SWOBLVAL_ORIG','SWOBLRES_ORIG',
					'SWOBLVAL','SWOBLRES']
	});
	
	/*/////////////////*/
	////// modelos //////
	/////////////////////
	
	////////////////////
	////// stores //////
	/*////////////////*/
	
	var conceptosGridStore = Ext.create('Ext.data.Store',
    {
		pageSize : 20
        ,model   : 'modelGridConceptos'
        ,autoLoad: true
        ,proxy   :
        {
        	type        : 'ajax'
            ,url        : _UrlConsultaConceptos
            ,reader     :
            {
                type  : 'json'
                ,root : 'loadList'
            }
        },
        listeners: {
        	load: function(){
        		_fieldByName('filtroConcep1',panelPrincipalConfResEstudios).setValue('');
        		conceptosGridStore.clearFilter();
        	}
        } 
    });
	
	var conceptoBusquedaStore = Ext.create('Ext.data.Store',{
		pageSize : 20
        ,model   : 'modelGridConceptos'
        ,autoLoad: true
        ,proxy   :
        {
        	type        : 'ajax'
            ,url        : _UrlConsultaConceptos
            ,reader     :
            {
                type  : 'json'
                ,root : 'loadList'
            }
        }
    });

	var resultadosEstGridStore = Ext.create('Ext.data.Store',
    {
		pageSize : 20
        ,model   : 'modelGridResultadosEst'
        ,autoLoad: true
        ,proxy   :
        {
        	type        : 'ajax'
            ,url        : _UrlConsultaResultadosEst
            ,reader     :
            {
                type  : 'json'
                ,root : 'loadList'
            }
        },
        listeners: {
        	load: function(){
        		_fieldByName('filtroResultado',panelPrincipalConfResEstudios).setValue('');
        		_fieldByName('filtroAgrupadorResultado',panelPrincipalConfResEstudios).setValue('');
        		resultadosEstGridStore.clearFilter();
        	}
        } 
    });

	var estudiosGridStore = Ext.create('Ext.data.Store',
    {
		pageSize : 20
        ,model   : 'modelGridEstudios'
        ,autoLoad: true
        ,proxy   :
        {
        	type        : 'ajax'
            ,url        : _UrlConsultaEstudiosMed
            ,reader     :
            {
                type  : 'json'
                ,root : 'loadList'
            }
        },
        listeners: {
        	load: function(){
        		_fieldByName('filtroEstudio',panelPrincipalConfResEstudios).setValue('');
        		_fieldByName('filtroResultadoEst',panelPrincipalConfResEstudios).setValue('');
        		estudiosGridStore.clearFilter();
        	}
        } 
    });
	
	var estudioBusquedaStore = Ext.create('Ext.data.Store',{
		pageSize : 20
        ,model   : 'modelGridEstudios'
        ,autoLoad: true
        ,proxy   :
        {
        	type        : 'ajax'
            ,url        : _UrlConsultaEstudiosMed
            ,reader     :
            {
                type  : 'json'
                ,root : 'loadList'
            }
        } 
    });

	var contEstCobGridStore = Ext.create('Ext.data.Store',
    {
		pageSize : 20
        ,model   : 'modelGridConfEstCob'
        ,autoLoad: false
        ,proxy   :
        {
        	type        : 'ajax'
            ,url        : _UrlConsultaConfEstudiosMedCob
            ,reader     :
            {
                type  : 'json'
                ,root : 'loadList'
            }
        }
    });
	
	/*////////////////*/
	////// stores //////
	////////////////////
	
	///////////////////////
	////// contenido //////
	/*///////////////////*/
	
	panelPrincipalConfResEstudios = Ext.create('Ext.panel.Panel',{
		title: 'Configuraci\u00f3n de estudios y resultados m&eacute;dicos para programas de detecci&oacute;n oportuna.',
		width: 930,
		titleAlign: 'center',
		defaults : {
			style : 'margin : 4px;'
		}
	    ,renderTo : 'mainDivConfSucurTrabajo'
	    ,items :
	    	[
	    		{
                    xtype: 'form',
                    //bodyPadding: '10 3 10 3',
                    defaults : {
						style : 'margin : 6px;'
					},
                    layout: {
                        type: 'column'
                    },
                    title: 'Filtro de B&uacute;queda.',
                    itemId: 'panelBusqueda',
                    items: [
                        {
                            xtype: 'combobox',
                            fieldLabel: 'Producto',
                            name: 'params.cdramo',
                            labelAlign: 'right',
                            labelWidth: 70,
                            width: 250,
                            displayField: 'value',
                            valueField: 'key',
                            forceSelection: true,
        	                anyMatch      : true,
                            queryMode     : 'local',
                            store       : Ext.create('Ext.data.Store', {
                                model : 'Generic',
                                autoLoad : true,
                                proxy : {
                                    type : 'ajax',
                                    url : _URL_CARGA_CATALOGO,
                                    extraParams : {
                                        catalogo : _Cat_ProductosSalud
                                    },
                                    reader : {
                                        type : 'json',
                                        root : 'lista'
                                    }
                                }
                            }),
                            listeners: {
                            	select: function(cmb, records){
                            		//alert(records[0].get('key'));
                            		//var panelBusq = btn.up('form');
                            		var comboModalidad = cmb.up('form').down('[name=params.cdtipsit]');
                            		comboModalidad.getStore().load({
                            			params:{
                            				'params.idPadre' : records[0].get('key')
                            			}
                            		})
                            	}
                            }
                        },
                        {
                            xtype: 'combobox',
                            fieldLabel: 'Subramo',
                            name: 'params.cdtipsit',
                            labelAlign: 'right',
                            labelWidth: 70,
                            width: 250,
                            displayField: 'value',
                            valueField: 'key',
                            forceSelection: true,
        	                anyMatch      : true,
                            queryMode     : 'local',
                            store       : Ext.create('Ext.data.Store', {
                                model : 'Generic',
                                autoLoad : false,
                                proxy : {
                                    type : 'ajax',
                                    url : _URL_CARGA_CATALOGO,
                                    extraParams : {
                                        catalogo : _Cat_SubramosSalud
                                    },
                                    reader : {
                                        type : 'json',
                                        root : 'lista'
                                    }
                                }
                            }),
                            listeners: {
                            	select: function(cmb, records){
                            		var comboRamo = cmb.up('form').down('[name=params.cdramo]');
                            		var comboGarantias = cmb.up('form').down('[name=params.cdgarant]');
                            		comboGarantias.getStore().load({
                            			params:{
                            				'params.cdramo' : comboRamo.getValue(),
                            				'params.cdtipsit' : records[0].get('key')
                            			}
                            		});
                            	}
                            }
                        },
                        {
                            xtype: 'combobox',
                            fieldLabel: 'Cobertura',
                            name: 'params.cdgarant',
                            labelAlign: 'right',
                            labelWidth: 70,
                            width: 250,
                            displayField: 'value',
                            valueField: 'key',
                            forceSelection: true,
        	                anyMatch      : true,
                            queryMode     : 'local',
                            store       : Ext.create('Ext.data.Store', {
                                model : 'Generic',
                                autoLoad : false,
                                proxy : {
                                    type : 'ajax',
                                    url : _URL_CARGA_CATALOGO,
                                    extraParams : {
                                        catalogo : _Cat_Coberturas
                                    },
                                    reader : {
                                        type : 'json',
                                        root : 'lista'
                                    }
                                }
                            }),
                            listeners: {
                            	select: function(cmb, records){
                            		var comboRamo = cmb.up('form').down('[name=params.cdramo]');
                            		var comboModalidad = cmb.up('form').down('[name=params.cdtipsit]');
                            		
                            		var comboSubcoberturas= cmb.up('form').down('[name=params.cdconval]');
                            		comboSubcoberturas.getStore().load({
                            			params:{
                            				'params.cdramo'   : comboRamo.getValue(),
                            				'params.cdtipsit' : comboModalidad.getValue(),
                            				'params.cdgarant' : records[0].get('key')
                            			}
                            		});
                            	}
                            }
                        },
                        {
                            xtype: 'combobox',
                            fieldLabel: 'Subcobertura',
                            name: 'params.cdconval',
                            labelAlign: 'right',
                            labelWidth: 70,
                            width: 250,
                            displayField: 'value',
                            valueField: 'key',
                            forceSelection: true,
        	                anyMatch      : true,
                            queryMode     : 'local',
                            store       : Ext.create('Ext.data.Store', {
                                model : 'Generic',
                                autoLoad : false,
                                proxy : {
                                    type : 'ajax',
                                    url : _URL_CARGA_CATALOGO,
                                    extraParams : {
                                        catalogo : _Cat_SubCoberturas
                                    },
                                    reader : {
                                        type : 'json',
                                        root : 'lista'
                                    }
                                }
                            })
                            
                        },
                        {
                            xtype: 'combobox',
                            fieldLabel: 'Concepto',
                            name: 'params.cdconcep',
                            labelAlign: 'right',
                            labelWidth: 70,
                            width: 250,
                            displayField: 'DSCONCEP',
                            valueField: 'CDCONCEP',
                            forceSelection: true,
        	                anyMatch      : true,
                            queryMode     : 'local',
                            listConfig : {
                            	itemTpl : '{CDCONCEP} - {DSCONCEP}'
                            },
                            store         : conceptoBusquedaStore
                        },
                        {
                            xtype: 'combobox',
                            fieldLabel: 'Estudio',
                            name: 'params.cdest',
                            labelAlign: 'right',
                            labelWidth: 70,
                            width: 250,
                            displayField: 'DSEST',
                            valueField: 'CDEST',
                            listConfig : {
                            	itemTpl : '{CDEST} - {DSEST}'
                            },
                            forceSelection: true,
        	                anyMatch      : true,
                            queryMode     : 'local',
                            store: estudioBusquedaStore
                            
                        }
                    ],
                    buttonAlign: 'center',
                    buttons: [
                        {
                            text: 'Buscar',
                            icon:_CONTEXT+'/resources/fam3icons/icons/magnifier.png',
                            handler: function(btn){
                            	var panelBusq = btn.up('form');
   	                			contEstCobGridStore.load({
   	                				params: panelBusq.getForm().getValues()
   	                			});
                            }
                        },{
    	                	text: 'Limpiar'
    	                		,icon:_CONTEXT+'/resources/fam3icons/icons/arrow_refresh.png'
        	                	,handler: function(btn){
       	                			var panelBusq = btn.up('form');
       	                			panelBusq.getForm().reset();
        	                	}	
        	             }
                    ]
                },
                {
                    xtype: 'gridpanel',
                    height: 310,
                    itemId: 'gridBusqueda',
                    title: 'Resultados de B&uacute;queda para Configuraci&oacute;n de Estudios M&eacute;dicos Por Producto',
                    store: contEstCobGridStore,
                    columns: [
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'DSRAMO',
                            text: 'Producto',
                            width: 100
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'DSTIPSIT',
                            text: 'Subramo',
                            width: 120
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'DSGARANT',
                            text: 'Cobertura',
                            width: 150
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'DSCONVAL',
                            text: 'SubCobertura',
                            width: 200
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'DSCONCEP',
                            text: 'Concepto',
                            width: 270,
                            renderer: function(value,metadata,recordFila){
                            	return recordFila.get('CDCONCEP')+' - '+recordFila.get('DSCONCEP');
                            }
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'DSEST',
                            text: 'Estudio',
                            width: 380,
                            renderer: function(value,metadata,recordFila){
                            	return recordFila.get('CDEST')+' - '+recordFila.get('DSEST');
                            }
                        }
                    ],
                   /*  buttonAlign: 'center',
                    buttons: [
                        {
                            xtype: 'button',
                            text: 'Guardar Cambios de Configuraci&oacute;n'
                        }
                    ], */
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            dock: 'top',
                            items: [
                                {
                                    xtype: 'button',
                                    text: 'Agregar',
                                    icon:_CONTEXT+'/resources/fam3icons/icons/page_add.png',
                                    handler: function(btn){
                                    	
                                    	
                                    	var comboRamo = panelPrincipalConfResEstudios.down('[name=params.cdramo]');
    									var comboModalidad = panelPrincipalConfResEstudios.down('[name=params.cdtipsit]');
                                		var comboGarantias = panelPrincipalConfResEstudios.down('[name=params.cdgarant]');
    									var comboSubcoberturas= panelPrincipalConfResEstudios.down('[name=params.cdconval]');
    									
                                    	var nuevoRecConf = new modelGridConfEstCob();
                                    	nuevoRecConf.set('CDRAMO',comboRamo.getValue());
                                    	nuevoRecConf.set('CDTIPSIT',comboModalidad.getValue());
                                    	nuevoRecConf.set('CDGARANT',comboGarantias.getValue());
                                    	nuevoRecConf.set('CDCONVAL',comboSubcoberturas.getValue());
                                    	
                                    	agregarEditarConfEstudios(nuevoRecConf,btn);
                                    }
                                },
                                {
                                    xtype: 'button',
                                    text: 'Editar',
                                    icon:_CONTEXT+'/resources/fam3icons/icons/page_edit.png',
                                    handler: function(btn){
                                    	var gridConfEstudiosCob = btn.up('grid');
                                    	
                                    	if(gridConfEstudiosCob.getSelectionModel().hasSelection()){
                                   			var recordSelected = gridConfEstudiosCob.getSelectionModel().getLastSelected();
                                   			agregarEditarConfEstudios(recordSelected,btn);
                                   		}else{
                                   			mensajeWarning("Debe seleccionar un registro para editar.")	
                                   		}
                                    }
                                },
                                {
                                    xtype: 'button',
                                    text: 'Eliminar',
                                    icon:_CONTEXT+'/resources/fam3icons/icons/page_delete.png',
                                    handler: function(btn){
										var gridConfEstudiosCob = btn.up('grid');
                                    	
                                    	if(gridConfEstudiosCob.getSelectionModel().hasSelection()){
                                   			var recordSelected = gridConfEstudiosCob.getSelectionModel().getLastSelected();
                                   			eliminarConfEstudios(recordSelected,btn);
                                   		}else{
                                   			mensajeWarning("Debe seleccionar un registro para eliminar.")	
                                   		}
                                    }
                                }
                            ]
                        }
                    ]
                },
                {
                    xtype: 'gridpanel',
                    height: 250,
                    title: 'Conceptos de Subcoberturas',
                    plugins: [Ext.create('Ext.grid.plugin.RowEditing', {clicksToEdit: 2, pluginId: 'pluginConcepts'})],
                    store: conceptosGridStore,
                    columns: [
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'CDCONCEP',
                            text: 'Clave Concepto',
                            flex: 1,
                            editor : {
            	                xtype      : 'textfield',
            	                name       : 'CDCONCEP',
            	                maxLength  : 6,
            	               	allowBlank : false
            	            }
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'DSCONCEP',
                            text: 'Descripci&oacute;n de Concepto',
                            flex: 2,
                            editor : {
            	                xtype      : 'textfield',
            	                name       : 'DSCONCEP',
            	                maxLength  : 100,
            	               	allowBlank : false
            	            }
                        }
                    ],
                    buttonAlign: 'center',
                    buttons: [
                    	{
                            xtype: 'button',
                            icon:_CONTEXT+'/resources/fam3icons/icons/disk.png',
                            text: 'Guardar Cambios',
                            handler: function (btn,e){
                            	
                            	var gridConceptos = btn.up('grid');
                            	
                               	if(gridConceptos.getPlugin('pluginConcepts').editing){
                            		mensajeWarning('Antes de guardar primero finalice la edicion de los Conceptos.');
                            		return;
                            	}
                                
                                var updateList = [];
                                
                                gridConceptos.getStore().getModifiedRecords().forEach(function(record,index,arr){
                                	if(record.dirty){
                                		var datosResultado = {
                                          		 'pi_cdconcep_ant': record.get('CDCONCEP_ORIG'),
                                          		 'pi_cdconcep'    : record.get('CDCONCEP'),
                                          		 'pi_dsconcep'    : record.get('DSCONCEP'),
                                          		 'pi_swop'        : 'U'
                                           };
                                      	    
                                           updateList.push(datosResultado);
                                	}
                                });
                                
								gridConceptos.getStore().getRemovedRecords().forEach(function(record,index,arr){
                                	
                                    var datosResultado = {
                                   		 'pi_cdconcep_ant': record.get('CDCONCEP_ORIG'),
                                   		 'pi_cdconcep'    : record.get('CDCONCEP'),
                                   		 'pi_dsconcep'    : record.get('DSCONCEP'),
                                   		 'pi_swop'        : 'D'
                                    };
                               	    
                                    updateList.push(datosResultado);
                                });
                                
                                
                                if(updateList.length <= 0){
                            		mensajeWarning('No hay cambios que guardar.');
                               	 	return;
                                }
                                
                                debug('Lista de Resultados a guardar: ',updateList);

                                var maskGuarda = _maskLocal('Guardando...');
                                
                                Ext.Ajax.request({
                                    url: _UrlActualizaConceptos,
                                    jsonData : {
                                        'saveList' : updateList
                                    },
                                    success  : function(response, options){
                                   	 maskGuarda.close();
                                        var json = Ext.decode(response.responseText);
                                        if(json.success){
                                        	mensajeCorrecto('Aviso','Se ha guardado correctamente.');
                               	        	gridConceptos.getStore().reload();
                               	        	conceptoBusquedaStore.reload();
                                        }else{
                                            mensajeError(json.mensaje);
                                            gridConceptos.getStore().reload();
                               	        	conceptoBusquedaStore.reload();
                                        }
                                    }
                                    ,failure  : function(response, options){
                                   	 maskGuarda.close();
                                        var json = Ext.decode(response.responseText);
                                        mensajeError(json.mensaje);
                                    }
                                });
                            
                                
                            }
                        }
                    ],
                    dockedItems: [{
                            xtype: 'toolbar',
                            dock: 'top',
                            items: [
                                {
                                    xtype: 'button',
                                    text: 'Agregar',
                                    icon:_CONTEXT+'/resources/fam3icons/icons/page_add.png',
                                   	handler: function(btn){
                                   		var gridConceptos = btn.up('grid');
                                   		var nuevoRecord = new modelGridConceptos();
                                   		gridConceptos.getStore().add(nuevoRecord);
                                   		
                                   		gridConceptos.getPlugin('pluginConcepts').startEdit(nuevoRecord,gridConceptos.columns[0]);
                                   		
                                   	}
                                },
                                {
                                    xtype: 'button',
                                    text: 'Eliminar',
                                    icon:_CONTEXT+'/resources/fam3icons/icons/page_delete.png',
                                    handler: function(btn){
										var gridConceptos = btn.up('grid');
                                   		
                                   		if(gridConceptos.getSelectionModel().hasSelection()){
                                   			var recordSelected = gridConceptos.getSelectionModel().getLastSelected();
                                   			gridConceptos.getStore().remove(recordSelected);
                                   		}else{
                                   			mensajeWarning("Debe seleccionar un registro para eliminar.")	
                                   		}
                                    }
                                },'->','-',{
                                    xtype : 'textfield',
                                    name : 'filtroConcep1',
                                    fieldLabel : '<span style="color:white;font-size:12px;font-weight:bold;">Filtrar Concepto:</span>',
                                    labelWidth : 100,
                                    width: 240,
                                    listeners:{
                                    	change: function(elem,newValue,oldValue){
                                    		newValue = Ext.util.Format.uppercase(newValue);
                    						if( newValue == Ext.util.Format.uppercase(oldValue)){
                    							return false;
                    						}
                    						
                    						try{
                    							conceptosGridStore.removeFilter('filtroConcept');
                    							conceptosGridStore.filter(Ext.create('Ext.util.Filter', {property: 'DSCONCEP', anyMatch: true, value: newValue, root: 'data', id:'filtroConcept'}));
                    						}catch(e){
                    							error('Error al filtrar por concepto',e);
                    						}
                                    	}
                                    }
                                }
                            ]
                        }
                    ]
                },
                {
                    xtype: 'gridpanel',
                    height: 300,
                    title: 'Estudios M&eacute;dicos',
                    plugins: [Ext.create('Ext.grid.plugin.RowEditing', {clicksToEdit: 2, pluginId: 'pluginConfEstudios'})],
                    store: estudiosGridStore,
                    columns: [
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'CDEST',
                            text: 'Clave Estudio',
                            flex: 1,
                            editor : {
            	                xtype      : 'textfield',
            	                name       : 'CDEST',
            	                maxLength  : 6,
            	               	allowBlank : false
            	            }
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'DSEST',
                            text: 'Descripci&oacute;n del Estudio',
                            flex: 2,
                            editor : {
            	                xtype      : 'textfield',
            	                name       : 'DSEST',
            	                maxLength  : 80,
            	               	allowBlank : false
            	            }
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'CDTABRES',
                            text: 'Agrupador de Resultados Asociado',
                            flex: 1,
                            editor : {
            	                xtype      : 'textfield',
            	                name       : 'CDTABRES',
            	                maxLength  : 6
            	               	//allowBlank : false
            	            }
                        }
                    ],
                    buttonAlign: 'center',
                    buttons:[{
                        xtype: 'button',
                        icon:_CONTEXT+'/resources/fam3icons/icons/disk.png',
                        text: 'Guardar Cambios',
                        handler: function (btn,e){
                        	
                        	var gridEstudios = btn.up('grid');
                        	
                           	if(gridEstudios.getPlugin('pluginConfEstudios').editing){
                        		mensajeWarning('Antes de guardar primero finalice la edicion de los Estudios.');
                        		return;
                        	}
                            
                            var updateList = [];
                            
                            gridEstudios.getStore().getModifiedRecords().forEach(function(record,index,arr){
                            	if(record.dirty){
                            			var datosResultado = {
                                      		 'pi_cdest_ant': record.get('CDEST_ORIG'),
                                      		 'pi_cdest'    : record.get('CDEST'),
                                      		 'pi_dsest'    : record.get('DSEST'),
                                      		 'pi_cdtabres' : record.get('CDTABRES'),
                                      		 'pi_swop'     : 'U'
                                       };
                                  	    
                                       updateList.push(datosResultado);
                            	}
                            });
                            
                            gridEstudios.getStore().getRemovedRecords().forEach(function(record,index,arr){
                            	
                            	var datosResultado = {
                                 		 'pi_cdest_ant': record.get('CDEST_ORIG'),
                                 		 'pi_cdest'    : record.get('CDEST'),
                                 		 'pi_dsest'    : record.get('DSEST'),
                                 		 'pi_cdtabres' : record.get('CDTABRES'),
                                 		 'pi_swop'     : 'D'
                                };
                           	    
                                updateList.push(datosResultado);
                            });
                            
                            
                            if(updateList.length <= 0){
                        		mensajeWarning('No hay cambios que guardar.');
                           	 	return;
                            }
                            
                            debug('Lista de Estudios a guardar: ',updateList);

                            var maskGuarda = _maskLocal('Guardando...');
                            
                            Ext.Ajax.request({
                                url: _UrlActualizaEstudiosMed,
                                jsonData : {
                                    'saveList' : updateList
                                },
                                success  : function(response, options){
                               	 maskGuarda.close();
                                    var json = Ext.decode(response.responseText);
                                    if(json.success){
                                    	mensajeCorrecto('Aviso','Se ha guardado correctamente.');
                                    	gridEstudios.getStore().reload();
                                    	estudioBusquedaStore.reload();
                                    }else{
                                        mensajeError(json.mensaje);
                                        gridEstudios.getStore().reload();
                                    	estudioBusquedaStore.reload();
                                    }
                                }
                                ,failure  : function(response, options){
                               	 maskGuarda.close();
                                    var json = Ext.decode(response.responseText);
                                    mensajeError(json.mensaje);
                                }
                            });
                        
                        }
                    }],
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            dock: 'top',
                            items: [
                                {
                                    xtype: 'button',
                                    text: 'Agregar',
                                    icon:_CONTEXT+'/resources/fam3icons/icons/page_add.png',
                                    handler: function(btn){
                                   		var gridConfEst = btn.up('grid');
                                   		var nuevoRecord = new modelGridEstudios();
                                   		gridConfEst.getStore().add(nuevoRecord);
                                   		
                                   		gridConfEst.getPlugin('pluginConfEstudios').startEdit(nuevoRecord,gridConfEst.columns[0]);
                                   		
                                   	}
                                },
                                {
                                    xtype: 'button',
                                    text: 'Eliminar',
                                    icon:_CONTEXT+'/resources/fam3icons/icons/page_delete.png',
                                    handler: function(btn){
										var gridConfEst = btn.up('grid');
                                   		
                                   		if(gridConfEst.getSelectionModel().hasSelection()){
                                   			var recordSelected = gridConfEst.getSelectionModel().getLastSelected();
                                   			gridConfEst.getStore().remove(recordSelected);
                                   		}else{
                                   			mensajeWarning("Debe seleccionar un registro para eliminar.")	
                                   		}
                                    }
                                },'->','-',{
                                    xtype : 'textfield',
                                    name : 'filtroEstudio',
                                    fieldLabel : '<span style="color:white;font-size:12px;font-weight:bold;">Filtrar Estudio:</span>',
                                    labelWidth : 100,
                                    width: 240,
                                    listeners:{
                                    	change: function(elem,newValue,oldValue){
                                    		newValue = Ext.util.Format.uppercase(newValue);
                    						if( newValue == Ext.util.Format.uppercase(oldValue)){
                    							return false;
                    						}
                    						
                    						try{
                    							estudiosGridStore.removeFilter('filtroEst');
                    							estudiosGridStore.filter(Ext.create('Ext.util.Filter', {property: 'DSEST', anyMatch: true, value: newValue, root: 'data', id:'filtroEst'}));
                    						}catch(e){
                    							error('Error al filtrar por estudio',e);
                    						}
                                    	}
                                    }
                                },{
                                    xtype : 'textfield',
                                    name : 'filtroResultadoEst',
                                    fieldLabel : '<span style="color:white;font-size:12px;font-weight:bold;">Filtrar por Agrupador de Resultado:</span>',
                                    labelWidth : 180,
                                    width: 290,
                                    listeners:{
                                    	change: function(elem,newValue,oldValue){
                                    		newValue = Ext.util.Format.uppercase(newValue);
                    						if( newValue == Ext.util.Format.uppercase(oldValue)){
                    							return false;
                    						}
                    						
                    						try{
                    							estudiosGridStore.removeFilter('filtroResEst');
                    							estudiosGridStore.filter(Ext.create('Ext.util.Filter', {property: 'CDTABRES', anyMatch: true, value: newValue, root: 'data', id:'filtroResEst'}));
                    						}catch(e){
                    							error('Error al filtrar por resultado',e);
                    						}
                                    	}
                                    }
                                }
                            ]
                        }
                    ]
                },
                {
                    xtype: 'gridpanel',
                    height: 300,
                    title: 'Resultados de Estudios M&eacute;dicos',
                    plugins: [Ext.create('Ext.grid.plugin.RowEditing', {clicksToEdit: 2, pluginId: 'pluginResEstudios'})],
                    store: resultadosEstGridStore,
                    columns: [
                    	{
                            xtype: 'gridcolumn',
                            dataIndex: 'CDTABRES',
                            text: 'Agrupador de Resultado',
                            flex: 1,
                            editor : {
            	                xtype      : 'textfield',
            	                name       : 'CDTABRES',
            	                maxLength  : 6,
            	               	allowBlank : false
            	            }
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'CDRESEST',
                            text: 'Clave Resultado',
                            flex: 1,
                            editor : {
            	                xtype      : 'textfield',
            	                name       : 'CDRESEST',
            	                maxLength  : 6,
            	               	allowBlank : false
            	            }
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'DSRESEST',
                            text: 'Descripci&oacute;n Resultado',
                            flex: 2,
                            editor : {
            	                xtype      : 'textfield',
            	                name       : 'DSRESEST',
            	                maxLength  : 50,
            	               	allowBlank : false
            	            }
                        }
                        
                    ],
                    buttonAlign: 'center',
                    buttons:[{
                        xtype: 'button',
                        icon:_CONTEXT+'/resources/fam3icons/icons/disk.png',
                        text: 'Guardar Cambios',
						handler: function (btn,e){
                        	
                        	var gridResEstudios = btn.up('grid');
                        	
                           	if(gridResEstudios.getPlugin('pluginResEstudios').editing){
                        		mensajeWarning('Antes de guardar primero finalice la edicion de los Resultados de Estudios.');
                        		return;
                        	}
                            
                            var updateList = [];
                            
                            gridResEstudios.getStore().getModifiedRecords().forEach(function(record,index,arr){
                            	if(record.dirty){
                            			var datosResultado = {
                                      		 'pi_cdtabres_ant': record.get('CDTABRES_ORIG'),
                                      		 'pi_cdresest_ant': record.get('CDRESEST_ORIG'),
                                      		 'pi_cdtabres'    : record.get('CDTABRES'),
                                      		 'pi_cdresest'    : record.get('CDRESEST'),
                                      		 'pi_dsresest'    : record.get('DSRESEST'),
                                      		 'pi_swop'        : 'U'
                                       };
                                  	    
                                       updateList.push(datosResultado);
                            	}
                            });
                            
                            gridResEstudios.getStore().getRemovedRecords().forEach(function(record,index,arr){
                            	
                            		var datosResultado = {
                                 		 'pi_cdtabres_ant': record.get('CDTABRES_ORIG'),
                                 		 'pi_cdresest_ant': record.get('CDRESEST_ORIG'),
                                 		 'pi_cdtabres'    : record.get('CDTABRES'),
                                 		 'pi_cdresest'    : record.get('CDRESEST'),
                                 		 'pi_dsresest'    : record.get('DSRESEST'),
                                 		 'pi_swop'        : 'D'
                                 	};
                           	    
                                updateList.push(datosResultado);
                            });
                            
                            
                            if(updateList.length <= 0){
                        		mensajeWarning('No hay cambios que guardar.');
                           	 	return;
                            }
                            
                            debug('Lista de Resultados a guardar: ',updateList);

                            var maskGuarda = _maskLocal('Guardando...');
                            
                            Ext.Ajax.request({
                                url: _UrlActualizaResultadosEst,
                                jsonData : {
                                    'saveList' : updateList
                                },
                                success  : function(response, options){
                               	 maskGuarda.close();
                                    var json = Ext.decode(response.responseText);
                                    if(json.success){
                                    	mensajeCorrecto('Aviso','Se ha guardado correctamente.');
                                    	gridResEstudios.getStore().reload();
                                    }else{
                                        mensajeError(json.mensaje);
                                        gridResEstudios.getStore().reload();
                                    }
                                }
                                ,failure  : function(response, options){
                               	 maskGuarda.close();
                                    var json = Ext.decode(response.responseText);
                                    mensajeError(json.mensaje);
                                }
                            });
                        
                            
                        }
                    }],
                    dockedItems: [{
                            xtype: 'toolbar',
                            dock: 'top',
                            items: [
                                {
                                    xtype: 'button',
                                    text: 'Agregar',
                                    icon:_CONTEXT+'/resources/fam3icons/icons/page_add.png',
                                    handler: function(btn){
                                   		var gridResEst = btn.up('grid');
                                   		var nuevoRecord = new modelGridResultadosEst();
                                   		gridResEst.getStore().add(nuevoRecord);
                                   		
                                   		gridResEst.getPlugin('pluginResEstudios').startEdit(nuevoRecord,gridResEst.columns[0]);
                                   	}
                                },
                                {
                                    xtype: 'button',
                                    text: 'Eliminar',
                                    icon:_CONTEXT+'/resources/fam3icons/icons/page_delete.png',
                                    handler: function(btn){
										var gridResEst = btn.up('grid');
                                   		
                                   		if(gridResEst.getSelectionModel().hasSelection()){
                                   			var recordSelected = gridResEst.getSelectionModel().getLastSelected();
                                   			gridResEst.getStore().remove(recordSelected);
                                   		}else{
                                   			mensajeWarning("Debe seleccionar un registro para eliminar.")	
                                   		}
                                    }
                                },'->','-',{
                                    xtype : 'textfield',
                                    name : 'filtroAgrupadorResultado',
                                    fieldLabel : '<span style="color:white;font-size:12px;font-weight:bold;">Filtrar Agrupador de Resultado:</span>',
                                    labelWidth : 180,
                                    width: 290,
                                    listeners:{
                                    	change: function(elem,newValue,oldValue){
                                    		newValue = Ext.util.Format.uppercase(newValue);
                    						if( newValue == Ext.util.Format.uppercase(oldValue)){
                    							return false;
                    						}
                    						
                    						try{
                    							resultadosEstGridStore.removeFilter('filtroAgrupaRes');
                    							resultadosEstGridStore.filter(Ext.create('Ext.util.Filter', {property: 'CDTABRES', anyMatch: true, value: newValue, root: 'data', id:'filtroAgrupaRes'}));
                    						}catch(e){
                    							error('Error al filtrar por resultado',e);
                    						}
                                    	}
                                    }
                                },{
                                    xtype : 'textfield',
                                    name : 'filtroResultado',
                                    fieldLabel : '<span style="color:white;font-size:12px;font-weight:bold;">Filtrar Resultado:</span>',
                                    labelWidth : 100,
                                    width: 240,
                                    listeners:{
                                    	change: function(elem,newValue,oldValue){
                                    		newValue = Ext.util.Format.uppercase(newValue);
                    						if( newValue == Ext.util.Format.uppercase(oldValue)){
                    							return false;
                    						}
                    						
                    						try{
                    							resultadosEstGridStore.removeFilter('filtroRes');
                    							resultadosEstGridStore.filter(Ext.create('Ext.util.Filter', {property: 'DSRESEST', anyMatch: true, value: newValue, root: 'data', id:'filtroRes'}));
                    						}catch(e){
                    							error('Error al filtrar por resultado',e);
                    						}
                                    	}
                                    }
                                }
                            ]
                        }
                    ]
                }
            ]
	});
	

	/*///////////////////*/
	////// contenido //////
	///////////////////////
	

function agregarEditarConfEstudios(recordEditar,btnGrid){
		
	var windowConfEstudios;
	var esNuevoRegistro = (!Ext.isEmpty(recordEditar) && recordEditar.phantom == false) ? false : true;

	var panelAgregarEditarConfig = Ext.create('Ext.form.Panel',{
        defaults : {
			style : 'margin : 6px;'
		},
        layout: {
            type: 'column'
        },
        items: [
            {
                xtype: 'combobox',
                fieldLabel: 'Producto',
                name: 'CDRAMO',
                labelAlign: 'right',
                labelWidth: 70,
                width: 250,
                displayField: 'value',
                valueField: 'key',
                forceSelection: true,
                anyMatch      : true,
                queryMode     : 'local',
                allowBlank: false,
                store       : Ext.create('Ext.data.Store', {
                    model   : 'Generic',
                    autoLoad : true,
                    proxy : {
                        type : 'ajax',
                        url : _URL_CARGA_CATALOGO,
                        extraParams : {
                            catalogo : _Cat_ProductosSalud
                        },
                        reader : {
                            type : 'json',
                            root : 'lista'
                        }
                    }
                }),
                listeners: {
                	select: function(cmb, records){
                		//alert(records[0].get('key'));
                		//var panelBusq = btn.up('form');
                		var comboModalidad = cmb.up('form').down('[name=CDTIPSIT]');
                		comboModalidad.getStore().load({
                			params:{
                				'params.idPadre' : records[0].get('key')
                			}
                		})
                	}
                }
            },
            {
                xtype: 'combobox',
                fieldLabel: 'Subramo',
                name: 'CDTIPSIT',
                labelAlign: 'right',
                labelWidth: 70,
                width: 250,
                displayField: 'value',
                valueField: 'key',
                forceSelection: true,
                anyMatch      : true,
                queryMode     : 'local',
                allowBlank: false,
                store       : Ext.create('Ext.data.Store', {
                    model : 'Generic',
                    autoLoad : false,
                    proxy : {
                        type : 'ajax',
                        url : _URL_CARGA_CATALOGO,
                        extraParams : {
                            catalogo : _Cat_SubramosSalud
                        },
                        reader : {
                            type : 'json',
                            root : 'lista'
                        }
                    }
                }),
                listeners: {
                	select: function(cmb, records){
                		var comboRamo = cmb.up('form').down('[name=CDRAMO]');
                		var comboGarantias = cmb.up('form').down('[name=CDGARANT]');
                		comboGarantias.getStore().load({
                			params:{
                				'params.cdramo' : comboRamo.getValue(),
                				'params.cdtipsit' : records[0].get('key')
                			}
                		});
                	}
                }
            },
            {
                xtype: 'combobox',
                fieldLabel: 'Cobertura',
                name: 'CDGARANT',
                labelAlign: 'right',
                labelWidth: 70,
                width: 250,
                displayField: 'value',
                valueField: 'key',
                forceSelection: true,
                anyMatch      : true,
                queryMode     : 'local',
                allowBlank: false,
                store       : Ext.create('Ext.data.Store', {
                    model : 'Generic',
                    autoLoad : false,
                    proxy : {
                        type : 'ajax',
                        url : _URL_CARGA_CATALOGO,
                        extraParams : {
                            catalogo : _Cat_Coberturas
                        },
                        reader : {
                            type : 'json',
                            root : 'lista'
                        }
                    }
                }),
                listeners: {
                	select: function(cmb, records){
                		
                		var comboRamo = cmb.up('form').down('[name=CDRAMO]');
                		var comboModalidad = cmb.up('form').down('[name=CDTIPSIT]');
                		
                		var comboSubcoberturas= cmb.up('form').down('[name=CDCONVAL]');
                		comboSubcoberturas.getStore().load({
                			params:{
                				'params.cdramo'   : comboRamo.getValue(),
                				'params.cdtipsit' : comboModalidad.getValue(),
                				'params.cdgarant' : records[0].get('key')
                			}
                		});
                	}
                }
            },
            {
                xtype: 'combobox',
                fieldLabel: 'Subcobertura',
                name: 'CDCONVAL',
                labelAlign: 'right',
                labelWidth: 70,
                width: 250,
                displayField: 'value',
                valueField: 'key',
                forceSelection: true,
                anyMatch      : true,
                queryMode     : 'local',
                allowBlank: false,
                store       : Ext.create('Ext.data.Store', {
                    model : 'Generic',
                    autoLoad : false,
                    proxy : {
                        type : 'ajax',
                        url : _URL_CARGA_CATALOGO,
                        extraParams : {
                            catalogo : _Cat_SubCoberturas
                        },
                        reader : {
                            type : 'json',
                            root : 'lista'
                        }
                    }
                })
                
            },
            {
                xtype: 'combobox',
                fieldLabel: 'Concepto',
                name: 'CDCONCEP',
                labelAlign: 'right',
                labelWidth: 70,
                width: 250,
                displayField: 'DSCONCEP',
                valueField: 'CDCONCEP',
                forceSelection: true,
                anyMatch      : true,
                queryMode     : 'local',
                listConfig : {
                	itemTpl : '{CDCONCEP} - {DSCONCEP}'
                },
                allowBlank: false,
                store         : Ext.create('Ext.data.Store',{
            		pageSize : 20
                    ,model   : 'modelGridConceptos'
                    ,autoLoad: true
                    ,proxy   :
                    {
                    	type        : 'ajax'
                        ,url        : _UrlConsultaConceptos
                        ,reader     :
                        {
                            type  : 'json'
                            ,root : 'loadList'
                        }
                    }
                })
            },
            {
                xtype: 'combobox',
                fieldLabel: 'Estudio',
                name: 'CDEST',
                labelAlign: 'right',
                labelWidth: 70,
                width: 250,
                displayField: 'DSEST',
                valueField: 'CDEST',
                listConfig : {
                	itemTpl : '{CDEST} - {DSEST}'
                },
                forceSelection: true,
                anyMatch      : true,
                queryMode     : 'local',
                allowBlank: false,
                store: Ext.create('Ext.data.Store',{
            		pageSize : 20
                    ,model   : 'modelGridEstudios'
                    ,autoLoad: true
                    ,proxy   :
                    {
                    	type        : 'ajax'
                        ,url        : _UrlConsultaEstudiosMed
                        ,reader     :
                        {
                            type  : 'json'
                            ,root : 'loadList'
                        }
                    } 
                })
           	},{
              	xtype: 'checkbox',
              	fieldLabel: 'El estudio requiere un resultado obligatorio',
              	name : 'SWOBLRES_CHECK',
              	labelWidth: 200,
              	labelAlign: 'right', 
                value: false,
                listeners: {
                	change: function(ck, newValue){
                		if(newValue){
                			panelAgregarEditarConfig.down('hidden[name=SWOBLRES]').setValue('S');
                		}else{
                			panelAgregarEditarConfig.down('hidden[name=SWOBLRES]').setValue('N');
                		}
                	}
                }
            }
            ,{
              	xtype: 'checkbox',
              	fieldLabel: 'El estudio requiere un valor obligatorio',
              	name : 'SWOBLVAL_CHECK',
              	labelWidth: 200,
              	labelAlign: 'right',
                value: false,
                listeners: {
                	change: function(ck, newValue){
                		if(newValue){
                			panelAgregarEditarConfig.down('hidden[name=SWOBLVAL]').setValue('S');
                		}else{
                			panelAgregarEditarConfig.down('hidden[name=SWOBLVAL]').setValue('N');
                		}
                	}
                }
            },{
              	xtype: 'hidden',
                name : 'SWOBLVAL',
                value: 'N'
            },
            {
            	xtype: 'hidden',
                name : 'SWOBLRES',
                value: 'N'
            },
              {
              	xtype: 'hidden',
                  name : 'CDRAMO_ORIG'
              },
              {
              	xtype: 'hidden',
                  name : 'CDTIPSIT_ORIG'
              },
              {
              	xtype: 'hidden',
                  name : 'CDGARANT_ORIG'
              },
              {
              	xtype: 'hidden',
                  name : 'CDCONVAL_ORIG'
              },
              {
              	xtype: 'hidden',
                  name : 'CDCONCEP_ORIG'
              },
              {
              	xtype: 'hidden',
                  name : 'CDEST_ORIG'
              },
              {
              	xtype: 'hidden',
                  name : 'SWOBLVAL_ORIG',
                  value: 'N'
              },
              {
              	xtype: 'hidden',
                  name : 'SWOBLRES_ORIG',
                  value: 'N'
              },
              {
              	xtype: 'hidden',
                  name : 'SWOP',
                  value: 'U'
              }
        ],
        buttonAlign: 'center',
        buttons: [
            {
                text: 'Guardar',
                icon:_CONTEXT+'/resources/fam3icons/icons/disk.png',
                handler: function(btn){
                	var panelGuardar = btn.up('form');
                	
                	if(panelGuardar.getForm().isValid()){
                		
                		panelGuardar.getForm().updateRecord(recordEditar);

                		var datosResultado = {
                       		 'pi_cdramo_ant'  : recordEditar.get('CDRAMO_ORIG'),
                       		 'pi_cdtipsit_ant': recordEditar.get('CDTIPSIT_ORIG'),
                       		 'pi_cdgarant_ant': recordEditar.get('CDGARANT_ORIG'),
                       		 'pi_cdconval_ant': recordEditar.get('CDCONVAL_ORIG'),
                       		 'pi_cdconcep_ant': recordEditar.get('CDCONCEP_ORIG'),
                       		 'pi_cdest_ant'   : recordEditar.get('CDEST_ORIG'),
                       		 'pi_swoblval_ant': recordEditar.get('SWOBLVAL_ORIG'),
                       		 'pi_swoblres_ant': recordEditar.get('SWOBLRES_ORIG'),
                       		 'pi_cdramo'      : recordEditar.get('CDRAMO'),
                       		 'pi_cdtipsit'    : recordEditar.get('CDTIPSIT'),
                       		 'pi_cdgarant'    : recordEditar.get('CDGARANT'),
                       		 'pi_cdconval'    : recordEditar.get('CDCONVAL'),
                       		 'pi_cdconcep'    : recordEditar.get('CDCONCEP'),
                       		 'pi_cdest'       : recordEditar.get('CDEST'),
                       		 'pi_swoblval'    : recordEditar.get('SWOBLVAL'),
                       		 'pi_swoblres'    : recordEditar.get('SWOBLRES'),
                       		 'pi_swop'        : 'U'
                        };
                       	    
                        debug('Configuracion a guardar: ',datosResultado);

                        var maskGuarda = _maskLocal('Guardando...');
                        
                        Ext.Ajax.request({
                            url: _UrlActualizaConfEstudiosMedCob,
                            jsonData: {
                            	'params' : datosResultado
                            },
                            success  : function(response, options){
                           	 maskGuarda.close();
                                var json = Ext.decode(response.responseText);
                                if(json.success){
                                	mensajeCorrecto('Aviso','Se ha guardado correctamente.');
                                	var gridConfigEstCob = btnGrid.up('grid');
                                	var panelBusq = panelPrincipalConfResEstudios.down('#panelBusqueda');
                                	gridConfigEstCob.getStore().load({
       	                				params: panelBusq.getForm().getValues()
       	                			});
                                	
                                	windowConfEstudios.close();
                                	
                                }else{
                                    mensajeError(json.mensaje);
                                }
                            }
                            ,failure  : function(response, options){
                           	 maskGuarda.close();
                                var json = Ext.decode(response.responseText);
                                mensajeError(json.mensaje);
                            }
                        });
                	}else{
                		mensajeWarning('Capture todos los datos requeridos.');
                	}
           			
                }
            },{
				text:'Cancelar',
				icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
				handler:function()
				{
					windowConfEstudios.close();
				}
			}
        ],
        dockedItems: [
            {
                xtype: 'toolbar',
                dock: 'top',
                items: ['->','-',{
                		text: 'Limpiar'
                		,hidden: !esNuevoRegistro
                		,icon:_CONTEXT+'/resources/fam3icons/icons/page_white.png'
                    	,handler: function(btn){
                    		panelAgregarEditarConfig.getForm().reset();
                    	}	
                 }]
            }
        ]
    });
	
	windowConfEstudios = Ext.create('Ext.window.Window',{
		title   : esNuevoRegistro ? 'Agregar Configuraci&oacute;n.':'Editar Configuraci&oacute;n.'
		,modal  : true
		,width  : 820
		,items : [panelAgregarEditarConfig]
	}).show( null , function(){
		
		panelAgregarEditarConfig.loadRecord(recordEditar);
		
		if(!esNuevoRegistro){
			if(!Ext.isEmpty(recordEditar.get('SWOBLVAL')) && recordEditar.get('SWOBLVAL') == 'S'){
				panelAgregarEditarConfig.down('checkbox[name=SWOBLVAL_CHECK]').setValue(true);
			}else{
				panelAgregarEditarConfig.down('checkbox[name=SWOBLVAL_CHECK]').setValue(false);
			}
			
			if(!Ext.isEmpty(recordEditar.get('SWOBLRES')) && recordEditar.get('SWOBLRES') == 'S'){
				panelAgregarEditarConfig.down('checkbox[name=SWOBLRES_CHECK]').setValue(true);
			}else{
				panelAgregarEditarConfig.down('checkbox[name=SWOBLRES_CHECK]').setValue(false);
			}
		}
		
		panelAgregarEditarConfig.down('[name=CDRAMO]').getStore().load({
			callback: function(records, op, success){
        		if(success && !Ext.isEmpty(records) && records.length > 0){
        			
        			debug('<<<<<< Entrando a cargar datos en Edicion >>>>>>');
        			
        			var comboRamo = panelAgregarEditarConfig.down('[name=CDRAMO]');
        			comboRamo.select(recordEditar.get('CDRAMO'));
        			
        			if(!Ext.isEmpty(comboRamo.getValue())){
        			
	        			var comboModalidad = panelAgregarEditarConfig.down('[name=CDTIPSIT]');
	            		comboModalidad.getStore().load({
	            			params:{
	            				'params.idPadre' : comboRamo.getValue()
	            			},
	            			callback: function(records, op, success){
	                    		if(success && !Ext.isEmpty(records) && records.length > 0){
	                    			
	                    			debug('<<<<<< Entrando a cargar datos en Edicion >>>>>>');
	                    			
	                    			var comboModalidad = panelAgregarEditarConfig.down('[name=CDTIPSIT]');
	                    			comboModalidad.select(recordEditar.get('CDTIPSIT'));
	                    			
	                    			var comboGarantia = panelAgregarEditarConfig.down('[name=CDGARANT]');
	                    			comboGarantia.getStore().load({
	                    				params:{
	                        				'params.cdramo'  : comboRamo.getValue(),
	                        				'params.cdtipsit': comboModalidad.getValue()
	                        			},
	                        			callback: function(records, op, success){
	                                		if(success && !Ext.isEmpty(records) && records.length > 0){
	                                			
	                                			debug('<<<<<< Entrando a cargar datos en Edicion >>>>>>');
	                                			
	                                    		var comboRamo      = panelAgregarEditarConfig.down('[name=CDRAMO]');
	                                    		var comboModalidad = panelAgregarEditarConfig.down('[name=CDTIPSIT]');
	                                			var comboGarantia  = panelAgregarEditarConfig.down('[name=CDGARANT]');
	                                			
	                                			comboGarantia.select(recordEditar.get('CDGARANT'));
	                                			
	                                			var comboSubcobertura = panelAgregarEditarConfig.down('[name=CDCONVAL]');
	                                			comboSubcobertura.getStore().load({
	                                				params:{
	                                    				'params.cdramo'   : comboRamo.getValue(),
	                                    				'params.cdtipsit' : comboModalidad.getValue(),
	                                    				'params.cdgarant' : comboGarantia.getValue()
	                                    			},
	                                    			callback: function(records, op, success){
	                                            		if(success && !Ext.isEmpty(records) && records.length > 0){
	                                            			debug('<<<<<< Entrando a cargar datos en Edicion >>>>>>');
	                                            			var comboSubcobertura = panelAgregarEditarConfig.down('[name=CDCONVAL]');
	                                            			comboSubcobertura.select(recordEditar.get('CDCONVAL'));
	                                            		}
	                                            	}
	                                    		});
	                                		}
	                                	}
	                        		});
	                    		}
	                    	}
	            		});
        			}
        		}
        	}
		});
		
		var comboConcepto = panelAgregarEditarConfig.down('[name=CDCONCEP]');
		comboConcepto.getStore().load({
			callback: function(records, op, success){
        		if(!esNuevoRegistro && success && !Ext.isEmpty(records) && records.length > 0){
        			comboConcepto.select(recordEditar.get('CDCONCEP'));
        		}
        	}
		});
		
		var comboEstudio = panelAgregarEditarConfig.down('[name=CDEST]');
		comboEstudio.getStore().load({
			callback: function(records, op, success){
        		if(!esNuevoRegistro && success && !Ext.isEmpty(records) && records.length > 0){
        			comboEstudio.select(recordEditar.get('CDEST'));
        		}
        	}
		});
		
		if(esNuevoRegistro){
			numWindowAgregar++;
			if(numWindowAgregar == 1){
				mensajeInfo('Puede utilizar los valores de la B&uacute;queda principal para cargar autom&aacute;ticamente los valores de Producto, Subramo, Cobertura y Subcobertura en esta ventana.');
			}
		}
		
	} );
	
	centrarVentanaInterna(windowConfEstudios);
	
}

	
function eliminarConfEstudios(recordEliminar,btnGrid){
	
	var ventanaConfEliminar = Ext.Msg.show({
        title: 'Confirmar acci&oacute;n',
        msg: "&iquest;Esta seguro que desea eliminar el estudio ''"+ recordEliminar.get('DSEST') + "'' de la configuraci&oacute;n de "+ recordEliminar.get('DSRAMO') +"?",
        buttons: Ext.Msg.YESNO,
        fn: function(buttonId, text, opt) {
            if(buttonId == 'yes') {
            	var datosResultado = {
                		 'pi_cdramo_ant'  : recordEliminar.get('CDRAMO_ORIG'),
                		 'pi_cdtipsit_ant': recordEliminar.get('CDTIPSIT_ORIG'),
                		 'pi_cdgarant_ant': recordEliminar.get('CDGARANT_ORIG'),
                		 'pi_cdconval_ant': recordEliminar.get('CDCONVAL_ORIG'),
                		 'pi_cdconcep_ant': recordEliminar.get('CDCONCEP_ORIG'),
                		 'pi_cdest_ant'   : recordEliminar.get('CDEST_ORIG'),
                		 'pi_swoblval_ant': recordEliminar.get('SWOBLVAL_ORIG'),
                		 'pi_swoblres_ant': recordEliminar.get('SWOBLRES_ORIG'),
                		 'pi_cdramo'      : recordEliminar.get('CDRAMO'),
                		 'pi_cdtipsit'    : recordEliminar.get('CDTIPSIT'),
                		 'pi_cdgarant'    : recordEliminar.get('CDGARANT'),
                		 'pi_cdconval'    : recordEliminar.get('CDCONVAL'),
                		 'pi_cdconcep'    : recordEliminar.get('CDCONCEP'),
                		 'pi_cdest'       : recordEliminar.get('CDEST'),
                		 'pi_swoblval'    : recordEliminar.get('SWOBLVAL'),
                		 'pi_swoblres'    : recordEliminar.get('SWOBLRES'),
                		 'pi_swop'        : 'D'
                 };
                	    
                 debug('Configuracion a eliminar: ',datosResultado);

                 var maskGuarda = _maskLocal('Eliminando...');
                 
                 Ext.Ajax.request({
                     url: _UrlActualizaConfEstudiosMedCob,
                     jsonData: {
                     	'params' : datosResultado
                     },
                     success  : function(response, options){
                    	 maskGuarda.close();
                         var json = Ext.decode(response.responseText);
                         if(json.success){
                         	mensajeCorrecto('Aviso','Se ha eliminado correctamente.');
                         	var gridConfigEstCob = btnGrid.up('grid');
                         	gridConfigEstCob.getStore().reload();
                         }else{
                             mensajeError(json.mensaje);
                         }
                     }
                     ,failure  : function(response, options){
                    	 maskGuarda.close();
                         var json = Ext.decode(response.responseText);
                         mensajeError(json.mensaje);
                     }
                 });
            }
        },
        //animateTarget: btnGrid,
        icon: Ext.Msg.QUESTION
    });
	
	centrarVentanaInterna(ventanaConfEliminar);
	
}

});
</script>

</head>
<body>
<div id="mainDivConfSucurTrabajo" style="height:1250px;"></div>
</body>
</html>