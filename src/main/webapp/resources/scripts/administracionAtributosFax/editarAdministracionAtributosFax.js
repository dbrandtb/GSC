function editar(record) { 
//alert(record);
var codAtribu = new Ext.form.Hidden( {
                disabled:false,
                name:'cdAtribu',
                id:'cdAtribuId'
            });
            
var codTipoAr = new Ext.form.Hidden( {
                disabled:false,
                name:'cdTipoAr',
                id:'cdTipoArId'
            });            

var atributo = new Ext.form.TextField({
       	fieldLabel: getLabelFromMap('txtFldCdAtrEdtAtrDeFax', helpMap,'Atributo'), 
		tooltip: getToolTipFromMap('txtFldCdAtrEdtAtrDeFax', helpMap,'Atributo de Fax'),  				                    	
        id: 'dsAtributoId', 
        name: 'dsAtributo',
        allowBlank: false,
        width:260
    });

var minimo = new Ext.form.TextField({
       	fieldLabel: getLabelFromMap('txtFldMnmEdtAtrDeFax', helpMap,'Minimo'), 
		tooltip: getToolTipFromMap('txtFldMnmEdtAtrDeFax', helpMap,'Minimo Tama&ntilde;o'),  				                    	
        id: 'dsMinimoId', 
        name: 'dsMinimo',
        allowBlank: false,
        width:260
    });

var maximo = new Ext.form.TextField({
       	fieldLabel: getLabelFromMap('txtFldMxmEdtAtrDeFax', helpMap,'Maximo'), 
		tooltip: getToolTipFromMap('txtFldMxmEdtAtrDeFax', helpMap,'Maximo Tama&ntilde;o'),  				                    	
        id: 'dsMaximoId', 
        name: 'dsMaximo',
        allowBlank: true,
        width:260
    });

var tabla = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtFldTblEdtAtrDeFax', helpMap,'Maximo'), 
        tooltip: getToolTipFromMap('txtFldTblEdtAtrDeFax', helpMap,'Maximo Tama&ntilde;o'),                                        
        id: 'dsTablaId', 
        name: 'dsTabla',
        allowBlank: true,
        width:260
    });

var obligatorio = new Ext.form.Checkbox({
    id:'obligatorioId',
    fieldLabel: getLabelFromMap('oblChcBoxEdtAtrDeFax', helpMap,'Obligatorio'),
    tooltip: getToolTipFromMap('oblChcBoxEdtAtrDeFax', helpMap,'Obligatorio'),
    hasHelpIcon:getHelpIconFromMap('oblChcBoxEdtAtrDeFax',helpMap),
    helpText:getHelpTextFromMap('oblChcBoxEdtAtrDeFax',helpMap),
    name:'cdObligatorio'
});

//LOS COMBOS

var comboFormato = new Ext.form.ComboBox(
{
    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
    store: desFormato,
    id:'comboFormatoId',
    fieldLabel: getLabelFromMap('cmbEdtAtrDeFaxFormato',helpMap,'Formato'),
    tooltip: getToolTipFromMap('cmbEdtAtrDeFaxFormato',helpMap,'Formato a utilizar'),
    anchor:'100%',
    allowBlank: false,
    displayField:'descripcion',
    valueField: 'codigo',
    hiddenName: 'cdFormato',
    typeAhead: true,
    triggerAction: 'all',
    emptyText:'Seleccione Formato...',
    selectOnFocus:true,
    forceSelection:true
}
);

var comboStatus = new Ext.form.ComboBox(
{
    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
    store: desStatus,
    id:'comboMetEnvId',
    fieldLabel: getLabelFromMap('cmbEdtAtrDeFaxStatus',helpMap,'Status'),
    tooltip: getToolTipFromMap('cmbEdtAtrDeFaxStatus',helpMap,'Status'),
    anchor:'100%',
    allowBlank: false,
    displayField:'descripcion',
    valueField: 'codigo',
    hiddenName: 'status',
    typeAhead: true,
    triggerAction: 'all',
    emptyText:'Seleccione Status ...',
    selectOnFocus:true,
    forceSelection:true
}
);

// Nombre y Mensaje
var admEdtFax =  new Ext.FormPanel({
    width: 590,
    layout: 'table',
    layoutConfig: {columns: 2},
    //labelWidth: 70,
    labelAlign: 'right',
    defaults: {frame:true, width:295, height: 75},
    border:false,
    reader: elJson_GetNotifi,
    url: _ACTION_OBTENER_ADMINISTRACION_ATRIBUTOS_FAX,
    items: [
            {
                layout: 'form',
                width:380,
                items: [
                        codAtribu,
                        mensaje
                        ]
            },
            {
                layout: 'form',
                width:210,
                items: [
                        comboFormato
                        ]
            },
            {
                layout: 'form',
                //width:295,
                items: [
                        minimo
                        ]
            },
            {
                layout: 'form',
                //width:295,
                items: [
                        maximo
                        ]
            },
            {
               layout: 'form', 
               height: 420,              
               items: [
                        tabla
                        ]
            },
            {
               layout: 'form', 
               height: 170,              
               items: [
                        obligatorio
                        ]
            },
            {
                width:590,
                height: 90,
                colspan: 3,
                layout: 'form',
                items:[
                        comboStatus                                      
                    ]
            }        
           ]
}); 


var _window = new Ext.Window ({
    //title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('96',helpMap,'Formato de Documentos')+'</span>',
    width: 610,
    modal: true,
    height: 516,
    items: [
            admEdtFax
            ],
    buttonAlign: "center",
	buttons:[{
		text:getLabelFromMap('ntfcnEdtButtonGuardar',helpMap,'Aceptar'),
		tooltip: getToolTipFromMap('ntfcnEdtButtonGuardar',helpMap,'Guarda Atributo de Fax'),
		handler: function() {
   			if (notificacionMensaje.form.isValid()) {
                    guardarNotiProcesos();
			} else{
				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
			}
		}
		},{
		text:getLabelFromMap('ntfcnEdtButtonRegresar',helpMap,'Regresar'),
		tooltip: getToolTipFromMap('ntfcnEdtButtonRegresar',helpMap,'Retorna a Pantalla Anterior'),                              
		handler: function() {
			_window.close();
		}
	}]
});
_window.show();
//Ext.getCmp('itemselector').refreshFromView(storeGetProceNotifi);






if (record.get('cdAtribu')!= "" && record.get('cdTipoAr')!= "" ) 
{
    admEdtFax.findById('cdAtribuId').setValue(record.get('cdAtribu'));
    admEdtFax.findById('cdTipoArId').setValue(record.get('cdTipoAr'));
}


admEdtFax.form.load (
{
    params: { 
                cdAtribu: setValue(record.get('cdAtribu')),
                cdTipoAr: setValue(record.get('cdTipoAr'))
                },
    waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
    waitMsg : getLabelFromMap('400028',helpMap,'Leyendo datos ...'),
    success: function(form, action){
    //console.log(action.result);
    	var cdFormato_Fax = action.result.data.cdFormato;
    	var cdStatus_Fax = action.result.data.status;
        desFormato.load({
            callback: function (r, o, success) {
            		if (success) {
            			comboFormato.setValue(cdFormato_Fax);
            		}
            }
        });
        desStatus.load({
        	callback: function (r, o, success) {
        			if (success) {
						comboStatus.setValue(cdStatus_Fax);
        			}
        	}
        });
    }
}
);

function guardarAtrbutosFax(){
    var conn = new Ext.data.Connection();
	                conn.request({
				    	url: _ACTION_GUARDAR_ADMINISTRACION_ATRIBUTOS_FAX,
				    	method: 'POST',
				    	params: {
				    				cdAtribu: setValue(record.get('cdAtribu')),
                                    cdTipoAr: setValue(record.get('cdTipoAr')),                                    
				    				dsAtribu: admEdtFax.findById("dsAtributoId").getValue(),
                                    swFormat: admEdtFax.findById("comboFormatoId").getValue(),  
						    		nmlMax: admEdtFax.findById("dsMaximoId").getValue(),
                                    nmlMin: admEdtFax.findById("dsMinimoId").getValue(),
                                    swObliga: admEdtFax.findById("obligatorioId").getValue(),                   
                                    otTabVal: admEdtFax.findById("dsTablaId").getValue(),
                                    status: admEdtFax.findById("comboMetEnvId").getValue(),
				    			},
	 					waitMsg : getLabelFromMap('400021', helpMap,'Espere por favor ...'),
                   		     callback: function (options, success, response) {
                                if (Ext.util.JSON.decode(response.responseText).success == false) {
                                        Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), Ext.util.JSON.decode(response.responseText).errorMessages[0]);
                                } else {
                                        Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), Ext.util.JSON.decode(response.responseText).actionMessages[0], function(){reloadGrid();});
                                }
    }
 })
}
};
