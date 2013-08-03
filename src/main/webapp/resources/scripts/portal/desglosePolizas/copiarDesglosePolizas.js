// Funcion de Copiar Desglose de Polizas
function copiar(record) {

      var dsClientesCorp = new Ext.data.Store({
           proxy: new Ext.data.HttpProxy({
               url: _ACTION_OBTENER_CLIENTE_CORPO
           }),
           reader: new Ext.data.JsonReader({
           root: 'clientesCorp',
           id: 'clientesCorps'
           },[
           {name: 'cdElemento', type: 'string',mapping:'cdElemento'},
           {name: 'cdPerson2', type: 'string',mapping:'cdPerson'},
           {name: 'dsElemen', type: 'string',mapping:'dsElemen'}
       ])
       });

       var desProducto = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_PRODUCTOS_AYUDA
            }),
            reader: new Ext.data.JsonReader({
            root: 'productosComboBox',
            id: 'productos'
            },[
           {name: 'codigo', type: 'string',mapping:'codigo'},
           {name: 'descripcion', type: 'string',mapping:'descripcion'}
        ])
    });



    
		//se define el formulario
var formPanel = new Ext.FormPanel ({
		    frame : true,
            width : 800,
            autoHeight: true,
            waitMsgTarget : true,
            defaults: {labelWidth: 70},
            bodyStyle : 'background: white',            
            
            item: [
            	   {
            		  html: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
            		  layout: 'column',
            		  layoutConfig: {columns: 2},
            		  bodyStyle:'background: white',

            //se definen los campos del formulario
            		  items : [
                 				{
				                   layout:'form',
				                   columnWidth: .45,
				                   items:[
						                   {
						                     xtype: 'textfield', 
						                     fieldLabel: 'Cliente', 
						                     name: 'dsCliente', 
						                     id: 'dsCliente',
						                     allowBlank: false,
						                     readOnly:true
					                   		},
					                   		{
						                     xtype: 'hidden', 
						                     fieldLabel: 'Cliente id', 
						                     name: 'cdPerson', 
						                     id: 'cdPerson',
						                     allowBlank: false,
						                     readOnly:true
					                  	 	},
					                  	 	{
						                     xtype: 'hidden', 
						                     fieldLabel: 'producto id', 
						                     name: 'cdRamo', 
						                     id: 'cdRamo',
						                     allowBlank: false,
						                     readOnly:true
					                   		},
					                   		{
						                     xtype: 'hidden', 
						                     fieldLabel: 'dsPerson', 
						                     name: 'dsPerson', 
						                     id: 'dsPerson',
						                     allowBlank: false,
						                     readOnly:true
					                   		}
			                   			  ]
                   					},
					                   {
					                   layout: 'form',
					                   columnWidth: .5, 
					                   items: [
					                   			{
							                       xtype: 'combo',
							                       tpl: '<tpl for="."><div ext:qtip="{cdElemento}.{cdPerson2}.{dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
							                       store: dsClientesCorp,
							                       displayField:'dsElemen',
							                       valueField: 'cdElemento',
							                       hiddenName: 'dsPerson1',
							                       typeAhead: true,
							                       mode: 'local',
							                       triggerAction: 'all',
							                       fieldLabel: "Cliente",
							                       forceSelection: true,
							                       width: 180,
							                       emptyText:'Seleccione Cliente ...',
							                       selectOnFocus:true,
							                       labelSeparator:'',
							                       onSelect: function (record) {
							                                   this.setValue(record.get("cdElemento"));
							                                   formPanel.findById("dsPerson").setValue(record.get("cdPerson2"));
							                                   this.collapse();
							                                 }
					            				}
					            			  ]
					   			      },
					   			      {
					                   layout:'form',
					                   columnWidth: .5,
					                   items:[
					                          {
							                     xtype: 'textfield', 
							                     fieldLabel: 'Producto', 
							                     name: 'dsProducto', 
							                     id: 'dsProductoId',
							                     allowBlank: false,       		
							                     readOnly:true                     
					                          }
					                         ]
					                   },
					                   {
					                   layout: 'form',
					                   columnWidth: .45, 
					                   items: [
					                   			{
								                    xtype: 'combo',
								                    tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
								                    store: desProducto,
								                    displayField:'descripcion',
								                    valueField:'codigo',
								                    hiddenName: 'dsRamo',
								                    typeAhead: true,
								                    mode: 'local',
								                    triggerAction: 'all',
								                    fieldLabel: "Producto",
								                    forceSelection: true,
								                    width: 180,
								                    emptyText:'Seleccione Producto...',
								                    selectOnFocus:true,
								                    labelSeparator:''
					                   			}
					            			]
					            		}
            		                                                    
            				]
            		}
      		]
});


//Windows donde se van a visualizar la pantalla
var window = new Ext.Window({
        	title: 'Copiar Desglose de Polizas',
            width: 800,
            autoHeight: true,
            plain:true,
			bodyStyle:'background: white',
            //bodyStyle:'padding:5px;',
            buttonAlign:'center',

			modal: true,
        	items: [formPanel],
            //se definen los botones del formulario
            buttons : [ {

                text : 'Copiar',

                disabled : false,

                handler : function() {

                    if (formPanel.form.isValid()) {

                        formPanel.form.submit( {

                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_COPIAR_DESGLOSE_POLIZAS,

                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert('Aviso','Copiado satisfactoriamente');
                                window.close();                                     
                            },

                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.MessageBox.alert('Error','Problemas al Copiar: ' + action.result.errorMessages[0]);
                           },

                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : 'copiando datos ...'

                        });

                    } else {

                        Ext.Msg.alert('Informacion', 'Por favor complete la informaci&oacute;n requerida!');

                    }

                }

            }, {

                text : 'Cancelar',
                handler : function() {
                window.close();
                }

            }]

});



window.show();
  
//recuperar los campos del registro selecionado para copiar

    if (record != null && record.get('dsCliente') != "") {
		formPanel.findById('dsCliente').setValue(record.get('dsCliente'));
	}
	if (record != null && record.get('dsProducto') != "") {
		formPanel.findById('dsProducto').setValue(record.get('dsRamo'));
	}
	if (record != null && record.get('cdPerson') != "") {
		formPanel.findById('cdPerson').setValue(record.get('cdPerson'));
	}
	if (record != null && record.get('cdRamo') != "") {
		formPanel.findById('cdRamo').setValue(record.get('cdRamo'));
	}
      
      
   dsClientesCorp.load();
   desProducto.load();
       

};

