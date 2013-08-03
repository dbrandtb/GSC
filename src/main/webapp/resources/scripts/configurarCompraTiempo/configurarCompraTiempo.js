//alert(66);

function configurarCompraTiempo(_cdproceso,_cdnivatencion,_nombreTarea){

	
	var cmbNivel = new Ext.form.ComboBox({
	    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	    store:storeNivel,
	    id:'nivelId',
	    name: 'nivelId',
	    displayField:'descripcion',
	    valueField:'codigo',
	    hiddenName: 'codigoh',
	    typeAhead: true,
	    anchor:'90%',
	    allowBlank : false,
	    mode: 'local',
	    triggerAction: 'all',
	    fieldLabel: getLabelFromMap('compraTiempoCboNivel',helpMap,'Nivel'),
	    tooltip:getToolTipFromMap('compraTiempoCboNivel',helpMap,'Seleccione Nivel de Atenci&oacute;n'),
	    labelAlign:'right',
	    selectOnFocus:true,
	    forceSelection:true,
	    emptyText:'...'
	    
       });	

storeNivel.load();


	var cmbUnidad = new Ext.form.ComboBox({
	    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	    store: storeUnidad,
	    id:'unidadId',
	    name: 'unidadId',
	    displayField:'descripcion',
	    valueField:'codigo',
	    hiddenName: 'codigounidadh',
	    typeAhead: true,
	    anchor:'90%',
	    allowBlank : false,
	    mode: 'local',
	    triggerAction: 'all',
	    fieldLabel: getLabelFromMap('compraTiempoCboUnidad',helpMap,'Unidad'),
	    tooltip:getToolTipFromMap('compraTiempoCboUnidad',helpMap,'Seleccione unidad'),
	    labelAlign:'right',
	    selectOnFocus:true,      
	    forceSelection:true,
	    emptyText:'...'
	    
	   });			
	   //console.log(storeUnidad);
storeUnidad.load();

	
var el_form = new Ext.FormPanel ({
			id: 'el_form',
			
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('24',helpMap,'Configurar Compra de Tiempo')+'</span>',
            labelWidth : 110,
            frame : true,
            reader:jsonObtener,
            bodyStyle : 'padding:5px 5px 0',
            bodyStyle:'background: white',
            width : 620,
            autoHeight: true,
            labelAlign:'right',
            waitMsgTarget : true,
        	layout: 'table',
			url:_ACTION_OBTENER_COMPRA_TIEMPO,
			layoutConfig:{
						columns:3
		},
	
		items:[
				{
				layout: "form",
				colspan: 2,
				width:360,
				items:[
				{
						xtype: 'textfield',
						disabled:true,
						fieldLabel: 'Tarea',
						tooltip:'Tarea',
						allowBlank : false,
						width:240,
						labelAlign:'right',
						id: 'dstareaId', 
						name: 'dstarea'
				    	}
				 ]
					
				 },	
		       {		
				layout:"form",
				width:230,
				items:[
						cmbNivel,
						{xtype: 'hidden', name:'tarea', value:_cdproceso}
					]
				},				
				{
				layout:"form",
				width:206,
				items:[{
						xtype: 'numberfield',
						fieldLabel: 'Tiempo Desde',
						tooltip:'Tiempo Desde',
						allowBlank : false,
						anchor:'75%',
						labelAlign:'right',
						id: 'tdesde', 
						maxLength: 3,
						value:1,
						disabled: true
				    	}]
				 },				 
				 {
				layout:"form",
				width:165,
				colspan:1,
				items:[{
						xtype: 'numberfield',
						fieldLabel: 'Hasta',
						tooltip:'Tiempo Hasta',
						allowBlank : false,
						anchor:'90%',
						labelAlign:'right',
						id: 'thasta', 
						name: 'thasta',
						maxLength: 3
				    	}]
				 },				 
				 {
				 layout:"form",
				 width:230,
				 colspan:1,
				 items:[
				 		cmbUnidad
				 		]

				 },
			 	 {
				layout:"form",
				colspan:3,
				items:[{
						xtype: 'numberfield',
						fieldLabel: 'Cantidad Compras',
						tooltip:'Cantidad de compras',
						allowBlank : false,
						anchor:'25%',
						labelAlign:'right',
						id: 'compra', 
						name: 'compra',
						maxLength: 3
				    	}]
				 }
			 ]
	});

var ventana = new Ext.Window ({
		title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('96',helpMap,'Definici&oacute;n de Tareas')+'</span>',
		width: 625,
		autoHeight: true,
        modal:true,
    	items: [el_form],
        buttonAlign:'center',
        buttons: [ {
		        text:getLabelFromMap('BtnGuardar',helpMap,'Guardar'),
		        tooltip: getToolTipFromMap('BtnGuardar',helpMap,'Guardar'),
				handler: function(){
                if (el_form.form.isValid()) {
	                if(validarNumDesde()){
	                	el_form.form.submit({
	                        url : _ACTION_GUARDAR_COMPRA_TIEMPO,
	                        success : function(form, action) {
	                            Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0]);
	                           // el_form.getForm().reset();
	                           // window.close();                                     
	                        },
	                        failure : function(form, action){
	                            Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0]);
	                       },
	                       	waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
	                        waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')
	                    });
	                }                    
                } else {
                    Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
                }
            }
						},
						
						/*{
		                    text:getLabelFromMap('compraBtnAdd',helpMap,'Agregar'),
		                    tooltip: getToolTipFromMap('compraBtnAdd',helpMap,'Agregar'),
							handler: function(){//el_form.form.reset()
							Ext.getCmp('tdesde').setValue("1");
							Ext.getCmp('thasta').setValue("");
							Ext.getCmp('compra').setValue("");
							//Ext.getCmp('dstarea').setValue("");
							Ext.getCmp('unidadId').setValue("");
							Ext.getCmp('nivelId').setValue("");
							storeNivel.load();
							storeUnidad.load();
							}
						},
		               { 
		                  text:getLabelFromMap('compraBtnDel',helpMap,'Borrar'),
		                  tooltip:getToolTipFromMap('compraBtnDel',helpMap, 'Borrar'),
		                    handler:function(){borrar(_cdproceso)} // pv_cdproceso_i,pv_cdnivatn_i
		                    
		               	},*/
					    { 
		                    text:getLabelFromMap('compraBtnBack',helpMap,'Regresar'),
		                    tooltip: getToolTipFromMap('compraBtnBack',helpMap,'Regresa a la pantalla anterior'),
							handler: function(){ventana.close()}
						}
				 
			]

        
        
});

function validarNumDesde(){
		if(el_form.findById('thasta').getValue()!=""){
   			if (eval(el_form.findById('tdesde').getValue()) > eval(el_form.findById('thasta').getValue())){
   			       Ext.Msg.alert('Informaci&oacute;n', 'El campo Tiempo Hasta debe ser mayor a 1');
  			       return false;
   				} else {
   					return true;
   					}
   		    }
   		    else{
   			       Ext.Msg.alert('Aviso', 'El campo Tiempo Hasta no puede estar Vacio.');   
   			       return false;		    
   		    }
   		return true;
   };


ventana.show();
el_form.load ({
 
 	params: {
 				//alert(record),
 	 			 tarea: _cdproceso, 
                 codigoh:_cdnivatencion
 	},
 	callback: function (record,opt,success) {
 		alert(success);
        if (Ext.util.JSON.decode(response.responseText).success == false) {
       		    
        	}else {
            //Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), 'Los datos se guardaron con exito', function(){reloadGrid();});
            //Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), 'Los datos se guardaron con exito');
        }
    }
 }
 );

el_form.findById("dstareaId").setValue(_nombreTarea);
	
/********* Fin del form ************************************/
	
	
	
function borrar(_cdproceso) {
	
		//alert(_cdproceso);
		//alert(Ext.getCmp('nivelId').getValue());
		 
		
		//if(_record )
		if(_cdproceso != "")
		{
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
			{
		        if (btn == "yes")
		        {
         			var _params = {
         						
         						 tarea:_cdproceso,
         						 codigoh:Ext.getCmp('nivelId').getValue()
         						
         			};
         			execConnection(_ACTION_BORRAR_COMPRA_TIEMPO, _params, cbkConnection)
               }
			})
		}else{
				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'))
		}
};


function cbkConnection (_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message)
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message)
	}
}   
				
};			// fin onReady()

			