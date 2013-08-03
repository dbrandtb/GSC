// Funcion de Editar Atributos Variables

function editar(key) {

var codRenova= new Ext.form.Hidden({
    	disabled:false,
        name:'cdRenova',
        value: key
});
    
var codRol= new Ext.form.Hidden({
    	disabled:false,
        name:'cdRol',
        value: key
});

var codTitulo= new Ext.form.Hidden({
    	disabled:false,
        name:'cdTitulo',
        value: key
});        

var codCampo= new Ext.form.Hidden({
    	disabled:false,
        name:'cdCampo',
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

//el JsonReader de las Acciones
var elJsonAccion = new Ext.data.JsonReader(
    {
        root : 'accEstructuraList',
        totalProperty: 'total',
        successProperty : '@success'
    },
    [ 
    {name: 'cdRol',  mapping:'cdRol',  type: 'string'},
    {name: 'dsRol',  mapping:'dsRol',  type: 'string'},
    {name: 'cdTitulo',  mapping:'cdTitulo',  type: 'string'},
    {name: 'dsTitulo',  mapping:'dsTitulo',  type: 'string'},  
    {name: 'cdCampo',  mapping:'cdCampo',  type: 'string'},
    {name: 'dsCampo',  mapping:'dsCampo',  type: 'string'}
    ]
);

//combos el Store
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

//Ext.Msg.alert('Informacion', key);
var desObtengoAccion = new Ext.data.Store(
{
    proxy: new Ext.data.HttpProxy(
    {
        url: _ACTION_OBTENER_CONFIGURAR_ACCIONES_RENOVACION_ACCIONES
    }
    ),
    reader: elJsonAccion,
		baseParams: {
			cdRenova: key   
		}
}
);

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

var desRoles = new Ext.form.TextField({
    xtype: 'textfield',
    fieldLabel: 'Rol',
    name: 'dsRol',
    readOnly: true,
    width: 150,
    id: 'dsRolId'
});

var desPantalla = new Ext.form.TextField({
    xtype: 'textfield',
    fieldLabel: 'Pantalla',
    name: 'dsTitulo',
    readOnly: true,
    width: 116,
    id: 'dsTituloId'
});

var desCampos = new Ext.form.TextField({
    xtype: 'textfield',
    fieldLabel: 'Campo',
    name: 'dsCampo',
    readOnly: true,
    width: 116,
    id: 'dsCampoId'
});

//definicion de los combos
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
var _formPanel = new Ext.FormPanel( {
       //labelWidth : 10,
       url : _ACTION_OBTENER_CONFIGURAR_ACCIONES_RENOVACION,
       reader: elJson,
       frame : true,
       width : 560,
       labelAlign:'right',
       buttonAlign:'center',
       bodyStyle:'background: white',
       height: 220,            
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
          							codRenova,
          							desElemen,
          							desTipo,
          							desRoles
          							]
         				    },
         				    {//2da columna
          				   	columnWidth: .50,
          					layout: 'form',
          					items: [
          							codRol,
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
								items: [
										codTitulo,
										desPantalla
										]
							},
							{//2da columna
								columnWidth: .33,
								layout: 'form',
								items: [
										codCampo,
										desCampos
										]
							},
							{//3da columna
								columnWidth: .33,
								layout: 'form',
								items: [comboAccion]
							}
						]
					}
		   ]
	}
);   

//Windows donde se van a visualizar la pantalla
var _window = new Ext.Window({
	title: 'Configurar Acciones de Renovaci&oacute;n',
	width: 550,
	height:250,
	modal: true,
	plain:true,
	bodyStyle:'padding:5px;',
	buttonAlign:'center',
	items: [_formPanel],
    //se definen los botones del formulario
    buttons : [ {
        text : 'Guardar',
        disabled : false,
        handler : function() {
            if (_formPanel.form.isValid()) {
                _formPanel.form.submit( {
                    //action a invocar cuando el formulario haga submit
                    url : _ACTION_GUARDAR_CONFIGURAR_ACCIONES_RENOVACION,
                    //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                    success : function(from, action) {
                        Ext.MessageBox.alert('Aviso', action.result.actionMessages[0], function(){reloadGrid();});
                        _window.close();
                    },
                    //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                    failure : function(form, action) {
                        Ext.MessageBox.alert('Error', action.result.errorMessages[0]);
                    },
                    //mensaje a mostrar mientras se guardan los datos
                    waitMsg : 'guardando datos ...'
                });
            } else {
                Ext.Msg.alert('Aviso', 'Por favor complete la informaci&oacute;n requerida');
            }
        }
    }, 
    {
        text : 'Regresar',
        handler : function() {
        _window.close();
        }
    }]
});

desAccion.load();
_formPanel.form.load(
{
	params:{cdRenova: key},
	success:function(){
		desObtengoAccion.load({
		    callback:function(record,opt,success){
			    if (success){
			    	codRol.setValue(desObtengoAccion.reader.jsonData.accEstructuraList[0].cdRol);
			    	desRoles.setValue(desObtengoAccion.reader.jsonData.accEstructuraList[0].dsRol);
			      	codTitulo.setValue(desObtengoAccion.reader.jsonData.accEstructuraList[0].cdTitulo);
			      	desPantalla.setValue(desObtengoAccion.reader.jsonData.accEstructuraList[0].dsTitulo);
			      	codCampo.setValue(desObtengoAccion.reader.jsonData.accEstructuraList[0].cdCampo);
			      	desCampos.setValue(desObtengoAccion.reader.jsonData.accEstructuraList[0].dsCampo);
			      	Ext.getCmp('comboAccionId').setValue(desObtengoAccion.reader.jsonData.accEstructuraList[0].cdAccion)
			    }
		    }	                         
		})
	},
	failure: function(){
		Ext.Msg.alert("Error", "No se encontraron registros");
	} 	
}
);
_window.show();
        
};