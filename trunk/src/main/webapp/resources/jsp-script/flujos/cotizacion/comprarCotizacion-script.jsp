//<script type="text/javascript">


//Ext singleton
Ext.onReady(function(){

Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";
var windowAgregarPersonaFuncionPoliza;

 
var descripcionAseguradora = new Ext.form.TextField({
    value:'<s:property value="dsUnieco" />',
    name:'descripcionAseguradora',
    fieldLabel: 'Aseguradora',
    //readOnly:true,
    disabled: true,
    labelStyle: 'text-align:right;'
});        

var descripcionPlan = new Ext.form.TextField({
    value:'<s:property value="dsPlan" />',
    name:'descripcionPlan',
    fieldLabel: 'Plan',
    //readOnly:true,
    disabled: true,
    labelStyle: 'text-align:right;'
});       

var descripcionFormaDePago = new Ext.form.TextField({
    value:'<s:property value="dsPerpag" />',
    name:'descripcionFormaDePago',
    fieldLabel: 'FormaDePago',
    //readOnly:true,
    disabled: true,
    labelStyle: 'text-align:right;'
});       

var marca = new Ext.form.TextField({
    value:'<s:property value="dsclave1" />',
    name:'dsclave1',
    fieldLabel: 'Marca',
    readOnly:true
});        

var modelo = new Ext.form.TextField({
    value:'<s:property value="dsclave2" />',
    name:'dsclave2',
    fieldLabel: 'Modelo',
    readOnly:true
});       

var descripcion = new Ext.form.TextField({
    value:'<s:property value="descripcion" />',
    name:'descripcion',
    fieldLabel: 'Descripcion',
    readOnly:true
});       
       
var vigenciaInicio = new Ext.form.TextField({
    value:'<s:property value="feEmisio" />',
    name:'vigenciaInicio',
    fieldLabel:'Inicio',
    //readOnly:true,
    disabled : true,
    labelStyle: 'text-align:right;'
});

var vigenciaFin = new Ext.form.TextField({
    value:'<s:property value="feVensim" />',
    name:'vigenciaFin',
    fieldLabel:'Fin',   
    //readOnly:true
    disabled: true
});

var tipoAccesorio = new Ext.form.TextField({
    value:'<s:property value="tipoAccesorio" />',
    name:'tipoAccesorio',
    fieldLabel: 'Tipo',
    readOnly:true       
});       

var montoAccesorio = new Ext.form.TextField({
    value:'<s:property value="montoAccesorio" />',
    name:'montoAccesorio',
    fieldLabel: 'Monto',
    readOnly:true       
});       

var totalPagar = new Ext.form.TextField({
    value:'<s:property value="totalPagar" />',
    name:'totalPagar',
    fieldLabel: 'Total a pagar',
    //allowDecimals :false,
    //allowNegative :false
    readOnly:true,
    labelStyle: 'text-align:right;'
});       

var reciboUnico = new Ext.form.TextField({
    value:'<s:property value="reciboUnico" />',
    name:'reciboUnico',
    fieldLabel: 'Recibo Inicial',
    //allowDecimals :false,
    //allowNegative :false
    //readOnly:true,
    readOnly:true,
    labelStyle: 'text-align:right;'
});

var numRecibo = new Ext.form.TextField({
    value:'<s:property value="numRecibos" />',
    name:'numRecibos',
    fieldLabel: 'N&uacute;mero de Recibos Subsecuentes',
    //allowDecimals :false,
    //allowNegative :false
    readOnly:true,
    labelStyle: 'text-align:right;'
});  

var recibosSub = new Ext.form.TextField({
    value:'<s:property value="recibosSub" />',
    name:'recibosSub',
    fieldLabel: 'Recibos Subsecuentes',
    //allowDecimals :false,
    //allowNegative :false
    readOnly:true,
    labelStyle: 'text-align:right;'
});

var numeroTarjeta = new Ext.form.NumberField({
    id: 'numero-tarjeta',
    name:'numeroTarjeta',
    fieldLabel:'N&uacute;mero de Tarjeta',
    allowDecimals :false,
    allowNegative :false,
    allowBlank:false,
    labelStyle: 'text-align:right;'
});

var codigoAseguradora = new Ext.form.Hidden({   
    name:'codigoAseguradora'
});        

var codigoPlan = new Ext.form.Hidden({  
    name:'codigoPlan'
});  

var codigoFormaDePago = new Ext.form.Hidden({   
    name:'codigoFormaDePago'
});  

var vencimiento = new Ext.form.DateField({
    id: 'fecha-vencimiento',
    name:'vencimiento',
    fieldLabel:'Fecha de vencimiento',
    labelStyle: 'text-align:right;',
    allowBlank: false,
    format: 'd/m/Y',
    width: 170,
	maxLength: '10',
    maxLengthText: 'La fecha debe tener formato dd/MM/yyyy',
    frame: true
});

var digitoVerificador = new Ext.form.NumberField({
    id:'digito-verificador',
    name:'digitoVerificador',
    fieldLabel:'Digito Verificador',
    allowDecimals :false,
    allowNegative :false,
    allowBlank: false,
    labelStyle: 'text-align:right;'
});

var numeroSituacion = new Ext.form.Hidden({ 
    value:'<s:property value="numeroSituacion" />',
    name:'numeroSituacion'
}); 

var codigoPersonaUsuario = new Ext.form.Hidden({    
    id:'hidden-codigo-persona-usuario-compra',
    name:'codigoPersonaUsuario'
}); 

var codigoDomicilio = new Ext.form.Hidden({ 
    id:'hidden-codigo-domicilio-compra',
    name:'codigoDomicilio'
});

var codigoInstrumentoPago = new Ext.form.Hidden({   
    id:'hidden-codigo-instrumento-pago-compra',
    name:'codigoInstrumentoPago'
});

var codigoInstrumentoPagoPorCliente = new Ext.form.Hidden({   
    id:'hidden-cdInsCte',
    name:'cdInsCte'
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
    id:'hidden-tipo-tarjeta-compra',
    name:'codigoTipoTarjeta'
});

var swCredito  = new Ext.form.Hidden({   
    id:'hidden-sw-credito',
    name:'swCredito'
});

//*****Funcion que al dar click en el boton del GridPanel muestra un mensaje si no se seleccionó un renglon*****
//@param idBtn id del boton del GridPanel
//@param grid  elemento de tipo Ext.grid.GridPanel (tal como fue declarada la variable)
function muestraMsgEligeRecordDeGrid(idBtn, grid) {
	Ext.getCmp(idBtn).on('click',function(){
		if( !grid.getSelectionModel().hasSelection() ) {
			Ext.Msg.alert(_MSG_AVISO, _MSG_NO_ROW_SELECTED);
		}
	});
}

/**********************************************
 **          Catalogos Compra                **
 **********************************************/
var storeComboPersonasUsuarioMultiSelect = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: '<s:url namespace="/flujocotizacion" action="listaPersonasUsuarioMultiSelect" includeParams="none"/>'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaPersonasUsuarioMultiSelect'
            },[
           {name: 'value',      type: 'string', mapping:'value'},
           {name: 'label',      type: 'string', mapping:'label'}
        ]),
        remoteSort: false
});
storeComboPersonasUsuarioMultiSelect.setDefaultSort('label', 'DESC');
storeComboPersonasUsuarioMultiSelect.load();
var storeComboPersonasUsuario = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: '<s:url namespace="/flujocotizacion" action="listaPersonasUsuario" includeParams="none"/>'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaPersonasUsuario'
            },[
           {name: 'value',      type: 'string', mapping:'value'},
           {name: 'label',      type: 'string', mapping:'label'}
        ]),
        remoteSort: false
});
storeComboPersonasUsuario.setDefaultSort('label', 'DESC');
storeComboPersonasUsuario.load();
var storeComboDomicilio = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: '<s:url namespace="/flujocotizacion" action="listaDomicilio"/>'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaDomicilio'
            },[
           {name: 'value',      type: 'string', mapping:'value'},
           {name: 'label',      type: 'string', mapping:'label'}
        ]),
        remoteSort: false,
        baseParams: {codigoPersonaUsuario: ''}
});
storeComboDomicilio.setDefaultSort('label', 'DESC');
//storeComboDomicilio.load();


var storeComboInstrumentoDePago = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: '<s:url namespace="/flujocotizacion" action="listaFormasPago" includeParams="none"/>'
        }),
        reader: new Ext.data.JsonReader({
            root: 'instrumentosClienteList'
            },[
           {name: 'value',      type: 'string', mapping:'cdForPag'},
           {name: 'label',      type: 'string', mapping:'dsForPag'},
           {name: 'cdInsCte',      type: 'string', mapping:'cdInsCte'}
        ]),
        remoteSort: true
});




storeComboInstrumentoDePago.setDefaultSort('label', 'DESC');
storeComboInstrumentoDePago.load();
/*var storeBanco = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: '<s:url namespace="/flujocotizacion" action="listaBancos" includeParams="none"/>'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaBancos'
            },[
           {name: 'value',      type: 'string', mapping:'value'},
           {name: 'label',      type: 'string', mapping:'label'}
        ]),
        remoteSort: false
});
storeBanco.setDefaultSort('label', 'DESC');
storeBanco.load();

var storeSucursal = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: '<s:url namespace="/flujocotizacion" action="listaSucursal" includeParams="none"/>'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaSucursal'
            },[
           {name: 'value',      type: 'string', mapping:'value'},
           {name: 'label',      type: 'string', mapping:'label'}
        ]),
        baseParams:{codigoBanco:''},
        remoteSort: false
});
storeSucursal.setDefaultSort('label', 'DESC');
//storeSucursal.load();*/
var storeFuncionRol = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: '<s:url namespace="/flujocotizacion" action="listaFuncionRol" includeParams="none"/>'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaFuncionRol'
            },[
           {name: 'value',      type: 'string', mapping:'value'},
           {name: 'label',      type: 'string', mapping:'label'}
        ]),
        remoteSort: false
});
storeFuncionRol.setDefaultSort('label', 'DESC');
storeFuncionRol.load();
var storeSexo = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: '<s:url namespace="/flujocotizacion" action="listaSexo" includeParams="none"/>'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaSexo'
            },[
           {name: 'value',      type: 'string', mapping:'value'},
           {name: 'label',      type: 'string', mapping:'label'}
        ]),
        remoteSort: false
});
storeSexo.setDefaultSort('label', 'DESC');
storeSexo.load();

/*
var storeComboTipoTarjeta = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: '<s:url namespace="/flujocotizacion" action="listaTipoTarjetaYCredito" includeParams="none"/>'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaTipoTarjetaYCredito'
            },[
           {name: 'value', type: 'string', mapping:'msgId'},
           {name: 'label', type: 'string', mapping:'msgTitle'},
           {name: 'extra', type: 'string', mapping:'resultado'}
        ]),
        remoteSort: false
});
storeComboTipoTarjeta.setDefaultSort('label', 'DESC');
storeComboTipoTarjeta.load();
*/
var comboPersonaUsuario =new Ext.form.ComboBox({
        tpl: '<tpl for="."><div ext:qtip="{label}. {value}" class="x-combo-list-item">{label}</div></tpl>',
        store: storeComboPersonasUsuario,
        displayField:'label',
        valueField: 'value',
        allowBlank:false,
        typeAhead: true,
        labelAlign: 'top',
        labelStyle: 'text-align:right;',
        width:300,
        listWidth:350,
        mode: 'local',
        triggerAction: 'all',
        emptyText:'Seleccione...',
        selectOnFocus:true,
        fieldLabel: 'Pagado Por',
        name:"descripcionPersonaUsuario",
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
            //alert(Ext.getCmp('hidden-codigo-persona-usuario-compra').getValue());
            comboDomicilio.clearValue();
            storeComboDomicilio.baseParams['codigoPersonaUsuario'] = valor;
            storeComboDomicilio.load({
              callback : function(r, options, success) {
                  if (!success) {
                    //  Ext.MessageBox.alert('Aviso','No se encontraron registros');  
                         storeComboDomicilio.removeAll();
                      }
                  }
            });
        }
});
storeComboDomicilio
var comboDomicilio =new Ext.form.ComboBox({
        tpl: '<tpl for="."><div ext:qtip="{label}. {value}" class="x-combo-list-item">{label}</div></tpl>',
        store: storeComboDomicilio,
        displayField:'label',
        valueField: 'value',
        allowBlank:false,
        typeAhead: true,
        labelAlign: 'top',
        labelStyle: 'text-align:right;',
        width:300,
        listWidth:350,        
        mode: 'local',
        triggerAction: 'all',
        emptyText:'Seleccione...',
        selectOnFocus:true,
        fieldLabel: 'Domicilio',
        name:"descripcionDomicilio",
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
            //alert(Ext.getCmp('hidden-codigo-domicilio-compra').getValue());
        }
});

var comboInstrumentoDePago =new Ext.form.ComboBox({
        tpl: '<tpl for="."><div ext:qtip="{label}. {value}" class="x-combo-list-item">{label}</div></tpl>',
        store: storeComboInstrumentoDePago,
        displayField:'label',
        valueField: 'value',
        allowBlank:false,
        typeAhead: true,
        labelAlign: 'top',
        labelStyle: 'text-align:right;',
        mode: 'local',
        editable:false,
        triggerAction: 'all',
        emptyText:'Seleccione...',
        selectOnFocus:true,
        fieldLabel: 'Instrumento de pago',
        name:"descripcionInstrumentoDePago",
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
            comboInstrumentoDePago.setDisabled(true);
            var connect = new Ext.data.Connection();
			connect.request ({
				url: '<s:url namespace="/flujocotizacion" action="atributosVariablesInstrumetosPago" includeParams="none"/>',
				params: {
					cdInsCte: record.get('cdInsCte')/*,
					descripcionFormaDePago: record.get('label')*/
				},
				callback: function (options, success, response) {	
					comboInstrumentoDePago.setDisabled(false);			   
					if (Ext.util.JSON.decode(response.responseText).success != false) {
						//alert(Ext.util.JSON.decode(response.responseText).mensaje);
						/*try{
							if(Ext.util.JSON.decode(response.responseText).storesCombos){
								alert(Ext.util.JSON.decode(response.responseText).storesCombos);
							}
						}catch(e){}
						*/
						if(Ext.getCmp('fieldsetId')) Ext.getCmp('fieldsetId').destroy();
						Ext.getCmp('id-form-instrumentos-pago').add(Ext.util.JSON.decode(Ext.util.JSON.decode(response.responseText).mensaje));
						Ext.getCmp('id-form-instrumentos-pago').doLayout();
						
						
					}else{
						if(Ext.getCmp('fieldsetId')) Ext.getCmp('fieldsetId').destroy();
						Ext.getCmp('id-form-instrumentos-pago').doLayout();
					}
				}
			});
            /*
            var feedback = record.get('extra');
            var valor = record.get('value');
            //alert( "valor="+valor + "   desc="+record.get('label') + "   feedback="+feedback );
            
            //Ext.getCmp('id-form-pago-hide').reset();
             
             
            if(feedback == 'S'){
                Ext.getCmp('id-form-pago-hide').show();
                Ext.getCmp('combo-banco').enable();
                Ext.getCmp('combo-banco').reset();
                Ext.getCmp('descripcion-combo-sucursal-compra').enable();
                Ext.getCmp('descripcion-combo-sucursal-compra').reset();
                Ext.getCmp('combo-tipo-tarjeta').enable();
                Ext.getCmp('combo-tipo-tarjeta').reset();
      
                Ext.getCmp('numero-tarjeta').enable();
                Ext.getCmp('numero-tarjeta').reset();
                Ext.getCmp('fecha-vencimiento').enable();
                Ext.getCmp('fecha-vencimiento').reset();
                Ext.getCmp('digito-verificador').enable();
                Ext.getCmp('digito-verificador').reset();
                
                var swcredito = record.get('extra2');
                Ext.getCmp('hidden-sw-credito').setValue(swcredito);
                 //alert('swcredito=' + Ext.getCmp('hidden-sw-credito').getValue());
                
            }else{
                Ext.getCmp('id-form-pago-hide').hide();
                Ext.getCmp('combo-banco').disable();
                Ext.getCmp('combo-banco').reset();
                Ext.getCmp('descripcion-combo-sucursal-compra').disable();
                Ext.getCmp('descripcion-combo-sucursal-compra').reset();
                Ext.getCmp('combo-tipo-tarjeta').disable();
                Ext.getCmp('combo-tipo-tarjeta').reset();
                
           
                Ext.getCmp('numero-tarjeta').disable();
                Ext.getCmp('numero-tarjeta').reset();
                Ext.getCmp('fecha-vencimiento').disable();
                Ext.getCmp('fecha-vencimiento').reset();
                Ext.getCmp('digito-verificador').disable();
                Ext.getCmp('digito-verificador').reset();
            }
            
            */
            //var valor = ;
            Ext.getCmp('hidden-codigo-instrumento-pago-compra').setValue(record.get('value'));
            Ext.getCmp('hidden-cdInsCte').setValue(record.get('cdInsCte'));
            //alert("Fijando hidden: "+ Ext.getCmp('hidden-codigo-instrumento-pago-compra').getValue());
        }
});

/*
var comboSucursal =new Ext.form.ComboBox({
        id:'descripcion-combo-sucursal-compra',
        tpl: '<tpl for="."><div ext:qtip="{label}. {value}" class="x-combo-list-item">{label}</div></tpl>',
        store: storeSucursal,
        displayField:'label',
        valueField: 'value',
        typeAhead: true,
        allowBlank:false,
        labelAlign: 'top',
        labelStyle: 'text-align:right;',
        width:300,
        listWidth:350,        
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
            //alert(Ext.getCmp('hidden-codigo-sucursal-compra').getValue());
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
        labelStyle: 'text-align:right;',
        width:300,
        listWidth:350,        
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
            //alert(Ext.getCmp('hidden-codigo-banco-compra').getValue());
        }
});

var comboTipoTarjeta =new Ext.form.ComboBox({
        id: 'combo-tipo-tarjeta',
        tpl: '<tpl for="."><div ext:qtip="{label}. {value}" class="x-combo-list-item">{label}</div></tpl>',
        store: storeComboTipoTarjeta,
        displayField:'label',
        valueField: 'value',
        typeAhead: true,
        labelAlign: 'top',
        labelStyle: 'text-align:right;',
        listWidth:200,        
        mode: 'local',
        triggerAction: 'all',
        emptyText:'Seleccione...',
        selectOnFocus:true,
        allowBlank:false,
        fieldLabel: 'Tipo de tarjeta',
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
            Ext.getCmp('hidden-tipo-tarjeta-compra').setValue(valor);
            
            //var swcredito = record.get('swcredito');
            //Ext.getCmp('hidden-sw-credito').setValue(swcredito);
            //alert(Ext.getCmp('hidden-tipo-tarjeta-compra').getValue());
            //alert(Ext.getCmp('hidden-sw-credito').getValue());
        }
});
*/
 /*********************************************
 **          Catalogos Compra                **
 **********************************************/
/**********************************************
 **          Grid de Coberturas              **
 **********************************************/

var storeGridCoberturas = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: '<s:url namespace="/flujocotizacion" action="listaCobertutasCompra" includeParams="none"/>'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaCoberturaCompra'
            },[
           {name: 'cdGarant',       type: 'string', mapping:'cdGarant'},
           {name: 'dsGarant',       type: 'string', mapping:'dsGarant'},
           {name: 'sumaAsegurada',  type: 'string', mapping:'sumaAsegurada'},
           {name: 'deducible',      type: 'string', mapping:'deducible'}
        ]),
        baseParams:{cdPlan:'<s:property value="cdPlan" />', numeroSituacion:'<s:property value="numeroSituacion" />',cdCiaaseg:'<s:property value="cdCiaaseg" />'}
        //,remoteSort: true
});
storeGridCoberturas.load();
function toggleDetails(btn, pressed){
        var view = grid.getView();
        view.showPreview = pressed;
        view.refresh();
}
        
var cm = new Ext.grid.ColumnModel([
        {header: "Nombre",            dataIndex:'dsGarant',         width: 200, sortable:true,id:'dsGarant'},           
        {header: "Suma Asegurada",    dataIndex:'sumaAsegurada',    width: 175, sortable:true, align:"right"},                 
        {header: "Deducible",         dataIndex:'deducible',        width: 100, sortable:true, align:"right"}     
]);
       
        
    
var gridCotizacionCompra= new Ext.grid.GridPanel({
        store: storeGridCoberturas,
        id:'grid-cotizacion-compra',
        //border:true,
        cm: cm,
        enableColLock: false,
        loadMask: true,     
        sm: new Ext.grid.RowSelectionModel({
            singleSelect: true,     
            listeners: {                            
                rowselect: function(sm, row, rec) {                                                                                                                                 
                }         
            }
        }),   
        //collapsible:true,
        stripeRows:true,                                                    
        width:520,
        height:190,
        frame:true,     
        //title:'Coberturas',
        bodyStyle:'padding:5px'                                                                                              
});     
/**********************************************
 **          Grid de Coberturas              **
 **********************************************/
 /**********************************************
 **          Grid de Accesorios              **
 **********************************************/

var storeGridAccesorios = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: '<s:url namespace="/flujocotizacion" action="listaAccesorios" includeParams="none"/>'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaMPoliObj'
            },[
           {name: 'dsobjeto',   	type: 'string', mapping:'dsobjeto'},
           {name: 'nmvalor',    	type: 'string', mapping:'nmvalor'},
           {name: 'dsdescripcion',	type: 'string', mapping:'dsdescripcion'}   
        ]),
        baseParams:{numeroSituacion:'<s:property value="numeroSituacion" />'},
        remoteSort: true
});
storeGridAccesorios.load();
        
var cmAccesorios = new Ext.grid.ColumnModel([
        {header: "Tipo",      		dataIndex:'dsobjeto', 		width: 150, sortable:true,id:'dsobjeto'},
        {header: "Descripción",     dataIndex:'dsdescripcion',  width: 250, sortable:true },           
        {header: "Monto",     		dataIndex:'nmvalor', 		width: 100, sortable:true, renderer: Ext.util.Format.usMoney}     
]);
       
        
    
var gridAccesoriosCompra= new Ext.grid.GridPanel({
        store: storeGridAccesorios,
        id:'grid-accesorios-compra',
        //border:true,
        cm: cmAccesorios,
        enableColLock: false,
        loadMask: true,     
        sm: new Ext.grid.RowSelectionModel({
            singleSelect: true,     
            listeners: {                            
                rowselect: function(sm, row, rec) {                                                                                                                                 
                }         
            }
        }),   
        collapsible:true,
        stripeRows:true,                                                    
        width:540,
        height:190,
        frame:true,     
        title:'Accesorios',
        bodyStyle:'padding:5px'                                                                                              
});     
/**********************************************
 **          Grid de Accesorios              **
 **********************************************/
/**********************************************
 **          Grid de Funciones Poliza        **
 **********************************************/

var storeGridFuncionesPoliza = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: '<s:url namespace="/flujocotizacion" action="listaFuncionesPoliza" includeParams="none"/>'
        }),
        reader: new Ext.data.JsonReader({
            root: 'funcionesPoliza'
            },[
           {name: 'value',  type: 'string', mapping:'value'},
           {name: 'identificador',  type: 'string', mapping:'identificador'},
           {name: 'descripcionRol',     type: 'string', mapping:'descripcionRol'},
           {name: 'fechaNacimiento',    type: 'string', mapping:'fechaNacimiento'}
        ]),
        remoteSort: true
});
storeGridFuncionesPoliza.load();
        
var cmFuncionesPoliza = new Ext.grid.ColumnModel([
        {header: "Identificador", dataIndex:'identificador', hidden:true, width: 20, sortable:true, id:'identificador'},    
        {header: "Funcion", dataIndex:'descripcionRol', width: 170, sortable:true},    
        {header: "Nombre", dataIndex:'value', width: 170, sortable:true},      
        {header: "Fecha de Nacimiento", dataIndex:'fechaNacimiento', width: 135, sortable:true}                 
]);
       

var indexFuncionesPolizaCompra;
var smFuncionesPolizaCompra;
var gridFuncionesPolizaCompra= new Ext.grid.GridPanel({
        store: storeGridFuncionesPoliza,
        id:'grid-funciones-poliza-compra',
        cm: cmFuncionesPoliza,
        enableColLock: false,
        loadMask: true,     
        sm: new Ext.grid.RowSelectionModel({
            singleSelect: true,     
            listeners: {                            
                rowselect: function(sm, row, rec) {
                    indexFuncionesPolizaCompra = row;
                    smFuncionesPolizaCompra = sm;
                }
            }
        }),  
        stripeRows:true,                                                    
        width:520,
        height:190,
        frame:true,     
        //title:'Funciones en la Poliza',
        bodyStyle:'padding:5px',                                                                                             
        buttonAlign:'center',
        buttons:[{
			text:"Eliminar",
			id:'eliminar-funciones-poliza-compra'
			<s:if test="%{#session['LISTA_COMBOS_FUNCIONES_POLIZA']==null}">
				,hidden: true
			</s:if>
        }]                                                                                               
});
Ext.getCmp('eliminar-funciones-poliza-compra').on('click', function(){

	if ( smFuncionesPolizaCompra == null ) {
		Ext.Msg.alert(_MSG_AVISO, _MSG_NO_ROW_SELECTED);
	} else {
		record=smFuncionesPolizaCompra.getSelected();
		
		if(record!=undefined && CD_SISROL!=undefined && CD_SISROL!= null && record.get('identificador')!=undefined && record.get('identificador')!=null && record.get('identificador')==CD_SISROL){
				Ext.Msg.alert('Aviso', 'No puede eliminar este rol de la p&oacute;liza');
				return;
		}

	    params = 'row='+ indexFuncionesPolizaCompra;
	    if(smFuncionesPolizaCompra.hasSelection()){
	    	var conn = new Ext.data.Connection();
    		conn.request ({
	    		url:'eliminarFuncionPoliza.action',
        		method: 'POST',
	        	params: params,
        		successProperty : '@success',
	        	callback: function (options, success, response) {
            			storeGridFuncionesPoliza.load();
	       		}
	    	});
    	} 
    }
});
//muestraMsgEligeRecordDeGrid('eliminar-funciones-poliza-compra', gridFuncionesPolizaCompra);



/**********************************************
 **          Grid de Funciones Poliza        **
 **********************************************/
/* 
 var formMultiselect = new Ext.form.FormPanel({ 
                labelWidth: 75,
                labelAlign: "right",
                height:200,
                width:545,
                items:[{
                    xtype:"combo",
                    fieldLabel:"Titular",
                    name:"multiselect",
                    store: storeComboPersonasUsuario,
                    displayField:'label',
                    valueField: 'value',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',                   
                    width:250,
                    //height:160,
                    allowBlank:true
                },{
                    xtype:"combo",
                    fieldLabel:"Conyuge",
                    name:"multiselect",
                    store: storeComboPersonasUsuario,
                    displayField:'label',
                    valueField: 'value',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',                   
                    width:250,
                    //height:160,
                    allowBlank:true
                },{
                
                    xtype:"multiselect",
                    fieldLabel:"Hijo",
                    name:"multiselect",
                    store: storeComboPersonasUsuario,
                    displayField:'label',
                    valueField: 'value',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',                   
                    width:250,
                    height:130,
                    allowBlank:true
                }]
            });
            formMultiselect.render("form-ct-multiselect");

*/ 

// Aqui se obtienen los stores para los combos de la sección 'Datos Complementarios' 
<s:if test="%{#session['STORES_ITEMS_PANTALLA_COMPRAR']!=null}">
	<s:component template="storesItemsComprarCotizacion.vm" templateDir="templates" theme="components" >
		<s:param name="STORES_ITEMS_PANTALLA_COMPRAR" value="%{#session['STORES_ITEMS_PANTALLA_COMPRAR']}" />
	</s:component>
</s:if>

var comprarFormPanel =  new Ext.form.FormPanel({                          
   id:'comprarFormPanel',
   url:'flujocotizacion/comprarCotizacionCompras.action',
   border:false,
   //frame:true,
   //autoScroll:true,
   width:550,   
   //height:800,
   autoHeight:true,
   title:'Comprar',
   layout:'form',
   items: [numeroSituacion,{
            xtype:"textfield",
            name:"id",
            id:"id",
            type:'hidden',
            hidden:true,
            labelSeparator:'' 
          },{
            layout:'form',
            //autoWidth:true,
            autoHeight:true,   
            border:false,
            items:[{          
                    xtype:'fieldset',
                    collapsible:true,
                    //title:'Datos Generales',
                    title:'<span style="color:#98012e;font-size:11px;font-family:Arial,Helvetica,sans-serif;">Datos Generales</span>',
                    width:540,
                    height:140,
                    items:[
                          descripcionAseguradora,
                          descripcionPlan,
                          {
                            layout:'form',
                            border:false,
                            items:[{
                                    layout:'column', 
                                    border:false,
                                    //width:540,
                                    items:[{                                            
                                            columnWidth:.5,
                                            border:false,
                                            layout:'form',
                                            //width:'240',
                                            //labelWidth: 100,
                                            items:[vigenciaInicio]
                                        },{                                         
                                            columnWidth:.5,
                                            border:false,
                                            layout:'form',
                                            labelAlign:'right',
                                            //width:'200',
                                            labelWidth: 30,
                                            items:[vigenciaFin]
                                    }]
                            }]
                          },
                          descripcionFormaDePago
                    ]
            }]
        },{
            layout:'form',
            border:false,
            autoWidth:true,
            autoHeight:true,
            items:[ {
                    xtype:'fieldset',
                    collapsible:true,
                    //title:'Inciso',
                    labelAlign: 'right',
                    title:'<span style="color:#98012e;font-size:11px;font-family:Arial,Helvetica,sans-serif;">Objeto Asegurado</span>',
                    width:540,
                    autoHeight :true
                    <s:if test="%{#session['TEXT_FIELDS']!=null}">
                    ,
                    items: [    
                     <s:component template="builderItemsResultCotizacion.vm" templateDir="templates" theme="components">
                            <s:param name="TEXT_FIELDS" value="%{#session['TEXT_FIELDS']}"/>
                     </s:component>
                    ]
                    </s:if>
            }]    
        }<s:if test="%{#session['ACCESORIOS_COMPRA']!=null}">
        ,{
                            layout:'form',
                            border:false,
                            //autoWidth:true,
                            autoHeight:true,
                            items:[gridAccesoriosCompra]
        }</s:if>,{
                            layout:'form',
                            border:false,
                            //autoWidth:true,
                            autoHeight:true,
                            //items:[gridCotizacionCompra]
                            items:[{
                                xtype:'fieldset',
                                collapsible:true,
                                width:540,
                                autoHeight :true,
                                //title:'Detalle',
                                title:'<span style="color:#98012e;font-size:11px;font-family:Arial,Helvetica,sans-serif;">Coberturas</span>',
                                items:[gridCotizacionCompra]
                            }]
                    
        },{
            layout:'form',
            border:false,
            autoHeight :true,
            items:[{
            		id: 'datosComplementarios',
                    xtype:'fieldset',
                    collapsible:true,
                    width:540,
                    autoHeight :true,
                    //title:'Datos Complementarios',
                    labelAlign: 'right',
                    title:'<span style="color:#98012e;font-size:11px;font-family:Arial,Helvetica,sans-serif;">Datos Complementarios</span>',
                    items:[{
                            layout:'form',
                            border:false,
                            autoHeight :true
                            <s:if test="%{#session['ITEMS_PANTALLA_COMPRAR']!=null}">
                    		,
                            items:[             
                                <s:component template="itemsComprarCotizacion.vm" templateDir="templates" theme="components" >
                                    <s:param name="ITEMS_PANTALLA_COMPRAR" value="%{#session['ITEMS_PANTALLA_COMPRAR']}" />
                                </s:component>
                            ]
                            </s:if>
                    }]
            }]        
        }
        ,{
            layout:'form',
            border:false,
            autoHeight :true,
            items:[
            		<s:if test="%{#session['LISTA_COMBOS_FUNCIONES_POLIZA']!=null}">
            		{
                    xtype:'fieldset',
                    collapsible:true,
                    width:540,
                    autoHeight :true,
                    //title:'Funciones en la Poliza',
                    labelAlign: 'right',
                    title:'<span style="color:#98012e;font-size:11px;font-family:Arial,Helvetica,sans-serif;">Roles en la P&oacute;liza</span>',
                    items:[{
                            layout:'form',                          
                            border:false,
                            buttonAlign:'center',
                            autoHeight :true
                            <s:if test="%{#session['LISTA_COMBOS_FUNCIONES_POLIZA']!=null}">
                    		,
                            items:[
                                <s:component template="itemsCompraCotizacionFuncionesPersona.vm" templateDir="templates" theme="components" >
                                    <s:param name="LISTA_COMBOS_FUNCIONES_POLIZA" value="%{#session['LISTA_COMBOS_FUNCIONES_POLIZA']}" />
                                </s:component>
                            ]
                            </s:if>
                            ,
                            buttons:[{text:'Asociar', handler:function(){
                                //alert(Ext.getCmp('comprarFormPanel').getForm().getValues(true)); 
                                var params = Ext.getCmp('comprarFormPanel').getForm().getValues(true);
                                params+="&validacion=algo";
                                //alert(params);
                                var connecti = new Ext.data.Connection();
                                connecti.request ({
                                    url:'flujocotizacion/funcionesPolizaCotizacionCompra.action',
                                    method: 'POST',
                                    params: params,
                                    successProperty : '@success',
                                    callback: function (options, success, response) {
                                        //if (Ext.util.JSON.decode(response.responseText).success) {
                                            //Ext.getCmp('grid-funciones-poliza-compra').getStore().load();
                                            storeGridFuncionesPoliza.load();
                                        //}
                                    }   
                                }); 
                            }},{text:'Nuevo',handler:function(){ formPanel.getForm().reset(); windowAgregarPersonaFuncionPoliza.show();;}}]    
                    }]
            //},gridFuncionesPolizaCompra]        
        },
        </s:if>
        {
                xtype:'fieldset',
                collapsible:true,
                width:540,
                autoHeight :true,
                //title:'Funciones en la Poliza',
                title:'<span style="color:#98012e;font-size:11px;font-family:Arial,Helvetica,sans-serif;">Roles asociados en la Póliza</span>',
                items:[gridFuncionesPolizaCompra]
            }]
        }
        ,{
        
                    xtype:'fieldset',
                    collapsible:true,
                    autoHeight:true,
                    width:540,
                    //title:'Recibos',
                    title:'<span style="color:#98012e;font-size:11px;font-family:Arial,Helvetica,sans-serif;">Recibos</span>',
                    items:[{
                            layout:'form',
                            border:false,
                            autoHeight:true,
                            items:[totalPagar,reciboUnico]
                    },{
                            layout:'form',
                            id:'id-form-num-recibos-hide',
                            hidden:true,
                            border:false,
                            items:[numRecibo]
                    },{
                            layout:'form',
                            id:'id-form-recibos-sub-hide',
                            hidden:true,
                            border:false,
                            items:[recibosSub]
                    }]
        },{
            layout:'form',
            border:false,
            autoHeight :true,
            items:[{
                    xtype:'fieldset',
                    collapsible:true,
                    width:540,
                    height: 350,
                    autoHeight :true,
                    //title:'Instrumento de Pago',
                    title:'<span style="color:#98012e;font-size:11px;font-family:Arial,Helvetica,sans-serif;">Instrumento de Pago</span>',
                    items:[{
                            layout:'form',
                            border:false,
                            //width:540,
                            //autoHeight :true,
                            //height: 600,
                            items:[{
                                    layout:'form',
                                    border:false,
                                    items:[ comboPersonaUsuario,codigoPersonaUsuario,
                                            comboDomicilio,codigoDomicilio,
                                            comboInstrumentoDePago,codigoInstrumentoPago,codigoInstrumentoPagoPorCliente,
                                            {
                                            layout:'form',
                                            id:'id-form-instrumentos-pago',
                                            border:false,
                                            items:[/*
                                                comboBanco,codigoBanco,
                                                comboSucursal,codigoSucursal,
                                                comboTipoTarjeta,codigoTipoTarjeta,swCredito,
                                                numeroTarjeta,
                                                vencimiento,
                                                digitoVerificador*/
                                                {
                                    		xtype: 'hidden', 
                                    		id: 'fieldsetId' 
                                    		}]
                                    		}
                                    		
                                    
                                    
                                    ]
                            }]
                    }]
            }]
    }],
    buttons:[{text:'Comprar', 
			handler:function(){
				
				if (comprarFormPanel.form.isValid()) {
				
					if(Ext.getCmp('fieldsetId') && Ext.getCmp('fieldsetId').items){
						if(!validarFormaFieldsetDinamico()){
							Ext.MessageBox.alert('INFORMACI&Oacute;N', 'Por favor complete la informaci&oacute;n requerida');
							return;
						}
					}
				
					/*var fechaVencimientoValida = true;
					// Si el instrumento de pago es:
					// 1 Tarjeta de débito
					// 2 Tarjeta de crédito
					// Si alguno de estos conceptos cambia de clave, entonces modificar a nuevo valor
					if ( instrumentoPago == '1' || instrumentoPago == '2' ) {
						var fechaHoy = new Date();
						var fechaVenc = Ext.getCmp('fecha-vencimiento').getValue();

						// La Fecha de Vencimiento de la tarjeta de crédito (o débito) debe ser posterior al día de hoy 
						if ( fechaVenc <= fechaHoy ) {
							fechaVencimientoValida = false;
							Ext.MessageBox.alert('INFORMACI&Oacute;N','La fecha de Vencimiento debe ser posterior al d&iacute;a de hoy');
							Ext.getCmp('fecha-vencimiento').focus();
						} else {
							fechaVencimientoValida = true;	
						}
					}*/
					
					//Si no hay errores con la fecha de vencimiento de la tarjeta, continuar con el flujo
                  	//if ( fechaVencimientoValida ) {
                    	comprarFormPanel.form.submit({
                        	waitMsg:'Procesando...',
                        	success:function(form,action){
                        
                        		//VALIDACIONES PARAMETRIZADAS: Si hay mensajeValidacion hay que mostrarlo, sino continuar con el flujo
                        		var mensajeValidacion = Ext.util.JSON.decode(action.response.responseText).mensajeValidacion;
                        		if( !Ext.isEmpty(mensajeValidacion) ){
			                       	Ext.Msg.alert('Aviso', mensajeValidacion);
								}else{
	                        		//Si errorMessage es false, continuar con el flujo
                        			if(Ext.util.JSON.decode(action.response.responseText).errorMessage=="false"){
                        				//Enfocar la sección de Datos Complementarios para intentar que el Msg se vea al centro de la pantalla
                        				Ext.getCmp('datosComplementarios').focus();
		                        		Ext.Msg.show({
			                            	title: 'Estatus',
		                            		msg: Ext.util.JSON.decode(action.response.responseText).mensaje,
		                            		buttons: Ext.Msg.OK,
		                            		icon: Ext.Msg.OK,
		                            		fn: function (btn, text){
			                                    if (btn == 'ok'){
		                                    		var ot = Ext.util.JSON.decode(action.response.responseText).ordenTrabajo;
		                                    		if (ot != 1) {
			                                        	urlGenDocs="/acw/pdf/emision/";
		                                        		archivo=Ext.util.JSON.decode(action.response.responseText).namePdf;
		                                        		url = urlGenDocs + archivo;
		                                        		window.open(url, "popup", "width=800,height=600,Scrollbars=YES,Resizable=YES");
		                                        
		                                        		var recibo = Ext.util.JSON.decode(action.response.responseText).recibo;
		                                        		if (recibo !='1') {
			                                        		archivoRecibo=Ext.util.JSON.decode(action.response.responseText).namePdfRecibo;
		                                        			url2 = urlGenDocs + archivoRecibo;
		                                        			window.open(url2, "popup2", "width=800,height=600,Scrollbars=YES,Resizable=YES");
		                                        		}
		        									}
		        									
		        									
		        									//self.parent.location.reload(true);
		        									
													self.parent.location.href=_ACTION_PAGINA_PRINCIPAL;
		                                    	}
		                                	}
		                        		}); 
                        			}else{
                        				Ext.MessageBox.alert('INFORMACI&Oacute;N', Ext.util.JSON.decode(action.response.responseText).errorMessage);
                        			}
                        		}//end else de validaciones parametrizadas
                    		}//end success de submit
                    	});
                  	//}

                }else{

					Ext.MessageBox.alert('INFORMACI&Oacute;N', 'Por favor complete la informaci&oacute;n requerida');

                }
			}
		},
		{text:'Cancelar', handler:function(){self.parent.location.reload(true);}},
		{text:'Nueva cotizacion', handler:function(){window.location.replace("${ctx}/flujocotizacion/capturaCotizacion.action?CDRAMO=" 
                                        + _CDRAMO //Ext.util.JSON.decode(action.response.responseText).cdRamo  
                                        + "&&CDTIPSIT=" + _CDTIPSIT);}},
		{
			text: 'Regresar',
			handler: function() {
				window.location.href= _ACTION_RESULTADO_COTIZACION + '?clicBotonRegresaComprar=S';
			}
		}]     
});
comprarFormPanel.render('items');


function validarFormaFieldsetDinamico(){
	for(var i = 0 ; i< Ext.getCmp('fieldsetId').items.length ; i ++){
		if(!Ext.getCmp('fieldsetId').items.get(i).isValid())return false;
	}
return true;
}

/****************************************************
 *          Ventana Agregar Funcion Poliza          *
 ****************************************************/
    var rolFuncionPoliza = new Ext.form.ComboBox({
                    tpl: '<tpl for="."><div ext:qtip="{label}. {value}" class="x-combo-list-item">{label}</div></tpl>',
                    store: storeFuncionRol,
                    displayField:'label',
                    valueField: 'value',
                    typeAhead: true,
                    labelAlign: 'top',
                    labelStyle: 'text-align:right;',
                    heigth:'50',
                    listWidth:'150',        
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: 'Rol Funcion',
                    name: 'parametros.rol',
                    hiddenName: 'parametros.rol',
                    allowBlank:false,
                    blankText : 'Dato Requerido',
                    width:'150'   
    });
    var nombreFuncionPoliza = new Ext.form.TextField({
                    fieldLabel: 'Nombre',
                    name: 'parametros.nombre',
                    allowBlank:false,
                    blankText : 'Dato Requerido',
                    width:'150'   
    });
    var apellidoPaternoFuncionPoliza = new Ext.form.TextField({
                    fieldLabel: 'Apellido Paterno',
                    name: 'parametros.apellidoPaterno',
                    allowBlank:false,
                    blankText : 'Dato Requerido',
                    width:'150'   
    });
    var apellidoMaternoFuncionPoliza = new Ext.form.TextField({
                    fieldLabel: 'Apellido Materno',
                    name: 'parametros.apellidoMaterno',
                    allowBlank:false,
                    blankText : 'Dato Requerido',
                    width:'150'   
    });
    var sexoFuncionPoliza = new Ext.form.ComboBox({
                    tpl: '<tpl for="."><div ext:qtip="{label}. {value}" class="x-combo-list-item">{label}</div></tpl>',
                    store: storeSexo,
                    displayField:'label',
                    valueField: 'value',
                    typeAhead: true,
                    labelAlign: 'top',
                    labelStyle: 'text-align:right;',
                    heigth:'50',
                    listWidth:'150',        
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: 'Sexo',
                    name: 'parametros.sexo',
                    hiddenName: 'parametros.sexo',
                    allowBlank:false,
                    selectOnFocus:true,
                    blankText : 'Dato Requerido',
                    emptyText: 'Seleccione...',
                    width:'150'   
    });
    var fechaNacimientoFuncionPoliza = new Ext.form.DateField({
                    fieldLabel: 'Fecha de Nacimiento',
                    name: 'parametros.fechaNacimiento',
                    format:'d/m/Y',
                    allowBlank:false,
                    blankText : 'Dato Requerido',
                    width:'150'   
    });    
    var formPanel = new Ext.FormPanel({
        //id:'another-id',
        labelAlign: 'right',
        frame:true,
        bodyStyle:'padding:5px 5px 0',
        width: 600,
        url:'flujoscotizacion/guardaPersonaCotizacion.action',
        items: [
            //rolFuncionPoliza,
            nombreFuncionPoliza,
            apellidoPaternoFuncionPoliza,
            apellidoMaternoFuncionPoliza,
            sexoFuncionPoliza,
            fechaNacimientoFuncionPoliza
        ]
    });
    
    // define window and show it in desktop
    windowAgregarPersonaFuncionPoliza = new Ext.Window({
        title: 'Nuevo',
        width: 400,
        height:300,
        minWidth: 300,
        minHeight: 150,
        layout: 'fit',
        plain:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: formPanel,
        modal:true,
        buttons: [{
            text: 'Guardar', 
            handler: function() {
                // check form value 
                if (formPanel.form.isValid()) {
                    formPanel.form.submit({
                        waitMsg:'Procesando...',
                        failure: function(form, action) {
                            Ext.MessageBox.alert('Error', "Persona no agregada");
                        },
                        success: function(form, action) {
                            Ext.MessageBox.alert('Status', "Persona agregada correctamente");
                            windowAgregarPersonaFuncionPoliza.hide();
                            storeComboPersonasUsuarioMultiSelect.load({params:{start:0, limit:20}});
                            storeComboPersonasUsuario.load({params:{start:0, limit:20}});
                            
                            /*
                            //Mostrar en el grid de Roles asociados la nueva persona:
                            var _claveNuevaPersona = Ext.util.JSON.decode(action.response.responseText).claveNuevaPersona
                            var conexion = new Ext.data.Connection();
                            conexion.request ({
                            	url:'flujocotizacion/funcionesPolizaCotizacionCompra.action',
                                method: 'POST',
                                params: {
									funcionesPersona: _claveNuevaPersona
								},
                                successProperty : '@success',
                                callback: function (options, success, response) {
                                	storeGridFuncionesPoliza.load();
								}
							});
							*/
                        }
                    });                   
                } else{
                    Ext.MessageBox.alert('Error', 'Favor de llenar los datos requeridos');
                }             
            }
        },{
            text: 'Cancelar',
            handler: function(){windowAgregarPersonaFuncionPoliza.hide();}
        }]
    });

   
var contNumRecibos = '<s:property value="numRecibos" />';
if(contNumRecibos==null || contNumRecibos=="" || contNumRecibos=="0"){
    Ext.getCmp('id-form-num-recibos-hide').hide();
}else{
    Ext.getCmp('id-form-num-recibos-hide').show();
}

var contRecibosSub = '<s:property value="recibosSub" />';
if(contRecibosSub==null || contRecibosSub==""){
    Ext.getCmp('id-form-recibos-sub-hide').hide();
}else{
    Ext.getCmp('id-form-recibos-sub-hide').show();
}
 /****************************************************
 *          Ventana Agregar Funcion Poliza          *
 ****************************************************/
 
//Validacion para modificar etiqueta de reciboUnico:
if( totalPagar.getValue() == reciboUnico.getValue() ){
	reciboUnico.setFieldLabel("Unico Recibo:");
}
 
});

//</script>