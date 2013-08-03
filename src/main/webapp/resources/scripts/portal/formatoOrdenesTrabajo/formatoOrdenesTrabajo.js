Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";


var desFormatoOrden = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('desFormatoOrden',helpMap,'Nombre'),
        tooltip:getToolTipFromMap('desFormatoOrden',helpMap,'Nombre del formato de &oacute;rdenes de trabajo'),
        hasHelpIcon:getHelpIconFromMap('desFormatoOrden',helpMap),								 
        Ayuda: getHelpTextFromMap('desFormatoOrden',helpMap,''),

        id: 'desFormatoOrden', 
        name: 'desFormatoOrden',
        allowBlank: true,
        width: 300,
        anchor: '100%'
    });

       
var incisosForm = new Ext.FormPanel({
		id: 'incisosForm',
        el:'formBusqueda',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('incisosForm',helpMap,'Formato de Ordenes de Trabajo')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,   
        url: _ACTION_BUSCAR_FORMATO_ORDENES_TRABAJO,
        width: 505,
        height:150,
        items: [{
        		layout:'form',
				border: false,
				items:[{
        		bodyStyle:'background: white',
		        labelWidth: 100,
		        labelAlign: 'rigth',
                layout: 'form',
                title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
				frame:true,
		       	baseCls: '',
		       	buttonAlign: "center",
        		        items: [{
        		        	layout:'column',
		 				    border:false,
		 				    columnWidth: 1,
        		    		items:[{
						    	columnWidth:.6,
                				layout: 'form',
		                		border: false,
        		        		items:[       		        				
        		        				 desFormatoOrden       		        				
                                       ]
								},
								{
								columnWidth:.4,
                				layout: 'form'
                				}
                				]
                				
                			}],
                			buttons:[{
        							text:getLabelFromMap('fotBtnFind',helpMap,'Buscar'),
        							tooltip:getToolTipFromMap('fotBtnFind',helpMap,'Busca formato de &oacute;rdenes de trabajo'),
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
        							},{
        							text:getLabelFromMap('fotBtnCancel',helpMap,'Cancelar'),
        							tooltip:getToolTipFromMap('fotBtnCancel',helpMap,'Cancela Busqueda formato de &oacute;rdenes de trabajo'),                             
        							handler: function() {
        								incisosForm.form.reset();
        							}
        						}]
        					}]
        				}]	            
        
});   

// Definicion de las columnas de la grilla
var cm = new Ext.grid.ColumnModel([
        {
        dataIndex: 'cdFormatoOrden',
        hidden :true
        },
        {
        header:getLabelFromMap('fotCmNomb',helpMap,'Nombre'),
        tooltip:getToolTipFromMap('fotCmNomb',helpMap,'Nombre del formato &oacute;rden de trabajo'),
        hasHelpIcon:getHelpIconFromMap('fotCmNomb',helpMap),								 
        Ayuda: getHelpTextFromMap('fotCmNomb',helpMap,''),
        
        dataIndex: 'dsFormatoOrden',
        sortable: true,
        width: 480
        }
]);

var lasFilas=new Ext.data.Record.create([
  {name: 'cdFormatoOrden',  mapping:'cdFormatoOrden',  type: 'string'},
  {name: 'dsFormatoOrden',  mapping:'dsFormatoOrden',  type: 'string'}           
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
             url: _ACTION_BUSCAR_FORMATO_ORDENES_TRABAJO,
             			waitTitle: getLabelFromMap('400017',helpMap,'Espere por favor...'),
						waitMsg: getLabelFromMap('400070',helpMap,'Cargando Datos')
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
            title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
            store:storeGrilla(),
            reader:jsonGrilla,
            loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
            border:true,
            cm: cm,
            clicksToEdit:1,
	        successProperty: 'success',
            buttons:[
                  {
                  text:getLabelFromMap('fotBtnAdd',helpMap,'Agregar'),
       			  tooltip:getToolTipFromMap('fotBtnAdd',helpMap,'Agrega un nuevo formato de &oacute;rden de trabajo'),
                  handler:function(){ editar()}
                  },
                  {
                  text:getLabelFromMap('fotBtnEdit',helpMap,'Editar'),
       			  tooltip:getToolTipFromMap('fotBtnEdit',helpMap,'Edita un formato de &oacute;rden de trabajo seleccionado'),
                  handler:function(){
						if (getSelectedKey(grid2, "cdFormatoOrden") != "") {
                        		editar(getSelectedKey(grid2, "cdFormatoOrden"));
                        } else {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                                }
                   }
                  },
                  {

                  text:getLabelFromMap('fotBtnDel',helpMap,'Eliminar'),
       			  tooltip:getToolTipFromMap('fotBtnDel',helpMap,'Elimina un formato de &oacute;rden de trabajo seleccionado'),
                  handler:function(){
						if (getSelectedKey(grid2, "cdFormatoOrden") != "") {
                        		borrar(getSelectedKey(grid2, "cdFormatoOrden"));
                    			}
                    			else
                    			{
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                                }
                	}
                  },
                  {
                  text:getLabelFromMap('fotBtnCopy',helpMap,'Copiar'),
       			  tooltip:getToolTipFromMap('fotBtnCopy',helpMap,'Copia un formato de &oacute;rden de trabajo seleccionado'),
                  handler:function(){
                          if (getSelectedKey(grid2, "cdFormatoOrden") != "") {
                        		copiar(getSelectedKey(grid2, "cdFormatoOrden"));
                    			}
                    			else
                    			{
                                 Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                                }
                	}               	
                  },
                  {
                  text:getLabelFromMap('fotBtnConfig',helpMap,'Configurar'),
       			  tooltip:getToolTipFromMap('fotBtnConfig',helpMap,'Configura un formato de &oacute;rden de trabajo seleccionado'),
                  handler:function(){
                           if (getSelectedKey(grid2, "cdFormatoOrden") != "") {
                          	       configurar(getSelectedKey(grid2, "cdFormatoOrden"),getSelectedKey(grid2, "dsFormatoOrden"))
                	 	   }
                	 	   else{
                	 	         Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));	 	                 	 	         
                	 	     }
                       }
                  }/*,
                  {
                  text:getLabelFromMap('fotBtnBck',helpMap,'Regresar'),
       			  tooltip:getToolTipFromMap('fotBtnBck',helpMap,'Regresa a la pantalla anterior')
                  
                  }*/
                  ],
            width:505,
            frame:true,
            height:320,
            buttonAlign:'center',
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			collapsible: true,
			bbar: new Ext.PagingToolbar({
					pageSize:itemsPerPage,
					store: store,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })          
        });
   grid2.render()
}

incisosForm.render();
createGrid();


	function borrar (key) {
		if(key != "")
		{
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
			{
		        if (btn == "yes")
		        {
					var _params = {
							cdFormatoOrden: key
					};
					execConnection(_ACTION_BORRAR_FORMATO_ORDENES_TRABAJO, _params, cbkConnection);
               }
			}
			)
		}else{
				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
		}
	};


function copiar (key)
{	        			
	if(key != "")
	{
		Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400032', helpMap,'Se copiar&aacute; el registro seleccionado'),function(btn)
		{
          if (btn == "yes")
          {
			var _params = {
					cdFormatoOrden: key
			};
			execConnection(_ACTION_COPIAR_FORMATO_ORDENES_TRABAJO, _params, cbkConnection);
          }
		})
	}else{
			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
	}
};
function cbkConnection(_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid();});
	}
}
function configurar (key,desc)
{
 window.location=_ACTION_IR_CONFIGURAR_ORDENES_TRABAJO+"?cdFormatoOrden="+key+"&descripcionEscapedJavaScript="+desc;
	
};    
         
});

function reloadGrid(){
		var _params = {
        		dsFormatoOrden: Ext.getCmp('incisosForm').form.findField('desFormatoOrden').getValue()
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