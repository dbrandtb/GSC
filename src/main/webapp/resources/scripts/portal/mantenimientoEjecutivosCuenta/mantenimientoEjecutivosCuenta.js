Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";

	var persona_store  = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_PERSONAS}),
						reader: new Ext.data.JsonReader({
								root: 'confAlertasAutoClientes',
								successProperty: '@success'
							}, [
								{name: 'codigo', type: 'string', mapping: 'cdPerson'},
								{name: 'descripcion', type: 'string', mapping: 'dsElemen'}
							]),
						remoteSort: true
				});

	var status_store  = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_STATUS}),
						reader: new Ext.data.JsonReader({
								root: 'comboEstadosEjecutivoCuenta',
								successProperty: '@success'
							}, [
								{name: 'codigo', type: 'string', mapping: 'codigo'},
								{name: 'descripcion', type: 'string', mapping: 'descripcion'}
							]),
						remoteSort: true
				});

    /********* Comienza el form ********************************/
    var el_form = new Ext.FormPanel ({
    		id: 'el_form',
    		name: 'el_form',
            renderTo: 'formBusqueda',
	  		title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('el_formMantEjecutivosCta', helpMap,'Mantenimiento de Ejecutivos de Cuenta')+'</span>',
            //title: '<span style="color:black;font-size:14px;">Configuraci&oacute;n de Secciones</span>',
            url : _ACTION_BUSCAR_EJECUTIVOS,
            frame : true,
            iconCls: 'logo',
            //bodyStyle : {padding: '5px 5px 0', background: 'white'},
            bodyStyle:'background: white',
            buttonAlign: "center",
            labelAlign: 'right',
            waitMsgTarget : true,
            //layout: 'form',
	    	width:500,
	    	//height: 300,
	    	border: false,
	    	items: [{
			    	//layout: 'form',
			    	border: false,
		                bodyStyle:'background: white',
		                layout: 'form',
		                baseCls: '',
		                title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br/>',
		                baseCls: '',
		            items: [
		                        {
		                        xtype: 'textfield', 
		                    	fieldLabel: getLabelFromMap('cdEjecutivo', helpMap,'C&oacute;digo'), 
								tooltip: getToolTipFromMap('cdEjecutivo', helpMap, 'C&oacute;digo'),  				                    	
		                        //fieldLabel: 'Seccion', 
		                        id: 'cdEjecutivo', 
		                        maxLength:15,
		                        name: 'cdEjecutivo'
		                        },
					            {   xtype: 'combo', 
					                id: 'cdPersona', 
					                labelWidth: 50, 
					                tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
						            store: persona_store, 
						            fieldLabel: getLabelFromMap('cdPersona',helpMap,'Persona'),
					                tooltip: getToolTipFromMap('cdPersona',helpMap, 'Persona'),			          		
						            displayField:'descripcion', 
						            valueField:'codigo', 
						            hiddenName: 'cdPerson', 
						            typeAhead: true,
						            mode: 'local', 
						            triggerAction: 'all', 
						            width: 200, 
						            emptyText:'Seleccionar Persona...',
						            selectOnFocus:true,
						            forceSelection:true,
			                        listeners: {
		                            		blur: function () {
		                            				if (this.getRawValue() == "") {
		                            					this.setValue();
		                            				}
		                            		}
		                            	}
					           	},
					           	{
					           		xtype: 'datefield',
					           		id: 'fechaInicial',
					           		name: 'fechaInicial',
						            fieldLabel: getLabelFromMap('fechaInicial',helpMap,'Fecha Inicial desde'),
					                tooltip: getToolTipFromMap('fechaInicial',helpMap, 'Fecha Inicial desde'),
					                format: 'd/m/Y'			          		
					           	},
					           	{
					           		xtype: 'datefield',
					           		id: 'fechaFinal',
					           		name: 'fechaFinal',
						            fieldLabel: getLabelFromMap('fechaFinal',helpMap,'Fecha Inicial hasta'),
					                tooltip: getToolTipFromMap('fechaFinal',helpMap, 'Fecha Inicial hasta'),
					                format: 'd/m/Y'
					           	},
					            {   xtype: 'combo', 
					                name: 'status', 
					                labelWidth: 50, 
					                tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
						            store: status_store, 
						            fieldLabel: getLabelFromMap('status',helpMap,'Estatus'),
					                tooltip: getToolTipFromMap('status',helpMap, 'Estatus'),			          		
						            displayField:'descripcion', 
						            valueField:'codigo', 
						            hiddenName: 'status', 
						            typeAhead: true,
						            mode: 'local', 
						            triggerAction: 'all', 
						            width: 200, 
						            emptyText:'Seleccionar Estatus...',
						            selectOnFocus:true,
						            forceSelection:true,
			                        listeners: {
		                            		blur: function () {
		                            				if (this.getRawValue() == "") {
		                            					this.setValue();
		                            				}
		                            		}
		                            	}
					           	}
		                    ],
		            buttonAlign: 'center',
		            buttons: [
		                        {
		     					text:getLabelFromMap('busqueda', helpMap,'Buscar'),
		   						tooltip:getToolTipFromMap('busqueda', helpMap,'Buscar'),
		                        //text: 'Buscar', 
		                        handler: function () {
		                                         if (el_form.form.isValid()) {
		                                         	 if (Ext.getCmp('fechaInicial').getValue() != "" && Ext.getCmp('fechaFinal').getValue() != "") {
			                                         	 if (Ext.getCmp('fechaInicial').getValue() > Ext.getCmp('fechaFinal').getValue()) {
			                                         		Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'), getLabelFromMap('400010', helpMap, 'La fecha de inicio debe ser menor o igual que la de fin'));
			                                         		return;
														 }
		                                         	 }
			                                             if (grilla != null) {
			                                                 reloadGrid();
			                                             }else {
			                                                 createGrid();
			                                             }
		                                         }
				                         }
		                        },
		                        {
		     					text:getLabelFromMap('cancelar', helpMap,'Cancelar'),
		   						tooltip:getToolTipFromMap('cancelar', helpMap,'Cancelar'),
		                        //text: 'Cancelar', 
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
	    			dataIndex: 'cdEjecutivo',
				   	header: getLabelFromMap('cdEjecutivo',helpMap,'C&oacute;digo'),
			        tooltip: getToolTipFromMap('cdEjecutivo', helpMap,'C&oacute;digo'),		           	
		           	sortable: true,
		           	width: 140
	    		},{
				   	header: getLabelFromMap('dsPersona',helpMap, 'Persona'),
			        tooltip: getToolTipFromMap('dsPersona', helpMap,'Persona'),		           	
		           	//header: "Nombre",
		           	dataIndex: 'dsPersona',
		           	sortable: true,
		           	width: 240
	        	},{
	    			dataIndex: 'fechaInicial',
				   	header: getLabelFromMap('fechaInicial',helpMap, 'Fecha Inicial'),
			        tooltip: getToolTipFromMap('fechaInicial', helpMap,'Fecha Inicial'),		           	
		           	//header: "Nombre",
		           	sortable: true,
		           	width: 140
	    		},{
	    			dataIndex: 'fechaFinal',
				   	header: getLabelFromMap('fechaFinal',helpMap, 'Fecha Final'),
			        tooltip: getToolTipFromMap('fechaFinal', helpMap,'Fecha Final'),		           	
		           	sortable: true,
		           	width: 140
	        	},{
	    			dataIndex: 'status',
				   	header: getLabelFromMap('status',helpMap, 'Estatus'),
			        tooltip: getToolTipFromMap('status', helpMap,'Estatus'),		           	
		           	sortable: true,
		           	width: 140
	        	}
	           	]);

		//Crea el Store
		function crearGridStore(){
		 			store = new Ext.data.Store({
		    			proxy: new Ext.data.HttpProxy({
						url: _ACTION_BUSCAR_EJECUTIVOS
		                }),
		                reader: new Ext.data.JsonReader({
		            	root:'listaEjecutivos',
		            	totalProperty: 'totalCount',
			            successProperty : '@success'
			        },[
			        {name: 'cdEjecutivo', type: 'string', mapping: 'cdAgente'},
			        {name: 'dsPersona',  type: 'string',  mapping:'dsPerson'},
			        {name: 'fechaInicial',  type: 'string',  mapping:'feInicio'},
			        {name: 'fechaFinal',  type: 'string',  mapping:'feFin'},
			        {name: 'status',  type: 'string',  mapping:'dsEstado'}
					])
		        });
				return store;
		 	}
		//Fin Crea el Store
	var grilla;

	function createGrid(){
		grilla= new Ext.grid.GridPanel({
			id: 'grilla',
	        title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
	        renderTo:'grid',
	        store:crearGridStore(),
	        buttonAlign:'center',
			border:true,
	        cm: cm,
	        loadMask: {msg: getLabelFromMap('400058',helpMap,'Cargando datos ...'), disabled: false},
			buttons:[
		        		{
		      				text:getLabelFromMap('gridButtonAgregar', helpMap,'Agregar'),
		           			tooltip:getToolTipFromMap('gridButtonAgregar', helpMap,'Agregar un nuevo ejecutivo'),			        			
		        			//text:'Agregar',
		            		//tooltip:'Agregar una nueva seccion',
		            		handler:function(){
		            			agregar();
		            		}
		            	},
		            	{
		      				text:getLabelFromMap('gridButtonEditar', helpMap,'Editar'),
		           			tooltip:getToolTipFromMap('gridButtonEditar', helpMap,'Edita un ejecutivo'),			        			
		            		//text:'Editar',
		            		//tooltip:'Edita una seccion',
		            		handler:function(){
			            			if(getSelectedRecord(grilla)!=null){
			                			editar(getSelectedRecord(grilla));
			                		}
			                		else{
			                			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
			                		}
		                	}
		            	},
		            	{
		      				text:getLabelFromMap('gridButtonBorrar', helpMap,'Eliminar'),
		           			tooltip:getToolTipFromMap('gridButtonBorrar', helpMap,'Elimina un ejecutivo'),			        			
		                	handler:function(){
		                			if(getSelectedRecord(grilla) != null){
		                					borrar(getSelectedRecord(grilla));
		                			}
		                			else{
			                			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
		                			}
		                		}
		            	},
		            	{
		      				text:getLabelFromMap('gridButtonAtributos', helpMap,'Atributos'),
		           			tooltip:getToolTipFromMap('gridButtonAtributos', helpMap,'Atributos del ejecutivo'),			        			
		                	handler:function(){
		                		if (getSelectedRecord(grilla) != null) {
		                			window.location.href = _ACTION_CONFIGURAR_ATRIBUTOS + "?cdEjecutivo=" + getSelectedRecord(grilla).get('cdEjecutivo');
		                		}else {
			                			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
		                		}
                            }
		            	},
		            	{
		      				text:getLabelFromMap('gridButtonExportar', helpMap,'Exportar'),
		           			tooltip:getToolTipFromMap('gridButtonExportar', helpMap,'Exportar la Grilla de B&uacute;squeda'),			        			
		                	handler:function(){
                                  var url = _ACTION_EXPORTAR_EJECUTIVOS + "?cdEjecutivo=" + Ext.getCmp('el_form').form.findField('cdEjecutivo').getValue() +
													"&cdPerson=" + Ext.getCmp('el_form').form.findField('cdPersona').getRawValue() +
													"&fechaInicial=" + Ext.getCmp('el_form').form.findField('fechaInicial').getValue() +
													"&fechaFinal=" + Ext.getCmp('el_form').form.findField('fechaFinal').getValue() +
													"&status=" + Ext.getCmp('el_form').form.findField('status').getValue()
                	 	          showExportDialog( url );
                               }
		            	}/*,
		            	{
		      				text:getLabelFromMap('gridSeccionesButtonRegresar', helpMap,'Regresar'),
		           			tooltip:getToolTipFromMap('gridSeccionesButtonRegresar', helpMap,'Regresa a la pantalla anterior')		        			
		            	}*/
	            	],
	    	width:500,
	    	frame:true,
			height:590,
			sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			bbar: new Ext.PagingToolbar({
					pageSize: itemsPerPage,
					store: store,
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar'),
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })
			});
	    grilla.render()
	}

	/********* Fin del grid **********************************/

	//Borra la alerta seleccionada
	function borrar(record){
		Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
			{
         		if (btn == "yes"){
         			var _params = {
         					codEjecutivo: record.get('cdEjecutivo')
         			};
         			execConnection (_ACTION_BORRAR_EJECUTIVO, _params, cbkBorrar);
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
    //Muestra los componentes en pantalla
    status_store.load({
    		callback: function () {
			    persona_store.load();
    		}
    });
	el_form.render();
	createGrid();
	//Fin Muestra los componentes en pantalla    

});
function reloadGrid(){
	var _params = {
			cdEjecutivo: Ext.getCmp('el_form').form.findField('cdEjecutivo').getValue(),
			cdPerson: Ext.getCmp('el_form').form.findField('cdPersona').getRawValue(),
			fechaInicial: Ext.getCmp('el_form').form.findField('fechaInicial').getRawValue(),
			fechaFinal: Ext.getCmp('el_form').form.findField('fechaFinal').getRawValue(),
			status: Ext.getCmp('el_form').form.findField('status').getValue()
	};
    reloadComponentStore(Ext.getCmp('grilla'), _params, cbkReload);
}
function cbkReload (_r, _o, _success, _store) {
	if (!_success) {
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
		_store.removeAll();
	}
}
