Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";


 //cantidad a estructuras a mostrar por pagina
 var itemsPerPage=10;

    // Definicion de las columnas de la grilla
        var cm = new Ext.grid.ColumnModel([
        {
            header: "Elemento",
            dataIndex: 'cdElemento',
            width: 250,
            hidden :true
            },{
            header: getLabelFromMap('cmPlanClienteGar',helpMap,'Garantia'),
            tooltip: getToolTipFromMap('cmPlanClienteGar',helpMap, 'Garantía del Plan'),
            dataIndex: 'cdGarant',
            width: 100
            },{
            header: getLabelFromMap('cmPlanClienteSit',helpMap,'Situación'),
            tooltip: getToolTipFromMap('cmPlanClienteSit',helpMap, 'Situación del Plan'),
            dataIndex: 'dsTipSit',
            width: 100,
            sortable: true
            },{
            header: getLabelFromMap('cmPlanClientePro',helpMap,'Producto'),
            tooltip: getToolTipFromMap('cmPlanClientePro',helpMap, 'Producto'),
            dataIndex: 'dsRamo',
            width: 100,
            sortable: true
            },{
            header: getLabelFromMap('cmPlanClientePer',helpMap,'Persona'),
            tooltip: getToolTipFromMap('cmPlanClientePer',helpMap, 'Persona'),
            dataIndex: 'cdPerson',
            width: 100,
            sortable: true
            },{
            header: getLabelFromMap('cmPlanClienteObl',helpMap,'Oblig.?'),
            tooltip: getToolTipFromMap('cmPlanClienteObl',helpMap, 'Tilde si es obligatorio'),
            dataIndex: 'swOblig',
            width: 100,
            sortable: true
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
			]),
            baseParams: {
                codigoCliente:codigoCliente.getValue(),
                codigoElemento:codigoElemento.getValue(),
                codigoProducto:codigoProducto.getValue(),
                codigoPlan:codigoPlan.getValue(),
                codigoTipoSituacion:codigoTipoSituacion.getValue(),
                garantia:garantia.getValue()
            }
        });
		return store;
 	}

    var grid2;

function createGrid(){
	grid2= new Ext.grid.GridPanel({
        el:'gridElementos',
        store:test(),
        title: '<span style="height:10"></span>',
		border:true,
        cm: cm,
        loadMask: {msg: getLabelFromMap('400058',helpMap,'Cargando datos ...'), disabled: false},
		buttons:[
        		{
            	tooltip: getToolTipFromMap('gridPlanClienteAdd',helpMap,'Agregar'),
        		text:getLabelFromMap('gridPlanClienteAdd',helpMap,'Agrega un plan'),
            	handler:function(){
	                agregar();}
            	},{
            	tooltip: getToolTipFromMap('gridPlanClienteEdit',helpMap,'Editar'),
        		text:getLabelFromMap('gridPlanClienteEdit',helpMap, 'Edita un plan'),
            	handler:function(){
            	editar();}
            	},{
            	tooltip: getToolTipFromMap('gridPlanClienteEdit',helpMap,'Eliminar'),
        		text:getLabelFromMap('gridPlanClienteEdit',helpMap,'Elimina un plan')
            	},{
            	tooltip: getToolTipFromMap('gridPlanClienteBack',helpMap,'Regresar'),
        		text:getLabelFromMap('gridPlanClienteBack',helpMap,'Regresa a la pantalla anterior')
            	},{
            	tooltip: getToolTipFromMap('gridPlanClienteExp',helpMap,'Exportar'),
        		text:getLabelFromMap('gridPlanClienteExp',helpMap, 'Exporta un plan'),
            	handler:function(){
                        var url = _ACTION_PLANESCLIENTE_EXPORT + '?codigoElemento=' + codigoElemento.getValue()+ '&codigoProducto=' + codigoProducto.getValue()+ '&codigoPlan=' + codigoPlan.getValue() + '?codigoTipoSituacion=' + codigoTipoSituacion.getValue() + '?garantia=' + garantia.getValue() + '?aseguradora=' + aseguradora.getValue();
                	 	showExportDialog( url );
                        }
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
                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
				emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
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
                         Ext.MessageBox.alert(getLabelFromMap('400033', helpMap,'No se encontraron registros'));
                         mStore.removeAll();
                      }
                  }

              }
            );


}

    var codigoCliente = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtPCCodCliente',helpMap,'Código Cliente'),
        tooltip: getLabelFromMap('txtPCCodCliente',helpMap, 'Código del Cliente del plan'),
        name:'codigoCliente',
        width: 200
    });


    var codigoElemento = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtPCCodElem',helpMap,'Código Elemento'),
        tooltip: getLabelFromMap('txtPCCodElem',helpMap, 'Codigo del elemento'),
        name:'codigoElemento',
        width: 200
    });

    var codigoProducto = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtPCCodProd',helpMap,'Código Producto'),
        tooltip: getLabelFromMap('txtPCCodProd',helpMap, 'Código del Producto'),
        name:'codigoProducto',
        width: 200
    });

    var codigoPlan = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtPCCodPlan',helpMap,'Código Plan'),
        tooltip: getLabelFromMap('txtPCCodPlan',helpMap, 'Código del Plan'),
        name:'codigoPlan',
        width: 200
    });



    var codigoTipoSituacion = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtPCCodTipSit',helpMap,'Código Tipo Sit.'),
        tooltip: getLabelFromMap('txtPCCodTipSit',helpMap, 'Código del Tipo Sit.'),
        name:'codigoTipoSituacion',
        width: 200
    });

    var garantia = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtPCCodGar',helpMap,'Garantía'),
        tooltip: getLabelFromMap('txtPCCodGar',helpMap,'Garantía del Plan'),
        name:'garantia',
        width: 200
    });



    var incisosForm = new Ext.form.FormPanel({
        el:'formBusqueda',
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
        		border: false,
				items:[{
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
        							text: getLabelFromMap('btnPCGuardar',helpMap,'Guardar'),
        							tooltip: getLabelFromMap('btnPCGuardar',helpMap,'Guarda actualización'),
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
        							text: getLabelFromMap('btnPCCancel',helpMap,'Cancelar'),
        							tooltip: getLabelFromMap('btnPCCancel',helpMap, 'Cancela la búsqueda'),                              
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
        Ext.MessageBox.alert('Agregar', 'Accion de Agregar un elemento!');
    }

    editar = function() {
        Ext.MessageBox.alert('Editar', 'Accion de Editar un elemento!');
    }

});