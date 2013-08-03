function agregar() {

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
        
     
   			
        //se define el formulario
        var formPanel = new Ext.FormPanel( {

            labelWidth : 150,

            url : _ACTION_GUARDAR_CARRITO_COMPRAS,

            frame : true,

            renderTo: Ext.get('formulario'),


            bodyStyle : 'padding:5px 5px 0',
            
            bodyStyle:'background: white',

            width : 350,

            waitMsgTarget : true,

            defaultType : 'textfield',
            
            labelAlign: 'right',

            //se definen los campos del formulario

            items : [
			/*
                    new Ext.form.Hidden({
                    name : 'cdElemento',
                    id : 'cdElementoId'
                    }),

            {
                xtype: 'combo',
                labelWidth: 50,
                tpl: '<tpl for="."><div ext:qtip="{cdElemento}.{cdPerson}. {dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
                id:'agregarCdClienteId',
                fieldLabel: getLabelFromMap('agregarCdClienteId',helpMap,'Cliente'),
                tooltip: getToolTipFromMap('agregarCdClienteId',helpMap,'Cliente'),
                store: dsClientesCorp,
                displayField:'dsElemen',
                valueField:'cdPerson',
                hiddenName: 'cdCliente',
                typeAhead: true,
                mode: 'local',
                triggerAction: 'all',
                //fieldLabel: "Cliente",
                forceSelection: true,
                allowBlank : false,
                width: 200,
                emptyText:'Seleccione Cliente ...',
                selectOnFocus:true,
                //id:'cdClienteId',
                onSelect: function (record) {
                    this.setValue(record.get("cdPerson"));
                    if (record.get("cdPerson") == "") this.setRawValue(record.get("dsElemen"));
                    formPanel.findById(('cdElementoId')).setValue(record.get("cdElemento"));
                    this.collapse();
                }
             }, */

                    new Ext.form.Hidden({
                    name : 'cdCliente',
                    id : 'cdClienteId'
                    }),

            {
                xtype: 'combo',
                labelWidth: 50,
                tpl: '<tpl for="."><div ext:qtip="{cdElemento}.{cdPerson}.{dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
                id:'agregarCdClienteId',
                fieldLabel: getLabelFromMap('agregarCdClienteId',helpMap,'Cliente'),
                tooltip: getToolTipFromMap('agregarCdClienteId',helpMap,'Cliente'),
                hasHelpIcon:getHelpIconFromMap('agregarCdClienteId',helpMap),								 
                Ayuda: getHelpTextFromMap('agregarCdClienteId',helpMap),
                store: dsClientesCorp,
                displayField:'dsElemen',
                valueField:'cdElemento',
                hiddenName: 'cdElemento',
                typeAhead: true,
                mode: 'local',
                triggerAction: 'all',
                //fieldLabel: "Cliente",
                forceSelection: true,
                allowBlank : false,
                width: 200,
                emptyText:'Seleccione Cliente ...',
                selectOnFocus:true,
                //id:'cdClienteId',
                onSelect: function (record) {
                    this.setValue(record.get("cdElemento"));
                    //if (record.get("cdPerson") == "") this.setRawValue(record.get("dsElemen"));
                    formPanel.findById(('cdClienteId')).setValue(record.get("cdPerson"));
                    this.collapse();
                }
             },


            {
                xtype: 'combo',
                labelWidth: 50,
                tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                id:'agregarFgSiNoF',
                fieldLabel: getLabelFromMap('agregarFgSiNoF',helpMap,'¿Usa carrito de compra?'),
                tooltip: getToolTipFromMap('agregarFgSiNoF',helpMap,'¿Usa el Carrito de Compras?'),
                hasHelpIcon:getHelpIconFromMap('agregarFgSiNoF',helpMap),								 
                Ayuda: getHelpTextFromMap('agregarFgSiNoF',helpMap),
                store: dsSiNo,
                displayField:'descripcion',
                valueField:'codigo',
                hiddenName: 'fgSiNo',
                typeAhead: true,
                mode: 'local',
                triggerAction: 'all',
                //fieldLabel: "¿Usa carrito de compra?",
                forceSelection: true,
                width: 200,
                emptyText:'Seleccione ...',
                selectOnFocus:true
            }


           
            ]});

 			 
//Windows donde se van a visualizar la pantalla
        var window = new Ext.Window({
        	id:'windowAgregarId',
        //	title: '<span style="color:black;font-size:14px;">'+getLabelFromMap('windowAgregarId', helpMap,'Agregar Carrito de Compras')+'</span>',
            title: getLabelFromMap('windowAgregarId', helpMap,'Agregar Carrito de Compras'),        	
        	//title: 'Uso carrito de compras',
        	modal:true,
        	width: 500,
        	height:160,
        	layout: 'fit',
        	//modal:true,
        	plain:true,
        	bodyStyle:'padding:5px;',
        	bodyStyle:'background: white',
        	buttonAlign:'center',
        	items: formPanel,
            //se definen los botones del formulario
            buttons : [ {
				id: 'windowAgregarIdButtonsGuardarId', 
				text: getLabelFromMap('windowAgregarIdButtonsGuardarId', helpMap,'Guardar'),                
				tooltip: getToolTipFromMap('windowAgregarIdButtonsGuardarId', helpMap,'Guarda Carrito de Compras'), 

                //text : 'Guardar',

                //disabled : false,

                handler : function() {

                    if (formPanel.form.isValid()) {

                        formPanel.form.submit( {

                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_GUARDAR_NUEVO_CARRITO_COMPRAS,

                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0], function() {reloadGrid ();});
                                window.close();
                            },

                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0]);
                            },

                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')
                            //waitMsg : 'guardando datos ...'

                        });

                    } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacuten requerida'));
                    }

                }

            }, {
                text: getLabelFromMap('windowAgregarButtonCancelarId', helpMap,'Cancelar'),       
				tooltip: getToolTipFromMap('windowAgregarButtonCancelarId', helpMap,'Cancela el ingreso de datos'), 
                //text : 'Cancelar',

                handler : function() {
                    window.close();
                }

            }]

    	});
 
        dsClientesCorp.load();
        dsSiNo.load({
   			callback: function (r, o, success) {
        			if (success) {
						formPanel.findById("agregarFgSiNoF").setValue('S');
						//comboMetodoEnvio.setValue(cdMet_Env);
        			}
        	}
    });
        window.show();
};