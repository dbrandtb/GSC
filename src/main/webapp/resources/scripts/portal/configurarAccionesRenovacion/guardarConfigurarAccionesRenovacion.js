// Funcion de Editar Atributos Variables

function agregar(key) {

//Ext.Msg.alert('Informacion', key);
var codRenova= new Ext.form.Hidden({
    	disabled:false,
        name:'cdRenova',
        value: key
});
    			
//el JsonReader del encabezado
var elJson = new Ext.data.JsonReader(
    {
        root : 'aotEstructuraList',
        totalProperty: 'total',
        successProperty : '@success'
    },
    [ 
    {name: 'dsElemen',  mapping:'dsElemen',  type: 'string'},
    {name: 'dsUniEco',  mapping:'dsUniEco',  type: 'string'},  
    {name: 'dsRamo',  mapping:'dsRamo',  type: 'string'},
    {name: 'dsTipoRenova',  mapping:'dsTipoRenova',  type: 'string'}  
    ]
)
//combos el Store
var desRoles = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: _ACTION_OBTENER_ACCIONES_RENOVACION_ROLES
            }),
        reader: new Ext.data.JsonReader({
        root: 'comboAccionesRenovacionRoles',
        id: 'codigo'
        },[
       {name: 'codigo', mapping:'codigo', type: 'string'},
       {name: 'descripcion', mapping:'descripcion', type: 'string'}
    ])
});

var desPantalla = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
        	url: _ACTION_OBTENER_ACCIONES_RENOVACION_PANTALLA
        	}),
        reader: new Ext.data.JsonReader({
        root: 'comboAccionesRenovacionPantalla',
        id: 'codigo'
        },[
       {name: 'codigo', mapping:'codigo', type: 'string'},
       {name: 'descripcion', mapping:'descripcion', type: 'string'}
    ])
});

var desCampos = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
        	url: _ACTION_OBTENER_ACCIONES_RENOVACION_CAMPO
        	}),
        reader: new Ext.data.JsonReader({
        root: 'comboAccionesRenovacionCampo',
        id: 'codigo'
        },[
       {name: 'codigo', mapping:'codigo', type: 'string'},
       {name: 'descripcion', mapping:'descripcion', type: 'string'}
    ])
});

var desAccion = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
        	url: _ACTION_OBTENER_ACCIONES_RENOVACION_ACCION
        	}),
        reader: new Ext.data.JsonReader({
        root: 'comboAccionesRenovacionAccion',
        id: 'codigo'
        },[
       {name: 'codigo', mapping:'codigo', type: 'string'},
       {name: 'descripcion', mapping:'descripcion', type: 'string'}
    ])
});

//definicion de los textfield
var desElemen = new Ext.form.TextField({
    xtype: 'textfield',
    fieldLabel: 'Cliente',
    name: 'dsElemen',
    readOnly: true,
    width: 150,
    id: 'dsElemenId'
});

var desTipo = new Ext.form.TextField({
    xtype: 'textfield',
    fieldLabel: 'Tipo',
    name: 'dsTipoRenova',
    readOnly: true,
    width: 150,
    id: 'dsTipoRenovaId'
});

var desUnieco = new Ext.form.TextField({
    xtype: 'textfield',
    fieldLabel: 'Aseguradora',
    name: 'dsUniEco',
    readOnly: true,
    width: 150,
    id: 'dsUniEcoId'
});

var desRamo = new Ext.form.TextField({
    xtype: 'textfield',
    fieldLabel: 'Producto',
    name: 'dsRamo',
    readOnly: true,
    width: 150,
    id: 'dsRamoId'
});

//definicion de los combos
var comboRoles = new Ext.form.ComboBox({
    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
    id:'comboRolesId',
    store: desRoles,
    displayField:'descripcion',
    valueField:'codigo',
    hiddenName: 'cdRol',
    typeAhead: true,
    allowBlank : false,
    mode: 'local',
    triggerAction: 'all',
    fieldLabel: "Rol",
    width: 150,
    selectOnFocus:true,
    forceSelection:true,
    emptyText:'Seleccione rol...'
});	

var comboPantalla = new Ext.form.ComboBox({
    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
    id:'comboPantallaId',
    store: desPantalla,
    displayField:'descripcion',
    valueField:'codigo',
    hiddenName: 'cdTitulo',
    typeAhead: true,
    allowBlank : false,
    mode: 'local',
    triggerAction: 'all',
    fieldLabel: "Pantalla",
    width: 116,
    selectOnFocus:true,
    forceSelection:true,
    emptyText:'Seleccione titulo...',
    onSelect: function (record) {
		desCampos.removeAll();
		this.setValue(record.get('codigo'));
		desCampos.load({
				params: {
					cdTitulo: formPanel.findById('comboPantallaId').getValue()
				}
			});
		formPanel.findById("comboCampoId").setRawValue('');
		
		this.collapse();	
        }
});	
    
var comboCampo = new Ext.form.ComboBox({
    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
    id:'comboCampoId',
    store: desCampos,
    displayField:'descripcion',
    valueField:'codigo',
    hiddenName: 'cdCampo',
    typeAhead: true,
    allowBlank : false,
    mode: 'local',
    triggerAction: 'all',
    fieldLabel: "Campo",
    width: 116,
    selectOnFocus:true,
    forceSelection:true,
    emptyText:'Seleccione campo...'
});	

var comboAccion = new Ext.form.ComboBox({
    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
    id:'comboAccionId',
    store: 	desAccion,
    displayField:'descripcion',
    valueField:'codigo',
    hiddenName: 'cdAccion',
    typeAhead: true,
    allowBlank : false,
    mode: 'local',
    triggerAction: 'all',
    fieldLabel: "Accion",
    width: 116,
    selectOnFocus:true,
    forceSelection:true,
    emptyText:'Seleccione accion...'
});	

//**************************************************************************//    
//se define el formulario
var formPanel = new Ext.FormPanel( {
       //labelWidth : 10,
       url : _ACTION_OBTENER_CONFIGURAR_ACCIONES_RENOVACION,
       reader: elJson,
       frame : true,
       width : 560,
       bodyStyle:'background: white',
       height: 260,
       labelAlign:'right',            
       waitMsgTarget : true,
       //se definen los campos del formulario
      items : [
       		    {
         			layout: 'column',
					defaults:{labelWidth:68},
         			items:[
         					{//1ra columna
          					columnWidth: .50,
          					layout: 'form',
          					items: [
          							desElemen,
          							desTipo,
          							comboRoles
          							]
         				    },
         				    {//2da columna
          				   	columnWidth: .50,
          					layout: 'form',
          					items: [
          							desUnieco,
          							desRamo
          							]
         				    }
         			]
         		   },{
						layout: 'column',
						defaults:{labelWidth:40},
						items:[
								{//1ra columna
									columnWidth: .33,
									layout: 'form',
									items: [comboPantalla]
							    },
							    {//2da columna
								   	columnWidth: .33,
									layout: 'form',
									items: [comboCampo]
							    },
							    {//3da columna
								   	columnWidth: .33,
									layout: 'form',
									items: [
										codRenova,
										comboAccion
										]
							    }
						]
	    			}
	         ]
	}
);    

//Windows donde se van a visualizar la pantalla
var window = new Ext.Window({
	title: 'Configurar Acciones de Renovaci&oacute;n',
	width: 550,
    height:300,
	plain:true,
	bodyStyle:'padding:5px;',
	modal: true,
	buttonAlign:'center',
	labelAlign:'right',
	items: [formPanel],
    //se definen los botones del formulario
    buttons : [ {
        text : 'Guardar',
        disabled : false,
        handler : function() {
            if (formPanel.form.isValid()) {
                formPanel.form.submit( {
                    //action a invocar cuando el formulario haga submit
                    url : _ACTION_GUARDAR_CONFIGURAR_ACCIONES_RENOVACION,
                    //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                    success : function(from, action) {
                        Ext.MessageBox.alert('Aviso', action.result.actionMessages[0], function(){reloadGrid();});
                        window.close();
                    },
                    //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                    failure : function(form, action) {
                        Ext.MessageBox.alert('Error', action.result.errorMessages[0]);
                    },
                    //mensaje a mostrar mientras se guardan los datos
                    waitMsg : 'guardando datos ...'
                });
            } else {
                Ext.Msg.alert('Aviso', 'Complete la informaci&oacute;n requerida');
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
    	
formPanel.form.load(
{
	params:{cdRenova: key},
	success:function(){
		desRoles.load({
			params:{
					cdRenova:key	
			}	
		}),
		desPantalla.load(),
		desAccion.load()	
	},
	failure: function () {
        Ext.Msg.alert("Error", "No se encontraron registros");
    } 
}
);

window.show();
        
};