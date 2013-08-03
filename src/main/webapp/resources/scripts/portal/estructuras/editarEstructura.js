function editar(key) {
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

			baseParams : [
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
		

        var _jsonFormReader = new Ext.data.JsonReader( {
            root : 'estructuraVO',
            totalProperty: 'total',
            successProperty : 'success'
        }, [ {
            name : 'codigo',
            mapping : 'codigo',
            type : 'string'
        }, {
            name : 'descripcion',
            type : 'string',
            mapping : 'descripcion'
        }, {
            name : 'codigoRegion',
            type : 'string',
            mapping : 'codigoRegion'
        }, {
            name : 'codigoIdioma',
            type : 'string',
            mapping : 'codigoIdioma'
        }]);

        var formPanel = new Ext.FormPanel( {
            labelWidth : 100,
            url : _ACTION_GET_ESTRUCTURA,
            baseParams : {codigo: key},
            bodyStyle : ('padding: 5px, 0, 0, 0;','background: white'),
            labelAlign: 'right',
            width : 500,
            autoHeight: true,
            reader : _jsonFormReader,
            defaultType : 'textfield',
            items : [ 
            	new Ext.form.TextField({
				id:'flCodigoId',
                fieldLabel : getLabelFromMap('flCodigoId',helpMap,'C&oacute;digo'),
                tooltip : getToolTipFromMap('flCodigoId',helpMap,'C&oacute;digo de la Estructura') ,
				hasHelpIcon:getHelpIconFromMap('flCodigoId',helpMap),
				Ayuda:getHelpTextFromMap('flCodigoId',helpMap,'help'),
                name : 'codigo',
                disabled : true,
                allowBlank : false,
                width: 200//,
                //anchor: ''
            }), new Ext.form.TextField( {
            	id:'flDescId',
                fieldLabel : getLabelFromMap('flDescId',helpMap,'Descripci&oacute;n'),
                tooltip : getToolTipFromMap('flDescId',helpMap,'Descripci&oacute;n de la Estructura') ,
                blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
                hasHelpIcon:getHelpIconFromMap('flDescId',helpMap),
				Ayuda:getHelpTextFromMap('flDescId',helpMap,'ayuda'),
                allowBlank : false,
		        name : 'descripcion',
		        width: 300,
				maxLength: 40,
				maxLengthText: 'Solo se aceptan hasta 40 caracteres'
            }),
            /*
            {
     		   xtype: 'combo',
     		   id: 'cmbEdtRegionId',
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
                fieldLabel: getLabelFromMap('cmbEdtRegionId',helpMap,'Regi&oacute;n'),
                tooltip:getToolTipFromMap('cmbEdtRegionId',helpMap,'Regi&oacute;n'),
                hasHelpIcon: getHelpIconFromMap('cmbEdtRegionId',helpMap),
				Ayuda: getHelpTextFromMap('cmbEdtRegionId',helpMap,'ayuda'),
                width: 200,
                emptyText:'Seleccione Region ...',
                selectOnFocus:false,
                forceSelection:false,
                allowBlank :true,
                blankText: getMsgBlankTextFromMap('400013', helpMap, 'Este campo es requerido')
                */
                
                /*,
                se pido segun issue 1229
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
             		Ext.getCmp('formComboIdiomaEdit').clearValue();
     	   	   }*/
              //},
            {
            	xtype: 'combo',
            	id: 'formComboIdiomaEdit',
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
                fieldLabel: getLabelFromMap('cmbIdioma',helpMap,'Idioma'),
                tooltip: getToolTipFromMap('cmbIdioma',helpMap,'Idioma'), 
                hasHelpIcon: getHelpIconFromMap('cmbEdtRegionId',helpMap),
				Ayuda: getHelpTextFromMap('cmbEdtRegionId',helpMap,'ayuda'),
                width: 200, 
                emptyText:'Seleccione Idioma...',
                selectOnFocus:true, 
                forceSelection:true ,
                allowBlank : false,
                blankText: getMsgBlankTextFromMap('400013', helpMap, 'Este campo es requerido')
             }]
        });



//Windows donde se van a visualizar la pantalla
    
        var window = new Ext.Window({
        	id:'wndwEdtrEstrctrsId',
        	title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('wndwEdtrEstrctrsId', helpMap,'Editar Estructuras')+'</span>',
			//title: getLabelFromMap('52', helpMap,'Editar Estructura'),
        	width: 500,
        	//height:160,
        	autoHeight: true,
        	minWidth: 300,
        	minHeight: 100,
        	modal:true,
        	layout: 'fit',
        	plain:true,
        	bodyStyle:'padding:5px;',
        	buttonAlign:'center',
        	items: formPanel,
            buttons : [ {
				id: 'window1GuardarId',
                text : getLabelFromMap('window1GuardarId',helpMap,'Guardar'),
				tooltip : getToolTipFromMap('window1GuardarId',helpMap,'Guardar la actualizaci&oacute;n'),
                disabled : false,

                handler : function() {

                    if (formPanel.form.isValid()) {

                        formPanel.form.submit( {

                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_GUARDAR_ESTRUCTURA,

                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0], function(){reloadGrid();});
                                window.close();                                     
                            },

                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Aviso'),'Problemas al Guardar: ' + action.result.errorMessages[0]);
                            },

                            waitMsg : getLabelFromMap('400022', helpMap,'Guardando Actualizaci&oacute;n de datos...')

                        });

                    } else {

                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));

                    }

                }

            }, {

                id: 'window1CancelarId',
                text : getLabelFromMap('window1CancelarId',helpMap, 'Cancelar'),
				tooltip : getToolTipFromMap('window1CancelarId',helpMap,'Cancela la edici&oacute;n'),
                handler : function() {
                    window.close();
                }

            }]

    	});



    	window.show();
    	
    	//Primero se cargan los stores para que se muestren la descripciones de los combos, segun los valores del registro elegido
    	//storeRegion.load({
    	//	callback: function(){
    				storeIdioma.load({
    					callback: function(){
    					formPanel.form.load({
    						waitTitle : getLabelFromMap('400021', helpMap,'Espere...'),
                    		waitMsg : getLabelFromMap('400028', helpMap,'Leyendo datos...')
        									});
    									}
    					});
    	//			}
    	//		});
    	

        //se carga el formulario con los datos de la estructura a editar
        


    };