<script type="text/javascript">
Ext.onReady(function(){
Ext.form.Field.prototype.msgTarget = "side";
    var grid2;
    var selectedId;
    var codigoPlan;
    var codigoProducto;
    var descripcionProducto;
    var descripcionPlan;
    var cm;
    var store;
    var ds;
function toggleDetails(btn, pressed){
            var view = grid.getView();
            view.showPreview = pressed;
            view.refresh();
        }
       
        var cm = new Ext.grid.ColumnModel([
            new Ext.grid.RowNumberer(),
            {header: "Clave",                   dataIndex:'key',    width: 100, sortable:true},         
            {header: "Nombre de la cobertura",  dataIndex:'value',  width: 220, sortable:true}
                                    
        ]);
function getSelectedRecord(){
        var m = grid2.getSelections();
            if (m.length == 1 ) {
                return m[0];
             }
    }
function createGrid(){
    grid2= new Ext.grid.GridPanel({
        id:'grid-planes-configuracion-coberturas',
        store:test(),
        border:true,
        cm: cm,                                                     
        width:500,
        frame:true,
        height:320,        
        title:'Coberturas',
        
        
        sm: new Ext.grid.RowSelectionModel({
            singleSelect: true,
            listeners: {                            
                        rowselect: function(sm, row, rec) {                                                                                                                                     
                        }
                }
        
        }),
        tbar:[{
            text:'Agregar',
            tooltip:'Agregar un nuevo plan',
            handler:function(){
            agregar(ds);}
        },'-',{
            id:'editar',
            text:'Editar',
            tooltip:'Edita plan seleccionado',
            handler:function(){
                if (getSelectedRecord() != null) {
                    editarCatalogoPlanes();                                 
                    Ext.getCmp('editFormPlan').getForm().loadRecord(getSelectedRecord());   
                } else {
                Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operacion');
                }
            }
        }],
        viewConfig: {autoFill: true,forceFit:true},                
        bbar: new Ext.PagingToolbar({
            pageSize:25,
            store: store,                                               
            displayInfo: true,
            displayMsg: 'Mostrando registros {0} - {1} de {2}',
            emptyMsg: "No hay resultados que mostrar"                      
            })                                                                                               
        });     
    }
    createGrid();
    
    
function test(){                                        
    url='planes/ListaCoberturas.action';                                
    store = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: url
        }),
        reader: new Ext.data.JsonReader({
            root:'coberturas',
            id:'cobertura',   
            totalProperty: 'totalCount'             
            },[
            {name: 'key',   type: 'string',  mapping:'key'},
            {name: 'value', type: 'string',  mapping:'value'}               
        ]),
        remoteSort: true,
        baseParams: {codigoPlan:''}
    });
    store.setDefaultSort('cobertura', 'desc');
    //store.load();
    return store;
        }
    var dsComboCobertura = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url:_ACTION_COBERTURAS+'?combo='+'cobertura'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaCoberturas',
            id: 'comboCobertura'
            },[
                {name: 'key',   type: 'string', mapping:'key'},
                {name: 'value', type: 'string', mapping:'value'}      
            ]),
        remoteSort: true
    });
    
    
    ds = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'planes/ListaPlanes.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'planes',
            id: 'listas'
            },[
                {name: 'key',   type: 'string',  mapping:'key'},
                {name: 'value', type: 'string',  mapping:'value'}    
            ]),
            remoteSort: true
        });
    
    ds.setDefaultSort('listas', 'desc');

/***************************************
*Se crea la ventana de agregar planes.  
****************************************/
agregar = function() {
    
    
    var comboCobertura =new Ext.form.ComboBox({
            tpl: '<tpl for="."><div ext:qtip="{key}" class="x-combo-list-item">{value}</div></tpl>',
            store: dsComboCobertura,                        
            displayField:'value',
            listWidth: '250',
            allowBlank:false,
            blankText :'Seleccione cobertura',
            valueField: 'key',                          
            mode: 'local',
            triggerAction: 'all',                           
            selectOnFocus:true,
            emptyText:'Seleccione cobertura...',
            fieldLabel:'Cobertura*',                 
            name:"descripcionCobertura",
            selectFirst : function() {
                this.focusAndSelect(this.store.data.first());
            },
            focusAndSelect : function(record) {
                var index = typeof record === 'number' ? record : this.store.indexOf(record);
                this.select(index, this.isExpanded());
                this.onSelect(this.store.getAt(record), index, this.isExpanded());
            },
            onSelect : function(record, index, skipCollapse){
                if(this.fireEvent('beforeselect', this, record, index) !== false){
                    this.setValue(record.data[this.valueField || this.displayField]);
                    if( !skipCollapse ) {
                        this.collapse();
                    }
                    this.lastSelectedIndex = index + 1;
                    this.fireEvent('select', this, record, index);
                }
                var valor=record.get('key');
                Ext.getCmp('hidden-combo-cobertura-planes').setValue(valor);
            }                           
        });
    var separador = new Ext.form.TextField({
        fieldLabel: '',
        hiddeParent:true,
        labelSeparator:'',
        hidden:true
        
    });
    
    
    var formPanel = new Ext.form.FormPanel({
        url:'planes/asociaCoberturaPlan.action',
        baseCls: 'x-plain',
        labelWidth: 75,
        width: 480,
        frame:true,
        height:130,
        items:[separador,{
                layout:'column',
                border:false,
                width: '450',
                baseCls: 'x-plain',
                items: [{columnWidth:.65,
                    layout:'form',
                    labelAlign:'left',
                    baseCls: 'x-plain',
                    border:false,
                    items:[comboCobertura,{xtype:'hidden',id:'hidden-combo-cobertura-planes',name:'codigoCondicion'}]
                    },{
                        columnWidth:.35,
                        layout:'form',              
                        border:false,
                        baseCls: 'x-plain',
                            items:[
                                {xtype:'button', 
                                text: 'Agregar al Catalogo', 
                                name: 'AgregarCatalogo',
                                buttonAlign:'left', 
                                handler: function() {
                                    creaAltaCatalogoCoberturas(dsComboCobertura);
                                    
                                }}
                            ]   
                        }]
                }]
        });

        var windowPlan = new Ext.Window({
            title: 'Configuracion de coberturas del plan',
            plain:true,
            modal:true,      
            width: 480,
            height:130,
            minWidth: 300,
            minHeight: 100,
            buttonAlign:'center',
            items: formPanel,
            buttons: [{
                text: 'Guardar', 
                handler: function() {                
                    if (formPanel.form.isValid()) {
                        formPanel.form.submit({ 
                            waitTitle:'Espere',
                            waitMsg:'Procesando...',
                            failure: function(form, action) {
                                Ext.MessageBox.alert('Error','Error al asociar cobertura');
                                windowPlan.close();                             
                            },
                            success: function(form, action) {
                                Ext.MessageBox.alert('Estado','Cobertura asociada al Plan');
                                windowPlan.close();
                                store.load();
                            }
                        });                   
                    }else{
                        Ext.MessageBox.alert('Error', 'Inserte Campos Requeridos');
                    }             
                }
            },{
                text: 'Regresar',
                handler: function(){
                    windowPlan.close();
                }
            }]
        });
    windowPlan.show();
};
/*******CREAMOS LA VENTANA DE AGREGAR AL CATALOGO*************/
agregarCatalogoPlanes = function(ds){
    var clavePlan = new Ext.form.TextField({
        fieldLabel: 'Clave*',
        allowBlank: false,
        name:'cdPlan',
        blankText : 'Campo requerido',
        anchor:'90%'    
    });
    var nombrePlan = new Ext.form.TextField({
        fieldLabel: 'Nombre*',
        allowBlank: false,
        name:'dsPlan',
        blankText : 'Campo requerido',
        anchor:'90%'    
    });  
    var catalogoForm= new Ext.FormPanel({
        id:'agregar-catalogo-planes',
        labelWidth: 75,
        baseCls: 'x-plain',
        width: 350,
        frame:true,
        height:140,
        url:'planes/GuardaPlan.action',
        items:[clavePlan, nombrePlan]
    });
    var windowPlanAgregar = new Ext.Window({
            title: 'Agregar Nuevo Plan',
            plain:true,
            modal:true,      
            width: 350,
            height:140,
            minWidth: 300,
            minHeight: 100,
            buttonAlign:'center',
            items: catalogoForm,
            buttons: [{
                text: 'Guardar', 
                handler: function() {                
                    if (catalogoForm.form.isValid()) {
                        catalogoForm.form.submit({  
                            waitTitle:'Espere',
                            waitMsg:'Procesando...',
                            failure: function(form, action) {
                                Ext.MessageBox.alert('Error','Error agregando un plan');
                                windowPlanAgregar.close();                              
                            },
                            success: function(form, action) {
                                Ext.MessageBox.alert('Estado','Plan Creado Exitosamente');
                                windowPlanAgregar.close();
                                ds.load()                               
                                Ext.getCmp('arbol-productos').getRootNode().reload();                            
                            }
                        });                   
                    }else{
                        Ext.MessageBox.alert('Error', 'Inserte Campos Requeridos');
                    }             
                }
            },{
                text: 'Cancelar',
                handler: function(){
                    windowPlanAgregar.close();
                }
            }]
        });
    windowPlanAgregar.show();
      
};
/*******TERMINAMOS LA VENTANA DE AGREGAR AL CATALOGO*************/

 /*************************************
*Crea la ventana de editar
 **************************************/    
editarCatalogoPlanes = function(){

    var key = new Ext.form.TextField({
        fieldLabel: 'Clave*',
        allowBlank: false,
        name:'key',
        blankText : 'Campo requerido',
        anchor:'90%'    
    });
    var value = new Ext.form.TextField({
        fieldLabel: 'Nombre*',
        allowBlank: false,
        name:'value',
        blankText : 'Campo requerido',
        anchor:'90%'    
    });  
    
    var editForm= new Ext.FormPanel({
        id:'editFormPlan',
        labelWidth: 75,
        baseCls: 'x-plain',
        width: 350,
        frame:true,
        height:140,
        url:'planes/GuardaPlan.action',
        items:[key, value]
    });
    
    var windowEditarPlan = new Ext.Window({
        title: 'Editar Plan',
        plain:true,
        modal:true,      
        width: 350,
        height:140,
        minWidth: 300,
        minHeight: 100,
        buttonAlign:'center',
        items: editForm,
        buttons: [{
                    text: 'Guardar', 
                    handler: function() {
                        if (editForm.form.isValid()) {
                                editForm.form.submit({
                                    waitTitle:'Espere',
                                    waitMsg:'Procesando...',
                                    failure: function(form, action) {
                                        Ext.MessageBox.alert('Estado','Error en la Edicion ');
                                        windowEditarPlan.close();
                                    },
                                    success: function(form, action) {
                                        Ext.MessageBox.alert('Estado','Plan Editado Exitosamente');
                                        windowEditarPlan.close();
                                        store.load();                                      
                                    }
                                });                   
                        }else{
                            Ext.MessageBox.alert('Error', 'Inserte Campos Requeridos');
                        }             
                    }
                },{
                    text: 'Cancelar',
                    handler: function(){
                        windowEditarPlan.close();
                    }
                }]
        });     
    windowEditarPlan.show();
};


/******************
*Form para el grid. 
*******************/   
    var complemento = new Ext.form.TextField({
        fieldLabel: '',
        hiddeParent:true,
        labelSeparator:'',
        hidden:true
        
    });
    var separador2 = new Ext.form.TextField({
        fieldLabel: '',
        hiddeParent:true,
        labelSeparator:'',
        hidden:true
        
    });
    var separador3 = new Ext.form.TextField({
        fieldLabel: '',
        hiddeParent:true,
        labelSeparator:'',
        hidden:true
        
    });
    var productoLista = new Ext.form.TextField({
        fieldLabel: 'Riesgo',
        name:'cdRamoB',
        width:200
    });
     
    var codigoPlan = new Ext.form.ComboBox({
                id:'codigo-form-plan-recarga-store-arbol',
                tpl: '<tpl for="."><div ext:qtip="{key}. {value}" class="x-combo-list-item">{value}</div></tpl>',
                store: ds,
                width:180,
                mode: 'local',
                name: 'codigoPlan',
                typeAhead: true,
                labelSeparator:'',
                triggerAction: 'all',           
                displayField:'value',
                listWidth: '185',
                forceSelection: true,
                fieldLabel: 'Plan',
                emptyText:'Seleccione un Plan...',
                selectOnFocus:true,
                selectFirst : function() {
                                this.focusAndSelect(this.store.data.first());
                            },
                            focusAndSelect : function(record) {
                                var index = typeof record === 'number' ? record : this.store.indexOf(record);
                                this.select(index, this.isExpanded());
                                this.onSelect(this.store.getAt(record), index, this.isExpanded());
                            },
                            onSelect : function(record, index, skipCollapse){
                                if(this.fireEvent('beforeselect', this, record, index) !== false){
                                    this.setValue(record.data[this.valueField || this.displayField]);
                                    if( !skipCollapse ) {
                                        this.collapse();
                                    }
                                    this.lastSelectedIndex = index + 1;
                                    this.fireEvent('select', this, record, index);
                                }
                                var valor=record.get('key');
                                var mStore = grid2.store;
                                mStore.baseParams = mStore.baseParams || {};
                                mStore.baseParams['codigoPlan'] = valor;
                                mStore.reload({
                                      callback : function(r,options,success) {
                                            if (!success) {
                                                 Ext.MessageBox.alert('No se encontraron registros');
                                                 mStore.removeAll();
                                              }
                                      }

                                  });
                                //Ext.getCmp('hidden-combo-plan-cobertura').setValue(valor);
                                
                            }
    
        });  
    
    var incisosForm = new Ext.form.FormPanel({
        title: 'Configuracion de planes del producto',
        bodyStyle:'background: white',
        labelAlign: 'left',
        labelWidth:50,  
        frame:true,  
        url:'',                        
        width: 520,  
        items:[{
                layout:'form',
                border:true,
                buttonAlign:'left',
                width: '510',
                items: [{
                        layout:'column',
                        border:true,
                        width: '510',
                        baseCls: 'x-plain',
                        items: [{               
                                    columnWidth:.7,
                                    layout:'form',
                                    width:'280',
                                    labelAlign:'left',
                                    baseCls: 'x-plain',
                                    border:false,
                                    items:[separador2,codigoPlan]
                                    },{
                                    columnWidth:.3,
                                    layout:'form',             
                                    border:false,
                                    width: '170',
                                    baseCls: 'x-plain',
                                    items:[separador3, 
                                            {xtype:'button',
                                            text: 'Agregar al Catalogo',
                                            name: 'AgregarCatalogo',
                                            buttonAlign:'left',
                                            handler: function() {
                                            agregarCatalogoPlanes(ds);
                                            }
                                            }]  
                                    }]
                            },grid2]
                    }]                      
        });
        incisosForm.render('centerPlanes');
    
    ds.load();
    dsComboCobertura.load();
});

</script>

