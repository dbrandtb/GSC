//############################ CZO DE GUARDAR ASOCIAR FORMATOS ORDEN TRABAJO ######################################################
	
	function guardar(codigoFormato){

//****************Definición de los store de los combos*************************************************************

	var dsProcesos = new Ext.data.Store({
		     	proxy: new Ext.data.HttpProxy({
		         	url: _ACTION_OBTENER_PROCESOS
		     	}),
		     	reader: new Ext.data.JsonReader({
		     		root: 'comboProcesos',
		     		totalProperty: 'totalCount',
		     		id: 'codigo'
		     		},[
		    			{name: 'codigo', type: 'string',mapping:'codigo'},
		    			{name: 'descripcion', type: 'string',mapping:'descripcion'}
		 			  ])
		 	});
		dsProcesos.load();
		
		var dsFormatos = new Ext.data.Store({
		    proxy: new Ext.data.HttpProxy({
		        url: _ACTION_OBTENER_FORMATOS
		    }),
		    reader: new Ext.data.JsonReader({
		    	root: 'comboFormatos',
		    	totalProperty: 'totalCount',
		    	id: 'codigo'
		    	},[
		   			{name: 'codigo',  type: 'string', mapping:'codigo'},
		   			{name: 'descripcion',  type: 'string', mapping:'descripcion'}
				])
		});
		dsFormatos.load();
	
	    var dsClientesCorporativos = new Ext.data.Store({
	        proxy: new Ext.data.HttpProxy({
	            url: _ACTION_OBTENER_CLIENTES_CORPORATIVOS
	        }),
	        reader: new Ext.data.JsonReader({
	        	root: 'clientesCorp',
	        	totalProperty: 'totalCount',
	        	id: 'codigoElemento'
	        	},[
	       			{name: 'codigoPerson',   type: 'string',  mapping:'cdPerson'},
	       			{name: 'descripcionElemen',   type: 'string',  mapping:'dsElemen'},
	       			{name: 'codigoElemento',   type: 'string',  mapping:'cdElemento'}
		   		 ])
	    });
	    dsClientesCorporativos.load();
		
		
		
		var dsAseguradoras = new Ext.data.Store({
	        proxy: new Ext.data.HttpProxy({
	            url: _ACTION_OBTENER_ASEGURADORAS
	        }),
	        reader: new Ext.data.JsonReader({
	        	root: 'aseguradoraComboBox',
	        	totalProperty: 'totalCount',
	        	id: 'cdUniEco'
	        	},[
	       			{name: 'cdUniEco',   type: 'string',  mapping:'cdUniEco'},
	       			{name: 'dsUniEco',   type: 'string',  mapping:'dsUniEco'}
		   		 ])
	    });

	    
	    var dsProductos = new Ext.data.Store({
	        proxy: new Ext.data.HttpProxy({
	            url: _ACTION_OBTENER_PRODUCTOS
	        }),
	        reader: new Ext.data.JsonReader({
	        	root: 'productosComboBox',
	        	totalProperty: 'totalCount',
	        	id: 'codigo'	        	},[
	       			{name: 'codigo',   type: 'string',  mapping:'codigo'},
	       			{name: 'descripcion',   type: 'string',  mapping:'descripcion'}
		   		 ])
	    });
	    
		
		var dsFormaCalculoFolio = new Ext.data.Store({
	        proxy: new Ext.data.HttpProxy({
	            url: _ACTION_OBTENER_FORMA_CALCULO_FOLIOS
	        }),
	        reader: new Ext.data.JsonReader({
	        	root: 'comboFormasCalculoFolio',
	        	totalProperty: 'totalCount',
	        	id: 'codigo'
	        	},[
	       			{name: 'codigo',   type: 'string',  mapping:'codigo'},
	       			{name: 'descripcion',   type: 'string',  mapping:'descripcion'}
		   		 ])
	    });
	    dsFormaCalculoFolio.load();

		var codigoPerson = new Ext.form.Hidden({
			name:"codigoPerson",
			id:"codigoPersonId"
		});
    	var hidden = new Ext.form.Hidden({
					name:'hidden'
					});
	    //****************Definición de los objetos combos*************************************************************
	    var comboProcesos = new Ext.form.ComboBox({
	                    tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                    id:'comboProcesosId',
	                    store: dsProcesos,
	                    displayField:'descripcion',
	                    valueField:'codigo',
	                    hiddenName: 'cdproces',
	                    typeAhead: true,
	                    mode: 'local',
	                    triggerAction: 'all',
                        fieldLabel: getLabelFromMap('comboProcesosId',helpMap,'Proceso'),
                        tooltip: getToolTipFromMap('comboProcesosId',helpMap,'Proceso'),
                        hasHelpIcon:getHelpIconFromMap('comboProcesosId',helpMap),								 
                        Ayuda: getHelpTextFromMap('comboProcesosId',helpMap),
	                    width: 180,
	                    allowBlank : false,
	                    selectOnFocus:true,
	                    forceSelection:true,
	                    emptyText:'Seleccione...'
	                    //labelSeparator:''
	                    });
		
		var comboFormatos = new Ext.form.ComboBox({
	                    tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                    id:'comboFormatosId',
	                    store: dsFormatos,
	                    displayField:'descripcion',
	                    valueField:'codigo',
	                    hiddenName: 'cdforord',
	                    typeAhead: true,
		    			allowBlank : false,
	                    mode: 'local',
	                    triggerAction: 'all',
                        fieldLabel: getLabelFromMap('comboFormatosId',helpMap,'Formato'),
                        tooltip: getToolTipFromMap('comboFormatosId',helpMap,'Formato'),
                        hasHelpIcon:getHelpIconFromMap('comboFormatosId',helpMap),								 
                        Ayuda: getHelpTextFromMap('comboFormatosId',helpMap),
	                    width: 180,
	                    selectOnFocus:true,
	                    forceSelection:true,
	                    emptyText:'Seleccione...'
	                    //labelSeparator:''
	                    });
	                    
		var comboClientesCorporativos = new Ext.form.ComboBox({
	                    tpl: '<tpl for="."><div ext:qtip="{codigoElemento}.{codigoPerson}.{descripcionElemen}" class="x-combo-list-item">{descripcionElemen}</div></tpl>',
	                    id:'comboClientesCorporativosId',
	                    store: dsClientesCorporativos,
	                    displayField:'descripcionElemen',
	                    valueField:'codigoElemento',
	                    hiddenName: 'cdelemento',
	                    typeAhead: true,
	                    mode: 'local',
	        			allowBlank : false,
	                    triggerAction: 'all',
	                    fieldLabel: getLabelFromMap('comboClientesCorporativosId',helpMap,'Cliente'),
                        tooltip: getToolTipFromMap('comboClientesCorporativosId',helpMap,'Cliente'),
                        hasHelpIcon:getHelpIconFromMap('comboClientesCorporativosId',helpMap),								 
                        Ayuda: getHelpTextFromMap('comboClientesCorporativosId',helpMap),
	                    width: 180,
	                    selectOnFocus:true,
	                    forceSelection:true,
	                    emptyText:'Seleccione...',
	                    //labelSeparator:'',
	                    onSelect: function (record){
	                    						this.setValue(record.get("codigoElemento"));
                    							this.collapse();
                    							formPanel.findById("codigoPersonId").setValue(record.get("codigoPerson"));
	                            				comboAseguradoras.setRawValue("");
	                            				dsAseguradoras.removeAll();
	                            				comboProductos.setRawValue('');
	                            				dsProductos.removeAll();
	                            				dsAseguradoras.load({
	                            						params: {cdElemento: formPanel.findById(('comboClientesCorporativosId')).getValue()}
	                            					});	
	                            		        }
	                    });

		var comboAseguradoras = new Ext.form.ComboBox({
	                    tpl: '<tpl for="."><div ext:qtip="{cdUniEco}.{dsUniEco}" class="x-combo-list-item">{dsUniEco}</div></tpl>',
	                    id:'comboAseguradorasId',
	                    store: dsAseguradoras,
	                    displayField:'dsUniEco',
	                    valueField:'cdUniEco',
	                    hiddenName: 'cdunieco',
	                    typeAhead: true,
	                    mode: 'local',
	                    triggerAction: 'all',
                        fieldLabel: getLabelFromMap('comboAseguradorasId',helpMap,'Aseguradora'),
                        tooltip: getToolTipFromMap('comboAseguradorasId',helpMap,'Aseguradora'),
                        hasHelpIcon:getHelpIconFromMap('comboAseguradorasId',helpMap),								 
                        Ayuda: getHelpTextFromMap('comboAseguradorasId',helpMap),
	                    width: 180,
	                    selectOnFocus:true,
	        			allowBlank : false,
	                    emptyText:'Seleccione...',
	                    forceSelection:true,
	                    //labelSeparator:'',
	                    onSelect: function (record){
	                     						this.setValue(record.get("cdUniEco"));
                    							this.collapse();
	                            				dsProductos.removeAll();
	                            				
	                            				comboProductos.setRawValue("");
	                            				dsProductos.load({
	                            						params: {cdElemento: formPanel.findById(('comboAseguradorasId')).getValue()},
	                            						params: {cdElemento: formPanel.findById(('comboClientesCorporativosId')).getValue()}
	                            					});	
	                            		        }
	                    });	

		var comboProductos = new Ext.form.ComboBox({
	                    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                    id:'comboProductosId', 
	                    store: dsProductos,
	                    displayField:'descripcion',
	                    valueField:'codigo',
	                    hiddenName: 'cdramo',
	                    typeAhead: true,
	                    mode: 'local',
	                    triggerAction: 'all',
                        fieldLabel: getLabelFromMap('comboProductosId',helpMap,'Producto'),
                        tooltip: getToolTipFromMap('comboProductosId',helpMap,'Producto'),
                        hasHelpIcon:getHelpIconFromMap('comboProductosId',helpMap),								 
                        Ayuda: getHelpTextFromMap('comboProductosId',helpMap),
	                    width: 180,
			        	allowBlank : false,
	                    selectOnFocus:true,
	                    forceSelection:true,
	                    emptyText:'Seleccione...'
	                    //labelSeparator:''
	                    });	
	                    
		var comboFormaCalculoFolioAON = new Ext.form.ComboBox({
	                    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                    id:'comboFormaCalculoFolioId',
	                    store: dsFormaCalculoFolio,
	                    displayField:'descripcion',
	                    valueField:'codigo',
	                    hiddenName: 'cdfolioaon',
	                    typeAhead: true,
	                    mode: 'local',
	                    triggerAction: 'all',
                        fieldLabel: getLabelFromMap('comboFormaCalculoFolioId',helpMap,'Forma de c&aacute;lculo de folio de AON'),
                        tooltip: getToolTipFromMap('comboFormaCalculoFolioId',helpMap,'Forma de c&aacute;lculo de folio de AON'),
                        hasHelpIcon:getHelpIconFromMap('comboFormaCalculoFolioId',helpMap),								 
                        Ayuda: getHelpTextFromMap('comboFormaCalculoFolioId',helpMap),
	                    width: 180,
	                    selectOnFocus:true,
	                    emptyText:'Seleccione...',
	                    //labelSeparator:'',
	                    forceSelection:true,
	                    onSelect: function (record){
	                    			this.setValue(record.get("codigo"));
                    				this.collapse();
	                    			if(record.get("codigo") == 1){
	                    				formPanel.findById('folioAON_InicioId').setDisabled(false);
	                    				formPanel.findById('folioAON_FinId').setDisabled(false);
	                    				formPanel.findById('formulaCalculoAONId').setDisabled(true);
	                    				}
	                    			if(record.get("codigo") == 2){                  							
	                    				formPanel.findById('folioAON_InicioId').setDisabled(true);
	                    				formPanel.findById('folioAON_FinId').setDisabled(true);
	                    				formPanel.findById('formulaCalculoAONId').setDisabled(false);
	                    				}
	                    			}
	                    });

	                    
		var comboFormaCalculoFolioAseguradora = new Ext.form.ComboBox({
	                    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                    id:'comboFormaCalculoFolioAseguradoraId',
	                    store: dsFormaCalculoFolio,
	                    displayField:'descripcion',
	                    valueField:'codigo',
	                    hiddenName: 'cdfoliocia',
	                    typeAhead: true,
	                    mode: 'local',
	                    triggerAction: 'all',
                        fieldLabel: getLabelFromMap('comboFormaCalculoFolioAseguradoraId',helpMap,'Forma de c&aacute;lculo de folio de Aseguradora'),
                        tooltip: getToolTipFromMap('comboFormaCalculoFolioAseguradoraId',helpMap,'Forma de c&aacute;lculo de folio de Aseguradora'),
                        hasHelpIcon:getHelpIconFromMap('comboFormaCalculoFolioAseguradoraId',helpMap),								 
                        Ayuda: getHelpTextFromMap('comboFormaCalculoFolioAseguradoraId',helpMap),
	                    width: 180,
	                    selectOnFocus:true,
	                    emptyText:'Seleccione...',
	                    //labelSeparator:'',
	                    forceSelection:true,
	                    onSelect: function (record){
	                    			this.setValue(record.get("codigo"));
                    				this.collapse();
	                    			if(record.get("codigo") == 1){
	                    				formPanel.findById('folioAseguradora_InicioId').setDisabled(false);
	                    				formPanel.findById('folioAseguradora_FinId').setDisabled(false);
	                    				formPanel.findById('formulaCalculoAseguradoraId').setDisabled(true);
	                    				}
	                    			if(record.get("codigo") == 2){
	                    				formPanel.findById('folioAseguradora_InicioId').setDisabled(true);
	                    				formPanel.findById('folioAseguradora_FinId').setDisabled(true);
	                    				formPanel.findById('formulaCalculoAseguradoraId').setDisabled(false);
	                    				}
	                    			}
	                    });
	                    	                    
//************************Definición de los objetos NumberField************************************************	                    
		
		var folioAON_Inicio = new Ext.form.NumberField({
			 id: 'folioAON_InicioId',
             fieldLabel: getLabelFromMap('folioAON_InicioId',helpMap,'Folio Inicio'),
             tooltip: getToolTipFromMap('folioAON_InicioId',helpMap,'Folio inicio AON'),
             hasHelpIcon:getHelpIconFromMap('folioAON_InicioId',helpMap),								 
             Ayuda: getHelpTextFromMap('folioAON_InicioId',helpMap),
		     name:'cdfolaonini',
		     width: 40
	 	});
	 	
	 	var folioAON_Fin = new Ext.form.NumberField({
	 		 id: 'folioAON_FinId',
             fieldLabel: getLabelFromMap('folioAON_FinId',helpMap,'Folio Fin'),
             tooltip: getToolTipFromMap('folioAON_FinId',helpMap,'Folio fin AON'),
             hasHelpIcon:getHelpIconFromMap('folioAON_FinId',helpMap),								 
             Ayuda: getHelpTextFromMap('folioAON_FinId',helpMap),
		     name:'cdfolaonfin',
		     width: 40		    
	 	});
	 	
	 	var folioAseguradora_Inicio = new Ext.form.NumberField({
	 		 id: 'folioAseguradora_InicioId',
             fieldLabel: getLabelFromMap('folioAseguradora_InicioId',helpMap,'Folio Inicio'),
             tooltip: getToolTipFromMap('folioAseguradora_InicioId',helpMap,'Folio inicio Aseguradora'),
             hasHelpIcon:getHelpIconFromMap('folioAseguradora_InicioId',helpMap),								 
             Ayuda: getHelpTextFromMap('folioAseguradora_InicioId',helpMap),
		     name:'cdfolciaini',
		     width: 40
	 	});
	 	
	 	var folioAseguradora_Fin = new Ext.form.NumberField({
	 		 id: 'folioAseguradora_FinId',
             fieldLabel: getLabelFromMap('folioAseguradora_FinId',helpMap,'Folio Fin'),
             tooltip: getToolTipFromMap('folioAseguradora_FinId',helpMap,'Folio fin Aseguradora'),
             hasHelpIcon:getHelpIconFromMap('folioAseguradora_FinId',helpMap),								 
             Ayuda: getHelpTextFromMap('folioAseguradora_FinId',helpMap),
		     name:'cdfolciafin',
		     width: 40
	 	});
		
		//************************Definición de los objetos TextArea************************************************
		var formulaCalculoAON = new Ext.form.TextArea({
			 id: 'formulaCalculoAONId',
             fieldLabel: getLabelFromMap('formulaCalculoAONId',helpMap,'F&oacute;rmula de C&aacute;lculo para AON'),
             tooltip: getToolTipFromMap('formulaCalculoAONId',helpMap,'F&oacute;rmula de C&aacute;lculo para AON'),
             hasHelpIcon:getHelpIconFromMap('formulaCalculoAONId',helpMap),								 
             Ayuda: getHelpTextFromMap('formulaCalculoAONId',helpMap),
		     name:'dsformaon',
		     width: 200
	 	});
	 	
	 	var formulaCalculoAseguradora = new Ext.form.TextArea({
	 		 id: 'formulaCalculoAseguradoraId',
             fieldLabel: getLabelFromMap('formulaCalculoAseguradoraId',helpMap,'F&oacute;rmula de C&aacute;lculo para Aseguradora'),
             tooltip: getToolTipFromMap('formulaCalculoAseguradoraId',helpMap,'F&oacute;rmula de C&aacute;lculo para Aseguradora'),
             hasHelpIcon:getHelpIconFromMap('formulaCalculoAseguradoraId',helpMap),								 
             Ayuda: getHelpTextFromMap('formulaCalculoAseguradoraId',helpMap),
		     name:'dsformcia',
		     width: 200
	 	});
		
//************************Definición de los objetos TextArea************************************************
    	
		var formPanel = new Ext.FormPanel({
			id:'AgrAsocFor',
            title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('AgrAsocFor', helpMap,'Asociar Formato de Orden de Trabajo')+'</span>',        
	        iconCls:'logo',
	        bodyStyle:'background: white',
	        buttonAlign: "center",
	        labelAlign: 'right',
	        frame:true,
	        url: _ACTION_GUARDAR_ASOCIAR_ORDEN_TRABAJO,
	        width: 700,
	        height:485,
	        items: [//form
	                {
	    	    	xtype: 'hidden', 
 		            id: 'cdasocia', 
            		name: 'cdasocia'
            		}, 
	        		codigoPerson,
            		comboProcesos,
            		comboFormatos,
            		comboClientesCorporativos,
            		comboAseguradoras,
            		comboProductos,
            		{
           			layout: 'column',
           			items:[
           					{//1ra columna
	           					columnWidth: .5,
	           					layout: 'form',
	           					items: [comboFormaCalculoFolioAON,comboFormaCalculoFolioAseguradora]
           				    },
           				    {//2da columna
	           				   	columnWidth: .25,
	           					layout: 'form',
	           					items: [folioAON_Inicio,folioAseguradora_Inicio]
           				    },
           				    {//3da columna
	           				   	columnWidth: .25,
	           					layout: 'form',
	           					items: [folioAON_Fin,folioAseguradora_Fin]
           				    }
           			]
           		   },
           		   formulaCalculoAON,
           		   formulaCalculoAseguradora
           		]
        });


	
	 var window = new Ext.Window({
       	width: 700,
       	height:485,
       	minWidth: 300,
       	modal: true,
       	minHeight: 100,
       	layout: 'fit',
       	plain:true,
       	bodyStyle:'padding:5px;',
       	buttonAlign:'center',
       	items: formPanel,
           //se definen los botones del formulario
        buttons : [ 
        		   { text:getLabelFromMap('AgrAsocForBtnAdd',helpMap,'Guardar'),
                     tooltip: getToolTipFromMap('AgrAsocForBtnAdd',helpMap,'Guardar'),
		             disabled : false,
		             handler : function() {
		                 if (formPanel.form.isValid()) {
		                 	if ((validarFoliosAON()==true)&&(validarFoliosCIA()==true)){

		                     formPanel.form.submit( {
		                         //action a invocar cuando el formulario haga submit
		                         url : _ACTION_GUARDAR_ASOCIAR_ORDEN_TRABAJO,						
		                         
		                         //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
		                         success : function(from, action) {
		                             Ext.MessageBox.alert('Aviso', action.result.actionMessages[0], function(){reloadGrid(Ext.getCmp('grillita'))});
		                             window.close();
		                         },
		                         //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
		                         failure : function(form, action) {
		                             Ext.MessageBox.alert('Error', action.result.errorMessages[0]);
		                         },
		                         //mensaje a mostrar mientras se guardan los datos
		                         waitMsg : 'guardando datos ...'
		                    })
		                    }
		                }
		               else {
    						 Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
		                    }
					}
		          },
		          {	              
                   text:getLabelFromMap('AgrAsocForBtnCan',helpMap,'Cancelar'),
                   tooltip: getToolTipFromMap('AgrAsocForBtnCan',helpMap,'Cancelar'),
	               handler : function() {
	                   window.close();
	               }
				  }
				  ]
				});

   	window.show();
   	
   	formPanel.findById('folioAON_InicioId').setDisabled(true);
   	formPanel.findById('folioAON_FinId').setDisabled(true);
   	formPanel.findById('folioAseguradora_InicioId').setDisabled(true);
   	formPanel.findById('folioAseguradora_FinId').setDisabled(true);
   	formPanel.findById('formulaCalculoAONId').setDisabled(true);
   	formPanel.findById('formulaCalculoAseguradoraId').setDisabled(true);

   function validarFoliosAON(){
	 
		if (formPanel.findById("comboFormaCalculoFolioId").getValue()=="1" ){
			if (eval(formPanel.findById("folioAON_InicioId").getValue()) >= eval(formPanel.findById("folioAON_FinId").getValue())){
			       Ext.Msg.alert('Informacion', 'El folio de Inicio debe ser menor que el folio de Fin.');
			       var folFin = eval(formPanel.findById("folioAON_InicioId").getValue()) + 1;
			       formPanel.findById("folioAON_FinId").setValue(folFin);	
			       return false;
			 }					
		} 
			return true;
   };
	
    function validarFoliosCIA(){
	 
		if (formPanel.findById("comboFormaCalculoFolioAseguradoraId").getValue()=="1" ) {
			if (eval(formPanel.findById("folioAseguradora_InicioId").getValue()) >= eval(formPanel.findById("folioAseguradora_FinId").getValue())){
			       Ext.Msg.alert('Informacion', 'El folio de Inicio debe ser menor que el folio de Fin.');
			       var folFin = eval(formPanel.findById("folioAseguradora_InicioId").getValue()) + 1;
			       formPanel.findById("folioAseguradora_FinId").setValue(folFin);	
			
			return false;
		}
		}
			return true;
   };

	}
   	
//######################### FIN DE GUARDAR ASOCIAR FORMATOS ORDEN TRABAJO ############################################################ 
