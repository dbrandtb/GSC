var helpMap = new Map();

Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

    var swOblig = new Ext.grid.CheckColumn({
      id:'cmDetPlanSwObligId',
      header: getLabelFromMap('cmDetPlanSwObligId',helpMap,'Obligatorio'),
      tooltip: getToolTipFromMap('cmDetPlanSwObligId',helpMap, 'Si es obligatorio el plan'),
      dataIndex: 'swOblig',
      width: 112
    });



    // Definicion de las columnas de la grilla
        var cm = new Ext.grid.ColumnModel([
            {	id:'cmDetPlanTipSitid',
                header: getLabelFromMap('cmDetPlanTipSitid',helpMap,'Tipo Situaci&oacute;n'),
                tooltip: getToolTipFromMap('cmDetPlanTipSitid',helpMap, 'Tipo de Situaci&oacute;n del Plan'),
                dataIndex: 'dsTipSit',
                width: 250,
                sortable: true
                },
                {
                id:'cmDetPlanGarantId',
                header: getLabelFromMap('cmDetPlanGarantId',helpMap,'Garant&iacute;a'),
                tooltip: getToolTipFromMap('cmDetPlanGarantId',helpMap, 'Garant&iacute;a del Plan'),
                dataIndex: 'dsGarant',
                width: 112,
                sortable: true
                },
                swOblig,
                {
                dataIndex: 'cdRamo',
                hidden :true
                },
                {
                dataIndex: 'cdPlan',
                hidden :true
                },
                {
                dataIndex: 'cdTipSit',
                hidden :true
                },
                {
                dataIndex: 'cdGarant',
                hidden :true
                }
         ]);


 function test(){
 			store = new Ext.data.Store({

    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_BUSCAR_PLANES
                }),

                reader: new Ext.data.JsonReader({
            	root:'MMantenimientoPlanVO',
            	totalProperty: 'totalCount',
            	id: 'cdperson',
	            successProperty : '@success'
	        },[
	        {name: 'cdRamo',  type: 'string',  mapping:'cdRamo'},
	        {name: 'cdPlan',  type: 'string',  mapping:'cdPlan'},
	        {name: 'cdTipSit',  type: 'string',  mapping:'cdTipSit'},
	        {name: 'dsTipSit',  type: 'string',  mapping:'dsTipSit'},
	        {name: 'cdGarant',  type: 'string',  mapping:'cdGarant'},
	        {name: 'dsGarant',  type: 'string',  mapping:'dsGarant'},
            {name: 'swOblig',  type: 'bool',  mapping:'swOblig'}
			])
        });
		return store;
 	}


    var grid2;



function createGrid(){
	grid2= new Ext.grid.GridPanel({
		id: 'grid2',
        el:'gridElementos',
        title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
        store:test(),
		border:true,
        cm: cm,
        loadMask: {msg: getLabelFromMap('400058',helpMap,'Cargando datos ...'), disabled: false},
        buttonAlign:'center',
		buttons:[
        		{
        		id: 'btnAddDetPlanId',
        		text:getLabelFromMap('btnAddDetPlanId',helpMap,'Agregar'),
        		tooltip:getToolTipFromMap('btnAddDetPlanId',helpMap, 'Agrega detalle del Plan'),
            	handler:function(){agregar();}},
            	{
        		id: 'btnEditDetPlanId',
        		text:getLabelFromMap('btnEditDetPlanId',helpMap,'Editar'),
        		tooltip:getToolTipFromMap('btnEditDetPlanId',helpMap, 'Edita un Plan'),
            	handler:function(){
                if (getSelectedRecord(grid2) != null) {
                    editar(getSelectedRecord(grid2));
                } else {
                    Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                }
                }
            	},
            	{
        		id: 'btnDelDetPlanId',
        		text:getLabelFromMap('btnDelDetPlanId',helpMap,'Eliminar'),
        		tooltip:getToolTipFromMap('btnDelDetPlanId',helpMap, 'Elimina un Plan'),
            	handler: function () {borrar();}
            	},
            	{
            	id:'btnExportDetPlanId',
        		text:getLabelFromMap('btnExportDetPlanId', helpMap,'Exportar'),
            	tooltip:getToolTipFromMap('btnExportDetPlanId', helpMap,'Exporta el listado a un formato determinado'),
                handler:function(){                		
                		var url = _ACTION_EXPORT + '?codigoPlan='+CODIGO_PLAN+'&codigoProducto='+CODIGO_PRODUCTO+'&garantia='+Ext.getCmp('incisosForm').form.findField('tipoSituacion').getValue()+'&tipoSituacion='+Ext.getCmp('incisosForm').form.findField('garantia').getValue();
                	 	showExportDialog( url );
                	 }
            	},
            	/*{
        		id: 'btnConfigDetPlanId',
        		text:getLabelFromMap('btnConfigDetPlanId',helpMap,'Configurar'),
        		tooltip:getToolTipFromMap('btnConfigDetPlanId',helpMap, 'Configura plan'),
            	handler: function () {
            				window.location.href = _ACTION_CONFIGURAR_PLAN_CLIENTE + "?codigoProducto=" + CODIGO_PRODUCTO +
            										"&codigoPlan=" + CODIGO_PLAN + "&descripcionProducto=" + DESCRIPCION_PRODUCTO +
            										"&descripcionPlan=" + DESCRIPCION_PLAN;
            				
            			}
            	},*/
            	{
        		id: 'btnBackDetPlanId',
        		text:getLabelFromMap('btnBackDetPlanId',helpMap,'Regresar'),
        		tooltip:getToolTipFromMap('btnBackDetPlanId',helpMap, 'Regresa a la pantalla anterior'),
            	handler: function() {
            		window.location.href = _REGRESAR_MANTENIMIENTO_PLAN;
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

 	var descripcionProducto = new Ext.form.TextField ({
 		id:'descripcionProductoId',
 		fieldLabel: getLabelFromMap('descripcionProductoId',helpMap,'Producto'),
 		tooltip: getToolTipFromMap('descripcionProductoId',helpMap, 'Producto del Plan'),
 		value: DESCRIPCION_PRODUCTO,
 		width: 200,
 		disabled: true
 	});

	var descripcionPlan = new Ext.form.TextField({
 		id:'descripcionPlanId',
 		fieldLabel: getLabelFromMap('descripcionPlanId',helpMap,'Plan'),
 		tooltip: getToolTipFromMap('descripcionPlanId',helpMap, 'Plan'),
		value: DESCRIPCION_PLAN,
		width: 200,
		disabled: true
	});

    var codigoProducto = new Ext.form.Hidden({
        fieldLabel: 'Producto',
        name:'codigoProducto',
        width: 200,
        value: CODIGO_PRODUCTO
    });


    var codigoPlan = new Ext.form.Hidden({
        fieldLabel: 'Plan',
        name:'codigoPlan',
        width: 200,
        value: CODIGO_PLAN
    });

    var codigoTipoSituacion = new Ext.form.TextField({
 		id:'codigoTipoSituacionId',
 		fieldLabel: getLabelFromMap('codigoTipoSituacionId',helpMap,'Tipo Situaci&oacute;n'),
 		tooltip: getToolTipFromMap('codigoTipoSituacionId',helpMap, 'Tipo de Situaci&oacute;n del Plan'),
        name:'tipoSituacion',
        width: 200
    });

    var codigoGarantia = new Ext.form.TextField({
 		id:'codigoGarantiaId',
 		fieldLabel: getLabelFromMap('codigoGarantiaId',helpMap,'Garant&iacute;a'),
 		tooltip: getToolTipFromMap('codigoGarantiaId',helpMap, 'Garant&iacute;a del Plan'),
        name:'garantia',
        width: 200
    });


    var incisosForm = new Ext.form.FormPanel({
    	id: 'incisosForm',
        el:'formBusqueda',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('58',helpMap,'Configuraci&oacute;n de Planes')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,
        url:_ACTION_BUSCAR_PLANES,
        width: 500,
        autoHeight: true,
        items: [{
        		layout:'form',
        		border: false,
				items:[{
        		bodyStyle:'background: white',
        		title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
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
        		        			descripcionProducto,
        		        			descripcionPlan,
									codigoProducto,
									codigoPlan,
									codigoTipoSituacion,
									codigoGarantia		       						
								]
								},{
								columnWidth:.4,
                				layout: 'form'
                				}]
                			}],
                			buttons:[{
                					id:'btnFindPlanId',
        							text:getLabelFromMap('btnFindPlanId',helpMap,'Buscar'),
        							tooltip: getToolTipFromMap('btnFindPlanId',helpMap, 'Busca un Plan en base a los criterios de b&uacute;'),
        							handler: function() {
				               			if (incisosForm.form.isValid()) {
                                               if (grid2!=null) {
                                                reloadGrid();
                                               } else {
                                                createGrid();
                                               }
                						} else{
											Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
										}
									}
        							},
        							{
                					id:'btnCancelPlanId',
        							text:getLabelFromMap('btnCancelPlanId',helpMap,'Cancelar'), 
        							tooltip: getToolTipFromMap('btnCancelPlanId',helpMap, 'Cancela la B&uacute;squeda'),                             
        							handler: function(){incisosForm.form.reset();}
									}
								]
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

    function borrar() {
               var _record = grid2.getSelectionModel().getSelected();

                if (_record == null) {
                  Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                  return;
                }


               if (_record!=null) {//hay una estructura seleccionada?
                   Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'), function(btn) {
                       if (btn == "yes") {
	                           var m = grid2.getSelections();//obtener los items seleccionados
	                           var _params = {
	                                   cdRamo: m[0].get('cdRamo'),
						               cdPlan: m[0].get('cdPlan'),
						               cdTipSit: m[0].get('cdTipSit'),
						               cdGarant: m[0].get('cdGarant'),
						               swOblig: m[0].get('swOblig')
	                           };
	                           execConnection(_ACTION_BORRAR_DETALLEPLAN, _params, cbkConnection);
                       }
                   });
               } else {
                   Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
               }
           };
    function cbkConnection (_success, _message) {
    	if (!_success) {
    		Ext.Msg.alert('Error', _message);
    	}else {
    		Ext.Msg.alert('Aviso', _message, function(){reloadGrid();});
    	}
    }
});

function reloadGrid(){
	var _params = {
    		codigoProducto: Ext.getCmp('incisosForm').form.findField('codigoProducto').getValue(),
    		codigoPlan: Ext.getCmp('incisosForm').form.findField('codigoPlan').getValue(),
    		tipoSituacion: Ext.getCmp('incisosForm').form.findField('tipoSituacion').getValue(),
    		garantia: Ext.getCmp('incisosForm').form.findField('garantia').getValue()
	};
	reloadComponentStore(Ext.getCmp('grid2'), _params, cbkReload);
}
function cbkReload(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert('Error', _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert('Aviso', _store.reader.jsonData.actionErrors[0]);
	}
}

Ext.grid.CheckColumn = function(config){
    Ext.apply(this, config);
    if(!this.id){
        this.id = Ext.id();
    }
    this.renderer = this.renderer.createDelegate(this);
};

Ext.grid.CheckColumn.prototype ={
    init : function(grid){
        this.grid = grid;
        this.grid.on('render', function(){
            var view = this.grid.getView();
            view.mainBody.on('mousedown', this.onMouseDown, this);
        }, this);
    },

    onMouseDown : function(e, t){
        if(t.className && t.className.indexOf('x-grid3-cc-'+this.id) != -1){
            e.stopEvent();
            var index = this.grid.getView().findRowIndex(t);
            var record = this.grid.store.getAt(index);
            record.set(this.dataIndex, !record.data[this.dataIndex]);
        }
    },

    renderer : function(v, p, record){
        p.css += ' x-grid3-check-col-td';
        return '<div class="x-grid3-check-col'+(v?'-on':'')+' x-grid3-cc-'+this.id+'">&#160;</div>';
    }
};
