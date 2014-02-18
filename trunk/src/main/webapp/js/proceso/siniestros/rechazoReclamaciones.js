Ext.require([ 'Ext.form.*', 'Ext.data.*', 'Ext.chart.*', 'Ext.grid.*', 'Ext.grid.Panel','Ext.layout.container.Column', 'Ext.selection.CheckboxModel' ]);

Ext.onReady(function() {


    motivoRechazo= Ext.create('Ext.form.ComboBox',
    	    {
    	        id:'motivoRechazo',
    	        name:'params.motivoRechazo',
    	        fieldLabel: 'Motivo',
    	        //store: storeCirculoHospitalario,
    	        queryMode:'local',
    	        displayField: 'value',
    	        valueField: 'key',
    	        allowBlank:false,
    	        blankText:'El motivo es un dato requerido',
    	        editable:false,
    	        labelWidth : 150,
    	        width: 600,
    	        emptyText:'Seleccione un Motivo ...'
    	    });
 
    
    panelRechazarReclamaciones= Ext.create('Ext.form.Panel', {
        title: 'Rechazar Reclamaci&oacute;n',
    	id: 'panelRechazarReclamaciones',
        width: 650,
        //url: _URL_INSERTA_CLAUSU,
        bodyPadding: 5,
        renderTo: 'maindiv',
        items: [
            	motivoRechazo,
    	        {
    	            xtype: 'textarea'
	            	,fieldLabel: 'Descripci&oacute;n modificado'
            		,labelWidth: 150
            		,width: 600
            		,name:'params.descripcion'
        			,height: 250
        			,allowBlank: false
        			,blankText:'La descripci&oacute;n es un dato requerido'
    	        }],
	        buttonAlign:'center',
	        buttons: [{
    		text: 'Guardar'
    		,icon:_CONTEXT+'/resources/fam3icons/icons/accept.png'
    		,buttonAlign : 'center',
    		handler: function() {
    	    	if (panelRechazarReclamaciones.form.isValid()) {
    	    		panelRechazarReclamaciones.form.submit({
    		        	waitMsg:'Procesando...',			        	
    		        	failure: function(form, action) {
    		        		Ext.Msg.show({
    	   	                    title: 'ERROR',
    	   	                    msg: action.result.errorMessage,
    	   	                    buttons: Ext.Msg.OK,
    	   	                    icon: Ext.Msg.ERROR
    	   	                });
    					},
    					success: function(form, action) {
    						Ext.Msg.show({
    	   	                    title: '&Eacute;XITO',
    	   	                    msg: "La cl&aacute;usula se guardo correctamente",
    	   	                    buttons: Ext.Msg.OK
    	   	                });
    						panelRechazarReclamaciones.form.reset();
    						
    					}
    				});
    			} else {
    				Ext.Msg.show({
    	                   title: 'Aviso',
    	                   msg: 'Complete la informaci&oacute;n requerida',
    	                   buttons: Ext.Msg.OK,
    	                   icon: Ext.Msg.WARNING
    	               });
    			}
    		}
    	},{
    	    text: 'Cancelar',
    	    icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
    	    buttonAlign : 'center',
    	    handler: function() {
    	        rechazarReclamaciones.close();
    	        asginarUsuario.show();
    	    }
    	}
    	]
    });
	
});