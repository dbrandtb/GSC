/**
*  Muestra una ventana para que se escriba el password,
* si el password es correcto se ejecutara la funcion enviada como parametro
*
*	@params
*			funcionAlConfirmar: función ejecutada cuando el password es correcto
*
*	@return
*
*/
//TODO: Actualmente funcionAlConfirmar solo puede ser una funcion sin parametros, mejorar ese aspecto.
function confirmaPassword(funcionAlConfirmar){
	
	var _URL_CONFIRMA_PASSWORD = _CONTEXT + '/procesosCriticos/confirmaPassword.action'; 
		
	//var tmpMensaje = new Ext.Template(
    	//"<span class='x-form-item' style='background-color:#e4e4e4;font-size:14px;font:Arial,Helvetica, Sans-serif;'font-weight:bold''></span>"     
	//);
	
	var formPasswd = new Ext.form.FormPanel({
		id:'recarga-forma-window',
		url: _URL_CONFIRMA_PASSWORD,
		border:false,
		frame:true,
		autoScroll:true,
		//bodyStyle:'background:white',
		method:'post',
		width: 500,
		title: "<span class='x-form-item'>Para proceder con la operaci&oacute;n seleccionada es necesario que escriba nuevamente su contrase&ntilde;a.</span><br/>", 
		buttonAlign: "center",
		baseCls:'x-plain',
		labelWidth:75,
		items:[
			//tmpMensaje,
			{
				xtype:'textfield',
				id: 'txtPasswdId',
				name: 'password',
				inputType: 'password',
				allowBlank: false,
				fieldLabel: 'Contrase&ntilde;a',
				width: 220
			}
		]
	});
			
	var windowConfirmaPasswd = new Ext.Window({
		title : 'Confirmaci&oacute;n de contrase&ntilde;a',
		width : 500,
		height : 150,
		layout : 'fit',
		//plain : true,
		modal : true,
		bodyStyle : 'padding:5px;',
		resizable: false,
		buttonAlign : 'center',
		items: [
			formPasswd
		],
		closable : true,
		buttons : [{
			text : 'Aceptar',
			handler : function() {
				if (formPasswd.form.isValid()) {
					formPasswd.form.submit({
						waitTitle : 'Espere',
						waitMsg : 'Procesando...',
						failure : function(form, action) {
							var mensajeRespuesta = Ext.util.JSON.decode(action.response.responseText).mensajeRespuesta;
							Ext.MessageBox.alert('Error', Ext.isEmpty(mensajeRespuesta) ? 'La contrase&ntilde;a es incorrecta.' : mensajeRespuesta);
							windowConfirmaPasswd.close();
						},
						success : function(form, action) {
							windowConfirmaPasswd.close();
							//Se ejecuta la funcion enviada como parametro:
							funcionAlConfirmar();
						}
					});
				} else {
					Ext.MessageBox.alert('Error', 'Este campo es requerido');
				}
			}
		}, {
			text : 'Cancelar',
			handler : function() {
				windowConfirmaPasswd.close();
			}
		}]
	});
	windowConfirmaPasswd.show();
}
