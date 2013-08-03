// Funcion de Copiar Ayuda Cobertura

function copiar(key) {

var dsClientesCorp = new Ext.data.Store({
     proxy: new Ext.data.HttpProxy(
     {
         url: _ACTION_OBTENER_CLIENTE_CORPO
     }
     ),
     reader: new Ext.data.JsonReader(
     {
	     root: 'clientesCorp',
	     id: 'clientesCorps'
     },
     [
	    {name: 'cdElemento', type: 'string',mapping:'cdElemento'},
	    {name: 'cdPerson', type: 'string',mapping:'cdPerson'},
	    {name: 'dsElemen', type: 'string',mapping:'dsElemen'}
	 ]
	 )
});

var dsAseguradora = new Ext.data.Store ({
	proxy: new Ext.data.HttpProxy(
	{
		url: _ACTION_OBTENER_ASEGURADORAS_CLIENTE
	}
	),
	reader: new Ext.data.JsonReader(
	{
		root: 'aseguradoraComboBox',
		id: 'cdUniEco',
		successProperty: '@success'
	}, 
	[
		{name: 'cdUniEco', type: 'string', mapping: 'cdUniEco'},
		{name: 'dsUniEco', type: 'string', mapping: 'dsUniEco'} 
	]
	)
});

var dsProductos = new Ext.data.Store(
{
      proxy: new Ext.data.HttpProxy(
      {
          url: _ACTION_OBTENER_PRODUCTOS_ASEGURADORA_CLIENTE
      }
      ),
      reader: new Ext.data.JsonReader(
      {
      	root: 'productosAseguradoraCliente',
      	id: 'codigo'
      },
      [
   		{name: 'codigo', type: 'string',mapping:'codigo'},
   		{name: 'descripcion', type: 'string',mapping:'descripcion'}
  		]
  	)
});


//el JsonReader de la parte izquierda
var elJson = new Ext.data.JsonReader(
    {
        root : 'aotEstructuraList',
        totalProperty: 'total',
        successProperty : '@success'
    },
    [ 
    {name: 'cdIden',  mapping:'cdIden',  type: 'string'},
    {name: 'cdElemento',  mapping:'cdElemento',  type: 'string'},  
    {name: 'dsElemen',  mapping:'dsElemen',  type: 'string'},  
    {name: 'cdTipSit',  mapping:'cdTipSit',  type: 'string'},
    {name: 'dsTipSit',  mapping:'dsTipSit',  type: 'string'},
    {name: 'cdUnieco',  mapping:'cdUnieco',  type: 'string'},  
    {name: 'dsUnieco',  mapping:'dsUnieco',  type: 'string'},  
    {name: 'cdRamo',  mapping:'cdRamo',  type: 'string'},
    {name: 'dsRamo',  mapping:'dsRamo',  type: 'string'}
    ]
)

//se define el formulario
var formPanel = new Ext.FormPanel ({
            labelWidth : 10,
			//action a invocar al hacer al cargar(load) del formulario
            url : _ACTION_OBTENER_COPIA_CONFIGURAR_FORMA_CALCULO_ATRIBUTOS,
            reader : elJson,
            frame : true,
            bodyStyle : 'padding:5px 5px 0',
            bodyStyle:'background: white',
            width : 700,
            autoHeight: true,
            waitMsgTarget : true,
            defaults: {labelWidth: 90},
            layout: 'column',
            labelAlign:'right',
            layoutConfig: {columns: 2, align: 'right'},
            //se definen los campos del formulario
            items : [
                 {
                   layout:'form',
                   columnWidth: .50,
                   items:[
                   {
                   	 xtype: 'textfield',
                     fieldLabel: getLabelFromMap('copyForCalAtrVarTxtCli',helpMap,'Cliente'),
                     tooltip:getToolTipFromMap('copyForCalAtrVarTxtCli',helpMap,'Copiar cliente para forma de c&aacute;lculo '),
                     name: 'dsElemen', 
                     width: 150,
                    readOnly:true
                   },
                   {
                    xtype: 'hidden',
                    name : 'cdPerson',
                    id: 'cdPerson'
                	},
                	{
                    xtype: 'hidden',
                    name : 'cdIden',
                    id: 'cdIden'
                	}
                   ]
                 },
                 {
                   layout: 'form',
                   columnWidth: .50, 
                   items: [
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
		            fieldLabel: getLabelFromMap('copyForCalAtrVarCboCli',helpMap,'Cliente'),
		            tooltip:getToolTipFromMap('copyForCalAtrVarCboCli',helpMap,'Elija Cliente '),
                    width: 200,
                    emptyText:'Seleccionar Cliente ...',
                    selectOnFocus:true,
                    forceSelection:true,
                    allowBlank : false,
                    id:'cdElementoId',
                    onSelect: function (record) {
                        formPanel.findById(('cdPerson')).setValue(record.get("cdPerson"));
                        formPanel.findById(('cdRamoId')).setValue('');
                        dsProductos.removeAll();
	                    dsProductos.load({
	                                    	params: {
			                                    	cdElemento: record.get("cdElemento") ,
			                                    	cdUnieco: formPanel.findById(('cdUniecoId')).getValue()
			                                    	},
	                         	            waitMsg: 'Espere por favor....'
	                            		 });
	                    formPanel.findById(('cdUniecoId')).setValue('');
                        dsAseguradora.removeAll();
                        dsAseguradora.load({
	                                    	params: {
		                                    		cdElemento: record.get("cdElemento")
		                                    		},
	                         	            waitMsg: 'Espere por favor....'
	                            		 });
                        this.collapse();
                        this.setValue(record.get("cdElemento"));
                        }
                    }
            		]
   			     },
                 {
                   layout:'form',
                   columnWidth: .50,
                   items:[
                   {
                   	 xtype: 'textfield',
                     fieldLabel: getLabelFromMap('copyForCalAtrVarTxtAseg',helpMap,'Aseguradora'),
                     tooltip:getToolTipFromMap('copyForCalAtrVarTxtAseg',helpMap,'Copiar aseguradora para forma de c&aacute;lculo '),
                     name: 'dsUnieco', 
                     width: 150,
                     readOnly:true                     
                   }
                   ]
                 },
                 {
                   layout: 'form',
                   columnWidth: .50, 
                   items: [
                   {
                    xtype: 'combo', 
                    labelWidth: 70, 
                    tpl: '<tpl for="."><div ext:qtip="{cdUniEco}. {dsUniEco}" class="x-combo-list-item">{dsUniEco}</div></tpl>',
	                store: dsAseguradora,
	                displayField:'dsUniEco', 
	                valueField:'cdUniEco', 
	                hiddenName: 'cdUnieco', 
	                typeAhead: true,
	                mode: 'local', 
	                triggerAction: 'all', 
		            fieldLabel: getLabelFromMap('copyForCalAtrVarCboAseg',helpMap,'Aseguradora'),
		            tooltip:getToolTipFromMap('copyForCalAtrVarCboAseg',helpMap,'Elija Aseguradora '),
	                width: 200, 
	                emptyText:'Seleccionar Aseguradora...',
	                selectOnFocus:true, 
	                forceSelection:true,
	                allowBlank : false,
	                id: 'cdUniecoId',
	                onSelect: function (record) {
	                            				dsProductos.removeAll();
	                            				dsProductos.load({
	                            						params: {
	                            						cdElemento: formPanel.findById(('cdElementoId')).getValue() ,
	                            						cdUnieco: record.get('cdUniEco')
	                            						}
	                            					});
	                            				formPanel.findById("cdRamoId").setValue('');
	                            				this.setValue(record.get('cdUniEco'));
	                            				this.collapse();	
	                            		        }
                   }
           		 ]
           		 },
                 {
                   layout:'form',
                   columnWidth: .50,
                   items:[
                   {
                   	 xtype: 'textfield',
                     fieldLabel: getLabelFromMap('copyForCalAtrVarTxtProd',helpMap,'Producto'),
                     tooltip:getToolTipFromMap('copyForCalAtrVarTxtProd',helpMap,'Copiar producto para forma de c&aacute;lculo '),
                     name: 'dsRamo', 
                     width: 150,
                     readOnly:true          
                   }
                   ]
                 },
                 {
                   layout: 'form',
                   columnWidth: .50, 
                   items: [
                   {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                    store: dsProductos,
                    displayField:'descripcion',
                    valueField:'codigo',
                    hiddenName: 'cdRamo',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
		            fieldLabel: getLabelFromMap('copyForCalAtrVarCboProd',helpMap,'Producto'),
		            tooltip:getToolTipFromMap('copyForCalAtrVarCboProd',helpMap,'Elija Producto '),
                    width: 200,
                    emptyText:'Seleccionar Producto...',
                    selectOnFocus:true,
                    forceSelection:true,
                    allowBlank : false,
                    id:'cdRamoId'
            	   }
            	  ]
   			     },
                 {
                   layout:'form',
                   columnWidth: .50,
                   items:[
                   {
                   	 xtype: 'textfield',
                     fieldLabel: getLabelFromMap('copyForCalAtrVarTxtTS',helpMap,'Tipo de Situaci&oacute;n'),
                     tooltip:getToolTipFromMap('copyForCalAtrVarTxtTS',helpMap,'Copiar tipo de situaci&oacute;n para forma de c&aacute;lculo '),
                     name: 'dsTipSit',
                     width: 150, 
                     readOnly:true                     
                   }
                   ]
                 }
                 ]
});


//Windows donde se van a visualizar la pantalla
var window = new Ext.Window({
        	title:  '<span style="color:black;font-size:12px;">'+getLabelFromMap('123',helpMap,'Copiar Forma de C&aacute;lculo de Atributos Variables')+'</span>', 
            width: 700,
            autoHeight: true,
            bodyStyle:'padding:5px;',
            buttonAlign:'center',
            modal: true,
            labelAlign:true,
        	items: formPanel,
            //se definen los botones del formulario
            buttons : [ {
                text:getLabelFromMap('copyForCalAtrVarBtnCop',helpMap,'Copiar'),
                tooltip: getToolTipFromMap('copyForCalAtrVarBtnCop',helpMap,'Copia atributos de Configuraci&oacute;n'),
                disabled : false,
                handler : function() {
                    if (formPanel.form.isValid()) {
                        formPanel.form.submit( {
                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_COPIAR_CONFIGURAR_FORMA_CALCULO_ATRIBUTOS,
                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, accion) {
                            	if ((accion.result.cdError)== "200064")
                            	{
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), accion.result.actionMessages[0], function() {reloadGrid(Ext.getCmp('grid2'));});
                                window.close()
                                
                                }else
                                {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), accion.result.actionMessages[0], function() {/*reloadGrid(Ext.getCmp('grid2'));*/})
                                
                                }                                     
                            },
                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, accion) {
                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'), accion.result.errorMessages[0])
                           },
                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg :getLabelFromMap('400034',helpMap,'copiando datos ...')
                        });
                    } else {
                        Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'),'Por favor complete la informaci&oacute;n requerida!')
                    }
                }
            }, {
                text:getLabelFromMap('copyForCalAtrVarBtnCanc',helpMap,'Cancelar'),
                tooltip: getToolTipFromMap('copyForCalAtrVarBtnCanc',helpMap,'Cancela copia atributos de Configuraci&oacute;n'),
                handler : function() {
                window.close()
                }
            }]
});



formPanel.form.load( {
	params: {
			cdIden: key
			},
	success: function(){
	formPanel.findById('cdElementoId').clearValue();
	formPanel.findById('cdUniecoId').clearValue();
	formPanel.findById('cdRamoId').clearValue()
	}
});

window.show();
dsClientesCorp.load();
dsAseguradora.load();
dsProductos.load()


}

