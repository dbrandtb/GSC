//var helpMap = new Map();

helpMap.put('btnAddConfAlert',{tooltip:'Guardar la configuraci&oacute;n de alertas agregada'});

Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";
  //  var itemsPerPage=10;

	/********* Comienza desplegable de Usuarios *****************/
	var users_store = new Ext.data.Store ({
					proxy: new Ext.data.HttpProxy ({url: _ACTION_COMBO_USUARIOS}),
					reader: new Ext.data.JsonReader({
								root: 'confAlertasAutoUsuarios',
								id: 'cdUsuario',
								successProperty: '@success'
							}, [
								{name: 'cdUsuario', type: 'string', mapping: 'cdUsuario'},
								{name: 'dsUsuario', type: 'string', mapping: 'dsUsuario'} 
							])
			});
	
	/********** Fin desplegable de Usuario **********************/

	/********** Comienza desplegable de Clientes ****************/
			////////ATENCION: falta este SP
	var clientes_store = new Ext.data.Store ({
				proxy: new Ext.data.HttpProxy({url: _ACTION_LISTA_CLIENTES}),
				reader: new Ext.data.JsonReader ({
							root: 'UsuariosClientesComboBox'
						}, [
							{name: 'codigo', type: 'string', mapping: 'codigo'},
							{name: 'descripcion', type: 'string', mapping: 'descripcion'}
						])
			});
	var clientes = new Ext.form.ComboBox ({
                            tpl: '<tpl for="."><div ext:qtip="{cdElemento}. {dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
                            store: clientes_store,
                            displayField:'descripcion',
                            valueField:'codigo',
                            hiddenName: 'dsCliente',
                            typeAhead: true,
                            mode: 'local',
                            triggerAction: 'all',                            
		        			text:getLabelFromMap('cmConfAlerAutClienteId',helpMap,'Nivel'),
		        			tooltip:getToolTipFromMap('cmConfAlerAutClienteId',helpMap,'Nivel'),
                            width: 300,
                            emptyText:'Seleccione Nivel...',
                            selectOnFocus:true,
                            forceSelection:true
			});
	/********** Fin desplegable de Clientes *********************/
	
	/********* Comienza el form ********************************/
	var el_form = new Ext.FormPanel ({
			id:'el_formId',
			renderTo: 'formulario',
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('el_formId', helpMap,'Configuraci&oacute;n de Alertas')+'</span>',
			//title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('12',helpMap,''/*Configuraci&oacute;n de Alertas*/)+'</span>',
            //labelWidth : 50,
            url : _ACTION_BUSCAR_CONFIGURACION_ALERTAS,
            frame : true,
            bodyStyle : 'padding:5px 5px 0',
            bodyStyle:'background: white',
            width : 550,
            height: 180,
            autoHeight: true,
            labelAlign:'right',
            waitMsgTarget : true,
            //border:false,
			items:[{
					html: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
            
		            layout: 'column',
		            layoutConfig: {columns: 2},
		            items: [
		            			{layout: 'form',
		            			columnWidth: .50,
		            			items: [            					
						                  {
						                  xtype: 'textfield', 
						                  fieldLabel: getLabelFromMap('confAAdsUsuarioId',helpMap,'Usuario'), 
						                  tooltip: getToolTipFromMap('confAAdsUsuarioId',helpMap,'Usuario para Configuración de Alertas'),
						                  hasHelpIcon:getHelpIconFromMap('confAAdsUsuarioId',helpMap),
										  Ayuda: getHelpTextFromMap('confAAdsUsuarioId',helpMap),
						                  id: 'confAAdsUsuarioId', 
						                  name: 'dsUsuario',
						                  width: 120}
		            					]
		            			},
		            			{layout: 'form',
		            			columnWidth: .50,
		            			items: [
		            						{xtype: 'textfield',  
		            						fieldLabel: getLabelFromMap('confAAdsRamoId',helpMap,'Producto'), 
		            						tooltip: getToolTipFromMap('confAAdsRamoId',helpMap,'Producto para Configuración de Alertas'),
		            						hasHelpIcon:getHelpIconFromMap('confAAdsRamoId',helpMap),
										  	Ayuda: getHelpTextFromMap('confAAdsRamoId',helpMap),
		            						name: 'dsRamo',
		            						width: 120,
		            						id: 'confAAdsRamoId'}
		            					]
		            			},
		            			{layout: 'form',
		            			columnWidth: .50,
		            			items: [
						                    {xtype: 'textfield', 
						                    fieldLabel: getLabelFromMap('confAAdsClienteId',helpMap,'Nivel'), 
						                    tooltip: getToolTipFromMap('confAAdsClienteId',helpMap,'Nivel para Configuraci&oacute;n de Alertas'),
						                    hasHelpIcon:getHelpIconFromMap('confAAdsClienteId',helpMap),
										  	Ayuda: getHelpTextFromMap('confAAdsClienteId',helpMap),
						                    id: 'confAAdsClienteId', 
						                    width: 120,
						                    name: 'dsCliente'}
		            					]
		            			},
		            			{layout: 'form',
		            			columnWidth: .50, //Para 2 columnas
		            			items: [
		            						{xtype: 'textfield',  
		            						fieldLabel: getLabelFromMap('confAAdsAseguradoraId',helpMap,'Aseguradora'), 
		            						tooltip: getToolTipFromMap('confAAdsAseguradoraId',helpMap,'Aseguradora para Configuraci&oacute;n de Alertas'),
		            						hasHelpIcon:getHelpIconFromMap('confAAdsAseguradoraId',helpMap),
										  	Ayuda: getHelpTextFromMap('confAAdsAseguradoraId',helpMap),
		            						id: 'confAAdsAseguradoraId', 
		            						width: 120,
		            						name: 'dsAseguradora'}
		            					]
		            			},
			        			{
			        				layout: 'form',
			        				columnWidth: .50,
			        				items: [
			        						{xtype: 'textfield',  
			        						fieldLabel: getLabelFromMap('confAAdsProcesoId',helpMap,'Proceso'), 
			        						tooltip: getToolTipFromMap('confAAdsProcesoId',helpMap,'Proceso para Configuraci&oacute;n de Alertas'),
			        						hasHelpIcon:getHelpIconFromMap('confAAdsProcesoId',helpMap),
										  	Ayuda: getHelpTextFromMap('confAAdsProcesoId',helpMap),
			        						name: 'dsProceso', 
			        						width: 120,
			        						id: 'confAAdsProcesoId'}
			        					   ]
			        			},
			        			{layout: 'form',
			        			columnWidth: .50,
			        			items: [
			        						{xtype: 'textfield', 
			        						fieldLabel: getLabelFromMap('confAAdsRolId',helpMap,'Rol'), 
			        						tooltip: getToolTipFromMap('confAAdsRolId',helpMap,'Rol para Configuraci&oacute;n de Alertas'),
			        						hasHelpIcon:getHelpIconFromMap('confAAdsRolId',helpMap),
										  	Ayuda: getHelpTextFromMap('confAAdsRolId',helpMap),
			        						name: 'dsRol',
			        						width: 120,
			        						id: 'confAAdsRolId'}
			        					]
			        			},{
				   			html: '<span class="x-form-item" style="font-weight:bold"></span><br>'
				   			}
		            		],
		            		buttonAlign: 'center',
		            		buttons: [
		            					{text: getLabelFromMap('confAABtnBuscar',helpMap,'Bu'), 
		            					tooltip: getToolTipFromMap('confAABtnBuscar',helpMap,'Busca configurar alertas'),
		            					handler: function () {
		   														if (el_form.form.isValid()) {
		   															if (grilla != null) {
		   																reloadGrid(grilla,el_form);
		   															}else {
		   																createGrid();
		   															}
		   														}
			            										}
		            					},
		            					{text: getLabelFromMap('confAABtnCancelar',helpMap,'Cancelar'), 
		            					tooltip: getToolTipFromMap('confAABtnCancelar',helpMap,'Cancelar operaci&oacute;n'),
		            					 handler: function() {el_form.getForm().reset();}}
		            				]
			}]
            //se definen los campos del formulario
	});
	/********* Fin del form ************************************/
	
	/********* Comienzo del grid *****************************/
		//Definición Column Model
	    var cm = new Ext.grid.ColumnModel([
	    		{
	    			header: '',
	    			dataIndex: 'cdIdUnico',
	    			hidden: true
	    		},
				{
		           	header: getLabelFromMap('confAACmCliente',helpMap,'Nivel'),
		           	tooltip: getToolTipFromMap('confAACmCliente',helpMap,'Nivel'),
		           	dataIndex: 'dsNombre',
		           	sortable: true,
		           	width: 90
	        	},{
		           	header: getLabelFromMap('confAACmNombre',helpMap,'Nombre'),
		           	tooltip: getToolTipFromMap('confAACmNombre',helpMap,'Nombre'),
		           	dataIndex: 'dsAlerta',
		           	sortable: true,
		           	width: 90
	        	},{
		           	header: getLabelFromMap('confAACmProceso',helpMap,'Proceso'),
		           	tooltip: getToolTipFromMap('confAACmProceso',helpMap,'Proceso'),
		           	dataIndex: 'dsProceso',
		           	sortable: true,
		           	width: 90
	           	},{
		           	header: getLabelFromMap('confAACmUsuario',helpMap,'Usuario'),
		           	tooltip: getToolTipFromMap('confAACmUsuario',helpMap,'Usuario'),
		           	dataIndex: 'dsUsuario',
		           	sortable: true,
		           	width: 90
	           	},{
		           	header: getLabelFromMap('confAACmRamo',helpMap,'Producto'),
		           	tooltip: getToolTipFromMap('confAACmRamo',helpMap,'Producto'),
		           	dataIndex: 'dsRamo',
		           	width: 90,
		           	sortable: true
	           	},
	           	{
	           		header: getLabelFromMap('confAACmAseguradora',helpMap,'Aseguradora'),
		           	tooltip: getToolTipFromMap('confAACmAseguradora',helpMap,'Aseguradora'),
	           		dataIndex: 'dsUniEco',
	           		sortable: true,
	           		width: 90
	           	},
	           	{
	           		header: getLabelFromMap('confAACmProducto',helpMap,'Producto'),
		           	tooltip: getToolTipFromMap('confAACmProducto',helpMap,'Producto'),
	           		dataIndex: 'dsProducto',
	           		sortable: true,
	           		width: 90
	           	},
	           	{
	           		header: getLabelFromMap('confAACmRol',helpMap,'Rol'),
		           	tooltip: getToolTipFromMap('confAACmRol',helpMap,'Rol'),
	           		dataIndex: 'dsRol',
	           		sortable: true,
	           		width: 90
	           	}
	           	]);
		//Fin Definición Column Model
		//Crea el Store
		function crearGridStore(){
		 			store = new Ext.data.Store({
		    			proxy: new Ext.data.HttpProxy({
						url: _ACTION_BUSCAR_CONFIGURACION_ALERTAS,
						waitMsg: getLabelFromMap('400070',helpMap,'Cargando datos...')
		                }),
		                reader: new Ext.data.JsonReader({
		            	root:'MConfiguracionAlertasAutomaticoList',
		            	totalProperty: 'totalCount',
			            successProperty : '@success'
			        },[
			        {name: 'cdIdUnico', type: 'string', mapping: 'cdIdUnico'},
			        {name: 'cdCliente',  type: 'string',  mapping:'cdCliente'},
			        {name: 'dsNombre',  type: 'string',  mapping:'dsNombre'},
			        {name: 'dsProceso',  type: 'string',  mapping:'dsProceso'},
			        {name: 'dsUsuario',  type: 'string',  mapping:'dsUsuario'},
			        {name: 'cdRamo',  type: 'string',  mapping:'cdRamo'},
			        {name: 'dsUniEco',  type: 'string',  mapping:'dsUniEco'},
			        {name: 'dsProducto',  type: 'string',  mapping:'dsProducto'},
			        {name: 'dsAlerta', type: 'string', mapping: 'dsAlerta'},
			        {name: 'dsRamo', type: 'string', mapping: 'dsRamo'},
			        {name: 'dsRol', type: 'string', mapping: 'dsRol'}
					])
		        });
				return store;
		 	}
		//Fin Crea el Store
		
	var grilla;
	
	function createGrid(){
		grilla= new Ext.grid.GridPanel({
			id:'grillaId',
	        el:'gridConfiguraAlertas',
	        store:crearGridStore(),
			border:true,
			title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
	        cm: cm,
	        loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
	        stripeRows: true,
	        collapsible: true,
	        buttonAlign:'center',
			buttons:[
		        		{
		        			text: getLabelFromMap('confAABtnAgregar',helpMap,'Agregar'), 
            				tooltip: getToolTipFromMap('confAABtnAgregar',helpMap,'Agrega una configuraci&oacute;n alerta'),
		            		handler:function(){agregar()}
		            	},
		            	{
		            		text: getLabelFromMap('confAABtnEditar',helpMap,'Editar'), 
            				tooltip: getToolTipFromMap('confAABtnEditar',helpMap,'Edita una configuraci&oacute;n alerta'),
		            		handler:function(){
			            			if(getSelectedRecord()!=null){
			                			editar(getSelectedRecord());
			                		}
			                		else{
			                			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe Seleccione un registro para realizar esta operaci&oacute;n'));
			                		}
		                	}
		            	},
		            	{
		            		text: getLabelFromMap('confAABtnBorrar',helpMap,'Eliminar'), 
            				tooltip: getToolTipFromMap('confAABtnBorrar',helpMap,'Elimina una configuraci&oacute;n de alerta'),
		                	handler:function(){
		                			if(getSelectedCodigo() != ""){
		                					borrar(getSelectedCodigo());
		                					//reloadGrid();
		                			}
		                			else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe Seleccione un registro para realizar esta operaci&oacute;n'));}
		                		}
		            	},
		            	{
		            		text: getLabelFromMap('confAABtnExportar',helpMap,'Exportar'), 
            				tooltip: getToolTipFromMap('confAABtnExportar',helpMap,'Exporta el contenido del grid'),
		                	handler:function(){
                                  //Ext.getCmp('el_form').form.findField('dsUsuario').getValue()
                                  //el_form.findById('dsUsuario').getValue()
                                  var url = _ACTION_CONF_ALERTAS_EXPORTAR + '?dsUsuario=' +Ext.getCmp('confAAdsUsuarioId').getValue()+ '&dsCliente=' +Ext.getCmp('confAAdsClienteId').getValue()+ '&dsProceso=' +Ext.getCmp('confAAdsProcesoId').getValue() + '&dsRamo=' +Ext.getCmp('confAAdsRamoId').getValue()+ '&dsAseguradora=' +Ext.getCmp('confAAdsAseguradoraId').getValue()+ '&dsRol=' +Ext.getCmp('confAAdsRolId').getValue();
                	 	          showExportDialog( url );
                               }
		            	}
		            	/*{
		            		text: getLabelFromMap('confAABtnRegresar',helpMap,'Regresar'), 
            				tooltip: getToolTipFromMap('confAABtnRegresar',helpMap)
		            	}*/
	            	],
	    	width:550,
	    	frame:true,
			height:590,
			sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			bbar: new Ext.PagingToolbar({
					pageSize: itemsPerPage,
					store: store,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })
			});
	
	    grilla.render();
	}
	
//***
	/********* Fin del grid **********************************/

	//Función para obtener el registro seleccionado en la grilla
		function getSelectedRecord(){
             var m = grilla.getSelections();
             if (m.length == 1 ) {
                return m[0];
             }
        }
	


	//Muestra los componentes en pantalla
	el_form.render();
	createGrid();
	//Fin Muestra los componentes en pantalla

	//Obtiene el Código de la alerta seleccionada
	function getSelectedCodigo(){
              var m = grilla.getSelections();
              var jsonData = "";
              for (var i = 0, len = m.length;i < len; i++) {
                var ss = m[i].get("cdIdUnico");
                if (i == 0) {
                jsonData = jsonData + ss;
                } else {
                  jsonData = jsonData + "," + ss;
               }
              }
              return jsonData;
    }
	//Borra la alerta seleccionada
	function borrar(key){
   			
					var conn = new Ext.data.Connection();
					Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
					{
	           if (btn == "yes"){
								conn.request({
								url: _ACTION_ELIMINA_CONFIGURACION_ALERTAS,
								method: 'POST',
								params: {"cdIdUnico":key},
								success: function() {
								//Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),'Configuraci&oacute;n eliminada');
								Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400063', helpMap,'Configuraci&oacute;n eliminada'));
								reloadGrid(grilla,el_form);
								},
								failure: function() {
								//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'),'No se elimino la configuraci&oacute;n');
								Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400064', helpMap,'No se elimino la configuraci&oacute;n'));
								}
								});
								}

					});
								
  	};

});

	
	function reloadGrid(grilla,el_form){
	    var mStore = grilla.store;
	    var o = {start: 0};
	    mStore.baseParams = mStore.baseParams || {};
	    mStore.baseParams['dsUsuario'] = el_form.findById('confAAdsUsuarioId').getValue();
	    mStore.baseParams['dsCliente'] = el_form.findById('confAAdsClienteId').getValue();
	    mStore.baseParams['dsProceso'] = el_form.findById('confAAdsProcesoId').getValue();
	    mStore.baseParams['dsRamo'] = el_form.findById('confAAdsRamoId').getValue();
	    mStore.baseParams['dsAseguradora'] = el_form.findById('confAAdsAseguradoraId').getValue();
	    mStore.baseParams['dsRol'] = el_form.findById('confAAdsRolId').getValue();

	    mStore.reload(
	              {
	                  params:{start:0,limit:itemsPerPage},
	                  callback : function(r,options,success) {
	                      if (!success) {
	                         Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400033', helpMap,'No se encontraron registros'));
	                         mStore.removeAll();
	                      }
	                  }
	
	              }
	            );
	
	}
