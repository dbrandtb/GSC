Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

var itemsPerPage=10;

var desProceso = new Ext.form.TextField({
        xtype: 'textfield',
        fieldLabel: getLabelFromMap('desProceso',helpMap,'Proceso'),
		tooltip:getToolTipFromMap('desProceso',helpMap,' Proceso'), 
        hasHelpIcon:getHelpIconFromMap('desProceso',helpMap),								 
        Ayuda: getHelpTextFromMap('desProceso',helpMap),
        name: 'desProceso',
        allowBlank: true,
        width: 120,
        id: 'desProceso'
    });

    var desFormato = new Ext.form.TextField({
        xtype: 'textfield',
        fieldLabel: getLabelFromMap('desFormato',helpMap,'Formato'),
		tooltip:getToolTipFromMap('desFormato',helpMap,'Formato'), 
        hasHelpIcon:getHelpIconFromMap('desFormato',helpMap),								 
        Ayuda: getHelpTextFromMap('desFormato',helpMap),
        name: 'desFormato',
        allowBlank: true,
        width: 120,
        id: 'desFormato'
    });

    var desCliente = new Ext.form.TextField({
        xtype: 'textfield',
        fieldLabel: getLabelFromMap('desCliente',helpMap,'Cliente'),
		tooltip:getToolTipFromMap('desCliente',helpMap,'Cliente'), 
        hasHelpIcon:getHelpIconFromMap('desCliente',helpMap),								 
        Ayuda: getHelpTextFromMap('desCliente',helpMap),
        name: 'desCliente',
        allowBlank: true,
        width: 120,
        id: 'desCliente'
    });

    var desAseguradora = new Ext.form.TextField({
        xtype: 'textfield',
        fieldLabel: getLabelFromMap('desAseguradora',helpMap,'Aseguradora'),
		tooltip:getToolTipFromMap('desAseguradora',helpMap,'Aseguradora'), 
        hasHelpIcon:getHelpIconFromMap('desAseguradora',helpMap),								 
        Ayuda: getHelpTextFromMap('desAseguradora',helpMap),
        
        
        name: 'desAseguradora',
        allowBlank: true,
        width: 120,
        id: 'desAseguradora'
    });

    var desProducto = new Ext.form.TextField({
        xtype: 'textfield',
        fieldLabel: getLabelFromMap('desProducto',helpMap,'Producto'),
		tooltip:getToolTipFromMap('desProducto',helpMap,'Producto'), 
        hasHelpIcon:getHelpIconFromMap('desProducto',helpMap),								 
        Ayuda: getHelpTextFromMap('desProducto',helpMap),
        name: 'desProducto',
        allowBlank: true,
        width: 120,
        id: 'desProducto'
    });


var incisosForm = new Ext.FormPanel({
	    id:'AsocFor',
        el:'formBusqueda',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('AsocFor', helpMap,'Asociar Formato')+'</span>',        
        iconCls:'logo',
        url: _ACTION_BUSCAR_ASOCIAR_FORMATOS,
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,
        width: 500,       
        autoHeight: true,
        items: [{
        		layout:'form',
				border: false,
				items:[{
	        		bodyStyle:'background: white',
			        labelWidth: 100,
			        html:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
				    layout: 'column',
	                layoutConfig: {columns: 2, align: 'left'},
			        defaults: {labelWidth: 90}, 
			        labelSeparator:'',
			       	buttonAlign: "center",
        		      items: [
  							  {
							   layout:'form',
							   columnWidth: .50,
							   items:[desProceso ]
							  },
							  {
							   layout: 'form',
							   columnWidth: .50, 
							   items: [ desFormato  ]
							  },
							  {
							   layout: 'form',
							   columnWidth: .50, 
							   items: [ desCliente  ]
							  },
							  {
							   layout: 'form',
							   columnWidth: .50, 
							   items: [ desAseguradora ]
							  },
							  {
							   layout: 'form',
							   columnWidth: .50, 
							   items: [ desProducto ]
							  }
							 ],
                			buttons:[
                                    {
				                     text: getLabelFromMap('AsocFormaBus',helpMap,'Buscar'),
				                     tooltip: getToolTipFromMap('AsocFormaBus',helpMap,'Busca un Formato Asociado'),
        							 handler: function() {
				               			if (incisosForm.form.isValid()) {
                                              if (grid2!=null) {
                                                             reloadGrid(grid2);
                                              } else {
                                                         createGrid();
                                                     }
                						  } else{
											      Ext.MessageBox.alert('Error', 'Por Favor revise los errores!');
								                 }
								     }
        							},
								    {
				                     text:getLabelFromMap('AsocFormaCanc',helpMap,'Cancelar'),
				                     tooltip: getToolTipFromMap('AsocFormaCanc',helpMap,'Cancelar'),
        							 handler: function() {
        								                incisosForm.form.reset();
        							                    }
        						    }
                                   ]
        					    }]
        				   }]	            
        
});   

// Definicion de las columnas de la grilla
var cm = new Ext.grid.ColumnModel([
        {
        header: getLabelFromMap('asocFormCmProc',helpMap,'Proceso'),        
		tooltip:getToolTipFromMap('asocFormCmProc',helpMap,'Proceso'), 
        hasHelpIcon:getHelpIconFromMap('asocFormCmProc',helpMap),								 
        Ayuda: getHelpTextFromMap('asocFormCmProc',helpMap),
        dataIndex: 'dsProceso',
        sortable: true,
        width: 70
        },
        {
        header: getLabelFromMap('asocFormCmForm',helpMap,'Formato'),        
		tooltip:getToolTipFromMap('asocFormCmForm',helpMap,'Formato'), 
        hasHelpIcon:getHelpIconFromMap('asocFormCmForm',helpMap),								 
        Ayuda: getHelpTextFromMap('asocFormCmForm',helpMap),
        dataIndex: 'dsFormatoOrden',
        sortable: true,
        width: 100
        },
        {
        header: getLabelFromMap('asocFormCmCli',helpMap,'Cliente'),        
		tooltip:getToolTipFromMap('asocFormCmCli',helpMap,'Cliente'), 
        hasHelpIcon:getHelpIconFromMap('asocFormCmCli',helpMap),								 
        Ayuda: getHelpTextFromMap('asocFormCmCli',helpMap),
        dataIndex: 'dsElemen',
        sortable: true,
        width: 130
        },
        {
        header: getLabelFromMap('asocFormCmAseg',helpMap,'Aseguradora'),        
		tooltip:getToolTipFromMap('asocFormCmAseg',helpMap,'Aseguradora'), 
        hasHelpIcon:getHelpIconFromMap('asocFormCmAseg',helpMap),								 
        Ayuda: getHelpTextFromMap('asocFormCmAseg',helpMap),
        dataIndex: 'dsUnieco',
        sortable: true,
        width: 130
        },
        {
        header: getLabelFromMap('asocFormCmProd',helpMap,'Producto'),        
		tooltip:getToolTipFromMap('asocFormCmProd',helpMap,'Producto'), 
        hasHelpIcon:getHelpIconFromMap('asocFormCmProd',helpMap),								 
        Ayuda: getHelpTextFromMap('asocFormCmProd',helpMap),
        dataIndex: 'dsRamo',
        sortable: true,
        width: 130
        },
        {
        dataIndex: 'cdAsocia',
        hidden :true
        }
]);

var lasFilas=new Ext.data.Record.create([
  {name: 'dsProceso',  mapping:'dsProceso',  type: 'string'},
  {name: 'dsFormatoOrden',  mapping:'dsFormatoOrden',  type: 'string'},
  {name: 'dsElemen',  mapping:'dsElemen',  type: 'string'},
  {name: 'dsUnieco',  mapping:'dsUnieco',  type: 'string'},
  {name: 'dsRamo',  mapping:'dsRamo',  type: 'string'},
  {name: 'cdAsocia', mapping:'cdAsocia', type:'string'}
]);

var jsonGrilla= new Ext.data.JsonReader(
  {
   root:'MEstructuraList',
   totalProperty: 'totalCount',
   successProperty : '@success'
  },
 lasFilas  
);

function storeGrilla(){
             store = new Ext.data.Store({
             proxy: new Ext.data.HttpProxy({
             url: _ACTION_BUSCAR_ASOCIAR_FORMATOS,
						waitMsg: getLabelFromMap('400017',helpMap,'Espere por favor...')
		                }),
             reader:jsonGrilla,	
             baseParams: {
					dsProducto: desProceso.getValue(),
					dsFormatoOrden: desFormato.getValue(),
					dsElemen: desCliente.getValue(),
					dsUnieco: desAseguradora.getValue(),
					dsRamo: desProducto.getValue()
					}
             });
             return store;
}
var grid2;

function createGrid(){
       grid2= new Ext.grid.GridPanel({
            el:'gridElementos',
            store:storeGrilla(),
            reader:jsonGrilla,
            border:true,
            title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
            cm: cm,
            buttonAlign:'center',
            loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
            clicksToEdit:1,
            id:'grillita',
	    	successProperty: 'success',
            buttons:[
                     /*{
                      text:getLabelFromMap('AsocFormBtnAdd',helpMap,'Agregar'),
                      tooltip: getToolTipFromMap('AsocFormBtnAdd',helpMap,'Crea un Nuevo Formato Asociado'),
                      handler:function(){ guardar()}
                     },*/
                     {
                      text:getLabelFromMap('AsocFormBtnEdit',helpMap,'Editar'),
                      tooltip: getToolTipFromMap('AsocFormBtnEdit',helpMap,'Editar un Formato Asociado'),
                      handler:function(){
						if (getSelectedKey(grid2, "cdAsocia") != "") {
								editar(getSelectedKey(grid2, "cdAsocia"));
                        	
                        } else {
                                  Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operaci&oacute;n');
                                }
                     }
                    },
                    /*{
                     text:getLabelFromMap('AsocFormBtnBorr',helpMap,'Borrar'),
                     tooltip: getToolTipFromMap('AsocFormBtnBorr',helpMap,'Borrar Formato Asociado'),
                     handler:function(){
						if (getSelectedKey(grid2, "cdAsocia") != "") {
                        		borrar(getSelectedKey(grid2, "cdAsocia"));
                    			} else {
		                			    Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                                       }
                	 }
                    },*/
                    {
                     text:getLabelFromMap('AsocFormaExp',helpMap,'Exportar'),
                     tooltip:getToolTipFromMap('AsocFormaExp',helpMap,'Exporta el contenido del grid'),
                     handler:function(){
                        var url = _ACTION_EXPORTAR_ASOCIAR_FORMATOS + '?dsProceso='+ desProceso.getValue()+'&dsFormatoOrden='+ desFormato.getValue()+'&dsElemen='+ desCliente.getValue()+'&dsUnieco='+ desAseguradora.getValue()+'&dsRamo='+ desProducto.getValue();
                	 	showExportDialog( url );
                     }
                    }
/*                  {
                  text:'Regresar',
                  tooltip:'Vuelve a la pantalla anterior'
                  }*/
                  ],
            width:500,
            frame:true,
            height:320,
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			bbar: new Ext.PagingToolbar({
					pageSize:20,
					store: store,
					displayInfo: true,
	                displayMsg: 'Mostrando registros {0} - {1} of {2}',
	                emptyMsg: "No hay registros para visualizar"
			    })          
        });
   grid2.render()
}


        
  
incisosForm.render();
createGrid();
                                      
function borrar(key) {
		if(key != "")
		{
			Ext.MessageBox.confirm('Eliminar', 'Se eliminará el Formato Asociado seleccionado',function(btn)
			{
		        if (btn == "yes"){
		        	var _params = {cdAsocia: key};
					execConnection(_ACTION_BORRAR_ASOCIAR_FORMATOS, _params, cbkBorrar);
                }
		    });
		}else{
				Ext.Msg.alert('Advertencia', 'Debe seleccionar un formato para eliminar');
		}
};
function cbkBorrar (_success, _message) {
	if (!_success) {
		Ext.Msg.alert('Error', _message);
	}else {
		Ext.Msg.alert('Aviso', _message, function(){reloadGrid(grid2);});
	}
}
});

function reloadGrid(grilla2){
        var _params = {
        			dsProceso: Ext.getCmp('desProceso').getValue(),
        			dsFormatoOrden: Ext.getCmp('desFormato').getValue(),
        			dsElemen: Ext.getCmp('desCliente').getValue(),
        			dsUnieco: Ext.getCmp('desAseguradora').getValue(),
        			dsRamo: Ext.getCmp('desProducto').getValue()
        };
        reloadComponentStore(grilla2, _params, cbkReload);
}
function cbkReload(_r, _o, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert('Error', _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert('Aviso', _store.reader.jsonData.actionErrors[0]);
	}
}