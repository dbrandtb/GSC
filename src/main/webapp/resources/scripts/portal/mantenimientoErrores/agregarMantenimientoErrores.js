function agregar() {
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
            url : _ACTION_GUARDAR_MENSAJE_ERROR,
            frame : true,
            autoHeight: true,
            //bodyStyle : 'padding:5px 5px 0',
            bodyStyle:'background:white',
            width : 350,
            waitMsgTarget : true,
            defaults : {
                width : 230
            },
            labelAlign:'right',
            defaultType : 'textfield',
            items : [ 	{
            				xtype: 'hidden',
			            	//fieldLabel: 'C&oacute;digo',
			            	anchor: '50%',
			            	name: 'cdError',
			            	hidden: true
			            },{
					        fieldLabel: getLabelFromMap('agrManErrTxtMen',helpMap,'Mensaje'),
					        tooltip:getToolTipFromMap('agrManErrTxtMen',helpMap,'Mensaje de agregar mentenimiento de errores'), 
					        hasHelpIcon:getHelpIconFromMap('agrManErrTxtMen',helpMap),
		                     Ayuda: getHelpTextFromMap('agrManErrTxtMen',helpMap,''),    
			            	anchor: '70%',
							allowBlank: false,
							//blankText: getMsgBlankTextFromMap('cdEjecutivo', helpMap, 'Campo requerido'),
							blankText: getMsgBlankTextFromMap('400055', helpMap, 'Campo requerido'),
			            	name: 'dsMensaje'
			            },new Ext.form.ComboBox({
			                          tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
			                          anchor:'70%',
			                          name: 'cmbTipo',
			                          store: dsTiposError,
			                          displayField:'descripcion',
			                          valueField:'codigo',
			                          hiddenName: 'cdTipo',
			                          typeAhead: true,
			                          mode: 'local',
			                          triggerAction: 'all',
	                                  fieldLabel: getLabelFromMap('agrManErrCboTip',helpMap,'Tipo'),
                                      tooltip:getToolTipFromMap('agrManErrCboTip',helpMap,'Tipo de error '),
                                       hasHelpIcon:getHelpIconFromMap('agrManErrCboTip',helpMap),
		                                Ayuda: getHelpTextFromMap('agrManErrCboTip',helpMap,''),  
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
        	id:'Agre_Window',
        	//title:'<span style="color:black;font-size:12px;">'+getLabelFromMap('113',helpMap, 'Agregar Error')+'</span>',
        	
        	title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('Agre_Window', helpMap,'Agregar Error')+'</span>',
        	width: 500,
        	//height:350,
            autoHeight: true,
        	minWidth: 300,
        	modal: true,
        	minHeight: 100,
        	layout: 'fit',
        	plain:true,
        	bodyStyle:'padding:5px;',
        	buttonAlign:'center',
        	items: formPanel,
            //se definen los botones del formulario
            buttons : [ {
                text:getLabelFromMap('agrManErrBtnSave',helpMap,'Guardar'),
                tooltip: getToolTipFromMap('agrManErrBtnSave',helpMap,'Guarda en agregar mantenimiento de errores'),
                disabled : false,
                handler : function() {
               				guardarDatos(true);
                		}
            }, {
                text:getLabelFromMap('agrManErrBtnSaveAdd',helpMap,'Guardar y Agregar'),
                tooltip: getToolTipFromMap('agrManErrBtnSaveAdd',helpMap,'Guarda y agrega en mantenimiento de errores'),
            	handler: function () {
            				guardarDatos(false);
            			}
            },{
                text:getLabelFromMap('agrManErrBtnCanc',helpMap,'Cancelar'),
                tooltip: getToolTipFromMap('agrManErrBtnCanc',helpMap,'Cancela operaci&oacute;n con mantenimiento de errores'),
                handler : function() {
                    window.close();
                }
            }]

    	});

    	window.show();

		dsTiposError.load();

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
                          							}
                          					});
                      },
                      failure : function(form, action) {
                          Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0]);
                      },
                      waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')
                  });

              } else {
                  Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
              }
		}
};