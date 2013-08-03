Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

function muestraMsgEligeRecordDeGrid(idBtn, grid) {
    Ext.getCmp(idBtn).on('click',function(){
        if( !grid.getSelectionModel().hasSelection() ) {
            Ext.Msg.alert(_MSG_AVISO, _MSG_NO_ROW_SELECTED);
        }
    });
}

//Store para grid de Cobreturas
function createOptionGrid(){  
    url=_ACTION_LISTA_COBERTURAS 
    storeCob = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: url
        }),
        reader: new Ext.data.JsonReader({
            root: 'listCoberturasIncisos',
            id: 'cdGarant'
        },[
                {name: 'cdGarant',              type: 'string',    mapping:'cdGarant'},
                {name: 'dsGarant',              type: 'string',    mapping:'dsGarant'},
                {name: 'dsSumaAsegurada',       type: 'string',    mapping:'dsSumaAsegurada'},
                {name: 'dsSumaAseguradaCapita', type: 'string',    mapping:'dsSumaAseguradaCapita'},
                {name: 'dsSumaText',            type: 'string',    mapping:'dsSumaText'},
                {name: 'dsDeducible',           type: 'string',    mapping:'dsDeducible'}
        ]),
        remoteSort: true
    });
    storeCob.setDefaultSort('cdGarant', 'desc');
    storeCob.load();
    return storeCob;
}
var storeCob;
//Store para grid de Accesorios
function createGridAccesorios(){
    storeA = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
            url: 'flujoendoso/accesoriosIncisos.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listAccesoriosIncisos',
            id: 'nmObjeto'
        },[
                {name: 'nmObjeto',        type: 'string',    mapping:'nmObjeto'},
                {name: 'dsObjeto',        type: 'string',    mapping:'dsObjeto'},
                {name: 'ptObjeto',        type: 'string',    mapping:'ptObjeto'},
                {name: 'ptObjeto2',       type: 'string',    mapping:'ptObjeto'},
                {name: 'cdTipoObjeto',    type: 'string',    mapping:'cdTipoObjeto'}
        ]),
        remoteSort: true
    });
    storeA.setDefaultSort('nmObjeto', 'desc');
    storeA.load();
    return storeA;
}
var storeA;

//DATOS DE APOYO
    var idAseguradora = new Ext.form.Hidden({
        name:'cdUnieco',
        value:_cdunieco
    });
    var idProducto = new Ext.form.Hidden({
        name:'cdRamo',
        value:_cdramo
    });
    var idPoliza = new Ext.form.Hidden({
        name:'nmpoliza',
        value:_nmpoliza
    });
    var idEstado = new Ext.form.Hidden({
        name:'estado',
        value:_estado
    });
    var idNmSituac = new Ext.form.Hidden({
        name:'nmsituac',
        value:_nmsituac
    });
    var idStaus = new Ext.form.Hidden({
        name:'status',
        value:_status
    });
    var idTipSit = new Ext.form.Hidden({
        name:'cdTipsit',
        value:_cdtipsit
    });
    var nombreAseguradora = new Ext.form.TextField({
        fieldLabel: 'Aseguradora',
        name:'dsUnieco',
        value:_aseguradora,
        disabled:true,
        width: 200
    });
    var nombreProducto = new Ext.form.TextField({
        fieldLabel: 'Producto',
        name:'dsRamo',
        value:_producto,
        disabled:true,
        width: 200
    });
    var numPoliza = new Ext.form.TextField({
        fieldLabel: 'Poliza',
        name:'poliza',
        value:_poliza,
        disabled:true,
        width: 200
    });
    var numInciso = new Ext.form.TextField({
        fieldLabel: 'Inciso',
        name:'cdInciso',
        value:_cdinciso,
        disabled:true,
        width: 200
    });
    var nombreDescripcion = new Ext.form.TextField({
        fieldLabel: 'Calculo Pago',
        name:'dsDescripcion',
        value:_dsdescripcion,
        disabled:true,
        width: 200
    });
    
    var opcionesForm = new Ext.form.FormPanel({    
        el:'encabezadoInciso',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',   
        frame:true,                         
        width: 680,
        autoHeight: true,
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
                        items:[{
                            layout:'column',
                            border:false,
                            items:[{
                                layout: 'form',                     
                                border: false,                      
                                items:[ 
                                        nombreAseguradora,
                                        nombreProducto,
                                        numPoliza
                                ]
                            },
                            {
                                layout: 'form',                     
                                border: false,                      
                                items:[ 
                                        numInciso,
                                        nombreDescripcion
                                ]
                            }]
                        }]
                }]
        }]  
    });
    opcionesForm.render();

//DATOS INCISOS
var datosIncisosForm = new Ext.form.FormPanel({
    id:'datosIncisosForm',
    border: false,
    width: 776,
    url: 'flujoendoso/agregarDatosAdicionales.action' ,
    items:  ElementosExt,
    autoScroll:true,
    buttons:[BotonVariablesExt]
});
    
//******************DETALLE COBERTURAS******************
var itemsVariables;
var valorCombo;

function detalleCoberturas(cdGarant,dsGarant,dsSumaAsegurada,dsDeducible,dsSumaAseguradaCapita){
    var conn = new Ext.data.Connection();
    conn.request({
         url: 'flujoendoso/datosCoberturas.action?cdUnieco='+idAseguradora.getValue()+'&cdRamo='+idProducto.getValue()+'&estado='+idEstado.getValue()+'&nmPoliza='+idPoliza.getValue()+'&nmSituac='+idNmSituac.getValue()+'&cdGarant='+cdGarant+'&status='+idStaus.getValue()+'&sumaAsegurada='+dsSumaAsegurada+'&dsDeducible='+dsDeducible,
         method: 'POST',
         successProperty : '@success',
         callback: function (options, success, response){
                     if(Ext.util.JSON.decode(response.responseText).success == false){
                         Ext.Msg.alert('Error', 'Ocurrio un error en la transaccion');
                     }else{
                         var codeExtResp = Ext.util.JSON.decode(response.responseText).extElements;
                         var codeExtJson = Ext.util.JSON.encode(codeExtResp);
                         
                         if(codeExtResp != ''){
                            var newStartStore = "\"store\":";
                            var newEndStore = ",\"triggerAction\"";
                            var onSelectVar = "";
                            
                            codeExtJson = codeExtJson.replace(/\"store\":\"/gi, newStartStore);
                            codeExtJson = codeExtJson.replace(/\",\"triggerAction\"/gi, newEndStore);
                            codeExtJson = codeExtJson.replace(/,\"onSelect\":null/gi, onSelectVar);
                            
                            var itemsDecode = Ext.util.JSON.decode(codeExtJson);
                            
                            var campoIncis = new Ext.form.TextField({                              
                                name:'inciso',
                                value:idNmSituac.getValue(),
                                fieldLabel: 'Inciso',
                                disabled:true,
                                width: 200
                            });
                            
                            var campoGrant = new Ext.form.TextField({                               
                                name:'dsGarant',
                                value:dsGarant,
                                fieldLabel: 'Cobertura',
                                disabled:true,
                                width: 200
                            });
                            var hcampoGrant = new Ext.form.TextField({  
                                fieldLabel: '',
                                name:'cdGarant',
                                value:cdGarant,
                                labelSeparator:'',
                                hidden:true
                            });
                            var campoSuma = new Ext.form.NumberField({                              
                                name:'sumaAsegurada',
                                value:dsSumaAseguradaCapita,
                                fieldLabel: 'Suma Asegurada',
                                width: 200,
                                allowDecimals: true,
                                allowNegative: false
                            });
                             
                            var editarCoberturaForm = new Ext.form.FormPanel({
                                    id:'editar-forma-cobertura-window',
                                    url:_ACTION_EDITAR_COBERTURA,
                                    boder:false,
                                    frame:true,
                                    autoScroll:true,
                                    method:'post',                          
                                    width: 570,
                                    buttonAlign: "center",
                                    baseCls:'x-plain',
                                    labelWidth:75,   
                                    items:[
                                            campoGrant,
                                            campoSuma,                                          
                                                {  
                                                layout:'form',                                  
                                                border:false,
                                                frame:true,
                                                method:'post',  
                                                baseCls:'x-plain',
                                                autoScroll:true,                            
                                                items: itemsDecode                                               
                                                },
                                            hcampoGrant
                                            ]
                                    });
                             
                             var windowCoberturaEditar = new Ext.Window({
                                title: 'Datos Coberturas',
                                width: 460,
                                height:380,
                                autoScroll:true,
                                maximizable:true,
                                minWidth: 440,
                                minHeight: 300,
                                layout: 'fit',
                                modal:true,
                                plain:true,
                                bodyStyle:'padding:5px;',
                                buttonAlign:'center',
                                items: editarCoberturaForm,
                                buttons:[{
                                    text: 'Guardar', 
                                        tooltip: 'Guardar cambios',
                                        handler: function(){
                                            if (editarCoberturaForm.form.isValid()) {
                                                editarCoberturaForm.form.submit({
                                                	waitTitle:'Espere',
                                                    waitMsg:'Procesando...',
                                                    failure: function(form, action) {
                                                        Ext.MessageBox.alert('Error', 'No se pudo editar la Cobertura');
                                                        windowCoberturaEditar.close();                                                      
                                                    },
                                                    success: function(form, action) {
                                                        Ext.MessageBox.alert('Cobertura editada', 'Se editó la Cobertura');
                                                        windowCoberturaEditar.close();
                                                        storeCob.load();
                                                    }
                                                });
                                            }else{
                                                Ext.MessageBox.alert('Error', 'Por favor revise los errores!');
                                            }
                                        }
                                    },{
                                    text: 'Regresar',
                                    tooltip: 'Cierra la ventana',
                                    handler: function(){
                                        windowCoberturaEditar.close();
                                        windowCoberturaEditar.destroy();
                                    }//handler
                                }]
                            });
                            windowCoberturaEditar.show();
                         }
                      }//else
                   }//function
    });
}
var importe='0';
var validaCobertura;
//******************AGREGAR COBERTURAS******************
agregarCobertura = function(storeCob, validaRepeat){

                            var connCtaCob = new Ext.data.Connection();
                                                                    
                            connCtaCob.request ({
                                url:_ACTION_CUENTA_COBERTURAS,
                                method: 'POST',
                                successProperty : '@success',
                                    callback: function (options, success, response) {                         
                                        validaCobertura = Ext.util.JSON.decode(response.responseText).agregarCoberturas;
                                        if (validaCobertura==false && validaRepeat==true) {
                                            Ext.Msg.alert('Aviso', "Ya seleccionó todas las Coberturas posibles");
                                            windowCobertura.close();
                                        }
                                    }
                                });
                                
                                var storeComboCoberturasSelect = new Ext.data.Store({
                                        proxy: new Ext.data.HttpProxy({
                                            url: _ACTION_CARGA_COBERTURAS
                                        }),
                                        reader: new Ext.data.JsonReader({
                                            root: 'listaCoberturas'
                                            },[
                                           {name: 'value',      type: 'string', mapping:'value'},
                                           {name: 'label',      type: 'string', mapping:'label'}
                                        ]),
                                        remoteSort: true
                                });
                                storeComboCoberturasSelect.setDefaultSort('label', 'desc');
                                storeComboCoberturasSelect.load();
                                
                                var campoIncis = new Ext.form.TextField({                              
                                    	name:'inciso',
	                                    value:_cdinciso,
	                                    fieldLabel: 'Inciso',
	                                    disabled:true,
	                                    width: 200
                                });
                                
                                var comboCoberturas = new Ext.form.ComboBox({
                                        allowBlank:false,
                                        id:'combo-coberturas',
                                        tpl: '<tpl for="."><div ext:qtip="{label}. {value}" class="x-combo-list-item">{label}</div></tpl>',
                                        store: storeComboCoberturasSelect,
                                        displayField:'label',
                                        valueField:'value',
                                        typeAhead: true,
                                        labelAlign: 'top',
                                        heigth:'50',
                                        listWidth:'150',        
                                        mode: 'local',
                                        triggerAction: 'all',
                                        emptyText:'Seleccione...',
                                        selectOnFocus:true,
                                        fieldLabel: 'Cobertura',
                                        name:"cobertura",
                                        hiddenName:"hcobertura",
                                        onSelect : function(record, index, skipCollapse){
                                            if(this.fireEvent('beforeselect', this, record, index) !== false){
                                                this.setValue(record.data[this.valueField || this.displayField]);
                                                if( !skipCollapse ) {
                                                    this.collapse();
                                                }
                                                this.lastSelectedIndex = index + 1;
                                                this.fireEvent('select', this, record, index);
                                            }
                                                                                    
                                            var valor=record.get('value');
                                            var label=record.get('label');
                                            var params="";                  
                                            params  = "claveObjeto="+valor;
                                            params += "&& descripcionObjeto="+label;
                                            
                                            var conn = new Ext.data.Connection();
                                            
                                            conn.request ({
                                                url:_ACTION_CARGA_ITEMS,
                                                method: 'POST',
                                                successProperty : '@success',
                                                params : params,
                                                callback: function (options, success, response) {                         
                                                    var itemsVariablesDecode = Ext.util.JSON.decode(response.responseText).extElementsAgregar;
                                                    var itemsVariablesEncode = Ext.util.JSON.encode(itemsVariablesDecode);
                                                    if(itemsVariablesDecode != ''){
                                                        var newStartStore = "\"store\":";
                                                        var newEndStore = ",\"triggerAction\"";
                                                        var onSelectVar = "";
                                                        
                                                        itemsVariablesEncode = itemsVariablesEncode.replace(/\"store\":\"/gi, newStartStore);
                                                        itemsVariablesEncode = itemsVariablesEncode.replace(/\",\"triggerAction\"/gi, newEndStore);
                                                        itemsVariablesEncode = itemsVariablesEncode.replace(/,\"onSelect\":null/gi, onSelectVar);
                                                    }
                                                    itemsVariables = Ext.util.JSON.decode(itemsVariablesEncode);
                                                    validacion = Ext.util.JSON.decode(response.responseText).validoElementsAgregar;
                                                    valorCombo = Ext.util.JSON.decode(response.responseText).labelCombo;
                                                    importe = Ext.util.JSON.decode(response.responseText).importe;
                                                    if(validacion==true){                               
                                                        windowCobertura.close();
                                                        agregarCobertura(storeCob, false);
                                                        Ext.getCmp('combo-coberturas').setValue(valorCombo);
                                                    }/* else{
                                                        Ext.MessageBox.alert('Error', 'No existen datos asociados');                                                
                                                    }*/                                           
                                                }
                                            });
                                        }
                                });
                                
                                var campoSuma = new Ext.form.NumberField({                              
                                    fieldLabel: 'Suma Asegurada',
                                    width: 200,
                                    blankText : 'Suma Asegurada ',
                                    allowBlank:false,
                                    name:'sumaAsegurada',
                                    value:importe 
                                });
                                
                                var agregarCoberturaForm = new Ext.form.FormPanel({
                                    url:_ACTION_GUARDA_COBERTURAS,
                                    id:'agregarCoberturaForm',
                                    boder:false,
                                    frame:true,
                                    autoScroll:true,
                                    method:'post',                          
                                    width: 540,
                                    buttonAlign: "center",
                                    baseCls:'x-plain',
                                    labelWidth:75,                              
                                    items:[
                                            comboCoberturas,
                                                {  
                                                layout:'form',                                  
                                                border:false,
                                                frame:true,
                                                method:'post',  
                                                baseCls:'x-plain',
                                                autoScroll:true,                            
                                                items:[campoSuma]                                               
                                                },  
                                                {  
                                                layout:'form',                                  
                                                border:false,
                                                frame:true,
                                                method:'post',  
                                                baseCls:'x-plain',
                                                autoScroll:true,                            
                                                items: itemsVariables                                               
                                                }                                       
                                            ]
                                 });
                
                                 itemsVariables = null;
                                 
                                 var windowCobertura = new Ext.Window({
                                    title: 'Agregar Coberturas',
                                    width: 460,
                                    height:380,
                                    autoScroll:true,
                                    maximizable:true,
                                    minWidth: 440,
                                    minHeight: 300,
                                    layout: 'fit',
                                    modal:true,
                                    plain:true,
                                    bodyStyle:'padding:5px;',
                                    buttonAlign:'center',
                                    items: agregarCoberturaForm,
                                    buttons:[{
                                        text: 'Guardar',
                                        tooltip: 'Guardar',
                                        handler: function(){
                                            agregarCoberturaForm.form.submit({
                                                url:'', 
                                                waitTitle:'Espere',
                                                waitMsg:'Procesando...',
                                                failure: function(form, action) {
                                                    Ext.MessageBox.alert('Estado','La Cobertura no se guard&oacute');
                                                },
                                                success: function(form, action){
                                                    if(Ext.util.JSON.decode(action.response.responseText).saved == "true" ||
                                                            Ext.util.JSON.decode(action.response.responseText).mensajeError == null ||
                                                            Ext.util.JSON.decode(action.response.responseText).mensajeError == ''){
                                                        Ext.MessageBox.alert('Estado','Cobertura Guardada');
                                                        itemsVariables = null;
                                                        windowCobertura.close();
                                                        storeCob.load();
                                                    }else {
                                                        Ext.Msg.alert('Error', Ext.util.JSON.decode(action.response.responseText).mensajeError +". Intente de nuevo.");
                                                    }
                                                }
                                            });     
                                        }
                                        },{
                                        text: 'Regresar',
                                        tooltip: 'Cierra la ventana',
                                        handler: function(){
                                            windowCobertura.close();
                                        }//handler
                                    }]
                                });
                                windowCobertura.show();
};

    /*****se crea la ventana de borrar una Cobertura***/
    var msgBorrar = new Ext.form.TextField({
        fieldLabel:'¿Esta seguro que desea Eliminar la Cobertura?',
        labelSeparator:'',
        anchor:'90%',
        hidden:true
    });                   
   
    var _cdGarant = new Ext.form.TextField({
        fieldLabel: '',   
        name:'cdGarant',
        anchor:'90%',
        hiddeParent:true,
        labelSeparator:'',
        hidden:true
    });  
    var _dsSumaAsegurada = new Ext.form.NumberField({
        fieldLabel: '',   
        name:'dsSumaAsegurada',
        anchor:'90%',
        hiddeParent:true,
        labelSeparator:'',
        hidden:true
    });   
    var borrarForm= new Ext.FormPanel({
        id:'borrarForm',
        labelAlign:'top',
        baseCls: 'x-plain',
        anchor:'100%',
        url:'flujoendoso/borrarCoberturas.action',
        items:[ msgBorrar, _cdGarant, _dsSumaAsegurada]
    });

    var windowDel = new Ext.Window({
        title: 'Eliminar Cobertura',
        minHeight: 50,
        minWidth: 250,
        width: 250,
        height:120,
        layout: 'fit',
        plain:true,
        modal:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: borrarForm,
        buttons: [{
                text: 'Eliminar', 
                handler: function() {
                    if (borrarForm.form.isValid()) {
                            borrarForm.form.submit({      
                            	waitTitle:'Espere',
                                waitMsg:'Procesando...',
                                failure: function(form, action) {
                                    Ext.MessageBox.alert('Error', 'Error eliminando Cobertura');
                                    windowDel.hide();
                                },
                                success: function(form, action) {
                                    Ext.MessageBox.alert('Exito', 'Cobertura Eliminada');
                                    windowDel.hide();
                                    storeCob.load();                                      
                                }
                            });                   
                    }else{
                        Ext.MessageBox.alert('Error', 'Verifique!');
                    }             
                }
            },{
                text: 'Cancelar',
                handler: function(){windowDel.close();}
            }]
        });

//GRID COBERTURAS
    var storeGrid = createOptionGrid();
    var cm = new Ext.grid.ColumnModel([
        new Ext.grid.RowNumberer(),
        {header: "cdGarant",              dataIndex:'cdGarant',        width: 120, sortable:true, hidden: true},
        {header: "Cobertura",             dataIndex:'dsGarant',        width: 120, sortable:true},
        {header: "dsSumaAsegurada",       dataIndex:'dsSumaAsegurada', width: 120, sortable:true, hidden: true},
        {header: "dsSumaAseguradaCapita", dataIndex:'dsSumaAseguradaCapita', width: 120, sortable:true, hidden: true},
        {header: "Suma Asegurada",        dataIndex:'dsSumaAsegurada', width: 120, sortable:true},
        {header: "Deducible",             dataIndex:'dsDeducible',     width: 120, sortable:true}
    ]);
    var selectedId;

var cdGarant = "";
var dsGarant = "";
var dsSumaAsegurada = "";
var dsDeducible = "";
var dsSumaAseguradaCapita = "";
                    
var gridCoberturas = new Ext.grid.GridPanel({
    store:storeGrid,
    border:true,
    buttonAlign: 'center',
    baseCls:' background:white ',
    cm: cm,
    buttons:[{
                id:'agregarCobertura',
                text:'Agregar',
                tooltip:'Agregar',
                handler:function(){
                    agregarCobertura(storeGrid, true);
                }
            },{
                id:'detallesCobertura',
                text:'Editar',
                tooltip:'Editar'
            },{
                id:'borrarCobertura',
                text:'Eliminar',
                tooltip:'Eliminar Cobertura'
            }],                                 
    width:656,
    frame:true,
    height:200,
    
    sm: new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {                            
            rowselect: function(sm, row, rec) { 
                selectedId = storeGrid.data.items[row].id;                                                                                                                                    
                
                cdGarant = rec.get('cdGarant');
                dsGarant = rec.get('dsGarant');
                dsSumaAsegurada = rec.get('dsSumaAsegurada');
                dsSumaAseguradaCapita = rec.get('dsSumaAseguradaCapita');
                dsDeducible = rec.get('dsDeducible');
                    
                Ext.getCmp('borrarCobertura').on('click', function(){
                    windowDel.show();
                    Ext.getCmp('borrarForm').getForm().loadRecord(rec);
                }); 
            }
        }
        
    }),
    viewConfig: {autoFill: true,forceFit:true}, 
    bbar: new Ext.PagingToolbar({
        pageSize:20,
        store: storeGrid,                                               
        displayInfo: true,
        displayMsg: 'Registros mostrados {0} - {1} de {2}',
        emptyMsg: 'No hay registros para mostrar',
		beforePageText: 'P&aacute;gina',
		afterPageText: 'de {0}'                    
        })
    });

Ext.getCmp('detallesCobertura').on('click',function(){
        if(cdGarant!=""){
            detalleCoberturas(cdGarant, dsGarant, dsSumaAsegurada, dsDeducible, dsSumaAseguradaCapita);
        }else{
            Ext.Msg.alert('Coberturas','No ha seleccionado ningun registro');
        }
});

//DETALLE AGRUPADOR
var codigoPersonaUsuario = new Ext.form.Hidden({    
    id:'hidden-codigo-persona-usuario-compra',
    name:'codigoPersonaUsuario'
}); 

var codigoDomicilio = new Ext.form.Hidden({ 
    id:'hidden-codigo-domicilio-compra',
    name:'codigoDomicilio'
});

var codigoInstrumentoPago = new Ext.form.Hidden({   
    id:'hidden-codigo-intrumeto-pago-compra',
    name:'codigoInstrumentoPago'
});

var codigoBanco = new Ext.form.Hidden({
    id:'hidden-codigo-banco-compra',
    name:'codigoBanco'
}); 

var codigoSucursal = new Ext.form.Hidden({  
    id:'hidden-codigo-sucursal-compra',
    name:'codigoSucursal'
});

var codigoTipoTarjeta = new Ext.form.Hidden({  
    id:'hidden-codigo-tipo-tarjeta',
    name:'codigoTipoTarjeta'
});

var storeComboPersonasUsuarioMultiSelect = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'flujoendoso/listaPersonasUsuarioMultiSelect.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaPersonasUsuarioMultiSelect'
            },[
           {name: 'value',      type: 'string', mapping:'value'},
           {name: 'label',      type: 'string', mapping:'label'}
        ]),
        remoteSort: false
});
storeComboPersonasUsuarioMultiSelect.setDefaultSort('label','DESC');
storeComboPersonasUsuarioMultiSelect.load();
var storeComboPersonasUsuario = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'flujoendoso/listaPersonasUsuario.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaPersonasUsuario'
            },[
           {name: 'value', type: 'string', mapping:'value'},
           {name: 'label', type: 'string', mapping:'label'}
        ]),
        remoteSort: false
});
storeComboPersonasUsuario.setDefaultSort('label','DESC');
storeComboPersonasUsuario.load();
var storeComboTipoTarjeta = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'flujoendoso/listaTipoTarjeta.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaTipoTarjeta'
            },[
           {name: 'value', type: 'string', mapping:'value'},
           {name: 'label', type: 'string', mapping:'label'}
        ]),
        remoteSort: false
});
storeComboTipoTarjeta.setDefaultSort('label','DESC');
storeComboTipoTarjeta.load();

var storeComboInstrumentoDePago = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'flujoendoso/listaFormasDePago.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaIntrumentoPago'
            },[
           {name: 'value', type: 'string', mapping:'value'},
           {name: 'label', type: 'string', mapping:'label'},
           {name: 'extra',      type: 'string', mapping:'extra'}
        ]),
        remoteSort: false
});
storeComboInstrumentoDePago.setDefaultSort('label','DESC');
storeComboInstrumentoDePago.load();
var storeBanco = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'flujoendoso/listaBancos.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaBancos'
            },[
           {name: 'value', type: 'string', mapping:'value'},
           {name: 'label', type: 'string', mapping:'label'}
        ]),
        remoteSort: false
});
storeBanco.setDefaultSort('label','DESC');
storeBanco.load();
var storeSucursal = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'flujoendoso/listaSucursal.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaSucursal'
            },[
           {name: 'value', type: 'string', mapping:'value'},
           {name: 'label', type: 'string', mapping:'label'}
        ]),
        baseParams:{codigoBanco:''},
        remoteSort: false
});
storeSucursal.setDefaultSort('label','DESC');
var comboPersonaUsuario =new Ext.form.ComboBox({
        tpl: '<tpl for="."><div ext:qtip="{label}.{value}" class="x-combo-list-item">{label}</div></tpl>',
        store: storeComboPersonasUsuario,
        displayField:'label',
        editable: true, 
        typeAhead: true,
        labelAlign: 'top',
        heigth:'50',
        listWidth:'150',        
        mode: 'local',
        triggerAction: 'all',
        emptyText:'Seleccione...',
        selectOnFocus:true,
        fieldLabel: 'Pagado Por',
        name:"dsNombre",
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
            var valor = record.get('value');        
            Ext.getCmp('hidden-codigo-persona-usuario-compra').setValue(valor);
            comboDomicilio.clearValue();
            storeComboDomicilio.baseParams['idUser'] = valor;
                storeComboDomicilio.load({
                  callback : function(r, options, success) {
                      if (!success) {
                             storeComboDomicilio.removeAll();
                          }
                      }
                });
        }
});

var storeComboDomicilio = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'flujoendoso/obtieneDomicilio.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'domicilio'
            },[
           {name: 'value', type: 'string', mapping:'value'},
           {name: 'label', type: 'string', mapping:'label'}
        ]),
    remoteSort: false,
    baseParams: {idUser: comboPersonaUsuario.getValue()}
});
storeComboDomicilio.setDefaultSort('label','DESC');
storeComboDomicilio.load();

var comboDomicilio =new Ext.form.ComboBox({
        tpl: '<tpl for="."><div ext:qtip="{label}. {value}" class="x-combo-list-item">{label}</div></tpl>',
        store: storeComboDomicilio,
        displayField:'label',
        valueField: 'value',
        typeAhead: true,
        labelAlign: 'top',
        heigth:'50',
        listWidth:'150',        
        mode: 'local',
        triggerAction: 'all',
        emptyText:'Seleccione...',
        selectOnFocus:true,
        fieldLabel: 'Domicilio',
        name:"dsDomicilio",
        allowBlank:false,
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
            var valor = record.get('value');
            
            Ext.getCmp('hidden-codigo-domicilio-compra').setValue(valor);
        }
});
var comboInstrumentoDePago =new Ext.form.ComboBox({
        tpl: '<tpl for="."><div ext:qtip="{label}. {value}" class="x-combo-list-item">{label}</div></tpl>',
        store: storeComboInstrumentoDePago,
        displayField:'label',
        valueField: 'value',
        typeAhead: true,
        labelAlign: 'top',
        heigth:'50',
        mode: 'local',
        triggerAction: 'all',
        emptyText:'Seleccione...',
        selectOnFocus:true,
        fieldLabel: 'Instrumento de pago',
        name:"dsForpag",
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
            var feedback = record.get('extra');
            var valor = record.get('value');
            if(feedback == 'S'// || valor == "3"
            ){
                Ext.getCmp('id-form-pago-hide').show();
                Ext.getCmp('combo-banco').enable();
                Ext.getCmp('combo-tipo-tarjeta').enable();
                Ext.getCmp('numero-tarjeta').enable();
                Ext.getCmp('fecha-vencimiento').enable();
                Ext.getCmp('digito-verificador').enable();
            }else{
                Ext.getCmp('id-form-pago-hide').hide();
                Ext.getCmp('combo-banco').disable();
                Ext.getCmp('combo-tipo-tarjeta').disable();
                Ext.getCmp('numero-tarjeta').disable();
                Ext.getCmp('fecha-vencimiento').disable();
                Ext.getCmp('digito-verificador').disable();
                Ext.getCmp('combo-banco').reset();
                Ext.getCmp('combo-tipo-tarjeta').reset();
                Ext.getCmp('numero-tarjeta').reset();
                Ext.getCmp('fecha-vencimiento').reset();
                Ext.getCmp('digito-verificador').reset();
            }
            Ext.getCmp('hidden-codigo-intrumeto-pago-compra').setValue(valor);
        }
});
var comboSucursal =new Ext.form.ComboBox({
        id:'descripcion-combo-sucursal-compra',
        tpl: '<tpl for="."><div ext:qtip="{label}. {value}" class="x-combo-list-item">{label}</div></tpl>',
        store: storeSucursal,
        displayField:'label',
        valueField: 'value',
        typeAhead: true,
        labelAlign: 'top',
        heigth:'50',
        listWidth:'150',        
        mode: 'local',
        triggerAction: 'all',
        emptyText:'Seleccione...',
        selectOnFocus:true,
        fieldLabel: 'Sucursal',
        name:"descripcionSucursal",
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
            var valor = record.get('value');
            
            Ext.getCmp('hidden-codigo-sucursal-compra').setValue(valor);
        }
});
var comboBanco =new Ext.form.ComboBox({
        id:'combo-banco',
        tpl: '<tpl for="."><div ext:qtip="{label}. {value}" class="x-combo-list-item">{label}</div></tpl>',
        store: storeBanco,
        displayField:'label',
        valueField: 'value',
        typeAhead: true,
        labelAlign: 'top',
        heigth:'50',
        listWidth:'150',        
        mode: 'local',
        triggerAction: 'all',
        emptyText:'Seleccione...',
        selectOnFocus:true,
        allowBlank:false,
        fieldLabel: 'Banco',
        name:"descripcionBanco",
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
            var valor = record.get('value');
            
            storeSucursal.baseParams['codigoBanco'] = valor;
            storeSucursal.load({
                callback : function(r,options,success) {
                    if (!success) {
                        storeSucursal.removeAll();
                    }
                }
            });
            Ext.getCmp('hidden-codigo-banco-compra').setValue(valor);
        }
});
var comboTipoTarjeta =new Ext.form.ComboBox({
        id:'combo-tipo-tarjeta',
        tpl: '<tpl for="."><div ext:qtip="{label}. {value}" class="x-combo-list-item">{label}</div></tpl>',
        store: storeComboTipoTarjeta,
        displayField:'label',
        valueField: 'value',
        typeAhead: true,
        labelAlign: 'top',
        heigth:'50',
        mode: 'local',
        triggerAction: 'all',
        emptyText:'Seleccione...',
        selectOnFocus:true,
        fieldLabel: 'Tipo de Pago',
        allowBlank:false,
        name:"descripcionTipoTarjeta",
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
            var valor = record.get('value');            
            Ext.getCmp('hidden-codigo-tipo-tarjeta').setValue(valor);
        }
});
    var cdUnieco = new Ext.form.Hidden({
            fieldLabel: '',
            allowBlank: false,   
            name:'cdUnieco',
            width: 300
    });    
    var cdRamo = new Ext.form.Hidden({
            fieldLabel: '',
            allowBlank: false,   
            name:'cdRamo',
            width: 300
    });   
    var estado = new Ext.form.Hidden({
            fieldLabel: '',  
            name:'estado',
            width: 300
    });    
    var nmPoliza = new Ext.form.Hidden({
            fieldLabel: '',  
            name:'nmPoliza',
            width: 300
    });   
    var nmSuplem = new Ext.form.Hidden({
            fieldLabel: '', 
            name:'nmSuplem',
            width: 300
    });    
    var cdAgrupa = new Ext.form.Hidden({
            fieldLabel: '', 
            name:'cdAgrupa',
            width: 300
    });   
    var cdBanco = new Ext.form.TextField({
            fieldLabel: '',  
            name:'cdBanco',
            width: 300,
            hiddeParent:true,
            labelSeparator:'',
            hidden:true
    });    
    var cdForpag = new Ext.form.TextField({
            fieldLabel: '',   
            name:'cdForpag',
            width: 300,
            hiddeParent:true,
            labelSeparator:'',
            hidden:true
    });     
    var numeroTarjeta = new Ext.form.NumberField({
        id:'numero-tarjeta',
        name:'numeroTarjeta',
        fieldLabel:'Numero de Tarjeta',
        allowDecimals :false,
        allowNegative :false,
        allowBlank:false
    });
    var vencimiento = new Ext.form.DateField({
        id:'fecha-vencimiento',
        name:'fechaVencimiento',
        fieldLabel:'Fecha de vencimiento',
        format:'d/m/Y',
        allowBlank:false
    });
    var digitoVerificador = new Ext.form.NumberField({
        id:'digito-verificador',
        fieldLabel: 'Dígito Verificador',
        name:'digitoVerificador',
        allowDecimals :false,
        allowNegative :false,
        width:40,
        maxLength:3,
        allowBlank:false
    });
    
    var datosAgrupadorForm = new Ext.form.FormPanel({
        id:'datosAgrupadorForm',
        url:'flujoendoso/editarAgrupador.action',
        border: false,
        buttonAlign: 'center',
        labelAlign: 'right',   
        width: 670,
        autoHeight: true,
        items: [{
		        labelWidth: 150,   
		        labelAlign: 'right',          
		        layout: 'form',
		        collapsible: true,
		        frame: true,
        		items: [
                cdUnieco,cdRamo,
                estado,nmPoliza,
                nmSuplem,cdAgrupa,
                comboPersonaUsuario,
                codigoPersonaUsuario,
                comboDomicilio,
                codigoDomicilio,
                comboInstrumentoDePago,
                codigoInstrumentoPago,
                {
                layout:'form',
                id:'id-form-pago-hide',
                hidden:true,
                border:false,
                items:[
                       comboBanco,
                       codigoBanco,
                       comboSucursal,
                       codigoSucursal,
                       comboTipoTarjeta,
                       codigoTipoTarjeta,
                       numeroTarjeta,
                       vencimiento,
                       digitoVerificador
                       ]
                }
        	]  
         }]
    });

var windowAgrupadores = new Ext.Window({
        title: 'Editar Datos de Cobro',
        width: 500,
        autoHeight: true,
        layout: 'fit',
        plain:true,
        modal:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: datosAgrupadorForm,

        buttons: [{
            text: 'Actualizar', 
            handler: function() {
                if (datosAgrupadorForm.form.isValid()) {                    
                    datosAgrupadorForm.form.submit({
                    	waitTitle:'Espere',
                        waitMsg:'Procesando...',
                        failure: function(form, action) {
                            Ext.MessageBox.alert('Estado','Error editando datos de cobro');
                        },
                        success: function(form, action) {
                            Ext.MessageBox.alert('Datos de Cobro editado', 'Se editaron datos de cobro');
                            windowAgrupadores.hide();
                            storeGridAgrupadores.load();
                        }
                    });                   
                } else{
                    Ext.MessageBox.alert('Informacion incompleta', 'Por favor verifique');
                }             
            }
        },{
            text: 'Regresar',
            handler: function(){windowAgrupadores.hide();}
        }]
    });
//GRID AGRUPADORES 
var storeGridAgrupadores = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
        method: 'POST', 
        url: '../procesoemision/consultaAgrupador.action'+'?cdUnieco='+idAseguradora.getValue()+'&cdRamo='+idProducto.getValue()+'&estado='+idEstado.getValue()+'&nmPoliza='+idPoliza.getValue()+'&nmSituac=1'
    }),
    reader: new Ext.data.JsonReader({
    root: 'listMpoliagr'
    },[
       {name: 'dsNombre', type: 'string', mapping: 'dsNombre'},
       {name: 'dsBanco', type: 'string', mapping: 'dsBanco'},
       {name: 'cdBanco', type: 'string', mapping: 'cdBanco'},
       {name: 'dsForpag', type: 'string', mapping: 'dsForpag'},
       {name: 'cdForpag', type: 'string', mapping: 'cdForpag'},
       {name: 'dsDomicilio', type: 'string', mapping: 'dsDomicilio'},
       {name: 'cdUnieco', type: 'string', mapping: 'cdUnieco'},
       {name: 'cdRamo', type: 'string', mapping: 'cdRamo'},
       {name: 'estado', type: 'string', mapping: 'estado'},
       {name: 'nmPoliza', type: 'string', mapping: 'nmPoliza'},
       {name: 'nmSuplem', type: 'string', mapping: 'nmSuplem'},
       {name: 'cdAgrupa', type: 'string', mapping: 'cdAgrupa'},
       {name: 'codigoPersonaUsuario', type: 'string', mapping: 'cdPerson'},
       {name: 'numeroTarjeta', type: 'string', mapping: 'nmcuenta'},
       {name: 'fechaVencimiento', type: 'string', mapping: 'fechaUltreg'},
       //{name: 'digitoVerificador', type: 'string', mapping:'digitoVerificador'},
       {name: 'descripcionPersonaUsuario', type: 'string', mapping: 'dsNombre'},
       {name: 'descripcionDomicilio', type: 'string', mapping: 'dsDomicilio'},
       {name: 'muestraCampos', type: 'string', mapping: 'muestraCampos'},
       {name: 'descripcionBanco', type: 'string', mapping: 'cdBanco'},
       {name: 'descripcionTipoTarjeta', type: 'string', mapping: 'dsTipotarj'},
       {name: 'descripcionSucursal', type: 'string', mapping: 'dsSucursal'},
       {name: 'digitoVerificador', type: 'string', mapping: 'nmDigver'},
       {name: 'codigoTipoTarjeta', type: 'string', mapping: 'cdTipoTarjeta'},
       {name: 'codigoSucursal', type: 'string', mapping: 'cdSucursal'},
       {name: 'codigoBanco', type: 'string', mapping: 'cdBanco'},
       {name: 'codigoInstrumentoPago', type: 'string', mapping: 'cdForpag'},
       {name: 'codigoDomicilio', type: 'string', mapping: 'nmorddom'}
     ]),
    remoteSort: true
});

storeGridAgrupadores.load();

var cm1 = new Ext.grid.ColumnModel([
    new Ext.grid.RowNumberer(),
        {header: "Nombre",    dataIndex:'dsNombre',     width:150,  sortable:true},
        {header: "Banco",     dataIndex:'dsBanco',      width:200,  sortable:true},
        {header: "Instrumento de Pago",    dataIndex:'dsForpag',    width:250,  sortable:true},
        {header: "Domicilio",  dataIndex:'dsDomicilio', width:400,  sortable:true}
]);

var recGridAgrupadores;
var GaMuestraCampos;

var gridAgrupadores = new Ext.grid.GridPanel({
        store: storeGridAgrupadores,
        border: true,
        buttonAlign: 'center',
        baseCls:' background:white ',
        cm: cm1,
        buttons:[{
                    id:'editar',
                    text:'Editar',
                    tooltip:'Editar'                           
                }],                                                     
        width:656,
        frame:true,
        height:200,      
        
    sm: new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {                            
                    rowselect: function(sm, row, rec){ 
                            selectedId = storeGridAgrupadores.data.items[row].id;
                            recGridAgrupadores = rec;
                            GaMuestraCampos = rec.get('muestraCampos');
                    }
                }//listeners
        
        }),
        bbar: new Ext.PagingToolbar({
            pageSize:20,
            store: storeGridAgrupadores,                                               
            displayInfo: true,
            displayMsg: 'Registros mostrados {0} - {1} de {2}',
            emptyMsg: 'No hay registros para mostrar',
    		beforePageText: 'P&aacute;gina',
    		afterPageText: 'de {0}'                     
            })                                                                                               
        });
        
        Ext.getCmp('editar').on('click',function(){
                if(GaMuestraCampos == 'S'){
                    Ext.getCmp('id-form-pago-hide').show();
                }else{
                    Ext.getCmp('id-form-pago-hide').hide();
                }
                windowAgrupadores.show();
                Ext.getCmp('datosAgrupadorForm').getForm().loadRecord(recGridAgrupadores);                                                                            
        }); 

var codeExt='';
var comboRolDato;
var comboRolValor;
var comboPersonaDato;
var comboPersonaValor;
//DETALLE DE FUNCION EN LA POLIZA AGREGAR
function datosFnPolizaAgregar(){
    var storeRol = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
            url: 'flujoendoso/getComboRol.action?cdTipsit='+_cdtipsit+'&cdNivel=1'
        }),
        reader: new Ext.data.JsonReader({
            root: 'datosRol',
            id: 'storeComboRol'
        },[
                {name: 'cdRol', type: 'string', mapping:'cdRol'},
                {name: 'dsRol', type: 'string', mapping:'dsRol'}
        ]),
        remoteSort: true
    });
    storeRol.load();
    
    var storePersona = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
            url: 'flujoendoso/getComboPersona.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'datosPersona',
            id: 'storeComboPersona'
        },[
                {name: 'label', type: 'string', mapping:'label'},
                {name: 'value', type: 'string', mapping:'value'}
        ]),
        remoteSort: true
    });
    storePersona.load();

    var accion = new Ext.form.Hidden({
        name:'parameters.accion',
        value:'I'
    });
    var idrol = new Ext.form.Hidden({
        id:'idrol',
        name:'parameters.cdrol',
        value:''
    });
    var idperson = new Ext.form.Hidden({
        id:'idperson',
        name:'parameters.cdperson',
        value:''
    });
    var comboRol =new Ext.form.ComboBox({
            id:'combo-rol',
            tpl: '<tpl for="."><div ext:qtip="{dsRol}.{cdRol}" class="x-combo-list-item">{dsRol}</div></tpl>',
            store: storeRol,
            displayField:'dsRol',
            valueField:'cdRol', 
            editable: true, 
            typeAhead: true,
            listWidth:'162',        
            mode: 'local',
            triggerAction: 'all',
            emptyText:'Seleccione...',
            selectOnFocus:true,
            fieldLabel: 'Rol',
            name:'parameters.comboRol',
            hiddenName:'parameters.comboRol',
            onSelect : function(record, index, skipCollapse){
                if(this.fireEvent('beforeselect', this, record, index) !== false){
                    this.setValue(record.data[this.valueField || this.displayField]);
                    if( !skipCollapse ) {
                        this.collapse();
                    }
                    this.lastSelectedIndex = index + 1;
                    this.fireEvent('select', this, record, index);
                }
                comboRolDato = record.get('dsRol');
                comboRolValor = record.get('cdRol');

                var url = 'flujoendoso/getPantallaRolPoliza.action?cdRol='+comboRolValor+'&cdTipsit='+_cdtipsit+'&accion=I'+'&cdNivel=1'
                var conn = new Ext.data.Connection();
                conn.request({
                     url: url,
                     method: 'POST',
                     successProperty : '@success',
                     callback: function (options, success, response){
                                 if(Ext.util.JSON.decode(response.responseText).success == false){
                                     Ext.Msg.alert('Error', 'Ocurrio un error en la transaccion');
                                 }else{
                                     var codeExt = '';
                                     codeExt = Ext.util.JSON.decode(response.responseText).dext;
                                     if(codeExt == '[]' || codeExt == ''){
                                        var sinDatos = new Ext.form.Hidden({
                                            name:'noData',
                                            value: ''
                                        });
                                        codeExt = sinDatos;
                                        itemsVariables = codeExt;
                                     }else{
                                            var codeExtJson = Ext.util.JSON.encode(codeExt);
                                            var newStartStore = "\"store\":";
                                            var newEndStore = ",\"triggerAction\"";
                                            var onSelectVar = "";
                                            
                                            codeExtJson = codeExtJson.replace(/\"store\":\"/gi, newStartStore);
                                            codeExtJson = codeExtJson.replace(/\",\"triggerAction\"/gi, newEndStore);
                                            codeExtJson = codeExtJson.replace(/,\"onSelect\":null/gi, onSelectVar);
                                            itemsVariables=Ext.util.JSON.decode(codeExtJson);
                                     }
                                        window.close();
                                        datosFnPolizaAgregar();
                                        if(comboRolDato!='undefined'){
                                            Ext.getCmp('combo-rol').setValue(comboRolDato);
                                            Ext.getCmp('idrol').setValue(comboRolValor);
                                        }
                                        if(comboPersonaDato!='undefined'){
                                            Ext.getCmp('combo-persona').setValue(comboPersonaDato);
                                            Ext.getCmp('idperson').setValue(comboPersonaValor);
                                        }
                                 }//else
                    }//function
                });
            }
    });

    var comboPersona =new Ext.form.ComboBox({
            id:'combo-persona',
            tpl: '<tpl for="."><div ext:qtip="{label}.{value}" class="x-combo-list-item">{label}</div></tpl>',
            store: storePersona,
            displayField:'label',
            valueField:'value', 
            editable: true, 
            typeAhead: true,
            listWidth:'162',        
            mode: 'local',
            triggerAction: 'all',
            emptyText:'Seleccione...',
            selectOnFocus:true,
            fieldLabel: 'Persona',
            name:'parameters.comboPersona',
            hiddenName:'parameters.comboPersona',
            onSelect : function(record, index, skipCollapse){
                if(this.fireEvent('beforeselect', this, record, index) !== false){
                    this.setValue(record.data[this.valueField || this.displayField]);
                    if( !skipCollapse ) {
                        this.collapse();
                    }
                    this.lastSelectedIndex = index + 1;
                    this.fireEvent('select', this, record, index);
                }
                comboPersonaDato    = record.get('label');
                comboPersonaValor   = record.get('value');
                Ext.getCmp('idperson').setValue(comboPersonaValor);
            }
    });
    var panelRol = new Ext.form.FormPanel({
            url:'flujoendoso/getPolizaRol.action',
            method:'post',
            border: false,
            width: 540,
            autoScroll:true,
            height:340,
            items:[{
                labelWidth: 60, 
                labelAlign: 'right',         
                layout: 'form',
                frame: true,
                items:[{
                        layout:'column',
                        border:false,
                        items:[{
                            layout: 'form',                     
                            border: false,
                            items:[accion,idrol,idperson,comboRol]
                            },{
                            layout: 'form',                     
                            border: false,
                            items:[comboPersona]
                       }]
                },{
                labelWidth: 150, 
                labelAlign: 'right',         
                layout: 'form',
                frame: true,
                autoScroll:true,
                items:itemsVariables
            }]
        }]
     });
     
     var window = new Ext.Window({
        title: 'Agregar rol en Riesgo',
        width: 550,
        height:350,
        minWidth: 300,
        minHeight: 200,
        layout: 'fit',
        plain:true,
        modal:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: [panelRol],
        buttons:[{
                text: 'Guardar', 
                handler: function() {                
                    if (panelRol.form.isValid()) {
                        panelRol.form.submit({ 
                        	waitTitle:'Espere',
                            waitMsg:'Procesando...',
                            failure: function(form, action) {
                                Ext.MessageBox.alert('Error', Ext.util.JSON.decode(action.response.responseText).respuesta);
                                window.close();
                            },
                            success: function(form, action) {
                                    var mensajeRes = Ext.util.JSON.decode(action.response.responseText).numRespuesta;
                                    if (mensajeRes=="2"){
                                        Ext.MessageBox.alert('Opcion Creada', 'Se agrego la opcion');
                                        storeGridFnPoliza.load();
                                    }else{
                                        Ext.MessageBox.alert('Error', 'No se pudo agregar la opcion');
                                    }
                                    itemsVariables = '';
                                    window.close();
                            }
                        });                   
                    }else{
                        Ext.MessageBox.alert('Error', 'Por favor revise los errores!');
                    }
                }
        },{
                text: 'Regresar',
                tooltip: 'Cierra la ventana',
                handler: function(){
                    itemsVariables = '';
                    window.close();
                }//handler
        }]
     });

    window.show();
}
//DETALLE DE ROL DE LA POLIZA EDITAR
function datosFnPolizaEditar(cdRol, dsRol, cdPerson, dsPerson, idProducto, cdTipsit){
    var storeRol = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
            url: 'flujoendoso/getComboRol.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'datosRol',
            id: 'storeComboRol'
        },[
                {name: 'cdRol', type: 'string', mapping:'cdRol'},
                {name: 'dsRol', type: 'string', mapping:'dsRol'}
        ]),
        remoteSort: true
    });
    storeRol.load();

    var storePersona = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
            url: 'flujoendoso/getComboPersona.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'datosPersona',
            id: 'storeComboPersona'
        },[
                {name: 'label', type: 'string', mapping:'label'},
                {name: 'value', type: 'string', mapping:'value'}
        ]),
        remoteSort: true
    });
    storePersona.load();
                            
    var url = 'flujoendoso/getPantallaRolPoliza.action?cdUnieco='+idAseguradora.getValue()+'&cdRol='+cdRol+'&cdPerson='+cdPerson+'&accion=U'
    var conn = new Ext.data.Connection();
    conn.request({
         url: url,
         method: 'POST',
         successProperty : '@success',
         callback: function (options, success, response){
                     if(Ext.util.JSON.decode(response.responseText).dext == null){
                         Ext.Msg.alert('Error', 'Ocurrio un error en la transaccion');
                     }else{
                         codeExt = Ext.util.JSON.decode(response.responseText).dext;
                         var codeExtJson = Ext.util.JSON.encode(codeExt);
                         
                         if(codeExtJson == '[]' || codeExtJson == ''){
                            var sinDatos = new Ext.form.Hidden({
                                name:'sinDatos',
                                value:''
                            });
                            codeExtJson = sinDatos;
                            itemsVariables=codeExtJson;
                         }else{
                            var newStartStore = "\"store\":";
                            var newEndStore = ",\"triggerAction\"";
                            var onSelectVar = "";
                            
                            codeExtJson = codeExtJson.replace(/\"store\":\"/gi, newStartStore);
                            codeExtJson = codeExtJson.replace(/\",\"triggerAction\"/gi, newEndStore);
                            codeExtJson = codeExtJson.replace(/,\"onSelect\":null/gi, onSelectVar);
                            itemsVariables=Ext.util.JSON.decode(codeExtJson);
                         }
                            var accion = new Ext.form.Hidden({
                                name:'parameters.accion',
                                value:'U'
                            });
                            var idrol = new Ext.form.Hidden({
                                id:'idrol',
                                name:'parameters.cdrol',
                                value:''
                            });
                            var idperson = new Ext.form.Hidden({
                                id:'idperson',
                                name:'parameters.cdperson',
                                value:''
                            });

                            var comboRol =new Ext.form.ComboBox({
                                    id:'combo-rol',
                                    tpl: '<tpl for="."><div ext:qtip="{dsRol}.{cdRol}" class="x-combo-list-item">{dsRol}</div></tpl>',
                                    store: storeRol,
                                    displayField:'dsRol',
                                    valueField:'cdRol', 
                                    editable: true, 
                                    typeAhead: true,
                                    listWidth:'162',        
                                    mode: 'local',
                                    triggerAction: 'all',
                                    emptyText:'Seleccione...',
                                    selectOnFocus:true,
                                    fieldLabel: 'Rol',
                                    name:'parameters.comboRol',
                                    hiddenName:'parameters.comboRol',
                                    disabled:true
                            });

                            var comboPersona =new Ext.form.ComboBox({
                                id:'combo-persona',
                                tpl: '<tpl for="."><div ext:qtip="{label}.{value}" class="x-combo-list-item">{label}</div></tpl>',
                                store: storePersona,
                                displayField:'label',
                                valueField:'value', 
                                editable: true, 
                                typeAhead: true,
                                listWidth:'162',        
                                mode: 'local',
                                triggerAction: 'all',
                                emptyText:'Seleccione...',
                                selectOnFocus:true,
                                fieldLabel: 'Persona',
                                name:'parameters.comboPersona',
                                hiddenName:'parameters.comboPersona',
                                onSelect : function(record, index, skipCollapse){
                                    if(this.fireEvent('beforeselect', this, record, index) !== false){
                                        this.setValue(record.data[this.valueField || this.displayField]);
                                        if( !skipCollapse ) {
                                            this.collapse();
                                        }
                                        this.lastSelectedIndex = index + 1;
                                        this.fireEvent('select', this, record, index);
                                    }
                                    comboPersonaDato    = record.get('label');
                                    comboPersonaValor   = record.get('value');
                                    Ext.getCmp('idperson').setValue(comboPersonaValor);
                                }
                            });

                            var panelRol = new Ext.form.FormPanel({
                                url:'flujoendoso/getPolizaRol.action?cdPerson='+cdPerson,
                                method:'post',
                                border: false,
                                width: 540,
                                autoScroll:true,
                                height:340,
                                items:[{
                                    labelWidth: 60, 
                                    labelAlign: 'right',         
                                    layout: 'form',
                                    frame: true,
                                    items:[{
                                            layout:'column',
                                            border:false,
                                            items:[{
                                                layout: 'form',                     
                                                border: false,
                                                items:[
                                                    accion,
                                                    idrol,
                                                    idperson,
                                                    comboRol
                                                ]
                                                },{
                                                layout: 'form',                     
                                                border: false,
                                                items:[
                                                    comboPersona
                                                ]
                                           }]
                                    },{
                                    labelWidth: 150, 
                                    labelAlign: 'right',         
                                    layout: 'form',
                                    frame: true,
                                    autoScroll: true,
                                    items: itemsVariables
                                }]
                            }]
                            });
     
                             var window = new Ext.Window({
                                title: 'Editar rol en póliza',
                                width: 550,
                                height:350,
                                minWidth: 300,
                                minHeight: 200,
                                layout: 'fit',
                                plain:true,
                                modal:true,
                                bodyStyle:'padding:5px;',
                                buttonAlign:'center',
                                items: [panelRol],
                                buttons:[{
                                        text: 'Guardar', 
                                        handler: function() {                
                                            if (panelRol.form.isValid()) {
                                                panelRol.form.submit({    
                                                	waitTitle:'Espere',
                                                    waitMsg:'Procesando...',
                                                    failure: function(form, action) {
                                                        var mensajeRes = Ext.util.JSON.decode(action.response.responseText).numRespuesta;
                                                        if (mensajeRes=="2"){
                                                            Ext.MessageBox.alert('Opcion Editada', 'Se edit&oacute; la opci&oacute;n');
                                                            storeGridFnPoliza.load();
                                                        }else{
                                                            Ext.MessageBox.alert('Error', 'No se pudo editar la opci&oacute;n');
                                                        }
                                                        window.close();
                                                    },
                                                    success: function(form, action) {
                                                        var mensajeRes = Ext.util.JSON.decode(action.response.responseText).numRespuesta;
                                                        if (mensajeRes=="2"){
                                                            Ext.MessageBox.alert('Opcion Editada', 'Se edit&oacute; la opci&oacute;n');
                                                            storeGridFnPoliza.load();
                                                        }else{
                                                            Ext.MessageBox.alert('Error', 'No se pudo editar la opci&oacute;n');
                                                        }
                                                        window.close();
                                                    }
                                                });                   
                                            }else{
                                                Ext.MessageBox.alert('Error', 'Por favor revise los errores!');
                                            }             
                                        }
                                },{
                                        text: 'Regresar',
                                        tooltip: 'Cierra la ventana',
                                        handler: function(){
                                            window.close();
                                        }//handler
                                }]
                             });
                            window.show();
                            codeExt = '';
                            codeExtJson = '';
                            itemsVariables = '';
                            comboRol.setValue(dsRol);
                            comboPersona.setValue(dsPerson);
                            idrol.setValue(cdRol);
                            idperson.setValue(cdPerson);
    
                     }//else
        }//function
    });
}
//DETALLE DE ROL DE LA POLIZA BORRAR
function datosFnPolizaBorrar(cdRol, cdPerson, cdTipsit, swObliga, totalPerRol){
    var accion = new Ext.form.Hidden({
        name:'parameters.accion',
        value:'D'
    });
    var idrol = new Ext.form.Hidden({
        id:'idrol',
        name:'parameters.cdrol',
        value:cdRol
    });
    var idperson = new Ext.form.Hidden({
        id:'idperson',
        name:'parameters.cdperson',
        value:cdPerson
    });    
    var swobliga = new Ext.form.Hidden({
        id:'swobliga',
        name:'parameters.swobliga',
        value:swObliga
    });
    var totalPerRol = new Ext.form.Hidden({
        id:'totalPerRol',
        name:'parameters.totalPerRol',
        value:totalPerRol
    });
    var msgBorrar = new Ext.form.TextField({
        fieldLabel:'¿Esta seguro que desea eliminar la opción?',
        labelSeparator:'',
        anchor:'90%',
        hidden:true
    });
    var Url = 'flujoendoso/getPolizaRol.action?cdTipsit='+cdTipsit; 
    var panelRol = new Ext.form.FormPanel({
        url: Url,
        method:'post',
        border: false,
        width: 400,
        height:160,
        items:[{
            labelWidth: 300, 
            labelAlign: 'right',         
            layout: 'form',
            frame: true,
            items:[{
                    layout:'column',
                    border:false,
                    items:[{
                        layout:'form',                     
                        border:false,
                        items:[
                        	msgBorrar,
                        	swobliga,
                            accion,
                            idrol,
                            idperson,
                            totalPerRol
                        ]
                        }]
            }]
    }]
    });
    var window = new Ext.Window({
        title: 'Eliminar rol en p&oacute;liza',
        width: 420,
        height:130,
        layout: 'fit',
        plain:true,
        modal:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: [panelRol],
        buttons:[{
                text: 'Eliminar', 
                handler: function() {                
                    if (panelRol.form.isValid()) {
                        panelRol.form.submit({   
                        	waitTitle:'Espere',
                            waitMsg:'Procesando...',
                            failure: function(form, action) {
                                Ext.MessageBox.alert('Error', Ext.util.JSON.decode(action.response.responseText).respuesta);
                                window.close();
                            },
                            success: function(form, action) {
                                var mensajeRes = Ext.util.JSON.decode(action.response.responseText).numRespuesta;
                                if (mensajeRes=="2"){
                                    Ext.MessageBox.alert('Opcion Eliminada', 'Se elimin&oacute; la opci&oacute;n');
                                    storeGridFnPoliza.load();
                                }else{
                                    Ext.MessageBox.alert('Error', 'No se pudo eliminar la opci&oacute;n');
                                }
                                window.close();
                            }
                        });                   
                    }else{
                        Ext.MessageBox.alert('Error', 'Por favor revise los errores!');
                    }             
                }
        },{
                text: 'Regresar',
                tooltip: 'Cierra la ventana',
                handler: function(){
                    window.close();
                }//handler
        }]
     });
    window.show();
}
//GRID ROL DE LA POLIZA 
var storeGridFnPoliza = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
        method: 'POST', 
        url: 'flujoendoso/consultaFuncionPoliza.action'
    }),
    reader: new Ext.data.JsonReader({
    root: 'fnPoliza'
    },[
       {name: 'rol', 		type: 'string', mapping:'rol'},
       {name: 'nombre', 	type: 'string', mapping:'nombre'},
       {name: 'cdRol', 		type: 'string', mapping:'cdRol'},
       {name: 'cdPerson', 	type: 'string', mapping:'cdPerson'},
       {name: 'nmSituac', 	type: 'string', mapping:'nmSituac'},
       {name: 'nmaximo',    type: 'string', mapping:'nmaximo'},
       {name: 'swDomici',   type: 'string', mapping:'swDomici'},
       {name: 'swObliga',   type: 'string', mapping:'swObliga'},
       {name: 'totalPerRol',type: 'string', mapping:'totalPerRol'}
     ]),
    remoteSort: true
});

storeGridFnPoliza.load();

var cm2 = new Ext.grid.ColumnModel([
    new Ext.grid.RowNumberer(),
        {header: "Rol",    	   dataIndex:'rol',     	width:120,  sortable:true},
        {header: "Nombre",     dataIndex:'nombre',  	width:120,  sortable:true},
        {header: "cdRol",      dataIndex:'cdRol',   	hidden: true},
        {header: "cdPerson",   dataIndex:'cdPerson',	hidden: true},
        {header: "nmSituac",   dataIndex:'nmSituac',	hidden: true},
        {header: "nmaximo",    dataIndex:'nmaximo',     width:150,  sortable:true, hidden: true},
        {header: "swDomici",   dataIndex:'swDomici',    width:150,  sortable:true, hidden: true},
        {header: "swObliga",   dataIndex:'swObliga',    width:150,  sortable:true, hidden: true},
        {header: "totalPerRol",dataIndex:'totalPerRol', width:150,  sortable:true, hidden: true}
]);

var cdRol = "";
var dsRol = "";
var cdPerson = "";
var dsPerson = ""; 
var nmaximo = ""; 
var swDomici = "";
var swObliga = "";
var totalPerRol = "";

var gridFnPoliza = new Ext.grid.GridPanel({
        store: storeGridFnPoliza,
        border: true,
        buttonAlign: 'center',
        baseCls:' background:white ',
        cm: cm2,
        buttons:[{
                    id:'fnPolizaAgregar',
                    text:'Agregar',
                    tooltip:'Agregar'
                },{
                    id:'fnPolizaEditar',
                    text:'Editar',
                    tooltip:'Editar'
                },{
                    id:'fnPolizaBorrar',
                    text:'Eliminar',
                    tooltip:'Eliminar'
                }],
        width:656,
        frame:true,
        height:200,
    sm: new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {
                    rowselect: function(sm, row, rec){
                            cdRol = rec.get('cdRol');
                            dsRol = rec.get('rol');
                            cdPerson = rec.get('cdPerson');
                            dsPerson = rec.get('nombre');
                            nmaximo = rec.get('nmaximo');
	                        swDomici = rec.get('swDomici');
	                        swObliga = rec.get('swObliga');
	                        totalPerRol = rec.get('totalPerRol');
                    }
                }//listeners
        }),
        viewConfig: {autoFill: true,forceFit:true},                
        bbar: new Ext.PagingToolbar({
            pageSize:20,
            store: storeGridFnPoliza,                                               
            displayInfo: true,
            displayMsg: 'Registros mostrados {0} - {1} de {2}',
            emptyMsg: 'No hay registros para mostrar',
    		beforePageText: 'P&aacute;gina',
    		afterPageText: 'de {0}'                    
            })                                                                                               
        });
        Ext.getCmp('fnPolizaEditar').on('click',function(){
            datosFnPolizaEditar(cdRol, dsRol, cdPerson, dsPerson, idProducto.getValue(), _cdtipsit);
        });
        Ext.getCmp('fnPolizaAgregar').on('click',function(){
            datosFnPolizaAgregar();                                                                            
        });
        Ext.getCmp('fnPolizaBorrar').on('click',function(){
            datosFnPolizaBorrar(cdRol, cdPerson, _cdtipsit, swObliga, totalPerRol);
        });

//******************DETALLE ACCESORIOS******************
var itemsVariablesEditar;
var itemsVariablesAgregar;

function detalleAccesorios(nmObjeto, cdTipoObjeto, dsObjeto, ptObjeto){
    var conn = new Ext.data.Connection();
    conn.request({
         url: 'flujoendoso/datosAccesorios.action?nmObjeto='+nmObjeto+'&cdTipoObjeto='+cdTipoObjeto+'&dsObjeto'+dsObjeto+'&ptObjeto'+ptObjeto,
         method: 'POST',
         successProperty : '@success',
         callback: function (options, success, response){
                     if (Ext.util.JSON.decode(response.responseText).success == false) {
                         Ext.Msg.alert('Error', 'Ocurrio un error en la transaccion');
                     }else{
                         var codeExt = Ext.util.JSON.decode(response.responseText).dextAccesorios;
                         var codeExtJson = Ext.util.JSON.encode(codeExt);
                            if(codeExt != ''){
                                var newStartStore = "\"store\":";
                                var newEndStore = ",\"triggerAction\"";
                                var onSelectVar = "";
                                
                                codeExtJson = codeExtJson.replace(/\"store\":\"/gi, newStartStore);
                                codeExtJson = codeExtJson.replace(/\",\"triggerAction\"/gi, newEndStore);
                                codeExtJson = codeExtJson.replace(/,\"onSelect\":null/gi, onSelectVar);
                            }
                         itemsVariablesEditar=Ext.util.JSON.decode(codeExtJson);  
                         if(itemsVariablesEditar != ''){
                            var nmObj = new Ext.form.TextField({
                                name:'nmObjeto',
                                value:nmObjeto,
                                anchor:'90%',
                                hiddeParent:true,
                                hidden:true,
                                labelSeparator:''                                
                            });  
                            var campoTipo = new Ext.form.TextField({
                                name:'tipo',
                                value:dsObjeto,
                                fieldLabel:'Tipo',
                                disabled:true,
                                width: 200
                            });
                            var hcampoTipo = new Ext.form.TextField({
                                fieldLabel: '',
                                name:'cdTipoObjeto',
                                value:cdTipoObjeto,
                                labelSeparator:'',
                                hidden:true
                            });
                            var hdsObjeto = new Ext.form.TextField({
                                fieldLabel: '',
                                name:'dsObjeto',
                                value:dsObjeto,
                                labelSeparator:'',
                                hidden:true
                            });
                            var campoMonto = new Ext.form.NumberField({
                                name:'ptObjeto',
                                value:ptObjeto,
                                fieldLabel:'Monto',
                                allowBlank:false,
                                width: 200
                            });
                            var panelDetalleAccesoriosEnc = new Ext.FormPanel({
                                    labelAlign:'right',
                                    border: false,
                                    width: 350,
                                    autoHeight:true,
                                    items:[
                                            campoTipo,
                                            campoMonto
                                    ]
                            });
                            var panelDetalleAccesorios = new Ext.FormPanel({
                                    labelAlign:'right',
                                    border: false,
                                    width: 350,
                                    autoHeight:true,
                                    items: codeExt
                            });
                            
                            var editarForm = new Ext.form.FormPanel({
                                    id:'editar-forma-window',
                                    url:_ACTION_EDITAR_ACCESORIO,
                                    boder:false,
                                    frame:true,
                                    autoScroll:true,
                                    method:'post',                          
                                    width: 570,
                                    buttonAlign: "center",
                                    baseCls:'x-plain',
                                    labelWidth:75,   
                                    items:[
                                        campoTipo,                                          
                                                {  
                                                layout:'form',                                  
                                                border:false,
                                                frame:true,
                                                method:'post',  
                                                baseCls:'x-plain',
                                                autoScroll:true,                            
                                                items: itemsVariablesEditar                                               
                                                },{  
                                                layout:'form',
                                                id:'forma-monto',                                    
                                                border:false,
                                                frame:true,
                                                method:'post',  
                                                baseCls:'x-plain',
                                                autoScroll:true,                            
                                                items:[campoMonto]                                                
                                                },
                                                nmObj, hcampoTipo, hdsObjeto
                                            ]
                                    });
                             
                            var windowAccesorios = new Ext.Window({
                                title: 'Detalle Accesorios',
                                width: 510,
                                height:350,
                                autoScroll:true,
                                maximizable:true,
                                minWidth: 500,
                                minHeight: 200,
                                layout: 'fit',
                                modal:true,
                                plain:true,
                                bodyStyle:'padding:5px;',
                                buttonAlign:'center',
                                items:editarForm,
                                buttons:[{
                                        text: 'Guardar', 
                                        tooltip: 'Guardar cambios',
                                        handler: function(){
                                            if (editarForm.form.isValid()) {
                                                editarForm.form.submit({
                                                	waitTitle:'Espere',
                                                    waitMsg:'Procesando...',
                                                    failure: function(form, action) {
                                                        Ext.MessageBox.alert('Error', 'No se pudo editar el Accesorio');
                                                        windowAccesorios.close();
                                                    },
                                                    success: function(form, action) {
                                                        Ext.MessageBox.alert('Accesorio editado', 'Se editó el Accesorio');
                                                        itemsVariablesEditar='';
                                                        windowAccesorios.close();
                                                        storeA.load(); 
                                                    }
                                                });
                                            }else{
                                                Ext.MessageBox.alert('Error', 'Por favor revise los errores!');
                                            }
                                                 }
                                        },{
                                        text: 'Regresar',
                                        tooltip: 'Cierra la ventana',
                                            handler: function(){
                                                windowAccesorios.close();
                                            }//handler
                                    }]
                                });
                            windowAccesorios.show();
                         }else{
                            Ext.Msg.alert('Mensaje', 'No se encontraron datos');
                         }
                      }//else
                   }//function
    });
}
//******************AGREGAR ACCESORIO******************
agregarAccesorio = function(storeA){
    var dataStoreTipos = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url:_ACTION_CARGA_TIPOS_ACCESORIOS
            }),
            reader: new Ext.data.JsonReader({
                root: 'listaTipo'
            },[
                {name: 'value',  type: 'string',  mapping:'value'},
                {name: 'label',  type: 'string',  mapping:'label'}    
            ]),
            remoteSort: true
        });
    dataStoreTipos.setDefaultSort('label', 'desc');
    dataStoreTipos.load();

    var montoCotizar = new Ext.form.NumberField({
                fieldLabel: 'Monto',
                labelSeparator:'',
                width:300,                    
                blankText : 'Monto a Cotizar ',
                allowBlank:false,
                name:'montoCotizar' 
        });     
            
    var comboTipos = new Ext.form.ComboBox({
                id:'combo-tipos-equipo-especial',
                tpl: '<tpl for="."><div ext:qtip="{label}. {value}" class="x-combo-list-item">{label}</div></tpl>',
                store: dataStoreTipos,
                width: 300,
                mode: 'local',
                name: 'descripcionTipo',
                typeAhead: true,
                allowBlank:false,
                labelSeparator:'',
                triggerAction: 'all',           
                displayField:'label',
                valueField:'value',
                forceSelection: true,
                fieldLabel: 'Tipo',
                emptyText:'Seleccione un Tipo...',
                selectOnFocus:true,
                onSelect : function(record, index, skipCollapse){
                    if(this.fireEvent('beforeselect', this, record, index) !== false){
                        this.setValue(record.data[this.valueField || this.displayField]);
                        if( !skipCollapse ) {
                            this.collapse();
                        }
                        this.lastSelectedIndex = index + 1;
                        this.fireEvent('select', this, record, index);
                    }
                    var valor=record.get('value');
                    var label=record.get('label');
                    var params="";                  
                    params  = "claveObjeto="+valor;
                    params += "&& descripcionObjeto="+label;
                    var conn = new Ext.data.Connection();
                    conn.request ({
                        url:_ACTION_CARGA_ITEMS_ACCESORIOS,
                        method: 'POST',
                        successProperty : '@success',
                        params : params,
                        callback: function (options, success, response) {
                                                                            
                            var codeExtResponse = Ext.util.JSON.decode(response.responseText);
                            var codeExtJsonRes=Ext.util.JSON.encode(codeExtResponse);
                            var codeExtResp = codeExtResponse.itemLista;    
                            var codeExtJson=Ext.util.JSON.encode(codeExtResp);
                            
                            if(codeExtResp != ''){
                                var newStartStore = "\"store\":";
                                var newEndStore = ",\"triggerAction\"";
                                var onSelectVar = "";
                                
                                codeExtJson = codeExtJson.replace(/\"store\":\"/gi, newStartStore);
                                codeExtJson = codeExtJson.replace(/\",\"triggerAction\"/gi, newEndStore);
                                codeExtJson = codeExtJson.replace(/,\"onSelect\":null/gi, onSelectVar);
                            }
                            itemsVariablesAgregar = Ext.util.JSON.decode(codeExtJson);                      
                            validacion = codeExtResponse.itemListaAccValido;
                            valorCombo = codeExtResponse.labelCombo;                           
                            if(validacion==true){                               
                                windowEquipo.close();
                                agregarAccesorio(storeA);
                                Ext.getCmp('combo-tipos-equipo-especial').setValue(valorCombo);
                            }else{
                                Ext.MessageBox.alert('Error', 'No existen datos asociados');
                            }                                           
                        }
                    });
                    
        
                }               
        }); 
    var agregarForm = new Ext.form.FormPanel({
                id:'recarga-forma-window',
                url:_ACTION_GUARDA_ACCESORIO,
                boder:false,
                frame:true,
                autoScroll:true,
                method:'post',                          
                width: 570,
                buttonAlign: "center",
                baseCls:'x-plain',
                labelWidth:75,   
                items:[
                    comboTipos,                                         
                            {  
                            layout:'form',                                  
                            border:false,
                            frame:true,
                            method:'post',  
                            baseCls:'x-plain',
                            autoScroll:true,                            
                            items: itemsVariablesAgregar                                               
                            },{  
                            layout:'form',
                            id:'forma-equipo-especial-variable',                                    
                            border:false,
                            frame:true,
                            method:'post',  
                            baseCls:'x-plain',
                            autoScroll:true,                            
                            items:[montoCotizar]                                                
                            }
                        ]
                });
                                
    itemsVariablesAgregar=null;
    
    var windowEquipo = new Ext.Window({
        title: 'Accesorios',
        width: 510,
        height:350,
        autoScroll:true,
        maximizable:true,
        minWidth: 500,
        minHeight: 200,
        layout: 'fit',
        modal:true,
        plain:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: agregarForm,
        buttons: [{
            text: 'Guardar', 
            handler: function() {
                if (agregarForm.form.isValid()) {
                    agregarForm.form.submit({
                        url:'',
                        waitTitle:'Espere',
                        waitMsg:'Procesando...',
                        failure: function(form, action) {
                            Ext.MessageBox.alert('Estado','El Accesorio no se guard&oacute');
                        },
                        success: function(form, action) {
                        	Ext.getCmp('mainPanel').focus();
                            Ext.MessageBox.alert('Estado', 'Accesorio Guardado');
                            itemsVariablesAgregar=null;
                            windowEquipo.close();
                            storeA.load();
                        }
                    });                   
                } else{
                    Ext.MessageBox.alert('¡Error!', 'Porfavor Verifique los Errores!');
                }             
            }
        },{
            text: 'Regresar',
            handler: function(){windowEquipo.close();}
        }]
    });
    windowEquipo.show();
};
    /*****se crea la ventana de borrar un Accesorio***/
    var msgBorrarAccesorios = new Ext.form.TextField({
        fieldLabel:'¿Esta seguro que desea Eliminar el Accesorio?',
        labelSeparator:'',
        anchor:'90%',
        hidden:true
    });                   
   
    var _nmObjeto = new Ext.form.TextField({
        fieldLabel: '',   
        name:'nmObjeto',
        anchor:'90%',
        hiddeParent:true,
        labelSeparator:'',
        hidden:true
    });  
    var _cdTipoObjeto = new Ext.form.TextField({
        fieldLabel: '',   
        name:'cdTipoObjeto',
        anchor:'90%',
        hiddeParent:true,
        labelSeparator:'',
        hidden:true
    });   
    var borrarAccesoriosForm= new Ext.FormPanel({
        id:'borrarAccesoriosForm',
        labelAlign:'top',
        baseCls: 'x-plain',
        anchor:'100%',
        url:'flujoendoso/borrarAccesorios.action',
        items:[ msgBorrarAccesorios, _nmObjeto, _cdTipoObjeto]
    });

    var windowAccesoriosDel = new Ext.Window({
        title: 'Elminar Accesorio',
        minHeight: 50,
        minWidth: 250,
        width: 250,
        height:120,
        layout: 'fit',
        plain:true,
        modal:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: borrarAccesoriosForm,
        buttons: [{
                text: 'Eliminar', 
                handler: function() {
                    if (borrarAccesoriosForm.form.isValid()) {
                            borrarAccesoriosForm.form.submit({   
                            	waitTitle:'Espere',
                                waitMsg:'Procesando...',
                                failure: function(form, action) {
                                    Ext.MessageBox.alert('Error', 'Error Eliminando Accesorio');
                                    windowAccesoriosDel.hide();
                                },
                                success: function(form, action) {
                                    Ext.MessageBox.alert('Exito', 'Accesorio Eliminado');
                                    windowAccesoriosDel.hide();
                                    storeA.load();                                      
                                }
                            });                   
                    }else{
                        Ext.MessageBox.alert('Error', 'Verifique!');
                    }             
                }
            },{
                text: 'Cancelar',
                handler: function(){windowAccesoriosDel.close();}
            }]
        });
//ACCESORIOS
var storeAccesoriosGrid = createGridAccesorios();
var cm3 = new Ext.grid.ColumnModel([
    new Ext.grid.RowNumberer(),
    {header: "nmObjeto",     dataIndex:'nmObjeto',     hidden: true},
    {header: "cdTipoObjeto", dataIndex:'cdTipoObjeto', hidden: true},
    {header: "Tipo",         dataIndex:'dsObjeto',     width: 305, sortable:true},
    {header: "Monto",        dataIndex:'ptObjeto2',    width: 305, sortable:true, renderer:Ext.util.Format.usMoney}
]);
var selectedId;

var nmObjeto = "";
var cdTipoObjeto = "";
var dsObjeto = "";
var ptObjeto = "";
                                    
var gridAccesorios = new Ext.grid.GridPanel({
    store:storeAccesoriosGrid,
    border:true,
    baseCls:' background:white ',
    cm: cm3,
    buttonAlign: 'center',
    
    buttons:[{
                text:'Agregar',
                tooltip:'Agregar',
                handler:function(){
                    agregarAccesorio(storeA);
                }
            },{
                id:'accesoriosDetalles',
                text:'Editar',
                tooltip:'Editar'
            },{
                id:'borrarAccesorios',
                text:'Eliminar',
                tooltip:'Eliminar Accesorio'
            }],                                
    width:656,
    frame:true,
    height:200,
    
    sm: new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {                            
                        rowselect: function(sm, row, rec) { 
                                selectedId = storeAccesoriosGrid.data.items[row].id;                                                                                                                                    
                                
                                nmObjeto = rec.get('nmObjeto');
                                cdTipoObjeto = rec.get('cdTipoObjeto');
                                dsObjeto = rec.get('dsObjeto');
                                ptObjeto = rec.get('ptObjeto');
                                    
                                Ext.getCmp('borrarAccesorios').on('click', function(){
                                    windowAccesoriosDel.show();
                                    Ext.getCmp('borrarAccesoriosForm').getForm().loadRecord(rec);
                                }); 
                        }
                }
        }),
    bbar: new Ext.PagingToolbar({
        pageSize:20,
        store: storeAccesoriosGrid,                                               
        displayInfo: true,
        displayMsg: 'Registros mostrados {0} - {1} de {2}',
        emptyMsg: 'No hay registros para mostrar',
		beforePageText: 'P&aacute;gina',
		afterPageText: 'de {0}'                      
        })
    });

Ext.getCmp('accesoriosDetalles').on('click',function(){
        if(nmObjeto!=""){
            detalleAccesorios(nmObjeto, cdTipoObjeto, dsObjeto, ptObjeto);
        }else{
            Ext.Msg.alert('Accesorios','No ha seleccionado ningun registro');
        }
});
//PANEL GRID COBERTURA
var coberturaPanelGrid = new Ext.Panel({
    border: false,
    width: 668,
    autoHeight: true,
    autoScroll: true,
    items:[{
        title :'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Coberturas</span>',
        layout: 'form',
        collapsible: true,
        frame: true,
        items:[gridCoberturas]
   }]
});
//PANEL GRID ROL DE LA POLIZA
var panelFnPolizaPanelGrid = new Ext.Panel({
    border: false,
    width: 668,
    autoScroll:true,
    items:[{
        title :'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Rol del Riesgo</span>',
        layout: 'form',
        collapsible: true,
        frame: true,
        autoScroll:true,
        items:[gridFnPoliza]
   }]
});
//PANEL GRID AGRUPADORES
var panelAgrupadoresPanelGrid = new Ext.Panel({
    border: false,
    width: 668,
    autoScroll:true,
    items:[{
        title :'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Datos de Cobro</span>',
        layout: 'form',
        collapsible: true,
        frame: true,
        autoScroll:true,
        items:[gridAgrupadores]
   }]
});
//PANEL GRID ACCESORIOS
var accesoriosPanelGrid = new Ext.Panel({
    border: false,
    width: 668,
    autoHeight: true,
    autoScroll: true,
    items:[{
        title :'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Accesorios</span>',
        layout: 'form',
        collapsible: true,
        frame: true,
        autoScroll:true,
        items:[gridAccesorios]
   }]
});
//PANEL GRID MAIN
var mainPanelGrid = new Ext.Panel({
    border: false,
    width: 680,
    autoHeight: true,
    id:'mainPanel',
    buttonAlign:'center',
    autoScroll:true,
    items:[{
        title :'<span style="color:black;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Inciso</span>',
        layout: 'form',
        buttonAlign:'center',
        frame: true,
        autoScroll:true,
        items:[datosIncisosForm,coberturaPanelGrid,panelFnPolizaPanelGrid,panelAgrupadoresPanelGrid,accesoriosPanelGrid],
        buttons:[{
        		id:'regresar',
                text:'Regresar',
                tooltip:'Regresar a detalles poliza'
        },{
       			id:'tarificar',
                text:'Continuar',
                tooltip:'Continua para confirmar el endoso'
        }],
        sm: new Ext.grid.RowSelectionModel({
            singleSelect: true,
            listeners: {       
                rowselect: function(sm, row, rec) {
                }
            }
        })
   }]
});
mainPanelGrid.render('pantallaInciso');

    Ext.getCmp('tarificar').on('click',function(){
    var url = 'flujoendoso/validaPoliza.action';
    
    var proceso="";
    var params="proc=";
    
    if(PROCESO_RENOVACION){
    	proceso=RENOVACION;
    	params+= RENOVACION;
    }else{
    	proceso=ENDOSO;
    	params+= ENDOSO;
    }

    var conn = new Ext.data.Connection();
    conn.request({
        url: url,
        method: 'POST',
        successProperty : '@success',
        params: params,
        callback: function (options, success, response){
            var res = Ext.util.JSON.decode(response.responseText).resultadoValidaPoliza;
            if(res == 'ok'){
                window.location.href = _CONTEXT+"/flujoendoso/poliza.action?nmSituac="+_nmsituac+"&dsDescripcion="+_dsdescripcion+"&cdInciso="+_cdinciso+"&status="+_status+"&aseguradora="+_aseguradora+"&poliza="+_poliza+"&producto="+_producto+"&Origen=inciso"+"&proc="+proceso+"&fechaefectividadendoso="+FECHA_EFECTIVIDAD_ENDOSO;
            }else{
                Ext.Msg.alert('Error', Ext.util.JSON.decode(response.responseText).mensajeError);
            }
        }
    });           
  });
    
    Ext.getCmp('regresar').on('click',function(){
    
    var proceso="";
	if(PROCESO_RENOVACION){
		proceso=RENOVACION;
	}else{
		proceso=ENDOSO;
	}
        window.location.href = _CONTEXT+"/flujoendoso/detallePoliza.action?cdUnieco="+idAseguradora.getValue()+"&cdRamo="+idProducto.getValue()+"&estado="+idEstado.getValue()+"&nmPoliza="+idPoliza.getValue()+"&poliza="+numPoliza.getValue()+"&producto="+nombreProducto.getValue()+"&aseguradora="+nombreAseguradora.getValue()+"&proc="+proceso+"&fechaefectividadendoso="+FECHA_EFECTIVIDAD_ENDOSO;
    });
});

function cerrar() {
	var proceso="";
    var params="proc=";
    
    if(PROCESO_RENOVACION){
    	proceso=RENOVACION;
    	params+= RENOVACION;
    }else{
    	proceso=ENDOSO;
    	params+= ENDOSO;
    }
    
    var url = 'flujoendoso/psacaEndoso.action';
    var conn = new Ext.data.Connection();
    
    conn.request({
        url: url,
        method: 'POST',
        successProperty : '@success',
        params: params,
        callback: function (options, success, response){
            var res = Ext.util.JSON.decode(response.responseText).mensajeError;
            if(res != ''){
            	Ext.Msg.alert('Error', Ext.util.JSON.decode(response.responseText).mensajeError);
            }
        }
    });      
}
