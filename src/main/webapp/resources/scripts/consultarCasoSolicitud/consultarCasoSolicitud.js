Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";
		
//VALORES DE INGRESO DE LA BUSQUEDA
var dsNumCaso = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsNumCasoID',helpMap,'N&uacute;mero de Caso'),
        tooltip:getToolTipFromMap('dsNumCasoID',helpMap,'N&uacute;mero de Caso'), 
        hasHelpIcon:getHelpIconFromMap('dsNumCasoID',helpMap),
		Ayuda: getHelpTextFromMap('dsNumCasoID',helpMap,''),        
        id: 'dsNumCasoID', 
        name: 'dsNumCaso',
        allowBlank: true,
        anchor: '100%'
    });
    
var dsNumOrden = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsNumOrdenID',helpMap,'N&uacute;mero de Orden'),
        tooltip:getToolTipFromMap('dsNumOrdenID',helpMap,'N&uacute;mero de Orden'), 
        hasHelpIcon:getHelpIconFromMap('dsNumOrdenID',helpMap),
		Ayuda: getHelpTextFromMap('dsNumOrdenID',helpMap,''),       
        id: 'dsNumOrdenID', 
        name: 'dsNumOrden',
        allowBlank: true,
        anchor: '100%'
    });

var dsTarea = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsTareaID',helpMap,'Tarea'),
        tooltip:getToolTipFromMap('dsTareaID',helpMap,'Tarea'), 
        hasHelpIcon:getHelpIconFromMap('dsTareaID',helpMap),
		Ayuda: getHelpTextFromMap('dsTareaID',helpMap,''),       
        id: 'dsTareaID', 
        name: 'dsTarea',
        allowBlank: true,
        anchor: '100%'
    });
       
       
var dtFechaIngreso = new Ext.form.DateField({
        fieldLabel: getLabelFromMap('dtFechaIngresoID',helpMap,'Fecha de Ingreso'),
        tooltip:getToolTipFromMap('dtFechaIngresoID',helpMap,'Fecha de Ingreso'), 
        //hasHelpIcon:getHelpIconFromMap('dtFechaIngresoID',helpMap),
		//Ayuda: getHelpTextFromMap('dtFechaIngresoID',helpMap,''),       
        id: 'dtFechaIngresoID', 
        name: 'dtFechaIngreso',
        allowBlank: true,
        anchor: '100%'
       // format: 'd/m/Y'
    });       
       
 
/*var dsPrioridad = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtFldDsPrioridad',helpMap,'Prioridad'),
        tooltip:getToolTipFromMap('txtFldDsPrioridad',helpMap,'Prioridad'), 
        id: 'dsPrioridadID', 
        name: 'dsPrioridad',
        allowBlank: true,
        anchor: '100%'
    }); */  
    
   var dsPrioridad = new Ext.form.ComboBox({
   			tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
            id:'dsPrioridadId',
            //fieldLabel: 'Prioridad',
            store: storeComboPrioridad,
            displayField:'descripcion',
            valueField:'codigo',
            hiddenName: 'cdpriord',
            typeAhead: true,
            mode: 'local',
            triggerAction: 'all',
            fieldLabel: getLabelFromMap('dsPrioridadId',helpMap,'Prioridad'),
		    tooltip: getToolTipFromMap('dsPrioridadId',helpMap,'Seleccione Prioridad'),
			hasHelpIcon:getHelpIconFromMap('dsPrioridadId',helpMap),
		    Ayuda: getHelpTextFromMap('dsPrioridadId',helpMap,''),	      
            width: 150,
            emptyText:'Seleccione Prioridad...',
            selectOnFocus:true,
            labelSeparator:'',
            forceSelection:true,
            listeners: {
					blur: function () {
							if (this.getRawValue() == "") {
								this.setValue();
							}
						  }
						}
   	});
           				   
var dsClienteSolicita = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsClienteSolicitaID',helpMap,'Cliente Solicitante'),
        tooltip:getToolTipFromMap('dsClienteSolicitaID',helpMap,'Cliente Solicitante'), 
        hasHelpIcon:getHelpIconFromMap('dsClienteSolicitaID',helpMap),
		Ayuda: getHelpTextFromMap('dsClienteSolicitaID',helpMap,''),	
        id: 'dsClienteSolicitaID', 
        name: 'dsClienteSolicita',
        allowBlank: true,
        anchor: '100%'
    });    

   
    
    
var incisosForm = new Ext.FormPanel({
		id: 'incisosForm',
        el:'formBusqueda',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('24',helpMap,'Consultar Casos/Solicitudes')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,   
        url: _ACTION_BUSCAR_CASOS_SOLICITUD,
        width: 700,
        height:250,
        items: [{
        		layout:'form',
				border: false,
				items:[{
        		bodyStyle:'background: white',
		        labelWidth: 100,
                layout: 'form',
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
        		        				  dsNumCaso,
        		        				  dsNumOrden,
        		        				  dsTarea,
        		        				  dtFechaIngreso,
        		        				  dsPrioridad,
        		        				  dsClienteSolicita		        				
                                       ]
								}
								]
                			}],
                			buttons:[{
        							text:getLabelFromMap('ntfcnButtonBuscar',helpMap,'Buscar'),
        							tooltip: getToolTipFromMap('ntfcnButtonBuscar',helpMap,'Busca un Grupo de Casos'),
        							handler: function() {
				               			if (incisosForm.form.isValid()) {
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
        							tooltip: getToolTipFromMap('ntfcnButtonCancelar',helpMap,'Cancelar b&uacute;squeda de Casos'),                             
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
        header: getLabelFromMap('cmNmCaso',helpMap,'N&uacute;mero de Caso'),
        tooltip: getToolTipFromMap('cmNmCaso',helpMap,'Columna N&uacute;mero de Caso'),
          hasHelpIcon:getHelpIconFromMap('cmNmCaso',helpMap),
		Ayuda: getHelpTextFromMap('cmNmCaso',helpMap,''),
        dataIndex: 'nmCaso',
        width: 115,
        sortable: true
        },
        {
        header: getLabelFromMap('cmCdOrdenTrabajo',helpMap,'Numero de Orden'),
        tooltip: getToolTipFromMap('cmCdOrdenTrabajo',helpMap,'Columna N&uacute;mero de Orden'),
          hasHelpIcon:getHelpIconFromMap('cmCdOrdenTrabajo',helpMap),
		Ayuda: getHelpTextFromMap('cmCdOrdenTrabajo',helpMap,''),
        dataIndex: 'cdnumerordencia',
        width: 115,
        sortable: true
        },
        {
        header: getLabelFromMap('cmDesProceso',helpMap,'Tarea'),
        tooltip: getToolTipFromMap('cmDesProceso',helpMap,'Columna Tarea'),
          hasHelpIcon:getHelpIconFromMap('cmDesProceso',helpMap),
		Ayuda: getHelpTextFromMap('cmDesProceso',helpMap,''),
        dataIndex: 'desProceso',
        width: 70,
        sortable: true,
        align: 'center'
        },
        {
        header: getLabelFromMap('cmFeRegistro',helpMap,'Fecha de Ingreso'),
        tooltip: getToolTipFromMap('cmFeRegistro',helpMap,'Columna Fecha de Ingreso'),
          hasHelpIcon:getHelpIconFromMap('cmFeRegistro',helpMap),
		Ayuda: getHelpTextFromMap('cmFeRegistro',helpMap,''),
        dataIndex: 'feRegistro',
        width: 115,
        sortable: true,
        align: 'center',
        renderer: function(val) {
		           			try{
		           			var fecha = new Date();
		           			fecha = Date.parseDate(val, 'Y-m-d H:i:s.g');
		           			//alert("Valor: " + val + "\nFecha: " + fecha + "\nformateada : " + fecha.format('d/m/Y'));
               var _val2 = val.format ('Y-m-d H:i:s.g');
              // alert(_val2);
               return _val2.format('d/m/Y');
               }
              catch(e)
              {
              	return fecha.format('d/m/Y');
              }
                }
        },
         {
        header: getLabelFromMap('cmDesNombre',helpMap,'Cliente Solicitante'),
        tooltip: getToolTipFromMap('cmDesNombre',helpMap,'Columna Cliente Solicitante'),
          hasHelpIcon:getHelpIconFromMap('cmDesNombre',helpMap),
		Ayuda: getHelpTextFromMap('cmDesNombre',helpMap,''),
        dataIndex: 'desNombre',
        width: 115,
        sortable: true,
        align: 'center'
        },
        {
        header: getLabelFromMap('cmDesPrioridad',helpMap,'Prioridad'),
        tooltip: getToolTipFromMap('cmDesPrioridad',helpMap,'Columna Prioridad'),
          hasHelpIcon:getHelpIconFromMap('cmDesPrioridad',helpMap),
		Ayuda: getHelpTextFromMap('cmDesPrioridad',helpMap,''),
        dataIndex: 'desPrioridad',
        width: 80,
        sortable: true,
        align: 'center'
        },
        {
        header: getLabelFromMap('cmIndSemafColor',helpMap,'Vigencia'),
        tooltip: getToolTipFromMap('cmIndSemafColor',helpMap,'Columna Vigencia'),
         hasHelpIcon:getHelpIconFromMap('cmIndSemafColor',helpMap),
		Ayuda: getHelpTextFromMap('cmIndSemafColor',helpMap,''),
        dataIndex: 'indSemafColor',
        
		renderer: cambiarColor,
        width: 80,
        sortable: true,
        align: 'center'
        
        },
        {
        dataIndex: 'cdMatriz',
        hidden :true
        },
        {
        dataIndex: 'cdFormatoOrden',
        hidden :true
        },
        {
        dataIndex: 'cdProceso',
        hidden :true
        },
        {
        dataIndex: 'cdUsuario',
        hidden :true
        },
        {
        dataIndex: 'cdPrioridad',
        hidden :true
        },
		{
        dataIndex: 'cdPerson',
        hidden :true
        },
		{
        dataIndex: 'feResolucion',
        hidden :true
        }         
]);

var grid2;

function createGrid(){
       grid2= new Ext.grid.GridPanel({
       		id: 'grid2',
            el:'gridCasos',
            store:storeGrilla,
            title: '<span style="height:10">Listado</span>',
            reader:jsonGrilla,
            border:true,
            buttonAlign:'center',
            loadMask: {msg: getLabelFromMap('4000',helpMap,'Cargando datos ...'), disabled: false},
            cm: cm,
            clicksToEdit:1,
	        successProperty: 'success',
            buttons:[
                  {
                  text:getLabelFromMap('btnAgregarId',helpMap,'Agregar'),
                  tooltip: getToolTipFromMap('btnAgregarId',helpMap,'Lleva a la pantalla Alta de Caso'),
                  handler:function(){
						//if(getSelectedRecord()!=null){			
                					window.location = _ACTION_IR_ALTA_CASO+'?cdperson='+CDPERSON+'&cdElemento='+CDELEMEN;
                			//}
                			//else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));}
                   }
                  },
                  /*{
                  text:getLabelFromMap('btnBorrarId',helpMap,'Borrar'),
                  tooltip: getToolTipFromMap('btnBorrarId',helpMap,'Borrar un Gui&oacute;n'),
                  handler:function(){
						if(getSelectedRecord()!=null){
                					borrar(getSelectedRecord());
                					//reloadGrid();
                			}
                			else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));}
                   }
                  },*/
                  {
                  text:getLabelFromMap('btnEditarId',helpMap,'Editar'),
                  tooltip: getToolTipFromMap('btnEditarId',helpMap,'LLeva a la pantalla consultar caso detalle'),
                  handler:function(){                  		
						if (getSelectedRecord()!=null) {
                        		window.location=_ACTION_IR_CONSULTA_CASO_DETALLE+'?cdmatriz='+getSelectedRecord().get('cdMatriz')+'&nmcaso='+getSelectedRecord().get('nmCaso')+'&cdperson='+CDPERSON+'&cdformatoorden='+getSelectedRecord().get('cdFormatoOrden')+'&limit=999'+'&edit=0';
               			} else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                        }
                	}
                  },
                  {
                  text:getLabelFromMap('gridNtfcnBottonReasignar',helpMap,'Reasignar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonReasignar',helpMap,'Reasignar Caso'),
                  handler:function(){
						 if(getSelectedRecord()!=null){			
                					window.location = _ACTION_IR_REASIGNAR_CASO+'?nmcaso='+getSelectedRecord().get('nmCaso');
                		 }else{
                		 	Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
                		 }
                   }
                  },
                  {
                  text:getLabelFromMap('gridNtfcnBottonExportar',helpMap,'Exportar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonExportar',helpMap,'Exportar datos en diversos Formatos'),
                   handler:function(){
                        var url = _ACTION_EXPORTAR_CASOS_SOLICITUD + '?pv_nmcaso_i=' + dsNumCaso.getValue() + '&pv_cdorden_i=' + dsNumOrden.getValue() + '&pv_dsproceso_i=' + dsTarea.getValue() + '&pv_feingreso_i=' + dtFechaIngreso.getValue() + '&pv_cdpriord_i=' + dsPrioridad.getValue() + '&pv_cdperson_i=' + dsClienteSolicita.getValue()+ '&pv_dsperson_i=' + dsClienteSolicita.getValue();
                         //Ext.getCmp('incisosForm').form.findField('dsNumCaso').getValue(),
       		            showExportDialog( url );
                    }   
                  },
                  {
                  text:getLabelFromMap('gridNtfcnBottonRegresar',helpMap,'Regresar'),
                  tooltip: getToolTipFromMap('gridNtfcnBottonRegresar',helpMap,'Volver a la Pantalla Anterior'),
                  handler: function(){
                  		if(VENGOCLIENT){
                  		VENGOCLIENT = false;
                  		window.location = _ACTION_VOLVER_CONSULTA_CLIENTE;}
                  		else{
                  		VENGOCLIENT = false;
                  		
                  		}
                  		//window.location = _ACTION_VOLVER_CONSULTA_CLIENTE;
                  }
                  }
                  ],
            width:700,
            frame:true,
            height:320,
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			bbar: new Ext.PagingToolbar({
					pageSize:itemsPerPage,
					store: storeGrilla,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })          
        });
   grid2.render()
// Verifico de donde viene para mostrar/ocultar el boton regresar
   if (VENGOCLIENT){
		Ext.getCmp('grid2').buttons[4].setVisible(true);
	}else{
		Ext.getCmp('grid2').buttons[4].setVisible(false);
	}
}

  
incisosForm.render();
createGrid();

//Si venimos de la pantalla Consulta de Cliente, se hará la busqueda automatica
if(VENGOCLIENT){
	reloadGrid();
}


function borrar(record) {
		if(record)
		{
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
			{
		        if (btn == "yes")
		        {
         			var _params = {
         						nmcaso: record.get("nmCaso")
         			};
         			execConnection(_ACTION_BORRAR_CASO_SOLICITUD, _params, cbkConnection);
               }
			})
		}else{
				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
		}
};

         
         
         
function cbkConnection (_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'error'), _message);              
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid()});
	}
}
      
});


function getSelectedRecord(){
    var m = Ext.getCmp('grid2').getSelections();
    if (m.length == 1 ) {
       return m[0];
       }
   }
   
function reloadGrid(){
	var _params = {
       		pv_nmcaso_i: Ext.getCmp('incisosForm').form.findField('dsNumCaso').getValue(),
       		pv_cdorden_i: Ext.getCmp('incisosForm').form.findField('dsNumOrden').getValue(),
       		pv_dsproceso_i: Ext.getCmp('incisosForm').form.findField('dsTarea').getValue(),
       		pv_feingreso_i: Ext.getCmp('incisosForm').form.findField('dtFechaIngreso').getRawValue(),
       		pv_cdpriord_i: Ext.getCmp('incisosForm').form.findField('dsPrioridadId').getValue(),
       		pv_cdperson_i: CDPERSON,
       		pv_dsperson_i: Ext.getCmp('incisosForm').form.findField('dsClienteSolicita').getValue(),
       		pv_cdusuario_i: CDUSUARIO       		
	};
		
	reloadComponentStore(Ext.getCmp('grid2'), _params, cbkReload);
}

    
   function cambiarColor(value, record) {

   	      if(value == 'red'){
	            this.style+=';background:#ff0000';      //  rojo ff0000                                                        
	           }
	     if (value == 'green') {
	            this.style+=';background:#00ff00';      // green 00ff00                                                         
	           }
	   
	     if (value == 'yellow') {
	            this.style+=';background:#ffff00';     // yellow ffff00                                                         
	           }
	    return;
	}




function cbkReload(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}
}