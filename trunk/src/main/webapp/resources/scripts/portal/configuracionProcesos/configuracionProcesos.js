
Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";


        /*var readerTipoPersona = new Ext.data.JsonReader( {
            root : 'personasJ',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'codigo',
            mapping : 'codigo',
            type : 'string'
        }, {
            name : 'descripcion',
            type : 'string',
            mapping : 'descripcion'
        }]);

       var dsTipoPersonaJ = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_TIPO_PERSONAS_J
            }),
            reader: readerTipoPersona,
        remoteSort: true
        });

        var readerGrupoCorporativo = new Ext.data.JsonReader( {
            root : 'clientesCorp',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'codigo',
            mapping : 'cdElemento',
            type : 'string'
        }, {
            name : 'descripcion',
            type : 'string',
            mapping : 'dsElemen'
        }]);

       var dsGrupoCorporativo = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_CLIENTES_CORP
            }),
            reader: readerGrupoCorporativo,
        remoteSort: true
        });*/
		/********* Comienza el form ********************************/

	var el_form = new Ext.FormPanel ({
			id: 'el_form',
			renderTo: 'formBusqueda',
	  		title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('formConfiguracionProcesoId', helpMap,'Validaci&oacute;n de Procesos')+'</span>',
            url : _ACTION_BUSCAR_PROCESOS,
            frame : true,
            bodyStyle : 'background: white',
            width : 600,
            autoHeight: true,
            waitMsgTarget : true,
            labelAlign:'right',
            //baseCls: '',
			items:[{
				html: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',            
	            layout: 'column',
	            layoutConfig: {columns: 2, align: 'left'},
	            bodyStyle : 'background: white',
	            baseCls: '',
	            frame: true,
	            items: [
	               	        {
	               			layout: 'form',
	            			columnWidth: .15,
				            baseCls: '',
				            html: '<span class="x-form-item" style="font-weight:bold"></span><br>'
				            },
	            			{
	            			layout: 'form',
	            			columnWidth: .50,
	            			bodyStyle : 'background: white',
				            baseCls: '',
	            			items: [
	            					{
	            					xtype: 'textfield', 
				          			fieldLabel: getLabelFromMap('procesosTextAseguradora',helpMap,'Aseguradora'),
	                            	tooltip: getToolTipFromMap('procesosTextAseguradora',helpMap,'Aseguradora'),
					                name: 'dsAseguradora'
					                },
	            					{
	            					xtype: 'textfield', 
				          			fieldLabel: getLabelFromMap('procesosTextProducto',helpMap,'Producto'),
	                            	tooltip: getToolTipFromMap('procesosTextProducto',helpMap,'Producto'),
					                name: 'dsProducto'
					                },
	            					{
	            					xtype: 'textfield', 
				          			fieldLabel: getLabelFromMap('procesosTextElemento',helpMap,'Nivel'),
	                            	tooltip: getToolTipFromMap('procesosTextElemento',helpMap,'Nivel'),
					                name: 'dsElemento'
					                },
	            					{
	            					xtype: 'textfield', 
				          			fieldLabel: getLabelFromMap('procesosTextProceso',helpMap,'Proceso'),
	                            	tooltip: getToolTipFromMap('procesosTextProceso',helpMap,'Proceso'),
					                name: 'dsProceso'
					                }
	            					]
	            			}
	           	],
            		buttonAlign: 'center',
            		buttons: [
            					{
            					text:getLabelFromMap('procesosButtonBuscar', helpMap,'Buscar'),
           						tooltip:getToolTipFromMap('procesosButtonBuscar', helpMap,'Buscar'),
            					//text: 'Buscar', 
            					handler: function () {
													if (grilla != null) {
														reloadGrid(grilla);
													}else {
														createGrid();
													}
      										}
            					},
            					{
      							text:getLabelFromMap('procesosButtonCancelar', helpMap,'Cancelar'),
           						tooltip:getToolTipFromMap('procesosButtonCancelar', helpMap,'Cancelar'),	
            					//text: 'Cancelar', 
            					handler: function() {
            							el_form.getForm().reset();
            							}
            					}
            				]
            	}]

            //se definen los campos del formulario
	});
	/********* Fin del form ************************************/
	
	/********* Comienzo del grid *****************************/
		//Definición Column Model
	    var cm = new Ext.grid.ColumnModel([
	    		{
	    			dataIndex: 'cdElemento',
	    			hidden: true
	    		},
	    		{
	    			dataIndex: 'cdProceso',
	    			hidden: true
	    		},
	    		{
	    			dataIndex: 'cdRamo',
	    			hidden: true
	    		},
				{
				   	header: getLabelFromMap('procesosAseguradora',helpMap,'Aseguradora'),
			        tooltip: getToolTipFromMap('procesosAseguradora', helpMap,'Aseguradora'),		           	
		           	dataIndex: 'dsUniEco',
		           	width: 120,
		           	sortable: true
	        	},{
				   	header: getLabelFromMap('procesosProducto',helpMap,'Producto'),
			        tooltip: getToolTipFromMap('procesosProducto', helpMap,'Producto'),		           	
		           	dataIndex: 'dsProducto',
		           	width: 110,
		           	sortable: true
	           	},{
				   	header: getLabelFromMap('procesosElemento',helpMap,'Nivel'),
			        tooltip: getToolTipFromMap('procesosElemento', helpMap,'Nivel'),
		           	dataIndex: 'dsElemento',
		           	width: 110,
		           	sortable: true
	           	},{
				   	header: getLabelFromMap('procesosProceso',helpMap,'Proceso'),
			        tooltip: getToolTipFromMap('procesosProceso', helpMap,'Proceso'),
		           	dataIndex: 'dsProceso',
		           	width: 120,
		           	sortable: true
	           	},
	           	{
				   	header: getLabelFromMap('procesosIndicador',helpMap,'Indicador'),
			        tooltip: getToolTipFromMap('procesosIndicador', helpMap,'Indicador'),
	           		dataIndex: 'dsEstado',
	           		width: 100,
		           	sortable: true
	           	}
	           	]);
          	
//Fin Definición Column Model
//Crea el Store
		function crearGridStore(){
		 			store = new Ext.data.Store({
		    			proxy: new Ext.data.HttpProxy({
						url: _ACTION_BUSCAR_PROCESOS
		                }),
		                reader: new Ext.data.JsonReader({
		            	root:'listaProcesos',
		            	totalProperty: 'totalCount',
			            successProperty : '@success'
			        },[
			        {name: 'dsProducto',  type: 'string',  mapping:'dsRamo'},
			        {name: 'dsUniEco',  type: 'string',  mapping:'dsUniEco'},
			        {name: 'dsElemento',  type: 'string',  mapping:'dsElemento'},
			        {name: 'dsProceso',  type: 'string',  mapping:'dsProceso'},
			        {name: 'dsEstado',  type: 'string',  mapping:'dsEstado'},
			        {name: 'cdProducto',  type: 'string',  mapping:'cdProducto'},
			        {name: 'cdUniEco', type: 'string', mapping: 'cdUniEco'},
			        {name: 'cdElemento', type: 'string', mapping: 'cdElemento'},
			        {name: 'cdProceso', type: 'string', mapping: 'cdProceso'},
			        {name: 'cdEstado', type: 'string', mapping: 'cdEstado'},
			        {name: 'swEstado', type: 'string', mapping: 'swEstado'},
			        {name: 'cdRamo', type: 'string', mapping: 'cdRamo'}
					])
		        });
				return store;
		 	}
		//Fin Crea el Store
	var grilla;
	
	function createGrid(){
		grilla= new Ext.grid.GridPanel({
	        el:'gridElementos',
	        id: 'grilla',
	        store:crearGridStore(),
			border:true,
			//autoWidth: true,
			title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
			buttonAlign: 'center',
	        cm: cm,
	        loadMask: {msg: getLabelFromMap('400058',helpMap,'Cargando datos ...'), disabled: false},
	        stripeRows: true,
	        collapsible: true, 
			buttons:[
		        		/*{
		      				text:getLabelFromMap('gridProcesosButtonAgregar', helpMap,'Agregar'),
		           			tooltip:getToolTipFromMap('gridProcesosButtonAgregar', helpMap,'Agrega un proceso'),			        			
		            		handler:function(){
		            					agregar();
		            				}
		            	},*/
		            	{
		      				text:getLabelFromMap('gridProcesosButtonEditar', helpMap,'Editar'),
		           			tooltip:getToolTipFromMap('gridProcesosButtonEditar', helpMap,'Edita un proceso'),			        			
		            		handler:function(){
		            				var record = getSelectedRecord(grilla);
			            			if(record != null){
										editar(record);
			                		}
			                		else{
			                			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));			                			
			                		}
		                	}
		            	},
		            	/*{
		      				text:getLabelFromMap('gridProcesosButtonBorrar', helpMap,'Borrar'),
		           			tooltip:getToolTipFromMap('gridProcesosButtonBorrar', helpMap,'Borra un proceso'),			        			
		            		handler:function(){
		            				var record = getSelectedRecord(grilla);
			            			if(record != null){
			            				borrar(record);
			                		}
			                		else{
			                			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));			                			
			                		}
		                	}
		            	},*/
		            	{
		      				text:getLabelFromMap('gridProcesosButtonExportar', helpMap,'Exportar'),
		           			tooltip:getToolTipFromMap('gridProcesosButtonExportar', helpMap,'Exporta datos a otros formatos'),			        			
		            		handler: function (){
				                        var url = _ACTION_EXPORT_PROCESOS + '?dsAseguradora=' + el_form.form.findField('dsAseguradora').getValue() +
																    		'&dsProducto=' + el_form.form.findField('dsProducto').getValue() +
																    		'&dsElemento=' + el_form.form.findField('dsElemento').getValue() +
																    		'&dsProceso=' + el_form.form.findField('dsProceso').getValue();
				                	 	showExportDialog( url );
		            		}

		            	}/*, {
		      				text:getLabelFromMap('gridProcesosButtonRegresar', helpMap,'Regresar'),
		           			tooltip:getToolTipFromMap('gridProcesosButtonRegresar', helpMap,'Regresa a la pantalla anterior')			        			
		            	}*/
	            	],
	    	width:600,
	    	frame:true,
			height:578,
			sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			bbar: new Ext.PagingToolbar({
					pageSize: itemsPerPage,
					store: store,
					displayInfo: true,
					displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} de {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar'),
					beforePageText: getLabelFromMap('400009', helpMap, 'P&aacute;gina'),
					afterPageText:  getLabelFromMap('400009', helpMap, 'de {0}'),
					firstText : getLabelFromMap('400009', helpMap, 'Primero'),
					prevText : getLabelFromMap('400009', helpMap, 'Anterior'),
					nextText : getLabelFromMap('400009', helpMap, 'Siguiente'),
					lastText : getLabelFromMap('400009', helpMap, 'Ultimo'),
					refreshText : getLabelFromMap('400009', helpMap, 'Refrescar')
			    })
			});
	
	    grilla.render()
	}
	
	/********* Fin del grid **********************************/
	
	el_form.render();
	createGrid();

	function borrar (record) {
		var _params = "";
		
		Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
			{
         		if (btn == "yes"){
					_params = "cdUniEco=" + record.get('cdUniEco') + "&cdRamo=" + record.get('cdRamo') + "&cdProceso=" + record.get('cdProceso') + "&cdElemento=" + record.get('cdElemento');
			
					execConnection (_ACTION_BORRAR_PROCESO, _params, cbkBorrar);
				}
			}); 
	}
	function cbkBorrar (_success, _message) {
		if (_success) {
			Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid(grilla);});
		}else {
			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
		}
	}

});

function reloadGrid(grilla){
	var _params = {
    		dsAseguradora: Ext.getCmp('el_form').form.findField('dsAseguradora').getValue(),
    		dsProducto: Ext.getCmp('el_form').form.findField('dsProducto').getValue(),
    		dsElemento: Ext.getCmp('el_form').form.findField('dsElemento').getValue(),
    		dsProceso: Ext.getCmp('el_form').form.findField('dsProceso').getValue()
	};
	reloadComponentStore(grilla, _params, cbkReload);
}
function cbkReload (_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors);
	}
}
