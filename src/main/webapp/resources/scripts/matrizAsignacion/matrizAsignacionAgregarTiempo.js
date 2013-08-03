function agregarTiempoMatriz(cdMatriz,nivAtencion){

	
var dsNivelesAtencion = new Ext.data.Store({
 	proxy: new Ext.data.HttpProxy({
     	url: _ACTION_COMBO_NIVELES_ATENCION_MATRIZ
 	}),
 	reader: new Ext.data.JsonReader({
 		root: 'comboNivelAtencionMatriz',
 		totalProperty: 'totalCount',
 		id: 'cdNivAtn'
 		},[
			{name: 'cdNivAtn', type: 'string',mapping:'cdNivAtn'},
			{name: 'dsNivAtn', type: 'string',mapping:'dsNivAtn'},
			{name: 'cdMatriz', type: 'string'}
		  ]),
	sortable:true
});
		 		
var dsUnidad = new Ext.data.Store({
 	proxy: new Ext.data.HttpProxy({
     	url: _ACTION_COMBO_UNIDAD_TIEMPO
 	}),
 	reader: new Ext.data.JsonReader({
 		root: 'comboUnidadTiempoAComprar',
 		totalProperty: 'totalCount',
 		id: 'id'
 		},[
			{name: 'id', type: 'string',mapping:'id'},
			{name: 'texto', type: 'string',mapping:'texto'}
		  ]),
		  
		  sortable:true
});




		 		 	
var txtTiempoResolucion= new Ext.form.NumberField({
   	fieldLabel: getLabelFromMap('txtTiempoResolucionId', helpMap,'Tiempo de Resoluci&oacute;n'), 
	tooltip: getToolTipFromMap('txtTiempoResolucionId', helpMap,'Tiempo de Resoluci&oacute;n'),
	hasHelpIcon:getHelpIconFromMap('txtTiempoResolucionId',helpMap),
	Ayuda: getHelpTextFromMap('txtTiempoResolucionId',helpMap),
    id: 'txtTiempoResolucionId', 
    name: 'txtTiempoResolucion',
    allowBlank: false,
    anchor:'80%'
    //width:100
});
    
var txtTiempoAlarma = new Ext.form.NumberField({
   	fieldLabel: getLabelFromMap('txtTiempoAlarmaId', helpMap,'Tiempo de Alarma'), 
	tooltip: getToolTipFromMap('txtTiempoAlarmaId', helpMap,'Tiempo de Alarma'),
	hasHelpIcon:getHelpIconFromMap('txtTiempoAlarmaId',helpMap),
	Ayuda: getHelpTextFromMap('txtTiempoAlarmaId',helpMap),
    id: 'txtTiempoAlarmaId', 
    name: 'txtTiempoAlarma',
    allowBlank: false,
    anchor:'80%'
    //width:100
});

var txtTiempoEscalamiento = new Ext.form.NumberField({
   	fieldLabel: getLabelFromMap('txtTiempoEscalamientoId', helpMap,'Tiempo de Escalamiento'), 
	tooltip: getToolTipFromMap('txtTiempoEscalamientoId', helpMap,'Tiempo de Escalamiento'),
	hasHelpIcon:getHelpIconFromMap('txtTiempoEscalamientoId',helpMap),
	Ayuda: getHelpTextFromMap('txtTiempoEscalamientoId',helpMap),
    id: 'txtTiempoEscalamientoId', 
    name: 'txtTiempoEscalamiento',
    allowBlank: false,
    anchor:'80%'
    // width:100
});

//LOS COMBOS
var comboNiveles_Atencion = new Ext.form.ComboBox(
{
    tpl: '<tpl for="."><div ext:qtip="{cdNivAtn}.{dsNivAtn}" class="x-combo-list-item">{dsNivAtn}</div></tpl>',
    store: dsNivelesAtencion,
    id:'comboNiveles_Atencion_Id',
    fieldLabel: getLabelFromMap('comboNiveles_AtencionId',helpMap,'Nivel de Atenci&oacute;n'),
    tooltip: getToolTipFromMap('comboNiveles_AtencionId',helpMap,'Listado de Niveles de Atenci&oacute;n'),
    hasHelpIcon:getHelpIconFromMap('comboNiveles_AtencionId',helpMap),
	Ayuda: getHelpTextFromMap('comboNiveles_AtencionId',helpMap),
    //anchor:'80%',
    width:200,
    displayField:'dsNivAtn',
    valueField: 'cdNivAtn',
    hiddenName: 'comboNiveles_Atencion',
    typeAhead: true,
    allowBlank: false,
    triggerAction: 'all',
    mode:'local',
    emptyText:'Seleccionar Nivel de Atencion...',
    selectOnFocus:true,
    forceSelection:true,
    onSelect:function(_record){
    		this.setValue(_record.get("cdNivAtn"));
    		this.collapse();
    		Ext.getCmp("hidden1").setValue(_record.get("cdMatriz"));
    		CDNIVATN = _record.get("cdNivAtn");
    	}
}
);

var cdMatrizHidden = new Ext.form.Hidden({  				                    	
    id: 'hidden1', 
    name: 'cdMatriz'
});

var comboUnidadesTpoResolucion = new Ext.form.ComboBox(
{
    tpl: '<tpl for="."><div ext:qtip="{id}.{texto}" class="x-combo-list-item">{texto}</div></tpl>',
    store: dsUnidad,
    id:'comboUnidadesTpoResolucionId',
    fieldLabel: getLabelFromMap('comboUnidadesTpoResolucionId',helpMap,'Unidad'),
    tooltip: getToolTipFromMap('comboUnidadesTpoResolucionId',helpMap,'Listado de Unidades de Tiempo'),
    hasHelpIcon:getHelpIconFromMap('comboUnidadesTpoResolucionId',helpMap),
	Ayuda: getHelpTextFromMap('comboUnidadesTpoResolucionId',helpMap),
    anchor:'85%',
    //width:200,
    displayField:'texto',
    valueField: 'id',
    hiddenName: 'id',
    typeAhead: true,
    allowBlank: false,
    triggerAction: 'all',
    emptyText:'Seleccionar Unidad...',
    selectOnFocus:true,
    mode:'local',
    forceSelection:true
}
);

var comboUnidadesTpoAlarma = new Ext.form.ComboBox(
{
    tpl: '<tpl for="."><div ext:qtip="{id}.{texto}" class="x-combo-list-item">{texto}</div></tpl>',
    store: dsUnidad,
    id:'comboUnidadesTpoAlarmaId',
    fieldLabel: getLabelFromMap('comboUnidadesTpoAlarmaId',helpMap,'Unidad'),
    tooltip: getToolTipFromMap('comboUnidadesTpoAlarmaId',helpMap,'Listado de Unidades de Tiempo'),
    hasHelpIcon:getHelpIconFromMap('comboUnidadesTpoAlarmaId',helpMap),
	Ayuda: getHelpTextFromMap('comboUnidadesTpoAlarmaId',helpMap),
    anchor:'85%',
    //width:120,
    displayField:'texto',
    valueField: 'id',
    hiddenName: 'id',
    typeAhead: true,
    allowBlank: false,
    triggerAction: 'all',
    emptyText:'Seleccionar Unidad...',
    selectOnFocus:true,
    mode:'local',
    forceSelection:true
}
);


var comboUnidadesTpoEscala = new Ext.form.ComboBox(
{
    tpl: '<tpl for="."><div ext:qtip="{id}.{texto}" class="x-combo-list-item">{texto}</div></tpl>',
    store: dsUnidad,
    id:'comboUnidadesTpoEscalaId',
    fieldLabel: getLabelFromMap('comboUnidadesTpoEscalaId',helpMap,'Unidad'),
    tooltip: getToolTipFromMap('comboUnidadesTpoEscalaId',helpMap,'Listado de Unidades de Tiempo'),
    hasHelpIcon:getHelpIconFromMap('comboUnidadesTpoEscalaId',helpMap),
	Ayuda: getHelpTextFromMap('comboUnidadesTpoEscalaId',helpMap),
    anchor:'85%',
    //width:120,
    displayField:'texto',
    valueField: 'id',
    hiddenName: 'id',
    typeAhead: true,
    allowBlank: false,
    triggerAction: 'all',
    emptyText:'Seleccionar Unidad...',
    selectOnFocus:true,
    mode:'local',
    forceSelection:true
}
);



function guardarTiempo(){
    var conn = new Ext.data.Connection();

    if ((formWindowEdit.findById("txtTiempoResolucionId").getValue()>= 0) && (formWindowEdit.findById("txtTiempoAlarmaId").getValue()>= 0)&& (formWindowEdit.findById("txtTiempoEscalamientoId").getValue()>= 0)){

	        conn.request({
				    	   url: _ACTION_GUARDAR_TIEMPO,
				    	   method: 'POST',
				    	   params: {
				    				  cdmatriz: cdMatriz,
				    				  cdnivatn: formWindowEdit.findById("comboNiveles_Atencion_Id").getValue(),
						    		  tresolucion: formWindowEdit.findById("txtTiempoResolucionId").getValue(),
                                      tresunidad: formWindowEdit.findById("comboUnidadesTpoResolucionId").getValue(),  
						    		  talarma: formWindowEdit.findById("txtTiempoAlarmaId").getValue(),
						    		  talaunidad: formWindowEdit.findById("comboUnidadesTpoAlarmaId").getValue(),
						    		  tescalamiento: formWindowEdit.findById("txtTiempoEscalamientoId").getValue(),
						    		  tescaunidad: formWindowEdit.findById("comboUnidadesTpoEscalaId").getValue(),
						    		  operacion: "0"
				    			    },
	 					   waitMsg : getLabelFromMap('400021', helpMap,'Espere por favor ...'),
                   		   callback: function (options, success, response) {
                           if (Ext.util.JSON.decode(response.responseText).success == false) {
                               Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), Ext.util.JSON.decode(response.responseText).errorMessages[0]);
                              }else {
                                     //Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), 'Los datos se guardaron con exito', function(){reloadGridTiempos(cdMatriz,formWindowEdit.findById("comboNiveles_AtencionId").getValue());});
                                     ///  Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), Ext.util.JSON.decode(response.responseText).actionMessages[0], function(){reloadGridTiempos(/*Ext.getCmp("hidden1").getValue(),*/Ext.getCmp("comboNiveles_AtencionId").getValue())});
                                    
                              	Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), Ext.util.JSON.decode(response.responseText).actionMessages[0], function(){recargarGridNivAtencionEdit(cdMatriz,formWindowEdit.findById("comboNiveles_Atencion_Id").getValue());});
                                        	                              	
                                     _window.close();
                                     
                                    }
                           }
                         })
    }else{
             Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400089', helpMap,'El tiempo debe ser mayor o igual a cero'));
          }
}      


   
    

//CONFIGURACION PARA EL FORMULARIO DE EDICIÓN *****************************
var formWindowEdit = new Ext.FormPanel({
		id:'formWindowEditId',
        //title: '<span style="color:black;font-size:14px;">Tiempos</span>',
               title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('formWindowEditId', helpMap,'Tiempos')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'top',
        frame:true,
        layout: 'table',
		layoutConfig: {columns: 3},
        width: 750,
        height:150,
         items: [{
                
		            			layout: 'form',colspan: 3,
		            			items: [
		            				     comboNiveles_Atencion
		            			       ]
		            		},{
		            			layout: 'form',//colspan: 3,
		            			items: [
		            				     txtTiempoResolucion
		            			       ]
		            			       },{layout: 'form',
                				html:'&nbsp;'},{
		            			layout: 'form',colspan: 3,
		            			items: [
		            				     comboUnidadesTpoResolucion
		            			       ]
		            			       },
		            			       {
		            			layout: 'form',//colspan: 3,
		            			items: [
		            				     txtTiempoAlarma
		            			       ]
		            			       },{layout: 'form',
                				html:'&nbsp;'},
		            			        {
		            			layout: 'form',colspan: 3,
		            			items: [
		            				     comboUnidadesTpoAlarma
		            			       ]
		            			       },
		            			       {
		            			layout: 'form',//colspan: 3,
		            			items: [
		            				     txtTiempoEscalamiento
		            			       ]
		            			       },{layout: 'form',
                				html:'&nbsp;'},
		            			        {
		            			layout: 'form',colspan: 3,
		            			items: [
		            				     comboUnidadesTpoEscala
		            			       ]
		            			       }             			     
		            	],
        		buttons:[{
        							text:getLabelFromMap('ntfcnButtonGuardar',helpMap,'Guardar'),
        							tooltip: getToolTipFromMap('ntfcnButtonGuardar',helpMap,'Guardar datos de Tiempo'),
        							handler: function() {
        							if (formWindowEdit.form.isValid()){
				               			if((transformarMinuto(txtTiempoAlarma.getValue(),comboUnidadesTpoAlarma.getValue())) >= 60){
			                					guardarTiempo();
			        						}   					 					
				   					 		else{
										    	Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400121', helpMap,'El tiempo de alarma debe ser menor a 60 minutos'));
												}
										//guardarTiempo();		
			                				
				               		}else{
                    	              Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
				               		}
				               			
									}
        							},{
        							text:getLabelFromMap('ntfcnButtonRegresar',helpMap,'Regresar'),
        							tooltip: getToolTipFromMap('ntfcnButtonRegresar',helpMap,'Regresar a la pantalla anterior'),                              
        							handler: function() {
        								_window.close();
        							}
        						}]	
		});


 var _window = new Ext.Window({
   	width: 500,
   	height:300,
   	minWidth: 300,
   	minHeight: 100,
   	layout: 'fit',
   	plain:true,
   	bodyStyle:'padding:5px;',
   	buttonAlign:'center',
   	items: formWindowEdit
});





_window.show();

dsNivelesAtencion.load({
                        params: { cdMatriz: cdMatriz }
                       });
//formWindowEdit.form.load();
dsUnidad.load();


};
function transformarMinuto(valor,unidad){
	var aux;
	if (unidad === 'M' || unidad === 'MINUTOS') {
		return valor;
	};
	if (unidad === 'H' || unidad === 'HORAS') {
		aux = valor * 60;
		return aux;
	};
	if (unidad === 'D' || unidad === 'DIAS') {
		aux = valor * 1440;
		return aux;
	};
	if (unidad === 'S' || unidad === 'SEMANAS') {
		aux = valor * 10080;
		return aux;
	};
}


