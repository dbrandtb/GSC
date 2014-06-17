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


Ext.define('modelListadoProvMedico',{
    extend: 'Ext.data.Model',
    fields: [
             	{type:'string',    name:'cdpresta'},		{type:'string',    name:'nombre'},		{type:'string',    name:'cdespeci'},		{type:'string',    name:'descesp'}
			]
});

Ext.define('modelListadoPoliza',{
    extend: 'Ext.data.Model',
    fields: [
             	{type:'string',    name:'cdramo'},		{type:'string',    name:'cdunieco'},		{type:'string',    name:'estado'},
				{type:'string',    name:'nmpoliza'},	{type:'string',    name:'nmsituac'}
			]
});


Ext.onReady(function() {

	var storeTipoAtencion = Ext.create('Ext.data.JsonStore', {
		model:'Generic',
		proxy: {
			type: 'ajax',
			url: mesConUrlLoadCatalo,
			extraParams : {catalogo:_CATALOGO_TipoAtencion},
			reader: {
				type: 'json',
				root: 'lista'
			}
		}
	});
	storeTipoAtencion.load();
    
	
	storeAsegurados = Ext.create('Ext.data.Store', {
        model:'Generic',
        autoLoad:false,
        proxy: {
            type: 'ajax',
            url : _URL_LISTADO_ASEGURADO,
            reader: {
                type: 'json',
                root: 'listaAsegurado'
            }
        }
    });
	
	var storeTipoPago = Ext.create('Ext.data.JsonStore', {
		model:'Generic',
		proxy: {
			type: 'ajax',
			url: mesConUrlLoadCatalo,
			extraParams : {catalogo:_CATALOGO_TipoPago},
			reader: {
				type: 'json',
				root: 'lista'
			}
		}
	});
    storeTipoPago.load();
    
    var storeProveedor = Ext.create('Ext.data.Store', {
        model:'modelListadoProvMedico',
        autoLoad:false,
        proxy: {
            type: 'ajax',
            url : _URL_CONSULTA_PROVEEDOR_MEDICO,
            extraParams:{
            	'params.tipoprov' : 'C'
            },
            reader: {
                type: 'json',
                root: 'listaProvMedico'
            }
        }
    });

    storeListadoPoliza = new Ext.data.Store(
    {
    	pageSize : 5
        ,model      : 'modelListadoPoliza'
        ,autoLoad  : false
        ,proxy     :
        {
            enablePaging : true,
            reader       : 'json',
            type         : 'memory',
            data         : []
        }
    });
    
    
    Ext.selection.CheckboxModel.override( {
        mode: 'SINGLE',
        allowDeselect: true
    });

    oficinaReceptora= Ext.create('Ext.form.ComboBox',
  	{
    	id:'cmbOficReceptora',	   name:'cmbOficReceptora',     fieldLabel: 'Oficina receptora',		queryMode:'local',		   
    	displayField: 'value',	   valueField: 'key',			allowBlank:false,				        editable:false,
    	labelWidth : 250,		   width		 : 500,			emptyText:'Seleccione...'
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
    	id:'cmbOficEmisora',		name:'cmbOficEmisora',		fieldLabel: 'Oficina emisora',			queryMode:'local',
	    displayField: 'value',	    valueField: 'key',			width		 : 500,					    allowBlank:false,					    editable:false,
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
     
    var comboTipoAte= Ext.create('Ext.form.ComboBox',
    {
        id:'cmbTipoAtencion',			   name:'cmbTipoAtencion',		        fieldLabel: 'Tipo Atenci&oacute;n',				queryMode:'local',
        displayField: 'value',	   valueField: 'key',					allowBlank:false,						editable:false,
        labelWidth : 250,		   emptyText:'Seleccione...',			width		 : 500,						store: storeTipoAtencion
    });

    var tipoServicioInterno = Ext.create('Ext.form.ComboBox',
    {
        id:'tipoServicioInterno',			   name:'tipoServicioInterno',		        fieldLabel: 'Tipo de servicio',				queryMode:'local',
        displayField: 'value',	   valueField: 'key',					allowBlank:false,						editable:false,
        labelWidth : 170,		   emptyText:'Seleccione...',			width		 : 500,						store: storeTipoAtencion
    });

    tipoPago= Ext.create('Ext.form.ComboBox',
    		{
    	id:'cmbTipoPago',			   name:'cmbTipoPago',		        fieldLabel: 'Tipo pago',				queryMode:'local',
    	displayField: 'value',	   valueField: 'key',					allowBlank:false,						editable:false,
    	labelWidth : 250,		   width		 : 500,					emptyText:'Seleccione...',			store: storeTipoPago
    	,
    	listeners : {
    		'select' : function(combo, record) {
    			closedStatusSelectedID = this.getValue();
    			if(closedStatusSelectedID ==1){
    				//PAGO DIRECTO
    				Ext.getCmp('editorIncisos').hide();
    				Ext.getCmp('cmbBeneficiario').hide();
    				Ext.getCmp('cmbProveedor').show();
    				Ext.getCmp('txtNoFactura').show();
    				Ext.getCmp('txtImporte').show();
    				Ext.getCmp('dtFechaFactura').show();
    				
    			}else{
    				//PAGO POR REEMBOLSO
    				Ext.getCmp('cmbProveedorInterno').hide();
    				Ext.getCmp('txtNoFactura').hide();
    				Ext.getCmp('txtImporte').hide();
    				Ext.getCmp('dtFechaFactura').hide();
    				Ext.getCmp('editorIncisos').show();
    				Ext.getCmp('cmbBeneficiario').show();
    			}
    		}
    	}
	});

    
    aseguradoAfectado = Ext.create('Ext.form.field.ComboBox',
    {
		fieldLabel : 'Asegurado afectado',			allowBlank: false,				displayField : 'value',		name:'cmbAseguradoAfectado',
        id:'cmbAseguradoAfectado',				labelWidth: 250,					width:500,						valueField   : 'key',
        forceSelection : false,			matchFieldWidth: false,				queryMode :'remote',			queryParam: 'params.cdperson',
        store : storeAsegurados,		triggerAction: 'all',				//editable:false
        listeners : {
            'select' : function(combo, record) {
                    obtieneCDPerson = this.getValue();
                    var params = {
                            'params.cdperson' : obtieneCDPerson
                    };
                    
                    cargaStorePaginadoLocal(storeListadoPoliza, _URL_CONSULTA_LISTADO_POLIZA, 'listaPoliza', params, function(options, success, response){
                        if(success){
                            var jsonResponse = Ext.decode(response.responseText);
                            if(jsonResponse.listaPoliza == null) {
                                Ext.Msg.show({
                                    title: 'Aviso',
                                    msg: 'No se encontraron datos.',
                                    buttons: Ext.Msg.OK,
                                    icon: Ext.Msg.WARNING
                                });
                                return;
                            }
                            
                        }else{
                            Ext.Msg.show({
                                title: 'Aviso',
                                msg: 'Error al obtener los datos.',
                                buttons: Ext.Msg.OK,
                                icon: Ext.Msg.ERROR
                            });
                        }
                    });
                    
                    modPolizasAltaTramite.showAt(200,100);
                }
            }
    });
    
    cmbBeneficiario= Ext.create('Ext.form.ComboBox',
	{
        id:'cmbBeneficiario',        name:'cmbBeneficiario',        	fieldLabel: 'Beneficiario',        queryMode:'local',        
        displayField: 'value',    valueField: 'key',        			allowBlank:false,        				editable:false,        
        labelWidth : 250,         width		 : 500,					emptyText:'Seleccione...'//,       		store: storePenalizacion
        
    });
    
    
    cmbProveedor = Ext.create('Ext.form.field.ComboBox',
    {
    	fieldLabel : 'Proveedor',			allowBlank: false,				displayField : 'nombre',		name:'cmbProveedor',
    		id:'cmbProveedor',				labelWidth: 250,					valueField   : 'cdpresta',			
    		forceSelection : false,	width:500,		emptyText:'Seleccione...',
    	matchFieldWidth: false,			queryMode :'remote',				queryParam: 'params.cdpresta',	store : storeProveedor,			editable:false,
    	triggerAction: 'all'
    });
    
    cmbProveedorInterno = Ext.create('Ext.form.field.ComboBox',
    {
    	fieldLabel : 'Proveedor',			allowBlank: false,				displayField : 'nombre',		name:'cmbProveedorInterno',
    		id:'cmbProveedorInterno',				labelWidth: 250,					valueField   : 'cdpresta',			
    		forceSelection : false,	width:500,		emptyText:'Seleccione...',
    	matchFieldWidth: false,			queryMode :'remote',				queryParam: 'params.cdpresta',	store : storeProveedor,			editable:false,
    	triggerAction: 'all'
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
            tipoServicioInterno
            ,
            cmbProveedorInterno
            ,
            	//proveedor2
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
        					 	proveedor: datos.cmbProveedorInterno,
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
 		title: 'Alta de Tr&eacute;mite',
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
	 	
	 	onAddClick: function(btn, e){
	 		ventanaGrid.animateTarget=btn;
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


    
    
    
    gridPolizasAltaTramite= Ext.create('Ext.grid.Panel',
	{
	    id             : 'polizasAltaTramite'
	    ,store         :  storeListadoPoliza
	    ,collapsible   : true
	    ,titleCollapse : true
	    //,style         : 'margin:5px'
	    ,width   : 640
	    ,height: 200
	    ,columns       :
	    [
	        {
	             header     : 'N&uacute;mero de P&oacute;liza'
	             ,dataIndex : 'nmpoliza'
	             ,width	 	: 150
	         },
	         {
	             header     : 'Ramo'
	             ,dataIndex : 'cdramo'
	             ,width     : 100
	         }
	         ,
	         {
	             header     : 'Unieco'
	             ,dataIndex : 'cdunieco'
	             ,width     : 100
	         },
	         {
	             header     : 'Estado'
	             ,dataIndex : 'estado'
	             ,width	    : 100
	         }
	         ,
	         {
	             header     : 'N&uacute;mero de Situaci&oacute;n'
	             ,dataIndex : 'nmsituac'
	             ,width	    : 200
	         }
	     ],
	     bbar     :
	     {
	         displayInfo : true,
	         store       : storeListadoPoliza,
	         xtype       : 'pagingtoolbar'
	     }
	});
    
    modPolizasAltaTramite = Ext.create('Ext.window.Window',
	{
	    title        : 'Listado de P&oacute;liza'
	    ,modal       : true
	    ,buttonAlign : 'center'
	    ,closable : false
	    ,width		 : 650
	    ,minHeight 	 : 100 
	    ,maxheight      : 400
	    ,items       :
	        [
	            gridPolizasAltaTramite
	         ],
	         buttons: [{
	                text: "Seleccionar"
	                ,icon:_CONTEXT+'/resources/fam3icons/icons/application_edit.png'
	                ,handler: function(){
	                        if(Ext.getCmp('polizasAltaTramite').getSelectionModel().hasSelection()){
	                            var rowSelected = Ext.getCmp('polizasAltaTramite').getSelectionModel().getSelection()[0];
	                            console.log(rowSelected);
	                            /*Ext.getCmp('idUnieco').setValue(rowSelected.get('cdunieco'));
	                            Ext.getCmp('idEstado').setValue(rowSelected.get('estado'));
	                            Ext.getCmp('idcdRamo').setValue(rowSelected.get('cdramo'));
	                            Ext.getCmp('idNmSituac').setValue(rowSelected.get('nmsituac'));
	                            Ext.getCmp('polizaAfectada').setValue(rowSelected.get('nmpoliza'));*/
	                            modPolizasAltaTramite.hide();
	                        }else {
	                            Ext.Msg.show({
	                                title: 'Aviso',
	                                msg: 'Debe de seleccionar la p&oacute;liza a afectar',
	                                buttons: Ext.Msg.OK,
	                                icon: Ext.Msg.ERROR
	                            });
	                            
	                        }
	                }
	            }
	         ]
	});
    
    Ext.create('Ext.form.Panel',
    	    {
    	        border    : 0
    	        ,title: 'Alta de Tr&eacute;mite'
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
		            	,width		 : 500
		            	,name       : 'txtContraRecibo'
		            },
		            {
		                id: 'txtEstado',
		                name: 'txtEstado',
		                xtype: 'textfield',
		                fieldLabel: 'Estado',
		                readOnly   : true,
		               labelWidth: 250,
		               width		 : 500,
		               value:'Pendiente'
		            },
		            
		            oficinaReceptora
		            ,
            		oficinaEmisora
	            	,
	            	{
		            	id:'dtFechaRecepcion'
		                ,xtype      : 'datefield'
		            	,fieldLabel : 'Fecha recepci&oacute;n'
		            	,labelWidth : 250
		            	,width		 : 500
		            	,name       : 'dtFechaRecepcion'
	            		,value		: new Date()
		            	,format		: 'd/m/Y'
		            	,readOnly   : true
		            }
	            	,
	            	comboTipoAte,
	        		tipoPago
        			,
            		aseguradoAfectado
	            	,
	            	cmbBeneficiario
	            	,
	            	cmbProveedor
	        	    ,
	        	    {
		            	id:'txtNoFactura'
		                ,xtype      : 'textfield'
		            	,fieldLabel : 'No. Factura'
		            	,labelWidth : 250
		            	,width		: 500
		            	,name       : 'txtNoFactura'
		            }
		            ,
                	{
		            	id:'txtImporte'
		                ,xtype      : 'textfield'
		            	,fieldLabel : 'Importe'
		            	,labelWidth : 250
		            	,width		: 500
		            	,name       : 'txtImporte'
		            },
		            {
                        id: 'dtFechaFactura',
                        name: 'dtFechaFactura',
                        fieldLabel: 'Fecha Factura',
                        xtype: 'datefield',
                        format: 'd/m/Y',
                        editable: true,
                        allowBlank:false,
                        labelWidth : 250,
                        width		 : 500,
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
    	        },
    	        {
    	            text:'Cancelar',
    	            //icon:_CONTEXT+'/resources/fam3icons/icons/arrow_refresh.png',
    	            ///id:'botonLimpiar',
    	            handler:function()
    	            {
    	            	windowLoader.close();
    	            }
    	        }
    	    ]
    	    });
});