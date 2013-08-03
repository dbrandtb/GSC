function consultarCliente(){
		

var dsAseguradora = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtFldDsEncuesta',helpMap,'Aseguradora'),
        tooltip:getToolTipFromMap('txtFldDsEncuesta',helpMap,'Nombre de la Aseguradora'),
        id: 'dsAseguradoraId', 
        name: 'dsAseguradora',
        allowBlank: true,
        anchor: '90%'
    });

var dsProducto = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtFldDsEncuesta',helpMap,'Producto'),
        tooltip:getToolTipFromMap('txtFldDsEncuesta',helpMap,'Nombre del Producto'),
        id: 'dsProductoId', 
        name: 'dsProducto',
        allowBlank: true,
        anchor: '90%'
    });
    
  
    
// Definicion de las columnas de la grilla
var cmComun = new Ext.grid.ColumnModel([
        
        {
        header:getLabelFromMap('cmPoliza',helpMap,'P&oacute;liza'),
        tooltip:getToolTipFromMap('cmPoliza',helpMap,'Columna Poliza'),
        dataIndex: 'nmPoliza',
        sortable: true,
        width: 60
        },
        {
        header:getLabelFromMap('cmInciso',helpMap,'Inciso'),
        tooltip:getToolTipFromMap('cmInciso',helpMap,'Columna Inciso'),
        dataIndex: 'inciso',
        sortable: true,
        width: 60
        },
        {
        header:getLabelFromMap('cmSubinciso',helpMap,'Subinciso'),
        tooltip:getToolTipFromMap('cmSubinciso',helpMap,'Columna Subinciso'),
        dataIndex: 'subinciso',
        sortable: true,
        width: 70
        },
        {
        header:getLabelFromMap('cmNombre',helpMap,'Nombre'),
        tooltip:getToolTipFromMap('cmNombre',helpMap,'Columna Nombre'),
        dataIndex: 'nombre',
        sortable: true,
        width: 80
        },
        {
        header:getLabelFromMap('cmRfc',helpMap,'RFC'),
        tooltip:getToolTipFromMap('cmRfc',helpMap,'Columna RFC'),
        dataIndex: 'rfc',
        sortable: true,
        width: 60
        },
        {
        header:getLabelFromMap('cmViDesde',helpMap,'Vigencia Desde'),
        tooltip:getToolTipFromMap('cmViDesde',helpMap,'Columna Vigencia Desde'),
        dataIndex: 'viDesde',
        sortable: true,
        width: 93
        },
        {
        header:getLabelFromMap('cmViHasta',helpMap,'Vigencia Hasta'),
        tooltip:getToolTipFromMap('cmViHasta',helpMap,'Columna Vigencia Hasta'),
        dataIndex: 'viHasta',
        sortable: true,
        width: 90
        },
        {
        header:getLabelFromMap('cmEstado',helpMap,'Estado'),
        tooltip:getToolTipFromMap('cmEstado',helpMap,'Columna Estado'),
        dataIndex: 'estado',
        sortable: true,
        width: 70
        },
         {
        header:getLabelFromMap('cmConductoCobro',helpMap,'Conducto de Cobro'),
        tooltip:getToolTipFromMap('cmConductoCobro',helpMap,'Columna Conducto de Cobro'),
        dataIndex: 'conductoCobro',
        sortable: true,
        width: 115
        },
         {
        header:getLabelFromMap('cmPrimaTotal',helpMap,'Prima Total'),
        tooltip:getToolTipFromMap('cmPrimaTotal',helpMap,'Columna Prima Total'),
        dataIndex: 'primaTotal',
        sortable: true,
        width: 100
        },
        {
        dataIndex: 'cdFormatoDoc',
        hidden :true
        }
])
var gridComun;

function createGridFrmDoc(){
    if (gridComun != null && gridComun != undefined) return;
       gridComun= new Ext.grid.EditorGridPanel({
            id: 'grid2',
            //el:'gridElementos',
            //title: '<span style="height:10">Configurar Preguntas</span>',
            store:storeGrillaPreguntasEncuesta,
            reader:jsonGrillaPreguntasEncuesta,
            border:true,
            cm: cmComun,
            clicksToEdit:1,
            successProperty: 'success',
            buttonAlign: "center",
            buttons:[
                  {
                  text:getLabelFromMap('gridFrmDocButtonAceptar',helpMap,'Aceptar'),
                  tooltip:getToolTipFromMap('gridFrmDocButtonAceptar',helpMap,'Aceptar')
                 // handler:function(){ }
                  },
                 
                  {
                  text:getLabelFromMap('gridFrmDocButtonDetalles',helpMap,'Detalles'),
                  tooltip:getToolTipFromMap('gridFrmDocButtonDetalles',helpMap,'Detalles')
                  /*handler:function(){
                        if (getSelectedKey(gridComun, "cdFormatoDoc") != "") {
                            borrarFrmDoc(getSelectedKey(gridComun, "cdFormatoDoc"));
                        }
                        else{
                            Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
                        }
                    }*/
                  },
                  {
                  text:getLabelFromMap('gridFrmDocButtonRegresar',helpMap,'Regresar'),
                  tooltip:getToolTipFromMap('gridFrmDocButtonRegresar',helpMap,'Regresar a la pantalla anterior'),
                 handler:function(){ _window.close();}                     
                                              
                  }
                  ],
            width:790,
            frame:true,
            height:250,
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
            stripeRows: true,
            collapsible: true         
        });
   //gridComun.render();
}
    
createGridFrmDoc();

var incisosForm = new Ext.FormPanel({
		id: 'incisosForm',
        //el:'formDocumentos',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('23',helpMap,'Consulta de P&oacute;lizas')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,   
        url: _ACTION_BUSCAR_CLIENTES,
        width: 790,
        height:62,
        items: [{
        		layout:'form',
				border: false,
				items:[{
                		bodyStyle:'background: white',
        		        
                        layout: 'form',
        				frame:true,
        		       	baseCls: '',
        		       	buttonAlign: "center",
                		items: [{
            		        	layout:'column',
            		        	labelWidth: 110,
        	 				    border:false,
        	 				    columnWidth: 1,
            		    		items:[{
        					    	columnWidth:.5,
                    				layout: 'form',
        	                		border: false,
            		        		items:[       		        				
            		        				 dsAseguradora
            		        				 
                                           ]
        							},
        							{
        							columnWidth:.5,
        							labelWidth: 90,
                    				layout: 'form',
                    				border: false,
            		        		items:[ dsProducto ]
                    				}]
                    			}]
            			}]
        		}]
})   

_window = new Ext.Window ({
	//title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('96',helpMap,'Formato de Documentos')+'</span>',
	width: 804,
    modal: true,
	height: true,
	items: [
            incisosForm,
            gridComun
           ]
       
});
_window.show();	


function borrarFrmDoc (key) {
	if(key != "")
	{
		Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
		{
	        if (btn == "yes")
	        {
				var _params = {
						cdFormato: key
				};
				execConnection(_ACTION_BORRAR_FORMATOS_DOCUMENTOS, _params, cbkConnection);
           }
		}
		)
	}else{
			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
	}
}

function cbkConnection(_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid();});
	}
}

function reloadGridFrmDoc(){
		var _params = {
        		dsNomFormato: Ext.getCmp('incisosForm').form.findField('desNomFormato').getValue()
		};
		reloadComponentStore(Ext.getCmp('grid2'), _params, cbkReloadFrmDoc);
}

function cbkReloadFrmDoc(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}
}

};