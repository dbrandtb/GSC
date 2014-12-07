<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Afiliados Afectados</title>
		<script type="text/javascript">
		/*INICIO DECLARACION VARIABLES*/
			var _CONTEXT 							= '${ctx}';
			var _URL_CATALOGOS						= '<s:url namespace="/catalogos" action="obtieneCatalogo" />';
			var _CATALOGO_TipoMoneda				= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TIPO_MONEDA"/>';
			var _ROL_MEDICO							= '<s:property value="@mx.com.gseguros.portal.general.util.RolSistema@MEDICO_AJUSTADOR.cdsisrol" />';
			var _CATALOGO_COB_X_VALORES 			= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@COBERTURASXVALORES"/>';
			var _11_url_RequiereAutServ				= '<s:url namespace="/siniestros" action="obtieneRequiereAutServ"         />';
			var _11_urlIniciarSiniestroSinAutServ	= '<s:url namespace="/siniestros"  action="generarSiniestrosinAutServ"    />';
			var _11_urlIniciarSiniestroTworksin		= '<s:url namespace="/siniestros"  action="iniciarSiniestroTworksin"      />';
			var _p12_urlObtenerSiniestrosTramite	= '<s:url namespace="/siniestros"  action="obtenerSiniestrosTramite" />';
			var _URL_LISTA_COBERTURA 				= '<s:url namespace="/siniestros"  action="consultaListaCoberturaPoliza" />';
			var _URL_LISTA_SUBCOBERTURA				= '<s:url namespace="/siniestros"  action="consultaListaSubcobertura" />';
			var _TIPO_PAGO_DIRECTO					= '<s:property value="@mx.com.gseguros.portal.general.util.TipoPago@DIRECTO.codigo"/>';
			var _TIPO_PAGO_REEMBOLSO				= '<s:property value="@mx.com.gseguros.portal.general.util.TipoPago@REEMBOLSO.codigo"/>';
			var _URL_GuardaFactura					= '<s:url namespace="/siniestros" action="guardaFacturaTramite" />';
			var _URL_LISTA_AUTSERVICIO				= '<s:url namespace="/siniestros" action="consultaAutServicioSiniestro"         />';
			var _URL_DATOS_VALIDACION				= '<s:url namespace="/siniestros" action="consultaDatosValidacionSiniestro"         />';
			var _URL_LoadConceptos					= '<s:url namespace="/siniestros" action="obtenerMsinival" />';
			var _11_params							= <s:property value="%{convertToJSON('params')}" escapeHtml="false" />;
			debug("VALOR DE _11_params --->");
			debug(_11_params);
			var _CATALOGO_TipoConcepto				= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TIPO_CONCEPTO_SINIESTROS"/>';
			var _CATALOGO_ConceptosMedicos			= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@CODIGOS_MEDICOS"/>';
			var _URL_MONTO_ARANCEL					= '<s:url namespace="/siniestros"  action="obtieneMontoArancel"/>';
			var _UrlAjustesMedicos					=  '<s:url namespace="/siniestros" action="includes/ajustesMedicos" />';
			var _URL_GUARDA_CONCEPTO_TRAMITE		= '<s:url namespace="/siniestros"  		action="guardarMsinival"/>';
			var _CDROL								= '<s:property value="params.cdrol" />';
			var _11_urlTabbedPanel					= '<s:url namespace="/siniestros"  action="includes/detalleSiniestro"/>';
			//var _URL_LOADER_INFO_GRAL_RECLAMACION	= '<s:url namespace="/siniestros" action="includes/loadInfoGeneralReclamacion" />';
			var _11_urlMesaControl					= '<s:url namespace="/mesacontrol" action="mcdinamica"                    />';
			var _11_itemsForm	=
			[
				<s:property value="imap.itemsForm" />
				,{
					xtype	: 'label'
					,hidden	: true
				}
			];
			var _11_itemsRechazo = [ <s:property value="imap.itemsCancelar" /> ];
			_11_itemsRechazo[2]['width']  = 500;
			_11_itemsRechazo[2]['height'] = 150;
			var _11_form;
			var recordsStoreFactura = [];
			var _11_textfieldAsegurado;
			var _11_textfieldNmautserv;
			var panelInicialPral;
			var storeAseguradoFactura;
			var modPolizasAltaTramite;
			var storeFacturaDirectoNva;
			var gridAutorizacion;
			var storeListadoAutorizacion;
			var cmbCveTipoConcepto;
			var cmbCveConcepto;
			var _11_windowRechazoSiniestro;
			var _11_formRechazo;
			var _11_conceptoSeleccionado=null;
			var _tipoPago = _11_params.OTVALOR02;

			var storeCobertura = Ext.create('Ext.data.Store', {
				model:'Generic',
				autoLoad:false,
				proxy:
				{
					type: 'ajax',
					url : _URL_CATALOGOS,
					extraParams : {catalogo:_CATALOGO_COB_X_VALORES},
					reader:
					{
						type: 'json',
						root: 'lista'
					}
				}
			});

			var storeSubcobertura= Ext.create('Ext.data.Store', {
				model:'Generic',
				autoLoad:false,
				proxy: {
					type: 'ajax',
					url : _URL_LISTA_SUBCOBERTURA,
					reader: {
						type: 'json',
						root: 'listaSubcobertura'
					}
				}
			});
		/*FIN DECLARACION VARIABLES*/
			<s:set name="contadorFactura" value="0" />
			<s:iterator value="slist2">
				recordsStoreFactura.push({
					reclamacion:	'<s:property value='%{getSlist2().get(#contadorFactura).get("CODRECLAM")}'			escapeHtml="false" />'
					,ntramite:		'<s:property value='%{getSlist2().get(#contadorFactura).get("NTRAMITE")}'			escapeHtml="false" />'
					,factura:		'<s:property value='%{getSlist2().get(#contadorFactura).get("NFACTURA")}'			escapeHtml="false" />'
					,fechaFactura:	'<s:property value='%{getSlist2().get(#contadorFactura).get("FFACTURA")}'			escapeHtml="false" />'
					,cdtipser:		'<s:property value='%{getSlist2().get(#contadorFactura).get("CDTIPSER")}'			escapeHtml="false" />'
					,descServicio:	'<s:property value='%{getSlist2().get(#contadorFactura).get("DESCSERVICIO")}'		escapeHtml="false" />'
					,cdpresta:		'<s:property value='%{getSlist2().get(#contadorFactura).get("CDPRESTA")}'			escapeHtml="false" />'
					,nomProveedor:	'<s:property value='%{getSlist2().get(#contadorFactura).get("NOMBREPROVEEDOR")}'	escapeHtml="false" />'
					,ptimport:		'<s:property value='%{getSlist2().get(#contadorFactura).get("PTIMPORT")}'			escapeHtml="false" />'
					,cdgarant:		'<s:property value='%{getSlist2().get(#contadorFactura).get("CDGARANT")}'			escapeHtml="false" />'
					,descCdgarant:	'<s:property value='%{getSlist2().get(#contadorFactura).get("DSGARANT")}'			escapeHtml="false" />'
					,desctoPorc:	'<s:property value='%{getSlist2().get(#contadorFactura).get("DESCPORC")}'			escapeHtml="false" />'
					,desctoNum:		'<s:property value='%{getSlist2().get(#contadorFactura).get("DESCNUME")}'			escapeHtml="false" />'
					,cdconval:		'<s:property value='%{getSlist2().get(#contadorFactura).get("CDCONVAL")}'			escapeHtml="false" />'
					,dssubgar:		'<s:property value='%{getSlist2().get(#contadorFactura).get("DSSUBGAR")}'			escapeHtml="false" />'
					,cdmoneda:		'<s:property value='%{getSlist2().get(#contadorFactura).get("CDMONEDA")}'			escapeHtml="false" />'
					,descMoneda:	'<s:property value='%{getSlist2().get(#contadorFactura).get("DESTIPOMONEDA")}'		escapeHtml="false" />'
					,tasaCambio:	'<s:property value='%{getSlist2().get(#contadorFactura).get("TASACAMB")}'			escapeHtml="false" />'
					,ptimporta:		'<s:property value='%{getSlist2().get(#contadorFactura).get("PTIMPORTA")}'			escapeHtml="false" />'
					,dctoNuex:'		<s:property value='%{getSlist2().get(#contadorFactura).get("DCTONUEX")}'			escapeHtml="false" />'
				});
				<s:set name="contadorFactura" value="#contadorFactura+1" />
			</s:iterator>
			
			var _11_columnas_Factura = [
				{
					xtype			: 'actioncolumn'
					,menuDisabled	: true
					,width			: 70
					,align			: 'center'
					,items			:
					[
						{
							icon		: '${ctx}/resources/fam3icons/icons/pencil.png'
							,tooltip	: 'Factura'
							,handler	: _11_editar
						}/*
						,{
							icon		: '${ctx}/resources/fam3icons/icons/folder.png'
							,tooltip	: 'Capturar Detalle'
							//,handler	: revisarDocumento
						}
						,{
							icon		: '${ctx}/resources/fam3icons/icons/delete.png'
							,tooltip	: 'Eliminar Fac'
							//,handler	: revisarDocumento
						}*/
					]
				},{
					text	:'Reclamaci&oacute;n',		dataIndex	:'reclamacion',		width	: 100
				},{
					text	:'Factura',					dataIndex	:'factura'
				},{
					text	:'Fecha Factura',			dataIndex	:'fechaFactura',	format	:'d/m/Y',			xtype	:'datecolumn'
				},{
					text	:'Cobertura',				dataIndex	:'descCdgarant',	width	: 300
				},{
					text	:'Subcobertura',			dataIndex	:'dssubgar',		width	: 300
				},{
					text	:'Proveedor',				dataIndex	:'nomProveedor',	width	: 350
				},{
					text	:'Moneda',					dataIndex	:'descMoneda'
				},{
					text	:'Importe MXN',				dataIndex	:'ptimport',		renderer: Ext.util.Format.usMoney
				},{
					text	:'Desc. %',					dataIndex	:'desctoPorc'
				},{
					text	:'Desc. $',					dataIndex	:'desctoNum',		renderer: Ext.util.Format.usMoney
				},{
					text	:'tasaCambio',				dataIndex	:'tasaCambio',		renderer: Ext.util.Format.usMoney
				},{
					text	:'Importe Factura',			dataIndex	:'ptimporta',		renderer: Ext.util.Format.usMoney
				}
			];

		/*INICIO DE FUNCIONES*/
			function _11_regresarMC()
			{
				debug('_11_regresarMC');
				Ext.create('Ext.form.Panel').submit({
					url				: _11_urlMesaControl
					,standardSubmit	:true
					,params			:
					{
						'smap1.gridTitle'		: 'Siniestros'
						,'smap2.pv_cdtiptra_i'	: 16
					}
				});
			}
		
			function _11_rechazoSiniestro()
			{
				debug('_11_rechazoSiniestro');
				
				var valido = _11_formRechazo.isValid();
				if(!valido)
				{
					datosIncompletos();
				}
				
				if(valido)
				{
					//_11_formEdicion.setLoading(true);
					/*var json =
					{
						params : _11_formEdicion.getValues()
					};
					json.params['cdunieco'] = _11_recordActivo.get('CDUNIECO');
					json.params['cdramo']   = _11_recordActivo.get('CDRAMO');
					json.params['estado']   = _11_recordActivo.get('ESTADO');
					json.params['nmpoliza'] = _11_recordActivo.get('noPoliza');
					json.params['nmsituac'] = _11_recordActivo.get('NMSITUAC');
					json.params['nmsuplem'] = _11_recordActivo.get('NMSUPLEM');
					json.params['status']   = _11_recordActivo.get('STATUS');
					json.params['aaapertu'] = _11_recordActivo.get('AAAPERTU');
					json.params['nmsinies'] = _11_recordActivo.get('IdReclamacion');
					json.params['cancelar']       = 'si';
					json.params['cdmotivo']       = _11_itemsRechazo[0].getValue();
					json.params['cdsubmotivo']    = _11_itemsRechazo[1].getValue();
					json.params['rechazocomment'] = _11_itemsRechazo[2].getValue();
					debug('datos enviados:',json);
					_11_windowRechazoSiniestro.setLoading(true);
					Ext.Ajax.request(
					{
						url       : _11_urlActualizarSiniestro
						,jsonData : json
						,success  : function(response)
						{
							_11_windowRechazoSiniestro.setLoading(false);
							json = Ext.decode(response.responseText);
							if(json.success == true)
							{
								mensajeCorrecto('Datos guardados',json.mensaje,function()
								{
									Ext.create('Ext.form.Panel').submit(
									{
										url             : _11_urlMesaControl
										,standardSubmit :true
										,params         :
										{
											'smap1.gridTitle'      : 'Siniestros'
											,'smap2.pv_cdtiptra_i' : 16
										}
									});
								});
							}
							else
							{
								mensajeError(json.mensaje);
							}
						}
						,failure  : function()
						{
							_11_windowRechazoSiniestro.setLoading(false);
							errorComunicacion();
						}
					});*/
				}
			}
			
			function _11_editar(grid,rowindex)
			{
				_11_recordActivo = grid.getStore().getAt(rowindex);
				debug('_11_abrirEditor _11_recordActivo:',_11_recordActivo.raw);
				_11_llenaFormulario();
				modPolizasAltaTramite.show();
				centrarVentanaInterna(modPolizasAltaTramite);
			}
			
			function _11_llenaFormulario()
			{
				debug('_11_llenaFormulario --> ');
				debug(_11_recordActivo);
				/*OBTENEMOS LOS VALORES QUE TIENE PARA LAS VALIDACIONES DE IVA Y REQUI*/
				Ext.Ajax.request({
					url     : _URL_DATOS_VALIDACION
					,params:{
						'params.ntramite': _11_recordActivo.get('ntramite')
						,'params.nfactura': _11_recordActivo.get('factura')
					}
					,success : function (response)
					{
						panelInicialPral.down('[name=params.ntramite]').setValue(_11_recordActivo.get('ntramite'));
						panelInicialPral.down('[name=params.cdpresta]').setValue(_11_recordActivo.get('cdpresta'));
						panelInicialPral.down('[name=params.cdtipser]').setValue(_11_recordActivo.get('cdtipser'));
							
						panelInicialPral.down('[name=params.nfactura]').setValue(_11_recordActivo.get('factura'));
						debug("FECHA DE FACTURA");
						debug(_11_recordActivo.get('fechaFactura'));
						
						var fechaFacturaM = _11_recordActivo.get('fechaFactura').match(/\d+/g); 
						var dateFec = fechaFacturaM[2]+"/"+fechaFacturaM[1]+"/"+fechaFacturaM[0];
						
						panelInicialPral.down('[name=params.fefactura]').setValue(dateFec);
						
						storeCobertura.load({
							params:{
								'params.ntramite':_11_recordActivo.get('ntramite'),
								'params.tipopago':_tipoPago
							}
						});
						
						panelInicialPral.down('[name=params.cdgarant]').setValue(_11_recordActivo.get('cdgarant'));
						
						storeSubcobertura.load({
							params:{
								'params.cdgarant' :_11_recordActivo.get('params.cdgarant')
							}
						});
						panelInicialPral.down('combo[name=params.cdconval]').setValue(_11_recordActivo.get('cdconval'));
						
						panelInicialPral.down('combo[name=params.tipoMoneda]').setValue(_11_recordActivo.get('cdmoneda'));
						
						panelInicialPral.down('[name=params.ptimport]').setValue(_11_recordActivo.get('ptimport'));
						panelInicialPral.down('[name=params.tasacamb]').setValue(_11_recordActivo.get('tasaCambio'));
						panelInicialPral.down('[name=params.ptimporta]').setValue(_11_recordActivo.get('ptimporta'));
						panelInicialPral.down('[name=params.descporc]').setValue(_11_recordActivo.get('desctoPorc'));
						panelInicialPral.down('[name=params.descnume]').setValue(_11_recordActivo.get('desctoNum'));
						storeAseguradoFactura.removeAll();
						storeAseguradoFactura.load({
							params: {
								'smap.ntramite'   : _11_recordActivo.get('ntramite'),
								'smap.nfactura'   : _11_recordActivo.get('factura')
							}
						});
						if(Ext.decode(response.responseText).datosValidacion != null){
							var aplicaIVA = null;
							var ivaRetenido = null;
							var ivaAntesDespues = null;
							var autAR = null;
							var autAM = null;
							var commAR = null;
							var commAM = null;
							var json=Ext.decode(response.responseText).datosValidacion;
							debug("VALOR DE JSON");
							debug(json);
							if(json.length > 0){
								aplicaIVA = json[0].OTVALOR01;
								ivaAntesDespues = json[0].OTVALOR02;
								ivaRetenido = json[0].OTVALOR03;
								
								for(var i = 0; i < json.length; i++){
									if(json[i].AREAAUTO =="ME"){
										autAM = json[i].SWAUTORI;
										commAM = json[i].COMENTARIOS;
									}
									if(json[i].AREAAUTO =="RE"){
										autAR = json[i].SWAUTORI;
										commAR = json[i].COMENTARIOS;
									}
								}
							}
							/*REALIZAMOS LA ASIGNACIÓN DE LAS VARIABLES*/
							if(aplicaIVA == null){
								panelInicialPral.down('[name="parametros.pv_otvalor01"]').setValue("S");
							}else{
								panelInicialPral.down('[name="parametros.pv_otvalor01"]').setValue(aplicaIVA);
							}
							if(aplicaIVA == null){
								panelInicialPral.down('[name="parametros.pv_otvalor02"]').setValue("D");
							}else{
								panelInicialPral.down('[name="parametros.pv_otvalor02"]').setValue(ivaAntesDespues);
							}
							if(aplicaIVA == null){
								panelInicialPral.down('[name="parametros.pv_otvalor03"]').setValue("N");
							}else{
								panelInicialPral.down('[name="parametros.pv_otvalor03"]').setValue(ivaRetenido);
							}
							
							if(autAR == null){
								panelInicialPral.down('[name="params.autrecla"]').setValue(null);
							}else{
								panelInicialPral.down('[name="params.autrecla"]').setValue(autAR);
							}
							
							if(commAR == null){
								panelInicialPral.down('[name="params.commenar"]').setValue(null);
							}else{
								panelInicialPral.down('[name="params.commenar"]').setValue(commAR);
							}
							if(autAM == null){
								panelInicialPral.down('[name="params.autmedic"]').setValue(null);
							}else{
								panelInicialPral.down('[name="params.autmedic"]').setValue(autAM);
							}
							if(commAM == null){
								panelInicialPral.down('[name="params.commenme"]').setValue(null);
							}else{
								panelInicialPral.down('[name="params.commenme"]').setValue(commAM);
							}
						}
					},
					failure : function (){
						me.up().up().setLoading(false);
						Ext.Msg.show({
							title:'Error',
							msg: 'Error de comunicaci&oacute;n',
							buttons: Ext.Msg.OK,
							icon: Ext.Msg.ERROR
						});
					}
				});
			}
			
			function revisarDocumento(grid,rowIndex)
			{
				var record = grid.getStore().getAt(rowIndex);
				debug('record.raw:',record.raw);
				var valido = true;
				Ext.Ajax.request(
				{
					url    : _11_url_RequiereAutServ
					,params:{
						'params.cobertura': panelInicialPral.down('[name=params.cdgarant]').getValue(),
						'params.subcobertura': panelInicialPral.down('[name=params.cdconval]').getValue()
					}
					,success : function (response)
					{
						var json=Ext.decode(response.responseText).datosInformacionAdicional[0];
						var requiereAutorizacion = json.REQAUTSERV
						debug(requiereAutorizacion);

						if(requiereAutorizacion == "SI"){ //Requiere autorizacion de servicio
							var idReclamacion = record.raw.NMSINIES;
							valido = idReclamacion && idReclamacion>0;
							if(!valido){
			                    _11_pedirAutorizacion(record);
			                }
						}else{
							//NO REQUIERE AUTORIZACIÓN DE SERVICIO
							var idReclamacion = record.raw.NMSINIES;
							debug(idReclamacion);
							valido = idReclamacion && idReclamacion>0;
							if(!valido){
								//Preguntamos si esta seguro de generar el siniestro
								msgWindow = Ext.Msg.show({
									title: 'Aviso',
									msg: '&iquest;Desea asociar el asegurado con la autorizaci&oacute;n de Servicio ?',
									buttons: Ext.Msg.YESNO,
									icon: Ext.Msg.QUESTION,
									fn: function(buttonId, text, opt){
										if(buttonId == 'no'){
											var json =
											{
												'params.ntramite' : panelInicialPral.down('[name=params.ntramite]').getValue(),
												'params.cdunieco' : record.raw.CDUNIECO,
												'params.cdramo'   : record.raw.CDRAMO,
												'params.estado'   : record.raw.ESTADO,
												'params.nmpoliza' : record.raw.NMPOLIZA,
												'params.nmsuplem' : record.raw.NMSUPLEM,
												'params.nmsituac' : record.raw.NMSITUAC,
												'params.cdtipsit' : record.raw.CDTIPSIT,
												'params.dateOcurrencia' : record.raw.FEOCURRE,
												'params.nfactura' : panelInicialPral.down('[name=params.nfactura]').getValue()
											};
											Ext.Ajax.request(
											{
												url      : _11_urlIniciarSiniestroSinAutServ
												,params  : json
												,success : function(response)
												{
													json = Ext.decode(response.responseText);
													if(json.success==true){
														mensajeCorrecto('Autorizaci&oacute;n',json.mensaje,function(){
															storeAseguradoFactura.removeAll();
															storeAseguradoFactura.load({
														    	params: {
														    		'smap.ntramite'   : panelInicialPral.down('[name=params.ntramite]').getValue(),
														    		'smap.nfactura'   : panelInicialPral.down('[name=params.nfactura]').getValue()
														    	}
														    });
														});
													}else{
														mensajeError(json.mensaje);
													}
												}
												,failure : function()
												{
													errorComunicacion();
												}
											});
										}else{
											var valido = true;
											var nAut = record.get('NoAutorizacion');
											valido = nAut && nAut>0;
											if(!valido){
												_11_pedirAutorizacion(record);
											}
											debug('!_11_validaAutorizacion: ',valido?'si':'no');
											return valido;
										}
									}
								});
								centrarVentana(msgWindow);
							}
						}
						
						if(valido)
						{
							valido = record.get('VoBoAuto')!='n'&&record.get('VoBoAuto')!='N';
							if(!valido)
							{
								mensajeError('El siniestro no se puede continuar porque no tiene el visto bueno autom&aacute;tico');
							}
						}
						
						if(valido)
						{
						windowLoader = Ext.create('Ext.window.Window',{
						        modal       : true,
						        buttonAlign : 'center',
						        title: 'Informaci&oacute;n general',
						        width       : 800,
						        height      : 450,
						        autoScroll  : true,
						        loader      : {
						            url     : _11_urlTabbedPanel
						            ,params         :
									{
										'params.ntramite'  : panelInicialPral.down('[name=params.ntramite]').getValue()
										,'params.cdunieco' : record.raw.CDUNIECO
										,'params.cdramo'   : record.raw.CDRAMO
										,'params.estado'   : record.raw.ESTADO
										,'params.nmpoliza' : record.raw.NMPOLIZA
										,'params.nmsuplem' : record.raw.NMSUPLEM
										,'params.nmsituac' : record.raw.NMSITUAC
										,'params.aaapertu' : record.raw.AAAPERTU
										,'params.status'   : record.raw.STATUS
										,'params.nmsinies' : record.raw.NMSINIES
										,'params.cdtipsit' : record.raw.CDTIPSIT
										,'params.cdrol'    : _CDROL
										,'params.tipopago' : _tipoPago
									},
						            scripts  : true,
						            loadMask : true,
						            autoLoad : true,
						            ajaxOptions: {
						            	method: 'POST'
						            }
						        }/*
							    ,
							    listeners:{
							         close:function(){
							             if(true){
							                 //Actualizamos la información de la consulta del grid inferior
							            	 storeConceptos.reload();
							             }
							         }
							    }*/
						    }).show();
						    centrarVentanaInterna(windowLoader);
						}
						/*
						'params.ntramite' : panelInicialPral.down('[name=params.ntramite]').getValue(),
												'params.cdunieco' : record.raw.CDUNIECO,
												'params.cdramo'   : record.raw.CDRAMO,
												'params.estado'   : record.raw.ESTADO,
												'params.nmpoliza' : record.raw.NMPOLIZA,
												'params.nmsuplem' : record.raw.NMSUPLEM,
												'params.nmsituac' : record.raw.NMSITUAC,
												'params.cdtipsit' : record.raw.CDTIPSIT,
												'params.dateOcurrencia' : record.raw.FEOCURRE,
						*/
						/*if(valido)
						{
							valido = record.get('AUTRECLA')!='n'&&record.get('AUTRECLA')!='N';
							if(!valido)
							{
								mensajeError('El siniestro no se puede continuar porque no tiene el visto bueno del &aacute;rea de reclamaciones');
							}
						}

						if(valido)
						{
							valido = record.get('AUTMEDIC')!='n'&&record.get('AUTMEDIC')!='N';
							if(!valido)
							{
								mensajeError('El siniestro no se puede continuar porque no tiene el visto bueno del &aacute;rea m&eacute;dica');
							}
						}*/
					},
					failure : function ()
					{
						Ext.Msg.show({
							title:'Error',
							msg: 'Error de comunicaci&oacute;n',
							buttons: Ext.Msg.OK,
							icon: Ext.Msg.ERROR
						});
					}
				});
			}
			
			function _11_pedirAutorizacion(record)
			{
				_11_recordActivo = record;
				debug('_11_recordActivo:',_11_recordActivo.data);
				
				_11_textfieldAsegurado.setValue(_11_recordActivo.get('NOMBRE'));
				var params = {
						'params.cdperson'	:	_11_recordActivo.get('CDPERSON')
				};
				cargaStorePaginadoLocal(storeListadoAutorizacion, _URL_LISTA_AUTSERVICIO, 'datosInformacionAdicional', params, function(options, success, response){
					if(success){
						var jsonResponse = Ext.decode(response.responseText);
						if(jsonResponse.datosInformacionAdicional.length <= 0) {
							centrarVentanaInterna(Ext.Msg.show({ 
								title: 'Aviso',
								msg: 'No existen autorizaci&oacute;n para el asegurado elegido.',
								buttons: Ext.Msg.OK,
								icon: Ext.Msg.WARNING
							}));
							_11_WindowPedirAut.close();
						}else{
							_11_windowPedirAut.show();
							_11_textfieldNmautserv.setValue('');
							centrarVentanaInterna(_11_windowPedirAut);
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
				
			}
			
			function _p21_agregarGrupoClic()
			{
				if(_tipoPago == _TIPO_PAGO_DIRECTO){
					storeFacturaDirectoNva.add(new modelFacturaSiniestroNva({tasaCambioNva:'0.00',importeFacturaNva:'0.00',tipoMonedaNameNva:'001'}));
				}else{
					//storeFacturaReembolso.add(new modelFacturaSiniestro({tasaCambio:'0.00',importeFactura:'0.00',tipoMonedaName:'001'}));
				}
			}
			
			
			function _mostrarVentanaAjustes(grid,rowIndex,colIndex){
			    var record = grid.getStore().getAt(rowIndex);
			    debug("record");
			    debug(record);
			    var recordFactura = gridFacturaDirecto.getSelectionModel().getSelection()[0];
			    
			    if ( _CDROL == _ROL_MEDICO){
				    windowLoader = Ext.create('Ext.window.Window',{
				        modal       : true,
				        buttonAlign : 'center',
				        title: 'Ajustes M&eacute;dico',
				        width       : 800,
				        height      : 450,
				        //autoScroll  : true,
				        loader      : {
				            url     : _UrlAjustesMedicos,
				            params         :
				            {
				            	'params.ntramite'  		: panelInicialPral.down('[name=params.ntramite]').getValue()
				                ,'params.cdunieco' 		: recordFactura.get('CDUNIECO')
				                ,'params.cdramo'   		: recordFactura.get('CDRAMO')
				                ,'params.estado'   		: recordFactura.get('ESTADO')
				                ,'params.nmpoliza' 		: recordFactura.get('NMPOLIZA')
				                ,'params.nmsuplem' 		: recordFactura.get('NMSUPLEM')
				                ,'params.nmsituac' 		: recordFactura.get('NMSITUAC')
				                ,'params.aaapertu' 		: recordFactura.get('AAAPERTU')
				                ,'params.status'   		: recordFactura.get('STATUS')
				                ,'params.nmsinies' 		: recordFactura.get('NMSINIES')
				                ,'params.nfactura'		: panelInicialPral.down('[name=params.nfactura]').getValue()
				                ,'params.cdgarant' 		: record.get('CDGARANT')
				                ,'params.cdconval' 		: record.get('CDCONVAL')
				                ,'params.cdconcep' 		: record.get('CDCONCEP')
				                ,'params.idconcep' 		: record.get('IDCONCEP')
				                ,'params.nmordina' 		: record.get('NMORDINA')
				                ,'params.precio'   		: record.get('PTPRECIO')
				                ,'params.cantidad'      : record.get('CANTIDAD')
				                ,'params.descuentoporc' : record.get('DESTOPOR')
				                ,'params.descuentonum'  : record.get('DESTOIMP')
				                ,'params.importe'       : record.get('PTIMPORT')
				                ,'params.ajusteMedi'    : record.get('TOTAJUSMED')
				            },
				            scripts  : true,
				            loadMask : true,
				            autoLoad : true,
				            ajaxOptions: {
				            	method: 'POST'
				            }
				        }
					    ,
					    listeners:{
					         close:function(){
					             if(true){
					                 //Actualizamos la información de la consulta del grid inferior
					            	 storeConceptos.reload();
					             }
					         }
					    }
				    }).show();
				    centrarVentanaInterna(windowLoader);
			    }
			}
			
			function _guardarConceptosxFactura(){
			    var obtener = [];
			    storeConceptos.each(function(record) {
			        obtener.push(record.data);
			    });
			    if(obtener.length <= 0){
			        Ext.Msg.show({
			            title:'Error',
			            msg: 'Se requiere al menos un concepto',
			            buttons: Ext.Msg.OK,
			            icon: Ext.Msg.ERROR
			        });
			        storeConceptos.reload();
			        return false;
			    }else{
			        
			        for(i=0;i < obtener.length;i++){
			            if(obtener[i].IDCONCEP == null ||obtener[i].CDCONCEP == null ||obtener[i].PTMTOARA == null ||obtener[i].PTPRECIO == null ||obtener[i].CANTIDAD == null ||
			                obtener[i].IDCONCEP == "" ||obtener[i].CDCONCEP == "" ||obtener[i].PTMTOARA == ""||obtener[i].PTPRECIO == "" || obtener[i].CANTIDAD ==""){
			                centrarVentanaInterna(Ext.Msg.show({
			                    title:'Concepto',
			                    msg: 'Favor de introducir los campos requeridos en el concepto',
			                    buttons: Ext.Msg.OK,
			                    icon: Ext.Msg.WARNING
			                }));
			                return false;
			            }
			        }
			        var submitValues={};
			        var formulario=panelInicialPral.form.getValues();
			        submitValues['params']=formulario;
			        var datosTablas = [];
			        var recordFactura = gridFacturaDirecto.getSelectionModel().getSelection()[0];
			        debug(recordFactura);
			        storeConceptos.each(function(record,index){
			            datosTablas.push({
			            	cdunieco  : recordFactura.get('CDUNIECO')
			            	,cdramo   : recordFactura.get('CDRAMO')
			                ,estado   : recordFactura.get('ESTADO')
			                ,nmpoliza : recordFactura.get('NMPOLIZA')
			                ,nmsuplem : recordFactura.get('NMSUPLEM')
			                ,nmsituac : recordFactura.get('NMSITUAC')
			                ,aaapertu : recordFactura.get('AAAPERTU')
			                ,status   : recordFactura.get('STATUS')
			                ,nmsinies : recordFactura.get('NMSINIES')
			                ,nfactura : panelInicialPral.down('[name=params.nfactura]').getValue()
			                ,cdgarant : panelInicialPral.down('[name=params.cdgarant]').getValue()
			                ,cdconval : panelInicialPral.down('combo[name=params.cdconval]').getValue()
			                ,cdconcep : record.get('CDCONCEP')
			                ,idconcep : record.get('IDCONCEP')
			                ,cdcapita : record.get('CDCAPITA')
			                ,nmordina : record.get('NMORDINA')
			                ,cdmoneda : "001"
		                	,ptprecio : record.get('PTPRECIO')
			                ,cantidad : record.get('CANTIDAD')
			                ,destopor : record.get('DESTOPOR')
			                ,destoimp : record.get('DESTOIMP')
			                ,ptimport : record.get('SUBTAJUSTADO')
			                ,ptrecobr : record.get('PTRECOBR')
			                ,nmapunte : record.get('NMAPUNTE')
			                ,feregist : record.get('FEREGIST')
			                ,operacion: "I"
			                ,ptpcioex : "0.00"
			                ,dctoimex : "0.00"
			                ,ptimpoex : "0.0"
			                ,mtoArancel : record.get('PTMTOARA')
						});
					});
			        submitValues['datosTablas']=datosTablas;
			        debug("VALORES A ENVAR --> submitValues");
			        debug(submitValues);
			        
			        panelInicialPral.setLoading(true);
			        Ext.Ajax.request(
			        {
			            url: _URL_GUARDA_CONCEPTO_TRAMITE,
			            jsonData:Ext.encode(submitValues),
			            success:function(response,opts){
			                panelInicialPral.setLoading(false);
			                var jsonResp = Ext.decode(response.responseText);
			                if(jsonResp.success==true){
			                    panelInicialPral.setLoading(false);
			                    storeConceptos.reload();
			                }
			            },
			            failure:function(response,opts)
			            {
			                panelInicialPrincipal.setLoading(false);
			                Ext.Msg.show({
			                    title:'Error',
			                    msg: 'Error de comunicaci&oacute;n',
			                    buttons: Ext.Msg.OK,
			                    icon: Ext.Msg.ERROR
			                });
			            }
			        });
			    }
			}
			
			
			
			function _p21_agregarConcepto()
			{
				storeConceptos.add(new modelConceptos({PTPRECIO:'0.00'}));
			}
			
			function _11_asociarAutorizacion()
			{
				var valido = _11_formPedirAuto.isValid();
				if(!valido)
				{
					datosIncompletos();
				}

				if(valido)
				{
					var json =
					{
						'params.nmautser'  : _11_textfieldNmautserv.getValue()
						,'params.nmpoliza' : _11_recordActivo.get('NMPOLIZA')
						,'params.cdperson' : _11_recordActivo.get('CDPERSON')
						,'params.ntramite' : panelInicialPral.down('[name=params.ntramite]').getValue()
						,'params.nfactura' : panelInicialPral.down('[name=params.nfactura]').getValue()
						,'params.feocurrencia' : _11_recordActivo.get('FEOCURRE')
					};
					debug(json);
					_11_formPedirAuto.setLoading(true);
					_11_windowPedirAut.close();
					Ext.Ajax.request(
					{
						url      : _11_urlIniciarSiniestroTworksin
						,params  : json
						,success : function(response)
						{
							_11_formPedirAuto.setLoading(false);
							
							json = Ext.decode(response.responseText);
							if(json.success==true)
							{
								mensajeCorrecto('Autorizaci&oacute;n Servicio',json.mensaje,function(){
									storeAseguradoFactura.removeAll();
									storeAseguradoFactura.load({
								    	params: {
								    		'smap.ntramite'   : panelInicialPral.down('[name=params.ntramite]').getValue(),
								    		'smap.nfactura'   : panelInicialPral.down('[name=params.nfactura]').getValue()
								    	}
								    });
								});
							}
							else
							{
								mensajeError(json.mensaje);
							}
						}
						,failure : function()
						{
							_11_formPedirAuto.setLoading(false);
							errorComunicacion();
						}
					});
				}
			}
		//FIN DE FUNCIONES
			Ext.onReady(function()
			{
			/**INICIO DE COMPONENTES***/
				var storeTipoMoneda = Ext.create('Ext.data.JsonStore', {
					model:'Generic',
					proxy: {
						type: 'ajax',
						url: _URL_CATALOGOS,
						extraParams : {catalogo:_CATALOGO_TipoMoneda},
						reader: {
							type: 'json',
							root: 'lista'
						}
					}
				});
				storeTipoMoneda.load();
				var storeTipoMonedaNueva = Ext.create('Ext.data.JsonStore', {
					model:'Generic',
					proxy: {
						type: 'ajax',
						url: _URL_CATALOGOS,
						extraParams : {catalogo:_CATALOGO_TipoMoneda},
						reader: {
							type: 'json',
							root: 'lista'
						}
					}
				});
				storeTipoMonedaNueva.load();
				
				var cmbTipoMoneda = Ext.create('Ext.form.ComboBox',
				{
					name:'params.tipoMoneda',		fieldLabel	: 'MONEDA',		store: storeTipoMoneda,			queryMode:'local',  
					displayField: 'value',		valueField: 'key',			editable:false,					allowBlank:false
				});
				
				var cmbTipoMonedaNueva = Ext.create('Ext.form.ComboBox',
			    {
			        id:'cmbTipoMonedaNueva',	store: storeTipoMonedaNueva,value:'001',		queryMode:'local',
			        displayField: 'value',		valueField: 'key',			editable:false,		allowBlank:false
			    });
				
				
				var subCobertura = Ext.create('Ext.form.field.ComboBox',
			    {
			    	name:'params.cdconval',		fieldLabel : 'SUBCOBERTURA',	allowBlank: false,				displayField : 'value',			id:'idSubcobertura',
			    	valueField   : 'key',			forceSelection : true,			matchFieldWidth: false,
			    	queryMode :'remote',			store : storeSubcobertura,		triggerAction: 'all',			editable:false
			    });
				
				var cobertura = Ext.create('Ext.form.field.ComboBox',
			    {
			    	name:'params.cdgarant',			fieldLabel : 'COBERTURA',	allowBlank: false,				displayField : 'value',
			    	valueField   : 'key',			forceSelection : true,		matchFieldWidth: false,
			    	queryMode :'remote',			store : storeCobertura,		editable:false,
			    	listeners : {
			        	'change' : function(combo, record) {
				    		storeSubcobertura.removeAll();
				        	storeSubcobertura.load({
				                params:{
				                	'params.cdgarant' :this.getValue()
				                }
				            });
				        }
			        }
			    });
				
				//MODELOS
				
				Ext.define('modelFacturaSiniestroNva', {
				    extend:'Ext.data.Model',
				    fields:['noFacturaNva','fechaFacturaNva','tipoServicioNva','tipoServicioNameNva','proveedorNva',
							'proveedorNameNva','importeNva','tipoMonedaNva','tipoMonedaNameNva','tasaCambioNva','importeFacturaNva'
					]
				});
				
				storeFacturaDirectoNva =new Ext.data.Store(
				{
				    autoDestroy: true,
				    model: 'modelFacturaSiniestroNva'
				});
				
				Ext.define('modelAseguradosFactura',{
				extend: 'Ext.data.Model',
				fields: [	{type:'string',	name:'AAAPERTU'},		{type:'string',	name:'AUTMEDIC'},
							{type:'string',	name:'AUTRECLA'},		{type:'string',	name:'CDICD'},
							{type:'string',	name:'CDICD2'},			{type:'string',	name:'CDPERSON'},
							{type:'string',	name:'CDRAMO'},			{type:'string',	name:'CDTIPSIT'},
							{type:'string',	name:'CDUNIECO'},		{type:'string',	name:'COMMENAR'},
							{type:'string',	name:'COMMENME'},		{type:'string',	name:'COPAGO'},
							{type:'string',	name:'DESCNUME'},		{type:'string',	name:'DESCPORC'},
							{type:'string',	name:'DSICD'},			{type:'string',	name:'DSICD2'},
							{type:'string',	name:'DSRAMO'},			{type:'string',	name:'DSTIPSIT'},
							{type:'string',	name:'DSUNIECO'},		{type:'string',	name:'ESTADO'},
							{type:'string',	name:'FEOCURRE'},		{type:'string',	name:'NMAUTSER'},
							{type:'string',	name:'NMPOLIZA'},		{type:'string',	name:'NMRECLAMO'},
							{type:'string',	name:'NMSINIES'},		{type:'string',	name:'NMSITUAC'},
							{type:'string',	name:'NMSUPLEM'},		{type:'string',	name:'NOMBRE'},
							{type:'string',	name:'PTIMPORT'},		{type:'string',	name:'STATUS'},
							{type:'string',	name:'VOBOAUTO'}
						]
				});
				
				storeAseguradoFactura = Ext.create('Ext.data.Store',
				{
					autoLoad : false
					,model   : 'modelAseguradosFactura'
					,proxy   :
					{
						reader :
						{
							type  : 'json'
							,root : 'slist1'
						}
						,type  : 'ajax'
						,url   : _p12_urlObtenerSiniestrosTramite
				    }
				});
				
				/*Ext.define('EditorFacturaNuevaPDirecto', {
			 		extend: 'Ext.grid.Panel',
					name:'editorFacturaNuevaPDirecto',
			 		frame: true,
					selType  : 'rowmodel',
				 	initComponent: function(){
				 			Ext.apply(this, {
				 			//width: 750,
				 			height: 250,
				 			plugins  :
					        [
					            Ext.create('Ext.grid.plugin.CellEditing',
					            {
					                clicksToEdit: 1
					            })
					        ],
				 			store: storeFacturaDirectoNva,
				 			columns: 
				 			[
							 	{	
							 		header: 'No. de Factura',			dataIndex: 'noFacturaNva',			flex:2,  allowBlank: false
							 		,editor: {
							                xtype: 'textfield'
						            }
							 	},
							 	{
							 		header: 'Fecha de Factura',			dataIndex: 'fechaFacturaNva',		flex:2,			 	renderer: Ext.util.Format.dateRenderer('d/m/Y'),  allowBlank: false
							 		,editor : {
										xtype : 'datefield',
										format : 'd/m/Y',
										editable : true
									}
							 	},
							 	{
									header: 'Moneda', 				dataIndex: 'tipoMonedaNameNva',	flex:2,  allowBlank: false
									,editor : cmbTipoMonedaNueva
									,renderer : function(v) {
									var leyenda = '';
										if (typeof v == 'string')// tengo solo el indice
										{
											storeTipoMonedaNueva.each(function(rec) {
												if (rec.data.key == v) {
													leyenda = rec.data.value;
												}
											});
										}else // tengo objeto que puede venir como Generic u otro mas complejo
										{
											if (v.key && v.value)
											{
												leyenda = v.value;
											} else {
												leyenda = v.data.value;
											}
										}
										return leyenda;
									}
								}
							 	,
							 	{
								 	header: 'Tasa cambio', 				dataIndex: 'tasaCambioNva',	flex:2,				renderer: Ext.util.Format.usMoney,  allowBlank: false
								 	,editor: {
							                xtype: 'textfield'
						            }
							 	},
							 	{
								 	header: 'Importe Factura', 				dataIndex: 'importeFacturaNva',		 	flex:2,				renderer: Ext.util.Format.usMoney,  allowBlank: false
								 	,editor: {
							                xtype: 'textfield'
						            }
							 	},
							 	{
								 	header: 'Importe MXN', 					dataIndex: 'importeNva',		 	flex:2,				renderer: Ext.util.Format.usMoney,  allowBlank: false
								 	,editor: {
							                xtype: 'textfield'
						            }
							 	},
							 	{
								 	xtype: 'actioncolumn',
								 	width: 30,
								 	sortable: false,
								 	menuDisabled: true,
								 	items: [{
								 		icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/delete.png',
								 		tooltip: 'Quitar inciso',
								 		scope: this,
								 		handler: this.onRemoveClick
							 		}]
							 	}
					 		],
					 		selModel: {
						 		selType: 'cellmodel'
						 	},
					 		tbar: [{
			                    icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/add.png'
			                    ,handler : _p21_agregarGrupoClic
			                }]
					 	});
			 			this.callParent();
				 	},
				 	onRemoveClick: function(grid, rowIndex){
				 		var record=this.getStore().getAt(rowIndex);
				 		this.getStore().removeAt(rowIndex);
				 	}
			 	});
			    gridFacturaNuevaPDirecto = new EditorFacturaNuevaPDirecto();*/
			    
				Ext.define('EditorFacturaDirecto', {
					extend: 'Ext.grid.Panel',
					name:'editorFacturaDirecto',
					title: 'Asegurados',
					icon        : '${ctx}/resources/fam3icons/icons/user.png',
					frame: true,
					selModel: { selType: 'checkboxmodel', mode: 'SINGLE', checkOnly: true },
					initComponent: function(){
						Ext.apply(this, {
						//width: 850,
						height: 300,
						plugins  :
						[
							Ext.create('Ext.grid.plugin.CellEditing',{	clicksToEdit: 1	})
						],
						store: storeAseguradoFactura,
						columns: 
						[
							{
								xtype         : 'actioncolumn'
								,menuDisabled : true
								,width        : 70
								,items        :
								[
								    {
								    	icon     : '${ctx}/resources/fam3icons/icons/folder.png'
								    	,tooltip : 'Capturar Detalle'
								    	,handler : revisarDocumento
								    }/*,
								    {
										icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/delete.png',
										tooltip: 'Eliminar',
									 	scope: this,
									 	handler: this.onRemoveClick
								 	}*/
								]
							},
							{
								header: 'Id<br/>Sini.',				dataIndex: 'NMSINIES'
							},
							{
								header: '# Auto.',					dataIndex: 'NMAUTSER'
							},
							{
								header: 'Clave<br/>asegu.',			dataIndex: 'CDPERSON'
							},
							{
								header: 'Nombre<br/>Asegurado',		dataIndex: 'NOMBRE'
							},
							{
								header: 'Fecha<br/>Ocurrencia',		dataIndex: 'FEOCURRE'
							},
							{
								header: 'P&oacute;liza',			dataIndex: 'NMPOLIZA'
							},
							{
								header: 'Vo.Bo.<br/>Auto.',			dataIndex: 'VOBOAUTO',
								renderer        : function(v)
								{
									var r=v;
									if(v=='S'||v=='s')
									{
										r='SI';
									}
									else if(v=='N'||v=='n')
									{
										r='NO';
									}
									return r;
								}
							},
							{
								header: 'ICD<br/>Principal',		dataIndex: 'CDICD'
							},
							{
								header: 'ICD<br/>Secundario',		dataIndex: 'CDICD2'
							},
							{
								header: 'Copago',					dataIndex: 'COPAGO'
							},
							{
								header: '$<br/>Facturado',			dataIndex: 'PTIMPORT',			renderer       :Ext.util.Format.usMoney
							},
							{
								header: '#<br/>Reclamo',			dataIndex: 'NMRECLAMO'
							},
							{
								header: 'Tipsit',					dataIndex: 'DSTIPSIT',			hidden	:	true
							}
						],
						listeners: {
							select: function (grid, record, index, opts){
								var numSiniestro = record.get('NMSINIES');
								if(numSiniestro.length == "0"){
									revisarDocumento(grid,index)
									
								}else{
									storeConceptos.load({
										params: {
											'params.nfactura'  : panelInicialPral.down('[name=params.nfactura]').getValue(),
											'params.cdunieco'  : record.get('CDUNIECO'),
											'params.cdramo'    : record.get('CDRAMO'),
											'params.estado'    : record.get('ESTADO'),
											'params.nmpoliza'  : record.get('NMPOLIZA'),
											'params.nmsituac'  : record.get('NMSITUAC'),
											'params.nmsuplem'  : record.get('NMSUPLEM'),
											'params.status'    : record.get('STATUS'),
											'params.aaapertu'  : record.get('AAAPERTU'),
											'params.nmsinies'  : record.get('NMSINIES')
										}
									});
								}
							}
						}
					 });
						this.callParent();
					},
					onRemoveClick: function(grid, rowIndex){
						var record=this.getStore().getAt(rowIndex);
						this.getStore().removeAt(rowIndex);
					}
				});
				gridFacturaDirecto=new EditorFacturaDirecto();
				
				
				Ext.define('modelConceptos',{
					extend: 'Ext.data.Model',
					fields: [	
					         	{type:'string',	name:'CDUNIECO'},		{type:'string',	name:'CDRAMO'},			{type:'string',	name:'ESTADO'},
					         	{type:'string',	name:'NMPOLIZA'},		{type:'string',	name:'NMSUPLEM'},		{type:'string',	name:'NMSITUAC'},
					         	{type:'string',	name:'AAAPERTU'},		{type:'string',	name:'NMSINIES'},		{type:'string',	name:'NFACTURA'},
					         	{type:'string',	name:'STATUS'},			{type:'string',	name:'CDGARANT'},		{type:'string',	name:'DSGARANT'},
					         	{type:'string',	name:'CDCONVAL'},		{type:'string',	name:'DSSUBGAR'},		{type:'string',	name:'CDCONCEP'},
					         	{type:'string',	name:'DESCONCEP'},		{type:'string',	name:'IDCONCEP'},		{type:'string',	name:'DESIDCONCEP'},
					         	{type:'string',	name:'DESIDCONCEP'},	{type:'string',	name:'CDCAPITA'},		{type:'string',	name:'NMORDINA'},
					         	{type:'string',	name:'FEMOVIMI'},		{type:'string',	name:'CDMONEDA'},		{type:'string',	name:'PTPRECIO'},
					         	{type:'string',	name:'CANTIDAD'},		{type:'string',	name:'DESTOPOR'},		{type:'string',	name:'DESTOIMP'},
					         	{type:'string',	name:'PTIMPORT'},		{type:'string',	name:'PTRECOBR'},		{type:'string',	name:'NMANNO'},
					         	{type:'string',	name:'NMAPUNTE'},		{type:'string',	name:'USERREGI'},		{type:'string',	name:'FEREGIST'},
					         	{type:'string',	name:'PTPCIOEX'},		{type:'string',	name:'DCTOIMEX'},		{type:'string',	name:'PTIMPOEX'},
					         	{type:'string',	name:'PTMTOARA'},		{type:'string',	name:'TOTAJUSMED'},		{type:'string',	name:'SUBTAJUSTADO'}
							]
					});
					
					/*storeConceptos = Ext.create('Ext.data.Store',
					{
						autoLoad : false
						,model   : 'modelConceptos'
						,proxy   :
						{
							reader :
							{
								type  : 'json'
								,root : 'slist1'
							}
							,type  : 'ajax'
							,url   : _p12_urlObtenerSiniestrosTramite
					    }
					});*/
					
					storeConceptos=new Ext.data.Store(
					{
					    autoDestroy: true,
					    model: 'modelConceptos',
					    proxy: {
				            type: 'ajax',
				            url: _URL_LoadConceptos,
				            reader: {
				                type: 'json',
				                root: 'loadList'
				            }
				        }
					});
					
					
					var storeTipoConcepto = Ext.create('Ext.data.JsonStore', {
						model:'Generic',
						proxy: {
							type: 'ajax',
							url: _URL_CATALOGOS,
							extraParams : {catalogo:_CATALOGO_TipoConcepto},
							reader: {
								type: 'json',
								root: 'lista'
							}
						}
					});
					storeTipoConcepto.load();
					
					var storeConceptosCatalogo = Ext.create('Ext.data.JsonStore', {
						model:'Generic',
						proxy: {
							type: 'ajax',
							url: _URL_CATALOGOS,
							extraParams : {catalogo:_CATALOGO_ConceptosMedicos},
							reader: {
								type: 'json',
								root: 'lista'
							}
						}
					});
					
					cmbCveTipoConcepto = Ext.create('Ext.form.ComboBox',
					{
						name:'params.idconcep',		store: storeTipoConcepto,		queryMode:'local',
						displayField: 'value',		valueField: 'key',				editable:false,				allowBlank:false,
						listeners:{
							select: function (combo, records, opts){
								debug("VALOR DE COMBO -->");
								debug(combo);
								debug("VALOR DE RECORDS -->");
								debug(records);
								debug("VALOR DE OPTS -->");
								debug(opts);
								var cdTipo =  records[0].get('key');
								debug("CLAVE DE TIPO --> "+cdTipo);
								storeConceptosCatalogo.proxy.extraParams=
								{
									'params.idPadre' : cdTipo
									,catalogo        : _CATALOGO_ConceptosMedicos
								};
							}
						}
					});
					
					cmbCveConcepto = Ext.create('Ext.form.ComboBox',
					{
			        	name:'params.cdconcep',		store: storeConceptosCatalogo,		queryMode:'remote',
			        	displayField: 'value',		valueField: 'key',					editable:true,				allowBlank:false,
			            forceSelection: true,		queryParam  : 'params.descripc',	hideTrigger : true,			minChars    : 3
			            ,listeners : {
			   				select:function(e){
			   					debug("Proveedor -->"+panelInicialPral.down('[name=params.cdpresta]').getValue());
			   					debug("idConceptoTipo -->"+ e.getValue());
			   					
			   					Ext.Ajax.request(
								{
								    url     : _URL_MONTO_ARANCEL
								    ,params:{
										'params.tipoConcepto'   : _11_conceptoSeleccionado.get('IDCONCEP'),
										'params.idProveedor'    : panelInicialPral.down('[name=params.cdpresta]').getValue(),
			                            'params.idConceptoTipo' : e.getValue()
					                }
								    ,success : function (response)
								    {
								    	if(Ext.decode(response.responseText).montoArancel == null){
								    		_11_conceptoSeleccionado.set('PTMTOARA','0');
								    	}else{
								    		_11_conceptoSeleccionado.set('PTMTOARA',Ext.decode(response.responseText).montoArancel);
								    	}
								    },
								    failure : function ()
								    {
								        me.up().up().setLoading(false);
								        Ext.Msg.show({
								            title:'Error',
								            msg: 'Error de comunicaci&oacute;n',
								            buttons: Ext.Msg.OK,
								            icon: Ext.Msg.ERROR
								        });
								    }
								});
			   	    		}
			   	        }
			        });
					
					
					Ext.define('EditorConceptos', {
						extend: 'Ext.grid.Panel',
						name:'editorConceptos',
						title: 'Conceptos',
						icon        : '${ctx}/resources/fam3icons/icons/paste_plain.png',
						frame: true,
						selType  : 'rowmodel',
						initComponent: function(){
							Ext.apply(this, {
							//width: 850,
							height: 250,
							plugins  :
					        [
					            Ext.create('Ext.grid.plugin.CellEditing',
					            {
					                clicksToEdit: 1
					                ,listeners :
				                	{
				                	    beforeedit : function()
				                	    {
				                	    	_11_conceptoSeleccionado = gridEditorConceptos.getView().getSelectionModel().getSelection()[0];
				                	    	debug('_11_conceptoSeleccionado:',_11_conceptoSeleccionado);
				                	    }
				                	}
					            })
					        ],
							store: storeConceptos,
							columns: 
							[
								{
									xtype         : 'actioncolumn'
									,menuDisabled : true
									,width        : 70
									,items        :
									[
									    {
											icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/delete.png',
											tooltip: 'Eliminar',
										 	scope: this,
										 	handler: this.onRemoveClick
									 	}
									    ,{
									    	icon     : '${ctx}/resources/fam3icons/icons/page_edit.png'
									    	,tooltip : 'Ajuste'
									    	,handler : _mostrarVentanaAjustes
									    }
									]//,flex:1
								},
								
								{
				 					dataIndex : 'NMORDINA',
				 					width : 20,
				 					hidden: true
				 				},{
				 					header : 'Factura',
				 					dataIndex:  'NFACTURA',
				 					hidden: true
				 				},
				 				
				 				{
									header: 'Tipo Concepto', 				dataIndex: 'IDCONCEP',	width : 150		,  allowBlank: false
									,editor : cmbCveTipoConcepto
									,renderer : function(v) {
									var leyenda = '';
										if (typeof v == 'string')// tengo solo el indice
										{
											storeTipoConcepto.each(function(rec) {
												if (rec.data.key == v) {
													leyenda = rec.data.value;
												}
											});
										}else // tengo objeto que puede venir como Generic u otro mas complejo
										{
											if (v.key && v.value)
											{
												leyenda = v.value;
											} else {
												leyenda = v.data.value;
											}
										}
										return leyenda;
									}
								},
								{
									header: 'Codigo Concepto', 				dataIndex: 'CDCONCEP',	width : 150		,  allowBlank: false
									,editor : cmbCveConcepto
									,renderer : function(v) {
									var leyenda = '';
										if (typeof v == 'string')// tengo solo el indice
										{
											storeConceptosCatalogo.each(function(rec) {
												if (rec.data.key == v) {
													leyenda = rec.data.value;
												}
											});
										}else // tengo objeto que puede venir como Generic u otro mas complejo
										{
											if (v.key && v.value)
											{
												leyenda = v.value;
											} else {
												leyenda = v.data.value;
											}
										}
										return leyenda;
									}
								},
								{
									header: 'Valor Arancel', 				dataIndex: 'PTMTOARA',	width : 150,				renderer: Ext.util.Format.usMoney
									,editor: {
										xtype: 'numberfield',
										decimalSeparator :'.',
										allowBlank: false
									}
								},
				 				{
									header: 'Precio Concepto', 				dataIndex: 'PTPRECIO',	width : 150,				renderer: Ext.util.Format.usMoney
									,editor: {
										xtype: 'numberfield',
										decimalSeparator :'.',
										allowBlank: false,
										listeners : {
											change:function(e){
												var cantidad = _11_conceptoSeleccionado.get('CANTIDAD');
												var importe = e.getValue();
												var destopor = _11_conceptoSeleccionado.get('DESTOPOR');
												var destoimp = _11_conceptoSeleccionado.get('DESTOIMP');
												var ImporteConcepto = ((+cantidad * +importe) * (1-( +destopor/100)))- (+destoimp);
												_11_conceptoSeleccionado.set('PTIMPORT',ImporteConcepto);
												
												var totalAjusteMedico = _11_conceptoSeleccionado.get('TOTAJUSMED');
												var totalFactura = _11_conceptoSeleccionado.get('PTIMPORT');
												var Total = +totalFactura - (+totalAjusteMedico);
												_11_conceptoSeleccionado.set('SUBTAJUSTADO',ImporteConcepto);
											}
										}
									}
								}
								,
								{
									header: 'Cantidad', 				dataIndex: 'CANTIDAD',		width : 150//,				renderer: Ext.util.Format.usMoney
									,editor: {
										xtype: 'numberfield',
										allowBlank: false,
										listeners : {
											change:function(e){
												var cantidad = e.getValue();
												var importe = _11_conceptoSeleccionado.get('PTPRECIO');
												var destopor = _11_conceptoSeleccionado.get('DESTOPOR');
												var destoimp = _11_conceptoSeleccionado.get('DESTOIMP');
												var ImporteConcepto = ((+cantidad * +importe) *(1-( +destopor/100)))- (+destoimp);
												_11_conceptoSeleccionado.set('PTIMPORT',ImporteConcepto);
												
												var totalAjusteMedico = _11_conceptoSeleccionado.get('TOTAJUSMED');
												var totalFactura = _11_conceptoSeleccionado.get('PTIMPORT');
												var Total = +totalFactura - (+totalAjusteMedico);
												_11_conceptoSeleccionado.set('SUBTAJUSTADO',ImporteConcepto);
											}
										}
									}
								},
								{
				 					header : 'Descuento (%)',			dataIndex : 'DESTOPOR',		width : 150
				 					,editor: {
										xtype: 'numberfield',
										decimalSeparator :'.',
										allowBlank: false,
										listeners : {
											change:function(e){
												var cantidad = _11_conceptoSeleccionado.get('CANTIDAD');
												var importe = _11_conceptoSeleccionado.get('PTPRECIO');
												var destopor = e.getValue();
												var destoimp = _11_conceptoSeleccionado.get('DESTOIMP');
												var ImporteConcepto = ((+cantidad * +importe) *(1-( +destopor/100)))- (+destoimp);
												_11_conceptoSeleccionado.set('PTIMPORT',ImporteConcepto);
												
												var totalAjusteMedico = _11_conceptoSeleccionado.get('TOTAJUSMED');
												var totalFactura = _11_conceptoSeleccionado.get('PTIMPORT');
												var Total = +totalFactura - (+totalAjusteMedico);
												_11_conceptoSeleccionado.set('SUBTAJUSTADO',ImporteConcepto);
											}
										}
									}
				 				},
				 				{
				 					header : 'Descuento ($)',			dataIndex : 'DESTOIMP',		width : 150
				 					,editor: {
										xtype: 'numberfield',
										decimalSeparator :'.',
										allowBlank: false,
										listeners : {
											change:function(e){
												var cantidad = _11_conceptoSeleccionado.get('CANTIDAD');
												var importe = _11_conceptoSeleccionado.get('PTPRECIO');
												var destopor = _11_conceptoSeleccionado.get('DESTOPOR');
												var destoimp = e.getValue();
												var ImporteConcepto = ((+cantidad * +importe) *(1-( +destopor/100)))- (+destoimp);
												_11_conceptoSeleccionado.set('PTIMPORT',ImporteConcepto);
												
												var totalAjusteMedico = _11_conceptoSeleccionado.get('TOTAJUSMED');
												var totalFactura = _11_conceptoSeleccionado.get('PTIMPORT');
												var Total = +totalFactura - (+totalAjusteMedico);
												_11_conceptoSeleccionado.set('SUBTAJUSTADO',ImporteConcepto);
											}
										}
									}
				 				},{
				 					header : 'Ajuste M&eacute;dico',
				 					dataIndex : 'TOTAJUSMED',
				 					width : 150,
				 					renderer : Ext.util.Format.usMoney
				 				},{
				 					header : 'Subtotal Factura',
				 					dataIndex : 'PTIMPORT',
				 					width : 150,
				 					renderer : Ext.util.Format.usMoney
				 				},{
				 					header : 'Subtotal Ajustado',
				 					dataIndex : 'SUBTAJUSTADO',
				 					width : 150,
				 					renderer : Ext.util.Format.usMoney
				 				}
							],
							selModel: {
						 		selType: 'cellmodel'
						 	},
							tbar:[
									{
										text	: 'Agregar Concepto'
										,icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/book.png'
										,handler : _p21_agregarConcepto
									},
									{
										text	: 'Guardar Concepto'
										,icon:_CONTEXT+'/resources/fam3icons/icons/disk.png'
										,handler : function() {
											_guardarConceptosxFactura();
										}
									}
								]
						 });
							this.callParent();
						}
						,onRemoveClick: function(grid, rowIndex){
							/*Eliminamos el record del store*/
							var record=this.getStore().getAt(rowIndex);
							this.getStore().removeAt(rowIndex);
							_guardarConceptosxFactura();
						}
					});
					gridEditorConceptos = new EditorConceptos();
				
				
					Ext.define('modelListadoAutorizacion',{
				        extend: 'Ext.data.Model',
				        fields: [  	{type:'string',    name:'NMAUTSER'},       		{type:'string',    name:'CDPROVEE'},
									{type:'string',    name:'FESOLICI'},			{type:'string',    name:'NOMPROV'}]
				    });
					
					storeListadoAutorizacion = new Ext.data.Store(
				    {
				    	pageSize : 5
				        ,model      : 'modelListadoAutorizacion'
				        ,autoLoad  : false
				        ,proxy     :
				        {
				            enablePaging : true,
				            reader       : 'json',
				            type         : 'memory',
				            data         : []
				        }
				    });
					gridAutorizacion= Ext.create('Ext.grid.Panel',
					{
						id             : 'clausulasGridId'
						,store         :  storeListadoAutorizacion
						//,collapsible   : true
						//,titleCollapse : true
						,style         : 'margin:5px'
						,selType: 'checkboxmodel'
						,width   : 600
						,height: 200
						,selModel: { selType: 'checkboxmodel', mode: 'SINGLE', checkOnly: true }
						,columns       :
						[
							 {
								 header     : 'N&uacute;mero <br/> Autorizaci&oacute;n'
								 ,dataIndex : 'NMAUTSER'
								 ,width	 	: 100
							 },
							 {
								 header     : 'Fecha Solicitud'
								 ,dataIndex : 'FESOLICI'
								 ,width     : 100
							 }
							 ,
							 {
								 header     : 'clave Proveedor'
								 ,dataIndex : 'CDPROVEE'
								 ,width     : 100
							 }
							 ,
							 {
								 header     : 'Nombre Proveedor'
								 ,dataIndex : 'NOMPROV'
								 ,width     : 300
							 }
						 ],
						 bbar     :
						 {
							 displayInfo : true,
							 store       : storeListadoAutorizacion,
							 xtype       : 'pagingtoolbar'
						 },
						//aqui va el listener
						listeners: {
					        itemclick: function(dv, record, item, index, e) {
					        	debug("VaLOR DEL RECORD");
					        	
					        	_11_textfieldNmautserv.setValue(record.get('NMAUTSER'));
								debug(record);
					        }
					    }
						
							
					});
					gridAutorizacion.store.sort([
				        {
				        	property    : 'nmautser',			direction   : 'DESC'
				        }
				    ]);
				panelInicialPral= Ext.create('Ext.form.Panel',
				{
					border    : 0
		            ,layout     :
					{
						type     : 'table'
						,columns : 2
					}
		            ,defaults 	:
					{
						style : 'margin:5px;'
					}
					,items    :
					[
						{
							xtype		: 'textfield',			fieldLabel	: 'N0. TR&Aacute;MITE',		name	: 'params.ntramite'
						},
						{
							xtype		: 'textfield',			fieldLabel	: 'PROVEEDOR',		name	: 'params.cdpresta',		hidden:true
						},
						{
							xtype		: 'textfield',			fieldLabel	: 'TIP SERVICIO',		name	: 'params.cdtipser',		hidden:true
						},
						{
							xtype		: 'textfield',			fieldLabel	: 'NO. FACTURA',			name	: 'params.nfactura'
						},
						{
			            	xtype		: 'datefield',			fieldLabel	: 'FECHA FACTURA',			name	: 'params.fefactura',	format	: 'd/m/Y'
			            },
			            cobertura,
						subCobertura,
						cmbTipoMoneda,
						{
							xtype		: 'numberfield',		fieldLabel 	: 'IMPORTE MXN',			name	: 'params.ptimport',
							allowBlank	: false,				allowDecimals :true	,					decimalSeparator :'.'
						},
						{
							xtype		: 'numberfield',		fieldLabel 	: 'TASA CAMBIO',			name	: 'params.tasacamb',
							allowBlank	: false,				allowDecimals :true	,					decimalSeparator :'.'
						},
						{
							xtype		: 'numberfield',		fieldLabel 	: 'IMPORTE FACTURA',		name	: 'params.ptimporta',
							allowBlank	: false,				allowDecimals :true	,					decimalSeparator :'.'
						},
						{
							xtype		: 'numberfield',		fieldLabel 	: 'DESCUENTO %',			name	: 'params.descporc',
							allowBlank	: false,				allowDecimals :true	,					decimalSeparator :'.'
						},
						{
							xtype		: 'numberfield',		fieldLabel 	: 'DESCUENTO $',			name	: 'params.descnume',
							allowBlank	: false,				allowDecimals :true	,					decimalSeparator :'.'
						}
						<s:property value='%{"," + imap.tatrisinItems}' />
						<s:property value='%{"," + imap.itemsEdicion}' />
					]
				});
				for(var i=0;i<panelInicialPral.items.items.length;i++)
				{
					panelInicialPral.items.items[i].labelWidth =150;
					panelInicialPral.items.items[i].style      = 'margin-right:100px;';
				}
				
				
				modPolizasAltaTramite = Ext.create('Ext.window.Window',
				{
				    title        : 'Detalle Factura'
				    ,modal       : true
				    ,resizable   : false
				    ,buttonAlign : 'center'
				    ,closable    : true
				    ,closeAction: 'hide'
				    ,width		 : 900
				    ,items       : 
			        [
						panelInicialPral
						,gridFacturaDirecto
						,gridEditorConceptos
			        ],
					buttonAlign:'center',
					buttons: [
						{
							text:'Guardar Fac',
							icon:_CONTEXT+'/resources/fam3icons/icons/disk.png',
							handler:function()
							{
								
								var valido = panelInicialPral.isValid();
								if(!valido)
								{
									datosIncompletos();
								}else{
									var autorizaRecla = panelInicialPral.down('[name="params.autrecla"]').getValue()+"";
									var autorizaMedic = panelInicialPral.down('[name="params.autmedic"]').getValue()+"";
									if(autorizaRecla == "null" || autorizaRecla == null){
										autorizaRecla="S";
									}
									if(autorizaMedic == "null" || autorizaMedic == null){
										autorizaMedic="S";
									}
									
									var valido =  autorizaRecla =='S' &&  autorizaMedic!='N' ;
									if(!valido)
									{
										mensajeWarning(
												'El tr&aacute;mite de pago directo ser&aacute; cancelado debido a que no ha sido autorizado alguno de los siniestros'
												,function(){_11_windowRechazoSiniestro.show();centrarVentanaInterna(_11_windowRechazoSiniestro);}
										);
									}else{
										//Guardamos la información de la factura
										//windowLoader.close();
										debug("VALOR  ENVIAR ");
										debug(panelInicialPral.form);
										panelInicialPral.form.submit({
			            		        	waitMsg:'Procesando...',	
			            		        	url: _URL_GuardaFactura,
			            		        	failure: function(form, action) {
			            		        		centrarVentanaInterna(mensajeError("Verifica los datos requeridos"));
			            					},
			            					success: function(form, action) {
			            						centrarVentanaInterna(mensajeCorrecto('Guardar Cambios',"Se ha guardado la Factura",function()
		           								{
		           									Ext.create('Ext.form.Panel').submit(
		           									{
		           										standardSubmit :true
		           										,params        :
		           										{
		           											'params.ntramite' : panelInicialPral.down('[name=params.ntramite]').getValue()
		           										}
		           									});
		           								}));
			            					}
			            				});
									}
								}
							}
						},
						{
							text:'Cancelar',
							icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
							handler:function()
							{
								modPolizasAltaTramite.close();
							}
						}
					]
				});
			
				/*modAgregarFacturas = Ext.create('Ext.window.Window',
				{
				    title        : 'Facturas nuevas'
				    ,modal       : true
				    ,resizable   : false
				    ,buttonAlign : 'center'
				    ,closable    : true
				    ,closeAction: 'hide'
				    ,width		 : 700
				    ,items       :
			        [
						gridFacturaNuevaPDirecto
			        ]
					,buttonAlign:'center',
					buttons: [
						{
							text:'Guardar',
							icon:_CONTEXT+'/resources/fam3icons/icons/disk.png',
							handler:function()
							{
								storeFacturaDirectoNva.removeAll();
								modAgregarFacturas.close();
							}
						},
						{
							text:'Cancelar',
							icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
							handler:function()
							{
								storeFacturaDirectoNva.removeAll();
								modAgregarFacturas.close();
							}
						}
					]
				});*/
					
				Ext.define('_11_WindowRechazoSiniestro',
			    {
			        extend         : 'Ext.window.Window'
			        ,initComponent : function()
			        {
			            debug('_11_WindowRechazoSiniestro initComponent');
			            Ext.apply(this,
			            {
			                title        : 'Rechazo de tr&aacute;mite'
			                ,width       : 600
			                ,height      : 350
			                ,autoScroll  : true
			                ,closeAction : 'hide'
			                ,modal       : true
			                ,defaults    : { style : 'margin : 5px; ' }
			                ,items       : _11_formRechazo
			                ,buttonAlign : 'center'
			                ,buttons     :
			                [
			                    {
			                        text     : 'Rechazar'
			                        ,icon    : '${ctx}/resources/fam3icons/icons/delete.png'
			                        ,handler : _11_rechazoSiniestro
			                    }
			                ]
			            });
			            this.callParent();
			        }
			    });
			
				Ext.define('_11_WindowPedirAut',
				{
					extend         : 'Ext.window.Window'
					,initComponent : function()
					{
						debug('_11_windowPedirAut initComponent');
						Ext.apply(this,
						{
							title        : 'Autorizaci&oacute;n de servicios'
							,icon        : '${ctx}/resources/fam3icons/icons/tick.png'
							//,width       : 350
							//,height      : 200
							,closeAction : 'hide'
							,modal       : true
							,defaults    : { style : 'margin : 5px; ' }
							,items       : _11_formPedirAuto
							,buttonAlign : 'center'
							,buttons     :
							[
								{
									text     : 'Asociar autorizaci&oacute;n'
									,icon    : '${ctx}/resources/fam3icons/icons/disk.png'
									,handler : _11_asociarAutorizacion
								}
							]
						});
						this.callParent();
					}
				});
				Ext.define('_11_FormPedirAuto',
				{
					extend         : 'Ext.form.Panel'
					,initComponent : function()
					{
						debug('_11_FormPedirAuto initComponent');
						Ext.apply(this,
						{
							border : 0
							,items :
							[
								{
									xtype : 'label'
									,text : 'Se requiere el número de autorización para continuar'
								}
								,_11_textfieldAsegurado
								,_11_textfieldNmautserv
								,gridAutorizacion
							]
						});
						this.callParent();
					}
				});
				
				Ext.define('_11_FormRechazo',
			    {
			        extend         : 'Ext.form.Panel'
			        ,initComponent : function()
			        {
			            debug('_11_FormRechazo initComponent');
			            Ext.apply(this,
			            {
			                border  : 0
			                ,items  : _11_itemsRechazo
			            });
			            this.callParent();
			        }
			    });
			/**FIN DE COMPONENTES***/
			
			/**INICIO DE CONTENIDO***/
				_11_textfieldAsegurado = Ext.create('Ext.form.TextField',
				{
					fieldLabel : 'Asegurado'
					,readOnly  : true
				});
				_11_textfieldNmautserv = Ext.create('Ext.form.NumberField',
			    {
			        fieldLabel  : 'No. de autorizaci&oacute;n'
			        ,readOnly   : false
			        ,allowBlank : false
			        ,hidden : true
			        ,minLength  : 1
			    });
						
				_11_formPedirAuto  = new _11_FormPedirAuto();
				_11_windowPedirAut = new _11_WindowPedirAut();
				_11_formRechazo            = new _11_FormRechazo();
				_11_windowRechazoSiniestro = new _11_WindowRechazoSiniestro();
				
			/**FIN DE CONTENIDO***/
			});
		</script>
		<script type="text/javascript" src="${ctx}/js/proceso/siniestros/afiliadosAfectados.js?${now}"></script>
		<script>
			Ext.onReady(function(){

			});
		</script>
	</head>
	<body>
		<div style="height:2000px;">
			<div id="div_clau"></div>
			<div id="divResultados" style="margin-top:10px;"></div>
		</div>
	</body>
</html>