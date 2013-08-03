var helpMap = new Map();

Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";

    var form_ejecutivo_reg = new Ext.data.JsonReader({
						root: 'listaEjecutivo',
						totalProperty: 'totalCount',
						successProperty: '@success'
						}, [
					        {name: 'cdEjecutivo', type: 'string', mapping: 'cdAgente' },
					        {name: 'dsPersona',  type: 'string',  mapping:'dsPerson'},
					        {name: 'status',  type: 'string',  mapping:'dsEstado'}
					        
						]
		);


    /********* Comienza el form ********************************/
    var el_form = new Ext.FormPanel ({
    		id: 'el_form',
            renderTo: 'formBusqueda',
	  		title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('el_formMantEjecutivosCta', helpMap,'Atributos de Ejecutivos de Cuenta')+'</span>',
            //title: '<span style="color:black;font-size:14px;">Configuraci&oacute;n de Secciones</span>',
            url : _ACTION_OBTENER_EJECUTIVO,
            frame : true,
            width: 500,
            bodyStyle : 'padding:5px 5px 0',
            bodyStyle: 'background:white',
            buttonAlign: "center",
            labelAlign: 'right',
            waitMsgTarget : true,
            layout: 'column',
            //labelWidth: 65,
            layoutConfig: {columns: 2},
            labelAlign: 'right',
            reader: form_ejecutivo_reg,
            items: [
            		{
            			layout: 'form', columnWidth: .5,
            			items: [
		            			{
		            				xtype: 'hidden',
		            				name: 'cdPerson',
		            				id: 'cdPerson'
		            			},
		                        {
		                        xtype: 'textfield',
		                        readOnly: true,
		                        labelWidth: 40,
		                    	fieldLabel: getLabelFromMap('cdEjecutivo', helpMap,'Codigo'), 
								tooltip: getToolTipFromMap('cdEjecutivo', helpMap, ''),  				                    	
		                        //fieldLabel: 'Seccion', 
		                        id: 'cdEjecutivo', 
		                        name: 'cdEjecutivo'
		                        }
            			]
            		},
            		{
            			layout: 'form', columnWidth: .5,
            			items: [
					            {   xtype: 'textfield', 
					                id: 'dsPersona',
					                name: 'dsPersona',
					                anchor: '100%',
					                readOnly: true, 
					                labelWidth: 40,
						            fieldLabel: getLabelFromMap('cdPersona',helpMap,'Persona'),
					                tooltip: getToolTipFromMap('cdPersona',helpMap, '')			          		
					           	}
            			]
            		},
            		{
            			layout: 'form', columnWidth: .5,
            			items: [
					            {   xtype: 'textfield', 
					                id: 'status',
					                name: 'status',
					                readOnly: true,
						            fieldLabel: getLabelFromMap('status',helpMap,'Estatus'),
					                tooltip: getToolTipFromMap('status',helpMap, '')          		
					           	}
            			]
            		}
                    ]
    });
    /********* Fin del form ************************************/

	/********* Comienzo del grid *****************************/
    
    
       //alert(grid.getStore().getAt(rowIndex).get('otTabval'));
      //  console.log(store);
		//Definición Column Model
	    var cm = new Ext.grid.ColumnModel([
	    		{
	    			dataIndex: 'cdAtribu',
	    			hidden: true
	    		},{
				   	header: getLabelFromMap('dsAtributo',helpMap, 'Atributo'),
			        tooltip: getToolTipFromMap('dsAtributo', helpMap, ''),		           	
		           	//header: "Nombre",
		           	dataIndex: 'dsAtribu',
		           	sortable: true,
		           	width: 190
	        	},{
       			//  Ext.getCmp('grilla').setDataIndex(((getSelectedRecord(grilla).get('otValor'))!="")?'dsvalor':'otValor');
	    			dataIndex: 'otValor', 
	    			
				   	header: getLabelFromMap('dsValor',helpMap, 'Valor'),
			        tooltip: getToolTipFromMap('dsValor', helpMap, 'Valor'),		           	
		           	id: 'otValorId',
		           	//editor: new Ext.form.TextField({id: 'otValor'}),
		           	sortable: true,
		           	width: 180, 
		           	renderer: function(val) {
		           	//Chequea que val != null y que es una fecha válida para JS.
		           	try{
		           		return val.format ('d/m/Y');
		           		}
		           	catch(e)
		           	{return val;}
		           	  }       	           			
		           	
		           		           	
	    		},{
	    			dataIndex: 'otTabval',
	    			header: getLabelFromMap('dsValor',helpMap, 'otTabval'),
			        tooltip: getToolTipFromMap('dsValor', helpMap, 'otTabval'),
	    			id: 'otTabvalId',
	    			width: 130, 
	    			hidden: true
	    		},{
	    			dataIndex: 'swObliga',
	    			header: getLabelFromMap('dsValor',helpMap, 'swObliga'),
			        tooltip: getToolTipFromMap('dsValor', helpMap, 'swObliga'),
	    			id: 'swObligaId',
	    			hidden: true
	    		},{
	    			dataIndex: 'nmLmax',
	    			id: 'nmLmaxId',
	    			hidden: true
	    		},{
	    			dataIndex: 'nmLmin',
	    			id: 'nmLminId',
	    			hidden: true
	    		},{
	    			dataIndex: 'swFormat',
	    			header: getLabelFromMap('dsValor',helpMap, 'swFormat'),
			        tooltip: getToolTipFromMap('dsValor', helpMap, 'swFormat'),
	    			id: 'swFormatId',
	    			hidden: true
	    		},{
	    			dataIndex: 'gbSwformat',
	    			id: 'gbSwformatId',
	    			hidden: true
	    		}
	           	]);
	           	
       // console.log(store);

		//Crea el Store
		function crearGridStore(){
		 			store = new Ext.data.Store({
		    			proxy: new Ext.data.HttpProxy({
						url: _ACTION_OBTENER_ATRIBUTOS
		                }),
		                reader: new Ext.data.JsonReader({
		            	root:'listaAtributosEjecutivos',
		            	totalProperty: 'totalCount',
			            successProperty : '@success'
			        },[
					        {name: 'cdAtribu', type: 'string', mapping: 'cdAtribu'},
					        {name: 'dsAtribu',  type: 'string',  mapping:'dsAtribu'},
					        {name: 'dsvalor',  type: 'string',  mapping:'dsvalor'},
					        {name: 'otValor',  type: 'string',  mapping:'otValor', dateFormat: 'd/m/Y'},
					        {name: 'otTabval',  type: 'string',  mapping:'otTabval'},
					        {name: 'swObliga',  type: 'string',  mapping:'swObliga'},
					        {name: 'nmLmax',  type: 'string',  mapping:'nmLmax'},
					        {name: 'nmLmin',  type: 'string',  mapping:'nmLmin'},
					        {name: 'swFormat',  type: 'string',  mapping:'swFormat'},
					        {name: 'gbSwformat',  type: 'string',  mapping:'gbSwformat'}
					])
		        });
				return store;
		 	}
		//Fin Crea el Store
	
	
	
	var grilla;

	function createGrid(){
		grilla= new Ext.grid.EditorGridPanel({
			id: 'grilla',
	        renderTo:'grid',
	        store:crearGridStore(),
			border:true,
	        cm: cm,
	        loadMask: {msg: getLabelFromMap('400058',helpMap,'Cargando datos ...'), disabled: false},
	        clicksToEdit:1,
	        buttonAlign: 'center',
			buttons:[
		        		{
		      				text:getLabelFromMap('gridButtonGuardar', helpMap,'Guardar'),
		           			tooltip:getToolTipFromMap('gridButtonGuardar', helpMap,'Guardar atributos'),			        			
		            		handler:function(){
		            			var _recs = grilla.store.getModifiedRecords(); //obtenemos las filas modificadas de la grilla
		            			var band = "1"; 
		            			var ds = Ext.getCmp('grilla').store;
		            			
		            			for(var i=0;i< ds.getCount();i++){
		            				
		            				if ((grilla.getStore().getAt(i).get('swObliga') == 'S') &&(grilla.getStore().getAt(i).get('otValor') == ""))
		            					band = "0"
		            			}
		            			if(band == 0){
		            			   Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
		            			}
		            			else{ if (_recs.length==0){
				  	  					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe modificar un registro para realizar esta operaci&oacute;n'));
            	 						}
            	 					  else{
            	 						guardarAtributos();
            	 						}
		            				}						
            	 			}
  
		            	},
		            	{
		      				text:getLabelFromMap('gridButtonExportar', helpMap,'Exportar'),
		           			tooltip:getToolTipFromMap('gridButtonExportar', helpMap,'Exportar la Grilla de Busqueda'),			        			
		                	handler:function(){
                                  var url = _ACTION_EXPORTAR_ATRIBUTOS_EJECUTIVO + '?cdEjecutivo=' + CODIGO_EJECUTIVO;
                	 	          showExportDialog( url );
                               }
		            	},
		            	{
		      				text:getLabelFromMap('gridPersonasButtonRegresar', helpMap,'Regresar'),
		           			tooltip:getToolTipFromMap('gridPersonasButtonRegresar', helpMap,'Regresa a la pantalla anterior'),
		           			handler: function () {
		           				window.location.href = _ACTION_REGRESAR;
		           			}			        			
		            	}
	            	],
	    	width:500,
	    	frame:true,
			height:578,
			sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			walkCells : function(J, C, B, E, I) {/*sobrescribo el walkCells para manejar la tecla Tab*/
						var H = this.colModel, F = H.getColumnCount();
						var A = this.store, G = A.getCount(), D = true;
						if (B < 0) {
							if (C < 0) {
								J--;
								D = false
							}
							while (J >= 0) {
								if (!D) {
									C = F - 1
								}
								D = false;
								while (C >= 0) {
									if (E.call(I || this, J, C, H) === true) {
										celdaEditable(I.grid, J, C, E); /*llamo a la funcion que setea el tipo de la celda y la hace editable*/
										return [J, C]
									}
									C--
								}
								J--
							}
						} else {
							if (C >= F) {
								J++;
								D = false
							}
							while (J < G) {
								if (!D) {
									C = 0
								}
								D = false;
								while (C < F) {
									if (E.call(I || this, J, C, H) === true) {
										celdaEditable(I.grid, J, C, E);/*llamo a la funcion que setea el tipo de la celda y la hace editable*/
										return [J, C]
									}
									C++
								}
								J++
							}
						}
						celdaEditable(I.grid, J, C, E);/*llamo a la funcion que setea el tipo de la celda y la hace editable*/
						return null
					},
			//COMIENZO LISTENER
			listeners:{
   				'cellclick' : {
	   							fn: function (grid, rowIndex, columnIndex, e){
	   									celdaEditable(grid, rowIndex, columnIndex, e);} /*llamo por primera vez a la funcion que setea el tipo de la celda y la hace editable haciendo clik*/
   								}
   			}, // FIN LISTENER
			
			bbar: new Ext.PagingToolbar({
					pageSize:20,
					store: store,
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })
			});
		grilla.render()
	}
function celdaEditable(grid, rowIndex, columnIndex, e){/*funcion que hace editable la celda de la grilla y setea el tipo*/
			
			var _cId = grid.getColumnModel().getColumnId(columnIndex); // columnId Ex: ('yourCol')
			var _otTabval = grid.getStore().getAt(rowIndex).get('otTabval');
			var _swFormat = grid.getStore().getAt(rowIndex).get('swFormat');
			var _swObliga = grid.getStore().getAt(rowIndex).get('swObliga');
			var _nmLmax = grid.getStore().getAt(rowIndex).get('nmLmax');
			var _nmLmin = grid.getStore().getAt(rowIndex).get('nmLmin');
			if (_cId=="otValorId"){
				var _editor="";
					if (_otTabval != ""){
						//definimos el store para el combo
						var storeListaValores = new Ext.data.Store({
    						proxy: new Ext.data.HttpProxy({
							url: _ACTION_OBTENER_COMBO
                			}),

		                reader: new Ext.data.JsonReader({
            				root:'comboGenerico',
            				totalProperty: 'totalCount',
            				successProperty : '@success'
	        			},
	        			[	
          					{name : 'codigo', mapping : 'codigo', type : 'string'},
             				{name : 'descripcion',mapping : 'descripcion',type : 'string'}
	             		]
	        				),
	        			baseParams: {idTablaLogica: grid.getStore().getAt(rowIndex).get('otTabval')}
    					});
						storeListaValores.load();

						_editor = new Ext.grid.GridEditor(new Ext.form.ComboBox({
							tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
            				store: storeListaValores, 
            				//anchor:'100%',
            				width: 140, 
            				displayField:'descripcion', 
            				valueField: 'codigo', 
            				//hiddenName: 'cdValor',
            				typeAhead: true, 
            				//dataIndex: 'otValor',
            				triggerAction: 'all', 
            				lazyRender:   true, 
            				emptyText:'Selecione ...', 
            				selectOnFocus:true,
            				forceSelection: true, 
            				labelSeparator:'',
            				allowBlank: ((_swObliga == 'S')?false:true),
            				mode: 'local'/*,
            				renderer: renderCombo(_editor)*/
						
						}));
						
						
					}else {if (_swFormat == 'A'){
					_editor = new Ext.grid.GridEditor(new Ext.form.TextField({
						dataIndex: 'otValor',
						header: getLabelFromMap('dsValor',helpMap, 'Valor'),
						//comentado porque bloquea los mensajes de validacion //tooltip: getToolTipFromMap('dsValor', helpMap, 'Valor Texto'),	
						sortable: true,
						width: 140,
						maxLength: ((_nmLmax != '')?_nmLmax:Number.MAX_VALUE),
						//maxLengthText: 'Solo se aceptan hasta '+ ((_nmLmax != '')?_nmLmax:Number.MAX_VALUE) +' caracteres',
						allowBlank: ((_swObliga == 'S')?false:true)
      				}));
						
					}else if ((_swFormat == 'N')||(_swFormat=='P')){
					_editor = new Ext.grid.GridEditor(new Ext.form.NumberField({
						dataIndex: 'otValor',
						header: getLabelFromMap('dsValor',helpMap, 'Valor'),
						//comentado porque bloquea los mensajes de validacion //tooltip: getToolTipFromMap('dsValor', helpMap, 'Valor Numérico'),	
						sortable: true,
						width: 140,
						maxLength: ((_nmLmax != '')?_nmLmax:Number.MAX_VALUE),
						allowBlank: ((_swObliga == 'S')?false:true)
						}));
						  
					} else if ((_swFormat == 'F')||(_swFormat == 'D')){
					_editor = new Ext.grid.GridEditor(new Ext.form.DateField({
						dataIndex: 'otValor',
						header: getLabelFromMap('dsValor',helpMap, 'Valor'),
						//comentado porque bloquea los mensajes de validacion //tooltip: getToolTipFromMap('dsValor', helpMap, 'Valor Fecha'),
						format: 'd/m/Y',
						altFormats : 'm/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g',
				        anchor: '100%', 
						sortable: true,
						width: 140,
						allowBlank: ((_swObliga == 'S')?false:true) 
					
						}));
					 }
					}
				grid.getColumnModel().setEditor(columnIndex,_editor);/*Aqui se hace editable la columna de la grilla*/
				
				if (_otTabval != ""){
					grid.getColumnModel().setRenderer(columnIndex, function (value, metadata, record, rowIndex, colIndex, store) {
						/*console.log(value);
						console.log(metadata);
						console.log(record);
						console.log(rowIndex);
						console.log(colIndex);
						console.log(store);*/
						var idx = storeListaValores.find('codigo', value);
						var rec = storeListaValores.getAt(idx);
						//console.log(idx);
						//console.log(rec);
						
						return (rec == null)?value:rec.get('descripcion');
						
					});
				}
				//console.log(_editor)
				//alert(_editor.field.maxLengthText);
				//alert(_editor.record.data.nmLmax);
				//alert(_editor.length);
				
			
			}
	   }	


	/********* Fin del grid **********************************/
	
	
function formatDate(dateVal){
            return (dateVal && dateVal.format) ? dateVal.dateFormat('d/m/Y') : 'Not Available';
        }

    //Muestra los componentes en pantalla
    el_form.form.load({
    	params: {
    			codEjecutivo: CODIGO_EJECUTIVO
    	},
    	success: function () {
    		reloadGrid();      
    		}
    });
	el_form.render();
	createGrid();
	
//	console.log(grilla.colModel);
	//grilla.on()
	//requerido();
	//Fin Muestra los componentes en pantalla    
//console.log(grilla.colModel);

	function guardarAtributos () {
		var params = "";
		var recs = grilla.store.getModifiedRecords();
		grilla.stopEditing();

		if (recs.length > 0) {
			for (var i=0; i<recs.length; i++) {
				if ((recs[i].get('swFormat') == 'F')||(recs[i].get('swFormat') == 'D'))  {
		          params += "datosAtributos[" + i + "].cdAgente=" + CODIGO_EJECUTIVO + "&" +
							"datosAtributos[" + i + "].cdAtribu=" + recs[i].get('cdAtribu') + "&" +
							"datosAtributos[" + i + "].otValor=" + Ext.util.Format.date(recs[i].get('otValor'), 'd/m/Y') + "&"
		           			}
		         else {params += "datosAtributos[" + i + "].cdAgente=" + CODIGO_EJECUTIVO + "&" +
							"datosAtributos[" + i + "].cdAtribu=" + recs[i].get('cdAtribu') + "&" +
							"datosAtributos[" + i + "].otValor=" + recs[i].get('otValor') + "&"} 
			}
			//alert(params);
			execConnection (_ACTION_GUARDAR_ATRIBUTOS, params, cbkGuardarAtributos);
		}
	}
	
	function cbkGuardarAtributos (_success, _message) {
		if (!_success) {
			
			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
		}else {
			grilla.store.commitChanges();
			Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function() {reloadGrid();});
			
		}
	}
});
 /*********************/
    
   
    function requerido(){
    var cols = Ext.getCmp('grilla').colModel.getColumnCount(); // determino cantidad de columnas de la grilla	
    var rows = Ext.getCmp('grilla').store.getCount();		  // determino cantidad de filas de la grilla
            // alert('rows:'+ rows); 	
              //	var bandValid = 0; // bandera para saber si ya todas las celdas de mi grilla son validas
              	
              	//recorro toda mi grilla preguntando si las celdas son validas
              	//var obliga = grid.getStore().getAt(rowIndex).get('swObliga');
              	 Ext.getCmp('grilla').getColumnModel().setRenderer(2, function (val, cell, record, row, col, store) {
								//if (record != undefined) {
              	 //	if(row == 3){obliga = 'S'}else{obliga = 'N'};
									if (val == '' && record.get('swObliga') == 'S') {
										cell.css += 'x-form-invalid';//'x-grid-red';
										cell.attr = 'ext:qtip="Este campo es requerido"; ext:qclass="x-form-invalid-tip"';
									} else {
										cell.css = '';
										cell.attr = 'ext:qtip=""';
									};
									
                                    if(record.get('otTabval')!=""){val= record.get('dsvalor')}	/*esto es para mostrar la descrición de los combos*/								
									
								//}
	  			    			return val;
							});//
							Ext.getCmp('grilla').getView().renderUI();//render();
             Ext.getCmp('grilla').getView().layout();
    };
    /**********************/	
		
function reloadGrid(){
	var _params = {
			cdEjecutivo: CODIGO_EJECUTIVO
	};
	reloadComponentStore(Ext.getCmp('grilla'), _params, cbkReload);
    
      
}

function cbkReload (_r, _o, _success, _store) {
	if (!_success) {
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
		_store.removeAll();
		
		
	}else{
		requerido();}
	}
