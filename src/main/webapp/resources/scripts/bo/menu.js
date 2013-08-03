Ext.onReady(function(){
    Ext.QuickTips.init();

    var onClickDoLogin = function()
    {
        window.location=_DO_LOGIN;
    };

    var onClickLoginAceptado = function()
    {
        window.location=_LOGIN_ACEPTADO;
    };
	
	var onClickAccesoEstructuraRolUsuario = function()
    {
        window.location=_ACCESO_ESTRUCTURA_ROL_USUARIO;
    };
    
    var onClickNotificaciones = function()
    {
        window.location=_NOTIFICACIONES;
    };

    var onClickFormatoDocumentos = function()
    {
        window.location=_FORMATO_DE_DOCUMENTOS;
    };

    var onClickCheckListCuenta = function()
    {
        window.location=_OPERACIONES_CAT;
    };
    
    var onClickConfiguracionCheckListCuenta = function()
    {
        window.location =_CAT_SCRIPT_ATENCION;
    };


    var onClickAyudaCoberturas = function()
    {
        window.location=_TEST_ITEM_SELECTOR;
    };

    var onClickMatrizAsignacion = function()
    {
        window.location=_MATRIZ_ASIGNACION;
    };

    var onClickDescuentos = function()
    {
        window.location=_CONFIGURAR_MATRIZ_TAREA;
    };

    var onClickAlertas = function()
    {
        window.location=_CONFIGURAR_MATRIZ_TAREA_EDITAR;
    };

    var onClickNumeracionCasos = function()
    {
        window.location=_NUMERACION_CASOS;
    };

    var onClickPersonas = function()
    {
        window.location=_MATRIZ_ASIGNACION_REASIGNAR_CASO;
    };

    var onClickConsultaCasos = function()
    {
        window.location=_CONSULTAR_CASO;
    };

    var onClickStatusCaso = function()
    {
        window.location=_STATUS_CASO;
    };
    var onClickSecciones = function()
    {
        window.location=_ALTA_CASO;
    };
    var onClickTareasCatBO = function()
    {
        window.location=_TAREAS_CATBO;
    };
    var onClickNumerosIncisos = function()
    {
        window.location=_CONSULTAR_CASO_SOLICITUD;
    };
    var onClickConsultaCliente = function()
    {
        window.location=_CONSULTAR_CLIENTE;
    };
    var onClickMotivosCancelacion = function()
    {
        window.location=_CONSULTAR_CASO_DETALLE;
    };
    var onClickMovimientosCaso = function()
    {
        window.location=_CONSULTAR_MOVIMIENTOS_CASO;
    };
    var onClickOrdenDeSolicitud = function()
    {
        window.location=_ORDEN_SOLICITUD;
    };
    var onClickDoLogin = function()
    {
        window.location=_DO_LOGIN;
    };
    var onClickConfiguracionEncuestas = function()
    {
        window.location=_CONFIGURACION_ENCUESTAS;
    };
    var onClickAsignacionEncuestas = function()
    {
        window.location=_ASIGNACION_ENCUESTAS;
    };
    var onClickConsultarEncuestas = function()
    {
        window.location=_CONSULTAR_ENCUESTAS;
    };
    var onClickConfigurarCompraTiempo = function()
    {
        window.location=_CONFIGURAR_COMPRA_TIEMPO;
    };

    var menu = new Ext.menu.Menu({
        id: 'mainMenu',
        items: [
		        {
		        	text: 'Primer Entrega',
		        	menu: {
		        		items: [
		        					{
	        							text: 'Login',
										checked: false,
	        							checkHandler: onClickDoLogin
	        						},
	        						{
	        							text: 'Acceso a la Estructura por Rol - Usuario',
										checked: false,
	        							checkHandler: onClickAccesoEstructuraRolUsuario
	        						},
	        						{
	        							text: 'Notificaciones',
										checked: false,
	        							checkHandler: onClickNotificaciones
	        						},
	        						{
	        							text: 'Formato de Documentos',
										checked: false,
	        							checkHandler: onClickFormatoDocumentos
	        						},
	        						{
	        							text: 'Matriz de Asignaci&oacute;n',
										checked: false,
	        							checkHandler: onClickMatrizAsignacion
	        						},
	        						{
	        							text: 'Numeraci&oacute;n de Casos',
										checked: false,
	        							checkHandler: onClickNumeracionCasos
	        						},
	        						{
	        							text: 'Consulta de Cliente',
										checked: false,
	        							checkHandler: onClickConsultaCliente
	        						},
	        						{
	        							text: 'Consulta de Casos',
										checked: false,
	        							checkHandler: onClickConsultaCasos
	        						},
	        						{
	        							text: 'Estatus de Caso',
										checked: false,
	        							checkHandler: onClickStatusCaso
	        						},
	        						{
	        							text: 'Tareas CatBO',
										checked: false,
	        							checkHandler: onClickTareasCatBO
	        						},
	        						{
	        							text: 'Orden de Solicitud',
										checked: false,
	        							checkHandler: onClickOrdenDeSolicitud
	        						},
	        						{
	        							text: 'Encuestas',
	        							menu: {
		        							items: [
		        								{
			        								text: 'Configuraci&oacute;n Encuestas',
			        								checked: false,
			        								checkHandler: onClickConfiguracionEncuestas
		        								},
		        								{
		        									text: 'Asignaci&oacute;n Encuestas',
		        									checked: false,
		        									checkHandler: onClickAsignacionEncuestas
		        								},
		        								{
		        									text: 'Consultar Encuestas',
		        									checked: false,
		        									checkHandler: onClickConsultarEncuestas
		        								},
		        								{
		        									text: 'Configurar Compra de Tiempo',
		        									checked: false,
		        									checkHandler: onClickConfigurarCompraTiempo
		        								}
		        								
		        							]
	        							}
	        						}
		        		]
		        	}
		       }
        ]
    });







    var tb = new Ext.Toolbar();
    tb.render('toolbar');

    tb.add({
            text:'Pantallas',
            iconCls: 'bmenu',  // <-- icon
            menu: menu  // assign menu by instance
        }

    );



});