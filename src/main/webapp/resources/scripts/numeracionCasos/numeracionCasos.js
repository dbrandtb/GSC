Ext.onReady(function(){  
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

/********* Comienza la grilla ******************************/
	var cm = new Ext.grid.ColumnModel ([
		        {      
			        header: "Modulo",
			        dataIndex: 'cdModulo',
			        hidden : true
				},
				{	header: "NumCaso",
			        dataIndex: 'cdNumCaso',
			        hidden : true
				},
				
				{	header: "id Numeracion",
			        dataIndex: 'indNumer',
			        hidden : true
				},
				{
			        header: getLabelFromMap('mumCasosCmMod',helpMap,'M&oacute;dulo'),
			        tooltip: getToolTipFromMap('mumCasosCmMod',helpMap,'M&oacute;dulo de numeraci&oacute;n de casos'),
			        hasHelpIcon:getHelpIconFromMap('mumCasosCmMod',helpMap),
		            Ayuda: getHelpTextFromMap('mumCasosCmMod',helpMap,''),  
					dataIndex: 'desModulo',
					width: 190,
					//align:'center',
					sortable: true
				},
				{
			        header: getLabelFromMap('mumCasosCmNd',helpMap,'N&uacute;mero Desde'),
			        tooltip: getToolTipFromMap('mumCasosCmNd',helpMap,'N&uacute;mero Desde de numeraci&oacute;n de casos'),
			         hasHelpIcon:getHelpIconFromMap('mumCasosCmNd',helpMap),
		           Ayuda: getHelpTextFromMap('mumCasosCmNd',helpMap,''),    
					dataIndex: 'numDesde',
					width: 97,
					//align:'center',
					sortable: true
				},
				{
			        header: getLabelFromMap('mumCasosCmNh',helpMap,'N&uacute;mero Hasta'),
			        tooltip: getToolTipFromMap('mumCasosCmNh',helpMap,'N&uacute;mero Hasta de numeraci&oacute;n de casos'),
			         hasHelpIcon:getHelpIconFromMap('mumCasosCmNh',helpMap),
	             	Ayuda: getHelpTextFromMap('mumCasosCmNh',helpMap,''),    
					dataIndex: 'numHasta',
					width: 97,
					//align:'center',
					sortable: true
				},
				{
			        header: getLabelFromMap('mumCasosCmNa',helpMap,'N&uacute;mero Actual'),
			        tooltip: getToolTipFromMap('mumCasosCmNa',helpMap,'N&uacute;mero Actual de numeraci&oacute;n de casos'),
			          hasHelpIcon:getHelpIconFromMap('mumCasosCmNa',helpMap),
	             	Ayuda: getHelpTextFromMap('mumCasosCmNa',helpMap,''),    
					dataIndex: 'numActual',
					width: 97,
					//align:'center',
					sortable: true
				}
			]);

	var descModulo = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('descModuloId',helpMap,'M&oacute;dulo'),
        tooltip:getToolTipFromMap('descModuloId',helpMap,'Asunto de la Notificaci&oacute;n'), 
         hasHelpIcon:getHelpIconFromMap('descModuloId',helpMap),
		Ayuda: getHelpTextFromMap('descModuloId',helpMap,''),        
        id: 'descModuloId', 
        name: 'descModulo',
        allowBlank: true,
        anchor: '100%'
    });


/*	var cdModulo = new Ext.form.Hidden( {
        disabled:false,
        name:'cdMod',
        id:'cdMod'
    });*/

	function cargar(){

			dsGrilla = new Ext.data.Store ({
			
			proxy: new Ext.data.HttpProxy ({url: _ACTION_BUSCAR_NUMERACION_CASOS}),
			reader: new Ext.data.JsonReader({
					root: 'MEstructuraNumCasoList',
					totalProperty: 'totalCount',
					id:'cdModulo',
					successProperty: '@success'
					},
					[
						{name: 'cdNumCaso', type: 'string', mapping: 'cdNumCaso'},
						{name: 'cdModulo', type: 'string', mapping: 'cdModulo'},
						{name: 'indNumer', type: 'string', mapping: 'indNumer'},
						{name: 'desModulo', type: 'string', mapping: 'desModulo'},
						{name: 'numDesde', type: 'string', mapping: 'nmDesde'},
						{name: 'numHasta', type: 'string', mapping: 'nmHasta'},
						{name: 'numActual', type: 'string', mapping: 'nmCaso'}
					]
					)
		});
		return dsGrilla
	}


var grilla;
			
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
				loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
				width: 500,
				height: 300,
				title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
				buttonAlign:'center',
				sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
				buttons: [
						{
		                    text:getLabelFromMap('mumCasosBtnAdd',helpMap,'Agregar'),
		                    tooltip: getToolTipFromMap('mumCasosBtnAdd',helpMap,'Agrega numeraci&oacute;n de casos'),
							handler: function(){agregar()}
						},
						{
		                    text:getLabelFromMap('mumCasosBtnEd',helpMap,'Editar'),
		                    tooltip: getToolTipFromMap('mumCasosBtnEd',helpMap,'Edita numeraci&oacute;n de casos'),
							handler: function(){
										if (getSelectedRecord(grilla) != null) {
											editar(getSelectedRecord(grilla))
										}else {
											Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'))
										}
									}
						},
		                  { 
		                  text:getLabelFromMap('mumCasosBtnDel',helpMap,'Eliminar'),
		                  tooltip:getToolTipFromMap('mumCasosBtnDel',helpMap, 'Elimina numeraci&oacute;n de casos'),
		                    handler:function(){		
		                      if (getSelectedKey(grilla, "cdNumCaso") != "")
		                      {
		                        borrar(getSelectedKey(grilla, "cdNumCaso"))
		                      }else {
		                              Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'))
		                            }
		                   	}
		                  },
						{
		                    text:getLabelFromMap('mumCasosBtnExp',helpMap,'Exportar'),
		                    tooltip: getToolTipFromMap('mumCasosBtnExp',helpMap,'Exporta en numeraci&oacute;n de casos'),
							handler: function(){
										//alert('valor:'+Ext.getCmp('descModuloId').getValue());
				                        var url = _ACTION_EXPORTAR_NUMERACION_CASOS + '?desmodulo=' + Ext.getCmp('descModuloId').getValue();
				                        showExportDialog( url )
								}
						}
						/*
						,{
		                    text:getLabelFromMap('mumCasosBtnBack',helpMap,'Regresar'),
		                    tooltip: getToolTipFromMap('mumCasosBtnBack',helpMap,'Regresa a la pantalla anterior'),
							handler: function(){}
						}
						*/
					],
				bbar: new Ext.PagingToolbar({
						pageSize:itemsPerPage,
						store: dsGrilla,
						displayInfo: true,
		                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
						emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
				    })
		});
	grilla.render()
 };		
		

/********* Comienza el form ********************************/
	var el_form = new Ext.FormPanel ({
			id: 'el_form',
			renderTo: 'formDocumentos',
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('24',helpMap,'Numeraci&oacute;n de Solicitudes CAT-BO')+'</span>',
            labelWidth : 70,
            frame : true,
            bodyStyle : 'padding:5px 5px 0',
            bodyStyle:'background: white',
            width : 500,
            autoHeight: true,
            labelAlign:'right',
            waitMsgTarget : true,
            layout: 'form',
				 items:[{
					layout: 'form',
					border: false,
					items:[{
						labelWidth: 100,
						layout: 'form',
						frame:true,
						baseCls: '',
						buttonAlign: "center",
							
						items:[{
							layout:'column',
							border: false,
							labelAlign:'right',
							html:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
							columnWidth: 1,
							
							items:[{	
						    	columnWidth:.6,
					           	layout: 'form',
					            border: false,
					            items: [
					            		descModulo
				            			]
				            		},{
										columnWidth:.4,
				              			layout: 'form'
				                				
				            	}]
				            		//buttonAlign: 'center',
				            }],
            		buttons: [
            					{
				                 text:getLabelFromMap('mumCasosBtnSeek',helpMap,'Buscar'),
				                 tooltip: getToolTipFromMap('mumCasosBtnSeek',helpMap,'Busca en Numeraci&oacute;n de casos'),
            					 handler: function () {
   														if (el_form.form.isValid()) {
   															if (grilla != null) {
   																dsGrilla.removeAll;
   																reloadGrid()
   															}else {   																
   																createGrid();
   																dsGrilla.removeAll
   															}
   														}
            										}
            					},
            					{
				                 text:getLabelFromMap('mumCasosBtnCanc',helpMap,'Cancelar'),
				                 tooltip: getToolTipFromMap('mumCasosBtnCanc',helpMap,'Cancela operaci&oacute;n en Numeraci&oacute;n de casos'),
     	       					 handler: function() {
     	       					 						el_form.getForm().reset()
     	       					 					 }
     	       					}
            				]
            	}]
       }]     				

            //se definen los campos del formulario
	});
	/********* Fin del form ************************************/
	
	el_form.render();
	createGrid();
	//grilla.render();
		
function borrar(key) {
		if(key != "")
		{
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
			{
		        if (btn == "yes")
		        {
         			var _params = {
         						cdNumeCaso: key
         			};
         			execConnection(_ACTION_BORRAR_NUMERACION_CASOS, _params, cbkConnection)
               }
			})
		}else{
				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'))
		}
};

function callbackGrabar (_success, _errorMessages) {
		if (_success) {
			Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400027', helpMap,'Guardando datos...'))
		} else {
			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _errorMessages)
		}
	}

function cbkConnection (_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message)
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid()})
	}
}   
				
});			// fin onReady()



function reloadGrid(){
	var params = {
					//cdModulo: Ext.getCmp('el_form').form.findField('cdModulo').getValue(),
					desmodulo:  Ext.getCmp('descModuloId').getValue()
				}
	//alert(desModulo),
	reloadComponentStore(Ext.getCmp('grilla'), params, myCallback)
};

function myCallback(_rec, _opt, _success, _store) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,getLabelFromMap('400010', helpMap,'Error')), _store.reader.jsonData.actionErrors[0]);
		_store.removeAll()
	}
}
			