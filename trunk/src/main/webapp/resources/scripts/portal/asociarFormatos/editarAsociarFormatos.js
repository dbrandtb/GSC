//######################### COMIENZO EDITAR ASOCIAR FORMATOS ORDEN TRABAJO ################################## 
function editar (codigoFormato){
var codigoAsocia=codigoFormato;
//***************Recarga del store si es que se seleccionó un formato para editar**************************
var readerFormato = new Ext.data.JsonReader(
		{
			root:'aotEstructuraList',
			totalProperty: 'totalCount',
			successProperty : 'success'
		},
		[
		{name: 'cdasocia',  mapping:'cdasocia',  type:'string'},
		{name: 'cdforord',  mapping:'cdforord',  type:'string'},
		{name: 'dsformatoorden', mapping:'dsformatoorden', type:'string'},	
		{name: 'cdproces',   mapping:'cdproces',   type:'string'},
		{name: 'dsproces',   mapping:'dsproces',   type:'string'},
		{name: 'cdelemento',   mapping:'cdelemento',   type:'string'},
		{name: 'dselemen',   mapping:'dselemen',   type:'string'},
		{name: 'cdperson', mapping:'cdperson', type:'string'},
		{name: 'cdunieco', mapping:'cdunieco', type:'string'},
		{name: 'dsunieco', mapping:'dsunieco', type:'string'},
		{name: 'codRamo', mapping:'cdramo', type:'string'},
		{name: 'dsramo', mapping:'dsramo', type:'string'},
		{name: 'cdfolioaon', mapping:'cdfolioaon', type:'string'},
		{name: 'dsfolioaon', mapping:'dsfolioaon', type:'string'},
		{name: 'cdfoliocia', mapping:'cdfoliocia', type:'string'},
		{name: 'dsfoliocia', mapping:'dsfoliocia', type:'string'},
		{name: 'dsformaon', mapping:'dsformaon', type:'string'},
		{name: 'dsformcia', mapping:'dsformcia', type:'string'},
		{name: 'cdfolaonini', mapping:'cdfolaonini', type:'string'},
		{name: 'cdfolaonfin', mapping:'cdfolaonfin', type:'string'},
		{name: 'cdfolciaini', mapping:'cdfolciaini', type:'string'},
		{name: 'cdfolciafin', mapping:'cdfolciafin', type:'string'}		
		]
	);
	
//****************Definición de los store de los combos*********************************************************
    
var dsProductos = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
        url: _ACTION_OBTENER_PRODUCTOS
    }),
    reader: new Ext.data.JsonReader({
    	root: 'productosComboBox',
    	totalProperty: 'totalCount',
    	id: 'codigo'
    	},[
   			{name: 'codigo',   type: 'string',  mapping:'codigo'},
   			{name: 'descripcion',   type: 'string',  mapping:'descripcion'}
		 ])
});
		
/*var dsFormaCalculoFolio = new Ext.data.Store({
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
dsFormaCalculoFolio.load();*/

var dsFormaCalculoFolio2 = new Ext.data.Store({
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
dsFormaCalculoFolio2.load();

//****************Definición de los objetos combos*************************************************************
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
    selectOnFocus:true,
    forceSelection:true,
    emptyText:'Seleccione...'
    });	
	                    
/*var comboFormaCalculoFolioAON = new Ext.form.ComboBox({
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
    forceSelection:true,
    emptyText:'Seleccione...',
    //labelSeparator:'',
    onSelect: function (record){
    			this.setValue(record.get("codigo"));
   				this.collapse();
    			if(record.get("codigo") == 1){
    				formPanel.findById('cdfolaonini').setDisabled(false);
    				formPanel.findById('cdfolaonfin').setDisabled(false);
    				formPanel.findById('formulaCalculoAONId').setDisabled(true);
    				formPanel.findById('formulaCalculoAONId').allowBlank = true;
					formPanel.findById('formulaCalculoAONId').setRawValue('');
    				}
    			if(record.get("codigo") == 2){
    				formPanel.findById('cdfolaonini').setDisabled(true);
    				formPanel.findById('cdfolaonfin').setDisabled(true);
    				formPanel.findById('formulaCalculoAONId').setDisabled(false);
    				formPanel.findById('formulaCalculoAONId').allowBlank = false;
    				formPanel.findById('cdfolaonini').setRawValue('');
    				formPanel.findById('cdfolaonfin').setRawValue('');
    				//formPanel.findById('cdfolaonini').setValue('');
    				//formPanel.findById('cdfolaonfin').setValue('');
    				}
    			}
    });*/
	                    
var comboFormaCalculoFolioAseguradora = new Ext.form.ComboBox({
    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
    id:'comboFormaCalculoFolioAseguradoraId',
    store: dsFormaCalculoFolio2,
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
    forceSelection:true,
    emptyText:'Seleccione...',
    //labelSeparator:'',
    onSelect: function (record){
    			this.setValue(record.get("codigo"));
   				this.collapse();
    			if(record.get("codigo") == 1){
    				formPanel.findById('cdfolciaini').setDisabled(false);
    				formPanel.findById('cdfolciafin').setDisabled(false);
    				formPanel.findById('formulaCalculoAseguradoraId').setDisabled(true);
    				formPanel.findById('formulaCalculoAseguradoraId').allowBlank = true;
					formPanel.findById('formulaCalculoAseguradoraId').setRawValue('');
					formPanel.findById('btnExpresion').disable();
    				}
    			if(record.get("codigo") == 2){
    				formPanel.findById('cdfolciaini').setDisabled(true);
    				formPanel.findById('cdfolciafin').setDisabled(true);
    				formPanel.findById('formulaCalculoAseguradoraId').setDisabled(false);
    				formPanel.findById('formulaCalculoAseguradoraId').allowBlank = false;
    				formPanel.findById('btnExpresion').enable();
    				formPanel.findById('cdfolciaini').setRawValue('');
    				formPanel.findById('cdfolciafin').setRawValue('');
    				//formPanel.findById('cdfolciaini').setValue('');
    				//formPanel.findById('cdfolciafin').setValue('');
					//formPanel.findById('formulaCalculoAseguradoraId').setValue('');
    				}
    			}
    });
	                    	                    
//************************Definición de los objetos NumberField************************************************	                    
		
/*var folioAON_Inicio = new Ext.form.NumberField({
	 id: 'cdfolaonini',
     fieldLabel: getLabelFromMap('cdfolaonini',helpMap,'Folio Inicio'),
     tooltip: getToolTipFromMap('cdfolaonini',helpMap,'Folio inicio AON'),
     hasHelpIcon:getHelpIconFromMap('cdfolaonini',helpMap),								 
     Ayuda: getHelpTextFromMap('cdfolaonini',helpMap),
     name:'cdfolaonini',
     width: 40
	});
	
var folioAON_Fin = new Ext.form.NumberField({
	 id: 'cdfolaonfin',
     fieldLabel: getLabelFromMap('cdfolaonfin',helpMap,'Folio Fin'),
     tooltip: getToolTipFromMap('cdfolaonfin',helpMap,'Folio fin AON'),
     hasHelpIcon:getHelpIconFromMap('cdfolaonfin',helpMap),								 
     Ayuda: getHelpTextFromMap('cdfolaonfin',helpMap),
     name:'cdfolaonfin',
     width: 40
	});
*/	
var folioAseguradora_Inicio = new Ext.form.NumberField({
	 id: 'cdfolciaini',
     fieldLabel: getLabelFromMap('cdfolciaini',helpMap,'Folio Inicio'),
     tooltip: getToolTipFromMap('cdfolciaini',helpMap,'Folio inicio Aseguradora'),
     hasHelpIcon:getHelpIconFromMap('cdfolciaini',helpMap),								 
     Ayuda: getHelpTextFromMap('cdfolciaini',helpMap),
     name:'cdfolciaini',
     width: 40
	});
	
var folioAseguradora_Fin = new Ext.form.NumberField({
	 id: 'cdfolciafin',
     fieldLabel: getLabelFromMap('cdfolciafin',helpMap,'Folio Fin'),
     tooltip: getToolTipFromMap('cdfolciafin',helpMap,'Folio fin Aseguradora'),
     hasHelpIcon:getHelpIconFromMap('cdfolciafin',helpMap),								 
     Ayuda: getHelpTextFromMap('cdfolciafin',helpMap),
     name:'cdfolciafin',
     width: 40
	});



		
//************************Definición de los objetos TextField************************************************
var desProceso = new Ext.form.TextField({
        xtype: 'textfield',
        id: 'desProcesoId',
        fieldLabel: getLabelFromMap('desProcesoId',helpMap,'Proceso'),
        tooltip: getToolTipFromMap('desProcesoId',helpMap,'Proceso'),
        hasHelpIcon:getHelpIconFromMap('desProcesoId',helpMap),								 
        Ayuda: getHelpTextFromMap('desProcesoId',helpMap),
        name: 'dsproces',
        disabled : true,
        width: 180
    });

var desFormato = new Ext.form.TextField({
        xtype: 'textfield',
        id: 'desFormatoId',
        fieldLabel: getLabelFromMap('desFormatoId',helpMap,'Formato'),
        tooltip: getToolTipFromMap('desFormatoId',helpMap,'Formato'),
        hasHelpIcon:getHelpIconFromMap('desFormatoId',helpMap),								 
        Ayuda: getHelpTextFromMap('desFormatoId',helpMap),
        name:'dsformatoorden',
        disabled : true,
        width: 180
    });

var desCliente = new Ext.form.TextField({
        xtype: 'textfield',
        id: 'desClienteId',
        fieldLabel: getLabelFromMap('desClienteId',helpMap,'Cliente'),
        tooltip: getToolTipFromMap('desClienteId',helpMap,'Cliente'),
        hasHelpIcon:getHelpIconFromMap('desClienteId',helpMap),								 
        Ayuda: getHelpTextFromMap('desClienteId',helpMap),
        name:'dselemen',
        disabled : true,
        width: 180
    });

var desAseguradora = new Ext.form.TextField({
        xtype: 'textfield',
        id: 'desAseguradoraId',
        fieldLabel: getLabelFromMap('desAseguradoraId',helpMap,'Aseguradora'),
        tooltip: getToolTipFromMap('desAseguradoraId',helpMap,'Aseguradora'),
        hasHelpIcon:getHelpIconFromMap('desAseguradoraId',helpMap),								 
        Ayuda: getHelpTextFromMap('desAseguradoraId',helpMap),
        name:'dsunieco',
        disabled : true,
        width: 180
    });

//************************Definición de los objetos TextArea************************************************
/*var formulaCalculoAON = new Ext.form.TextArea({
	 id: 'formulaCalculoAONId',
     fieldLabel: getLabelFromMap('formulaCalculoAONId',helpMap,'F&oacute;rmula de C&aacute;lculo para AON'),
     tooltip: getToolTipFromMap('formulaCalculoAONId',helpMap,'F&oacute;rmula de C&aacute;lculo para AON'),
     hasHelpIcon:getHelpIconFromMap('formulaCalculoAONId',helpMap),								 
     Ayuda: getHelpTextFromMap('formulaCalculoAONId',helpMap),
     name:'dsformaon',
     //allowBlank : false,
     width: 200
	});
	*/
/*var formulaCalculoAseguradora = new Ext.form.TextArea({
	 id: 'formulaCalculoAseguradoraId',
     fieldLabel: getLabelFromMap('formulaCalculoAseguradoraId',helpMap,'F&oacute;rmula de C&aacute;lculo para Aseguradora'),
     tooltip: getToolTipFromMap('formulaCalculoAseguradoraId',helpMap,'F&oacute;rmula de C&aacute;lculo para Aseguradora'),
     hasHelpIcon:getHelpIconFromMap('formulaCalculoAseguradoraId',helpMap),								 
     Ayuda: getHelpTextFromMap('formulaCalculoAseguradoraId',helpMap),
     name:'dsformcia',
     //allowBlank : false,
     width: 200
	});
*/
    //Panel para Formula:
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
                		fieldLabel: getLabelFromMap('formulaCalculoAseguradoraId',helpMap,'F&oacute;rmula de C&aacute;lculo para Aseguradora'),  
                		tooltip: getToolTipFromMap('formulaCalculoAseguradoraId',helpMap,'F&oacute;rmula de C&aacute;lculo para Aseguradora'),  
		        		hasHelpIcon:getHelpIconFromMap('formulaCalculoAseguradoraId',helpMap),  
			    		Ayuda: getHelpTextFromMap('formulaCalculoAseguradoraId',helpMap),  
                		name:'dsformcia',  
                		id: 'formulaCalculoAseguradoraId', 
                		width: 75,  
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
                			if(Ext.getCmp('formulaCalculoAseguradoraId').getValue() != ""){
                				ExpresionesVentana2(Ext.getCmp('formulaCalculoAseguradoraId').getValue(), Ext.getCmp('hidden-codigo-expresion-session').getValue());
                			}else{
                				var connect = new Ext.data.Connection();
								connect.request ({
									url:'atributosVariables/ObtenerCodigoExpresion.action',
									callback: function (options, success, response) {
										try{
											//alert(Ext.util.JSON.decode(response.responseText).codigoExpresion);
											Ext.getCmp('hidden-codigo-expresion-atributos-variables').setValue(Ext.util.JSON.decode(response.responseText).codigoExpresion);
											ExpresionesVentana2(Ext.getCmp('hidden-codigo-expresion-atributos-variables').getValue(),Ext.getCmp('hidden-codigo-expresion-session').getValue());
											Ext.getCmp('formulaCalculoAseguradoraId').setValue( Ext.getCmp('hidden-codigo-expresion-atributos-variables').getValue() );
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
						value:'EXPRESION_ATRIBUTOS_VARIABLES'
					},{
                		xtype:'hidden',                	
                		id:'hidden-codigo-expresion-atributos-variables',
		           		name: "codigoExpresion"
                	}
                ]
			}
		]//end column items
	});


//**********************************************************************************************************		
var formPanel = new Ext.FormPanel({
       id:'EditAsocFor',
       title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('EditAsocFor', helpMap,'Asociar Formato de Orden de Trabajo')+'</span>',        
       iconCls:'logo',
       bodyStyle:'background: white',
       buttonAlign: "center",
       labelAlign: 'right',
       frame:true,   
       url: _ACTION_OBTENER_FORMATO_X_CUENTA,
       width: 700,
       height:485,
       reader: readerFormato,
       items: [//form
              
				new Ext.form.Hidden( {
                 name : 'cdforord'
                }),
                new Ext.form.Hidden( {
                 name : 'cdproces'
                }),
                new Ext.form.Hidden( {
                 name : 'cdelemento'
                }),
                new Ext.form.Hidden( {
                 name : 'cdperson'
                }),       		    
       		    new Ext.form.Hidden( {
                 name : 'cdunieco'
                 
                }),
				new Ext.form.Hidden( {
                 name : 'codRamo'                 
                }),
       		    desProceso,
          		desFormato,
          		desCliente,
          		desAseguradora,
          		comboProductos,
         		{
         			layout: 'column',
         			items:[
         					{//1ra columna
          					columnWidth: .5,
          					layout: 'form',
          					items: [/*comboFormaCalculoFolioAON,*/comboFormaCalculoFolioAseguradora]
         				    },
         				    {//2da columna
          				   	columnWidth: .25,
          					layout: 'form',
          					items: [/*folioAON_Inicio,*/folioAseguradora_Inicio]
         				    },
         				    {//3da columna
          				   	columnWidth: .25,
          					layout: 'form',
          					items: [/*folioAON_Fin,*/folioAseguradora_Fin]
         				    }
         			]
         		   },
         		   /*formulaCalculoAON,*/
         		   //formulaCalculoAseguradora,
         		   formulaPanelEditar
         		]
      });

formPanel.load (
{
	
	params: {cdasocia: codigoAsocia},
	waitMsg : 'leyendo datos...',
	success: function() {
			/*if (formPanel.findById('comboFormaCalculoFolioId').getValue()== "1")
			{
			 formPanel.findById('cdfolaonini').setDisabled(false);
			 formPanel.findById('cdfolaonfin').setDisabled(false);
			 formPanel.findById('formulaCalculoAONId').setDisabled(true);
			};*/
			if (formPanel.findById('comboFormaCalculoFolioAseguradoraId').getValue()== ""){
				formPanel.findById('btnExpresion').disable();
			};
			if (formPanel.findById('comboFormaCalculoFolioAseguradoraId').getValue()== "1")
			{
			 formPanel.findById('cdfolciaini').setDisabled(false);
			 formPanel.findById('cdfolciafin').setDisabled(false);
			 formPanel.findById('formulaCalculoAseguradoraId').setDisabled(true);
			 formPanel.findById('btnExpresion').disable();
			};
			/*if (formPanel.findById('comboFormaCalculoFolioId').getValue()== "2")
			{
			 formPanel.findById('cdfolaonini').setDisabled(true);
			 formPanel.findById('cdfolaonfin').setDisabled(true);
			 formPanel.findById('formulaCalculoAONId').setDisabled(false);
			};*/
			if (formPanel.findById('comboFormaCalculoFolioAseguradoraId').getValue()== "2")
			{
			 formPanel.findById('cdfolciaini').setDisabled(true);
			 formPanel.findById('cdfolciafin').setDisabled(true);
			 formPanel.findById('formulaCalculoAseguradoraId').setDisabled(false);
			 formPanel.findById('btnExpresion').enable();
			}
			dsProductos.reload({
				params: {
				cdunieco: formPanel.form.findField('cdunieco').getValue(),
				cdelemento: formPanel.form.findField('cdelemento').getValue()
				},callback:function(r,o,s){ 
								comboProductos.setValue(formPanel.form.findField('codRamo').getValue());
				}
			});   
			 //dsProductos.removeAll();
			
	}
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
        buttons : [ {
                      text:getLabelFromMap('EditAsocForBtnGuar',helpMap,'Guardar'),
                      tooltip: getToolTipFromMap('EditAsocForBtnGuar',helpMap,'Guardar'),
                      disabled : false,
                      handler : function() {
                      if (formPanel.form.isValid()) {
                           if(/*(validarFormulariosAON()==true)&& */(validarFormulariosCIA()==true)){
                                formPanel.form.submit( {
                               //action a invocar cuando el formulario haga submit
                                url : _ACTION_GUARDAR_ASOCIAR_ORDEN_TRABAJO,
					            params:{
					                     cdasocia: codigoAsocia
					                    },
                                success : function(from, action) {
                                    Ext.MessageBox.alert('Aviso',action.result.actionMessages[0],function(){reloadGrid(Ext.getCmp('grillita'))});
                                    window.close();
        
                                },
                                 //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                                   failure : function(form, action) {
                                   Ext.MessageBox.alert('Error',action.result.errorMessages[0]);
                                   return false;
                                 },
                                //mensaje a mostrar mientras se guardan los datos
                                  waitMsg : 'guardando datos ...'
                               });
                           }
                       } else {
                                Ext.Msg.alert('Informacion', 'Por favor complete la informaci&oacute;n requerida');
                               }
             }
             },
             {
               text:getLabelFromMap('EditAsocForBtnCan',helpMap,'Cancelar'),
               tooltip: getToolTipFromMap('EditAsocForBtnCan',helpMap,'Cancelar'),
               handler : function() {
                 window.close();
             }
         }]
 	});

window.show();

/*function validarFormulariosAON(){
	if (formPanel.findById('comboFormaCalculoFolioId').getValue()=="1") {
		if((formPanel.findById('cdfolaonini').getValue()!="")&& (formPanel.findById('cdfolaonfin').getValue()!="")){
   			if (eval(formPanel.findById('cdfolaonini').getValue()) > eval(formPanel.findById('cdfolaonfin').getValue())){
   			       Ext.Msg.alert('Informacion', 'El folio de Inicio en AON debe ser menor que el folio de Fin.');
  			       return false;
   				} else {
   					return true;
   					}
   		    }else{
   			       Ext.Msg.alert('Informacion', 'El folio de Inicio o Fin de AON no pueden ser Vacios.');   
   			       return false;		    
   		    }
   			
   		}
   		return true;
   };*/

function validarFormulariosCIA(){
  	if (formPanel.findById('comboFormaCalculoFolioAseguradoraId').getValue()=="1") {
  		if((formPanel.findById('cdfolciaini').getValue()!="")&& (formPanel.findById('cdfolciafin').getValue()!="")){		    
   			if (eval(formPanel.findById('cdfolciaini').getValue()) > eval(formPanel.findById('cdfolciafin').getValue())){
   			       Ext.Msg.alert('Informacion', 'El folio de Inicio en CIA debe ser menor que el folio de Fin.');
   			       return false;
				} else {
   					return true;
   					}	
   		    }else{
   			       Ext.Msg.alert('Informacion', 'Los folio de Inicio y Fin de CIA no pueden ser Vacios.'); 
   			       return false;  		    
   		    }
   			
   		}
   		return true
   };
}

//######################### FIN ASOCIAR FORMATOS ORDEN TRABAJO ################################################ 
