function anexarArchivoDigitalizado(nmCaso, nmMovimiento, _record){

    var storeArchivo = new Ext.data.Store({
			proxy:  new Ext.data.HttpProxy({url: _ACTION_AGREGAR_ARCHIVO}),
			reader: new Ext.data.JsonReader({
						root:'MArchivosList',
						totalProperty: 'totalCount',
						successProperty : '@success'
					},
					[
						{name: 'tipo',  type: 'string'},
						{name: 'descripcion',  type: 'string'}
					]
			)
	});

var comboTipoArchivo = new Ext.form.ComboBox({
	    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	    store:obtenerStoreParaCombo("CATBOTIPAR"),
	    //success:function() {obtenerStoreParaCombo()}, 
	    id:'tipoArchivo',
	    name: 'tipoArchivo',
	    displayField:'descripcion',
	    valueField:'codigo',
	    hiddenName: 'codigoh',
	    typeAhead: true,
	    anchor:'97%',
	    allowBlank : false,
	    mode: 'local',
	    triggerAction: 'all',
	    fieldLabel: getLabelFromMap('tipoArchivo',helpMap,'Tipo de Archivo'),
	    tooltip:getToolTipFromMap('tipoArchivo',helpMap,'Seleccione Tipo de Archivo'),
        hasHelpIcon:getHelpIconFromMap('tipoArchivo',helpMap),								 
        Ayuda: getHelpTextFromMap('tipoArchivo',helpMap),	    
	    labelAlign:'right',
	    selectOnFocus:true,
	    forceSelection:true,
	    emptyText:'...'
       });	

	var panelUpload = new Ext.ux.UploadPanel({
		//anchor:'30%',
		width: 300,
		autoHeight:true,
		//height: 300,
		fieldLabel:'Upload Panel',
		hidden:true,
		frame: true,
		addText:'Agregar',
		clickRemoveText:'Click para eliminar de la lista',
		clickStopText:'Click para detener',
		emptyText:'Agrega un archivo para cargar...',
		errorText:'Error',
		fileDoneText:'Archivo <b>{0}</b> ha sido cargado exitosamente',
		fileFailedText:'No se pudo cargar el archivo <b>{0}</b>',
		fileQueuedText:'Esperando a cargar el archivo <b>{0}</b>',
		fileStoppedText:'Archivo <b>{0}</b> detenido por el usuario',
		fileUploadingText:'Cargando archivo <b>{0}</b>',
		removeAllText:'Eliminar todo',
		removeText:'Eliminar',
		stopAllText:'Detener todo',
		uploadText:'Cargar',
		singleUpload: true,
		maxFileCount: 1,
		hideUploadButton: true,
		hideCloseButton: true
	});

var store ;
 function obtenerStoreParaCombo(_tableId){
		store = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_TIPO_ARCHIVO}),
			reader: new Ext.data.JsonReader(
				{
					root:'comboTiposArchivos',
					totalProperty: 'totalCount',
					successProperty : '@success'
				},
				[
					{name: 'codigo',mapping:'cdTipoar', type: 'string'},
					{name: 'descripcion',mapping:'dsArchivo', type: 'string'}
				]
				)
		});
		return store;
	}; 
	var formPanel = new Ext.FormPanel({
		    width: 500,
		    store: storeArchivo,
		    bodyStyle:'background: white',
	        buttonAlign: "center",
	        labelAlign: 'right',
	        frame:true,
	        layout: 'table',
            layoutConfig: { columns: 1, columnWidth: .33},
		    items: [
		    		{
           			html: '<br/><span class="x-form-item" style="font-weight:bold"></span>',
            		colspan:3
            		},
		            {            		
           			layout: 'form',  
           			labelWidth: 90,         		
           			items: [
           			        comboTipoArchivo
           				]
            		},
            		   {
           			layout: 'form',
           			labelWidth: 90,
           			items: [
                           {
                            xtype: 'textfield',
                            id: 'dsArchivo',
                            fieldLabel: getLabelFromMap('dsArchivo',helpMap,'Descripci&oacute;n'),
                            tooltip:getToolTipFromMap('dsArchivo',helpMap,'Descripci&oacute;n'),
                            hasHelpIcon:getHelpIconFromMap('dsArchivo',helpMap),								 
                            Ayuda: getHelpTextFromMap('dsArchivo',helpMap),                            
                            name: 'dsArchivo',
                            maxLength: 200,
                            width: 150
                           }
           				  ]
            		},
            		   {
           			layout: 'form',
           			labelWidth: 90,
           			items: [
           				panelUpload
                       /*{
                           xtype: 'textfield',
                           id: 'txtDireccionId',
                            fieldLabel: getLabelFromMap('txtDescripcionId',helpMap,'Direcci&oacute;n'),
                            tooltip:getToolTipFromMap('txtDescripcionId',helpMap,'Direcci&oacute;n'),
                            name: 'txtDireccionId',
                            //value: txtDescripcionId,
                            disabled: true,
                            width: 150
                     }*/
           				]
            		},
                    {
                       layout: 'form',
                       labelWidth: 90,
                       items: [
                       {
                           xtype: 'textfield',
                           id: 'nmarchivo',
                            fieldLabel: getLabelFromMap('nmarchivo',helpMap,'Numero Archivo'),
                            tooltip:getToolTipFromMap('nmarchivo',helpMap,'Numero Archivo'),
                            hasHelpIcon:getHelpIconFromMap('nmarchivo',helpMap),								 
                            Ayuda: getHelpTextFromMap('nmarchivo',helpMap),
                            name: 'nmarchivo',
                           // value: numArchivo,
                            disabled: true,
                            width: 35
                         }
                           ]
                    },
            		{
           			layout: 'form'
            		},
            		{
           			html: '<br/><span class="x-form-item" style="font-weight:bold"></span>',
            		colspan:3
            		}
		   ]
	});

/*   function obtenerStoreParaCombo(cat){
		var store = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_TIPO_ARCHIVO}),
			reader: new Ext.data.JsonReader(
				{
					root:'comboDatosCatalogo',
					totalProperty: 'totalCount',
					successProperty : '@success'
				},
				[
					{name: 'codigo',mapping:'id', type: 'string'},
					{name: 'descripcion',mapping:'texto', type: 'string'}
				]
				)
		});
		store.load();
		return store;
	};*/

		var ventana = new Ext.Window ({
			id:'WindAnexar',
            title: getLabelFromMap('WindAnexar', helpMap,'Anexar Archivo Digitalizado'),		    
		    width: 500,
		    modal: true,
		    autoHeight: true,
		    items: [formPanel],
		      buttonAlign:'center',
		       buttons: [ {
		        text:getLabelFromMap('BtnGuardar',helpMap,'Guardar'),
		        tooltip: getToolTipFromMap('BtnGuardar',helpMap,'Guardar'),
				handler: function(){
                if (formPanel.form.isValid()) {
                	var _nmmov = "";
                	var _files = panelUpload.store.queryBy(function(r){return 'done' !== r.get('state');});
                	if (_files.getCount()) { //Si hay archivos para subir
                	_nmmov = (vg_nmLoadFile)?vg_nmMovimiento:"";
                	vg_nmLoadFile = true;                	
                	
                	
                	var nombreArchivo="";
                	if (_record != null && _record != undefined)nombreArchivo = _record.get('dsArchivo');
                	else nombreArchivo = panelUpload.store.getAt(0).get('fileName');
                	
	                	var _url = "cmd=upload" + "&" +
	                				"tipoArchivo=" + comboTipoArchivo.getValue() + "&" +
	                				"dsArchivo=" + Ext.getCmp('dsArchivo').getValue() + "&" +
	                				"nmarchivo=" + Ext.getCmp('nmarchivo').getValue() + "&" +
	                				"nmcaso=" + nmCaso + "&" +
	                				"nmovimiento=" + nmMovimiento + "&" +
	                				"dsnomarc=" + nombreArchivo;
	                	panelUpload.setUrl (_ACTION_AGREGAR_ARCHIVO + "?" + _url);
						panelUpload.uploadCallback = function (_success,_response) {
							endMask();
							if (_success) {			
								Ext.getCmp("tipoArchivo").setRawValue();								
								formPanel.findById("dsArchivo").setValue("");
								//limpiar el componente de upload
								panelUpload.removeAll();
								//panelUpload.buttonsAt[1].disabled=true;
								panelUpload.getButtonAt();
								vg_nmMovimiento = Ext.util.JSON.decode(_response.responseText).nmovimiento;
							}
						}
						panelUpload.onUpload();
						startMask('WindAnexar', 'Espere...');
					} else {
						_nmmov = (vg_nmLoadFile)?vg_nmMovimiento:"";
						vg_nmLoadFile = true;
						
							var nombreArchivo="";
	                	if (_record != null && _record != undefined)nombreArchivo = _record.get('dsArchivo');
	                	else nombreArchivo = panelUpload.store.getAt(0).get('fileName');
	                	
						var _params = {
							tipoArchivo: comboTipoArchivo.getValue(),
							dsArchivo: Ext.getCmp('dsArchivo').getValue(),
							nmarchivo: Ext.getCmp('nmarchivo').getValue(),
							nmcaso: nmCaso,
							nmovimiento: nmMovimiento,
							dsnomarc: nombreArchivo
						};
						startMask('WindAnexar', 'Espere...');
						execConnection (_ACTION_AGREGAR_ARCHIVOJSON, _params, function (_success, _message,_response) {
							endMask();
							if (_success) {
								Ext.Msg.alert('Aviso', _message, function(){
									Ext.getCmp("tipoArchivo").setRawValue();
									formPanel.findById("dsArchivo").setValue("");
									//limpiar el componente de upload			
									vg_nmMovimiento = Ext.util.JSON.decode(_response).nmovimiento;
								});
							}else {
								Ext.Msg.alert('Aviso', _message);
							}
						});
					}
                } else {
                    Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
                }
            }
						},
						
						  { 
		                    text:getLabelFromMap('compraBtnBack',helpMap,'Regresar'),
		                    tooltip: getToolTipFromMap('compraBtnBack',helpMap,'Regresa a la pantalla anterior'),
							handler: function(){
												Ext.getCmp("gridId").store.load(
													{
														params:{nmcaso: nmCaso, nmovimiento: vg_nmMovimiento,start:0,limit:20}
													});
												vg_nmLoadFile = false;
												Ext.getCmp("dsMovimiento").setValue(vg_nmMovimiento);
												ventana.close();
												}
						}
				 
			]
						
						
		});

		ventana.show();
		panelUpload.show();
		store.load({
			param:{cdTabla: "CATBOTIPAR"},
			callback: function () {
				if (_record != null && _record != undefined) {
					comboTipoArchivo.setValue(_record.get('tipo'));
					Ext.getCmp('dsArchivo').setValue(_record.get('descripcion'));
					Ext.getCmp('nmarchivo').setValue(_record.get('numArchivo'));
				}
			}
		});
		
        //formPanel.store.load({
			//params:{nmcaso: caso,nmovimiento:movimiento,start:0,limit:10}
		//});
		
	
	}


