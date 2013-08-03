
Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";


    var cm = new Ext.grid.ColumnModel([
        {
       	header: "cdPerson",
        dataIndex: 'cdPerson',
        hidden : true
        },{
      	header: getLabelFromMap('cmDsNombreEjecutivosCuenta',helpMap,'Cliente'),
   		tooltip: getToolTipFromMap('cmDsNombreEjecutivosCuenta', helpMap,'Cliente'),	   	
        //header: "Cliente",
        dataIndex: 'dsNombre',
        width: 90,
        sortable: true
        },{
	    header: "cdTipRam",
        dataIndex: 'cdTipRam',
        hidden : true
        },{
      	header: getLabelFromMap('cmDsTipRamEjecutivosCuenta',helpMap,'Ramo'),
   		tooltip: getToolTipFromMap('cmDsTipRamEjecutivosCuenta', helpMap,'Ramo'),	   	
        //header: "Ramo",
        dataIndex: 'dsTipRam',
        width: 88,
        sortable: true
        },{
        header: "cdRamo",
        dataIndex: 'cdRamo',
        hidden : true
        },{
      	header: getLabelFromMap('cmDsRamoEjecutivosCuenta',helpMap,'Producto'),
   		tooltip: getToolTipFromMap('cmDsRamoEjecutivosCuenta', helpMap,'Producto'),	   	
        //header: "Producto",
        dataIndex: 'dsRamo',
        width: 100,
        sortable: true
        },{
        header: "cdGrupo",
        dataIndex: 'cdGrupo',
        hidden : true
        },{
      	header: getLabelFromMap('cmDesGrupoEjecutivosCuenta',helpMap,'Grupo de Personas'),
   		tooltip: getToolTipFromMap('cmDsNombreEjecutivosCuenta', helpMap,'Grupo de Personas'),	   	
        //header: "Grupo de Personas",
        dataIndex: 'desGrupo',
        width: 120,
        sortable: true
        },{
        header: "cdAgente",
        dataIndex: 'cdAgente',
        hidden : true
        },{
      	header: getLabelFromMap('cmNomAgenteEjecutivosCuenta',helpMap,'Ejecutivo'),
   		tooltip: getToolTipFromMap('cmDsNombreEjecutivosCuenta', helpMap,'Ejecutivo'),	   	
        //header: "Ejecutivo",
        dataIndex: 'nomAgente',
        width: 100,
        sortable: true
		},{
      	header: getLabelFromMap('cmTipoAgenteEjecutivosCuenta',helpMap,'Tipo de Ejecutivo'),
   		tooltip: getToolTipFromMap('cmDsNombreEjecutivosCuenta', helpMap,'Tipo de Ejecutivo'),	   	
        //header: "Tipo de Ejecutivo",
        dataIndex: 'dsTipage',
        width: 170,
        sortable: true
        },{
        header: "cdEstado",
        dataIndex: 'cdEstado',
        hidden : true
        },{
      	header: getLabelFromMap('cmDsEstadoEjecutivosCuenta',helpMap,'Estado'),
   		tooltip: getToolTipFromMap('cmDsNombreEjecutivosCuenta', helpMap,'Estado'),	   	
        //header: "Estado",
        dataIndex: 'dsEstado',
        width: 98,
        sortable: true
        }]);


 function test(){

                store = new Ext.data.Store({

    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_BUSCAR_EJECUTIVOS_CUENTA
                }),

                reader: new Ext.data.JsonReader({
            	root:'MEjecutivosCuentaList',
            	totalProperty: 'totalCount',
            	//id: 'cdPerson',
	            successProperty : '@success'
	        },[
	        {name: 'cdPerson',  type: 'string',  mapping:'cdPerson'},
	        {name: 'dsNombre',  type: 'string',  mapping:'dsNombre'},
	        {name: 'cdTipRam',  type: 'string',  mapping:'cdTipRam'},
	        {name: 'dsTipRam',  type: 'string',  mapping:'dsTipRam'},
	        {name: 'cdRamo',  type: 'string',  mapping:'cdRamo'},
	        {name: 'dsRamo',  type: 'string',  mapping:'dsRamo'},
	        {name: 'cdGrupo',  type: 'string',  mapping:'cdGrupo'},
	        {name: 'desGrupo',  type: 'string',  mapping:'desGrupo'},
	        {name: 'cdAgente',  type: 'string',  mapping:'cdAgente'},
	        {name: 'nomAgente',  type: 'string',  mapping:'nomAgente'},
	        {name: 'cdEstado',  type: 'string',  mapping:'cdEstado'},
	        {name: 'dsEstado',  type: 'string',  mapping:'dsEstado'},
	        {name: 'cdElemento',  type: 'string',  mapping:'cdElemento'},
	        {name: 'cdTipage',  type: 'string',  mapping:'cdTipage'},
	        {name: 'dsTipage',  type: 'string',  mapping:'dsTipage'}
			])
           
        		        				
        });

       return store;
 	}


    var grid2;



function createGrid(){
	grid2= new Ext.grid.GridPanel({
		id: 'grid2',
        el:'gridEjecutivosCuenta',
        //title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
        title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">'+getLabelFromMap('grid2', helpMap,'Listado')+'</span>',
        store:test(),
        loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
		border:true,
		cm: cm,
		buttonAlign:'center',
		buttons:[
        		{
  				text:getLabelFromMap('gridEjctCntButtonAgregar', helpMap,'Agregar'),
       			tooltip:getToolTipFromMap('gridEjctCntButtonAgregar', helpMap,'Agrega Nuevo Ejecutivo por Cuenta'),			        			
            	handler:function(){
                agregar("I");
                }
            	},{
  				text:getLabelFromMap('gridEjctCntButtonEditar', helpMap,'Editar'),
       			tooltip:getToolTipFromMap('gridEjctCntButtonEditar', helpMap,'Edita Ejecutivo por Cuenta'),			        			
            	handler:function(){
                     if (getSelectedKey(grid2, "cdAgente") != "") {
                        editar(getSelectedRecord(grid2),"U");
                    } else {
                           Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                          }
            	}
            	},
            	{
  				 text:getLabelFromMap('gridEjctCntButtonBorrar', helpMap,'Eliminar'),
       			 tooltip:getToolTipFromMap('gridEjctCntButtonBorrar', helpMap,'Elimina Ejecutivo por Cuenta'),			        			
                 handler:function(){
                    if (getSelectedKey(grid2, "cdAgente") != "") {
                        borrar(getSelectedRecord(grid2));
                    } else {
                          Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                        }
                }
               },{
		      				text:getLabelFromMap('gridEjctCntButtonExportar', helpMap,'Exportar'),
		           			tooltip:getToolTipFromMap('gridEjctCntButtonExportar', helpMap,'Exportar la Grilla de B&uacute;squeda'),			        			
		                	handler:function(){
                                  var url = _ACTION_EXPORTAR_EJECUTIVOS_CUENTA + '?dsNombre=' + Ext.getCmp('incisosForm').form.findField('dsCliente').getValue() +
													'&nomAgente=' + Ext.getCmp('incisosForm').form.findField('dsEjecutivo').getValue() +
													'&desGrupo=' + Ext.getCmp('incisosForm').form.findField('dsGrupoPersonas').getValue()
                	 	          showExportDialog( url );
                               	}
		        }/*,{
  				text:getLabelFromMap('gridEjctCntButtonRegresar', helpMap,'Regresar'),
       			tooltip:getToolTipFromMap('gridEjctCntButtonRegresar', helpMap)			        			
            	}*/
            	],
            	
    	width:500,
    	frame:true,
		height:578,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		stripeRows: true,
		collapsible: true,
		bbar: new Ext.PagingToolbar({
				pageSize: itemsPerPage,
				store: store,
				displayInfo: true,
				displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} de {2}'),
				beforePageText : getLabelFromMap('400123', helpMap,'P&aacute;gina'),
				afterPageText : getLabelFromMap('400124', helpMap,'de {0}'),
				firstText : getLabelFromMap('400125', helpMap,'Primera P&aacute;gina'),
				prevText : getLabelFromMap('400126', helpMap,'P&aacute;gina Anterior'),
				nextText : getLabelFromMap('400127', helpMap,'Siguiente P&aacute;gina'),
				lastText : getLabelFromMap('400128', helpMap,'Ultima P&aacute;gina'),
				refreshText : getLabelFromMap('400129', helpMap,'Actualizar'),
				emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
		    })
		});

    grid2.render()
}



    var dsCliente = new Ext.form.TextField({
	    fieldLabel: getLabelFromMap('txtFldEjctCntCliente', helpMap,'Cliente'), 
	    tooltip: getToolTipFromMap('txtFldEjctCntCliente', helpMap,'Cliente'),
	    hasHelpIcon:getHelpIconFromMap('txtFldEjctCntCliente',helpMap),
		Ayuda: getHelpTextFromMap('txtFldEjctCntCliente',helpMap),
        name:'dsCliente',
        width: 180
    });


    var dsEjecutivo = new Ext.form.TextField({
	    fieldLabel: getLabelFromMap('txtFldEjctCntEjecutivo', helpMap,'Ejecutivo'), 
    	tooltip: getToolTipFromMap('txtFldEjctCntEjecutivo', helpMap,'Ejecutivo'),   
    	 hasHelpIcon:getHelpIconFromMap('txtFldEjctCntEjecutivo',helpMap),
		Ayuda: getHelpTextFromMap('txtFldEjctCntEjecutivo',helpMap),
        name:'dsEjecutivo',
        width: 180
    });
    
    var dsGrupoPersonas = new Ext.form.TextField({
	    fieldLabel: getLabelFromMap('txtFldEjctCntGrupoPersonas', helpMap,'Grupo de Personas'), 
    	tooltip: getToolTipFromMap('txtFldEjctCntGrupoPersonas', helpMap,'Grupo de Personas'),   
    	hasHelpIcon:getHelpIconFromMap('txtFldEjctCntGrupoPersonas',helpMap),
		Ayuda: getHelpTextFromMap('txtFldEjctCntGrupoPersonas',helpMap),
        name:'dsGrupoPersonas',
        width: 180
    });
    

    var incisosForm = new Ext.form.FormPanel({
    	id: 'incisosForm',
        el:'formBusqueda',
		title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('incisosFormEjctCntId', helpMap,'Ejecutivos por Cuenta')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,
        url: _ACTION_BUSCAR_EJECUTIVOS_CUENTA,
        width: 500,
        autoHeight: true,
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
        		        	layout:'column',
		 				    border:false,
		 				    columnWidth: 1,
		 				    html: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">'+getLabelFromMap('incisosFormBuscar', helpMap,'B&uacute;squeda')+'</span><br>',
        		    		items:[{
						    	columnWidth:.6,
                				layout: 'form',
		                		border: false,
        		        		items:[
        		        				dsCliente,
        		        				dsEjecutivo,
        		        				dsGrupoPersonas
		       						]
								},{
								columnWidth:.4,
                				layout: 'form'
                				}]
                			}],
                			buttons:[{
  			 		   				text:getLabelFromMap('formEjctCntButtonAgregar', helpMap,'Buscar'),
        				   			tooltip:getToolTipFromMap('formEjctCntButtonAgregar', helpMap,'Busca Grupo de Ejecutivos por Cuenta'),			        			
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
        							},{
  			 		   				text:getLabelFromMap('formEjctCntButtonCancelar', helpMap,'Cancelar'),
        				   			tooltip:getToolTipFromMap('formEjctCntButtonCancelar', helpMap,'Cancela la operaci&oacute;n de b&uacute;squeda'),			        			
        							handler: function() {
        								incisosForm.form.reset();
        							}
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

  
    function borrar(record) {
    			if( record.get('cdAgente') != "" && record.get('cdElemento') != "")
				{
					var conn = new Ext.data.Connection();
				
					Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
					{
	           	       if (btn == "yes")
	           	        {
	           	        	var _params = {
				              cdElemento: record.get('cdElemento'),
				              cdAgente: record.get('cdAgente')
				          	};
	           	        	execConnection(_ACTION_BORRAR_EJECUTIVOS_CUENTA, _params, cbkBorrar);

	                	}
					})
				}else{
						Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
						//Ext.Msg.alert('Advertencia', 'Debe seleccionar un Ejecutivo por Cuenta para borrar');
					}
	};
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
    		dsNombre: Ext.getCmp('incisosForm').form.findField('dsCliente').getValue(),
    		nomAgente: Ext.getCmp('incisosForm').form.findField('dsEjecutivo').getValue(),
    		desGrupo: Ext.getCmp('incisosForm').form.findField('dsGrupoPersonas').getValue()
    }
    reloadComponentStore(Ext.getCmp('grid2'), _params, cbkReload);
};

function cbkReload (_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}
};