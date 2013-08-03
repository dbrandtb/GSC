Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

var codRazon = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('codRazon',helpMap,'Clave'),
        tooltip:getToolTipFromMap('codRazon',helpMap,'Clave de la configuraci&oacute;n del motivo de cancelaci&oacute;n'), 
        hasHelpIcon:getHelpIconFromMap('codRazon',helpMap),
		Ayuda: getHelpTextFromMap('codRazon',helpMap),
        id: 'codRazon', 
        name: 'codRazon',
        allowBlank: false,
        anchor: '85%',
        readOnly: true,
        disabled : true, 
        value: CODIGO_RAZON
    });

//Ext.Msg.alert('Codigo','de la razon ' +codRazon.getValue());

//combos
var desRehabilitacion = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_REHABILITACION_MOTIVOS_CANCELACION}),
        reader: new Ext.data.JsonReader({
        root: 'siNo',
        id: 'codigo'
        },[
       {name: 'codigo', type: 'string', mapping:'codigo'},
       {name: 'descripcion', type: 'string', mapping:'descripcion'}
    ]),
    remoteSort: true
});

var desVerPagos = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_VERPAGOS_MOTIVOS_CANCELACION}),
        reader: new Ext.data.JsonReader({
        root: 'siNo',
        id: 'codigo'
        },[
       {name: 'codigo', type: 'string', mapping:'codigo'},
       {name: 'descripcion', type: 'string', mapping:'descripcion'}
    ]),
    remoteSort: true
});

//la grilla
var lasFilas=new Ext.data.Record.create([
  {name: 'cdRazon',  mapping:'cdRazon',  type: 'string'},
  {name: 'cdAdvert',  mapping:'cdAdvert',  type: 'string'},
  {name: 'dsAdvert',  mapping:'dsAdvert',  type: 'string'}                  
]);

//el JsonReader de la grilla a mostrar
var jsonGrilla= new Ext.data.JsonReader(
  {
   root:'rcEstructuraList',
   totalProperty: 'totalCount',
   successProperty : '@success'
  },
 lasFilas
);

function storeGrilla(){
       store = new Ext.data.Store({
       proxy: new Ext.data.HttpProxy({
             url: _ACTION_BUSCAR_MOTIVOS_CANCELACION_REQUISITOS,
             waitMsg: 'Espere por favor....'
            }),
       reader:jsonGrilla
       });
       return store;
}

function addGridNewRecord () {
	var new_record = new lasFilas({
						cdRazon: CODIGO_RAZON,
						cdAdvert: '',
						dsAdvert: ''
					});
	grid2.stopEditing();
	grid2.store.insert(0, new_record);
	grid2.startEditing(0, 0);
	grid2.getSelectionModel().selectRow(0);
}
var grid2;

// definicion de la grilla de requisitos
var cm = new Ext.grid.ColumnModel([
        {
        header: getLabelFromMap('confMotCancCmClav',helpMap,'Requisitos'),
        tooltip: getToolTipFromMap('confMotCancCmClav',helpMap,'Requisitos de configurar motivo de cancelaci&oacute;n'),
        dataIndex: 'dsAdvert',
        width: 330,
        sortable: true,
        resizable: true,
        editor: new Ext.form.TextField({
		           				allowBlank: true,
		           				id: 'dsAdvert'
		           		})
        },
        {
        dataIndex: 'cdRazon',
        hidden :true
        },
        {
        dataIndex: 'cdAdvert',
        hidden :true
        }
]);


function createGrid(){
    grid2= new Ext.grid.EditorGridPanel({
         store:storeGrilla(),
         reader:jsonGrilla,
         id:'grid2',
         border:true,
         cm: cm,
         clicksToEdit:1,
         successProperty: 'success',
         buttonAlign: "center",
         buttons:[
               {
               text:getLabelFromMap('confMotCancBtnAdd',helpMap,'Agregar'),
               tooltip: getToolTipFromMap('confMotCancBtnAdd',helpMap,'Agrega un nuevo Requisito de Cancelaci&oacute;n'),
               handler:function(){agregar()}
               },
               {
               text:getLabelFromMap('confMotCancBtnDel',helpMap,'Eliminar'),
               tooltip: getToolTipFromMap('confMotCancBtnDel',helpMap,'Elimina un Requisito de Cancelaci&oacute;n'),
               handler:function(){
				if (!grid2.getSelectionModel().getSelected()) {
					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));	               
	   			}
	   			else
	   			if (grid2.getSelectionModel().getSelected().get("cdAdvert")==""){
	   					grid2.getSelectionModel().getSelected().set("cdAdvert", "borrar");
	   					grid2.store.remove(grid2.getSelectionModel().getSelected());
	   				}else{
	   	    		    borrar(getSelectedRecord(grid2));
	   				}
               }
               }
               ],
         width:100,
         frame:true,
         height:230,
         title:'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">'+getLabelFromMap('99',helpMap,'Requisitos')+'</span>',
         sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
         stripeRows: true,
         collapsible: true,
         bbar: new Ext.PagingToolbar({
		        //pageSize:10,
		        store: store,
		        displayInfo: false,
               // displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
				emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
             })           
      });
//grid2.render()
}

createGrid();





//el JsonReader de la parte izquierda
var elJson = new Ext.data.JsonReader(
    {
        root : 'MEstructuraList',
        totalProperty: 'total',
        successProperty : '@success'
    },
    [ 
    {name: 'codRazon',  mapping:'cdRazon',  type: 'string'},
    {name: 'dsRazon',  mapping:'dsRazon',  type: 'string'},  
    {name: 'swReInst',  mapping:'swReInst',  type: 'string'},
    {name: 'swVerPag',  mapping:'swVerPag',  type: 'string'}  
    ]
)

       
var incisosForm = new Ext.FormPanel({
	id: 'incisosForm',
    el:'formBusqueda',
    title: (CODIGO_RAZON)?getLabelFromMap('incisosFormEd',helpMap,'Editar Configurar Motivos de Cancelaci&oacute;n'):getLabelFromMap('incisosFormAg',helpMap,'Agregar Configurar Motivos de Cancelaci&oacute;n'),
    iconCls:'logo',
    buttonAlign: "center",
    bodyStyle : 'background: white',      
    labelAlign: 'right',
    frame:true,   
    url: _ACTION_GET_MOTIVOS_CANCELACION,
    //baseParams: {cdRazon: codRazon.getValue()},
    reader: elJson,
    width: 700,
    height:330,
    items: [{
    		layout:'form',
            border: false,
            bodyStyle : 'background: white',      
            items:[{
    		        title :'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Motivo</span>',
                    labelWidth: 100,
                    layout: 'form',
                    frame:true,
     	            baseCls: '',
     	            buttonAlign: "center",
    		        items: [{
    		        	     layout:'column',
			                 border:false,
			                 items:[{
		    	                columnWidth:.4,
            				    layout: 'form',
              		            border: false,
    		        		    items:[  		        				
                                       codRazon,
                                       { 
                                       fieldLabel: getLabelFromMap('dsRazon',helpMap,'Razon'),
                                       tooltip:getToolTipFromMap('dsRazon',helpMap,'Razon del motivo de cancelaci&oacute;n '),
                                       hasHelpIcon:getHelpIconFromMap('dsRazon',helpMap),
									   Ayuda: getHelpTextFromMap('dsRazon',helpMap),
									   xtype: 'textfield', 
                                       // fieldLabel: "Razon",
                                       allowBlank: false, 
                                        name: 'dsRazon', 
                                        anchor: '85%',
                                        id: 'dsRazon'
                                       },
                                       {
                                        xtype: 'combo', 
                                        labelWidth: 50,
                                        tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                                        store: desRehabilitacion,
                                        forceSelection:true,
                                        allowBlank: false,
                                        displayField:'descripcion',
                                        valueField:'codigo',
                                        hiddenName: 'swReInst',
                                        typeAhead: true,
                                        mode: 'local',
                                        triggerAction: 'all',
                                        fieldLabel: getLabelFromMap('comboRehabilitacion',helpMap,'Permite Rehabilitaci&oacute;n'),
                                        tooltip:getToolTipFromMap('comboRehabilitacion',helpMap,'Permite Rehabilitaci&oacute;n'),
                                        hasHelpIcon:getHelpIconFromMap('comboRehabilitacion',helpMap),
									    Ayuda: getHelpTextFromMap('comboRehabilitacion',helpMap),
                                        anchor: '85%',
                                        emptyText:'Seleccione Rehabilitación...',
                                        selectOnFocus:true,
                                        id:'comboRehabilitacion'
                                       }/*,
                                       {
                                        xtype: 'combo', 
                                        labelWidth: 50,
                                        tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                                        store: desVerPagos,
                                        forceSelection:true,
                                        displayField:'descripcion',
                                        valueField:'codigo',
                                        hiddenName: 'swVerPag',
                                        typeAhead: true,
                                        mode: 'local',
                                        triggerAction: 'all',
                                        fieldLabel: getLabelFromMap('comboVerPagos',helpMap,'Desea Ver Pagos'),
                                        tooltip:getToolTipFromMap('comboVerPagos',helpMap,'Desea Ver Pagos '),
                                        hasHelpIcon:getHelpIconFromMap('comboVerPagos',helpMap),
									    Ayuda: getHelpTextFromMap('comboVerPagos',helpMap),
                                        anchor: '85%',
                                        emptyText:'Seleccione ...',
                                        selectOnFocus:true,
                                        id:'comboVerPagos'
                                       } */                   
                                      ]
								},
								{
								columnWidth:.6,
                				layout: 'fit',
                				items:[
                				     grid2
                				]
                				}]
                			}],
                			buttons:[{
					                text:getLabelFromMap('confMotCancBtnSave',helpMap,'Guardar'),
					                tooltip: getToolTipFromMap('confMotCancBtnSave',helpMap,'Guarda el motivo y los requisitos de Cancelaci&oacute;n'),
        							handler: function(){ 
        							if (incisosForm.form.isValid())
        									guardarConfiguracionMotivosCancelacion(incisosForm);
        									
        							else{
                							Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
										}
									}
        							},{
					                text:getLabelFromMap('confMotCancBtnBack',helpMap,'Regresar'),
					                tooltip: getToolTipFromMap('confMotCancBtnBack',helpMap,'Regresa a Motivos de Cancelaci&oacute;n'),
        							handler: function() {
        							//window.location=_ACTION_REGRESAR_A_MOTIVOS_CANCELACION
	                           			window.location.href = _ACTION_REGRESAR_A_MOTIVOS_CANCELACION+"?codRazon="+FILTRO_CODIGO_RAZON+"&dsRazon="+DESCRIPCION_RAZON;
        							}
        						}]
        					}]
        				}]	            
        
});   

function reloadGrid(){
	var _params = {
			cdRazon: CODIGO_RAZON
	};
	reloadComponentStore(grid2, _params, cbkReload);
}
function cbkReload(_r, _options, _success, _store) {
	if (!_success){
		_store.removeAll();
		//addGridNewRecord();
	}
}

function getSelectedRecord(){
               var m = grid2.getSelections();
               if (m.length == 1 ) {
                  return m[0];
               }
   }

function borrar(record) {
	if(record != "")
	{
		Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
		{
	        if (btn == "yes")
	        {
	        	var _params = {
	                   cdRazon: record.get('cdRazon'),
	                   cdAdvert: record.get('cdAdvert')
	        	};
	        	execConnection(_ACTION_BORRAR_MOTIVOS_CANCELACION_REQUISITOS, _params, cbkConnection);
            }
		})
	}else{
			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
    }
};

function cbkConnection(_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid();});
	}
}
incisosForm.render();



if (CODIGO_RAZON!="")
{
	incisosForm.form.load({
		waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
	    waitMsg: getLabelFromMap('400028',helpMap,'Leyendo datos ...'),
	    params: {cdRazon: codRazon.getValue()},
	    success: function(form, action) {
	    var _swReInst = action.reader.jsonData.MEstructuraList[0].swReInst;
		var _swVerPag = action.reader.jsonData.MEstructuraList[0].swVerPag;
		// CARGAMOS LOS COMBOS
	 	desRehabilitacion.load({
    		callback: function (r, options, success){
    		if (success) {
						  incisosForm.findById('comboRehabilitacion').setValue(_swReInst);
						}
    			desVerPagos.load({
    				callback: function (r, options, success){
    				if (success) {
						  //incisosForm.findById('comboVerPagos').setValue(_swVerPag);
								}
    					}
    				});
    			}
    		});           			
	               			reloadGrid();
	               			//incisosForm.findById('dsRazon').focus();
	               			
	   },
	   failure: function () {
	                        Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), getLabelFromMap('400033', helpMap,'No se encontraron registros'));
	                        }                       
	});
} else {
	
	// esto es para ocultar el campo Clave al agregar
	Ext.override(Ext.form.Field, {
    showContainer: function() {
        this.enable();
        this.show();
        this.getEl().up('.x-form-item').setDisplayed(true);
    },
    
    hideContainer: function() {
        this.disable();
        this.hide();
        this.getEl().up('.x-form-item').setDisplayed(false);
    },
    
    setContainerVisible: function(visible) {
        if (visible) {
            this.showContainer();
        } else {
            this.hideContainer();
        }
        return this;
    }
});
	
	codRazon.setContainerVisible(false);
	
	desRehabilitacion.load({
		callback: function () {
			desVerPagos.load();
		}
	});
}

 
    


function agregar()
{
 addGridNewRecord();
}

function guardarConfiguracionMotivosCancelacion(incisosForm){
    var _url="";
    if (CODIGO_RAZON!="")
    {
      _url=_ACTION_GUARDAR_MOTIVOS_CANCELACION;
    }
    else
    {
    _url=_ACTION_INSERTAR_MOTIVOS_CANCELACION;
    }
	    var arreglo = "motivoCancelacionVO.cdRazon="+CODIGO_RAZON+"&motivoCancelacionVO.dsRazon="+incisosForm.findById("dsRazon").getValue()+"&motivoCancelacionVO.swReInst="+incisosForm.findById("comboRehabilitacion").getValue()+/*"&motivoCancelacionVO.swVerPag="+incisosForm.findById("comboVerPagos").getValue()+*/"&";
		    arreglo +=generarStringGrillaRequisitos(incisosForm.findById('grid2'));
	var conn = new Ext.data.Connection();
            conn.request({
   	               url: _url,
   	               method: 'POST',
   	               params: arreglo,
   	               callback: function (options, success, response) {
   	                        
	                       if (Ext.util.JSON.decode(response.responseText).success == false) {
	                           Ext.Msg.alert('Error', Ext.util.JSON.decode(response.responseText).errorMessages[0]);
	                       } else {
	                           CODIGO_RAZON = Ext.util.JSON.decode(response.responseText).codRazon;
	                           Ext.Msg.alert('Confirmacion', Ext.util.JSON.decode(response.responseText).actionMessages[0], function(){
	                           			//reloadGrid();
	                           			window.location.href = _ACTION_REGRESAR_A_MOTIVOS_CANCELACION+"?codRazon="+FILTRO_CODIGO_RAZON+"&dsRazon="+DESCRIPCION_RAZON;
	                           });
                           }
	                   }
             });
}

function generarStringGrillaRequisitos(grilla){
		var arreglo = "";
		var registros = grilla.store.getModifiedRecords();
		grilla.stopEditing();
		for (var i=0; i<registros.length; i++) {
			if (registros[i].get('cdAdvert') != 'borrar') {
				arreglo += "motivoCancelacionVO.requisitoCancelacionVOs["+i+"].cdRazon="+CODIGO_RAZON+"&";
				arreglo += "motivoCancelacionVO.requisitoCancelacionVOs["+i+"].cdAdvert="+registros[i].get('cdAdvert')+"&";
				arreglo += "motivoCancelacionVO.requisitoCancelacionVOs["+i+"].dsAdvert="+registros[i].get('dsAdvert')+"&";
			}
		}
		return arreglo;
	}

         
});
