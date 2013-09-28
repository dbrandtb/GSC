Ext.require([ 'Ext.form.*', 'Ext.data.*', 'Ext.chart.*', 'Ext.grid.Panel','Ext.layout.container.Column', 'Ext.selection.CheckboxModel' ]);

Ext.onReady(function() {

    Ext.selection.CheckboxModel.override( {
        mode: 'SINGLE',
        allowDeselect: true
    });
    
    // Conversión para el tipo de moneda
    Ext.util.Format.thousandSeparator = ',';
    Ext.util.Format.decimalSeparator = '.';
	
	//TODO: Eliminar esta variable cuando se estructure el codigo de quien la usa
	var sum=0;

    //var form = false, selectedRec = false;
	
    Ext.define('KeyValueModel', {
        extend: 'Ext.data.Model',
        fields : [{
            name : 'key',
            type : 'int'
        }, {
            name : 'value',
            type : 'string'
        }]
    });
    
    var storeTiposConsulta = Ext.create('Ext.data.JsonStore', {
        model: 'KeyValueModel',
        proxy: {
            type: 'ajax',
            url: _URL_TIPOS_CONSULTA,
            reader: {
                type: 'json',
                root: 'tiposConsulta'
            }
        }
    });
    storeTiposConsulta.load();

    var listViewOpcionesConsulta = Ext.create('Ext.grid.Panel', {
        //width:425,
        //height:250,
        collapsible:true,
        collapsed:true,
        //title:'Tipos de consulta',
        store: storeTiposConsulta,
        multiSelect: false,
        hideHeaders:true,
        viewConfig: {
            emptyText: 'No hay tipos de consulta'
        },
        columns: [/*{
            //text: '',
            flex: 5,
            dataIndex: 'key'
        },*/{
            flex: 1,
            dataIndex: 'value'
        }]
    });
    
    // little bit of feedback
    listViewOpcionesConsulta.on('selectionchange', function(view, nodes){
	
        if (this.getSelectionModel().hasSelection()) {
		
			var len = nodes.length,
				suffix = len === 1 ? '' : 's',
				str = '({0} item{1} seleccionado{1})</i>';
			listViewOpcionesConsulta.setTitle(Ext.String.format(str, len, suffix));
			
			var tipoConsultaSelected = listViewOpcionesConsulta.getSelectionModel().getSelection()[0];
			
			//hide all elements:
			//// panelInfoGralPoliza.hide();
			//// gridDatosAsegurado.hide();
			tabDatosGeneralesPoliza.hide();
			tabPanelAgentes.hide();
			
			
			switch (tipoConsultaSelected.get('key')) {
				case 1: //Consulta de Datos generales
					if (gridPanelSuplemento.getSelectionModel().hasSelection()) {
					
						//Mostrar info general poliza:
						//// panelInfoGralPoliza.show();
						tabDatosGeneralesPoliza.show();
						//Datos de Tarificación
						storeTarificacion.load({
							params: panelBusqueda.down('form').getForm().getValues(),
							callback: function(records, operation, success){
								if(success){
									//if(records.length <= 0){
										/*
										Ext.Msg.show({
											title: 'Error',
											msg: 'No existe tarificaci\u00F3n para dicha p\u00F3liza',
											buttons: Ext.Msg.OK,
											icon: Ext.Msg.ERROR
										});
										*/
										//gridDatosTarificacion.hide();
									//}else {
										//// gridDatosTarificacion.show();
									//}
								}else{
									Ext.Msg.show({
										title: 'Error',
										msg: 'Error al obtener la tarificaci\u00F3n de la p\u00F3liza',
										buttons: Ext.Msg.OK,
										icon: Ext.Msg.ERROR
									});
								}                    
							}
						});					
						//Datos para asegurados
						storeDatosAsegurado.load({
							params: panelBusqueda.down('form').getForm().getValues(),
							callback: function(records, operation, success){
								if(success){
									
									//if(records.length <= 0){
										//gridDatosAsegurado.hide();
									//}else {
										//// gridDatosAsegurado.show();
									//}
									/*
									Ext.Msg.show({
										title: 'Error',
										msg: 'No existe datos del asegurado',
										buttons: Ext.Msg.OK,
										icon: Ext.Msg.ERROR
									});
									*/
								}else{
									Ext.Msg.show({
										title: 'Error',
										msg: 'Error al obtener los datos del asegurado',
										buttons: Ext.Msg.OK,
										icon: Ext.Msg.ERROR
									});
								}        	        
							}
						});
					}
					break;
				case 2: //Consulta de Agentes
					tabPanelAgentes.show();
					if (gridPanelSuplemento.getSelectionModel().hasSelection()) {
						console.log('Params busqueda de agente=');console.log(panelBusqueda.down('form').getForm().getValues());
						storeInfoAgente.load({
							params: panelBusqueda.down('form').getForm().getValues(),
							callback: function(records, operation, success) {
								if(success){
									if(records.length > 0){
										panelDatosAgente.getForm().loadRecord(records[0]);  
									}else {
										Ext.Msg.show({
										title: 'Error',
										msg: 'El Agente no existe, verifique la clave',
										buttons: Ext.Msg.OK,
										icon: Ext.Msg.ERROR
										});
									}
								}else {	
									Ext.Msg.show({
										title: 'Error',
										msg: 'El Agente no existe, verifique la clave',
										buttons: Ext.Msg.OK,
										icon: Ext.Msg.ERROR
									});
								}
							}
						});
						
						//Obtenemos las polizas del agente:
						cargarGridPaginado(storePolizasAgente, _URL_CONSULTA_POLIZAS_AGENTE, "polizasAgente", panelBusqueda.down('form').getForm().getValues(), null);
						
						// Obtenemos los recibos de los agentes:
						storeRecibosAgente.load({
							params: panelBusqueda.down('form').getForm().getValues(),
							callback: function(records, operation, success){
								if(success){
									if(records.length <= 0){
										Ext.Msg.show({
											title: 'Error',
											msg: 'No existe p&oacute;lizas de dicho Agente',
											buttons: Ext.Msg.OK,
											icon: Ext.Msg.ERROR
										});
									}else {            	            	
										obtieneMontosRecibo(records);
									}
								}else{
									Ext.Msg.show({
										title: 'Error',
										msg: 'No existe p&oacute;lizas de dicho Agente',
										buttons: Ext.Msg.OK,
										icon: Ext.Msg.ERROR
									});
								}        	        
							}
						});
						
						tabPanelAgentes.show();
					}
					break;
				case 3: //Consulta de Recibos
				
				break;
			}
		}
    });
    

    Ext.define('Suplemento', {
        extend: 'Ext.data.Model',
        fields: [
            {name: 'cdramo'},
            {name: 'cdunieco'},
            {name: 'estado'},
            {name: 'nmpoliza'},
			{name: 'nmsuplem'},
            {name: 'dstipsup'},
            {name: 'feemisio', dateFormat: 'd/m/Y'},
            {name: 'feinival', dateFormat: 'd/m/Y'},
            {name: 'nlogisus'},
            {name: 'nsuplogi'},
            {name: 'ptpritot', type : 'float'}
        ],
        /*
        changeName: function() {
            var oldName = this.get('name'),
                newName = oldName + " The Barbarian";

            this.set('name', newName);
        }
        */
    });
    
	
    var storeSuplemento = new Ext.data.Store({
        model: 'Suplemento',
        //autoLoad:true,
        proxy:
        {
            type: 'ajax',
            url : _URL_CONSULTA_DATOS_SUPLEMENTO,
            reader:
            {
                type: 'json',
                root: 'datosSuplemento'
            }
        }
    });
	/*
	var storeSuplemento = Ext.create('Ext.data.Store', {
        pageSize : 5,
        autoLoad : true,
        model: 'Suplemento',
        proxy    : {
            enablePaging : true,
            reader       : 'json',
            type         : 'memory',
            data         : []
        }
    });
    */
    // create a grid that will list the dataset items.
    var gridPanelSuplemento = Ext.create('Ext.grid.Panel', {
        id : 'suplemento-form',
        store : storeSuplemento,
        selType: 'checkboxmodel',
        //title : 'HISTORICO DE MOVIMIENTOS',
        defaults: {sortable : true, width:120, align : 'right'},
        columns : [{
            id : 'dstipsup',
            text : 'Tipo endoso',
            dataIndex : 'dstipsup',
            width:200
        }, {
            text : 'Fecha de emisi\u00F3n',
            dataIndex : 'feemisio',
            format: 'd M Y',
            //renderer: Ext.util.Format.dateRenderer('d M Y'),
            width:150
        }, {
            text : 'Fecha inicio vigencia',
            dataIndex : 'feinival',
            format: 'd M Y',
            //renderer: Ext.util.Format.dateRenderer('d M Y'), 
            width:150
        }, {
            text : 'N&uacute;mero de endoso',
            dataIndex : 'nsuplogi',
            width:150
        }, {
            text : 'Prima total',
            dataIndex : 'ptpritot',
            renderer : 'usMoney',
            width:150
        }],

        listeners : {
            selectionchange : function(model, records) {
                
                //Limpiar seleccion de la lista de opciones de consulta
                listViewOpcionesConsulta.getSelectionModel().deselectAll();
                listViewOpcionesConsulta.expand();
				panelInfoGralPoliza.getForm().reset();
				//// gridDatosTarificacion.hide();
				storeTarificacion.removeAll();
				tabDatosGeneralesPoliza.setActiveTab(0);
				tabPanelAgentes.setActiveTab(0);
				
				if(this.getSelectionModel().hasSelection()) {
					panelBusqueda.down('form').getForm().reset();
					console.log('Params busqueda reset=');console.log(panelBusqueda.down('form').getForm().getValues());
					//Lenar campos de formulario de busqueda:
					var rowSelected = gridPanelSuplemento.getSelectionModel().getSelection()[0];
					panelBusqueda.down('form').getForm().findField("params.cdunieco").setValue(rowSelected.get('cdunieco'));
					panelBusqueda.down('form').getForm().findField("params.cdramo").setValue(rowSelected.get('cdramo'));
					panelBusqueda.down('form').getForm().findField("params.estado").setValue(rowSelected.get('estado'));
					panelBusqueda.down('form').getForm().findField("params.nmpoliza").setValue(rowSelected.get('nmpoliza'));
					panelBusqueda.down('form').getForm().findField("params.suplemento").setValue(rowSelected.get('nmsuplem'));
					
					console.log('Params busqueda de datos grales poliza=');console.log(panelBusqueda.down('form').getForm().getValues());

					// Consultar Datos Generales de la Poliza:
					storeDatosPoliza.load({
						params : panelBusqueda.down('form').getForm().getValues(),
						callback : function(records, operation, success) {
							if (success) {
								if (records.length > 0) {
									// Se asigna valor al parametro de busqueda:
									panelBusqueda.down('form').getForm().findField("params.cdagente").setValue(records[0].get('cdagente'));
									
									// Se llenan los datos generales de la poliza elegida
									Ext.getCmp("formularioPoliza").getForm().loadRecord(records[0]);
								} else {
									Ext.Msg.show({
										title : 'Info',
										msg : 'No existen datos generales de la p\u00F3liza elegidaLa P&oacute;iza no existe, verifique sus datos',
										buttons : Ext.Msg.OK,
										icon : Ext.Msg.ERROR
									});
								}
							} else {
								Ext.Msg.show({
									title : 'Error',
									msg : 'Error al obtener los datos generales de la p\u00F3liza elegida, intente m\u00E1s tarde',
									buttons : Ext.Msg.OK,
									icon : Ext.Msg.ERROR
								});
							}

						}
					});					
					/*var fields;
					if (records[0]) {
						selectedRec = records[0];
						if (!form) {
							form = this.up('panel').down('form').getForm();
							fields = form.getFields();
							fields.each(function(field) {
								if (field.name != 'company') {
									field.setDisabled(false);
								}
							});
						} else {
							fields = form.getFields();
						}
						// prevent change events from firing
						form.suspendEvents();
						form.loadRecord(selectedRec);
						form.resumeEvents();
					}*/
				}
            }
        }
    });
    
    
    /** INFORMACIÓN DEL FORMULARIO **/
  //-------------------------------------------------------------------------------------------------------------    
  // Modelo
  Ext.define('RowDatosPoliza', {
      extend: 'Ext.data.Model',
      fields: [
          {type:'string',        name:'nmsolici'      },
          {type:'string',        name:'titular'       },
          {type:'string',        name:'cdrfc'         },
          {type:'date', name:'feemisio', dateFormat: 'd/m/Y'},
          {type:'date', name:'feefecto', dateFormat: 'd/m/Y'},
          {type:'date', name:'feproren', dateFormat: 'd/m/Y'},
          {type:'string',        name:'dstarifi'      },
          {type:'string',        name:'dsmoneda'      },
          {type:'string',        name:'nmcuadro'      },
          {type:'string',        name:'dsperpag'      },
          {type:'string',        name:'dstempot'      },
          {type:'string',        name:'nmpoliex'      },
          {type:'string',        name:'cdagente'      }
      ]
  });

  // Store
  var storeDatosPoliza = new Ext.data.Store({
   model: 'RowDatosPoliza',
   proxy:
   {
        type: 'ajax',
        url : _URL_CONSULTA_DATOS_POLIZA,
    reader:
    {
         type: 'json',
         root: 'datosPoliza'
    }
   }
  });
    
    /**FORMULARIO DATOS DE LA POLIZA **/
    var panelInfoGralPoliza = Ext.create('Ext.form.Panel', {
        id : "formularioPoliza",
        //title : 'DATOS DE LA P&Oacute;LIZA',
        model : 'RowDatosPoliza',
        width : 820,
		border : false,
        //height : 280,
        defaults : {
            bodyPadding : 3,
            border : false
        },
        items : [ {
            layout : 'hbox',
            items : [
                {xtype: 'textfield', name: 'nmpoliex', fieldLabel: 'N&uacute;mero de P&oacute;liza', readOnly: true, labelWidth: 120},
                {xtype: 'textfield', id: 'nmsolici',  name: 'nmsolici', fieldLabel: 'N&uacute;mero de solicitud', width: 250, labelWidth: 120, readOnly: true}
            ]
        }, {
            layout : 'hbox',
            items : [ 
                {xtype:'textfield', name:'titular', fieldLabel: 'Nombre del titular', readOnly: true, labelWidth: 120, width: 400}, 
                {xtype:'textfield', name:'cdrfc', fieldLabel: 'RFC', readOnly: true, labelWidth: 120, labelAlign: 'right'}
            ]
        }, {
            layout : 'hbox',
            items : [ 
                {xtype: 'datefield', name: 'feemisio', fieldLabel: 'Fecha emisi&oacute;n',    format: 'd/m/Y', readOnly: true, labelWidth: 120, width: 200}, 
                {xtype: 'datefield', name: 'feefecto', fieldLabel: 'Fecha de efecto',         format: 'd/m/Y', readOnly: true, labelWidth: 100, width: 200, labelAlign: 'right'}, 
                {xtype: 'datefield', name: 'feproren', fieldLabel: 'Fecha renovaci&oacute;n', format: 'd/m/Y', readOnly: true, labelWidth: 100, width: 200, labelAlign: 'right'}
            ]
        }, {
            layout : 'hbox',
            items : {xtype: 'textfield', name: 'dstarifi', fieldLabel: 'Tipo de tarificaci&oacute;n', readOnly: true, labelWidth: 120}
        }, {
            layout : 'hbox',
            items : {xtype: 'textfield', name: 'dsmoneda', fieldLabel: 'C&oacute;digo de moneda', readOnly: true, labelWidth: 120}
        }, {
            layout : 'hbox',
            items : {xtype: 'textfield', name: 'nmcuadro', fieldLabel: 'Cuadro de comisiones', readOnly: true, labelWidth: 120}
        }, {
            layout : 'hbox',
            items : {xtype: 'textfield', name: 'dsperpag', fieldLabel: 'Periodicidad de pago', readOnly: true, labelWidth: 120}
        }, {
            layout : 'hbox',
            items : {xtype: 'textfield', name: 'dstempot', fieldLabel: 'Tipo de P&oacute;liza', readOnly: true, labelWidth: 120}
        } ]
    });
    
    
    /**INFORMACION DEL GRID DE TARIFICACION**/
    //-------------------------------------------------------------------------------------------------------------    
    //Modelo
    Ext.define('RowDatosTarificacion',{
        extend: 'Ext.data.Model',
        fields: [
              {type:'string',    name:'dsgarant'      },
              {type:'float',    name:'montoComision' },
              {type:'float',    name:'montoPrima'    },
              {type:'float',    name:'sumaAsegurada' }
        ]
    });
    
    // Store
    var storeTarificacion = new Ext.data.Store({
        model: 'RowDatosTarificacion',
        proxy: {
           type: 'ajax',
           url : _URL_CONSULTA_DATOS_TARIFA_POLIZA,
            reader: {
                type: 'json',
                root: 'datosTarifa'
            }
        }
    });
    /**GRID PARA LOS DATOS DE TARIFICACION **/
    var gridDatosTarificacion = Ext.create('Ext.grid.Panel', {
		width   : 820,
        //title   : 'DATOS TARIFICACI&Oacute;N',
        store   : storeTarificacion,
        id      : 'gridDatosTarificacion',
        features:[{
                    ftype:'summary'
                }],
        columns: [
			{
				text            :'Garant&iacute;a',
				dataIndex       :'dsgarant',
				width           :250,
				summaryRenderer : function(value){return Ext.String.format('TOTALES');}
			},
			{
				text            :'Suma Asegurada',  
				dataIndex       :'sumaAsegurada',
				width           :170 , 
				align           :'right' , 
				renderer        :Ext.util.Format.usMoney, 
				summaryType     :'sum'
			},
			{
				text            :'Monto de la Prima',
				dataIndex       :'montoPrima',
				width           : 170,
				align           :'right',        
				renderer        :Ext.util.Format.usMoney,
				summaryType     :'sum'
			},
			{
				text            : 'Monto de la Comisi&oacute;n',
				dataIndex       :'montoComision',
				width           : 170,
				renderer        :Ext.util.Format.usMoney,
				align           :'right',        
				summaryType     :'sum'
			}
        ]
    });
    gridDatosTarificacion.store.sort([
        { 
            property    : 'dsgarant',
            direction   : 'ASC'
        }
    ]);
	
	
	// Modelo
	Ext.define('RowDatosAsegurado', {
	    extend: 'Ext.data.Model',
	    fields: [
			{type:'string', name:'cdperson'},
			{type:'string',	name:'cdrfc'},
			{type:'string',	name:'cdrol'},
			{type:'string',	name:'dsrol'},
			{type:'date',	name:'fenacimi', dateFormat: 'd/m/Y'},
			{type:'string',	name:'nmsituac'},
			{type:'string',	name:'sexo'},
			{type:'string',	name:'titular'}
	    ]
	});
	
	// Store
	var storeDatosAsegurado = new Ext.data.Store({
	 model: 'RowDatosAsegurado',
	 proxy:
	 {
		  type: 'ajax',
		  url : _URL_CONSULTA_DATOS_ASEGURADO,
	  reader:
	  {
		   type: 'json',
		   root: 'datosAsegurados'
	  }
	 }
	});
	
	var gridDatosAsegurado = Ext.create('Ext.grid.Panel', {
	    title   : 'DATOS DE LOS ASEGURADOS',
	    store   : storeDatosAsegurado,
	    id      : 'gridDatosAsegurado',
	    width   : 800,	    
	    items:[{
		   xtype:'textfield', name:'cdrfc', fieldLabel: 'RFC', readOnly: true, labelWidth: 120
		}],
		columns: [
			{text:'Rol',dataIndex:'dsrol',width:130 , align:'left'},
			{text:'Titular',dataIndex:'titular',width:270,align:'left'},
			{text:'RFC',dataIndex:'cdrfc',width:150,align:'left'},
			{text:'Sexo',dataIndex:'sexo',width:100 , align:'left'},
			{text:'Fecha de Nacimiento',dataIndex:'fenacimi',width:150, align:'left',renderer: Ext.util.Format.dateRenderer('d/m/Y')}
		]
	});
    
    
    
    /**INFORMACION DEL GRID DE LA POLIZA DEL ASEGURADO**/
    //-------------------------------------------------------------------------------------------------------------    
    // Modelo
    Ext.define('RowPolizaAsegurados', {
        extend: 'Ext.data.Model',
        fields: [
            {type:'string',        name:'cdramo'        },
            {type:'string',        name:'compania'      },
            {type:'string',        name:'descripcion'   },
            {type:'string',        name:'dsramo'        },
            {type:'string',        name:'estado'         },
            {type:'string',        name:'nmpoliza'      },
            {type:'string',        name:'nombre'        }
        ]
    });
	/*
    var storePolizaAsegurado = new Ext.data.Store({
        model: 'RowPolizaAsegurados',
		proxy: {
			type: 'ajax',
			url : _URL_CONSULTA_POLIZAS_ASEGURADO,
			reader:{
				type: 'json',
				root: 'polizasAsegurado'
			}
		}
    });
	*/
	var storePolizaAsegurado = Ext.create('Ext.data.Store', {
        pageSize : 10,
        autoLoad : true,
        model: 'RowPolizaAsegurados',
        proxy    : {
            enablePaging : true,
            reader       : 'json',
            type         : 'memory',
            data         : []
        }
    });
	
	

    /** GRID PARA LOS DATOS DEL ASEGURADO **/
    var gridPolizasAsegurado= Ext.create('Ext.grid.Panel', {
        title : 'Eliga una p&oacute;liza:',
        store : storePolizaAsegurado,
        id: "gridPolizasAsegurado",
        selType: 'checkboxmodel',
        width : 680,
		bbar     :
        {
            displayInfo : true,
            store       : storePolizaAsegurado,
            xtype       : 'pagingtoolbar'
        },
        features:[{
           ftype:'summary'
        }],
        columns: [
			{text: 'Nombre', dataIndex: 'nombre', width: 200},
            {text: 'Descripci&oacute;n', dataIndex: 'descripcion', width: 150},
            {text: 'Ramo', dataIndex:'dsramo', width:150},
            {text: 'Estado', dataIndex: 'estado', width: 70},
            {text: 'P&oacute;liza', dataIndex: 'nmpoliza', width: 70}
        ]
    });
    
    //var windowPolizas = new Ext.Window({
    var windowPolizas= Ext.create('Ext.window.Window', {
        title: 'POLIZAS',
        //height: 400,
		width : 700,
        closeAction: 'hide',
        items:[gridPolizasAsegurado],
        buttons:[{
            text: 'Aceptar',
            handler: function(){
				if (gridPolizasAsegurado.getSelectionModel().hasSelection()) {
                    var rowPoliza = gridPolizasAsegurado.getSelectionModel().getSelection()[0];
                    //Asignar valores de la poliza seleccionada al formulario de busqueda
                    var formBusqueda = panelBusqueda.down('form').getForm();
                    formBusqueda.findField("params.cdunieco").setValue(rowPoliza.get('compania'));
                    formBusqueda.findField("params.cdramo").setValue(rowPoliza.get('cdramo'));
                    formBusqueda.findField("params.estado").setValue(rowPoliza.get('estado'));
                    formBusqueda.findField("params.nmpoliza").setValue(rowPoliza.get('nmpoliza'));
                    gridPolizasAsegurado.getStore().removeAll();
                    windowPolizas.close();
					
                    // Recargar store con busqueda de historicos de la poliza seleccionada
                    cargaStoreSuplemento(formBusqueda.getValues());
                 }else{                       
                     Ext.Msg.show({
                        title: 'Aviso',
                        msg: 'Debes seleccionar un registro',
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.WARNING
                    });                   
                }
            }
        }]
    });
        //-------------------------------------------------------------------------------------------------------------    
    var tabDatosGeneralesPoliza = Ext.create('Ext.tab.Panel', {
	    width: 830,
	    //height: 200,
	    items: [{
	        title : 'DATOS DE LA P&Oacute;LIZA',
	        //html: 'Home',
	        //itemId: 'tabDatosGralesPoliza',
			border:false,
	        items:[
                   {
                       //layout  :  'hbox',
                       items   :  [ panelInfoGralPoliza]
                   }
               ]
	    }, {
	        title: 'DATOS TARIFICACI&Oacute;N',
	        itemId: 'tabDatosTarificacion',
	        items:[	                    
                   {
                       //layout  :  'hbox',
                       items   :  [ gridDatosTarificacion]
                   }
               ]
	    }, {
	        title: 'ASEGURADOS',
	        itemId: 'tabDatosAsegurados',
	        items:[	                    
                   {
                       //layout  :  'hbox',
                       items   :  [ gridDatosAsegurado]
                   }
               ]
	    }, {
			id: 'tbDocumentos',
			title : 'DOCUMENTACI&Oacute;N',
			width: '350',
			loader : {
				url : _URL_CONSULTA_DOCUMENTOS,
				scripts : true,
				autoLoad : false
			},
			listeners : {
				activate : function(tab) {
					tab.loader.load({
						params : {
							'smap1.readOnly' :  true,
							'smap1.nmpoliza' :  
								panelBusqueda.down('form').getForm().findField("params.estado").getValue() == 'M'?
								Ext.getCmp('nmsolici').getValue() : 
								panelBusqueda.down('form').getForm().findField("params.nmpoliza").getValue(),
							'smap1.cdunieco' :  panelBusqueda.down('form').getForm().findField("params.cdunieco").getValue(),
							'smap1.cdramo' :  panelBusqueda.down('form').getForm().findField("params.cdramo").getValue(),
							'smap1.estado' :  panelBusqueda.down('form').getForm().findField("params.estado").getValue(),
							'smap1.nmsuplem' :  panelBusqueda.down('form').getForm().findField("params.suplemento").getValue()
						}
					});
				}
			}
		}]    
	});
	
	//--------------------
	// Modelo
	Ext.define('ModelInfoAgente', {
		extend: 'Ext.data.Model',
		fields: [
			{type:'string',		name:'cdagente'      },
			{type:'string',		name:'cdideper'      },
			{type: 'date',      name: 'fedesde' , dateFormat: 'd/m/Y' },
			{type:'string',		name:'nombre'        }
		]
	});
	// Store
	var storeInfoAgente = new Ext.data.Store({
		model: 'ModelInfoAgente',
		proxy: {
			type: 'ajax',
			url : _URL_CONSULTA_DATOS_AGENTE,
			reader:{
				type: 'json',
				root: 'datosAgente'
			}
		}
	});
	// Panel Info Agente
	var panelDatosAgente = Ext.create('Ext.form.Panel', {
		title   : 'INFORMACION DEL AGENTE',
		model   : 'ModelInfoAgente',
		//width      : 750,
		//height     : 100,
		border: false,
		defaults: {
			bodyPadding: 5
		},
		items:[
			{xtype:'textfield', name:'cdideper', fieldLabel: 'RFC', readOnly: true, labelWidth: 120}, 
			{xtype:'textfield', name:'nombre',   fieldLabel: 'Nombre', readOnly: true, labelWidth: 120, width: 400},
			{xtype: 'datefield',name:'fedesde',  fieldLabel: 'Fecha de ingreso', format: 'd/m/Y', readOnly: true, labelWidth: 120}
		]
	});
	
	
	/**INFORMACION DEL GRID DE CONSULTA DE POLIZAS DEL AGENTE **/
	//-------------------------------------------------------------------------------------------------------------	
	//Modelo
	Ext.define('RowDatosPolizasAgente',{
	  extend: 'Ext.data.Model',
	  fields: [
			{type:'string',		name:'cdramo'      	},
			{type:'string',		name:'cdunieco' 	},
			{type:'string',		name:'dsramo'    	},
			{type:'string',		name:'dsunieco' 	},
			{type:'string',		name:'nmcuadro'    	},
			{type:'string',		name:'nmpoliza' 	}
	  ]
	});

	//Store	
	var storePolizasAgente = new Ext.data.Store({
		pageSize : 10,
		autoLoad : true,
		model: 'RowDatosPolizasAgente',
		proxy:
		{
			 /*type: 'ajax',
			 url : _URL_CONSULTA_POLIZAS_AGENTE,
			  reader:
			  {
			   type: 'json',
			   root: 'polizasAgente'
			  }*/
			enablePaging : true,
			reader       : 'json',
			type         : 'memory',
			data         : []
		}
	});
	
	/**GRID PARA LOS DATOS DE POLIZA DEL AGENTE **/
	var panelDatosPolizaAgente = Ext.create('Ext.grid.Panel', {
	    title   : 'DATOS DE P&Oacute;LIZA DEL AGENTE',
	    store   : storePolizasAgente,
	    //id      : 'panelDatosPolizaAgente',
	    //width   : 650,	    
	    columns: [            
	                {text:'Producto', dataIndex:'dsramo', width:200, align:'left'},
	                {text:'Unidad econ&oacute;mica', dataIndex:'dsunieco', width:300, align:'left'},
	                {text:'N&uacutemero de p&oacuteliza', dataIndex:'nmpoliza', width:200, align:'left'}
		],
		bbar:new Ext.PagingToolbar({
			//pageSize: 10,
			store: storePolizasAgente,
			displayInfo: true,
			displayMsg: 'Registros mostrados {0} - {1} de {2}',
			emptyMsg: 'No hay registros para mostrar',
			beforePageText: 'P&aacute;gina',
			afterPageText: 'de {0}'
		})
	});


	/**INFORMACION DEL GRID DE CONSULTA DE RECIBOS DEL AGENTE **/
	//-------------------------------------------------------------------------------------------------------------	
	//Modelo
	Ext.define('RowDatosRecibosAgente',{
	extend: 'Ext.data.Model',
	fields: [
		{type:'string',	name:'dsgarant'},
		{type:'date',	name:'fefin',    dateFormat: 'd/m/Y'},
		{type:'date',	name:'feinicio', dateFormat: 'd/m/Y'},
		{type:'string',	name:'nmrecibo'},
		{type:'float',	name:'ptimport'}
	]
	});

	//Store	
	var storeRecibosAgente = new Ext.data.Store({
		model: 'RowDatosRecibosAgente',
		groupField: 'dsgarant',
		proxy: {
			type: 'ajax',	 
			url : _URL_CONSULTA_RECIBOS_AGENTE,
			reader: {
			    type: 'json',
			    root: 'recibosAgente'
			}
		}
	});
	
	var totalMontoRecibos = Ext.create('Ext.toolbar.Toolbar',{
		buttonAlign:'center',
		//width   : 550,
		items:
		[
			{xtype: 'tbtext', text: 'Monto Total '},
			'->',
			,Ext.create('Ext.form.Label',{
				style:'color:white;',
				initComponent:function()
				{
					this.setText(Ext.util.Format.usMoney(sum));
					this.callParent();
				}
			})
		]
	});
		
	var consultaRecibosAgente = Ext.create('Ext.grid.Panel', {
		//title   : 'RECIBOS DEL AGENTE',
		store   : storeRecibosAgente,
		//id      : 'consultaRecibosAgente',
		collapsible: true,
		//width   : 550,	    
		columns: [	               
			{header: 'N&uacute;mero de Recibo', dataIndex: 'nmrecibo', width:350 , summaryType: 'count', summaryRenderer: function(value){ return Ext.String.format('Total de Garant&iacute;a{1}: {0}', value, value !== 1 ? 's' : ''); }},
			{header: 'Importe', dataIndex:'ptimport', width:200 , align:'right', renderer: Ext.util.Format.usMoney, summaryType: 'sum'}
		],
		features: [{
			groupHeaderTpl: 'Tipo Garant&iacute;a: {name}', ftype:'groupingsummary', summaryType: 'sum', startCollapsed :true
		}]
	});
	
	
	var tabPanelAgentes = Ext.create('Ext.tab.Panel', {
	    //width: 830,
	    //height: 200,
	    items: [{
			//itemId: 'tabPanelAgentes',
	        title : 'DATOS DEL AGENTE',
			border:false,
	        items:[panelDatosAgente]
	    }, {
			//itemId: 'tabPolizasAgente',
	        title: 'POLIZAS DEL AGENTE',
	        items:[panelDatosPolizaAgente]
	    }, {
			//itemId: 'tabRecibosAgente',
	        title: 'RECIBOS DEL AGENTE',
	        items:[totalMontoRecibos, consultaRecibosAgente]
	    }]
	});
	
	
    // Main Panel

    var panelBusqueda = Ext.create('Ext.Panel', {
        id:'main-panel',
        baseCls:'x-plain',
        renderTo: Ext.getBody(),
        layout: {
            type: 'table',
            columns: 2
        },
        autoScroll:true,
        // title : 'PANEL PRINCIPAL',
        // applied to child components
        defaults: {frame:true, width:200, height: 200, margin : '2'},
        items:[{
            title:'BUSQUEDA DE POLIZAS',
            colspan:2,
            width:990,
            height:100,
            // //////////////
            items: [
                {
                    xtype: 'form',
                    url: _URL_CONSULTA_DATOS_SUPLEMENTO,
                    // title: 'Formulario',
                    border: false,
                    layout: {
                        type:'hbox'/*,
                        align:'stretch'*/
                    },
                    margin: '5',
                    defaults: {
                        //width: 150,
                        //labelAlign: 'right',
                        //enforceMaxLength: true,
                        bubbleEvents: ['change']
                    },
                    items : [
                        {
                            xtype: 'radiogroup',
							name: 'groupTipoBusqueda',
							flex: 15,
                            columns: 1,
                            vertical: true,
                            items: [
                                {boxLabel: 'Por datos generales', name: 'tipoBusqueda', inputValue: 1, checked: true, width: 140},
                                {boxLabel: 'Por RFC', name: 'tipoBusqueda', inputValue: 2}
                            ],
                            listeners : {
                                change : function(radiogroup, newValue, oldValue, eOpts) {
                                    switch (newValue.tipoBusqueda) {
                                        case 1:
                                            Ext.getCmp('subpanelBusquedaRFC').hide();
											Ext.getCmp('subpanelBusquedaGral').show();
                                            break;
                                        case 2:
											Ext.getCmp('subpanelBusquedaGral').hide();
											Ext.getCmp('subpanelBusquedaRFC').show();
                                            break;
                                    }
                                }
                            }
                        },
                        {
                            xtype: 'tbspacer',
                            flex: 2.5
                        },
                        {
                            layout : 'vbox',
                            align:'stretch',
							flex: 70,
                            border: false,
                            items: [
                                {
									id: 'subpanelBusquedaGral',
                                    layout : 'hbox',
                                    align:'stretch',
                                    border: false,
                                    defaults: {
                                        //labelAlign: 'right',
                                        enforceMaxLength: true
                                    },
                                    items : [
                                        {
											// Numero de poliza externo
                                            xtype : 'textfield',
                                            name : 'params.nmpoliex',
                                            fieldLabel : 'N&uacute;mero de P&oacute;liza',
                                            value:'02130001400A',
                                            labelWidth : 120,
                                            width: 300,
                                            maxLength : 40,
											msgTarget: 'side'
                                        },{
                                            xtype : 'hiddenfield',
                                            name : 'params.cdunieco'
                                        },{
                                            xtype : 'hiddenfield',
                                            name : 'params.cdramo'
                                        },{
                                            xtype : 'hiddenfield',
                                            name : 'params.estado'
                                        },{
                                            xtype: 'hiddenfield',
                                            name : 'params.nmpoliza'
                                        },{
                                            xtype: 'hiddenfield',
                                            name : 'params.suplemento'
                                        },{
                                        	xtype: 'hiddenfield',
                                            name : 'params.cdagente'
                                        }
                                    ]
                                },
                                {
									id: 'subpanelBusquedaRFC',
                                    layout : 'hbox',
                                    align:'stretch',
                                    border: false,
									hidden: true,
									defaults: {
                                        labelAlign: 'right',
                                        enforceMaxLength: true,
										msgTarget: 'side'
                                    },
                                    items : [
                                        {
                                            xtype: 'textfield',
                                            name : 'params.rfc',
											//msgTarget: 'side',
                                            fieldLabel : 'RFC',
                                            //labelWidth : 20,
                                            maxLength : 13,
											allowBlank: false
											//, value: 'PUMC820429B61'
                                        }
                                    ]/*
									,buttons: [{
										text: 'Save',
										handler: function() {
											this.up('form').getForm().findField('params.rfc').isValid();
											alert('ss');
											//alert(this.up('form').getForm().isValid());
										}
									}]*/
                                }
                            ]
                        },
                        {
                            xtype:'tbspacer',
                            flex: 2.5
                        },
                        {
                            xtype : 'button',
							flex:10,
                            text: 'Buscar',
                            //width: '100',
                            handler: function () {
                                var formBusqueda = this.up('form').getForm();
								//Obtenemos el valor elegido en 'groupTipoBusqueda' para elegir el tipo de busqueda a realizar.
                                switch (formBusqueda.findField('groupTipoBusqueda').getValue().tipoBusqueda) {
									case 1:
										// Busqueda por Datos Generales de la poliza:
										cargaStoreSuplemento(formBusqueda.getValues());
										break;
										
									case 2:
										// Busqueda de polizas por RFC:
										if(!formBusqueda.findField('params.rfc').isValid()){
											setMessage('', _MSG_RFC_INVALIDO, Ext.Msg.OK, Ext.Msg.INFO);
											return;
										}
										var callbackGetPolizasAsegurado = function(options, success, response) {
											if(success){
												var jsonResponse = Ext.decode(response.responseText);
												if(jsonResponse.polizasAsegurado && jsonResponse.polizasAsegurado.length == 0) {
													setMessage(_MSG_SIN_DATOS, _MSG_BUSQUEDA_SIN_DATOS, Ext.Msg.OK, Ext.Msg.INFO);
													return;
												}
												storePolizaAsegurado.setProxy(
												{
													type         : 'memory',
													enablePaging : true,
													reader       : 'json',
													data         : jsonResponse.polizasAsegurado
												
												});
												windowPolizas.show();
												/*storePolizaAsegurado.load({
													callback: function(records, operation, success) {
														if(success){
															windowPolizas.show();
														}
													}
												});*/
											}else{
												Ext.Msg.show({
													 title: 'Error',
													 msg: 'Error al obtener las p&oacute;lizas, intente m\u00E1s tarde.',
													 buttons: Ext.Msg.OK,
													 icon: Ext.Msg.ERROR
												 });
											}
										}
										cargarGridPaginado(storePolizaAsegurado, 
											_URL_CONSULTA_POLIZAS_ASEGURADO, 
											'polizasAsegurado', 
											formBusqueda.getValues(), 
											callbackGetPolizasAsegurado);
											
										/*
										storePolizaAsegurado.load({
											params: this.up('form').getForm().getValues(),
											callback: function(records, operation, success) {
												if(success){
													if(records.length <= 0){
														Ext.Msg.show({
															 title: 'Error',
															 msg: 'No existe P&oacute;liza para el asegurado',
															 buttons: Ext.Msg.OK,
															 icon: Ext.Msg.ERROR
														 });
													}else {
														windowPolizas.show();
													}
												}else {
													Ext.Msg.show({
														 title: 'Error',
														 msg: 'No existe P&oacute;liza para el asegurado',
														 buttons: Ext.Msg.OK,
														 icon: Ext.Msg.ERROR
													 });
												}
											}
										});
										*/
										break;
								}
                            }
                        }
                    ]
                }
            ]
        },
		{
            title:'HISTORICO DE MOVIMIENTOS',
            width:990,
            height:150,
            colspan:2,
            items : [
                gridPanelSuplemento
            ]
        },
		{
            title:'CONSULTAS',
            //rowspan: 2,
            width: 130,
            height: 400,
            items: [
                listViewOpcionesConsulta
            ]
        },
		{
            //title:'Detalle',
            width:850,
            height:400,
            colspan:2,
            autoScroll:true,
            items : [
                tabDatosGeneralesPoliza,
				tabPanelAgentes
            ]
        }]
    });
    
    
    ////Hide elements
    //// if(panelInfoGralPoliza.isVisible()) {
        //// panelInfoGralPoliza.hide();
    //// }
    //// if(gridDatosTarificacion.isVisible()) {
        //// gridDatosTarificacion.hide();
    //// }
	if(tabDatosGeneralesPoliza.isVisible()) {
        tabDatosGeneralesPoliza.hide();
    }
	if(tabPanelAgentes.isVisible()) {
        tabPanelAgentes.hide();
    }
	

    /**
    * 
    * @param String/Object
    */
    function cargaStoreSuplemento(params){
		
		console.log('Params busqueda de suplemento=');console.log(params);
		
        gridPanelSuplemento.setLoading(true);
		/*
		var clbkSuplemento = function(options, success, response) {
			gridPanelSuplemento.setLoading(false);
			gridPanelSuplemento.getView().el.focus();
			if (!success) {
				setMessage(_MSG_ERROR, _MSG_BUSQUEDA_SIN_DATOS, Ext.Msg.OK, Ext.Msg.ERROR);
			}
			var jsonResp = Ext.decode(response.responseText);
			console.log("jsonResp=");
			console.log(jsonResp);
			console.log(jsonResp.datosSuplemento.length);
			if(jsonResp.datosSuplemento.length == 0){
				setMessage(_MSG_SIN_DATOS, _MSG_BUSQUEDA_SIN_DATOS, Ext.Msg.OK, Ext.Msg.INFO);
			}
			
			//Limpiar seleccion de la lista de opciones de consulta
			listViewOpcionesConsulta.collapse();
			listViewOpcionesConsulta.getSelectionModel().deselectAll();
		}
        cargarGridPaginado(storeSuplemento, _URL_CONSULTA_DATOS_SUPLEMENTO, 'datosSuplemento', params, clbkSuplemento);
		*/
		
		storeSuplemento.load({
            params: params,
            callback: function(records, operation, success) {
				
                gridPanelSuplemento.setLoading(false);
				console.log('gridPanelSuplemento setted false');
                gridPanelSuplemento.getView().el.focus();
				console.log('gridPanelSuplemento.getView().el.focus()');
                if (!success) {
                    setMessage(_MSG_ERROR, _MSG_BUSQUEDA_SIN_DATOS, Ext.Msg.OK, Ext.Msg.ERROR);
                }
                if(records.length == 0){
                    setMessage(_MSG_SIN_DATOS, _MSG_BUSQUEDA_SIN_DATOS, Ext.Msg.OK, Ext.Msg.INFO);
                }
                
                //Limpiar seleccion de la lista de opciones de consulta
                listViewOpcionesConsulta.collapse();
                listViewOpcionesConsulta.getSelectionModel().deselectAll();
                
                //console.log(operation.response.responseText);
                //console.log(records);
                //console.log(records.length);
            }
        });
		
    }
	 
	
	function cargarGridPaginado(_store, _url, _root, _params, _callback) {
		Ext.Ajax.request(
		{
			url       : _url,
			params    : _params,
			callback  : _callback,
			success   : function(response)
			{
				var jsonResponse = Ext.decode(response.responseText);
				_store.setProxy(
				{
					type         : 'memory',
					enablePaging : true,
					reader       : 'json',
					data         : jsonResponse[_root]
				});
				_store.load();
			},
			failure   : function()
			{
				Ext.Msg.show(
				{
					title   : 'Error',
					icon    : Ext.Msg.ERROR,
					msg     : 'Error cargando los datos de ' + _url,
					buttons : Ext.Msg.OK
				});
			}
		});
	}

	/* FUNCION PARA OBTENER RECIBOS DEL AGENTE */
	function obtieneMontosRecibo(records) {
		sum=0;
    	for(var i=0;i<records.length;i++) {
            sum+=parseFloat(records[i].get("ptimport"));
        }
	}
    
});