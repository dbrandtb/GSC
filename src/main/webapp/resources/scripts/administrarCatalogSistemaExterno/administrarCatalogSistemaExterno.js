
Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

//VALORES DE INGRESO DE LA BUSQUEDA
var desNotificacion = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtFldDesNotificacion',helpMap,'Pais'),
        tooltip:getToolTipFromMap('txtFldDesNotificacion',helpMap,'Pais'), 
        id: 'codPaisId', 
        name: 'codPais',
        disabled : true, 
        allowBlank: true,
        anchor: '100%'
    });
    
var desFormato = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtFldDesFormato',helpMap,'Nº Tabla'),
        tooltip:getToolTipFromMap('txtFldDesFormato',helpMap,'Numero de Tabla'), 
        id: 'numTablaId', 
        name: 'numTabla',
        disabled : true, 
        allowBlank: true,
        anchor: '50%'
    });

var desMetodoEnvio = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtFldDesMetodoEnvio',helpMap,'Sistema Externo'),
        tooltip:getToolTipFromMap('txtFldDesNotificacion',helpMap,'Sistema Externo'), 
        id: 'sistExternoId', 
        name: 'sistExterno',
        disabled : true,
        allowBlank: true,
        anchor: '100%'
    });
       
var campoColumnas = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtFldDesMetodoEnvio',helpMap,'Columnas'),
        tooltip:getToolTipFromMap('txtFldDesNotificacion',helpMap,'Columnas'), 
        id: 'desColumnaId', 
        name: 'desColumna',
        disabled : true,
        allowBlank: true,
        anchor: '50%'
    });      

var campoSistema = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtFldDesMetodoEnvio',helpMap,'Codigo de Sistema'),
        tooltip:getToolTipFromMap('txtFldDesNotificacion',helpMap,'Codigo de Sistema'), 
        id: 'codSistemaId', 
        name: 'codSistema',
        disabled : true,
        allowBlank: true,
        anchor: '50%'
    }); 
    
var busquedaOtvalorExt = new Ext.form.TextField({
        fieldLabel: 'OTVALOR EXT',
        tooltip: 'Búsqueda por OTVALOR EXT', 
        id: 'otvalorExtId', 
        name: 'otvalorExt',
        allowBlank: true,
        maxLength:20,
        anchor: '100%'
    }); 
    
    
var formCatalog = new Ext.FormPanel({ //incisosFormNoti
		id: 'formCatalog',
        renderTo:'formBusqueda',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('24',helpMap,'Administracion Catalogos Sistema Externo')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        waitMsgTarget : true,
        frame:true,   
        width: 500,
        height:290,
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
						    	columnWidth:.6,
                				layout: 'form',
		                		border: false,
        		        		items:[       		        				
        		        				 desNotificacion,
        		        				 desFormato,
        		        				 desMetodoEnvio ,
        		        				 campoColumnas,
        		        				 campoSistema,
        		        				 busquedaOtvalorExt
                                       ]
								},
								{
								columnWidth:.2,
                				layout: 'form'
                				}]
                			}]
                			
        					}],
					        buttonAlign: 'center',
					        buttons: [
					                        {
					     					text:getLabelFromMap('busqueda', helpMap,'Buscar'),
		   									tooltip:getToolTipFromMap('busqueda', helpMap,'Buscar'),
					                        //text: 'Buscar', 
					                        handler: function () {
					                        	if (formCatalog.form.isValid()) {
					                        		 if (grid2 != null) {
			                                                 reloadGridBusqueda();
			                                             }else {
			                                                 createGrid();
			                                             }
		                                         }
							                }
					                        },
					                        {
					     					text:getLabelFromMap('cancelar', helpMap,'Cancelar'),
		   									tooltip:getToolTipFromMap('cancelar', helpMap,'Cancelar'),
					                        //text: 'Cancelar', 
					                        handler: function() {
					                        	Ext.getCmp("otvalorExtId").setValue('');
					                        }}
					                    ]
        				}]
        				
        		// reloadGrid();		
        					            
        
});   


// Definicion de las columnas de la grilla
var cm = new Ext.grid.ColumnModel([
        {
        header: getLabelFromMap('cmDsNotificacionNtfcn',helpMap,'Clave 01 Ext'),
        tooltip: getToolTipFromMap('cmDsNotificacionNtfcn',helpMap,'Columna Clave 01 Ext'),
        dataIndex: 'dsClave1',
        width: 70,
        sortable: true
        },
        {
        header: getLabelFromMap('cmDsFormatoOrdenNtfcn',helpMap,'Clave 02 Ext'),
        tooltip: getToolTipFromMap('cmDdsFormatoNtfcn',helpMap,'Columna Clave 02 Ext'),
        dataIndex: 'dsClave2',
        width: 70,
        sortable: true
        },
        {
        header: getLabelFromMap('cmDsMetEnvNtfcn',helpMap,'Clave 03 Ext'),
        tooltip: getToolTipFromMap('cmDsMetEnvNtfcn',helpMap,'Columna Clave 03 Ext'),
        dataIndex: 'dsClave3',
        width: 70,
        sortable: true,
        align: 'center'
        },
        {
        header: getLabelFromMap('cmDsMetEnvNtfcn',helpMap,'Clave 04 Ext'),
        tooltip: getToolTipFromMap('cmDsMetEnvNtfcn',helpMap,'Columna Clave 04 Ext'),
        dataIndex: 'dsClave4',
        width: 70,
        sortable: true,
        align: 'center'
        },
        {
        header: getLabelFromMap('cmDsMetEnvNtfcn',helpMap,'Clave 05 Ext'),
        tooltip: getToolTipFromMap('cmDsMetEnvNtfcn',helpMap,'Columna Clave 05 Ext'),
        dataIndex: 'dsClave5',
        width: 70,
        sortable: true,
        align: 'center'
        },
        {
        header: getLabelFromMap('cmDsMetEnvNtfcn',helpMap,'OTVALOR EXT'),
        tooltip: getToolTipFromMap('cmDsMetEnvNtfcn',helpMap,'Columna OTVALOR EXT'),
        dataIndex: 'dsOtValor',
        width: 140,
        sortable: true,
        align: 'center'
        },
        
        {
        dataIndex: 'codPais',
        hidden :true
        },
        {
        dataIndex: 'numTabla',
        hidden :true
        },
        
        
        {
        dataIndex: 'cdMetEnv',
        hidden :true
        },
		{
        dataIndex: 'cdProceso',
        hidden :true
        }        
]);

var grid2;

function createGrid(){
       grid2= new Ext.grid.GridPanel({
       		id: 'grid2',
            renderTo:'gridNotificaciones',
            store: storeGetTcataex,
            title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
            reader:elJson_GetNotifi,
            border:true,
            cm: cm,
            loadMask: {msg: getLabelFromMap('400058',helpMap,'Cargando datos ...'), disabled: false},
            clicksToEdit:1,
	        successProperty: 'success',
            buttonAlign: "center",
            buttons:[
                  {
                  text:getLabelFromMap('gridNtfcnBottonAgregar',helpMap,'Agregar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonAgregar',helpMap,'Crea una Nueva Notificacion'),
                  handler:function(){
                  if (Ext.getCmp("sistExternoId").getValue() != "") //&& Ext.getCmp("sistExternoId").getValue()!= "")
                  	{
                  	insertarOActualizar(null);
                  	}
                  
                  else Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe cargar el nombre del catálogo en el sistema externo'));
                  }
                  },
                 
                  {
                  text:getLabelFromMap('gridNtfcnBottonEditar',helpMap,'Editar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonEditar',helpMap,'Editar una Notificacion'),
                  handler:function(){
						
               			if(getSelectedRecord()!= null){
                			 
                			    insertarOActualizar(getSelectedRecord());
                			}                                            
               			
               			
               			 else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
                        }
                	}
                  },
                  
                  {
		      				text:getLabelFromMap('gridButtonBorrar', helpMap,'Eliminar'),
		           			tooltip:getToolTipFromMap('gridButtonBorrar', helpMap,'Elimina una Notificaci&oacute;n'),			        			
		                	handler:function(){
		                			if(getSelectedRecord(grid2) != null){
		                					borrar(getSelectedRecord(grid2));
		                			}
		                			else{
			                			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
		                			}
		                		}
		            	},                  
                  
                 /*  {
                  text:getLabelFromMap('gridNtfcnBottonBorrar',helpMap,'Borrar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonBorrar',helpMap,'Borrar una Notificacion'),
                  handler:function(){
						/*if (getSelectedKey(grid2, "cdNotificacion") != "") {
                        		borrar(getSelectedKey(grid2, "cdNotificacion"));
                        }*/
                         /*	if(getSelectedRecord()!= null){
                			   //actualizar(getSelectedRecord());
                			      borrar(getSelectedRecord());
                			}                                            
		                          else {
		                        		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
		                                }
                   }
                  },*/
                  {
                  text:getLabelFromMap('gridNtfcnBottonRegresar',helpMap,'Regresar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonRegresar',helpMap,'Volver a la Pantalla Anterior'),
                  handler:function(){
                       window.location=_ACTION_IR_ADMINISTRA_EQUIVALENCIA_CATALOGO;
                  }
                  },
                  
                  
                  /*{
		      				text:getLabelFromMap('gridPersonasButtonExportar', helpMap,'Exportar'),
		           			tooltip:getToolTipFromMap('gridPersonasButtonExportar', helpMap,'Exporta datos a otros formatos'),			        			
		            		handler: function (){
				                        var url = _ACTION_EXPORTAR_PERSONAS + '?codTipoPersonaJ=' + el_form.form.findField('dsUsuario').getValue() + '&codCorporativo=' + el_form.form.findField('dsCliente').getValue() +
				                        			'&nombre=' + el_form.form.findField('dsNombre').getValue() + '&rfc=' + el_form.form.findField('dsRFC').getValue();
				                	 	showExportDialog( url );
		            		}

		            	}*/
                  
                  {
                  text:getLabelFromMap('gridNtfcnBottonExportar',helpMap,'Exportar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonExportar',helpMap,'Exportar'),
                  handler:function(){
                                 //var url = _EXPORTAR_TCATAEX + '?codPais='+Ext.getCmp('formCatalog').form.findField('codPais').getValue() +'&cdSistem='+ Ext.getCmp('formCatalog').form.findField('codSistemaId').getValue()+'&otClave='+Ext.getCmp('formCatalog').form.findField('desColumnaId').getValue()+'&otValor='+ Ext.getCmp('formCatalog').form.findField('dsOtValor').getValue()+ '&cdTabla='+Ext.getCmp('formCatalog').form.findField('numTablaId').getValue();
                                 //var url = _EXPORTAR_TCATAEX + '?codPais='+Ext.getCmp('formCatalog').form.findField('codPais').getValue() +'&cdSistem='+ Ext.getCmp('formCatalog').form.findField('codSistemaId').getValue()+ '&cdTabla='+Ext.getCmp('formCatalog').form.findField('numTablaId').getValue(); 
                                 var url = _EXPORTAR_TCATAEX + '?codPais='+ formCatalog.form.findField('codPaisId').getValue() +'&cdSistem='+ formCatalog.form.findField('codSistemaId').getValue()+ '&cdTabla='+formCatalog.form.findField('sistExternoId').getValue(); 
               	 	              showExportDialog( url );
                              }
                              // (codPais, cdSistem, otClave, otValor, cdTabla);*/
                              //  Ext.getCmp('codPais').getValue()
                  }
                  
                  ],
            width:500,
            frame:true,
            height:320,
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			bbar: new Ext.PagingToolbar({
					pageSize:20,
					store: storeGetTcataex,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })          
        });
   grid2.render()
}

Ext.getCmp("codPaisId").setValue(_CODPAIS);	
Ext.getCmp("numTablaId").setValue(_NUMTABLA);	
Ext.getCmp("codSistemaId").setValue(_CODSISTEMA);	// 
Ext.getCmp("sistExternoId").setValue(_CDTABLA);
Ext.getCmp("desColumnaId").setValue(_COLUMNA);
Ext.getCmp("otvalorExtId").setValue(_OTVALOREXT);

formCatalog.render();
createGrid();


function getSelectedRecord(){
	    var m = grid2.getSelections();
	    if (m.length == 1 ) {
	       return m[0];
	       }
    }

    
    
    
    function borrar(record){
		Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),
		
		function(btn)
			{	
         		if (btn == "yes"){
         			var _params = {
         					codPais: _CODPAIS,
				       		cdSistema: _CODSISTEMA,//Ext.getCmp('formCatalog').form.findField('codSistemaId').getValue(),// record.get(''),//
				       		otClave: record.get('dsClave1'),//Ext.getCmp('formCatalog').form.findField('desColumnaId').getValue(),
				       		otValor: record.get('dsOtValor'),//Ext.getCmp('formCatalog').form.findField('sistExternoId').getValue(),
				       		cdTabla:  _CDTABLA,//Ext.getCmp('formCatalog').form.findField('numTablaId').getValue(),// record.get(''),
				       		nmTabla: _NUMTABLA //'numTabla' 
         			};
         			execConnection (_BORRA_REGISTRO, _params, cbkConnection);
				}
								
		})
    
		
  	};

	
  	
  	function cbkConnection (_success, _message) {
		if (!_success) {
			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
		}else {
			Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function() {reloadGrid();});
		}
	}
	
	
    function reloadGridBusqueda(){
		var _params = {
	       		codPais: _CODPAIS,
	       		cdSistem: _CODSISTEMA,
	       		otClave: Ext.getCmp('formCatalog').form.findField('desColumnaId').getValue(),
	       		otValor: Ext.getCmp('formCatalog').form.findField('sistExternoId').getValue(),
	       		cdTabla: _CDTABLA,
	       		otvalorExt: Ext.getCmp('formCatalog').form.findField('otvalorExtId').getValue()
		};
		reloadComponentStore(Ext.getCmp('grid2'), _params, cbkBusqReload);
	}
	
	function cbkBusqReload(_r, _o, _success, _store) {
		var msg = _store.reader.jsonData.actionErrors[0];
		if (_store.reader.jsonData.totalCount == 0) {		
			Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
		}
	}

	
    //Muestra los componentes en pantalla
/*    status_store.load({
    		callback: function () {
			    persona_store.load();
    		}
    });*/
/*formCatalog.render();
createGrid();*/
});

storeGetTcataex.baseParams= { 
         'codPais': _CODPAIS,
         'cdSistem': _CODSISTEMA,
         'cdTabla': _CDTABLA,
         'otvalorExt': _OTVALOREXT
          };
    storeGetTcataex.proxy = new Ext.data.HttpProxy({
      url : _ACTION_BUSCAR_TCATAEXT
    });
    
    storeGetTcataex.load({
    callback: function(r, options, success){
    	if(!success){
        //console.log(storeGetTcataex.reader);
        Ext.MessageBox.alert('Buscar',storeGetTcataex.reader.jsonData.errorMessages[0]);
        //grid2.store.removeAll();
        }
      }
    });


/*storeGetTcataex.load(function (){ var _params = {
       		codPais: _CODPAIS,// Ext.getCmp('formCatalog').form.findField('codPaisId').getValue(),//_CODPAIS
       		cdSistem: _CODSISTEMA,//Ext.getCmp('formCatalog').form.findField('codSistemaId').getValue(), //_CODSISTEMA,
       		//otClave: Ext.getCmp('formCatalog').form.findField('desColumnaId').getValue(),//
       		//otValor: Ext.getCmp('formCatalog').form.findField('sistExternoId').getValue(),//
       		cdTabla: _CDTABLA//Ext.getCmp('formCatalog').form.findField('numTablaId').getValue() // _CDTABLA
	};
	proxy: new Ext.data.HttpProxy({
		url: _ACTION_BUSCAR_TCATAEXT,
        waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
        })
   reader:elJson_GetNotifi
   });
	
	//reloadComponentStore(Ext.getCmp('grid2'), _params, cbkReload);
*/
    
function reloadGrid(){
	var _params = {
       		codPais: _CODPAIS,// Ext.getCmp('formCatalog').form.findField('codPaisId').getValue(),//_CODPAIS
       		cdSistem: _CODSISTEMA,//Ext.getCmp('formCatalog').form.findField('codSistemaId').getValue(), //_CODSISTEMA,
       		otClave: Ext.getCmp('formCatalog').form.findField('desColumnaId').getValue(),//
       		otValor: Ext.getCmp('formCatalog').form.findField('sistExternoId').getValue(),//
       		cdTabla: _CDTABLA//Ext.getCmp('formCatalog').form.findField('numTablaId').getValue() // _CDTABLA
	};
	reloadComponentStore(Ext.getCmp('grid2'), _params, cbkReload);
}

function cbkReload(_r, _options, _success, _store) {
	if (!_success) {		
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
		_store.removeAll();
	}
}

