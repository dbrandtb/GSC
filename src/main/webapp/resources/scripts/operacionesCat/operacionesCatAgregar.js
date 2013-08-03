function agregar(){

var dsAseguradoras = new Ext.data.Store({
 	proxy: new Ext.data.HttpProxy({
     	url: _OBTENER_ASEGURADORAS
 	}),
 	reader: new Ext.data.JsonReader({
 		root: 'aseguradoraComboBox',
 		totalProperty: 'totalCount',
 		id: 'cdUniEco'
 		},[
			{name: 'cdUniEco', type: 'string',mapping:'cdUniEco'},
			{name: 'dsUniEco', type: 'string',mapping:'dsUniEco'}
		  ])
});
	

var dsProductos = new Ext.data.Store({
 	proxy: new Ext.data.HttpProxy({
     	url: _OBTENER_PRODUCTOS_2
 	}),
 	reader: new Ext.data.JsonReader({
 		root: 'ramosComboBox',
 		totalProperty: 'totalCount',
 		id: 'cdRamo'
 		},[
			{name: 'cdRamo', type: 'string',mapping:'cdRamo'},
			{name: 'dsRamo', type: 'string',mapping:'dsRamo'}
		  ])
});

var dsEstados = new Ext.data.Store({
 	proxy: new Ext.data.HttpProxy({
     	url: _OBTENER_ESTADOS
 	}),
 	reader: new Ext.data.JsonReader({
 		root: 'estadosEjecutivo',
 		totalProperty: 'totalCount',
 		id: 'id'
 		},[
			{name: 'id', type: 'string',mapping:'codigo'},
			{name: 'texto', type: 'string',mapping:'descripcion'}
		  ])
});

var dsTipoGuion = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
        url: _OBTENER_TIPO_GUION
    }),
    reader: new Ext.data.JsonReader({
        root: 'comboTipoGuion',
        totalProperty: 'totalCount',
        id: 'codigo'
        },[
            {name: 'codigo', type: 'string',mapping:'codigo'},
            {name: 'descripcion', type: 'string',mapping:'descripCorta'},
            {name: 'descripLarga', type: 'string',mapping:'descripLarga'}            
          ])
});

var dsProcesos = new Ext.data.Store({
 	proxy: new Ext.data.HttpProxy({
     	url: _OBTENER_PROCESOS
 	}),
 	reader: new Ext.data.JsonReader({
 		root: 'comboProcesosCat',
 		totalProperty: 'totalCount',
 		id: 'cdProceso'
 		},[
			{name: 'cdProceso', type: 'string',mapping:'cdProceso'},
			{name: 'dsProceso', type: 'string',mapping:'dsProceso'}
		  ])
});		 	
		 	
var dsClientesCorpo = new Ext.data.Store({
 	proxy: new Ext.data.HttpProxy({
     	url: _OBTENER_CLIENTES_CORPO
 	}),
    reader: new Ext.data.JsonReader({
        root: 'comboClientesCorpBO',
        id: 'cdElemento',
        successProperty: '@success'
    }, [
        {name: 'cdElemento', type: 'string', mapping: 'cdElemento'},
        {name: 'cdPerson', type: 'string', mapping: 'cdPerson'},
        {name: 'dsElemen', type: 'string', mapping: 'dsElemen'} 
    ])
    

});	

		 		 	
var guion = new Ext.form.TextField({
   	fieldLabel: getLabelFromMap('guionId', helpMap,'Gui&oacute;n'), 
	tooltip: getToolTipFromMap('guionId', helpMap,'Gui&oacute;n'),  				                    	
    id: 'guionId',
    allowBlank: false,
    name: 'guion',
    width: 200
});
    

var indInicial = new Ext.form.Checkbox({
   	fieldLabel: getLabelFromMap('indInicialId', helpMap,'Script Inicial'), 
	tooltip: getToolTipFromMap('indInicialId', helpMap,'Script Inicial'),  				                    	
    id: 'indInicialId', 
    name: 'indInicial',
    allowBlank: true
});


//LOS COMBOS

var comboAseguradora = new Ext.form.ComboBox(
{
    tpl: '<tpl for="."><div ext:qtip="{cdUniEco}.{dsUniEco}" class="x-combo-list-item">{dsUniEco}</div></tpl>',
    store: dsAseguradoras,
    id:'cdUniEcoId',
    fieldLabel: getLabelFromMap('cdUniEcoId',helpMap,'Aseguradoras'),
    tooltip: getToolTipFromMap('cdUniEcoId',helpMap,'Listado de Aseguradoras'),
    //anchor:'100%',
    width: 200,
    displayField:'dsUniEco',
    valueField: 'cdUniEco',
    hiddenName: 'cdUniEco',
    typeAhead: true,
    allowBlank: false,
    triggerAction: 'all',
    mode:'local',
    emptyText:'Seleccionar Aseguradora...',
    selectOnFocus:true,
    forceSelection:true,
       onSelect: function (record){
    	 this.setValue(record.get("cdUniEco"));
         this.collapse();
         dsProductos.reload ({	params: {
                            	cdunieco: record.get("cdUniEco"),                           					
                            	cdelemento: formWindowEdit.findById('cdGrupoId').getValue(), 
                            	cdramo: ""
                            				}
                            		});         
    }
    
}
);

var comboGrupo = new Ext.form.ComboBox(
{
    tpl: '<tpl for="."><div ext:qtip="{cdElemento}.{dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
    store: dsClientesCorpo,
    id:'cdGrupoId',
    fieldLabel: getLabelFromMap('cdGrupoId',helpMap,'Grupo'),
    tooltip: getToolTipFromMap('cdGrupoId',helpMap,'Listado de Grupos'),
    //anchor:'100%',
    width: 200,
    displayField:'dsElemen',
    valueField: 'cdElemento',
    hiddenName: 'cdElemento',
    typeAhead: true,
    allowBlank: false,
    triggerAction: 'all',
    mode:'local',
    emptyText:'Seleccionar Grupo...',
    selectOnFocus:true,
    forceSelection:true,
    onSelect: function (record){
    	 this.setValue(record.get("cdElemento"));
         this.collapse();
         dsProductos.reload ({	params: {
                            	cdunieco: formWindowEdit.findById('cdUniEcoId').getValue(),                          					
                            	cdelemento: record.get("cdElemento"),
                            	cdramo: ""
                            				}
                            		});         
    }
    
}
);


var comboProceso = new Ext.form.ComboBox(
{
    tpl: '<tpl for="."><div ext:qtip="{cdProceso}.{dsProceso}" class="x-combo-list-item">{dsProceso}</div></tpl>',
    store: dsProcesos,
    id:'cdProcesoId',
    fieldLabel: getLabelFromMap('cdProcesoId',helpMap,'Proceso'),
    tooltip: getToolTipFromMap('cdProcesoId',helpMap,'Listado de Procesos'),
    //anchor:'100%',
    width: 200,
    displayField:'dsProceso',
    valueField: 'cdProceso',
    hiddenName: 'cdProceso',
    typeAhead: true,
    allowBlank: false,
    triggerAction: 'all',
    //mode:'local',
    emptyText:'Seleccionar Proceso...',
    selectOnFocus:true,
    forceSelection:true
}
);


var comboTipoGuion = new Ext.form.ComboBox(
{
    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}.{descripLarga}" class="x-combo-list-item">{descripcion}</div></tpl>',
    store: dsTipoGuion,
    id:'cdTipoGuionId',
    fieldLabel: getLabelFromMap('cdTipoGuionId',helpMap,'Tipo de Gui&oacute;n'),
    tooltip: getToolTipFromMap('cdTipoGuionId',helpMap,'Listado de Tipos de Gui&oacute;n'),
    //anchor:'100%',
    width: 200,
    displayField:'descripcion',
    valueField: 'codigo',
    hiddenName: 'codigoTipoGuion',
    typeAhead: true,
    allowBlank: false,
    triggerAction: 'all',
    mode:'local',
    emptyText:'Seleccionar Tipo de Guion...',
    selectOnFocus:true,
    forceSelection:true
}
);

var comboProducto = new Ext.form.ComboBox(
{
    tpl: '<tpl for="."><div ext:qtip="{cdRamo}.{dsRamo}" class="x-combo-list-item">{dsRamo}</div></tpl>',
    store: dsProductos,
    id:'cdRamoId',
    fieldLabel: getLabelFromMap('cdRamoId',helpMap,'Producto'),
    tooltip: getToolTipFromMap('cdRamoId',helpMap,'Listado de Productos'),
    //anchor:'100%',
    width: 200,
    displayField:'dsRamo',
    valueField: 'cdRamo',
    hiddenName: 'cdRamo',
    typeAhead: true,
    triggerAction: 'all',
    mode:'local',
    allowBlank: false,
    forceSelection:true,
    emptyText:'Seleccionar Producto...',
    selectOnFocus:true 
 
}
);

var comboStatus = new Ext.form.ComboBox(
{
    tpl: '<tpl for="."><div ext:qtip="{id}.{texto}" class="x-combo-list-item">{texto}</div></tpl>',
    store: dsEstados,
    id:'statusId',
    fieldLabel: getLabelFromMap('statusId',helpMap,'Estado'),
    tooltip: getToolTipFromMap('statusId',helpMap,'Listado de Status'),
    //anchor:'100%',
    width: 200,
    displayField:'texto',
    valueField: 'id',
    hiddenName: 'cdEstado',
    typeAhead: true,
    triggerAction: 'all',
    mode:'local',
    allowBlank: false,
    emptyText:'Seleccionar Status...',
    selectOnFocus:true,
    forceSelection:true
}
);

//READERS Y STORE DE GRILLA***************************************************************
var recordDialogos = new Ext.data.Record.create([
      {name : 'cdDialogo', mapping : 'cdDialogo', type : 'string'},
      {name : 'dsDialogo', mapping : 'dsDialogo', type : 'string'},
      {name : 'cdSecuencia', mapping : 'cdSecuencia', type : 'string'},
      {name : 'otTapVal', mapping : 'otTapVal', type : 'string'}
    ]);
    
    
var readerDialogos = new Ext.data.JsonReader({
          root : 'csoEstructuraList',
          totalProperty: 'totalCount',
          successProperty : '@success'
      },
      recordDialogos 
);
          
function crearGridDialogosStore(){
	var dsDialogos = new Ext.data.Store ({
        proxy: new Ext.data.HttpProxy({
            url: _ACTION_BUSCAR_DIALOGOS_GUION
        }),
        reader: readerDialogos
    });
	return dsDialogos;
}


 function addGridDialogoNewRecord(){
  var new_record = new recordDialogos({
               cdDialogo: '',
		       dsDialogo:'',
		       cdSecuencia:'',
		       otTapVal:''
      });
  gridDialogos.stopEditing();
  gridDialogos.store.insert(0, new_record);
  gridDialogos.startEditing(0, 0);
  gridDialogos.getSelectionModel().selectRow(0);
 }

 function guardarDatosGuionAndDialogo (){
   var _params = "";
   var conn = new Ext.data.Connection();
   conn.request({
     url: _ACTION_GUARDAR_GUION,
     method: 'POST',
     params: {
        cdUniEco: formWindowEdit.findById("cdUniEcoId").getValue(),
        cdRamo: formWindowEdit.findById("cdRamoId").getValue(),
        cdElemento: formWindowEdit.findById("cdGrupoId").getValue(),
        //cdElemento: 5216,
        cdProceso: formWindowEdit.findById("cdProcesoId").getValue(),
        //cdGuion: formWindowEdit.findById("cdGuionId").getValue(),
        dsGuion: formWindowEdit.findById("guionId").getValue(),
        cdTipGuion: formWindowEdit.findById("cdTipoGuionId").getValue(),
        //cdTipGuion: 1,
        indInicial: (formWindowEdit.findById("indInicialId").getValue() == true )? "1" : "0",
        //indInical: 1,
        status: formWindowEdit.findById("statusId").getValue()
        //status: 1
        },
     callback: function (options, success, response) {
     	if (Ext.util.JSON.decode(response.responseText).success == false) {
            Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), 'No se pudo guardar los datos');
            //alert(1);
        }else {
            //alert(2);
           // guardarDatosDialogo();
          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), 'Los datos se guardaron con exito', function(){reloadGrid();});
          _window.close();
                       
        }
    }                
 })
}

 function guardarDatosDialogo () {
  var _params = "";
  var recs = gridDialogos.store.getModifiedRecords();
  gridDialogos.stopEditing();
  for (var i=0; i<recs.length; i++) {
   _params +=  "csoGrillaListDialogo[" + i + "].cdUniEco =" +  formWindowEdit.findById("cdUniEcoId").getValue() + "&" +
      "&csoGrillaListDialogo[" + i + "].cdRamo=" + formWindowEdit.findById("cdRamoId").getValue() + "&" +
      "&csoGrillaListDialogo[" + i + "].cdElemento=" +formWindowEdit.findById("cdGrupoId").getValue() + "&" +
      //"&csoGrillaListDialogo[" + i + "].cdElemento= 5216" + "&" +
      "&csoGrillaListDialogo[" + i + "].cdProceso=" + formWindowEdit.findById('cdProcesoId').getValue() + "&" +
      //"&csoGrillaListDialogo[" + i + "].cdGuion=" + formWindowEdit.findById("cdGuionId").getValue() + "&" +
      //"&csoGrillaListDialogo[" + i + "].cdGuion=" + 3 + "&" +
      "&csoGrillaListDialogo[" + i + "].cdDialogo=" + recs[i].get('cdDialogo') + "&" +
      "&csoGrillaListDialogo[" + i + "].dsDialogo=" + recs[i].get('dsDialogo') + "&" +
      "&csoGrillaListDialogo[" + i + "].cdSecuencia=" + recs[i].get('cdSecuencia') + "&" +
      "&csoGrillaListDialogo[" + i + "].otTapVal=" + recs[i].get('otTapVal');
  }
  if (recs.length > 0) {
   var conn = new Ext.data.Connection ();
   conn.request({
     url: _ACTION_GUARDAR_DIALOGO_GUION,
     params: _params,
     method: 'POST',
     callback: function (options, success, response) {
         if (Ext.util.JSON.decode(response.responseText).success == false) {
          Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), 'Problemas al guardar: ' + Ext.util.JSON.decode(response.responseText).errorMessages[0]);
         } else {
          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), 'Los datos se guardaron con exito', function(){reloadGrid();});
          _window.close();
          //gridDialogos.store.commitChanges();
         }
       }
     
   });
  }
 }    
 
// Definicion de las columnas de la grilla
var cm = new Ext.grid.ColumnModel([
        {
        dataIndex: 'cdDialogo',
        hidden :true
        },
        {
        header: getLabelFromMap('cmDsDialogoNtfcn',helpMap,'Dialogo'),
        tooltip: getToolTipFromMap('cmDsDialogoNtfcn',helpMap,'Columna Dialogo'),
        dataIndex: 'dsDialogo',
        width: 100,
        sortable: true,
         editor: new Ext.form.TextField({
               allowBlank: false
               })    
        },
        {
        dataIndex: 'cdSecuencia',
        hidden :true
        },
        {
        header: getLabelFromMap('cmotTapValNtfcn',helpMap,'Lista de Valores'),
        tooltip: getToolTipFromMap('cmotTapValNtfcn',helpMap,'Columna Lista de Valores'),
        dataIndex: 'otTapVal',
        width: 100,
        sortable: true,
        align: 'center',
         editor: new Ext.form.TextField({
               allowBlank: false
               })    
        }
]);

var gridDialogos;

function createGrid(){
   gridDialogos= new Ext.grid.EditorGridPanel({
   		id: 'gridDialogos',
        //el:'gridGuiones',
        store:crearGridDialogosStore(),
        title: '<span style="color:black;font-size:14px;">Configurar Dialogo</span>',
        reader:readerDialogos,
        border:true,
        cm: cm,
        clicksToEdit:1,
        buttonAlign:'center',
        successProperty: 'success',
        buttons:[
              {
              text:getLabelFromMap('gridNtfcnBottonAgregar',helpMap,'Agregar'),
              tooltip: getToolTipFromMap('gridNtfcnBottonAgregar',helpMap,'Crea una Nuevo Dialogo al Guion'),
              handler:function(){addGridDialogoNewRecord();}
              },{
              text:getLabelFromMap('gridNtfcnBottonGuardar',helpMap,'Guardar'),
              tooltip: getToolTipFromMap('gridNtfcnBottonGuardar',helpMap,'Guardar Informaci&oacute;n'),
              handler:function(){guardarDatosGuionAndDialogo(formWindowEdit)}
              },
              {
              text:getLabelFromMap('gridNtfcnBottonBorrar',helpMap,'Eliminar'),
              tooltip: getToolTipFromMap('gridNtfcnBottonBorrar',helpMap,'Eliminar un Dialogo para el Guion'),
              handler:function(){
					if (gridDialogos.getSelectionModel().getSelections().length > 0) {
                       Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'), 
                     function(btn) {
                       if (btn == "yes"){
                   			if (gridDialogos.getSelectionModel().getSelected().get("cdDialogo")==""){
                   				gridDialogos.store.remove(gridDialogos.getSelectionModel().getSelected());
                   			}else{
                   	    		 borrarDialogo();
                   			}
                   		} 
                     }
                  );
                 }else {
                  Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                 }
               }
              },
              {
              text:getLabelFromMap('gridNtfcnBottonExportar',helpMap,'Exportar'),
              tooltip: getToolTipFromMap('gridNtfcnBottonExportar',helpMap,'Exportar datos en diversos Formatos')
               /* handler:function(){
                    var url = _ACTION_EXPORTAR_DIALOGOS_GUION + '?dsUniEco=' +dsAseguradoras.getValue()  + '&dsElemento=' + dsClientesCorpo.getRawValue() + '&dsGuion=' + formWindowEdit.findById("guionId").getValue() + '&dsProceso=' + dsProceso.getRawValue() + '&dsTipGuion=' + dsTipGuion.getRawValue() + '&dsRamo=' + dsProductos.getRawValue();
                    showExportDialog( url );
                }  */ 
              },
              {
              text:getLabelFromMap('gridNtfcnBottonRegresar',helpMap,'Regresar'),
              tooltip: getToolTipFromMap('gridNtfcnBottonRegresar',helpMap,'Volver a la Pantalla Anterior'),
              handler: function() {
                        _window.close();
                }
              }
              ],
        width:600,
        frame:true,
        height:275,
        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		stripeRows: true,
		collapsible: true,
		bbar: new Ext.PagingToolbar({
				pageSize:20,
				store: storeGrilla,
				displayInfo: true,
                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
				emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
		    })          
    });
//gridDialogos.render()
}
createGrid();

//CONFIGURACION PARA EL FORMULARIO DE EDICIÓN *****************************
var formWindowEdit = new Ext.FormPanel({
		id:'formWindowAgrId',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('formWindowAgrId',helpMap,'Configurar Gui&oacute;n')+'</span>',
        //title: '<span style="color:black;font-size:14px;">Configurar Guion</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,
        //url: _ACTION_OBTENER_TIPOS_ENDOSOS,
        //reader:_reader,
        width: 750,
        height:450,
        items: [
       		{
      			layout: 'column',
      			items:[
      					{//1ra columna
       					columnWidth: .5,
       					layout: 'form',
       					items: [
							comboAseguradora,
  							comboGrupo,
  							comboProceso,
                            //cdGuion,
                           {
       						 dataIndex: 'cdGuionId',
        					 hidden :true
      						},
  							guion
						]
      				    },
      				    {//2da columna
       				   	columnWidth: .5,
       					layout: 'form',
       					items: [
           					comboTipoGuion,
  							comboProducto,
  							comboStatus,
  							indInicial
						]
      				    }
      			]        			
      		   }//, 
      		   	//gridDialogos
      		   ]
		});


 var _window = new Ext.Window({
   	width: 720,
   	height: 220,
   	minWidth: 300,
   	minHeight: 100,
   	layout: 'fit',
   	plain:true,
   	bodyStyle:'padding:5px;',
   	buttonAlign:'center',
   	items: formWindowEdit,
   	modal: 'true',
   	buttons:[
             {
              text:getLabelFromMap('gridNtfcnBottonGuardar',helpMap,'Guardar'),
              tooltip: getToolTipFromMap('gridNtfcnBottonGuardar',helpMap,'Guardar Informaci&oacute;n'),
              handler:function(){
                       if(formWindowEdit.form.isValid()){
                          guardarDatosGuionAndDialogo(formWindowEdit)
                          }else{ 
                               Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                              }
                   }
              },
              {
              text:getLabelFromMap('gridNtfcnBottonRegresar',helpMap,'Regresar'),
              tooltip: getToolTipFromMap('gridNtfcnBottonRegresar',helpMap,'Volver a la Pantalla Anterior'),
              handler: function() {
                        _window.close();
                }
              }
              ]
});
_window.show();

//formWindowEdit.form.load();

dsClientesCorpo.load();
dsEstados.load();
dsAseguradoras.load();  
//dsProductos.load();
        
dsTipoGuion.load();

 function reloadGridDialogos(){
  var _storeDom = gridDialogos.store;
     var o = {start: 0};
     _storeDom.baseParams = _storeDom.baseParams || {};
     _storeDom.baseParams['cdUniEco'] = formWindowEdit.findById("cdUniEcoId").getValue();
     _storeDom.baseParams['cdRamo'] = formWindowEdit.findById("cdRamoId").getValue();
     _storeDom.baseParams['cdElemento'] = formWindowEdit.findById("cdSeccionId").getValue();
     _storeDom.baseParams['cdProceso'] = formWindowEdit.findById("cdProcesoId").getValue();
     _storeDom.baseParams['cdGuion'] = formWindowEdit.findById("cdGuionId").getValue();
     _storeDom.reload(
               {
                   params:{start:0,limit:itemsPerPage},
                   callback : function(r, options, success) {
                       if (!success) {
                          _storeDom.removeAll();
                       }
                   }
               }
             );
 }

function borrarDialogo () {
    var recs = gridDialogos.getSelectionModel().getSelections();
    if (recs.length > 0) {
        var conn = new Ext.data.Connection();
        conn.request ({
              url: _ACTION_BORRAR_DIALOGO,
              params: {
                                "cdUniEco": formWindowEdit.findById("cdUniEcoId").getValue(),
                                "cdRamo": formWindowEdit.findById("cdRamoId").getValue(),
                                "cdElemento": formWindowEdit.findById("cdElementoId").getValue(),
                                "cdProceso": formWindowEdit.findById("cdProcesoId").getValue(),
                                "cdGuion": formWindowEdit.findById("cdGuionId").getValue(),
                                "cdDialogo":recs[0].get("cdDialogo")
                    },
              method: 'POST',
              callback: function(options, success, response) {
                     if (Ext.util.JSON.decode(response.responseText).success == false) {
                      Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), 'Problemas al eliminar: ' + Ext.util.JSON.decode(response.responseText).errorMessages[0]);
                     } else {
                      Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), 'Los datos se eliminaron con &eacute;xito', function() {reloadGridDialogos();});
                     }
              }
        });
      }else{
       Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
      }
}

};
