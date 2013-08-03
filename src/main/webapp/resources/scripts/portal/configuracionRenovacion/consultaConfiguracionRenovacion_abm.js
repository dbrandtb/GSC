function guardar(_record){
	
		existCustomNum = false;
        update = (_record)?true:false;
    	//CONFIGURACION PROXY PARA LOS COMBOS *********************************	
   		var _proxy_clientesCorpo = new Ext.data.HttpProxy({url: _ACTION_OBTENER_CLIENTES_CORPO});
   		var _proxy_aseguradoras = new Ext.data.HttpProxy({url: _ACTION_OBTENER_ASEGURADORAS});
   		var _proxy_prod_aseg_corpo = new Ext.data.HttpProxy({url: _ACTION_OBTENER_PRODUCTOS_ASEG_CORPO});
   		var _proxy_tipos_renova = new Ext.data.HttpProxy({url: _ACTION_OBTENER_TIPOS_RENOVA});   
   		var _proxy_cont_renova = new Ext.data.HttpProxy({url: _ACTION_COMBO_SINO});

   		//CONFIGURACION READERS PARA LOS COMBOS *****************************
   		var _reader_clientesCorpo = new Ext.data.JsonReader(
	   			{root: 'clientesCorp',totalProperty: 'totalCount',id: 'cdElemento'},
	     		[
	   			{name: 'cdElemento', type: 'string',mapping:'cdElemento'},
	   			{name: 'dsElemen', type: 'string',mapping:'dsElemen'},
	   			{name: 'cdPerson', type: 'string',mapping:'cdPerson'}
	 			]
	 		);
	 	
	 	var _reader_aseguradoras = new Ext.data.JsonReader(
	 			{root: 'aseguradoraComboBox',totalProperty: 'totalCount',id: 'cdUniEco'},
	     		[
    			{name: 'cdUniEco', type: 'string',mapping:'cdUniEco'},
    			{name: 'dsUniEco', type: 'string',mapping:'dsUniEco'}
	 			]
	 		);
	 	
	 	var _reader_prod_aseg_corpo = new Ext.data.JsonReader(
	 			{root: 'productosAseguradoraCliente',totalProperty: 'totalCount',id: 'codigo'},
	     		[
    			{name: 'codigo', type: 'string',mapping:'codigo'},
    			{name: 'descripcion', type: 'string',mapping:'descripcion'}
	 			]
	 		);

	 	var _reader_tipos_renova = new Ext.data.JsonReader(
	 			{root: 'comboTiposRenova',totalProperty: 'totalCount',id: 'id'},
	     		[
    			{name: 'id', type: 'string',mapping:'id'},
    			{name: 'texto', type: 'string',mapping:'texto'}
	 			]
	 		);
	 		
	 	var _reader_cont_num = new Ext.data.JsonReader({
	 			root: 'siNo', 
	 			id: 'id'
	 			},[
    			{name: 'id', type: 'string',mapping:'codigo'},
    			{name: 'texto', type: 'string',mapping:'descripcion'}
	 			]
	 		);
	 	
	 	//DEFINICION DE STORES DE LOS COMBOS *****************************	
    	var dsClientesCorpo = new Ext.data.Store({proxy: _proxy_clientesCorpo,reader: _reader_clientesCorpo});		 	
    	var dsAseguradoras = new Ext.data.Store({proxy: _proxy_aseguradoras,reader: _reader_aseguradoras});
    	var dsProductos = new Ext.data.Store({proxy: _proxy_prod_aseg_corpo,reader: _reader_prod_aseg_corpo});
    	var dsTiposRenova = new Ext.data.Store({proxy: _proxy_tipos_renova,reader: _reader_tipos_renova});
    	var dsContinuarNum = new Ext.data.Store({proxy: _proxy_cont_renova, reader: _reader_cont_num});

		//DEFINICION DE LOS COMBOS ********************************************************
		var comboClientes = new Ext.form.ComboBox({
				                    tpl: '<tpl for="."><div ext:qtip="{cdElemento}.{cdPerson}.{dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
				                    id:'comboClientesId',
				                    store: dsClientesCorpo,
				                    displayField:'dsElemen',
				                    valueField:'cdElemento',
				                    hiddenName: 'cdElemento',
				                    typeAhead: true,
				                    mode: 'local',
				                    triggerAction: 'all',
						            fieldLabel: getLabelFromMap('comboClientesId',helpMap,'Cliente'),
					                tooltip: getToolTipFromMap('comboClientesId',helpMap,'Elija Cliente'),	
                                    hasHelpIcon:getHelpIconFromMap('comboClientesId',helpMap),								 
                                    Ayuda: getHelpTextFromMap('comboClientesId',helpMap),
					                
				                    forceSelection: true,
				                    width: 200,
				                    selectOnFocus:true,
				                    allowBlank : false,
				                    emptyText:'Seleccione un cliente...',
						            onSelect: function (record){
						  						this.setValue(record.get("cdElemento"));
												formWindow.findById("cdPersonId").setValue(record.get("cdPerson"));
						          				comboAseguradoras.setRawValue('');
						          				dsAseguradoras.removeAll();
						          				dsAseguradoras.load({
						          						params: {cdElemento: record.get("cdElemento")}
						          					});
												this.collapse();	
						          		        }
		    					});
		    					
		var cliente = new Ext.form.TextField({
									id:'consConfRenAbmTxtCli',
									name: 'dsElemen',
								    fieldLabel: getLabelFromMap('consConfRenAbmTxtCli',helpMap,'Cliente'),
								    tooltip:getToolTipFromMap('consConfRenAbmTxtCli',helpMap,' Cliente para consulta renovaci&oacute;n'),
                                    hasHelpIcon:getHelpIconFromMap('consConfRenAbmTxtCli',helpMap),								 
                                    Ayuda: getHelpTextFromMap('consConfRenAbmTxtCli',helpMap),
								    
									readOnly: true,
									disabled:true,
									width:200
								});
		var cdElemento = new Ext.form.Hidden({id:'cdElementoId',name: 'cdElemento'});		
		    					
		var comboAseguradoras = new Ext.form.ComboBox({
				                    tpl: '<tpl for="."><div ext:qtip="{cdUniEco}.{dsUniEco}" class="x-combo-list-item">{dsUniEco}</div></tpl>',
				                    id:'comboAseguradorasId',
				                    store: dsAseguradoras,
				                    displayField:'dsUniEco',
				                    valueField:'cdUniEco',
				                    hiddenName: 'cdUniEco',
				                    typeAhead: true,
				                    mode: 'local',
				                    triggerAction: 'all',
						            fieldLabel: getLabelFromMap('comboAseguradorasId',helpMap,'Aseguradora'),
					                tooltip: getToolTipFromMap('comboAseguradorasId',helpMap,'Elija Aseguradora'),	
                                    hasHelpIcon:getHelpIconFromMap('comboAseguradorasId',helpMap),								 
                                    Ayuda: getHelpTextFromMap('comboAseguradorasId',helpMap),
					                
				                    forceSelection: true,
				                    width: 200,
				                    selectOnFocus:true,
				                    allowBlank : false,
				                    emptyText:'Seleccione aseguradora...',
						            onSelect: function (record){
						  						this.setValue(record.get("cdUniEco"));
						  						comboProductos.setRawValue('');
						          				dsProductos.removeAll();
						          				dsProductos.load({
						          						params: {cdElemento: formWindow.findById('comboClientesId').getValue(),
						          								 cdUniEco: record.get("cdUniEco")}
						          					});
												this.collapse();	
						          		        }
		    					});
		
		var aseguradora = new Ext.form.TextField({
									id:'aseguradoraId',
									name: 'dsUniEco',
								    fieldLabel: getLabelFromMap('aseguradoraId',helpMap,'Aseguradora'),
								    tooltip:getToolTipFromMap('aseguradoraId',helpMap,' Aseguradora para consulta renovaci&oacute;n'), 
                                    hasHelpIcon:getHelpIconFromMap('aseguradoraId',helpMap),								 
                                    Ayuda: getHelpTextFromMap('aseguradoraId',helpMap),
								    
									readOnly: true,
									disabled:true,
									width:200
								});
		var cdUniEco = new Ext.form.Hidden({id:'cdUniEcoId',name: 'cdUniEco'});		
		
		var comboProductos = new Ext.form.ComboBox({
				                    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
				                    id:'comboProductosId',
				                    store: dsProductos,
				                    displayField:'descripcion',
				                    valueField:'codigo',
				                    hiddenName: 'cdRamo',
				                    typeAhead: true,
				                    mode: 'local',
				                    triggerAction: 'all',
						            fieldLabel: getLabelFromMap('comboProductosId',helpMap,'Producto'),
					                tooltip: getToolTipFromMap('comboProductosId',helpMap,'Elija Producto'),	
                                    hasHelpIcon:getHelpIconFromMap('comboProductosId',helpMap),								 
                                    Ayuda: getHelpTextFromMap('comboProductosId',helpMap),
					                
				                    forceSelection: true,
				                    width: 200,
				                    selectOnFocus:true,
				                    allowBlank : false,
				                    emptyText:'Seleccione producto...'
		    					});
		    					
		var producto = new Ext.form.TextField({
									id:'productoId',
									name: 'dsRamo',
								    fieldLabel: getLabelFromMap('productoId',helpMap,'Producto'),
								    tooltip:getToolTipFromMap('productoId',helpMap,'Producto para consulta renovaci&oacute;n'), 
                                    hasHelpIcon:getHelpIconFromMap('productoId',helpMap),								 
                                    Ayuda: getHelpTextFromMap('productoId',helpMap),
								    
									readOnly: true,
									disabled:true,
									width:200
								});
		var cdRamo = new Ext.form.Hidden({id:'cdRamoId',name: 'cdRamo'});		
		
		var comboTiposRenova = new Ext.form.ComboBox({
				                    tpl: '<tpl for="."><div ext:qtip="{id}.{texto}" class="x-combo-list-item">{texto}</div></tpl>',
				                    id:'comboTiposRenovaId',
				                    store: dsTiposRenova,
				                    displayField:'texto',
				                    valueField:'id',
				                    hiddenName: 'cdTipoRenova',
				                    typeAhead: true,
				                    mode: 'local',
				                    triggerAction: 'all',
						            fieldLabel: getLabelFromMap('comboTiposRenovaId',helpMap,'Tipo de renovaci&oacute;n'),
					                tooltip: getToolTipFromMap('comboTiposRenovaId',helpMap,'Elija Tipo de renovaci&oacute;n'),	
                                    hasHelpIcon:getHelpIconFromMap('comboTiposRenovaId',helpMap),								 
                                    Ayuda: getHelpTextFromMap('comboTiposRenovaId',helpMap),
					                
				                    forceSelection: true,
				                    width: 200,
				                    selectOnFocus:true,
				                    allowBlank : false,
				                    emptyText:'Seleccione Tipo de Renovacion...'
		    					});

		var diasAnticipacion = new Ext.form.NumberField( {
			        	id:'diasAnticipacionId',
					    fieldLabel: getLabelFromMap('diasAnticipacionId',helpMap,'D&iacute;as de anticipaci&oacute;n'),
					    tooltip:getToolTipFromMap('diasAnticipacionId',helpMap,' D&iacute;as de anticipaci&oacute;n para consulta renovaci&oacute;n'),
                        hasHelpIcon:getHelpIconFromMap('diasAnticipacionId',helpMap),								 
                        Ayuda: getHelpTextFromMap('diasAnticipacionId',helpMap),
		                name : 'cdDiasAnticipacion',
		                allowBlank : false,
		                allowDecimals : false,
		                maxLength: 3,
		                width: 50
		            });
		            
		var continuarNum = new  Ext.form.ComboBox({
			
									tpl: '<tpl for="."><div ext:qtip="{id}.{texto}" class="x-combo-list-item">{texto}</div></tpl>',
				                    id:'comboContinuaNumId',
				                    store: dsContinuarNum,
				                    displayField:'texto',
				                    valueField:'id',
				                    hiddenName: 'continuaNum',
				                    typeAhead: true,
				                    mode: 'local',
				                    triggerAction: 'all',
						            fieldLabel: 'Continuar Numeraci&oacute;n',//getLabelFromMap('comboContNumId',helpMap,'Continuar Numeraci&oacute;n'),
					    			//tooltip:getToolTipFromMap('comboContNumId',helpMap,'¿Desea continuar la numeraci&oacute;n de P&oacute;lizas?'),	
                                    //hasHelpIcon:getHelpIconFromMap('comboContNumId',helpMap),								 
                                    //Ayuda: getHelpTextFromMap('comboContNumId',helpMap),
					                
				                    forceSelection: true,
//				                    width: 150,
//				                    selectOnFocus:true,
				                    allowBlank : false,
				                    emptyText:'Seleccione una opción...',
						            listeners: {
							            select: function (combo, record, index){
							            			//this.setValue(record.get("id"));
							            			
							            			var contNum=record.get("id");
							            			botonNumPol =Ext.getCmp('btnNumPol');
							            			
							  						if(!Ext.isEmpty(contNum))
							  							if(contNum=='S')botonNumPol.setDisabled(true);
							  							else botonNumPol.setDisabled(false);
							  						
							            }          	
						            
						            }	        
					});
					
		var btnVentanaNum = new Ext.Button({
					id: 'btnNumPol',
					text: 'Numeraci&oacute;n de P&oacute;lizas',
					fieldLabel: getLabelFromMap('contNum',helpMap,'Continuar Numeraci&oacute;n'),
					tooltip:getToolTipFromMap('contNum',helpMap,'¿Desea continuar la numeraci&oacute;n de P&oacute;lizas?'),
					disabled: true,
					handler: function (){
							if(Ext.getCmp('windowAgregarNum'))Ext.getCmp('windowAgregarNum').show();
							else {
								(existCustomNum)? editarNum(_store.reader.jsonData.regConfiguraCliente[0].cdElemento, _store.reader.jsonData.regConfiguraCliente[0].cdUniEco, _store.reader.jsonData.regConfiguraCliente[0].cdRamo) : agregarNuevaNum();
							}
					}
		
		});
		            
		//***********    VARIABLE OCULTA PARA ALMACENAR CDPERSON DE CLIENTES_CORPO  **********	
	 	var cdPerson = new Ext.form.Hidden ({
			        	id:'cdPersonId',
		                name:'cdPerson'
		            });
		            
		var cdRenova = new Ext.form.Hidden({id:'cdRenovaId',name: 'cdRenova'});
		if(_record != null){cdRenova.setValue(_record.get('cdRenova'));}
		
		//CONFIGURACION PARA EL GET DEL REGISTRO SELECCIONADO ****************************
		var _proxy = new Ext.data.HttpProxy({url: _ACTION_OBTENER_CONFIGURACION_CLIENTE});			
		var _reader = new Ext.data.JsonReader(
				{root:'regConfiguraCliente', totalProperty: 'totalCount',successProperty: '@success'
	       		},
	       		[
			        {name: 'cdElementoId', mapping: 'cdElemento', type: 'string'},
			        {name: 'dsElemen', mapping: 'dsElemen', type: 'string'},
			        {name: 'cdUniEco', mapping: 'cdUniEco', type: 'string'},
			        {name: 'dsUniEco', mapping: 'dsUniEco', type: 'string'},
			        {name: 'cdRamo', mapping: 'cdRamo', type: 'string'},
			        {name: 'dsRamo', mapping: 'dsRamo', type: 'string'},
			        {name: 'cdTipoRenova', mapping: 'cdTipoRenova', type: 'string'},
			        {name: 'dsTipoRenova', mapping: 'dsTipoRenova', type: 'string'},
			        {name: 'cdDiasAnticipacion', mapping: 'cdDiasAnticipacion', type: 'string'},
			        {name: 'continuaNum', type: 'string',  mapping:'swConNum'}
				]
			);
		
		var _store  = new Ext.data.Store({proxy: _proxy,reader: _reader});		
		
	 var titulo;
	
	if (_record ==undefined)
	   {
	   	titulo='<span style="color:black;font-size:12px;">'+getLabelFromMap('AgregarConfRenov',helpMap,'Agregar Configuraci&oacute;n de Renovaci&oacute;n')+'</span>';
	    }else{
	   	titulo='<span style="color:black;font-size:12px;">'+getLabelFromMap('EditarConfRenov',helpMap,'Editar Configuraci&oacute;n de Renovaci&oacute;n')+'</span>';
	    	
	          }

	          
		//***************  CONFIGURACION PARA EL FORMULARIO   **************************
	          
		var formWindow = new Ext.FormPanel({
		        title: titulo,
		        iconCls:'logo',
		        bodyStyle:'background: white',
  	            waitMsgTarget : true,
		        buttonAlign: "center",
		        labelAlign: 'right',
		        frame:true,
		        url: _ACTION_OBTENER_CONFIGURACION_CLIENTE,
		        reader:_reader,
		        width: 550,
		        height:550,
		        labelWidth:100,
		        items: [
		        		(_record)?cliente:comboClientes,
		        		cdPerson,
   		   				(_record)?aseguradora:comboAseguradoras,
   		   				(_record)?producto:comboProductos,
   		   				comboTiposRenova,
   		   				diasAnticipacion,
   		   				{
   		   					layout:'column',
   		   							items:[
   		   									{
   		   									columnWidth: .60,
   		   									layout:'form',
   		   											items: [continuarNum]
   		   									},
   		   									{
   		   									columnWidth: .40,
   		   									layout:'form',
   		   											items: [btnVentanaNum]
   		   									}
   		   									]
   		   				}
   		   			]
			});	

		 var window = new Ext.Window({
	       	width: 550,
	       	height:360,
	       	minWidth: 300,
	       	modal: true,
	       	minHeight: 100,
	       	layout: 'fit',
	       	plain:true,
	       	bodyStyle:'padding:5px;',
	       	buttonAlign:'center',
	       	items: formWindow,
	        buttons: [{
               text : 'Guardar',
               handler : function(){
               		if(formWindow.form.isValid())
                    { 
	               		if(Ext.getCmp('comboContinuaNumId').getValue()=='N'){
	               			if(Ext.getCmp('panelAgregarNum')){
	               				if(!Ext.getCmp('panelAgregarNum').form.isValid()){
	               					Ext.MessageBox.alert('Aviso','Por favor verif&iacute;que los datos de la nueva Numeraci&oacute;n de P&oacute;lizas');
	               					return;
	               				}else {
	               					//Para la forma de la numeracion de polizas:
	               					var numPolElemen = (_record)? cdElemento.getValue() : comboClientes.getValue();
	               					var numPolUnieco = (_record)? cdUniEco.getValue() : comboAseguradoras.getValue();
	               					var numPolRamo = (_record)? cdRamo.getValue() : comboProductos.getValue();
	               					
	               					var urlNumPol = (_record)? _ACTION_GUARDAR_NUMERO_POLIZA : _ACTION_INSERTAR_NUMERO_POLIZA;

		               					Ext.getCmp('numPolCdElemento').setValue(numPolElemen);
		               					Ext.getCmp('numPolCdUniEco').setValue(numPolUnieco);
		               					Ext.getCmp('numPolCdRamo').setValue(numPolRamo);

	               					
	               					Ext.getCmp('panelAgregarNum').form.submit( {
				                            url : urlNumPol,
				                            success : function(from, action) {
						                                	Ext.getCmp('windowAgregarNum').close();
						                                	
						                                	if(_record){guardar();}
									                   		else{
										                   		formWindow.form.submit({
											                           url: _ACTION_GUARDAR_CONFIGURACION,
											                           success : function(from, action){
											                               Ext.MessageBox.alert('Aviso', action.result.actionMessages[0], function(){reloadGrid();});
											                               window.close();
											                           },
											                           failure: function(form, action){
											                               Ext.MessageBox.alert('Error',action.result.errorMessages[0]);
											                           },
																		//waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
											                            waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')
												                 });
											                 }
				                                },
				                            failure : function(form, action) {
				                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap, 'Error'), action.result.errorMessages[0]);
				                                },
				                            waitTitle: getLabelFromMap('400021', helpMap, 'Espere...'),
				                            waitMsg : 'Guardando la numeraci&oacute;n'//getLabelFromMap('400022', helpMap, getLabelFromMap('', helpMap, 'Guardando Actualizaci&oacute;n de Datos ...'))
				                   });
	               				}
	               			}else {
	               					if(existCustomNum) guardar();
	               					else Ext.MessageBox.alert('Aviso','Por favor agregue la nueva numeraci&oacute;n de P&oacute;lizas');
	               					return;
	               				  }
	               			
	               		}else {
	               		
	               			if(_record){guardar();}
		                   		else{
			                   		formWindow.form.submit({
				                           url: _ACTION_GUARDAR_CONFIGURACION,
				                           success : function(from, action){
				                               Ext.MessageBox.alert('Aviso', action.result.actionMessages[0], function(){reloadGrid();});
				                               window.close();
				                           },
				                           failure: function(form, action){
				                               Ext.MessageBox.alert('Error',action.result.errorMessages[0]);
				                           },
											//waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
				                            waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')
					                 });
				                 }
	               		}
                   }
                   else
                   {
                        Ext.Msg.alert('Aviso', 'Por favor complete la informaci&oacute;n requerida');
                   }
               }
	        },
	           {
	             text : 'Regresar',
	             handler : function(){ if(Ext.getCmp('windowAgregarNum'))Ext.getCmp('windowAgregarNum').close(); window.close(); }
	           }]
	   	});
	   	
	   	existCustomNum = false;
	   	window.show();
	   	
	   	//LOAD DEL FORMULARIO Y DE LOS COMBOS *************************************	   	
		if(_record)
		{
			formWindow.form.load(
					{params:{cdRenova: _record.get('cdRenova')},
					 success: function()
					 {
                        var mStore = _store;
				        mStore.baseParams = mStore.baseParams || {},
						mStore.baseParams['cdRenova'] = _record.get('cdRenova'),
						mStore.reload({
							callback: function(r,options,success)
								{
								if(success)
									{
	                         		Ext.getCmp('cdRenovaId').setValue(_record.get('cdRenova'));
	                         		Ext.getCmp('cdPersonId').setValue(_record.get('cdPerson'));
	                         		Ext.getCmp('cdElementoId').setValue(_store.reader.jsonData.regConfiguraCliente[0].cdElemento);
	                         		Ext.getCmp('cdUniEcoId').setValue(_store.reader.jsonData.regConfiguraCliente[0].cdUniEco);
	                         		Ext.getCmp('cdRamoId').setValue(_store.reader.jsonData.regConfiguraCliente[0].cdRamo);
	                         		Ext.getCmp('comboTiposRenovaId').setValue(_store.reader.jsonData.regConfiguraCliente[0].cdTipoRenova);
	                         		Ext.getCmp('diasAnticipacionId').setValue(_store.reader.jsonData.regConfiguraCliente[0].cdDiasAnticipacion);
	                         		Ext.getCmp('comboContinuaNumId').setValue(_store.reader.jsonData.regConfiguraCliente[0].swConNum);
	                         		
	                         		if(Ext.getCmp('comboContinuaNumId').getValue() == 'N'){
	                         				Ext.getCmp('btnNumPol').setDisabled(false);
	                         				existCustomNum = true;
		                         			
	                         		}
	                         		
									}
								}	
							})
					 }
					});
		}	   	
	   	dsClientesCorpo.load();
	   	dsTiposRenova.load();
	   	dsContinuarNum.load();
	   	
	   	function guardar()
	   	{
	   		
	   		var _params =  {cdRenova: _record.get('cdRenova'),
							cdPerson: Ext.getCmp('cdPersonId').getValue(),
							cdElemento:Ext.getCmp('cdElementoId').getValue(),
							cdUniEco:Ext.getCmp('cdUniEcoId').getValue(),
							cdRamo:Ext.getCmp('cdRamoId').getValue(),
							cdTipoRenova:Ext.getCmp('comboTiposRenovaId').getValue(),
							cdDiasAnticipacion:Ext.getCmp('diasAnticipacionId').getValue(),
							continuaNum:Ext.getCmp('comboContinuaNumId').getValue()
							};
							
				
			execConnection(_ACTION_GUARDAR_CONFIGURACION, _params, cbkSaveConfig);
		}
	
	function cbkSaveConfig(_success, _message)
	{
		if(_success){Ext.Msg.alert('Aviso', _message, function(){reloadGrid(); if(Ext.getCmp('windowAgregarNum'))Ext.getCmp('windowAgregarNum').close(); window.close()});}
		else{Ext.Msg.alert('Error', _message);}
	}		 
};

function borrar(_record) {
               Ext.MessageBox.confirm('Eliminar', 'Se eliminar&aacute; el registro seleccionado', function(btn) {
                   if (btn == "yes") {
                   		var conn = new Ext.data.Connection();
               			conn.request({
						    url: _ACTION_BORRAR_CONFIGURACION_RENOVACION,
						    method: 'POST',
						    params: {"cdRenova": _record.get('cdRenova') 
						    		 },			    		 			 
						    callback: function(options, success, response) {
								if (Ext.util.JSON.decode(response.responseText).success != false) {
									Ext.Msg.alert("Aviso", "Registro eliminado");
									reloadGrid(); 	
								}else {
									Ext.Msg.alert("Error", Ext.util.JSON.decode(response.responseText).errorMessages[0]);
								}
							}
						});						
             		}
              });
}