function archivosPorMovimientoAll (caso, cdproceso, tarea, movimiento, mensajeEnviarMail){

    var storeArchivos = new Ext.data.Store({
			proxy:  new Ext.data.HttpProxy({url: _ACTION_OBTENER_ARCHIVOS}),
			reader: new Ext.data.JsonReader({
						root:'MArchivosList',
						totalProperty: 'totalCount',
						successProperty : '@success'
					},
					[
						{name: 'tipo',  type: 'string'},
						{name: 'descripcion',  type: 'string'},
						{name: 'dsArchivo',  type: 'string'},
						{name: 'numArchivo', type: 'string'}
					]
			)
	});


    var cm = new Ext.grid.ColumnModel([
				
				{
		           	header: "Nombre",
		           	dataIndex: 'dsArchivo',
		           	sortable: true,
		           	width: 100
				},	
				{
		           	header: "Tipo",
		           	dataIndex: 'tipo',
		           	sortable: true,
		           	width: 100
				},
				{
		           	header: "Descripci&oacute;n",
		           	dataIndex: 'descripcion',
		           	sortable: true,
		           	width: 400
		           	
	    		},
	        	{hidden:true,dataIndex:'dsramo'},
	        	{hidden: true, dataIndex: 'numArchivo'}
				]);

	var grid = new Ext.grid.GridPanel({
			id: 'gridId',
	        store: storeArchivos,
			loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
	        cm: cm,
	        buttonAlign:'center',
			buttons:[	{
   		                    text:getLabelFromMap('btnMovCasoGuardarId',helpMap,'Agregar'),
		                    tooltip: getToolTipFromMap('btnMovCasoGuardarId',helpMap,'Agregar'),
		            		handler:function(){anexarArchivoDigitalizado(Ext.getCmp('numCaso').getValue(), Ext.getCmp('dsMovimiento').getValue())}
		            	},
                        {
   		                    text:getLabelFromMap('btnMovCasoBorrarId',helpMap,'Eliminar'),
		                    tooltip: getToolTipFromMap('btnMovCasoBorrarId',helpMap,'Eliminar'),
		                    handler:function(){
	                			if(getSelectedRecord(grid) != null){
	                					borrar(getSelectedRecord(grid));
	                			}
	                			else{
	                			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
	                			}
	                		}

               
                        },
                        /*{
   		                    text:getLabelFromMap('btnMovCasoEditarId',helpMap,'Editar'),
		                    tooltip: getToolTipFromMap('btnMovCasoEditarId',helpMap,'Editar'),
                            handler:function(){
                            	
                            	if (getSelectedRecord(grid) != null) {
                            		anexarArchivoDigitalizado(Ext.getCmp('numCaso').getValue(), Ext.getCmp('dsMovimiento').getValue(), getSelectedRecord(grid))
                            	} else {
		                			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));			                			
                            	}
                            }
                        },*/
                        {
   		                    text:getLabelFromMap('btnMovCasoEditarId',helpMap,'Consultar'),
		                    tooltip: 'Consultar',//getToolTipFromMap('btnMovCasoEditarId',helpMap,'Consultar'),
                            handler:function(){
                            	var recordDescargar=getSelectedRecord(grid);
                            	if (recordDescargar != null) {
                            		
                            		bajarArchivo(recordDescargar.get('numArchivo'),recordDescargar.get('dsArchivo'));
                            		
                            	} else {
		                			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));			                			
                            	}
                            }
                        },
		            	{
   		                    text:getLabelFromMap('btnMovCasoRegresarId',helpMap,'Regresar'),
		                    tooltip: getToolTipFromMap('btnMovCasoRegresarId',helpMap,'Regresar'),
		            		handler:function(){window.close()}
		            	}
	            	],
	    	width:700,
	    	frame:true,
			height:200,
			sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			bbar: new Ext.PagingToolbar({
					pageSize:itemsPerPage,
					store: storeArchivos,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })
			});

	var formPanel = new Ext.FormPanel({
		    width: 700,
		    store: storeArchivos,
		    bodyStyle:'background: white',
	        buttonAlign: "center",
	        labelAlign: 'right',
	        frame:true,
	        layout: 'table',
            layoutConfig: { columns: 3, columnWidth: .33},
		    url: _ACTION_AGREGAR_ARCHIVO,
		    items: [
		    		{
           			html: '<br/><span class="x-form-item" style="font-weight:bold"></span>',
            		colspan:3
            		},
		            {
           			layout: 'form',
           			labelWidth: 30,
           			items: [
                       {
                           xtype: 'textfield',
                           id: 'numCaso',
                            fieldLabel: getLabelFromMap('numCaso',helpMap,'Caso'),
                            tooltip:getToolTipFromMap('numCaso',helpMap,'Caso'),
                            hasHelpIcon:getHelpIconFromMap('numCaso',helpMap),								 
                            Ayuda: getHelpTextFromMap('numCaso',helpMap),
                            
                            name: 'numCaso',
                            value: caso,
                            disabled: true,
                            width: 100
                     }
           				]
            		},
            		{
           			layout: 'form',
           			labelWidth: 90,
           			items: [
                       {
                           xtype: 'textfield',
                           id: 'txtTareaMovDesId',
                            fieldLabel: getLabelFromMap('txtTareaMovDesId',helpMap,'Tarea'),
                            tooltip:getToolTipFromMap('txtTareaMovDesId',helpMap,'Tarea'),
                            hasHelpIcon:getHelpIconFromMap('txtTareaMovDesId',helpMap),								 
                            Ayuda: getHelpTextFromMap('txtTareaMovDesId',helpMap),
                            
                            name: 'dsTarea',
                            value: tarea,
                            disabled: true,
                            width: 150
                         }
           				]
            		},
                    {
                       layout: 'form',
                       labelWidth: 90,
                       items: [
                              {
                               xtype: 'textfield',
                               id: 'dsMovimiento',
                               fieldLabel: getLabelFromMap('dsMovimiento',helpMap,'Movimiento'),
                               tooltip:getToolTipFromMap('dsMovimiento',helpMap,'Movimiento'),
                               hasHelpIcon:getHelpIconFromMap('dsMovimiento',helpMap),								 
                               Ayuda: getHelpTextFromMap('dsMovimiento',helpMap),
                               name: 'dsMovimiento',
                               value: movimiento,
                               disabled: true,
                               width: 150
                              }
                           ]
                    },
            		{
           			layout: 'form'
            		},
            		{
           			html: '<br/><span class="x-form-item" style="font-weight:bold"></span>',
            		colspan:3
            		}
		           ]
		});

		var window = new Ext.Window ({
			id:'WindArchPmov',
            title: getLabelFromMap('WindArchPmov', helpMap,'Archivos por Movimientos de un Caso'),		    
		    width: 710,
		    modal: true,
		    autoHeight: true,
		    listeners: {
		    
		    		close: function (){
		    		
		    			mensajeEnviarMail(caso, cdproceso);
		    		
		    		}
		    		},
		    items: [formPanel,grid]
		});

		window.show();
		//reloadGrid01();
		recargar();
    
	function recargar(){
	   grid.store.load({
			params:{nmcaso: caso,nmovimiento:movimiento,start:0,limit:20}
		});
	}	
	function borrar(record){
		Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
			{
         		if (btn == "yes"){
         			var _params = {
         				
                		 	nmcaso:     Ext.getCmp('numCaso').getValue(),         
         					nmovimiento:Ext.getCmp('dsMovimiento').getValue(),   
         					nmarchivo:  record.get('numArchivo')                       
         			};
         			execConnection (_ACTION_BORRAR_ARCHIVO_MOVIMIENTO, _params, cbkBorrarMovM);
				}
		})
  	}

	function cbkBorrarMovM (_success, _message) {
		if (!_success) {
			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
		}else {
			Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){ recargar(); } );
             }
		}
	//}
  	
  	
  	function reloadGrid01(){
		var _params = {
        	nmcaso: caso,
        //	nmcaso:numCaso,
        	nmovimiento:Ext.getCmp('dsMovimiento').getValue()
        //	nmovimiento:movimiento
		   };
		  
		   reloadComponentStore(grid, _params, cbkReload01);
     }
     
function cbkReload01 (_r, _options, _success, _store) {

	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	 }else{
 			_store.removeAll();
	       }
    }
    
    function bajarArchivo(numeroArch, nombreArch){
         			
         			//alert("Estamos en la bajada del archivo:  " + record.get('nombre'));
         			var submitForm = getNewSubmitForm();

 					createNewFormElement(submitForm, "pathCompletoArchivoBajada", nombreArch);  //escape( record.get('nombre'))
					 //createNewFormElement(submitForm, "nombredelparam2", "somevalue");
					 submitForm.action= _ACTION_DESCARGAR_ARCHIVO+"?nmcaso=" + caso + "&nmovimiento=" + movimiento + "&nmarchivo="+numeroArch;
					 //envio sincronico
					 submitForm.submit();
					 
	}
	
    
	//helper function to create the form
	function getNewSubmitForm(){
	 var submitForm = document.createElement("FORM");
	 document.body.appendChild(submitForm);
	 submitForm.method = "POST";
	 return submitForm;
	}
	
	//helper function to add elements to the form
	function createNewFormElement(inputForm, elementName, elementValue){
	
	 var newElement = document.createElement("input");
	 newElement.type='hidden';
	 newElement.name = elementName;
	 newElement.value = elementValue;
	 inputForm.appendChild(newElement);
	 return newElement;
	}

		
}


