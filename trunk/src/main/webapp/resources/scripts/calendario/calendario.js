Ext.onReady(function(){ 
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

/********* Comienza la grilla ******************************/
	var cm = new Ext.grid.ColumnModel ([
		        {      
			        header: "Pais",
			        dataIndex: 'codPais',
			        id:'codPaisId',
			        hidden : true
				},
		        {      
			        header: "Anio",
			        dataIndex: 'yearCabecera',
			        id:'yearCabeceraId',
			        hidden : true
				},
		        {      
			        header: "Mes",
			        dataIndex: 'codMes',
			        id:'codMesId',
			        hidden : true
				},
				{
			        header: getLabelFromMap('calendarCmDia',helpMap,'D&iacute;a'),
			        tooltip: getToolTipFromMap('calendarCmDia',helpMap,'D&iacute;a calendario'),
					dataIndex: 'dias',
					width: 120,
					sortable: true
				},
				{
			        header: getLabelFromMap('calendarCmDsDia',helpMap,'Descripci&oacute;n'),
			        tooltip: getToolTipFromMap('calendarCmDsDia',helpMap,'Descripci&oacute;n del D&iacute;a calendario'),
					dataIndex: 'dsDia',
					id:'dsDiaId',
					width: 362,
					sortable: true
				}
			]);

	var idPais = new Ext.data.Store({
	        proxy: new Ext.data.HttpProxy({
	            url: _ACTION_OBTENER_PAIS 
	            }),
	        reader: new Ext.data.JsonReader({
	        root: 'comboDatosPais',
	        id: 'codigo'
	        },[
	       {name: 'codPais', mapping:'codigo', type: 'string'},
	       {name: 'desl', mapping:'descripLarga', type: 'string'},
	       {name: 'desc', mapping:'descripCorta', type: 'string'}
	    ])
	});					
	
	var cmbPais = new Ext.form.ComboBox({
	    tpl: '<tpl for="."><div ext:qtip="{codPais}.{desc}" class="x-combo-list-item">{desc}</div></tpl>',
	    store: idPais,
	    id:'codPaisId',
	    name: 'codPaisName',
	    displayField:'desc',
	    valueField:'codPais',
	    hiddenName: 'codPais',
	    typeAhead: true,
	    mode: 'local',
	    triggerAction: 'all',
	    fieldLabel: getLabelFromMap('calendarCboIdPais',helpMap,'Pa&iacute;s'),
	    tooltip:getToolTipFromMap('calendarCboIdPais',helpMap,'Seleccione Pa&iacute;s '),
	    labelAlign:'right',
	    anchor:'95%',
	    allowBlank: false,
	    sortInfo: {field: "desc", direction: "ASC"},
	    selectOnFocus:true,
	    forceSelection:true,
	    emptyText:'...',
	    onSelect: function (record) {
		       this.setValue(record.get('codPais'));
		       cargar();
		       reloadGrid();
		       this.collapse();
		 }
	   });	

	var idMes = new Ext.data.Store({
	        proxy: new Ext.data.HttpProxy({
	            url: _ACTION_OBTENER_MES 
	            }),
	        reader: new Ext.data.JsonReader({
	        root: 'comboDatosMes',
	        id: 'datosMes'
	        },[
	       {name: 'codMes', mapping:'codigo', type: 'string'},
	       {name: 'desl', mapping:'descripLarga', type: 'string'},
	       {name: 'desc', mapping:'descripCorta', type: 'string'}
	    ])
	});			


	var cmbMes = new Ext.form.ComboBox({
	    tpl: '<tpl for="."><div ext:qtip="{codMes}.{desc}" class="x-combo-list-item">{desc}</div></tpl>',
	    store: idMes,
	    id:'codMesId',
	    displayField:'desc',
	    valueField:'codMes',
	    hiddenName: 'codMes',
	    typeAhead: true,
	    mode: 'local',
	    triggerAction: 'all',
	    fieldLabel: getLabelFromMap('calendarCboIdMes',helpMap,'Mes'),
	    tooltip:getToolTipFromMap('calendarCboIdMes',helpMap,'Seleccione mes calendario '),
	    labelAlign:'right',
	    anchor:'99%',
	    selectOnFocus:true,
	    forceSelection:true,
	    emptyText:'...',
	    onSelect: function (record) {
		       this.setValue(record.get('codMes'));
		       cargar();
		       reloadGrid();
		       this.collapse();
		 }
	    
	   });	

		var today = new Date();
		var yearCabecera =  today.getFullYear();
		var mesCabecera =  today.getMonth() + 1;

	var grilla;
	var dsGrilla;
	function cargar(){

			dsGrilla = new Ext.data.Store ({
			
			proxy: new Ext.data.HttpProxy ({url: _ACTION_BUSCAR_CALENDARIO}),
			reader: new Ext.data.JsonReader({
					root: 'MCalendarioList',
					totalProperty: 'totalCount',
					id:'cdCalendar',
					successProperty: '@success'
					},
					[
						{name: 'dias', type: 'string', mapping: 'dia'},
						{name: 'dsDia', type: 'string', mapping: 'descripcionDia'},
						{name: 'codPais', type: 'string', mapping: 'codigoPais'},
						{name: 'yearCabecera', type: 'string', mapping: 'anio'},
						{name: 'codMes', type: 'string', mapping: 'mes'}
					]
					)
		});
		return dsGrilla
	}



			
function createGrid(){
	   grilla = new Ext.grid.GridPanel ({
				id: 'grilla',
				el: 'gridElementos',
				cm: cm,
				store: cargar(),
				border: true,
				stripeRows: true,
				bodyStyle:'background: white',
				collapsible: true,
				frame: true,
				loadMask: {msg: getLabelFromMap('400058',helpMap,'Cargando datos ...'), disabled: false},
				width: 500,
				height: 360,
				title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
				buttonAlign:'center',
				sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
				buttons: [
						{
		                    text:getLabelFromMap('calendarBtnAdd',helpMap,'Agregar'),
		                    tooltip: getToolTipFromMap('calendarBtnAdd',helpMap,'Agrega d&iacute;as calendario'),
							handler: function(){
							                      if (el_form.form.isValid()){
							                          agregar(Ext.getCmp('codPaisId').getValue(),
														Ext.getCmp('anioId').getValue(),
														Ext.getCmp('codMesId').getValue())
														}
													else {
       							                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
									                    }
																		 
											     }

										
						},
						{
		                    text:getLabelFromMap('calendarBtnEd',helpMap,'Editar'),
		                    tooltip: getToolTipFromMap('calendarBtnEd',helpMap,'Edita d&iacute;as calendario'),
							handler: function(){
							      if (el_form.form.isValid()){
							    
										if (getSelectedRecord(grilla) != null) {
											editar(getSelectedRecord(grilla), Ext.getCmp('codPaisId').getValue(), Ext.getCmp('codMesId').getValue(),Ext.getCmp('anioId').getValue());
										}else {
											Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
										}
										
										}
										else {
										       Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
										        }
									}
						},
		                  { 
		                  text:getLabelFromMap('calendarBtnDel',helpMap,'Eliminar'),
		                  tooltip:getToolTipFromMap('calendarBtnDel',helpMap, 'Elimina d&iacute;as calendario'),
		                    handler:function(){		
		                      if (getSelectedKey(grilla, "dias") != "")
		                      {
		                        borrar(getSelectedKey(grilla, "dias"));
		                      }else {
		                              Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
		                            }
		                   	}
		                  },
						{
		                    text:getLabelFromMap('calendarBtnExp',helpMap,'Exportar'),
		                    tooltip: getToolTipFromMap('calendarBtnExp',helpMap,'Exporta el contenido del grid'),
							handler: function(){
										//buscado=Ext.getCmp('codPaisId').getValue();
										//alert(buscado);
										paisEleccion= cmbPais.lastSelectionText;
										mesEleccion= cmbMes.lastSelectionText;
										//alert(cosa);
				                        var url = _ACTION_EXPORTAR_CALENDARIO + '?codPais=' + Ext.getCmp('codPaisId').getValue()+ '&yearCabecera=' + Ext.getCmp('anioId').getValue()+'&codMes=' + Ext.getCmp('codMesId').getValue()+'&desPais='+paisEleccion+'&desMes='+mesEleccion;
				                        showExportDialog( url );
				                      //  exportar (url);
								}
						}
						/*
						,{
		                    text:getLabelFromMap('calendarBtnBack',helpMap,'Regresar'),
		                    tooltip: getToolTipFromMap('calendarBtnBack',helpMap,'Regresa a la pantalla anterior'),
							handler: function(){}
						}
					   */	
					],
				bbar: new Ext.PagingToolbar({
						pageSize: itemsPerPage,
						store: dsGrilla,
						displayInfo: true,
		                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
						emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
				    })
		});
	grilla.render();
 };			
				
	var anio = new Ext.form.NumberField({						
						anchor: '95%',
						fieldLabel: 'Año',
						valueField:'yearCabecera',
						tooltip:'Año Calendario',
						labelAlign:'right',
						id: 'anioId', 
						name: 'yearCabecera',
						maxLength: 4,
						anchor:'90%'
						})	
		
	var spinner = new Ext.ux.form.Spinner({
					id:'anioId',
	                fieldLabel: 'Año',
	                height:50,
	                name: 'yearCabecera',
	                value:yearCabecera,
	                maxLength: 4,
	                strategy: new Ext.ux.form.Spinner.NumberStrategy({minValue:'1980', maxValue:'2020'})	                
            		});	
            		
     spinner.on('spinup',function(){reloadGrid();cargar();});
     spinner.on('spindown',function(){reloadGrid();cargar();});           		

/********* Comienza el form ********************************/
	var el_form = new Ext.FormPanel ({
			id: 'el_form',
			renderTo: 'formDocumentos',
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('24',helpMap,'Mantenimiento de Calendario para d&iacute;as no Laborables')+'</span>',
            labelWidth : 70,
            frame : true,
            bodyStyle : 'padding:5px 5px 0',
            bodyStyle:'background: white',
            width : 500,
            height: 100,
            //autoHeight: true,
            labelAlign:'right',
            waitMsgTarget : true,
        layout: 'table',
		frame:true,
		layoutConfig:{
						columns:2
		},
		items:[{		
				layout:"form",
				colspan: 2,
				items:[
						cmbPais
						]
				
				},
				{
				layout:"form",
				colspan:1,
				items:[spinner
					/*{
						xtype: 'numberfield',
						anchor: '95%',
						fieldLabel: 'Año',
						valueField:'yearCabecera',
						tooltip:'Año Calendario',
						labelAlign:'right',
						id: 'anioId', 
						name: 'yearCabecera',
						maxLength: 4,
						anchor:'90%',					 
						
                        listeners: {
						        'change': function(){
						             //this.setValue(record.get('codPais'));
						         	 cargar();
		                             reloadGrid();
		                           
						           }
						      } 	 
						
		 
				    	}*/]
					
				 }
				 ,
				 {
				 layout:"form",
				 colspan:1,
				 //anchor:'95%',
				 items:[
				 		cmbMes
				 		]
				 	
				 }
			]


            //se definen los campos del formulario
	});
	
/********* Fin del form ************************************/
	el_form.render();
	createGrid();
 
    idPais.load({
    	params: {
    				cdTabla: 'CPAISISO'
                  		
    			},
    	callback:function(){
    		idPais.sort( "desc", "ASC" );
    		//alert("Pais Usuario logueado "+ cdPaisUsuarioLogueado);
    		cmbPais.setValue(cdPaisUsuarioLogueado);
	       	cargar();
		    reloadGrid();
    		
    	}	
      
    });
    idMes.load({
    	params: {
    				cdTabla: 'CMESISO'
    			},
		callback:function(){
			//idMes.sort("codMes","ASC");
			cmbMes.setValue(mesCabecera);
			}    			
    });
	
	Ext.getCmp('anioId').setValue(yearCabecera);


function borrar(key) {
						
		//if(_record )
		if(key != "")
		{
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
			{
		        if (btn == "yes")
		        {
         			var _params = {
         						codPais: Ext.getCmp('codPaisId').getValue(), 
         						yearCabecera: Ext.getCmp('anioId').getValue(), 
         						codMes: Ext.getCmp('codMesId').getValue(), 
         						dia: key
         			};
         			execConnection(_ACTION_BORRAR_CALENDARIO, _params, cbkConnection);
               }
			})
		}else{
				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
		}
};

function callbackGrabar (_success, _errorMessages) {
		if (_success) {
			Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400027', helpMap,'Guardando datos...'));
		} else {
			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _errorMessages);
		}
	}

function cbkConnection (_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid();})
	}
}   
	
function exportar (action) {
	var fmExport = new Ext.FormPanel({
		renderTo: 'formularioExport',
		labelWidth: 120,
		width: 0,
		height: 0,
		onSubmit: Ext.emptyFn,
		submit: function() {
			// en la funcion de submit de ExtJS se sobreescribe para hacer un submit sin AJAX 
			this.getEl().dom.setAttribute("action",action);
			this.getEl().dom.target = '_windowExport';
			this.getEl().dom.submit();
		},
		defaultType: 'textfield',
		baseCls: null,
		bodyStyle: {background: 'white', padding: '5px 10px 0'},
		items: [
			{
				xtype: 'hidden', id:'formato', value: 'PDF'
				}
			]
		});
	fmExport.render();
	fmExport.getForm().submit(action);
}

});			// fin onReady()



function reloadGrid(){
	var params = {
					codPais: Ext.getCmp('codPaisId').getValue(),  //Ext.getCmp('codPaisId').getValue(),
					yearCabecera: Ext.getCmp('el_form').form.findField('anioId').getValue(),
					codMes:  Ext.getCmp('el_form').form.findField('codMesId').getValue()  //Ext.getCmp('codMesId').getValue()
				};
	    reloadComponentStore(Ext.getCmp('grilla'), params, myCallback);
}

function myCallback(_rec, _opt, _success, _store) {
	if (!_success) {
		//alert('exito');

		Ext.Msg.alert(getLabelFromMap('400000', helpMap,getLabelFromMap('400000', helpMap,'Aviso')), _store.reader.jsonData.actionErrors[0]);
	    _store.removeAll()	
	}
}
			