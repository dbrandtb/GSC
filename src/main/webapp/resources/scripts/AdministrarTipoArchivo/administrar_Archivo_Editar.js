 function actualizar(record){
 console.log(record);
    		
   	/*	var _proxy = new Ext.data.HttpProxy({url: _ACTION_OBTENER_INDICADORES_SI_NO});
   		
   		var _reader = new Ext.data.JsonReader({
	     		root: 'comboIndicadoresSINO',
	     		totalProperty: 'totalCount',
	     		id: 'id'
	     		},[
	    			{name: 'id', type: 'string',mapping:'id'},
	    			{name: 'texto', type: 'string',mapping:'texto'}
	 			  ]);
   		
    	var dsTarificacionAutomatica = new Ext.data.Store({
	     	//proxy: _proxy,
	     	//reader: _reader
	     	
	 	});  */		
    var seccion_reg = new Ext.data.JsonReader({
						root: 'estructuraListArchivo',  
						totalProperty: 'totalCount',
						successProperty: '@success'
						}, [
							{name: 'tipo', type: 'string', mapping: 'tipo'},
			        		{name: 'dsarchivo', type: 'string', mapping: 'dsArchivo'},
			        		{name: 'indarchivo', type: 'string', mapping: 'indarchivo'},
			        		{name: 'descripcion', type: 'string', mapping: 'descripcion'}
 						   ]
		       );
		       
	 	
var dsTipoArchivo  = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_TIPO_ARCHIVO}),
						reader: new Ext.data.JsonReader({
								root: 'comboGenerico',
								id: 'id',
								successProperty: '@success'
							}, [
								{name: 'id', type: 'string', mapping: 'id'},
								{name: 'texto', type: 'string', mapping: 'texto'}
							])//,
						//remoteSort: true
				});	 	
	 	
   		dsTipoArchivo.load({
   		                   params:{
   		                   idTablaLogica:'CTIPOARC'
   		                   }
   		});                   //////////
    		
		var codigo = new Ext.form.TextField({
			     fieldLabel: getLabelFromMap('descripcion',helpMap,'Archivo'),
			     tooltip:getToolTipFromMap('descripcion',helpMap,'Archivo'), 
                 hasHelpIcon:getHelpIconFromMap('descripcion',helpMap),								 
                 Ayuda: getHelpTextFromMap('descripcion',helpMap),
			     
				 id:'descripcion',
			     name: 'descripcion',      //'cdTipoar',
			     disabled: false,
			     anchor: '100%'
			 });
			 
	/*	var descripcion = new Ext.form.TextField({
			     fieldLabel: getLabelFromMap('agrConfTipEndTxtDesc',helpMap,'Descripci&oacute;n'),
			     tooltip:getToolTipFromMap('agrConfTipEndTxtDesc',helpMap,' Descripci&oacute;n del tipo de endoso'), 
				// id:'descripcionId',
			     name: 'descripcion',    //'dsarchivo',
			     width: 300,
		         allowBlank: false,
			     anchor: '100%'
			 });  */
		
		var comboTipoArchivo = new Ext.form.ComboBox({
	                    tpl: '<tpl for="."><div ext:qtip="{id}.{texto}" class="x-combo-list-item">{texto}</div></tpl>',
	                  //  id:'comboTarificacionAutomaticaId',
	                    id:'combotipoArchEdit',
	                    store: dsTipoArchivo,
	                    displayField:'texto',
	                    valueField:'id',
	                    hiddenName: 'swTariFi',
	                    typeAhead: true,
	                    mode: 'local',
	                    triggerAction: 'all',
	                    allowBlank: false,
					    fieldLabel: getLabelFromMap('combotipoArchEdit',helpMap,'Tipo de Archivo'),
					    tooltip:getToolTipFromMap('combotipoArchEdit',helpMap,'Tipo de Archivo'), 
                        hasHelpIcon:getHelpIconFromMap('combotipoArchEdit',helpMap),								 
                        Ayuda: getHelpTextFromMap('combotipoArchEdit',helpMap),
					    
	                    forceSelection: true,
	                    width: 130,
	                    selectOnFocus:true,
	                    emptyText:'Seleccione Tarificación Automática...',
	                    labelSeparator:''
	   });
		
		var formWindowInsertar = new Ext.FormPanel({
			//	title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('115',helpMap,'Editar Tipos de Archivos')+'</span>',
                id:'EditWintipArch',		
                title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('EditWintipArch',helpMap,'Editar Tipos de Archivos')+'</span>',				
				
		        iconCls:'logo',
		        bodyStyle:'background: white',
		        buttonAlign: "center",
		        labelAlign: 'right',
		        frame:true,
		        url: _ACTION_OBTENER_TIPOS_ARCHIVOS,
		        width: 550,
		        height:200,
                reader: seccion_reg, 		        
		        items: [
            		{
           			layout: 'form',
           			items:[
           					{//1ra columna
	           					columnWidth: .6,
	           					layout: 'form',
	           					items: [codigo]
           				    }
           				    
           			      ]        			
           		    },
           		      comboTipoArchivo
           		      ]
				  },
   	           	   {
			       	xtype: 'hidden', 
                	id: 'cdtipoar', 
                	name: 'cdtipoder'
			        }
				  );
			
		 var window = new Ext.Window({
        	width: 550,
        	height:195,
        	minWidth: 300,
        	minHeight: 100,
        	modal: true,
        	layout: 'fit',
        	plain:true,
        	bodyStyle:'padding:5px;',
        	buttonAlign:'center',
        	items: formWindowInsertar,
            buttons: [{
                text:getLabelFromMap('EditConfTipEndBtnSave',helpMap,'Guardar'),
                tooltip: getToolTipFromMap('EditConfTipEndBtnSave',helpMap,'Guarda Tipos Archivos'),
                disabled : false,
                handler : function() {
                    if (formWindowInsertar.form.isValid()) {
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
                    }
                }
            },
            {
              text:getLabelFromMap('EditConfTipEndBtnBack',helpMap,'Regresar'),
              tooltip: getToolTipFromMap('EditConfTipEndBtnBack',helpMap,'Regresa a la pantalla anterior'),
              handler : function(){window.close();}
            }]
    	});
    	
    	window.show();
 }