Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

var USUARIO_LOG='';


var codUsuMov = new Ext.form.Hidden( {
                disabled: false,
                name: 'codUsuMov',
                id: 'codUsuMovId',
                value: USUARIO_LOG
            });

var numCaso = new Ext.form.Hidden( {
                disabled: false,
                name: 'numCaso',
                id: 'numCasoId',
                value: NUMERO_CASO
            });
            
            
var comboModulos = new Ext.form.ComboBox(
{
    tpl: '<tpl for="."><div ext:qtip="{codigoModulo}.{descriModulo}" class="x-combo-list-item">{descriModulo}</div></tpl>',
    store: descModulos,
    id: 'comboModulosId',
    fieldLabel: getLabelFromMap('comboModulosId',helpMap,'M&oacute;dulo'),
    tooltip: getToolTipFromMap('comboModulosId',helpMap,'Nombre M&oacute;dulo'),
    hasHelpIcon:getHelpIconFromMap('comboModulosId',helpMap),								 
    Ayuda: getHelpTextFromMap('comboModulosId',helpMap),    
    width: 220,
    allowBlank: false,
    displayField: 'descriModulo',
    valueField: 'codigoModulo',
    hiddenName: 'codiModulos',
    typeAhead: true,
    triggerAction: 'all',
    emptyText: 'Seleccione Modulo...',
    selectOnFocus: true,
    mode: 'local',
    forceSelection: true,
    onSelect: function (record) {
       this.setValue(record.get('codigoModulo'));
       el_storeIzquierdo.removeAll();
       buscarUsrMdl(record.get('codigoModulo'));    
       this.collapse();
	}
}
);


var comboRoles = new Ext.form.ComboBox(
{
    tpl: '<tpl for="."><div ext:qtip="{id}.{texto}.{descripLarga}" class="x-combo-list-item">{texto}</div></tpl>',
    store: descRoles,
    id:'comboRolesId',
    displayField:'texto', 
    valueField: 'id', 
    hiddenName: 'cdRolmat',
    typeAhead: true, 
    triggerAction: 'all', 
    lazyRender: true, 
    allowBlank: false,
    emptyText: 'Seleccione Rol...', 
    selectOnFocus: true,
    name: 'cdRolmat',
    id:'comboRolesId',
    forceSelection: true,
    listeners: {
		focus: function () {
			if (!this.list) {
				this.clearValue();
				this.setValue("");
				this.setRawValue("");
			}else{
				if (this.getRawValue().indexOf("&nbsp;")){
					this.clearValue();
					this.setValue("");
					this.setRawValue("");
	 			}
			} 
		}
	}
});
   


var admCmbBsqd = new Ext.FormPanel({
        id: 'incisosFormEstatus',
        renderTo: 'formBusqueda',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('incisosFormEstatus', helpMap,'Reasignaci&oacute;n de Caso')+'</span>',        
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,   
        width: 600,
        height:110,
        items: [{
                layout:'form',
                border: false,
                items:[
                		{
		                labelWidth: 100,
		                layout: 'form',
		                title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Asignaci&oacute;n</span><br>',
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
			                                                numCaso,
			                                                codUsuMov,
						                                    comboModulos                                   
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

descModulos.load({
            params: {
    				cdTabla: 'CATBOMODUL'
    				}	
});

var TopicRecord = Ext.data.Record.create([
    {name: 'cdUsuario', mapping: 'cdUsuario',  type: 'string'},
    {name: 'desUsuario',  mapping: 'desUsuario', type: 'string'},
    {name: 'cdModulo',  mapping: 'cdModulo', type: 'string'}
]);

function addDeVolver(parametro){
	var myNewRecord = new TopicRecord({
		cdUsuario: parametro.get('cdUsuario'),
		desUsuario: parametro.get('desUsrRsp'),
		cdModulo: parametro.get('cdModulo')
	});
el_storeIzquierdo.add(myNewRecord);	
};


var cmUsrMdl = new Ext.grid.ColumnModel([
        {
        dataIndex: 'cdUsuario',
        hidden: true
        },
        {
        header:getLabelFromMap('cmRsgncnCsUsr',helpMap,'Usuario'),
        tooltip:getToolTipFromMap('cmRsgncnCsUsr',helpMap,'Usuario'),
        hasHelpIcon:getHelpIconFromMap('cmRsgncnCsUsr',helpMap),								 
        Ayuda: getHelpTextFromMap('cmRsgncnCsUsr',helpMap),        
        dataIndex: 'desUsuario',
        sortable: true,
        width: 180
        }
]);


var gridUsrPrMdl;

function createGridUsrPrMdl(){
       gridUsrPrMdl= new Ext.grid.GridPanel({
            id: 'gridUsrPrMdl',
            el: 'grillaIzqUsrPrMdl',
            store: el_storeIzquierdo,
            title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Usuarios Por M&oacute;dulo</span>',
            reader: elJsonReasignacionCaso,
            border: true,
            cm: cmUsrMdl,
            clicksToEdit: 1,
            successProperty: 'success',
            width: 270,
            frame: true,
            height: 320,
            sm: new Ext.grid.RowSelectionModel({singleSelect: false}),
            stripeRows: true,
            collapsible: true
        });
   gridUsrPrMdl.render()
}

createGridUsrPrMdl();

var formBtnChc =  new Ext.FormPanel({
    renderTo: 'formBtnChc',       
    width: 38,
    bodyStyle: 'background: white',
    layout: 'table', 
    height: 320, 
    border : false,
    layoutConfig: {columns: 1},
    items:[
           	{
             width: 38,
             height: 100,
             buttonAlign: 'center',  
             border: false,        
             buttons:[
              			{
             				id: 'btnIzqSim',
              				text:getLabelFromMap('btnIzqSim',helpMap,'>'),
              				tooltip:getToolTipFromMap('btnIzqSim',helpMap,'Asignar Caso'),
              				handler: function() {
              							if(!gridUsrPrMdl.getSelectionModel().getSelected())
              							{
										    Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));	               
              							}else {
              								var numero=gridUsrPrMdl.getSelectionModel().getSelections().length;
              								if ((numero>0)&&(numero<2))
											{
												addGridNewRecord(gridUsrPrMdl.getSelectionModel().getSelected());
												el_storeIzquierdo.remove(gridUsrPrMdl.getSelectionModel().getSelected());
											}else {
										           Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));	               
                                 			      }
                                 		      }
                				           }
              			}
       				]
           	},
   			{
              width: 38,
              height: 60,
              buttonAlign: 'center',           
              border: false,   
              buttons:[{   
              				id: 'btnIzqVar',
              				text:getLabelFromMap('btnIzqVar',helpMap,'>>'),
              				tooltip:getToolTipFromMap('btnIzqVar',helpMap,'Asignar Casos'),
              				handler: function() {
              							var elegidos=gridUsrPrMdl.getSelectionModel().getSelections();
              							var numero=elegidos.length;
							            if ((numero== 0) || ((numero>0)&&(numero<2)))
              							{
										    Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));	               
              							}else {
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
              height: 60,
              buttonAlign: 'center',  
              border: false,    
              buttons:[
	          			{
               				id: 'btnDerSim',
               				text:getLabelFromMap('btnDerSim',helpMap,'<'),
               				tooltip:getToolTipFromMap('btnDerSim',helpMap,'Desasignar Caso'),
               				handler: function() {
	             				if (!gridUsrRol.getSelectionModel().getSelected()) {
										Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));	               
	   							}else{
	   								 	var numero=gridUsrRol.getSelectionModel().getSelections().length;
             							if ((numero>0)&&(numero<2))
										{
											var parametro = gridUsrRol.getSelectionModel().getSelected();
	   								 		addDeVolver(parametro);
	   										gridUsrRol.store.remove(parametro);
											
										}else{
										      Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));	               
                                		      }
	   								}      
                			}
               			}
          				]           
           	},
     		{
	       	  width: 38,
              height: 60,	       	  
              border: false, 
			  buttonAlign: 'center', 
			  buttons:[
	          			{
	           				id: 'btnDerVar',
	           				text:getLabelFromMap('btnDerVar',helpMap,'<<'),
	           				tooltip:getToolTipFromMap('btnDerVar',helpMap,'Desasignar Casos'),
	           				handler: function() {
              							var devolver=gridUsrRol.getSelectionModel().getSelections();
              							var total=devolver.length;
							            if ((total == 0) || ((total>0)&&(total<2)))
              							{
										      Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));	               
                                 			
              							}else {
          									for (var i=0; i<total; i++)
          									{
                       							addDeVolver(devolver[i]);
                        						gridUsrRol.store.remove(devolver[i]);
          									}							
                                 		}
	               			}
	           			}
             			]          
            } 
         ]
}); 

formBtnChc.render();
Ext.getCmp('btnIzqSim').getEl().dom.style.width = "26px";
Ext.getCmp('btnIzqVar').getEl().dom.style.width = "26px";
Ext.getCmp('btnDerSim').getEl().dom.style.width = "26px";
Ext.getCmp('btnDerVar').getEl().dom.style.width = "26px";

// Definicion de las columnas de la grilla de la derecha
var cmDerecha = new Ext.grid.ColumnModel([
        {
        dataIndex: 'cdUsuario',
        hidden : true
        },
        {
        header:getLabelFromMap('cmUsrRspRsgCaso',helpMap,'Usuarios Responsables'),
        tooltip:getToolTipFromMap('cmUsrRspRsgCaso',helpMap,'Usuarios Responsables'),
        hasHelpIcon:getHelpIconFromMap('cmUsrRspRsgCaso',helpMap),								 
        Ayuda: getHelpTextFromMap('cmUsrRspRsgCaso',helpMap),
        
        dataIndex: 'desUsrRsp',
        sortable: true,
        width: 150,
        resizable: true,
        editor: new Ext.form.TextField({
		           				allowBlank: false,
		           				readOnly:true,
		           				id: 'desUsrRsp'
		           		})  
        },
        {
        header:getLabelFromMap('cmRolRsgCaso',helpMap,'Rol'),
        tooltip:getToolTipFromMap('cmRolRsgCaso',helpMap,'Rol del Usuario'),
        hasHelpIcon:getHelpIconFromMap('cmUsrRspRsgCaso',helpMap),								 
        Ayuda: getHelpTextFromMap('cmUsrRspRsgCaso',helpMap),
        
        dataIndex: 'desRol',
        sortable: true,
        width: 110,
        editor: comboRoles,
        renderer: renderComboEditor(comboRoles)
        }
]);


var recordGrilla=new Ext.data.Record.create([
  {name: 'codUsuario',  mapping: 'cdUsuario',  type: 'string'},
  {name: 'descUsrRsp',  mapping: 'desUsrRsp', type: 'string'},
  {name: 'cdModulo',  mapping: 'cdModulo', type: 'string'},
  {name: 'cdRolmat',  mapping: 'cdRolmat',  type: 'string'}                
]);

function addGridNewRecord (elUsuario) {
	var new_record = new recordGrilla({
						cdUsuario: elUsuario.get('cdUsuario'),
						desUsrRsp: elUsuario.get('desUsuario'),
						cdModulo: elUsuario.get('cdModulo'),
						cdRolmat: ''
					});
	gridUsrRol.stopEditing();
	gridUsrRol.store.insert(0, new_record);
	gridUsrRol.startEditing(0, 0);
	gridUsrRol.getSelectionModel().selectRow(0);
	
};

var gridUsrRol;
function createGrid(){

 gridUsrRol= new Ext.grid.EditorGridPanel({
        id: 'gridUsrRolId',
        store: el_storeGrilla,
        reader: elJsonReasignacionCaso,
        renderTo: 'gridElementos',
        //border:true,
        cm: cmDerecha,
        clicksToEdit: 1,
        successProperty: 'success',
        width: 280,
        frame: true,
        height: 320,
        sm: new Ext.grid.RowSelectionModel({singleSelect: false}),
        stripeRows: true,
        collapsible: true       
    });
}
createGrid();
descRoles.load();

var admCmbBsqdBttn = new Ext.Panel({
  layout:'form',
  renderTo: 'formBtnGrd', 
  borderStyle:true, 
  bodyStyle:'background: white',
  width: 600,
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
                       	var numero=gridUsrRol.store.getCount();
                       	var numero1=gridUsrRol.store.getModifiedRecords().length;
                       	//gridUsrRol.getSelectionModel().selectAll();
                       	//var numero2=gridUsrRol.getSelectionModel().getCount();
                       	//getSelectionModel().selectAll();
                       	if (numero>0){
                       	if (numero==numero1){
                       	   	//gridUsrRol.getSelectionModel().clearSelections();
                        	//guardarReasignacionDeCaso();
                        	//alert(1);
                       		validaStatusCaso();
                        }else{
                        	Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400120', helpMap,'Falta asociar el rol del usuario responsable'));
                        }
                       	}else{ Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                       	      }
                   }
               },
               {
					text:getLabelFromMap('admCmbBsqBttnRegresar', helpMap,'Regresar'),
					tooltip:getToolTipFromMap('admCmbBsqBttnRegresar', helpMap,'Regresar a la Pantalla Anterior'),			        			
                   handler:function(){
                   		
                      if (FLAG!="")
                      {
                      	window.location=_ACTION_REGRESAR_A_CONSULTAR_CASO_DETALLE+'?nmcaso='+NUMERO_CASO+'&cdmatriz='+CDMATRIZ+'&cdperson='+CDPERSON+'&cdformatoorden='+CDFORMATOORDEN+'&limit=999'+'&edit=0'
                   	  }
                   	  else 
                   		       {
                   		         window.location=_ACTION_REGRESAR_A_CONSULTAR_CASO;
                   		       }
                   }
               }
               ]
              }
        ]
});

admCmbBsqdBttn.render();


function guardarMovimientoCaso()
{
        var params = {
                 nmCaso: Ext.getCmp('numCasoId').getValue(),
                 cdUsuMov: Ext.getCmp('codUsuMovId').getValue(),
                 cdNivatn: ''
             };
        execConnection (_ACTION_GUARDAR_MOVIMIENTO_CASO, params, cbkGuardar);
}

function cbkGuardar (success, message) {
    if (success) {
          guardarReasignacionDeCaso();
    } else {
          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), message);
    }

}



function guardarReasignacionDeCaso () {	
	startMask(admCmbBsqdBttn.id,"Guardando datos...");
  var _params = '';	
  _params +="nmCaso="+Ext.getCmp('numCasoId').getValue()+"&"+
  			"&cdUsuMov="+Ext.getCmp('codUsuMovId').getValue()+"&";
  
  var recs = gridUsrRol.store.getModifiedRecords();
  
  var limpiar=recs.length;
  gridUsrRol.stopEditing();
  for (var i=0; i<recs.length; i++) {
   _params +=  
   "listaReasignacionCasoVO["+i+"].numCaso="+Ext.getCmp('numCasoId').getValue()+"&"+
   "&listaReasignacionCasoVO["+i+"].codUsuMov="+Ext.getCmp('codUsuMovId').getValue()+"&"+
   "&listaReasignacionCasoVO["+i+"].codUsuario="+recs[i].get('cdUsuario')+"&"+
   "&listaReasignacionCasoVO["+i+"].codRolMat="+ recs[i].get('desRol')+"&"+
   "&listaReasignacionCasoVO["+i+"].cdModulo="+ recs[i].get('cdModulo') +"&";
  }
   if (recs.length > 0) {
   var conn = new Ext.data.Connection ();
   conn.request({
     url: _ACTION_GUARDAR_REASIGNACION_CASO,
     params: _params,
     method: 'POST',
     callback: function (options, success, response) {
         if (Ext.util.JSON.decode(response.responseText).success == false) {
         	Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), Ext.util.JSON.decode(response.responseText).errorMessages[0]);
         } else {
	          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), Ext.util.JSON.decode(response.responseText).actionMessages[0]);
	          for (var j=0; j<limpiar; j++)
	          {
	          	gridUsrRol.store.remove(recs[j]);
	          }	
         }
         endMask();
       }
   });
  }
};


function buscarUsrMdl(parametro){
    el_storeIzquierdo.load({
        params: {   
                    cdModulo: parametro
                 }
    });
};
function cbkBuscar (_success, _message) {
	if (!_success) {
		Ext.Msg.alert('Error', _message);
	}
}





///VALIDA STATUS
function validaStatusCaso()
    
{
        var params = {
       
                 nmCaso: Ext.getCmp('numCasoId').getValue()
                        
             };
        //SstartMask(el_form.id,"Validando datos...");
        execConnection (_VALIDA_SATUS_CASO, params, cbkValida);

}
function cbkValida (_success, _message) {
  //  endMask();  
    
    if (!_success) {
			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
		}else {
			//Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function() {reloadGrid();});
			//Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message);
			guardarReasignacionDeCaso();
		}

} 




});