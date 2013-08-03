function agregar () {  

      /*
		var storeRegion = new Ext.data.Store({

			proxy: new Ext.data.HttpProxy({
				url: _ACTION_COMBO_REGION
			}),

			reader: new Ext.data.JsonReader({
				root:'regionesComboBox',
				totalProperty: 'totalCount',
				successProperty : '@success'
			},[
			   {name: 'codigo',  type: 'string',  mapping:'codigo'},
			   {name: 'descripcion',  type: 'string',  mapping:'descripcion'}
			])
		});
       */
		var storeIdioma = new Ext.data.Store({

			proxy: new Ext.data.HttpProxy({
				url: _ACTION_COMBO_IDIOMAS_REGION
			}),
			
			baseParams : 
				[
				 { codigoRegion :' '}
			],

			reader: new Ext.data.JsonReader({
				root:'idiomasRegionComboBox',
				totalProperty: 'totalCount',
				successProperty : '@success'
			},[
			   {name: 'codigo',  type: 'string',  mapping:'codigo'},
			   {name: 'descripcion',  type: 'string',  mapping:'descripcion'}
		   ])
		});
	
        var formPanel = new Ext.FormPanel( {
			id:'formPanelId',
            labelWidth : 100,
            url : _ACTION_GUARDAR_NUEVO_ESTRUCTURA,
            frame : true,
            renderTo: Ext.get('formulario'),
            bodyStyle : ('padding:0 5px 5px 0','background: white'),
            labelAlign: 'right',
            width : 350,
            autoHeight : true,
            waitMsgTarget : true,            
            defaultType : 'textfield',
            items : [
               new Ext.form.TextField( {
                id:'formTextDescripcionId',
                blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
                fieldLabel : getLabelFromMap('formTextDescripcionId', helpMap,'Descripci&oacute;n') ,
                tooltip : getToolTipFromMap('formTextDescripcionId',helpMap,'Descripci&oacute;n de la Estructura') ,
				hasHelpIcon:getHelpIconFromMap('formTextDescripcionId',helpMap),
				Ayuda:getHelpTextFromMap('formTextDescripcionId',helpMap),
                name : 'descripcion',
                allowBlank : false,
                width: 280,
                maxLength: 40,
				maxLengthText: 'Solo se aceptan hasta 40 caracteres'
            }),
            
           /*
            	   {
            		   xtype: 'combo',
                       id: 'formCmbCodigoRegionId',
            		   labelWidth: 50,
                       tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                       store: storeRegion,
                       displayField:'descripcion',
                       valueField: 'codigo',
                       name: 'codigoRegion',
                       hiddenName: 'codigoRegion',
                       typeAhead: true,
                       mode: 'local',
                       triggerAction: 'all',
                       fieldLabel: getLabelFromMap('formCmbCodigoRegionId',helpMap,'Regi&oacute;n'),
                       tooltip:getToolTipFromMap('formCmbCodigoRegionId',helpMap,'Regi&oacute;n'),
					   hasHelpIcon:getHelpIconFromMap('formCmbCodigoRegionId',helpMap),
					   Ayuda: getHelpTextFromMap('formCmbCodigoRegionId',helpMap),                       
                       width: 200,
                       emptyText:'Seleccione Region ...',
                       selectOnFocus:true,
                       forceSelection:false,
                       allowBlank : true,
                       blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido')
                       */
                       
                       /*,
                       onSelect : function(record, index, skipCollapse){
            		   		if(this.fireEvent('beforeselect', this, record, index) !== false){
            		   			this.setValue(record.data[this.valueField || this.displayField]);
            		   			if( !skipCollapse ) {
            		   				this.collapse();
            		   			}
            		   			this.lastSelectedIndex = index + 1;
            		   			this.fireEvent('select', this, record, index);
            		   		}
            		   		
            		   		//Cargar el combo Idioma enviando el codigo de Region elegido 
            		   		var codigo = record.get('codigo');
            		   		storeIdioma.baseParams['codigoRegion'] = codigo;
                    		storeIdioma.load();
                    		Ext.getCmp('formComboIdioma').clearValue();
            	   	   }*/
                   //}
                   //,
                   {
                	   xtype: 'combo',
                	   id: 'formComboIdioma',
                       labelWidth: 70, 
                       tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                       store: storeIdioma,
                       displayField:'descripcion', 
                       valueField:'codigo',
                       name : 'codigoIdioma',
                       hiddenName: 'codigoIdioma', 
                       typeAhead: true,
                       mode: 'local', 
                       triggerAction: 'all', 
                       fieldLabel: getLabelFromMap('formComboIdioma',helpMap,'Idioma'),
                       tooltip:getToolTipFromMap('formComboIdioma',helpMap,'Idioma'), 
					   hasHelpIcon:getHelpIconFromMap('formComboIdioma',helpMap),
					   Ayuda: getHelpTextFromMap('formComboIdioma',helpMap),                       
                       width: 200, 
                       emptyText:'Seleccione Idioma...',
                       selectOnFocus:true, 
                       forceSelection:true ,
                       allowBlank : false,
                       blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido')
   	             }
            ]

        });


//Windows donde se van a visualizar la pantalla

        var window = new Ext.Window({
        	id: 'wndwAgrEstrctrId',
        	width: 500,
        	height:150,        	
        	autoHeight : true,
        	title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('wndwAgrEstrctrId', helpMap,'Agregar estructura')+'</span>',
        	//title: getLabelFromMap('51', helpMap,'Agregar estructura'),
        	minWidth: 300,
        	modal:true,
        	minHeight: 100,
        	layout: 'fit',
        	//bodyStyle:'background: white',
        	plain:true,
        	bodyStyle:'padding:5px;',
        	buttonAlign:'center',
        	items: formPanel,
            buttons : [ {

                text : getLabelFromMap('windowBtnGuardar', helpMap,'Guardar'),
                tooltip : getToolTipFromMap('windowBtnGuardar', helpMap,'Guarda una estructura') ,

                disabled : false,

                handler : function() {

                    if (formPanel.form.isValid()) {

                        formPanel.form.submit( {

                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_GUARDAR_NUEVO_ESTRUCTURA,

                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0], function(){reloadGrid();});
                                window.close();
                            },

                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'),action.result.errorMessages[0]);
                            },

                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')

                        });

                    } else {

                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));

                    }

                }

            }, {

               text : getLabelFromMap('windowBtnCancelar', helpMap,'Cancelar') ,
                tooltip : getToolTipFromMap('windowBtnCancelar', helpMap, 'Cancela el ingreso de datos') ,

                handler : function() {
                    window.close();
                }

            }]

    	});

    	window.show();
   
    
     	//storeRegion.load({
        //	callback: function () {
    			storeIdioma.load();
        //   	}
        //});
        
        
        
};