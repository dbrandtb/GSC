function anexarArchivoDigitalizado(_record){

var	storeComboCargaArchivos = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_TIPO_ARCHIVO2}),
			reader: new Ext.data.JsonReader(
				{
					root:'comboCargaArchivos',
					totalProperty: 'totalCount',
					successProperty : '@success'
				},
				[
					{name: 'codigo',mapping:'codigo', type: 'string'},
					{name: 'descripcion',mapping:'descripcion', type: 'string'},
					{name: 'directorioAsociado',mapping:'directorioAsociado', type: 'string', hidden: true} 
				]
				)
		})
var codigoDirectorioSubida;

var comboTipoArchivo = new Ext.form.ComboBox({
	    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	    store:storeComboCargaArchivos,
	    id:'tipoArchivo',
	    name: 'tipoArchivo',
	    displayField:'descripcion',
	    valueField:'codigo',
	    hiddenName: 'codigoh',
	    typeAhead: true,
	    anchor:'99%',
	    allowBlank : false,
	    mode: 'local',
	    emptyText:'Seleccionar Tipo de Archivo...',
	    triggerAction: 'all',
	    fieldLabel: getLabelFromMap('cboTipoArchivo',helpMap,'Tipo de Archivo'),
	    tooltip:getToolTipFromMap('cboTipoArchivo',helpMap,'Tipo de Archivo'),
	    labelAlign:'right',
	    selectOnFocus:true,
	    forceSelection:true,
	    emptyText:'Seleccione Tipo Archivo',
	    onSelect: function(record){
	    				this.setValue(record.get("codigo"));
		                this.collapse();
		                codigoDirectorioSubida=record.get("directorioAsociado");
		                //alert('direc ' + record.get("codigo"));
		                //alert('descripcion ' + record.get("descripcion"));
		                //alert('directorioAsociado ' + record.get("directorioAsociado"));
		                //alert('codigoDirectorio'+ codigoDirectorioSubida);
		                
	    }
	    
       });	
	

	var panelUpload = new Ext.ux.UploadPanel({
		//anchor:'30%',
		width: 305,
		autoHeight:true,
		
		//height: 300,
		fieldLabel:'Upload Panel',
		hidden:true,
		frame: true,
		//url:
		addText:'Agregar',
		clickRemoveText:'Click para eliminar de la lista',
		clickStopText:'Click para detener',
		emptyText:'Carga de Archivo de Fax',
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
		hideUploadButton: true
	});



		
	var formPanel = new Ext.FormPanel({
		    width: 320,
		    bodyStyle:'background: white',
	        buttonAlign: "center",
	        labelAlign: 'right',
	        frame:true,
	        layout: 'table',
            layoutConfig: { columns: 1, columnWidth: .33},
		    url: _ACTION_AGREGAR_ARCHIVO,
		    items: [
		    		{
           			html: '<br/><span class="x-form-item" style="font-weight:bold"></span>',
            		colspan:3
            		},
		            {            		
           			layout: 'form',  
           			labelWidth: 90,  
           			buttonAlign: "center",
           			     			            		
           			items: [
           			        comboTipoArchivo
           				]
            		},{
           			layout: 'form',
           			buttonAlign: "center",
           			labelWidth: 90,
           			items: [
           				panelUpload
                       
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



		var ventana = new Ext.Window ({
		    title: '<span style="color:black;font-size:12px;">Anexar Archivo de Fax Digitalizado</span>',
		    width: 335,
		    modal: true,
		    autoHeight: true,
		    items: [formPanel],
		      buttonAlign:'center',
		       buttons: [ {
		        text:getLabelFromMap('BtnGuardar',helpMap,'Aceptar'),
		        tooltip: getToolTipFromMap('BtnGuardar',helpMap,'Aceptar'),
				handler: function(){
                if (formPanel.form.isValid()) {
                	
                	var _files = panelUpload.store.queryBy(function(r){return 'done' !== r.get('state');});
                	 
                	 if (_files.getCount()) { 
	                	var _url = "cmd=upload&" + 
	                				"directorio=" + codigoDirectorioSubida ;//+ "&" +
	                				//"nombre=" + Ext.getCmp('dsArchivo').getValue();
						
	                	//panelUpload.setUrl (_ACTION_AGREGAR_ARCHIVO + "?" + _url);
	                	alert(_url);
						
						panelUpload.setUrl (Ext.getCmp("texArchivoId").setValue() );
						
						panelUpload.uploadCallback = function (_success) {
							
							if (_success) {
							
							   ventana.close();
							}
						}
						panelUpload.onUpload();
					} else { //alert("no hay archivos para subir");
						Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un archivo a cargar para realizar esta operaci&oacute;n'));
					}
                 
                } else {
                    Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
                }
            }
						},
						
						  { 
		                    text:getLabelFromMap('compraBtnBack',helpMap,'Regresar'),
		                    tooltip: getToolTipFromMap('compraBtnBack',helpMap,'Regresa a la pantalla anterior'),
							handler: function(){ventana.close()}
						}
				 
			]
						
						
		});
		
		//console.log(panelUpload);
		ventana.show();
		panelUpload.show();
		storeComboCargaArchivos.load();

		
	
	}


