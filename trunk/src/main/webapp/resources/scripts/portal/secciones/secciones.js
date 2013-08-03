//var helpMap = new Map();

Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";

    /********* Comienza el form ********************************/
    var el_form = new Ext.FormPanel ({
    		id: 'el_formSeccionesId',
    		//el:'formBusqueda',
            renderTo: 'formBusqueda',
	  		title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('el_formSeccionesId', helpMap,'Configuraci&oacute;n de Secciones')+'</span>',
            url : _ACTION_BUSCAR_SECCIONES,
            frame : true,
            width:500,
            bodyStyle : 'padding:5px 5px 0',
            bodyStyle:'background: white',
            buttonAlign: "center",
            labelAlign: 'right',
            waitMsgTarget : true,

			 items:[{
			 	layout:'form',
			 	border: false,
			        items:[{
						   layout:'column',
						   //frame:true,
						   bodyStyle:'background: white',
			        	   html:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
						   items:[{
			     		        layout:'form',
							    border:false,
							    columnWidth: 1,
						   		items:[        			
					                   {
					                   xtype: 'textfield', 
					                   fieldLabel: getLabelFromMap('seccion', helpMap,'Secci&oacute;n'), 
					 			       tooltip: getToolTipFromMap('seccion', helpMap, 'Secci&oacute;n'),
                                       hasHelpIcon:getHelpIconFromMap('seccion',helpMap),								 
                                       Ayuda: getHelpTextFromMap('seccion',helpMap),
					                   id: 'seccion', 
					                   name: 'seccion'
						                }
						               ]
				               }],
				            
			                
			           buttonAlign: 'center',
			           bodyStyle:'background: white',
				
			           buttons: [
			                     {
			  					  text:getLabelFromMap('busquedaSeccionesButtonBuscar', helpMap,'Buscar'),
								  tooltip:getToolTipFromMap('busquedaSeccionesButtonBuscar', helpMap,'Busca Secciones'),
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
			  					  text:getLabelFromMap('cancelarSeccionesButtonBuscar', helpMap,'Cancelar'),
								  tooltip:getToolTipFromMap('cancelarSeccionesButtonBuscar', helpMap,'Cancela la b&uacute;squeda'),
			                     handler: function() {el_form.getForm().reset();}}
			                   ]
          }]
 	}]

            //se definen los campos del formulario
    });
    /********* Fin del form ************************************/

	/********* Comienzo del grid *****************************/
		//Definición Column Model
	    var cm = new Ext.grid.ColumnModel([
	    		{
	    			dataIndex: 'cdSeccion',
	    			hidden: true
	    		},{
				   	header: getLabelFromMap('cmDsSeccionSeccions',helpMap,'Nombre'),
			        tooltip: getToolTipFromMap('cmDsSeccionSeccions', helpMap, 'Nombre'),		           	
		           	//header: "Nombre",
		           	dataIndex: 'dsSeccion',
		           	sortable: true,
		           	width: 240
	        	},{
	    			dataIndex: 'cdBloque',
	    			hidden: true
	    		},{
				   	header: getLabelFromMap('cmDsBloqueSeccions',helpMap,'Bloque'),
			        tooltip: getToolTipFromMap('cmDsBloqueSeccions', helpMap, 'Bloque'),		           	
		           	//header: "Bloque",
		           	dataIndex: 'dsBloque',
		           	sortable: true,
		           	width: 240
	        	}
	           	]);

		//Crea el Store
		function crearGridStore(){
		 			store = new Ext.data.Store({
		    			proxy: new Ext.data.HttpProxy({
						url: _ACTION_BUSCAR_SECCIONES,
						waitMsg: 'Espere por favor....'
		                }),
		                reader: new Ext.data.JsonReader({
		            	root:'listaSecciones',
		            	totalProperty: 'totalCount',
			            successProperty : '@success'
			        },[
			        {name: 'cdSeccion', type: 'string', mapping: 'cdSeccion'},
			        {name: 'dsSeccion',  type: 'string',  mapping:'dsSeccion'},
			        {name: 'cdBloque',  type: 'string',  mapping:'cdBloque'},
			        {name: 'dsBloque',  type: 'string',  mapping:'dsBloque'}
					])
		        });
				return store;
		 	}
		//Fin Crea el Store
	var grilla;

	function createGrid(){
		grilla= new Ext.grid.GridPanel({
			id: 'grilla',
	        el:'gridSecciones',
	        store:crearGridStore(),
	        buttonAlign:'center',
	        bodyStyle:'background: white',
			border:true,
			title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',			
	        cm: cm,
	        loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
			buttons:[
	        		{
	      				text:getLabelFromMap('gridSeccionesButtonAgregar', helpMap,'Agregar'),
	           			tooltip:getToolTipFromMap('gridSeccionesButtonAgregar', helpMap,'Agrega una nueva secci&oacute;n'),			        			
	            		handler:function(){editar()}
	            	},
	            	{
	      				text:getLabelFromMap('gridSeccionesButtonEditar', helpMap,'Editar'),
	           			tooltip:getToolTipFromMap('gridSeccionesButtonEditar', helpMap,'Edita una secci&oacute;n'),			        			
	            		handler:function(){
		            			if(getSelectedRecord()!=null){
		                			editar(getSelectedRecord());
		                		}
		                		else{
		                			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
		                		}
	                	}
	            	},
	            	{
	      				text:getLabelFromMap('gridSeccionesButtonBorrar', helpMap,'Eliminar'),
	           			tooltip:getToolTipFromMap('gridSeccionesButtonBorrar', helpMap,'Elimina una secci&oacute;n'),			        			
	                	handler:function(){
	                			if(getSelectedRecord() != null){
	                					borrar(getSelectedRecord());
	                			}
	                			else{
	                			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
	                			}
	                		}
	            	},
	            	{
	      				text:getLabelFromMap('gridSeccionesButtonExportar', helpMap,'Exportar'),
	           			tooltip:getToolTipFromMap('gridSeccionesButtonExportar', helpMap,'Exporta el contenido del grid'),			        			
	                	handler:function(){
                                 var url = _ACTION_EXPORTAR_SECCIONES + '?seccion=' + el_form.findById("seccion").getValue()
               	 	          showExportDialog( url );
                              }
	            	}
	            	/*{
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
					pageSize:itemsPerPage,
					store: store,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })
			});
	    grilla.render()
	}

	/********* Fin del grid **********************************/

	//Funcion para obtener el registro seleccionado en la grilla
		function getSelectedRecord(){
             var m = grilla.getSelections();
             if (m.length == 1 ) {
                return m[0];
             }
        }

    function agregar(){
    }
	//Borra la alerta seleccionada
	function borrar(record){
		Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminara el registro seleccionado'),function(btn)
			{
         		if (btn == "yes"){
         			var _params = {
         					cdSeccion:record.get('cdSeccion')
         			};
         			execConnection (_BORRAR_SECCION, _params, cbkBorrar);
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
	el_form.render();
	createGrid();
	//Fin Muestra los componentes en pantalla    

});
function reloadGrid(){
	var _params = {
			seccion: Ext.getCmp('seccion').getValue()
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
