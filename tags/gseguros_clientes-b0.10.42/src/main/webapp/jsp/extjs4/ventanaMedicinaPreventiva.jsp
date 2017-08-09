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
var _Cdperson  = '<s:property value="smap1.cdperson" />';

var _Edad       = '<s:property value="smap1.EDAD" />';
var _Rfc        = '<s:property value="smap1.RFC" />';
var _NombreAseg = '<s:property value="smap1.NOMBRE" />';

var tieneCobMedicinaPrev = '<s:property value="smap1.COBMEDPREV" />';


var _CONTEXT       = '${ctx}';

var _urlCargaPadecimientosAseg         = '<s:url namespace="/consultasAsegurado"  action="obtienePadecimientosAseg" />';
var _urlCargaHistorialPadeciAseg       = '<s:url namespace="/consultasAsegurado"  action="obtieneHistorialPadecimientoAseg" />';

var _URL_CatalogoPadecimientos         = '<s:url namespace="/consultasAsegurado" action="obtieneCatalogoPadecimientos" />';
var _URL_CatalogoEstadosProvMedicos    = '<s:url namespace="/consultasAsegurado" action="obtieneCatalogoEstadosProvMedicos" />';
var _URL_CatalogoMunicipiosProvMedicos = '<s:url namespace="/consultasAsegurado" action="obtieneCatalogoMunicipiosProvMedicos" />';
var _URL_CatalogoEspecialidadesMedicos = '<s:url namespace="/consultasAsegurado" action="obtieneCatalogoEspecialidadesMedicos" />';
var _URL_CatalogoFrecuenciaVisitas     = '<s:url namespace="/consultasAsegurado" action="obtieneCatalogoFrecuenciaVisitas" />';
var _URL_CatalogoPeriodicidadVisitas   = '<s:url namespace="/consultasAsegurado" action="obtieneCatalogoPeriodicidadVisitas" />';

var _urlActualizaPadecimientoAseg        = '<s:url namespace="/consultasAsegurado"  action="actualizaPadecimientoAseg" />';
var _urlActualizaCartaMPPadecimientoAseg = '<s:url namespace="/consultasAsegurado"  action="actualizaCartaMPPadecimientoAseg" />';
var _urlActualizaHistorialPadeciAseg     = '<s:url namespace="/consultasAsegurado"  action="actualizaHistorialPadecimientoAseg" />';

var _urlObtieneCopagoCobMedPrevPol       = '<s:url namespace="/consultasAsegurado"  action="obtieneCopagoCobMedPrevPol" />';
var _UrlConsultaMedicosTratantes         = '<s:url namespace="/consultasAsegurado"  action="obtieneCatDireccionProvMedPorEspecialidad" />';
var _urlGeneraCartaMedicinaPreventiva         = '<s:url namespace="/consultasAsegurado"  action="generaCartaMedicinaPreventiva" />';

var _UrlSubirArchivoHistPad    = '<s:url namespace="/documentos" action="subirArchivoHistorialMedicinaPrev" />';

var gridPadecimientosAsegurado;

var _urlRutaReports                   = '<s:property value="rutaServidorReports" />';
var _reportsServerUser                = '<s:property value="passServidorReports" />';

var _reporteCartaMedicinaPreventiva   = '<s:text name="rdf.medicinaprev.impresion.cartaMedPrev" />';
var panDocUrlViewDoc                  = '<s:url namespace ="/documentos" action="descargaDocInline" />';
var _urlViewDocHist     = '<s:url namespace ="/documentos" action="descargaDocInline" />';
var _urlDownloadDocHist = '<s:url namespace ="/documentos" action="descargaDoc"       />';
var _urlVerificaExisteDoc = '<s:url namespace ="/documentos" action="verficiaExiteDocumentoExt"       />';

var _RUTA_DOCUMENTOS_PERSONA = '<s:text name="ruta.documentos.persona" />';

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
					'CDPERSON','CDICD','FEGENCART','CDFREC','CDPERIOD','CDPRESTA','DSPRESTA','DSPAD',
					'DSPLANMED','SWGENCARTA','CDUSUARI','DSICD','COPAGO','SWFORMAT','NOMBRE','APELL1','APELL2']
	});
    
    Ext.define('modelHistorialPadecimientoAseg',
			{
				extend : 'Ext.data.Model'
				,fields :
					['NMORDTRA','CDUNIECO','CDRAMO','ESTADO','NMPOLIZA','NMSITUAC','CDTIPSIT','STATUS','NMSUPLEM',
						'CDPERSON','CDICD','DSICD','FEREG','PESO','TALLA','CDPRESTA','DSNOMMED',
						'DSAPELLMED1','DSAPELLMED2','COMENTARIOS','CDUSUARI','DSESPEC','NOMBRE_COMPLETO']
	});

    Ext.define('modelMedicosTratantes',
    		{
    	extend : 'Ext.data.Model'
    		,fields :
    			['CDPRESTA','NOMBRE','DIRECCION','NMTELEFO','ESPECIALIDAD']
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
        	load: function(str,records,success){
        		gridPadecimientosAsegurado.setLoading(false);
        		
        		if(success && records.length <= 0){
        			mensajeInfo('No hay padecimientos registrados para este asegurado');
        		}else if(!success){
        			mensajeError('Error al cargar los padecimientos de este asegurado');
        		}
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
                ,root : 'loadList'
            }
        },
        listeners: {
        	load: function(str,records,success){
        		if(success && records.length <= 0){
        			mensajeInfo('No se encontraron m&eacute;dicos para los criterios seleccionados.');
        		}else if(!success){
        			mensajeError('Error al cargar la consulta de m&eacute;dicos.');
        		}
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
        title   : 'Padecimientos del Asegurado: '+_NombreAseg,
        store   : padecimientosGridStore,
        //width   : 830,
        height  : 400 ,
        autoScroll:true,
        columns: [
            {
                xtype        : 'actioncolumn',
                icon         : _CONTEXT+'/resources/fam3icons/icons/folder_magnify.png',
                tooltip      : 'Detalle del Padecimiento',
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
                tooltip      : 'Carta de Medicina Preventiva',
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
                tooltip      : 'Historial del padecimiento',
                width        : 22,
                menuDisabled : true,
                sortable     : false,
                handler      : function(grid,rowIndex) {
                	var record = grid.getStore().getAt(rowIndex);
                    historialPadecimientoAseg(record);
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
                        	
                        	if(!Ext.isEmpty(tieneCobMedicinaPrev) && tieneCobMedicinaPrev == 'S'){
                        		agregarEditarPadecimientoAseg(nuevoRecConf,btn);
                        	}else{
                        		mensajeWarning('Este asegurado no tiene la cobertura de Medicina Preventiva en la p&oacute;liza actual.'
                        							+'<br/>S&oacute;lo puede dar de alta un padecimiento desde una p&oacute;liza que tenga esta cobertura.');
                        	}
                        	
                        }
                    }
                ]
            }
        ]
    });
    
    
    function eliminarPadecimientoAseg(recordEditar, ventanaAgregarEditarPad){
    	var datosResultado = {
          		 'params.pi_cdunieco'    : recordEditar.get('CDUNIECO'),
          		 'params.pi_cdramo'      : recordEditar.get('CDRAMO'),
          		 'params.pi_estado'      : recordEditar.get('ESTADO'),
          		 'params.pi_nmpoliza'    : recordEditar.get('NMPOLIZA'),
          		 'params.pi_nmsituac'    : recordEditar.get('NMSITUAC'),
          		 'params.pi_cdperson'    : recordEditar.get('CDPERSON'),
          		 'params.pi_cdicd'       : recordEditar.get('CDICD'),
          		 'params.pi_cdfrec'      : '',
          		 'params.pi_cdperiod'    : '',
          		 'params.pi_copago'      : '',
          		 'params.pi_swformat'    : '',
          		 'params.pi_cdpresta'    : '',
          		 'params.pi_dspad'       : '',
          		 'params.pi_dsplanmed'   : '',
          		 'params.pi_swgencarta'  : '',
          		 'params.pi_swop'        : 'D'
           };
          	    
           debug('Padecimiento a borrar: ',datosResultado);
			
           var maskGuarda = _maskLocal('Eliminando...');
           
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
                       mensajeError('Este padecimiento no puede ser borrado ya que tiene asociado un historial de tratamiento.');
                   }
               }
               ,failure  : function(response, options){
              	 maskGuarda.close();
                   var json = Ext.decode(response.responseText);
                   mensajeError(json.mensaje);
               }
           });
    }
    
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
                }),
                listeners: {
                	select: function(cmb,records){
                		if(esNuevoRegistro){
                			var recordSel = records[0];
                    		padecimientosGridStore.each(function(recordPadIt){
                    			if(recordPadIt.get('CDICD') == recordSel.get('key')){
                    				mensajeInfo('Este padecimiento ya se encuentra registrado para este asegurado, seleccione otro padecimiento.');
                    				cmb.clearValue();
                    				return false;
                    			}
                    		});
                		}
                	}
                }
            },
            {
                xtype: 'textareafield',
                fieldLabel: 'Descripci&oacute;n del padecimiento',
                name: 'DSPAD',
                height: 72,
                width: 500,
                allowBlank: false,
                maxLength : 2000
                
            },
            {
                xtype: 'textareafield',
                fieldLabel: 'Plan M&eacute;dico',
                name: 'DSPLANMED',
                allowBlank    : false,
                maxLength : 2000,
                width: 400
            }],
            buttonAlign: 'center',
            buttons:[
    			{
    				text:'Eliminar este Padecimiento',
    				icon:_CONTEXT+'/resources/fam3icons/icons/report_delete.png',
    				hidden: esNuevoRegistro,
    				handler:function(btn)
    				{
    					Ext.Msg.show({
        		            title: 'Aviso',
        		            msg: '&iquest;Esta seguro que desea eliminar este padecimiento del asegurado?',
        		            buttons: Ext.Msg.YESNO,
        		            fn: function(buttonId, text, opt) {
        		            	if(buttonId == 'yes') {
        		            		if(recordEditar.get('SWGENCARTA') == 'S'){
        		            			Ext.Msg.show({
        		        		            title: 'Aviso',
        		        		            msg: 'Ya existe una o mas cartas de Medicina Preventiva para el padecimiento de este asegurado.<br/>&iquest;Desea eliminar este padecimiento?',
        		        		            buttons: Ext.Msg.YESNO,
        		        		            fn: function(buttonId, text, opt) {
        		        		            	if(buttonId == 'yes') {
        		        		            		eliminarPadecimientoAseg(recordEditar, ventanaAgregarEditarPad);
        		        		            	}
        		                			},
        		        		            animateTarget: btn,
        		        		            icon: Ext.Msg.WARNING
        		    					});
        		            		}else{
        		            			eliminarPadecimientoAseg(recordEditar, ventanaAgregarEditarPad);
        		            		}
        		            	}
                			},
        		            animateTarget: btn,
        		            icon: Ext.Msg.QUESTION
    					});
    				}
    			},'->',
    			{
                    text: 'Guardar Datos del Padecimiento',
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
                           		 'params.pi_copago'      : '',
                           		 'params.pi_swformat'    : '',
                           		 'params.pi_cdpresta'    : '',
                           		 'params.pi_dspad'       : datosForma.DSPAD,
                           		 'params.pi_dsplanmed'   : datosForma.DSPLANMED,
                           		 'params.pi_swgencarta'  : '',
                           		 'params.pi_swop'        : 'U'
                            };
                           	    
                            debug('Padecimiento a guardar: ',datosResultado);
                            
                    		if(!esNuevoRegistro && recordEditar.get('SWGENCARTA') == 'S'){
                    			Ext.Msg.show({
		        		            title: 'Aviso',
		        		            msg: 'Ya existe una o mas cartas de Medicina Preventiva para el padecimiento de este asegurado.<br/>&iquest;Desea cambiar la informaci&oacute;n del padecimiento?',
		        		            buttons: Ext.Msg.YESNO,
		        		            fn: function(buttonId, text, opt) {
		        		            	if(buttonId == 'yes') {
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
		        		            	}
		                			},
		        		            animateTarget: btn,
		        		            icon: Ext.Msg.WARNING
		    					});
                    		}else{
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
                    		}
                    		
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
    	var panelGuardar;
    	
    	var SinDatosCartaMedPrev = (Ext.isEmpty(recordEditar.get('CDPRESTA'))&& Ext.isEmpty(recordEditar.get('CDFREC'))&& Ext.isEmpty(recordEditar.get('CDPERIOD')));
    		
    	var panelBuscarMedicos = Ext.create('Ext.form.Panel',{
    		border:false,
            defaults : {
            	style : 'margin : 10px 10px 4px;'
    		},
    		layout: {
                type: 'column',
                columns: 3 
            },
            items: [{
       			xtype: 'combobox',
                fieldLabel: 'Estado',
                name: 'EDO_DOM',
                width: 210,
                labelWidth: 50,
                displayField: 'value',
                valueField: 'key',
                forceSelection: true,
                anyMatch      : true,
                queryMode     : 'local',
                allowBlank    : true,
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
                }),
                listeners: {
                	select: function(cmb, records){
                		var comboMunici = cmb.up('form').down('[name=MUNICI_DOM]');
                		comboMunici.getStore().load({
                			params:{
                				'params.pi_cdedo' : records[0].get('key')
                			}
                		});
                	}
                }
                },
                {
            	xtype: 'combobox',
                fieldLabel: 'Municipio',
                name: 'MUNICI_DOM',
                width: 210,
                labelWidth: 65,
                displayField: 'value',
                valueField: 'key',
                forceSelection: true,
                anyMatch      : true,
                queryMode     : 'local',
                allowBlank    : true,
                store       : Ext.create('Ext.data.Store', {
                    model : 'Generic',
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
                width: 250,
                labelWidth: 75,
                displayField: 'value',
                valueField: 'key',
                forceSelection: true,
                anyMatch      : true,
                queryMode     : 'local',
                allowBlank    : true,
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
                })}],
                buttonAlign: 'center',
            	buttons:[{
	                xtype: 'button',
	                text: 'Buscar',
	                icon:_CONTEXT+'/resources/fam3icons/icons/zoom.png',
	                handler: function(btn){
	                	
	                    	medicosTratantesStore.load({
		                		params:{
		                			'params.pi_cdedo'   : panelBuscarMedicos.down('[name=EDO_DOM]').getValue(),
		                			'params.pi_cdmunici': panelBuscarMedicos.down('[name=MUNICI_DOM]').getValue(),
		                			'params.pi_cdespeci': panelBuscarMedicos.down('[name=ESPECIALIDAD]').getValue()
		                		}
		                	});
	                	
	                }
                },{
                	text: 'Limpiar'
                		,icon:_CONTEXT+'/resources/fam3icons/icons/arrow_refresh.png'
	                	,handler: function(btn){
	                			var panelBusq = btn.up('form');
	                			panelBusq.getForm().reset();
	                			medicosTratantesStore.removeAll();
	                			panelBuscarMedicos.down('[name=MUNICI_DOM]').getStore().removeAll();
	                	}	
	             }]
        });
    	
    	var fieldsetMedicos = Ext.create('Ext.form.FieldSet',{
    		title: '<span style="font:bold 12px Calibri;">B&uacute;squeda y selecci&oacute;n de m&eacutedico tratante.</span>',
    		defaults : {
    			style : 'margin : 3px 2px 2px 2px;'
    		},
            items: [
            	panelBuscarMedicos,
            	Ext.create('Ext.grid.Panel', {
                border: false,
                height : 120,
                viewConfig: {
                    stripeRows: false,
                    trackOver : true
                },
            	store: medicosTratantesStore,
                columns: [
                    { text: 'Nombre M&eacutedico', dataIndex: 'NOMBRE' , flex: 2 },
                    { text: 'Direcci&oacute;n M&eacutedico', dataIndex: 'DIRECCION', flex: 3 },
                    { text: 'Tel&eacute;fono', dataIndex: 'NMTELEFO', flex: 1 },
                    { text: 'Especialidad', dataIndex: 'ESPECIALIDAD', flex: 2 }
                ],
                listeners: {
                	select: function(grd,record){
                		panelGuardar.down('[name=DSPRESTA]').setValue(record.get('CDPRESTA')+' - '+record.get('NOMBRE'));
                		panelGuardar.down('[name=CDPRESTA]').setValue(record.get('CDPRESTA'));
                	}
                }
            })]
        });
    	
    	panelGuardar = Ext.create('Ext.form.Panel',{
    		title: 'Informaci&oacute;n para Carta de Medicina Preventiva.',
    		border: false,
            defaults : {
            	style : 'margin : 10px 10px 4px;'
    		},
    		layout: {
                type: 'column',
                columns: 3 
            },
            items: [{
                xtype: 'textfield',
                fieldLabel: 'Enfermedad ICD',
                value: recordEditar.get('DSICD'),
                labelWidth: 100,
                width: 400,
                readOnly: true
            	},
                {
                    xtype: 'numberfield',
                    fieldLabel: 'Copago',
                    value: recordEditar.get('COPAGO'),
                    labelWidth: 50,
                    width: 200,
                    allowBlank: true,
                    name: 'COPAGO',
                    minValue:0,
                    negativeText : 'El valor no puede ser negativo'
                    //readOnly: true
                    
                },
                {
                	xtype: 'combobox',
                	value: Ext.isEmpty(recordEditar.get('SWFORMAT'))?'N' : recordEditar.get('SWFORMAT'),
                    labelWidth: 0,
                    width: 85,
                    allowBlank: false,
                    name: 'SWFORMAT',
	            	displayField: 'value',
	                valueField: 'key',
	                forceSelection: true,
	                anyMatch      : true,
	                queryMode     : 'local',
	                allowBlank    : true,
	                store         : Ext.create('Ext.data.Store', {
	                    fields: ['key', 'value'],
	                    data : [
	                        {"key":"N", "value":"(MONTO)"},
	                        {"key":"P", "value":"(%)"}
	                        //{"key":"NA", "value":"N/A"}
	                    ]
	                })
                },{
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
                    autoLoad : true,
                    proxy : {
                        type : 'ajax',
                        url : _URL_CatalogoFrecuenciaVisitas,
                        reader : {
                            type : 'json',
                            root : 'listaCatalogo'
                        }
                    },
                    listeners: {
                    	load: function(){
                    		panelGuardar.down('[name=CDFREC]').setValue(recordEditar.get('CDFREC'));
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
                    autoLoad : true,
                    proxy : {
                        type : 'ajax',
                        url : _URL_CatalogoPeriodicidadVisitas,
                        reader : {
                            type : 'json',
                            root : 'listaCatalogo'
                        }
                    },
                    listeners: {
                    	load: function(){
                    		panelGuardar.down('[name=CDPERIOD]').setValue(recordEditar.get('CDPERIOD'));
                    	}
                    }
                })
            },
            {
                xtype: 'textfield',
                fieldLabel: 'M&eacute;dico Tratante',
                labelWidth: 120,
                width: 460,
                allowBlank: false,
                readOnly: true,
                name: 'DSPRESTA',
                value: recordEditar.get('DSPRESTA')
            },{
                xtype: 'hidden',
                name: 'CDPRESTA',
                value: recordEditar.get('CDPRESTA')
            }]
        });
    		
    	ventanaCartaMedPrevPad = Ext.create('Ext.window.Window',{
    		title   : 'Datos para Carta de Medicina Preventiva'
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
                        		var valCopago = panelGuardar.down('[name=COPAGO]').getValue();
                        		var forCopago = panelGuardar.down('[name=SWFORMAT]').getValue();
                        		
                        		if (forCopago == 'P' && (valCopago*1) > 100){
                    				mensajeWarning('El valor del copago debe ser menor o igual a 100 para porcentaje.');
                    				return;
                    			}
                        		
                        		var datosForma = panelGuardar.getValues();

                        		var datosResultado = {
                               		 'params.pi_cdunieco'    : recordEditar.get('CDUNIECO'),
                               		 'params.pi_cdramo'      : recordEditar.get('CDRAMO'),
                               		 'params.pi_estado'      : recordEditar.get('ESTADO'),
                               		 'params.pi_nmpoliza'    : recordEditar.get('NMPOLIZA'),
                               		 'params.pi_nmsituac'    : recordEditar.get('NMSITUAC'),
                               		 'params.pi_cdperson'    : recordEditar.get('CDPERSON'),
                               		 'params.pi_cdicd'       : recordEditar.get('CDICD'),
                               		 'params.pi_cdfrec'      : panelGuardar.down('[name=CDFREC]').getValue(),
                               		 'params.pi_cdperiod'    : panelGuardar.down('[name=CDPERIOD]').getValue(),
                               		 'params.pi_copago'      : panelGuardar.down('[name=COPAGO]').getValue(),
                               		 'params.pi_swformat'    : panelGuardar.down('[name=SWFORMAT]').getValue(),
                               		 'params.pi_cdpresta'    : panelGuardar.down('[name=CDPRESTA]').getValue(),
                               		 'params.pi_dspad'       : '',
                               		 'params.pi_dsplanmed'   : '',
                               		 'params.pi_swgencarta'  : '',
                               		 'params.pi_swop'        : 'U'
                                };
                        		
                        		if(recordEditar.get('SWGENCARTA') == 'S'){
                        			Ext.Msg.show({
    		        		            title: 'Aviso',
    		        		            msg: 'Ya existe una o mas cartas de Medicina Preventiva para el padecimiento de este asegurado.<br/>&iquest;Desea actualizar esta informaci&oacute;n?',
    		        		            buttons: Ext.Msg.YESNO,
    		        		            fn: function(buttonId, text, opt) {
    		        		            	if(buttonId == 'yes') {
    		        		            		debug('Datos de carta a guardar: ',datosResultado);
    		        							
    		                                    var maskGuarda = _maskLocal('Guardando...');
    		                                    
    		                                    Ext.Ajax.request({
    		                                        url: _urlActualizaPadecimientoAseg,
    		                                        params: datosResultado,
    		                                        success  : function(response, options){
    		                                       	 maskGuarda.close();
    		                                            var json = Ext.decode(response.responseText);
    		                                            if(json.success){
    		                                            	
    		                                            	padecimientosGridStore.reload();
    		                                            	mensajeCorrecto('Aviso','Se ha guardado correctamente.');
    		                                            	SinDatosCartaMedPrev = false;
    		                                            	
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
    		        		            animateTarget: btn,
    		        		            icon: Ext.Msg.WARNING
    		    					});
                        		}else{
                        			
                        			debug('Datos de carta a guardar: ',datosResultado);
        							
                                    var maskGuarda = _maskLocal('Guardando...');
                                    
                                    Ext.Ajax.request({
                                        url: _urlActualizaPadecimientoAseg,
                                        params: datosResultado,
                                        success  : function(response, options){
                                       	 maskGuarda.close();
                                            var json = Ext.decode(response.responseText);
                                            if(json.success){
                                            	
                                            	padecimientosGridStore.reload();
                                            	mensajeCorrecto('Aviso','Se ha guardado correctamente.');
                                            	SinDatosCartaMedPrev = false;
                                            	
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
                               	    
                        	}else{
                        		var valCopago = panelGuardar.down('[name=COPAGO]').getValue();
                        		if(Ext.isEmpty(valCopago)){
                        			mensajeWarning('Capture correctamente todos los datos requeridos.');
                        		}else{
                        			if((valCopago*1) < 0){
                        				mensajeWarning('Verif&iacute;que que el valor del copago sea un valor positivo.');
                        			}else{
                        				mensajeWarning('Capture correctamente todos los datos requeridos.');
                        			}
                        		}
                        	}
                   			
                        }
                    }
                ]
            }],
            dockedItems: [
                {
                    xtype: 'toolbar',
                    dock: 'bottom',
                    items: [
                        {
                            xtype: 'button',
                            icon:_CONTEXT+'/resources/fam3icons/icons/zoom.png',
                            text: 'Vista Previa de la Carta',
                            tooltip: 'Vista Previa con los datos actualmente guardados',
                            handler: function(btn){
                            	
                            		if(SinDatosCartaMedPrev){
                            			mensajeInfo('No hay datos para Carta de Medicina Preventiva. Guarde los datos.');
                            			return;
                            		}
                            	
	                                verCartaMedicinaPreventiva(true);
				            }
                        },
                        {
                            xtype: 'button',
                            icon:_CONTEXT+'/resources/fam3icons/icons/page_go.png',
                            text: 'Imprimir Carta',
                            tooltip: 'Generar Carta de Medicina Preventiva en los documentos de p&oacute;liza',
                            handler: function(btn){
                            	
                        		if(SinDatosCartaMedPrev){
                        			mensajeInfo('No hay datos para Carta de Medicina Preventiva. Guarde los datos.');
                        			return;
                        		}
                        	
                        		var datosResultado = {
                               		 'params.pi_cdunieco'    : recordEditar.get('CDUNIECO'),
                               		 'params.pi_cdramo'      : recordEditar.get('CDRAMO'),
                               		 'params.pi_estado'      : recordEditar.get('ESTADO'),
                               		 'params.pi_nmpoliza'    : recordEditar.get('NMPOLIZA'),
                               		 'params.pi_nmsituac'    : recordEditar.get('NMSITUAC'),
                               		 'params.pi_cdperson'    : recordEditar.get('CDPERSON'),
                               		 'params.pi_cdicd'       : recordEditar.get('CDICD'),
                               		 'params.pi_cdfrec'      : '',
                            		 'params.pi_cdperiod'    : '',
                            		 'params.pi_copago'      : '',
                            		 'params.pi_swformat'    : '',
                            		 'params.pi_cdpresta'    : '',
                            		 'params.pi_dspad'       : '',
                            		 'params.pi_dsplanmed'   : '',
                            		 'params.pi_swgencarta'  : 'S', //Para cambiar switch de carta generada
                            		 'params.pi_swop'        : 'U', //Para cambiar switch de carta generada
                               		 'params.pi_nombre'      : _NombreAseg
                                };
                               	    
                        		if(recordEditar.get('SWGENCARTA') == 'S'){
                        			Ext.Msg.show({
    		        		            title: 'Aviso',
    		        		            msg: 'Ya existe una o mas cartas de Medicina Preventiva para el padecimiento de este asegurado.<br/>&iquest;Desea crear una nueva carta?',
    		        		            buttons: Ext.Msg.YESNO,
    		        		            fn: function(buttonId, text, opt) {
    		        		            	if(buttonId == 'yes') {
    		        		            		debug('Datos de carta a guardar: ',datosResultado);
    		        							
    		                                    var maskGuarda = _maskLocal('Generando Carta de Medicina Preventiva...');
    		                                    
    		                                    Ext.Ajax.request({
    		                                        url: _urlGeneraCartaMedicinaPreventiva,
    		                                        params: datosResultado,
    		                                        success  : function(response, options){
    		                                       	 maskGuarda.close();
    		                                            var json = Ext.decode(response.responseText);
    		                                            if(json.success){
    		                                            	
    		                                            	padecimientosGridStore.reload();
    		                                            	mensajeCorrecto('Aviso','La Carta de Medicina Preventiva se ha guardado correctamente en los documentos de p&oacute;liza.');
    		                                            	
    		                                            	verCartaMedicinaPreventiva(false);
    		                                            	
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
    		        		            animateTarget: btn,
    		        		            icon: Ext.Msg.WARNING
    		    					});
                        		}else{
                        			
                        			debug('Datos de carta a guardar: ',datosResultado);
        							
                                    var maskGuarda = _maskLocal('Generando Carta de Medicina Preventiva...');
                                    
                                    Ext.Ajax.request({
                                        url: _urlGeneraCartaMedicinaPreventiva,
                                        params: datosResultado,
                                        success  : function(response, options){
                                       	 maskGuarda.close();
                                            var json = Ext.decode(response.responseText);
                                            if(json.success){
                                            	
                                            	padecimientosGridStore.reload();
                                            	mensajeCorrecto('Aviso','La Carta de Medicina Preventiva se ha guardado correctamente en los documentos de p&oacute;liza.');
                                            	
                                            	verCartaMedicinaPreventiva(false);
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
                        		
			            }
                        },'->',{
            				text:'Cerrar',
            				icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
            				handler:function(btn)
            				{
            					ventanaCartaMedPrevPad.close();
            					medicosTratantesStore.removeAll();
            				}
            			}
                    ]
                }
            ]
    	}).show();
    	
    	//centrarVentanaInterna(ventanaCartaMedPrevPad);
    	
//    	if(ventanaCartaMedPrevPad){
//			ventanaCartaMedPrevPad.setLoading(true);
//		}
    	
    	//SE DESHABILITA CARGA DE COPAGO DE POLIZA
//    	Ext.Ajax.request({
//            url: _urlObtieneCopagoCobMedPrevPol,
//            params: {
//            	 'params.pi_cdunieco'    : recordEditar.get('CDUNIECO'),
//          		 'params.pi_cdramo'      : recordEditar.get('CDRAMO'),
//          		 'params.pi_estado'      : recordEditar.get('ESTADO'),
//          		 'params.pi_nmpoliza'    : recordEditar.get('NMPOLIZA'),
//          		 'params.pi_nmsituac'       : recordEditar.get('NMSITUAC'),
//            },
//            success  : function(response, options){
//                var json = Ext.decode(response.responseText);
//                if(json.success){
//                	
//                	var copago  = json.params.COPAGO;
//                	var formato = json.params.FORMATO;
//                	
//                	if(!Ext.isEmpty(formato)){
//                		if(formato == 'P' ){
//                			copago = copago+' %';
//                		}else if(formato == 'N' ){
//                			copago = '$ '+copago;
//                		}
//                	}
//                	
//                	var campoCopago = panelGuardar.down('[name=COPAGO]');
//                	campoCopago.setValue(copago);
//                }else{
//                	mensajeInfo('No se pudo obtener el Copago para este asegurado');
//                }
//            }
//            ,failure  : function(response, options){
//            	mensajeInfo('No se pudo obtener el Copago para este asegurado');
//            }
//        });
    	
    	function verCartaMedicinaPreventiva(vistaPrevia){
    		
    		var urlServerReporteCartaMP = _urlRutaReports
            
            + '?P_UNIECO='   + recordEditar.get('CDUNIECO')
            + '&P_RAMO='     + recordEditar.get('CDRAMO')
            + '&P_POLIZA='   + recordEditar.get('NMPOLIZA')
            + '&P_SITUAC='   + recordEditar.get('NMSITUAC')
            + '&P_CDPERSON=' + recordEditar.get('CDPERSON')
            + '&P_CDICD='    + recordEditar.get('CDICD')
            + '&destype=cache'
            + "&desformat=PDF"
            + "&userid="        + _reportsServerUser
            + "&ACCESSIBLE=YES"
            + "&report="        + _reporteCartaMedicinaPreventiva
            + "&paramform=no";
            
            debug(urlServerReporteCartaMP);
            var numRand = Math.floor((Math.random() * 100000) + 1);
            debug(numRand);
            var windowVerDocu = Ext.create('Ext.window.Window',
            {
                title          : vistaPrevia?'Vista Previa':'Carta de Medicina Preventiva'
                ,width         : 700
                ,height        : 500
                ,collapsible   : true
                ,titleCollapse : true
                ,html : '<iframe innerframe="'
                        + numRand
                        + '" frameborder="0" width="100" height="100"'
                        + 'src="'
                        + panDocUrlViewDoc
                        + "?contentType=application/pdf&url="
                        + encodeURIComponent(urlServerReporteCartaMP)
                        + "\">"
                        + '</iframe>'
                ,listeners :
                {
                    resize : function(win,width,height,opt)
                    {
                        debug(width,height);
                        $('[innerframe="'+ numRand+ '"]').attr(
                        {
                            'width'   : width - 20
                            ,'height' : height - 60
                        });
                    }
                }
            }).show();
            windowVerDocu.center();
    	}
    }

    function historialPadecimientoAseg(recordEditar){
    	var ventanaHistorialPad;
    	
    	var FormatoCopago = Ext.isEmpty(recordEditar.get('SWFORMAT'))?'N' : recordEditar.get('SWFORMAT');
    	var ValorCopago   = Ext.isEmpty(recordEditar.get('COPAGO'))?'' : recordEditar.get('COPAGO');
    	
    	if(FormatoCopago == 'N'){
    		ValorCopago = '$ '+ValorCopago;
    	}else{
    		ValorCopago = ValorCopago+' %';
    	}
    	
    	var historialGridStore = Ext.create('Ext.data.Store',
			    {
			        model   : 'modelHistorialPadecimientoAseg'
		        	,autoLoad: true
			        ,proxy   :
			        {
			        	type        : 'ajax'
			            ,url        : _urlCargaHistorialPadeciAseg
			            ,extraParams : {
			            	 'params.pi_cdunieco'    : recordEditar.get('CDUNIECO'),
			          		 'params.pi_cdramo'      : recordEditar.get('CDRAMO'),
			          		 'params.pi_estado'      : recordEditar.get('ESTADO'),
			          		 'params.pi_nmpoliza'    : recordEditar.get('NMPOLIZA'),
			          		 'params.pi_nmsituac'    : recordEditar.get('NMSITUAC'),
			          		 'params.pi_cdperson'    : recordEditar.get('CDPERSON'),
			          		 'params.pi_cdicd'       : recordEditar.get('CDICD'),
			          		 'params.pi_nmordtra'    : ''
			            }
			            ,reader     :
			            {
			                type  : 'json'
			                ,root : 'loadList'
			            }
			        }
		    });
    	
    	
    	var gridHistPanel = Ext.create('Ext.grid.Panel', {
			border: false,
			height : 200,
			viewConfig: {
				stripeRows: false,
				trackOver : true
			},
			store: historialGridStore,
			columns: [
				{ text: 'No', dataIndex: 'NMORDTRA' , width: 25 },
				{ text: 'Fecha', dataIndex: 'FEREG' , flex: 3 },
				{ text: 'M&eacutedico Tratante', dataIndex: 'NOMBRE_COMPLETO', flex: 6 },
				{ text: 'Especialidad', dataIndex: 'DSESPEC', flex: 6 },
				{ text: 'Peso', dataIndex: 'PESO', flex: 2 },
				{ text: 'Talla', dataIndex: 'TALLA', flex: 2 },
				{ text: 'Comentarios', dataIndex: 'COMENTARIOS', flex: 6 },
				{
	                xtype        : 'actioncolumn',
	                icon         : _CONTEXT+'/resources/fam3icons/icons/eye.png',
	                tooltip      : 'Ver Documento',
	                width        : 30,
	                menuDisabled : true,
	                sortable     : false,
	                handler      : function(grid,rowIndex)
	                {
	                    var recordSel = grid.getStore().getAt(rowIndex);
	                    var nombreArchivo = "DocHistorialMed_Aseg_" + recordSel.get("CDPERSON") + "_ICD_" + recordSel.get("CDICD")+
						"_No_" + recordSel.get("NMORDTRA");
	                    
	                    Ext.Ajax.request({
	                        url: _urlVerificaExisteDoc,
	                        params:{
		                    	'path'      : _RUTA_DOCUMENTOS_PERSONA,
		                    	'subfolder' : recordSel.get("CDPERSON"),
		                    	'filename'  : nombreArchivo
		                    },
	                        success  : function(response, options){
	                       	 
	                            var json = Ext.decode(response.responseText);
	                            if(json.success){
	                            	
	                            	if(json.smap1.EXISTE_ARCHIVO == 'S'){
	                            		nombreArchivo = nombreArchivo + json.smap1.EXTENSION;
	                            		
	                            		var numRand=Math.floor((Math.random()*100000)+1);
    	    		                    
    	    		                    var windowVerDocu=Ext.create('Ext.window.Window',
    	    		                    {
    	    		                        title          : 'Informe M&eacute;dico ' + recordSel.get('NMORDTRA')
    	    		                        ,width         : 700
    	    		                        ,height        : 500
    	    		                        ,collapsible   : true
    	    		                        ,titleCollapse : true
    	    		                        ,html          : '<iframe innerframe="'+numRand+'" frameborder="0" style="overflow: hidden; height: 100%;width: 100%; position: absolute;" height="100%" width="100%"'
    	    		                                			+'src="'+_urlViewDocHist+'?path='+_RUTA_DOCUMENTOS_PERSONA+'&subfolder='+recordSel.get("CDPERSON")+'&filename='+nombreArchivo+'">'
    	    		                                		+'</iframe>'
    	    		                        ,listeners     :
    	    		                        {
    	    		                             resize : function(win,width,height,opt){
    	    		                                 debug(width,height);
    	    		                                 $('[innerframe="'+numRand+'"]').attr({'width':width-20,'height':height-60});
    	    		                             }
    	    		                        }
    	    		                    }).show();
    	    		                    centrarVentanaInterna(windowVerDocu);
    	    		                    
	                            	}else{
	                            		mensajeInfo('No existe documento para este registro del historial.');
	                            	}
	                            }else{
	                                mensajeError(json.respuesta);
	                            }
	                        }
	                        ,failure  : function(response, options){
	                       	 
	                            var json = Ext.decode(response.responseText);
	                            mensajeError(json.respuesta);
	                        }
	                    });
	                    
	                }
	            },
	            {
	                xtype        : 'actioncolumn',
	                icon         : _CONTEXT+'/resources/fam3icons/icons/page_white_put.png',
	                tooltip      : 'Descargar Documento',
	                width        : 30,
	                menuDisabled : true,
	                sortable     : false,
	                handler      : function(grid,rowIndex)
	                {
	                	 var recordSel = grid.getStore().getAt(rowIndex);
		                    var nombreArchivo = "DocHistorialMed_Aseg_" + recordSel.get("CDPERSON") + "_ICD_" + recordSel.get("CDICD")+
							"_No_" + recordSel.get("NMORDTRA");
		                    
		                    Ext.Ajax.request({
		                        url: _urlVerificaExisteDoc,
		                        params:{
			                    	'path'      : _RUTA_DOCUMENTOS_PERSONA,
			                    	'subfolder' : recordSel.get("CDPERSON"),
			                    	'filename'  : nombreArchivo
			                    },
		                        success  : function(response, options){
		                       	 
		                            var json = Ext.decode(response.responseText);
		                            if(json.success){
		                            	
		                            	if(json.smap1.EXISTE_ARCHIVO == 'S'){
		                            		nombreArchivo = nombreArchivo + json.smap1.EXTENSION;
		                            		
		                            		
		                            		Ext.create('Ext.form.Panel').submit({
                        			        url              : _urlDownloadDocHist
                        			        , standardSubmit : true
                        			        , target         : '_blank'
                        			        , params         :
                        			        {
                        			        	path      : _RUTA_DOCUMENTOS_PERSONA
                        			            ,subfolder: recordSel.get("CDPERSON")
                        			            ,filename : nombreArchivo
                        			        }
                        			    });
		                            		
		                            	}else{
		                            		mensajeInfo('No existe documento para este registro de historial.');
		                            	}
		                            }else{
		                                mensajeError(json.respuesta);
		                            }
		                        }
		                        ,failure  : function(response, options){
		                       	 
		                            var json = Ext.decode(response.responseText);
		                            mensajeError(json.respuesta);
		                        }
		                    });
	                }
	            }
				],
			listeners: {
				select: function(grd,record){}
			},
			dockedItems: [
                {
                    xtype: 'toolbar',
                    dock: 'top',
                    items: [
                        {
                            xtype: 'button',
                            text: 'Agregar nuevo registro',
                            icon:_CONTEXT+'/resources/fam3icons/icons/page_add.png',
                            handler: function(btn){
                            	
                            	var nuevoRecHist = new modelHistorialPadecimientoAseg();
                            	nuevoRecHist.set('CDUNIECO', recordEditar.get('CDUNIECO'));
                            	nuevoRecHist.set('CDRAMO'  , recordEditar.get('CDRAMO'));
                            	nuevoRecHist.set('ESTADO'  , recordEditar.get('ESTADO'));
                            	nuevoRecHist.set('NMPOLIZA', recordEditar.get('NMPOLIZA'));
                            	nuevoRecHist.set('NMSITUAC', recordEditar.get('NMSITUAC'));
                            	nuevoRecHist.set('CDPERSON', recordEditar.get('CDPERSON'));
                            	nuevoRecHist.set('CDICD'   , recordEditar.get('CDICD'));
                            	
                            	agregarEditarRegistroHistorial(nuevoRecHist,recordEditar);
                            }
                        },{
                            xtype: 'button',
                            text: 'Editar &uacute;ltimo registro',
                            icon:_CONTEXT+'/resources/fam3icons/icons/page_edit.png',
                            handler: function(btn){
                            	
                            	var maxOrd = -1;
                            	var recordMax;
                            	
                            	gridHistPanel.getStore().each(function(recordIt){
                            		var ordenAct = (recordIt.get('NMORDTRA'))*1;
                            		if(ordenAct>maxOrd){
                            			maxOrd = ordenAct;
                            			recordMax = recordIt;
                            		}
                            	});
                            	
                            	if(maxOrd == -1){
                            		mensajeInfo('No hay historial registrado para este padecimiento.');
                            		return;
                            	}
                            	
                            	agregarEditarRegistroHistorial(recordMax,recordEditar);
                            }
                        }
                    ]
                }
            ]
		});
    	
    	ventanaHistorialPad = Ext.create('Ext.window.Window',{
    		title   : 'Historial Medicina Preventiva'
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
    							labelWidth: 100,
    							width: 210,
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
    							width: 240,
    							readOnly : true,
    							value: _Rfc
    						},
    						{
    							xtype: 'textfield',
    							fieldLabel: 'Nombre',
    							labelWidth: 100,
    							width: 400,
    							readOnly : true,
    							value: _NombreAseg
    						},{
    							xtype: 'textfield',
    							fieldLabel: 'Enfermedad ICD',
    							value: recordEditar.get('DSICD'),
    							labelWidth: 100,
    							width: 400,
    							readOnly: true
    						},
    						{
    							xtype: 'textfield',
    							fieldLabel: 'Copago',
    							value: ValorCopago,
    							labelWidth: 50,
    							width: 240,
    							name: 'COPAGO',
    							readOnly: true
    								
    						},
    						{
    							xtype: 'textfield',
    							fieldLabel: 'M&eacute;dico Tratante',
    							labelWidth: 100,
    							width: 400,
    							readOnly: true,
    							name: 'DSPRESTA',
    							value: recordEditar.get('DSPRESTA')
    						},{
    							xtype: 'combobox',
    							width: 240,
    							labelWidth: 120,
    							fieldLabel: 'Frecuencia de Visitas',
    							name: 'CDFREC',
    							displayField: 'value',
    							valueField: 'key',
    							forceSelection: true,
    							anyMatch      : true,
    							queryMode     : 'local',
    							readOnly: true,
    							store       : Ext.create('Ext.data.Store', {
    								model : 'Generic',
    								autoLoad : true,
    								proxy : {
    									type : 'ajax',
    									url : _URL_CatalogoFrecuenciaVisitas,
    									reader : {
    										type : 'json',
    										root : 'listaCatalogo'
    									}
    								},
    								listeners: {
    									load: function(){
    										ventanaHistorialPad.down('[name=CDFREC]').setValue(recordEditar.get('CDFREC'));
    									}
    								}
    							})
    						},
    						{
    							xtype: 'combobox',
    							fieldLabel: 'Periodicidad',
    							labelWidth: 100,
    							name: 'CDPERIOD',
    							displayField: 'value',
    							valueField: 'key',
    							forceSelection: true,
    							anyMatch      : true,
    							queryMode     : 'local',
    							readOnly: true,
    							store       : Ext.create('Ext.data.Store', {
    								model : 'Generic',
    								autoLoad : true,
    								proxy : {
    									type : 'ajax',
    									url : _URL_CatalogoPeriodicidadVisitas,
    									reader : {
    										type : 'json',
    										root : 'listaCatalogo'
    									}
    								},
    								listeners: {
    									load: function(){
    										ventanaHistorialPad.down('[name=CDPERIOD]').setValue(recordEditar.get('CDPERIOD'));
    									}
    								}
    							})
    						}
    						]
    					})
    	    			,
    	    			gridHistPanel
    					]
    			}],
    			dockedItems: [
    				{
    					xtype: 'toolbar',
    					dock: 'bottom',
    					items: ['->',{
    							text:'Cerrar',
    							icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
    							handler:function(btn)
    							{
    								ventanaHistorialPad.close();
    							}
    						}
    						]
    				}
    				]
    	}).show();
    	
    	
    	function agregarEditarRegistroHistorial(recordEditarHist,recordPadeci){
    		var ventanaAgregarEditarHist;
    		var esNuevoRegistroHist = (!Ext.isEmpty(recordEditarHist) && recordEditarHist.phantom == false) ? false : true;
    		
    		var panelAgregarHistorialPad = Ext.create('Ext.form.Panel',{
    			title: 'Datos del Informe M&eacute;dico',
    			border:false,
    			defaults : {
    				style : 'margin : 10px 10px 4px;'
    			},
    			layout: {
    				type: 'column',
    				columns: 3 
    			},
    			items: [{
    	                xtype: 'datefield',
    	                fieldLabel: 'Fecha',
    	                name: 'FECHA',
    	                labelWidth: 60,
    	                allowBlank: false,
    	                width: 200,
    	                value: !esNuevoRegistroHist? recordEditarHist.get('FEREG') : ''
    	        	},{
    	              	xtype: 'checkbox',
    	              	fieldLabel: 'Usar datos del M&eacute;dico Tratante Asignado por Default',
    	              	name: 'ES_CDPRESTA',
    	              	labelWidth: 350,
    	              	labelPad: 20,
    	              	labelAlign: 'right', 
    	                checked: !esNuevoRegistroHist? (Ext.isEmpty(recordEditarHist.get('CDPRESTA'))? false : true ) : false,
    	                listeners: {
    	                	change: function(ck, newValue){
    	                		
    	                		var nombreMed = panelAgregarHistorialPad.down('textfield[name=NOMBRE_MED]');
    	                		var appatMed = panelAgregarHistorialPad.down('textfield[name=APPAT_MED]');
    	                		var apmatMed = panelAgregarHistorialPad.down('textfield[name=APMAT_MED]');
    	                		
    	                		if(newValue){
    	                			nombreMed.setValue(recordPadeci.get('NOMBRE'));
    	                			appatMed.setValue(recordPadeci.get('APELL1'));
    	                			apmatMed.setValue(recordPadeci.get('APELL2'));
    	                			
    	                			nombreMed.setReadOnly(true);
    	                			appatMed.setReadOnly(true);
    	                			apmatMed.setReadOnly(true);
    	                			
    	                		}else{
    	                			nombreMed.reset();
    	                			appatMed.reset();
    	                			apmatMed.reset();
    	                			
    	                			nombreMed.setReadOnly(false);
    	                			appatMed.setReadOnly(false);
    	                			apmatMed.setReadOnly(false);
    	                		}
    	                	}
    	                }
    	            },
    	            {
    	                xtype: 'textfield',
    	                fieldLabel: 'Nombre del M&eacute;dico Tratante',
    	                name: 'NOMBRE_MED',
    	                allowBlank: false,
    	                labelWidth: 120,
    	                width: 340,
    	                value: !esNuevoRegistroHist? recordEditarHist.get('DSNOMMED') : '',
    	                readOnly: !esNuevoRegistroHist? (Ext.isEmpty(recordEditarHist.get('CDPRESTA'))? false : true ) : false
    	            },
    	            {
    	                xtype: 'textfield',
    	                fieldLabel: 'Apellido Paterno del M&eacute;dico Tratante',
    	                name: 'APPAT_MED',
    	                allowBlank: false,
    	                labelWidth: 120,
    	                width: 340,
    	                value: !esNuevoRegistroHist? recordEditarHist.get('DSAPELLMED1') : '',
    	    	        readOnly: !esNuevoRegistroHist? (Ext.isEmpty(recordEditarHist.get('CDPRESTA'))? false : true ) : false
    	            },
    	            {
    	                xtype: 'textfield',
    	                fieldLabel: 'Apellido Materno del M&eacute;dico Tratante',
    	                name: 'APMAT_MED',
    	                allowBlank: false,
    	                labelWidth: 120,
    	                width: 340,
    	                value: !esNuevoRegistroHist? recordEditarHist.get('DSAPELLMED2') : '',
    	    	        readOnly: !esNuevoRegistroHist? (Ext.isEmpty(recordEditarHist.get('CDPRESTA'))? false : true ) : false
    	            },
    	            {
    	                xtype: 'numberfield',
    	                fieldLabel: 'Peso del Asegurado',
    	                name: 'PESO',
    	                allowBlank: false,
    	                labelWidth: 70,
    	                allowDecimals: true,
    	                minValue: 0,
    	                negativeText : 'El valor no puede ser negativo',
    	                width: 160,
    	                value: !esNuevoRegistroHist? recordEditarHist.get('PESO') : ''
    	            },
    	            {
    	                xtype: 'numberfield',
    	                fieldLabel: 'Talla del Asegurado',
    	                name: 'TALLA',
    	                allowBlank: false,
    	                labelWidth: 70,
    	                allowDecimals: true,
    	                minValue: 0,
    	                negativeText : 'El valor no puede ser negativo',
    	                width: 160,
    	                value: !esNuevoRegistroHist? recordEditarHist.get('TALLA') : ''
    	            },
    	            {
    	                xtype: 'textareafield',
    	                height: 80,
    	                width: 340,
    	                labelWidth: 120,
    	                fieldLabel: 'Comentarios',
    	                name: 'COMENTARIOS',
    	                maxLength : 2000,
    	                value: !esNuevoRegistroHist? recordEditarHist.get('COMENTARIOS') : ''
    	            },{
    	    	        xtype: 'filefield',
    	    	        fieldLabel: 'Subir Archivo',
    	    	        labelWidth: 70,
    	    	        name: 'file',
    	    	        width: 340,
    	    	        msgTarget: 'side',
    	    	        buttonText: 'Examinar...',
    	    	        cAccept  : ['pdf','png','jpg','jpeg'],
    	    	        listeners: {
    	    	        	change: function(me, value, opts){
    	    	        		
    	    	        		var indexofPeriod = value.lastIndexOf(".");
    	                        var uploadedExtension = value.substr(indexofPeriod + 1, value.length - indexofPeriod).toLowerCase();
    	                        if (!Ext.Array.contains(this.cAccept, uploadedExtension)){
    	                            Ext.MessageBox.show({
    	                                title   : 'Error de tipo de archivo',
    	                                msg     : 'Extensiones permitidas: ' + this.cAccept.join(),
    	                                buttons : Ext.Msg.OK,
    	                                icon    : Ext.Msg.WARNING
    	                            });
    	                            me.reset();
    	                        }
    	    	        	}
    	    	        }
    	    	    }
    	        ],
    				buttonAlign: 'center',
    				buttons:[{
    					xtype: 'button',
    					text: 'Guardar Registro de Historial',
    					icon:_CONTEXT+'/resources/fam3icons/icons/add.png',
    					handler: function(btnH){

    						var panelGuardarHist = btnH.up('form');
    						
    						if(panelGuardarHist.getForm().isValid()){
    							
    							var formaHist = panelGuardarHist.getValues();
    							var esCdpresta = panelGuardarHist.down('[name=ES_CDPRESTA]').getValue();
    							var codigoCdpresta   = '';
    							
    							if(esCdpresta){
    								codigoCdpresta = recordPadeci.get('CDPRESTA');
    							}else{
    								
    							}

    							var datosGuardHist = {
    					       		 'params.pi_cdunieco'    : recordEditarHist.get('CDUNIECO'),
    					       		 'params.pi_cdramo'      : recordEditarHist.get('CDRAMO'),
    					       		 'params.pi_estado'      : recordEditarHist.get('ESTADO'),
    					       		 'params.pi_nmpoliza'    : recordEditarHist.get('NMPOLIZA'),
    					       		 'params.pi_nmsituac'    : recordEditarHist.get('NMSITUAC'),
    					       		 'params.pi_cdperson'    : recordEditarHist.get('CDPERSON'),
    					       		 'params.pi_cdicd'       : recordEditarHist.get('CDICD'),
    					       		 'params.pi_nmordtra'    : recordEditarHist.get('NMORDTRA'),
    					       		 'params.pi_fereg'       : formaHist.FECHA,
    					       		 'params.pi_peso'        : formaHist.PESO,
    					       		 'params.pi_talla'       : formaHist.TALLA,
    					       		 'params.pi_cdpresta'    : codigoCdpresta,
    					       		 'params.pi_dsnommed'    : formaHist.NOMBRE_MED,
    					       		 'params.pi_dsapellmed1' : formaHist.APPAT_MED,
    					       		 'params.pi_dsapellmed2' : formaHist.APMAT_MED,
    					       		 'params.pi_comentarios' : formaHist.COMENTARIOS,
    					       		 'params.pi_swop'        : 'U'
    					        };
    					       	    
    					        debug('Tratamiento/Historial a guardar: ',datosGuardHist);
    					        

    							var maskGuarda = _maskLocal('Guardando...');
    					        
    					        Ext.Ajax.request({
    					            url: _urlActualizaHistorialPadeciAseg,
    					            params: datosGuardHist,
    					            success  : function(response, options){
    					           	 maskGuarda.close();
    					                var json = Ext.decode(response.responseText);
    					                if(json.success){
    					                	
    					                	historialGridStore.reload();
    					                	mensajeCorrecto('Aviso','Se ha guardado correctamente.');
    					                	
    					                	var ordinal = recordEditarHist.get('NMORDTRA');
    					                	
    					                	if(Ext.isEmpty(ordinal)){
    					                		ordinal = json.params.nuevoOrdinal;
    					                	}
    					                	
    					                	var subirArchivo = !Ext.isEmpty(panelGuardarHist.down('[name=file]').getRawValue());
    					                	
    					                	if(subirArchivo){
    					                		panelGuardarHist.submit({
        						                    url: _UrlSubirArchivoHistPad,
        						        			params:{
        						                    	'smap1.cdperson': recordEditarHist.get('CDPERSON'),
        						                    	'smap1.cdicd'   : recordEditarHist.get('CDICD'),
        						                    	'smap1.nmordtra': ordinal
        						                    },
        						                    waitMsg: 'Subiendo Archivo...',
        						                    success: function(fp, o) {
        						                        mensajeCorrecto('Exito', 'Se ha guardado correctamente. Archivo Cargado Correctamente');
        						                        ventanaAgregarEditarHist.close();
        						                    },
        						                    failure: function(form, action) {
        						                		switch (action.failureType) {
        						                            case Ext.form.action.Action.CONNECT_FAILURE:
        						                        	    Ext.Msg.show({title: 'Error', msg: 'Informaci&oacute;n guardada. Error de comunicaci&oacute;n al subir Archivo', buttons: Ext.Msg.OK, icon: Ext.Msg.ERROR});
        						                                break;
        						                            case Ext.form.action.Action.SERVER_INVALID:
        						                            case Ext.form.action.Action.LOAD_FAILURE:
        						                            	 var msgServer = Ext.isEmpty(action.result.mensajeRespuesta) ? 'Informaci&oacute;n guardada. Error interno del servidor al subir Archivo, consulte a soporte, ' : 'Informaci&oacute;n guardada. Error al subir archivo, ' + action.result.mensajeRespuesta;
        						                                 Ext.Msg.show({title: 'Error', msg: msgServer, buttons: Ext.Msg.OK, icon: Ext.Msg.ERROR});
        						                                break;
        						                        }
        						        			}
        						                });
    					                	}else{
    					                		ventanaAgregarEditarHist.close();
    					                	}
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
    				}]
    		});
    			
    		ventanaAgregarEditarHist = Ext.create('Ext.window.Window',{
    			title   : esNuevoRegistroHist ? 'Agregar registro de Historial M&eacute;dico.':'Editar registro de Historial M&eacute;dico.'
    			,modal  : true
    			,width  : 790
    			,items : [{
    	            xtype: 'form',
    	            defaults : {
    	    			style : 'margin : 6px 10px 6px 10px;'
    	    		},
    	            items: [panelAgregarHistorialPad]
    	        }],
    			dockedItems: [
    				{
    					xtype: 'toolbar',
    					hidden: true,
    					dock: 'bottom',
    					items: ['->',{
    							text:'Cerrar',
    							icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
    							handler:function(btn)
    							{
    								ventanaAgregarEditarHist.close();
    							}
    						}
    						]
    				}
    				]
    		}).show();
    		
    		centrarVentanaInterna(ventanaAgregarEditarHist);
    		
    	}
    	
}
    
    Ext.define('PanelPrincipalMedPRev',
    {
        extend         : 'Ext.panel.Panel',
        items: [
            {
                xtype: 'panel',
                height: 835,
                items: [
                	gridPadecimientosAsegurado
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