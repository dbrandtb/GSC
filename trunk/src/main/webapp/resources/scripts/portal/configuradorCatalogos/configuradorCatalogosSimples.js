Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";
		var _params = "";
		var arrparams = new Array();
		Ext.each(itemsFormBusqueda, function (campito) {
			if (campito.hidden == "true") {
				campito.xtype = "hidden";
				campito.hidden = true;
				campito.allowBlank = false;
				campito.fieldLabel = "";
				campito.labelSeparator = "";
			} else {
				campito.hidden = false;
			}
		});

	var incisosForm = new Ext.FormPanel({
		id: 'incisosForm',
		renderTo:'formBusqueda',
		title: '<span style="color:black;font-size:12px;">' + TITULO_PANTALLA + '</span>',
		//iconCls:'logo',
        bodyStyle : 'padding:5px 5px 0',
        bodyStyle:'background: white',
		labelAlign: 'right',
		frame:true,   
		url: _ACTION_OBENER_CAMPOS_BUSQUEDA,
		//reader: elJson,
		width: 500,
		//height: 120,
		//autoScroll: true,
		autoHeight: true,
		       
	//layout:'form',
	items:[
			{
			bodyStyle:'background: white',
			items:[
				{
	            bodyStyle : 'padding:5px 5px 0',
	            bodyStyle:'background: white',
				labelWidth: 100,
				layout: 'form',
				
				//frame:true,
				items:[
						{
			   		    layout:'column',
					    border:false,
					    html:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
					    columnWidth: 1,
						items:[
							{
				    		columnWidth:.9,
			           		layout: 'form',
			             	border: false,
							items: itemsFormBusqueda
							}
						]
						}
					]
					}
					],
				   buttonAlign: 'center',
				   buttons: [
							{
								//text: 'Buscar',
								text:getLabelFromMap('confCatSimpBttnBuscar', helpMap,'Buscar'),
								tooltip:getToolTipFromMap('confCatSimpBttnBuscar', helpMap,'Buscar en el Catalogo'),
								handler: function () {
									if (!incisosForm.form.isValid()) {
						                Ext.Msg.alert('Aviso', 'Por favor complete la informaci&oacute;n requerida');
						                return;
									}
									var i=0;
									Ext.each(incisosForm.form.items.items, function (campito) {
										var _index = eval(campito.cdAtribu);
										if (campito.altFormats) arrparams[_index - 1] = campito.getRawValue()
										else arrparams[_index - 1] = campito.getValue();
									});
									//var _params = "?cdPantalla=" + CODIGO_PANTALLA + "&";
									
						        	/*Ext.each(incisosForm.form.items.items, function (campito) {
						        		if (campito.swLlave == "S") {
						        			var _index = eval(campito.cdAtribu);//.substring(1, campito.cdAtribu.length);
						        			arrparams[_index - 1] = campito.getValue();
						        		}
						        	});
						        	Ext.each(incisosForm.form.items.items, function (campito) {
						        		if (campito.cdAtribu == "") {
						        			arrparams[1] = campito.getValue();
						        			i=2;
						        		}
						        	});
						        	i=1;
						        	Ext.each(incisosForm.form.items.items, function (campito){
						        		if (campito.swLlave == "N" && !isNaN(campito.cdAtribu)) {
						        			var j = eval(campito.cdAtribu);
							        		arrparams[j+i] = campito.getValue();
							        		i++;
						        		}
						        	});*/
									/*Ext.each(incisosForm.form.items.items, function(campito) {
											arrparams[i] = campito.getValue();
											i++;
									});*/
									var _params = {
										cdPantalla: CODIGO_PANTALLA,
										valor1: arrparams[0],
										valor2: arrparams[1],
										valor3: arrparams[2],
										valor4: arrparams[3],
										valor5: arrparams[4],
										valor6: arrparams[5]
									}
									reloadGrid(grid2, _params);
								}
							},
							{
								//text: 'Cancelar',
								text:getLabelFromMap('confCatSimpBttnCancelar', helpMap,'Cancelar'),
								tooltip:getToolTipFromMap('confCatSimpBttnCancelar', helpMap,'Cancela la operaci&oacute;n de b&uacute;squeda'),
								handler: function () {
									incisosForm.getForm().reset();
								}
							}
				   ]
						}]

/*		       items: itemsFormBusqueda,
		       buttonAlign: 'center',
		       buttons: [
		       			{
		       				text: 'Buscar',
		       				handler: function () {
								var i=0;
								//var _params = "?cdPantalla=" + CODIGO_PANTALLA + "&";
								
								Ext.each(incisosForm.items.items, function(campito) {
										arrparams[i] = campito.getValue();
										i++;
								});
								var _params = {
									cdPantalla: CODIGO_PANTALLA,
									valor1: arrparams[0],
									valor2: arrparams[1],
									valor3: arrparams[2],
									valor4: arrparams[3],
									valor5: arrparams[4]
								}
		       					reloadGrid(grid2, _params);
		       				}
		       			},
		       			{
		       				text: 'Cancelar',
		       				handler: function () {
		       					incisosForm.getForm().reset();
		       				}
		       			}
		       ]*/
			, listeners: {
				render: function () {
						indexStore--;
						processStores ();
				}
			}				       
			}
		);   

var jsonGrilla= new Ext.data.JsonReader(
  {
   root:'listaBeans',
   totalProperty: 'totalCount',
   successProperty : '@success'
  },
  recordsReader 
 /*[
  {name: 'cdClave1',  mapping:'cdBanco',  type: 'string'},
  {name: 'cdSucurs',  mapping:'cdRol',  type: 'string'},
  {name: 'dsRol',  mapping:'dsRol',  type: 'string'},
  {name: 'cdTitulo',  mapping:'cdTitulo',  type: 'string'},
  {name: 'dsTitulo',  mapping:'dsTitulo',  type: 'string'},
  {name: 'cdCampo',  mapping:'cdCampo',  type: 'string'},      
  {name: 'dsCampo',  mapping:'dsCampo',  type: 'string'},      
  {name: 'cdAccion',  mapping:'cdAccion',  type: 'string'},               
  {name: 'dsAccion',  mapping:'dsAccion',  type: 'string'}               
]*/
);
		function storeGrilla(){
		             store = new Ext.data.Store({
		             proxy: new Ext.data.HttpProxy({
		             		url: _ACTION_OBENER_CAMPOS_BUSQUEDA
				     }),
		             reader:jsonGrilla
		             });
		             return store;
		}
	  /*var cm = new Ext.grid.ColumnModel ([
	  	{
	  		header: 'Id',
	  		sortable: true
	  	},
	  	{
	  		header: 'Descripcion',
	  		sortable: true
	  	}
	  ]);*/
	  var cm = new Ext.grid.ColumnModel(gridColumModel);
      var grid2= new Ext.grid.GridPanel({
       		id: 'grid2',
            el:'gridElementos',
            store:storeGrilla(),
            reader:jsonGrilla,
            border:true,
            loadMask: {msg: getLabelFromMap('400058',helpMap,'Cargando datos ...'), disabled: false},
            width: 500,
            buttonAlign:'Center',
            loadMask: {msg: getLabelFromMap('400058',helpMap,'Cargando datos ...'), disabled: false},
            cm: cm,
            title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
            clicksToEdit:1,
	        successProperty: 'success',
            buttons:[
                  {
                  text:'Agregar',
                  tooltip:'Crea un nuevo Registro',
                  handler:function(){
                  				agregar();
                        		//agregar(CODIGO_CDRENOVA);
						}
                  },
				  {
                  text:'Editar',
                  tooltip:'Edita un Registro',
                  handler:function(){
						if (getSelectedRecord(grid2) != null) {
                        		editar(getSelectedRecord(grid2));
                        } else {
                                 Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operaci&oacute;n');
                                }
                   }
                  },   
                  {
                  text:'Eliminar',
                  tooltip:'Elimina un Registro',
                  handler:function(){
                  		var clave;
                  		Ext.each(recordsReader, function (campito) {
							if (campito.swLlave == "S") {
						        			clave=campito.name;
						      		}
						});
						var fila=getSelectedRecord(grid2);
						if ( fila != null) {
							if (Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap, 'Aviso'), getLabelFromMap('400031', helpMap, 'Se eliminar&aacute; el registro seleccionado'), function (btn){
										if (btn == "yes") {
			                        		borrar(fila.get(clave));
										}
							}));
						} else {
                               	Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operaci&oacute;n');
                        }
					}
                  
                  },
                 /* {
                  text:'Borrar',
                  tooltip:'Borra un Registro',
                  handler:function(){
						if (getSelectedRecord(grid2) != null) {
								if (Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap, 'Aviso'), getLabelFromMap('400031', helpMap, 'Se borrar&aacute; el registro seleccionado'), function (btn){
										if (btn == "yes") {
			                        		borrar(getSelectedRecord(grid2));
										}
								}));
								
               			} else {
                                Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operaci&oacute;n');
                        }
                	}
                  },*/
                  {
                  text:'Exportar',
                  tooltip:'Exporta la B&uacute;squeda',
                  handler:function(){
								var i=0;
								//var _params = "?cdPantalla=" + CODIGO_PANTALLA + "&";
								
								/*Ext.each(incisosForm.form.items.items, function(campito) {
										arrparams[i] = campito.getValue();
										i++;
								});*/
						        	/*Ext.each(incisosForm.form.items.items, function (campito) {
						        		if (campito.swLlave == "S") {
						        			var _index = eval(campito.cdAtribu);//.substring(1, campito.cdAtribu.length);
						        			arrparams[_index - 1] = campito.getValue();
						        		}
						        	});
						        	Ext.each(incisosForm.form.items.items, function (campito) {
						        		if (campito.cdAtribu == "") {
						        			arrparams[1] = campito.getValue();
						        			i=2;
						        		}
						        	});
						        	i=1;
						        	Ext.each(incisosForm.form.items.items, function (campito){
						        		if (campito.swLlave == "N" && !isNaN(campito.cdAtribu)) {
						        			var j = eval(campito.cdAtribu);
							        		arrparams[j+i] = campito.getValue();
							        		i++;
						        		}
						        	});*/
						        Ext.each(incisosForm.form.items.items, function (campito) {
						        	var _index = eval(campito.cdAtribu);
						        	arrparams[_index - 1] = campito.getValue();
						        });
								var _params = "&valor1=" + ((arrparams[0] != undefined)?arrparams[0]:"") + "&" +
									"valor2=" + ((arrparams[1] != undefined)?arrparams[1]:"") + "&" +
									"valor3=" + ((arrparams[2] != undefined)?arrparams[2]:"") + "&" +
									"valor4=" + ((arrparams[3] != undefined)?arrparams[3]:"") + "&" +
									"valor5=" + ((arrparams[4] != undefined)?arrparams[4]:"");
                        var url = _ACTION_EXPORTAR + '?cdPantalla=' + CODIGO_PANTALLA + _params;

                	 	showExportDialog( url );
                    }
                  }/*,
                  {
					text:'Regresar', 
					tooltip:'Vuelve a la Pantalla Anterior',                             
					handler: function() {
					//window.location=_ACTION_REGRESAR_A_CONSULTA_CONFIGURARCION_RENOVACION + '?cdRenova=' + CODIGO_CDRENOVA;
					}
                  }*/
                  ],
            width:500,
            frame:true,
            height:320,
            buttonAlign:'center',
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			//viewConfig: {autoFill: true,forceFit:true},
			stripeRows: true,
			collapsible:true,
			bbar: new Ext.PagingToolbar({
					pageSize: itemsPerPage,
					store: store,
					displayInfo: true,
	                displayMsg: 'Mostrando registros {0} - {1} of {2}',
	                emptyMsg: "No hay registros para visualizar"
			    }),
			    listeners: {
				beforerender: function () {
					//console.log(grid2.getColumnModel());
					for (i=0; i<((grid2.getColumnModel().config.length));i++)
					{
						myString=grid2.getColumnModel().config[i].header;
						myTama=myString.length;
						if ((myTama < 5) && (myTama > 1))
						{
							grid2.getColumnModel().config[i].width=(Math.round((myTama*148)/10));
						}
						else{
							if ((myTama < 12) && (myTama > 4))
							{
								grid2.getColumnModel().config[i].width=(Math.round((myTama*148)/14));
							}else{
								if ((myTama < 20) && (myTama > 11))
								{
									grid2.getColumnModel().config[i].width=(Math.round((myTama*130)/16));
								}else{
								      if ((myTama < 30) && (myTama > 19))
								      {
								      	grid2.getColumnModel().config[i].width=(Math.round((myTama*114)/16));
								      }else{
								      		grid2.getColumnModel().config[i].width=(Math.round((myTama*102)/16));
								      }
								 }	
							}					
						
						}     
						//alert(myTama);
					}				
			    }
			  }
			              
        });
        
        
        
		Ext.each(incisosForm.form.items.items, function (campito) {
			campito.allowBlank = true;
			campito.clearInvalid();
			campito.getEl().dom.readOnly = false;
			if (campito.xtype = "numberfield") {
				campito.setValue();
			}
		});

	incisosForm.render();
   grid2.render()

	function borrar (key) {
	       	var _params = "cdPantalla=" + CODIGO_PANTALLA
						 +'&vlLlave='+key;
	       	execConnection(_ACTION_BORRAR_REGISTRO, _params, cbkBorrar);
	}
	function cbkBorrar (_success, _message) {
		if (!_success) {
			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
		}else {
			Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function() {reloadGrid(Ext.getCmp('grid2'));});
		}
	}
});
function reloadGrid(grilla, _params){
	if (_params == "" || _params == undefined) _params = {cdPantalla: CODIGO_PANTALLA};
    reloadComponentStore(grilla, _params, cbkReload);
}
function cbkReload (_r, _o, _success, _store) {
	if (!_store.reader.jsonData.success) {
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
		_store.removeAll();
	}
}