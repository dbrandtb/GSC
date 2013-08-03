// Funcion de Editar y Agregar Tiempos
function administrarTiempo(record) { 


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

	
	
var form_edit = new Ext.FormPanel ({
	id: 'form_editId',
    url : _ACTION_GET_CONFIGURACION_ENCUESTAS_TIEMPO,
    frame : true,
    width : 500,
	height: true,
	 bodyStyle:'background: white',
    reader: elJsonFrmDocTiempo,
    items: [
            
            {
                xtype: 'numberfield', 
                fieldLabel: getLabelFromMap('txtFldNmUnidad',helpMap,'Tiempo'), 
                tooltip: getToolTipFromMap('txtFldNmUnidad',helpMap,'Tiempo'),
                hasHelpIcon:getHelpIconFromMap('txtFldNmUnidad',helpMap),
	            Ayuda: getHelpTextFromMap('txtFldNmUnidad',helpMap,''),         
                name: 'nmUnidad',
                id:'nmUnidadId',
                allowBlank:false,
                width: 200
            },{
            	 xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{id}.{texto}" class="x-combo-list-item">{texto}</div></tpl>',
    				store: dsUnidad,
    				id:'cdUnidtmpoId',
    				fieldLabel: getLabelFromMap('fgUnidadTiempoF',helpMap,'Unidad'),
    				tooltip: getToolTipFromMap('fgUnidadTiempoF',helpMap,'Listado de Unidades de Tiempo'),
    				hasHelpIcon:getHelpIconFromMap('fgUnidadTiempoF',helpMap),
	              	Ayuda: getHelpTextFromMap('fgUnidadTiempoF',helpMap,''),  
    //				anchor:'80%',
    				width:200,
    				displayField:'texto',
    				valueField: 'id',
    				hiddenName: 'cdUnidtmpo',
    				typeAhead: true,
    				allowBlank: false,
    				triggerAction: 'all',
    				emptyText:'Seleccionar Unidad...',
    				selectOnFocus:true,
    				forceSelection:true
            }
           ]
});


 function guardarTiempoConfigEncuesta(){
    var conn = new Ext.data.Connection();
	                conn.request({
				    	url: _ACTION_GUARDAR_CONFIGURACION_ENCUESTAS_TIEMPO,
				    	method: 'POST',
				    	params: {
				    				
				    				cdCampan: record.get('cdCampan'),
				    				cdRamo: record.get('cdRamo'),
						    		cdUnieco:  record.get('cdUnieco'),
                                    nmUnidad: form_edit.findById("nmUnidadId").getValue(),  
						    		cdUnidtmpo: form_edit.findById("cdUnidtmpoId").getValue()
				    			},
	 					waitMsg : getLabelFromMap('400021', helpMap,'Espere por favor ...'),
                   		     callback: function (options, success, response) {
        if (Ext.util.JSON.decode(response.responseText).success == false) {
            Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), 'No se pudo guardar los datos');
        }else {
             
             Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), 'Los datos se guardaron con exito');
             window.close();
             // function(){reloadGridNivAtencion(cdMatriz);habilitaBotonesTiempo();/*reloadGridResponsables("","");*/ _window.close();}
        }
    }
 })
} 


function borrarTiempoConfigEncuesta() {
		var _params = {
        		        pv_cdcampana_i: record.get('cdCampan'),
        				pv_cdunieco_i:record.get('cdUnieco'),
        				pv_cdramo_i:record.get('cdRamo')
         			};
        execConnection(_ACTION_BORRAR_CONFIGURACION_ENCUESTAS_TIEMPO, _params, cbkConnection);
        
};

var window = new Ext.Window ({
		title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('96',helpMap,'Configurar Tiempo de Permanencia de la Encuesta')+'</span>',
		width: 500,
		autoHeight: true,
        modal:true,
    	items: [form_edit],
        buttonAlign:'center',
         bodyStyle:'background: white',
        buttons : [ {
            text : getLabelFromMap('frmtDcmntEdtButtonGuardar',helpMap,'Guardar'), 
            tooltip: getToolTipFromMap('frmtDcmntEdtButtonGuardar',helpMap,'Guardar Tiempo'),
            disabled : false,
            handler : function() {
            	if (form_edit.form.isValid()){
               guardarTiempoConfigEncuesta();
            }else {
                    Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
           }}
           },
           {
           text : getLabelFromMap('frmtDcmntEdtButtonBorrar',helpMap,'Eliminar'), 
           tooltip: getToolTipFromMap('frmtDcmntEdtButtonBorrar',helpMap,'Eliminar Tiempo'),
           handler : function() {
                borrarTiempoConfigEncuesta();
            }
        },{
           text : getLabelFromMap('frmtDcmntEdtButtonCancelar',helpMap,'Cancelar'), 
            tooltip: getToolTipFromMap('frmtDcmntEdtButtonCancelar',helpMap,'Cancelar guardar el formato de Documento'),
           handler : function() {
                window.close();
            }
        }]
});



dsUnidad.load({callback:function(rec,opt,success){ form_edit.form.load({
    params: {
    	      pv_cdcampana_i: record.get('cdCampan'),
			  pv_cdramo_i: record.get('cdRamo'),
			  pv_cdunieco_i:  record.get('cdUnieco')
    	},
    waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
    waitMsg : getLabelFromMap('400028',helpMap,'Leyendo datos ...')	
});}
}); 



window.show();


	
	
function cbkConnection (_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){window.close()});
	}
}  

}


