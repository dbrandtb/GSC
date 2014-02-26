<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp"%>

<script type="text/javascript">

Ext.onReady(function() {
/*
 * Se cambió informacionGral por pnlInformacionGral
 */
 
	var pnlInformacionGral= Ext.create('Ext.form.Panel',{
	    border    : 0,
	    renderTo : 'dvInformacionGeneral',
	    bodyPadding: 10,
	    width: 900,
	    layout :{
	        type   : 'table',
	        columns: 2
	    },
	    defaults:{
	        style : 'margin:5px;'
	    },
	    items:
	    [
	        {
	            xtype      : 'textfield',
	            id         :'txtContraRecibo',
	            name       : 'params.contraRecibo',
	            fieldLabel : 'Contra recibo',
	            colspan    : 2,
	            labelWidth : 250
	        },{
	        	xtype      : 'textfield',
	            id:        'txtEstado',
	            name       : 'params.estado',
	            fieldLabel : 'Estado',
	            colspan    : 2,
	            labelWidth : 250
	        },{            
	        	xtype: 'combo',
				id:'oficinaEmisora',
				name:'params.oficinaEmisora',
				fieldLabel: 'Oficina emisora',
				//store: storeCirculoHospitalario,
				colspan:2,
				queryMode:'local',
				displayField: 'value',
				valueField: 'key',
				allowBlank:false,
				editable:false,
				labelWidth : 250,
				emptyText:'Seleccione...'
	        },{
	        	xtype: 'combo',
				id:'oficinaDocumento',
				name:'params.oficinaDocumento',
				fieldLabel: 'Oficina documento',
				colspan:2,
				//store: storeCirculoHospitalario,
				queryMode:'local',
				displayField: 'value',
				valueField: 'key',
				allowBlank:false,
				editable:false,
				labelWidth : 250,
				emptyText:'Seleccione...'
	        },{
	            id         : 'params.fechaRecepcion',
	            xtype      : 'textfield',
	            fieldLabel : 'Fecha recepci&oacute;n',
	            colspan    : 2,
	            labelWidth : 250,
	            name       : 'params.fechaRecepcion'
	        },{
	            id         : 'fechaOcurrencia',
	            xtype      : 'datefield',
	            fieldLabel : 'Fecha ocurrencia',
	            labelWidth : 250,
	            name       : 'params.fechaOcurrencia',
	            format     : 'd/m/Y',
	            colspan    : 2,
	            editable   : true,
	            value      :new Date()
	        },{
	        	xtype: 'combo',
				id:'origenSiniestro',
				name:'params.origenSiniestro',
				fieldLabel: 'Origen siniestro',
				//store: storeCirculoHospitalario,
				colspan:2,
				queryMode:'local',
				displayField: 'value',
				valueField: 'key',
				allowBlank:false,
				editable:false,
				labelWidth : 250,
				emptyText:'Seleccione...'
	        },{
	            id: 'txtPlan',
	            xtype      : 'textfield',
	            fieldLabel : 'Plan',
	            colspan    :2,
	            labelWidth : 250,
	            name       : 'params.plan'
	        },{
	            id         : 'txtCirculoHospitalario',
	            xtype      : 'textfield',
	            fieldLabel : 'Circulo hospitalario',
	            colspan    :2,
	            labelWidth : 250,
	            name       : 'params.circuloHospitalario'
	        },{
	            id         : 'txtZonaTarificacion',
	            xtype      : 'textfield',
	            fieldLabel : 'Zona tarificaci&oacute;n',
	            colspan    : 2,
	            labelWidth : 250,
	            name       : 'params.zonaTarificacion'
	        },{
	            id: 'txtSumAseguradaContratada' ,
	            xtype      : 'textfield',
	            fieldLabel : 'Suma asegurada contratada',
	            colspan    : 2,
	            labelWidth : 250,
	            name       : 'params.sumAseguradaContr'
	        },{
	        	xtype: 'combo',
	        	colspan:2,
	            id:'tipoPago',
	            name:'params.tipoPago',
	            fieldLabel: 'Tipo pago',
	            //store: storeCirculoHospitalario,
	            queryMode:'local',
	            displayField: 'value',
	            valueField: 'key',
	            allowBlank:false,
	            editable:false,
	            labelWidth : 250,
	            emptyText:'Seleccione...'
	        },{
	            id: 'txtPoliza',
	            xtype      : 'textfield',
	            fieldLabel : 'P&oacute;liza',
	            labelWidth: 250,
	            name       : 'params.poliza'
	        },
	        Ext.create('Ext.Button', {
	            text: 'Ver detalle p&oacute;liza',
	            width : 180,
	            icon : _CONTEXT + '/resources/fam3icons/icons/application_view_list.png'
	        })
	        ,
	        {
	            id: 'txtSumaDisponible'         ,xtype      : 'textfield'                   ,fieldLabel : 'Suma asegurada disponible',
	            labelWidth: 250                 ,name       : 'params.sumaDisponible'
	            
	        },
	        Ext.create('Ext.Button', {
	            text: 'Ver coberturas',
	            width : 180,
	            icon : _CONTEXT + '/resources/fam3icons/icons/application_view_list.png'
	        })
	        ,
	        {
	            id: 'fechaInicioVigencia'       ,xtype      : 'textfield'                   ,fieldLabel : 'Inicio vigencia',
	            labelWidth : 250                ,name       : 'params.fechaInicioVigencia'
	        }
	        ,
	        Ext.create('Ext.Button', {
	            text: 'Ver historial de reclamaci&oacute;n',
	            width : 180,
	            icon : _CONTEXT + '/resources/fam3icons/icons/application_view_list.png'
	        })
	        ,
	        {
	            id:'fechaFinVigencia',xtype      : 'textfield',fieldLabel : 'Fin vigencia',labelWidth : 250 ,name       : 'params.fechaFinVigencia'
	        }
	        ,
	        Ext.create('Ext.Button', {
	            text: 'Ver exclusiones p&oacute;liza',
	            width : 180,
	            icon : _CONTEXT + '/resources/fam3icons/icons/application_view_list.png'
	        })
	        ,
	        {
	            id:'estatusPoliza',xtype      : 'textfield',fieldLabel : 'Estatus p&oacute;liza',labelWidth : 250,name       : 'params.estatusPoliza'
	        }
	        ,
	        Ext.create('Ext.Button', {
	            text: 'Ver historial rehabilitaciones',
	            width : 180,
	            icon : _CONTEXT + '/resources/fam3icons/icons/application_view_list.png'
	        })
	        ,
	        {
	            id:'fechaAntiguedad',xtype      : 'textfield'
	            ,fieldLabel : 'Reconocimiento de Antig&uuml;edad'
	            ,readOnly   : true
	            ,labelWidth : 250
	            ,name       : 'params.fechaAntiguedad'
	            ,colspan    : 2
	        }
	        ,
	        {
	            id:'fechaAntiguedadGSS'
	            ,xtype      : 'textfield'
	            ,fieldLabel : 'Antig&uuml;edad con General de Salud'
	            ,readOnly   : true
	            ,labelWidth : 250
	            ,name       : 'params.fechaAntiguedadGSS'
	        }
	        ,
	        {
	            id:'tiempoAntiguedadGSS'
	            ,xtype      : 'textfield'
	            //,fieldLabel : 'Antig&uuml;edad con General de Salud'
	            ,readOnly   : true
	            ,labelWidth : 250
	            ,name       : 'params.tiempoAntiguedadGSS'
	        }
	        ,
	        {
	            id:'formaPagoPoliza'
	            ,xtype      : 'textfield'
	            ,fieldLabel : 'Forma de pago de la p&oacute;liza'
	            ,readOnly   : true
	            ,labelWidth : 250
	            ,name       : 'params.formaPagoPoliza'
	            ,colspan    : 2
	        },{
				xtype: 'combo',
				colspan:2,
				id:'aseguradoAfectado',
				name:'params.aseguradoAfectado',
				fieldLabel: 'Asegurado afectado',
				//store: storeCirculoHospitalario,
				queryMode:'local',
				displayField: 'value',
				valueField: 'key',
				allowBlank:false,
				editable:false,
				labelWidth : 250,
				emptyText:'Seleccione...'
	        },{
				xtype: 'combo',
				id:'proveedor',
				name:'params.proveedor',
				fieldLabel: 'Proveedor',
				//store: storeCirculoHospitalario,
				queryMode:'local',
				displayField: 'value',
				valueField: 'key',
				allowBlank:false,
				editable:false,
				labelWidth : 250,
				emptyText:'Seleccione...'
	        },{
	            id:'circuloHospitalario'
	            ,xtype      : 'textfield'
	            ,fieldLabel : 'Circulo hospitalario'
	            ,readOnly   : true
	            ,labelWidth : 150
	            ,name       : 'params.circuloHospitalario'
	        },{
	        	xtype: 'combo',
	        	colspan:2,
	            id:'icd',
	            name:'params.icd',
	            fieldLabel: 'ICD',
	            //store: storeCirculoHospitalario,
	            queryMode:'local',
	            displayField: 'value',
	            valueField: 'key',
	            allowBlank:false,
	            editable:false,
	            labelWidth : 250,
	            emptyText:'Seleccione...'
	        },{
	        	xtype: 'combo',
				colspan:2,
				id:'icdSecundario',
				name:'params.icdSecundario',
				fieldLabel: 'ICD secundario',
				//store: storeCirculoHospitalario,
				queryMode:'local',
				displayField: 'value',
				valueField: 'key',
				allowBlank:false,
				editable:false,
				labelWidth : 250,
				emptyText:'Seleccione...'
	        }
	    ],
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
	    },{
	        text:'Limpiar',
	        icon:_CONTEXT+'/resources/fam3icons/icons/arrow_refresh.png',
	        id:'botonLimpiar',
	        handler:function()
	        {}
	    }
	]
	}); 

});
</script>
<div id="dvInformacionGeneral" style="height:870px;"></div>