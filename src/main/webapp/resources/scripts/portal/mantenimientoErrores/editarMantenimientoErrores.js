function editar(codError) {

		var reader_mensaje = new Ext.data.JsonReader({
            root: 'mensajeError',
            successProperty: 'success'
            },[
           {name: 'dsMensaje', type: 'string',mapping:'msgText'},
           {name: 'cdTipo', type: 'string',mapping:'msgTitle'}
        ]);
        var dsMensajeError = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_GET_MENSAJE_ERROR
            }),
            reader: reader_mensaje 
        });

        var dsTiposError = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_TIPO_ERROR
            }),
            reader: new Ext.data.JsonReader({
            root: 'comboTiposError'
            },[
           {name: 'codigo', type: 'string',mapping:'codigo'},
           {name: 'descripcion', type: 'string',mapping:'descripcion'}
        ])
        });


        //se define el formulario
        var formPanel = new Ext.FormPanel( {
            labelWidth : 100,
            url : _ACTION_GET_MENSAJE_ERROR,
            frame : true,
            height: 170,
            //bodyStyle : 'padding:5px 5px 0',
            bodyStyle:'background:white',
            width : 350,
            defaults : {
                width : 230
            },
            defaultType : 'textfield',
            reader: reader_mensaje,
            labelAlign:'right',
            successProperty: 'success',
            items : [ {
					        fieldLabel: getLabelFromMap('ediManErrTxtCod',helpMap,'C&oacute;digo'),
					        tooltip:getToolTipFromMap('ediManErrTxtCod',helpMap,'C&oacute;digo de editar mentenimiento de errores'), 
					        hasHelpIcon:getHelpIconFromMap('ediManErrTxtCod',helpMap),
		                     Ayuda: getHelpTextFromMap('ediManErrTxtCod',helpMap,''), 					        
			            	//anchor: '50%',
			            	name: 'cdError',
			            	value: codError,
			            	readOnly: true
			            },{
					        fieldLabel: getLabelFromMap('ediManErrTxtMsg',helpMap,'Mensaje'),
					        tooltip:getToolTipFromMap('ediManErrTxtMsg',helpMap,'Mensaje en editar mentenimiento de errores'), 
					        hasHelpIcon:getHelpIconFromMap('ediManErrTxtMsg',helpMap),
		                    Ayuda: getHelpTextFromMap('ediManErrTxtMsg',helpMap,''),    
							allowBlank: false,
							blankText: getMsgBlankTextFromMap('400055', helpMap, 'Campo requerido'),
			            	name: 'dsMensaje'
			            },new Ext.form.ComboBox({
			                          tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
			                          //anchor:'70%',
			                          name: 'cmbTipo',
			                          disabled: true,
			                          store: dsTiposError,
			                          displayField:'descripcion',
			                          valueField:'codigo',
			                          hiddenName: 'cdTipo',
			                          typeAhead: true,
			                          mode: 'local',
			                          triggerAction: 'all',
	                                  fieldLabel: getLabelFromMap('ediManErrCboTip',helpMap,'Tipo'),
                                      tooltip:getToolTipFromMap('ediManErrCboTip',helpMap,'Tipo de error '),
                                      hasHelpIcon:getHelpIconFromMap('ediManErrCboTip',helpMap),
		                               Ayuda: getHelpTextFromMap('ediManErrCboTip',helpMap,''),    
			                          emptyText:'Seleccione Tipo...',
			                          selectOnFocus:true,
			                          forceSelection:true,
									  allowBlank: false,
									  blankText: getMsgBlankTextFromMap('cdEjecutivo', helpMap, 'Campo requerido')
			                          //labelSeparator:''
			                          })
                ]

        });


//Windows donde se van a visualizar la pantalla

        var window = new Ext.Window({
        	id:'editarWindow',
        	//title:'<span style="color:black;font-size:12px;">'+getLabelFromMap('114',helpMap, 'Editar Error')+'</span>',
        	
        	title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('editarWindow', helpMap,' Editar Error')+'</span>',
        	width: 500,
        	height:180,
            //autoHeight: true,
        	//minWidth: 300,
        	//minHeight: 100,
        	layout: 'fit',
        	modal: true,
        	plain:true,
        	bodyStyle:'padding:5px;',
        	buttonAlign:'center',
        	items: [formPanel],
            //se definen los botones del formulario
            buttons : [ {
                text:getLabelFromMap('ediManErrBtnSave',helpMap,'Guardar'),
                tooltip: getToolTipFromMap('ediManErrBtnSave',helpMap,'Guarda en mantenimiento de errores'),
                disabled : false,
                handler : function() {
               				guardarDatos(true);
                		}
            }, /*{
                text:getLabelFromMap('ediManErrBtnSaveAdd',helpMap,'Guardar y Agregar'),
                tooltip: getToolTipFromMap('ediManErrBtnSaveAdd',helpMap,'Agrega y guarda en mantenimiento de errores'),
            	handler: function () {
            				guardarDatos(false);
            			}
            },*/{
                text:getLabelFromMap('ediManErrBtnCanc',helpMap,'Cancelar'),
                tooltip: getToolTipFromMap('ediManErrBtnCanc',helpMap,'Cancela la operaci&oacute;n en editar mantenimiento de errores'),
                handler : function() {
                    window.close();
                }
            }]

    	});

    	window.show();

		dsTiposError.load();
		
		formPanel.form.load({
				params: {cdError: codError},
				
				waitTitle: getLabelFromMap('400021',helpMap,'Espere ...'),
				waitMsg: getLabelFromMap('400028',helpMap,'Leyendo datos ...'),
				failure: function() {
							Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), getLabelFromMap('400033', helpMap,'No se encontraron registros'));
						}
		});

		function guardarDatos(cerrarVentana) {
              if (formPanel.form.isValid()) {
                  formPanel.form.submit( {
                      url : _ACTION_GUARDAR_MENSAJE_ERROR,
                      success : function(from, action) {
                          Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0], function(){
                          							reloadGrid();
                          							if (cerrarVentana) {
                          								window.close();
                          							}else {
                          								formPanel.getForm().reset();
                          								formPanel.form.findField('cdError').setValue('');
                          							}
                          					});
                      },
                      failure : function(form, action) {
                          Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0]);
                      },
                      waitTitle: getLabelFromMap('400021',helpMap,'Espere ...'),
                      waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')
                  });

              } else {
                  Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
              }
		}
};