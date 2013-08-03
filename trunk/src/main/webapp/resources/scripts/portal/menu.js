Ext.onReady(function(){
    Ext.QuickTips.init();

    var onClickCatalogoEstrucutura = function()
    {
        window.location=_CATALOGO_ESTRUCTURAS;
    };

    var onClickMantenimientoPlanes = function()
    {
        window.location=_MANTENIMIENTO_PLANES;
    };

    var onClickMantenimientoPlanesCliente = function()
    {
        window.location=_MANTENIMIENTO_PLANES_CLIENTE;
    };

    var onClickTareasChecklist = function()
    {
        window.location=_TAREAS_CHECKLIST;
    };

    var onClickCheckListCuenta = function()
    {
        window.location=_CHECKLIST_CUENTA;
    };
    
    var onClickConfiguracionCheckListCuenta = function()
    {
        window.location =_CONFIGURA_CHECKLIST_CUENTA;
    };


    var onClickAyudaCoberturas = function()
    {
        window.location=_AYUDA_COBERTURAS;
    };

    var onClickCarritoCompras = function()
    {
        window.location=_CARRITO_COMPRAS;
    };

    var onClickDescuentos = function()
    {
        window.location=_DESCUENTOS;
    };

    var onClickAlertas = function()
    {
        window.location=_ALERTAS;
    };

    var onClickDesglosePolizas = function()
    {
        window.location=_DESGLOSE_POLIZAS;
    };

    var onClickPersonas = function()
    {
        window.location=_PERSONAS;
    };

    var onClickEjecutivoCuenta = function()
    {
        window.location=_EJECUTIVO_CUENTA;
    };

    var onClickAgrupacionPolizas = function()
    {
        window.location=_AGRUPACION_POLIZAS;
    };
    var onClickSecciones = function()
    {
        window.location=_SECCIONES;
    };
    var onClickFormatoOrdenesTrabajo = function()
    {
        window.location=_FORMATO_ORDENES_TRABAJO;
    };
    var onClickNumerosIncisos = function()
    {
        window.location=_NUMEROS_INCISOS;
    };
    var onClickNumerosPoliza = function()
    {
        window.location=_NUMEROS_POLIZAS;
    };
    var onClickMotivosCancelacion = function()
    {
        window.location=_MOTIVOS_CANCELACION;
    };
    var onClickRazonesCancelacionPoducto = function()
    {
        window.location=_RAZONES_CANCELACION_PRODUCTO;
    };
    var onClickMetodosCancelacion = function()
    {
        window.location=_METODOS_CANCELACION;
    };
    var onClickAlertasUsuario = function() {
        window.location=_ALERTAS_USUARIO;
    }
    var onClickPeriodoGracia = function() {
        window.location=_PERIODO_GRACIA;
    }
    var onClickPeriodoGraciaCliente = function() {
        window.location=_PERIODO_GRACIA_CLIENTE;
    }
    var onClickAsociarFormato = function() {
        window.location=_ASOCIAR_FORMATO;
    }
    var onClickRequisitosRehabilitacion = function() {
        window.location=_REQUISITOS_REHABILITACION;
    }
    var onClickMensajesError = function() {
        window.location=_MENSAJES_ERROR;
    }
	var onClickSeleccionPolizasRenovacion = function() {
        window.location=_SELECCION_POLIZAS_RENOVACION;
    }    
    var onClickConfiguracionEndosos = function() {
        window.location=_CONFIGURACION_ENDOSOS;
    }
    var onClickConfigurarNumeracionEndosos = function() {
        window.location=_CONFIGURAR_NUMERACION_ENDOSOS;
    }
	var onClickConfigurarFormaCalculoAtributosVariables = function() {
        window.location=_CONFIGURAR_FORMA_CALCULO_ATRIBUTOS;
    }
    var onClickConsultaConfiguracionRenovacion = function() {
        window.location=_CONSULTA_CONFIGURAR_RENOVACION;
    }
    var onClickTooltips = function () {
    	window.location=_EJEMPLO_TOOLTIPS;
    }   
    var onClickHistorialOrdenesCompra = function() {
        window.location=_HISTORIAL_ORDENES_COMPRAS;
    }
    var onClickMecanismoDeTooltip = function() {
        window.location=_MECANISMO_DE_TOOLTIPS;
    }
    var onClickMantenimientoEjecutivosCuenta = function() {
        window.location=_MANTENIMIENTO_EJECUTIVOS_CUENTA;
    }
    var onClickRehabilitacionMasiva = function () {
    	window.location = _ACTION_REHABILITACION_MASIVA;
    }
    var onClickRehabilitacionManual = function () {
    	window.location = _ACTION_REHABILITACION_MANUAL;
    }
    var onClickValidacionProcesos = function () {
    	window.location = _VALIDACION_PROCESOS;
    }
    var onClickManttoCatLog = function () {
    	window.location = _MANTTO_CATLOG;
    }
    var onClickRelacionPersona = function () {
    	window.location = _RELACION_PERSONA;
    }
    var onClickAtributoAgente = function () {
    	window.location = _ATRIBUTOS_AGENTES;
    }
    var onClickAtributoPersona = function () {
    	window.location = _ATRIBUTOS_PERSONA;
    }
    var onClickCancelacionManual = function () {
    	window.location = _CANCELACION_MANUAL;
    }

    
    var onClickPolizasCancelar = function () {
    	window.location = _POLIZAS_CANCELAR;
    }
       var onClickPolizasCanceladas = function () {
    	window.location = _POLIZAS_CANCELADAS;
    }
       var onClickFuncionalidadesPrivilegios = function () {
    	window.location = _FUNCIONALIDADES_PRIVILEGIOS;
    }
       var onClickCotizacionMasiva = function () {
    	window.location = _COTIZACIONES_MASIVAS;
    }
    
    var menu = new Ext.menu.Menu({
        id: 'mainMenu',
        items: [
		        {
		        	text: 'Primer Entrega',
		        	menu: {
		        		items: [
	        						{
	        							text: 'Cat&aacute;logo de Estructuras',
										checked: false,
	        							checkHandler: onClickCatalogoEstrucutura
	        						},
	        						{
	        							text: 'Mantenimiento de Planes',
										checked: false,
	        							checkHandler: onClickMantenimientoPlanes
	        						},
	        						{
	        							text: 'Configurar Planes Por Cliente',
										checked: false,
	        							checkHandler: onClickMantenimientoPlanesCliente
	        						},
	        						{
	        							text: 'Tareas checklist',
										checked: false,
	        							checkHandler: onClickTareasChecklist
	        						},
	        						{
	        							text: 'Configuraci&oacute;n Checklist Cuenta',
										checked: false,
	        							checkHandler: onClickConfiguracionCheckListCuenta
	        						}/*,
	        						{
	        							text: 'Checklist Cuenta',
										checked: false,
	        							checkHandler: onClickCheckListCuenta
	        						}*/
		        		]
		        	}
		        },
		        {
		        	text: 'Segunda Entrega',
		        	menu: {
		        			items: [
		        					{
							            text: 'Ayuda Coberturas',
										checked: false,
							            checkHandler: onClickAyudaCoberturas
							        },
							        {
							            text: 'Configuraci&oacute;n Alertas',
										checked: false,
							            checkHandler: onClickAlertas
							        },
							        {
							            text: 'Consulta Alertas',
										checked: false,
							            checkHandler: onClickAlertasUsuario
							        },
									{
							            text: 'Carrito Compras',
										checked: false,
							            checkHandler: onClickCarritoCompras
							        },
							        {
							            text: 'Descuentos',
										checked: false,
							            checkHandler: onClickDescuentos
							        },
							        {
							            text: 'Desglose P&oacute;lizas',
										checked: false,
							            checkHandler: onClickDesglosePolizas
							        }
		        			]
		        	}
		        },
        		{
        			text: 'Tercera Entrega',
        			menu: {
        					items: [
							        {
							            text: 'Ejecutivo Cuenta',
										checked: false,
							            checkHandler: onClickEjecutivoCuenta
							        },
							        {
							            text: 'Agrupaci&oacute;n P&oacute;lizas',
										checked: false,
							            checkHandler: onClickAgrupacionPolizas
							        },
							        {
							            text: 'Personas',
										checked: false,
							            checkHandler: onClickPersonas
							        }
        					]
        			}
        		},
        		{
        			text: 'Cuarta Entrega',
        			menu: {
        					items: [
							        {
							            text: 'N&uacute;meros P&oacute;lizas',
										checked: false,
							            checkHandler: onClickNumerosPoliza
							        },
							        {
							            text: 'N&uacute;meros Incisos',
										checked: false,
							            checkHandler: onClickNumerosIncisos
							        },
							        {
							            text: 'Secciones',
										checked: false,
							            checkHandler: onClickSecciones
							        },
							        {
							            text: 'Formatos Ordenes de Trabajo',
										checked: false,
							            checkHandler: onClickFormatoOrdenesTrabajo
							        },
							        {
							            text: 'Motivos Cancelaci&oacute;n',
										checked: false,
							            checkHandler: onClickMotivosCancelacion
							        },
							        {
							            text: 'Metodos de Cancelaci&oacute;n',
										checked: false,
							            checkHandler: onClickMetodosCancelacion
							        }
        					]
        			}
        		},
        		{
        			text: 'Quinta Entrega',
        			menu: {
        					items: [
							        {
							            text: 'Razones Cancelaci&oacute;n Producto',
										checked: false,
							            checkHandler: onClickRazonesCancelacionPoducto
							        },
							        {
							            text: 'Periodo Gracia',
										checked: false,
							            checkHandler: onClickPeriodoGracia
							        },
							        {
							            text: 'Periodo Gracia Cliente',
										checked: false,
							            checkHandler: onClickPeriodoGraciaCliente
							        },
							        {
							            text: 'Asociar Formato',
										checked: false,
							            checkHandler: onClickAsociarFormato
							        },
							        {
							            text: 'Requisitos Rehabilitacion',
										checked: false,
							            checkHandler: onClickRequisitosRehabilitacion
							        },
							        {
							        	text: 'Mensajes Error',
							        	checked: false,
							        	checkHandler: onClickMensajesError
							        }
        					]
        			}
        		},
        		{
        			text: 'Sexta Entrega',
        			menu: {
        					items: [
							        {
							            text: 'Selecci&oacute;n de P&oacute;lizas para Renovaci&oacute;n',
										checked: false,
							            checkHandler: onClickSeleccionPolizasRenovacion
							        },							        
							        {
							            text: 'Configuración de Endosos',
										checked: false,
							            checkHandler: onClickConfiguracionEndosos
							        },							        
							        {
							            text: 'Configurar Numeracion de Endosos',
										checked: false,
							            checkHandler: onClickConfigurarNumeracionEndosos
							        },
							        {
							            text: 'Configurar Forma Calculo Atributos',
										checked: false,
							            checkHandler: onClickConfigurarFormaCalculoAtributosVariables
							        },
							        {
							            text: 'Consulta Configuracion Renovacion',
										checked: false,
							            checkHandler: onClickConsultaConfiguracionRenovacion
							        },
							        {
							            text: 'Historial Ordenes de Compra',
										checked: false,
							            checkHandler: onClickHistorialOrdenesCompra
							        },
							        {
							            text: 'Tooltips',
										checked: false,
							            checkHandler: onClickMecanismoDeTooltip
							        },
							        {
							        	text: 'Rehabilitación Manual',
							        	checked: false,
							        	checkHandler: onClickRehabilitacionManual
							        },
							        {
							        	text: 'Rehabilitación Masiva',
							        	checked: false,
							        	checkHandler: onClickRehabilitacionMasiva
							        }
							        
							        
							        
        					]
        			}
        		}/*,
        		{
        			text: 'Ejemplos',
        			menu: {
        				items: [
        						{
        							text: 'Ejemplo Tooltips',
        							checked: false,
        							checkHandler: onClickTooltips
        						}
        				]
        			}
        		}*/,
        		{
        			text: 'Entrega Adicional',
        			menu: {
        				items: [
        					{
        						text: 'Mantenimiento Ejecutivos',
        						checked: false,
        						checkHandler: onClickMantenimientoEjecutivosCuenta
        					},
        					{
        						text: 'Validaci&oacute;n Procesos',
        						checked: false,
        						checkHandler: onClickValidacionProcesos
        					},
					        {
					        	text: 'Mantenimiento de Cat&aacute;logos L&oacute;gicos',
					        	checked: false,
					        	checkHandler: onClickManttoCatLog
					        },
					        /*{
					        	text: 'Relacion Persona',
					        	checked: false,
					        	checkHandler: onClickRelacionPersona
					        },*/
					        {
					        	text: 'Atributos Variables de Agente',
					        	checked: false,
					        	checkHandler: onClickAtributoAgente
					        },
					        {
					        	text: 'Atributos Variables de Persona',
					        	checked: false,
					        	checkHandler: onClickAtributoPersona
					        },
					        {
					        	text: 'Cancelaci&oacute;n Manual P&oacute;lizas',
					        	checked: false,
					        	checkHandler: onClickCancelacionManual
					        },
					        {
					        	text: 'P&oacute;lizas a Cancelar',
					        	checked: false,
					        	checkHandler: onClickPolizasCancelar
					        },
					        {
					        	text: 'P&oacute;lizas Canceladas',
					        	checked: false,
					        	checkHandler: onClickPolizasCanceladas
					        },
					        {
					        	text: 'Funcionalidades Privilegios',
					        	checked: false,
					        	checkHandler: onClickFuncionalidadesPrivilegios
					        },
					        {
					        	text: 'Cotizaciones Masivas',
					        	checked: false,
					        	checkHandler: onClickCotizacionMasiva
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