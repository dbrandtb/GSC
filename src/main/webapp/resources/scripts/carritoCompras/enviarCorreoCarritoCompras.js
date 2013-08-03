var helpMap = new Map();
function enviarCorreo(_msg, grid2) {

/*Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";
*/
	//se define el formulario
        var formPanel = new Ext.FormPanel ( {

        	labelWidth : 200,

			//action a invocar al hacer al cargar(load) del formulario
           url : _ACTION_ENVIAR_CORREO,


            bodyStyle : 'padding:5px 5px 0',
            
            bodyStyle:'background: white',

            width : 350,
            defaults : {width : 200 },

			//se definen los campos del formulario
            items : [
               
               /* new Ext.form.Hidden( {
                name : 'cdCliente',
                value: record.get('cdCliente')
                }),*/
              
                new Ext.form.TextField( {
                id: 'correoElectronicoId', 
				fieldLabel: getLabelFromMap('correoElectronicoId',helpMap,'Direcci&oacute;n de correo electr&oacute;nico'),
				tooltip: getToolTipFromMap('correoElectronicoId',helpMap,'Direcci&oacute;n de correo electr&oacute;nico'),
                hasHelpIcon:getHelpIconFromMap('correoElectronicoId',helpMap),
				Ayuda:getHelpTextFromMap('correoElectronicoId',helpMap),
                name : 'correo',
                value: _msg
                }),

                new Ext.form.Checkbox( {
                id: 'enviarCorreoElectronicoId', 
				fieldLabel: getLabelFromMap('enviarCorreoElectronicoId',helpMap,'Enviar a mi email registrado'),
				tooltip: getToolTipFromMap('enviarCorreoElectronicoId',helpMap,'Enviar a mi email registrado'),
                hasHelpIcon:getHelpIconFromMap('enviarCorreoElectronicoId',helpMap),
				Ayuda:getHelpTextFromMap('enviarCorreoElectronicoId',helpMap),
                name : 'enviar'
                })

              
                    
            ]});

            
            function validaDatos(){
            /*if ((formPanel.findById().getValue()=="")&&(formPanel.findById().getValue()==false)){
            		return 0;
            	}else{
              	       return 1;
            	     }*/
            	return true;
            	
            }
//Windows donde se van a visualizar la pantalla
    
        var window = new Ext.Window({
            id:'windowEnvCorreoElectronicoId',
        	title: '<span style="color:black;font-size:14px;">'+getLabelFromMap('windowEnvCorreoElectronicoId', helpMap,'Enviar Correo Electr&oacute;nico')+'</span>',
        	//title: 'Enviar Correo Electronico',
        	width: 500,
        	height:150,
        	minWidth: 300,
        	minHeight: 100,
        	modal:true,
        	layout: 'fit',
        	plain:true,
        	bodyStyle:'padding:5px;',
        	bodyStyle:'background: white',
        	buttonAlign:'center',
        	items: formPanel,
            //se definen los botones del formulario
            buttons : [ {
				id: 'windowEnvCorreoElectronicoButtonsEnviarId', 
				text: getLabelFromMap('windowEnvCorreoElectronicoButtonsEnviarId', helpMap,'Enviar Correo'),                
				tooltip: getToolTipFromMap('windowEnvCorreoElectronicoButtonsEnviarId', helpMap,'Enviar Correo'), 

                //text : 'Enviar Correo',

                disabled : false,

                handler : function() {

                    if (validaDatos()) {

                        formPanel.form.submit( {

                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_ENVIAR_CORREO,

                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),action.result.actionMessages[0], function() {
                                		
                                });
                                window.close();                                     
                            },

                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                            Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'),'Problemas al Guardar: ' + action.result.errorMessages[0]);
                           },

                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')
                            

                        });

                    } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));                        
                    }
                }
            }, {
                id: 'windowEnvCorreoElectronicoButtonsCancelarId',
                text: getLabelFromMap('windowEnvCorreoElectronicoButtonsCancelarId',helpMap,'Cancelar'),                
				tooltip: getToolTipFromMap('windowEnvCorreoElectronicoButtonsCancelarId',helpMap,'Cancelar'), 
                                          
                handler : function() {
                    window.close();
                    //reloadGrid();
                }
                 
                                              

            }]

    	});

          
	window.show();
}
//});

