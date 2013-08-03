 function insertarOActualizar(record){
		  		
 /* var codigoTipoArchivo = new Ext.form.Hidden({    	
    	id: 'cdtipoarId',
        name:'cdtipoar',
        value: (record)?record.get('cdTipoar'):""
    });	*/
    
    var seccion_reg = new Ext.data.JsonReader({
						root: 'estructuraListArchivo',
						totalProperty: 'totalCount',
						successProperty: '@success'
						}, [
							{name: 'cdtipoar', type: 'string', mapping: 'cdtipoar'},
			        		{name: 'dsarchivo', type: 'string', mapping: 'dsarchivo'},
			        		{name: 'indarchivo', type: 'string', mapping: 'indarchivo'}
 						   ]
		       );
	 	
	 	
	var dsTipoArchivo  = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_TIPO_ARCHIVO}),
						reader: new Ext.data.JsonReader({
								root: 'comboDatosCatalogo',
								id: 'id',
								successProperty: '@success'
							}, [
								{name: 'id', type: 'string', mapping: 'id'},
								{name: 'texto', type: 'string', mapping: 'texto'}
							])
				});	 	
	 	

    		
		/*var codigo = new Ext.form.TextField({
			     fieldLabel: getLabelFromMap('agrConfTipEndTxtCod',helpMap,'Archivo'),
			     tooltip:getToolTipFromMap('agrConfTipEndTxtCod',helpMap,'Archivo'), 
				 id:'descrArchId',
			     name:'dsArchivo',
			     disabled: false,
			     anchor: '100%'
			 });*/
			 
	
		
		/*var comboTipoArchivo = new Ext.form.ComboBox({
	                    tpl: '<tpl for="."><div ext:qtip="{id}.{texto}" class="x-combo-list-item">{texto}</div></tpl>',
	                     id:'comboTipoArchivoId',
	                    store: dsTipoArchivo,
	                    displayField:'texto',
	                    valueField:'id',
	                    hiddenName: 'indarchivo',
	                    typeAhead: true,
	                    mode: 'local',
	                    triggerAction: 'all',
	                    allowBlank: false,
					    fieldLabel: getLabelFromMap('agrConfTipEndCboTA',helpMap,'Tipo de Archivo'),
					    tooltip:getToolTipFromMap('agrConfTipEndCboTA',helpMap,'Tipo de Archivo'), 
	                    forceSelection: true,
	                    width: 130,
	                    selectOnFocus:true,
	                    emptyText:'Seleccione Tipo de Archivo...',
	                    labelSeparator:''
	   });*/
	     
	   
	  var titulo;
     if(record == undefined ){
		titulo='<span style="color:black;font-size:12px;">'+getLabelFromMap('WintipArchAgr', helpMap,'Agregar Tipos de Archivos')+'</span>';
	  }else{
		titulo='<span style="color:black;font-size:12px;">'+getLabelFromMap('WintipArchEdit', helpMap,'Editar Tipos de Archivos')+'</span>';
	  }
		
		var formWindowInsertar = new Ext.FormPanel({
				title: titulo,
 	            iconCls:'logo',
		        bodyStyle:'background: white',
		        buttonAlign: "center",
		        labelAlign: 'right',
		        frame:true,
		        url: _ACTION_GUARDAR_TIPO_ARCHIVOS,
		        width: 550,
		        height:200,
		        layout: 'table',
            	layoutConfig: { columns: 2, columnWidth: .5},
            	items:[{
            		layout: 'form',
           			colspan:2,  
           			labelWidth: 100,          			            		
           			items: [
           				{
           					xtype: 'textfield',
           					fieldLabel: getLabelFromMap('descrArchId',helpMap,'Archivo'),
						    tooltip:getToolTipFromMap('descrArchId',helpMap,'Archivo'),
                            hasHelpIcon:getHelpIconFromMap('descrArchId',helpMap),								 
                            Ayuda: getHelpTextFromMap('descrArchId',helpMap),
							id:'descrArchId',
		                    allowBlank: false,
						    name:'dsArchivo',
						    disabled: false,
						    maxLength:100,
						    //anchor: '100%'
						    width:200
					     }
           				]
            		},
            		{
            		layout: 'form',  
           			labelWidth: 100,  
           			colspan:2,        			            		
           			items: [
           				{
           				   xtype: 'combo',
           				  tpl: '<tpl for="."><div ext:qtip="{id}.{texto}" class="x-combo-list-item">{texto}</div></tpl>',
	                        id:'comboTipoArchivoId',
		                    store: dsTipoArchivo,
		                    displayField:'texto',
		                    valueField:'id',
		                    typeAhead: true,
		                    mode: 'local',
		                    triggerAction: 'all',
		                    allowBlank: false,
						    fieldLabel: getLabelFromMap('comboTipoArchivoId',helpMap,'Tipo de Archivo'),
						    tooltip:getToolTipFromMap('comboTipoArchivoId',helpMap,'Tipo de Archivo'), 
                            hasHelpIcon:getHelpIconFromMap('comboTipoArchivoId',helpMap),								 
                            Ayuda: getHelpTextFromMap('comboTipoArchivoId',helpMap),
						    
		                    forceSelection: true,
		                    width: 130,
		                    selectOnFocus:true,
		                    emptyText:'Seleccione Tipo de Archivo...',
		                    labelSeparator:''
					     }
           				]
            		}
				 ]
		});
  	  
			
		 var window = new Ext.Window({
        	width: 550,
        	height:195,
        	id:'windowId',
        	minWidth: 300,
        	minHeight: 100,
        	modal: true,
        	layout: 'fit',
        	plain:true,
        	bodyStyle:'padding:5px;',
        	buttonAlign:'center',
        	items: formWindowInsertar,
            buttons: [{
                text:getLabelFromMap('agrConfTipEndBtnSave',helpMap,'Guardar'),
                tooltip: getToolTipFromMap('agrConfTipEndBtnSave',helpMap,'Guarda Tipos Archivos'),
                disabled : false,
                handler : function() {
                	if (formWindowInsertar.form.isValid()){
                               guardarDatos();
                             }else{
                    	           Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                                  }
                             
                   /* if (formWindowInsertar.form.isValid()) {
                        formWindowInsertar.form.submit( {
                            url : _ACTION_GUARDAR_TIPO_ARCHIVOS,
                            success : function(from, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400027', helpMap,'Guardando datos...'), function(){reloadGrid();});
                                window.close();
                            },
                            failure : function(form, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'),'Problemas al Insertar : ' + action.result.errorMessages[0]);
                            },
                            waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),	
                            waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')
                        });
                    }else{
                    	Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                    }*/
               }
            },
            {
              text:getLabelFromMap('agrConfTipEndBtnBack',helpMap,'Regresar'),
              tooltip: getToolTipFromMap('agrConfTipEndBtnBack',helpMap,'Regresa a la pantalla anterior'),
              handler : function(){window.close();}
            }]
    	});
    	window.show();
	
	if(record)
		{
			Ext.getCmp("descrArchId").setDisabled(true);
			Ext.getCmp("descrArchId").setValue(record.get("dsTipSup"));			
		}
    	
    dsTipoArchivo.load({
    			params:{cdTabla:'CTIPOARC'},
    			callback:function(){
    						if(record){Ext.getCmp("comboTipoArchivoId").setValue(record.get("indArchivo"));}   					
    					}    			
    		});


// ************************************************************************************************************************
function guardarDatos(){
	//alert(Ext.getCmp("comboTipoArchivoId").getValue()+" - "+Ext.getCmp("descrArchId").getValue()+" - "+Ext.getCmp("cdtipoarId").getValue());		
	 var _params = 
	 		{	 			
	 			indarchivo: Ext.getCmp("comboTipoArchivoId").getValue(),
                dsArchivo: Ext.getCmp("descrArchId").getValue(),
                cdTipoar: record?record.get('cdTipoar'):""
            };
                
	 startMask(formWindowInsertar.id,"Guardando datos...");
	 execConnection(_ACTION_GUARDAR_TIPO_ARCHIVOS, _params, cbkGuardarDatos);		
	}
	
	function cbkGuardarDatos (_success, _message, _response) {
		endMask();
		if (!_success) {
			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
		}else {
			Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){Ext.getCmp('windowId').close();reloadGrid()});
		}
	}
// ***********************************************************************************************************************	    		 
}