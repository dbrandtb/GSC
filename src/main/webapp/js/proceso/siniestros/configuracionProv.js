Ext.require([ 'Ext.form.*', 'Ext.data.*', 'Ext.chart.*', 'Ext.grid.Panel','Ext.layout.container.Column', 'Ext.selection.CheckboxModel' ]);
Ext.onReady(function() {
	var panelProveedor;
	var valorIndexSeleccionado= null;
	
	Ext.selection.CheckboxModel.override( {
		mode: 'SINGLE',
		allowDeselect: true
	});
	//Declaracion de los modelos 
	Ext.define('modeloLayout',{
		extend:'Ext.data.Model',
		fields:[{type:'string',		name:'CDLAYOUT'},		{type:'string',		name:'DSLAYOUT'}
        ]
	});
	
	//Declaracion de los stores
	var storeGridLayout = new Ext.data.Store({
		pageSize	: 10
		,model		: 'modeloLayout'
		,autoLoad	: false
		,proxy		: {
			enablePaging	:	true,
			reader			:	'json',
			type			:	'memory',
			data			:	[]
		}
	});
	
	///COMBOS, LABEL
	var panelInicialPral= Ext.create('Ext.panel.Panel',{
		border    : 0
		,renderTo : 'div_clau'
		,items    : [
			Ext.create('Ext.panel.Panel',{
				border  : 0
				,title: 'Listado de Layout'
				,style         : 'margin:5px'
				,layout : {
					type     : 'table'
					,columns : 2
				}
				,defaults : {
					style : 'margin:5px;'
				}
				,items : [
					{	xtype	: 'textfield',		fieldLabel: 'Layout',		name		: 'txtLayout',
						width	: 350,				colspan:2
					}
				]
				,buttonAlign: 'center'
				,buttons : [{
					text: "Buscar"
					,icon:_CONTEXT+'/resources/fam3icons/icons/magnifier.png'
					,handler: function(){
						cargarPaginacion();
					}	
				},{
					text: "Limpiar"
					,icon:_CONTEXT+'/resources/fam3icons/icons/arrow_refresh.png'
					,handler: function(){
						panelInicialPral.down('[name=txtLayout]').setValue('');
						storeGridLayout.removeAll();
					}	
				}] 
			})
			,Ext.create('Ext.grid.Panel',{
				id             : 'layoutGrid'
				,title         : 'LAYOUT'
				,store         :  storeGridLayout
				,titleCollapse : true
				,selType		:	'checkboxmodel'
				,style         : 'margin:5px'
				,height        : 400
				,columns       : [
					{	xtype: 'actioncolumn',			width: 120,		sortable: false,		menuDisabled: true,
						items: [{
							icon: _CONTEXT+'/resources/fam3icons/icons/application_edit.png'
							,tooltip: 'Editar Layout'
							,scope: this
							,handler : editarLayout
				 		},{
							icon: _CONTEXT+'/resources/fam3icons/icons/delete.png',
							tooltip: 'Eliminar Proveedor',
							scope: this,
							handler : eliminarProveedor
						}]
					},
					{
						header     : 'Cve Layout',					dataIndex : 'CDLAYOUT'//,	flex : 0.5
					},
					{
						header     : 'Descripci&oacute;n Layout',	dataIndex  : 'DSLAYOUT',	flex : 1
					}
				],
				bbar     :{
				displayInfo : true,
					store		: storeGridLayout,
					xtype		: 'pagingtoolbar'
				}
				,tbar: [
				{
					text		: 'Agregar Layout'
					,icon		:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/book.png'
					,handler	: agregarLayout
				}
				]
				,listeners: {
					itemclick: function(dv, record, item, index, e){
						//1.- Validamos que el asegurado este vigente
						debug("LO SELECCIONAMOS ", record);
					}
				}
			})
			
		]
	});
	cargarPaginacion();
	
	
	
	
	
	//FUNCIONES GENERALES
	function cargarPaginacion(){
		storeGridLayout.removeAll();
		var params = {
			'params.descLayout' : panelInicialPral.down('[name=txtLayout]').getValue()
		};
		cargaStorePaginadoLocal(storeGridLayout, _URL_CONSULTA_LAYOUT, 'datosValidacion', params, function(options, success, response){
			if(success){
				var jsonResponse = Ext.decode(response.responseText);
				if(jsonResponse.datosValidacion &&jsonResponse.datosValidacion.length == 0) {
					if(panelInicialPral.down('[name=txtLayout]').getValue() == null){
						centrarVentanaInterna(showMessage("Aviso", "No existe configuraci&oacute;n de layout.", Ext.Msg.OK, Ext.Msg.INFO));
					}else{
						centrarVentanaInterna(showMessage("Aviso", "No existe configuraci&oacute;n del layout seleccionado.", Ext.Msg.OK, Ext.Msg.INFO));
					}
					return;
				}
			}else{
				centrarVentanaInterna(showMessage('Error', 'Error al obtener los datos',Ext.Msg.OK, Ext.Msg.ERROR));
			}
		});
	}
	
	function editarLayout(grid,rowIndex){
		var record = grid.getStore().getAt(rowIndex);
		debug("VALOR DEL RECORD ========>>>", record);
		windowLoader = Ext.create('Ext.window.Window',{
			title         : 'Parametrizaci&oacute;n Layout'
			,buttonAlign  : 'center'
			,width        : 800
			,height       : 600
			,autoScroll   : true
			,loader       : {
				url       : _VER_CONFIG_LAYOUT
				,scripts  : true
				,autoLoad : true
				,params   : {
					'params.claveLayout'	:	record.get('CDLAYOUT'),
					'params.tipoLayout'		:	record.get('DSLAYOUT'),
					'params.tipoProceso'	:	"U"
				}
			}
		}).show();
		centrarVentanaInterna(windowLoader);
	}
	
	function agregarLayout(){
		windowLoader = Ext.create('Ext.window.Window',{
			title         : 'Parametrizaci&oacute;n Layout'
			,buttonAlign  : 'center'
			,width        : 800
			,height       : 600
			,autoScroll   : true
			,loader       : {
				url       : _VER_CONFIG_LAYOUT
				,scripts  : true
				,autoLoad : true
				,params   : {
					'params.claveLayout'	:	"0",
					'params.tipoLayout'		:	null,
					'params.tipoProceso'	:	"I"
				}
			}
		}).show();
		centrarVentanaInterna(windowLoader);
	}
	
	function eliminarProveedor(grid,rowIndex){
		var record = grid.getStore().getAt(rowIndex);
		
	}
});