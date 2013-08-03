// Funcion de Agregar Desglose de Polizas
function editarConfiguracionEncuesta(record) {
	
var jsonAsignaEncuesta= new Ext.data.JsonReader(
{
root:'MEstructuraList',
totalProperty: 'totalCount',
successProperty : '@success'
},
[
{name: 'nmConfig',  mapping:'nmConfig',  type: 'string'},
{name: 'cdCampan',  mapping:'cdCampan',  type: 'string'},
{name: 'cdElemento',  mapping:'cdElemento',  type: 'string'},
{name: 'cdEncuesta',  mapping:'cdEncuesta',  type: 'string'},
{name: 'cdModulo',  mapping:'cdModulo',  type: 'string'},
{name: 'cdPerson',  mapping:'cdPerson',  type: 'string'},
{name: 'cdProceso',  mapping:'cdProceso',  type: 'string'},
{name: 'cdRamo',  mapping:'cdRamo',  type: 'string'},
{name: 'cdUnieco',  mapping:'cdUnieco',  type: 'string'},
{name: 'campan',  mapping:'dsCampan',  type: 'string'},
{name: 'ctaCliente',  mapping:'dsElemen',  type: 'string'},
{name: 'encuesta',  mapping:'dsEncuesta',  type: 'string'},
{name: 'modulo',  mapping:'dsModulo',  type: 'string'},
{name: 'operacion',  mapping:'dsProceso',  type: 'string'},
{name: 'producto',  mapping:'dsRamo',  type: 'string'},
{name: 'dsUniEco',  mapping:'dsUnieco',  type: 'string'},
{name: 'estado',  mapping:'estado',  type: 'string'},
{name: 'fedesde_i',  mapping:'fedesde_i',  type: 'string'},
{name: 'fehasta_i',  mapping:'fehasta_i',  type: 'string'},
{name: 'nmPoliza',  mapping:'nmPoliza',  type: 'string'}
]
);
var store_reg = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: _ACTION_GET_CONFIGURACION_ENCUESTAS}),
		reader: jsonAsignaEncuesta
	});

//se define el formulario
var formPanel = new Ext.FormPanel ({
            labelWidth : 100,
            bodyStyle : 'padding:5px 5px 0',
            width : 350,
            defaults : {width : 200 },
            defaultType : 'textfield',
            labelAlign:'right',
            url:_ACTION_GET_CONFIGURACION_ENCUESTAS,
            store:store_reg,
            //store:storeAsignaEncuesta,
            reader:jsonAsignaEncuesta,
            //se definen los campos del formulario
            items : [
                {
                    xtype: 'hidden',
                    name : 'cdPerson',
                    id: 'cdPerson'
                },{
                    xtype: 'hidden',
                    name : 'swSubInc',
                    id: 'swSubInc'
                },
                {
                  xtype: 'textfield',fieldLabel: getLabelFromMap('txtdsUniEco',helpMap,'Aseguradora'),
                  tooltip:getToolTipFromMap('txtdsUniEco',helpMap,'Aseguradora'), 
                   hasHelpIcon:getHelpIconFromMap('txtdsUniEco',helpMap),
	               Ayuda: getHelpTextFromMap('txtdsUniEco',helpMap,''),    
                  id: 'dsUniEco', 
                  name: 'dsUniEco',
                  readOnly:true,
                  disabled: true 
                  
                },
                {
                  xtype: 'textfield',fieldLabel: getLabelFromMap('txtProducto',helpMap,'Producto'),
                  tooltip:getToolTipFromMap('txtProducto',helpMap,'Producto'), 
                  hasHelpIcon:getHelpIconFromMap('txtProducto',helpMap),
	              Ayuda: getHelpTextFromMap('txtProducto',helpMap,''),    
                  id: 'productoId', 
                  name: 'producto',
                  readOnly:true,
                  disabled: true 
	             },

                {
                  xtype: 'textfield',fieldLabel: getLabelFromMap('txtCtaCliente',helpMap,'Cuenta/Cliente'),
                  tooltip:getToolTipFromMap('txtCtaCliente',helpMap,'Cuenta/Cliente'), 
                  hasHelpIcon:getHelpIconFromMap('txtCtaCliente',helpMap),
	              Ayuda: getHelpTextFromMap('txtCtaCliente',helpMap,''),    
                  id: 'ctaClienteId', 
                  name: 'ctaCliente',
                  readOnly:true,
                  disabled: true 
	                            		        
                } ,
                 {
                  xtype: 'textfield',fieldLabel: getLabelFromMap('txtOperacion',helpMap,'Operaci&oacute;n'),
                  tooltip:getToolTipFromMap('txtOperacion',helpMap,'Operaci&oacute;n'), 
                  hasHelpIcon:getHelpIconFromMap('txtOperacion',helpMap),
	              Ayuda: getHelpTextFromMap('txtOperacion',helpMap,''),    
                  id: 'operacionId', 
                  name: 'operacion',
                  readOnly:true,
                  disabled: true 
                },
                {
                  xtype: 'textfield',fieldLabel: getLabelFromMap('txtCampan',helpMap,'Campaña'),
                  tooltip:getToolTipFromMap('txtCampan',helpMap,'Campaña'), 
                   hasHelpIcon:getHelpIconFromMap('txtCampan',helpMap),
	              Ayuda: getHelpTextFromMap('txtCampan',helpMap,''),    
                  id: 'campanId', 
                  name: 'campan',
                  readOnly:true,
                  disabled: true 
	                            					
                },
                {
                  xtype: 'textfield',fieldLabel: getLabelFromMap('txtModulo',helpMap,'M&oacute;dulo'),
                  tooltip:getToolTipFromMap('txtModulo',helpMap,'M&oacute;dulo'), 
                   hasHelpIcon:getHelpIconFromMap('txtModulo',helpMap),
	              Ayuda: getHelpTextFromMap('txtModulo',helpMap,''),    
                  id: 'moduloId', 
                  name: 'modulo',
                  readOnly:true,
                  disabled: true 
                },
                {
                  xtype: 'textfield',fieldLabel: getLabelFromMap('txtEncuesta',helpMap,'Encuesta'),
                  tooltip:getToolTipFromMap('txtEncuesta',helpMap,'Encuesta'), 
                   hasHelpIcon:getHelpIconFromMap('txtEncuesta',helpMap),
	              Ayuda: getHelpTextFromMap('txtEncuesta',helpMap,''),    
                  id: 'encuestaId', 
                  name: 'encuesta',
                  readOnly:true,
                  disabled: true 
                },
                {
                    xtype: 'datefield', 
                    id: 'fedesde_i', 
		            fieldLabel: getLabelFromMap('dtFeDesde',helpMap,'Fecha Desde'),
		            tooltip: getToolTipFromMap('dtFeDesde',helpMap,'Fecha Desde'), 
		            hasHelpIcon:getHelpIconFromMap('dtFeDesde',helpMap),
	              	Ayuda: getHelpTextFromMap('dtFeDesde',helpMap,''),                       				    
                    name: 'fedesde_i',
                    width:100,
                    altFormats : "m/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g",
                    format:'d/m/Y'
                    
                },
                {
                    xtype: 'datefield', 
                    id: 'fehasta_i', 
		            fieldLabel: getLabelFromMap('nmTarj',helpMap,'Fecha Hasta'),
		            tooltip: getToolTipFromMap('nmTarj',helpMap,'Fecha Hasta'),      
		            hasHelpIcon:getHelpIconFromMap('nmTarj',helpMap),
	              	Ayuda: getHelpTextFromMap('nmTarj',helpMap,''),                  				    
                    name: 'fehasta_i',
                    width:100,
                    altFormats : "m/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g",
                    format:'d/m/Y'
                }  
           ]
        });


 function guardarConfiguracionEncuesta(){
    var conn = new Ext.data.Connection();
	                conn.request({
				    	url: _ACTION_GUARDAR_CONFIGURACION_ENCUESTAS_EDITAR,
				    	method: 'POST',
				    	params: {
				    				nmConfig:record.get('nmConfig'),
				    				cdUnieco: record.get('cdUnieco'),
				    				cdRamo:record.get('cdRamo'),
						    		cdElemento: record.get('cdElemento'),
                                    cdProceso: record.get('cdProceso'),
						    		cdCampan:record.get('cdCampan'),
						    		cdModulo:record.get('cdModulo'),
						    		cdEncuesta: record.get('cdEncuesta'),
						    		fedesde_i: formPanel.findById("fedesde_i").getRawValue(),
						    		fehasta_i: formPanel.findById("fehasta_i").getRawValue()
						    		
				    			},
	 					waitMsg : getLabelFromMap('400021', helpMap,'Espere por favor ...'),
                   		     callback: function (options, success, response) {
        if (Ext.util.JSON.decode(response.responseText).success == false) {
            Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), 'No se pudo guardar los datos');
        }else {
             Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), 'Los datos se guardaron con exito',function(){reloadGrid()});
             window.close();
        }
    }
 })
} 


//Windows donde se van a visualizar la pantalla
var window = new Ext.Window({
       id: 'AdminiAsigEncuestEditWindow',
      //title: 'Administrar Asignaci&oacute;n Encuesta',
	   title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('AdminiAsigEncuestWindow', helpMap,'Administrar Asignaci&oacute;n Encuestas')+'</span>',
        width: 500,
        height:320,
        minWidth: 300,
        minHeight: 100,
        modal: true,
        layout: 'fit',
        plain:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: formPanel,
        //se definen los botones del formulario
            buttons : [ {
                //text : 'Guardar',
                text:getLabelFromMap('btnGuardarEd',helpMap,'Guardar'),
                tooltip: getToolTipFromMap('btnGuardarEd',helpMap,'Guardar'), 
                disabled : false,
                handler : function() {
                   	guardarConfiguracionEncuesta();
                }
            },
             {
                 //text : 'Regresar',
                  text:getLabelFromMap('btnREgresarEd',helpMap,'Regresar'),
                 tooltip: getToolTipFromMap('btnREgresarEd',helpMap,'Regresar'),
                 handler : function() {
                 window.close();
                    }
            }]
    	});

    	
formPanel.load({
    params: {
    	      pv_nmconfig_i: record.get('nmConfig'),
			  pv_cdproceso_i: record.get('cdProceso'),
			  pv_cdcampan_i:  record.get('cdCampan'),
			  pv_cdmodulo_i:record.get('cdModulo'),
			  pv_cdencuesta_i:record.get('cdEncuesta')
    	},
    waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
    waitMsg : getLabelFromMap('400028',helpMap,'Leyendo datos ...')	
});
  window.show();
  //alert(store_reg.reader.jsonData.MEstructuraList[0]);
 // console.log(store_reg);

};
//alert(store_reg.reader.jsonData.MEstructuraList[0]);
function feFormato(val) {
		try{
		var fecha = new Date();
		fecha = Date.parseDate(val, 'Y-m-d H:i:s.g');
	
                    //alert("Valor: " + val + "\nFecha: " + fecha + "\nformateada : " + fecha.format('d/m/Y'));
                var _val2 = val.format ('Y-m-d H:i:s.g');
                   // alert(_val2);
               return _val2.format('d/m/Y');
               }
              catch(e)
              {
              	return fecha.format('d/m/Y');
              }
         };

