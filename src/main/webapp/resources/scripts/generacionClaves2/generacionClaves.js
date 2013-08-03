Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

 var storeListaValores = new Ext.data.Store({
    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_OBTENER_ALGORITMOS
                }),

                reader: new Ext.data.JsonReader({
            	root:'comboAlgoritmos',
            	totalProperty: 'totalCount',
            	successProperty : '@success'
	        },
	        [
          		{name : 'codigo', mapping : 'codigo', type : 'string'},
             	{name : 'descripcion',mapping : 'descripcion',type : 'string'}
             ]
	        )
	        //baseParams: {cdTipo: '1'}
    });


    var comboListaValores = new Ext.form.ComboBox(
    	{
    		tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
            store: storeListaValores, 
            anchor:'100%', 
            displayField:'descripcion', 
            valueField: 'codigo', 
            hiddenName: 'cdValor',
            typeAhead: true, 
            triggerAction: 'all', 
            lazyRender:   true, 
            emptyText:'Selecione ...', 
            selectOnFocus:true,
            name: 'cdListaValor',
            id:'cdListaValor',
            forceSelection: true, 
            labelSeparator:''
       });

	var recordGrilla = new Ext.data.Record.create([
				
		        {name:'descripcionC',  type:'string',  mapping:'descripcion'},
		        {name:'valor',  type:'string',  mapping:'valor'},
		        {name:'idGenerador',  type:'string',  mapping:'idGenerador'},
		        {name:'idParam',  type:'string',  mapping:'idParam'}
			]);	
			
    var storeGrilla = new Ext.data.Store({
    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_BUSCAR_VALORES 
                }),

                reader: new Ext.data.JsonReader({
            	root:'MGeneracionClavesList',
            	totalProperty: 'totalCount',
            	successProperty : '@success'
	        },
	        	recordGrilla
	        )
    });

 /********* Comienza el form ********************************/
    var el_form = new Ext.FormPanel ({
    		id: 'el_form',
    		//el:'formBusqueda',
            renderTo: 'formBusqueda',
	  		title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('el_formGenClavessId', helpMap,'Generaci&oacute;n de Claves')+'</span>',
            frame : true,
            bodyStyle : 'padding:5px 5px 0',
            bodyStyle:'background: white',
            buttonAlign: "center",
            labelAlign: 'right',
            waitMsgTarget : true,
            width:500,

			 items:[{
			 	layout:'form',
			 	border: false,
			        items:[{
						   layout:'column',
						   //frame:true,
						   bodyStyle:'background: white',
			        	   html:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
						   items:[{
			     		        layout:'form',
							    border:false,
							    columnWidth: 1,
						   		items:[        			
					                   
					                  	comboListaValores
						               
						               ]
				               }],
				            
			                
			           buttonAlign: 'center',
			           bodyStyle:'background: white',
				
			           buttons: [
			                     {
			  					   text:getLabelFromMap('busquedaGenClavesButtonBuscar', helpMap,'Buscar'),
								   tooltip:getToolTipFromMap('busquedaGenClavesButtonBuscar', helpMap,'Busca Valores'),
			                      handler: function () {
			                                      if (el_form.form.isValid()) {
			                                          if (grid2 != null) {
			                                              reloadGrid(grid2);
			                                          }else {
			                                              //createGrid();
			                                          		}
			                                      							}		
			                        					}
			                     },
			                     {
			  					  text:getLabelFromMap('cancelarAdmTipoFaxButtonBuscar', helpMap,'Cancelar'),
								  tooltip:getToolTipFromMap('cancelarAdmTipoFaxButtonBuscar', helpMap,'Cancela la b&uacute;squeda'),
			                     handler: function() {el_form.getForm().reset();}}
			                   ]
          }]
 	}]

            //se definen los campos del formulario
    });
    /********* Fin del form ************************************/


	var cm = new Ext.grid.ColumnModel ([
			{
		        header: getLabelFromMap('cmDescripcion',helpMap,'Descripcion'),
		        tooltip: getToolTipFromMap('cmDescripcion',helpMap,'Descripcion'),
		        dataIndex: 'descripcionC',
		        width:425,
		        sortable: true
		        //editor: new Ext.form.TextField({name: 'descripcionC',id:'descripionId',maxLength:15})
			},
			{
		        header: getLabelFromMap('cmValor',helpMap,'Valor'),
		        tooltip: getToolTipFromMap('cmValor',helpMap,'Valor'),
		        dataIndex: 'valor',
		        sortable: true,
		        width: 60,
		        editor: new Ext.form.NumberField({name: 'valor'})
			},
	        {
	        dataIndex: 'idGenerador',
	        hidden :true
	        },
	        {
	        dataIndex: 'idParam',
	        hidden :true
	        }
			
	]);

	var grid2= new Ext.grid.EditorGridPanel({
        renderTo:'gridElementos',
        id: 'grid2',
        store: storeGrilla,
        clicksToEdit: 1,
		border:true,
		title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Generaci&oacute;n de Claves</span>',
        cm: cm,
        loadMask: {msg: getLabelFromMap('400058',helpMap,'Cargando datos ...'), disabled: false},            	
    	width:500,
    	frame:true,
		height:320,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		collapsible: true,
		buttonAlign: 'center',
		buttons: [
            		/*{
            			text: getLabelFromMap('atbVrbAgnBottonAgregar',helpMap,'Agregar'),
                 		tooltip:getToolTipFromMap('atbVrbAgnBottonAgregar',helpMap,'Agregar'),
                 		handler: function () {
                 				var _record = new recordGrilla ({
                 				descripcionC: '',
                 				valor: '',
                 				idGenerador: '',
                 				idParam: ''
                 			});
                 				grid2.stopEditing();
								grid2.store.insert(0, _record);
								grid2.startEditing(0, 0);
								grid2.getSelectionModel().selectRow(0);
                 		}                 		
            		},*/
            		{
            			text: getLabelFromMap('genClavesBottonGuardar',helpMap,'Guardar'),
                 		tooltip:getToolTipFromMap('genClavesBottonGuardar',helpMap,'Guardar'),
                 		handler: function () {
                 			//alert(Ext.getCmp('nombreId').getSize());
                 			if (grid2.store.getModifiedRecords()==0)
							{
					  	  		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe ingresar un registro para realizar esta operaci&oacute;n'));
	            	 		}else{
		            		 	if(grid2.getSelectionModel().getSelected().get('descripcionC').length <= 299)
		            		 		{
		            		 			actualizaValores();
		            		 		}else{	
		            		 			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe ingresar un nombre m&aacute;s corto'));
				            		 	}
							} 
						}
					}
            		/*{
            			text: getLabelFromMap('atbVrbAgnBottonBorrar',helpMap,'Borrar'),
                 		tooltip:getToolTipFromMap('atbVrbAgnBottonBorrar',helpMap,'Borrar'),
                 		handler: function () {
                 				if (!grid2.getSelectionModel().getSelected()) {
										Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));	               
	   							}else{
	   								if (grid2.getSelectionModel().getSelected().get("cdNombre")==""){
	   									grid2.store.remove(grid2.getSelectionModel().getSelected());
	   								}else{
	   	    		   					borrar(getSelectedRecord(grid2));
	   								}
                 				}
                 	
						}
					}*/            		
		],
		bbar: new Ext.PagingToolbar({
				pageSize:20,
				store: storeGrilla,
				displayInfo: true,
				displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
				emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
		    })
		});

	grid2.render();


storeListaValores.load;
//reloadGrid(grid2);
 
/* storeListaValores.load({
		callback: function () {
					reloadGrid(grid2);				
		}
	});
*/	
 function actualizaValores () {
  var _params = "";
  
  var recs = grid2.store.getModifiedRecords();
  grid2.stopEditing();
  for (var i=0; i<recs.length; i++) {
    _params +=  "grillaGeneracionClavesList[" + i + "].descripcionC=" + recs[i].get('descripcionC') + "&" +
      "&grillaGeneracionClavesList[" + i + "].valor=" + recs[i].get('valor') + "&" + 
      "&grillaGeneracionClavesList[" + i + "].idGenerador=" + recs[i].get('idGenerador')  + "&" +
      "&grillaGeneracionClavesList[" + i + "].idParam=" + recs[i].get('idParam') + "&";
  };
  if (recs.length > 0) {
   var conn = new Ext.data.Connection ();
   conn.request({
     url: _ACTION_GUARDAR_VALORES,
     params: _params,
     method: 'POST',
     callback: function (options, success, response) {
         if (Ext.util.JSON.decode(response.responseText).success == false) {
          Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), Ext.util.JSON.decode(response.responseText).errorMessages[0]);
         } else {
          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), 'Los datos se guardaron con &eacute;xito', function(){reloadGrid(grid2);});
         }
       }
   });
  }
 } 	

/*
function borrar(record) {
		if(record != "")
		{
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se borrar&aacute; el registro seleccionado'),function(btn)
			{
		        if (btn == "yes")
		        {
         			var _params = {
         						cdAtribu: record.get('cdNombre')
         			};
         			execConnection(_ACTION_BORRAR_ATRIBUTOS_VARIABLES_AGENTE, _params, cbkConnection);
               }
			})
		}else{
				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
		}
};
*/
function cbkConnection (_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid(grid2);});
	}
}      

function reloadGrid(grid2){
	var _params = {
	idGenerador: comboListaValores.getValue()
	};
	reloadComponentStore(grid2, _params, cbkReload);
};
function cbkReload(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}
};	

});

