<%@ include file="/taglibs.jsp"%>

/*************************************************************
** FormPanel del TAB 2
**************************************************************/ 

	var storeComboRolXNivel = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: "<s:url action='obtenerComboRolUsuario' namespace='/combos'/>"
		}),
		reader: new Ext.data.JsonReader({
			root:'comboRolUsuario'
		},[
			{name:'cdSisRol', 	type:'string', 	mapping:'cdSisRol'},
			{name:'dsSisRol', 	type:'string', 	mapping:'dsSisRol'}
		])
	});

var formPanelDatos = new Ext.form.FormPanel({
                        id:'formPanelDatos',
                        //el: 'divFormPanelDatos',
                        //border:false,
                        labelWidth: 140,
                        width: 800,
                        autoHeight: true,
                        layout:'column',
                       	layoutConfig: {columns: 1, columnWidth: 1.0},
                       	border: false,
						items: [/*{
                        	layout: 'column',
                        	layoutConfig: {columns: 1, columnWidth: 1.0},
                        	border: false,
	                        items: [*/{
	                                border:false,
	                                layout:'form',
	                                //colspan: 3,
	                                //columnWidth: 1,
	                                width:800,
	                                items:[cdPantalla, {xtype: 'hidden', name: 'dsCampos', id: 'dsCampos'}, {xtype: 'hidden', name: 'dsLabels', id: 'dsLabels'}]
	                               },{    
	                                border:false,
	                                layout:'form',
	                                //colspan: 3,
	                                //columnWidth: 1,
	                                width:800,
	                                items:[nombrePantalla]
	                              },{    
	                                border:false,
	                                layout:'form',
	                                //columnWidth: 0.9,
	                                //colspan: 3,
	                                //columnWidth: 1,
	                                width:800,
	                                items:[comboTipoPantalla]
	                              },{    
	                                border:false,
	                                layout:'form',
	                                //colspan: 3,
	                                //columnWidth: 1,
	                                width:800,
	                                items:[descripcionPantalla]
	                              },{ 
	                                border:false,
	                                layout:'form',
	                                //columnWidth: 1,
	                                //height:0.5,  
	                                items:[cdProceso]
	                              },{    
	                                border:false,
	                                layout:'form',
	                                //columnWidth: 1,
	                                //height:0.5,
	                                items:[cdProducto]
	                              },{    
	                                border:false,
	                                layout:'form',
	                                //columnWidth: 1,
	                                //height:0.5,
	                                items:[cdTipoMaster]
	                              },{    
	                                border:false,
	                                layout:'form',
	                                //columnWidth: 1,
	                                //height:0.5,
	                                items:[tipoMaster]
	                              },{   
	                                border:false,
	                                layout:'table',
	                                //height:0.5,
	                                //columnWidth: 1,
	                                items:[dsArchivo]
	                              },{   
	                                border:false,
	                                layout:'table',
	                                //columnWidth: 1,
	                                items:[claveSituacion]
	                              }, {
	                              	border: false,
	                              	layout: 'form',
	                              	width: 800,
	                              	items: [
			                              { 
														//id:'id-seccion-combo-configuracion',
														tpl: '<tpl for="."><div ext:qtip="{cdSisRol}. {dsSisRol}" class="x-combo-list-item">{dsSisRol}</div></tpl>',
										    			store: storeComboRolXNivel,
														width: 250,
														listWidth: 250,
										    			mode: 'local',
														name: 'comboRol',
														id: 'comboRol',
														hiddenName: 'cdRol',
														allowBlank:false,
										    			typeAhead: true,
														labelSeparator:'',			
										    			triggerAction: 'all',
										    			forceSelection: true,    		
										    			displayField:'dsSisRol',
										    			valueField: 'cdSisRol',
														fieldLabel: 'Rol',
														emptyText:'Seleccionar ...',
														selectOnFocus:true,
														xtype: 'combo'
											}
	                              	]
	                              }
	                              
	                               
	                           /*]//end items FormPanel
                        }*/],
                       //layout:'column',
                       //layoutConfig: {columns: 1},
                        //margins:'5 5 0 5',
                        buttons:[{
                                 text: TAB_2_BOTON_SALVAR,
                                 id:'btnSalvarPantalla',
                                 tooltip: 'Guarda la configuraci&oacute;n de pantalla',
								 handler: function() {
                                        if (formPanelDatos.form.isValid()) {
                                                //Obtenemos el codigo JSON generado en el editor y lo almacenamos en 'dsArchivo'
                                                //getTreeConfig() y encode son metodos declarados en main.js
                                                //alert("Descripcion: "+Ext.getCmp('descripcion-textarea').getValue());
                                                var cleanConfig = main.getTreeConfig();
                                                cleanConfig = (cleanConfig.items?cleanConfig.items[0]||{}:{});
                                                cleanConfig = Main.JSON.encode(cleanConfig);
                                                dsArchivo.setValue(cleanConfig);
                                                
                                                /**
                                                *	Mapa con los caracteres acentuados. Sirve para enviar
                                                *	los caracteres escapeados a la bd.
                                                */
                                                /*
                                                var charMap = new Map();
                                                charMap.put('á', '&aacute;');
                                                charMap.put('é', '&eacute;');
                                                charMap.put('í', '&iacute;');
                                                charMap.put('ó', '&oacute;');
                                                charMap.put('ú', '&uacute;');
                                                charMap.put('ñ', '&ntilde;');
                                                charMap.put('Ñ', '&Ntilde;');
                                                charMap.put('Á', '&Aacute;');
                                                charMap.put('É', '&Eacute;');
                                                charMap.put('Í', '&Iacute;');
                                                charMap.put('Ó', '&Oacute;');
                                                charMap.put('Ú', '&Uacute;');
												*/
												
												var stringPantalla = dsArchivo.getValue();

												var arreglo1 = busca(stringPantalla, 'id:"');
												var arreglo2 = busca(stringPantalla, 'name:"');
												if ( repetidos(arreglo1) && repetidos(arreglo2) ) {
													Ext.MessageBox.alert(TAB_2_BOTON_SALVAR_ERROR, TAB_2_BOTON_SALVAR_ERROR_ELEM_REPETIDO);
												} else {

                                                /*
                                                for (var i=0; i<stringPantalla.length; i++) {
                                                	var ch = stringPantalla.charAt(i);
                                                	var cd = String.escape(ch);
                                                	//if (ch == 'á') {
                                                	if (charMap.get(ch)) {
                                                		stringPantalla[i] = charMap.get(ch);
                                                		var strTemp = stringPantalla.substring(0, i);
                                                		strTemp += charMap.get(ch);
                                                		stringPantalla = strTemp + stringPantalla.substring(i+1, stringPantalla.length);
                                                	} 
                                                }
                                                */
                                                dsArchivo.setValue(stringPantalla);

												var currentComboPantallaValue = comboTipoPantalla.getValue();					
                                                var currentRecord;
                                                
                                                for( index =0; index < storeTipoPantalla.getTotalCount(); index ++  ){

                                                	currentRecord = storeTipoPantalla.getAt(index);
                                                	if (currentRecord) {
	                                                	if( currentComboPantallaValue == currentRecord.get('cdMaster') ){
	                                                		tipoMaster.setValue( currentRecord.get('cdTipo') );
	                                                		index = storeTipoPantalla.getTotalCount() + 1;
	                                                	}                                                	
                                                	}
                                                }
                                                
                                                var elementos = (main.getTreeConfig().items)?main.getTreeConfig().items[0] || []:[];
                                                if (elementos.items) {
	                                                Ext.each (elementos.items, function (campito) {
	                                                		if (campito) {
		                                                		if (campito.name) {
		                                                			var _index = campito.name.indexOf('.'); //Ext.getCmp('dsCampos').getValue().indexOf('.');
		                                                			if (_index != -1) {
		                                                				Ext.getCmp('dsCampos').setValue(Ext.getCmp('dsCampos').getValue() + '|' + campito.name.substring(_index + 1));
		                                                			} else {
		                                                				Ext.getCmp('dsCampos').setValue(Ext.getCmp('dsCampos').getValue() + '|' + campito.name);
		                                                			}
		                                                		}
		                                                		if (campito.fieldLabel) {
		                                                			Ext.getCmp('dsLabels').setValue(Ext.getCmp('dsLabels').getValue() + '|' + campito.fieldLabel);
		                                                		}
		                                                		if (campito.items) {
		                                                			Ext.each(campito.items, function (subcampito) {
				                                                		if (subcampito.name) {
				                                                			var _index = subcampito.name.indexOf('.'); //Ext.getCmp('dsCampos').getValue().indexOf('.');
				                                                			if (_index != -1) {
				                                                				Ext.getCmp('dsCampos').setValue(Ext.getCmp('dsCampos').getValue() + '|' + subcampito.name.substring(_index + 1));
				                                                			} else {
				                                                				Ext.getCmp('dsCampos').setValue(Ext.getCmp('dsCampos').getValue() + '|' + subcampito.name);
				                                                			}
				                                                		}
				                                                		if (subcampito.fieldLabel) {
				                                                			Ext.getCmp('dsLabels').setValue(Ext.getCmp('dsLabels').getValue() + '|' + subcampito.fieldLabel);
				                                                		}
		                                                			});
		                                                		}
	                                                		}
	                                                });
                                                }

                                                //////////////////////////////////////////////////////////////////////////////
                                                //////////////////////////////////////////////////////////////////////////////
                                                //////////////////////////////////////////////////////////////////////////////
                                                
                                                formPanelDatos.form.submit({
                                                    //url:'confpantallas/salvarPantalla.action',
                                                    url:'configuradorpantallas/salvarPantalla.action',
                                                    
                                                    waitMsg: TAB_2_BOTON_SALVAR_WAIT_MSG,
                                                    failure: function(form, action) {
                                                        Ext.MessageBox.alert(TAB_2_BOTON_SALVAR_FAILURE, action.result.actionErrors[0]/*TAB_2_BOTON_SALVAR_FAILURE_DESC*/);
                                                    },
                                                    success: function(form, action) {
                                                        Ext.MessageBox.alert(TAB_2_BOTON_SALVAR_SUCCESS, action.result.actionMessages[0], function () {
	                                                    	var _cdPantalla = action.result.cdPantalla; 
	                                                    	seleccionaPantallaCreada (_cdPantalla);
                                                        });
                                                        var treeN = Ext.getCmp('treeNavegacion');  
                                                        treeN.root.reload();
                                                        comboTipoPantalla.disable();
                                                        comboTipoPantalla.setHeight(25);//Para corregir el error cuando el combo se oculta inesperadamente
                                                       
                                                        //alert('cargando treeProcesosMaster');
                                                        var treeMasterTest = Ext.getCmp('treeProcesosMaster');
                                                        
		treeMasterTest.loader.baseParams['cdTipoMaster'] = Ext.getCmp('cdTipoMaster').getValue();
		treeMasterTest.loader.baseParams['cdProceso'] = Ext.getCmp('cdProceso').getValue();
		treeMasterTest.loader.baseParams['tipoMaster'] = Ext.getCmp('tipoMaster').getValue();
		treeMasterTest.loader.baseParams['cdProducto'] = Ext.getCmp('cdProducto').getValue();
		treeMasterTest.loader.baseParams['claveSituacion'] = Ext.getCmp('claveSituacion').getValue();

                                                        
                                                        treeMasterTest.root.reload();
                                                         
                                                        
                                                        //TODO: REVISAR COMO HACER EL RELOAD SIN CERRAR LA VENTANA
                                                        //window.location.reload(true)

                                                    }
                                                 });
											} // end else

                                        } else{
                                             Ext.MessageBox.alert(TAB_2_BOTON_SALVAR_ERROR, TAB_2_BOTON_SALVAR_ERROR_DESC);
                                        }
                                    }      //end handler  
                                   
                                 },{
                                 text: TAB_2_BOTON_ELIMINAR,
                                 id:'btnEliminar',
                                 tooltip: 'Elimina la pantalla',
                                 handler: function() { 
                                 Ext.MessageBox.buttonText.ok = TAB_2_BOTON_ELIMINAR_MSGBOX_BOTON_OK;
                                 Ext.MessageBox.buttonText.cancel = TAB_2_BOTON_ELIMINAR_MSGBOX_BOTON_CANCEL;
                                 Ext.Msg.show({
                                            title: TAB_2_BOTON_ELIMINAR_MSGBOX_TITLE,
                                            msg: TAB_2_BOTON_ELIMINAR_MSGBOX_MSG,
                                            buttons: Ext.Msg.OKCANCEL,
                                            fn: procesarResultado,
                                            icon: Ext.MessageBox.QUESTION
                                    
                                });
                                 
                                 function procesarResultado (btn){
                                    if(btn=='ok'){
                                            if (formPanelDatos.form.isValid()) {
                                                formPanelDatos.form.submit({
                                                    url:'configuradorpantallas/eliminarPantalla.action',
                                                    
                                                    waitMsg: TAB_2_BOTON_ELIMINAR_MSGBOX_BOTON_OK_WAIT_MSG,
                                                    failure: function(form, action) {
                                                        Ext.MessageBox.alert(TAB_2_BOTON_ELIMINAR_MSGBOX_BOTON_OK_FAILURE, action.result.actionErrors[0]/*TAB_2_BOTON_ELIMINAR_MSGBOX_BOTON_OK_FAILURE_DESC*/);
                                                    },
                                                    success: function(form, action) {
                                                        Ext.MessageBox.alert(TAB_2_BOTON_ELIMINAR_MSGBOX_BOTON_OK_SUCCESS, action.result.actionMessages[0]/*TAB_2_BOTON_ELIMINAR_MSGBOX_BOTON_OK_SUCCESS_DESC*/);
                                                        cdPantalla.reset();
                                                        nombrePantalla.reset();
                                                        comboTipoPantalla.reset();
                                                        descripcionPantalla.reset();
                                                        cdTipoMaster.reset();
                                                        Ext.getCmp('dsCampos').setValue('');
                                                        Ext.getCmp('dsLabels').setValue('');
                                                        
                                                        // formPanelDatos.form.reset();
                                                        Ext.getCmp('btnEliminar').disable();
                                                        Ext.getCmp('btnNPantalla').disable();
                                                        Ext.getCmp('btnVistaPrevia').disable();
                                                        comboTipoPantalla.enable();
                                                        var treeN = Ext.getCmp('treeNavegacion');  
                                                        treeN.root.reload();
                                                        
                                                        ///TODO: CRERAR FUNCION PARA REUTILIZAR EL RELOAD DEL TREE MASTER
                                                        var treeMasterTest = Ext.getCmp('treeProcesosMaster');							
														treeMasterTest.loader.baseParams['cdTipoMaster'] = '';
														treeMasterTest.loader.baseParams['cdProceso'] = '';
														treeMasterTest.loader.baseParams['tipoMaster'] = '';
														treeMasterTest.loader.baseParams['cdProducto'] = '';
														treeMasterTest.loader.baseParams['claveSituacion'] = '';
														treeMasterTest.root.reload();
                                                        
														
                                                        //BORRAR LOS COMPONENTES DEL EDITOR
                                                        main.resetAll();
                                                    }
                                                 });                      
                                        } else{
                                             Ext.MessageBox.alert(TAB_2_BOTON_ELIMINAR_MSGBOX_BOTON_OK_ERROR, TAB_2_BOTON_ELIMINAR_MSGBOX_BOTON_OK_ERROR_DESC);
                                        }
                                    }else{
                                    	
                                 	}
                                 }//functionprocesarResultado
                                   }      //end handler  
                                 
                                 },{
                                 text: TAB_2_BOTON_NUEVA,
                                 id:'btnNPantalla',
                                 tooltip: 'Crea una nueva pantalla',
                                        handler: function() {
                                            Ext.getCmp('btnEliminar').disable();
                                            Ext.getCmp('btnNPantalla').disable();
                                            Ext.getCmp('btnVistaPrevia').disable();
                                              cdPantalla.reset();
                                              nombrePantalla.reset();
                                              comboTipoPantalla.reset();
                                              descripcionPantalla.reset();
                                              cdTipoMaster.reset();
                                              Ext.getCmp('dsCampos').setValue('');
                                              Ext.getCmp('dsLabels').setValue('');
                                          //  formPanelDatos.form.reset();
                                             comboTipoPantalla.enable();
                                               formPanelDatos.form.submit({ 
                                               url:'configuradorpantallas/nuevaPantalla.action'
                                            });
                                            
                                            ///TODO: CRERAR FUNCION PARA REUTILIZAR EL RELOAD DEL TREE MASTER
                                                        var treeMasterTest = Ext.getCmp('treeProcesosMaster');							
														treeMasterTest.loader.baseParams['cdTipoMaster'] = '';
														treeMasterTest.loader.baseParams['cdProceso'] = '';
														treeMasterTest.loader.baseParams['tipoMaster'] = '';
														treeMasterTest.loader.baseParams['cdProducto'] = '';
														treeMasterTest.loader.baseParams['claveSituacion'] = '';
														treeMasterTest.root.reload();
                                            
                                            
                                            //BORRAR LOS COMPONENTES DEL EDITOR
                                            main.resetAll();
                                 }//end handler
                                 },{
                                 text: TAB_2_BOTON_VISTA,
                                 id:'btnVistaPrevia',
                                 tooltip: 'Genera una vista previa de la pantalla',
                                 handler: function() { 
                                
                                               
                                                var cleanConfig = main.getTreeConfig();
                                                cleanConfig = (cleanConfig.items?cleanConfig.items[0]||{}:{});
                                                cleanConfig = Main.JSON.encode(cleanConfig);
                                                dsArchivo.setValue(cleanConfig);
    
												var stringPantalla = dsArchivo.getValue();

												var arreglo1 = busca(stringPantalla, 'id:"');
												var arreglo2 = busca(stringPantalla, 'name:"');
												if ( repetidos(arreglo1) && repetidos(arreglo2) ) {
													Ext.MessageBox.alert(TAB_2_BOTON_SALVAR_ERROR, TAB_2_BOTON_SALVAR_ERROR_ELEM_REPETIDO);
												} else {

                                                
                                                dsArchivo.setValue(stringPantalla);

												var currentComboPantallaValue = comboTipoPantalla.getValue();					
                                                var currentRecord;
                                                
                                                for( index =0; index < storeTipoPantalla.getTotalCount(); index ++  ){

                                                	currentRecord = storeTipoPantalla.getAt(index);
                                                	if (currentRecord) {
	                                                	if( currentComboPantallaValue == currentRecord.get('cdMaster') ){
	                                                		tipoMaster.setValue( currentRecord.get('cdTipo') );
	                                                		index = storeTipoPantalla.getTotalCount() + 1;
	                                                	}                                                	
                                                	}
                                                }
                                                
                                                var elementos = (main.getTreeConfig().items)?main.getTreeConfig().items[0] || []:[];
                                                if (elementos.items) {
	                                                Ext.each (elementos.items, function (campito) {
	                                                		if (campito) {
		                                                		if (campito.name) {
		                                                			var _index = campito.name.indexOf('.'); //Ext.getCmp('dsCampos').getValue().indexOf('.');
		                                                			if (_index != -1) {
		                                                				Ext.getCmp('dsCampos').setValue(Ext.getCmp('dsCampos').getValue() + '|' + campito.name.substring(_index + 1));
		                                                			} else {
		                                                				Ext.getCmp('dsCampos').setValue(Ext.getCmp('dsCampos').getValue() + '|' + campito.name);
		                                                			}
		                                                		}
		                                                		if (campito.fieldLabel) {
		                                                			Ext.getCmp('dsLabels').setValue(Ext.getCmp('dsLabels').getValue() + '|' + campito.fieldLabel);
		                                                		}
		                                                		if (campito.items) {
		                                                			Ext.each(campito.items, function (subcampito) {
				                                                		if (subcampito.name) {
				                                                			var _index = subcampito.name.indexOf('.'); //Ext.getCmp('dsCampos').getValue().indexOf('.');
				                                                			if (_index != -1) {
				                                                				Ext.getCmp('dsCampos').setValue(Ext.getCmp('dsCampos').getValue() + '|' + subcampito.name.substring(_index + 1));
				                                                			} else {
				                                                				Ext.getCmp('dsCampos').setValue(Ext.getCmp('dsCampos').getValue() + '|' + subcampito.name);
				                                                			}
				                                                		}
				                                                		if (subcampito.fieldLabel) {
				                                                			Ext.getCmp('dsLabels').setValue(Ext.getCmp('dsLabels').getValue() + '|' + subcampito.fieldLabel);
				                                                		}
		                                                			});
		                                                		}
	                                                		}
	                                                });
                                                }
												
												
												formPanelDatos.form.submit({
                                                    
                                                    url:'configuradorpantallas/vistaPreviaPantalla2.action',
                                                    
                                                    waitMsg: 'Generando Vista Previa',
                                                    failure: function(form, action) {
                                                        Ext.MessageBox.alert('Error', 'No se ha podido generar la Vista Previa');
                                                    },
                                                    success: function(form, action) {
                                                       window.open("irVistaPreviaPantalla.action", "popupVistaPrevia", "width=800,height=600,scrollbars=YES,resizable=YES");
                                                    }
                                                 });
                                               
											} // end else

                                        
                                     
                                  }//end handler
                              },{
                                // este boton el handler invoca al atributo undo...                        
                                id      : 'FBUndoBtn',
                                tooltip: 'Deshace el &uacute;ltimo cambio realizado',
                                //iconCls : 'icon-undo',
                                text    : TAB_2_BOTON_DESHACER,
                                disabled: true,
                                //tooltip : TAB_2_BOTON_DESHACER_TOOLTIP,
                                scope   : this,
                                handler :  function() {
                                    main.undo();
                                }
                            }]// end buttons FormPanel
});//end FormPanel

function busca(dsArchivo, criterio){
        var arreglo = new Array();
        var longitud = dsArchivo.length;
        var bandera = true;
        var tmp = dsArchivo;
        var i = 0;
        while( bandera ) {
            var indice = tmp.indexOf(criterio);
            if (indice != -1){
                var tmp2 = tmp.substring(indice,longitud);
                var indice2 = tmp2.indexOf('",');
                arreglo[i++] = tmp2.substring(criterio.length,indice2);
                tmp2 = tmp2.substring(indice2,tmp2.length);
                tmp = tmp2;
            } else {
                bandera = false;
            }
        }
        return arreglo;
}

function repetidos(arreglo){
        for (i = 0; i < arreglo.length; i++){
            var tmp = arreglo[i];
            if ( !Ext.isEmpty(tmp) ) {
            	for (j = i+1; j < arreglo.length; j++){
	                if ( tmp == arreglo[j] )
                    	return true;
            	}
            }
        }
        return false;
}


function seleccionaPantallaCreada (_cdPantalla) {
                              clavePantalla = _cdPantalla;
                              
                              getRegistroPantalla(clavePantalla);
                                storeRegistroPantalla.on('load', function(){
                                	
                                 if(storeRegistroPantalla.getTotalCount()==null || storeRegistroPantalla.getTotalCount()==""){
                                 
                                 }else{
                                    var recordP = storeRegistroPantalla.getAt(0);
                                    formPanelDatos.getForm().loadRecord(recordP);
                                     cdTipoMaster.setValue(recordP.get('htipoPantalla'));
				                                     Ext.getCmp('comboRol').setValue(recordP.json.cdRol);

										/*storeComboRolXNivel.load({
											params: {pv_cdelemento_i: comboNivel.getValue()},
											callback: function () {
											}
										});*/
                                        if(recordP.get('cdPantalla')==null || recordP.get('cdPantalla')=="")
                                            {
                                                 comboTipoPantalla.enable();
                                                 formPanelDatos.getForm().reset();
                                                 Ext.getCmp('btnEliminar').disable();
                                                 Ext.getCmp('btnNPantalla').disable();
                                                 Ext.getCmp('btnVistaPrevia').disable();
                                        }else{
                                                 
                                                 comboTipoPantalla.disable();
                                                 comboTipoPantalla.setHeight(25);//Para corregir el error cuando el combo se oculta inesperadamente
                                                 
                                                 Ext.getCmp('btnEliminar').enable();
                                                 Ext.getCmp('btnNPantalla').enable();
                                                 Ext.getCmp('btnVistaPrevia').enable();
                                                
                                                storeTipoPantalla.baseParams['cdProceso'] = cdProceso.getValue();
                                                storeTipoPantalla.load({
                                                
                                                        callback : function(r,options,success) {
                                                            if (!success) {
                                                             //  Ext.MessageBox.alert('Aviso','No se encontraron registros');  
                                                            	storeProducto.removeAll();
                                                            } 
                                                         //   comboTipoPantalla.setValue(recordP.get('htipoPantalla'));
                                                        }
                                                            
                                                      });
                                                      
                                               ////////////////////////////////////////////////
                                               var treeMasterTest = Ext.getCmp('treeProcesosMaster');
                                            		
                                            		var _idx = Ext.getCmp('tipoPantalla').store.find(Ext.getCmp('tipoPantalla').valueField, Ext.getCmp('tipoPantalla').getValue());
                                            		var _rec = Ext.getCmp('tipoPantalla').store.getAt(_idx);
                                                        
										treeMasterTest.loader.baseParams['cdTipoMaster'] = Ext.getCmp('cdTipoMaster').getValue();
										treeMasterTest.loader.baseParams['cdProceso'] = Ext.getCmp('cdProceso').getValue();
										treeMasterTest.loader.baseParams['tipoMaster'] = (_rec)?_rec.get('cdTipo'):Ext.getCmp('tipoMaster').getValue();
										treeMasterTest.loader.baseParams['cdProducto'] = Ext.getCmp('cdProducto').getValue();
										treeMasterTest.loader.baseParams['claveSituacion'] = Ext.getCmp('claveSituacion').getValue();
                                                        
                                               treeMasterTest.root.reload();
                                               
       
       
                                                      
                                               ///////////////////////////////////////////////       
                                       }
                                       
                                        //SI dsArchivo TRAE ELEMENTOS, LOS CARGAMOS EN EL EDITOR
                                        if(dsArchivo.getValue() != "{}"){
                                            var configuracion = null;
                                            configuracion = Ext.decode(dsArchivo.getValue());
                                            main.setConfig({items:[configuracion]});
                                        }
                                        //SINO, DEJAMOS VACIO EL EDITOR
                                        else{
                                            main.setConfig({items:[]});
                                        }
                                     }//else totalcount
                                     
                                   });                                   

}