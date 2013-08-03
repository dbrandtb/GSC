Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

var bd = Ext.get('formulario');
var fallo_encab = false;
var el_panel;


var estado;

var tab_actual = -1;

	Ext.Ajax.request ({
		url: _ACTION_OBTENER_SECCION,
		params: (CODIGO_CONFIGURACION != "")? {}:{codigoConfiguracion: '0'},
		method: 'GET',
		waitMsg : getLabelFromMap('400028',helpMap,'Leyendo datos ...'),
		activeTab: 0,
		success: function (result, request) {
			var jsonData = Ext.util.JSON.decode (result.responseText);

			el_panel = new Ext.form.FormPanel ({
									title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('67', helpMap,'Configurar Checklist de la Cuenta')+'</span>',
									//title: 'Configurar Checklist de la Cuenta',
									defaultType: 'tabpanel',
									defaults: {msgTarget: 'center'},
									width: 725,
									items: [{
												xtype: 'tabpanel',												
												items: jsonData.MCheckListXCuentaSeccion,
												listeners: {tabchange: function(tabs, item) {
																tab_actual = item.id;
																LoadFormEncabData(item.id);
															}
												}											
									}],
									renderTo: 'formulario'
							});
		   if(CODIGO_CONFIGURACION!="")el_panel.items.items[0].setActiveTab(0);
		}
	});

        //se define la forma en que se van a leer los datos que envie la action
        var _jsonFormReader = new Ext.data.JsonReader( {
            root : 'MEncabezadosCheckListVO',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'cdConfig',
            mapping : 'cdConfig',
            type : 'string'
        }, {
            name : 'id',
            type : 'string',
            mapping : 'cdLinOpe'
        }, {
            name : 'dsConfig',
            type : 'string',
            mapping : 'dsConfig'
        }, {
            name : 'dsLinOpe',
            type : 'string',
            mapping : 'dsLinOpe'
        }, {
            name : 'dsNombre',
            type : 'string',
            mapping : 'dsNombre'
        }, {
        	name: 'comboLineaOperacion',
        	type: 'string',
        	mapping: 'cdLinOpe'
        }, {
        	name: 'comboClientes',
        	type: 'string',
        	mapping: 'cdElemen'
        }]);

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
        ]),
        remoteSort: true
        });		  	  	

       var dsLineaOperacion = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_LINEA_OPERACION
            }),
            reader: new Ext.data.JsonReader({
            root: 'lineasOperacion',
            id: 'id'
            },[
            {name: 'id', type: 'string',mapping:'codigo'},
            {name: 'texto', type: 'string',mapping:'descripcionLarga'}
        ])
        });		  	  	


	var storeEncabezado = new Ext.data.Store({
								url: _ACTION_OBTENER_ENCABEZADOS,
								reader: _jsonFormReader								
						});
/*
alert(estado);
if ((CODIGO_CONFIGURACION=="")&&(estado===undefined))
{
	estado=false;

}else
{	
	estado=true;
}
*/
	var formEncabezado = new Ext.FormPanel({
		el:'formBusqueda',
        iconCls:'logo',
        autoHeight: true,
        bodyStyle:{background: 'white', padding: "0px 0px 0px 0px"},
        buttonAlign: "center",
        border: true,
        //labelAlign: 'left',
        frame:true,
        url:_ACTION_OBTENER_ENCABEZADOS,
        //autoWidth:true,
        width:725,
        //store: storeEncabezado,
        reader: _jsonFormReader,
        successProperty : 'success',
        layout: 'column',
        layoutConfig: {columns: 3},
        defaults: { labelAlign: 'right'},
        items: [
        			{layout: 'form',
        			 labelWidth: 40, 
        			
        			 //columnWidth: .4,
        			 items: [{xtype: 'hidden', name: 'codigoCliente', id: 'codigoCliente'}, 
        			 		 {xtype: 'hidden', name: 'codigoSeccion', id: 'codigoSeccion'},
        			 		 {xtype: 'hidden', name: 'cdPerson', id: 'cdPerson'},
        			 		 {xtype: 'hidden', name: 'cdConfig', id: 'cdConfig'},
        			 		 {xtype: 'combo',
        			 		  labelAlign: 'right', 
        			 		  tpl: '<tpl for="."><div ext:qtip="{cdElemento}.{cdPerson}.{dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',                               
	                          fieldLabel: getLabelFromMap('comboClientes',helpMap,'Cliente'),
   	                          tooltip: getToolTipFromMap('comboClientes',helpMap,'Cliente'), 
		                      hasHelpIcon:getHelpIconFromMap('comboClientes',helpMap),								 
		                      Ayuda: getHelpTextFromMap('comboClientes',helpMap),
				              store: dsClientesCorp, 
				              disabled: (CODIGO_CONFIGURACION == "")?false:true,
				              //disabled: estado,
				              displayField:'dsElemen', 
				              valueField:'cdElemento', 
				              hiddenName: 'codigoElemento', 
				              typeAhead: true,
				              mode: 'local', 
				              triggerAction: 'all', 
				              width: 160, 
				              emptyText:'Seleccionar Cliente',
				              selectOnFocus:true, 				              
				              id: 'comboClientes',
				              forceSelection:true,
				              onSelect: function (record) { 
				                            	this.setValue(record.get("cdElemento")); 
				                                formEncabezado.findById(('codigoCliente')).setValue(record.get("cdElemento"));
				                                formEncabezado.findById('cdPerson').setValue(record.get('cdPerson'));
				                                this.collapse();
				                            }
		                            }                     
		                    ]
        			},
        			{
        			layout: 'form',
        			width:20	
        			},
        			{
        				layout: 'form',
        				items: [
        						{xtype: 'textfield',
								 fieldLabel: getLabelFromMap('checklistCuentaDsConfig',helpMap,'Configuraci&oacute;n'),
								 tooltip:getLabelFromMap('checklistCuentaDsConfig',helpMap,'Configuraci&oacute;n del CheckList de la Cuenta'),
		                         hasHelpIcon:getHelpIconFromMap('checklistCuentaDsConfig',helpMap),								 
		                         Ayuda: getHelpTextFromMap('checklistCuentaDsConfig',helpMap),
        						 labelWidth: 30, 
        						 id: 'dsConfig', 
        						 name: 'dsConfig', 
        						 msgTarget: 'side', 
        						 allowBlanks: true, 
        						 width: 100},
        						{xtype: 'hidden', fieldLabel: 'Nombre', name: 'cdConfig', msgTarget: 'side'}
        					   ]
        			},
        			{layout :'form',
        			width:30
        				
        			},
        			{
        				layout: 'form',
        				items: [
        						{xtype: 'hidden', fieldLabel: 'L&iacute;nea Operac.', id: 'cdLinOpe', name: 'cdLinOpe', msgTarget: 'side'},
        						{
        						xtype: 'combo', 
        						labelWidth: 100, 
        						tpl: '<tpl for="."><div ext:qtip="{id}.{cdPerson}.{texto}" class="x-combo-list-item">{texto}</div></tpl>',
                                id:'checklistCuentaComboDsLineaOperacion',	                                
	                            fieldLabel: getLabelFromMap('comboLineaOperacion',helpMap,'Linea Operativa'),
   	                            tooltip: getToolTipFromMap('comboLineaOperacion',helpMap,'Linea Operativa del CheckList de la Cuenta'),
		                        hasHelpIcon:getHelpIconFromMap('comboLineaOperacion',helpMap),								 
		                        Ayuda: getHelpTextFromMap('comboLineaOperacion',helpMap),
				                store: dsLineaOperacion, 
				                displayField:'texto', 
				                valueField:'id', 
				                hiddenName: 'cdLinOpe',
				                typeAhead: true,
				                mode: 'local', 
				                triggerAction: 'all', 
				                //fieldLabel: "L&iacute;nea Oper.", 
				                width: 115, 
				                emptyText:'Seleccione Linea Operativa',
				                forceSelection:true,
				                selectOnFocus:true,
				                id: 'comboLineaOperacion', 
				                name: 'comboLineaOperacion',
				                onSelect: function (record) {
				                            	this.setValue(record.get('id'));
				                            	formEncabezado.findById('cdLinOpe').setValue(record.get('id'));
				                            	this.collapse();
				                            }
				                  }
        					    ]
        			}
        		]
	});

	formEncabezado.render();
	//formEncabezado.render();
	dsLineaOperacion.load({
		callback:function(){
				dsClientesCorp.load({
						callback: function () {
							formEncabezado.findById('comboClientes').setValue(formEncabezado.findById('codigoCliente').getValue());
						}
				});
		}
	});
	
	function LoadFormEncabData (item_id) {
		//alert(item_id);
	var recs = grid.store.getModifiedRecords();
	if (recs.length > 0){
		grid.store.rejectChanges()}
		formEncabezado.findById('codigoSeccion').setValue(item_id);
		storeEncabezado.reload({
					params: (CODIGO_CONFIGURACION != "")?{codigoConfiguracion: CODIGO_CONFIGURACION}:{codigoConfiguracion: '0'},
				callback: function (r, o, success) {
					if (storeEncabezado.reader.jsonData.MEncabezadosCheckListVO != null) {
								cargarDatosTareas(item_id); 
								fallo_encab = false;
								formEncabezado.findById('comboClientes').setValue(storeEncabezado.reader.jsonData.MEncabezadosCheckListVO[0].cdElemen);
								formEncabezado.findById('cdConfig').setValue(storeEncabezado.reader.jsonData.MEncabezadosCheckListVO[0].cdConfig);
								formEncabezado.findById('dsConfig').setValue(storeEncabezado.reader.jsonData.MEncabezadosCheckListVO[0].dsConfig);
								formEncabezado.findById('cdLinOpe').setValue(storeEncabezado.reader.jsonData.MEncabezadosCheckListVO[0].cdLinOpe);
								formEncabezado.findById('comboLineaOperacion').setValue(storeEncabezado.reader.jsonData.MEncabezadosCheckListVO[0].cdLinOpe);
								formEncabezado.findById('comboClientes').setValue(storeEncabezado.reader.jsonData.MEncabezadosCheckListVO[0].cdElemen);
								formEncabezado.findById('codigoCliente').setValue(storeEncabezado.reader.jsonData.MEncabezadosCheckListVO[0].cdElemen);
					}else {
					cargarDatosTareas(item_id);
					//todo revisar en que casos va el alert APinto-ver
					//	Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), storeEncabezado.reader.jsonData.actionErrors);
					}
					//alert(success);
				}
		});
		return;
		formEncabezado.load({
					params: (CODIGO_CONFIGURACION != "")?{codigoConfiguracion: CODIGO_CONFIGURACION}:{codigoConfiguracion: '0'},
					//callback:function(){alert(1)}
					//waitMsg: 'Leyendo datos...',
					success: function (){
								
								cargarDatosTareas(item_id); 
								fallo_encab = false;
								formEncabezado.findById('codigoCliente').setValue(formEncabezado.findById('comboClientes').getValue());
							},
					failure: function(form, action) {
									
									fallo_encab = true;
									cargarDatosTareas(item_id); 
									formEncabezado.render();
							}
			});
	}

	        //se define la forma en que se van a leer los datos que envie la action
        var _jsonFormReaderTareas = new Ext.data.JsonReader( {
            root : 'MTareaChecklistVO',
            totalProperty: 'totalCount',
            waitMsg: 'Leyendo tareas...',
            callback: function (records, options, success) {if (!success){
               Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'),getLabelFromMap('400033', helpMap,'No se encontraron registros'));
               }},
            successProperty : '@success'

        }, [ {
            name : 'dsTarea',
            mapping : 'dsTarea',
            type : 'string'
        }, {
            name : 'fgComple',
            type : 'string',
            mapping : 'fgComple'
        }, {
            name : 'fgNoRequ',
            type : 'string',
            mapping : 'fgNoRequ'
        },{
        	name: 'codigoTarea',
        	type: 'string',
        	mapping: 'codigoTarea'
        }, {
        	name: 'cdtareaPadre',
        	type: 'string',
        	mapping: 'cdtareaPadre'
        }, {
        	name: 'dsTareaPadre',
        	type: 'string',
        	mapping: 'dsTareaPadre'
        }, {
        	name: 'dsUrl',
        	type: 'string',
        	mapping: 'dsUrl'
        }, {
        	name: 'dsAyuda',
        	type: 'string',
        	mapping: 'dsAyuda'
        },
        {
        	name: 'fgPendiente',
        	type: 'string',
        	mapping: 'fgPendiente'
        }]);


    var fgComple = new Ext.grid.CheckColumn({
      id:'chColFgCompleChecklistCuentaId',
      header: getLabelFromMap('chColFgCompleChecklistCuentaId',helpMap,'Concluida'),
      tooltip: getToolTipFromMap('chColFgCompleChecklistCuentaId', helpMap,'Concluida'),
      //header: "Completada",
      dataIndex: 'fgComple',
      align: 'center',
      sortable: true,
      width: 80
    });
    var fgNoRequ = new Ext.grid.CheckColumn({
      id:'chColFgNoRequChecklistCuentaId',
      header: getLabelFromMap('chColFgNoRequChecklistCuentaId',helpMap,'No Requerida'),
      tooltip: getToolTipFromMap('chColFgNoRequChecklistCuentaId', helpMap,'No Requerida'),
      //header: "No Requerida",
      dataIndex: 'fgNoRequ',
      align: 'center',
      sortable: true,
      width: 80
    });
    var fgPendiente = new Ext.grid.CheckColumn({
      id:'chColFgPendienteChecklistCuentaId',
      header: getLabelFromMap('chColFgPendienteChecklistCuentaId',helpMap,'Pendiente'),
      tooltip: getToolTipFromMap('chColFgPendienteChecklistCuentaId', helpMap,'Pendiente'),
      //header: "No Requerida",
      dataIndex: 'fgPendiente',
      align: 'center',
      sortable: true,
      width: 80
    });

	//Store de Tareas
	store = new Ext.data.GroupingStore({

    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_OBTENER_TAREAS_SECCION
                }),

                reader: new Ext.data.JsonReader({
            	root:'MTareaChecklistVO',
            	totalProperty: 'totalCount',
            	waitMsg: 'Leyendo tareas...',
	            successProperty : '@success'
	        },[
	        {name: 'codigoTarea', type: 'string', mapping: 'codigoTarea'},
	        {name: 'dsTarea',  type: 'string',  mapping:'dsTarea'},
	        {name: 'fgComple',  type: 'bool',  mapping:'fgComple'},
	        {name: 'fgNoRequ',  type: 'bool',  mapping:'fgNoRequ'},
	        {name: 'cdtareaPadre', type: 'string', mapping: 'cdtareaPadre'},
	        {name: 'dsTareaPadre', type: 'string', mapping: 'dsTareaPadre'},
	        {name: 'dsUrl', type: 'string', mapping: 'dsUrl'},
	        {name: 'dsAyuda', type: 'string', mapping: 'dsAyuda'},
	        {name: 'fgPendiente', type: 'bool', mapping: 'fgPendiente'}
			]),
			groupField: 'dsTareaPadre',
			sortInfo:{field: 'dsTarea', direction: "ASC"}
        });
        
    //Modelo de Columnas de Tareas
    var cmTareas = new Ext.grid.ColumnModel([{
            id:'cmChklCtadsTarea',
            header: getLabelFromMap('cmChklCtadsTarea',helpMap,'Tarea'),
            tooltip: getToolTipFromMap('cmChklCtadsTarea', helpMap,'Tarea del CheckList de la Cuenta'),
   	        hasHelpIcon:getHelpIconFromMap('cmChklCtadsTarea',helpMap),								 
            Ayuda: getHelpTextFromMap('cmChklCtadsTarea',helpMap),
            
           //header: "Tarea",
           dataIndex: 'dsTarea',
           sortable: true,
           width: 220
        }, {
        	id:'codigoTarea',
        	dataIndex: 'codigoTarea',
            sortable: true
        },
		fgComple,
		fgNoRequ, 
		fgPendiente,
		{
			id: 'cdtareaPadre',
			dataIndex: 'cdtareaPadre',
           sortable: true
		},
		{
			id: 'dsTareaPadre',
			dataIndex: 'dsTareaPadre',
			hidden: true
		}
    ]);

	var value = 0;
    
    //Crea la grilla de tipo GroupingGrid
    var grid = new Ext.grid.GridPanel ({
   		store: store,
   		enableColumnHide: false,
   		columns: [
   					{
   					id: 'codigoTarea', 
   					header: 'Tarea', 
   					dataIndex: 'codigoTarea', 
   					hidden: true
   					},{
        			header: getLabelFromMap('cmDsTareaChecklistCuentaId',helpMap,'Tarea'),
        			tooltip: getToolTipFromMap('cmDsTareaChecklistCuentaId', helpMap, 'Tarea del CheckList de la Cuenta'), 
   	                hasHelpIcon:getHelpIconFromMap('cmDsTareaChecklistCuentaId',helpMap),								 
                    Ayuda: getHelpTextFromMap('cmDsTareaChecklistCuentaId',helpMap),
   					//header: 'Tarea', 
   					dataIndex: 'dsTarea', 
   					align: 'left', 
   					renderer : crearHLink/*,
   					sortable: true*/
   					},{
        			header: getLabelFromMap('cmDsAyudaChecklistCuentaId',helpMap,'Comentario'),
        			tooltip: getToolTipFromMap('cmDsAyudaChecklistCuentaId', helpMap,'Comentario'),
   	                hasHelpIcon:getHelpIconFromMap('cmDsAyudaChecklistCuentaId',helpMap),								 
                    Ayuda: getHelpTextFromMap('cmDsAyudaChecklistCuentaId',helpMap),
        			
   					//header: 'Comentario', 
   					dataIndex: 'dsAyuda', 
   					align: 'left'
   					},
   					fgComple, 
   					fgNoRequ, 
   					fgPendiente,
   					{
   					header: '', 
   					dataIndex: 'cdtareaPadre', 
   					hidden: true, 
   					align: 'center'
   					},{
   					header: '', 
   					dataIndex: 'dsTareaPadre', 
   					hidden: true, 
   					align: 'center'
   					},{
   					header: '', 
   					dataIndex: 'dsUrl', 
   					hidden: true
   					}
   				],
   		view: new Ext.grid.GroupingView ({
   						forceFit: true,
   						emptyGroupText: '&nbsp;',
   						showGroupName: false,
   						enableGroupingMenu: false, 
   						groupTextTpl: '{text}'
   				}),
   		frame: true,
   		width: 725,    //750,
   		height: 300,
   		collapsible: true,
   		loadMask: {msg: getLabelFromMap('400021', helpMap,'Espere...'), disabled: false},
   		animCollapse: true,
   		title: 'Tareas',
   		iconCls: 'icon-grid',
   		renderTo: 'formTareasSeccion',
   		plugins: [fgComple, fgNoRequ, fgPendiente],
   		buttonAlign: 'center',
   		buttons:[
   				{       			
      			text:getLabelFromMap('chkListCtaBttAdd', helpMap,'Guardar Tareas'),
          		tooltip:getToolTipFromMap('chkListCtaBttAdd', helpMap,'Guarda las tareas del CheckList de la Cuenta'),
           		handler:function(){grid.getSelectionModel().selectAll();handlerEdit();}
           		},
           		{       			
      			text:getLabelFromMap('chkListCtaBttBack', helpMap,'Regresar'),
          		tooltip:getToolTipFromMap('chkListCtaBttBack', helpMap,'Regresa a la pantalla anterior'),
           		handler:function(){
           				if(CODIGO_CONFIGURACION != "")value=1;
           				window.location=_ACTION_IR_CHECKLIST_CONFIGURA_CUENTA+"?flag="+value;}
           		}
           		]
   		/*bbar: [{text: 'Guardar Tareas', 
   				handler: function () {
   											grid.getSelectionModel().selectAll();
   											handlerEdit();
   									}
   				},
   				{
   				text: 'Regresar', 
   				handler: function () {Ext.Msg.alert("Advertencia","Se regresara a la pagina anterior");}
   				}]*/
   	});

		//Funcion para manejo de ediciones
		function handlerEdit (edtEvt) {
			if (formEncabezado.findById('dsConfig').getValue() == "") {
				Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'), function(){formEncabezado.findById('dsConfig').focus();});
				return;
			}
			if (tab_actual == -1) {
				//Esto es temporal hasta que encuentre una manera mas elegante de hacerlo
				//Ext.Msg.alert('Aviso', 'Debe seleccionar una secci&oacute;n');
				Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400086', helpMap,'Debe seleccionar una secci&oacute;n'));
				return;
			}
			var _params = ((CODIGO_CONFIGURACION != "")?("codigoConfiguracion=" + CODIGO_CONFIGURACION):("codigoConfiguracion=")) + "&" + 
							"descripcionConf=" + formEncabezado.findById('dsConfig').getValue() +
							"&lineaOperacion=" + formEncabezado.findById('comboLineaOperacion').getValue() +
							"&codigoCliente=" + formEncabezado.findById('codigoCliente').getValue() +
							"&cdPerson=" + formEncabezado.findById('cdPerson').getValue() +
							"&codigoSeccion=" + formEncabezado.findById('codigoSeccion').getValue() + "&";
			var recs = grid.store.getModifiedRecords();
			if (recs.length > 0) {
				for (var i=0; i<recs.length; i++) {
					_params += "listaParams[" + i + "].cdTarea=" + recs[i].get('codigoTarea') + "&listaParams[" + i + "].cdCompletada=" + ((recs[i].get('fgComple') == true)?1:0) + "&" +
								"listaParams[" + i + "].noRequerida=" + ((recs[i].get('fgNoRequ') == true)?1:0) + "&" +
								"listaParams[" + i + "].cdPendiente=" + ((recs[i].get('fgPendiente') == true)?1:0) + "&";
				}
			} else {
				//Si no ha modificado tareas, debe sí o sí enviarlas al momento de guardar
				//pedido por Herbe 20/05/2008
				recs = grid.getSelectionModel().getSelections(); //Obtiene los datos de todas las tareas
				for (var i=0; i<recs.length; i++) {
					_params += "listaParams[" + i + "].cdTarea=" + recs[i].get('codigoTarea') + "&listaParams[" + i + "].cdCompletada=" + ((recs[i].get('fgComple') == true)?1:0) + "&" +
								"listaParams[" + i + "].noRequerida=" + ((recs[i].get('fgNoRequ') == true)?1:0) + "&" +
								"listaParams[" + i + "].cdPendiente=" + ((recs[i].get('fgPendiente') == true)?1:0) + "&";
				}
			}
			startMask (grid.id, 'Guardando datos...');
			/*
			var conn = new Ext.data.Connection();
            conn.request({
		    url: _ACTION_GUARDA_TAREA_SECCION,
			method: 'POST',
		    params : _params,
		    success: function(form, action, response){
		    		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'),action.result.actionMessages[0]);
		    		band = false;
		    		},
			 failure: function(form, action){
			         Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'),action.result.errorMessages[0]);
					 if (CODIGO_CONFIGURACION == "") CODIGO_CONFIGURACION = Ext.util.JSON.decode(response.responseText).codigoConfiguracion;
                        	 grid.store.commitChanges(); 
                          }
			});*/
			
           var conn = new Ext.data.Connection ();
            conn.request ({
				url: _ACTION_GUARDA_TAREA_SECCION,
				method: 'POST',
				successProperty : '@success',
				params : _params,
				waitMsg: getLabelFromMap('400017', helpMap,'Espere por favor'),
	                          	callback: function (options, success, response) {
	                          				endMask();
	                          				if (Ext.util.JSON.decode(response.responseText).success == false) {
	                          					Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), Ext.util.JSON.decode(response.responseText).errorMessages[0]);
	                          					//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), getLabelFromMap('400035', helpMap,response.responseText.errorMessages[0]));
	                          					band = false;
	                          				} else {
	                          					Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), 'Se han guardado con &eacute;xito las tareas');
	                          					if (CODIGO_CONFIGURACION == "")
	                          					{ 
	                          						CODIGO_CONFIGURACION = Ext.util.JSON.decode(response.responseText).codigoConfiguracion;
	                          						//Ext.getCmp('comboClientes').disabled=true;
	                          						Ext.getCmp('comboClientes').setDisabled(true);
	                          						Ext.getCmp('dsConfig').setDisabled(true);
	                          						Ext.getCmp('comboLineaOperacion').setDisabled(true);
	                          					}
	                          					grid.store.commitChanges(); 
	                          				}
	                          			}
           	});
			return;
		}

		function cargarDatosTareas (item_id) {
			store.load({
					params: (CODIGO_CONFIGURACION != "")?{codigoConfiguracion: CODIGO_CONFIGURACION, codigoSeccion: item_id}:{codigoConfiguracion: formEncabezado.findById('cdConfig').getValue(), codigoSeccion: item_id},
					waitMsg: getLabelFromMap('400028', helpMap,'Leyendo datos...'),
		            callback: function (records, options, success) {if (!success){Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), getLabelFromMap('400033', helpMap,'No se encontraron registros')); grid.store.removeAll();}}
					
			});
		}

		//Función que crea un texto de columna/fila como hiperlink
		function crearHLink (value, metadata, record, rowIndex, colIndex, store) {
			if (record.data['dsUrl'] != "" && record.data['dsUrl'] != undefined) {
				return '<a target="_blank" href="' + record.data['dsUrl'] + '">' + value + '</a>';
			} else {
				return value;
			}
		}

});


/*************  Definición de Plugin para Checkbox en grillas ********************************************/
		Ext.grid.CheckColumn = function(config){
		    Ext.apply(this, config);
		    if(!this.id){
		        this.id = Ext.id();
		    }
		    this.renderer = this.renderer.createDelegate(this);
		};

		Ext.grid.CheckColumn.prototype ={
		    init : function(grid){
		        this.grid = grid;
		        this.grid.on('render', function(){
		            var view = this.grid.getView();
		            view.mainBody.on('mousedown', this.onMouseDown, this);
		        }, this);
		    },
		
		    onMouseDown : function(e, t){
		        if(t.className && t.className.indexOf('x-grid3-cc-'+this.id) != -1){
		            e.stopEvent();
		            var index = this.grid.getView().findRowIndex(t);
		            var record = this.grid.store.getAt(index);
		            record.set(this.dataIndex, !record.data[this.dataIndex]);

		            if (this.dataIndex == 'fgNoRequ') {
		            	if (record.data['fgNoRequ']) record.set('fgComple', false);
		            	if (record.data['fgNoRequ']) record.set('fgPendiente', false);
		            }
		            if (this.dataIndex == 'fgComple') {
		            	if (record.data['fgComple']) record.set('fgNoRequ', false);
		            	if (record.data['fgComple']) record.set('fgPendiente', false);
		            }
		            if (this.dataIndex == 'fgPendiente') {
		            	if (record.data['fgPendiente']) record.set('fgNoRequ', false);
		            	if (record.data['fgPendiente']) record.set('fgComple', false);
		            }
		            
		            this.grid.getSelectionModel().selectRow(index); //Selecciona la fila completa
		        }
		    },
			onClick: function (e, t) {
			},
		    renderer : function(v, p, record){
		        p.css += ' x-grid3-check-col-td'; 
		        return '<div class="x-grid3-check-col'+(v?'-on':'')+' x-grid3-cc-'+this.id+'">&#160;</div>';
		    }
		};
/*************  Fin Definición de Plugin para Checkbox en grillas ********************************************/