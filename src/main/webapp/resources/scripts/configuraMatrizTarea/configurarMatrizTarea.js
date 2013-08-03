Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";


	var aseg_store = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_ASEGURADORA}),
						reader: new Ext.data.JsonReader({
								root: 'aseguradoraComboBox',
								id: 'cdUniEco',
								successProperty: '@success'
							}, [
								{name: 'codigo', type: 'string', mapping: 'codigo'},
								{name: 'descripcion', type: 'string', mapping: 'descripcion'} 
							])
				});
				
				
	
			
		var dsClientesCorpo = new Ext.data.Store({
 	proxy: new Ext.data.HttpProxy({
     	url: _ACTION_OBTENER_CLIENTE_CORPO
 	}),
    reader: new Ext.data.JsonReader({
        root: 'comboClientesCorpBO',
        id: 'cdElemento',
        successProperty: '@success'
    }, [
        {name: 'cdElemento', type: 'string', mapping: 'cdElemento'},
        {name: 'cdPerson', type: 'string', mapping: 'cdPerson'},
        {name: 'dsElemen', type: 'string', mapping: 'dsElemen'} 
    ])
    

});	
		 	
		 			
				
	  var desProducto = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_PRODUCTOS
            }),
            reader: new Ext.data.JsonReader({
            root: 'productosComboBox',
            id: 'cdRamo'
            },[
           {name: 'codigo', type: 'string',mapping:'codigo'},
           {name: 'descripcion', type: 'string',mapping:'descripcion'}
        ])
    });		
    
    var dsProcesos = new Ext.data.Store({
 	proxy: new Ext.data.HttpProxy({
     	url: _OBTENER_PROCESOS
 	}),
 	reader: new Ext.data.JsonReader({
 		root: 'comboProcesosCat',
 		totalProperty: 'totalCount',
 		id: 'cdProceso'
 		},[
			{name: 'cdProceso', type: 'string',mapping:'cdProceso'},
			{name: 'dsProceso', type: 'string',mapping:'dsProceso'}
		  ])
});		 	
	
    
    
    
var elJson_CmbNomFormato = new Ext.data.JsonReader(
{
root: 'comboFormatos',
id: 'codigo'
},
[
{name: 'codigo', mapping:'codigo', type: 'string'},
{name: 'descripcion', mapping:'descripcion', type: 'string'}
]
);

var elJson_CmbMetEnvFrm = new Ext.data.JsonReader(
{
    root: 'comboTipoMetodoEnvio',
    id: 'codigo'
},
[
    {name: 'codigo',mapping:'codigo', type: 'string'},
    {name: 'descripcion',mapping:'descripcion', type: 'string'}
]
);



    //store del combo de formato de documentos
var dsNombreFormato = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_OBTENER_FORMATO,
        waitMsg : getLabelFromMap('400017', helpMap, 'Espere por favor....')
           }),
reader: elJson_CmbNomFormato
});

//store del combo metodo envio
var dsTipoMetodoEnvio = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_OBTENER_TAREAS,
        waitMsg : getLabelFromMap('400017', helpMap, 'Espere por favor....')
           }),
reader: elJson_CmbMetEnvFrm 
});
    
    
    	
//VALORES DE INGRESO DE LA BUSQUEDA
var cdUniEco = new Ext.form.ComboBox({
        fieldLabel: getLabelFromMap('txtFldAseguradora',helpMap,'Aseguradora'),
        tooltip:getToolTipFromMap('txtFldAseguradora',helpMap,'Aseguradora'),
        id: 'cdUniEcoId', 
        name: 'cdUniEco',
        allowBlank: false,
        //anchor: '100%'
        width:200
    });
    
var cdElemento = new Ext.form.ComboBox({
        fieldLabel: getLabelFromMap('txtFldCdElemento',helpMap,'Cliente Corporativo'),
        tooltip:getToolTipFromMap('txtFldCdElemento',helpMap,'Cliente Corporativo'), 
        id: 'cdElementoId', 
        name: 'cdElemento',
        allowBlank: false,
        //anchor: '100%'
        width:200
    });

var cdFormato = new Ext.form.ComboBox({
        fieldLabel: getLabelFromMap('txtFldFormatoSolicitud',helpMap,'Formato Solicitud'),
        tooltip:getToolTipFromMap('txtFldFormatoSolicitud',helpMap,'Formato Solicitud'), 
        id: 'cdFormatoId', 
        name: 'cdFormato',
        allowBlank: false,
        //anchor: '100%'
        width:200
    });

var comboFormato = new Ext.form.ComboBox(
{
    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
    store: dsNombreFormato,
    id:'comboFormatoId',
    fieldLabel: getLabelFromMap('comboFormatoId',helpMap,'Formato'),
    tooltip: getToolTipFromMap('comboFormatoId',helpMap,'Formato a utilizar'),
    hasHelpIcon:getHelpIconFromMap('comboFormatoId',helpMap),
	Ayuda: getHelpTextFromMap('comboFormatoId',helpMap),
    //anchor: '100%'
    width:150,
    allowBlank: false,
    displayField:'descripcion',
    valueField: 'codigo',
    hiddenName: 'dsFormatoOrden',
    typeAhead: true,
    triggerAction: 'all',
    mode:'local',
    emptyText:'Seleccione Formato...',
    selectOnFocus:true,
    forceSelection:true
}
);
    
var cdRamo = new Ext.form.ComboBox({
        fieldLabel: getLabelFromMap('txtFldCdRamo',helpMap,'Producto'),
        tooltip:getToolTipFromMap('txtFldCdRamo',helpMap,'Producto'), 
        id: 'cdRamoId', 
        name: 'cdRamo',
        allowBlank: false,
        //anchor: '100%'
        width:200
    });
    
var cdProceso = new Ext.form.ComboBox({
        fieldLabel: getLabelFromMap('txtFldCdProceso',helpMap,'Tarea'),
        tooltip:getToolTipFromMap('txtFldCdProceso',helpMap,'Tarea'), 
        id: 'cdProcesoId', 
        name: 'cdProceso',
        allowBlank: false,
        //anchor: '100%'
        width:200
    });   
    
var comboTareas = new Ext.form.ComboBox(
{
    tpl: '<tpl for="."><div ext:qtip="{cdProceso}.{dsProceso}" class="x-combo-list-item">{dsProceso}</div></tpl>',
    store: dsProcesos,
    id:'comboTareasId',
    fieldLabel: getLabelFromMap('comboTareasId',helpMap,'Tarea'),
    tooltip: getToolTipFromMap('comboTareasId',helpMap,'Tarea a utilizar'),
    hasHelpIcon:getHelpIconFromMap('comboTareasId',helpMap),
	Ayuda: getHelpTextFromMap('comboTareasId',helpMap),
    //anchor: '100%'
    width:150,
    allowBlank: false,
    displayField:'dsProceso',
    valueField: 'cdProceso',
    hiddenName: 'cdProceso',
    typeAhead: true,
    triggerAction: 'all',
    mode:'local',
    id:'cdProcesoId',
    emptyText:'Seleccione Tarea...',
    selectOnFocus:true,
    forceSelection:true
}
);
   

                storeNivelAtencion = new Ext.data.Store({

    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_BUSCAR_NIVELES_ATENCION
                }),

                reader: new Ext.data.JsonReader({
            	root:'MEstructuraList',
            	totalProperty: 'totalCount',
	            successProperty : '@success'
	        },[
	        {name: 'cdmatriz',  type: 'string',  mapping:'cdmatriz'},
	        {name: 'cdnivatn',  type: 'string',  mapping:'cdnivatn'},
	        {name: 'cdrolmat',  type: 'string',  mapping:'cdrolmat'},
	        {name: 'cdusr',  type: 'string',  mapping:'cdusr'},
	        {name: 'cdusuari',  type: 'string',  mapping:'cdusuari'},
	        {name: 'dsnivatn',  type: 'string',  mapping:'dsnivatn'},
	        {name: 'dsrolmat',  type: 'string',  mapping:'dsrolmat'},
	        {name: 'dsusuari',  type: 'string',  mapping:'dsusuari'},
	        {name: 'email',  type: 'string',  mapping:'email'},
	        {name: 'status',  type: 'string',  mapping:'status'}
			])
        });

       //return storeNivelAtencion;}

 
//function testResponsables(){

                storeResponsables = new Ext.data.Store({

    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_BUSCAR_RESPONSABLES
                }),

                reader: new Ext.data.JsonReader({
            	root:'MEstructuraList',
            	totalProperty: 'totalCount',
	            successProperty : '@success'
	        },[
	        {name: 'cdmatriz',  type: 'string',  mapping:'cdmatriz'},
	        {name: 'cdnivatn',  type: 'string',  mapping:'cdnivatn'},
	        {name: 'cdrolmat',  type: 'string',  mapping:'cdrolmat'},
	        {name: 'cdusr',  type: 'string',  mapping:'cdusr'},
	        {name: 'cdusuari',  type: 'string',  mapping:'cdusuari'},
	        {name: 'dsnivatn',  type: 'string',  mapping:'dsnivatn'},
	        {name: 'dsrolmat',  type: 'string',  mapping:'dsrolmat'},
	        {name: 'dsusuari',  type: 'string',  mapping:'dsusuari'},
	        {name: 'email',  type: 'string',  mapping:'email'},
	        {name: 'status',  type: 'string',  mapping:'status'}
			])
        });

       //return storeResponsables;}





                storeTiempo = new Ext.data.Store({

    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_BUSCAR_TIEMPOS
                }),

                reader: new Ext.data.JsonReader({
            	root:'MEstructuraTiemposList',
            	totalProperty: 'totalCount',
	            successProperty : '@success'
	        },[
	        
	        {name: 'cdmatriz',  type: 'string',  mapping:'cdmatriz'},
	        {name: 'cdnivatn',  type: 'string',  mapping:'cdnivatn'},
	        {name: 'dstresunidad',  type: 'string',  mapping:'dstresunidad'},
	        {name: 'dstalaunidad',  type: 'string',  mapping:'dstalaunidad'},
	        {name: 'dstescaunidad',  type: 'string',  mapping:'dstescaunidad'},
	        {name: 'talarma',  type: 'string',  mapping:'talarma'},
	        {name: 'talaunidad',  type: 'string',  mapping:'talaunidad'},
	        {name: 'tescalamiento',  type: 'string',  mapping:'tescalamiento'},
	        {name: 'tescaunidad',  type: 'string',  mapping:'tescaunidad'},
	        {name: 'tresolucion',  type: 'string',  mapping:'tresolucion'},
	        {name: 'tresunidad',  type: 'string',  mapping:'tresunidad'}
	        
			])
        });

     

// Definicion de las columnas de la grilla responsables
var cmNivAtencion = new Ext.grid.ColumnModel([
        {
        header: getLabelFromMap('cmNivAtencion',helpMap,'Nivel de Atenci&oacute;n'),
        tooltip: getToolTipFromMap('cmNivAtencion',helpMap,'Columna Nivel de Atenci&oacute;n'),
        dataIndex: 'dsnivatn',
        width: 260,
        sortable: true,
        align: 'center'
        },
        {
        dataIndex: 'cdnivatn',
        hidden :true
        }
]);




// Definicion de las columnas de la grilla responsables
var cm = new Ext.grid.ColumnModel([
        {
        header: getLabelFromMap('cmResponsable',helpMap,'Responsable'),
        tooltip: getToolTipFromMap('cmResponsable',helpMap,'Columna Responsable'),
        dataIndex: 'dsusuari',
        width: 120,
        align:'center',
        sortable: true
        },
        {
        header: getLabelFromMap('cmRol',helpMap,'Rol en Matriz'),
        tooltip: getToolTipFromMap('cmRol',helpMap,'Columna Rol en Matriz'),
        dataIndex: 'dsrolmat',
        width: 100,
        align:'center',
        sortable: true
        },
        {
        header: getLabelFromMap('cmemail',helpMap,'E-Mail'),
        tooltip: getToolTipFromMap('cmemail',helpMap,'Columna E-Mail'),
        dataIndex: 'email',
        width: 130,
        sortable: true,
        align: 'center'
        },
        {
        header: getLabelFromMap('cmstatus',helpMap,'Estado'),
        tooltip: getToolTipFromMap('cmstatus',helpMap,'Columna Estado'),
        dataIndex: 'status',
        width: 80,
        sortable: true,
        align: 'center'
        },{
        dataIndex: 'cdmatriz',
        hidden :true
        },
        {
        dataIndex: 'cdnivatn',
        hidden :true
        },
        {
        dataIndex: 'cdrolmat',
        hidden :true
        },
		{
        dataIndex: 'cdusr',
        hidden :true
        },
		{
        dataIndex: 'cdusuari',
        hidden :true
        }   
        
              
]);

var gridResp;

function createGrid(){
       gridResp= new Ext.grid.GridPanel({
       		id: 'gridResponsables',
            store:storeResponsables,
            title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Responsables</span>',
            border:true,
            cm: cm,
            clicksToEdit:1,
	        successProperty: 'success',
            width:430,
            frame:true,
            height:200,
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			buttonAlign: "center",
			buttons:[
        							{
        							text:getLabelFromMap('ntfcnButtonAgregaResponsable',helpMap,'Agregar Responsable'),
        							tooltip: getToolTipFromMap('ntfcnButtonAgregaResponsable',helpMap,'Agregar un nuevo responsable para un caso'),
        							disabled:true,                                 
        							handler: function() {
        								                   agregarResponsable(cdmatriz, Ext.getCmp('cdProcesoId').getValue());
        							                    }
        						},{
        							text:getLabelFromMap('ntfcnButtonEditar',helpMap,'Editar'),
        							tooltip: getToolTipFromMap('ntfcnButtonEditar',helpMap,'Editar'),
        							disabled:true,                                 
        							handler: function() {
        									if (getSelectedRecord(gridResp)!= null) {
        									                editarResponsable(getSelectedRecord(gridResp));
                        									
               									} else {
               												Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un responsable para realizar esta operaci&oacute;n'));
                        					}
                        
        							    
        								//reemplazarUsuario();
        							}
        						},{
        							text:getLabelFromMap('ntfcnButtonBorrarResp',helpMap,'Eliminar'),
        							tooltip: getToolTipFromMap('ntfcnButtonBorrarResp',helpMap,'Eliminar'),
        							disabled:true,                                 
        							handler: function() {
        								 if (getSelectedRecord(gridResp)!= null) {
                        						borrarResponsable(getSelectedRecord(gridResp));
                       
						                    } else {
						                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un responsable para realizar esta operaci&oacute;n'));
                    					 }
        							}
        						}
        							
        						],
			bbar: new Ext.PagingToolbar({
					pageSize:itemsPerPage,
					store:storeResponsables,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })     
        });
}

var gridNivAtencion;
function createGridNivelesAtencion(){
       gridNivAtencion= new Ext.grid.GridPanel({
       		id: 'gridNivAtencion',
            store: storeNivelAtencion,
            title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Niveles de Atenci&oacute;n</span>',
            border:true,
            cm: cmNivAtencion,
            clicksToEdit:1,
            successProperty: 'success',
            width:260,
            frame:true,
            height:200,
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			
			bbar: new Ext.PagingToolbar({
					pageSize:20,
					store: storeNivelAtencion
					}),
			      listeners: {
						    'click': function(){
						    	nivAtencion=getSelectedKey(gridNivAtencion, "cdnivatn");
						    	matriz=getSelectedKey(gridNivAtencion, "cdmatriz");
						    	if ( nivAtencion != "") {
                        				reloadGridResponsables(matriz,nivAtencion);
                        				reloadGridTiempos(matriz,nivAtencion);
                        			} 
                        			
						    }
						  }  
        });

}
 
 
	         
	     
   
// Definicion de las columnas de la grilla tiempos
var cmTiempos = new Ext.grid.ColumnModel([
        {
        header: getLabelFromMap('cmTiempoResolucion',helpMap,'Tiempo de Resoluci&oacute;n'),
        tooltip: getToolTipFromMap('cmTiempoResolucion',helpMap,'Columna Tiempo de Resoluci&oacute;n'),
        dataIndex: 'tresolucion',
        width: 145,
         align: 'center',
        sortable: true
        },
        {
        header: getLabelFromMap('cmUnidadRes',helpMap,'Unidad'),
        tooltip: getToolTipFromMap('cmUnidadRes',helpMap,'Unidad'),
        dataIndex: 'dstresunidad',
        width: 95,
        align: 'center',
        sortable: true
        },
        {
        header: getLabelFromMap('cmTiempoAlarma',helpMap,'Tiempo Alarma'),
        tooltip: getToolTipFromMap('cmTiempoAlarma',helpMap,'Columna Tiempo Alarma'),
        dataIndex: 'talarma',
        width: 115,
        sortable: true,
        align: 'center'
        },
        {
        header: getLabelFromMap('cmUnidadAlarma',helpMap,'Unidad'),
        tooltip: getToolTipFromMap('cmUnidadAlarma',helpMap,'Columna Unidad'),
        dataIndex: 'dstalaunidad',
        width: 95,
        sortable: true,
        align: 'center'
        },
        {
        header: getLabelFromMap('cmTiempoEscala',helpMap,'Tiempo Escalamiento'),
        tooltip: getToolTipFromMap('cmTiempoEscala',helpMap,'Columna Tiempo Escalamiento'),
        dataIndex: 'tescalamiento',
        width: 145,
        sortable: true,
        align: 'center'
        },
        {
        header: getLabelFromMap('cmUnidadEscala',helpMap,'Unidad'),
        tooltip: getToolTipFromMap('cmUnidadEscala',helpMap,'Columna Unidad'),
        dataIndex: 'dstescaunidad',
        width: 95,
        sortable: true,
        align: 'center'
        },
        {
        dataIndex: 'cdmatriz',
        hidden :true
        },
        {
        dataIndex: 'cdnivatn',
        hidden :true
        }     
]);
 
 var gridTiempos;
 function createGridTiempos(){
       gridTiempos = new Ext.grid.GridPanel({
       		id: 'gridTiempos',
            store:storeTiempo,
            title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Tiempos</span>',
            border:true,
            cm: cmTiempos,
            clicksToEdit:1,
	        successProperty: 'success',
            width:690,
            frame:true,
            height:150,
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			 buttonAlign: "center",
			buttons:[{
        							text:getLabelFromMap('ntfcnButtonAgregarTiempos',helpMap,'Agregar Tiempo'),
        							tooltip: getToolTipFromMap('ntfcnButtonAgregarTiempos',helpMap,'Agregar tiempos par un Nivel de Atenci&oacute;n'),
        							disabled:true,                              
        							handler: function() {
        							    nivAte=getSelectedKey(gridNivAtencion, "cdnivatn");
        							    agregarTiempoMatriz(cdmatriz,nivAte);
        							    
        							     
        							}
        							
        							
        						},{
        							text:getLabelFromMap('ntfcnButtonEditar',helpMap,'Editar'),
        							tooltip: getToolTipFromMap('ntfcnButtonEditar',helpMap,'Editar'), 
        							disabled:true,                                 
        							handler: function() {
        							if (getSelectedRecord(gridTiempos)!= null) {
        							
        									                editarTiempos(getSelectedRecord(gridTiempos));
                        									
               									} else {
               												Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un tiempo para realizar esta operaci&oacute;n'));
                        					}
        							
        								//reemplazarUsuario();
        							}
        						},{
        							text:getLabelFromMap('ntfcnButtonBorrarTiempo',helpMap,'Eliminar'),
        							tooltip: getToolTipFromMap('ntfcnButtonBorrarTiempo',helpMap,'Eliminar'),  
        							disabled:true,                                
        							handler: function() {
        							if (getSelectedRecord(gridTiempos)!= null) {
                        						borrarTiempo(getSelectedRecord(gridTiempos));
                                         } else {
						                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe Seleccione un tiempo para realizar esta operaci&oacute;n'));
                    				}
        							}
        						}]
			
        });
}

createGridTiempos();
createGridNivelesAtencion();
createGrid();
 

function guardarMatriz(){
    var conn = new Ext.data.Connection();
    //
    if(incisosFormConfiguraMatrizTarea.form.isValid()){
	                conn.request({
				    	url: _ACTION_GUARDAR_MATRIZ,
				    	method: 'POST',
				    	params: {
				    				cdmatriz: "",
				    				cdunieco: incisosFormConfiguraMatrizTarea.findById("cdUniEcoId").getValue(),
						    		cdramo: incisosFormConfiguraMatrizTarea.findById("cdRamoId").getValue(),
                                    cdelemento: incisosFormConfiguraMatrizTarea.findById("cdElemento").getValue(),  
						    		cdproceso: incisosFormConfiguraMatrizTarea.findById("cdProcesoId").getValue(),
						    		cdformatoorden: incisosFormConfiguraMatrizTarea.findById("comboFormatoId").getValue()
				    			},
	 					waitMsg : getLabelFromMap('400021', helpMap,'Espere por favor ...'),
                   		     callback: function (options, success, response) {
      //alert(response);
      //alert(Ext.util.JSON.decode(response.responseText).msjId)
        if (Ext.util.JSON.decode(response.responseText).success == false) {
        	Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), Ext.util.JSON.decode(response.responseText).errorMessages[0]);
            //Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), 'No se pudo guardar los datos');
            incisosFormConfiguraMatrizTarea.form.reset();
        }else {
             //Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), 'Los datos se guardaron con exito', function () {
             Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), Ext.util.JSON.decode(response.responseText).actionMessages[0], function(){
                  Ext.getCmp('btnGuardar').setDisabled(true);
                  Ext.getCmp('cdClienteId').setDisabled(true); //elemento
	              Ext.getCmp('cdUniEcoId').setDisabled(true);
	              Ext.getCmp('cdRamoId').setDisabled(true);  //producto
	              Ext.getCmp('comboFormatoId').setDisabled(true);
	              Ext.getCmp('cdProcesoId').setDisabled(true);
	              });
	
 
             //habilitar botones grilla
              habilitaBotonesResponsables();
              cdmatriz=Ext.util.JSON.decode(response.responseText).newCdMatriz;
        }
    }
 })
 }
 else{
 	Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
 }
 //
}      
 
 
 var incisosFormConfiguraMatrizTarea = new Ext.FormPanel({
		id: 'incisosFormConfiguraMatrizTarea',
        el:'formBusqueda',
        title: '<span style="color:black;font-size:14px;">'+getLabelFromMap('incisosFormConfiguraMatrizTarea',helpMap,'Configurar Matriz/Tarea')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true, 
        border:true,  
        url: _ACTION_BUSCAR_RESPONSABLES,
        layout: 'table',
		layoutConfig: {columns: 5},
		labelWidth:75,
        width: 700,
        height:520,
        items: [
        {layout: 'form',colspan: 4,
		            			items: [  {
                    xtype: 'combo',
                    labelWidth: 50,
                    tpl: '<tpl for="."><div ext:qtip="{cdElemento}.{cdPerson2}.{dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
                    store: dsClientesCorpo,
                    displayField:'dsElemen',
                    valueField: 'cdElemento',
                    hiddenName: 'cdPerson',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('cdClienteId',helpMap,'Cliente'),
        			tooltip:getToolTipFromMap('cdClienteId',helpMap,'Cliente'),
        			hasHelpIcon:getHelpIconFromMap('cdClienteId',helpMap),
            		Ayuda: getHelpTextFromMap('cdClienteId',helpMap),
                    //fieldLabel: "Cliente",
                    forceSelection: true,
                     //anchor:'100%',
                     width:200,
                    emptyText:'Seleccione Cliente ...',
                    selectOnFocus:true,
                    allowBlank: false,
                    mode:'local',
                    //labelSeparator:'',
                    id:'cdClienteId',
                    onSelect: function (record) {
                    	          this.setValue(record.get("cdElemento"));
                                  incisosFormConfiguraMatrizTarea.findById(('cdElemento')).setValue(record.get("cdElemento"));
                                  
                                   
                                           				incisosFormConfiguraMatrizTarea.findById(('cdUniEcoId')).setValue("");
                                          				aseg_store.removeAll();
                                          				
                                          				aseg_store.load({
	                            											params: {cdElemento: record.get('cdElemento')},
	                            											failure: aseg_store.removeAll()
	                            										});
	                            										
	                            						incisosFormConfiguraMatrizTarea.findById(('cdRamoId')).setValue("");				
	                            					    desProducto.removeAll();
	                            					    desProducto.load({
	                            											params: {cdunieco: incisosFormConfiguraMatrizTarea.findById(('cdUniEcoId')).getValue(),
	                            											         cdElemento: record.get('cdElemento'),
	                            											         cdRamo: ""
	                            											         },
	                            											failure: aseg_store.removeAll()
	                            										});
	                              this.collapse();
                            }
                }]}
        			       
        			       	
        			       	    		
		            		,
		            			{
		            			layout: 'form',colspan: 1,
		            			items: [
		            				     
		            				     comboFormato
		            			       ]
		            			       }
		            				
		            			      
		            			       ,{
		            			layout: 'form',colspan: 4,
		            			items: [{
                
		            			//layout: 'form',colspan: 4,
		            			//items: [
		            				     
		            				     xtype: 'combo',labelWidth: 70, tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                            			store: aseg_store, displayField:'descripcion', valueField:'codigo',id:'cdUniEcoId', hiddenName: 'codAseguradora', typeAhead: true,allowBlank:false,
	                            			mode: 'local', triggerAction: 'all',  /*anchor:'100%',*/width:200, emptyText:'Seleccione Aseguradora...',forceSelection:true,
	                            			selectOnFocus:true, loadMask: true,mode:'local',
	                            			fieldLabel: getLabelFromMap('cdUniEcoId',helpMap,'Aseguradora'),
	                            			tooltip:getToolTipFromMap('cdUniEcoId',helpMap,'Aseguradora'),
	                            			hasHelpIcon:getHelpIconFromMap('cdUniEcoId',helpMap),
											Ayuda: getHelpTextFromMap('cdUniEcoId',helpMap),

	                            			onSelect: function (record) {
	                            				this.setValue(record.get('codigo'));
	                            				incisosFormConfiguraMatrizTarea.findById(('cdRamoId')).setValue("");				
	                            					    desProducto.removeAll();
	                            					    desProducto.load({
	                            											params: {cdunieco: record.get('codigo'),
	                            											         cdElemento: incisosFormConfiguraMatrizTarea.findById(('cdElemento')).getValue(),
	                            											         cdRamo: ""
	                            											         },
	                            											failure: aseg_store.removeAll()
	                            										});
	                            				
	                            				this.collapse();
	                            				
	                            		
	                                       }
		            			       //]
		            		            }]
		            			       },
		            			       {
		            			layout: 'form',colspan: 1,
		            			items: [
		            				     
		            				     comboTareas
		            			       ]
		            			       },
		            			        {
		            			layout: 'form',colspan: 5,
		            			items: [
		            				   {
		            			layout: 'form',colspan: 1,
		            			items: [
		            				    
		            				      {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                    store: desProducto,
                    displayField:'descripcion',
                    valueField:'codigo',
                    hiddenName: 'cdRamo',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('cdRamoId',helpMap,'Producto'),
	                tooltip:getToolTipFromMap('cdRamoId',helpMap,'Producto'),
	                hasHelpIcon:getHelpIconFromMap('cdRamoId',helpMap),
					Ayuda: getHelpTextFromMap('cdRamoId',helpMap),
                    //fieldLabel: "Producto",
                    forceSelection: true,
                    mode:'local',
                     //anchor:'100%',
                     width:200,
                    emptyText:'Seleccione Producto...',
                    selectOnFocus:true,
                    id:'cdRamoId',
                    allowBlank:false//,
                    //labelSeparator:''
                } 
		            			       ]
		            			       }
		            				     
		            			       ]
		            			       },
		            			      
		            			       {
		            			layout: 'form',colspan: 2,
		            			items: [
		            				     gridNivAtencion
		            			       ]
		            			       },
		            			        {
		            			layout: 'form',colspan: 3,
		            			items: [
		            				       gridResp
		            			       ]
		            			       },
		            			  
		            			       {
		            		    layout: 'form',colspan: 5,
		            			items: [
		            				     gridTiempos
		            			       ]
		            			       
		            		
               
        				} ,{
                    xtype: 'hidden',
                    name : 'cdElemento',
                    id: 'cdElemento'
                }
                ],
        		buttons:[{
        							text:getLabelFromMap('ntfcnButtonGuardar',helpMap,'Guardar'),
        							tooltip: getToolTipFromMap('ntfcnButtonGuardar',helpMap,'Guardar'),
        							id: 'btnGuardar',
        							handler: function() {
				               			guardarMatriz();
									}
        							},
        							
        						{
        							text:getLabelFromMap('ntfcnButtonRegresar',helpMap,'Regresar'),
        							tooltip: getToolTipFromMap('ntfcnButtonRegresar',helpMap,'Regresar a la pantalla anterior'),                              
        							handler: function() {
        							      window.location = _ACTION_IR_MATRIZ_ASIGNACION;  
        							}
        						},{
        							text:getLabelFromMap('ntfcnButtonReasignar',helpMap,'Reasignar'),
        							tooltip: getToolTipFromMap('ntfcnButtonReasignar',helpMap,'Reasignar nuevo usuario a Caso'), 
        							disabled:true,                            
        							handler: function() {
        							

        							
        							/*
        							//cdusuari
        								
        								window.location = _ACTION_IR_MATRIZ_ASIGNACION_REASIGNA_CASO;
        							*/	
        								
        							}
        						}
        						
        						]	            
        
});
 
 
 
incisosFormConfiguraMatrizTarea.render();
dsClientesCorpo.load();




   
function borrarResponsable(record) {
		if(record.get('cdnivatn') != "" && record.get('cdusuari') != "")
		{
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
			{
		        if (btn == "yes")
		        {
         			var _params = {
         						pv_cdmatriz_i: record.get('cdmatriz'),
         						pv_cdnivatn_i:record.get('cdnivatn'),
         						pv_cdusuario_i:record.get('cdusuari')
         			};
         			nivAteResp=record.get('cdnivatn');
         			execConnection(_ACTION_BORRAR_RESPONSABLE, _params, cbkConnectionResp);
               }
			})
		}else{
				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
		}
};


function borrarTiempo(record) {

		if (record.get('cdmatriz') != "" && record.get('cdnivatn')!="")
		{
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
			{
		        if (btn == "yes")
		        {
         			var _params = {
         						pv_cdmatriz_i: record.get('cdmatriz'),
         						pv_cdnivatn_i:record.get('cdnivatn')
         			};
         			nivAteTiempo=record.get('cdnivatn');
         			execConnection(_ACTION_BORRAR_TIEMPO, _params, cbkConnectionTiempos);
               }
			})
		}else{
				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
		}
		
};
/*
function cbkConnection (_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid()});
	}
} 
*/


function cbkConnectionResp (_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGridNivAtencion(cdmatriz);reloadGridResponsables("","");reloadGridTiempos("");});
	}
}; 

function cbkConnectionTiempos (_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGridTiempos(nivAteTiempo)});
	}
};


dsProcesos.load({
	callback:function(){dsNombreFormato.load()}
});


});//FIN DEL ONREADY





function reloadGridResponsables(matriz,atencion){
	var _params = {
       		         pv_cdmatriz_i: matriz,
                     pv_cdnivatn_i: atencion
	};
	reloadComponentStore(Ext.getCmp('gridResponsables'), _params, cbkReload);
}

function reloadGridTiempos(matriz,atencion){	
	var _params = {
       		         codigoMatriz: matriz,
                     nivAtencion: atencion
	};
	reloadComponentStore(Ext.getCmp('gridTiempos'), _params, cbkReload);
}

function reloadGridNivAtencion(matriz){
	var _params = {
       		         pv_cdmatriz_i: matriz
	};
	reloadComponentStore(Ext.getCmp('gridNivAtencion'), _params, cbkReloadNivAtn);
}

var _hidden1 = new Ext.form.Hidden();
var _hidden2 = new Ext.form.Hidden();
function recargarGridNivAtencionEdit(_cdMatriz, _cdNivatn){
	_hidden2 = _cdNivatn;
	_hidden1 = _cdMatriz;	
	reloadGridNivAtencion(_cdMatriz);
}

//SE CREA UNA FUNCION CALLBACK PARA LA GRILLA DE NIVELES DE ATENCION
function cbkReloadNivAtn(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}else{
		//reloadGridResponsables(storeNivelAtencion.reader.jsonData.MEstructuraList[0].cdmatriz,storeNivelAtencion.reader.jsonData.MEstructuraList[0].cdnivatn);
 		//reloadGridTiempos(storeNivelAtencion.reader.jsonData.MEstructuraList[0].cdmatriz,storeNivelAtencion.reader.jsonData.MEstructuraList[0].cdnivatn);
		reloadGridResponsables(_hidden1,_hidden2);
 		reloadGridTiempos(_hidden1,_hidden2);
	}	
}

function habilitaBotonesTiempo(){
	Ext.getCmp('gridTiempos').buttons[0].setDisabled(false);
	Ext.getCmp('gridTiempos').buttons[1].setDisabled(false);
	Ext.getCmp('gridTiempos').buttons[2].setDisabled(false);
}

function habilitaBotonesResponsables(){
	Ext.getCmp('gridResponsables').buttons[0].setDisabled(false);
	Ext.getCmp('gridResponsables').buttons[1].setDisabled(false);
	Ext.getCmp('gridResponsables').buttons[2].setDisabled(false);
}

function cbkReload(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}
}
