function comprarTiempo(_nmcaso,_nivatn, _proceso, _usuario){

/********* Comienza la grilla ******************************/

	var idUnidad = new Ext.data.Store({
	        proxy: new Ext.data.HttpProxy({
	            url: _ACTION_COMBO_UNIDAD_TIEMPO 
	            }),
	        reader: new Ext.data.JsonReader({
	        root: 'comboDatosCatalogo',
	        id: 'datosUnidad'
	        },[
	       {name: 'unidadCom', mapping:'id', type: 'string'},
	       {name: 'desc', mapping:'texto', type: 'string'}
	     
	    ])
	});			


	var cmbUnidad = new Ext.form.ComboBox({
	    tpl: '<tpl for="."><div ext:qtip="{unidadCom}.{desc}" class="x-combo-list-item">{desc}</div></tpl>',
	    store: idUnidad,
	    id:'cmbUnidad',
	    name: 'cmbUnidad',
	    displayField:'desc',
	    valueField:'unidadCom',
	    hiddenName: 'cmbUnidadH',
	    typeAhead: true,
	    allowBlank : false,
	    mode: 'local',
	    triggerAction: 'all',
	    fieldLabel: getLabelFromMap('cmbUnidad',helpMap,'Unidad'),
	    tooltip:getToolTipFromMap('cmbUnidad',helpMap,'Seleccione unidad de tiempo a comprar '),
	    hasHelpIcon:getHelpIconFromMap('cmbUnidad',helpMap),
		Ayuda: getHelpTextFromMap('cmbUnidad',helpMap,''),  
	    labelAlign:'right',
	    width:120,
	    selectOnFocus:true,
	    forceSelection:true,
	    emptyText:'Seleccione Unidad...'
	    
	    
	   });	
	

		var jsonForm= new Ext.data.JsonReader(
		{
		root:'listaCompra',
		successProperty : '@success'
		},
		
		
	    [
	       {name: 'tarea', mapping:'cdProceso', type: 'string'},
	       {name: 'dsProceso', mapping:'dsProceso', type: 'string'},
	       {name: 'nmCaso', mapping:'nmCaso', type: 'string'},
	       {name: 'nmOrden', mapping:'cdOrdenTrabajo', type: 'string'},
	       {name: 'nmCompra_Per', mapping:'nmCompraPer', type: 'string'},
	       {name: 'nmCompra_Con', mapping:'nmCompraCon', type: 'string'},
	       {name: 'nmCompra_Dis', mapping:'nmCompraDis', type: 'string'},
	       {name: 'tDisponible', mapping:'nmCantHasta', type: 'string'},
	       {name: 'unidadTDisponible', mapping:'TUnidad', type: 'string'}
	       
	    ]
	    );

			
	/*var storeForm = Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
	            url: _ACTION_OBTENER_COMPRA_TIEMPO 
	            }),
		reader:jsonForm
	});*/
		

/********* Comienza el form ********************************/
	var el_form = new Ext.FormPanel ({			
            bodyStyle : 'padding:5px 5px 0',
            bodyStyle:'background: white',
            width : 680,
            autoHeigth:true,
            labelAlign:'right',
            url: _ACTION_OBTENER_COMPRA_TIEMPO,
            reader: jsonForm,
        	layout: 'table',
			frame:true,
			layoutConfig:{
						columns:3
			},
			items:[{
				layout:"form",
				labelWidth : 50,
				//width: 330,
				items:[{
						xtype: 'numberfield',
						readOnly: true,
						disabled: true,
						//anchor: '95%',
						//fieldLabel: 'Tarea',
						//tooltip:'Tarea',
						fieldLabel: getLabelFromMap('tarea_Id',helpMap,'Tarea'),
					    tooltip:getToolTipFromMap('tarea_Id',helpMap,'Tarea'), 	     
					    hasHelpIcon:getHelpIconFromMap('tarea_Id',helpMap),
		                Ayuda: getHelpTextFromMap('tarea_Id',helpMap,''),     
						labelAlign:'right',
						id: 'tarea_Id', 
						name: 'tarea',
						maxLength: 4,
						width:50
				    	}]
				 },
				 {
				layout:"form",
				colspan:2,
				//width: 330,
				labelWidth : 60,
				items:[{
						xtype: 'textfield',
						//id:'dsTarea',
						readOnly: true,
						disabled: true,
						//fieldLabel: '',
						 labelSeparator:'',
						//tooltip:'Descripci&oacute;n de Tarea',
						fieldLabel: getLabelFromMap('tarea_Id_Desc',helpMap,''),
					    tooltip:getToolTipFromMap('tarea_Id_Desc',helpMap,'Tarea'), 	     
					    hasHelpIcon:getHelpIconFromMap('tarea_Id_Desc',helpMap),
		                Ayuda: getHelpTextFromMap('tarea_Id_Desc',helpMap,''),     
						labelAlign:'right',
						id:'dsTarea',
						name: 'dsProceso',
					    maxLength: 40,
						width:250
				    	}]
				 },
				 {
				layout:"form",
				labelWidth : 100,
				items:[{
						xtype: 'textfield',
						readOnly: true,
						disabled: true,
						//anchor: '95%',
						//fieldLabel: 'N&uacute;mero de Caso',
						//tooltip:'N&uacute;mero de Caso',
						fieldLabel: getLabelFromMap('nmCaso_Id',helpMap,'N&uacute;mero de Caso'),
					    tooltip:getToolTipFromMap('nmCaso_Id',helpMap,'N&uacute;mero de Caso'), 	     
					    hasHelpIcon:getHelpIconFromMap('nmCaso_Id',helpMap),
		                Ayuda: getHelpTextFromMap('nmCaso_Id',helpMap,''),    
						labelAlign:'right',
						id: 'nmCaso_Id',
						value:_nmcaso, 
						name: 'nmCaso',
						maxLength: 50,
						width:170
				    	}]
				 },{
				layout:"form"
				 },
				 {
				layout:"form",
				labelWidth : 120,
				items:[{
						xtype: 'numberfield',
						readOnly: true,
						disabled: true,
						//anchor: '95%',
						//fieldLabel: 'N&uacute;mero de Orden',
						//tooltip:'N&uacute;mero de Orden',
						fieldLabel: getLabelFromMap('nmOrden_Id',helpMap,'N&uacute;mero de Orden'),
					    tooltip:getToolTipFromMap('nmOrden_Id',helpMap,'N&uacute;mero de Orden'), 	     
					    hasHelpIcon:getHelpIconFromMap('nmOrden_Id',helpMap),
		                Ayuda: getHelpTextFromMap('nmOrden_Id',helpMap,''),    
						labelAlign:'right',
						//id: 'nmOrdenId', 
						name: 'nmOrden',
						maxLength: 20,
						width:154
				    	}]
				 },
				 {
				layout:"form",
				labelWidth : 200,
				colspan:3,
				items:[{
						xtype: 'numberfield',
						readOnly: true,
						disabled: true,
						//anchor: '95%',
						//fieldLabel: 'Cantidad de Compras Permitidas',
						//tooltip:'Cantidad de Compras Permitidas',
						fieldLabel: getLabelFromMap('nmcantCompras_Id',helpMap,'Cantidad de Compras Permitidas'),
					    tooltip:getToolTipFromMap('nmcantCompras_Id',helpMap,'Cantidad de Compras Permitidas'), 	     
					    hasHelpIcon:getHelpIconFromMap('nmcantCompras_Id',helpMap),
		                Ayuda: getHelpTextFromMap('nmcantCompras_Id',helpMap,''),    
						labelAlign:'right',
						//id: 'nmCompra_PerId', 
						name: 'nmCompra_Per',
						maxLength: 4,
						width:75
				    	}]					
				 },
				 {
				layout:"form",
				labelWidth : 200,
				colspan:3,
				items:[{
						xtype: 'numberfield',
						readOnly: true,
						disabled: true,
						//anchor: '95%',
						//fieldLabel: 'Cantidad de Compras Consumidas',
						//tooltip:'Cantidad de Compras Consumidas',
						fieldLabel: getLabelFromMap('nmcantCompConsum_Id',helpMap,'Cantidad de Compras Consumidas'),
					    tooltip:getToolTipFromMap('nmcantCompConsum_Id',helpMap,'Cantidad de Compras Consumidas'), 	     
					    hasHelpIcon:getHelpIconFromMap('nmcantCompConsum_Id',helpMap),
		                Ayuda: getHelpTextFromMap('nmcantCompConsum_Id',helpMap,''),   
						labelAlign:'right',
						//id: 'nmCompra_ConId', 
						name: 'nmCompra_Con',
						maxLength: 4,
						width:75
				    	}]
				 },
				 {
				layout:"form",
				labelWidth : 200,
				colspan:3,
				items:[{
						xtype: 'numberfield',
						readOnly: true,
						disabled: true,
						//anchor: '95%',
						//fieldLabel: 'Cantidad de Compras Disponibles',
						//tooltip:'Cantidad de Compras Disponibles',
						fieldLabel: getLabelFromMap('nmcantCompDisp_Id',helpMap,'Cantidad de Compras Disponibles'),
					    tooltip:getToolTipFromMap('nmcantCompDisp_Id',helpMap,'Cantidad de Compras Disponibles'), 	     
					    hasHelpIcon:getHelpIconFromMap('nmcantCompDisp_Id',helpMap),
		                Ayuda: getHelpTextFromMap('nmcantCompDisp_Id',helpMap,''),   
						labelAlign:'right',
						//id: 'nmCompra_DisId', 
						name: 'nmCompra_Dis',
						maxLength: 4,
						width:75
				    	}]
				 },
				 {
				layout:"form",
				labelWidth : 200,
				items:[{
						xtype: 'numberfield',
						readOnly: true,
						disabled: true,
						//anchor: '95%',
						//fieldLabel: 'Tiempo Disponible para Compras',
						//tooltip:'Tiempo Disponible para Compras',
						fieldLabel: getLabelFromMap('nmcantTiempDisp_Id',helpMap,'Tiempo Disponible para Compras'),
					    tooltip:getToolTipFromMap('nmcantTiempDisp_Id',helpMap,'Tiempo Disponible para Compras'), 	     
					    hasHelpIcon:getHelpIconFromMap('nmcantTiempDisp_Id',helpMap),
		                Ayuda: getHelpTextFromMap('nmcantTiempDisp_Id',helpMap,''),   
						labelAlign:'right',
						id: 'tDisponibleId', 
						name: 'tDisponible',
						maxLength: 4,
						width:75
				    	}]
				 },
				 {
				layout:"form"
				},
				 {
				layout:"form",
				labelWidth : 70,
				items:[{
						xtype: 'textfield',
						readOnly: true,
						disabled: true,
						//anchor: '95%',
						//fieldLabel: 'Unidad',
						//tooltip:'Unidad de Tiempo Disponible para Compra',
						fieldLabel: getLabelFromMap('nmUnidadDisp_Id',helpMap,'Unidad'),
					    tooltip:getToolTipFromMap('nmUnidadDisp_Id',helpMap,'Unidad de Tiempo Disponible para Compra'), 	     
					    hasHelpIcon:getHelpIconFromMap('nmUnidadDisp_Id',helpMap),
		                Ayuda: getHelpTextFromMap('nmUnidadDisp_Id',helpMap,''),   
						labelAlign:'right',
						id: 'unidadTDisponibleId', 
						name: 'unidadTDisponible',
						maxLength: 50,
						width:120
				    	}]
				 },
				 {
				layout:"form",
				labelWidth : 200,
				items:[{
						xtype: 'textfield',
						//readOnly: true,
						//fieldLabel: 'Tiempo a Comprar',
						//tooltip:'Tiempo a Comprar',
						fieldLabel: getLabelFromMap('tCompra_Id',helpMap,'Tiempo a Comprar'),
					    tooltip:getToolTipFromMap('tCompra_Id',helpMap,'Tiempo a Comprar'), 	     
					    hasHelpIcon:getHelpIconFromMap('tCompra_Id',helpMap),
		                Ayuda: getHelpTextFromMap('tCompra_Id',helpMap,''),   
						labelAlign:'right',
						id: 'tCompra_Id', 
						allowBlank: false,
						name: 'tCompra',
						maxLength: 4,
						width:75
				    	}]
				 },
				 {
				layout:"form"
				},
				 {
				 layout:"form",
				 labelWidth : 70,
				 items:[
				 		cmbUnidad
				 		]
				 }
			]


            //se definen los campos del formulario
	});
	
/********* Fin del form ************************************/

	var _window = new Ext.Window ({
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('cnstCsDtCmprTmp',helpMap,'Comprar Tiempo')+'</span>',
			width: 670,
			aupoHeight: true,
        	bodyStyle:'background: white',
        	modal: true,
        	buttonAlign:'center',
        	items: [el_form],
            //se definen los botones del formulario
            buttons : [ {
                text : getLabelFromMap('txtBtnGuardarId',helpMap,'Guardar'),
				tooltip:getToolTipFromMap('txtBtnGuardarId',helpMap,'Guarda compra de tiempo'),
                disabled : false,
                handler : function() {
                			if (el_form.form.isValid()) {
                				
	                	    	if((transformarMinuto(el_form.findById('tCompra_Id').getValue(),el_form.findById('cmbUnidad').getValue())) <= (transformarMinuto(el_form.findById('tDisponibleId').getValue(),el_form.findById('unidadTDisponibleId').getValue()))){
                				//if (el_form.findById('tCompra_Id').getValue() <= el_form.findById('tDisponibleId').getValue()){
	                				//guardarDatos();                
	                	    		validaStatusCaso();
	                			}   					 					
	   					 		else{
							    	Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400066', helpMap,'El Tiempo a Comprar no puede ser mayor que el Tiempo Disponible para Compras'));
									}
                				}
                			else{
				               	Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400087', helpMap,'Debe ingresar el tiempo y la unidad para la compra'));
                			}
                						
   					 		
                		//  }//Fin function
                }//Fin Boton Guardar
                },
            	
            	{

                text : getLabelFromMap('txtBtnRegresarId',helpMap,'Regresar'),
				tooltip:getToolTipFromMap('txtBtnRegresarId',helpMap,'Regresa a la pantalla anterior'),

                handler : function() {
                    					_window.close()
                					 }
                }//Fin boton Regresar
                ]//Fin Buttons

	});
	
	
	_window.show();
	
	el_form.form.load({
    params: {
    	      tarea: _proceso,
			  nivAtencion: _nivatn,
			  nmCaso: _nmcaso 
    	},
    	
    waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
    waitMsg : getLabelFromMap('400028',helpMap,'Leyendo datos ...')
       
       });
    
    idUnidad.load({
            params: {
        cdTabla: 'TUNIDAD'
        }
           
        });

        
        
         
function validaStatusCaso()
    
{
        var params = {
       
                 nmCaso: el_form.findById('nmCaso_Id').getValue()
                        
             };
      //  startMask(el_form.id,"Validando datos...");
        execConnection (VALIDA_SATUS_CASO, params, cbkValida);

}
function cbkValida (_success, _message) {
    //endMask();
    /*if (success) {
        //  Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), message,function(){_window.close();reloadEnc();});//recargaEnc
    	     Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), message,function(){_window.close();reloadEnc();});//recargaEnc
    } else {
          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), message);
          guardarDatos();     
    }*/
    
    if (!_success) {
			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
		}else {
			//Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function() {reloadGrid();});
			//Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message);
			guardarDatos(); 
		}

} 
                
        
        
function guardarDatos()

    
{
        var params = {
       
                 nmCaso: el_form.findById('nmCaso_Id').getValue(), 
                 tarea: el_form.findById('tarea_Id').getValue(),
                 cdUsuario: _usuario,
                 nivAtencion: _nivatn,
                 tCompra: el_form.findById('tCompra_Id').getValue(),
                 tUnidad: Ext.getCmp('cmbUnidad').getValue()                 
             };
        startMask(el_form.id,"Guardando datos...");
        execConnection (_ACTION_GUARDAR_COMPRA_TIEMPO, params, cbkGuardar);

}
function cbkGuardar (success, message) {
    endMask();
    if (success) {
          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), message,function(){_window.close();reloadEnc();});//recargaEnc
    } else {
          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), message);
    }

}


}


function recargaEnc(){
	storeEncCasoDetalle.load({
		params:{cdmatriz: CDMATRIZ,
				pv_nmcaso_i:NMCASO
				},
		success:function(){
			reloadGrid(NMCASO,CDMATRIZ);
			Ext.getCmp('movimientoId').setValue(storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].nmovimiento);
			if (storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].color == 'red')
				{
				Ext.getCmp('txtVigenteId').getEl().dom.innerHTML='&nbsp; &nbsp; &nbsp; Vigente <img src="../resources/images/red.bmp" alt="hola"/>';
				}
			if (storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].color == 'green')
				{
				Ext.getCmp('txtVigenteId').getEl().dom.innerHTML='&nbsp; &nbsp; &nbsp; Vigente <img src="../resources/images/green.bmp" alt="hola"/>';
				}
			if (storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].color == 'yellow')
				{
				Ext.getCmp('txtVigenteId').getEl().dom.innerHTML='&nbsp; &nbsp; &nbsp; Vigente <img src="../resources/images/yellow.bmp" alt="hola"/>';				
				}			
			}
		});
		_window.close();
}
// FUNCION VALIDA COMPRA TIEMPO 

function validaCompraTiempo(_nmcaso,_nivatn, _proceso)
{
       //alert(Ext.getCmp('cmbUnidad').getValue());
        var params = {
       
				 pv_cdproceso_i: _proceso,       
                 pv_cdnivatn_i: _nivatn,
                 nmcaso: _nmcaso 
                               
                                  
             };
        execConnection (_ACTION_VALIDACION_COMPRA_TIEMPO, params, cbkValidaCompraTiempo);
}

function cbkValidaCompraTiempo (success, message, _response) {
	//console.log(_response);
    //alert('Aviso'+Ext.util.JSON.decode(_response).messageResult);
    //if (Ext.util.JSON.decode(_response).flagValida == 1) {
    if (success) {
    	if (Ext.util.JSON.decode(_response).messageResult == 1){ 
          comprarTiempo(NMCASO,Ext.getCmp("cdnivatn").getValue(),Ext.getCmp("cdproceso").getValue(),Ext.getCmp("cdusuario").getValue())
          }
        else { Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), message)}
    } else {
          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), message);
    }
    

}

function transformarMinuto(valor,unidad){
	var aux;
	if (unidad === 'M' || unidad === 'MINUTOS') {
		return valor;
	};
	if (unidad === 'H' || unidad === 'HORAS') {
		aux = valor * 60;
		return aux;
	};
	if (unidad === 'D' || unidad === 'DIAS') {
		aux = valor * 1440;
		return aux;
	};
	if (unidad === 'S' || unidad === 'SEMANAS') {
		aux = valor * 10080;
		return aux;
	};
}
			