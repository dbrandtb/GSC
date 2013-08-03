function agregar () {
		Ext.each(itemsFormAdd, function(campito) {
			campito.hidden = false;
			campito.width = 300;
			if (campito.visibleEnEdicion == "N") {
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
		       width: 500,
		       //height: 120,
	            bodyStyle : 'padding:5px 5px 0',
	            bodyStyle:'background: white',
		       autoScroll: true,
		       //bodyStyle: {position: 'relative'},
		       autoHeight: true,
		       items: itemsFormAdd
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
        //text:getLabelFromMap('catScrAtnBsqBttnGuardar', helpMap,'Guardar'),
		//tooltip:getToolTipFromMap('catScrAtnBsqBttnGuardar', helpMap,'Guarda el Registro'),
        text : 'Guardar',
        disabled : false,
        handler : function() {
            if (incisosForm.form.isValid()) {
	        	/*var _params = "cdPantalla=" + CODIGO_PANTALLA;
	        	var i = 1;
	        	Ext.each(incisosForm.items.items, function (campito){
	        		_params += "&valoresPantalla[" + 0 + "].valor" + i + "=" + campito.getValue() + "&";
	        		i++;
	        	});*/
	        	var _params = "cdPantalla=" + CODIGO_PANTALLA;
	        	var i = 1;
	        	/*Ext.each(incisosForm.items.items, function (campito) {
	        		if (campito.swLlave == "S") {
	        			var _index = eval(campito.cdAtribu);//.substring(1, campito.cdAtribu.length);
	        			_params += "&valoresPantalla[0].valor" + 1 + "=" + campito.getValue() + "&";
	        			i++;
	        		}
	        	});
	        	Ext.each(incisosForm.items.items, function (campito) {
	        		if (campito.cdAtribu == "") {
	        			_params += "&valoresPantalla[0].valor2=" + campito.getValue() + "&";
	        			i=2;
	        		}
	        	});
	        	Ext.each(incisosForm.items.items, function (campito){
	        		if (campito.swLlave == "N" && !isNaN(campito.cdAtribu)) {
	        			var j = eval(campito.cdAtribu);
		        		_params += "&valoresPantalla[" + 0 + "].valor" + (j+i) + "=" + campito.getValue() + "&";
		        		i++;
	        		}
	        	});*/
	        	Ext.each(incisosForm.items.items, function (campito) {
	        		var _index = eval(campito.cdAtribu);
	        		var _value = campito.getValue();
	        		if (campito.altFormats) _value = campito.getRawValue();
	        		_params += "&valoresPantalla[0].valor" + _index + "=" + _value + "&";
	        	});
	        	startMask(_window.id, 'Espere...');
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
			Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function() {reloadGrid(Ext.getCmp('grid2'));});
			_window.close();
		}
 }
}