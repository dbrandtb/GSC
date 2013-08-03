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
            layout: 'table',
            layoutConfig: {columns: 1},
            items: [
            	{
            		html: '<b>Login succeeded...</b>'
            	}
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
				{text: 'Login'},
				{text: 'Cancelar', handler: function(){el_formpanel.form.reset();}}
			]

    	});

    	wnd.show();
	
});