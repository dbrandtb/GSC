//MATRIZ DE USUARIOS RESPONABLES --> PKG_CATBO.P_OBTIENE_MCASO_REG

var readerEncCasoDetalle = new Ext.data.JsonReader(
		{
			root:'MEstructuraCasoList',
			totalProperty: 'totalCount',
			successProperty : '@success'
		},
		[
			{name: 'cdRolmat', type: 'string'},
			{name: 'cdproceso', type: 'string'},
			{name: 'dsproceso', type: 'string'},
			{name: 'cdpriord', type: 'string'},
			{name: 'dspriord', type: 'string'},
			{name: 'cdnivatn', type: 'string'},
			{name: 'dsnivatn', type: 'string'},
			{name: 'nmcaso', type: 'string'},
			{name: 'cdusuario', type: 'string'},
			{name: 'cdusuari', type: 'string'},
			{name: 'cdunieco', type: 'string'},
			{name: 'dsunieco', type: 'string'},
			{name: 'porcentaje', type: 'string'},
			{name: 'color', type: 'string'},
			{name: 'cdordentrabajo', type: 'string'},
			{name: 'cdnumerordencia', type: 'string'},
			{name: 'cdmodulo', type: 'string'},
			{name: 'dsmodulo', type: 'string'},
			{name: 'cdstatus', type: 'string'},
			{name: 'tiemporestanteparaatender', type: 'string'},
			{name: 'tiemporestanteparaescalarTex', type: 'string',mapping:'tiemporestanteparaescalar'},
			{name: 'nmvecescompra', type: 'string'},
			{name: 'cdformatoorden', type: 'string'},
			{name: 'nmovimiento', type: 'string'},
			{name: 'dsstatus', type: 'string'},
			{name: 'nmCompra', type: 'string'}
		]
		); 
		
var storeEncCasoDetalle = new Ext.data.Store({
	proxy: new Ext.data.HttpProxy({url: _ACTION_GET_ENC_CASO_DETALLE}),
	reader: readerEncCasoDetalle
}); 

//MATRIZ DE USUARIOS RESPONABLES --> PKG_CATBO.P_OBTENER_MCASOUSR_REG
var storeCasoUsr = new Ext.data.Store({
	proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_USUARIOS_DEL_CASO}),
	reader: new Ext.data.JsonReader(
		{
			root:'MUsuariosResponsablesList',
			totalProperty: 'totalCount',
			successProperty : '@success'
		},
		[
			{name: 'cdUsuario', type: 'string'},
			{name: 'desUsuario', type: 'string'},
			{name: 'cdRolmat', type: 'string'},
			{name: 'desRolmat', type: 'string'},
			{name: 'email', type: 'string'}
		]
		)
});


//STORE PARA BLOQUE DE DATOS DINAMICOS
/*var storeAtributosVariables = new Ext.data.Store({
	proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_ATRIBUTOS_VARIABLES}),
	reader: new Ext.data.JsonReader(
		{
			root:'MListAtributosVariables',
			totalProperty: 'totalCount',
			successProperty : '@success'
		},
		[
			{name: 'cdFormatoOrden', type: 'string'},
			{name: 'dsFomatoOrden', type: 'string'},
			{name: 'cdSeccion', type: 'string'},
			{name: 'dsSeccion', type: 'string'},
			{name: 'cdAtribu', type: 'string'},
			{name: 'dsAtribu', type: 'string'},
			{name: 'cdBloque', type: 'string'},
			{name: 'dsBloque', type: 'string'},
			{name: 'cdcampo', type: 'string'},
			{name: 'dsCampo', type: 'string'},
			{name: 'otTabval', type: 'string'},
			{name: 'swFormat', type: 'string'},
			{name: 'nmLmax', type: 'string'},
			{name: 'nmlmin', type: 'string'},
			{name: 'cdexpres', type: 'string'},
			{name: 'nmorden', type: 'string'}
		]
		)
});*/

//STORE PARA LOS DATOS DEL BLOQUE DE DATOS DINAMICOS
var storeDatosAtributosVariables = new Ext.data.Store({
	proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_DATOS_ATRIBUTOS_VARIABLES}),
	reader: new Ext.data.JsonReader(
		{
			root:'MListaSeccionesOrden',
			totalProperty: 'totalCount',
			successProperty : '@success'
		},
		[
			{name: 'cdformatoorden', type: 'string'},
			{name: 'cdseccion', type: 'string'},
			{name: 'cdatribu', type: 'string'},
			{name: 'cdordentrabajo', type: 'string'},
			{name: 'otvalor', type: 'string'}
		]
		)
});                   