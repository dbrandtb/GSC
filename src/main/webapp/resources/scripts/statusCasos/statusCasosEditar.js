// Funcion de Editar y Agregar Status de Casos

function editarSttsCs(key) { 

var desEstatus = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('AgreEdidesEstatusId',helpMap,'Estado'),
        tooltip:getToolTipFromMap('AgreEdidesEstatusId',helpMap,'Nombre Estado'),
        hasHelpIcon:getHelpIconFromMap('AgreEdidesEstatusId',helpMap),								 
        Ayuda: getHelpTextFromMap('AgreEdidesEstatusId',helpMap,''),	
        labelWidth: 10,
        id: 'AgreEdidesEstatusId', 
        name: 'desStatus',
        allowBlank: false,
        anchor: '100%'
});



var comboIndAviso = new Ext.form.ComboBox(
{
    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
    store: dsIndAviso,
    id:'comboIndAvisoId',
    fieldLabel: getLabelFromMap('comboIndAvisoId',helpMap,'Indicador de Aviso'),
    tooltip: getToolTipFromMap('comboIndAvisoId',helpMap,'Indicador de Aviso'),
    hasHelpIcon:getHelpIconFromMap('comboIndAvisoId',helpMap),								 
    Ayuda: getHelpTextFromMap('comboIndAvisoId',helpMap,''),	
    width: 90,
    //anchor:'100%',
    allowBlank: false,
    displayField:'descripcion',
    valueField: 'codigo',
    hiddenName: 'codIndAviso',
    typeAhead: true,
    triggerAction: 'all',
    emptyText:'Seleccione Indicador de Aviso...',
    selectOnFocus:true,
    forceSelection:true
}
);

var form_edit = new Ext.FormPanel ({
	id: 'form_editId',
    url : _ACTION_OBTENER_STATUS_CASOS,
    frame : true,
    width : 550,
	height: true,
    labelAlign: 'right',
    reader: elJsonEstatus,
    layout:'column',
    bodyStyle:'background: white',
    border:false,
    columnWidth: 1,
    items:[
            {
            columnWidth:.5,
            layout: 'form',
            border: false,
            items:[   
                    {
                        xtype: 'hidden', 
                        id: 'codStatusId', 
                        name: 'codStatus'  
                    },                                  
                     desEstatus                                  
                   ]
            },
            {
            columnWidth:.5,
            layout: 'form',
            border: false,
            items:[                                     
                     comboIndAviso                                  
                   ]
            }
        ]   
});


var window = new Ext.Window ({
	
	    id:'EstadoCasos',
		title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('EstadoCasos',helpMap,'Administrar Estado de Casos')+'</span>',
		width: 550,
		autoHeight: true,
        modal:true,
    	items: [form_edit],
        buttonAlign:'center',
        buttons : [ 
        {
            text : getLabelFromMap('frmtDcmntEdtButtonGuardar',helpMap,'Guardar'), 
            tooltip: getToolTipFromMap('frmtDcmntEdtButtonGuardar',helpMap,'Guardar el Estado'),
            handler : function() {
                  if (form_edit.form.isValid()) {
                    guardarStatusCasos();
                    window.close();
                  } else{
                    Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
                  }
            }

        }/*, 
        {
           text : getLabelFromMap('frmtDcmntEdtButtonCancelar',helpMap,'Agregar'), 
            tooltip: getToolTipFromMap('frmtDcmntEdtButtonCancelar',helpMap,'Agrega un Nuevo Estatus Estatus'),
           handler : function() {
                  if (form_edit.form.isValid()) {
                    guardarStatusCasos();
                    form_edit.getForm().reset();
                  } else{
                    Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
                  }
             }
        }*/,
        {
           text : getLabelFromMap('frmtDcmntEdtButtonCancelar',helpMap,'Cancelar'), 
            tooltip: getToolTipFromMap('frmtDcmntEdtButtonCancelar',helpMap,'Cancelar guardar el Estado'),
           handler : function() {
                window.close();
        }
        }
        ]
});

window.show();	
if (key != "") 
{
	form_edit.findById('codStatusId').setValue(key);
    form_edit.form.load ({
    params: {
        cdStatus: key
        },
    waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
    waitMsg : getLabelFromMap('400028',helpMap,'Leyendo datos ...'),
    success: function(form, action){
    //console.log(action.result);
        var cdIndi_Envio = action.result.data.indiAviso;
        //alert(cdIndi_Envio);
        dsIndAviso.load({
            callback: function (r, o, success) {
                    if (success) {
                        comboIndAviso.setValue(cdIndi_Envio);
                        }
                }
            });
        }       
    });
}
else
{
    form_edit.form.load ();
}

/*
form_edit.form.load (
{
    params: {
    	cdStatus: key
    	},
    waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
    waitMsg : getLabelFromMap('400028',helpMap,'Leyendo datos ...'),
    success: function(form, action){
    //console.log(action.result);
        var cdIndi_Envio = action.result.data.indiAviso;
        //alert(cdIndi_Envio);
        dsIndAviso.load({
            callback: function (r, o, success) {
                    if (success) {
                        comboIndAviso.setValue(cdIndi_Envio);
                    }
            }
        });
   }	
}
);*/

function guardarStatusCasos()
{
        var params = {
                 cdStatus: form_edit.findById('codStatusId').getValue(),
                 dsStatus: form_edit.findById('AgreEdidesEstatusId').getValue(),
                 indAviso: form_edit.findById('comboIndAvisoId').getValue()
             };
        execConnection (_ACTION_GUARDAR_STATUS_CASOS, params, cbkGuardar);
}
function cbkGuardar (success, message) {
    if (success) {
          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), message,function(){reloadGrid()});
    } else {
          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), message);
    }

}


	
}