function agregar (){

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

         var storeIdioma = new Ext.data.Store({

    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_COMBO_IDIOMA
                }),

                reader: new Ext.data.JsonReader({
            	root:'comboIdiomas',
            	totalProperty: 'totalCount',
            	successProperty : '@success'
	        },[
		        {name: 'codigo',  type: 'string',  mapping:'codigo'},
		        {name: 'descripcion',  type: 'string',  mapping:'descripcion'}
			])
        });

var formAdd = new Ext.FormPanel ({			
            labelWidth : 100,
            labelAlign:'right',
            bodyStyle : 'padding:5px 5px 0',
            width : 500,
            labelAlign:'right',
            defaults : {width : 200 },
            defaultType : 'textfield',
            //se definen los campos del formulario
            items : [
				{
                    fieldLabel: getLabelFromMap('txtTabla',helpMap,'Nombre tabla'),
                    tooltip:getToolTipFromMap('txtTabla',helpMap,'Nombre tabla'),
                    hasHelpIcon:getHelpIconFromMap('txtTabla',helpMap),
                    Ayuda: getHelpTextFromMap('txtTabla',helpMap,''),   	
                    allowBlank:false,
                    xtype: 'textfield',
                    name : 'cdTabla'
                },
                {
                    xtype: 'combo',
                    labelWidth: 50,
                    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                    store: storeRegion,
                    displayField:'descripcion',
                    valueField: 'codigo',
                    hiddenName: 'cdRegion',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('cmbRegion',helpMap,'Regi&oacute;n'),
                    tooltip:getToolTipFromMap('cmbRegion',helpMap,'Regi&oacute;n'),
                    hasHelpIcon:getHelpIconFromMap('cmbRegion',helpMap),
                    Ayuda: getHelpTextFromMap('cmbRegion',helpMap,''),   	
                    width: 200,
                    emptyText:'Seleccione Region ...',
                    selectOnFocus:true,
                    forceSelection:true,
                    allowBlank : false
                    //blankText: getMsgBlankTextFromMap('400013', helpMap, 'Este campo es requerido'),
                },
                {
                    xtype: 'combo', 
                    labelWidth: 70, 
                    tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                store: storeIdioma,
	                displayField:'descripcion', 
	                valueField:'codigo', 
	                hiddenName: 'cdIdioma', 
	                typeAhead: true,
	                mode: 'local', 
	                triggerAction: 'all', 
	                fieldLabel: getLabelFromMap('cmbIdioma',helpMap,'Idioma'),
                    tooltip:getToolTipFromMap('cmbIdioma',helpMap,'Idioma'), 
                    hasHelpIcon:getHelpIconFromMap('cmbIdioma',helpMap),
                    Ayuda: getHelpTextFromMap('cmbIdioma',helpMap,''),   	
	                width: 200, 
	                emptyText:'Seleccione Idioma...',
	                selectOnFocus:true, 
	                forceSelection:true ,
	                allowBlank : false
	                //blankText: getMsgBlankTextFromMap('400013', helpMap, 'Este campo es requerido'),
	             },
                {
                	xtype: 'textfield',
                	fieldLabel: getLabelFromMap('txtCodigo',helpMap,'C&oacute;digo'),
                    tooltip:getToolTipFromMap('txtCodigo',helpMap,'C&oacute;digo'),
                    hasHelpIcon:getHelpIconFromMap('txtCodigo',helpMap),
                    Ayuda: getHelpTextFromMap('txtCodigo',helpMap,''),   	
                    allowBlank:false,
                 	name: 'codigo'
                 },
               	{
               		xtype: 'textfield',
                 	fieldLabel: getLabelFromMap('txtDescripcion',helpMap,'Descripci&oacute;n'),
                 	tooltip:getToolTipFromMap('txtDescripcion',helpMap,'Descripci&oacute;n'),
                    hasHelpIcon:getHelpIconFromMap('txtDescripcion',helpMap),
                    Ayuda: getHelpTextFromMap('txtDescripcion',helpMap,''),   	
                 	allowBlank:false,
                 	maxLength: 30,
                   	name: 'descripcion'
				},
                {
                	xtype: 'textfield',
                	fieldLabel: getLabelFromMap('txtDescripcionLarga',helpMap,'Descripci&oacute;n Larga'),
                    tooltip:getToolTipFromMap('txtDescripcionLarga',helpMap,'Descripci&oacute;n Larga'),
                    hasHelpIcon:getHelpIconFromMap('txtDescripcionLarga',helpMap),
                    Ayuda: getHelpTextFromMap('txtDescripcionLarga',helpMap,''),   	
                    allowBlank:false,
                    maxLength: 80,
                 	name: 'descripcionLarga'
                 },
               	{
               		xtype: 'textfield',
                 	fieldLabel: getLabelFromMap('txtEtiqueta',helpMap,'Etiqueta'),
                 	tooltip:getToolTipFromMap('txtEtiqueta',helpMap,'Etiqueta'),
                    hasHelpIcon:getHelpIconFromMap('txtEtiqueta',helpMap),
                    Ayuda: getHelpTextFromMap('txtEtiqueta',helpMap,''),   	
                 	
                   	name: 'etiqueta'
				}
            ]
        });

	var win = new Ext.Window ({
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('95', helpMap,'Agregar Cat&aacute;logo L&oacute;gico')+'</span>',
			width: 400,
			autoHeight: true,
        	bodyStyle:'padding:5px;',
        	buttonAlign:'center',
        	modal: true,
        	items: formAdd,
            buttons : [ {
				text:getLabelFromMap('BtnGuardar', helpMap,'Guardar'),
				tooltip:getToolTipFromMap('BtnGuardar', helpMap,'Guarda el registro'),
                disabled : false,
                handler : function() {
                    if (formAdd.form.isValid()) {
                        formAdd.form.submit( {
                            url : _ACTION_GUARDAR_REGISTROS,
                            success : function(from, action) {
                                Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0], function(){reloadGrid(Ext.getCmp('grid2'));});
                                win.close();
                            },
                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0]);
                           },
                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')
                        });
                    } else {
                       Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                    }
                }
            }, {
				text:getLabelFromMap('BtnCancelar', helpMap,'Cancelar'),
				tooltip:getToolTipFromMap('BtnCancelar', helpMap, 'Cancela la operaci&oacute;n de guardar'),
                handler : function() {
                    win.close();
                }
            }]
	});

    win.show();
    storeRegion.load({
    	callback: function () {
    		storeIdioma.load();
    	}
    });
}
