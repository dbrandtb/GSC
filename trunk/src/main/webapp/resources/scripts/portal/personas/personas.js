
Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";
   var cod;

        var readerTipoPersona = new Ext.data.JsonReader( {
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
        });
		/********* Comienza el form ********************************/

	var el_form = new Ext.FormPanel ({
			renderTo: 'formBusqueda',
	  		title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('incisosFormDescuentoId', helpMap,'Consulta de Personas')+'</span>',
            url : _ACTION_BUSCAR_PERSONAS,
            bodyStyle: 'background: white',
            frame : true,
            width : 600,            
            autoHeight: true,
            waitMsgTarget : true,         
            //baseCls: '', 
			items:[{
			layout:'form',
			bodyStyle:'background:white',			
            labelAlign:'right',
            buttonAlign: 'center',   
			items:[{			
				html: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',            		       	
	            layout: 'column',
	            layoutConfig: {columns: 2, align: 'left'},
	            border:false,
	            items: [
	            			{layout: 'form',
	            			columnWidth: .50,
	            			items: [
	            					{
	            					xtype: 'combo', 
	            					tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
					                store: dsTipoPersonaJ, 
				          			fieldLabel: getLabelFromMap('dsUsuario',helpMap,'Tipo de Persona'),
	                            	tooltip: getToolTipFromMap('dsUsuario',helpMap,'Tipo de Persona'),	
	                            	hasHelpIcon:getHelpIconFromMap('dsUsuario',helpMap),
									Ayuda: getHelpTextFromMap('dsUsuario',helpMap),	                            			          		
					                displayField:'descripcion', 
					                valueField:'codigo', 
					                hiddenName: 'codigoH',
					                labelAlign:'right', 
					                typeAhead: true,
					                mode: 'local', 
					                triggerAction: 'all', 
					                width: 170, 
					                emptyText:'Seleccione Tipo Persona...',
					                //allowBlank: false,
					                blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
					                selectOnFocus:true, 
					                forceSelection:true,
					                id: 'dsUsuario',
			                        listeners: {
		                            		blur: function () {
		                            				if (this.getRawValue() == "") {
		                            					this.setValue();
		                            				}
		                            		}
		                            	}
					                }
	            					]
	            			},
	            			{layout: 'form',
	            			columnWidth: .50,
	            			items: [
	            						{
	            						xtype: 'combo', 
	            						tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
					                    store: dsGrupoCorporativo, 
					          			fieldLabel: getLabelFromMap('dsCliente',helpMap,'Corporativo'),
		                            	tooltip: getToolTipFromMap('dsCliente',helpMap,'Corporativo'),	
		                            	hasHelpIcon:getHelpIconFromMap('dsCliente',helpMap),
										Ayuda: getHelpTextFromMap('dsCliente',helpMap),	      		          		
					                    displayField:'descripcion', 
					                    valueField:'codigo', 
					                    hiddenName: 'codigoGrupo', 
					                    typeAhead: true,
					                    labelAlign:'right',
					                    mode: 'local', 
					                    triggerAction: 'all', 
					                    width: 160, 
					                    emptyText:'Seleccione Corporativo...',
					                    selectOnFocus:true, 
					                    forceSelection:true,
					                    id: 'dsCliente',
					                    listeners: {
			                            		blur: function () {
			                            				if (this.getRawValue() == "") {
			                            					this.setValue("");
			                            				}
				                            		}
				                            	}
					                     }
	            					]
	            			}/*, {
	            				layout: 'form',
	            				columnWidth: .50
	            			}*/
	            			,
	            			{layout: 'form',
	            			columnWidth: .50,
	            			items: [
					                    {
					                    	xtype: 'textfield', 
					                    	fieldLabel: getLabelFromMap('dsNombre', helpMap,'Nombre'), 
	     									tooltip: getToolTipFromMap('dsNombre', helpMap,'Nombre'),
	     									labelAlign:'right', 
	     									hasHelpIcon:getHelpIconFromMap('dsNombre',helpMap),
											Ayuda: getHelpTextFromMap('dsNombre',helpMap),
	     								    width: 160,
	     									
					                    	//fieldLabel: 'Nombre', 
					                    	id: 'dsNombre'
					                    },{
					                    	xtype: 'textfield', 
					                    	fieldLabel: getLabelFromMap('dsRFC', helpMap,'RFC'), 
	     									tooltip: getToolTipFromMap('dsRFC', helpMap,'RFC'),
	     									hasHelpIcon:getHelpIconFromMap('dsRFC',helpMap),
											Ayuda: getHelpTextFromMap('dsRFC',helpMap),	     
	     									labelAlign:'right',  	
	     									width: 160,
					                    	//fieldLabel: 'RFC', 
					                    	id: 'dsRFC'
					                    }
	           					]
	            			}, {
	            				layout: 'form',
	            				columnWidth: .50
	            			}/*,{
	            			html: '<span class="x-form-item" style="font-weight:bold"></span><br>'
	            			}*/
	           	]
            	}],           		
            		buttons: [
            					{
            					text:getLabelFromMap('busquedaPersonasButtonBuscar', helpMap,'Buscar'),
           						tooltip:getToolTipFromMap('busquedaPersonasButtonBuscar', helpMap,'Realiza una b&uacute;squeda de acuerdo a los criterios de selecci&oacute;n'),
           						   
            					//text: 'Buscar', 
            					handler: function () {
												if (el_form.form.isValid()) {
													if (grilla != null) {
														reloadGrid();
													}else {
														createGrid();
													}
												}
												else{
												Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
												}
      										}
            					},
            					{
      							text:getLabelFromMap('busquedaPersonasButtonCancelar', helpMap,'Cancelar'),
           						tooltip:getToolTipFromMap('busquedaPersonasButtonCancelar', helpMap,'Cancela la b&uacute;squeda'),	
           							    
            					//text: 'Cancelar', 
            					handler: function() {
            							el_form.getForm().reset();
            							}
            					}
            				]}]

            //se definen los campos del formulario
	});
	/********* Fin del form ************************************/
	
	/********* Comienzo del grid *****************************/
		//Definición Column Model
	    var cm = new Ext.grid.ColumnModel([
	    		{
	    			dataIndex: 'codigoPersona',
	    			hidden: true
	    		},
	    		{dataIndex: 'otFisJur',
	    			hidden: true
	    		},
				{
				   	header: getLabelFromMap('cmNombrePersonas',helpMap,'Nombre'),
			        tooltip: getToolTipFromMap('cmNombrePersonas', helpMap,'Nombre'),	
			        	    	           	
		           	//header: "Nombre",
		           	dataIndex: 'nombre',
		           	width: 120,
		           	sortable: true
	        	},{
				   	header: getLabelFromMap('cmDsTipPerPersonas',helpMap,'Tipo de Entidad'),
			        tooltip: getToolTipFromMap('cmDsTipPerPersonas', helpMap,'Tipo de Entidad'),
			       
		           	//header: "Tipo de Persona",
		           	dataIndex: 'dsTipPer',
		           	width: 120,
		           	sortable: true
	           	},{
				   	header: getLabelFromMap('cmNmNumNomPersonas',helpMap,'Nro. N&oacute;mina'),
			        tooltip: getToolTipFromMap('cmNmNumNomPersonas', helpMap,'Nro. N&oacute;mina'),
			        
                   	//header: "Nº N&oacute;mina",
		           	dataIndex: 'nmNumNom',
		           	width: 120,
		           	sortable: true
	           	},{
				   	header: getLabelFromMap('cmDsElemenPersonas',helpMap,'Estructura'),
			        tooltip: getToolTipFromMap('cmDsElemenPersonas', helpMap,'Estructura'),
			        
		           	//header: "Corporativo",
		           	dataIndex: 'dsElemen',
		           	width: 120,
		           	sortable: true
	           	},
	           	{
				   	header: getLabelFromMap('cmDsDsctoNombrePersonas',helpMap,'RFC'),
			        tooltip: getToolTipFromMap('cmDsDsctoNombrePersonas', helpMap,'RFC'),
			        
	           		//header: 'RFC',
	           		dataIndex: 'cdRfc',
	           		width: 100,
		           	sortable: true
	           	},{
				   	header: getLabelFromMap('cmDsFisJurPersonas',helpMap,'Tipo de Persona'),
			        tooltip: getToolTipFromMap('cmDsFisJurPersonas', helpMap,'Tipo de Persona'),	
			        	           	
		           	//header: "Tipo de Persona",
		           	dataIndex: 'dsFisJur',
		           	width: 120,
		           	sortable: true
	           	}
	           	]);
	           	
		//Fin Definición Column Model
		//Crea el Store
		 		var	gstore = new Ext.data.Store({
		    			proxy: new Ext.data.HttpProxy({
						url: _ACTION_BUSCAR_PERSONAS
						//waitMsg: 'Espere por favor....'
		                }),
		                reader: new Ext.data.JsonReader({
		            	root:'MListaPersonas',
		            	totalProperty: 'totalCount',
			            successProperty : '@success'
			        },[
			        {name: 'codigoPersona', type: 'string', mapping: 'codigoPersona'},
			        {name: 'cdTipPer',  type: 'string',  mapping:'cdTipPer'},
			        {name: 'nombre',  type: 'string',  mapping:'nombre'},
			        {name: 'dsTipPer',  type: 'string',  mapping:'dsTipPer'},
			        {name: 'dsElemen',  type: 'string',  mapping:'dsElemen'},
			        {name: 'nmNumNom',  type: 'string',  mapping:'nmNumNom'},
			        {name: 'cdRfc',  type: 'string',  mapping:'cdRfc'},
			        {name: 'dsFisJur', type: 'string', mapping:'dsFisJur'},
			        {name: 'otFisJur', type: 'string', mapping:'otFisJur'}
					])
		        });
		//Fin Crea el Store
	var grilla;
	
	function createGrid(){
		grilla= new Ext.grid.GridPanel({
	        el:'gridElementos',
	        store: gstore,
			border:true,
			//autoWidth: true,
			title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
			buttonAlign: 'center',
	        cm: cm,
	        loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
	        stripeRows: true,
	        collapsible: true, 
			buttons:[
		        		{
		      				text:getLabelFromMap('gridPersonasButtonAgregar', helpMap,'Agregar'),
		           			tooltip:getToolTipFromMap('gridPersonasButtonAgregar', helpMap,'Agrega una nueva persona'),		
		           		  	        			
		            		handler:function(){
		            					/*var _combo = Ext.getCmp('dsUsuario');
		            					if (_combo.getValue() != "") {*/
		            						window.location.href = _ACTION_IR_AGREGAR_PERSONA + "?codigoPersona=&codigoTipoPersona=" + Ext.getCmp('dsUsuario').getValue() ;
		            					/*}else {
		            						Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
		            						//Ext.Msg.alert('Aviso', 'Seleccione un tipo de persona');
		            					}*/
		            				}
		            	},
		            	{
		      				text:getLabelFromMap('gridPersonasButtonEditar', helpMap,'Editar'),
		           			tooltip:getToolTipFromMap('gridPersonasButtonEditar', helpMap,'Edita los datos de una persona'),	
		           		  		        			
		            		handler:function(){
		            			//if(grilla.store){	            				
			            			if(getSelectedKey(grilla, "codigoPersona")!=""){
			                			window.location.href = _ACTION_IR_EDITAR_PERSONA + "?codigoPersona=" + getSelectedKey(grilla, "codigoPersona") + "&codigoTipoPersona=" + getSelectedKey(grilla, "otFisJur"), "cod=0";
			                		}
			                		else{
			                			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));			                			
			                		}
			                	/*}else
			                	{
			                	Ext.MessageBox.alert('Aviso','No hay registros para editar');
			                	}*/
		                	}
		            	},
		            	{
		      				text:getLabelFromMap('gridPersonasButtonBorrar', helpMap,'Eliminar'),
		           			tooltip:getToolTipFromMap('gridPersonasButtonBorrar', helpMap,'Elimina los datos de una persona'),	
		           			  		        			
		            		handler:function(){
		            			//if(grilla.store){	            				
			            			if(getSelectedKey(grilla, "codigoPersona")!=""){
			                			borrarPersona (getSelectedKey(grilla, "codigoPersona"));
			                		}
			                		else{
			                			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));			                			
			                		}
			                	/*}else
			                	{
			                	Ext.MessageBox.alert('Aviso','No hay registros para editar');
			                	}*/
		                	}
		            	},
		            	{
		      				text:getLabelFromMap('gridPersonasButtonExportar', helpMap,'Exportar'),
		           			tooltip:getToolTipFromMap('gridPersonasButtonExportar', helpMap,'Exporta datos a otros formatos'),			        			
		            		handler: function (){
				                        var url = _ACTION_EXPORTAR_PERSONAS + '?codTipoPersonaJ=' + el_form.form.findField('dsUsuario').getValue() + '&codCorporativo=' + el_form.form.findField('dsCliente').getValue() +
				                        			'&nombre=' + el_form.form.findField('dsNombre').getValue() + '&rfc=' + el_form.form.findField('dsRFC').getValue();
				                	 	showExportDialog( url );
		            		}

		            	}
	            	],
	    	width:600,
	    	frame:true,
			height:590,
			sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			bbar: new Ext.PagingToolbar({
					pageSize: itemsPerPage,
					store: gstore,
					displayInfo: true,
					displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })
			});
	
	    grilla.render()
	}
	
	function reloadGrid(){
		var _params = {
	    		codTipoPersonaJ: el_form.findById('dsUsuario').getValue(),
	    		codCorporativo: el_form.findById('dsCliente').getValue(),
	    		nombre: el_form.findById('dsNombre').getValue(),
	    		rfc: el_form.findById('dsRFC').getValue()
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
	/********* Fin del grid **********************************/
	el_form.render();
	createGrid();
	dsTipoPersonaJ.load({
		callback:function(){
			dsGrupoCorporativo.load();
		}
	});	

	function getSelectedCodigo(){
              var m = grilla.getSelections();
              var jsonData = "";
              for (var i = 0, len = m.length;i < len; i++) {
                var ss = m[i].get("codigoPersona");
                if (i == 0) {
                jsonData = jsonData + ss;
                } else {
                  jsonData = jsonData + "," + ss;
               }
              }
              return jsonData;
    }
        
        
	function borrarPersona(cdPerson){
		Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminara el registro seleccionado'),function(btn)
			{
         		if (btn == "yes"){
         			var _params = {
         					codigoPersona: cdPerson
         			};
         			execConnection (_ACTION_BORRAR_PERSONA, _params, cbkBorrar);
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

});