// Funcion de Agregar Desglose de Polizas
function editar (record) {


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
               name : 'dsElemen', mapping : 'dsElemen', type : 'string'
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
               name : 'dsUniEco',type : 'string',mapping : 'dsUniEco'
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
        }
       ]);
    
    
   
//se define el formulario
var formPanel = new Ext.FormPanel ({

           labelWidth : 100,

			//action a invocar al hacer al cargar(load) del formulario
            url : _ACTION_GET_NUMERO_POLIZA,

            baseParams : {cdUniEco: record.get("cdUniEco"),
                          cdElemento: record.get("cdElemento"),
                          cdRamo: record.get("cdRamo")
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
                /*
                {
                    xtype: 'combo',
                    labelWidth: 50,
                    tpl: '<tpl for="."><div ext:qtip="{cdElemento}.{cdPerson}.{dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
                    store: dsClientesCorp,
                    displayField:'dsElemen',
                    valueField: 'cdElemento',
                    hiddenName: 'cdElemento',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: "Cliente",
                    width: 200,
                    emptyText:'Seleccionar Cliente ...',
                    selectOnFocus:true,
                    labelSeparator:'',
                    allowBlank : false,
                    id:'cdElementoId',
                    onSelect: function (record) {
                    	
                        formPanel.findById(('cdPerson')).setValue(record.get("cdPerson"));
                        dsAseguradora.removeAll();
	                    dsAseguradora.load({
	                                    	params: {cdElemento: record.get("cdElementoId")},
	                         	            waitMsg: 'Espere por favor....'
	                            		 });
                        
                        this.collapse();
                        this.setValue(record.get("cdElemento"));
                        }
                },
                {
                    xtype: 'combo', 
                    labelWidth: 70, 
                    tpl: '<tpl for="."><div ext:qtip="{cdUniEco}. {dsUniEco}" class="x-combo-list-item">{dsUniEco}</div></tpl>',
	                store: dsAseguradora,
	                displayField:'dsUniEco', 
	                valueField:'cdUniEco', 
	                hiddenName: 'cdUniEco', 
	                typeAhead: true,
	                mode: 'local', 
	                triggerAction: 'all', 
	                fieldLabel: "Aseguradora", 
	                width: 200, 
	                emptyText:'Seleccionar Aseguradora...',
	                selectOnFocus:true, 
	                labelSeparator:'', 
	                allowBlank : false,
	                id: 'cdUniEcoId',// name: 'cdUniEco'
	                onSelect: function (record) {
	                            				
	                            				dsProductos.removeAll();
	                            				dsProductos.load({
	                            						params: {cdElemento: formPanel.findById(('cdElementoId')).getValue() ,cdunieco: record.get('cdUniEco')},
	                            					});
	                            				this.setValue(record.get('cdUniEco'));
	                            				this.collapse();	
	                            		        }
	             },*/

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
                    emptyText:'Seleccionar Producto...',
                    selectOnFocus:true,
                    forceSelection:true,
                    labelSeparator:'',
                    allowBlank : false,
                    id:'cdRamoId'
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
                    emptyText:'Seleccionar ...',
                    selectOnFocus:true,
                    forceSelection:true,
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
                    emptyText:'Seleccionar Forma Calculo Folio...',
                    
                    selectOnFocus:true,
                    forceSelection:true,
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
						         actualizaFolioActual();
						    }
						  }
				},
				
				{xtype: 'textfield', fieldLabel: "Folio Final", name: 'nmFolioFin', id: 'nmFolioFin',
				listeners: {
						    'change': function(){
						         validarFolios();
						    }
						  }
				},
				
				{xtype: 'textfield', fieldLabel: "Folio Actual", name: 'nmFolioAct', readOnly: true,  id: 'nmFolioAct'}



            ]
        });
alert(3);
return;

//Windows donde se van a visualizar la pantalla
var window = new Ext.Window({
        title: 'Editar Numero de Poliza',
        width: 500,
        height:450,
        minWidth: 300,
        modal: true,
        minHeight: 100,
        layout: 'fit',
        plain:true,
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
                            url : _ACTION_GUARDAR_NUMERO_POLIZA,
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
                            url : _ACTION_GUARDAR_NUMERO_POLIZA,
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
            },
             {
                 text : 'Regresar',
                 handler : function() {
                 window.close();
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
   			       Ext.Msg.alert('Informacion', 'El folio de Inicio debe ser menor que el folio de Fin.');
   			       var folFin = eval(formPanel.findById("nmFolioIni").getValue()) + 1;
   			       formPanel.findById("nmFolioFin").setValue(folFin);	
   			}
   		}
   };
   
   
    dsProductos.load({
	                  params: {cdElemento: record.get("cdElemento") ,cdunieco: record.get('cdUniEco')}
	                 });
 
    dsFormaCalculoFolio.load();
  
    dsIndicadorSP.load();

  
    formPanel.findById("cdRamoId").setValue(record.get('cdRamo'));
    
    window.show();
     //se carga el formulario con los datos de la estructura a editar
        formPanel.form.load({
    
                    waitMsg : 'leyendo datos...',
                    success:function(){
                                     if (formPanel.findById("indCalcId").getValue()=="1"){
	                            						    
	                            							
	                            							formPanel.findById("nmFolioIni").setDisabled(false);
	                            							formPanel.findById("nmFolioFin").setDisabled(false);
	                            							
	                            							formPanel.findById("nmFolioIni").allowBlank =  false;
	                            							formPanel.findById("nmFolioFin").allowBlank = false;
	                            							
	                            							formPanel.findById("dsCalculo").setValue('');
	                            							formPanel.findById("dsCalculo").allowBlank = true;
	                            							formPanel.findById("dsCalculo").setDisabled(true);
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
	                            							
	                            	}
	                            	
                        }

                });


};

