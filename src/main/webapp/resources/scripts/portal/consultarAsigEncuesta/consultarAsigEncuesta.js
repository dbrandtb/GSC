var helpMap = new Map();

//helpMap.put('btnAddConfAlert',{tooltip:'Guardar la configuraci&oacute;n de alertas agregada'});

Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";
    var itemsPerPage=10;

	/********* Combo Usuarios Responsables *****************/
	
	 var dataStoreUsuarios = new Ext.data.Store ({
					proxy: new Ext.data.HttpProxy ({url: _ACTION_COMBO_USUARIOS}),
					reader: new Ext.data.JsonReader({
								root: 'confAlertasAutoUsuarios',
								id: 'cdUsuario',
								successProperty: '@success'
							}, [
								{name: 'cdUsuario', type: 'string', mapping: 'cdUsuario'},
								{name: 'dsUsuario', type: 'string', mapping: 'dsUsuario'} 
							])
			});
    dataStoreUsuarios.load({
    //params:{nmConfig:}
    });
   
   
	var dsUnieco = new Ext.form.TextField({
			    fieldLabel: getLabelFromMap('dsUniecoEdtId',helpMap,'Aseguradora'),
			    tooltip:getToolTipFromMap('dsUniecoEdtId',helpMap,'Aseguradora'), 
			    hasHelpIcon:getHelpIconFromMap('dsUniecoEdtId',helpMap),
			    Ayuda:getHelpTextFromMap('dsUniecoEdtId',helpMap),
				//fieldLabel:'Aseguradora',
				allowBlank:false,
				name:'dsUnieco',
				//tooltip:'Aseguradora',
				width: 200
				//anchor:'91%'
		});
		
		
	var dsRamo = new Ext.form.TextField({
				fieldLabel: getLabelFromMap('dsRamoEdtId',helpMap,'Producto'),
				tooltip:getToolTipFromMap('dsRamoEdtId',helpMap,'Producto'), 
				hasHelpIcon:getHelpIconFromMap('dsRamoEdtId',helpMap),
				Ayuda:getHelpTextFromMap('dsRamoEdtId',helpMap),
				//fieldLabel:'Producto',
				allowBlank:false,
				name:'dsRamo',
				//tooltip:'Producto',
				width: 200
				//anchor:'91%'
		});	
	
	var estado = new Ext.form.TextField({
				fieldLabel: getLabelFromMap('estadoEdtId',helpMap,'Estado'),
				tooltip:getToolTipFromMap('estadoEdtId',helpMap,'Estado'), 
				hasHelpIcon:getHelpIconFromMap('estadoEdtId',helpMap),
				Ayuda:getHelpTextFromMap('estadoEdtId',helpMap),
				//fieldLabel:'Estado',
				allowBlank:false,
				name:'estado',
				//tooltip:'Estado',
				width: 200
				//anchor:'91%'
		});
		
		var nmPoliza = new Ext.form.TextField({
		        fieldLabel: getLabelFromMap('nmPolizaEdtId',helpMap,'P&oacute;liza'),
		        tooltip:getToolTipFromMap('nmPolizaEdtId',helpMap,'P&oacute;liza'), 
		        hasHelpIcon:getHelpIconFromMap('nmPolizaEdtId',helpMap),
		        Ayuda:getHelpTextFromMap('nmPolizaEdtId',helpMap),
				//fieldLabel:'P&oacute;liza',
				allowBlank:false,
				name:'nmPoliza',
				//tooltip:'P&oacute;liza',
				width: 200
				//anchor:'91%'
		});
		
		var nombreCliente = new Ext.form.TextField({
		        fieldLabel: getLabelFromMap('nombreClientEdtId',helpMap,'Cliente'),
		        tooltip:getToolTipFromMap('nombreClientEdtId',helpMap,'Cliente'), 
		        hasHelpIcon:getHelpIconFromMap('nombreClientEdtId',helpMap),
		        Ayuda:getHelpTextFromMap('nombreClientEdtId',helpMap),
				//fieldLabel:'Cliente',
				allowBlank:false,
				name:'nombreCliente',
				//tooltip:'Cliente',
				width: 200
				//anchor:'91%'
		});

var nombreUsuario = new Ext.form.ComboBox({
                tpl: '<tpl for="."><div ext:qtip="{dsUsuari}. {cdUsuario}" class="x-combo-list-item">{dsUsuari}</div></tpl>',
                store: dataStoreUsuarios,
                width: 300,
                mode: 'local',
                name: 'dsUsuari',
                //tooltip:'Usuario Responsable',
                hiddenName: 'nombreUsuario',
                typeAhead: true,
                //labelSeparator:'',          
                triggerAction: 'all',           
                displayField:'nombreUsuario',
                forceSelection: true,
		        fieldLabel: getLabelFromMap('nombreUsuarioEdtId',helpMap,'Usuario Responsable'),
		        tooltip:getToolTipFromMap('nombreUsuarioEdtId',helpMap,'Usuario Responsable'), 
		        hasHelpIcon:getHelpIconFromMap('nombreUsuarioEdtId',helpMap),
		        Ayuda:getHelpTextFromMap('nombreUsuarioEdtId',helpMap),
                //fieldLabel: "Usuario Responsable",
                valueField:'cdUsuario', 
                emptyText:'Seleccionar un Usuario...',
                selectOnFocus:true
        }); 
	
		
	
	var editarForm = new Ext.form.FormPanel({
				id:'editarForm',
				url:'menuusuario/editarAction.action',
				boder:false,            	            
        		width: 470,
        		buttonAlign: "center",
				baseCls:'x-plain',
				labelWidth:75,
				labelAlign:'right',
				bodyStyle:'background: white',
		
				items:[
                    dsUnieco,
					dsRamo,
					estado,
                    nmPoliza,
					nombreCliente,
                    nombreUsuario
				]
			});
	
	/*******COMIENZA EDITAR**********/
		
		var window = new Ext.Window({
        //title: 'Editar administración de asignaci&oacute;n de Encuesta',
		title: '<span style="color:black;font-size:14px;">'+getLabelFromMap('wndwCnsltAsigEncEdt',helpMap,'Editar administración de asignaci&oacute;n de Encuesta')+'</span>',
        width: 500,
        height:270,
        minWidth: 300,
        minHeight: 250,
        layout: 'fit',
        plain:true,
        modal:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        closeAction:'hide', 
        items: editarForm,

        buttons: [{
        	text:getLabelFromMap('consltAsigEncEdtButtonBuscar',helpMap,'Guard'),
        	tooltip: getToolTipFromMap('consltAsigEncEdtButtonBuscar',helpMap,'Guarda una Encuesta'),
			//text: 'Guardar', 
            handler: function() {
                if (editarForm.form.isValid()) {
                	
	 		        editarForm.form.submit({
	 		       		waitMsg:'Procesando...',
			            failure: function(form, action) {
			            	var mensajeRes = Ext.util.JSON.decode(action.response.responseText).mensajeRespuesta;
						    Ext.MessageBox.alert('Error',mensajeRes);
						    editarForm.form.reset();
						    window.hide();
						    store.load();
						},
						success: function(form, action) {
							var mensajeRes = Ext.util.JSON.decode(action.response.responseText).mensajeRespuesta;
						    Ext.MessageBox.alert('Configuracion Editada', mensajeRes);
						    editarForm.form.reset();
						    window.hide();
						    store.load();
						    
						}
			        });                   
                } else{
					Ext.MessageBox.alert('Error!', 'Por favor verifique');
				}             
	        }
        },
        
        {
        	text:getLabelFromMap('consltAsigEncEdtButtonRegresar',helpMap,'Regresar'),
        	tooltip: getToolTipFromMap('consltAsigEncEdtButtonRegresar',helpMap,'Regresa pantalla anterior'),
            //text: 'Regresar',
            handler: function(){window.hide();}
        }]
    });
    
    
    /*******TERMINA EDITAR**********/
	
	/********* Comienza el form ********************************/
	var el_form = new Ext.FormPanel ({
			id:'el_formId',
			renderTo: 'formulario',
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('el_formId',helpMap,'Consultar Asignaci&oacute;n de Encuesta ')+'</span>',
            //labelWidth : 50,
            url : _ACTION_BUSCAR_ASIGNACION_ENCUESTA,
            frame : true,
            bodyStyle : 'padding:5px 5px 0',
            bodyStyle:'background: white',
            width : 550,
            height: 120,
            autoHeight: true,
            labelWidth:125,
            labelAlign:'right',
            waitMsgTarget : true,
            //border:false,
			items:[{
					html: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
            
		            layout: 'column',
		            //layoutConfig: {columns: 1},
		            items: [
		            			/*{layout: 'form',
		            			 
		            			columnWidth: 1,
		            			items: [            					
						                  {
						                  xtype: 'textfield', 
						                  fieldLabel: getLabelFromMap('confAAdsUnieco',helpMap,'Aseguradora'),
						                  tooltip: getToolTipFromMap('confAAdsUnieco',helpMap,'Aseguradora'),
						                  anchor:'65%',
						                  id: 'dsUnieco',
						                  name: 'dsUnieco'}
		            					]
		            			},
		            			
		            			
		            			{layout: 'form',
		            			//columnWidth: .50,
		            			columnWidth: 1,
		            			items: [
						                    {xtype: 'textfield',
						                     fieldLabel: getLabelFromMap('confAAdsRamo',helpMap,'Producto'),
						                     tooltip: getToolTipFromMap('confAAdsRamo',helpMap,'Producto'), 
						                     anchor:'65%',
						                     id: 'dsRamo',
						                     name: 'dsRamo'}
		            					]
		            			},
		            			
		            			
		            			{
			        				layout: 'form',
			        				//columnWidth: .50,
			        				columnWidth: 1,
			        				items: [
			        						{xtype: 'textfield',
			        						 fieldLabel: getLabelFromMap('confAAestado',helpMap,'Estado'),
			        						 tooltip: getToolTipFromMap('confAAestado',helpMap,'Estado'),
			        						 anchor:'65%',
			        						 name: 'cdEstado',
			        						 id: 'cdEstado'}
			        					   ]
			        			},*/
		            			
		            			
		            			{layout: 'form',
		            			//columnWidth: .50,
		            			columnWidth: 1,
		            			items: [
		            						{
		            						xtype: 'textfield',
		            						fieldLabel: getLabelFromMap('confAAnmPoliza',helpMap,'P&oacute;liza'),
		            						tooltip: getToolTipFromMap('confAAnmPoliza',helpMap,'P&oacute;liza'),
										    hasHelpIcon:getHelpIconFromMap('dsUniecoEdtId',helpMap),
										    Ayuda:getHelpTextFromMap('dsUniecoEdtId',helpMap),
										    width: 220,
		            						//anchor:'65%',
		            						name: 'nmPoliza', 
		            						id: 'nmPoliza'
		            						}
		            					]
		            			},
		            			
		            			
		            			{layout: 'form',
		            			//columnWidth: .50, //Para 2 columnas
		            			columnWidth: 1,
		            			items: [
		            						{
		            						xtype: 'textfield',
		            						fieldLabel: getLabelFromMap('confAAdsUnieco',helpMap,'Cliente'),
		            						tooltip: getToolTipFromMap('confAAdsUnieco',helpMap,'Cliente'),
										    hasHelpIcon:getHelpIconFromMap('dsUniecoEdtId',helpMap),
										    Ayuda:getHelpTextFromMap('dsUniecoEdtId',helpMap),
		            						width: 220,
		            						//anchor:'65%',
		            						id: 'nombreCliente', 
		            						name: 'nombreCliente'
		            						}
		            					]
		            			},
		            			     				            			            			
		            			    			
			        			{layout: 'form',
			        			//columnWidth: .50,
			        			columnWidth: 1,
			        			
			        			items: [
			        						{
			        						xtype: 'textfield',
			        						fieldLabel: getLabelFromMap('confAAdsResponsable',helpMap,'Usuario Responsable'),
			        						tooltip: getToolTipFromMap('confAAdsResponsable',helpMap,'Usuario Responsable'),
										    hasHelpIcon:getHelpIconFromMap('dsUniecoEdtId',helpMap),
										    Ayuda:getHelpTextFromMap('dsUniecoEdtId',helpMap),
			        						width: 220,
			        						//anchor:'65%',
			        						name: 'nombreUsuario', 
			        						id: 'nombreUsuario'
			        						}
			        					]
			        			},
			        			    	    			{
				   			html: '<span class="x-form-item" style="font-weight:bold"></span><br>'
				   			}
		            		],
		            		buttonAlign: 'center',
		            		buttons: [
		            					{
		            					text: getLabelFromMap('confAABtnBuscar',helpMap,'Buscar'), 
		            					tooltip: getToolTipFromMap('confAABtnBuscar',helpMap,'Busca asignaci&oacute;n de encuesta'),
		            					handler: function () {
		   														if (el_form.form.isValid()) {
		   															if (grilla != null) {
		   																reloadGrid(grilla,el_form);
		   															}else {
		   																createGrid();
		   															}
		   														}
			            										}
		            					},
		            					{
		            					text: getLabelFromMap('confAABtnCancelar',helpMap,'Cancelar'), 
		            					tooltip: getToolTipFromMap('confAABtnCancelar',helpMap,'Cancela la operaci&oacute;n'),
		            					handler: function() {el_form.getForm().reset();}
		            					}
		            				]
			}]
            //se definen los campos del formulario
	});
	/********* Fin del form ************************************/
	
	/********* Comienzo del grid *****************************/
		//Definición Column Model
	    var cm = new Ext.grid.ColumnModel([
	    		{
	    			header: '',
	    			dataIndex: 'cdUnieco',
	    			hidden: true
	    		},
	    		
	    		{
	    			header: '',
	    			dataIndex: 'nmConfig',
	    			hidden: true
	    		},
	    		
	    		{
	    			header: '',
	    			dataIndex: 'cdRamo',
	    			hidden: true
	    		},
	    		
	    		{
	    			header: '',
	    			dataIndex: 'cdPerson',
	    			hidden: true
	    		},
	    		
	    		{
	    			header: '',
	    			dataIndex: 'cdUsuario',
	    			hidden: true
	    		},
	    		
	    			{
	    			header: '',
	    			dataIndex: 'estado',
	    			hidden: true
	    		},
	    		
	    		
				{
		           	header: getLabelFromMap('confAACmCliente',helpMap,'Aseguradora'),
		           	tooltip: getToolTipFromMap('confAACmCliente',helpMap,'Aseguradora'),
		           	dataIndex: 'dsUnieco',
		           	sortable: true,
		           	width: 100
	        	},{
		           	header: getLabelFromMap('confAACmNombre',helpMap,'Producto'),
		           	tooltip: getToolTipFromMap('confAACmNombre',helpMap,'Producto'),
		           	dataIndex: 'dsRamo',
		           	sortable: true,
		           	width: 80
	        	},{
		           	header: getLabelFromMap('confAACmProceso',helpMap,'Estado'),
		           	tooltip: getToolTipFromMap('confAACmProceso',helpMap,'Estado'),
		           	dataIndex: 'estado',
		           	sortable: true,
		           	width: 70
	           	},{
		           	header: getLabelFromMap('confAACmUsuario',helpMap,'P&oacute;liza'),
		           	tooltip: getToolTipFromMap('confAACmUsuario',helpMap,'P&oacute;liza'),
		           	dataIndex: 'nmPoliza',
		           	sortable: true,
		           	width: 70
	           	},{
		           	header: getLabelFromMap('confAACmRamo',helpMap,'Cliente'),
		           	tooltip: getToolTipFromMap('confAACmRamo',helpMap,'Cliente'),
		           	dataIndex: 'nombreCliente',
		           	width: 70,
		           	sortable: true
	           	},
	           	{
	           		header: getLabelFromMap('confAACmAseguradora',helpMap,'Usuario Responsable'),
		           	tooltip: getToolTipFromMap('confAACmAseguradora',helpMap,'Usuario Responsable'),
	           		dataIndex: 'nombreUsuario',
	           		sortable: true,
	           		width: 150
	           		
	           	}
	           
	           	]);
		//Fin Definición Column Model
		//Crea el Store
		function crearGridStore(){
		 			store = new Ext.data.Store({
		    			proxy: new Ext.data.HttpProxy({
						url: _ACTION_BUSCAR_ASIGNACION_ENCUESTA,
						waitMsg: getLabelFromMap('400017',helpMap,'Espere por favor...')
		                }),
		                reader: new Ext.data.JsonReader({
		            	root:'MConsularAsignacionEncuestaList',
		            	totalProperty: 'totalCount',
			            successProperty : '@success'
			        },[
			       
			       
			        {name: 'cdUnieco',  type: 'string',  mapping:'cdUnieco'},
			        {name: 'cdRamo',  type: 'string',  mapping:'cdRamo'},
			        {name: 'cdPerson',  type: 'string',  mapping:'cdPerson'},
			        {name: 'cdUsuario',  type: 'string',  mapping:'cdUsuario'},
			        {name: 'estado',  type: 'string',  mapping:'estado'},
			        {name: 'dsUnieco',  type: 'string',  mapping:'dsUnieco'},	
			        {name: 'dsRamo',  type: 'string',  mapping:'dsRamo'},    
			        {name: 'estado',  type: 'string',  mapping:'estado'},
			        {name: 'nmPoliza',  type: 'string',  mapping:'nmPoliza'},
			        {name: 'nombreCliente', type: 'string', mapping: 'nombreCliente'},
			        {name: 'nombreUsuario',  type: 'string',  mapping:'nombreUsuario'},
			        {name: 'nmConfig',  type: 'string',  mapping:'nmConfig'}
			       
			       		  			        
			        
			     
			        
					])
		        });
				return store;
		 	}
		//Fin Crea el Store
		
	var grilla;
	
	function createGrid(){
		grilla= new Ext.grid.GridPanel({
			id:'grillaId',
	        el:'gridConfiguraAlertas',
	        store:crearGridStore(),
			border:true,
			title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
	        cm: cm,
	        loadMask: {msg: getLabelFromMap('400058',helpMap,'Cargando datos ...'), disabled: false},
	        stripeRows: true,
	        collapsible: true,
	        buttonAlign:'center',
			buttons:[
		        		{
		        			text: getLabelFromMap('confAABtnAgregar',helpMap,'Agregar'), 
            				tooltip: getToolTipFromMap('confAABtnAgregar',helpMap,'Agrega una asignaci&oacute;n de encuesta'),
		            		handler:function(){agregarEncuesta()}
		            	},
		            	{
		            		text: getLabelFromMap('confAABtnEditar',helpMap,'Editar'), 
            				tooltip: getToolTipFromMap('confAABtnEditar',helpMap,'Edita una asignaci&oacute;n de encuesta'),
		            		handler:function(){
			            			if(getSelectedRecord()!=null){
			                			editar(getSelectedRecord());
			                		}
			                		else{
			                			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe Seleccionar un registro para realizar esta operaci&oacute;n'));
			                		}
		                	}
		            	},
		            	{
		            		text: getLabelFromMap('confAABtnBorrar',helpMap,'Eliminar'), 
            				tooltip: getToolTipFromMap('confAABtnBorrar',helpMap,'Elimina una asignaci&oacute;n de encuesta'),
		                	handler:function(){
		                			if(getSelectedCodigo() != ""){
		                					borrar(getSelectedCodigo());
		                					//reloadGrid();
		                			}
		                			else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe Seleccionar un registro para realizar esta operaci&oacute;n'));}
		                		}
		            	},
		            	{
		            		text: getLabelFromMap('confAABtnExportar',helpMap,'Exportar'), 
            				tooltip: getToolTipFromMap('confAABtnExportar',helpMap,'Exporta el contenido del grid'),
		                	handler:function(){
                                  //Ext.getCmp('el_form').form.findField('dsUnieco').getValue()
                                  //el_form.findById('dsUnieco').getValue()
                                  var url = _ACTION_ASIGNACION_ENCUESTA_EXPORTAR + '?nmPoliza=' +Ext.getCmp('nmPoliza').getValue()+ '&nombreUsuario=' +Ext.getCmp('nombreUsuario').getValue()+ '&nombreCliente=' +Ext.getCmp('nombreCliente').getValue();
                	 	          showExportDialog( url );
                               }
		            	},
		            	{
		            		text: getLabelFromMap('confAABtnRegresar',helpMap,'Regresar'), 
            				tooltip: getToolTipFromMap('confAABtnRegresar',helpMap)
		            	}
	            	],
	            	
	    	width:550,
	    	frame:true,
			height:320,
			sm: new Ext.grid.RowSelectionModel({
			singleSelect: true,
			
		   listeners: {                            
                        rowselect: function(sm, row, rec) {
                        		selectedId = store.data.items[row].id;
                        		recGridConf = rec;
                                Ext.getCmp('borrar').on('click', function(){
                                	//alert(selectedId);
                                	if(selectedId==null){
                                		Ext.MessageBox.alert('Informaci&oacute;n','No se ha seleccionado ningun registro');
                                	}else{
                                		windowDel.show();
                                		Ext.getCmp('borrarForm').getForm().loadRecord(rec);
                                	}
                                });     
                                                                                                                                                      
                                Ext.getCmp('editar').on('click',function(){
                                	//Se cargan valores a las variables de apoyo para enviarlas con el form
                                	idClienteCombo.setValue(clienteCombo.getValue());
                                	idCdRol.setValue(cdRol.getValue());
                    				idUsuarioCombo.setValue(usuarioCombo.getValue());
									idTipoCombo.setValue(tipoCombo.getValue());
                                	
                        			window.show();
                                    Ext.getCmp('editarForm').getForm().loadRecord(rec);
                                    
                                });
                                                                                 
                        }
                }
                }),			
			
			bbar: new Ext.PagingToolbar({
					pageSize: itemsPerPage,
					store: store,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })
			});
	
	    grilla.render();
	    
	  
	    
	}
	
//***
	/********* Fin del grid **********************************/

	//Función para obtener el registro seleccionado en la grilla
		function getSelectedRecord(){
             var m = grilla.getSelections();
             if (m.length == 1 ) {
                return m[0];
             }
        }
	


	//Muestra los componentes en pantalla
	el_form.render();
	createGrid();
	//Fin Muestra los componentes en pantalla

	//Obtiene el Código de la alerta seleccionada
	function getSelectedCodigo(){
              var m = grilla.getSelections();
              var jsonData = "";
              for (var i = 0, len = m.length;i < len; i++) {
                var ss = m[i].get("cdUnieco");
                if (i == 0) {
                jsonData = jsonData + ss;
                } else {
                  jsonData = jsonData + "," + ss;
               }
              }
              return jsonData;
    }

    //Borra la alerta seleccionada
	function borrar(key){
   			
					var conn = new Ext.data.Connection();
					Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
					{
	           if (btn == "yes"){
								conn.request({
								url: _ACTION_ELIMINA_ASIGNACION_ENCUESTA,
								method: 'POST',
								params: {"cdUnieco":key},
								success: function() {
								Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),'Configuraci&oacute;n eliminada');
								reloadGrid(grilla,el_form);
								},
								failure: function() {
								Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'),'No se elimino la configuraci&oacute;n');
								}
								});
								}

					});
								
  	};
});


	
	function reloadGrid(grilla,el_form){
	    var mStore = grilla.store;
	    var o = {start: 0};
	    mStore.baseParams = mStore.baseParams || {};
	    
	   /* mStore.baseParams['dsUnieco'] = el_form.findById('dsUnieco').getValue();
	    mStore.baseParams['dsRamo'] = el_form.findById('dsRamo').getValue();	    	  
	    mStore.baseParams['cdEstado'] = el_form.findById('cdEstado').getValue();*/
	    
	    mStore.baseParams['nmPoliza'] = el_form.findById('nmPoliza').getValue();
	    mStore.baseParams['nombreCliente'] = el_form.findById('nombreCliente').getValue();
        mStore.baseParams['nombreUsuario'] = el_form.findById('nombreUsuario').getValue();
	    
	   
	  
	    mStore.reload(
	              {
	                  params:{start:0,limit:itemsPerPage},
	                  callback : function(r,options,success) {
	                      if (!success) {
	                         Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400033', helpMap,'No se encontraron registros'));
	                         mStore.removeAll();
	                      }
	                  }
	
	              }
	            );
	
	}
