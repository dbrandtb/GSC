// Funcion de Copiar una configuración de cuenta
 function copiar(record) {
		  var dsCuentaCorpo = new Ext.data.Store({
			     proxy: new Ext.data.HttpProxy({
			     url: _ACTION_OBTENER_CLIENTE_CORPO
		       	}),
		  		 reader: new Ext.data.JsonReader({root: 'clientesCorp',id: 'cdElemento'},
		  		 [{name: 'cdElemento', type: 'string',mapping:'cdElemento'},
		  		  {name: 'dsElemen', type: 'string',mapping:'dsElemen'},
		  		  {name: 'cdPerson', type: 'string',mapping:'cdPerson'}
		  		 ])
		  		 });
		  	  	
          var formPanel = new Ext.FormPanel( {
	            labelWidth : 100,
	            url : _ACTION_COPIAR_CONFIGURA_CUENTA,
	            frame : true,
	            labelAlign:'right',	          
	            renderTo: Ext.get('formulario'),
	            bodyStyle : 'background: white',
	            width : 350,
	            waitMsgTarget : true,
	            defaults : {
	                width : 230
	            },
	            defaultType : 'textfield',
	            //se definen los campos del formulario
	            items : [
	            new Ext.form.Hidden({name: 'cdPerson', id: 'cdPerson'}),
                new Ext.form.TextField(
                {
				fieldLabel: getLabelFromMap('txtFldCopiarCdConfig',helpMap,'Cuenta'),
				tooltip:getLabelFromMap('txtFldCopiarCdConfig',helpMap, 'Cuenta a la que se copiar&aacute; la configuraci&oacute;n'),
        		hasHelpIcon:getHelpIconFromMap('txtFldCopiarCdConfig',helpMap),
				Ayuda:getHelpTextFromMap('txtFldCopiarCdConfig',helpMap),
                //fieldLabel : 'Cuenta',
                name : 'cdConfig',
                value:record.get("dsElemen") ,
                disabled: true,
                allowBlank : false,
                width: 200
                }
                ),
                new Ext.form.Hidden({name : 'cdConfig',value:record.get("cdConfig")}),
                new Ext.form.ComboBox({
                    tpl: '<tpl for="."><div ext:qtip="{cdElemento}.{cdPerson}.{dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
					id:'windowCopiarComboDsCuentaCorpo',
					fieldLabel: getLabelFromMap('windowCopiarComboDsCuentaCorpo',helpMap,'Cliente'),
					tooltip: getToolTipFromMap('windowCopiarComboDsCuentaCorpo',helpMap, 'Cliente'),                    
			        hasHelpIcon:getHelpIconFromMap('windowCopiarComboDsCuentaCorpo',helpMap),
					Ayuda:getHelpTextFromMap('windowCopiarComboDsCuentaCorpo',helpMap),
                    store: dsCuentaCorpo,
                    //anchor:'100%',
                    displayField:'dsElemen',
                    valueField: 'cdElemento',
                    hiddenName: 'cdElemento',
                    typeAhead: true,
                    triggerAction: 'all',
                    //fieldLabel: "Cliente",
                    forceSelection: true,
                    mode: 'local',
                    width: 250,
                    emptyText:'Seleccione Cliente...',
                    selectOnFocus:true,
                    onSelect: function (record) {
                    	this.setValue(record.get("cdElemento"));
                        formPanel.findById(('cdPerson')).setValue(record.get("cdPerson"));
                        this.collapse();
                            }
                    })
	            ]
	        });

//Windows donde se van a visualizar la pantalla
        var window = new Ext.Window({
           	id:'windowCopiarId',
        	title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('windowCopiarId', helpMap,'Copiar configuraci&oacute;n de la cuenta')+'</span>',
        	//title: 'Copiar configuración de la cuenta',
        	width: 500,
        	height:170,
        	layout: 'fit',
        	plain:true,
        	modal: true,
        	bodyStyle:'padding:5px;',
        	buttonAlign:'center',
        	items: formPanel,
            //se definen los botones del formulario
            buttons : [ {
				id: 'windowCopiarIdButtonsGuardarId', 
				text: getLabelFromMap('windowCopiarIdButtonsGuardarId', helpMap,'Copiar'),
				tooltip: getToolTipFromMap('windowCopiarIdButtonsGuardarId', helpMap, 'Copia una configuraci&oacute;n de cuenta'),               
                //text : 'Copiar',
                disabled : false,
                handler : function() {
                    if (formPanel.form.isValid()) {
                        formPanel.form.submit( {
                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),action.result.actionMessages[0],function() {reloadGrid(Ext.getCmp('grid2'));});
                                window.close();
                                  
                            },
                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'),action.result.errorMessages[0]);
                            },
                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : getLabelFromMap('400034',helpMap,'copiando datos ...')
                        });
                    } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                    }
                }
            }, {
				id: 'windowCopiarIdButtonsCancelarId', 
				text: getLabelFromMap('windowCopiarIdButtonsCancelarId', helpMap,'Cancelar'),
				tooltip: getToolTipFromMap('windowCopiarIdButtonsCancelarId', helpMap,'Cancela la copia'),   
                text : 'Cancelar',
                handler : function() {
                    window.close();
                }
            }]
    	});
   
    	window.show();
        dsCuentaCorpo.load();

};