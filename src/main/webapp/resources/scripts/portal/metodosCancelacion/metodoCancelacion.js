Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

    var cm = new Ext.grid.ColumnModel([
        {
        header: getLabelFromMap('metCancelCmCod',helpMap,'Clave'),
        tooltip: getToolTipFromMap('metCancelCmCod',helpMap,'Clave'),
        dataIndex: 'cdMetodo',
        width: 100,
        sortable: true
        },{
        header: getLabelFromMap('metCancelCmDesc',helpMap,'Descripci&oacute;n'),
        tooltip:getToolTipFromMap('metCancelCmDesc',helpMap,'Descripci&oacute;n'),
        dataIndex: 'dsMetodo',
        width: 365,
        sortable: true
    }]);


    var cdMetodo = new Ext.form.TextField({
    	id:'metCancelTxtCod',
        fieldLabel: getLabelFromMap('metCancelTxtCod',helpMap,'Clave'),//C&oacute;digo'),
        tooltip:getToolTipFromMap('metCancelTxtCod',helpMap,''),//C&oacute;digo del M&eacute;todo de Cancelaci&oacute;n'), 
        hasHelpIcon:getHelpIconFromMap('metCancelTxtCod',helpMap),								 
        Ayuda: getHelpTextFromMap('metCancelTxtCod',helpMap),
        name:'cdMetodo',
        allowBlank: true,
        anchor: '100%'
    });
    

    var dsMetodo = new Ext.form.TextField({
    	id:'metCancelTxtDesc',
    	fieldLabel: getLabelFromMap('metCancelTxtDesc',helpMap,'Descripci&oacute;n'),
    	tooltip: getToolTipFromMap('metCancelTxtDesc',helpMap,'Descripci&oacute;n del M&eacute;todo de Cancelaci&oacute;n'),
        hasHelpIcon:getHelpIconFromMap('metCancelTxtDesc',helpMap),								 
        Ayuda: getHelpTextFromMap('metCancelTxtDesc',helpMap),
        name:'dsMetodo',
        allowBlank: true,
        anchor: '100%'
    });


 function test(){

             store = new Ext.data.Store({

    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_BUSCAR_METODOS_CANCELACION
                }),

                reader: new Ext.data.JsonReader({
            	root:'listaMetodosCancelacion',
            	totalProperty: 'totalCount',
            	id: 'cdMetodo',
	            successProperty : '@success'
	        },[
	        {name: 'cdMetodo',  type: 'string',  mapping:'cdMetodo'},
	        {name: 'dsMetodo',  type: 'string',  mapping:'dsMetodo'}
			])
        });

        return store;
 	}


    var grid2;



function createGrid(){
	grid2= new Ext.grid.GridPanel({
		id: 'grid2',
        el:'gridMetodosCancelacion',
        store:test(),
        title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
        
		border:true,
		loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
        cm: cm,
        buttonAlign: 'center',
		buttons:[
        		{
        		text:getLabelFromMap('metCancelBtnAdd',helpMap,'Agregar'),
        		tooltip: getToolTipFromMap('metCancelBtnAdd',helpMap,'Agrega un nuevo M&eacute;todo de Cancelaci&oacute;n'),
            	handler:function(){
                agregar();
                }
            	},{
            	text:getLabelFromMap('metCancelBtnEd',helpMap,'Editar'),
            	tooltip: getToolTipFromMap('metCancelBtnEd',helpMap,'Edita un M&eacute;todo de Cancelaci&oacute;n'),
            	handler:function(){
                    if (getSelectedKey(grid2, "cdMetodo") != "") {
                        editar(getSelectedKey(grid2, "cdMetodo"));
                    } else {
                      Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                    }
                }
            	},{
        		text:getLabelFromMap('metCancelBtnBorrar',helpMap,'Eliminar'),
        		tooltip: getToolTipFromMap('metCancelBtnBorrar',helpMap,'Elimina un M&eacute;todo de Cancelaci&oacute;n'),
            	handler:function(){
              			if(getSelectedRecord() != null){
	                				borrar(getSelectedRecord());
	                			}
	                	else{
	                		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
	                		}
                }
            	},{
            	text:getLabelFromMap('metCancelBtnExp',helpMap,'Exportar'),
            	tooltip: getToolTipFromMap('metCancelBtnExp',helpMap,'Exporta los Metodos de Cancelaci&oacute;n'),
                handler:function(){
                		var url = _ACTION_EXPORT_METODOS_CANCELACION + '?cdMetodo=' + cdMetodo.getValue()+ '&dsMetodo=' + dsMetodo.getValue();
                	 	showExportDialog( url );
                	 }
            	}/*,{
            	text:getLabelFromMap('metCancelBtnBack',helpMap,'Regresar'),
            	tooltip: getToolTipFromMap('metCancelBtnbBack',helpMap,'Regresa a la pantalla anterior')
            	}*/],
    	width:500,
    	frame:true,
		height:320,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		stripeRows: true,
		collapsible: true,
		bbar: new Ext.PagingToolbar({
				pageSize: itemsPerPage,
				store: store,
				displayInfo: true,
				displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
				emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
		    })
		});

    grid2.render()
}

    var incisosForm = new Ext.form.FormPanel({
    	id: 'incisosForm',
        el:'formBusqueda',
		title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('incisosForm',helpMap,'M&eacute;todos de Cancelaci&oacute;n')+'</span>',
		
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        autoHeight: true,
        labelAlign: 'right',
        frame:true,
        url:_ACTION_BUSCAR_METODOS_CANCELACION,     
        width: 500,
        height:150,
        items: [{
        		layout:'form',
				border: false,
				items:[{
        		bodyStyle:'background: white',
		        labelWidth: 100,
                layout: 'form',
				frame:true,
		       	baseCls: '',
		       	buttonAlign: "center",
        		        items: [{
        		        	html:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
        		        	layout:'column',
		 				    border:false,
		 				    columnWidth: 1,
        		    		items:[{
						    	columnWidth:.6,
                				layout: 'form',
		                		border: false,
        		        		items:[
        		        				cdMetodo,
        		        				dsMetodo
		       						]
								},{
								columnWidth:.4,
                				layout: 'form'
                				}]
                			}],
                			buttons:[{
                					text:getLabelFromMap('metCancelBtnSeek',helpMap,'Buscar'),
                					tooltip: getToolTipFromMap('metCancelBtnSeek',helpMap,'Busca un Grupo de M&eacute;todos de Cancelaci&oacute;n'),
        							handler: function() {
				               			if (incisosForm.form.isValid()) {
                                               if (grid2!=null) {
                                                reloadGrid();
                                               } else {
                                                createGrid();
                                               }
                						} else{
											Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'), getLabelFromMap('400033', helpMap,'No se encontraron registros'));
										}
									}
        							},{
                					text:getLabelFromMap('metCancelBtnCanc',helpMap,'Cancelar'),
                					tooltip: getToolTipFromMap('metCancelBtnCanc',helpMap,'Cancela Operaci&oacute;n de M&eacute;todos de Cancelaci&oacute;n'),
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

//Funcion para obtener el registro seleccionado en la grilla
function getSelectedRecord(){
             var m = grid2.getSelections();
             if (m.length == 1 ) {
                return m[0];
             }
        }
//Borra el registro seleccionado
	function borrar(record){
		Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
			{
         		if (btn == "yes"){
         			var _params = {
         					cdMetodo: record.get('cdMetodo')
                		 	         					
         			};
         			execConnection (_ACTION_BORRAR_METODOS_CANCELACION, _params, cbkBorrar);
         			reloadGrid();
				}
		})

  	}
	function cbkBorrar (_success, _message) {
		if (!_success) {
			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
		}else {
			Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function() {reloadGrid();});
		}
	}


});




function reloadGrid(){
	var _params = {
    		cdMetodo: Ext.getCmp('incisosForm').form.findField('cdMetodo').getValue(),
    		dsMetodo: Ext.getCmp('incisosForm').form.findField('dsMetodo').getValue()
	};
	reloadComponentStore(Ext.getCmp('grid2'), _params, cbkReload);
};

function cbkReload(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}
};