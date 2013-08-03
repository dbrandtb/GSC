
Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";


var dsarchivo = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('desArchivoId',helpMap,'Tipo de Archivo'),
        tooltip:getToolTipFromMap('desArchivoId',helpMap,'Tipo de Archivo'),
        hasHelpIcon:getHelpIconFromMap('desArchivoId',helpMap),								 
        Ayuda: getHelpTextFromMap('desArchivoId',helpMap,''),	
        id: 'desArchivoId', 
        name: 'dsarchivo',
        allowBlank: true,
       // anchor: '100%'
         width: 200
    });
    
    var cdtipoar = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('numeroId',helpMap,'N&uacute;mero'),
        tooltip:getToolTipFromMap('numeroId',helpMap,'N&uacute;mero'),
        hasHelpIcon:getHelpIconFromMap('numeroId',helpMap),								 
        Ayuda: getHelpTextFromMap('numeroId',helpMap,''),	
        id: 'numeroId', 
        name: 'cdtipoar',
        allowBlank: true,
        //anchor: '100%'
        width: 200
    });
      
var incisosFormArchivos = new Ext.FormPanel({
        id: 'incisosFormArchivos',
        el:'formBusqueda',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('23',helpMap,'Datos de Archivos')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,   
        url: _ACTION_BUSCAR_DATOS_ARCHIVO,
        width: 520,
        height:180,
        items: [{
                layout:'form',
                border: false,
                items:[{
                labelWidth: 100,
                layout: 'form',
                title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
                frame:true,
                baseCls: '',
                buttonAlign: "center",
                        items: [
                            {
                                layout:'column',
                                border:false,
                                columnWidth: 1,
                                items:[
                                    {
                                        columnWidth:.1,
                                        layout: 'form'
                                    },
                                    {
                                        columnWidth:1,
                                        layout: 'form',
                                        labelWidth: 150,
                                        border: false,
                                        items:[                                     
                                                dsarchivo                                    
                                            ]
                                    },
                                    
                                    {
                                        columnWidth:1,
                                        layout: 'form',
                                        labelWidth: 150,
                                        border: false,
                                        items:[                                     
                                                cdtipoar                                    
                                            ]
                                    },
                                    {
                                        columnWidth:.2,                                     
                                        layout: 'form'
                                    }
                                ]
                                
                            }],
                            buttons:[{
                                    text:getLabelFromMap('frmtEsttCsButtonBuscar',helpMap,'Buscar'),
                                    tooltip:getToolTipFromMap('frmtEsttCsButtonBuscar',helpMap,'Busca Archivo'),
                                    handler: function() {
                                        if (incisosFormArchivos.form.isValid()) {
                                               if (gridArchivo!=null) {
                                                reloadGrid();
                                               } else {
                                                createGrid();
                                               }
                                        } else{
                                            Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                                        }
                                    }
                                    },
                                    
                                    {
                                    text:getLabelFromMap('frmtEsttCsButtonCancelar',helpMap,'Cancelar'),
                                    tooltip:getToolTipFromMap('frmtEsttCsButtonCancelar',helpMap,'Cancela B&uacute;squeda de Archivo'),                             
                                    handler: function() {
                                        incisosFormArchivos.form.reset();
                                    }
                                }]
                            }]
                        }]              
        
});   

// Definicion de las columnas de la grilla
var grilla = new Ext.grid.ColumnModel([
        /*{
        dataIndex: 'codStatus',
        hidden :true
        },*/
        {
        header:getLabelFromMap('cmNumDstoArch',helpMap,'N&uacute;mero'),
        tooltip:getToolTipFromMap('cmNumDstoArch',helpMap,'N&uacute;mero'),
        hasHelpIcon:getHelpIconFromMap('cmNumDstoArch',helpMap),								 
        Ayuda: getHelpTextFromMap('cmNumDstoArch',helpMap,''),	
        dataIndex: 'cdtipoar',
        sortable: true,
        width: 140
        },
        
        {
        header:getLabelFromMap('cmAtributo',helpMap,'Atributo'),
        tooltip:getToolTipFromMap('cmAtributo',helpMap,'Nombre del atributo'),
        hasHelpIcon:getHelpIconFromMap('cmAtributo',helpMap),								 
        Ayuda: getHelpTextFromMap('cmAtributo',helpMap,''),	
        dataIndex: 'dsatribu',
        sortable: true,
        width: 140
        },
        
        {
        header:getLabelFromMap('cmValor',helpMap,'Valor'),
        tooltip:getToolTipFromMap('cmValor',helpMap,'Valor'),
        hasHelpIcon:getHelpIconFromMap('cmValor',helpMap),								 
        Ayuda: getHelpTextFromMap('cmValor',helpMap,''),	
        dataIndex: 'otvalor',
        sortable: true,
        width: 120
        },
        
        {
        header:getLabelFromMap('cmFecha',helpMap,'Fecha'),
        tooltip:getToolTipFromMap('cmFecha',helpMap,'Fecha'),
        hasHelpIcon:getHelpIconFromMap('cmFecha',helpMap),								 
        Ayuda: getHelpTextFromMap('cmFecha',helpMap,''),	
        dataIndex: 'feingreso',
        sortable: true,
        width: 120
        }
]);

var gridArchivo;

function createGrid(){
       gridArchivo= new Ext.grid.GridPanel({
            id: 'grid2',
            el:'gridElementos',
            store:storeGrillaArchivos,
            title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
            reader:jsonGrillaArchivos,
            border:true,
            cm: grilla,
            loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
            clicksToEdit:1,
            successProperty: 'success',
            buttonAlign: "center",
            buttons:[
                  
                  {
                  text:getLabelFromMap('gridEsttCsButtonExportar',helpMap,'Exportar'),
                  tooltip:getToolTipFromMap('gridEsttCsButtonExportar',helpMap,'Exportar el Archivo'),
                  handler:function(){
                        var url = _ACTION_EXPORTAR_DATOS_ARCHIVOS + '?dsarchivo=' + dsarchivo.getValue()+ '&cdtipoar=' + cdtipoar.getValue();
                        showExportDialog( url );
                    }                 
                  },
                  {
                  text:getLabelFromMap('gridEsttCsBottonRegresar',helpMap,'Regresar'),
                  tooltip: getToolTipFromMap('gridEsttCsBottonRegresar',helpMap,'Volver a la Pantalla Anterior'),
                  handler:function(){}
                  }
                             
                  ],
            width:520,
            frame:true,
            height:320,
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
            stripeRows: true,
            collapsible: true,
            bbar: new Ext.PagingToolbar({
                    pageSize:20,
                    store: storeGrillaArchivos,
                    displayInfo: true,
                    displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
                    emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
                })          
        });
   gridArchivo.render()
}

incisosFormArchivos.render();
createGrid();

function cbkConnection (_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid()});
	}
}      
});


function reloadGrid(){
	var _params = {
       		dsarchivo: Ext.getCmp('incisosFormArchivos').form.findField('dsarchivo').getValue(),
       		cdtipoar: Ext.getCmp('incisosFormArchivos').form.findField('cdtipoar').getValue()
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