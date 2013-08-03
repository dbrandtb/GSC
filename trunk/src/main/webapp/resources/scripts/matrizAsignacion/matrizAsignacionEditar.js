/*
function editar(key) { 
//alert(key);
var codigo = new Ext.form.Hidden( {
                disabled:false,
                name:'cdNotificacion',
                id:'cdNotificacion'
            });

var nombre = new Ext.form.TextField({
       	fieldLabel: getLabelFromMap('txtFldNmbrNtfcn', helpMap,'Nombre'), 
		tooltip: getToolTipFromMap('txtFldNmbrNtfcn', helpMap,'Nombre de la notificacion'),  				                    	
        id: 'dsNotificacion', 
        name: 'dsNotificacion',
        allowBlank: false,
        width:260
    });

var mensaje = new Ext.form.TextField({
       	fieldLabel: getLabelFromMap('txtFldMnsjNtfcn', helpMap,'Mensaje'), 
		tooltip: getToolTipFromMap('txtFldMnsjNtfcn', helpMap,'Mensaje de la notificacion'),  				                    	
        id: 'dsFormatoOrden', 
        name: 'dsFormatoOrden',
        allowBlank: false,
        width:260
    });

var procesoyTarea = new Ext.form.TextField({
       	fieldLabel: getLabelFromMap('txtFldPrcsTrNtfcn', helpMap,'Proceso/Tarea'), 
		tooltip: getToolTipFromMap('txtFldPrcsTrNtfcn', helpMap,'Proceso/Tareas de la notificacion'),  				                    	
        id: 'procesoyTarea', 
        name: 'procesoyTarea',
        allowBlank: true,
        width:260
    });

//LOS COMBOS

var comboFormato = new Ext.form.ComboBox(
{
    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
    store: dsNombreFormato,
    id:'comboFormatoId',
    fieldLabel: getLabelFromMap('cmbNtfEdtDsNomFormato',helpMap,'Formato'),
    tooltip: getToolTipFromMap('cmbNtfEdtNomFormato',helpMap),
    anchor:'100%',
    allowBlank: false,
    displayField:'descripcion',
    valueField: 'codigo',
    hiddenName: 'dsFormatoOrden',
    typeAhead: true,
    triggerAction: 'all',
    emptyText:'Seleccionar El Formato...',
    selectOnFocus:true,
    forceSelection:true
}
);

var comboMetodoEnvio = new Ext.form.ComboBox(
{
    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
    store: dsTipoMetodoEnvio,
    id:'comboMetEnvId',
    fieldLabel: getLabelFromMap('cmbNtfEdtDsMetEnv',helpMap,'Metodo Envio'),
    tooltip: getToolTipFromMap('cmbNtfEdtDsMetEnv',helpMap),
    anchor:'100%',
    allowBlank: false,
    displayField:'descripcion',
    valueField: 'codigo',
    hiddenName: 'codigo',
    typeAhead: true,
    triggerAction: 'all',
    emptyText:'Seleccionar Metodo de Envio...',
    selectOnFocus:true,
    forceSelection:true
}
);

var formItemSelector = new Ext.form.FormPanel({ 
    width:590,
    height: 200,
    renderTo: 'formMultiselect',
    items:[{
	       xtype:"itemselector",
           height: 200,
	       name:"itemselector",
	       id:"itemselector",
	       fieldLabel:"",
           labelSeparator: "",
           fromStore: storeGetProceNotifi,
           toStore: el_storeDer,
	       msWidth:250,
	       msHeight:200,
	       valueField:"cdProceso",
	       displayField:"dsProceso",
	       toLegend:"Procesos/Tarea Asociado",
	       fromLegend:"Procesos/Tarea",
	       toTBar:[{
	              text:"Borrar",
	              handler:function(){
	                     var i=formItemSelector.getForm().findField("itemselector");
    	                     i.reset.call(i);
	                  }
	              }]
           }]
});


// Nombre y Mensaje
var notificacionMensaje =  new Ext.FormPanel({
    width: 590,
    layout: 'table',
    layoutConfig: {columns: 3},
    //labelWidth: 70,
    defaults: {frame:true, width:295, height: 75},
    border:false,
    reader: elJson_GetNotifi,
    url: _ACTION_OBTENER_NOTIFICACIONES,
    items: [
            {
                colspan: 3,
                layout: 'form',
                width:590,
                items: [
                        codigo,
                        nombre,            
                        mensaje
                        ]
            },
            {
               colspan: 2,
               height: 85,
               layout: 'form',               
               items: [
                        comboFormato
                        ],
                buttonAlign: 'center',
                buttons:[
                 {
                    text:getLabelFromMap('dtlFrmtButtonDetalle', helpMap,'Detalle'),
                    tooltip:getToolTipFromMap('dtlFrmtButtonDetalle', helpMap,'Ir a Registrar los formatos'),                               
                    handler: function () {
                        ventana();
                     }
                 }
                ]
            },
            {
               height: 85,
               layout: 'form', 
                items: [
                    comboMetodoEnvio
                 ]
            },
            {
                width:590,
                height: 90,
                colspan: 3,
                layout: 'form',
                items:[
                        procesoyTarea                                      
                    ],
                buttonAlign: 'center',           
                buttons:[
                        {
                        text:getLabelFromMap('frmtDcmntButtonBuscar',helpMap,'Buscar'),
                        tooltip:getToolTipFromMap('frmtDcmntButtonBuscar',helpMap,'Buscar formato de documentos'),
                        handler: function() {
                            if (notificacionMensaje.findById('procesoyTarea').getValue()) {
                                     buscarProceso(notificacionMensaje.findById('procesoyTarea').getValue());                             
                            }
                            else{
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
                            }                               
                         }
                        }
                    ]
            }        
           ]
}); 


formItemSelector.render();
var _window = new Ext.Window ({
    //title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('96',helpMap,'Formato de Documentos')+'</span>',
    width: 610,
    modal: true,
    height: 560,
    items: [
            notificacionMensaje,
            formItemSelector
            ],
    buttonAlign: "center",
	buttons:[{
		text:getLabelFromMap('ntfcnEdtButtonGuardar',helpMap,'Guardar'),
		tooltip: getToolTipFromMap('ntfcnEdtButtonGuardar',helpMap,'Guarda Notificaciones y Procesos'),
		handler: function() {
   			if (notificacionMensaje.form.isValid()) {
                    guardarNotiProcesos();
			} else{
				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
			}
		}
		},{
		text:getLabelFromMap('ntfcnEdtButtonRegresar',helpMap,'Regresar'),
		tooltip: getToolTipFromMap('ntfcnEdtButtonRegresar',helpMap,'Cancelar busqueda de Notificaciones'),                              
		handler: function() {
			storeGetProceNotifi.removeAll();
            el_storeDer.removeAll();
			_window.close();
		}
	}]
});
_window.show();
//Ext.getCmp('itemselector').refreshFromView(storeGetProceNotifi);
if (key != "") 
{
    notificacionMensaje.findById('cdNotificacion').setValue(key);
}


notificacionMensaje.form.load (
{
    params: { cdNotificacion: key},
    waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
    waitMsg : getLabelFromMap('400028',helpMap,'Leyendo datos ...'),
    success: function(form, action){
    //console.log(action.result);
    	var cdFormato_Orden = action.result.data.cdFormatoOrden;
    	var cdMet_Env = action.result.data.cdMetEnv;
        dsNombreFormato.reload({
            params: { dsNomFormato:action.result.data.dsFormatoOrden},
            callback: function (r, o, success) {
            		if (success) {
            			comboFormato.setValue(cdFormato_Orden);
            		}
            }
        });
        dsTipoMetodoEnvio.load({
        	callback: function (r, o, success) {
        			if (success) {
						comboMetodoEnvio.setValue(cdMet_Env);
        			}
        	}
        });
        el_storeDer.reload({
        params: {cdNotificacion: key},
        callback: function (r, o, success) {
         		Ext.getCmp('itemselector').refreshToView(el_storeDer);
        }
    });   
    }
}
);

function  buscarProceso(parametro){
    storeGetProceNotifi.reload({
        params: {   
                    cdNotificacion:key,
                    dsProceso: parametro
                 },
        callback: function (r, o, success) {
        		Ext.getCmp('itemselector').refreshFromView(storeGetProceNotifi);
        }
    });
}
function cbkBuscar (_success, _message) {
	if (!_success) {
		Ext.Msg.alert('Error', _message);
	}
}

function guardarNotiProcesos(){
    var conn = new Ext.data.Connection();
	                conn.request({
				    	url: _ACTION_GUARDAR_NOTIFICACIONES,
				    	method: 'POST',
				    	params: {
				    				cdNotificacion: key,
				    				dsNotificacion: notificacionMensaje.findById("dsNotificacion").getValue(),
						    		dsMensaje: notificacionMensaje.findById("dsFormatoOrden").getValue(),
                                    cdFormatoOrden: notificacionMensaje.findById("comboFormatoId").getValue(),  
						    		cdMetEnv: notificacionMensaje.findById("comboMetEnvId").getValue()
				    			},
	 					waitMsg : getLabelFromMap('400021', helpMap,'Espere por favor ...'),
                   		     callback: function (options, success, response) {
        if (Ext.util.JSON.decode(response.responseText).success == false) {
            Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), 'No se pudo guardar los datos');
        }else {
            codNoti=Ext.util.JSON.decode(response.responseText).cdNotificacion;
            guardarProcesosNoti(codNoti);
        }
    }
 })
}
 function guardarProcesosNoti (codNoti) {
 var _params = "";
 _params +="cdNotificacion="+ codNoti +"&";
 Ext.getCmp('itemselector').toMultiselect.view.selectRange();
 var recs = Ext.getCmp('itemselector').toMultiselect.view.getSelectedIndexes();
    if (recs.length > 0) {
        for (var i=0; i<recs.length; i++) {
            var record = Ext.getCmp('itemselector').toMultiselect.view.store.getAt(i);
            _params +=  "listaNotificacionVO[" + i + "].cdNotificacion=" + codNoti + "&" + "listaNotificacionVO[" + i + "].cdProceso=" + record.get('cdProceso') + "&";
        }
  }

 if (recs.length > 0) {
   var conn = new Ext.data.Connection ();
   conn.request({
     url: _ACTION_GUARDAR_NOTIFICACIONES_PROCESO,
     params: _params,
     method: 'POST',
     callback: function (options, success, response) {
         if (Ext.util.JSON.decode(response.responseText).success == false) {
          Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), Ext.util.JSON.decode(response.responseText).errorMessages[0]);
         } else {
          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), Ext.util.JSON.decode(response.responseText).actionMessages[0], function(){reloadGrid();});
         }
       }
   });
  }
 }

};
*/