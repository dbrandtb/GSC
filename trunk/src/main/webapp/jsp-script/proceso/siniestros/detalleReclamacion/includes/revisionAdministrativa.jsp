<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp"%>

<script type="text/javascript">

var _CONTEXT = '${ctx}';

var _PAGO_DIRECTO = "1";
var _REEMBOLSO    = "2";


var _URL_LoadFacturas =  '<s:url namespace="/siniestros" action="loadListaFacturasTramite" />';
var _URL_GuardaFactura =  '<s:url namespace="/siniestros" action="guardaFacturaTramite" />';
var _URL_ActualizaFactura =  '<s:url namespace="/siniestros" action="actualizaFacturaTramite" />';
var _URL_EliminaFactura =  '<s:url namespace="/siniestros" action="eliminaFacturaTramite" />';

var _URL_LoadConceptos =  '<s:url namespace="/siniestros" action="obtenerMsinival" />';
var _URL_GuardaConcepto =  '<s:url namespace="/siniestros" action="guardarMsinival" />';

var _UrlAjustesMedicos =  '<s:url namespace="/siniestros" action="includes/ajustesMedicos" />';

var _URL_CATALOGOS = '<s:url namespace="/catalogos" action="obtieneCatalogo" />';
var _CATALOGO_TipoAtencion = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TIPO_ATENCION_SINIESTROS"/>';
var _CATALOGO_PROVEEDORES  = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@PROVEEDORES"/>';
var _CATALOGO_COBERTURAS  = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@COBERTURAS"/>';
var _CATALOGO_SUBCOBERTURAS  = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@SUBCOBERTURAS"/>';

var _CATALOGO_TipoConcepto  = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TIPO_CONCEPTO_SINIESTROS"/>';
var _CATALOGO_ConceptosMedicos  = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@CODIGOS_MEDICOS"/>';

var _Operacion;
var _Nmordina;

Ext.onReady(function() {
	
	var storeFacturas;
	var storeConceptos;
	
	var gridFacturas;
	var gridConceptos;
	
	var panelPrincipal;
	
	Ext.define('modelFacturas',{
        extend: 'Ext.data.Model',
        fields: [{type:'string',    name:'NFACTURA'},
                 {type:'string',    name:'FFACTURA'},
                 {type:'string',    name:'CDTIPSER'},
                 {type:'string',    name:'DESCRIPC'},
                 {type:'string',    name:'CDPRESTA'},
                 {type:'string',    name:'PTIMPORT'},
                 {type:'string',    name:'CDGARANT'},
                 {type:'string',    name:'DSGARANT'},
                 {type:'string',    name:'DESCPORC'},
                 {type:'string',    name:'DESCNUME'},
                 {type:'string',    name:'CDCONVAL'},
                 {type:'string',    name:'COPAGO'},
                 {type:'string',    name:'NOM_PRESTA'},
                 {type:'string',    name:'DSSUBGAR'},
                 {type:'string',    name:'AUTRECLA'},
                 {type:'string',    name:'AUTMEDIC'},
                 {type:'string',    name:'COMMENME'},
                 {type:'string',    name:'DEDUCIBLE'},
                 {type:'string',    name:'COMMENAR'}
				]
    });
	
	Ext.define('modelConceptos',{
        extend: 'Ext.data.Model',
        fields: ["CDUNIECO","CDRAMO","ESTADO","NMPOLIZA","NMSUPLEM",
         		"NMSITUAC","AAAPERTU","STATUS","NMSINIES","NFACTURA",
        		"CDGARANT","CDCONVAL","CDCONCEP","IDCONCEP","CDCAPITA",
        		"NMORDINA",{name:"FEMOVIMI", type: "date", dateFormat: "d/m/Y"},"CDMONEDA","PTPRECIO","CANTIDAD",
        		"DESTOPOR","DESTOIMP","PTIMPORT","PTRECOBR","NMANNO",
        		"NMAPUNTE","USERREGI",{name:"FEREGIST", type: "date", dateFormat: "d/m/Y"}]
    });

	Ext.define('modelListadoProvMedico',{
	    extend: 'Ext.data.Model',
	    fields: [
            {type:'string', name:'cdpresta'},
            {type:'string', name:'nombre'},
            {type:'string', name:'cdespeci'},
            {type:'string',name:'descesp'}
	    ]
	});
	
	storeFacturas=new Ext.data.Store(
	{
	    autoDestroy: true,
	    model: 'modelFacturas',
	    proxy: {
            type: 'ajax',
            url: _URL_LoadFacturas,
            reader: {
                type: 'json',
                root: 'loadList'
            }
        }
	});
	storeFacturas.load({
    	params: {
    		'params.ntramite' : _NTRAMITE,
    		'params.cdunieco'  : _CDUNIECO,
    		'params.cdramo'    : _CDRAMO,
    		'params.estado'    : _ESTADO,
    		'params.nmpoliza'  : _NMPOLIZA,
    		'params.nmsituac'  : _NMSITUAC,
    		'params.nmsuplem'  : _NMSUPLEM,
    		'params.status'    : _STATUS,
    		'params.aaapertu'  : _AAAPERTU,
    		'params.nmsinies'  : _NMSINIES
    	}
    });

	storeConceptos=new Ext.data.Store(
			{
			    autoDestroy: true,
			    model: 'modelConceptos',
			    proxy: {
		            type: 'ajax',
		            url: _URL_LoadConceptos,
		            reader: {
		                type: 'json',
		                root: 'loadList'
		            }
		        }
			});
	
	var storeTipoAtencion = Ext.create('Ext.data.JsonStore', {
		model:'Generic',
		proxy: {
			type: 'ajax',
			url: _URL_CATALOGOS,
			extraParams : {catalogo:_CATALOGO_TipoAtencion},
			reader: {
				type: 'json',
				root: 'lista'
			}
		}
	});
	storeTipoAtencion.load();
	
	var storeProveedor = Ext.create('Ext.data.Store', {
        model:'modelListadoProvMedico',
        autoLoad:false,
        proxy: {
            type: 'ajax',
            url : _URL_CATALOGOS,
            extraParams:{
                catalogo         : _CATALOGO_PROVEEDORES,
                catalogoGenerico : true
            },
            reader: {
                type: 'json',
                root: 'listaGenerica'
            }
        }
    });
	storeProveedor.load();

	var storeCoberturas = Ext.create('Ext.data.Store',{
        model: 'Generic',
        autoLoad: true,
        proxy: {
            type: 'ajax',
            url: _URL_CATALOGOS,
            reader: {
                type: 'json',
                root: 'lista'
            },
            extraParams: {
                'catalogo' : _CATALOGO_COBERTURAS,
                'params.cdramo' : _CDRAMO,
                'params.cdtipsit' : 'SL'
            }
        }
	});
	storeCoberturas.load();

	var storeSubcoberturas = Ext.create('Ext.data.Store',{
        model: 'Generic',
        autoLoad: false,
        proxy: {
            type: 'ajax',
            url: _URL_CATALOGOS,
            reader: {
                type: 'json',
                root: 'lista'
            },
            extraParams: {
                catalogo: _CATALOGO_SUBCOBERTURAS
            }
        }
	});

	var storeSubcoberturasC = Ext.create('Ext.data.Store',{
        model: 'Generic',
        autoLoad: false,
        proxy: {
            type: 'ajax',
            url: _URL_CATALOGOS,
            reader: {
                type: 'json',
                root: 'lista'
            },
            extraParams: {
                catalogo: _CATALOGO_SUBCOBERTURAS
            }
        }
	});
	
	var storeTipoConcepto = Ext.create('Ext.data.JsonStore', {
		model:'Generic',
		proxy: {
			type: 'ajax',
			url: _URL_CATALOGOS,
			extraParams : {catalogo:_CATALOGO_TipoConcepto},
			reader: {
				type: 'json',
				root: 'lista'
			}
		}
	});
	storeTipoConcepto.load();
	
	var storeConceptosCatalogo = Ext.create('Ext.data.JsonStore', {
		model:'Generic',
		proxy: {
			type: 'ajax',
			url: _URL_CATALOGOS,
			extraParams : {catalogo:_CATALOGO_ConceptosMedicos},
			reader: {
				type: 'json',
				root: 'lista'
			}
		}
	});
	
	
	var panelEdicionFacturas= Ext.create('Ext.form.Panel',{
        border  : 0,
        url: _URL_GuardaFactura
        ,bodyStyle:'padding:5px;'
        ,items :
        [   
             {
		        xtype      : 'textfield'
		    	,fieldLabel : 'No. Factura'
	    		,allowBlank:false
		    	,name       : 'params.nfactura'
    		},            
    		{
    	        name: 'params.fefactura',
    	        fieldLabel: 'Fecha Factura',
    	        xtype: 'datefield',
    	        format: 'd/m/Y',
    	        editable: true,
    	        allowBlank:false,
    	        value:new Date()
    	    },{
            	xtype: 'combo',
                name:'params.cdtipser',
                valueField: 'key',
                displayField: 'value',
                fieldLabel: 'Tipo de servicio',
                store: storeTipoAtencion,
                queryMode:'local',
                allowBlank:false,
                editable:false
            },{
            	xtype       : 'combo',
            	name        : 'params.cdpresta',
            	fieldLabel  : 'Proveedor',
            	displayField: 'nombre',
            	valueField  : 'cdpresta',
            	allowBlank  : false,
                forceSelection : true,
                matchFieldWidth: false,
                queryMode   :'remote',
                queryParam  : 'params.cdpresta',
                store       : storeProveedor,
                triggerAction  : 'all',
                editable    : false
            },{
            	xtype       : 'combo',
            	name        : 'params.cdgarant',
            	fieldLabel  : 'Cobertura',
            	displayField: 'value',
            	valueField  : 'key',
            	allowBlank  : true,
                forceSelection : true,
                matchFieldWidth: false,
                queryMode   :'remote',
                store       : storeCoberturas,
                triggerAction  : 'all',
                listeners: {
                	select: function (combo, records, opts){
                		var cdGarant =  records[0].get('key');
                		storeSubcoberturas.load({
                			params: {
                				'params.cdgarant' : cdGarant
                			}
                		});
                	}
                },
                editable    : false
            },{
            	xtype       : 'combo',
            	name        : 'params.cdconval',
            	fieldLabel  : 'Sub Cobertura',
            	displayField: 'value',
            	valueField  : 'key',
            	allowBlank  : true,
                forceSelection : true,
                matchFieldWidth: false,
                queryMode   :'local',
                store       : storeSubcoberturas,
                triggerAction  : 'all',
                editable    : false
            },{
		        xtype      : 'numberfield'
		    	,fieldLabel : 'Importe'
                ,allowBlank:false
                ,allowDecimals: true
                ,decimalSeparator: '.'
                ,minValue: 0
		    	,name       : 'params.ptimport'
    		},{
		        	xtype      : 'numberfield'
			    	,fieldLabel : 'Descuento %'
	                ,allowBlank:false
	                ,allowDecimals: true
	                ,decimalSeparator: '.'
	                ,minValue: 0
			    	,name       : 'params.descporc'
	    	},{
		        xtype      : 'numberfield'
			    	,fieldLabel : 'Descuento Importe'
	                ,allowBlank:false
	                ,allowDecimals: true
	                ,decimalSeparator: '.'
	                ,minValue: 0
			    	,name       : 'params.descnume'
	    	}
	    	<s:property value='%{"," + imap.itemsEdicion}' />
        ]
    });
    
	
    var panelEdicionConceptos = Ext.create('Ext.form.Panel',{
        border  : 0
        ,bodyStyle:'padding:5px;'
        ,url: _URL_GuardaConcepto
        ,items :
        [   {
        	xtype       : 'combo',
        	name        : 'params.cdconval',
        	fieldLabel  : 'Sub Cobertura',
        	labelWidth: 150,
        	displayField: 'value',
        	valueField  : 'key',
        	allowBlank  : false,
            forceSelection : true,
            matchFieldWidth: false,
            queryMode   :'local',
            store       : storeSubcoberturasC,
            triggerAction  : 'all',
            editable    : false
        },{
        	xtype: 'combo',
            name:'params.idconcep',
            labelWidth: 150,
            valueField: 'key',
            displayField: 'value',
            fieldLabel: 'Tipo de Concepto',
            store: storeTipoConcepto,
            queryMode:'local',
            allowBlank:false,
            editable:false,
            listeners:{
            	select: function (combo, records, opts){
            		var cdTipo =  records[0].get('key');
            		storeConceptosCatalogo.load({
            			params: {
            				'params.idPadre' : cdTipo
            			}
            		});
            	}
            }
        },{
        	xtype: 'combo',
            name:'params.cdconcep',
            labelWidth: 150,
            valueField: 'key',
            displayField: 'value',
            fieldLabel: 'Concepto',
            store: storeConceptosCatalogo,
            queryMode:'local',
            allowBlank:false,
            editable:true,
            forceSelection: true
        },
            {
		        xtype      : 'numberfield'
		    	,fieldLabel : 'Precio'
	    		,labelWidth: 150
	    		,allowBlank:false
	    		,allowDecimals: true
                ,decimalSeparator: '.'
                ,minValue: 0
		    	,name       : 'params.ptprecio'
		    	,listeners: {
		    		change: calculaImporteConcepto
		    	}
    		}
            ,{
		       		 xtype      : 'numberfield'
			    	,fieldLabel : 'Cantidad'
		    		,labelWidth: 150
		    		,allowBlank:false
	                ,minValue: 0
			    	,name       : 'params.cantidad'
			    	,listeners: {
				    		change: calculaImporteConcepto
				    }
	    	}
	         ,
            {
		        xtype      : 'numberfield'
		    	,fieldLabel : 'Descuento (%)'
	    		,labelWidth: 150
                ,minValue: 0
	    		,allowBlank:false
		    	,name       : 'params.destopor'
		    	,listeners: {
			    		change: calculaImporteConcepto
			    }
    		},
            {
		        xtype      : 'numberfield'
		    	,fieldLabel : 'Descuento Importe'
	    		,labelWidth: 150
	    		,allowBlank:false
	    		,allowDecimals: true
                ,decimalSeparator: '.'
                ,minValue: 0
		    	,name       : 'params.destoimp'
		    	,listeners: {
			    		change: calculaImporteConcepto
			    }
    		}
            ,
            {
		        xtype      : 'numberfield'
		    	,fieldLabel : 'Subtotal factura'
	    		,labelWidth: 150
	    		,allowBlank:false
	    		,allowDecimals: true
                ,decimalSeparator: '.'
                ,readOnly: true
                ,minValue: 0
		    	,name       : 'params.ptimport'
    		}
            /*,
            {
		        xtype      : 'textfield'
		    	,fieldLabel : 'Importe autorizado arancel'
	    		,labelWidth: 150
	    		,allowBlank:false
		    	,name       : 'importeAutorizado'
    		}*/
        ]
    });
    
    function calculaImporteConcepto(){
    	var importe = panelEdicionConceptos.down('[name="params.ptprecio"]').getValue()*1;
    	var cantidad = panelEdicionConceptos.down('[name="params.cantidad"]').getValue()*1;
    	var destopor = panelEdicionConceptos.down('[name="params.destopor"]').getValue()*1;
    	var destoimp = panelEdicionConceptos.down('[name="params.destoimp"]').getValue()*1;
    	
    	panelEdicionConceptos.down('[name="params.ptimport"]').setValue(((cantidad*importe) *(1-(destopor/100)))-destoimp);
    	
    }

	/*PANTALLA EMERGENTE PARA LA INSERCION Y MODIFICACION DE LOS DATOS DEL GRID*/
    var windowConceptos= Ext.create('Ext.window.Window', {
          title: 'Agregar Concepto',
          closeAction: 'hide',
          modal:true,
          items:[panelEdicionConceptos],
          bodyStyle:'padding:15px;',
          buttons:[{
                 text: 'Guardar',
                 icon:_CONTEXT+'/resources/fam3icons/icons/accept.png',
                 handler: function() {
                       if (panelEdicionConceptos.form.isValid()) {
                    	   var record = gridFacturas.getSelectionModel().getSelection()[0];
                    	   debug("CdGarant: "+record.get('CDGARANT'));
                    	   debug("Factura: "+record.get('NFACTURA'));
                    	   
                    	   panelEdicionConceptos.form.submit({
           		        	waitMsg:'Procesando...',			
           		        	params: {
           		        		'params.cdunieco'   : _CDUNIECO,
           		        		'params.cdramo'     : _CDRAMO,
           		        		'params.estado'     : _ESTADO,
           		        		'params.nmpoliza'   : _NMPOLIZA,
           		        		'params.nmsituac'   : _NMSITUAC,
           		        		'params.nmsuplem'   : _NMSUPLEM,
           		        		'params.status'     : _STATUS,
           		        		'params.aaapertu'   : _AAAPERTU,
           		        		'params.nmsinies'   : _NMSINIES,
           		        		'params.nfactura'   : record.get('NFACTURA'),
           		        		'params.cdgarant'   : record.get('CDGARANT'),
           		        		'params.nmordina'   : _Operacion == 'U'? _Nmordina:'',
           		        		'params.operacion'   : _Operacion
           		        	},
           		        	failure: function(form, action) {
           		        		mensajeError("Error al guardar el concepto");
           					},
           					success: function(form, action) {
           						
           						storeConceptos.reload();
           						panelEdicionConceptos.getForm().reset();
                               	windowConceptos.close();
                               	mensajeCorrecto("Aviso","Se ha guardado el concepto.");
           						
           						
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
             },
           {
                 text: 'Cancelar',
                 icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
                 handler: function() {
                	 panelEdicionConceptos.getForm().reset();
                     windowConceptos.close();
                 }
           }
          ]
          });
    
    var windowFacturas= Ext.create('Ext.window.Window', {
           title: 'Agregar Factura',
           bodyStyle:'padding:5px;',
           closeAction: 'hide',
           modal: true,
           items:[panelEdicionFacturas],
           
           buttons:[{
                  text: 'Guardar',
                  icon:_CONTEXT+'/resources/fam3icons/icons/accept.png',
                  handler: function() {
                        if (panelEdicionFacturas.form.isValid()) {
                        	
                        	var UrlFact;
                        	if(_Operacion == 'I'){
                        		UrlFact = _URL_GuardaFactura;
                        	}else {
                        		UrlFact = _URL_ActualizaFactura;
                        	}
                        	//var datos=panelEdicionFacturas.form.getValues();
                        	panelEdicionFacturas.form.submit({
            		        	waitMsg:'Procesando...',	
            		        	url: UrlFact,
            		        	params: {
            		        		'params.ntramite'   : _NTRAMITE,
            		        		'params.cdunieco'  : _CDUNIECO,
            		        		'params.cdramo'    : _CDRAMO,
            		        		'params.estado'    : _ESTADO,
            		        		'params.nmpoliza'  : _NMPOLIZA,
            		        		'params.nmsituac'  : _NMSITUAC,
            		        		'params.nmsuplem'  : _NMSUPLEM,
            		        		'params.status'    : _STATUS,
            		        		'params.aaapertu'  : _AAAPERTU,
            		        		'params.nmsinies'  : _NMSINIES
            		        	},
            		        	failure: function(form, action) {
            		        		mensajeError("Error al guardar la Factura");
            					},
            					success: function(form, action) {
            						
            						storeFacturas.reload();
            						panelEdicionFacturas.getForm().reset();
            						storeConceptos.removeAll();
                                	windowFacturas.close();
                                	mensajeCorrecto("Aviso","Se ha guardado la Factura");
            						
            						
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
              },
            {
                  text: 'Cancelar',
                  icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
                  handler: function() {
                      panelEdicionFacturas.getForm().reset();
                      windowFacturas.close();
                  }
            }
           ]
           });    

	
/*////////////////////////////////////////////////////////////////
////////////////   DECLARACION DE GRID FACTURAS ////////////
///////////////////////////////////////////////////////////////*/
Ext.define('EditorFacturas', {
		extend: 'Ext.grid.Panel',
 		requires: [
	 	'Ext.selection.CellModel',
	 	'Ext.grid.*',
	 	'Ext.data.*',
	 	'Ext.util.*',
	 	'Ext.form.*'
 	],
		selType: 'checkboxmodel',
		title: 'Facturas en Tr&aacute;mite',
		frame: false,
 		initComponent: function(){
 		this.cellEditing = new Ext.grid.plugin.CellEditing({
 		clicksToEdit: 1
 		});

 			Ext.apply(this, {
 			height: 250,
 			plugins: [this.cellEditing],
 			store: storeFacturas,
 			columns: 
 				[{
 					header : 'No. de Factura',
 					dataIndex : 'NFACTURA',
 					width : 150
 				},{
 					header : 'Fecha de Factura',
 					dataIndex : 'FFACTURA',
 					width : 150
 				// , renderer: Ext.util.Format.dateRenderer('d M Y')

 				},{
 					header : 'Cobertura',
 					dataIndex : 'DSGARANT',
 					width : 150
 				},{
 					header : 'Importe',
 					dataIndex : 'PTIMPORT',
 					width : 150,
 					renderer : Ext.util.Format.usMoney
 				},{
 					header : 'Deducible',
 					dataIndex : 'DEDUCIBLE',
 					width : 120
 				},{
 					header : 'Copago',
 					dataIndex : 'COPAGO',
 					width : 120
 				} <s:property value='%{"," + imap.gridColumns}' />
 				,{
 					xtype : 'actioncolumn',
 					width : 50,
 					sortable : false,
 					menuDisabled : true,
 					items : [{
 						icon : _CONTEXT+'/resources/fam3icons/icons/pencil.png',
 						tooltip : 'Editar Factura',
 						scope : this,
 						handler : this.onEditClick
 					},{
 						icon : _CONTEXT+'/resources/fam3icons/icons/delete.png',
 						tooltip : 'Eliminar Factura',
 						scope : this,
 						handler : this.onRemoveClick
 					}]
 				} ],
	 		tbar: [{
			 	icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/add.png',
			 	text: 'Agregar Factura',
			 	scope: this,
			 	hidden: (Ext.isEmpty(_TIPOPAGO) || _TIPOPAGO == _PAGO_DIRECTO),
			 	handler: this.onAddClick
	 		}]
	 	});
			this.callParent();
 	},
 	getColumnIndexes: function () {
	 	var me, columnIndexes;
	 	me = this;
	 	columnIndexes = [];
	 	Ext.Array.each(me.columns, function (column)
	 	{
	 		if (column.getEditor&&Ext.isDefined(column.getEditor())&&column.getEditor().allowBlank==false) {
	 			columnIndexes.push(column.dataIndex);
		 	} else {
		 		columnIndexes.push(undefined);
		 	}
	 	});
	 	return columnIndexes;
 	},
 	validateRow: function (columnIndexes,record, y)
 	//hace que una celda de columna con allowblank=false tenga el estilo rojito
 	{
	 	var view = this.getView();
	 	Ext.each(columnIndexes, function (columnIndex, x)
	 	{
	 		if(columnIndex)
		 	{
		 		var cell=view.getCellByPosition({row: y, column: x});
		 		cellValue=record.get(columnIndex);
			 	if(cell.addCls&&((!cellValue)||(cellValue.lenght==0))){
			 		cell.addCls("custom-x-form-invalid-field");
			 	}
		 	}
	 	});
	 	return false;
 	},
 	onAddClick: function(){	 
 		panelEdicionFacturas.getForm().reset();
 		panelEdicionFacturas.down('[name="params.nfactura"]').setReadOnly(false);
 		panelEdicionFacturas.down('[name="params.cdgarant"]').setReadOnly(false);
 		_Operacion = 'I';
 		
 		windowFacturas.setTitle('Agregar Factura');
 		windowFacturas.show();
 		centrarVentana(windowFacturas);
 		
 	},
 	onRemoveClick: function(grid, rowIndex){
 		if(Ext.isEmpty(_TIPOPAGO) || _TIPOPAGO == _PAGO_DIRECTO){
 			mensajeWarning('No se pueden eliminar Facturas en Pago Directo.');
 			return;
 		}
 		
 		var record=grid.getStore().getAt(rowIndex);
 		
 		panelEdicionFacturas.getForm().reset();
 		
 		centrarVentana(Ext.Msg.show({
	        title: 'Aviso',
	        msg: '&iquest;Esta seguro que desea eliminar esta factura?',
	        buttons: Ext.Msg.YESNO,
	        icon: Ext.Msg.QUESTION,
	        fn: function(buttonId, text, opt){
	        	if(buttonId == 'yes'){
	        		
	        		gridFacturas.setLoading(true);
	         		
	         		Ext.Ajax.request({
	        			url: _URL_EliminaFactura,
	        			params: {
	        		    		'params.ntramite' : _NTRAMITE,
	        		    		'params.nfactura' : record.get('NFACTURA')
	        			},
	        			success: function(response) {
	        				var res = Ext.decode(response.responseText);
	        				gridFacturas.setLoading(false);
	        				
	        				if(res.success){
		        				mensajeCorrecto('Aviso','Se ha eliminado con exito.');
	    	    				storeFacturas.reload();
	        				}else {
	        					mensajeError('No se pudo eliminar.');	
	        				}
	        			},
	        			failure: function(){
	        				gridFacturas.setLoading(false);
	        				mensajeError('No se pudo eliminar.');
	        			}
	        		});
	        	}
	        	
	        }
	    }));
 		
 		
 	},
 	onEditClick: function(grid, rowIndex){
 		var record=grid.getStore().getAt(rowIndex);
 		_Operacion = 'U';
 		debug("Editando...");
 		
 		panelEdicionFacturas.getForm().reset();
 		
 		panelEdicionFacturas.down('[name="params.nfactura"]').setReadOnly(true);
 		panelEdicionFacturas.down('[name="params.cdgarant"]').setReadOnly(true);
 		panelEdicionFacturas.down('[name="params.nfactura"]').setValue(record.get('NFACTURA'));
 		panelEdicionFacturas.down('[name="params.fefactura"]').setValue(record.get('FFACTURA'));
 		panelEdicionFacturas.down('[name="params.cdtipser"]').setValue(record.get('CDTIPSER'));
 		panelEdicionFacturas.down('[name="params.cdpresta"]').setValue(record.get('CDPRESTA'));
 		panelEdicionFacturas.down('[name="params.cdgarant"]').setValue(record.get('CDGARANT'));
 		
 		
 		panelPrincipal.setLoading(true);
 		storeSubcoberturas.load({
			params: {
				'params.cdgarant' : record.get('CDGARANT')
			},
			callback: function (){ 
				panelEdicionFacturas.down('[name="params.cdconval"]').setValue(record.get('CDCONVAL'));
				panelPrincipal.setLoading(false);
				
				windowFacturas.setTitle('Editar Factura');
				windowFacturas.show();
		 		centrarVentana(windowFacturas);
			}
		});
 		panelEdicionFacturas.down('[name="params.ptimport"]').setValue(record.get('PTIMPORT'));
 		panelEdicionFacturas.down('[name="params.descporc"]').setValue(record.get('DESCPORC'));
 		panelEdicionFacturas.down('[name="params.descnume"]').setValue(record.get('DESCNUME'));

 		if(!Ext.isEmpty(panelEdicionFacturas.down('[name="params.autrecla"]'))){
 			panelEdicionFacturas.down('[name="params.autrecla"]').setValue(record.get('AUTRECLA'));
 		}
 		if(!Ext.isEmpty(panelEdicionFacturas.down('[name="params.commenar"]'))){
 			panelEdicionFacturas.down('[name="params.commenar"]').setValue(record.get('COMMENAR'));
 		}
 		if(!Ext.isEmpty(panelEdicionFacturas.down('[name="params.autmedic"]'))){
 			panelEdicionFacturas.down('[name="params.autmedic"]').setValue(record.get('AUTMEDIC'));
 		}
 		if(!Ext.isEmpty(panelEdicionFacturas.down('[name="params.commenme"]'))){
 			panelEdicionFacturas.down('[name="params.commenme"]').setValue(record.get('COMMENME'));
 		}
 		
 	},
 	listeners: {
 		select: function (grid, record, index, opts){
 			debug(record.get('NFACTURA'));
 			storeConceptos.load({
 				params: {
 		    		'params.nfactura'  : record.get('NFACTURA'),
 		    		'params.cdunieco'  : _CDUNIECO,
 		    		'params.cdramo'    : _CDRAMO,
 		    		'params.estado'    : _ESTADO,
 		    		'params.nmpoliza'  : _NMPOLIZA,
 		    		'params.nmsituac'  : _NMSITUAC,
 		    		'params.nmsuplem'  : _NMSUPLEM,
 		    		'params.status'    : _STATUS,
 		    		'params.aaapertu'  : _AAAPERTU,
 		    		'params.nmsinies'  : _NMSINIES
 		    	}
 			});
 		}
 	}
	});

gridFacturas=new EditorFacturas();


/*////////////////////////////////////////////////////////////////
////////////////   DECLARACION DE GRID CONCEPTOS  ////////////
///////////////////////////////////////////////////////////////*/
Ext.define('EditorConceptos', {
		extend: 'Ext.grid.Panel',
 	requires: [
	 	'Ext.selection.CellModel',
	 	'Ext.grid.*',
	 	'Ext.data.*',
	 	'Ext.util.*',
	 	'Ext.form.*'
 	],
		title: 'Conceptos en Factura',
		frame: false,

 	initComponent: function(){
 		this.cellEditing = new Ext.grid.plugin.CellEditing({
 		clicksToEdit: 1
 		});

 			Ext.apply(this, {
 			height: 250,
 			plugins: [this.cellEditing],
 			store: storeConceptos,
 			columns: 
 				[{
 					dataIndex : 'NMORDINA',
 					width : 20
 				},{
 					header : 'Factura',
 					dataIndex:  'NFACTURA',
 					hidden: true
 				},{
 					header : 'Tipo Concepto',
 					dataIndex : 'IDCONCEP',
 					width : 150
 				},{
 					header : 'Codigo Concepto',
 					dataIndex : 'CDCONCEP',
 					width : 150
 				// , renderer: Ext.util.Format.dateRenderer('d M Y')

 				},{
 					header : 'Subcobertura',
 					dataIndex : 'CDCONVAL',
 					width : 150
 				},{
 					header : 'Precio',
 					dataIndex : 'PTPRECIO',
 					width : 150,
 					renderer : Ext.util.Format.usMoney
 				},{
 					header : 'Cantidad',
 					dataIndex : 'CANTIDAD',
 					width : 150
 				},{
 					header : 'Descuento (%)',
 					dataIndex : 'DESTOPOR',
 					width : 150
 				},{
 					header : 'Descuento ($)',
 					dataIndex : 'DESTOIMP',
 					width : 150
 				},{
 					header : 'Subtotal Factura',
 					dataIndex : 'PTIMPORT',
 					width : 150
 				},/*{
 					header : 'Importe autorizado <br/> arancel',
 					dataIndex : 'importeAutorizado',
 					width : 150
 				},*/{
 					xtype : 'actioncolumn',
 					width : 80,
 					sortable : false,
 					menuDisabled : true,
 					items : [<s:property value="imap.conceptosButton" />]
 				} ],
	 		tbar: [{
			 	icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/add.png',
			 	text: 'Agregar concepto',
			 	scope: this,
			 	handler: this.onAddClick
	 		}]
	 	});
			this.callParent();
 	},
 	getColumnIndexes: function () {
	 	var me, columnIndexes;
	 	me = this;
	 	columnIndexes = [];
	 	Ext.Array.each(me.columns, function (column)
	 	{
	 		if (column.getEditor&&Ext.isDefined(column.getEditor())&&column.getEditor().allowBlank==false) {
	 			columnIndexes.push(column.dataIndex);
		 	} else {
		 		columnIndexes.push(undefined);
		 	}
	 	});
	 	return columnIndexes;
 	},
 	validateRow: function (columnIndexes,record, y)
 	//hace que una celda de columna con allowblank=false tenga el estilo rojito
 	{
	 	var view = this.getView();
	 	Ext.each(columnIndexes, function (columnIndex, x)
	 	{
	 		if(columnIndex)
		 	{
		 		var cell=view.getCellByPosition({row: y, column: x});
		 		cellValue=record.get(columnIndex);
			 	if(cell.addCls&&((!cellValue)||(cellValue.lenght==0))){
			 		cell.addCls("custom-x-form-invalid-field");
			 	}
		 	}
	 	});
	 	return false;
 	},
 	onAddClick: function(){
 		if(gridFacturas.getSelectionModel().hasSelection()){
 			
 			panelEdicionConceptos.getForm().reset();
 			panelEdicionConceptos.down('[name="params.cdconval"]').setReadOnly(false);
 			panelEdicionConceptos.down('[name="params.idconcep"]').setReadOnly(false);
 			panelEdicionConceptos.down('[name="params.cdconcep"]').setReadOnly(false);
 			_Operacion = 'I';
 			
 			var record = gridFacturas.getSelectionModel().getSelection()[0];
 			
 			storeSubcoberturasC.load({
    			params: {
    				'params.cdgarant' : record.get('CDGARANT')
    			}
    		});
 			
 			windowConceptos.setTitle('Agregar Concepto');
 			windowConceptos.show();
 			centrarVentana(windowConceptos);
 		}else {
 			mensajeWarning("Debe seleccionar una factura para poder agregar un concepto a la misma.");
 		} 
 	},
 	onEditClick: function(grid, rowIndex){
 		var record=grid.getStore().getAt(rowIndex);
 		_Operacion = 'U';
 		_Nmordina = record.get('NMORDINA');
 		debug("Editando...");
 		
 		panelEdicionConceptos.getForm().reset();
 		panelPrincipal.setLoading(true);
 		
 		storeSubcoberturasC.load({
			params: {
				'params.cdgarant' : record.get('CDGARANT')
			},
			callback: function (){ 
				panelEdicionConceptos.down('[name="params.cdconval"]').setValue(record.get('CDCONVAL'));
			}
		});
 		
 		panelEdicionConceptos.down('[name="params.idconcep"]').setValue(record.get('IDCONCEP'));
 		
 		storeConceptosCatalogo.load({
			params: {
				'params.idPadre' : record.get('IDCONCEP')
			},
			callback: function (){ 
				panelEdicionConceptos.down('[name="params.cdconcep"]').setValue(record.get('CDCONCEP'));
				panelPrincipal.setLoading(false);
				
				windowConceptos.setTitle('Editar Concepto');
				windowConceptos.show();
		 		centrarVentana(windowConceptos);
			}
		});
 		
 		panelEdicionConceptos.down('[name="params.ptprecio"]').setValue(record.get('PTPRECIO'));
 		panelEdicionConceptos.down('[name="params.cantidad"]').setValue(record.get('CANTIDAD'));
 		panelEdicionConceptos.down('[name="params.destopor"]').setValue(record.get('DESTOPOR'));
 		panelEdicionConceptos.down('[name="params.destoimp"]').setValue(record.get('DESTOIMP'));
 		panelEdicionConceptos.down('[name="params.ptimport"]').setValue(record.get('PTIMPORT'));
 		
 		panelEdicionConceptos.down('[name="params.cdconval"]').setReadOnly(true);
 		panelEdicionConceptos.down('[name="params.idconcep"]').setReadOnly(true);
 		panelEdicionConceptos.down('[name="params.cdconcep"]').setReadOnly(true);
 		
 	},
 	onRemoveClick: function(grid, rowIndex){
 		var record=grid.getStore().getAt(rowIndex);
 		_Operacion = 'D';
 		
 		centrarVentana(Ext.Msg.show({
	        title: 'Aviso',
	        msg: '&iquest;Esta seguro que desea eliminar este concepto?',
	        buttons: Ext.Msg.YESNO,
	        icon: Ext.Msg.QUESTION,
	        fn: function(buttonId, text, opt){
	        	if(buttonId == 'yes'){
	        		
	        		gridConceptos.setLoading(true);
	         		
	         		Ext.Ajax.request({
	        			url: _URL_GuardaConcepto,
	        			params         :
	                    {
	                        'params.ntramite'  : _NTRAMITE
	                        ,'params.cdunieco' : _CDUNIECO
	                        ,'params.cdramo'   : _CDRAMO
	                        ,'params.estado'   : _ESTADO
	                        ,'params.nmpoliza' : _NMPOLIZA
	                        ,'params.nmsuplem' : _NMSUPLEM
	                        ,'params.nmsituac' : _NMSITUAC
	                        ,'params.aaapertu' : _AAAPERTU
	                        ,'params.status'   : _STATUS
	                        ,'params.nmsinies' : _NMSINIES
	                        ,'params.nfactura' : record.get('NFACTURA')
	                        ,'params.cdgarant' : record.get('CDGARANT')
	                        ,'params.cdconval' : record.get('CDCONVAL')
	                        ,'params.cdconcep' : record.get('CDCONCEP')
	                        ,'params.idconcep' : record.get('IDCONCEP')
	                        ,'params.nmordina' : record.get('NMORDINA')
	                        ,'params.operacion': _Operacion
	                    },
	        			success: function(response) {
	        				var res = Ext.decode(response.responseText);
	        				gridConceptos.setLoading(false);
	        				
	        				if(res.success){
	        					mensajeCorrecto('Aviso','Se ha eliminado con exito.');
		        				storeConceptos.reload();	
	        				}else {
		        				mensajeError('No se pudo eliminar.');
	        				}
	        			},
	        			failure: function(){
	        				gridConceptos.setLoading(false);
	        				mensajeError('No se pudo eliminar.');
	        			}
	        		});
	        	}
	        	
	        }
	    }));
 		
 		
 	}
	});

gridConceptos=new EditorConceptos();


panelPrincipal = Ext.create('Ext.form.Panel',{
	renderTo: 'maindivAdminData',
	border     : false
	,bodyStyle:'padding:5px;'
	,items      : [
        		gridFacturas,
		        {
			        xtype      : 'textfield'
			    	,fieldLabel : 'Total Facturado'
		    		,labelWidth: 170
		            ,width:500
		            ,allowBlank:false
			    	,name       : 'totalFacturado'
		    		,aling:	'center'
					,padding : 10
				},
				gridConceptos
	]
	});
	
function _mostrarVentanaAjustes(grid,rowIndex,colIndex){
    var record = grid.getStore().getAt(rowIndex);
    var recordFactura = gridFacturas.getSelectionModel().getSelection()[0];
    
    debug("Codigo Garantia: "+recordFactura.get('NFACTURA'));
    
    windowLoader = Ext.create('Ext.window.Window',{
        modal       : true,
        buttonAlign : 'center',
        width       : 650,
        height      : 450,
        autoScroll  : true,
        loader      : {
            url     : _UrlAjustesMedicos,
            params         :
            {
                'params.ntramite'  : _NTRAMITE
                ,'params.cdunieco' : _CDUNIECO
                ,'params.cdramo'   : _CDRAMO
                ,'params.estado'   : _ESTADO
                ,'params.nmpoliza' : _NMPOLIZA
                ,'params.nmsuplem' : _NMSUPLEM
                ,'params.nmsituac' : _NMSITUAC
                ,'params.aaapertu' : _AAAPERTU
                ,'params.status'   : _STATUS
                ,'params.nmsinies' : _NMSINIES
                ,'params.nfactura' : recordFactura.get('NFACTURA')
                ,'params.cdgarant' : record.get('CDGARANT')
                ,'params.cdconval' : record.get('CDCONVAL')
                ,'params.cdconcep' : record.get('CDCONCEP')
                ,'params.idconcep' : record.get('IDCONCEP')
                ,'params.nmordina' : record.get('NMORDINA')
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

});

</script>

<div id="maindivAdminData" style="height:600px;"></div>