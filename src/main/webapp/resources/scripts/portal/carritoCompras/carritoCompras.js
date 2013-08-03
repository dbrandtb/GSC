//var helpMap = new Map();  

Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

    var dsSiNo = new Ext.data.Store({
         proxy: new Ext.data.HttpProxy({
             url: _ACTION_OBTENER_SINO
         }),
         reader: new Ext.data.JsonReader({
         root: 'siNo',
         id: 'codigo'
         },[
         {name: 'codigo', type: 'string',mapping:'codigo'},
         {name: 'descripcion', type: 'string',mapping:'descripcion'}
     ])
     });

    


 //cantidad a estructuras a mostrar por pagina

    var cm = new Ext.grid.ColumnModel([
        {
        header:  "fgSiNo",   //  "cdConfiguracion",
        dataIndex: 'fgSiNo', //  'cdConfiguracion',
         hidden :true,
        width: 0
        },{
        header: "cdCliente",
        dataIndex: 'cdCliente',
         hidden :true,
        width: 0
        },{
        id:'cmDsNombreCarritoComprasId',
        header: getLabelFromMap('cmDsNombreCarritoComprasId',helpMap,'Cliente'),
        tooltip: getToolTipFromMap('cmDsNombreCarritoComprasId', helpMap,'Cliente'),
		hasHelpIcon:getHelpIconFromMap('cmDsNombreCarritoComprasId',helpMap),								 
        Ayuda: getHelpTextFromMap('cmDsNombreCarritoComprasId',helpMap),
        //header: "Cliente",
        dataIndex: 'dsNombre',
        sortable: true,
        width: 250
        },{
        id:'cmFgSiNoCarritoComprasId',
        header: getLabelFromMap('cmFgSiNoCarritoComprasId',helpMap,'¿Usa Carrito?'),
        tooltip: getToolTipFromMap('cmFgSiNoCarritoComprasId', helpMap,'¿Usa Carrito de Compras?'),
		hasHelpIcon:getHelpIconFromMap('cmFgSiNoCarritoComprasId',helpMap),								 
        Ayuda: getHelpTextFromMap('cmFgSiNoCarritoComprasId',helpMap),
        //header: "¿Usa Carrito?",
        dataIndex: 'fgSiNo',
        sortable: true,
        align: 'left',
        width: 230
    }]);


 function test(){

                store = new Ext.data.Store({

    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_BUSCAR_CARRITO_COMPRAS,
				waitMsg: getLabelFromMap('400017',helpMap,'Espere por favor...')
                }),

                reader: new Ext.data.JsonReader({
            	root:'MCarritoComprasManagerList',
            	totalProperty: 'totalCount',
	            successProperty : '@success'
	        },[
	        {name: 'cdConfiguracion',  type: 'string',  mapping:'cdConfiguracion'},
	        {name: 'cdCliente',  type: 'string',  mapping:'cdCliente'},
	        {name: 'dsNombre',  type: 'string',  mapping:'dsNombre'},
	        {name: 'fgSiNo',  type: 'string',  mapping:'fgSiNo'}
			])
        });

       return store;
 	}


    var grid2;



function createGrid(){
	grid2= new Ext.grid.GridPanel({
        el:'gridCarritoCompras',
        id: 'grilla',
        store:test(),
        title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
		border:true,
		stripeRows: true,
		collapsible: true,
		buttonAlign:'center',
		loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
        cm: cm,
		buttons:[
        		{
        		id:'grid2ButtonAgregarId',
        		text:getLabelFromMap('grid2ButtonAgregarId', helpMap,'Agregar'),
            	tooltip:getToolTipFromMap('grid2ButtonAgregarId', helpMap,'Agrega Carrito de Compras'),	
        		//text:'Agregar',
            	//tooltip:'Agregar uso de Carrito de Compras',
            	handler:function(){
                agregar(null);}
                },{
        		id:'grid2ButtonEditarId',
        		text:getLabelFromMap('grid2ButtonEditarId', helpMap,'Editar'),
            	tooltip:getToolTipFromMap('grid2ButtonEditarId', helpMap,'Edita Carrito de Compras'),	
            	handler:function(){
                    if (getSelectedCodigo() != "") {
                       editar(getSelectedRecord());
                    } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                        //Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operacion');
                    }
            	}
            	},{
        		id:'grid2ButtonBorrarId',
        		text:getLabelFromMap('grid2ButtonBorrarId', helpMap,'Eliminar'),
            	tooltip:getToolTipFromMap('grid2ButtonBorrarId', helpMap,'Elimina Carrito de Compras'),	            	
                handler:function(){
                    if (getSelectedCodigo() != "") {
                        borrar(getSelectedCodigo());
                    } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                        //Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operacion');
                    }
                }
               
            	}/*,{
            	id:'grid2ButtonRegresarId',
        		text:getLabelFromMap('grid2ButtonRegresarId', helpMap,'Regresar'),
            	tooltip:getToolTipFromMap('grid2ButtonRegresarId', helpMap)	
            	
            	//text:'Regresar',
            	//tooltip:'Regresa a la pantalla anterior'
            	} */],
            	
            	
    	width:500,
    	frame:true,
		height:320,
		
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		bbar: new Ext.PagingToolbar({
				pageSize: itemsPerPage,
				store: store,
				displayInfo: true,
				displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
				emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
				//displayMsg: 'Mostrando registros {0} - {1} of {2}',
				//emptyMsg: "No hay registros para visualizar"
		    })
		});

    grid2.render()
}


var cdCliente = new Ext.form.TextField({
        id:'txtFldCdClienteId',   
        fieldLabel: getLabelFromMap('txtFldCdClienteId', helpMap,'Cliente'), 
        tooltip: getToolTipFromMap('txtFldCdClienteId', helpMap,'Cliente'),
		hasHelpIcon:getHelpIconFromMap('txtFldCdClienteId',helpMap),								 
        Ayuda: getHelpTextFromMap('txtFldCdClienteId',helpMap),
        //fieldLabel: 'Cliente',
        name:'cdCliente',
        width: 120
    });



    var incisosForm = new Ext.form.FormPanel({
        el:'formBusqueda',
        id: 'incisosForm',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('incisosForm', helpMap,'Uso de Carrito de Compras')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,
        labelWidth: 150,
        url:_ACTION_BUSCAR_CARRITO_COMPRAS,
        width: 500,
        //height:150,
        autoHeight: true,
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
	        		        	html:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
	        		        	layout:'column',
			 				    border:false,
			 				    columnWidth: 1,
	        		    		items:[{
							    	columnWidth:.6,
	                				layout: 'form',
			                		border: false,
	        		        		items:[
	        		        				cdCliente,
	                                  {
	                                      xtype: 'combo', labelWidth: 50,
	                                      tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                                      id:'fgSiNoF',
	                                      fieldLabel: getLabelFromMap('fgSiNoF',helpMap,'¿Usa carrito de compras?'),
	                                      tooltip: getToolTipFromMap('fgSiNoF',helpMap,'¿Usa Carrito de Compras?'),
		                                  hasHelpIcon:getHelpIconFromMap('fgSiNoF',helpMap),								 
                                          Ayuda: getHelpTextFromMap('fgSiNoF',helpMap),
	                                      store: dsSiNo,
	                                      displayField:'descripcion',
	                                      valueField:'codigo',
	                                      hiddenName: 'fgSiNo',
	                                      typeAhead: true,
	                                      mode: 'local',
	                                      triggerAction: 'all',
	                                      //fieldLabel: "¿Usa carrito de compra?",
	                                      width: 120,
	                                      emptyText:'Seleccione ...',
	                                      selectOnFocus:true,
	                                      //labelSeparator:'',
	                                      forceSelection:true,
	                                      //id:'fgSiNoF', 
	                                      onSelect: function (record) {
	                                      		if (record.get("descripcion") == "") {
	                                      			this.setValue("");
	                                      		}
	                                      		else {
	                                      			this.setValue(record.get("codigo"));
	                                      		}
	                                      		this.collapse();
	                                      },
	                                      listeners: {
			                                    blur: function () {
			                                      		if (this.getRawValue() == "") {
			                                      			this.setValue("");
			                                      		}
			                                    }
	                                      }
	                                  }
	                                       ]
									},{
									columnWidth:.4,
	                				layout: 'form'
	                				}]
	                			}],
	                			buttons:[{
	                				id: 'incisosFormButtonsBuscarId', 
	            					text: getLabelFromMap('incisosFormButtonsBuscarId', helpMap,'Buscar'),
	            					tooltip: getToolTipFromMap('incisosFormButtonsBuscarId', helpMap,'Busca Carrito de Compras'), 
	        							
	        							
	        							//text:'Buscar',
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
	        							id:'incisosFormButtonCancelarId',
	        							text: getLabelFromMap('incisosFormButtonCancelarId', helpMap,'Cancelar'),
	        							tooltip: getToolTipFromMap('incisosFormButtonCancelarId', helpMap,'Cancela la B&uacute;squeda'),
	        							//text:'Cancelar',
	        							handler: function() {
	        								incisosForm.form.reset();
	        							}
	        						}]
	        					}]
        				}]
				});

		incisosForm.render();
        createGrid();
        dsSiNo.load();


        function toggleDetails(btn, pressed){
        var view = grid.getView();
        view.showPreview = pressed;
        view.refresh();
    	}

var store;

    function getSelectedRecord(){
               var m = grid2.getSelections();
               if (m.length == 1 ) {
                  return m[0];
               }
          }

     function getSelectedCodigo(){
              var m = grid2.getSelections();
              var jsonData = "";
              for (var i = 0, len = m.length;i < len; i++) {
                var ss = m[i].get("cdConfiguracion");
                if (i == 0) {
                jsonData = jsonData + ss;
                } else {
                  jsonData = jsonData + "," + ss;
               }
              }
              return jsonData;
         }
     
    function borrar(key) {
    			if(key != "")
				{
					var conn = new Ext.data.Connection();
				
					Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado y sus descuentos asociados'),function(btn)
					{
	           	       if (btn == "yes")
	           	        {
	           	        var _params = {cdConfiguracion: key};
	           	        execConnection(_ACTION_BORRAR_CARRITO_COMPRAS, _params, cbkBorrar);
	           	       
	                	}
					})
				}else{
						Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe Seleccionar un registro para realizar &eacute;sta operaci&oacute;n'));
						//Ext.Msg.alert('Advertencia', 'Debe seleccionar un uso de Carrito de Compras para borrar');
					}
           };
    function cbkBorrar(_success, _messages) {
    	if (_success) {
    		//Ext.Msg.alert('Aviso', _messages, function(){reloadGrid ();});
    		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _messages);
    	}else {
    		//Ext.Msg.alert('Aviso', _messages);   
    		//reloadGrid ();
    		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _messages, function() {reloadGrid();});
    	}
    } 
});
function reloadGrid () {
	var _params = {
			    cdCliente: Ext.getCmp('incisosForm').form.findField('cdCliente').getValue(),
			    fgSiNo: Ext.getCmp('incisosForm').findById('fgSiNoF').getValue()
	}
	reloadComponentStore(Ext.getCmp('grilla'), _params, cbkReload);
}

function cbkReload(_records, _options, _success, _store) {
	if (!_success) {
		//Ext.Msg.alert('Error', _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
		Ext.getCmp('grilla').store.removeAll();
	}
}