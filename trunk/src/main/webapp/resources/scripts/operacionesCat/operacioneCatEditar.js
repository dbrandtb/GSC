function editar (record){

var guion_read = new Ext.data.JsonReader({
					root: 'MOperacionCatList',
					totalProperty: 'totalCount',
					successProperty: '@success'
					}, 
					[
						{name: 'codAseguradora', type: 'string', mapping: 'cdUniEco'},
						{name: 'desAseguradora', type: 'string', mapping: 'dsUniEco'},
                        {name: 'codTipoGuion', type: 'string', mapping: 'cdTipGuion'},
                        {name: 'desTipoGuion', type: 'string', mapping: 'dsTipGuion'},
						{name: 'codGrupo', type: 'string', mapping: 'cdElemento'},
                        {name: 'desGrupo', type: 'string', mapping: 'dsElemen'},
                        {name: 'codProducto', type: 'string', mapping: 'cdRamo'},
						{name: 'desProducto', type: 'string', mapping: 'dsRamo'},
                        {name: 'codProceso', type: 'string', mapping: 'cdProceso'},
                        {name: 'desProceso', type: 'string', mapping: 'dsProceso'},
                        {name: 'codStatus', type: 'string', mapping: 'dsSeccion'},
                        {name: 'desStatus', type: 'string', mapping: 'status'},
						{name: 'codGuion', type: 'string', mapping: 'cdGuion'},
                        {name: 'desGuion', type: 'string', mapping: 'dsGuion'},
                        {name: 'indInicial', type: 'string', mapping: 'indInicial'}
					]
	);


var codAseguradora = new Ext.form.Hidden( {
    disabled:false,
    id:'cdUniEcoId',
    name:'codAseguradora'

});
var desAseguradora = new Ext.form.TextField({
	
        fieldLabel: getLabelFromMap('desAseguradoraId',helpMap,'Aseguradora'),
        tooltip:getToolTipFromMap('desAseguradoraId',helpMap,'Nombre de la Aseguradora'), 
        hasHelpIcon:getHelpIconFromMap('desAseguradoraId',helpMap),								 
        Ayuda: getHelpTextFromMap('desAseguradoraId',helpMap),
        id: 'desAseguradoraId', 
        name: 'desAseguradora',
        readOnly: true,
        width:'120'
    });


var codTipoGuion = new Ext.form.Hidden( {
    disabled:false,
    id:'cdTipoGuionId',
    name:'codTipoGuion'
});
var desTipoGuion = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('desTipoGuionId',helpMap,'Tipo de Gui&oacute;n'),
        tooltip:getToolTipFromMap('desTipoGuionId',helpMap,'Tipo de Gui&oacute;n Utilizado'), 
        hasHelpIcon:getHelpIconFromMap('desTipoGuionId',helpMap),								 
        Ayuda: getHelpTextFromMap('desTipoGuionId',helpMap),
        id: 'desTipoGuionId', 
        name: 'desTipoGuion',
        readOnly: true,
        width:'120'
        
    });

var codGrupo = new Ext.form.Hidden( {
    disabled:false,
    id:'cdElementoId',
    name:'codGrupo'

});
var desGrupo = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('desGrupoId',helpMap,'Grupo'),
        tooltip:getToolTipFromMap('desGrupoId',helpMap,'Grupo'), 
        hasHelpIcon:getHelpIconFromMap('desGrupoId',helpMap),								 
        Ayuda: getHelpTextFromMap('desGrupoId',helpMap),
        id: 'desGrupoId', 
        name: 'desGrupo',
        readOnly: true,
        width:'120'
    });

var codProducto = new Ext.form.Hidden( {
    disabled:false,
    id:'cdRamoId',
    name:'codProducto'
});

var desProducto = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('desProductoId',helpMap,'Producto'),
        tooltip:getToolTipFromMap('desProductoId',helpMap,'Producto ingresado'), 
        hasHelpIcon:getHelpIconFromMap('desProductoId',helpMap),								 
        Ayuda: getHelpTextFromMap('desProductoId',helpMap),
        id: 'desProductoId', 
        name: 'desProducto',
        readOnly: true,
        width:'120'
    });

var codProceso = new Ext.form.Hidden( {
    disabled:false,
    id:'cdProcesoId',
    name:'codProceso'
});    
var desProceso = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('desProcesoId',helpMap,'Proceso'),
        tooltip:getToolTipFromMap('desProcesoId',helpMap,'Proceso '),
        hasHelpIcon:getHelpIconFromMap('desProcesoId',helpMap),								 
        Ayuda: getHelpTextFromMap('desProcesoId',helpMap),
        id: 'desProcesoId', 
        name: 'desProceso',
        readOnly: true,
        width:'120'
    });

var codStatus = new Ext.form.Hidden( {
    disabled:false,
    id:'statusId',
    name:'codStatus'

});
var desStatus = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('desStatusId',helpMap,'Estado'),
        tooltip:getToolTipFromMap('desStatusId',helpMap,'Status del Gui&oacute;n'), 
        hasHelpIcon:getHelpIconFromMap('desStatusId',helpMap),								 
        Ayuda: getHelpTextFromMap('desStatusId',helpMap),
        id: 'desStatusId', 
        name: 'desStatus',
        readOnly: true,
        width:'120'
    });

var codGuion = new Ext.form.Hidden( {
    disabled:false,
    id:'cdGuionId',
    name:'codGuion'
});
var desGuion = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('desGuionId',helpMap,'Gui&oacute;n'),
        tooltip:getToolTipFromMap('desGuionId',helpMap,'Descripcion del Gui&oacute;n'), 
        hasHelpIcon:getHelpIconFromMap('desGuionId',helpMap),								 
        Ayuda: getHelpTextFromMap('desGuionId',helpMap),
        id: 'desGuionId', 
        name: 'desGuion',
        allowBlank: true,
        width:'120'
    });

var indInicial = new Ext.form.Checkbox({
    	fieldLabel: getLabelFromMap('indInicialId', helpMap,'Srcipt Inicial'), 
    	tooltip: getToolTipFromMap('indInicialId', helpMap,'Srcipt Inicial'),  		
        hasHelpIcon:getHelpIconFromMap('indInicialId',helpMap),								 
        Ayuda: getHelpTextFromMap('indInicialId',helpMap),
        width:'128',
        id: 'indInicialId', 
        name: 'indInicial',
        allowBlank: true
});


//READERS Y STORE DE GRILLA***************************************************************
var recordDialogo = new Ext.data.Record.create([
      {name : 'cdDialogo', mapping : 'cdDialogo', type : 'string'},
      {name : 'dsDialogo', mapping : 'dsDialogo', type : 'string'},
      {name : 'cdSecuencia', mapping : 'cdSecuencia', type : 'string'},
      {name : 'otTapVal', mapping : 'otTapVal', type : 'string'},
      {name: 'dsTabla',mapping :'dsLabel',type : 'string'}
    ]);
    
    
var readerDialogo = new Ext.data.JsonReader({
          root : 'MOperacionCatLista',
          totalProperty: 'totalCount',
          successProperty : '@success'
      },
      recordDialogo 
);
     
var storeGrillas = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_BUSCAR_DIALOGOS_GUION
		
        }),
reader: readerDialogo
});



 function addGridDialogoNewRecord(){
  var new_record = new recordDialogo({
               cdDialogo: '',
		       dsDialogo:'',
		       cdSecuencia:'',
		       otTapVal:''/*,
		       dsTabla: ''*/
      });
  gridDialogo.stopEditing();
  gridDialogo.store.insert(0, new_record);
  gridDialogo.startEditing(0, 0);
  gridDialogo.getSelectionModel().selectRow(0);
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
        cdElemento: formWindowEdit.findById("cdElementoId").getValue(),
        cdProceso: formWindowEdit.findById("cdProcesoId").getValue(),
        cdGuion: formWindowEdit.findById("cdGuionId").getValue(),
        dsGuion: formWindowEdit.findById("desGuionId").getValue(),
        cdTipGuion: formWindowEdit.findById("cdTipoGuionId").getValue(),
        indInicial: (formWindowEdit.findById("indInicialId").getValue() == true )? "1" : "0",
        //indInical: 1,
       status: (formWindowEdit.findById("desStatusId").getValue()== "Activo")? "1" : "0"
        //status: 1
        },
     callback: function (options, success, response) {
        if (Ext.util.JSON.decode(response.responseText).success == false) {
           
            Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400068', helpMap,'No se pudo guardar'));
        }else {  guardarDatosDialogo();
               }
    }                
 })
}

 function guardarDatosDialogo () {
  var _params = "";
  var recs = gridDialogo.store.getModifiedRecords();
  
  gridDialogo.stopEditing();
 for (var i=0; i<recs.length; i++) {
			_params += "csoGrillaListDialogo["+i+"].cdUniEco ="+formWindowEdit.findById("cdUniEcoId").getValue()+"&";
			_params += "csoGrillaListDialogo["+i+"].cdRamo=" + formWindowEdit.findById("cdRamoId").getValue()+"&";
			_params += "csoGrillaListDialogo["+i+"].cdElemento=" +formWindowEdit.findById('cdElementoId').getValue()+"&";
			_params += "csoGrillaListDialogo["+i+"].cdProceso=" + formWindowEdit.findById('cdProcesoId').getValue()+"&";
			_params += "csoGrillaListDialogo["+i+"].cdGuion=" + formWindowEdit.findById("cdGuionId").getValue()+"&";
			_params += "csoGrillaListDialogo["+i+"].cdDialogo=" + recs[i].get('cdDialogo')+"&";
			_params += "csoGrillaListDialogo["+i+"].dsDialogo=" + recs[i].get('dsDialogo')+"&";
			_params += "csoGrillaListDialogo["+i+"].cdSecuencia=" + recs[i].get('cdSecuencia')+"&";
			_params += "csoGrillaListDialogo["+i+"].otTapVal=" + recs[i].get('otTapVal')+"&";
		}
  //alert(_params);
  
  
  /*for (var i=0; i<recs.length; i++) {
  	_params +=  "csoGrillaListDialogo[" + i + "].cdUniEco =" +  formWindowEdit.findById("cdUniEcoId").getValue() + "&" +
      "&csoGrillaListDialogo[" + i + "].cdRamo=" + formWindowEdit.findById("cdRamoId").getValue() + "&" +
      "&csoGrillaListDialogo[" + i + "].cdElemento=" +formWindowEdit.findById('cdElementoId').getValue() + "&" +
      "&csoGrillaListDialogo[" + i + "].cdProceso=" + formWindowEdit.findById('cdProcesoId').getValue() + "&" +
      "&csoGrillaListDialogo[" + i + "].cdGuion=" + formWindowEdit.findById("cdGuionId").getValue() + "&" +
      "&csoGrillaListDialogo[" + i + "].cdDialogo=" + recs[i].get('cdDialogo') + "&" +
      "&csoGrillaListDialogo[" + i + "].dsDialogo=" + recs[i].get('dsDialogo') + "&" +
      "&csoGrillaListDialogo[" + i + "].cdSecuencia=" + recs[i].get('cdSecuencia') + "&" +
      "&csoGrillaListDialogo[" + i + "].otTapVal=" + recs[i].get('otTapVal');
  alert(_params);
  }*/
  
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
          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'),  getLabelFromMap('400068', helpMap,'Los datos se guardaron con Exito'), function(){reloadGridDialogos();});
          gridDialogo.store.commitChanges();
    
          //window.close();
         }
       }
   });
  }
    else
        { Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'),  getLabelFromMap('400068', helpMap,'Los datos se guardaron con Exito'));
        }
  
 }    
 /****************Funcion para obtener la celda invalida***************/
 function validarGrilla(grilla){
 	
 }
 
 var dsListas = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: _ACTION_OBTENER_TABLA 
            }),
        reader: new Ext.data.JsonReader({
        root: 'comboTabla',
        id: 'cd'
        },[
       {name: 'cd', mapping:'dsLabel', type: 'string'},
       {name: 'ds', mapping:'cdTabla', type: 'string'}
    ])
});			

 
 
 
 var comboListaValores = new Ext.form.ComboBox({
		tpl: '<tpl for="."><div ext:qtip="{cd}.{ds}" class="x-combo-list-item">{ds}</div></tpl>',
		store: dsListas,
		anchor:'90%',
		displayField:'ds',
		valueField: 'cd',
		hiddenName: 'lista',
		//hiddenName: 'cdTabla',
		//hiddenName: 'ds',
		typeAhead: true,
		triggerAction: 'all',
		lazyRender: true,
		emptyText:'Selecione Valores...',
		selectOnFocus:true,
		mode: 'local',
		name: 'lista', 
		id:'listaId', 
		forceSelection: true,
		labelSeparator:'',
		
		/*listeners: {
	     blur: function () {
			if (this.getRawValue() == "") {
				this.setValue();
				var rec1 = gridDialogo.getSelectionModel().getSelected();
                rec1.set("dsTabla", "");
      
				}
			  }
		   },*/
		 
		onSelect: function (record){
    	this.setValue(record.get("ds"));
        this.collapse();
     
        var rec = gridDialogo.getSelectionModel().getSelected();
         rec.set("dsTabla", record.get("cd"));
      
         
      
    }
});
	
/****************FIN -Funcion para obtener la celda invalida**********/
// Definicion de las columnas de la grilla
var cm = new Ext.grid.ColumnModel([
        {
        dataIndex: 'cdDialogo',
        hidden :true
        },
        {
        header: getLabelFromMap('cmDsDialogoNtfcn',helpMap,'Di&aacute;logo'),
        tooltip: getToolTipFromMap('cmDsDialogoNtfcn',helpMap,'Columna Di&aacute;logo'),
        hasHelpIcon:getHelpIconFromMap('cmDsDialogoNtfcn',helpMap),								 
        Ayuda: getHelpTextFromMap('cmDsDialogoNtfcn',helpMap,'h'),
        
        dataIndex: 'dsDialogo',
        width: 300,
        sortable: true,
        editor: new Ext.form.TextField({
               allowBlank: false
               }),
        // funcion que permite colocarle a todas las celdas el rojo de requerido y el icono de requerido
        renderer:function extGrid_renderer(val, cell, record, row, col, store) {
						if (record != undefined) {
						// Make sure we have a value
						if (val == '') {
						// No value so highlight this cell as in an error state
							cell.css += 'x-form-invalid';
							cell.attr = 'ext:qtip="Este campo es requerido"; ext:qclass="x-form-invalid-tip"';
							//cell.attr = 'ext:qclass="x-form-invalid-tip"';
					} else {
						// Value is valid
						cell.css = '';
						cell.attr = 'ext:qtip=""';
					}
					}
	// Return the value so that it is displayed within the grid
				return val;
			}
        },
        {
        dataIndex: 'cdSecuencia',
        hidden :true
        },
        {
        header: getLabelFromMap('cmotTapValNtfcn',helpMap,'Lista de Valores'),
        tooltip: getToolTipFromMap('cmotTapValNtfcn',helpMap,'Columna Lista de Valores'),
        hasHelpIcon:getHelpIconFromMap('cmotTapValNtfcn',helpMap),								 
        Ayuda: getHelpTextFromMap('cmotTapValNtfcn',helpMap,'h'),
        
        dataIndex: 'otTapVal',
        width: 100,
        sortable: true,
        align: 'center',
        
        editor: comboListaValores,
        renderer: renderComboEditor(comboListaValores)
        
   /*   /*editor: new Ext.form.TextField({
               allowBlank: false
               }),
        // funcion que permite colocarle a todas las celdas el rojo de requerido y el icono de requerido
        renderer:function extGrid_renderer(val, cell, record, row, col, store) {
						if (record != undefined) {
						// Make sure we have a value
						if (val == '') {
						// No value so highlight this cell as in an error state
							cell.css += 'x-form-invalid';//'x-grid-red';
							cell.attr = 'ext:qtip="Este campo es requerido"; ext:qclass="x-form-invalid-tip"';
							//cell.attr = 'ext:qclass="x-form-invalid-tip"';
					} else {
						// Value is valid
						cell.css = '';
						cell.attr = 'ext:qtip=""';
					}
					}
	      // Return the value so that it is displayed within the grid
			return val;
			}/**/
        },
          {
        header:getLabelFromMap('dsTabla',helpMap,'Valor Tabla'),
        tooltip:getToolTipFromMap('dsTabla',helpMap,''),
        dataIndex: 'dsTabla',
        sortable: true,
        width: 170/*,
        editor: new Ext.form.TextField({
        	   id:'dsTablaId',
        	   name: 'dsTablaN',
      	       maxValue:99,
               allowBlank: true,
               disabled:false
               })*/
        }
]);
var colModel;
var gridDialogo;
var cols;
var rows;
var cell;
var field;
var record;

function createGrid(){
   gridDialogo= new Ext.grid.EditorGridPanel({
   		id: 'gridDialogosId',
        //el:'gridGuiones',
        //store: dsDialogos,//crearGridDialogosStore(),
        store:storeGrillas,
        title: '<span style="color:black;font-size:14px;">Configurar Di&aacute;logo</span>',
        //reader:readerDialogo,
        border:true,
        cm: cm,
        clicksToEdit:1,
        buttonAlign:'center',
        successProperty: 'success',
        plugins:new Ext.ux.plugins.GridValidator(),//plugins para validar la celda en el jsp se debe agregar: <script src="${ctx}/resources/scripts/ValidacionGrilla/Ext.ux.plugins.GridValidator.js" type="text/javascript"></script> 
        buttons:[
              {
              text:getLabelFromMap('gridNtfcnBotAgregarEditar',helpMap,'Agregar'),
              tooltip: getToolTipFromMap('gridNtfcnBotAgregarEditar',helpMap,'Crea un Nuevo Di&aacute;logo al Gui&oacute;n'),
              handler:function(){addGridDialogoNewRecord();}
              },{
              text:getLabelFromMap('gridNtfcnBottonGuardar',helpMap,'Guardar'),
              tooltip: getToolTipFromMap('gridNtfcnBottonGuardar',helpMap,'Guardar Informaci&oacute;n'),
              handler:function(){
             
              	cols = gridDialogo.colModel.getColumnCount(); // determino cantidad de columnas de la grilla
                rows = gridDialogo.store.getCount();		  // determino cantidad de filas de la grilla
              	
              	var bandValid = 0; // bandera para saber si ya todas las celdas de mi grilla son validas
              	
              	//recorro toda mi grilla preguntando si las celdas son validas
              	for (var i = 0; i < rows; i++) {
              		for (var j = 0; j < cols; j++) {
              		 if(!gridDialogo.isCellValid(j,i)){
              		   		bandValid =+ 1;
              		   }
              		}
              		
              	}
              	//aseguro de que todas mis celdas son validas
              	if (bandValid != 0){
              		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
              	}else{
              		guardarDatosGuionAndDialogo(formWindowEdit)
              	     }
              }
              },
              {
              text:getLabelFromMap('gridNtfcnBottonBorrar',helpMap,'Eliminar'),
              tooltip: getToolTipFromMap('gridNtfcnBottonBorrar',helpMap,'Eliminar un Dialogo para el Gui&oacute;n'),
              handler:function(){
					if (gridDialogo.getSelectionModel().getSelections().length > 0) {
                       Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'), 
                     function(btn) {
                       if (btn == "yes"){
                   			if (gridDialogo.getSelectionModel().getSelected().get("cdDialogo")==""){
                   				gridDialogo.store.remove(gridDialogo.getSelectionModel().getSelected());
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
              tooltip: getToolTipFromMap('gridNtfcnBottonExportar',helpMap,'Exportar datos en diversos Formatos'),
                handler:function(){
                    var url = _ACTION_EXPORTAR_DIALOGOS_GUION + '?cdUniEco=' + codAseguradora.getValue()  + '&cdElemento=' + codGrupo.getValue() + '&cdGuion=' + codGuion.getValue() + '&cdProceso=' + codProceso.getValue() + '&cdTipGuion=' + codTipoGuion.getValue() + '&cdRamo=' + codProducto.getValue();
                    showExportDialog( url );
                }   
              },
              {
              text:getLabelFromMap('gridNtfcnBottonRegresar',helpMap,'Regresar'),
              tooltip: getToolTipFromMap('gridNtfcnBottonRegresar',helpMap,'Volver a la Pantalla Anterior'),
             handler: function() {
			         reloadGrid();
			         window.close();
		          }
              }
              ],
        width:600,
        frame:true,
        height:275,
        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		//stripeRows: true,
		collapsible: true,
		bbar: new Ext.PagingToolbar({
				pageSize:1,
				store: storeGrillas,
				displayInfo: true,
                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
				emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
		    })          
    });
//gridDialogos.render()
}
createGrid();
//console.log(gridDialogo);
//dsDialogos.load();
//CONFIGURACION PARA EL FORMULARIO DE EDICIÓN *****************************
var formWindowEdit = new Ext.FormPanel({
		id:'formWindowEditId',
       // title: '<span style="color:black;font-size:14px;">Configurar Gui&oacute;n</span>',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('formWindowEditId', helpMap,'Configurar Gui&oacute;n')+'</span>',        
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,
        url: _ACTION_GET_GUION,
        baseParams : {    cdUniEco: record.get("cdUniEco"),
                          cdElemento: record.get("cdElemento"),
                          cdRamo: record.get("cdRamo"),
                          cdProceso: record.get("cdProceso"),
                          cdGuion: record.get("cdGuion")
                         },
        reader: guion_read,
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
       						codAseguradora,
							desAseguradora,
							codGrupo,
  							desGrupo,
  							codProceso,
  							desProceso,
                            codGuion,
  							desGuion
						]
      				    },
      				    {//2da columna
       				   	columnWidth: .5,
       					layout: 'form',
       					items: [
           					codTipoGuion,
  							desTipoGuion,
  							codProducto,
  							desProducto,
  							codStatus,
  							desStatus,
  							indInicial
						]
      				    }
      			]        			
      		   }, 
      		   	gridDialogo
      		   ]
		});


 var window = new Ext.Window({
   	width: 650,
   	height:450,
   	minWidth: 300,
   	minHeight: 100,
   	layout: 'fit',
   	plain:true,
   	modal:true,
   	bodyStyle:'padding:5px;',
   	buttonAlign:'center',
   	items: formWindowEdit
});
window.show();

                                                      
formWindowEdit.form.load();
dsListas.load();

reloadGridDialogo(record.get('cdGuion'));

//console.log(gridDialogo);
 function reloadGridDialogos(){
  var _storeDom = gridDialogo.store;
     var o = {start: 0};
     
     _storeDom.baseParams = _storeDom.baseParams || {};
     /*_storeDom.baseParams['cdUniEco'] = formWindowEdit.findById("cdUniEcoId").getValue();
     _storeDom.baseParams['cdRamo'] = formWindowEdit.findById("cdRamoId").getValue();
     _storeDom.baseParams['cdElemento'] = formWindowEdit.findById("cdElementoId").getValue();
     _storeDom.baseParams['cdProceso'] = formWindowEdit.findById("cdProcesoId").getValue();*/
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
    var recs = gridDialogo.getSelectionModel().getSelections();
    if (recs.length > 0) {
        var conn = new Ext.data.Connection();
        conn.request ({
              url: _ACTION_BORRAR_DIALOGO,
              params: {
                                //"cdUniEco": record.get("cdUniEco"),
                                //"cdRamo": record.get("cdRamo"),
                                //"cdElemento": record.get("cdElemento"),
                                //"cdProceso": record.get("cdProceso"),
                                "cdGuion": record.get("cdGuion"),
                                "cdDialogo":recs[0].get("cdDialogo")
                    },
              method: 'POST',
              callback: function(options, success, response) {
                     if (Ext.util.JSON.decode(response.responseText).success == false) {
                      Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), 'Problemas al eliminar: ' + Ext.util.JSON.decode(response.responseText).errorMessages[0]);
                     } else {
                      Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400081', helpMap,'Los datos se eliminaron con éxito'), function() {reloadGridDialogos();});
                      
                     }
              }
        });
      }else{
       Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
      }
}

};
