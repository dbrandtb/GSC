Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

//VALORES DE INGRESO DE LA BUSQUEDAssssss

 var dsCliNivel = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_CLIENTES_CORPORA}),
						reader: new Ext.data.JsonReader({
								root: 'comboClientesCorpBO',
								id: 'cdElemento',
								successProperty: '@success'
							}, [
								{name: 'cdElemento', type: 'string', mapping: 'cdElemento'},
								{name: 'dsElemen', type: 'string', mapping: 'dsElemen'} 
							]),
						remoteSort: true
				});

 var dsRolUsuario = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_ROL_USUARIO}),
						reader: new Ext.data.JsonReader({
								root: 'comboRolUsuario',
								id: 'cdSisRol',
								successProperty: '@success'
							}, [
								{name: 'cdSisRol', type: 'string', mapping: 'cdSisRol'},
								{name: 'dsSisRol', type: 'string', mapping: 'dsSisRol'} 
							]),
						remoteSort: true
				});
				
				
 var dsRolUsuarioFuncionalidad = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_ROL_USUARIO_FUNCIONALIDAD}),
						reader: new Ext.data.JsonReader({
								root: 'comboRolUsuarioFuncionalidad',
								id: 'cdUsuario',
								successProperty: '@success'
							}, [
								{name: 'cdUsuario', type: 'string', mapping: 'cdUsuario'},
								{name: 'dsNombre', type: 'string', mapping: 'dsNombre'} 
							]),
						remoteSort: true
				});				
				
var dsNivel = new Ext.form.ComboBox({
        tpl: '<tpl for="."><div ext:qtip="{cdElemento}. {dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
    	id:'dsNivelId',
    	fieldLabel: getLabelFromMap('cmbNivel',helpMap,'Nivel'),
    	tooltip:getToolTipFromMap('cmbNivel',helpMap,'Nivel'),
    	store: dsCliNivel,
    	displayField:'dsElemen',
    	valueField:'cdElemento',
    	hiddenName: 'dsNivel',
    	typeAhead: true,
    	mode: 'local',
    	triggerAction: 'all',
    	//width: 210,
    	anchor: '100%',
    	emptyText:'Seleccione Nivel...',
    	selectOnFocus:true,
    	//allowBlank:false,
    	forceSelection:true,
        onSelect: function (record) {
                    	
                        incisosFormFuncionalidadAgrega.findById(('cdUsuarioId')).setValue('');
                        dsRolUsuarioFuncionalidad.removeAll();
                        
                        incisosFormFuncionalidadAgrega.findById(('cdSisRolId')).setValue('');
                        dsRolUsuario.removeAll();
	                    dsRolUsuario.load({
	                                    	params: {pv_cdelemento_i: record.get("cdElemento") },
	                         	            waitMsg: 'Espere por favor....'
	                            		 });
	                    
                        this.collapse();
                        this.setValue(record.get("cdElemento"));
                        }
    });
 
var dsRol = new Ext.form.ComboBox({
        tpl: '<tpl for="."><div ext:qtip="{cdSisRol}. {dsSisRol}" class="x-combo-list-item">{dsSisRol}</div></tpl>',
    	id:'cdSisRolId',
    	fieldLabel: getLabelFromMap('cmbRol',helpMap,'Rol'),
    	tooltip:getToolTipFromMap('cmbRol',helpMap,'Rol'),
    	store: dsRolUsuario,
    	displayField:'dsSisRol',
    	valueField:'cdSisRol',
    	hiddenName: 'dsRol',
    	typeAhead: true,
    	mode: 'local',
    	triggerAction: 'all',
    	//width: 210,
    	anchor: '100%',
    	emptyText:'Seleccione Rol...',
    	selectOnFocus:true,
    	forceSelection:true,
    	//allowBlank:false,
    	onSelect: function (record) {
                    	
                       
                        incisosFormFuncionalidadAgrega.findById(('cdUsuarioId')).setValue('');
                        dsRolUsuarioFuncionalidad.removeAll();
                        
	                    dsRolUsuarioFuncionalidad.load({
	                                    	params: {pv_cdelemento_i: incisosFormFuncionalidadAgrega.findById(('dsNivelId')).getValue(),
	                                    	         pv_cdsisrol_i:record.get("cdSisRol")},
	                         	            waitMsg: 'Espere por favor....'
	                            		 });
	                    
                        this.collapse();
                        this.setValue(record.get("cdSisRol"));
                        }
    });
  
var dsUsuario = new Ext.form.ComboBox({
        tpl: '<tpl for="."><div ext:qtip="{cdUsuario}. {dsNombre}" class="x-combo-list-item">{dsNombre}</div></tpl>',
    	id:'cdUsuarioId',
    	fieldLabel: getLabelFromMap('cmbUsuario',helpMap,'Usuario'),
    	tooltip:getToolTipFromMap('cmbUsuario',helpMap,'Usuario'),
    	store: dsRolUsuarioFuncionalidad,
    	displayField:'dsNombre',
    	valueField:'cdUsuario',
    	hiddenName: 'dsUsuario',
    	typeAhead: true,
    	mode: 'local',
    	triggerAction: 'all',
    	//width: 210,
    	anchor: '100%',
    	//allowBlank:false,
    	emptyText:'Seleccione Usuario...',
    	selectOnFocus:true
    	/*forceSelection:true*/
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
      //header: "No Requerida",
      dataIndex: 'swEstado',
      align: 'center',
      sortable: true,
      width: 80
    });



var incisosFormFuncionalidadAgrega = new Ext.FormPanel({
		id: 'incisosFormFuncionalidadAgrega',
        el:'formBusquedaAgregar',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('24',helpMap,'Configuraci&oacute;n de Funcionalidades')+'</span>',
        iconCls:'logo',
        store:storeGrillaFuncionalidadesPrivilegios,
        reader:jsonGrillaFuncionalidadesPrivilegios,
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
              //  title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br/>',
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
        		        				 dsNivel,
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
				               			if (incisosFormFuncionalidadAgrega.form.isValid()) {
                                               if (grid2!=null) {
                                                reloadGrid(grid2);
                                               } else {
                                                createGrid();
                                               }
                						} else{
                							Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
										}
									}
        							},{
        							text:getLabelFromMap('ntfcnButtonCancelar',helpMap,'Cancelar'),
        							tooltip: getToolTipFromMap('ntfcnButtonCancelar',helpMap,'Cancelar busqueda de Funcionalidades de Privilegios'),                              
        							handler: function() {
        								incisosFormFuncionalidadAgrega.form.reset();
        							}
        						}]
        					}]
        				}]	            
        
});   

incisosFormFuncionalidadAgrega.render();
// Definicion de las columnas de la grilla
var cm = new Ext.grid.ColumnModel([
        
        
      {
        header: getLabelFromMap('cmDsFuncionalidad',helpMap,'Funcionalidade'),
        tooltip: getToolTipFromMap('cmDsFuncionalidad',helpMap,'Columna Funcionalidad'),
        dataIndex: 'dsFunciona',
        width: 170,
        sortable: true
        
        },{
        header: getLabelFromMap('cmDsOperacion',helpMap,'Operaci&oacute;nes'),
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

var grid2;

function createGrid(){
    //Crea la grilla de tipo GroupingGrid
        grid2 = new Ext.grid.GridPanel ({
   		store: storeGrillaFuncionalidadesPrivilegios,
   		//reader:jsonGrillaFuncionalidadesPrivilegios,
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
   		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
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
          		tooltip:getToolTipFromMap('chkListCtaBttBack', helpMap,'Regresa a la pantalla anterior'),
           		handler:function(){
           				window.location = _ACTION_IR_FUNCIONALIDADES;
           				}
           		}
           		],
           			
		 bbar: new Ext.PagingToolbar({
		 	    pageSize:20,
				store: storeGrillaFuncionalidadesPrivilegios,
				//reader:jsonGrillaFuncionalidadesPrivilegios,
				displayInfo: true,
				displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
				emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
		     })
   	
   	});
   	
   grid2.render()
}

   
		//Funcion para manejo de ediciones
		function handlerEdit (edtEvt) {
			//para el issue ACW-1126 se puso al combo Usuario como opcional 08-11-2008
			if((Ext.getCmp('incisosFormFuncionalidadAgrega').form.findField('dsNivel').getValue()!="" && Ext.getCmp('incisosFormFuncionalidadAgrega').form.findField('dsRol').getValue() !="") /*&& Ext.getCmp('incisosFormFuncionalidadAgrega').form.findField('dsUsuario').getValue()!=""*/){ 
			var _params ='';
			var totalRecs = grid2.store.getTotalCount();
			if (totalRecs>0)
			{
			var recs = grid2.store.getModifiedRecords();
			if (recs.length > 0) {
				for (var i=0; i<recs.length; i++) {
					_params += "funcionalidades[" + i + "].cdElemento=" + Ext.getCmp('incisosFormFuncionalidadAgrega').form.findField('dsNivel').getValue() + "&"+
							   "funcionalidades[" + i + "].cdFunciona=" + recs[i].get('cdFunciona') + "&"+
							   "funcionalidades[" + i + "].cdSisRol=" + Ext.getCmp('incisosFormFuncionalidadAgrega').form.findField('dsRol').getValue() + "&"+
							   "funcionalidades[" + i + "].cdUsuario=" + Ext.getCmp('incisosFormFuncionalidadAgrega').form.findField('dsUsuario').getValue() + "&"+
							   "funcionalidades[" + i + "].cdOpera=" + recs[i].get('cdOpera') + "&"+
							   "funcionalidades[" + i + "].swEstado=" + ((recs[i].get('swEstado') == true)?1:0) + "&";
							   //"funcionalidades[" + i + "].dsEstado=" + ((recs[i].get('swEstado') == true)?'Activo':'Inactivo') + "&"+
				}
			} else {
				//Si no ha modificado tareas, debe sí o sí enviarlas al momento de guardar
				recs = grid2.getSelectionModel().getSelections(); //Obtiene los datos de todas las tareas
				for (var i=0; i<recs.length; i++) {
					_params += "funcionalidades[" + i + "].cdElemento=" + Ext.getCmp('incisosFormFuncionalidadAgrega').form.findField('dsNivel').getValue() + "&"+
							   "funcionalidades[" + i + "].cdFunciona=" + recs[i].get('cdFunciona') + "&"+
							   "funcionalidades[" + i + "].cdSisRol=" + Ext.getCmp('incisosFormFuncionalidadAgrega').form.findField('dsRol').getValue() + "&"+
							   "funcionalidades[" + i + "].cdUsuario=" + Ext.getCmp('incisosFormFuncionalidadAgrega').form.findField('dsUsuario').getValue() + "&"+
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
	                          					Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), Ext.util.JSON.decode(response.responseText).actionMessages[0],function(){reloadGrid(grid2)});
	                          					//grid2.store.commitChanges(); 
	                          				}
	                          			}
           	});
			}else{
					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'No hay datos para guardar.'));
			}
           	
			return;}else{
           		           Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar Nivel y Rol.'));      
           		                     }
		}
		
		//******************************************************

dsCliNivel.load();
//incisosFormFuncionalidadAgrega.render();
createGrid();

function borrar(record) {
		if(record.get('nmConfig') != "")
		{
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminara el registro seleccionado'),function(btn)
			{
		        if (btn == "yes")
		        {
         			var _params = {
         						pv_nmconfig_i:record.get('nmConfig')
         			};
         			execConnection(_ACTION_BORRAR_FUNCIONALIDAD_PRIVILEGIO, _params, cbkConnection);
               }
			})
		}else{
				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
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


function reloadGrid(grid2){
	var _params = {
       				pv_nivel_i:Ext.getCmp('incisosFormFuncionalidadAgrega').form.findField('dsNivel').getValue(),
       				pv_sisrol_i:Ext.getCmp('incisosFormFuncionalidadAgrega').form.findField('dsRol').getValue(), 
       				pv_usuario_i:Ext.getCmp('incisosFormFuncionalidadAgrega').form.findField('dsUsuario').getValue(), 
       				pv_funciona_i:Ext.getCmp('incisosFormFuncionalidadAgrega').form.findField('comboFuncionalidad').getValue()	
       			  };
       			  //grid2.store.load({params:{_params, start:0,limit:itemsPerPage},callback: (cbkReload != undefined)?function(r, options, success) {eval(cbkReload(r, options, success, grid2.store))}:grid2.store.removeAll()});
       			  grid2.store.baseParams=_params;
       			  grid2.store.load({start:0,limit:itemsPerPage});
       			
	//reloadComponentStore(Ext.getCmp('grid2'), _params, cbkReload);
}
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