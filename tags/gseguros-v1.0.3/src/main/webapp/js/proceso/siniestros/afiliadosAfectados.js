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
        height: 200,
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
    
    
    _11_itemsForm.push(
    {
        colspan:2,
        items    :
        [
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
    //gridIncisos
});