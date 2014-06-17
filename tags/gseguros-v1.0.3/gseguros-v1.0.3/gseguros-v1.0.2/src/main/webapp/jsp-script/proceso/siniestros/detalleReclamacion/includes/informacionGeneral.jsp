<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp"%>

<script type="text/javascript">

Ext.onReady(function() {
 
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
	            name       : 'params.contraRecibo',
	            fieldLabel : 'Contra recibo',
	            colspan    : 2,
	            labelWidth : 250
	        },{
	        	xtype      : 'textfield',
	        	name       : 'params.estado',
	            fieldLabel : 'Estado',
	            colspan    : 2,
	            labelWidth : 250
	        },{            
	        	xtype: 'combo',
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
	            xtype      : 'textfield',
	            fieldLabel : 'Fecha recepci&oacute;n',
	            colspan    : 2,
	            labelWidth : 250,
	            name       : 'params.fechaRecepcion'
	        },{
	            xtype      : 'datefield',
	            fieldLabel : 'Fecha ocurrencia',
	            labelWidth : 250,
	            name       : 'params.fechaOcurrencia',
	            format     : 'd/m/Y',
	            colspan    : 2,
	            editable   : true,
	            value      : new Date()
	        },{
	        	xtype       : 'combo',
				name        : 'params.origenSiniestro',
				fieldLabel  : 'Origen siniestro',
				//store     : storeCirculoHospitalario,
				colspan     : 2,
				queryMode   :'local',
				displayField: 'value',
				valueField  : 'key',
				allowBlank  : false,
				editable    : false,
				labelWidth  : 250,
				emptyText   :'Seleccione...'
	        },{
	            xtype      : 'textfield',
	            name       : 'params.plan',
	            fieldLabel : 'Plan',
	            colspan    : 2,
	            labelWidth : 250 
	        },{
	            xtype      : 'textfield',
	            fieldLabel : 'Circulo hospitalario',
	            colspan    : 2,
	            labelWidth : 250,
	            name       : 'params.circuloHospitalario'
	        },{
	            xtype      : 'textfield',
	            name       : 'params.zonaTarificacion',
	            fieldLabel : 'Zona tarificaci&oacute;n',
	            colspan    : 2,
	            labelWidth : 250
	        },{
	            xtype      : 'textfield',
	            name       : 'params.sumAseguradaContr',
	            fieldLabel : 'Suma asegurada contratada',
	            colspan    : 2,
	            labelWidth : 250
	        },{
	        	xtype       : 'combo',
	        	name        : 'params.tipoPago',
	        	colspan     : 2,
	            fieldLabel  : 'Tipo pago',
	            //store     : storeCirculoHospitalario,
	            queryMode   : 'local',
	            displayField: 'value',
	            valueField  : 'key',
	            allowBlank  : false,
	            editable    : false,
	            labelWidth  : 250,
	            emptyText   : 'Seleccione...'
	        },{
	            xtype      : 'textfield',
	            name       : 'params.poliza',
	            fieldLabel : 'P&oacute;liza',
	            labelWidth : 250
	        },{
	        	xtype  : 'button',
	        	text   : 'Ver detalle p&oacute;liza',
                width  : 180,
                icon   : _CONTEXT + '/resources/fam3icons/icons/application_view_list.png'
	        },{
	            xtype      : 'textfield',
	            name       : 'params.sumaDisponible',
	            fieldLabel : 'Suma asegurada disponible',
	            labelWidth : 250
	        },{
	        	xtype: 'button',
	        	text: 'Ver coberturas',
                width : 180,
                icon : _CONTEXT + '/resources/fam3icons/icons/application_view_list.png'
	        },{
	            xtype      : 'textfield',
	            name       : 'params.fechaInicioVigencia',
	            fieldLabel : 'Inicio vigencia',
	            labelWidth : 250
	        },{
	        	xtype  : 'button',
	        	text   : 'Ver historial de reclamaci&oacute;n',
                width  : 180,
                icon   : _CONTEXT + '/resources/fam3icons/icons/application_view_list.png'
	        },{
	            xtype      : 'textfield',
	            name       : 'params.fechaFinVigencia',
	            fieldLabel : 'Fin vigencia',
	            labelWidth : 250
	        },{
	        	xtype  : 'button',
	        	text   : 'Ver exclusiones p&oacute;liza',
                width  : 180,
                icon   : _CONTEXT + '/resources/fam3icons/icons/application_view_list.png'
	        },{
	            xtype      : 'textfield',
	            name       : 'params.estatusPoliza',
	            fieldLabel : 'Estatus p&oacute;liza',
	            labelWidth : 250
	        },{
	        	xtype  : 'button',
	        	text   : 'Ver historial rehabilitaciones',
                width  : 180,
                icon   : _CONTEXT + '/resources/fam3icons/icons/application_view_list.png'
	        },{
	        	xtype      : 'textfield',
	        	name       : 'params.fechaAntiguedad',
	            fieldLabel : 'Reconocimiento de Antig&uuml;edad',
	            readOnly   : true,
	            labelWidth : 250,
	            colspan    : 2
	        },{
	            xtype      : 'textfield',
	            name       : 'params.fechaAntiguedadGSS',
	            fieldLabel : 'Antig&uuml;edad con General de Salud',
	            readOnly   : true,
	            labelWidth : 250
	        },{
	            xtype      : 'textfield',
	            name       : 'params.tiempoAntiguedadGSS',
	            //,fieldLabel : 'Antig&uuml;edad con General de Salud'
	            readOnly   : true,
	            labelWidth : 250
	        },{
	            xtype      : 'textfield',
	            name       : 'params.formaPagoPoliza',
	            fieldLabel : 'Forma de pago de la p&oacute;liza',
	            readOnly   : true,
	            labelWidth : 250,
	            colspan    : 2
	        },{
				xtype       : 'combo',
				colspan     : 2,
				name        : 'params.aseguradoAfectado',
				fieldLabel  : 'Asegurado afectado',
				//store     : storeCirculoHospitalario,
				queryMode   : 'local',
				displayField: 'value',
				valueField  : 'key',
				allowBlank  : false,
				editable    : false,
				labelWidth  : 250,
				emptyText   : 'Seleccione...'
	        },{
				xtype       : 'combo',
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
	            xtype      : 'textfield',
	            name       : 'params.circuloHospitalario2',
	            fieldLabel : 'Circulo hospitalario',
	            readOnly   : true,
	            labelWidth : 150
	        },{
	        	xtype       : 'combo',
	        	name        : 'params.icd',
	        	colspan     : 2,
	            fieldLabel  : 'ICD',
	            //store     : storeCirculoHospitalario,
	            queryMode   : 'local',
	            displayField: 'value',
	            valueField  : 'key',
	            allowBlank  : false,
	            editable    : false,
	            labelWidth  : 250,
	            emptyText   : 'Seleccione...'
	        },{
	        	xtype       : 'combo',
	        	name        : 'params.icdSecundario',
				colspan     : 2,
				fieldLabel  : 'ICD secundario',
				//store     : storeCirculoHospitalario,
				queryMode   : 'local',
				displayField: 'value',
				valueField  : 'key',
				allowBlank  : false,
				editable    : false,
				labelWidth  : 250,
				emptyText   : 'Seleccione...'
	        }
	    ],
	    buttonAlign:'center',
	    buttons: [{
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
	        handler:function()
	        {}
	    }]
	}); 

});
</script>
<div id="dvInformacionGeneral" style="height:870px;"></div>