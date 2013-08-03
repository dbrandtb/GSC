function agregar() {

   var dsEncabezadoRangoRenovacion = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_GET_ENCABEZADO_RANGO_RENOVACION
            }),
            reader: new Ext.data.JsonReader({
            root: 'MRangoRenovacionReporteManagerList',
            id: 'dsElemen'
            },[
           {name: 'dsElemen', type: 'string',mapping:'dsElemen'},
           {name: 'dsUniEco', type: 'string',mapping:'dsUniEco'},
           {name: 'dsRamo', type: 'string',mapping:'dsRamo'},
           {name: 'tipo', type: 'string',mapping:'tipo'}
        ]),
        remoteSort: true
    });
    
    
    
     //se define el formulario
            var formPanel = new Ext.FormPanel ({

            labelWidth : 10,
		    frame : true,
            bodyStyle : 'padding:5px 5px 0',
            width : 500,
            heigth:250,
            //autoHeight: true,
            waitMsgTarget : true,
            bodyStyle:'background: white',
            labelAlign:'right',
            defaults: {labelWidth: 70},
            layout: 'column',
            layoutConfig: {columns: 2, align: 'left'},


            //se definen los campos del formulario
            items : [
                    {
                     layout:'form',
                     columnWidth: .50,
                     items:[{
                     xtype: 'textfield', 
                     fieldLabel: 'Cliente', 
                     name: 'dsElemen', 
                     id: 'dsElemen',
                     allowBlank: false,
                     readOnly:true,
                     disabled:true
                   },/*
                   {
                     xtype: 'hidden', 
                     name: 'tipo', 
                     id: 'tipo'
                   },*/
                   	{
	                    
                     xtype: 'textfield', 
                     fieldLabel: 'Tipo', 
                     name: 'tipo', 
                     id: 'tipo',
                     allowBlank: false,
                     //disabled: true,
                     readOnly:true,
                     disabled:true
	                   
                   },{
                     xtype: 'hidden', 
                     fieldLabel: 'cdRenova', 
                     name: 'cdRenova', 
                     id: 'cdRenova',
                     allowBlank: false,
                     readOnly:true,
                     value:	CDRENOVA
                   },{
                     xtype: 'hidden', 
                     fieldLabel: 'cdRango', 
                     name: 'cdRango', 
                     id: 'cdRango',
                     allowBlank: false,
                     readOnly:true/*,
                     value:CDRANGO*/
                   },{
                     xtype: 'hidden', 
                     fieldLabel: 'dsPerson', 
                     name: 'dsPerson', 
                     id: 'dsPerson',
                     allowBlank: false,
                     readOnly:true
                   }
                   ]},
                   {
                     layout:'form',
                     columnWidth: .50,
                     items:[{
			                     xtype: 'textfield', 
			                     fieldLabel: 'Aseguradora', 
			                     name: 'dsUniEco', 
			                     id: 'dsUniEco',
			                     allowBlank: false,       		
			                     readOnly:true,
                     			 disabled:true                   
		                     },{
		                         xtype: 'textfield', 
			                     fieldLabel: 'Producto', 
			                     name: 'dsRamo', 
			                     id: 'dsRamo',
			                     allowBlank: false,       		
			                     readOnly:true,
                     			 disabled:true   
			               }]
                   },
                 
                   {
                   layout: 'column',
                   columnWidth: .5,
                   items:[{
                     layout:'form',
                     columnWidth: 1,
                     items:[{
			                     xtype: 'textfield', 
			                     fieldLabel: 'Rango', 
			                     name: 'dsRango', 
			                     id: 'dsRango',
			                     allowBlank: false,
			                     maxLength:30                    
		                     }]
                   },{
                     layout:'form',
                     columnWidth: 1,
                     items:[{
			                     xtype: 'numberfield', 
			                     fieldLabel: 'Inicio', 
			                     name: 'cdInicioRango', 
			                     id: 'cdInicioRangoID',
			                     allowBlank: false,
			                     maxLength:3,
			                     listeners: {
						    	 'change': function(){
						         		validarRangos();
						    		}
						  		 }                 
		                     }]
                   },{
                     layout:'form',
                     columnWidth: 1,
                     items:[{
			                     xtype: 'numberfield', 
			                     labelAlign:'top',
			                     fieldLabel: 'Fin', 
			                     name: 'cdFinRango', 
			                     id: 'cdFinRangoID',
			                     allowBlank: false,
			                     maxLength:3,
			                     listeners: {
						    	 'change': function(){
						         		validarRangos();
						    		}
						  		 }                     
		                     }]
                   }]
			               
                   }
            		                                                    
            ]});



//Windows donde se van a visualizar la pantalla
var window = new Ext.Window({
        	title: 'Agregar Configura Rango de Renovaci&oacute;n',
            width: 500,
            heigth:250,
            //autoHeight: true,
            bodyStyle:'padding:5px;',
            buttonAlign:'center',
			modal: true,
        	items: [formPanel],
            //se definen los botones del formulario
            buttons : [ {

                text : 'Guardar',

                disabled : false,

                handler : function() {

                    if (formPanel.form.isValid()) {

                        formPanel.form.submit( {

                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_AGREGAR_GUARDAR_RANGOS_RENOVACION,

                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert('Aviso', action.result.actionMessages[0], function() {reloadGrid ();});
                                window.close();                                     
                            },

                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.MessageBox.alert('Error',action.result.errorMessages[0]);
                           },

                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : 'guardando datos ...'

                        });

                    } else {

                        Ext.Msg.alert('Aviso', 'Por favor complete la informaci&oacute;n requerida');

                    }

                }

            }, {

                text : 'Regresar',
                handler : function() {
                window.close();
                }

            }]

});



window.show();
 
 function validarRangos(){
   		
   		if (formPanel.findById("cdInicioRangoID").getValue()!="" && formPanel.findById("cdFinRangoID").getValue()!="") {
   			if (eval(formPanel.findById("cdInicioRangoID").getValue()) > eval(formPanel.findById("cdFinRangoID").getValue())){
   			       Ext.Msg.alert('Informacion', 'El rango de Inicio debe ser menor que el rango de Fin.');
   			       var rangoFin = eval(formPanel.findById("cdInicioRangoID").getValue()) + 1;
   			       formPanel.findById("cdFinRangoID").setValue(rangoFin);	
   			}
   		}
   }; 
    
dsEncabezadoRangoRenovacion.load({
				                      params:{cdRenova: CDRENOVA},
				                      
				                      callback: function(r,options,success){
				                          if(success)
				                          {
				                          	formPanel.findById("dsElemen").setValue(dsEncabezadoRangoRenovacion.reader.jsonData.MRangoRenovacionReporteManagerList[0].dsElemen);
				                          	formPanel.findById("dsUniEco").setValue(dsEncabezadoRangoRenovacion.reader.jsonData.MRangoRenovacionReporteManagerList[0].dsUniEco);
				                          	formPanel.findById("dsRamo").setValue(dsEncabezadoRangoRenovacion.reader.jsonData.MRangoRenovacionReporteManagerList[0].dsRamo);
				                          	formPanel.findById("tipo").setValue(dsEncabezadoRangoRenovacion.reader.jsonData.MRangoRenovacionReporteManagerList[0].tipo);
				                          	//formPanel.findById("tiposId").setValue(dsEncabezadoRangoRenovacion.reader.jsonData.MRangoRenovacionReporteManagerList[0].tipo);
				                          }
				                      }
					});
};

