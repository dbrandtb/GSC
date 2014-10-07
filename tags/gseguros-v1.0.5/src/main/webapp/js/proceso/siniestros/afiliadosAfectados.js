Ext.require([ 'Ext.form.*', 'Ext.data.*', 'Ext.chart.*', 'Ext.grid.Panel','Ext.layout.container.Column', 'Ext.selection.CheckboxModel' ]);
var datosgrid;
var storeIncisos;

Ext.define('modelClau',
{
    extend:'Ext.data.Model',
    fields:['noFactura','fechaFactura','tipoServicio','proveedor','importe']
});

storeIncisos=new Ext.data.Store(
{
    autoDestroy: true,
    model: 'modelClau'
});

Ext.define('modelListadoPoliza',{
    extend: 'Ext.data.Model',
    fields: [	{type:'string',    name:'cdramo'},				{type:'string',    name:'cdunieco'},				{type:'string',    name:'estado'},
				{type:'string',    name:'nmpoliza'},			{type:'string',    name:'nmsituac'},				{type:'string',    name:'mtoBase'},
				{type:'string',    name:'feinicio'},			{type:'string',    name:'fefinal'},					{type:'string',    name:'dssucursal'},
				{type:'string',    name:'dsramo'},				{type:'string',    name:'estatus'},					{type:'string',    name:'dsestatus'},
				{type:'string',    name:'nmsolici'},			{type:'string',    name:'nmsuplem'},				{type:'string',    name:'cdtipsit'},
				{type:'string',    name:'dsestatus'},			{type:'string',    name:'vigenciaPoliza'},			{type:'string',    name:'faltaAsegurado'},
				{type:'string',    name:'fcancelacionAfiliado'},{type:'string',    name:'desEstatusCliente'},		{type:'string',    name:'numPoliza'}]
});

var storeListadoPoliza = new Ext.data.Store(
{
	pageSize : 5
    ,model      : 'modelListadoPoliza'
    ,autoLoad  : false
    ,proxy     :
    {
        enablePaging : true,
        reader       : 'json',
        type         : 'memory',
        data         : []
    }
});
    
var storeAsegurados = Ext.create('Ext.data.Store', {
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

Ext.onReady(function() {

    Ext.selection.CheckboxModel.override( {
        mode: 'SINGLE',
        allowDeselect: true
    });
    
    ///// NUEVOS
    
    cobertura= Ext.create('Ext.form.ComboBox',
    	    {
        id:'cobertura',
        name:'params.cobertura',
        fieldLabel: 'Cobertura',
        //store: storeCirculoHospitalario,
        queryMode:'local',
        displayField: 'value',
        valueField: 'key',
        allowBlank:false,
        editable:false,
        //labelWidth : 250,
        emptyText:'Seleccione...'
    });


    proveedor= Ext.create('Ext.form.ComboBox',
    	    {
        id:'proveedor',
        name:'params.proveedor',
        fieldLabel: 'Proveedor',
        //store: storeCirculoHospitalario,
        queryMode:'local',
        displayField: 'value',
        valueField: 'key',
        allowBlank:false,
        editable:false,
        //labelWidth : 250,
        emptyText:'Seleccione...'
    });
    
    subcobertura= Ext.create('Ext.form.ComboBox',
    	    {
        id:'subcobertura',
        name:'params.subcobertura',
        fieldLabel: 'Subcobertura',
        //store: storeCirculoHospitalario,
        queryMode:'local',
        displayField: 'value',
        valueField: 'key',
        allowBlank:false,
        editable:false,
        //labelWidth : 250,
        emptyText:'Seleccione...'
    });

    Ext.define('DatosTarificacionModel',{
        extend: 'Ext.data.Model',
        fields: [
					{type:'string',    name:'IdReclamacion'      },
					{type:'string',    name:'NoAutorizacion'      },
					{type:'string',    name:'codAfiliado'      },
					{type:'string',    name:'nombre'      },
					{type:'date',    name:'fechaOcurrencia',dateFormat : 'd/m/Y'      },
					{type:'string',    name:'noPoliza'      },
					{type:'string',    name:'VoBoAuto'      },
					{type:'string',    name:'icd'      },
					{type:'string',    name:'icdSecundario'      },
					{type:'string',    name:'cpthcpc'      },
					{type:'string',    name:'cantidad'      },
					{type:'string',    name:'importeArancel'      },
					{type:'string',    name:'subtoArancel'      },
					{type:'string',    name:'porcDescuento'      },
					'impoDescuento',
					{type:'string',    name:'copago'      },
					{type:'string',    name:'impFacturado'      },
					{type:'string',    name:'autoFacturado'      },
					{type:'string',    name:'noReclamo'      },
					{type:'string',    name:'capDetalle'      },
					{type:'string',    name:'observaciones'      }
					,'AUTRECLA'
					,'COMMENAR'
					,'AUTMEDIC'
					,'COMMENME'
					,{name:'AAAPERTU',type:'int'}
					,'STATUS'
					,'CDUNIECO'
					,'CDRAMO'
					,'NMSUPLEM'
					,'NMSITUAC'
					,'ESTADO'
        ]
    });
    

    var storeDatosTarificacion= Ext.create('Ext.data.Store', {
        storeId: 'storeDatosTarificacion',
        model: 'DatosTarificacionModel',
        data : recordsStore/* [{
        	"IdReclamacion" : "1",
        	"NoAutorizacion" : "12340",
        	"codAfiliado" : "13564",
        	"nombre" : "Pedro Perez T.",
        	"fechaOcurrencia" : "12/12/1986",
        	"noPoliza" : "123",
        	"VoBoAuto" : "Yes",
        	"icd" : "12345",
        	"icdSecundario" : "45643",
        	"cpthcpc" : "34532",
        	"cantidad" : "23",
        	"importeArancel" : "12340.00",
        	"subtoArancel" : "456.00",
        	"porcDescuento" : "10",
        	"copago" : "19990.00",
        	"impFacturado" : "454",
        	"autoFacturado" : "Yes",
        	"noReclamo" : "3464",
        	"capDetalle" : "345",
        	"observaciones" :"Factura Pagada"
        }]*/
    });

    
    
    var gridDatosTarificacion = Ext.create('Ext.grid.Panel', {
        //width   : 780,
        height: 400,
        title   : 'Reclamaciones',
        store   : storeDatosTarificacion,
        autoScroll:true,
        id      : 'gridDatosTarificacion',
        features:[{
            ftype:'summary'
        }],
        columns: _11_columnas
    });
    gridDatosTarificacion.store.sort([
        { 
            property    : 'dsgarant',
            direction   : 'ASC'
        }
    ]);
    
    /* PANEL PARA LA BUSQUEDA DE LA INFORMACIÓN DEL ASEGURADO PARA LA BUSQUEDA DE LAS POLIZAS */
    gridPolizasAltaTramite= Ext.create('Ext.grid.Panel',
	{
	    id            : 'polizaGridAltaTramite',
	    store         : storeListadoPoliza,
	    //collapsible   : true,
	    //titleCollapse : true,
	    selType	      : 'checkboxmodel',
	    width         : 700,
	    height		  : 200,
	    columns       :
	    [
	        
	        
	        {
	             header     : 'N&uacute;mero de P&oacute;liza',		dataIndex : 'numPoliza',		width	 	: 200
	        },
	        {
	        	 header     : 'Estatus p&oacute;liza ',							dataIndex : 'dsestatus',	width	 	: 100
	        },
	        {
	             header     : 'Vigencia p&oacute;liza <br/> Fecha inicio \t\t  |  \t\t Fecha fin  ',						dataIndex : 'vigenciaPoliza',		width	    : 200
	        },
	        {
	             header     : 'Fecha alta <br/> asegurado',		dataIndex : 'faltaAsegurado',		width	    : 100
	        },
	        {
	             header     : 'Fecha cancelaci&oacute;n <br/> asegurado',						dataIndex : 'fcancelacionAfiliado',		width	    : 150
	        },
	        {
	             header     : 'Estatus<br/> asegurado',						dataIndex : 'desEstatusCliente',		width	    : 100
	        },
	        {
	             header     : 'Producto',							dataIndex : 'dsramo',		width       : 150
	        },
	        {
	             header     : 'Sucursal',							dataIndex : 'dssucursal',	width       : 150
	        },
	        {
	             header     : 'Estado',								dataIndex : 'estado',		width	    : 100
	        },
	        {
	             header     : 'N&uacute;mero de Situaci&oacute;n',	dataIndex : 'nmsituac',		width	    : 150
	         }
		],
	    bbar :
	    {
	        displayInfo : true,
	        store       : storeListadoPoliza,
	        xtype       : 'pagingtoolbar'
	    },
	    listeners: {
                itemclick: function(dv, record, item, index, e){
                	//1.- Validamos que el asegurado este vigente
                	if(record.get('desEstatusCliente')=="Vigente")
            		{
                		var valorFechaOcurrencia;
                		var valorFechaOcu = panelListadoAsegurado.query('datefield[name=dtfechaOcurrencias]')[0].rawValue;
                		valorFechaOcurrencia = new Date(valorFechaOcu.substring(6,10)+"/"+valorFechaOcu.substring(3,5)+"/"+valorFechaOcu.substring(0,2));
                		
                		var valorFechaInicial = new Date(record.get('feinicio').substring(6,10)+"/"+record.get('feinicio').substring(3,5)+"/"+record.get('feinicio').substring(0,2));
                		var valorFechaFinal =   new Date(record.get('fefinal').substring(6,10)+"/"+record.get('fefinal').substring(3,5)+"/"+record.get('fefinal').substring(0,2));
                		var valorFechaAltaAsegurado = new Date(record.get('faltaAsegurado').substring(6,10)+"/"+record.get('faltaAsegurado').substring(3,5)+"/"+record.get('faltaAsegurado').substring(0,2));
                		
                		if( (valorFechaOcurrencia <= valorFechaFinal) && (valorFechaOcurrencia >= valorFechaInicial)){
                    		if( valorFechaOcurrencia >= valorFechaAltaAsegurado )
                			{
                    				panelListadoAsegurado.down('[name="cdUniecoAsegurado"]').setValue(record.get('cdunieco'));
                					panelListadoAsegurado.down('[name="cdRamoAsegurado"]').setValue(record.get('cdramo'));
                					panelListadoAsegurado.down('[name="estadoAsegurado"]').setValue(record.get('estado'));
                					panelListadoAsegurado.down('[name="nmPolizaAsegurado"]').setValue(record.get('nmpoliza'));
                					panelListadoAsegurado.down('[name="nmSoliciAsegurado"]').setValue(record.get('nmsolici'));
                					panelListadoAsegurado.down('[name="nmSuplemAsegurado"]').setValue(record.get('nmsuplem'));
                					panelListadoAsegurado.down('[name="nmSituacAsegurado"]').setValue(record.get('nmsituac'));
                					panelListadoAsegurado.down('[name="cdTipsitAsegurado"]').setValue(record.get('cdtipsit'));
                					modPolizasAltaTramite.hide();
                			}else{
                				// No se cumple la condición la fecha de ocurrencia es mayor a la fecha de alta de tramite
                				Ext.Msg.show({
        	                    	title:'Error',
        	                    	msg: 'La fecha de ocurrencia es mayor a la fecha de alta del asegurado',
        	                    	buttons: Ext.Msg.OK,
        	                    	icon: Ext.Msg.ERROR
        	                	});
                				modPolizasAltaTramite.hide();
                    			//limpiarRegistros();
                			}
                		}else{
                			// La fecha de ocurrencia no se encuentra en el rango de la poliza vigente
                			Ext.Msg.show({
    	                    	title:'Error',
    	                    	msg: 'La fecha de ocurrencia no se encuentra en el rango de la p&oacute;liza vigente',
    	                    	buttons: Ext.Msg.OK,
    	                    	icon: Ext.Msg.ERROR
    	                	});
                			modPolizasAltaTramite.hide();
                			//limpiarRegistros();
                		}
            		}else{
            			// El asegurado no se encuentra vigente
            			Ext.Msg.show({
	                    	title:'Error',
	                    	msg: 'El asegurado de la p&oacute;liza seleccionado no se encuentra vigente',
	                    	buttons: Ext.Msg.OK,
	                    	icon: Ext.Msg.ERROR
	                	});
            			modPolizasAltaTramite.hide();
            			//limpiarRegistros();
            		}
                }
            }

	});
    gridPolizasAltaTramite.store.sort([
        {
        	property    : 'nmpoliza',			direction   : 'DESC'
        }
    ]);
    
    
    var modPolizasAltaTramite = Ext.create('Ext.window.Window',
	{
	    title        : 'Listado de P&oacute;liza'
	    ,modal       : true
	    ,resizable   : false
	    ,buttonAlign : 'center'
	    ,closable    : false
	    ,width		 : 710
	    ,minHeight 	 : 100 
	    ,maxheight   : 400
	    ,items       :
	        [
	            gridPolizasAltaTramite
	        ]
	});
    
    
        /* PANEL PARA EL PAGO DIRECTO */
    var panelListadoAsegurado= Ext.create('Ext.form.Panel',{
        border  : 0
        ,startCollapsed : true
        ,bodyStyle:'padding:5px;'
        ,url: _URL_GUARDA_ASEGURADO
        ,items :
        [   			
            {
                xtype      : 'datefield',
                name       : 'dtfechaOcurrencias',
                fieldLabel : 'Fecha ocurrencia',
                //allowBlank: false,
                maxValue   :  new Date(),
                format		: 'd/m/Y'
            },
            {	
            	xtype      : 'textfield',
            	name       : 'cdUniecoAsegurado',
            	fieldLabel : 'Unieco',//,allowBlank:false
            	hidden:true
            },
            {	
            	xtype      : 'textfield',
            	name       : 'cdRamoAsegurado',
            	fieldLabel : 'Ramo',//,allowBlank:false
            	hidden:true
            },
            {	
            	xtype      : 'textfield',
            	name       : 'estadoAsegurado',
            	fieldLabel : 'Estado',//,allowBlank:false
            	hidden:true
            },
            {	
            	xtype      : 'textfield',
            	name       : 'nmPolizaAsegurado',
            	fieldLabel : 'Poliza Asegurado',//,allowBlank:false
            	hidden:true
            },
            {	
            	xtype      : 'textfield',
            	name       : 'nmSoliciAsegurado',
            	fieldLabel : 'No. Solicitud',//,allowBlank:false
            	hidden:true
            },
            {	
            	xtype      : 'textfield',
            	name       : 'nmSuplemAsegurado',
            	fieldLabel : 'No. Suplement',//,allowBlank:false
            	hidden:true
            },
            {	
            	xtype      : 'textfield',
            	name       : 'nmSituacAsegurado',
            	fieldLabel : 'No. Situac',//,allowBlank:false
            	hidden:true
            },
            {	
            	xtype      : 'textfield',
            	name       : 'cdTipsitAsegurado',
            	fieldLabel : 'Cdtipsit',//,allowBlank:false
            	hidden:true
            },
            {
            	xtype: 'combo',
            	name:'cmbAseguradoAfect',
            	fieldLabel : 'Asegurado',
        	    displayField : 'value',
        	    valueField   : 'key',
        	    //allowBlank: false,
        	    width:500,
        	    minChars  : 2,
        	    forceSelection : true,
        	    matchFieldWidth: false,
        	    queryMode :'remote',
        	    queryParam: 'params.cdperson',
        	    store : storeAsegurados,
        	    triggerAction: 'all',
        	    hideTrigger:true,
        	    listeners : {
        	    	
        	        'select' : function(combo, record) {
        	        	var params = {'params.cdperson' : this.getValue(),
        	        				  'params.cdramo' : _11_params.CDRAMO };
                        
                        cargaStorePaginadoLocal(storeListadoPoliza, _URL_CONSULTA_LISTADO_POLIZA, 'listaPoliza', params, function(options, success, response){
                            if(success){
                                var jsonResponse = Ext.decode(response.responseText);
                                if(jsonResponse.listaPoliza == null) {
                                    Ext.Msg.show({
                                        title: 'Aviso',
                                        msg: 'No existen p&oacute;lizas para el asegurado elegido.',
                                        buttons: Ext.Msg.OK,
                                        icon: Ext.Msg.WARNING
                                    });
                                    
                                    combo.clearValue();
                                    modPolizasAltaTramite.hide();
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
                        modPolizasAltaTramite.show();
                    }
        	    }
            }
        ]
    });

    /*PANTALLA EMERGENTE PARA EL PAGO DIRECTO */
   var ventanaAgregarAsegurado= Ext.create('Ext.window.Window', {
        title: 'Asegurados',
        //height: 200,
        closeAction: 'hide',
        modal: true, 
        resizable: false,
        items:[panelListadoAsegurado],
        buttonAlign : 'center',
        buttons:[
            {
                text: 'Aceptar',
                icon:_CONTEXT+'/resources/fam3icons/icons/accept.png',
                handler: function() {
                    if (panelListadoAsegurado.form.isValid()) {
                    	var datos=panelListadoAsegurado.form.getValues();
                    	
                    	if(datos.cmbAseguradoAfect==''|| datos.cmbAseguradoAfect== null && datos.dtfechaOcurrencias==''|| datos.dtfechaOcurrencias== null )
                    	{
                    		Ext.Msg.show({
	                            title: 'Aviso',
	                            msg: 'Complete la informaci&oacute;n requerida',
	                            buttons: Ext.Msg.OK,
	                            icon: Ext.Msg.WARNING
	                        });
                    	}else{
                        panelListadoAsegurado.form.submit({
           		        	waitMsg:'Procesando...',
           		        	//standardSubmit : true,
           		        	params: {
           		        		'params.nmtramite'  : _11_params.NTRAMITE,
                                'params.cdunieco'   : datos.cdUniecoAsegurado,
           		        		'params.cdramo'     : datos.cdRamoAsegurado,
           		        		'params.estado'     : datos.estadoAsegurado,
           		        		'params.nmpoliza'   : datos.nmPolizaAsegurado,
                                'params.nmsolici'   : datos.nmSoliciAsegurado,
           		        		'params.nmsuplem'   : datos.nmSuplemAsegurado,
           		        		'params.nmsituac'   : datos.nmSituacAsegurado,
                                'params.cdtipsit'   : datos.cdTipsitAsegurado,
                                'params.cdperson'   : datos.cmbAseguradoAfect,
                                'params.feocurre'   : datos.dtfechaOcurrencias
           		        	},
           		        	failure: function(form, action) {
           		        		centrarVentanaInterna(mensajeError("Error al guardar el Asegurado"));
           					},
           					success: function(form, action) {
           						var params =
        		                {
        		                    'params.ntramite' : _11_params.NTRAMITE,
        		                    'params.tipopago' : _11_params.OTVALOR02
        		                };
        						
        		                centrarVentanaInterna(mensajeCorrecto('Datos guardados',"Se ha guardado el Asegurado",function()
			            		{
			            		    Ext.create('Ext.form.Panel').submit(
			            		    {
			            		        url             : _selCobUrlAvanza
			            		        ,standardSubmit : true
			            		        ,params         : params
			            		    });
			            		}));
           						
           					}
           				});
                        //limpiarRegistros();
                        panelListadoAsegurado.getForm().reset();
                        ventanaAgregarAsegurado.close();
                    }
                    } else {
                        Ext.Msg.show({
                            title: 'Aviso',
                            msg: 'Complete la informaci&oacute;n requerida',
                            buttons: Ext.Msg.OK,
                            icon: Ext.Msg.WARNING
                        });
                    }
                }
            },
            {
                text: 'Cancelar',
                icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
                handler: function(){
                    panelListadoAsegurado.getForm().reset();
                    ventanaAgregarAsegurado.close();
                }
            }
        ]
    });
    
    
    
    
    
    
    _11_itemsForm.push(
    {
        colspan:2,
        border    : 0,
        items    :
        [
         Ext.create('Ext.Button', {
		    text: 'Agregar Asegurado',
		    icon    : _CONTEXT+'/resources/fam3icons/icons/application_edit.png',
		    renderTo: Ext.getBody(),
		    handler: function() {
		        //alert('You clicked the button!');
		    	ventanaAgregarAsegurado.show();
		    }
		}),
         
         gridDatosTarificacion
        ]
    });
    
    _11_form=Ext.create('Ext.form.Panel',
    	    {
    	        border    : 0
    	        ,title: 'Afiliados Afectados'
    	        ,renderTo : 'div_clau'
	        	,bodyPadding: 5
	            //,width: 1000
	            ,layout     :
		    	{
		    		type     : 'table'
		    		,columns : 2
		    	}
		        ,defaults   :
		        {
		        	style : 'margin:5px;'
		        }
		        ,listeners : { afterrender : heredarPanel }
		        ,
		        items    : _11_itemsForm
    	        /*[
    	            {
		    	    	xtype       : 'textfield'
                        ,fieldLabel : 'No. de Tr&aacute;mite'
                        ,name       : 'smap1.noTramite'
                        ,allowBlank : false
                        ,value      : _11_params.NTRAMITE
                        ,readOnly   : true
                    }
		    	    ,{
		    	    	xtype       : 'datefield'
		    	    	,format     : 'd/m/Y'
                        ,fieldLabel : 'fecha de Factura'
                        ,name       : 'parametros.pv_otvalor06'
                        ,allowBlank : false
                        ,value      : _11_params.OTVALOR06
		    	    }
		    	    ,{
		    	    	xtype       : 'textfield'
                        ,fieldLabel : 'Factura'
                        ,name       : 'smap1.factura'
                        ,allowBlank : false
                        ,value      : _11_params.OTVALOR08
                        ,name       : 'parametros.pv_otvalor08'
		    	    }
		    	    ,
		    	    cobertura
		    	    ,
		    	    proveedor
		    	    ,
		    	    subcobertura
		    	    ,{
		    	    	colspan:2,
		    	    	xtype       : 'textfield'
                        ,fieldLabel : 'Importe'
                        ,name       : 'smap1.importe'
                        ,allowBlank : false
		    	    }
		    	    ,
		    	    {
		    	        colspan:2,
		    	        items    :
	        	        [
	        	         gridDatosTarificacion
	        	        ]
		    	    }
	    	    ]*/
    	        ,
    	        buttonAlign:'center',
    	        buttons: 
    	        [      
    	            {
					    text     : 'Regresar'
					    ,icon    : _CONTEXT+'/resources/fam3icons/icons/cancel.png'
					    ,handler : _11_regresarMC
					}
					,{
    	            id:'botonCotizar',
    	            icon:_CONTEXT+'/resources/fam3icons/icons/disk.png',
    	            //text: hayTramiteCargado?'Precaptura':'Cotizar',
    	            text: 'Guardar',
            		handler: function()
            		{
            			
            			var form = this.up('form').getForm();
            			
            			var valido=true;
            			valido = form.isValid();
            			if(!valido)
            			{
            				datosIncompletos();
            				/*
            				var incisosRecords = storeIncisos.getRange();
            				console.log(incisosRecords.length);
            				
            				var incisosJson = [];
            				storeIncisos.each(function(record,index){
	                        	if(record.get('nombre')
	                        			&&record.get('nombre').length>0)
                        		{
	                        		nombres++;
                        		}
	                            incisosJson.push({
	                            	noFactura: record.get('noFactura'),
	                            	fechaFactura: record.get('fechaFactura'),
	                            	tipoServicio: record.get('tipoServicio'),
	                            	proveedor: record.get('proveedor'),
	                            	importe: record.get('importe')
	                            });
                            });
            				
            				console.log('---- VALOR DE IncisosJson ---- ');
            				console.log(incisosJson);
            				
            				var submitValues=form.getValues();
                        	submitValues['incisos']=incisosJson;
                        	console.log('---- VALOR DE submitValues ---- ');
            				console.log(submitValues);
            				
            				Ext.Msg.show({
    	                    	title:'Datos incompletos',
    	                    	msg: 'Favor de introducir todos los campos requeridos',
    	                    	buttons: Ext.Msg.OK,
    	                    	icon: Ext.Msg.WARNING
    	                	});
    	                	*/
        				}
            			
            			if(valido)
            			{
	            			var json =
	            			{
	            				params : form.getValues()
	            			};
	            			
	            			debug('datos a enviar:',json);
	            			
	            			_11_form.setLoading(true);
	            			
	            			Ext.Ajax.request(
	            			{
	            				url       : _11_urlGuardar
	            				,jsonData : json
	            				,success  : function(response)
	            				{
	            					_11_form.setLoading(false);
	            					json = Ext.decode(response.responseText);
	            					if(json.success==true)
	            					{
	            						var params =
	            		                {
	            		                    'params.ntramite' : _11_params.NTRAMITE,
	            		                    'params.tipopago' : _11_params.OTVALOR02
	            		                };
	            						
	            		                mensajeCorrecto('Datos guardados',json.mensaje,function()
        			            		{
        			            		    Ext.create('Ext.form.Panel').submit(
        			            		    {
        			            		        url             : _selCobUrlAvanza
        			            		        ,standardSubmit : true
        			            		        ,params         : params
        			            		    });
        			            		});
	            						
	            					}
	            					else
	            					{
	            						mensajeError(json.mensaje);
	            					}
	            				}
	            			    ,failure  : function(response)
	            			    {
	            			    	_11_form.setLoading(false);
	            			    	errorComunicacion();
	            			    }
	            			});
            			}
    	            }
    	        }/*,
    	        {
    	            text:'Limpiar',
    	            icon:_CONTEXT+'/resources/fam3icons/icons/arrow_refresh.png',
    	            id:'botonLimpiar',
    	            handler:function()
    	            {}
    	        }*/
    	    ]
    	    }); 
    	    _fieldByName('OTVALOR11').forceSelection=true;
    	    _fieldByName('OTVALOR11').setEditable(true);
    //gridIncisos
});