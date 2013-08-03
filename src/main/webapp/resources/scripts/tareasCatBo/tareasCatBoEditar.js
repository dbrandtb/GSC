// Funcion de Editar y Agregar Tareas de Cat Bo
function editarTrsCtBo(key) {

var valida; 
var guardarYAgregar = false;
var codiIndSema = new Ext.form.NumberField({
        fieldLabel: getLabelFromMap('indiSemaforoId',helpMap,'Indicador Sem&aacute;foro'),
        tooltip:getToolTipFromMap('indiSemaforoId',helpMap,'Porcentaje del tiempo para cambio de color entre 1 y 99'),
        labelWidth: 10,
        id: 'indiSemaforoId', 
        name: 'indiSemaforo',
        //blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
        allowBlank : false,
        //maxLength:2,
        width: 160,
        decimalPrecision: 0,
        allowDecimals: false,
        allowNegative: false,
        helpText: 'Porcentaje del tiempo para cambio de color',
        validator : function(value){
                     if(value < 100){
                            return true;
                        }else{
                            this.setValue(1);
                            Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Debe Ingresar un Numero entre 1 y 99'));
                            return true;
                        }
                }
});

var comboTareas = new Ext.form.ComboBox(
{
    tpl: '<tpl for="."><div ext:qtip="{codigoProceso}.{descriProceso}" class="x-combo-list-item">{descriProceso}</div></tpl>',
    store: descProcesos,
    id:'comboTareaId',
    fieldLabel: getLabelFromMap('comboTareaId',helpMap,'Tareas'),
    tooltip: getToolTipFromMap('comboTareaId',helpMap,'Tareas'),
    width: 160,
    //blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
    allowBlank: false,
    displayField:'descriProceso',
    valueField: 'codigoProceso',
    hiddenName: 'codiTareas',
    typeAhead: true,
    triggerAction: 'all',
    mode: 'local',
    emptyText:'Seleccione Tarea...',
    selectOnFocus:true,
    forceSelection:true
}
);

var comboEstatus = new Ext.form.ComboBox(
{
    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
    store: descEstatus,
    id:'comboEstatusId',
    fieldLabel: getLabelFromMap('comboEstatusId',helpMap,'Estatus'),
    tooltip: getToolTipFromMap('comboEstatusId',helpMap,'Estatus'),
    width: 126,
    //anchor:'100%',
    //allowBlank: false,
    displayField:'descripcion',
    valueField: 'codigo',
    hiddenName: 'codiEstatus',
    typeAhead: true,
    mode: 'local',
    triggerAction: 'all',
    emptyText:'Seleccione Estatus...',
    selectOnFocus:true,
    forceSelection:true
}
);

var comboModulos = new Ext.form.ComboBox(
{
    tpl: '<tpl for="."><div ext:qtip="{codigoModulo}.{descriModulo}" class="x-combo-list-item">{descriModulo}</div></tpl>',
    store: descModulos,
    id:'comboModulosId',
    fieldLabel: getLabelFromMap('comboModulosId',helpMap,'M&oacute;dulo'),
    tooltip: getToolTipFromMap('comboModulosId',helpMap,'Nombre M&oacute;dulo'),
    width: 160,
    //anchor:'100%',
    allowBlank: false,
    displayField:'descriModulo',
    valueField: 'codigoModulo',
    hiddenName: 'codiModulos',
    typeAhead: true,
    triggerAction: 'all',
    emptyText:'Seleccione Módulo...',
    selectOnFocus:true,
    mode: 'local',
    forceSelection:true
}
);

var comboPrioridad = new Ext.form.ComboBox(
{
    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
    store: descPrioridad,
    id:'comboPrioridadId',
    fieldLabel: getLabelFromMap('comboPrioridadId',helpMap,'Prioridad'),
    tooltip: getToolTipFromMap('comboPrioridadId',helpMap,'Prioridad'),
    width: 126,
    allowBlank: false,
    displayField:'descripcion',
    valueField: 'codigo',
    hiddenName: 'codiPrioridad',
    typeAhead: true,
    triggerAction: 'all',
    mode:'local',
    emptyText:'Seleccione Prioridad...',
    selectOnFocus:true,
    forceSelection:true
}
);

var form_edit = new Ext.FormPanel ({
	id: 'form_editId',
    url : _ACTION_OBTENER_TAREAS_CAT_BO,
    frame : true,
    width : 600,
	height: true,
    labelAlign: 'right',
    reader: elJsonTareas,
    layout:'column',
    bodyStyle:'background: white',
    border:false,
    columnWidth: 1,
    items:[
            {
            columnWidth:.5,
            layout: 'form',
            border: false,
            items:[   
                     {
                        xtype: 'hidden', 
                        id: 'frmAbId', 
                        name: 'frmAbId'  
                     },
                     comboTareas,
                     comboModulos,
                     codiIndSema                                  
                 ]
            },
            {
            columnWidth:.5,
            layout: 'form',
            border: false,
            items:[                                     
                     comboEstatus,
                     comboPrioridad
                     
                    
                            
                  ]
            }
        ]   
});

	  var titulo;
     if(key == '' ){
        titulo= getLabelFromMap('ElmtEstrAgregarId',helpMap,'Agregar elemento de Estructura');	
	  }else{
	         titulo=getLabelFromMap('ElmtEstrEditarId',helpMap,'Editar elemento de Estructura');		
	       }


var window = new Ext.Window ({
	    title: titulo,
		width: 600,
		autoHeight: true,
        modal:true,
    	items: [form_edit],
        buttonAlign:'center',
        //bodyStyle:'background: white',
        buttons : [ 
            {
                text : getLabelFromMap('frmtTrsCtBoEdtButtonGuardar',helpMap,'Guardar'), 
                tooltip: getToolTipFromMap('frmtTrsCtBoEdtButtonGuardar',helpMap,'Guarda Nueva Tarea CAT-BO'),
                handler : function() {
                      if (form_edit.form.isValid()) {
                      	guardarYAgregar = false;
                        guardarTareaCatBo();
                      } else{
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                      }
                }
            }, 
            {
                id:'elBotonId',
                text : getLabelFromMap('elBotonId',helpMap,'Guardar y Agregar'), 
                tooltip: getToolTipFromMap('elBotonId',helpMap,'Guarda y Agrega una nueva Tarea CAT-BO'),
                handler : function() {
                      if (form_edit.form.isValid()) {
                      	guardarYAgregar = true;
                        guardarTareaCatBo();
                        //form_edit.getForm().reset();
                      } else{
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                      }
                    }
            },
            {
               text : getLabelFromMap('frmtTrsCtBoEdtButtonRegresar',helpMap,'Regresar'), 
                tooltip: getToolTipFromMap('frmtTrsCtBoEdtButtonRegresar',helpMap,'Regresar'),
               handler : function() {
                    window.close();
                }
            },
            {
               text : getLabelFromMap('frmtTrsCtBoEdtButtonCompraTiempo',helpMap,'Comprar Tiempo'), 
               tooltip: getToolTipFromMap('frmtTrsCtBoEdtButtonCompraTiempo',helpMap,'Comprar Tiempo'),
               handler : function() {
                        //_ACTION_BUSCAR_TAREAS_CAT_BO_VALIDA,
               			validar();
               			//var blanco='';
               			//if((Ext.util.JSON.decode(response.responseText).valida)=="")||((Ext.util.JSON.decode(response.responseText).valida)==null)
               			     // Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Debe guardar una tarea para Comprar Tiempo')); 
               			  
               			//else
               			 // configurarCompraTiempo(key,blanco,comboTareas.getRawValue());
                    //window.close();
                }
            }
        ]
});

window.show();	
if (key != "") 
{
	form_edit.findById('frmAbId').setValue(1);
    Ext.getCmp('elBotonId').setVisible(false);
    Ext.getCmp('comboTareaId').setDisabled(true);
    Ext.getCmp('comboModulosId').setDisabled(true);
    
    form_edit.form.load ({
    params: {
      //  cdValida: _ACTION_BUSCAR_TAREAS_CAT_BO_VALIDA,
        cdProceso: key
        },
    waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
    waitMsg : getLabelFromMap('400028',helpMap,'Leyendo datos ...'),
    success: function(form, action){
        //console.log(action.result)
        var cdPro_In = action.result.data.codProceso;
        var cdEst_In = action.result.data.codEstatus;
        var cdMod_In = action.result.data.codModulo;
        var cdPri_In = action.result.data.codPriord;
        descProcesos.load({
        
            callback: function (r, o, success) {
                    if (success) {
                        comboTareas.setValue(cdPro_In);
                    }
            }
        });
        descEstatus.load({
            callback: function (r, o, success) {
                    if (success) {
                        comboEstatus.setValue(cdEst_In);
                    }
            }
        });
        descModulos.load({
            params: {
    				cdTabla: 'CATBOMODUL'
    				},
            callback: function (r, o, success) {
                    if (success) {
                        comboModulos.setValue(cdMod_In);
                    }
            }
        });
        descPrioridad.load({
            callback: function (r, o, success) {
                    if (success) {
                        comboPrioridad.setValue(cdPri_In);
                    }
            }
        }); 
         
    
        }
    });
}
else
{
    form_edit.findById('frmAbId').setValue(0);
    descModulos.load({
            params: {
    				cdTabla: 'CATBOMODUL'
    				}	
    });
    descEstatus.load();
    descPrioridad.load();
    descProcesos.load();
    form_edit.form.load ();
    Ext.getCmp('comboTareaId').setDisabled(false);
    Ext.getCmp('comboModulosId').setDisabled(false);
   
}

function guardarTareaCatBo()
{
        var params = {
                 cdProceso: form_edit.findById('comboTareaId').getValue(),
                 estatus: form_edit.findById('comboEstatusId').getValue(),
                 cdModulo: form_edit.findById('comboModulosId').getValue(),
                 cdPriord: form_edit.findById('comboPrioridadId').getValue(),
                 indSemaforo: form_edit.findById('indiSemaforoId').getValue(),
                 frmAb: form_edit.findById('frmAbId').getValue()
                 
                                 
             };
        execConnection (_ACTION_GUARDAR_TAREAS_CAT_BO, params, cbkGuardar);
}

function cbkGuardar (success, message) {
    if (success) {
          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), message,function(){
          
          		reloadGrid();
          		form_edit.getForm().reset();
				if (!guardarYAgregar) window.close(); 
          		if (key != "") {
					form_edit.findById('frmAbId').setValue(1);
	          		key = "";
          		} else  {
					form_edit.findById('frmAbId').setValue(0);
          		}
          });
    } else {
          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), message);
    }
}

function validar()
{
        var params = {
                 cdProceso: form_edit.findById('comboTareaId').getValue()                                                
             };
        //execConnection (_ACTION_BUSCAR_TAREAS_CAT_BO_VALIDA, params, cbkValidar,300000);

	var conn = new Ext.data.Connection ();
	//if (_timeout == null || _timeout == undefined) _timeout = 30000;
	conn.request ({
			url: _ACTION_BUSCAR_TAREAS_CAT_BO_VALIDA,
			method: 'POST',
			timeout: 30000,
			params: params,
			callback: function (options, success, response) {
						if (success) {
        if((Ext.util.JSON.decode(response.responseText).valida)==1){
          var blanco='';
          configurarCompraTiempo(key,blanco,comboTareas.getRawValue());
    }}
     else {     
         Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),message);  
    }
					}
	});
}


}
/*function cbkValidar (success, message) {
    if (success) {
        if((Ext.util.JSON.decode(response.responseText).valida)==1){
          var blanco='';
          configurarCompraTiempo(key,blanco,comboTareas.getRawValue());
    }}
     else {     
         Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),message);  
    }
}*/