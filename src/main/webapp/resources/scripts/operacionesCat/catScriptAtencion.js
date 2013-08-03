function scriptAtencion(_cdperson, _cdElemento){

/*Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";*/
    var Tree = Ext.tree;
	var jsonData;
	var tree;


var CODIGO_USUARIO;
var txt;
txt='Operador CAT';

CODIGO_USUARIO='SLIZARDI';

/**************** la funcion y el nombre usuario ****************/
var lasFuncionNombret=new Ext.data.Record.create([
  {name: 'funcionNombre',  mapping:'funcionNombre',  type: 'string'}       
]);

//el JsonReader de la grilla a mostrar cuando se presione en el arbol de la derecha
var readerFuncionNombre= new Ext.data.JsonReader(
  {
   root:'MOperacionCatList',
   totalProperty: 'totalCount',
   successProperty : '@success'
  },
 lasFuncionNombret
);

storeFuncionNombre = new Ext.data.Store({
   proxy: new Ext.data.HttpProxy({
         url: _ACTION_OBTENER_FUNCION_NOMBRE
        }),
   reader: readerFuncionNombre
});

/**************** la grilla de el guion izquierda ****************/

var losScript=new Ext.data.Record.create([
  {name: 'cdDialogo',  mapping:'cdDialogo',  type: 'string'},
  {name: 'dsDialogo',  mapping:'dsDialogo',  type: 'string'},
  {name: 'otTapVal',  mapping:'otTapVal',  type: 'string'},
  {name: 'dsLabel',  mapping:'dsLabel',  type: 'string'}
]);

//el JsonReader de la grilla a mostrar cuando se presione en el arbol de la derecha
var readerScript= new Ext.data.JsonReader(
  {
   root:'MOperacionCatList',
   totalProperty: 'totalCount',
   successProperty : '@success'
  },
 losScript
);

storeGrid = new Ext.data.Store({
   proxy: new Ext.data.HttpProxy({
         url: _ACTION_BUSCAR_DIALOGOS_GUION
        }),
   reader: readerScript
});

// Definicion de las columnas de la grilla
var cm = new Ext.grid.ColumnModel([
        {
        header:getLabelFromMap('cmDsDialogo',helpMap,'Bloque de Script Actual'),
        tooltip:getToolTipFromMap('cmDsDialogo',helpMap,''),	
        dataIndex: 'dsDialogo',
        width: 300
        },
        {
        header:getLabelFromMap('cmOtTapVal',helpMap,'Lista de Valores'),
        tooltip:getToolTipFromMap('cmOtTapVal',helpMap,''),	
        dataIndex: 'otTapVal',
        width: 100,
        hidden :true
        },
        {
        header:getLabelFromMap('cmDsLabel',helpMap,'Valor Tabla'),
        tooltip:getToolTipFromMap('cmDsLabel',helpMap,''),	
        header: 'Valor Tabla',
        dataIndex: 'dsLabel',
        width: 170,
        hidden :true
        },
    	{
        dataIndex: 'cdDialogo',
        hidden :true
        }       
]);

var grid21;


function createGrid(){
       grid21= new Ext.grid.GridPanel({
       		id: 'grid21',
            //el:'gridGuiones',
            store: storeGrid,
            title: '<span style="height:10"></span>',
            //loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
            //reader:jsonGrilla,
            border:false,
            cm: cm,
            //clicksToEdit:1,
	        successProperty: 'success',
            width:250,
           // frame:true,
            height:285,
            //height: 285,
            //sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			///stripeRows: true,
			collapsible: true			
        });
   //grid2.render();
}
createGrid();




/****************Grilla de Encuestas Pendientes****************/

var lasUrl=new Ext.data.Record.create([
  {name: 'nmConfig',  mapping:'nmConfig',  type: 'string'},
  {name: 'cdEncuesta',  mapping:'cdEncuesta',  type: 'string'},
  {name: 'dsEncuesta',  mapping:'dsEncuesta',  type: 'string'},
  {name: 'cdUnieco',  mapping:'cdUnieco',  type: 'string'},
  {name: 'cdRamo',  mapping:'cdRamo',  type: 'string'},
  {name: 'nmPoliza',  mapping:'nmPoliza',  type: 'string'},
  {name: 'estado',  mapping:'estado',  type: 'string'},
  {name: 'nmPoliex',  mapping:'nmPoliex',  type: 'string'},
  {name: 'dsUnieco',  mapping:'dsUnieco',  type: 'string'},
  {name: 'dsRamo',  mapping:'dsRamo',  type: 'string'},
  {name: 'dsPerson',  mapping:'dsPerson',  type: 'string'},
  {name: 'cdPerson',  mapping:'cdPerson',  type: 'string'}
]);

//el JsonReader de la grilla a mostrar cuando se presione en el arbol de la derecha
var readerUrl= new Ext.data.JsonReader(
  {
   root:'MEncuestaPendienteLista',
   totalProperty: 'totalCount',
   successProperty : '@success'
  },
 lasUrl
);

storeGridUrl = new Ext.data.Store({
   proxy: new Ext.data.HttpProxy({
         url: _ACTION_OBTENER_ENCUESTA_PENDIENTES
        }),
   reader: readerUrl
});

// Definicion de las columnas de la grilla de URL
var cmUrl = new Ext.grid.ColumnModel([
        {
        header: 'Encuesta Pendiente',
        dataIndex: 'dsEncuesta',
        width: 260
        },
    	{
        dataIndex: 'nmConfig',
        hidden :true
        },
    	{
        dataIndex: 'cdEncuesta',
        hidden :true
        }       
]);

var gridUrl;


function createGridUrl(){
       gridUrl= new Ext.grid.GridPanel({
       		id: 'gridUrl',
            store: storeGridUrl,
            title: '<span style="height:10"></span>',
            border:false,
            columns: [
	   					{
	   					//id: 'nmConfigId', 
	   					dataIndex: 'nmConfig', 
	   					hidden: true
	   					},{
	   					//id: 'cdEncuestaId', 
	   					dataIndex: 'cdEncuesta', 
	   					hidden: true
	   					},{
	        			header: getLabelFromMap('cdEncuestaId',helpMap,'Encuesta Pendiente'),
	        			tooltip: getToolTipFromMap('cdEncuestaId', helpMap, 'Encuesta Pendiente'),  
                        hasHelpIcon:getHelpIconFromMap('cdEncuestaId',helpMap),								 
                        Ayuda: getHelpTextFromMap('cdEncuestaId',helpMap),
	        			
	   					dataIndex: 'dsEncuesta', 
	   					align: 'left', 
	   					width: 260/*,
	   					renderer : crearHLink*/
	   					},
	   					
	   					{
	   					//id: 'cdUniecoId', 
	   					dataIndex: 'cdUnieco', 
	   					hidden: true
	   					},
	   					{
	   					//id: 'cdRamoId', 
	   					dataIndex: 'cdRamo', 
	   					hidden: true
	   					},
	   					
	   					{
	   					//id: 'nmPolizaId', 
	   					dataIndex: 'nmPoliza', 
	   					hidden: true
	   					},
	   					
	   					{
	   					//id: 'cdEstadoId', 
	   					dataIndex: 'estado', 
	   					hidden: true
	   					},
	   					{
	   					//id: 'nmPoliexId', 
	   					dataIndex: 'nmPoliex', 
	   					hidden: true
	   					},
	   					{
	   					//id: 'dsuniecoId', 
	   					dataIndex: 'dsUnieco', 
	   					hidden: true
	   					},
	   					
	   					{//id: 'dsramoId', 
	   					dataIndex: 'dsRamo', 
	   					hidden: true
	   					},
	   					
	   					
	   					{//id: 'dsnombreId', 
	   					dataIndex: 'dsPerson', 
	   					hidden: true
	   					},
	   					
	   					{//id: 'cdpersonId', 
	   					dataIndex: 'cdPerson', 
	   					hidden: true
	   					}
	   					
	   					
            		],//cm: cmUrl,
	        successProperty: 'success',
            width:256,
            height:130,
			collapsible: true			
        });
   //gridUrl.render();
}
createGridUrl();

var dsTiposGuion = new Ext.data.Store({
	     	proxy: new Ext.data.HttpProxy({
	         	url: _ACTION_OBTENER_TIPO_GUION
	     	}),
	     	reader: new Ext.data.JsonReader({
	     		root: 'comboTipoGuion',
 		totalProperty: 'totalCount',
 		id: 'codigo'
 		},[
			{name: 'codigo', type: 'string',mapping:'codigo'},
			{name: 'descripcion', type: 'string',mapping:'descripCorta'}
		  ])
});


var tree = new Tree.TreePanel({
    //el:'tree-div',
		  
    frame : true,
    header: false,
    layout: 'table',
	useArrows:true,
    autoScroll:true,
    animate:true,
    enableDD:true,
    height: 285,
    width: 256,
    containerScroll: false,
	loader: new Tree.TreeLoader({
					clearOnLoad: true
	}),

	listeners:{
		'dblclick':function(nodo,ev){
			nodoSel = nodo.id.split("/");
			if ((nodoSel.length)>1)
			{
				//alert(nodoSel.length);
				//CdRamo 0
				//CdGuion 1
				//CdProceso 2
				//cdElemento 3
				//cdUniEco 4
				//traer dialogos
				startMask('elformapanelId', getLabelFromMap('400070',helpMap,'Cargando datos ...'));
				var _params = {
	   				/*cdUniEco: nodoSel[4],
	   				cdRamo: nodoSel[0],
	   				cdElemento: nodoSel[3],
	   				cdProceso: nodoSel[2],*/
	   				cdGuion: nodoSel[1]
	   			 };
	   			 
	   			storeGrid.baseParams= { 
			   			'cdGuion': nodoSel[1]
		     			};
				storeGrid.proxy = new Ext.data.HttpProxy({
							url : _ACTION_BUSCAR_DIALOGOS_GUION
				});
				storeGrid.load({
					
					callback: function(r, options, success){
						if(!success){
						//console.log(storeGrid.reader.jsonData.actionErrors[0]);
						Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),storeGrid.reader.jsonData.actionErrors[0]);
						grid21.store.removeAll();
						endMask();
						}else{
							endMask();
						}
						
					}
				});
			} 
   			 
			//reloadComponentStore(Ext.getCmp('grid21'), _params, cbkReload);
		}
	}
});
		    
function cbkReload(_r, _options, _success, _store) {

		if (!_success) {
						_store.removeAll();
						//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
	}
};

root = new Tree.TreeNode({
            text: 'Scripts Disponibles',                               
            
            draggable:false, 
            id:'listaDatos'
        });
tree.setRootNode(root);

	    
var	el_formpanel = new Ext.FormPanel ({
		//el: "div_form",
		id: 'elformapanelId',
        frame : true,
        header: false,
        labelWidth: 100,
        height: 560,
        width: 560,
        layout: 'table',
        layoutConfig: {columns: 2},
        url: _ACTION_BUSCAR_DIALOGOS_GUION,
      // defaults: {labelWidth: 100},
        items: [
        		{
        			layout: 'form',
        		 
        			height: 40,
        			labelWidth: 42, 
        			
        			items: [
        				{
        				 
        				 	xtype: 'combo',
        					tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
        					store: dsTiposGuion,
        					displayField:'descripcion',
        					valueField:'codigo',
        					hiddenName: 'tipoGuion',
        					typeAhead: true,
        					mode: 'local',
        					triggerAction: 'all',
        					
        					//fieldLabel: "Tipo de Guion",
        					
                            fieldLabel: getLabelFromMap('tipoGuionId',helpMap,'Gui&oacute;n'),
                            tooltip:getToolTipFromMap('tipoGuionId',helpMap,'Descripcion del Gui&oacute;n'), 
                            hasHelpIcon:getHelpIconFromMap('tipoGuionId',helpMap),								 
                            Ayuda: getHelpTextFromMap('tipoGuionId',helpMap),
        					
        					forceSelection: true,
        					width: 200,
        					emptyText:'Seleccione Tipo de Guion...',
        					selectOnFocus:true,
        					//labelSeparator:'',
        					allowBlank : false,
        					id:'tipoGuionId',
        					onSelect: function (record) {
        							this.setValue(record.get("codigo"));
        								
        							cargarPaneles(record.get("codigo"));
        							if (record.get("codigo")=='1')
        							{
        								Ext.getCmp('encPenId').show();
        							}else{
        								Ext.getCmp('encPenId').hide();
        							}

                   					this.collapse();
               						}
          					            				
        				}
        			]
        		},
        		{
        			layout: 'form',
        				labelWidth: 15, 
        			
        			height: 35,
        			items: [
        				     {
        				      xtype: 'textfield', 
        				      labelSeparator: '', 
        				      width: 235,
        				      readOnly: true,
        				      
                              hasHelpIcon:getHelpIconFromMap('LabelParametri',helpMap),								 
                              Ayuda: getHelpTextFromMap('LabelParametri',helpMap),
        				      id:'LabelParametri',
        				      
        				      name: 'funcionNombre',
        				      id:'funcionNombreId'/*, 
        				      value: txt +' - '+ CODIGO_USUARIO*/
        				     }
        			       ]
        		},
        		{
        			layout: 'form',
        			labelWidth: 42, 
        			colspan: 2,
        			height: 30,
        			items: [
        				     {
        				      xtype: 'textfield', 
        				      labelSeparator: '',
        				      width: 200, 
        				      readOnly: true, 
                              hasHelpIcon:getHelpIconFromMap('LabelGuion',helpMap),								 
                              Ayuda: getHelpTextFromMap('LabelGuion',helpMap),
        				      id:'LabelGuion',
        				      value: 'Script - Guión Atención Inicial'
        				      }
        			       ]
        		},
        		{
        			layout: 'form',
        			labelAlign: 'top',
        			height: 285,
        			items: [
        			         grid21
        				   ]
        		},
        		{
        			layout: 'form',
        			height: 285,
        			items: [
        				     tree
        				   ]
        		},
        		{
        			layout: 'form',
        			
        			layoutConfig: {columns: 1},
        			defaults: {labelWidth: 60},
        			
        			items:[
        					{
								layout: 'form',
								
        						labelAlign: 'top',
        						//height: 160,
        						items:[
        								{
		            						xtype: 'textarea', 
		            					//	labelSeparator: 'Observaciones',
		            						width:250,
		            						//height: 70,
                                            fieldLabel: getLabelFromMap('observacionId',helpMap,'Observaciones'),
                                            tooltip:getToolTipFromMap('observacionId',helpMap,'Observaciones'), 
                                            hasHelpIcon:getHelpIconFromMap('observacionId',helpMap),								 
                                            Ayuda: getHelpTextFromMap('observacionId',helpMap),
		            						cols: 70,
		            						id: 'observacionId'
        								}
        							]
        					},
        					{
        						layout: 'form',
        						buttonAlign: 'center',
        						buttons:[
	          									{
	              									text:getLabelFromMap('catScrAtnBsqBttnGuardar', helpMap,'Guardar'),
													tooltip:getToolTipFromMap('catScrAtnBsqBttnGuardar', helpMap,'Guarda las Observaciones'),
	          										//text: 'Guardar',
	              									handler: function(){
	              												guardarObservacion();
	              									}
	          									},
	          									
	          									{
							text: getLabelFromMap('catScrAtnBsqBttnRegresar',helpMap,'Cancelar'),
						    tooltip:getToolTipFromMap('catScrAtnBsqBttnRegresar',helpMap,'Cancelar'), 
		            		//text:'Regresar',
		            		handler:function(){ventana.close()}
		            	}		            	
		            	
      										]
		            					  }
        				  ]
        		},
        		{
        			id: 'encPenId',
        			layout: 'form',
        			labelAlign: 'top',
        			//rowspan:2,
        			height: 220,
        			items: [
        				//{
        					/*xtype: 'textarea', 
        					labelSeparator: 'Encuestas Pendientes',
        					width:250, 
        					cols: 70*/
        				gridUrl	,
        				
        				
        				{
        						layout: 'form',
        						buttonAlign: 'center',
        						buttons:[
	          									
		            	
		            	{
	              									text:getLabelFromMap('catScrAtnBsqBttnEncuesta', helpMap,'Encuesta'),
													tooltip:getToolTipFromMap('catScrAtnBsqBttnEncuesta', helpMap,'Ingreso de Encuestas'),
	          																					
													 handler:function(){
		            			            				
			            			if(getSelectedKey(gridUrl, "cdPerson")!=""){
			                			window.location.href = IR_INGRESAR_ENCUESTAS + "?cdperson=" + getSelectedKey(gridUrl, "cdPerson") +
			                																	'&cdunieco='+getSelectedKey(gridUrl, "cdUnieco")+
																								 '&cdramo='+getSelectedKey(gridUrl, "cdRamo") +
																								 '&nmpoliza=' + getSelectedKey(gridUrl, "nmPoliza")+
																								 '&estado='+getSelectedKey(gridUrl, "estado")+
																								 '&nmpoliex='+getSelectedKey(gridUrl, "nmPoliex")+
																								 '&dsunieco='+getSelectedKey(gridUrl, "dsUnieco")+
																								 '&dsramo='+getSelectedKey(gridUrl, "dsRamo")+
																								 '&nombrePersona='+getSelectedKey(gridUrl, "dsPerson");
																								 
																																																
			                		}
			                		else{
			                			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));			                			
			                		}
			                	
		                	}
													
											
	          									}
		            	
		            	
      										]
		            					  }
        				
        				
        			
        			]
        		}
		]
});

var ventana = new Ext.Window ({
			//title: '<span style="color:black;font-size:14px;">'+getLabelFromMap('wndwCnsltPlzs',helpMap,'Consulta de P&oacute;lizas')+'</span>',
		    //title: '<span style="color:black;font-size:12px;">Consulta de P&oacute;lizas</span>',
		    width: 560,
		    modal: true,
		    autoHeight: true,
		    items: [el_formpanel]
		    
		    
		});
		
		ventana.show();




function cargarPaneles(guion){

	startMask('elformapanelId', getLabelFromMap('400070',helpMap,'Cargando datos ...'));
	while(root.firstChild){
	                 root.removeChild(root.firstChild);
	}
		
	Ext.Ajax.request ({
			url: _ACTION_OBTENER_DATOS_TREE,
			method: 'GET',
			params :{
					CdTipGui: guion,
					cdElemento: _cdElemento
					},
			success: function (result, request) {
				jsonData = Ext.util.JSON.decode(result.responseText).listaDatos;
	
				for (var i=0; i<jsonData.length; i++) {
					root.appendChild(tree.getLoader().createNode(jsonData[i]));
				}
				
			    //tree.render();
				tree.getRootNode().expand();
				storeGrid.baseParams= { 
						   'cdTipGui': guion,
						   'indInicial': 1
					     };
				storeGrid.proxy = new Ext.data.HttpProxy({
						url : _ACTION_OBTENER_DIALOGO_GUION_INICIAL
				});
				storeGrid.load({
					
				callback: function(r, options, success){
								if(!success){
								//console.log(storeGrid.reader.jsonData.mensajeError);
								Ext.MessageBox.alert('Buscar',storeGrid.reader.jsonData.mensajeError);
								grid21.store.removeAll();
								endMask();
								}else
								{
									endMask();
								}
								
						}
				});
			}
		});
		

}

function cargarEncuestaPendientes (_cdperson) {
	storeGridUrl.load({
		//params:{cdPerson:(_cdperson)?_cdperson:CDPERSON,cdElemento:""},
			params :{
					cdPerson: _cdperson
					},
			waitMsg: getLabelFromMap('400028', helpMap,'Leyendo datos...'),
            callback: function (records, options, success) {if (!success){Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), getLabelFromMap('400033', helpMap,'No se encontraron registros')); gridUrl.storeGridUrl.removeAll();}}
			
	});
}
			            				
			
 
el_formpanel.render();
//el_formpanel.form.load();

	

dsTiposGuion.load({
		callback:function(r,o,success){	
					if (success){
					 cargarPaneles('1');
					 Ext.getCmp('encPenId').show();
					 Ext.getCmp('tipoGuionId').setValue('1');	
					 cargarEncuestaPendientes(_cdperson);
					 storeFuncionNombre.load(
					 {
					 	callback:function(record,opt,success)
	 					{
        					Ext.getCmp('funcionNombreId').setValue(storeFuncionNombre.reader.jsonData.funcionNombre);
    					} 
					 
					 });
				}
				
		}
});
		

function guardarObservacion()
{
        var params = {
                 cdUsuario: CODIGO_USUARIO,
                 cdPerson: _cdperson,
                 dsObservacion: Ext.getCmp('observacionId').getValue()
             };
        execConnection (_ACTION_GUARDAR_DIALOGO_OBSERVACION, params, cbkGuardar);
}

function cbkGuardar (success, message) {
    if (success) {
          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), message,Ext.getCmp('observacionId').setValue(""));
    } else {
          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), message)
    }
}	

function crearHLink (value, metadata, record, rowIndex, colIndex, storeGridUrl) {
	if (record.data['dsUrl'] != "" && record.data['dsUrl'] != undefined) {
		return '<a target="_blank" href="' + record.data['dsUrl'] + '">' + value + '</a>';
	} else {
		return value;
	}
}


}
