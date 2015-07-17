Ext.require([ 'Ext.form.*', 'Ext.data.*', 'Ext.grid.Panel','Ext.layout.container.Column', 'Ext.selection.CheckboxModel' ]);
Ext.onReady(function() {
	var valorIndexSeleccionado= null;
	var myMask = new Ext.LoadMask(Ext.getBody(), {msg:"loading..."});
	
	Ext.selection.CheckboxModel.override( {
	    mode: 'SINGLE',
	    allowDeselect: true
	});
	//Models:
	Ext.define('modelFacturaSiniestro', {
	    extend:'Ext.data.Model',
	    fields:['noFactura','fechaFactura','tipoServicio','tipoServicioName','proveedor','proveedorName','importe','tipoMoneda','tipoMonedaName','tasaCambio','importeFactura']
	});
	
	Ext.define('modeLayoutProveedor', {
	    extend:'Ext.data.Model',
	    fields:['cveAtributo','cveFormato','valorMin','valorMax','columna']
	});
	
	Ext.define('modelListadoProvMedico',{
	    extend: 'Ext.data.Model',
		    fields: [
					{type:'string', 	name:'cdpresta'},	{type:'string', name:'nombre'},		{type:'string', name:'cdespeci'},	{type:'string',		name:'descesp'}
			]
	});
	
	var storeFormatoAtributo = Ext.create('Ext.data.JsonStore', {
		model:'Generic',
		proxy: {
			type: 'ajax',
			url: _URL_CATALOGOS,
			extraParams : {catalogo:_CATALOGO_TFORMATOS},
			reader: {
				type: 'json',
				root: 'lista'
			}
		}
	});
    storeFormatoAtributo.load();

	var storeCveColumna = Ext.create('Ext.data.JsonStore', {
		model:'Generic',
		proxy: {
			type: 'ajax',
			url: _URL_CATALOGOS,
			extraParams : {catalogo:_CATALOGO_CVECOLUMNA},
			reader: {
				type: 'json',
				root: 'lista'
			}
		}
	});
	storeCveColumna.load();
	
	var storeFacturaDirecto =new Ext.data.Store({
	    autoDestroy: true,
	    model: 'modelFacturaSiniestro'
	});
	
	var storeLayoutProveedor =new Ext.data.Store({
	    autoDestroy: true,
	    model: 'modeLayoutProveedor'
	});
	
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
    
	var storeAplicaIVA = Ext.create('Ext.data.Store', {
		model:'Generic',
		autoLoad:true,
		proxy: {
			type	: 'ajax',
			url: _URL_CATALOGOS,
			extraParams	: {catalogo:_SINO},
			reader: {
				type	:	'json',
				root	:	'lista'
			}
		}
	});
	storeAplicaIVA.load();
	
	var storeSecuenciaIVA = Ext.create('Ext.data.Store', {
		model:'Generic',
		autoLoad:true,
		proxy: {
			type: 'ajax',
			url: _URL_CATALOGOS,
			extraParams : {catalogo:_SECUENCIA},
			reader: {
				type:	'json',
				root:	'lista'
			}
		}
	});
	storeSecuenciaIVA.load();
	
	var storeAplicaIVARet = Ext.create('Ext.data.Store', {
		model:'Generic',
		autoLoad:true,
		proxy: {
			type: 'ajax',
			url: _URL_CATALOGOS,
			extraParams : {catalogo:_SINO},
			reader: {
				type:	'json',
				root:	'lista'
			}
		}
	});
	storeAplicaIVARet.load();
	
    cmbProveedor = Ext.create('Ext.form.field.ComboBox', {
    	fieldLabel : 'Proveedor',		displayField : 'nombre',		name:'cmbProveedor',		valueField   : 'cdpresta',
    	forceSelection : true,			matchFieldWidth: false,			queryMode :'remote',		queryParam: 'params.cdpresta',
    	minChars	: 2,				store : storeProveedor,			triggerAction: 'all',		hideTrigger:true,	allowBlank:false,
    	width		: 400,				readOnly : true
    }); 
	
	cmbTipoFormato = Ext.create('Ext.form.ComboBox',{
        id:'cmbTipoFormato',			store: storeFormatoAtributo,	queryMode:'local',
        displayField: 'value',		valueField: 'key',			editable:false,		allowBlank:false
    });
    
    cmbCveColumna = Ext.create('Ext.form.ComboBox',{
        id:'cmbCveColumna',			store: storeCveColumna,	queryMode:'local',
        displayField: 'value',		valueField: 'key',			editable:false,		allowBlank:false
    });
	
    cmbProveedorReembolso = Ext.create('Ext.form.field.ComboBox', {
    	displayField : 'nombre',		name:'cmbProveedorReembolso',	valueField   : 'cdpresta',
    	forceSelection : true,			matchFieldWidth: false,			queryMode :'remote',		queryParam: 'params.cdpresta',
    	width		 : 300,				minChars  : 2,					store : storeProveedor,		triggerAction: 'all',
    	hideTrigger:true,	allowBlank:false
    });

	aplicaIVADesc = Ext.create('Ext.form.field.ComboBox',{
		fieldLabel   : 'Aplica IVA',	allowBlank		: false,
		editable   : false,			displayField : 'value',			valueField:'key',
		queryMode    :'local',		editable  :false,				name			:'idaplicaIVA',
		store : storeAplicaIVA,		forceSelection  : true,			readOnly : true
	});
	
	secuenciaIVADesc = Ext.create('Ext.form.field.ComboBox',{
		fieldLabel   : 'Secuencia IVA',		allowBlank		: false,
		editable   : false,			displayField : 'value',				valueField:'key',			    		forceSelection  : true,
		queryMode    :'local',				editable  :false,						name			:'secuenciaIVA',
		store : storeSecuenciaIVA,			readOnly : true
	});
	
	aplicaIVARETDesc = Ext.create('Ext.form.field.ComboBox',{
		fieldLabel   : 'Aplica IVA Retenido',allowBlank		: false,
		editable   : false,			displayField : 'value',				valueField:'key',			    		forceSelection  : true,
		queryMode    :'local',				editable  :false,						name			:'idaplicaIVARET',
		store : storeAplicaIVARet,			readOnly : true
	});
    /*////////////////////////////////////////////////////////////////
    ////////////////   DECLARACION DE EDITOR DE INCISOS  ////////////
    ///////////////////////////////////////////////////////////////*/
	//1.- GRID´S
	Ext.define('EditorLayoutProveedor', {
 		extend: 'Ext.grid.Panel',
		name:'editorLayoutProveedor',
 		title: 'Configuraci&oacute;n Atributos',
 		frame: true,
		selType  : 'rowmodel',
	 	initComponent: function(){
	 			Ext.apply(this, {
	 			width: 750,
	 			height: 350
	 			,plugins  : [
					Ext.create('Ext.grid.plugin.CellEditing', {
					clicksToEdit: 1
					,listeners : {
						beforeedit : function() {
							valorIndexSeleccionado = gridLayoutProveedor.getView().getSelectionModel().getSelection()[0];
						}
					}
		            })
		        ],
	 			store: storeLayoutProveedor,
	 			columns: 
	 			[	
	 				{	header: 'Atributo',			dataIndex: 'cveAtributo',			flex:2,  allowBlank: false
				 		,editor: {		
				 				xtype: 'textfield',
								editable : true,
								allowBlank: false
						}
				 	},
				 	{	header: 'Formato', 				dataIndex: 'cveFormato',	flex:2
						,editor : cmbTipoFormato
						,renderer : function(v) {
							var leyenda = '';
							if (typeof v == 'string'){
								storeFormatoAtributo.each(function(rec) {
									if (rec.data.key == v) {
										leyenda = rec.data.value;
									}
								});
							}else{
								if (v.key && v.value){
									leyenda = v.value;
								} else {
									leyenda = v.data.value;
								}
							}
							return leyenda;
						}
					},
				 	{	header: 'Valor Minimo',			dataIndex: 'valorMin',			flex:2,  allowBlank: false
				 		,editor: {		
				 				xtype: 'textfield',
				                editable : true,
				                allowBlank: false
			            }
				 	},
				 	{	header: 'Valor M&aacute;ximo',			dataIndex: 'valorMax',			flex:2,  allowBlank: false
				 		,editor: {		
				 				xtype: 'textfield',
				                editable : true,
				                allowBlank: false
			            }
				 	},
				 	{	header: 'Columna', 				dataIndex: 'columna',	flex:2
						,editor : cmbCveColumna
						,renderer : function(v) {
							var leyenda = '';
							if (typeof v == 'string'){
								storeCveColumna.each(function(rec) {
									if (rec.data.key == v) {
										leyenda = rec.data.value;
									}
								});
							}else{
								if (v.key && v.value){
									leyenda = v.value;
								} else {
									leyenda = v.data.value;
								}
							}
							return leyenda;
						}
					},
				 	{	xtype: 'actioncolumn',			width: 30,		sortable: false,		menuDisabled: true,
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
                    text     : 'Agregar Atributo'
                    ,icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/book.png'
                    ,handler : _p21_agregarGrupoClic
                }]
		 	});
 			this.callParent();
	 	},
	 	onRemoveClick: function(grid, rowIndex){
	 		var record=this.getStore().getAt(rowIndex);
	 		this.getStore().removeAt(rowIndex);
	 	}
 	});
    gridLayoutProveedor=new EditorLayoutProveedor();
    
    /* PANEL PARA LA BUSQUEDA DE LA INFORMACIÓN DEL ASEGURADO PARA LA BUSQUEDA DE LAS POLIZAS */
	var panelInicialPral= Ext.create('Ext.form.Panel',
    	    {
    	        border      : 0,
    	        id          : 'panelInicialPral',
    	        renderTo    : 'div_clau21',
    	        bodyPadding : 5,
    	        width       : 758,
    	        layout      : {
					type    : 'table'
					,columns: 2
				},
				defaults 	: {
					style   : 'margin:5px;'
				},
				items       :[
					cmbProveedor,
					aplicaIVADesc,
					secuenciaIVADesc,
					aplicaIVARETDesc,
					{	colspan:2
				 		,border: false
				 		,items    :
				 			[
				 			 	gridLayoutProveedor
			 			 	]
				 	}
				 	//
				 	
    	        ],
    	        buttonAlign:'center',
    	        buttons: [{
    	            id:'botonCotizar',
    	            icon:_CONTEXT+'/resources/fam3icons/icons/accept.png',
    	            text: 'Guardar Facturas',
            		handler: function() {
            			var form = this.up('form').getForm();
            			//myMask.show();
            			if (form.isValid()){
            				//
            			}
            			else {
            				Ext.Msg.show({
    	                    	title:'Datos incompletos',
    	                    	msg: 'Favor de introducir todos los campos requeridos',
    	                    	buttons: Ext.Msg.OK,
    	                    	icon: Ext.Msg.WARNING
    	                	});
        				}
    	            }
    	        }
    	    ]
    	    });

    if(valorAction.ntramite != null){
    	debug("Valores de recuperacion", valorAction);
		storeProveedor.load();
		storeSecuenciaIVA.load();
		storeAplicaIVARet.load();
		panelInicialPral.down('combo[name=cmbProveedor]').setValue(valorAction.cdpresta);
		panelInicialPral.down('combo[name=idaplicaIVA]').setValue(valorAction.idaplicaIVA);
		panelInicialPral.down('combo[name=secuenciaIVA]').setValue(valorAction.secuenciaIVA);
		panelInicialPral.down('combo[name=idaplicaIVARET]').setValue(valorAction.idaplicaIVARET);
		
		
	}
	
	function _p21_agregarGrupoClic(){
		storeLayoutProveedor.add(new modeLayoutProveedor({atributo:''}));
	}
});