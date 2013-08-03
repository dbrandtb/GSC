<script type="text/javascript">

Ext.onReady(function(){

    Ext.QuickTips.init();
    Ext.QuickTips.enable();
    
    // turn on validation errors beside the field globally
    Ext.form.Field.prototype.msgTarget = 'side';
    // Variable con datos ext.
    var ElementosExt = <s:component template="camposPantallaDatosExt.vm" templateDir="templates" theme="components" ><s:param name="dext" value="dext" /></s:component>;
    var BotonDatosVariables = <s:component template="botonsDatosVariables.vm" templateDir="templates" theme="components" ><s:param name="botonDatVar" value="botonDatVar" /></s:component>;
    //alert(ElementosExt);
    
    //Variable tipo hidden para evitar la excepcion de la pantalla
    //si el pl no trae datos.
    var sinDatos = new Ext.form.Hidden({
        id:'sinDatos',
        name:'SinDatos'
    });
    
    
    //Si el Pl no trae datos se mostrara un mensaje y se pintara un hidden.
    if (ElementosExt==""){
    /*  Ext.Msg.show({
           title:'Datos Rol',
           msg: 'No se encontraron datos.',
           buttons: Ext.Msg.OK,
           icon: Ext.MessageBox.INFO
        });*/   
        ElementosExt = sinDatos;
    }
    
//***********
var cdUnieco = new Ext.form.TextField({
    fieldLabel: '',
    allowBlank: true,
    name: 'cdUnieco',
    //value: '1',
    value:'<s:property value="cdUnieco" />',
    hidden: true
});

var cdRamo = new Ext.form.TextField({
    fieldLabel: '',
    allowBlank: true,
    name: 'cdRamo',
    //value: '1', 
    value:'<s:property value="cdRamo" />',
    hidden: true
});

var nmPoliza = new Ext.form.TextField({
    fieldLabel: '',
    allowBlank: true,
    name: 'nmPoliza',
    //value: '26', 
    value:'<s:property value="nmPoliza" />',
    hidden: true
});

var estado = new Ext.form.TextField({
    fieldLabel: '',
    allowBlank: true,
    name: 'estado',
    //value: 'M', 
    value:'<s:property value="estado" />',
    hidden: true
});

//******** DATOS DE POLIZA **********
var poliza = new Ext.form.TextField({
    fieldLabel: 'Poliza',
    allowBlank: true,
    name: 'poliza',
    width: 150,
    disabled: true,
	value:'<s:property value="poliza" />',
    frame: true 
});

var aseguradora = new Ext.form.TextField({
    fieldLabel: 'Aseguradora',
    allowBlank: true,
    name: 'aseguradora',
    hideLabel: false,
    width: 170,
    disabled: true,
    value:'<s:property value="aseguradora" />',
    frame: true 
});

var producto = new Ext.form.TextField({
    fieldLabel: 'Producto',
    allowBlank: true,
    name: 'producto',
    hideLabel: false,
    width: 270,
    disabled: true,
    value:'<s:property value="producto" />',
    frame: true 
});

var fechasolicitud = new Ext.form.DateField({
    fieldLabel: 'Fecha Solicitud',
    allowBlank: true,
    name: 'fechasolicitud',
    id: 'idfechasolicitud',
    hideLabel: PROCESO_RENOVACION,
    width: 170,
    readOnly: true,
    value:'<s:property value="fechasolicitud" />',
    frame: true,
    allowBlank: false,
    format:'d/m/Y',
    maxLength: '10',
    maxLengthText: 'La fecha debe tener formato dd/MM/yyyy',
    hidden: PROCESO_RENOVACION,
    disabled: true
});

Ext.getCmp('idfechasolicitud').on('change',function(){
    var fechaSol = Ext.getCmp('idfechasolicitud').value;
    
    var conn = new Ext.data.Connection();
    conn.request({
         url: 'flujoendoso/actualizaFechas.action?fecha='+fechaSol+'&fechaActualiza=S',
         method: 'POST',
         successProperty : '@success',
         callback: function (options, success, response){
                     if(Ext.util.JSON.decode(response.responseText).success == false){
                         Ext.Msg.alert('Error', 'No se pudo actualizar la Fecha Solicitud');
                     }
                     if(Ext.util.JSON.decode(response.responseText).errorFechas == true){
                         Ext.Msg.alert('Error', Ext.util.JSON.decode(response.responseText).mensajeErrorFechas);
                     }
                   }//function
    });                                  
});

var fechaefectividadendoso = new Ext.form.DateField({
    fieldLabel: 'Fecha de Alta Endoso',//'Fecha Efectivi. Endoso',
    allowBlank: true,
    name: 'fechaefectividadendoso',
    id: 'idfechaefectividadendoso',
    hideLabel: PROCESO_RENOVACION,
    width: 170,
    readOnly: true,
    value:FECHA_EFECTIVIDAD_ENDOSO,
    frame: true,
    allowBlank: false,
    format:'d/m/Y',
    maxLength: '10',
    maxLengthText: 'La fecha debe tener formato dd/MM/yyyy' ,
    hidden: PROCESO_RENOVACION
});

Ext.getCmp('idfechaefectividadendoso').on('change',function(){
    var fechaEfec = Ext.getCmp('idfechaefectividadendoso').value;
    
    var conn = new Ext.data.Connection();
    conn.request({
         url: 'flujoendoso/actualizaFechas.action?fecha='+fechaEfec+'&fechaActualiza=E',
         method: 'POST',
         successProperty : '@success',
         callback: function (options, success, response){
                     if(Ext.util.JSON.decode(response.responseText).success == false){
                         Ext.Msg.alert('Error', 'No se pudo actualizar la Fecha de Alta Endoso');
                     }
                     if(Ext.util.JSON.decode(response.responseText).errorFechas == true){
                         Ext.Msg.alert('Error', Ext.util.JSON.decode(response.responseText).mensajeErrorFechas);
                     }
                   }//function
    });                                  
});

var inciso = new Ext.form.TextField({
    fieldLabel: 'Inciso',
    allowBlank: true,
    name: 'Inciso',
    hideLabel: false,
    width: 150,
    disabled: true,
    value:'<s:property value="inciso" />',
    frame: true 
});

//******** DATOS GENERALES DE POLIZA ***********
var vigenciaDesde = new Ext.form.TextField({
    fieldLabel: 'Vigencia Desde',
    allowBlank: true,
    name: 'vigenciaDesde',
    width: 150,
    disabled: true,
    frame: true,
    value:'<s:property value="vigenciaDesde" />',
    id: 'vigenciaDesde' 
});

var fechaEfectividad = new Ext.form.TextField({
    fieldLabel: 'Fecha de Alta',
    allowBlank: true,
    name: 'fechaEfectividad',
    width: 150,
    disabled: true,
    frame: true,
    value:'<s:property value="fechaEfectividad" />',
    id: 'fechaEfectividad'
});

/*
var numeroEndoso = new Ext.form.TextField({
    fieldLabel: 'Número Endoso',
    allowBlank: true,
    name: 'numeroEndoso',
    width: 100,
    disabled: true,
    frame: true 
});*/

var vigenciaHasta = new Ext.form.TextField({
    fieldLabel: 'Hasta',
    allowBlank: true,
    name: 'vigenciaHasta',
    width: 150,
    disabled: true,
    frame: true,
    value:'<s:property value="vigenciaHasta" />',
    id: 'vigenciaHasta'
});

var fechaRenovacion = new Ext.form.TextField({
    fieldLabel: 'Fecha de Renovación',
    allowBlank: true,
    name: 'fechaRenovacion',
    width: 150,
    disabled: true,
    value:'<s:property value="fechaRenovacion" />',
    frame: true
});

var moneda = new Ext.form.TextField({
    fieldLabel: 'Moneda',
    allowBlank: true,
    name: 'moneda',
    width: 150,
    disabled: true,
    frame: true,
    value:'<s:property value="moneda" />',
    id: 'moneda'
});

/*
var tipoCoaseguro = new Ext.form.TextField({
    fieldLabel: 'Tipo de Coaseguro',
    allowBlank: true,
    name: 'tipoCoaseguro',
    width: 100,
    disabled: true,
    frame: true 
});

var tipoPoliza = new Ext.form.TextField({
    fieldLabel: 'Tipo de Poliza',
    allowBlank: true,
    name: 'tipoPoliza',
    width: 100,
    disabled: true,
    frame: true 
});*/

var formaPago = new Ext.form.TextField({
    fieldLabel: 'Periocidad',
    allowBlank: true,
    name: 'formaPago',
    width: 150,
    disabled: true,
    frame: true,
    value:'<s:property value="formaPago" />',
    id: 'formaPago'
});

var saved = '';

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

var dataGeneralesPoliza = new Ext.Panel({
    border: false,
    width: 776,
    //labelAlign: 'right', 
    //autoHeight: true,
    //frame: true,
    items:[{
        title :'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Datos Generales de Poliza</span>',
        //bodyStyle:'background: white',
        labelWidth: 100,     
        labelAlign: 'right',        
        layout: 'form',
        width: 774,
        //defaults:{anchor: '55%'},
        collapsible: true,
        frame: true,
        //bodyStyle:'padding:5px 5px 0',
        items:[{
                layout: 'column',
                border: false,
                items: [{
                    columnWidth:.4,
                    layout: 'form',
                    border: false,
                    items: [vigenciaDesde, 
                           fechaEfectividad, 
                           formaPago]
                },{
                    columnWidth:.5,
                    layout: 'form',
                    border: false,
                    labelWidth: 130,
                    items: [vigenciaHasta, 
                            fechaRenovacion, 
                            moneda]
                }/*,{
                    columnWidth:.4,
                    layout: 'form',
                    border: false,
                    labelWidth: 110,
                    items: [tipoPoliza, tipoCoaseguro, formaPago]
                }*/]
        }]
    }]
});//dataGeneralesPoliza

var dataAdicionalesPoliza = new Ext.form.FormPanel({
	id:'dataAdicionalesPoliza',
    border: false,
    width: 776,
    url: 'flujoendoso/guardaDatosAdicionales.action' ,
    //height: 1500,
    //autoHeight: true,
    //frame: true,
    items:[{
        title :'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Datos Adicionales de Poliza</span>',
        //bodyStyle:'background: white',
        buttonAlign: 'center',
        labelWidth: 100,             
        layout: 'form',
        width: 774,
        defaults:{anchor: '55%'},
        //autoHeight: true,
        collapsible: true,
        frame: true,
        labelWidth: 250,
        labelAlign: 'right',
        items:  ElementosExt,
        buttons:[BotonDatosVariables]
    }]
});

/*
var storeDataAdicionales = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
        method: 'POST', 
        url: 'procesoemision/consultaDatosAdicionalesPoliza.action'+'?cdUnieco='+cdUnieco.getValue()+'&cdRamo='+cdRamo.getValue()+'&estado='+estado.getValue()+'&nmPoliza='+nmPoliza.getValue()
    }),
    reader: new Ext.data.JsonReader({
    root: 'datAdicionales'
    //id: 'value'
    },[
       {name: 'dsNombre',   type: 'string', mapping:'nombreComponente'},
       {name: 'dsEtiqueta', type: 'string', mapping:'etiqueta'},
       {name: 'otValor',    type: 'string', mapping:'valor'},
       {name: 'dsValor',    type: 'string', mapping:'descripcion'}
     ]),
    remoteSort: true
});

storeDataAdicionales.on('load', function(){
    for(var index = 0; index < storeDataAdicionales.getCount(); index++){
        var record = storeDataAdicionales.getAt(index);
        
        //alert(record.get('dsNombre').substring(11, record.get('dsNombre').length));
        if(record.get('dsValor') != ""){
            Ext.getCmp(record.get('dsNombre').substring(11, record.get('dsNombre').length)).setValue(record.get('dsValor'));
         }
         else{
            Ext.getCmp(record.get('dsNombre').substring(11, record.get('dsNombre').length)).setValue(record.get('otValor'));
         }
    }//for
});

storeDataAdicionales.load();*/
/*************
var store = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
        method: 'POST', 
        url: '../procesoemision/procesoemision/consultaDetallePoliza.action'+'?cdUnieco='+cdUnieco.getValue()+'&cdRamo='+cdRamo.getValue()+'&estado='+estado.getValue()+'&nmPoliza='+nmPoliza.getValue()
    }),
    reader: new Ext.data.JsonReader({
    root: 'polizaDet'
    //id: 'value'
    },[
       {name: 'vigenciaHasta',      type: 'string', mapping:'vigenciaHasta'},
       {name: 'vigenciaDesde',      type: 'string', mapping:'vigenciaDesde'},
       {name: 'fechaEfectividad',   type: 'string', mapping:'fechaEfectividad'},
       {name: 'moneda',             type: 'string', mapping:'moneda'},
       {name: 'formaPago',          type: 'string', mapping:'periocidad'},
     ]),
    remoteSort: true
});
//store.setDefaultSort('value', 'desc');

store.on('load', function(){
    var record = store.getAt(0);
    //editForm.loadRecord(record);
    //alert(record.get('vigenciaDesde'));
    Ext.getCmp('vigenciaDesde').setValue(record.get('vigenciaDesde'));
    Ext.getCmp('vigenciaHasta').setValue(record.get('vigenciaHasta'));
    Ext.getCmp('fechaEfectividad').setValue(record.get('fechaEfectividad'));
    Ext.getCmp('moneda').setValue(record.get('moneda'));
    Ext.getCmp('formaPago').setValue(record.get('formaPago'));
});

store.load();
***********/
//******** GRID OBJETO ASEGURABLE *************
var storeGridObjAsegurable = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
        method: 'POST', 
        url: '../procesoemision/procesoemision/consultaObjetoAsegurable.action'+'?cdUnieco='+cdUnieco.getValue()+'&cdRamo='+cdRamo.getValue()+'&estado='+estado.getValue()+'&nmPoliza='+nmPoliza.getValue()+'&nmSituac=1'
    }),
    reader: new Ext.data.JsonReader({
    root: 'objAsegurable'
    },[
       {name: 'cdInciso', type: 'string', mapping:'inciso'},
       {name: 'nmsituac', type: 'string', mapping:'nmsituac'},
       {name: 'status', type: 'string', mapping:'status'},
       {name: 'dsDescripcion', type: 'string', mapping:'descripcion'}
     ]),
    remoteSort: true,
    listeners : {
    	load : function() {
    		//Ext.MessageBox.alert('Aviso', 'Numero de registros: ' + storeGridObjAsegurable.getTotalCount());
    		if(this.getTotalCount() <= 1){
            	Ext.getCmp('btn-eliminar-inciso').disable();
			}else{
	           	Ext.getCmp('btn-eliminar-inciso').enable();
			}
    	}
    }
});

storeGridObjAsegurable.load();

var cm = new Ext.grid.ColumnModel([
    new Ext.grid.RowNumberer(),
        {header: "Riesgo",          dataIndex:'cdInciso',         width:20,   sortable:true},
        {header: "Descripción",     dataIndex:'dsDescripcion',    width:100,  sortable:true}
]);

function getSelectedRecord(grid){
	var m = grid.getSelections();
    if (m.length == 1 ) {
		return m[0];
	}
}

var nmSituacion;
var gridObjAsegurable = new Ext.grid.GridPanel({
        store: storeGridObjAsegurable,
        border: true,
        //collapsible: true, 
        buttonAlign: 'center',
        baseCls:' background:white ',
        cm: cm,
        buttons:[{
                    id:'btn-eliminar-inciso',
                    text:'Eliminar',
                    tooltip:'Elimina el inciso seleccionado'
                },{
                    id:'detalle',
                    text:'Detalle',
                    tooltip:'Detalle'
                }],
        width: 764,
        height: 200,        
        sm: new Ext.grid.RowSelectionModel({
        	singleSelect: true,
        	listeners: {       
				rowselect: function(sm, row, rec) {
					Ext.getCmp('btn-eliminar-inciso').on('click',function(){
						nmSituacion  = rec.get('nmsituac');
						eliminarInciso(storeGridObjAsegurable, nmSituacion);	                                	 
				 	});
					
					Ext.getCmp('detalle').on('click',function(){
					
					var proceso="";
					if(PROCESO_RENOVACION){
						proceso=RENOVACION;
					}else{
    					proceso=ENDOSO;
   					}
   					
   					
						//Ext.MessageBox.alert('Datos', "Inciso: " + rec.get('cdInciso') + " Descripcion: " + rec.get('dsDescripcion') );
						document.forms[0].action = '${ctx}/flujoendoso/datosIncisosEndoso.action?nmSituac='+rec.get('nmsituac')+
						'&dsDescripcion='+rec.get('dsDescripcion')+'&cdInciso='+rec.get('cdInciso')+
						'&status='+rec.get('status')+'&aseguradora='+aseguradora.getValue()+'&poliza='+poliza.getValue()+'&producto='+producto.getValue()+'&proc='+proceso+'&fechaefectividadendoso='+FECHA_EFECTIVIDAD_ENDOSO; 
                		document.forms[0].submit();
                		document.forms[0].method = 'POST';
					});
				}
			}
        }),
        viewConfig: {autoFill: true,forceFit:true},                
        bbar: new Ext.PagingToolbar({
            pageSize:20,
            store: storeGridObjAsegurable,                                               
            displayInfo: true,
            displayMsg: 'Registros mostrados {0} - {1} de {2}',
            emptyMsg: 'No hay registros para mostrar',
    		beforePageText: 'P&aacute;gina',
    		afterPageText: 'de {0}'                    
            })                                                                                               
        });

muestraMsgEligeRecordDeGrid('btn-eliminar-inciso', gridObjAsegurable);
muestraMsgEligeRecordDeGrid('detalle', gridObjAsegurable);

eliminarInciso = function(storeGridObjAsegurable, nmsituac) {
	Ext.MessageBox.buttonText.yes = 'Si';
	Ext.MessageBox.confirm('Mensaje','Esta seguro de querer eliminar este elemento?', function(btn) {
		if(btn == 'yes'){
			//parametros que seran enviados al action
			//El nombre debe ser el mismo que el atributo del action si es que se quiere mapear el valor
			var params="";
			params = "nmSituac=" +nmsituac;
			//params +="&&cdRamo="+cdRamo;
			//params +="&&estado="+estado;
			var conn = new Ext.data.Connection();
			conn.request ({
				url: _ACTION_ELIMINA_INCISO,
				method: 'POST',
				successProperty : '@success',
				params : params,
				callback: function (options, success, response) {  
					if(Ext.util.JSON.decode(response.responseText).saved == 'true'){
						Ext.MessageBox.alert('Estado', 'Elemento eliminado');
						storeGridObjAsegurable.load();
					}else {
						Ext.MessageBox.alert('Estado', 'Error al eliminar');
					}
				}
			});
		}
    });
};

        
var panelObjAsegurable = new Ext.Panel({
    border: false,
    width: 776,
    //autoHeight: true,
    //frame: true,
    items:[{
        title :'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Riesgos</span>',
        //bodyStyle:'background: white',
        labelWidth: 100,             
        layout: 'form',
        //autoWidth: true,
        width: 774,
        //defaults:{anchor: '55%'},
        collapsible: true,
        frame: true,
        //bodyStyle:'padding:5px 5px 0',
        items:[gridObjAsegurable]
   }]
});
        
//******** DATOS ROL ***********

/*var window = new Ext.Window({
    title: 'Datos Rol',
    width: 600,
    //height:340,
    autoHeight: true,
    minWidth: 300,
    minHeight: 200,
    layout: 'fit',
    plain:true,
    modal:true,
    bodyStyle:'padding:5px;',
    buttonAlign:'center',
    items: [panelRol],
    buttons:[{
        text: 'Regresar',
        tooltip: 'Cierra la ventana',
        handler: function(){
            window.hide();
        }//handler
    }]
});*/

var codeExt='';
var comboRolDato;
var comboRolValor;
var comboPersonaDato;
var comboPersonaValor;
var itemsVariables = '';
//DETALLE DE FUNCION EN LA POLIZA AGREGAR
function datosFnPolizaAgregar(cdRamo, cdTipsit, nmSituac){
    
    //codeExt = '';
    
    var storeRol = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
            url: 'flujoendoso/getComboRol.action?cdRamo='+cdRamo+'&cdTipsit='+cdTipsit+'&nmSituac='+nmSituac+'&cdNivel=0'
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
        //hiddenName:'parameters.idrol',
        value:''
    });
    var idperson = new Ext.form.Hidden({
        id:'idperson',
        name:'parameters.cdperson',
        //hiddenName:'parameters.idperson',
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

                var url = 'flujoendoso/getPantallaRolPoliza.action?cdRol='+comboRolValor+'&cdTipsit='+cdTipsit+'&accion=I'+'&nmSituac='+nmSituac+'&cdNivel=0'
                var conn = new Ext.data.Connection();
                conn.request({
                     url: url,
                     method: 'POST',
                     successProperty : '@success',
                     callback: function (options, success, response){
                                 if(Ext.util.JSON.decode(response.responseText).success == false){
                                     Ext.Msg.alert('Error', 'Ocurrio un error en la transaccion');
                                 }else{
                                     codeExt = '';
                                     codeExt = Ext.util.JSON.decode(response.responseText).dext;
                                     if(codeExt == '[]' || codeExt == ''){
                                        var sinDatos = new Ext.form.Hidden({
                                            name:'noData',
                                            value:''
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
                                        datosFnPolizaAgregar(cdRamo, cdTipsit, nmSituac);
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
            url:'flujoendoso/getPolizaRol.action?cdTipsit='+cdTipsit+'&nmSituac='+nmSituac,
            method:'post',
            border: false,
            width: 540,
            //autoHeight: true,
            autoScroll:true,
            height:340,
            items:[{
                //title :'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Rol de la P&oacute;liza</span>',
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
                //title :'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Rol de la P&oacute;liza</span>',
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
        title: 'Agregar rol en póliza',
        width: 550,
        height:350,
        //autoHeight: true,
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
function datosFnPolizaEditar(cdRol, dsRol, cdPerson, dsPerson, cdTipsit, nmSituac){

    var storeRol = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
            url: 'flujoendoso/getComboRol.action?cdTipsit='+cdTipsit+'&nmSituac='+nmSituac+'&cdNivel=0'
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
                            
    //alert(cdRol+'-'+dsRol+'-'+cdPerson+'-'+dsPerson+'-'+cdRamo+'-'+cdTipsit);
    var url = 'flujoendoso/getPantallaRolPoliza.action?cdUnieco='+cdUnieco.getValue()+'&cdRol='+cdRol+'&cdPerson='+cdPerson+'&accion=U'+'&cdTipsit='+cdTipsit+'&nmSituac='+nmSituac+'&cdNivel=0';
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
                         var itemsVariables;
                         
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
                                //disabled:true,
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
                                url:'flujoendoso/getPolizaRol.action?cdTipsit='+cdTipsit+'&nmSituac='+nmSituac+'&cdPerson='+cdPerson,
                                method:'post',
                                border: false,
                                width: 540,
                                //autoHeight: true,
                                autoScroll:true,
                                height:340,
                                items:[{
                                    //title :'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Rol de la P&oacute;liza</span>',
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
                                    //title :'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Rol de la P&oacute;liza</span>',
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
                                //autoHeight: true,
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
function datosFnPolizaBorrar(cdRol, cdPerson, swObliga, cdTipsit, nmSituac){


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
    var msgBorrar = new Ext.form.TextField({
        fieldLabel:'¿Esta seguro que desea eliminar la opción?',
        labelSeparator:'',
        anchor:'90%',
        hidden:true
    });
    var Url = 'flujoendoso/getPolizaRol.action?cdTipsit='+cdTipsit+'&nmSituac='+nmSituac; 
    //alert(Url);
    var panelRol = new Ext.form.FormPanel({
        url: Url,
        method:'post',
        border: false,
        width: 400,
        //autoHeight: true,
        //autoScroll:true,
        height:160,
        items:[{
            //title :'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Rol de la P&oacute;liza</span>',
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
                            accion,
                            idrol,
                            idperson,
                            swobliga,
                            msgBorrar
                        ]
                        }]
            }]
    }]
    });

     var window = new Ext.Window({
        title: 'Eliminar rol en p&oacute;liza',
        width: 420,
        height:130,
        //autoHeight: true,
        //minWidth: 300,
        //minHeight: 200,
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

//Ya no se utilizara este metodo
/*
function datosRol(cdRol, dsRol, dsNombre, nmSituac, pVentana){
    var conn = new Ext.data.Connection();
    conn.request({
         url: 'flujoendoso/datosRol.action'+'?cdUnieco=' + cdUnieco.getValue() + '&cdRamo=' + cdRamo.getValue() + '&estado=' + estado.getValue() + '&nmPoliza=' + nmPoliza.getValue() + '&cdRol=' + cdRol + '&dsRol=' + dsRol + '&dsNombre=' + dsNombre + '&nmSituac=' + nmSituac,
         method: 'POST',
         successProperty : '@success',
         //params : [idHoja],
         callback: function (options, success, response){
                     if (Ext.util.JSON.decode(response.responseText).success == false) {
                         Ext.Msg.alert('Error', 'Ocurrio un error en la transaccion');
                     }else{
                        
                         var codeExt = Ext.util.JSON.decode(response.responseText).dext;
                       
                        if(codeExt != ''){
                              var panelDinamico = new Ext.Panel({
                                    border: false,
                                    width: 600,
                                    autoHeight: true,
                                    items: [{"allowBlank":false,"disabled":false,"fieldLabel":"Clave Única del Registro de Población (CURP)","hiddeParent":true,"hidden":false,"id":"parameters.B3B_C1_1","labelSeparator":"","maxLengthText":18,"minLengthText":1,"name":"parameters.B3B_C1_1","type":"","value":"","width":200,"xtype":"textfield"}, {"allowBlank":false,"disabled":false,"fieldLabel":"Clave de afiliación al IMSS / ISSSTE (en caso de que aplique).","hiddeParent":true,"hidden":false,"id":"parameters.B3B_C2_1","labelSeparator":"","maxLengthText":18,"minLengthText":1,"name":"parameters.B3B_C2_1","type":"","value":"","width":200,"xtype":"textfield"}, {"allowBlank":true,"displayField":"","editable":false,"emptyText":"Seleccione...","fieldLabel":"","forceSelection":false,"hiddenName":"","id":"parameters.B3B_C3_1","labelSeparator":"","listWidth":200,"mode":"","name":"parameters.B3B_C3_1","selectOnFocus":false,"triggerAction":"all","typeAhead":false,"valueField":"","width":200,"xtype":"combo"}, {"allowBlank":true,"displayField":"","editable":false,"emptyText":"Seleccione...","fieldLabel":"","forceSelection":false,"hiddenName":"","id":"parameters.B3B_C4_1","labelSeparator":"","listWidth":200,"mode":"","name":"parameters.B3B_C4_1","selectOnFocus":false,"triggerAction":"all","typeAhead":false,"valueField":"","width":200,"xtype":"combo"}, {"allowBlank":true,"displayField":"","editable":false,"emptyText":"Seleccione...","fieldLabel":"","forceSelection":false,"hiddenName":"","id":"parameters.B3B_C5_1","labelSeparator":"","listWidth":200,"mode":"","name":"parameters.B3B_C5_1","selectOnFocus":false,"triggerAction":"all","typeAhead":false,"valueField":"","width":200,"xtype":"combo"}, {"allowBlank":true,"displayField":"","editable":false,"emptyText":"Seleccione...","fieldLabel":"","forceSelection":false,"hiddenName":"","id":"parameters.B3B_C6_1","labelSeparator":"","listWidth":200,"mode":"","name":"parameters.B3B_C6_1","selectOnFocus":false,"triggerAction":"all","typeAhead":false,"valueField":"","width":200,"xtype":"combo"}, {"allowBlank":true,"displayField":"","editable":false,"emptyText":"Seleccione...","fieldLabel":"","forceSelection":false,"hiddenName":"","id":"parameters.B3B_C7_1","labelSeparator":"","listWidth":200,"mode":"","name":"parameters.B3B_C7_1","selectOnFocus":false,"triggerAction":"all","typeAhead":false,"valueField":"","width":200,"xtype":"combo"}, {"allowBlank":true,"displayField":"","editable":false,"emptyText":"Seleccione...","fieldLabel":"","forceSelection":false,"hiddenName":"","id":"parameters.B3B_C8_1","labelSeparator":"","listWidth":200,"mode":"","name":"parameters.B3B_C8_1","selectOnFocus":false,"triggerAction":"all","typeAhead":false,"valueField":"","width":200,"xtype":"combo"}, {"allowBlank":false,"disabled":false,"fieldLabel":"Tiempo de radicar en la vivienda actual en meses.","hiddeParent":true,"hidden":false,"id":"parameters.B3B_C9_1","labelSeparator":"","maxLengthText":3,"minLengthText":1,"name":"parameters.B3B_C9_1","type":"","value":"","width":200,"xtype":"textfield"}, {"allowBlank":true,"displayField":"","editable":false,"emptyText":"Seleccione...","fieldLabel":"","forceSelection":false,"hiddenName":"","id":"parameters.B3B_C10_1","labelSeparator":"","listWidth":200,"mode":"","name":"parameters.B3B_C10_1","selectOnFocus":false,"triggerAction":"all","typeAhead":false,"valueField":"","width":200,"xtype":"combo"}, {"allowBlank":true,"displayField":"","editable":false,"emptyText":"Seleccione...","fieldLabel":"","forceSelection":false,"hiddenName":"","id":"parameters.B3B_C11_1","labelSeparator":"","listWidth":200,"mode":"","name":"parameters.B3B_C11_1","selectOnFocus":false,"triggerAction":"all","typeAhead":false,"valueField":"","width":200,"xtype":"combo"}, {"allowBlank":true,"displayField":"","editable":false,"emptyText":"Seleccione...","fieldLabel":"","forceSelection":false,"hiddenName":"","id":"parameters.B3B_C12_1","labelSeparator":"","listWidth":200,"mode":"","name":"parameters.B3B_C12_1","selectOnFocus":false,"triggerAction":"all","typeAhead":false,"valueField":"","width":200,"xtype":"combo"}, {"allowBlank":true,"displayField":"","editable":false,"emptyText":"Seleccione...","fieldLabel":"","forceSelection":false,"hiddenName":"","id":"parameters.B3B_C13_1","labelSeparator":"","listWidth":200,"mode":"","name":"parameters.B3B_C13_1","selectOnFocus":false,"triggerAction":"all","typeAhead":false,"valueField":"","width":200,"xtype":"combo"}, {"allowBlank":false,"disabled":false,"fieldLabel":"Meses de antigüedad en la actividad actual.","hiddeParent":true,"hidden":false,"id":"parameters.B3B_C14_1","labelSeparator":"","maxLengthText":3,"minLengthText":1,"name":"parameters.B3B_C14_1","type":"","value":"","width":200,"xtype":"textfield"}, {"allowBlank":false,"disabled":false,"fieldLabel":"Meses de antigüedad en la actividad anterior, en caso de tener menos de un año en el empleo actual","hiddeParent":true,"hidden":false,"id":"parameters.B3B_C15_1","labelSeparator":"","maxLengthText":3,"minLengthText":1,"name":"parameters.B3B_C15_1","type":"","value":"","width":200,"xtype":"textfield"}, {"allowBlank":false,"disabled":false,"fieldLabel":"Capacidad de pago en pesos del acreditado y, en su caso, del co-acreditado determinada por el intermediario financiero","hiddeParent":true,"hidden":false,"id":"parameters.B3B_C16_1","labelSeparator":"","maxLengthText":0,"minLengthText":0,"name":"parameters.B3B_C16_1","type":"","value":"","width":200,"xtype":"textfield"}, {"allowBlank":false,"disabled":false,"fieldLabel":"Número de dependientes económicos.","hiddeParent":true,"hidden":false,"id":"parameters.B3B_C17_1","labelSeparator":"","maxLengthText":2,"minLengthText":1,"name":"parameters.B3B_C17_1","type":"","value":"","width":200,"xtype":"textfield"}, {"allowBlank":false,"disabled":false,"fieldLabel":"Saldo en cuentas bancarias de inversión o ahorro (cheques inversión, ahorro, maestra) del acreditado / coacreditado","hiddeParent":true,"hidden":false,"id":"parameters.B3B_C18_1","labelSeparator":"","maxLengthText":0,"minLengthText":0,"name":"parameters.B3B_C18_1","type":"","value":"","width":200,"xtype":"textfield"}, {"allowBlank":false,"disabled":false,"fieldLabel":"Saldo en cuentas de préstamos (tarjeta de crédito, crédito hipotecario, automóvil) del acreditado / coacreditado","hiddeParent":true,"hidden":false,"id":"parameters.B3B_C19_1","labelSeparator":"","maxLengthText":0,"minLengthText":0,"name":"parameters.B3B_C19_1","type":"","value":"","width":200,"xtype":"textfield"}, {"allowBlank":false,"disabled":false,"fieldLabel":"Total de egresos mensuales del acreditado y, en su caso del co-acreditado.","hiddeParent":true,"hidden":false,"id":"parameters.B3B_C20_1","labelSeparator":"","maxLengthText":0,"minLengthText":0,"name":"parameters.B3B_C20_1","type":"","value":"","width":200,"xtype":"textfield"}, {"allowBlank":false,"disabled":false,"fieldLabel":"Valor estimado del (los) automóvil (es) que posee en pesos el acreditado y, en su caso el co-acreditado.","hiddeParent":true,"hidden":false,"id":"parameters.B3B_C21_1","labelSeparator":"","maxLengthText":0,"minLengthText":0,"name":"parameters.B3B_C21_1","type":"","value":"","width":200,"xtype":"textfield"}, {"allowBlank":true,"displayField":"","editable":false,"emptyText":"Seleccione...","fieldLabel":"","forceSelection":false,"hiddenName":"","id":"parameters.B3B_C22_1","labelSeparator":"","listWidth":200,"mode":"","name":"parameters.B3B_C22_1","selectOnFocus":false,"triggerAction":"all","typeAhead":false,"valueField":"","width":200,"xtype":"combo"}, {"allowBlank":true,"displayField":"","editable":false,"emptyText":"Seleccione...","fieldLabel":"","forceSelection":false,"hiddenName":"","id":"parameters.B3B_C23_1","labelSeparator":"","listWidth":200,"mode":"","name":"parameters.B3B_C23_1","selectOnFocus":false,"triggerAction":"all","typeAhead":false,"valueField":"","width":200,"xtype":"combo"}, {"allowBlank":true,"displayField":"","editable":false,"emptyText":"Seleccione...","fieldLabel":"","forceSelection":false,"hiddenName":"","id":"parameters.B3B_C24_1","labelSeparator":"","listWidth":200,"mode":"","name":"parameters.B3B_C24_1","selectOnFocus":false,"triggerAction":"all","typeAhead":false,"valueField":"","width":200,"xtype":"combo"}, {"allowBlank":false,"disabled":false,"fieldLabel":"Número de folio de la consulta a una sociedad de información crediticia.","hiddeParent":true,"hidden":false,"id":"parameters.B3B_C25_1","labelSeparator":"","maxLengthText":15,"minLengthText":1,"name":"parameters.B3B_C25_1","type":"","value":"","width":200,"xtype":"textfield"}, {"allowBlank":true,"displayField":"","editable":false,"emptyText":"Seleccione...","fieldLabel":"","forceSelection":false,"hiddenName":"","id":"parameters.B3B_C26_1","labelSeparator":"","listWidth":200,"mode":"","name":"parameters.B3B_C26_1","selectOnFocus":false,"triggerAction":"all","typeAhead":false,"valueField":"","width":200,"xtype":"combo"}, {"allowBlank":false,"disabled":false,"fieldLabel":"Monto del adeudo vencido correspondiente al MOP mayor.","hiddeParent":true,"hidden":false,"id":"parameters.B3B_C27_1","labelSeparator":"","maxLengthText":0,"minLengthText":0,"name":"parameters.B3B_C27_1","type":"","value":"","width":200,"xtype":"textfield"}, {"allowBlank":false,"disabled":false,"fieldLabel":"Código postal donde habita actualmente el acreditado.","hiddeParent":true,"hidden":false,"id":"parameters.B3B_C28_1","labelSeparator":"","maxLengthText":5,"minLengthText":1,"name":"parameters.B3B_C28_1","type":"","value":"","width":200,"xtype":"textfield"}, {"allowBlank":true,"displayField":"","editable":false,"emptyText":"Seleccione...","fieldLabel":"","forceSelection":false,"hiddenName":"","id":"parameters.B3B_C29_1","labelSeparator":"","listWidth":200,"mode":"","name":"parameters.B3B_C29_1","selectOnFocus":false,"triggerAction":"all","typeAhead":false,"valueField":"","width":200,"xtype":"combo"}, {"allowBlank":false,"disabled":false,"fieldLabel":"Ingresos mensuales brutos del co-acreditado (usar valor 0 si no existe co-acreditado).","hiddeParent":true,"hidden":false,"id":"parameters.B3B_C30_1","labelSeparator":"","maxLengthText":0,"minLengthText":0,"name":"parameters.B3B_C30_1","type":"","value":"","width":200,"xtype":"textfield"}, {"allowBlank":true,"displayField":"","editable":false,"emptyText":"Seleccione...","fieldLabel":"","forceSelection":false,"hiddenName":"","id":"parameters.B3B_C31_1","labelSeparator":"","listWidth":200,"mode":"","name":"parameters.B3B_C31_1","selectOnFocus":false,"triggerAction":"all","typeAhead":false,"valueField":"","width":200,"xtype":"combo"}, {"allowBlank":false,"disabled":false,"fieldLabel":"Clave Única de Vivienda.","hiddeParent":true,"hidden":false,"id":"parameters.B3B_C32_1","labelSeparator":"","maxLengthText":16,"minLengthText":1,"name":"parameters.B3B_C32_1","type":"","value":"","width":200,"xtype":"textfield"}, {"allowBlank":false,"disabled":false,"fieldLabel":"Monto de la subcuenta INFONAVIT.","hiddeParent":true,"hidden":false,"id":"parameters.B3B_C33_1","labelSeparator":"","maxLengthText":0,"minLengthText":0,"name":"parameters.B3B_C33_1","type":"","value":"","width":200,"xtype":"textfield"}, {"allowBlank":true,"displayField":"","editable":false,"emptyText":"Seleccione...","fieldLabel":"","forceSelection":false,"hiddenName":"","id":"parameters.B3B_C34_1","labelSeparator":"","listWidth":200,"mode":"","name":"parameters.B3B_C34_1","selectOnFocus":false,"triggerAction":"all","typeAhead":false,"valueField":"","width":200,"xtype":"combo"}, {"allowBlank":false,"disabled":false,"fieldLabel":"Porcentaje de participación en ingresos de la fuente de mayor aportación del acreditado","hiddeParent":true,"hidden":false,"id":"parameters.B3B_C35_1","labelSeparator":"","maxLengthText":0,"minLengthText":0,"name":"parameters.B3B_C35_1","type":"","value":"","width":200,"xtype":"textfield"}, {"allowBlank":true,"displayField":"","editable":false,"emptyText":"Seleccione...","fieldLabel":"","forceSelection":false,"hiddenName":"","id":"parameters.B3B_C36_1","labelSeparator":"","listWidth":200,"mode":"","name":"parameters.B3B_C36_1","selectOnFocus":false,"triggerAction":"all","typeAhead":false,"valueField":"","width":200,"xtype":"combo"}, {"allowBlank":false,"disabled":false,"fieldLabel":"Porcentaje de participación en ingresos de la segunda fuente de mayor aportación del acreditado","hiddeParent":true,"hidden":false,"id":"parameters.B3B_C37_1","labelSeparator":"","maxLengthText":0,"minLengthText":0,"name":"parameters.B3B_C37_1","type":"","value":"","width":200,"xtype":"textfield"}, {"allowBlank":false,"disabled":false,"fieldLabel":"Porcentaje de participación en ingresos de la fuente de mayor aportación del co-acreditado","hiddeParent":true,"hidden":false,"id":"parameters.B3B_C38_1","labelSeparator":"","maxLengthText":0,"minLengthText":0,"name":"parameters.B3B_C38_1","type":"","value":"","width":200,"xtype":"textfield"}, {"allowBlank":true,"displayField":"","editable":false,"emptyText":"Seleccione...","fieldLabel":"","forceSelection":false,"hiddenName":"","id":"parameters.B3B_C39_1","labelSeparator":"","listWidth":200,"mode":"","name":"parameters.B3B_C39_1","selectOnFocus":false,"triggerAction":"all","typeAhead":false,"valueField":"","width":200,"xtype":"combo"}, {"allowBlank":false,"disabled":false,"fieldLabel":"Porcentaje de participación en ingresos de la segunda fuente de mayor aportación del co-acreditado","hiddeParent":true,"hidden":false,"id":"parameters.B3B_C40_1","labelSeparator":"","maxLengthText":0,"minLengthText":0,"name":"parameters.B3B_C40_1","type":"","value":"","width":200,"xtype":"textfield"}, {"allowBlank":false,"disabled":false,"fieldLabel":"Porcentaje en puntos porcentuales de la razón deuda-ingreso.","hiddeParent":true,"hidden":false,"id":"parameters.B3B_C41_1","labelSeparator":"","maxLengthText":0,"minLengthText":0,"name":"parameters.B3B_C41_1","type":"","value":"","width":200,"xtype":"textfield"}, {"allowBlank":true,"displayField":"","editable":false,"emptyText":"Seleccione...","fieldLabel":"","forceSelection":false,"hiddenName":"","id":"parameters.B3B_C42_1","labelSeparator":"","listWidth":200,"mode":"","name":"parameters.B3B_C42_1","selectOnFocus":false,"triggerAction":"all","typeAhead":false,"valueField":"","width":200,"xtype":"combo"}, {"allowBlank":false,"disabled":false,"fieldLabel":"Porcentaje de coberturas por incumplimiento del acreditado solicitado a SHF, expresado en puntos porcentuales.","hiddeParent":true,"hidden":false,"id":"parameters.B3B_C43_1","labelSeparator":"","maxLengthText":0,"minLengthText":0,"name":"parameters.B3B_C43_1","type":"","value":"","width":200,"xtype":"textfield"}, {"allowBlank":false,"disabled":false,"fieldLabel":"Tasa de la contraprestación por las cobeturas por incumplimiento del acreditado otorgada por SHF","hiddeParent":true,"hidden":false,"id":"parameters.B3B_C44_1","labelSeparator":"","maxLengthText":0,"minLengthText":0,"name":"parameters.B3B_C44_1","type":"","value":"","width":200,"xtype":"textfield"}, {"allowBlank":true,"displayField":"","editable":false,"emptyText":"Seleccione...","fieldLabel":"","forceSelection":false,"hiddenName":"","id":"parameters.B3B_C45_1","labelSeparator":"","listWidth":200,"mode":"","name":"parameters.B3B_C45_1","selectOnFocus":false,"triggerAction":"all","typeAhead":false,"valueField":"","width":200,"xtype":"combo"}, {"allowBlank":false,"disabled":false,"fieldLabel":"Número de nomina","hiddeParent":true,"hidden":false,"id":"parameters.B3B_C46_1","labelSeparator":"","maxLengthText":60,"minLengthText":1,"name":"parameters.B3B_C46_1","type":"","value":"","width":200,"xtype":"textfield"}, {"allowBlank":false,"disabled":false,"fieldLabel":"PRUEBA","hiddeParent":true,"hidden":false,"id":"parameters.B3B_C47_1","labelSeparator":"","maxLengthText":1,"minLengthText":1,"name":"parameters.B3B_C47_1","type":"","value":"","width":200,"xtype":"textfield"}, {"allowBlank":false,"disabled":false,"fieldLabel":"aaaaaaaaaaaa","hiddeParent":true,"hidden":false,"id":"parameters.B3B_C48_1","labelSeparator":"","maxLengthText":0,"minLengthText":0,"name":"parameters.B3B_C48_1","type":"","value":"","width":200,"xtype":"textfield"}, {"allowBlank":false,"disabled":false,"fieldLabel":"algo:","hiddeParent":true,"hidden":false,"id":"parameters.B3B_C49_1","labelSeparator":"","maxLengthText":1,"minLengthText":1,"name":"parameters.B3B_C49_1","type":"","value":"","width":200,"xtype":"textfield"}, {"allowBlank":false,"disabled":false,"fieldLabel":"tttt","hiddeParent":true,"hidden":false,"id":"parameters.B3B_C50_1","labelSeparator":"","maxLengthText":0,"minLengthText":0,"name":"parameters.B3B_C50_1","type":"","value":"","width":200,"xtype":"textfield"}]
                              });
                             alert('.:XXXXX:.');
                             var txRol = new Ext.form.TextField({
                                    fieldLabel: 'Rol',
                                    allowBlank: true,
                                    name: 'txRol',
                                    width: 150,
                                    disabled: true,
                                    frame: true 
                             });
                                
                             var txPersona = new Ext.form.TextField({
                                    fieldLabel: 'Persona',
                                    allowBlank: true,
                                    name: 'txPersona',
                                    width: 150,
                                    disabled: true,
                                    frame: true 
                              });
                             
                             var panelRol = new Ext.form.FormPanel({
                                    border: false,
                                    width: 600,
                                    autoHeight: true,
                                    items:[{
                                        //title :'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Rol de la Poliza</span>',
                                        labelWidth: 45, 
                                        labelAlign: 'right',         
                                        layout: 'column',
                                        frame: true,
                                        items: [{
                                            columnWidth:.5,
                                            layout: 'form',
                                            border: false,
                                            items: [txRol]
                                        },{
                                            columnWidth:.5,
                                            layout: 'form',
                                            border: false,
                                            items: [txPersona]
                                        }]
                                   }]
                             });
                             
                             var window = new Ext.Window({
                                title: 'Datos Rol',
                                width: 500,
                                //height:340,
                                autoHeight: true,
                                minWidth: 300,
                                minHeight: 200,
                                layout: 'fit',
                                plain:true,
                                modal:true,
                                bodyStyle:'padding:5px;',
                                buttonAlign:'center',
                                items: [panelRol, panelDinamico],
                                buttons:[{
                                    text: 'Regresar',
                                    tooltip: 'Cierra la ventana',
                                    handler: function(){
                  	                  if(_VOLVER3){
            								window.location=_ACTION_IR_POLIZAS_RENOVADAS;	
            							}else{
                                        window.close();
                                    }//handler
                                }}]
                            });
                            
                            window.show();
                         }//if codeExt
                         else{
                            Ext.Msg.alert('Mensaje', 'No se encontraron datos');
                         }
                      }//else
                   }//function
    });
}
*/

//******** GRID ROL DE LA POLIZA *************
var storeGridFnPoliza = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
        method: 'POST', 
        url: '../procesoemision/procesoemision/consultaFuncionPoliza.action'+'?cdUnieco='+cdUnieco.getValue()+'&cdRamo='+cdRamo.getValue()+'&estado='+estado.getValue()+'&nmPoliza='+nmPoliza.getValue()+'&nmSituac=0'
    }),
    reader: new Ext.data.JsonReader({
    root: 'fnPoliza'
    },[
       {name: 'dsFuncion',  type: 'string', mapping:'rol'},
       {name: 'dsNombre',   type: 'string', mapping:'nombre'},
       {name: 'cdRol',      type: 'string', mapping:'cdRol'},
       {name: 'nmSituac',   type: 'string', mapping:'nmSituac'},
       {name: 'cdPerson',   type: 'string', mapping:'cdPerson'},
       {name: 'nmaximo',    type: 'string', mapping:'nmaximo'},
       {name: 'swDomici',   type: 'string', mapping:'swDomici'},
       {name: 'swObliga',   type: 'string', mapping:'swObliga'}
     ]),
    remoteSort: true
});

storeGridFnPoliza.load();

var cm = new Ext.grid.ColumnModel([
    new Ext.grid.RowNumberer(),
        {header: "Rol",    dataIndex:'dsFuncion',   width:150,  sortable:true},
        {header: "Nombre",     dataIndex:'dsNombre',    width:150,  sortable:true},
        {header: "cdRol",      dataIndex:'cdRol',       width:150,  sortable:true, hidden: true},
        {header: "nmSituac",   dataIndex:'nmSituac',    width:150,  sortable:true, hidden: true},
        {header: "cdPerson",   dataIndex:'cdPerson',    width:150,  sortable:true, hidden: true},
        {header: "nmaximo",    dataIndex:'nmaximo',     width:150,  sortable:true, hidden: true},
        {header: "swDomici",   dataIndex:'swDomici',    width:150,  sortable:true, hidden: true},
        {header: "swObliga",   dataIndex:'swObliga',    width:150,  sortable:true, hidden: true}
]);

var cdRol = "";
var dsRol = "";
var cdPerson = "";
var dsPerson = ""; 
var nmSituac = "";
var cdTipsit = "";
var nmaximo = ""; 
var swDomici = "";
var swObliga = "";

var gridFnPoliza = new Ext.grid.GridPanel({
        store: storeGridFnPoliza,
        border: true,
        //collapsible: true, 
        buttonAlign: 'center',
        baseCls:' background:white ',
        cm: cm,
        buttons:[{
                    id:'fnPolizaAgregar',
                    text:'Agregar',
                    tooltip:'Agrega un nuevo registro'
                },{
                    id:'fnPolizaEditar',
                    text:'Editar',
                    tooltip:'Edita el registro seleccionado'                           
                },{
                    id:'fnPolizaBorrar',
                    text:'Eliminar',
                    tooltip:'Eliminar el registro seleccionado'                
                }],                                                     
        width: 764,
        //autoWidth: true,
        frame: true,
        height: 200,        
        //title:'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Rol en la Poliza</span>',
        
    sm: new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {                            
                    rowselect: function(sm, row, rec){
                        cdRol = rec.get('cdRol');
                        dsRol = rec.get('dsFuncion');
                        cdPerson = rec.get('cdPerson');
                        dsPerson = rec.get('dsNombre');
                        nmSituac = rec.get('nmSituac');
                        nmaximo = rec.get('nmaximo');
                        swDomici = rec.get('swDomici');
                        swObliga = rec.get('swObliga');
                        /*
                        Ext.getCmp('agregar').on('click',function(){
                            //window.location.replace('datosRol.action' + '?cdUnieco=' + cdUnieco.getValue() + '&cdRamo=' + cdRamo.getValue() + '&estado=' + estado.getValue() + '&nmPoliza=' + nmPoliza.getValue() + '&cdRol=' + cdRol + '&dsRol=' + dsRol + '&dsNombre=' + dsNombre + '&nmSituac=' + nmSituac + '&aseguradora=' + aseguradora.getValue() + '&poliza=' + poliza.getValue() + '&producto=' + producto.getValue());
                            alert('Agregar Fn Poliza');
                            datosRol('1', 'xxx', 'xxx', '1', 'agregar');                                                                            
                        });
                        Ext.getCmp('editar').on('click',function(){
                            var nmSituac = rec.get('nmSituac');
                            var cdRol = rec.get('cdRol');
                            var dsNombre = rec.get('dsNombre');
                            var dsRol = rec.get('dsFuncion');
                            var cdPerson = rec.get('cdPerson');
                            //alert('cdRol:: ' + cdRol + ' cdPerson:: ' + cdPerson + ' dsNombre:: ' + dsNombre + ' dsRol:: ' + dsRol);
                            //window.location.replace('datosRol.action' + '?cdUnieco=' + cdUnieco.getValue() + '&cdRamo=' + cdRamo.getValue() + '&estado=' + estado.getValue() + '&nmPoliza=' + nmPoliza.getValue() + '&cdRol=' + cdRol + '&dsRol=' + dsRol + '&dsNombre=' + dsNombre + '&nmSituac=' + nmSituac + '&aseguradora=' + aseguradora.getValue() + '&poliza=' + poliza.getValue() + '&producto=' + producto.getValue());
                            datosRol(cdRol, dsRol, dsNombre, nmSituac, cdPerson, 'agregar');                                                                            
                        });
                        */
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
        cdTipsit = 'NA';
        nmSituac = '0';
        if( !gridFnPoliza.getSelectionModel().hasSelection() ) {
			Ext.Msg.alert(_MSG_AVISO, _MSG_NO_ROW_SELECTED);
		} else {
        	datosFnPolizaEditar(cdRol, dsRol, cdPerson, dsPerson, cdTipsit, nmSituac);
        }
    });
    
    Ext.getCmp('fnPolizaAgregar').on('click',function(){
        cdTipsit = 'NA';
        nmSituac = '0';
        datosFnPolizaAgregar(cdRamo.getValue(), cdTipsit, nmSituac);
    });

    Ext.getCmp('fnPolizaBorrar').on('click',function(){
        cdTipsit = 'NA';
        nmSituac = '0';
        if( !gridFnPoliza.getSelectionModel().hasSelection() ) {
			Ext.Msg.alert(_MSG_AVISO, _MSG_NO_ROW_SELECTED);
		} else {
        	datosFnPolizaBorrar(cdRol, cdPerson, swObliga, cdTipsit, nmSituac);
        }
    });

var panelFnPoliza = new Ext.Panel({
    border: false,
    width: 776,
    //autoHeight: true,
    //frame: true,
    items:[{
        title :'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Rol de la Poliza</span>',
        width:774,
        labelWidth: 100,             
        layout: 'form',
        collapsible: true,
        frame: true,
        //defaults:{anchor:'55%'},
        items:[gridFnPoliza]
   }]
});

//************ AGRUPADORES *************
var wcdUnieco = new Ext.form.Hidden({
    fieldLabel: '',
    allowBlank: true,
    name: 'cdUnieco',
    value: cdUnieco.getValue(),
    hidden: true
});

var wcdRamo = new Ext.form.Hidden({
    fieldLabel: '',
    allowBlank: true,
    name: 'cdRamo',
    value: cdRamo.getValue(),
    hidden: true
});

var wnmPoliza = new Ext.form.Hidden({
    fieldLabel: '',
    allowBlank: true,
    name: 'nmPoliza',
    value: nmPoliza.getValue(),
    hidden: true
});

var westado = new Ext.form.Hidden({
    fieldLabel: '',
    allowBlank: true,
    name: 'estado',
    value: estado.getValue(),
    hidden: true
});
var storeBancos = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
        method: 'POST', 
        url: 'flujoendoso/obtieneBancos.action'
    }),
    reader: new Ext.data.JsonReader({
    root: 'bancos'
    },[
       {name: 'label',  type: 'string', mapping:'label'},
       {name: 'value',  type: 'string', mapping:'value'}
     ]),
    remoteSort: true
});

storeBancos.load();

var storePersonasUsuarios = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
        method: 'POST', 
        url: 'flujoendoso/obtienePersonasUsuarios.action'
    }),
    reader: new Ext.data.JsonReader({
    root: 'personasUsuarios'
    },[
       {name: 'label',  type: 'string', mapping:'label'},
       {name: 'value',  type: 'string', mapping:'value'}
     ]),
    remoteSort: true
});

storePersonasUsuarios.load();

var storeInstrumentos = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
        method: 'POST', 
        url: 'flujoendoso/listaFormasDePago.action'
    }),
    reader: new Ext.data.JsonReader({
    root: 'listaIntrumentoPago'
    },[
       {name: 'label',  type: 'string', mapping:'label'},
       {name: 'value',  type: 'string', mapping:'value'},
       {name: 'extra',  type: 'string', mapping:'extra'}
     ]),
    remoteSort: true
});

storeInstrumentos.load();

var idPerson = new Ext.form.Hidden({
    id:'idPerson',
    name:'cdPerson',
    value:''
});

var idDomicilio = new Ext.form.Hidden({
    id:'idDomicilio',
    name:'cdDomicilio',
    value:''
});

var idInstPago = new Ext.form.Hidden({
    id:'idInstrumentoPago',
    name:'cdInstrumentoPago',
    value:''
});

var idTipoTar = new Ext.form.Hidden({
    id:'idTipoTarjeta',
    name:'cdTipoTarjeta',
    value:''
});

var idBanco = new Ext.form.Hidden({
    id:'idBanco',
    name:'cdBanco',
    value:''
});

var idSucursal = new Ext.form.Hidden({
    id:'idSucursal',
    name:'cdSucursal',
    value:''
});

/*
domicilio.on('select', function(){
    
});
*/

var pagadoPor = new Ext.form.ComboBox({
        store: storePersonasUsuarios,
        width: 200,
        mode: 'local',
        hiddenName: 'idUser',
        name: 'pagadoPor',
        id: 'pagadoPor',
        typeAhead: true,
        labelSeparator:'',          
        triggerAction: 'all',   
        valueField: 'value',        
        displayField:'label',
        forceSelection: true,
        allowBlank: true,
        fieldLabel: "Pagado por",
        emptyText:'Seleccione...',
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
            comboPersonaDato    = record.get('label');
            comboPersonaValor   = record.get('value');
            Ext.getCmp('idPerson').setValue(comboPersonaValor);
        
            domicilio.clearValue();
            storeDomicilio.baseParams['idUser'] = pagadoPor.getValue();
                storeDomicilio.load({
                  callback : function(r, options, success) {
                      if (!success) {
                             storeDomicilio.removeAll();
                          }
                      }
                });
        }
});

var storeDomicilio = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
        method: 'POST', 
        url: 'flujoendoso/obtieneDomicilio.action'
    }),
    reader: new Ext.data.JsonReader({
    root: 'domicilio'
    },[
       {name: 'label',  type: 'string', mapping:'label'},
       {name: 'value',  type: 'string', mapping:'value'}
     ]),
    remoteSort: true,
    baseParams: {idUser: pagadoPor.getValue()}
});

var domicilio = new Ext.form.ComboBox({
        store: storeDomicilio,
        width: 200,
        mode: 'local',
        hiddenName: 'dsDomicilio',
        name: 'dsDomicilio',
        id: 'domicilio',
        typeAhead: true,
        labelSeparator:'',          
        triggerAction: 'all',   
        valueField: 'value',        
        displayField:'label',
        forceSelection: true,
        allowBlank: true,
        fieldLabel: "Domicilio",
        emptyText:'Seleccione...',
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
            comboDomicilioDato    = record.get('label');
            comboDomicilioValor   = record.get('value');
            Ext.getCmp('idDomicilio').setValue(comboDomicilioValor);
        }
});

/*
pagadoPor.on('select', function(){

});
*/
var instrumentoPago = new Ext.form.ComboBox({
        store: storeInstrumentos,
        width: 200,
        mode: 'local',
        hiddenName: 'dsInstrumentoPago',
        name: 'dsInstrumentoPago',
        id: 'instrumentoPago',
        typeAhead: true,
        labelSeparator:'',          
        triggerAction: 'all',   
        valueField: 'value',        
        displayField:'label',
        forceSelection: true,
        allowBlank: true,
        fieldLabel: "Instrumento de Pago",
        emptyText:'Seleccione...',
        selectOnFocus:true,
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

            comboInstDato    = record.get('label');
            comboInstValor   = record.get('value');
            Ext.getCmp('idInstrumentoPago').setValue(comboInstValor);

            var feedback = record.get('extra');
            if(feedback == 'S'){
                Ext.getCmp('id-form-pago-hide').show();
                Ext.getCmp('combo-banco').enable();
                Ext.getCmp('sucursal').enable();
                Ext.getCmp('combo-tipo-tarjeta').enable();
                Ext.getCmp('numero-tarjeta').enable();
                Ext.getCmp('fecha-vencimiento').enable();
                Ext.getCmp('digito-verificador').enable();
            }else{
                Ext.getCmp('id-form-pago-hide').hide();
                Ext.getCmp('combo-banco').disable();
                Ext.getCmp('sucursal').disable();
                Ext.getCmp('combo-tipo-tarjeta').disable();
                Ext.getCmp('numero-tarjeta').disable();
                Ext.getCmp('fecha-vencimiento').disable();
                Ext.getCmp('digito-verificador').disable();
                Ext.getCmp('combo-banco').reset();
                Ext.getCmp('sucursal').reset();
                Ext.getCmp('combo-tipo-tarjeta').reset();
                Ext.getCmp('numero-tarjeta').reset();
                Ext.getCmp('fecha-vencimiento').reset();
                Ext.getCmp('digito-verificador').reset();
            }
        }
});

var banco = new Ext.form.ComboBox({
        id:'combo-banco',
        store: storeBancos,
        width: 200,
        listWidth: 215, 
        mode: 'local',
        hiddenName: 'dsBanco',
        name: 'dsBanco',
        typeAhead: true,
        labelSeparator:'',          
        triggerAction: 'all',   
        valueField: 'value',        
        displayField:'label',
        forceSelection: true,
        fieldLabel: 'Banco',
        emptyText:'Seleccione...',
        allowBlank:false,
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
            comboBancoDato    = record.get('label');
            comboBancoValor   = record.get('value');
            Ext.getCmp('idBanco').setValue(comboBancoValor);
            
            sucursal.clearValue();
            storeSucursal.baseParams['codigoBanco'] = banco.getValue();
            storeSucursal.load({
              callback : function(r, options, success) {
                  if (!success) {
                    //  Ext.MessageBox.alert('Aviso','No se encontraron registros');  
                         storeSucursal.removeAll();
                      }
                  }
            });
        }
});

var storeSucursal = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
        method: 'POST', 
        url: 'flujoendoso/obtieneSucursal.action'
    }),
    reader: new Ext.data.JsonReader({
    root: 'sucursal'
    },[
       {name: 'label',  type: 'string', mapping:'label'},
       {name: 'value',  type: 'string', mapping:'value'}
     ]),
    remoteSort: true,
    baseParams: {codigoBanco: banco.getValue()}
});

var sucursal = new Ext.form.ComboBox({
        store: storeSucursal,
        width: 200,
        listWidth: 215,
        mode: 'local',
        hiddenName: 'dsSucursal',
        name: 'dsSucursal',
        id: 'sucursal',
        typeAhead: true,
        labelSeparator:'',          
        triggerAction: 'all',   
        valueField: 'value',        
        displayField:'label',
        forceSelection: true,
        allowBlank: true,
        fieldLabel: "Sucursal",
        emptyText:'Seleccione...',
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
            comboSucDato    = record.get('label');
            comboSucValor   = record.get('value');
            Ext.getCmp('idSucursal').setValue(comboSucValor);
        }
});

var storeTipoTarejta = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
        method: 'POST', 
        url: 'flujoendoso/obtieneTipoTarjeta.action'
    }),
    reader: new Ext.data.JsonReader({
    root: 'tipoTarjeta'
    },[
       {name: 'label',  type: 'string', mapping:'label'},
       {name: 'value',  type: 'string', mapping:'value'}
     ]),
    remoteSort: true
});

storeTipoTarejta.load();

var tipoTarjeta = new Ext.form.ComboBox({
        id: 'combo-tipo-tarjeta',
        store: storeTipoTarejta,
        width: 200,
        listWidth: 215,
        mode: 'local',
        hiddenName: 'dsTipoTarjeta',
        name: 'dsTipoTarjeta',
        typeAhead: true,
        labelSeparator:'',          
        triggerAction: 'all',   
        valueField: 'value',        
        displayField:'label',
        forceSelection: true,
        allowBlank: true,
        fieldLabel: "Tipo Tarjeta",
        emptyText:'Seleccione...',
        allowBlank:false,
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
            comboTiptarjDato    = record.get('label');
            comboTiptarjValor   = record.get('value');
            Ext.getCmp('idTipoTarjeta').setValue(comboTiptarjValor);
        }
}); 

var txNumeroTarjeta = new Ext.form.TextField({
    id:'numero-tarjeta',
    fieldLabel: 'Numero de tarjeta',
    allowBlank: true,
    name: 'numeroTarjeta',
    width: 200,
    allowBlank:false,
    frame: true 
});

var txFechaVencimiento = new Ext.form.TextField({
    id:'fecha-vencimiento',
    fieldLabel: 'Fecha de Vencimiento',
    allowBlank: true,
    name: 'fechaVencimiento',
    width: 200,
    allowBlank:false,
    frame: true 
});

var txDigitoVerificador = new Ext.form.TextField({
    id:'digito-verificador',
    fieldLabel: 'Digito Verificador',
    allowBlank: true,
    name: 'digitoVerificador',
    width: 200,
    allowBlank:false,
    frame: true 
});

var txtAccion = new Ext.form.Hidden({
    name: 'accion',
    width: 200,
    value:'I' 
});

var formPanelAgrupadores = new Ext.form.FormPanel({
    url: 'flujoendoso/guardaAgrupadores.action',
    border: false,
    width: 776,
    autoHeight: true,
    items:[{
        //title :'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Agrupadores</span>',
        labelWidth: 150,   
        labelAlign: 'right',          
        layout: 'form',
        collapsible: true,
        frame: true,
        items: [
                txtAccion,
        		wcdUnieco,
				wcdRamo,
				westado,
				wnmPoliza,
                idPerson,
                idInstPago,
				pagadoPor,
                idDomicilio,
				domicilio,
				instrumentoPago,
        	{
				layout:'form',
				id:'id-form-pago-hide',
				hidden:true,
				border:true,
				items : [
					idBanco,
                    banco,
                    idSucursal,
					sucursal,
                    idTipoTar,
					tipoTarjeta,
					txNumeroTarjeta,
					txFechaVencimiento,
					txDigitoVerificador
					]
        	}
        ]
   }]
});

var windowAgrupadores = new Ext.Window({
    title: 'Editar Datos de Cobro',
    width: 500,
    //height:350,
    autoHeight: true,
    //minWidth: 300,
    //minHeight: 200,
    layout: 'fit',
    plain:true,
    modal:true,
    bodyStyle:'padding:5px;',
    buttonAlign:'center',
    closable : false,
    items: [formPanelAgrupadores],
    buttons:[{
        text: 'Guardar',
        tooltip: 'Guarda la información',
        handler: function(){
            
            if(formPanelAgrupadores.form.isValid()){
                formPanelAgrupadores.form.submit({    
                			waitTitle:'Espere',
                            waitMsg:'Procesando...',
                            failure: function(form, action) {
                                Ext.MessageBox.alert('Error en Agregar ', '');
                                windowAgrupadores.hide();
                            },
                            success: function(form, action) {
                                Ext.MessageBox.alert('Transacción Satisfactoria', 'La operación ha sido exitosa');
                                windowAgrupadores.hide();
                                Ext.getCmp('combo-banco').reset();
                                Ext.getCmp('fecha-vencimiento').reset();
                                Ext.getCmp('numero-tarjeta').reset();
                                Ext.getCmp('combo-tipo-tarjeta').reset();
                                Ext.getCmp('sucursal').reset();
                                Ext.getCmp('instrumentoPago').reset();
                                Ext.getCmp('pagadoPor').reset();
                                Ext.getCmp('domicilio').reset();
                                Ext.getCmp('idPerson').reset();
                                Ext.getCmp('idDomicilio').reset();
                                Ext.getCmp('digito-verificador').reset();
                                storeAgrupadores.load();
                                /*grid2.destroy();
                                createGrid();
                                store.load({params:{start:0, limit:10}}); 
                                tabs.setActiveTab(0);*/
                            }
                        });                   
                    }else{
                        Ext.MessageBox.alert('Error', 'Por favor revise los errores!');
                    } 
            }//function
    },{
        text: 'Regresar',
        tooltip: 'Cierra la ventana',
        handler: function(){
        if(_VOLVER3){
            		window.location=_ACTION_IR_POLIZAS_RENOVADAS + "?idRegresar=S";
            }else{
            windowAgrupadores.hide();
            }
        }//handler
    }]
});

var storeAgrupadores = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
        method: 'POST', 
        url: '../procesoemision/consultaAgrupador.action'+'?cdUnieco='+cdUnieco.getValue()+'&cdRamo='+cdRamo.getValue()+'&estado='+estado.getValue()+'&nmPoliza='+nmPoliza.getValue()+'&nmSituac=1'
    }),
    reader: new Ext.data.JsonReader({
    root: 'listMpoliagr'
    },[
       {name: 'dsNombre',             type: 'string', mapping:'dsNombre'},
       {name: 'dsBanco',              type: 'string', mapping:'dsBanco'},
       {name: 'dsDomicilio',          type: 'string', mapping:'dsDomicilio'},
       {name: 'dsFormpag',            type: 'string', mapping:'dsForpag'},
       {name: 'dsFechaVencimiento',   type: 'string', mapping:'fechaUltreg'},
       {name: 'dsTipoTarjeta',        type: 'string', mapping:'dsTipotarj'},
       {name: 'dsNumCuenta',          type: 'string', mapping:'nmcuenta'},
       {name: 'dsSucursal',           type: 'string', mapping:'dsSucursal'},
       {name: 'cdBanco',			  type: 'string', mapping:'cdBanco'},
       {name: 'cdPerson',			  type: 'string', mapping:'cdPerson'},
       {name: 'cdFormpag',            type: 'string', mapping:'cdForpag'},
       {name: 'cdDomicilio',          type: 'string', mapping:'nmorddom'},
       {name: 'cdSucursal',           type: 'string', mapping:'cdSucursal'},
       {name: 'nmDigver',             type: 'string', mapping:'nmDigver'},
       {name: 'cdTipoTarjeta',        type: 'string', mapping:'cdTipoTarjeta'},
       {name: 'dsTipoTarjeta',        type: 'string', mapping:'dsTipoTarjeta'},
       {name: 'muestraCampos',        type: 'string', mapping:'muestraCampos'} 
     ]),
    remoteSort: true
});

var cmAgrupadores = new Ext.grid.ColumnModel([
    new Ext.grid.RowNumberer(),
        {header: "Nombre",              dataIndex:'dsNombre',       width:150,  sortable:true},
        {header: "Banco",               dataIndex:'dsBanco',        width:200,  sortable:true},
        {header: "Instrumento de Pago", dataIndex:'dsFormpag',      width:250,  sortable:true},
        {header: "Domicilio",           dataIndex:'dsDomicilio',    width:400,  sortable:true}
]);

var cdGaPerson;
var cdGaDom;
var cdGaFormpag;
var cdGaBanco;
var cdGaSucursal;
var dsGaBanco;
var dsGaTipoTarjeta;
var dsGaNumCuenta;
var dsGaFechaVen;
var dsGaSucursal;
var dsGaNombre;
var dsGaInstrumento;
var dsGaDom;
var dsGaDV;
var cdGaTipTar;
var dsGaTipTar;
var GaMuestraCampos;
                                
var gridAgrupadores = new Ext.grid.GridPanel({
        store: storeAgrupadores,
        border: true,
        buttonAlign: 'center',
        baseCls:' background:white ',
        cm: cmAgrupadores,
        buttons:[/*{
                    id:'agregarAgrupadores',
                    text:'Agregar',
                    tooltip:'Agrega un nuevo registro',
                    handler: function(){
        				Ext.getCmp('banco').clearValue();
        				Ext.getCmp('fecha-vencimiento').reset();
        				Ext.getCmp('numeroTarjeta').reset();
        				Ext.getCmp('tipoTarjeta').clearValue();
        				Ext.getCmp('sucursal').clearValue();
        				Ext.getCmp('instrumentoPago').clearValue();
        				Ext.getCmp('pagadoPor').clearValue();
        				Ext.getCmp('domicilio').clearValue();
                        windowAgrupadores.show();
                    }
                },*/{
                    id:'editarAgrupadores',
                    text:'Editar',
                    tooltip:'Edita el registro seleccionado'                           
                }],                                                     
        width: 764,
        frame: true,
        height: 135,
        //defaults:{anchor: '55%'},
    sm: new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {                            
                    rowselect: function(sm, row, rec){ 
                            
                                cdGaPerson = rec.get('cdPerson');
                                cdGaDom = rec.get('cdDomicilio');
                                cdGaFormpag = rec.get('cdFormpag');
                                cdGaBanco = rec.get('cdBanco');
                                cdGaSucursal = rec.get('cdSucursal');
                                cdGaTipTar = rec.get('cdTipoTarjeta');
                                //la variable dsGaBanco esta mapeada a cdbanco 
                                //ya que dsbanco no lo trae el pl.
                                dsGaBanco = rec.get('cdBanco');
                                dsGaTipoTarjeta = rec.get('dsTipoTarjeta');
                                dsGaNumCuenta = rec.get('dsNumCuenta');
                                dsGaFechaVen = rec.get('dsFechaVencimiento');
                                dsGaSucursal = rec.get('dsSucursal');
                                dsGaNombre = rec.get('dsNombre');
                                dsGaInstrumento = rec.get('dsFormpag');
                                dsGaDom = rec.get('dsDomicilio');
                                dsGaDV  = rec.get('nmDigver');
                                dsGaTipTar = rec.get('dsTipoTarjeta');
                                GaMuestraCampos = rec.get('muestraCampos');
                    }
                }//listeners
        
        }),
        //viewConfig: {autoFill: true,forceFit:true},                
        bbar: new Ext.PagingToolbar({
            pageSize:20,
            store: storeAgrupadores,                                               
            displayInfo: true,
            displayMsg: 'Registros mostrados {0} - {1} de {2}',
            emptyMsg: 'No hay registros para mostrar',
    		beforePageText: 'P&aacute;gina',
    		afterPageText: 'de {0}'                   
            })                                                                                               
        });

	Ext.getCmp('editarAgrupadores').on('click',function(){
		if( !gridAgrupadores.getSelectionModel().hasSelection() ) {
			Ext.Msg.alert(_MSG_AVISO, _MSG_NO_ROW_SELECTED);
		} else {
			Ext.getCmp('instrumentoPago').setValue(dsGaInstrumento);
	        Ext.getCmp('pagadoPor').setValue(dsGaNombre);
	        Ext.getCmp('domicilio').setValue(dsGaDom);
	        Ext.getCmp('idPerson').setValue(cdGaPerson);
	        Ext.getCmp('idDomicilio').setValue(cdGaDom);
	        Ext.getCmp('idInstrumentoPago').setValue(cdGaFormpag);
	        
	        windowAgrupadores.show();
	
	        if(GaMuestraCampos == 'S'){
	            Ext.getCmp('id-form-pago-hide').show();
	            Ext.getCmp('idBanco').setValue(cdGaBanco);
	            Ext.getCmp('combo-banco').setValue(dsGaBanco);
	            Ext.getCmp('fecha-vencimiento').setValue(dsGaFechaVen);
	            Ext.getCmp('numero-tarjeta').setValue(dsGaNumCuenta);
	            Ext.getCmp('idTipoTarjeta').setValue(cdGaTipTar);
	            Ext.getCmp('combo-tipo-tarjeta').setValue(dsGaTipoTarjeta);
	            Ext.getCmp('idSucursal').setValue(cdGaSucursal);
	            Ext.getCmp('sucursal').setValue(dsGaSucursal);
	            Ext.getCmp('digito-verificador').setValue(dsGaDV);
	        }else{
	            Ext.getCmp('id-form-pago-hide').hide();
	        }
	    } 

    });
    
var panelAgrupadores = new Ext.Panel({
    border: false,
    width: 776,
    items:[{
        title :'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Datos de Cobro</span>',
        labelWidth: 150,   
        labelAlign: 'right',          
        layout: 'form',
        collapsible: true,
        frame: true,
        items: [gridAgrupadores]
   }]
});

storeAgrupadores.load();

//************ RECIBOS ***************
function createOptionGrid(){                                       
        url='../procesoemision/procesoemision/recibos.action'+'?cdUnieco='+cdUnieco.getValue()+'&cdRamo='+cdRamo.getValue()+'&nmPoliza='+nmPoliza.getValue();
        storeRecibos = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: url
        }),
        reader: new Ext.data.JsonReader({
			root:'listRecibos',
			id:'opcionesStore',
			totalProperty: 'totalCount'             
		},[
			{name: 'feinicio',      type: 'string',    mapping:'feinicio'},
			{name: 'fefinal',       type: 'string',    mapping:'fefinal'},
			{name: 'dsestado',      type: 'string',    mapping:'dsestado'},
			{name: 'dsTipoRecibo',  type: 'string',    mapping:'dsTipoRecibo'},
			{name: 'ptimport',      type: 'string',    mapping:'ptimport'}
        ]),
        remoteSort: true
    });
    storeRecibos.setDefaultSort('opcionesStore', 'desc');
    storeRecibos.load();
    return storeRecibos;
}


//Columnas Grid Recibos
function toggleDetails(btn, pressed){
        var view = grid.getView();
        view.showPreview = pressed;
        view.refresh();
        }

var cmRecibos = new Ext.grid.ColumnModel([
            new Ext.grid.RowNumberer(),
            {header: "Inicio Vigencia", dataIndex:'feinicio',       width: 120, sortable:true},
            {header: "Fin Vigencia",    dataIndex:'fefinal',        width: 120, sortable:true},
            {header: "Estado",          dataIndex:'dsestado',       width: 140, sortable:true},
            {header: "Tipo Recibo",     dataIndex:'dsTipoRecibo',   width: 140, sortable:true},
            {header: "Importe",         dataIndex:'ptimport',       width: 120, sortable:true, renderer:Ext.util.Format.usMoney}
]);


//Crear Grid Recibos
//function gridRecibos(){
var recibosGrid= new Ext.grid.GridPanel({
    store:createOptionGrid(),
    border:true,
    buttonAlign: 'left',
    collapsible: true, 
    baseCls:' background:white ',
    cm: cmRecibos,
    /*buttons:[
            {text:'Regresar',
            tooltip:'Regresar a la pantalla anterior'
            }], */                                                        
    width:764,
    frame:true,
    height:320,        
    //title:'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Listado</span>',      
    viewConfig: {autoFill: true,forceFit:true},                
    bbar: new Ext.PagingToolbar({
        pageSize:20,
        store: storeRecibos,                                               
        displayInfo: true,
        displayMsg: 'Registros mostrados {0} - {1} de {2}',
        emptyMsg: 'No hay registros para mostrar',
		beforePageText: 'P&aacute;gina',
		afterPageText: 'de {0}'                     
        })                                                                                               
});
    
    //recibosGrid.render('gridRecibos');
//}
//gridRecibos();

var panelRecibos = new Ext.Panel({
    border: false,
    width: 774,
    items:[{
        title :'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Recibo</span>',
        labelWidth: 100,             
        layout: 'form',
        collapsible: true,
        frame: true,
        items:[recibosGrid]
   }]
});

//************* TABS ***********
var tabs = new Ext.TabPanel({
    activeTab: 0,
    width: 778,
    autoScroll: false,
    deferredRender:false,
    plain:true,
    frame: true,
    defaults:{autoScroll: true},
    items:[{
            title: 'Datos Generales',
            layout: 'form',                         
            border: false,
            autoHeight: true,
            autoWidth: true,                           
            items: [dataGeneralesPoliza, dataAdicionalesPoliza, panelObjAsegurable, panelFnPoliza, panelAgrupadores]
        },{
            title: 'Recibo',
            layout:'form',                         
            border:false,                           
            items: [panelRecibos]
        }
    ]
});

//***********
  
  
/*************************************************************
** Panel
**************************************************************/ 
  var panelPrincipal = new Ext.Panel({
        //region: 'north',
        buttonAlign: 'center',
        title: 'CAMBIO DE POLIZAS',
        //autoHeight : true ,
        //height: 800,
        width: 800,
        //autoWidth: true,
        id:'panelPrincipal',
        bodyStyle:'padding:5px',
        frame: true,
        items: [cdUnieco, cdRamo, estado, nmPoliza,
            {
            layout: 'column',
            border: false,
            bodyStyle:'padding:5px 5px 0',
            width: 850,
            items: [{
                columnWidth: .3,
                layout: 'form',
                border: false,
                labelWidth: 70,
                items: [aseguradora, fechasolicitud]       
            },{
                columnWidth: .4,
                layout: 'form',
                width: 100,
                border: false,
                labelWidth: 50,
                items: [producto, fechaefectividadendoso]
            },{
                columnWidth: .3,
                layout: 'form',
                border: false,
                labelWidth: 30,
                items: [poliza, inciso]
            }]
        },{ 
            layout: 'form',
            border: false,
            items: [tabs]}/*dataGeneralesPoliza, dataAdicionalesPoliza*/],
        buttons:[{
            id:'regresar',
            text:'Regresar',
            tooltip:'Regresar',
            handler: function(){
            if(_VOLVER3 || PROCESO_RENOVACION){
            		window.location=_ACTION_IR_POLIZAS_RENOVADAS + "?idRegresar=S";
            }else{
                    window.location.replace(_CONTEXT+"/procesoemision/busquedaPoliza.action" + "?idRegresar=S");
                }
            }
        },{
            id:'tarificar',
            text:'Continuar',
            tooltip:'Continua para confirmar el endoso'
        }] 
    });

panelPrincipal.render('items');

// Funciona para validar la tarificación
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
//*************************
var connSuplLog = new Ext.data.Connection();
    connSuplLog.request({
         url: 'flujoendoso/generaSuplLogico.action'+'?cdUnieco=' + cdUnieco.getValue() + '&cdRamo=' + cdRamo.getValue() + '&estado=' + estado.getValue() + '&nmPoliza=' + nmPoliza.getValue(),
         method: 'POST',
         successProperty : '@success',
         //params : [idHoja],
         callback: function (options, success, response){
                     if (Ext.util.JSON.decode(response.responseText).success == false) {
                         Ext.Msg.alert('Error', 'Ocurrio un error en la transaccion');
                     }else{
                     
                      }//else
                   }//function
    });
    
var connSuplFis = new Ext.data.Connection();
    connSuplFis.request({
         url: 'flujoendoso/generaSuplFisico.action'+'?cdUnieco=' + cdUnieco.getValue() + '&cdRamo=' + cdRamo.getValue() + '&estado=' + estado.getValue() + '&nmPoliza=' + nmPoliza.getValue(),
         method: 'POST',
         successProperty : '@success',
         //params : [idHoja],
         callback: function (options, success, response){
                     if (Ext.util.JSON.decode(response.responseText).success == false) {
                         Ext.Msg.alert('Error', 'Ocurrio un error en la transaccion');
                     }else{
                     
                      }//else
                   }//function
    });
    
    
    //TODO: esto va en la pantalla anterior, solo es temporal aqui
    //Si msgId tiene un mensaje, no se puede hacer un endoso y se redirige a la pagina principal:
    <s:if test="msgId != ''">
    Ext.Msg.show({
		title: 'Status',
		msg: 'No se puede continuar con el Endoso',
		buttons: Ext.Msg.OK,
		icon: Ext.Msg.ERROR,
		fn: function (btn, text){
    		if (btn == 'ok'){
    			document.forms[0].action = '${ctx}/portal.action'; 
    			document.forms[0].submit();
    		}
		}
	});
	</s:if>
	
});

</script>