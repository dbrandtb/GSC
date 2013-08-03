function agregarResponsable(cdMatriz,_cdproceso){

var dsEstadoActivo = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsEstadoActivoId',helpMap,'Estado'),
        tooltip:getToolTipFromMap('dsEstadoActivoId',helpMap,'Estado del Responsable'),
        hasHelpIcon:getHelpIconFromMap('dsEstadoActivoId',helpMap),
		Ayuda: getHelpTextFromMap('dsEstadoActivoId',helpMap),
        id: 'dsEstadoActivoId', 
        name: 'dsEstadoActivo',
        allowBlank: true,
        value:'Activo',
        disabled:true,
        //anchor: '90%'
        width:55
    });
    
var dsNiveles_Atencion = new Ext.data.Store({
 	proxy: new Ext.data.HttpProxy({
     	url: _ACTION_COMBO_NIVELES_ATENCION
 	}),
 	reader: new Ext.data.JsonReader({
 		root: 'comboNivelAtencion',
 		totalProperty: 'totalCount',
 		id: 'id'
 		},[
			{name: 'id', type: 'string',mapping:'id'},
			{name: 'texto', type: 'string',mapping:'texto'}
		  ]),
		  sortable:true
});
		 		
var dsResponsables = new Ext.data.Store({
 	proxy: new Ext.data.HttpProxy({
     	url: _ACTION_COMBO_RESPONSABLES
 	}),
 	reader: new Ext.data.JsonReader({
 		root: 'comboResponsables',
 		totalProperty: 'totalCount',
 		id: 'id'
 		},[
			{name: 'id', type: 'string',mapping:'id'},
			{name: 'texto', type: 'string',mapping:'texto'}
			//{name: 'modulo', type: 'string',mapping:'modulo'}
		  ])
});



var dsStatus = new Ext.data.Store({
 	proxy: new Ext.data.HttpProxy({
     	url: _ACTION_COMBO_STATUS
 	}),
 	reader: new Ext.data.JsonReader({
 		root: 'estadosEjecutivo',
 		totalProperty: 'totalCount',
 		id: 'id'
 		},[
			{name: 'id', type: 'string',mapping:'id'},
			{name: 'texto', type: 'string',mapping:'texto'}
		  ])
});

var dsRoles = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
        url: _ACTION_COMBO_ROLES
    }),
    reader: new Ext.data.JsonReader({
        root: 'comboRoles',
        totalProperty: 'totalCount',
        id: 'id'
        },[
            {name: 'id', type: 'string',mapping:'id'},
            {name: 'texto', type: 'string',mapping:'texto'}           
          ])
});

		 		 	
var txtMail= new Ext.form.TextField({
   	fieldLabel: getLabelFromMap('txtMailId', helpMap,'Email'), 
	tooltip: getToolTipFromMap('txtMailId', helpMap,'Email'),
	hasHelpIcon:getHelpIconFromMap('txtMailId',helpMap),
	Ayuda: getHelpTextFromMap('txtMailId',helpMap),
    id: 'txtMailId',
    vtype:'email', 
    name: 'mail',
    //anchor:'85%',
    width: 175,
	emailText:'Este campo deber&iacute;a ser una direcci&oacute;n email con el formato usuario@dominio.com',
	blankText: 'Este campo es requerido'
});
    

//LOS COMBOS
var comboNivelesAtencion = new Ext.form.ComboBox(
{
    tpl: '<tpl for="."><div ext:qtip="{id}.{texto}" class="x-combo-list-item">{texto}</div></tpl>',
    store: dsNiveles_Atencion,
    id:'comboNivelesAtencionId',
    fieldLabel: getLabelFromMap('comboNivelesAtencionId',helpMap,'Nivel de Atenci&oacute;n'),
    tooltip: getToolTipFromMap('comboNivelesAtencionId',helpMap,'Listado de Niveles de Atenci&oacute;n'),
    hasHelpIcon:getHelpIconFromMap('comboNivelesAtencionId',helpMap),
	Ayuda: getHelpTextFromMap('comboNivelesAtencionId',helpMap),
    //anchor:'85%',
    width:190,
    displayField:'texto',
    valueField: 'id',
    hiddenName: 'id',
    typeAhead: true,
    allowBlank: false,
    mode:'local',
    triggerAction: 'all',
    emptyText:'Seleccionar Nivel de Atencion ...',
    selectOnFocus:true,
    forceSelection:true
}
);

var comboResponsables = new Ext.form.ComboBox(
{
    tpl: '<tpl for="."><div ext:qtip="{id}.{texto}" class="x-combo-list-item">{texto}</div></tpl>',
    store: dsResponsables,
    id:'comboResponsablesId',
    fieldLabel: getLabelFromMap('comboResponsablesId',helpMap,'Responsable'),
    tooltip: getToolTipFromMap('comboResponsablesId',helpMap,'Listado de Responsables'),
     hasHelpIcon:getHelpIconFromMap('comboResponsablesId',helpMap),
	Ayuda: getHelpTextFromMap('comboResponsablesId',helpMap),
   // anchor:'80%',
	width:190,
    displayField:'texto',
    valueField: 'id',
    hiddenName: 'comboResponsables',
    typeAhead: true,
    allowBlank: false,
    triggerAction: 'all',
    mode:'local',
    emptyText:'Seleccionar Responsable...',
    selectOnFocus:true,
    forceSelection:true
}
);


var comboRoles = new Ext.form.ComboBox(
{
    tpl: '<tpl for="."><div ext:qtip="{id}.{texto}.{descripLarga}" class="x-combo-list-item">{texto}</div></tpl>',
    store: dsRoles,
    id:'comboRolesId',
    fieldLabel: getLabelFromMap('comboRolesId',helpMap,'Rol en Matriz'),
    tooltip: getToolTipFromMap('comboRolesId',helpMap,'Listado de Roles'),
     hasHelpIcon:getHelpIconFromMap('comboRolesId',helpMap),
	Ayuda: getHelpTextFromMap('comboRolesId',helpMap),
    //anchor:'80%',
	width:110,
    displayField:'texto',
    valueField: 'id',
    hiddenName: 'comboRoles',
    typeAhead: true,
    allowBlank: false,
    mode:'local',
    triggerAction: 'all',
    emptyText:'Seleccionar Rol...',
    selectOnFocus:true,
    forceSelection:true
}
);




var comboStatus = new Ext.form.ComboBox(
{
    tpl: '<tpl for="."><div ext:qtip="{id}.{texto}" class="x-combo-list-item">{texto}</div></tpl>',
    store: dsStatus,
    id:'comboStatusId',
    fieldLabel: getLabelFromMap('comboStatusId',helpMap,'Estado'),
    tooltip: getToolTipFromMap('comboStatusId',helpMap,'Listado de Status'),
     hasHelpIcon:getHelpIconFromMap('comboStatusId',helpMap),
	Ayuda: getHelpTextFromMap('comboStatusId',helpMap),
    //anchor:'85%',
    width:150,
    displayField:'texto',
    valueField: 'id',
    hiddenName: 'id',
    typeAhead: true,
    triggerAction: 'all',
    allowBlank: false,
    emptyText:'Seleccionar Status...',
    mode:'local',
    selectOnFocus:true,
    forceSelection:true
}
);


function guardarResponsable(recarga){
    var conn = new Ext.data.Connection();
    
    if (formWindowEdit.form.isValid()){
	                conn.request({
				    	url: _ACTION_GUARDAR_RESPONSABLE,
				    	method: 'POST',
				    	params: {
				    				cdmatriz: cdMatriz,
						    		cdrolmat: formWindowEdit.findById("comboRolesId").getValue(),
				    				cdnivatn: formWindowEdit.findById("comboNivelesAtencionId").getValue(),
						    	//	cdrolmat: formWindowEdit.findById("comboRolesId").getValue(),
                                    cdusuari: formWindowEdit.findById("comboResponsablesId").getValue(),  
						    		email: formWindowEdit.findById("txtMailId").getValue(),
						    		status: "1", //ACTIVO POR DEFECTO
						    		operacion:'I'
						    	
				    			},
	 					waitMsg : getLabelFromMap('400021', helpMap,'Espere por favor ...'),
                   		     callback: function (options, success, response) {
        if (Ext.util.JSON.decode(response.responseText).success == false) {
        	Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), Ext.util.JSON.decode(response.responseText).errorMessages[0]);
        }else {
             if (recarga==1){
             Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), Ext.util.JSON.decode(response.responseText).actionMessages[0], function(){/*reloadGridNivAtencion(cdMatriz);*/recargarGridNivAtencionEdit(cdMatriz,formWindowEdit.findById("comboNivelesAtencionId").getValue());habilitaBotonesTiempo(); _window.close();});
             }else{
             	Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), Ext.util.JSON.decode(response.responseText).actionMessages[0], function(){ /*recargaGrid=1*/recargarGridNivAtencionEdit(cdMatriz,formWindowEdit.findById("comboNivelesAtencionId").getValue());formWindowEdit.form.reset();});
            
             }
        }
    }
 })
 
 }else{
     Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
 }
}      


//CONFIGURACION PARA EL FORMULARIO DE EDICIÓN *****************************
var formWindowEdit = new Ext.FormPanel({
		id:'formWindowEditIdRes',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('formWindowEditIdRes', helpMap,'Responsables')+'</span>',
        //title: '<span style="color:black;font-size:14px;">Responsables</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        allowBlank: false,        
        labelAlign: 'top',
        frame:true,
        layout: 'table',
		layoutConfig: {columns: 5},
        width: 680,        
        height:150,
         items: [{
                
		            			layout: 'form',colspan: 5,
		            			items: [
		            				     comboNivelesAtencion
		            			       ]
		            		},{
		            			layout: 'form',colspan: 1,width:230,
		            			items: [
		            				     comboResponsables
		            			       ]
		            			       },{
		            			layout: 'form',colspan: 1,width:150,
		            			items: [
		            				     comboRoles
		            			       ]
		            			       },
		            			       {
		            			layout: 'form',colspan: 1,width:210,
		            			items: [
		            				     txtMail
		            			       ]
		            			       },
		            			        {
		            			layout: 'form',colspan: 1,width:80,
		            			items: [
		            				     //comboStatus
		            				     dsEstadoActivo
		            			       ]
		            			       }          			     
		            	],
        		buttons:[{
        							text:getLabelFromMap('ntfcnButtonGuardarRes',helpMap,'Guardar'),
        							tooltip: getToolTipFromMap('ntfcnButtonGuardarRes',helpMap,'Guardar datos de responsable'),
        							handler: function() {
        							
				               			guardarResponsable(1);
				               			
									}
        							},{
        							text:getLabelFromMap('ntfcnButtonNuevoRes',helpMap,'Guardar y Agregar'),
        							tooltip: getToolTipFromMap('ntfcnButtonNuevoRes',helpMap,'Guarda y Agregar un nuevo responsable para un caso'),                              
        							handler: function() {
        								//Resetear Campos
        								guardarResponsable(0);
        								
        							}
        						},{
        							text:getLabelFromMap('ntfcnButtonRegresarRes',helpMap,'Regresar'),
        							tooltip: getToolTipFromMap('ntfcnButtonRegresarRes',helpMap,'Regresar a la pantalla anterior'),                              
        							handler: function() {
        							//	if (recargaGrid){
        								// recargarGridNivAtencionEdit(cdMatriz);
        								// habilitaBotonesTiempo(); //_window.close();});
        								//	reloadGridNivAtencion(cdMatriz);
        									//habilitaBotonesTiempo();
        									//reloadGridResponsables("","");
        							//	}
        								_window.close();
        								habilitaBotonesTiempo();
        							}
        						}]	
		});


 var _window = new Ext.Window({
   	width: 720,
   	height:200,
   	minWidth: 300,
   	minHeight: 100,
   	layout: 'fit',
   	plain:true,
   	modal:true,
   	bodyStyle:'padding:5px;',
   	buttonAlign:'center',
   	items: formWindowEdit
});
_window.show();



//formWindowEdit.form.load();
dsNiveles_Atencion.load();
dsResponsables.load({params:{cdproceso: _cdproceso}});
dsStatus.load();
dsRoles.load();


};
