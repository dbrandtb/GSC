Ext.require([ 'Ext.form.*', 'Ext.data.*', 'Ext.chart.*', 'Ext.grid.Panel','Ext.layout.container.Column', 'Ext.selection.CheckboxModel' ]);
var datosgrid;
var storeIncisos;

Ext.define('modelClau',
{
    extend:'Ext.data.Model',
    fields:['noFactura','fechaFactura','tipoServicio','proveedor','importe']
});

storeIncisos=new Ext.data.Store(
{
    autoDestroy: true,
    model: 'modelClau'
});


Ext.onReady(function() {

	var storeTipoPago = Ext.create('Ext.data.JsonStore', {
		model:'Generic',
		proxy: {
			type: 'ajax',
			url: _URL_TIPO_PAGO,
			reader: {
				type: 'json',
				root: 'tipoPago'
			}
		}
	});
    storeTipoPago.load();
    
    
    Ext.selection.CheckboxModel.override( {
        mode: 'SINGLE',
        allowDeselect: true
    });

    oficinaReceptora= Ext.create('Ext.form.ComboBox',
  	{
    	id:'oficinaReceptora',	   name:'params.oficinaReceptora',      fieldLabel: 'Oficina receptora',		queryMode:'local',		   
    	displayField: 'value',	   valueField: 'key',					allowBlank:false,				        editable:false,
    	labelWidth : 250,		   emptyText:'Seleccione...'
   		,store : Ext.create('Ext.data.Store', {
   			model:'Generic',
   			autoLoad:true,
   			proxy:
   			{
   				type: 'ajax',
   				url:mesConUrlLoadCatalo,
   				extraParams : {catalogo:_CATALOGO_OFICINA_RECEP},
   				reader:
   				{
   					type: 'json',
   					root: 'lista'
   				}
   			}
   		})
   	});
    
    oficinaEmisora= Ext.create('Ext.form.ComboBox',
	{
    	id:'oficinaEmisora',		name:'params.oficinaEmisora',		fieldLabel: 'Oficina emisora',			queryMode:'local',
	    displayField: 'value',	    valueField: 'key',				    allowBlank:false,					    editable:false,
	    labelWidth : 250,		    emptyText:'Seleccione...'
	    ,store : Ext.create('Ext.data.Store', {
			model:'Generic',
			autoLoad:true,
			proxy:
			{
				type: 'ajax',
				url:mesConUrlLoadCatalo,
				extraParams : {catalogo:_CATALOGO_OFICINA_RECEP},
				//extraParams : {catalogo:'<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@MC_TIPOS_TRAMITE"/>'},
				reader:
				{
					type: 'json',
					root: 'lista'
				}
			}
		})
	    
	});
     
    tipoPago= Ext.create('Ext.form.ComboBox',
    {
        id:'tipoPago',			   name:'params.tipoPago',		        fieldLabel: 'Tipo pago',				queryMode:'local',
        displayField: 'value',	   valueField: 'key',					allowBlank:false,						editable:false,
        labelWidth : 250,		   emptyText:'Seleccione...',			store: storeTipoPago
       ,
        listeners : {
			'select' : function(combo, record) {
					closedStatusSelectedID = this.getValue();
					if(closedStatusSelectedID ==1){
						//PAGO DIRECTO
						Ext.getCmp('beneficiario').hide();
						Ext.getCmp('editorIncisos').hide();
						Ext.getCmp('proveedor').show();
						Ext.getCmp('txtNoFactura').show();
						Ext.getCmp('txtImporte').show();
						Ext.getCmp('fechaFactura').show();
						
					}else{
						//PAGO POR REEMBOLSO
						Ext.getCmp('beneficiario').show();
						Ext.getCmp('editorIncisos').show();
						Ext.getCmp('proveedor').hide();
						Ext.getCmp('txtNoFactura').hide();
						Ext.getCmp('txtImporte').hide();
						Ext.getCmp('fechaFactura').hide();
						
					}    					
				}
			}
    });
    
    aseguradoAfectado= Ext.create('Ext.form.ComboBox',
    {
        id:'aseguradoAfectado',		name:'params.aseguradoAfectado',	fieldLabel: 'Asegurado afectado',		queryMode:'local',
        displayField: 'value',      valueField: 'key',        			allowBlank:false,        				editable:false,        
        labelWidth : 250,        	emptyText:'Seleccione...'        	//store: storeCirculoHospitalario
    });
    
    beneficiario= Ext.create('Ext.form.ComboBox',
	{
        id:'beneficiario',        name:'params.beneficiario',        	fieldLabel: 'Beneficiario',        queryMode:'local',        
        displayField: 'value',    valueField: 'key',        			allowBlank:false,        				editable:false,        
        labelWidth : 250,         emptyText:'Seleccione...'//,       		store: storePenalizacion
        
    });
    
    proveedor= Ext.create('Ext.form.ComboBox',
    {
        id:'proveedor',        	  name:'params.proveedor',        		fieldLabel: 'Proveedor',       	queryMode:'local',        
        displayField: 'value',    valueField: 'key',        			allowBlank:false,        				editable:false,        
        labelWidth : 250,        emptyText:'Seleccione...'        		//store: storeCirculoHospitalario
    });
    
    
    var panelModificacionInsercion= Ext.create('Ext.form.Panel',{
        border  : 0
        ,bodyStyle:'padding:5px;'
        ,items :
        [   
             {
		    	id:'noFactInterno'
		        ,xtype      : 'textfield'
		    	,fieldLabel : 'No. Factura'
	    		,labelWidth: 170
	    		,allowBlank:false
		    	,name       : 'noFactInterno'
    		},            
    		{
    	        id: 'fechaFactInterno',
    	        name: 'fechaFactInterno',
    	        fieldLabel: 'Fecha Factura',
    	        xtype: 'datefield',
    	        format: 'd/m/Y',
    	        editable: true,
    	        allowBlank:false,
    	        labelWidth : 170,
    	        value:new Date()
    	    }
            ,
            {
		    	id:'tipoServicioInterno'
		        ,xtype      : 'textfield'
		    	,fieldLabel : 'Tipo de Servicio'
	    		,labelWidth: 170
                ,width:500
                ,allowBlank:false
		    	,name       : 'tipoServicioInterno'
    		}
            ,
            	//proveedor2
           	{
		    	id:'proveedorInterno'
		        ,xtype      : 'textfield'
		    	,fieldLabel : 'Proveedor'
	    		,labelWidth: 170
                ,width:500
                ,allowBlank:false
		    	,name       : 'proveedorInterno'
    		}
            ,
            {
		    	id:'importeInterno'
		        ,xtype      : 'textfield'
		    	,fieldLabel : 'Importe'
	    		,labelWidth: 170
                ,width:500
                ,allowBlank:false
		    	,name       : 'importeInterno'
    		}
        ]
    });
    /*PANTALLA EMERGENTE PARA LA INSERCION Y MODIFICACION DE LOS DATOS DEL GRID*/
    ventanaGrid= Ext.create('Ext.window.Window', {
         renderTo: document.body,
           title: 'NUEVAS FACTURAS',
           height: 230,
           closeAction: 'hide',
           items:[panelModificacionInsercion],
           
           buttons:[{
                  text: 'Aceptar',
                  icon:_CONTEXT+'/resources/fam3icons/icons/accept.png',
                  handler: function() {
                        if (panelModificacionInsercion.form.isValid()) {
                        	
                        	var datos=panelModificacionInsercion.form.getValues();
                        	console.log(datos);
                        	var rec = new modelClau({
        	 				  	noFactura: datos.noFactInterno,
        					 	fechaFactura: datos.fechaFactInterno,
        					 	tipoServicio: datos.tipoServicioInterno,
        					 	proveedor: datos.proveedorInterno,
        					 	importe: datos.importeInterno            	
		        	 		});
                        	storeIncisos.add(rec);
                        	panelModificacionInsercion.getForm().reset();
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
                       //Ext.getCmp('btnGuardaRegistro').disable();
                      panelModificacionInsercion.getForm().reset();
                      ventanaGrid.close();
                  }
            }
           ]
           });
    
    /*////////////////////////////////////////////////////////////////
    ////////////////   DECLARACION DE EDITOR DE INCISOS  ////////////
    ///////////////////////////////////////////////////////////////*/
    Ext.define('EditorIncisos', {
 		extend: 'Ext.grid.Panel',
	 	requires: [
		 	'Ext.selection.CellModel',
		 	'Ext.grid.*',
		 	'Ext.data.*',
		 	'Ext.util.*',
		 	'Ext.form.*'
	 	],
 		xtype: 'cell-editing',
		id:'editorIncisos',
 		title: 'Facturas en Tr&aacute;mite',
 		frame: false,

	 	initComponent: function(){
	 		this.cellEditing = new Ext.grid.plugin.CellEditing({
	 		clicksToEdit: 1
	 		});

	 			Ext.apply(this, {
	 			width: 750,
	 			height: 200,
	 			plugins: [this.cellEditing],
	 			store: storeIncisos,
	 			columns: 
	 			[
				 	{	
				 		header: 'No. de Factura',			dataIndex: 'noFactura',			flex:2
				 	},
				 	{
				 		header: 'Fecha de Factura',			dataIndex: 'fechaFactura',		flex:2//,			 	renderer: Ext.util.Format.dateRenderer('d M Y')
				 		
				 	},
				 	{
					 	header: 'Tipo de Servicio',			dataIndex: 'tipoServicio',	 	flex:2	
				 	},
				 	{
					 	header: 'Proveedor (Reembolso)',	dataIndex: 'proveedor',			flex:2
				 	},
				 	{
					 	header: 'Importe', 					dataIndex: 'importe',		 	flex:2,				renderer: Ext.util.Format.usMoney
				 	},
				 	{
					 	xtype: 'actioncolumn',
					 	width: 30,
					 	sortable: false,
					 	menuDisabled: true,
					 	items: [{
					 		icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/delete.png',
					 		tooltip: 'Quitar inciso',
					 		scope: this,
					 		handler: this.onRemoveClick
				 		}]
				 	}
		 		],
		 		selModel: {
			 		selType: 'cellmodel'
			 	},
		 		tbar: [{
				 	icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/add.png',
				 	text: 'Agregar Factura',
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
	 		ventanaGrid.show();
	 	},
	 	onRemoveClick: function(grid, rowIndex){
	 		var record=this.getStore().getAt(rowIndex);
	 		console.log(record);        	
	 		this.getStore().removeAt(rowIndex);
	 	}
 	});
    gridIncisos=new EditorIncisos();
    mesConStoreUniAdmin=[];
    
    
    
    
    
    
    
    Ext.create('Ext.form.Panel',
    	    {
    	        border    : 0
    	        ,title: 'Facturas en Tr&aacute;mite'
    	        ,renderTo : 'div_clau'
	        	,bodyPadding: 10
	            ,width: 800
    	        ,items    :
    	        [
    	            {
            			id:'txtContraRecibo'
		                ,xtype      : 'textfield'
		            	,fieldLabel : 'Contra recibo'
		            	,readOnly   : true
		            	,labelWidth : 250
		            	,name       : 'params.contraRecibo'
		            },
		            {
		                id: 'txtEstado',
		                name: 'params.estado',
		                xtype: 'textfield',
		                fieldLabel: 'Estado',
		               labelWidth: 250
		            },
		            oficinaReceptora
		            ,
            		oficinaEmisora
	            	,
	            	{
		            	id:'params.fechaRecepcion'
		                ,xtype      : 'textfield'
		            	,fieldLabel : 'Fecha recepci&oacute;n'
		            	,readOnly   : true
		            	,labelWidth : 250
		            	,name       : 'params.fechaRecepcion'
		            },
	        		tipoPago
        			,
            		aseguradoAfectado
	            	,
            		beneficiario
	            	,
	            	proveedor
	        	    ,
	        	    {
		            	id:'txtNoFactura'
		                ,xtype      : 'textfield'
		            	,fieldLabel : 'No. Factura'
		            	,readOnly   : true
		            	,labelWidth : 250
		            	,name       : 'params.noFactura'
		            }
		            ,
                	{
		            	id:'txtImporte'
		                ,xtype      : 'textfield'
		            	,fieldLabel : 'Importe'
		            	,readOnly   : true
		            	,labelWidth : 250
		            	,name       : 'params.importe'
		            },
		            {
                        id: 'fechaFactura',
                        name: 'params.fechaFactura',
                        fieldLabel: 'Fecha Factura',
                        xtype: 'datefield',
                        format: 'd/m/Y',
                        editable: true,
                        allowBlank:false,
                        labelWidth : 250,
                        value:new Date()
                    },
                    gridIncisos
    	        ],
    	        buttonAlign:'center',
    	        buttons: [{
    	            id:'botonCotizar',
    	            icon:_CONTEXT+'/resources/fam3icons/icons/calculator.png',
    	            //text: hayTramiteCargado?'Precaptura':'Cotizar',
    	            text: 'Generar Tramite',
            		handler: function() {
            			
            			var form = this.up('form').getForm();
            			if (form.isValid())
    	                {
            				Ext.Msg.show({
    	                    	title:'Exito',
    	                    	msg: 'Se contemplaron todo',
    	                    	buttons: Ext.Msg.OK,
    	                    	icon: Ext.Msg.WARNING
    	                	});
    	                }
            			else
        				{
            				var incisosRecords = storeIncisos.getRange();
            				console.log(incisosRecords.length);
            				
            				var incisosJson = [];
            				storeIncisos.each(function(record,index){
	                        	if(record.get('nombre')
	                        			&&record.get('nombre').length>0)
                        		{
	                        		nombres++;
                        		}
	                            incisosJson.push({
	                            	noFactura: record.get('noFactura'),
	                            	fechaFactura: record.get('fechaFactura'),
	                            	tipoServicio: record.get('tipoServicio'),
	                            	proveedor: record.get('proveedor'),
	                            	importe: record.get('importe')
	                            });
                            });
            				
            				console.log('---- VALOR DE IncisosJson ---- ');
            				console.log(incisosJson);
            				
            				var submitValues=form.getValues();
                        	submitValues['incisos']=incisosJson;
                        	console.log('---- VALOR DE submitValues ---- ');
            				console.log(submitValues);
            				
            				Ext.Msg.show({
    	                    	title:'Datos incompletos',
    	                    	msg: 'Favor de introducir todos los campos requeridos',
    	                    	buttons: Ext.Msg.OK,
    	                    	icon: Ext.Msg.WARNING
    	                	});
        				}
    	            }
    	        },
    	        {
    	            text:'Limpiar',
    	            icon:_CONTEXT+'/resources/fam3icons/icons/arrow_refresh.png',
    	            id:'botonLimpiar',
    	            handler:function()
    	            {}
    	        }
    	    ]
    	    });
});