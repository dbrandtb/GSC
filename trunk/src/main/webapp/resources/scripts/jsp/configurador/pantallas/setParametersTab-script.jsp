<%@ include file="/taglibs.jsp"%>

var componente = ' ';

/*************************************************************
** Combo Activacion de Conjunto
**************************************************************/ 
var myData = [
        ['S',TAB_3_ACCION_OPCION_1],
        ['N',TAB_3_ACCION_OPCION_2]
   ];

var storeActivacion = new Ext.data.SimpleStore({
        fields: [
           {name: 'idAccion'},
           {name: 'descripcion'}
          
       ]
    });
storeActivacion.loadData(myData);

  var cdProceso = new Ext.form.TextField({
    name:'cdProceso',
    id:'cdProceso',
    type:'hidden',
    hidden:true,
    labelSeparator:''
 });
 
  var cdProcesoActivacion = new Ext.form.TextField({
    name:'cdProcesoActivacion',
    id:'cdProcesoActivacion',
    type:'hidden',
    hidden:true,
    labelSeparator:''
 });
 
  var cdPantalla = new Ext.form.TextField({
    name:'cdPantalla',
    type:'hidden',
    hidden:true,
    labelSeparator:'',
    id: 'cdPantallaTest'
 });
 
 var cdProducto = new Ext.form.TextField({
    name:'cdProducto',
    id:'cdProducto',
    type:'hidden',
    hidden:true,
    labelSeparator:''
 });
 
 var cdProductoActivacion = new Ext.form.TextField({
    name:'cdProductoActivacion',
    id:'cdProductoActivacion',
    type:'hidden',
    hidden:true,
    labelSeparator:''
 });
 
 var cdCliente = new Ext.form.TextField({
    name:'cdCliente',
    id:'cdCliente',
    type:'hidden',
    hidden:true,
    labelSeparator:''
 });
 
var tipoMaster = new Ext.form.TextField({
    name:'tipoMaster',
    id:'tipoMaster',
    type:'hidden',
    hidden:true,
    labelSeparator:''
 });

 var cdTipoMaster = new Ext.form.TextField({
    name:'cdTipoMaster',
    id:'cdTipoMaster',
    type:'hidden',
    hidden:true,
    labelSeparator:''
 });
 
 
 var claveSituacion = new Ext.form.TextField({
    name:'claveSituacion',
    id:'claveSituacion',
    type:'hidden',
    hidden:true,
    labelSeparator:''
 });
 
 
 
 
 var dsArchivo = new Ext.form.TextField({
    name:'dsArchivo',
    id:'dsArchivo',
    type:'hidden',
    hidden:true,
    labelSeparator:''
 });

/*************************************************************
** Store 
**************************************************************/ 

var storeProceso = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({                
                url: 'configuradorpantallas/obtenerListasParamConjunto.action'
            }),
            reader: new Ext.data.JsonReader({
            root: 'procesoList',
            id: 'value'
            },[
           {name: 'value', type: 'string', mapping:'value'},
           {name: 'label', type: 'string', mapping:'label'}  
             ]),
            remoteSort: true
        });
        storeProceso.setDefaultSort('value', 'desc');
        storeProceso.load();

  
  var  storeCliente = new Ext.data.Store({
           proxy: new Ext.data.HttpProxy({                
                url: 'configuradorpantallas/obtenerListasParamConjunto.action'
           }),
            reader: new Ext.data.JsonReader({
            root: 'clienteList',
            id: 'cdCliente'
            },[
           {name: 'cdCliente', type: 'string',mapping:'cdCliente'},
           {name: 'dsCliente', type: 'string',mapping:'dsCliente'}    
            ]),
            remoteSort: true
        });
        storeCliente.setDefaultSort('cdCliente', 'desc');
        storeCliente.load();
       
  
var storeRegistro;
function getRegistro(){
storeRegistro = new Ext.data.Store({
         proxy: new Ext.data.HttpProxy({               
                url: 'configuradorpantallas/obtenerRegistro.action'           
          }),
            reader: new Ext.data.JsonReader({
            root: 'registroList',
            id: 'cdConjunto'
            },[
           {name: 'cdConjunto', type: 'string',mapping:'cdConjunto'},
           {name: 'proceso', type: 'string',mapping:'proceso'},
           {name: 'cdProceso', type: 'string',mapping:'cdProceso'},
           {name: 'cdProducto', type: 'string',mapping:'cdRamo'},
           {name: 'cdCliente', type: 'string',mapping:'cdCliente'},
           {name: 'cliente', type: 'string',mapping:'cliente'},
           {name: 'producto', type: 'string',mapping:'dsRamo'},
           {name: 'nombreConjunto', type: 'string',mapping:'nombreConjunto'},
           {name: 'descripcion', type: 'string',mapping:'descripcion'},
           {name: 'situacion', type: 'string',mapping:'tipoSituacion'},
           {name: 'dsTipoSituacion', type: 'string',mapping:'dsTipoSituacion'}
           ]),
            remoteSort: true
        });
        storeRegistro.load({
        	callback: function () {
        		//console.log(storeRegistro);
        		storeComboRolXNivel.load({
					params: {pv_cdelemento_i: storeRegistro.reader.jsonData.registroList[0].cdCliente}
        		});
        	}
        });
        
        return storeRegistro;
}

var storeRegistroPantalla;
var clavePantalla = ' ';
function getRegistroPantalla(clavePantalla){   
storeRegistroPantalla = new Ext.data.Store({
         proxy: new Ext.data.HttpProxy({            
            url: 'configuradorpantallas/obtenerRegistroPantalla.action'+'?idP='+clavePantalla
           
          }),
            reader: new Ext.data.JsonReader({
            root: 'registroPantallaList',
            id: 'cdPantalla'
            },[
           {name: 'cdPantalla', type: 'string',mapping:'cdPantalla'},
           {name: 'nombrePantalla', type: 'string',mapping:'nombrePantalla'},
           {name: 'htipoPantalla', type: 'string',mapping:'cdMaster'},
           {name: 'htipo', type: 'string',mapping:'tipoMaster'},
           {name: 'descripcionPantalla', type: 'string',mapping:'descripcion'},
           {name: 'dsArchivo', type: 'string',mapping:'dsArchivo'}
            ]),
            remoteSort: true
        });
        storeRegistroPantalla.load();
        return storeRegistroPantalla;
}


var storeTipoPantalla = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({             
                url: 'configuradorpantallas/obtenerMasters.action'
            }),
            reader: new Ext.data.JsonReader({
            root: 'tipoPantallaList'/*,
            id: 'cdMaster'*/
            },[
           {name: 'cdTipo', type: 'string', mapping:'cdTipo'},
           {name: 'cdMaster', type: 'string', mapping:'cdMaster'},
           {name: 'dsMaster', type: 'string', mapping:'dsMaster'}    
             ]),
            remoteSort: true,
            baseParams: {cdProceso: cdProceso.getValue() }
        });
        storeTipoPantalla.setDefaultSort('cdMaster', 'desc');
        storeTipoPantalla.load();                
        


 /*************************************************************
** Combos del combosFieldSet (funcion onchange)
**************************************************************/ 
        
var comboProceso = new Ext.form.ComboBox({
    name: 'proceso',
    id: 'proceso',
    fieldLabel: TAB_1_PROCESO,
    store: storeProceso,
    displayField:'label',
    hiddenName: 'hproceso',  
    valueField:'value',
    typeAhead: true,
    mode: 'local',
    triggerAction: 'all',
    //listWidth : 200,
    width:200,
    emptyText: TAB_1_PROCESO_EMPTY,
    editable: true,
    allowBlank: false,
    labelSeparator:'', 
    forceSelection: true,
    selectOnFocus:true
});

 var comboNivel = new Ext.form.ComboBox({
     name: 'cliente',
     id: 'cliente',
     fieldLabel: TAB_1_NIVEL,
     store: storeCliente,
     displayField:'dsCliente',
     hiddenName: 'hcliente',  
     valueField:'cdCliente',
     typeAhead: true,
     //listWidth : 200,
     width: 200,
     mode: 'local',
     triggerAction: 'all',
     emptyText: TAB_1_NIVEL_EMPTY,
     editable: true,
     allowBlank: false,
     labelSeparator:'', 
     forceSelection: true,
     selectOnFocus:true
  });
  
  
  var storeProducto = new Ext.data.Store({
           proxy: new Ext.data.HttpProxy({            
               url: 'configuradorpantallas/obtenerListaProductos.action'
          }),
            reader: new Ext.data.JsonReader({
            root: 'productoList',
            id: 'value'
            },[
           {name: 'value', type: 'string',mapping:'value'},
           {name: 'label', type: 'string',mapping:'label'}    
            ]),
            remoteSort: true,
            baseParams: {hcliente: comboNivel.getValue()}
           
        });
      
        storeProducto.setDefaultSort('value', 'desc');
        storeProducto.load();  
 
  var storeTipoSituacion = new Ext.data.JsonStore({
			url : 'configuradorpantallas/obtenerListaSituaciones.action',
			root : 'situacionesList',
			remoteSort : true,
			autoLoad : true,

			sortInfo : {
				field : 'value',
				direction : 'DESC'
			},
			fields : [{
						name : 'value',
						type : 'string',
						mapping : 'value'
					}, {
						name : 'label',
						type : 'string',
						mapping : 'label'
					}]
		})       
        
  
 var comboProducto = new Ext.form.ComboBox({
     name: 'producto',
     id: 'producto',
     fieldLabel: TAB_1_PRODUCTO,
     store: storeProducto,
     displayField:'label',
     hiddenName: 'hproducto',  
     valueField:'value',
     typeAhead: true,
     //listWidth : 200,
     width: 200,
     mode: 'local',
     triggerAction: 'all',
     emptyText: TAB_1_PRODUCTO_EMPTY,
     selectOnFocus:true,
     forceSelection: true,
     editable: true,
   //   tpl: '<tpl for="."><div ext:qtip="{value}. {nick}" class="x-combo-list-item">{label}</div></tpl>',
     labelSeparator:'',
     
     listeners :{
     	select : function(combo, record){
     		Ext.getCmp('situacion').clearValue();
     		Ext.getCmp('situacion').store.removeAll();
     		Ext.getCmp('situacion').store.baseParams['parameterValueItem'] = combo.value;
     		Ext.getCmp('situacion').store.load();
     	}     	
     }     
     
     
  });
  
  comboNivel.on('select', function(){
    comboProducto.clearValue();
    storeProducto.baseParams['hcliente'] = comboNivel.getValue();
    storeProducto.load({
                  callback : function(r,options,success) {
                      if (!success) {                          
                         storeProducto.removeAll();
                      }
                  }
              }
            );
    Ext.getCmp('comboRol').clearValue();
    storeComboRolXNivel.load({
		params: {pv_cdelemento_i: comboNivel.getValue()}
    });
   });
   
   
   var comboTipoSituacion = new Ext.form.ComboBox({
     name: 'situacion',
     id: 'situacion',
     fieldLabel: 'Tipo de Riesgo',
     store: storeTipoSituacion,
     displayField:'label',
     hiddenName: 'hsituacion',  
     valueField:'value',
     typeAhead: true,
     //listWidth : 200,
     width: 200,
     mode: 'local',
     triggerAction: 'all',
     emptyText: TAB_1_PRODUCTO_EMPTY,
     selectOnFocus:true,
     forceSelection: true,
     editable: true,
   //   tpl: '<tpl for="."><div ext:qtip="{value}. {nick}" class="x-combo-list-item">{label}</div></tpl>',
     labelSeparator:''
  });
   
  
  
/*************************************************************
** Textfields del datosFieldSet
**************************************************************/ 

var nombreConjunto = new Ext.form.TextField({
     fieldLabel: TAB_1_NOMBRE_CONJUNTO,
     name:'nombreConjunto',
     allowBlank: false,
     width : 250,
     maxLengthText:'30'
   });

var descripcion = new Ext.form.TextArea({
    fieldLabel: TAB_1_DESCRIPCION,
    name:'descripcion',
    allowBlank: false,
    width : 250,
    grow: false,
    preventScrollbars:true
   });

var cdConjunto = new Ext.form.TextField({
    name:'cdConjunto',
    type:'hidden',
    hidden:true,
    labelSeparator:'',
    id: 'cdConjuntoTest'
   
 });
 
 
/*************************************************************
** Textfields y combo del FormPanel del TAB 2
**************************************************************/ 

var nombrePantalla = new Ext.form.TextField({
     fieldLabel: TAB_2_NOMBRE,
     name:'nombrePantalla',
     allowBlank: false,
     width : 250,
     maxLengthText:'30'
   });
 
 var descripcionPantalla = new Ext.form.TextArea({
     fieldLabel: TAB_2_DESCRIPCION,
     name:'descripcionPantalla',
     allowBlank: false,
     grow: false,
     height:40,  
     preventScrollbars:true,
     width : 250
     
   });   
   
    var comboTipoPantalla = new Ext.form.ComboBox({
			name : 'tipoPantalla',
			id : 'tipoPantalla',
			fieldLabel : TAB_2_MASTER,
			store : storeTipoPantalla,
			displayField : 'dsMaster',
			hiddenName : 'htipoPantalla',
			valueField : 'cdMaster',
			typeAhead : true,
			mode : 'local',
			triggerAction : 'all',
			listWidth : 250,
			width : 240,
			emptyText : TAB_2_MASTER_EMPTY,
			editable : true,
			forceSelection: true,
			//autoHeight : 'true',
			allowBlank : false,
			labelSeparator : '',
			focusAndSelect : function(record) {
				var index = typeof record === 'number' ? record : this.store.indexOf(record);
				this.select(index, this.isExpanded());
				this.onSelect(this.store.getAt(record), index, this.isExpanded());
			},
			onSelect : function(record, index, skipCollapse) {
				if (this.fireEvent('beforeselect', this, record, index) !== false) {
					this.setValue(record.data[this.valueField || this.displayField]);
					if (!skipCollapse) {
						this.collapse();
					}
					this.lastSelectedIndex = index + 1;
					this.fireEvent('select', this, record, index);
				}
				
				cdTipoMaster.setValue(record.get('cdMaster'));

				tipoMaster.setValue(record.get('cdMaster'));
				
			},

			selectOnFocus : true/*,
			
			view : new Ext.DataView({
				autoHeight:true

			})*/		
			
		});
   

/*************************************************************
** Textfields y combo del FormPanel del TAB 3
**************************************************************/ 
   var comboActivacion = new Ext.form.ComboBox({
    name: 'activacion',
    id: 'activacion',
    fieldLabel: TAB_3_ACCION,
    listWidth : 215,
    store: storeActivacion,
    displayField:'descripcion',
    hiddenName: 'hactivacion',  
    valueField:'idAccion',
    typeAhead: true,
    mode: 'local',
    triggerAction: 'all',
    width:200,
    emptyText: TAB_3_ACCION_EMPTY,
    editable: true,
    forceSelection: true,
    autoHeight:'true',
    allowBlank: false,
    selectOnFocus:true
}); 
