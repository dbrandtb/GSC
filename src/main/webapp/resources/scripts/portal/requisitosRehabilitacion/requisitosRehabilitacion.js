//var helpMap = new Map();

Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

	    var cm = new Ext.grid.ColumnModel([
        
        {
		   	header: getLabelFromMap('cmDsClienteRqstRhbltcn',helpMap,'Cliente'),
	        tooltip: getToolTipFromMap('cmDsClienteRqstRhbltcn', helpMap,'Columna Cliente'),	
	        hasHelpIcon:getHelpIconFromMap('cmDsClienteRqstRhbltcn',helpMap), 
		    Ayuda: getHelpTextFromMap('cmDsClienteRqstRhbltcn',helpMap,''),			           	
	        //header: "Cliente",
	        dataIndex: 'dsElemen',
	        sortable: true,
	        width: 120
        },{
		   	header: getLabelFromMap('cmDsAseguradoraRqstRhbltcn',helpMap,'Aseguradora'),
	        tooltip: getToolTipFromMap('cmDsAseguradoraRqstRhbltcn', helpMap,'Columna Aseguradora'),	
	        hasHelpIcon:getHelpIconFromMap('cmDsAseguradoraRqstRhbltcn',helpMap), 
		    Ayuda: getHelpTextFromMap('cmDsAseguradoraRqstRhbltcn',helpMap,''),			   	           	
	        //header: "Aseguradora",
	        dataIndex: 'dsUnieco',
	        sortable: true,
	        width: 120
        },{
		   	header: getLabelFromMap('cmDsProductoRqstRhbltcn',helpMap,'Producto'),
	        tooltip: getToolTipFromMap('cmDsProductoRqstRhbltcn', helpMap,'Columna Producto'),		
	        hasHelpIcon:getHelpIconFromMap('cmDsProductoRqstRhbltcn',helpMap), 
		    Ayuda: getHelpTextFromMap('cmDsProductoRqstRhbltcn',helpMap,''),			              	
	        //header: "Producto",
	        dataIndex: 'dsRamo',
	        sortable: true,
	        width: 120
	        
        },{
		   	header: getLabelFromMap('cmDsDescriRqstRhbltcn',helpMap,'Descripci&oacute;n'),
	        tooltip: getToolTipFromMap('cmDsDescriRqstRhbltcn', helpMap,'Columna Descripci&oacute;n'),	
	        hasHelpIcon:getHelpIconFromMap('cmDsDescriRqstRhbltcn',helpMap), 
		    Ayuda: getHelpTextFromMap('cmDsDescriRqstRhbltcn',helpMap,''),			   	           	
	        //header: "Descripcion",
	        dataIndex: 'dsRequisito',
	        sortable: true,
	        width: 120
	     },{
	        header: "cdRequisito",
	        dataIndex: 'cdRequisito',
	        hidden:true
        },{
	        header: "cdElemento",
	        dataIndex: 'cdElemento',
	        hidden:true
       },{
	        header: "cdPerson",
	        dataIndex: 'cdPerson',
	        hidden:true
	    },{
	        header: "cdUnieco",
	        dataIndex: 'cdUnieco',
	        hidden:true
	    },{
	        header: "cdDocXcta",
	        dataIndex: 'cdDocXcta',
	        hidden:true
       }]);

    var dsElemen = new Ext.form.TextField({
        id: 'clienteId',
       	fieldLabel: getLabelFromMap('clienteId', helpMap,'Cliente'), 
		tooltip: getToolTipFromMap('clienteId', helpMap,'Texto de Cliente'), 
		hasHelpIcon:getHelpIconFromMap('clienteId',helpMap), 
		Ayuda: getHelpTextFromMap('clienteId',helpMap,''),				                    	
        //fieldLabel: 'Cliente',
        name:'dsElemen',
        allowBlank: true,
        anchor: '100%'
       
    });

    var dsRamo = new Ext.form.TextField({
        id: 'productoId',
       	fieldLabel: getLabelFromMap('productoId', helpMap,'Producto'), 
		tooltip: getToolTipFromMap('productoId', helpMap,'Texto de Producto'),  
		hasHelpIcon:getHelpIconFromMap('productoId',helpMap), 
		Ayuda: getHelpTextFromMap('productoId',helpMap,''),						                    	
        //fieldLabel: 'Producto',
        name:'dsRamo',
        allowBlank: true,
        anchor: '100%'
    });

   var dsUniEco = new Ext.form.TextField({
        id:'aseguradoraId',
       	fieldLabel: getLabelFromMap('aseguradoraId', helpMap,'Aseguradora'), 
		tooltip: getToolTipFromMap('aseguradoraId', helpMap,'Texto de Aseguradora'), 
        hasHelpIcon:getHelpIconFromMap('aseguradoraId',helpMap), 
		Ayuda: getHelpTextFromMap('aseguradoraId',helpMap,''),					                    	
        //fieldLabel: 'Aseguradora',
        name:'dsUniEco',
        allowBlank: true,
        anchor: '100%'
    });
    
 function test(){
             store = new Ext.data.Store({
    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_BUSCAR_REQUISITOS_REHABILITACION
                }),
                //proxy: proxyUrl,
                reader: new Ext.data.JsonReader({
            	root:'MEstructuraList',
            	totalProperty: 'totalCount',
            	successProperty : '@success'
	        },[
	        {name: 'cdRequisito',  type: 'string',  mapping:'cdRequisito'},
	        {name: 'cdPerson',  type: 'string',  mapping:'cdPerson'},
	        {name: 'cdUnieco',  type: 'string',  mapping:'cdUnieco'},
	        {name: 'dsUnieco',  type: 'string',  mapping:'dsUnieco'},
	        {name: 'cdRamo',  type: 'string',  mapping:'cdRamo'},
	        {name: 'dsRamo',  type: 'string',  mapping:'dsRamo'},
	        {name: 'dsRequisito',  type: 'string',  mapping:'dsRequisito'},
	        {name: 'cdElemento',  type: 'string',  mapping:'cdElemento'},
	        {name: 'dsElemen',  type: 'string',  mapping:'dsElemen'},
	        {name: 'cdDocXcta',  type: 'string',  mapping:'cdDocXcta'}
	    	])
        });
       return store;
 	}


var grid2;

function createGrid(){
	grid2= new Ext.grid.GridPanel({
		id: 'grid2',
        el:'gridRequisitos',
        title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
        store:test(),
		border:true,
		buttonAlign:'center',
        cm: cm,
        loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
		buttons:[
        		{
        		id:'btnAgregarId',
   				text:getLabelFromMap('btnAgregarId', helpMap,'Agregar'),
       			tooltip:getToolTipFromMap('btnAgregarId', helpMap,'Agrega un nuevo Requisitos de Rehabilitaci&oacute;n'),
       				        			
            	handler:function(){
                agregar();                
                }
            	},{
            	id:'btnEditarId',
   				text:getLabelFromMap('btnEditarId', helpMap,'Editar'),
       			tooltip:getToolTipFromMap('btnEditarId', helpMap,'Edita un Requisito de Rehabilitaci&oacute;n'),
       						        			
            	handler:function(){
                    if (getSelectedKey(grid2, "cdElemento") != "" && getSelectedKey(grid2, "cveAgrupa") != "" && getSelectedKey(grid2, "cveProducto") != "") {
                        editar(getSelectedRecord(grid2));                        
                    } else {
                      Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                    }
                }
            	},{
            	id:'btnBorrarId',
   				text:getLabelFromMap('btnBorrarId', helpMap,'Eliminar'),
       			tooltip:getToolTipFromMap('btnBorrarId', helpMap,'Elimina un Requisito de Rehabilitaci&oacute;n'),	
       					        			
            	handler:function(){
                    if (getSelectedKey(grid2, "cdTramo") != "" && getSelectedKey(grid2, "cdElemento") != "" && getSelectedKey(grid2, "cveAgrupa") != "" && getSelectedKey(grid2, "cveProducto") != "") {
                        borrar(getSelectedRecord(grid2));                        
                    } else {
                      Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                    }
                }
            	},{
   				text:getLabelFromMap('btnExportarId', helpMap,'Exportar'),
       			tooltip:getToolTipFromMap('btnExportarId', helpMap,'Exporta la b&uacute;squeda de Requisitos de Rehabilitaci&oacute;n'),
       			        			
                handler:function(){
                		var url = _ACTION_EXPORTAR_REQUISITOS_REHABILITACION + '?dsElemen=' + dsElemen.getValue()+ '&dsRamo=' + dsRamo.getValue()+ '&dsUniEco=' + dsUniEco.getValue();
                	 	showExportDialog( url );
                	 }
            	}/*,
            	{
   				text:getLabelFromMap('gridRqstRhbltcnButtonRegresar', helpMap,'Regresar'),
       			tooltip:getToolTipFromMap('gridRqstRhbltcnButtonRegresar', helpMap,'Regresa a la pantalla anterior')			        			
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
				displayInfo: true,
				displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
				emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
		    })
		});
    grid2.render()
}

    var incisosForm = new Ext.form.FormPanel({
    	id: 'incisosForm',
        el:'formBusqueda',
		title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('incisosForm', helpMap,'Requisitos de Rehabilitaci&oacute;n')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,
        url:_ACTION_BUSCAR_REQUISITOS_REHABILITACION,
        width: 500,
        height:180,
        items: [{
        		layout:'form',
				border: false,
				items:[{
	        		bodyStyle:'background: white',
			        labelWidth: 100,
	                layout: 'form',
					frame:true,
			       	baseCls: '',
			       	buttonAlign: "center",
        		        items: [{
        		        	layout:'column',
        		        	labelAlign:'right',
		 				    border:false,
			                html:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
		 				    columnWidth: 1,
        		    		items:[
        		    			{
								columnWidth:.1,
                				layout: 'form',
                				html:'&nbsp;'
                				},
        		    			{
						    	columnWidth:.6,
                				layout: 'form',
		                		border: false,
        		        		items:[
        		        				dsElemen,
        		        				dsRamo,
        		        				dsUniEco
		       						]
								},{
								columnWidth:.3,
                				layout: 'form'
                				}]
                			}],
                			buttons:[{
        							text:getLabelFromMap('btnBuscarId', helpMap,'Buscar'),
       								tooltip:getToolTipFromMap('btnBuscarId', helpMap,'Busca un requisito de rehabilitac&iacute;on'),
       									
       										        			
        							handler: function() {
				               			if (incisosForm.form.isValid()) {
                                               if (grid2!=null) {
                                                reloadGrid(grid2);
                                               } else {
                                                createGrid(grid2);
                                               }
                						} else{
											Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
										}
									}
        							},{
        							text:getLabelFromMap('btnCancelarId', helpMap,'Cancelar'),
       								tooltip:getToolTipFromMap('btnCancelarId', helpMap,'Cancela la b&uacute;squeda'),
       								
       										        			
        							handler: function() {
        								incisosForm.form.reset();
        							}
        						}]
        					}]
        				}]
				});

		incisosForm.render();
        createGrid();
        function toggleDetails(btn, pressed){
        var view = grid.getView();
        view.showPreview = pressed;
        view.refresh();
    	}
var store;


function borrar(record) {
        if(record.get('cdRequisito') != "" && record.get('cdUnieco') != "" && record.get('cdRamo') != "")
        {
            Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
            {
                if (btn == "yes")
                {
					var _params = {
                          cdRequisito: record.get('cdRequisito'),
		                  cdUnieco: record.get('cdUnieco'),
		                  cdRamo: record.get('cdRamo'),
		                  cdElemento: record.get('cdElemento'),
		                  cdDocXcta: record.get('cdDocXcta')
					};
					execConnection(_ACTION_BORRAR_REQUISITOS_REHABILITACION, _params, cbkBorrar);
                    /*var conn = new Ext.data.Connection ();
                    conn.request ({
                        url: _ACTION_BORRAR_REQUISITOS_REHABILITACION,
                        method: 'POST',
                        successProperty : '@success',
                       	params:{
                                  cdRequisito: record.get('cdRequisito'),
				                  cdUnieco: record.get('cdUnieco'),
				                  cdRamo: record.get('cdRamo'),
				                  cdElemento: record.get('cdElemento'),
				                  cdDocXcta: record.get('cdDocXcta')
				               },
					    					
                        waitMsg: 'Espere por favor...',
                                          callback: function (options, success, response) {
                                                      if (Ext.util.JSON.decode(response.responseText).success == false) {
                                                          Ext.Msg.alert('Error', 'Problemas al eliminar: '+ Ext.util.JSON.decode(response.responseText).errorMessages[0]);
                                                      } else {
                                                          Ext.Msg.alert('Confirmacion', 'Requisito de Rehabilitacion eliminado');
                                                          reloadGrid();
                                                      }
                                                  },
                           waitMsg: 'Espere por favor....'
                       });*/                               
               }
               //reloadGrid();
            })
        }else{
               Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
               //Ext.Msg.alert('Advertencia', 'Debe seleccionar un Requisito de Rehabilitacion para borrar');
    	}
};
    function cbkBorrar (_success, _message) {
    	if (!_success) {
    		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
    		//Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Aviso'),getLabelFromMap('400058', helpMap,'Descuento Eliminado'));
    	}else {
    		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function() {reloadGrid();});
    		
    	}
    }    
        
// Funcion de Agregar requisito de rehabilitacion

function agregar() {
    
   var dsClientesCorp = new Ext.data.Store({
           proxy: new Ext.data.HttpProxy({
               url: _ACTION_OBTENER_CLIENTES_CORPORATIVO
           }),
           reader: new Ext.data.JsonReader({
           root: 'clientesCorp',
           id: 'clientesCorps'
           },[
           {name: 'cdElemento', type: 'string',mapping:'cdElemento'},
           {name: 'cdPerson', type: 'string',mapping:'cdPerson'},
           {name: 'dsElemen', type: 'string',mapping:'dsElemen'}
       ])
       });

     var dsAseguradora = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_ASEGURADORAS_CLIENTE}),
						reader: new Ext.data.JsonReader({
								root: 'aseguradoraComboBox',
								id: 'cdUniEco',
								successProperty: '@success'
							}, [
								{name: 'cdUniEco', type: 'string', mapping: 'cdUniEco'},
								{name: 'dsUniEco', type: 'string', mapping: 'dsUniEco'} 
							]),
						remoteSort: true
				});

    var dsProductos = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_PRODUCTOS_ASEGURADORA_CLIENTE
            }),
            reader: new Ext.data.JsonReader({
            root: 'productosAseguradoraCliente',
            id: 'codigo'
            },[
           {name: 'codigo', type: 'string',mapping:'codigo'},
           {name: 'descripcion', type: 'string',mapping:'descripcion'}
        ])
    });
    
     var dsTipoDocumento = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_TIPOS_DOCUMENTO
            }),
            reader: new Ext.data.JsonReader({
            root: 'comboTiposDocumento'
            },[
           {name: 'cdDocXcta', type: 'string',mapping:'cdDocXcta'},
           {name: 'dsFormato', type: 'string',mapping:'dsFormato'}
        ])
    });

        //se define el formulario
        var formPanel = new Ext.FormPanel( {
            labelWidth : 100,
            url : _ACTION_INSERTAR_GUARDAR_REQUISITOS_REHABILITACION,
            frame : true,
            renderTo: Ext.get('formulario'),
            bodyStyle : 'padding:5px 5px 0',
            width : 350,
            labelAlign:'right',
            bodyStyle:'background: white',
            waitMsgTarget : true,
            defaults : {
            width:200                         //230
            },
            defaultType : 'textfield',
            //se definen los campos del formulario
           items : [
                {
                    xtype: 'hidden',
                    name : 'cdPerson',
                    id: 'cdPerson'
                },
                {
                    xtype: 'combo',
                    labelWidth: 50,
                    tpl: '<tpl for="."><div ext:qtip="{cdElemento}.{cdPerson}.{dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
                    store: dsClientesCorp,
		            fieldLabel: getLabelFromMap('agrClienteCombo',helpMap,''),
               		tooltip: getToolTipFromMap('agrClienteCombo',helpMap,'Combo de Cliente'),			
               		hasHelpIcon:getHelpIconFromMap('agrClienteCombo',helpMap), 
		            Ayuda: getHelpTextFromMap('agrClienteCombo',helpMap,''),	          		
                    displayField:'dsElemen',
                    valueField: 'cdElemento',
                    hiddenName: 'cdElemento',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    //fieldLabel: "Cliente",
                    width: 200,
                    emptyText:'Seleccione Cliente ...',
                    selectOnFocus:true,
                    forceSelection:true,
                    //labelSeparator:'',
                    allowBlank : false,
                    id:'cdElementoId',
                    onSelect: function (record) {
                        formPanel.findById(('cdPerson')).setValue(record.get("cdPerson"));
                        formPanel.findById(('cveProductoId')).setValue('');
                        dsProductos.removeAll();
	                    dsProductos.load({
	                                    	params: {cdElemento: record.get("cdElemento") ,cdunieco: formPanel.findById(('cveAseguradoraId')).getValue()},
	                         	            waitMsg: 'Espere por favor....'
	                            		 });
	                    formPanel.findById(('cveAseguradoraId')).setValue('');
                        dsAseguradora.removeAll();
                        dsAseguradora.load({
	                                    	params: {cdElemento: record.get("cdElemento")},
	                         	            waitMsg: 'Espere por favor....'
	                            		 });
                        this.collapse();
                        this.setValue(record.get("cdElemento"));
                        }
                },
                {
                    xtype: 'combo', 
                    labelWidth: 70, 
                    tpl: '<tpl for="."><div ext:qtip="{cdUniEco}. {dsUniEco}" class="x-combo-list-item">{dsUniEco}</div></tpl>',
	                store: dsAseguradora,
		            fieldLabel: getLabelFromMap('agrAseguradoraCombo',helpMap,'Aseguradora'),
               		tooltip: getToolTipFromMap('agrAseguradoraCombo',helpMap,'Combo de Aseguradora'),	
               		hasHelpIcon:getHelpIconFromMap('agrAseguradoraCombo',helpMap), 
		            Ayuda: getHelpTextFromMap('agrAseguradoraCombo',helpMap,''),	               			          		
	                displayField:'dsUniEco', 
	                valueField:'cdUniEco', 
	                hiddenName: 'cdUnieco', 
	                typeAhead: true,
	                mode: 'local', 
	                triggerAction: 'all', 
	                //fieldLabel: "Aseguradora", 
	                width: 200, 
	                emptyText:'Seleccione Aseguradora...',
	                selectOnFocus:true, 
	                forceSelection:true,
	                //labelSeparator:'', 
	                allowBlank : false,
	                id: 'cveAseguradoraId',
	                onSelect: function (record) {
	                            				dsProductos.removeAll();
	                            				dsProductos.load({
	                            						params: {cdElemento: formPanel.findById(('cdElementoId')).getValue() ,cdunieco: record.get('cdUniEco')}
	                            					});
	                            				formPanel.findById("cveProductoId").setValue('');
	                            				this.setValue(record.get('cdUniEco'));
	                            				this.collapse();	
	                            		        }
	             },
	             {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                    store: dsProductos,
		            fieldLabel: getLabelFromMap('agrRqstRhbltcnComboCveProducto',helpMap,'Producto'),
               		tooltip: getToolTipFromMap('agrRqstRhbltcnComboCveProducto',helpMap,'Combo de Producto'),		
               		hasHelpIcon:getHelpIconFromMap('agrRqstRhbltcnComboCveProducto',helpMap), 
		            Ayuda: getHelpTextFromMap('agrRqstRhbltcnComboCveProducto',helpMap,''),	   	          		
                    displayField:'descripcion',
                    valueField:'codigo',
                    hiddenName: 'cdRamo',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    //fieldLabel: "Producto",
                    width: 200,                          //300
                    emptyText:'Seleccione Producto...',
                    selectOnFocus:true,
                    forceSelection:true,
                    //labelSeparator:'',
                    allowBlank : false,
                    id:'cveProductoId',
	                onSelect: function (record) {
	                            				dsTipoDocumento.removeAll();
	                            				dsTipoDocumento.load({
	                            						params: {cdElemento: formPanel.findById(('cdElementoId')).getValue() ,cdUniEco: formPanel.findById(('cveAseguradoraId')).getValue(),cdRamo: record.get('codigo')}
	                            					});
	                            				formPanel.findById("cdDocXctaId").setValue('');
	                            				this.setValue(record.get('codigo'));
	                            				this.collapse();	
	                            		        }
                },
                {
                xtype: 'textfield', 
		       	fieldLabel: getLabelFromMap('agrTxtNombre', helpMap,'Nombre'), 
				tooltip: getToolTipFromMap('agrTxtNombre', helpMap,'Nombre'), 
				hasHelpIcon:getHelpIconFromMap('agrTxtNombre',helpMap), 
		            Ayuda: getHelpTextFromMap('agrTxtNombre',helpMap,''),			                    	
                //fieldLabel: "Nombre", 
                name: 'dsRequisito', 
                id: 'dsRequisitoId'
                },
                {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{cdDocXcta}. {dsFormato}" class="x-combo-list-item">{dsFormato}</div></tpl>',
                    store: dsTipoDocumento,
		            fieldLabel: getLabelFromMap('agrRqstRhbltcnComboCdDocXcta',helpMap,'Tipo Documento'),
               		tooltip: getToolTipFromMap('agrRqstRhbltcnComboCdDocXcta',helpMap,'Combo Tipo Documento'),
               		hasHelpIcon:getHelpIconFromMap('agrRqstRhbltcnComboCdDocXcta',helpMap), 
		            Ayuda: getHelpTextFromMap('agrRqstRhbltcnComboCdDocXcta',helpMap,''),	 		          		
                    displayField:'dsFormato',
                    valueField:'cdDocXcta',
                    hiddenName: 'cdDocXcta',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    //fieldLabel: "Tipo Documento",
                    width:200,                                         //300,
                    emptyText:'Seleccione Tipo Documento...',
                    selectOnFocus:true,
                    forceSelection:false,
                    //labelSeparator:'',
                    allowBlank : true,
                    id:'cdDocXctaId'
                }
                ]

        });


//Windows donde se van a visualizar la pantalla

var window = new Ext.Window({
	        id:'windowAgrRqstRhbltcn',
	  		title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('windowAgrRqstRhbltcn', helpMap,'Crear Requisito de Rehabilitaci&oacute;n')+'</span>',
        	//title: 'Crear Requisito de Rehabilitacion',
        	width: 400,                                   //500
            height:250,
        	layout: 'fit',
        	plain:true,
        	bodyStyle:'padding:5px;',
        	bodyStyle:'background: white',
        	buttonAlign:'center',
        	items: formPanel,
        	modal: true,
            //se definen los botones del formulario
            buttons : [ {
				text:getLabelFromMap('agrButtonGuardar', helpMap,'Guardar'),
				tooltip:getToolTipFromMap('agrButtonGuardar', helpMap,'Agrega una nuevo requisito'),		
						        			
                //text : 'Guardar',
                disabled : false,
                handler : function() {
                    if (formPanel.form.isValid()) {
                        formPanel.form.submit( {
                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_INSERTAR_GUARDAR_REQUISITOS_REHABILITACION,
                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0], function(){reloadGrid();});
                                window.close();
                            },
                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0]);
                            },
                            //mensaje a mostrar mientras se guardan los datos
                            waitTitle: 'Espere',
                            waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')
                            //waitMsg : 'Guardando datos ...'
                        });
                    } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                        //Ext.Msg.alert('Informacion', 'Por favor complete la informacion requerida!');
                    }
                }
            }, {
				text:getLabelFromMap('agrButtonCancelar', helpMap,'Cancelar'),
				tooltip:getToolTipFromMap('agrButtonCancelar', helpMap,'Cancela operaci&oacute;n con requisitos'),
					        			
                //text : 'Cancelar',
                handler : function() {
                    window.close();
                }
            }]
    	});
        dsClientesCorp.load();
        dsTipoDocumento.load();
    	window.show();
};

// Funcion de editar requisito de rehabilitacion
function editar(record) {

    var dsTipoDocumento = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_TIPOS_DOCUMENTO
            }),
            reader: new Ext.data.JsonReader({
            root: 'comboTiposDocumento'
            },[
           {name: 'cdDocXcta', type: 'string',mapping:'cdDocXcta'},
           {name: 'dsFormato', type: 'string',mapping:'dsFormato'}
        ])
    });
    
   
    //se define la forma en que se van a leer los datos que envie la action
        var _jsonFormReader = new Ext.data.JsonReader( {
            //indica el arreglo a leer
            root : 'MEstructuraList',
            //indica la cantidad de registro que debe leerse
            totalProperty: 'total',
            //indica el resultado de la respuesta de la action
            successProperty : 'success'
        }, [ {
               name : 'cdRequisito',mapping : 'cdRequisito',type : 'string'
        }, {
               name : 'cdPerson',type : 'string',mapping : 'cdPerson'
        },{
               name : 'cdUnieco',type : 'string',mapping : 'cdUnieco'
        },{
               name : 'dsUnieco',type : 'string',mapping : 'dsUnieco'
        },{
               name : 'cdRamo',type : 'string',mapping : 'cdRamo'
         },{
               name : 'dsRamo',type : 'string',mapping : 'dsRamo'
        },{
               name : 'dsRequisito', mapping : 'dsRequisito', type : 'string'
        },{
               name :'cdElemento', mapping : 'cdElemento', type : 'string'
        },{
               name :'dsElemen', mapping : 'dsElemen', type : 'string'
        }
        ,{
               name : 'cdDocXcta',type : 'string',mapping : 'cdDocXcta'
        }
       ]);

//se define el formulario
var formPanel = new Ext.FormPanel ({
           labelWidth : 100,
			//action a invocar al hacer al cargar(load) del formulario
            url : _ACTION_GET_REQUISITOS_REHABILITACION,
            baseParams : {cdRequisito: record.get("cdRequisito")},
            bodyStyle : 'padding:5px 5px 0',
			//se setea el reader que va a usar el form cuando se cargue
            reader : _jsonFormReader,
            labelAlign:'right',
            //reader : test(),
            defaults : {
                width :200                             // 200 
            },
            defaultType : 'textfield',
            //se definen los campos del formulario
            items : [
                {
                    xtype: 'hidden',
                    name : 'cdElemento',
                    id: 'cdElemento'
                },{
                	xtype: 'hidden',
                    name : 'cdPerson',
                    id: 'cdPerson'
                },{
                    xtype: 'hidden',
                    name : 'cdUnieco',
                    id: 'cdUnieco'
                },{
                    xtype: 'hidden',
                    name : 'cdRamo',
                    id: 'cdRamo'
                },{
                    xtype: 'textfield',
			       	fieldLabel: getLabelFromMap('txtFldDsElemenEdtRqstRhbltcn', helpMap,'Cliente'), 
					tooltip: getToolTipFromMap('txtFldDsElemenEdtRqstRhbltcn', helpMap,'Texto de Cliente'),  
					hasHelpIcon:getHelpIconFromMap('txtFldDsElemenEdtRqstRhbltcn',helpMap),
	              	Ayuda: getHelpTextFromMap('txtFldDsElemenEdtRqstRhbltcn',helpMap,''),
									                    	
                    name : 'dsElemen',
                    //fieldLabel: "Cliente",
                    disabled:true,
                    id: 'dsElemen'
                },{
                    xtype: 'textfield',
       				fieldLabel: getLabelFromMap('txtFldDsUniecoEdtRqstRhbltcn', helpMap,'Aseguradora'), 
					tooltip: getToolTipFromMap('txtFldDsUniecoEdtRqstRhbltcn', helpMap,'Texto de Aseguradora'),  	
					hasHelpIcon:getHelpIconFromMap('txtFldDsUniecoEdtRqstRhbltcn',helpMap),
	              	Ayuda: getHelpTextFromMap('txtFldDsUniecoEdtRqstRhbltcn',helpMap,''),			                    	
                    name : 'dsUnieco',
                    //fieldLabel: "Aseguradora",
                    disabled:true,
                    id: 'dsUnieco'
                },{
                    xtype: 'textfield',
       				fieldLabel: getLabelFromMap('txtFldDsRamoEdtRqstRhbltcn', helpMap,'Producto'), 
					tooltip: getToolTipFromMap('txtFldDsRamoEdtRqstRhbltcn', helpMap,'Texto de Producto'), 
					hasHelpIcon:getHelpIconFromMap('txtFldDsRamoEdtRqstRhbltcn',helpMap),
	              	Ayuda: getHelpTextFromMap('txtFldDsRamoEdtRqstRhbltcn',helpMap,''),	 
									                    	
                    name : 'dsRamo',
                    //fieldLabel: "Producto",
                    disabled:true,
                    id: 'dsRamoId'
                },
                {
                xtype: 'textfield', 
		       	fieldLabel: getLabelFromMap('txtFldDsNombreEdtRqstRhbltcn', helpMap,'Nombre'), 
				tooltip: getToolTipFromMap('txtFldDsNombreEdtRqstRhbltcn', helpMap,'Texto de Nombre'),  
			    hasHelpIcon:getHelpIconFromMap('txtFldDsNombreEdtRqstRhbltcn',helpMap),
	            Ayuda: getHelpTextFromMap('txtFldDsNombreEdtRqstRhbltcn',helpMap,''),			                    	
                //fieldLabel: "Nombre", 
                name: 'dsRequisito', 
                id: 'dsRequisito'
                },
                {
                    xtype: 'combo',
                     tpl: '<tpl for="."><div ext:qtip="{cdDocXcta}. {dsFormato}" class="x-combo-list-item">{dsFormato}</div></tpl>',
                    store: dsTipoDocumento,
		            fieldLabel: getLabelFromMap('agrComboCdDocXcta',helpMap,'Tipo de Documento'),
               		tooltip: getToolTipFromMap('agrComboCdDocXcta',helpMap,'Combo Tipo de Documento'),	
               		hasHelpIcon:getHelpIconFromMap('agrComboCdDocXcta',helpMap),
	                Ayuda: getHelpTextFromMap('agrComboCdDocXcta',helpMap,''),					          		
                    displayField:'dsFormato',
                    valueField:'cdDocXcta',
                    hiddenName: 'cdDocXcta', 
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    //fieldLabel: "Tipo de Documento",
                    width: 200,                                           //  200
                    emptyText:'Seleccione Tipo de Documento...',
                    selectOnFocus:true,
                    forceSelection:false,
                    //labelSeparator:'',
                    allowBlank : true,
                    id:'cdDocXctaId'
                } 
                ]
        });


//Windows donde se van a visualizar la pantalla
var window = new Ext.Window({
  		title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('windowEdtRqstRhbltcn', helpMap,'Editar Requisito de Rehabilitaci&oacute;n')+'</span>',
        //title: 'Editar Requisito de Rehabilitacion',
        width:400,                              //500,
        height:250,
        layout: 'fit',
        plain:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: formPanel,
        //se definen los botones del formulario
            buttons : [ {
				text:getLabelFromMap('edtRqstRhbltcnButtonGuardar', helpMap,'Guardar'),
				tooltip:getToolTipFromMap('edtRqstRhbltcnButtonGuardar', helpMap,'Guarda requisito de rehabilitaci&oacute;n'),			        			
                //text : 'Guardar',
                disabled : false,
                handler : function() {
                    if (formPanel.form.isValid()) {
                        formPanel.form.submit( {
                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_INSERTAR_GUARDAR_REQUISITOS_REHABILITACION,
                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0], function(){reloadGrid();});
                                window.close();
                                },
                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0]);
                                },
                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : getLabelFromMap('400022', helpMap,'Guardando Actualizaci&oacute;n de datos...')
                            //waitMsg : 'Guardando Actualizacion de Datos ...'
                        });
                     } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                        //Ext.Msg.alert('Informacion', 'Por favor complete la informacion requerida!');
                     }
                }
            },
             {
				text:getLabelFromMap('edButtonRegresar', helpMap,'Regresar'),
				tooltip:getToolTipFromMap('edButtonRegresar', helpMap,'Regresa a la pantalla anterior'),			        			
                //text : 'Regresar',
                 handler : function() {
                 window.close();
                    }
            }]
    	});   
    	
    window.show();
 
    //se carga el formulario con los datos de la estructura a editar
    dsTipoDocumento.load({
          params: {cdElemento: record.get("cdElemento") ,cdUniEco: record.get('cdUnieco'),cdRamo: record.get('cdRamo')},
          callback: function (r, o, success) {
           			formPanel.form.load({
							waitTitle: 'Espere',
			                waitMsg : getLabelFromMap('400028', helpMap,'Leyendo datos ...')
			    	});
          }
    });
    
            
              
};
});

function reloadGrid(){
	var _params = {
    		dsElemen: Ext.getCmp('incisosForm').form.findField('dsElemen').getValue(),
    		dsRamo: Ext.getCmp('incisosForm').form.findField('dsRamo').getValue(),
    		dsUnieco: Ext.getCmp('incisosForm').form.findField('dsUniEco').getValue()
	};
	reloadComponentStore(Ext.getCmp('grid2'), _params, cbkReload);
/*    var mStore = grid2.store;
    var o = {start: 0};
    mStore.baseParams = mStore.baseParams || {};
    mStore.baseParams['dsElemen'] = dsElemen.getValue();
    mStore.baseParams['dsRamo'] = dsRamo.getValue();
    mStore.baseParams['dsUnieco'] = dsUniEco.getValue();
    mStore.reload(
              {
                  params:{start:0,limit:itemsPerPage},
                  callback : function(r,options,success) {
                      if (!success) {
                          Ext.MessageBox.alert('Aviso','No se encontraron registros');  
                         mStore.removeAll();
                      }
                  }

              }
            );*/
}

function cbkReload (_r, _o, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	
	}
}