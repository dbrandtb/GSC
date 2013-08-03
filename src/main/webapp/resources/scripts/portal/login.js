/*
 * AON
 * 
 * Creado el 24/01/2008 11:33:52 a.m. (dd/mm/aaaa hh:mm:ss)
 *
 * Copyright (C) Biosnet S.C.    
 * All rights reserved. Todos los derechos reservados.
 *
 * http://www.biosnetmx.com/
 */



/**
 * 
 * Carga de componentes ExtJs en la pantalla index.jsp
 */
Ext.onReady(function(){

    var text = new Ext.Panel({
        renderTo: Ext.get('message'),
        autoLoad: _CONTEXT+'/resources/static/login.html',
        border: false,
        autoHeight: true ,
        autoScroll: true,
        autoWidth: true,
        baseCls: null
    });
    
    var fmLogin = new Ext.FormPanel({
        labelWidth: 60,
        url: _ACTION,
        frame: true,
		renderTo: Ext.get('cuerpo'),
        bodyStyle:'padding:5px 5px 0',
        width: 250,
        defaults: {width: 150},
        defaultType: 'textfield',

        items: [userLb = {
                fieldLabel: 'User',
                name: 'user',
                id: 'user',
                allowBlank: false
            },passwordLb = {
                fieldLabel: 'Password',
                name: 'password',
                id: 'password',
                allowBlank: false,
                inputType: 'password'
            }
        ],

        buttons: [{
            text: 'Login',
            id: 'botonLogin',
            handler: function(){
				fmLogin.getForm().submit({waitMsg:'Validating...'});
			}
        }]
    });

	fmLogin.on("actioncomplete", function(t, action) {
		window.location.replace(_REDIRECT);
	});
	
	fmLogin.on("actionfailed", function(t, action) {
		//Ext.MessageBox.alert('Error Message', action.result.errorMessages[0]);
		Ext.get('messageError').dom.innerHTML = action.result.errorMessages[0];
		Ext.get('user').dom.value="";
		Ext.get('password').dom.value="";
	    Ext.get('user').dom.focus();
	});

	Ext.get('user').on('keydown', function(e){
		var k = null;
        (e.keyCode) ? k=e.keyCode : k=e.which;
        if(k==13){
			Ext.get('password').dom.focus();
        }
	});
	
	Ext.get('password').on('keydown', function(e){
		var k = null;
        (e.keyCode) ? k=e.keyCode : k=e.which;
        if(k==13){
			fmLogin.getForm().submit({waitMsg:'Validating...'});
        }
	});
    
	Ext.get('user').dom.focus();

});