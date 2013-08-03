Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

//VALORES DE INGRESO DE LA BUSQUEDAssssss

		
				
var dsElemen = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtDsNivel',helpMap,'Nivel'),
        tooltip:getToolTipFromMap('txtDsNivel',helpMap,'Nivel'), 
        id: 'dsElemenId', 
        name: 'dsElemen',
        allowBlank: true,
        readOnly:true,
        disabled:true,
        anchor: '100%'
    });
 
var dsRol = new Ext.form.TextField({
         fieldLabel: getLabelFromMap('txtDsRol',helpMap,'Rol'),
        tooltip:getToolTipFromMap('txtDsRol',helpMap,'Rol'), 
        id: 'dsRolId', 
        name: 'dsRol',
        allowBlank: true,
         readOnly:true,
         disabled:true,
        anchor: '100%'
    });
  
var dsUsuario = new Ext.form.TextField({
         fieldLabel: getLabelFromMap('txtDsUsuario',helpMap,'Usuario'),
        tooltip:getToolTipFromMap('txtDsUsuario',helpMap,'Usuario'), 
        id: 'dsUsuarioId', 
        name: 'dsUsuario',
        allowBlank: true,
     readOnly:true,
     disabled:true,
        anchor: '100%'
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
  
    
var comboFuncionalidad = new Ext.form.ComboBox({
      tpl: '<tpl for="."><div ext:qtip="{dsFunciona}" class="x-combo-list-item">{dsFunciona}</div></tpl>',
      store: storeComboFuncionalidad,
      id:'comboFuncionalidadId',
      name: 'comboFuncionalidad',
      fieldLabel: 'Funcionalidad',
      tooltip: 'selecione',
      anchor:  '100%',
      allowBlank: true,
      displayField:'dsFunciona',
      valueField:  'dsFunciona',
      typeAhead: true,
      triggerAction: 'all',
      emptyText:'Seleccione funcionalidad..',
      mode: 'local',
      selectOnFocus:true,
      forceSelection:false       
});    
        
    

  var fgEstado = new Ext.grid.CheckColumn({
      id:'fgEstadoId',
      header: getLabelFromMap('fgEstadoId',helpMap,'Estado'),
      tooltip: getToolTipFromMap('fgEstadoId', helpMap,'Estado'),
      dataIndex: 'swEstado',
      align: 'center'
    });

var incisosFormFuncionalidad = new Ext.FormPanel({
		id: 'incisosFormFuncionalidad',
        el:'formBusqueda',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('24',helpMap,'Configuraci&oacute;n de Funcionalidades')+'</span>',
        iconCls:'logo',
        store:storeGrillaFuncionalidadesPrivilegiosEditar,
        reader:jsonGrillaFuncionalidadesPrivilegiosEditar,
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,   
        url: _ACTION_GET_CONFIGURACION_FUNCIONALIDADES_PRIVILEGIOS,
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
                //title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br/>',
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
        		        				 dsElemen,
        		        				 dsRol,
        		        				 dsUsuario,

        		        			
        		        				 {
							              html: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span>'
							             
							              },
        		        				  comboFuncionalidad

                                       ]
								},
								{
								columnWidth:.4,
                				layout: 'form'
                				}]
                			}],
                			buttons:[{
        							text:getLabelFromMap('ntfcnButtonBuscar',helpMap,'Buscar'),
        							tooltip: getToolTipFromMap('ntfcnButtonBuscar',helpMap,'Busca un Grupo de Funcionalidades de Privilegios'),
        							handler: function() {
				               			if (incisosFormFuncionalidad.form.isValid()) {
                                               if (grid2!=null) {
                                                //reloadGrid(Ext.getCmp('incisosFormFuncionalidad').form.findField('dsFuncionalidad').getValue(),grid2);
                                               	reloadGrid(Ext.getCmp('incisosFormFuncionalidad').form.findField('comboFuncionalidad').getValue(),grid2);
                                               } else {
                                               // createGrid();
                                               }
                						} else{
                							Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
										}
									}
        							},{
        							text:getLabelFromMap('ntfcnButtonCancelar',helpMap,'Cancelar'),
        							tooltip: getToolTipFromMap('ntfcnButtonCancelar',helpMap,'Cancelar busqueda de Funcionalidades de Privilegios'),                              
        							handler: function() {
        								Ext.getCmp('incisosFormFuncionalidad').form.findField('comboFuncionalidad').setValue('');
        								//incisosFormFuncionalidad.form.reset();
        							}
        						}]
        					}]
        				}]	            
        
});   

incisosFormFuncionalidad.render();
// Definicion de las columnas de la grilla
var cm = new Ext.grid.ColumnModel([
        
        
      {
        header: getLabelFromMap('cmDsFuncionalidad',helpMap,'Funcionalidad'),
        tooltip: getToolTipFromMap('cmDsFuncionalidad',helpMap,'Columna Funcionalidad'),
        dataIndex: 'dsFunciona',
        width: 170,
        sortable: true
        
        },{
        header: getLabelFromMap('cmDsOperacion',helpMap,'Operaci&oacute;n'),
        tooltip: getToolTipFromMap('cmDsOperacion',helpMap,'Columna Operaci&oacute;n'),
        dataIndex: 'dsOpera',
        width: 160,
        sortable: true
        
        },
        	fgEstado,
        	
        		/*{header: getLabelFromMap('cmDsEstado',helpMap,'Estado'),
        tooltip: getToolTipFromMap('cmDsEstado',helpMap,'Columna Estado'),
        dataIndex: 'dsEstado',
        width: 100,
        sortable: true},*/
       	
       		{
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
        dataIndex: 'swEstado',
        hidden :true
        
       },{
        dataIndex: 'cdFunciona',
        hidden :true
        
       },{
        dataIndex: 'cdOpera',
        hidden :true
        
       }                                     
]);

//var grid2;

/*function createGrid(){
       grid2= new Ext.grid.EditorGridPanel({
       		id: 'grid2',
            el:'gridElementos',
            store:storeGrillaFuncionalidadesPrivilegios,
            title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
            reader:jsonGrillaFuncionalidadesPrivilegios,
            border:true,
            cm: cm,
            clicksToEdit:1,
	        successProperty: 'success',
            buttonAlign: "center",
            buttons:[
                  
                  {
                  text:getLabelFromMap('gridNtfcnBottonAgregar',helpMap,'Agregar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonAgregar',helpMap,'Agregar'),
                  handler:function(){
						if (getSelectedKey(grid2, "cdNotificacion") != "") {
                        		//editar(getSelectedKey(grid2, "cdNotificacion"));
               			} else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                        }
                	}
                  },
                   {
                  text:getLabelFromMap('gridNtfcnBottonGuardar',helpMap,'Guardar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonGuardar',helpMap,'Guardar'),
                  handler:function(){
						if (getSelectedKey(grid2, "nmConfig") != "") {
                        		borrar(getSelectedRecord(grid2));
                        } else {
                        		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                                }
                   }
                  },
                  {
                  text:getLabelFromMap('gridNtfcnBottonExportar',helpMap,'Exportar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonExportar',helpMap,'Exportar datos a diversos formatos'),
                    handler:function(){
                        var url = _ACTION_BUSCAR_FUNCIONALIDADES_EXPORT + '?pv_dsunieco_i=' + dsUniEco.getValue() + '&pv_dsramo_i='+ dsRamo.getValue() + '&pv_dsencuesta_i=' + dsEncuesta.getValue() + '&pv_dscampana_i=' + dsCampan.getValue() + '&pv_dsmodulo_i=' + dsModulo.getValue() + '&pv_dsproceso_i=' + dsProceso.getValue() + '&pv_nmpoliza_i=' + nmPoliza.getValue();
                        showExportDialog( url );
                    } 
                  
                  },
                  {
                  text:getLabelFromMap('gridNtfcnBottonRegresar',helpMap,'Regresar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonRegresar',helpMap,'Volver a la Pantalla Anterior')
                  }
                  ],
            width:500,
            frame:true,
            height:320,
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			bbar: new Ext.PagingToolbar({
					pageSize:10,
					store:storeGrillaFuncionalidadesPrivilegios,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })          
        });
   grid2.render()
}*/

   //Crea la grilla de tipo GroupingGrid
    var grid2 = new Ext.grid.GridPanel ({
    	id:'grid2Id',
   		store: storeGrillaFuncionalidadesPrivilegiosEditar, 
   		columns: [
   					{
        			header: getLabelFromMap('cmDsFuncionalidadId',helpMap,'Funcionalidad'),
        			tooltip: getToolTipFromMap('cmDsFuncionalidadId', helpMap,'Funcionalidad'),   					
   					//header: 'Comentario', 
   					dataIndex: 'dsFunciona',
   					width: 170, 
   					align: 'center'
   					},{
        			header: getLabelFromMap('cmDsOperacionId',helpMap,'Operaci&oacute;n'),
        			tooltip: getToolTipFromMap('cmDsOperacionId', helpMap,'Operaci&oacute;n'),   					
   					//header: 'Comentario', 
   					dataIndex: 'dsOpera', 
   					width: 160,
   					align: 'center'
   					},
   					
   					fgEstado,
   					{
   					header: '', 
   					dataIndex: 'cdOpera', 
   					hidden: true, 
   					align: 'center'
   					},{
   					header: '', 
   					dataIndex: 'cdFunciona', 
   					hidden: true, 
   					align: 'center'
   					},{
   					header: '', 
   					dataIndex: 'cdElemento', 
   					hidden: true
   					}
   				],
   		 		frame: true,
		   		width: 500,
		   		height: 300,
		   		collapsible: true,
		   		loadMask: {msg: getLabelFromMap('400021', helpMap,'Espere...'), disabled: false},
		   		animCollapse: true,
		   		  title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
		   		iconCls: 'icon-grid',
		   		renderTo: 'gridElementos',
		   		plugins: [fgEstado],
		   		buttonAlign: 'center',
		   		buttons:[
		   				{       			
		      			text:getLabelFromMap('chkListCtaBttAdd', helpMap,'Guardar'),
		          		tooltip:getToolTipFromMap('chkListCtaBttAdd', helpMap,'Guardar'),
		           		handler:function(){grid2.getSelectionModel().selectAll();handlerEdit();}
		           		},
		           		{       			
		      			text:getLabelFromMap('chkListCtaBttBack', helpMap,'Regresar'),
		          		tooltip:getToolTipFromMap('chkListCtaBttBack', helpMap,'Regresa a la pantalla anterior.'),
		           		handler:function(){
		           				window.location = _ACTION_IR_FUNCIONALIDADES;
		           				}
		           		}
		           		],
		           		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		      bbar: new Ext.PagingToolbar({
				pageSize:20,
				store: storeGrillaFuncionalidadesPrivilegiosEditar,
				displayInfo: true,
				displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
				emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
		     })
		           		
   	
   	});
   	grid2.render();
		//Funcion para manejo de ediciones
		function handlerEdit (edtEvt) {
			
			var _params ='';
			var recs = grid2.store.getModifiedRecords();
			if (recs.length > 0) {
				for (var i=0; i<recs.length; i++) {
					_params += "funcionalidades[" + i + "].cdElemento=" + CODIGO_NIVEL + "&"+
							   "funcionalidades[" + i + "].cdFunciona=" + recs[i].get('cdFunciona') + "&"+
							   "funcionalidades[" + i + "].cdSisRol=" + CODIGO_ROL + "&"+
							   "funcionalidades[" + i + "].cdUsuario=" + CODIGO_USUARIO + "&"+
							   "funcionalidades[" + i + "].cdOpera=" + recs[i].get('cdOpera') + "&"+
							   "funcionalidades[" + i + "].swEstado=" + ((recs[i].get('swEstado') == true)?1:0) + "&";
							   //"funcionalidades[" + i + "].dsEstado=" + ((recs[i].get('swEstado') == true)?'Activo':'Inactivo') + "&"+
				}
			} else {
				//Si no ha modificado tareas, debe sí o sí enviarlas al momento de guardar
				recs = grid2.getSelectionModel().getSelections(); //Obtiene los datos de todas las tareas
				for (var i=0; i<recs.length; i++) {
					_params += "funcionalidades[" + i + "].cdElemento=" + CODIGO_NIVEL + "&"+
							   "funcionalidades[" + i + "].cdFunciona=" + recs[i].get('cdFunciona') + "&"+
							   "funcionalidades[" + i + "].cdSisRol=" + CODIGO_ROL + "&"+
							   "funcionalidades[" + i + "].cdUsuario=" + CODIGO_USUARIO + "&"+
							   "funcionalidades[" + i + "].cdOpera=" + recs[i].get('cdOpera') + "&"+
							   "funcionalidades[" + i + "].swEstado=" + ((recs[i].get('swEstado') == true)?1:0) + "&";
							   //"funcionalidades[" + i + "].dsEstado=" + ((recs[i].get('swEstado') == true)?'Activo':'Inactivo') + "&"+
				}
			}
			startMask (grid2.id, 'Guardando datos...');
			
			
           var conn = new Ext.data.Connection ();
            conn.request ({
				url: _ACTION_GUARDAR_CONFIGURACION_FUNCIONALIDAD_PRIVILEGIO,
				method: 'POST',
				successProperty : '@success',
				params : _params,
				waitMsg: getLabelFromMap('400017', helpMap,'Espere por favor'),
	                          	callback: function (options, success, response) {
	                          				endMask();
	                          				if (Ext.util.JSON.decode(response.responseText).success == false) {
	                          					Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), Ext.util.JSON.decode(response.responseText).errorMessages[0]);
	                          					band = false;
	                          				} else {
	                          					//Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), Ext.util.JSON.decode(response.responseText).actionMessages[0],function(){reloadGrid( Ext.getCmp('incisosFormFuncionalidad').form.findField('dsFuncionalidad').getValue() );});
	                          					Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), Ext.util.JSON.decode(response.responseText).actionMessages[0],function(){reloadGrid( Ext.getCmp('incisosFormFuncionalidad').form.findField('comboFuncionalidad').getValue() );});
	                          					//grid2.store.commitChanges(); 
	                          				}
	                          			}
           	});
			return;
		} //cierra handlerEdit
		
		//******************************************************



function cbkConnection (_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid("")});
	}
}      
});



function reloadGrid(param,grid2){
	
	
	var _params = {
       				pv_nivel_i:CODIGO_NIVEL,
       				pv_sisrol_i:CODIGO_ROL, 
       				pv_usuario_i:CODIGO_USUARIO, 
       				pv_funciona_i:param
       			  };
       			  storeGrillaFuncionalidadesPrivilegiosEditar.removeAll();
       			  
       			  /*grid2.store.baseParams=_params,
       			  Ext.getCmp('grid2Id').store.load({params:{start:0,limit:itemsPerPage},callback:function(){
       			    if (storeGrillaFuncionalidadesPrivilegiosEditar.reader.jsonData.success){                                               	
                    Ext.getCmp('incisosFormFuncionalidad').form.findField('dsElemen').setValue(storeGrillaFuncionalidadesPrivilegiosEditar.reader.jsonData.listaFuncionalidades[0].dsElemen);
	   				Ext.getCmp('incisosFormFuncionalidad').form.findField('dsRol').setValue(storeGrillaFuncionalidadesPrivilegiosEditar.reader.jsonData.listaFuncionalidades[0].dsSisRol);
	   				Ext.getCmp('incisosFormFuncionalidad').form.findField('dsUsuario').setValue(storeGrillaFuncionalidadesPrivilegiosEditar.reader.jsonData.listaFuncionalidades[0].dsNombre);}
	   				
	   		      }});*/
       			  storeGrillaFuncionalidadesPrivilegiosEditar.baseParams=_params,
       			  storeGrillaFuncionalidadesPrivilegiosEditar.load({params:{start:0,limit:itemsPerPage},
       			                                                   callback:function(){
       			    if (storeGrillaFuncionalidadesPrivilegiosEditar.reader.jsonData.success){                                               	
       			    Ext.getCmp('incisosFormFuncionalidad').form.findField('dsElemen').setValue(CODIGO_NIVEL);
	   				Ext.getCmp('incisosFormFuncionalidad').form.findField('dsRol').setValue(CODIGO_ROL);
	   				Ext.getCmp('incisosFormFuncionalidad').form.findField('dsUsuario').setValue(CODIGO_USUARIO);
	   				
	   				}
	   				
	   		      }
                  });
}




reloadGrid("");
  
function cbkReload(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}
}

storeComboFuncionalidad.load();
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

		            /*if (this.dataIndex == 'fgNoRequ') {
		            	if (record.data['fgNoRequ']) record.set('fgComple', false);
		            	if (record.data['fgNoRequ']) record.set('fgPendiente', false);
		            }*/
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