Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";
	
	var readerStore=new Ext.data.JsonReader({
           	root:'encabezado',
           	totalProperty: 'totalCount',
            successProperty : '@success'
	        },[
	        {name: 'dsElemen', type: 'string',  mapping:'dsElemen'},
	        {name: 'cdElemento', type: 'string',  mapping:'cdElemento'},
	        {name: 'dsUniEco', type: 'string',  mapping:'dsUniEco'},
	        {name: 'dsTipoRenova',   type: 'string',  mapping:'dsTipoRenova'},
	        {name: 'dsRamo',   type: 'string',  mapping:'dsRamo'}
			]);
			
	var formularioStore = new Ext.data.Store({
   			proxy: new Ext.data.HttpProxy({
			url: _ACTION_GET_ENCABEZADO_ROLES_RENOVACION,
			waitMsg: 'Espere por favor....'
            }),
            reader: readerStore
        });
		    	
	            
	var el_form = new Ext.FormPanel ({
			el:'formBusqueda',
			reader: readerStore,
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('el_formSeccionesId', helpMap,'Roles de Ejecuci&oacute;n de Renovaci&oacute;n')+'</span>',
            labelWidth : 10,
            url : _ACTION_GET_ENCABEZADO_ROLES_RENOVACION,
            frame : true,
            width : 500,
            height: 130,
            waitMsgTarget : true,
            layout: 'column',
            bodyStyle:'background: white',
            labelAlign:'right',
            layoutConfig: {columns: 2},
            defaults:{labelWidth:70},
//            items: [{
  //          	layout:'form',
    //        	frame:true,
      // 			html:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;"></span><br>',

            	items:[
            			{layout: 'form',
            			columnWidth: .50,
            			//html:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;"></span><br>',
            			items: [
				                  {xtype: 'textfield',
				                  fieldLabel: getLabelFromMap('confRolRenTxtCli',helpMap,'Cliente'),
     							  tooltip:getToolTipFromMap('confRolRenTxtCli',helpMap,' Cliente para consulta de Renovaci&oacute;n de Roles'), 
				                  hasHelpIcon:getHelpIconFromMap('confRolRenTxtCli',helpMap),
				                  helpText:getHelpTextFromMap('confRolRenTxtCli',helpMap),
				                  id: 'dsElemen', 
				                  name: 'dsElemen', 
				                  readOnly:'true',
				                  disabled:true}
            					]
            			},
            			{layout: 'form',
            			columnWidth: .50,
            			items: [
            						{xtype: 'textfield', 
				                     fieldLabel: getLabelFromMap('confRolRenTxtAseg',helpMap,'Aseguradora'),
     							     tooltip:getToolTipFromMap('confRolRenTxtAseg',helpMap,' Aseguradora para consulta de Renovaci&oacute;n de Roles'), 
            						 hasHelpIcon:getHelpIconFromMap('confRolRenTxtAseg',helpMap),
				                  	 helpText:getHelpTextFromMap('confRolRenTxtAseg',helpMap),
            						 id: 'dsUniEco', 
            						 name: 'dsUniEco', 
            						 readOnly:'true',
				                  	 disabled:true}            						
            					]
            			},
            			{layout: 'form',
            			columnWidth: .50,
            			items: [
				                    {xtype: 'textfield',
				                    fieldLabel: getLabelFromMap('confRolRenTxtTip',helpMap,'Tipo'),
     							    tooltip:getToolTipFromMap('confRolRenTxtTip',helpMap,' Tipo para consulta de Renovaci&oacute;n de Roles'),
            						hasHelpIcon:getHelpIconFromMap('confRolRenTxtTip',helpMap),
				                  	helpText:getHelpTextFromMap('confRolRenTxtTip',helpMap),
				                    id: 'dsTipoRenova', 
				                    name: 'dsTipoRenova', 
				                    readOnly:'true',
				                  	disabled:true}
            					]
            			},
            			{layout: 'form',
            			columnWidth: .50, 
            			items: [
            						{xtype: 'textfield', 
				                    fieldLabel: getLabelFromMap('confRolRenTxtProd',helpMap,'Producto'),
     							    tooltip:getToolTipFromMap('confRolRenTxtProd',helpMap,' Producto para consulta de Renovaci&oacute;n de Roles'),
            						hasHelpIcon:getHelpIconFromMap('confRolRenTxtProd',helpMap),
            						helpText:getHelpTextFromMap('confRolRenTxtTip',helpMap),
            						name: 'dsRamo', 
            						id: 'dsRamo', 
            						readOnly:'true',
				                  	disabled:true}
            					]
            			},
            			{xtype: 'hidden', name: 'cdElemento', id: 'cdElemento' }
           		]
           // 	}]


	});


	    var cm = new Ext.grid.ColumnModel([
	    		{
	    			dataIndex: 'cdRol',
	    			hidden: true
	    		},
				{
		           	header:getLabelFromMap('consConfCmRol',helpMap,'Rol'),
		           	tooltip:getToolTipFromMap('consConfCmRol',helpMap,' Columna de Roles de Renovaci&oacute;n'), 
		           	headerAlign:'left',
		           	dataIndex: 'dsSisRol',
		           	width: 480,
		           	sortable: true
	        	}
	           	]);
	           	
		
		var store;
		 			var store = new Ext.data.Store({
	    			proxy: new Ext.data.HttpProxy({
					url: _ACTION_OBTENER_ROLES_RENOVACION,
					waitMsg: 'Espere por favor....'
	                }),
	                reader: new Ext.data.JsonReader({
	            	root:'listaRoles',
	            	totalProperty: 'totalCount',
		            successProperty : '@success'
			        },[
			        {name: 'dsSisRol',  type: 'string',  mapping:'dsSisRol'},
			        {name: 'cdRol',  type: 'string',  mapping:'cdRol'},
			        {name: 'cdRenova',  type: 'string',  mapping:'cdRenova'}
					])
		        });

		var grilla= new Ext.grid.GridPanel({
			id:'grilla',
	        el:'gridElementos',
	        store: store,
	        title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
	        buttonAlign:'center',
			border:true,
	        cm: cm,
	        loadMask: {msg: getLabelFromMap('400058',helpMap,'Cargando datos ...'), disabled: false},
			buttons:[
		        	  {
		        		  text: getLabelFromMap('confRolRenBtnAdd', helpMap,'Agregar'),
		        		  tooltip:getToolTipFromMap('confRolRenBtnAdd',helpMap,' Agrega un Nuevo Rol a la Renovaci&oacute;n'),
		            	  handler:function(){agregar(_CodRenovacion ,  Ext.getCmp("cdElemento").getValue())}
		              },
	                  {
		        		  text: getLabelFromMap('confRolRenBtnDel', helpMap,'Eliminar'),
		        		  tooltip:getToolTipFromMap('confRolRenBtnDel',helpMap,'Elimina un Rol a la Renovaci&oacute;n'),
		                  handler:function(){
							if (getSelectedKey(grilla,"cdRol") != "") {
	                        		borrar(getSelectedKey(grilla, "cdRol"));
                    			} else {
                                        Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operaci&oacute;n');
                                }
	                	}
	                  },
		              {
		        		  text: getLabelFromMap('confRolRenBtnExp', helpMap,'Exportar'),
		        		  tooltip:getToolTipFromMap('confRolRenBtnExp',helpMap,' Exporta los Roles del Grid '),
		                  handler:function(){
		                        var url = _ACTION_EXPORTAR_ROLES_RENOVACION + '?cdRenova='+ _CodRenovacion;
		                	 	showExportDialog( url );
		                    }
                     },
	                  {
		        		  text: getLabelFromMap('confRolRenBtnBack', helpMap,'Regresar'),
		        		  tooltip:getToolTipFromMap('confRolRenBtnBack',helpMap,' Vuelve a la Pantalla Anterior '),
						  handler: function() {
						  window.location=_ACTION_REGRESAR_A_CONSULTA_CONFIGURARCION_RENOVACION + '?cdRenova=' + _CodRenovacion;
					  }
                     }
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
					displayInfo: true,
					displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })
			});
	el_form.render();
	grilla.render();


function borrar(key) {
		if(key != "") 
		{
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
			{
		        if (btn == "yes"){
		        	var _params = {cdRenova:_CodRenovacion, cdRol:key};
					execConnection(_ACTION_BORRAR_ROL_RENOVACION, _params, cbkBorrar);
                }
		    });
		}else{
			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
		}
};
	function cbkBorrar (_success, _message) {
		if (!_success) {
			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
		}else {
			Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid();});
		}
	}

	el_form.load(
				{params:{cdRenova: _CodRenovacion},
				 success:function(){
						reloadGrid();
				}
				});

});  

 function reloadGrid()
	{
		var params = {
				cdRenova: _CodRenovacion
			  };
		reloadComponentStore(Ext.getCmp('grilla'), params, myCallback);
	};

 function myCallback(_rec, _opt, _success, store)
	{
		if (!_success){
			//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Aviso'), store.reader.jsonData.actionErrors[0]);
			store.removeAll();
		}
	};		
