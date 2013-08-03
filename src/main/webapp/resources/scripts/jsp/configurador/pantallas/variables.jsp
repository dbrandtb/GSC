<%@ include file="/taglibs.jsp"%>

var PANEL_1_TITLE = '<s:text name="panel1.title"/>';//

var TAB_1_TITLE = '<s:text name="tab1.title"/>';//
var TAB_1_PROCESO = '<s:text name="tab1.proceso"/>';//
var TAB_1_PROCESO_EMPTY = '<s:text name="tab1.proceso.empty"/>';//
var TAB_1_NIVEL = '<s:text name="tab1.nivel"/>';//
var TAB_1_NIVEL_EMPTY = '<s:text name="tab1.nivel.empty"/>';//
var TAB_1_PRODUCTO = '<s:text name="tab1.producto"/>';//
var TAB_1_PRODUCTO_EMPTY = '<s:text name="tab1.producto.empty"/>';//
var TAB_1_NOMBRE_CONJUNTO = '<s:text name="tab1.nombreconjunto"/>';//
var TAB_1_DESCRIPCION = '<s:text name="tab1.descripcion"/>';//
var TAB_1_BOTON_SALVAR = '<s:text name="tab1.boton.salvar"/>';//
var TAB_1_BOTON_SALVAR_WAIT_MSG = '<s:text name="tab1.boton.salvar.waitmsg"/>';//
var TAB_1_BOTON_SALVAR_FAILURE = '<s:text name="tab1.boton.salvar.failure"/>';//
var TAB_1_BOTON_SALVAR_FAILURE_DESC = '<s:text name="tab1.boton.salvar.failure.desc"/>';//
var TAB_1_BOTON_SALVAR_SUCCESS = '<s:text name="tab1.boton.salvar.success"/>';//
var TAB_1_BOTON_SALVAR_SUCCESS_DESC = '<s:text name="tab1.boton.salvar.success.desc"/>';//
var TAB_1_BOTON_SALVAR_ERROR_VALIDA = '<s:text name="tab1.boton.salvar.errorvalida"/>';//
var TAB_1_BOTON_SALVAR_ERROR_VALIDA_DESC = '<s:text name="tab1.boton.salvar.errorvalida.desc"/>';//
var TAB_1_BOTON_NUEVO_CONJUNTO = '<s:text name="tab1.boton.nuevoconjunto"/>';//
var TAB_1_BOTON_COPIAR_CONJUNTO = '<s:text name="tab1.boton.copiarconjunto"/>';//
var TAB_1_BOTON_COPIAR_CONJUNTO_ERROR = '<s:text name="tab1.boton.copiarconjunto.error"/>';//
var TAB_1_BOTON_COPIAR_CONJUNTO_ERROR_DESC = '<s:text name="tab1.boton.copiarconjunto.error.desc"/>';//

var TAB_2_TITLE='<s:text name="tab2.title"/>';//
var TAB_2_NOMBRE='<s:text name="tab2.nombre"/>';//
var TAB_2_MASTER='<s:text name="tab2.master"/>';//
var TAB_2_MASTER_EMPTY='<s:text name="tab2.master.empty"/>';//
var TAB_2_DESCRIPCION='<s:text name="tab2.descripcion"/>';//
var TAB_2_BOTON_SALVAR='<s:text name="tab2.boton.salvar"/>';//
var TAB_2_BOTON_SALVAR_WAIT_MSG='<s:text name="tab2.boton.salvar.waitmsg"/>';//
var TAB_2_BOTON_SALVAR_FAILURE='<s:text name="tab2.boton.salvar.failure"/>';//
var TAB_2_BOTON_SALVAR_FAILURE_DESC='<s:text name="tab2.boton.salvar.failure.desc"/>';//
var TAB_2_BOTON_SALVAR_SUCCESS='<s:text name="tab2.boton.salvar.success"/>';//
var TAB_2_BOTON_SALVAR_SUCCESS_DESC='<s:text name="tab2.boton.salvar.success.desc"/>';//
var TAB_2_BOTON_SALVAR_ERROR='<s:text name="tab2.boton.salvar.error"/>';//
var TAB_2_BOTON_SALVAR_ERROR_DESC='<s:text name="tab2.boton.salvar.error.desc"/>';//
var TAB_2_BOTON_SALVAR_ERROR_ELEM_REPETIDO='<s:text name="tab2.boton.salvar.error.elem.repetido"/>';//
var TAB_2_BOTON_ELIMINAR='<s:text name="tab2.boton.eliminar"/>';//
var TAB_2_BOTON_ELIMINAR_MSGBOX_TITLE='<s:text name="tab2.boton.eliminar.msgbox.title"/>';//
var TAB_2_BOTON_ELIMINAR_MSGBOX_MSG='<s:text name="tab2.boton.eliminar.msgbox.msg"/>';//
var TAB_2_BOTON_ELIMINAR_MSGBOX_BOTON_OK='<s:text name="tab2.boton.eliminar.msgbox.boton.ok"/>';//
var TAB_2_BOTON_ELIMINAR_MSGBOX_BOTON_OK_WAIT_MSG='<s:text name="tab2.boton.eliminar.msgbox.boton.ok.waitmsg"/>';//
var TAB_2_BOTON_ELIMINAR_MSGBOX_BOTON_OK_FAILURE='<s:text name="tab2.boton.eliminar.msgbox.boton.ok.failure"/>';//
var TAB_2_BOTON_ELIMINAR_MSGBOX_BOTON_OK_FAILURE_DESC='<s:text name="tab2.boton.eliminar.msgbox.boton.ok.failure.desc"/>';//
var TAB_2_BOTON_ELIMINAR_MSGBOX_BOTON_OK_SUCCESS='<s:text name="tab2.boton.eliminar.msgbox.boton.ok.success"/>';//
var TAB_2_BOTON_ELIMINAR_MSGBOX_BOTON_OK_SUCCESS_DESC='<s:text name="tab2.boton.eliminar.msgbox.boton.ok.success.desc"/>';//
var TAB_2_BOTON_ELIMINAR_MSGBOX_BOTON_OK_ERROR='<s:text name="tab2.boton.eliminar.msgbox.boton.ok.error"/>';//
var TAB_2_BOTON_ELIMINAR_MSGBOX_BOTON_OK_ERROR_DESC='<s:text name="tab2.boton.eliminar.msgbox.boton.ok.error.desc"/>';//
var TAB_2_BOTON_ELIMINAR_MSGBOX_BOTON_CANCEL='<s:text name="tab2.boton.eliminar.msgbox.boton.cancel"/>';//
var TAB_2_BOTON_NUEVA='<s:text name="tab2.boton.nueva"/>';//
var TAB_2_BOTON_VISTA='<s:text name="tab2.boton.vista"/>';//
var TAB_2_BOTON_VISTA_WAIT_MSG='<s:text name="tab2.boton.vista.waitmsg"/>';//
var TAB_2_BOTON_DESHACER='<s:text name="tab2.boton.deshacer"/>';//
var TAB_2_BOTON_DESHACER_TOOLTIP='<s:text name="tab2.boton.deshacer.tooltip"/>';//

var TAB_3_TITLE='<s:text name="tab3.title"/>';//
var TAB_3_ACCION='<s:text name="tab3.accion"/>';//
var TAB_3_ACCION_EMPTY='<s:text name="tab3.accion.empty"/>';//
var TAB_3_ACCION_OPCION_1='<s:text name="tab3.accion.opcion1"/>';//
var TAB_3_ACCION_OPCION_2='<s:text name="tab3.accion.opcion2"/>';//
var TAB_3_BOTON_ACEPTAR='<s:text name="tab3.boton.aceptar"/>';//
var TAB_3_BOTON_ACEPTAR_MSGBOX_TITLE='<s:text name="tab3.boton.aceptar.msgbox.title"/>';//
var TAB_3_BOTON_ACEPTAR_MSGBOX_MSG='<s:text name="tab3.boton.aceptar.msgbox.msg"/>';//
var TAB_3_BOTON_ACEPTAR_MSGBOX_BOTON_OK='<s:text name="tab3.boton.aceptar.msgbox.boton.ok"/>';//
var TAB_3_BOTON_ACEPTAR_MSGBOX_BOTON_OK_WAIT_MSG='<s:text name="tab3.boton.aceptar.msgbox.boton.ok.waitmsg"/>';//
var TAB_3_BOTON_ACEPTAR_MSGBOX_BOTON_OK_FAILURE='<s:text name="tab3.boton.aceptar.msgbox.boton.ok.failure"/>';//
var TAB_3_BOTON_ACEPTAR_MSGBOX_BOTON_OK_FAILURE_DESC='<s:text name="tab3.boton.aceptar.msgbox.boton.ok.failure.desc"/>';//
var TAB_3_BOTON_ACEPTAR_MSGBOX_BOTON_OK_SUCCESS='<s:text name="tab3.boton.aceptar.msgbox.boton.ok.success"/>';//
var TAB_3_BOTON_ACEPTAR_MSGBOX_BOTON_OK_SUCCESS_DESC='<s:text name="tab3.boton.aceptar.msgbox.boton.ok.success.desc"/>';//
var TAB_3_BOTON_ACEPTAR_MSGBOX_BOTON_OK_ERROR='<s:text name="tab3.boton.aceptar.msgbox.boton.ok.error"/>';//
var TAB_3_BOTON_ACEPTAR_MSGBOX_BOTON_OK_ERROR_DESC='<s:text name="tab3.boton.aceptar.msgbox.boton.ok.error.desc"/>';//
var TAB_3_BOTON_ACEPTAR_MSGBOX_BOTON_CANCEL='<s:text name="tab3.boton.aceptar.msgbox.boton.cancel"/>';//

var MENU_TITLE='<s:text name="menu.title"/>';//

var MENU_ARBOL_TITLE='<s:text name="menu.arbol.title"/>';//
var MENU_ARBOL_EXPANDIR='<s:text name="menu.arbol.expandir"/>';//
var MENU_ARBOL_EXPANDIR_TOOLTIP='<s:text name="menu.arbol.expandir.tooltip"/>';//
var MENU_ARBOL_CONTRAER='<s:text name="menu.arbol.contraer"/>';//
var MENU_ARBOL_CONTRAER_TOOLTIP='<s:text name="menu.arbol.contraer.tooltip"/>';//
var MENU_ARBOL_RAIZ='<s:text name="menu.arbol.raiz"/>';//
//etc

var MENU_PARAMETROS_TITLE='<s:text name="menu.parametros.title"/>';//
var MENU_PARAMETROS_NOMBRE='<s:text name="menu.parametros.nombre"/>';
var MENU_PARAMETROS_VALOR='<s:text name="menu.parametros.valor"/>';

var MENU_COMUNES_TITLE='<s:text name="menu.comunes.title"/>';//
var MENU_COMUNES_CATEGORIAS='<s:text name="menu.comunes.categorias"/>';//
var MENU_COMUNES_TODAS='<s:text name="menu.comunes.todas"/>';//
var MENU_COMUNES_ELEMCOMUNES='<s:text name="menu.comunes.elemcomunes"/>';//
var MENU_COMUNES_CONTROL_ETIQUETA='<s:text name="menu.comunes.control.etiqueta"/>';//
var MENU_COMUNES_CONTROL_ETIQUETA_TOOLTIP='<s:text name="menu.comunes.control.etiqueta.tooltip"/>';//
var MENU_COMUNES_CONTROL_ETIQUETA_WINDOW_TITLE='<s:text name="menu.comunes.control.etiqueta.window.title"/>';//
var MENU_COMUNES_CONTROL_ETIQUETA_WINDOW_BOTON_OK='<s:text name="menu.comunes.control.etiqueta.window.boton.ok"/>';//
var MENU_COMUNES_CONTROL_ETIQUETA_WINDOW_BOTON_CANCEL='<s:text name="menu.comunes.control.etiqueta.window.boton.cancel"/>';//
var MENU_COMUNES_CONTROL_IMAGEN='<s:text name="menu.comunes.control.imagen"/>';//
var MENU_COMUNES_CONTROL_IMAGEN_WINDOW_TITLE='<s:text name="menu.comunes.control.imagen.window.title"/>';//
var MENU_COMUNES_CONTROL_IMAGEN_WINDOW_BOTON_OK='<s:text name="menu.comunes.control.imagen.window.boton.ok"/>';//
var MENU_COMUNES_CONTROL_IMAGEN_WINDOW_BOTON_OK_ALERT='<s:text name="menu.comunes.control.imagen.window.boton.ok.alert"/>';//
var MENU_COMUNES_CONTROL_IMAGEN_WINDOW_BOTON_OK_ALERT_DESC='<s:text name="menu.comunes.control.imagen.window.boton.ok.alert.desc"/>';//
var MENU_COMUNES_CONTROL_IMAGEN_WINDOW_BOTON_CANCEL='<s:text name="menu.comunes.control.imagen.window.boton.cancel"/>';//
var MENU_COMUNES_CONTROL_IMAGEN_TOOLTIP='<s:text name="menu.comunes.control.imagen.tooltip"/>';//

var MENU_LAYOUTS_TITLE='<s:text name="menu.layouts.title"/>';//
var MENU_LAYOUTS_CATEGORIAS='<s:text name="menu.layouts.categorias"/>';//
var MENU_LAYOUTS_TODAS='<s:text name="menu.layouts.todas"/>';//
var MENU_LAYOUTS_LAYOUTS='<s:text name="menu.layouts.layouts"/>';//
var MENU_LAYOUTS_PANELS='<s:text name="menu.layouts.panels"/>';//
var MENU_LAYOUTS_CONTROL_FORMPANEL='<s:text name="menu.layouts.control.formpanel"/>';//
var MENU_LAYOUTS_CONTROL_FORMPANEL_TITLE='<s:text name="menu.layouts.control.formpanel.title"/>';//
var MENU_LAYOUTS_CONTROL_FORMPANEL_TOOLTIP='<s:text name="menu.layouts.control.formpanel.tooltip"/>';//
var MENU_LAYOUTS_CONTROL_TABPANEL='<s:text name="menu.layouts.control.tabpanel"/>';//
var MENU_LAYOUTS_CONTROL_TABPANEL_TOOLTIP='<s:text name="menu.layouts.control.tabpanel.tooltip"/>';//
var MENU_LAYOUTS_CONTROL_TABPANEL_WINDOW_TITLE='<s:text name="menu.layouts.control.tabpanel.window.title"/>';//
var MENU_LAYOUTS_CONTROL_TABPANEL_WINDOW_FIELDSET_TITLE='<s:text name="menu.layouts.control.tabpanel.window.fieldset.title"/>';//
var MENU_LAYOUTS_CONTROL_TABPANEL_WINDOW_FIELDSET_TITLE_1='<s:text name="menu.layouts.control.tabpanel.window.fieldset.title1"/>';//
var MENU_LAYOUTS_CONTROL_TABPANEL_WINDOW_FIELDSET_TITLE_2='<s:text name="menu.layouts.control.tabpanel.window.fieldset.title2"/>';//
var MENU_LAYOUTS_CONTROL_TABPANEL_WINDOW_RADIO_TITLE='<s:text name="menu.layouts.control.tabpanel.window.radio.title"/>';//
var MENU_LAYOUTS_CONTROL_TABLE='<s:text name="menu.layouts.control.table"/>';//
var MENU_LAYOUTS_CONTROL_TABLE_TOOLTIP='<s:text name="menu.layouts.control.table.tooltip"/>';//
var MENU_LAYOUTS_CONTROL_TABLE_WINDOW_TITLE='<s:text name="menu.layouts.control.table.window.title"/>';//
var MENU_LAYOUTS_CONTROL_TABLE_WINDOW_DIMENSION='<s:text name="menu.layouts.control.table.window.dimension"/>';//
var MENU_LAYOUTS_CONTROL_TABLE_WINDOW_RELLENO='<s:text name="menu.layouts.control.table.window.relleno"/>';//
var MENU_LAYOUTS_CONTROL_TABLE_WINDOW_MARGEN='<s:text name="menu.layouts.control.table.window.margen"/>';//
var MENU_LAYOUTS_CONTROL_TABLE_WINDOW_BORDES='<s:text name="menu.layouts.control.table.window.bordes"/>';//
var MENU_LAYOUTS_CONTROL_TABLE_WINDOW_CONTENIDO='<s:text name="menu.layouts.control.table.window.contenido"/>';//
var MENU_LAYOUTS_CONTROL_TABLE_WINDOW_BOTON_OK='<s:text name="menu.layouts.control.table.window.boton.ok"/>';//
var MENU_LAYOUTS_CONTROL_TABLE_WINDOW_BOTON_OK_ERROR='<s:text name="menu.layouts.control.table.window.boton.ok.error"/>';//
var MENU_LAYOUTS_CONTROL_TABLE_WINDOW_BOTON_OK_ERROR_DESC='<s:text name="menu.layouts.control.table.window.boton.ok.error.desc"/>';//
var MENU_LAYOUTS_CONTROL_TABLE_WINDOW_BOTON_CANCELAR='<s:text name="menu.layouts.control.table.window.boton.cancelar"/>';//
//etc

var MENU_PROCESO_TITLE='<s:text name="menu.proceso.title"/>';//
var MENU_PROCESO_CATEGORIAS='<s:text name="menu.proceso.categorias"/>';//
var MENU_PROCESO_TODAS='<s:text name="menu.proceso.todas"/>';//
var MENU_PROCESO_COTIZACION='<s:text name="menu.proceso.cotizacion"/>';//
var MENU_PROCESO_CONTROL_COTIZA_AUTOS='<s:text name="menu.proceso.control.cotizaautos"/>';//
var MENU_PROCESO_CONTROL_COTIZA_AUTOS_TOOLTIP='<s:text name="menu.proceso.control.cotizaautos.tooltip"/>';//
//etc

var MENU_NAVEGACION_TITLE='<s:text name="menu.navegacion.title"/>';//
var MENU_NAVEGACION_ROOT='<s:text name="menu.navegacion.root"/>';//

var WINDOW_1_VISTA_PREVIA_TITLE='<s:text name="window1.vista.previa.title"/>';//
var WINDOW_1_VISTA_PREVIA_BOTON_CERRAR='<s:text name="window1.vista.previa.boton.cerrar"/>';//

//errores de formulario
