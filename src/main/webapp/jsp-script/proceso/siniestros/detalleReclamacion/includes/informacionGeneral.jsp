<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp"%>

<script type="text/javascript">

	/*
	var _CDUNIECO = '<s:property value="params.cdunieco" />';
	var _CDRAMO   = '<s:property value="params.cdramo" />';
	var _ESTADO   = '<s:property value="params.estado" />';
	var _NMPOLIZA = '<s:property value="params.nmpoliza" />';
	var _NMSITUAC = '<s:property value="params.nmsituac" />';
	var _NMSUPLEM = '<s:property value="params.nmsuplem" />';
	var _STATUS   = '<s:property value="params.status" />';// status del siniestro
	var _AAAPERTU = '<s:property value="params.aaapertu" />';
	var _NMSINIES = '<s:property value="params.nmsinies" />';
	*/

	var _URL_CATALOGOS             = '<s:url namespace="/catalogos"  action="obtieneCatalogo" />';
	var _URL_LOADER_VER_COBERTURAS = '<s:url namespace="/siniestros" action="includes/verCoberturas" />';

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
		    	colspan    : 2,
		        style      : 'margin:5px;',
		        labelWidth : 200
		    },
		    items:
		    [
		        {
		            xtype      : 'textfield',
		            name       : 'params.contraRecibo',
		            fieldLabel : 'Contra recibo'
		        },{
		        	xtype      : 'textfield',
		        	name       : 'params.estado',
		            fieldLabel : 'Estado'
		        },{            
		        	xtype: 'combo',
					name:'params.oficinaEmisora',
					fieldLabel: 'Oficina emisora',
					queryMode:'local',
					displayField: 'value',
					valueField: 'key',
					allowBlank:false,
					forceSelection : true,
					emptyText:'Seleccione...',
					store : Ext.create('Ext.data.Store', {
	                    model:'Generic',
	                    autoLoad:true,
	                    proxy: {
	                        type: 'ajax',
	                        url:  _URL_CATALOGOS,
	                        extraParams : {
	                        	catalogo:'<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@MC_SUCURSALES_ADMIN"/>'
	                        },
	                        reader: {
	                            type: 'json',
	                            root: 'lista'
	                        }
	                    }
	                })
		        },{
		        	xtype: 'combo',
					name:'params.oficinaDocumento',
					fieldLabel: 'Oficina documento',
					queryMode:'local',
					displayField: 'value',
					valueField: 'key',
					allowBlank:false,
					forceSelection : true,
					emptyText:'Seleccione...',
					store : Ext.create('Ext.data.Store', {
	                    model:'Generic',
	                    autoLoad:true,
	                    proxy: {
	                        type: 'ajax',
	                        url:  _URL_CATALOGOS,
	                        extraParams : {
	                            catalogo:'<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@MC_SUCURSALES_DOCUMENTO"/>'
	                        },
	                        reader: {
	                            type: 'json',
	                            root: 'lista'
	                        }
	                    }
	                })
		        },{
		            xtype      : 'textfield',
		            fieldLabel : 'Fecha recepci&oacute;n',
		            name       : 'params.fechaRecepcion'
		        },{
		            xtype      : 'datefield',
		            fieldLabel : 'Fecha ocurrencia',
		            name       : 'params.fechaOcurrencia',
		            format     : 'd/m/Y',
		            value      : new Date()
		        },{
		        	xtype       : 'combo',
					name        : 'params.origenSiniestro',
					fieldLabel  : 'Origen siniestro',
					queryMode   : 'local',
					displayField: 'value',
					valueField  : 'key',
					allowBlank  : false,
					forceSelection : true,
					emptyText   :'Seleccione...',
					store : Ext.create('Ext.data.Store', {
	                    model:'Generic',
	                    autoLoad:true,
	                    proxy: {
	                        type: 'ajax',
	                        url:  _URL_CATALOGOS,
	                        extraParams : {
	                            catalogo:'<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@CAUSA_SINIESTRO"/>'
	                        },
	                        reader: {
	                            type: 'json',
	                            root: 'lista'
	                        }
	                    }
	                })
		        },{
		            xtype      : 'textfield',
		            name       : 'params.plan',
		            fieldLabel : 'Plan'
		        },{
		            xtype      : 'textfield',
		            fieldLabel : 'Circulo hospitalario',
		            name       : 'params.circuloHospitalario'
		        },{
		            xtype      : 'textfield',
		            name       : 'params.zonaTarificacion',
		            fieldLabel : 'Zona tarificaci&oacute;n'
		        },{
		            xtype      : 'textfield',
		            name       : 'params.sumAseguradaContr',
		            fieldLabel : 'Suma asegurada contratada'
		        },{
		        	xtype       : 'combo',
		        	name        : 'params.tipoPago',
		            fieldLabel  : 'Tipo pago',
		            queryMode   : 'local',
		            displayField: 'value',
		            valueField  : 'key',
		            allowBlank  : false,
		            forceSelection : true,
		            emptyText   : 'Seleccione...',
		            store : Ext.create('Ext.data.Store', {
	                    model:'Generic',
	                    autoLoad:true,
	                    proxy: {
	                        type: 'ajax',
	                        url:  _URL_CATALOGOS,
	                        extraParams : {
	                            catalogo:'<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TIPO_PAGO_SINIESTROS"/>'
	                        },
	                        reader: {
	                            type: 'json',
	                            root: 'lista'
	                        }
	                    }
	                })
		        },{
		        	colspan    : 1,
		            xtype      : 'textfield',
		            name       : 'params.poliza',
		            fieldLabel : 'P&oacute;liza'
		        },{
		        	colspan    : 1,
		        	xtype  : 'button',
		        	text   : 'Ver detalle p&oacute;liza',
	                width  : 180,
	                icon   : _CONTEXT + '/resources/fam3icons/icons/application_view_list.png'
		        },{
		        	colspan    : 1,
		            xtype      : 'textfield',
		            name       : 'params.sumaDisponible',
		            fieldLabel : 'Suma asegurada disponible'
		        },{
		        	colspan    : 1,
		        	xtype: 'button',
		        	text: 'Ver coberturas',
	                width : 180,
	                icon : _CONTEXT + '/resources/fam3icons/icons/application_view_list.png',
	                handler: function() {
	                    Ext.create('Ext.window.Window', {
	                       title        : 'Coberturas de la p&oacute;liza',
	                       //modal       : true,
	                       buttonAlign : 'center',
	                       width       : 620,
	                       height      : 400,
	                       autoScroll  : true,
	                       loader      : {
	                           url      : _URL_LOADER_VER_COBERTURAS,
	                           scripts  : true,
	                           autoLoad : true,
	                           params:{
	                               'params.cdunieco'  : _CDUNIECO,
	                               'params.cdramo'    : _CDRAMO,
	                               'params.estado'    : _ESTADO,
	                               'params.nmpoliza'  : _NMPOLIZA,
	                               'params.suplemento': _NMSUPLEM
	                           }
	                       }
	                    }).showAt(150,150);
	                }
		        },{
		        	colspan    : 1,
		            xtype      : 'textfield',
		            name       : 'params.fechaInicioVigencia',
		            fieldLabel : 'Inicio vigencia'
		        },{
		        	colspan    : 1,
		        	xtype  : 'button',
		        	text   : 'Ver historial de reclamaci&oacute;n',
	                width  : 180,
	                icon   : _CONTEXT + '/resources/fam3icons/icons/application_view_list.png'
		        },{
		        	colspan    : 1,
		            xtype      : 'textfield',
		            name       : 'params.fechaFinVigencia',
		            fieldLabel : 'Fin vigencia'
		        },{
		        	colspan    : 1,
		        	xtype  : 'button',
		        	text   : 'Ver exclusiones p&oacute;liza',
	                width  : 180,
	                icon   : _CONTEXT + '/resources/fam3icons/icons/application_view_list.png'
		        },{
		        	colspan    : 1,
		            xtype      : 'textfield',
		            name       : 'params.estatusPoliza',
		            fieldLabel : 'Estatus p&oacute;liza'
		        },{
		        	colspan    : 1,
		        	xtype  : 'button',
		        	text   : 'Ver historial rehabilitaciones',
	                width  : 180,
	                icon   : _CONTEXT + '/resources/fam3icons/icons/application_view_list.png'
		        },{
		        	xtype      : 'textfield',
		        	name       : 'params.fechaAntiguedad',
		            fieldLabel : 'Reconocimiento de Antig&uuml;edad',
		            readOnly   : true
		        },{
		        	colspan    : 1,
		            xtype      : 'textfield',
		            name       : 'params.fechaAntiguedadGSS',
		            fieldLabel : 'Antig&uuml;edad con General de Salud',
		            readOnly   : true
		        },{
		        	colspan    : 1,
		            xtype      : 'textfield',
		            name       : 'params.tiempoAntiguedadGSS',
		            //,fieldLabel : 'Antig&uuml;edad con General de Salud'
		            readOnly   : true
		        },{
		            xtype      : 'textfield',
		            name       : 'params.formaPagoPoliza',
		            fieldLabel : 'Forma de pago de la p&oacute;liza',
		            readOnly   : true
		        },{
					xtype       : 'combo',
					name        : 'params.aseguradoAfectado',
					fieldLabel  : 'Asegurado afectado',
					//store     : storeCirculoHospitalario,
					queryMode   : 'local',
					displayField: 'value',
					valueField  : 'key',
					allowBlank  : false,
					forceSelection : true,
					emptyText   : 'Seleccione...'
		        },{
		        	colspan     : 1,
					xtype       : 'combo',
					name        : 'params.proveedor',
					fieldLabel  : 'Proveedor',
					emptyText   : 'Seleccione...',
					valueField  : 'key',
					displayField: 'value',
					allowBlank  : false,
					forceSelection : true,
					matchFieldWidth: false,
					hideTrigger : true,
	                triggerAction: 'all',
	                queryMode   : 'remote',
	                queryParam  : 'params.cdpresta',
	                minChars    : 2,
	                store       : Ext.create('Ext.data.Store', {
	                    model:'Generic',
	                    autoLoad:false,
	                    proxy: {
	                        type: 'ajax',
	                        url : _URL_CATALOGOS,
	                        extraParams:{
	                            catalogo:'<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@PROVEEDORES"/>'
	                            
	                        },
	                        reader: {
	                            type: 'json',
	                            root: 'lista'
	                        }
	                    }
	                })
		        },{
		        	colspan    : 1,
		            xtype      : 'textfield',
		            name       : 'params.circuloHospitalario2',
		            fieldLabel : 'Circulo hospitalario',
		            readOnly   : true,
		            labelWidth : 120
		        },{
		        	xtype       : 'combo',
		        	name        : 'params.icd',
		        	fieldLabel  : 'ICD',
		        	emptyText   : 'Seleccione...',
		        	valueField  : 'key',
		        	displayField: 'value',
		            allowBlank  : false,
		            forceSelection : true,
		            matchFieldWidth: false,
		            hideTrigger : true,
		            triggerAction: 'all',
		            queryMode   : 'remote',
	                queryParam  : 'params.otclave',
	                minChars    : 2,
	                store       : Ext.create('Ext.data.Store', {
	                    model:'Generic',
	                    autoLoad:false,
	                    proxy: {
	                        type: 'ajax',
	                        url : _URL_CATALOGOS,
	                        extraParams:{
	                            catalogo:'<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@ICD"/>',
	                            'params.cdtabla' : '2TABLICD'
	                        },
	                        reader: {
	                            type: 'json',
	                            root: 'lista'
	                        }
	                    }
	                })
		        },{
		        	xtype       : 'combo',
		        	name        : 'params.icdSecundario',
					fieldLabel  : 'ICD secundario',
					emptyText   : 'Seleccione...',
					valueField  : 'key',
					displayField: 'value',
					forceSelection : true,
					matchFieldWidth: false,
					hideTrigger : true,
					triggerAction: 'all',
					queryMode   : 'remote',
	                queryParam  : 'params.otclave',
	                minChars    : 2,
					store       : Ext.create('Ext.data.Store', {
				        model:'Generic',
				        autoLoad:false,
				        proxy: {
				            type: 'ajax',
				            url : _URL_CATALOGOS,
				            extraParams:{
				            	catalogo:'<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@ICD"/>',
				                'params.cdtabla' : '2TABLICD'
				            },
				            reader: {
				                type: 'json',
				                root: 'lista'
				            }
				        }
				    })
		        }
		    ],
		    buttonAlign:'center',
		    buttons: [{
		        icon:_CONTEXT+'/resources/fam3icons/icons/calculator.png',
		        text: 'Guardar',
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
		    }/*,{
		        text:'Limpiar',
		        icon:_CONTEXT+'/resources/fam3icons/icons/arrow_refresh.png',
		        handler:function()
		        {}
		    }*/]
		}); 
	
	});
</script>
<div id="dvInformacionGeneral" style="height:870px;"></div>