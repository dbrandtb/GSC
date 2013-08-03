Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";
	var el_formpanel = new Ext.FormPanel ({
			el: "formBusqueda",
            frame : true,
            header: false,
            autoWidth : true,
            autoHeight: true,
            labelWidth: 70,
            url: _ACTION_LOGIN,
			onSubmit: Ext.emptyFn,
			submit: function () {
				this.getEl().dom.setAttribute("action", _ACTION_LOGIN);
				this.getEl().dom.submit();
			},
            items: [
            	new Ext.form.TextField({
            			fieldLabel: 'Usuario',
            			name: 'user',
                        value: 'SLIZARDI',
            			id: 'user',
            			maxLength: 15
            	}),
            	new Ext.form.TextField({
            			fieldLabel: 'Password',
            			name: 'pwd',
                        value: 'SLIZARDI',
            			id: 'pwd',
            			inputType: 'password'
            	}),
                new Ext.form.TextField({
                        fieldLabel: 'cdElemento',
                        name: 'cdElemento',
                        id: 'cdElemento'
            	})

			]
	});

	var wnd = new Ext.Window({         	
        	title: '<center>Login</center>',
        	width: 300,
        	autoHeight:true,
        	plain:true,
        	bodyStyle:'padding:5px;',
        	buttonAlign:'center',
        	closable: false,
        	items: el_formpanel,
            //se definen los botones del formulario
			buttons: [
				{
				 text: 'Login', 
				 handler: function() {
							el_formpanel.getForm().submit({waitMsg:'Validating...'});
						}
				},
				{text: 'Cancelar', handler: function(){el_formpanel.form.reset();}}
			]

    	});
    	wnd.on('activate', function () {
				Ext.getCmp('user').focus(true, 100);
		});

    	wnd.show();
});