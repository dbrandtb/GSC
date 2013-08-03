Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

//VALORES DE INGRESO DE LA BUSQUEDA
var dsNivel = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtDsNivel',helpMap,'Nivel'),
        tooltip:getToolTipFromMap('txtDsNivel',helpMap,'Nivel'), 
        id: 'dsNivelId', 
        name: 'dsNivel',
        allowBlank: true,
        anchor: '100%'
        //width:150
    });
 
var dsRol = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtDsRol',helpMap,'Rol'),
        tooltip:getToolTipFromMap('txtDsRol',helpMap,'Rol'), 
        id: 'dsRolId', 
        name: 'dsRol',
        allowBlank: true,
        anchor: '100%'
        //width:150
    });
  
var dsUsuario = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtDsUsuario',helpMap,'Usuario'),
        tooltip:getToolTipFromMap('txtDsUsuario',helpMap,'Usuario'), 
        id: 'dsUsuarioId', 
        name: 'dsUsuario',
        allowBlank: true,
        anchor: '100%'
        //width:150
    });
    
    
    
var dsFuncionalidad = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtDsFuncionalidad',helpMap,'Funcionalidad'),
        tooltip:getToolTipFromMap('txtDsFuncionalidad',helpMap,'Funcionalidad'), 
        id: 'dsFuncionalidadId', 
        name: 'dsFuncionalidad',
        allowBlank: true,
        anchor: '100%'
        //width:150
    });
  
    

 

var incisosFormFuncionalidad = new Ext.FormPanel({
		id: 'incisosFormFuncionalidad',
        el:'formBusqueda',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('24',helpMap,'Consulta de Funcionalidades')+'</span>',
        iconCls:'logo',
        store: storeGrillaFuncionalidadesPrivilegios,
        reader: jsonGrillaFuncionalidadesPrivilegios,
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,   
        url: _ACTION_BUSCAR_FUNCIONALIDADES_PRIVILEGIOS,
        width: 500,
        height:220,
        items: [{
        		layout:'form',
				border: false,
 				items:[
                {
        		bodyStyle:'background: white',
		        labelWidth: 100,
                layout: 'form',
                title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br/>',
				frame:true,
		       	baseCls: '',
		       	buttonAlign: "center",
        		        items: [{
        		        	layout:'column',
		 				    border:false,
		 				    columnWidth: 1,
        		    		items:[{
						    	columnWidth:.5,
                				layout: 'form',
		                		border: false,
        		        		items:[       		        				
        		        				 dsNivel,
        		        				 dsRol,
        		        				 dsUsuario,
        		        				 dsFuncionalidad
                                       ]
								},
								{
								columnWidth:.5,
                				layout: 'form'
                				},{
								columnWidth:.2,
                				layout: 'form'
                				}]
                			}],
                			buttons:[{
        							text:getLabelFromMap('ntfcnButtonBuscar',helpMap,'Buscar'),
        							tooltip: getToolTipFromMap('ntfcnButtonBuscar',helpMap,'Busca un Grupo de Polizas a Cancelar'),
        							handler: function() {
        							//alert(1);
				               			if (incisosFormFuncionalidad.form.isValid()) {
                                               if (grid2!=null) {
                                                reloadGrid();
                                               } else {
                                                createGrid();
                                               }
                						} else{
                							Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
										}
									}
        							},{
        							text:getLabelFromMap('ntfcnButtonCancelar',helpMap,'Cancelar'),
        							tooltip: getToolTipFromMap('ntfcnButtonCancelar',helpMap,'Cancelar busqueda de Polizas'),                              
        							handler: function() {
        								incisosFormFuncionalidad.form.reset();
        							}
        						}]
        					}]
        				}]	            
        
});   


 var modEstado = new Ext.grid.CheckColumn({
      id:'modEstadoId',
      header: getLabelFromMap('modEstadoId',helpMap,'Estado'),
      tooltip: getToolTipFromMap('modEstadoId', helpMap,'Estado'),
      dataIndex: 'swEstado',
      align: 'center',
      sortable: true,
      width: 80
    });

// Definicion de las columnas de la grilla
var cm = new Ext.grid.ColumnModel([
        {
        
        header: getLabelFromMap('cmDsNivel',helpMap,'Nivel'),
        tooltip: getToolTipFromMap('cmDsNivel',helpMap,'Columna Nivel'),
        dataIndex: 'dsElemen',
        width: 100,
        sortable: true
        
        },{
        
        header: getLabelFromMap('cmDsRol',helpMap,'Rol'),
        tooltip: getToolTipFromMap('cmDsRol',helpMap,'Columna Rol'),
        dataIndex: 'dsSisRol',
        width: 100,
        sortable: true
        
        },{
        
        header: getLabelFromMap('cmDsUsuario',helpMap,'Usuario'),
        tooltip: getToolTipFromMap('cmDsUsuario',helpMap,'Columna Usuario'),
        dataIndex: 'dsNombre',
        width: 100,
        sortable: true
        
        },{
        	
        header: getLabelFromMap('cmDsFuncionalidad',helpMap,'Funcionalidad'),
        tooltip: getToolTipFromMap('cmDsFuncionalidad',helpMap,'Columna Funcionalidad'),
        dataIndex: 'dsFunciona',
        width: 100,
        sortable: true
        
        },{
        header: getLabelFromMap('cmDsOperacion',helpMap,'Operaci&oacute;n'),
        tooltip: getToolTipFromMap('cmDsOperacion',helpMap,'Columna Operaci&oacute;n'),
        dataIndex: 'dsOpera',
        width: 100,
        sortable: true
        
        },/*{
        	
        header: getLabelFromMap('cmDsEstado',helpMap,'Estado'),
        tooltip: getToolTipFromMap('cmDsEstado',helpMap,'Columna Estado'),
        dataIndex: 'dsEstado',
        width: 100,
        sortable: true
       
       }*/
       modEstado
       ,{
        dataIndex: 'cdElemento',
        hidden :true
       },{
        dataIndex: 'cdSisRol',
        hidden :true
       },{
        dataIndex: 'cdUsuario',
        hidden :true
       },{
        dataIndex: 'cdFunciona',
        hidden :true
       },{
        dataIndex: 'cdOpera',
        hidden :true
       }               
]);

var grid2;

function createGrid(){
       grid2= new Ext.grid.GridPanel({
       		id: 'grid2',
            el:'gridElementos',
            store: storeGrillaFuncionalidadesPrivilegios,
            title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
            reader: jsonGrillaFuncionalidadesPrivilegios,
            plugins: [modEstado],
            border:true,
            cm: cm,
	        successProperty: 'success',
            buttonAlign: "center",
            buttons:[
                  
                  {
                  text:getLabelFromMap('gridNtfcnBottonAgregar',helpMap,'Agregar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonAgregar',helpMap,'Agregar'),
                  handler:function(){
						window.location = _ACTION_IR_CONFIGURA_FUNCIONALIDADES;
                	}
                  },
                   {
                  text:getLabelFromMap('gridNtfcnBottonEditar',helpMap,'Editar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonEditar',helpMap,'Editar'),
                  handler:function(){
						if (getSelectedKey(grid2, "cdElemento") != "") {
                        		window.location = _ACTION_IR_CONFIGURA_FUNCIONALIDADES_EDITAR + '?cdNivel=' + getSelectedKey(grid2, "cdElemento") + '&cdRol =' + getSelectedKey(grid2, "cdSisRol") + '&cdUsuario =' + getSelectedKey(grid2, "cdUsuario") + '&cdFunciona =' + getSelectedKey(grid2, "dsFunciona");
                        } else {
                        		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                                }
                   }
                  }, {
                  text:getLabelFromMap('gridNtfcnBottonBorrar',helpMap,'Eliminar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonBorrar',helpMap,'Eliminar'),
                  handler:function(){
						if (getSelectedKey(grid2, "nmConfig") != "") {
                        		borrar(getSelectedRecord(grid2));
                        } else {
                        		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
                                }
                   }
                  },
                  {
                  text:getLabelFromMap('gridNtfcnBottonExportar',helpMap,'Exportar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonExportar',helpMap,'Exportar datos a diversos formatos'),
                    handler:function(){
                        //var url = _ACTION_BUSCAR_POLIZAS_EXPORT + '?pv_nivel_i='+ dsNivel.getValue() + '&pv_sisrol_i='+ dsRol.getValue() + '&pv_usuario_i=' + dsUsuario.getValue() + '&pv_funciona_i=' + dsFuncionalidad.getValue();
                        var url = _ACTION_BUSCAR_FUNCIONALIDADES_EXPORT + '?pv_nivel_i='+ Ext.getCmp('dsNivelId').getValue()+ '&pv_sisrol_i='+ Ext.getCmp('dsRolId').getValue() + '&pv_usuario_i=' + Ext.getCmp('dsUsuarioId').getValue() + '&pv_funciona_i=' + Ext.getCmp('dsFuncionalidadId').getValue();
                        showExportDialog( url );
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
					pageSize:itemsPerPage,
					store: storeGrillaFuncionalidadesPrivilegios,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })          
        });
   grid2.render()
}

  
incisosFormFuncionalidad.render();
createGrid();

function borrar(record) {
		if(record.get('cdElemento') != "")
		{
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
			{
		        if (btn == "yes")
		        {
         			var _params = {
         				pv_cdelemento_i : record.get('cdElemento'), 
         				pv_cdsisrol_i : record.get('cdSisRol'), 
         				pv_cdusuario_i : record.get('cdUsuario'), 
         				pv_cdfunciona_i : record.get('cdFunciona'), 
         				pv_cdopera_i : record.get('cdOpera')
         						
         			};
         			execConnection(_ACTION_BORRAR_FUNCIONALIDAD_PRIVILEGIO, _params, cbkConnection);
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
});


function reloadGrid(){
	var _params = {
       				pv_nivel_i: Ext.getCmp('incisosFormFuncionalidad').form.findField('dsNivelId').getValue(),
       				pv_sisrol_i: Ext.getCmp('incisosFormFuncionalidad').form.findField('dsRolId').getValue(), 
       				pv_usuario_i: Ext.getCmp('incisosFormFuncionalidad').form.findField('dsUsuarioId').getValue(), 
       				pv_funciona_i: Ext.getCmp('incisosFormFuncionalidad').form.findField('dsFuncionalidadId').getValue()
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

/*************  Definición de Plugin para Checkbox en grillas ********************************************/
		Ext.grid.CheckColumn = function(config){
		    Ext.apply(this, config);
		    if(!this.id){
		        this.id = Ext.id();
		    }
		    this.renderer = this.renderer.createDelegate(this);
		};

		Ext.grid.CheckColumn.prototype ={
		    init : function(grid){
		        this.grid = grid;
		        this.grid.on('render', function(){
		            var view = this.grid.getView();
		            view.mainBody.on('mousedown', this.onMouseDown, this);
		        }, this);
		    },
		
		    onMouseDown : function(e, t){
		        if(t.className && t.className.indexOf('x-grid3-cc-'+this.id) != -1){
		            e.stopEvent();
		            var index = this.grid.getView().findRowIndex(t);
		            var record = this.grid.store.getAt(index);
		            record.set(this.dataIndex, !record.data[this.dataIndex]);

		            this.grid.getSelectionModel().selectRow(index); //Selecciona la fila completa
		        }
		    },
			onClick: function (e, t) {
			},
		    renderer : function(v, p, record){
		        p.css += ' x-grid3-check-col-td'; 
		        return '<div class="x-grid3-check-col'+(v?'-on':'')+' x-grid3-cc-'+this.id+'">&#160;</div>';
		    }
		};
/*************  Fin Definición de Plugin para Checkbox en grillas ********************************************/