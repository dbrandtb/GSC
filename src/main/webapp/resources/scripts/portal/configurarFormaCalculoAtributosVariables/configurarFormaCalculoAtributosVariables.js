var helpMap = new Map();

Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";




var desElemen = new Ext.form.TextField({
    fieldLabel: getLabelFromMap('desElemenId',helpMap,'Cliente'),
    tooltip:getToolTipFromMap('desElemenId',helpMap,'Cliente para forma de c&aacute;lculo'), 
    hasHelpIcon:getHelpIconFromMap('desElemenId',helpMap),								 
    Ayuda: getHelpTextFromMap('desElemenId',helpMap),
    name: 'desElemen', 
    width: 135,
    id: 'desElemenId'
});

var desTipSit = new Ext.form.TextField({
    fieldLabel: getLabelFromMap('desTipSitId',helpMap,'Tipo de situaci&oacute;n'),
    tooltip:getToolTipFromMap('desTipSitId',helpMap,'Tipo de situaci&oacute;n para forma de c&aacute;lculo'),
     hasHelpIcon:getHelpIconFromMap('desTipSitId',helpMap),								 
    Ayuda: getHelpTextFromMap('desTipSitId',helpMap),
    name: 'desTipSit',
    width: 135,
    id: 'desTipSitId'
});

var desUnieco = new Ext.form.TextField({
    fieldLabel: getLabelFromMap('desUniecoId',helpMap,'Aseguradora'),
    tooltip:getToolTipFromMap('desUniecoId',helpMap,'Aseguradora para forma de c&aacute;lculo'), 
     hasHelpIcon:getHelpIconFromMap('desUniecoId',helpMap),								 
    Ayuda: getHelpTextFromMap('desUniecoId',helpMap),
    name: 'desUnieco',
    width: 135,
    id: 'desUniecoId'
});

var desRamo = new Ext.form.TextField({
    fieldLabel: getLabelFromMap('desRamoId',helpMap,'Producto'),
    tooltip:getToolTipFromMap('desRamoId',helpMap,'Producto para forma de c&aacute;lculo'), 
     hasHelpIcon:getHelpIconFromMap('desRamoId',helpMap),								 
    Ayuda: getHelpTextFromMap('desRamoId',helpMap),
    name: 'desRamo',
    width: 135,
    id: 'desRamoId'
});

var incisosForm = new Ext.FormPanel({
		id: 'incisosForm',
        el:'formBusqueda',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('120',helpMap,'Forma de C&aacute;lculo de Atributos Variables')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,
        autoHeight:true,   
        url: _ACTION_BUSCAR_CONFIGURAR_FORMA_CALCULO_ATRIBUTOS,
        width: 500,
        height: 200,
        items: [
        		{
        		layout:'form',
				border: false,
				items:[
				{
        		bodyStyle:'background: white',
		        labelWidth: 130,
                layout: 'form',
				frame:true,
		       	baseCls: '',
		       	buttonAlign: "center",
        		items: [
        		        {
        		        	layout:'column',
		 				    border:false,
		 				    html:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
		 				    columnWidth: 1,
        		    		items:[
        		    			{
						    	columnWidth:.6,
                				layout: 'form',
		                		border: false,
        		        		items:[       		        				
        		        				 desElemen,
        		        				 desTipSit,
        		        				 desUnieco,
        		        				 desRamo      		        				
                                       ]
								},
								{
								columnWidth:.4,
                				layout: 'form'
                				}
                				]
                			}
                		 ],
				buttonAlign: 'center',
			    buttons:
			       	[
			      	  {
					   text:getLabelFromMap('confForCalAtrVarBtnSeek',helpMap,'Buscar'),
					   tooltip: getToolTipFromMap('confForCalAtrVarBtnSeek',helpMap,'Busca forma de c&aacute;lculos de atributos'),
	                   handler: function() {
	                         if (incisosForm.form.isValid()) {
	                            if (grid2!=null) {
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
					      text:getLabelFromMap('confForCalAtrVarBtnCanc',helpMap,'Cancelar'),
					      tooltip: getToolTipFromMap('confForCalAtrVarBtnCanc',helpMap,'Cancela operaci&oacute;n de forma de c&aacute;lculos de atributos'),
		                  handler: function() {
		                                     incisosForm.form.reset();
		                           }
		               }
		            ]                			
      				}
      				]
        	}
        	]	            
	}
);   


// Definicion de las columnas de la grilla
var cm = new Ext.grid.ColumnModel([
        {
        header: getLabelFromMap('confForCalAtrVarCmCli',helpMap,'Cliente'),
        tooltip: getToolTipFromMap('confForCalAtrVarCmCli',helpMap,'Clientes de forma de c&aacute;lculos'),
        dataIndex: 'dsElemen',
        sortable: true,
        width: 120
        },
        {
        header: getLabelFromMap('confForCalAtrVarCmTS',helpMap,'Tipo de situaci&oacute;n'),
        tooltip: getToolTipFromMap('confForCalAtrVarCmTS',helpMap,'Tipo de situaci&oacute;n de forma de c&aacute;lculos'),
        dataIndex: 'dsTipSit',
        sortable: true,
        width: 120
        },
        {
        header: getLabelFromMap('confForCalAtrVarCmAseg',helpMap,'Aseguradora'),
        tooltip: getToolTipFromMap('confForCalAtrVarCmAseg',helpMap,'Aseguradora de forma de c&aacute;lculos'),
        dataIndex: 'dsUnieco',
        sortable: true,
        width: 120
        },
        {
        header: getLabelFromMap('confForCalAtrVarCmProd',helpMap,'Producto'),
        tooltip: getToolTipFromMap('confForCalAtrVarCmProd',helpMap,'Producto de forma de c&aacute;lculos'),
        sortable: true,
        dataIndex: 'dsRamo',
        width: 120
        },
        {
        header: getLabelFromMap('confForCalAtrVarCmAtrib',helpMap,'Atributo'),
        tooltip: getToolTipFromMap('confForCalAtrVarCmAtrib',helpMap,'Atributo de forma de c&aacute;lculos'),
        sortable: true,
        dataIndex: 'atributo',
        width: 120
        },
        {
        header: getLabelFromMap('confForCalAtrVarCmCal',helpMap,'C&aacute;lculo'),
        tooltip: getToolTipFromMap('confForCalAtrVarCmCal',helpMap,'C&aacute;lculo de forma de c&aacute;lculos'),
        sortable: true,
        dataIndex: 'calculo',
        width: 120
        },
        {
        dataIndex: 'cdIden',
        hidden :true
        }
]);

var lasFilas=new Ext.data.Record.create([
  {name: 'cdIden',  mapping:'cdIden',  type: 'string'},
  {name: 'cdElemento',  mapping:'cdElemento',  type: 'string'},
  {name: 'dsElemen',  mapping:'dsElemen',  type: 'string'},
  {name: 'cdTipSit',  mapping:'cdTipSit',  type: 'string'},      
  {name: 'dsTipSit',  mapping:'dsTipSit',  type: 'string'},
  {name: 'cdUnieco',  mapping:'cdUnieco',  type: 'string'},
  {name: 'dsUnieco',  mapping:'dsUnieco',  type: 'string'},
  {name: 'cdRamo',  mapping:'cdRamo',  type: 'string'},
  {name: 'dsRamo',  mapping:'dsRamo',  type: 'string'},
  {name: 'cdTabla',  mapping:'cdTabla',  type: 'string'},
  {name: 'swFormaCalculo',  mapping:'swFormaCalculo',  type: 'string'},
  {name: 'atributo',  mapping:'atributo',  type: 'string'},
  {name: 'calculo',  mapping:'calculo',  type: 'string'}               
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
             url: _ACTION_BUSCAR_CONFIGURAR_FORMA_CALCULO_ATRIBUTOS
						//waitMsg: getLabelFromMap('400017',helpMap,'Espere por favor...'),
		                }),
             reader:jsonGrilla
             });
             return store;
}

var grid2;

function createGrid(){
       grid2= new Ext.grid.GridPanel({
       		id: 'grid2',
            el:'gridElementos',
            store:storeGrilla(),
            reader:jsonGrilla,
            border:true,
            title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
            width: 500,
            cm: cm,
            buttonAlign:'center',
            loadMask: {msg: getLabelFromMap('400058',helpMap,'Cargando datos ...'), disabled: false},
            clicksToEdit:1,
	        successProperty: 'success',
            buttons:[
                  {
                  text:getLabelFromMap('confForCalAtrVarBtnAdd',helpMap,'Agregar'),
                  tooltip: getToolTipFromMap('confForCalAtrVarBtnAdd',helpMap,'Crea Nuevos Atributos de Configuraci&oacute;n'),
                  handler:function(){configurar()}
                  },
				  {
                  text:getLabelFromMap('confForCalAtrVarBtnEd',helpMap,'Editar'),
                  tooltip: getToolTipFromMap('confForCalAtrVarBtnEd',helpMap,'Edita Atributos de Configuraci&oacute;n'),
                  handler:function(){
						if (getSelectedRecord(grid2) != null) {
                        		editar(getSelectedRecord(grid2));
                        } else {
                        		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'))
                                }
                   }
                  },
                  {
                  text:getLabelFromMap('confForCalAtrVarBtnDel',helpMap,'Eliminar'),
                  tooltip: getToolTipFromMap('confForCalAtrVarBtnDel',helpMap,'Elimina Atributos de Configuraci&oacute;n'),
                  handler:function(){
						if (getSelectedKey(grid2, "cdIden") != "") {
                        		borrar(getSelectedKey(grid2, "cdIden"));
               			} else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'))
                        }
                	}
                  },                  
                  {
                  text:getLabelFromMap('confForCalAtrVarBtnCopy',helpMap,'Copiar'),
                  tooltip: getToolTipFromMap('confForCalAtrVarBtnCopy',helpMap,'Copia Atributos de Configuraci&oacute;n'),
                  handler:function(){
						if (getSelectedKey(grid2, "cdIden") != "") {
                        		copiar(getSelectedKey(grid2, "cdIden"));
                        } else {
                        		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'))
                                }
                   }
                  },
                  {
                  text:getLabelFromMap('confForCalAtrVarBtnExp',helpMap,'Exportar'),
                  tooltip: getToolTipFromMap('confForCalAtrVarBtnExp',helpMap,'Exporta Atributos de Configuraci&oacute;n'),
                  handler:function(){
                        var url = _ACTION_EXPORTAR_CONFIGURAR_FORMA_CALCULO_ATRIBUTOS + '?dsUnieco=' + desUnieco.getValue()+ '&dsRamo=' + desRamo.getValue()+ '&dsElemen=' + desElemen.getValue()+ '&dsTipSit=' + desTipSit.getValue();
                	 	showExportDialog( url )
                    }
                  }/*,
                  {
                  text:getLabelFromMap('confForCalAtrVarBtnBack',helpMap,'Regresar'),
                  tooltip: getToolTipFromMap('confForCalAtrVarBtnBack',helpMap,'Volver a Pantalla Anterior')
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
					displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })          
        });
   grid2.render()
}

//Muestra los componentes en pantalla
incisosForm.render();
createGrid();


function borrar(key) {
		if(key != "")
		{
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
			{
		        if (btn == "yes")
		        {
         			var _params = {
         						cdIden: key
         			};
         			execConnection(_ACTION_BORRAR_CONFIGURAR_FORMA_CALCULO_ATRIBUTOS, _params, cbkConnection);
               }
			})
		}else{
			  Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'))
		}
};

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
       		dsUnieco: Ext.getCmp('incisosForm').form.findField('desUniecoId').getValue(),
       		dsRamo: Ext.getCmp('incisosForm').form.findField('desRamoId').getValue(),
       		dsElemen: Ext.getCmp('incisosForm').form.findField('desElemenId').getValue(),
       		dsTipSit: Ext.getCmp('incisosForm').form.findField('desTipSitId').getValue()
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
