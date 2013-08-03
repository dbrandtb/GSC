Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

//******************************************
//Form Principal
//******************************************
var idProceso = new Ext.form.Hidden({
    name:'cdprocxcta',
    value:OPCION_PROCESO_ID
});
var idPaso = new Ext.form.Hidden({
    name:'cdpaso',
    value:OPCION_PASO_ID
});
var nombrePaso = new Ext.form.TextField({
    fieldLabel: CONFIGWORKFLOW_PS3_PASO,
    name:'dspaso',
    value:OPCION_DESC_PASO,
    disabled:true,
    width: 200
});

var storePasoExito = new Ext.data.Store({
	proxy: new Ext.data.HttpProxy({
	    url: 'configworkflow/comboPasos.action'
	}),
	reader: new Ext.data.JsonReader({
	root: 'pasosList',
	id: 'pasosExito'
	    },[
	   {name: 'value', type: 'string',mapping:'value'},
	   {name: 'label', type: 'string',mapping:'label'}    
	    ]),
	    remoteSort: true
	});
storePasoExito.setDefaultSort('label', 'desc');
storePasoExito.load();

var cdPasoExitoHidden = new Ext.form.Hidden({
	id:'PasoExitoHidden',
	name: 'cdpasoexito'
});

var cdPasoFracasoHidden = new Ext.form.Hidden({
	id:'PasoFracasoHidden',
	name: 'cdpasofracaso'
});

var comboPasoExito = new Ext.form.ComboBox({
	name: 'dspasoexito',
    fieldLabel: CONFIGWORKFLOW_PS3_PASO_EXITO,
    store: storePasoExito,
    allowBlank: false,
    displayField:'label',
    //hiddenName: 'cdpasoexito',
    valueField:'value',
    typeAhead: true,
    mode: 'local',
    triggerAction: 'all',
    width:200,
    emptyText: CONFIGWORKFLOW_COMBO_EMPTY,
    //editable:false,
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
		cdPasoExitoHidden.setValue(valor);
		
	}
});

var storePasoFracaso = new Ext.data.Store({
	proxy: new Ext.data.HttpProxy({
	    url: 'configworkflow/comboPasos.action'
	}),
	reader: new Ext.data.JsonReader({
	root: 'pasosList',
	id: 'pasosFracaso'
	    },[
	   {name: 'value', type: 'string',mapping:'value'},
	   {name: 'label', type: 'string',mapping:'label'}    
	    ]),
	    remoteSort: true,
	    baseParams: {cdpaso: comboPasoExito.getValue()}
});
storePasoFracaso.setDefaultSort('label', 'desc');
storePasoFracaso.load();

var comboPasoFracaso = new Ext.form.ComboBox({
	name: 'dspasofracaso',
	fieldLabel:CONFIGWORKFLOW_PS3_PASO_FRACASO,
    store: storePasoFracaso,
    allowBlank: false,
    displayField:'label',
    //hiddenName: 'cdpasofracaso',
    valueField:'value',
    typeAhead: true,
    mode: 'local',
    triggerAction: 'all',
    width:200,
    emptyText: CONFIGWORKFLOW_COMBO_EMPTY,
    //editable:false,
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
		cdPasoFracasoHidden.setValue(valor);
		
	}
});

comboPasoExito.on('select', function(){
comboPasoFracaso.clearValue();
storePasoFracaso.baseParams['cdpaso'] = comboPasoExito.getValue();
	storePasoFracaso.load({
      callback : function(r, options, success) {
          if (!success) {
                 storePasoFracaso.removeAll();
              }
          }
	});
});

var chkPantallaFinal = new Ext.form.Checkbox({
	name: 'swfinal',
	fieldLabel: 'Pantalla Final',
	labelSeparator: ':' 
});

// opcionesForm, dentro del js, para pintar las variables de session en los campos
var opcionesForm = new Ext.form.FormPanel({
    	el:'opcionesForm',
        title: '<span style="color:black;font-size:14px;">Configurar Pasos Por Cuenta</span>',
        //iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',   
        frame:true,   
        url:'configworkflow/agregarConfig.action',                         
        width: 500,
        height:200,
        items: [{
                layout:'form',
                border: false,
                items:[{
                    title :'<span style="color:#98012e;font-size:10px;font-family:Arial,Helvetica,sans-serif">*'+CONFIGWORKFLOW_PS3_LABEL_COMBOS+'</span>',
                    bodyStyle:'background: white',
                labelWidth: 150,             
                layout: 'form',
                frame:true,
                baseCls: '',
                buttonAlign: "center",
                        items: [{
                            layout:'column',
                            border:false,
                            columnWidth: 1,
                            items:[{
                                columnWidth:1,
                                layout: 'form',                     
                                border: false,                      
                                items:	[ 
                                		cdPasoExitoHidden,
                                		cdPasoFracasoHidden,
                                        idProceso,
                                        idPaso,
                                        nombrePaso,
                                        comboPasoExito,
                                        comboPasoFracaso,
                                        chkPantallaFinal
                                		]
                                	},{
                                		columnWidth:.4,
                                		layout: 'form'
                                	}]
                            	}],
								        buttons: [{
								            text: CONFIGWORKFLOW_BTN_GUARDAR, 
								            handler: function() {                
								                if (opcionesForm.form.isValid()) {
								                    opcionesForm.form.submit({               
								                        waitMsg:CONFIGWORKFLOW_PROCESS_TITLE,
								                        failure: function(form, action) {
								                                Ext.MessageBox.alert(CONFIGWORKFLOW_BTN_GUARDAR,CONFIGWORKFLOW_PS3_GUARDAR_FAILURE);
								                        },
								                        success: function(form, action) {
								                            Ext.MessageBox.alert(CONFIGWORKFLOW_BTN_GUARDAR,CONFIGWORKFLOW_PS3_GUARDAR_SUCCESS);
								                        }
								                    });                   
								                }else{
								                    Ext.MessageBox.alert(CONFIGWORKFLOW_EXCEPTION_TITLE);
								                      }             
								                }
								        },{
								        text: CONFIGWORKFLOW_BTN_REGRESAR,
								        handler: function(){
													OpcionesVentana(idProceso.getValue());
								                }
								        }]
                        }]
                }]
        });

        opcionesForm.render();
//******************************************
//			Inicializar valores
//******************************************
if(OPCION_SWFINAL=='S'){
	chkPantallaFinal.checked=true;
}else{
	OPCION_SWFINAL='N';
	chkPantallaFinal.checked=false;
}

if(OPCION_PASO_EXITO!=null && OPCION_PASO_EXITO!=""){
	comboPasoExito.setValue(OPCION_PASO_EXITO);
	cdPasoExitoHidden.setValue(OPCION_ID_PASO_EXITO);
}else{
	cdPasoExitoHidden.setValue('');
}
if(OPCION_PASO_FRACASO!=null && OPCION_PASO_FRACASO!=""){
	comboPasoFracaso.setValue(OPCION_PASO_FRACASO);
	cdPasoFracasoHidden.setValue(OPCION_ID_PASO_FRACASO);
}else{
	cdPasoFracasoHidden.setValue('');
}
	
//******************************************
//Opcion para configurar
//******************************************
	OpcionesVentana = function(cdprocxcta) {

//			alert(_ACTION_CONFIG);
			window.location.href = _ACTION_CONFIG + '?cdprocxcta='+cdprocxcta;
	            
	}

});