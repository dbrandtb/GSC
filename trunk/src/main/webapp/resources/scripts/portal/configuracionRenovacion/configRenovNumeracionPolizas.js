function agregarNuevaNum() {

     var dsFormaCalculoFolio = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_COMBO_FORMA_CALCULO_FOLIO
            }),
            reader: new Ext.data.JsonReader({
            root: 'formaCalculoFolioNroIncisos',
            id: 'id'
            },[
           {name: 'id', type: 'string',mapping:'codigo'},
           {name: 'texto', type: 'string',mapping:'descripcion'}
        ])
    });


   var dsIndicadorSP = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_COMBO_INDICADOR_SP
            }),
            reader: new Ext.data.JsonReader({
            root: 'indicador_SP_NroIncisos',
            id: 'id'
            },[
            {name: 'id', type: 'string',mapping:'codigo'},
            {name: 'texto', type: 'string',mapping:'descripcion'}
        ])
    });
    
    
    var dsProceso = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_COMBO_PROCESO_POLIZA
            }),
            reader: new Ext.data.JsonReader({
            root: 'comboProcesoPoliza',
            id: 'id'
            },[
            {name: 'id', type: 'string',mapping:'codigo'},
            {name: 'texto', type: 'string',mapping:'descripcion'}
        ])
    });
    
    var dsUsaCombinacion = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_COMBO_USA_AGRUPACION
            }),
            reader: new Ext.data.JsonReader({
            root: 'siNo',
            id: 'id'
            },[
            {name: 'id', type: 'string',mapping:'codigo'},
            {name: 'texto', type: 'string',mapping:'descripcion'}
        ])
    });
    
    var dsTipoPoliza = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_COMBO_TIPO_POLIZA
            }),
            reader: new Ext.data.JsonReader({
            root: 'listaTipoPoliza',
            id: 'id'
            },[
            {name: 'id', type: 'string',mapping:'codigo'},
            {name: 'texto', type: 'string',mapping:'descripcion'}
        ])
    });
   
    
    formulaPanelAgregar=new Ext.Panel({
    	layout:'column',
		border: false,
		width : 350,
		//column items:
		items:[
			{
				columnWidth:.7,
               	layout: 'form',
               	border: false,
				items:[
					{
                		xtype: 'textfield', 
                		fieldLabel: getLabelFromMap('dsCalculo', helpMap, "F&oacute;rmula"), 
                		tooltip: getToolTipFromMap('dsCalculo', helpMap, 'F&oacute;rmula de Calculo de Folio'), 
		        		hasHelpIcon:getHelpIconFromMap('dsCalculo',helpMap),
			    		Ayuda:getHelpTextFromMap('dsCalculo',helpMap),
                		name: 'dsCalculo', 
                		id: 'dsCalculo',
                		width: 100,
                		blankText: 'Debe elegir una Expresi&oacute;n',
						disabled: true,
						readOnly: true
                	}
			  	]
			  },{
			  	columnWidth:.3,
               	layout: 'form',
               	border: false,
               	//Componentes agregados para las Expresiones
				items:[
					{
			  			xtype:'button',
                		text: 'Expresi&oacute;n',
                		id: 'btnExpresion',
                		name: 'btnExpresion',
                		buttonAlign: "left",
                		handler: function() {
                			//Si el campo Formula ya tiene valor, mostraremos la expresión con ése código, para que sea editada:
                			if(Ext.getCmp('dsCalculo').getValue() != ""){
                				ExpresionesVentana2(Ext.getCmp('dsCalculo').getValue(), Ext.getCmp('hidden-codigo-expresion-session').getValue());
                			}else{
                				var connect = new Ext.data.Connection();
								connect.request ({
									url:'atributosVariables/ObtenerCodigoExpresion.action',
									callback: function (options, success, response) {
										try{
											//alert(Ext.util.JSON.decode(response.responseText).codigoExpresion);
											Ext.getCmp('hidden-codigo-expresion-numeracion-polizas').setValue(Ext.util.JSON.decode(response.responseText).codigoExpresion);
											ExpresionesVentana2(Ext.getCmp('hidden-codigo-expresion-numeracion-polizas').getValue(),Ext.getCmp('hidden-codigo-expresion-session').getValue());
											Ext.getCmp('dsCalculo').setValue( Ext.getCmp('hidden-codigo-expresion-numeracion-polizas').getValue() );
										}catch(e){
										}
									}
								});
                			}
						}
                	},{
						xtype:'hidden',
						id:'hidden-codigo-expresion-session',
						name:'codigoExpresionSession', 
						value:'EXPRESION_NUMERACION_POLIZAS'
					},{
                		xtype:'hidden',                	
                		id:'hidden-codigo-expresion-numeracion-polizas',
		           		name: "codigoExpresion"
                	}
                ]
			}
		]//end column items
	});
	
    
//se define el formulario
var formPanel = new Ext.FormPanel ({
			id: 'panelAgregarNum',
            labelWidth : 100,
            bodyStyle : 'padding:5px 5px 0',
            width : 350,
            labelAlign:'right',
            //defaults : {width : 250 },
            defaultType : 'textfield',
            //se definen los campos del formulario
            items : [
                {
                    xtype: 'hidden',
                    name : 'cdPerson',
                    id: 'cdPerson'
                },
                 {
                    xtype: 'hidden',
                    name : 'cdElemento',
                    id: 'numPolCdElemento'
                },
                {
                    xtype: 'hidden',
                    name : 'cdUniEco',
                    id: 'numPolCdUniEco'
                },
                {
                    xtype: 'hidden',
                    name : 'cdRamo',
                    id: 'numPolCdRamo'
                },
                {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{id}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
                    store: dsIndicadorSP,
                    displayField:'texto',
                    valueField:'id',
                    hiddenName: 'indSufPre',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('indSufPreId', helpMap, "Indicador (S/P)"),
                    tooltip: getToolTipFromMap('indSufPreId', helpMap, "Indicador (S/P)"),
                    hasHelpIcon:getHelpIconFromMap('indSufPreId',helpMap),
				    Ayuda:getHelpTextFromMap('indSufPreId',helpMap),
                    width: 250,
                    emptyText: getLabelFromMap('indSufPreId', helpMap, 'Seleccione ...'),
                    selectOnFocus:true,
                    forceSelection:true,
                    //labelSeparator:'',
                    id:'indSufPreId',
                    onSelect: function (record) {
	                            				
	                            				if (record.get("id")=="N"){
	                            				          
	                            							formPanel.findById("dsSufPre").setDisabled(true);
	                            							formPanel.findById("dsSufPre").allowBlank = true;
	                            							formPanel.findById("dsSufPre").setValue('');
	                            							
	                            				}
	                            				
	                            				if (record.get("id")=="S" || record.get("id")=="P"){
	                            				
	                            							formPanel.findById("dsSufPre").setDisabled(false);
	                            							formPanel.findById("dsSufPre").allowBlank = false;
	                            							
	                            				}
	                            				
	                            				this.setValue(record.get('id'));
	                            				this.collapse();
	                            				}
                } ,
                {
                xtype: 'textfield', 
                fieldLabel: getLabelFromMap('dsSufPre', helpMap, "Valor"), 
                tooltip: getToolTipFromMap('dsSufPre', helpMap, 'Valor'),
                hasHelpIcon:getHelpIconFromMap('dsSufPre',helpMap),
				Ayuda:getHelpTextFromMap('dsSufPre',helpMap),
                name: 'dsSufPre', 
                id: 'dsSufPre'
                },
               
               
               
                /*Combo Proceso Polizas*/
                {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{id}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
                    store: dsProceso,
                    displayField:'texto',
                    valueField:'id',
                    hiddenName: 'cdProceso',//procesoPoliza',
                    allowBlank : false,
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('cdProcesoId', helpMap, "Proceso"),
                    tooltip: getToolTipFromMap('cdProcesoId', helpMap, "Proceso"),
			        hasHelpIcon:getHelpIconFromMap('cdProcesoId',helpMap),
	    			Ayuda:getHelpTextFromMap('cdProcesoId',helpMap),
                    width: 250,
                    emptyText: getLabelFromMap('cdProcesoId', helpMap, 'Seleccione ...'),
                    selectOnFocus:true,
                    forceSelection:true,
                    //labelSeparator:'',
                    id:'cdProcesoId',
                    onSelect: function (record) {
	                            				
	                            				/*if (record.get("id")=="N"){
	                            				          
	                            							formPanel.findById("dsSufPre").setDisabled(true);
	                            							formPanel.findById("dsSufPre").allowBlank = true;
	                            							formPanel.findById("dsSufPre").setValue('');
	                            							
	                            				}
	                            				
	                            				if (record.get("id")=="S" || record.get("id")=="P"){
	                            				
	                            							formPanel.findById("dsSufPre").setDisabled(false);
	                            							formPanel.findById("dsSufPre").allowBlank = false;
	                            							
	                            				}*/
	                            				
	                            				this.setValue(record.get('id'));
	                            				this.collapse();
	                            				}
                } ,
                
                {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{id}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
                    store: dsUsaCombinacion,
                    displayField:'texto',
                    valueField:'id',
                    hiddenName: 'swAgrupa',
                    allowBlank : false,
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('swAgrupaId', helpMap, "¿Usa Agrupaci&oacute;n?"),
                    tooltip: getToolTipFromMap('swAgrupaId', helpMap, "¿Usa Agrupaci&oacute;n?"),
			        hasHelpIcon:getHelpIconFromMap('swAgrupaId',helpMap),
	    			Ayuda:getHelpTextFromMap('swAgrupaId',helpMap),
                    width: 250,
                    emptyText: getLabelFromMap('swAgrupaId', helpMap, 'Selecione ...'),
                    selectOnFocus:true,
                    forceSelection:true,
                    //labelSeparator:'',
                    id:'swAgrupaId',
                    onSelect: function (record) {
	                            				
	                            				/*if (record.get("id")=="N"){
	                            				          
	                            							formPanel.findById("dsSufPre").setDisabled(true);
	                            							formPanel.findById("dsSufPre").allowBlank = true;
	                            							formPanel.findById("dsSufPre").setValue('');
	                            							
	                            				}
	                            				
	                            				if (record.get("id")=="S" || record.get("id")=="P"){
	                            				
	                            							formPanel.findById("dsSufPre").setDisabled(false);
	                            							formPanel.findById("dsSufPre").allowBlank = false;
	                            							
	                            				}*/
	                            				
	                            				this.setValue(record.get('id'));
	                            				this.collapse();
	                            				}
                } ,
                
                {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{id}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
                    store: dsTipoPoliza,
                    displayField:'texto',
                    valueField:'id',
                    hiddenName: 'cdTipPol',
                    allowBlank : false,
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('cdTipPolId', helpMap, "Tipo Poliza"),
                    tooltip: getToolTipFromMap('cdTipPolId', helpMap, "Tipo Poliza"),
			        hasHelpIcon:getHelpIconFromMap('cdTipPolId',helpMap),
	    			Ayuda:getHelpTextFromMap('cdTipPolId',helpMap),
                    width: 250,
                    emptyText: getLabelFromMap('cdTipPolId', helpMap, 'Selecione ...'),
                    selectOnFocus:true,
                    forceSelection:true,
                    //labelSeparator:'',
                    id:'cdTipPolId',
                    onSelect: function (record) {
	                            				
	                            				/*if (record.get("id")=="N"){
	                            				          
	                            							formPanel.findById("dsSufPre").setDisabled(true);
	                            							formPanel.findById("dsSufPre").allowBlank = true;
	                            							formPanel.findById("dsSufPre").setValue('');
	                            							
	                            				}
	                            				
	                            				if (record.get("id")=="S" || record.get("id")=="P"){
	                            				
	                            							formPanel.findById("dsSufPre").setDisabled(false);
	                            							formPanel.findById("dsSufPre").allowBlank = false;
	                            							
	                            				}*/
	                            				
	                            				this.setValue(record.get('id'));
	                            				this.collapse();
	                            				}
                } ,
                
                
                
                {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{id}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
                    store: dsFormaCalculoFolio,
                    displayField:'texto',
                    valueField:'id',
                    hiddenName: 'indCalc',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('indCalcId', helpMap, "Forma C&aacute;lculo Folio"),
                    tooltip: getToolTipFromMap('indCalcId', helpMap, 'Forma C&aacute;lculo Folio'),
			        hasHelpIcon:getHelpIconFromMap('indCalcId',helpMap),
	    			Ayuda:getHelpTextFromMap('indCalcId',helpMap),
                    width: 250,
                    emptyText: getLabelFromMap('400020', helpMap, 'Seleccione Forma Calculo Folio...'),
                    selectOnFocus:true,
                    forceSelection:true,
                    labelSeparator:'',
                    allowBlank : false,
                    blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
                    id:'indCalcId',
                                        onSelect: function (record) {
	                            				
	                            				if (record.get("id")=="1"){
	                            				
	                            				            formPanel.findById("nmFolioIni").setDisabled(false);
	                            							formPanel.findById("nmFolioFin").setDisabled(false);
	                            							
	                            							formPanel.findById("nmFolioIni").allowBlank =  false;
	                            							formPanel.findById("nmFolioFin").allowBlank = false;
	                            							
	                            							
	                            							formPanel.findById("dsCalculo").setValue('');
	                            							formPanel.findById("dsCalculo").allowBlank = true;
	                            							formPanel.findById("dsCalculo").setDisabled(true);
	                            							Ext.getCmp('btnExpresion').setDisabled(true);
	                            						    
	                            				}
	                            				
	                            				if (record.get("id")=="2"){
	                            				
	                            				            formPanel.findById("nmFolioIni").allowBlank = true;
	                            							formPanel.findById("nmFolioFin").allowBlank = true;
	                            							
	                            							formPanel.findById("nmFolioIni").setValue('');
	                            							formPanel.findById("nmFolioFin").setValue('');
	                            							
	                            							formPanel.findById("nmFolioIni").setDisabled(true);
	                            							formPanel.findById("nmFolioFin").setDisabled(true);
	                            							
	                            							
	                            								                            							
	                            							
	                            							formPanel.findById("dsCalculo").allowBlank = false;
	                            							formPanel.findById("dsCalculo").setDisabled(false);
	                            							Ext.getCmp('btnExpresion').setDisabled(false);
	                            							
	                            							
	                            				}
	                            				
	                            				this.setValue(record.get('id'));
	                            				this.collapse();	
	                            		        }
                } ,
                formulaPanelAgregar,
               // {xtype: 'numberfield', fieldLabel: getLabelFromMap('dsCalculo', helpMap, "F&oacute;rmula"), tooltip: getToolTipFromMap('dsCalculo', helpMap, 'Formula de Calculo de Folio'), name: 'dsCalculo', heigth:150, id: 'dsCalculo'},	
				{
				xtype: 'textfield', 
				fieldLabel: getLabelFromMap('nmFolioIni', helpMap, "Folio Inicial"), 
				tooltip: getToolTipFromMap('nmFolioIni', helpMap, 'Folio Inicial'), 
		        hasHelpIcon:getHelpIconFromMap('nmFolioIni',helpMap),
			    Ayuda:getHelpTextFromMap('nmFolioIni',helpMap),
				name: 'nmFolioIni', 
				id: 'nmFolioIni',
				listeners: {
						    'change': function(){
						    	 validarFolios();
						         actualizaFolioActual();
						    }
						  }
				},
				
				{
				xtype: 'textfield', 
				fieldLabel: getLabelFromMap('nmFolioFin', helpMap, "Folio Final"), 
				tooltip: getToolTipFromMap('nmFolioFin', helpMap, 'Folio Final'), 
		        hasHelpIcon:getHelpIconFromMap('nmFolioFin',helpMap),
	    		Ayuda:getHelpTextFromMap('nmFolioFin',helpMap),
				name: 'nmFolioFin', 
				id: 'nmFolioFin',
				listeners: {
						    'change': function(){
						         validarFolios();
						    }
						  }
				},
			    {
			    xtype: 'textfield', 
			    fieldLabel: getLabelFromMap('nmFolioAct', helpMap, "Folio Actual"), 
			    tooltip: getToolTipFromMap('nmFolioAct', helpMap, 'Folio Actual'), 
		        hasHelpIcon:getHelpIconFromMap('nmFolioAct',helpMap),
	    		Ayuda:getHelpTextFromMap('nmFolioAct',helpMap),
			    name: 'nmFolioAct', 
			    readOnly: true,  
			    id: 'nmFolioAct'
			    }
            ]
        });


//Windows donde se van a visualizar la pantalla
var window = new Ext.Window({
        title: getLabelFromMap('windowAgregar', helpMap, 'Agregar N&uacute;mero de P&oacute;liza'),
        id: 'windowAgregarNum',
        width: 450,
        height:400,
        modal: true,
        minWidth: 300,
        minHeight: 100,
        layout: 'fit',
        plain:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        closable: false,
        items: formPanel,
        //se definen los botones del formulario
            buttons : [ {
                text : getLabelFromMap('cmdGuardar', helpMap, 'Aceptar'),
                id: 'cmdGuardar',
                tooltip: getToolTipFromMap('cmdGuardar', helpMap, 'Guarda un n&uacute;mero de P&oacute;liza'),
                disabled : false,
                handler : function() {
                    if (formPanel.form.isValid()) {
                       window.hide();
                     } else {
                        Ext.Msg.alert(getLabelFromMap('400010', helpMap, 'Error'), getLabelFromMap('400013', helpMap, 'Complete la informaci&oacute;n requerida'));
                     }
                }
            },
             {
                 text : getLabelFromMap('cmdRegresar', helpMap, 'Cancelar'),
                 tooltip: getToolTipFromMap('cmdRegresar', helpMap, 'Cancelar y pasar a la pantalla anterior'),
                 id: 'cmdRegresar',
                 handler : function() {
					Ext.Msg.show({
					   title:'Aviso',
					   msg: 'Se perderá la informaci&oacute;n cargada, ¿Desea continuar?',
					   buttons: Ext.Msg.OKCANCEL,
					   icon: Ext.MessageBox.QUESTION,
					   fn: function (buttonId, text){
					   			if(buttonId == 'ok')window.close();
					   		}
					});
                 		
                    }
            }/*,{
                 text : getLabelFromMap('cmdFormula', helpMap, 'Formula'),
                 tooltip: getToolTipFromMap('cmdFormula', helpMap, 'Formula'),
                 id: 'cmdFormula',
                 handler : function() {
                 //window.close();
                    }
            }*/
            ]
    	});


   function actualizaFolioActual(){
   		   formPanel.findById("nmFolioAct").setValue('');	
   		   formPanel.findById("nmFolioAct").setValue(formPanel.findById("nmFolioIni").getValue());
   };
   
   function validarFolios(){
   		 
   		if (formPanel.findById("nmFolioIni").getValue()!="" && formPanel.findById("nmFolioFin").getValue()!="") {
   			if (eval(formPanel.findById("nmFolioIni").getValue()) >= eval(formPanel.findById("nmFolioFin").getValue())){
   			       Ext.Msg.alert(getLabelFromMap('400023', helpMap, 'Informacion'), getLabelFromMap('400024', helpMap, 'El folio de Inicio debe ser menor que el folio de Fin.'));
   			       var folFin = eval(formPanel.findById("nmFolioIni").getValue()) + 1;
   			       formPanel.findById("nmFolioFin").setValue(folFin);	
   			}
   		}
   };
    
    dsFormaCalculoFolio.load();
    dsIndicadorSP.load();
    dsProceso.load();
    dsUsaCombinacion.load();
    dsTipoPoliza.load();
   

    window.show();

};



// Funcion de Editar la Numeracion de polizas
function editarNum(_cdElemento, _cdUnieco, _cdRamo) {

     var dsFormaCalculoFolio = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_COMBO_FORMA_CALCULO_FOLIO
            }),
            reader: new Ext.data.JsonReader({
            root: 'formaCalculoFolioNroIncisos',
            id: 'id'
            },[
           {name: 'id', type: 'string',mapping:'codigo'},
           {name: 'texto', type: 'string',mapping:'descripcion'}
           
        ]),
        remoteSort: true
    });


    var dsIndicadorSP = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_COMBO_INDICADOR_SP
            }),
            reader: new Ext.data.JsonReader({
            root: 'indicador_SP_NroIncisos',
            id: 'id'
            },[
           {name: 'id', type: 'string',mapping:'codigo'},
           {name: 'texto', type: 'string',mapping:'descripcion'}
        ]),
        remoteSort: true
    });
    
     var dsProceso = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_COMBO_PROCESO_POLIZA
            }),
            reader: new Ext.data.JsonReader({
            root: 'comboProcesoPoliza',
            id: 'id'
            },[
            {name: 'id', type: 'string',mapping:'codigo'},
            {name: 'texto', type: 'string',mapping:'descripcion'}
        ])
    });
    
    var dsUsaCombinacion = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_COMBO_USA_AGRUPACION
            }),
            reader: new Ext.data.JsonReader({
            root: 'siNo',
            id: 'id'
            },[
            {name: 'id', type: 'string',mapping:'codigo'},
            {name: 'texto', type: 'string',mapping:'descripcion'}
        ])
    });
    
    var dsTipoPoliza = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_COMBO_TIPO_POLIZA
            }),
            reader: new Ext.data.JsonReader({
            root: 'listaTipoPoliza',
            id: 'id'
            },[
            {name: 'id', type: 'string',mapping:'codigo'},
            {name: 'texto', type: 'string',mapping:'descripcion'}
        ])
    });
    
    //se define la forma en que se van a leer los datos que envie la action
        var _jsonFormReader = new Ext.data.JsonReader( {

            //indica el arreglo a leer
            root : 'MNumeroPolizasManagerList',

            //indica la cantidad de registro que debe leerse
            totalProperty: 'total',

            //indica el resultado de la respuesta de la action
            successProperty : 'success'

        }, [ {
               name : 'cdElementoId',mapping : 'cdElemento',type : 'string'
        }, {
               name : 'cdPerson',type : 'string',mapping : 'cdPerson'
        },{
               name : 'dsElemenId', mapping : 'dsElemen', type : 'string'
        },{
               name :'dsUniEco', mapping : 'dsUniEco', type : 'string'
        },{
               name : 'cdRamo1',type : 'string',mapping : 'cdRamo'
        },{
               name : 'dsRamo',type : 'string',mapping : 'dsRamo'
        },{
               name : 'dsElemen',type : 'string',mapping : 'dsElemen'
        },{
               name : 'cdUniEco',type : 'string',mapping : 'cdUniEco'
        },{
               name : 'dsUniEcoId',type : 'string',mapping : 'dsUniEco'
        },{
               name : 'dsCalculo',type : 'string',mapping : 'dsCalculo'
        },{
               name : 'indSufPre',type : 'string',mapping : 'indSufPre'
        },{
               name : 'dsIndSufPre',type : 'string',mapping : 'dsIndSufPre'
        },{
               name : 'indCalc',type : 'string',mapping : 'indCalc'
        },{
               name : 'dsCalculo',type : 'string',mapping : 'dsCalculo'
        },{
               name : 'nmFolioFin',type : 'string',mapping : 'nmFolioFin'
        },{
               name : 'nmFolioIni',type : 'string',mapping : 'nmFolioIni'
        },{
               name : 'dsSufPre',type : 'string',mapping : 'dsSufPre'
        },{
               name : 'nmFolioAct',type : 'string',mapping : 'nmFolioAct'
        },{
               name : 'dsAgrupa',type : 'string',mapping : 'dsAgrupa'
        },{
               name : 'swAgrupa',type : 'string',mapping : 'swAgrupa'
        },{
               name : 'swAgrupa',type : 'string',mapping : 'swAgrupa'
        },{
               name : 'cdProceso',type : 'string',mapping : 'cdProceso'
        },{
               name : 'dsProceso',type : 'string',mapping : 'dsProceso'
        },{
               name : 'dsTipPol',type : 'string',mapping : 'dsTipPol'
        },{
               name : 'cdNumPol',type : 'string',mapping : 'cdNumPol'
        },{
               name : 'cdTipPol',type : 'string',mapping : 'cdTipPol'
        }
        
        
        
       ]);
    
    
   
	formulaPanelEditar=new Ext.Panel({
    	layout:'column',
		border: false,
		width : 350,
		//column items:
		items:[
			{
				columnWidth:.6,
               	layout: 'form',
               	border: false,
				items:[
					{
						xtype: 'textfield', 
                		fieldLabel: getLabelFromMap('dsCalculo', helpMap, "F&oacute;rmula"), 
                		tooltip: getToolTipFromMap('dsCalculo', helpMap, 'Formula de Calculo de Folio'), 
		        		hasHelpIcon:getHelpIconFromMap('dsCalculo',helpMap),
	    				Ayuda:getHelpTextFromMap('dsCalculo',helpMap),
                		name: 'dsCalculo', 
                		id: 'dsCalculo',
                		width: 100,
                		blankText: 'Debe elegir una Expresi&oacute;n',
						disabled: true,
						readOnly: true
                	}
			  	]
			  },{
			  	columnWidth:.4,
               	layout: 'form',
               	border: false,
               	//Componentes agregados para las Expresiones
				items:[
					{
			  			xtype:'button',
                		text: 'Expresi&oacute;n',
                		id: 'btnExpresion',
                		name: 'btnExpresion',
                		buttonAlign: "left",
                		handler: function() {
                			//Si el campo Formula ya tiene valor, mostraremos la expresión con ése código, para que sea editada:
                			if(Ext.getCmp('dsCalculo').getValue() != ""){
                				ExpresionesVentana2(Ext.getCmp('dsCalculo').getValue(), Ext.getCmp('hidden-codigo-expresion-session').getValue());
                			}else{
                				//Si no tiene valor el campo Formula, creamos un nuevo codigoExpresion
                				var connect = new Ext.data.Connection();
								connect.request ({
									url:'atributosVariables/ObtenerCodigoExpresion.action',
									callback: function (options, success, response) {
										try{
											//alert(Ext.util.JSON.decode(response.responseText).codigoExpresion);
											Ext.getCmp('hidden-codigo-expresion-numeracion-polizas').setValue(Ext.util.JSON.decode(response.responseText).codigoExpresion);
											ExpresionesVentana2(Ext.getCmp('hidden-codigo-expresion-numeracion-polizas').getValue(),Ext.getCmp('hidden-codigo-expresion-session').getValue());
											Ext.getCmp('dsCalculo').setValue( Ext.getCmp('hidden-codigo-expresion-numeracion-polizas').getValue() );
										}catch(e){
										}
									}
								});
                			}
						}
                	},{
						xtype:'hidden',
						id:'hidden-codigo-expresion-session',
						name:'codigoExpresionSession', 
						value:'EXPRESION_NUMERACION_POLIZAS'
					},{
                		xtype:'hidden',                	
                		id:'hidden-codigo-expresion-numeracion-polizas',
		           		name: "codigoExpresion"
                	}
                ]
			}
		]//end column items
	});
	
       
//se define el formulario
var formPanel = new Ext.FormPanel ({
		   id: 'panelAgregarNum',
           labelWidth : 100,
           labelAlign:'right',
           
			//action a invocar al hacer al cargar(load) del formulario
            url : _ACTION_GET_NUMERO_POLIZA,

            baseParams : {cdUniEco: _cdUnieco,
                          cdElemento: _cdElemento,
                          cdRamo: _cdRamo
                         },

            bodyStyle : 'padding:5px 5px 0',

			//se setea el reader que va a usar el form cuando se cargue
            reader : _jsonFormReader,
            //reader : test(),
            //defaults : {width : 200},
            defaultType : 'textfield',
            //se definen los campos del formulario
            items : [
                {
                    xtype: 'hidden',
                    id: 'cdPerson'
                },
                 {
                    xtype: 'hidden',
                    id: 'numPolCdElemento'
                },
                {
                    xtype: 'hidden',
                    id: 'numPolCdUniEco'
                },
                {
                    xtype: 'hidden',
                    id: 'numPolCdRamo'
                },
                {
                    xtype: 'combo',
                     tpl: '<tpl for="."><div ext:qtip="{id}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
                    store: dsIndicadorSP,
                    displayField:'texto',
                    valueField:'id',
                    hiddenName: 'indSufPre',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('indSufPreId', helpMap, "Indicador (S/P)"),
                    tooltip: getToolTipFromMap('indSufPreId', helpMap, 'Indicador (S/P)'),
			        hasHelpIcon:getHelpIconFromMap('indSufPreId',helpMap),
		    		Ayuda:getHelpTextFromMap('indSufPreId',helpMap),
                    width: 200,
                    emptyText: getLabelFromMap('indSufPreId', helpMap, 'Seleccione ...'),
                    selectOnFocus:true,
                    forceSelection:true,
                    id:'indSufPreId',
                    onSelect: function (record) {
	                            				
	                            				if (record.get("id")=="N"){
	                            				          
	                            							formPanel.findById("dsSufPre").setDisabled(true);
	                            							formPanel.findById("dsSufPre").allowBlank = true;
	                            							formPanel.findById("dsSufPre").setValue('');
	                            							
	                            				}
	                            				
	                            				if (record.get("id")=="S" || record.get("id")=="P"){
	                            				
	                            							formPanel.findById("dsSufPre").setDisabled(false);
	                            							formPanel.findById("dsSufPre").allowBlank = false;
	                            							
	                            				}
	                            				
	                            				this.setValue(record.get('id'));
	                            				this.collapse();
	                            				}
                } ,
                {
                xtype: 'textfield',
                 fieldLabel: getLabelFromMap('dsSufPre', helpMap, "Valor"),
                 tooltip: getToolTipFromMap('dsSufPre', helpMap, 'Valor'),
 		         hasHelpIcon:getHelpIconFromMap('dsSufPre',helpMap),
		    	 Ayuda:getHelpTextFromMap('dsSufPre',helpMap),
                 name: 'dsSufPre', 
                 id: 'dsSufPre',
                 width: '200'
                 },
                {
                    xtype: 'combo',
                     tpl: '<tpl for="."><div ext:qtip="{id}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
                    store: dsFormaCalculoFolio,
                    displayField:'texto',
                    valueField:'id',
                    hiddenName: 'indCalc',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('indCalcId', helpMap, "Forma C&aacute;lculo Folio"),
                    tooltip: getToolTipFromMap('indCalcId', helpMap, 'Forma C&aacute;lculo Folio'),
			        hasHelpIcon:getHelpIconFromMap('indCalcId',helpMap),
		    		Ayuda:getHelpTextFromMap('indCalcId',helpMap),
                    width: 200,
                    emptyText: getLabelFromMap('indCalcId', helpMap, 'Seleccione Forma C&aacute;lculo Folio...'),
                    selectOnFocus:true,
                    forceSelection:true,
                    allowBlank : false,
                    blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
                    id:'indCalcId',
                    onSelect: function (record) {
	                            				
	                            				if (record.get("id")=="1"){
	                            						    
	                            							
	                            							formPanel.findById("nmFolioIni").setDisabled(false);
	                            							formPanel.findById("nmFolioFin").setDisabled(false);
	                            							
	                            							formPanel.findById("nmFolioIni").allowBlank =  false;
	                            							formPanel.findById("nmFolioFin").allowBlank = false;
	                            							
	                            							formPanel.findById("dsCalculo").setValue('');
	                            							formPanel.findById("dsCalculo").allowBlank = true;
	                            							formPanel.findById("dsCalculo").setDisabled(true);
	                            							Ext.getCmp('btnExpresion').setDisabled(true);
	                            							
	                            							
	                            				}
	                            				
	                            				if (record.get("id")=="2"){
	                            				
	                            							
	                            							formPanel.findById("nmFolioIni").setDisabled(true);
	                            							formPanel.findById("nmFolioFin").setDisabled(true);
	                            							
	                            							formPanel.findById("nmFolioIni").setValue('');
	                            							formPanel.findById("nmFolioFin").setValue('');
	                            							
	                            							
	                            							formPanel.findById("nmFolioIni").allowBlank = true;
	                            							formPanel.findById("nmFolioFin").allowBlank = true;
	                            							
	                            							formPanel.findById("dsCalculo").allowBlank = false;
	                            							formPanel.findById("dsCalculo").setDisabled(false);
	                            							Ext.getCmp('btnExpresion').setDisabled(false);
	                            							
	                            				}
	                            				
	                            				this.setValue(record.get('id'));
	                            				this.collapse();	
	                            		        }
                } ,
                
                
                formulaPanelEditar,
				
				/*{xtype: 'textarea', fieldLabel: getLabelFromMap('txtADsCalculo', helpMap, "F&oacute;rmula de C&aacute;lculo de Folio"), tooltip: getToolTipFromMap('txtADsCalculo', helpMap, 'Formula de Calculo de Folio'),
				name: 'dsCalculo',
				blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
				heigth:150, id: 'dsCalculo'},*/	

                /*Combo Proceso Polizas*/
                {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{id}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
                    store: dsProceso,
                    displayField:'texto',
                    valueField:'id',
                    hiddenName: 'cdProceso',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('cdProcesoId', helpMap, "Proceso"),
                    tooltip: getToolTipFromMap('cdProcesoId', helpMap, "Proceso"),
			        hasHelpIcon:getHelpIconFromMap('cdProcesoId',helpMap),
		    		Ayuda:getHelpTextFromMap('cdProcesoId',helpMap),
                    width: 200,
                    emptyText: getLabelFromMap('cdProcesoId', helpMap, 'Selecione ...'),
                    selectOnFocus:true,
                    forceSelection:true,
                    //labelSeparator:'',
                    id:'cdProcesoId',
                    onSelect: function (record) {
	                            				
	                            				
	                            				
	                            				this.setValue(record.get('id'));
	                            				this.collapse();
	                            				}
                } ,
                
                {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{id}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
                    store: dsUsaCombinacion,
                    displayField:'texto',
                    valueField:'id',
                    hiddenName: 'swAgrupa',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('usaCombinacionId', helpMap, "¿Usa Agrupaci&oacute;n?"),
                    tooltip: getToolTipFromMap('usaCombinacionId', helpMap, "¿Usa Agrupaci&oacute;n?"),
			        hasHelpIcon:getHelpIconFromMap('usaCombinacionId',helpMap),
		    		Ayuda:getHelpTextFromMap('usaCombinacionId',helpMap),
                    width: 200,
                    emptyText: getLabelFromMap('usaCombinacionId', helpMap, 'Selecione ...'),
                    selectOnFocus:true,
                    forceSelection:true,
                    //labelSeparator:'',
                    id:'usaCombinacionId',
                    onSelect: function (record) {
	                            				
	                            				                            				
	                            				this.setValue(record.get('id'));
	                            				this.collapse();
	                            				}
                } ,
                
                {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{id}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
                    store: dsTipoPoliza,
                    displayField:'texto',
                    valueField:'id',
                    hiddenName: 'cdTipPol',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('tipoPolizaId', helpMap, "Tipo Poliza"),
                    tooltip: getToolTipFromMap('tipoPolizaId', helpMap, "Tipo Poliza"),
			        hasHelpIcon:getHelpIconFromMap('tipoPolizaId',helpMap),
		    		Ayuda:getHelpTextFromMap('tipoPolizaId',helpMap),
                    width: 200,
                    emptyText: getLabelFromMap('tipoPolizaId', helpMap, 'Selecione Tipo P&oacute;liza...'),
                    selectOnFocus:true,
                    forceSelection:true,
                    //labelSeparator:'',
                    id:'tipoPolizaId',
                    onSelect: function (record) {
	                            				
	                            					                            				
	                            				this.setValue(record.get('id'));
	                            				this.collapse();
	                            				}
                } ,
				{
				xtype: 'textfield', 
				fieldLabel: getLabelFromMap('nmFolioIni', helpMap, "Folio Inicial"), 
				tooltip: getToolTipFromMap('nmFolioIni', helpMap, 'Folio Inicial'), 
		        hasHelpIcon:getHelpIconFromMap('nmFolioIni',helpMap),
	    		Ayuda:getHelpTextFromMap('nmFolioIni',helpMap),
				name: 'nmFolioIni', 
				id: 'nmFolioIni',
				width: '200',
				listeners: {
						    'change': function(){
						    	 validarFolios();
						         actualizaFolioActual();
						    }
						  }
				},
				{
				xtype: 'textfield', 
				fieldLabel: getLabelFromMap('nmFolioFin', helpMap, "Folio Final"), 
				tooltip: getToolTipFromMap('nmFolioFin', helpMap, 'Folio Final'), 
		        hasHelpIcon:getHelpIconFromMap('nmFolioFin',helpMap),
	    		Ayuda:getHelpTextFromMap('nmFolioFin',helpMap),
				name: 'nmFolioFin', 
				id: 'nmFolioFin',
				width: '200',
				listeners: {
						    'change': function(){
						         validarFolios();
						    }
						  }
				},
				{
				xtype: 'textfield', 
				fieldLabel: getLabelFromMap('nmFolioAct', helpMap, "Folio Actual"), 
				tooltip: getToolTipFromMap('nmFolioAct', helpMap, 'Folio Actual'), 
		        hasHelpIcon:getHelpIconFromMap('nmFolioAct',helpMap),
	    		Ayuda:getHelpTextFromMap('nmFolioAct',helpMap),
				name: 'nmFolioAct', 
				readOnly: true,  
				id: 'nmFolioAct',
				width: '200'
				}

            ]
        });


//Windows donde se van a visualizar la pantalla
var window = new Ext.Window({
        title: getLabelFromMap('windowEditar', helpMap, 'Editar N&uacute;mero de P&oacute;liza'),
        id: 'windowAgregarNum',
        width: 450,
        height:400,
        minWidth: 300,
        minHeight: 100,
        layout: 'fit',
        plain:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        labelAlign:'right',
        closable: false,
        items: formPanel,
        //se definen los botones del formulario
            buttons : [ {
                text : getLabelFromMap('cmdGuardarEdt', helpMap, 'Aceptar'),
                tooltip: getToolTipFromMap('cmdGuardarEdt', helpMap, 'Guarda la actualizaci&oacute;n'),
                id: 'cmdGuardarEdt',
                disabled : false,
                handler : function() {
                    if (formPanel.form.isValid()) {
                        window.hide();
                     } else {
                        Ext.Msg.alert(getLabelFromMap('400010', helpMap, 'Error'), getLabelFromMap('400013', helpMap, 'Complete la informaci&oacute;n requerida'));
                     }
                }
            },
             {
                 text : getLabelFromMap('cmdRegresarEdt', helpMap, 'Cancelar'),
                 id: 'cmdRegresarEdt',
                 tooltip: getToolTipFromMap('cmdRegresarEdt', helpMap, 'Cancelar y pasar a la pantalla anterior'),
                 handler : function() {
                 			Ext.Msg.show({
						   title:'Aviso',
						   msg: 'Se perderá la informaci&oacute;n cargada, ¿Desea continuar?',
						   buttons: Ext.Msg.OKCANCEL,
						   icon: Ext.MessageBox.QUESTION,
						   fn: function (buttonId, text){
						   			if(buttonId == 'ok')window.close();
						   		}
							});
                    }
            }]
    	});



	 function actualizaFolioActual(){
   		   formPanel.findById("nmFolioAct").setValue('');	
   		   formPanel.findById("nmFolioAct").setValue(formPanel.findById("nmFolioIni").getValue());
   };
   
   function validarFolios(){
   		 
   		if (formPanel.findById("nmFolioIni").getValue()!="" && formPanel.findById("nmFolioFin").getValue()!="") {
   			if (eval(formPanel.findById("nmFolioIni").getValue()) >= eval(formPanel.findById("nmFolioFin").getValue())){
   			       Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400024', helpMap, 'El folio de Inicio debe ser menor que el folio de Fin.'));
   			       var folFin = eval(formPanel.findById("nmFolioIni").getValue()) + 1;
   			       formPanel.findById("nmFolioFin").setValue(folFin);	
   			}
   		}
   };

 
    dsFormaCalculoFolio.load();
  
    dsIndicadorSP.load();
    dsProceso.load();
    dsUsaCombinacion.load();
    dsTipoPoliza.load();

    
    window.show();
     //se carga el formulario con los datos de la estructura a editar
        formPanel.form.load({
    				waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
                    waitMsg : getLabelFromMap('400028',helpMap,'Leyendo datos ...'),                                                            
                    success:function(){
                                     if (formPanel.findById("indCalcId").getValue()=="1"){
	                            						    
	                            							
	                            							formPanel.findById("nmFolioIni").setDisabled(false);
	                            							formPanel.findById("nmFolioFin").setDisabled(false);
	                            							
	                            							formPanel.findById("nmFolioIni").allowBlank =  false;
	                            							formPanel.findById("nmFolioFin").allowBlank = false;
	                            							
	                            							formPanel.findById("dsCalculo").setValue('');
	                            							formPanel.findById("dsCalculo").allowBlank = true;
	                            							formPanel.findById("dsCalculo").setDisabled(true);
	                            							Ext.getCmp('btnExpresion').setDisabled(true);
	                            				}
	                            				
	                            	if (formPanel.findById("indCalcId").getValue()=="2"){
	                            				
	                            							
	                            							formPanel.findById("nmFolioIni").setDisabled(true);
	                            							formPanel.findById("nmFolioFin").setDisabled(true);
	                            							
	                            							formPanel.findById("nmFolioIni").setValue('');
	                            							formPanel.findById("nmFolioFin").setValue('');
	                            							
	                            							
	                            							formPanel.findById("nmFolioIni").allowBlank = true;
	                            							formPanel.findById("nmFolioFin").allowBlank = true;
	                            							
	                            							formPanel.findById("dsCalculo").allowBlank = false;
	                            							formPanel.findById("dsCalculo").setDisabled(false);
	                            							Ext.getCmp('btnExpresion').setDisabled(false);
	                            							
	                            	}
	                            	
	                            	if (formPanel.findById("indSufPreId").getValue()=="N"){
	                            				          
	                            							formPanel.findById("dsSufPre").setDisabled(true);
	                            							formPanel.findById("dsSufPre").allowBlank = true;
	                            							formPanel.findById("dsSufPre").setValue('');
	                            							
	                            				}
	                            				
	                            	if (formPanel.findById("indSufPreId").getValue() =="S" || formPanel.findById("indSufPreId").getValue() =="P"){
	                            				
	                            							formPanel.findById("dsSufPre").setDisabled(false);
	                            							formPanel.findById("dsSufPre").allowBlank = false;
	                            							
	                            	}
	                            	
                        }
                });


};
