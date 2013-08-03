Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

//VALORES DE INGRESO DE LA BUSQUEDAssssss
var dsUniEco = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsUniEcoId',helpMap,'Aseguradora'),
        tooltip:getToolTipFromMap('dsUniEcoId',helpMap,'Aseguradora'), 
        hasHelpIcon:getHelpIconFromMap('dsUniEcoId',helpMap),								 
        Ayuda: getHelpTextFromMap('dsUniEcoId',helpMap),
        id: 'dsUniEcoId', 
        name: 'dsUniEco',
        allowBlank: true,
        anchor: '100%'
    });
 
var dsRamo = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsRamoId',helpMap,'Producto'),
        tooltip:getToolTipFromMap('dsRamoId',helpMap,'Producto'), 
        hasHelpIcon:getHelpIconFromMap('dsRamoId',helpMap),								 
        Ayuda: getHelpTextFromMap('dsRamoId',helpMap),
        id: 'dsRamoId', 
        name: 'dsRamo',
        allowBlank: true,
        anchor: '100%'
    });
  
var dsLayout = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsLayoutId',helpMap,'Carga'),
        tooltip:getToolTipFromMap('dsLayoutId',helpMap,'Carga'), 
        hasHelpIcon:getHelpIconFromMap('dsLayoutId',helpMap),
        Ayuda: getHelpTextFromMap('dsLayoutId',helpMap),
        id: 'dsLayoutId', 
        name: 'dsLayout',
        allowBlank: true,
        anchor: '100%'
    });
    
    
    var dtVigenciaInicio = new Ext.form.DateField({       
        id: 'dtVigenciaInicioId',
        name: 'dtVigenciaInicio',
        fieldLabel: getLabelFromMap('dtVigenciaInicio',helpMap,'Vigencia Inicio'),
	    tooltip: getToolTipFromMap('dtVigenciaInicio',helpMap,'Vigencia Inicio'),  
        hasHelpIcon:getHelpIconFromMap('dtVigenciaInicio',helpMap),								 
        Ayuda: getHelpTextFromMap('dtVigenciaInicio',helpMap),
        format:'d/m/Y',
        //width:'120',
        anchor:'100%'
    });

     var dtVigenciaFin = new Ext.form.DateField({
        id: 'dtVigenciaFinId',
        name: 'dtVigenciaFin',
        fieldLabel: getLabelFromMap('dtVigenciaFin',helpMap,'Fin'),
	    tooltip: getToolTipFromMap('dtVigenciaFin',helpMap,'Vigencia Fin'),     
        hasHelpIcon:getHelpIconFromMap('dtVigenciaFin',helpMap),								 
        Ayuda: getHelpTextFromMap('dtVigenciaFin',helpMap),        
        format:'d/m/Y',
        //width:'95',
        anchor:'100%'
    });

	var eleccionColumn = new Ext.grid.CheckColumn({
		id: 'eleccionColumnId',
		header: getLabelFromMap('eleccionColumnId',helpMap,'Aprobar'),
		tooltip: getToolTipFromMap('eleccionColumnId', helpMap,'Seleccionar Elementos'),
		dataIndex: 'seleccionar',
		value: false,
		width: 55
	});

    
function subirArchivoFTP(){
	
	var panelUpload = new Ext.ux.UploadPanel({
		anchor:'99%',
		autoHeight:true,
		fieldLabel:'Upload Panel',
		hidden:true,
		//url:
		addText:'Agregar',
		clickRemoveText:'Click para eliminar de la lista',
		clickStopText:'Click para detener',
		emptyText:'Agrega un archivo para cargar...',
		errorText:'Error',
		fileDoneText:'Archivo <b>{0}</b> ha sido cargado exitosamente',
		fileFailedText:'No se pudo cargar el archivo <b>{0}</b>',
		fileQueuedText:'Esperando a cargar el archivo <b>{0}</b>',
		fileStoppedText:'Archivo <b>{0}</b> detenido por el usuario',
		fileUploadingText:'Cargando archivo <b>{0}</b>',
		removeAllText:'Eliminar todo',
		removeText:'Eliminar',
		stopAllText:'Detener todo',
		uploadText:'Cargar'
	});

	var windowSubirArchivo = new Ext.Window({
    	plain: true,
    	width: 300,
        autoHeight: true,
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('24',helpMap,'Cargar Archivo')+'</span>',
        layout: 'fit',
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: [
        	{
            	xtype: 'combo',
                tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                store: storeComboCotizacionesMasivasTipoRamo,
                displayField:'descripcion',
                valueField:'codigo',
                hiddenName: 'pv_cdramo',
                typeAhead: true,
                allowBlank:false,
                mode: 'local',
                triggerAction: 'all',
                fieldLabel: getLabelFromMap('cdRamoId',helpMap,'Producto'),
                tooltip: getToolTipFromMap('cdRamoId',helpMap),
                width: 200,
                emptyText:'Seleccione Producto ...',
                selectOnFocus:true,
                forceSelection:true,
                id:'cdRamoId',
                onSelect : function(record, index, skipCollapse){
         			if(this.fireEvent('beforeselect', this, record, index) !== false){
           				this.setValue(record.data[this.valueField || this.displayField]);
           				if( !skipCollapse ) {
            				this.collapse();
           				}
           				this.lastSelectedIndex = index + 1;
           				this.fireEvent('select', this, record, index);
         			}
         			
         			//Asignar la url al UploadPanel:
        			var cdRamo = record.get('codigo');
        			//alert(cdRamo);
        			panelUpload.setUrl( _ACTION_SUBIR_ARCHIVO + '?cmd=upload&pv_cdramo=' + cdRamo );
        			panelUpload.show();
       			}
            },
            panelUpload
		],
        buttons :[{
        	text	: getLabelFromMap('ntfcnButtonCancelar',helpMap,'Cerrar'),
        	tooltip	: getToolTipFromMap('ntfcnButtonCancelar',helpMap,'Cerrar Ventana'),
           	handler	: function(){
				windowSubirArchivo.close();
			}
		}]
	});
	
    windowSubirArchivo.show();
    storeComboCotizacionesMasivasTipoRamo.load();
}
    
var incisosFormCotizaciones = new Ext.FormPanel({
		id: 'incisosFormCotizaciones',
        el:'formBusqueda',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('incisosFormCotizaciones',helpMap,'Resultados Cotizaciones Masivas')+'</span>',
        iconCls:'logo',
        store:storeGrillaCotizacionesMasivas,
        reader:jsonGrillaCotizacionesMasivas,
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,   
        url: _ACTION_BUSCAR_COTIZACIONES,
        width: 700,
        autoHeight:true,
		items: [{
        		layout:'form',
				border: false,
				items:[{
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
        		    		items:[{
								columnWidth:.47,//.45
                				layout: 'form',
		                		border: false,
		                		labelWidth: 100,
        		        		items:[
        		        			dsUniEco,
        		        			dsRamo        		        				         		        				 
								]
							},{
								columnWidth:.47,//.5
                				layout: 'form',
		                		border: false,
		                		labelWidth: 90,
        		        		items:[
        		        			dsLayout,
        		        			{
        		        				border:false,
        		        				layout:'column',
        		        				items:[{
	        		        				columnWidth:.58,
        		        					layout:'form',
        		        					border:false,
        		        					labelWidth: 90,
        		        					items:[dtVigenciaInicio]        		        			
        		        				},{
	        		        				columnWidth:.42,
        		        					layout: 'form',
        		        					border:false,
        		        					labelWidth : 30,
        		        					items: [dtVigenciaFin]
        		        				}] //fin interno items dtVigenciaInicio & dtVigenciaFin
        		        		}] // fin externo items dtVigenciaInicio & dtVigenciaFin 
                			}] // fin todos los elementos del form
                	}], // fin items
                	buttons:[{
						text:getLabelFromMap('ntfcnButtonBuscar',helpMap,'Buscar'),
						tooltip: getToolTipFromMap('ntfcnButtonBuscar',helpMap,'Busca cotizaciones según datos ingresados'),
						handler: function() {
							if (incisosFormCotizaciones.form.isValid()) {
								if (grid2!=null) {
									reloadGrid();
								} else {
									createGrid();
								}
							} else{
								Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
							}
						}
					},{
						text:getLabelFromMap('ntfcnButtonCancelar',helpMap,'Cancelar'),
						tooltip: getToolTipFromMap('ntfcnButtonCancelar',helpMap,'Cancelar busqueda de Polizas'),                              
						handler: function() {
							incisosFormCotizaciones.form.reset();
						}
					}]
				}]
		}]
});   


// Definicion de las columnas de la grilla
var cm = new Ext.grid.ColumnModel([
        {
        header: getLabelFromMap('cmDsUniEco',helpMap,'Aseguradora'),
        tooltip: getToolTipFromMap('cmDsUniEco',helpMap,'Columna Aseguradora'),
        dataIndex: 'dsAsegura',
        width: 100,
        sortable: true
        
        },{
        
        header: getLabelFromMap('cmDsProducto',helpMap,'Producto'),
        tooltip: getToolTipFromMap('cmDsProducto',helpMap,'Columna Producto'),
        dataIndex: 'dsRamo',
        width: 200,
        sortable: true
        
        },{
        header: getLabelFromMap('cmCarga',helpMap,'Carga'),
        tooltip: getToolTipFromMap('cmCarga',helpMap,'Columna Carga'),
        dataIndex: 'carga',
        width: 50,
        sortable: true
        
        },{
        	
        header: getLabelFromMap('cmCotizacion',helpMap,'Cotizaci&oacute;n'),
        tooltip: getToolTipFromMap('cmCotizacion',helpMap,'Columna Cotizaci&oacute;n'),
        dataIndex: 'nmPoliza',
        width: 70,
        sortable: true
        
        },{       	

        header: getLabelFromMap('cdDtInicio',helpMap,'Inicio'),
        tooltip: getToolTipFromMap('cdDtInicio',helpMap,'Columna Inicio'),
        dataIndex: 'feInivig',
        width: 90,
        sortable: true
        
        },{
        	
        header: getLabelFromMap('cmDtFin',helpMap,'Fin'),
        tooltip: getToolTipFromMap('cmDtFin',helpMap,'Columna Fin'),
        dataIndex: 'feFinvig',
        width: 90,
        sortable: true
        
        },{
        
        header: getLabelFromMap('cmPrimaTotal',helpMap,'Prima Total'),
        tooltip: getToolTipFromMap('cmPrimaTotal',helpMap,'Columna Prima Total'),
        dataIndex: 'prima',
        width: 80,
        sortable: true,
        renderer: Ext.util.Format.usMoney
        
        },{

		dataIndex: 'estado',
		hidden :true

		},{

		dataIndex: 'cdAsegura',
		hidden :true

		},{

		dataIndex: 'nmsituac',
		hidden :true

		},{

		dataIndex: 'nmsuplem',
		hidden :true

		},{

		dataIndex: 'cdPerson',
		hidden :true

		},{

		dataIndex: 'cdplan',
		hidden :true

		},{

		dataIndex: 'cdRamo',
		hidden :true

		},

		eleccionColumn
]);

var grid2;

function createGrid(){
       grid2= new Ext.grid.EditorGridPanel({
       		id: 'grid2',
            el:'gridElementos',
            store:storeGrillaCotizacionesMasivas,
            title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
            reader:jsonGrillaCotizacionesMasivas,
            border:true,
            cm: cm,
            clicksToEdit:1,
	        successProperty: 'success',
            buttonAlign: "center",
            plugins: [eleccionColumn],
            buttons:[
					{
						text: getLabelFromMap('gridNtfcnBottonDetalle',helpMap,'Detalle'),
						tooltip: getToolTipFromMap('gridNtfcnBottonDetalle',helpMap,'Detalle'),
						handler: function() {
							if(Ext.getCmp('grid2').getSelectionModel().hasSelection()) {
								var record = getSelectedRecord(grid2);
								var urlDetalleCotizacion = 
									"?cdunieco=" + record.get('cdunieco') +
									"&cdramo=" + record.get('cdRamo') +
									"&estado=" + record.get('estado') +
									"&nmpoliza=" + record.get('nmPoliza') +
									"&nmsituac=" + record.get('nmsituac') +
									"&nmsuplem=" + record.get('0') +
									"&dsunieco=" + record.get('dsAsegura') +
									"&cdcia=" + record.get('cdcia') +
									"&cdtipsit=" + record.get('cdtipsit');
								window.location.href = _CONTEXT + '/flujocotizacion/detalleConsultaCotizacion.action' + urlDetalleCotizacion;
							} else {
								Ext.Msg.alert('Aviso', 'Debe seleccionar un registro para realizar esta operaci&oacute;n');
							}
						}

					},{

						text: getLabelFromMap('gridNtfcnBottonBorrar',helpMap,'Eliminar'),
						tooltip: getToolTipFromMap('gridNtfcnBottonBorrar',helpMap,'Eliminar'),
						handler: function() {
							if (getSelectedKey(grid2, "dsAsegura") != "") {
								borrar(getSelectedRecord(grid2));
							} else {
								Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
							}
						}
					},{

						text: getLabelFromMap('gridNtfcnBottonNuevaCotizacion',helpMap,'Cargar Archivo'),
						tooltip: getToolTipFromMap('gridNtfcnBottonNuevaCotizacion',helpMap,'Cargar Archivo'),
						handler: function() {
							subirArchivoFTP();
						}

					},{

						text: getLabelFromMap('gridNtfcnBottonAprobar',helpMap,'Aprobar'),
						tooltip: getToolTipFromMap('gridNtfcnBottonAprobar',helpMap,'Aprobar'),
						handler: function() {
							if (getSelectedKey(grid2, "dsAsegura") != "") {
								cotizacionMasiva(storeGrillaCotizacionesMasivas);
							} else {
								Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
							}
						}

                  },{

						text:getLabelFromMap('gridNtfcnBottonExportar',helpMap,'Exportar'),
						tooltip: getToolTipFromMap('gridNtfcnBottonExportar',helpMap,'Exportar datos a diversos formatos'),
						handler:function(){
	                        //var url = _ACTION_BUSCAR_ENCUESTAS_EXPORT + '?pv_dsunieco_i=' + dsUniEco.getValue() + '&pv_dsramo_i='+ dsRamo.getValue() + '&pv_dsencuesta_i=' + dsEncuesta.getValue() + '&pv_dscampana_i=' + dsCampan.getValue() + '&pv_dsmodulo_i=' + dsModulo.getValue() + '&pv_dsproceso_i=' + dsProceso.getValue() + '&pv_nmpoliza_i=' + nmPoliza.getValue();
    	                    //showExportDialog( url );
						}

                  }],
            width:700,
            frame:true,
            height:320,
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			bbar: new Ext.PagingToolbar({
					pageSize:20,
					store:storeGrillaCotizacionesMasivas,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} de {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })          
        });
   grid2.render()
}

  
incisosFormCotizaciones.render();
createGrid();

function borrar(record) {
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
			{		        if (btn == "yes")
		        {
         			var _params = {
         						pv_cdusuari:record.get('cdElemento'),
         						pv_cdunieco:record.get('cdAsegura'),
         						pv_cdramo:record.get('cdRamo'),
         						pv_estado:record.get('nmPoliza'),
         						pv_nmpoliza:record.get('nmPoliza'),
         						pv_nmsituac:record.get('cdEncuesta'),
         						pv_nmsuplem:record.get('cdEncuesta'),
         						pv_cdelement:record.get('cdElemento')
         						
         			};
         			execConnection(_ACTION_BORRAR_COTIZACIONES, _params, cbkConnection);
               }
			})
};

function cbkConnection (_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid()});
	}
}

/*

function cotizacionMasiva(record) {
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('', helpMap,'Realizar la aprobacion de Polizas'),function(btn)
			{
		        if (btn == "yes")
		        {  
		        	//if()
         			var _params = {
         						pv_cdusuari:record.get(''),
         						pv_cdunieco:record.get('cdAsegura'),
         						pv_cdramo:record.get('cdRamo'),
         						pv_estado:record.get(''),//lo trae el pl pero no el servicio java
         						pv_nmpoliza:record.get('nmPoliza'),
         						pv_nmsituac:record.get('nmsituac'),
         						pv_nmsuplem:record.get(''),
         						pv_cdelement:record.get('cdElemento'),
         						pv_cdcia:record.get(''),
         						pv_cdplan:record.get('cdplan'),
         						pv_cdperpag:record.get(''),
         						pv_cdperson:record.get('cdPerson'),
         						pv_fecha:record.get('feFinvig')
         						
         						// // pv_cdcia, pv_cdplan, pv_cdperpag, pv_cdperson, pv_fecha
         			};
         			execConnection(_ACTION_APROBAR_COTIZACIONES, _params, cbkConnection);
               }
			})
};

function cbkConnection (_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid()});
	}
}*/

function cotizacionMasiva (elStore) {
  var _seleccionados=0; 
  var _params = "";
  for (var i=0; i<elStore.getCount(); i++) {
  	if ((elStore.getAt(i).data.seleccionar)== true){
	   _params +=   "csoGrillaList[" + i + "].cdUnieco=" + elStore.getAt(i).data.noTraelBuscar + "&" + //cdusuari
	      "&csoGrillaList[" + i + "].cdRamo=" + elStore.getAt(i).data.cdAsegura + "&" +
	      "&csoGrillaList[" + i + "].estado=" + elStore.getAt(i).data.cdRamo + "&" +
	      "&csoGrillaList[" + i + "].nmPoliza=" + elStore.getAt(i).data.noTraelBuscar + "&" +//estado
	      "&csoGrillaList[" + i + "].nmsuplem=" + elStore.getAt(i).data.nmPoliza + "&" +
	      "&csoGrillaList[" + i + "].cdagrupa=" + elStore.getAt(i).data.nmsituac + "&" +
	      "&csoGrillaList[" + i + "].nmrecibo=" + elStore.getAt(i).data.noTraelBuscar + "&" + //nmsuplem
	      "&csoGrillaList[" + i + "].cdcancel=" + elStore.getAt(i).data.cdElemento + "&" +
	      "&csoGrillaList[" + i + "].status=" + elStore.getAt(i).data.noTraelBuscar + "&" +//cdcia
	      "&csoGrillaList[" + i + "].feCancel=" + elStore.getAt(i).data.cdplan + "&" +
	      "&csoGrillaList[" + i + "].feCancel=" + elStore.getAt(i).data.noTraelBuscar + "&" +//cdperpag
	      "&csoGrillaList[" + i + "].feCancel=" + elStore.getAt(i).data.cdPerson + "&" +
	      "&csoGrillaList[" + i + "].feCancel=" + elStore.getAt(i).data.feFinvig + "&" ;
	      	++_seleccionados;
  	}
  }

  if (_seleccionados > 0) {
  	execConnection(_ACTION_APROBAR_COTIZACIONES,_params,callBackGuardar);
  	_seleccionados=0;
  }
  else
  {
  	Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));	
  }
 }
    

    function callBackGuardar (_success, _message) {
        if (!_success) {
            Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
        }else {
            Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid()});
        }
    }




});


function reloadGrid(){
	var _params = {
       				pv_cdelement:Ext.getCmp('incisosFormCotizaciones').form.findField('dsUniEco').getValue(),
       				pv_asegura:Ext.getCmp('incisosFormCotizaciones').form.findField('dsUniEco').getValue(), 
       				pv_cdramo_i:Ext.getCmp('incisosFormCotizaciones').form.findField('dsRamo').getValue(), 
       				pv_cdlayout:Ext.getCmp('incisosFormCotizaciones').form.findField('dsLayout').getValue(), 
       				pv_fedesde_i:Ext.getCmp('incisosFormCotizaciones').form.findField('dtVigenciaInicio').getValue(), 
       				pv_fehasta_i:Ext.getCmp('incisosFormCotizaciones').form.findField('dtVigenciaFin').getValue()
       			  };
	reloadComponentStore(Ext.getCmp('grid2'), _params, cbkReload);
}

function cbkReload(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}
}
