Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";
	
	//Campos (criterios/filtros) de búsqueda.
	 var cliente = new Ext.form.TextField({
	 id:'consConfRenTxtCli',
     fieldLabel: getLabelFromMap('consConfRenTxtCli',helpMap,'Cliente'),
     tooltip:getToolTipFromMap('consConfRenTxtCli',helpMap,' Cliente para consulta de renovaci&oacute;n'), 
  /*   hasHelpIcon:getHelpIconFromMap('consConfRenTxtCli',helpMap),								 
     Ayuda: getHelpTextFromMap('consConfRenTxtCli',helpMap),*/
     width:190,
	 name:'dsElemen'});
	 
	 var aseguradora = new Ext.form.TextField({
	 id:'consConfRenTxtAseg',	
     fieldLabel: getLabelFromMap('consConfRenTxtAseg',helpMap,'Aseguradora'),
     tooltip:getToolTipFromMap('consConfRenTxtAseg',helpMap,' Aseguradora para consulta de renovaci&oacute;n'), 
  /*   hasHelpIcon:getHelpIconFromMap('consConfRenTxtAseg',helpMap),								 
     Ayuda: getHelpTextFromMap('consConfRenTxtAseg',helpMap),*/
     width:190,
	 name:'dsUniEco'});
	 
	 var tipo = new Ext.form.TextField({
	 id:'consConfRenTxtTip',	
     fieldLabel: getLabelFromMap('consConfRenTxtTip',helpMap,'Tipo'),
     tooltip:getToolTipFromMap('consConfRenTxtTip',helpMap,' Tipo para consulta de renovaci&oacute;n'), 
   /*  hasHelpIcon:getHelpIconFromMap('consConfRenTxtTip',helpMap),								 
     Ayuda: getHelpTextFromMap('consConfRenTxtTip',helpMap),*/
     width:190,
	 name:'dsTipoRenova'});
	 
	 var producto = new Ext.form.TextField({
	 id:'consConfRenTxtProd',
     fieldLabel: getLabelFromMap('consConfRenTxtProd',helpMap,'Producto'),
     tooltip:getToolTipFromMap('consConfRenTxtProd',helpMap,' Producto para consulta de renovaci&oacute;n'), 
  /*   hasHelpIcon:getHelpIconFromMap('consConfRenTxtProd',helpMap),								 
     Ayuda: getHelpTextFromMap('consConfRenTxtProd',helpMap),*/
     width:190,
	 name:'dsRamo'});
	
	 var lyt_cliente = new Ext.Panel({layout:'form',borderStyle:false,items:[cliente]});
	 var lyt_aseguradora = new Ext.Panel({layout:'form',borderStyle:false,items:[aseguradora]});
	 var lyt_tipo = new Ext.Panel({layout:'form',borderStyle:false,items:[tipo]});
	 var lyt_producto = new Ext.Panel({layout:'form',borderStyle:false,items:[producto]});	
	
	var formBusqueda = new Ext.form.FormPanel({
		  id:'form',
	      el:'formBusqueda',
	      title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('incisosFormId',helpMap,'Configuraci&oacute;n Renovaci&oacute;n')+'</span>',
	      iconCls:'logo',
	      //defaults:{labelWidth:70},
	      bodyStyle:'background: white',
	      //bodyStyle:{position:'relative'},
	      buttonAlign: "center",
	      labelAlign: 'right',
	      autoHeight:true,
	      frame:true,
	      url:_ACTION_OBTENER_CLIENTES_TIPO_RENOVACION,
	      width: 670,
	      height:150,
	      items: [
	      		{
	      		layout:'form',
				border: false,
				items:[
					{
	      			//bodyStyle:'background: white',
	        		//labelWidth: 100,
	              	layout: 'form',
					frame:true,
			       	baseCls: '',
			       	buttonAlign: "center",
      		        items:[
      		        	{
      		        	layout:'column',
      		        	html:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
      		    		items:[
	      		    		{
						    	columnWidth:.5,
		              			layout: 'form',
		      		        	items:[lyt_cliente,lyt_tipo]
							},
							{
								columnWidth:.5,
		              			layout: 'form',
		              			items:[lyt_aseguradora,lyt_producto]
	              			}
	              		 ]
              		}],
			        buttons:[
			        	{
					     text:getLabelFromMap('consConfRenBtnSeek',helpMap,'Buscar'),
					     tooltip: getToolTipFromMap('consConfRenBtnSeek',helpMap,'Busca p&oacute;lizas para renovaci&oacute;n'),
  						 handler: function(){if(grid!=null){reloadGrid();}else{createGrid();}}
			      		},
				      	{
					     text:getLabelFromMap('consConfRenBtnCanc',helpMap,'Cancelar'),
					     tooltip: getToolTipFromMap('consConfRenBtnCanc',helpMap,'Cancela operaci&oacute;n de p&oacute;lizas para renovaci&oacute;n'),
						 handler: function(){formBusqueda.form.reset();}
			      		}
			      		]
			   	}]
			}]
		});
	
	 var _cm = new Ext.grid.ColumnModel([
		{
        header: getLabelFromMap('consConfRenCmCli',helpMap,'Cliente'),
        tooltip: getToolTipFromMap('consConfRenCmCli',helpMap,'Columna de Clientes para renovaci&oacute;n'),
     /*   hasHelpIcon:getHelpIconFromMap('consConfRenCmCli',helpMap),								 
        Ayuda: getHelpTextFromMap('consConfRenCmCli',helpMap),*/
        
		dataIndex: 'dsElemen',
		sortable: true,
		width:200},
		{
        header: getLabelFromMap('consConfRenCmAseg',helpMap,'Aseguradora'),
        tooltip: getToolTipFromMap('consConfRenCmAseg',helpMap,'Columna de Aseguradora para renovaci&oacute;n'),
    /*    hasHelpIcon:getHelpIconFromMap('consConfRenCmAseg',helpMap),								 
        Ayuda: getHelpTextFromMap('consConfRenCmAseg',helpMap),*/
        
		dataIndex: 'dsUniEco',
		sortable: true,
		width:115},
	    {
        header: getLabelFromMap('consConfRenCmProd',helpMap,'Producto'),
        tooltip: getToolTipFromMap('consConfRenCmProd',helpMap,'Columna de Producto para renovaci&oacute;n'),
    /*    hasHelpIcon:getHelpIconFromMap('consConfRenCmProd',helpMap),								 
        Ayuda: getHelpTextFromMap('consConfRenCmProd',helpMap),*/

	    dataIndex: 'dsRamo',
	    sortable: true,
	    width:170},
	    {
        header: getLabelFromMap('consConfRenCmTip',helpMap,'Tipo'),
        tooltip: getToolTipFromMap('consConfRenCmTip',helpMap,'Columna de Tipo para renovaci&oacute;n'),
   /*     hasHelpIcon:getHelpIconFromMap('consConfRenCmTip',helpMap),								 
        Ayuda: getHelpTextFromMap('consConfRenCmTip',helpMap),*/
        
	    dataIndex: 'dsTipoRenova',
	    sortable: true,
	    width:100},
	    {
        header: getLabelFromMap('consConfRenCmDias',helpMap,'D&iacute;as'),
        tooltip: getToolTipFromMap('consConfRenCmDias',helpMap,'Columna de D&iacute;as para renovaci&oacute;n'),
   /*     hasHelpIcon:getHelpIconFromMap('consConfRenCmDias',helpMap),								 
        Ayuda: getHelpTextFromMap('consConfRenCmDias',helpMap),*/
        
	    dataIndex: 'cdDiasAnticipacion',
	    sortable: true,
	    width:50, 
	    align: 'center'},
	    /*{
        header: getLabelFromMap('consConfRenConNum',helpMap,'Continua Numeraci&oacute;n'),
        tooltip: getToolTipFromMap('consConfRenConNum',helpMap,'Columna de Continua Numeraci&oacute;n'),
	    dataIndex: 'swConNum',
	    sortable: true,
	    width:50, 
	    align: 'center'},*/
	    {
	    dataIndex: 'cdRenova',
	    hidden: true},
	    {
	    dataIndex: 'cdTipoRenova',
	    hidden: true}
	 ]);
		
	var _proxy = new Ext.data.HttpProxy({url: _ACTION_OBTENER_CLIENTES_TIPO_RENOVACION});
	
	var _reader = new Ext.data.JsonReader(
		{root: 'confRenovaEstructuraList',totalProperty: 'totalCount',successProperty: '@success'},
		[
       		{name: 'cdRenova',   type: 'string',  mapping:'cdRenova'},
       		{name: 'cdElemento',   type: 'string',  mapping:'cdElemento'},
			{name: 'dsElemen',  type: 'string',  mapping:'dsElemen'},
       		{name: 'cdTipoRenova',  type: 'string',  mapping:'cdTipoRenova'},
       		{name: 'dsTipoRenova', type: 'string',  mapping:'dsTipoRenova'},
       		{name: 'cdUniEco', type: 'string',  mapping:'cdUniEco'},
       		{name: 'dsUniEco', type: 'string',  mapping:'dsUniEco'},
       		{name: 'cdRamo', type: 'string',  mapping:'cdRamo'},
       		{name: 'dsRamo', type: 'string',  mapping:'dsRamo'},
       		{name: 'cdDiasAnticipacion', type: 'string',  mapping:'cdDiasAnticipacion'}//,
       		//{name: 'swConNum', type: 'string',  mapping:'swConNum'}
		]);
		
	var _store = new Ext.data.Store(
		{
		 proxy: _proxy,
		 id: 'storeConfRen',
		 reader: _reader,
		 waitMsg: getLabelFromMap('400017',helpMap,'Espere por favor...'),
		 baseParams:{
		 			 dsElemen:cliente.getValue(),
		 			 dsUniEco:aseguradora.getValue(),
		 			 dsTipoRenova:tipo.getValue(),
		 			 dsRamo:producto.getValue()
		 			 }
		});

	var grid = new Ext.grid.GridPanel({
			id:'grid',
	        el:'grilla',
	        store:_store,
	        buttonAlign:'center',
			border:true,
			loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
			title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
	        cm: _cm,
			buttons:[
	        		{
					text:getLabelFromMap('consConfRenBtnAdd',helpMap,'Agregar'),
					tooltip: getToolTipFromMap('consConfRenBtnAdd',helpMap,'Agrega una nueva configuraci&oacute;n de renovaci&oacute;n'),
            		handler:function(){guardar(null);}
	            	},
	        		{
					text:getLabelFromMap('consConfRenBtnEd',helpMap,'Editar'),
					tooltip: getToolTipFromMap('consConfRenBtnEd',helpMap,'Edita una configuraci&oacute;n de renovaci&oacute;n'),
            		handler:function()
            				{if(getSelectedRecord(grid)!=null){guardar(getSelectedRecord(grid));}
                			 else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));}
                			}
	            	},
	            	{
					text:getLabelFromMap('consConfRenBtnDel',helpMap,'Eliminar'),
					tooltip: getToolTipFromMap('consConfRenBtnDel',helpMap,'Elimina una configuración de renovaci&oacute;n'),
            		handler:function()
            				{if(getSelectedRecord(grid)!=null){borrar(getSelectedRecord(grid));}
                			 else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));}
	                		}
	            	},
	        		{
					text:getLabelFromMap('consConfRenBtnRol',helpMap,'Roles'),
					id: 'btnRoles',
					tooltip: getToolTipFromMap('consConfRenBtnRol',helpMap,'Ir a Configuraci&oacute;n de Roles de Renovaci&oacute;n para Reporte'),
                    handler:function(){
                    if (getSelectedKey(grid, "cdRenova") != ""){
	                        goToPage(getSelectedKey(grid, "cdRenova"),1);
	                    } else {
	                    	Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
	                    }
	                  }       		
	            	},
	        	/*  este boton se quito "botón Acciones", dado que, la seguridad de campos desapareció(se quitó).
	        	*
	        		{
					text:getLabelFromMap('consConfRenBtnAcc',helpMap,'Acciones'),
					tooltip: getToolTipFromMap('consConfRenBtnAcc',helpMap,'Ir a Configuración de Acciones de Renovaci&oacute;n para Reporte'),
                    handler:function(){
                    if (getSelectedKey(grid, "cdRenova") != ""){
                    		validate(getSelectedKey(grid, "cdRenova"));	                        
	                    } else {
	                    	Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
	                    }
	                  }
        			},*/
	        		{
					text:getLabelFromMap('consConfRenBtnRang',helpMap,'Rangos'),
					tooltip: getToolTipFromMap('consConfRenBtnRang',helpMap,'Ir a Configuraci&oacute;n de Rangos de Renovaci&oacute;n para Reporte'),
                    handler:function(){
                    if (getSelectedKey(grid, "cdRenova") != "") {
	                        goToPage(getSelectedKey(grid, "cdRenova"),3);
	                    } else {
	                    	Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
	                    }
	                  }
                  
        			},
	            	{
					text:getLabelFromMap('consConfRenBtnExp',helpMap,'Exportar'),
					tooltip: getToolTipFromMap('consConfRenBtnExp',helpMap,'Exporta las configuraciones de renovaci&oacute;n listadas'),
                    handler:function(){
                        var url = _ACTION_EXPORTAR_CLIENTES_TIPO_RENOVACION + '?dsElemen=' + cliente.getValue()+ '&dsUniEco=' + aseguradora.getValue()+ '&dsTipoRenova=' + tipo.getValue() + '&dsRamo=' + producto.getValue();
                	 	showExportDialog(url);
                        }
	            	}/*,
	            	{
					text:getLabelFromMap('consConfRenBtnBack',helpMap,'Regresar'),
					tooltip: getToolTipFromMap('consConfRenBtnBack',helpMap,'Regresa a la pantalla anterior')
	                }*/
	            	],
	    	width:670,
	    	frame:true,
			height:320,
			sm: new Ext.grid.RowSelectionModel({
				singleSelect: true,
				listeners: {
				
				rowselect: function(sm, row, rec){
						
						 var tipoRen=rec.get('cdTipoRenova');
							 if(!Ext.isEmpty(tipoRen)){
							 	if(tipoRen=='2'){
							 		Ext.getCmp('btnRoles').setDisabled(false);
							 	}
							 	else{
							 		Ext.getCmp('btnRoles').setDisabled(true);
							 	}
							 }
					}
				}
			}),
			
			stripeRows: true,
			collapsible:true,
			bbar: new Ext.PagingToolbar({
					pageSize: itemsPerPage,
					store: _store,
					displayInfo: true,
					displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })
			});

	formBusqueda.render();
	grid.render();
	

function validate(key)
{
	var _params = {cdRenova: key};
    
    execConnection(_ACTION_VALIDAR_ROL_PARA_IR_A_CONF_ACCION, _params, cbkValidateConfig);
}
	
function cbkValidateConfig(_success, _message, _rdo)
{
	if(_success){
		var code = Ext.util.JSON.decode(_rdo).codeResult;
	 	if(code == 1){goToPage(getSelectedKey(grid, "cdRenova"),2);}
	 	else{Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400106', helpMap,'No existen roles asociados a este registro. Debe configurar roles primero'));}
	}
	else{Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'),getLabelFromMap('400107', helpMap,'Error en la validaci&oacute;n'));}
}

function goToPage(_code, _key)
{
	var url;
    switch(_key)
    {
    	case 1:_url=_ACTION_IR_DETALLE_ROLES_DE_RENOVACION_REPORTE;break;
    	case 2:_url=_ACTION_IR_DETALLE_ACCIONES_DE_RENOVACION_REPORTE;break;
    	case 3:_url=_ACTION_IR_DETALLE_RANGOS_DE_RENOVACION_REPORTE;break;
    }
    window.location=_url+"?cdRenova="+_code;
};

if(_FLAG){var params={dsElemen: "",dsUniEco: "",dsTipoRenova: "",dsRamo: ""};reloadComponentStore(Ext.getCmp('grid'), params, myCallback);}
	
});

function reloadGrid()
	{		
		var params = {
				dsElemen: Ext.getCmp('form').form.findField('dsElemen').getValue(),
			  	dsUniEco: Ext.getCmp('form').form.findField('dsUniEco').getValue(),
			  	dsTipoRenova: Ext.getCmp('form').form.findField('dsTipoRenova').getValue(),
			  	dsRamo: Ext.getCmp('form').form.findField('dsRamo').getValue()
			  };
		reloadComponentStore(Ext.getCmp('grid'), params, myCallback);
	};

function myCallback(_rec, _opt, _success, _store)
	{
		if (!_success){
			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
			_store.removeAll();
		}
	};	