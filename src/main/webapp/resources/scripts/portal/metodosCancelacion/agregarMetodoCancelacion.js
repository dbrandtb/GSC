// Funcion de Agregar

function agregar() {

        //se define el formulario
        var formPanel = new Ext.FormPanel( {

            labelWidth : 100,

            url : _ACTION_INSERTAR_GUARDAR_METODOS_CANCELACION,

            frame : true,

            renderTo: Ext.get('formulario'),

            bodyStyle : 'padding:5px 5px 0',
            
            bodyStyle:'background: white',
            
            labelAlign:'right',

            width : 350,

            waitMsgTarget : true,

            defaults : {

                width : 320

            },

            defaultType : 'textfield',

            //se definen los campos del formulario

            items : [
            
                new Ext.form.TextField( {
                id:'agrMetCancelTxtClav',
				fieldLabel: getLabelFromMap('agrMetCancelTxtClav',helpMap,'Clave'),
				tooltip: getToolTipFromMap('agrMetCancelTxtClav',helpMap,'Clave del M&eacute;todo de Cancelaci&oacute;n'),
                hasHelpIcon:getHelpIconFromMap('agrMetCancelTxtClav',helpMap),								 
                Ayuda: getHelpTextFromMap('agrMetCancelTxtClav',helpMap),
                name : 'cdMetodo',
                disabled : true,
                allowBlank : false,
		        width: 320
            }),
            
            new Ext.form.TextField( {
                id:'agrMetCancelTxtDesc',
				fieldLabel: getLabelFromMap('agrMetCancelTxtDesc',helpMap,'Descripci&oacute;n'),
				tooltip: getToolTipFromMap('agrMetCancelTxtDesc',helpMap,'Descripci&oacute;n del M&eacute;todo de Cancelaci&oacute;n'),
                hasHelpIcon:getHelpIconFromMap('agrMetCancelTxtDesc',helpMap),								 
                Ayuda: getHelpTextFromMap('agrMetCancelTxtDesc',helpMap),
                name : 'dsMetodo',
                allowBlank : false,
				maxLength: 30,
				maxLengthText: 'Solo se aceptan hasta 30 caracteres',
		        width: 320
            })
            
            ]

        });


//Windows donde se van a visualizar la pantalla

        var window = new Ext.Window({
        	id:'wndwMetCancAgreId',
        //	title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('101',helpMap,'Agregar M&eacute;todo de Cancelaci&oacute;n')+'</span>',
            title: getLabelFromMap('wndwMetCancAgreId', helpMap,'Agregar M&eacute;todo de Cancelaci&oacute;n'),        	
        	width: 500,
        	height:180,
        	layout: 'fit',
        	modal: true,
        	plain:true,
        	bodyStyle:'padding:5px;',
        	buttonAlign:'center',
        	items: formPanel,
            //se definen los botones del formulario
            buttons : [ {

				text:getLabelFromMap('agrMetCancelBtnSave',helpMap,'Guardar'),
				tooltip: getToolTipFromMap('agrMetCancelBtnSave',helpMap,'Guarda un nuevo M&eacute;todo de Cancelaci&oacute;n'),

                disabled : false,

                handler : function() {

                    if (formPanel.form.isValid()) {

                        formPanel.form.submit( {

                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_INSERTAR_GUARDAR_METODOS_CANCELACION,

                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0], function(){reloadGrid();});
                                window.close();
                            },

                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0]);
                            },

                            //mensaje a mostrar mientras se guardan los datos
                           // waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
                            waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')

                        });

                    } else if (!formPanel.form.isValid() && Ext.getCmp('agrMetCancelTxtDesc').getValue().length > 30) {

                    	Ext.Msg.alert(getLabelFromMap('400011', helpMap,'Aviso'), getLabelFromMap('400012', helpMap,'S&oacute;lo se aceptan hasta 30 caracteres'));
                    	
                    }
                    else {

                        Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));

                    }

                }

            }, {
				
				text:getLabelFromMap('agrMetCancelBtnCanc',helpMap,'Cancelar'),
				tooltip: getToolTipFromMap('agrMetCancelBtnCanc',helpMap,'Cancela Operaci&oacute;n de agregar un M&eacute;todo de Cancelaci&oacute;n'),

                handler : function() {
                    window.close();
                }

            }]

    	});

    	window.show();
};