
function editar(key, region, metodoEnv) { 
	
	
	
var ANCHO_VENTANA=700;
var codigo = new Ext.form.Hidden( {
                disabled:false,
                name:'cdNotificacion',
                id:'cdNotificacion'
            });

var nombre = new Ext.form.TextField({
       	fieldLabel: getLabelFromMap('dsNotificacion', helpMap,'Nombre'), 
		tooltip: getToolTipFromMap('dsNotificacion', helpMap,'Nombre de la Notificaci&oacute;n'),  				                    	
        hasHelpIcon:getHelpIconFromMap('dsNotificacion',helpMap),
        Ayuda:getHelpTextFromMap('dsNotificacion',helpMap),
        id: 'dsNotificacion', 
        name: 'dsNotificacion',
        allowBlank: false,
        width:260
    });


//var procesoyTarea = new Ext.form.TextField({
//       	fieldLabel: getLabelFromMap('procesoyTarea', helpMap,'Proceso/Tarea'), 
//		tooltip: getToolTipFromMap('procesoyTarea', helpMap,'Proceso/Tareas de la Notificaci&oacute;n'),  				                    	
//        hasHelpIcon:getHelpIconFromMap('procesoyTarea',helpMap),
//        Ayuda:getHelpTextFromMap('procesoyTarea',helpMap),
//        id: 'procesoyTarea', 
//        name: 'procesoyTarea',
//        allowBlank: true,
//        width:260
//    });

//LOS COMBOS

var comboRegion = new Ext.form.ComboBox(
{
    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
    store: dsRegion,
    id:'comboRegionId',
    fieldLabel: getLabelFromMap('comboRegionId',helpMap,'Regi&oacute;n'),
    tooltip: getToolTipFromMap('comboRegionId',helpMap,'Regi&oacute;n'),
    hasHelpIcon:getHelpIconFromMap('comboRegionId',helpMap),
    Ayuda:getHelpTextFromMap('comboRegionId',helpMap),
    //anchor:'100%',
    width:150,
    allowBlank: false,
    displayField:'descripcion',
    valueField: 'codigo',
    hiddenName: 'dsRegion',
    typeAhead: true,
    triggerAction: 'all',
    emptyText:'Seleccione la Región...',
    selectOnFocus:true,
    forceSelection:true
}
);

var comboMetodoEnvio = new Ext.form.ComboBox(
{
    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
    store: dsTipoMetodoEnvio,
    id:'comboMetEnvId',
    fieldLabel: getLabelFromMap('comboMetEnvId',helpMap,'M&eacute;todo Env&iacute;o'),
    tooltip: getToolTipFromMap('comboMetEnvId',helpMap,'M&eacute;todo Env&iacute;o a utilizar'),
    hasHelpIcon:getHelpIconFromMap('comboMetEnvId',helpMap),
    Ayuda:getHelpTextFromMap('comboMetEnvId',helpMap),
   //anchor:'100%',
    width:150,
    allowBlank: false,
    displayField:'descripcion',
    valueField: 'codigo',
    hiddenName: 'codigo',
    typeAhead: true,
    triggerAction: 'all',
    emptyText:'Seleccione Metodo de Envio...',
    selectOnFocus:true,
    forceSelection:true
}
);

var formItemSelector = new Ext.form.FormPanel({ 
    width:ANCHO_VENTANA-15,
    height: 130,
    //autoScroll : true,
    labelWidth : 75,
    //baseCls : 'x-window',
    layout : 'absolute',
    border : false,
    renderTo: 'formMultiselect',
    items:[{
	       xtype:"itemselector",
           //height: 70,
	       name:"itemselector",
	       id:"itemselector",
	       //fieldLabel:"",
	       fieldLabel: getLabelFromMap('itemselector',helpMap,''),
	       tooltip: getToolTipFromMap('itemselector',helpMap,'Movimientos de Proceso'),
	       //hasHelpIcon:getHelpIconFromMap('itemselector',helpMap),
	       //Ayuda:getHelpTextFromMap('itemselector',helpMap,'Help'),
           fromStore: storeGetProceNotifi,
           toStore: el_storeDer,
	       msWidth:324,
	       msHeight:100,
	       valueField:"cdProceso",
	       displayField:"dsProceso",
	       toLegend:"Procesos en Notificaci&oacute;n",
	       fromLegend:"Procesos Back Office"/*,
	       toTBar:[{
	              text:"Borrar",
	              handler:function(){
	                     var i=formItemSelector.getForm().findField("itemselector");
    	                     i.reset.call(i);
	                  }
	              }]*/
           }]
});

var formItemSelectorEdos = new Ext.form.FormPanel({ 
    width:ANCHO_VENTANA-15,
    height: 130,
    //autoScroll : true,
    labelWidth : 75,
    //baseCls : 'x-window',
    layout : 'absolute',
    border : false,
    renderTo: 'formMultiselectEdos',
    items:[
           {
	       xtype:"itemselector",
           //height: 200,
	       name:"itemselectorEdos",
	       id:"itemselectorEdos",
	       //fieldLabel:"",
	       fieldLabel: getLabelFromMap('itemselectorEdos',helpMap,''),
	       tooltip: getToolTipFromMap('itemselectorEdos',helpMap,'Movimientos de Estado'),
	       //hasHelpIcon:getHelpIconFromMap('itemselector',helpMap),
	       //Ayuda:getHelpTextFromMap('itemselector',helpMap,'Help'),
           fromStore: storeGetEdosCaso,
           toStore: storeGetEdosNotifi,
	       msWidth:324,
	       msHeight:100,
	       valueField:"cdEstado",
	       displayField:"dsEstado",
	       toLegend:"Estados en Notificaci&oacute;n",
	       fromLegend:"Estados de Casos"/*,
	       toTBar:[{
	              text:"Borrar",
	              handler:function(){
	                     var i=formItemSelector.getForm().findField("itemselector");
    	                     i.reset.call(i);
	                  }
	              }]*/
           }]
});
var root = new Ext.tree.TreeNode({
            text: 'elementosTreeView', 
            draggable:false, 
            id:'elementosTreeView'
        });
        
var arbol = new Ext.tree.TreePanel ({
  title:'Variables',
          autoScroll:true,
          border:false,
          iconCls:'nav',
  split:true,
  width: 200,
  minSize: 175,
  maxSize: 400,
  collapsible: true,
  margins:'0 0 0 0',
  loader: new Ext.tree.TreeLoader(),
  root:root,
  rootVisible: false
});

var formVariablesEditorHtml = new Ext.form.FormPanel({ 
    width:ANCHO_VENTANA-10,
    height: 180,
    frame : true,
    border : false,
    //bodyStyle : 'padding:5px 5px 0',
    labelWidth : 10,
    //baseCls : '',
    layout : 'column',
    labelAlign:'right',
    //renderTo: 'formVariablesHtml',
    items:[{
        			layout: 'column',
        			columnWidth: 1.0,
        			labelWidth: 0,
        			labelSeparator: '',
        			items: [
			        		{
			        			layout: 'form',
			        			columnWidth: .22,
			        			labelWidth: 0,
			        			labelSeparator: '',
			        			items: [
			        						{
			        							id: 'accordion1',
										        split:true,
										        width:140,
										        height: 150,
										        minSize:'200',
										        maxSize:'200',
										        margins:'0 2 0 2',
										        bodyStyle:'padding:0px',
										        layout:'accordion',
										        layoutConfig:{
										             animate:true
										        },
										        items: [arbol]
			        						}
			        			]
			        		},
			        		{
			        			layout: 'form',
			        			columnWidth: .78,
			        			labelWidth: 0,
			        			labelAlign: 'top',
			        			labelSeparator: '',
						        bodyStyle:'padding:0px',
			        			items: [
        						{xtype: 'htmleditor', name: 'dsMensaje',allowBlank:false, 
        						fieldLabel: getLabelFromMap('addConfAlertMsg',helpMap,'Mensaje'), 
        						tooltip: getToolTipFromMap('addConfAlertMsg',helpMap,'Mensaje'),
        						hasHelpIcon:getHelpIconFromMap('addConfAlertMsg',helpMap),
								Ayuda: getHelpTextFromMap('addConfAlertMsg',helpMap),
        						id: 'addConfAlertMsg', width:520, height: 140}
        						]
        					}
        			]
        		}
           ]
});


	arbol.on('dblclick', function (nodo, evt) {
			Ext.getCmp('addConfAlertMsg').insertAtCursor(nodo.text);
		});
	
	Ext.Ajax.request ({
		url: _ACTION_BUSCAR_VARS_NOTIFICACIONES,
		method: 'POST',
		success: function (result, request) {
				var listaElementos = Ext.util.JSON.decode(result.responseText).elementosTreeView;
				for (var i=0; i<listaElementos.length; i++) {
					root.appendChild(arbol.getLoader().createNode(listaElementos[i]));
				}
		}
	});



// Nombre y Mensaje
var notificacionMensaje =  new Ext.FormPanel({
    width: ANCHO_VENTANA-15,
    layout: 'column',
    //labelWidth: 70,
    labelAlign: 'right',
    border:false,
    //baseCls:'x-window',
    reader: elJson_GetNotifi,
    url: _ACTION_OBTENER_NOTIFICACIONES,
    items: [
    				{
        			layout: 'column',
        			columnWidth: 1.0,
        			border: false,
        			labelWidth: 190,
        			labelSeparator: '',
        			items: [
			            {
			                layout: 'form',
			                bodyStyle:'padding:4px',
			                columnWidth: 1.0,
			                border: false,
			                height: 35,
			                items: [
			                        codigo,
			                        nombre            
			                        ]
			            }]
			         },{
			            layout: 'column',
			            //bodyStyle:'padding:px',
	        			columnWidth: 1.0,
	        			border: false,
	        			labelWidth: 100,
	        			labelSeparator: '',
	        			items: [
	        				{
	        				layout: 'column',
	        				border: false,
	        				columnWidth: 0.03
	        				},
	        				{
				               layout: 'form', 
				               columnWidth: .42,
				               border: false,
				               height: 35,
				               items: [
				                        comboRegion
				                        ],
				                buttonAlign: 'center'
				                
				            },
				            {
				               layout: 'form',
				               columnWidth: .42,
				               border: false,
				               height: 35,
				               items: [
				                    	comboMetodoEnvio
				                 		]
				            },
				            {
	        				layout: 'column',
	        				border: false,
	        				columnWidth: 0.03
	        				}
	        				] 
				        }
			]
           
}); 


formItemSelector.render();
formItemSelectorEdos.render();


var _window = new Ext.Window ({
     title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('wndwsNtfcacEdtr',helpMap,'Configuración de Notificación')+'</span>',
    width: ANCHO_VENTANA,
    modal: true,
    height: 580,
    items: [
            notificacionMensaje,
            formItemSelector,
            formItemSelectorEdos,
            formVariablesEditorHtml
            ],
    buttonAlign: "center",
	buttons:[{
		text:getLabelFromMap('ntfcnEdtButtonGuardar',helpMap,'Guardar'),
		tooltip: getToolTipFromMap('ntfcnEdtButtonGuardar',helpMap,'Guarda Notificaciones y Procesos'),
		handler: function() {
   			if (notificacionMensaje.form.isValid()) {
   				
   				if (Ext.getCmp('addConfAlertMsg').getValue() == "&nbsp;" || Ext.getCmp('addConfAlertMsg').getValue() == "") {
                       		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400065', helpMap,'Ingrese un mensaje'));
                       		return;
   				}else{
                    guardarNotiProcesos();
                }
                
			} else{
				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
			}
		}
		},{
		text:getLabelFromMap('ntfcnEdtButtonRegresar',helpMap,'Regresar'),
		tooltip: getToolTipFromMap('ntfcnEdtButtonRegresar',helpMap,'Regresar pantalla anterior'),                              
		handler: function() {
			storeGetProceNotifi.removeAll();
            el_storeDer.removeAll();
            storeGetEdosCaso.removeAll();
            storeGetEdosNotifi.removeAll();
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
    waitTitle: getLabelFromMap('400017',helpMap,'Espere por favor ...'),
    waitMsg : getLabelFromMap('400028',helpMap,'Leyendo datos ...'),
    success: function(form, action){
    //console.log(action.result);
    	
        dsRegion.reload({
            
            callback: function (r, o, success) {
            		if (success) {
            			comboRegion.setValue(region);
            		}
            }
        });
        dsTipoMetodoEnvio.load({
        	callback: function (r, o, success) {
        			if (success) {
						comboMetodoEnvio.setValue(metodoEnv);
        			}
        	}
        });
        
        storeGetEdosNotifi.reload({
        params: {cdNotificacion: key},
        callback: function (r, o, success) {
         		Ext.getCmp('itemselectorEdos').refreshToView(storeGetEdosNotifi);
         		
        }
    	});
    
   		el_storeDer.reload({
        params: {cdNotificacion: key},
        callback: function (r, o, success) {
         		Ext.getCmp('itemselector').refreshToView(el_storeDer);
         		
        }
  		});
  		var msgHtml=action.result.data.dsMensaje;
    	
    	if(msgHtml!=null &&msgHtml != undefined )
  		Ext.getCmp("addConfAlertMsg").setValue(msgHtml);
         
    }
}
);
 storeGetProceNotifi.reload({
        params: {cdNotificacion: key},
        callback: function (r, o, success) {
         		Ext.getCmp('itemselector').refreshFromView(storeGetProceNotifi);
         		
        }
    });
    
   
  
    
  storeGetEdosCaso.reload({
        params: {cdNotificacion: key},
        callback: function (r, o, success) {
         		Ext.getCmp('itemselectorEdos').refreshFromView(storeGetEdosCaso);
         		
        }
    });
    
  
    
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
							    		dsMensaje: Ext.getCmp("addConfAlertMsg").getValue(),
	                                    cdRegion: notificacionMensaje.findById("comboRegionId").getValue(),  
							    		cdMetEnv: notificacionMensaje.findById("comboMetEnvId").getValue()
					    			},
		 					waitMsg : getLabelFromMap('400017', helpMap,'Espere por favor ...'),
	                   		     callback: function (options, success, response) {
	        
	        if (Ext.util.JSON.decode(response.responseText).success == false) {
	   			Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'),getLabelFromMap('400062', helpMap,'No se pudo guardar los datos'));
	            //Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), 'No se pudo guardar los datos');
	        }else {
	            codNoti=Ext.util.JSON.decode(response.responseText).cdNotificacionNew;
	            msjRetorno=Ext.util.JSON.decode(response.responseText).actionMessages[0];
	            guardarProcesosNoti(codNoti,msjRetorno);
	        }
	      }
	  })
	
}
 function guardarProcesosNoti (codNoti,msjRetorno) {
 var _params = "";
 _params +="cdNotificacion="+ codNoti +"&";
 
 Ext.getCmp('itemselector').toMultiselect.view.selectRange();
 Ext.getCmp('itemselectorEdos').toMultiselect.view.selectRange();
 
 var recs = Ext.getCmp('itemselector').toMultiselect.view.getSelectedIndexes();
 var recsEdos = Ext.getCmp('itemselectorEdos').toMultiselect.view.getSelectedIndexes();
 var noNotisVO = -1;
    if (recs.length > 0 && recsEdos.length > 0){
        for (var i=0; i<recs.length; i++) {
            var record = Ext.getCmp('itemselector').toMultiselect.view.store.getAt(i);
            
             for (var j=0; j<recsEdos.length; j++) {
             	noNotisVO++;
            	var recordEdos = Ext.getCmp('itemselectorEdos').toMultiselect.view.store.getAt(j);
           		_params +=  "listaNotificacionVO[" + noNotisVO + "].cdNotificacion=" + codNoti + "&" + "listaNotificacionVO[" + noNotisVO + "].cdProceso=" + record.get('cdProceso') + "&" + "listaNotificacionVO[" + noNotisVO + "].cdEstado=" + recordEdos.get('cdEstado') + "&";
        
        	}
        }
  

 
	   var conn = new Ext.data.Connection ();
	   conn.request({
	     url: _ACTION_GUARDAR_NOTIFICACIONES_PROCESO,
	     params: _params,
	     method: 'POST',
	     callback: function (options, success, response) {
	         if (Ext.util.JSON.decode(response.responseText).success == false) {
	          Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), Ext.util.JSON.decode(response.responseText).errorMessages[0]);
	         } else {
	         	
	          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), msjRetorno/*Ext.util.JSON.decode(response.responseText).actionMessages[0]*/, function(){reloadGridNoti();});
	          _window.close();
	         }
	       }
	   });
   
   } //Fin del if else si hay elementos en los itemselectors
   
   storeGetProceNotifi.removeAll();
   el_storeDer.removeAll();
   storeGetEdosCaso.removeAll();
   storeGetEdosNotifi.removeAll();
   _window.close();
  }

};
