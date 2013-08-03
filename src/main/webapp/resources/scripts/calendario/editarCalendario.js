function editar(record,pais,mes,anio) {

	var xPais= pais;
	var xMes= mes;
	var xAnio= anio;

    var calendario_reg = new Ext.data.JsonReader({
						root: 'MCalendarioList',
						totalProperty: 'totalCount',
						successProperty: '@success'
						}, [
						{name: 'dias', type: 'string', mapping: 'dia'},
						{name: 'dsDia', type: 'string', mapping: 'descripcionDia'}
						/*{name: 'codPais', type: 'string', mapping: 'codPais'},
						{name: 'yearCabecera', type: 'string', mapping: 'yearCabecera'},
						{name: 'codMes', type: 'string', mapping: 'codMes'}*/
						]
		);

	var dsDiaStore = new Ext.data.Store({
	        proxy: new Ext.data.HttpProxy({
	            url: _ACTION_OBTENER_CALENDARIO 
	            }),
	        reader: new Ext.data.JsonReader({
	        root: 'MCalendarioList',
	        id: 'calendarioListId'
	        },[
	       {name: 'dias', mapping:'dia', type: 'string'},
	       {name: 'dsDia', mapping:'descripcionDia', type: 'string'}
	    ])
	});		

	var numDia = new Ext.form.NumberField({
        fieldLabel: getLabelFromMap('agCalendarNFnumDia',helpMap,'D&iacute;a'),
        tooltip:getToolTipFromMap('agCalendarNFnumDia',helpMap,'D&iacute;a calendario'), 
        id: 'nDiaId', 
        name: 'dias',
        disable:true,
        //value: xDia,
        maxLength: 2,
        anchor: '80%',
        readOnly:'true'
		});
		
		
	var descDia = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('agCalendarTxtdsDia',helpMap,'Descripci&oacute;n'),
        tooltip:getToolTipFromMap('agCalendarTxtdsDia',helpMap,'Descripci&oacute;n del feriado'), 
        id: 'dsDiaId', 
        name: 'dsDia',
        maxLength: 30,
        anchor: '115%'
		});


	var form_ed = new Ext.FormPanel ({
        labelWidth : 100,
        url : _ACTION_OBTENER_CALENDARIO,
        frame : true,
        bodyStyle : 'padding:5px 5px 0',
        bodyStyle:'background: white',
        width : 500,
        autoHeight: true,
        waitMsgTarget : true,
        layout: 'form',
        buttonAlign: "center",
        labelAlign: 'right',
        layout: 'table',
		frame:true,
		reader:calendario_reg,
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
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('105',helpMap,'Editar d&iacute;as NO laborales ')+'</span>',
			width: 500,
			autoHeight: true,
			//bodyStyle : 'padding:5px 5px 0',
        	bodyStyle:'padding:5px;',
        	bodyStyle:'background: white',
        	buttonAlign:'center',
        	items: form_ed,
            //se definen los botones del formulario
            buttons : [ {

                text : getLabelFromMap('agCalendarBtnSave',helpMap,'Guardar'),
				tooltip:getToolTipFromMap('agCalendarBtnSave',helpMap,'Guarda d&iacute;a NO laboral'),

                disabled : false,

                handler : function() {

                    if (form_ed.form.isValid()) {
                    	//if(validarDiaMes()==true){
					
							guardarDiaFeriado();
							window.close();
					
						//}
					
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

	
	 form_ed.form.load ({
 
 	params: {
 				//alert(record),
 	 			 dia: record.get('dias'), 
                 descripcionDia: record.get('dsDia'),
                 codPais:xPais,
                 yearCabecera:xAnio,
                 codMes:xMes
                 
 		
 	}
 	/*
 	success: function(form, action){
         //console.log(action.result);
         var cdIndi_Envio = action.result.data.nmDesde  //¿?¿?
        // alert(cdIndi_Envio)
        }*/
 });

    window.show();
    
    //alert(dias);
   // alert(dsDia);

function guardarDiaFeriado()
{
        var params = {
                 codPais: xPais,
                 yearCabecera: xAnio,
                 codMes: xMes,  
                 dia: Ext.getCmp('nDiaId').getValue(), 
                 descripcionDia: Ext.getCmp('dsDiaId').getValue()
                 
            
             };
        execConnection (_ACTION_EDITAR_CALENDARIO, params, cbkGuardar)
}

function cbkGuardar (success, message) {
    if (success) {
          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), message,function(){reloadGrid();})
    } else {
          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), message);
    	}
	}              

} 	//fin de la funcion

