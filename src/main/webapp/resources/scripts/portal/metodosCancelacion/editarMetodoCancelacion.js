// Funcion de Agregar

function editar(key) {



        //se define la forma en que se van a leer los datos que envie la action
        var _jsonFormReader = new Ext.data.JsonReader( {

            //indica el arreglo a leer
            root : 'MMetodoCancelacionList',

            //indica la cantidad de registro que debe leerse
            totalProperty: 'total',

            //indica el resultado de la respuesta de la action
            successProperty : 'success'

        }, [ {

            name : 'cdMetodo',

            mapping : 'cdMetodo',

            type : 'string'


        }, {

            name : 'dsMetodo',

            type : 'string',

            mapping : 'dsMetodo'

        }]);

		//se define el formulario
        var formPanel = new Ext.FormPanel( {

            labelWidth : 90,

			//action a invocar al hacer al cargar(load) del formulario
            url : _ACTION_GET_METODO_CANCELACION,

            frame : true,

            baseParams : {cdMetodo: key},

            bodyStyle : 'padding:5px 5px 0',
            
            bodyStyle:'background: white',

            width : 350,
            
            labelAlign:'right',

            waitMsgTarget : true,

			//se setea el reader que va a usar el form cuando se cargue
            reader : _jsonFormReader,

            //reader : test(),
            defaults : {

                width : 330

            },

   			//se definen los campos del formulario
            
            defaultType : 'textfield',
            items : [ {
                id:'editMetCancelTxtCod',
				fieldLabel: getLabelFromMap('editMetCancelTxtCod',helpMap,'Codigo'),
				tooltip: getToolTipFromMap('editMetCancelTxtCod',helpMap,'Codigo del M&eacute;todo de Cancelaci&oacute;n'),
                hasHelpIcon:getHelpIconFromMap('editMetCancelTxtCod',helpMap),								 
                Ayuda: getHelpTextFromMap('editMetCancelTxtCod',helpMap),

                name : 'cdMetodo',

                disabled : true,

                allowBlank : false,
                                
                width: 330
                
                //anchor: '90%'

            }, new Ext.form.TextField( {
                id:'editMetCancelTxtDesc',
				fieldLabel: getLabelFromMap('editMetCancelTxtDesc',helpMap,'Descripci&oacute;n'),
				tooltip: getToolTipFromMap('editMetCancelTxtDesc',helpMap,'Descripci&oacute;n del M&eacute;todo de Cancelaci&oacute;n'),
                hasHelpIcon:getHelpIconFromMap('editMetCancelTxtDesc',helpMap),								 
                Ayuda: getHelpTextFromMap('editMetCancelTxtDesc',helpMap),
                name : 'dsMetodo',
                allowBlank : false,
				maxLength: 30,
				maxLengthText: 'Solo se aceptan hasta 30 caracteres',
                width: 330
            })]

        });



//Windows donde se van a visualizar la pantalla
    
        var win = new Ext.Window({
        	title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('wndwMetCancEdId',helpMap,'Editar M&eacute;todo de Cancelaci&oacute;n')+'</span>',
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

				text:getLabelFromMap('editMetCancelBtnSave',helpMap,'Guardar'),
				tooltip: getToolTipFromMap('editMetCancelBtnSave',helpMap,'Guardar un M&eacute;todo de Cancelaci&oacute;n'),

                text : 'Guardar',

                disabled : false,

                handler : function() {

                    if (formPanel.form.isValid()) {

                        formPanel.form.submit( {

                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_INSERTAR_GUARDAR_METODOS_CANCELACION,

                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0], function(){reloadGrid();});
                                win.close();                                     
                            },

                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0]);
                            },

                            //mensaje a mostrar mientras se guardan los datos
                          //  waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
                            waitMsg : getLabelFromMap('400022', helpMap,'Guardando Actualizacion de datos...')

                        });

                    } else if (!formPanel.form.isValid() && Ext.getCmp('editMetCancelTxtDesc').getValue().length > 30) {

                    	Ext.Msg.alert(getLabelFromMap('400111', helpMap,'Aviso'), getLabelFromMap('400012', helpMap,'S&oacute;lo se aceptan hasta 30 caracteres'));
                    	
                    }
                    else {

                    	Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));

                    }

                }

            }, {

				text:getLabelFromMap('editMetCancelBtnCanc',helpMap,'Cancelar'),
				tooltip: getToolTipFromMap('editMetCancelBtnCanc',helpMap,'Cancela la Operaci&oacute;n de M&eacute;todo de Cancelaci&oacute;n'),

                handler : function() {

                    win.close();
                }

            }/*, {

				text:getLabelFromMap('editMetCancelBtnPNDP',helpMap,'Prima no Devengada Pagada'),

				tooltip: getToolTipFromMap('editMetCancelBtnPNDP',helpMap,'Prima Pagada'),

                handler : function() {

                	window.location = _ACTION_IR_FORMULA_METODO_CANCELACION + "?cdMetodo=" + key + "&cdTipoPrima=1";

                }

            }, {

				text:getLabelFromMap('editMetCancelBtnPNDT',helpMap,'Prima no Devengada Total'),

				tooltip: getToolTipFromMap('editMetCancelBtnPNDT',helpMap,'Prima Total'),

                handler : function() {
                	window.location = _ACTION_IR_FORMULA_METODO_CANCELACION + "?cdMetodo=" + key + "&cdTipoPrima=2";
                }

            }*/
            
            ]

    	});


    	win.show();
        //se carga el formulario con los datos de la estructura a editar
        formPanel.form.load( {
                       /*     waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
                            waitMsg : getLabelFromMap('400070', helpMap,'Cargando datos...')*/
        });

    };
