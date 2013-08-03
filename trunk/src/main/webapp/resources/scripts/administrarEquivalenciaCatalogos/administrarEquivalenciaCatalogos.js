var helpMap = new Map();

Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";



    /********* Comienza el form ********************************/
    var el_form = new Ext.FormPanel ({
    		id: 'el_form',
    		name: 'el_form',
            renderTo: 'formBusqueda',
	  		title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('el_formMantEjecutivosCta', helpMap,'Administrar Equivalencia de Cat&aacute;logo')+'</span>',
            //title: '<span style="color:black;font-size:14px;">Configuraci&oacute;n de Secciones</span>',
            //url : _ACTION_BUSCAR_EQUIVALENCIA_CATALOGOS,
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
		                    	fieldLabel: getLabelFromMap('cdPais', helpMap,'Pa&iacute;s'), 
								tooltip: getToolTipFromMap('cdPais', helpMap, 'Pa&iacute;s'),  				                    	
		                        id: 'cdPais', 
		                        name: 'cdPais'
		                        },{
		                        xtype: 'textfield', 
		                    	fieldLabel: getLabelFromMap('cdSistema', helpMap,'C&oacute;digo de Sistema'), 
								tooltip: getToolTipFromMap('cdSistema', helpMap, 'C&oacute;digo de Sistema'),  				                    	
		                        id: 'cdSistema', 
		                        name: 'cdSistema'
		                        },{
		                        xtype: 'textfield', 
		                    	fieldLabel: getLabelFromMap('cdTablaAcw', helpMap,'Tabla AON CatWeb'), 
								tooltip: getToolTipFromMap('cdTablaAcw', helpMap, 'Tabla AON CatWeb'),  				                    	
		                        id: 'cdTablaAcw', 
		                        name: 'cdTablaAcw'
		                        },{
		                        xtype: 'textfield', 
		                    	fieldLabel: getLabelFromMap('cdTablaExt', helpMap,'Sistema Externo'), 
								tooltip: getToolTipFromMap('cdTablaExt', helpMap, 'Sistema Externo'),  				                    	
		                        id: 'cdTablaExt', 
		                        name: 'cdTablaExt'
		                        },{
		                        xtype: 'textfield', 
		                    	fieldLabel: getLabelFromMap('indUso', helpMap,'Uso'), 
								tooltip: getToolTipFromMap('indUso', helpMap, 'Uso'),  				                    	
		                        id: 'indUso', 
		                        name: 'indUso'
		                        },{
		                        xtype: 'textfield', 
		                    	fieldLabel: getLabelFromMap('dsUsoAcw', helpMap,'Descripci&oacute;n'), 
								tooltip: getToolTipFromMap('dsUsoAcw', helpMap, 'Descripci&oacute;n'),  				                    	
		                        id: 'dsUsoAcw', 
		                        name: 'dsUsoAcw'
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
	    			dataIndex: 'countryName',
	    			header: getLabelFromMap('countryName',helpMap,'Pa&iacute;s'),
			        tooltip: getToolTipFromMap('countryName', helpMap,'Pa&iacute;s'),		           	
		           	sortable: true,
		           	width: 100
	    		},{
	    			dataIndex: 'countryCode',
	    			hidden: true          	
		           	
	    		}
	    		,{
				   	dataIndex: 'cdSistema',
				   	header: getLabelFromMap('cdSistema',helpMap, 'C&oacute;digo de Sistema'),
			        tooltip: getToolTipFromMap('cdSistema', helpMap,'C&oacute;digo de Sistema'),		           	
		          	sortable: true,
		           	width: 100
	        	},{
	    			dataIndex: 'cdTablaAcw',
				   	header: getLabelFromMap('cdTablaAcw',helpMap, 'Tabla AON CatWeb'),
			        tooltip: getToolTipFromMap('cdTablaAcw', helpMap,'Tabla AON CatWeb'),		           	
		          	sortable: true,
		           	width: 100
	    		},{
	    			dataIndex: 'cdTablaExt',
				   	header: getLabelFromMap('cdTablaExt',helpMap, 'Sistema Externo'),
			        tooltip: getToolTipFromMap('cdTablaExt', helpMap,'Sistema Externo'),		           	
		           	sortable: true,
		           	width: 100
	        	},{
	    			dataIndex: 'nmTabla',
				   	header: getLabelFromMap('nmTabla',helpMap, 'Nº Tabla'),
			        tooltip: getToolTipFromMap('nmTabla', helpMap,'Nº Tabla'),		           	
   		           	sortable: true,
		           	width: 100
	        	},{
	    			dataIndex: 'indUso',
				   	header: getLabelFromMap('indUso',helpMap, 'Uso'),
			        tooltip: getToolTipFromMap('indUso', helpMap,'Uso'),		           	
		           	sortable: true,
		           	width: 100
	        	},{
	    			dataIndex: 'dsUsoAcw',
				   	header: getLabelFromMap('dsUsoAcw',helpMap, 'Descripci&oacute;n'),
			        tooltip: getToolTipFromMap('dsUsoAcw', helpMap,'Descripci&oacute;n'),		           	
		           	sortable: true,
		           	width: 100
	        	},{
	    			dataIndex: 'nmColumna',
				   	header: getLabelFromMap('nmColumna',helpMap, 'Columnas'),
			        tooltip: getToolTipFromMap('nmColumna', helpMap,'Columnas'),		           	
		           	sortable: true,
		           	width: 100
	        	}
	        	
	           	]);

		//Crea el Store
		function crearGridStore(){
		 			store = new Ext.data.Store({
		    			proxy: new Ext.data.HttpProxy({
						url: _ACTION_BUSCAR_EQUIVALENCIA_CATALOGOS
		                }),
		                reader: new Ext.data.JsonReader({
		            	root:'listaEquivalenciaCatalogos',
		            	totalProperty: 'totalCount',
			            successProperty : '@success'
			        },[
			        {name: 'countryName', type: 'string', mapping: 'countryName'},
			        {name: 'cdSistema',  type: 'string',  mapping:'cdSistema'},
			        {name: 'cdTablaAcw',  type: 'string',  mapping:'cdTablaAcw'},
			        {name: 'cdTablaExt',  type: 'string',  mapping:'cdTablaExt'},
			        {name: 'dsUsoAcw',  type: 'string',  mapping:'dsUsoAcw'},
    			    {name: 'indUso',  type: 'string',  mapping:'indUso'},
					{name: 'nmColumna',  type: 'string',  mapping:'nmColumna'},
					{name: 'nmTabla',  type: 'string',  mapping:'nmTabla'}, 
					{name: 'countryCode',  type: 'string',  mapping:'countryCode'}
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
	        renderTo:'gridSecciones',
	        store:crearGridStore(),
	        buttonAlign:'center',
			border:true,
	        cm: cm,
	        loadMask: {msg: getLabelFromMap('400058',helpMap,'Cargando datos ...'), disabled: false},
			buttons:[
		        		{
		      				text:getLabelFromMap('gridButtonAgregar', helpMap,'Agregar'),
		           			tooltip:getToolTipFromMap('gridButtonAgregar', helpMap,'Agregar una Equivalencia de Cat&aacute;logo'),			        			
		        			//text:'Agregar',
		            		//tooltip:'Agregar una nueva seccion',
		            		handler:function(){
		            			insertarOActualizarEquivalencia();
		            		}
		            	},
		            	{
		      				text:getLabelFromMap('gridButtonEditar', helpMap,'Editar'),
		           			tooltip:getToolTipFromMap('gridButtonEditar', helpMap,'Edita una Equivalencia de Cat&aacute;logo'),			        			
		            		//text:'Editar',
		            		//tooltip:'Edita una seccion',
		            		handler:function(){
			            			if(getSelectedRecord(grilla)!=null){
			                			insertarOActualizarEquivalencia(getSelectedRecord(grilla));
			                		}
			                		else{
			                			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
			                		}
		                	}
		            	},
		            	{
		      				text:getLabelFromMap('gridButtonBorrar', helpMap,'Eliminar'),
		           			tooltip:getToolTipFromMap('gridButtonBorrar', helpMap,'Elimina Equivalencia de Cat&aacute;logo'),			        			
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
		      				text:getLabelFromMap('gridButtonSistemaExterno', helpMap,'Sistema Externo'),
		           			tooltip:getToolTipFromMap('gridButtonSistemaExterno', helpMap,'Sistema Externo'),			        			
		                	handler:function(){
                    			if (getSelectedRecord(grilla)) {
                    				
                        			window.location=_ACTION_IR_ADMINISTRAR_CATALOG_SISTEMA_EXTERNO+"?cdPais="+getSelectedRecord(grilla).get("countryCode")+"&nmTabla="+getSelectedRecord(grilla).get("nmTabla")+"&cdSistema="+getSelectedRecord(grilla).get("cdSistema")+"&cdTablaExt="+getSelectedRecord(grilla).get("cdTablaExt")+"&nmColumna="+getSelectedRecord(grilla).get("nmColumna");
                    			} else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));}
                			}
		            	},
		            	{
		      				text:getLabelFromMap('gridButtonExportar', helpMap,'Exportar'),
		           			tooltip:getToolTipFromMap('gridButtonExportar', helpMap,'Exportar la Grilla de B&uacute;squeda'),			        			
		                	handler:function(){
                                 var url = _ACTION_EXPORTAR_EQUIVALENCIA_CATALOGOS + "?cdPais=" + Ext.getCmp('el_form').form.findField('cdPais').getValue() +
													"&cdSistema=" + Ext.getCmp('el_form').form.findField('cdSistema').getValue() +
													"&cdTablaAcw=" + Ext.getCmp('el_form').form.findField('cdTablaAcw').getValue() +
													"&cdTablaExt=" + Ext.getCmp('el_form').form.findField('cdTablaExt').getValue() +
													"&indUso=" + Ext.getCmp('el_form').form.findField('indUso').getValue() +
													"&dsUsoAcw=" + Ext.getCmp('el_form').form.findField('dsUsoAcw').getValue()
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
			height:320,
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
         					cdPais: record.get('countryCode'),
         					cdSistema: record.get('cdSistema'),
         					nmTabla: record.get('nmTabla')
         			};
         			execConnection (_ACTION_BORRAR_EQUIVALENCIA_CATALOGO, _params, cbkBorrar);
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
/*    status_store.load({
    		callback: function () {
			    persona_store.load();
    		}
    });*/
	el_form.render();
	createGrid();
	//Fin Muestra los componentes en pantalla    

});
function reloadGrid(){
	var _params = {
			cdPais: Ext.getCmp('el_form').form.findField('cdPais').getValue(),
			cdSistema: Ext.getCmp('el_form').form.findField('cdSistema').getValue(),
			cdTablaAcw: Ext.getCmp('el_form').form.findField('cdTablaAcw').getValue(),
			cdTablaExt: Ext.getCmp('el_form').form.findField('cdTablaExt').getValue(),
			indUso: Ext.getCmp('el_form').form.findField('indUso').getValue(),
			dsUsoAcw: Ext.getCmp('el_form').form.findField('dsUsoAcw').getValue()
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
