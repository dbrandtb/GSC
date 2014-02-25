Ext.require([ 'Ext.form.*', 'Ext.data.*', 'Ext.chart.*', 'Ext.grid.Panel','Ext.layout.container.Column', 'Ext.selection.CheckboxModel' ]);
var datosgrid;
var storeIncisos;

Ext.define('modelClau',
{
    extend:'Ext.data.Model',
    fields:['noFactura','fechaFactura','tipoServicio','proveedor','importe']
});

Ext.define('modelDetalle',
{
    extend:'Ext.data.Model',
    fields:['concepto','ub','subcobertura','importe','descuento','subtotalfactura','importeAutorizado']
});

storeIncisos=new Ext.data.Store(
{
    autoDestroy: true,
    model: 'modelClau'
});

storeDetalle=new Ext.data.Store(
		{
		    autoDestroy: true,
		    model: 'modelDetalle'
		});


Ext.onReady(function() {

	oficinaEmisora= Ext.create('Ext.form.ComboBox',
	{
		id:'oficinaEmisora',
		name:'params.oficinaEmisora',
		fieldLabel: 'Oficina emisora',
		//store: storeCirculoHospitalario,
		colspan:2,
		queryMode:'local',
		displayField: 'value',
		valueField: 'key',
		allowBlank:false,
		editable:false,
		labelWidth : 250,
		emptyText:'Seleccione...'
	});
        
    oficinaDocumento= Ext.create('Ext.form.ComboBox',
    {
        id:'oficinaDocumento',
        name:'params.oficinaDocumento',
        fieldLabel: 'Oficina documento',
        colspan:2,
        //store: storeCirculoHospitalario,
        queryMode:'local',
        displayField: 'value',
        valueField: 'key',
        allowBlank:false,
        editable:false,
        labelWidth : 250,
        emptyText:'Seleccione...'
    });
    
    origenSiniestro= Ext.create('Ext.form.ComboBox',
	{
		id:'origenSiniestro',
		name:'params.origenSiniestro',
		fieldLabel: 'Origen siniestro',
		//store: storeCirculoHospitalario,
		colspan:2,
		queryMode:'local',
		displayField: 'value',
		valueField: 'key',
		allowBlank:false,
		editable:false,
		labelWidth : 250,
		emptyText:'Seleccione...'
	});
    
	tipoPago= Ext.create('Ext.form.ComboBox',
    {
		colspan:2,
		id:'tipoPago',
	    name:'params.tipoPago',
	    fieldLabel: 'Tipo pago',
	    //store: storeCirculoHospitalario,
	    queryMode:'local',
	    displayField: 'value',
	    valueField: 'key',
	    allowBlank:false,
	    editable:false,
	    labelWidth : 250,
	    emptyText:'Seleccione...'
	});
        
    aseguradoAfectado= Ext.create('Ext.form.ComboBox',
    {
    	colspan:2,
    	id:'aseguradoAfectado',
        name:'params.aseguradoAfectado',
        fieldLabel: 'Asegurado afectado',
        //store: storeCirculoHospitalario,
        queryMode:'local',
        displayField: 'value',
        valueField: 'key',
        allowBlank:false,
        editable:false,
        labelWidth : 250,
        emptyText:'Seleccione...'
    });
        
    beneficiario= Ext.create('Ext.form.ComboBox',
    {
    	colspan:2,
    	id:'beneficiario',
        name:'params.beneficiario',
        fieldLabel: '*(R) Beneficiario',
        //store: storeCirculoHospitalario,
        queryMode:'local',
        displayField: 'value',
        valueField: 'key',
        allowBlank:false,
        editable:false,
        labelWidth : 250,
        emptyText:'Seleccione...'
    });
        
    proveedor= Ext.create('Ext.form.ComboBox',
    {
    	id:'proveedor',
        name:'params.proveedor',
        fieldLabel: 'Proveedor',
        //store: storeCirculoHospitalario,
        queryMode:'local',
        displayField: 'value',
        valueField: 'key',
        allowBlank:false,
        editable:false,
        labelWidth : 250,
        emptyText:'Seleccione...'
    });
    
    icd= Ext.create('Ext.form.ComboBox',
    {
    	colspan:2,
    	id:'icd',
        name:'params.icd',
        fieldLabel: 'ICD',
        //store: storeCirculoHospitalario,
        queryMode:'local',
        displayField: 'value',
        valueField: 'key',
        allowBlank:false,
        editable:false,
        labelWidth : 250,
        emptyText:'Seleccione...'
    });
 
    icdSecundario= Ext.create('Ext.form.ComboBox',
    {
    	colspan:2,
    	id:'icdSecundario',
        name:'params.icdSecundario',
        fieldLabel: 'ICD secundario',
        //store: storeCirculoHospitalario,
        queryMode:'local',
        displayField: 'value',
        valueField: 'key',
        allowBlank:false,
        editable:false,
        labelWidth : 250,
        emptyText:'Seleccione...'
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
    
    
    var panelInsercionDetalle= Ext.create('Ext.form.Panel',{
        border  : 0
        ,bodyStyle:'padding:5px;'
        ,items :
        [   
             {
		    	id:'nombreConcepto'
		        ,xtype      : 'textfield'
		    	,fieldLabel : 'Concepto'
	    		,labelWidth: 170
	    		,allowBlank:false
		    	,name       : 'nombreConcepto'
    		}
            ,
            {
		    	id:'ub'
		        ,xtype      : 'textfield'
		    	,fieldLabel : 'UB'
	    		,labelWidth: 170
	    		,allowBlank:false
		    	,name       : 'ub'
    		}
            ,
            {
		    	id:'subcobertura'
		        ,xtype      : 'textfield'
		    	,fieldLabel : 'Subcobertura'
	    		,labelWidth: 170
	    		,allowBlank:false
		    	,name       : 'subcobertura'
    		}
            ,
            {
		    	id:'importe'
		        ,xtype      : 'textfield'
		    	,fieldLabel : 'importe'
	    		,labelWidth: 170
	    		,allowBlank:false
		    	,name       : 'importe'
    		}
            ,
            {
		    	id:'porcentajeDescuento'
		        ,xtype      : 'textfield'
		    	,fieldLabel : 'Descuento (%)'
	    		,labelWidth: 170
	    		,allowBlank:false
		    	,name       : 'porcentajeDescuento'
    		}
            ,
            {
		    	id:'subtotalFactura'
		        ,xtype      : 'textfield'
		    	,fieldLabel : 'Subtotal factura'
	    		,labelWidth: 170
	    		,allowBlank:false
		    	,name       : 'subtotalFactura'
    		}
            ,
            {
		    	id:'importeAutorizado'
		        ,xtype      : 'textfield'
		    	,fieldLabel : 'Importe autorizado arancel'
	    		,labelWidth: 170
	    		,allowBlank:false
		    	,name       : 'importeAutorizado'
    		}
        ]
    });
    
    /*PANTALLA EMERGENTE PARA LA INSERCION Y MODIFICACION DE LOS DATOS DEL GRID*/
    ventanaGrid2= Ext.create('Ext.window.Window', {
        renderTo: document.body,
          title: 'NUEVAS FACTURAS',
          height: 285,
          closeAction: 'hide',
          items:[panelInsercionDetalle],
          
          buttons:[{
                 text: 'Aceptar',
                 icon:_CONTEXT+'/resources/fam3icons/icons/accept.png',
                 handler: function() {
                       if (panelInsercionDetalle.form.isValid()) {
                       	
                       	var datos=panelInsercionDetalle.form.getValues();
                       	console.log(datos);
                       	var rec = new modelDetalle({
                       		concepto: datos.nombreConcepto,
                       		ub: datos.ub,
                       		subcobertura: datos.subcobertura,
                       		importe: datos.importe,
                       		descuento: datos.porcentajeDescuento,        	
                       		subtotalfactura: datos.subtotalFactura,
                       		importeAutorizado: datos.importeAutorizado,
		        	 		});
                       	storeDetalle.add(rec);
                       	panelInsercionDetalle.getForm().reset();
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
                	 panelInsercionDetalle.getForm().reset();
                     ventanaGrid.close();
                 }
           }
          ]
          });
    
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
        
    informacionGral= Ext.create('Ext.form.Panel',
    {
        border    : 0
        //,title: 'Facturas en Tr&aacute;mite'
        //,renderTo : 'div_clau'
    	,bodyPadding: 10
        ,width: 900
        ,layout     :
    	{
    		type     : 'table'
    		,columns : 2
    	}
        ,defaults   :
        {
        	style : 'margin:5px;'
        }
        ,
        items    :
        [
            {
            	id:'txtContraRecibo'			,xtype      : 'textfield'           		,fieldLabel : 'Contra recibo',
            	colspan:2						,labelWidth : 250				,name       : 'params.contraRecibo'
            },
            {
                id: 'txtEstado'					,xtype		: 'textfield'					,fieldLabel : 'Estado',
                colspan:2						,labelWidth	: 250				,name		: 'params.estado'
            },            
            	oficinaEmisora
            ,
            	oficinaDocumento
        	,
        	{
            	id:	'params.fechaRecepcion'	    ,xtype      : 'textfield'            		,fieldLabel : 'Fecha recepci&oacute;n',
            	colspan:2						,labelWidth : 250            	,name       : 'params.fechaRecepcion'
            }
            ,
            {
                id: 'fechaOcurrencia'			,xtype		: 'datefield'					,fieldLabel	: 'Fecha ocurrencia',
                labelWidth : 250				,name		: 'params.fechaOcurrencia'		,format		: 'd/m/Y',
                colspan:2						,editable: true					,value		:new Date()
            },
            origenSiniestro
            ,
            {
                id: 'txtPlan'					,xtype		: 'textfield'					,fieldLabel	: 'Plan',
                colspan:2						,labelWidth: 250					,name		: 'params.plan'
            },
            {
                id: 'txtCirculoHospitalario'	,xtype		: 'textfield'					,fieldLabel : 'Circulo hospitalario',
                colspan:2						,labelWidth: 250					,name		: 'params.circuloHospitalario'
            },
            {
                id: 'txtZonaTarificacion'		,xtype		: 'textfield'					,fieldLabel : 'Zona tarificaci&oacute;n',
                colspan:2						,labelWidth: 250					,name		: 'params.zonaTarificacion'
            },
            {
                id: 'txtSumAseguradaContratada'	,xtype		: 'textfield'					,fieldLabel	: 'Suma asegurada contratada',
                colspan:2						,labelWidth: 250					,name		: 'params.sumAseguradaContr'
        	},
            	tipoPago
        	,
        	{
                id: 'txtPoliza'					,xtype		: 'textfield'					,fieldLabel	: 'P&oacute;liza',
                labelWidth: 250					,name		: 'params.poliza'
                
            },
            Ext.create('Ext.Button', {
				text: 'Ver detalle p&oacute;liza',
				width : 180,
				icon : _CONTEXT + '/resources/fam3icons/icons/application_view_list.png'
			})
			,
            {
                id: 'txtSumaDisponible'			,xtype		: 'textfield'					,fieldLabel	: 'Suma asegurada disponible',
                labelWidth: 250					,name		: 'params.sumaDisponible'
                
            },
            Ext.create('Ext.Button', {
				text: 'Ver coberturas',
				width : 180,
				icon : _CONTEXT + '/resources/fam3icons/icons/application_view_list.png'
			})
			,
            {
            	id: 'fechaInicioVigencia'		,xtype      : 'textfield'					,fieldLabel : 'Inicio vigencia',
            	labelWidth : 250				,name       : 'params.fechaInicioVigencia'
            }
            ,
            Ext.create('Ext.Button', {
				text: 'Ver historial de reclamaci&oacute;n',
				width : 180,
				icon : _CONTEXT + '/resources/fam3icons/icons/application_view_list.png'
			})
			,
            {
            	id:'fechaFinVigencia',xtype      : 'textfield',fieldLabel : 'Fin vigencia',labelWidth : 250	,name       : 'params.fechaFinVigencia'
            }
            ,
            Ext.create('Ext.Button', {
				text: 'Ver exclusiones p&oacute;liza',
				width : 180,
				icon : _CONTEXT + '/resources/fam3icons/icons/application_view_list.png'
			})
			,
            {
            	id:'estatusPoliza',xtype      : 'textfield',fieldLabel : 'Estatus p&oacute;liza',labelWidth : 250,name       : 'params.estatusPoliza'
            }
            ,
            Ext.create('Ext.Button', {
				text: 'Ver historial rehabilitaciones',
				width : 180,
				icon : _CONTEXT + '/resources/fam3icons/icons/application_view_list.png'
			})
			,
            {
            	id:'fechaAntiguedad',xtype      : 'textfield'
            	,fieldLabel : 'Reconocimiento de Antig&uuml;edad'
            	,readOnly   : true
            	,labelWidth : 250
            	,name       : 'params.fechaAntiguedad'
        		,colspan	: 2
            }
            ,
            {
            	id:'fechaAntiguedadGSS'
                ,xtype      : 'textfield'
            	,fieldLabel : 'Antig&uuml;edad con General de Salud'
            	,readOnly   : true
            	,labelWidth : 250
            	,name       : 'params.fechaAntiguedadGSS'
            }
            ,
            {
            	id:'tiempoAntiguedadGSS'
                ,xtype      : 'textfield'
            	//,fieldLabel : 'Antig&uuml;edad con General de Salud'
            	,readOnly   : true
            	,labelWidth : 250
            	,name       : 'params.tiempoAntiguedadGSS'
            }
            ,
            {
            	id:'formaPagoPoliza'
                ,xtype      : 'textfield'
            	,fieldLabel : 'Forma de pago de la p&oacute;liza'
            	,readOnly   : true
            	,labelWidth : 250
            	,name       : 'params.formaPagoPoliza'
        		,colspan	: 2
            }
            ,
        		aseguradoAfectado
        	,
        		/*beneficiario
        	,*/
        	    proveedor
    	    ,
    	    {
            	id:'circuloHospitalario'
                ,xtype      : 'textfield'
            	,fieldLabel : 'Circulo hospitalario'
            	,readOnly   : true
            	,labelWidth : 150
            	,name       : 'params.circuloHospitalario'
            }
            ,
            	icd
            ,
            	icdSecundario
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
				 		header: 'No. de Factura',			dataIndex: 'noFactura',			width           : 150
				 	},
				 	{
				 		header: 'Fecha de Factura',			dataIndex: 'fechaFactura',		width           : 150 //,			 	renderer: Ext.util.Format.dateRenderer('d M Y')
				 		
				 	},
				 	{
					 	header: 'Cobertura',			dataIndex: 'tipoServicio',			width           : 150
				 	},
				 	{
					 	header: 'Importe', 					dataIndex: 'importe',			width           : 150,				renderer: Ext.util.Format.usMoney
				 	},
				 	{
					 	header: 'Deducible',	dataIndex: 'proveedor',			width           : 150
				 	},
				 	{
					 	header: 'Copago',	dataIndex: 'proveedor',			width           : 150
				 	},
				 	{
					 	header: 'Autorizado AR',	dataIndex: 'proveedor',			width           : 150
				 	},
				 	{
					 	header: 'Observaciones AR',	dataIndex: 'proveedor',			width           : 150
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
	 		
	 		/*window.parent.scrollTo(0,600);
	 		// Create a model instance
	 		var rec = new modelClau({
	 				  	noFactura: '1233',
					 	fechaFactura: '12/12/2012',
					 	tipoServicio: 'CONSULTA',
					 	proveedor: 'MATRIZ',
					 	importe: '1245678.00'            	
	 		});
	 		this.getStore().add(rec);
		 	//para acomodarse en la primer celda para editar
		 	this.cellEditing.startEditByPosition({
		 		row: storeIncisos.getRange().length-1, 
		 		column: 0
		 	});*/
	 	},
	 	onRemoveClick: function(grid, rowIndex){
	 		var record=this.getStore().getAt(rowIndex);
	 		console.log(record);        	
	 		this.getStore().removeAt(rowIndex);
	 	}
 	});
    
    gridIncisos=new EditorIncisos();
    
    
    /*////////////////////////////////////////////////////////////////
    ////////////////   DECLARACION DE EDITOR DE INCISOS  ////////////
    ///////////////////////////////////////////////////////////////*/
    Ext.define('EditorIncisos2', {
 		extend: 'Ext.grid.Panel',
	 	requires: [
		 	'Ext.selection.CellModel',
		 	'Ext.grid.*',
		 	'Ext.data.*',
		 	'Ext.util.*',
		 	'Ext.form.*'
	 	],
 		xtype: 'cell-editing',

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
	 			store: storeDetalle,
	 			columns: 
	 			[
				 	{	
				 		header: 'Concepto',			dataIndex: 'concepto',			width           : 150
				 	},
				 	{
				 		header: 'UB',			dataIndex: 'ub',		width           : 150 //,			 	renderer: Ext.util.Format.dateRenderer('d M Y')
				 		
				 	},
				 	{
					 	header: 'Subcobertura',			dataIndex: 'subcobertura',			width           : 150
				 	},
				 	{
					 	header: 'Importe', 					dataIndex: 'importe',			width           : 150,				renderer: Ext.util.Format.usMoney
				 	},
				 	{
					 	header: 'Descuento (%)',	dataIndex: 'descuento',			width           : 150
				 	},
				 	{
					 	header: 'Subtotal Factura',	dataIndex: 'subtotalfactura',			width           : 150
				 	},
				 	{
					 	header: 'Importe autorizado <br/> arancel',	dataIndex: 'importeAutorizado',			width           : 150
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
				 	text: 'Agregar detalle',
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
	 		ventanaGrid2.show();
	 		
	 		/*window.parent.scrollTo(0,600);
	 		// Create a model instance
	 		var rec = new modelClau({
	 				  	noFactura: '1233',
					 	fechaFactura: '12/12/2012',
					 	tipoServicio: 'CONSULTA',
					 	proveedor: 'MATRIZ',
					 	importe: '1245678.00'            	
	 		});
	 		this.getStore().add(rec);
		 	//para acomodarse en la primer celda para editar
		 	this.cellEditing.startEditByPosition({
		 		row: storeIncisos.getRange().length-1, 
		 		column: 0
		 	});*/
	 	},
	 	onRemoveClick: function(grid, rowIndex){
	 		var record=this.getStore().getAt(rowIndex);
	 		console.log(record);        	
	 		this.getStore().removeAt(rowIndex);
	 	}
 	});
    
    gridIncisos2=new EditorIncisos2();
    
    tramite= Ext.create('Ext.form.Panel',{
		id          : 'altaTramiteSiniestros',
    	buttonAlign : 'center'
    	//,url        : mesConUrlSaveTra
    	,border     : 0
    	,defaults   :
        {
        	style : 'margin:5px;'
        }
    	,items      :
    	[
            gridIncisos,
            {
		    	id:'totalFacturado'
		        ,xtype      : 'textfield'
		    	,fieldLabel : 'Total Facturado'
	    		,labelWidth: 170
                ,width:500
                ,allowBlank:false
		    	,name       : 'totalFacturado'
	    		,aling:	'center'
    			,padding : 10
    		},
    		gridIncisos2
    	],
        buttonAlign:'center',
        buttons: [
                 {
	            id:'botonCotizar',
	            icon:_CONTEXT+'/resources/fam3icons/icons/calculator.png',
	            //text: hayTramiteCargado?'Precaptura':'Cotizar',
	            text: 'Generar Tramite',
        		handler: function(btn,e) {
        			console.log(btn);
        			console.log(e);
        			
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
        			else {
	    				Ext.Msg.show({
	    					title: 'Aviso',
        		            msg: 'Complete la informaci&oacute;n requerida',
        		            buttons: Ext.Msg.OK,
        		            animateTarget: btn,
        		            icon: Ext.Msg.WARNING
	    				});
	    			}
        			
        			/*else
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
    				}*/
	            }
	        },
	        {
	        	text: 'Cancelar',
                icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
                handler: function() {
                	menu2.close();
                }
	        }
		]
   	});

	Ext.create('Ext.tab.Panel', {
    	    //width: 800,
    	    //height: 400,
    	    renderTo: document.body,
    	    border : false,
    	    items: [{
    	        title: 'Informaci&oacute;n General',
    	        	defaults : {
	    	            bodyPadding : 5,
	    	            border : false
    	        	},
    	        	items: [informacionGral]    	        
	    	    }, {
	    	        title: 'Revisi&oacute;n Administrativa',
	    	        defaults : {
	    	            bodyPadding : 5,
	    	            border : false
    	        	},
    	        	items: [tramite]
	    	    }, {
	    	        title: 'C&aacute;lculos',
	    	        loader: {
	    	        	url: _UrlPanelCalculos,
	    	        	scripts: true,
	    	        	autoLoad: false,
	    	        	ajaxOptions: {
	    	        		method: 'POST'
	    	        	}
	    	        },
	                listeners : {
	                    activate : function(tab) {
	                        tab.loader.load();
	                    }
	                }
	    	    }]
    	});

});