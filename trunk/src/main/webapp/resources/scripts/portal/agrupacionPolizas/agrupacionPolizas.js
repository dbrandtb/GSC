Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

 //cantidad a estructuras a mostrar por pagina

    var cm = new Ext.grid.ColumnModel([
        {
       
        header: "cdGrupo",
        dataIndex: 'cdGrupo',
        hidden : true
        },{
        header: " cdElemento",
        dataIndex: 'cdElemento',
        hidden : true
        },{
        header: " cdPerson",
        dataIndex: 'cdPerson',
        hidden : true
        },{
        header: getLabelFromMap('agrPolCmCli',helpMap,'Cliente'),
        tooltip: getToolTipFromMap('agrPolCmCli',helpMap,'Cliente'),
        dataIndex: 'dsElemen',
        sortable: true,
        width: 100
        },{
        header: "cdUnieco",
        dataIndex: 'cdUnieco',
        hidden : true
        },{
        header: getLabelFromMap('agrPolCmAseg',helpMap,'Aseguradora'),
        tooltip: getToolTipFromMap('agrPolCmAseg',helpMap,'Aseguradora'),
        dataIndex: 'dsUnieco',
        sortable: true,
        width: 100
        },{
        header: "cdTipram",
        dataIndex: 'cdTipram',
        hidden : true
        },{
        header: getLabelFromMap('agrPolCmTipRam',helpMap,'Tipo de Ramo'),
        tooltip: getToolTipFromMap('agrPolCmTipRam',helpMap,'Tipo de Ramo'),
        dataIndex: 'dsTipram',
        sortable: true,
        width: 110
        },{
        header: "cdRamo",
        dataIndex: 'cdRamo',
        hidden : true
        },{
        header: getLabelFromMap('agrPolCmProd',helpMap,'Producto'),
        tooltip: getToolTipFromMap('agrPolCmProd',helpMap,'Producto'),
        dataIndex: 'dsRamo',
        sortable: true,
        width: 100
        },{
        header: "cdTipo",
        dataIndex: 'cdTipo',
        hidden : true
        },{
        header: getLabelFromMap('agrPolCmTipAgr',helpMap,'Tipo de Agrupaciones'),
        tooltip: getToolTipFromMap('agrPolCmTipAgr',helpMap,'Tipo de Agrupaciones'),
        dataIndex: 'dsAgrupa',
        sortable: true,
        width: 140
        },{
        header: "cdAgrupa",
        dataIndex: 'cdAgrupa',
        hidden : true
        },{
        header: "cdEstado",
        dataIndex: ' cdEstado',
        hidden : true
        },{
        header: getLabelFromMap('agrPolCmEst',helpMap,'Estado'),
        tooltip: getToolTipFromMap('agrPolCmEst',helpMap,'Estado'),
        dataIndex: 'dsEstado',
        sortable: true,
        width: 80
        }]);        


 function test(){

                store = new Ext.data.Store({

    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_BUSCAR_AGRUPACION_POLIZAS,
				waitMsg: getLabelFromMap('400017',helpMap,'Espere por favor...')				
                }),

                reader: new Ext.data.JsonReader({
            	root:'listaPolizas',
            	totalProperty: 'totalCount',
            	id: 'cdGrupo',
	            successProperty : '@success'
	        },[
	        {name: 'cdGrupo',  type: 'string',  mapping:'cdGrupo'},
	        {name: 'cdElemento',  type: 'string',  mapping:'cdElemento'},
	        {name: 'dsElemen',  type: 'string',  mapping:'dsElemen'},
	        {name: 'cdUnieco',  type: 'string',  mapping:'cdUnieco'},
	        {name: 'dsUnieco',  type: 'string',  mapping:'dsUnieco'},
	        {name: 'cdTipram',  type: 'string',  mapping:'cdTipram'},
	        {name: 'dsTipram',  type: 'string',  mapping:'dsTipram'},
	        {name: 'cdRamo',  type: 'string',  mapping:'cdRamo'},
	        {name: 'dsRamo',  type: 'string',  mapping:'dsRamo'},
	        {name: 'cdTipo',  type: 'string',  mapping:'cdTipo'},
	        {name: 'dsAgrupa',  type: 'string',  mapping:'dsAgrupa'},
	        {name: 'cdAgrupa',  type: 'string',  mapping:'cdAgrupa'},
	        {name: 'cdEstado',  type: 'string',  mapping:'cdEstado'},
	        {name: 'dsEstado',  type: 'string',  mapping:'dsEstado'}
			])           
        		        				
        });

       return store;
 	}


    var grid2;



function createGrid(){
	grid2= new Ext.grid.GridPanel({
		id: 'grid2',
        el:'gridAgrupacionPolizas',
        title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
        store:test(),
		border:true,
		buttonAlign:'center',
		loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
		cm: cm,
		buttons:[
        		{
        		text:getLabelFromMap('agrPolBtnAdd',helpMap,'Agregar'),
        		tooltip: getToolTipFromMap('agrPolBtnAdd',helpMap, 'Agrega Agrupaci&oacute;n por P&oacute;lizas'),
            	handler:function(){
                agregar();
                }
            	},{
            	text:getLabelFromMap('agrPolBtnEdit',helpMap,'Editar'),
        		tooltip: getToolTipFromMap('agrPolBtnEdit',helpMap,'Edita Agrupaci&oacute;n por P&oacute;lizas'),
            	handler:function(){
                     if (getSelectedKey(grid2, "cdGrupo") != "") {
                        editar(getSelectedRecord(grid2, "cdGrupo"));
                        reloadGrid(grid2);
                    } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                    }
            	}
            	},{
            	text:getLabelFromMap('agrPolBtnDel',helpMap,'Eliminar'),
        		tooltip: getToolTipFromMap('agrPolBtnDel',helpMap,'Elimina Agrupaci&oacute;n por P&oacute;lizas'),
                handler:function(){
                    if (getSelectedKey(grid2, "cdGrupo") != "") {
                        borrar(getSelectedKey(grid2, "cdGrupo"));
                    } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                    }
                }
               },{
                 text:getLabelFromMap('agrPolBtnExp',helpMap,'Exportar'),
        			tooltip: getToolTipFromMap('agrPolBtnExp',helpMap,'Exportar Agrupaci&oacute;n por P&oacute;liza'),
                    handler:function(){
                        var url = _ACTION_EXPORTAR_CONFIGURAR_AGRUPACION + '?cliente=' + dsCliente.getValue()+ '&tipoRamo=' + dsTipoRamo.getValue()+ '&tipoAgrupacion=' + dsTipoAgrupacion.getValue() + '&aseguradora=' + dsAseguradora.getValue() + '&producto=' + dsProducto.getValue();
                	 	showExportDialog( url );
                        }
            	},{
            	text:getLabelFromMap('agrPolBtnCon',helpMap,'Configurar'),
        		tooltip: getToolTipFromMap('agrPolBtnCon',helpMap,'Configurar Agrupaci&oacute;n por P&oacute;lizas'),
                handler:function(){
                    if (getSelectedKey(grid2, "cdGrupo") != "") {
                        configurar(getSelectedRecord(grid2));                       
                    } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                    }
                	}
               }/*,
               {
            	text:getLabelFromMap('agrPolBtnBack',helpMap,'Regresar'),
        		tooltip: getToolTipFromMap('agrPolBtnBack',helpMap)
                }*/
               ],
   
    	width:505,
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



    var dsCliente = new Ext.form.TextField({
    	id:'agrPolTxtCli',
        fieldLabel: getLabelFromMap('agrPolTxtCli',helpMap,'Cliente'),
        tooltip: getToolTipFromMap('agrPolTxtCli',helpMap,'Cliente'),
        hasHelpIcon:getHelpIconFromMap('agrPolTxtCli',helpMap),
		Ayuda: getHelpTextFromMap('agrPolTxtCli',helpMap),
		id:'agrPolTxtCli',
        name:'dsCliente',
        width: 180
    });


    var dsTipoRamo = new Ext.form.TextField({
    	id:'agrPolTxtTipRam',
        fieldLabel: getLabelFromMap('agrPolTxtTipRam',helpMap,'Tipo de Ramo'),
        tooltip: getToolTipFromMap('agrPolTxtTipRam',helpMap,'Tipo de Ramo'),
        hasHelpIcon:getHelpIconFromMap('agrPolTxtTipRam',helpMap),
		Ayuda: getHelpTextFromMap('agrPolTxtTipRam',helpMap),
        name:'dsTipoRamo',
        width: 180
    });
    
    
    var dsTipoAgrupacion = new Ext.form.TextField({
    	id:'agrPolTxtTipAgr',
        fieldLabel: getLabelFromMap('agrPolTxtTipAgr',helpMap,'Tipo de Agrupaci&oacute;n'),
        tooltip: getToolTipFromMap('agrPolTxtTipAgr',helpMap,'Tipo de Agrupaci&oacute;n'),
        hasHelpIcon:getHelpIconFromMap('agrPolTxtTipAgr',helpMap),
		Ayuda: getHelpTextFromMap('agrPolTxtTipAgr',helpMap),
        name:'dsTipoAgrupacion',
        width: 180
    });
    
    var dsAseguradora = new Ext.form.TextField({
    	id:'agrPolTxtAseg',
        fieldLabel: getLabelFromMap('agrPolTxtAseg',helpMap,'Aseguradora'),
        tooltip: getToolTipFromMap('agrPolTxtAseg',helpMap,'Aseguradora'),
         hasHelpIcon:getHelpIconFromMap('agrPolTxtAseg',helpMap),
		Ayuda: getHelpTextFromMap('agrPolTxtAseg',helpMap),
        name:'dsAseguradora',
        width: 180
    });
    
    var dsProducto = new Ext.form.TextField({
    	id:'agrPolTxtPro',
        fieldLabel: getLabelFromMap('agrPolTxtPro',helpMap,'Producto'),
        tooltip: getToolTipFromMap('agrPolTxtPro',helpMap,'Producto'),
         hasHelpIcon:getHelpIconFromMap('agrPolTxtPro',helpMap),
		Ayuda: getHelpTextFromMap('agrPolTxtPro',helpMap),
        name:'dsProducto',
        width: 180
    });
    
    

    var incisosForm = new Ext.form.FormPanel({
    	id: 'incisosForm',
        el:'formBusqueda',
		title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('incisosForm',helpMap,'Agrupaci&oacute;n por P&oacute;lizas')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,
        url: _ACTION_BUSCAR_AGRUPACION_POLIZAS,
        width: 505,
        heigth: 315,
        autoHeight: true,
        items: [{
        		 layout:'form',
				 border: false,
				 items:[{
				        bodyStyle:'background: white',
                        labelWidth: 100,
                        layout: 'column',
				        frame:true,
		       	        baseCls: '',
		       	        buttonAlign: "center",
        		        items: [{
        		        	    layout:'column',
		 				        border:false,
		 				        columnWidth: 1,
		 				        html: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
        		    		    items:[{
						    	       columnWidth:.1,
                				       layout: 'form',
                				       html:'&nbsp;'
        		    	      	      },
        		    				  {
						    	       columnWidth:.6,
                				       layout: 'form',
		                		       border: false,
        		        		       items:[
        		        				      dsCliente,
        		        				      dsTipoRamo,
        		        				      dsTipoAgrupacion,
        		        				      dsAseguradora,
        		        				      dsProducto
		       						         ]
								      },
									  {
								       columnWidth:.3,
                				       layout: 'form'
                				      }]
                			 }],
                			buttons:[{
        							text:getLabelFromMap('agrPolBtnFind',helpMap,'Buscar'),
        							tooltip: getToolTipFromMap('agrPolBtnFind',helpMap,'Busca Agrupaci&oacute;n  P&oacute;lizas'),
        							handler: function() {
				               			if (incisosForm.form.isValid()) {
                                               if (grid2!=null) {
                                                 reloadGrid(grid2);
                                               } else {
                                                 createGrid();
                                               }
                						} else{
											Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
										}
									}
        							},{
        							text:getLabelFromMap('agrPolBtnCancel',helpMap,'Cancelar'),
        							tooltip: getToolTipFromMap('agrPolBtnCancel',helpMap,'Cancela Agrupaci&oacute;n P&oacute;lizas'),
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
    
    
    function borrar(key) {
        if(key != "")
        {
            Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
            {
                if (btn == "yes")
                {
                	var _params = {cveAgrupa: key};
                	execConnection(_ACTION_BORRAR_AGRUPACION_POLIZAS, _params, cbkBorrar);
				}
            }
            )
        }else{
                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
    	}
};
function cbkBorrar(_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid(grid2)});
	}
}

    function configurar(record)	{
			if (record.get('cdTipo') == "1") {
				window.location=_ACTION_IR_CONFIGURAR_AGRUPACION+"?cdTipo ="+record.get('cdTipo')+"& codigoConfiguracion="+record.get('cdGrupo');
			}else {
				window.location=_ACTION_IR_CONFIGURAR_AGRUPACION+"?cdTipo ="+record.get('cdTipo')+"& cveAgrupa ="+record.get('cdGrupo');
			}
	};
	 
});
function reloadGrid(grilla){
	var _params = {
    			cliente: Ext.getCmp('incisosForm').form.findField('dsCliente').getValue(),
    			tipoRamo: Ext.getCmp('incisosForm').form.findField('dsTipoRamo').getValue(),
    			tipoAgrupacion: Ext.getCmp('incisosForm').form.findField('dsTipoAgrupacion').getValue(),
    			aseguradora: Ext.getCmp('incisosForm').form.findField('dsAseguradora').getValue(),
    			producto: Ext.getCmp('incisosForm').form.findField('dsProducto').getValue() 
	};
	reloadComponentStore(grilla, _params, cbkReload);
}
function cbkReload(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}
}
