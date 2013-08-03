Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";
var agregarFila = false;

var codigoGrupo = new Ext.form.Hidden({
    	disabled:false,
        name:'codigoGrupo',
        value: CODIGO_GRUPO
    });

var formReader = new Ext.data.JsonReader(
        {
            root:'MEstructuraList',
            totalProperty: 'totalCount',
			successProperty : '@success'

        }, 
        [ 
        {name: 'cdGrupo',  mapping:'cdGrupo',  type: 'string'},
        {name: 'cdElementoId',  mapping:'cdElemento',  type: 'string'},
        {name: 'desCliente',  mapping:'dsElemen',  type: 'string'},
        {name: 'desAseguradora',  mapping:'dsUnieco',  type: 'string'},
        {name: 'desRamo',  mapping:'dsTipram',  type: 'string'},
        {name: 'desProducto',  mapping:'dsRamo',  type: 'string'},
        {name: 'desAgrupacion',  mapping:'dsAgrupa',  type: 'string'},
        {name: 'codTipo',  mapping:'cdTipo',  type: 'string'}
     	]
);

//Combo de Eleccion Grupo de Persona depende de cliente
var desGrupoPersonas = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_GRUPO_PERSONAS_CLIENTES}),
        reader: new Ext.data.JsonReader({
        root: 'grupoPersonasCliente',
        id: 'codigo'
        },[
       {name: 'codigo', type: 'string', mapping:'codigo'},
       {name: 'descripcion', type: 'string', mapping:'descripcion'}
    ])
});
       
var incisosForm = new Ext.FormPanel({
        el:'formBusqueda',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('formBusquedaGrpPerId',helpMap,'Configurar Agrupaci&oacute;n por Grupo de Personas')+'</span>',        
        iconCls:'logo',
        url: _ACTION_GET_AGRUPACION_GRUPO_PERSONAS,
        frame: true,
        baseParams: {cdGrupo: codigoGrupo.getValue()},
        reader: formReader,
        bodyStyle : 'padding:5px 5px 0',
        width: 600,
        height:150,
        autoHeight: true,
        waitMsgTarget : true,
        layout: 'column',
        layoutConfig: {columns: 2, align: 'left'},
		defaults: {labelWidth: 80}, 
		bodyStyle:'background: white',             
        items: [        
             codigoGrupo,
             {
             xtype: 'hidden', 
             id: 'cdElementoId', 
             name: 'cdElementoId'
             },
             {
             xtype: 'hidden', 
             id: 'codTipoId', 
             name: 'codTipo'
             },
             {
             layout:'form',
             columnWidth: .50,
             items:[
                      {
                      xtype: 'textfield',
                      fieldLabel: getLabelFromMap('desClienteId',helpMap,'Cliente'),
                      tooltip: getToolTipFromMap('desClienteId',helpMap,'Cliente'),
                      hasHelpIcon:getHelpIconFromMap('desClienteId',helpMap),
					  Ayuda:getHelpTextFromMap('desClienteId',helpMap),
                      name: 'desCliente',
                      width: '180',
                      id: 'desClienteId',
                      allowBlank: false,       		
                      readOnly:true                         
                      }
                   ]
             },
             {
              layout: 'form',
              columnWidth: .50, 
              items: [
                       {
                      xtype: 'textfield',
                      fieldLabel: getLabelFromMap('desAseguradoraId',helpMap,'Aseguradora'),
                      tooltip: getToolTipFromMap('desAseguradoraId',helpMap,'Aseguradora'),
                      hasHelpIcon:getHelpIconFromMap('desAseguradoraId',helpMap),
					  Ayuda:getHelpTextFromMap('desAseguradoraId',helpMap),
                      name: 'desAseguradora', 
                      width: '180',
                      id: 'desAseguradoraId',
                      allowBlank: false,       		
                      readOnly:true                            
                      }
                    ]
   			},
   			{
              layout: 'form',
              columnWidth: .50, 
              items: [
                       {
                       xtype: 'textfield',
                       fieldLabel: getLabelFromMap('desRamoId',helpMap,'Tipo de Ramo'),
                       tooltip: getToolTipFromMap('desRamoId',helpMap,'Tipo de Ramo'),
                       hasHelpIcon:getHelpIconFromMap('desRamoId',helpMap),
					   Ayuda:getHelpTextFromMap('desRamoId',helpMap),
                       name: 'desRamo',
                       width: '180',
                       id: 'desRamoId',
                       allowBlank: false,       		
                       readOnly:true      
                       }
                     ]
   			},
   			{
              layout: 'form',
              columnWidth: .50, 
              items: [
                      {
                       xtype: 'textfield',
                       fieldLabel: getLabelFromMap('desProductoId',helpMap,'Producto'),
                       tooltip: getToolTipFromMap('desProductoId',helpMap,'Producto'),
                       hasHelpIcon:getHelpIconFromMap('desProductoId',helpMap),
					   Ayuda:getHelpTextFromMap('desProductoId',helpMap),
                       name: 'desProducto',
                       width: '180',
                       id: 'desProductoId',
                       allowBlank: false,       		
                       readOnly:true      
                      }                     
                     ]
   			},
   			{
              layout: 'form',
              columnWidth: .50, 
              items: [
                      {
                      xtype: 'textfield',
                      fieldLabel: getLabelFromMap('desAgrupacionId',helpMap,'Tipo de Agrupacion'),
                      tooltip: getToolTipFromMap('desAgrupacionId',helpMap,'Tipo de Agrupacion'),
                      hasHelpIcon:getHelpIconFromMap('desAgrupacionId',helpMap),
					  Ayuda:getHelpTextFromMap('desAgrupacionId',helpMap),
                      name: 'desAgrupacion',
                      width: '180',
                      id: 'desAgrupacionId',
                      allowBlank: false,       		
                      readOnly:true      
                     }
                     ]
   			}
    	]
});   

//Combo de la grilla
var comboGrupoDePersonas = new Ext.form.ComboBox({
                    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	        		store: desGrupoPersonas,
                    forceSelection:true,
	        		anchor:'100%',	            	
	            	displayField:'descripcion',
	                valueField:'codigo',
	            	hiddenName:'cdElemento',
	            	typeAhead: true,
	            	triggerAction: 'all',
	            	mode: 'local',
                    selectOnFocus:true
			        //labelSeparator:''
      });

desGrupoPersonas.load();
// Definicion de las columnas de la grilla

var cm = new Ext.grid.ColumnModel([
        {
	        header: getLabelFromMap('agrGPerCmAgrNiv',helpMap,'Agrupar en Nivel'),
	        tooltip: getToolTipFromMap('agrGPerCmAgrNiv',helpMap,'Agrupar en Nivel'),
	        dataIndex: 'cdAgrupa',
		    sortable: true,
	        width: 120,
	        editor: new Ext.form.NumberField({
			           				allowBlank: true,
			           				maxLength:2,
			           				id: 'cdAgrupa'
			           		})
        },
        {
		     header: getLabelFromMap('agrGPerCmGrPer',helpMap,'Grupo de Personas'),
	         tooltip: getToolTipFromMap('agrGPerCmGrPer',helpMap,'Grupo de Personas'),
		     dataIndex: 'cdGrupoPer',
		     width: 133,
		     sortable: true,
		     editor: comboGrupoDePersonas,
		     renderer: function extGrid_renderer(val, cell, record, row, col, store) {
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
					//console.log(comboFormatos);
					// Return the value so that it is displayed within the grid
					var idx;
					for (var i=0; i < comboGrupoDePersonas.store.data.items.length; i++) {
						//console.log("entrando:" + i);
						var registro = comboGrupoDePersonas.store.getAt(i);
						//console.log(registro);
						if (registro) {
							if (registro.get(comboGrupoDePersonas.valueField) == val) {
								//alert("matching: " + i + "\nvalue: " + value);
								idx = i;
							}//else alert("registro: " + registro.get(combo.valueField) + "\nvalue: " + value);
						} 
					}
					var rec = comboGrupoDePersonas.store.getAt(idx);
					//console.log(comboGrupoDePersonas.store.getAt(idx));
					return (rec == null)?val:rec.get(comboGrupoDePersonas.displayField);
					
				}
		     //renderer:renderComboEditor(comboGrupoDePersonas)
		},
		{
         	header: getLabelFromMap('agrGPerCmPlzAsg',helpMap,'P&oacute;liza Aseguradora'),
         	tooltip: getToolTipFromMap('agrGPerCmPlzAsg',helpMap,'P&oacute;liza Aseguradora'),
         	dataIndex: 'nmPoliEx',
        	sortable: true,
         	width: 143
     	},
     	{
         	header: getLabelFromMap('agrGPerCmFchInc',helpMap,'Fecha Inicio'),
         	tooltip: getToolTipFromMap('agrGPerCmFchInc',helpMap,'Fecha Inicial'),
         	dataIndex: 'feInicio',
        	sortable: true,
         	width: 100,
         	format: 'd/m/Y'
     	},
     	{
         	header: getLabelFromMap('agrGPerCmFchFn',helpMap,'Fecha Fin'),
         	tooltip: getToolTipFromMap('agrGPerCmFchFn',helpMap,'Fecha Fin'),
         	dataIndex: 'feFin',
        	sortable: true,
         	width: 90,
         	format: 'd/m/Y'
     	},
        {
	        dataIndex: 'cdGrupo',
	        hidden :true
        },
        {
	        dataIndex: 'cdAgrGrupo',
	        hidden :true
        },
   		{
  			dataIndex: 'cdPolMtra',
   			hidden: true
   		}
]);

var lasFilas=new Ext.data.Record.create([
  {name: 'cdGrupo',  mapping:'cdGrupo',  type: 'string'},
  {name: 'cdAgrGrupo',  mapping:'cdAgrGrupo',  type: 'string'},
  {name: 'cdAgrupa',  mapping:'cdAgrupa',  type: 'string'},
  {name: 'cdGrupoPer',  mapping:'cdGrupoPer',  type: 'string'},
  {name: 'cdPolMtra', mapping: 'cdPolMtra', type: 'string'},
  {name: 'nmPoliEx', mapping : 'nmPoliEx', type : 'string'},
  {name: 'feInicio', mapping: 'feInicio', type: 'string'},
  {name: 'feFin', mapping: 'feFin', type: 'string'}                    
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
             url: _ACTION_BUSCAR_AGRUPACION_GRUPO_PERSONAS,
						//waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
						waitMsg: getLabelFromMap('400017',helpMap,'Espere por favor ...')
		                }),
             reader:jsonGrilla,					
             baseParams: { cdGrupo: codigoGrupo.getValue() }
             });
             return store;
}

function addGridNewRecord () {
		var new_record = new lasFilas({
							cdAgrupa: '',
							cdGrupoPer: '',
							cdGrupo: '',
							cdAgrGrupo: '',
							nmPoliEx: '',
							feInicio: '',
							feFin: ''
						});
		grid2.stopEditing();
		grid2.store.insert(0, new_record);
		grid2.startEditing(0, 0);
		grid2.getSelectionModel().selectRow(0);
	}
	
var colModel;
var cols;
var rows;
var cell;
var field;
var record;

var grid2;

function createGrid(){
       grid2= new Ext.grid.EditorGridPanel({
            el:'gridElementos',
            store:storeGrilla(),
            reader:jsonGrilla,
	        title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',            
            border:true,
            cm: cm,
            clicksToEdit:1,
            loadMask: {msg: getLabelFromMap('400058',helpMap,'Cargando datos ...'), disabled: false},
	        successProperty: 'success',
			plugins: [new Ext.ux.plugins.GridValidator()/*plugins para validar la celda en el jsp se debe agregar: <script src="${ctx}/resources/scripts/ValidacionGrilla/Ext.ux.plugins.GridValidator.js" type="text/javascript"></script>*/ ],
	        buttonAlign:'center',
            buttons:[
                  {
                  text:getLabelFromMap('agrGrPerBtnAdd',helpMap,'Agregar'),
         		  tooltip: getToolTipFromMap('agrGrPerBtnAdd',helpMap,'Agregar'),
                  handler:function(){
                  			//agregarFila = true;
                  			addGridNewRecord();
                  }		
                  },{
                  text:getLabelFromMap('agrGrPerBtnSave',helpMap,'Guardar'),
         		  tooltip: getToolTipFromMap('agrGrPerBtnSave',helpMap,'Guardar'),
                  handler:function(){
                  		cols = grid2.colModel.getColumnCount(); // determino cantidad de columnas de la grilla
                		rows = grid2.store.getCount();		  // determino cantidad de filas de la grilla
              			var bandValid = 0; // bandera para saber si ya todas las celdas de mi grilla son validas
              	
              			//recorro toda mi grilla preguntando si las celdas son validas
              			for (var i = 0; i < rows; i++) {
              				for (var j = 0; j < cols; j++) {
              		 			if(!grid2.isCellValid(j,i)){
              		   				bandValid =+ 1;
              		   			}
              				}
		              	}
 		              	//aseguro de que los combos son validas
		              	verifico = grid2.store.getModifiedRecords();
						for (var i=0; i<verifico.length; i++) {
							if ((verifico[i].get('cdGrupoPer'))==''){
								bandValid++;
							}
						}
              			if (bandValid != 0){
              				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
              			}else{
              				
              				//agregarFila = false; 
	    	 				agregar();
              			}        	
                  	/*
                  			agregarFila = false; 
                  			agregar();*/
                  	}
                  },                  
                   {
            		text: getLabelFromMap('agrGrPerBtnDel',helpMap,'Eliminar'),
        			tooltip: getToolTipFromMap('agrGrPerBtnDel',helpMap,'Eliminar'),
			        handler: function(){
	                	//alert(grilla.getSelectionModel().getSelected().get("cdAgrupa"));
						if (!grid2.getSelectionModel().getSelected()) {
							Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));	               
			   			}else {
				   			if (grid2.getSelectionModel().getSelected().get("cdAgrGrupo")==""){
				   				//alert(1);
				   				grid2.store.remove(grid2.getSelectionModel().getSelected());
				   			}else{
				   	    		   //alert(2); 
				   	    		   borrar(getSelectedRecord(grid2));
				   			}
			   			}
	                 }
			         
			       },
			       {
	            		text: getLabelFromMap('agrGrPerBtnAsgnrPlz',helpMap,'Asignar Poliza'),
	           			tooltip: getToolTipFromMap('agrGrPerBtnAsgnrPlz',helpMap,'Asignar Poliza'),
	            		handler: function(){
	          				if (!grid2.getSelectionModel().getSelected()) {
								Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));	               
		   					}else{
		   						if (grid2.getSelectionModel().getSelected().get("cdAgrGrupo")==""){
		   							//alert(1);
		   							Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'No existe el campo cdAgrRol'));
		   						}else{
		   	    		   			//var _descripcionProducto= Ext.urlEncode(Ext.getCmp('desRamoId').getValue());
		   	    		   			//var _descripcionProducto= encodeURIComponent(Ext.getCmp('desRamoId').getValue());
		   	    		   			var _descripcionNivel= Ext.getCmp('desClienteId').getValue();
		   	    		   			var _descripcionAseguradora= Ext.getCmp('desAseguradoraId').getValue();
		   	    		   			var _descripcionProducto= Ext.getCmp('desProductoId').getValue();
		   	    		   			//alert (_descripcionProducto);
		   	    		   			//return;
		   	    		   			window.location = _ACTION_IR_ASIGNAR_POLIZAS + '?descripcionNivel=' + _descripcionNivel + '&codigoConfiguracion=' + CODIGO_GRUPO +
	                							'&descripcionAseguradora=' + _descripcionAseguradora + '&descripcionProducto=' +_descripcionProducto +
	                							'&codigoAgrupacion='+grid2.getSelectionModel().getSelected().get("cdAgrGrupo")+'&codigoTipo='+Ext.getCmp('codTipoId').getValue();
		   						}
	          				}
	                			
	                  	}
		           },
                   {
	                  text:getLabelFromMap('agrGrPerBtnBack',helpMap,'Regresar'),
	         		  tooltip: getToolTipFromMap('agrGrPerBtnBack',helpMap,'Regresar'),
	                  handler:function(){window.location = _ACTION_IR_AGRUPACION_POLIZAS;}
                  }
                  ],
            width:600,
            frame:true,
            height:320,
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			bbar: new Ext.PagingToolbar({
					pageSize:itemsPerPage,
					store: store,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })          
        });
   grid2.render()
}

function reloadGrid(){
		var _params = {
        			cdGrupo: codigoGrupo.getValue()
		};
		reloadComponentStore(grid2, _params, cbkReload);
}
function cbkReload (_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'),  _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}/*else {
		if (agregarFila) addGridNewRecord();
	}*/
}
        
incisosForm.render();
createGrid();
incisosForm.form.load({
                       waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
                       waitMsg: getLabelFromMap('400028',helpMap,'Leyendo datos ...'),
                       success: function () {
                               	desGrupoPersonas.baseParams =  desGrupoPersonas.baseParams || {};
								desGrupoPersonas.baseParams['cdElemento'] = incisosForm.findById("cdElementoId").getValue();
								
								desGrupoPersonas.load({
										callback: function (r, options, success) {
										
										if (success){
										grid2.store.load({
										callback: function (r, options, success) {
													if (!success) {
													}
													if (CODIGO_POLMTRA!="")
													{
														//alert(CODIGO_AGRUPACION);
														count=grid2.store.getCount();
														for (var i=0; i< count; i++)
														{
															 records=grid2.store.data.items[i].data;													 
															 if (records.cdAgrGrupo==CODIGO_AGRUPACION)
															 {
															 	//alert(grid2.store.data.items[i].data.cdAgrGrupo);
															 	//console.log(grid2.store.data.items[i].data)
							 								 	actualizar(records);
															 	break;
															 }
															 //console.log(grilla.store.data.items[i].data.cdAgrRol);
													    	}
												     	}
													/*else
													{
													 alert(CODIGO_POLMTRA);
													}
													//console.log(grilla.store.data.items[0].data.cdAgrRol);*/
										       }
									      })
									      }
									      }
									      });
									
                       },
				       failure: function () {
				                            Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), "No se pudo obtener la configuracion");}                       
                       });


    function borrar(record) {
   			
				var conn = new Ext.data.Connection();
			
				Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
				{
           	       if (btn == "yes")
           	        {
           	        	var _params = {
			              cdGrupo: CODIGO_GRUPO ,
			              cdAgrGrupo: record.get('cdAgrGrupo')
			          	};
           	        	execConnection(_AGRUPACION_BORRAR_GRUPO, _params, cbkBorrar);

                	}
				})
			
	};
   	function cbkBorrar (_success, _message) {
   		if (!_success) {
   			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
   		}else {
   			Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function() {reloadGrid();});
   		}
   	}                     
                      
function agregar (){
    
    var _params = "";
    var _url="";
    /*if (agregarFila==true)
    {
		_url+= _ACTION_INSERTAR_AGRUPACION_GRUPO_PERSONAS;
	}
	else
	{*/
		_url+= _ACTION_GUARDAR_AGRUPACION_GRUPO_PERSONAS;
	//}
    grid2.stopEditing();
    var records = grid2.store.getModifiedRecords();
    for (var i=0; i<records.length; i++) {
			_params += "listaGrupoPersonaVO[" + i + "].cdGrupo=" +  codigoGrupo.getValue() + "&listaGrupoPersonaVO[" + i + "].cdAgrGrupo=" + records[i].get('cdAgrGrupo') + "&listaGrupoPersonaVO[" + i + "].cdAgrupa=" + records[i].get('cdAgrupa') + "&listaGrupoPersonaVO[" + i + "].cdGrupoPer=" + records[i].get('cdGrupoPer') + "&";
	}
	if (records.length > 0) {
    	execConnection(_url, _params, cbkAgregar);
    }/*else {
    	if (agregarFila) addGridNewRecord();
    }*/
   }

	function actualizar(records){
		var _params = "cdGrupo=" + CODIGO_GRUPO + "&cdAgrGrupo="+records.cdAgrGrupo+"&cdAgrupa="+records.cdAgrupa+"&cdGrupoPer="+records.cdGrupoPer+"&cdPolMtra="+CODIGO_POLMTRA;
		execConnection(_ACTION_ACTUALIZAR_DATOS_GRILLA, _params, cbkAgregar);
	}	

   function cbkAgregar (_success, _message) {
   	if (_success) {
   		grid2.store.commitChanges();
   		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function () {
   				reloadGrid();
   		});
   	}else {
   		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
   	}
   }

});

