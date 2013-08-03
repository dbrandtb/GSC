// Funcion de editar requisito de rehabilitacion
function editar(record) {

    var dsTipoDocumento = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_TIPOS_DOCUMENTO
            }),
            reader: new Ext.data.JsonReader({
            root: 'comboTiposDocumento'
            },[
           {name: 'cdDocXcta', type: 'string',mapping:'cdDocXcta'},
           {name: 'dsFormato', type: 'string',mapping:'dsFormato'}
        ])
    });
    
   
    //se define la forma en que se van a leer los datos que envie la action
        var _jsonFormReader = new Ext.data.JsonReader( {

            //indica el arreglo a leer
            root : 'MEstructuraList',

            //indica la cantidad de registro que debe leerse
            totalProperty: 'total',

            //indica el resultado de la respuesta de la action
            successProperty : 'success'

        }, [ {
               name : 'cdRequisito',mapping : 'cdRequisito',type : 'string'
        }, {
               name : 'cdPerson',type : 'string',mapping : 'cdPerson'
        },{
               name : 'cdUnieco',type : 'string',mapping : 'cdUnieco'
        },{
               name : 'dsUnieco',type : 'string',mapping : 'dsUnieco'
        },{
               name : 'cdRamo',type : 'string',mapping : 'cdRamo'
         },{
               name : 'dsRamo',type : 'string',mapping : 'dsRamo'
        },{
               name : 'dsRequisito', mapping : 'dsRequisito', type : 'string'
        },{
               name :'cdElemento', mapping : 'cdElemento', type : 'string'
        },{
               name :'dsElemen', mapping : 'dsElemen', type : 'string'
        }
        /*,{
               name : 'cdDocXcta',type : 'string',mapping : 'cdDocXcta'
        }*/
       ]);
    
    
   
//se define el formulario
var formPanel = new Ext.FormPanel ({

           labelWidth : 100,
           heigth: 200,

			//action a invocar al hacer al cargar(load) del formulario
            url : _ACTION_GET_REQUISITOS_REHABILITACION,

            baseParams : {cdRequisito: record.get("cdRequisito")},

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
                    name : 'cdElemento',
                    id: 'cdElemento'
                },{
                	xtype: 'hidden',
                    name : 'cdPerson',
                    id: 'cdPerson'
                },{
                    xtype: 'hidden',
                    name : 'cdUnieco',
                    id: 'cdUnieco'
                },{
                    xtype: 'hidden',
                    name : 'cdRamo',
                    id: 'cdRamo'
                },{
                    xtype: 'textfield',
	               	fieldLabel: getLabelFromMap('editReqRehTxtNom', helpMap,'Cliente'), 
					tooltip: getToolTipFromMap('editReqRehTxtNom', helpMap, 'Texto Cliente'), 			                    	
                    name : 'dsElemen',
                    disabled:true,
                    id: 'dsElemen'
                },{
                    xtype: 'textfield',
	               	fieldLabel: getLabelFromMap('editReqRehTxtAseg', helpMap,'Aseguradora'), 
					tooltip: getToolTipFromMap('editReqRehTxtAseg', helpMap, 'Texto Aseguradora'), 			                    	
                    name : 'dsUnieco',
                    disabled:true,
                    id: 'dsUnieco'
                },{
                    xtype: 'textfield',
	               	fieldLabel: getLabelFromMap('editReqRehTxtProd', helpMap,'Producto'), 
					tooltip: getToolTipFromMap('editReqRehTxtProd', helpMap, 'Texto Producto'), 			                    	
                    name : 'dsRamo',
                    disabled:true,
                    id: 'dsRamoId'
                },
                
                
                
                {xtype: 'textfield',
                 fieldLabel: getLabelFromMap('editReqRehTxtNom', helpMap,'Nombre'), 
				 tooltip: getToolTipFromMap('editReqRehTxtNom', helpMap, 'Texto Nombre'), 			                    	
                 name: 'dsRequisito', 
                 id: 'dsRequisito'},
                
                {
                    xtype: 'combo',
                     tpl: '<tpl for="."><div ext:qtip="{cdDocXcta}. {dsFormato}" class="x-combo-list-item">{dsFormato}</div></tpl>',
                    store: dsTipoDocumento,
                    displayField:'dsFormato',
                    valueField:'cdDocXcta',
                    hiddenName: 'cdDocXcta',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
	                fieldLabel: getLabelFromMap('editReqRehCboTipDoc', helpMap,'Tipo de Documento'), 
					tooltip: getToolTipFromMap('editReqRehCboTipDoc', helpMap, 'Elija Tipo de Documento'), 			                    	
                    forceSelection: true,
                    width: 200,
                    emptyText:'Seleccione Tipo de Documento...',
                    selectOnFocus:true,
                    forceSelection:true,
                    labelSeparator:'',
                    allowBlank : false,
                    id:'cdDocXctaId'
                } 
             
                ]
        });


//Windows donde se van a visualizar la pantalla
var window = new Ext.Window({
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('111', helpMap,'Editar Requisito de Rehabilitaci&oacute;n')+'</span>',
        width: 500,
        height:200,
        layout: 'fit',
        plain:true,
        modal: true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: formPanel,
        //se definen los botones del formulario
            buttons : [ {
				text:getLabelFromMap('editReqRehBtnSave', helpMap,'Guardar'),
				tooltip:getToolTipFromMap('editReqRehBtnSave', helpMap, 'Guarda requisito de rehabilitaci&oacute;n'),
                disabled : false,
                handler : function() {
                    if (formPanel.form.isValid()) {
                        formPanel.form.submit( {
                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_INSERTAR_GUARDAR_REQUISITOS_REHABILITACION,
                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),'Guardado satisfactoriamente');
                                window.close();
                                },
                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'),'Problemas al Guardar: ' + action.result.errorMessages[0]);
                                },
                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : getLabelFromMap('400022', helpMap,'Guardando Actualizaci&oacute;n de datos...')
                        });
                     } else {
                     		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                     }
                }
            },
             {
				text:getLabelFromMap('editReqRehBtnBack', helpMap,'Regresar'),
				tooltip:getToolTipFromMap('editReqRehBtnBack', helpMap, 'Cancela Guardar Requisito de rehabilitaci&oacute;n'),
                text : '',
                handler : function() {
                window.close();
                    }
            }]
    	});
   
     dsTipoDocumento.load({
			                  params: {cdElemento: record.get("cdElemento") ,cdUniEco: record.get('cdUnieco'),cdRamo: record.get('cdRamo')},
			                  });    
    window.show();
     //se carga el formulario con los datos de la estructura a editar
        formPanel.form.load({
    
                    waitMsg : getLabelFromMap('400028',helpMap,'Leyendo datos ...'),
                    success:function(){
                                
	                          formPanel.findById("cdDocXctaId").setValue(record.get('cdDocXcta'));  	
                        }

                });


};
