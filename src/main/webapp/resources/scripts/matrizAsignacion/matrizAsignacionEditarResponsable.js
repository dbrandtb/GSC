function editarResponsable(record){

var cdmatriz = new Ext.form.TextField({
              name: 'cdmatriz',
              id:'cdmatrizId',
              labelSeparator:'',
              hidden:true
          });
    
 var cdnivatn = new Ext.form.TextField({
              name: 'cdnivatn',
              id:'cdnivatnId',
              labelSeparator:'',
              hidden:true
          }); 
          
          
var cdusuario = new Ext.form.TextField({
              name: 'cdusuario',
              id:'cdusuarioId',
              labelSeparator:'',
              hidden:true
          }); 
          
var cdmodulo = new Ext.form.TextField({
              name: 'cdmodulo',
              id:'cdmoduloId',
              labelSeparator:'',
              hidden:true
          }); 
          


var dsNivelAtencion = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsNivelAtencionIdEdRes',helpMap,'Nivel Atenci&oacute;n'),
        tooltip:getToolTipFromMap('dsNivelAtencionIdEdRes',helpMap,'Nivel Atenci&oacute;n'),
        hasHelpIcon:getHelpIconFromMap('dsNivelAtencionIdEdRes',helpMap),
		Ayuda: getHelpTextFromMap('dsNivelAtencionIdEdRes',helpMap),
        id: 'dsNivelAtencionIdEdRes', 
        name: 'dsnivatn',
        disabled:true,
        //anchor: '100%'
        width:200
    });
   
var dsResponsable = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsResponsableIdEdRes',helpMap,'Responsable'),
        tooltip:getToolTipFromMap('dsResponsableIdEdRes',helpMap,'Responsable'),
        hasHelpIcon:getHelpIconFromMap('dsResponsableIdEdRes',helpMap),
		Ayuda: getHelpTextFromMap('dsResponsableIdEdRes',helpMap),
        id: 'dsResponsableIdEdRes', 
        name: 'dsusuari',
        disabled:true,
        anchor:'85%'
        //width:120
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
			{name: 'id', type: 'string',mapping:'codigo'},
			{name: 'texto', type: 'string',mapping:'descripcion'}
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
   	fieldLabel: getLabelFromMap('txtMailIdEd', helpMap,'Email'), 
	tooltip: getToolTipFromMap('txtMailIdEd', helpMap,'Email'),
	hasHelpIcon:getHelpIconFromMap('txtMailIdEd',helpMap),
	Ayuda: getHelpTextFromMap('txtMailIdEd',helpMap),
    id: 'txtMailIdEd', 
    name: 'email',
    anchor:'80%',
    vtype:'email',
	emailText:getLabelFromMap('400079', helpMap,'Este campo deber&iacute;a ser una direcci&oacute;n email con el formato usuario@dominio.com'),//'Este campo deber&iacute;a ser una direcci&oacute;n email con el formato usuario@dominio.com',
	blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido')//'Este campo es requerido'
    //width:190
});
    

//LOS COMBOS

var comboRoles = new Ext.form.ComboBox(
{
    tpl: '<tpl for="."><div ext:qtip="{id}.{texto}.{descripLarga}" class="x-combo-list-item">{texto}</div></tpl>',
    store: dsRoles,
    id:'comboRolesIdEd',
    fieldLabel: getLabelFromMap('comboRolesIdEd',helpMap,'Rol en Matriz'),
    tooltip: getToolTipFromMap('comboRolesIdEd',helpMap,'Listado de Roles'),
    hasHelpIcon:getHelpIconFromMap('comboRolesIdEd',helpMap),
	Ayuda: getHelpTextFromMap('comboRolesIdEd',helpMap),
    anchor:'80%',
    //width:110,
    displayField:'texto',
    valueField: 'id',
    hiddenName: 'cdrolmat',
    typeAhead: true,
    allowBlank: false,
    mode:'local',
    triggerAction: 'all',
    emptyText:'Seleccionar Rol...',
    selectOnFocus:true,
    forceSelection:true
}
);




var comboStatus = new Ext.form.ComboBox({
    tpl: '<tpl for="."><div ext:qtip="{id}.{texto}" class="x-combo-list-item">{texto}</div></tpl>',
    store: dsStatus,
    id:'comboStatusIdEd',
    fieldLabel: getLabelFromMap('comboStatusIdEd',helpMap,'Estado'),
    tooltip: getToolTipFromMap('comboStatusIdEd',helpMap,'Listado de Status'),
    hasHelpIcon:getHelpIconFromMap('comboStatusIdEd',helpMap),
	Ayuda: getHelpTextFromMap('comboStatusIdEd',helpMap),
    anchor:'90%',
    //width:115,
    displayField:'texto',
    valueField: 'id',
    hiddenName: 'status',
    typeAhead: true,
    triggerAction: 'all',
    allowBlank: false,
    mode:'local',
    emptyText:'Seleccionar Status...',
    selectOnFocus:true,
    forceSelection:true,
    onSelect: function(_record){
    	this.setValue(_record.get("id"));
    	this.collapse();
    	if(_record.get("id")==0){  		
    		var conn = new Ext.data.Connection();
            conn.request({
			    	url: _ACTION_VALIDAR_SUPLENTE_RESPONSABLE,
			    	method: 'POST',
			    	params: {
		    				pv_cdmatriz_i: formWindowEdit.findById("cdmatrizId").getValue(),
		    				pv_cdnivatn_i: formWindowEdit.findById("cdnivatnId").getValue(),
                            pv_cdusuari_i: formWindowEdit.findById("cdusuarioId").getValue()
			    			},
 					waitMsg : getLabelFromMap('400021', helpMap,'Espere por favor ...'),
                  	callback: function (options, success, response){                  					
			       			if(Ext.util.JSON.decode(response.responseText).actionMessages == 1){
			           			Ext.MessageBox.confirm('Aviso', 'Confirma que desea reemplazar al usuario seleccionado?',function(btn){
							        if (btn == "yes")
							        {
							        	
							        	reemplazarUsuario(record.get("cdmatriz"),record.get("cdnivatn"), Ext.getCmp('cdusuarioId').getValue(),Ext.getCmp('cdmoduloId').getValue(),record.get("cdrolmat"),record.get("email"),1,"I");		
							        						        									        							         			
					       			}
						       		else 
						       			if(Ext.util.JSON.decode(response.responseText).actionMessages == 0){
						          			reemplazarUsuario(record.get("cdmatriz"),record.get("cdnivatn"),Ext.getCmp('cdusuarioId').getValue(),Ext.getCmp('cdmoduloId').getValue());
						          			//reemplazarUsuario(record.get("cdmatriz"),record.get("cdnivatn"), Ext.getCmp('cdusuarioId').getValue(),Ext.getCmp('cdmoduloId').getValue(),record.get("cdrolmat"),record.get("email"),1,"I");
						          	  }
  							})
  						}
  						
  						else
  						reemplazarUsuario(record.get("cdmatriz"),record.get("cdnivatn"),Ext.getCmp('cdusuarioId').getValue(),Ext.getCmp('cdmoduloId').getValue());
  						//reemplazarUsuario(record.get("cdmatriz"),record.get("cdnivatn"), Ext.getCmp('cdusuarioId').getValue(),Ext.getCmp('cdmoduloId').getValue(),record.get("cdrolmat"),record.get("email"),1,"I");
					}	
    	})   		
    }
    
}
});

function guardarResponsable(){
    var conn = new Ext.data.Connection();
	                conn.request({
				    	url: _ACTION_GUARDAR_RESPONSABLE,
				    	method: 'POST',
				    	params: {
				    				cdmatriz: formWindowEdit.findById("cdmatrizId").getValue(),
				    				cdnivatn: formWindowEdit.findById("cdnivatnId").getValue(),
						    		cdrolmat: formWindowEdit.findById("comboRolesIdEd").getValue(),
                                    cdusuari: formWindowEdit.findById("cdusuarioId").getValue(),  
						    		email: formWindowEdit.findById("txtMailIdEd").getValue(),
						    		status: formWindowEdit.findById("comboStatusIdEd").getValue(),
						    		operacion:'U'
				    			},
	 					waitMsg : getLabelFromMap('400021', helpMap,'Espere por favor ...'),
                   		     callback: function (options, success, response) {
        if (Ext.util.JSON.decode(response.responseText).success == false) {
          Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), Ext.util.JSON.decode(response.responseText).actionErrors[0]);
        //_window.close();     
        }else {
             Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), Ext.util.JSON.decode(response.responseText).actionMessages[0], function(){reloadGridResponsables(formWindowEdit.findById("cdmatrizId").getValue(),formWindowEdit.findById("cdnivatnId").getValue());});
             _window.close();                                         // los datos se guardaron con exito
           
        }
    }
 })
}      




 //se define la forma en que se van a leer los datos que envie la action
        var _jsonFormReader = new Ext.data.JsonReader( {

            //indica el arreglo a leer
            root : 'MEstructuraResponsablesList',

            //indica la cantidad de registro que debe leerse
            totalProperty: 'total',

            //indica el resultado de la respuesta de la action
            successProperty : 'success'

        }, [ 
               {name : 'cdmatriz',mapping : 'cdmatriz',type : 'string'},
               {name : 'cdnivatn', mapping : 'cdnivatn', type : 'string'},
               {name : 'dsnivatn', mapping : 'dsnivatn', type : 'string'},
               {name : 'cdrolmat', mapping : 'cdrolmat', type : 'string'},
               {name : 'cdusr',mapping : 'cdusr',type : 'string'},
               {name : 'cdusuari', mapping : 'cdusuari', type : 'string'},
                {name : 'cdusuario', mapping : 'cdusuario', type : 'string'},
               {name : 'dsnivatn',mapping : 'dsnivatn',type : 'string'},
               {name : 'dsrolmat', mapping : 'dsrolmat', type : 'string'},
               {name : 'dsusuari', mapping : 'dsusuari', type : 'string'},
               {name : 'email',mapping : 'email',type : 'string'},
               {name : 'status',mapping : 'status',type : 'string'},
              {name : 'cdmodulo',mapping : 'cdmodulo',type : 'string'}
               
        
        ]);
        
        
//CONFIGURACION PARA EL FORMULARIO DE EDICIÓN *****************************
var formWindowEdit = new Ext.FormPanel({
		id:'formWindowEditIdRes',
        //title: '<span style="color:black;font-size:14px;">Responsables</span>',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('formWindowEditIdRes', helpMap,'Responsables')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'top',
        frame:true,
        url : _ACTION_GET_RESPONSABLE,

        baseParams : {
                          pv_cdmatriz_i: record.get("cdmatriz"),
                          pv_cdnivatn_i: record.get("cdnivatn"),
                          pv_cdusuario_i: record.get("cdusuari")
                         },
        reader:_jsonFormReader,
        layout: 'table',
		layoutConfig: {columns: 5},
        width: 680,
        height:150,
         items: [{
                
		            			layout: 'form',colspan: 5,
		            			items: [
		            				      dsNivelAtencion
    
		            			       ]
		            		},{
		            			layout: 'form',colspan: 1,width:230,
		            			items: [
		            				     dsResponsable
		            			       ]
		            			       },{
		            			layout: 'form',colspan: 1,width:150,
		            			items: [
		            				     comboRoles
		            			       ]
		            			       },{layout: 'form',width:12,
                				html:'&nbsp;'},
		            			       {
		            			layout: 'form',colspan: 1,width:180,
		            			items: [
		            				     txtMail
		            			       ]
		            			       },
		            			        {
		            			layout: 'form',colspan: 1,width:75,
		            			items: [
		            				     comboStatus
		            				     
		            			       ]
		            			       },
		            			       {
		            			layout: 'form',colspan: 1,
		            			items: [
		            				     cdmatriz,
                                         cdnivatn,
											cdusuario,
											cdmodulo
		            			       ]
		            			       }              			     
		            	],
        		buttons:[{
        							text:getLabelFromMap('ntfcnButtonGuardarEdRes',helpMap,'Guardar'),
        							tooltip: getToolTipFromMap('ntfcnButtonGuardarEdRes',helpMap,'Guarda datos de responsable'),
        							
        							handler: function() {
				               			guardarResponsable();
									}
        							},{
        							text:getLabelFromMap('ntfcnButtonRegresarEdRes',helpMap,'Regresar'),
        							tooltip: getToolTipFromMap('ntfcnButtonRegresarEdRes',helpMap,'Regresa a la pantalla anterior'),                              
        							handler: function() {
        								_window.close();
        							}
        						}]	
		});


 var _window = new Ext.Window({
   	width: 720,
   	height:230,
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

dsStatus.load({callback:function(record,opt,success){dsRoles.load({callback:function(record,opt,success){formWindowEdit.form.load();}});}}); 

};

