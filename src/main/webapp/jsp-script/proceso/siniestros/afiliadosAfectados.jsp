<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Afiliados Afectados</title>
		<script type="text/javascript">
			var _CONTEXT 								= '${ctx}';
			var _11_params								= <s:property value="%{convertToJSON('params')}" escapeHtml="false" />;
			var numAutServ								= null;
			//Llamados MC
			var _URL_MESACONTROL						= '<s:url namespace="/mesacontrol" 		action="mcdinamica" />';
			var _URL_ACTSTATUS_TRAMITE 					= '<s:url namespace="/mesacontrol" 		action="actualizarStatusTramite" />';
			var _URL_FINDETALLEMC 						= '<s:url namespace="/mesacontrol" 		action="finalizarDetalleTramiteMC" />';
            var _URL_ActualizaStatusTramite 			= '<s:url namespace="/mesacontrol" 		action="actualizarStatusTramite" />';
			var _URL_TurnarAOperadorReclamacion 		= '<s:url namespace="/mesacontrol" 		action="turnarAOperadorReclamacion" />';
			var _URL_DETALLEMC        					= '<s:url namespace="/mesacontrol" 		action="obtenerDetallesTramite" />';
            var panDocUrlViewDoc     					= '<s:url namespace="/documentos" 		action="descargaDocInline" />';
			var _CDROL									= '<s:property value="params.cdrol" />';
            //Catalogos
			var _URL_CATALOGOS							= '<s:url namespace="/catalogos" 		action="obtieneCatalogo"/>';
			var _CATALOGO_TIPOMONEDA					= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TIPO_MONEDA"/>';
			var _CATALOGO_COBERTURASTOTALES 			= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@COBERTURASTOTALES"/>';
			var _CATALOGO_SUBCOBERTURASTOTALES 			= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@SUBCOBERTURASTOTALES"/>';
			var _CATALOGO_VALIDACIONESGRALES            = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@VALIDACIONESGRALES"/>';
			var _CATALOGO_SUBCOBERTURASTOTALESMS 		= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@SUBCOBERTURAS4MS"/>';
			var _CATALOGO_SUBCOBERTURASTOTALESGMPI      = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@SUBCOBERTURASGMPI"/>';
			var _CATALOGO_SUBCOBERTURASTOTALESGMPC      = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@SUBCOBERTURASGMPC"/>';
			var _CATALOGO_SUBCOBERTURASTOTALESMSC		= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@SUBCOBERTURAS4MSC"/>';
			var _CATALOGO_SUBCOBERTURASTOTALINFONAVIT	= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@SUBCOBERTURASINFONAVIT"/>';
			var _CATALOGO_SUBCOBERTURASRECUPERA			= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@SUBCOBERTURASRECUPERA"/>';
			var _CATALOGO_COB_X_VALORES 				= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@COBERTURASXVALORES"/>';
			var _CATALOGO_TIPOCONCEPTO					= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TIPO_CONCEPTO_SINIESTROS"/>';
			var _CATALOGO_CONCEPTOSMEDICOS				= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@CODIGOS_MEDICOS"/>';
			var _CATALOGO_CONCEPTOSMEDICOSTOTALES   	= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@CODIGOS_MEDICOS_TOTALES"/>';
			var _CAT_CAUSASINIESTRO						= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@CAUSA_SINIESTRO"/>';
			var _CAT_DESTINOPAGO                        = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@DESTINOPAGO"/>';
            var _CAT_CONCEPTO                           = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@CATCONCEPTO"/>';
            var _CATALOGO_PROVEEDORES  					= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@PROVEEDORES"/>';
            var _CATALOGO_CONCEPTOPAGO					= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@CONCEPTOPAGO"/>';
			var _SINO									= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@SINO" />';
			//Roles
			var _ROL_MEDICO								= '<s:property value="@mx.com.gseguros.portal.general.util.RolSistema@MEDICO_AJUSTADOR.cdsisrol" />';
			var _ROL_MESASINIESTRO						= '<s:property value="@mx.com.gseguros.portal.general.util.RolSistema@MESA_DE_CONTROL_SINIESTROS.cdsisrol" />';
			var _ROL_COORD_MEDICO						= '<s:property value="@mx.com.gseguros.portal.general.util.RolSistema@GERENTE_MEDICO_MULTIREGIONAL.cdsisrol" />';
			var _OPERADOR_REC							= '<s:property value="@mx.com.gseguros.portal.general.util.RolSistema@OPERADOR_SINIESTROS.cdsisrol" />';
			var _COORDINADOR_REC						= '<s:property value="@mx.com.gseguros.portal.general.util.RolSistema@COORDINADOR_SINIESTROS.cdsisrol" />';
			var _GERENTE_REC							= '<s:property value="@mx.com.gseguros.portal.general.util.RolSistema@GERENTE_OPERACION_SINIESTROS.cdsisrol"/>'; // (EGS)
			//Tipo de pagos
			var _TIPO_PAGO_DIRECTO						= '<s:property value="@mx.com.gseguros.portal.general.util.TipoPago@DIRECTO.codigo"/>';
			var _TIPO_PAGO_REEMBOLSO					= '<s:property value="@mx.com.gseguros.portal.general.util.TipoPago@REEMBOLSO.codigo"/>';
			var _TIPO_PAGO_INDEMNIZACION				= '<s:property value="@mx.com.gseguros.portal.general.util.TipoPago@INDEMNIZACION.codigo"/>';
			//Estatus Tramite
			var _STATUS_DEVOLVER_TRAMITE				= '<s:property value="@mx.com.gseguros.portal.general.util.EstatusTramite@TRAMITE_EN_DEVOLUCION.codigo" />';
            var _RECHAZADO								= '<s:property value="@mx.com.gseguros.portal.general.util.EstatusTramite@RECHAZADO.codigo" />';
            var _STATUS_TRAMITE_EN_ESPERA_DE_ASIGNACION = '<s:property value="@mx.com.gseguros.portal.general.util.EstatusTramite@EN_ESPERA_DE_ASIGNACION.codigo" />';
          	var _STATUS_TRAMITE_EN_REVISION_MEDICA  	= '<s:property value="@mx.com.gseguros.portal.general.util.EstatusTramite@EN_REVISION_MEDICA.codigo" />';
            var _STATUS_TRAMITE_CONFIRMADO              = '<s:property value="@mx.com.gseguros.portal.general.util.EstatusTramite@CONFIRMADO.codigo" />';
            var _STATUS_TRAMITE_EN_CAPTURA              = '<s:property value="@mx.com.gseguros.portal.general.util.EstatusTramite@EN_CAPTURA.codigo" />';
            var _STATUS_TRAMITE_PENDIENTE				= '<s:property value="@mx.com.gseguros.portal.general.util.EstatusTramite@PENDIENTE.codigo" />'; // (EGS)
			//Ramos
            var _SALUD_VITAL							= '<s:property value="@mx.com.gseguros.portal.general.util.Ramo@SALUD_VITAL.cdramo" />';
            var _MULTISALUD								= '<s:property value="@mx.com.gseguros.portal.general.util.Ramo@MULTISALUD.cdramo" />';
            var _GMMI									= '<s:property value="@mx.com.gseguros.portal.general.util.Ramo@GASTOS_MEDICOS_MAYORES.cdramo" />';
            var _RECUPERA								= '<s:property value="@mx.com.gseguros.portal.general.util.Ramo@RECUPERA.cdramo" />';
            var _GMPRUEBA                               = '<s:property value="@mx.com.gseguros.portal.general.util.Ramo@GASTOS_MEDICOS_MAYORES_PRUEBA.cdramo" />';
            //cdtiptra
            var _TIPO_TRAMITE_SINIESTRO					= '<s:property value="@mx.com.gseguros.portal.general.util.TipoTramite@SINIESTRO.cdtiptra"/>';
            var _TIPO_PAGO_AUTOMATICO					= '<s:property value="@mx.com.gseguros.portal.general.util.TipoTramite@PAGO_AUTOMATICO.cdtiptra"/>';
			//documentos			
			var _URL_DOCUMENTOSPOLIZA					= '<s:url namespace="/documentos" 		action="ventanaDocumentosPoliza" />';
			//Llamados Siniestros
			var _11_URL_REQUIEREAUTSERV					= '<s:url namespace="/siniestros" 		action="obtieneRequiereAutServ" />';
			var _11_URL_INICIARSINIESTROSINAUTSERV		= '<s:url namespace="/siniestros"  		action="generarSiniestrosinAutServ" />';
			var _11_URL_INICIARSINIESTROTWORKSIN		= '<s:url namespace="/siniestros"  		action="iniciarSiniestroTworksin" />';
			var _URL_LISTA_COBERTURA 					= '<s:url namespace="/siniestros"  		action="consultaListaCoberturaPoliza" />';
			var _URL_LISTA_SUBCOBERTURA					= '<s:url namespace="/siniestros"  		action="consultaListaSubcobertura" />';
			var _URL_CONCEPTODESTINO        			= '<s:url namespace="/siniestros"       action="guardarConceptoDestino" />';
            var _URL_SOLICITARPAGO           			= '<s:url namespace="/siniestros" 		action="solicitarPago" />';
            var _URL_VALIDAR_PROVEEDOR_PD				= '<s:url namespace="/siniestros"		action="validarProveedorPD" />'; // (EGS)
            var _URL_TIPO_ATENCION						= '<s:url namespace="/siniestros"  		action="consultaListaTipoAtencion" />';
            var _URL_CONSULTA_TRAMITE       			= '<s:url namespace="/siniestros"       action="consultaListadoMesaControl" />';
          	var _URL_LISTARECHAZOS						= '<s:url namespace="/siniestros"		action="loadListaRechazos" />';
            var _URL_LISTAINCISOSRECHAZOS				= '<s:url namespace="/siniestros"		action="loadListaIncisosRechazos" />';
			var _URL_LISTA_CPTICD						= '<s:url namespace="/siniestros"  		action="consultaListaCPTICD" />';
            var _URL_LISTA_ICD							= '<s:url namespace="/siniestros"  		action="consultaListaICD" />'; // (EGS)
			var _URL_GENERAR_CALCULO					= '<s:url namespace="/siniestros" 		action="generarCalculoSiniestros" />';
			var _URL_CONCEPTOSASEG						= '<s:url namespace="/siniestros" 		action="obtenerMsinival" />';
			var _URL_LISTADO_ASEGURADO          		= '<s:url namespace="/siniestros"       action="consultaListaAsegurado" />';
			var _URL_CONSULTA_LISTADO_POLIZA			= '<s:url namespace="/siniestros" 		action="consultaListaPolizaFeOcu" />';//(EGS) cambiamos consultaListaPoliza
			var _URL_ASOCIA_MSINEST_REFERENCIADO    	= '<s:url namespace="/siniestros"      	action="asociaMsiniestroReferenciado" />';
			var _URL_ACTUALIZA_INFO_GRAL_SIN       		= '<s:url namespace="/siniestros"      	action="actualizaDatosGeneralesSiniestro" />';
			var _URL_GUARDA_CONCEPTO_TRAMITE			= '<s:url namespace="/siniestros"  		action="guardarMsinival"/>';
			var _URL_MONTO_ARANCEL						= '<s:url namespace="/siniestros"		action="obtieneMontoArancel"/>';
			var _URL_LISTA_AUTSERVICIO					= '<s:url namespace="/siniestros" 		action="consultaAutServicioSiniestro"/>';
			var _URL_LISTA_MSINIESTRO					= '<s:url namespace="/siniestros" 		action="consultaSiniestroMaestro" />';
			var _URL_DATOS_VALIDACION					= '<s:url namespace="/siniestros" 		action="consultaDatosValidacionSiniestro" />';
			var _URL_GUARDA_CAMBIOS_FACTURA				= '<s:url namespace="/siniestros" 		action="guardaFacturaTramite" />';
			var _URL_VAL_AJUSTADOR_MEDICO				= '<s:url namespace="/siniestros" 		action="consultaDatosValidacionAjustadorMed" />';
			var _URL_GUARDA_ASEGURADO					= '<s:url namespace="/siniestros" 		action="guardaaseguradoUnico" />';
			var _URL_OBTENERSINIESTROSTRAMITE			= '<s:url namespace="/siniestros"  		action="obtenerSiniestrosTramite" />';
			var _URL_OBTENER_SUMAASEGURADA				= '<s:url namespace="/siniestros"  		action="consultaDatosSumaAsegurada" />';
			var _URL_RECHAZOTRAMITE  					= '<s:url namespace="/siniestros" 		action="includes/rechazoReclamaciones" />';
			var _URL_AJUSTESMEDICOS						= '<s:url namespace="/siniestros" 		action="includes/ajustesMedicos" />';
            var _URL_ELIMINAR_ASEGURADO					= '<s:url namespace="/siniestros"		action="eliminarAsegurado" />';
			var _VER_ALTA_FACTURAS 						= '<s:url namespace="/siniestros"  		action="includes/altaFacturas" />';
			var _URL_GUARDA_FACTURAS_TRAMITE  			= '<s:url namespace="/siniestros"       action="guardaFacturasTramite" />';
			var _URL_ELIMINAR_FACT_ASEG					= '<s:url namespace="/siniestros" 		action="eliminarFactAsegurado" />';
			var _URL_MONTO_PAGO_SINIESTRO				= '<s:url namespace="/siniestros"		action="obtieneMontoPagoSiniestro"/>';
			var _URL_TABBEDPANEL						= '<s:url namespace="/siniestros"  		action="includes/detalleSiniestro" />';
			var _URL_CONSULTA_AUTORIZACION_ESP			= '<s:url namespace="/siniestros"		action="consultaAutorizacionServicio" />';
			var _URL_LISTADO_ASEGURADO_POLIZA			= '<s:url namespace="/siniestros"       action="consultaListaAseguradoPoliza" />';
			var _URL_CONSULTA_BENEFICIARIO				= '<s:url namespace="/siniestros"		action="consultaDatosBeneficiario" />';
			var _URL_APLICA_IVA_CONCEPTO				= '<s:url namespace="/siniestros"		action="obtieneAplicacionIVA"/>';
			var _URL_MRECUPERA							= '<s:url namespace="/siniestros" 		action="obtenerMRecupera" />';
			var _URL_SUMASEG_RECUPERA					= '<s:url namespace="/siniestros"		action="obtieneDatosRecupera"/>';
			var _URL_GUARDA_CONCEPTO_RECUPERA			= '<s:url namespace="/siniestros"  		action="guardarMRecupera"/>';
			var _URL_LOADER_HISTORIAL_CPTPAGADO 		= '<s:url namespace="/siniestros"		action="includes/historialCPTPagados" />';
			var _URL_VALIDA_AUTESPECIFICA				= '<s:url namespace="/siniestros"		action="validaAutorizacionEspecial"/>';
			var _URL_INF_AUT_ESPECIFICO					= '<s:url namespace="/siniestros" 		action="consultaDatosAutorizacionEspecial" />';
			var _URL_EXISTE_COBERTURA					= '<s:url namespace="/siniestros" 		action="consultaExisteCoberturaTramite" />';
			var _11_urlActualizarSiniestro        		= '<s:url namespace="/siniestros"  		action="actualizarMultiSiniestro"      />';
			var _URL_VALIDA_FACTMONTO					= '<s:url namespace="/siniestros" 		action="validaFacturaMontoPagoAutomatico" />';
			var _UrlGeneraSiniestroTramite 				= '<s:url namespace="/siniestros" 		action="generaSiniestroTramite" />';
			var _URL_VAL_CAUSASINI			        	= '<s:url namespace="/siniestros" 	   	action="consultaInfCausaSiniestroProducto" />';
			var _URL_VALIDACION_CONSULTA          		= '<s:url namespace="/siniestros" 		action="obtenerInfCoberturaInfonavit"/>';
			var _URL_VAL_CONDICIONGRAL		  			= '<s:url namespace="/siniestros" 		action="consultaInfCausaSiniestroProducto" />';
			var _URL_GENERARCARTARECHAZO				= '<s:url namespace="/siniestros"		action="generaCartaRechazo" />';
            var _URL_NOMBRE_TURNADO   					= '<s:url namespace="/siniestros" 		action="obtieneUsuarioTurnado" />';
            var _URL_ACTUALIZA_TURNADOMC				= '<s:url namespace="/siniestros" 		action="actualizaTurnadoMesaControl" />';
			var _URL_P_MOV_MAUTSINI						= '<s:url namespace="/siniestros"		action="obtieneMensajeMautSini"/>';
			var _URL_VALIDADOCCARGADOS					= '<s:url namespace="/siniestros" 		action="validaDocumentosCargados"/>';
            var _UrlGenerarContrarecibo     			= '<s:url namespace="/siniestros" 		action="generarContrarecibo"       />';
			var _URL_VALIDA_COBASEGURADOS				= '<s:url namespace="/siniestros" 		action="validaLimiteCoberturaAsegurados"/>';
			var _URL_VALIDA_STATUSASEG			        = '<s:url namespace="/siniestros" 	   	action="validaStatusAseguradoSeleccionado" />';
			var _URL_VALIDA_IMP_ASEGSINIESTRO			= '<s:url namespace="/siniestros" 	   	action="validaImporteAsegTramiteAseg" />';
			var _URL_VALIDA_IMPASEGURADOSINIESTRO		= '<s:url namespace="/siniestros" 		action="validaImporteTramiteAsegurados"/>';
			var _URL_LISTA_TIPOEVENTO                   = '<s:url namespace="/siniestros"       action="consultaListaTipoEventoSiniestro" />';
			var _URL_ALTA_EVENTO                        = '<s:url namespace="/siniestros"       action="consultaDatosTipoEventoAlta" />';
			var _URL_REQAUTES							= '<s:url namespace="/siniestros"		action="actualizarReqautes" />'; // (EGS) para marcar afiliados que excedan limite de medicamentos
			var _URL_REVISIONDOCSINIESTRO               = '<s:url namespace="/siniestros"       action="includes/revisionDocumentos" />';
			
			var _UrlConsultaDatosEstudiosCobAsegurado = '<s:url namespace="/siniestros"    action="obtieneDatosEstCobAseg" />';
			var _UrlCatTiposResultado = '<s:url namespace="/siniestros"    action="obtieneTiposResultadoEstudio" />';
			var _UrlCatTiposResultadoTodos = '<s:url namespace="/siniestros"    action="obtieneTiposResultadoTodos" />';
			var _UrlValidaCapturaEstudiosCobertura = '<s:url namespace="/siniestros"    action="validaRequiereCapturaResEstudios" />';
			var _UrlActualizaEliminaEstudiosCobertura = '<s:url namespace="/siniestros"    action="actualizaEliminaEstudiosCobAseg" />';
			var _UrlValidaDatosEstudiosReclamacion = '<s:url namespace="/siniestros"    action="validaDatosEstudiosReclamacion" />';
			
			var _11_itemsForm	= [
				<s:property value="imap.itemsForm" />
				,{
					xtype	: 'label'
					,hidden	: true
				} ];
			
			var _11_itemsRechazo = [ <s:property value="imap.itemsCancelar" /> ];
			_11_itemsRechazo[2]['width']  = 500;
			_11_itemsRechazo[2]['height'] = 150;
			
			//Asignaciones
			var _tipoPago         	= _11_params.OTVALOR02;
			var _tipoProducto    	= _11_params.CDRAMO;
			var _cdtipsitProducto 	= _11_params.CDTIPSIT
			var _cdtipAtencion    	= _11_params.OTVALOR26
			var _cdtipoProceso    	= _11_params.OTVALOR25
			var banderaConcepto 	= "0";
			var banderaAsegurado 	= "0";
			var recordsStoreFactura = [];
			var _fenacimi			= "";	// (EGS)
			var _genero				= "";	// (EGS)
			var _edad				= "";	// (EGS)
			var _11_fueraVigencia	= "0";	// (EGS)
			
			//iteramos el arreglos de loas Facturas para hacer la asignacion
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
					,dctoNuex:		'<s:property value='%{getSlist2().get(#contadorFactura).get("DCTONUEX")}'			escapeHtml="false" />'
					,feegreso:		'<s:property value='%{getSlist2().get(#contadorFactura).get("FEEGRESO")}'			escapeHtml="false" />'
					,diasdedu:		'<s:property value='%{getSlist2().get(#contadorFactura).get("DIASDEDU")}'			escapeHtml="false" />'
					,contraRecibo:	'<s:property value='%{getSlist2().get(#contadorFactura).get("CONTRARECIBO")}'		escapeHtml="false" />'
					,totalpagar:	'<s:property value='%{getSlist2().get(#contadorFactura).get("TOTALPAGAR")}'			escapeHtml="false" />'
					,swisr:			'<s:property value='%{getSlist2().get(#contadorFactura).get("SWISR")}'				escapeHtml="false" />'
					,swice:			'<s:property value='%{getSlist2().get(#contadorFactura).get("SWICE")}'				escapeHtml="false" />'
				});
				<s:set name="contadorFactura" value="#contadorFactura+1" />
			</s:iterator>

			var _11_columnas_Factura = [
				{
					xtype			: 'actioncolumn'
					,menuDisabled	: true
					,width			: 100
					,align			: 'center'
					,items			:
					[
						{
							icon		: '${ctx}/resources/fam3icons/icons/pencil.png'
							,tooltip	: 'Factura'
							,handler	: _11_editar
						},
						{
							icon		: '${ctx}/resources/fam3icons/icons/delete.png'
							,tooltip	: 'Eliminar Factura'
							,handler	: _11_eliminarFactura
						}
					]
				},{
					text	:'ContraRecibo',			dataIndex	:'contraRecibo',		hidden : true
				},{
					text	:'Reclamaci&oacute;n',		dataIndex	:'reclamacion'
				},{
					text	:'Factura',					dataIndex	:'factura'
				},{
					text	:'Fecha Factura',			dataIndex	:'fechaFactura',	format	:'d/m/Y',			xtype	:'datecolumn'
				},{
					text	:'Cobertura',				dataIndex	:'descCdgarant'
				},{
					text	:'Subcobertura',			dataIndex	:'dssubgar'
				},{
					text	:'Proveedor',				dataIndex	:'nomProveedor'
				},{
					text	:'Moneda',					dataIndex	:'descMoneda'
				},{
					text	:'Importe MXN',				dataIndex	:'ptimport',		renderer: Ext.util.Format.usMoney
				},{
					text	:'Total Pagar',				dataIndex	:'totalpagar',		renderer: Ext.util.Format.usMoney
				},{
					text	:'Desc. %',					dataIndex	:'desctoPorc'
				},{
					text	:'Desc. $',					dataIndex	:'desctoNum',		renderer: Ext.util.Format.usMoney
				},{
					text	:'tasaCambio',				dataIndex	:'tasaCambio',		renderer: Ext.util.Format.usMoney
				},{
					text	:'Importe Factura',			dataIndex	:'ptimporta',		renderer: Ext.util.Format.usMoney
				},{
					text	:'Fecha Egreso',			dataIndex	:'feegreso'
				},{
					text	:'Dias Deducible',			dataIndex	:'diasdedu'	,		hidden : _tipoPago != _TIPO_PAGO_INDEMNIZACION
				},{
					text	:'swisr',					dataIndex	:'swisr',		hidden : true
				},{
					text	:'swice',					dataIndex	:'swice',		hidden : true
				}
			];

			Ext.onReady(function() {
				
				/*############################		MODEL		########################################*/
				Ext.define('modelAseguradosFactura',{
					extend: 'Ext.data.Model',
					fields: [
						{type:'string',	name:'AAAPERTU'},		{type:'string',	name:'AUTMEDIC'},
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
						{type:'string',	name:'VOBOAUTO'},		{type:'string',	name:'CDCAUSA'},
						{type:'string',	name:'CDGARANT'},		{type:'string',	name:'CDCONVAL'},
						{type:'string',	name:'NMSINREF'},		{type:'string',	name:'IMPORTEASEG'},
						{type:'string',	name:'PTIVAASEG'},		{type:'string',	name:'PTIVARETASEG'},
						{type:'string',	name:'PTISRASEG'},		{type:'string',	name:'PTIMPCEDASEG'},
						{type:'string',	name:'DEDUCIBLE'},		{type:'string',	name:'IMPORTETOTALPAGO'},
						{type:'string',	name:'COMPLEMENTO'},	{type:'string',	name:'REQAUTES'},
						{type:'string',	name:'NMAUTESP'},		{type:'string',	name:'REQAUTESPECIAL'},
						{type:'string',	name:'VALTOTALCOB'},	{type:'string',	name:'LIMITE'},
						{type:'string',	name:'IMPPAGCOB'},		{type:'string',	name:'NMCALLCENTER'},
						{type:'string',	name:'SECTWORKSIN'},    {type:'string', name:'SWFONSIN'},
						{type:'string', name:'GENERO'},			{type:'string', name:'FENACIMI'},	// (EGS)
						{type:'date',   name:'FEINGRESO',  dateFormat : 'd/m/Y'},
						{type:'date',   name:'FEEGRESO',   dateFormat : 'd/m/Y'},
						{type:'string', name:'CDTIPEVE'},       {type:'string', name:'CDTIPALT'},
						{type:'string', name:'FLAGTIPEVE'},     {type:'string', name:'FLAGTIPALT'},
						{type:'string', name:'NUMRECLA'},       {type:'string', name:'FLAGREQAUT'}
					]
				});
//MODELO DE LOS CONCEPTOS
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
						{type:'string',	name:'PTMTOARA'},		{type:'string',	name:'TOTAJUSMED'},		{type:'string',	name:'SUBTAJUSTADO'},
						{type:'string',	name:'APLICIVA'},		{type:'string',	name:'PTIVA'}
					]
				});

//MODELO DE RECUPERA
				Ext.define('modelRecupera',{
					extend: 'Ext.data.Model',
					fields: [
						{type:'string',	name:'NTRAMITE'},		{type:'string',	name:'NFACTURA'},		{type:'string',	name:'CDGARANT'},
						{type:'string',	name:'CDCONVAL'},		{type:'string',	name:'CANTPORC'},		{type:'string',	name:'PTIMPORT'},
						{type:'string',	name:'SUMAASEG'},		{type:'string',	name:'ESQUEMAASEG'}
					]
				});
				
//MODELO DEL LISTADO DE LAS COBERTURAS
				Ext.define('modelListadoCobertura',{
					extend: 'Ext.data.Model',
					fields: [	{type:'string',    name:'cdgarant'},			{type:'string',    name:'dsgarant'},              	{type:'string',    name:'ptcapita'}		]
				});
				
//MODELO DEL LISTADO DE LAS AUTORIZACIONES DE SERVICIO
				Ext.define('modelListadoAutorizacion',{
					extend: 'Ext.data.Model',
					fields: [  	{type:'string',	name:'NMAUTSER'},	   		{type:'string',	name:'CDPROVEE'},
								{type:'string',	name:'FESOLICI'},			{type:'string',	name:'NOMPROV'},
								{type:'string',	name:'CDCAUSA'},			{type:'string',	name:'DSCAUSA'},
								{type:'string',	name:'CDICD'},				{type:'string',	name:'DSICD'}]
				});
//MODELO PARA EL LISTADO DE LA ASOCIACION
				Ext.define('modelListadoSiniestroMaestro',{
					extend: 'Ext.data.Model',
					fields: [  	{type:'string',	name:'NMSINIES'},	   		{type:'string',	name:'CDCAUSA'},
								{type:'string',	name:'DSCAUSA'},			{type:'string',	name:'CDICD'},
								{type:'string',	name:'DSICD'},	   			{type:'string',	name:'FEOCURRE'},
								{type:'string',	name:'FEAPERTU'}]
				});
//MODELO PARA LA OBTENER LA INFORMACION DE LA POLIZA
				Ext.define('modelListadoPoliza',{
				    extend: 'Ext.data.Model',
				    fields: [	{type:'string',    name:'cdramo'},				{type:'string',    name:'cdunieco'},				{type:'string',    name:'estado'},
								{type:'string',    name:'nmpoliza'},			{type:'string',    name:'nmsituac'},				{type:'string',    name:'mtoBase'},
								{type:'string',    name:'feinicio'},			{type:'string',    name:'fefinal'},					{type:'string',    name:'dssucursal'},
								{type:'string',    name:'dsramo'},				{type:'string',    name:'estatus'},					{type:'string',    name:'dsestatus'},
								{type:'string',    name:'nmsolici'},			{type:'string',    name:'nmsuplem'},				{type:'string',    name:'cdtipsit'},
								{type:'string',    name:'dsestatus'},			{type:'string',    name:'vigenciaPoliza'},			{type:'string',    name:'faltaAsegurado'},
								{type:'string',    name:'fcancelacionAfiliado'},{type:'string',    name:'desEstatusCliente'},		{type:'string',    name:'numPoliza'}	]
				});
				
				Ext.define('modeloRechazos',{
					extend: 'Ext.data.Model',
					fields: [{type:'string',    name:'key'},	{type:'string',    name:'value'}]
				});
				
				Ext.define('modeloIncisosRechazos',{
					extend: 'Ext.data.Model',
					fields: [{type:'string',    name:'key'},	{type:'string',    name:'value'}]
				});
				
				Ext.define('DetalleMC',{
			        extend:'Ext.data.Model',
			        fields:
			        [
			            "NTRAMITE",			"NMORDINA",			"CDTIPTRA",			"CDCLAUSU",
		            	"COMMENTS",			"CDUSUARI_INI",		"CDUSUARI_FIN",		"usuario_ini",
	            		"usuario_fin",
		            	{name:"FECHAINI",type:'date',dateFormat:'d/m/Y H:i'},
	            		{name:"FECHAFIN",type:'date',dateFormat:'d/m/Y H:i'}	            		
			        ]
			    });
				
				Ext.define('modelListadoProvMedico',{
					extend: 'Ext.data.Model',
						fields: [
								{type:'string',		name:'cdpresta'},	{type:'string', name:'nombre'},		{type:'string', name:'cdespeci'},
								{type:'string',		name:'descesp'}
					]
				});
				/*############################		STORE		########################################*/
				storeTipoAtencion = Ext.create('Ext.data.Store', {
					model:'Generic',
					autoLoad:true,
					proxy: {
						type: 'ajax',
						url:_URL_TIPO_ATENCION,
						reader: {
							type: 'json',
							root: 'listaTipoAtencion'
						}
					}
				});
				
				storeProveedor = Ext.create('Ext.data.Store', {
					model:'modelListadoProvMedico',
					autoLoad:false,
					proxy: {
						type: 'ajax',
						url : _URL_CATALOGOS,
						extraParams: {
							catalogo         : _CATALOGO_PROVEEDORES,
							catalogoGenerico : true
						},
						reader: {
							type: 'json',
							root: 'listaGenerica'
						}
					}
				});
			//STORE DE LAS COBERTURAS X VALORES
				storeCobertura = Ext.create('Ext.data.Store', {
					model:'Generic',
					autoLoad:false,
					proxy: {
						type: 'ajax',
						url : _URL_CATALOGOS,
						extraParams : {catalogo:_CATALOGO_COB_X_VALORES},
						reader: {
							type: 'json',
							root: 'lista'
						}
					}
				});
//STORE DE LAS SUBCOBERTURAS X VALORES
				storeSubcobertura= Ext.create('Ext.data.Store', {
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
//STORE DE COBERTURAS PARA LOS ASEGURADOS
				storeCoberturaxAsegurado = Ext.create('Ext.data.Store', {
					model:'modelListadoCobertura',
					autoLoad:false,
					proxy: {
						type: 'ajax',
						url : _URL_LISTA_COBERTURA,
						reader: {
							type: 'json',
							root: 'listaCoberturaPoliza'
						}
					}
				});
				
//STORE DE COBERTURAS PARA LOS ASEGURADOS RECUPERA
				storeCoberturaRecupera = Ext.create('Ext.data.Store', {
					model:'modelListadoCobertura',
					autoLoad:false,
					proxy: {
						type: 'ajax',
						url : _URL_LISTA_COBERTURA,
						reader: {
							type: 'json',
							root: 'listaCoberturaPoliza'
						}
					}
				});
				
				sstoreCoberturaRecuperaRender = Ext.create('Ext.data.JsonStore', {
					model:'Generic',
					cargado:false,
					proxy: {
						type: 'ajax',
						url: _URL_CATALOGOS,
						extraParams : {catalogo:_CATALOGO_COBERTURASTOTALES},
						reader: {
							type: 'json',
							root: 'lista'
						}
					},listeners: {
						load : function(){
							this.cargado=true;
							if(!Ext.isEmpty(gridFacturaDirecto)){
								gridFacturaDirecto.getView().refresh();
							}
						}
					}
				});
				sstoreCoberturaRecuperaRender.load();
				
//STORE DE SUBCOBERTURAS PARA LOS ASEGURADOS
				storeSubcoberturaAsegurado= Ext.create('Ext.data.Store', {
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

//STORE DE SUBCOBERTURAS PARA RECUPERA
				storeSubcoberturaRecupera= Ext.create('Ext.data.Store', {
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
				
				storeCoberturaxAseguradoRender = Ext.create('Ext.data.JsonStore', {
					model:'Generic',
					//autoLoad:true,
					cargado:false,
					proxy: {
						type: 'ajax',
						url: _URL_CATALOGOS,
						extraParams : {catalogo:_CATALOGO_COBERTURASTOTALES},
						reader: {
							type: 'json',
							root: 'lista'
						}
					},listeners: {
						load : function(){
							this.cargado=true;
							if(!Ext.isEmpty(gridFacturaDirecto)){
								gridFacturaDirecto.getView().refresh();
							}
						}
					}
				});
				storeCoberturaxAseguradoRender.load();
				
				storeSubcoberturaAseguradoRender = Ext.create('Ext.data.JsonStore', {
					model:'Generic',
					//autoLoad:true,
					cargado:false,
					proxy: {
						type: 'ajax',
						url: _URL_CATALOGOS,
						extraParams : {catalogo:_CATALOGO_SUBCOBERTURASTOTALES},
						reader: {
							type: 'json',
							root: 'lista'
						}
					},listeners: {
						load : function() {
							this.cargado=true;
							if(!Ext.isEmpty(gridFacturaDirecto)){
								gridFacturaDirecto.getView().refresh();
							}
						}
					}
				});
				storeSubcoberturaAseguradoRender.load();
				
				storeSubcoberturaAsegurado4MSRender = Ext.create('Ext.data.JsonStore', {
					model:'Generic',
					//autoLoad:true,
					cargado:false,
					proxy: {
						type: 'ajax',
						url: _URL_CATALOGOS,
						extraParams : {catalogo:_CATALOGO_SUBCOBERTURASTOTALESMS},
						reader: {
							type: 'json',
							root: 'lista'
						}
					},listeners: {
						load : function() {
							this.cargado=true;
							if(!Ext.isEmpty(gridFacturaDirecto)){
								gridFacturaDirecto.getView().refresh();
							}
						}
					}
				});
				storeSubcoberturaAsegurado4MSRender.load();
				
				storeSubcoberturaAsegurado4MSCRender = Ext.create('Ext.data.JsonStore', {
					model:'Generic',
					//autoLoad:true,
					cargado:false,
					proxy: {
						type: 'ajax',
						url: _URL_CATALOGOS,
						extraParams : {catalogo:_CATALOGO_SUBCOBERTURASTOTALESMSC},
						reader: {
							type: 'json',
							root: 'lista'
						}
					},listeners: {
						load : function() {
							this.cargado=true;
							if(!Ext.isEmpty(gridFacturaDirecto)){
								gridFacturaDirecto.getView().refresh();
							}
						}
					}
				});
				storeSubcoberturaAsegurado4MSCRender.load();
				
                storeSubcoberturaAseguradoGMPCRender = Ext.create('Ext.data.JsonStore', {
                    model:'Generic',
                    //autoLoad:true,
                    cargado:false,
                    proxy: {
                        type: 'ajax',
                        url: _URL_CATALOGOS,
                        extraParams : {catalogo:_CATALOGO_SUBCOBERTURASTOTALESGMPC},
                        reader: {
                            type: 'json',
                            root: 'lista'
                        }
                    },listeners: {
                        load : function() {
                            this.cargado=true;
                            if(!Ext.isEmpty(gridFacturaDirecto)){
                                gridFacturaDirecto.getView().refresh();
                            }
                        }
                    }
                });
                storeSubcoberturaAseguradoGMPCRender.load();
                
                storeSubcoberturaAseguradoGMPIRender = Ext.create('Ext.data.JsonStore', {
                    model:'Generic',
                    //autoLoad:true,
                    cargado:false,
                    proxy: {
                        type: 'ajax',
                        url: _URL_CATALOGOS,
                        extraParams : {catalogo:_CATALOGO_SUBCOBERTURASTOTALESGMPI},
                        reader: {
                            type: 'json',
                            root: 'lista'
                        }
                    },listeners: {
                        load : function() {
                            this.cargado=true;
                            if(!Ext.isEmpty(gridFacturaDirecto)){
                                gridFacturaDirecto.getView().refresh();
                            }
                        }
                    }
                });
                storeSubcoberturaAseguradoGMPIRender.load();
				
				storeSubcoberturaAsegurado4INFORender = Ext.create('Ext.data.JsonStore', {
					model:'Generic',
					//autoLoad:true,
					cargado:false,
					proxy: {
						type: 'ajax',
						url: _URL_CATALOGOS,
						extraParams : {catalogo:_CATALOGO_SUBCOBERTURASTOTALINFONAVIT},
						reader: {
							type: 'json',
							root: 'lista'
						}
					},listeners: {
						load : function() {
							this.cargado=true;
							if(!Ext.isEmpty(gridFacturaDirecto)){
								gridFacturaDirecto.getView().refresh();
							}
						}
					}
				});
				storeSubcoberturaAsegurado4INFORender.load();
				
				storeSubcoberturaRecuperaRender = Ext.create('Ext.data.JsonStore', {
					model:'Generic',
					cargado:false,
					proxy: {
						type: 'ajax',
						url: _URL_CATALOGOS,
						extraParams : {catalogo:_CATALOGO_SUBCOBERTURASRECUPERA},
						reader: {
							type: 'json',
							root: 'lista'
						}
					},listeners: {
						load : function() {
							this.cargado=true;
							if(!Ext.isEmpty(gridFacturaDirecto)){
								gridFacturaDirecto.getView().refresh();
							}
						}
					}
				});
				storeSubcoberturaRecuperaRender.load();
				
// STORE PARA EL LISTADO DE LAS AUTORIZACIONES DE SERVICIO
				storeListadoAutorizacion = new Ext.data.Store({
					pageSize : 10
					,model	  : 'modelListadoAutorizacion'
					,autoLoad  : false
					,proxy	 : {
						enablePaging : true,
						reader	   : 'json',
						type		 : 'memory',
						data		 : []
					}
				});
// STORE PARA EL LISTADO SINIESTRO MAESTRO
				storeListadoSiniestMaestro = new Ext.data.Store({
					pageSize  : 5
					,model	  : 'modelListadoSiniestroMaestro'
					,autoLoad : false
					,proxy	  : {
						enablePaging : true,
						reader	   : 'json',
						type		 : 'memory',
						data		 : []
					}
				});
// STORE PARA EL LISTADO DE LA CAUSA DEL SINIESTRO
				var storeCausaSinestro = Ext.create('Ext.data.JsonStore', {
					model:'Generic',
					proxy: {
						type: 'ajax',
						url: _URL_CATALOGOS,
						extraParams : {catalogo:_CAT_CAUSASINIESTRO},
						reader: {
							type: 'json',
							root: 'lista'
						}
					}
				});
				storeCausaSinestro.load();
// STORE PARA LA OBTENCION DEL TIPO DE EVENTO
                var storeTiposEvento = Ext.create('Ext.data.JsonStore', {
                    model:'Generic',
                    proxy: {
                        type: 'ajax',
                        url: _URL_LISTA_TIPOEVENTO,
                        reader: {
                            type: 'json',
                            root: 'datosValidacionGral'
                        }
                    },
                    listeners: {
                        'beforeload' : function(store, operation) {
                            store.removeAll();
                            store.proxy.extraParams = {
                                'params.cdramo'         : _tipoProducto,
                                'params.cdtipsit'       : _cdtipsitProducto,
                                'params.cdgarant'       : _edad,
                                'params.cveCatalogo'    : _genero
                            };
                        }
                    }
                });
                
                var storeTiposEventoRender = Ext.create('Ext.data.JsonStore', {
                    model:'Generic',
                    cargado:false,
                    proxy: {
                        type: 'ajax',
                        url: _URL_CATALOGOS,
                        extraParams : {catalogo:_CATALOGO_VALIDACIONESGRALES},
                        reader: {
                            type: 'json',
                            root: 'lista'
                        }
                    },listeners: {
                        load : function(){
                            this.cargado=true;
                            if(!Ext.isEmpty(gridFacturaDirecto)){
                                gridFacturaDirecto.getView().refresh();
                            }
                        }
                    }
                });
                storeTiposEventoRender.load();
                /*var storeTiposEventoRender = Ext.create('Ext.data.JsonStore', {
                    model:'Generic',
                    autoLoad:true,
                    cargado:false,
                    proxy: {
                        type: 'ajax',
                        url: _URL_LISTA_TIPOEVENTO,
                        reader: {
                            type: 'json',
                            root: 'datosValidacionGral'
                        }
                    }
                    ,listeners: {
                        'beforeload' : function(store, operation) {
                            store.removeAll();
                            store.proxy.extraParams = {
                                'params.cdramo'        : "4",
                                'params.cdtipsit'      : "MS",
                                'params.cdgarant'      : "4HOS",
                                'params.cveCatalogo'   : "1"
                            };
                        },
                        load : function() {
                            this.cargado=true;
                            if(!Ext.isEmpty(gridFacturaDirecto)){
                                gridFacturaDirecto.getView().refresh();
                            }
                        }
                    }
                });*/
                
                
                
// STORE PARA LA OBTENCION DEL TIPO DE EVENTO
                var storeAltasHospital = Ext.create('Ext.data.JsonStore', {
                    model:'Generic',
                    proxy: {
                        type: 'ajax',
                        url: _URL_LISTA_TIPOEVENTO,
                        reader: {
                            type: 'json',
                            root: 'datosValidacionGral'
                        }
                    },
                    listeners: {
                        'beforeload' : function(store, operation) {
                            store.removeAll();
                            store.proxy.extraParams = {
                                'params.cdramo'         : _tipoProducto,
                                'params.cdtipsit'       : _cdtipsitProducto,
                                'params.cdgarant'       : _edad,
                                'params.cveCatalogo'    : "2"
                            };
                        }
                    }
                });
                
                var storeAltasHospitalRender = Ext.create('Ext.data.JsonStore', {
                    model:'Generic',
                    cargado:false,
                    proxy: {
                        type: 'ajax',
                        url: _URL_CATALOGOS,
                        extraParams : {catalogo:_CATALOGO_VALIDACIONESGRALES},
                        reader: {
                            type: 'json',
                            root: 'lista'
                        }
                    },listeners: {
                        load : function(){
                            this.cargado=true;
                            if(!Ext.isEmpty(gridFacturaDirecto)){
                                gridFacturaDirecto.getView().refresh();
                            }
                        }
                    }
                });
                storeAltasHospitalRender.load();
                
// STORE PARA EL ICD PRINCIPAL
				var storeTiposICDPrimario = Ext.create('Ext.data.JsonStore', {
					model:'Generic',
					proxy: {
						type: 'ajax',
						//url: _URL_LISTA_CPTICD, // (EGS)
						url: _URL_LISTA_ICD,
						//extraParams : {'params.cdtabla' : '2TABLICD'}, // (EGS)
						reader: {
							type: 'json',
							root: 'listaCPTICD'
						}
					},
					listeners: { // se agrega listener (EGS)
						'beforeload' : function(store, operation) {
							store.removeAll();
							store.proxy.extraParams = {
								'params.cdramo' 	: _tipoProducto,
								'params.cdtipsit' 	: _cdtipsitProducto,
								'params.edad' 		: _edad,
								'params.genero' 	: _genero
							};
						}
					}
				});
				//storeTiposICDPrimario.load();	// (EGS)
				var storeTiposICDPrimarioRender = Ext.create('Ext.data.JsonStore', {
					model:'Generic',
					autoLoad:true,
					cargado:false,
					proxy: {
						type: 'ajax',
						//url: _URL_LISTA_CPTICD, // (EGS)
						url: _URL_LISTA_ICD, // (EGS)
						//extraParams : {'params.cdtabla' : '2TABLICD'},	// (EGS)
						reader: {
							type: 'json',
							root: 'listaCPTICD'
						}
					}
					,listeners: {
						'beforeload' : function(store, operation) { // (EGS)
							store.removeAll();
							store.proxy.extraParams = {
								'params.cdramo' 	: _tipoProducto,
								'params.cdtipsit' 	: _cdtipsitProducto,
								'params.edad' 		: _edad,
								'params.genero' 	: _genero
							};
						},
						load : function() {
							this.cargado=true;
							if(!Ext.isEmpty(gridFacturaDirecto)){
								gridFacturaDirecto.getView().refresh();
							}
						}
					}
				});
				
// STORE PARA EL ICD SECUNDARIO
				var storeTiposICDSecundario = Ext.create('Ext.data.JsonStore', {
					model:'Generic',
					proxy: {
						type: 'ajax',
						//url: _URL_LISTA_CPTICD,	// (EGS)
						url: _URL_LISTA_ICD,	// (EGS)
						//extraParams : {'params.cdtabla' : '2TABLICD'},	// (EGS)
						reader: {
							type: 'json',
							root: 'listaCPTICD'
						}
					},
					listeners: {	// se agrega listener (EGS)
						'beforeload' : function(store,operation){
							store.removeAll();
							store.proxy.extraParams = {
								'params.cdramo'		: _tipoProducto,
								'params.cdtipsit'	: _cdtipsitProducto,
								'params.edad'		: _edad,
								'params.genero'		: _genero
							};
						}
					}
				});
				//storeTiposICDSecundario.load();	// (EGS)
				var storeTiposICDSecundarioRender = Ext.create('Ext.data.JsonStore', {
					model:'Generic',
					autoLoad:true,
					cargado:false,
					proxy: {
						type: 'ajax',
						//url: _URL_LISTA_CPTICD,	// (EGS)
						url: _URL_LISTA_ICD, // (EGS)
						//extraParams : {'params.cdtabla' : '2TABLICD'},	// (EGS)
						reader: {
							type: 'json',
							root: 'listaCPTICD'
						}
					}
					,listeners: {
						'beforeload' : function(store,operation){	// (EGS)
							store.removeAll();
							store.proxy.extraParams = {
								'params.cdramo'		: _tipoProducto,
								'params.cdtipsit'	: _cdtipsitProducto,
								'params.edad'		: _edad,
								'params.genero'		: _genero
							};
						},
						load : function() {
							this.cargado=true;
							if(!Ext.isEmpty(gridFacturaDirecto)){
								gridFacturaDirecto.getView().refresh();
							}
						}
					}
				});
// STORE TIPO DE MONEDA
				var storeTipoMoneda = Ext.create('Ext.data.JsonStore', {
					model:'Generic',
					proxy: {
						type: 'ajax',
						url: _URL_CATALOGOS,
						extraParams : {catalogo:_CATALOGO_TIPOMONEDA},
						reader: {
							type: 'json',
							root: 'lista'
						}
					}
				});
				storeTipoMoneda.load();
// STORE PARA EL OBTENER LOS VALORES DE LOS ASEGURADOS
                storeAseguradoFactura = new Ext.data.Store({
                    pageSize    : 11
                    ,model      : 'modelAseguradosFactura'
                    ,autoLoad   : false
                    ,proxy      : {
                        enablePaging    :   true,
                        reader          :   'json',
                        type            :   'memory',
                        data            :   []
                    }
                });
//STORE PARA EL TIPO DE CONCEPTO
				var storeTipoConcepto = Ext.create('Ext.data.JsonStore', {
					model:'Generic',
					proxy: {
						type: 'ajax',
						url: _URL_CATALOGOS,
						extraParams : {catalogo:_CATALOGO_TIPOCONCEPTO},
						reader: {
							type: 'json',
							root: 'lista'
						}
					}
				});
				storeTipoConcepto.load();
				
				var storeAplicaIVA = Ext.create('Ext.data.JsonStore', {
					model:'Generic',
					proxy: {
						type: 'ajax',
						url: _URL_CATALOGOS,
						extraParams : {catalogo:_SINO},
						reader: {
							type: 'json',
							root: 'lista'
						}
					}
				});
				storeAplicaIVA.load();
				
                var storeAplicaFondo = Ext.create('Ext.data.JsonStore', {
                    model:'Generic',
                    proxy: {
                        type: 'ajax',
                        url: _URL_CATALOGOS,
                        extraParams : {catalogo:_SINO},
                        reader: {
                            type: 'json',
                            root: 'lista'
                        }
                    }
                });
                storeAplicaFondo.load();
				
// STORE PARA OBTENER EL LISTADO DE LOS ICD,CDT Y UB
				var storeConceptosCatalogo = Ext.create('Ext.data.JsonStore', {
					model:'Generic',
					proxy: {
						type: 'ajax',
						url:_URL_CATALOGOS,
						extraParams : {catalogo:_CATALOGO_CONCEPTOSMEDICOS},
						reader: {
							type: 'json',
							root: 'lista'
						}
					}
				});

				storeConceptosCatalogoRender = Ext.create('Ext.data.JsonStore', {
					model:'Generic',
					//autoLoad:true,
					cargado:false,
					proxy: {
						type: 'ajax',
						url: _URL_CATALOGOS,
						extraParams : {catalogo:_CATALOGO_CONCEPTOSMEDICOSTOTALES},
						reader: {
							type: 'json',
							root: 'lista'
						}
					},listeners: {
						load : function() {
							this.cargado=true;
							if(!Ext.isEmpty(gridEditorConceptos)) {
								gridEditorConceptos.getView().refresh();
							}
						}
					}
				});
				storeConceptosCatalogoRender.load();
				
				storeConceptos=new Ext.data.Store( {
					autoDestroy: true,
					model: 'modelConceptos',
					proxy: {
						type: 'ajax',
						url: _URL_CONCEPTOSASEG,
						reader: {
							type: 'json',
							root: 'loadList'
						}
					}
				});
				
				storeRecupera = new Ext.data.Store( {
					autoDestroy: true,
					model: 'modelRecupera',
					proxy: {
						type: 'ajax',
						url: _URL_MRECUPERA,
						reader: {
							type: 'json',
							root: 'loadList'
						}
					}
				});
				
//STORE DE LAS POLIZAS
				var storeListadoPoliza = new Ext.data.Store({
					pageSize : 5
					,model      : 'modelListadoPoliza'
					,autoLoad  : false
					,proxy     : {
						enablePaging : true,
						reader       : 'json',
						type         : 'memory',
						data         : []
					}
				});
//STORE LISTADO DE ASEGURADOS
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
				
				storeRechazos = Ext.create('Ext.data.JsonStore', {
					model:'modeloRechazos',
					proxy: {
						type: 'ajax',
						url: _URL_LISTARECHAZOS,
						reader: {
							type: 'json',
							root: 'loadList'
						}
					}
				});
				storeRechazos.load();
				
				storeIncisosRechazos = Ext.create('Ext.data.JsonStore', {
					model:'modeloIncisosRechazos',
					proxy: {
						type: 'ajax',
						url: _URL_LISTAINCISOSRECHAZOS,
						reader: {
							type: 'json',
							root: 'loadList'
						}
					}
				});
				
				storeDestinoPago = Ext.create('Ext.data.JsonStore', {
					model:'Generic',
					autoLoad:true,
					proxy: {
						type: 'ajax',
						url: _URL_CATALOGOS,
						extraParams : {catalogo:_CAT_DESTINOPAGO},
						reader: {
							type: 'json',
							root: 'lista'
						}
					}
				});
				
				storeConceptoPago = Ext.create('Ext.data.JsonStore', {
					model:'Generic',
					autoLoad:false,
					proxy: {
						type: 'ajax',
						url: _URL_CATALOGOS,
						extraParams : {catalogo:_CATALOGO_CONCEPTOPAGO},
						reader: {
							type: 'json',
							root: 'lista'
						}
					}
				});
				
				storeAsegurados2 = Ext.create('Ext.data.Store', {
					model:'Generic',
					autoLoad:false,
					proxy: {
						type: 'ajax',
						url : _URL_LISTADO_ASEGURADO_POLIZA,
						reader: {
							type: 'json',
							root: 'listaAsegurado'
						}
					}
				});
				
				storeAplicaISR = Ext.create('Ext.data.Store', {
					model:'Generic',
					autoLoad:true,
					proxy: {
						type: 'ajax',
						url: _URL_CATALOGOS,
						extraParams : {catalogo:_SINO},
						reader: {
							type: 'json',
							root: 'lista'
						}
					}
				});
				storeAplicaISR.load();
			
			    storeAplicaICE = Ext.create('Ext.data.Store', {
					model:'Generic',
					autoLoad:true,
					proxy: {
						type: 'ajax',
						url: _URL_CATALOGOS,
						extraParams : {catalogo:_SINO},
						reader: {
							type: 'json',
							root: 'lista'
						}
					}
				});
				storeAplicaICE.load();
				/*############################		DECLARACION DE COMBOX Y LABEL		########################################*/
				var comboTipoAte= Ext.create('Ext.form.ComboBox',
				{
					name:'params.cdtipser',			fieldLabel: 'TIPO ATENCI&Oacute;N',		allowBlank : false,		editable:true,
					displayField: 'value',			emptyText:'Seleccione...',				valueField: 'key',		forceSelection : true,
					queryMode:'local',				store: storeTipoAtencion
				});
				
				var cmbProveedor = Ext.create('Ext.form.field.ComboBox',
				{
					fieldLabel : 'PROVEEDOR',			displayField : 'nombre',			name:'params.cdpresta',
					valueField   : 'cdpresta',			forceSelection : true,
					matchFieldWidth: false,				queryMode :'remote',				queryParam: 'params.cdpresta',
					minChars  : 2,						store : storeProveedor,				triggerAction: 'all',
					hideTrigger	:true,					allowBlank:false,
					listeners	: {	//(EGS)
						'blur'	: function(e){
							debug("blur...",_GLOBAL_CDSISROL, _11_params.STATUS, _STATUS_TRAMITE_CONFIRMADO, _COORDINADOR_REC, _GERENTE_REC);
							if(_11_params.STATUS == _STATUS_TRAMITE_EN_CAPTURA){
								_11_clickAplicarCambiosFactura();
							}
							if(_11_params.STATUS == _STATUS_TRAMITE_CONFIRMADO && (_GLOBAL_CDSISROL == _COORDINADOR_REC || _GLOBAL_CDSISROL == _GERENTE_REC)){
								_11_clickAplicarCambiosFactura();
							}
						}
					}
				});
				var comboICDPrimario = Ext.create('Ext.form.field.ComboBox',
				{
					allowBlank: false,				displayField : 'value',		forceSelection : true,
					name:'idComboICDPrimario',		valueField   : 'key',		store : storeTiposICDPrimario,
					matchFieldWidth: false,			queryMode :'remote',		queryParam: 'params.otclave1', //queryParam: 'params.otclave', // (EGS)
					minChars  : 2,					editable:true,				triggerAction: 'all',		hideTrigger:true,
					listeners : {
						'select' : function(combo, record) {
							banderaAsegurado = 1;
							debug("VALOR DE LA BANDERA ASEURADO -->",banderaAsegurado);
							_11_aseguradoSeleccionado.set('CDICD',this.getValue());
						}
					}
				});
				
				var comboICDSecundario = Ext.create('Ext.form.field.ComboBox', {
					allowBlank: false,				displayField : 'value',		forceSelection : true,
					name:'idComboICDSEcundario',	valueField   : 'key',		store : storeTiposICDSecundario,
					matchFieldWidth: false,			queryMode :'remote',		queryParam: 'params.otclave1',	//queryParam: 'params.otclave',	// (EGS)
					minChars  : 2,					editable:true,				triggerAction: 'all',		hideTrigger:true,
					listeners : {
						'select' : function(combo, record) {
							banderaAsegurado = 1;
							debug("VALOR DE LA BANDERA ASEURADO -->",banderaAsegurado);
							_11_aseguradoSeleccionado.set('CDICD2',this.getValue());
						}
					}
				});
				
                var comboTipoEventos = Ext.create('Ext.form.ComboBox', {
                    name:'idComboTipoEvento',           store: storeTiposEvento,      value:'1',      queryMode:'local',  
                    displayField: 'value',              valueField: 'key',          editable:false//,     allowBlank:false
                });
                
                var comboAltaHospital = Ext.create('Ext.form.ComboBox', {
                    name:'idComboAltaHospital',     store: storeAltasHospital,  value:'1',      queryMode:'local',  
                    displayField: 'value',          valueField: 'key',          editable:false//,     allowBlank:false
                });
                
				var cmbCausaSiniestro = Ext.create('Ext.form.ComboBox', {
					name:'cmbCausaSiniestro',			store: storeCausaSinestro,		value:'1',		queryMode:'local',  
					displayField: 'value',		valueField: 'key',			editable:false,		allowBlank:false,
					listeners : {
						select:function(e){
							//banderaAsegurado = 1;
							debug("VALOR DE LA BANDERA ASEURADO -->",banderaAsegurado);
						}
					}
				});

				var cmbTipoMoneda = Ext.create('Ext.form.ComboBox', {
					name:'params.tipoMoneda',		fieldLabel	: 'MONEDA',		store: storeTipoMoneda,			queryMode:'local',  
					displayField: 'value',			valueField: 'key',			editable:false,					allowBlank:false,
					listeners : {
						select:function(e){
							if(e.getValue() == "001"){
								panelInicialPral.down('[name=params.tasacamb]').setValue("0.00");
								panelInicialPral.down('[name=params.ptimporta]').setValue("0.00");
								panelInicialPral.down('[name=params.tasacamb]').hide();
								panelInicialPral.down('[name=params.ptimporta]').hide();
							}else{
								panelInicialPral.down('[name=params.tasacamb]').setValue('');
								panelInicialPral.down('[name=params.ptimporta]').setValue('');
								panelInicialPral.down('[name=params.tasacamb]').show();
								panelInicialPral.down('[name=params.ptimporta]').show();
							}
						}
					}
				});

				
				var cobertura = Ext.create('Ext.form.field.ComboBox', {
					name:'params.cdgarant',			fieldLabel : 'COBERTURA',	/*allowBlank: false,*/				displayField : 'value',
					valueField   : 'key',			forceSelection : true,		matchFieldWidth: false,				hidden: true,
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

				
				coberturaxAsegurado = Ext.create('Ext.form.field.ComboBox', {
					allowBlank: false,			displayField : 'dsgarant',		id:'idCobAfectada',		name:'cdgarant',
					valueField   : 'cdgarant',	forceSelection : true,			matchFieldWidth: false,
					queryMode :'remote',				store : storeCoberturaxAsegurado,		triggerAction: 'all',			editable:true,
					listeners : {
						'select' : function(combo, record) {
							//banderaAsegurado = 1;
							_11_aseguradoSeleccionado.set('CDGARANT',this.getValue());
							storeSubcoberturaAsegurado.removeAll();
							storeSubcoberturaAsegurado.load({
								params:{
									'params.cdunieco':_11_aseguradoSeleccionado.get('CDUNIECO'),
									'params.cdramo':_11_aseguradoSeleccionado.get('CDRAMO'),
									'params.estado':_11_aseguradoSeleccionado.get('ESTADO'),
									'params.nmpoliza':_11_aseguradoSeleccionado.get('NMPOLIZA'),
									'params.nmsituac':_11_aseguradoSeleccionado.get('NMSITUAC'),
									'params.cdtipsit':_11_aseguradoSeleccionado.get('CDTIPSIT'),
									'params.cdgarant' :this.getValue(),
									'params.cdsubcob' :null
								}
							});
						}
					}
				});
				
				
				coberturaRecupera = Ext.create('Ext.form.field.ComboBox', {
					allowBlank: false,			displayField : 'dsgarant',		id:'idCobAfectadaRec',		name:'cdgarantRec',
					valueField   : 'cdgarant',	forceSelection : true,			matchFieldWidth: false,
					queryMode :'remote',				store : storeCoberturaRecupera,		triggerAction: 'all',			editable:true,
					listeners : {
						'select' : function(combo, record) {
							_11_conceptoSeleccionado.set('CDGARANT',this.getValue());
							storeSubcoberturaRecupera.removeAll();
							storeSubcoberturaRecupera.load({
								params:{
									'params.cdunieco':_11_aseguradoSeleccionado.get('CDUNIECO'),
									'params.cdramo':_11_aseguradoSeleccionado.get('CDRAMO'),
									'params.estado':_11_aseguradoSeleccionado.get('ESTADO'),
									'params.nmpoliza':_11_aseguradoSeleccionado.get('NMPOLIZA'),
									'params.nmsituac':_11_aseguradoSeleccionado.get('NMSITUAC'),
									'params.cdtipsit':_11_aseguradoSeleccionado.get('CDTIPSIT'),
									'params.cdgarant' :this.getValue(),
									'params.cdsubcob' :null
								}
							});
						}
					}
			    });
			    
			    var subCoberturaAsegurado = Ext.create('Ext.form.field.ComboBox', {
			    	allowBlank: false,				displayField : 'value',			id:'idSubcobertura1',		name:'cdconval',
			    	labelWidth: 170,				valueField   : 'key',			forceSelection : true,			matchFieldWidth: false,
			    	queryMode :'local',			store : storeSubcoberturaAsegurado,		triggerAction: 'all',			editable:true,
			    	listeners : {
						'select' : function(combo, recordsSel) {
							
							var valorCobertura = _11_aseguradoSeleccionado.get('CDGARANT');
							var valorSubCobertura = this.getValue();
							
							var recordRecAseg = gridFacturaDirecto.getSelectionModel().getLastSelected();
							
							var paramsResEstudios = {
                     			cdunieco  : recordRecAseg.get('CDUNIECO'),
                     			cdramo    : recordRecAseg.get('CDRAMO'),
                     			aaapertu  : recordRecAseg.get('AAAPERTU'),
                     			status    : recordRecAseg.get('STATUS'),
                     			nmsinies : recordRecAseg.get('NMSINIES'),
                     			nmsituac  : recordRecAseg.get('NMSITUAC'),
                     			cdtipsit  : recordRecAseg.get('CDTIPSIT'),
                     			cdgarant  : valorCobertura,
                     			cdconval  : valorSubCobertura,
                     			dsconval  : recordsSel[0].get('value'),
                     			dsasegurado: recordRecAseg.get('NOMBRE'),
                     			accionBoton: false
                     		};
							
                           	validaCapturaResultadoEstudiosAsegurado(paramsResEstudios);
                            	
							_11_aseguradoSeleccionado.set('CDCONVAL',this.getValue());
							//banderaAsegurado = 1;
							debug("VALOR DE LA BANDERA ASEURADO -->",banderaAsegurado);
                            Ext.Ajax.request( {
                                url  : _URL_ALTA_EVENTO 
                                ,params:{
                                    'params.cdramo'    : _11_aseguradoSeleccionado.get('CDRAMO'),
                                    'params.cdtipsit'  : _11_aseguradoSeleccionado.get('CDTIPSIT'),
                                    'params.cdgarant'  : _11_aseguradoSeleccionado.get('CDGARANT'),
                                    'params.cdconval'  : this.getValue()
                                }
                                ,success : function (response) {
                                	
                                    //Obtenemos los datos
                                    if(Ext.decode(response.responseText).datosValidacion != null){
                                        var jsonValidacionCober =Ext.decode(response.responseText).datosValidacion;
                                        debug("Valor de los datos de Respuesta para validaciones de alta =>",jsonValidacionCober[0]);
                                        _11_aseguradoSeleccionado.set('CDTIPEVE','0');
                                        _11_aseguradoSeleccionado.set('CDTIPALT','0');
                                        storeAltasHospital.removeAll();
                                        storeTiposEvento.removeAll();
                                        
                                        if(+jsonValidacionCober[0].FLAGTIPALT > 0){
                                        	_11_aseguradoSeleccionado.set('FLAGTIPALT',jsonValidacionCober[0].FLAGTIPALT);
                                            storeAltasHospital.removeAll();
                                            storeAltasHospital.load({
                                                params:{
                                                'params.cdramo'         : _11_aseguradoSeleccionado.get('CDRAMO'),
                                                'params.cdtipsit'       : _11_aseguradoSeleccionado.get('CDTIPSIT'),
                                                'params.cdgarant'       : _11_aseguradoSeleccionado.get('CDGARANT'),
                                                'params.cveCatalogo'    : "2"
                                                }
                                            });
                                        }
                                        
                                        if(+jsonValidacionCober[0].FLAGTIPEVE > 0){
                                        	_11_aseguradoSeleccionado.set('FLAGTIPEVE',jsonValidacionCober[0].FLAGTIPEVE);
                                            storeTiposEvento.removeAll();
                                            storeTiposEvento.load({
                                                params:{
                                                'params.cdramo'         : _11_aseguradoSeleccionado.get('CDRAMO'),
                                                'params.cdtipsit'       : _11_aseguradoSeleccionado.get('CDTIPSIT'),
                                                'params.cdgarant'       : _11_aseguradoSeleccionado.get('CDGARANT'),
                                                'params.cveCatalogo'    : "1"
                                                }
                                            });
                                        }
                                        
                                        if(jsonValidacionCober[0].FLAGREQAUT == "SI" &&  (  _11_aseguradoSeleccionado.get('NMAUTSER') =="N/A" || +_11_aseguradoSeleccionado.get('NMAUTSER') <= '0' || _11_aseguradoSeleccionado.get('NMAUTSER')== "")){
                                	        msgWindow = Ext.Msg.show({
                                                title: 'Aviso',
                                                msg: 'Se requiere una autorizaci&oacute;n de Servicio. <br/> &iquest;Desea realizar la asociaci&oacute;n ?',
                                                buttons: Ext.Msg.YESNO,
                                                icon: Ext.Msg.QUESTION,
                                                fn: function(buttonId, text, opt){
                                                    if(buttonId == 'no'){
                                                        // actualizamos la información
                                                        banderaAsegurado = 0;
                                                        storeConceptos.removeAll();
                                                        cargarPaginacion(panelInicialPral.down('[name=params.ntramite]').getValue(), panelInicialPral.down('[name=params.nfactura]').getValue());
                                                    }
                                                    if(buttonId == 'yes'){
                                                        _11_modificarAutorizacion(_11_aseguradoSeleccionado);
                                                    }
                                                }
                                            });
                                            centrarVentanaInterna(msgWindow);
                                        }
                                    }
                                },
                                failure : function () {
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

				var subCoberturaRecupera = Ext.create('Ext.form.field.ComboBox', {
					allowBlank: false,				displayField : 'value',			id:'idSubcoberturaRec',		name:'cdconval',
					labelWidth: 170,				valueField   : 'key',			forceSelection : true,			matchFieldWidth: false,
					queryMode :'local',			store : storeSubcoberturaRecupera,		triggerAction: 'all',			editable:true
					,listeners : {
						select:function(e){
							//ESQUEMA DE SUMA ASEGURADA
							Ext.Ajax.request( {
								url	 : _URL_SUMASEG_RECUPERA
								,params:{
									'params.cdunieco':_11_aseguradoSeleccionado.get('CDUNIECO'),
									'params.cdramo':_11_aseguradoSeleccionado.get('CDRAMO'),
									'params.estado':_11_aseguradoSeleccionado.get('ESTADO'),
									'params.nmpoliza':_11_aseguradoSeleccionado.get('NMPOLIZA'),
									'params.nmsituac':_11_aseguradoSeleccionado.get('NMSITUAC'),
									'params.nmsuplem'	: _11_aseguradoSeleccionado.get('NMSUPLEM'),
									'params.cdgarant' :_11_conceptoSeleccionado.get('CDGARANT'),
									'params.cdconval' :this.getValue()
								}
								,success : function (response) {
									var infRecupera = Ext.decode(response.responseText).loadList;
									_11_conceptoSeleccionado.set('ESQUEMAASEG',infRecupera[0].ESQUEMAASEG);
									_11_conceptoSeleccionado.set('SUMAASEG',infRecupera[0].SUMAASEG);
									var esquema = _11_conceptoSeleccionado.get('ESQUEMAASEG');
									var sumaAsegurada = _11_conceptoSeleccionado.get('SUMAASEG');
									var porcentajePago = _11_conceptoSeleccionado.get('CANTPORC');
									var ImporteConcepto = ((+esquema * +sumaAsegurada) * (+porcentajePago/100));
									_11_conceptoSeleccionado.set('PTIMPORT',ImporteConcepto);
								},
								failure : function () {
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
					}
				});
				var subCobertura = Ext.create('Ext.form.field.ComboBox', {
					name:'params.cdconval',		fieldLabel : 'SUBCOBERTURA',	/*allowBlank: false,*/				displayField : 'value',			id:'idSubcobertura',
					valueField   : 'key',			forceSelection : true,			matchFieldWidth: false,		hidden: true,
					queryMode :'remote',			store : storeSubcobertura,		triggerAction: 'all',			editable:false
				});
				
				cmbCveTipoConcepto = Ext.create('Ext.form.ComboBox', {
					name:'params.idconcep',		store: storeTipoConcepto,		queryMode:'local',
					displayField: 'value',		valueField: 'key',				editable:false,				allowBlank:false,
					listeners:{
						select: function (combo, records, opts){
							banderaConcepto = 1;
							var cdTipo = records[0].get('key');
							storeConceptosCatalogo.proxy.extraParams= {
								'params.idPadre' : cdTipo
								,catalogo		: _CATALOGO_CONCEPTOSMEDICOS
							};
						}
					}
				});
				
				cmbAplicaIVA = Ext.create('Ext.form.ComboBox', {
					name:'params.idAplicaIVA',		store: storeAplicaIVA,		queryMode:'local',
					displayField: 'value',		valueField: 'key',				editable:false,				allowBlank:false
				});
				
                cmbAplicaFondo = Ext.create('Ext.form.ComboBox', {
                    name:'params.idAplicaFondo',    store: storeAplicaFondo,    queryMode:'local',
                    displayField: 'value',          valueField: 'key',          editable:false,             allowBlank:false
                    ,readOnly    : _CDROL ==  _OPERADOR_REC
                });
                
				cmbCveConcepto = Ext.create('Ext.form.ComboBox', {
					name:'params.cdconcep',		store: storeConceptosCatalogo,		queryMode:'remote',
					displayField: 'value',		valueField: 'key',					editable:true,				allowBlank:false,
					forceSelection: true,		queryParam  : 'params.descripc',	hideTrigger : true,			minChars	: 3
					,listeners : {
						select:function(e){
							//Verificamos una tabla de apoyo para saber si se encuentra en la tabla de apoyo
							var aplicaIVAFact = panelInicialPral.down('[name="parametros.pv_otvalor01"]').getValue();
							if(aplicaIVAFact =='S'){
								Ext.Ajax.request( {
									url	 : _URL_APLICA_IVA_CONCEPTO
									,params:{
										'params.idConcepto'   : e.getValue()
									}
									,success : function (response) {
										_11_conceptoSeleccionado.set('APLICIVA',Ext.decode(response.responseText).msgResult);
									},
									failure : function () {
										//me.up().up().setLoading(false);
										Ext.Msg.show({
											title:'Error',
											msg: 'Error de comunicaci&oacute;n',
											buttons: Ext.Msg.OK,
											icon: Ext.Msg.ERROR
										});
									}
								});
							}else{
								_11_conceptoSeleccionado.set('APLICIVA',aplicaIVAFact);
							}
							
							
							Ext.Ajax.request( {
								url	 : _URL_MONTO_ARANCEL
								,params:{
									'params.tipoConcepto'   : _11_conceptoSeleccionado.get('IDCONCEP'),
									'params.idProveedor'	: panelInicialPral.down('combo[name=params.cdpresta]').getValue(),
									'params.idConceptoTipo' : e.getValue()
								}
								,success : function (response) {
									banderaConcepto = 1;
									if(Ext.decode(response.responseText).montoArancel == null){
										_11_conceptoSeleccionado.set('PTMTOARA','0');
									}else{
										_11_conceptoSeleccionado.set('PTMTOARA',Ext.decode(response.responseText).montoArancel);
									}
									var valorArancel = _11_conceptoSeleccionado.get('PTMTOARA');
									
									if(+ valorArancel > 0){
										_11_conceptoSeleccionado.set('PTPRECIO',valorArancel);
									}
								},
								failure : function () {
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
					}
				});
				
				var aplicaISR = Ext.create('Ext.form.field.ComboBox',{
					fieldLabel   : 'APLICA ISR',	id		  : 'swAplicaisr',			allowBlank		: false,
					editable   : false,			displayField : 'value',				valueField:'key',			    		forceSelection  : true,
					queryMode    :'local',				editable  :false,						name			:'params.swAplicaisr',
					store : storeAplicaISR
				});
			    
			    var aplicaICE = Ext.create('Ext.form.field.ComboBox',{
					fieldLabel   : 'APLICA ICE',	id		  : 'swAplicaice',			allowBlank		: false,
					editable   : false,			displayField : 'value',				valueField:'key',			    		forceSelection  : true,
					queryMode    :'local',				editable  :false,						name			:'params.swAplicaice',
					store : storeAplicaICE
				});
							
				/////////////////////////////////////////////////////////////////////////////////////
				Ext.define('EditorFacturaDirecto', {
					extend: 'Ext.grid.Panel',
					name:'editorFacturaDirecto',
					title: 'Asegurados',
					icon		: '${ctx}/resources/fam3icons/icons/user.png',
					frame: true,
					selType  : 'rowmodel',
					//selModel: { selType: 'checkboxmodel', mode: 'SINGLE', checkOnly: true },
					initComponent: function(){
						Ext.apply(this, {
						height: 450,
						plugins  :
						[
							Ext.create('Ext.grid.plugin.CellEditing', {
								clicksToEdit: 1
								,listeners : {
									beforeedit : function() {
										if (banderaConcepto == "1"){
											// Guardar Conceptos del asegurado
											_guardarConceptosxFactura();
										}else if(banderaAsegurado == "1"){
											// Guardamos los datos complementarios del Asegurado
											guardaDatosComplementariosAsegurado(_11_aseguradoSeleccionado, banderaAsegurado);
											storeConceptos.removeAll();
										}else{
											debug("Entra al beforeedit :::: ");
											_11_aseguradoSeleccionado = gridFacturaDirecto.getView().getSelectionModel().getSelection()[0];
											debug("_11_aseguradoSeleccionado ====>>>",_11_aseguradoSeleccionado);
											//Cobertura
											storeCoberturaxAsegurado.proxy.extraParams= {
												'params.ntramite'	: panelInicialPral.down('[name=params.ntramite]').getValue(),
												'params.nfactura'	: panelInicialPral.down('[name=params.nfactura]').getValue(),
												'params.tipoPago'   : _tipoPago,
												'params.cdunieco'	: _11_aseguradoSeleccionado.get('CDUNIECO'),
								            	'params.estado'		: _11_aseguradoSeleccionado.get('ESTADO'),
								            	'params.cdramo'		: _11_aseguradoSeleccionado.get('CDRAMO'),
								            	'params.nmpoliza'	: _11_aseguradoSeleccionado.get('NMPOLIZA'),
								            	'params.nmsituac'	: _11_aseguradoSeleccionado.get('NMSITUAC')
											};
											storeCoberturaxAsegurado.load();
											//Subcobertura
											storeSubcoberturaAsegurado.load({
												params:{
													'params.cdunieco'	: _11_aseguradoSeleccionado.get('CDUNIECO'),
									            	'params.cdramo'		: _11_aseguradoSeleccionado.get('CDRAMO'),
									            	'params.estado'		: _11_aseguradoSeleccionado.get('ESTADO'),
									            	'params.nmpoliza'	: _11_aseguradoSeleccionado.get('NMPOLIZA'),
									            	'params.nmsituac'	: _11_aseguradoSeleccionado.get('NMSITUAC'),
									            	'params.cdtipsit'	: _11_aseguradoSeleccionado.get('CDTIPSIT'),
									            	'params.cdgarant' 	: _11_aseguradoSeleccionado.get('CDGARANT'),
									            	'params.cdsubcob' 	: null
												}
											});
										}
									}
								}
							})
						],
						store: storeAseguradoFactura,
						columns: 
						[
							{
								xtype		 : 'actioncolumn'
								,menuDisabled : true
								,width		: 100
								,items		:
								[
									{
										icon	 : '${ctx}/resources/fam3icons/icons/application_edit.png'
										,tooltip : 'Generar Siniestro'
										,handler : revisarDocumento
									},
									{
										icon	 : '${ctx}/resources/fam3icons/icons/accept.png'
										,tooltip : 'Guardar'
										,disabled: _11_params.STATUS == _STATUS_TRAMITE_CONFIRMADO && !(_GLOBAL_CDSISROL == _COORDINADOR_REC || _GLOBAL_CDSISROL == _GERENTE_REC) ? true : false  // (EGS)
										,handler : guardarDatosComplementarios
									},
									{
										icon	 : '${ctx}/resources/fam3icons/icons/user_delete.png'
										,tooltip : 'Eliminar Asegurado'
										,disabled: _11_params.STATUS == _STATUS_TRAMITE_CONFIRMADO && !(_GLOBAL_CDSISROL == _COORDINADOR_REC || _GLOBAL_CDSISROL == _GERENTE_REC) ? true : false  // (EGS)  
										,handler : eliminarAsegurado
										
									},
									{
										icon	 : '${ctx}/resources/fam3icons/icons/page_edit.png'
										,tooltip : 'Datos Informativos de estudios m&eacute;dicos por asegurado.'
										,handler : function(view, rowIndex, colIndex, item, e, recordRecAseg, row ){
											
											var valorCobertura = recordRecAseg.get('CDGARANT');
											var valorSubCobertura = recordRecAseg.get('CDCONVAL');
											if(Ext.isEmpty(valorCobertura) || Ext.isEmpty(valorSubCobertura)){
												mensajeWarning('Aun no hay datos capturados de Cobertura y Subcobertura para esta acci&oacute;n');
											}else{
												var paramsResEstudios = {
						                     			cdunieco  : recordRecAseg.get('CDUNIECO'),
						                     			cdramo    : recordRecAseg.get('CDRAMO'),
						                     			aaapertu  : recordRecAseg.get('AAAPERTU'),
						                     			status    : recordRecAseg.get('STATUS'),
						                     			nmsinies : recordRecAseg.get('NMSINIES'),
						                     			nmsituac  : recordRecAseg.get('NMSITUAC'),
						                     			cdtipsit  : recordRecAseg.get('CDTIPSIT'),
						                     			cdgarant  : valorCobertura,
						                     			cdconval  : valorSubCobertura,
						                     			dsconval  : 'Sin DESCRIPCION',
						                     			dsasegurado: recordRecAseg.get('NOMBRE'),
						                     			accionBoton: true
						                     		};
													
						                           	validaCapturaResultadoEstudiosAsegurado(paramsResEstudios);
											}
											
										}
										
									}
								]
							},
							{
								header: 'Id<br/>Sini.',				dataIndex: 'NMSINIES',			width: 120 
							},
							{
								header: '#<br/>Call Center',		dataIndex: 'NMCALLCENTER',		width: 70,    hidden : true
								,editor: {
									xtype: 'numberfield'
								}
							},
							{
								header: '# Auto.',					dataIndex: 'NMAUTSER',			width: 70
								,editor: {
									xtype: 'numberfield',
									listeners : {
										change:function(e){
											_11_modificarAutorizacion(_11_aseguradoSeleccionado);
										}
									}
								}
							},
                            {
                                header: 'Reclamo',   dataIndex: 'NUMRECLA',          width: 90, hidden : (_tipoProducto != _GMMI && _tipoProducto != _GMPRUEBA)
                            },
							{
								header: 'Id<br/>Sini. Existente',   dataIndex: 'NMSINREF',          width: 90, hidden : true//(_tipoProducto != _GMMI && _tipoProducto != _GMPRUEBA)
							},
							{
								header: 'Complemento',				dataIndex: 'COMPLEMENTO',		width: 90, hidden : true//(_tipoProducto != _GMMI && _tipoProducto != _GMPRUEBA)
							},
							{
								header: 'Fecha<br/>Ocurrencia',		dataIndex: 'FEOCURRE'
							},
							{
								header: 'Clave<br/>asegu.',			dataIndex: 'CDPERSON'
							},
							{
								header: 'Nombre<br/>Asegurado',		dataIndex: 'NOMBRE'
							},
							{
                                header: 'Aplica Fondo',             dataIndex: 'SWFONSIN' 
                                ,editor : cmbAplicaFondo
                                ,renderer        : function(v) {
                                    var r=v;
                                    if(v=='S'||v=='s') {
                                        r='SI';
                                    }
                                    else if(v=='N'||v=='n'){
                                        r='NO';
                                    }
                                    return r;
                                }
                            },
							{
								header: 'Causa <br/> Siniestro', 	dataIndex: 'CDCAUSA'
								,editor : cmbCausaSiniestro
								,renderer : function(v) {
									var leyenda = '';
									if (typeof v == 'string') {
										storeCausaSinestro.each(function(rec) {
											if (rec.data.key == v) {
												leyenda = rec.data.value;
											}
										});
									}else {
										if (v.key && v.value) {
											leyenda = v.value;
										} else {
											leyenda = v.data.value;
										}
									}
									return leyenda;
								}
							},
							{
								header: 'Cobertura',				dataIndex: 'CDGARANT',		allowBlank: false
								,editor: coberturaxAsegurado
								,hidden : _tipoProducto == _RECUPERA
								,renderer : function(v) {
									var leyenda = '';
									if (typeof v == 'string') {
										if(storeCoberturaxAseguradoRender.cargado) {
											//debug("storeCoberturaxAseguradoRender :",storeCoberturaxAseguradoRender);
											storeCoberturaxAseguradoRender.each(function(rec) {
												if (rec.data.key == v) {
													leyenda = rec.data.value;
												}
											});
										}
										else{
										    leyenda='Cargando...';
										}
									}else {
										if (v.key && v.value) {
											leyenda = v.value;
										} else {
											leyenda = v.data.value;
										}
										leyenda= v;
									}
									return leyenda;
								}
							},
							{
								header: 'Subcobertura',				dataIndex: 'CDCONVAL',	allowBlank: false
								,hidden : _tipoProducto == _RECUPERA
								,editor: subCoberturaAsegurado
								,renderer : function(v) {
									var leyenda = '';
									if (typeof v == 'string') {
										debug("Valor de V : "+v);
										debug("Valor de storeSubcoberturaAseguradoRender.cargado : "+storeSubcoberturaAseguradoRender.cargado);
										debug("======> Valor de _cdtipsitProducto", _cdtipsitProducto);
										if(_cdtipsitProducto == "MS"){
											if(storeSubcoberturaAsegurado4MSRender.cargado) {
												debug("storeSubcoberturaAsegurado4MSRender");
												debug(storeSubcoberturaAsegurado4MSRender);
												storeSubcoberturaAsegurado4MSRender.each(function(rec) {
													if (rec.data.key == v){
														leyenda = rec.data.value;
													}
												});
											}
											else{
											    leyenda='Cargando...';
											}
										}else if(_cdtipsitProducto =="MSC"){
											if(storeSubcoberturaAsegurado4MSCRender.cargado) {
												debug("storeSubcoberturaAsegurado4MSCRender");
												debug(storeSubcoberturaAsegurado4MSCRender);
												storeSubcoberturaAsegurado4MSCRender.each(function(rec) {
													if (rec.data.key == v){
														leyenda = rec.data.value;
													}
												});
											}
											else{
											    leyenda='Cargando...';
											}
										}
										else if(_cdtipsitProducto =="SSI"){
											if(storeSubcoberturaAsegurado4INFORender.cargado) {
												debug("storeSubcoberturaAsegurado4INFORender");
												debug(storeSubcoberturaAsegurado4INFORender);
												storeSubcoberturaAsegurado4INFORender.each(function(rec) {
													if (rec.data.key == v){
														leyenda = rec.data.value;
													}
												});
											}
											else{
											    leyenda='Cargando...';
											}
										}
										else if(_cdtipsitProducto =="GMPC"){
                                            if(storeSubcoberturaAseguradoGMPCRender.cargado) {
                                                debug("storeSubcoberturaAseguradoGMPCRender");
                                                debug(storeSubcoberturaAseguradoGMPCRender);
                                                storeSubcoberturaAseguradoGMPCRender.each(function(rec) {
                                                    if (rec.data.key == v){
                                                        leyenda = rec.data.value;
                                                    }
                                                });
                                            }
                                            else{
                                                leyenda='Cargando...';
                                            }
                                        }
                                        else if(_cdtipsitProducto =="GMPI"){
                                            if(storeSubcoberturaAseguradoGMPIRender.cargado) {
                                                debug("storeSubcoberturaAseguradoGMPIRender");
                                                debug(storeSubcoberturaAseguradoGMPIRender);
                                                storeSubcoberturaAseguradoGMPIRender.each(function(rec) {
                                                    if (rec.data.key == v){
                                                        leyenda = rec.data.value;
                                                    }
                                                });
                                            }
                                            else{
                                                leyenda='Cargando...';
                                            }
                                        }
										else{
											if(storeSubcoberturaAseguradoRender.cargado) {
												debug("storeSubcoberturaAseguradoRender");
												debug(storeSubcoberturaAseguradoRender);
												storeSubcoberturaAseguradoRender.each(function(rec) {
													if (rec.data.key == v){
														leyenda = rec.data.value;
													}
												});
											}
											else{
											    leyenda='Cargando...';
											}
										}
									}else{
										if (v.key && v.value){
											leyenda = v.value;
										} else {
											leyenda = v.data.value;
										}
										leyenda= v;
									}
									return leyenda;
								}
							},
                            {   header: 'Fecha ingreso',        dataIndex: 'FEINGRESO',  renderer: Ext.util.Format.dateRenderer('d/m/Y')  ,hidden : _tipoProducto == _GMPRUEBA
                                ,editor : {
                                    xtype : 'datefield',
                                    format : 'd/m/Y',
                                    editable : true,
                                    listeners : {
                                        'change':function(field){
                                            _11_aseguradoSeleccionado.set('FEEGRESO','');                                            
                                        }
                                    }
                                }
                            }, 
                            {   header: 'Fecha egreso',             dataIndex: 'FEEGRESO',    renderer: Ext.util.Format.dateRenderer('d/m/Y')       //,hidden : _tipoProducto == _GMPRUEBA
                                ,editor : {
                                    xtype    : 'datefield',
                                    format   : 'd/m/Y',
                                    editable : true,
                                    focus : function(me)
                                    {
                                    	this.minvalue = _11_aseguradoSeleccionado.get('FEINGRESO')
                                    }
                                }
                            },
                            {
                                header: 'Tipo evento',              dataIndex: 'CDTIPEVE'           ,hidden : _tipoProducto == _GMPRUEBA
                                ,editor : comboTipoEventos
                                ,renderer : function(v) {
                                    var leyenda = '';
                                    if (typeof v == 'string') {
                                        if(storeTiposEventoRender.cargado) {
                                            debug("storeTiposEventoRender :",storeTiposEventoRender);
                                            storeTiposEventoRender.each(function(rec) {
                                                if (rec.data.key == v) {
                                                    leyenda = rec.data.value;
                                                }
                                            });
                                        }
                                        else{
                                            leyenda='Cargando...';
                                        }
                                    }else {
                                        if (v.key && v.value) {
                                            leyenda = v.value;
                                        } else {
                                            leyenda = v.data.value;
                                        }
                                        leyenda= v;
                                    }
                                    return leyenda;
                                }
                            },
                             {
                                header: 'Alta',                     dataIndex: 'CDTIPALT'           ,hidden : _tipoProducto == _GMPRUEBA
                                ,editor : comboAltaHospital
                                ,renderer : function(v) {
                                    var leyenda = '';
                                    if (typeof v == 'string') {
                                        if(storeAltasHospitalRender.cargado) {
                                            debug("storeAltasHospitalRender :",storeAltasHospitalRender);
                                            storeAltasHospitalRender.each(function(rec) {
                                                if (rec.data.key == v) {
                                                    leyenda = rec.data.value;
                                                }
                                            });
                                        }
                                        else{
                                            leyenda='Cargando...';
                                        }
                                    }else {
                                        if (v.key && v.value) {
                                            leyenda = v.value;
                                        } else {
                                            leyenda = v.data.value;
                                        }
                                        leyenda= v;
                                    }
                                    return leyenda;
                                }
                            },
							{
								header: 'ICD<br/>Principal', 		dataIndex: 'CDICD'
								,editor : comboICDPrimario
								,renderer : function(v) {
									var leyenda = '';
									if (typeof v == 'string') {
										if(storeTiposICDPrimarioRender.cargado) {
											storeTiposICDPrimarioRender.each(function(rec) {
												if (rec.data.key == v) {
													leyenda = rec.data.value;
												}
											});
										}
										else{
										    leyenda= v;
										}
									}else {
										if (v.key && v.value) {
											leyenda = v.value;
										} else {
											leyenda = v.data.value;
										}
										leyenda='2';
									}
									return leyenda;
								}
							},
							{
								header: 'ICD<br/>Secundario', 		dataIndex: 'CDICD2'
								,editor : comboICDSecundario
								,renderer : function(v) {
									var leyenda = '';
									if (typeof v == 'string') {
										if(storeTiposICDSecundarioRender.cargado) {
											storeTiposICDSecundarioRender.each(function(rec) {
												if (rec.data.key == v) {
													leyenda = rec.data.value;
												}
											});
										}
										else {
										    leyenda= v;
										}
									}else {
										if (v.key && v.value) {
											leyenda = v.value;
										} else {
											leyenda = v.data.value;
										}
										leyenda='2';
									}
									return leyenda;
								}
							},
							{
								header: 'P&oacute;liza',			dataIndex: 'NMPOLIZA'
							},
							{
								header: 'Vo.Bo.<br/>Auto.',			dataIndex: 'VOBOAUTO', 
								hidden: true,
								renderer		: function(v) {
									var r=v;
									if(v=='S'||v=='s') {
										r='SI';
									}
									else if(v=='N'||v=='n'){
										r='NO';
									}
									return r;
								}
							},
							{
								header: 'Deducible',				dataIndex: 'DEDUCIBLE'
							},
							{
								header: 'Copago',					dataIndex: 'COPAGO'
							},
							{
								header: '$<br/>Facturado',			dataIndex: 'PTIMPORT',			renderer	   :Ext.util.Format.usMoney
							},
							{
								header: '#<br/>Reclamo',			dataIndex: 'NMRECLAMO',			hidden	:	true
							},
							{
								header: 'Tipsit',					dataIndex: 'DSTIPSIT',			hidden	:	true
							},
							{
								header: 'Subtotal',					dataIndex: 'IMPORTEASEG',renderer  : Ext.util.Format.usMoney
							},
							{
								header: 'IVA',						dataIndex: 'PTIVAASEG',renderer  : Ext.util.Format.usMoney
							},
							{
								header: 'IVA Retenido',				dataIndex: 'PTIVARETASEG',renderer  : Ext.util.Format.usMoney
							},
							{
								header: 'ISR',						dataIndex: 'PTISRASEG',renderer  : Ext.util.Format.usMoney
							},
							{
								header: 'Imp. Cedular',				dataIndex: 'PTIMPCEDASEG',renderer  : Ext.util.Format.usMoney
							},
							{
								header: 'Importe a Pagar',			dataIndex: 'IMPORTETOTALPAGO',renderer  : Ext.util.Format.usMoney
							},
							{
								header: 'CDTIPSIT',					dataIndex: 'CDTIPSIT',				hidden	:	true
							},
							{
								header: 'ReqValidacion',			dataIndex: 'REQAUTESPECIAL',		hidden	:	true
							},
							{
								header: 'ValidacionTotal',			dataIndex: 'VALTOTALCOB',			hidden	:	true
							},
							{
								header: 'Limite',					dataIndex: 'LIMITE',				hidden	:	true
							},
							{
								header: 'TotalPagado',				dataIndex: 'IMPPAGCOB',				hidden	:	true
							},
							{
								header: 'Req. Aut Especial',		dataIndex: 'REQAUTES',			width: 50, 		hidden : true
							},
							{
								header: 'No. Aut',					dataIndex: 'NMAUTESP',			width: 50, 		hidden : true
							},
							{
								header: 'Sec. tworksin',			dataIndex: 'SECTWORKSIN',		width: 50, 		hidden : true
							},
                            {
                                header: 'TipoEvento',               dataIndex: 'FLAGTIPEVE',       width: 50,        hidden : true
                            },
                            {
                                header: 'TipoAlta',                 dataIndex: 'FLAGTIPALT',       width: 50,        hidden : true
                            },
                            {
                                header: 'Req.Aut.Serv',             dataIndex: 'FLAGREQAUT',       width: 50//,        hidden : true
                            }
                            
							
						],
						bbar     :{
                            displayInfo : true,
                            store       : storeAseguradoFactura,
                            xtype       : 'pagingtoolbar'
                        }
                        ,
                        tbar:[
								{
									text	 : 'Agregar Asegurado'
									,icon	 : '${ctx}/resources/fam3icons/icons/user_add.png'
									,handler : _p21_agregarAsegurado
									,hidden  : (_tipoPago != _TIPO_PAGO_DIRECTO)
									,disabled: _11_params.STATUS == _STATUS_TRAMITE_CONFIRMADO && !(_GLOBAL_CDSISROL == _COORDINADOR_REC || _GLOBAL_CDSISROL == _GERENTE_REC) ? true : false  // (EGS)  
								},
								{
									text	: 'Generar Calculo'
									,icon:_CONTEXT+'/resources/fam3icons/icons/book.png'
									,handler : _p21_generarCalculo
									,hidden  : (_cdtipoProceso  == "1")
									,disabled: _11_params.STATUS == _STATUS_TRAMITE_CONFIRMADO && !(_GLOBAL_CDSISROL == _COORDINADOR_REC || _GLOBAL_CDSISROL == _GERENTE_REC) ? true : false  // (EGS)  
								}
							],							
						listeners: {
							select: function (grid, record, index, opts){
								debug("<--VALOR DE LA BANDERA DEL CONCEPTO-->",banderaConcepto,"<--VALOR DE LA BANDERA DEL ASEGURADO-->",banderaAsegurado);
								_genero = record.get('GENERO');	// (EGS)
								_fenacimi = record.get('FENACIMI');	// (EGS)
								calculaEdad();
								storeTiposICDPrimario.removeAll();	// (EGS)
								storeTiposICDPrimario.load({ // (EGS)
									params:{
									'params.cdramo' 	: _tipoProducto,
									'params.cdtipsit' 	: _cdtipsitProducto,
									'params.edad' 		: _edad,
									'params.genero' 	: record.get('GENERO') //_genero
									}
				
								});
								storeTiposICDSecundario.removeAll();	// (EGS)
								storeTiposICDSecundario.load({			// (EGS)
									params: {
										'params.cdramo'		: _tipoProducto,
										'params.cdtipsit'	: _cdtipsitProducto,
										'params.edad'		: _edad,
										'params.genero'		: record.get('GENERO')
									}
								});
								
                                storeTiposEvento.removeAll();
                                storeTiposEvento.load({
                                    params:{
                                    'params.cdramo'     : record.get('CDRAMO'),
                                    'params.cdtipsit'   : record.get('CDTIPSIT'),
                                    'params.cdgarant'   : record.get('CDGARANT'),
                                    'params.cveCatalogo'     : "1"
                                    }
                                });
                                
                                storeAltasHospital.removeAll();
                                storeAltasHospital.load({
                                    params:{
                                    'params.cdramo'     : record.get('CDRAMO'),
                                    'params.cdtipsit'   : record.get('CDTIPSIT'),
                                    'params.cdgarant'   : record.get('CDGARANT'),
                                    'params.cveCatalogo'     : "2"
                                    }
                                });
								
								
								if(_11_params.CDRAMO != _RECUPERA){
									debug("<<<<<======  DIFERENTE DE RECUPERA	======>>>>>>>");
									if (banderaConcepto == "1"){
										debug("1.- Guardamos los conceptos.");
										_guardarConceptosxFactura();
										storeConceptos.removeAll();
										
									}else if(banderaAsegurado == "1"){
										debug("2.- Guardamos los datos complementarios.");
										guardaDatosComplementariosAsegurado(_11_aseguradoSeleccionado, banderaAsegurado);
										storeConceptos.removeAll();
									}else{
										debug("3.- Validamos si requiere autorizaciones especiales para eso verificamos si ya tiene Id.Siniestro / Autorizacion especial.");
										numSiniestro = record.get('NMSINIES');
										numAutServ   = record.get('NMAUTSER');
										if(+numAutServ  > 0){
											Ext.getCmp('historialCPT').enable();
										}else{
											Ext.getCmp('historialCPT').disable();
										}
										debug("4.- Validamos que exista  Id. Siniestros, si no lo mandamos a ejecutar. ");
										if(numSiniestro.length == "0"){
											revisarDocumento(grid,index);
										}else{
											debug("5.- Validamos que si requiere autorizacion especial. ");
											if((record.get('REQAUTES') == 1 && record.get('NMAUTESP') == 0) ||(record.get('REQAUTESPECIAL') == 1 && record.get('NMAUTESP') == 0 )){
												reqAutorizacionEspecial(panelInicialPral.down('[name=params.ntramite]').getValue(), _11_params.OTVALOR02, 
														panelInicialPral.down('[name=params.nfactura]').getValue(), record.get('CDUNIECO'), record.get('CDRAMO'),
														record.get('ESTADO'), record.get('NMPOLIZA'), record.get('NMSUPLEM'), record.get('NMSITUAC'),
														record.get('NMSINIES'), record.get('CDPERSON'), record.get('CDTIPSIT'));
											}
											
											if(record.get('REQAUTES') == 1 && record.get('NMAUTESP') > 0){
												Ext.Ajax.request( {
													url	 : _URL_INF_AUT_ESPECIFICO 
													,params:{
														'params.nmautespecial'  : record.get('NMAUTESP')
													}
													,success : function (response) {
														//Obtenemos los datos
														if(Ext.decode(response.responseText).datosValidacion != null){
															var jsonValidacion =Ext.decode(response.responseText).datosValidacion;
															debug("jsonValidacion ", jsonValidacion);
															if(+jsonValidacion[0].VALRANGO <= 0){
																debug("Valor 1 ==>");
																if (_11_fueraVigencia == "1"){	//(EGS) agregamos if para que solo muestre el mensaje si siniestro fuera de vigencia y no hay autorizacion especial por fuera de vigencia
																	mensajeWarning('La autorizaci&oacute;n '+jsonValidacion[0].NMAUTESP+' no contempla el rango de fecha.<br/> '+
																	'Favor de validarlo con el Gerente de Siniestros.',function(){
																	cargarPaginacion(panelInicialPral.down('[name=params.ntramite]').getValue(),panelInicialPral.down('[name=params.nfactura]').getValue());
																	});
																}	// (EGS)
															}
															
															if(+jsonValidacion[0].VALRANGO > 0){
																debug("Valor 2 ==>");
																if(record.get('REQAUTESPECIAL') == '1' )
																{
																	debug("Valor 3 ==>");
																	if(jsonValidacion[0].VALCOBER =="0"){
																		mensajeWarning('La autorizaci&oacute;n '+jsonValidacion[0].NMAUTESP+' no incluye  la cobertura NO amparada.<br/> '+
																			'Favor de validarlo con el Gerente de operaci&oacute;n de siniestros.');
																	}else{
																		mensajeWarning('La autorizaci&oacute;n '+jsonValidacion[0].NMAUTESP+' incluye la cobertura: '+jsonValidacion[0].DSGARANT+
																		'<br/>Favor de realizar el cambio.');
																	}
																}																
															}															
														}
													},
													failure : function () {
														//me.up().up().setLoading(false);
														Ext.Msg.show({
															title:'Error',
															msg: 'Error de comunicaci&oacute;n',
															buttons: Ext.Msg.OK,
															icon: Ext.Msg.ERROR
														});
													}
												});											
											}else{
												if(record.get('REQAUTESPECIAL') == '1' && record.get('NMAUTESP') > 0 ) {
													Ext.Ajax.request( {
														url	 : _URL_INF_AUT_ESPECIFICO 
														,params:{
															'params.nmautespecial'  : record.get('NMAUTESP')
														}
														,success : function (response) {
															//Obtenemos los datos
															debug("Valor de respuesta ==> ",Ext.decode(response.responseText).datosValidacion);
															if(Ext.decode(response.responseText).datosValidacion != null){
																var jsonValidacion =Ext.decode(response.responseText).datosValidacion;
																debug("Valor de respuesta ==> ",jsonValidacion);
																if(+jsonValidacion[0].VALCOBER <= 0){
																	mensajeWarning('La autorizaci&oacute;n '+jsonValidacion[0].NMAUTESP+' no incluye la cobertura no amparada.<br/> '+
																		'Favor de validarlo con el Gerente de operaci&oacute;n de siniestros.');
																}
																if(+jsonValidacion[0].VALCOBER > 0){
																	mensajeWarning('La autorizaci&oacute;n '+jsonValidacion[0].NMAUTESP+' incluye la cobertura: '+jsonValidacion[0].DSGARANT+
																	'<br/>Favor de realizar el cambio.');
																}
															}
														},
														failure : function () {
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
											
											storeConceptos.removeAll();
											debug("VALOR DEL RECORD  ===> ",record);
											storeConceptos.load({
												params: {
													'params.nfactura'  : panelInicialPral.down('[name=params.nfactura]').getValue(),
													'params.cdunieco'  : record.get('CDUNIECO'),
													'params.cdramo'	: record.get('CDRAMO'),
													'params.estado'	: record.get('ESTADO'),
													'params.nmpoliza'  : record.get('NMPOLIZA'),
													'params.nmsituac'  : record.get('NMSITUAC'),
													'params.nmsuplem'  : record.get('NMSUPLEM'),
													'params.status'	: record.get('STATUS'),
													'params.aaapertu'  : record.get('AAAPERTU'),
													'params.nmsinies'  : record.get('NMSINIES')
												}
											});
											
											Ext.Ajax.request( {
												url	 : _URL_CONCEPTOSASEG
												,params:{
													'params.nfactura'  : panelInicialPral.down('[name=params.nfactura]').getValue(),
													'params.cdunieco'  : record.get('CDUNIECO'),
													'params.cdramo'	: record.get('CDRAMO'),
													'params.estado'	: record.get('ESTADO'),
													'params.nmpoliza'  : record.get('NMPOLIZA'),
													'params.nmsituac'  : record.get('NMSITUAC'),
													'params.nmsuplem'  : record.get('NMSUPLEM'),
													'params.status'	: record.get('STATUS'),
													'params.aaapertu'  : record.get('AAAPERTU'),
													'params.nmsinies'  : record.get('NMSINIES')
												}
												,success : function (response) {
													//Obtenemos los datos
													var conceptos = Ext.decode(response.responseText).loadList;
													var i = 0;
													var totalConsumido = 0;
													for(i = 0; i < conceptos.length; i++){
														totalConsumido = (+ totalConsumido) + (+ conceptos[i].SUBTAJUSTADO);
													}
													debug("Total Consumido ===>>>> ",totalConsumido,record);
													obtenerSumaAseguradaMontoGastados (
														record.get('CDUNIECO'), record.get('CDRAMO'),record.get('ESTADO'),record.get('NMPOLIZA'), 
														record.get('NMSUPLEM'), record.get('NMSITUAC'), record.get('CDGARANT'), record.get('CDCONVAL'),
														record.get('CDPERSON'), record.get('NMSINREF'), totalConsumido, record.get('NMSINIES'), 
														record.get('VALTOTALCOB'), record.get('SWFONSIN'), record.get('NUMRECLA'));
												},
												failure : function () {
													Ext.Msg.show({
														title:'Error',
														msg: 'Error de comunicaci&oacute;n',
														buttons: Ext.Msg.OK,
														icon: Ext.Msg.ERROR
													});
												}
											});
											
											if(record.get('FLAGREQAUT') == "SI" &&  (  record.get('NMAUTSER') =="N/A" || +record.get('NMAUTSER') <= '0' || record.get('NMAUTSER')== "")){
												debug("Entra a la configuración");
											     _11_obtieneDatosOpcionalesValor(record.get('CDRAMO'),record.get('CDTIPSIT'),record.get('CDGARANT'),record.get('CDCONVAL'),record, "1");	
											}
										}
									}
								}else{
									debug("<<<<<======  RECUPERA	======>>>>>>>");
									storeRecupera.load({
										params: {
											'params.cdunieco'	: record.get('CDUNIECO'),
											'params.cdramo'		: record.get('CDRAMO'),
											'params.estado'		: record.get('ESTADO'),
											'params.nmpoliza'	: record.get('NMPOLIZA'),
											'params.nmsituac'	: record.get('NMSITUAC'),
											'params.nmsuplem'	: record.get('NMSUPLEM'),
											'params.ntramite'	: panelInicialPral.down('[name=params.ntramite]').getValue(),
											'params.nfactura'	: panelInicialPral.down('[name=params.nfactura]').getValue()
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
				
				Ext.define('EditorConceptos', {
					extend: 'Ext.grid.Panel',
					name:'editorConceptos',
					title: 'DETALLE DEL ASEGURADO (CONCEPTOS)',
					icon		: '${ctx}/resources/fam3icons/icons/paste_plain.png',
					frame: true,
					selType  : 'rowmodel',
					initComponent: function(){
						Ext.apply(this, {
						//width: 850,
						height: 250,
						plugins  :
						[
							Ext.create('Ext.grid.plugin.CellEditing', {
								clicksToEdit: 1
								,listeners : {
									beforeedit : function() {
										_11_conceptoSeleccionado = gridEditorConceptos.getView().getSelectionModel().getSelection()[0];
										storeConceptosCatalogo.proxy.extraParams= {
											'params.idPadre' :  _11_conceptoSeleccionado.get('IDCONCEP')
											,catalogo		: _CATALOGO_CONCEPTOSMEDICOS
										};
									}
								}
							})
						],
						store: storeConceptos,
						columns: 
						[
							{
								xtype		 : 'actioncolumn'
								,menuDisabled : true
								,width		: 70
								,items		:
								[
									{
										icon:_CONTEXT+'/resources/fam3icons/icons/delete.png',
										tooltip: 'Eliminar',
										scope: this,
										handler: this.onRemoveClick
									}
									,{
										icon	 : '${ctx}/resources/fam3icons/icons/page_edit.png'
										,tooltip : 'Ajuste'
										,disabled: _11_params.STATUS == _STATUS_TRAMITE_CONFIRMADO && !(_GLOBAL_CDSISROL == _COORDINADOR_REC || _GLOBAL_CDSISROL == _GERENTE_REC) ? true : false  // (EGS)  
										,handler : _mostrarVentanaAjustes
									}
								]
							},
							
							{
								dataIndex : 'NMORDINA'
								,width : 20
								//,hidden: true
							},
							{
								header: 'Tipo Concepto', 				dataIndex: 'IDCONCEP',	width : 90		,  allowBlank: false
								,editor : cmbCveTipoConcepto
								,renderer : function(v) {
								var leyenda = '';
									if (typeof v == 'string') {
										storeTipoConcepto.each(function(rec) {
											if (rec.data.key == v) {
												leyenda = rec.data.value;
											}
										});
									}else {
										if (v.key && v.value) {
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
									if (typeof v == 'string'){
										if(storeConceptosCatalogoRender.cargado) {
											storeConceptosCatalogoRender.each(function(rec) {
												if (rec.data.key == v) {
													leyenda = rec.data.value;
												}
											});
										}
										else{
										    leyenda='Cargando...';
										}
									}else{
										if (v.key && v.value){
											leyenda = v.value;
										} else {
											leyenda = v.data.value;
										}
										leyenda= v;
									}
									return leyenda;
								}
							},
							{
								header: 'Aplica IVA', 				dataIndex: 'APLICIVA',	width : 90		,  allowBlank: false
								,editor : cmbAplicaIVA
								,renderer : function(v) {
								var leyenda = '';
									if (typeof v == 'string') {
										storeAplicaIVA.each(function(rec) {
											if (rec.data.key == v) {
												leyenda = rec.data.value;
											}
										});
									}else {
										if (v.key && v.value) {
											leyenda = v.value;
										} else {
											leyenda = v.data.value;
										}
									}
									return leyenda;
								}
							},
							{
								header: 'Valor Arancel', 				dataIndex: 'PTMTOARA',	width : 100,				renderer: Ext.util.Format.usMoney
								,editor: {
									xtype: 'numberfield',
									decimalSeparator :'.',
									allowBlank: false,
									listeners : {
										change:function(e){
											var valorArancel = e.getValue();
											banderaConcepto = 1;
											_11_conceptoSeleccionado.set('PTPRECIO',valorArancel);
											var cantidad = _11_conceptoSeleccionado.get('CANTIDAD');
											var importe = _11_conceptoSeleccionado.get('PTPRECIO');
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
							},
							{
								header: 'Precio Concepto', 				dataIndex: 'PTPRECIO',	width : 100,				renderer: Ext.util.Format.usMoney
								,editor: {
									xtype: 'numberfield',
									decimalSeparator :'.',
									allowBlank: false,
									listeners : {
										change:function(e){
											var cantidad = _11_conceptoSeleccionado.get('CANTIDAD');
											banderaConcepto = 1;
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
							},
							{
								header: 'IVA', 				dataIndex: 'PTIVA',	width : 100,				renderer: Ext.util.Format.usMoney
								,hidden : (_cdtipoProceso  != "1")
								,editor : {
									xtype: 'numberfield',
									decimalSeparator :'.',
									allowBlank: false
								}
							}
							,
							{
								header: 'Cantidad', 				dataIndex: 'CANTIDAD',		width : 60//,				renderer: Ext.util.Format.usMoney
								,editor: {
									xtype: 'numberfield',
									allowBlank: false,
									listeners : {
										change:function(e){
											var cantidad = e.getValue();
											banderaConcepto = 1;
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
								header : 'Descuento (%)',			dataIndex : 'DESTOPOR',		width : 100
								,editor: {
									xtype: 'numberfield',
									decimalSeparator :'.',
									allowBlank: false,
									listeners : {
										change:function(e){
											banderaConcepto = 1;
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
								header : 'Descuento ($)',			dataIndex : 'DESTOIMP',		width : 100
								,editor: {
									xtype: 'numberfield',
									decimalSeparator :'.',
									allowBlank: false,
									listeners : {
										change:function(e){
											banderaConcepto = 1;
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
								width : 120,
								renderer : Ext.util.Format.usMoney
							},{
								header : 'Subtotal Factura',
								dataIndex : 'PTIMPORT',
								width : 120,
								renderer : Ext.util.Format.usMoney
							},{
								header : 'Subtotal Ajustado',
								dataIndex : 'SUBTAJUSTADO',
								width : 120,
								renderer : Ext.util.Format.usMoney
							}
						],
						selModel: {
							selType: 'cellmodel'
						},
						tbar:[
								{
									text	: 'Agregar Concepto'
									,icon:_CONTEXT+'/resources/fam3icons/icons/book.png'
									,disabled: _11_params.STATUS == _STATUS_TRAMITE_CONFIRMADO && !(_GLOBAL_CDSISROL == _COORDINADOR_REC || _GLOBAL_CDSISROL == _GERENTE_REC) ? true : false  // (EGS)  
									,handler : _p21_agregarConcepto
									//, hidden : (_11_params.CDTIPTRA == _TIPO_PAGO_AUTOMATICO)
								},
								{
									text	: 'Guardar Concepto'
									,icon:_CONTEXT+'/resources/fam3icons/icons/disk.png'
									,disabled: _11_params.STATUS == _STATUS_TRAMITE_CONFIRMADO && !(_GLOBAL_CDSISROL == _COORDINADOR_REC || _GLOBAL_CDSISROL == _GERENTE_REC) ? true : false  // (EGS)  
									,handler : function() {
										_guardarConceptosxFactura();
									}
								},
								{
									text	: 'Historial Autorizaci&oacute;n'
									,icon	: _CONTEXT+'/resources/fam3icons/icons/disk.png'
									,id		: 'historialCPT'
									,name  	: 'historialCPT'
									,hidden : _tipoPago != _TIPO_PAGO_DIRECTO
									,handler : function() {
										//_guardarConceptosxFactura();
										debug("numAutServ   ===> :",numAutServ);
										var windowHistSinies = Ext.create('Ext.window.Window',{
											modal       : true,
											buttonAlign : 'center',
											width       : 850,
											height      : 500,
											autoScroll  : true,
											loader      : {
												url     : _URL_LOADER_HISTORIAL_CPTPAGADO,
												params  : {
													'params.nmautser'  : numAutServ 
												},
												scripts  : true,
												loadMask : true,
												autoLoad : true,
												ajaxOptions: {
													method: 'POST'
												}
											},
											buttons: [{
												icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
												text: 'Cerrar',
												handler: function() {windowHistSinies.close();}
											}]
										}).show();
										centrarVentanaInterna(windowHistSinies);
										
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
						banderaConcepto = "1";
						//_guardarConceptosxFactura();
					}
				});
				gridEditorConceptos = new EditorConceptos();

				Ext.define('EditorCoberturaRecupera', {
				extend: 'Ext.grid.Panel',
				name:'editorCoberturaRecupera',
				title: 'RECUPERA',
				icon		: '${ctx}/resources/fam3icons/icons/paste_plain.png',
				frame: true,
				selType  : 'rowmodel',
				initComponent: function(){
					Ext.apply(this, {
					height: 250,
					plugins  :
					[
						Ext.create('Ext.grid.plugin.CellEditing', {
							clicksToEdit: 1
							,listeners : {
								beforeedit : function() {
									_11_aseguradoSeleccionado = gridFacturaDirecto.getView().getSelectionModel().getSelection()[0];
									_11_conceptoSeleccionado = gridEditorCoberturaRecupera.getView().getSelectionModel().getSelection()[0];
									debug("VALOR DE _11_conceptoSeleccionado --->>>>>>",_11_conceptoSeleccionado);
									
									storeCoberturaRecupera.proxy.extraParams= {
										'params.cdunieco':_11_aseguradoSeleccionado.get('CDUNIECO'),
										'params.estado':_11_aseguradoSeleccionado.get('ESTADO'),
										'params.cdramo':_11_aseguradoSeleccionado.get('CDRAMO'),
										'params.nmpoliza':_11_aseguradoSeleccionado.get('NMPOLIZA'),
										'params.nmsituac':_11_aseguradoSeleccionado.get('NMSITUAC')
									};
									
									storeSubcoberturaRecupera.load({
										params:{
											'params.cdunieco':_11_aseguradoSeleccionado.get('CDUNIECO'),
											'params.cdramo':_11_aseguradoSeleccionado.get('CDRAMO'),
											'params.estado':_11_aseguradoSeleccionado.get('ESTADO'),
											'params.nmpoliza':_11_aseguradoSeleccionado.get('NMPOLIZA'),
											'params.nmsituac':_11_aseguradoSeleccionado.get('NMSITUAC'),
											'params.cdtipsit':_11_aseguradoSeleccionado.get('CDTIPSIT'),
											'params.cdgarant' :_11_conceptoSeleccionado.get('CDGARANT'),
											'params.cdsubcob' :null//_11_conceptoSeleccionado.get('CDCONVAL')
										}
									});
								}
							}
						})
					],
					store: storeRecupera,
					columns: 
					[
						{
							xtype		 : 'actioncolumn'
							,menuDisabled : true
							,width		: 70
							,items		:
							[
								{
									icon:_CONTEXT+'/resources/fam3icons/icons/delete.png',
									tooltip: 'Eliminar',
									scope: this,
									handler: this.onRemoveClick
								}
							]
						},
						{
							header: 'Cobertura',			dataIndex: 'CDGARANT',		allowBlank: false
							,editor: coberturaRecupera
							,renderer : function(v) {
								var leyenda = '';
								if (typeof v == 'string') {
									if(sstoreCoberturaRecuperaRender.cargado) {
										debug("sstoreCoberturaRecuperaRender :",sstoreCoberturaRecuperaRender);
										sstoreCoberturaRecuperaRender.each(function(rec) {
											if (rec.data.key == v) {
												leyenda = rec.data.value;
											}
										});
									}
									else{
										leyenda='Cargando...';
									}
								}else {
									if (v.key && v.value) {
										leyenda = v.value;
									} else {
										leyenda = v.data.value;
									}
									leyenda= v;
								}
								return leyenda;
							}
						},
						{
							header: 'Subcobertura',			dataIndex: 'CDCONVAL',	allowBlank: false
							,editor: subCoberturaRecupera
							,renderer : function(v) {
								var leyenda = '';
								if (typeof v == 'string') {
									//debug("Valor de V : "+v);
									//debug("Valor de storeSubcoberturaAseguradoRender.cargado : "+storeSubcoberturaAseguradoRender.cargado);
									if(storeSubcoberturaRecuperaRender.cargado) {
										debug("storeSubcoberturaAseguradoRender");
										debug(storeSubcoberturaRecuperaRender);
										storeSubcoberturaRecuperaRender.each(function(rec) {
											//debug("rec.data.key -->",rec.data.key,"rec.data.value -->",rec.data.value);
											if (rec.data.key == v){
												leyenda = rec.data.value;
											}
										});
									}
									else{
									    leyenda='Cargando...';
									}
								}else{
									if (v.key && v.value){
										leyenda = v.value;
									} else {
										leyenda = v.data.value;
									}
									leyenda= v;
								}
								return leyenda;
							}
						},
						
						{
							header : 'Esquema de Suma',
							dataIndex : 'ESQUEMAASEG',
							width : 120
						},{
							header : 'Suma Asegurada',
							dataIndex : 'SUMAASEG',
							width : 120,
							renderer : Ext.util.Format.usMoney
						},
						{
							header: '% Pago', 				dataIndex: 'CANTPORC',		width : 60
							,editor: {
								xtype: 'numberfield',
								decimalSeparator :'.',
								//allowBlank: false,
								listeners : {
									change:function(e){
										var porcentajePago = e.getValue();
										var esquema = _11_conceptoSeleccionado.get('ESQUEMAASEG');
										var sumaAsegurada = _11_conceptoSeleccionado.get('SUMAASEG');
										var ImporteConcepto = ((+esquema * +sumaAsegurada) * (+porcentajePago/100));
										_11_conceptoSeleccionado.set('PTIMPORT',ImporteConcepto);
									}
								}
							}
						},{
							header : 'Total a Pagar',
							dataIndex : 'PTIMPORT',
							width : 120,
							renderer : Ext.util.Format.usMoney
						}
					],
					selModel: {
						selType: 'cellmodel'
					},
					tbar:[
							{
								text	: 'Agregar Conceptos'
								,icon:_CONTEXT+'/resources/fam3icons/icons/book.png'
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
					//banderaConcepto = "1";
				}
				});
				gridEditorCoberturaRecupera = new EditorCoberturaRecupera();

				gridAutorizacionMod = Ext.create('Ext.grid.Panel',
				{
					id			 : 'clausulasGridId'
					,store		 :  storeListadoAutorizacion
					//,collapsible   : true
					//,titleCollapse : true
					,style		 : 'margin:5px'
					,selType: 'checkboxmodel'
					,width   : 600
					,height: 400
					,selModel: { selType: 'checkboxmodel', mode: 'SINGLE', checkOnly: true }
					,columns	   :
					[
						{
							 header	 : 'N&uacute;mero <br/> Autorizaci&oacute;n'
							 ,dataIndex : 'NMAUTSER'
							 ,width	 	: 100
						},
						{
							 header	 : 'Fecha Solicitud'
							 ,dataIndex : 'FESOLICI'
							 ,width	 : 100
						},
						{
							 header	 : 'clave Proveedor'
							 ,dataIndex : 'CDPROVEE'
							 ,width	 : 100
						 },
						{
							 header	 : 'Nombre Proveedor'
							 ,dataIndex : 'NOMPROV'
							 ,width	 : 300
						}
					 ],
					 bbar	 :
					 {
						 displayInfo : true,
						 store	   : storeListadoAutorizacion,
						 xtype	   : 'pagingtoolbar'
					 },
					//aqui va el listener
					listeners: {
						itemclick: function(dv, record, item, index, e) {
							_11_textfieldNmautservMod.setValue(record.get('NMAUTSER'));
						}
					}
					
						
				});
				
				gridAutorizacion= Ext.create('Ext.grid.Panel',
				{
					id			 : 'clausulasGridId'
					,store		 :  storeListadoAutorizacion
					//,collapsible   : true
					//,titleCollapse : true
					,style		 : 'margin:5px'
					,selType: 'checkboxmodel'
					,width   : 600
					,height: 400
					,selModel: { selType: 'checkboxmodel', mode: 'SINGLE', checkOnly: true }
					,columns	   :
					[
						{
							 header	 : 'N&uacute;mero <br/> Autorizaci&oacute;n'
							 ,dataIndex : 'NMAUTSER'
							 ,width	 	: 100
						},
						{
							 header	 : 'Fecha Solicitud'
							 ,dataIndex : 'FESOLICI'
							 ,width	 : 100
						}
						,
						{
							 header	 : 'Nombre Proveedor'
							 ,dataIndex : 'NOMPROV'
							 ,width	 : 300
						},
						{
							 header	 : 'Causa Siniestro'
							 ,dataIndex : 'DSCAUSA'
							 ,width	 : 100
						},
						{
							 header	 : 'Diagn&oacute;stico'
							 ,dataIndex : 'DSICD'
							 ,width	 : 300
						}
					 ],
					 bbar	 :
					 {
						 displayInfo : true,
						 store	   : storeListadoAutorizacion,
						 xtype	   : 'pagingtoolbar'
					 },
					//aqui va el listener
					listeners: {
						itemclick: function(dv, record, item, index, e) {
							_11_textfieldNmautserv.setValue(record.get('NMAUTSER'));
						}
					}
					
						
				});

				gridMsiniestMaestro = Ext.create('Ext.grid.Panel',
				{
					id			 : 'msiniestMaestroGridId'
					,store		 :  storeListadoSiniestMaestro
					,style		 : 'margin:5px'
					,selType: 'checkboxmodel'
					,width   : 600
					,height: 200
					,selModel: { selType: 'checkboxmodel', mode: 'SINGLE', checkOnly: true }
					,columns	   :
					[
						 {
							 header	 : 'N&uacute;mero <br/> Siniestro'
							 ,dataIndex : 'NMSINIES'
							 ,width	 	: 100
						 },
						 {
							 header	 : 'Causa Siniestro'
							 ,dataIndex : 'CDCAUSA'
							 ,width	 : 100
							 //,hidden : true
						 }
						 ,
						 {
							 header	 : 'Causa <br/> Siniestro'
							 ,dataIndex : 'DSCAUSA'
							 ,width	 : 100
						 }
						 ,
						 {
							 header	 : 'Id ICD'
							 ,dataIndex : 'CDICD'
							 ,width	 : 300
							 ,hidden : true
						 },
						 {
							 header	 : 'ICD'
							 ,dataIndex : 'DSICD'
							 ,width	 : 300
						 },
						 {
							 header	 : 'Fecha <br/> Ocurrencia'
							 ,dataIndex : 'FEOCURRE'
							 ,width	 : 100
						 },
						 {
							 header	 : 'Fecha <br/> Apertura'
							 ,dataIndex : 'FEAPERTU'
							 ,width	 : 100
						 }
					 ],
					 bbar	 :
					 {
						 displayInfo : true,
						 store	   : storeListadoSiniestMaestro,
						 xtype	   : 'pagingtoolbar'
					 },
					//aqui va el listener
					listeners: {
						itemclick: function(dv, record, item, index, e) {
							debug("VALOR RECORD-->",record);
							_11_textfieldNmSiniest.setValue(record.get('NMSINIES'));
						}
					}	
				});
				
				panelComplementos = Ext.create('Ext.form.Panel',
				{
					border	: 0
					,layout	 :
					{
						type	 : 'table'
						,columns : 2
					}
					,defaults 	:
					{
						style : 'margin:5px;'
					}
					,items	:
					[
						{
							xtype		: 'displayfield',			fieldLabel	: 'Suma Asegurada',			name	: 'params.sumaAsegurada', value : '0.00', 	//hidden : _tipoProducto != _GMMI,
    	    	    		valueToRaw : function(value){
	    	                    return Ext.util.Format.usMoney(value);
	    	                }
						},
						{
							xtype		: 'displayfield',			fieldLabel	: 'Suma Disponible',			name	: 'params.sumaGastada', value : '0.00',		//hidden : _tipoProducto != _GMMI,
    	    	    		valueToRaw : function(value){
	    	                    return Ext.util.Format.usMoney(value);
	    	                }
						},
						{
							xtype		: 'displayfield',			fieldLabel	: 'Sub L&iacute;mite',			name	: 'params.sublimite', value : '0.00',
    	    	    		valueToRaw : function(value){
	    	                    return Ext.util.Format.usMoney(value);
	    	                }
						},
						{
							 xtype		: 'displayfield',			fieldLabel	: 'Pagado',			name	: 'params.pagado', value : '0.00',
    	    	    		valueToRaw : function(value){
	    	                    return Ext.util.Format.usMoney(value);
	    	                }
						},
						{
							xtype		: 'displayfield',			fieldLabel	: 'Disponible',			name	: 'params.disponibleCob', value : '0.00',
    	    	    		valueToRaw : function(value){
	    	                    return Ext.util.Format.usMoney(value);
	    	                },colspan:2
						},
						{
    	    	    		xtype       : 'displayfield',		fieldLabel : 'Subtotal Factura',		name	: 'params.subtotalFac', value : '0.00',
    	    	    		valueToRaw : function(value){
	    	                    return Ext.util.Format.usMoney(value);
	    	                }
	    	    	    },
						{
							xtype		: 'displayfield',			fieldLabel	: 'IVA Factura',			name	: 'params.ivaFac',	 value : '0.00',
							valueToRaw : function(value){
	    	                    return Ext.util.Format.usMoney(value);
	    	                }
						},
						{
							xtype		: 'displayfield',			fieldLabel	: 'IVA Ret. Factura',		name	: 'params.ivaRetFac', value : '0.00',
							valueToRaw : function(value){
	    	                    return Ext.util.Format.usMoney(value);
	    	                }
						},
						{
							xtype		: 'displayfield',			fieldLabel	: 'ISR Factura',			name	: 'params.isrFac', value : '0.00',
							valueToRaw : function(value){
	    	                    return Ext.util.Format.usMoney(value);
	    	                }
						},
						{
							xtype		: 'displayfield',			fieldLabel	: 'Imp. Cedular Factura',		name	: 'params.impCedularFac', value : '0.00',
							valueToRaw : function(value){
	    	                    return Ext.util.Format.usMoney(value);
	    	                }
						},
						{
							xtype		: 'displayfield',			fieldLabel	: 'Importe Pagar Factura',			name	: 'params.impPagarFac', value : '0.00',
							valueToRaw : function(value){
	    	                    return Ext.util.Format.usMoney(value);
	    	                }
						}
					]
				});
				
				for(var i=0;i<panelComplementos.items.items.length;i++)
				{
					panelComplementos.items.items[i].labelWidth =200;
					panelComplementos.items.items[i].style	  = 'margin-right:100px;';
				}
				
				panelInicialPral= Ext.create('Ext.form.Panel',
				{
					border	: 0
					,layout	 :
					{
						type	 : 'table'
						,columns : 2
					}
					,defaults 	:
					{
						//style : 'margin:5px;',
						bodyStyle:'padding: 10px'
					}
					,items	:
					[
						{
							xtype		: 'textfield',			fieldLabel	: 'NO. TR&Aacute;MITE',		name	: 'params.ntramite', readOnly   : true, hidden: true
						},
                        {
							xtype		: 'textfield',			fieldLabel	: 'CDTIPTRA',				name	: 'params.cdtiptra', value   : _11_params.CDTIPTRA, hidden: true
						},
						{
							xtype		: 'textfield',			fieldLabel	: 'CONTRARECIBO',			name	: 'params.contrarecibo', readOnly   : true
						},
						{
							xtype		: 'textfield',			fieldLabel	: 'NO. FACTURA ORIGINAL',	name	: 'params.nfacturaOrig', readOnly   : true, hidden: true 
						},
						{
							xtype		: 'textfield',			fieldLabel	: 'NO. FACTURA',			name	: 'params.nfactura', readOnly   : (_11_params.CDTIPTRA != _TIPO_PAGO_AUTOMATICO)
						},
						{
							xtype		: 'datefield',			fieldLabel	: 'FECHA FACTURA',			name	: 'params.fefactura',	format	: 'd/m/Y'
						},
						{
							xtype		: 'datefield',			fieldLabel	: 'FECHA EGRESO',			name	: 'params.feegreso',	format	: 'd/m/Y',		allowBlank : false//,  hidden: true
						},
						{
							xtype		: 'numberfield',		fieldLabel 	: 'DEDUCIBLE (D&Iacute;AS)',name	: 'params.diasdedu',		allowBlank : false
						},
						cmbProveedor,
						comboTipoAte,
						cobertura,
						subCobertura,
						cmbTipoMoneda,
						{
							xtype		: 'numberfield',		fieldLabel 	: 'TASA CAMBIO',			name	: 'params.tasacamb',
							allowBlank	: false,				allowDecimals :true	,					decimalSeparator :'.',
							listeners : {
								'change':function(e){
									if(panelInicialPral.down('combo[name=params.tipoMoneda]').getValue() !="001"){
										var tasaCambio = e.getValue();
										var montoFactura = panelInicialPral.down('[name=params.ptimporta]').getValue();
										var montoFacturamxn = +tasaCambio * +montoFactura
										panelInicialPral.down('[name=params.ptimport]').setValue(montoFacturamxn);
									}
									
								}
							}
						},
						{
							xtype		: 'numberfield',		fieldLabel 	: 'IMPORTE FACTURA',		name	: 'params.ptimporta',
							allowBlank	: false,				allowDecimals :true	,					decimalSeparator :'.',
							listeners : {
								'change':function(e){
									if(panelInicialPral.down('combo[name=params.tipoMoneda]').getValue() !="001"){
										var tasaCambio = panelInicialPral.down('[name=params.tasacamb]').getValue();
										var montoFactura = e.getValue();
										var montoFacturamxn = +tasaCambio * +montoFactura
										panelInicialPral.down('[name=params.ptimport]').setValue(montoFacturamxn);
									}
									
								}
							}
						},
						{
							xtype		: 'numberfield',		fieldLabel 	: 'IMPORTE MXN',			name	: 'params.ptimport',
							allowBlank	: false,				allowDecimals :true	,					decimalSeparator :'.'
						},
						{
							xtype		: 'numberfield',		fieldLabel 	: 'DESCUENTO %',			name	: 'params.descporc',
							allowBlank	: false,				allowDecimals :true	,					decimalSeparator :'.'
						},
						{
							xtype		: 'numberfield',		fieldLabel 	: 'DESCUENTO $',			name	: 'params.descnume',
							allowBlank	: false,				allowDecimals :true	,					decimalSeparator :'.'
						},
						aplicaISR,
						aplicaICE,
						<s:property value='%{"," + imap.tatrisinItems}' />
						<s:property value='%{"," + imap.itemsEdicion}' />
					]
					,buttonAlign:'center'
					,buttons: [ {
							text:'Aplicar Cambios Factura',
							icon:_CONTEXT+'/resources/fam3icons/icons/disk.png',
							disabled: _11_params.STATUS == _STATUS_TRAMITE_CONFIRMADO && !(_GLOBAL_CDSISROL == _COORDINADOR_REC || _GLOBAL_CDSISROL == _GERENTE_REC) ? true : false  // (EGS)  
							,handler:function() {
								debug("Aplicar Cambios Factura");
								_11_clickAplicarCambiosFactura(); // (EGS) codigo original se convierte en funcion para re-utilizarlo
/*
								var valido = panelInicialPral.isValid();
								if(!valido) {
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
									if(!valido) {
										mensajeWarning(
												'El tr&aacute;mite ser&aacute; cancelado debido a que no ha sido autorizado alguno de los siniestros'
												,function(){
													//_11_windowRechazoSiniestro.show();
													//centrarVentanaInterna(_11_windowRechazoSiniestro);
													_11_rechazarTramiteSiniestro();
												}
										);
									}else{
										//Guardamos la información de la factura
										panelInicialPral.form.submit({
											waitMsg:'Procesando...',	
											url: _URL_GUARDA_CAMBIOS_FACTURA,
											failure: function(form, action) {
												centrarVentanaInterna(mensajeError("Verifica los datos requeridos"));
											},
											success: function(form, action) {
												if(_11_params.CDTIPTRA == _TIPO_PAGO_AUTOMATICO){
													Ext.create('Ext.form.Panel').submit({
														standardSubmit :true
														,params		: {
															'params.ntramite' : _11_params.NTRAMITE
														}
													});
													 panelInicialPral.getForm().reset();
													storeAseguradoFactura.removeAll();
													storeConceptos.removeAll();
												}
												
												
											}
										});
									}
								}
*/
							}
						},
						{
							text:'Cancelar',
							icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
							handler:function()
							{
								Ext.create('Ext.form.Panel').submit( {
									standardSubmit :true
									,params		: {
										'params.ntramite' : panelInicialPral.down('[name=params.ntramite]').getValue()
									}
								});
							}
						}
					]
					
				});
				
				for(var i=0;i<panelInicialPral.items.items.length;i++) {
					panelInicialPral.items.items[i].labelWidth =150;
					panelInicialPral.items.items[i].style	  = 'margin-right:100px;';
				}

				modPolizasAltaTramite = Ext.create('Ext.window.Window', {
					title		: 'Detalle Factura'
					,modal	   : true
					,resizable   : false
					,buttonAlign : 'center'
					,closable	: true
					,closeAction: 'hide'
					,width		 : 900
					//,border	: 0
					/*,layout	 :
					{
						type	 : 'table'
						,columns : 2
					}*/
					,defaults 	:
					{
						style : 'margin:5px;'
					}
					,items	   : 
					[
						panelInicialPral
						,gridFacturaDirecto
						,panelComplementos
						,gridEditorConceptos
						,gridEditorCoberturaRecupera
					],
					listeners:{
						 close:function(){
							 if(true){
								Ext.create('Ext.form.Panel').submit( {
									standardSubmit :true
									,params		: {
										'params.ntramite' : panelInicialPral.down('[name=params.ntramite]').getValue()
									}
								});
								 panelInicialPral.getForm().reset();
								storeAseguradoFactura.removeAll();
								storeConceptos.removeAll();
							 }
						 }
					}
				});

				/************    INICIA PROCESO PARA LA SOLICITUD DEL ALTA DE ASEGURADOS  ************/
				gridPolizasAltaTramite= Ext.create('Ext.grid.Panel', {
					id            : 'polizaGridAltaTramite',
					store         : storeListadoPoliza,
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
					bbar : {
						displayInfo : true,
						store       : storeListadoPoliza,
						xtype       : 'pagingtoolbar'
					},
					listeners: {
							itemclick: function(dv, record, item, index, e){
								_11_fueraVigencia = "0";	// (EGS) reiniciamos valor
								//1.- Validamos que el asegurado este vigente
								if(record.get('desEstatusCliente')=="Vigente") {
									var valorFechaOcurrencia;
									var valorFechaOcu = panelListadoAsegurado.query('datefield[name=dtfechaOcurrencias]')[0].rawValue;
									valorFechaOcurrencia = new Date(valorFechaOcu.substring(6,10)+"/"+valorFechaOcu.substring(3,5)+"/"+valorFechaOcu.substring(0,2));
									
									var valorFechaInicial = new Date(record.get('feinicio').substring(6,10)+"/"+record.get('feinicio').substring(3,5)+"/"+record.get('feinicio').substring(0,2));
									var valorFechaFinal =   new Date(record.get('fefinal').substring(6,10)+"/"+record.get('fefinal').substring(3,5)+"/"+record.get('fefinal').substring(0,2));
									var valorFechaAltaAsegurado = new Date(record.get('faltaAsegurado').substring(6,10)+"/"+record.get('faltaAsegurado').substring(3,5)+"/"+record.get('faltaAsegurado').substring(0,2));
					                Ext.Ajax.request({
					                    url     : _URL_VALIDA_STATUSASEG
					                    ,params:{
					                        'params.cdperson'  : panelListadoAsegurado.down('combo[name=cmbAseguradoAfect]').getValue(),
					                        'params.feoocurre' : valorFechaOcurrencia,
					                        'params.nmpoliza'  : record.get('nmpoliza')
					                    }
					                    ,success : function (response) {
					                        json = Ext.decode(response.responseText);
					                         if(Ext.decode(response.responseText).validacionGeneral =="V"){
												if((valorFechaOcurrencia <= valorFechaFinal) && (valorFechaOcurrencia >= valorFechaInicial)){
													if( valorFechaOcurrencia >= valorFechaAltaAsegurado ) {
														_11_fueraVigencia = "1";	// (EGS) indicamos que siniestro sale de vigencia de la poliza
															panelListadoAsegurado.down('[name="cdUniecoAsegurado"]').setValue(record.get('cdunieco'));
															panelListadoAsegurado.down('[name="cdRamoAsegurado"]').setValue(record.get('cdramo'));
															panelListadoAsegurado.down('[name="estadoAsegurado"]').setValue(record.get('estado'));
															panelListadoAsegurado.down('[name="nmPolizaAsegurado"]').setValue(record.get('nmpoliza'));
															panelListadoAsegurado.down('[name="nmSoliciAsegurado"]').setValue(record.get('nmsolici'));
															panelListadoAsegurado.down('[name="nmSuplemAsegurado"]').setValue(record.get('nmsuplem'));
															panelListadoAsegurado.down('[name="nmSituacAsegurado"]').setValue(record.get('nmsituac'));
															panelListadoAsegurado.down('[name="cdTipsitAsegurado"]').setValue(record.get('cdtipsit'));
															modPolizasAsegurado.hide();
													}else{
														// No se cumple la condición la fecha de ocurrencia es mayor a la fecha de alta de tramite
														Ext.Msg.show({
															title:'Error',
															msg: 'La fecha de ocurrencia es mayor a la fecha de alta del asegurado',
															buttons: Ext.Msg.OK,
															icon: Ext.Msg.ERROR
														});
														panelListadoAsegurado.down('combo[name=cmbAseguradoAfect]').setValue("");
														modPolizasAsegurado.hide();
														//limpiarRegistros();
													}
												}else{
													// La fecha de ocurrencia no se encuentra en el rango de la poliza vigente
													centrarVentanaInterna(Ext.Msg.show({
														title:'Error',
														msg: 'La fecha de ocurrencia no se encuentra en el rango de la p&oacute;liza vigente',
														buttons: Ext.Msg.OK,
														icon: Ext.Msg.ERROR
													}));
													panelListadoAsegurado.down('combo[name=cmbAseguradoAfect]').setValue("");
													modPolizasAsegurado.hide();
												}
				                             }else{
												// La fecha de ocurrencia no se encuentra en el rango de la poliza vigente
												centrarVentanaInterna(Ext.Msg.show({
													title:'Error',
													msg: 'La fecha de ocurrencia no se encuentra en el rango de la p&oacute;liza vigente',
													buttons: Ext.Msg.OK,
													icon: Ext.Msg.ERROR
												}));
												panelListadoAsegurado.down('combo[name=cmbAseguradoAfect]').setValue("");
												modPolizasAsegurado.hide();
				                             }
					                    },
					                    failure : function (){
					                        me.up().up().setLoading(false);
					                        centrarVentanaInterna(Ext.Msg.show({
					                            title:'Error',
					                            msg: 'Error de comunicaci&oacute;n',
					                            buttons: Ext.Msg.OK,
					                            icon: Ext.Msg.ERROR
					                        }));
					                    }
					                });
								}else{
									// El asegurado no se encuentra vigente
									centrarVentanaInterna(Ext.Msg.show({
										title:'Error',
										msg: 'El asegurado de la p&oacute;liza seleccionado no se encuentra vigente',
										buttons: Ext.Msg.OK,
										icon: Ext.Msg.ERROR
									}));
									panelListadoAsegurado.down('combo[name=cmbAseguradoAfect]').setValue("");
									modPolizasAsegurado.hide();
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
				
				var modPolizasAsegurado = Ext.create('Ext.window.Window',
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
				
				var panelListadoAsegurado= Ext.create('Ext.form.Panel',{
					border  : 0
					,startCollapsed : true
					,bodyStyle:'padding:5px;'
					,url: _URL_GUARDA_ASEGURADO
					,items :
					[   			
						{
							xtype      : 'datefield',		name       : 'dtfechaOcurrencias',		fieldLabel : 'Fecha ocurrencia',
							maxValue   :  new Date(),		format		: 'd/m/Y'
						},
						{	
							xtype      : 'textfield',		name       : 'cdUniecoAsegurado',		fieldLabel : 'Unieco',
							hidden:true//,					allowBlank:false
						},
						{	
							xtype      : 'textfield',		name       : 'cdRamoAsegurado',			fieldLabel : 'Ramo',
							hidden:true//,					allowBlank:false
						},
						{	
							xtype      : 'textfield',		name       : 'estadoAsegurado',			fieldLabel : 'Estado',
							hidden:true//,					allowBlank:false
						},
						{	
							xtype      : 'textfield',		name       : 'nmPolizaAsegurado',		fieldLabel : 'Poliza Asegurado',
							hidden:true//,					allowBlank:false
						},
						{	
							xtype      : 'textfield',		name       : 'nmSoliciAsegurado',		fieldLabel : 'No. Solicitud',
							hidden:true//,					allowBlank:false
						},
						{	
							xtype      : 'textfield',		name       : 'nmSuplemAsegurado',		fieldLabel : 'No. Suplement',
							hidden:true//,					allowBlank:false
						},
						{	
							xtype      : 'textfield',		name       : 'nmSituacAsegurado',		fieldLabel : 'No. Situac',
							hidden:true//,					allowBlank:false
						},
						{	
							xtype      : 'textfield',		name       : 'cdTipsitAsegurado',		fieldLabel : 'Cdtipsit',
							hidden:true//,					allowBlank:false
						},
						{
							xtype: 'combo',					name:'cmbAseguradoAfect',				fieldLabel : 'Asegurado',
							displayField : 'value',			valueField   : 'key',					width:500,
							minChars  : 2,					forceSelection : true,					matchFieldWidth: false,
							queryMode :'remote',			queryParam: 'params.cdperson',			store : storeAsegurados,
							triggerAction: 'all',			hideTrigger:true,						allowBlank: false,
							listeners : {
								
								'select' : function(combo, record) {
									var params = {'params.cdperson' : this.getValue(),
												  'params.cdramo' : _11_params.CDRAMO,
												  'params.fe_ocurre' : panelListadoAsegurado.form.getValues().dtfechaOcurrencias	//(EGS)
												  };
									cargaStorePaginadoLocal(storeListadoPoliza, _URL_CONSULTA_LISTADO_POLIZA, 'listaPoliza', params, function(options, success, response){
										if(success){
											var jsonResponse = Ext.decode(response.responseText);
											if(jsonResponse.listaPoliza == null) {
												Ext.Msg.show({
													title: 'Aviso',
					                                //msg: 'No existen p&oacute;lizas para el asegurado elegido.', (EGS)
					                                msg: 'La fecha de ocurrencia del siniestro, no coincide con alguna p&oacute;liza del asegurado',	// (EGS)
													buttons: Ext.Msg.OK,
													icon: Ext.Msg.WARNING
												});
												
												combo.clearValue();
												modPolizasAsegurado.hide();
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
									centrarVentanaInterna(modPolizasAsegurado.show());
								}
							}
						}
					]
				});

				/*PANTALLA EMERGENTE PARA EL PAGO DIRECTO */
				ventanaAgregarAsegurado= Ext.create('Ext.window.Window', {
					title: 'ASEGURADOS',
					//height: 200,
					closeAction: 'hide',
					modal: true, 
					resizable: false,
					items:[panelListadoAsegurado],
					buttonAlign : 'center',
					buttons:[ {
							text: 'Aceptar',
							icon:_CONTEXT+'/resources/fam3icons/icons/accept.png',
							handler: function() {
								if (panelListadoAsegurado.form.isValid()) {
									var datos=panelListadoAsegurado.form.getValues();
									
									if(datos.cmbAseguradoAfect==''|| datos.cmbAseguradoAfect== null && datos.dtfechaOcurrencias==''|| datos.dtfechaOcurrencias== null ){
										Ext.Msg.show({
											title: 'Aviso',
											msg: 'Complete la informaci&oacute;n requerida',
											buttons: Ext.Msg.OK,
											icon: Ext.Msg.WARNING
										});
									}else{
										Ext.Ajax.request( {
											url	 : _URL_GUARDA_ASEGURADO
											,params:{
												'params.nmtramite'  : panelInicialPral.down('[name=params.ntramite]').getValue(),
												'params.nfactura'   : panelInicialPral.down('[name=params.nfactura]').getValue(),
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
											}
											,success : function (response){
												//alert("Guardado");
												banderaAsegurado = 0;
												cargarPaginacion(panelInicialPral.down('[name=params.ntramite]').getValue(),panelInicialPral.down('[name=params.nfactura]').getValue());
											},
											failure : function () {
												//me.up().up().setLoading(false);
												Ext.Msg.show({
													title:'Error',
													msg: 'Error de comunicaci&oacute;n',
													buttons: Ext.Msg.OK,
													icon: Ext.Msg.ERROR
												});
											}
										});
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
				/************    FINALIZA PROCESO PARA LA SOLICITUD DEL ALTA DE ASEGURADOS  ************/
				
				Ext.define('_11_WindowPedirAut', {
					extend		 : 'Ext.window.Window'
					,initComponent : function() {
						debug('_11_windowPedirAut initComponent');
						Ext.apply(this, {
							title		: 'Autorizaci&oacute;n de servicios'
							,icon		: '${ctx}/resources/fam3icons/icons/tick.png'
							//,width	   : 350
							//,height	  : 200
							,closeAction : 'hide'
							,modal	   : true
							,defaults	: { style : 'margin : 5px; ' }
							,items	   : _11_formPedirAuto
							,buttonAlign : 'center'
							,buttons	 :
							[
								{
									text	 : 'Asociar autorizaci&oacute;n'
									,icon	: '${ctx}/resources/fam3icons/icons/disk.png'
									,handler : _11_asociarAutorizacion
								}
							]
						});
						this.callParent();
					}
				});
				Ext.define('_11_FormPedirAuto', {
					extend		 : 'Ext.form.Panel'
					,initComponent : function() {
						debug('_11_FormPedirAuto initComponent');
						Ext.apply(this, {
							border : 0
							,items :
							[
								{
									xtype : 'label'
									,text : 'Se requiere el número de autorización para continuar ::::'
								}
								,_11_textfieldAsegurado
								,_11_textfieldNmautserv
								,gridAutorizacion
							]
						});
						this.callParent();
					}
				});
				
				Ext.define('_11_WindowModificarAut', {
					extend		 : 'Ext.window.Window'
					,initComponent : function() {
						debug('_11_windowModificarAut initComponent');
						Ext.apply(this, {
							title		: 'Modificar autorizaci&oacute;n de servicios'
							,icon		: '${ctx}/resources/fam3icons/icons/tick.png'
							,closeAction : 'hide'
							,modal	   : true
							,defaults	: { style : 'margin : 5px; ' }
							,items	   : _11_formModificarAuto
							,buttonAlign : 'center'
							,buttons	 :
							[
								{
									text	 : 'Modificar autorizaci&oacute;n'
									,icon	: '${ctx}/resources/fam3icons/icons/disk.png'
									,handler : _11_asociarAutorizacionNueva
								}
							],
                            listeners:{
                                 close:function(){
                                     if(true){
                                     	banderaAsegurado = 0;
                                        storeConceptos.removeAll();
                                        cargarPaginacion(panelInicialPral.down('[name=params.ntramite]').getValue(), panelInicialPral.down('[name=params.nfactura]').getValue());
                                     }
                                 }
                            }
						});
						this.callParent();
					}
				});
				Ext.define('_11_FormModificarAuto', {
					extend		 : 'Ext.form.Panel'
					,initComponent : function() {
						debug('_11_FormModificarAuto initComponent');
						Ext.apply(this, {
							border : 0
							,items :
							[
								{
									xtype : 'label'
									,text : 'Se requiere el número de autorización para continuar'
								}
								,_11_textfieldAseguradoMod
								,_11_textfieldNmautservMod
								,gridAutorizacionMod
							]
						});
						this.callParent();
					}
				});
				Ext.define('_11_WindowPedirMsiniest',{
					extend		 : 'Ext.window.Window'
					,initComponent : function() {
						debug('_11_WindowPedirMsiniest initComponent');
						Ext.apply(this, {
							title		: 'Asociar Reclamaci&oacute;n'
							,icon		: '${ctx}/resources/fam3icons/icons/tick.png'
							,closeAction : 'hide'
							,modal	   : true
							,defaults	: { style : 'margin : 5px; ' }
							,items	   : _11_formPedirMsiniest
							,buttonAlign : 'center'
							,buttons	 :
							[
								{
									text	 : 'Asociar Siniestro'
									,icon	: '${ctx}/resources/fam3icons/icons/disk.png'
									,handler : _11_asociarMsiniestMaestro
								}
							]
						});
						this.callParent();
					}
				});
				
				Ext.define('_11_formPedirMsiniest', {
					extend		 : 'Ext.form.Panel'
					,initComponent : function() {
						debug('_11_formPedirMsiniest initComponent');
						Ext.apply(this, {
							border : 0
							,items :
							[
								{
									xtype : 'label'
									,text : 'Se requiere el Siniestro para continuar'
								}
								,_11_textfieldAseguradoMsiniest
								,_11_textfieldNmSiniest
								,gridMsiniestMaestro
							]
						});
						this.callParent();
					}
				});
				
			/**FIN DE COMPONENTES***/
			var venDocuTramite=Ext.create('Ext.window.Window', {
						title		   : 'Documentos del tr&aacute;mite '
						,closable	   : false
						,width		  : 370
						,height		 : 300
						,autoScroll	 : true
						,collapsible	: true
						,titleCollapse  : true
						,startCollapsed : true
						,resizable	  : false
						,cls            : 'VENTANA_DOCUMENTOS_CLASS'
						,loader		 :
						{
							scripts   : true
							,autoLoad : true
							,url	  : _URL_DOCUMENTOSPOLIZA
							,params   : {
								'smap1.ntramite'   : _11_params.NTRAMITE
								,'smap1.cdtippag' : _11_params.OTVALOR02
								,'smap1.cdtipate' : ''
								,'smap1.cdtiptra' : _11_params.CDTIPTRA
								,'smap1.cdunieco' : ''
								,'smap1.cdramo'   : ''
								,'smap1.estado'   : ''
								,'smap1.nmpoliza' : ''
								,'smap1.nmsuplem' : '0'
								,'smap1.nmsolici' : ''
								,'smap1.tipomov'  :  _11_params.OTVALOR02
							}
						}
					}).showAt(600,0);
					venDocuTramite.collapse();
			/**INICIO DE CONTENIDO***/
				_11_textfieldAsegurado = Ext.create('Ext.form.TextField', {
					fieldLabel : 'Asegurado'
					,width	 : 500
					,readOnly  : true
				});
				_11_textfieldNmautserv = Ext.create('Ext.form.NumberField', {
					fieldLabel  : 'No. de autorizaci&oacute;n'
					,readOnly   : false
					,allowBlank : false
					,hidden : true
					,minLength  : 1
				});
				
				_11_textfieldNmautservMod = Ext.create('Ext.form.NumberField', {
					fieldLabel  : 'No. de autorizaci&oacute;n'
					,readOnly   : false
					,allowBlank : false
					,hidden : true
					,minLength  : 1
				});
				
				_11_textfieldAseguradoMod = Ext.create('Ext.form.TextField', {
					fieldLabel : 'Asegurado'
					,width	 : 500
					,readOnly  : true
				});
				
				_11_textfieldAseguradoMsiniest = Ext.create('Ext.form.TextField', {
					fieldLabel : 'Asegurado'
					,width	 : 500
					,readOnly  : true
				});
				_11_textfieldNmSiniest = Ext.create('Ext.form.NumberField', {
					fieldLabel  : 'No. de Siniestro'
					,name : 'nmsiniestroRef'
					,readOnly   : false
					,allowBlank : false
					,hidden : true
					,minLength  : 1
				});
						
				_11_formPedirAuto	= new _11_FormPedirAuto();
				_11_windowPedirAut	= new _11_WindowPedirAut();
				
				_11_formModificarAuto	= new _11_FormModificarAuto();
				_11_windowModificarAut	= new _11_WindowModificarAut();
				
				_11_formPedirMsiniest	= new _11_formPedirMsiniest();
				_11_WindowPedirMsiniest	= new _11_WindowPedirMsiniest();
				
			/**FIN DE CONTENIDO***/
			});
			
    ////////////////////////////////////  VALIDACIONES GENERALES DE LA APLICACION SINIESTROS  ////////////////////////////////////
    
    //10.- Generar contrarecibo
    function _11_GenerarContrarecibo(grid,rowIndex,colIndex){
        Ext.Ajax.request({
            url: _UrlGenerarContrarecibo,
            params: {
                'paramsO.pv_cdunieco_i' : null,
                'paramsO.pv_cdramo_i'   : _11_params.CDRAMO,
                'paramsO.pv_estado_i'   : null,
                'paramsO.pv_nmpoliza_i' : null,
                'paramsO.pv_nmsuplem_i' : null,
                'paramsO.pv_ntramite_i' : _11_params.NTRAMITE,
                'paramsO.pv_nmsolici_i' : null,
                'paramsO.pv_cdtippag_i' : _11_params.OTVALOR02,
                'paramsO.pv_cdtipate_i' : _11_params.OTVALOR07,
                'paramsO.pv_tipmov_i'   : _11_params.OTVALOR02,
                'paramsO.pv_pagoAut_i'  : "1"//pago Automatico
            },
            success: function(response, opt) {
                var jsonRes=Ext.decode(response.responseText);
                if(jsonRes.success == true){
                    var numRand=Math.floor((Math.random()*100000)+1);
                    debug('numRand a: ',numRand);
                    var windowVerDocu=Ext.create('Ext.window.Window', {
                        title          : 'Contrarecibo de Documentos del Siniestro'
                        ,width         : 700
                        ,height        : 500
                        ,collapsible   : true
                        ,titleCollapse : true
                        ,html          : '<iframe innerframe="'+numRand+'" frameborder="0" width="100" height="100"'
                        +'src="'+panDocUrlViewDoc+'?subfolder=' + _11_params.NTRAMITE + '&filename=' + '<s:text name="siniestro.contrarecibo.nombre"/>' +'">'
                        +'</iframe>'
                        ,listeners     : {
                            resize : function(win,width,height,opt){
                                debug(width,height);
                                $('[innerframe="'+numRand+'"]').attr({'width':width-20,'height':height-60});
                            }
                        }
                    }).show();
                    windowVerDocu.center();
                }else {
                    mensajeError(jsonRes.msgResult);
                }
            },
            failure: function(){
                mensajeError('No se pudo generar contrarecibo.');
            }
        });
    }
    
	//12.- Eliminar Facturas
	function _11_eliminarFactura(grid,rowindex){
		_11_recordActivo = grid.getStore().getAt(rowindex);
 		centrarVentanaInterna(Ext.Msg.show({
	        title: 'Aviso',
	        msg: '&iquest;Esta seguro que desea eliminar esta factura?',
	        buttons: Ext.Msg.YESNO,
	        icon: Ext.Msg.QUESTION,
	        fn: function(buttonId, text, opt){
	        	if(buttonId == 'yes'){
	        		Ext.Ajax.request({
						url     : _URL_ELIMINAR_FACT_ASEG
						,params:{
							'params.ntramite'		: _11_recordActivo.get('ntramite'),
		                    'params.nfactura'		: _11_recordActivo.get('factura'),
		                    'params.tipoPago'		: _tipoPago,
		                    'params.procedencia' 	: 'SINIESTROS',
		                    'params.cdramo'			: _11_params.CDRAMO,
		                    'params.valorAccion' 	: 1
						}
						,success : function (response){
							Ext.create('Ext.form.Panel').submit({
								standardSubmit :true
								,params		: {
									'params.ntramite' : _11_params.NTRAMITE
								}
							});
						},
						failure : function (){
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
	    }));
	}
    
    //25.- Editar Factura
    function _11_editar(grid,rowindex){
        _11_recordActivo = grid.getStore().getAt(rowindex);
        debug("Valor de respuesta _11_recordActivo :) ====>>>> ",_11_recordActivo);
        
        _11_llenaFormulario();
        modPolizasAltaTramite.show();
        centrarVentanaInterna(modPolizasAltaTramite);
    }
    
    //.- 
	function _11_asociarMsiniestMaestro(){		
		debug("##### Record activo  #####",_11_recordActivo.data);
		var valido = _11_formPedirMsiniest.isValid();
		if(!valido) {
			datosIncompletos();
		}else{
			
			var formulario=_11_formPedirMsiniest.form.getValues();
			Ext.Ajax.request( {
				url	 : _URL_ASOCIA_MSINEST_REFERENCIADO
				,params:{
					'params.nmsiniesRef' : formulario.nmsiniestroRef
					,'params.cdunieco'   : _11_recordActivo.data.CDUNIECO
					,'params.cdramo'     : _11_recordActivo.data.CDRAMO
					,'params.estado'     : _11_recordActivo.data.ESTADO
					,'params.nmpoliza'   : _11_recordActivo.data.NMPOLIZA
					,'params.nmsuplem'   : _11_recordActivo.data.NMSUPLEM
					,'params.nmsituac'   : _11_recordActivo.data.NMSITUAC
					,'params.aaapertu'   : _11_recordActivo.data.AAAPERTU
					,'params.status'     : _11_recordActivo.data.STATUS
					,'params.nmsinies'   : _11_recordActivo.data.NMSINIES
				}
				,success : function (response) {
					mensajeCorrecto('Asociar',"Se ha asociado el siniestro a uno existente",function(){
						debug("VALOR DEL SINIESTRO SELECCIONADO -->",formulario.nmsiniestroRef);
						_11_WindowPedirMsiniest.close();
						storeAseguradoFactura.removeAll();
						cargarPaginacion(panelInicialPral.down('[name=params.ntramite]').getValue(), panelInicialPral.down('[name=params.nfactura]').getValue());
					});
				},
				failure : function () {
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
	}
	
	//.- 
	function _11_pedirMsiniestMaestro(grid,rowIndex)
	{
		//1.- Ver si esta ya tiene generado un siniestro
		var record = grid.getStore().getAt(rowIndex);
		_11_recordActivo = record;
		debug('_11_pedirMsiniestMaestro: ###########',record.data);
		
		var idReclamacion = record.data.NMSINIES;
		valido = idReclamacion && idReclamacion>0;
		
		//if ( _CDROL == _ROL_MEDICO){
			if(!valido){
				_11_pedirAutorizacion(record);
			}
			else{
				msgWindow = Ext.Msg.show({
					title: 'Aviso',
					msg: '&iquest;Desea asociar la reclamaci&oacute;n a un siniestro existente?',
					buttons: Ext.Msg.YESNO,
					icon: Ext.Msg.QUESTION,
					fn: function(buttonId, text, opt){
						if(buttonId == 'no'){
							//Solo actualizare los registros de los asegurados
							cargarPaginacion(panelInicialPral.down('[name=params.ntramite]').getValue(), panelInicialPral.down('[name=params.nfactura]').getValue());
						}else{
							var params = {
								'params.cdunieco'	:	record.data.CDUNIECO
								,'params.cdramo'	:	record.data.CDRAMO
								,'params.estado'	:	record.data.ESTADO
								,'params.nmpoliza'	:	record.data.NMPOLIZA
								,'params.nmsuplem'	:	record.data.NMSUPLEM
								,'params.nmsituac'	:	record.data.NMSITUAC
								,'params.status'	:	record.data.STATUS
							};
							debug("params-->",params);
							_11_textfieldAseguradoMsiniest.setValue(record.data.NOMBRE);
							
							cargaStorePaginadoLocal(storeListadoSiniestMaestro, _URL_LISTA_MSINIESTRO, 'datosInformacionAdicional', params, function(options, success, response){
								if(success){
									var jsonResponse = Ext.decode(response.responseText);
									if(jsonResponse.datosInformacionAdicional.length <= 0) {
										storeConceptos.removeAll();
										centrarVentanaInterna(Ext.Msg.show({ 
											title: 'Aviso',
											msg: 'No existen siniestro para el asegurado elegido.',
											buttons: Ext.Msg.OK,
											icon: Ext.Msg.WARNING
										}));
									}else{
										_11_WindowPedirMsiniest.show();
										_11_textfieldNmSiniest.setValue('');
										centrarVentanaInterna(_11_WindowPedirMsiniest);
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
					}
				});
				centrarVentanaInterna(msgWindow);
			}
		//}
	}
	
	//FIN DE FUNCIONES
		</script>
		<script type="text/javascript" src="${ctx}/js/proceso/siniestros/afiliadosAfectados.js?${now}"></script>
		<script>
			Ext.onReady(function(){
				//Ext.Ajax.timeout = 1000*60*1000;
				Ext.Ajax.timeout = 1000*60*120; // 3 minutos
			    Ext.override(Ext.form.Basic, { timeout: Ext.Ajax.timeout / 1000 });
			    Ext.override(Ext.data.proxy.Server, { timeout: Ext.Ajax.timeout });
			    Ext.override(Ext.data.Connection, { timeout: Ext.Ajax.timeout });
			});
		</script>
		<script src="${ctx}/js/proceso/siniestros/funcionesSiniestrosGral.js?now=${now}"></script>
	</head>
	<body>
		<div style="height:2000px;">
			<div id="div_clau"></div>
			<div id="divResultados" style="margin-top:10px;"></div>
		</div>
	</body>
</html>