Ext.require([ 'Ext.form.*', 'Ext.data.*', 'Ext.chart.*', 'Ext.grid.Panel','Ext.layout.container.Column', 'Ext.selection.CheckboxModel' ]);
var datosgrid;
var storeConceptoAutorizados;
var storeQuirugicoBase;
var storeQuirugico;

Ext.onReady(function() {

	Ext.selection.CheckboxModel.override( {
	    mode: 'SINGLE',
	    allowDeselect: true
	});
    
	//////////////////////////////////////////
	///// DECLARACION DE LOS COMBOS /////////
	////////////////////////////////////////
	
	//SUCURSAL
	sucursal= Ext.create('Ext.form.ComboBox',
    {
    	colspan:2,	    		id:'sucursal',	        	name:'smap1.sucursal',        fieldLabel: 'Sucursal',    	//store: storeCirculoHospitalario,
        queryMode:'local',		displayField: 'value',		valueField: 'key',			  allowBlank:false,		        editable:false,
        labelWidth : 170,   	emptyText:'Seleccione...'
    });
    //COBERTURA AFECTADA
    coberturaAfectada= Ext.create('Ext.form.ComboBox',
    {
        id:'cobAfectada',      name:'smap1.cobAfectada', 	fieldLabel: 'Cobertura afectada',		//store: storeCirculoHospitalario,
        queryMode:'local',     displayField: 'value',       valueField: 'key',				        allowBlank:false,
        editable:false,        labelWidth : 170,	        emptyText:'Seleccione...'
    });
    //SUBCOBERTURA
    subCobertura= Ext.create('Ext.form.ComboBox',
    {
        id:'subCobertura',     name:'smap1.subCobertura',  fieldLabel: 'Subcobertura',		        //store: storeCirculoHospitalario,
        queryMode:'local',     displayField: 'value',      valueField: 'key',						allowBlank:false,
        editable:false,        labelWidth : 170,	       emptyText:'Seleccione...'
    });
    //PROVEEDOR
    proveedor= Ext.create('Ext.form.ComboBox',
	{
    	colspan:2,		    	id:'proveedor',			        name:'smap1.proveedor',			        fieldLabel: 'Proveedor',		        //store: storeCirculoHospitalario,
        queryMode:'local',      displayField: 'value',			valueField: 'key',				        allowBlank:false,				        editable:false,
        labelWidth : 170,       emptyText:'Seleccione...'
    });
    //MEDICO    
    medico= Ext.create('Ext.form.ComboBox',
    {
        id:'medico',        	name:'smap1.medico',	        fieldLabel: 'M&eacute;dico',	        //store: storeCirculoHospitalario,
        queryMode:'local',  	displayField: 'value',	        valueField: 'key',				        allowBlank:false,
        editable:false,     	labelWidth : 170,		        emptyText:'Seleccione...'
    });
    
    //PENALIZACION
    penalizacion= Ext.create('Ext.form.ComboBox',
	{
    	colspan:2,		    	id:'penalizacion',			    name:'smap1.penalizacion',		        fieldLabel: 'Penalizaci&oacute;n',        //store: storeCirculoHospitalario,				        
    	queryMode:'local',      displayField: 'value',	        valueField: 'key',				        allowBlank:false,
        editable:false,	        labelWidth : 170,				emptyText:'Seleccione...'
    });
    
  //CAUSA SINIESTRO
    causaSiniestro= Ext.create('Ext.form.ComboBox',
	{
    	colspan:2,		    	id:'causaSiniestro',			    name:'smap1.causaSiniestro',		        fieldLabel: 'Causa Siniestro',        //store: storeCirculoHospitalario,				        
    	queryMode:'local',      displayField: 'value',	        valueField: 'key',				        allowBlank:false,
        editable:false,	        labelWidth : 170,				emptyText:'Seleccione...'
    });
    
    //MEDICO    
    medicoInt= Ext.create('Ext.form.ComboBox',
    {
        id:'medicoInt',        	name:'medicoInt',		        fieldLabel: 'M&eacute;dico',	        //store: storeCirculoHospitalario,
        queryMode:'local',  	displayField: 'value',	        valueField: 'key',				        //allowBlank:false,
        editable:false,     	labelWidth : 100,		        width		:350,						emptyText:'Seleccione...'
    });
    
    //CPT    
    cptInt= Ext.create('Ext.form.ComboBox',
    {
        id:'cptInt',        	name:'cptInt',			        fieldLabel: 'CPT',	        			//store: storeCirculoHospitalario,
        queryMode:'local',  	displayField: 'value',	        valueField: 'key',				        //allowBlank:false,
        editable:false,     	labelWidth : 100,		        width		:350,						emptyText:'Seleccione...'
    });
    ////////////////////////////////////////////////
    /////////// 		MODELO				////////
    ////////////////////////////////////////////////
    
    //DATOS PARA EL PRIMER GRID --> CONCEPTOS AUTORIZADOS
    Ext.define('modelConceptoAutorizado',
	{
	    extend:'Ext.data.Model',
	    fields:['medico','cpt','descripcion','precio','cantidad','importe']
	});
    
    //DATOS PARA EL SEGUNDO GRID --> EQUIPO QUIRURGICO BASE
    Ext.define('equipoQuirugicoBase',
	{
	    extend:'Ext.data.Model',
	    fields:['cpt','descripcion','precio','porcentaje','importe']
	});
    
    //DATOS PARA EL TERCER GRID --> EQUIPO QUIRURGICO
    Ext.define('equipoQuirurgico',
	{
	    extend:'Ext.data.Model',
	    fields:['medico','porcentaje','importe']
	});
    
    ////////////////////////////////////////////////
    /////////// 		STORE				////////
    ////////////////////////////////////////////////
    
    //DATOS PARA EL PRIMER GRID --> CONCEPTOS AUTORIZADOS
    storeConceptoAutorizados=new Ext.data.Store(
	{
	    autoDestroy: true,
	    model: 'modelConceptoAutorizado'
	});
    
    //DATOS PARA EL SEGUNDO GRID --> EQUIPO QUIRURGICO BASE
    storeQuirugicoBase=new Ext.data.Store(
	{
	    autoDestroy: true,
	    model: 'equipoQuirugicoBase'
	});
    
    //DATOS PARA EL TERCER GRID --> EQUIPO QUIRURGICO
    storeQuirurgico=new Ext.data.Store(
	{
	    autoDestroy: true,
	    model: 'equipoQuirurgico'
	});
    
    ////////////////////////////////////////////////////////////////////////
    /////////// 		PANEL PARA PEDIR LOS REGISTROS				////////
    ///////////////////////////////////////////////////////////////////////
    //DATOS PARA EL PRIMER GRID --> CONCEPTOS AUTORIZADOS
	var panelConceptosAutorizados= Ext.create('Ext.form.Panel',{
		border  : 0
		,startCollapsed : true
		,bodyStyle:'padding:5px;'
		,items :
		[   			
			medicoInt,
			cptInt,			
			{
				id		: 'descripcionInt',			xtype      	: 'textfield',			fieldLabel 	: 'Descripci&oacute;n',		labelWidth: 100,			width:350,
				name    : 'descripcionInt',			allowBlank	: false
			}
			,
			{
				id		: 'precioInt',				xtype      	: 'textfield',			fieldLabel 	: 'Precio',					labelWidth: 100,			width:350,
				name    : 'precioInt',				allowBlank	: false
			
			}
			,
			{
				id		: 'cantidadInt',			xtype      	: 'textfield',			fieldLabel 	: 'Cantidad',				labelWidth: 100,			width:350,
				name    : 'cantidadInt',			allowBlank	:false
			}
			,
			{
				id		: 'importeInterno',			xtype      	: 'textfield',			fieldLabel 	: 'Importe',				labelWidth: 100,			width:350,
				name    : 'importeInt',				allowBlank	:false
			}
		]
	});
	
	//DATOS PARA EL SEGUDO GRID --> EQUIPO QUIRURGICO BASE
    var panelEquipoQuirurgicoBase= Ext.create('Ext.form.Panel',{
        border  : 0
        ,bodyStyle:'padding:5px;'
        ,items :
        [              
            /*{
		    	id		: 'cptQuirurgico',					xtype      	: 'textfield',			fieldLabel 	: 'CPT',					labelWidth: 100,
		    	name    : 'cptQuirurgico',					allowBlank	: false		    	
    		}*/
            cptInt
            ,
            {
		    	id		: 'descripcionQuirurgico',			xtype      	: 'textfield',			fieldLabel 	: 'Descripci&oacute;n',		labelWidth: 100,
		    	name    : 'descripcionQuirurgico',			allowBlank	: false
    		}
            ,
            {
		    	id		: 'precioQuirurgico',				xtype      	: 'textfield',			fieldLabel 	: 'Precio',					labelWidth: 100,
		    	name    : 'precioQuirurgico',				allowBlank	: false
		    	
    		}
            ,
            {
		    	id		: 'porcentajeQuirurgico',			xtype      	: 'textfield',			fieldLabel 	: 'Porcentaje',				labelWidth: 100,
		    	name    : 'porcentajeQuirurgico',			allowBlank	: false
    		}
            ,
            {
		    	id		: 'importeQuirurgico',				xtype      	: 'textfield',			fieldLabel 	: 'Importe',				labelWidth: 100,
		    	name    : 'importeQuirurgico',				allowBlank	:false
    		}
        ]
    });
    
    
	//DATOS PARA EL TERCER GRID --> EQUIPO QUIRURGICO
    var panelEquipoQuirurgico= Ext.create('Ext.form.Panel',{
        border  : 0
        ,bodyStyle:'padding:5px;'
        ,items :
        [ 
            medicoInt,
            {
		    	id		: 'porcentajeEquipo',			xtype      	: 'textfield',			fieldLabel 	: 'Porcentaje',		labelWidth: 100,
		    	name    : 'porcentajeEquipo',			allowBlank	: false
    		}
            ,
            {
		    	id		: 'importeEquipo',			xtype      	: 'textfield',			fieldLabel 	: 'Importe',		labelWidth: 100,
		    	name    : 'importeEquipo',			allowBlank	: false
    		}
        ]
    });

	////////////////////////////////////////////////////////////////////////////
	/////////// 		VENTANA PARA PEDIR LOS REGISTROS				////////
	///////////////////////////////////////////////////////////////////////////
	//DATOS PARA EL PRIMER GRID --> CONCEPTOS AUTORIZADOS
	ventanaConceptosAutorizado= Ext.create('Ext.window.Window', {
		renderTo: document.body,
		title: 'Conceptos Autorizados',
		height: 270,
		closeAction: 'hide',           
		items:[panelConceptosAutorizados],		
		buttons:[
		         {
					text: 'Aceptar',
					icon:_CONTEXT+'/resources/fam3icons/icons/accept.png',
					handler: function() {
						if (panelConceptosAutorizados.form.isValid()) {			
							var datos=panelConceptosAutorizados.form.getValues();
							console.log(datos);
							
							var rec = new modelConceptoAutorizado({
							medico: datos.medicoInt,
							cpt: datos.cptInt,
							descripcion: datos.descripcionInt,
							precio: datos.precioInt,
							cantidad: datos.cantidadInt,
							importe: datos.importeInt            	
							});
							storeConceptoAutorizados.add(rec);
							panelConceptosAutorizados.getForm().reset();
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
					handler: function(){				
						panelConceptosAutorizados.getForm().reset();
						ventanaConceptosAutorizado.close();
					}
				}
			]
	});

	//DATOS PARA EL SEGUDO GRID --> EQUIPO QUIRURGICO BASE

	ventanaEqQuirurgicoBase= Ext.create('Ext.window.Window', {
			renderTo: document.body,
			title: 'Equipo quir&uacute;rgico base',
			height: 270,
			closeAction: 'hide',
			items:[panelEquipoQuirurgicoBase],		
			buttons:[{
				text: 'Aceptar',
				icon:_CONTEXT+'/resources/fam3icons/icons/accept.png',
				handler: function() {
					if (panelEquipoQuirurgicoBase.form.isValid()){
						var datos=panelEquipoQuirurgicoBase.form.getValues();
						console.log(datos);
						var rec = new equipoQuirugicoBase({
									cpt: datos.cptQuirurgico,
									descripcion: datos.descripcionQuirurgico,
									precio: datos.precioQuirurgico,
									porcentaje: datos.porcentajeQuirurgico,
									importe: datos.importeQuirurgico
						});
						storeQuirugicoBase.add(rec);
						panelEquipoQuirurgicoBase.getForm().reset();
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
					panelEquipoQuirurgicoBase.getForm().reset();
					ventanaEqQuirurgicoBase.close();
				}
			}
		]
	});

	//DATOS PARA EL TERCER GRID --> EQUIPO QUIRURGICO

	ventanaEqQuirurgico= Ext.create('Ext.window.Window', {
			renderTo: document.body,
			title: 'Equipo quir&uacute;rgico',
			height: 270,
			closeAction: 'hide',
			items:[panelEquipoQuirurgico],		
			buttons:[{
				text: 'Aceptar',
				icon:_CONTEXT+'/resources/fam3icons/icons/accept.png',
				handler: function() {
					if (panelEquipoQuirurgico.form.isValid()){
						var datos=panelEquipoQuirurgico.form.getValues();
						console.log(datos);
						var rec = new equipoQuirurgico({
									medico: datos.medico,
									porcentaje: datos.porcentajeEquipo,
									importe: datos.importeEquipo
						});
						storeQuirurgico.add(rec);
						panelEquipoQuirurgico.getForm().reset();
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
					panelEquipoQuirurgico.getForm().reset();
					ventanaEqQuirurgico.close();
				}
			}
		]
	});
	
    

    
    
    
    
    
    
    //////////////////////////////////////////////////////////
    ////////// EDITOR PARA MANIPULACION DE LAS TABLAS  ///////
    //////////////////////////////////////////////////////////
   
    //DATOS PARA EL PRIMER GRID --> CONCEPTOS AUTORIZADOS
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
		title: 'Conceptos Autorizados',
		frame: false,
		initComponent: function(){
			//
	 		this.cellEditing = new Ext.grid.plugin.CellEditing({
	 			clicksToEdit: 1
	 		});
	 		Ext.apply(this, {
	 			height: 200,
	 			plugins: [this.cellEditing],
	 			store: storeConceptoAutorizados,
	 			columns: 
	 			[
				 	{	
				 		header: 'M&eacute;dico',			dataIndex: 'medico',			width:250
				 	},
				 	{
				 		header: 'CPT',						dataIndex: 'cpt',				width:150				 		
				 	},
				 	{
					 	header: 'Descripci&oacute;n',		dataIndex: 'descripcion',	 	width:250	
				 	},
				 	{
					 	header: 'Precio',					dataIndex: 'precio',			width:150,				renderer: Ext.util.Format.usMoney
				 	},
				 	{
					 	header: 'Cantidad', 				dataIndex: 'cantidad',		 	width:150
				 	},
				 	{
					 	header: 'Importe', 					dataIndex: 'importe',		 	width:150,				renderer: Ext.util.Format.usMoney
				 	},
				 	{
					 	xtype: 'actioncolumn',				width: 30,					 	sortable: false,	 	menuDisabled: true,
					 	items: [{
					 		icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/delete.png',
					 		tooltip: 'Quitar conceptos autorizados',
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
				 	text: 'Agregar conceptos autorizados',
				 	scope: this,
				 	handler: this.onAddClick
		 		}]
		 	});
	 		this.callParent();
		},
	 	/*getColumnIndexes: function () {
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
	 	},*/
	 	onAddClick: function(btn, e){
	 		ventanaConceptosAutorizado.animateTarget=btn;
	 		ventanaConceptosAutorizado.show();
	 	},
	 	onRemoveClick: function(grid, rowIndex){
	 		var record=this.getStore().getAt(rowIndex);
	 		console.log(record);        	
	 		this.getStore().removeAt(rowIndex);
	 	}
	});
	gridIncisos=new EditorIncisos();
	
	//DATOS PARA EL SEGUDO GRID --> EQUIPO QUIRURGICO BASE
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
		title: 'Equipo quir&uacute;rgico base',
		frame: false,
		initComponent: function(){
			this.cellEditing = new Ext.grid.plugin.CellEditing({
				clicksToEdit: 1
			});

			Ext.apply(this, {
				height: 200,
				plugins: [this.cellEditing],
				store: storeQuirugicoBase,
				columns: 
					[				 	
						{
							header: 'CPT',						dataIndex: 'cpt',				width:150				 		
						},
						{
							header: 'Descripci&oacute;n',		dataIndex: 'descripcion',	 	width:250	
						},
						{
							header: 'Precio',					dataIndex: 'precio',			width:150,				renderer: Ext.util.Format.usMoney
						},
						{
							header: 'Porcentaje', 				dataIndex: 'porcentaje',		width:150
						},
						{
							header: 'Importe', 					dataIndex: 'importe',		 	width:150,				renderer: Ext.util.Format.usMoney
						},
						{
							xtype: 'actioncolumn',				width: 30,						sortable: false,		menuDisabled: true,
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
						text: 'Agregar equipo quir&uacute;gico',
						scope: this,
						handler: this.onAddClick
					}]
			});
			this.callParent();
		},
		/*getColumnIndexes: function () {
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
		},*/		
		onAddClick: function(btn, e){
			ventanaEqQuirurgicoBase.animateTarget=btn;
			ventanaEqQuirurgicoBase.show();
		},
		onRemoveClick: function(grid, rowIndex){
			var record=this.getStore().getAt(rowIndex);
			console.log(record);        	
			this.getStore().removeAt(rowIndex);
		}
	});
	gridIncisos2=new EditorIncisos2();

    
	//DATOS PARA EL TERCER GRID --> EQUIPO QUIRURGICO
	Ext.define('EditorIncisos3', {
		extend: 'Ext.grid.Panel',
		requires: [
			'Ext.selection.CellModel',
			'Ext.grid.*',
			'Ext.data.*',
			'Ext.util.*',
			'Ext.form.*'
		],
		xtype: 'cell-editing',
		title: 'Equipo quir&uacute;rgico',
		frame: false,
		initComponent: function(){
			this.cellEditing = new Ext.grid.plugin.CellEditing({
				clicksToEdit: 1
			});

			Ext.apply(this, {
				height: 200,
				plugins: [this.cellEditing],
				store: storeQuirurgico,
				columns: 
					[				 	
						{
							header: 'M&eacute;dico',		dataIndex: 'medico',	 		width:250	
						},
						{
							header: 'Porcentaje',			dataIndex: 'porcentaje',		width:150				 		
						},
						{
							header: 'Importe',				dataIndex: 'importe',	 		width:250	
						},						
						{
							xtype: 'actioncolumn',				width: 30,						sortable: false,		menuDisabled: true,
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
						text: 'Agregar equipo quir&uacute;gico',
						scope: this,
						handler: this.onAddClick
					}]
			});
			this.callParent();
		},
		/*getColumnIndexes: function () {
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
		},*/		
		onAddClick: function(btn, e){
			ventanaEqQuirurgico.animateTarget=btn;
			ventanaEqQuirurgico.show();
		},
		onRemoveClick: function(grid, rowIndex){
			var record=this.getStore().getAt(rowIndex);
			console.log(record);        	
			this.getStore().removeAt(rowIndex);
		}
	});
	gridIncisos3=new EditorIncisos3();

    
    ////////////////////////////////////////////////////////////////////
    //// PANEL PRINCIPAL DE PANTALLA DE AUTORIZACION DE SERVICIOS //////
    ////////////////////////////////////////////////////////////////////
    Ext.create('Ext.form.Panel',
	{
		border    : 0
		,title: 'Autorizaci&oacuten de Servicios'
		,renderTo : 'div_clau'
		,bodyPadding: 5
		,width: 750
		,layout     :
		{
				type     : 'table'
				,columns : 2
		}
		,defaults 	:
		{
				style : 'margin:5px;'
		}
		,
		items    	:
		[
		 	/* se agrupa el No. de autorización , el No. de autorización anterior y el botón
		 	 * de buscar
		 	 */
		 	{
		 		colspan:2
		 		,border: false		
		 		,layout     :
		 		{
		 			type     : 'table'
	 				,columns : 3
				},				        
				items    :
				[
					{
						xtype       : 'textfield'	    	,fieldLabel : 'No. de autorizaci&oacute;n'		    	,name       : 'smap1.noAutorizacion'		    
						,labelWidth: 170					,readOnly       : true
					},
					{
						xtype       : 'textfield'		    ,fieldLabel : 'No. de autorizaci&oacute;n anterior'	  	,name       : 'smap1.noAutorizacionAnterior'
						,labelWidth: 170					
					}
					,
					Ext.create('Ext.Button',{
						text: 'Buscar',
						icon : _CONTEXT + '/resources/fam3icons/icons/folder.png'
						//falta la implementacion del boton, para cuando la busqueda se encuentre
					})
				]
		 	}					
			,
			{
				xtype       : 'textfield'				,fieldLabel : 'C&oacute;digo del asegurado'				,name       : 'smap1.codigoAsegurado'
				,allowBlank : false						,labelWidth : 170
			}
			,
			{
				xtype       : 'textfield'				,fieldLabel : 'Nombre del asegurado'					,name       : 'smap1.nombreAsegurado'
				,allowBlank : false						,labelWidth : 170
			}
			,
			{
				id: 'fechaSolicitud'					,xtype		: 'datefield'								,fieldLabel	: 'Fecha Solicitud',
				labelWidth : 170						,name		: 'smap1.fechaSolicitud'					,format		: 'd/m/Y',
				editable: true							,value		: new Date()
			}
			,
			{
				id: 'fechaAutorizacion'					,xtype		: 'datefield'								,fieldLabel	: 'Fecha Autorizaci&oacute;n',
				labelWidth : 170						,name		: 'smap1.fechaAutorizacion'					,format		: 'd/m/Y',
				editable: true,							//,value		: new Date()
				listeners:{
                    change:function(field,value)
                    {
                        try
                        {
                            Ext.getCmp('fechaVencimiento').setValue(Ext.Date.add(value, Ext.Date.DAY, 15));
                        }catch(e){}
                    }
                }
			}
			,
			{
				id: 'fechaVencimiento'					,xtype		: 'datefield'								,fieldLabel	: 'Fecha de vencimiento',
				labelWidth : 170						,name		: 'smap1.fechaVencimiento'					,format		: 'd/m/Y',
				editable: true							//,value		: new Date()
			}
			,
			{
				id: 'fechaIngreso'						,xtype		: 'datefield'								,fieldLabel	: 'Fecha de Ingreso',
				labelWidth : 170						,name		: 'smap1.fechaIngreso'						,format		: 'd/m/Y',
				editable: true							//,value		: new Date()
			}
			,
			{
				colspan:2
				,border: false
				,layout     :
				{
					type     : 'table'
					,columns : 3
				
				},				        
				items    :
				[
					{
						xtype       : 'textfield'						,fieldLabel : 'P&oacute;liza afectada'						,name       : 'smap1.polizaAfectada'
						,allowBlank : false								,labelWidth: 170
					}
					,
					Ext.create('Ext.Button', {
						text: 'Ver coberturas',
						icon : _CONTEXT + '/resources/fam3icons/icons/application_view_list.png'
					})
					,
					Ext.create('Ext.Button', {
						text: 'Ver historial de reclamaciones',
						icon : _CONTEXT + '/resources/fam3icons/icons/clock.png'
					})							
				]
			}
			,
			sucursal
			,
			coberturaAfectada
			,
			subCobertura
			,
			proveedor
			,
			medico
			,
			{
				xtype       : 'textfield'				,fieldLabel : 'Especialidad'				,name       : 'smap1.especialidad'
				,allowBlank : false						,labelWidth: 170
			}
			,
			{
				xtype       : 'textfield'				,fieldLabel : 'Deducible'					,name       : 'smap1.deducible'
				,allowBlank : false						,labelWidth: 170
			}
			,
			{
				xtype       : 'textfield'				,fieldLabel : 'Copago'						,name       : 'smap1.copago'
				,allowBlank : false						,labelWidth: 170
			}
			,
			penalizacion
			,
			{
				xtype       : 'textfield'				,fieldLabel : 'C&oacute;digo ICD'			,name       : 'smap1.codigoICD'
				,allowBlank : false						,labelWidth: 170
			}
			,
			{
				colspan:2								,xtype       : 'textfield'					,fieldLabel : 'Descripci&oacute;n ICD'
				,name       : 'smap1.descICD'			,allowBlank : false							,labelWidth: 170					,readOnly       : true
			}
			,
			{
				colspan:2								,xtype       : 'textfield'					,fieldLabel : 'Suma disponible proveedor'
				,name       : 'smap1.sumDisponible'		,allowBlank  : false						,labelWidth	: 170
			}
			,
			causaSiniestro
			,
			{
				colspan:2								,xtype       : 'textareafield'				,fieldLabel : 'Tr&aacute;tamiento'			,name       : 'smap1.tratamiento'
				,allowBlank : false                     ,labelWidth	 : 170							,width      : 700							,height		: 70
			}
			,
			{
				colspan:2								,xtype       : 'textareafield'				,fieldLabel : 'Observaciones'				,name       : 'smap1.observaciones'
				,allowBlank : false                     ,labelWidth	 : 170							,width      : 700							,height		: 70
			}
			,
			{
				colspan:2								,xtype       : 'textareafield'				,fieldLabel : 'Notas internas'				,name       : 'smap1.notaInterna'
				,allowBlank : false						,labelWidth: 170							,width      : 700							,height: 70
			}
			,
			{
				colspan:2,
				items    :
				[
				 	gridIncisos
				]
			}
			,
			{
				colspan:2,
				items    :
				[
				 	gridIncisos2
				]
			}
			,
			{
				colspan:2,
				items    :
				[
				 	gridIncisos3
				]
			}
		],
		buttonAlign:'center',
		buttons: [{
			text:'Guardar',
			icon:_CONTEXT+'/resources/fam3icons/icons/disk.png',
			id:'botonLimpiar',
			handler:function()
			{}
			},
			{
				text:'Autorizar',
				icon:_CONTEXT+'/resources/fam3icons/icons/key.png',
				id:'Autorizar',
				handler:function()
				{}
				}
			,
			{
				text:'Rechazar',
				icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
				id:'Rechazar',
				handler:function()
				{}
				}
		]		
	});
});