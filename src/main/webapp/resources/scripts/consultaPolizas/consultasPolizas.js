Ext.require([ 'Ext.form.*', 'Ext.data.*', 'Ext.chart.*', 'Ext.grid.Panel','Ext.layout.container.Column', 'Ext.selection.CheckboxModel' ]);

Ext.onReady(function() {

    Ext.selection.CheckboxModel.override( {
        mode: 'SINGLE',
        allowDeselect: true
    });
    
    // Conversion para el tipo de moneda
    Ext.util.Format.thousandSeparator = ',';
    Ext.util.Format.decimalSeparator = '.';
    
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
        collapsible:true,
        collapsed:true,
        store: storeTiposConsulta,
        multiSelect: false,
        hideHeaders:true,
        viewConfig: {
            emptyText: 'No hay tipos de consulta'
        },
        columns: [{
            flex: 1,
            dataIndex: 'value'
        }],
        listeners : {
            selectionchange : function(view, nodes) {
                tabDatosGeneralesPoliza.hide();
                tabPanelAgentes.hide();
            
                if (this.getSelectionModel().hasSelection()) {
                    var tipoConsultaSelected = this.getSelectionModel().getSelection()[0];
                    
                    if (gridSuplementos.getSelectionModel().hasSelection()) {
                    
                        switch (tipoConsultaSelected.get('key')) {
                            case 1: //Consulta de Datos generales
                                
                                //Mostrar session de datos generales:
                                tabDatosGeneralesPoliza.show();
                                
                                //Datos de Copagos de poliza
                                storeCopagosPoliza.load({
                                    params: panelBusqueda.down('form').getForm().getValues(),
                                    callback: function(records, operation, success){
                                        if(!success){
                                            showMessage('Error', 'Error al obtener los copagos de la p\u00F3liza', 
                                                Ext.Msg.OK, Ext.Msg.ERROR)
                                        }              
                                    }
                                });
                                
                                //Datos para asegurados
                                storeAsegurados.load({
                                    params: panelBusqueda.down('form').getForm().getValues(),
                                    callback: function(records, operation, success){
                                        if(!success){
                                            showMessage('Error', 'Error al obtener los datos del asegurado', Ext.Msg.OK, Ext.Msg.ERROR);
                                        }
                                    }
                                });
                                break;
                                
                                case 2: //Consulta de Agentes
                                    
                                    if (gridSuplementos.getSelectionModel().hasSelection()) {
                                        
                                        //Mostrar seccion de agentes:
                                        tabPanelAgentes.show();
                                    
                                        //Agentes de la poliza
                                        storeAgentesPol.load({
                                        	params: {
                                        		'params.PV_CDUNIECO_I' : panelBusqueda.down('form').getForm().findField("params.cdunieco").getValue(),
                                        		'params.PV_CDRAMO_I'   : panelBusqueda.down('form').getForm().findField("params.cdramo").getValue(),
                                        		'params.PV_ESTADO_I'   : panelBusqueda.down('form').getForm().findField("params.estado").getValue(),
                                        		'params.PV_NMPOLIZA_I' : panelBusqueda.down('form').getForm().findField("params.nmpoliza").getValue(),
                                        		'params.PV_NMSUPLEM_I' : panelBusqueda.down('form').getForm().findField("params.suplemento").getValue()
                                        	},
                                        	callback: function(records, operation, success) {
                                                if(!success){
                                                    showMessage('Error', 'Error al obtener los datos del agente, intente m\u00E1s tarde',
                                                            Ext.Msg.OK, Ext.Msg.ERROR);
                                                }
                                             }
                                        });
                                        
                                        // Obtenemos los recibos de los agentes:
                                        storeRecibosAgente.load({
                                            params: panelBusqueda.down('form').getForm().getValues(),
                                            callback: function(records, operation, success){
                                                if(success){
                                                    if(records.length > 0){
                                                        Ext.getCmp('montoTotalRecibos').setText(obtieneMontosRecibo(records));
                                                    }
                                                }else{
                                                    showMessage('Error', 'Error al obtener los recibos de dicho Agente', Ext.Msg.OK, Ext.Msg.ERROR);
                                                }                    
                                            }
                                        });
                                    }
                                    break;
                                case 3: //Consulta de Recibos
                                break;                    
                        }//end switch                
                    } else {
                        showMessage('Aviso', 'Debe seleccionar primero un hist\u00F3rico', Ext.Msg.OK, Ext.Msg.WARN)
                    }
                }
            }
        }
    });
    
    // Historico de movimientos (suplementos):
    Ext.define('SuplementoModel', {
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
            {name: 'nsuplogi', type:'int'},
            {name: 'ptpritot', type : 'float'}
        ]
    });
    
    
    var storeSuplementos = new Ext.data.Store({
    	pageSize : 20,
        model: 'SuplementoModel',
        proxy: {
            type         : 'memory'
                ,enablePaging : true
                ,reader      : 'json'
                ,data        : []
            }
    });
    
    var gridSuplementos = Ext.create('Ext.grid.Panel', {
        id : 'suplemento-form',
        store : storeSuplementos,
        selType: 'checkboxmodel',
        //autoScroll:true,
        defaults: {sortable : true, width:120, align : 'right'},
        columns : [{
            text : '#',
            dataIndex : 'nsuplogi',
            width:50
        }, {
            id : 'dstipsup',
            text : 'Tipo de endoso',
            dataIndex : 'dstipsup',
            width:250
        }, {
            text : 'Fecha de emisi\u00F3n',
            dataIndex : 'feemisio',
            format: 'd M Y',
            width:150
        }, {
            text : 'Fecha inicio vigencia',
            dataIndex : 'feinival',
            format: 'd M Y',
            width:150
        }, {
            text : 'Prima total',
            dataIndex : 'ptpritot',
            renderer : 'usMoney',
            width:150
        }],

        listeners : {
            selectionchange : function(model, records) {
                
                listViewOpcionesConsulta.up('panel').setTitle('');
                
                //Limpiar seleccion de la lista de opciones de consulta
                limpiaSeleccionTiposConsulta();
                
                //Limpiar la seccion de informacion principal:
                limpiaSeccionInformacionPrincipal();
                
                if(this.getSelectionModel().hasSelection()) {
                    
                    // Mostrar listado de tipos de consulta:
                    listViewOpcionesConsulta.up('panel').setTitle('Elija una consulta:');
                    listViewOpcionesConsulta.expand();
                    
                
                    //Lenar campos de formulario de busqueda:
                    var rowSelected = gridSuplementos.getSelectionModel().getSelection()[0];
                    panelBusqueda.down('form').getForm().findField("params.cdunieco").setValue(rowSelected.get('cdunieco'));
                    panelBusqueda.down('form').getForm().findField("params.cdramo").setValue(rowSelected.get('cdramo'));
                    panelBusqueda.down('form').getForm().findField("params.estado").setValue(rowSelected.get('estado'));
                    panelBusqueda.down('form').getForm().findField("params.nmpoliza").setValue(rowSelected.get('nmpoliza'));
                    panelBusqueda.down('form').getForm().findField("params.suplemento").setValue(rowSelected.get('nmsuplem'));
                    
                    //console.log('Params busqueda de datos grales poliza=');console.log(panelBusqueda.down('form').getForm().getValues());

                    // Consultar Datos Generales de la Poliza:
                    storeDatosPoliza.load({
                        params : panelBusqueda.down('form').getForm().getValues(),
                        callback : function(records, operation, success) {
                            if (success) {
                                if (records.length > 0) {
                                    // Se asigna valor al parametro de busqueda:
                                    panelBusqueda.down('form').getForm().findField("params.cdagente").setValue(records[0].get('cdagente'));
                                    
                                    // Se llenan los datos generales de la poliza elegida
                                    panelDatosPoliza.getForm().loadRecord(records[0]);
                                } else {
                                    showMessage('Aviso', 
                                        'No existen datos generales de la p\u00F3liza elegidaLa P&oacute;iza no existe, verifique sus datos', 
                                        Ext.Msg.OK, Ext.Msg.ERROR);
                                }
                            } else {
                                showMessage('Error', 
                                    'Error al obtener los datos generales de la p\u00F3liza elegida, intente m\u00E1s tarde',
                                    Ext.Msg.OK, Ext.Msg.ERROR);
                            }

                        }
                    });
                }
            }
        }
    });
    gridSuplementos.store.sort([
        { 
        	property    : 'nsuplogi',
        	direction   : 'DESC'
        }
    ]);
    
    // Modelo
    Ext.define('DatosPolizaModel', {
        extend: 'Ext.data.Model',
        fields: [
            {type:'string', name:'nmsolici'},
            {type:'string', name:'titular'},
            {type:'string', name:'cdrfc'},
            {type:'date',   name:'feemisio', dateFormat: 'd/m/Y'},
            {type:'date',   name:'feefecto', dateFormat: 'd/m/Y'},
            {type:'date',   name:'feproren', dateFormat: 'd/m/Y'},
            {type:'string', name:'dstarifi'},
            {type:'string', name:'dsmoneda'},
            {type:'string', name:'dscuadro'},
            {type:'string', name:'dsperpag'},
            {type:'string', name:'dstempot'},
            {type:'string', name:'nmpoliex'},
            {type:'string', name:'cdagente'},
            {type:'string', name:'statuspoliza'},
            {type:'string', name:'nmpolant'},
            {type:'string', name:'dsunieco'},
            {type:'string', name:'dsramo'},
            {type:'string', name:'dsplan'},
            {type:'string', name:'dstipsit'}
        ]
    });

    // Store
    var storeDatosPoliza = new Ext.data.Store({
        model: 'DatosPolizaModel',
        proxy: {
            type: 'ajax',
            url : _URL_CONSULTA_DATOS_POLIZA,
            reader: {
                type: 'json',
                root: 'datosPoliza'
            }
        }
    });
    
    // FORMULARIO DATOS DE LA POLIZA
    var panelDatosPoliza = Ext.create('Ext.form.Panel', {
        model : 'DatosPolizaModel',
        width : 815 ,
        border : false,
        //height : 280,
        defaults : {
            bodyPadding : 5,
            border : false
        },
        items : [ {
            layout : 'hbox',
            items : [
                {xtype: 'textfield', name: 'dsunieco', fieldLabel: 'Sucursal',      readOnly: true, labelWidth: 120, width: 300},
                {xtype: 'textfield', name: 'dsramo',   fieldLabel: 'Producto',      readOnly: true, labelWidth: 65,  width: 290, labelAlign: 'right'},
                {xtype: 'textfield', name: 'nmpoliex', fieldLabel: 'P&oacute;liza', readOnly: true, labelWidth: 50,  width: 210, labelAlign: 'right'}
            ]
        },{
            layout : 'hbox',
            items : [
                {xtype: 'textfield', name: 'dsplan',   fieldLabel: 'Plan',      readOnly: true, labelWidth: 120, width: 300},
                {xtype: 'textfield', name: 'dstipsit', fieldLabel: 'Modalidad', readOnly: true, labelWidth: 65,  width: 290, labelAlign: 'right'}
            ]
        },{
            layout : 'hbox',
            items : [
                {xtype: 'textfield',                  name: 'statuspoliza', fieldLabel: 'Estatus',                readOnly: true, labelWidth: 120, width: 300},
                {xtype: 'textfield', id: 'nmpolant',  name: 'nmpolant',     fieldLabel: 'P&oacute;liza anterior', readOnly: true, labelWidth: 100, width: 210, labelAlign: 'right'},
                {xtype: 'textfield', id: 'nmsolici',  name: 'nmsolici',     fieldLabel: 'No. de solicitud',       readOnly: true, labelWidth: 120, width: 290, labelAlign: 'right'}
            ]
        }, {
            layout : 'hbox',
            items : [ 
                {xtype:'textfield', name:'titular', fieldLabel: 'Contratante', readOnly: true, labelWidth: 120, width: 590}, 
                {xtype:'textfield', name:'cdrfc',   fieldLabel: 'RFC',         readOnly: true, labelWidth: 80,  width: 210, labelAlign: 'right'}
            ]
        }, {
            layout : 'hbox',
            items : [ 
                {xtype: 'datefield', name: 'feemisio', fieldLabel: 'Fecha emisi&oacute;n',    format: 'd/m/Y', readOnly: true, labelWidth: 120, width: 300}, 
                {xtype: 'datefield', name: 'feefecto', fieldLabel: 'Fecha de efecto',         format: 'd/m/Y', readOnly: true, labelWidth: 200, width: 290, labelAlign: 'right'}, 
                {xtype: 'datefield', name: 'feproren', fieldLabel: 'Fecha renovaci&oacute;n', format: 'd/m/Y', readOnly: true, labelWidth: 120, width: 210, labelAlign: 'right'}
            ]
        }, {
            layout : 'hbox',
            items : [ 
                {xtype: 'textfield', name: 'dstarifi', fieldLabel: 'Tipo de tarificaci&oacute;n', readOnly: true, labelWidth: 120, width: 300},
                {xtype: 'textfield', name: 'dscuadro', fieldLabel: 'Cuadro de comisiones',        readOnly: true, labelWidth: 150, width: 290, labelAlign: 'right'},
                {xtype: 'textfield', name: 'dsmoneda', fieldLabel: 'Moneda',                      readOnly: true, labelWidth: 120, width: 210, labelAlign: 'right'}
            ]
        }, {
            layout : 'hbox',
            items : [ 
                {xtype: 'textfield', name: 'dsperpag', fieldLabel: 'Periodicidad de pago',  readOnly: true, labelWidth: 120, width: 300},
                {xtype: 'textfield', name: 'dstempot', fieldLabel: 'Tipo de P&oacute;liza', readOnly: true, labelWidth: 120, width: 290, labelAlign: 'right'}
            ]
        }
        ]
    });
    
    
    /**INFORMACION DEL GRID DE TARIFICACION**/
    //-------------------------------------------------------------------------------------------------------------    
    //Modelo
    Ext.define('DatosTarificacionModel',{
        extend: 'Ext.data.Model',
        fields: [
              {type:'string',    name:'dsgarant'      },
              {type:'float',    name:'montoComision' },
              {type:'float',    name:'montoPrima'    },
              {type:'string',    name:'sumaAsegurada' }
        ]
    });
    
    // Store
    var storeDatosTarificacion = new Ext.data.Store({
        model: 'DatosTarificacionModel',
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
        store   : storeDatosTarificacion,
        autoScroll:true,
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
                align           :'right'  
                
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
    
    
    /**INFORMACION DEL GRID DE COPAGOS**/
    //-------------------------------------------------------------------------------------------------------------    
    //Modelo
    Ext.define('CopagosPolizaModel',{
        extend: 'Ext.data.Model',
        fields: [
              {type:'int',    name:'orden'      },
              {type:'string',    name:'descripcion' },
              {type:'string',    name:'valor' },
              {type:'string',    name:'agrupador' }
        ]
    });
    // Store
    var storeCopagosPoliza = new Ext.data.Store({
        model: 'CopagosPolizaModel',
        groupField : 'agrupador',
        proxy: {
           type: 'ajax',
           url : _URL_CONSULTA_COPAGOS_POLIZA,
            reader: {
                type: 'json',
                root: 'datosCopagosPoliza'
            }
        }
    });
    // GRID PARA LOS DATOS DE COPAGOS
    var gridCopagosPoliza = Ext.create('Ext.grid.Panel', {
        width   : 500,
        viewConfig: {
            stripeRows: false,
            enableTextSelection: true
        },
        //title   : 'DATOS COPAGOS',
        store   : storeCopagosPoliza,
        autoScroll:true,
        id      : 'gridCopagosPoliza',
        columns: [
            //{text:'Orden',            dataIndex:'orden',       width:50, sortable:false, hidden:true},
            {text:'Descripci\u00F3n', dataIndex:'descripcion', width:300, align:'left', sortable:false},
            {text:'Valor',            dataIndex:'valor',       width:200, align:'left', sortable:false}
        ]
        ,features: [{
            groupHeaderTpl: '{name}',
            ftype:          'grouping',
            startCollapsed: false
        }]
    });
    gridCopagosPoliza.store.sort([
        { 
            property    : 'orden',
            direction   : 'ASC'
        }
    ]);
    
    
    /**INFORMACION DE LA SECCION DE ASEGURADOS**/
    //-------------------------------------------------------------------------------------------------------------
    // Modelo
    Ext.define('AseguradosModel', {
        extend: 'Ext.data.Model',
        fields: [
            {type:'string', name:'cdperson'},
            {type:'string', name:'cdrfc'},
            {type:'string', name:'cdrol'},
            {type:'string', name:'dsrol'},
            {type:'date'  , name:'fenacimi', dateFormat: 'd/m/Y'},
            {type:'string', name:'nmsituac'},
            {type:'string', name:'cdtipsit'},
            {type:'string', name:'sexo'},
            {type:'string', name:'nombre'},
            {type:'string', name:'status'},
            {type:'string', name:'parentesco'}
        ]
    });
    
    // Store
    var storeAsegurados = new Ext.data.Store({
     model: 'AseguradosModel',
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
        store   : storeAsegurados,
        id      : 'gridDatosAsegurado',
        width   : 830,
        autoScroll:true,
        items:[{
           xtype:'textfield', name:'cdrfc', fieldLabel: 'RFC', readOnly: true, labelWidth: 120
        }],
        columns: [
            //{text:'Rol',dataIndex:'dsrol',width:130 , align:'left'},
            {text:'Tipo de <br/>asegurado',dataIndex:'parentesco',width:100 , align:'left'},
            {text:'Clave <br/>Asegurado',dataIndex:'cdperson',width:100,align:'left'},
            {text:'Nombre',dataIndex:'nombre',width:200,align:'left'},
            {text:'Estatus',dataIndex:'status',width:100,align:'left'},
            {text:'RFC',dataIndex:'cdrfc',width:100,align:'left'},
            {text:'Sexo',dataIndex:'sexo',width:60 , align:'left'},
            {text:'Fecha Nac.',dataIndex:'fenacimi',width:100, align:'left',renderer: Ext.util.Format.dateRenderer('d/m/Y')}
            ,{
            	xtype        : 'actioncolumn',
            	icon         : _CONTEXT+'/resources/fam3icons/icons/lock.png',
            	tooltip      : 'Ver exclusiones',
            	width        : 30,
            	menuDisabled : true,
            	sortable     : false,
            	handler      : function(grid,rowIndex)
            	{
            		var record = grid.getStore().getAt(rowIndex);
            		var values = panelBusqueda.down('form').getForm().getValues();
            		debug('record nmsituac:',record.get('nmsituac'));
            		values['params.nmsituac']=record.get('nmsituac');
            		debug('form values:',values);
            		Ext.create('Ext.window.Window', {
                        title       : 'Exclusiones de la p&oacute;liza',
                        modal       : true,
                        buttonAlign : 'center',
                        autoScroll  : true,
                        width       : 450,
                        height      : 455,
                        loader      :
                        {
                            url      : _URL_LOADER_VER_EXCLUSIONES,
                            scripts  : true,
                            autoLoad : true,
                            params   : values
                        }
                     }).show();
            	}
            },{
            	xtype        : 'actioncolumn',
            	icon         : _CONTEXT+'/resources/fam3icons/icons/information.png',
            	tooltip      : 'Ver detalle del asegurado',
            	width        : 30,
            	menuDisabled : true,
            	sortable     : false,
            	handler      : function(grid, rowIndex) {
            		// Se obtiene los parametros a enviar y se complementan:
            		var record = grid.getStore().getAt(rowIndex);
            		var values = panelBusqueda.down('form').getForm().getValues();
            		values['params.nmsituac']=record.get('nmsituac');
            		values['params.cdtipsit']=record.get('cdtipsit');
            		debug('parametros para obtener los datos de tatrisit:', values);
            		// Se invoca al loader del panel que contendrá los datos de tatrisit:
            		var pnlDatosTatrisit = tabDatosGeneralesPoliza.down('panel[name=pnlDatosTatrisit]');
            		pnlDatosTatrisit.getLoader().load({
            			params: values
            		});
            		pnlDatosTatrisit.setTitle('Detalle de ' + record.get('nombre') + ':');
            	}
            }
        ]
    });
    
    
    
    /**INFORMACION DEL GRID DE LA POLIZA DEL ASEGURADO**/
    //-------------------------------------------------------------------------------------------------------------    
    // Modelo
    Ext.define('PolizaAseguradoModel', {
        extend: 'Ext.data.Model',
        fields: [
            {type:'string', name:'cdramo'},
            {type:'string', name:'cdunieco'},
            {type:'string', name:'dsramo'},
            {type:'string', name:'dsunieco'},
            {type:'string', name:'estado'},
            {type:'string', name:'nmpoliex'},
            {type:'string', name:'nmpoliza'},
            {type:'string', name:'nombreAsegurado'}
        ]
    });
    
    var storePolizaAsegurado = Ext.create('Ext.data.Store', {
        pageSize : 10,
        autoLoad : true,
        model: 'PolizaAseguradoModel',
        proxy    : {
            enablePaging : true,
            reader       : 'json',
            type         : 'memory',
            data         : []
        }
    });
    
    
    // GRID PARA LOS DATOS DEL ASEGURADO
    var gridPolizasAsegurado= Ext.create('Ext.grid.Panel', {
        store : storePolizaAsegurado,
        selType: 'checkboxmodel',
        width : 850,
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
            {text: 'P&oacute;liza', dataIndex: 'nmpoliex', width: 160},
            {text: 'Nombre del asegurado', dataIndex: 'nombreAsegurado', width: 220},
            {text: 'Sucursal', dataIndex: 'dsunieco', width: 180},
            {text: 'Producto', dataIndex: 'dsramo', width:180},
            {text: 'Estado', dataIndex: 'estado', width: 100}
            //,{text: '# poliza', dataIndex: 'nmpoliza', width: 70}
        ]
    });
    
    
    var windowPolizas= Ext.create('Ext.window.Window', {
        title : 'Elija una p&oacute;liza:',
        //height: 400,
        modal:true,
        //width : 660,
        closeAction: 'hide',
        items:[gridPolizasAsegurado],
        buttons:[{
            text: 'Aceptar',
            handler: function(){
                if (gridPolizasAsegurado.getSelectionModel().hasSelection()) {
                    
                    //Asignar valores de la poliza seleccionada al formulario de busqueda
                    var rowPolizaSelected = gridPolizasAsegurado.getSelectionModel().getSelection()[0];
                    var formBusqueda = panelBusqueda.down('form').getForm();
                    formBusqueda.findField("params.nmpoliex").setValue(rowPolizaSelected.get('nmpoliex'));
                    
                    gridPolizasAsegurado.getStore().removeAll();
                    windowPolizas.close();
                    
                    // Recargar store con busqueda de historicos de la poliza seleccionada
                    cargaStoreSuplementos(formBusqueda.getValues());
                }else{
                    showMessage('Aviso', 'Seleccione un registro', Ext.Msg.OK, Ext.Msg.INFO);
                }
            }
        }]
    });
    
    
    var tabDatosGeneralesPoliza = Ext.create('Ext.tab.Panel', {
        width: 830,
        items: [{
            title : 'DATOS DE LA POLIZA',
            border:false,
            items:[{
                items: [panelDatosPoliza]
                   
            }]
        }, {
            //title: 'DATOS TARIFICACION',
        	title: 'COPAGOS',
            //itemId: 'tabDatosTarificacion',
        	itemId: 'tabDatosCopagosPoliza',
            items:[{
                //items: [gridDatosTarificacion]
            	items: [gridCopagosPoliza]
            }]
        }, {
            title: 'ASEGURADOS',
            itemId: 'tabDatosAsegurados',
            items:[
				gridDatosAsegurado, 
				{
					xtype  : 'panel',
					name   : 'pnlDatosTatrisit',
					autoScroll : true,
				    loader: {
				        url: _URL_LOADER_VER_TATRISIT,
				        scripts  : true,
				        loadMask : true,
				        autoLoad : false,
				        ajaxOptions: {
				            method: 'POST'
				        }
				    }
				}
            ]
        }, {
            id: 'tbDocumentos',
            title : 'DOCUMENTACION',
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
                            panelBusqueda.down('form').getForm().findField("params.nmpoliza").getValue(),
                            'smap1.cdunieco' :  panelBusqueda.down('form').getForm().findField("params.cdunieco").getValue(),
                            'smap1.cdramo' :  panelBusqueda.down('form').getForm().findField("params.cdramo").getValue(),
                            'smap1.estado' :  panelBusqueda.down('form').getForm().findField("params.estado").getValue(),
                            'smap1.nmsuplem' :  panelBusqueda.down('form').getForm().findField("params.suplemento").getValue(),
                            'smap1.ntramite' : "",
                            'smap1.tipomov'  : '0'
                        }
                    });
                }
            }
        }, {
        	id: 'tbRecibos',
        	title: 'RECIBOS',
        	autoScroll: true,
        	loader: {
        		url: _URL_LOADER_RECIBOS,
        		scripts: true,
        		autoLoad: false
        	},
        	listeners: {
        		activate: function(tab) {
        			tab.loader.load({
        				params : {
                            'params.cdunieco': panelBusqueda.down('form').getForm().findField("params.cdunieco").getValue(),
                            'params.cdramo'  : panelBusqueda.down('form').getForm().findField("params.cdramo").getValue(),
                            'params.estado'  : panelBusqueda.down('form').getForm().findField("params.estado").getValue(),
                            'params.nmpoliza': panelBusqueda.down('form').getForm().findField("params.nmpoliza").getValue(),
                            'params.nmsuplem': panelBusqueda.down('form').getForm().findField("params.suplemento").getValue()
                        }
        			});
        		}
        	}
        }]    
    });
    
    
    
    /**INFORMACION DE AGENTES EN POLIZA**/
    // Modelo
    Ext.define('AgentesPolModel', {
        extend: 'Ext.data.Model',
        fields: [
            {type:'string', name:'CDAGENTE'},
            {type:'string', name:'NOMBRE'},
            {type:'string', name:'CDTIPOAG'},
            {type:'string', name:'DESCRIPL'},
            {type:'string', name:'PORREDAU'},
            {type:'string', name:'PORPARTI'}
        ]
    });
    
    // Store
    var storeAgentesPol = new Ext.data.Store({
     model: 'AgentesPolModel',
     proxy:
     {
          type: 'ajax',
          url : _URL_CONSULTA_AGENTES_POLIZA,
      reader:
      {
           type: 'json',
           root: 'loadList'
      }
     }
    });
    
    
    var gridAgentesPoliza = Ext.create('Ext.grid.Panel', {
        title   : 'Agentes en la P&oacute;liza',
        store   : storeAgentesPol,
//        width   : 830,
//        autoScroll:true,
        columns: [
            {text:'Agente',dataIndex:'CDAGENTE', flex:1},
            {text:'Nombre',dataIndex:'NOMBRE', flex:2},
            {text:'Tipo Agente',dataIndex:'DESCRIPL',flex:1},
            {text:'Cesi&oacute;n de Comisi&oacute;n',dataIndex:'PORREDAU',flex:1},
            {text:'Participaci&oacute;n',dataIndex:'PORPARTI',flex:1}
        ]
    });
    
    // INFORMACION DEL GRID DE CONSULTA DE RECIBOS DEL AGENTE:
    //Modelo
    Ext.define('RecibosAgenteModel',{
    extend: 'Ext.data.Model',
    fields: [
        {type:'string',    name:'dsgarant'},
        {type:'date',    name:'fefin',    dateFormat: 'd/m/Y'},
        {type:'date',    name:'feinicio', dateFormat: 'd/m/Y'},
        {type:'string',    name:'nmrecibo'},
        {type:'float',    name:'ptimport'}
    ]
    });

    //Store    
    var storeRecibosAgente = new Ext.data.Store({
        model: 'RecibosAgenteModel',
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
        width   : 450,
        style:'color:white;font-weight:bold;',
        items:
        [
            {xtype: 'label', text: 'Monto Total '},
            '->',
            {id:'montoTotalRecibos', xtype: 'label' }
        ]
    });
    
        
    var consultaRecibosAgente = Ext.create('Ext.grid.Panel', {
        title   : 'Ver desglose de coberturas:',
        store   : storeRecibosAgente,
        //autoScroll :true,
        //isExpanded : true,
        collapsible: true,
        collapsed:true,
        width   : 450,
        //height: 250,
        columns: [                   
            {header: 'N&uacute;mero de Recibo',dataIndex: 'nmrecibo', width:200},
            {header: 'Importe', dataIndex:'ptimport', width:250 , align:'right', renderer: Ext.util.Format.usMoney, summaryType: 'sum',summaryRenderer: function(value){ return Ext.String.format('Total     {0}',Ext.util.Format.usMoney( value)); }}
        ],
        features: [{ groupHeaderTpl: '{name} ({rows.length} Item{[values.rows.length > 1 ? "s" : ""]})', ftype:'groupingsummary', summaryType: 'sum', startCollapsed :true }]
    });
    
        
    var tabPanelAgentes = Ext.create('Ext.tab.Panel', {
    	border : false,
    	defaults: {
    		border:false
    	},
        items: [{
            title : 'DATOS DEL AGENTE',
            items:[gridAgentesPoliza]
        }, {
            title: 'RECIBOS DEL AGENTE',
            autoScroll:true,
            items:[totalMontoRecibos, consultaRecibosAgente]
            
        }]
    });
    
    
    // SECCION DE INFORMACION GENERAL:
    
    var panelBusqueda = Ext.create('Ext.Panel', {
        id:'main-panel',
        baseCls:'x-plain',
        renderTo: 'dvConsultasPolizas',
        layout: {
            type: 'column',
            columns: 2
        },
        autoScroll:true,
        defaults: {frame:true, width:200, height: 200, margin : '2'},
        items:[{
            title:'B&Uacute;SQUEDA DE P&Oacute;LIZAS',
            colspan:2,
            width:990,
            height:170,
            items: [
                {
                    xtype: 'form',
                    url: _URL_CONSULTA_DATOS_SUPLEMENTO,
                    border: false,
                    layout: {
                        type:'hbox'
                    },
                    margin: '5',
                    defaults: {
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
                                {boxLabel: 'Por n\u00FAmero de p\u00F3liza', name: 'tipoBusqueda', inputValue: 1, checked: true, width: 160},
                                {boxLabel: 'Por RFC', name: 'tipoBusqueda', inputValue: 2},
                                {boxLabel: 'Por clave de asegurado', name: 'tipoBusqueda', inputValue: 3},
                                {boxLabel: 'Por nombre', name: 'tipoBusqueda', inputValue: 4}
                                
                            ],
                            listeners : {
                                change : function(radiogroup, newValue, oldValue, eOpts) {
                                	Ext.getCmp('subpanelBusquedas').query('panel').forEach(function(c){c.hide();});
                                	//Ext.getCmp('subpanelBusquedas').query('textfield').forEach(function(c){c.setValue('');});
                                	this.up('form').getForm().findField('params.rfc').setValue('');
                                	this.up('form').getForm().findField('params.cdperson').setValue('');
                                	this.up('form').getForm().findField('params.nombre').setValue('');
                                	
                                    switch (newValue.tipoBusqueda) {
                                        case 1:
                                            Ext.getCmp('subpanelBusquedaGral').show();
                                            break;
                                        case 2:
                                            Ext.getCmp('subpanelBusquedaRFC').show();
                                            break;
                                        case 3:
                                            Ext.getCmp('subpanelBusquedaCodigoPersona').show();
                                            break;
                                        case 4:
                                            Ext.getCmp('subpanelBusquedaNombre').show();
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
                        	id: 'subpanelBusquedas',
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
                                        enforceMaxLength: true,
                                        msgTarget: 'side'
                                    },
                                    items : [
                                        {
                                            // Numero de poliza externo
                                            xtype : 'textfield',
                                            name : 'params.nmpoliex',
                                            fieldLabel : 'N&uacute;mero de P&oacute;liza',
                                            labelWidth : 120,
                                            width: 300,
                                            maxLength : 30,
                                            allowBlank: false
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
                                            fieldLabel : 'RFC',
                                            maxLength : 13,
                                            allowBlank: false
                                        }
                                    ]
                                },
                                {
                                    id: 'subpanelBusquedaCodigoPersona',
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
                                            xtype: 'numberfield',
                                            name : 'params.cdperson',
                                            fieldLabel : 'Clave de asegurado',
                                            maxLength : 9,
                                            allowBlank: false,
                                            minValue: 0
                                        }
                                    ]
                                },
                                {
                                    id: 'subpanelBusquedaNombre',
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
                                            name : 'params.nombre',
                                            fieldLabel : 'Nombre',
                                            maxLength : 255,
                                            allowBlank: false
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
                            handler: function(btn, e) {
                                var formBusqueda = this.up('form').getForm();
                                //Obtenemos el valor elegido en 'groupTipoBusqueda' para elegir el tipo de busqueda a realizar.
                                switch (formBusqueda.findField('groupTipoBusqueda').getValue().tipoBusqueda) {
                                    case 1:
                                        // Busqueda por Datos Generales de la poliza:
                                        if(!formBusqueda.findField('params.nmpoliex').isValid()){
                                            showMessage('', _MSG_NMPOLIEX_INVALIDO, Ext.Msg.OK, Ext.Msg.INFO);
                                            return;
                                        }
                                        cargaStoreSuplementos(formBusqueda.getValues());
                                    break;
                                        
                                    case 2:
                                        // Busqueda de polizas por RFC:
                                        if(!formBusqueda.findField('params.rfc').isValid()){
                                            showMessage('', _MSG_RFC_INVALIDO, Ext.Msg.OK, Ext.Msg.INFO);
                                            return;
                                        } 
                                        cargaPolizasAsegurado(formBusqueda, btn);
                                    break;
                                    
                                    case 3:
                                    	// Busqueda de polizas por CDPERSON:
                                        if(!formBusqueda.findField('params.cdperson').isValid()){
                                            showMessage('', _MSG_CDPERSON_INVALIDO, Ext.Msg.OK, Ext.Msg.INFO);
                                            return;
                                        }
                                        cargaPolizasAsegurado(formBusqueda, btn);
                                        
                                    break;
                                    
                                    case 4:
                                    	// Busqueda de polizas por nombre:
                                        if(!formBusqueda.findField('params.nombre').isValid()){
                                            showMessage('', _MSG_NOMBRE_INVALIDO, Ext.Msg.OK, Ext.Msg.INFO);
                                            return;
                                        }
                                        cargaPolizasAsegurado(formBusqueda, btn);
                                        
                                    break;
                                }
                            }
                        }
                    ]
                },{
        	    	layout: 'column',
        	    	margin: '5',
        	    	border: false,
        	    	name: 'mensajeAgente',
        	    	html:''
        	     }
            ]
        },
        {
            title:'HISTORICO DE MOVIMIENTOS',
            width:990,
            height:150,
            colspan:2,
            autoScroll:true,
            items : [
                gridSuplementos
            ]
        },
        {
            //title:
            width: 130,
            height: 400,
            items: [
                listViewOpcionesConsulta
            ]
        },
        {
            //title:
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
    function cargaStoreSuplementos(params){
        
        debug('Params busqueda de suplemento: ',params);
        
        /*gridSuplementos.setLoading(true);
        storeSuplementos.load({
            params: params,
            callback: function(records, operation, success) {
                
                gridSuplementos.setLoading(false);
                //gridSuplementos.getView().el.focus();
                
                //Limpiar seleccion de la lista de opciones de consulta
                limpiaSeleccionTiposConsulta();
                
                //Mostrar un mensaje para el agente  
                cambiaTextoMensajeAgente('Esto es un mensaje de prueba para la aplicacion donde no se puede meter mucho mas texto del que puede ser.');
                
                if (!success) {
                    showMessage(_MSG_ERROR, _MSG_BUSQUEDA_SIN_DATOS, Ext.Msg.OK, Ext.Msg.ERROR);
                    return;
                }
                if(records.length == 0){
                    showMessage(_MSG_SIN_DATOS, _MSG_BUSQUEDA_SIN_DATOS, Ext.Msg.OK, Ext.Msg.INFO);
                    return;
                }
            }
        });*/
        
        cargaStorePaginadoLocal(storeSuplementos, _URL_CONSULTA_DATOS_SUPLEMENTO, 'datosSuplemento', params, function (options, success, response){
    		if(success){
                var jsonResponse = Ext.decode(response.responseText);
                
                if(!jsonResponse.success) {
                	showMessage(_MSG_ERROR, _MSG_BUSQUEDA_SIN_DATOS, Ext.Msg.OK, Ext.Msg.ERROR);
                    return;
                }
                
                gridSuplementos.setLoading(false);
                //gridSuplementos.getView().el.focus();
                
                //Limpiar seleccion de la lista de opciones de consulta
                limpiaSeleccionTiposConsulta();
                
                //Mostrar un mensaje para el agente  
                cambiaTextoMensajeAgente(jsonResponse.mensajeRes);
                
            }else{
                showMessage('Error', 'Error al obtener los datos.', Ext.Msg.OK, Ext.Msg.ERROR);
            }
    	}, gridSuplementos);
        
    }

    // FUNCION PARA OBTENER RECIBOS DEL AGENTE
    function obtieneMontosRecibo(records) {
        var sum=0;
        for(var i=0;i<records.length;i++) {
            sum+=parseFloat(records[i].get("ptimport"));
        }
        return Ext.util.Format.usMoney(sum);
    }
    
    function limpiaSeccionInformacionPrincipal() {
        panelDatosPoliza.getForm().reset();
        //gridDatosTarificacion.getStore().removeAll();
        gridCopagosPoliza.getStore().removeAll();
        gridDatosAsegurado.getStore().removeAll();
        //Limpiamos seccion de Datos de tatrisit:
        tabDatosGeneralesPoliza.down('panel[name=pnlDatosTatrisit]').setTitle('');
        tabDatosGeneralesPoliza.down('panel[name=pnlDatosTatrisit]').update('');
        tabDatosGeneralesPoliza.setActiveTab(0);
        tabPanelAgentes.setActiveTab(0);
        tabDatosGeneralesPoliza.hide();
        tabPanelAgentes.hide();
    }
    
    function limpiaSeleccionTiposConsulta() {
        listViewOpcionesConsulta.getSelectionModel().deselectAll();
        listViewOpcionesConsulta.collapse('top', false);
    }

    function cambiaTextoMensajeAgente(texto) {
    	if(!Ext.isEmpty(texto)){
    		panelBusqueda.down('[name=mensajeAgente]').update('<span style="color:#E96707;font-size:14px;font-weight:bold;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Aviso: '+texto+'</span>');
    	}else {
    		panelBusqueda.down('[name=mensajeAgente]').update('<span></span>');
    	}
    }
    
    function cargaPolizasAsegurado(formBusqueda, btn) {
    	gridPolizasAsegurado.down('pagingtoolbar').moveFirst();
    	var callbackGetPolizasAsegurado = function(options, success, response) {
            if(success){
                var jsonResponse = Ext.decode(response.responseText);
                if(jsonResponse.polizasAsegurado && jsonResponse.polizasAsegurado.length == 0) {
                    showMessage(_MSG_SIN_DATOS, _MSG_BUSQUEDA_SIN_DATOS, Ext.Msg.OK, Ext.Msg.INFO);
                    return;
                }
                windowPolizas.animateTarget= btn;
                windowPolizas.show();
            }else{
                showMessage('Error', 'Error al obtener las p&oacute;lizas, intente m\u00E1s tarde.', 
                    Ext.Msg.OK, Ext.Msg.ERROR);
            }
        }
        //console.log('Params busqueda de polizas por RFC=');console.log(formBusqueda.getValues());
        cargaStorePaginadoLocal(storePolizaAsegurado, 
            _URL_CONSULTA_POLIZAS_ASEGURADO, 
            'polizasAsegurado', 
            formBusqueda.getValues(), 
            callbackGetPolizasAsegurado);
    }
    
    
});