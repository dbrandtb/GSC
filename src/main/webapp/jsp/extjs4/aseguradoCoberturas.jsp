<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
	///////////////////////
	//////variables //////
	/*///////////////////*/
	var storeCoberturas;
	var panelCoberturas;
	var urlRegresar = '<s:url namespace="/" action="editarAsegurados" />';
	var urlCargarCoberturas = '<s:url namespace="/" action="cargarPantallaCoberturas" />';
	var inputCdunieco = '<s:property value="smap1.pv_cdunieco" />';
	var inputCdramo = '<s:property value="smap1.pv_cdramo" />';
	var inputEstado = '<s:property value="smap1.pv_estado" />';
	var inputNmpoliza = '<s:property value="smap1.pv_nmpoliza" />';
	var inputNmsituac = '<s:property value="smap1.pv_nmsituac" />';
	var inputCdpersona = '<s:property value="smap1.pv_cdperson" />';
	var urlGuardarCoberturas = '<s:url namespace="/" action="guardarCoberturasUsuario" />';
	var panelAdicionales;
	var urlTatri = '<s:url namespace="/" action="obtenerCamposTatrigar" />';
	var urlLoadTatri = '<s:url namespace="/" action="obtenerValoresTatrigar" />';
	var urlSaveTatri = '<s:url namespace="/" action="guardarValoresTatrigar" />';
	/*///////////////////*/
	//////variables //////
	///////////////////////
	Ext
			.onReady(function() {

				/////////////////////
				////// Modelos //////
				/*/////////////////*/
				Ext.define('Modelo1', {
					extend : 'Ext.data.Model',
					fields :
				    [
				        {name : 'GARANTIA'        },
				        {name : 'NOMBRE_GARANTIA' },
				        {name : 'SWOBLIGA'        },
				        {name : 'SUMA_ASEGURADA'  },
				        {name : 'CDCAPITA'        },
				        {name : 'status'          },
				        {name : 'cdtipbca'        },
				        {name : 'ptvalbas'        },
				        {name : 'swmanual'        },
				        {name : 'swreas'          },
				        {name : 'cdagrupa'        },
				        {name : 'ptreduci'        },
				        {
				        	name       : 'fereduci',
				        	type       : 'date',
				        	dateFormat : 'd/m/Y' 
				        },
				        {name : 'swrevalo'}
				    ]
				});

				Ext.define('ModeloAdicionales', {
					extend : 'Ext.data.Model'
				});
				/*/////////////////*/
				////// Modelos //////
				/////////////////////
				////////////////////
				////// Stores //////
				/*////////////////*/
				storeCoberturas = Ext.create('Ext.data.Store', {
					storeId : 'storeCoberturas',
					model : 'Modelo1',
					proxy : {
						type : 'ajax',
						url : urlCargarCoberturas,
						extraParams : {
							'smap1.pv_cdunieco_i' : inputCdunieco,
							'smap1.pv_cdramo_i' : inputCdramo,
							'smap1.pv_estado_i' : inputEstado,
							'smap1.pv_nmpoliza_i' : inputNmpoliza,
							'smap1.pv_nmsituac_i' : inputNmsituac
						},
						reader : {
							type : 'json',
							root : 'slist1'
						}
					},
					autoLoad : true
				});
				/*////////////////*/
				////// Stores //////
				////////////////////
				/////////////////////////
				////// Componentes //////
				/*/////////////////////*/
				panelAdicionales = Ext.create('Ext.form.Panel', {
					frame : true,
					title : 'Datos adicionales',
					collapsible : true,
					titleCollapse : true,
					bodyPadding:5
});
/*/////////////////////*/
////// Componentes //////
/////////////////////////

///////////////////////
////// Contenido //////
/*///////////////////*/
panelCoberturas=Ext.create('Ext.grid.Panel',
{
									title : 'Coberturas',
									collapsible : true,
									titleCollapse : true,
									style : 'margin:5px;',
									store : storeCoberturas,
									renderTo : 'pan_usu_cob_divgrid',
									frame : true,
									columns : [
											{
												header : 'Cobertura',
												dataIndex : 'NOMBRE_GARANTIA',
												flex : 2
											},
											{
												header : 'Suma asegurada',
												dataIndex : 'SUMA_ASEGURADA',
												flex : 1,
												editor : {
													xtype : 'textfield',
													allowBlank : true
}
},
{
												menuDisabled : true,
												dataIndex : 'SWOBLIGA',
												width : 30,
												renderer : function(value) {
													var rvalue = '';
													if (value == 'N') {
														rvalue = '<img src="resources/fam3icons/icons/eye.png" data-qtip="Ver datos adicionales" style="cursor:pointer;">';
													}
													return rvalue;
}
},
{
												menuDisabled : true,
												dataIndex : 'SWOBLIGA',
												width : 30,
												renderer : function(value) {
													var rvalue = '';
													if (value == 'N') {
														rvalue = '<img src="resources/fam3icons/icons/pencil.png" data-qtip="Editar suma asegurada" style="cursor:pointer;">';
													}
													return rvalue;
}
},
{
												menuDisabled : true,
												dataIndex : 'SWOBLIGA',
												width : 30,
												renderer : function(value) {
													var rvalue = '';
													if (value == 'N') {
														rvalue = '<img src="resources/fam3icons/icons/delete.png" data-qtip="Quitar cobertura" style="cursor:pointer;">';
													}
													return rvalue;
												}
											} ],
									selType : 'rowmodel',
									plugins : [ Ext.create(
											'Ext.grid.plugin.RowEditing', {
												clicksToEdit : 1
											}) ],
									width : 500,
									height : 300,
									buttonAlign : 'center',
									listeners:
{
										beforeedit : function(grid, e, eOpts) {
											return e.colIdx == 3
													&& e.record.get('SWOBLIGA') == 'N';
										},
										cellclick : function(grid, td,
												cellIndex, record, tr,
												rowIndex, e, eOpts) {
											if (record.get('SWOBLIGA') == 'N') {
												if (cellIndex == 2)//load tatri
												{
													console
															.log("click view record "
																	+ rowIndex);
													panelAdicionales.destroy();
													Ext.Ajax
															.request({
																url : urlTatri,
																params : {
																	'smap1.pv_cdramo_i' : inputCdramo,
																	'smap1.pv_cdgarant_i' : record
																			.get('GARANTIA')
																},
																success : function(
																		response,
																		opts) {
																	var json = Ext
																			.decode(response.responseText);
																	console
																			.log(json);
																	if (json.success == true) {
																		////// crear modelo con campos que vienen de str1 //////
																		Ext
																				.define(
																						'ModeloAdicionales',
																						{
																							extend : 'Ext.data.Model',
																							fields : Ext
																									.decode(json.str1)
																						});
																		////// !crear modelo con campos que vienen de str1 //////

																		////// crear formuario con campos que vienen de str2 //////
																		panelAdicionales = Ext
																				.create(
																						'Ext.form.Panel',
																						{
																							frame : true,
																							model : 'ModeloAdicionales',
																							title : 'Datos adicionales',
																							collapsible : true,
																							titleCollapse : true,
																							bodyPadding : 5,
																							buttonAlign : 'center',
																							renderTo : 'pan_usu_cob_divadicionales',
																							url : urlSaveTatri,
																							items : Ext
																									.decode(json.str2),
																							buttons : [
																									{
																										text : 'Guardar cambios',
																										icon : 'resources/fam3icons/icons/accept.png',
																										handler : function() {
																											if (panelAdicionales
																													.getForm()
																													.isValid()) {
																												panelAdicionales
																														.getForm()
																														.submit(
																																{
																																	params : {
																																		'smap1.pv_cdunieco' : inputCdunieco,
																																		'smap1.pv_cdramo' : inputCdramo,
																																		'smap1.pv_estado' : inputEstado,
																																		'smap1.pv_nmpoliza' : inputNmpoliza,
																																		'smap1.pv_nmsituac' : inputNmsituac,
																																		'smap1.pv_cdgarant' : record
																																				.get('GARANTIA')
																																	},
																																	success : function(
																																			form,
																																			action) {
																																		if (action.result.success == true) {
																																			Ext.Msg
																																					.show({
																																						title : 'Datos guardados',
																																						msg : 'Los datos han sido guardados',
																																						buttons : Ext.Msg.OK
																																					});
																																		} else {
																																			Ext.Msg
																																					.show({
																																						title : 'Error',
																																						msg : 'Error al guardar',
																																						buttons : Ext.Msg.OK,
																																						icon : Ext.Msg.ERROR
																																					});
																																		}
																																	},
																																	failure : function() {
																																		Ext.Msg
																																				.show({
																																					title : 'Error',
																																					msg : 'Error de comunicaci&oacute;n',
																																					buttons : Ext.Msg.OK,
																																					icon : Ext.Msg.ERROR
																																				});
																																	}
																																});
																											} else {
																												Ext.Msg
																														.show({
																															title : 'Datos incompletos',
																															msg : 'Favor de introducir todos los datos requeridos',
																															buttons : Ext.Msg.OK,
																															icon : Ext.Msg.WARNING
																														});
																											}
																										}
																									},
																									{
																										text : 'Cancelar',
																										icon : 'resources/fam3icons/icons/cancel.png',
																										handler : function() {
																											panelAdicionales
																													.destroy();
																										}
																									} ]
																						});
																		////// !crear formuario con campos que vienen de str2 //////

																		////// cargar formulario con modelo creado //////
																		Ext
																				.define(
																						'LoaderModeloAdicionales',
																						{
																							extend : 'ModeloAdicionales',
																							proxy : {
																								type : 'ajax',
																								url : urlLoadTatri,
																								extraParams : {
																									'smap1.pv_cdunieco_i' : inputCdunieco,
																									'smap1.pv_cdramo_i' : inputCdramo,
																									'smap1.pv_cdestado_i' : inputEstado,
																									'smap1.pv_nmpoliza_i' : inputNmpoliza,
																									'smap1.pv_nmsituac_i' : inputNmsituac,
																									'smap1.pv_cdgarant_i' : record
																											.get('GARANTIA')
																								},
																								reader : {
																									type : 'json'
																								}
																							}
																						});
																		var loaderAdicionales = Ext.ModelManager
																				.getModel('LoaderModeloAdicionales');
																		loaderAdicionales
																				.load(
																						123,
																						{
																							success : function(
																									resp) {
																								panelAdicionales
																										.getForm()
																										.loadRecord(
																												resp);
																							},
																							failure : function() {
																								Ext.Msg
																										.show({
																											title : 'Error',
																											msg : 'Error al cargar',
																											buttons : Ext.Msg.OK,
																											icon : Ext.Msg.ERROR
																										});
																							}
																						});
																		////// !cargar formulario con modelo creado //////
																	} else {
																		Ext.Msg
																				.show({
																					title : 'Error',
																					msg : 'Error al obtener los datos adicionales',
																					buttons : Ext.Msg.OK,
																					icon : Ext.Msg.ERROR
																				});
																	}
																},
																failure : function(
																		response,
																		opts) {
																	//panelCoberturas.setLoading(true);
																	Ext.Msg
																			.show({
																				title : 'Error',
																				msg : 'Error de comunicaci&oacute;n',
																				buttons : Ext.Msg.OK,
																				icon : Ext.Msg.ERROR
																			});
																}
															});
												} else if (cellIndex == 4)//delete
												{
													grid.getStore().removeAt(
															rowIndex);
												}
											}
										}
									},
									buttons:
[
{
												text : 'Regresar',
												icon : 'resources/extjs4/resources/ext-theme-neptune/images/toolbar/scroll-left.png',
												handler : function() {
													Ext
															.create(
																	'Ext.form.Panel')
															.submit(
																	{
																		url : urlRegresar,
																		standardSubmit : true,
																		params : {
																			'map1.cdunieco' : inputCdunieco,
																			'map1.cdramo' : inputCdramo,
																			'map1.estado' : inputEstado,
																			'map1.nmpoliza' : inputNmpoliza
																		}
																	});
}
},
{
												text : 'Guardar cambios',
												icon : 'resources/fam3icons/icons/accept.png',
												handler : function() {
													var jsonList = [];
													storeCoberturas
															.each(function(
																	record,
																	index) {
																jsonList.push({
																GARANTIA        : record.get('GARANTIA'),
																NOMBRE_GARANTIA : record.get('NOMBRE_GARANTIA'),
																SWOBLIGA        : record.get('SWOBLIGA'),
																SUMA_ASEGURADA  : record.get('SUMA_ASEGURADA'),
																CDCAPITA        : record.get('CDCAPITA'),
																status          : record.get('status'),
																cdtipbca        : record.get('cdtipbca'),
																ptvalbas        : record.get('ptvalbas'),
																swmanual        : record.get('swmanual'),
																swreas          : record.get('swreas'),
																cdagrupa        : record.get('cdagrupa'),
																ptreduci        : record.get('ptreduci'),
																fereduci        : typeof record.get('fereduci')=='string'?
																		              record.get('fereduci'):
																		              Ext.Date.format(record.get('fereduci'), 'd/m/Y'),
															    swrevalo        : record.get('swrevalo')
															});
														});
													var post = {};
													post['slist1'] = jsonList;
													var smap1 = {
														pv_cdunieco_i : inputCdunieco,
														pv_cdramo_i : inputCdramo,
														pv_estado_i : inputEstado,
														pv_nmpoliza_i : inputNmpoliza,
														pv_nmsituac_i : inputNmsituac
													};
													post['smap1'] = smap1;
													panelCoberturas.setLoading(true);
													//console.log(Ext.encode(post));
													Ext.Ajax
															.request({
																url : urlGuardarCoberturas,
																jsonData : Ext
																		.encode(post),
																success : function(
																		response,
																		opts) {
																	panelCoberturas.setLoading(false);
																	var json = Ext
																			.decode(response.responseText);
																	if (json.success == true) {
																		Ext.Msg
																				.show({
																					title : 'Datos guardados',
																					msg : 'Se han guardado las coberturas',
																					buttons : Ext.Msg.OK
																				});
																	} else {
																		Ext.Msg
																				.show({
																					title : 'Error',
																					msg : 'Error al guardar las coberturas',
																					buttons : Ext.Msg.OK,
																					icon : Ext.Msg.ERROR
																				});
																	}
																},
																failure : function(
																		response,
																		opts) {
																	panelCoberturas.setLoading(true);
																	Ext.Msg
																			.show({
																				title : 'Error',
																				msg : 'Error de comunicaci&oacute;n',
																				buttons : Ext.Msg.OK,
																				icon : Ext.Msg.ERROR
																			});
																}
															});
												}
											} ]
								});
				/*///////////////////*/
				////// Contenido //////
				///////////////////////
			});
</script>
</head>
<body style="margin:0; padding:0;">
	<table width="100%" height="500">
		<tr>
			<td align="center">
				<table align="center">
					<tr>
						<td><div id="pan_usu_cob_divgrid"></div></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="center">
				<table align="center">
					<tr>
						<td><div id="pan_usu_cob_divadicionales"></div></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>