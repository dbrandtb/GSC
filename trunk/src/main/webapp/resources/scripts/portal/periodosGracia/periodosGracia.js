Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

    var cm = new Ext.grid.ColumnModel([
        {      
        header: "cdTramo",
        dataIndex: 'cdTramo',
        hidden : true
        },{
        header: getLabelFromMap('pgCmDesc',helpMap,'Descripci&oacute;n'),
        tooltip: getToolTipFromMap('pgCmDesc',helpMap,'Descripci&oacute;n'),
        dataIndex: 'dsTramo',
        sortable: true,
        width: 100
        },{        
        header: getLabelFromMap('pgCmDelRec',helpMap,'Del Recibo'),
        tooltip: getToolTipFromMap('pgCmDelRec',helpMap,'Del Recibo'),
        dataIndex: 'nmMinimo',
        sortable: true,
        width: 90
        
        },{
        header: getLabelFromMap('pgCmAlRec',helpMap,'Al Recibo'),
        tooltip: getToolTipFromMap('pgCmAlRec',helpMap,'Al Recibo'),
        dataIndex: 'nmMaximo',
        sortable: true,
        width: 90
        },{
        header: getLabelFromMap('pgCmDiasGr',helpMap,'D&iacute;as de Gracia'),
        tooltip: getToolTipFromMap('pgCmDiasGr',helpMap,'D&iacute;as de Gracia'),
        dataIndex: 'diasGrac',
        sortable: true,
        align: 'center',
        width: 95
        },{
        header: getLabelFromMap('pgCmDiasCan',helpMap,'D&iacute;as antes cancel.'),
        tooltip: getToolTipFromMap('pgCmDiasCan',helpMap,'D&iacute;as antes cancel.'),
        dataIndex: 'diasCanc',
        sortable: true,
        align: 'center',
        width: 130
        }]);


 function test(){

                store = new Ext.data.Store({

    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_BUSCAR_PERIODOS_GRACIA
                }),
				waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
				waitMsg: getLabelFromMap('400028',helpMap,'Leyendo datos ...'),
                reader: new Ext.data.JsonReader({
            	root:'listaPeriodosGracia',
            	totalProperty: 'totalCount',
            	id: 'cdTramo',
	            successProperty : '@success'
	        },[
			        {name: 'cdTramo', type: 'string', mapping:'cdTramo'},
			        {name: 'dsTramo', type: 'string', mapping:'dsTramo'},
			        {name: 'nmMinimo', type: 'string', mapping:'nmMinimo'},
			        {name: 'nmMaximo', type: 'string', mapping:'nmMaximo'},
			        {name: 'diasGrac', type: 'string', mapping:'diasGrac'},
			        {name: 'diasCanc', type: 'string', mapping:'diasCanc'}	        
			])			                   		        				
        });

       return store;
 	}


    var grid2;



function createGrid(){
	grid2= new Ext.grid.GridPanel({
        el:'gridPeriodosGracia',
        store:test(),
		border:true,
		title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
		cm: cm,
		buttonAlign:'center',
		loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
		buttons:[
        		{text:getLabelFromMap('pgBtnAdd', helpMap,'Agregar'),
            	tooltip:getToolTipFromMap('pgBtnAdd',helpMap,'Agrega un nuevo per&iacute;odo de gracia'),
            	handler:function(){
                agregar();                
                }
            	},{
            	text:getLabelFromMap('pgBtnEdit', helpMap,'Editar'),
            	tooltip:getToolTipFromMap('pgBtnEdit',helpMap,'Edita un per&iacute;odo de gracia'),
            	handler:function(){
                     if (getSelectedKey(grid2, "cdTramo") != "") {
                        editar(getSelectedRecord(grid2));
                     } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                     }
            	}
            	},{
            	text:getLabelFromMap('pgBtnDel', helpMap,'Eliminar'),
            	tooltip:getToolTipFromMap('pgBtnDel',helpMap,'Elimina un per&iacute;odo de gracia'),
                handler:function(){
                    if (getSelectedKey(grid2, "cdTramo") != "") {
                        borrar(getSelectedRecord(grid2));
                    } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                    }
                }
               },{
                text:getLabelFromMap('pgBtnExp', helpMap,'Exportar'),
        		tooltip:getToolTipFromMap('pgBtnExp',helpMap,'Exporta la lista a un formato PDF, XSL, TXT, etc...'),
                handler:function(){
                    var url =  _ACTION_EXPORTAR_PERIODOS_GRACIA + '?descripcion=' + incisosForm.findById("descripcion").getValue();
            	 	showExportDialog( url );
                    }
            	}/*,{
            	text:getLabelFromMap('pgBtnBack', helpMap,'Regresar'),
            	tooltip:getToolTipFromMap('pgBtnBack',helpMap,'Regresa a la pantalla anterior')
                }*/
               ],
            	
            	
    	width:500,  //650,
    	frame:true,
		height:370,
		
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
};

	function validar(){

		if ((Ext.getCmp('addPgTextDelRec').getValue())>(Ext.getCmp('addPgTextAlRec').getValue())){
 			     Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400092', helpMap,'El valor "Del Recibo" debe ser menor o igual que el valor "Al Recibo"'));
		   		 return false;
		   	}else {
	               return true;
		   	       }
	};

	function validar_edit(){
    	
    	 if ((Ext.getCmp('edPgTextDescr').getValue())>(Ext.getCmp('edPgTextAlRec').getValue())){
  			       Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400092', helpMap,'El valor "Del Recibo" debe ser menor o igual que el valor "Al Recibo"'));
  			       return false;
		   	 }else{
		            return true;
		   	      }
    };
		   		
		   		
		   		
function reloadGrid(){
	var _params = {
			descripcion: incisosForm.findById("descripcion").getValue()
	};
	reloadComponentStore(grid2, _params, cbkReload);
}
function cbkReload (_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}
}

    var dsTramo = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('pgTxtDescIdTxt', helpMap,'Descripci&oacute;n'),
        tooltip:getToolTipFromMap('pgTxtDescIdTxt',helpMap,'Descripci&oacute;n del per&iacute;odo de gracia'),
        hasHelpIcon:getHelpIconFromMap('pgTxtDescIdTxt',helpMap),
		Ayuda: getHelpTextFromMap('pgTxtDescIdTxt',helpMap),
        hasHelpIcon:getHelpIconFromMap('descripcionId',helpMap),
		Ayuda: getHelpTextFromMap('descripcionId',helpMap),
        name:'dsTramo',
        width: 60
    });
    

    var incisosForm = new Ext.form.FormPanel({
        el:'formBusqueda',
		title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('formBusqueda',helpMap,'Per&iacute;odos de Gracia')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,
        url: _ACTION_BUSCAR_PERIODOS_GRACIA,
        width: 500,   // 650,
        autoHeight: true,
        items: [{
        		layout:'form',
				border: false,
				items:[{
	        		bodyStyle:'background: white',
			        labelWidth: 100,
				    layout:'column',
				    frame:true,
	        	    html:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
			       	baseCls: '',
			       	buttonAlign: "center",
        		        items: [{
			     		        layout:'column',
			     		        labelAlign:'right',
							    border:false,
							    columnWidth: 1,
        		        		
        		        	items:[
        		    			   {
								    columnWidth:.1,
                				    layout: 'form',
                				    html:'&nbsp;'
                				  },
								  {
						    	   columnWidth:.6,
                				   layout: 'form',
		                		   border: false,
								 
								  items:[{	
								          xtype: 'textfield', fieldLabel: getLabelFromMap('descripcion', helpMap,'Descripci&oacute;n'),
        						          tooltip:getToolTipFromMap('descripcion',helpMap,'Descripci&oacute;n del per&iacute;odo de gracia'),
        						          hasHelpIcon:getHelpIconFromMap('descripcion',helpMap),
								          Ayuda: getHelpTextFromMap('descripcion',helpMap),
        						          id: 'descripcion', name: 'descripcion', width:'160'
        						        }]
        						  },
        						  {
								   columnWidth:.3,
                				   layout: 'form'
        						  }]
                			}],
                			buttons:[{
        							text:getLabelFromMap('pgBtnFind', helpMap,'Buscar'),
        							tooltip:getToolTipFromMap('pgBtnFind',helpMap,'Busca per&iacute;odos por descripci&oacute;n'),
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
        							text:getLabelFromMap('pgBtnCancel', helpMap,'Cancelar'),
        							tooltip:getToolTipFromMap('pgBtnCancel',helpMap,'Cancela b&uacute;squeda de per&iacute;odos por descripci&oacute;n'),
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

 
    function borrar(record) {
    			if( record.get('cdTramo') != "" )
				{
					Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
					{
	           	     if (btn == "yes")
	           	        {
							var _params = {
									cdTramo: record.get('cdTramo')
							};
							execConnection(_ACTION_BORRAR_PERIODOS_GRACIA, _params, cbkConnection);
	                	}
					})
				}else{
						Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
				}
    };
  	function cbkConnection (_success, _message) {
  		if (!_success) {
  			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
  		}else {
  			Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid();});
  		}
  	}


function editar(record) {
    var periodosgracia_reg = new Ext.data.JsonReader({
						root: 'listaPeriodosGracia',
						totalProperty: 'totalCount',
						successProperty: '@success'
						}, [
					        {name: 'cdTramo', type: 'string', mapping:'cdTramo'},
					        {name: 'dsTramo', type: 'string', mapping:'dsTramo'},
			    		    {name: 'nmMinimo', type: 'string', mapping:'nmMinimo'},
			        		{name: 'nmMaximo', type: 'string', mapping:'nmMaximo'},
			        		{name: 'diasGrac', type: 'string', mapping:'diasGrac'},
			        		{name: 'diasCanc', type: 'string', mapping:'diasCanc'}			        
						]
		);

	var form_edit = new Ext.FormPanel ({
        labelWidth : 100,
        url : _ACTION_OBTENER_PERIODOS_GRACIA,
        frame : true,
        bodyStyle : 'padding:5px 5px 0',
        bodyStyle:'background: white',
        width : 400,
        autoHeight: true,
        waitMsgTarget : true,
        layout: 'form',
        buttonAlign: "center",
        labelAlign: 'right',
        reader: periodosgracia_reg,  
        items: [
                {xtype: 'hidden', id: 'cdTramo', name: 'cdTramo'},
                
                {xtype: 'textfield', 
                fieldLabel: getLabelFromMap('dsTramo',helpMap,'Descripci&oacute;n'),
                tooltip: getToolTipFromMap('dsTramo',helpMap,'Descripci&oacute;n'),
                hasHelpIcon:getHelpIconFromMap('dsTramo',helpMap),
		        Ayuda: getHelpTextFromMap('dsTramo',helpMap),
                allowBlank:false,
                width:'120',
                name: 'dsTramo', 
                id: 'dsTramo'
                },
                
                {xtype: 'numberfield', 
                fieldLabel: getLabelFromMap('edPgTextDelRec',helpMap,'Del Recibo'),
                tooltip: getToolTipFromMap('edPgTextDelRec',helpMap,'Del Recibo'),
                hasHelpIcon:getHelpIconFromMap('edPgTextDescr',helpMap),
		        Ayuda: getHelpTextFromMap('edPgTextDescr',helpMap),
                 allowBlank:false,
                 width:'120',name: 'nmMinimo', id: 'edPgTextDescr', allowDecimals: false},
                {xtype: 'numberfield', 
                fieldLabel: getLabelFromMap('edPgTextAlRec',helpMap,'Al Recibo'),
                 tooltip: getToolTipFromMap('edPgTextAlRec',helpMap,'Al recibo'),
                 hasHelpIcon:getHelpIconFromMap('edPgTextAlRec',helpMap),
		        Ayuda: getHelpTextFromMap('edPgTextAlRec',helpMap),
                 allowBlank:false,
                 width:'120',name: 'nmMaximo', id: 'edPgTextAlRec', allowDecimals: false},
                {xtype: 'numberfield', 
                fieldLabel: getLabelFromMap('edPgTextDGrac',helpMap,'D&iacute;as Gracia'),
                 tooltip: getToolTipFromMap('edPgTextDGrac',helpMap,'D&iacute;as Gracia'),
                 hasHelpIcon:getHelpIconFromMap('edPgTextDGrac',helpMap),
		        Ayuda: getHelpTextFromMap('edPgTextDGrac',helpMap),
                 allowBlank:false,
                width:'120',name: 'diasGrac', id: 'edPgTextDGrac', allowDecimals: false},
                {xtype: 'numberfield',  
                fieldLabel: getLabelFromMap('edPgTextDACan',helpMap,'D&iacute;as antes Cancelaci&oacute;n'),
                tooltip: getToolTipFromMap('edPgTextDACan',helpMap,'D&iacute;as antes Cancelaci&oacute;n'),
                hasHelpIcon:getHelpIconFromMap('edPgTextDACan',helpMap),
		        Ayuda: getHelpTextFromMap('edPgTextDACan',helpMap),
                 allowBlank:false,
                width:'120',name: 'diasCanc', id: 'edPgTextDACan', allowDecimals: false}
        	]

    });

	var window = new Ext.Window ({
			id:'winEditPeriodosGraciaId',
			//title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('106',helpMap,'Editar Per&iacute;odos de Gracia ')+'</span>',
			title: getLabelFromMap('winEditPeriodosGraciaId', helpMap,'Editar Per&iacute;odos de Gracia'),
			width: 400,
			autoHeight: true,
        	bodyStyle:'padding:5px;',
        	modal: true,
        	buttonAlign:'center',
        	labelAlign:'right',
        	items: form_edit,
            //se definen los botones del formulario
            buttons : [ {

                text : getLabelFromMap('edPgBtnSave',helpMap,'Guardar'),
				tooltip:getToolTipFromMap('edPgBtnSave',helpMap,'Guarda actualizaci&oacute;n de per&iacute;odo de gracia'),
                disabled : false,

                handler : function() {
                	
                    if (form_edit.form.isValid()) {
                     
                     if (validar_edit()){
                    	
                        form_edit.form.submit( {

                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_AGREGAR_GUARDAR_PERIODOS_GRACIA,

                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0], function(){reloadGrid();});
                                window.close();
                            },

                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0]);
                           },

                            //mensaje a mostrar mientras se guardan los datos
                          //  waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
                            waitMsg : getLabelFromMap('400022', helpMap,'Guardando Actualizacion de datos...')

                        });
                      }
                    } else {
                            Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                           }
                 }
                },
                {
                 text : getLabelFromMap('edPgBtnCancel',helpMap,'Cancelar'),
				 tooltip:getToolTipFromMap('edPgBtnCancel',helpMap,'Cancela actualizaci&oacute;n de per&iacute;odo de gracia'),

                handler : function() {
                    window.close();
                }
            }]
	});


        form_edit.form.load ({
                params: {cdTramo: record.get('cdTramo')}
        });

    window.show();

}


function agregar(record) {
	var form_edit = new Ext.FormPanel ({
        labelWidth : 100,
        url : _ACTION_AGREGAR_GUARDAR_PERIODOS_GRACIA,
        frame : true,
        bodyStyle : 'padding:5px 5px 0',
        bodyStyle:'background: white',
        width : 400,
        autoHeight: true,
        waitMsgTarget : true,
        layout: 'form',
        buttonAlign: "center",
        labelAlign: 'right',
       // reader: periodosgracia_reg,  
        items: [
                {xtype: 'hidden', id: 'cdTramo', name: 'cdTramo'},
                
                {xtype: 'textfield',
                 fieldLabel: getLabelFromMap('addPgTextDescr',helpMap,'Descripci&oacute;n'),
                 tooltip: getToolTipFromMap('addPgTextDescr',helpMap,'Descripci&oacute;n'),
                 hasHelpIcon:getHelpIconFromMap('addPgTextDescr',helpMap),
		        Ayuda: getHelpTextFromMap('addPgTextDescr',helpMap),
                width:'120',name: 'dsTramo', id: 'addPgTextDescr',allowBlank:false
                },
                
                {xtype: 'numberfield', 
				fieldLabel: getLabelFromMap('addPgTextDelRec',helpMap,'Del Recibo'),
                 tooltip: getToolTipFromMap('addPgTextDelRec',helpMap,'Del Recibo'),
                 hasHelpIcon:getHelpIconFromMap('addPgTextDelRec',helpMap),
		        Ayuda: getHelpTextFromMap('addPgTextDelRec',helpMap),
				width:'120', name: 'nmMinimo', id: 'addPgTextDelRec',allowBlank:false, allowDecimals: false
				},
                {xtype: 'numberfield', 
				fieldLabel: getLabelFromMap('addPgTextAlRec',helpMap,'Al Recibo'),
                 tooltip: getToolTipFromMap('addPgTextAlRec',helpMap,'Al Recibo'),
                 hasHelpIcon:getHelpIconFromMap('addPgTextAlRec',helpMap),
		        Ayuda: getHelpTextFromMap('addPgTextAlRec',helpMap),
				width:'120', name: 'nmMaximo', id: 'addPgTextAlRec',allowBlank:false, allowDecimals: false
				},
                {xtype: 'numberfield', 
                fieldLabel: getLabelFromMap('addPgTextDGrac',helpMap,'D&iacute;as Gracia'),
                 tooltip: getToolTipFromMap('addPgTextDGrac',helpMap,'D&iacute;as Gracia'),
                 hasHelpIcon:getHelpIconFromMap('addPgTextDGrac',helpMap),
		        Ayuda: getHelpTextFromMap('addPgTextDGrac',helpMap),
                 width:'120',name: 'diasGrac', id: 'addPgTextDGrac',allowBlank:false, allowDecimals: false},
                {xtype: 'numberfield', 
                fieldLabel: getLabelFromMap('addPgTextDACan',helpMap,'D&iacute;as antes Cancelaci&oacute;n'),
                 tooltip: getToolTipFromMap('addPgTextDACan',helpMap,'D&iacute;as antes Cancelaci&oacute;n'),
                 hasHelpIcon:getHelpIconFromMap('addPgTextDACan',helpMap),
		        Ayuda: getHelpTextFromMap('addPgTextDACan',helpMap),
                width:'120', name: 'diasCanc', id: 'addPgTextDACan',allowBlank:false, allowDecimals: false}
        	]

    });

	var window = new Ext.Window ({
		id:'winAgregperiodoGraciId',	
			//title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('105',helpMap,'Agregar Per&iacute;odos de Gracia')+'</span>',
			title: getLabelFromMap('winAgregperiodoGraciId', helpMap,'Agregar Per&iacute;odos de Gracia'),
			width: 400,
			autoHeight: true,
        	bodyStyle:'padding:5px;',
        	buttonAlign:'center',
        	modal: true,
        	items: form_edit,
            //se definen los botones del formulario
            buttons : [ {

                text : getLabelFromMap('addPgBtnSave',helpMap,'Guardar'),
				tooltip:getToolTipFromMap('addPgBtnSave',helpMap,'Guarda per&iacute;odo de gracia'),

                disabled : false,
                
                handler : function() {
                	
                    if (form_edit.form.isValid()){
                     
                     if (validar()){
					
                            form_edit.form.submit({

                                    //action a invocar cuando el formulario haga submit
                                     url : _ACTION_AGREGAR_GUARDAR_PERIODOS_GRACIA,

                                    // funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                                     success : function(from, action) {
                                     Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0], function(){reloadGrid();});
                                     window.close();
                                     },
                                      //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                          Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0]);
                            },
                           // waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
                            waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')

                           });
                      } 
                     }else {

                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));

                    }
                  }
            }, {

                text : getLabelFromMap('addPgBtnCancel',helpMap,'Cancelar'),
				tooltip:getToolTipFromMap('addPgBtnCancel',helpMap,'Cancela operaci&oacute;n de per&iacute;odo de gracia'),

                handler : function() {
                    window.close();
                }

            }]

	});

    window.show();

}

});