Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

    var cm = new Ext.grid.ColumnModel([
        
        {
        header: getLabelFromMap('perGraClieCmCli',helpMap,'Cliente'),
        tooltip: getToolTipFromMap('perGraClieCmCli',helpMap,'Cliente de per&iacute;odos de gracia'),
        dataIndex: 'cliente',
        width: 120,
        sortable: true
        },{
        header: getLabelFromMap('perGraClieCmAseg',helpMap,'Aseguradora'),
        tooltip: getToolTipFromMap('perGraClieCmAseg',helpMap,'Aseguradora de per&iacute;odos de gracia por cliente'),
        dataIndex: 'aseguradora',
        width: 120,
        sortable: true
        },{
        header: getLabelFromMap('perGraClieCmProd',helpMap,'Producto'),
        tooltip: getToolTipFromMap('perGraClieCmProd',helpMap,'Producto de per&iacute;odos de gracia por cliente'),
        dataIndex: 'producto',
        width: 120,
        sortable: true
         },{
        header: getLabelFromMap('perGraClieCmRec',helpMap,'Recibos'),
        tooltip: getToolTipFromMap('perGraClieCmRec',helpMap,'Recibos de per&iacute;odos de gracia por cliente'),
        dataIndex: 'recibos',
        width: 120,
        sortable: true
         },{
        header: getLabelFromMap('perGraClieCmDdG',helpMap,'D&iacute;as De Gracia'),
        tooltip: getToolTipFromMap('perGraClieCmDdG',helpMap,'D&iacute;as de gracia de per&iacute;odos '),
        dataIndex: 'diasDeGracia',
        width: 120,
        align:'center',
        sortable: true
         },{
        header: getLabelFromMap('perGraClieCmDAC',helpMap,'D&iacute;as Antes De Cancelaci&oacute;n'),
        tooltip: getToolTipFromMap('perGraClieCmDAC',helpMap,'D&iacute;as Antes De Cancelaci&oacute;n de per&iacute;odos de gracia por cliente'),
        dataIndex: 'diasAntesDeCancelacion',
        width: 155,
        align:'center',
        sortable: true
        },{
        header: "cdElemento",
        dataIndex: 'cdElemento',
        hidden:true
        },{
        header: "cdPerson",
        dataIndex: 'cdPerson',
        hidden:true
        },{
        header: "cdTramo",
        dataIndex: 'cdTramo',
        hidden:true
        },{
        header: "cveAseguradora",
        dataIndex: 'cveAseguradora',
        hidden:true
        },{
        header: "cveProducto",
        dataIndex: 'cveProducto',
        hidden:true
       
    }]);


    var dsElemen = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('perGraClieTxtCli',helpMap,'Cliente'),
        tooltip:getToolTipFromMap('perGraClieTxtCli',helpMap,'Cliente de per&iacute;odo de gracia '), 
        hasHelpIcon:getHelpIconFromMap('perGraClieTxtCli',helpMap),
		Ayuda: getHelpTextFromMap('perGraClieTxtCli',helpMap),
		id:'perGraClieTxtCli',
        name:'dsElemen',
        allowBlank: true,
        anchor: '100%'
       
    });
    

    var dsRamo = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('perGraClieTxtProd',helpMap,'Producto'),
        tooltip:getToolTipFromMap('perGraClieTxtProd',helpMap,'Producto de per&iacute;odo de gracia por cliente'),
        hasHelpIcon:getHelpIconFromMap('perGraClieTxtProd',helpMap),
		Ayuda: getHelpTextFromMap('perGraClieTxtProd',helpMap),
        id:'perGraClieTxtProd',
        name:'dsRamo',
        allowBlank: true,
        anchor: '100%'
        
    });


   var dsUniEco = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('perGraClieTxtAseg',helpMap,'Aseguradora'),
        tooltip:getToolTipFromMap('perGraClieTxtAseg',helpMap,'Aseguradora de per&iacute;odo de gracia por cliente'), 
        hasHelpIcon:getHelpIconFromMap('perGraClieTxtAseg',helpMap),
		Ayuda: getHelpTextFromMap('perGraClieTxtAseg',helpMap),
		id:'perGraClieTxtAseg',
        name:'dsUniEco',
        allowBlank: true,
        anchor: '100%'
      
    });
    
  function test(){

             store = new Ext.data.Store({

    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_BUSCAR_PERIODOS_GRACIA_CLIENTE
                }),

                //proxy: proxyUrl,
                reader: new Ext.data.JsonReader({
            	root:'MPeriodoGraciaClienteList',
            	totalProperty: 'totalCount',
            	successProperty : '@success'
	        },[
	        {name: 'aseguradora',  type: 'string',  mapping:'aseguradora'},
	        {name: 'cdElemento',  type: 'string',  mapping:'cdElemento'},
	        {name: 'cdPerson',  type: 'string',  mapping:'cdPerson'},
	        {name: 'cdTramo',  type: 'string',  mapping:'cdTramo'},
	        {name: 'cliente',  type: 'string',  mapping:'cliente'},
	        {name: 'cveAseguradora',  type: 'string',  mapping:'cveAseguradora'},
	        {name: 'cveProducto',  type: 'string',  mapping:'cveProducto'},
	        {name: 'diasAntesDeCancelacion',  type: 'string',  mapping:'diasAntesDeCancelacion'},
	        {name: 'diasDeGracia',  type: 'string',  mapping:'diasDeGracia'},
	        {name: 'producto',  type: 'string',  mapping:'producto'},
	        {name: 'recibos',  type: 'string',  mapping:'recibos'}
	        
			])
        });

       return store;
 	}


    var grid2;



function createGrid(){
	grid2= new Ext.grid.GridPanel({
		id: 'grid2',
        el:'gridPeriodosGraciaCliente',
        store:test(),
        title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
		border:true,
		buttonAlign:'center',
		labelAlign:'right',
        cm: cm,
        loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
		buttons:[
        		{
				text:getLabelFromMap('perGraClieBtnSeekAgreg',helpMap,'Agregar'),
				tooltip: getToolTipFromMap('perGraClieBtnSeekAgreg',helpMap,'Agrega un per&iacute;odo de gracia por cliente'),
            	handler:function(){
                agregar();
                }
            	},{
				text:getLabelFromMap('perGraClieBtnDel',helpMap,'Eliminar'),
				tooltip: getToolTipFromMap('perGraClieBtnDel',helpMap,'Elimina un Per&iacute;odos de Gracia por Cliente'),
            	handler:function(){
                    if (getSelectedKey(grid2, "cdTramo") != "" && getSelectedKey(grid2, "cdElemento") != "" && getSelectedKey(grid2, "cveAgrupa") != "" && getSelectedKey(grid2, "cveProducto") != "") {
                        borrar(getSelectedRecord(grid2));
                    } else {
							Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                    }
                }
            	},{
				text:getLabelFromMap('perGraClieBtnExp',helpMap,'Exportar'),
				tooltip: getToolTipFromMap('perGraClieBtnExp',helpMap,'Exporta Per&iacute;odos de Gracia por Cliente'),
                handler:function(){
                		var url = _ACTION_EXPORT_PERIODOS_GRACIA_CLIENTE + '?dsElemen=' + dsElemen.getValue()+ '&dsRamo=' + dsRamo.getValue()+ '&dsUniEco=' + dsUniEco.getValue();
                	 	showExportDialog( url )
                	 }
            	}/*,{
				text:getLabelFromMap('perGraClieBtnBack',helpMap,'Regresar'),
				tooltip: getToolTipFromMap('perGraClieBtnBack',helpMap,'Regresa a la pantalla anterior')
            	}*/
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

    grid2.render()
}

    var incisosForm = new Ext.form.FormPanel({
    	id: 'incisosForm',
        el:'formBusqueda',
		title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('incisosForm',helpMap,'Per&iacute;odos de Gracia por Cliente')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,
        url:_ACTION_BUSCAR_PERIODOS_GRACIA_CLIENTE,
        width: 500,
        height:180,
        items: [{
        		layout:'form',
				border: false,
				items:[{
	        		bodyStyle:'background: white',
			        labelWidth: 100,
	                layout: 'form',
					frame:true,
			       	baseCls: '',
			       	buttonAlign: "center",
        		        items: [{
        		        	layout:'column',
		 				    border:false,
		 				    html:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
		 				    columnWidth: 1,
        		    		items:[{
						    	columnWidth:.6,
                				layout: 'form',
		                		border: false,
        		        		items:[
        		        				dsElemen,
        		        				dsRamo,
        		        				dsUniEco
        		        				
		       						]
								},{
								columnWidth:.4,
                				layout: 'form'
                				}]
                			}],
                			buttons:[{
        							text:getLabelFromMap('perGraClieBtnSeek',helpMap,'Buscar'),
        							tooltip: getToolTipFromMap('perGraClieBtnSeek',helpMap,'Busca un grupo de per&iacute;odos de gracia por cliente'),
        							handler: function() {
				               			if (incisosForm.form.isValid()) {
                                               if (grid2!=null) {
                                                reloadGrid();
                                               } else {
                                                createGrid();
                                               }
                						} else{
	                						Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'))
										}
									}
        							},{
        							text:getLabelFromMap('perGraClieBtnCanc',helpMap,'Cancelar'),
        							tooltip: getToolTipFromMap('perGraClieBtnCanc',helpMap,'Cancela la operaci&oacute;n de per&iacute;odos de gracia por cliente'),
        							handler: function() {
        								incisosForm.form.reset()
        							}
        						}]
        					}]
        				}]
				});

		incisosForm.render();
        createGrid();

        function toggleDetails(btn, pressed){
        var view = grid.getView();
        view.showPreview = pressed;
        view.refresh()
    	}
var store;


function borrar(record) {
        if(record.get('cdTramo') != "" && record.get('cdElemento') != "" && record.get('cveProducto') != "" && record.get('cveAseguradora') != "")
        {
        	Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
            {
                if (btn == "yes")
                {
                	var _params = {
				              cdTramo: record.get('cdTramo'),
				              cdElemento: record.get('cdElemento'),
				              cveProducto: record.get('cveProducto'),
				              cveAseguradora: record.get('cveAseguradora')
                	};
                	execConnection(_ACTION_BORRAR_PERIODOS_GRACIA_CLIENTE, _params, cbkBorrar)
               }
            })
        }else{
        		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'))
    	}
};

function cbkBorrar(_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid();})
	}
}
    
});
function reloadGrid(){
	var _params = {
    		dsElemen: Ext.getCmp('incisosForm').form.findField('dsElemen').getValue(),
    		dsRamo: Ext.getCmp('incisosForm').form.findField('dsRamo').getValue(),
    		dsUniEco: Ext.getCmp('incisosForm').form.findField('dsUniEco').getValue()
	};
	reloadComponentStore(Ext.getCmp('grid2'), _params, cbkReload)
}
function cbkReload(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0])
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0])
	}
}
