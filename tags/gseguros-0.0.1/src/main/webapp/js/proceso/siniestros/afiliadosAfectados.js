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
					{type:'string',    name:'fechaOcurrencia'      },
					{type:'string',    name:'noPoliza'      },
					{type:'string',    name:'VoBoAuto'      },
					{type:'string',    name:'icd'      },
					{type:'string',    name:'icdSecundario'      },
					{type:'string',    name:'cpthcpc'      },
					{type:'string',    name:'cantidad'      },
					{type:'string',    name:'importeArancel'      },
					{type:'string',    name:'subtoArancel'      },
					{type:'string',    name:'porcDescuento'      },
					{type:'string',    name:'copago'      },
					{type:'string',    name:'impFacturado'      },
					{type:'string',    name:'autoFacturado'      },
					{type:'string',    name:'noReclamo'      },
					{type:'string',    name:'capDetalle'      },
					{type:'string',    name:'observaciones'      }
        ]
    });
    

    var storeDatosTarificacion= Ext.create('Ext.data.Store', {
        storeId: 'storeDatosTarificacion',
        model: 'DatosTarificacionModel',
        data: [{
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
        }]
    });

    
    
    var gridDatosTarificacion = Ext.create('Ext.grid.Panel', {
        width   : 780,
        height: 200,
        //title   : 'DATOS TARIFICACI&Oacute;N',
        store   : storeDatosTarificacion,
        autoScroll:true,
        id      : 'gridDatosTarificacion',
        features:[{
            ftype:'summary'
        }],
        columns: [
            

            
            {	
            	text            :'ID <br/>Reclamaci&oacute;n',			width			: 110,
            	align			:'center',								dataIndex       :'IdReclamacion'                
            },
            {	
            	text            :'N&uacute;mero <br/>Autorizaci&oacute;n',  		width           : 110,
            	align			:'center',											dataIndex       :'NoAutorizacion'                
            }
            ,
            {	
            	text            :'C&oacute;digo <br/>Asegurado',  		width           : 110,
            	align			:'center',								dataIndex       :'codAfiliado'                
            },
            {	
            	text            :'Nombre <br/> Asegurado', 				width           : 200,
            	align			:'center',								dataIndex       :'nombre'                
            },
            {
                text            :'Fecha Ocurrencia',  					width           : 110,
                align			:'center',								dataIndex       :'fechaOcurrencia'                
            },
            {
                text            :'P&oacute;liza',  						width           : 110,
                align			:'center',								dataIndex       :'noPoliza'
            },
            {
                text            :'Vo.Bo. <br/> Autom&aacute;tico',  	width           : 110,
                align			:'center',								dataIndex       :'VoBoAuto'
            },
            {
                text            :'ICD',  								width           : 110,
                align			:'center',								dataIndex       :'icd'
                
            },
            {
                text            :'ICD <br/>Secundario', 					width           : 110, 
                align			:'center',									dataIndex       :'icdSecundario'
            },
            {
                text            : 'CPT/HCPC',						width           : 110,
                align			:'center',							dataIndex       :'cpthcpc'
            },
            {
                text            :'Cantidad',						width           : 110,  
                align			:'center',							dataIndex       :'cantidad'
            },
            {
                text            :'Importe <br/>Arancel',  			width           : 110, 			renderer        :Ext.util.Format.usMoney,
                align			:'center',							dataIndex       :'importeArancel'
            },
            {
                text            :'Subtotal <br/>Arancel',			width           : 110, 			renderer        :Ext.util.Format.usMoney, 
                align			:'center',							dataIndex       :'subtoArancel'
            },
            {
                text            :'% Descuento',  
                dataIndex       :'porcDescuento',
                align			:'center',
                width           :110                
            },
            {
                text            :'Copago',  						width           : 110, 			renderer        :Ext.util.Format.usMoney,
                align			:'center',
                dataIndex       :'copago'
            },
            {
                text            :'Importe <br/>Facturado', 				width           : 120, 			renderer        :Ext.util.Format.usMoney, 
                align			:'center',
                dataIndex       :'impFacturado'
            },
            {
                text            :'Autorizar <br/>Facturado', 			width           : 120, 
                align			:'center',
                dataIndex       :'autoFacturado'
            },
            {
                text            :'N&uacute;mero de <br/>Reclamo',  					width           : 110,
                align			:'center',
                dataIndex       :'noReclamo'
            },            
            {	 xtype      : 'actioncolumn'						,menuDisabled : true			,header       : 'Capturar Detalle'       ,width        : 150,
            	 align		: 'center',
            	items      :
                   [
                       {
                       	/*Revisión de Documentos*/
                       	icon     : _CONTEXT+'/resources/fam3icons/icons/folder.png'
                           ,tooltip : 'Capturar Detalle'
                           ,handler : this.onRevisionDocumentoClick
                       }
                   ]
           },
            {
                text            :'Observaciones',  					width           : 300,
                dataIndex       :'observaciones'
        	}
        ]
    });
    gridDatosTarificacion.store.sort([
        { 
            property    : 'dsgarant',
            direction   : 'ASC'
        }
    ]);
    
    
    
    
    Ext.create('Ext.form.Panel',
    	    {
    	        border    : 0
    	        ,title: 'Afiliados Afectados'
    	        ,renderTo : 'div_clau'
	        	,bodyPadding: 5
	            ,width: 800
	            ,layout     :
		    	{
		    		type     : 'table'
		    		,columns : 2
		    	}
		        ,defaults   :
		        {
		        	style : 'margin:5px;'
		        }
		        ,
		        items    :
    	        [
    	            {
		    	    	xtype       : 'textfield'
                        ,fieldLabel : 'No. de Tr&aacute;mite'
                        ,name       : 'smap1.noTramite'
                        ,allowBlank : false
                    }
		    	    ,{
		    	    	xtype       : 'textfield'
                        ,fieldLabel : 'fecha de Factura'
                        ,name       : 'smap1.fechaFactura'
                        ,allowBlank : false
		    	    }
		    	    ,{
		    	    	xtype       : 'textfield'
                        ,fieldLabel : 'Factura'
                        ,name       : 'smap1.factura'
                        ,allowBlank : false
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
	    	    ]
    	        ,
    	        buttonAlign:'center',
    	        buttons: [{
    	            id:'botonCotizar',
    	            icon:_CONTEXT+'/resources/fam3icons/icons/calculator.png',
    	            //text: hayTramiteCargado?'Precaptura':'Cotizar',
    	            text: 'Generar Tramite',
            		handler: function() {
            			
            			var form = this.up('form').getForm();
            			if (form.isValid())
    	                {
            				Ext.Msg.show({
    	                    	title:'Exito',
    	                    	msg: 'Se contemplaron todo',
    	                    	buttons: Ext.Msg.OK,
    	                    	icon: Ext.Msg.WARNING
    	                	});
    	                }
            			else
        				{
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
        				}
    	            }
    	        },
    	        {
    	            text:'Limpiar',
    	            icon:_CONTEXT+'/resources/fam3icons/icons/arrow_refresh.png',
    	            id:'botonLimpiar',
    	            handler:function()
    	            {}
    	        }
    	    ]
    	    }); 
    //gridIncisos
});