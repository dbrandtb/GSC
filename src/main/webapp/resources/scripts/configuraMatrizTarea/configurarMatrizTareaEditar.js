Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";


               
var cdmatriz = new Ext.form.TextField({
              name: 'cdmatriz',
              id:'cdmatrizId',
              labelSeparator:'',
              hidden:true
          });
          
var cdelemento = new Ext.form.TextField({
              name: 'cdelemento',
              id:'cdelementoId',
              labelSeparator:'',
              hidden:true
          }); 

var cdproceso = new Ext.form.TextField({
              name: 'cdproceso',
              id:'cdprocesoId',
              labelSeparator:'',
              hidden:true
          }); 
 
var cdramo = new Ext.form.TextField({
              name: 'cdramo',
              id:'cdramoId',
              labelSeparator:'',
              hidden:true
          }); 
          
 var cdrolmat = new Ext.form.TextField({
              name: 'cdrolmat',
              id:'cdrolmatId',
              labelSeparator:'',
              hidden:true
          }); 
          
var cdunieco = new Ext.form.TextField({
              name: 'cdunieco',
              id:'cduniecoId',
              labelSeparator:'',
              hidden:true
          });           
                             
var dsunieco = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsuniecoIdConfEd',helpMap,'Aseguradora'),
        tooltip:getToolTipFromMap('dsuniecoIdConfEd',helpMap,'Aseguradora'),
        hasHelpIcon:getHelpIconFromMap('dsuniecoIdConfEd',helpMap),
		Ayuda: getHelpTextFromMap('dsuniecoIdConfEd',helpMap),
        id: 'dsuniecoIdConfEd', 
        name: 'dsunieco',
        disabled:true,
        //anchor: '100%'
        width:252
    });

var dselemen = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtFldClienteConfEd',helpMap,'Cliente Corporativo'),
        tooltip:getToolTipFromMap('txtFldClienteConfEd',helpMap,'Cliente Corporativo'),
        hasHelpIcon:getHelpIconFromMap('txtFldClienteConfEd',helpMap),
		Ayuda: getHelpTextFromMap('txtFldClienteConfEd',helpMap),
        id: 'txtFldClienteConfEd', 
        name: 'dselemen',
        disabled:true,
        //anchor: '100%'
        width:252
    });

var dsramo = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtFldDsRamoConfEd',helpMap,'Producto'),
        tooltip:getToolTipFromMap('txtFldDsRamoConfEd',helpMap,'Producto'),
         hasHelpIcon:getHelpIconFromMap('txtFldDsRamoConfEd',helpMap),
		Ayuda: getHelpTextFromMap('txtFldDsRamoConfEd',helpMap),
        id: 'txtFldDsRamoConfEd', 
        name: 'dsramo',
        disabled:true,
        //anchor: '100%'
        width:252
    });

var dsproceso = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtFldDsProcesoConfEd',helpMap,'Tarea'),
        tooltip:getToolTipFromMap('txtFldDsProcesoConfEd',helpMap,'Tarea'),
         hasHelpIcon:getHelpIconFromMap('txtFldDsProcesoConfEd',helpMap),
		Ayuda: getHelpTextFromMap('txtFldDsProcesoConfEd',helpMap),
        id: 'txtFldDsProcesoConfEd', 
        name: 'dsproceso',
        disabled:true,
        //anchor: '100%'
        width:220
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
        waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
           }),
reader: elJson_CmbNomFormato
});


    
    	

    




var comboFormato = new Ext.form.ComboBox(
{
    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
    store: dsNombreFormato,
    id:'comboFormatoIdConfEd',
    fieldLabel: getLabelFromMap('comboFormatoIdConfEd',helpMap,'Formato'),
    tooltip: getToolTipFromMap('comboFormatoIdConfEd',helpMap,'Formato a utilizar'),
    hasHelpIcon:getHelpIconFromMap('comboFormatoIdConfEd',helpMap),
	Ayuda: getHelpTextFromMap('comboFormatoIdConfEd',helpMap),
    //anchor:'100%',
    width:218,
    allowBlank: false,
     disabled:true,
    displayField:'descripcion',
    valueField: 'codigo',
    hiddenName: 'cdformatoorden',
    typeAhead: true,
    triggerAction: 'all',
    emptyText:'Seleccione Formato...',
    selectOnFocus:true,
    mode:'local',
    forceSelection:true
}
);
    

   
    //function test(){

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
	        {name: 'dsnivatn',  type: 'string',  mapping:'dsnivatn'}
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
        header: getLabelFromMap('cmNivAtencionConfEd',helpMap,'Nivel de Atenci&oacute;n'),
        tooltip: getToolTipFromMap('cmNivAtencionConfEd',helpMap,'Columna Nivel de Atenci&oacute;n'),
        dataIndex: 'dsnivatn',
        width: 244,
        sortable: true,
        align: 'center'
        },
        {
        dataIndex: 'cdnivatn',
        hidden :true
        },
        {
        dataIndex:'cdmatriz',
        hidden :true
        }
]);




// Definicion de las columnas de la grilla responsables
var cm = new Ext.grid.ColumnModel([
        {
        header: getLabelFromMap('cmResponsableConfEd',helpMap,'Responsable'),
        tooltip: getToolTipFromMap('cmResponsableConfEd',helpMap,'Columna Responsable'),
        dataIndex: 'dsusuari',
        width: 120,
        sortable: true,
         align: 'center'
        },
        {
        header: getLabelFromMap('cmRolConfEd',helpMap,'Rol en Matriz'),
        tooltip: getToolTipFromMap('cmRolConfEd',helpMap,'Columna Rol en Matriz'),
        dataIndex: 'dsrolmat',
        width: 92,
        sortable: true,
         align: 'center'
        },
        {
        header: getLabelFromMap('cmemailConfEd',helpMap,'E-Mail'),
        tooltip: getToolTipFromMap('cmemailConfEd',helpMap,'Columna E-Mail'),
        dataIndex: 'email',
        width: 120,
        sortable: true,
        align: 'center'
        },
        {
        header: getLabelFromMap('cmstatusConfEd',helpMap,'Estado'),
        tooltip: getToolTipFromMap('cmstatusConfEd',helpMap,'Columna Status'),
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
        dataIndex: 'cdmodulo',
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
        							text:getLabelFromMap('ntfcnButtonAgregaResponsableConfEd',helpMap,'Agregar Responsable'),
        							tooltip: getToolTipFromMap('ntfcnButtonAgregaResponsableConfEd',helpMap,'Agrega un nuevo responsable para un caso'),                              
        							handler: function() {
        								agregarResponsable(cdMatrizEdita,Ext.getCmp('cdprocesoId').getValue());
        								
        								
        							}
        						},{
        							text:getLabelFromMap('ntfcnButtonEditarConfEd',helpMap,'Editar'),
        							tooltip: getToolTipFromMap('ntfcnButtonEditarConfEd',helpMap,'Edita un responsable'),                              
        							handler: function() {
        									if (getSelectedRecord(gridResp)!= null) {
        									                editarResponsable(getSelectedRecord(gridResp))
                        									
               									} else {
               												Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un responsable para realizar esta operaci&oacute;n'));
                        					}
                        					                               					   
        								}
        						},{
        							text:getLabelFromMap('ntfcnButtonBorrarRespConfEd',helpMap,'Eliminar'),
        							tooltip: getToolTipFromMap('ntfcnButtonBorrarRespConfEd',helpMap,'Eliminar'),                              
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
					pageSize:20,
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
            store:storeNivelAtencion,
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
					//displayInfo: true
	                //displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					//emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    }),
			      listeners: {
						    'click': function(){
						    	nivAtencion=getSelectedKey(gridNivAtencion, "cdnivatn");
						    	matriz=getSelectedKey(gridNivAtencion, "cdmatriz");
						    	if ( nivAtencion != "") {
                        				reloadGridResponsables(matriz,nivAtencion);
                        				reloadGridTiempos(nivAtencion);
                        				(nivAtencion);
                        			} 
                        			
						    }
						  }     
        });

}
 
 
	         
	     
   
// Definicion de las columnas de la grilla tiempos
var cmTiempos = new Ext.grid.ColumnModel([
        {
        header: getLabelFromMap('cmTiempoResolucionConfEd',helpMap,'Tiempo de Resoluci&oacute;n'),
        tooltip: getToolTipFromMap('cmTiempoResolucionConfEd',helpMap,'Columna Tiempo de Resoluci&oacute;n'),
        dataIndex: 'tresolucion',
        width: 145,
        sortable: true,
          align: 'center'
        },
        {
        header: getLabelFromMap('cmUnidadResConfEd',helpMap,'Unidad'),
        tooltip: getToolTipFromMap('cmUnidadResConfEd',helpMap,'Unidad'),
        dataIndex: 'dstresunidad',
          align: 'center',
        width: 95,
        sortable: true
        },
        {
        header: getLabelFromMap('cmTiempoAlarmaConfEd',helpMap,'Tiempo Alarma'),
        tooltip: getToolTipFromMap('cmTiempoAlarmaConfEd',helpMap,'Columna Tiempo Alarma'),
        dataIndex: 'talarma',
        width: 115,
        sortable: true,
        align: 'center'
        },
        {
        header: getLabelFromMap('cmUnidadAlarmaConfEd',helpMap,'Unidad'),
        tooltip: getToolTipFromMap('cmUnidadAlarmaConfEd',helpMap,'Columna Unidad'),
        //dataIndex: 'talaunidad',
        dataIndex: 'dstalaunidad',
        width: 95,
        sortable: true,
        align: 'center'
        },
        {
        header: getLabelFromMap('cmTiempoEscalaConfEd',helpMap,'Tiempo Escalamiento'),
        tooltip: getToolTipFromMap('cmTiempoEscalaConfEd',helpMap,'Columna Tiempo Escalamiento'),
        dataIndex: 'tescalamiento',
        width: 145,
        sortable: true,
        align: 'center'
        },
        {
         header: getLabelFromMap('cmUnidadEscalaConfEd',helpMap,'Unidad'),
        tooltip: getToolTipFromMap('cmUnidadEscalaConfEd',helpMap,'Columna Unidad'),
        dataIndex: 'dstescaunidad',
        //dataIndex: 'tescaunidad',
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
            width:710,
            frame:true,
            height:200,
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			 buttonAlign: "center",
			buttons:[{
        							text:getLabelFromMap('ntfcnButtonAgregarTiemposConfEd',helpMap,'Agregar Tiempo'),
        							tooltip: getToolTipFromMap('ntfcnButtonAgregarTiempos',helpMap,'Agrega tiempos para un Nivel de Atenci&oacute;n'),                              
        							handler: function() {
        							    nivAte=getSelectedKey(gridNivAtencion, "cdnivatn");
						    	        agregarTiempoMatriz(cdMatrizEdita,nivAte);
        							}
        							
        							
        						},{
        							text:getLabelFromMap('ntfcnButtonEditarConfEd',helpMap,'Editar'),
        							tooltip: getToolTipFromMap('ntfcnButtonEditarConfEd',helpMap,'Edita un tiempo'),                              
        							handler: function() {
        							if (getSelectedRecord(gridTiempos)!= null) {
        							
        									                editarTiempos(getSelectedRecord(gridTiempos))
                        									
               									} else {
               												Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un tiempo para realizar esta operaci&oacute;n'));
                        					}
        							
        							
        							}
        						},{
        							text:getLabelFromMap('ntfcnButtonBorrarTiempoConfEd',helpMap,'Borrar'),
        							tooltip: getToolTipFromMap('ntfcnButtonBorrarTiempoConfEd',helpMap,'Borrar'),                              
        							handler: function() {
        							if (getSelectedRecord(gridTiempos)!= null) {
                        						borrarTiempo(getSelectedRecord(gridTiempos));
                                         } else {
						                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un tiempo para realizar esta operaci&oacute;n'));
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
	                conn.request({
				    	url: _ACTION_GUARDAR_MATRIZ,
				    	method: 'POST',
				    	params: {
				    				cdmatriz: cdMatrizEdita,
				    				cdunieco: incisosFormConfiguraMatrizTareaEdita.findById("cduniecoId").getValue(),
						    		cdramo: incisosFormConfiguraMatrizTareaEdita.findById("cdramoId").getValue(),
                                    cdelemento: incisosFormConfiguraMatrizTareaEdita.findById("cdelementoId").getValue(),  
						    		cdproceso: incisosFormConfiguraMatrizTareaEdita.findById("cdprocesoId").getValue(),
						    		cdformatoorden: incisosFormConfiguraMatrizTareaEdita.findById("comboFormatoIdConfEd").getValue()
				    			},
	 					waitMsg : getLabelFromMap('400021', helpMap,'Espere por favor ...'),
                   		     callback: function (options, success, response) {
        if (Ext.util.JSON.decode(response.responseText).success == false) {
       		     Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), Ext.util.JSON.decode(response.responseText).actionErrors[0]);
        	}else {
            //Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), 'Los datos se guardaron con exito', function(){reloadGrid();});
             Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), Ext.util.JSON.decode(response.responseText).actionMessages[0]);
        }
    }
 })
}      
 
 
 
 
 

 //se define la forma en que se van a leer los datos que envie la action
        var _jsonFormReader = new Ext.data.JsonReader( {

            //indica el arreglo a leer
            root : 'MEstructuraList',

            //indica la cantidad de registro que debe leerse
            totalProperty: 'total',

            //indica el resultado de la respuesta de la action
            successProperty : 'success'

        }, [ 
               {name : 'cdelemento',mapping : 'cdelemento',type : 'string'},
               {name : 'cdproceso',mapping : 'cdproceso',type : 'string'},
               {name : 'cdramo',mapping : 'cdramo',type : 'string'},
               {name : 'cdrolmat',mapping : 'cdrolmat',type : 'string'},
               {name : 'cdunieco',mapping : 'cdunieco',type : 'string'},
               {name : 'cdmatriz',mapping : 'cdmatriz',type : 'string'},
               {name : 'dselemen', mapping : 'dselemen', type : 'string'},
               {name : 'dsproceso', mapping : 'dsproceso', type : 'string'},
               {name : 'dsramo', mapping : 'dsramo', type : 'string'},
               {name : 'dsunieco',mapping : 'dsunieco',type : 'string'},
               {name : 'cdusuari', mapping : 'cdusuari', type : 'string'},
               {name : 'cdusuario', mapping : 'cdusuario', type : 'string'},
               {name : 'cdformatoorden', mapping : 'cdformatoorden', type : 'string'}
        ]);
        
        
 
 var incisosFormConfiguraMatrizTareaEdita = new Ext.FormPanel({
		id: 'incisosFormConfiguraMatrizTareaEdita',
        el:'formBusqueda',
        title: '<span style="color:black;font-size:14px;">'+getLabelFromMap('incisosFormConfiguraMatrizTareaEdita',helpMap,'Configurar Matriz/Tarea Editar')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        labelWidth:75,
        frame:true, 
        border:true,  
        url: _ACTION_GET_MATRIZ_ASIGNACION,
         baseParams : {
                          codigoMatriz: cdMatrizEdita
                      },
        reader:_jsonFormReader,
        layout: 'table',
		layoutConfig: {columns: 5},
        width: 720,
        height:600,
        items: [
        			{
            			layout: 'form',
            			colspan: 3,
            			items: [
            				     dselemen
            			       ]
            		},
            		{	labelWidth: 45,
            		 	layout: 'form',
            			colspan: 2,
            			items: [
            						comboFormato
            				   ]
   			        },
   			        {
            			layout: 'form',
            			colspan: 3,
            			items: [
									dsunieco

            			       ]
			       },
			       {    labelWidth: 45,
            			layout: 'form',
            			colspan: 2,
            			items: [
									dsproceso
            			       ]
			       },
			       {
            			layout: 'form',
            			colspan: 5,
            			items: [
            				    dsramo
            			       ]
			       },
			       {
            			layout: 'form',
            			colspan: 2,
            			items: [
            				     gridNivAtencion
            			       ]
			       },
			       {
            			layout: 'form',
            			colspan: 3,
            			items: [
            				       gridResp
            			       ]
			       },
			       {
            		    layout: 'form',
            		    colspan: 5,
            			items: [
            				     gridTiempos,
            				     cdunieco,
            				     cdmatriz,
            				     cdramo,
            				     cdproceso,
            				     cdelemento
            			       ]
   			       },
   			       {
                    xtype: 'hidden',
                    name : 'cdElemento',
                    id: 'cdElementoId'
                	}
                ],
        		buttons:[{
        							text:getLabelFromMap('ntfcnButtonGuardarConfEd',helpMap,'Guardar'),
        							tooltip: getToolTipFromMap('ntfcnButtonGuardarConfEd',helpMap,'Guardar'),
        							disabled:true, 
        							handler: function() {
				               			guardarMatriz();
									}
        							},
        							
        						{
        							text:getLabelFromMap('ntfcnButtonReasignarConfEd',helpMap,'Reasignar'),
        							tooltip: getToolTipFromMap('ntfcnButtonReasignarConfEd',helpMap,'Reasignar nuevo usuario a Caso'), 
        							//disabled:true,
        							handler: function() {
        								if(getSelectedRecord(gridResp)!=null){			
                								window.location = _ACTION_IR_REASIGNAR_CASO_USR+'?cdusuari='+getSelectedRecord(gridResp).get('cdusuari')+'&dsusuari='+getSelectedRecord(gridResp).get('dsusuari')+'&cdmatriz='+cdMatrizEdita;  
                		 				}else{
                		 						Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un responsable para realizar esta operaci&oacute;n'));
                		 				}	
        								/*
        								window.location = _ACTION_IR_MATRIZ_ASIGNACION_REASIGNA_CASO;
        								*/
        							}
        						},
        						
        						{
        							text:getLabelFromMap('ntfcnButtonRegresarConfEd',helpMap,'Regresar'),
        							tooltip: getToolTipFromMap('ntfcnButtonRegresarConfEd',helpMap,'Regresa a la pantalla anterior'),                              
        							handler: function() {
        								window.location = _ACTION_IR_MATRIZ_ASIGNACION;
        							}
        						}
        						
        						]	            
        
});
 
 
 
incisosFormConfiguraMatrizTareaEdita.render();


  
   
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

		if(record.get('cdmatriz') != "" && record.get('cdnivatn')!="")
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

function cbkConnection (_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid()});
	}
} 

function cbkConnectionResp (_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGridNivAtencion(cdMatrizEdita);reloadGridResponsables("","");reloadGridTiempos("");});
	}
} 

function cbkConnectionTiempos (_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGridTiempos(nivAteTiempo)});
	}
}

function deshabilitarGrids(){

incisosFormConfiguraMatrizTareaEdita.findById("reasig").setDisabled(false);
}



//dsNombreFormato.load();
//incisosFormConfiguraMatrizTareaEdita.form.load();
dsNombreFormato.load({callback:function(record,opt,success){incisosFormConfiguraMatrizTareaEdita.form.load();}}); 
 


/*storeNivelAtencion.load({
                        params: { pv_cdmatriz_i: cdMatrizEdita },
                        callback:function(){
                        	
                        }
                       });*/
//LA SENTENCIA DE CODIGO ANTERIOR SE REEMPLAZA POR LA SIGUIENTE:
reloadGridNivAtencion(cdMatrizEdita);                                              


});//FIN 





function reloadGridResponsables(matriz,nivAtencion){
	var _params = {
       		         pv_cdmatriz_i: matriz,
                     pv_cdnivatn_i: nivAtencion
	};
	reloadComponentStore(Ext.getCmp('gridResponsables'), _params, cbkReload);
}

function reloadGridTiempos(nivAtencion){
	var _params = {
       		         codigoMatriz: cdMatrizEdita,
                     nivAtencion: nivAtencion
	};
	reloadComponentStore(Ext.getCmp('gridTiempos'), _params, cbkReload);
}


function reloadGridNivAtencion(matriz){
	var _params = {
       		         pv_cdmatriz_i: matriz
	};
	reloadComponentStore(Ext.getCmp('gridNivAtencion'), _params, cbkReloadNivAtn);
}


function reloadGrid(){
	var _params = {
       		dsNotificacion: Ext.getCmp('incisosFormNoti').form.findField('desNotificacion').getValue(),
       		dsFormatoOrden: Ext.getCmp('incisosFormNoti').form.findField('desFormato').getValue(),
       		dsMetEnv: Ext.getCmp('incisosFormNoti').form.findField('desMetodoEnvio').getValue()
	};
	reloadComponentStore(Ext.getCmp('grid2'), _params, cbkReload);
}

function habilitaBotonesTiempo(){
	Ext.getCmp('gridTiempos').buttons[0].setDisabled(false);
	Ext.getCmp('gridTiempos').buttons[1].setDisabled(false);
	Ext.getCmp('gridTiempos').buttons[2].setDisabled(false);
}

function cbkReload(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}
	
}

//SE CREA UNA FUNCION CALLBACK PARA LA GRILLA DE NIVELES DE ATENCION
function cbkReloadNivAtn(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}else{
		reloadGridResponsables(storeNivelAtencion.reader.jsonData.MEstructuraList[0].cdmatriz,storeNivelAtencion.reader.jsonData.MEstructuraList[0].cdnivatn);
 		reloadGridTiempos(storeNivelAtencion.reader.jsonData.MEstructuraList[0].cdnivatn);
	}	
}

//FUNCION CREADA PARA QUE CUANDO SE EDITE UN TIEMPO SE RECARGUE Y SE VEA LOS CAMBIOS
var _hidden1 = new Ext.form.Hidden();
var _hidden2 = new Ext.form.Hidden();
function recargarGridNivAtencionEdit(_cdMatriz,_cdNivatn){
	_hidden2 = _cdNivatn;
	_hidden1 = _cdMatriz;
	reloadGridNivAtencionEdit(_cdMatriz)
}

function reloadGridNivAtencionEdit(matriz){
	var _params = {
       		         pv_cdmatriz_i: matriz
	};
	reloadComponentStore(Ext.getCmp('gridNivAtencion'), _params, cbkReloadNivAtnEdit);
}
function cbkReloadNivAtnEdit(_r, _options, _success, _store){
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}else{
		reloadGridResponsables(_hidden1,_hidden2);
 		reloadGridTiempos(_hidden2);
	}
}