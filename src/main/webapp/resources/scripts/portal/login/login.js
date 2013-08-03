Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";

	var _URL_VALIDA_USUARIO = _CONTEXT + '/autenticaUsuario.action';
	
	var dsUser = new Ext.form.TextField({
		id:'user',
		fieldLabel: 'Usuario', 
		tooltip: 'Nombre de Usuario',
		name:'user',
		mask:'*',
		allowBlank:false
	});
	
	var dsPassword = new Ext.form.TextField({
	    id:'password',   
	    fieldLabel: 'Contraseña', 
	    tooltip: 'Contraseña',
	    name:'password',
	    allowBlank:false,
	    inputType: 'password'
	});
	  
	var loginForm = new Ext.form.FormPanel({
	    el:'formLogin',
	    id: 'loginForm',
	    url: _URL_VALIDA_USUARIO,
	    iconCls:'logo',
	    bodyStyle:'background:white',
	    labelAlign: 'top',
	    frame:true,
	    labelWidth: 150,
	    width: 170,
	    height:200,
	    autoHeight: true,
	    items: [{
	    	layout:'form',
			border: false,
			items:[{
				bodyStyle:'background: white',			        
	            layout: 'form',
				frame:true,
				align:'rigth',
			    baseCls: '',			       
			    buttonAlign: "center",
			    items: [{
		    		//html:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
	    		    layout:'column',
		 			border:false,
		 			columnWidth: 1,
	    		    items:[{
	    		    	columnWidth:1,
	            		layout: 'form',
	            		align:'rigth',
		                border: false,
	    		        items:[
	    		        	dsUser,
	    		        	dsPassword
	    		        ]		
					}]
	            }]
			}]
		}],
		buttons: [{
			text: 'Entrar',
	        handler: function() {
	        	if (loginForm.form.isValid()) {
	        		loginForm.form.submit({
			        	waitMsg:'Procesando...',
			        	failure: function(form, action) {
							Ext.MessageBox.alert('Error', action.result.actionErrors[0]);
						},
						success: function(form, action) {
							alert("INGRESO!");	    	
						}
					});
				} else {
					Ext.MessageBox.alert('Error', 'Por favor revise los datos requeridos.!');
				}
			}
		}]
	});
			
	loginForm.render();
	
	function toggleDetails(btn, pressed) {
	    var view = grid.getView();
	    view.showPreview = pressed;
	    view.refresh();
	}
	//var store;    
});