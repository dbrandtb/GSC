// Funcion de Editar y Agregar Formato Ordenes Trabajo
function editar(key) { 

var elJson = new Ext.data.JsonReader(
    {
        root : 'MEstructuraList',
        totalProperty: 'total',
        successProperty : '@success'
    },
    [ 
    {name: 'cdFormatoOrden',  mapping:'cdFormatoOrden',  type: 'string'},
    {name: 'dsFormatoOrden',  mapping:'dsFormatoOrden',  type: 'string'}  
    ]
);
	
	var form_edit = new Ext.FormPanel ({
		id: 'form_editId',
        url : _ACTION_GET_FORMATO_ORDENES_TRABAJO,
        frame : true,
        width : 400,
        bodyStyle : 'background: white',        
		autoHeight: true,
        reader: elJson,
        items: [
                {
                xtype: 'hidden', 
                id: 'cdFormatoOrden', 
                name: 'cdFormatoOrden'  
                },
                {
                xtype: 'textfield',
                id:'edFotTxtNomb',
                fieldLabel: getLabelFromMap('edFotTxtNomb',helpMap,'Nombre'), 
                tooltip: getToolTipFromMap('edFotTxtNomb',helpMap,'Nombre del formato de orden de trabajo'),
                hasHelpIcon:getHelpIconFromMap('edFotTxtNomb',helpMap),								 
                Ayuda: getHelpTextFromMap('edFotTxtNomb',helpMap,''),
                name: 'dsFormatoOrden',
                allowBlank:false,
                blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
                width: 200
                }
               ]
    });


	var window = new Ext.Window ({
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('wndwCnfgFrmOrdTrj',helpMap,'Configurar Formato de orden de trabajo')+'</span>',
			width: 400,
			autoHeight: true,
			modal:true,
        	items: [form_edit],
            buttons : [ {
                text : getLabelFromMap('edFotBtnSave',helpMap,'Guardar'), 
                tooltip: getToolTipFromMap('edFotBtnSave',helpMap,'Guardar el formato de orden de trabajo'),
                disabled : false,
                handler : function() {
                    if (form_edit.form.isValid()) {
                        form_edit.form.submit({
                            url : _ACTION_GUARDAR_FORMATO_ORDENES_TRABAJO,
                            success : function(from, action) {
                                Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0], function(){reloadGrid();});
                                window.close();                                     
                            },
                            failure : function(form, action) {
                                Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0]);
                           },
                           	waitTitle: getLabelFromMap('400017',helpMap,'Espere por favor...'),
                            waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')
                        });

                    } else {

                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));

                    }

                }

            }, {
               text : getLabelFromMap('edFotBtnCancel',helpMap,'Cancelar'), 
               tooltip: getToolTipFromMap('edFotBtnCancel',helpMap,'Cancelar guardar el formato de orden de trabajo'),
               handler : function() {
                    window.close();
                }
            }]
	});

	window.show();	
	if (key != "") 
	{
		form_edit.findById('cdFormatoOrden').setValue(key);
	}
	

	form_edit.form.load (
	{
			params: {cdFormatoOrden: key},
			waitTitle: getLabelFromMap('400017',helpMap,'Espere por favor...'),
            waitMsg : getLabelFromMap('400028',helpMap,'Leyendo datos ...')	
	}
	);
	
}
