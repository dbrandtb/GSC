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
			        		{name: 'diasCanc', type: 'string', mapping:'diasCanc'},			        
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
		         fieldLabel: getLabelFromMap('editPerGracTxtDesc', helpMap,'Descripcion'),
        		 tooltip:getToolTipFromMap('editPerGracTxtDesc',helpMap,'Edita descripci&oacute;n del per&iacute;odo de gracia'),
                 name: 'dsTramo',
                 id: 'dsTramo'},
                {xtype: 'numberfield',
		         fieldLabel: getLabelFromMap('editPerGracTxtDelR', helpMap,'Del Recibo '),
        		 tooltip:getToolTipFromMap('editPerGracTxtDelR',helpMap,'Edita Del Recibo del per&iacute;odo de gracia'),
                 name: 'nmMinimo', 
                 id: 'nmMinimo'},
                {xtype: 'numberfield', 
		         fieldLabel: getLabelFromMap('editPerGracTxtAlR', helpMap,'Al Recibo '),
        		 tooltip:getToolTipFromMap('editPerGracTxtAlR',helpMap,'Edita Al Recibo del per&iacute;odo de gracia'),
                 name: 'nmMaximo', 
                 id: 'nmMaximo'},
                {xtype: 'numberfield', 
		         fieldLabel: getLabelFromMap('editPerGracTxtDG', helpMap,'D&iacute;as Gracia'),
        		 tooltip:getToolTipFromMap('editPerGracTxtDG',helpMap,'Edita D&iacute;as Gracia  del per&iacute;odo de gracia'),
                 name: 'diasGrac', 
                 id: 'diasGrac'},
                {xtype: 'numberfield', 
		         fieldLabel: getLabelFromMap('editPerGracTxtDaC', helpMap,'D&iacute;as antes Cancelacion'),
        		 tooltip:getToolTipFromMap('editPerGracTxtDaC',helpMap,'Edita D&iacute;as antes Cancelaci&oacute;n del per&iacute;odo de gracia'),
                 name: 'diasCanc', 
                 id: 'diasCanc'},                
        	]

    });

	var window = new Ext.Window ({
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('106',helpMap,' Configurar Per&iacute;odos de Gracia ')+'</span>',
			width: 400,
			autoHeight: true,
        	bodyStyle:'padding:5px;',
        	modal: true,
        	buttonAlign:'center',
        	items: form_edit,
            //se definen los botones del formulario
            buttons : [ {

				text:getLabelFromMap('editPerGracBtnSave', helpMap,'Guardar'),
				tooltip:getToolTipFromMap('editPerGracBtnSave',helpMap,'Guarda per&iacute;odo de gracia'),

                disabled : false,

                handler : function() {

                    if (form_edit.form.isValid()) {

                        form_edit.form.submit( {

                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_AGREGAR_GUARDAR_PERIODOS_GRACIA,

                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'),'Guardado satisfactoriamente');
                                reloadGrid();
                                window.close();
                            },

                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'),action.result.errorMessages[0]);
                           },

                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')

                        });

                    } else {

						Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));

                    }

                }

            }, {

				text:getLabelFromMap('editPerGracBtnCanc', helpMap,'Cancelar'),
				tooltip:getToolTipFromMap('editPerGracBtnCanc',helpMap,'Cancela guardar per&iacute;odo de gracia'),

                handler : function() {
                    window.close();
                }

            }]

	});


        form_edit.form.load ({
                params: {cdTramo: record.get('cdTramo')},
        });

    window.show();

}