var helpMap = new Map();

Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";

        var storeAsegurado  = new Ext.data.Store ({
                            proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_ASEGURADO}),
                            reader: new Ext.data.JsonReader({
                                    root: 'confAlertasAutoClientes',
                                    successProperty: '@success'
                                }, [
                                    {name: 'codigo', type: 'string', mapping: 'cdPerson'},
                                    {name: 'descripcion', type: 'string', mapping: 'dsElemen'}
                                ]),
                            remoteSort: true
                    });

	var storeAseguradora  = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_ASEGURADORA}),
						reader: new Ext.data.JsonReader({
								root: 'aseguradoraComboBox',
								successProperty: '@success'
							}, [
								{name: 'codigo', type: 'string', mapping: 'cdUniEco'},
								{name: 'descripcion', type: 'string', mapping: 'dsUniEco'}
							]),
						remoteSort: true
				});
	
	var storeProducto  = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_PRODUCTO}),
						reader: new Ext.data.JsonReader({
								root: 'productosAseguradoraCliente',
								successProperty: '@success'
							}, [
								{name: 'codigo', type: 'string', mapping: 'codigo'},
								{name: 'descripcion', type: 'string', mapping: 'descripcion'}
							]),
						remoteSort: true
				});
	
	var storePoliza  = new Ext.data.Store ({
					    proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_POLIZA}),
						reader: new Ext.data.JsonReader({
								root: 'comboObtienePoliza',
								successProperty: '@success'
							}, [
								{name: 'codigo', type: 'string', mapping: 'codigo'},
								{name: 'descripcion', type: 'string', mapping: 'descripcion'}
							]),
						remoteSort: true
				});
	
	var comboAsegurado = new Ext.form.ComboBox({   
					                id: 'asegurado', 
					                labelWidth: 50, 
					                tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
						            store: storeAsegurado, 
						            fieldLabel: getLabelFromMap('cmbAsegurado',helpMap,'Asegurado'),
					                tooltip: getToolTipFromMap('cmbAsegurado',helpMap, 'Asegurado'),			          		
						            displayField:'descripcion', 
						            valueField:'codigo', 
						            hiddenName: 'cmbAsegurado', 
						            typeAhead: true,
						            mode: 'local', 
						            triggerAction: 'all', 
						            width: 200, 
						            emptyText:'Seleccionar Asegurado...',
						            selectOnFocus:true,
						            forceSelection:true, 
						            allowBlank: false,
						            onSelect: function (record) {
	                            				this.setValue(record.get('codigo'));
	                            				
	                            				//el_form.findById(('aseguradora')).setValue("");				
	                            					    storeAseguradora.removeAll();
	                            					    storeAseguradora.load({
	                            											params: {cdPerson: record.get('codigo')
	                            											        
	                            											         },
	                            											failure: storeAseguradora.removeAll()
	                            										});
	                            				
	                            					
	                            				this.collapse();
		                            		
	                                       }
						            						          
						            			                        
					           	});					
					           	
	var comboAseguradora = new Ext.form.ComboBox({   
					                id: 'aseguradora', 
					                labelWidth: 50, 
					                tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
						            store: storeAseguradora, 
						            fieldLabel: getLabelFromMap('cmbAseguradora',helpMap,'Aseguradora'),
					                tooltip: getToolTipFromMap('cmbAseguradora',helpMap, 'Aseguradora'),			          		
						            displayField:'descripcion', 
						            valueField:'codigo', 
						            hiddenName: 'cmbAseguradora', 
						            typeAhead: true,
						            mode: 'local', 
						            triggerAction: 'all', 
						            width: 200, 
						            emptyText:'Seleccionar Aseguradora...',
						            selectOnFocus:true,
						            forceSelection:true,
						            allowBlank: false,
						            onSelect: function (record) {
	                            				this.setValue(record.get('codigo'));
	                            				
	                            				//el_form.findById(('aseguradora')).setValue("");				
	                            					    //storeProducto.removeAll();
	                            					    /*storeProducto.load({
	                            											params: {cdunieco: record.get('codigo'), //codigo Aseguradora
	                            											         cdPerson: el_form.findById(('asegurado')).getValue() //codigo cliente
	                            											         },
	                            											failure: storeProducto.removeAll()
	                            										});*/
	                            						storePoliza.removeAll();
	                            						storePoliza.load({
	                            											params: {cdunieco: record.get('codigo'), //codigo Aseguradora
	                            											         cdramo: '', //el_form.findById(('asegurado')).getValue() //codigo cliente
	                            											         cdPerson: Ext.getCmp('asegurado').getValue()
	                            											         },
	                            											failure: storePoliza.removeAll()
	                            										});	                            					
	                            				this.collapse();
		                            		
	                                       }

					           	});
					        
			           	
					           	
					           	
					           	
	/*var comboProducto = new Ext.form.ComboBox({   
					                id: 'producto', 
					                labelWidth: 50, 
					                tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
						            store: storeProducto, 
						            fieldLabel: getLabelFromMap('cmbProducto',helpMap,'Producto'),
					                tooltip: getToolTipFromMap('cmbProducto',helpMap, 'Producto'),			          		
						            displayField:'descripcion', 
						            valueField:'codigo', 
						            hiddenName: 'cmbProducto', 
						            typeAhead: true,
						            mode: 'local', 
						            triggerAction: 'all', 
						            width: 200, 
						            emptyText:'Seleccionar Producto...',
						            selectOnFocus:true,
						            forceSelection:true,
						                   
						            onSelect: function (record) {
	                            				this.setValue(record.get('codigo'));
	                            				
	                            				//el_form.findById(('aseguradora')).setValue("");				
	                            					    storePoliza.removeAll();
	                            					    storePoliza.load({
	                            											/*params: {cdPerson: record.get('codigo')
	                            											        
	                            											         },*/
	                            										/*	failure: storePoliza.removeAll()
	                            										});
	                            				
	                            					
	                            				this.collapse();
		                            		
	                                       }
			                        
					           	});	*/
					           	
	var comboPoliza = new Ext.form.ComboBox({   
					                id: 'poliza', 
					                labelWidth: 50, 
					                tpl: '<tpl for="."><div ext:qtip="{descripcion}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
						            store: storePoliza, 
						            fieldLabel: getLabelFromMap('cmbPoliza',helpMap,'Poliza'),
					                tooltip: getToolTipFromMap('cmbPoliza',helpMap, 'Poliza'),			          		
						            displayField:'descripcion', 
						            valueField:'descripcion', 
						            hiddenName: 'cmbPoliza', 
						            typeAhead: true,
						            mode: 'local', 
						            triggerAction: 'all', 
						            width: 200, 
						            emptyText:'Seleccionar Poliza...',
						            selectOnFocus:true,
						            forceSelection:true,
			                        allowBlank: false
					           	});
					           	
 	var fechaCnclcnDe=	new Ext.form.DateField({
				name: 'fechaCnclcnDe',
				id: 'fechaCnclcnDeId',
				fieldLabel: getLabelFromMap('edAlerDfUltEnv',helpMap,'Fecha Desde'),
				tooltip: getToolTipFromMap('edAlerDfUltEnv',helpMap), 
				format: 'd/m/Y'
				//altFormats : 'm/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g'
								});
					           	
					           	

    /********* Comienza el form ********************************/
    var el_form = new Ext.FormPanel ({
    		id: 'el_form',
    		name: 'el_form',
            renderTo: 'formBusqueda',
	  		title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('el_formMantEjecutivosCta', helpMap,'Reporte de Deuda')+'</span>',
            //title: '<span style="color:black;font-size:14px;">Configuraci&oacute;n de Secciones</span>',
            url : _ACTION_BUSCAR_REPORTE_DEUDA,
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
		                    comboAsegurado,
		                    comboAseguradora,
		                    //comboProducto,
		                    comboPoliza,
		                    fechaCnclcnDe
		                    
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
	    			dataIndex: 'poliza',
				   	header: getLabelFromMap('polizaLbl',helpMap,'P&oacute;liza'),
			        tooltip: getToolTipFromMap('polizaLbl', helpMap,'P&oacute;liza'),		           	
		           	sortable: true,
		           	width: 140
	    		},{
				   	dataIndex: 'endoso',
				   	header: getLabelFromMap('endosoLbl',helpMap, 'Endoso'),
			        tooltip: getToolTipFromMap('endosoLbl', helpMap,'Endoso'),		           	
		           	sortable: true,
		           	width: 140
	        	},{
	    			dataIndex: 'certificado',
				   	header: getLabelFromMap('certificadoLbl',helpMap, 'Certificado'),
			        tooltip: getToolTipFromMap('certificadoLbl', helpMap,'Certificado'),		           	
		           	//header: "Nombre",
		           	sortable: true,
		           	width: 140
	    		},{
	    			dataIndex: 'fechaPago',
				   	header: getLabelFromMap('fechaPagoLbl',helpMap, 'Fecha Pago'),
			        tooltip: getToolTipFromMap('fechaPagoLbl', helpMap,'Fecha Pago'),		           	
		           	sortable: true,
   		            renderer: function(val) {
		           	try{
		           			var fecha = new Date();
		           			fecha = Date.parseDate(val, 'c');
		           		return  fecha.format('d/m/Y');
               			}
         		    catch(e)
              		{   //alert("catch");
              			return val;
              		}
                	},		           	
		           	width: 140
	        	},{
	    			dataIndex: 'referenciaBancaria',
				   	header: getLabelFromMap('referenciaBancariaLbl',helpMap, 'Referencia Bancaria'),
			        tooltip: getToolTipFromMap('referenciaBancariaLbl', helpMap,'Referencia Bancaria'),		           	
		           	sortable: true,
		           	width: 140
	        	},{
	    			dataIndex: 'recibo',
				   	header: getLabelFromMap('reciboLbl',helpMap, 'Recibo'),
			        tooltip: getToolTipFromMap('reciboLbl', helpMap,'Recibo'),		           	
		           	sortable: true,
		           	width: 140
	        	},{
	    			dataIndex: 'monto',
				   	header: getLabelFromMap('montoLbl',helpMap, 'Monto'),
			        tooltip: getToolTipFromMap('montoLbl', helpMap,'Monto'),		           	
		           	sortable: true,
		           	width: 140
	        	},{
	    			dataIndex: 'idAseguradora',
				   	header: getLabelFromMap('idAseguradoraLbl',helpMap, 'Id Aseguradora'),
			        tooltip: getToolTipFromMap('idAseguradoraLbl', helpMap,'Id Aseguradora'),		           	
		           	hidden:true,
		           	sortable: true,
		           	width: 140
	        	},{
	    			dataIndex: 'codAseguradora',
				   	header: getLabelFromMap('codAseguradoraLbl',helpMap, 'Cod Aseguradora'),
			        tooltip: getToolTipFromMap('codAseguradoraLbl', helpMap,'Cod Aseguradora'),		           	
		           	hidden:true,
		           	sortable: true,
		           	width: 140
	        	},{
	    			dataIndex: 'aseguradora',
				   	header: getLabelFromMap('aseguradoraLbl',helpMap, 'Aseguradora'),
			        tooltip: getToolTipFromMap('aseguradoraLbl', helpMap,'Aseguradora'),		           	
		           	hidden:true,
		           	sortable: true,
		           	width: 140
	        	},{
	    			dataIndex: 'idCliente',
				   	header: getLabelFromMap('idClienteLbl',helpMap, 'Id Cliente'),
			        tooltip: getToolTipFromMap('idClienteLbl', helpMap,'Id Cliente'),		           	
		           	hidden:true,
		           	sortable: true,
		           	width: 140
	        	},{
	    			dataIndex: 'cliente',
				   	header: getLabelFromMap('clienteLbl',helpMap, 'Cliente'),
			        tooltip: getToolTipFromMap('clienteLbl', helpMap,'Cliente'),		           	
		           	hidden:true,
		           	sortable: true,
		           	width: 140
	        	},{
	    			dataIndex: 'idOperacion',
				   	header: getLabelFromMap('idOperacionLbl',helpMap, 'Id Operacion'),
			        tooltip: getToolTipFromMap('idOperacionLbl', helpMap,'Id Operacion'),		           	
		           	hidden:true,
		           	sortable: true,
		           	width: 140
	        	},{
	    			dataIndex: 'codPoliza',
				   	header: getLabelFromMap('codPolizaLbl',helpMap, 'Cod Poliza'),
			        tooltip: getToolTipFromMap('codPolizaLbl', helpMap,'Cod Poliza'),		           	
		           	hidden:true,
		           	sortable: true,
		           	width: 140
	        	},{
	    			dataIndex: 'codRamo',
				   	header: getLabelFromMap('codRamoLbl',helpMap, 'Cod Ramo'),
			        tooltip: getToolTipFromMap('codRamoLbl', helpMap,'Cod Ramo'),		           	
		           	hidden:true,
		           	sortable: true,
		           	width: 140
	        	},{
	    			dataIndex: 'descRamo',
				   	header: getLabelFromMap('descRamoLbl',helpMap, 'Ramo'),
			        tooltip: getToolTipFromMap('descRamoLbl', helpMap,'Ramo'),		           	
		           	hidden:true,
		           	sortable: true,
		           	width: 140
	        	},{
	    			dataIndex: 'nroCuota',
				   	header: getLabelFromMap('nroCuotaLbl',helpMap, 'Nro Cuota'),
			        tooltip: getToolTipFromMap('nroCuotaLbl', helpMap,'Nro Cuota'),		           	
		           	hidden:true,
		           	sortable: true,
		           	width: 140
	        	},{
	    			dataIndex: 'fecVencimiento',
				   	header: getLabelFromMap('fecVencimientoLbl',helpMap, 'Fecha Vencimiento'),
			        tooltip: getToolTipFromMap('fecVencimientoLbl', helpMap,'Fecha Vencimiento'),		           	
		           	hidden:true,
		           	sortable: true,
		           	renderer: function(val) {
		           	try{
		           			var fecha = new Date();
		           			fecha = Date.parseDate(val, 'c');
		           		return  fecha.format('d/m/Y');
               			}
         		    catch(e)
              		{   //alert("catch");
              			return val;
              		}
                	},	
		           	width: 140
	        	},{
	    			dataIndex: 'descMoneda',
				   	header: getLabelFromMap('descMonedaLbl',helpMap, 'Moneda'),
			        tooltip: getToolTipFromMap('descMonedaLbl', helpMap,'Moneda'),		           	
		           	hidden:true,
		           	sortable: true,
		           	width: 140
	        	},{
	    			dataIndex: 'impSaldoCuota',
				   	header: getLabelFromMap('impSaldoCuotaLbl',helpMap, 'Importe Saldo Cuota'),
			        tooltip: getToolTipFromMap('impSaldoCuotaLbl', helpMap,'Importe Saldo Cuota'),		           	
		           	hidden:true,
		           	sortable: true,
		           	width: 140
	        	},{
	    			dataIndex: 'fecFinalCuota',
				   	header: getLabelFromMap('fecFinalCuotaLbl',helpMap, 'Fecha Final Cuota'),
			        tooltip: getToolTipFromMap('fecFinalCuotaLbl', helpMap,'Fecha Final Cuota'),		           	
		           	sortable: true,
		           	hidden:true,
		           	renderer: function(val) {
		           	try{
		           			var fecha = new Date();
		           			fecha = Date.parseDate(val, 'c');
		           		return  fecha.format('d/m/Y');
               			}
         		    catch(e)
              		{   //alert("catch");
              			return val;
              		}
                	},	
		           	width: 140
	        	},{
	    			dataIndex: 'nroCuotas',
				   	header: getLabelFromMap('nroCuotasLbl',helpMap, 'Nro Cuotas'),
			        tooltip: getToolTipFromMap('nroCuotasLbl', helpMap,'Nro Cuotas'),		           	
		           	hidden:true,
		           	sortable: true,
		           	width: 140
	        	} 	 	        
	      
	           	]);
	           	
	           	
	          

		//Crea el Store
		function crearGridStore(){
		 			store = new Ext.data.Store({
		    			proxy: new Ext.data.HttpProxy({
						url: _ACTION_BUSCAR_REPORTE_DEUDA
		                }),
		                reader: new Ext.data.JsonReader({
		            	root:'MReporteDeudaList',
		            	totalProperty: 'totalCount',
			            successProperty : '@success'
			        },[
			        {name: 'poliza', type: 'string', mapping: 'idPoliza'},
			        {name: 'endoso',  type: 'string',  mapping:'codEndoso'},
			        {name: 'certificado',  type: 'string',  mapping:'codCertificado'},
			        {name: 'fechaPago',  type: 'string',  mapping:'fecPago'},
			        {name: 'referenciaBancaria',  type: 'string',  mapping:'nroCuentaDebito'},
			        {name: 'recibo',  type: 'string',  mapping:'nroCuota'},
			        {name: 'monto',  type: 'string',  mapping:'impSaldoCuota'},
			        //hasta acá pedía el FRD en la pantalla
			        {name: 'idAseguradora',  type: 'string',  mapping:'idAseguradora'},
			        {name: 'codAseguradora',  type: 'string',  mapping:'codAseguradora'},
			        {name: 'aseguradora',  type: 'string',  mapping:'aseguradora'},
			        {name: 'idCliente',  type: 'string',  mapping:'idCliente'},
			        {name: 'cliente',  type: 'string',  mapping:'cliente'},
			        {name: 'idOperacion',  type: 'string',  mapping:'idOperacion'},
			        {name: 'codPoliza',  type: 'string',  mapping:'codPoliza'},
			        {name: 'codRamo',  type: 'string',  mapping:'codRamo'},
			        {name: 'descRamo',  type: 'string',  mapping:'descRamo'},
			        {name: 'nroCuota',  type: 'string',  mapping:'nroCuota'},
			        {name: 'fecVencimiento',  type: 'string',  mapping:'fecVencimiento'},
			        {name: 'descMoneda',  type: 'string',  mapping:'descMoneda'},
			        {name: 'impSaldoCuota',  type: 'string',  mapping:'impSaldoCuota'},
			        {name: 'fecFinalCuota',  type: 'string',  mapping:'fecFinalCuota'},
			        {name: 'nroCuotas',  type: 'string',  mapping:'nroCuotas'}
			        
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
		        		/*{
		      				text:getLabelFromMap('gridButtonEditar', helpMap,'Imprimir'),
		           			tooltip:getToolTipFromMap('gridButtonEditar', helpMap,'Imprime Reporte Deuda'),			        			
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
		            	},*/{
		      				text:getLabelFromMap('gridButtonExportar', helpMap,'Exportar'),
		           			tooltip:getToolTipFromMap('gridButtonExportar', helpMap,'Exportar la Grilla de B&uacute;squeda'),			        			
		                	handler:function(){
                                  var url = _ACTION_EXPORTAR_REPORTE_DEUDA + "?cdAsegurado=" + Ext.getCmp('el_form').form.findField('cmbAsegurado').getValue() +
													"&cdAseguradora=" + Ext.getCmp('el_form').form.findField('cmbAseguradora').getValue() +
													"&nmPoliza=" + Ext.getCmp('el_form').form.findField('cmbPoliza').getValue() +
													"&fechaDesde=" + Ext.getCmp('el_form').form.findField('fechaCnclcnDe').getRawValue();
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


    //Muestra los componentes en pantalla
	
	storeAsegurado.load();
	//storeAseguradora.load();
	//storeProducto.load();
	//storePoliza.load();
	el_form.render();
	createGrid();
	//Fin Muestra los componentes en pantalla    

});
function reloadGrid(){
	var _params = {
				cdAsegurado: Ext.getCmp('el_form').form.findField('cmbAsegurado').getValue(),
				cdAseguradora: Ext.getCmp('el_form').form.findField('cmbAseguradora').getValue(),
				//cdProducto: Ext.getCmp('el_form').form.findField('cmbProducto').getValue(),
				nmPoliza: Ext.getCmp('el_form').form.findField('cmbPoliza').getValue(),				
				fechaDesde: Ext.getCmp('el_form').form.findField('fechaCnclcnDe').getRawValue()
			
	};
    reloadComponentStore(Ext.getCmp('grilla'), _params, cbkReload);
}
function cbkReload (_r, _o, _success, _store) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
		_store.removeAll();
	}
}
