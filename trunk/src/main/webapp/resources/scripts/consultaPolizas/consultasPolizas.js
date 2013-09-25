Ext.require([ 'Ext.form.*', 'Ext.data.*', 'Ext.chart.*', 'Ext.grid.Panel','Ext.layout.container.Column', 'Ext.selection.CheckboxModel' ]);

Ext.onReady(function() {

    Ext.selection.CheckboxModel.override( {
        mode: 'SINGLE',
        allowDeselect: true
    });
    
    /*Conversión para el tipo de moneda*/
    Ext.util.Format.thousandSeparator = ',';
    Ext.util.Format.decimalSeparator = '.';

    var form = false, selectedRec = false;
    
    var consultasJson = {
            
    };
    ///////////////////////////////////////////////
	
	
	
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
            url: _URL_TIPOS_CONSULTA_DUMMY,
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
        //renderTo: Ext.getBody(),
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
            flex: 95,
            dataIndex: 'value'
        }]
    });
    
    // little bit of feedback
    listViewOpcionesConsulta.on('selectionchange', function(view, nodes){
        
//        console.log('view=');
//        console.log(view);
//        console.log('nodes=');
//        console.log(nodes);
        
        var len = nodes.length,
            suffix = len === 1 ? '' : 's',
            str = '({0} item{1} seleccionado{1})</i>';
        listViewOpcionesConsulta.setTitle(Ext.String.format(str, len, suffix));
        console.log(gridPanelSuplemento.getSelectionModel().hasSelection());
        if (gridPanelSuplemento.getSelectionModel().hasSelection()) {
            var rowSelected = gridPanelSuplemento.getSelectionModel().getSelection()[0];
            panelBusqueda.down('form').getForm().findField("params.suplemento").setValue(rowSelected.get('nmsuplem'));
            console.log(rowSelected);
            console.log('Suplemento=' + rowSelected.get('nsuplogi'));
            console.log(panelBusqueda.down('form').getForm());
            console.log(panelBusqueda.down('form').getForm().findField("params.nmpoliza").getValue());
            
            // Consultar Datos Generales de la Poliza
            storeDatosPoliza.load({
                params : panelBusqueda.down('form').getForm().getValues(),
                callback : function(records, operation, success) {
                    if (success) {
                        if (records.length > 0) {
                            Ext.getCmp("formularioPoliza").getForm().loadRecord(records[0]);
                        } else {
                            Ext.Msg.show({
                                title : 'Error',
                                msg : 'La P&oacute;iza no existe, verifique sus datos',
                                buttons : Ext.Msg.OK,
                                icon : Ext.Msg.ERROR
                            });
                        }
                    } else {
                        Ext.Msg.show({
                            title : 'Error',
                            msg : 'La P&oacute;iza no existe, verifique sus datos',
                            buttons : Ext.Msg.OK,
                            icon : Ext.Msg.ERROR
                        });
                    }

                }
            });
            panelInfoGralPoliza.show();
			tabDatosGeneralesPoliza.show();
            /**Datos para la Tarificación**/ 
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
							gridDatosTarificacion.show();
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
			/**Datos para asegurados**/ 
			storeDatosAsegurado.load({
				params: panelBusqueda.down('form').getForm().getValues(),
				callback: function(records, operation, success){
					if(success){
						
						//if(records.length <= 0){
							//gridDatosAsegurado.hide();
                        //}else {
							gridDatosAsegurado.show();
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
    });
    

    Ext.define('Suplemento', {
        extend: 'Ext.data.Model',
        fields: [
            {name: 'dstipsup'},
            {name: 'feemisio'},
            {name: 'feinival', dateFormat: 'd/m/Y'},
            {name: 'nlogisus'},
            {name: 'nmsuplem'},
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
            url : _URL_DATOS_SUPLEMENTO,
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
            text : 'Tipo de suplemento',
            dataIndex : 'dstipsup',
            width:150
        }, {
            text : 'Fecha de emisi\u00F3n',
            dataIndex : 'feemisio',
            renderer: Ext.util.Format.dateRenderer('d M Y')
        }, {
            text : 'Fecha inicio',
            dataIndex : 'feinival',
            renderer: Ext.util.Format.dateRenderer('d M Y')
        }, {
            text : 'Nlogisus',
            dataIndex : 'nlogisus'
        }, {
            text : 'N\u00FAmero de suplemento',
            dataIndex : 'nmsuplem',
            width:150
        }, {
            text : 'Nsuplogi',
            dataIndex : 'nsuplogi'
        }, {
            text : 'Ptpritot',
            dataIndex : 'ptpritot',
            renderer : 'usMoney'
        }],

        listeners : {
            selectionchange : function(model, records) {
                
                //Limpiar seleccion de la lista de opciones de consulta
                listViewOpcionesConsulta.getSelectionModel().deselectAll();
                listViewOpcionesConsulta.expand();
				panelInfoGralPoliza.getForm().reset();
				gridDatosTarificacion.hide();
				storeTarificacion.removeAll();
				tabDatosGeneralesPoliza.setActiveTab(0);
                
                console.log('model=');
                console.log(model);
                console.log('records=');
                console.log(records);
                
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
          {type:'string',        name:'dstempot'      }
      ]
  });

  // Store
  var storeDatosPoliza = new Ext.data.Store({
   model: 'RowDatosPoliza',
   proxy:
   {
        type: 'ajax',
        url : _URL_VALIDA_DATOS_POLIZA,
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
        //renderTo : Ext.getBody(),
        defaults : {
            bodyPadding : 3,
            border : false
        },
        items : [ {
            layout : 'hbox',
            items : {xtype: 'textfield', id: 'nmsolici',  name: 'nmsolici', fieldLabel: 'N&uacute;mero de solicitud', width: 250, labelWidth: 120, readOnly: true}
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
           url : _URL_VALIDA_DATOS_TARIFICACION,
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
                        width           :130 , 
                        align           :'right' , 
                        renderer        :Ext.util.Format.usMoney, 
                        summaryType     :'sum'
                    },
                    {
                        text            :'Monto de la Prima',
                        dataIndex       :'montoPrima',
                        width           : 130,
                        align           :'right',        
                        renderer        :Ext.util.Format.usMoney,
                        summaryType     :'sum'
                    },
                    {
                        text            : 'Monto de la Comisi&oacute;n',
                        dataIndex       :'montoComision',
                        width           : 130,
                        renderer        :Ext.util.Format.usMoney,
                        align           :'right',        
                        summaryType     :'sum'
                    }
                ]//,
                //renderTo : Ext.getBody()
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
		  url : _URL_VALIDA_DATOS_ASEGURADO,
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
			url : _URL_VALIDA_POLIZA_ASEGURADO,
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
        ]//,
        //renderTo : Ext.getBody()
    });
    
    //var windowPolizas = new Ext.Window({
    var windowPolizas= Ext.create('Ext.window.Window', {
        //renderTo: document.body,
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
	    //renderTo: document.body,
	    items: [{
	        title : 'DATOS DE LA POLIZA',
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
	        title: 'DATOS TARIFICACION',
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
			title : 'DOCUMENTACION',
			width: '350',
			loader : {
				url : _URL_DOCUMENTOS,
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
                    url: _URL_DATOS_SUPLEMENTO,
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
                                            xtype : 'numberfield',
                                            name : 'params.cdunieco',
                                            fieldLabel : 'Unidad econ\u00F3mica',
                                            labelWidth : 120,
                                            width: 200,
                                            maxLength : 3/*,
                                            value: 1*/
                                        },
                                        {
                                            xtype:'tbspacer',
                                            width: 5
                                        },
                                        {
                                            xtype : 'numberfield',
                                            name : 'params.cdramo',
                                            fieldLabel : 'Ramo',
                                            maxLength : 3,
                                            //value: 2,
                                            labelWidth : 50,
                                            width: 120
                                        },
                                        {
                                            xtype:'tbspacer',
                                            width: 5
                                        },
                                        {
                                            xtype : 'textfield',
                                            name : 'params.estado',
                                            fieldLabel : 'Estado',
                                            maxLength : 1,
                                            labelWidth : 50,
                                            width: 120/*,
                                            value: 'W'*/
                                        },
                                        {
                                            xtype:'tbspacer',
                                            width:5
                                        },
                                        {
                                            xtype: 'numberfield',
                                            name : 'params.nmpoliza',
                                            fieldLabel : 'N\u00FAm. de p\u00F3liza',
                                            labelWidth : 100,
                                            width: 200,
                                            minValue : 0,
                                            maxValue : 999999,
                                            maxLength : 6/*,
                                            value: 1250*/
                                        },{
                                            xtype: 'hiddenfield',
                                            name : 'params.suplemento'
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
                                        enforceMaxLength: true
                                    },
                                    items : [
                                        {
                                            xtype: 'textfield',
                                            name : 'params.rfc',
                                            fieldLabel : 'RFC',
                                            labelWidth : 20,
                                            maxLength : 13/*,
                                            value: 'PUMC820429B61'*/
                                        }
                                    ]
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
								
								console.log(formBusqueda.getValues());
								
								//Obtenemos el valor elegido en 'groupTipoBusqueda' para elegir el tipo de busqueda a realizar.
                                switch (formBusqueda.findField('groupTipoBusqueda').getValue().tipoBusqueda) {
									case 1:
										// Busqueda por Datos Generales de la poliza:
										cargaStoreSuplemento(formBusqueda.getValues());
										break;
									case 2:
										// Busqueda de polizas por RFC:
										//cargarGridPolizasAsegurado(formBusqueda.getValues());
										var clbk = function(options, success, response) {
											console.log('clbk');
											console.log(options);
											console.log(success);
											console.log(response);
											if(success){
												var jsonResponse = Ext.decode(response.responseText);
												console.log(jsonResponse.polizasAsegurado.length);
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
													 msg: 'No existe P&oacute;liza para el asegurado',
													 buttons: Ext.Msg.OK,
													 icon: Ext.Msg.ERROR
												 });
											}
										}
										cargarGridPaginado(storePolizaAsegurado, 
											_URL_VALIDA_POLIZA_ASEGURADO, 
											'polizasAsegurado', 
											formBusqueda.getValues(), 
											clbk);
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
                tabDatosGeneralesPoliza
            ]
        }]
    });
    
    
    ////Hide elements
    if(panelInfoGralPoliza.isVisible()) {
        panelInfoGralPoliza.hide();
    }
    if(gridDatosTarificacion.isVisible()) {
        gridDatosTarificacion.hide();
    }
	if(tabDatosGeneralesPoliza.isVisible()) {
        tabDatosGeneralesPoliza.hide();
    }

    /**
    * 
    * @param String/Object
    */
    function cargaStoreSuplemento(params){
        gridPanelSuplemento.setLoading(true);
		/*
		var clbkSuplemento = function(options, success, response) {
			gridPanelSuplemento.setLoading(false);
			gridPanelSuplemento.getView().el.focus();
			if (!success) {
				setMessage(_MSG_ERROR, _MSG_ERROR_HISTORICO_MOVIMIENTOS, Ext.Msg.OK, Ext.Msg.ERROR);
			}
			var jsonResp = Ext.decode(response.responseText);
			console.log("jsonResp=");
			console.log(jsonResp);
			console.log(jsonResp.datosSuplemento.length);
			if(jsonResp.datosSuplemento.length == 0){
				setMessage(_MSG_SIN_DATOS, _MSG_SIN_DATOS_HISTORICO_MOVIMIENTOS, Ext.Msg.OK, Ext.Msg.INFO);
			}
			
			//Limpiar seleccion de la lista de opciones de consulta
			listViewOpcionesConsulta.collapse();
			listViewOpcionesConsulta.getSelectionModel().deselectAll();
		}
        cargarGridPaginado(storeSuplemento, _URL_DATOS_SUPLEMENTO, 'datosSuplemento', params, clbkSuplemento);
		*/
		
		storeSuplemento.load({
            params: params,
            callback: function(records, operation, success) {
                gridPanelSuplemento.setLoading(false);
                gridPanelSuplemento.getView().el.focus();
                if (!success) {
                    setMessage(_MSG_ERROR, _MSG_ERROR_HISTORICO_MOVIMIENTOS, Ext.Msg.OK, Ext.Msg.ERROR);
                }
                if(records.length == 0){
                    setMessage(_MSG_SIN_DATOS, _MSG_SIN_DATOS_HISTORICO_MOVIMIENTOS, Ext.Msg.OK, Ext.Msg.INFO);
                }
                
                //Limpiar seleccion de la lista de opciones de consulta
                listViewOpcionesConsulta.collapse();
                listViewOpcionesConsulta.getSelectionModel().deselectAll();
                
                console.log(operation.response.responseText);
                console.log(records);
                console.log(records.length);
            }
        });
		
    }
	
		

   
	function cargarGridPolizasAsegurado(p_params) {
		Ext.Ajax.request({
			url       : _URL_VALIDA_POLIZA_ASEGURADO,
			params: p_params,			
			callback: function(options, success, response) {
				if(success){
					var jsonResponse = Ext.decode(response.responseText);
					console.log(jsonResponse.polizasAsegurado.length);
					storePolizaAsegurado.setProxy(
					{
						type         : 'memory',
						enablePaging : true,
						reader       : 'json',
						data         : jsonResponse.polizasAsegurado
					
					});
					storePolizaAsegurado.load({
						callback: function(records, operation, success) {
							if(success){
								windowPolizas.show();
							}
						}
					});
				}else{
					Ext.Msg.show({
						 title: 'Error',
						 msg: 'No existe P&oacute;liza para el asegurado',
						 buttons: Ext.Msg.OK,
						 icon: Ext.Msg.ERROR
					 });
				}
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


    
});