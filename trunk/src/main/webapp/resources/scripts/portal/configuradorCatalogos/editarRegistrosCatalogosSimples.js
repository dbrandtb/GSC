function editar (record) {
		Ext.each(itemsFormABM, function(campito) {
			campito.hidden = false;
			campito.width = 300;
			if (campito.visibleEnEdicion == "N") {
				campito.hidden = true;campito.xtype="hidden";
				campito.fieldLabel = "";
				campito.labelSeparator = "";
			}
		});

		var _incisosFormEdit = new Ext.FormPanel({
		       //title: '<span style="color:black;font-size:12px;">Acciones de Renovaci&oacute;n</span>',
		       iconCls:'logo',
		       bodyStyle:'background: white',
	            bodyStyle : 'padding:5px 5px 0',
	            bodyStyle:'background: white',
		       labelAlign: 'right',
		       frame:true,   
		       url: _ACTION_OBENER_CAMPOS_BUSQUEDA,
		       //reader: elJson,
		       width: 500,
		       //height: 120,
		       autoScroll: true,
		       //bodyStyle: {position: 'relative'},
		       autoHeight: true,
		       items: itemsFormABM
			}
		);
		
var _window = new Ext.Window({
	title: 'Editar Registro',
	width: 550,
	autoHeight:true,
	modal: true,
	plain:true,
	bodyStyle:'padding:5px;',
	buttonAlign:'center',
	items: [_incisosFormEdit],
    //se definen los botones del formulario
    buttons : [ {
        text : 'Guardar',
        disabled : false,
        handler : function() {
            if (_incisosFormEdit.form.isValid()) {
	        	var _params = "cdPantalla=" + CODIGO_PANTALLA;
	        	var i = 1;
	        	/*Ext.each(_incisosFormEdit.items.items, function (campito) {
	        		if (campito.swLlave == "S") {
	        			var _index = eval(campito.cdAtribu);//.substring(1, campito.cdAtribu.length);
	        			_params += "&valoresPantalla[0].valor" + 1 + "=" + campito.getValue() + "&";
	        			i++;
	        		}
	        	});
	        	Ext.each(_incisosFormEdit.items.items, function (campito) {
	        		if (campito.cdAtribu == "") {
	        			_params += "&valoresPantalla[0].valor2=" + campito.getValue() + "&";
	        			i=2;
	        		}
	        	});
	        	Ext.each(_incisosFormEdit.items.items, function (campito){
	        		if (campito.swLlave == "N" && !isNaN(campito.cdAtribu)) {
	        			var j = eval(campito.cdAtribu);
		        		_params += "&valoresPantalla[" + 0 + "].valor" + (j+i) + "=" + campito.getValue() + "&";
		        		i++;
	        		}
	        	});*/
	        	Ext.each(_incisosFormEdit.items.items, function (campito) {
	        		var _index = eval(campito.cdAtribu);
	        		var _value = campito.getValue();
	        		if (campito.altFormats) _value = campito.getRawValue();
	        		_params += "&valoresPantalla[0].valor" + _index + "=" + _value + "&"; 
	        	});
	        	startMask(_window.id, 'Espere...');
	        	execConnection(_ACTION_ACTUALIZAR_REGISTRO, _params, cbkGuardar);
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
 Ext.each (_incisosFormEdit.items.items, function (campito){
 		campito.setValue(record.get(campito.name));
 		if (campito.swLlave == "S") {
 			campito.setDisabled(true);
 		}
 		/*if (campito.fgObligatorio == "S") {
 			campito.allowBlank = false;
 		}*/
			if (campito.swLlave == "S") {
				campito.setDisabled(true);
				campito.readOnly = true;
				campito.getEl().dom.readOnly = true;
			}
 });
 					Ext.each(_incisosFormEdit.items.items, function (campito) {
						if (campito.fgObligatorio == "N") {
							campito.allowBlank = true;
						}
						if (eval(campito.allowBlank) == false) {
							campito.allowBlank = false;
						} else {
							campito.allowBlank = true;
						}
						campito.clearInvalid();
						campito.getEl().dom.readOnly = false;
					});
 
 function cbkGuardar (_success, _message) {
 		endMask();
 		if (!_success) {
			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
		}else {
			Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function() {reloadGrid(Ext.getCmp('grid2'));});
			_window.close();
		}
 }
}