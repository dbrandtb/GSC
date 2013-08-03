function statusCasosTareas(key) { 
//alert(key);

var codigoStatus = new Ext.form.Hidden( {
                disabled:false,
                name:'codigoStatus',
                id:'codigoStatusId'
            });

var dscrStatus = new Ext.form.TextField({
       	fieldLabel: getLabelFromMap('txtFldAdmSttCsProcesos', helpMap,'Estado'), 
		tooltip: getToolTipFromMap('txtFldAdmSttCsProcesos', helpMap,'Estado'),  				                    	
        id: 'dscrStatusId', 
        name: 'desStatus',
        allowBlank: false,
        readOnly:true,
        width:260
    });

var formItemSelector = new Ext.form.FormPanel({ 
    width:590,
    height: 200,
    labelWidth : 75,
    baseCls : 'x-plain',
    layout : 'absolute',
    border : true,
    renderTo: 'formMultiselect',
    items:[{
	       xtype:"itemselector",
	       name:"itemselector",
	       id:"itemselector",
	       fieldLabel:"",
           labelSeparator: "",
           fromStore: storeStatusProcesos,
           toStore: el_storeDerecho,
	       msWidth:282,
	       msHeight:200,
	       valueField:"cdProcesos",
	       displayField:"dsProcesos",
	       toLegend:"Estado por Procesos",
	       fromLegend:"Procesos"
           }]
});


// Nombre y Mensaje
var admStatusCasos =  new Ext.FormPanel({
    width: 590,
    layout: 'table',
    layoutConfig: {columns: 1},
    labelAlign: 'right',
    defaults: {frame:true, width: 586,height: 75},
    border:false,
    reader: elJsonEstatus,
    url: _ACTION_OBTENER_STATUS_CASOS ,
    items: [
            {
                layout: 'form',
                items: [
                        codigoStatus,
                        dscrStatus            
                      ]
            }
           ]
}); 


formItemSelector.render();

var _window = new Ext.Window ({

    width: 596,
    modal: true,
    height: 346,
    items: [
            admStatusCasos,
            formItemSelector
            ],
    buttonAlign: "center",
	buttons:[
        {
		text:getLabelFromMap('ntfcnEdtButtonGuardar',helpMap,'Guardar'),
		tooltip: getToolTipFromMap('ntfcnEdtButtonGuardar',helpMap,'Guarda Estado Procesos'),
		handler: function() {
                    actulizarStatusProcesos(key);
                    
        	}
		},
        {
		text:getLabelFromMap('ntfcnEdtButtonRegresar',helpMap,'Regresar'),
		tooltip: getToolTipFromMap('ntfcnEdtButtonRegresar',helpMap,'Cancelar Guarda Estado Procesos'),                              
		handler: function() {
			storeStatusProcesos.removeAll();
            el_storeDerecho.removeAll();
			_window.close();
		}
	}]
});
_window.show();
//Ext.getCmp('itemselector').refreshFromView(storeGetProceNotifi);
if (key != "") 
{
    admStatusCasos.findById('codigoStatusId').setValue(key);
}


admStatusCasos.form.load (
{
    params: { cdStatus: key},
    waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
    waitMsg : getLabelFromMap('400028',helpMap,'Leyendo datos ...'),
    success: function(form, action){
    //console.log(action.result);
        storeStatusProcesos.load({
        	params: {cdStatus: key},
        	callback: function (r, o, success) {
                Ext.getCmp('itemselector').refreshFromView(storeStatusProcesos);
        		
        	}
   		});   
   		el_storeDerecho.load({
        	params: {cdStatus: key},
        	callback: function (r, o, success) {
                Ext.getCmp('itemselector').refreshToView(el_storeDerecho);
        		
        	}
   		});   
    }
}
);


function actulizarStatusProcesos(codiStatus){

	Ext.getCmp('itemselector').fromMultiselect.view.selectRange();
	Ext.getCmp('itemselector').toMultiselect.view.selectRange();
	
	var csoGrillaListStatusProcesoFrom = Ext.getCmp('itemselector').fromMultiselect.view.getSelectedIndexes();
 	var csoGrillaListStatusProcesoTo = Ext.getCmp('itemselector').toMultiselect.view.getSelectedIndexes();
	
 	if(csoGrillaListStatusProcesoFrom.length <= 0 && csoGrillaListStatusProcesoTo.length <= 0) Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), 'No se carg&oacute; correctamente la p&aacute;gina');
	else guardarStatusProcesos(codiStatus, csoGrillaListStatusProcesoTo, csoGrillaListStatusProcesoFrom);
	
	
}

function guardarStatusProcesos (codiStatus, csoGrillaListStatusProcesoTo, csoGrillaListStatusProcesoFrom) {
 var _params = "";
 
    if (csoGrillaListStatusProcesoTo.length > 0) {
        for (var i=0; i<csoGrillaListStatusProcesoTo.length; i++) {
            var record = Ext.getCmp('itemselector').toMultiselect.view.store.getAt(i);
            _params +=  "csoGrillaListStatusProceso["+ i + "].cdStatus="+ codiStatus + "&" + "csoGrillaListStatusProceso["+ i + "].cdProceso=" + record.get('cdProcesos') + "&";
        }
  }

 if (csoGrillaListStatusProcesoTo.length > 0) {
   var conn = new Ext.data.Connection ();
   conn.request({
     url: _ACTION_GUARDAR_STATUS_CASOS_TAREAS,
     params: _params,
     method: 'POST',
     callback: function (options, success, response) {
         if (Ext.util.JSON.decode(response.responseText).success == false) {
          Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), Ext.util.JSON.decode(response.responseText).errorMessages[0]);
         } else {
          borrarStatusProcesos (codiStatus, csoGrillaListStatusProcesoFrom);
          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), Ext.util.JSON.decode(response.responseText).actionMessages[0],_window.close());
         }
       }
   });
  }
 }
 
 function borrarStatusProcesos (codiStatus, csoGrillaListStatusProcesoFrom) {
 var _params = "";
 
    if (csoGrillaListStatusProcesoFrom.length > 0) {
        for (var i=0; i<csoGrillaListStatusProcesoFrom.length; i++) {
            var record = Ext.getCmp('itemselector').fromMultiselect.view.store.getAt(i);
            _params +=  "csoGrillaListStatusProceso["+ i + "].cdStatus="+ codiStatus + "&" + "csoGrillaListStatusProceso["+ i + "].cdProceso=" + record.get('cdProcesos') + "&";
        }
  }

 if (csoGrillaListStatusProcesoFrom.length > 0) {
   var conn = new Ext.data.Connection ();
   conn.request({
     url: _ACTION_BORRAR_STATUS_CASOS_TAREAS,
     params: _params,
     method: 'POST'/*,
     callback: function (options, success, response) {
         if (Ext.util.JSON.decode(response.responseText).success == false) {
          Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), Ext.util.JSON.decode(response.responseText).errorMessages[0]);
         } else {
          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), Ext.util.JSON.decode(response.responseText).actionMessages[0],_window.close());
         }
       }*/
   });
  }
 }

};
