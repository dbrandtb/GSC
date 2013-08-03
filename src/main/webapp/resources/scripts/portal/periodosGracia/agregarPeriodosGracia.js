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
		         fieldLabel: getLabelFromMap('agrPerGracTxtDesc',helpMap,'Descripci&oacute;n'),
        		 tooltip:getToolTipFromMap('agrPerGracTxtDesc',helpMap,'Descripci&oacute;n'),
                 name: 'dsTramo',
                 id: 'dsTramo'},
                {xtype: 'numberfield',
		         fieldLabel: getLabelFromMap('agrPerGracTxtDelR', helpMap,'Del Recibo '),
        		 tooltip:getToolTipFromMap('agrPerGracTxtDelR',helpMap,'Del Recibo'),
                 name: 'nmMinimo', 
                 id: 'nmMinimo'},
                {xtype: 'numberfield', 
		         fieldLabel: getLabelFromMap('agrPerGracTxtAlR', helpMap,'Al Recibo '),
        		 tooltip:getToolTipFromMap('agrPerGracTxtAlR',helpMap,'Al Recibo'),
                 name: 'nmMaximo', 
                 id: 'nmMaximo'},
                {xtype: 'numberfield', 
		         fieldLabel: getLabelFromMap('agrPerGracTxtDG', helpMap,'D&iacute;as Gracia'),
        		 tooltip:getToolTipFromMap('agrPerGracTxtDG',helpMap,'D&iacute;as Gracia'),
                 name: 'diasGrac', 
                 id: 'diasGrac'},
                {xtype: 'numberfield', 
		         fieldLabel: getLabelFromMap('agrPerGracTxtDaC', helpMap,'D&iacute;as antes Cancelaci&oacute;n'),
        		 tooltip:getToolTipFromMap('agrPerGracTxtDaC',helpMap,'D&iacute;as antes Cancelaci&oacute;n'),
                 name: 'diasCanc', 
                 id: 'diasCanc'},
        	]

    });

	var window = new Ext.Window ({
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('105',helpMap,' Configurar Per&iacute;odo de Gracia ')+'</span>',
			width: 400,
			autoHeight: true,
			modal: true,
        	bodyStyle:'padding:5px;',
        	buttonAlign:'center',
        	items: form_edit,
            //se definen los botones del formulario
            buttons : [ {

				text:getLabelFromMap('agrPerGracBtnSave', helpMap,'Guardar'),
				tooltip:getToolTipFromMap('agrPerGracBtnSave',helpMap,'Guarda per&iacute;odo de gracia'),

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
                                Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'),'Problemas al Guardar: ' + action.result.errorMessages[0]);
                           },

                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')

                        });

                    } else {

                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));

                    }
					reloadGrid();
                }

            }, {

				text:getLabelFromMap('agrPerGracBtnCanc', helpMap,'Cancelar'),
				tooltip:getToolTipFromMap('agrPerGracBtnCanc',helpMap,'Cancela guardar per&iacute;odo de gracia'),
                handler : function() {
                window.close();
                }

            }]

	});

    window.show();

}