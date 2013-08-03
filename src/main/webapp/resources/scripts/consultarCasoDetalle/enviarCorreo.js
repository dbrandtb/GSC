
//function enviarCorreo(_msg, numeroCaso, _tipoEnvio/*, _params*/) {
function enviarCorreo(_numeroCaso, _cdproceso) {
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
              	new Ext.form.Hidden ({
              		name: 'numeroCaso',
              		id: 'numeroCaso',
              		value: _numeroCaso              		
              	}),
              	new Ext.form.Hidden ({
              		name: 'cdproceso',
              		id: 'cdprocesoId',
              		value: _cdproceso
              	}),
                new Ext.form.TextField( {
                id: 'addressId', 
				fieldLabel: getLabelFromMap('correoElectronicoId',helpMap,'Direcci&oacute;n de correo electr&oacute;nico'),
				tooltip: getToolTipFromMap('correoElectronicoId',helpMap,'Direcci&oacute;n de correo electr&oacute;nico'),
                name : 'correo',
                //value: _msg,
                vtype:'email',
                emailText:'Este campo deber&iacute;a ser una direcci&oacute;n email con el formato usuario@dominio.com'
                }),

                new Ext.form.Checkbox( {
                id: 'sendOptionId', 
				fieldLabel: getLabelFromMap('enviarCorreoElectronicoId',helpMap,'Enviar a mi email registrado'),
				tooltip: getToolTipFromMap('enviarCorreoElectronicoId',helpMap),
                 name : 'enviar'
                })

              
                    
            ]});

            
            function validaDatos(){
            	if((formPanel.findById("addressId").getValue()=="")&&(formPanel.findById("sendOptionId").getValue()==false)){
            		return false;
            	}else{
              	       return true;
            	     }            	
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
				tooltip: getToolTipFromMap('windowEnvCorreoElectronicoButtonsEnviarId', helpMap), 
                disabled : false,
                handler : function() {
                	if(formPanel.form.isValid()){
	                    if (validaDatos()) {
	                    
	                    	sendMail();
	                    }
	                    else{
	                    	Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400094', helpMap,'Introduzca una direcci&oacute;n o marque el check box.'));
	                    	//Ext.MessageBox.alert("Introduzca una direcci&oacute;n o marque el check box.");
	                    }
                    }else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));                        
                    }
                }
            }, {
                id: 'windowEnvCorreoElectronicoButtonsEnviarId',
                text: getLabelFromMap('windowEnvCorreoElectronicoButtonsCancelarId', helpMap,'Cancelar'),                
				tooltip: getToolTipFromMap('windowEnvCorreoElectronicoButtonsCancelarId', helpMap),    
                handler : function() {
                    window.close();
                }
            }]
    	});
          
	window.show();
	
	
	function sendMail(){	
		var _params = "";	
		_params += "&option="+Ext.getCmp("sendOptionId").getValue();
		_params += "&correo="+Ext.getCmp("addressId").getValue();
		_params += "&numeroCaso="+_numeroCaso;
		_params += "&cdProceso="+_cdproceso;			
		
		startMask(formPanel.id,"Enviando e-mail...");
		execConnection(_ACTION_ENVIAR_CORREO, _params, cbkEnviar);
	}
	
	function cbkEnviar (_success, _message) {
		endMask();
			
			if (!_success) {				

					Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message);
			
			}else {
					Ext.Msg.alert('Aviso', _message, function(){window.close();});				
				
			}
	}	
	
	
}

