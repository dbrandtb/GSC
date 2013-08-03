Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";		
	
	var cm = new Ext.grid.ColumnModel([
        {
        header: getLabelFromMap('cmMovimientoId',helpMap,'Movimiento'),
        tooltip: getToolTipFromMap('cmMovimientoId',helpMap,'Movimiento'),
        dataIndex: 'nmovimiento',
        width: 105,
        sortable: true
        },
       /*{
        header: getLabelFromMap('cmEstatusId',helpMap,'Estatus'),
        tooltip: getToolTipFromMap('cmEstatusId',helpMap,'Estatus'),
        dataIndex: 'cdStatus',
        width: 55,
        sortable: true
        },*/
        {       
        header: getLabelFromMap('cmDesEstatusId',helpMap,'Estado'),
        tooltip: getToolTipFromMap('cmDesEstatusId',helpMap,'Descripcion de Estado'),
        dataIndex: 'desStatus',
        width: 120,
        sortable: true
        },
        {
        header: getLabelFromMap('cmObservacionId',helpMap,'Descripci&oacute;n'),
        tooltip: getToolTipFromMap('cmObservacionId',helpMap,'Descripci&oacute;n'),
        dataIndex: 'dsObservacion',
        width: 206,
        sortable: true
        },
		{        
        header: getLabelFromMap('cmFeIngresoId',helpMap,'Fecha de Ingreso'),
        tooltip: getToolTipFromMap('cmFeIngresoId',helpMap,'Fecha de Ingreso'),
        dataIndex: 'feRegistro',
        width: 120,
        sortable: true,
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
        }
	]);
	
	var codigoFormatoOrden = new Ext.form.Hidden({id:'codigoFormatoOrdenId',name:'cdFormatoOrden'});
	
	var grid = new Ext.grid.GridPanel({
       		id: 'gridId',
       		//el:'gridListado',
            store: storeGridMovimientos,
            border:true,
            cm: cm,
	        successProperty: 'success', 
	        title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',           
            width:595,
            buttonAlign:'center',
            frame:true,
            height:320,
            buttons:[
                  {
                  text:getLabelFromMap('gridBtn1',helpMap,'Agregar'),
                  tooltip: getToolTipFromMap('gridBtn1',helpMap,'Agrega un movimiento'),
                  handler:function(){
                  
                  validaStatusCaso();
                  }
                                    
                  
                  },                  
                  {
                  text:getLabelFromMap('gridBtn2',helpMap,'Consultar'),
                  tooltip: getToolTipFromMap('gridBtn2',helpMap,'Edita un movimiento seleccionado del grid'),
                  handler:function(){
						if (getSelectedRecord(grid)) {
                        		agregarEditar(getSelectedRecord(grid),Ext.getCmp('codigoFormatoOrdenId').getValue(),Ext.getCmp('txtNumCasoId').getValue(),getSelectedRecord(grid).get("nmovimiento"),Ext.getCmp("txtNumOrdenId").getValue(),Ext.getCmp('txtTareaId').getValue(), Ext.getCmp('txtDesTareaId').getValue());
               			} else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                        }
                  }
                  },
                  /*{
                  text:getLabelFromMap('gridBtn3',helpMap,'Borrar'),
                  tooltip: getToolTipFromMap('gridBtn3',helpMap,'Borra un movimiento seleccionado del grid'),
                  handler:function(){
						if (getSelectedRecord(grid)) {
                        		borrarMovimiento(getSelectedRecord(grid));
               			} else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                        }}
                  },*/
                  {
                    text:getLabelFromMap('gridBtn4',helpMap,'Exportar'),
                    tooltip: getToolTipFromMap('gridBtn4',helpMap,'Exporta el listado en un formato determinado'),
					handler: function(){
		                        var url = _ACTION_EXPORTAR_LISTADO_MOVIMIENTOS_CASO+'?pv_nmcaso_i='+NMCASO;
		                        showExportDialog( url );
						}
				  },
                  {
                  text:getLabelFromMap('gridBtn5',helpMap,'Regresar'),
                  tooltip: getToolTipFromMap('gridBtn5',helpMap,'Regresa a la pantalla de consulta de detalle de caso'),
                  handler:function(){window.location=_ACTION_VOLVER_A_CONSULTA_CASO_DETALLE+'?nmcaso='+NMCASO+'&cdmatriz='+CDMATRIZ+'&cdperson='+CDPERSON+'&cdformatoorden='+CDFORMATOORDEN+'&limit=999'+'&edit=0'}
                  }
                  ],          
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			bbar: new Ext.PagingToolbar({
					pageSize:itemsPerPage,
					store: storeGridMovimientos,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })          
        });          
	
	var hidden = new Ext.form.Hidden({
		id: 'hiddenCdFormatoOrdenId',
		name:'cdFormatoOrden'
	});
	
	//FORMULARIO PRINCIPAL ************************************
	var formPanel = new Ext.FormPanel({			
	        el:'formBusqueda',
	        id: 'formPanelId',	        
	        title: '<span style="color:black;font-size:12px;">Movimientos por caso</span>',
	        iconCls:'logo',
	        bodyStyle:'background: white',	              
	        frame:true,
	        reader: readerEncMovimientosCaso,
	        store: storeEncMovimientosCaso,
	        url:_ACTION_OBTENER_ENCABEZADO,
	        width: 600,
	        autoHeight:true,
	        //height:500,	        
            items:[            		
            		{            		
	        		layout: 'table',
            		layoutConfig: { columns: 4, columnWidth: .25},
            		labelAlign: 'right',
            		items:[	      
            			{
	            		layout: 'form',
	            		colspan:4,
	            		html: '<br/><br/>'
	            		},      		
	            		{
	            		layout: 'form',
	            		labelWidth: 38,
	            		items: [
	           				{
	           					xtype:'textfield',
			            		id: 'txtTareaId',
						        fieldLabel: getLabelFromMap('txtTareaId',helpMap,'Tarea'),
						        tooltip:getToolTipFromMap('txtTareaId',helpMap,'Tarea'), 				   
						        name: 'cdProceso',
						        disabled:true,
						        width: 50
						     }
	           				]
	            		},
	            		{
	            		layout: 'form',
	            		labelWidth: 0,
	            		colspan:1,
	            		items: [
	           				{
	           					xtype:'textfield',
			            		id: 'txtDesTareaId',
						        fieldLabel: getLabelFromMap('txtDesTareaId',helpMap),
						        tooltip:getToolTipFromMap('txtDesTareaId',helpMap,'Descripci&oacute;n de la Tarea'), 	
						        hasHelpIcon:getHelpIconFromMap('txtTareaId',helpMap),								 
   							    Ayuda: getHelpTextFromMap('txtTareaId',helpMap),
						        name: 'desProceso',
						        labelSeparator:'',
						        disabled:true,
						        width: 200
						     }
	           				]
	            		},
						{
	            		layout: 'form',
	            		labelWidth: 80,
	            		colspan:2,
	            		items: [
	           				{
	           					html:'',
           						id: 'txtVigenteId'
	           					/*xtype:'textfield',
			            		id: 'txtVigenteId',
						        fieldLabel: getLabelFromMap('txtDesTareaId',helpMap,'Vigente'),
						        tooltip:getToolTipFromMap('txtDesTareaId',helpMap,'Vigente'), 	        
						        name: 'indSemafColor',
						        disabled:true,
						        width: 40*/
						     }
	           				]
	            		},
						{
	            		layout: 'form', 
	            		labelWidth: 101,
	            		colspan:2,   			
	           			items: [
	           				{
	           					xtype:'textfield',
			            		id: 'txtNumCasoId',
						        fieldLabel: getLabelFromMap('txtNumCasoId',helpMap,'N&uacute;mero de Caso'),
						        tooltip:getToolTipFromMap('txtNumCasoId',helpMap,'N&uacute;mero de Caso'), 
						        hasHelpIcon:getHelpIconFromMap('txtNumCasoId',helpMap),								 
   							    Ayuda: getHelpTextFromMap('txtNumCasoId',helpMap),
						        name: 'nmCaso',
						        disabled:true,
						        width: 100
						     }
	           				]
	            		},
	            		{
	            		layout: 'form', 
	            		labelWidth: 70,   
	            		colspan:4,      			
	           			items: [
	           				{
	           					xtype:'textfield',
			            		id: 'txtNumOrdenId',
						        fieldLabel: getLabelFromMap('txtNumOrdenId',helpMap,'N&uacute;mero de Orden'),
						        tooltip:getToolTipFromMap('txtNumOrdenId',helpMap,'N&uacute;mero de Orden'), 
						        hasHelpIcon:getHelpIconFromMap('txtNumOrdenId',helpMap),								 
   							    Ayuda: getHelpTextFromMap('txtNumOrdenId',helpMap),
						        disabled:true,	        
						        name: 'cdOrdenTrabajo',
						        width: 80
						     }
	           				]
	            		},	            		
	            		{
	            		layout: 'form', 
	            		colspan:4,         			
	           			html:'<br/><br/><br/>'
	            		},
	            		{
	            		layout: 'form', 
	            		colspan:4,
	            		//labelWidth: 120,         			
	           			items: [grid]
	            		}            		
	            		]
    				}    				
    				]  
	});  
	
	//console.log(formPanel);
	formPanel.render();
	//grid.render();
		
	formPanel.load({
	params:{nmcaso: NMCASO},
	success:function(){
		Ext.getCmp('codigoFormatoOrdenId').setValue(storeEncMovimientosCaso.reader.jsonData.MListEncMovimientosCaso[0].cdFormatoOrden);
		reloadGrid();
		if (storeEncMovimientosCaso.reader.jsonData.MListEncMovimientosCaso[0].indSemafColor == 'red')
				{
				Ext.getCmp('txtVigenteId').getEl().dom.innerHTML='&nbsp; &nbsp; &nbsp; Vigente <img src="../resources/images/red.bmp" alt=""/>';
				//alert(storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].color);
				}
		if (storeEncMovimientosCaso.reader.jsonData.MListEncMovimientosCaso[0].indSemafColor == 'green')
				{
				Ext.getCmp('txtVigenteId').getEl().dom.innerHTML='&nbsp; &nbsp; &nbsp; Vigente <img src="../resources/images/green.bmp" alt=""/>';
				//alert(storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].color);
				}
		if (storeEncMovimientosCaso.reader.jsonData.MListEncMovimientosCaso[0].indSemafColor == 'yellow')
				{
				Ext.getCmp('txtVigenteId').getEl().dom.innerHTML='&nbsp; &nbsp; &nbsp; Vigente <img src="../resources/images/yellow.bmp" alt=""/>';
				//alert(storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].color);
				}
			
		/*storeGridMovimientos.load({
			params:{nmcaso: NMCASO}
		});*/
	}
	});

		
	
	function borrarMovimiento(_record){	
		Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn){
			if (btn == "yes"){				
				var  _params = "";				
				_params += "&nmcaso="+NMCASO;
				_params += "&nmovimiento="+_record.get("nmovimiento");		 				 			  
				execConnection(_ACTION_BORRAR_MOVIMIENTO_CASO, _params, cbkBorrarNuevoMovimiento);
			}		
		})
	}
	
	function cbkBorrarNuevoMovimiento (_success, _message, _response) {
		if (!_success) {
			Ext.Msg.alert('Error', _message);
		}else {
			
			Ext.Msg.alert('Aviso', _message,function(){/*ventana.close();*/reloadGrid();});			
		}
	}	
    

	function validaStatusCaso()
    
{
        var params =  {
       
                 nmCaso: formPanel.findById('txtNumCasoId').getValue()
                        
             };
    
        execConnection (_VALIDA_SATUS_CASO, params, cbkValida);

}
function cbkValida (_success, _message) {
   
    
    if (!_success) {
			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
		}else {
			//Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function() {reloadGrid();});
			//Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message);
			agregarEditar(null,Ext.getCmp('codigoFormatoOrdenId').getValue(),Ext.getCmp('txtNumCasoId').getValue(),null,Ext.getCmp("txtNumOrdenId").getValue(),Ext.getCmp('txtTareaId').getValue() , Ext.getCmp('txtDesTareaId').getValue())
		}

}               
    
	
	
	
	
});

function reloadGrid(){
		var _params = {nmcaso: NMCASO};
		reloadComponentStore(Ext.getCmp('gridId'), _params, cbkReload);
	}
	
function cbkReload(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}
}


/**
*	Carga los datos del encabezado del formulario
*
*/
function recargarDatos (formPanel) {
	formPanel.load({
	params:{nmcaso: NMCASO},
	success:function(){
		Ext.getCmp('codigoFormatoOrdenId').setValue(storeEncMovimientosCaso.reader.jsonData.MListEncMovimientosCaso[0].cdFormatoOrden);
		reloadGrid();
		if (storeEncMovimientosCaso.reader.jsonData.MListEncMovimientosCaso[0].indSemafColor == 'red')
				{
				Ext.getCmp('txtVigenteId').getEl().dom.innerHTML='&nbsp; &nbsp; &nbsp; Vigente <img src="../resources/images/red.bmp" alt=""/>';
				//alert(storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].color);
				}
		if (storeEncMovimientosCaso.reader.jsonData.MListEncMovimientosCaso[0].indSemafColor == 'green')
				{
				Ext.getCmp('txtVigenteId').getEl().dom.innerHTML='&nbsp; &nbsp; &nbsp; Vigente <img src="../resources/images/green.bmp" alt=""/>';
				//alert(storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].color);
				}
		if (storeEncMovimientosCaso.reader.jsonData.MListEncMovimientosCaso[0].indSemafColor == 'yellow')
				{
				Ext.getCmp('txtVigenteId').getEl().dom.innerHTML='&nbsp; &nbsp; &nbsp; Vigente <img src="../resources/images/yellow.bmp" alt=""/>';
				//alert(storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].color);
				}
			
		/*storeGridMovimientos.load({
			params:{nmcaso: NMCASO}
		});*/
	}
	});
}
