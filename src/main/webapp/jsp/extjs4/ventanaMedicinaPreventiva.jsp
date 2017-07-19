<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>


<script type="text/javascript">
	
//////////////////////////
////// variables    //////
/*//////////////////////*/

var _Cdunieco  = '<s:property value="smap1.cdunieco" />';
var _Cdramo    = '<s:property value="smap1.cdramo" />';
var _Estado    = '<s:property value="smap1.estado" />';
var _Nmpoliza  = '<s:property value="smap1.nmpoliza" />';
var _Nmsituac  = '<s:property value="smap1.nmsituac" />';
var _Cdperson   = '<s:property value="smap1.cdperson" />';

var _Edad       = '<s:property value="smap1.EDAD" />';
var _Rfc        = '<s:property value="smap1.RFC" />';
var _NombreAseg = '<s:property value="smap1.NOMBRE" />';


var _CONTEXT       = '${ctx}';

var _urlCargaPadecimientosAseg   = '<s:url namespace="/consultasAsegurado"  action="obtienePadecimientosAseg" />';
var _urlCargaHistorialPadeciAseg = '<s:url namespace="/consultasAsegurado"  action="obtieneHistorialPadecimientoAseg" />';

var _URL_CatalogoPadecimientos         = '<s:url namespace="/consultasAsegurado" action="obtieneCatalogoPadecimientos" />';
var _URL_CatalogoEstadosProvMedicos    = '<s:url namespace="/consultasAsegurado" action="obtieneCatalogoEstadosProvMedicos" />';
var _URL_CatalogoMunicipiosProvMedicos = '<s:url namespace="/consultasAsegurado" action="obtieneCatalogoMunicipiosProvMedicos" />';
var _URL_CatalogoEspecialidadesMedicos = '<s:url namespace="/consultasAsegurado" action="obtieneCatalogoEspecialidadesMedicos" />';
var _URL_CatalogoFrecuenciaVisitas     = '<s:url namespace="/consultasAsegurado" action="obtieneCatalogoFrecuenciaVisitas" />';
var _URL_CatalogoPeriodicidadVisitas   = '<s:url namespace="/consultasAsegurado" action="obtieneCatalogoPeriodicidadVisitas" />';

var _urlActualizaPadecimientoAseg        = '<s:url namespace="/consultasAsegurado"  action="actualizaPadecimientoAseg" />';
var _urlActualizaCartaMPPadecimientoAseg = '<s:url namespace="/consultasAsegurado"  action="actualizaCartaMPPadecimientoAseg" />';
var _urlActualizaHistorialPadeciAseg     = '<s:url namespace="/consultasAsegurado"  action="actualizaHistorialPadecimientoAseg" />';

var _urlObtieneCopagoCobMedPrevPol        = '<s:url namespace="/consultasAsegurado"  action="obtieneCopagoCobMedPrevPol" />';

var _UrlConsultaMedicosTratantes        = '<s:url namespace="/consultasAsegurado"  action="obtieneMedicosTratantes" />';

var gridPadecimientosAsegurado;

/*//////////////////////*/
////// variables    //////
//////////////////////////

Ext.onReady(function()
{
    //////////////////////////
    ////// modelos      //////
    /*//////////////////////*/

    Ext.define('modelPadecimientosAsegCartaMP',
			{
				extend : 'Ext.data.Model'
				,fields :
				['CDUNIECO','CDRAMO','ESTADO','NMPOLIZA','NMSITUAC','CDTIPSIT','STATUS','NMSUPLEM',
					'CDPERSON','CDICD','FEGENCART','CDFREC','CDPERIOD','CDPRESTA','DSPAD','DSPLANMED','SWGENCARTA','CDUSUARI','DSICD']
	});
    
    Ext.define('modelHistorialPadecimientoAseg',
			{
				extend : 'Ext.data.Model'
				,fields :
				['CDUNIECO','CDRAMO','ESTADO','NMPOLIZA','NMSITUAC','CDTIPSIT','STATUS','NMSUPLEM',
					'CDPERSON','CDICD','FEREG','PESO','TALLA','CDPRESTA','DSNOMMED','DSAPELLMED1','DSAPELLMED2','COMENTARIOS','CDUSUARI']
	});

    Ext.define('modelMedicosTratantes',
    		{
    	extend : 'Ext.data.Model'
    		,fields :
    			['CDPRESTA','NOMBRE','DIRECCION','NMTELEFO']
    });
    
    /*//////////////////////*/
    ////// modelos      //////
    //////////////////////////
    
    //////////////////////////
    ////// stores       //////
    /*//////////////////////*/

    var padecimientosGridStore = Ext.create('Ext.data.Store',
    {
        model   : 'modelPadecimientosAsegCartaMP'
        ,autoLoad: true
        ,proxy   :
        {
        	type        : 'ajax'
            ,url        : _urlCargaPadecimientosAseg
            ,extraParams : {
            	 'params.pi_cdunieco'    : '',
           		 'params.pi_cdramo'      : '',
           		 'params.pi_estado'      : '',
           		 'params.pi_nmpoliza'    : '',
           		 'params.pi_nmsituac'    : '',
           		 'params.pi_cdperson'    : _Cdperson
            }
            ,reader     :
            {
                type  : 'json'
                ,root : 'loadList'
            }
        },
        listeners:{
        	beforeload: function(){
        		gridPadecimientosAsegurado.setLoading(true);
        	},
        	load: function(){
        		gridPadecimientosAsegurado.setLoading(false);
        	}
        }
    });

    var historialGridStore = Ext.create('Ext.data.Store',
    {
        model   : 'modelHistorialPadecimientoAseg'
        ,autoLoad: true
        ,proxy   :
        {
        	type        : 'ajax'
            ,url        : _urlCargaHistorialPadeciAseg
            ,reader     :
            {
                type  : 'json'
                ,root : 'loadList'
            }
        }
    });
    
    var medicosTratantesStore = Ext.create('Ext.data.Store',{
        model   : 'modelMedicosTratantes'
        ,proxy  :{
        	type        : 'ajax'
            ,url        : _UrlConsultaMedicosTratantes
            ,reader     :
            {
                type  : 'json'
                ,root : 'slist1'
            }
        }
    });
    
    /*//////////////////////*/
    ////// stores       //////
    //////////////////////////
    
    //////////////////////////
    ////// componentes  //////
    /*//////////////////////*/
    
    gridPadecimientosAsegurado = Ext.create('Ext.grid.Panel', {
        title   : 'Padecimientos del Asegurado',
        store   : padecimientosGridStore,
        //width   : 830,
        height  : 400 ,
        autoScroll:true,
        columns: [
            {
                xtype        : 'actioncolumn',
                icon         : _CONTEXT+'/resources/fam3icons/icons/folder_magnify.png',
                tooltip      : 'Detalle de Padecimiento',
                width        : 22,
                menuDisabled : true,
                sortable     : false,
                handler      : function(grid,rowIndex)
                {
                    var record = grid.getStore().getAt(rowIndex);
                    debug("Valor de respuesta de record ==>",record);
                    agregarEditarPadecimientoAseg(record);
                }
            },
            {
                xtype        : 'actioncolumn',
                icon         : _CONTEXT+'/resources/fam3icons/icons/page.png',
                tooltip      : 'Ver Carta de Medicina Preventiva',
                width        : 22,
                menuDisabled : true,
                sortable     : false,
                handler      : function(grid,rowIndex) {
                    var record = grid.getStore().getAt(rowIndex);
                    agregarEditarCartaPadecimientoAseg(record);
                }
            },
            {
                xtype        : 'actioncolumn',
                icon         : _CONTEXT+'/resources/fam3icons/icons/page_paste.png',
                tooltip      : 'Ver historial del padecimiento',
                width        : 22,
                menuDisabled : true,
                sortable     : false,
                handler      : function(gridView, rowIndex, colIndex, item, e, record, row) {

                }
            },
        	{text:'Descripci&oacute;n ICD', dataIndex:'DSICD', flex: 2},
        	{text:'Descripci&oacute;n Padecimiento', dataIndex:'DSPAD', flex: 2}
        ],
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
                        	var nuevoRecConf = new modelPadecimientosAsegCartaMP();
                        	nuevoRecConf.set('CDUNIECO',_Cdunieco);
                        	nuevoRecConf.set('CDRAMO'  ,_Cdramo);
                        	nuevoRecConf.set('ESTADO'  ,_Estado);
                        	nuevoRecConf.set('NMPOLIZA',_Nmpoliza);
                        	nuevoRecConf.set('NMSITUAC',_Nmsituac);
                        	nuevoRecConf.set('CDPERSON',_Cdperson);
                        	
                        	agregarEditarPadecimientoAseg(nuevoRecConf,btn);
                        }
                    }
                ]
            }
        ]
    });
    
    
    function agregarEditarPadecimientoAseg(recordEditar){
    	var ventanaAgregarEditarPad;
    	var esNuevoRegistro = (!Ext.isEmpty(recordEditar) && recordEditar.phantom == false) ? false : true;
    	
    	var panelGuardar = Ext.create('Ext.form.Panel',{
    		title: 'Informaci&oacute;n del Padecimiento.',
            defaults : {
    			style : 'margin : 10px;'
    		},
            items: [{
                xtype: 'combobox',
                fieldLabel: 'Enfermedad ICD',
                name: 'CDICD',
                width: 400,
                displayField: 'value',
                valueField: 'key',
                forceSelection: true,
                anyMatch      : true,
                queryMode     : 'local',
                allowBlank    : false,
                readOnly      : !esNuevoRegistro,
                store       : Ext.create('Ext.data.Store', {
                    model : 'Generic',
                    autoLoad : true,
                    proxy : {
                        type : 'ajax',
                        url : _URL_CatalogoPadecimientos,
                        reader : {
                            type : 'json',
                            root : 'listaCatalogo'
                        }
                    },
                    listeners: {
                    	load: function(){
                    		if(!esNuevoRegistro){
                    			panelGuardar.loadRecord(recordEditar);
                    		}
                    		
                    		if(ventanaAgregarEditarPad){
                    			ventanaAgregarEditarPad.setLoading(false);
                    		}
                    	}
                    }
                })
            },
            {
                xtype: 'textareafield',
                fieldLabel: 'Descripci&oacute;n del padecimiento',
                name: 'DSPAD',
                height: 72,
                width: 500,
                allowBlank: false,
                maxLength : 1500
                
            },
            {
                xtype: 'textareafield',
                fieldLabel: 'Plan M&eacute;dico',
                name: 'DSPLANMED',
                allowBlank    : false,
                maxLength : 1500,
                width: 400
            }],
            buttonAlign: 'center',
            buttons:[
            	{
                    text: 'Guardar',
                    icon:_CONTEXT+'/resources/fam3icons/icons/disk.png',
                    handler: function(btn){
                    	var panelGuardar = btn.up('form');
                    	
                    	if(panelGuardar.getForm().isValid()){
                    		
                    		var datosForma = panelGuardar.getValues();

                    		var datosResultado = {
                           		 'params.pi_cdunieco'    : recordEditar.get('CDUNIECO'),
                           		 'params.pi_cdramo'      : recordEditar.get('CDRAMO'),
                           		 'params.pi_estado'      : recordEditar.get('ESTADO'),
                           		 'params.pi_nmpoliza'    : recordEditar.get('NMPOLIZA'),
                           		 'params.pi_nmsituac'    : recordEditar.get('NMSITUAC'),
                           		 'params.pi_cdperson'    : recordEditar.get('CDPERSON'),
                           		 'params.pi_cdicd'       : datosForma.CDICD,
                           		 'params.pi_cdfrec'      : '',
                           		 'params.pi_cdperiod'    : '',
                           		 'params.pi_cdpresta'    : '',
                           		 'params.pi_dspad'       : datosForma.DSPAD,
                           		 'params.pi_dsplanmed'   : datosForma.DSPLANMED,
                           		 'params.pi_swgencarta'  : '',
                           		 'params.pi_swop'        : 'U'
                            };
                           	    
                            debug('Padecimiento a guardar: ',datosResultado);
							
                            var maskGuarda = _maskLocal('Guardando...');
                            
                            Ext.Ajax.request({
                                url: _urlActualizaPadecimientoAseg,
                                params: datosResultado,
                                success  : function(response, options){
                               	 maskGuarda.close();
                                    var json = Ext.decode(response.responseText);
                                    if(json.success){
                                    	
                                    	padecimientosGridStore.reload();
                                    	ventanaAgregarEditarPad.close();
                                    	mensajeCorrecto('Aviso','Se ha guardado correctamente.');
                                    	
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
    					ventanaAgregarEditarPad.close();
    				}
    			}
            ]
        });
    		
    	ventanaAgregarEditarPad = Ext.create('Ext.window.Window',{
    		title   : esNuevoRegistro ? 'Agregar Padecimiento / Enfermedad ICD.':'Editar Padecimiento / Enfermedad ICD.'
    		,modal  : true
    		,width  : 790
    		,items : [{
                xtype: 'form',
                defaults : {
        			style : 'margin : 6px 10px 6px 10px;'
        		},
                items: [
                	Ext.create('Ext.form.FieldSet',{
                		title: '<span style="font:bold 12px Calibri;">Datos del asegurado.</span>',
                        defaults : {
                			style : 'margin : 5px 20px;',
                			labelWidth: 60
                		},
                        layout: {
                            type: 'column',
                            columns: 3 
                        },
                        items: [{
	                            xtype: 'textfield',
	                            fieldLabel: 'C&oacute;digo',
	                            width: 200,
	                            readOnly : true,
	                            value: _Cdperson
	                        },
	                        {
	                            xtype: 'textfield',
	                            fieldLabel: 'Edad',
	                            width: 150,
	                            readOnly : true,
	                            value: _Edad
	                            
	                        },
	                        {
	                            xtype: 'textfield',
	                            fieldLabel: 'Identidad',
	                            width: 250,
	                            readOnly : true,
	                            value: _Rfc
	                        },
	                        {
	                            xtype: 'textfield',
	                            fieldLabel: 'Nombre',
	                            width: 390,
	                            readOnly : true,
	                            value: _NombreAseg
	                        }
                    	]
                    }),
                    panelGuardar
                ]
            }]
    	}).show();
    	
    	centrarVentanaInterna(ventanaAgregarEditarPad);
    	
    	if(ventanaAgregarEditarPad){
			ventanaAgregarEditarPad.setLoading(true);
		}
    }

    function agregarEditarCartaPadecimientoAseg(recordEditar){
    	var ventanaCartaMedPrevPad;
    	
//    	medicosTratantesStore.load({
//    		params:{
//    			'params.pi_cdedo':'',
//    			'params.pi_cdmunici':'',
//    			'params.pi_cdespeci':''
//    		}
//    	});
    	
    	var fieldsetMedicos = Ext.create('Ext.form.FieldSet',{
    		title: '<span style="font:bold 12px Calibri;">Seleccione un m&eacutedico tratante.</span>',
    		defaults : {
    			style : 'margin : 3px 2px 2px 2px;'
    		},
    		hidden: true,
            items: [
            		{
           			xtype: 'combobox',
                    fieldLabel: 'Estado',
                    name: 'EDO_DOM',
                    width: 300,
                    displayField: 'value',
                    valueField: 'key',
                    forceSelection: true,
                    anyMatch      : true,
                    queryMode     : 'local',
                    allowBlank    : false,
                    store       : Ext.create('Ext.data.Store', {
                        model : 'Generic',
                        autoLoad : true,
                        proxy : {
                            type : 'ajax',
                            url : _URL_CatalogoEstadosProvMedicos,
                            reader : {
                                type : 'json',
                                root : 'listaCatalogo'
                            }
                        }
                    })
                    },
                    {
                	xtype: 'combobox',
                    fieldLabel: 'Municipio',
                    name: 'MUNICI_DOM',
                    width: 300,
                    displayField: 'value',
                    valueField: 'key',
                    forceSelection: true,
                    anyMatch      : true,
                    queryMode     : 'local',
                    allowBlank    : false,
                    store       : Ext.create('Ext.data.Store', {
                        model : 'Generic',
                        autoLoad : true,
                        proxy : {
                            type : 'ajax',
                            url : _URL_CatalogoMunicipiosProvMedicos,
                            reader : {
                                type : 'json',
                                root : 'listaCatalogo'
                            }
                        }
                    })
                    },
                    {
            		xtype: 'combobox',
                    fieldLabel: 'Especialidad',
                    name: 'ESPECIALIDAD',
                    width: 300,
                    displayField: 'value',
                    valueField: 'key',
                    forceSelection: true,
                    anyMatch      : true,
                    queryMode     : 'local',
                    allowBlank    : false,
                    store       : Ext.create('Ext.data.Store', {
                        model : 'Generic',
                        autoLoad : true,
                        proxy : {
                            type : 'ajax',
                            url : _URL_CatalogoEspecialidadesMedicos,
                            reader : {
                                type : 'json',
                                root : 'listaCatalogo'
                            }
                        }
                    })
                    },{
                        xtype: 'button',
                        text: 'Buscar',
                        icon:_CONTEXT+'/resources/fam3icons/icons/zoom.png',
                        handler: function(btn){
                        }
                    },
            	Ext.create('Ext.grid.Panel', {
                border: false,
                autoScroll: false,
                height : 120,
                disableSelection: true,
                viewConfig: {
                    stripeRows: false,
                    trackOver : false
                },
            	store: medicosTratantesStore,
                columns: [
                    { text: 'Nombre M&eacutedico', dataIndex: 'NOMBRE' , flex: 2 },
                    { text: 'Direcci&oacute;n M&eacutedico', dataIndex: 'DIRECCION', flex: 3 },
                    { text: 'Tel&eacute;fono', dataIndex: 'NMTELEFO', flex: 1 }
                ]
            })]
        });
    	
    	var panelGuardar = Ext.create('Ext.form.Panel',{
    		title: 'Informaci&oacute;n para Carta de Medicina Preventiva.',
            defaults : {
            	style : 'margin : 15px 10px 15px;'
    		},
    		layout: {
                type: 'column',
                columns: 3 
            },
            items: [{
                xtype: 'combobox',
                width: 220,
                labelWidth: 120,
                fieldLabel: 'Frecuencia de Visitas',
                name: 'CDFREC',
                displayField: 'value',
                valueField: 'key',
                forceSelection: true,
                anyMatch      : true,
                queryMode     : 'local',
                allowBlank    : false,
                store       : Ext.create('Ext.data.Store', {
                    model : 'Generic',
                    autoLoad : false,
                    proxy : {
                        type : 'ajax',
                        url : _URL_CatalogoFrecuenciaVisitas,
                        reader : {
                            type : 'json',
                            root : 'listaCatalogo'
                        }
                    }
                })
            },
            {
                xtype: 'combobox',
                fieldLabel: 'Periodicidad',
                labelWidth: 80,
                name: 'CDPERIOD',
                displayField: 'value',
                valueField: 'key',
                forceSelection: true,
                anyMatch      : true,
                queryMode     : 'local',
                allowBlank    : false,
                store       : Ext.create('Ext.data.Store', {
                    model : 'Generic',
                    autoLoad : false,
                    proxy : {
                        type : 'ajax',
                        url : _URL_CatalogoPeriodicidadVisitas,
                        reader : {
                            type : 'json',
                            root : 'listaCatalogo'
                        }
                    }
                })
            },
            {
                xtype: 'textfield',
                fieldLabel: 'Copago',
                labelWidth: 80,
                name: 'COPAGO',
                readOnly: true
                
            }]
        });
    		
    	ventanaCartaMedPrevPad = Ext.create('Ext.window.Window',{
    		title   : 'Carta de Medicina Preventiva'
    		,modal  : true
    		,width  : 790
    		,items : [{
                xtype: 'form',
                defaults : {
        			style : 'margin : 6px 10px 6px 10px;'
        		},
                items: [
                	Ext.create('Ext.form.FieldSet',{
                		title: '<span style="font:bold 12px Calibri;">Datos del asegurado.</span>',
                        defaults : {
                			style : 'margin : 5px 20px;',
                			labelWidth: 60
                		},
                        layout: {
                            type: 'column',
                            columns: 3 
                        },
                        items: [{
	                            xtype: 'textfield',
	                            fieldLabel: 'C&oacute;digo',
	                            width: 200,
	                            readOnly : true,
	                            value: _Cdperson
	                        },
	                        {
	                            xtype: 'textfield',
	                            fieldLabel: 'Edad',
	                            width: 150,
	                            readOnly : true,
	                            value: _Edad
	                            
	                        },
	                        {
	                            xtype: 'textfield',
	                            fieldLabel: 'Identidad',
	                            width: 250,
	                            readOnly : true,
	                            value: _Rfc
	                        },
	                        {
	                            xtype: 'textfield',
	                            fieldLabel: 'Nombre',
	                            width: 390,
	                            readOnly : true,
	                            value: _NombreAseg
	                        }
                    	]
                    }),
                    panelGuardar,
                    fieldsetMedicos
                ],
                buttonAlign: 'center',
                buttons:[
                	{
                        text: 'Guardar Datos',
                        icon:_CONTEXT+'/resources/fam3icons/icons/disk.png',
                        handler: function(btn){
                        	if(panelGuardar.getForm().isValid()){
                        		
                        		var datosForma = panelGuardar.getValues();

                        		var datosResultado = {
                               		 'params.pi_cdunieco'    : recordEditar.get('CDUNIECO'),
                               		 'params.pi_cdramo'      : recordEditar.get('CDRAMO'),
                               		 'params.pi_estado'      : recordEditar.get('ESTADO'),
                               		 'params.pi_nmpoliza'    : recordEditar.get('NMPOLIZA'),
                               		 'params.pi_nmsituac'    : recordEditar.get('NMSITUAC'),
                               		 'params.pi_cdperson'    : recordEditar.get('CDPERSON'),
                               		 'params.pi_cdicd'       : datosForma.CDICD,
                               		 'params.pi_cdfrec'      : '',
                               		 'params.pi_cdperiod'    : '',
                               		 'params.pi_cdpresta'    : '',
                               		 'params.pi_dspad'       : datosForma.DSPAD,
                               		 'params.pi_dsplanmed'   : datosForma.DSPLANMED,
                               		 'params.pi_swgencarta'  : '',
                               		 'params.pi_swop'        : 'U'
                                };
                               	    
                                debug('Padecimiento a guardar: ',datosResultado);
    							
                                var maskGuarda = _maskLocal('Guardando...');
                                
                                Ext.Ajax.request({
                                    url: _urlActualizaPadecimientoAseg,
                                    params: datosResultado,
                                    success  : function(response, options){
                                   	 maskGuarda.close();
                                        var json = Ext.decode(response.responseText);
                                        if(json.success){
                                        	
                                        	padecimientosGridStore.reload();
                                        	ventanaCartaMedPrevPad.close();
                                        	mensajeCorrecto('Aviso','Se ha guardado correctamente.');
                                        	
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
        					ventanaCartaMedPrevPad.close();
        				}
        			}
                ]
            }],
            dockedItems: [
                {
                    xtype: 'toolbar',
                    dock: 'bottom',
                    items: ['->',
                        {
                            xtype: 'button',
                            text: 'Vista Previa de Carta'
                        },
                        {
                            xtype: 'button',
                            text: 'Confirmar/Generar Carta'
                        }
                    ]
                }
            ]
    	}).show();
    	
    	centrarVentanaInterna(ventanaCartaMedPrevPad);
    	
//    	if(ventanaCartaMedPrevPad){
//			ventanaCartaMedPrevPad.setLoading(true);
//		}
    	
    	Ext.Ajax.request({
            url: _urlObtieneCopagoCobMedPrevPol,
            params: {
            	 'params.pi_cdunieco'    : recordEditar.get('CDUNIECO'),
          		 'params.pi_cdramo'      : recordEditar.get('CDRAMO'),
          		 'params.pi_estado'      : recordEditar.get('ESTADO'),
          		 'params.pi_nmpoliza'    : recordEditar.get('NMPOLIZA'),
          		 'params.pi_nmsituac'       : recordEditar.get('NMSITUAC'),
            },
            success  : function(response, options){
                var json = Ext.decode(response.responseText);
                if(json.success){
                	
                	var copago  = json.params.COPAGO;
                	var formato = json.params.FORMATO;
                	
                	if(!Ext.isEmpty(formato)){
                		if(formato == 'P' ){
                			copago = copago+' %';
                		}else if(formato == 'N' ){
                			copago = '$ '+copago;
                		}
                	}
                	
                	var campoCopago = panelGuardar.down('[name=COPAGO]');
                	campoCopago.setValue(copago);
                }else{
                	mensajeInfo('No se pudo obtener el Copago para este asegurado');
                }
            }
            ,failure  : function(response, options){
            	mensajeInfo('No se pudo obtener el Copago para este asegurado');
            }
        });
    }
    
    Ext.define('PanelPrincipalMedPRev',
    {
        extend         : 'Ext.panel.Panel',
        items: [
            {
                xtype: 'panel',
                height: 835,
                //title: 'Medicina Preventiva',
                items: [
                	gridPadecimientosAsegurado,
                    {
                        xtype: 'panel',
                        hidden: true,
                        buttons: [
                            {
                                text: 'Asignar m&eacute;dicoseleccionado'
                            }
                        ],
                        height: 357,
                        layout: 'anchor',
                        bodyPadding: 10,
                        title: 'Seleccionar M&eacute;dico',
                        items: [
                            {
                                xtype: 'form',
                                buttons: [
                                    {
                                        text: 'Buscar',
                                        buttonAlign: 'center'
                                    }
                                ],
                                height: 156,
                                layout: 'column',
                                bodyPadding: 10,
                                title: 'B&uacute;squeda',
                                items: [
                                    {
                                        xtype: 'combobox',
                                        fieldLabel: 'Estado'
                                    },
                                    {
                                        xtype: 'combobox',
                                        fieldLabel: 'Municipio'
                                    },
                                    {
                                        xtype: 'combobox',
                                        fieldLabel: 'Especialidad'
                                    }
                                ]
                            },
                            {
                                xtype: 'gridpanel',
                                title: 'M&eacute;dicos',
                                columns: [
                                    {
                                        xtype: 'gridcolumn',
                                        dataIndex: 'string',
                                        text: 'Nombre'
                                    },
                                    {
                                        xtype: 'gridcolumn',
                                        dataIndex: 'string',
                                        text: 'Direcci&oacute;n'
                                    },
                                    {
                                        xtype: 'gridcolumn',
                                        dataIndex: 'string',
                                        text: 'Tel&eacute;fono'
                                    }
                                ]
                            }
                        ]
                    },
                    {
                        xtype: 'panel',
                        hidden: true,
                        buttons: [
                            {
                                text: 'Aceptar'
                            }
                        ],
                        height: 515,
                        layout: 'anchor',
                        bodyPadding: 10,
                        title: 'Historial Medicina Preventiva',
                        items: [
                            {
                                xtype: 'form',
                                buttons: [
                                    {
                                        text: 'Agregar'
                                    }
                                ],
                                height: 253,
                                layout: 'column',
                                bodyPadding: 10,
                                title: 'M&eacute;dico tratante',
                                items: [
                                    {
                                        xtype: 'textfield',
                                        fieldLabel: 'Nombre'
                                    },
                                    {
                                        xtype: 'textfield',
                                        fieldLabel: 'Apellido Paterno'
                                    },
                                    {
                                        xtype: 'textfield',
                                        fieldLabel: 'Apellido Materno'
                                    },
                                    {
                                        xtype: 'datefield',
                                        fieldLabel: 'Fecha'
                                    },
                                    {
                                        xtype: 'numberfield',
                                        fieldLabel: 'Peso'
                                    },
                                    {
                                        xtype: 'numberfield',
                                        fieldLabel: 'Talla'
                                    },
                                    {
                                        xtype: 'textareafield',
                                        height: 91,
                                        width: 293,
                                        fieldLabel: 'Comentarios'
                                    }
                                ]
                            },
                            {
                                xtype: 'gridpanel',
                                title: 'M&eacute;dicos',
                                columns: [
                                    {
                                        xtype: 'gridcolumn',
                                        dataIndex: 'string',
                                        text: 'Nombre'
                                    },
                                    {
                                        xtype: 'gridcolumn',
                                        dataIndex: 'string',
                                        text: 'Direcci&oacute;n'
                                    },
                                    {
                                        xtype: 'gridcolumn',
                                        dataIndex: 'string',
                                        text: 'Tel&eacute;fono'
                                    }
                                ]
                            }
                        ]
                    }
                ]
            }
        ]
        
    });
    /*//////////////////////*/
    ////// componentes  //////
    //////////////////////////
    
    //////////////////////////
    ////// contenido    //////
    /*//////////////////////*/
    var panelPrincipal =new PanelPrincipalMedPRev();
    panelPrincipal.render('maindiv_medprev_<s:property value="smap1.random" />');
    //Ext.getCmp('venDocMenuSupBotGenConrec').hide();
    /*//////////////////////*/
    ////// contenido    //////
    //////////////////////////
    
});
</script>
<div id="maindiv_medprev_<s:property value="smap1.random" />" style="height:1000px;border:1px solid #999999;"></div>