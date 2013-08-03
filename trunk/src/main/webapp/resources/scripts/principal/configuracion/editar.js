var archivo1 = "";
var archivo2 = "";




function editarConfiguracion(record, store){

nameFile1 = '';
popup_window = '';
popup_winUpload = '';  
//winUploadFile ='';
editor;
accionEdit = 'edit';
	
	/*Store que carga el combo de Secciones*/
	
	var dataStoreSeccion = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url:'principal/seccionAction.action'
		}),
		reader: new Ext.data.JsonReader({
			root:'secciones'
		},[
			{name:'dsSeccion', 	type:'string', 	mapping:'dsSeccion'},
			{name:'cdSeccion', 	type:'string', 	mapping:'cdSeccion'}
		]),
		remoteSort:false
	});
	dataStoreSeccion.setDefaultSort('dsSeccion','desc');
	/*Store que carga el combo de tipos*/
	
	var dataStoreTipo = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url:'principal/tipoAction.action'
		}),
		reader: new Ext.data.JsonReader({
			root:'tipos'
		},[
			{name:'clave', 	type:'string', 	mapping:'clave'},
			{name:'valor', 	type:'string', 	mapping:'valor'}
		]),
		remoteSort:false
	});
	dataStoreTipo.setDefaultSort('valor','desc');
	
	var claveConfiguracion = new Ext.form.Hidden({	
		name :'claveConfiguracion',
		width: 200,
		value:_CVECONF
		/*,
		hideParent:true,
		hidden:true
		*/
	});
	var claveSistemaRol= new Ext.form.Hidden({
		name: 'claveSistemaRol',
		width:200,
		value:_CVESISROL
	});
	var claveElemento = new Ext.form.Hidden({
		name:'claveElemento',
		width:200,
		value:_CVEELEM
	});

	var dsConfiguracion = new Ext.form.Hidden({
		name:'dsConfiguracion',
		width:200,
		value:_DESCONF		
	});
	var dsSistemaRol= new Ext.form.TextField({
		fieldLabel:'Rol',
		name: 'dsSistemaRol',
		width:200,
		value:_DESSISROL,
		readOnly:true
	});
	var dsElemento = new Ext.form.TextField({
		fieldLabel:'Nivel',
		name:'dsElemento',
		width:200,
		value:_DESELEM,
		readOnly:true
	});
		
	/*atributos definidos por default*/
	
	
	/*
	var htmledit = new Ext.form.HtmlEditor({
            fieldLabel:'Texto Html',
            name:'texto',
            //id:'texto',
            hideParent:true,
            height:200,
            width:500,
            maxLength:2000,
            anchor:'98%'
	});
	
	*/
	
	var htmledit = new Ext.form.HtmlEditor({
            fieldLabel:'Texto Html',
            name:'texto',
            id:'texto',
            createLink: function() {
                   if ( htmledit.win.getSelection) 
                   {    // Mozilla Model
                        selection = htmledit.win.getSelection();
                            
                       if( !Ext.getCmp("windowLink")){
                          createLinkOption();
                       } 
                   }
                   
                   else if ( htmledit.doc.selection) 
                   {    // IE Model
                        rangoSelection =  htmledit.doc.selection.createRange();
                        selection =  htmledit.doc.selection.createRange().text
                       // alert("selectionIE=" + selection );
                        if( !Ext.getCmp("windowLink")){
                          createLinkOption();
                       } 
                   }      
                },
            hideParent:true,
            height:200,
            width:542,
            plugins: new Ext.ux.plugins.HtmlEditorImageInsert({
                           popTitle: 'Image url?',
                           popMsg: 'Please insert an image URL...',
                           popWidth: 400,
                           popValue: ''
                         })
	});
	
	 htmlEditLink = htmledit;
	
	
	var fileLoad = new Ext.form.TextField({
		//id:'id-fileLoad',
		fieldLabel:'Cargar',
		inputType:'file',
		anchor: '70%',
		name:'archivo'
	});
	var fileTwo = new Ext.form.TextField({
		fieldLabel:'Cargar',
		inputType:'file',
		anchor: '70%',
		name:'archivoDos'
	});
	var hiddenSeccionCombo = new Ext.form.Hidden({
		name:'claveSeccion',
		width:200,
		value:''		
	});	
	var seccionCombo = new Ext.form.ComboBox({ 
				//id:'id-seccion-combo-configuracion',
				tpl: '<tpl for="."><div ext:qtip="{dsSeccion}. {cdSeccion}" class="x-combo-list-item">{dsSeccion}</div></tpl>',
    			store: dataStoreSeccion,
				width: 300,
    			mode: 'local',
				name: 'comboSeccion',
				allowBlank:false,
    			typeAhead: true,
				labelSeparator:'',			
    			triggerAction: 'all',
    			forceSelection: true,    		
    			displayField:'dsSeccion',
				fieldLabel: 'Seccion',
				emptyText:'Seleccionar sección...',
				selectOnFocus:true,
				disabled:true
	});
	var habilitarCheck = new Ext.form.Checkbox({
				fieldLabel:'Habilitado',
                name:'chkHabilitado'/*,             	
                checked:false*/
    });		
	var nomArchivo = new Ext.form.TextField({
		fieldLabel:'Nombre Archivo',
		name:'dsArchivo',
		anchor:'70%'
	});
	var hiddenTipoCombo = new Ext.form.Hidden({
		name:'tipo',
		width:200,
		value:''		
	});
	var tipoCombo = new Ext.form.ComboBox({ 
				//id:'id-tipo-combo-configuracion',
				tpl: '<tpl for="."><div ext:qtip="{valor}.{clave}" class="x-combo-list-item">{valor}</div></tpl>',
    			store: dataStoreTipo,
				width: 300,
    			mode: 'local',
				name: 'comboTipo',
    			typeAhead: true,
				labelSeparator:'',			
    			triggerAction: 'all',    		
    			displayField:'valor',
    			allowBlank:false,
				fieldLabel: 'Tipo',
				emptyText:'Seleccionar tipo...',
				selectOnFocus:true
		});
		

		var upload_Reader = new Ext.data.JsonReader({
		      root: 'upload',
		      totalProperty: 'totalCount',
		      successProperty: '@success'
		      }
		 );
		
		
	var editarForm = new Ext.form.FormPanel({
				//id:'editar',
				url:'principal/editarConf.action',
				fileUpload : true,
				boder : false,
				frame : true,
				method : 'post',            	            
        		width : 570,
        		buttonAlign : 'center',
				baseCls : 'x-plain',
				labelWidth : 75,
				reader: upload_Reader,	
				/*onSubmit: Ext.emptyFn,
        		submit: function() {
               				// en la funcion de submit de ExtJS se sobreescribe para hacer un submit sin AJAX 
     					this.getEl().dom.setAttribute("action",'principal/editarConf.action');
      					//this.getEl().dom.target = '_windowExport';
      					this.getEl().dom.submit();
      			},	*/
				items:[				
					claveConfiguracion,
					dsSistemaRol,
					dsElemento,	
					claveSistemaRol,
					hiddenSeccionCombo,
					hiddenTipoCombo,
					seccionCombo,
					tipoCombo,
					habilitarCheck,
					nomArchivo,
					fileLoad,
					fileTwo,
					claveElemento,
					htmledit,
					dsConfiguracion
				]
			});

	var panelUpload = new Ext.ux.UploadPanel({
		//anchor:'30%',
		width: 300,
		autoHeight:true,
		//height: 300,
		fieldLabel:'Upload Panel',
		hidden:true,
		//url:
		addText:'Agregar',
		clickRemoveText:'Click para eliminar de la lista',
		clickStopText:'Click para detener',
		emptyText:'Agrega un archivo para cargar...',
		errorText:'Error',
		fileDoneText:'Archivo <b>{0}</b> ha sido cargado exitósamente',
		fileFailedText:'No se pudo cargar el archivo <b>{0}</b>',
		fileQueuedText:'Esperando a cargar el archivo <b>{0}</b>',
		fileStoppedText:'Archivo <b>{0}</b> detenido por el usuario',
		fileUploadingText:'Cargando archivo <b>{0}</b>',
		removeAllText:'Eliminar todo',
		removeText:'Eliminar',
		stopAllText:'Detener todo',
		uploadText:'Cargar',
		singleUpload: true,
		maxFileCount: 2
	});


	var winEdit = new Ext.Window({
        title	   : 'Editar Configuraci&oacute;n P&aacute;gina Principal',
        width	   : 678,
        //autoHeight : true,
        height	   : 500,
        layout	   : 'form',
        modal	   : true,
        plain	   : true,
        draggable  : false,
        resizable  : false,
        bodyStyle  : 'padding:5px;',
        buttonAlign: 'center',
        //items	   : editarForm,
        items: [
					claveConfiguracion,
					dsSistemaRol,
					dsElemento,	
					claveSistemaRol,
					hiddenSeccionCombo,
					hiddenTipoCombo,
					seccionCombo,
					tipoCombo,
					habilitarCheck,
					nomArchivo,
					//panelUpload,
					claveElemento,
					htmledit,
					dsConfiguracion
        ],
        buttons: [
        			{
        			text: 'Guardar', 
            		handler: function() {

							//panelUpload.show();
            			var _url =  'claveConfiguracion=' + claveConfiguracion.getValue() + '&' + 
												'claveSistemaRol=' + claveSistemaRol.getValue() + '&' +
												'claveElemento=' + claveElemento.getValue() + '&' +
												'dsConfiguracion=' + dsConfiguracion.getValue() + '&' +
												'dsSistemaRol=' + dsSistemaRol.getValue() + '&' +
												'dsElemento=' + dsElemento.getValue() + '&' +
												'texto=' + htmledit.getValue() + '&' +
												'claveSeccion=' + hiddenSeccionCombo.getValue() + '&' +
												'comboSeccion=' + seccionCombo.getValue() + '&' +
												'chkHabilitado=' + habilitarCheck.getValue() + '&' +
												'dsArchivo=' + nomArchivo.getValue() + '&' +
												'tipo=' + tipoCombo.getValue();
							
																			
								var _url =  {claveConfiguracion: claveConfiguracion.getValue(), 
												claveSistemaRol: claveSistemaRol.getValue(),
												claveElemento: claveElemento.getValue(),
												dsConfiguracion: dsConfiguracion.getValue(),
												dsSistemaRol: dsSistemaRol.getValue(),
												dsElemento: dsElemento.getValue(),
												texto: htmledit.getValue(),
												claveSeccion: hiddenSeccionCombo.getValue(),
												comboSeccion: seccionCombo.getValue(),
												chkHabilitado: habilitarCheck.getValue(),
												dsArchivo: nomArchivo.getValue(),
												tipo: tipoCombo.getValue(),
												nombreUpload1: archivo1,
												nombreUpload2: archivo2
								 }
								 	execConnection (_ACTION_GUARDARJSON, _url, function(_success, _message){
									
									if (_success) {
										Ext.Msg.alert('Aviso', _message, function(){
		           							winEdit.hide();
		           							winEdit.destroy();
		           							store.load();
										});
									} else {
										Ext.Msg.alert('Aviso', _message);
									}
								});
	        		}
            	},{
            		text: 'Guardar y Agregar', 
            		handler: function() {
            		          // validamos si se cargara archivo o texto en el Html 
            		          var lsThmledit = htmledit.getValue();  
							  if( lsThmledit == '<br>'){
							       htmledit.reset();
							  }
							   
							  if( lsThmledit != '<br>' &&  lsThmledit != ''){
                                     nomArchivo.reset(); 
                                     archivo1 = "";
                                     archivo2 = "";
							  } 		
            		
								var _url =  {
												claveConfiguracion: claveConfiguracion.getValue(),
												claveSistemaRol: claveSistemaRol.getValue(),
												claveElemento: claveElemento.getValue(),
												dsConfiguracion: dsConfiguracion.getValue(),
												dsSistemaRol: dsSistemaRol.getValue(),
												dsElemento: dsElemento.getValue(),
												texto: htmledit.getValue(),
												claveSeccion: hiddenSeccionCombo.getValue(),
												comboSeccion: seccionCombo.getValue(),
												chkHabilitado: habilitarCheck.getValue(),
												dsArchivo: nomArchivo.getValue(),
												tipo: hiddenTipoCombo.getValue(),
												comboTipo: tipoCombo.getValue(),
												nombreUpload1: archivo1,
												nombreUpload2: archivo2
											};
								execConnection (_ACTION_GUARDARJSON, _url, function(_success, _message){
									
									Ext.Msg.alert('Aviso', _message, function () {
	           							winEdit.hide();
	           							winEdit.destroy();
	           							store.load();
									});
								});
								
	        		}
        		},
        		
        		/*
        		{
        			text: 'Cargar Archivo',
        			id:'cargaArchivo',
        			handler: function () {
        			
        			    //htmledit.reset();
        			    
        			    /*
                        if( winUploadFile != '' ){
        		           //alert('0CFile');
                           winUploadFile.close();
                        } 
                  
                        if(  popup_winUpload != '' ){
                          // alert('0CpImg')
                           popup_winUpload.close();
                        }  
        			    	  
        		        //mostrarVentanaAgregarUploadFileEdit(); 
        			   // winUploadFile.toFront();
        			
        			}
        	    
        			
        		},
        		  */
        		
        		{
            		text: 'Cancelar',
            		handler: function(){
            					winEdit.close(); 
            					//store.load();
            		}
        		}
        		]
       
    });
    winEdit.show();
    

    dataStoreSeccion.load();
    dataStoreTipo.load();
	//Inicializar datos de los elementos
	seccionCombo.setValue(record.get('dsSeccion'));
	hiddenSeccionCombo.setValue(record.get('dsSeccion'));
	
	tipoCombo.setValue(record.get('dsTipo'));
	hiddenTipoCombo.setValue(record.get('dsTipo'));
	
	//alert(record.get('habilitado'));
	habilitarCheck.setValue(record.get('habilitado'));
	htmledit.setValue(record.get('especificacion'));
	nomArchivo.setValue(record.get('dsArchivo'));
	
	tipoCombo.on('select', function(){
		
		if(this.getValue()=='ALERTAS'){
            htmledit.reset();
            htmledit.disable();
            Ext.getCmp('cargaArchivo').disable(); 
		}else{
		    htmledit.reset();
            htmledit.enable();
            Ext.getCmp('cargaArchivo').enable();
		}
		
	});
	
};



/*
function  mostrarVentanaAgregarUploadFileEdit() {
	archivo1 = "";
	archivo2 = "";
	var panelUpload = new Ext.ux.UploadPanel({
		//anchor:'30%',
		width: 300,
		autoHeight:true,
		//height: 300,
		fieldLabel:'Upload Panel',
		hidden:true,
		//url:
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
		maxFileCount: 2
	});

	var winUpload = new Ext.Window({
	    id         : 'winUpload',
        title	   : 'Cargar Archivo',
        width	   : 320,
        //autoHeight : true,
        height	   : 150,
        layout	   : 'form',
        modal	   : false,
        plain	   : false,
        draggable  : false,
        resizable  : false,
        bodyStyle  : 'padding:5px;',
        buttonAlign: 'center',
        //items	   : editarForm,
        items: [
					panelUpload
        ],
        buttons: [{
        	text: 'Regresar',
        	handler: function () {
        	    winUploadFile = '';
        		winUpload.close();
        	}
        }]
	});
	
	winUploadFile = Ext.getCmp('winUpload');
	winUpload.show();
	panelUpload.show();
	winUpload.toFront();
	
	panelUpload.setUrl(_ACTION_UPLOAD + '?cmd=upload');
	panelUpload.uploadCallback = function (_success, _response) {
		//alert("dentro del callback: " + _success);
		if (_success) {
			winUpload.close();
			mostrarVentanaAgregarUpload2();
			var fileList = Ext.util.JSON.decode(_response.responseText).fileList;

			if (fileList.length > 1) {
				archivo1 = fileList[0];
				archivo2 = fileList[1];
			}else {
				archivo1 = fileList[0];
			}
		}
	}
}
*/

function mostrarVentanaUploadImgEdit() {
	archivo1 = "";
	archivo2 = "";
	var panelUpload = new Ext.ux.UploadPanel({
		//anchor:'30%',
		width: 300,
		autoHeight:true,
		//height: 300,
		fieldLabel:'Upload Panel',
		hidden:true,
		//url:
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
		maxFileCount: 1
	});

	var winUpload = new Ext.Window({
	    id         : 'winUpload',
        title	   : 'Cargar Archivo de tipo Imagen',
        width	   : 320,
        //autoHeight : true,
        height	   : 150,
        layout	   : 'form',
        modal	   : false,
        plain	   : true,
        draggable  : false,
        resizable  : false,
        bodyStyle  : 'padding:5px;',
        buttonAlign: 'center',
        toFront : function(){
                      this.focus();
                      return this;
                   },
        
        
        //items	   : editarForm,
        items: [
					panelUpload
        ],
        buttons: [{
        	text: 'Regresar',
        	handler: function () {
        		winUpload.close();
        	}
        }]
	});
	
	popup_winUpload = Ext.getCmp('winUpload'); 
	winUpload.show();
	panelUpload.show();
    winUpload.toFront(); 
	
	panelUpload.setUrl(_ACTION_UPLOAD + '?cmd=upload');
	panelUpload.uploadCallback = function (_success, _response) {
		//alert("dentro del callback: " + _success);
		if (_success) {
			winUpload.close();
			popup_winUpload = '';
			mostrarVentanaAgregarUpload2();
			var fileList = Ext.util.JSON.decode(_response.responseText).fileList;

			if (fileList.length > 1) {
				archivo1 = fileList[0];
				archivo2 = fileList[1];
				
				
				nameFile1  =  archivo1;
			    var ruta = _RUTA_PUB_IMG + nameFile1; 
			    //alert( 'ruta1='+  ruta );
				editor.relayCmd('insertimage', ruta );
				
			}else {
				archivo1 = fileList[0];
				
				nameFile1 = fileList[0];
				var ruta = _RUTA_PUB_IMG + nameFile1; 
				//alert( 'ruta2='+  ruta );
				editor.relayCmd('insertimage', ruta );
				
			}
		}
	}
}



//elemento creado para cierre de terminacion de carga de archivo
function mostrarVentanaAgregarUpload2 () {
	
	var panelUpload = new Ext.ux.UploadPanel({
		//anchor:'30%',
		width: 300,
		autoHeight:true,
		//height: 300,
		fieldLabel:'Upload Panel',
		hidden:true,
		//url:
		addText:'',
		clickRemoveText:'Click para eliminar de la lista',
		clickStopText:'Click para detener',
		emptyText:'Archivo ha sido cargado exitósamente',
		errorText:'Error',
		fileDoneText:'Archivo <b>{0}</b> ha sido cargado exitósamente',
		fileFailedText:'No se pudo cargar el archivo <b>{0}</b>',
		fileQueuedText:'Esperando a cargar el archivo <b>{0}</b>',
		fileStoppedText:'Archivo <b>{0}</b> detenido por el usuario',
		fileUploadingText:'Cargando archivo <b>{0}</b>',
		removeAllText:'Eliminar todo',
		removeText:'Eliminar',
		stopAllText:'Detener todo',
		uploadText:'',
		singleUpload: true,
		maxFileCount: 0
	});

	var winUpload = new Ext.Window({
        title	   : 'Cargar Archivo',
        width	   : 320,
        //autoHeight : true,
        height	   : 150,
        layout	   : 'form',
        //modal	   : true,
        resizable  : false,
        bodyStyle  : 'padding:5px;',
        buttonAlign: 'center',
        //items	   : editarForm,
        items: panelUpload,
        buttons: [{
        	text: 'OK',
        	handler: function () {
        		winUpload.close();
        	}
        }]
	});
	winUpload.show();
	panelUpload.show();
}


/* override del metodo enable y disable */
Ext.override(Ext.form.HtmlEditor, {
	onDisable: function(){
		if(this.rendered){
			this.wrap.mask();
		}
		Ext.form.HtmlEditor.superclass.onDisable.call(this);
	},
	onEnable: function(){
		if(this.rendered){
			this.wrap.unmask();
		}
		Ext.form.HtmlEditor.superclass.onEnable.call(this);
	}
});


	




