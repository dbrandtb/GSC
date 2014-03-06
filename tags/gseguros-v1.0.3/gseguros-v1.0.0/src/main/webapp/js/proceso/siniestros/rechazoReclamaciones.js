Ext.require([ 'Ext.form.*', 'Ext.data.*', 'Ext.chart.*', 'Ext.grid.*', 'Ext.grid.Panel','Ext.layout.container.Column', 'Ext.selection.CheckboxModel' ]);

Ext.onReady(function() {


	Ext.define('modeloRechazos',{
        extend: 'Ext.data.Model',
        fields: [{type:'string',    name:'key'},
                 {type:'string',    name:'value'},
				]
    });
	
	 var storeRechazos = Ext.create('Ext.data.JsonStore', {
	    	model:'modeloRechazos',
	        proxy: {
	            type: 'ajax',
	            url: _URL_ListaRechazos,
	            reader: {
	                type: 'json',
	                root: 'loadList'
	            }
	        }
	    });
	 storeRechazos.load();

	 Ext.define('modeloIncisosRechazos',{
		 extend: 'Ext.data.Model',
		 fields: [{type:'string',    name:'key'},
		          {type:'string',    name:'value'},
		          ]
	 });
	 
	 var storeIncisosRechazos = Ext.create('Ext.data.JsonStore', {
		 model:'modeloIncisosRechazos',
		 proxy: {
			 type: 'ajax',
			 url: _URL_ListaIncisosRechazos,
			 reader: {
				 type: 'json',
				 root: 'loadList'
			 }
		 }
	 });
	
	
	
    motivoRechazo= Ext.create('Ext.form.ComboBox',
    	    {
    	        id:'motivoRechazo',
    	        name:'smap1.cdmotivo',
    	        fieldLabel: 'Motivo',
    	        store: storeRechazos,
    	        queryMode:'local',
    	        displayField: 'value',
    	        valueField: 'key',
    	        allowBlank:false,
    	        blankText:'El motivo es un dato requerido',
    	        editable:false,
    	        labelWidth : 150,
    	        width: 600,
    	        emptyText:'Seleccione ...',
    	        listeners: {
    	        	select: function(combo, records, eOpts){
    	        		
    	        		storeIncisosRechazos.load({
    	        			 params: {
    	        				'params.pv_cdmotivo_i' : records[0].get('key')
    	        			 }
    	        		 });
    	        	}
    	        }
    	    });

    
    textoRechazo = Ext.create('Ext.form.field.TextArea', {
        	fieldLabel: 'Descripci&oacute;n modificado'
    		,labelWidth: 150
    		,width: 600
    		,name:'smap1.comments'
			,height: 250
			,allowBlank: false
			,blankText:'La descripci&oacute;n es un dato requerido'
        });
    
    incisosRechazo= Ext.create('Ext.form.ComboBox',
    		{
    	id:'incisosRechazo',
    	name:'smap1.incisosRechazo',
    	fieldLabel: 'Incisos Rechazo',
    	store: storeIncisosRechazos,
    	queryMode:'local',
    	displayField: 'value',
    	valueField: 'key',
//    	allowBlank:false,
    	blankText:'El motivo es un dato requerido',
    	editable:false,
    	labelWidth : 150,
    	width: 600,
    	emptyText:'Seleccione ...',
    		listeners: {
	        	select: function(combo, records, eOpts){
	        		
	        		textoRechazo.setValue(records[0].get('value'));
	        	}
	        }
    });
 
    
    panelRechazarReclamaciones= Ext.create('Ext.form.Panel', {
        title: 'Rechazar Reclamaci&oacute;n',
    	id: 'panelRechazarReclamaciones',
        width: 650,
        url: _URL_ActualizaStatusTramite,
        bodyPadding: 5,
        renderTo: 'maindiv',
        items: [
            	motivoRechazo,incisosRechazo,textoRechazo
    	        ],
	        buttonAlign:'center',
	        buttons: [{
    		text: 'Guardar'
    		,icon:_CONTEXT+'/resources/fam3icons/icons/accept.png'
    		,buttonAlign : 'center',
    		handler: function() {
    	    	if (panelRechazarReclamaciones.form.isValid()) {
    	    		panelRechazarReclamaciones.form.submit({
    		        	waitMsg:'Procesando...',			
    		        	params: {
    		        		'smap1.ntramite' : _nmTramite, 
    		        		'smap1.status'   : 4,
    		        	},
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
    	   	                    msg: "Se ha rechazado correctamente",
    	   	                    buttons: Ext.Msg.OK
    	   	                });
    						loadMcdinStore();
//    						panelRechazarReclamaciones.form.reset();
    						windowLoader.close();
    						
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
    	        windowLoader.close();
    	        //asginarUsuario.show();
    	    }
    	}
    	]
    });
	
});