function agregar(xpais,xanio,xmes) {

	var numDia = new Ext.form.NumberField({
        fieldLabel: getLabelFromMap('agCalendarNFnumDia',helpMap,'D&iacute;a'),
        tooltip:getToolTipFromMap('agCalendarNFnumDia',helpMap,'D&iacute;a calendario'), 
        id: 'nDiaId', 
        name: 'nDia',
        maxLength: 2,
        allowBlank: false,
        anchor: '80%'
		});
		
		
	var descDia = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('agCalendarTxtdsDia',helpMap,'Descripci&oacute;n'),
        tooltip:getToolTipFromMap('agCalendarTxtdsDia',helpMap,'Descripci&oacute;n del feriado'), 
        id: 'dsDiaId', 
        name: 'dsDia',
        maxLength: 30,
        anchor: '115%'
		});


	var form_ag = new Ext.FormPanel ({
        labelWidth : 100,
        url : _ACTION_AGREGAR_GUARDAR_CALENDARIO,
        frame : true,
        bodyStyle : 'padding:5px 5px 0',
        bodyStyle:'background: white',
        width : 600,
        autoHeight: true,
        waitMsgTarget : true,
        layout: 'form',
        buttonAlign: "center",
        labelAlign: 'right',
        layout: 'table',
		frame:true,
		layoutConfig:{
						columns:3
		},
		items:[{
				layout:"form",
				colspan:1,
				width: 170, 
				items:[
						numDia
						]
					
				 },
				 {
				 layout:"form",
				 colspan:2,
				 items:[
				 		descDia
				 		]
				 	
				 }
			]
        
    });

	var window = new Ext.Window ({
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('105',helpMap,'Agregar d&iacute;as NO laborales ')+'</span>',
			width: 500,
			autoHeight: true,
			//bodyStyle : 'padding:5px 5px 0',
        	bodyStyle:'padding:5px;',
        	bodyStyle:'background: white',
        	buttonAlign:'center',
        	items: form_ag,
        	modal:true,
            //se definen los botones del formulario
            buttons : [ {

                text : getLabelFromMap('agCalendarBtnSave',helpMap,'Guardar'),
				tooltip:getToolTipFromMap('agCalendarBtnSave',helpMap,'Guarda d&iacute;a NO laboral'),

                disabled : false,

                handler : function() {

                    if (form_ag.form.isValid()) {
                    	if(validarDiaMes()==true){	
							guardarDiaFeriado();	
							window.close();
						}
					
                    } else {

                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));

                    }

                }
                },
            	{

                text : getLabelFromMap('agCalendarBtnSaveBack',helpMap,'Regresar'),
				tooltip:getToolTipFromMap('agCalendarBtnSaveBack',helpMap,'Regresa a la pantalla anterior'),

                handler : function() {
                    window.close();
                		}
                }
         ]

	});

    window.show();

function guardarDiaFeriado()
{
        var params = {
                 codPais: xpais,
                 yearCabecera: xanio,
                 codMes: xmes,
                 dia: form_ag.findById('nDiaId').getValue(), 
                 descripcionDia: form_ag.findById('dsDiaId').getValue()
            
             };
        execConnection (_ACTION_AGREGAR_GUARDAR_CALENDARIO, params, cbkGuardar);
}

function cbkGuardar (success, message) {
    if (success) {
          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), message,function(){reloadGrid();})
    } else {
          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), message);
    }

}
       
//***

function validarDiaMes(){
		if((form_ag.findById('nDiaId').getValue() <= 0) || (form_ag.findById('nDiaId').getValue()>31)){
  		        Ext.Msg.alert('Aviso', ' Error: Dia del mes fuera de rango o el valor no es valido');   
   			    return false		    
   		    }
   		    else{
		   		return true
   		    	
   		    }
   }
       
       
 } 	//fin de la funcion

