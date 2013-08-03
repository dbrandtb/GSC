// Funcion de Agregar  Plan

agregar = function() {

        //se define el formulario
        var formPanel = new Ext.FormPanel( {

            labelWidth : 100,
            url : _ACTION_GUARDAR_NUEVO_ESTRUCTURA,

            frame : true,

            renderTo: Ext.get('formulario'),

            title : 'Crear Estructura',

			bodyStyle:'background: white',
			
            //bodyStyle : 'padding:5px 5px 0',

            width : 350,
            
             bodyStyle:'background: white',

            waitMsgTarget : true,

            defaults : {

                width : 230

            },

            defaultType : 'textfield',

            //se definen los campos del formulario

            items : [
               new Ext.form.TextField( {

                fieldLabel : 'Descripcion' ,

                name : 'descripcion',

                allowBlank : false,

                width: 200

            })]

        });


//Windows donde se van a visualizar la pantalla

        var window = new Ext.Window({
        	title: 'Crear Estructura',
        	width: 500,
        	height:150,
        	minWidth: 300,
        	modal: true,
        	minHeight: 100,
        	bodyStyle:'background: white',
        	frame:true,
        	layout: 'fit',
        	plain:true,
        	bodyStyle:'padding:5px;',
        	buttonAlign:'center',
        	items: formPanel,
            //se definen los botones del formulario
            buttons : [ {

                text : 'Guardar',

                disabled : false,

                handler : function() {

                    if (formPanel.form.isValid()) {

                        formPanel.form.submit( {

                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_GUARDAR_NUEVO_ESTRUCTURA,

                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert('Aviso','Guardado satisfactoriamente');
                                window.close();
                            },

                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.MessageBox.alert('Aviso','Problemas al Guardar');
                                window.close();
                            },

                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : 'guardando datos ...'

                        });

                    } else {

                        Ext.Msg.alert('Informacion', 'Por favor complete la informacion requerida!');

                    }

                }

            }, {

                text : 'Cancelar',

                handler : function() {
                    window.close();
                }

            }]

    	});

    	window.show();
};