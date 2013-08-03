Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";


/*************************************************************
** Store combos
**************************************************************/ 

var storeProceso = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                //url: 'confpantallas/obtenerListasConfPantallas.action'
            	url: 'configuradorpantallas/obtenerListasConfPantallas.action'
            }),
            reader: new Ext.data.JsonReader({
            root: 'procesoList',
            id: 'value'
            },[
           {name: 'value', type: 'string', mapping:'value'},
           {name: 'label', type: 'string', mapping:'label'}  
             ]),
            remoteSort: true
        });
        storeProceso.setDefaultSort('value', 'desc');
        storeProceso.load();

    var storeCliente = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                //url: 'confpantallas/obtenerListasConfPantallas.action'
            	url: 'configuradorpantallas/obtenerListasConfPantallas.action'
            }),
            reader: new Ext.data.JsonReader({
            root: 'clienteList',
            id: 'cdCliente'
            },[
           {name: 'cdCliente', type: 'string',mapping:'cdCliente'},
           {name: 'dsCliente', type: 'string',mapping:'dsCliente'}    
            ]),
            remoteSort: true
        });
        storeCliente.setDefaultSort('cdCliente', 'desc');
        storeCliente.load();



/*************************************************************
** Store grid
**************************************************************/
var storeGrid;
function test(){
//url='confpantallas/buscaConjuntos.action'+'?nombreConjunto='+nombreConjunto.getValue()+'&proceso='+comboProceso.getValue()+'&cliente='+comboCliente.getValue();
url='configuradorpantallas/buscaConjuntos.action'; //+'?nombreConjunto='+nombreConjunto.getValue()+'&proceso='+comboProceso.getValue()+'&cliente='+comboCliente.getValue();	
storeGrid = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
           url: url
          
        }),
        reader: new Ext.data.JsonReader({
            root: 'pagedDataList',
            totalProperty: 'totalCount',
            id: 'cdConjunto'
            },[
                {name: 'cdConjunto',  type: 'string',  mapping:'cdConjunto'},
                {name: 'nombreConjunto',  type: 'string',  mapping:'nombreConjunto'},
                {name: 'descripcion',  type: 'string',  mapping:'descripcion'},
                {name: 'proceso',  type: 'string',  mapping:'proceso'},
                {name: 'cliente',  type: 'string',  mapping:'cliente'}
            ]),
        remoteSort: true
    });

    storeGrid.setDefaultSort('cdConjunto', 'desc');
    //storeGrid.load({params:{start:0, limit:10}});
    return storeGrid;
 }
   
   
var nombreConjunto = new Ext.form.TextField({
    fieldLabel: BUSQUEDA_FORM_1_NOMBRE,
    name:'nombreConjunto',
    width:240
});



 var comboProceso = new Ext.form.ComboBox({
     name: 'proceso',
     id: 'idProceso',
     fieldLabel: BUSQUEDA_FORM_1_PROCESO,
     store: storeProceso,
     displayField:'label',
     hiddenName: 'proceso',  
     valueField:'value',
     typeAhead: true,
     mode: 'local',
     allowBlank: false,
     triggerAction: 'all',
     width:240,
     emptyText: BUSQUEDA_FORM_1_PROCESO_EMPTY,
     editable: true,
     labelSeparator:'',  
     selectOnFocus:true,
     forceSelection: true
});


var comboCliente = new Ext.form.ComboBox({
    name: 'cliente',
    id: 'idCliente',
    fieldLabel: BUSQUEDA_FORM_1_CLIENTE,
    store: storeCliente,
    allowBlank: false,
    displayField:'dsCliente',
    hiddenName: 'cliente',  
    valueField:'cdCliente',
    typeAhead: true,
    mode: 'local',
    triggerAction: 'all',
    width:240,
    emptyText: BUSQUEDA_FORM_1_CLIENTE_EMPTY,
    editable: true,
    selectOnFocus:true,
    forceSelection: true
});


var cdConjunto = new Ext.form.TextField({
    name:'cdConjunto',
    type:'hidden',
    labelSeparator:'',
    hidden:true
 });


/*************************************************************
** Forma de busqueda
**************************************************************/ 
   var filterAreaForm = new Ext.form.FormPanel({  
        //url:'confpantallas/buscaConjuntos.action',
   	url:'configuradorpantallas/buscaConjuntos.action',
        //layout:'column',
        id:'filterArea',
        renderTo: 'pantalla',
		cls :'alinear',
        //border:true,
        //frame: true,
        width: 600,
        //height: 300,
        items: [{
               
                //xtype:'fieldset',
                layout: 'form',
                bodyStyle:{background: 'white'},
                title: '<span style="color:black;font-size:12px;">' + BUSQUEDA_PANEL_1_TITLE + '</span>',  
                width: 600,
                height: 220,
                frame: true,
                //defaultType: 'textfield',
                items: [{
                	layout: 'column',
			        	   html:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
	                items :[{
	                		layout: 'form',
	                		width: 600,
	                		items: [
	                			nombreConjunto,
		                        comboProceso,
		                        comboCliente,
		                        cdConjunto 
	                		],  
	                		  buttonAlign: 'center',
			                  buttons:[{
			                        text: BUSQUEDA_FORM_1_BOTON_BUSCAR,  
			                        handler: function() {                                            
			                                if (filterAreaForm.form.isValid()) {
			                                		var _params = {
			                                				cdConjunto: cdConjunto.getValue(),	
															cliente: comboCliente.getValue(),
															nombreConjunto: nombreConjunto.getValue(),
															proceso: comboProceso.getValue()
			                                		}
			                                		reloadComponentStore(gridConjuntos, _params, cbkReload);
			                                        /*
			                                         filterAreaForm.form.submit({
			                                         waitTitle: 'Espere...',
			                                        waitMsg: BUSQUEDA_FORM_1_BOTON_BUSCAR_WAIT_MSG,
			                                         
			                                             failure: function(form, action) {
			                                             		console.log(form);
			                                             		console.log(action);
			                                                    Ext.MessageBox.alert(BUSQUEDA_FORM_1_BOTON_BUSCAR_FAILURE, BUSQUEDA_FORM_1_BOTON_BUSCAR_FAILURE_DESC);
			                                                    gridConjuntos.store.removeAll();
			                                                   },
			                                             success: function(form, action) {
			                                                    //gridConjuntos.destroy();
			                                                    //createGrid();
			                                                    storeGrid.reload();
			                                               
			                                                  }
			                                        });*/                   
			                                 } else{
			                                            Ext.MessageBox.alert(BUSQUEDA_FORM_1_BOTON_BUSCAR_ERROR, BUSQUEDA_FORM_1_BOTON_BUSCAR_ERROR_DESC);
			                                        }  
			                              }      //end handler                                                 
			                         },{
			                         text: BUSQUEDA_FORM_1_BOTON_LIMPIAR,
			                             handler: function() {
			                                     filterAreaForm.form.reset();
			                                    }
			                        },{
			                         text: BUSQUEDA_FORM_1_BOTON_NUEVO,
			                        
			                         handler: function() { 
			                                 window.location  =  _CONTEXT + '/configuradorpantallas/entrarConfigurador.action'+'?id='+cdConjunto.getValue();
			                                                   
			                      }      //end handler     
			                         }]//end buttons FieldSet
			              }]//items panelForm
	                }	                                        
	                ]////end items FieldSet
                }]
              
    });

function cbkReload (_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(BUSQUEDA_FORM_1_BOTON_BUSCAR_FAILURE, BUSQUEDA_FORM_1_BOTON_BUSCAR_FAILURE_DESC);
		Ext.Msg.alert(BUSQUEDA_FORM_1_BOTON_BUSCAR_FAILURE, _store.reader.jsonData.actionErrors[0]);
	}
}
function submitForm(){
        cargaForm.getForm().submit();
    }


/*************************************************************
** Paneles
**************************************************************/ 

    /*var parametros = new Ext.Panel({
        region: 'north',
        title: BUSQUEDA_PANEL_1_TITLE,
        iconCls:'logo',
        split:true,
        height:230,
        id:'filterArea',
        bodyStyle:'padding:5px',
        collapsible: true,
        layoutConfig:{
             animate:true
        },
        items: [filterAreaForm]      
    });

parametros.render('pantalla');*/


var cm = new Ext.grid.ColumnModel([
            new Ext.grid.RowNumberer(),
            {header: BUSQUEDA_GRID_1_HEADER_CLAVE,      dataIndex:'cdConjunto',  width: 120, sortable:true,  locked:false,   id:'cdConjunto', hidden:true},          
            {header: BUSQUEDA_GRID_1_HEADER_NOMBRE,   dataIndex:'nombreConjunto',  width: 150, sortable:true},
            {header: BUSQUEDA_GRID_1_HEADER_DESCRIPCION, dataIndex:'descripcion',  width: 150, sortable:true},
            {header: BUSQUEDA_GRID_1_HEADER_PROCESO,       dataIndex:'proceso',  width: 150, sortable:true},
            {header: BUSQUEDA_GRID_1_HEADER_CLIENTE,       dataIndex:'cliente',  width: 150, sortable:true}
                                
        ]);


////// crear el Grid

var gridConjuntos;
 var selectedId;
	function createGrid() {
		gridConjuntos = new Ext.grid.GridPanel(
				{
					store :test(),
					//title :'<span style="color:#98012e;font-size:11px;font-family:Arial,Helvetica,sans-serif;">' + BUSQUEDA_GRID_1_TITLE + '</span>',
					title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
					cls :'alinear',
					cm :cm,
					stripeRows :true,
					height :320,
					width :600,
					frame: true,
					renderTo: 'main',
					// renderTo:document.body,
			        loadMask: {msg: 'Cargando datos ...', disabled: false},
					stripeRows: true,
					collapsible: true,
					viewConfig : {
						autoFill :true,
						forceFit :true
					},

					sm :new Ext.grid.RowSelectionModel(
							{
								singleSelect :true/*,
								listeners : {
									target :'_top',
									rowselect : function(sm, row, rec) {
										selectedId = storeGrid.data.items[row].id;
										// Ext.MessageBox.alert('selectedID',
										// selectedId);
										cdConjunto.setValue(selectedId);
										window.location.href =  _CONTEXT + '/configuradorpantallas/entrarConfigurador.action' + '?id=' + cdConjunto.getValue();
									}
								}*/
							}),
					buttonAlign: 'center',
					buttons: [
						{
							text: 'Editar',
							handler: function () {
									var _rec = getSelectedRecord(gridConjuntos); 
									if (_rec != null) {
										cdConjunto.setValue(_rec.get('cdConjunto'));
										window.location.href =  _CONTEXT + '/configuradorpantallas/entrarConfigurador.action' + '?id=' + cdConjunto.getValue();
									}else{
			                			Ext.MessageBox.alert('Aviso', 'Debe seleccionar un registro para realizar esta operaci&oacute;n');
	    	            			}
									
							}
						}, 
						{
							text: 'Exportar',
							handler: function () {
                                  var url = _ACTION_EXPORTAR_PANTALLAS + '?nombreConjunto=' + nombreConjunto.getValue() + 
									'&cdProceso=' + comboProceso.getValue() + '&cdElemento=' + comboCliente.getValue() +
									'&cdConjunto=' + cdConjunto.getValue(); 

	               	 	          showExportDialog( url );
							}
						}
					],
					bbar :new Ext.PagingToolbar( {
						pageSize : itemsPerPage,
						store :storeGrid,
						displayInfo :true,
						displayMsg :BUSQUEDA_GRID_1_HEADER_DISPLAY_MSG,
						emptyMsg :BUSQUEDA_GRID_1_HEADER_EMPTY_MSG
					})

				});

		gridConjuntos.render();

	}
filterAreaForm.render();
createGrid();
});
                              