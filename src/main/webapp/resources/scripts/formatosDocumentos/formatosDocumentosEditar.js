// Funcion de Editar y Agregar Formatos de Documentos
function editarFrmDoc(key) { 


var form_edit = new Ext.FormPanel ({
	id: 'form_editId',
    url : _ACTION_GET_FORMATO_DOCUMENTOS,
    frame : true,
    width : 676,
	height: true,
    reader: elJsonFrmDoc,
    items: [
            {
                xtype: 'hidden', 
                id: 'cdFormato', 
                name: 'cdFormato'  
            },
            {
                xtype: 'textfield', 
                fieldLabel: getLabelFromMap('txtFldDsNomFormatoDocEditar',helpMap,'Nombre Formato'), 
                tooltip: getToolTipFromMap('txtFldDsNomFormatoDocEditar',helpMap,'Nombre del formato de orden de trabajo'),
                hasHelpIcon:getHelpIconFromMap('txtFldDsNomFormatoDocEditar',helpMap),
                Ayuda:getHelpTextFromMap('txtFldDsNomFormatoDocEditar',helpMap),
                name: 'dsNomFormato', 
                allowBlank: false,
                width: 300
            },
            {
                xtype: 'htmleditor', 
                fieldLabel: getLabelFromMap('htmlEdtrDsFormatoEditar',helpMap,'Formato'), 
                tooltip: getToolTipFromMap('htmlEdtrDsFormatoEditar',helpMap,'Formato de orden de trabajo'),
                hasHelpIcon:getHelpIconFromMap('htmlEdtrDsFormatoEditar',helpMap),
                Ayuda:getHelpTextFromMap('htmlEdtrDsFormatoEditar',helpMap),
                name: 'dsFormato',
                height: 150,
                width: 516
            }
           ]
});
var window = new Ext.Window ({
		title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('wndwFrmtDcmntEdtr',helpMap,'Configurar Formato de Documento')+'</span>',
		width: 668,
		autoHeight: true,
        modal:true,
    	items: [form_edit],
        buttonAlign:'center',
        buttons : [ {
            text : getLabelFromMap('frmtDcmntEdtButtonGuardar',helpMap,'Guardar'), 
            tooltip: getToolTipFromMap('frmtDcmntEdtButtonGuardar',helpMap,'Guardar el formato de Documento'),
            disabled : false,
            handler : function() {
                if (form_edit.form.isValid()) {
                    form_edit.form.submit({
                        url : _ACTION_GUARDAR_FORMATO_DOCUMENTOS,
                        success : function(from, action) {
                            Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0], function(){reloadGridFrmDoc();});
                            window.close();                                     
                        },
                        failure : function(form, action) {
                            Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0]);
                       },
                       	waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
                        waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')
                    });
                } else {
                    Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
                }
            }

        }, {
           text : getLabelFromMap('frmtDcmntEdtButtonCancelar',helpMap,'Cancelar'), 
            tooltip: getToolTipFromMap('frmtDcmntEdtButtonCancelar',helpMap,'Cancelar guardar el formato de Documento'),
           handler : function() {
                window.close();
            }
        }]
});

window.show();	
if (key != "") 
{
	form_edit.findById('cdFormato').setValue(key);
}

form_edit.form.load (
{
    params: {
    	cdFormato: key
    	},
    waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
    waitMsg : getLabelFromMap('400028',helpMap,'Leyendo datos ...')	
}
);
	
}