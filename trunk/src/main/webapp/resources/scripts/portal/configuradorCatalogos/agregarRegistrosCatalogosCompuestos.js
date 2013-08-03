
 function clearCombo (idCombo) {
 	var el_Combo = Ext.getCmp(idCombo); 
 	if (el_Combo) {
 		el_Combo.clearValue();
 		el_Combo.setRawValue('');
 		el_Combo.store.removeAll();
 	}
 }
 function reloadCombo (nroClave) {
 	if (nroClave == 1) { //Es el 1º combo de la clave, por lo tanto no se hace nada
	 					clearCombo('AgregarCombo_2');
	 					clearCombo('AgregarCombo_3');
	 					clearCombo('AgregarCombo_4');
 		if (Ext.getCmp('AgregarCombo_2')) {
	 		Ext.getCmp('AgregarCombo_2').store.load({
	 			params: {
	 				cdColumna: Ext.getCmp('AgregarCombo_2').cdColumna,
	 				cdClave1: Ext.getCmp('AgregarCombo_1').getValue()
	 			},
	 			callback: function (r, o, success) {
	 				if (!success) {
	 				}
	 			}
	 		});
 		}
 	}
 	if (nroClave == 2) { //Es el 2º combo de la clave
	 					clearCombo('AgregarCombo_3');
	 					clearCombo('AgregarCombo_4');
 		if (Ext.getCmp('AgregarCombo_3')) {
	 		Ext.getCmp('AgregarCombo_3').store.load({
	 			params: {
	 				cdColumna: Ext.getCmp('AgregarCombo_3').cdColumna,
	 				cdClave1: Ext.getCmp('AgregarCombo_1').getValue(),
	 				cdClave2: Ext.getCmp('AgregarCombo_2').getValue()
	 			},
	 			callback: function (r, o, success) {
	 				if (!success) {
	 				}
	 			}
	 		});
 		}
 	}

 	if (nroClave == 3) { //Es el 2º combo de la clave
	 					clearCombo('AgregarCombo_4');
 		if (Ext.getCmp('AgregarCombo_4')) {
	 		Ext.getCmp('AgregarCombo_4').store.load({
	 			params: {
	 				cdColumna: Ext.getCmp('AgregarCombo_4').cdColumna,
	 				cdClave1: Ext.getCmp('AgregarCombo_2').getValue(),
	 				cdClave2: Ext.getCmp('AgregarCombo_3').getValue()
	 			},
	 			callback: function (r, o, success) {
	 				if (!success) {
	 				}
	 			}
	 		});
 		}
 	}
 }

function agregar () {
		Ext.each(itemsFormAdd, function(campito) {
			campito.width = 300;
			if (campito.visibleEnEdicion == "S") {
				campito.hidden = false;
			} else {
				campito.hidden = true;campito.xtype="hidden";
				campito.fieldLabel = "";
				campito.labelSeparator = "";
			}
		});
		var incisosForm = new Ext.FormPanel({
		       iconCls:'logo',
		       bodyStyle:'background: white',
		       labelAlign: 'right',
		       frame:true,   
		       url: _ACTION_OBENER_CAMPOS_BUSQUEDA,
		       //reader: elJson,
		       width: 550,
		       //height: 120,
		       autoScroll: true,
		       autoHeight: true,
		       items: itemsFormAdd,
		       listeners: {
		       		beforerender: function () {
		       			/*Ext.each(this.items.items, function (campito) {
		       				if (campito.fgObligatorio == "N") {
		       					campito.fieldLabel = "";
		       					campito.labelSeparator = "";
		       					campito.setVisible(false);
		       				}
		       			});*/
		       		}
		       }
			}
		);
		
var _window = new Ext.Window({
	title: 'Agregar Registro',
	width: 550,
	autoHeight: true,
	modal: true,
	plain:true,
	bodyStyle:'padding:5px;',
	buttonAlign:'center',
	items: [incisosForm],
    //se definen los botones del formulario
    buttons : [ {
        text : 'Guardar',
        disabled : false,
        handler : function() {
            if (incisosForm.form.isValid()) {
	        	var _params = "cdPantalla=" + CODIGO_PANTALLA;
	        	var i = 1;
	        	Ext.each(incisosForm.items.items, function (campito){
	        		if (campito.fgObligatorio == "S") {
	        			var _value =  campito.getValue();
	        			//alert(campito.fieldLabel + '\n' + campito.xtype);
	        			if (campito.altFormats) _value = campito.getRawValue(); 
		        		_params += "&valoresPantalla[" + 0 + "].valor" + i + "=" + _value + "&";
		        		i++;
		        	}
	        	});
	        	startMask(incisosForm.id, 'Espere...');
	        	execConnection(_ACTION_GUARDAR_REGISTRO, _params, cbkGuardar);
            } else {
                Ext.Msg.alert('Aviso', 'Por favor complete la informaci&oacute;n requerida');
            }
        }
    }, 
    {
        text : 'Regresar',
        handler : function() {
        _window.close();
        }
    }]
});

 _window.show();
 
					Ext.each(incisosForm.items.items, function (campito) {
						if (campito.fgObligatorio == "N") {
							campito.allowBlank = true;
						}
						if (eval(campito.allowBlank) == false) {
							campito.allowBlank = false;
						} else {
							campito.allowBlank = true;
						}
						if (campito.xtype = "numberfield") {
							campito.setValue();
						}
						campito.clearInvalid();
						campito.getEl().dom.readOnly = false;
					});
 function cbkGuardar (_success, _message) {
 		endMask();
		if (!_success) {
			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
		}else {
			Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function() {

					/**
					*	Prepara el envío de parámetros para la búsqueda
					*/
					var i=0;
					var arrparams = new Array();
		        	Ext.each (Ext.getCmp('incisosForm').form.items.items, function (campito) {
		        		var _index = eval(campito.cdAtribu);
		
		        		if (campito.altFormats) arrparams[_index - 1] = campito.getRawValue()
		        		else arrparams[_index - 1] = campito.getValue();
		        	});
		
					var _params = {
						cdPantalla: CODIGO_PANTALLA,
						valor1: arrparams[0],
						valor2: arrparams[1],
						valor3: arrparams[2],
						valor4: arrparams[3],
						valor5: arrparams[4],
						valor6: arrparams[5],
						valor7: arrparams[6],
						valor8: arrparams[7],
						valor9: arrparams[8]
					}
					reloadGrid(Ext.getCmp('grid2'), _params);
			});
			_window.close();
		}
 }
}