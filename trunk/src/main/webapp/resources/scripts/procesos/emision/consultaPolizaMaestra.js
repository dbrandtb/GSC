Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";
	var _url;
	    var storeNivel = new Ext.data.Store({
	        	proxy: new Ext.data.HttpProxy({
	            //url: 'procesoemision/getComboNivel.action'
	            url: _ACTION_COMBO_NIVEL
	        }),
	        reader: new Ext.data.JsonReader({
	            root: 'listaNivel',
	            id: 'storeNivel'
	        },[
	                {name: 'cdCliente', type: 'string', mapping:'cdCliente'},
	                {name: 'dsCliente', type: 'string', mapping:'dsCliente'}
	        ]),
	        remoteSort: false
	    });
	    storeNivel.setDefaultSort('dsCliente', 'desc');
	    storeNivel.load();

		//******************Store para combo de Aseguradora******************
	    var storeAseg = new Ext.data.Store({
	        	proxy: new Ext.data.HttpProxy({
	            //url: 'procesoemision/getComboAseguradora.action'
	             url: _ACTION_COMBO_ASEGURADORA_CLIENTE 
	            //  url: _ACTION_COMBO_ASEGURADORA  
	        }),
	        reader: new Ext.data.JsonReader({
	            root: 'listaAseguradoras',
	            id: 'storeAseguradoras'
	        },[
	                {name: 'label', type: 'string', mapping:'label'},
	                {name: 'value', type: 'string', mapping:'value'}
	        ]),
	        remoteSort: false
	    });
	    storeAseg.setDefaultSort('value', 'desc');
	    storeAseg.load();
    
		//******************Store para combo de Producto******************
	    var storeProducto = new Ext.data.Store({
	        	proxy: new Ext.data.HttpProxy({
	            //url: 'procesoemision/getComboProducto.action'
	            //url: _ACTION_COMBO_PRODUCTO
	              url: _ACTION_COMBO_PRODUCTO_ASEGURADORA
	        }),
	        reader: new Ext.data.JsonReader({
	            root: 'listaProductos',
	            id: 'storeProducto'
	        },[
	                {name: 'label', type: 'string', mapping:'label'},
	                {name: 'value', type: 'string', mapping:'value'}
	        ]),
	        remoteSort: false
	    });
	    storeProducto.setDefaultSort('value', 'desc');
	    storeProducto.load();
	    
		//******************Store para combo de Tipo de Situacion (Riesgo) ******************
	    var storeTipoDeSituacion = new Ext.data.Store({
	        	proxy: new Ext.data.HttpProxy({
	            url: _ACTION_COMBO_TIPO_DE_SITUACION
	        }),
	        reader: new Ext.data.JsonReader({
	            root: 'listaTipoDeSituacion',
	            id: 'storeTipoDeSituacion'
	        },[
	                {name: 'label', type: 'string', mapping:'label'},
	                {name: 'value', type: 'string', mapping:'value'}
	        ]),
	        remoteSort: false
	    });
	    storeTipoDeSituacion.setDefaultSort('value', 'desc');
	    storeTipoDeSituacion.load();
	    
		//******************Store para combo de Tipos******************
	    var storeTipos = new Ext.data.Store({
	        	proxy: new Ext.data.HttpProxy({
	            //url: 'procesoemision/getComboTipos.action'
	            url: _ACTION_COMBO_TIPO
	        }),
	        reader: new Ext.data.JsonReader({
	            root: 'listaTipos',
	            id: 'storeTipos'
	        },[
	                {name: 'label', type: 'string', mapping:'label'},
	                {name: 'value', type: 'string', mapping:'value'}
	        ]),
	        remoteSort: false
	    });
	    storeTipos.setDefaultSort('value', 'desc');
	    storeTipos.load();
	    
	var lasFilas = new Ext.data.Record.create([{
				name : 'cdpolmtra',
				type : 'string',
				mapping : 'cdpolmtra'
			}, {
				name : 'cdelemento',
				type : 'string',
				mapping : 'cdelemento'
			}, {
				name : 'cdcia',
				type : 'string',
				mapping : 'cdcia'
			}, {
				name : 'cdramo',
				type : 'string',
				mapping : 'cdramo'
			}, {
				name : 'cdtipsit',
				type : 'string',
				mapping : 'cdtipsit'
			}, {
				name : 'dstipsit',
				type : 'string',
				mapping : 'dstipsit'
			}, {
				name : 'cdtipo',
				type : 'string',
				mapping : 'cdtipo'
			}, {
				name : 'nmpoliex',
				type : 'string',
				mapping : 'nmpoliex'
			}, {
				name : 'nmpoliza',
				type : 'string',
				mapping : 'nmpoliza'
			}, {
				name : 'feinicio',
				type : 'string',
				mapping : 'feinicio'
				
				
			}, {
				name : 'fefin',
				type : 'string',
				mapping : 'fefin'
			}, {
				name : 'dselemen',
				type : 'string',
				mapping : 'dselemen'
			}, {
				name : 'dsramo',
				type : 'string',
				mapping : 'dsramo'
			}, {
				name : 'dsunieco',
				type : 'string',
				mapping : 'dsunieco'
			}, {
				name : 'dstipo',
				type : 'string',
				mapping : 'dstipo'
			},{
				name : 'cdnumpol',
				type : 'string',
				mapping : 'cdnumpol'
			},{
				name : 'cdnumren',
				type : 'string',
				mapping : 'cdnumren'
			}
			]);

	/*var jsonGrilla = new Ext.data.JsonReader({
				root : 'listPolizasMaestras',
				totalProperty: 'totalCount',
			    successProperty : '@success'
			}, 
			lasFilas
	);*/

	var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
		header : 'cdpolmtra',
		dataIndex : 'cdpolmtra',
		hidden : true
	},{
		header : 'cdtipo',
		dataIndex : 'cdtipo',
		hidden : true
	},{
		header : 'Nivel',
		dataIndex : 'dselemen',
		width : 140,
		sortable : true
	}, {
		header : "Aseguradora",
		dataIndex : 'dsunieco',
		width : 180,
		sortable : true
	}, {
		header : "Producto",
		dataIndex : 'dsramo',
		width : 180,
		sortable : true
	}, {
		header : "Riesgo",
		dataIndex : 'dstipsit',
		width : 140,
		sortable : true
	}, {
		header : "Tipo",
		dataIndex : 'dstipo',
		width : 180,
		sortable : true
	}, {
		header : "Poliza Aseguradora",
		dataIndex : 'nmpoliza',
		width : 140,
		sortable : true
	}, {
		header : "P&oacute;liza",
		dataIndex : 'nmpoliex',
		width : 140,
		sortable : true
	}, {
		header : "Inicio",
		dataIndex : 'feinicio',
		width : 140,
		sortable : true,
		//renderer: fecha.format('d/m/Y'),
		//format: 'd/m/Y',
		//editor: new Ext.form.DateField({name: 'feInicio', format: 'd/m/Y'}),
		renderer: function(val) {
		           			try{
		           			var fecha = new Date();
		           			fecha = Date.parseDate(val, 'Y-m-d H:i:s.g');
		           			//alert("Valor: " + val + "\nFecha: " + fecha + "\nformateada : " + fecha.format('d/m/Y'));
               var _val2 = val.format ('Y-m-d H:i:s.g');
              // alert(_val2);
               return _val2.format('d/m/Y');
               }
              catch(e)
              {
              	return fecha.format('d/m/Y');
              }
                }
	}, {
		header : "Fin",
		dataIndex : 'fefin',
		width : 140,
		sortable : true,
		renderer: function(val) {
		           			try{
		           			var fecha = new Date();
		           			fecha = Date.parseDate(val, 'Y-m-d H:i:s.g');
		           			//alert("Valor: " + val + "\nFecha: " + fecha + "\nformateada : " + fecha.format('d/m/Y'));
               var _val2 = val.format ('Y-m-d H:i:s.g');
              // alert(_val2);
               return _val2.format('d/m/Y');
               }
              catch(e)
              {
              	return fecha.format('d/m/Y');
              }
                }
		
		
	},{
		header : "cdnumpol",
		dataIndex : 'cdnumpol',
		hidden: true
	},{
		header : "cdnumren",
		dataIndex : 'cdnumren',
		hidden: true
	},{
		header : "cdtipsit",
		dataIndex : 'cdtipsit',
		hidden: true
	},{
		header : "cdcia",
		dataIndex : 'cdcia',
		hidden: true
	},{
		header : "cdelemento",
		dataIndex : 'cdelemento',
		hidden: true
	},{
		header : "cdramo",
		dataIndex : 'cdramo',
		hidden: true
	}
	]);
	cm.defaultSortable = true;

	var boton=false; 
	
	//alert (DESC_NIVEL);
	var storeGridResultado;
	var STORE= 'STORE';

    if(DESC_PRODUCTO!=""){
     _url = _NUEVA_BUSQUEDA+'?parametrosBusqueda.dselemen='+DESC_NIVEL+'&parametrosBusqueda.dsunieco='+DESC_ASEGURADORA+'&parametrosBusqueda.dsramo='+DESC_PRODUCTO;

    }else{
     _url = _NUEVA_BUSQUEDA;
     boton=true;
     //Ext.getCmp('maestraVolver').setVisible(false);
    }

	function formatDate(dateVal){
            return (dateVal && dateVal.format) ? dateVal.dateFormat('d/m/Y') : 'Not Available';
        }
	//function grillaResultado() {
		//var url = _NUEVA_BUSQUEDA+'?store=STORE';
	var url = _url;
    storeGridResultado = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
				url : url
			}),
			reader : new Ext.data.JsonReader({
				root : 'listPolizasMaestras',
				totalProperty: 'totalCount',
			    successProperty : '@success'
			}, 
			lasFilas
	)
	//		remoteSort: true
		});
		//storeGridResultado.setDefaultSort('dselemen', 'desc');
		//return storeGridResultado;
	//}

	// ******************Form Filtros******************
	var Nivel = new Ext.form.TextField({
		id: 'dsNivelId',
		fieldLabel : 'Nivel',
		name : 'parametrosBusqueda.dselemen',
		//value : '',
		width : 200
	});
	var Aseguradora = new Ext.form.TextField({
		id: 'dsUniecoId',
		fieldLabel : 'Aseguradora',
		name : 'parametrosBusqueda.dsunieco',
		//value : '',
		width : 200
	});
	var Producto = new Ext.form.TextField({
		id: 'dsRamoId',
		fieldLabel : 'Producto',
		name : 'parametrosBusqueda.dsramo',
		//value : '',
		width : 200
	});

	var filtroForm = new Ext.form.FormPanel({
        title: '<span style="color:black;font-size:12px;">P&oacute;liza Maestra</span>',
		//url : 'procesoemision/getPolizasMaestras.action',		
		buttonAlign : 'center',
		labelAlign : 'right',
		//collapsible : true,
		frame : true,
		width : 668,
		autoHeight : true,
		items: [{
				html: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',            		       	
	            layout: 'column',
				items : [{
								layout: 'form',
								bodyStyle: {background: 'white'},
								buttonAlign: 'center',
								items: [Nivel, Aseguradora, Producto],
								buttons : [{
									text : 'Buscar',
									handler : function() {
									    storeGridResultado.baseParams= storeGridResultado.baseParams || {};
									    storeGridResultado.baseParams= { 'parametrosBusqueda.dsunieco': Aseguradora.getValue(),
									    								   'parametrosBusqueda.dselemen': Nivel.getValue(),
									    								   'parametrosBusqueda.dsramo': Producto.getValue()
																	     };
										storeGridResultado.params = {start: 0, limit: itemsPerPage};
										storeGridResultado.proxy = new Ext.data.HttpProxy({
																url : _NUEVA_BUSQUEDA
															});
										storeGridResultado.load({
																//proxy.url : _NUEVA_BUSQUEDA,
																callback: function(r, options, success){
																				if(!success){
																				//console.log(storeGridResultado.reader.jsonData.mensajeError);
																				Ext.MessageBox.alert('Buscar',storeGridResultado.reader.jsonData.mensajeError);
																				//alert(1);
																				gridResultados.store.removeAll();
																				gridResultados.getBottomToolbar().updateInfo();
																				}
																		}
															});
										return;
										
										
	/*Ext.getCmp('dsNivelId').setValue(DESC_NIVEL);
	Ext.getCmp('dsUniecoId').setValue(DESC_ASEGURADORA);
	Ext.getCmp('dsRamoId').setValue(DESC_PRODUCTO);*/
										
										
										if (filtroForm.form.isValid()) {
											filtroForm.form.submit({
												url: _NUEVA_BUSQUEDA,
												waitMsg : 'Procesando...',
												failure : function(form, action) {
													Ext.MessageBox.alert('Buscar','No se Encontraron datos');
													storeGridResultado.removeAll();
												},
												success : function(form, action) {



												}
											});
										} else {
											Ext.MessageBox.alert('Error', 'Ocurrio un error');
										}
									}
								}, {
									text : 'Cancelar',
									handler : function() {
										filtroForm.form.reset();
									}
								}]
				}]
		}], 
		//[Nivel, Aseguradora, Producto],
		renderTo: 'filtrosPolizaMaestra',
		bodyStyle: {background: 'white'}
	});

	var selectedId = "";
	var cdPolmtra="";
	var dsNivel="";
	var dsAseg="";
	var dsProd="";
	var dsTipSit="";
	var dsTipo="";
	var dsPolizaA="";
	var dsPolizaC="";
	var dsFI="";
	var dsFF="";
	var result="";
	
	var cdCia="";
	var cdElemento="";
	var cdRamo="";
	var cdTipSit="";
	var cdTipo="";
	
	var gridResultados = new Ext.grid.GridPanel({
		id:'gridResultados',
		el: 'listadoPolizaMaestra',
		store : storeGridResultado,//grillaResultado(),
		//reader: jsonGrilla,
		border : true,
		//loadMask: {msg: getLabelFromMap('400058',helpMap,'Cargando datos ...'), disabled: false},
		buttonAlign : 'center',
		//baseCls : 'background:white',
		cm : cm,
		//loadMask : true,
		buttons : [{
					id : 'maestraAgregar',
					text : 'Agregar',
					tooltip : 'Agregar',
					listeners: {
						beforerender: function(){
							//Se debe deshabilitar si venimos de la pantalla Numeracion de Polizas
							if(PANTALLA_ORIGEN == NUMERACION_POLIZAS){
								Ext.getCmp('maestraAgregar').disable();
							}
						}
					}
				},{
					id : 'maestraEditar',
					text : 'Editar',
					tooltip : 'Editar'
				},{
					id : 'maestraExportar',
					text : 'Exportar',
					tooltip : 'Exportar',
					//handler : exportButton(_ACTION_EXPORT)
					handler:function(){                		
                		var url = _NUEVO_ACTION_EXPORT + '?dsElemen=' + Ext.getCmp('dsNivelId').getValue()+ '&dsUnieco=' + Ext.getCmp('dsUniecoId').getValue() +'&dsRamo=' + Ext.getCmp('dsRamoId').getValue();
                	 	showExportDialog( url );
                	}
				},{
					id : 'maestraVolverRol',
					text : 'Regresar a Rol',
					tooltip : 'Regresar a Agrupacion por Rol',
					handler: function(){
						//console.log(gridResultados.getSelections());
						if (gridResultados.getSelections()!=""){
								if (gridResultados.getSelectionModel().getSelected().get("cdpolmtra")==""){
					   				Ext.MessageBox.alert('Aviso', 'No Existe Informacion de cdpolmtra');
							   	}else{
				   	    			//alert(gridResultados.getSelectionModel().getSelected().get("cdpolmtra")); 
					                	window.location = _ACTION_IR_AGRUPACION_POR_ROL+ '?cdPolMtra=' + gridResultados.getSelectionModel().getSelected().get("cdpolmtra") +
					                					'&codigoConfiguracion=' + CODIGO_CONFIGURACION+'&codigoAgrupacion='+CODIGO_AGRUPACION;
							   	}
					   	}else{
					   		var _cdPolMtra='';
					   		window.location = _ACTION_IR_AGRUPACION_POR_ROL+ '?cdPolMtra=' + _cdPolMtra +
					                					'&codigoConfiguracion=' + CODIGO_CONFIGURACION+'&codigoAgrupacion='+CODIGO_AGRUPACION;
					   		//Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
					   	}
			       }
				},{
					id : 'maestraVolverGrupoPersona',
					text : 'Regresar a Grupo',
					tooltip : 'Regresar a Agrupacion por Grupo de Persona',
					handler: function(){
						//console.log(gridResultados.getSelections());
						if (gridResultados.getSelections()!=""){
								if (gridResultados.getSelectionModel().getSelected().get("cdpolmtra")==""){
					   				Ext.MessageBox.alert('Aviso', 'No Existe Informacion de cdpolmtra');
							   	}else{
				   	    			//alert(gridResultados.getSelectionModel().getSelected().get("cdpolmtra")); 
					                	window.location = _ACTION_IR_AGRUPACION_POR_GRUPO_PERSONA+ '?cdPolMtra=' + gridResultados.getSelectionModel().getSelected().get("cdpolmtra") +
					                					'&cveAgrupa=' + CODIGO_CONFIGURACION+'&codigoAgrupacion='+CODIGO_AGRUPACION;
							   	}
					   	}else{
					   		var _cdPolMtra='';
					   		window.location = _ACTION_IR_AGRUPACION_POR_GRUPO_PERSONA+ '?cdPolMtra=' + _cdPolMtra +
					                					'&cveAgrupa=' + CODIGO_CONFIGURACION+'&codigoAgrupacion='+CODIGO_AGRUPACION;
					   		//Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
					   	}
			       }
				},
				//boton para llamar numeración pólizas
				{
					id : 'asignarNumeroAEmision',
					text : 'Asignar N&uacute;mero a Emisi&oacute;n',
					tooltip : 'Asignar N&uacute;mero a Emisi&oacute;n',
					//handler : exportButton(_ACTION_EXPORT)
					handler:function(){        
						if (getSelectedKey(gridResultados, "cdpolmtra") != "") {
                           var asignarNumeroA = 'cdnumpol';//nombre del campo al que se asignara el numero
                           numeracionPolizas(gridResultados, asignarNumeroA);
                           
                           //numeracionPolizas(gridResultados.getSelectionModel().getSelected().get("dsunieco"),gridResultados.getSelectionModel().getSelected().get("dselemen"),gridResultados.getSelectionModel().getSelected().get("dsramo"),COD_NUMPOL_REGRESO);
                          // alert(COD_NUM_POL);
                           //asignar(getSelectedRecord(gridResultados));
                    		}else{
                        		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                	    }	
					
                	 }
				},
				//boton para llamar numeración renovacion
				{
					id : 'asignarNumeroARenovacion',
					text : 'Asignar N&uacute;mero a Renovaci&oacute;n',
					tooltip : 'Asignar N&uacute;mero a Renovaci&oacute;n',
					//handler : exportButton(_ACTION_EXPORT)
					handler:function(){        
						if (getSelectedKey(gridResultados, "cdpolmtra") != "") {
                           var asignarNumeroA = 'cdnumren';//nombre del campo al que se asignara el numero
                           numeracionPolizas(gridResultados, asignarNumeroA);
                           
                           //numeracionPolizas(gridResultados.getSelectionModel().getSelected().get("dsunieco"),gridResultados.getSelectionModel().getSelected().get("dselemen"),gridResultados.getSelectionModel().getSelected().get("dsramo"),COD_NUMPOL_REGRESO);
                          // alert(COD_NUM_POL);
                           //asignar(getSelectedRecord(gridResultados));
                    		}else{
                        		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                	    }	
					
                	 }
				},
				//boton Regresar
				{
					id : 'regresarNumeracionPolizas',
					text : 'Regresar',
					tooltip : 'Regresar a Numeraci&oacute;n de P&oacute;lizas',
					handler:function(){
						window.location  = 'numeroPolizas.action';
                	 }
				}
				],
		width : 668,
		frame : true,
		height : 340,
        title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',

		sm : new Ext.grid.RowSelectionModel({
			singleSelect : true,
			listeners : {
				rowselect : function(sm, row, rec) {
					selectedId = storeGridResultado.data.items[row].id;
					cdPolmtra = rec.get('cdpolmtra');
					dsNivel = rec.get('dselemen');
					dsAseg = rec.get('dsunieco');
					dsProd = rec.get('dsramo');
					dsTipSit = rec.get('dstipsit');
					dsTipo = rec.get('dstipo');
					dsPolizaA = rec.get('nmpoliza');
					dsPolizaC = rec.get('nmpoliex');
					dsFI = rec.get('feinicio');
					dsFF = rec.get('fefin');
					cdCia = rec.get('cdcia');
					cdElemento = rec.get('cdelemento');
					cdRamo = rec.get('cdramo');
					cdTipSit = rec.get('cdtipsit');
					cdTipo = rec.get('cdtipo');
				}
			}

		}),
		stripeRows: true,
		collapsible: true,
		bbar: new Ext.PagingToolbar({
			pageSize: itemsPerPage,
			store: storeGridResultado,
			displayInfo: true
			/*displayMsg : 'Displaying rows {0} - {1} of {2}',
			emptyMsg : 'No rows to display'*/
		})
	});

	Ext.getCmp('maestraAgregar').on('click', function() {
		agregarMaestra();
	});

	Ext.getCmp('maestraEditar').on('click', function() {
		if(cdPolmtra!=""){
			editarMaestra(cdPolmtra,dsNivel,dsAseg,dsProd,dsTipSit,dsTipo,dsPolizaA,dsPolizaC,dsFI,dsFF,cdCia,cdElemento,cdRamo,cdTipSit,cdTipo);
		}else{
			Ext.Msg.alert('P&oacute;liza Maestra','No ha seleccionado ningun registro');
		}
	});

	// ******************PANEL GRID RESULTADOS******************
	/*var resultadosPanelGrid = new Ext.form.FormPanel({
		// el:'listadoRenovacion',
		title : '<span style="color:#98012e;font-size:12px;">Listado</span>',
		buttonAlign : "center",
		labelAlign : 'right',
		collapsible : true,
		frame : true,
		width : 668,
		autoHeight : true,
		items : [{
			layout : 'form',
			border : false,
			items : [{
				// bodyStyle:'background: white',
				labelWidth : 100,
				layout : 'form',
				frame : true,
				baseCls : '',
				buttonAlign : "center",
				items : [gridResultados]
			}]
		}]
	});*/

	// ******************PANEL GRID MAIN******************
	/*var mainPanelGrid = new Ext.Panel({
		border : false,
		width : 680,
		autoHeight : true,
		items : [{
			title : '<span style="color:black;font-size:14px;">P&oacute;liza Maestra</span>',
			labelAlign : 'center',
			layout : 'form',
			// collapsible: true,
			frame : true,
			buttonAlign : 'center',
			items : [filtroForm, resultadosPanelGrid]
		}]
	});*/
	//mainPanelGrid.render('mainPolizaMaestra');
	filtroForm.render();

	//PARAMETROS DE LA PANTALLA DE ROL
	//Nivel.setValue(DESC_NIVEL);
	//Aseguradora.setValue(DESC_ASEGURADORA);
	//Producto.setValue(DESC_PRODUCTO);
	Ext.getCmp('dsNivelId').setValue(DESC_NIVEL);
	Ext.getCmp('dsUniecoId').setValue(DESC_ASEGURADORA);
	Ext.getCmp('dsRamoId').setValue(DESC_PRODUCTO);
	//FIN DE PARAMETROS DE LA PANTALLLA DE ROL	
	gridResultados.render();
	
	//PARAMETROS DE LA PANTALLLA DE NUMERACION DE POLIZAS
	if(DES_ELEMENTO != ""){ Ext.getCmp('dsNivelId').setValue(DES_ELEMENTO); }
	if(DES_UNIECO != ""){ Ext.getCmp('dsUniecoId').setValue(DES_UNIECO); }
	if(DES_RAMO != ""){ Ext.getCmp('dsRamoId').setValue(DES_RAMO); }
	//FIN DE PARAMETROS DE LA PANTALLLA DE NUMERACION DE POLIZAS

		//if(COD_NUMPOL == ""){
		//alert(1);
				storeGridResultado.baseParams = {
					'parametrosBusqueda.dselemen' : Ext.getCmp('dsNivelId').getValue(),
					'parametrosBusqueda.dsunieco' : Ext.getCmp('dsUniecoId').getValue(),
					'parametrosBusqueda.dsramo' : Ext.getCmp('dsRamoId').getValue()
				};
				storeGridResultado.load({
						callback: function(r, options, success){
							if(!success){
							//console.log(storeGridResultado.reader.jsonData.mensajeError);
							Ext.MessageBox.alert('Buscar',storeGridResultado.reader.jsonData.mensajeError);
							gridResultados.store.removeAll();
							}
						  }
						});
	/*}else{
		alert(2);
		Ext.getCmp('dsNivelId').setValue(DESC_NIV);
	    Ext.getCmp('dsUniecoId').setValue(DESC_ASEG);
	    Ext.getCmp('dsRamoId').setValue(DESC_PROD);
	    alert(CODIGO_POLMTRA);
		_url = _NUEVA_BUSQUEDA+'?parametrosBusqueda.dselemen='+DESC_NIV+'&parametrosBusqueda.dsunieco='+DESC_ASEG+'&parametrosBusqueda.dsramo='+DESC_PROD+'&parametrosBusqueda.dsramo='+CODIGO_POLMTRA+'?store=STORE';
	    storeGridResultado.load({ 
						callback: function(r, options, success){
							if(!success){
							//console.log(storeGridResultado.reader.jsonData.mensajeError);
							Ext.MessageBox.alert('Buscar',storeGridResultado.reader.jsonData.mensajeError);
							gridResultados.store.removeAll();
							}
						  }
						});
	   
	};*/
	
	
	
	/*


	storeGridResultado.load({ 

			callback: function(r, options, success){
						if(!success){
						//console.log(storeGridResultado.reader.jsonData.mensajeError);
						Ext.MessageBox.alert('Buscar',storeGridResultado.reader.jsonData.mensajeError);
						gridResultados.store.removeAll();
						}
				}
	});*/

	if (boton!=true)
	{
		if (CODIGO_TIPO==1)
		{	
			Ext.getCmp('maestraVolverGrupoPersona').setVisible(false);
		}else{
			Ext.getCmp('maestraVolverRol').setVisible(false);
		}
	}else
	{
		Ext.getCmp('maestraVolverGrupoPersona').setVisible(false);
		Ext.getCmp('maestraVolverRol').setVisible(false);
	};



	// ******************AGREGAR COBERTURAS******************
	agregarMaestra = function() {
	    
	    var Nivel = new Ext.form.ComboBox({ 
	        tpl: '<tpl for="."><div ext:qtip="{dsCliente}.{cdCliente}" class="x-combo-list-item">{dsCliente}</div></tpl>',
	        store: storeNivel,
	        width: 200,
	        allowBlank: false,
	        mode: 'local',
	        name: 'parametrosBusqueda.cdelemento',
	        hiddenName: 'parametrosBusqueda.cdelemento',
	        typeAhead: true,
	        labelSeparator: ':',          
	        triggerAction: 'all',
	        valueField: 'cdCliente',
	        displayField: 'dsCliente',
	        fieldLabel: 'Nivel',
	        emptyText: 'Seleccione...',
	        selectOnFocus:true,
	        id: 'comboNivel',
	        onSelect: function (record) {
                        
                        storeAseg.removeAll();
                        Ext.getCmp("comboAseg").clearValue();
                        storeProducto.removeAll();
                        Ext.getCmp("comboProducto").clearValue();
                        
                    	this.setValue(record.get("cdCliente"));
                       
                        storeAseg.load({
		                       			params:{cdCliente: Ext.getCmp("comboNivel").getValue()}
		                       		});
                        
                        this.collapse();
                      }
	    });
		var Aseguradora = new Ext.form.ComboBox({ 
		    tpl: '<tpl for="."><div ext:qtip="{label}.{value}" class="x-combo-list-item">{label}</div></tpl>',
		    store: storeAseg,
		    width: 200,
	        allowBlank: false,
	        mode: 'local',
		    name: 'parametrosBusqueda.cdcia',
		    hiddenName: 'parametrosBusqueda.cdcia',
		    typeAhead: true,
		    labelSeparator: ':',          
		    triggerAction: 'all',
		    valueField: 'value',
		    displayField: 'label',
		    fieldLabel: 'Aseguradora',
		    emptyText: 'Seleccione...',
		    selectOnFocus:true,
		    id: 'comboAseg',
		    onSelect: function (record) {
                        
                        storeProducto.removeAll();
                        Ext.getCmp("comboProducto").clearValue();
                    	this.setValue(record.get("value"));
                       
                        storeProducto.load({
		                       			params:{cdAsegurado: Ext.getCmp("comboAseg").getValue()}
		                       		});
                        
                        this.collapse();
                      }
		  
		});
		var Producto = new Ext.form.ComboBox({ 
		    tpl: '<tpl for="."><div ext:qtip="{label}.{value}" class="x-combo-list-item">{label}</div></tpl>',
		    store: storeProducto,
		    width: 200,
	        allowBlank: false,
		    mode: 'local',
		    name: 'parametrosBusqueda.cdramo',
		    hiddenName: 'parametrosBusqueda.cdramo',
		    typeAhead: true,
		    labelSeparator: ':',          
		    triggerAction: 'all',
		    valueField: 'value',
		    displayField: 'label',
		    fieldLabel: 'Producto',
		    emptyText: 'Seleccione...',
		    selectOnFocus:true,
		    id:'comboProducto',
		    onSelect: function (record) {
		    	storeTipoDeSituacion.removeAll();
                Ext.getCmp('comboTipoDeSituacion').clearValue();
		    	this.setValue(record.get("value"));                     
				storeTipoDeSituacion.load({
		        	params:{cdRamo: this.getValue()}
				});
				this.collapse();
			}
		});
		var TipoDeSituacion = new Ext.form.ComboBox({ 
		    tpl: '<tpl for="."><div ext:qtip="{label}.{value}" class="x-combo-list-item">{label}</div></tpl>',
		    store: storeTipoDeSituacion,
		    width: 200,
	        allowBlank: false,
		    mode: 'local',
		    name: 'parametrosBusqueda.cdtipsit',
		    hiddenName: 'parametrosBusqueda.cdtipsit',
		    typeAhead: true,
		    labelSeparator: ':',          
		    triggerAction: 'all',
		    valueField: 'value',
		    displayField: 'label',
		    fieldLabel: 'Riesgo',
		    emptyText: 'Seleccione...',
		    selectOnFocus:true,
		    id:'comboTipoDeSituacion'
		});
		var Tipos = new Ext.form.ComboBox({ 
		    tpl: '<tpl for="."><div ext:qtip="{label}.{value}" class="x-combo-list-item">{label}</div></tpl>',
		    store: storeTipos,
		    width: 200,
	        allowBlank: false,
		    mode: 'local',
		    name: 'parametrosBusqueda.cdtipo',
		    hiddenName: 'parametrosBusqueda.cdtipo',
		    typeAhead: true,
		    labelSeparator: ':',          
		    triggerAction: 'all',
		    valueField: 'value',
		    displayField: 'label',
		    fieldLabel: 'Tipo',
		    emptyText: 'Seleccione...',
		    selectOnFocus:true
		});
		/*var PolizaAseg = new Ext.form.NumberField({
			name : 'parametrosBusqueda.nmpoliex',
			//value : '',
			fieldLabel : 'P&oacute;liza aseguradora',
			width : 200
		});
		var PolizaCat = new Ext.form.TextField({
			name : 'parametrosBusqueda.nmpoliza',
			//value : '',
			fieldLabel : 'P&oacute;liza catweb',
			width : 200
		});*/
		var PolizaAseg = new Ext.form.TextField({
			name : 'parametrosBusqueda.nmpoliza',
			allowBlank: false,
			fieldLabel : 'P&oacute;liza aseguradora',
			width : 200
		});		
		var PolizaCat = new Ext.form.NumberField({
			name : 'parametrosBusqueda.nmpoliex',
			//value : '',
			fieldLabel : 'P&oacute;liza catweb',
			maxLength: 10, 
			width : 200
		});		
		
		
		var FechaI = new Ext.form.DateField({
			name : 'parametrosBusqueda.feinicio',
			//value : '',
			fieldLabel : 'Inicio',
			format:'d/m/Y',
	        allowBlank: false,
			width : 100
		});
		var FechaF = new Ext.form.DateField({
			name : 'parametrosBusqueda.fefin',
			//value : '',
			fieldLabel : 'Fin',
			format:'d/m/Y',
	        allowBlank: false,
			width : 100
		});

		var agregarForm = new Ext.form.FormPanel({
			url : 'procesoemision/guardaPolizaMaestra.action',
			id : 'agregarCoberturaForm',
			boder : false,
			frame : true,
			autoScroll : true,
			method : 'post',
			width : 540,
			buttonAlign : "center",
			baseCls : 'x-plain',
			labelWidth : 75,
			items : [
				Nivel,
				Aseguradora,
				Producto,
				TipoDeSituacion,
				Tipos,
				PolizaAseg,
				PolizaCat,
				FechaI,
				FechaF
			]
		});

		var windowAgregar = new Ext.Window({
			title : 'Agregar P&oacute;liza Maestra',
			width : 460,
			height : 350,
			autoScroll : true,
			maximizable : true,
			minWidth : 440,
			minHeight : 300,
			layout : 'fit',
			modal : true,
			plain : true,
			bodyStyle : 'padding:5px;',
			buttonAlign : 'center',
			items : agregarForm,
			buttons : [{
				text : 'Guardar',
				tooltip : 'Guardar',
				handler : function() {
				if (agregarForm.form.isValid()) {
					agregarForm.form.submit({
						url : '',
						waitTitle : 'Espere',
						waitMsg : 'Procesando',
						failure : function(form, action) {
							var msjResult = Ext.util.JSON.decode(action.response.responseText).messageResult;
							Ext.MessageBox.alert( 'Error', (msjResult) ? msjResult : 'Error al crear registro' );
						},
						success : function(form, action) {
							Ext.MessageBox.alert('P&oacute;liza Maestra', Ext.util.JSON.decode(action.response.responseText).messageResult);
							windowAgregar.close();
							storeGridResultado.load();
						}
					});
				}else{
					Ext.MessageBox.alert('Aviso', 'Por favor complete la informaci&oacute;n requerida');
				}   
				}
			}, {
				text : 'Regresar',
				tooltip : 'Cierra la ventana',
				handler : function() {
					windowAgregar.close();
				}// handler
			}]
		});
		windowAgregar.show();
	};
	
	editarMaestra = function(cdPolmtra,dsNivel,dsAseguradora,dsProducto,dsTipSit,dsTipos,dsPolizaAseg,dsPolizaCat,dsFI,dsFF,cdCia,cdElemento,cdRamo,cdTipSit,cdTipo) {
		//  alert(dsPolizaAseg);
		//******************Store para combo de Nivel******************

	    
	    //var params = "parametrosBusqueda.cdpolmta="+cdPolmtra;
/*
		var connect = new Ext.data.Connection();
		connect.request ({
				url:'procesoemision/getPolizaMaestra.action',
				method:'POST',
				successProperty :'@success',
				params:params,
				callback: function (options, success, response) {
					
					if (Ext.util.JSON.decode(response.responseText).success == false) {
                    	Ext.Msg.alert('Error', 'Ocurrio un error en la transaccion');
                    }else{
                    	
                    }
                     
				}
		});*/
                    	//Ext.Msg.alert('TEST', 'Donas');
                    	//Ext.Msg.alert('TEST',Ext.util.JSON.decode(response.responseText).polizaMaestraReg.dselemen);
						var idPoliza = new Ext.form.Hidden({
							name : 'parametrosBusqueda.cdpolmtra',
							value : cdPolmtra
						});
						var hNivel = new Ext.form.Hidden({
							name : 'parametrosBusqueda.cdelemento',
							value : cdElemento
						});
						var hAseg = new Ext.form.Hidden({
							name : 'parametrosBusqueda.cdcia',
							value : cdCia
						});
						var hProd = new Ext.form.Hidden({
							name : 'parametrosBusqueda.cdramo',
							value : cdRamo
						});
						var hCdTipSit = new Ext.form.Hidden({
							name : 'parametrosBusqueda.cdtipsit',
							value : cdTipSit
						});
						var hTipo = new Ext.form.Hidden({
							name : 'parametrosBusqueda.cdtipo',
							value : cdTipo
						});
					    var Nivel = new Ext.form.ComboBox({ 
					        tpl: '<tpl for="."><div ext:qtip="{dsCliente}.{cdCliente}" class="x-combo-list-item">{dsCliente}</div></tpl>',
					        store: storeNivel,
					        width: 200,
					        mode: 'local',
					        name: 'nivel',
					        hiddenName: 'nivel',
					        typeAhead: true,
					        labelSeparator: ':',          
					        triggerAction: 'all',
					        valueField: 'cdCliente',
					        displayField: 'dsCliente',
					        fieldLabel: 'Nivel',
					        emptyText: 'Seleccione...',
					        disabled: true, 
					        selectOnFocus:true
					    });
						var Aseguradora = new Ext.form.ComboBox({ 
						    tpl: '<tpl for="."><div ext:qtip="{label}.{value}" class="x-combo-list-item">{label}</div></tpl>',
						    store: storeAseg,
						    width: 200,
						    mode: 'local',
						    name: 'aseg',
						    hiddenName: 'aseg',
						    typeAhead: true,
						    labelSeparator: ':',          
						    triggerAction: 'all',
						    valueField: 'value',
						    displayField: 'label',
						    fieldLabel: 'Aseguradora',
						    emptyText: 'Seleccione...',
						    disabled: true,
						    selectOnFocus:true
						});
						var Producto = new Ext.form.ComboBox({ 
						    tpl: '<tpl for="."><div ext:qtip="{label}.{value}" class="x-combo-list-item">{label}</div></tpl>',
						    store: storeProducto,
						    width: 200,
						    mode: 'local',
						    name: 'prod',
						    hiddenName: 'prod',
						    typeAhead: true,
						    labelSeparator: ':',          
						    triggerAction: 'all',
						    valueField: 'value',
						    displayField: 'label',
						    fieldLabel: 'Producto',
						    emptyText: 'Seleccione...',
						    disabled: true,
						    selectOnFocus:true
						});
						var TipoDeSituacion = new Ext.form.ComboBox({ 
		    				tpl: '<tpl for="."><div ext:qtip="{label}.{value}" class="x-combo-list-item">{label}</div></tpl>',
		    				store: storeTipoDeSituacion,
		    				width: 200,
	        				allowBlank: false,
		    				mode: 'local',
		    				name: 'tipsit',
		    				hiddenName: 'tipsit',
		    				typeAhead: true,
		    				labelSeparator: ':',          
		    				triggerAction: 'all',
		    				valueField: 'value',
		    				displayField: 'label',
		    				fieldLabel: 'Riesgo',
		    				emptyText: 'Seleccione...',
						    disabled: true,
						    selectOnFocus:true
						});
						var Tipos = new Ext.form.ComboBox({ 
						    tpl: '<tpl for="."><div ext:qtip="{label}.{value}" class="x-combo-list-item">{label}</div></tpl>',
						    store: storeTipos,
						    width: 200,
						    mode: 'local',
						    name: 'tipo',
						    hiddenName: 'tipo',
						    typeAhead: true,
						    labelSeparator: ':',          
						    triggerAction: 'all',
						    valueField: 'value',
						    displayField: 'label',
						    fieldLabel: 'Tipo',
						    emptyText: 'Seleccione...',
						    disabled: true,
						    selectOnFocus:true
						});
						var PolizaAseg = new Ext.form.NumberField({
							name : 'parametrosBusqueda.nmpoliza',
							//value : '',
							clearCls:'',
							fieldLabel : 'P&oacute;liza aseguradora',
							width : 200
						});
						
						var PolizaCat = new Ext.form.TextField({
							name : 'parametrosBusqueda.nmpoliex',
							value : '',
							fieldLabel : 'P&oacute;liza catweb',
							width : 200
						});
						var FechaI = new Ext.form.DateField({
							name : 'parametrosBusqueda.feinicio',
							value : '',
							fieldLabel : 'Inicio',
							format:'d/m/Y',
							width : 100
						});
						var FechaF = new Ext.form.DateField({
							name : 'parametrosBusqueda.fefin',
							value : '',
							fieldLabel : 'Fin',
							format:'d/m/Y',
							width : 100
						});
				
						var editarForm = new Ext.form.FormPanel({
							url : 'procesoemision/guardaPolizaMaestra.action',
							id : 'agregarCoberturaForm',
							boder : false,
							frame : true,
							autoScroll : true,
							method : 'post',
							width : 540,
							buttonAlign : "center",
							baseCls : 'x-plain',
							labelWidth : 115,
							items : [
								idPoliza,
								hNivel,
								hAseg,
								hProd,
								hCdTipSit,
								hTipo,
								Nivel,
								Aseguradora,
								Producto,
								TipoDeSituacion,
								Tipos,
								PolizaAseg,
								PolizaCat,
								FechaI,
								FechaF
							]
						});
				
						var windowEditar = new Ext.Window({
							title : 'Editar P&oacute;liza Maestra',
							width : 460,
							height : 350,
							autoScroll : true,
							maximizable : true,
							minWidth : 440,
							minHeight : 300,
							layout : 'fit',
							modal : true,
							plain : true,
							bodyStyle : 'padding:5px;',
							buttonAlign : 'center',
							items : editarForm,
							buttons : [{
								text : 'Guardar',
								tooltip : 'Guardar',
								handler : function() {
									editarForm.form.submit({
										url : '',
										waitTitle : 'Espere',
										waitMsg : 'Procesando',
										failure : function(form, action) {
											var msjResult = Ext.util.JSON.decode(action.response.responseText).messageResult; 
											Ext.MessageBox.alert('Error', (msjResult) ? msjResult : 'Error al editar registro' );
										},
										success : function(form, action) {
											Ext.MessageBox.alert('P&oacute;liza Maestra', Ext.util.JSON.decode(action.response.responseText).messageResult);
											windowEditar.close();
											storeGridResultado.load();
										}
									});
								}
							}, {
								text : 'Regresar',
								tooltip : 'Cierra la ventana',
								handler : function() {
									windowEditar.close();
								}// handler
							}]
						});

						Nivel.setValue(dsNivel);
						Aseguradora.setValue(dsAseguradora);
						Producto.setValue(dsProducto);
						Tipos.setValue(dsTipos);
						TipoDeSituacion.setValue(dsTipSit);
						if (dsPolizaAseg != ""){
						
						PolizaAseg.setValue(dsPolizaAseg);
						}
						
						PolizaCat.setValue(dsPolizaCat);
						var fechaIn = dsFI;
						if(fechaIn!=""){
							var fechaInS = fechaIn.split(" ");
							FechaI.setValue(fechaInS[0]);
						}						
						var fechaFi = dsFF;
						if(fechaFi!=""){
							var fechaFiS = fechaFi.split(" ");
							FechaF.setValue(fechaFiS[0]);
						}

						windowEditar.show();


	};
	
	function asignar(record)	{
			
				window.location=_ACTION_ASIGNAR_NUMERO +"?dsElemen ="+record.get('dselemen')+"& dsUnieco="+record.get('dsunieco')+"& dsRamo="+record.get('dsramo');
			
			};

});