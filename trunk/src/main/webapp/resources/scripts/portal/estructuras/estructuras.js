Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

    var cm = new Ext.grid.ColumnModel([
        {
        id: 'cmCodigoEstructuraId',
        tooltip: getToolTipFromMap('cmCodigoEstructuraId', helpMap,'C&oacute;digo'),
        header: getLabelFromMap('cmCodigoEstructuraId', helpMap,'C&oacute;digo'),
        dataIndex: 'codigo',
        width: 240,
        sortable: true
        },
        {
        id: 'cmDescripcionEstructuraId',
        tooltip: getToolTipFromMap('cmDescripcionEstructuraId', helpMap,'Descripci&oacute;n de la estructura'),
        header: getLabelFromMap('cmDescripcionEstructuraId', helpMap,'Descripci&oacute;n'),        
        dataIndex: 'descripcion',
        width: 240,
        sortable: true
    }]);
	


    var descripcion = new Ext.form.TextField({
    	id: 'descripcionId',
        fieldLabel: getLabelFromMap('descripcionId', helpMap,'Descripci&oacute;n'),
        tooltip: getToolTipFromMap('descripcionId', helpMap,'Descripci&oacute;n'),
		//hasHelpIcon:getHelpIconFromMap('descripcionId',helpMap),
		//Ayuda: getHelpTextFromMap('descripcionId',helpMap),
		name:'descripcion',
        allowBlank: true,
        anchor: '100%'
    });


 function test(){

             store = new Ext.data.Store({

    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_BUSCAR_ESTRUCTURAS
                }),

                reader: new Ext.data.JsonReader({
            	root:'MEstructuraList',
            	totalProperty: 'totalCount',
            	id: 'codigo',
	            successProperty : '@success'
	        },[
	        {name: 'codigo',  type: 'string',  mapping:'codigo'},
	        {name: 'descripcion',  type: 'string',  mapping:'descripcion'}
			])
        });

        return store;
 	}


    var grid2;



function createGrid(){
	grid2= new Ext.grid.GridPanel({
		id: 'grid2',
        tooltip: getToolTipFromMap('grid2', helpMap),	
        el:'gridEstructuras',
        title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
        store: test(),
		border:true,
		loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
        cm: cm,
        buttonAlign: "center",
		buttons:[
        		{
        		id:'grid2ButtonAgregarId',
        		text:getLabelFromMap('grid2ButtonAgregarId', helpMap,'Agregar'),
            	tooltip:getToolTipFromMap('grid2ButtonAgregarId', helpMap,'Agrega una nueva Estructura' ),
            	handler:function(){agregar();}
            	},
            	{
        		id:'grid2ButtonEditarId',
        		text:getLabelFromMap('grid2ButtonEditarId', helpMap,'Editar'),
            	tooltip:getToolTipFromMap('grid2ButtonEditarId', helpMap,'Edita una Estructura'),
            	handler:function(){
                    if (getSelectedKey(grid2, "codigo") != "") {
                        editar(getSelectedKey(grid2, "codigo"));
                    }else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));}
                	}
            	},
            	{
        		id:'grid2ButtonBorrarId',
        		text:getLabelFromMap('grid2ButtonBorrarId', helpMap,'Eliminar'),
            	tooltip:getToolTipFromMap('grid2ButtonBorrarId', helpMap,'Elimina una Estructura'),
                handler:function(){
                    if (getSelectedKey(grid2, "codigo") != "") {
                        borrar(getSelectedKey(grid2, "codigo"));
                    } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                    }
                }
            	},
            	{
        		id:'grid2ButtonCopiarId',
        		text:getLabelFromMap('grid2ButtonCopiarId', helpMap,'Copiar'),
            	tooltip:getToolTipFromMap('grid2ButtonCopiarId', helpMap,'Copia una Estructura'),
            	handler:function(){
                    if (getSelectedKey(grid2, "codigo") != "") {
                        copiar(getSelectedKey(grid2, "codigo"));
                     }else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));}
            		}
            	},
            	{
            	id:'grid2ButtonExportarId',
        		text:getLabelFromMap('grid2ButtonExportarId', helpMap,'Exportar'),
            	tooltip:getToolTipFromMap('grid2ButtonExportarId', helpMap,'Exporta una Estructura'),
                handler:function(){                		
                		var url = _ACTION_EXPORT + '?descripcion=' + Ext.getCmp('descripcionId').getValue();
                	 	showExportDialog( url );
                	 }
            	},
            	{
            	id:'grid2ButtonConfigurarId',
        		text:getLabelFromMap('grid2ButtonConfigurarId', helpMap,'Configurar'),
            	tooltip:getToolTipFromMap('grid2ButtonConfigurarId', helpMap,'Configura una Estructura'),
            	handler:function(){
                    if (getSelectedKey(grid2, "codigo") != "") {
                        configurar(getSelectedKey(grid2, "codigo"),getSelectedKey(grid2, "descripcion"));
                    } else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));}
                	}
            	}],
    	width:505,
    	frame:true,
		height: 320,
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
    	id: 'incisosFormId',
        el:'formBusqueda',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('incisosFormId', helpMap,'Cat&aacute;logo de Estructuras')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,
        url:_ACTION_BUSCAR_ESTRUCTURAS,
        width: 505,
        height:135,
        items: [{
        		layout:'form',
				border: false,
				items:[{
                bodyStyle:'background: white',
		        labelWidth: 100,
                layout: 'form',
                title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br/>',
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
        		        				descripcion
		       						]
								},{
								columnWidth:.4,
                				layout: 'form'
                				}]
                			}],
                			buttons:[{
                					id: 'incisosFormButtonBuscarId',
        							text: getLabelFromMap('incisosFormButtonBuscarId', helpMap,'Buscar'),
        							tooltip: getToolTipFromMap('incisosFormButtonBuscarId', helpMap,'Busca una Estructura'),
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
        							id:'incisosFormButtonCancelarId',
        							text: getLabelFromMap('incisosFormButtonCancelarId', helpMap,'Cancelar'),
        							tooltip: getToolTipFromMap('incisosFormButtonCancelarId', helpMap,'Cancela la B&uacute;squeda'),
        							handler: function(){incisosForm.form.reset();}
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

    	function borrar(key) {
    			if(key != "")
				{
					Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
					
					//Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se borrara el registro seleccionado'),function(btn)
					{
	           	       if (btn == "yes")
	           	        {
	           	        	var _params = {
	           	        				codigo: key
	           	        	};
	           	        	execConnection(_ACTION_BORRAR_ESTRUCTURA, _params, cbkExecConnection);
	                	}
					})
				}else{
						Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
				}
        };
     
		function configurar(key,desc)
		{
			window.location=_ACTION_IR_CONFIGURAR_ESTRUCTURA+"?codigo="+key+"&descripcion="+unescape(desc);
		};

		function copiar(key)
		{	        			
			if(key != "")
			{
				Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400032', helpMap,'Se copiar&aacute; el registro seleccionado'),function(btn)
				{
           	       if (btn == "yes")
           	        {
           	        	var _params = {
           	        			codigo: key
           	        	};
						execConnection(_ACTION_COPIAR_ESTRUCTURA, _params, cbkExecConnection);
                	}
				})
			}else{
					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
			}
		};
		function cbkExecConnection (_success, _message) {
			if (!_success) {
				Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
			}else {
				Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid()});
			}
		}
		
	if (CODIGO_ESTRUCTURA){reloadGrid();}
		
		

});

function reloadGrid(){

	var _params = {
    			descripcion: Ext.getCmp('incisosFormId').form.findField('descripcion').getValue()
	};
	reloadComponentStore(Ext.getCmp('grid2'), _params, cbkReload);
}

function cbkReload (_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();		
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}
}



