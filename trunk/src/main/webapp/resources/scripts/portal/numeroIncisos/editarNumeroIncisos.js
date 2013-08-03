// Funcion de Agregar Desglose de Polizas
editar = function(record) {


  

    var dsProductos = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_PRODUCTOS_ASEGURADORA_CLIENTE
            }),
            reader: new Ext.data.JsonReader({
            root: 'productosAseguradoraCliente',
            id: 'codigo'
            },[
           {name: 'codigo', type: 'string',mapping:'codigo'},
           {name: 'descripcion', type: 'string',mapping:'descripcion'}
        ]),
        remoteSort: true
    });
    
    
     var dsFormaCalculoFolio = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_COMBO_FORMA_CALCULO_FOLIO
            }),
            reader: new Ext.data.JsonReader({
            root: 'formaCalculoFolioNroIncisos',
            id: 'id'
            },[
           {name: 'id', type: 'string',mapping:'id'},
           {name: 'texto', type: 'string',mapping:'texto'}
           
        ]),
        remoteSort: true
    });


   var dsIndicadorNumeracion = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_COMBO_INDICADOR_NUMERACION
            }),
            reader: new Ext.data.JsonReader({
            root: 'indicadorNumeracionNroIncisos',
            id: 'id'
            },[
           {name: 'id', type: 'string',mapping:'id'},
           {name: 'texto', type: 'string',mapping:'texto'}
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
           {name: 'id', type: 'string',mapping:'id'},
           {name: 'texto', type: 'string',mapping:'texto'}
        ]),
        remoteSort: true
    });
    
   var dsTramos = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_TRAMO
            }),
            reader: new Ext.data.JsonReader({
            root: 'numeroIncisosManagerListLoadTramo',
            id: 'swSubInc',
            successProperty:"success"
            },[
            {name: 'swSubInc', type: 'string',mapping:'swSubInc'}
              ]),
        remoteSort: true
    });
    
    
    
    //se define la forma en que se van a leer los datos que envie la action
        var _jsonFormReader = new Ext.data.JsonReader( {

            //indica el arreglo a leer
            root : 'numeroIncisosManagerList',

            //indica la cantidad de registro que debe leerse
            totalProperty: 'total',

            //indica el resultado de la respuesta de la action
            successProperty : 'success'

        }, [ {
                name : 'cdElementoId1',mapping : 'cdElemento',type : 'string'
        },{
                name : 'dsElemen', mapping : 'dsElemen', type : 'string'
        },{
                name : 'dsUniEco', mapping : 'dsUniEco', type : 'string'
        },{ 
               name : 'cdPerson',type : 'string',mapping : 'cdPerson'
        },{
               name : 'cdRamo1',type : 'string',mapping : 'cdRamo'
        },{
               name : 'cdSufPreCia',type : 'string',mapping : 'cdSufPreCia'
        },{
               name : 'cdUniEco1',type : 'string',mapping : 'cdUniEco'
        },{
               name : 'dsCalculo',type : 'string',mapping : 'dsCalculo'
        },{
               name : 'dsSufPre',type : 'string',mapping : 'dsSufPre'
        },{
               name : 'indCalc',type : 'string',mapping : 'indCalc'
        },{
               name : 'indSituac1',type : 'string',mapping : 'indSituac'
        },{
               name : 'indSufPre',type : 'string',mapping : 'indSufPre'
        },{
               name : 'nmFolioFin',type : 'string',mapping : 'nmFolioFin'
        },{
               name : 'nmFolioIni',type : 'string',mapping : 'nmFolioIni'
        }
        
        
        ]);
    
    
   
//se define el formulario
var formPanel = new Ext.FormPanel ({

           labelWidth : 100,

			//action a invocar al hacer al cargar(load) del formulario
            url : _ACTION_GET_NUMERO_INCISO,

            baseParams : {cdUniEco: record.get("cdUniEco"),
                          cdElemento: record.get("cdElemento"),
                          cdRamo: record.get("cdRamo"),
                          indSituac: record.get("indSituac")
                         },

            bodyStyle : 'padding:5px 5px 0',

          
			//se setea el reader que va a usar el form cuando se cargue
            reader : _jsonFormReader,

            //reader : test(),
            defaults : {

                width : 200

            },

            defaultType : 'textfield',
            //se definen los campos del formulario
            items : [
                {
                    xtype: 'hidden',
                    name : 'cdPerson',
                    id: 'cdPerson'
                },{
                    xtype: 'textfield',
                    name : 'dsElemen',
                    fieldLabel: "Cliente",
                    disabled:true,
                    id: 'dsElemen'
                },{
                    xtype: 'textfield',
                    name : 'dsUniEco',
                    fieldLabel: "Aseguradora",
                    disabled:true,
                    id: 'dsUniEco'
                },
                /*{
                    xtype: 'combo',
                    labelWidth: 50,
                    tpl: '<tpl for="."><div ext:qtip="{cdElemento}.{cdPerson}.{dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
                    store: dsClientesCorp,
                    displayField:'dsElemen',
                    valueField: 'cdElemento',
                    hiddenName: 'cdElemento1',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: "Cliente",
                    width: 200,
                    emptyText:'Seleccionar Cliente ...',
                    selectOnFocus:true,
                    labelSeparator:'',
                    allowBlank : false,
                    id:'cdElementoId1',
                    onSelect: function (record) {
                    	
                        formPanel.findById(('cdPerson')).setValue(record.get("cdPerson"));
                        dsProductos.removeAll();
	                    dsProductos.load({
	                                    	params: {cdElemento: record.get("cdElemento1") ,cdunieco: formPanel.findById(('cdUniEcoId1')).getValue()},
	                         	            waitMsg: 'Espere por favor....'
	                            		 });
                        
                        this.collapse();
                        this.setValue(record.get("cdElemento1"));
                        }
                },
                {
                    xtype: 'combo', 
                    labelWidth: 70, 
                    tpl: '<tpl for="."><div ext:qtip="{cdUniEco}. {dsUniEco}" class="x-combo-list-item">{dsUniEco}</div></tpl>',
	                store: dsAseguradora,
	                displayField:'dsUniEco', 
	                valueField:'cdUniEco', 
	                hiddenName: 'cdUniEco1', 
	                typeAhead: true,
	                mode: 'local', 
	                triggerAction: 'all', 
	                fieldLabel: "Aseguradora", 
	                width: 200, 
	                emptyText:'Seleccionar Aseguradora...',
	                selectOnFocus:true, 
	                labelSeparator:'', 
	                allowBlank : false,
	                id: 'cdUniEcoId1',
	                onSelect: function (record) {
	                            				
	                            				dsProductos.removeAll();
	                            				dsProductos.load({
	                            						params: {cdElemento: formPanel.findById(('cdElementoId1')).getValue() ,cdunieco: record.get('cdUniEco1')},
	                            					});
	                            				this.setValue(record.get('cdUniEco1'));
	                            				this.collapse();	
	                            		        }
	             }*/
                {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                    store: dsProductos,
                    displayField:'descripcion',
                    valueField:'codigo',
                    hiddenName: 'cdRamo1',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: "Producto",
                    forceSelection: true,
                    width: 200,
                    emptyText:'Seleccione Producto...',
                    selectOnFocus:true,
                    labelSeparator:'',
                    allowBlank : false,
                    id:'cdRamoId1',
                    onSelect: function (record) {
	                            				this.setValue(record.get('codigo'));
	                            				var mStore = dsTramos;
										        var o = {start: 0};
										        mStore.baseParams = mStore.baseParams || {};
										        mStore.baseParams['cdRamo'] = formPanel.findById("cdRamoId1").getValue();
										        
										        mStore.reload(
										                  {
										                      params:{start:0,limit:20},
										                       
										                      callback : function(r,options,success) {
										                          if (success) {
										                                 if (mStore.getAt(0).get('swSubInc') == "N"){
						                            					 Ext.MessageBox.alert('Aviso','Producto No permite manejo de Sub-Incisos.');
						                            					 formPanel.findById("indSituacId1").setValue('1');
						                            					 formPanel.findById("indSituacId1").setDisabled(true);    
				                            				              }else{
				                            				              		formPanel.findById("indSituacId1").setDisabled(false);
				                            				              }
										                           
										                          }else{
										                          		alert("fallo");
										                          }
										                      }
										
										                  }
										                );
	                            				
	                            				this.setValue(record.get('codigo'));
	                            				this.collapse();	
	                            		        }
	                            		        
                } ,
                 {
                    xtype: 'combo',
                     tpl: '<tpl for="."><div ext:qtip="{id}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
                    store: dsIndicadorNumeracion,
                    displayField:'texto',
                    valueField:'id',
                    hiddenName: 'indSituac1',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: "Indicador de Numeracion",
                    forceSelection: true,
                    width: 200,
                    emptyText:'Seleccione Indicador de Numeración...',
                    selectOnFocus:true,
                    labelSeparator:'',
                    allowBlank : false,
                    id:'indSituacId1'
                } ,
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
                    fieldLabel: "Indicador (S/P)",
                    forceSelection: true,
                    width: 200,
                    emptyText:'Seleccione Indicador (S/P)',
                    selectOnFocus:true,
                    labelSeparator:'',
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
                
                 {xtype: 'textfield', fieldLabel: "Valor", name: 'dsSufPre', id: 'dsSufPre'},
                
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
                    fieldLabel: "Forma Calculo Folio",
                    forceSelection: true,
                    width: 200,
                    emptyText:'Seleccione Forma Calculo Folio...',
                    selectOnFocus:true,
                    labelSeparator:'',
                    allowBlank : false,
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
	                            							
	                            				}
	                            				
	                            				this.setValue(record.get('id'));
	                            				this.collapse();	
	                            		        }
                } ,
                
                {xtype: 'textarea', fieldLabel: "Formula de Calculo de Folio", name: 'dsCalculo',heigth:150, id: 'dsCalculo'},	

               

				{xtype: 'textfield', fieldLabel: "Folio Inicial", name: 'nmFolioIni', id: 'nmFolioIni',
				listeners: {
						    'change': function(){
						    	 validarFolios();
						        
						    }
						  }
				},
				
				{xtype: 'textfield', fieldLabel: "Folio Final", name: 'nmFolioFin', id: 'nmFolioFin',
				listeners: {
						    'change': function(){
						         validarFolios();
						    }
						  }
				}
				
            ]});


//Windows donde se van a visualizar la pantalla
var window = new Ext.Window({
        title: 'Editar Numero de Inciso',
        width: 500,
        height:450,
        minWidth: 300,
        minHeight: 100,
        layout: 'fit',
        plain:true,
        modal: true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: formPanel,
        //se definen los botones del formulario
            buttons : [ {
                text : 'Guardar',
                disabled : false,
                handler : function() {
                    if (formPanel.form.isValid()) {
                        formPanel.form.submit( {
                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_GUARDAR_NUMERO_INCISO,
                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert('Aviso','Guardado satisfactoriamente');
                                window.close();
                                },
                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.MessageBox.alert('Error','Problemas al Guardar: ' + action.result.errorMessages[0]);
                                },
                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : 'Guardando Actualizacion de Datos ...'
                        });
                     } else {
                        Ext.Msg.alert('Informacion', 'Por favor complete la informacion requerida!');
                     }
                }
            },{
                text : 'Guardar y Agregar',
                disabled : false,
                handler : function() {
                    if (formPanel.form.isValid()) {
                        formPanel.form.submit( {
                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_GUARDAR_NUMERO_INCISO,
                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert('Aviso','Guardado satisfactoriamente');
                                 dsProductos.removeAll();
                                    dsAseguradora.removeAll();
                                    
                                    dsFormaCalculoFolio.removeAll();
								    dsIndicadorNumeracion.removeAll();
								    dsIndicadorSP.removeAll();
								    
                                    
                                    dsFormaCalculoFolio.load();
								    dsIndicadorNumeracion.load();
								    dsIndicadorSP.load();
								    
								    
								    
								    formPanel.findById("cdElementoId").setValue('');
								    formPanel.findById("cdUniEcoId").setValue('');
								    formPanel.findById("cdRamoId").setValue('');
								    formPanel.findById("indSituacId").setValue('');
								    formPanel.findById("indSufPreId").setValue('');      
								    formPanel.findById("indCalcId").setValue('');   
								    
								    formPanel.findById("indSituacId").setDisabled(false);  
								    formPanel.findById("dsCalculo").setDisabled(false);  
								    formPanel.findById("nmFolioIni").setDisabled(false);  
	                            	formPanel.findById("nmFolioFin").setDisabled(false);  
	                            	 
								    formPanel.findById("cdPerson").setValue('');
								    formPanel.findById("dsCalculo").setValue('');
								    formPanel.findById("dsSufPre").setValue('');
								    formPanel.findById("nmFolioIni").setValue('');
								    formPanel.findById("nmFolioFin").setValue('');      
                                },
                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.MessageBox.alert('Error','Problemas al Guardar: ' + action.result.errorMessages[0]);
                                },
                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : 'Guardando Actualizacion de Datos ...'
                        });
                     } else {
                        Ext.Msg.alert('Informacion', 'Por favor complete la informacion requerida!');
                     }
                }
            },
             {
                 text : 'Regresar',
                 handler : function() {
                 window.close();
                    }
            }]
    	});

    //dsAseguradora.load({params: {cdElemento: record.get("cdElemento")}});
    dsProductos.load({
	                  params: {cdElemento: record.get("cdElemento") ,cdunieco: record.get('cdUniEco')}
	                 });
    //dsClientesCorp.load();
    dsFormaCalculoFolio.load();
    dsIndicadorNumeracion.load();
    dsIndicadorSP.load();

   // formPanel.findById("cdElementoId1").setValue(record.get('cdElemento'));
   // formPanel.findById("cdUniEcoId1").setValue(record.get('cdUniEco'));
    formPanel.findById("cdRamoId1").setValue(record.get('cdRamo'));
    
    window.show();
     //se carga el formulario con los datos de la estructura a editar
        formPanel.form.load( {
    
                    waitMsg : 'leyendo datos...',
                    success:function(){
                    
                    
                    
                                    if (formPanel.findById("indCalcId").getValue()=="1"){
	                            				
	                            				formPanel.findById("nmFolioIni").setDisabled(false);
	                            				
	                            							formPanel.findById("nmFolioIni").setDisabled(false);
	                            							formPanel.findById("nmFolioFin").setDisabled(false);
	                            								                            							
	                            							formPanel.findById("nmFolioIni").allowBlank = false;
	                            							formPanel.findById("nmFolioFin").allowBlank = false;
	                            							
	                            							formPanel.findById("dsCalculo").allowBlank = true;
	                            							formPanel.findById("dsCalculo").setValue('');
	                            							formPanel.findById("dsCalculo").setDisabled(true);
	                            							
	                            							
	                            							
	                            						   
	                            				}
	                            				
	                            				if (formPanel.findById("indCalcId").getValue()=="2"){
	                            				
	                            							
	                            						    formPanel.findById("nmFolioIni").allowBlank = true;
	                            							formPanel.findById("nmFolioFin").allowBlank = true;
	                            							
	                            							formPanel.findById("nmFolioIni").setValue('');
	                            							formPanel.findById("nmFolioFin").setValue('');
	                            							
	                            							formPanel.findById("nmFolioIni").setDisabled(true);
	                            							formPanel.findById("nmFolioFin").setDisabled(true);
	                            							
	                            							formPanel.findById("dsCalculo").allowBlank = false;
	                            							formPanel.findById("dsCalculo").setDisabled(false);
	                            				}
	                            	
	                            	 var mStore = dsTramos;
										        var o = {start: 0};
										        mStore.baseParams = mStore.baseParams || {};
										        mStore.baseParams['cdRamo'] = formPanel.findById("cdRamoId1").getValue();
										        
										        mStore.reload(
										                  {
										                      params:{start:0,limit:20},
										                       
										                      callback : function(r,options,success) {
										                          if (success) {
										                                 if (mStore.getAt(0).get('swSubInc') == "N"){
						                            					 Ext.MessageBox.alert('Aviso','Producto No permite manejo de Sub-Incisos.');
						                            					 formPanel.findById("indSituacId1").setValue('I');
						                            					 formPanel.findById("indSituacId1").setDisabled(true);    
				                            				              }else{
				                            				              		formPanel.findById("indSituacId1").setDisabled(false);
				                            				              }
										                           
										                          }else{
										                          		alert("fallo");
										                          }
										                      }
										
										                  }
										                );
                     
                    }
        });
        
      
      function validarFolios(){
   		 
   		if (formPanel.findById("nmFolioIni").getValue()!="" && formPanel.findById("nmFolioFin").getValue()!="") {
   			if (eval(formPanel.findById("nmFolioIni").getValue()) > eval(formPanel.findById("nmFolioFin").getValue())){
   			       Ext.Msg.alert('Informacion', 'El folio de Inicio debe ser menor que el folio de Fin.');
   			       var folFin = eval(formPanel.findById("nmFolioIni").getValue()) + 1;
   			       formPanel.findById("nmFolioFin").setValue(folFin);	
   			}
   		}
   }; 
   

};

