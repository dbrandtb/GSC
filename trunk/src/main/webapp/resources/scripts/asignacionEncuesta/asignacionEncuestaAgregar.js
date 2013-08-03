// Funcion de Agregar Desglose de Polizas
function agregarAsignacionEncuesta(record) {

//console.log(record);

var jsonAsignaEncuesta= new Ext.data.JsonReader(
{
root:'MAsignaEncuestaList',
totalProperty: 'totalCount',
successProperty : '@success'
},
[
{name: 'nmConfig',  mapping:'nmConfig',  type: 'string'},
{name: 'dsUniEco',  mapping:'dsUnieco',  type: 'string'},
{name: 'nmPoli',  mapping:'nmPoliza',  type: 'string'},
{name: 'dsUsuari',  mapping:'dsUsuari',  type: 'string'},
{name: 'estado',  mapping:'estado',  type: 'string'},
{name: 'dsRamo',  mapping:'dsRamo',  type: 'string'},
{name: 'cdPerson',  mapping:'cdPerson',  type: 'string'},
{name: 'cdUsuari',  mapping:'cdUsuario',  type: 'string'},
{name: 'dsPersona',  mapping:'dsNombre',  type: 'string'}


]
);

	
      var dsUsuarios = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_USUARIOS_2}),
						reader: new Ext.data.JsonReader({
								root: 'comboUsuarioResponsable',
								id: 'cdUsuari',
								successProperty: '@success'
							}, [
								{name: 'cdUsuari', type: 'string', mapping: 'cdusuario'},
								{name: 'dsUsuari', type: 'string', mapping: 'dsusuari'} 
							]),
						remoteSort: true
				});


  
 
//se define el formulario
var formPanel = new Ext.FormPanel ({
            labelWidth : 100,
            bodyStyle : 'padding:5px 5px 0',
            width : 350,
            defaults : {width : 200 },
            defaultType : 'textfield',
            labelAlign:'right',
            url:_ACTION_GET_ASIGNACION_ENCUESTAS,
            reader:jsonAsignaEncuesta,
            items : [
                {
                    xtype: 'hidden',
                    name : 'cdPerson',
                    id: 'cdPerson'
                }, {
                    xtype: 'hidden',
                    name : 'cdModulo',
                    id: 'cdModuloId'
                },{
                    xtype: 'hidden',
                    name : 'cdUsuari',
                    id: 'cdUsuarioId'
                },
                	{
                    xtype: 'hidden',
                    name : 'swSubInc',
                    id: 'swSubInc'
                },
                {
                   xtype: 'textfield', 
                   width: 250, 
			       fieldLabel: getLabelFromMap('desUniEcoId',helpMap,'Aseguradora'),
			       tooltip:getToolTipFromMap('desUniEcoId',helpMap,'Aseguradora'), 
			       hasHelpIcon:getHelpIconFromMap('desUniEcoId',helpMap),
			       Ayuda:getHelpTextFromMap('desUniEcoId',helpMap),
                   //fieldLabel: "Aseguradora", 
                   disabled: true, 
                   name: 'dsUniEco',
                   id: 'desUniEcoId',
                   readOnly:true
                },
                {
                    xtype: 'textfield', 
                    width: 250,  
			        fieldLabel: getLabelFromMap('dsProductoId',helpMap,'Producto'),
			        tooltip:getToolTipFromMap('dsProductoId',helpMap,'Producto'), 
			        hasHelpIcon:getHelpIconFromMap('dsProductoId',helpMap),
			        Ayuda:getHelpTextFromMap('dsProductoId',helpMap),
                    //fieldLabel: "Producto",
                    disabled: true, 
                    name: 'dsRamo', 
                    id: 'dsProductoId',
                    readOnly:true
	             },

                {
                    xtype: 'textfield', 
                    width: 250,  
			        fieldLabel: getLabelFromMap('dsStatusId',helpMap,'Estado'),
			        tooltip:getToolTipFromMap('dsStatusId',helpMap,'Estado'), 
			        hasHelpIcon:getHelpIconFromMap('dsStatusId',helpMap),
			        Ayuda:getHelpTextFromMap('dsStatusId',helpMap),
                    //fieldLabel: "Estado",
                    disabled: true, 
                    name: 'estado', 
                    id: 'dsStatusId',
                    readOnly:true	                            		        
                } ,
                 {
                   xtype: 'textfield', 
                   width: 250,  
			       fieldLabel: getLabelFromMap('nmPoliId',helpMap,'Poliza'),
			       tooltip:getToolTipFromMap('nmPoliId',helpMap,'Poliza'), 
			       hasHelpIcon:getHelpIconFromMap('nmPoliId',helpMap),
			       Ayuda:getHelpTextFromMap('nmPoliId',helpMap),
                   //fieldLabel: "Poliza",
                   disabled: true, 
                   name: 'nmPoli', 
                   id: 'nmPoliId',
                   readOnly:true
                } ,
                {
                    xtype: 'textfield', 
                    width: 250,  
			        fieldLabel: getLabelFromMap('dsPersonaId',helpMap,'Cliente'),
			        tooltip:getToolTipFromMap('dsPersonaId',helpMap,'Cliente'), 
			        hasHelpIcon:getHelpIconFromMap('dsPersonaId',helpMap),
			        Ayuda:getHelpTextFromMap('dsPersonaId',helpMap),
                    //fieldLabel: "Cliente",
                    disabled: true, 
                    name: 'dsPersona', 
                    id: 'dsPersonaId',
                    readOnly:true
                },
                {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{cdUsuari}. {dsUsuari}" class="x-combo-list-item">{dsUsuari}</div></tpl>',
                    store: dsUsuarios,
                    displayField:'dsUsuari',
                    valueField:'cdUsuari',
                    hiddenName: 'cdUsuarioE',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
			        fieldLabel: getLabelFromMap('cdUsuarioEncuestaId',helpMap,'Usuario Responsable'),
			        tooltip:getToolTipFromMap('cdUsuarioEncuestaId',helpMap,'Usuario Responsable'), 
			        hasHelpIcon:getHelpIconFromMap('cdUsuarioEncuestaId',helpMap),
			        Ayuda:getHelpTextFromMap('cdUsuarioEncuestaId',helpMap),
                    //fieldLabel: "Usuario Responsable",
                    forceSelection: true,
                    width: 250,
                    emptyText:'Seleccione Usuario...',
                    selectOnFocus:true,                    
                    allowBlank : false,
                    id:'cdUsuarioEncuestaId'
                }
           ]
        });


  function guardarAsignacionEncuesta(){
    var conn = new Ext.data.Connection();
	                conn.request({
				    	url: _ACTION_GUARDAR_ASIGNACION_ENCUESTA,
				    	method: 'POST',
				    	params: {
				    				nmConfig:record.get('nmConfig'),
				    				cdUnieco: record.get('cdUnieco'),
				    				cdRamo: record.get('cdRamo'),
						    		estado:"",
                                    nmPoliza: record.get('nmPoliza'),  
						    		cdPerson:record.get('cdPerson'),
						    		status: 1,//record.get('status'),
						    		cdUsuari: formPanel.findById("cdUsuarioEncuestaId").getValue()
						    		
				    			},
	 					waitMsg : getLabelFromMap('400021', helpMap,'Espere por favor ...'),
                   		     callback: function (options, success, response) {
        if (Ext.util.JSON.decode(response.responseText).success == false) {
            Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), 'No se pudo guardar los datos');
        }else {
             
             Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), 'Los datos se guardaron con exito',function(){reloadGrid()});
             window.close();
             // function(){reloadGridNivAtencion(cdMatriz);habilitaBotonesTiempo();/*reloadGridResponsables("","");*/ _window.close();}
        }
    }
 })
} 
       
        
        
        
//Windows donde se van a visualizar la pantalla
var window = new Ext.Window({
        //title: 'Administrar Asignaci&oacute;n Encuesta',
		title: '<span style="color:black;font-size:14px;">'+getLabelFromMap('wndwAsgEncAgr',helpMap,'Administrar Asignaci&oacute;n Encuesta')+'</span>',
        width: 500,
        height:270,
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
                text : 'Guardar',
                disabled : false,
                handler : function() {
                   guardarAsignacionEncuesta();
                }
            },
             {
                 text : 'Regresar',
                 handler : function() {
                 window.close();
                    }
            }]
    	});

  window.show();
//formPanel.load();
   dsUsuarios.load({ params: {
    	     //cdModulo:record.get('cdModulo')
    	       nmConfig:record.get('nmConfig')
			},
			
	
			callback:function(r,options,success){	                        
				formPanel.load({
				    params: {
				    	     /* nmConfig: record.get('nmConfig'),
							    nmPoliza: record.get('nmPoliza'),
							    cdPerson:  record.get('cdPerson'),
							    cdUsuario:record.get('cdUsuari')*/		
				    	      pv_nmconfig_i: record.get('nmConfig'),
				    	      pv_cdunieco_i: record.get('cdUnieco'),
							  pv_estado_i: record.get('estado'),
							  pv_cdramo_i:  record.get('cdRamo'),
							  pv_nmpoliza_i:record.get('nmPoliza')
							  
				    	
				    	},
				    waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
				    waitMsg : getLabelFromMap('400028',helpMap,'Leyendo datos ...'),
				    success: function () {formPanel.findById("cdUsuarioEncuestaId").setValue(record.get('dsUsuari'))}
				});
			}
}); 
  

//ExtgetCm("dsUsuarios").setValue();
  
  
  /*dsUsuarios.load({
    params: {
    	     cdModulo:record.get('cdModulo')
			},success: function () {
			                        formPanel.load({
    params: {
    	      pv_nmconfig_i: record.get('nmConfig'),
			  pv_nmpoliza_i: record.get('nmPoliza'),
			  pv_cdperson_i:  record.get('cdPerson'),
			  pv_cdusuario_i:record.get('cdUsuario')
    	},
    waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
    waitMsg : getLabelFromMap('400028',helpMap,'Leyendo datos ...'),
    success: function () {formPanel.findById("cdUsuarioEncuestaId").setValue(record.get('cdUsuario'))}
});
			                         }
    });*/
    




};

