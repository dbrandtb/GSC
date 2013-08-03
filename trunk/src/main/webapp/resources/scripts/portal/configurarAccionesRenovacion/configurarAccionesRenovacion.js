Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

var codRenova= new Ext.form.Hidden({
    	disabled:false,
        name:'codRenova',
        value: CODIGO_CDRENOVA
    });

var desElemen = new Ext.form.TextField({
    xtype: 'textfield',
    fieldLabel: 'Cliente',
    name: 'dsElemen', 
    readOnly: true,
    width: 120,
    id: 'desElemenId'
});

var desTipo = new Ext.form.TextField({
    xtype: 'textfield',
    fieldLabel: 'Tipo',
    name: 'dsTipoRenova',
    readOnly: true,
    width: 120,
    id: 'desTipoId'
});

var desUnieco = new Ext.form.TextField({
    xtype: 'textfield',
    fieldLabel: 'Aseguradora',
    name: 'dsUniEco',
    readOnly: true,
    width: 120,
    id: 'desUniecoId'
});

var desRamo = new Ext.form.TextField({
    xtype: 'textfield',
    fieldLabel: 'Producto',
    name: 'dsRamo',
    readOnly: true,
    width: 120,
    id: 'desRamoId'
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

var incisosForm = new Ext.FormPanel({
	   id: 'incisosForm',
       el:'formBusqueda',
       title: '<span style="color:black;font-size:12px;">Acciones de Renovaci&oacute;n</span>',
       iconCls:'logo',
       bodyStyle:'background: white',
       labelAlign: 'right',
       frame:true,   
       url: _ACTION_OBTENER_CONFIGURAR_ACCIONES_RENOVACION,
       reader: elJson,
       width: 500,
       height: 120,
       items: [
       		{
       		layout:'column',
			items:[
			  {
        		columnWidth: .50,
           		layout: 'form',
        		items: [
						{
						 layout:'form',
						 columnWidth: .50,
						 items:[
						         desElemen
						       ]
						},
        				{
						 layout:'form',
						 columnWidth: .50,
						 items:[
						         desTipo
						       ]
						}       		
               			]      			
     		  },
     		  {
     			columnWidth: .50,
           		layout: 'form',
        		items: [
     				  			{
						 	layout:'form',
						 	columnWidth: .50,
						 	items:[
						    	     desUnieco
						       	]
							},
							{
						 	layout:'form',
						 	columnWidth: .50,
						 	items:[
						    	     desRamo
						       	]
							}
     				       ]
       	    		}
       			]	            
			}
			]
	}
);   


// Definicion de las columnas de la grilla
var cm = new Ext.grid.ColumnModel([
        {
        header: "Rol",
        dataIndex: 'dsRol',
        sortable: true,
        width: 120
        },
        {
        header: "Pantalla",
        dataIndex: 'dsTitulo',
        sortable: true,
        width: 120
        },
        {
        header: "Campo",
        dataIndex: 'dsCampo',
        sortable: true,
        width: 120
        },
        {
        header: "Acci&oacute;n",
        dataIndex: 'dsAccion',
        sortable: true,
        width: 120
        },
        {
        //header: "cdRenova",
        dataIndex: 'cdRenova',
        hidden :true
        },
        {
        dataIndex: 'cdRol',
        hidden :true
        },
        {
        dataIndex: 'cdTitulo',
        hidden :true
        },
        {
        dataIndex: 'cdCampo',
        hidden :true
        },
        {
        dataIndex: 'cdAccion',
        hidden :true
        }

]);

var lasFilas=new Ext.data.Record.create([
  {name: 'cdRenova',  mapping:'cdRenova',  type: 'string'},
  {name: 'cdRol',  mapping:'cdRol',  type: 'string'},
  {name: 'dsRol',  mapping:'dsRol',  type: 'string'},
  {name: 'cdTitulo',  mapping:'cdTitulo',  type: 'string'},
  {name: 'dsTitulo',  mapping:'dsTitulo',  type: 'string'},
  {name: 'cdCampo',  mapping:'cdCampo',  type: 'string'},      
  {name: 'dsCampo',  mapping:'dsCampo',  type: 'string'},      
  {name: 'cdAccion',  mapping:'cdAccion',  type: 'string'},               
  {name: 'dsAccion',  mapping:'dsAccion',  type: 'string'}               
]);

var jsonGrilla= new Ext.data.JsonReader(
  {
   root:'MEstructuraList',
   totalProperty: 'totalCount',
   successProperty : '@success'
  },
 lasFilas  
);

function storeGrilla(){
             store = new Ext.data.Store({
             proxy: new Ext.data.HttpProxy({
             url: _ACTION_BUSCAR_CONFIGURAR_ACCIONES_RENOVACION,
						waitMsg: getLabelFromMap('400017',helpMap,'Espere por favor...')
		                }),
             reader:jsonGrilla
             });
             return store;
}

var grid2;

function createGrid(){
       grid2= new Ext.grid.GridPanel({
       		id: 'grid2',
            el:'gridElementos',
            store:storeGrilla(),
            reader:jsonGrilla,
            border:true,
            loadMask: {msg: getLabelFromMap('400058',helpMap,'Cargando datos ...'), disabled: false},
            width: 500,
            buttonAlign:'Center',
            loadMask: {msg: getLabelFromMap('400058',helpMap,'Cargando datos ...'), disabled: false},
            cm: cm,
            title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
            clicksToEdit:1,
	        successProperty: 'success',
            buttons:[
                  {
                  text:'Agregar',
                  tooltip:'Crea una Nueva Acci&oacute;n de Renovaci&oacute;n',
                  handler:function(){
                        		agregar(CODIGO_CDRENOVA);
						}
                  },
				  {
                  text:'Editar',
                  tooltip:'Edita una Acci&oacute;n de Renovaci&oacute;n',
                  handler:function(){
						if (getSelectedKey(grid2, "cdRenova") != "") {
                        		editar(getSelectedKey(grid2, "cdRenova"));
                        } else {
                                 Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operaci&oacute;n');
                                }
                   }
                  },                
                  {
                  text:'Eliminar',
                  tooltip:'Elimina una Acci&oacute;n de Renovaci&oacute;n',
                  handler:function(){
						if (getSelectedRecord1(grid2) != null) {								
                        		borrar(getSelectedRecord1(grid2));
               			} else {
                                Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operaci&oacute;n');
                        }
                	}
                  },
                  {
                  text:'Exportar',
                  tooltip:'Exporta la B&uacute;squeda Acci&oacute;n de Renovaci&oacute;n',
                  handler:function(){
                        var url = _ACTION_EXPORTAR_CONFIGURAR_ACCIONES_RENOVACION + '?cdRenova=' + codRenova.getValue();
                	 	showExportDialog( url );
                    }
                  },
                  {
					text:'Regresar', 
					tooltip:'Vuelve a la Pantalla Anterior',                             
					handler: function() {
					window.location=_ACTION_REGRESAR_A_CONSULTA_CONFIGURARCION_RENOVACION + '?cdRenova=' + CODIGO_CDRENOVA;
					}
                  }
                  ],
            width:500,
            frame:true,
            height:320,
            buttonAlign:'center',
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			//viewConfig: {autoFill: true,forceFit:true},
			stripeRows: true,
			collapsible:true,
			bbar: new Ext.PagingToolbar({
					pageSize:20,
					store: store,
					displayInfo: true,
	                displayMsg: 'Mostrando registros {0} - {1} of {2}',
	                emptyMsg: "No hay registros para visualizar"
			    })          
        });
   grid2.render()
}

//Muestra los componentes en pantalla
incisosForm.render();
createGrid();

if (CODIGO_CDRENOVA!="")
{
	incisosForm.form.load({
		//waitTitle: 'Espere',
	    //waitMsg: "Leyendo configuraci&oacute;n....",
	    params: {cdRenova: codRenova.getValue()},
	    success: function () {
	               			reloadGrid();
	               		   },
	   failure: function () {
	                        Ext.Msg.alert("Error", "No se encontraron registros");
	                        }                       
	});
}

function getSelectedRecord1(_grid){
	if (_grid == null || _grid == undefined) return null;
     var m = _grid.getSelections();     
     /*if (m.length == 1 ) {
        return m[0];
     }*/
     if (m.length == 1 ){return m[0]}else{return null}
}

function borrar(record) {
		if(record.get('cdRenova') != "" && record.get('cdTitulo') != "" && record.get('cdRol') != "" && record.get('cdCampo'))
		{
			Ext.MessageBox.confirm('Aviso', 'Se eliminar&aacute; el registro seleccionado',function(btn)
			{
		        if (btn == "yes")
		        {
         			var _params = {
         						cdRenova: record.get('cdRenova'),
         						cdTitulo: record.get('cdTitulo'),
         						cdRol: record.get('cdRol'),
         						cdCampo: record.get('cdCampo')
         			};
         			execConnection(_ACTION_BORRAR_CONFIGURAR_ACCIONES_RENOVACION, _params, cbkConnection);
               }
			})
		}else{
				Ext.Msg.alert('Aviso', 'Debe seleccionar un registro para realizar esta operaci&oacute;n');
		}
};

function cbkConnection (_success, _message) {
	if (!_success) {
		Ext.Msg.alert('Error', _message);
	}else {
		Ext.Msg.alert('Aviso', _message, function(){reloadGrid()});
	}
}



});

function reloadGrid(){
	var _params = {
       		cdRenova:CODIGO_CDRENOVA 
	};
	reloadComponentStore(Ext.getCmp('grid2'), _params, cbkReload);
}
function cbkReload(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert('Error', _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}
}
