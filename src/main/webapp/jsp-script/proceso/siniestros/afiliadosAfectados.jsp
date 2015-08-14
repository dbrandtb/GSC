<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Afiliados Afectados</title>
		<script type="text/javascript">
		/*LLAMADO Y ASIGNACION DE LAS VARIABLES*/
			var _CONTEXT 								= '${ctx}';
			var _CATALOGO_TIPOMONEDA					= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TIPO_MONEDA"/>';
			var _CATALOGO_COBERTURASTOTALES 			= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@COBERTURASTOTALES"/>';
			var _CATALOGO_SUBCOBERTURASTOTALES 			= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@SUBCOBERTURASTOTALES"/>';
			var _ROL_MEDICO								= '<s:property value="@mx.com.gseguros.portal.general.util.RolSistema@MEDICO_AJUSTADOR.cdsisrol" />';
			var _ROL_COORD_MEDICO						= '<s:property value="@mx.com.gseguros.portal.general.util.RolSistema@GERENTE_MEDICO_MULTIREGIONAL.cdsisrol" />';
			var _OPERADOR_REC							= '<s:property value="@mx.com.gseguros.portal.general.util.RolSistema@OPERADOR_SINIESTROS.cdsisrol" />';
			var _COORDINADOR_REC						= '<s:property value="@mx.com.gseguros.portal.general.util.RolSistema@COORDINADOR_SINIESTROS.cdsisrol" />';
			var _CATALOGO_COB_X_VALORES 				= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@COBERTURASXVALORES"/>';
			var _TIPO_PAGO_DIRECTO						= '<s:property value="@mx.com.gseguros.portal.general.util.TipoPago@DIRECTO.codigo"/>';
			var _TIPO_PAGO_REEMBOLSO					= '<s:property value="@mx.com.gseguros.portal.general.util.TipoPago@REEMBOLSO.codigo"/>';
			var _TIPO_PAGO_INDEMNIZACION				= '<s:property value="@mx.com.gseguros.portal.general.util.TipoPago@INDEMNIZACION.codigo"/>';
			var _CATALOGO_TIPOCONCEPTO					= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TIPO_CONCEPTO_SINIESTROS"/>';
			var _CATALOGO_CONCEPTOSMEDICOS				= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@CODIGOS_MEDICOS"/>';
			var _CATALOGO_CONCEPTOSMEDICOSTOTALES   	= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@CODIGOS_MEDICOS_TOTALES"/>';
			var _11_params								= <s:property value="%{convertToJSON('params')}" escapeHtml="false" />;
			var _CDROL									= '<s:property value="params.cdrol" />';
			var _CAT_CAUSASINIESTRO						= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@CAUSA_SINIESTRO"/>';
			var _STATUS_TRAMITE_EN_REVISION_MEDICA  	= '<s:property value="@mx.com.gseguros.portal.general.util.EstatusTramite@EN_REVISION_MEDICA.codigo" />';
            var _STATUS_DEVOLVER_TRAMITE				= '<s:property value="@mx.com.gseguros.portal.general.util.EstatusTramite@TRAMITE_EN_DEVOLUCION.codigo" />';
            var _CAT_DESTINOPAGO                        = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@DESTINOPAGO"/>';
            var _CAT_CONCEPTO                           = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@CATCONCEPTO"/>';
            var _STATUS_TRAMITE_CONFIRMADO              = '<s:property value="@mx.com.gseguros.portal.general.util.EstatusTramite@CONFIRMADO.codigo" />';
            var _STATUS_TRAMITE_EN_ESPERA_DE_ASIGNACION = '<s:property value="@mx.com.gseguros.portal.general.util.EstatusTramite@EN_ESPERA_DE_ASIGNACION.codigo" />';
            var _RECHAZADO								= '<s:property value="@mx.com.gseguros.portal.general.util.EstatusTramite@RECHAZADO.codigo" />';
            var _SALUD_VITAL							= '<s:property value="@mx.com.gseguros.portal.general.util.Ramo@SALUD_VITAL.cdramo" />';
            var _MULTISALUD								= '<s:property value="@mx.com.gseguros.portal.general.util.Ramo@MULTISALUD.cdramo" />';
            var _GMMI									= '<s:property value="@mx.com.gseguros.portal.general.util.Ramo@GASTOS_MEDICOS_MAYORES.cdramo" />';
            var _RECUPERA								= '<s:property value="@mx.com.gseguros.portal.general.util.Ramo@RECUPERA.cdramo" />';
            var _CATALOGO_PROVEEDORES  					= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@PROVEEDORES"/>';
            var _URL_CATALOGOS							= '<s:url namespace="/catalogos" 		action="obtieneCatalogo"/>';
			var _URL_DOCUMENTOSPOLIZA					= '<s:url namespace="/documentos" 		action="ventanaDocumentosPoliza" />';
			var _URL_FINDETALLEMC 						= '<s:url namespace="/mesacontrol" 		action="finalizarDetalleTramiteMC" />';
            var _URL_DETALLEMC        					= '<s:url namespace="/mesacontrol" 		action="obtenerDetallesTramite" />';
            var _URL_MESACONTROL						= '<s:url namespace="/mesacontrol" 		action="mcdinamica" />';
			var _URL_ACTSTATUS_TRAMITE 					= '<s:url namespace="/mesacontrol" 		action="actualizarStatusTramite" />';
			var _11_URL_REQUIEREAUTSERV					= '<s:url namespace="/siniestros" 		action="obtieneRequiereAutServ" />';
			var _11_URL_INICIARSINIESTROSINAUTSERV		= '<s:url namespace="/siniestros"  		action="generarSiniestrosinAutServ" />';
			var _11_URL_INICIARSINIESTROTWORKSIN		= '<s:url namespace="/siniestros"  		action="iniciarSiniestroTworksin" />';
			var _URL_LISTA_COBERTURA 					= '<s:url namespace="/siniestros"  		action="consultaListaCoberturaPoliza" />';
			var _URL_LISTA_SUBCOBERTURA					= '<s:url namespace="/siniestros"  		action="consultaListaSubcobertura" />';
			var _URL_CONCEPTODESTINO        			= '<s:url namespace="/siniestros"       action="guardarConceptoDestino" />';
            var _URL_SOLICITARPAGO           			= '<s:url namespace="/siniestros" 		action="solicitarPago" />';
            var _URL_TIPO_ATENCION						= '<s:url namespace="/siniestros"  		action="consultaListaTipoAtencion" />';
            var _URL_NOMBRE_TURNADO   					= '<s:url namespace="/siniestros" 		action="obtieneUsuarioTurnado" />';
            var _URL_CONSULTA_TRAMITE       			= '<s:url namespace="/siniestros"       action="consultaListadoMesaControl" />';
          	var _URL_VALIDADOCCARGADOS					= '<s:url namespace="/siniestros" 		action="validaDocumentosCargados"/>';
            var _URL_GENERARCARTARECHAZO				= '<s:url namespace="/siniestros"		action="generaCartaRechazo" />';
            var _URL_LISTARECHAZOS						= '<s:url namespace="/siniestros"		action="loadListaRechazos" />';
            var _URL_LISTAINCISOSRECHAZOS				= '<s:url namespace="/siniestros"		action="loadListaIncisosRechazos" />';            
			var _URL_LISTA_CPTICD						= '<s:url namespace="/siniestros"  		action="consultaListaCPTICD" />';
			var _URL_GENERAR_CALCULO					= '<s:url namespace="/siniestros" 		action="generarCalculoSiniestros" />';
			var _URL_CONCEPTOSASEG						= '<s:url namespace="/siniestros" 		action="obtenerMsinival" />';
			var _URL_LISTADO_ASEGURADO          		= '<s:url namespace="/siniestros"       action="consultaListaAsegurado" />';
			var _URL_CONSULTA_LISTADO_POLIZA			= '<s:url namespace="/siniestros" 		action="consultaListaPoliza" />';
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
			var _URL_REVISIONDOCSINIESTRO   			= '<s:url namespace="/siniestros" 		action="includes/revisionDocumentos" />';
            var _URL_AJUSTESMEDICOS						= '<s:url namespace="/siniestros" 		action="includes/ajustesMedicos" />';
            var _URL_ELIMINAR_ASEGURADO					= '<s:url namespace="/siniestros"		action="eliminarAsegurado" />';
			var _VER_ALTA_FACTURAS 						= '<s:url namespace="/siniestros"  		action="includes/altaFacturas" />';
			var _URL_GUARDA_FACTURAS_TRAMITE  			= '<s:url namespace="/siniestros"       action="guardaFacturasTramite" />';
			var _URL_ELIMINAR_FACT_ASEG					= '<s:url namespace="/siniestros" 		action="eliminarFactAsegurado" />';
			var _URL_MONTO_PAGO_SINIESTRO				= '<s:url namespace="/siniestros"		action="obtieneMontoPagoSiniestro"/>';
			var _URL_P_MOV_MAUTSINI						= '<s:url namespace="/siniestros"		action="obtieneMensajeMautSini"/>';
			var _URL_TABBEDPANEL						= '<s:url namespace="/siniestros"  		action="includes/detalleSiniestro" />';
			var _URL_CONSULTA_AUTORIZACION_ESP			= '<s:url namespace="/siniestros"		action="consultaAutorizacionServicio" />';
			var _CATALOGO_CONCEPTOPAGO					= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@CONCEPTOPAGO"/>';
			var _URL_LISTADO_ASEGURADO_POLIZA			= '<s:url namespace="/siniestros"       action="consultaListaAseguradoPoliza" />';
			var _URL_CONSULTA_BENEFICIARIO				= '<s:url namespace="/siniestros"		action="consultaDatosBeneficiario" />';
			var _SINO									= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@SINO" />';
			var _URL_APLICA_IVA_CONCEPTO				= '<s:url namespace="/siniestros"		action="obtieneAplicacionIVA"/>';
			debug("VALOR DE _11_params --->",_11_params);
			debug("VALOR DEL ROL ACTIVO --->",_CDROL);
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
			var _11_textfieldAseguradoMod;
			
			var _11_textfieldNmautserv;
			var _11_textfieldNmautservMod;
			
			var _11_textfieldAseguradoMsiniest;
			var _11_textfieldNmSiniest;
			var panelInicialPral;
			var panelComplementos;
			var storeAseguradoFactura;
			var storeProveedor;
			var storeTipoAtencion;
			var modPolizasAltaTramite;
			var gridAutorizacion;
			var gridAutorizacionMod;
			var gridMsiniestMaestro;
			var storeListadoAutorizacion;
			var storeListadoSiniestMaestro;
			var storeConceptosCatalogoRender;
			var cmbCveTipoConcepto;
			var cmbAplicaIVA;
			var cmbCveConcepto;
			var cmbCveConceptoRender;
			var _11_windowRechazoSiniestro;
			var _11_formRechazo;
			var _11_conceptoSeleccionado=null;
			var _11_aseguradoSeleccionado = null;
			var _tipoPago = _11_params.OTVALOR02;
			var _tipoProducto = _11_params.CDRAMO;
			var banderaConcepto = "0";
			var banderaAsegurado = "0";
			var storeCobertura;
			var storeSubcobertura;
			var storeCoberturaxAsegurado;
			var storeCoberturaxAseguradoRender;
			var storeSubcoberturaAsegurado;
			var storeSubcoberturaAseguradoRender;
			var storeRechazos;
			var storeIncisosRechazos;
			var storeDestinoPago;
			var storeConceptoPago;
			var storeAsegurados2;
			var ventanaAgregarAsegurado;
			
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
				}
			];

			Ext.onReady(function()
			{
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
						{type:'string',	name:'COMPLEMENTO'}
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
						{type:'string',	name:'APLICIVA'}
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
								{type:'string',    name:'fcancelacionAfiliado'},{type:'string',    name:'desEstatusCliente'},		{type:'string',    name:'numPoliza'}]
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
			            "NTRAMITE"
			            ,"NMORDINA"
			            ,"CDTIPTRA"
			            ,"CDCLAUSU"
			            ,{name:"FECHAINI",type:'date',dateFormat:'d/m/Y'}
			            ,{name:"FECHAFIN",type:'date',dateFormat:'d/m/Y'}
			            ,"COMMENTS"
			            ,"CDUSUARI_INI"
			            ,"CDUSUARI_FIN"
			            ,"usuario_ini"
			            ,"usuario_fin"
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
// STORE PARA EL ICD PRINCIPAL
				var storeTiposICDPrimario = Ext.create('Ext.data.JsonStore', {
					model:'Generic',
					proxy: {
						type: 'ajax',
						url: _URL_LISTA_CPTICD,
						extraParams : {'params.cdtabla' : '2TABLICD'},
						reader: {
							type: 'json',
							root: 'listaCPTICD'
						}
					}
				});
				storeTiposICDPrimario.load();
				var storeTiposICDPrimarioRender = Ext.create('Ext.data.JsonStore', {
					model:'Generic',
					autoLoad:true,
					cargado:false,
					proxy: {
						type: 'ajax',
						url: _URL_LISTA_CPTICD,
						extraParams : {'params.cdtabla' : '2TABLICD'},
						reader: {
							type: 'json',
							root: 'listaCPTICD'
						}
					}
				,listeners: {
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
						url: _URL_LISTA_CPTICD,
						extraParams : {'params.cdtabla' : '2TABLICD'},
						reader: {
							type: 'json',
							root: 'listaCPTICD'
						}
					}
				});
				storeTiposICDSecundario.load();
				var storeTiposICDSecundarioRender = Ext.create('Ext.data.JsonStore', {
					model:'Generic',
					autoLoad:true,
					cargado:false,
					proxy: {
						type: 'ajax',
						url: _URL_LISTA_CPTICD,
						extraParams : {'params.cdtabla' : '2TABLICD'},
						reader: {
							type: 'json',
							root: 'listaCPTICD'
						}
					}
					,listeners: {
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
				storeAseguradoFactura = Ext.create('Ext.data.Store', {
					autoLoad : false
					,model   : 'modelAseguradosFactura'
					,proxy   : {
						reader : {
							type  : 'json'
							,root : 'slist1'
						}
						,type  : 'ajax'
						,url   : _URL_OBTENERSINIESTROSTRAMITE
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
				/*############################		DECLARACION DE COMBOX Y LABEL		########################################*/
				var comboTipoAte= Ext.create('Ext.form.ComboBox',
				{
					name:'params.cdtipser',			fieldLabel: 'Tipo atenci&oacute;n',		allowBlank : false,		editable:true,
					displayField: 'value',			emptyText:'Seleccione...',				valueField: 'key',		forceSelection : true,
					queryMode:'local',				store: storeTipoAtencion
				});
				
				var cmbProveedor = Ext.create('Ext.form.field.ComboBox',
				{
					fieldLabel : 'Proveedor',			displayField : 'nombre',			name:'params.cdpresta',
					valueField   : 'cdpresta',			forceSelection : true,
					matchFieldWidth: false,				queryMode :'remote',				queryParam: 'params.cdpresta',
					minChars  : 2,						store : storeProveedor,				triggerAction: 'all',
					hideTrigger:true,					allowBlank:false
				});
				var comboICDPrimario = Ext.create('Ext.form.field.ComboBox',
				{
					allowBlank: false,				displayField : 'value',		forceSelection : true,
					name:'idComboICDPrimario',		valueField   : 'key',		store : storeTiposICDPrimario,
					matchFieldWidth: false,			queryMode :'remote',		queryParam: 'params.otclave',
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
					matchFieldWidth: false,			queryMode :'remote',		queryParam: 'params.otclave',
					minChars  : 2,					editable:true,				triggerAction: 'all',		hideTrigger:true,
					listeners : {
						'select' : function(combo, record) {
							banderaAsegurado = 1;
							debug("VALOR DE LA BANDERA ASEURADO -->",banderaAsegurado);
							_11_aseguradoSeleccionado.set('CDICD2',this.getValue());
						}
					}
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
					name:'params.tipoMoneda',		fieldLabel	: 'Moneda',		store: storeTipoMoneda,			queryMode:'local',  
					displayField: 'value',		valueField: 'key',			editable:false,					allowBlank:false,
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
				
				var subCoberturaAsegurado = Ext.create('Ext.form.field.ComboBox', {
			    	allowBlank: false,				displayField : 'value',			id:'idSubcobertura1',		name:'cdconval',
			    	labelWidth: 170,				valueField   : 'key',			forceSelection : true,			matchFieldWidth: false,
			    	queryMode :'local',			store : storeSubcoberturaAsegurado,		triggerAction: 'all',			editable:true,
			    	listeners : {
						'select' : function(combo, record) {
							_11_aseguradoSeleccionado.set('CDCONVAL',this.getValue());
							//banderaAsegurado = 1;
							debug("VALOR DE LA BANDERA ASEURADO -->",banderaAsegurado);
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
					displayField: 'value',		valueField: 'key',				editable:false,				allowBlank:false/*,
					listeners:{
						select: function (combo, records, opts){
							banderaConcepto = 1;
							var cdTipo = records[0].get('key');
							storeConceptosCatalogo.proxy.extraParams= {
								'params.idPadre' : cdTipo
								,catalogo		: _CATALOGO_CONCEPTOSMEDICOS
							};
						}
					}*/
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
											debug("Guardamos los conceptos ");
											//Mandamos a guardar los conceptos
											_guardarConceptosxFactura();
										}else if(banderaAsegurado == "1"){
											debug("VALOR SELECCIONADO :)-->",_11_aseguradoSeleccionado);
											guardaDatosComplementariosAsegurado(_11_aseguradoSeleccionado, banderaAsegurado);
											storeConceptos.removeAll();
										}else{
											_11_aseguradoSeleccionado = gridFacturaDirecto.getView().getSelectionModel().getSelection()[0];
											storeCoberturaxAsegurado.proxy.extraParams= {
												'params.cdunieco':_11_aseguradoSeleccionado.get('CDUNIECO'),
								            	'params.estado':_11_aseguradoSeleccionado.get('ESTADO'),
								            	'params.cdramo':_11_aseguradoSeleccionado.get('CDRAMO'),
								            	'params.nmpoliza':_11_aseguradoSeleccionado.get('NMPOLIZA'),
								            	'params.nmsituac':_11_aseguradoSeleccionado.get('NMSITUAC')
											};
											
											storeSubcoberturaAsegurado.load({
												params:{
													'params.cdunieco':_11_aseguradoSeleccionado.get('CDUNIECO'),
									            	'params.cdramo':_11_aseguradoSeleccionado.get('CDRAMO'),
									            	'params.estado':_11_aseguradoSeleccionado.get('ESTADO'),
									            	'params.nmpoliza':_11_aseguradoSeleccionado.get('NMPOLIZA'),
									            	'params.nmsituac':_11_aseguradoSeleccionado.get('NMSITUAC'),
									            	'params.cdtipsit':_11_aseguradoSeleccionado.get('CDTIPSIT'),
									            	'params.cdgarant' :_11_aseguradoSeleccionado.get('CDGARANT'),
									            	'params.cdsubcob' :null//_11_aseguradoSeleccionado.get('CDCONVAL')
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
										,handler : guardarDatosComplementarios
									},
									{
										icon	 : '${ctx}/resources/fam3icons/icons/user_delete.png'
										,tooltip : 'Eliminar Asegurado'
										,handler : eliminarAsegurado
										
									}
								]
							},
							{
								header: 'Id<br/>Sini.',				dataIndex: 'NMSINIES',		width: 50 
							},
							{
								header: '# Auto.',					dataIndex: 'NMAUTSER',		width: 70
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
								header: 'Id<br/>Sini. Existente',	dataIndex: 'NMSINREF',		width: 90, hidden : _tipoProducto != '7'
							},
							{
								header: 'Complemento',	dataIndex: 'COMPLEMENTO',		width: 90, hidden : _tipoProducto != '7'
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
								header: 'Causa <br/> Siniestro', 				dataIndex: 'CDCAUSA'
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
								header: 'Cobertura',			dataIndex: 'CDGARANT',		allowBlank: false
								,editor: coberturaxAsegurado
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
								header: 'Subcobertura',			dataIndex: 'CDCONVAL',	allowBlank: false
								,editor: subCoberturaAsegurado
								,renderer : function(v) {
									var leyenda = '';
									if (typeof v == 'string') {
										debug("Valor de V : "+v);
										debug("Valor de storeSubcoberturaAseguradoRender.cargado : "+storeSubcoberturaAseguradoRender.cargado);
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
								header: 'ICD<br/>Principal', 				dataIndex: 'CDICD'
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
								header: 'ICD<br/>Secundario', 				dataIndex: 'CDICD2'
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
								header: 'Subtotal',	dataIndex: 'IMPORTEASEG',renderer  : Ext.util.Format.usMoney
							},
							{
								header: 'IVA',	dataIndex: 'PTIVAASEG',renderer  : Ext.util.Format.usMoney
							},
							{
								header: 'IVA Retenido',	dataIndex: 'PTIVARETASEG',renderer  : Ext.util.Format.usMoney
							},
							{
								header: 'ISR',	dataIndex: 'PTISRASEG',renderer  : Ext.util.Format.usMoney
							},
							{
								header: 'Imp. Cedular',	dataIndex: 'PTIMPCEDASEG',renderer  : Ext.util.Format.usMoney
							},
							{
								header: 'Importe a Pagar',	dataIndex: 'IMPORTETOTALPAGO',renderer  : Ext.util.Format.usMoney
							}
						],
						tbar:[
								{
									text	 : 'Agregar Asegurado'
									,icon	 : '${ctx}/resources/fam3icons/icons/user_add.png'
									,handler : _p21_agregarAsegurado
									,hidden  : _tipoPago != _TIPO_PAGO_DIRECTO
								},
								{
									text	: 'Generar Calculo'
									,icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/book.png'
									,handler : _p21_generarCalculo
								}
							],
							
						listeners: {
							select: function (grid, record, index, opts){
								debug("VALOR DEL RECORD SELECCIONADO", record);
								debug("<--VALOR DE LA BANDERA DEL CONCEPTO-->",banderaConcepto,"<--VALOR DE LA BANDERA DEL ASEGURADO-->",banderaAsegurado);
								if (banderaConcepto == "1"){
									debug("Guardamos los conceptos ");
									//Mandamos a guardar los conceptos
									_guardarConceptosxFactura();
									storeConceptos.removeAll();
								}else if(banderaAsegurado == "1"){
									debug("VALOR SELECCIONADO :)-->",_11_aseguradoSeleccionado);
									guardaDatosComplementariosAsegurado(_11_aseguradoSeleccionado, banderaAsegurado);
									storeConceptos.removeAll();
								}else{
									var numSiniestro = record.get('NMSINIES');
									if(numSiniestro.length == "0"){
										revisarDocumento(grid,index);
									}else{
										storeConceptos.removeAll();
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
												if(record.get('CDRAMO') == _GMMI){
													obtenerSumaAsegurada (record.get('CDUNIECO'), record.get('CDRAMO'), record.get('ESTADO'), 
																		  record.get('NMPOLIZA'), record.get('CDPERSON'), record.get('NMSINREF'),
																		  totalConsumido);
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
										icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/delete.png',
										tooltip: 'Eliminar',
										scope: this,
										handler: this.onRemoveClick
									}
									,{
										icon	 : '${ctx}/resources/fam3icons/icons/page_edit.png'
										,tooltip : 'Ajuste'
										,handler : _mostrarVentanaAjustes
									}
								]//,flex:1
							},
							
							{
								dataIndex : 'NMORDINA',
								width : 20,
								hidden: true
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
						banderaConcepto = "1";
						//_guardarConceptosxFactura();
					}
				});
				gridEditorConceptos = new EditorConceptos();

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
							xtype		: 'displayfield',			fieldLabel	: 'Suma Asegurada',			name	: 'params.sumaAsegurada', value : '0.00', 	hidden : _tipoProducto != '7',
    	    	    		valueToRaw : function(value){
	    	                    return Ext.util.Format.usMoney(value);
	    	                }
						},
						{
							xtype		: 'displayfield',			fieldLabel	: 'Suma Disponible',			name	: 'params.sumaGastada', value : '0.00',		hidden : _tipoProducto != '7',
    	    	    		valueToRaw : function(value){
	    	                    return Ext.util.Format.usMoney(value);
	    	                }
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
						style : 'margin:5px;'
					}
					,items	:
					[
						{
							xtype		: 'textfield',			fieldLabel	: 'No. Tr&aacute;mite',		name	: 'params.ntramite', readOnly   : true, hidden: true
						},
						{
							xtype		: 'textfield',			fieldLabel	: 'ContraRecibo',		name	: 'params.contrarecibo', readOnly   : true
						},
						{
							xtype		: 'textfield',			fieldLabel	: 'No. Factura',			name	: 'params.nfactura', readOnly   : true
						},
						{
							xtype		: 'datefield',			fieldLabel	: 'Fecha Factura',			name	: 'params.fefactura',	format	: 'd/m/Y'
						},
						{
							xtype		: 'datefield',			fieldLabel	: 'Fecha Egreso',			name	: 'params.feegreso',	format	: 'd/m/Y',		allowBlank : false
						},
						{
							xtype		: 'numberfield',		fieldLabel 	: 'Deducible (D&iacute;as)',		name	: 'params.diasdedu',		allowBlank : false
						},
						cmbProveedor,
						comboTipoAte,
						cobertura,
						subCobertura,
						cmbTipoMoneda,
						{
							xtype		: 'numberfield',		fieldLabel 	: 'Tasa Cambio',			name	: 'params.tasacamb',
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
							xtype		: 'numberfield',		fieldLabel 	: 'Importe Factura',		name	: 'params.ptimporta',
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
							xtype		: 'numberfield',		fieldLabel 	: 'Importe Mxn',			name	: 'params.ptimport',
							allowBlank	: false,				allowDecimals :true	,					decimalSeparator :'.'
						},
						{
							xtype		: 'numberfield',		fieldLabel 	: 'Descuento %',			name	: 'params.descporc',
							allowBlank	: false,				allowDecimals :true	,					decimalSeparator :'.'
						},
						{
							xtype		: 'numberfield',		fieldLabel 	: 'Descuento $',			name	: 'params.descnume',
							allowBlank	: false,				allowDecimals :true	,					decimalSeparator :'.'
						},
						<s:property value='%{"," + imap.tatrisinItems}' />
						<s:property value='%{"," + imap.itemsEdicion}' />
					]
					,buttonAlign:'center'
					,buttons: [ {
							text:'Aplicar Cambios Factura',
							icon:_CONTEXT+'/resources/fam3icons/icons/disk.png',
							handler:function() {
								
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
												'El tr&aacute;mite de pago directo ser&aacute; cancelado debido a que no ha sido autorizado alguno de los siniestros'
												,function(){_11_windowRechazoSiniestro.show();centrarVentanaInterna(_11_windowRechazoSiniestro);}
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
												debug("exito y no realizar nada");
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
								//1.- Validamos que el asegurado este vigente
								if(record.get('desEstatusCliente')=="Vigente") {
									var valorFechaOcurrencia;
									var valorFechaOcu = panelListadoAsegurado.query('datefield[name=dtfechaOcurrencias]')[0].rawValue;
									valorFechaOcurrencia = new Date(valorFechaOcu.substring(6,10)+"/"+valorFechaOcu.substring(3,5)+"/"+valorFechaOcu.substring(0,2));
									
									var valorFechaInicial = new Date(record.get('feinicio').substring(6,10)+"/"+record.get('feinicio').substring(3,5)+"/"+record.get('feinicio').substring(0,2));
									var valorFechaFinal =   new Date(record.get('fefinal').substring(6,10)+"/"+record.get('fefinal').substring(3,5)+"/"+record.get('fefinal').substring(0,2));
									var valorFechaAltaAsegurado = new Date(record.get('faltaAsegurado').substring(6,10)+"/"+record.get('faltaAsegurado').substring(3,5)+"/"+record.get('faltaAsegurado').substring(0,2));
									
									if( (valorFechaOcurrencia <= valorFechaFinal) && (valorFechaOcurrencia >= valorFechaInicial)){
										if( valorFechaOcurrencia >= valorFechaAltaAsegurado ) {
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
										//limpiarRegistros();
									}
								}else{
									// El asegurado no se encuentra vigente
									centrarVentanaInterna(Ext.Msg.show({
										title:'Error',
										msg: 'El asegurado de la p&oacute;liza seleccionado no se encuentra vigente',
										buttons: Ext.Msg.OK,
										icon: Ext.Msg.ERROR
									}));
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
												storeAseguradoFactura.load({
													params: {
														'smap.ntramite'   : panelInicialPral.down('[name=params.ntramite]').getValue(),
														'smap.nfactura'   : panelInicialPral.down('[name=params.nfactura]').getValue()
													}
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
				
				Ext.define('_11_WindowRechazoSiniestro', {
					extend		 : 'Ext.window.Window'
					,initComponent : function() {
						debug('_11_WindowRechazoSiniestro initComponent');
						Ext.apply(this, {
							title		: 'Rechazo de tr&aacute;mite'
							,width	   : 600
							,height	  : 350
							,autoScroll  : true
							,closeAction : 'hide'
							,modal	   : true
							,defaults	: { style : 'margin : 5px; ' }
							,items	   : _11_formRechazo
							,buttonAlign : 'center'
							,buttons	 :
							[
								{
									text	 : 'Rechazar'
									,icon	: '${ctx}/resources/fam3icons/icons/delete.png'
									,handler : _11_rechazoSiniestro
								}
							]
						});
						this.callParent();
					}
				});

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
							]
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
				
				Ext.define('_11_FormRechazo', {
					extend		 : 'Ext.form.Panel'
					,initComponent : function() {
						debug('_11_FormRechazo initComponent');
						Ext.apply(this, {
							border  : 0
							,items  : _11_itemsRechazo
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
								,'smap1.nmsuplem' : ''
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
				
				_11_formRechazo		= new _11_FormRechazo();
				_11_windowRechazoSiniestro = new _11_WindowRechazoSiniestro();
				
			/**FIN DE CONTENIDO***/
			});

	/*############################		INICIO DE FUNCIONES		########################################*/
	function _11_rechazarTramiteSiniestro(){
		
		var motivoRechazo= Ext.create('Ext.form.ComboBox',{
			id:'motivoRechazo',			name:'smap1.cdmotivo',		fieldLabel: 'Motivo',		store: storeRechazos,
			queryMode:'local',			displayField: 'value',		valueField: 'key',			allowBlank:false,
			blankText:'El motivo es un dato requerido',				editable:false,				labelWidth : 150,
			width: 600,					emptyText:'Seleccione ...',
			listeners: {
				select: function(combo, records, eOpts){
					panelRechazarReclamaciones.down('[name=smap1.incisosRechazo]').setValue('');
					panelRechazarReclamaciones.down('[name=smap1.comments]').setValue('');
					storeIncisosRechazos.removeAll();
					storeIncisosRechazos.load({
						params: {
							'params.pv_cdmotivo_i' : records[0].get('key')
						}
					});
				}
			}
		});
		
		var textoRechazo = Ext.create('Ext.form.field.TextArea', {
			fieldLabel: 'Descripci&oacute;n modificado',			labelWidth: 150,			width: 600
			,name:'smap1.comments',									height: 250,				allowBlank: false
			,blankText:'La descripci&oacute;n es un dato requerido'
		});

		var incisosRechazo= Ext.create('Ext.form.ComboBox',{
			id:'incisosRechazo',							name:'smap1.incisosRechazo',						fieldLabel: 'Incisos Rechazo',
			store: storeIncisosRechazos,					queryMode:'local',									displayField: 'value',
			valueField: 'key',								blankText:'El motivo es un dato requerido',			editable:false,
			labelWidth : 150,								width: 600,											emptyText:'Seleccione ...',
			listeners: {
				select: function(combo, records, eOpts){
					textoRechazo.setValue(records[0].get('value'));
				}
			}
		});
					
		var panelRechazarReclamaciones= Ext.create('Ext.form.Panel', {
			id: 'panelRechazarReclamaciones',
			width: 650,
			url: _URL_ACTSTATUS_TRAMITE,
			bodyPadding: 5,
			items: [
				motivoRechazo,incisosRechazo,textoRechazo
			],
			buttonAlign:'center',
			buttons: [{
				text: 'Rechazar'
				,icon:_CONTEXT+'/resources/fam3icons/icons/accept.png'
				,buttonAlign : 'center',
				handler: function() {
					if (panelRechazarReclamaciones.form.isValid()) {
						panelRechazarReclamaciones.form.submit({
							waitMsg:'Procesando...',
							params: {
								'smap1.ntramite' : _11_params.NTRAMITE,
								'smap1.status'   : _RECHAZADO
							},
							failure: function(form, action) {
								Ext.Msg.show({
									title: 'ERROR',
									msg: 'Error al Rechazar.',
									buttons: Ext.Msg.OK,
									icon: Ext.Msg.ERROR
								});
							},
							success: function(form, action) {
								var respuesta = Ext.decode(action.response.responseText);
								if(respuesta.success==true){
									windowLoader.close();
									/*Se cierra y se tiene que mandar a la  MC*/
									Ext.Ajax.request({
										url: _URL_GENERARCARTARECHAZO,
										params: {
											'paramsO.pv_cdunieco_i' : null,
											'paramsO.pv_cdramo_i'   : _11_params.CDRAMO,
											'paramsO.pv_estado_i'   : null,
											'paramsO.pv_nmpoliza_i' : null,
											'paramsO.pv_nmsuplem_i' : null,
											'paramsO.pv_nmsolici_i' : null,
											'paramsO.pv_tipmov_i'   : _11_params.OTVALOR02,
											'paramsO.pv_ntramite_i' : _11_params.NTRAMITE,
											'paramsO.tipopago' : _11_params.OTVALOR02
										},
										success: function(response, opt) {
											var jsonRes=Ext.decode(response.responseText);
											if(jsonRes.success == true){	
												var numRand=Math.floor((Math.random()*100000)+1);
												var windowVerDocu=Ext.create('Ext.window.Window',
												{
													title          : 'Carta de Rechazo del Siniestro'
													,width         : 700
													,height        : 500
													,collapsible   : true
													,titleCollapse : true
													,html          : '<iframe innerframe="'+numRand+'" frameborder="0" width="100" height="100"'
																		+'src="'+panDocUrlViewDoc+'?subfolder=' + panelInicialPral.down('[name=idNumTramite]').getValue() + '&filename=' + nombreReporteRechazo +'">'
																		+'</iframe>'
													,listeners     :
													{
														resize : function(win,width,height,opt){
															$('[innerframe="'+numRand+'"]').attr({'width':width-20,'height':height-60});
														}
													}
												});
											}else {
												mensajeError('Error al generar la carta de rechazo.');
											}
										},
										failure: function(){
											mensajeError('Error al generar la carta de rechazo.');
										}
									});
									mensajeCorrecto('&Eacute;XITO','Se ha rechazado correctamente.',function(){
										windowLoader.close();
										Ext.create('Ext.form.Panel').submit(
										{
											url		: _URL_MESACONTROL
											,standardSubmit : true
											,params         :
											{
												'smap1.gridTitle'      : 'Siniestros en espera'
												,'smap2.pv_cdtiptra_i' : _TIPO_TRAMITE_SINIESTRO
											}
										});
									});
									
								}else {
									Ext.Msg.show({
										title: 'ERROR',
										msg: 'Error al Rechazar.',
										buttons: Ext.Msg.OK,
										icon: Ext.Msg.ERROR
									});
								}
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
			},{
				text: 'Cancelar',
				icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
				buttonAlign : 'center',
				handler: function() {
					windowLoader.close();
				}
			}
			]
		});
		windowLoader = Ext.create('Ext.window.Window',
		{
			title			: 'Rechazar Tr&aacute;mite'
			,modal			: true
			,buttonAlign	: 'center'
			,items			:
			[
				panelRechazarReclamaciones
			]
		}).show();
		centrarVentana(windowLoader);
	}
	
	
	
	function _11_regresarMC()
	{
		debug('_11_regresarMC');
		Ext.create('Ext.form.Panel').submit({
			url				: _URL_MESACONTROL
			,standardSubmit	:true
			,params			:
			{
				'smap1.gridTitle'		: 'Siniestros'
				,'smap2.pv_cdtiptra_i'	: _TIPO_TRAMITE_SINIESTRO
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
		
		if(valido){
		}
	}

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
							'params.ntramite': _11_recordActivo.get('ntramite'),
		                    'params.nfactura': _11_recordActivo.get('factura'),
		                    'params.tipoPago': _tipoPago,
		                    'params.procedencia' : 'SINIESTROS',
		                    'params.valorAccion' : 1
						}
						,success : function (response){
							Ext.create('Ext.form.Panel').submit(
							{
								standardSubmit :true
								,params		:
								{
									'params.ntramite' : _11_params.NTRAMITE
								}
							});
						},
						failure : function (){
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
	    }));
	}
	
	function _11_editar(grid,rowindex)
	{
		_11_recordActivo = grid.getStore().getAt(rowindex);
		debug("VALORES AL EDITAR",_11_recordActivo);
		_11_llenaFormulario();
		modPolizasAltaTramite.show();
		centrarVentanaInterna(modPolizasAltaTramite);
	}

	function _11_llenaFormulario()
	{
		Ext.Ajax.request({
			url	 : _URL_DATOS_VALIDACION
			,params:{
				'params.ntramite': _11_recordActivo.get('ntramite')
				,'params.nfactura': _11_recordActivo.get('factura')
				,'params.tipoPago': _tipoPago
			}
			,success : function (response)
			{
				panelInicialPral.down('[name=params.fefactura]').setValue(_11_recordActivo.get('fechaFactura'));
				panelInicialPral.down('[name=params.feegreso]').setValue(_11_recordActivo.get('feegreso')); 
				panelInicialPral.down('[name=params.diasdedu]').setValue(_11_recordActivo.get('diasdedu'));
				var valorRequerido = true;
				if(_tipoPago ==_TIPO_PAGO_INDEMNIZACION){
					gridEditorConceptos.hide();
					panelInicialPral.down('[name="parametros.pv_otvalor01"]').hide();
					panelInicialPral.down('[name="parametros.pv_otvalor02"]').hide();
					panelInicialPral.down('[name="parametros.pv_otvalor03"]').hide();
					if(_11_params.CDRAMO == _RECUPERA){
						//panelInicialPral.down('[name=params.feegreso]').hide(); 
						panelInicialPral.down('[name=params.diasdedu]').hide();
						valorRequerido = true;
					}else{
						//panelInicialPral.down('[name=params.feegreso]').show(); 
						panelInicialPral.down('[name=params.diasdedu]').show();
						valorRequerido = false;
					}
					
				}else if(_tipoPago ==_TIPO_PAGO_REEMBOLSO){
					gridEditorConceptos.show();
					panelInicialPral.down('[name="parametros.pv_otvalor01"]').hide();
					panelInicialPral.down('[name="parametros.pv_otvalor02"]').hide();
					panelInicialPral.down('[name="parametros.pv_otvalor03"]').hide();
					//panelInicialPral.down('[name=params.feegreso]').hide(); 
					panelInicialPral.down('[name=params.diasdedu]').hide();
					valorRequerido = true;
				}else{
					gridEditorConceptos.show();
					panelInicialPral.down('[name="parametros.pv_otvalor01"]').show();
					panelInicialPral.down('[name="parametros.pv_otvalor02"]').show();
					panelInicialPral.down('[name="parametros.pv_otvalor03"]').show();
					//panelInicialPral.down('[name=params.feegreso]').hide(); 
					panelInicialPral.down('[name=params.diasdedu]').hide();
					valorRequerido = true;
				}
				//panelInicialPral.down('[name=params.feegreso]').allowBlank = valorRequerido;
				panelInicialPral.down('[name=params.diasdedu]').allowBlank = valorRequerido;
				storeProveedor.load();
				
				panelInicialPral.down('combo[name=params.cdpresta]').setValue(_11_recordActivo.get('cdpresta'));
				
				storeTipoAtencion.load({
					params:{
						'params.cdramo':_11_params.CDRAMO,
						'params.tipoPago':_tipoPago
					}
				});
				panelInicialPral.down('combo[name=params.cdtipser]').setValue(_11_recordActivo.get('cdtipser'));
				
				panelInicialPral.down('[name=params.contrarecibo]').setValue(_11_recordActivo.get('contraRecibo'));
				
				panelInicialPral.down('[name=params.ntramite]').setValue(_11_recordActivo.get('ntramite'));
				
				//params.cdtipser
				panelInicialPral.down('[name=params.nfactura]').setValue(_11_recordActivo.get('factura'));
				
				if(_11_recordActivo.get('desctoNum').length == 0){
					panelInicialPral.down('[name=params.descnume]').setValue("0.00");
				}else{
					panelInicialPral.down('[name=params.descnume]').setValue(_11_recordActivo.get('desctoNum'));
				}
				if(_11_recordActivo.get('desctoPorc').length == 0){
					panelInicialPral.down('[name=params.descporc]').setValue("0.00");
				}else{
					panelInicialPral.down('[name=params.descporc]').setValue(_11_recordActivo.get('desctoPorc'));
				}
				
				storeCobertura.proxy.extraParams=
				{
					'params.ntramite':_11_recordActivo.get('ntramite')
					,'params.tipopago':_tipoPago
					,catalogo		: _CATALOGO_COB_X_VALORES
				};
				
				storeCobertura.load({
					params:{
						'params.ntramite':_11_recordActivo.get('ntramite'),
						'params.tipopago':_tipoPago
					}
				});
				
				panelInicialPral.down('[name=params.cdgarant]').setValue(_11_recordActivo.get('cdgarant'));
				
				debug("VALOR JOSE ;( -->",_11_recordActivo);
				storeSubcobertura.load({
					params:{
						'params.cdgarant' :_11_recordActivo.get('params.cdgarant')
					}
				});
				panelInicialPral.down('combo[name=params.cdconval]').setValue(_11_recordActivo.get('cdconval'));
				
				panelInicialPral.down('combo[name=params.tipoMoneda]').setValue(_11_recordActivo.get('cdmoneda'));
				
				if(_11_recordActivo.get('cdmoneda') =="001"){
					panelInicialPral.down('[name=params.ptimport]').setValue(_11_recordActivo.get('ptimport'));
					panelInicialPral.down('[name=params.tasacamb]').setValue("0.00");
					panelInicialPral.down('[name=params.ptimporta]').setValue("0.00");
					panelInicialPral.down('[name=params.tasacamb]').hide();
					panelInicialPral.down('[name=params.ptimporta]').hide();
					
				}else{
					panelInicialPral.down('[name=params.ptimport]').setValue(_11_recordActivo.get('ptimport'));
					panelInicialPral.down('[name=params.tasacamb]').setValue(_11_recordActivo.get('tasaCambio'));
					panelInicialPral.down('[name=params.ptimporta]').setValue(_11_recordActivo.get('ptimporta'));
					panelInicialPral.down('[name=params.tasacamb]').show();
					panelInicialPral.down('[name=params.ptimporta]').show();
				}
				
				storeAseguradoFactura.removeAll();
				storeAseguradoFactura.load({
					params: {
						'smap.ntramite'   : _11_recordActivo.get('ntramite'),
						'smap.nfactura'   : _11_recordActivo.get('factura')
					}
				});
				//Llenamos los campos que requeriremos
				debug("VALORES A---> ",storeAseguradoFactura);//,storeAseguradoFactura);
				obtenerTotalPagos(_11_recordActivo.get('ntramite'), _11_recordActivo.get('factura'));
				if(Ext.decode(response.responseText).datosValidacion != null){
					var aplicaIVA = null;
					var ivaRetenido = null;
					var ivaAntesDespues = null;
					var autAR = null;
					var autAM = null;
					var commAR = null;
					var commAM = null;
					var json=Ext.decode(response.responseText).datosValidacion;
					debug("VALOR DEL JSON DE LA RESPUESTA -->",json);
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
					panelInicialPral.down('[name="params.autrecla"]').setValue("S");
					panelInicialPral.down('[name="params.autrecla"]').hide();
					if(aplicaIVA == null){
						if(_tipoPago ==_TIPO_PAGO_INDEMNIZACION || _tipoPago == _TIPO_PAGO_REEMBOLSO){
							panelInicialPral.down('[name="parametros.pv_otvalor01"]').setValue("N");
						}else{
							panelInicialPral.down('[name="parametros.pv_otvalor01"]').setValue("S");
						}
					}else{
						panelInicialPral.down('[name="parametros.pv_otvalor01"]').setValue(aplicaIVA);
					}
					if(ivaAntesDespues == null){
						panelInicialPral.down('[name="parametros.pv_otvalor02"]').setValue("D");
					}else{
						panelInicialPral.down('[name="parametros.pv_otvalor02"]').setValue(ivaAntesDespues);
					}
					if(ivaRetenido == null){
						panelInicialPral.down('[name="parametros.pv_otvalor03"]').setValue("N");
					}else{
						panelInicialPral.down('[name="parametros.pv_otvalor03"]').setValue(ivaRetenido);
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
	
	function _11_asociarMsiniestMaestro(){
		
		debug("Record activo#####",_11_recordActivo.data);
		
		
		var valido = _11_formPedirMsiniest.isValid();
		if(!valido)
		{
			datosIncompletos();
		}else{
			
			var formulario=_11_formPedirMsiniest.form.getValues();
			Ext.Ajax.request(
			{
				url	 : _URL_ASOCIA_MSINEST_REFERENCIADO
				,params:{
					'params.nmsiniesRef' : formulario.nmsiniestroRef
					,'params.cdunieco' : _11_recordActivo.data.CDUNIECO
					,'params.cdramo'     : _11_recordActivo.data.CDRAMO
					,'params.estado'     : _11_recordActivo.data.ESTADO
					,'params.nmpoliza'   : _11_recordActivo.data.NMPOLIZA
					,'params.nmsuplem'   : _11_recordActivo.data.NMSUPLEM
					,'params.nmsituac'   : _11_recordActivo.data.NMSITUAC
					,'params.aaapertu'   : _11_recordActivo.data.AAAPERTU
					,'params.status'     : _11_recordActivo.data.STATUS
					,'params.nmsinies'   : _11_recordActivo.data.NMSINIES
				}
				,success : function (response)
				{
					mensajeCorrecto('Asociar',"Se ha asociado el siniestro a uno existente",function(){
						debug("VALOR DEL SINIESTRO SELECCIONADO -->",formulario.nmsiniestroRef);
						_11_WindowPedirMsiniest.close();
						storeAseguradoFactura.removeAll();
						storeAseguradoFactura.load({
							params: {
								'smap.ntramite'   : panelInicialPral.down('[name=params.ntramite]').getValue(),
								'smap.nfactura'   : panelInicialPral.down('[name=params.nfactura]').getValue()
							}
						});
					});
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
	}
	
	function obtenerSumaAsegurada (cdunieco, cdramo, estado, nmpoliza, cdperson, nmsinref, totalConsumido){
		Ext.Ajax.request(
		{
			url	 : _URL_OBTENER_SUMAASEGURADA
			,params:{
				'params.cdunieco' 	: cdunieco
				,'params.cdramo'  	: cdramo
				,'params.estado'  	: estado
				,'params.nmpoliza' 	: nmpoliza
				,'params.cdperson' 	: cdperson
				,'params.nmsinref' 	: nmsinref
			}
			,success : function (response)
			{
				var jsonResponse  = Ext.decode(response.responseText).datosValidacion[0];
				var sumAsegurada  = jsonResponse.SUMA_ASEGURADA;
				var sumDisponible = jsonResponse.RESERVA_DISPONIBLE;
				
				var sumaConceptos = (+sumDisponible) - (+ totalConsumido);
				
				panelComplementos.down('[name=params.sumaAsegurada]').setValue(sumAsegurada);
				panelComplementos.down('[name=params.sumaGastada]').setValue(sumaConceptos);
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

	function guardaCambiosAutorizacionServ(record, numeroAutorizacion, tipoProceso){
		debug("Valores de entrada para el guardado ",record);
		debug("Numero de Autorizacion : ",numeroAutorizacion);
		
		Ext.Ajax.request({
			url     : _URL_CONSULTA_AUTORIZACION_ESP
			,params : {
				'params.nmautser'  : numeroAutorizacion
			}
			,success : function (response) {
				var jsonAutServ = Ext.decode(response.responseText).datosAutorizacionEsp;
				debug("VALOR DE RESPUESTA :: ",jsonAutServ);
				
				_11_guardarDatosComplementario(record.data.CDUNIECO, 
											record.data.CDRAMO,
											record.data.ESTADO,
											record.data.NMPOLIZA,
											record.data.NMSUPLEM,
											record.data.AAAPERTU,
											record.data.NMSINIES,
											record.data.FEOCURRE,
											record.data.NMSINREF,
											jsonAutServ.cdicd,
											record.data.CDICD2,
											jsonAutServ.cdcausa,
											jsonAutServ.cdgarant,
											jsonAutServ.cdconval,
											jsonAutServ.nmautser,
											record.data.CDPERSON,
											tipoProceso,
											record.data.COMPLEMENTO
											);
				
			},
			failure : function (){
				//me.up().up().setLoading(false);
				centrarVentanaInterna(Ext.Msg.show({
					title:'Error',
					msg: 'Error de comunicaci&oacute;n',
					buttons: Ext.Msg.OK,
					icon: Ext.Msg.ERROR
				}));
			}
		});
	}
	
	function guardarDatosComplementarios(grid,rowIndex){
		var record = grid.getStore().getAt(rowIndex);
		banderaAsegurado = 0;
		guardaDatosComplementariosAsegurado(record, banderaAsegurado);
	}
	
	function guardaDatosComplementariosAsegurado(record, banderaAsegurado){
		debug("guardaDatosComplementariosAsegurado ====> : ",record);
		var idICD = record.data.CDICD;
		var idCdgarant = record.data.CDGARANT;
		var idConval = record.data.CDCONVAL;
		var idcausa = record.data.CDCAUSA;
		if(idICD.length > 0 && idCdgarant.length > 0 && idConval.length > 0 && idcausa.length > 0){
		//if(idCdgarant.length > 0 && idConval.length > 0 && idcausa.length > 0){
			var valorRegistro = "1";
			if(banderaAsegurado == "1"){
				valorRegistro = "0";
			}
			_11_guardarDatosComplementario(record.data.CDUNIECO, 
											record.data.CDRAMO,
											record.data.ESTADO,
											record.data.NMPOLIZA,
											record.data.NMSUPLEM,
											record.data.AAAPERTU,
											record.data.NMSINIES,
											record.data.FEOCURRE,
											record.data.NMSINREF,
											record.data.CDICD,
											record.data.CDICD2,
											record.data.CDCAUSA,
											record.data.CDGARANT,
											record.data.CDCONVAL,
											record.data.NMAUTSER,
											record.data.CDPERSON,
											valorRegistro,
											record.data.COMPLEMENTO);
		}else{
			mensajeWarning(
				'Complemente la informaci&oacute;n del Asegurado');
		}
	}
	
	function _11_guardarDatosComplementario(cdunieco,cdramo, estado, nmpoliza, nmsuplem,
										aaapertu, nmsinies,feocurre, nmreclamo, cdicd,
										cdicd2,cdcausa, cdgarant,cdconval, nmautser,
										cdperson, tipoProceso, complemento){
		Ext.Ajax.request(
		{
			url	 : _URL_ACTUALIZA_INFO_GRAL_SIN
			,params:{
				'params.cdunieco' : cdunieco,
				'params.cdramo'   : cdramo,
				'params.estado'   : estado,
				'params.nmpoliza' : nmpoliza,
				'params.nmsuplem' : nmsuplem,
				'params.aaapertu' : aaapertu,
				'params.nmsinies' : nmsinies,
				'params.feocurre' : feocurre,
				'params.nmreclamo': nmreclamo,
				'params.cdicd'    : cdicd,
				'params.cdicd2'   : cdicd2,
				'params.cdcausa'  : cdcausa,
				'params.ntramite' : panelInicialPral.down('[name=params.ntramite]').getValue(),
				'params.cdgarant' : cdgarant,
				'params.cdconval' : cdconval,
				'params.nmautser' : nmautser,
				'params.tipoPago' : _tipoPago,
				'params.nfactura' : panelInicialPral.down('[name=params.nfactura]').getValue(),
				'params.fefactura': panelInicialPral.down('[name=params.fefactura]').getValue(),
				'params.cdtipser' : panelInicialPral.down('combo[name=params.cdtipser]').getValue(),
				'params.cdpresta' : panelInicialPral.down('combo[name=params.cdpresta]').getValue(),
				'params.ptimport' : panelInicialPral.down('[name=params.ptimport]').getValue(),
				'params.descporc' : panelInicialPral.down('[name=params.descporc]').getValue(),
				'params.descnume' : panelInicialPral.down('[name=params.descnume]').getValue(),
				'params.tipoMoneda' : panelInicialPral.down('combo[name=params.tipoMoneda]').getValue(),
				'params.tasacamb' : panelInicialPral.down('[name=params.tasacamb]').getValue(),
				'params.ptimporta' : panelInicialPral.down('[name=params.ptimporta]').getValue(),
				'params.feegreso' : panelInicialPral.down('[name=params.feegreso]').getValue(),
				'params.diasdedu' : panelInicialPral.down('[name=params.diasdedu]').getValue(),
				'params.dctonuex' : null,
				'params.cdperson' : cdperson,
				'params.tipoProceso' : tipoProceso,
				'params.complemento' : complemento
			}
			,success : function (response)
			{
				banderaAsegurado = 0;
				storeConceptos.removeAll();
				storeAseguradoFactura.load({
					params: {
						'smap.ntramite'   : panelInicialPral.down('[name=params.ntramite]').getValue(),
						'smap.nfactura'   : panelInicialPral.down('[name=params.nfactura]').getValue()
					}
				});
			},
			failure : function ()
			{
				//me.up().up().setLoading(false);
				Ext.Msg.show({
					title:'Error',
					msg: 'Error de comunicaci&oacute;n --> ',
					buttons: Ext.Msg.OK,
					icon: Ext.Msg.ERROR
				});
			}
		});
	}
	
	function _11_pedirMsiniestMaestro(grid,rowIndex)
	{
		//1.- Ver si esta ya tiene generado un siniestro
		var record = grid.getStore().getAt(rowIndex);
		_11_recordActivo = record;
		debug('_11_recordAsegurado: ###########',record.data);
		
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
							storeAseguradoFactura.load({
								params: {
									'smap.ntramite'   : panelInicialPral.down('[name=params.ntramite]').getValue(),
									'smap.nfactura'   : panelInicialPral.down('[name=params.nfactura]').getValue()
								}
							});
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
				centrarVentana(msgWindow);
			}
		//}
	}
	function revisarDocumento(grid,rowIndex)
	{
		var record = grid.getStore().getAt(rowIndex);
		debug('record.raw:',record.raw);
		var valido = true;
		Ext.Ajax.request(
		{
			url	: _11_URL_REQUIEREAUTSERV
			,params:{
				'params.cobertura': null,
				'params.subcobertura': null,
				'params.cdramo': record.raw.CDRAMO,
				'params.cdtipsit': record.raw.CDTIPSIT
			}
			,success : function (response)
			{
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
									url	  : _11_URL_INICIARSINIESTROSINAUTSERV
									,params  : json
									,success : function(response)
									{
										json = Ext.decode(response.responseText);
										if(json.success==true){
											_11_guardarInformacionAdicional();
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
							}
							if(buttonId == 'yes'){
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
						modal	   : true,
						buttonAlign : 'center',
						title: 'Informaci&oacute;n general',
						width	   : 800,
						height	  : 450,
						autoScroll  : true,
						loader	  : {
							url	 : _URL_TABBEDPANEL
							,params		 :
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
								,'params.cdrol'	: _CDROL
								,'params.tipopago' : _tipoPago
							},
							scripts  : true,
							loadMask : true,
							autoLoad : true,
							ajaxOptions: {
								method: 'POST'
							}
						}
					}).show();
					centrarVentanaInterna(windowLoader);
				}
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
	
	function _11_guardarInformacionAdicional(){
		panelInicialPral.form.submit({
			waitMsg:'Procesando...',	
			url: _URL_GUARDA_CAMBIOS_FACTURA,
			failure: function(form, action) {
				centrarVentanaInterna(mensajeError("Verifica los datos requeridos"));
			},
			success: function(form, action) {
				storeAseguradoFactura.removeAll();
				storeAseguradoFactura.load({
					params: {
						'smap.ntramite'   : panelInicialPral.down('[name=params.ntramite]').getValue(),
						'smap.nfactura'   : panelInicialPral.down('[name=params.nfactura]').getValue()
					}
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
					storeConceptos.removeAll();
					banderaConcepto = 0;
					banderaAsegurado = 0;
					storeAseguradoFactura.removeAll();
					storeAseguradoFactura.load({
						params: {
							'smap.ntramite'   : panelInicialPral.down('[name=params.ntramite]').getValue(),
							'smap.nfactura'   : panelInicialPral.down('[name=params.nfactura]').getValue()
						}
					});
					centrarVentanaInterna(Ext.Msg.show({ 
						title: 'Aviso',
						msg: 'No existen autorizaci&oacute;n para el asegurado elegido.',
						buttons: Ext.Msg.OK,
						icon: Ext.Msg.WARNING
					}));
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
	
	function _11_modificarAutorizacion(record)
	{
		_11_recordActivo = record;
		debug('_11_recordActivo:',_11_recordActivo.data);
		
		_11_textfieldAseguradoMod.setValue(_11_recordActivo.get('NOMBRE'));
		var params = {
				'params.cdperson'	:	_11_recordActivo.get('CDPERSON')
		};
		cargaStorePaginadoLocal(storeListadoAutorizacion, _URL_LISTA_AUTSERVICIO, 'datosInformacionAdicional', params, function(options, success, response){
			if(success){
				var jsonResponse = Ext.decode(response.responseText);
				if(jsonResponse.datosInformacionAdicional.length <= 0) {
					storeConceptos.removeAll();
					storeAseguradoFactura.removeAll();
					banderaConcepto = 0;
					banderaAsegurado = 0;
					storeAseguradoFactura.load({
						params: {
							'smap.ntramite'   : panelInicialPral.down('[name=params.ntramite]').getValue(),
							'smap.nfactura'   : panelInicialPral.down('[name=params.nfactura]').getValue()
						}
					});
					centrarVentanaInterna(Ext.Msg.show({ 
						title: 'Aviso',
						msg: 'No existen autorizaci&oacute;n para el asegurado elegido.',
						buttons: Ext.Msg.OK,
						icon: Ext.Msg.WARNING
					}));
				}else{
					_11_windowModificarAut.show();
					_11_textfieldNmautservMod.setValue('');
					centrarVentanaInterna(_11_windowModificarAut);
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
	
	function _mostrarVentanaAjustes(grid,rowIndex,colIndex){
		var record = grid.getStore().getAt(rowIndex);
		var recordFactura = gridFacturaDirecto.getSelectionModel().getSelection()[0];
		//if ( _CDROL == _ROL_MEDICO){
			windowLoader = Ext.create('Ext.window.Window',{
				modal	   : true,
				buttonAlign : 'center',
				title: 'Ajustes M&eacute;dico',
				width	   : 800,
				height	  : 450,
				//autoScroll  : true,
				loader	  : {
					url	 : _URL_AJUSTESMEDICOS,
					params		 :
					{
						'params.ntramite'		: panelInicialPral.down('[name=params.ntramite]').getValue()
						,'params.cdunieco'		: recordFactura.get('CDUNIECO')
						,'params.cdramo'		: recordFactura.get('CDRAMO')
						,'params.estado'		: recordFactura.get('ESTADO')
						,'params.nmpoliza'		: recordFactura.get('NMPOLIZA')
						,'params.nmsuplem'		: recordFactura.get('NMSUPLEM')
						,'params.nmsituac'		: recordFactura.get('NMSITUAC')
						,'params.aaapertu'		: recordFactura.get('AAAPERTU')
						,'params.status'		: recordFactura.get('STATUS')
						,'params.nmsinies'		: recordFactura.get('NMSINIES')
						,'params.nfactura'		: panelInicialPral.down('[name=params.nfactura]').getValue()
						,'params.cdgarant'		: record.get('CDGARANT')
						,'params.cdconval'		: record.get('CDCONVAL')
						,'params.cdconcep'		: record.get('CDCONCEP')
						,'params.idconcep'		: record.get('IDCONCEP')
						,'params.nmordina'		: record.get('NMORDINA')
						,'params.precio'		: record.get('PTPRECIO')
						,'params.cantidad'		: record.get('CANTIDAD')
						,'params.descuentoporc'	: record.get('DESTOPOR')
						,'params.descuentonum'	: record.get('DESTOIMP')
						,'params.importe'		: record.get('PTIMPORT')
						,'params.ajusteMedi'	: record.get('TOTAJUSMED')
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
		//}
	}
	
	function _guardarConceptosxFactura(){
		var obtener = [];
		storeConceptos.each(function(record) {
			obtener.push(record.data);
		});
		if(obtener.length <= 0){
			centrarVentanaInterna(Ext.Msg.show({
				title:'Error',
				msg: 'Se requiere al menos un concepto',
				buttons: Ext.Msg.OK,
				icon: Ext.Msg.ERROR
			}).defer(100));
			storeConceptos.reload();
			return false;
		}else{
			
			for(i=0;i < obtener.length;i++){
				if(obtener[i].IDCONCEP == null ||obtener[i].CDCONCEP == null ||obtener[i].PTMTOARA == null ||obtener[i].PTPRECIO == null ||obtener[i].CANTIDAD == null ||
					obtener[i].IDCONCEP == "" ||obtener[i].CDCONCEP == "" ||obtener[i].PTMTOARA == ""||obtener[i].PTPRECIO == "" || obtener[i].CANTIDAD ==""){
					centrarVentanaInterna(Ext.Msg.show({
						title:'Concepto',
						msg: 'Favor de introducir los campos requeridos en el concepto.',
						buttons: Ext.Msg.OK,
						icon: Ext.Msg.WARNING
					}).defer(100));
					return false;
				}
			}
			var submitValues={};
			var formulario=panelInicialPral.form.getValues();
			submitValues['params']=formulario;
			var datosTablas = [];
			var _11_aseguradoSeleccionado = gridFacturaDirecto.getView().getSelectionModel().getSelection()[0];
			debug("VALORES -->",_11_aseguradoSeleccionado)
			storeConceptos.each(function(record,index){
				datosTablas.push({
					cdunieco  : record.get('CDUNIECO')
					,cdramo   : record.get('CDRAMO')
					,estado   : record.get('ESTADO')
					,nmpoliza : record.get('NMPOLIZA')
					,nmsuplem : record.get('NMSUPLEM')
					,nmsituac : record.get('NMSITUAC')
					,aaapertu : record.get('AAAPERTU')
					,status   : record.get('STATUS')
					,nmsinies : record.get('NMSINIES')
					,nfactura : panelInicialPral.down('[name=params.nfactura]').getValue()
					,cdgarant : record.get('CDGARANT')
					,cdconval : record.get('CDCONVAL')
					,cdconcep : record.get('CDCONCEP')
					,idconcep : record.get('IDCONCEP')
					,cdcapita : record.get('CDCAPITA')
					,nmordina : record.get('NMORDINA')
					,cdmoneda : "001"
					,ptprecio : record.get('PTPRECIO')
					,cantidad : record.get('CANTIDAD')
					,destopor : record.get('DESTOPOR')
					,destoimp : record.get('DESTOIMP')
					,ptimport : record.get('PTIMPORT')
					,ptrecobr : record.get('PTRECOBR')
					,nmapunte : record.get('NMAPUNTE')
					,feregist : record.get('FEREGIST')
					,operacion: "I"
					,ptpcioex : record.get('PTPCIOEX')
					,dctoimex : record.get('DCTOIMEX')
					,ptimpoex : record.get('PTIMPOEX')
					,mtoArancel : record.get('PTMTOARA')
					,aplicaIVA : record.get('APLICIVA')
				});
			});
			submitValues['datosTablas']=datosTablas;
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
						banderaConcepto = "0";
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

	function _p21_agregarAsegurado(){
		centrarVentanaInterna(ventanaAgregarAsegurado.show());
	}
	
	function obtenerTotalPagos(ntramite, nfactura)
	{
		Ext.Ajax.request(
		{
			url	 : _URL_OBTENERSINIESTROSTRAMITE
			,params:{
				'smap.ntramite'   : ntramite ,
				'smap.nfactura'   : nfactura
			}
			,success : function (response)
			{
				var aseguradosTotales = Ext.decode(response.responseText).slist1;
				var totalPago = 0;
				var subtotalFactura=0;
		    	var ivaFactura=0;
		    	var ivaRetFactura=0;
		    	var isrFactura=0;
		    	var impCedFactura=0;
		    	var imporTotalFactura=0;
		    	 
		    	for(var i = 0; i < aseguradosTotales.length; i++)
		    	{
		    	    totalPago = 0;
		    	   	var importeAseg = aseguradosTotales[i].IMPORTEASEG;
		    	   	var ivaAseg     = aseguradosTotales[i].PTIVAASEG;
		    	   	var ivaRete     = aseguradosTotales[i].PTIVARETASEG;
			    	var isrAse      = aseguradosTotales[i].PTISRASEG;
			    	var impCedul    = aseguradosTotales[i].PTIMPCEDASEG;
			    	var totalPagoVa = aseguradosTotales[i].IMPORTETOTALPAGO;
			    	
			    	if(importeAseg.length > 0){
		    	   		subtotalFactura = parseFloat(subtotalFactura) + parseFloat(importeAseg);
		    	   	}else{
		    	   		subtotalFactura = parseFloat(subtotalFactura) + parseFloat(totalPago);
		    	   	}
		    	    
		    	   	if(ivaAseg.length > 0){
		    	   		ivaFactura = parseFloat(ivaFactura) + parseFloat(ivaAseg);
		    	   	}else{
		    	   		ivaFactura = parseFloat(ivaFactura) + parseFloat(totalPago);
		    	   	}
			    	
			    	if(ivaRete.length > 0){
			    		ivaRetFactura = parseFloat(ivaRetFactura) + parseFloat(ivaRete);
			    	}else{
			    		ivaRetFactura = parseFloat(ivaRetFactura) + parseFloat(totalPago);
			    	}
			    	
			    	if(isrAse.length > 0){
			    		isrFactura = parseFloat(isrFactura) + parseFloat(isrAse);
			    	}else{
			    		isrFactura = parseFloat(isrFactura) + parseFloat(totalPago);
			    	}
			    	
			    	if(impCedul.length > 0){
			    		impCedFactura = parseFloat(impCedFactura) + parseFloat(impCedul);
			    	}else{
			    		impCedFactura = parseFloat(impCedFactura) + parseFloat(totalPago);
			    	}
			    	
			    	if(totalPagoVa.length > 0){
			    		imporTotalFactura = parseFloat(imporTotalFactura) + parseFloat(totalPagoVa);
			    	}else{
			    		imporTotalFactura = parseFloat(imporTotalFactura) + parseFloat(totalPago);
			    	}
		    	}
		    	panelComplementos.down('[name=params.subtotalFac]').setValue(subtotalFactura);
		    	panelComplementos.down('[name=params.ivaFac]').setValue(ivaFactura);
		    	panelComplementos.down('[name=params.ivaRetFac]').setValue(ivaRetFactura);
		    	panelComplementos.down('[name=params.isrFac]').setValue(isrFactura);
		    	panelComplementos.down('[name=params.impCedularFac]').setValue(impCedFactura);
		    	panelComplementos.down('[name=params.impPagarFac]').setValue(imporTotalFactura);
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
    	return true;
	}
	
	function _p21_generarCalculo(){
		// Se manda a llamar al procedimiento y se guarda
		Ext.Ajax.request(
		{
			url	 : _URL_GENERAR_CALCULO
			,params:{
				'params.ntramite'  : panelInicialPral.down('[name=params.ntramite]').getValue()
			}
			,success : function (response)
			{
				
				storeAseguradoFactura.load({
					params: {
						'smap.ntramite'   : panelInicialPral.down('[name=params.ntramite]').getValue() ,
						'smap.nfactura'   : panelInicialPral.down('[name=params.nfactura]').getValue()
					}
				});
				
				panelComplementos.down('[name=params.sumaAsegurada]').setValue("0.00");
				panelComplementos.down('[name=params.sumaGastada]').setValue("0.00");
				obtenerTotalPagos(panelInicialPral.down('[name=params.ntramite]').getValue() , panelInicialPral.down('[name=params.nfactura]').getValue());
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
	
	
	function _p21_agregarConcepto()
	{
		if(gridFacturaDirecto.getSelectionModel().hasSelection()){
			var recordFactura = gridFacturaDirecto.getSelectionModel().getSelection()[0];
			debug("VALOR DEL RECORD PARA LOS CONCEPTOS -->",recordFactura);
			var idReclamacion = recordFactura.get('NMSINIES');
			valido = idReclamacion && idReclamacion>0;
			if(valido){
				var idCobertura = recordFactura.get('CDGARANT');
				var idSubcobertura = recordFactura.get('CDCONVAL');
				var idcausaSiniestro = recordFactura.get('CDCAUSA');
				var idICDP = recordFactura.get('CDICD');
				
				if(recordFactura.get('CDGARANT').length > 0 && recordFactura.get('CDCONVAL').length > 0 && 
				   recordFactura.get('CDCAUSA').length > 0 && recordFactura.get('CDICD').length > 0){
						banderaConcepto = "1";
						storeConceptos.add(new modelConceptos(
						{
							PTPRECIO : '0.00',
							DESTOPOR : '0.00',
							DESTOIMP : '0.00',
							CDUNIECO : recordFactura.get('CDUNIECO'),
							CDRAMO   : recordFactura.get('CDRAMO'),
							ESTADO   : recordFactura.get('ESTADO'),
							NMPOLIZA : recordFactura.get('NMPOLIZA'),
							NMSUPLEM : recordFactura.get('NMSUPLEM'),
							NMSITUAC : recordFactura.get('NMSITUAC'),
							AAAPERTU : recordFactura.get('AAAPERTU'),
							STATUS   : recordFactura.get('STATUS'),
							NMSINIES : recordFactura.get('NMSINIES'),
							PTPCIOEX : '0.00',
							DCTOIMEX : '0.00',
							PTIMPOEX : '0.00',
							CDGARANT : recordFactura.get('CDGARANT'),
							CDCONVAL : recordFactura.get('CDCONVAL')
						}));
						
				}else{
					mensajeWarning(
						'Complemente la informaci&oacute;n del Asegurado');
				}
			}else{
				mensajeWarning(
						'Debes generar una autorizaci&oacute;n para el asegurado'
				);
			}
		}else{
			centrarVentanaInterna(mensajeWarning("Debe seleccionar un asegurado para agregar sus conceptos"));
		}
	}
	
	function _11_asociarAutorizacionNueva(){
		debug("Entra a _11_asociarAutorizacionNueva --->");
		var valido = _11_formModificarAuto.isValid();
		if(!valido)
		{
			datosIncompletos();
		}else{
			_11_windowModificarAut.close();
			guardaCambiosAutorizacionServ(_11_aseguradoSeleccionado, _11_textfieldNmautservMod.getValue(),"0");
		}
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
			_11_formPedirAuto.setLoading(true);
			_11_windowPedirAut.close();
			Ext.Ajax.request(
			{
				url	  : _11_URL_INICIARSINIESTROTWORKSIN
				,params  : json
				,success : function(response)
				{
					_11_formPedirAuto.setLoading(false);
					
					json = Ext.decode(response.responseText);
					if(json.success==true)
					{
						_11_guardarInformacionAdicional();
						
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
	
	function _11_turnarAreaMedica(){
		var comentariosText = Ext.create('Ext.form.field.TextArea', {
        	fieldLabel: 'Observaciones'
    		,labelWidth: 150
    		,width: 600
    		,name:'smap1.comments'
			,height: 250
			,allowBlank : false
        });
		
		windowLoader = Ext.create('Ext.window.Window',{
	        modal       : true,
	        buttonAlign : 'center',
	        width       : 663,
	        height      : 400,
	        autoScroll  : true,
	        items       : [
        	        		Ext.create('Ext.form.Panel', {
        	                title: 'Turnar al Area M&eacute;dica',
        	                width: 650,
        	                url: _URL_ACTSTATUS_TRAMITE,
        	                bodyPadding: 5,
        	                items: [comentariosText],
        	        	    buttonAlign:'center',
        	        	    buttons: [{
        	            		text: 'Turnar'
        	            		,icon:_CONTEXT+'/resources/fam3icons/icons/accept.png'
        	            		,buttonAlign : 'center',
        	            		handler: function() {
        	            	    	if (this.up().up().form.isValid()) {
        	            	    		this.up().up().form.submit({
        	            		        	waitMsg:'Procesando...',
        	            		        	params: {
        	            		        		'smap1.ntramite' :  _11_params.NTRAMITE,
        	            		        		'smap1.status'   : _STATUS_TRAMITE_EN_REVISION_MEDICA
        	            		        		,'smap1.rol_destino'     : 'medajustador'
        	            		        		,'smap1.usuario_destino' : ''
        	            		        	},
        	            		        	failure: function(form, action) {
        	            		        		Ext.Msg.show({
													title:'Error',
													msg: 'Error de comunicaci&oacute;n',
													buttons: Ext.Msg.OK,
													icon: Ext.Msg.ERROR
												});
        	            					},
        	            					success: function(form, action) {
        	            						//Se realiza el llamado por el numero de trámite
        	            						Ext.Ajax.request(
								    	        {
								    	            url     : _URL_NOMBRE_TURNADO
								    	            ,params : 
								    	            {           
								    	                'params.ntramite': _11_params.NTRAMITE,
								    	                'params.rolDestino': 'medajustador'
								    	            }
								    	            ,success : function (response)
								    	            {
								    	                var usuarioTurnadoSiniestro = Ext.decode(response.responseText).usuarioTurnadoSiniestro;
														debug("VALOR DE RESPUESTA -->",usuarioTurnadoSiniestro);
														mensajeCorrecto('&Eacute;XITO','Se ha turnado correctamente a: '+usuarioTurnadoSiniestro,function(){
															windowLoader.close();
															Ext.create('Ext.form.Panel').submit(
															{
																url		: _URL_MESACONTROL
																,standardSubmit : true
																,params         :
																{
																	'smap1.gridTitle'      : 'Siniestros en espera'
																	,'smap2.pv_cdtiptra_i' : 16
																}
															});
														});
														
														
														
								    	            },
								    	            failure : function ()
								    	            {
								    	                //me.up().up().setLoading(false);
								    	                centrarVentanaInterna(Ext.Msg.show({
								    	                    title:'Error',
								    	                    msg: 'Error de comunicaci&oacute;n',
								    	                    buttons: Ext.Msg.OK,
								    	                    icon: Ext.Msg.ERROR
								    	                }));
								    	            }
								    	        });
        	            					}
        	            				});
        	            			} else {
        	            				centrarVentanaInterna(Ext.Msg.show({
        	            	                   title: 'Aviso',
        	            	                   msg: 'Complete la informaci&oacute;n requerida',
        	            	                   buttons: Ext.Msg.OK,
        	            	                   icon: Ext.Msg.WARNING
        	            	               }));
        	            			}
        	            		}
        	            	},{
        	            	    text: 'Cancelar',
        	            	    icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
        	            	    buttonAlign : 'center',
        	            	    handler: function() {
        	            	        windowLoader.close();
        	            	    }
        	            	}
        	            	]
        	            })  
	            	]
	    }).show();
		centrarVentana(windowLoader);
	}

	function _11_historialTramite()
	{
	    var window=Ext.create('Ext.window.Window',
	    {
	        title        : 'Detalles del tr&aacute;mite '+_11_params.NTRAMITE
	        ,modal       : true
	        ,buttonAlign : 'center'
	        ,width       : 700
	        ,height      : 400
	        
	        ,items       :
	        [
	            Ext.create('Ext.grid.Panel',
	            {
	                height      : 190
	                ,autoScroll : true
	                ,store      : new Ext.data.Store(
	                {
	                    model     : 'DetalleMC'
	                    ,autoLoad : true
	                    ,proxy    :
	                    {
	                        type         : 'ajax'
	                        ,url         : _URL_DETALLEMC
	                        ,extraParams :
	                        {
	                            'smap1.pv_ntramite_i' : _11_params.NTRAMITE
	                        }
	                        ,reader      :
	                        {
	                            type  : 'json'
	                            ,root : 'slist1'
	                        }
	                    }
	                })
	                ,columns : 
	                [
	                    {
	                        header     : 'Tr&aacute;mite'
	                        ,dataIndex : 'NTRAMITE'
	                        ,width     : 60
	                    }
	                    ,{
	                        header     : 'Consecutivo'
	                        ,dataIndex : 'NMORDINA'
	                        ,width     : 80
	                    }
	                    ,{
	                        header     : 'Fecha de inicio'
	                        ,xtype     : 'datecolumn'
	                        ,dataIndex : 'FECHAINI'
	                        ,format    : 'd M Y'
	                        ,width     : 90
	                    }
	                    ,{
	                        header     : 'Usuario inicio'
	                        ,dataIndex : 'usuario_ini'
	                        ,width     : 150
	                    }
	                    ,{
	                        header     : 'Fecha de fin'
	                        ,xtype     : 'datecolumn'
	                        ,dataIndex : 'FECHAFIN'
	                        ,format    : 'd M Y'
	                        ,width     : 90
	                    }
	                    ,{
	                        header     : 'Usuario fin'
	                        ,dataIndex : 'usuario_fin'
	                        ,width     : 150
	                    }
	                    ,{
	                        width         : 30
	                        ,menuDisabled : true
	                        ,dataIndex    : 'FECHAFIN'
	                        ,renderer     : function(value)
	                        {
	                            debug(value);
	                            if(value&&value!=null)
	                            {
	                                value='';
	                            }
	                            else
	                            {
	                                value='<img src="${ctx}/resources/fam3icons/icons/accept.png" style="cursor:pointer;" data-qtip="Finalizar" />';
	                            }
	                            return value;
	                        }
	                    }
	                ]
	                ,listeners :
	                {
	                    cellclick : function(grid, td,
	                            cellIndex, record, tr,
	                            rowIndex, e, eOpts)
	                    {
	                        if(cellIndex<6)
	                        {
	                            Ext.getCmp('inputReadDetalleHtmlVisor').setValue(record.get('COMMENTS'));
	                        }
	                    }
	                }
	            })
	            ,Ext.create('Ext.form.HtmlEditor',
	            {
	                id        : 'inputReadDetalleHtmlVisor'
	                ,width    : 690
	                ,height   : 200
	                ,readOnly : true
	            })
	        ]
	    });
	    centrarVentanaInterna(window.show());
	    //window.center();
	    Ext.getCmp('inputReadDetalleHtmlVisor').getToolbar().hide();
	}
	
	function _11_turnarDevolucionTramite(grid,rowIndex,colIndex){
		var comentariosText = Ext.create('Ext.form.field.TextArea', {
			fieldLabel: 'Observaciones'
			,labelWidth: 150
			,width: 600
			,name:'smap1.comments'
			,height: 250
			,allowBlank : false
		});
		
		windowLoader = Ext.create('Ext.window.Window',{
			modal       : true,
			buttonAlign : 'center',
			width       : 663,
			height      : 400,
			autoScroll  : true,
			items       : [
				Ext.create('Ext.form.Panel', {
					title: 'Devolver Tr&aacute;mite',
					width: 650,
					url: _URL_ACTSTATUS_TRAMITE,
					bodyPadding: 5,
					items: [comentariosText],
					buttonAlign:'center',
					buttons: [{
						text: 'Devolver'
						,icon:_CONTEXT+'/resources/fam3icons/icons/accept.png'
						,buttonAlign : 'center',
						handler: function() {
							var formPanel = this.up().up();
							if (formPanel.form.isValid()) {
								formPanel.form.submit({
									waitMsg:'Procesando...',
									params: {
										'smap1.ntramite' : _11_params.NTRAMITE,
										'smap1.status'   : _STATUS_DEVOLVER_TRAMITE
									},
									failure: function(){
										mensajeError('Error al devolver el tr&aacute;mite');
									},
									success: function(form, action) {
										//centrarVentanaInterna
										centrarVentanaInterna(mensajeCorrecto('&Eacute;XITO','Se ha turnado correctamente.',function(){
											windowLoader.close();
											Ext.create('Ext.form.Panel').submit(
											{
												url		: _URL_MESACONTROL
												,standardSubmit : true
												,params         :
												{
													'smap1.gridTitle'      : 'Siniestros en espera'
													,'smap2.pv_cdtiptra_i' : 16
												}
											});
										}));

									}
								});
							}else {
								centrarVentanaInterna(Ext.Msg.show({
									title: 'Aviso',
									msg: 'Complete la informaci&oacute;n requerida',
									buttons: Ext.Msg.OK,
									icon: Ext.Msg.WARNING
								}));
							}
						}
					},{
						text: 'Cancelar',
						icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
						buttonAlign : 'center',
						handler: function() {
							windowLoader.close();
						}
					}
					]
				})
			]
		}).show();
		centrarVentana(windowLoader);
	}
	function _11_retornarMedAjustadorAOperador(grid,rowIndex,colIndex){
		Ext.Ajax.request({
			url     : _URL_P_MOV_MAUTSINI
			,params : {
				'params.ntramite': _11_params.NTRAMITE
			}
			,success : function (response) {
				var respuestaMensaje = Ext.decode(response.responseText).mensaje;
				Ext.Ajax.request({
					url: _URL_VALIDADOCCARGADOS,
					params: {
						'params.PV_NTRAMITE_I' : _11_params.NTRAMITE,
						'params.PV_CDRAMO_I'   : _11_params.CDRAMO,
						'params.PV_cdtippag_I' : _11_params.OTVALOR02,
						'params.PV_CDTIPATE_I' : _11_params.OTVALOR07
					},
					success: function(response, opt) {
						var jsonRes=Ext.decode(response.responseText);
						if(jsonRes.success == true){
							var comentariosText = Ext.create('Ext.form.field.TextArea', {
			                	fieldLabel: 'Observaciones'
			            		,labelWidth: 150
			            		,width: 600
			            		,name:'smap1.comments'
			            		, value : respuestaMensaje
			        			,height: 250
			        			,allowBlank : false
			                });
			        		
			        		windowLoader = Ext.create('Ext.window.Window',{
			        	        modal       : true,
			        	        buttonAlign : 'center',
			        	        width       : 663,
			        	        height      : 400,
			        	        autoScroll  : true,
			        	        items       : [
					        	        		Ext.create('Ext.form.Panel', {
					        	                title: 'Turnar a Coordinador de Reclamaciones',
					        	                width: 650,
					        	                url: _URL_ACTSTATUS_TRAMITE,
					        	                bodyPadding: 5,
					        	                items: [comentariosText],
					        	        	    buttonAlign:'center',
					        	        	    buttons: [{
					        	            		text: 'Turnar'
					        	            		,icon:_CONTEXT+'/resources/fam3icons/icons/accept.png'
					        	            		,buttonAlign : 'center',
					        	            		handler: function() {
					        	            			var formPanel = this.up().up();
					        	            	    	if (formPanel.form.isValid()) {
					        	            	    		formPanel.form.submit({
									        	            		        	waitMsg:'Procesando...',
									        	            		        	params: {
									        	            		        		'smap1.ntramite' : _11_params.NTRAMITE, 
									        	            		        		'smap1.status'   : _STATUS_TRAMITE_EN_ESPERA_DE_ASIGNACION
									        	            		        	},
									        	            		        	failure: function(form, action)
									        	            		        	{
									        	            		        		mensajeError('Error al turnar al operador de reclamaciones');
									        	            					},
									        	            					success: function(form, action) {
									        	            						Ext.Ajax.request(
																	    	        {
																	    	            url     : _URL_NOMBRE_TURNADO
																	    	            ,params : 
																	    	            {           
																	    	                'params.ntramite': _11_params.NTRAMITE,
																	    	                'params.rolDestino': 'operadorsini'
																	    	            }
																	    	            ,success : function (response)
																	    	            {
																	    	                var usuarioTurnadoSiniestro = Ext.decode(response.responseText).usuarioTurnadoSiniestro;
																							debug("VALOR DE RESPUESTA -->",usuarioTurnadoSiniestro);
																							mensajeCorrecto('&Eacute;XITO','Se ha turnado correctamente a: '+usuarioTurnadoSiniestro,function(){
																								windowLoader.close();
																								Ext.create('Ext.form.Panel').submit(
																								{
																									url		: _URL_MESACONTROL
																									,standardSubmit : true
																									,params         :
																									{
																										'smap1.gridTitle'      : 'Siniestros en espera'
																										,'smap2.pv_cdtiptra_i' : 16
																									}
																								});
																							});
																							
																							
																							
																	    	            },
																	    	            failure : function ()
																	    	            {
																	    	                //me.up().up().setLoading(false);
																	    	                centrarVentanaInterna(Ext.Msg.show({
																	    	                    title:'Error',
																	    	                    msg: 'Error de comunicaci&oacute;n',
																	    	                    buttons: Ext.Msg.OK,
																	    	                    icon: Ext.Msg.ERROR
																	    	                }));
																	    	            }
																	    	        });
									        	            					}
								        	            					});
					        	            			} else {
					        	            				centrarVentanaInterna(Ext.Msg.show({
					        	            	                   title: 'Aviso',
					        	            	                   msg: 'Complete la informaci&oacute;n requerida',
					        	            	                   buttons: Ext.Msg.OK,
					        	            	                   icon: Ext.Msg.WARNING
					        	            	               }));
					        	            			}
					        	            		}
					        	            	},{
					        	            	    text: 'Cancelar',
					        	            	    icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
					        	            	    buttonAlign : 'center',
					        	            	    handler: function() {
					        	            	        windowLoader.close();
					        	            	    }
					        	            	}
					        	            	]
					        	            })  
			        	            	]
			        	    	}).show();
			        			centrarVentanaInterna(windowLoader);
							}else {
								centrarVentanaInterna(mensajeError(jsonRes.msgResult));
							}
					},
					failure: function(){
						centrarVentanaInterna(mensajeError('Error al turnar.'));
					}
				});
			},
			failure : function (){
				centrarVentanaInterna(Ext.Msg.show({
					title:'Error',
					msg: 'Error de comunicaci&oacute;n',
					buttons: Ext.Msg.OK,
					icon: Ext.Msg.ERROR
				}));
			}
		});
	}
	
	function _p11_agregarFacturas(){
		debug("Valor de _11_params  --> : : : : ",_11_params);
		windowLoader = Ext.create('Ext.window.Window',{
			title         : 'Alta de Facturas'
			,buttonAlign  : 'center'
			,width        : 800
			,height       : 430
			,autoScroll   : true
			,loader       : {
				url       : _VER_ALTA_FACTURAS
				,scripts  : true
				,autoLoad : true
				,params   : {
					'params.ntramite'		: _11_params.NTRAMITE,
					'params.cdTipoPago'		: _11_params.OTVALOR02,
					'params.cdTipoAtencion'	: _11_params.OTVALOR07,
					'params.cdpresta'		: _11_params.OTVALOR11,
					'params.cdramo'			: _11_params.CDRAMO,
					'params.feOcurrencia'	: _11_params.OTVALOR10
				}
			},
			listeners:{
				 close:function(){
					 if(true){
						Ext.create('Ext.form.Panel').submit(
						{
							standardSubmit :true
							,params		:
							{
								'params.ntramite' : _11_params.NTRAMITE
							}
						});
						 panelInicialPral.getForm().reset();
						storeAseguradoFactura.removeAll();
						storeConceptos.removeAll();
					 }
				 }
			}
		}).show();
		centrarVentanaInterna(windowLoader);
	}
	
	function _11_revDocumentosWindow(){
		windowLoader = Ext.create('Ext.window.Window',{
			modal       : true,
			buttonAlign : 'center',
			width       : 600,
			height      : 400,
			autoScroll  : true,
			loader      : {
				url     : _URL_REVISIONDOCSINIESTRO,
				params  : {
					'params.nmTramite'  :  _11_params.NTRAMITE,
					'params.cdTipoPago' : _11_params.OTVALOR02,
					'params.cdTipoAtencion'  : _11_params.OTVALOR07,
					'params.tieneCR'  : !Ext.isEmpty(_11_params.OTVALOR01)
				},
				scripts  : true,
				loadMask : true,
				autoLoad : true,
				ajaxOptions: {
					method: 'POST'
				}
			}
		}).show();
		centrarVentanaInterna(windowLoader);
	}
	
	function _11_solicitarPago(){
		//1.- Verificamos que el tramite ya esta pagado
		if(_11_params.STATUS  == _STATUS_TRAMITE_CONFIRMADO){
			mensajeWarning('Ya se ha solicitado el pago para este tr&aacute;mite.');
			return;
		}else{
			debug("VALORES DE ENTRADA ===> ",_11_params);
			Ext.Ajax.request({
				url	: _URL_MONTO_PAGO_SINIESTRO
				,params:{
					'params.ntramite' : _11_params.NTRAMITE,
					'params.cdramo'   : _11_params.CDRAMO,
					'params.tipoPago' : _11_params.OTVALOR02
				}
				,success : function (response){
					var jsonRespuesta =Ext.decode(response.responseText);
					debug("Valor de Respuesta", jsonRespuesta);
					
					if(jsonRespuesta.success == true){
						if( _11_params.OTVALOR02 ==_TIPO_PAGO_DIRECTO){
							_11_mostrarSolicitudPago();
						}else{
							//Verificamos si tiene la validacion del dictaminador medico
							Ext.Ajax.request({
								url	 : _URL_VAL_AJUSTADOR_MEDICO
								,params:{
									'params.ntramite': _11_params.NTRAMITE
								}
								,success : function (response)
								{
									if(Ext.decode(response.responseText).datosValidacion != null){
										var autAM = null;
										var result ="";
										banderaValidacion = "0";
										var json = Ext.decode(response.responseText).datosValidacion;
										if(json.length > 0){
											for(var i = 0; i < json.length; i++){
												if(json[i].AREAAUTO =="ME"){
													var valorValidacion = json[i].SWAUTORI+"";
													if(valorValidacion == null || valorValidacion == ''|| valorValidacion == 'null'){
														banderaValidacion = "1";
														result = result + 'El m&eacute;dico no autoriza la factura ' + json[i].NFACTURA + '<br/>';
													}
													
												}
											}
											if(banderaValidacion == "1"){
												centrarVentanaInterna(mensajeWarning(result));
											}else{
												_11_mostrarSolicitudPago();
											}
										}else{
											centrarVentanaInterna(mensajeWarning('El m&eacute;dico no ha autizado la factura'));
										}
									}
								},
								failure : function (){
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
					}else {
						centrarVentanaInterna(mensajeWarning(jsonRespuesta.mensaje));
					}
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
	
	function _11_mostrarSolicitudPago(){
		msgWindow = Ext.Msg.show({
			title: 'Aviso',
				msg: '&iquest;Esta seguro que desea solicitar el pago?',
				buttons: Ext.Msg.YESNO,
				icon: Ext.Msg.QUESTION,
				fn: function(buttonId, text, opt){
					if(buttonId == 'yes'){
						storeConceptoPago.load({
							params : {
								'params.cdramo': _tipoProducto
							}
						});
						
						storeAsegurados2.load({
							params:{
								'params.cdunieco': _11_params.CDUNIECO,
								'params.cdramo': _tipoProducto,
								'params.estado': _11_params.ESTADO,
								'params.nmpoliza': _11_params.NMPOLIZA
							}
						});
						
						var pagocheque = Ext.create('Ext.form.field.ComboBox',
						{
							colspan	   :2,				fieldLabel   	: 'Destino Pago', 	name			:'destinoPago',
							allowBlank : false,			editable     	: true,			displayField    : 'value',
							valueField:'key',			forceSelection  : true,			width			:350,
							queryMode    :'local',		store 			: storeDestinoPago
						});

						var concepPago = Ext.create('Ext.form.field.ComboBox',
						{
							colspan	   :2,				fieldLabel   	: 'Concepto Pago', 	name			:'concepPago',
							allowBlank : false,			editable     	: true,				displayField    : 'value',
							valueField:'key',			forceSelection  : true,				width			:350,
							queryMode    :'local',		store 			: storeConceptoPago
						});
						
						var cmbBeneficiario= Ext.create('Ext.form.ComboBox',{
							name:'cmbBeneficiario',			fieldLabel: 'Beneficiario',			queryMode: 'local'/*'remote'*/,			displayField: 'value',
							valueField: 'key',				editable:true,						forceSelection : true,		matchFieldWidth: false,
							queryParam: 'params.cdperson',	minChars  : 2, 						store : storeAsegurados2,	triggerAction: 'all',
							width		 : 350,
							allowBlank: _tipoPago == _TIPO_PAGO_DIRECTO,
							hidden : _tipoPago == _TIPO_PAGO_DIRECTO,
							listeners : {
								'select' : function(e) {
									Ext.Ajax.request({
										url     : _URL_CONSULTA_BENEFICIARIO
										,params:{
											'params.cdunieco'  : _11_params.CDUNIECO,
											'params.cdramo'    : _11_params.CDRAMO,
											'params.estado'    : _11_params.ESTADO,
											'params.nmpoliza'  : _11_params.NMPOLIZA,
											'params.cdperson'  : e.getValue()
										}
										,success : function (response) {
											json = Ext.decode(response.responseText);
											if(json.success==false){
												Ext.Msg.show({
													title:'Beneficiario',
													msg: json.mensaje,
													buttons: Ext.Msg.OK,
													icon: Ext.Msg.WARNING
												});
												panelModificacion.query('combo[name=cmbBeneficiario]')[0].setValue('')
											}
										},
										failure : function (){
											//me.up().up().setLoading(false);
											centrarVentanaInterna(Ext.Msg.show({
												title:'Error',
												msg: 'Error de comunicaci&oacute;n',
												buttons: Ext.Msg.OK,
												icon: Ext.Msg.ERROR
											}));
										}
									});
								}
							}
						});
						
						var cdramoTramite="";
						var cdtipsitTramite ="";
						//3.- Obtenemos los valores de TMESACONTROL  el destino y concepto de pago si es que existen
						
						Ext.Ajax.request({
							url     : _URL_CONSULTA_TRAMITE
							,params:{
								'params.ntramite': _11_params.NTRAMITE
							}
							,success : function (response)
							{
								if(Ext.decode(response.responseText).listaMesaControl != null)
								{
									var json=Ext.decode(response.responseText).listaMesaControl[0];
									cdramoTramite = json.cdramomc;
									cdtipsitTramite = json.cdtipsitmc;
									panelModificacion.query('combo[name=cmbBeneficiario]')[0].setValue(json.otvalor04mc);
									if(json.otvalor18mc !=null)
									{
										panelModificacion.query('combo[name=destinoPago]')[0].setValue(json.otvalor18mc);
									}
									if(json.otvalor19mc !=null)
									{
										panelModificacion.query('combo[name=concepPago]')[0].setValue(json.otvalor19mc);
									}
								}
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
						
						windowCvePago = Ext.create('Ext.window.Window',{
							modal       : true,
							buttonAlign : 'center',
							width       : 550,
							autoScroll  : true,
							items       : [
								panelModificacion = Ext.create('Ext.form.Panel', {
									title: 'Destino de Pago',
									bodyPadding: 5,
									items: [pagocheque,
											concepPago,
											cmbBeneficiario],
									buttonAlign:'center',
									buttons: [
									{
										text: 'Solicitar'
										,icon:_CONTEXT+'/resources/fam3icons/icons/accept.png'
										,buttonAlign : 'center'
										,handler: function() { 
											if (panelModificacion.form.isValid()) {
												var datos=panelModificacion.form.getValues();
												debug("VALOR DEL DATO ")
												//4.- Guardamos la informacion del destino y el tipo de concepto
												Ext.Ajax.request({
													url     : _URL_CONCEPTODESTINO
													,jsonData: {
														params:{
															ntramite:_11_params.NTRAMITE,
															cdtipsit:cdtipsitTramite,
															destinoPago:datos.destinoPago,
															concepPago:datos.concepPago,
															beneficiario : datos.cmbBeneficiario,
															tipoPago : _11_params.OTVALOR02
														}
													}
													,success : function (response)
													{
														windowCvePago.close();
														//5.- Solicitamos el pago le mandamos el tramite y el tipo de pago
														Ext.Ajax.request({
															url: _URL_SOLICITARPAGO,
															params: {
																'params.pv_ntramite_i' : _11_params.NTRAMITE,
																'params.pv_tipmov_i'   : _11_params.OTVALOR02
															},
															success: function(response, opts) {
																var respuesta = Ext.decode(response.responseText);
																if(respuesta.success){
																	centrarVentanaInterna(mensajeCorrecto('&Eacute;XITO','El pago se ha solicitado con &eacute;xito.',function(){
																		Ext.create('Ext.form.Panel').submit(
																		{
																			url		: _URL_MESACONTROL
																			,standardSubmit : true
																			,params         :
																			{
																				'smap1.gridTitle'      : 'Siniestros en espera'
																				,'smap2.pv_cdtiptra_i' : 16
																			}
																		});
																	}));
																}else {
																	centrarVentanaInterna(mensajeError(respuesta.mensaje));
																}
															},
															failure: function(){
																centrarVentanaInterna(mensajeError('No se pudo solicitar el pago.'));
															}
														});
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
											}else {
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
										buttonAlign : 'center',
										handler: function() {
											windowCvePago.close();
										}
									}
									]
								})  
							]
						}).show();
						centrarVentana(windowCvePago);
					}
				}
		});
		centrarVentana(msgWindow);
	}
	
	function eliminarAsegurado(grid,rowIndex){
		if(_tipoPago == _TIPO_PAGO_DIRECTO){
			var record = grid.getStore().getAt(rowIndex);
			debug('record.eliminarAsegurado:',record.raw);
			centrarVentanaInterna(Ext.Msg.show({
				title: 'Aviso',
				msg: '&iquest;Esta seguro que desea eliminar el asegurado?',
				buttons: Ext.Msg.YESNO,
				icon: Ext.Msg.QUESTION,
				fn: function(buttonId, text, opt){
					if(buttonId == 'yes'){
						debug("VALOR DEL RECORD");
						debug(record);
						Ext.Ajax.request({
							url: _URL_ELIMINAR_ASEGURADO,
							params: {
								'params.nmtramite'  : panelInicialPral.down('[name=params.ntramite]').getValue(),
								'params.nfactura'   : panelInicialPral.down('[name=params.nfactura]').getValue(),
								'params.cdunieco'   : record.get('CDUNIECO'),
								'params.cdramo'     : record.get('CDRAMO'),
								'params.estado'     : record.get('ESTADO'),
								'params.nmpoliza'   : record.get('NMPOLIZA'),
								'params.nmsuplem'   : record.get('NMSUPLEM'),
								'params.nmsituac'   : record.get('NMSITUAC'),
								'params.cdtipsit'   : record.get('CDTIPSIT'),
								'params.cdperson'   : record.get('CDPERSON'),
								'params.feocurre'   : record.get('FEOCURRE'),
								'params.nmsinies'   : record.get('NMSINIES')
							},
							success: function(response) {
								var res = Ext.decode(response.responseText);
								if(res.success){
									mensajeCorrecto('Aviso','Se ha eliminado con &eacute;xito.',function(){
										banderaAsegurado = 0;
										storeAseguradoFactura.load({
											params: {
												'smap.ntramite'   : panelInicialPral.down('[name=params.ntramite]').getValue(),
												'smap.nfactura'   : panelInicialPral.down('[name=params.nfactura]').getValue()
											}
										});
									});
								}else {
									centrarVentanaInterna(mensajeError('No se pudo eliminar.'));
								}
							},
							failure: function(){
								centrarVentanaInterna(mensajeError('No se pudo eliminar.'));
							}
						});
					}
				}
			}));
		}else{
			centrarVentanaInterna(Ext.Msg.show({ 
				title: 'Aviso',
				msg: 'El asegurado no puede ser eliminado.',
				buttons: Ext.Msg.OK,
				icon: Ext.Msg.WARNING
			}));
		}
	}
	
	//FIN DE FUNCIONES
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