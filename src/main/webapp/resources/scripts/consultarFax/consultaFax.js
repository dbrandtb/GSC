Ext.onReady(function(){   
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";			
	var cm = new Ext.grid.ColumnModel([
        {
        header: getLabelFromMap('cmTipoArId',helpMap,'Tipo de Archivo'),
        tooltip: getToolTipFromMap('cmTipoArId',helpMap,'Tipo de Archivo'),
        dataIndex: 'dsarchivo',
        width: 120,
        sortable: true
        },
        {
        header: getLabelFromMap('cmNumeroFaxId',helpMap,'N&uacute;mero de Fax'),
        tooltip: getToolTipFromMap('cmNumeroFaxId',helpMap,'N&uacute;mero de Fax'),
        dataIndex: 'nmfax',
        width: 95,
        sortable: true
        },
        {
        header: getLabelFromMap('cmPolizaId',helpMap,'P&oacute;liza'),
        tooltip: getToolTipFromMap('cmPolizaId',helpMap,'P&oacute;liza'),
        dataIndex: 'nmpoliex',
        width: 45,
        sortable: true
        },
		{       
        header: getLabelFromMap('cmCasoId',helpMap,'N&uacute;mero de Caso'),
        tooltip: getToolTipFromMap('cmCasoId',helpMap,'N&uacute;mero de Caso'),
        dataIndex: 'nmcaso',
        width: 100,
        sortable: true
        },
        {        
        header: getLabelFromMap('cmUregistro',helpMap,'Usuario Registro'),
        tooltip: getToolTipFromMap('cmUregistro',helpMap,'Usuario Registro'),
        dataIndex: 'dsusuari',
        width: 100,
        sortable: true
        },
        {        
        header: getLabelFromMap('cmFechaIng',helpMap,'Fecha de Ingreso'),
        tooltip: getToolTipFromMap('cmFechaIng',helpMap,'Fecha de Ingreso'),
        dataIndex: 'ferecepcion',
        width: 150,
        sortable: true
        } ,      
        {
        dataIndex:'cdtipoar',hidden:true
        }/*,
        {
        dataIndex:'cdusuari',hidden:true
        },
        {
        dataIndex:'dsusuari',hidden:true
        },
        {
        dataIndex:'ferecepcion',hidden:true
        },
         
         {
        dataIndex:'blarchivo',hidden:true
        },  
         {
        dataIndex:'otvalor',hidden:true
        },       
        {
        dataIndex:'dstipoar',hidden:true
        }*/
	]);
	
	
	
	//alert(_VAR);
	
	
	var grid = new Ext.grid.GridPanel({
       		id: 'gridId',
       		el:'gridListado',
            store: storeGridFax,
            border:true,
            cm: cm,
	        successProperty: 'success', 
	        title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',           
            width:554,
            loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'),
            disabled: false},
            buttonAlign:'center',
            frame:true,
            height:320,  
            frame: true,
           buttons:[
                  {
                  text:getLabelFromMap('gridBtn1',helpMap,'Agregar'),
                  tooltip: getToolTipFromMap('gridBtn1',helpMap,'Agrega Fax'),
                  handler:function(){
                  /*agregarEditar(null,Ext.getCmp('codigoFormatoOrdenId').getValue(),Ext.getCmp('txtNumCasoId').getValue(),null,Ext.getCmp("txtNumOrdenId").getValue())*/
                      window.location = _IR_A_ADMINISTRAR_FAX +'?flag='+1;
                    }
                  },                  
                  
                  {
                  text:getLabelFromMap('gridBtn3',helpMap,'Editar'),
                  tooltip: getToolTipFromMap('gridBtn3',helpMap,'Edita un fax seleccionado del grid'),
                  handler:function(){
						if (getSelectedRecord(grid)) {
						         window.location = _IR_A_ADMINISTRAR_FAX_EDITAR+'?nmfax='+getSelectedRecord(grid).get('nmfax')+ '&nmcaso='+getSelectedRecord(grid).get('nmcaso')+ '&cdtipoar='+getSelectedRecord(grid).get('cdtipoar') ;
                        		//agregarEditar(getSelectedRecord(grid),Ext.getCmp('codigoFormatoOrdenId').getValue(),Ext.getCmp('txtNumCasoId').getValue(),getSelectedRecord(grid).get("nmovimiento"),Ext.getCmp("txtNumOrdenId").getValue());
               			} else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                        }
                  }
                  },
                  
                  {
                  text:getLabelFromMap('gridBtn2',helpMap,'Eliminar'),
                  tooltip: getToolTipFromMap('gridBtn2',helpMap,'Elimina un fax seleccionado del grid'),
                  handler:function(){
						if (getSelectedKey(grid, "nmfax") != "") {
                                borrar(getSelectedKey(grid, "nmfax"),getSelectedKey(grid, "nmcaso"));
               			} else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                        }}
                  },
                  
                  /*
                  {
                  text:getLabelFromMap('gridBtn4',helpMap,'Regresar'),
                  tooltip: getToolTipFromMap('gridBtn4',helpMap,'Regresa al menu principal '),
                  handler:function(){window.close}
                  },
                  */
                  
                 {
                  text:getLabelFromMap('gridTrsCtBoButtonExportar',helpMap,'Exportar'),
                  tooltip:getToolTipFromMap('gridTrsCtBoButtonExportar',helpMap,'Exporta un Fax'),
                  handler:function(){
                        //alert(comboCodiPrioridad.getValue());
                        url = _ACTION_EXPORTAR_FAX+'?dsarchivo='+ Ext.getCmp('txtArchivoId').getValue()+'&nmcaso='+ Ext.getCmp('txtNumCasoId').getValue()+'&nmfax='+ Ext.getCmp('txtNumFaxId').getValue()+'&nmpoliex='+Ext.getCmp('txtPolizaId').getValue();
                        showExportDialog( url );        
                    }                 
                  }
                  
                  ],          
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			bbar: new Ext.PagingToolbar({
					pageSize:itemsPerPage,
					store: storeGridFax,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })          
        });          
	
	var cdPerson = new Ext.form.Hidden({id:'cdPersonId', name:'cdPerson'});
	
	//FORMULARIO PRINCIPAL ************************************
	var formPanelFax = new Ext.FormPanel({			
	        el:'formBusqueda',
	        id: 'formPanelFax',	        
	       // title: '<span style="color:black;font-size:12px;">Consulta de Fax</span>',
	        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('formPanelFax', helpMap,'Consulta de Fax')+'</span>',
	        iconCls:'logo',
	        bodyStyle:'background: white',	
	        buttonAlign: "center",              
	        frame:true,
	        width: 554,
	        autoHeight:true,	        
            items:[
            		{            		
	        		layout: 'table',
            		layoutConfig: { columns: 3, columnWidth: .33},
            		labelAlign: 'right',
            		items:[
	            		{
	            		html: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br/>',
	            		colspan:3
	            		},
	            								
						{
	            		layout: 'form', 
	            		labelWidth: 120, 
	            		colspan:3,        			
	           			items: [
	           				{
	           					xtype:'textfield',
			            		id: 'txtArchivoId',
						        fieldLabel: getLabelFromMap('txtArchivoId',helpMap,'Tipo de Archivo'),
						        tooltip:getToolTipFromMap('txtArchivoId',helpMap,'Tipo de Archivo'),
						        hasHelpIcon:getHelpIconFromMap('txtArchivoId',helpMap),
		                        Ayuda: getHelpTextFromMap('txtArchivoId',helpMap),
						        name: 'cdtipoar',						        
						        width: 200
						     }
	           				]
	            		}, 
	            		
	            		{
	            		layout: 'form', 
	            		labelWidth: 120,  
	            		colspan:3,       			
	           			items: [
	           				{
	           					xtype:'textfield',
			            		id: 'txtNumCasoId',
						        fieldLabel: getLabelFromMap('txtNumCasoId',helpMap,'N&uacute;mero de Caso'),
						        tooltip:getToolTipFromMap('txtNumCasoId',helpMap,'N&uacute;mero de Caso'),
						        hasHelpIcon:getHelpIconFromMap('txtNumCasoId',helpMap),
		                        Ayuda: getHelpTextFromMap('txtNumCasoId',helpMap),
						        name: 'nmcaso',						        
						        width: 200
						     }
	           				]
	            		}, 
	            		
	            		{
	            		layout: 'form', 
	            		labelWidth: 120, 
	            		colspan:3,        			
	           			items: [
	           				{
	           					xtype:'textfield',
			            		id: 'txtNumFaxId',
						        fieldLabel: getLabelFromMap('txtNumFaxId',helpMap,'N&uacute;mero de Fax'),
						        tooltip:getToolTipFromMap('txtNumFaxId',helpMap,'N&uacute;mero de Fax'),
						         hasHelpIcon:getHelpIconFromMap('txtNumFaxId',helpMap),
		                        Ayuda: getHelpTextFromMap('txtNumFaxId',helpMap),
						        name: 'nmfax',						        
						        width: 200
						     }
	           				]
	            		}, 
	            		
	            		{
	            		layout: 'form', 
	            		labelWidth: 120, 
	            		colspan:3,        			
	           			items: [
	           				{
	           					xtype:'textfield',
			            		id: 'txtPolizaId',
						        fieldLabel: getLabelFromMap('txtPolizaId',helpMap,'P&oacute;liza'),
						        tooltip:getToolTipFromMap('txtPolizaId',helpMap,'P&oacute;liza'),
						        hasHelpIcon:getHelpIconFromMap('txtPolizaId',helpMap),
		                        Ayuda: getHelpTextFromMap('txtPolizaId',helpMap),
						        name: 'nmpoliex',						        
						        width: 200
						     }
	           				]
	            		}
	            		            		           		
            		
            		],
            		 buttonAlign: "center",	
            		 buttons:[{
    							text:getLabelFromMap('txtBtnBuscarId',helpMap,'Buscar'),
    							tooltip: getToolTipFromMap('txtBtnBuscarId',helpMap,'Busca fax'),
    							handler: function() {
               					if (formPanelFax.form.isValid()) {
                                           if (grid!=null) {reloadGrid();}
            						}
            						else
            						{
            							Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
									}
								}
    							},
    							{
    							text:getLabelFromMap('txtBtnCancelarId',helpMap,'Cancelar'),
    							tooltip: getToolTipFromMap('txtBtnCancelarId',helpMap,'Cancela la b&uacute;squeda de fax'),                              
    							handler: function() {
    								formPanelFax.form.reset();
    							}
    						}]
    				}]  
	});  
	
	
	//console.log(formPanelFax);
	formPanelFax.render();
	grid.render();
	
	
	function borrar(_nmfax,_nmcaso) {
		if(_nmfax != "" && _nmcaso != "" )
		{
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), 
			getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),
			
			function(btn)
			{
		        if (btn == "yes")
		        {
         			var _params = {
         						nmfax: _nmfax,
         						nmcaso:_nmcaso
         			};
         			execConnection(_ACTION_BORRAR_DETALLE_FAX, _params, cbkConnection);
               }
			})
		}else{
				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
		}
};
function cbkConnection (_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid()});
	}
}     

if(_VAR==1){
	reloadGrid();
	Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400117', helpMap,'Los datos se modificaron con éxito'));
	
	}  

  
  function reloadGrid(){
		var _params = {
       		dsarchivo: Ext.getCmp('txtArchivoId').getValue(),
       		nmcaso: Ext.getCmp('txtNumCasoId').getValue(),
       		nmfax: Ext.getCmp('txtNumFaxId').getValue(),	      		
       		nmpoliex: Ext.getCmp('txtPolizaId').getValue()
       		
       		};
       			
		reloadComponentStore(Ext.getCmp('gridId'), _params, cbkReload);
	};
	
	function cbkReload(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		_VAR==0;
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
		}else{_VAR==0;}
	}
  
  
	

});






