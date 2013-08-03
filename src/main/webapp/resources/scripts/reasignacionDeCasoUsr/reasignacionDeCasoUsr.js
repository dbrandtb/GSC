Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";
var codUsuMov = new Ext.form.Hidden( {
                disabled: false,
                name: 'codUsuMov',
                id: 'codUsuMovId',
                value: CDUSERMOV
            });

var codigoUsuario = new Ext.form.Hidden( {
                disabled: false,
                name: 'codigoUsuario',
                id: 'codigoUsuarioId',
                value: COD_USUARIO
            });
            
 
var desUsrRsgncn = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('desUsrRsgncn',helpMap,'Usuario'),
        tooltip:getToolTipFromMap('desUsrRsgncn',helpMap,'Usuario'),
        hasHelpIcon:getHelpIconFromMap('desUsrRsgncn',helpMap),								 
   		Ayuda: getHelpTextFromMap('desUsrRsgncn',helpMap),
   		
        id: 'desUsrRsgncn', 
        name: 'ddesUsrRsgncnId',
        //allowBlank: true,
        //anchor: '100%',
        
        width: 300,
        value: DES_USUARIO
    }); 

            
var desUsrRspnsbl = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('desUsrRspnsblId',helpMap,'Usuario'),
        tooltip:getToolTipFromMap('desUsrRspnsblId',helpMap,'Usuario'),
        hasHelpIcon:getHelpIconFromMap('desUsrRspnsblId',helpMap),								 
   		Ayuda: getHelpTextFromMap('desUsrRspnsblId',helpMap), 
        id: 'desUsrRspnsblId', 
        name: 'desUsrRspnsbl',
        allowBlank: true,
		width: 160
    });

//parte de arriba
var dtsUsr = new Ext.FormPanel({
        id: 'dtsUsr',
        renderTo: 'formBusqueda',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('23',helpMap,'Reasignacion de Caso')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        url: _ACTION_BUSCAR_REASIGNACION_CASO_USUARIO,
        frame: true,   
        width: 760,
        height: 110,
        items: [{
                layout:'form',
                border: false,
                items:[
                		{
		                labelWidth: 100,
		                layout: 'form',
		                title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Datos del Usuario</span><br>',
		                frame:true,
		                baseCls: '',
		                buttonAlign: "center",
		                        items: [
		                            	{
		                                layout:'column',
		                                border:false,
		                                columnWidth: 1,
		                                items:[
			                                    {
					                                columnWidth: .12,
					                                layout: 'form',
					                                html: '<span style="">&nbsp;</span>'
			                                    },
			                                    {
			                                        columnWidth:.6,
			                                        layout: 'form',
			                                        border: false,
			                                        items:[                                     
			                                                codUsuMov,
			                                                codigoUsuario,
						                                    desUsrRsgncn                                   
			                                            ]
			                                    },
			                                    {
			                                        columnWidth:.2,
			                                        layout: 'form',
					                                html: '<span style="">&nbsp;</span>'                                        
			                                    }
		                                	  ]
		                            	}
		                            	]
                          }
                        ]
                }
               ]  

});   
var TopicRecord = Ext.data.Record.create([
    {name: 'nmCaso', mapping: 'nmCaso',  type: 'string'},
    {name: 'cdProceso',  mapping: 'cdProceso', type: 'string'},
    {name: 'dsProceso',  mapping: 'desProceso', type: 'string'},
    {name: 'color', mapping: 'color', type: 'string'},
    {name: 'cdRolMat', mapping: 'cdRolmat', type: 'string' },
    {name: 'desRolMat', mapping: 'desRolmat', type: 'string' }
]);

function addDeVolver(parametro){
	//alert(parametro.get('numCaso'));
	var myNewRecord = new TopicRecord({
		nmCaso: parametro.get('numCaso'),
		cdProceso: parametro.get('codProceso'),
		dsProceso: parametro.get('desProceso'),
		color: parametro.get('elColor'),
		cdRolMat: parametro.get('codRolMat'),
		desRolMat: parametro.get('dscrRolMat')
	    /*nmCaso: 'H',
	    cdProceso: 'O',
	    dsProceso: 'L',
	    color: 'A',
	    cdRolMat: 'M',
	    desRolMat: 'U'*/
	});
el_storeIzquierdo.add(myNewRecord);	
};

// Definicion de las columnas de la grilla de la izquierda
var cmCaso = new Ext.grid.ColumnModel([
        {
        dataIndex: 'cdProceso',
        hidden :true
        },
        {
        dataIndex: 'cdRolMat',
        hidden :true
        },
        {
        header:getLabelFromMap('cmRsgncnCsUsr',helpMap,'N&uacute;mero de Caso'),
        tooltip:getToolTipFromMap('cmRsgncnCsUsr',helpMap,'N&uacute;mero de Caso'),
        dataIndex: 'nmCaso',
        sortable: true,
        width: 100
        },
        {
        header:getLabelFromMap('cmRsgncnCsUsr',helpMap,'Tarea Asociada'),
        tooltip:getToolTipFromMap('cmRsgncnCsUsr',helpMap,'Tarea Asociada'),
        dataIndex: 'dsProceso',
        sortable: true,
        width: 92
        },
        {
        header:getLabelFromMap('cmRsgncnCsUsr',helpMap,'Vigencia'),
        tooltip:getToolTipFromMap('cmRsgncnCsUsr',helpMap,'Vigencia'),
        dataIndex: 'color',
        renderer:cambiarColor,
        sortable: true,
        width: 56
        },
        {
        header:getLabelFromMap('cmRsgncnCsUsr',helpMap,'Rol en Caso'),
        tooltip:getToolTipFromMap('cmRsgncnCsUsr',helpMap,'Rol en Caso'),
        dataIndex: 'desRolMat',
        sortable: true,
        width: 115
        }
]);
      
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
      
      
      
      
      

var gridCaso;

function createGridIzq(){
       gridCaso= new Ext.grid.GridPanel({
            id: 'gridCaso',
            el: 'gridCasoUsr',
            store: el_storeIzquierdo,
            title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Caso</span>',
            reader: elJsonGrillaCasoUsr,
            border: true,
            cm: cmCaso,
            clicksToEdit: 1,
            successProperty: 'success',
            width: 355,
            frame: true,
            height: 530,
            sm: new Ext.grid.RowSelectionModel({singleSelect: false}),
            stripeRows: true,
            collapsible: true,
            bbar: new Ext.PagingToolbar({
                    pageSize:20,
                    store: el_storeIzquierdo,
                    displayInfo: false,
                    displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
                    emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
                })          
        });
   gridCaso.render()
}

createGridIzq();

//botones del centro 
var formBtnChc =  new Ext.FormPanel({
    renderTo: 'formBtnChc',       
    width: 40,
    bodyStyle: 'background: white',
    layout: 'table', 
    height: 500, 
    border : false,
    layoutConfig: {columns: 1},
    items:[

           	{
             width: 38,
             height: 390,
             buttonAlign: 'center',  
             border: false,        
             buttons:[
              			{
              				text:getLabelFromMap('btnIzqSim',helpMap,'>'),
              				id: 'btnIzqSim',
              				tooltip:getToolTipFromMap('btnIzqSim',helpMap,'Asignar Caso'),
              				handler: function() {
              							if(!gridCaso.getSelectionModel().getSelected())
              							{
                                 			Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operaci&oacute;n');              								
              							}else {
              								var numero=gridCaso.getSelectionModel().getSelections().length;
              								if ((numero>0)&&(numero<2))
											{
												addGridNewRecord(gridCaso.getSelectionModel().getSelected());
												el_storeIzquierdo.remove(gridCaso.getSelectionModel().getSelected());
											}else {
                                 				Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operaci&oacute;n');
                                 			}
                                 		}
                				}
              			}
       				]
           	},
   			{
              width: 38,
              height: 20,
              buttonAlign: 'center',           
              border: false,              
              buttons:[
	          			{
              				text:getLabelFromMap('btnIzqVar',helpMap,'>>'),
              				id: 'btnIzqVar',
              				tooltip:getToolTipFromMap('btnIzqVar',helpMap,'Asignar Casos'),
              				handler: function() {
              							var elegidos=gridCaso.getSelectionModel().getSelections();
              							var numero=elegidos.length;
							            if ((numero== 0) || ((numero>0)&&(numero<2)))
              							{
                                 			Ext.MessageBox.alert('Aviso','Debe seleccionar mas de un registro para realizar esta operaci&oacute;n');              								
              							}else {
              								//alert(1);
          									for (var i=0; i<numero; i++)
          									{
                       							addGridNewRecord(elegidos[i]);
                        						el_storeIzquierdo.remove(elegidos[i]);
          									}							
                                 		}
                 				}
               			}
               		   ]
           	},
   			{
              width: 38,
              height: 20,
              buttonAlign: 'center',  
              border: false,               
              buttons:[
	          			{
               				text:getLabelFromMap('btnDerSim',helpMap,'<'),
               				id: 'btnDerSim',
               				tooltip:getToolTipFromMap('btnDerSim',helpMap,'Desasignar Caso'),
               				handler: function() {
	             				if (!gridCsAsgnds.getSelectionModel().getSelected()) {
										Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));	               
	   							}else{
	   								 	var numero=gridCsAsgnds.getSelectionModel().getSelections().length;
             							if ((numero>0)&&(numero<2))
										{
											/*addGridNewRecord(gridCaso.getSelectionModel().getSelected());
											el_storeIzquierdo.remove(gridCaso.getSelectionModel().getSelected());*/
											var parametro = gridCsAsgnds.getSelectionModel().getSelected();
	   								 		addDeVolver(parametro);
	   										gridCsAsgnds.store.remove(parametro);

										}else {
                                				Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operaci&oacute;n');
                                		}
	   								}      
                			}
               			}
          				]
           	},
     		{
	       	  width: 38,
              height: 20,	       	  
              border: false, 
			  buttonAlign: 'center',           
              buttons:[
	          			{
	           				text:getLabelFromMap('btnDerVar',helpMap,'<<'),
	           				id: 'btnDerVar',
	           				tooltip:getToolTipFromMap('btnDerVar',helpMap,'Desasignar Casos'),
	           				handler: function() {
              							var devolver=gridCsAsgnds.getSelectionModel().getSelections();
              							var total=devolver.length;
							            if ((total == 0) || ((total>0)&&(total<2)))
              							{
                                 			Ext.MessageBox.alert('Aviso','Debe seleccionar mas de un registro para realizar esta operaci&oacute;n');              								
              							}else {
              								//alert(1);
          									for (var i=0; i<total; i++)
          									{
                       							addDeVolver(devolver[i]);
                        						gridCsAsgnds.store.remove(devolver[i]);
          									}							
                                 		}
	               			}
	           			}
             			]
            } 
         ]
});

Ext.getCmp('btnIzqSim').getEl().dom.style.width = "26px";
Ext.getCmp('btnIzqVar').getEl().dom.style.width = "26px";
Ext.getCmp('btnDerSim').getEl().dom.style.width = "26px";
Ext.getCmp('btnDerVar').getEl().dom.style.width = "26px";
formBtnChc.render();

//busqueda de usuario responsable
var usrRspnsblForm = new Ext.FormPanel({
        id: 'usrRspnsblForm',
        renderTo: 'formBusquedaDer',
        bodyStyle: 'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame: true,   
        url: _ACTION_BUSCAR_REASIGNACION_CASO_USUARIO_RSPNSBL,
        width: 355,
        height: 130,
        items: [{
                layout:'form',
                border: false,
                items:[
                	{
                    bodyStyle:'background: white',
                    labelWidth: 50,
                    layout: 'form',
                    title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Usuario Responsable</span><br>',
                    frame:true,
                    baseCls: '',
                    items: [
                    	desUsrRspnsbl 
					],
                    buttonAlign: "center",
                    buttons:[
                            {
                            text:getLabelFromMap('rsgncnCsUsrButtonBuscar',helpMap,'Buscar'),
                            tooltip:getToolTipFromMap('rsgncnCsUsrButtonBuscar',helpMap,'Busca Usuario Responsable'),
                            handler: function() {
                                if (usrRspnsblForm.form.isValid()) {
                                       if (gridUsrRspnsbl!=null) {
                                        reloadGrid();
                                       } else {
                                        createGridUsrRspnsbl();
                                       }
                                } else{
                                    Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                                    }
                              }
                            },
                            {
                            text:getLabelFromMap('rsgncnCsUsrButtonCancelar',helpMap,'Cancelar'),
                            tooltip:getToolTipFromMap('rsgncnCsUsrButtonCancelar',helpMap,'Cancela Busqueda Usuario Responsable'),                             
                            handler: function() {
                                usrRspnsblForm.form.reset();
                            }
                        }]
                	}]
            }]              
});   
usrRspnsblForm.render();
// Definicion de las columnas de la grilla de la derecha
var cmUsrRspnsbl = new Ext.grid.ColumnModel([
        {
        dataIndex: 'cdUsuario',
        hidden :true
        },
        {
        header:getLabelFromMap('cmDsEttsEsttCs',helpMap,'Usuario'),
        tooltip:getToolTipFromMap('cmDsEttsEsttCs',helpMap,'Usuario'),
        dataIndex: 'desUsuario',
        sortable: true,
        width: 160
        },
        {
        header:getLabelFromMap('cmDsEttsEsttCs',helpMap,'Casos Asignados'),
        tooltip:getToolTipFromMap('cmDsEttsEsttCs',helpMap,'Casos Asignados'),
        dataIndex: 'caso',
        sortable: true,
        width: 200
        }
]);

var gridUsrRspnsbl;

function createGridUsrRspnsbl(){
       gridUsrRspnsbl= new Ext.grid.GridPanel({
            id: 'gridUsrRspnsbl',
            el: 'gridCasoUsrRspnsbl',
            store: el_storeDerecha,
            //title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;"></span>',
            reader: elJsonGrillaUsrRspnsbl,
            border: true,
            cm: cmUsrRspnsbl,
            clicksToEdit: 1,
            successProperty: 'success',
            width:355,
            frame:true,
            height:200,
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
            stripeRows: true,
            collapsible: true,
            bbar: new Ext.PagingToolbar({
                    pageSize:20,
                    store: el_storeDerecha,
                    displayInfo: false,
                  
                    displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
                    emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
                })          
        });
   gridUsrRspnsbl.render()
}

createGridUsrRspnsbl();


// Definicion de las columnas de la grilla de la derecha abajo
var cmCsAsgnds = new Ext.grid.ColumnModel([

        {
        dataIndex: 'codProceso',
        hidden :true
        },
        {
        dataIndex: 'codRolMat',
        hidden :true
        },
        {
        header:getLabelFromMap('cmRsgncnCsUsr',helpMap,'N&uacute;mero de Caso'),
        tooltip:getToolTipFromMap('cmRsgncnCsUsr',helpMap,'Nombre del formato documento'),
        dataIndex: 'numCaso',
        sortable: true,
        width: 115,
        resizable: true,
        editor: new Ext.form.TextField({
		           				allowBlank: true,
		           				id: 'numCaso'
		           		})  
        },
        {
        header:getLabelFromMap('cmRsgncnCsUsr',helpMap,'Tarea Asignada'),
        tooltip:getToolTipFromMap('cmRsgncnCsUsr',helpMap,'Indicador de Aviso'),
        dataIndex: 'desProceso',
        sortable: true,
        width: 125,
        resizable: true,
        editor: new Ext.form.TextField({
		           				allowBlank: true,
		           				id: 'desProceso'
		           		})         
        },
        {
        header:getLabelFromMap('cmRsgncnCsUsr',helpMap,'Rol en Caso'),
        tooltip:getToolTipFromMap('cmRsgncnCsUsr',helpMap,'Indicador de Aviso'),
        dataIndex: 'dscrRolMat',
        sortable: true,
        width: 100,
        resizable: true,
        editor: new Ext.form.TextField({
		           				allowBlank: true,
		           				id: 'dscrRolMat'
		           		})         
        }
]);


var recordGrilla=new Ext.data.Record.create([
    {name: 'numCaso', mapping: 'nmCaso',  type: 'string'},
    {name: 'codProceso',  mapping: 'cdProceso', type: 'string'},
    {name: 'desProceso',  mapping: 'dsProceso', type: 'string'},
    {name: 'elColor', mapping: 'color', type: 'string'},    
    {name: 'codRolMat', mapping: 'cdRolMat', type: 'string' },
    {name: 'dscrRolMat', mapping: 'desRolMat', type: 'string' }
]);

function addGridNewRecord (record) {
	//alert(record.get('nmCaso'));
	var new_record = new recordGrilla({
						numCaso: record.get('nmCaso'),
						codProceso: record.get('cdProceso'),
						desProceso: record.get('dsProceso'),
						elColor: record.get('color'),
						codRolMat: record.get('cdRolMat'),
						dscrRolMat: record.get('desRolMat')
					});
	gridCsAsgnds.stopEditing();
	gridCsAsgnds.store.insert(0, new_record);
	//gridCsAsgnds.startEditing(0, 0);
	gridCsAsgnds.getSelectionModel().selectRow(0);
};

var gridCsAsgnds;
function createGridCsAsgnds(){

 gridCsAsgnds= new Ext.grid.GridPanel({
        id: 'gridCsAsgndsId',
        title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Casos Asignados</span>',
        store: el_storeGrillaCasoAsgnds,
        reader: elJsonGrillaCasoAsgnds,
        renderTo: 'gridCasoAsignados',
        //border:true,
        cm: cmCsAsgnds,
        clicksToEdit: 1,
        successProperty: 'success',
        width: 355,
        frame: true,
        height: 197,
        sm: new Ext.grid.RowSelectionModel({singleSelect: false}),
        stripeRows: true,
        collapsible: true   
        
    });
}
createGridCsAsgnds();

var rsgncCsGrdBttn = new Ext.Panel({
  layout:'form',
  renderTo: 'formBtnGrd', 
  borderStyle:true, 
  bodyStyle:'background: white',
  width: 760,
  height: 50, 
  border: false,  
  items:[ 
          {
		  buttonAlign: "center",
		  border: false,			
          buttons:[
               {
					text:getLabelFromMap('admCmbBsqBttnGuardar', helpMap,'Guardar'),
					tooltip:getToolTipFromMap('admCmbBsqBttnGuardar', helpMap,'Guarda la Reasignacion de Caso'),			        			
                   handler: function(){
                   		gridCsAsgnds.getSelectionModel().selectAll();
                   		var agrabar=gridCsAsgnds.getSelectionModel().getSelections();
              			var ttl=agrabar.length;
                        if (getSelectedKey(gridUsrRspnsbl, "cdUsuario") != "") {
                        	if (ttl!=0){
                        		//alert(1);
                        		//guardarReasignacionDeCasoUsr(getSelectedKey(gridUsrRspnsbl, "cdUsuario"));
                        		validaStatusCaso(getSelectedKey(gridUsrRspnsbl, "cdUsuario"));
                        		
                        	}else{
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe tener casos asignados para realizar esta operaci&oacute;n'));                        	
                        	}
                        } else {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un nuevo usuario para realizar esta operaci&oacute;n'));
                        }

                   }
               },
               {
					text:getLabelFromMap('admCmbBsqBttnRegresar', helpMap,'Regresar'),
					tooltip:getToolTipFromMap('admCmbBsqBttnRegresar', helpMap,'Regresa a la Pantalla Anterior'),			        			
                   handler:function(){
						window.location=_ACTION_REGRESAR_A_CONFIGURAR_MATRIZ_TAREA+'?cdmatriz='+ CODMATRIZ; 
						
                   }
               }
               ]
              }
        ]
});

rsgncCsGrdBttn.render();

/*
usuarioMov
usuarioOld
usuarioNew
estado
listaCasos
*/
function guardarReasignacionDeCasoUsr (key) {

 /* var _params = '';	
  _params +="usuarioMov="+Ext.getCmp('codUsuMovId').getValue()+"&"+
  			"&usuarioOld="+Ext.getCmp('codigoUsuarioId').getValue()+"&"+
  			"&usuarioNew="+ key +"&"+
  			"&estado="+ASIGNACASO+"&";*/
  startMask(rsgncCsGrdBttn.id,"Guardando datos...");
  			
  var _params = '';	
  var recs = gridCsAsgnds.getSelectionModel().getSelections();
  var limpiar=recs.length;
  //gridCsAsgnds.stopEditing();
  for (var i=0; i<recs.length; i++) {
   _params +=  "csoGrillaListReasignacionCasoUsr["+i+"].nmCaso="+recs[i].get('numCaso')+"&"+
   "&csoGrillaListReasignacionCasoUsr["+i+"].cdUsuMov="+Ext.getCmp('codUsuMovId').getValue()+"&"+
   "&csoGrillaListReasignacionCasoUsr["+i+"].cdUsuarioOld="+Ext.getCmp('codigoUsuarioId').getValue()+"&"+
   "&csoGrillaListReasignacionCasoUsr["+i+"].cdUsuarioNew="+ key +"&";
  }

   if (recs.length > 0) {
   var conn = new Ext.data.Connection ();
   conn.request({
     url: _ACTION_GUARDAR_REASIGNACION_CASO_USUARIO,
     params: _params,
     method: 'POST',
     callback: function (options, success, response) {
     	 //gridCsAsgnds.getSelectionModel().clearSelections();
         if (Ext.util.JSON.decode(response.responseText).success == false) {
          	Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), Ext.util.JSON.decode(response.responseText).errorMessages[0]);
         } else {
          	Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), Ext.util.JSON.decode(response.responseText).actionMessages[0],function(){reloadGridIzq(); reloadGrid()});          
         	for (var j=0; j<limpiar; j++)
	        {
	          	gridCsAsgnds.store.remove(recs[j]);
	        }	
         }
         endMask();         
       }
   });
  }
};


if (COD_USUARIO!="")
{
	//alert(1);
	reloadGridIzq();
}


//valida status


         
function validaStatusCaso(clave)    
{  
	var recs = gridCsAsgnds.getSelectionModel().getSelections();
	var limpiar=recs.length;
	 var params = '';	
	
	for (var i=0; i<recs.length; i++) {
      params +=  "csoGrillaListReasignacionCasoUsr["+i+"].nmCaso="+recs[i].get('numCaso')+"&"
      //execConnection (VALIDA_SATUS_CASO, params, cbkValida);
     }
	
    if (recs.length > 0) {
   var conn = new Ext.data.Connection ();
   conn.request({
     url: VALIDA_SATUS_CASO,
     params: params,
     method: 'POST',
     callback: function (options, success, response) {
     	 //gridCsAsgnds.getSelectionModel().clearSelections();
         if (Ext.util.JSON.decode(response.responseText).success == false) {
          	Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), Ext.util.JSON.decode(response.responseText).errorMessages[0]);
         	// alert(2);
         } else {
          //	Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), Ext.util.JSON.decode(response.responseText).actionMessages[0],function(){reloadGridIzq(); reloadGrid()});          
         	//Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), Ext.util.JSON.decode(response.responseText).actionMessages[0]);
         	guardarReasignacionDeCasoUsr(clave);
         //	alert(1);
         		
         }
         //endMask();         
       }
   });
  } 
     
     /*   var params = {
       
                nmCaso: Ext.getCmp('numCaso').getValue()
                        
             };
      */
       // execConnection (VALIDA_SATUS_CASO, params, cbkValida);

}
/*
function cbkValida (_success, _message) {
    
    
    if (!_success) {
			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
			alert(1);
		}else {
			//Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function() {reloadGrid();});
			Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message);
			guardarReasignacionDeCasoUsr(clave);
		}

}*/ 
                
        

});

function reloadGridIzq(){
	var _params = {
       		cdUsuario: Ext.getCmp('dtsUsr').form.findField('codigoUsuarioId').getValue()       
 	};
	reloadComponentStore(Ext.getCmp('gridCaso'), _params, cbkReloadIzq);
}

function cbkReloadIzq(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}
}



function reloadGrid(){
	var _params = {
       		desUsuario: Ext.getCmp('usrRspnsblForm').form.findField('desUsrRspnsblId').getValue(),
       		cdUsuarioOld: COD_USUARIO
 	};
	reloadComponentStore(Ext.getCmp('gridUsrRspnsbl'), _params, cbkReload);
}





function cbkReload(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}
}






