var itemsPerPage=10;
Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

    // Definicion de las columnas de la grilla
        var cm = new Ext.grid.ColumnModel([
        {
	        header: getLabelFromMap('plansCliCmElem', helpMap,'Elemento'),
	        tooltip: getToolTipFromMap('plansCliCmElem', helpMap,'Elemento'),
            dataIndex: 'cdElemento',
            width: 250,
            hidden :true
            },{
	        header: getLabelFromMap('plansCliCmGar', helpMap,'Garant&iacute;a'),
	        tooltip: getToolTipFromMap('plansCliCmGar', helpMap,'Garant&iacute;a'),
            dataIndex: 'cdGarant',
            sortable: true,
            width: 100
            },{
	        header: getLabelFromMap('plansCliCmSit', helpMap,'Situaci&oacute;n'),
	        tooltip: getToolTipFromMap('plansCliCmSit', helpMap,'Situaci&oacute;n'),
            dataIndex: 'dsTipSit',
            width: 100
            },{
	        header: getLabelFromMap('plansCliCmProd', helpMap,'Producto'),
	        tooltip: getToolTipFromMap('plansCliCmProd', helpMap,'Producto'),
            dataIndex: 'dsRamo',
            sortable: true,
            width: 100
            },{
	        header: getLabelFromMap('plansCliCmPer', helpMap,'Persona'),
	        tooltip: getToolTipFromMap('plansCliCmPer', helpMap,'Persona'),
            dataIndex: 'cdPerson',
            sortable: true,
            width: 100
            },{
	        header: getLabelFromMap('plansCliCmOblig', helpMap,'Oblig?'),
	        tooltip: getToolTipFromMap('plansCliCmOblig', helpMap,'Oblig?'),
            dataIndex: 'swOblig',
            sortable: true,
            width: 100
        }]);


 function test(){
 			store = new Ext.data.Store({

    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_BUSCAR_PLANCLIENTE
                }),

                reader: new Ext.data.JsonReader({
            	root:'MDetallePlanXCliente',
            	totalProperty: 'totalCount',
            	id: 'cdperson',
	            successProperty : '@success'
	        },[
	        {name: 'cdElemento',  type: 'string',  mapping:'cdElemento'},
	        {name: 'cdGarant',  type: 'string',  mapping:'cdGarant'},
	        {name: 'dsTipSit',  type: 'string',  mapping:'dsTipSit'},
	        {name: 'dsRamo',  type: 'string',  mapping:'dsRamo'},
            {name: 'cdPerson',  type: 'string',  mapping:'cdPerson'},
            {name: 'swOblig',  type: 'string',  mapping:'swOblig'}
			])
        });
		return store;
 	}



    var grid2;



function createGrid(){
	grid2= new Ext.grid.GridPanel({
		id: 'grid2',
        el:'gridElementos',
        store:test(),
		border:true,
		title: '<span style="height:10"></span>',
        cm: cm,
        loadMask: {msg: getLabelFromMap('400058',helpMap,'Cargando datos ...'), disabled: false},
		buttons:[
        		{text:'Agregar',
            	tooltip:'Agrega un nuevo Plan',
            	handler:function(){
	                agregar();}
            	},{
            	text:'Editar',
            	tooltip:'Edita elemento seleccionado',
            	handler:function(){
            	editar();}
            	},{
            	text:'Eliminar',
            	tooltip:'Elimina elemento seleccionado'
            	},{
            	text: 'Regresar',
            	tooltip: 'Regresa a la pantalla anterior'
            	},{
            	text:'Exportar',
            	tooltip:'Exporta elementos'
            	}],
    	width:500,
    	frame:true,
		height:320,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		stripeRows: true,
		collapsible: true,
		bbar: new Ext.PagingToolbar({
				pageSize:20,
				store: store,
				displayInfo: true,
                displayMsg: 'Mostrando registros {0} - {1} of {2}',
                emptyMsg: "No hay registros para visualizar"
		    })
		});
            grid2.render()
}    

function reloadGrid(){
    var mStore = grid2.store;
    var o = {start: 0};
    mStore.baseParams = mStore.baseParams || {};
    mStore.baseParams['codigoCliente'] = codigoCliente.getValue();
    mStore.baseParams['codigoElemento'] = codigoElemento.getValue();
    mStore.baseParams['codigoProducto'] = codigoProducto.getValue();
    mStore.baseParams['codigoPlan'] = codigoPlan.getValue();
    mStore.baseParams['codigoTipoSituacion'] = codigoTipoSituacion.getValue();
    mStore.baseParams['garantia'] = garantia.getValue();
    mStore.reload(
              {
                  params:{start:0,limit:itemsPerPage},
                  callback : function(r,options,success) {
                      if (!success) {
                         Ext.MessageBox.alert('No se encontraron registros');
                         mStore.removeAll();
                      }
                  }

              }
            );


    var codigoCliente = new Ext.form.TextField({
        fieldLabel: 'C&oacute;digo Cliente',
        name:'codigoCliente',
        width: 200
    });


    var codigoElemento = new Ext.form.TextField({
        fieldLabel: 'C&oacute;digo Elemento',
        name:'codigoElemento',
        width: 200
    });

    var codigoProducto = new Ext.form.TextField({
        fieldLabel: 'C&oacute;digo Producto',
        name:'codigoProducto',
        width: 200
    });

    var codigoPlan = new Ext.form.TextField({
        fieldLabel: 'Codigo Plan',
        name:'codigoPlan',
        width: 200
    });



    var codigoTipoSituacion = new Ext.form.TextField({
        fieldLabel: 'C&oacute;digo Tipo Situaci&oacute;n',
        name:'codigoTipoSituacion',
        width: 200
    });

    var garantia = new Ext.form.TextField({
        fieldLabel: 'Garant&iacute;a',
        name:'garantia',
        width: 200
    });
    
    var lblBusqueda = new Ext.form.Label({
    		text: 'B&uacute;squeda'
    	});



    var incisosForm = new Ext.form.FormPanel({
    	id: 'incisosForm',
        el:'formBusqueda',
		//title: '<span style="color:black;font-size:14px;">B&uacute;squeda</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,
        url:_ACTION_BUSCAR_PLANCLIENTE,
        width: 500,
        height:400,
        items: [{
        		layout:'form',
        		//title :'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">Búsqueda</span>',
				border: false,
				items:[{
        		//title :'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">Búsqueda</span>',
                bodyStyle:'background: white',
		        labelWidth: 50,
                layout: 'form',
				frame:true,
		       	baseCls: '',
		       	buttonAlign: "center",
        		        items: [{
        		        	layout:'column',
		 				    border:false,
		 				    columnWidth: 1,
        		    		items:[{
						    	columnWidth:.6,
                				layout: 'form',
		                		border: false,
        		        		items:[
                                        codigoCliente,
                                        codigoElemento,
                                        codigoProducto,
                                        lblBusqueda,
                                        codigoPlan,
                                        codigoTipoSituacion,
                                        garantia 
		       						]
								},{
								columnWidth:.4,
                				layout: 'form'
                				}]
                			}],
                			buttons:[{
        							text:'Buscar',
        							handler: function() {
				               			if (incisosForm.form.isValid()) {
                                               if (grid2!=null) {
                                                reloadGrid();
                                               } else {
                                                createGrid();
                                               }
                						} else{
											Ext.MessageBox.alert('Error', 'Por Favor revise los errores!');
										}
									}
        							},{
        							text:'Cancelar',                              
        							handler: function() {
        								incisosForm.form.reset();
        							}
        						}]
        					}]
        				}]
				});

		incisosForm.render();
        createGrid();




        function toggleDetails(btn, pressed){
        var view = grid.getView();
        view.showPreview = pressed;
        view.refresh();
    	}
    var store;


    agregar = function() {
        Ext.MessageBox.alert('Agregar', 'Acci&oacute;n de Agregar un elemento!');
    }

    editar = function() {
        Ext.MessageBox.alert('Editar', 'Acci&oacute;n de Editar un elemento!');
    }

});