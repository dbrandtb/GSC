<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Personas</title>
<style>
.status{
	font-size:14px; 
	font-weight: bold;
}
</style>

<script>

////// overrides Para quitar overrides de paginas padre//////
Ext.override(Ext.form.TextField,
{
    beforeRender:function()
    {
    	Ext.apply(this,
        {
            labelWidth : 100
        });
        return this.callParent();
    }
});

Ext.override(Ext.form.NumberField,
{
    beforeRender:function()
    {
        Ext.apply(this,
        {
            decimalPrecision : 2
        });
        return this.callParent();
    }
});
////// overrides //////


//debug('PANTALLAS PERSONA: ',Ext.ComponentQuery.query('#companiaGroupId'));
//if(Ext.ComponentQuery.query('#companiaGroupId').length >= 1){
//	debugError('Error, no se puede crear mas de una instancia de la pantalla de contratante');
//}else
//{

////// variables //////
var _p22_urlObtenerPersonas     = '<s:url namespace="/catalogos"  action="obtenerPersonasPorRFC"              />';
var _p22_urlGuardar             = '<s:url namespace="/catalogos"  action="guardarPantallaPersonas"            />';
var _p22_urlObtenerDomicilio    = '<s:url namespace="/catalogos"  action="obtenerDomicilioPorCdperson"        />';
var _p22_urlTatriperTvaloper    = '<s:url namespace="/catalogos"  action="obtenerTatriperTvaloperPorCdperson" />';
var _p22_urlGuadarTvaloper      = '<s:url namespace="/catalogos"  action="guardarDatosTvaloper"               />';
var _p22_urlPantallaDocumentos  = '<s:url namespace="/catalogos"  action="pantallaDocumentosPersona"          />';
var _p22_urlSubirArchivo        = '<s:url namespace="/"           action="subirArchivoPersona"                />';
var _p22_UrlUploadPro           = '<s:url namespace="/"           action="subirArchivoMostrarBarra"           />';
var _p22_urlViewDoc             = '<s:url namespace="/documentos" action="descargaDocInline"           />';
var _p22_urlCargarNombreArchivo = '<s:url namespace="/catalogos"  action="cargarNombreDocumentoPersona"       />';

var _URL_CARGA_CATALOGO = '<s:url namespace="/catalogos" action="obtieneCatalogo" />';
var _CAT_NACIONALIDAD  = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@NACIONALIDAD"/>';

var _UrlCargaAccionistas = '<s:url namespace="/catalogos" action="obtieneAccionistas" />';
var _UrlGuardaAccionista = '<s:url namespace="/catalogos" action="guardaAccionista" />';
var _UrlEliminaAccionistas = '<s:url namespace="/catalogos" action="eliminaAccionistas" />';

var _UrlActualizaStatusPersona = '<s:url namespace="/catalogos" action="actualizaStatusPersona" />';
var _UrlImportaPersonaWS = '<s:url namespace="/catalogos" action="importaPersonaExtWS" />';
var _UrlguardaPersonaWS = '<s:url namespace="/catalogos" action="guardarClienteCompania" />';

/* PARA EL LOADER */
var _p22_urlCargarPersonaCdperson = '<s:url namespace="/catalogos" action="obtenerPersonaPorCdperson" />';
/* PARA EL LOADER */

var _RUTA_DOCUMENTOS_PERSONA = '<s:text name="ruta.documentos.persona" />';

var _p22_windowAgregarDocu;

var windowAccionistas = undefined;
var accionistasStore;
var gridAccionistas;
var fieldEstCorp;
var _0_botAceptar;

var _statusDataDocsPersona;

var ultimoValorQueryRFC;


var _DocASubir;

/* PARA LOADER */
var _p22_smap1 = <s:property value='%{convertToJSON("smap1")}' escapeHtml="false" />;
debug('_p22_smap1:',_p22_smap1);


var _RFCsel;
var _RFCnomSel;

var _p22_cdperson = false;
var _p22_tipoPersona;
var _p22_nacionalidad;
var _CDIDEPERsel = '';
var _CDIDEEXTsel = '';
var _esSaludDanios;


var _p22_cdpersonTMP;
var _p22_tipoPersonaTMP;
var _p22_nacionalidadTMP;
var _CDIDEPERselTMP;
var _CDIDEEXTselTMP;
var _esSaludDaniosTMP;

var municipioImportarTMP;
var coloniaImportarTMP;


var _esCargaClienteNvo = false;
var _ocultaBusqueda    = false;
var _cargaCompania;
var _cargaFenacMin;
var _cargaFenacMax;
var _cargaCdPerson;
var _cargaCP;
var _cargaTipoPersona;
var _cargaSucursalEmi;

var _activaCveFamiliar = false;
var _PanelPrincipalPersonas;
var _panelTipoPer;

var _nombreComContratante;

var _modoRecuperaDanios;
var _modoSoloEdicion; // no es compatible con _esCargaClienteNvo
var _contrantantePrincipal;

if(!Ext.isEmpty(_p22_smap1)){
	
	_cargaCdPerson = _p22_smap1.cdperson;
	
	_esCargaClienteNvo = !Ext.isEmpty(_p22_smap1.esCargaClienteNvo) && _p22_smap1.esCargaClienteNvo == "S" ? true : false ;
	_ocultaBusqueda = !Ext.isEmpty(_p22_smap1.ocultaBusqueda) && _p22_smap1.ocultaBusqueda == "S" ? true : false ;
	_cargaCompania  = _p22_smap1.esSaludDanios;	
	_cargaFenacMin = _p22_smap1.cargaFenacMin;
	_cargaFenacMax = _p22_smap1.cargaFenacMax;
	_cargaCP = _p22_smap1.cargaCP;	
	_cargaTipoPersona = _p22_smap1.cargaTipoPersona;	
	_cargaSucursalEmi = _p22_smap1.cargaSucursalEmi;
	
	_activaCveFamiliar = !Ext.isEmpty(_p22_smap1.activaCveFamiliar) && _p22_smap1.activaCveFamiliar == "S" ? true : false ;
	_modoRecuperaDanios = !Ext.isEmpty(_p22_smap1.modoRecuperaDanios) && _p22_smap1.modoRecuperaDanios == "S" ? true : false ;
	_modoSoloEdicion = !Ext.isEmpty(_p22_smap1.modoSoloEdicion) && _p22_smap1.modoSoloEdicion == "S" ? true : false ;

	_contrantantePrincipal = !Ext.isEmpty(_p22_smap1.contrantantePrincipal) && _p22_smap1.contrantantePrincipal == "S" ? true : false ;
}

////// variables //////

Ext.onReady(function()
{
	
	// Se aumenta el timeout para todas las peticiones:
	Ext.Ajax.timeout = 1*60*60*1000; // 1 hora
	Ext.override(Ext.form.Basic, { timeout: Ext.Ajax.timeout / 1000 });
	Ext.override(Ext.data.proxy.Server, { timeout: Ext.Ajax.timeout });
	Ext.override(Ext.data.Connection, { timeout: Ext.Ajax.timeout });
	
	////// modelos //////
	Ext.define('_p22_modeloGrid',
	{
		extend  : 'Ext.data.Model'
		,fields : [ <s:property value="imap.gridModelFields" /> ]
	});
	
	Ext.define('_p22_modeloDomicilio',
	{
		extend  : 'Ext.data.Model'
		,fields : [ <s:property value="imap.fieldsDomicilio" /> ]
	});
	////// modelos //////
	
	////// componentes //////
	////// componentes //////
	
	////// contenido //////
	_PanelPrincipalPersonas = Ext.create('Ext.panel.Panel',{
		renderTo  : '_p22_divpri'
		,defaults : { style : 'margin:5px;' }
	    ,border   : 0
	    ,itemId   : '_p22_PanelPrincipal'
	    ,items    :
	    [
	        Ext.create('Ext.form.Panel',
	        {
	        	 title        : _esCargaClienteNvo?"Ingrese el RFC de la Persona, posteriormente de clic en continuar para validar el RFC y seguir con la captura." : 
	        	 				(_modoSoloEdicion? "Escriba el RFC de la Persona a buscar y de clic en 'Continuar'." : "Escriba el RFC de la Persona a buscar/agregar y de clic en 'Continuar'. Si selecciona una persona de la lista ser&aacute; editada, de lo contrario se agregar&aacute; una nueva.")
	        	 ,itemId      : '_p22_formBusqueda'
	        	 ,hidden      : !Ext.isEmpty(_cargaCdPerson)
//	        	 ,layout      :
//	        	 {
//	        	     type     : 'table'
//	        	     ,columns : 3
//	        	 }
	        	 ,defaults    : { style : 'margin:5px;' }
	        	 ,items       : [{
	        	 					xtype      : 'fieldcontainer',
						            fieldLabel : 'Tipo de Compa&ntilde;ia',
						            defaultType: 'radiofield',
						            id        : 'companiaGroupId',
						            border: true,	
									defaults : { style : 'margin:5px;' },
						            layout: 'hbox',
						            items: [
						                {
						                    boxLabel  : 'General de Seguros',
						                    name      : 'smap1.esSalud',
						                    inputValue: 'D',
						                    checked   : true,
						                    id        : 'companiaId',
						                    listeners: {
			        	 						change: function(){
			        	 								var form=_p22_formBusqueda();
			        	 								form.down('[name=smap1.rfc]').reset();
						                        		form.down('[name=smap1.nombre]').reset();
			        	 								form.down('[name=smap1.rfc]').getStore().removeAll();
						                        		form.down('[name=smap1.nombre]').getStore().removeAll();
			        	 						}
			        	 					}
						                }, {
						                    boxLabel  : 'General de Salud',
						                    name      : 'smap1.esSalud',
//						                    id        : 'companiaIdSalud',
						                    inputValue: 'S',
						                    checked   : false
						                }
						            ]},{
	        	 				xtype: 'combobox',
								fieldLabel:'B&uacute;squeda por RFC',
								labelWidth: 100,
								width:    800,
								queryParam  : 'smap1.rfc',
								queryMode   : 'remote',
								queryCaching: false,
								allQuery    : 'dummyForAllQuery',
            					minChars    : 9,
								minLength   : 2,
//								queryDelay  : 2000,
								name          : 'smap1.rfc',
					            valueField    : 'CDRFC',
					            displayField  : 'NOMBRE_COMPLETO',
					            autoSelect    : false,
					            hideTrigger   : true,
					            tpl: Ext.create('Ext.XTemplate',
					                    '<tpl for=".">',
					                        '<div class="x-boundlist-item">{CDRFC} - {DSNOMBRE} {DSNOMBRE1} {DSAPELLIDO} {DSAPELLIDO1} - {DIRECCIONCLI}</div>',
					                    '</tpl>'
					            ),
					            enableKeyEvents: true,
					            listeners: {
					            	select: function(comb, records){
					            		_RFCsel = records[0].get('CDRFC');
					            		_p22_cdpersonTMP = records[0].get('CDPERSON');
					            		_p22_tipoPersonaTMP = records[0].get('OTFISJUR');
					            		_p22_nacionalidadTMP = records[0].get('CDNACION');
					            		
					            		_CDIDEPERselTMP = records[0].get('CDIDEPER');
					            		_CDIDEEXTselTMP = records[0].get('CDIDEEXT');
					            		_esSaludDaniosTMP = Ext.ComponentQuery.query('#companiaId')[Ext.ComponentQuery.query('#companiaId').length-1].getGroupValue(); 
					            		
					            		var form=_p22_formBusqueda();
					            		form.down('[name=smap1.nombre]').reset();
					            		Ext.ComponentQuery.query('#btnContinuarId')[Ext.ComponentQuery.query('#btnContinuarId').length-1].setText(_modoRecuperaDanios?'Continuar y Recuperar Cliente':'Continuar y Editar Cliente');
					            	}, 
					            	keydown: function( com, e, eOpts ){
					            		var form=_p22_formBusqueda();
					            		
					            		if(e.isSpecialKey() || e.isNavKeyPress() || e.getKey() == e.CONTEXT_MENU || e.getKey() == e.HOME || e.getKey() == e.NUM_CENTER 
						            		|| e.getKey() == e.F1 || e.getKey() == e.F2 || e.getKey() == e.F3 || e.getKey() == e.F4 || e.getKey() == e.F5 || e.getKey() == e.F6 
						            		|| e.getKey() == e.F7 || e.getKey() == e.F8 || e.getKey() == e.F9 || e.getKey() == e.F10 || e.getKey() == e.F11 || e.getKey() == e.F12){
						            			if(e.getKey() == e.BACKSPACE){
						            				form.down('[name=smap1.rfc]').getStore().removeAll();
						            			}
					            		}else{
					            			_RFCsel = '';
						            		form.down('[name=smap1.nombre]').reset();
					            			form.down('[name=smap1.rfc]').getStore().removeAll();
					            			
					            			Ext.ComponentQuery.query('#btnContinuarId')[Ext.ComponentQuery.query('#btnContinuarId').length-1].setText(_esCargaClienteNvo?'Continuar': (_modoSoloEdicion?'Seleccione...' : 'Continuar y Agregar Cliente') );
					            		}
					            		
					            	},
					            	change: function(me, val, oldVal, eopts){
						    				try{
							    				if('string' == typeof val){
							    					if(String(val.toUpperCase()).indexOf(" -") != -1){
							    						me.setValue(ultimoValorQueryRFC);
							    					}else {
							    						ultimoValorQueryRFC = val.toUpperCase();
							    						me.setValue(ultimoValorQueryRFC);
							    					}
							    				}
						    				}
						    				catch(e){
						    					debug(e);
						    				}
									},
									beforequery: function( queryPlan, eOpts ){
									
										queryPlan.query = Ext.String.trim(queryPlan.query);
										if(String(queryPlan.query).indexOf(" -") != -1){
//											queryPlan.combo.getStore().removeAll();
//											debug("Cambiando el QueryPlan a: ",ultimoValorQueryRFC);
											queryPlan.query = ultimoValorQueryRFC;
//											queryPlan.rawQuery=true;
//											queryPlan.cancel = true;
//											debug("CAncelado!");
//											debug("haciendo query para: ", ultimoValorQueryRFC);
//											queryPlan.combo.doQuery(ultimoValorQueryRFC);
//											queryPlan.combo.setValue(ultimoValorQueryRFC,false);
//											queryPlan.combo.expand();
										}
									}
					            },
					            store         : Ext.create('Ext.data.Store', {
					                model     : '_p22_modeloGrid', 
					                proxy     : {
				                            type        : 'ajax'
				                            ,url        : _p22_urlObtenerPersonas
				                            ,reader     :
				                            {
				                                type  : 'json'
				                                ,root : 'slist1'
				                            }
				                        }
				                        ,listeners: {
				                        	beforeload: function( store, operation, eOpts){
				                        		operation.callback = function(records, op, succ){
				                        			if(succ){
					                        			var jsonResponse = Ext.decode(op.response.responseText);
	//				                        			debug(typeof jsonResponse.exito);
	//				                        			debug(jsonResponse.exito);
					                        			if(!jsonResponse.exito){
					                        				mensajeError('Error al hacer la consulta, Favor de Reintentar');
					                        				var form=_p22_formBusqueda();
						            						form.down('[name=smap1.rfc]').reset();
					                        				form.down('[name=smap1.nombre]').reset();
					                        			}
					                        			
					                        			if(_esCargaClienteNvo && jsonResponse.exito && records.length >0){
						                        			var form =_p22_formBusqueda();
				        	 								form.down('[name=smap1.rfc]').reset();
				        	 								form.down('[name=smap1.rfc]').getStore().removeAll();
							                        		
						                        			mensajeWarning('La persona para el RFC ingresado ya existe como cliente. Favor de volver a realizar la cotizaci&oacute;n como cliente existente.');
						                        			form.down('[name=smap1.rfc]').reset();
						                        			
						                        		}else if(_esCargaClienteNvo && jsonResponse.exito){
						                        			irModoAgregar();
						                        		}
					                        		}else{
					                        				mensajeError('Error al hacer la consulta, Favor de Reintentar2');
					                        				var form=_p22_formBusqueda();
						            						form.down('[name=smap1.rfc]').reset();
					                        				form.down('[name=smap1.nombre]').reset();
					                        		}
				                        		};
				                        		operation.params['smap1.esSalud'] = Ext.ComponentQuery.query('#companiaId')[Ext.ComponentQuery.query('#companiaId').length-1].getGroupValue(); //SALUD o DAÑOS
				                        		operation.params['smap1.validaTienePoliza'] = _esCargaClienteNvo?'S':'N';
				                        		Ext.ComponentQuery.query('#btnContinuarId')[Ext.ComponentQuery.query('#btnContinuarId').length-1].disable();
				                        		Ext.ComponentQuery.query('#companiaGroupId')[Ext.ComponentQuery.query('#companiaGroupId').length-1].disable();
				                        	},
				                        	load: function(store, records, successful, eOpts){
				                        		var form=_p22_formBusqueda();
//				                        		debug("luego del Load");
//				                        		debug(form.down('[name=smap1.rfc]').getValue());
//				                        		debug(form.down('[name=smap1.rfc]').getRawValue());
				                        		
				                        		form.down('[name=smap1.rfc]').setRawValue(form.down('[name=smap1.rfc]').getValue())
				                        		
				                        		Ext.ComponentQuery.query('#btnContinuarId')[Ext.ComponentQuery.query('#btnContinuarId').length-1].enable();
				                        		Ext.ComponentQuery.query('#companiaGroupId')[Ext.ComponentQuery.query('#companiaGroupId').length-1].enable();
				                        		
				                        	}
				                        }
					            })
								},
								{
								xtype: 'combobox',
								fieldLabel:'B&uacute;squeda por Nombre',
								labelWidth: 100,
								width:    800,
								queryParam  : 'smap1.nombre',
								queryMode   : 'remote',
								queryCaching: false,
								allQuery    : 'dummyForAllQuery',
            					minChars    : 2,
								minLength   : 2,
								name          : 'smap1.nombre',
					            valueField    : 'CDRFC',
					            displayField  : 'NOMBRE_COMPLETO',
					            forceSelection: false,
					            autoSelect    : false,
	                            typeAhead     : false,
					            anyMatch      : false,
					            hideTrigger   : true,
					            tpl: Ext.create('Ext.XTemplate',
					                    '<tpl for=".">',
					                        '<div class="x-boundlist-item">{CDRFC} - {DSNOMBRE} {DSNOMBRE1} {DSAPELLIDO} {DSAPELLIDO1} - {DIRECCIONCLI}</div>',
					                    '</tpl>'
					            ),
					            enableKeyEvents: true,
					            listeners: {
					            	select: function(comb, records){
					            		_RFCnomSel = records[0].get('CDRFC');
					            		_p22_cdpersonTMP = records[0].get('CDPERSON');
					            		_p22_tipoPersonaTMP = records[0].get('OTFISJUR');
					            		_p22_nacionalidadTMP = records[0].get('CDNACION');
					            		
					            		_CDIDEPERselTMP = records[0].get('CDIDEPER');
					            		_CDIDEEXTselTMP = records[0].get('CDIDEEXT');
					            		_esSaludDaniosTMP = Ext.ComponentQuery.query('#companiaId')[Ext.ComponentQuery.query('#companiaId').length-1].getGroupValue(); 
					            		
					            		var form=_p22_formBusqueda();
					            		form.down('[name=smap1.rfc]').reset();
					            		Ext.ComponentQuery.query('#btnContinuarId')[Ext.ComponentQuery.query('#btnContinuarId').length-1].setText(_modoRecuperaDanios?'Continuar y Recuperar Cliente':'Continuar y Editar Cliente');
					            	}, 
					            	keydown: function( com, e, eOpts ){
					            		var form=_p22_formBusqueda();
					            		
					            		if(e.isSpecialKey() || e.isNavKeyPress() || e.getKey() == e.CONTEXT_MENU || e.getKey() == e.HOME || e.getKey() == e.NUM_CENTER 
						            		|| e.getKey() == e.F1 || e.getKey() == e.F2 || e.getKey() == e.F3 || e.getKey() == e.F4 || e.getKey() == e.F5 || e.getKey() == e.F6 
						            		|| e.getKey() == e.F7 || e.getKey() == e.F8 || e.getKey() == e.F9 || e.getKey() == e.F10 || e.getKey() == e.F11 || e.getKey() == e.F12){
						            			if(e.getKey() == e.BACKSPACE){
						            				form.down('[name=smap1.nombre]').getStore().removeAll();
						            			}
					            		}else{
					            			_RFCnomSel = '';
						            		form.down('[name=smap1.rfc]').reset();
						            		form.down('[name=smap1.nombre]').getStore().removeAll();
					            			
					            			Ext.ComponentQuery.query('#btnContinuarId')[Ext.ComponentQuery.query('#btnContinuarId').length-1].setText(_esCargaClienteNvo?'Continuar': (_modoSoloEdicion?'Seleccione...' : 'Continuar y Agregar Cliente') );
					            		}
					            	},
					            	change: function(me, val){
						    				try{
							    				if('string' == typeof val){
//							    					debug('mayus de '+val);
							    					me.setValue(val.toUpperCase());
							    				}
						    				}
						    				catch(e){
						    					debug(e);
						    				}
									}
					            },
					            store         : Ext.create('Ext.data.Store', {
					                model     : '_p22_modeloGrid',
					                proxy     : {
				                            type        : 'ajax'
				                            ,url        : _p22_urlObtenerPersonas
				                            ,reader     :
				                            {
				                                type  : 'json'
				                                ,root : 'slist1'
				                            }
				                        }
				                        ,listeners: {
				                        	beforeload: function( store, operation, eOpts){
				                        		operation.callback = function(records, op, succ){
				                        			if(succ){
	//				                        			debug('op:',op);
					                        			var jsonResponse = Ext.decode(op.response.responseText);
					                        			if(!jsonResponse.exito){
					                        				mensajeError('Error al hacer la consulta, Favor de Reintentar');	
					                        				var form=_p22_formBusqueda();
						            						form.down('[name=smap1.rfc]').reset();
					                        				form.down('[name=smap1.nombre]').reset();
					                        			}
				                        			}else{
				                        				mensajeError('Error al hacer la consulta, Favor de Reintentar2');	
				                        				var form=_p22_formBusqueda();
					            						form.down('[name=smap1.rfc]').reset();
				                        				form.down('[name=smap1.nombre]').reset();
				                        			}
				                        		};
				                        		operation.params['smap1.esSalud'] = Ext.ComponentQuery.query('#companiaId')[Ext.ComponentQuery.query('#companiaId').length-1].getGroupValue(); //SALUD o DAÑOS
				                        		operation.params['smap1.validaTienePoliza'] = _esCargaClienteNvo?'S':'N';
				                        		Ext.ComponentQuery.query('#btnContinuarId')[Ext.ComponentQuery.query('#btnContinuarId').length-1].disable();
				                        		Ext.ComponentQuery.query('#companiaGroupId')[Ext.ComponentQuery.query('#companiaGroupId').length-1].disable();
				                        	},
				                        	load: function(store, records, successful, eOpts){
				                        		Ext.ComponentQuery.query('#btnContinuarId')[Ext.ComponentQuery.query('#btnContinuarId').length-1].enable();
				                        		Ext.ComponentQuery.query('#companiaGroupId')[Ext.ComponentQuery.query('#companiaGroupId').length-1].enable();
				                        		
				                        		if(_esCargaClienteNvo && successful && store.getCount()>0){
				                        			store.removeAll();
				                        			var form=_p22_formBusqueda();
				                        			if(!Ext.isEmpty(form.down('[name=smap1.rfc]').getValue())) mensajeWarning('La persona para el RFC: '+ form.down('[name=smap1.rfc]').getValue() +' ya existe como cliente. Favor de volver a realizar la cotizaci&oacute;n como cliente existente.');
				                        			form.down('[name=smap1.rfc]').reset();
				                        		}
				                        		
				                        	}
				                        }
					            })
								},{
										xtype      : 'hidden',
										name       : 'smap1.snombre',
										value      : ' '  // 1 INSERT, 2 UPDATE
									},
									{
										xtype      : 'hidden',
										name       : 'smap1.apat',
										value      : ' '  // 1 INSERT, 2 UPDATE
									},{
										xtype      : 'hidden',
										name       : 'smap1.amat',
										value      : ' '  // 1 INSERT, 2 UPDATE
									}
								]
	        	 ,buttonAlign : 'center'
	        	 ,buttons     :
	        	 [
	        	     {
                         text     : _esCargaClienteNvo?'Continuar': (_modoSoloEdicion?'Seleccione...' : 'Continuar y Agregar Cliente')
                         ,xtype   : 'button'
                         ,itemId  : 'btnContinuarId'
                         ,disabled: !_esCargaClienteNvo
                         ,icon    : '${ctx}/resources/fam3icons/icons/building_go.png'
                         ,handler : function (){
										var form = _p22_formBusqueda();
										
										var valorRFC = form.down('[name=smap1.rfc]').getValue();
										var valorRFCRaw = form.down('[name=smap1.rfc]').getRawValue();
										
										debug('valorRFC: ',valorRFC);
										debug('valorRFCRaw: ',valorRFCRaw);
										
										var valorNombre = form.down('[name=smap1.nombre]').getValue();
										var valorNombreRaw = form.down('[name=smap1.nombre]').getRawValue();
										
										debug('valorNombre: ',valorNombre);
										debug('valorNombreRaw: ',valorNombreRaw);
										
										/**
										 * PARA PROBLEMA CUANDO AVECES NO SE SELECCIONA CORRECTAMENTE
										 */
										
										if(Ext.isEmpty(valorRFC) && Ext.isEmpty(valorNombre) && Ext.isEmpty(valorRFCRaw) && Ext.isEmpty(valorNombreRaw)){
											mensajeWarning('Llene la informaci&oacute;n solicitada para continuar.');
											return;
										}
										
										if((!Ext.isEmpty(valorRFCRaw) && Ext.isEmpty(valorRFC)) || (!Ext.isEmpty(valorNombreRaw) && Ext.isEmpty(valorNombre))){
											mensajeWarning('Hubo un problema al seleccionar el Cliente. Vuelva a intentarlo.');
											form.down('[name=smap1.rfc]').reset();
								    		form.down('[name=smap1.nombre]').reset();
								    		form.down('[name=smap1.rfc]').getStore().removeAll();
								    		form.down('[name=smap1.nombre]').getStore().removeAll();
								    		return;
										}
										
										debug('valorRFC:',valorRFC);
										debug('valorNombre:',valorNombre);
										debug('_RFCsel:',_RFCsel);
										debug('_RFCnomSel:',_RFCnomSel);
										
										_nombreComContratante = '';
										if(Ext.isEmpty(valorRFC)){
											_nombreComContratante = form.down('[name=smap1.nombre]').getRawValue();
										}else{
											_nombreComContratante = form.down('[name=smap1.rfc]').getRawValue();
										}
										
										if( (!Ext.isEmpty(_RFCsel)&&(_RFCsel == valorRFC)) || ((!Ext.isEmpty(_RFCnomSel))&&(_RFCnomSel == valorNombre))){
											_p22_formDatosGenerales().getForm().reset();
											_p22_formDomicilio().getForm().reset();
											_p22_formDatosGenerales().hide();
								    		_p22_formDomicilio().hide();
								    		_p22_principalDatosAdicionales().hide();
								    		
								    		_fieldByName('CDMUNICI',_PanelPrincipalPersonas).forceSelection = false;
        									_fieldByName('CDEDO',_PanelPrincipalPersonas).forceSelection = false;
											
											_p22_cdperson = _p22_cdpersonTMP;
											_p22_tipoPersona = _p22_tipoPersonaTMP;
											_p22_nacionalidad = _p22_nacionalidadTMP;
											_CDIDEPERsel = _CDIDEPERselTMP;
											_CDIDEEXTsel = _CDIDEEXTselTMP;
											_esSaludDanios = _esSaludDaniosTMP;
											municipioImportarTMP = '';
											coloniaImportarTMP = '';
											
								    		//Si el la persona es proveniente de WS, primero se genera la persona y se inserta los datos del WS para luego ser editada
								    		if("1" == _p22_cdperson){
								    			importaPersonaWS( _esSaludDanios , (_esSaludDanios == 'S') ? _CDIDEEXTsel : _CDIDEPERsel );
								    			return;
								    		}
								    		
								    		form.down('[name=smap1.rfc]').reset();
								    		form.down('[name=smap1.nombre]').reset();
								    		form.down('[name=smap1.rfc]').getStore().removeAll();
								    		form.down('[name=smap1.nombre]').getStore().removeAll();
								    		
								    		
											irModoEdicion();
											
										}else if(!Ext.isEmpty(valorRFC)){
											
											if(_modoSoloEdicion){
												mensajeWarning('Seleccione una persona.');
												return;
											}
											
											_p22_formDatosGenerales().getForm().reset();
											_p22_formDomicilio().getForm().reset();
											_p22_formDatosGenerales().hide();
						    				_p22_formDomicilio().hide();
										    _p22_principalDatosAdicionales().hide();
										    
										    _fieldByName('CDMUNICI',_PanelPrincipalPersonas).forceSelection = false;
        									_fieldByName('CDEDO',_PanelPrincipalPersonas).forceSelection = false;
											
										    if(form.down('[name=smap1.rfc]').getStore().count() > 0){
										    	
										    	var confirm = Ext.Msg.show({
							    		            title: 'Confirmar acci&oacute;n',
							    		            msg: 'Este RFC ya est&aacute; registrado. &iquest;Desea capturar otro cliente con este mismo RFC?<br/> De no ser as&iacute; vuelva a seleccionar un cliente de la b&uacute;squeda.',
							    		            buttons: Ext.Msg.YESNO,
							    		            fn: function(buttonId, text, opt) {
							    		            	if(buttonId == 'yes') {
							    		            		_p22_fieldRFC().setValue(valorRFC);
															_p22_cdperson = '';
															_p22_tipoPersona = '';
															_p22_nacionalidad = '';
															_CDIDEPERsel = '';
															_CDIDEEXTsel = '';
															municipioImportarTMP = '';
															coloniaImportarTMP = '';
															
															form.down('[name=smap1.rfc]').reset();
															form.down('[name=smap1.nombre]').reset();
															form.down('[name=smap1.rfc]').getStore().removeAll();
												    		form.down('[name=smap1.nombre]').getStore().removeAll();
															
															_esSaludDanios = Ext.ComponentQuery.query('#companiaId')[Ext.ComponentQuery.query('#companiaId').length-1].getGroupValue();
															
															irModoAgregar();
							    		            	}else{
							    		            		return;
							    		            	}
							            			},
							    		            icon: Ext.Msg.QUESTION
							        			});
							        			
							        			centrarVentana(confirm);
										    	
										    }else{
										    	_p22_fieldRFC().setValue(valorRFC);
												_p22_cdperson = '';
												_p22_tipoPersona = '';
												_p22_nacionalidad = '';
												_CDIDEPERsel = '';
												_CDIDEEXTsel = '';
												municipioImportarTMP = '';
												coloniaImportarTMP = '';

												_fieldByName('CDMUNICI',_PanelPrincipalPersonas).forceSelection = true;
        										_fieldByName('CDEDO',_PanelPrincipalPersonas).forceSelection = true;
        									
												form.down('[name=smap1.rfc]').reset();
												form.down('[name=smap1.nombre]').reset();
												form.down('[name=smap1.rfc]').getStore().removeAll();
									    		form.down('[name=smap1.nombre]').getStore().removeAll();
												
												_esSaludDanios = Ext.ComponentQuery.query('#companiaId')[Ext.ComponentQuery.query('#companiaId').length-1].getGroupValue();
												
												if(_esCargaClienteNvo){
													form.down('[name=smap1.rfc]').doQuery(valorRFC,true,false);
												}else{
													irModoAgregar();
												}
												
										    }
										    
										}else if(!Ext.isEmpty(valorNombre)){
											
											if(!Ext.isEmpty(_nombreComContratante) && !Ext.isEmpty(valorNombre) && _nombreComContratante != valorNombre){
												mensajeWarning('Hubo un problema al seleccionar el Cliente. Vuelva a intentarlo.');
												form.down('[name=smap1.nombre]').reset();
												form.down('[name=smap1.nombre]').getStore().removeAll();
											}else{
												mensajeWarning('Seleccione un cliente de la b&uacute;squeda.');
											}
											
											return;
										}
									}
                     }
                 ]
	        })
	        ,Ext.create('Ext.form.Panel',
	        	    {
	        	    	title     : 'Datos generales de la Persona'
	        	    	,itemId   : '_p22_formDatosGenerales'
                        ,border   : 0
	        	    	,defaults : { style : 'margin:5px'}
	        	        ,layout   :
	        	        {
	        	        	type     : 'table'
	        	        	,columns : 3
	        	        }
	        	    	,items    : [ <s:property value="imap.datosGeneralesItems" escapeHtml="false" /> ]
	        	    })
	        	    ,Ext.create('Ext.form.Panel',
                    {
                        title     : 'Domicilio de la Persona'
                        ,itemId   : '_p22_formDomicilio'
                        ,border   : 0
                        ,defaults : { style : 'margin:5px' }
                        ,layout   :
                        {
                            type     : 'table'
                            ,columns : 3
                        }
                        ,items    : [ <s:property value="imap.itemsDomicilio" escapeHtml="false" /> ]
                    })
                    ,Ext.create('Ext.form.Panel',
                    {
                        title   : 'Datos adicionales de la Persona'
                    	,itemId : '_p22_principalDatosAdicionales'
                        ,border   : 0
                        //,defaults : { style : 'margin:5px' }
                        ,buttonAlign: 'center'
                        ,buttons    :
	                    [{
	                            text     : _modoRecuperaDanios?'Guardar datos y Recuperar Persona':'Guardar datos de Persona'
	                            ,itemId  : '_p22_botonGuardar'
	                            ,icon    : '${ctx}/resources/fam3icons/icons/disk.png'
	                            ,handler : function(){
	                            			if(!Ext.isEmpty(municipioImportarTMP)){
	                            				mensajeWarning("El municipio '" + municipioImportarTMP + "' no se ha encontrado, favor de seleccionar el equivalente u otro municipio de la lista.");
	                            				return;
	                            			}
	                            			
	                            			_p22_guardarClic(_p22_guardarDatosAdicionalesClic,false);
	                            }
	                    }]
                    })
                    
	    ]
	});
	////// contenido //////
	
	////// loaders //////
	
	_p22_comboCodPostal().addListener('blur',_p22_heredarColonia);
	_p22_fieldTipoPersona().addListener('change',_p22_tipoPersonaChange);
	_fieldByName('CDNACION',_PanelPrincipalPersonas).addListener('change',_p22_nacionalidadChange);
	_p22_tipoPersonaChange(_p22_fieldTipoPersona(),'F');
	_p22_nacionalidadChange(_fieldByName('CDNACION',_PanelPrincipalPersonas),'001');
	_fieldByName('NMNUMERO',_PanelPrincipalPersonas).regex = /^[A-Za-z0-9-\s]*$/;
	_fieldByName('NMNUMERO',_PanelPrincipalPersonas).regexText = 'Solo d&iacute;gitos, letras y guiones';
    _fieldByName('NMNUMINT',_PanelPrincipalPersonas).regex = /^[A-Za-z0-9-\s]*$/;
    _fieldByName('NMNUMINT',_PanelPrincipalPersonas).regexText = 'Solo d&iacute;gitos, letras y guiones';
    
    _p22_fieldCdperson().mpoliper = false;
    _p22_fieldCdperson().validado = false;
	////// loaders //////
    
    
    _p22_fieldCumuloPrima().addListener('select', function(){_p22_guardarClic(_p22_datosAdicionalesClic, true);});
    _p22_fieldResidente().addListener('select', function(){_p22_guardarClic(_p22_datosAdicionalesClic, true);});
    
    _p22_formDatosGenerales().hide();
    _p22_formDomicilio().hide();
    _p22_principalDatosAdicionales().hide();
    
    function irModoAgregar(){
    	var windowTipo;
    	_panelTipoPer = Ext.create('Ext.form.Panel', {
		    defaults : { style : 'margin:5px;' },
		    items: [	{
		    				xtype: 'displayfield',
		    				fieldLabel: 'RFC',
		    				value: _p22_fieldRFC().getValue()
		    			}
		    			,{	xtype: 'combobox',
						fieldLabel:'Tipo de persona',
						itemId: 'tipoPerAgregarId',
						allowBlank:false,
						typeAhead:true,
						anyMatch:true,
						displayField:'value',
						valueField:'key',
						forceSelection:true,
						editable:false,
						queryMode:'local',
						store:Ext.create('Ext.data.Store',{
							model:'Generic',
							autoLoad:true,
							proxy:{ type:'ajax',
									url:_URL_CARGA_CATALOGO,
									reader:{type:'json',
									root:'lista',
									rootProperty:'lista'
									},
									extraParams:{catalogo:'TIPOS_PERSONA'}
								},
							listeners : {
								load:function(str, records, successful, eOpts){
									
									if(_esCargaClienteNvo && !Ext.isEmpty(_cargaTipoPersona)){
										_p22_fieldTipoPersona().setValue(_cargaTipoPersona);
										Ext.ComponentQuery.query('#tipoPerAgregarId')[Ext.ComponentQuery.query('#tipoPerAgregarId').length-1].setValue(_cargaTipoPersona);
										Ext.ComponentQuery.query('#tipoPerAgregarId')[Ext.ComponentQuery.query('#tipoPerAgregarId').length-1].setReadOnly(true);
									}
								}
							}
						}),
						listeners: {
							select: function(combo,records){
								_p22_fieldTipoPersona().select(records[0]);
							}
						}
					},{
						xtype:'combobox',
						fieldLabel:'C&uacute;mulo de prima',
						name: 'PTCUMUPRAGR',
						allowBlank:false,
						typeAhead:true,
						anyMatch:true,
						displayField:'value',
						valueField:'key',
						forceSelection:true,
						editable:false,
						queryMode:'local',
						store:Ext.create('Ext.data.Store',{
						model:'Generic',
						autoLoad:true,
						proxy:{type:'ajax',
						url:_URL_CARGA_CATALOGO,
						reader:{type:'json',
						root:'lista',
						rootProperty:'lista'
						},
						extraParams:{catalogo:'TCUMULOS'}
						},
						listeners:{
							load: function(str, records, successful, eOpts){
								_p22_fieldCumuloPrimaModoAgregar().select(records[0]);
								_p22_fieldCumuloPrima().select(records[0]);
							}
						}
						}),
						listeners: {
							select: function(combo,records){
								_p22_fieldCumuloPrima().select(records[0]);
							}
						}
					},{
						xtype:'combobox',
						fieldLabel:'Nacionalidad',
						allowBlank:false,
						typeAhead:true,
						anyMatch:true,
						displayField:'value',
						valueField:'key',
						forceSelection:true,
						editable:false,
						queryMode:'local',
						store:Ext.create('Ext.data.Store',{
						model:'Generic',
						autoLoad:true,
						proxy:{type:'ajax',
						url:_URL_CARGA_CATALOGO,
						reader:{type:'json',
						root:'lista',
						rootProperty:'lista'
						},
						extraParams:{catalogo:'NACIONALIDAD'}
						}
						}),
						listeners: {
							select: function(combo,records){
								_p22_fielCdNacion().select(records[0]);
							},
							change:  _p22_nacionalidadChange2
						}
					},{
						xtype:'combobox',
						fieldLabel:'Residente',
						name: 'RESIDENTE2',
						allowBlank:false,
						typeAhead:true,
						anyMatch:true,
						displayField:'value',
						valueField:'key',
						forceSelection:true,
						editable:false,
						queryMode:'local',
						store:Ext.create('Ext.data.Store',{
						model:'Generic',
						autoLoad:true,
						proxy:{type:'ajax',
						url:_URL_CARGA_CATALOGO,
						reader:{type:'json',
						root:'lista',
						rootProperty:'lista'
						},
						extraParams:{catalogo:'TIPO_RESIDENCIA'}
						}
						}),
						listeners: {
							select: function(combo,records){
								_p22_fieldResidente().select(records[0]);
							}
						}
					}],
		    buttons: [{
		        text: 'Cancelar',
		        handler: function() {
		            this.up('form').getForm().reset();
		            windowTipo.close();
		        }
		    }, {
		        text: 'Aceptar',
		        formBind: true, //only enabled once the form is valid
		        disabled: true,
		        handler: function() {
		            var form = this.up('form').getForm();
		            if (form.isValid()) {
		            	
		            	if(!validarRFC(_p22_fieldRFC().getValue(),_p22_fieldTipoPersona().getValue())){
		            		return;
		            	}
		            	
		                _p22_formDatosGenerales().show();
		                _p22_formDomicilio().show();
		                _p22_principalDatosAdicionales().show();
		                
		                windowTipo.close();
		                
		                if(_esCargaClienteNvo && !Ext.isEmpty(_cargaCP)){
							_p22_comboCodPostal().setValue(_cargaCP);
							_p22_formBusqueda().hide();
							heredarPanel(_p22_formDomicilio());
							_p22_heredarColonia();
						}

						if(_ocultaBusqueda){
							_p22_formBusqueda().hide();
						}
						
						/**
						 * POR SI HAY UNA SUCURSAL A CARGAR EN EL MODO DE AGREGAR
						 * @type String
						 */
						if(!Ext.isEmpty(_cargaSucursalEmi)){
							_fieldByName('CDSUCEMI',_PanelPrincipalPersonas).setValue(_cargaSucursalEmi);
						}
		                
						try{
			    			var ventanaMensaje = window.parent;
			    			
			    			if (ventanaMensaje != window.top) {
							  debug('Para postMessage, El parent es el mismo que el top');
							}else{
							  debug('Para postMessage, El parent no es el mismo que el top');
							}
							
							var _codigoDanios = '';
							var _codigoSalud  = '';
							
							try{
								_codigoDanios = _CDIDEPERsel.substring(5);
							}catch(e){
								debug('Error al obtener codigo externo de Danios',e);
							}
							
							try{
								_codigoSalud = _CDIDEEXTsel.substring(5);
							}catch(e){
								debug('Error al obtener codigo externo de Salud',e);
							}
												
							var objMsg = {
								clienteIce: true,
								modo: 'A',
								cdperson: _p22_cdperson,
								cdideper: _CDIDEPERsel,
								cdideext: _CDIDEEXTsel,
								codigoDanios: _codigoDanios,
								codigoSalud:_codigoSalud
							};
							
							ventanaMensaje.postMessage(objMsg, "*");
							
			    		}catch(e){
			    			debugError('Error en postMessage',e);
			    		}
										    		
		                _p22_guardarClic(_p22_datosAdicionalesClic, true);
		            }
		        }
		    }]
		});
		
		
		windowTipo = Ext.create('Ext.window.Window', {
			title: 'Elija el tipo de persona a Agregar',
		    height: 230,
		    width: 300,
		    closable: false,
		    items: [_panelTipoPer]
		}).show();
		centrarVentanaInterna(windowTipo);
    	
    }
    
function irModoEdicion(){
	
	if(_modoRecuperaDanios){
		
		if(!Ext.isEmpty(_CDIDEPERsel)){
			try{
				var ventanaMensaje = window.parent;
				
				if (ventanaMensaje != window.top) {
				  debug('Para postMessage, El parent es el mismo que el top');
				}else{
				  debug('Para postMessage, El parent no es el mismo que el top');
				}
				
				var _codigoDanios = '';
				var _codigoSalud  = '';
				
				try{
					_codigoDanios = _CDIDEPERsel.substring(5);
				}catch(e){
					debug('Error al obtener codigo externo de Danios',e);
				}
				
				try{
					_codigoSalud = _CDIDEEXTsel.substring(5);
				}catch(e){
					debug('Error al obtener codigo externo de Salud',e);
				}
				
				var objMsg = {
					clienteIce: true,
					modo: 'R',
					cdperson: _p22_cdperson,
					cdideper: _CDIDEPERsel,
					cdideext: _CDIDEEXTsel,
					codigoDanios: _codigoDanios,
					codigoSalud:_codigoSalud
				};
				
				ventanaMensaje.postMessage(objMsg, "*");
				
				try{
                	if(_p22_recuperaCallback){
                    	_p22_recuperaCallback();
                	}
                }catch(e){
                	debug('Error en recupera callback',e)
                }		
                
				mensajeCorrecto('Aviso','Cliente Recuperado: ' + _codigoDanios);
			}catch(e){
				debugError('Error en postMessage',e);
			}
		}else{
			var loadMaskTabla = new Ext.LoadMask('_p22_divpri', {msg:"Recuperando Cliente..."});
			loadMaskTabla.show();
			Ext.Ajax.request(
	        {
	            url       : _UrlguardaPersonaWS
	            ,params: {
            		'smap1.esSalud' :  'D',// solo para Danios en este caso
            		'smap1.cdperson':  _p22_cdperson
            	}
	            ,success  : function(response)
	            {
	            	loadMaskTabla.hide();
	            	
	                var json = Ext.decode(response.responseText);
	                debug('response text:',json);
	                if(json.exito){
	                	try{
	                		var codigoExtGen = json.smap1.codigoExternoGen;
							var ventanaMensaje = window.parent;
							
							if (ventanaMensaje != window.top) {
							  debug('Para postMessage, El parent es el mismo que el top');
							}else{
							  debug('Para postMessage, El parent no es el mismo que el top');
							}
							
							var _codigoDanios = '';
							var _codigoSalud  = '';
							
							try{
								_codigoDanios = codigoExtGen.substring(5);
							}catch(e){
								debug('Error al obtener codigo externo de Danios',e);
							}
							
							try{
								_codigoSalud = _CDIDEEXTsel.substring(5);
							}catch(e){
								debug('Error al obtener codigo externo de Salud',e);
							}
							
							var objMsg = {
								clienteIce: true,
								modo: 'R',
								cdperson: _p22_cdperson,
								cdideper: codigoExtGen,
								cdideext: _CDIDEEXTsel,
								codigoDanios: _codigoDanios,
								codigoSalud:_codigoSalud
							};
							
							ventanaMensaje.postMessage(objMsg, "*");
							mensajeCorrecto('Aviso','Cliente Recuperado: ' + _codigoDanios);
						}catch(e){
							debugError('Error en postMessage',e);
						}
	                }
	                else
	                {
	                    mensajeError("Error al recuperar el c&oacute;digo externo del cliente. Datos incompletos.");
	                }
	            }
	            ,failure  : function()
	            {
	            	loadMaskTabla.hide();
	                errorComunicacion(null,'En recuperar c&oacute;digo externo. Consulte a soporte.');
	            }
			});
		}
		
		return;
	}
	
	if(_p22_cdperson!=false){
		_p22_formDatosGenerales().show();
		_p22_formDomicilio().show();
	    _p22_principalDatosAdicionales().show();

	    if(_ocultaBusqueda){
			_p22_formBusqueda().hide();
		}
		
		/**
		 * Autosave en true para auto guardado cuando carga la pantalla o cuando cambian los factores de art 140
		 */
	    _p22_loadRecordCdperson(function(){_p22_guardarClic(_p22_datosAdicionalesClic, true);},true);
	    
	    try{
			var ventanaMensaje = window.parent;
			
			if (ventanaMensaje != window.top) {
			  debug('Para postMessage, El parent es el mismo que el top');
			}else{
			  debug('Para postMessage, El parent no es el mismo que el top');
			}
			
			var _codigoDanios = '';
			var _codigoSalud  = '';
			
			try{
				_codigoDanios = _CDIDEPERsel.substring(5);
			}catch(e){
				debug('Error al obtener codigo externo de Danios',e);
			}
			
			try{
				_codigoSalud = _CDIDEEXTsel.substring(5);
			}catch(e){
				debug('Error al obtener codigo externo de Salud',e);
			}
			
			var objMsg = {
				clienteIce: true,
				modo: 'E',
				cdperson: _p22_cdperson,
				cdideper: _CDIDEPERsel,
				cdideext: _CDIDEEXTsel,
				codigoDanios: _codigoDanios,
				codigoSalud:_codigoSalud
			};
			
			ventanaMensaje.postMessage(objMsg, "*");
			
		}catch(e){
			debugError('Error en postMessage',e);
		}
	    
	}else{
		mensajeWarning('Error al cargar datos.');
	}
}
    
function importaPersonaWS(esSaludD, codigoCliExt){
    	
    Ext.Ajax.request(
	        {
	            url       : _UrlImportaPersonaWS
	            ,params: {
            		'params.esSalud':  esSaludD,
            		'params.codigoCliExt':  codigoCliExt
            	}
	            ,success  : function(response)
	            {
	                //_p22_formDatosAdicionales().setLoading(false);
	                var json = Ext.decode(response.responseText);
	                debug('response text:',json);
	                if(json.exito)
	                {
	                	if(esSaludD == 'S'){
	                		
	                		/**
	                		 * 
	                		 * SE PONE EL CDIDEEXT EN BLANCO POR PETICION DE ARGENIS POR PROBLEMAS DE SUCURSAL CON EL CLIENTE
	                		 * SE MANDAN A CREAR NUEVOS CLIENTES.
	                		 * 
	                		 * Codigo Original:
	                		 * _CDIDEEXTsel = json.params.codigoExterno; 
	                		 * 
	                		 */
	                		
	                		_CDIDEEXTsel = '';
	                		
	                	}else{
	                		_CDIDEPERsel = json.params.codigoExterno;
	                	}
	                	
		                _p22_cdperson = json.params.cdperson;
		                
						var form=_p22_formBusqueda();
						form.down('[name=smap1.rfc]').reset();
						form.down('[name=smap1.nombre]').reset();
	
						municipioImportarTMP = json.params.municipioImp;
						coloniaImportarTMP = json.params.coloniaImp;
	
						irModoEdicion();
	                }
	                else
	                {
	                    mensajeError("Error al Editar Cliente, vuelva a intentarlo.");
	                }
	            }
	            ,failure  : function()
	            {
	                errorComunicacion(null,'En importar persona. Consulte a soporte.');
	            }
	});
    
    }
    
    _fieldByName('CDSUCEMI',_PanelPrincipalPersonas).editable = true;
    _fieldByName('CDSUCEMI',_PanelPrincipalPersonas).forceSelection = false;
    _fieldByName('CDSUCEMI',_PanelPrincipalPersonas).setReadOnly(true);

    _fieldByName('CDEDO',_PanelPrincipalPersonas).editable = true;
    _fieldByName('CDEDO',_PanelPrincipalPersonas).forceSelection = false;
    
	_fieldByName('CDMUNICI',_PanelPrincipalPersonas).forceSelection = false;
	_fieldByName('CDCOLONI',_PanelPrincipalPersonas).forceSelection = false;
	_fieldByName('CDCOLONI',_PanelPrincipalPersonas).on({
			change: function(me, val){
    				try{
	    				if('string' == typeof val){
//	    					debug('mayus de '+val);
	    					me.setValue(val.toUpperCase());
	    				}
    				}
    				catch(e){
    					debug(e);
    				}
			},
			select: function(){
				coloniaImportarTMP = '';
			}
	});
	
	_fieldByName('CDMUNICI',_PanelPrincipalPersonas).on({
			select: function(){
				municipioImportarTMP = '';
			}
	});
	
    
	/**
	 *	Para Cliente Existente de una pantalla anterior 
	 */
    if(!Ext.isEmpty(_cargaCdPerson)){
    	
    	setTimeout(function(){
			_p22_cdperson = _cargaCdPerson;
			_CDIDEPERsel = _p22_smap1.cdideper;
			_CDIDEEXTsel = _p22_smap1.cdideext;
			_esSaludDanios = _p22_smap1.esSaludDanios;
			
			if('D' == _esSaludDanios){
				_fieldByName('DSL_CDIDEEXT',_PanelPrincipalPersonas).hide();
				_fieldByName('CDSUCEMI',_PanelPrincipalPersonas).hide();				
			}else if('S' == _esSaludDanios){
				_fieldByName('DSL_CDIDEPER',_PanelPrincipalPersonas).hide();
			}
				
			if(!Ext.isEmpty(_cargaCP)){
				_p22_comboCodPostal().setReadOnly(true);
			}
			
    		irModoEdicion();
		},1000);
    	
    }else if(_esCargaClienteNvo){
    
    /**
	 *	Para Cliente No existente pero con un Codigo postal y tipo de persona predefinido 
	 */
    	setTimeout(function(){
    		
    		Ext.ComponentQuery.query('#companiaGroupId')[Ext.ComponentQuery.query('#companiaGroupId').length-1].hide();
    		
			if(!Ext.isEmpty(_cargaCompania)){
				Ext.ComponentQuery.query('#companiaId')[Ext.ComponentQuery.query('#companiaId').length-1].setValue(_cargaCompania);
				
				if('D' == _cargaCompania){
					_fieldByName('DSL_CDIDEEXT',_PanelPrincipalPersonas).hide();
					_fieldByName('CDSUCEMI',_PanelPrincipalPersonas).hide();				
				}else if('S' == _cargaCompania){
					_fieldByName('DSL_CDIDEPER',_PanelPrincipalPersonas).hide();
				}
			}
			
			_p22_formDatosGenerales().hide();
    		_p22_formDomicilio().hide();
    		_p22_principalDatosAdicionales().hide();
    		
			var form=_p22_formBusqueda();
			form.down('[name=smap1.rfc]').setFieldLabel('Ingrese el RFC');
			form.down('[name=smap1.nombre]').hide();
			form.down('[name=smap1.rfc]').minChars = 100;
			
			if(!Ext.isEmpty(_cargaCP)){
				_p22_comboCodPostal().setReadOnly(true);
			}
			
		},1000);
	    
    }else if(!Ext.isEmpty(_cargaCompania)){
    	setTimeout(function(){
    		
    		Ext.ComponentQuery.query('#companiaGroupId')[Ext.ComponentQuery.query('#companiaGroupId').length-1].hide();
			Ext.ComponentQuery.query('#companiaId')[Ext.ComponentQuery.query('#companiaId').length-1].setValue(_cargaCompania);
			
			if('D' == _cargaCompania){
				_fieldByName('DSL_CDIDEEXT',_PanelPrincipalPersonas).hide();
				_fieldByName('CDSUCEMI',_PanelPrincipalPersonas).hide();				
			}else if('S' == _cargaCompania){
				_fieldByName('DSL_CDIDEPER',_PanelPrincipalPersonas).hide();
			}
			
			_p22_formDatosGenerales().hide();
    		_p22_formDomicilio().hide();
    		_p22_principalDatosAdicionales().hide();
			
		},1000);
    }
    
    if(!Ext.isEmpty(_cargaFenacMin)){
    	_fieldByName('FENACIMI',_PanelPrincipalPersonas).setMinValue(_cargaFenacMin);
    }

    if(!Ext.isEmpty(_cargaFenacMax)){
    	_fieldByName('FENACIMI',_PanelPrincipalPersonas).setMaxValue(_cargaFenacMax);
    }
    
});

////// funciones //////

function _p22_formBusqueda()
{
    debug('>_p22_formBusqueda<');
    debug(Ext.ComponentQuery.query('#_p22_formBusqueda'));
	return Ext.ComponentQuery.query('#_p22_formBusqueda')[Ext.ComponentQuery.query('#_p22_formBusqueda').length-1];
}

function _p22_heredarColonia(callbackload)
{
    debug('>_p22_heredarColonia');
    var comboColonias  = _p22_comboColonias();
    var comboCodPostal = _p22_comboCodPostal();
    var codigoPostal   = comboCodPostal.getValue();
    debug('comboColonias:',comboColonias,'comboCodPostal:',comboCodPostal);
    debug('codigoPostal:',codigoPostal);
    comboColonias.getStore().load(
    {
        params :
        {
            'params.cp' : codigoPostal
        }
        ,callback : function()
        {
            var hay=false;
            comboColonias.getStore().each(function(record)
            {
                if(comboColonias.getValue()==record.get('key'))
                {
                    hay=true;
                }
            });
            if(!hay)
            {
                comboColonias.setValue('');
            }
            
            try{
            	callbackload();	
            }catch(e){
            	debug("Excepcion al ejecutar callback");
            }
            
        }
    });
    debug('<_p22_heredarColonia');
}

function _p22_nacionalidadChange(combo,value)
{
    debug('>_p22_nacionalidadChange',value);
    if(value!='001')//extranjero
    {
        _fieldByName('RESIDENTE',_PanelPrincipalPersonas).show();
        _fieldByName('RESIDENTE',_PanelPrincipalPersonas).allowBlank = false;
        _fieldByName('RESIDENTE',_PanelPrincipalPersonas).validate();
    }
    else//nacional
    {
        _fieldByName('RESIDENTE',_PanelPrincipalPersonas).hide();
        _fieldByName('RESIDENTE',_PanelPrincipalPersonas).allowBlank = true;
        _fieldByName('RESIDENTE',_PanelPrincipalPersonas).validate();
    }
    debug('<_p22_nacionalidadChange');
}

function _p22_nacionalidadChange2(combo,value)
{
    if(value!='001')//extranjero
    {
        _fieldByName('RESIDENTE2',_panelTipoPer).show();
        _fieldByName('RESIDENTE2',_panelTipoPer).allowBlank = false;
        _fieldByName('RESIDENTE2',_panelTipoPer).validate();
    }
    else//nacional
    {
        _fieldByName('RESIDENTE2',_panelTipoPer).hide();
        _fieldByName('RESIDENTE2',_panelTipoPer).allowBlank = true;
        _fieldByName('RESIDENTE2',_panelTipoPer).validate();
    }
}

function _p22_tipoPersonaChange(combo,value)
{
    debug('>_p22_tipoPersonaChange',value);
    if(value!='F')
    {
        _p22_fieldSegundoNombre().hide();
        _p22_fieldApat().hide();
        _p22_fieldAmat().hide();
        _p22_fieldSexo().hide();
        _fieldByName('CDESTCIV',_PanelPrincipalPersonas).hide();
        
        _fieldByName('DSNOMBRE',_PanelPrincipalPersonas).setFieldLabel('Raz&oacute;n social*');
        _fieldByName('FENACIMI',_PanelPrincipalPersonas).setFieldLabel('Fecha de constituci&oacute;n*');
        
        if(value == 'S'){
        	_fieldByName('FENACIMI',_PanelPrincipalPersonas).allowBlank = true;
        	_fieldByName('FENACIMI',_PanelPrincipalPersonas).setValue('');
        	_fieldByName('FENACIMI',_PanelPrincipalPersonas).hide();
        }else {
        	_fieldByName('FENACIMI',_PanelPrincipalPersonas).allowBlank = false;
        	_fieldByName('FENACIMI',_PanelPrincipalPersonas).setValue('');
        	_fieldByName('FENACIMI',_PanelPrincipalPersonas).show();
        }
    }
    else
    {
        _p22_fieldSegundoNombre().show();
        _p22_fieldApat().show();
        _p22_fieldAmat().show();
        _p22_fieldSexo().show();
        _fieldByName('CDESTCIV',_PanelPrincipalPersonas).show();
        
        _fieldByName('DSNOMBRE',_PanelPrincipalPersonas).setFieldLabel('Nombre*');
        _fieldByName('FENACIMI',_PanelPrincipalPersonas).setFieldLabel('Fecha de nacimiento*');
        
        _fieldByName('FENACIMI',_PanelPrincipalPersonas).allowBlank = false;
    	_fieldByName('FENACIMI',_PanelPrincipalPersonas).setValue('');
    	_fieldByName('FENACIMI',_PanelPrincipalPersonas).show();
    }
    debug('<_p22_tipoPersonaChange');
}

function _p22_comboColonias()
{
    debug('>_p22_comboColonias<');
    return _fieldByName('CDCOLONI',_PanelPrincipalPersonas);
}

function _p22_comboCodPostal()
{
    debug('>_p22_comboCodPostal<');
    return _fieldByName('CODPOSTAL',_PanelPrincipalPersonas);
}

function _p22_fieldSegundoNombre()
{
    debug('>_p22_fieldSegundoNombre<');
    return _fieldByName('DSNOMBRE1',_PanelPrincipalPersonas);
}

function _p22_fieldApat()
{
    debug('>_p22_fieldApat<');
    return _PanelPrincipalPersonas.down('[name=DSAPELLIDO]');
}

function _p22_fieldAmat()
{
    debug('>_p22_fieldAmat<');
    return _fieldByName('DSAPELLIDO1',_PanelPrincipalPersonas);
}

function _p22_fieldSexo()
{
    debug('>_p22_fieldSexo<');
    return _fieldByName('OTSEXO',_PanelPrincipalPersonas);
}

function _p22_fieldTipoPersona()
{
    debug('>_p22_fieldTipoPersona<');
    return _fieldByName('OTFISJUR',_PanelPrincipalPersonas);
}

function _p22_fieldCumuloPrima(){
    return _fieldByName('PTCUMUPR',_PanelPrincipalPersonas);
}

function _p22_fieldCumuloPrimaModoAgregar(){
    return _fieldByName('PTCUMUPRAGR',_panelTipoPer);
}

function _p22_fielCdNacion(){
    return _fieldByName('CDNACION',_PanelPrincipalPersonas);
}

function _p22_fieldResidente(){
    return _fieldByName('RESIDENTE',_PanelPrincipalPersonas);
}

function _p22_formDatosGenerales()
{
    return Ext.ComponentQuery.query('#_p22_formDatosGenerales')[Ext.ComponentQuery.query('#_p22_formDatosGenerales').length-1];
}

/* PARA EL LOADER */
function _p22_loadRecordCdperson(callbackload,autosave)
{
    debug('>_p22_loadRecordCdperson');
    _p22_PanelPrincipal().setLoading(true);
    Ext.Ajax.request(
    {
        url     : _p22_urlCargarPersonaCdperson
        ,params :
        {
            'smap1.cdperson' : _p22_cdperson
        }
        ,success : function(response)
        {
            _p22_PanelPrincipal().setLoading(false);
            var json=Ext.decode(response.responseText);
            if(json.exito)
            {
            	var record = new _p22_modeloGrid(json.smap2);
            	_p22_PanelPrincipal().setLoading(true);
            	
            	/**
            	 * Para cargar el cdideper y cdideext segun sea el caso.
            	 */
            	if(!Ext.isEmpty(record.get('CDIDEPER'))){
            		_CDIDEPERsel = record.get('CDIDEPER');
            	}
            	if(!Ext.isEmpty(record.get('CDIDEEXT'))){
            		_CDIDEEXTsel = record.get('CDIDEEXT');
            	}
            	
            	var valTel  = _fieldByName('TELEFONO',_PanelPrincipalPersonas).getValue(); 
            	var valMail = _fieldByName('EMAIL',_PanelPrincipalPersonas).getValue(); 
            	
			    _p22_formDatosGenerales().loadRecord(record);
			    
			    if(!Ext.isEmpty(valTel) && Ext.isEmpty(_fieldByName('TELEFONO',_PanelPrincipalPersonas).getValue())){
			    	_fieldByName('TELEFONO',_PanelPrincipalPersonas).setValue(valTel);
			    }
			    if(!Ext.isEmpty(valMail) && Ext.isEmpty(_fieldByName('EMAIL',_PanelPrincipalPersonas).getValue())){
			    	_fieldByName('EMAIL',_PanelPrincipalPersonas).setValue(valMail);
			    }
			    
			    Ext.Ajax.request(
			    {
			        url      : _p22_urlObtenerDomicilio
			        ,params  :
			        {
			            'smap1.cdperson' : record.get('CDPERSON'),
			            'smap1.AUTOSAVE' : autosave?'S':'N'
			        }
			        ,success : function(response)
			        {
			            _p22_PanelPrincipal().setLoading(false);
			            var json=Ext.decode(response.responseText);
			            debug('json response:',json);
			            if(json.exito)
			            {
			            	
			            	/**
			            	 * Conservar orden de Variables 
			            	 */
			            	var valorMun = _fieldByName('CDMUNICI',_PanelPrincipalPersonas).getValue();
			                _p22_formDomicilio().loadRecord(new _p22_modeloDomicilio(json.smap1));
			                
							
			                if(_esCargaClienteNvo && !Ext.isEmpty(_cargaCP)){
								_p22_comboCodPostal().setValue(_cargaCP);
							}
			                
			                var valorCol = _fieldByName('CDCOLONI',_PanelPrincipalPersonas).getValue();
			                
			                heredarPanel(_p22_formDomicilio());
			                
                    		_p22_heredarColonia(function(){
                    				_fieldByName('CDCOLONI',_PanelPrincipalPersonas).setValue(valorCol);
                    				
                    				debug('valor de codigo colonia: ', valorCol);
                    				debug('valor de coloniaImportarTMP: ', coloniaImportarTMP);
                    				debug('valor de municipioImportarTMP: ', municipioImportarTMP);
                    				
                    				if(Ext.isEmpty(valorCol) && !Ext.isEmpty(coloniaImportarTMP)){
                    					_fieldByName('CDCOLONI',_PanelPrincipalPersonas).setValue(coloniaImportarTMP);
                    				}
                    				
                    				if(Ext.isEmpty(valorMun) && !Ext.isEmpty(municipioImportarTMP)){
//                    					_fieldByName('CDMUNICI',_PanelPrincipalPersonas).forceSelection = false;
                    					_fieldByName('CDMUNICI',_PanelPrincipalPersonas).setValue(municipioImportarTMP);
                    					
//                    					setTimeout(function(){
//											_fieldByName('CDMUNICI',_PanelPrincipalPersonas).forceSelection = true;
//										},500);
                    				}
                    			}
                    			
                    			
                    		);
                    		_fieldByName('CDMUNICI',_PanelPrincipalPersonas).forceSelection = true;
	                        _fieldByName('CDEDO',_PanelPrincipalPersonas).forceSelection = true;
	                        _fieldByName('CDEDO',_PanelPrincipalPersonas).validate();
	                        _fieldByName('CDMUNICI',_PanelPrincipalPersonas).validate();
			                
			                try{
			                	callbackload();
			                }catch(e){
			                	debug("Excepcion al ejecutar callback");
			                }
			                	
			            }
			            else
			            {
			            	if(autosave){
			            		try{
				                	callbackload();
				                }catch(e){
				                	debug("Excepcion al ejecutar callback EN AUTOSAVE");
				                }
			            	}else{
			            		mensajeError(json.respuesta);
			            	}
			            }
			        }
			        ,failure : function()
			        {
			            _p22_PanelPrincipal().setLoading(false);
			            errorComunicacion(null,'En cargar domicilio');
			        }
			    });
            }
            else
            {
                mensajeError(json.respuesta);
            }
        }
        ,failure : function()
        {
            _p22_PanelPrincipal().setLoading(false);
            errorComunicacion(null,'En cargar datos generales');
        }
    });
    debug('<_p22_loadRecordCdperson');
}
/* PARA EL LOADER */


function _p22_PanelPrincipal()
{
    debug('>_p22_PanelPrincipal<');
    return Ext.ComponentQuery.query('#_p22_PanelPrincipal')[Ext.ComponentQuery.query('#_p22_PanelPrincipal').length-1];
}

function _p22_guardarClic(callback, autosave)
{
    debug('>_p22_guardarClic');
    var valido = true;
    
    if(valido)
    {
        valido = autosave || _p22_formDatosGenerales().isValid();
        if(!valido)
        {
            mensajeWarning('Favor de verificar los datos generales');
        }
    }
    
//    if(valido)
//    {
//        valido = validarRFC(_p22_fieldRFC().getValue(),_p22_fieldTipoPersona().getValue());
//        if(!valido)
//        {
//        }
//    }
    
    if(valido&&_p22_fieldTipoPersona().getValue()=='F')
    {
        valido = autosave || (!Ext.isEmpty(_p22_fieldApat().getValue())
                 &&!Ext.isEmpty(_p22_fieldAmat().getValue())
                 &&!Ext.isEmpty(_p22_fieldSexo().getValue()));
        if(!valido)
        {
            mensajeWarning('Favor de introducir apellidos y sexo para persona f&iacute;sica');
            ponerActivo(1);
        }
    }
    
    if(valido&&Ext.isEmpty(_p22_fieldConsecutivo().getValue()))
    {
        _p22_fieldConsecutivo().setValue(1);
    }

    if(valido)
    {
    	_fieldByName('CDMUNICI',_PanelPrincipalPersonas).forceSelection = true;
        _fieldByName('CDEDO',_PanelPrincipalPersonas).forceSelection = true;
        
    	var edoMunValido = (_fieldByName('CDEDO',_PanelPrincipalPersonas).getStore().getCount()>0) && (_fieldByName('CDMUNICI',_PanelPrincipalPersonas).getStore().getCount()>0) && _fieldByName('CDEDO',_PanelPrincipalPersonas).isValid() && _fieldByName('CDMUNICI',_PanelPrincipalPersonas).isValid();
    	
        valido = autosave || edoMunValido;
        
        if(!valido)
        {
            mensajeWarning('Favor de verificar el estado y municipio.');
        }
    }
    
    if(valido)
    {
        valido = autosave || _p22_formDomicilio().isValid();
        if(!valido)
        {
            mensajeWarning('Favor de verificar los datos del domicilio.');
        }
    }
    
    if(valido)
    {
        _p22_PanelPrincipal().setLoading(true);
        
    	if(!Ext.isEmpty(municipioImportarTMP)){
				_fieldByName('CDMUNICI',_PanelPrincipalPersonas).setValue('');
		}
		
		/**
		 * PARA CARGAR LA SUCURSAL ANTES DE GUARDAR
		 * @type String
		 */
		if(!autosave && Ext.isEmpty(_fieldByName('CDSUCEMI',_PanelPrincipalPersonas).getValue()) && !Ext.isEmpty(_cargaSucursalEmi)){
			_fieldByName('CDSUCEMI',_PanelPrincipalPersonas).setValue(_cargaSucursalEmi);
		}
        
        Ext.Ajax.request(
        {
            url       : _p22_urlGuardar
            ,jsonData :
            {
                smap1  : _p22_formDatosGenerales().getValues()
                ,smap2 : _p22_formDomicilio().getValues()
                ,smap3: {
                	'AUTOSAVE': autosave?'S':'N'
                }
            }
            ,success : function(response)
            {
                _p22_PanelPrincipal().setLoading(false);
                var json = Ext.decode(response.responseText);
                debug('json response:',json);
                if(json.exito)
                {
                    _p22_fieldCdperson().setValue(json.smap1.CDPERSON);
                    _p22_fieldCdperson().validado = _p22_fieldCdperson().validado||Ext.isEmpty(autosave)||autosave==false;
                    _p22_cdperson = json.smap1.CDPERSON;
                    
                    json.smap1.CDPOSTAL = _p22_comboCodPostal().getValue();
                    json.smap1.CDEDO    = _fieldByName('CDEDO',_PanelPrincipalPersonas).getValue();
                    json.smap1.CDMUNICI = _fieldByName('CDMUNICI',_PanelPrincipalPersonas).getValue();
                    
                    
                    if(!Ext.isEmpty(municipioImportarTMP)){
//						_fieldByName('CDMUNICI',_PanelPrincipalPersonas).forceSelection = false;
						_fieldByName('CDMUNICI',_PanelPrincipalPersonas).setValue(municipioImportarTMP);
                    					
//    					setTimeout(function(){
//							_fieldByName('CDMUNICI',_PanelPrincipalPersonas).forceSelection = true;
//						},500);
					}
		

					try{
                        callback();
                    }catch(e){
                    	debug("Excepcion al ejecutar callback");
                    	mensajeCorrecto('Datos guardados',json.respuesta);
                    }

                    try{
                    	if(_p22_cdperson!=false && !autosave && _p22_parentCallback){
                        	_p22_parentCallback(json);
                    	}
                    }catch(e){
                    	debug('Error',e)
                    }

                    try{
                    	if(_p22_cdperson!=false && !autosave && _contrantantePrincipal && _callbackContPrincipal){
                        	_callbackContPrincipal(json);
                    	}
                    }catch(e){
                    	debug('Error',e)
                    }
                    
                }
                else
                {
                    mensajeError(json.respuesta);
                }
            }
            ,failure : function()
            {
                _p22_PanelPrincipal().setLoading(false);
                errorComunicacion(null,'En almacenar datos generales');
            }
        });
    }
    
    debug('<_p22_guardarClic');
}

function _p22_formDomicilio()
{
    debug('>_p22_formDomicilio<');
    return Ext.ComponentQuery.query('#_p22_formDomicilio')[Ext.ComponentQuery.query('#_p22_formDomicilio').length-1];
}

function _p22_principalDatosAdicionales()
{
    debug('>_p22_principalDatosAdicionales<');
    return Ext.ComponentQuery.query('#_p22_principalDatosAdicionales')[Ext.ComponentQuery.query('#_p22_principalDatosAdicionales').length-1];
}

function _p22_fieldRFC()
{
    debug('>_p22_fieldRFC<');
    return _fieldByName('CDRFC',_PanelPrincipalPersonas);
}

function _p22_fieldCdperson()
{
    debug('>_p22_fieldCdperson<');
    return _fieldByName('CDPERSON',_PanelPrincipalPersonas);
}

function _p22_fieldConsecutivo()
{
    debug('>_p22_fieldConsecutivo<');
    return _fieldByName('NMORDDOM',_PanelPrincipalPersonas);
}

function recargaTelefonoEmail(){
	//rellenar al cargar telefono y correo electronico
	
	try{
		debug('Tel en Datos Generales: ' , _fieldByName('TELEFONO',_PanelPrincipalPersonas).getValue());
		debug('Tel en Datos Adicional: ' , _fieldByName('parametros.pv_otvalor38',_PanelPrincipalPersonas).getValue());
				
		if(Ext.isEmpty(_fieldByName('TELEFONO',_PanelPrincipalPersonas).getValue()) && !Ext.isEmpty(_fieldByName('parametros.pv_otvalor38',_PanelPrincipalPersonas).getValue())){
			_fieldByName('TELEFONO',_PanelPrincipalPersonas).setValue(_fieldByName('parametros.pv_otvalor38',_PanelPrincipalPersonas).getValue());
		}
		if(!Ext.isEmpty(_fieldByName('TELEFONO',_PanelPrincipalPersonas).getValue()) && ( Ext.isEmpty(_fieldByName('parametros.pv_otvalor38',_PanelPrincipalPersonas).getValue()) || _fieldByName('parametros.pv_otvalor38',_PanelPrincipalPersonas).getValue() != _fieldByName('TELEFONO',_PanelPrincipalPersonas).getValue() )){
			_fieldByName('parametros.pv_otvalor38',_PanelPrincipalPersonas).setValue(_fieldByName('TELEFONO',_PanelPrincipalPersonas).getValue());
		}
	}catch(e){
		debug('Aun no existe el campo telefono. ', e);
	}
	
	try{
		debug('Email en Datos Generales: ' , _fieldByName('EMAIL',_PanelPrincipalPersonas).getValue());
		debug('Email en Datos Adicional: ' , _fieldByName('parametros.pv_otvalor39',_PanelPrincipalPersonas).getValue());
				
		if(Ext.isEmpty(_fieldByName('EMAIL',_PanelPrincipalPersonas).getValue()) && !Ext.isEmpty(_fieldByName('parametros.pv_otvalor39',_PanelPrincipalPersonas).getValue())){
			_fieldByName('EMAIL',_PanelPrincipalPersonas).setValue(_fieldByName('parametros.pv_otvalor39',_PanelPrincipalPersonas).getValue());
		}
		if(!Ext.isEmpty(_fieldByName('EMAIL',_PanelPrincipalPersonas).getValue()) && ( Ext.isEmpty(_fieldByName('parametros.pv_otvalor39',_PanelPrincipalPersonas).getValue()) || _fieldByName('parametros.pv_otvalor39',_PanelPrincipalPersonas).getValue() != _fieldByName('EMAIL',_PanelPrincipalPersonas).getValue() )){
			_fieldByName('parametros.pv_otvalor39',_PanelPrincipalPersonas).setValue(_fieldByName('EMAIL',_PanelPrincipalPersonas).getValue());
		}
		
	}catch(e){
		debug('Aun no existe el campo email. ', e);
	}
}

function _p22_datosAdicionalesClic()
{
    debug('>_p22_datosAdicionalesClic');
    
    windowAccionistas = undefined;
    
    /** PARA ACTUALIZAR EL NUEVO ESTATUS GENERAL DE LA PERSONA **/
    Ext.Ajax.request(
	        {
	            url       : _UrlActualizaStatusPersona
	            ,params: {
            		'params.pv_cdperson_i':  _p22_fieldCdperson().getValue(),
            		'params.pv_cdrol_i':  '1'
            	}
	            ,success  : function(response)
	            {
	                //_p22_formDatosAdicionales().setLoading(false);
	                var json = Ext.decode(response.responseText);
	                debug('response text:',json);
	                if(json.exito)
	                {
	                    debug('Actualizando estatus de Persona: ');
	                    _fieldByName('STATUS',_PanelPrincipalPersonas).setValue(json.respuesta);
	                    
	                }
	                else
	                {
	                    mensajeError(json.respuesta);
	                }
	            }
	            ,failure  : function()
	            {
	                _p22_formDatosAdicionales().setLoading(false);
	                errorComunicacion(null,'En actualizar estatus persona');
	            }
	});
    
    _p22_PanelPrincipal().setLoading(true);
    
    _p22_principalDatosAdicionales().removeAll();
    
    Ext.Ajax.request(
    {
        url      : _p22_urlTatriperTvaloper
        ,params  : { 'smap1.cdperson' : _p22_fieldCdperson().getValue(), 'smap1.cdrol' : '1' }
        ,success : function(response)
        {
            _p22_PanelPrincipal().setLoading(false);
            var json = Ext.decode(response.responseText);
            debug('json response:',json);
            if(json.exito)
            {
                Ext.define('_p22_modeloTatriper',
                {
                    extend  : 'Ext.data.Model'
                    ,fields : Ext.decode(json.smap1.fieldsTatriper.substring("fields:".length))
                });
                
                
                _p22_principalDatosAdicionales().add({
            	    	layout: 'column',
            	    	border: false,
            	    	html:'<span style="font-size:14px; font-weight: bold;">Para que el estatus de la persona sea completo se requiere que los campos con el s&iacute;mbolo: <img src="${ctx}/resources/fam3icons/icons/transmit_error.png" alt="">, sean capturados.</span><br/><br/>'
            	});
                _p22_principalDatosAdicionales().add(
                Ext.create('Ext.form.Panel',
                        {
                            border    : 0
                            ,itemId   : '_p22_formDatosAdicionales'
//                            ,width    : 570
                            ,defaults : { style : 'margin:5px;' }
                            ,layout   :
                            {
                                type     : 'table'
                                ,columns : 3
                            }
                            ,items    : Ext.decode(json.smap1.itemsTatriper.substring("items:".length))
                        })
                );
                
                
               /* Ext.create('Ext.form.Panel',
                {
                    title   : 'Datos adicionales'
                    ,itemId : '_p22_principalDatosAdicionales'
                    ,width  : 650
                    ,height : 600
                    ,autoScroll : true
                    ,modal  : true
                    ,items  :
                    [{
            	    	layout: 'column',
            	    	border: false,
            	    	html:'<span style="font-size:14px; font-weight: bold;">Para que el estatus de la persona sea completo se requiere que los campos con el s&iacute;mbolo: <img src="${ctx}/resources/fam3icons/icons/transmit_error.png" alt="">, sean capturados.</span><br/><br/>'
            	    },
                        Ext.create('Ext.form.Panel',
                        {
                            border    : 0
                            ,itemId   : '_p22_formDatosAdicionales'
                            ,width    : 570
                            ,defaults : { style : 'margin:5px;' }
                            ,layout   :
                            {
                                type     : 'table'
                                ,columns : 3
                            }
                            ,items    : Ext.decode(json.smap1.itemsTatriper.substring("items:".length))
                        })
                    ]
                    ,bbar    :
                    [
                        '->'
                        ,{
                            text     : 'Guardar'
                            ,icon    : '${ctx}/resources/fam3icons/icons/disk.png'
                            ,handler : _p22_guardarDatosAdicionalesClic
                        }
                        ,{
                        	text: 'Cerrar'
                        	,icon: '${ctx}/resources/fam3icons/icons/cancel.png'
                        	,handler: function(){
                        		_p22_principalDatosAdicionales().close();
                        	}
                        }
                        ,'->'
                    ]
                }).show(); */
                
//                fieldMail=_fieldByLabel('Correo electrónico', null, true);
//                if(fieldMail)
//                {
//                    fieldMail.regex = /^[_A-Z0-9-]+(\.[_A-Z0-9-]+)*@[A-Z0-9-]+(\.[A-Z0-9-]+)*(\.[A-Z]{2,4})$/;
//                }
                
				fieldEstCorp = _fieldByLabel('Estructura corporativa', null, true);
				var fieldEstCorpAux = Ext.clone(fieldEstCorp);
				
				if(fieldEstCorp){
					var panelDatAdic = fieldEstCorp.up();
					var indEstCorp = panelDatAdic.items.indexOf(fieldEstCorp);
					
					/*debug("fieldEstCorp" , fieldEstCorp);
					
					if(( (indEstCorp) %2) != 0){
						panelDatAdic.insert(indEstCorp,{
	            	    	layout: 'column',
	            	    	border: false,
	            	    	html:'<br/>'
	            	    });
						indEstCorp = indEstCorp + 1;
					}*/
					
					_p22_formDatosAdicionales().items.remove(fieldEstCorp, true);
					fieldEstCorp = fieldEstCorpAux;
					
					panelDatAdic.insert(indEstCorp,{
						xtype      : 'panel',
						//padding    :  '2px 2px 2px 2px',
						defaults : { style : 'margin:3px' },
						border     :  1,
						items      : [fieldEstCorp,{
                       	 xtype    : 'button'
                         	,text     : 'Ver/Editar Accionistas'
                             ,icon     : '${ctx}/resources/fam3icons/icons/award_star_add.png'
                             ,tooltip  : 'Ver/Editar Accionostas'
                             ,handler  : function(button)
                             {
                                verEditarAccionistas(_p22_fieldCdperson().getValue(), fieldEstCorp.getName().substring(fieldEstCorp.getName().length-2, fieldEstCorp.getName().length), fieldEstCorp.getValue());
                             }
 					}]
					});
					
					fieldEstCorp.addListener('beforeselect',function (combo){
						if(windowAccionistas){
							
							var valorAnterior = combo.getValue();
							
							Ext.Msg.show({
		    		            title: 'Confirmar acci&oacute;n',
		    		            msg  : 'Si cambia la Estructura Coorporativa perder&aacute; la lista de Accionistas guardada. &iquest;Desea continuar?',
		    		            buttons: Ext.Msg.YESNO,
		    		            fn: function(buttonId, text, opt) {
		    		            	if(buttonId == 'yes') {
		    		            		
		    		            		windowAccionistas = undefined;
		    		            		
		    		            		Ext.Ajax.request(
		    		                	        {
		    		                	            url       : _UrlEliminaAccionistas
		    		                	            ,params: {
	    		                	            		'params.pv_cdperson_i':   _p22_fieldCdperson().getValue(),
	    		                    	            	'params.pv_cdatribu_i':  fieldEstCorp.getName().substring(fieldEstCorp.getName().length-2, fieldEstCorp.getName().length)
	    		                	            	}
		    		                	            ,success  : function(response)
		    		                	            {
		    		                	                _p22_formDatosAdicionales().setLoading(false);
		    		                	                var json = Ext.decode(response.responseText);
		    		                	                debug('response text:',json);
		    		                	                if(json.exito)
		    		                	                {
		    		                	                    mensajeCorrecto('Aviso','Datos de Accionistas eliminados correctamente.');
		    		                	                }
		    		                	                else
		    		                	                {
		    		                	                    mensajeError(json.respuesta);
		    		                	                }
		    		                	            }
		    		                	            ,failure  : function()
		    		                	            {
		    		                	                _p22_formDatosAdicionales().setLoading(false);
		    		                	                errorComunicacion(null,'En guardar datos de accionistas');
		    		                	            }
		    		                	});
		    		            	
		    		            		
		    		            	}else{
		    		            		combo.setValue(valorAnterior);
		    		            	}
		            			},
		    		            icon: Ext.Msg.QUESTION
		        			});	
						}else {
							return true;
						}
					});
				}
				
				
				try{
					_fieldByName('TELEFONO',_PanelPrincipalPersonas).allowBlank = _fieldByName('parametros.pv_otvalor38',_PanelPrincipalPersonas).allowBlank;	
				}catch(e){
					debug('No se encontro el elemento de telefono para fijar obligatoriedad', e);
				}
				
				try{
					_fieldByName('EMAIL',_PanelPrincipalPersonas).allowBlank = _fieldByName('parametros.pv_otvalor39',_PanelPrincipalPersonas).allowBlank;	
				}catch(e){
					debug('No se encontro el elemento de email para fijar obligatoriedad', e);
				}
				
				
				_p22_formDatosAdicionales().items.each(function(item,index,len){
					
                	if(!item.allowBlank){
                		item.allowBlank = true;
                		if(item.getFieldLabel){
                			item.inicialField = item.getFieldLabel();
                			item.setFieldLabel('<span>'+ item.getFieldLabel() +'<img src="${ctx}/resources/fam3icons/icons/transmit_error.png" alt=""></span>');
                		}
                	}else{
                		if(item.getFieldLabel){
                			item.inicialField = item.getFieldLabel();
                		}
                	}
                	
                });
				
                _p22_formDatosAdicionales().loadRecord(new _p22_modeloTatriper(json.smap2));
                
                var itemsDocumento=Ext.ComponentQuery.query('[tieneDocu]');
                debug('itemsDocumento:',itemsDocumento);
                
                /*debug("agregar espacio docs: ", _p22_formDatosAdicionales().items.getCount());
                if(( (_p22_formDatosAdicionales().items.getCount()) %2 ) == 0){
                	_p22_formDatosAdicionales().add({
            	    	layout: 'column',
            	    	border: false,
            	    	html:'<br/>'
            	     });
                }*/
                
                for(var i=0;i<itemsDocumento.length;i++)
                {
                    itemDocumento=itemsDocumento[i];
                    
                    
                    if('DOC' == itemDocumento.tieneDocu){
//                    	debug('Icono itemDoc: ' , itemDocumento.icon);
	                    itemDocumento.up().add(
	                    		{
	        						xtype      : 'panel',
	        						//padding    :  '2px 2px 2px 2px',
	        						defaults : { style : 'margin:3px' },
	        						border     :  1,
	        						items      : [
	        						         Ext.clone(itemDocumento),
	        						        {
	        	                       		 xtype    : 'panel'
	        		                        ,layout  : 'hbox'
	        		                        ,border  : 0
	        		                        ,items   :
	        		                        [
	        		                            {
	        		                                xtype       : 'displayfield'
	        		                                ,labelWidth : 180
	        		                                ,fieldLabel : 'Documento digital' + (itemDocumento.allowBlank==false ? '<span style="font-size:10px;">(obligatorio)</span>' : '')
	        		                            }
	        		                            ,{
	        		                                xtype     : 'button'
	        		                                ,itemId   : itemDocumento.name
	        		                                ,icon     : '${ctx}/resources/fam3icons/icons/arrow_up.png'
	        		                                ,tooltip  : 'Subir nuevo'
	        		                                ,codidocu : itemDocumento.codidocu
	        		                                ,descrip  : itemDocumento.inicialField
	        		                                ,handler  : function(button)
	        		                                {
	        		                                	_DocASubir = button.itemId;
	        		                                    _p22_subirArchivo(_p22_fieldCdperson().getValue(),button.codidocu,button.descrip);
	        		                                }
	        		                            },{
	        		                                xtype     : 'button'
	        		                                ,icon     : '${ctx}/resources/fam3icons/icons/eye.png'
	        		                                ,tooltip  : 'Descargar'
	        		                                ,codidocu : itemDocumento.codidocu
	        		                                ,descrip  : itemDocumento.inicialField
	        		                                ,handler  : function(button)
	        		                                {
	        		                                    _p22_cargarArchivo(_p22_fieldCdperson().getValue(),button.codidocu,button.descrip);
	        		                                }
	        		                            },{
	        		                                xtype: 'imagecomponent'
	        		                                ,docCargado: itemDocumento.name+'ImgDocId'
	        		                                ,src : "CARGADO" == itemDocumento.icon ? '${ctx}/resources/fam3icons/icons/accept.png' : '${ctx}/resources/fam3icons/icons/cancel.png'
	        		                                ,height: 24
	        		                                ,width:  24
	        		                            }
	        		                        ]
	        		                    },{
											xtype: 'tbspacer',        	    	
											height: 15                	
						        	    }]
	        					}
	                    	);
	                }/*else{
	                	itemDocumento.up().add({
                	    	layout: 'column',
                	    	border: false,
                	    	html:'<br/>'
                	     });
	                }*/
                    //itemDocumento.destroy();
                    //itemDocumento.allowBlank = true;
                }
                
                _p22_formDatosAdicionales().add({
					xtype: 'tbspacer',        	    	
					height: 50                	
        	     });
                _p22_formDatosAdicionales().add({
					xtype: 'tbspacer',        	    	
					height: 50                	
        	     });
                _p22_formDatosAdicionales().add({
					xtype: 'tbspacer',        	    	
					height: 50                	
        	     });
        	     
        	     
        	     
				//rfc solo lextura        	     
        	     try{
        	     	_fieldByName('parametros.pv_otvalor13',_PanelPrincipalPersonas, true).setValue(_p22_fieldRFC().getValue());
        	     	_fieldByName('parametros.pv_otvalor13',_PanelPrincipalPersonas, true).setReadOnly(true);
        	     }catch(e){
        	     	debug('Error en adaptacion de  campo Cliente VIP',e);
        	     }
        	     
				//cliente VIP solo lextura        	     
        	     try{
        	     	_fieldByName('parametros.pv_otvalor37',_PanelPrincipalPersonas, true).setReadOnly(true);
        	     }catch(e){
        	     	debug('Error en adaptacion de  campo Cliente VIP',e);
        	     }
        	     
				
				try{
					_fieldByName('EMAIL',_PanelPrincipalPersonas).vtype = 'email';
					_fieldByName('EMAIL',_PanelPrincipalPersonas).on({
							change: function(me, val){
				    				_fieldByName('parametros.pv_otvalor39',_PanelPrincipalPersonas).setValue(val);
							}
					});
					
					_fieldByName('parametros.pv_otvalor39',_PanelPrincipalPersonas).hide();
					
				}catch(e){
					debug('Error en adaptacion de  campo email',e);
				}
				
				try{
					
					_fieldByName('TELEFONO',_PanelPrincipalPersonas).on({
						change: function(me, val){
			    				_fieldByName('parametros.pv_otvalor38',_PanelPrincipalPersonas).setValue(val);
						}
					});
					_fieldByName('parametros.pv_otvalor38',_PanelPrincipalPersonas).hide();	
				}catch(e){
					debug('Error en adaptacion de  campo telefono',e);
				}
				
				recargaTelefonoEmail();
				
				
				if(!_activaCveFamiliar){
			    	
					try{
						//Clave familiar
			    		_fieldByName('parametros.pv_otvalor49', _PanelPrincipalPersonas, true).hide();	
					}catch(e){
						debug('Sin campo de Clave Familiar');
					}

					try{
						//Numero socio
			    	_fieldByName('parametros.pv_otvalor50', _PanelPrincipalPersonas, true).hide();	
					}catch(e){
						debug('Sin campo de Numero de Socio');
					}
			    }
				
            }
            else
            {
                mensajeError(json.respuesta);
            }
        }
        ,failure : function()
        {
            _p22_PanelPrincipal().setLoading(false);
            errorComunicacion(null,'En carga conf. datos adicionales');
        }
    });
    debug('<_p22_datosAdicionalesClic');
}

function _p22_guardarDatosAdicionalesClic()
{
    debug('>_p22_guardarDatosAdicionalesClic');
    
    var saveList = [];
    var updateList = [];
    var deleteList = [];
    
    if(windowAccionistas){
    	accionistasStore.getRemovedRecords().forEach(function(record,index,arr){
        	deleteList.push(record.data);
    	});
        accionistasStore.getNewRecords().forEach(function(record,index,arr){
    		if(record.dirty) saveList.push(record.data);
    	});
        accionistasStore.getUpdatedRecords().forEach(function(record,index,arr){
    		updateList.push(record.data);
    	});
    }
	
    debug('Accionistas Removed: ' , deleteList);
    debug('Accionistas Added: '   , saveList);
    debug('Accionistas Updated: ' , updateList);
    
    var valido=true;
    
    if(valido)
    {
        valido = _p22_formDatosAdicionales().isValid();
        if(!valido)
        {
            mensajeWarning('Favor de verificar los datos');
        }
    }
    
    if(valido)
    {
        _p22_formDatosAdicionales().setLoading(true);
        var jsonData = _p22_formDatosAdicionales().getValues();
        jsonData['cdperson'] = _p22_fieldCdperson().getValue();
        jsonData['cdrol'] = '1';
        jsonData['esSalud'] = _esSaludDanios;
        jsonData['codigoExterno'] = (_esSaludDanios=='S')?_CDIDEEXTsel:_CDIDEPERsel;
        jsonData['codigoExterno2'] = (_esSaludDanios=='S')?_CDIDEPERsel:_CDIDEEXTsel;
        
        Ext.Ajax.request(
        {
            url       : _p22_urlGuadarTvaloper
            ,jsonData :
            {
                smap1 : jsonData,
                smap2  : _p22_formDatosGenerales().getValues(),
                params : _p22_formDomicilio().getValues()
            }
            ,success  : function(response)
            {
                _p22_formDatosAdicionales().setLoading(false);
                var json = Ext.decode(response.responseText);
                debug('response text:',json);
                if(json.exito)
                {
                    mensajeCorrecto('Datos guardados',json.respuesta);
                    
                    
                    	if(_esSaludDanios == 'S'){
                    		_CDIDEEXTsel = json.smap1.codigoExterno;
	                	}else{
							_CDIDEPERsel = json.smap1.codigoExterno;
	                	}
	                	
	                	_p22_loadRecordCdperson(function(){debug('Sin Callback');},true);
                }
                else
                {
                	/**
                	 * TODO: Cambiar por Es en Proceso
                	 */
                    if(!Ext.isEmpty(_cargaCompania)){
                    	mensajeWarning(json.respuesta);
                    }else{
                    	mensajeError(json.respuesta);	
                    }
                    
                }
                
                try{
	    			var ventanaMensaje = window.parent;
	    			
	    			if (ventanaMensaje != window.top) {
					  debug('Para postMessage, El parent es el mismo que el top');
					}else{
					  debug('Para postMessage, El parent no es el mismo que el top');
					}
					
					var _codigoDanios = '';
					var _codigoSalud  = '';
					
					try{
						_codigoDanios = _CDIDEPERsel.substring(5);
					}catch(e){
						debug('Error al obtener codigo externo de Danios',e);
					}
					
					try{
						_codigoSalud = _CDIDEEXTsel.substring(5);
					}catch(e){
						debug('Error al obtener codigo externo de Salud',e);
					}
					
					var objMsg = {
						clienteIce: true,
						modo: 'G',
						success : json.exito,
						cdperson: _p22_cdperson,
						cdideper: _CDIDEPERsel,
						cdideext: _CDIDEEXTsel,
						codigoDanios: _codigoDanios,
						codigoSalud:_codigoSalud
					};
					
					ventanaMensaje.postMessage(objMsg, "*");
					if(_modoRecuperaDanios){
						mensajeInfo('Cliente Guardado y Recuperado: ' + _codigoDanios);
					}
					
	    		}catch(e){
	    			debugError('Error en postMessage',e);
	    		}
                
                /** PARA ACTUALIZAR EL NUEVO ESTATUS GENERAL DE LA PERSONA 
                Ext.Ajax.request(
            	        {
            	            url       : _UrlActualizaStatusPersona
            	            ,params: {
        	            		'params.pv_cdperson_i':  _p22_fieldCdperson().getValue(),
        	            		'params.pv_cdrol_i':  '1'
        	            	}
            	            ,success  : function(response)
            	            {
            	                //_p22_formDatosAdicionales().setLoading(false);
            	                var json = Ext.decode(response.responseText);
            	                debug('response text:',json);
            	                if(json.exito)
            	                {
            	                    debug('Actualizando estatus de Persona: ');
            	                    _fieldByName('STATUS',_PanelPrincipalPersonas).setValue(json.respuesta);
            	                    
            	                }
            	                else
            	                {
            	                    mensajeError(json.respuesta);
            	                }
            	            }
            	            ,failure  : function()
            	            {
            	                _p22_formDatosAdicionales().setLoading(false);
            	                errorComunicacion(null,'En actualiza estatus persona');
            	            }
            	});**/
            }
            ,failure  : function()
            {
                _p22_formDatosAdicionales().setLoading(false);
                errorComunicacion(null,'En guardar datos adicionales');
            }
        });
        
    }
    
    if(valido && (deleteList.length > 0 || saveList.length > 0 || updateList.length > 0)){
    	_p22_formDatosAdicionales().setLoading(true);
    	
    	Ext.Ajax.request(
    	        {
    	            url       : _UrlGuardaAccionista
    	            ,jsonData :
    	            {
    	            	params: {
    	            		'pv_cdperson_i':   _p22_fieldCdperson().getValue(),
        	            	'pv_cdatribu_i':  fieldEstCorp.getName().substring(fieldEstCorp.getName().length-2, fieldEstCorp.getName().length),
        	            	'pv_cdtpesco_i':  fieldEstCorp.getValue()
    	            	},
    	                'saveList'   : saveList,
    	                'deleteList' : deleteList,
    	                'updateList' : updateList
    	            }
    	            ,success  : function(response)
    	            {
    	                _p22_formDatosAdicionales().setLoading(false);
    	                var json = Ext.decode(response.responseText);
    	                debug('response text:',json);
    	                if(json.exito)
    	                {
    	                	windowAccionistas = undefined;
    	                    debug('Datos de Accionistas guardados correctamente.');
    	                }
    	                else
    	                {
    	                    mensajeError(json.respuesta);
    	                }
    	            }
    	            ,failure  : function()
    	            {
    	                _p22_formDatosAdicionales().setLoading(false);
    	                errorComunicacion(null,'En guardar datos de accionistas');
    	            }
    	});
	}
    
    debug('<_p22_guardarDatosAdicionalesClic');
}

function _p22_formDatosAdicionales()
{
    debug('>_p22_formDatosAdicionales<');
    return Ext.ComponentQuery.query('#_p22_formDatosAdicionales')[Ext.ComponentQuery.query('#_p22_formDatosAdicionales').length-1];
}

function _p22_documentosClic()
{
    debug('>_p22_documentosClic');
    var windowDocsPer = Ext.create('Ext.window.Window',
    {
        title        : 'Documentos'
        ,modal       : true
        ,buttonAlign : 'center'
        ,width       : 600
        ,height      : 400
        ,autoScroll  : true
        ,loader      :
        {
            url       : _p22_urlPantallaDocumentos
            ,params   :
            {
                'smap1.cdperson'  : _p22_fieldCdperson().getValue()
            }
            ,scripts  : true
            ,autoLoad : true
        }
    }).show();
    centrarVentanaInterna(windowDocsPer);
    debug('<_p22_documentosClic');
}

function _p22_subirArchivo(cdperson,codidocu,descrip)
{
    debug('>_p22_subirArchivo',cdperson,codidocu,descrip);
    _p22_windowAgregarDocu=Ext.create('Ext.window.Window',
            {
                id           : '_p22_WinPopupAddDoc'
                ,title       : 'Agregar documento'
                ,closable    : false
                ,modal       : true
                ,width       : 500
                //,height   : 700
                ,bodyPadding : 5
                ,items       :
                [
                    panelSeleccionDocumento= Ext.create('Ext.form.Panel',
                    {
                        border       : 0
                        ,url         : _p22_urlSubirArchivo
                        ,buttonAlign : 'center'
                        ,items       :
                        [
                            {
                                xtype       : 'datefield'
                                ,readOnly   : true
                                ,format     : 'd/m/Y'
                                ,name       : 'smap1.fecha'
                                ,value      : new Date()
                                ,fieldLabel : 'Fecha'
                            }
                            ,{
                                xtype       : 'textfield'
                                ,fieldLabel : 'Descripci&oacute;n'
                                ,name       : 'smap1.descripcion'
                                ,value      : descrip
                                ,readOnly   : true
                                ,width      : 450
                            }
                            ,{
                                xtype       : 'filefield'
                                ,fieldLabel : 'Documento'
                                ,buttonText : 'Examinar...'
                                ,buttonOnly : false
                                ,width      : 450
                                ,name       : 'file'
                                ,cAccept    : ['jpg','png','gif','zip','pdf','rar','jpeg','doc','docx','xls','xlsx','ppt','pptx']
                                ,listeners  :
                                {
                                    change : function(me)
                                    {
                                        var indexofPeriod = me.getValue().lastIndexOf("."),
                                        uploadedExtension = me.getValue().substr(indexofPeriod + 1, me.getValue().length - indexofPeriod).toLowerCase();
                                        if (false&&!Ext.Array.contains(this.cAccept, uploadedExtension))
                                        {
                                            Ext.MessageBox.show(
                                            {
                                                title   : 'Error de tipo de archivo',
                                                msg     : 'Extensiones permitidas: ' + this.cAccept.join(),
                                                buttons : Ext.Msg.OK,
                                                icon    : Ext.Msg.WARNING
                                            });
                                            me.reset();
                                            Ext.getCmp('_p22_botGuaDoc').setDisabled(true);
                                        }
                                        else
                                        {
                                            Ext.getCmp('_p22_botGuaDoc').setDisabled(false);
                                        }
                                    }
                                }
                            }
                            ,Ext.create('Ext.panel.Panel',
                            {
                                html    :'<iframe id="_p22_IframeUploadDoc" name="_p22_IframeUploadDoc"></iframe>'
                                ,hidden : true
                            })
                            ,Ext.create('Ext.panel.Panel',
                            {
                                border  : 0
                                ,html   :'<iframe id="_p22_IframeUploadPro" name="_p22_IframeUploadPro" width="100%" height="30" src="'+_p22_UrlUploadPro+'" frameborder="0"></iframe>'
                                ,hidden : false
                            })
                        ]
                        ,buttons     :
                        [
                            {
                                id        : '_p22_botGuaDoc'
                                ,text     : 'Agregar'
                                ,icon     : '${ctx}/resources/fam3icons/icons/disk.png'
                                ,disabled : true
                                ,handler  : function (button,e)
                                {
                                    debug(button.up().up().getForm().getValues());
                                    button.setDisabled(true);
                                    Ext.getCmp('_p22_BotCanDoc').setDisabled(true);
                                    Ext.create('Ext.form.Panel').submit(
                                    {
                                        url             : _p22_UrlUploadPro
                                        ,standardSubmit : true
                                        ,target         : '_p22_IframeUploadPro'
                                        ,params         :
                                        {
                                            uploadKey : '1'
                                        }
                                    });
                                    button.up().up().getForm().submit(
                                    {
                                        standardSubmit : true
                                        ,target        : '_p22_IframeUploadDoc'
                                        ,params        :
                                        {
                                            'smap1.cdperson'    : cdperson
                                            ,'smap1.codidocu'   : codidocu
                                            ,'smap1.callbackFn' : 'callbackDocumentoSubidoPersona'
                                        }
                                    });
                                }
                            }
                            ,{
                                id       : '_p22_BotCanDoc'
                                ,text    : 'Cancelar'
                                ,icon    : '${ctx}/resources/fam3icons/icons/cancel.png'
                                ,handler : function (button,e)
                                {
                                    _p22_windowAgregarDocu.destroy();
                                }
                            }
                        ]
                    })
                ]
            }).show();
            centrarVentanaInterna(_p22_windowAgregarDocu);
    debug('<_p22_subirArchivo');
}

function _p22_cargarArchivo(cdperson,codidocu,dsdocume)
{
    debug('>_p22_cargarArchivo',cdperson,codidocu,dsdocume);
    _p22_principalDatosAdicionales().setLoading(true);
    Ext.Ajax.request(
    {
        url      : _p22_urlCargarNombreArchivo
        ,params  :
        {
            'smap1.cdperson'  : cdperson
            ,'smap1.codidocu' : codidocu
        }
        ,success : function(response)
        {
            _p22_principalDatosAdicionales().setLoading(false);
            var json=Ext.decode(response.responseText);
            debug('json response:',json);
            if(json.exito)
            {
                var numRand=Math.floor((Math.random()*100000)+1);
                debug('numRand a: ',numRand);
                var windowVerDocu=Ext.create('Ext.window.Window',
                {
                    title          : dsdocume
                    ,width         : 700
                    ,height        : 500
                    ,collapsible   : true
                    ,titleCollapse : true
                    ,html          : '<iframe innerframe="'+numRand+'" frameborder="0" width="100" height="100"'
                                     +'src="'+_p22_urlViewDoc+'?path='+_RUTA_DOCUMENTOS_PERSONA+'&subfolder='+cdperson+'&filename='+json.smap1.cddocume+'">'
                                     +'</iframe>'
                    ,listeners     :
                    {
                        resize : function(win,width,height,opt){
                            debug(width,height);
                            $('[innerframe="'+numRand+'"]').attr({'width':width-20,'height':height-60});
                        }
                    }
                }).show();
                centrarVentanaInterna(windowVerDocu);
            }
            else
            {
                mensajeError(json.respuesta);
            }
        }
        ,failure : function()
        {
            _p22_principalDatosAdicionales().setLoading(false);
            errorComunicacion(null,'En cargar archivo');
        }
    });
    
    debug('<_p22_cargarArchivo');
}

function callbackDocumentoSubidoPersona()
{
    _p22_windowAgregarDocu.destroy();
    
    var elemento = _fieldByName(_DocASubir,null,true);
    
    if(!Ext.isEmpty(elemento.store)){
    	elemento.setValue('S');
    }
    
    (Ext.ComponentQuery.query('[docCargado='+_DocASubir+'ImgDocId]')[Ext.ComponentQuery.query('[docCargado='+_DocASubir+'ImgDocId]').length-1]).setSrc('${ctx}/resources/fam3icons/icons/accept.png');
    
}


function verEditarAccionistas(cdperson, cdatribu, cdestructcorp){
	
	
	if(Ext.isEmpty(cdestructcorp)){
		mensajeWarning('Debe seleccionar una Estructura Coorporativa.');
		return;
	}
	
	if(!windowAccionistas){
		Ext.define('modeloAccionistas',{
	        extend  : 'Ext.data.Model'
	        ,fields :
	        [
	            'NMORDINA'
	            ,'DSNOMBRE'
	            ,'CDNACION'
	            ,'PORPARTI'
	        ]
		});
		
		accionistasStore = Ext.create('Ext.data.Store',
			    {
					pageSize : 20,
			        autoLoad : true
			        ,model   : 'modeloAccionistas'
			        ,proxy   :
			        {
			            type         : 'memory'
			            ,enablePaging : true
			            ,reader      : 'json'
			            ,data        : []
			        }
			    });
		
		gridAccionistas = Ext.create('Ext.grid.Panel',
		    {
		    title    : 'Para Editar un Accionista de Doble Clic en la fila deseada.'
		    ,height  : 200
		    ,plugins : Ext.create('Ext.grid.plugin.RowEditing',
		    {
		    	pluginId: 'accionistasRowId',
		        clicksToEdit  : 2,
		        errorSummary : false,
		        listeners: {
		    		beforeedit: function(){
		    			_0_botAceptar.disable();
		    		},
		    		edit: function(){
		    			_0_botAceptar.enable();
		    		},
		    		canceledit: function(){
		    			_0_botAceptar.enable();
		    		}
		    	}
		        
		    })
		    ,tbar     :
		        [
		            {
		                text     : 'Agregar'
		                ,icon    : '${ctx}/resources/fam3icons/icons/add.png'
		                ,handler : function(){
		                	accionistasStore.add(new modeloAccionistas());
		                	gridAccionistas.getPlugin('accionistasRowId').startEdit(accionistasStore.getCount()-1,0);
		                }
		            },{
		                text     : 'Eliminar'
			                ,icon    : '${ctx}/resources/fam3icons/icons/delete.png'
			                ,handler : function(){
			                	accionistasStore.remove(gridAccionistas.getSelectionModel().getSelection());
			                }
			            }
		        ]
		    ,columns :
		    [
		        {
		            header     : 'Accionista'
		            ,dataIndex : 'DSNOMBRE'
		            ,flex      : 1
		            ,editor    :
		            {
		                xtype             : 'textfield'
		                ,allowBlank       : false
		            }
		        }
		        ,{
		            header     : 'Nacionalidad'
		            ,dataIndex : 'CDNACION'
		            ,flex      : 1
		            ,renderer  : function(valor){
		            	return rendererColumnasDinamico(valor,'CDNACION'); 
		            }
		            ,editor    :
		            {
		                xtype         : 'combobox',
		                allowBlank    : false,
		                name          : 'CDNACION',
		                valueField    : 'key',
		                displayField  : 'value',
		                forceSelection: true,
		                typeAhead     : true,
		                anyMatch      : true,
		                store         : Ext.create('Ext.data.Store', {
		                    model     : 'Generic',
		                    autoLoad  : true,
		                    proxy     : {
		                        type        : 'ajax'
		                        ,url        : _URL_CARGA_CATALOGO
		                        ,extraParams: {catalogo:_CAT_NACIONALIDAD}
		                        ,reader     :
		                        {
		                            type  : 'json'
		                            ,root : 'lista'
		                        }
		                    }
		                })
		               
		            }
		        }
		        ,{
		            header     : 'Porcentaje Participaci&oacute;n'
		            ,dataIndex : 'PORPARTI'
		            ,flex      : 1
		            ,editor    :
		            {
		                xtype             : 'numberfield'
		                ,allowBlank       : false
		                ,allowDecimals    : true
		                ,minValue         : 0
		                ,negativeText     : 'No se puede introducir valores negativos.'
		                ,decimalSeparator : '.'
		            }
		        }
		    ]
		    ,store : accionistasStore
		});
		
		
		_0_botAceptar = Ext.create('Ext.Button',{
            text: 'Aceptar',
            icon:'${ctx}/resources/fam3icons/icons/accept.png',
            handler: function() {
           	 windowAccionistas.close();
            }
      	});
		
		windowAccionistas = Ext.create('Ext.window.Window', {
	          title: 'Accionistas',
	          closeAction: 'close',
	          modal:true,
	          closable: false,
	          height : 320,
	          width  : 800,
		      items: [gridAccionistas],
	          bodyStyle:'padding:15px;',
	          buttons:[_0_botAceptar]
	        });
		
			var params = {
				'params.pv_cdperson_i' : cdperson,
				'params.pv_cdatribu_i' : cdatribu,
				'params.pv_cdtpesco_i' : cdestructcorp
			};
			
			cargaStorePaginadoLocal(accionistasStore, _UrlCargaAccionistas, 'slist1', params, function (options, success, response){
	    		if(success){
	                var jsonResponse = Ext.decode(response.responseText);
	                
	                if(!jsonResponse.success) {
	                    showMessage(_MSG_SIN_DATOS, _MSG_BUSQUEDA_SIN_DATOS, Ext.Msg.OK, Ext.Msg.INFO);
	                }
	            }else{
	                showMessage('Error', 'Error al obtener los datos.', Ext.Msg.OK, Ext.Msg.ERROR);
	            }
	    	}, gridAccionistas);
	}
	
			windowAccionistas.show();
			
}

destruirContLoaderPersona = function(){
	
	debug("DESTRUYENDO!!!");
	
	_p22_PanelPrincipal().query().forEach(function(element){
		try{
			element.destroy();
		}catch(e){
			debug('Error al destruir en Pantalla de Clientes',e);
		}
	});
	
	try{
		_p22_PanelPrincipal().destroy();	
	}catch(e){
		debug('Error al destruir Panel Principal en Pantalla de Clientes',e);
	}
	
	try{
		windowAccionistas.destroy();	
	}catch(e){
		debug('Error al destruir Window accionistas en Pantalla de Clientes',e);
	}
};

function destruirLoaderContratante(){
	
	debug("DESTRUYENDO!!!");
	
	_p22_PanelPrincipal().query().forEach(function(element){
		try{
			element.destroy();
		}catch(e){
			debug('Error al destruir en Pantalla de Clientes',e);
		}
	});
	
	try{
		_p22_PanelPrincipal().destroy();	
	}catch(e){
		debug('Error al destruir Panel Principal en Pantalla de Clientes',e);
	}
	
	try{
		windowAccionistas.destroy();	
	}catch(e){
		debug('Error al destruir Window accionistas en Pantalla de Clientes',e);
	}
}

obtieneDatosClienteContratante = function(){
	var datosPersona = {
		cdperson: (_p22_cdperson != false && !Ext.isEmpty(_p22_cdperson))? _p22_cdperson : '',
		fenacimi: _fieldByName('FENACIMI',_PanelPrincipalPersonas).getValue(),
		sexo:     _p22_fieldSexo().getValue(),
		tipoper:  _p22_fieldTipoPersona().getValue(),
		naciona:  _p22_fielCdNacion().getValue(),
		nombre:   _fieldByName('DSNOMBRE',_PanelPrincipalPersonas).getValue(),
		snombre:  _p22_fieldSegundoNombre().getValue(),
		appat:    _p22_fieldApat().getValue(),
		apmat:    _p22_fieldAmat().getValue(),
		rfc:      _p22_fieldRFC().getValue(),
		cdideper: _fieldByName('CDIDEPER',_PanelPrincipalPersonas).getValue(),
		cdideext: _fieldByName('CDIDEEXT',_PanelPrincipalPersonas).getValue(),
		codpos:   _p22_comboCodPostal().getValue(),
		cdedo:    _fieldByName('CDEDO',_PanelPrincipalPersonas).getValue(),
		cdmunici: _fieldByName('CDMUNICI',_PanelPrincipalPersonas).getValue(),
		nomRecupera: _nombreComContratante
		
	}
	
	return datosPersona;
};

function obtDatLoaderContratante(){
	
	var datosPersona = {
		cdperson: (_p22_cdperson != false && !Ext.isEmpty(_p22_cdperson))? _p22_cdperson : '',
		fenacimi: _fieldByName('FENACIMI',_PanelPrincipalPersonas).getValue(),
		sexo:     _p22_fieldSexo().getValue(),
		tipoper:  _p22_fieldTipoPersona().getValue(),
		naciona:  _p22_fielCdNacion().getValue(),
		nombre:   _fieldByName('DSNOMBRE',_PanelPrincipalPersonas).getValue(),
		snombre:  _p22_fieldSegundoNombre().getValue(),
		appat:    _p22_fieldApat().getValue(),
		apmat:    _p22_fieldAmat().getValue(),
		rfc:      _p22_fieldRFC().getValue(),
		cdideper: _fieldByName('CDIDEPER',_PanelPrincipalPersonas).getValue(),
		cdideext: _fieldByName('CDIDEEXT',_PanelPrincipalPersonas).getValue(),
		codpos:   _p22_comboCodPostal().getValue(),
		cdedo:    _fieldByName('CDEDO',_PanelPrincipalPersonas).getValue(),
		cdmunici: _fieldByName('CDMUNICI',_PanelPrincipalPersonas).getValue(),
		nomRecupera: _nombreComContratante
	}
	
	return datosPersona;
}

////// funciones //////

//}//fin de if para que no se duplique codigo
</script>
</head>
<body>
<div id="_p22_divpri" style="height : 1400px;"></div>
</body>
</html>