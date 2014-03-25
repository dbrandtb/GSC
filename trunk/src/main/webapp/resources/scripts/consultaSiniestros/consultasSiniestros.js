Ext.require([ 'Ext.form.*', 'Ext.data.*', 'Ext.chart.*', 'Ext.grid.Panel','Ext.layout.container.Column', 'Ext.selection.CheckboxModel' ]);
var _PAGO_DIRECTO = '1';
var _PAGO_REEMBOLSO = '2';
Ext.onReady(function() {

    Ext.selection.CheckboxModel.override( {
        mode: 'SINGLE',
        allowDeselect: true
    });
    
    // Conversion para el tipo de moneda
    Ext.util.Format.thousandSeparator = ',';
    Ext.util.Format.decimalSeparator = '.';
    
    var storePagoDirecto = Ext.create('Ext.data.JsonStore', {
    	model:'Generic',
        proxy: {
            type: 'ajax',
            url: _URL_BUSQUEDA_DIRECTO,
            reader: {
                type: 'json',
                root: 'pagoDirecto'
            }
        }
    });
    storePagoDirecto.load();
    
    var storePagoReembolso = Ext.create('Ext.data.JsonStore', {
    	model:'Generic',
        proxy: {
            type: 'ajax',
            url: _URL_BUSQUEDA_REEMBOLSO,
            reader: {
                type: 'json',
                root: 'pagoReembolso'
            }
        }
    });
    storePagoReembolso.load();
    
    storeAsegurados = Ext.create('Ext.data.Store', {
        model:'Generic',
        autoLoad:false,
        proxy: {
            type: 'ajax',
            url : _URL_LISTADO_ASEGURADO,
            reader: {
                type: 'json',
                root: 'listaAsegurado'
            }
        }
    });
    
    
    
    Ext.define('modelListaSiniestroAseguradoReem',{
        extend: 'Ext.data.Model',
        fields: [	{type:'string',    name:'feapertu'},			{type:'string',    name:'feocurre'},              	{type:'string',    name:'nmautser'},
                 	{type:'string',    name:'nmsinies'},			{type:'string',    name:'ntramite'}]
    });
    
    storeListadoAseguradoReembolso = new Ext.data.Store(
    {
    	pageSize : 5
        ,model      : 'modelListaSiniestroAseguradoReem'
        ,autoLoad  : false
        ,proxy     :
        {
            enablePaging : true,
            reader       : 'json',
            type         : 'memory',
            data         : []
        }
    });
    
    /*medicoConAutorizado = Ext.create('Ext.form.field.ComboBox',
    {
		fieldLabel : 'M&eacute;dico',	allowBlank: false,				displayField : 'nombre',			id:'idmedicoConAutorizado',
		labelWidth: 100,				width:450,						valueField   : 'cdpresta',			forceSelection : true,
		matchFieldWidth: false,			queryMode :'remote',			queryParam: 'params.cdpresta',		store : storeMedico,//,		editable:false,
		minChars  : 2,					triggerAction: 'all',			name:'idmedicoConAutorizado',		hideTrigger:true
	});*/
    
    comboTipoPagoReembolso = Ext.create('Ext.form.field.ComboBox',{
		id:'tipoPagoReembolso',			fieldLabel: 'Filtrar',			store: storePagoReembolso,				queryMode:'local',
		displayField: 'value',			valueField: 'key',				allowBlank:false,						blankText:'Es un dato requerido',
		editable:false,					emptyText:'Seleccione tipo busqueda ...',
		listeners : {
        	//'select' : function(combo, record) {
			change:function(field,value){
        		//console.log(this.getValue());
        		if(this.getValue() =="2")
    			{
        			//Ocutamos los valores de los campos y el boton
        			Ext.getCmp('btnBuscar').hide();
        			this.up('form').getForm().findField('params.pagoReembolso').hide();
        			modificacionClausula.show();
    			}else{
    				this.up('form').getForm().findField('params.pagoReembolso').show();
    				Ext.getCmp('btnBuscar').show();
    			}
	        }
        }
	});
    
    gridAseguradoReembolso  = Ext.create('Ext.grid.Panel',
	{
		id             : 'gridAseguradoReembolso'
		,store         :  storeListadoAseguradoReembolso
		,collapsible   : true
		,titleCollapse : true
		,style         : 'margin:5px'
		,selType: 'checkboxmodel'
		,width   : 600
		,height: 200
		,columns       :
		[
			 {
				 header     : 'N&uacute;mero <br/> Tr&aacute;mite'
				 ,dataIndex : 'ntramite'
				 ,width	 	: 100
			 },
			 {
				 header     : 'N&uacute;mero <br/> Autorizaci&oacute;n Servicio'
				 ,dataIndex : 'nmautser'
				 ,width     : 200
			 }
			 ,
			 {
				 header     : 'N&uacute;mero <br/> Siniestro'
				 ,dataIndex : 'nmsinies'
				 ,width     : 100
			 },
			 {
				 header     : 'Fecha Ocurrencia'
				 ,dataIndex : 'feocurre'
				 ,width	    : 100
			 }
			 ,
			 {
				 header     : 'Fecha Apertura'
				 ,dataIndex : 'feapertu'
				 ,width	    : 100
			 }
		 ],
		 bbar     :
		 {
			 displayInfo : true,
			 store       : storeListadoAseguradoReembolso,
			 xtype       : 'pagingtoolbar'
		 },
		//aqui va el listener
		 listeners: {
		        itemclick: function(dv, record, item, index, e) {
		        	if(Ext.getCmp('gridAseguradoReembolso').getSelectionModel().hasSelection()){
							var rowSelected = Ext.getCmp('gridAseguradoReembolso').getSelectionModel().getSelection()[0];
							//console.log(rowSelected);
							var ntramite= rowSelected.get('ntramite');
							_TIPOPAGO = _PAGO_REEMBOLSO;
							validaTipoPagoReembolso(_TIPOPAGO,1,ntramite);
							//console.log(ntramite);
						}else {
							Ext.Msg.show({
								title: 'Aviso',
								msg: 'Debe de seleccionar un registro para realizar la edici&oacute;n',
								buttons: Ext.Msg.OK,
								icon: Ext.Msg.ERROR
							});
							
						}
					modificacionClausula.hide();
		           	
		        }			     	
		    }
	});
    
    
	panelbusquedas = Ext.create('Ext.panel.Panel',
	{
		border  : 0,		id     : 'panelbusqueda',				width	: 600,		style  : 'margin:5px'
		,items :
			[
			 Ext.create('Ext.form.field.ComboBox',
			 {
				 fieldLabel 	: 'Nombre/C&oacute;digo asegurado',			allowBlank		: false,		displayField 	: 'value'
				 ,id			: 'idCodigoAsegurado',						labelWidth		: 170,			width		 	: 500
				 ,valueField   	: 'key',									forceSelection 	: true,		matchFieldWidth	: false
				 ,minChars  	: 2,										queryMode 		:'remote'		,queryParam: 'params.cdperson'
				 ,store : storeAsegurados,									hideTrigger:true				,triggerAction: 'all'
			 })
			 ]
		,buttonAlign: 'center'
		,buttons : [{
			text: "Buscar"
			,icon:_CONTEXT+'/resources/fam3icons/icons/magnifier.png'
			,handler: function(){
				if (panelClausula.form.isValid()) {
					storeListadoAseguradoReembolso.removeAll();
					var params = {
							'params.cdperson' : Ext.getCmp('idCodigoAsegurado').getValue()
					};
					
					cargaStorePaginadoLocal(storeListadoAseguradoReembolso, _URL_CONSULTA_LIST_ASEG_REEMBOLSO, 'datosSiniestroAsegurado', params, function(options, success, response){
						if(success){
							var jsonResponse = Ext.decode(response.responseText);
							if(jsonResponse.datosSiniestroAsegurado == null) {
								Ext.Msg.show({
									title: 'Aviso',
									msg: 'No se encontraron datos.',
									buttons: Ext.Msg.OK,
									icon: Ext.Msg.WARNING
								});
								return;
							}
							
						}else{
							Ext.Msg.show({
								title: 'Aviso',
								msg: 'Error al obtener los datos.',
								buttons: Ext.Msg.OK,
								icon: Ext.Msg.ERROR
							});
						}
					});
				} else {
					Ext.Msg.show({
						title: 'Aviso',
						msg: 'Complete la informaci&oacute;n requerida',
						buttons: Ext.Msg.OK,
						icon: Ext.Msg.WARNING
					});
				}
			}	
		}] 
	});
	
	
	panelClausula= Ext.create('Ext.form.Panel', {
		id: 'panelClausula',
		border:0,
		bodyPadding: 5,
		defaults 	:
		{	
			style : 'margin:5px;'
		}
		,
		items: [
		        	panelbusquedas 	// <-- contiene el formulario para la busqueda de acuerdo al codigo del asegurado
				    ,gridAseguradoReembolso 			// <-- Contiene la información de los asegurados
				]
		});
	
	
	modificacionClausula = Ext.create('Ext.window.Window',
	{
	    title        : 'Modo de Autorizaci&oacute;n'
	    ,modal       : true
	    ,buttonAlign : 'center'
	    ,closable : true
	    ,width		 : 650
	    ,minHeight 	 : 100 
	    ,maxheight      : 400
	    ,items       :
	        [
	            panelClausula
	        ]
	});
    
    var gridSuplementos = Ext.create('Ext.grid.Panel', {
        id : 'suplemento-form',
        //store : storeSuplementos,
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
        }]
    });
    gridSuplementos.store.sort([
        { 
        	property    : 'nsuplogi',
        	direction   : 'DESC'
        }
    ]);
    
    var tabDatosGeneralesPoliza =  Ext.create('Ext.tab.Panel', {
	    border : false,
	    items: [{
	            title: 'Informaci&oacute;n General'
    	    }, {
    	        title: 'Revisi&oacute;n Administrativa'
    	    }, {
    	        title: 'C&aacute;lculos'
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
            title:'BUSQUEDA DE SINIESTROS',
            colspan:2,
            width:990,
            height:140,
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
                                {boxLabel: 'Pago Directo', name: 'tipoBusqueda', inputValue: 1, checked: true, width: 200},
                                {boxLabel: 'Pago Reembolso', name: 'tipoBusqueda', inputValue: 2,width: 200}
                                
                            ],
                            listeners : {
                                change : function(radiogroup, newValue, oldValue, eOpts) {
                                	Ext.getCmp('subpanelBusquedas').query('panel').forEach(function(c){c.hide();});
                                	this.up('form').getForm().findField('params.pagoReembolso').setValue('');
                                	this.up('form').getForm().findField('params.pagoDirecto').setValue('');
                                	
                                    switch (newValue.tipoBusqueda) {
                                        case 1:
                                            Ext.getCmp('subpanelBusquedaGral').show();
                                            break;
                                        case 2:
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
                        	id: 'subpanelBusquedas',
                            layout : 'vbox',
                            align:'stretch',
                            flex: 70,
                            //border: false,
                            border  : 0,
                            style  : 'margin:5px',
                        	items: [
                                {
                                    id: 'subpanelBusquedaGral',
                                    layout : 'hbox',
                                    align:'stretch',
                                    border: false,
                                    defaults: {
                                        enforceMaxLength: true,
                                        msgTarget: 'side',
                                        labelAlign: 'right'
                                        	
                                    },
                                    items : [
										{
											xtype: 'combo',
											id:'tipoPagoDirecto',
											fieldLabel: 'Filtrar',
											store: storePagoDirecto,
											queryMode:'local',
											displayField: 'value',
											valueField: 'key',
											allowBlank:false,
											blankText:'Es un dato requerido',
											emptyText:'Seleccione tipo busqueda ...'
										},
                                        {
                                            // Numero de poliza externo
                                            xtype : 'textfield',
                                            name : 'params.pagoDirecto',
                                            bodyStyle:'padding:5px;',
                                            fieldLabel : 'Busqueda',
                                            //labelWidth : 120,
                                            //width: 300,
                                            //maxLength : 30,
                                            allowBlank: false
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
                                        comboTipoPagoReembolso,
										{
                                            xtype: 'textfield',
                                            //id: 'IdpagoReembolso',
                                            name : 'params.pagoReembolso',
                                            fieldLabel : 'Busqueda',
                                            //maxLength : 13,
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
                            id: 'btnBuscar',
                            handler: function(btn, e) {
                                var formBusqueda = this.up('form').getForm();
                                //Obtenemos el valor elegido en 'groupTipoBusqueda' para elegir el tipo de busqueda a realizar.
                                switch (formBusqueda.findField('groupTipoBusqueda').getValue().tipoBusqueda) {
                                    case 1:
                                        // Busqueda por Datos Generales de la poliza:
                                        if(!formBusqueda.findField('params.pagoDirecto').isValid()){
                                            showMessage('', _MSG_NMPOLIEX_INVALIDO, Ext.Msg.OK, Ext.Msg.INFO);
                                            return;
                                        }
                                        //cargaStoreSuplementos(formBusqueda.getValues());
                                    break;
                                        
                                    case 2:
                                        // Busqueda de polizas por RFC:
                                        if(!formBusqueda.findField('params.pagoReembolso').isValid()){
                                            showMessage('', _MSG_RFC_INVALIDO, Ext.Msg.OK, Ext.Msg.INFO);
                                            return;
                                        }
                                        //Le pasamos los valores
                                        _TIPOPAGO = _PAGO_REEMBOLSO;
                                        validaTipoPagoReembolso(_TIPOPAGO,Ext.getCmp('tipoPagoReembolso').getValue(),formBusqueda.findField('params.pagoReembolso').rawValue);
                                        
                                    break;
                                }
                            }
                        }
                    ]
                }
            ]
        },
        {
            title:'ASEGURADOS AFECTADOS',
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
            width:990,
            height:400,
            colspan:2,
            autoScroll:true,
            items : [
                tabDatosGeneralesPoliza
            ]
        }]
    });

    function validaTipoPagoReembolso(tipoPago,accion,valorBusqueda){
    	if(accion == "1"){
    		//Por numero de autorizacion
    		Ext.Ajax.request(
			{
			    url     : _URL_CARGA_INFORMACION
			    ,params:{
					'params.ntramite': valorBusqueda,
					'params.tipoPago': tipoPago
                }
			    ,success : function (response)
			    {
			    	//console.log(Ext.decode(response.responseText));
			    	var json = Ext.decode(response.responseText);
		    		_CDUNIECO = json.params.cdunieco;
		    		_CDRAMO = json.params.cdramo;
	                _ESTADO = json.params.estado;
	                _NMPOLIZA = json.params.nmpoliza;
	                _NMSITUAC = json.params.nmsituac;
	                _NMSUPLEM = json.params.nmsuplem;
	                _STATUS = json.params.status;
	                _AAAPERTU = json.params.aaapertu;
	                _NMSINIES = json.params.nmsinies;
	                _NTRAMITE = json.params.ntramite;
	                cargaInformacionTab();

			    },
			    failure : function ()
			    {
			        //me.up().up().setLoading(false);
			        Ext.Msg.show({
			            title:'Error',
			            msg: 'Error de comunicaci&oacute;n',
			            buttons: Ext.Msg.OK,
			            icon: Ext.Msg.ERROR
			        });
			    }
			});
    	}
    	return true;
    }
    
    function cargaInformacionTab(){
    	tabDatosGeneralesPoliza.remove(tabDatosGeneralesPoliza.getComponent(0));
	                tabDatosGeneralesPoliza.insert(0,{
	                	title: 'Informaci&oacute;n General',
                        loader: {
            	        	url: _URL_LOADER_INFO_GRAL_RECLAMACION,
            	        	scripts: true,
            	        	autoLoad: true,
            	        	ajaxOptions: {
            	        		method: 'POST'
            	        	}
            	        },
                        listeners : {
                            activate : function(tab) {
                                tab.loader.load();
                                
                            }
                        }
                    });
	                
	       tabDatosGeneralesPoliza.remove(tabDatosGeneralesPoliza.getComponent(1));
	              tabDatosGeneralesPoliza.insert(1,{
	                	title: 'Revisi&oacute;n Administrativa',
                        //layout: 'fit',
                        loader: {
            	        	url: _URL_LOADER_REV_ADMIN,
            	        	scripts: true,
            	        	autoLoad: true,
            	        	ajaxOptions: {
            	        		method: 'POST'
            	        	}
            	        },
                        listeners : {
                            activate : function(tab) {
                                tab.loader.load();
                                
                            }
                        }
                    });
	                
	      tabDatosGeneralesPoliza.remove(tabDatosGeneralesPoliza.getComponent(2));
	           tabDatosGeneralesPoliza.insert(2,{
	                	title: 'C&aacute;lculos',
	                    loader: {
            	        	url: _URL_LOADER_CALCULOS,
            	        	scripts: true,
            	        	autoLoad: true,
            	        	params:{
    	    			    	'params.ntramite': _NTRAMITE
    	                    },
    	                    ajaxOptions: {
            	        		method: 'POST'
            	        	}
            	        },
                        listeners : {
                            activate : function(tab) {
                                tab.loader.load();
                                
                            }
                        }
                    });
	                tabDatosGeneralesPoliza.setActiveTab(0);
    	return true;
    }
});