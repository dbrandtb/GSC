// Funcion de Editar Eemento Estructura

function editar(record) {

    
     var dsClientesCorp = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_CLIENTES_CORP
            }),
            reader: new Ext.data.JsonReader({
            root: 'clientesCorp',
            id: 'cdElemento'
            },[
            {name: 'cdElemento', type: 'string',mapping:'cdElemento'},
            {name: 'cdPerson', type: 'string',mapping:'cdPerson'},
            {name: 'dsElemen', type: 'string',mapping:'dsElemen'}
        ])
        });
        
       var dsSiNo = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_SINO
            }),
            reader: new Ext.data.JsonReader({
            root: 'siNo',
            id: 'codigo'
            },[
            {name: 'codigo', type: 'string',mapping:'codigo'},
            {name: 'descripcion', type: 'string',mapping:'descripcion'}
        ])
        });
 
 		var fgSiNoCbo = new Ext.form.ComboBox ({
                    xtype: 'combo',
                    labelWidth: 50,
                    tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                    id:'EdifgSiNoCboId',
                    fieldLabel: getLabelFromMap('EdifgSiNoCboId',helpMap,'¿Usa carrito de compra?'),
                    tooltip: getToolTipFromMap('EdifgSiNoCboId',helpMap,'¿Usa el carrito de compras?'),  
                    hasHelpIcon:getHelpIconFromMap('EdifgSiNoCboId',helpMap),								 
                    Ayuda: getHelpTextFromMap('EdifgSiNoCboId',helpMap),
                    store: dsSiNo,
                    displayField:'descripcion',
                    valueField:'codigo',
                    hiddenName: 'fgSiNo',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    //fieldLabel: "¿Usa carrito de compra?",
                    forceSelection: true,
                    width: 60,
                    emptyText:'Seleccione ...',
                    selectOnFocus:true
                    //labelSeparator:''
                });
       

		//se define el formulario
        var formPanel = new Ext.FormPanel ( {

            labelWidth : 150,

			//action a invocar al hacer al cargar(load) del formulario
           url : _ACTION_GUARDAR_CARRITO_COMPRAS,


            bodyStyle : 'padding:5px 5px 0',
            
            bodyStyle:'background: white',
            
            frame:true,
            height:250,

            width : 350,


            defaults : {width : 200 },
            
            labelAlign: 'right',

			//se definen los campos del formulario
            items : [
               
                new Ext.form.Hidden( {
                name : 'cdCliente',
                value: record.get('cdCliente')
                }),
                
                 new Ext.form.TextField( {
                 id:'editarCdClienteId',   
        		 fieldLabel: getLabelFromMap('editarCdClienteId', helpMap,'Cliente'), 
           		 tooltip: getToolTipFromMap('editarCdClienteId', helpMap,'Cliente'),
                 hasHelpIcon:getHelpIconFromMap('editarCdClienteId',helpMap),								 
                 Ayuda: getHelpTextFromMap('editarCdClienteId',helpMap),
           		 
                 name : 'Cliente',
                 //fieldLabel: "Cliente",
                 readOnly:true,
                 value: record.get('dsNombre')
                }),

                fgSiNoCbo,

                new Ext.form.Hidden({
                name : 'cdConfiguracion',
                value: record.get('cdConfiguracion')
                })
                
                    
            ]});

//Windows donde se van a visualizar la pantalla
    
        var window = new Ext.Window({
        	id:'windowEditarId',
        	//title: '<span style="color:black;font-size:14px;">'+getLabelFromMap('windowEditarId', helpMap,'Editar uso de carrito de compras')+'</span>',
            title: getLabelFromMap('windowEditarId', helpMap,'Editar uso Carrito de Compras'),        	
        	
        	//title: 'Editar uso de carrito de compras',
        	width: 500,
        	height:160,
        	modal:true,
        	layout: 'fit',
        	plain:true,
        	bodyStyle:'padding:5px;',
        	bodyStyle:'background: white',
        	buttonAlign:'center',
        	items: formPanel,
            //se definen los botones del formulario
            buttons : [ {
				id: 'windowEditarIdButtonsGuardarId', 
				text: getLabelFromMap('windowEditarIdButtonsGuardarId', helpMap,'Guardar'),                
				tooltip: getToolTipFromMap('windowEditarIdButtonsGuardarId', helpMap,'Guarda Carrito de Compras'), 
                //text : 'Guardar',

                disabled : false,

                handler : function() {

                    if (formPanel.form.isValid()) {

                        formPanel.form.submit( {

                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_GUARDAR_CARRITO_COMPRAS,

                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                               Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),action.result.actionMessages[0], function() {
                                		reloadGrid ();
                                });
                                window.close();                                     
                            },

                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0]);  
                           },
                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : getLabelFromMap('400022', helpMap,'Guardando Actualizacion de datos...')  
                            //waitMsg : 'guardando datos ...'
                        });

                    } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                        //Ext.Msg.alert('Informacion', 'Por favor complete la informacion requerida!');
                    }

                }

            }, {
                id: 'windowEditarIdButtonsCanceloarId', 
                text: getLabelFromMap('windowEditarIdButtonsCanceloarId', helpMap,'Cancelar'),                
				tooltip: getToolTipFromMap('windowEditarIdButtonsCanceloarId', helpMap,'Cancela la edici&oacute;n de datos'), 
				//text : 'Cancelar',
				handler : function() {
                  window.close();
                }
            }]
    	});
    	
    fgSiNoCbo.setValue(record.get('fgSiNo'));
   
    dsSiNo.load();
	window.show();

    };

