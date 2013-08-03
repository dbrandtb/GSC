Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

//VALORES DE INGRESO DE LA BUSQUEDAssssss

  var dsTipoCancela = new Ext.data.Store({
           proxy: new Ext.data.HttpProxy({
               url: _ACTION_COMBO_TIPO_CANCELACION
           }),
           reader: new Ext.data.JsonReader({
           root: 'comboTipoActividad',
           id: 'id'
           },[
           {name: 'id', type: 'string',mapping:'codigo'},
           {name: 'texto', type: 'string',mapping:'descripcion'}
       ])
       });
       
          var comboTipoCancela = new Ext.form.ComboBox({tpl: '<tpl for="."><div ext:qtip="{id}.{texto}" class="x-combo-list-item">{texto}</div></tpl>',
                         store: dsTipoCancela, anchor:'100%', displayField:'texto', valueField: 'id', hiddenName: 'cdTipoCancela',
                         typeAhead: true, triggerAction: 'all', lazyRender:   true, emptyText:'Selecione Tipo...', selectOnFocus:true,
                         name: 'cdTipoCancela',id:'cdTipoCancelaId', forceSelection: true,labelSeparator:''
			              });
			              
			              
			              
var dsAseg = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtDsUniEco',helpMap,'Asegurado'),
        tooltip:getToolTipFromMap('txtDsUniEco',helpMap,'Asegurado'), 
        id: 'dsAsegId', 
        name: 'dsAseg',
        allowBlank: true,
        anchor: '100%'
});
 
var dsRamo = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtDsRamo',helpMap,'Producto'),
        tooltip:getToolTipFromMap('txtDsRamo',helpMap,'Producto'), 
        id: 'dsRamoId', 
        name: 'dsRamo',
        allowBlank: true,
        anchor: '100%'       
    });
  
var nmInciso = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtInciso',helpMap,'Inciso'),
        tooltip:getToolTipFromMap('txtInciso',helpMap,'Inciso'), 
        id: 'nmIncisoId', 
        name: 'nmInciso',
        allowBlank: true,
        anchor: '100%'
    });
    
    
    
var dsUniEco = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtDsUniEco',helpMap,'Aseguradora'),
        tooltip:getToolTipFromMap('txtDsUniEco',helpMap,'Aseguradora'), 
        id: 'dsUniEcoId', 
        name: 'dsUniEco',
        allowBlank: true,
        anchor: '100%'
        //width:150
    });
  
    

 
var nmPoliza = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtNmPoliza',helpMap,'Poliza'),
        tooltip:getToolTipFromMap('txtNmPoliza',helpMap,'Poliza'), 
        id: 'nmPolizaId', 
        name: 'nmPoliza',
        allowBlank: true,
        anchor: '100%'       
    });
  
    
  var fgBFP = new Ext.grid.CheckColumn({
      id:'fgBFPId',
      header: getLabelFromMap('chfgBFPId',helpMap,'BFP'),
      tooltip: getToolTipFromMap('chfgBFPId', helpMap,'BFP'),
      dataIndex: 'nmSituac',
      align: 'center',
      sortable: true,
      width: 80
    });
    
    
      var fgCancelar = new Ext.grid.CheckColumn({
      id:'fgCancelarId',
      header: getLabelFromMap('chfgCancelarId',helpMap,'Cancelar'),
      tooltip: getToolTipFromMap('chfgCancelarId', helpMap,'Cancelar'),
      dataIndex: 'swcancela',
      align: 'center',
      sortable: true,
      width: 80
    });
    
    
var incisosFormPolizas = new Ext.FormPanel({
		id: 'incisosFormPolizas',
        el:'formBusqueda',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('24',helpMap,'P&oacute;lizas a Cancelar')+'</span>',
        iconCls:'logo',
        store:storeGrillaPolizasCancelar,
        reader:jsonGrillaPolizasCancelar,
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,   
        url: _ACTION_BUSCAR_POLIZAS_A_CANCELAR,
        width: 750,
        height:220,
        items: [{
        		layout:'form',
				border: false,
 				items:[
                {
        		bodyStyle:'background: white',
		        labelWidth: 100,
                layout: 'form',
                title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br/>',
				frame:true,
		       	baseCls: '',
		       	buttonAlign: "center",
        		        items: [{
        		        	layout:'column',
		 				    border:false,
		 				    columnWidth: 1,
        		    		items:[{
						    	columnWidth:.4,
                				layout: 'form',
		                		border: false,
        		        		items:[       		        				
        		        				 dsAseg,
        		        				 dsRamo,
        		        				 nmInciso
                                       ]
								},
								{
								columnWidth:.4,
                				layout: 'form',
		                		border: false,
        		        		items:[       		        				
        		        				 dsUniEco,
        		        				 nmPoliza
                                       ]
                				},{
								columnWidth:.2,
                				layout: 'form'
                				}]
                			}],
                			buttons:[{
        							text:getLabelFromMap('ntfcnButtonBuscar',helpMap,'Buscar'),
        							tooltip: getToolTipFromMap('ntfcnButtonBuscar',helpMap,'Busca un Grupo de Polizas a Cancelar'),
        							handler: function() {
				               			if (incisosFormPolizas.form.isValid()) {
                                               if (grid=null) {
                                                reloadGrid();
                                               } else {
                                                reloadGrid();
                                               }
                						} else{
                							Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
										}
									}
        							},{
        							text:getLabelFromMap('ntfcnButtonCancelar',helpMap,'Cancelar'),
        							tooltip: getToolTipFromMap('ntfcnButtonCancelar',helpMap,'Cancelar busqueda de Polizas'),                              
        							handler: function() {
        								incisosFormPolizas.form.reset();
        							}
        						}]
        					}]
        				}]	            
        
});   


// Definicion de las columnas de la grilla
var cm = new Ext.grid.ColumnModel([
        {
        
        header: getLabelFromMap('cmDsAseg',helpMap,'Asegurado'),
        tooltip: getToolTipFromMap('cmDsAseg',helpMap,'Columna Asegurado'),
        dataIndex: 'asegurado',
        width: 100,
        sortable: true
        
        },{
        
        header: getLabelFromMap('cmDsUniEco',helpMap,'Aseguradora'),
        tooltip: getToolTipFromMap('cmDsUniEco',helpMap,'Columna Aseguradora'),
        dataIndex: 'dsUnieco',
        width: 100,
        sortable: true
        
        },{
        
        header: getLabelFromMap('cmDsProducto',helpMap,'Producto'),
        tooltip: getToolTipFromMap('cmDsProducto',helpMap,'Columna Producto'),
        dataIndex: 'dsRamo',
        width: 100,
        sortable: true
        
        },{
        	
        header: getLabelFromMap('cmNmPoliza',helpMap,'P&oacute;lizaa'),
        tooltip: getToolTipFromMap('cmNmPoliza',helpMap,'Columna P&oacute;liza'),
        dataIndex: 'nmPoliex',
        width: 100,
        sortable: true
        
        },{
        header: getLabelFromMap('nmsuplem',helpMap,'Inciso'),
        tooltip: getToolTipFromMap('nmsuplem',helpMap,'Columna Inciso'),
        dataIndex: 'nmsuplem',
        width: 100,
        sortable: true
        
        },{
        	
        header: getLabelFromMap('cmTipoCancela',helpMap,'Tipo de Cancelaci&oacute;n'),
        tooltip: getToolTipFromMap('cmTipoCancela',helpMap,'Columna Tipo de Cancelaci&oacute;n'),
        dataIndex: 'tipoCancel',
        width: 100,
        sortable: true,
        editor: comboTipoCancela,
        renderer: renderComboEditor(comboTipoCancela)
        
        },
        
       // fgBFP,
        fgCancelar,
        
        {
        
        header: getLabelFromMap('cmFechaCancelar',helpMap,'Fecha a Cancelar'),
        tooltip: getToolTipFromMap('cmFechaCancelar',helpMap,'Columna Fecha a Cancelar'),
        dataIndex: 'feCancel',
        width: 100,
        sortable: true
         
       },{
        dataIndex: 'cdUnieco',
        hidden :true
        
       },{
        dataIndex: 'cdRamo',
        hidden :true

        },{
         dataIndex: 'nmSituac',
         hidden :true
        },{
         dataIndex: 'cdagrupa',
         hidden :true

        },{
        dataIndex: 'nmrecibo',
        hidden :true
        },{
        dataIndex: 'cdcancel',
        hidden :true
        },{
        dataIndex: 'swcancela',
        hidden :true
        },{
        dataIndex: 'status',
        hidden :true
        
       }          
]);

    //Crea la grilla 
var grid = new Ext.grid.EditorGridPanel ({
   		store: storeGrillaPolizasCancelar, 
   		id:'gridId',
   		columns: [
   					{
   					 header: getLabelFromMap('cmDsAseg',helpMap,'Asegurado'),
        				tooltip: getToolTipFromMap('cmDsAseg',helpMap,'Columna Asegurado'),
       				 dataIndex: 'asegurado',
       				 width: 100,
       				 sortable: true
   					},{
        			 header: getLabelFromMap('cmDsUniEco',helpMap,'Aseguradora'),
			        tooltip: getToolTipFromMap('cmDsUniEco',helpMap,'Columna Aseguradora'),
			        dataIndex: 'dsUnieco',
			        width: 100,
			        sortable: true
   					},{
        			 header: getLabelFromMap('cmDsProducto',helpMap,'Producto'),
				        tooltip: getToolTipFromMap('cmDsProducto',helpMap,'Columna Producto'),
				        dataIndex: 'dsRamo',
				        width: 100,
				        sortable: true
   					},{
        			  header: getLabelFromMap('cmNmPoliza',helpMap,'P&oacute;liza'),
				        tooltip: getToolTipFromMap('cmNmPoliza',helpMap,'Columna P&oacute;liza'),
				        dataIndex: 'nmPoliex',
				        width: 100,
				        sortable: true
   					},
   					{
        			header: getLabelFromMap('nmsuplem',helpMap,'Inciso'),
			        tooltip: getToolTipFromMap('nmsuplem',helpMap,'Columna Inciso'),
			        dataIndex: 'nmSituac',
			        width: 100,
			        sortable: true
   					}, {
        			header: getLabelFromMap('cmTipoCancela',helpMap,'Tipo de Cancelaci&oacute;n'),
			        tooltip: getToolTipFromMap('cmTipoCancela',helpMap,'Columna Tipo de Cancelaci&oacute;n'),
			        dataIndex: 'cdcancel',//tipoCancel
			        width: 100,
			        sortable: true,
			        editor: comboTipoCancela,
			        renderer: renderComboEditor(comboTipoCancela)
   					}, 
   					//fgBFP, 
   					fgCancelar,
   					{
        			 header: getLabelFromMap('cmFechaCancelar',helpMap,'Fecha a Cancelar'),
				        tooltip: getToolTipFromMap('cmFechaCancelar',helpMap,'Columna Fecha a Cancelar'),
				        dataIndex: 'feCancel',
				        width: 100,
				        sortable: true
                       },{
                        dataIndex: 'cdUnieco',
                        hidden :true

                       },{
                        dataIndex: 'cdRamo',
                        hidden :true

                        },{
                        dataIndex: 'estado',
                        hidden :true
                        },{
                         dataIndex: 'cdagrupa',
                         hidden :true
                        },{
                         dataIndex: 'nmrecibo',
                         hidden :true

                        },{
                        dataIndex: 'nmsuplem',
                        hidden :true
                        },{
                        dataIndex: 'nmrecibo',
                        hidden :true
                        },{
                        dataIndex: 'tipoCancel',
                        hidden :true
                       },{
                        dataIndex: 'status',
                        hidden :true

                       }
                   ],
   	
   		frame: true,
   		width: 750,
   		height: 300,
   		collapsible: true,
   		loadMask: {msg: getLabelFromMap('400021', helpMap,'Espere...'), disabled: false},
   		animCollapse: true,
   		title: 'Listado',
   		iconCls: 'icon-grid',
   		renderTo: 'gridElementos',
   		plugins: [fgCancelar],//fgbfp
   		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
   		buttonAlign: 'center',
   		buttons:[
   				{       			
      			text:getLabelFromMap('chkListCtaBttAdd', helpMap,'Guardar'),
          		tooltip:getToolTipFromMap('chkListCtaBttAdd', helpMap,'Guardar'),
           		handler:function(){
           		                   Ext.getCmp('gridId').getSelectionModel().selectAll();
           		                   guardarDatosPolizas();
           		                  }
           		}, {
                  text:getLabelFromMap('gridNtfcnBottonExportar',helpMap,'Exportar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonExportar',helpMap,'Exportar datos a diversos formatos'),
                    handler:function(){
                    	//pv_asegurado_i, pv_dsuniage_i, pv_dsramo_i, pv_nmpoliza_i, pv_nmsituac_i
                    	var url = _ACTION_EXPORTAR_POLIZAS_A_CANCELAR + '?pv_asegurado_i=' + dsAseg.getValue() + '&pv_dsuniage_i='+ dsUniEco.getValue() + '&pv_dsramo_i=' + dsRamo.getValue() + '&pv_nmpoliza_i=' + nmPoliza.getValue() + '&pv_nmsituac_i=' + nmInciso.getValue();
                        showExportDialog( url );
                    } 
                  
                  },{       			
      			text:getLabelFromMap('chkListCtaBttAddArch', helpMap,'Cargar Archivo'),
          		tooltip:getToolTipFromMap('chkListCtaBttAddArch', helpMap,'Guardar')
           		//handler:function(){grid.getSelectionModel().selectAll();handlerEdit();}
           		}
           		],
           		bbar: new Ext.PagingToolbar({
					pageSize:20,
					store: storeGrillaPolizasCancelar,
					displayInfo: true,
					displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })
   		/*bbar: [{text: 'Guardar Tareas', 
   				handler: function () {
   											grid.getSelectionModel().selectAll();
   											handlerEdit();
   									}
   				},
   				{
   				text: 'Regresar', 
   				handler: function () {Ext.Msg.alert("Advertencia","Se regresara a la pagina anterior");}
   				}]*/
   	});


   	
   			//Funcion para manejo de ediciones
/*
		function handlerEdit (edtEvt) {
			if (incisosFormPolizas.findById('cdUnieco').getValue() == "") {
				Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'), function(){incisosFormPolizas.findById('cdUnieco').focus();});
				return;
			}
			if (tab_actual == -1) {
				//Esto es temporal hasta que encuentre una manera mas elegante de hacerlo
				Ext.Msg.alert('Aviso', 'Debe seleccionar una secci&oacute;n');
				return;
			}
            var _params = "";
			var recs = grid.store.getModifiedRecords();
			if (recs.length > 0) {
				for (var i=0; i<recs.length; i++) {
				 _params +=   "csoGrillaListAtr[" + i + "].cdUnieco=" + recs[i].get('cdUnieco') + "&" +
			      "csoGrillaListAtr[" + i + "].cdRamo=" + recs[i].get('cdRamo') + "&" +
			      "&csoGrillaListAtr[" + i + "].estado=" + recs[i].get('estado') + "&" +
			      "&csoGrillaListAtr[" + i + "].nmPoliza=" + recs[i].get('nmPoliza') + "&" +
			      "&csoGrillaListAtr[" + i + "].nmsuplem=" + recs[i].get('nmsuplem') + "&" +
			      "&csoGrillaListAtr[" + i + "].cdagrupa=" + recs[i].get('cdagrupa') + "&" +
			      "&csoGrillaListAtr[" + i + "].nmrecibo=" + recs[i].get('nmrecibo') + "&" +
			      "&csoGrillaListAtr[" + i + "].cdcancel=" + recs[i].get('cdcancel') + "&" +
			      "&csoGrillaListAtr[" + i + "].status=" + recs[i].get('status') + "&" +
			      "&csoGrillaListAtr[" + i + "].feCancel=" + recs[i].get('feCancel') + "&" +
			      "&csoGrillaListAtr[" + i + "].swcancela=" + recs[i].get('swcancela') + "&";
				}
			}

            execConnection(_ACTION_BUSCAR_POLIZAS_A_CANCELAR,_params,callBackGuardar);
			return;
		}

*/


function guardarDatosPolizas () {
  var _params = "";
  
  var recs =  Ext.getCmp('gridId').store.getModifiedRecords();
  Ext.getCmp('gridId').stopEditing();
  for (var i=0; i<recs.length; i++) {
   _params +=   "csoGrillaList[" + i + "].cdUnieco=" + recs[i].get('cdUnieco') + "&" +
      "csoGrillaList[" + i + "].cdRamo=" + recs[i].get('cdRamo') + "&" +
      "&csoGrillaList[" + i + "].estado=" + recs[i].get('estado') + "&" +
      "&csoGrillaList[" + i + "].nmPoliza=" + recs[i].get('nmPoliza') + "&" +
      "&csoGrillaList[" + i + "].nmsuplem=" + recs[i].get('nmsuplem') + "&" +
      "&csoGrillaList[" + i + "].cdagrupa=" + recs[i].get('cdagrupa') + "&" +
      "&csoGrillaList[" + i + "].nmrecibo=" + recs[i].get('nmrecibo') + "&" +
      "&csoGrillaList[" + i + "].cdcancel=" + recs[i].get('cdcancel') + "&" +
      "&csoGrillaList[" + i + "].status=" + recs[i].get('status') + "&" +
      "&csoGrillaList[" + i + "].feCancel=" + recs[i].get('feCancel') + "&" +
      "&csoGrillaList[" + i + "].swcancela=" + ((recs[i].get('swcancela') == true)?'S':'N')  + "&";
  }
  if (recs.length > 0) {
        execConnection(_ACTION_GUARDAR_POLIZAS_A_CANCELAR,_params,callBackGuardar);
   }
 }
    

    function callBackGuardar (_success, _message) {
        if (!_success) {
            Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
        }else {
            Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid()});
        }
    }
		
incisosFormPolizas.render();
dsTipoCancela.load();
//createGrid();




function reloadGrid(){
	
	var _params = {
       				pv_asegurado_i:Ext.getCmp('dsAsegId').getValue(),
       				pv_dsuniage_i:Ext.getCmp('dsUniEcoId').getValue(), 
       				pv_dsramo_i:Ext.getCmp('dsRamoId').getValue(), 
       				pv_nmpoliza_i:Ext.getCmp('nmPolizaId').getValue(), 
       				pv_nmsituac_i:Ext.getCmp('nmIncisoId').getValue()
       			  };
       			    
       			    Ext.getCmp('gridId').store.baseParams=_params,
       			  //  grid.store.baseParams=_params,
       			  Ext.getCmp('gridId').store.load({params:{start:0,limit:itemsPerPage},
                  callback : function(r,options,success) {
                  	  if (!success) {
                      	Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), Ext.getCmp('gridId').store.reader.jsonData.actionErrors[0]);
                        
                      }
                  }});
     // storeGrillaPolizasCancelar.load({params:_params});
	
}


function cbkReload(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}
}


});