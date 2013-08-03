function agregar(codRenova, _cdElemento) {

	var leerEncabezado=new Ext.data.JsonReader({
           	root:'encabezado',
           	totalProperty: 'totalCount',
            successProperty : '@success'
	        },[
	        {name: 'dsElemen', type: 'string',  mapping:'dsElemen'},
	        {name: 'dsUniEco', type: 'string',  mapping:'dsUniEco'},
	        {name: 'dsTipoRenova',   type: 'string',  mapping:'dsTipoRenova'},
	        {name: 'dsRamo',   type: 'string',  mapping:'dsRamo'}
			]);
			
	var desRoles = new Ext.data.Store({
	        proxy: new Ext.data.HttpProxy({
	            url: _ACTION_OBTENER_ACCIONES_RENOVACION_ROL
	            }),
	        reader: new Ext.data.JsonReader({
	        root: 'comboAccionesRenovacionRol',
	        id: 'cdSisRol'
	        },[
	       {name: 'cdEstado', mapping:'cdEstado', type: 'string'},
	       {name: 'cdSisRol', mapping:'cdSisRol', type: 'string'},
	       {name: 'dsSisRol', mapping:'dsSisRol', type: 'string'}
	    ])
	});			
	
var comboRoles = new Ext.form.ComboBox({
    tpl: '<tpl for="."><div ext:qtip="{cdSisRol}.{dsSisRol}" class="x-combo-list-item">{dsSisRol}</div></tpl>',
    id:'comboRolesId',
    store: desRoles,
    displayField:'dsSisRol',
    valueField:'cdSisRol',
    hiddenName: 'cdRol',
    typeAhead: true,
    allowBlank : false,
    mode: 'local',
    triggerAction: 'all',
    fieldLabel: getLabelFromMap('agrConfRolRenCboRol',helpMap,'Rol'),
    tooltip: getToolTipFromMap('agrConfRolRenCboRol',helpMap,'Elija Rol'),
	hasHelpIcon:getHelpIconFromMap('agrConfRolRenCboRol',helpMap),
    helpText:getHelpTextFromMap('agrConfRolRenCboRol',helpMap),
    selectOnFocus:true,
    forceSelection:true,
    emptyText:'Seleccione Rol ..'
    
    });	


	var formularioStore = new Ext.data.Store({
   			proxy: new Ext.data.HttpProxy({
			url: _ACTION_GET_ENCABEZADO_ROLES_RENOVACION,
			waitMsg: 'Espere por favor....'
            }),
            reader: leerEncabezado
        });
		    	

	var form_agregar = new Ext.FormPanel ({
            labelWidth : 10,
            url : _ACTION_GET_ENCABEZADO_ROLES_RENOVACION,
            frame : true,
            width : 500,
            height: 160,
            waitMsgTarget : true,
            layout: 'column',
            layoutConfig: {columns: 2},
            defaults:{labelWidth:70},
            bodyStyle:'background: white',
            store:leerEncabezado,
            labelAlign:'right',
            reader: leerEncabezado,
            items: [
            			{layout: 'form',
            			columnWidth: .50,
            			items: [
				                  {xtype: 'textfield',
								  fieldLabel: getLabelFromMap('agrConfRolRenTxtCli',helpMap,'Cliente'),
								  tooltip: getToolTipFromMap('agrConfRolRenTxtCli',helpMap,'Cliente en Roles de Ejecuci&oacute;n'),
								  hasHelpIcon:getHelpIconFromMap('agrConfRolRenTxtCli',helpMap),
								  helpText:getHelpTextFromMap('agrConfRolRenCboCli',helpMap),
				                  id: 'dsElemenId', 
				                  name: 'dsElemen', 
				                  readOnly:'true',
				                  disabled:true}
            					]
            			},
            			{layout: 'form',
            			columnWidth: .50,
            			items: [
            						{xtype: 'textfield',
								    fieldLabel: getLabelFromMap('agrConfRolRenTxtAseg',helpMap,'Aseguradora'),
								    tooltip: getToolTipFromMap('agrConfRolRenTxtAseg',helpMap,'Aseguradora en Roles de Ejecuci&oacute;n'),
								    hasHelpIcon:getHelpIconFromMap('agrConfRolRenTxtAseg',helpMap),
								    helpText:getHelpTextFromMap('agrConfRolRenTxtAseg',helpMap),
            						id: 'dsUniEcoId', 
            						name: 'dsUniEco', 
            						readOnly:'true',
				                  	disabled:true}            						
            					]
            			},
            			{layout: 'form',
            			columnWidth: .50,
            			items: [
				                    {xtype: 'textfield', 
								    fieldLabel: getLabelFromMap('agrConfRolRenTxtTip',helpMap,'Tipo'),
								    tooltip: getToolTipFromMap('agrConfRolRenTxtTip',helpMap,'Tipo en Roles de Ejecuci&oacute;n'),
								    hasHelpIcon:getHelpIconFromMap('agrConfRolRenTxtTip',helpMap),
								    helpText:getHelpTextFromMap('agrConfRolRenTxttip',helpMap),
				                    id: 'dsTipoRenovaId', 
				                    name: 'dsTipoRenova', 
				                    readOnly:'true',
				                  	disabled:true}
            					]
            			},
            			{layout: 'form',
            			columnWidth: .50,
            			items: [
            						{xtype: 'textfield', 
								    fieldLabel: getLabelFromMap('agrConfRolRenTxtProd',helpMap,'Producto'),
								    tooltip: getToolTipFromMap('agrConfRolRenTxtProd',helpMap,'Producto en Roles de Ejecuci&oacute;n'),
								    hasHelpIcon:getHelpIconFromMap('agrConfRolRenTxtProd',helpMap),
								    helpText:getHelpTextFromMap('agrConfRolRenTxtProd',helpMap),
            						name: 'dsRamo', 
            						id: 'dsRamoId', 
            						readOnly:'true',
				                  	disabled:true},
            						
            						{xtype: 'hidden', value: _CodRenovacion, name: 'cdRenova'}
            					]
            			},
            			{layout: 'form',
            			columnWidth: .60,             			
            			items: [
            						comboRoles
            					]
            			}
            		]

	});


	var window = new Ext.Window ({
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('el_formSeccionesId', helpMap,'Configurar Rol de Ejecuci&oacute;n de Renovaci&oacute;n')+'</span>',
			width: 500,
			autoHeight: true,
        	bodyStyle:'padding:5px;',
        	buttonAlign:'center',
        	modal: true,
        	items: form_agregar,
            buttons : [ {

       		    text: getLabelFromMap('agrConfRolRenBtnSave', helpMap,'Guardar'),

       		    tooltip:getToolTipFromMap('agrConfRolRenBtnSave',helpMap,' Guarda un Nuevo Rol de Renovaci&oacute;n'),

                disabled : false,

                handler : function() {

                    if (form_agregar.form.isValid()) {

                        form_agregar.form.submit( {

                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_AGREGAR_GUARDAR_ROL_RENOVACION,

                             //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert('Aviso', action.result.actionMessages[0], function() {reloadGrid ();});
                                window.close();                                     
                            },

                           //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.Msg.alert('Error', action.result.errorMessages[0]);
                            },

                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : 'guardando datos ...'

                        });

                    } else {

                        Ext.Msg.alert('Aviso', 'Complete la informaci&oacute;n requerida');

                    }
					//reloadGrid();
                }

            }, {

       		    text: getLabelFromMap('agrConfRolRenBtnCanc', helpMap,'Cancelar'),

       		    tooltip:getToolTipFromMap('agrConfRolRenBtnCanc',helpMap,' Cancela operaci&oacute;n de Rol de Renovaci&oacute;n'),

                handler : function() {
                    window.close();
                }

            }]

	});

    window.show();
    form_agregar.load({params:{cdRenova:_CodRenovacion}});
    desRoles.load({
    params: {cdelemento: _cdElemento}
    });
}