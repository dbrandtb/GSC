/*
 * Ext JS Library 2.2
 * Copyright(c) 2006-2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */


Ext.onReady(function(){

    Ext.QuickTips.init();
    
    var msg = function(title, msg){
        Ext.Msg.show({
            title: title, 
            msg: msg,
            minWidth: 200,
            modal: true,
            icon: Ext.Msg.INFO,
            buttons: Ext.Msg.OK
        });
    };
    
    var upload_Reader = new Ext.data.JsonReader({
						root: 'upload',
						totalProperty: 'totalCount',
						successProperty: '@success'
						}
	);
    /*var fibasic = new Ext.form.FileUploadField({
        renderTo: 'fi-basic',
        width: 400
    });
 
    new Ext.Button({
        text: 'Get File Path',
        renderTo: 'fi-basic-btn',
        handler: function(){
            var v = fibasic.getValue();
            msg('Selected File', v && v != '' ? v : 'None');
        }
    });

    var fbutton = new Ext.form.FileUploadField({
        renderTo: 'fi-button',
        buttonOnly: true,
        listeners: {
            'fileselected': function(fb, v){
                var el = Ext.fly('fi-button-msg');
                el.update('<b>Selected:</b> '+v);
                if(!el.isVisible()){
                    el.slideIn('t', {
                        duration: .2,
                        easing: 'easeIn',
                        callback: function(){
                            el.highlight();
                        }
                    });
                }else{
                    el.highlight();
                }
            }
        }
    });*/
    
    var fp = new Ext.FormPanel({
        renderTo: 'div_form',
        fileUpload: true,
        width: 500,
        frame: true,
        title: 'Subir Archivo',
        autoHeight: true,
        successProperty: 'success',
        reader: upload_Reader,
        //bodyStyle: 'padding: 10px 10px 0 10px;',
        labelWidth: 50,
        defaults: {
            anchor: '85%',
            allowBlank: false,
            msgTarget: 'side'
        },
        items: [{
            xtype: 'textfield',
            fieldLabel: 'fileCaption',
            id: 'fileCaption',
            name: 'fileCaption'
        },{
            xtype: 'fileuploadfield',
            id: 'uploadFileName',
            emptyText: 'Select an image',
            fieldLabel: 'Photo',
            name: 'uploadFileName',
            buttonCfg: {
                text: ''//,
                //iconCls: 'upload-icon'
            }
        }],
        buttons: [{
            text: 'Guardar',
            handler: function(){
                if(fp.form.isValid()){
	                fp.form.submit({
	                    url: _ACTION_UPLOAD_FILE,
	                    waitMsg: 'Uploading ...',
	                    success: function(fp, o){
	                        msg('Success', 'Processed file "'+o.result.file+'" on the server');
	                    },
	                    failure: function (){
	                    	alert("Error");
	                    }
	                });
                }
            }
        },{
            text: 'Cancelar',
            handler: function(){
                fp.getForm().reset();
            }
        }]
    });
    
});