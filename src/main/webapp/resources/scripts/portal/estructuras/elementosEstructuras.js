Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

/*	var codigoBandera = new Ext.form.Hidden({
    	disabled:false,
        name:'codigoBandera',
        value: CODIGO_BANDERA 
    });*/

    var codigoEstructura = new Ext.form.Hidden({
    	disabled:false,
        name:'codigoEstructura',
        value: CODIGO_ESTRUCTURA 
    });
    var descripcionEstructura = new Ext.form.TextField({
    	id:'descripcionEstructuraId',
        fieldLabel: getLabelFromMap('descripcionEstructuraId', helpMap, 'Estructura'),
        tooltip: getToolTipFromMap('descripcionEstructuraId', helpMap, 'Estructura'),
        hasHelpIcon:getHelpIconFromMap('descripcionEstructuraId',helpMap),
		Ayuda: getHelpTextFromMap('descripcionEstructuraId',helpMap),
        name:'descripcionEstructura',
        allowBlank: true,
        readOnly:true,
        value: DESCRIPCION_ESTRUCTURA,
        width: 200
    });
     var nombre = new Ext.form.TextField({
    	id:'elEstNombreId',
        fieldLabel: getLabelFromMap('elEstNombreId', helpMap, 'Nombre'),
        tooltip: getToolTipFromMap('elEstNombreId', helpMap,'Nombre del Elemento'),
        hasHelpIcon:getHelpIconFromMap('elEstNombreId',helpMap),
		Ayuda:getHelpTextFromMap('elEstNombreId',helpMap),
        name:'nombre',
        width: 200
    });    
    var vinculoPadre = new Ext.form.TextField({
       	id:'vinculoPadreId',
        fieldLabel: getLabelFromMap('vinculoPadreId', helpMap,'V&iacute;nculo Padre'),
        tooltip: getToolTipFromMap('vinculoPadreId', helpMap,'V&iacute;nculo Padre del Elemento'),
        hasHelpIcon:getHelpIconFromMap('vinculoPadreId',helpMap),
		Ayuda:getHelpTextFromMap('vinculoPadreId',helpMap),
        name:'vinculoPadre',
         width: 200
    });
    var nomina = new Ext.form.Checkbox({
       	id:'elEstNominaId',
        fieldLabel: getLabelFromMap('elEstNominaId', helpMap,'¿Usa Descuento por n&oacute;mina?'),
        tooltip: getToolTipFromMap('elEstNominaId', helpMap,'N&oacute;mina del Elemento'),
        hasHelpIcon:getHelpIconFromMap('elEstNominaId',helpMap),
		Ayuda:getHelpTextFromMap('elEstNominaId',helpMap),
		labelWidth:170,
        name:'nomina',
        width: 10
    });
    
    var fgNomina = new Ext.grid.CheckColumn({
      id:'cmEstFgNominaId',
      header: getLabelFromMap('cmEstFgNominaId', helpMap, 'N&oacute;mina'),
      tooltip: getToolTipFromMap('cmEstFgNominaId', helpMap,'N&oacute;mina del Elemento'),
      //hasHelpIcon:getHelpIconFromMap('cmEstFgNominaId',helpMap),
	  //helpText:getHelpTextFromMap('cmEstFgNominaId',helpMap),
      dataIndex: 'nomina',
      width: 50,
      sortable: true,
      resizable: true
    });


// Definicion de las columnas de la grilla
    var cm = new Ext.grid.ColumnModel([
    {   
        dataIndex: 'codigoEstructura',
        hidden :true
        },{
        dataIndex: 'codigoElemento',
        hidden :true
        },
        {
        id:'elEstCmGridNombre',
        header: getLabelFromMap('elEstCmGridNombre', helpMap, 'Nombre'),
        tooltip: getToolTipFromMap('elEstCmGridNombre', helpMap,'Nombre del Elemento'),
        dataIndex: 'nombre',
        width: 150,
        sortable: true
        },
        {
        id:'elEstCmGridVincPadre',
        header: getLabelFromMap('elEstCmGridVincPadre', helpMap, 'V&iacute;nculo-Padre'),
        tooltip: getToolTipFromMap('elEstCmGridVincPadre', helpMap, 'V&iacute;nculo-Padre del Elemento'),
        dataIndex: 'dsPadre',
        width: 150,
        sortable: true
        },
        {
        id:'elEstCmGridtipNiv',
        header: getLabelFromMap('elEstCmGridtipNiv', helpMap, 'Tipo de Nivel'),
        tooltip: getToolTipFromMap('elEstCmGridtipNiv', helpMap,'Tipo de Nivel del Elemento'),
        dataIndex: 'tipoNivel',
        width: 100,
        sortable: true
        },
        {
        id:'elEstCmGridPos',
        header: getLabelFromMap('elEstCmGridPos', helpMap, 'Posici&oacute;n'),
        tooltip: getToolTipFromMap('elEstCmGridPos', helpMap, 'Posici&oacute;n del Elemento'),
        dataIndex: 'posicion',
        width: 60,
        sortable: true
        },
            fgNomina
     ]);

 function test(){
 			
 			
            store = new Ext.data.Store({
    
    			proxy: new Ext.data.HttpProxy({
    			url: _ACTION_BUSCAR_ELEMENTO_ESTRUCTURA
                }),
                reader: new Ext.data.JsonReader({
            	root:'MEstructuraList',
            	totalProperty: 'totalCount',
                successProperty : '@success'
	        },[
            {name: 'codigoElemento',  type: 'string',  mapping:'codigoElemento'},
            {name: 'codigoEstructura',  type: 'string',  mapping:'codigoEstructura'},
            {name: 'nombre',  type: 'string',  mapping:'nombre'},
            {name: 'vinculoPadre',  type: 'string',  mapping:'vinculoPadre'},
            {name: 'dsPadre',  type: 'string',  mapping:'dsPadre'},            
            {name: 'tipoNivel',  type: 'string',  mapping:'tipoNivel'},
            {name: 'posicion',  type: 'string',  mapping:'posicion'},
            {name: 'nomina',  type: 'bool',  mapping:'nomina'}            
			])
           
        });
		return store;
 	}


    var grid2;


    function createGrid(){
    	grid2= new Ext.grid.GridPanel({
    		id: 'grid2',
    		title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
            renderTo:'gridElementos',
            store:test(),
    		border:true,
            cm: cm,
            loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
            buttonAlign:"center",
    		buttons:[
            		{
            		id:'gridElemEstruAgregarId',
            		text:getLabelFromMap('gridElemEstruAgregarId', helpMap,'Agregar'),
                	tooltip:getToolTipFromMap('gridElemEstruAgregarId', helpMap,'Agrega un nuevo Elemento'),
                	handler:function(){agregar(codigoEstructura.getValue());}
                	},
                	{
            		id:'gridElemEstruEditarId',
            		text:getLabelFromMap('gridElemEstruEditarId', helpMap,'Editar'),
                	tooltip:getToolTipFromMap('gridElemEstruEditarId', helpMap,'Edita un Elemento'),
                	handler:function(){
                        if (codigoEstructura.getValue()!="" && getSelectedKey(grid2, "codigoElemento") != "" ) {
                            editar(getSelectedRecord(grid2));
                        } else {
                          Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                        }
                    }
                	},
                	{
            		id:'gridElemEstruBorrarId',
                	text:getLabelFromMap('gridElemEstruBorrarId', helpMap,'Eliminar'),
                	tooltip:getToolTipFromMap('gridElemEstruBorrarId', helpMap,'Elimina un Elemento'),
                	handler:function(){
                	if (codigoEstructura.getValue()!="" && getSelectedKey(grid2, "codigoElemento")!="")
                	{
						borrar(codigoEstructura.getValue(),getSelectedKey(grid2, "codigoElemento"));
					}else {
                          Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                        }
                	}
                	},
                	{
            		id:'gridElemEstruCopiarId',
                	text:getLabelFromMap('gridElemEstruCopiarId', helpMap,'Copiar'),
                	tooltip:getToolTipFromMap('gridElemEstruCopiarId', helpMap, 'Copia un Elemento'),
                	handler:function(){
                	if (codigoEstructura.getValue()!="" && getSelectedKey(grid2, "codigoElemento")!="")
                	{
            			copiar(codigoEstructura.getValue(),getSelectedKey(grid2, "codigoElemento"));
            		}else {
                          Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                        }
                	}
                	},
                	{
            		id:'gridElemEstruExportarId',
                	text:getLabelFromMap('gridElemEstruExportarId', helpMap,'Exportar'),
                	tooltip:getToolTipFromMap('gridElemEstruExportarId', helpMap,'Exporta un Elemento'),
                	handler:function(){
                		var url = _ACTION_EXPORTAR_ELEMENTO_ESTRUCTURA + '?codigoEstructura=' + codigoEstructura.getValue()+ '&nombre=' + nombre.getValue()+ '&vinculoPadre=' + vinculoPadre.getValue() + '&nomina=' + nomina.getValue();
                	 	showExportDialog( url );
                        }
                	},
                	{
            		id:'gridElemEstruRegresarId',
                	text:getLabelFromMap('gridElemEstruRegresarId', helpMap,'Regresar'),
                	tooltip:getToolTipFromMap('gridElemEstruRegresarId', helpMap, 'Regresa a la Pantalla Anterior'),
                	handler:function(){
							window.location=_ACTION_REGRESAR_A_ESTRUCTURA+ '?codigoEstructura=' + CODIGO_ESTRUCTURA;
                	}
                	}
                	],
        	width:500,
        	frame:true,
    		height:590,
    		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
    		stripeRows: true,
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

    var incisosForm = new Ext.form.FormPanel({
    	id: 'incisosForm',
        renderTo:'formBusqueda',
		title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('incisosForm', helpMap,'Configurar Elementos de Estructuras')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,
        url:_ACTION_BUSCAR_ELEMENTO_ESTRUCTURA,
        width: 500,
        height:235,
        items: [{
        		layout:'form',
				border: false,
				items:[{
        		bodyStyle:'background: white',
        		title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
		        labelWidth: 70,
                layout: 'form',
				frame:true,
		       	baseCls: '',
		       	buttonAlign: "center",
        		        items: [{
        		        	layout:'column',
		 				    border:false,
		 				    columnWidth: 1,
        		    		items:[{
						    	columnWidth:.8,
                				layout: 'form',
		                		border: false,
		                		labelWidth:170,
        		        		items:[
        		        				codigoEstructura,
                                        descripcionEstructura,
                                        nombre,
                                        vinculoPadre,
                                        nomina
                                       ]
								}]
                			}],
                			buttons:[{
                					id:'elemEstruBuscarId',
        							text:getLabelFromMap('elemEstruBuscarId', helpMap,'Buscar'),
        							tooltip:getToolTipFromMap('elemEstruBuscarId', helpMap,'Busca un Elemento'),
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
                					id:'elemEstruCancelarId',
        							text:getLabelFromMap('elemEstruCancelarId', helpMap,'Cancelar'),
        							tooltip:getToolTipFromMap('elemEstruCancelarId', helpMap,'Cancela la B&uacute;squeda'),                              
        							handler: function(){incisosForm.form.reset();}
        							}
        							]
        					}]
        				}]
				});

		incisosForm.render();
        createGrid();

        function toggleDetails(btn, pressed){
        var view = grid.getView();
        view.showPreview = pressed;
        view.refresh();
    	}
    
    var store;

  function borrar(ces,key){
					Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
					{
	           			if (btn == "yes"){
	           					var _params = {
	           							codigoEstructura: ces,
	           							codigoElemento: key
	           					};
	           					execConnection(_ACTION_BORRAR_ELEMENTO_ESTRUCTURA, _params, cbkConnection);
						}
					})
								
  };
  function copiar (ces,key) {
	
		Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400032', helpMap,'Se copiara el registro seleccionado'),function(btn)
		{
         		if (btn == "yes")
				{
					var _params = {
								codigoEstructura: ces,
								codigoElemento: key
					};
					execConnection(_ACTION_COPIAR_ELEMENTO_ESTRUCTURA, _params, cbkConnection);
           		}
		})
  };
  function cbkConnection(_success, _message) {
  	if (!_success) {
  		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
  	}else {
  		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid();});
  	}
  }

});
function reloadGrid(){
	var _params = {
    		codigoEstructura: Ext.getCmp('incisosForm').form.findField('codigoEstructura').getValue(),
    		nombre: Ext.getCmp('incisosForm').form.findField('nombre').getValue(),
    		vinculoPadre: Ext.getCmp('incisosForm').form.findField('vinculoPadre').getValue(),
    		nomina: Ext.getCmp('incisosForm').form.findField('nomina').getValue() 
	};
	reloadComponentStore(Ext.getCmp('grid2'), _params, cbkReload);
}
function cbkReload (_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}
}

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
        }
    },

    renderer : function(v, p, record){
        p.css += ' x-grid3-check-col-td';
        return '<div class="x-grid3-check-col'+(v?'-on':'')+' x-grid3-cc-'+this.id+'">&#160;</div>';
    }
};