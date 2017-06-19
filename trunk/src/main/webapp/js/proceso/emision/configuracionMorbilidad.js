Ext.require([ 'Ext.form.*', 'Ext.data.*', 'Ext.chart.*', 'Ext.grid.Panel','Ext.layout.container.Column', 'Ext.selection.CheckboxModel' ]);
Ext.onReady(function() {
	var panelProveedor;
	Ext.selection.CheckboxModel.override( {
		mode: 'SINGLE',
		allowDeselect: true
	});
	//Declaracion de los modelos 
	Ext.define('modeloMorbilidad',{
		extend:'Ext.data.Model',
		fields:[
			    {type:'string',      name:'EDAD'},       
			    {type:'string',      name:'MEDPREVENTIVAHOM'},		    {type:'string',      name:'MEDPREVENTIVAMUJ'},
			    {type:'string',      name:'MEDPRIMERCONTHOM'},		    {type:'string',      name:'MEDPRIMERCONTAMUJ'},
			    {type:'string',      name:'MATERNIDADHOM'},			    {type:'string',      name:'MATERNIDADMUJ'},
			    {type:'string',      name:'AYUDAMATHOM'},			    {type:'string',      name:'AYUDAMATMUJ'},
			    {type:'string',      name:'SERVODONTHOM'},			    {type:'string',      name:'SERVODONTMUJ'},
			    {type:'string',      name:'SERVAUXDIAGTHOM'},		    {type:'string',      name:'SERVAUXDIAGTMUJ'},
			    {type:'string',      name:'MEDICAMENTOSHOM'},		    {type:'string',      name:'MEDICAMENTOSMUJ'},
			    {type:'string',      name:'HOSPITALIZAHOM'},		    {type:'string',      name:'HOSPITALIZAMUJ'},
			    {type:'string',      name:'ASISTENCIAHOM'},             {type:'string',      name:'ASISTENCIAMUJ'},
			    {type:'string',      name:'URGENCIAMEDHOM'},		    {type:'string',      name:'URGENCIAMEDMUJ'}
        ]
	});
	
    var storeGridMorbilidad = new Ext.data.Store({
    	pageSize	: 10
    	,model		: 'modeloMorbilidad'
        ,autoLoad	: false
        ,proxy		: {
        	enablePaging	:	true,
    	    reader			:	'json',
            type			:	'memory',
    		data			:	[]
    	}
    });
    
    
    var storeMorbilidad = Ext.create('Ext.data.Store', {
        model:'Generic',
        autoLoad:true,
        proxy: {
            type: 'ajax',
            url : _URL_CATALOGOS,
            extraParams : {
                catalogo:_CAT_MORBILIDAD
            },
        reader: {
            type: 'json',
            root: 'lista'
            }
        }
    });
    
    morbilidad = Ext.create('Ext.form.field.ComboBox',{
        colspan    :2,          fieldLabel   : 'Morbilidad',            id        : 'morbilidad',       allowBlank     : false, 
        editable   : false,     displayField : 'value',                 valueField: 'key',              forceSelection : true,
        width      :500,        labelWidth   : 170,                     queryMode : 'local',            name           :'morbilidad'
        ,store : storeMorbilidad
    });
	
	var panelInicialPral= Ext.create('Ext.panel.Panel',{
		border    : 0
		,renderTo : 'div_clau'
		,items    : [
			Ext.create('Ext.panel.Panel',{
				border  : 0
				,style         : 'margin:5px'
				,layout : {
					type     : 'table'
					,columns : 2
				}
				,defaults : {
					style : 'margin:5px;'
				}
				,items : [
					morbilidad
				]
				,buttonAlign: 'center'
				,buttons : [{
					text: "Buscar"
					,icon:_CONTEXT+'/resources/fam3icons/icons/magnifier.png'
					,handler: function(){
						cargarPaginacion();
					}	
				},{
					text: "Limpiar"
					,icon:_CONTEXT+'/resources/fam3icons/icons/arrow_refresh.png'
					,handler: function(){
						panelInicialPral.down('combo[name=params.cmbProveedor]').reset();
						storeGridMorbilidad.removeAll();
					}	
				}] 
			})
			,Ext.create('Ext.grid.Panel',{
				id             : 'clausulasGridId'
				,title         : 'Morbilidad'
				,store         :  storeGridMorbilidad
				,titleCollapse : true
				,style         : 'margin:5px'
				,height        : 400
				,columns       : [
					{
						header     : 'EDAD',                                            dataIndex  : 'EDAD',	width: 50
					},{
                        header     : 'MEDICINA PREVENTIVA <br/>HOMBRE',                 dataIndex  : 'MEDPREVENTIVAHOM',    width: 200
                    },{
                        header     : 'MEDICINA PREVENTIVA <br/>MUJER',                  dataIndex  : 'MEDPREVENTIVAMUJ',    width: 200
                    },{
                        header     : 'MEDICINA PRIMER CONTACTO <br/>HOMBRE',            dataIndex : 'MEDPRIMERCONTHOM',     width: 200
                    },{
                        header     : 'MEDICINA PRIMER CONTACTO <br/>MUJER',             dataIndex : 'MEDPRIMERCONTAMUJ',    width: 200
                    },{
                        header     : 'MATERNIDAD <br/>HOMBRE',                          dataIndex : 'MATERNIDADHOM',        width: 100
                    },{
                        header     : 'MATERNIDAD <br/>MUJER',                           dataIndex : 'MATERNIDADMUJ',        width: 150
                    },{
                        header     : 'AYUDA DE MATERNIDAD <br/>HOMBRE',                 dataIndex : 'AYUDAMATHOM',          width: 150
                    },{
                        header     : 'AYUDA DE MATERNIDAD <br/>MUJER',                  dataIndex : 'AYUDAMATMUJ',          width: 150
                    },{
                        header     : 'SERVICIOS ODONTOLOGICOS <br/>HOMBRE',             dataIndex : 'SERVODONTHOM',         width: 220
                    },{
                        header     : 'SERVICIOS ODONTOLOGICOS <br/>MUJER',              dataIndex : 'SERVODONTMUJ',         width: 220
                    },{
                        header     : 'SERVICIOS AUXILIARES DE DIAGNOSTICO <br/>HOMBRE', dataIndex : 'SERVAUXDIAGTHOM',      width: 230
                    },{
                        header     : 'SERVICIOS AUXILIARES DE DIAGNOSTICO <br/>MUJER',  dataIndex : 'SERVAUXDIAGTMUJ',      width: 230
                    },{
                        header     : 'MEDICAMENTOS <br/>HOMBRE',                        dataIndex : 'MEDICAMENTOSHOM',      width: 150
                    },{
                        header     : 'MEDICAMENTOS <br/>MUJER',                         dataIndex : 'MEDICAMENTOSMUJ',      width: 150
                    },{
                        header     : 'HOSPITALIZACI&Oacute;N <br/>HOMBRE',              dataIndex : 'HOSPITALIZAHOM',       width: 150
                    },{
                        header     : 'HOSPITALIZACI&Oacute;N <br/>MUJER',               dataIndex : 'HOSPITALIZAMUJ',       width: 150
                    },{
                        header     : 'ASISTENCIA INTERNACIONAL <br/>HOMBRE',            dataIndex : 'ASISTENCIAHOM',        width: 220
                    },{
                        header     : 'ASISTENCIA INTERNACIONAL <br/>MUJER',             dataIndex : 'ASISTENCIAMUJ',        width: 220
                    },{
                        header     : 'URGENCIAS MEDICAS <br/>HOMBRE',                   dataIndex : 'URGENCIAMEDHOM',       width: 220
                    },{
                        header     : 'URGENCIAS MEDICAS <br/>MUJER',                    dataIndex : 'URGENCIAMEDMUJ',       width: 220
                    }
				],
				bbar     :{
				displayInfo : true,
					store		: storeGridMorbilidad,
					xtype		: 'pagingtoolbar'
				}
			})
		]
	});
	storeMorbilidad.load({
        params:{
            'params.cdatribu' :'19',
            'params.cdramo'   :'4',
            'params.idPadre'   :null
        }
    });
	cargarPaginacion();
	
	function cargarPaginacion(){
		if(panelInicialPral.down('[name=morbilidad]').getValue() != null){
			storeGridMorbilidad.removeAll();
            var params = {
                //'params.morbilidad' : morbilidad.rawValue
            	'params.morbilidad' : panelInicialPral.down('[name=morbilidad]').getValue()
            };
            cargaStorePaginadoLocal(storeGridMorbilidad, _URL_CONSULTA_MORBILIDAD, 'slist1', params, function(options, success, response){
                if(success){
                    
                    var jsonResponse = Ext.decode(response.responseText);
                    if(jsonResponse.slist1 &&jsonResponse.slist1.length == 0) {
                        if(jsonResponse.slist1.length == 0){
                            centrarVentanaInterna(showMessage("Aviso", "No existe configuraci&oacute;n de la morbilidad.", Ext.Msg.OK, Ext.Msg.INFO));
                        }
                        return;
                    }
                }else{
                    centrarVentanaInterna(showMessage('Error', 'Error al obtener los datos',Ext.Msg.OK, Ext.Msg.ERROR));
                }
            });
		}
	}
});