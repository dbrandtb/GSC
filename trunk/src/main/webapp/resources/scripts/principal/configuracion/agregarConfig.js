var archivo1 = "";
var archivo2 = "";
var selection = '';
var rangoSelection ;
var htmlEditLink;

function agregarConfig (store){ 

nameFile1 = '';
popup_window = '';
popup_winUpload = '';  
//winUploadFile ='';
editor;

	/*Store que carga el combo de Secciones*/
	
	var dataStoreSeccion = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url:'principal/seccionAction.action'
		}),
		reader: new Ext.data.JsonReader({
			root:'secciones',
			id:'sec'
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
			root:'tipos',
			id:'tip'
		},[
			{name:'clave', 	type:'string', 	mapping:'clave'},
			{name:'valor', 	type:'string', 	mapping:'valor'}
		]),
		remoteSort:false
	});
	dataStoreTipo.setDefaultSort('valor','desc');
	
	/*atributos definidos por default*/
	
	
	var claveConfiguracion = new Ext.form.Hidden({	
		name :'claveConfiguracion',
		width: 200,
		value:_CVECONF
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
				id:'id-fileLoad',
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
	
	var seccionCombo = new Ext.form.ComboBox({ 
				id:'id-seccion-combo-configuracion',
				tpl: '<tpl for="."><div ext:qtip="{dsSeccion}. {cdSeccion}" class="x-combo-list-item">{dsSeccion}</div></tpl>',
    			store: dataStoreSeccion,
				width: 300,
    			mode: 'local',
				name: 'claveSeccion',
				allowBlank:false,
    			typeAhead: true,
				labelSeparator:'',			
    			triggerAction: 'all',
    			forceSelection: true,    		
    			displayField:'dsSeccion',
				fieldLabel: 'Seccion',
				emptyText:'Seleccionar sección...',
				selectOnFocus:true
		});
	var habilitarCheck = new Ext.form.Checkbox({
				fieldLabel:'Habilitado',
                name:'habilitar',             	
                checked:false,
                onClick:function(){
          			if(this.getValue()){                 
                  		this.setRawValue('1');                 
                 		}else{
                  		this.setRawValue('0');            
                 		}
                } 
        });		
	var especificacion = new Ext.form.TextField({
				fieldLabel:'Especificacion',
				name:'especific',
				anchor:'70%'
		});
	var contenido = new Ext.form.TextField({
				fieldLabel:'Contenido',
				name:'content',
				anchor:'90%',
				//disabled:true
				readOnly:true
		});
	var tipoCombo = new Ext.form.ComboBox({ 
				id:'id-tipo-combo-configuracion',
				tpl: '<tpl for="."><div ext:qtip="{valor}.{clave}" class="x-combo-list-item">{valor}</div></tpl>',
    			store: dataStoreTipo,
				width: 300,
    			mode: 'local',
				name: 'tipo',
    			typeAhead: true,
				labelSeparator:'',			
    			triggerAction: 'all',    		
    			displayField:'valor',
				fieldLabel: 'Tipo',
				allowBlank: false,
				emptyText:'Seleccionar tipo...',
				selectOnFocus:true
		});
		
	tipoCombo.on('select', function(){
		
		if(this.getValue()=='ALERTAS'){
            htmledit.reset();
            htmledit.disable();
            //Ext.getCmp('cargaArchivo').disable(); 
		}else{
		    htmledit.reset();
            htmledit.enable();
           // Ext.getCmp('cargaArchivo').enable();
		}
		
	});
	
	
	var agregarForm = new Ext.form.FormPanel({
				id : 'carga',
				url : 'principal/guardarAction.action',
				fileUpload : true,
				boder : false,
				frame : true,
				method : 'post',            	            
        		width : 570,
        		buttonAlign : 'center',
				baseCls : 'x-plain',
				labelWidth : 75,
				
				items:[				
					claveConfiguracion,
					dsSistemaRol,
					dsElemento,	
					claveSistemaRol,
					seccionCombo,
					tipoCombo,
					habilitarCheck,
					//especificacion,
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

	var window = new Ext.Window({
	    id         : 'window',
        title	   : 'Agregar Configuraci&oacute;n P&aacute;gina Principal',
        width	   : 678,
        //autoHeight : true,
        height	   : 500,
        layout	   : 'form',
        modal	   : false,
        plain	   : true,
        draggable  : true,
        resizable  : false,
        bodyStyle  : 'padding:5px;',
        buttonAlign: 'center',
        //items	   : agregarForm,
				items:[				
					claveConfiguracion,
					dsSistemaRol,
					dsElemento,	
					claveSistemaRol,
					seccionCombo,
					tipoCombo,
					habilitarCheck,
					//especificacion,
					//fileLoad,
					//fileTwo,
					//panelUpload,
					claveElemento,
					htmledit,
					dsConfiguracion
				],
        buttons: [{
        			text: 'Guardar', 
        			tooltip:'Guarda una nueva Configuraci&oacute;n',
            		handler: function() {
            		if (agregarForm.form.isValid()) {
            			var bolean1 = true; 
            			var seccion = Ext.getCmp('id-seccion-combo-configuracion').getValue();
            			if(seccion.length==0 || seccion == 'Seleccionar secci&oacute;n...'){
            				bolean1 = false;
            			}
            			var boolean2=true;
            			var tipo = Ext.getCmp('id-tipo-combo-configuracion').getValue();
            			if(tipo.length==0 || tipo=='Seleccionar tipo...'){
            				boolean2 = false;
            			}
            		
            			if(fileLoad.getValue()==''){
            				fileLoad.disable();
            			}
            			if(fileTwo.getValue()==''){
            				fileTwo.disable();
            			}
            			if(bolean1 && boolean2){
					
								var _url =  {claveConfiguracion: claveConfiguracion.getValue(), 
											dsSistemaRol: dsSistemaRol.getValue(),
											dsElemento: dsElemento.getValue(),
											claveSistemaRol: claveSistemaRol.getValue(),
											claveSeccion: seccionCombo.getValue(),
											tipo: tipoCombo.getValue(),
											chkHabilitado: habilitarCheck.getValue(),
											claveElemento: claveElemento.getValue(),
											texto: htmledit.getRawValue(),
											dsConfiguracion: dsConfiguracion.getValue(),
											nombreUpload1: archivo1,
											nombreUpload2: archivo2
								}
								execConnection (_ACTION_AGREGARJSON, _url, function(_success, _message){
									if (_success) {
										Ext.Msg.alert('Aviso', _message, function(){
		           							window.hide();
		           							window.destroy();
		           							store.load();
										});
									} else {
										Ext.Msg.alert('Aviso', _message);
									}
								});
				
   							
            			}else{
            				Ext.MessageBox.alert('Error','Inserte los parametros requeridos');
            			}
            		}else{
            			Ext.MessageBox.alert('Aviso', 'Por favor complete la informaci&oacute;n requerida');
            		}
            			
	        		}        	
            	},{
            		text: 'Guardar y Agregar', 
            		tooltip:'Guarda y Agrega una nueva Configuraci&oacute;n',
            		handler: function() {
					if (agregarForm.form.isValid()) {
            			var boleanito1 = true; 
            			var seccion = Ext.getCmp('id-seccion-combo-configuracion').getValue();
            			if(seccion.length==0 || seccion == 'Seleccionar secci&oacute;n...'){
            				boleanito1 = false;
            			}
            			var booleanito2=true;
            			var tipo = Ext.getCmp('id-tipo-combo-configuracion').getValue();
            			if(tipo.length==0 || tipo=='Seleccionar tipo...'){
            				booleanito2 = false;
            			}
            			if(fileLoad.getValue()==''){
            				fileLoad.disable();
            			}
            			if(fileTwo.getValue()==''){
            				fileTwo.disable();
            			}
            			if(boleanito1 && booleanito2){
						
								var _url =  {
												claveConfiguracion: claveConfiguracion.getValue(), 
												dsSistemaRol: dsSistemaRol.getValue(),
												dsElemento: dsElemento.getValue(),
												claveSistemaRol: claveSistemaRol.getValue(),
												claveSeccion: seccionCombo.getValue(),
												tipo: tipoCombo.getValue(),
												chkHabilitado: habilitarCheck.getValue(),
												claveElemento: claveElemento.getValue(),
												texto: htmledit.getValue(),
												dsConfiguracion: dsConfiguracion.getValue(),
												nombreUpload1: archivo1,
												nombreUpload2: archivo2
								};
								execConnection (_ACTION_AGREGARJSON, _url, function(_success, _message){
									if (_success) {
										Ext.Msg.alert('Aviso', _message, function(){
		           							window.hide();
		           							window.destroy();
		           							store.load();
		           							agregarConfig(store);
										});
									} else {
										Ext.Msg.alert('Aviso', _message);
									}
								});
						
            			}else{
            				Ext.MessageBox.alert('Error','Inserte los parametros requeridos');
            			}
            		}else{
            			Ext.MessageBox.alert('Aviso', 'Por favor complete la informaci&oacute;n requerida');
            		}	
	        		}
        		}
        		
        		/*
        		,{
        			text: 'Cargar Archivo',
        			id:'cargaArchivo',
        			tooltip:'carga Archivo',
        			handler: function () {
        			  
        			  
        		if( winUploadFile != '' ){
        		 // alert('0CFile');
                  winUploadFile.close();
                }  
        			
                if(  popup_winUpload != '' ){
                 // alert('0CpImg')
                  popup_winUpload.close();
                }  
        			    	  
        			  mostrarVentanaAgregarUploadFile ();
        			  winUploadFile.toFront();
        			  
        			}
        		}
        		
        		*/
        		,{
            		text: 'Cancelar',
            		tooltip:'Cancelar Configuraci&oacute;n',
            		handler: function(){window.close(); store.load();}
        		}]
       
    });
    window.show();
    popup_window = Ext.getCmp('window'); 
    dataStoreSeccion.load();
    dataStoreTipo.load();
    
    
};


function mostrarVentanaAgregarUploadImag () {
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
		maxFileCount: 1
	});

	var winUpload = new Ext.Window({
	    id         : 'winUpload',
        title	   : 'Cargar Archivo de tipo imagen',
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
        toFront : function(){
                      this.focus();
                      return this;
                   },
        buttons: [{
        	text: 'Regresar',
        	handler: function () {
        		winUpload.close();
        		popup_winUpload = '';
      
        	}
        }]
	});
	
	popup_winUpload = Ext.getCmp('winUpload'); 
	winUpload.show();
	panelUpload.show();
	winUpload.toFront(); 
	
	
	panelUpload.setUrl(_ACTION_UPLOAD + '?cmd=upload');
	panelUpload.uploadCallback = function (_success, _response) {
		
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
			
			
		// Ext.MessageBox.alert('Aviso','Archivo termino de cargarse');	
			
		}
	}
}


/*
function mostrarVentanaAgregarUploadFile () {
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
		maxFileCount: 1
	});

	var winUpload = new Ext.Window({
	    id         : 'winUpload',
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
		
		if (_success) {
			winUpload.close();
			mostrarVentanaAgregarUpload2();
		    
			
			var fileList = Ext.util.JSON.decode(_response.responseText).fileList;

			if (fileList.length > 1) {
				archivo1 = fileList[0];
				archivo2 = fileList[1];
				
				nameFile1  =  archivo1;
			 
			    
			}else {
				archivo1 = fileList[0];
				nameFile1 = fileList[0];
	
			}	
			
			
		// Ext.MessageBox.alert('Aviso','Archivo termino de cargarse');	
			
		}
	}
}

*/

 function  createLinkOption() {
 
  var  fileName ='';      
  var  idLink  = 0;
      
  
      var createLinkText = new Ext.form.TextField({
        id:'createLinkText',
		fieldLabel:'URL',
		name:'createLinkText',
		width:380
	   });
      
       
	   var panelUpload = new Ext.ux.UploadPanel({
		width: 300,
		autoHeight:true,
		fieldLabel:'Upload Panel',
		hidden:true,
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
		maxFileCount: 1
	  });
	  
	  //metodo para guardar 
	  
	  panelUpload.setUrl(_ACTION_UPLOAD + '?cmd=upload');
	  panelUpload.uploadCallback = function (_success, _response) {
		
	     if (_success) {
	        var fileList = Ext.util.JSON.decode(_response.responseText).fileList;

		    if (fileList.length > 0) {
		       fileName = fileList[0];
		    }
	     }else{
	        Ext.Msg.alert("Error","Archivo no cargado");
	     }
	  }
	 
	      
       	windowLink = new Ext.Window({
            	title: 'Hyperlink',
            	id:'windowLink',
            	width:550,
            	height:175,
            	resizable: false,
            	toFront : function(e){      
                          this.focus();
                          return this;
                }
                ,
            	closeAction:'hide',
            	plain: true,
            	modal: false,
            	items: fmExport = new Ext.FormPanel({
            		   labelWidth: 120,
            		   defaultType: 'textfield',
					   baseCls: null,
					   bodyStyle:'padding: 5px 10px 0',
					   items: [ 
					      cbxFormat = new Ext.form.ComboBox({  
							id:'comboLink',
							fieldLabel: 'Tipo Link',
							store: new Ext.data.SimpleStore({
							fields: ['IdLink', 'desc'],
							data : [
							// Elementos del combobox con los formatos a ser exportados
							['1', 'http'],
							['2', 'link para archivo']]
							}),
							valueField:'IdLink',
							displayField:'desc',
							typeAhead: true,
							mode: 'local',
							forceSelection: true,
							triggerAction: 'all',
							emptyText:'Seleccione un Formato...',
							selectOnFocus:true,
							onSelect : function(record){
							    idLink = record.get("IdLink");
							    Ext.getCmp("comboLink").setValue(idLink);
                                                 
                                if( idLink ==  1 ){
                                   createLinkText.enable();
                                   createLinkText.focus();
                                }
                                
                                if(idLink ==  2){
                                    panelUpload.show();
                                    createLinkText.disable();
                                }
                                
                                this.collapse();
                              
							},
							onViewClick: function() {
                                    Ext.form.ComboBox.prototype.onViewClick.apply(this, arguments);
                                    createLinkText.focus();
                                    //alert( idLink );
                                      if(idLink ==  2){
                                         createLinkText.disable();
                                      }
                                      
                                      if( idLink ==  1 ){
                                         //  panelUpload.hidden();
                                      }
                                      
                                        this.collapse();
                            }
		
											    	
							}),// cierra combo 
							createLinkText,
							panelUpload
							]/// cierra elements
							
							,
							buttons: [
							    btnExp =   {
	        							      text:'Guardar',
	        							      handler: function(){

	        							         if( idLink == 1 ){
	        							          var url = Ext.getCmp("createLinkText").getValue();
	                                              createlinkHttp( htmlEditLink ,url ,selection , rangoSelection );
	        							         }  
	        
	        							         if( idLink == 2 ){
	                                                //validar que se aya cargado archivo
	                                                
	                                                //alert('fileName='+ fileName);
	                                                
	                                                if(fileName != ''){
	                                                	
	                                                    	
	                                                   var url = '\/resources\/';
	                                                    // var diagonal= "/"
	                                                     //var url =  _RUTA_PUB_IMG.replace(/diagonal/g,'\/');  
	                                                     //alert(url);
	                                                   createlinkFile( htmlEditLink  ,url , selection,rangoSelection ,fileName );

	                                                }else{
	                                                  Ext.Msg.alert("Error", "Debe de cargar un archivo.");
	                                                }						          
	        							         }
	        							            windowLink.close();
	        								  }
	        								},
	        								{
	        						           text: 'Cancel',
	        						           handler: function(){
	        		                                 windowLink.close();
	        						           }
	        					            }
	        					      ]
							
               })  // cierra elementos Item       
      })   // cierra windows 
     
     windowLink.show();
    // windowExport.toFront();  
  }    


 function createlinkFile( objEdit ,url , TextSelected , rango ,nameFile ) {
   
          var urlFile = url + nameFile;
          
          if ( objEdit.win.getSelection) {    // Mozilla Model
            html =  "<a    onClick = openWind(\'"+ urlFile +"\') >" + TextSelected  + "</a>"; 
            objEdit.insertAtCursor( html );
          }else if ( objEdit.doc.selection) {    // IE Model
            html =  "<a   onClick = openWind(\'"+ urlFile +"\') >" + TextSelected  + "</a>"; 
            rango.pasteHTML(html); 
          }
   }
  
   function createlinkHttp( objEdit ,url , TextSelected , rango ) {
   
        if (  !url.match(/^http(s)*:\/\//) )
        {
          Ext.Msg.alert("Url Error", "La URL [" + url +"]  no es valida.");
          return;
        }else{
          if ( objEdit.win.getSelection) {    // Mozilla Model
            html =  "<a    onClick = openWind(\'"+ url +"\') >" + TextSelected  + "</a>"; 
            objEdit.insertAtCursor(  html );
          }else if ( objEdit.doc.selection) {    // IE Model
            html =  "<a    onClick = openWind(\'"+ url +"\') >" + TextSelected  + "</a>"; 
            rango.pasteHTML(html); 
          }
        } 
   }
     














