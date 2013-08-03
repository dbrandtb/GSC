function editar (record){

        var storeRegion = new Ext.data.Store({
    			proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_REGION}),
                reader: new Ext.data.JsonReader({
		            	root:'regionesComboBox',
		            	totalProperty: 'totalCount',
		            	successProperty : '@success'
	        	},
	        	[
		        {name: 'codigo',  type: 'string',  mapping:'codigo'},
		        {name: 'descripcion',  type: 'string',  mapping:'descripcion'}
				])
        });

         var storeIdioma = new Ext.data.Store({
    			proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_IDIOMA}),
		        reader: new Ext.data.JsonReader({
		            	root:'comboIdiomas',
		            	totalProperty: 'totalCount',
		            	successProperty : '@success'
	       	 	},
	       	 	[
		        {name: 'codigo',  type: 'string',  mapping:'codigo'},
		        {name: 'descripcion',  type: 'string',  mapping:'descripcion'}
				])
        });

		var readerForm = new Ext.data.JsonReader({
            	root:'MRegistroCatalogoLogico',
            	totalProperty: 'totalCount',
            	successProperty : '@success'
	        },[
	        {name: 'cdTabla',  type: 'string',  mapping:'cdtabla'},
	        {name: 'cdRegion',  type: 'string',  mapping:'cdregion'},
	        {name: 'cdIdioma',  type: 'string',  mapping:'cdidioma'},
	        {name: 'codigo',  type: 'string',  mapping:'codigo'},
	        {name: 'descripcion',  type: 'string',  mapping:'descripcion'},
	        {name: 'descripcionLarga',  type: 'string',  mapping:'descripcionLarga'},
	        {name: 'etiqueta',  type: 'string',  mapping:'etiqueta'}
		]);
		
         var storeForm = new Ext.data.Store({
    			proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_IDIOMA}),
				reader: readerForm
        });


var formEdit = new Ext.FormPanel ({
            labelWidth : 100,
            labelAlign:'right',
            url : _ACTION_OBTENER_REGISTRO,
            reader : storeForm,
            bodyStyle : 'padding:5px 5px 0',
            width : 500,
            labelAlign:'right',
            defaults : {width : 200 },
            defaultType : 'textfield',
            store: storeForm,
            reader: readerForm,
            items : [
				{
                    xtype: 'textfield',
                    fieldLabel: getLabelFromMap('txtTabla',helpMap,'Nombre tabla'),
                    tooltip:getToolTipFromMap('txtTabla',helpMap,'Nombre tabla'),
                    disabled : true,
                    allowBlank: false,
                    name : 'cdTabla',
                    id: 'cdTablaId',
                    readOnly: true
                },
                {
                    xtype: 'combo',
                    id:'comboRegionEditId',
                    labelWidth: 50,
                    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                    store: storeRegion,
                    disabled : true,
                    displayField:'descripcion',
                    valueField: 'codigo',
                    hiddenName: 'cdRegion',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('cmbRegion',helpMap,'Regi&oacute;n'),
                    tooltip:getToolTipFromMap('cmbRegion',helpMap,'Regi&oacute;n'),
                    width: 200,
                    emptyText:'Seleccione Region ...',
                    selectOnFocus:true,
                    forceSelection:true,
                    allowBlank : false
                    //blankText: getMsgBlankTextFromMap('400013', helpMap, 'Este campo es requerido'),
                },
                {
                    xtype: 'combo', 
                    id:'comboIdiomaEditId',
                    labelWidth: 70, 
                    tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                store: storeIdioma,
	                displayField:'descripcion', 
	                valueField:'codigo', 
	                hiddenName: 'cdIdioma', 
	                disabled : true,
	                typeAhead: true,
	                mode: 'local', 
	                triggerAction: 'all', 
	                fieldLabel: getLabelFromMap('cmbIdioma',helpMap,'Idioma'),
                    tooltip:getToolTipFromMap('cmbIdioma',helpMap,'Idioma'), 
	                width: 200, 
	                emptyText:'Seleccione Idioma...',
	                selectOnFocus:true, 
	                forceSelection:true,
	                allowBlank : false
	                //blankText: getMsgBlankTextFromMap('400013', helpMap, 'Este campo es requerido'),
	             },
                {
                	xtype: 'textfield',
                	id:'txtCodigoId',
                	fieldLabel: getLabelFromMap('txtCodigo',helpMap,'C&oacute;digo'),
                    tooltip:getToolTipFromMap('txtCodigo',helpMap,'C&oacute;digo'),
                    disabled : true,
                    allowBlank: false,
                 	name: 'codigo',
                 	readOnly: 'true'
                 },
               	{
               		xtype: 'textfield',
               		id:'txtDescripcionId',
                 	fieldLabel: getLabelFromMap('txtDescripcion',helpMap,'Descripci&oacute;n'),
                 	tooltip:getToolTipFromMap('txtDescripcion',helpMap,'Descripci&oacute;n'),
                 	allowBlank:false,
                 	maxLength: 30,
                   	name: 'descripcion'
				},
                {
                	xtype: 'textfield',
                	id:'txtDescripcionLarga',
                	fieldLabel: getLabelFromMap('txtDescripcionLarga',helpMap,'Descripci&oacute;n Larga'),
                    tooltip:getToolTipFromMap('txtDescripcionLarga',helpMap,'Descripci&oacute;n Larga'),
                    allowBlank:false,
                    maxLength: 80,
                 	name: 'descripcionLarga'
                 },
               	{
               		xtype: 'textfield',
               		id:'txtEtiquetaId',
                 	fieldLabel: getLabelFromMap('txtEtiqueta',helpMap,'Etiqueta'),
                 	tooltip:getToolTipFromMap('txtEtiqueta',helpMap,'Etiqueta'),
                   	name: 'etiqueta'
				}
            ]
        });

	var win = new Ext.Window ({
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('95', helpMap,'Editar Cat&aacute;logo L&oacute;gico')+'</span>',
			width: 400,
			autoHeight: true,
        	bodyStyle:'padding:5px;',
        	buttonAlign:'center',
        	modal: true,
        	items: formEdit,
            buttons : [ {
				text:getLabelFromMap('BtnGuardar', helpMap,'Guardar'),
				tooltip:getToolTipFromMap('BtnGuardar', helpMap,'Guarda el registro'),
                disabled : false,
                handler : function() {
                    if (formEdit.form.isValid()) {
                        /*formEdit.form.submit( {
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
                        });*/
                        	guardarCatalogosLogicos();
                            win.close();                       
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
    //TODO  Hacer el load del form 
    storeRegion.load({
    	callback: function () {
    		storeIdioma.load({
    			callback: function () {
    			formEdit.form.load({
    			params: {	
    				cdTabla:record.get("cdtabla"),
    				cdRegion:record.get("cdregion"),
    				cdIdioma:record.get("cdidioma"),
    				codigo:record.get("codigo")
					},
				callback:function(record,opt,success){
			    	if (success){
			    		formEdit.findById('comboRegionEditId').setValue(storeForm.reader.jsonData.MRegistroCatalogoLogico[0].cdregion);
			    		formEdit.findById('comboIdiomaEditId').setValue(storeForm.reader.jsonData.MRegistroCatalogoLogico[0].cdidioma);
			      		formEdit.findById('txtCodigoId').setValue(storeForm.reader.jsonData.MRegistroCatalogoLogico[0].codigo);
			      		formEdit.findById('txtDescripcionId').setValue(storeForm.reader.jsonData.MRegistroCatalogoLogico[0].descripcion);
			      		formEdit.findById('txtDescripcionLargaId').setValue(storeForm.reader.jsonData.MRegistroCatalogoLogico[0].descripcionLarga);
			      		formEdit.findById('txtEtiquetaId').setValue(storeForm.reader.jsonData.MRegistroCatalogoLogico[0].etiqueta);
			   				 }
				},
				failure: function() {Ext.Msg.alert('Error', 'No se encontraron registros');}
    	});
    	}
    });
    	}
    });
    //storeIdioma.load();
    

function guardarCatalogosLogicos()
{
        var params = {
                 cdTabla: Ext.getCmp("cdTablaId").getValue(),
                 cdRegion: Ext.getCmp("comboRegionEditId").getValue(),
                 cdIdioma: Ext.getCmp("comboIdiomaEditId").getValue(),
                 codigo: Ext.getCmp("txtCodigoId").getValue(),
                 descripcion: Ext.getCmp("txtDescripcionId").getValue(),
                 descripcionLarga: Ext.getCmp("txtDescripcionLarga").getValue(),
                 etiqueta: Ext.getCmp("txtEtiquetaId").getValue()                
             };
        execConnection (_ACTION_GUARDAR_REGISTROS, params, cbkGuardar);
}
function cbkGuardar (success, message) {
    if (success) {
          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), message,function(){reloadGrid(Ext.getCmp('grid2'));});
    } else {
          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), message);
    }

}

}
