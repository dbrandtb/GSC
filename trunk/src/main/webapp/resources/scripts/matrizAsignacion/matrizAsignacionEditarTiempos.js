function editarTiempos(record){
 
 //console.lang(record);


var cdmatriz = new Ext.form.TextField({
              name: 'cdmatriz',
              id:'cdmatrizId',
              labelSeparator:'',
              hidden:true
          });
    
 var cdnivatn = new Ext.form.TextField({
              name: 'cdnivatn',
              id:'cdnivatnId',
              labelSeparator:'',
              hidden:true
          });   
    
   
var dsNivelAtencion = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsNivelAtencionIdEdTmp',helpMap,'Nivel Atenci&oacute;n'),
        tooltip:getToolTipFromMap('dsNivelAtencionIdEdTmp',helpMap,'Nivel Atenci&oacute;n'),
        hasHelpIcon:getHelpIconFromMap('dsNivelAtencionIdEdTmp',helpMap),
		Ayuda: getHelpTextFromMap('dsNivelAtencionIdEdTmp',helpMap),
        id: 'dsNivelAtencionIdEdTmp', 
        name: 'dsnivatn',
        disabled:true,
        //anchor: '80%'
        width:200
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
		  ])
});




		 		 	
var txtTiempoResolucion= new Ext.form.NumberField({
   	fieldLabel: getLabelFromMap('txtTiempoResolucionId', helpMap,'Tiempo de Resoluci&oacute;n'), 
	tooltip: getToolTipFromMap('txtTiempoResolucionId', helpMap,'Tiempo de Resoluci&oacute;n'),
	hasHelpIcon:getHelpIconFromMap('txtTiempoResolucionId',helpMap),
	Ayuda: getHelpTextFromMap('txtTiempoResolucionId',helpMap),
    id: 'txtTiempoResolucionId', 
    name: 'tresolucion',
     anchor:'80%'
     //width:150
});
    
var txtTiempoAlarma = new Ext.form.NumberField({
   	fieldLabel: getLabelFromMap('txtTiempoAlarmaId', helpMap,'Tiempo de Alarma'), 
	tooltip: getToolTipFromMap('txtTiempoAlarmaId', helpMap,'Tiempo de Alarma'),
	hasHelpIcon:getHelpIconFromMap('txtTiempoAlarmaId',helpMap),
	Ayuda: getHelpTextFromMap('txtTiempoAlarmaId',helpMap),
    id: 'txtTiempoAlarmaId', 
    allowBlank:false,
    name: 'talarma',
    anchor:'80%'
    //width:150
});

var txtTiempoEscalamiento = new Ext.form.NumberField({
   	fieldLabel: getLabelFromMap('txtTiempoEscalamientoId', helpMap,'Tiempo de Escalamiento'), 
	tooltip: getToolTipFromMap('txtTiempoEscalamientoId', helpMap,'Tiempo de Escalamiento'),
	hasHelpIcon:getHelpIconFromMap('txtTiempoEscalamientoId',helpMap),
	Ayuda: getHelpTextFromMap('txtTiempoEscalamientoId',helpMap),
    id: 'txtTiempoEscalamientoId',
    allowBlank:false, 
    name: 'tescalamiento',
    anchor:'80%'
     //width:150
});

//LOS COMBOS
var comboUnidadesTpoResolucion = new Ext.form.ComboBox(
{
    tpl: '<tpl for="."><div ext:qtip="{id}.{texto}" class="x-combo-list-item">{texto}</div></tpl>',
    store: dsUnidad,
    id:'comboUnidadesTpoResolucionId',
    fieldLabel: getLabelFromMap('comboUnidadesTpoResolucionId',helpMap,'Unidad'),
    tooltip: getToolTipFromMap('comboUnidadesTpoResolucionId',helpMap,'Listado de Unidades de Tiempo'),
    hasHelpIcon:getHelpIconFromMap('comboUnidadesTpoResolucionId',helpMap),
	Ayuda: getHelpTextFromMap('comboUnidadesTpoResolucionId',helpMap),
    //anchor:'80%',
    width:150,
    displayField:'texto',
    valueField: 'id',
    hiddenName: 'tresunidad',
    typeAhead: true,
    allowBlank: false,
    triggerAction: 'all',
    emptyText:'Seleccionar Unidad...',
    selectOnFocus:true,
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
    //anchor:'80%',
    width:150,
    displayField:'texto',
    valueField: 'id',
    hiddenName: 'talaunidad',
    typeAhead: true,
    allowBlank: false,
    triggerAction: 'all',
    emptyText:'Seleccionar Unidad...',
    selectOnFocus:true,
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
    //anchor:'80%',
    width:150,
    displayField:'texto',
    valueField: 'id',
    hiddenName: 'tescaunidad',
    typeAhead: true,
    allowBlank: false,
    triggerAction: 'all',
    emptyText:'Seleccionar Unidad...',
    selectOnFocus:true,
    forceSelection:true
}
);



function guardarTiempo(){
    var conn = new Ext.data.Connection();
//  if ((formWindowEdit.findById("txtTiempoAlarmaId").getValue()!="") && (formWindowEdit.findById("txtTiempoEscalamientoId").getValue()!="")){
    if (formWindowEdit.form.isValid()){
	                conn.request({
				    	url: _ACTION_GUARDAR_TIEMPO,
				    	method: 'POST',
				    	params: {
				    				cdmatriz: formWindowEdit.findById("cdmatrizId").getValue(),
				    				cdnivatn: formWindowEdit.findById("cdnivatnId").getValue(),
				    				tresolucion: formWindowEdit.findById("txtTiempoResolucionId").getValue(),
                                    tresunidad: formWindowEdit.findById("comboUnidadesTpoResolucionId").getValue(),  
						    		talarma: formWindowEdit.findById("txtTiempoAlarmaId").getValue(),
						    		talaunidad: formWindowEdit.findById("comboUnidadesTpoAlarmaId").getValue(),
						    		tescalamiento: formWindowEdit.findById("txtTiempoEscalamientoId").getValue(),
						    		tescaunidad: formWindowEdit.findById("comboUnidadesTpoEscalaId").getValue(),
						    		operacion: "1" 
				    			},
	 					waitMsg : getLabelFromMap('400021', helpMap,'Espere por favor ...'),
                   		     callback: function (options, success, response) {
        if (Ext.util.JSON.decode(response.responseText).success == false) {
            Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), Ext.util.JSON.decode(response.responseText).errorMessages[0]);
        }else {
             //Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), 'Los datos se guardaron con exito', function(){reloadGridTiempos();});
             //SE REEMPLAZA LA LINEA DE CODIGO DE ARRIBA PARA QUE CARGUE LA GRILLA DE ATENCION CON EL NIVEL SELEECIONADO PARA ADICION
             Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), Ext.util.JSON.decode(response.responseText).actionMessages[0], function(){recargarGridNivAtencionEdit(formWindowEdit.findById("cdmatrizId").getValue(),formWindowEdit.findById("cdnivatnId").getValue());});
            
             _window.close();
            //codNoti=Ext.util.JSON.decode(response.responseText).cdNotificacion;
            //guardarProcesosNoti(codNoti);
        }
    }
 })
 
 }else{
     Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
}      

}

 //se define la forma en que se van a leer los datos que envie la action
        var _jsonFormReader = new Ext.data.JsonReader( {

            //indica el arreglo a leer
            root : 'MEstructuraTiemposList',

            //indica la cantidad de registro que debe leerse
            totalProperty: 'total',

            //indica el resultado de la respuesta de la action
            successProperty : 'success'

        }, [ 
               {name : 'cdmatriz',mapping : 'cdmatriz',type : 'string'},
               {name : 'cdnivatn', mapping : 'cdnivatn', type : 'string'},
               {name : 'dsnivatn', mapping : 'dsnivatn', type : 'string'},
               {name : 'talarma',mapping : 'talarma',type : 'string'},
               {name : 'talaunidad', mapping : 'talaunidad', type : 'string'},
               {name : 'tescalamiento',mapping : 'tescalamiento',type : 'string'},
               {name : 'tescaunidad', mapping : 'tescaunidad', type : 'string'},
               {name : 'tresolucion', mapping : 'tresolucion', type : 'string'},
               {name : 'tresunidad',mapping : 'tresunidad',type : 'string'}
        
        
        ]);
    
    
   
    

//CONFIGURACION PARA EL FORMULARIO DE EDICIÓN *****************************
var formWindowEdit = new Ext.FormPanel({
		id:'formWindowEditIdTiempoEd',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('formWindowEditIdTiempoEd', helpMap,'Tiempos')+'</span>',
		//title: '<span style="color:black;font-size:14px;">Tiempos</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'top',
        frame:true,
        
       //action a invocar al hacer al cargar(load) del formulario
            url : _ACTION_GET_TIEMPO,

            baseParams : {codigoMatriz: record.get("cdmatriz"),
                          nivAtencion: record.get("cdnivatn")
                         
                         },
        reader:_jsonFormReader,
        layout: 'table',
		layoutConfig: {columns: 3},
        width: 750,
        height:150,
         items: [{
                
		            			layout: 'form',colspan: 3,
		            			items: [
		            				     dsNivelAtencion
		            			       ]
		            		},{
		            			layout: 'form',//colspan: 3,
		            			items: [
		            				     txtTiempoResolucion
		            			       ]
		            			       },{layout: 'form',
                				html:'&nbsp;'},
		            			       	{
		            			layout: 'form',colspan: 2,
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
		            			layout: 'form',colspan: 2,
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
		            			layout: 'form',colspan: 2,
		            			items: [
		            				     comboUnidadesTpoEscala,
		            				     cdmatriz,
		            				     cdnivatn
		            			       ]
		            			       },
		            			        {
		            			layout: 'form',colspan: 2,
		            			items: [
		            				     cdmatriz,
		            				     cdnivatn
		            			       ]
		            			       }                 			     
		            	],
        		buttons:[{
        							text:getLabelFromMap('ntfcnButtonGuardarEdTmp',helpMap,'Guardar'),
        							tooltip: getToolTipFromMap('ntfcnButtonGuardarEdTmp',helpMap,'Guardar datos de Tiempo'),
        							handler: function() {
				               			
				               			if((transformarMinuto(txtTiempoAlarma.getValue(),comboUnidadesTpoAlarma.getValue())) >= 60){
			                					guardarTiempo();
			        							
				                			}   					 					
				   					 		else{
										    Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400121', helpMap,'El tiempo de alarma debe ser menor a 60 minutos'));
												}
				               			
				               			//guardarTiempo();
				               			
									}
        							},{
        							text:getLabelFromMap('ntfcnButtonRegresarEdTmp',helpMap,'Regresar'),
        							tooltip: getToolTipFromMap('ntfcnButtonRegresarEdTmp',helpMap,'Regresar a la pantalla anterior'),                              
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


 dsUnidad.load({callback:function(record,opt,success){formWindowEdit.form.load();}}); 
      
_window.show();




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
