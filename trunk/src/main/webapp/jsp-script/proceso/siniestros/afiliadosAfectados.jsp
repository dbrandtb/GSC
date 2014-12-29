<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Afiliados Afectados</title>
		<script type="text/javascript">
		/*LLAMADO Y ASIGNACION DE LAS VARIABLES*/
			var _CONTEXT 							= '${ctx}';
			var _URL_CATALOGOS						= '<s:url namespace="/catalogos" action="obtieneCatalogo" />';
			var _UrlDocumentosPoliza				= '<s:url namespace="/documentos" action="ventanaDocumentosPoliza"   />';
			var _CATALOGO_TipoMoneda				= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TIPO_MONEDA"/>';
			var _ROL_MEDICO							= '<s:property value="@mx.com.gseguros.portal.general.util.RolSistema@MEDICO_AJUSTADOR.cdsisrol" />';
			var _CATALOGO_COB_X_VALORES 			= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@COBERTURASXVALORES"/>';
			var _11_url_RequiereAutServ				= '<s:url namespace="/siniestros" action="obtieneRequiereAutServ"		 />';
			var _11_urlIniciarSiniestroSinAutServ	= '<s:url namespace="/siniestros"  action="generarSiniestrosinAutServ"	/>';
			var _11_urlIniciarSiniestroTworksin		= '<s:url namespace="/siniestros"  action="iniciarSiniestroTworksin"	  />';
			var _p12_urlObtenerSiniestrosTramite	= '<s:url namespace="/siniestros"  action="obtenerSiniestrosTramite" />';
			var _URL_LISTA_COBERTURA 				= '<s:url namespace="/siniestros"  action="consultaListaCoberturaPoliza" />';
			var _URL_LISTA_SUBCOBERTURA				= '<s:url namespace="/siniestros"  action="consultaListaSubcobertura" />';
			var _TIPO_PAGO_DIRECTO					= '<s:property value="@mx.com.gseguros.portal.general.util.TipoPago@DIRECTO.codigo"/>';
			var _TIPO_PAGO_REEMBOLSO				= '<s:property value="@mx.com.gseguros.portal.general.util.TipoPago@REEMBOLSO.codigo"/>';
			var _URL_GUARDA_CAMBIOS_FACTURA			= '<s:url namespace="/siniestros" action="guardaFacturaTramite" />';
			var _URL_LISTA_AUTSERVICIO				= '<s:url namespace="/siniestros" action="consultaAutServicioSiniestro"		 />';
			var _URL_LISTA_MSINIESTRO				= '<s:url namespace="/siniestros" action="consultaSiniestroMaestro"		 />';
			var _URL_DATOS_VALIDACION				= '<s:url namespace="/siniestros" action="consultaDatosValidacionSiniestro"		 />';
			var _URL_LoadConceptos					= '<s:url namespace="/siniestros" action="obtenerMsinival" />';
			var _11_params							= <s:property value="%{convertToJSON('params')}" escapeHtml="false" />;
			var _CATALOGO_TipoConcepto				= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TIPO_CONCEPTO_SINIESTROS"/>';
			var _CATALOGO_ConceptosMedicos			= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@CODIGOS_MEDICOS"/>';
			var _URL_MONTO_ARANCEL					= '<s:url namespace="/siniestros"  action="obtieneMontoArancel"/>';
			var _UrlAjustesMedicos					=  '<s:url namespace="/siniestros" action="includes/ajustesMedicos" />';
			var _URL_GUARDA_CONCEPTO_TRAMITE		= '<s:url namespace="/siniestros"  		action="guardarMsinival"/>';
			var _CDROL								= '<s:property value="params.cdrol" />';
			var _11_urlTabbedPanel					= '<s:url namespace="/siniestros"  action="includes/detalleSiniestro"/>';
			var _11_urlMesaControl					= '<s:url namespace="/mesacontrol" action="mcdinamica"					/>';
			var _CAT_CAUSASINIESTRO					= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@CAUSA_SINIESTRO"/>';
			var _URL_LISTA_CPTICD					= '<s:url namespace="/siniestros"  action="consultaListaCPTICD" />';
			var _URL_ACTUALIZA_INFO_GRAL_SIN        = '<s:url namespace="/siniestros"      action="actualizaDatosGeneralesSiniestro" />';
			var _URL_ASOCIA_MSINEST_REFERENCIADO    = '<s:url namespace="/siniestros"      action="asociaMsiniestroReferenciado" />';
			var _URL_LISTADO_ASEGURADO          	= '<s:url namespace="/siniestros"       action="consultaListaAsegurado" />';
			var _URL_CONSULTA_LISTADO_POLIZA		= '<s:url namespace="/siniestros" 		action="consultaListaPoliza" />';
			var _URL_GUARDA_ASEGURADO				= '<s:url namespace="/siniestros" 		action="guardaaseguradoUnico" />';
			var _URL_GENERAR_CALCULO				= '<s:url namespace="/siniestros" 		action="generarCalculoSiniestros" />';
			debug("VALOR DE _11_params --->");
			debug(_11_params);
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
			var _11_textfieldAseguradoMsiniest;
			var _11_textfieldNmSiniest;
			var panelInicialPral;
			var storeAseguradoFactura;
			var modPolizasAltaTramite;
			//var storeFacturaDirectoNva;
			var gridAutorizacion;
			var gridMsiniestMaestro;
			var storeListadoAutorizacion;
			var storeListadoSiniestMaestro;
			var cmbCveTipoConcepto;
			var cmbCveConcepto;
			var _11_windowRechazoSiniestro;
			var _11_formRechazo;
			var _11_conceptoSeleccionado=null;
			var _11_aseguradoSeleccionado = null;
			var _tipoPago = _11_params.OTVALOR02;
			var banderaConcepto = "0";
			var storeCobertura;
			var storeSubcobertura;
			var storeCoberturaxAsegurado;
			var storeSubcoberturaAsegurado;
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
						}
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

			Ext.onReady(function()
			{
				/*############################		MODEL		########################################*/
//MODELO DE LOS ASEGURADOS
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
						{type:'string',	name:'PTISRASEG'},		{type:'string',	name:'PTIMPCEDASEG'}
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
						{type:'string',	name:'PTMTOARA'},		{type:'string',	name:'TOTAJUSMED'},		{type:'string',	name:'SUBTAJUSTADO'}
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
								{type:'string',	name:'FESOLICI'},			{type:'string',	name:'NOMPROV'}]
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
				/*############################		STORE		########################################*/
//STORE DE LAS COBERTURAS X VALORES
				storeCobertura = Ext.create('Ext.data.Store', {
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
			    
				var storeSubcoberturaAseguradoRender = Ext.create('Ext.data.Store',
						{
							model:'Generic',
							autoLoad:true,
							cargado:false,
							proxy: {
								type: 'ajax',
								url : _URL_LISTA_SUBCOBERTURA,
								reader: {
					                type: 'json',
					                root: 'listaSubcobertura'
					            }
							}
							,listeners:
							{
								load : function()
								{
									this.cargado=true;
									if(!Ext.isEmpty(gridFacturaDirecto))
									{
										gridFacturaDirecto.getView().refresh();
									}
								}
							}
						});
// STORE PARA EL LISTADO DE LAS AUTORIZACIONES DE SERVICIO
				storeListadoAutorizacion = new Ext.data.Store(
				{
					pageSize : 5
					,model	  : 'modelListadoAutorizacion'
					,autoLoad  : false
					,proxy	 :
					{
						enablePaging : true,
						reader	   : 'json',
						type		 : 'memory',
						data		 : []
					}
				});
// STORE PARA EL LISTADO SINIESTRO MAESTRO
				storeListadoSiniestMaestro = new Ext.data.Store(
				{
					pageSize : 5
					,model	  : 'modelListadoSiniestroMaestro'
					,autoLoad  : false
					,proxy	 :
					{
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
				,listeners:
				{
					load : function()
					{
						this.cargado=true;
						if(!Ext.isEmpty(gridFacturaDirecto))
						{
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
				,listeners:
				{
					load : function()
					{
						this.cargado=true;
						if(!Ext.isEmpty(gridFacturaDirecto))
						{
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
						extraParams : {catalogo:_CATALOGO_TipoMoneda},
						reader: {
							type: 'json',
							root: 'lista'
						}
					}
				});
				storeTipoMoneda.load();
// STORE PARA EL OBTENER LOS VALORES DE LOS ASEGURADOS
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
//STORE PARA EL TIPO DE CONCEPTO
				var storeTipoConcepto = Ext.create('Ext.data.JsonStore', {
					model:'Generic',
					proxy:
					{
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
// STORE PARA OBTENER EL LISTADO DE LOS ICD,CDT Y UB
				var storeConceptosCatalogo = Ext.create('Ext.data.JsonStore', {
					model:'Generic',
					proxy: {
						type: 'ajax',
						url:_URL_CATALOGOS,
						extraParams : {catalogo:_CATALOGO_ConceptosMedicos},
						reader: {
							type: 'json',
							root: 'lista'
						}
					}
				});

				var storeConceptosCatalogoRender = Ext.create('Ext.data.JsonStore', {
					model:'Generic',
					autoLoad:true,
					cargado:false,
					proxy: {
						type: 'ajax',
						url: _URL_CATALOGOS,
						extraParams : {catalogo:_CATALOGO_ConceptosMedicos},
						reader: {
							type: 'json',
							root: 'lista'
						}
					},listeners:
					{
						load : function()
						{
							this.cargado=true;
							if(!Ext.isEmpty(gridEditorConceptos))
							{
								gridEditorConceptos.getView().refresh();
							}
						}
					}
				});

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
//STORE DE LAS POLIZAS
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
				
				/*############################		DECLARACION DE COMBOX Y LABEL		########################################*/
				var comboICDPrimario = Ext.create('Ext.form.field.ComboBox',
				{
					allowBlank: false,				displayField : 'value',		forceSelection : true,
					name:'idComboICDPrimario',		valueField   : 'key',		store : storeTiposICDPrimario,
					matchFieldWidth: false,			queryMode :'remote',		queryParam: 'params.otclave',
					minChars  : 2,					editable:true,				triggerAction: 'all',		hideTrigger:true,
					listeners : {
						'select' : function(combo, record) {
							_11_aseguradoSeleccionado.set('CDICD',this.getValue());
						}
					}
				});
				
				var comboICDSecundario = Ext.create('Ext.form.field.ComboBox',
				{
					allowBlank: false,				displayField : 'value',		forceSelection : true,
					name:'idComboICDSEcundario',	valueField   : 'key',		store : storeTiposICDSecundario,
					matchFieldWidth: false,			queryMode :'remote',		queryParam: 'params.otclave',
					minChars  : 2,					editable:true,				triggerAction: 'all',		hideTrigger:true,
					listeners : {
						'select' : function(combo, record) {
							_11_aseguradoSeleccionado.set('CDICD2',this.getValue());
						}
					}
				});
				
				var cmbCausaSiniestro = Ext.create('Ext.form.ComboBox',
				{
					name:'cmbCausaSiniestro',			store: storeCausaSinestro,		value:'1',		queryMode:'local',  
					displayField: 'value',		valueField: 'key',			editable:false,		allowBlank:false
				});

				var cmbTipoMoneda = Ext.create('Ext.form.ComboBox',
				{
					name:'params.tipoMoneda',		fieldLabel	: 'MONEDA',		store: storeTipoMoneda,			queryMode:'local',  
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

				
				var cobertura = Ext.create('Ext.form.field.ComboBox',
				{
					name:'params.cdgarant',			fieldLabel : 'COBERTURA',	allowBlank: false,				displayField : 'value',
					valueField   : 'key',			forceSelection : true,		matchFieldWidth: false,		hidden: true,
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

				
				coberturaxAsegurado = Ext.create('Ext.form.field.ComboBox',
			    {
			    	allowBlank: false,			displayField : 'dsgarant',		id:'idCobAfectada',		name:'cdgarant',
			    	valueField   : 'cdgarant',	forceSelection : true,			matchFieldWidth: false,
			    	queryMode :'remote',				store : storeCoberturaxAsegurado,		triggerAction: 'all',			editable:false,
			    	listeners : {
						'select' : function(combo, record) {
							_11_aseguradoSeleccionado.set('CDGARANT',this.getValue());
							storeSubcoberturaAsegurado.removeAll();
							storeSubcoberturaAsegurado.load({
								params:{
									'params.cdgarant' :this.getValue()
								}
							});
						}
					}
			    });
				
				var subCoberturaAsegurado = Ext.create('Ext.form.field.ComboBox',
					    {
			    	allowBlank: false,				displayField : 'value',			id:'idSubcobertura1',		name:'cdconval',
			    	labelWidth: 170,				valueField   : 'key',			forceSelection : true,			matchFieldWidth: false,
			    	queryMode :'remote',			store : storeSubcoberturaAsegurado,		triggerAction: 'all',			editable:false,
			    	listeners : {
						'select' : function(combo, record) {
							_11_aseguradoSeleccionado.set('CDCONVAL',this.getValue());
						}
					}
			    });

				var subCobertura = Ext.create('Ext.form.field.ComboBox',
				{
					name:'params.cdconval',		fieldLabel : 'SUBCOBERTURA',	allowBlank: false,				displayField : 'value',			id:'idSubcobertura',
					valueField   : 'key',			forceSelection : true,			matchFieldWidth: false,		hidden: true,
					queryMode :'remote',			store : storeSubcobertura,		triggerAction: 'all',			editable:false
				});
						
				
				cmbCveTipoConcepto = Ext.create('Ext.form.ComboBox',
				{
					name:'params.idconcep',		store: storeTipoConcepto,		queryMode:'local',
					displayField: 'value',		valueField: 'key',				editable:false,				allowBlank:false,
					listeners:{
						select: function (combo, records, opts){
							var cdTipo = records[0].get('key');
							storeConceptosCatalogo.proxy.extraParams=
							{
								'params.idPadre' : cdTipo
								,catalogo		: _CATALOGO_ConceptosMedicos
							};
						}
					}
				});

				cmbCveConcepto = Ext.create('Ext.form.ComboBox',
				{
					name:'params.cdconcep',		store: storeConceptosCatalogo,		queryMode:'remote',
					displayField: 'value',		valueField: 'key',					editable:true,				allowBlank:false,
					forceSelection: true,		queryParam  : 'params.descripc',	hideTrigger : true,			minChars	: 3
					,listeners : {
						select:function(e){
							Ext.Ajax.request(
							{
								url	 : _URL_MONTO_ARANCEL
								,params:{
									'params.tipoConcepto'   : _11_conceptoSeleccionado.get('IDCONCEP'),
									'params.idProveedor'	: panelInicialPral.down('[name=params.cdpresta]').getValue(),
									'params.idConceptoTipo' : e.getValue()
								}
								,success : function (response)
								{
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

				/////////////////////////////////////////////////////////////////////////////////////
				Ext.define('EditorFacturaDirecto', {
					extend: 'Ext.grid.Panel',
					name:'editorFacturaDirecto',
					title: 'Asegurados',
					icon		: '${ctx}/resources/fam3icons/icons/user.png',
					frame: true,
					selType  : 'rowmodel',
					initComponent: function(){
						Ext.apply(this, {
						height: 300,
						plugins  :
						[
							Ext.create('Ext.grid.plugin.CellEditing',
							{
								clicksToEdit: 1
								,listeners :
								{
									beforeedit : function()
									{
										_11_aseguradoSeleccionado = gridFacturaDirecto.getView().getSelectionModel().getSelection()[0];
										storeCoberturaxAsegurado.proxy.extraParams=
										{
											'params.cdunieco':_11_aseguradoSeleccionado.get('CDUNIECO'),
							            	'params.estado':_11_aseguradoSeleccionado.get('ESTADO'),
							            	'params.cdramo':_11_aseguradoSeleccionado.get('CDRAMO'),
							            	'params.nmpoliza':_11_aseguradoSeleccionado.get('NMPOLIZA'),
							            	'params.nmsituac':_11_aseguradoSeleccionado.get('NMSITUAC')
										};
										
										storeSubcoberturaAsegurado.load({
											params:{
												'params.cdgarant' :_11_aseguradoSeleccionado.get('CDGARANT')
											}
										});
										
										
										
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
								,width		: 70
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
										icon	 : '${ctx}/resources/fam3icons/icons/folder.png'
										,tooltip : 'Siniestro'
										,handler : _11_pedirMsiniestMaestro
									}
								]
							},
							{
								header: 'Id<br/>Sini.',				dataIndex: 'NMSINIES'
							},
							{
								header: '# Auto.',					dataIndex: 'NMAUTSER'
							},
							{
								header: 'Id<br/>Sini. Existente',	dataIndex: 'NMSINREF'
							},
							{
								header: 'Causa <br/> Siniestro', 				dataIndex: 'CDCAUSA'
								,editor : cmbCausaSiniestro
								,renderer : function(v) {
									var leyenda = '';
									if (typeof v == 'string')
									{
										storeCausaSinestro.each(function(rec) {
											if (rec.data.key == v) {
												leyenda = rec.data.value;
											}
										});
									}else
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
								header: 'Cobertura',			dataIndex: 'CDGARANT',		allowBlank: false
								,editor: coberturaxAsegurado
								,renderer : function(v) {
									var leyenda = v;
									/*var leyenda = '';
									if (typeof v == 'string')// tengo solo el indice
									{
										storeCoberturaxAsegurado.each(function(rec) {
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
									}*/
									return leyenda;
								}
							},
							{
								header: 'Subcobertura',			dataIndex: 'CDCONVAL',	allowBlank: false
								,editor: subCoberturaAsegurado
								,renderer : function(v) {
									var leyenda = v;
									return leyenda;
								}
							},
							{
								header: 'ICD<br/>Principal', 				dataIndex: 'CDICD'
								,editor : comboICDPrimario
								,renderer : function(v) {
									var leyenda = '';
									if (typeof v == 'string')// tengo solo el indice
									{
										if(storeTiposICDPrimarioRender.cargado)
										{
											storeTiposICDPrimarioRender.each(function(rec)
										    {
												if (rec.data.key == v)
											    {
													leyenda = rec.data.value;
												}
											});
										}
										else
										{
										    leyenda= v;
										}
									}else // tengo objeto que puede venir como Generic u otro mas complejo
									{
										if (v.key && v.value)
										{
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
									if (typeof v == 'string')// tengo solo el indice
									{
										if(storeTiposICDSecundarioRender.cargado)
										{
											storeTiposICDSecundarioRender.each(function(rec)
										    {
												if (rec.data.key == v)
											    {
													leyenda = rec.data.value;
												}
											});
										}
										else
										{
										    leyenda= v;
										}
									}else // tengo objeto que puede venir como Generic u otro mas complejo
									{
										if (v.key && v.value)
										{
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
								hidden: true,
								renderer		: function(v)
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
							}/*,
							{
								header: 'Imp. Cedular',	dataIndex: 'PTIMPCEDASEG',renderer  : Ext.util.Format.usMoney
							}*/
						],
						tbar:[
								/*{
									text	: 'Agregar Asegurado'
									,icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/book.png'
									,handler : _p21_agregarAsegurado
								}*/
								{
									text	: 'Generar Calculo'
									,icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/book.png'
									,handler : _p21_generarCalculo
								}
							],
							
						listeners: {
							select: function (grid, record, index, opts){
								debug("VALOR DEL RECORD SELECCIONADO", record);
								if (banderaConcepto == "1"){
									debug("Guardamos los conceptos ");
									//Mandamos a guardar los conceptos
									_guardarConceptosxFactura();
								}else{
									var numSiniestro = record.get('NMSINIES');
									if(numSiniestro.length == "0"){
										revisarDocumento(grid,index)
										
									}else{
										/*VERIFICAMOS LA INFORMACIÓN ANTES DE CARGA A OTRO*/
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
							Ext.create('Ext.grid.plugin.CellEditing',
							{
								clicksToEdit: 1
								,listeners :
								{
									beforeedit : function()
									{
										_11_conceptoSeleccionado = gridEditorConceptos.getView().getSelectionModel().getSelection()[0];
										storeConceptosCatalogo.proxy.extraParams=
										{
											'params.idPadre' :  _11_conceptoSeleccionado.get('IDCONCEP')
											,catalogo		: _CATALOGO_ConceptosMedicos
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
								/*,renderer : function(v) {
									var leyenda = '';
									if (typeof v == 'string')
									{
										debug("storeConceptosCatalogoRender -->",storeConceptosCatalogoRender.cargado);
										debug("storeConceptosCatalogoRender.each",storeConceptosCatalogoRender);
										if(storeConceptosCatalogoRender.cargado)
										{
											storeConceptosCatalogoRender.each(function(rec)
										    {
												debug("cat-->",rec.data);
												if (rec.data.key == v)
											    {
													leyenda = rec.data.value;
												}
											});
										}
										else
										{
										    leyenda='Cargando...';
										}
									}else
									{
										if (v.key && v.value)
										{
											leyenda = v.value;
										} else {
											leyenda = v.data.value;
										}
										leyenda= v;
									}
									return leyenda;
								}*/,renderer : function(v) {
									var leyenda = v;
									return leyenda;
								}
							},
							{
								header: 'Valor Arancel', 				dataIndex: 'PTMTOARA',	width : 150,				renderer: Ext.util.Format.usMoney
								,editor: {
									xtype: 'numberfield',
									decimalSeparator :'.',
									allowBlank: false,
									listeners : {
										change:function(e){
											var valorArancel = e.getValue();
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
						banderaConcepto = "1";
						//_guardarConceptosxFactura();
					}
				});
				gridEditorConceptos = new EditorConceptos();

				gridAutorizacion= Ext.create('Ext.grid.Panel',
				{
					id			 : 'clausulasGridId'
					,store		 :  storeListadoAutorizacion
					//,collapsible   : true
					//,titleCollapse : true
					,style		 : 'margin:5px'
					,selType: 'checkboxmodel'
					,width   : 600
					,height: 200
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
							 header	 : 'clave Proveedor'
							 ,dataIndex : 'CDPROVEE'
							 ,width	 : 100
						 }
						 ,
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
							_11_textfieldNmautserv.setValue(record.get('NMAUTSER'));
						}
					}
					
						
				});
				gridAutorizacion.store.sort([
					{
						property	: 'nmautser',			direction   : 'DESC'
					}
				]);

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
							 ,hidden : true
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
				gridMsiniestMaestro.store.sort([
					{
						property	: 'nmautser',			direction   : 'DESC'
					}
				]);
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
							xtype		: 'textfield',			fieldLabel	: 'N0. TR&Aacute;MITE',		name	: 'params.ntramite', readOnly   : true
						},
						{
							xtype		: 'textfield',			fieldLabel	: 'PROVEEDOR',		name	: 'params.cdpresta',		hidden:true
						},
						{
							xtype		: 'textfield',			fieldLabel	: 'TIP SERVICIO',		name	: 'params.cdtipser',		hidden:true
						},
						{
							xtype		: 'textfield',			fieldLabel	: 'NO. FACTURA',			name	: 'params.nfactura', readOnly   : true
						},
						{
							xtype		: 'datefield',			fieldLabel	: 'FECHA FACTURA',			name	: 'params.fefactura',	format	: 'd/m/Y'
						},
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
						}
						<s:property value='%{"," + imap.tatrisinItems}' />
						<s:property value='%{"," + imap.itemsEdicion}' />
					]
					,buttonAlign:'center'
					,buttons: [
						{
							text:'Aplicar Cambios Factura',
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
								panelInicialPral.getForm().reset();
								storeAseguradoFactura.removeAll();
								storeConceptos.removeAll();
								modPolizasAltaTramite.close();
							}
						}
					]
					
				});
				for(var i=0;i<panelInicialPral.items.items.length;i++)
				{
					panelInicialPral.items.items[i].labelWidth =150;
					panelInicialPral.items.items[i].style	  = 'margin-right:100px;';
				}

				modPolizasAltaTramite = Ext.create('Ext.window.Window',
				{
					title		: 'Detalle Factura'
					,modal	   : true
					,resizable   : false
					,buttonAlign : 'center'
					,closable	: true
					,closeAction: 'hide'
					,width		 : 900
					,items	   : 
					[
						panelInicialPral
						,gridFacturaDirecto
						,gridEditorConceptos
					],
					listeners:{
						 close:function(){
							 if(true){
								Ext.create('Ext.form.Panel').submit(
								{
									standardSubmit :true
									,params		:
									{
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

				
				gridPolizasAltaTramite= Ext.create('Ext.grid.Panel',
				{
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
												modPolizasAsegurado.hide();
										}else{
											// No se cumple la condición la fecha de ocurrencia es mayor a la fecha de alta de tramite
											Ext.Msg.show({
												title:'Error',
												msg: 'La fecha de ocurrencia es mayor a la fecha de alta del asegurado',
												buttons: Ext.Msg.OK,
												icon: Ext.Msg.ERROR
											});
											modPolizasAsegurado.hide();
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
										modPolizasAsegurado.hide();
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
									modPolizasAsegurado.show();
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
										debug("Entra al else000",datos);
										Ext.Ajax.request(
										{
											url	 : _URL_GUARDA_ASEGURADO
											,params:{
												//'params.nmtramite'  : _11_params.NTRAMITE,
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
											,success : function (response)
											{
												alert("Guardado");
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
										
										
										
										panelListadoAsegurado.form.submit({
										waitMsg:'Procesando...',
										//standardSubmit : true,
										params: {
											//'params.nmtramite'  : _11_params.NTRAMITE,
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
											alert("Correcto");
											
											
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
				
				Ext.define('_11_WindowRechazoSiniestro',
				{
					extend		 : 'Ext.window.Window'
					,initComponent : function()
					{
						debug('_11_WindowRechazoSiniestro initComponent');
						Ext.apply(this,
						{
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

				Ext.define('_11_WindowPedirAut',
				{
					extend		 : 'Ext.window.Window'
					,initComponent : function()
					{
						debug('_11_windowPedirAut initComponent');
						Ext.apply(this,
						{
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
				Ext.define('_11_FormPedirAuto',
				{
					extend		 : 'Ext.form.Panel'
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
				
				
				
				Ext.define('_11_WindowPedirMsiniest',
						{
							extend		 : 'Ext.window.Window'
							,initComponent : function()
							{
								debug('_11_WindowPedirMsiniest initComponent');
								Ext.apply(this,
								{
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
						Ext.define('_11_formPedirMsiniest',
						{
							extend		 : 'Ext.form.Panel'
							,initComponent : function()
							{
								debug('_11_formPedirMsiniest initComponent');
								Ext.apply(this,
								{
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
				
				
				
				
				Ext.define('_11_FormRechazo',
				{
					extend		 : 'Ext.form.Panel'
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
			var venDocuTramite=Ext.create('Ext.window.Window',
					{
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
							,url	  : _UrlDocumentosPoliza
							,params   :
							{
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
				
				_11_textfieldAseguradoMsiniest = Ext.create('Ext.form.TextField',
				{
					fieldLabel : 'Asegurado'
					,width	 : 500
					,readOnly  : true
				});
				_11_textfieldNmSiniest = Ext.create('Ext.form.NumberField',
				{
					fieldLabel  : 'No. de Siniestro'
					,name : 'nmsiniestroRef'
					,readOnly   : false
					,allowBlank : false
					,hidden : true
					,minLength  : 1
				});
						
				_11_formPedirAuto	= new _11_FormPedirAuto();
				_11_windowPedirAut	= new _11_WindowPedirAut();
				_11_formPedirMsiniest	= new _11_formPedirMsiniest();
				_11_WindowPedirMsiniest	= new _11_WindowPedirMsiniest();
				
				_11_formRechazo		= new _11_FormRechazo();
				_11_windowRechazoSiniestro = new _11_WindowRechazoSiniestro();
				
			/**FIN DE CONTENIDO***/
			});

	/*############################		INICIO DE FUNCIONES		########################################*/
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
		
		if(valido){
		}
	}

	function _11_editar(grid,rowindex)
	{
		_11_recordActivo = grid.getStore().getAt(rowindex);
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
				panelInicialPral.down('[name=params.ntramite]').setValue(_11_recordActivo.get('ntramite'));
				panelInicialPral.down('[name=params.cdpresta]').setValue(_11_recordActivo.get('cdpresta'));
				panelInicialPral.down('[name=params.cdtipser]').setValue(_11_recordActivo.get('cdtipser'));
				panelInicialPral.down('[name=params.nfactura]').setValue(_11_recordActivo.get('factura'));
				panelInicialPral.down('[name=params.fefactura]').setValue(_11_recordActivo.get('fechaFactura'));
				
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
				if(Ext.decode(response.responseText).datosValidacion != null){
					var aplicaIVA = null;
					var ivaRetenido = null;
					var ivaAntesDespues = null;
					var autAR = null;
					var autAM = null;
					var commAR = null;
					var commAM = null;
					var json=Ext.decode(response.responseText).datosValidacion;
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
		
		
		//var record = grid.getStore().getAt(rowIndex);
		//alert("Entra");
		/*var valido = _11_formPedirMsiniest.isValid();
		if(!valido)
		{
			datosIncompletos();
		}else{
		
		}*/

	}
	
	function guardarDatosComplementarios(grid,rowIndex){
		var record = grid.getStore().getAt(rowIndex);
		Ext.Ajax.request(
		{
			url	 : _URL_ACTUALIZA_INFO_GRAL_SIN
			,params:{
				'params.cdunieco' : record.data.CDUNIECO,
				'params.cdramo'   : record.data.CDRAMO,
				'params.estado'   : record.data.ESTADO,
				'params.nmpoliza' : record.data.NMPOLIZA,
				'params.nmsuplem' : record.data.NMSUPLEM,
				'params.aaapertu' : record.data.AAAPERTU,
				'params.nmsinies' : record.data.NMSINIES,
				'params.feocurre' : record.data.FEOCURRE,
				'params.nmreclamo': record.data.NMRECLAMO,
				'params.cdicd'    : record.data.CDICD,
				'params.cdicd2'   : record.data.CDICD2,
				'params.cdcausa'  : record.data.CDCAUSA,
				'params.ntramite' : panelInicialPral.down('[name=params.ntramite]').getValue(),
				'params.cdgarant' : record.data.CDGARANT,
				'params.cdconval' : record.data.CDCONVAL
			}
			,success : function (response)
			{
				storeAseguradoFactura.load({
					params: {
						'smap.ntramite'   : panelInicialPral.down('[name=params.ntramite]').getValue(),
						'smap.nfactura'   : panelInicialPral.down('[name=params.nfactura]').getValue()
					}
				});
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
	
	function _11_pedirMsiniestMaestro(grid,rowIndex)
	{
		//1.- Ver si esta ya tiene generado un siniestro
		var record = grid.getStore().getAt(rowIndex);
		_11_recordActivo = record;
		debug('_11_recordAsegurado: ###########',record.data);
		
		var idReclamacion = record.data.NMSINIES;
		valido = idReclamacion && idReclamacion>0;
		
		if ( _CDROL == _ROL_MEDICO){
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
		}
	}
	function revisarDocumento(grid,rowIndex)
	{
		var record = grid.getStore().getAt(rowIndex);
		debug('record.raw:',record.raw);
		var valido = true;
		Ext.Ajax.request(
		{
			url	: _11_url_RequiereAutServ
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
										url	  : _11_urlIniciarSiniestroSinAutServ
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
						modal	   : true,
						buttonAlign : 'center',
						title: 'Informaci&oacute;n general',
						width	   : 800,
						height	  : 450,
						autoScroll  : true,
						loader	  : {
							url	 : _11_urlTabbedPanel
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
			
	function _mostrarVentanaAjustes(grid,rowIndex,colIndex){
		var record = grid.getStore().getAt(rowIndex);
		var recordFactura = gridFacturaDirecto.getSelectionModel().getSelection()[0];
		if ( _CDROL == _ROL_MEDICO){
			windowLoader = Ext.create('Ext.window.Window',{
				modal	   : true,
				buttonAlign : 'center',
				title: 'Ajustes M&eacute;dico',
				width	   : 800,
				height	  : 450,
				//autoScroll  : true,
				loader	  : {
					url	 : _UrlAjustesMedicos,
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
					,cdgarant : _11_aseguradoSeleccionado.get('CDGARANT')
					,cdconval : _11_aseguradoSeleccionado.get('CDCONVAL')
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
					,ptpcioex : record.get('PTPCIOEX')
					,dctoimex : record.get('DCTOIMEX')
					,ptimpoex : record.get('PTIMPOEX')
					,mtoArancel : record.get('PTMTOARA')
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
		ventanaAgregarAsegurado.show();
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
						'smap.ntramite'   : panelInicialPral.down('[name=params.ntramite]').getValue(),
						'smap.nfactura'   : panelInicialPral.down('[name=params.nfactura]').getValue()
					}
				});
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
	
	
	function _p21_agregarConcepto()
	{
		if(gridFacturaDirecto.getSelectionModel().hasSelection()){
			var recordFactura = gridFacturaDirecto.getSelectionModel().getSelection()[0];
			recordFactura.get('NMSINIES')
			var idReclamacion = recordFactura.get('NMSINIES');
			valido = idReclamacion && idReclamacion>0;
			if(valido){
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
							PTIMPOEX : '0.00'
						}));
			}else{
				mensajeWarning(
						'Debes generar una autorizaci&oacute;n para el asegurado'
				);
			}
		}else{
			centrarVentanaInterna(mensajeWarning("Debe seleccionar un asegurado para agregar sus conceptos"));
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
				url	  : _11_urlIniciarSiniestroTworksin
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