<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>

<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Personas</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script> 
<script type="text/javascript" src="${ctx}/resources/scripts/util/ManagedIFrame.js"></script> 
<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_PERSONAS = "<s:url action='buscarPersonas' namespace='/personas'/>";
    var _ACTION_GET_PERSONA = "<s:url action='obtenerPersona' namespace='/personas'/>";

 	var _ACTION_GUARDAR_DATOS_GENERALES = "<s:url action='guardarDatosGenerales' namespace='/personas'/>";
 	var _ACTION_GUARDAR_DATOS_DOMICILIO = "<s:url action='guardarDatosDomicilio' namespace='/personas'/>";
 	var _ACTION_GUARDAR_TELEFONOS = "<s:url action='guardarTelefonos' namespace='/personas'/>";
 	var _ACTION_BORRAR_DOMICILIO = "<s:url action='borrarDomicilio' namespace='/personas'/>";
 	var _ACTION_BORRAR_TELEFONO = "<s:url action='borrarTelefono' namespace='/personas'/>";
 	var _ACTION_LEER_DATOS_ADICIONALES = "<s:url action='obtenerDatosAdicionales' namespace='/personas'/>";

 	var _ACTION_OBTENER_DOMICILIOS = "<s:url action='obtenerDomicilio' namespace='/personas'/>";
 	var _ACTION_OBTENER_TELEFONOS = "<s:url action='obtenerTelefonos' namespace='/personas'/>";
 	var _ACTION_OBTENER_CORPORATIVO = "<s:url action='obtenerCorporativo' namespace='/personas'/>";
	var _ACTION_GUARDAR_DATOS_CORPORATIVO = "<s:url action='guardarCorporativo' namespace='/personas'/>";
	var _ACTION_REGRESAR = "<s:url action='personas' namespace='/'/>";
	var _ACTION_REGRESAR_CLIENTE = "<s:url action='consultaDeCliente' namespace='/'/>";
 	var _ACTION_OBTENER_DATOS_ADICIONALES = "<s:url action='obtenerDatosAdicionales' namespace='/personas'/>";

	var _ACTION_OBTENER_RELACIONES = "<s:url action='obtenerRelaciones' namespace='/personas'/>";
	var _ACTION_GUARDAR_DATOS_RELACIONES = "<s:url action='guardarDatosRelaciones' namespace='/personas'/>";

	var _ACTION_OBTENER_USUARIOS_PERSONAS = "<s:url action='obtenerUsuariosPersonas' namespace='/personas'/>";
	var _ACTION_GUARDAR_DATOS_USUARIOS_PERSONAS = "<s:url action='guardarDatosUsuariosPersonas' namespace='/personas'/>";
	
	var _ACTION_BUSCAR_USUARIOS_SIN_PERSONAS = "<s:url action='buscarUsuariosSinPersonas' namespace='/personas'/>";
	var _ACTION_GUARDAR_USUARIOS_SIN_PERSONAS = "<s:url action='guardarUsuariosSinPersonas' namespace='/personas'/>";
	
	var _ACTION_GUARDAR_CREAR_USUARIOS = "<s:url action='guardarCrearUsuariosPersonas' namespace='/personas'/>";

// Combos url de los combos

    var _ACTION_OBTENER_TIPO_PERSONAS_J = "<s:url action='comboTipoPersonaJ' namespace='/combos'/>";
    var _ACTION_OBTENER_NACIONALIDAD = "<s:url action='comboNacionalidad' namespace='/combos'/>";
    var _ACTION_OBTENER_TIPO_EMPRESA = "<s:url action='comboTipoEmpresa' namespace='/combos'/>";
    var _ACTION_OBTENER_TIPO_IDENTIFICADOR = "<s:url action='comboTipoIdentificador' namespace='/combos'/>";
	var _ACTION_OBTENER_TIPO_DOMICILIO = "<s:url action='comboTipoDomicilio' namespace='/combos'/>";
	var _ACTION_OBTENER_PAISES = "<s:url action='comboPaises' namespace='/combos'/>";
	var _ACTION_OBTENER_ESTADOS = "<s:url action='comboEstadosPais' namespace='/combos'/>";
	var _ACTION_OBTENER_TIPO_TELEFONOS = "<s:url action='comboTiposTelefono' namespace='/combos'/>";
	var _ACTION_OBTENER_MUNICIPIOS = "<s:url action='comboMunicipios' namespace='/combos'/>";
	var _ACTION_OBTENER_COLONIAS = "<s:url action='comboColonias' namespace='/combos'/>";
	var _ACTION_OBTENER_GRUPO_CORPORATIVO = "<s:url action='comboGruposCorporativos' namespace='/combos'/>";
	var _ACTION_OBTENER_ESTADO_CORPORATIVO = "<s:url action='comboEstadosCorporativos' namespace='/combos'/>";
	var _ACTION_COMBO_GENERICO = "<s:url action='obtenerComboGenerico' namespace='/combos'/>";
	var _ACTION_COMBO_CODPOS= "<s:url action='comboCodigoPostal' namespace='/combos'/>";

	var _ACTION_CLIENTES_CORP = "<s:url action='obtenerClientesCorp' namespace='/combos'/>";
	
	//por si se usa en otro lado creo una nueva var para combo persona relacionada en pestaña relaciones 
	var _ACTION_COMBO_PERSONA_RELACIONADA = "<s:url action='comboPersonas' namespace='/combos'/>";
	
	var _ACTION_SEXO = "<s:url action='comboSexo' namespace='/combos'/>";
	var _ACTION_ESTADO_CIVIL = "<s:url action='comboEstadoCivil' namespace='/combos'/>";
	var _COMBO_ROLES = "<s:url action='obtenerComboRoles' namespace='/combos'/>";
	var _ACTION_COMBO_RELACIONES = "<s:url action='obtenerCatalogo' namespace='/combos'/>";

	var CODIGO_PERSONA = "<s:property value='codigoPersona'/>";
	var TIPO_PERSONA = "<s:property value='codigoTipoPersona'/>";
	
	var PERSONA_MORAL= (TIPO_PERSONA == 'M')?true:false;

 	var _ACTION_GUARDAR_DATOS_ADICIONALES = "<s:url action='guardarDatosAdicionales' namespace='/personas'/>";

  	var COD = "<s:property value='cod'/>";
   
    // var itemsPerPage=10;
    
    var itemsPerPage= _NUMROWS;
        var helpMap= new Map();
    <%=session.getAttribute("helpMap")%>
    
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("513")%>
    
     _URL_AYUDA = "/catweb/personas.html";
    
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/MetaForm.js"></script>
<script type="text/javascript">

var helpMap = new Map();
Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";


var agregarNuevoDomicilio = false;
var banderaAgregarDomicilio = false;

var agregarNuevoTelefono = false;
var banderaAgregarTelefono = false;

var agregarNuevoCorporativo = false;
var banderaAgregarCorporativo = false;

var agregarNuevaPersona = false;

var agregarNuevaRelacion = false;
var banderaAgregarRelacion = false;

var agregarNuevoUsuario = false;
var banderaAgregarUsuario = false;

/*************** Definición de Stores y sus correspondientes Readers ********************/
        var readerTipoPersona = new Ext.data.JsonReader( {
            root : 'personasJ',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'codigo',
            mapping : 'codigo',
            type : 'string'
        }, {
            name : 'descripcion',
            type : 'string',
            mapping : 'descripcion'
        }]);

       var dsTipoPersonaJ = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_TIPO_PERSONAS_J
            }),
            reader: readerTipoPersona
        });
      
        var readerNacionalidad = new Ext.data.JsonReader( {
            root : 'comboNacionalidad',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'codigo',
            mapping : 'codigo',
            type : 'string'
        }, {
            name : 'descripcion',
            type : 'string',
            mapping : 'descripcion'
        }]);

       var dsNacionalidad = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_NACIONALIDAD
            }),
            reader: readerNacionalidad
        });

        var readerTipoEmpresa = new Ext.data.JsonReader( {
            root : 'comboTipoEmpresa',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'codigo',
            mapping : 'codigo',
            type : 'string'
        }, {
            name : 'descripcion',
            type : 'string',
            mapping : 'descripcion'
        }]);

       var dsTipoEmpresa = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_TIPO_EMPRESA
            }),
            reader: readerTipoEmpresa,
            baseParams: {tipoPersona: CODIGO_PERSONA}
        });

        var readerTipoIdentificador = new Ext.data.JsonReader( {
            root : 'comboTipoIdentificador',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'codigo',
            mapping : 'codigo',
            type : 'string'
        }, {
            name : 'descripcion',
            type : 'string',
            mapping : 'descripcion'
        }]);

       var dsTipoIdentificador = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_TIPO_IDENTIFICADOR
            }),
            reader: readerTipoIdentificador,
            baseParams: {tipoPersona: CODIGO_PERSONA}
        });
        
        var readerPersona = new Ext.data.JsonReader( {
            root : 'personaVO',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'codigoPersona',
            mapping : 'codigoPersona',
            type : 'string'
        }/*, {
            name : 'tipoPersonaJ',
            type : 'string',
            mapping : 'otFisJur'
        }*/, {
            name : 'nombre',
            type : 'string',
            mapping : 'dsNombre'
        }, {
            name : 'apellidoPaterno',
            type : 'string',
            mapping : 'dsApellido'
        }, {
            name : 'apellidoMaterno',
            type : 'string',
            mapping : 'dsApellido1'
        }, {
            name : 'sexo',
            type : 'string',
            mapping : 'otSexo'
        }, {
            name : 'estadoCivil',
            type : 'string',
            mapping : 'cdEstCiv'
        }, {
            name : 'fechaNacimiento',
            type : 'string',
            mapping : 'feNacimi'/*,
            dateFormat: 'Y-m-d H:i:s.u'*/
        }, {
            name : 'nacionalidad',
            type : 'string',
            mapping : 'cdNacion'
        }, /*{
        	name: 'fechaIngreso',
        	type: 'string',
        	mapping: 'feIngreso'
        },*/ {
        	name: 'tipoIdentificador',
        	type: 'string',
        	mapping: 'cdTipIde'
        }, {
        	name: 'nroIdentificador',
        	type: 'string',
        	mapping: 'cdIdePer'
        }, {
        	name: 'tipoEntidad',
        	type: 'string',
        	mapping: 'cdTipPer'
        }, {
        	name: 'curpId',
        	type: 'string',
        	mapping: 'curp'
        }, {
        	name: 'rfcId',
        	type: 'string',
        	mapping: 'cdRfc'
        }, {
        	name: 'emailId',
        	type: 'string',
        	mapping: 'dsEmail'
        }
        ]);
        var dsPersona = new Ext.data.Store ({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_GET_PERSONA
            }),
            reader: readerPersona
        });

        var readerTipoDomicilio = new Ext.data.JsonReader( {
            root : 'comboTiposDomicilio',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'codigo',
            mapping : 'codigo',
            type : 'int'
        }, {
            name : 'descripcion',
            type : 'string',
            mapping : 'descripcion'
        }]);

       var dsTipoDomicilio = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_TIPO_DOMICILIO
            }),
            reader: readerTipoDomicilio
        });

        var readerPaises = new Ext.data.JsonReader( {
            root : 'comboPaises',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'codigo',
            mapping : 'codigo',
            type : 'string'
        }, {
            name : 'descripcion',
            type : 'string',
            mapping : 'descripcion'
        }]);
		var dsPaises = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_PAISES
            }),
            reader: readerPaises
        }); 
        
        var readerCodigosPostales = new Ext.data.JsonReader( {
            root : 'comboCodigoPostal',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'cdPostal',
            mapping : 'value',
            type : 'string'
        }, {
            name : 'dsPostal',
            type : 'string',
            mapping : 'label'
        }]);
		var dataStoreCodPos = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_COMBO_CODPOS
            }),
            reader: readerCodigosPostales
        }); 

        var readerEstados = new Ext.data.JsonReader( {
            root : 'comboEstados',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'codigo',
            mapping : 'codigo',
            type : 'string'
        }, {
            name : 'descripcion',
            type : 'string',
            mapping : 'descripcion'
        }]);
		var dsEstados = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_ESTADOS
            }),
            reader: readerEstados
        }); 

        var readerTipoTelefono = new Ext.data.JsonReader( {
            root : 'comboTipoTelefono',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'codigo',
            mapping : 'codigo',
            type : 'string'
        }, {
            name : 'descripcion',
            type : 'string',
            mapping : 'descripcion'
        }]);
		var dsTipoTelefono = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_TIPO_TELEFONOS
            }),
            reader: readerTipoTelefono
        }); 

        var readerMunicipios = new Ext.data.JsonReader( {
            root : 'comboMunicipios',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'codigo',
            mapping : 'codigo',
            type : 'int'
        }, {
            name : 'descripcion',
            type : 'string',
            mapping : 'descripcion'
        }]);
		var dsMunicipios = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_MUNICIPIOS
            }),
            reader: readerMunicipios
        }); 

        var readerColonias = new Ext.data.JsonReader( {
            root : 'comboColonias',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'codigo',
            mapping : 'codigo',
            type : 'string'
        }, {
            name : 'descripcion',
            type : 'string',
            mapping : 'descripcion'
        }]);
		var dsColonias = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
            url: _ACTION_OBTENER_COLONIAS
            }),
            reader: readerColonias
        });

        var readerGrupoCorporativo = new Ext.data.JsonReader( {
            root : 'comboListaGrupoCorporativo',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'id',
            mapping : 'id',
            type : 'string'
        }, {
            name : 'texto',
            type : 'string',
            mapping : 'texto'
        }]);
		var dsGrupoCorporativo = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_GRUPO_CORPORATIVO
            }),
            reader: readerGrupoCorporativo
        });

        var readerEstadoCorporativo = new Ext.data.JsonReader( {
            root : 'comboListaEstadoCorporativo',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'codigo',
            mapping : 'codigo',
            type : 'string'
        }, {
            name : 'descripcion',
            type : 'string',
            mapping : 'descripcion'
        }]);
		var dsEstadoCorporativo = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_ESTADO_CORPORATIVO
            }),
            reader: readerEstadoCorporativo
        });



        var readerClientesCorporativos = new Ext.data.JsonReader( {
            root : 'clientesCorp',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'cdElemento',
            mapping : 'cdElemento',
            type : 'string'
        }, {
            name : 'dsElemen',
            type : 'string',
            mapping : 'dsElemen'
        }]);
        
         var readerClientesCorporativosRelaciones = new Ext.data.JsonReader( {
            root : 'comboPersonas',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'cdPerson',
            mapping : 'cdPerson',
            type : 'string'
        }, {
            name : 'cdPerRel',
            type : 'string',
            mapping : 'dsNombre1'
        }]);
        
        var dsClientesCorporativos = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_CLIENTES_CORP
                
            }),
            reader: readerClientesCorporativos
            
        });
        
        
		var dsClientesCorporativosRelaciones = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                   url: _ACTION_COMBO_PERSONA_RELACIONADA
            }),
            reader: readerClientesCorporativosRelaciones
        });

        var readerSexo = new Ext.data.JsonReader( {
            root : 'comboSexo',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'codigo',
            mapping : 'codigo',
            type : 'string'
        }, {
            name : 'descripcion',
            type : 'string',
            mapping : 'descripcion'
        }]);
		var dsSexo = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_SEXO
            }),
            reader: readerSexo
        });	

        var readerEstadoCivil = new Ext.data.JsonReader( {
            root : 'comboEstadoCivil',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'id',
            mapping : 'codigo',
            type : 'string'
        }, {
            name : 'texto',
            type : 'string',
            mapping : 'descripcion'
        }]);
		var dsEstadoCivil = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_ESTADO_CIVIL
            }),
            reader: readerEstadoCivil
        });	

        var readerTipoRelacion = new Ext.data.JsonReader( {
            root : 'comboGenerico',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'id',
            mapping : 'codigo',
            type : 'string'
        }, {
            name : 'texto',
            type : 'string',
            mapping : 'descripcion'
        }]);
		var dsTipoRelacionPersona = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_COMBO_RELACIONES
            }),
            reader: readerTipoRelacion,
            baseParams: {cdTabla: 'RELACIONES'}
        });	

        var readerFuncionEnPoliza = new Ext.data.JsonReader( {
            root : 'comboRoles',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'id',
            mapping : 'id',
            type : 'string'
        }, {
            name : 'texto',
            type : 'string',
            mapping : 'texto'
        }]);
		var dsFuncionEnPoliza = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _COMBO_ROLES
            }),
            reader: readerFuncionEnPoliza
        });	

    /**************Fin Definición de Stores y sus correspondientes Readers ******************/

	/****************************************************************************************/
	        var el_formDatosAdicionales = new Ext.ux.MetaForm({
	        	id: 'el_formDatosAdicionales',
				//renderTo: 'formDatosAdicionales',
				labelAlign:'right',
	            //frame : true,
	            width : 600,
	            height: 300,
	            bodyStyle: {position: 'relative', background: 'white'},
	            url: _ACTION_OBTENER_DATOS_ADICIONALES + "?codigoPersona=" + CODIGO_PERSONA + "&codigoTipoPersona=" + TIPO_PERSONA,
	            //autoHeight: true,
	            //waitMsgTarget : true,
	            successProperty: 'success',
	            //bodyStyle:'background: white',
	            labelWidth: 200,
	            autoScroll: true,
	            defaults: {labelWidth: 120},
	            buttonAlign: 'center',
	            onMetaChange: function (form, meta) {
						        if (this.removeComponentsOnLoad == true) this.removeAll();
						        
								var fields = meta.fields;
								
								var doConfig = function(config){
									// handle plugins
						            if(config.plugins){
						            	var plugins = config.plugins;
						            	delete config.plugins;
						            	if(plugins instanceof Array){
						            		config.plugins = [];
						            		Ext.each(plugins, function(plugin){
						            			config.plugins.push( Ext.ComponentMgr.create(plugin) );
						            		});
						            	}else{
						            		config.plugins = Ext.ComponentMgr.create(plugins);
						            	}
						            }	            
									
									// handle regexps
						            if(config.regex) {
						                config.regex = new RegExp(config.regex)
						            }
						            
						            // to avoid checkbox misalignment
						            if('checkbox' === config.xtype) {
						                Ext.apply(config, {
						                     boxLabel:' ',
						                     checked: config.defaultValue
						                });
						            }
						            
						            Ext.apply(config, meta.fieldConfig || {});
									return config;
								};
								
								Ext.each(fields, function(field){		
									var oConfig = doConfig(field);			
									Ext.apply(oConfig, meta.formConfig || {});
									field.valor = field.value;
									field.value = "";
									field.hidden = eval(field.hidden);
									field.permitirBlancos = field.allowBlank;
									//field.allowBlank = field.allowBlank;
						
									if (field.store && field.store != "") {
										field._store = field.store;
										field.store = undefined;
									}
									try {
							        	this.add(field);
							        }catch (e) {
							        }	        
								}, this);
								this.doLayout();
								var _this = this;
								Ext.each(fields, function(campo) {
									if (campo._store && campo._store != "") {
										Ext.getCmp(campo.id).store = eval(campo._store);
									}
									Ext.getCmp(campo.id).setValue(campo.valor);
									if (campo.xtype == "combo") {
										Ext.getCmp(campo.id).on('focus', function() {});
									}
									if(campo.onChange!=null && campo.onChange!= undefined && campo.onChange!="")
									{
										Ext.getCmp(campo.id).on('change', function() {eval(campo.onChange)});
									}
								});
	            					Ext.each(el_formDatosAdicionales.items.items,function (campito) {
      									if (campito.permitirBlancos == "false" || campito.permitirBlancos == "" ||campito.permitirBlancos == "S" || campito.permitirBlancos === "false" || campito.permitirBlancos === false || campito.permitirBlancos === "S") {
      										campito.allowBlank = false;
      									} else {
       										campito.allowBlank = true;
      									}
     					  			});
	            },
	            listeners: {
	            				afterrender: function (obj) {
	            					return true;
	            				}
	            		},
	            buttons: [
	            					{
	            					text:getLabelFromMap('abmPersonasDtAdcButtonGuardar', helpMap,'Guardar'),
           							tooltip:getToolTipFromMap('abmPersonasDtAdcButtonGuardar', helpMap,'Guardar'),
           							
	            					//text: 'Guardar',
	            					handler: function () {
		            					 agregarNuevaPersona = false;
		            					 if (el_formDatosAdicionales.isValid()) {
		            					 	guardarDatosAdicionales();
		            					 }else {
					                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
		            					 }
	            					 }
	            					},{
	            					text:getLabelFromMap('abmPersonasDtAdcButtonGuardarAgregar', helpMap,'Guardar y Agregar'),
           							tooltip:getToolTipFromMap('abmPersonasDtAdcButtonGuardarAgregar', helpMap,'Guardar y Agregar'),	
	            					//text: 'Guardar y Agregar', 
	            					handler: function () {
	            											agregarNuevaPersona = true; 
					            					 if (el_formDatosAdicionales.isValid()) {
	            											guardarDatosAdicionales();
					            					 }else {
								                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
					            					 }
	            								}
	            					},{
      								text:getLabelFromMap('abmPersonasDtAdcButtonRegresar', helpMap,'Regresar'),
           							tooltip:getToolTipFromMap('abmPersonasDtAdcButtonRegresar', helpMap,'Regresar'),	            					
	            					//text: 'Regresar', 
	            					/* handler: function() {
	            								window.location.href = _ACTION_REGRESAR;
	            								} */
	            								
	            								handler: function() {if(COD==1){
            					 window.location.href = _ACTION_REGRESAR_CLIENTE;
	            								}
	            								else{ window.location.href = _ACTION_REGRESAR;
	            								}
	            								}
	            					}
	            				]
			});
	el_formDatosAdicionales.on ('afterrender', function () {
	            					Ext.each(el_formDatosAdicionales.items.items,function (campito) {
      									if (campito.xtype == "textfield") console.log(campito.allowBlank);
      									if (eval(campito.allowBlank) == false) {
       										campito.allowBlank = false;
      									} else {
       										campito.allowBlank = true;
      									}
     					  			});
	});
	function guardarDatosAdicionales () {
		var contCampos = 0;
		var _params = "datosAdicionales[0].pi_cdperson=" + CODIGO_PERSONA;
		//window.parent.mostrarOcultarMascara(true);

		//window.parent.mostrarOcultarMascara(false);
		Ext.each(el_formDatosAdicionales.items.items, function(campito){
					if (campito.name && campito.name != "") {
						var idx = campito.name.indexOf(".");
						if (idx > 0) {
							contCampos++;
							var valor = campito.name.substring(idx + 1);
							if (valor.length == 1) valor = "0" + valor;
							_params += "&datosAdicionales[0].pi_otvalor" + valor + "=" + campito.getValue();
						}
					}else {
						_params += "&datosAdicionales[0]." + campito.name + "=" + campito.getValue();
					}
		
		});
		if (_params.length > 0 && contCampos > 0) {
			//startMask('ttabs', 'Espere...');
			my_Mask = new Ext.LoadMask(el_formDatosAdicionales.id, {msg: 'Espere ...', disabled: false});
			my_Mask.show();
			execConnection(_ACTION_GUARDAR_DATOS_ADICIONALES, _params, cbkGuardarDatosAdicionales);
		}
	}
	function cbkGuardarDatosAdicionales (_success, _message) {
		//endMask();
		if (my_Mask) {
			my_Mask.hide();
			my_Mask = null;
		}
		if (!_success) {
			Ext.Msg.alert('Error', _message/*, function() {window.parent.resetPersona();}*/);
		}else {
			Ext.Msg.alert('Aviso', _message, function() {
					if (agregarNuevaPersona) {
						limpiarCamposForm();
					    //window.parent.resetPersona2 ();
					    resetPersona();
						//el_formDatosAdicionales.destroy();
					}
			});
		}
	}


	/****************************************************************************************/


	/**************Comienza Form Datos Generales ********************************************/
	
	var _titleFechaDe = (TIPO_PERSONA == 'M')?'Fecha de Constituci&oacute;n':'Fecha de Nacimiento';
	
	var el_formpanel = new Ext.FormPanel ({
			renderTo: 'formDatosGrales',
			id:'el_formpanelId',
            url : _ACTION_GET_PERSONA,
            frame : true,
            width : 600,
            height: 360,
            bodyStyle:'background: white',
            labelAlign:'right',
            waitMsgTarget : true,
            store: dsPersona,
            reader: readerPersona,
            successProperty: 'success',
            items: [
            		{
            			layout: 'column',
            			items:[{
            					columnWidth: .50,
            					layout: 'form',
            					items: [
            					{
	            					xtype: 'hidden', 
	            					name: 'codigoPersona'
            					},{
									xtype: 'combo', 
									tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
		                    		store: dsTipoPersonaJ, 
		                    		fieldLabel: getLabelFromMap('tipoPersonaJ',helpMap,'Tipo de Persona'),
    	                        	tooltip: getToolTipFromMap('tipoPersonaJ',helpMap,'Tipo de Persona'),
    	                        	hasHelpIcon:getHelpIconFromMap('tipoPersonaJ',helpMap),
									Ayuda: getHelpTextFromMap('tipoPersonaJ',helpMap),
		                    		//anchor:'100%', 
		                    		width:'100%',		                    		
		                    		displayField:'descripcion', 
		                    		valueField: 'codigo', 
		                    		hiddenName: 'codigoTipoPersona',
		                    		typeAhead: true, 
		                    		triggerAction: 'all', 
		                    		lazyRender:   true, 
		                    		emptyText:'Seleccione Tipo de Persona...', 
		                    		selectOnFocus:true,
		                    		mode:'local',
		                    		forceSelection:true,
		                    		//fieldLabel: 'Tipo de Persona', 
		                    		name: 'tipoPersonaJ', 
		                    		id: 'tipoPersonaJ', 
		                    		allowBlank:false,
									//blankText: 'Este campo es requerido',
		                    		//disabled: true,
		                    		onSelect: function (record) {
		                    			var _label = Ext.DomQuery.select(String.format('label[for="{0}"]', Ext.getCmp('fechaNacimiento').id));
		                    			if (_label) {
		                    				if(record.get('codigo')== "F"){
		                    					_label[0].childNodes[0].nodeValue = 'Fecha de Nacimiento:';
		                    				}else{_label[0].childNodes[0].nodeValue = 'Fecha de Constitución:';
		                    			}};
		                    					                    					                    			
		                    			TIPO_PERSONA = record.get('codigo');
		                    			this.setValue(record.get('codigo'));
		                    			this.collapse();
		                    			habilitarCampos();
		                    			var frame_url = _ACTION_LEER_DATOS_ADICIONALES + '?codigoTipoPersona=' + TIPO_PERSONA + '&codigoPersona=' + CODIGO_PERSONA;
		                    			el_formDatosAdicionales.url = frame_url;
		                    			el_formDatosAdicionales.load();
		                    			///////////////////////////////////Ext.getCmp('framecito').setSrc(frame_url, true);
		                    			//Ext.getCmp('framecito').height = 375;
		                    			
		                    		}			                    
		                    	},
								{
									xtype: 'textfield',
     								fieldLabel: getLabelFromMap('nombre', helpMap,'Nombre'), 
	     							tooltip: getToolTipFromMap('nombre', helpMap,'Nombre'),  
	     							hasHelpIcon:getHelpIconFromMap('nombre',helpMap),
									Ayuda: getHelpTextFromMap('nombre',helpMap),         
									allowBlank:false,
									blankText: 'Este campo es requerido',
									//fieldLabel: 'Nombre', 
									name: 'nombre', 
									//anchor: '95%', 
									width:170,
									name: 'nombre', 
									id: 'nombre',
									allowBlank: false
								},{
									xtype: 'textfield', 
     								fieldLabel: getLabelFromMap('apellidoPaterno', helpMap,'Apellido Paterno'), 
	     							tooltip: getToolTipFromMap('apellidoPaterno', helpMap,'Apellido Paterno'),
	     							hasHelpIcon:getHelpIconFromMap('apellidoPaterno',helpMap),
									Ayuda: getHelpTextFromMap('apellidoPaterno',helpMap),               								
									//fieldLabel: 'Apellido Paterno', 
									name: 'apellidoPaterno', 
									// anchor: '95%', 
									width:170,
									name: 'apellidoPaterno', 
									id: 'apellidoPaterno'
								},{
									xtype: 'textfield', 
     								fieldLabel: getLabelFromMap('apellidoMaterno', helpMap,'Apellido Materno'), 
	     							tooltip: getToolTipFromMap('apellidoMaterno', helpMap,'Apellido Materno'),
	     							hasHelpIcon:getHelpIconFromMap('apellidoMaterno',helpMap),
									Ayuda: getHelpTextFromMap('apellidoMaterno',helpMap),    
									//fieldLabel: 'Apellido Materno', 
									name: 'apellidoMaterno', 
									//anchor: '95%', 
									width:170,
									name: 'apellidoMaterno', 
									id: 'apellidoMaterno'
								},{
									xtype: 'combo', 
									tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
				                    store: dsSexo, 
					          		fieldLabel: getLabelFromMap('sexo',helpMap,'Sexo'),
    		                        tooltip: getToolTipFromMap('sexo',helpMap,'Sexo'),
    		                        hasHelpIcon:getHelpIconFromMap('sexo',helpMap),
									Ayuda: getHelpTextFromMap('sexo',helpMap),    			          		
				                    //anchor:'100%', 
				                    width:'100%',
				                    displayField:'descripcion', 
				                    valueField: 'codigo', 
				                    hiddenName: 'sexoH',
				                    typeAhead: true, 
				                    triggerAction: 'all', 
				                    lazyRender:   true, 
				                    mode:'local',
				                    emptyText:'Seleccione Sexo...', 
				                    selectOnFocus:true,
				                    forceSelection:true,
				                    //fieldLabel: 'Sexo', 
				                    id: 'sexo'
			                    },{
				                    xtype: 'combo', 
				                    tpl: '<tpl for="."><div ext:qtip="{id}.{texto}" class="x-combo-list-item">{texto}</div></tpl>',
				                    store: dsEstadoCivil, 
					          		fieldLabel: getLabelFromMap('estadoCivil',helpMap,'Estado Civil'),
	    	                        tooltip: getToolTipFromMap('estadoCivil',helpMap,'Estado Civil'),	
	    	                        hasHelpIcon:getHelpIconFromMap('estadoCivil',helpMap),
									Ayuda: getHelpTextFromMap('estadoCivil',helpMap),		          		
				                    //anchor:'100%', 
				                    width:'100%',
				                    displayField:'texto', 
				                    valueField: 'id', 
				                    hiddenName: 'estadoCivilH',
				                    typeAhead: true, 
				                    triggerAction: 'all', 
				                    lazyRender:   true, 
				                    emptyText:'Seleccione Estado Civil...', 
				                    selectOnFocus:true,
				                    //fieldLabel: 'Estado Civil', 
				                    name: 'estadoCivil', 
				                    id: 'estadoCivil', 
				                    mode:'local',
				                    forceSelection: true
			                    },{
				                    xtype: 'datefield', 
	     							fieldLabel: getLabelFromMap('fechaNacimiento', helpMap,_titleFechaDe), 
		     						tooltip: getToolTipFromMap('fechaNacimiento', helpMap,_titleFechaDe),
		     						hasHelpIcon:getHelpIconFromMap('fechaNacimiento',helpMap),
									Ayuda: getHelpTextFromMap('fechaNacimiento',helpMap),	           
				                    //fieldLabel: 'Fecha de Nacimiento', 
				                    format: 'd/m/Y',
				                    altFormats : 'm/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g', 
				                    //anchor: '95%', 
				                    width: '100%', 
				                    name: 'fechaNacimiento', 
				                    id: 'fechaNacimiento',
				                    allowBlank: false
			                    },{
				                    xtype: 'combo', 
				                    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
				                    store: dsNacionalidad, 
					          		fieldLabel: getLabelFromMap('nacionalidad',helpMap,'Nacionalidad'),
    		                        tooltip: getToolTipFromMap('nacionalidad',helpMap,'Nacionalidad'),	
    		                        hasHelpIcon:getHelpIconFromMap('nacionalidad',helpMap),
									Ayuda: getHelpTextFromMap('nacionalidad',helpMap),	 		          		
				                    //anchor:'100%', 
				                    width:'100%',
				                    displayField:'descripcion', 
				                    valueField: 'codigo', 
				                    hiddenName: 'NacionalidadH',
				                    typeAhead: true, 
				                    triggerAction: 'all', 
				                    lazyRender:   true, 
				                    emptyText:'Seleccione Nacionalidad...', 
				                    selectOnFocus:true,
				                    mode:'local',
				                    forceSelection:true,
				                    //fieldLabel: 'Nacionalidad', 
				                    name: 'nacionalidad', 
				                    id: 'nacionalidad',
				                    allowBlank: false
				                   }
								]
            				   }, {
            				   	columnWidth: .5,
            					layout: 'form',
            					items: [
									{
										xtype: 'combo', 
										tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
				                    	store: dsTipoIdentificador, 
						          		fieldLabel: getLabelFromMap('tipoIdentificador',helpMap,'Tipo de Identificador'),
    			                        tooltip: getToolTipFromMap('tipoIdentificador',helpMap,'Tipo de Identificador'),	
    			                        hasHelpIcon:getHelpIconFromMap('tipoIdentificador',helpMap),
										Ayuda: getHelpTextFromMap('tipoIdentificador',helpMap),	 		          		
				                    	//anchor:'100%', 
				                    	width:175,
				                    	displayField:'descripcion', 
				                    	valueField: 'codigo', 
				                    	hiddenName: 'tipoIdentificadorH',
				                    	typeAhead: true, 
				                    	triggerAction: 'all', 
				                    	lazyRender:   true, 
				                    	emptyText:'Seleccione Tipo de Identificador...', 
				                    	selectOnFocus:true,
				                    	mode:'local',
				                    	forceSelection:true,
				                    	allowBlank:false,
										//blankText: 'Este campo es requerido',
				                    	//fieldLabel: 'Tipo de Identificador', 
				                    	id: 'tipoIdentificador', 
				                    	name: 'tipoIdentificador'
			                    	},{
				                    	xtype: 'textfield',
     									fieldLabel: getLabelFromMap('nroIdentificador', helpMap,'Nro. de Identificador'), 
	     								tooltip: getToolTipFromMap('nroIdentificador', helpMap,'Nro. de Identificador'),   
	     								hasHelpIcon:getHelpIconFromMap('nroIdentificador',helpMap),
										Ayuda: getHelpTextFromMap('nroIdentificador',helpMap),	 	        
										allowBlank:false,
										blankText: 'Este campo es requerido',
										//fieldLabel: 'Nro. de Identificador', 
										id: 'nroIdentificador', 
										// width:'95%',
										// anchor: '100%'
										width: 175
										
									},{
										xtype: 'combo', 
										tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
				                    	store: dsTipoEmpresa, 
						          		fieldLabel: getLabelFromMap('tipoEntidad',helpMap,'Tipo de Entidad'),
    			                        tooltip: getToolTipFromMap('tipoEntidad',helpMap,'Tipo de Entidad'),	
    			                        hasHelpIcon:getHelpIconFromMap('tipoEntidad',helpMap),
										Ayuda: getHelpTextFromMap('tipoEntidad',helpMap),	 			          		
				                    	//anchor:'100%', 
				                    	width:175,
				                    	displayField:'descripcion', 
				                    	valueField: 'codigo', 
				                    	hiddenName: 'tipoEntidadHS',
				                    	typeAhead: true, 
				                    	triggerAction: 'all', 
				                    	lazyRender:   true, 
				                    	mode:'local',
				                    	emptyText:'Seleccione Tipo de Entidad...', 
				                    	selectOnFocus:true,
				                    	forceSelection:true,
				                    	//fieldLabel: 'Tipo de Entidad', 
				                    	id: 'tipoEntidad', 
				                    	name: 'tipoEntidad',
				                    	allowBlank: false
			                    	}/*,{
				                    	xtype: 'datefield', 
		     							fieldLabel: getLabelFromMap('dtFldFechaIngresoAbmPersonas', helpMap,'Fecha de Ingreso'), 
	    		 						tooltip: getToolTipFromMap('dtFldFechaIngresoAbmPersonas', helpMap,'Fecha de Ingreso'),           
				                    	//fieldLabel: 'Fecha de Ingreso', 
				                    	id: 'fechaIngreso', 
				                    	format: 'd/m/Y', 
				                    	anchor: '95%', 
				                    	renderer: Ext.util.Format.dateRenderer('d/m/Y')
			                    	}*/,
			                    	{
				                    	xtype: 'textfield',
     									fieldLabel: getLabelFromMap('emailId', helpMap,'Email'), 
	     								tooltip: getToolTipFromMap('emailId', helpMap,'Email'),   
	     								hasHelpIcon:getHelpIconFromMap('emailId',helpMap),
										Ayuda: getHelpTextFromMap('emailId',helpMap),	         
										vtype:'email',
										emailText:'Este campo deber&iacute;a ser una direcci&oacute;n email con el formato usuario@dominio.com',
										blankText: 'Este campo es requerido',										
										id: 'emailId',
										name: 'emailId',
										//anchor: '100%'
										width: 175,
										allowBlank: false
									},
									{
				                    	xtype: 'textfield',
     									fieldLabel: getLabelFromMap('curpId', helpMap,'CURP'), 
	     								tooltip: getToolTipFromMap('curpId', helpMap,'CURP'),   
	     								hasHelpIcon:getHelpIconFromMap('curpId',helpMap),
										Ayuda: getHelpTextFromMap('curpId',helpMap),	        
										blankText: 'Este campo es requerido',										
										id: 'curpId',
										name: 'curpId',
										width:175,
										// anchor: '100%',
										//Pedido en issue 1010
										convertToUpperCase: true,
										
										validator: function(value){
										        if (this.convertToUpperCase) {
										            var v = value.toUpperCase();
										            this.el.dom.value = (v === null || v === undefined ? '' : v);
										        }
										        return true;
									    }
									},
									{
				                    	xtype: 'textfield',
     									fieldLabel: getLabelFromMap('rfcId', helpMap,'RFC'), 
	     								tooltip: getToolTipFromMap('rfcId', helpMap,'RFC'),
	     								hasHelpIcon:getHelpIconFromMap('curpId',helpMap),
										Ayuda: getHelpTextFromMap('rfcId',helpMap),           
										blankText: 'Este campo es requerido',										
										id: 'rfcId',
										name: 'rfcId',
										//anchor: '100%',
										 width:175,
										 allowBlank: false,
										//Pedido en issue 1010
										convertToUpperCase: true,
										validator: function(value){
										        if (this.convertToUpperCase) {
										            var v = value.toUpperCase();
										            this.el.dom.value = (v === null || v === undefined ? '' : v);
										        }
										        return true;
									    }
									}
								]
            				   }
            			]
            		}
            		],
            		buttonAlign: 'center',
            		buttons: [
            					{
            					text:getLabelFromMap('abmPersonasDtGrlButtonGuardar', helpMap,'Guardar'),
           						tooltip:getToolTipFromMap('abmPersonasDtGrlButtonGuardar', helpMap,'Guardar'),
            					//text: 'Guardar', 
            					handler: function () 
            						{
            							if(el_formpanel.form.isValid())
            								{
             									GuardarPersona(false);
            								}            								
            								else
            								{
            								Ext.MessageBox.alert('Aviso','Complete la informaci&oacute;n requerida');
            								}
            						}
            					},{
            					text:getLabelFromMap('abmPersonasDtGrlButtonGuardarAgregar', helpMap,'Guardar y Agregar'),
           						tooltip:getToolTipFromMap('abmPersonasDtGrlButtonGuardarAgregar', helpMap,'Guardar y Agregar'),	
            					//text: 'Guardar y Agregar', 
            					handler: function () {
            							if(el_formpanel.form.isValid()) {
		            						GuardarPersona(true);
            							} else {
            								Ext.MessageBox.alert('Aviso','Complete la informaci&oacute;n requerida');
            							}
            						}
            					},{
            					text:getLabelFromMap('abmPersonasDtGrlButtonRegresar', helpMap,'Regresar'),
           						tooltip:getToolTipFromMap('abmPersonasDtGrlButtonRegresar', helpMap,'Regresar'),	
            					//text: 'Regresar',
            					handler: function() {if(COD==1){
            					 window.location.href = _ACTION_REGRESAR_CLIENTE;
	            								}
	            								else{ window.location.href = _ACTION_REGRESAR;
	            								}
	            								}
            					}
            				]
	});

function GuardarPersona(_agregarPersona) {
	var Params = "";
	Params = "datosGenerales[0].codigoPersona=" + CODIGO_PERSONA +
			 "&datosGenerales[0].Nombre=" + el_formpanel.findById('nombre').getValue() +
			 "&datosGenerales[0].ApellidoPaterno=" + el_formpanel.findById('apellidoPaterno').getValue() +
			 "&datosGenerales[0].ApellidoMaterno=" + el_formpanel.findById('apellidoMaterno').getValue() +
			 "&datosGenerales[0].codigoTipoPersonaJ=" + el_formpanel.findById('tipoPersonaJ').getValue() +
			 "&datosGenerales[0].Sexo=" + el_formpanel.findById('sexo').getValue() +
			 "&datosGenerales[0].EstadoCivil=" + el_formpanel.findById('estadoCivil').getValue() +
			 "&datosGenerales[0].FechaNacimiento=" + el_formpanel.findById('fechaNacimiento').getRawValue() +
			 "&datosGenerales[0].Nacionalidad=" + el_formpanel.findById('nacionalidad').getValue() +
			 "&datosGenerales[0].tipoIdentificador=" + el_formpanel.findById('tipoIdentificador').getValue() +
			 "&datosGenerales[0].nroIdentificador=" + el_formpanel.findById('nroIdentificador').getValue() +
			 "&datosGenerales[0].tipoEntidad=" + el_formpanel.findById('tipoEntidad').getValue() +
			 //"&datosGenerales[0].fechaIngreso=" + el_formpanel.findById('fechaIngreso').getRawValue() +
			 "&datosGenerales[0].email=" + el_formpanel.findById('emailId').getValue() +
			 "&datosGenerales[0].curp=" + el_formpanel.findById('curpId').getValue() +
			 "&datosGenerales[0].rfc=" + el_formpanel.findById('rfcId').getValue() ;
			 
	var conn = new Ext.data.Connection();
	conn.request({
		url: _ACTION_GUARDAR_DATOS_GENERALES,
		method: 'POST',
		successProperty : '@success',
		params : Params,
		waitMsg: 'Espere por favor...',
                         	callback: function (options, success, response) {
                         				if (Ext.util.JSON.decode(response.responseText).success == false) 
                         					{
                         						Ext.Msg.alert('Error', Ext.util.JSON.decode(response.responseText).errorMessages[0]);
                         						band = false;
                         					} else {
                         					Ext.Msg.alert('Confirmaci&oacute;n', Ext.util.JSON.decode(response.responseText).actionMessages[0], 
                         										function(){
                         											if (_agregarPersona) {
                         												resetPersona();
                         												//TULY
                         												el_formpanel.findById('tipoPersonaJ').setValue(TIPO_PERSONA);
                         											}else {
						                          					if (CODIGO_PERSONA == "") {
						                          						if ((Ext.util.JSON.decode(response.responseText).codigoPersona != null) && (Ext.util.JSON.decode(response.responseText).codigoPersona != "")) {
						                          							CODIGO_PERSONA = Ext.util.JSON.decode(response.responseText).codigoPersona;
				                          									Ext.getCmp('tipoPersonaJ').setDisabled(true);
						                          							
											                    			////var frame_url = _ACTION_LEER_DATOS_ADICIONALES + '?codigoTipoPersona=' + TIPO_PERSONA + '&codigoPersona=' + CODIGO_PERSONA;
											                    			/////////////////////////////////////Ext.getCmp('framecito').setSrc(frame_url, true);
						                          						}
						                          					} 
                         											}
                         										}
                         								);
                         					}
                         			}
		});
	
}

	function formateoFecha(value) {
		var _value = value.substring(8, 1) + "/" + value.substring(5, 1) + "/" + value.substring(0, 4);

		return value.substring(8, 1) + "/" + value.substring(5, 1) + "/" + value.substring(0, 4);
	}
	/********* Comienzo del grid de Domicilios*****************************/

		var recordDomicilios = new Ext.data.Record.create([ 
		{name : 'codigoPersona', mapping : 'codigoPersona',type : 'string'},
		{
		        	name: 'numOrdenDomicilio',
		        	type: 'string',
		        	mapping: 'numOrdenDomicilio'
		        }, {
		            name : 'tipoDomicilio',
		            type : 'string',
		            mapping : 'tipoDomicilio'
		        }, {
		            name : 'dsDomicilio',
		            type : 'string',
		            mapping : 'dsDomicilio'
		        }, {
		            name : 'cdPostal',
		            type : 'string',
		            mapping : 'cdPostal'
		        }, {
		            name : 'numero',
		            type : 'string',
		            mapping : 'numero'
		        }, {
		            name : 'numeroInterno',
		            type : 'string',
		            mapping : 'numeroInterno'
		        }, {
		            name : 'codigoPais',
		            type : 'string',
		            mapping : 'codigoPais'
		        }, {
		        	name: 'dsPais',
		        	type: 'string',
		        	mapping: 'dsPais'
		        }, {
		            name : 'codigoEstado',
		            type : 'string',
		            mapping : 'codigoEstado'
		        }, {
		        	name: 'dsEstado',
		        	type: 'string',
		        	mapping: 'dsEstado'
		        }, {
		            name : 'codigoMunicipio',
		            type : 'string',
		            mapping : 'codigoMunicipio'
		        }, {
		        	name: 'dsMunicipio',
		        	type: 'string',
		        	mapping: 'dsMunicipio'
		        }, {
		            name : 'codigoColonia',
		            type : 'string',
		            mapping : 'codigoColonia'
		        }, {
		        	name: 'dsColonia',
		        	type: 'string',
		        	mapping: 'dsColonia'
		        }]);
		function crearGridDomciliosStore(){
		        var readerDomicilio = new Ext.data.JsonReader( {
		            root : 'datosDomicilio',
		            totalProperty: 'totalCount',
		            successProperty : '@success'
		
		        },
		        recordDomicilios 
		        );        
		        var dsDomicilio = new Ext.data.Store ({
		            proxy: new Ext.data.HttpProxy({
		                url: _ACTION_OBTENER_DOMICILIOS
		            }),
		            reader: readerDomicilio
		        });
		
				return dsDomicilio;
		 	}

		//Creación de Combos
		var comboPais = new Ext.form.ComboBox(
			{
				tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
			    store: dsPaises,
			    id:'comboPaisId',
			    width: 100, 
			    displayField:'descripcion', 
			    valueField: 'codigo', 
			    hiddenName: 'CodigoPais', 
			    name: 'codigoPais',
			    typeAhead: true, 
			    triggerAction: 'all', 
			    lazyRender:   true, 
			    mode:'local',
			    //selectOnFocus:true,
			    emptyText:'Seleccione Pais...', 
			    selectOnFocus:true,
			    forceSelection:true,
			    listeners: {
			    	focus: function () {
                         		if (!this.list) {
                         			this.clearValue();
                         			this.setValue("");
                         			this.setRawValue("");
                         		}
                         		else
                         		{
                         			if (this.getRawValue().indexOf("&nbsp;")){
                         				this.clearValue();
                         				this.setValue("");
                         				this.setRawValue("");
	                         		}
                         		} 
			    	},
			    	collapse: function (record) {
			    	
			    	
			    		//console.log(record);
			    		if (grillaDomicilios.getSelectionModel().getSelected().get('codigoPais') != record.value) {
           					grillaDomicilios.getSelectionModel().getSelected().set('codigoPais', record.value);
           					var reg = grillaDomicilios.getSelectionModel().getSelected();
           					reg.set('codigoEstado', '');
           					reg.set('codigoMunicipio', '');
           					reg.set('codigoColonia', '');
           					reg.set('dsColonia', '');
           					reg.set('dsMunicipio', '');
           					reg.set('dsEstado', '');
           					reg.set('cdPostal', '');
           					
           				}
			    	}
			    }
           		/*onSelect: function(record) {
           				this.setValue(record.get('codigo'));
           				this.setRawValue(record.get('descripcion'));
           				this.collapse();
           				console.log(record.get('codigo'));
           				grillaDomicilios.getSelectionModel().getSelected().set('codigoPais', record.get('codigo'));
           		}*//*,
           		labelSeparator:'',
           		onSelect: function (record) {
           						this.setValue(record.get('codigo'));
           						this.collapse();
           						comboEstados.store.reload({
           								params: {codigoPais: record.get('codigo')}
           						});
           		}*/
         });

		var comboCodigoPostal = new Ext.form.ComboBox(
			{
				tpl: '<tpl for="."><div ext:qtip="{cdPostal}.{dsPostal}" class="x-combo-list-item">{dsPostal}</div></tpl>',
			    store: dataStoreCodPos,
			    id:'comboCodigoPostalId',
			    width: 100, 
			    displayField:'dsPostal', 
			    valueField: 'cdPostal', 
			    hiddenName: 'codigoPostal', 
			    name: 'codigoPostal',
			    typeAhead: true, 
			    triggerAction: 'all', 
			    lazyRender:   true, 
			    mode:'local',
			    //selectOnFocus:true,
			    emptyText:'Seleccione el C&oacute;digo Postal...', 
			    selectOnFocus:true,
			    forceSelection:true,
			    listeners: {
				focus: function () {
                         		if (!this.list) {
                         			this.clearValue();
                         			this.setValue("");
                         			this.setRawValue("");
                         		}
                         		else
                         		{
                         			if (this.getRawValue().indexOf("&nbsp;")){
                         				this.clearValue();
                         				this.setValue("");
                         				this.setRawValue("");
	                         		}
                         		} 
						this.store.reload({
	               								params: {codigoPais: grillaDomicilios.getSelectionModel().getSelected().get('codigoPais')},
	               								success: function () {
	               											this.expand();
	               								},
	               								failure: function () {
	              												combo.store.removeAll();
	              												combo.setValue('');
	              												combo.setRawValue('');
	               										}
	               						});
				},
      			expand: function (combo, record) {
      						combo.store.reload({
      								params: {codigoPais: grillaDomicilios.getSelectionModel().getSelected().get('codigoPais')},
      								failure: function () {
      												combo.store.removeAll();
      												combo.setValue('');
      												combo.setRawValue('');
      										}
      						});
      					},
      			collapse: function (record) {
      				grillaDomicilios.getSelectionModel().getSelected().set('cdPostal', record.value);
      			}
           		}
           		
         });
         
		var comboEstados = new Ext.form.ComboBox(
			{
				tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
			    store: dsEstados,
			    id:'comboEstadosId', 
			    width: 100, 
			    displayField:'descripcion', 
			    valueField: 'codigo', 
			    hiddenName: 'CodigoEstado', 
			    id: 'codEstado', 
			    name: 'codEstado',
			    mode:'local',
			    typeAhead: true, 
			    triggerAction: 'all', 
			    lazyRender:   true, 
			    emptyText:'Seleccione Estado...', 
			    selectOnFocus:true,
			    forceSelection:true,
			    labelSeparator:'',
           		listeners: {
				focus: function () {
                         		if (!this.list) {
                         			this.clearValue();
                         			this.setValue("");
                         			this.setRawValue("");
                         		}
                         		else
                         		{
                         			if (this.getRawValue().indexOf("&nbsp;")){
                         				this.clearValue();
                         				this.setValue("");
                         				this.setRawValue("");
	                         		}
                         		} 
						this.store.reload({
	               								params: {codigoPais: grillaDomicilios.getSelectionModel().getSelected().get('codigoPais')},
	               								success: function () {
	               											this.expand();
	               								},
	               								failure: function () {
	              												combo.store.removeAll();
	              												combo.setValue('');
	              												combo.setRawValue('');
	               										}
	               						});
				},
      			expand: function (combo, record) {
      						combo.store.reload({
      								params: {codigoPais: grillaDomicilios.getSelectionModel().getSelected().get('codigoPais')},
      								failure: function () {
      												combo.store.removeAll();
      												combo.setValue('');
      												combo.setRawValue('');
      										}
      						});
      					},
      			collapse: function (record) {
      				grillaDomicilios.getSelectionModel().getSelected().set('codigoEstado', record.value);
      			}
           		}/*,
           		onSelect: function (record) {
           					grillaDomicilios.getSelectionModel().getSelected().set('codigoEstado', record.get('codigo'));
           					//this.setValue(record.get('codigo'));
           					//this.collapse();
           		}*/
	    });
		var comboMunicipio = new Ext.form.ComboBox(
			{
				tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
				store: dsMunicipios, 
				id:'comboMunicipioId',
				width: 100, 
				displayField:'descripcion', 
				valueField: 'codigo', 
				hiddenName: 'CodigoPais', 
				typeAhead: true, 
				triggerAction: 'all', 
				lazyRender:   true, 
				emptyText:'Estado...', 
				mode:'local',
				selectOnFocus:true,
				forceSelection:true,
			    /*labelSeparator:'',
           		onSelect: function (record) {
           					this.setValue(record.get('codigo'));
           					this.collapse();
           					comboColonias.store.reload({
           						params: {
        											codigoPais: grillaDomicilios.getSelectionModel().getSelected().get('codigoPais'),
        											codigoEstado: grillaDomicilios.getSelectionModel().getSelected().get('codigoEstado'),
        											codigoMunicipio: record.get('codigo')
           						}
           					});
           		},*/
           		listeners: {
           				focus: function () {
                         		if (!this.list) {
                         			this.clearValue();
                         			this.setValue("");
                         			this.setRawValue("");
                         		}
                         		else
                         		{
                         			if (this.getRawValue().indexOf("&nbsp;")){
                         				this.clearValue();
                         				this.setValue("");
                         				this.setRawValue("");
	                         		}
                         		} 
           					this.store.reload({
           										params: {
              										codigoPais: grillaDomicilios.getSelectionModel().getSelected().get('codigoPais'),
              										codigoEstado: grillaDomicilios.getSelectionModel().getSelected().get('codigoEstado')
              									},
              					success: function () {
              						this.expand();
              					},
           								failure: function () {
           												combo.store.removeAll();
           												combo.setValue('');
           												combo.setRawValue('');
           										}
           								});
           				},
           				expand: function (combo, record) {
           								combo.store.reload({
           										params: {
              										codigoPais: grillaDomicilios.getSelectionModel().getSelected().get('codigoPais'),
              										codigoEstado: grillaDomicilios.getSelectionModel().getSelected().get('codigoEstado')
              									},
           								failure: function () {
           												combo.store.removeAll();
           												combo.setValue('');
           												combo.setRawValue('');
           										}
           								});
           						},
		      			collapse: function (record) {
		      				grillaDomicilios.getSelectionModel().getSelected().set('codigoMunicipio', record.value);
		      			}
                  		}
	    });
		var comboColonias = new Ext.form.ComboBox(
			{
				tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
			    store: dsColonias, 
			    id:'comboColoniaId',
			    displayField:'descripcion', 
			    valueField: 'codigo', 
			    hiddenName: 'CodigColonia', 
			    name: 'codigoColonia',
			    typeAhead: true, 
			    triggerAction: 'all', 
			    lazyRender:   true, 
			    emptyText:'Seleccione Municipio...', 
			    selectOnFocus:true,
			    forceSelection:true,
			    mode:'local',
			    //labelSeparator:'', 
			    width: 100, 
           		listeners: {
     					focus: function () {
                         		if (!this.list) {
                         			this.clearValue();
                         			this.setValue("");
                         			this.setRawValue("");
                         		}
                         		else
                         		{
                         			if (this.getRawValue().indexOf("&nbsp;")){
                         				this.clearValue();
                         				this.setValue("");
                         				this.setRawValue("");
	                         		}
                         		} 
     							this.store.reload({
     								params: {
     											codigoPais: grillaDomicilios.getSelectionModel().getSelected().get('codigoPais'),
     											codigoEstado: grillaDomicilios.getSelectionModel().getSelected().get('codigoEstado'),
     											codigoMunicipio: grillaDomicilios.getSelectionModel().getSelected().get('codigoMunicipio')
     										},
     								success: function () {
     											this.expand();
     								},
     								failure: function () {
    												combo.store.removeAll();
    												combo.setValue('');
    												combo.setRawValue('');
     										}
						});
  					},
   					expand: function (combo, record){
   						combo.store.reload({
   								params: {
   											codigoPais: grillaDomicilios.getSelectionModel().getSelected().get('codigoPais'),
   											codigoEstado: grillaDomicilios.getSelectionModel().getSelected().get('codigoEstado'),
   											codigoMunicipio: grillaDomicilios.getSelectionModel().getSelected().get('codigoMunicipio')
   										},
   								failure: function () {
   												combo.store.removeAll();
   												combo.setValue('');
   												combo.setRawValue('');
   										}
   						});
   					},
	      			collapse: function (record) {
	      				grillaDomicilios.getSelectionModel().getSelected().set('codigoColonia', record.value);
	      			}
             	}
        });
		var comboDomicilios = new Ext.form.ComboBox(
			{
				tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
			    store: dsTipoDomicilio, 
			    displayField:'descripcion', 
			    valueField: 'codigo', 
			    hiddenName: 'TipoDomicilio',
			    typeAhead: true, 
			    triggerAction: 'all', 
			    lazyRender:   true, 
			    emptyText:'Seleccione Tipo...', 
			    mode:'local',
			    selectOnFocus:true,
			    forceSelection:true,
			    //labelSeparator:'', 
			    fieldLabel: 'Tipo de Entidad', 
			    width: 100
			 })
		//Fin Creación de Combos
	var grillaDomicilios;

	function createGridDomcilios(){
		//Definición Column Model
	    var cm = new Ext.grid.ColumnModel([
	    		{
	    			dataIndex: 'codigoPersona',
	    			hidden: true
	    		},
	    		{
	    			dataIndex: 'numOrdenDomicilio',
	    			hidden: true
	    		},{
				   	header: getLabelFromMap('cmTipoDomicilioAbmPersonas',helpMap,'Tipo'),
        			tooltip: getToolTipFromMap('cmTipoDomicilioAbmPersonas', helpMap,'Tipo'),	   	
		           	//header: "Tipo",
		           	dataIndex: 'tipoDomicilio',
	    			sortable: true,
		           	width: 80,
	           		resizable: true,
		           	editor: comboDomicilios,
			        renderer: renderComboEditor(comboDomicilios)
	        	},{
				   	header: getLabelFromMap('cmCodigoPaisAbmPersonas',helpMap,'Pa&iacute;s'),
        			tooltip: getToolTipFromMap('cmCodigoPaisAbmPersonas', helpMap,'Pa&iacute;s'),	   	
	           		//header: 'Pa&iacute;s',
	           		//dataIndex: 'codigoPais',
	           		dataIndex: 'dsPais',
		           	width: 100,
	    			sortable: true,
	           		resizable: true,
		           	editor: comboPais,
		           	renderer: renderComboEditor(comboPais)
	           		
	           	},{
				   	header: getLabelFromMap('cmCodigoEstadoAbmPersonas',helpMap,'Estado'),
        			tooltip: getToolTipFromMap('cmCodigoEstadoAbmPersonas', helpMap,'Estado'),	   	
	           		//header: 'Estado',
	           		//dataIndex: 'codigoEstado',
	           		dataIndex: 'dsEstado',
		           	width: 90,
	    			sortable: true,
	           		resizable: true,
		           	editor: comboEstados,
		           	renderer: renderComboEditor(comboEstados)
	           	},{
				   	header: getLabelFromMap('cmCodigoMunicipioAbmPersonas',helpMap,'Municipio'),
        			tooltip: getToolTipFromMap('cmCodigoMunicipioAbmPersonas', helpMap,'Municipio'),	   	
	           		//header: 'Municipio',
	           		//dataIndex: 'codigoMunicipio',
	           		dataIndex: 'dsMunicipio',
		           	width: 90,
		           	sortable: true,
	           		resizable: true,
		           	editor: comboMunicipio,
		           	renderer: renderComboEditor(comboMunicipio)
	           	},{
				   	header: getLabelFromMap('cmCodigoColoniaAbmPersonas',helpMap,'Colonia'),
        			tooltip: getToolTipFromMap('cmCodigoColoniaAbmPersonas', helpMap,'Colonia'),	   	
	           		//header: 'Colonia',
	           		//dataIndex: 'codigoColonia',
	           		dataIndex: 'dsColonia',
		           	width: 90,
		           	sortable: true,
	           		editor: comboColonias,
	           		resizable: true,
	           		renderer: renderComboEditor(comboColonias)
	           	},{
				   	header: getLabelFromMap('cmDsDomicilioAbmPersonas',helpMap,'Calle'),
        			tooltip: getToolTipFromMap('cmDsDomicilioAbmPersonas', helpMap,'Calle'),	   	
		           	//header: "Calle",
		           	dataIndex: 'dsDomicilio',
		           	width: 60,
	    			sortable: true,
	           		resizable: true,
		           	editor: new Ext.form.TextField({name: 'Calle', width: 100, maxLength: 50})
	           	},{
				   	header: getLabelFromMap('cmCdPostalAbmPersonas',helpMap,'C.P.'),
        			tooltip: getToolTipFromMap('cmCdPostalAbmPersonas', helpMap,'Codigo Postal'),	   	
		           	//header: "C.P.",
		           	dataIndex: 'cdPostal',
		           	width: 40,
	    			sortable: true,
	           		resizable: true,
		           	editor: comboCodigoPostal,//new Ext.form.NumberField({name: 'CodigoPostal', width: 100, maxLength: 10})
		           	renderer: renderComboEditor(comboCodigoPostal)
	           	},{
				   	header: getLabelFromMap('cmNumeroAbmPersonas',helpMap,'N&uacute;mero'),
        			tooltip: getToolTipFromMap('cmNumeroAbmPersonas', helpMap,'N&uacute;mero'),	   	
		           	//header: "N&uacute;mero",
		           	dataIndex: 'numero',
		           	width: 50,
	    			sortable: true,
	           		resizable: true,
		           	editor: new Ext.form.TextField({name: 'numeroExterno', width: 100, maxLength: 10})
	           	},{
				   	header: getLabelFromMap('cmNumeroInternoAbmPersonas',helpMap,'N&uacute;m. Interno'),
        			tooltip: getToolTipFromMap('cmNumeroInternoAbmPersonas', helpMap,'N&uacute;m. Interno'),	   	
	           		//header: 'N&uacute;m. Interno',
	           		dataIndex: 'numeroInterno',
		           	width: 70,
	    			sortable: true,
	           		resizable: true,
	           		editor: new Ext.form.TextField({name: 'numeroInterno', width: 100, maxLength: 10})
	           	}
	           	]);
	           	
		//Fin Definición Column Model
		grillaDomicilios = new Ext.grid.EditorGridPanel({
	        el:'gridDomicilios',
	        id: 'gridDomicilios',
	        store: crearGridDomciliosStore(),
			border:true,
			frame: true,
			region: 'center',
			labelAlign:'right',
			bodyStyle:'background: white',
			collapsible: true,
			stripeRows: true,
            autoWidth : true,
            autoScroll: true,
			enableColumnResize: true,
	        clicksToEdit:1,
	        loadMask: {msg: 'Cargando...', disabled: false},
	        cm: cm,
	        buttonAlign: 'center',
	        viewConfig: {
	        		onRowSelect: function (row) {
	        			this.addRowClass (row, 'x-grid3-row');
	        		}
	        },
			buttons:[
		        		{
   							text:getLabelFromMap('abmPersonasGrDmcButtonAgregar', helpMap,'Agregar'),
       						tooltip:getToolTipFromMap('abmPersonasGrDmcButtonAgregar', helpMap,'Agregar'),	
		        			//text:'Agregar',
		        			handler: function () 
		        			{
		        				addGridDomicilioNewRecord();
		        				banderaAgregarDomicilio = true;
		        			}
		            	},
		            	{
   							text:getLabelFromMap('abmPersonasGrDmcButtonGuardar', helpMap,'Guardar'),
       						tooltip:getToolTipFromMap('abmPersonasGrDmcButtonGuardar', helpMap,'Guardar'),	
		            		//text:'Guardar',
		            		handler: function () {
		            			//alert(1);
	            		     	//if(banderaAgregarDomicilio){
	            		     	var _recs = grillaDomicilios.store.getModifiedRecords(); 	
	            		      	if (_recs.length==0)
								{

				  	  				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe ingresar un registro para realizar esta operaci&oacute;n'));

            	 				}else{
            		 				agregarNuevoDomicilio = false;
	            					guardarDatosDomicilio();
								} 
								/*}else{
									Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe agregar un registro para realizar esta operaci&oacute;n'));
								}*/
		            		
		               				//agregarNuevoDomicilio = false;
		            				//guardarDatosDomicilio();
		            		}
		            	},
		            	{
   							text:getLabelFromMap('abmPersonasGrDmcButtonGuardarAgregar', helpMap,'Guardar y Agregar'),
       						tooltip:getToolTipFromMap('abmPersonasGrDmcButtonGuardarAgregar', helpMap,'Guardar y Agregar'),	
		            		//text:'Guardar y Agregar',
		            		handler: function() {
		            			//alert(2);
		            			//if(banderaAgregarDomicilio){
		            			var _recs = grillaDomicilios.store.getModifiedRecords();
	            		      	if (_recs.length==0)
								{

				  	  				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe ingresar un registro para realizar esta operaci&oacute;n'));

            	 				}else{
            		 				agregarNuevoDomicilio = true;
	            					guardarDatosDomicilio();
								} 
								/*}else{
									Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe agregar un registro para realizar esta operaci&oacute;n'));
								}*/
		            				
		            				
		            				//agregarNuevoDomicilio = true;
		            				//guardarDatosDomicilio();
		            		}
		            	}, {
   							text:getLabelFromMap('abmPersonasGrDmcButtonBorrar', helpMap,'Eliminar'),
       						tooltip:getToolTipFromMap('abmPersonasGrDmcButtonBorrar', helpMap,'Elimina un domicilio'),
		            		handler: function() {
            					if (grillaDomicilios.getSelectionModel().getSelections().length > 0){
            						if(grillaDomicilios.getSelectionModel().getSelected().get("numOrdenDomicilio")==""){            								
            								grillaDomicilios.store.remove(grillaDomicilios.getSelectionModel().getSelected());
            						}
            						else{
            							Ext.MessageBox.confirm('Confirmar', 'Seguro desea eliminar el domicilio?', 
       									function(btn) {
       											if (btn == "yes") {
       													borrarDomicilio();       													
       											}
       										});
            						}            						
            					}else {
            						Ext.Msg.alert('Error', 'Debe seleccionar un domicilio para eliminar');
            					}
            					
            				}
		            	}, {
   							text:getLabelFromMap('abmPersonasGrDmcButtonRegresar', helpMap,'Regresar'),
       						tooltip:getToolTipFromMap('abmPersonasGrDmcButtonRegresar', helpMap,'Regresar'),	
		            		//text: 'Regresar',
            					/* handler: function() {
            					 				window.location.href = _ACTION_REGRESAR;
            					 		}*/
            					 		
            					 		handler: function() {if(COD==1){
            					 window.location.href = _ACTION_REGRESAR_CLIENTE;
	            								}
	            								else{ window.location.href = _ACTION_REGRESAR;
	            								}
	            								}
		            	}
	            	],
			height:320,
			sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			bbar: new Ext.PagingToolbar({
					pageSize:itemsPerPage,
					store: crearGridDomciliosStore(),
					displayInfo: true
	                //displayMsg: 'Mostrando registros {0} - {1} of {2}',
	                //emptyMsg: "No hay registros para visualizar"
			    })
			});
	
	}
	//createGridDomcilios();
	
	function addGridDomicilioNewRecord () {
		var new_record = new recordDomicilios({
				            codigoPersona: CODIGO_PERSONA,
				        	numOrdenDomicilio: '',
				        	tipoDomicilio: '',
							dsDomicilio: '',
							cdPostal: '',
							numero: '',
							numeroInterno: '',
							codigoPais: '',
							codigoEstado: '',
							codigoMunicipio: '',
							codigoColonia: ''
						});
		grillaDomicilios.stopEditing();
		grillaDomicilios.store.insert(0, new_record);
		grillaDomicilios.startEditing(0, 0);
	}

	function guardarDatosDomicilio () {
		var _params = "";
		
		var recs = grillaDomicilios.store.getModifiedRecords();
		grillaDomicilios.stopEditing();
		for (var i=0; i<recs.length; i++) {
			_params += "datosDomicilio[" + i + "].codigoPersona=" + CODIGO_PERSONA + "&" +
						"&datosDomicilio[" + i + "].numOrdenDomicilio=" + recs[i].get('numOrdenDomicilio') + "&" +
						"&datosDomicilio[" + i + "].tipoDomicilio=" + recs[i].get('tipoDomicilio') + "&" +
						"&datosDomicilio[" + i + "].dsDomicilio=" + recs[i].get('dsDomicilio') + "&" +
						"&datosDomicilio[" + i + "].cdPostal=" + recs[i].get('cdPostal') + "&" +
						"&datosDomicilio[" + i + "].numero=" + recs[i].get('numero') + "&" +
						"&datosDomicilio[" + i + "].numeroInterno=" + recs[i].get('numeroInterno') + "&" +
						"&datosDomicilio[" + i + "].codigoPais=" + recs[i].get('codigoPais') + "&" +
						"&datosDomicilio[" + i + "].codigoEstado=" + recs[i].get('codigoEstado') + "&" +
						"&datosDomicilio[" + i + "].codigoMunicipio=" + recs[i].get('codigoMunicipio') + "&" +
						"&datosDomicilio[" + i + "].codigoColonia=" + recs[i].get('codigoColonia') + "&";
		}
		if (recs.length > 0) {
			execConnection(_ACTION_GUARDAR_DATOS_DOMICILIO, _params, cbkGuardarDomicilio);
			banderaAgregarDomicilio = false;
		}
	}
	function cbkGuardarDomicilio (_success, _message) {
		if (_success) {
			grillaDomicilios.store.commitChanges();
			Ext.Msg.alert('Aviso', _message, function(){
					var _params = {codigoPersona: CODIGO_PERSONA, start: 0, limit: itemsPerPage};
					
					reloadComponentStore(grillaDomicilios, _params, function(success) {if(agregarNuevoDomicilio) addGridDomicilioNewRecord();});
					
			});
		}else {
			Ext.Msg.alert('Error', _message);
		}
	}
	function ValidarEdicion(eventoEdicion) {
		if (eventoEdicion.field == "codigoEstado") {
			var _store = eventoEdicion.store;//eventoEdicion.record.editor.store;
			_store.baseParams = _store.baseParams || {};
			_store.baseParams['codigoPais'] = 'MEX';
			_store.reload({
				params: {codigoPais: 'MEX'}
			});
		}
	}
	function borrarDomicilio () {
		var recs = grillaDomicilios.getSelectionModel().getSelections();
		if (recs.length > 0) {
			var params = {codigoPersona: CODIGO_PERSONA, numOrden: recs[0].get('numOrdenDomicilio')};
			execConnection(_ACTION_BORRAR_DOMICILIO, params, cbkBorrarDomicilio);
		}else {
			Ext.Msg.alert('Error', 'Debe seleccionar un Domicilio');
		}
	}
	function cbkBorrarDomicilio (_success, _messages) {
		if (_success) {
			var _params = {codigoPersona: CODIGO_PERSONA, start: 0, limit: itemsPerPage};
			Ext.Msg.alert('Aviso', _messages, function(){reloadComponentStore(grillaDomicilios, _params);});
		}else {
			Ext.Msg.alert('Error', _messages);
		}
	}
	/************* Fin Grilla de Domicilios **************************/


	/********* Comienzo del grid de Teléfonos *****************************/
		//Crea el Store
		var recordTelefono = new Ext.data.Record.create([
			        {name: 'codigoPersona', type: 'string', mapping: 'codigoPersona'},
			        {name: 'numeroOrdenTel',  type: 'string',  mapping:'numeroOrden'},
			        {name: 'codTipTel',  type: 'string',  mapping:'codTipTel'},
			        {name: 'codArea',  type: 'string',  mapping:'codArea'},
			        {name: 'numTelef',  type: 'string',  mapping:'numTelef'},
			        {name: 'numExtens',  type: 'string',  mapping:'numExtens'}
					]);
		function crearGridTelefonosStore(){
		 			store = new Ext.data.Store({
		    			proxy: new Ext.data.HttpProxy({
						url: _ACTION_OBTENER_TELEFONOS,
						waitMsg: 'Espere por favor....'
		                }),
		                reader: new Ext.data.JsonReader({
		            	root:'datosTelefono',
		            	totalProperty: 'totalCount',
			            successProperty : '@success'
			        },
			        	recordTelefono
			        )
		        });
				return store;
		 	}
		//Fin Crea el Store

		//Combo Tipo de Teléfono
		var comboTipoTelefono = new Ext.form.ComboBox(
			{
				tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
			    store: dsTipoTelefono, 
			    anchor:'100%', 
			    displayField:'descripcion', 
			    valueField: 'codigo', 
			    hiddenName: 'TipoDomicilio',
			    typeAhead: true, 
			    triggerAction: 'all', 
			    lazyRender:   true, 
			    emptyText:'Seleccione Tipo...', 
			    selectOnFocus:true,
			    name: 'codTipTel', 
			    mode:'local',
			    //labelSeparator:'', 
			    fieldLabel: 'Tipo de Entidad'
			 })
		//Fin Combo Tipo de Teléfono
	var grillaTelefonos;
	function createGridTelefonos(){
		//Definición Column Model
	    var cm = new Ext.grid.ColumnModel([
	    		{
	    			dataIndex: 'codigoPersona',
	    			hidden: true
	    		}, {
	    			dataIndex: 'numeroOrdenTel',
	    			hidden: true
	    		}, {
				   	header: getLabelFromMap('cmCodTipTelAbmPersonas',helpMap,'Tipo de Tel&eacute;fono'),
        			tooltip: getToolTipFromMap('cmCodTipTelAbmPersonas', helpMap,'Tipo de Tel&eacute;fono'),	   	
		           	//header: "Tipo de Tel&eacute;fono",
		           	dataIndex: 'codTipTel',
		           	width: 100,
		           	sortable: true,
		           	editor: comboTipoTelefono,
		           	renderer: renderComboEditor(comboTipoTelefono)
	        	},{
				   	header: getLabelFromMap('cmCodAreaAbmPersonas',helpMap,'C&oacute;digo de Area'),
        			tooltip: getToolTipFromMap('cmCodAreaAbmPersonas', helpMap,'C&oacute;digo de Area'),	   	
		           	//header: "C&oacute;digo de Area",
		           	dataIndex: 'codArea',
		           	width: 100,
		           	sortable: true,
		           	editor: new Ext.form.NumberField({name: 'codArea', maxLength: 3})
	           	},{
				   	header: getLabelFromMap('cmNumTelefAbmPersonas',helpMap,'N&uacute;mero'),
        			tooltip: getToolTipFromMap('cmNumTelefAbmPersonas', helpMap,'N&uacute;mero'),	   	
		           	//header: "N&uacute;mero",
		           	dataIndex: 'numTelef',
		           	width: 100,
		           	sortable: true,
		           	editor: new Ext.form.NumberField({name: 'numTelef', maxLength: 15})
	           	},{
				   	header: getLabelFromMap('cmNumExtensAbmPersonas',helpMap,'Extensi&oacute;n'),
        			tooltip: getToolTipFromMap('cmNumExtensAbmPersonas', helpMap,'Extensi&oacute;n'),	   	
		           	//header: "Extensi&oacute;n",
		           	dataIndex: 'numExtens',
		           	width: 100,
		           	sortable: true,
		           	editor: new Ext.form.NumberField({name: 'numExtens', maxLength: 6})
	           	}
	           	]);
	        	
		//Fin Definición Column Model
		grillaTelefonos = new Ext.grid.EditorGridPanel({
	        el:'gridTelefonos',
	        id: 'gridTelefonos',
	        store: crearGridTelefonosStore(),
			border:true,
			bodyStyle:'background: white',
			autoWidth: true,
	        clicksToEdit:1,
	        loadMask: {msg: 'Cargando...', disabled: false},
	        cm: cm,
	        buttonAlign: 'center',
			buttons:[
		        		{
   							text:getLabelFromMap('abmPersonasGrTlfButtonAgregar', helpMap,'Agregar'),
       						tooltip:getToolTipFromMap('abmPersonasGrTlfButtonAgregar', helpMap,'Agregar'),	
		        			//text:'Agregar',
		        			handler: function () {
		        						addNewRecordTelefono();
		        						banderaAgregarTelefono=true;}
		            	},
		            	{
   							text:getLabelFromMap('abmPersonasGrTlfButtonGuardar', helpMap,'Guardar'),
       						tooltip:getToolTipFromMap('abmPersonasGrTlfButtonGuardar', helpMap,'Guardar'),	
		            		//text:'Guardar',
		            		handler: function() {
									//alert(1);
		            		      //if(banderaAgregarTelefono){
		            		      	var _recs = grillaTelefonos.store.getModifiedRecords();
		            		      	if (_recs.length==0)
									{

					  	  				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe ingresar un registro para realizar esta operaci&oacute;n'));

	            	 				}else{
	            		 				agregarNuevoTelefono = false;
		            					guardarTelefonos();
									}
									/*}else{
										Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe agregar un registro para realizar esta operaci&oacute;n'));
									} */		            		
		            				//agregarNuevoTelefono = false;
		            				//guardarTelefonos();
		            		}
		            	},
		            	{
   							text:getLabelFromMap('abmPersonasGrTlfButtonGuardarAgregar', helpMap,'Guardar y Agregar'),
       						tooltip:getToolTipFromMap('abmPersonasGrTlfButtonGuardarAgregar', helpMap,'Guardar y Agregar'),	
		            		//text:'Guardar y Agregar',
		            		handler: function() {
									//alert(2);
									//if(banderaAgregarTelefono){
										var _recs = grillaTelefonos.store.getModifiedRecords();
		            		      		if (_recs.length==0)
										{

					  	  					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe ingresar un registro para realizar esta operaci&oacute;n'));

	            	 					}else{
			            					agregarNuevoTelefono = true;
			            					guardarTelefonos(true);
										} 		            		
									/*}else{
											Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe agregar un registro para realizar esta operaci&oacute;n'));
										}*/
		            				//agregarNuevoTelefono = true;
		            				//guardarTelefonos(true);
		            		}
		            	}, {
   							text:getLabelFromMap('abmPersonasGrTlfButtonBorrar', helpMap,'Eliminar'),
       						tooltip:getToolTipFromMap('abmPersonasGrTlfButtonBorrar', helpMap,'Eliminar'),	
		            		//text: 'Borrar',
		            		handler: function () {
            						if (grillaTelefonos.getSelectionModel().getSelections().length > 0) {
            							if(grillaTelefonos.getSelectionModel().getSelected().get("numeroOrdenTel")==""){
            								grillaTelefonos.store.remove(grillaTelefonos.getSelectionModel().getSelected());
            							}else{
            								Ext.MessageBox.confirm('Confirmaci&oacute;n', 'Seguro desea eliminar el tel&eacute;fono seleccionado?', 
   											function(btn){
   												if (btn == "yes") {
   													borrarTelefono();
   												}
   											});
            							}            							
            						}else {
            							Ext.Msg.alert('Error', 'Debe seleccionar un tel&eacute;fono para eliminar');
            						}
            				}
		            	}, {
   							text:getLabelFromMap('abmPersonasGrTlfButtonRegresar', helpMap,'Regresar'),
       						tooltip:getToolTipFromMap('abmPersonasGrTlfButtonRegresar', helpMap,'Regresar'),	
		            		text: 'Regresar',
            					/* handler: function() {
            					 				window.location.href = _ACTION_REGRESAR;
            					 		}*/
            					 		
            					 		handler: function() {if(COD==1){
            					 window.location.href = _ACTION_REGRESAR_CLIENTE;
	            								}
	            								else{ window.location.href = _ACTION_REGRESAR;
	            								}
	            								}
		            	}
	            	],
	    	width:500,
	    	frame:true,
			height:320,
			sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			bbar: new Ext.PagingToolbar({
					pageSize:itemsPerPage,
					store: store,
					displayInfo: true/*,
	                displayMsg: 'Mostrando registros {0} - {1} of {2}',
	                emptyMsg: "No hay registros para visualizar"*/
			    })
			});
	
	}

	function addNewRecordTelefono() {
		var new_record = new recordTelefono({
					        codigoPersona: CODIGO_PERSONA,
					        numeroOrdenTel: '',
					        codTipTel: '',
					        codArea: '',
					        numTelef: '',
					        numExtens: ''
						});
		grillaTelefonos.stopEditing();
		grillaTelefonos.store.insert(0, new_record);
		grillaTelefonos.startEditing(0, 0);
	}
	function guardarTelefonos(agregarNuevo) {
		var _params = "";
		var recs = grillaTelefonos.store.getModifiedRecords();
		grillaTelefonos.stopEditing();
		for (var i=0; i<recs.length; i++) {
			_params += "datosTelefono[" + i + "].codigoPersona=" + CODIGO_PERSONA + "&" +
						"&datosTelefono[" + i + "].numeroOrden=" + recs[i].get('numeroOrdenTel') + "&" +
						"&datosTelefono[" + i + "].codTipTel=" + recs[i].get('codTipTel') + "&" +
						"&datosTelefono[" + i + "].codArea=" + recs[i].get('codArea') + "&" +
						"&datosTelefono[" + i + "].numTelef=" + recs[i].get('numTelef') + "&" +
						"&datosTelefono[" + i + "].numExtens=" + recs[i].get('numExtens') + "&" ;
		}
		if (recs.length > 0) {
			execConnection(_ACTION_GUARDAR_TELEFONOS, _params, cbkGuardarTelefonos);
			banderaAgregarTelefono = false;
		}
		
	}
	function cbkGuardarTelefonos(_success, _messages) {
		if (_success) {
			var _params = {codigoPersona: CODIGO_PERSONA, start: 0, limit: itemsPerPage};
			grillaTelefonos.store.commitChanges();
			Ext.Msg.alert('Aviso', _messages, reloadComponentStore(grillaTelefonos, _params, function(){if (agregarNuevoTelefono) addNewRecordTelefono();}));
		}else {
			Ext.Msg.alert('Error', _messages);
		}
	}
	function borrarTelefono () {
		var recs = grillaTelefonos.getSelectionModel().getSelections();
		if (recs.length > 0) {
			var params = {codigoPersona: CODIGO_PERSONA, numOrden: recs[0].get('numeroOrdenTel')}			
			execConnection(_ACTION_BORRAR_TELEFONO, params, cbkBorrarTelefono);
		}else {
			Ext.Msg.alert('Error', 'Debe seleccionar un teléfono');
		}
	}
	function cbkBorrarTelefono (_success, _message) {
		if (_success) {
			var _params = {codigoPersona: CODIGO_PERSONA, start: 0, limit: itemsPerPage};
			Ext.Msg.alert('Aviso', _message, function() {reloadComponentStore(grillaTelefonos, _params);});
		}else {
			Ext.Msg.alert('Error', _message);
		}
	}
	/************* Fin Grilla de Teléfonos **************************/


	/********* Comienzo del grid de Corporativo *****************************/
		//Crea el Store
		function formatDate(dateVal){
            return (dateVal && dateVal.format) ? dateVal.dateFormat('d/m/Y') : 'Not Available';
        }
		var recordCorporativo = new Ext.data.Record.create ([
			        {name: 'cdElemen', type: 'string', mapping: 'cdElemen'},
			        {name: 'cdGrupoPer',  type: 'string',  mapping:'cdGrupoPer'},
			        {name: 'cdStatus',  type: 'string',  mapping:'cdStatus'},
			        {name: 'dsElemen',  type: 'string',  mapping:'dsElemen'},
			        {name: 'dsGrupo',  type: 'string',  mapping:'dsGrupo'},
			        {name: 'dsStatus',  type: 'string',  mapping:'dsStatus'},
			        //{name: 'feFin',  type: 'date',  mapping:'feFin', dateFormat: 'd/m/Y'},
			        {name: 'feFin',  type: 'string',  mapping:'feFin', dateFormat: 'd/m/Y'},
			        //{name: 'feInicio',  type: 'date',  mapping:'feInicio', dateFormat: 'd/m/Y'}
			        {name: 'feInicio',  type: 'string',  mapping:'feInicio', dateFormat: 'd/m/Y'},
			        {name: 'nmNomina', type: 'string', mapping: 'nmNomina'}
			        
					])
		function crearGridCorporativoStore(){
		 			store = new Ext.data.Store({
		    			proxy: new Ext.data.HttpProxy({
						url: _ACTION_OBTENER_CORPORATIVO,
						waitMsg: 'Espere por favor....'
		                }),
		                reader: new Ext.data.JsonReader({
		            	root:'datosCorporativo',
		            	totalProperty: 'totalCount',
			            successProperty : '@success'
			        },
			        recordCorporativo
			        )
		        });
				return store;
		 	}

		//Fin Crea el Store

		//Comienzo Combos Corporativo
		var comboGrupoCorporativo = new Ext.form.ComboBox(
			{
				tpl: '<tpl for="."><div ext:qtip="{id}.{texto}" class="x-combo-list-item">{texto}</div></tpl>',
			    store: dsGrupoCorporativo, 
			    anchor:'100%', 
			    displayField:'texto', 
			    valueField: 'id', 
			    hiddenName: 'GrupoCorporativo',
			    typeAhead: true, 
			    triggerAction: 'all', 
			    lazyRender:   true, 
			    emptyText:'Seleccione Grupo...', 
			    selectOnFocus:true,
			    forceSelection:true,
			    mode:'local',
			    name: 'codGrupoCorporativo', 
			    //allowBlank: false,
			    //labelSeparator:'', 
			    fieldLabel: 'Grupo',
			    listeners: {
     					focus: function () {
                         		if (!this.list) {
                         			this.clearValue();
                         			this.setValue("");
                         			this.setRawValue("");
                         		}
                         		else
                         		{
                         			if (this.getRawValue().indexOf("&nbsp;")){
                         				this.clearValue();
                         				this.setValue("");
                         				this.setRawValue("");
	                         		}
                         		} 
     							this.store.reload({
              						params: {cdElemento: grillaCorporativo.getSelectionModel().getSelected().get('cdElemen')},
     								success: function () {
     											this.expand();
     								},
     								failure: function () {
    												combo.store.removeAll();
    												combo.setValue('');
    												combo.setRawValue('');
     										}
						});
  					},
   					expand: function (combo, record){
   						combo.store.reload({
           						params: {cdElemento: grillaCorporativo.getSelectionModel().getSelected().get('cdElemen')},
   								failure: function () {
   												combo.store.removeAll();
   												combo.setValue('');
   												combo.setRawValue('');
   										}
   						});
   					},
	      			collapse: function (record) {
	      				grillaCorporativo.getSelectionModel().getSelected().set('cdGrupoPer', record.value);
	      			}
			    }
			 });
		var comboClientesCorporativos = new Ext.form.ComboBox(
			{
				tpl: '<tpl for="."><div ext:qtip="{cdElemento}.{dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
			    store: dsClientesCorporativos, 
			    anchor:'100%', 
			    displayField:'dsElemen', 
			    valueField: 'cdElemento', 
			    hiddenName: 'clientesCorpo',
			    typeAhead: true, 
			    triggerAction: 'all', 
			    lazyRender:   true, 
			    emptyText:'Seleccione Corp...', 
			    mode:'local',
			    selectOnFocus:true,
			    forceSelection:true,
			    name: 'codEstadoCorpo', 
			    //labelSeparator:'', 
			    fieldLabel: 'Corporativo',
			    onSelect: function(record) {
			                    				//comboGrupoCorporativo.store.removeAll();
			                    				//comboGrupoCorporativo.clearValue();
			                    				this.setValue(record.get('cdElemento'));
			                    				this.collapse();
			                    				comboGrupoCorporativo.store.removeAll();
			                    				grillaCorporativo.getSelectionModel().getSelected().set('cdGrupoPer', '');
			                    				comboGrupoCorporativo.store.reload({
			                    						params: {cdElemento: record.get('cdElemento')}
			                    				});
			                    				
			                    		}
			                    		});
		var comboEstadoCorporativo = new Ext.form.ComboBox(
		{
			tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
			store: dsEstadoCorporativo, 
			anchor:'100%', 
			displayField:'descripcion', 
			valueField: 'codigo', 
			hiddenName: 'EstadoCorporativo',
			typeAhead: true, 
			triggerAction: 'all', 
			lazyRender:   true, 
			emptyText:'Seleccione Estado...', 
			selectOnFocus:true,
			forceSelection:true,
			name: 'codEstadoCorpo', 
			mode:'local',
			labelSeparator:'', 
			fieldLabel: 'Estado',
			listeners: {
				focus: function () {
                         		if (!this.list) {
                         			this.clearValue();
                         			this.setValue("");
                         			this.setRawValue("");
                         		}
                         		else
                         		{
                         			if (this.getRawValue().indexOf("&nbsp;")){
                         				this.clearValue();
                         				this.setValue("");
                         				this.setRawValue("");
	                         		}
                         		} 
					var valor = this.getValue();
					if (valor.toLowerCase().indexOf("<div") != -1) {
						this.setRawValue('');
						this.setValue('');
					}
				}
			}
		});
		//Fin Comienzo Combos Corporativo
	var grillaCorporativo;
	function createGridCorporativo(){
		//Definición Column Model
	    var cm = new Ext.grid.ColumnModel([
	    		{
	    			header: 'cdElemen',
	    			dataIndex: 'cdElemen',
	    			hidden: true
	    		},
				{
				   	header: getLabelFromMap('cmCdElemenAbmPersonas',helpMap,'Nivel'),//Estructura
        			tooltip: getToolTipFromMap('cmCdElemenAbmPersonas', helpMap,'Nivel'),	   	
		           	//header: "Corporativo",
		           	dataIndex: 'cdElemen',
		           	width: 100,
		           	sortable: true,
		           	editor: comboClientesCorporativos,
		           	renderer: renderComboEditor(comboClientesCorporativos)
	           	},
				{
				   	header: getLabelFromMap('cmCdGrupoPerAbmPersonas',helpMap,'Empresa / Grupo'),
        			tooltip: getToolTipFromMap('cmCdGrupoPerAbmPersonas', helpMap,'Empresa / Grupo'),	   	
		           	//header: "Grupo",
		           	//dataIndex: 'cdGrupoPer',
		           	dataIndex: 'dsGrupo',
		           	width: 100,
		           	sortable: true,
		           	editor: comboGrupoCorporativo,
		           	renderer: renderComboEditor(comboGrupoCorporativo)
	        	},
	        	{
				   	header: getLabelFromMap('cmFeInicioAbmPersonas',helpMap,'Fecha de ingreso'),
        			tooltip: getToolTipFromMap('cmFeInicioAbmPersonas', helpMap,'Fecha de ingreso'),	   	
		           	//header: "Fecha Alta",
		           	dataIndex: 'feInicio',
		           	width: 100,
		           	sortable: true,
		           	renderer: formatDate(),
		           	format: 'd/m/Y',
		           	editor: new Ext.form.DateField({name: 'feInicio', format: 'd/m/Y'}),
		           	renderer: function(val) {
		           			//Chequea que val != null y que es una fecha válida para JS.
		           			if (val && val instanceof Date) {
		           				return val.format ('d/m/Y');
		           			}
		           			return val;
		           	}
	           	},{
				   	header: getLabelFromMap('cmFeFinAbmPersonas',helpMap,'Fecha Baja'),
        			tooltip: getToolTipFromMap('cmFeFinAbmPersonas', helpMap,'Fecha Baja'),	   	
		           	//header: "Fecha Baja",
		           	dataIndex: 'feFin',
		           	sortable: true,
		           	width: 100,
		           	renderer: formatDate(),
		           	format: 'd/m/Y',
		           	editor: new Ext.form.DateField({name: 'feFin', format: 'd/m/Y'}),
		           	renderer: function(val) {
		           			//Chequea que val != null y que es una fecha válida para JS.
		           			if (val && val instanceof Date) {
		           				return val.format ('d/m/Y');
		           			}
		           			return val;
		           	}
		           	//renderer: Ext.util.Format.dateRenderer('d/m/Y')
	           	},{
				   	header: getLabelFromMap('cmCdStatusAbmPersonas',helpMap,'Estado'),
        			tooltip: getToolTipFromMap('cmCdStatusAbmPersonas', helpMap,'Estado'),	   	
	           		//header: 'Estado',
	           		dataIndex: 'cdStatus',
	           		width: 80,
		           	sortable: true,
		           	editor: comboEstadoCorporativo,
		           	renderer: renderComboEditor(comboEstadoCorporativo)
	           	},
	           	 {
			        header: getLabelFromMap('confMotCancCmClav',helpMap,'Nomina'),
			        tooltip: getToolTipFromMap('confMotCancCmClav',helpMap,'N&uacute;mero de Nomina'),
			        dataIndex: 'nmNomina',
			        width: 80,
			        sortable: true,
			        resizable: true ,
			        editor: new Ext.form.TextField({
					           			//	allowBlank: true,             //modificado
					           				id: 'nmNomina'
					           		})                                     
			        }
	           	]);
	           	
		//Fin Definición Column Model
		grillaCorporativo = new Ext.grid.EditorGridPanel({
	        el:'gridCorporativo',
	        id: 'gridCorporativo',
	        store: crearGridCorporativoStore(),
			border:true,
			autoWidth: true,
	        clicksToEdit:1,
	        cm: cm,
	        buttonAlign: 'center',
	        loadMask: {msg: 'Cargando...', disabled: false},
			buttons:[
		        		{
   							text:getLabelFromMap('abmPersonasGrCptButtonAgregar', helpMap,'Agregar'),
       						tooltip:getToolTipFromMap('abmPersonasGrCptButtonAgregar', helpMap,'Agregar'),	
		        			//text:'Agregar',
		        			handler: function () {
		        						addNewRecordCorporativo();
		        						banderaAgregarCorporativo = true;
		        			}
		            	},
		            	{
   							text:getLabelFromMap('abmPersonasGrCptButtonGuardar', helpMap,'Guardar'),
       						tooltip:getToolTipFromMap('abmPersonasGrCptButtonGuardar', helpMap,'Guardar'),	
		            		//text:'Guardar',
		            		handler: function () {
									//alert(1);
		            		     //if(banderaAgregarCorporativo){ 	
			            		   		var _recs = grillaCorporativo.store.getModifiedRecords();
		            		      		if (_recs.length==0)
											{

						  	  					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe ingresar un registro para realizar esta operaci&oacute;n'));
	
		    	        	 				}else{
		            						agregarNuevoCorporativo = false;
		            					
		            						//if(getSelectedKey(grillaCorporativo, "cdGrupoPer") != ""){
		            							guardarCorporativo();
		            						//}else{
		            							//Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe ingresar un valor en el campo Empresa / Grupo '));
		            						//}
										} 		            		


		            					//agregarNuevoCorporativo = false;
		            					//guardarCorporativo ();
		            					
		            				//}else{
		            				//	Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe agregar un registro para realizar esta operaci&oacute;n'));
		            				//}
		            		}
		            	},
		            	{
   							text:getLabelFromMap('abmPersonasGrCptButtonGuardarAgregar', helpMap,'Guardar y Agregar'),
       						tooltip:getToolTipFromMap('abmPersonasGrCptButtonGuardarAgregar', helpMap,'Guardar y Agregar'),	
		            		//text:'Guardar y Agregar',
		            		handler: function () {
									//alert(2);
		            		   //if(banderaAgregarCorporativo){ 
		            		   		var _recs = grillaCorporativo.store.getModifiedRecords();
		            		      	if (_recs.length==0)
										{

					  	  					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe ingresar un registro para realizar esta operaci&oacute;n'));

	            	 					}else{
		            						agregarNuevoCorporativo = true;
		            						guardarCorporativo();
										} 		            		


		            						//agregarNuevoCorporativo = true;
		            						//guardarCorporativo();
		            					//}else{
		            					//	Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe agregar un registro para realizar esta operaci&oacute;n'));
		            					//}
		            		}
		            	}, {
   							text:getLabelFromMap('abmPersonasGrCptButtonRegresar', helpMap,'Regresar'),
       						tooltip:getToolTipFromMap('abmPersonasGrCptButtonRegresar', helpMap,'Regresar'),	
		            		//text: 'Regresar',
            					/* handler: function() {
            					 				window.location.href = _ACTION_REGRESAR;
            					 		}*/
            					 		
            					 		handler: function() {if(COD==1){
            					 window.location.href = _ACTION_REGRESAR_CLIENTE;
	            								}
	            								else{ window.location.href = _ACTION_REGRESAR;
	            								}
	            								}
		            	}
	            	],
	    	width:500,
	    	frame:true,
			height:320,
			//renderTo:document.body,
			sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			//viewConfig: {autoFill: true,forceFit:true},
			bbar: new Ext.PagingToolbar({
					pageSize:itemsPerPage,
					store: store,
					displayInfo: true/*,
	                displayMsg: 'Mostrando registros {0} - {1} of {2}',
	                emptyMsg: "No hay registros para visualizar"*/
			    })
			});
	
	    //grilla.render()
	    grillaCorporativo.on('beforeedit', function(editEvent) {
	    		if (editEvent.field != "cdElemen") {
	    			if (editEvent.record.get("cdElemen") == "") {
	    				Ext.Msg.alert('Error', 'Primero seleccione el Corporativo');
	    				editEvent.cancel = true;
	    			}
	    		}
	    });
	}
	function addNewRecordCorporativo () {
		var new_record = new recordCorporativo({
			        cdElemen: '',
			        codGrupoCorporativo: '',
			        codEstadoCorpo: '',
			        dsElemen: '',
			        dsGrupo: '',
			        dsStatus: '',
			        feFin: '',
			        feInicio: '',
			        nmNomina:''
			       });
		grillaCorporativo.stopEditing();
		grillaCorporativo.store.insert(0, new_record);
		grillaCorporativo.startEditing(0, 0);
        
	}
	function guardarCorporativo () {
		var _params = "";
		var recs = grillaCorporativo.store.getModifiedRecords();
		grillaCorporativo.stopEditing();
		for (var i=0; i<recs.length; i++) {
			//alert("Grupo: " + recs[i].get("cdGrupoPer") + "\nElemen: " + recs[i].get("cdElemen"));	
			_params += "datosCorporativo[" + i + "].cdElemen=" + recs[i].get("cdElemen") + "&" +
						"datosCorporativo[" + i + "].codigoPersona=" + CODIGO_PERSONA + "&" +  
						"datosCorporativo[" + i + "].cdGrupoPer=" + recs[i].get("cdGrupoPer") + "&" + 
						"datosCorporativo[" + i + "].cdStatus=" + ((recs[i].get("cdStatus"))?recs[i].get("cdStatus"):"") + "&" + 
						"datosCorporativo[" + i + "].feInicio=" + Ext.util.Format.date(recs[i].get("feInicio"), 'd/m/Y') + "&" + 
						"datosCorporativo[" + i + "].feFin=" + Ext.util.Format.date(recs[i].get("feFin"), 'd/m/Y') + "&" +
						"datosCorporativo[" + i + "].nmNomina=" + ((recs[i].get('nmNomina'))?recs[i].get('nmNomina'):'') + "&"; //Ext.getCmp("nmNomina").getValue() + "&";

		}
		if (recs.length > 0) {
			execConnection(_ACTION_GUARDAR_DATOS_CORPORATIVO, _params, cbkGuardarCorporativo);
			banderaAgregarCorporativo = false;
		}
	}
	function cbkGuardarCorporativo (_success, _message) {
		if (_success) {
			grillaCorporativo.store.commitChanges();
			var _params = {codigoPersona: CODIGO_PERSONA, start: 0, limit: itemsPerPage};
			Ext.Msg.alert('Aviso', _message, function(){reloadComponentStore(grillaCorporativo, _params, function(){ if (agregarNuevoCorporativo)addNewRecordCorporativo(); } )});
		}else {
			Ext.Msg.alert('Error', _message);
		}
	}
	/************* Fin Grilla de Corporativo **************************/

	/*********** Comienza el form de Relaciones ********************/

	var comboTipoRelacion = new Ext.form.ComboBox({tpl: '<tpl for="."><div ext:qtip="{id}.{texto}" class="x-combo-list-item">{texto}</div></tpl>',
		                    		store: dsTipoRelacionPersona, anchor:'100%', displayField:'texto', valueField: 'id', hiddenName: 'cdTipoRelacion',
		                    		typeAhead: true, triggerAction: 'all', lazyRender:   true, emptyText:'Seleccione Tipo...', selectOnFocus:true,forceSelection:true,
		                    		name: 'codTipoRelacion', labelSeparator:'', allowBlank:false , fieldLabel: 'Tipo Relacion', mode: 'local'});
	var comboFuncionEnPoliza = new Ext.form.ComboBox({tpl: '<tpl for="."><div ext:qtip="{id}.{texto}" class="x-combo-list-item">{texto}</div></tpl>',
		                    		store: dsFuncionEnPoliza, anchor:'100%', displayField:'texto', valueField: 'id', hiddenName: 'cdFuncionEnPoliza',
		                    		typeAhead: true, triggerAction: 'all', lazyRender:   true, emptyText:'Seleccione Funcion...', selectOnFocus:true,forceSelection:true,
		                    		name: 'codFuncionEnPoliza', labelSeparator:'', fieldLabel: 'Funcion en Poliza', mode: 'local'});
	var comboClientesRelaciones = new Ext.form.ComboBox({tpl: '<tpl for="."><div ext:qtip="{cdPerson}.{cdPerRel}" class="x-combo-list-item">{cdPerRel}</div></tpl>',
		                    		store: dsClientesCorporativosRelaciones, anchor:'100%',allowBlank:false ,displayField:'cdPerRel', valueField: 'cdPerson', hiddenName: 'cdClientesRelacion',
		                    		typeAhead: true, triggerAction: 'all', lazyRender:   true, emptyText:'Seleccione Corp...', selectOnFocus:true,forceSelection:true,
		                    		name: 'codClientesRelacion', labelSeparator:'', fieldLabel: 'Relacion', mode: 'local'});

	var grillaRelaciones;
		var recordRelaciones = new Ext.data.Record.create ([
			        {name: 'cdPerson', type: 'string', mapping: 'cdPerson'},
			        {name: 'cdPerRel',  type: 'string',  mapping:'cdPerRel'},
			        {name: 'cdRol',  type: 'string',  mapping:'cdRol'},
			        {name: 'cdRelaci',  type: 'string',  mapping:'cdRelaci'},
			        {name: 'dsNombre',  type: 'string',  mapping:'dsNombre'},
			        {name: 'descrip',  type: 'string',  mapping:'descrip'},
			        {name: 'dsRol',  type: 'string',  mapping:'dsRol'},
			        {name: 'cdAccion', type: 'string', mapping: 'cdAccion'}
					])
	function crearGridRelacionesStore(){
	 			var	store = new Ext.data.Store({
	    			proxy: new Ext.data.HttpProxy({
					url: _ACTION_OBTENER_RELACIONES
	                }),
	                reader: new Ext.data.JsonReader({
	            	root:'datosRelaciones',
	            	totalProperty: 'totalCount',
		            successProperty : '@success'
		        },
		        recordRelaciones
		        )
	        });
			return store;
	 	}
	function createGridRelaciones(){
		//Definición Column Model
	    var cmRelaciones = new Ext.grid.ColumnModel([
	    		{
		           	header: getLabelFromMap('cmTipoRelacionAbmPersonas',helpMap,"Tipo de  Relaci&oacute;n"),
		           	tooltip: getToolTipFromMap('cmTipoRelacionAbmPersonas', helpMap,'Tipo de  Relaci&oacute;n'),	
		           	dataIndex: 'cdRelaci',
		           	width: 160,
		           	sortable: true,
		           	editor: comboTipoRelacion,
		           	renderer: renderComboEditor(comboTipoRelacion)
	        	},{
		           	header: getLabelFromMap('cmFuncionRelacionAbmPersonas',helpMap,"Rol"),       //Funci&oacute;n en la p&oacute;liza"),
		           	tooltip: getToolTipFromMap('cmFuncionRelacionAbmPersonas', helpMap,'Rol'),   //Función en la póliza	
		           	dataIndex: 'cdRol',
		           	width: 160,
		           	sortable: true,
		           	editor: comboFuncionEnPoliza,
		           	renderer: renderComboEditor(comboFuncionEnPoliza)
	           	},{
		           	header: getLabelFromMap('cmPersonaRelacionAbmPersonas',helpMap,"Persona relacionada"),
		           	tooltip: getToolTipFromMap('cmPersonaRelacionAbmPersonas', helpMap,'Persona relacionada'),	
		           	dataIndex: 'cdPerRel',
		           	width: 160,
		           	sortable: true,
		           	editor: comboClientesRelaciones,
		           	renderer: renderComboEditor(comboClientesRelaciones)
	           	},
	           	{
	           		header: '',
	           		dataIndex: 'cdPerson',
	           		hidden: true
	           	},
	           	{
	           		header: '',
	           		dataIndex: 'dsNombre',
	           		hidden: true
	           	},
	           	{
	           		header: '',
	           		dataIndex: 'descrip',
	           		hidden: true
	           	},
	           	{
	           		header: '',
	           		dataIndex: 'dsRol',
	           		hidden: true
	           	},
	           	{
	           		header: '',
	           		dataIndex: 'cdAccion',
	           		hidden: true
	           	}
	           	]);
	        	
		//Fin Definición Column Model
		grillaRelaciones = new Ext.grid.EditorGridPanel({
	        el:'gridRelaciones',
	        id: 'gridRelaciones',
	        store: crearGridRelacionesStore(),
			border:true,
			autoWidth: true,
	        clicksToEdit:1,
	        //loadMask: {msg: 'Cargando...', disabled: false},
	        cm: cmRelaciones,
	        buttonAlign: 'center',
			buttons:[
		        		{
   							text:getLabelFromMap('abmPersonasRelacionesButtonAgregar', helpMap,'Agregar'),
       						tooltip:getToolTipFromMap('abmPersonasRelacionesButtonAgregar', helpMap,'Agregar'),	
		        			handler: function () {
		        			                      addNewRecordRelaciones();
		        								  banderaAgregarRelacion = true;
		        								 }
		            	},
		            	{
   							text:getLabelFromMap('abmPersonasRelacionesButtonGuardar', helpMap,'Guardar'),
       						tooltip:getToolTipFromMap('abmPersonasRelacionesButtonGuardar', helpMap,'Guardar'),	
		            		handler: function() {

		            			//if (banderaAgregarRelacion){
		            				var _recs = grillaRelaciones.store.getModifiedRecords();
		            				if (_recs.length==0)
									{
					  	  				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe ingresar un registro para realizar esta operaci&oacute;n'));
	            	 				}else{
		            					agregarNuevaRelacion = false;
		            					guardarRelaciones();
		            							            					
		            				} 	
		            		//		agregarNuevaRelacion = false;
		            		//		guardarRelaciones();

		            				/*}else{
		            					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe agregar un registro para realizar esta operaci&oacute;n'));
		            				}*/
		            				
		            				}
		            	},
		            	{
   							text:getLabelFromMap('abmPersonasRelacionesButtonGuardarAgregar', helpMap,'Guardar y Agregar'),
       						tooltip:getToolTipFromMap('abmPersonasRelacionesButtonGuardarAgregar', helpMap,'Guardar y Agregar'),	
		            		handler: function() {
		            			
		            			//if (banderaAgregarRelacion){
		            				var _recs = grillaRelaciones.store.getModifiedRecords();
		            				if (_recs.length==0)
										{
					  	  					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe ingresar un registro para realizar esta operaci&oacute;n'));
	            	 					}else{
		            						agregarNuevaRelacion = true;
		            						guardarRelaciones(true);
		            				} 
		            			/*}else{
		            					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe agregar un registro para realizar esta operaci&oacute;n'));
		            				
		            			}*/
		            			//	agregarNuevaRelacion = true;
		            			//	guardarRelaciones(true);
		            		}
		            	}, {
   							text:getLabelFromMap('abmPersonasRelacionesButtonRegresar', helpMap,'Regresar'),
       						tooltip:getToolTipFromMap('abmPersonasRelacionesButtonRegresar', helpMap,'Regresar'),	
            					/* handler: function() {
            					 				window.location.href = _ACTION_REGRESAR;
            					 		}*/
            					 		
            					 		handler: function() {if(COD==1){
            					 window.location.href = _ACTION_REGRESAR_CLIENTE;
	            								}
	            								else{ window.location.href = _ACTION_REGRESAR;
	            								}
	            								}
		            	}
	            	],
	    	width:500,
	    	frame:true,
			height:320,
			sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			bbar: new Ext.PagingToolbar({
					pageSize:itemsPerPage,
					store: store,
					displayInfo: true/*,
	                displayMsg: 'Mostrando registros {0} - {1} of {2}',
	                emptyMsg: "No hay registros para visualizar"*/
			    })
			});
	
	}
	function addNewRecordRelaciones () {
		var new_record = new recordRelaciones({
			        cdPerson: '',
			        cdPerRel: '',
			        cdRol: '',
			        cdRelaci: '',
			        dsNombre: '',
			        descrip: '',
			        dsRol: '',
			        cdAccion: 'I'
			       });
		grillaRelaciones.stopEditing();
		grillaRelaciones.store.insert(0, new_record);
		grillaRelaciones.startEditing(0, 0);
	}
	function guardarRelaciones () {
		var _params = "";
		var recs = grillaRelaciones.store.getModifiedRecords();
		grillaRelaciones.stopEditing();
		
		//if(getSelectedKey(grillaRelaciones, "cdRelaci")!= ""){
		
			//if(getSelectedKey(grillaRelaciones, "cdPerRel")!= ""){
			for (var i=0; i<recs.length; i++) {
			        if(recs[i].get("cdRelaci")!= ""){
		
			             if(recs[i].get("cdPerRel")!= ""){
			
				
				                	_params += "datosRelaciones[" + i + "].cdPerson=" + CODIGO_PERSONA + "&" +
				                				"datosRelaciones[" + i + "].cdPerRel=" + recs[i].get("cdPerRel") + "&" +  
				 								"datosRelaciones[" + i + "].cdRol=" + recs[i].get("cdRol") + "&" +
												"datosRelaciones[" + i + "].cdRelaci=" + recs[i].get("cdRelaci") + "&" +
												"datosRelaciones[" + i + "].dsNombre=" + recs[i].get("dsNombre") + "&" +
												"datosRelaciones[" + i + "].descrip=" + recs[i].get("descrip") + "&" +
												"datosRelaciones[" + i + "].dsRol=" + recs[i].get("dsRol") + "&" +
													"datosRelaciones[" + i + "].cdAccion=" + ((recs[i].get("cdAccion") != "")?recs[i].get("cdAccion"):"U") + "&" ;
									
									if (recs.length > 0) {
										execConnection(_ACTION_GUARDAR_DATOS_RELACIONES, _params, cbkGuardarRelaciones);
											banderaAgregarRelacion = false;
						             }
				
			          }else{
							Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe ingresar un valor en el campo Persona Relacionada'));
				 			}
		         }else{
		                  Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe ingresar un valor en el campo Tipo de  Relaci&oacute;n'));
		        }
		} 
	}
	function cbkGuardarRelaciones (_success, _message) {
		if (_success) {
			grillaRelaciones.store.commitChanges();
			var _params = {codigoPersona: CODIGO_PERSONA, start: 0, limit: itemsPerPage};
			Ext.Msg.alert('Aviso', _message, function(){reloadComponentStore(grillaRelaciones, _params, function(){ grillaRelaciones.getView().refresh(); if (agregarNuevaRelacion)addNewRecordRelaciones(); } )});
		}else {
			Ext.Msg.alert('Error', _message);
		}
	}
	/*********** Fin el form de Relaciones ********************/

	/*********** Form Crear Usuarios *************************/
	var readerCrearUsuario = new Ext.data.JsonReader( {
            root : 'MUsuarioClave',
            totalProperty: 'totalCount',
            successProperty : '@success'
            },
            [
            { name: 'cdPerson', mapping : 'cdPerson', type : 'string'},
            { name: 'cdUsuariUsrId', mapping : 'cdUsuari', type : 'string' },
            { name: 'registroGridUsrId', mapping : 'registGrid',  type : 'string'}
            ]
    );
            
    var storeCrearUsuario = new Ext.data.Store ({
	            proxy: new Ext.data.HttpProxy({
	                url: _ACTION_OBTENER_USUARIOS_PERSONAS
	            }),
           	reader: readerCrearUsuario
    });
           
        var el_formUsuarios = new Ext.FormPanel({
        	el: 'formUsuarios',
			id: 'el_formUsuarios',
			labelAlign:'right',
            frame : true,
            width : 600,
            height: 320,
            url: _ACTION_OBTENER_USUARIOS_PERSONAS,
   	        reader: readerCrearUsuario,
            bodyStyle:'background: white',
            items: [
				{
				layout:'form',
				border: false,
				items:[
						{
						labelWidth: 140,
						layout: 'form',
						frame:true,
						baseCls: '',
						items: [
								{
								html: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Crear Usuario</span><br>',
								layout:'column',
								border:false,
								columnWidth: 1,
								items:[
									{
									columnWidth:.6,
									layout: 'form',
									border: false,
									items:[
											{
	            					   		xtype: 'textfield',
	     									fieldLabel: getLabelFromMap('cdUsuariUsrId', helpMap,'Clave'), 
		     								tooltip: getToolTipFromMap('cdUsuariUsrId', helpMap,'Clave'),
		     								hasHelpIcon:getHelpIconFromMap('cdUsuariUsrId',helpMap),
											Ayuda: getHelpTextFromMap('cdUsuariUsrId',helpMap),
											disabled: true,           
											blankText: 'Este campo es requerido',										
											id: 'cdUsuariUsrId',
											allowBlank: false,
											name: 'cdUsuariUsrId',
											//anchor: '100%',
											width:240
											},
											{
	            					   		xtype: 'textfield',
	     									fieldLabel: getLabelFromMap('registroGridUsrId', helpMap,'Registro Grid'), 
		     								tooltip: getToolTipFromMap('registroGridUsrId', helpMap,'Registro Grid'),
		     								hasHelpIcon:getHelpIconFromMap('registroGridUsrId',helpMap),
											Ayuda: getHelpTextFromMap('registroGridUsrId',helpMap), 
											//disabled: true,          
											blankText: 'Este campo es requerido',										
											id: 'registroGridUsrId',
											name: 'registroGridUsrId',
											allowBlank: false,
											//anchor: '100%',
											width:240																		
											} 
										   ]
										},
										{
											columnWidth:.1,
											layout: 'form'
										}
									]
								},
								{
									html: '<span class="x-form-item" style="font-weight:bold"></span><br>'
								}
							]
						}
					]
				}
			  ],
		buttonAlign: 'center',
        buttons: [
        		{
          		id:'abmPersonasDtUsrsButtonGuardar',
				text:getLabelFromMap('abmPersonasDtUsrsButtonGuardar', helpMap,'Guardar'),
				tooltip:getToolTipFromMap('abmPersonasDtUsrsButtonGuardar', helpMap,'Guardar'),
				handler: function () {
							 agregarNuevaUsuario = false;
							 if (el_formUsuarios.form.isValid()) {
							 	guardarCrearUsuarioPestanha();
							 }
							 else {
								Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
							 }
						}
				},
				{
				id: 'abmPersonasDtUsrsButtonAsociar',
				text:getLabelFromMap('abmPersonasDtUsrsButtonAsociar', helpMap,'Asociar'),
				tooltip:getToolTipFromMap('abmPersonasDtUsrsButtonAsociar', helpMap,'Asociar'),	
				handler: function () {
							buscarUsrPrsn();
						}
				},
				{
					id:'abmPersonasDtUsrsButtonRegresar',
					text:getLabelFromMap('abmPersonasDtUsrsButtonRegresar', helpMap,'Regresar'),
					tooltip:getToolTipFromMap('abmPersonasDtUsrsButtonRegresar', helpMap,'Regresar'),	            					
					/*handler: function() {
								window.location.href = _ACTION_REGRESAR;
							}*/
							handler: function() {if(COD==1){
            					 window.location.href = _ACTION_REGRESAR_CLIENTE;
	            								}
	            								else{ window.location.href = _ACTION_REGRESAR;
	            								}
	            								}
				}
   				],
   		listeners: {
         				afterrender: function (obj) {
          						return true;
   					}
          		}
		});
			

		function guardarCrearUsuarioPestanha()
		{
		        var params = {
						cdUsuari: Ext.getCmp('cdUsuariUsrId').getValue(),
		                registGrid: Ext.getCmp('registroGridUsrId').getValue()
		             };
		        execConnection (_ACTION_GUARDAR_DATOS_USUARIOS_PERSONAS, params, cbkGuardarCrearUsuarioPestanha);
		}
		
		function cbkGuardarCrearUsuarioPestanha (success, _message) {
		//console.log(success);
		    if (success) {
				Ext.Msg.alert('Aviso', _message, function() {loadEl_formUsuarios()});
			} else {
				Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message);
			}
		
		}	

/*************** Fin Form Crear Usuario ********************/




	createGridDomcilios();
	createGridTelefonos();
	createGridCorporativo();
	createGridRelaciones();

	/*var oldGetRowClass = grillaDomicilios.getView().getRowClass;
	Ext.apply(grillaDomicilios.getView(), {
		getRowClass: function (record) {
			return oldGetRowClass.apply(this, arguments) + ' x-grid3-row-resaltado';
		}
	});*/






	/*********** Comienza el form principal ************************/
   var el_form = new Ext.Panel({
   		renderTo: 'formTab',
        title: 'Personas',
        bodyStyle:'background: white',
        width: 700,
        //hideMode: 'offsets',
        layout: 'fit',
		//deferredRender: false,
        items: [
        {
        listeners: {
        				beforetabchange : function(_this, newTab, currentTab) {
									if (newTab.id != '1') {
										if (CODIGO_PERSONA == "") {
											Ext.Msg.alert('Error', 'Primero debe cargar los datos generales');
											return false;
										} else {
											if (newTab.id == '2') {
												//el_formDatosAdicionales.form.doLayout();
												el_formDatosAdicionales.on("afterlayout", function(){
													if (el_formDatosAdicionales.items && el_formDatosAdicionales.items.items) {
														Ext.each(el_formDatosAdicionales.items.items, function(c) {
															if (c.alignErrorIcon && c.errorIcon) {
																c.alignErrorIcon();
																c.clearInvalid();
															}
														});
													}
												});
											}
											return true;
										}
									}
									
						}
					},
        xtype:'tabpanel',
        //hideMode: 'offsets',
        id: 'ttabs',
	    resizeTabs:true,
	    minTabWidth: 115,
	    tabWidth:135,
	    enableTabScroll:true,
        renderHidden: true,
        layoutOnTabChange: true,
	    width:700,
	    height:320,
		deferredRender: false,
        hideMode: 'offsets',
            activeTab: 0,
            defaults: {labelWidth: 80},
            items:[{
                title:'Datos Generales',
                layout:'fit',
                bodyStyle:'background: white',
                id: '1',
                items: [
                	el_formpanel
				]
            },{
                title:'Datos Adicionales',
                layout:'fit',
                bodyStyle:'background: white',
                id: '2',
                width: 600,
                items: [el_formDatosAdicionales
            			/*{
            				xtype: 'iframepanel',
            				id: 'framecito',
            				name: 'framecito',
							loadMask:{msg:'Cargando ...'},
							autoCreate: {
                                                                       id: 'framecito',
                                                                       frameBorder:0,
                                                                       style:{
                                                                       //overflow:'auto;',
                                                                       width:'100%',
                                                                       height:'100%'
                                                                       }
                                                               },
              				defaultSrc: _ACTION_OBTENER_DATOS_ADICIONALES + '?codigoTipoPersona=' + TIPO_PERSONA + '&codigoPersona=' + CODIGO_PERSONA,
								    listeners: {
								       domready : function(frame){ //only raised for "same-origin" documents
								            //Set the tab Title to the Document Title
								             var doc= frame.getDocument();
								             if(doc){ frame.ownerCt.setTitle(doc.title); }
								 
								            //Add some custom CSS Rules to the frame document
								            var rule = 'p.fancyP {padding:0px 0px 0px 0px;border:1px solid;' +
								                               'font:normal 11px tahoma,arial,helvetica,sans-serif;}';
								            frame.CSS.createStyleSheet( rule, 'fancyP' );
								 
								            //Find all the 'p' tags in the frame docuement, and add the fancyP className to them
								            frame.select('p').addClass('fancyP');              
								       }
								    }
            			} */               ]
            },{
                title:'Domicilios',
                layout:'fit',
                //bodyStyle:'background: white',
                id: '3',
                items: [grillaDomicilios] 
            },{
            	title: 'Tel&eacute;fonos',
            	layout: 'fit',
            	//bodyStyle:'background: white',
                id: '4',
            	items: [grillaTelefonos]
            },{
            	title: 'Estructura',
            	layout: 'fit',
            	//bodyStyle:'background: white',
                id: '5',
            	items: [grillaCorporativo]
            },{
            	title: 'Relaciones',
            	layout: 'fit',
            	//bodyStyle:'background: white',
            	id: '6',
            	items: [grillaRelaciones]
            },{
                title:'Crear Usuario',
                layout:'fit',
                bodyStyle:'background: white',
                id: '7',
                width: 600,
                items: [el_formUsuarios]
            }
           ]
        }]
    });

    el_form.render();
	/*********** Termina el form principal ************************/
	dsTipoPersonaJ.reload({
			callback: function (r, options, success) {
								Ext.getCmp('tipoPersonaJ').setValue(TIPO_PERSONA);
			}
	});

    ///////////////////////////////////frame_url = _ACTION_LEER_DATOS_ADICIONALES + '?codigoTipoPersona=' + TIPO_PERSONA + '&codigoPersona=' + CODIGO_PERSONA;
//	iframe = Ext.DomHelper.append(Ext.getCmp('2').ownerCt.body, {tag: 'iframe', id: 'iframe-', frameBorder: 0, fitToFrame:true, src: frame_url, width: '100%', height: '100%', scrolling: 'no', style: 'position: absolute'});
//	console.log(Ext.getCmp('framecito'));
	/**
	Ext.getCmp('framecito').setSrc(frame_url);
	**/

	if (CODIGO_PERSONA != "") {
		desabilitarComboTipoPersona();
		startMask('ttabs', 'Cargando...');
		el_formpanel.load({
				params: {codigoPersona: CODIGO_PERSONA, codigoTipoPersona: TIPO_PERSONA, start: 0, limit: itemsPerPage},
				failure: function() {
								Ext.Msg.alert('Error', 'No se pudo leer los datos de la persona', function() {endMask();});
				},
				success: function(form, action) {
							var _estadoCivil = action.reader.jsonData.personaVO[0].cdEstCiv;
							var _nacionalidad = action.reader.jsonData.personaVO[0].cdNacion;
							var _otSexo = action.reader.jsonData.personaVO[0].otSexo;
							var _tipoIdentificador = action.reader.jsonData.personaVO[0].cdTipIde;
							var _cdTipPer = action.reader.jsonData.personaVO[0].cdTipPer;

							dsEstadoCivil.load({
								callback: function (r, options, success) {
										if (success) {
											Ext.getCmp('estadoCivil').setValue(_estadoCivil);
										}
										dsNacionalidad.load({
											callback: function(r, options, success) {
														if (success) {
															Ext.getCmp('nacionalidad').setValue(_nacionalidad);
														}
														dsSexo.load({
															callback: function(r, options, success) {
																if (success) {
																	Ext.getCmp('sexo').setValue(_otSexo);
																}
																dsTipoIdentificador.load({
																	callback: function(r, options, success) {
																		if (success) {
																			Ext.getCmp('tipoIdentificador').setValue(_tipoIdentificador);
																		}
																		dsTipoEmpresa.load({
																			callback: function (r, options, success) {
																				if (success) {
																					Ext.getCmp('tipoEntidad').setValue(_cdTipPer);
																				}
																				cargarCombos();
																			}
																		});
																	}
																});
															}
														});
											}
										});
								}
							});
						}
		});
	} else {
				startMask('ttabs', 'Cargando...');
							dsEstadoCivil.load({
								callback: function (r, options, success) {
										/*if (success) {
											Ext.getCmp('estadoCivil').setValue(_estadoCivil);
										}*/
										dsNacionalidad.load({
											callback: function(r, options, success) {
														dsSexo.load({
															callback: function(r, options, success) {
																dsTipoIdentificador.load({
																	callback: function(r, options, success) {
																		dsTipoEmpresa.load({
																			callback: function (r, options, success) {
																				cargarCombos();
																			}
																		});
																	}
																});
															}
														});
											}
										});
								}
							});
	}
	
	function cargarCombos() {
		//Carga combos uno por uno para evitar sobrecarga de requests simultáneos
		dsEstadoCorporativo.load({
				callback: function() {
						dsTipoTelefono.load({
							callback: function() {
									dsTipoDomicilio.load({
										callback: function(){
												dsEstados.load({
													callback: function() {
															dsClientesCorporativos.load({
																callback: function () {
																	dsTipoRelacionPersona.load({
																		callback: function () {
																			dsFuncionEnPoliza.load({
																				callback: function () {
																					dsPaises.load({
																						callback: function () {
																								dsClientesCorporativosRelaciones.load({
																									callback: function () {
																											if (CODIGO_PERSONA != "") {
																												reloadGridDomicilio();
																												reloadGridTelefono();
																												reloadGridCorporativo();
																												reloadGridRelaciones();
																												loadEl_formUsuarios();
																											}else{
																												Ext.getCmp('abmPersonasDtUsrsButtonGuardar').setDisabled(true);
																												Ext.getCmp('registroGridUsrId').setDisabled(true);	
																											}
																									}
																								});
																						}
																					});
																				}
																			});
																		}
																	});
																	endMask();
																}
															});
													}
												});
										}
									});
							}
						});
					
				}
		});
	}

	function reloadGridTelefono() {
	    var _storeTelef = grillaTelefonos.store;
	    _storeTelef.baseParams = _storeTelef.baseParams || {};
	    _storeTelef.baseParams['codigoPersona'] = CODIGO_PERSONA;
	    _storeTelef.reload({
	    		params: {start: 0, limit: itemsPerPage},
	    		callback: function (r, options, success) {
	    				if (!success) {
	    					_storeTelef.removeAll();
	    				}
	    		}
	    });
	}

	function reloadGridDomicilio () {
		var _storeDom = grillaDomicilios.store;
	    var o = {start: 0};
	    _storeDom.baseParams = _storeDom.baseParams || {};
	    _storeDom.baseParams['codigoPersona'] = CODIGO_PERSONA;
	    _storeDom.reload(
	              {
	                  params:{start:0,limit:itemsPerPage},
	                  callback : function(r, options, success) {
	                      if (!success) {
	                         _storeDom.removeAll();
	                      }/*else{
	                      	_codigoColonia = _storeDom.reader.jsonData.datosDomicilio[0].codigoColonia;
	                      	_codigoEstado = _storeDom.reader.jsonData.datosDomicilio[0].codigoEstado;
	                      	_codigoMunicipio = _storeDom.reader.jsonData.datosDomicilio[0].codigoMunicipio;
	                      	_codigoPais = _storeDom.reader.jsonData.datosDomicilio[0].codigoPais;
	                      	
	                      	Ext.getCmp('comboPaisId').setValue(_codigoPais);
	                      	Ext.getCmp('comboEstadosId').setValue(_codigoEstado);
	                      	Ext.getCmp('comboMunicipioId').setValue(_codigoMunicipio);
	                      	Ext.getCmp('comboColoniaId').setValue(_codigoColonia);
	                      	
	                      }*/
	                  }
	
	              }
	            );
	}	
	function reloadGridCorporativo () {
	    var _storeCorpo = grillaCorporativo.store;
	    _storeCorpo.baseParams = _storeCorpo.baseParams || {};
	    _storeCorpo.baseParams['codigoPersona'] = CODIGO_PERSONA;
	    _storeCorpo.reload({
	    	params: {start: 0, limit: itemsPerPage},
	    	callback: function(r, options, success) {
	    		if (!success) {
	    			_storeCorpo.removeAll();
	    		}
	    	}
	    });
	}

	function reloadGridRelaciones () {
		var _storeRelaciones = grillaRelaciones.store;
		_storeRelaciones.baseParams = _storeRelaciones.baseParams || {};
	    _storeRelaciones.baseParams['codigoPersona'] = CODIGO_PERSONA;
	    _storeRelaciones.reload({
	    	params: {start: 0, limit: itemsPerPage},
	    	callback: function(r, options, success) {
	    		if (!success) {
	    			_storeRelaciones.removeAll();
	    		}
	    	}
	    });
	}

	function loadEl_formUsuarios() {
		el_formUsuarios.form.load({
				params: {cdperson: CODIGO_PERSONA},
				failure: function(){
					Ext.getCmp('abmPersonasDtUsrsButtonGuardar').setDisabled(true);
				    Ext.getCmp('registroGridUsrId').setDisabled(true);	
				},
				success: function(form, action) {
					 //console.log(action.result);
				     Ext.getCmp('abmPersonasDtUsrsButtonAsociar').setDisabled(true);
				     Ext.getCmp('abmPersonasDtUsrsButtonGuardar').setDisabled(false);	
				     Ext.getCmp('registroGridUsrId').setDisabled(false);																												
				}
		});
	}	

	

	/**
	* Función usada para cambiar el texto del combo para mostrarlo en la grilla
	* en vez del código
	*/
	function renderComboEditor (combo) {
		return function (value) {
			var idx;// = combo.store.find(combo.valueField, value, 0, true, true);
			if (combo.id == comboDomicilios.id) {
				value = eval(value);
				for (var i=0; i < combo.store.data.items.length; i++) {
					var registro = combo.store.getAt(i);
					if (registro) {
						if (registro.get(combo.valueField) == value) idx = i;
					} 
				}
			}else idx = combo.store.find(combo.valueField, value);
			/*if (combo.id == comboDomicilios.id) {
				//alert("index: " + idx);
			}
			console.log(combo.store);*/
			//alert("idx: " + idx);
			var rec = combo.store.getAt(idx);
			return (rec == null)?value:rec.get(combo.displayField);
		}
	}
	
	/**
	* Función llamada para dejar la pantalla en estado de Agregar Persona
	*
	*/
	function resetPersona () {
		CODIGO_PERSONA = "";
		el_formpanel.getForm().reset();
		Ext.getCmp('tipoPersonaJ').setValue(TIPO_PERSONA);
		Ext.getCmp('tipoPersonaJ').setDisabled(false);
		if (el_formDatosAdicionales.items.items != null && el_formDatosAdicionales.items.items != undefined) {
			Ext.each (el_formDatosAdicionales.items.items, function(campito) {
				campito.setValue('');
				if (campito.xtype == "combo" || campito.xtype == "datefield") {
					campito.setRawValue('');
					//campito.clearValue();
				}
			});
		} 
		grillaDomicilios.store.removeAll();
		grillaCorporativo.store.removeAll();
		grillaTelefonos.store.removeAll();
		grillaRelaciones.store.removeAll();
		storeCrearUsuario.removeAll();
		Ext.getCmp('abmPersonasDtUsrsButtonGuardar').setDisabled(true);
		Ext.getCmp('registroGridUsrId').setDisabled(true);	
		
		Ext.getCmp('ttabs').setActiveTab('1');
	    ///////////////////var frame_url = _ACTION_LEER_DATOS_ADICIONALES + '?codigoTipoPersona=' + TIPO_PERSONA + '&codigoPersona=' + CODIGO_PERSONA;
		//////////////////////////////////Ext.getCmp('framecito').setSrc(frame_url, true);
	}
	
	function limpiarCamposForm () {
	}

	//Se agrego para corregir issue 771
	function habilitarCampos() {

	dsTipoIdentificador.baseParams['tipoPersona'] =TIPO_PERSONA;
	dsTipoEmpresa.baseParams['tipoPersona'] =TIPO_PERSONA;
	Ext.getCmp('tipoIdentificador').reset();
	Ext.getCmp('tipoEntidad').reset();
	
	dsTipoIdentificador.load();
	
	dsTipoEmpresa.load();
	
		if(TIPO_PERSONA == 'M'){
			Ext.getCmp('nombre').setFieldLabel('Razón Social');
			Ext.getCmp('apellidoPaterno').setVisible(false);
			Ext.getCmp('apellidoMaterno').setVisible(false);
			Ext.getCmp('sexo').setVisible(false);
			Ext.getCmp('estadoCivil').setVisible(false);
			Ext.getCmp('curpId').setVisible(false);
			
			Ext.getCmp('apellidoPaterno').setFieldLabel('');
			Ext.getCmp('apellidoMaterno').setFieldLabel('');
			Ext.getCmp('sexo').setFieldLabel('');
			Ext.getCmp('estadoCivil').setFieldLabel('');
			Ext.getCmp('curpId').setFieldLabel('');
			
		}
		//Pedido en issue 1010
		if (TIPO_PERSONA == "F") {
			
			Ext.getCmp('nombre').setFieldLabel('Nombre');
			
			el_formpanel.findById('apellidoPaterno').allowBlank = false;
			el_formpanel.findById('apellidoMaterno').allowBlank = false;
			el_formpanel.findById('sexo').allowBlank = false;
			el_formpanel.findById('estadoCivil').allowBlank = false;
		
			
			Ext.getCmp('apellidoPaterno').setVisible(true);
			Ext.getCmp('apellidoMaterno').setVisible(true);
			Ext.getCmp('sexo').setVisible(true);
			Ext.getCmp('estadoCivil').setVisible(true);
			Ext.getCmp('curpId').setVisible(true);
			
			Ext.getCmp('apellidoPaterno').setFieldLabel('Apellido Paterno');
			Ext.getCmp('apellidoMaterno').setFieldLabel('Apellido Materno');
			Ext.getCmp('sexo').setFieldLabel('Sexo');
			Ext.getCmp('estadoCivil').setFieldLabel('Estado Civil');
			Ext.getCmp('curpId').setFieldLabel('CURP');
						
		}
		if (TIPO_PERSONA == "M") {
			//el_formpanel.findById('nombre').allowBlank = false;
			el_formpanel.findById('apellidoPaterno').allowBlank = true;
			el_formpanel.findById('apellidoMaterno').allowBlank = true;
			el_formpanel.findById('sexo').allowBlank = true;
			el_formpanel.findById('estadoCivil').allowBlank = true;
			
		}
	}
	habilitarCampos();

/****** VENTANA DE BUSQUEDA DE USUARIOS SIN PERSONAS ******/
	function buscarUsrPrsn(){
	//alert(1);
	
		var jsonGrillaUsuario= new Ext.data.JsonReader(
		{
			root:'MListaUsuarioSinPersonas',
		    totalProperty: 'totalCount',
			successProperty : '@success'
		},
		[
	        {name: 'cdUsuari', type: 'string', mapping: 'cdUsuari'},
	        {name: 'dsUsuari',  type: 'string',  mapping:'dsUsuari'}
	    ]
		);
		
		/* ********************************** STORES *************************** */ 
		var storeGrillaUsuario = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
		url: _ACTION_BUSCAR_USUARIOS_SIN_PERSONAS,
		        waitMsg : getLabelFromMap('400017', helpMap, 'Espere por favor....')
		        }),
		reader: jsonGrillaUsuario
		});
	
		
		//*************Column Model Usuario sin Personas
		var cmUsr = new Ext.grid.ColumnModel([
	         {
	         header: getLabelFromMap('cmUsrClave',helpMap,'Clave'),
	       	 tooltip: getToolTipFromMap('cmUsrClave', helpMap,'Clave'),	
	         dataIndex: 'cdUsuari',
	         width: 130,
	         sortable: true
	         },
	         {
	         header: getLabelFromMap('cmUsrNombres',helpMap,'Nombres'),
	       	 tooltip: getToolTipFromMap('cmUsrNombres', helpMap,'Nombres'),
	         dataIndex: 'dsUsuari', 
	         width: 130,
	         sortable: true         
	         }
	        ]);
	
	
		//*************DEFINICION DE LA GRILLA DE Usuarios sin  Personas
		var gridUsr;
	
		function createGrid(){
			 gridUsr= new Ext.grid.GridPanel({
			        //el: 'gridUsr',
			        id: 'gridUsr',
			        store: storeGrillaUsuario,
					border:true,
					title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
			        cm: cmUsr,
			        reader: jsonGrillaUsuario,
			        buttonAlign:'center',
					buttons:[
			        		{
		      				text:getLabelFromMap('gridUsrDescuentosEdtButtonAgregar', helpMap,'Asociar'),
	    	       			tooltip:getToolTipFromMap('gridUsrDescuentosEdtButtonAgregar', helpMap,'Asociar Usuario'),			        			
			            	handler:function(){
			            			if (getSelectedKey(gridUsr, "cmUsrClave") != "") {
									      		guardarAsociarUsuario(getSelectedRecord(gridUsr));
									} else {
											Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
									}
								}
			            	},
			        		{
							text:getLabelFromMap('gridUsrDescuentosEdtButtonGuardar', helpMap,'Crear Usuario'),
							tooltip:getToolTipFromMap('gridUsrDescuentosEdtButtonGuardar', helpMap,'Crea nuevo Usuario'),	
			            	handler:function(){
									//if (getSelectedKey(gridUsr, "codUsuario") != "") {
									//      		agregarNeoUsuario(windowUsrPrsns, getSelectedRecord(gridUsr));
									//} else {
									//		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
									//}
									agregarNeoUsuario(windowUsrPrsns, null);
								}
							},
			        		{
			        		id:'gridUsrDescuentosEdtButtonRegresar',
			        		text:getLabelFromMap('wndwCrearUsrNeoButtonRegresar', helpMap,'Regresar'),
							tooltip:getToolTipFromMap('wndwCrearUsrNeoButtonRegresar', helpMap,'Regresar a pesta&ntilde;a'),			        			
							handler : function(){
					           		windowUsrPrsns.close();

	  		         			}
							}
			            	],
			    	width:480,
			    	frame:true,
					height:240,
					sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
					bbar: new Ext.PagingToolbar({
							pageSize:20,
							store: storeGrillaUsuario,
							displayInfo: true/*,
							displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
							emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')*/
	
					    })
			});
		//gridUsr.render();
		}
		createGrid();
	
		//*************** DEFINICION DE FORMULARIO DE USUARIOS SIN PERSONAS ************
	   var formPanelUsrPrsn = new Ext.FormPanel({
	       //el:'formBusqueda',
	       //url: _ACTION_OBTENER_TELEFONOS,
	       url: _ACTION_BUSCAR_USUARIOS_SIN_PERSONAS,
	       //successProperty : 'success',
	       iconCls:'logo',
	       bodyStyle:'background: white',
	       buttonAlign: "center",
	       labelAlign: 'right',
	       frame:true,
	       width: 500,
	       height:190,
			items: [
					{
					layout:'form',
					border: false,
					items:[
							{
							bodyStyle:'background: white',
							labelWidth: 100,
							layout: 'form',
							title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br/>',
							frame:true,
							baseCls: '',
							buttonAlign: "center",
							items: [
									{
									layout:'column',
									border:false,
									columnWidth: 1,
									items:[
											{
											columnWidth:.7,
											layout: 'form',
											border: false,
											items:[       		        				
													{
													xtype: 'textfield',
													fieldLabel: getLabelFromMap('nombreUsrPrsnId', helpMap,'Nombre'), 
													tooltip: getToolTipFromMap('nombreUsrPrsnId', helpMap,'Nombre'),
													hasHelpIcon:getHelpIconFromMap('nombreUsrPrsnId',helpMap),
													Ayuda: getHelpTextFromMap('nombreUsrPrsnId',helpMap),           
													//blankText: 'Este campo es requerido',										
													id: 'nombreUsrPrsnId',
													//allowBlank: false,
													name: 'nombreUsrPrsnId',
													//anchor: '100%',
													width:220
													}
												 ]
											},
											{
												columnWidth:.3,
												layout: 'form'
											}						
										]
									}
									],
							buttons:[
										{
										text:getLabelFromMap('usrSinPrsnButtonBuscar',helpMap,'Buscar'),
										tooltip: getToolTipFromMap('usrSinPrsnButtonBuscar',helpMap,'Busca un Grupo de Usuarios'),
										handler: function() {
												if (gridUsr!=null) {
	                                                reloadGrid();
	                                            } else {
	                                                createGrid();
	                                            }
										}
										},
										{
										text:getLabelFromMap('usrSinPrsnButtonCancelar',helpMap,'Cancelar'),
										tooltip: getToolTipFromMap('usrSinPrsnButtonCancelar',helpMap,'Cancelar busqueda de Usuarios'),                              
										handler: function() {
											formPanelUsrPrsn.form.reset();
										}
										}
									]
							},gridUsr
						]
	       			}				
				]	
		});		
					
					
		//Windows donde se van a visualizar la pantalla
		var windowUsrPrsns = new Ext.Window({
				title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('wndwUsrPrsn', helpMap,'Usuarios Sin Personas')+'</span>',
	        	width: 510,
	        	height:400,
	        	layout: 'fit',
	        	modal: true,
	        	plain:true,
	        	bodyStyle:'padding:5px;',
	        	bodyStyle:'background: white',
	        	buttonAlign:'center',
	        	items: 	formPanelUsrPrsn//,gridUsr
	      });
		windowUsrPrsns.show();			


		function guardarAsociarUsuario(record)
		{
		        //alert(record.get('cdUsuari'));
		        var params = {
						cdUsuari: record.get('cdUsuari'),
						cdperson: CODIGO_PERSONA

		             };
		        execConnection (_ACTION_GUARDAR_USUARIOS_SIN_PERSONAS, params, cbkGuardarAsociarUsuario);
		}
		
		function cbkGuardarAsociarUsuario (success, _message) {
		    if (success) {
			      loadEl_formUsuarios();
			      windowUsrPrsns.close();
		    } else {
		          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message);
		    }
		
		}
 

		function reloadGrid(){
			var _params = {
		       		nombre: Ext.getCmp('nombreUsrPrsnId').getValue()
		 	};
			reloadComponentStore(gridUsr, _params, cbkReload);
		}
		function cbkReload(_r, _options, _success, _storeUsr) {
		//console.log(_storeUsr);
			if (!_success) {
				_storeUsr.removeAll();
				Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _storeUsr.reader.jsonData.actionErrors[0]);
			}
		}
				
	}
	/****** FIN VENTANA DE BUSQUEDA DE USUARIOS SIN PERSONAS ******/
	
	/************* VENTANA DE CREAR USUARIO ****************************/
	function agregarNeoUsuario(windowUsrPrsns,record){
	 //console.log(record);
	 	
		var formAgregarNeoUsr = new Ext.FormPanel({
	       labelWidth : 100,
	       //url : _ACTION_CREAR_NUEVO_USUARIO,
	       frame : true,
	       bodyStyle : 'padding:5px 5px 0',
	       bodyStyle: 'background: white',
	       labelAlign:'right',
	       width : 300,
	       height:350,
	  	   items: [
			{
			xtype: 'textfield',
			fieldLabel: getLabelFromMap('dsUsuariCrearUsrId', helpMap,'Usuario'), 
			tooltip: getToolTipFromMap('dsUsuariCrearUsrId', helpMap,'Usuario'),
			hasHelpIcon:getHelpIconFromMap('dsUsuariCrearUsrId',helpMap),
			Ayuda: getHelpTextFromMap('dsUsuariCrearUsrId',helpMap), 
			//blankText: 'Este campo es requerido',										
			id: 'dsUsuariCrearUsrId',
			//allowBlank: false,
			name: 'dsUsuariCrearUsrId',
			//anchor: '100%',
			width:160
			},
			{
			xtype: 'textfield',
			fieldLabel: getLabelFromMap('cdUsuariCrearUsrId', helpMap,'Clave'), 
			tooltip: getToolTipFromMap('cdUsuariCrearUsrId', helpMap,'Clave'),
			hasHelpIcon:getHelpIconFromMap('cdUsuariCrearUsrId',helpMap),
			Ayuda: getHelpTextFromMap('cdUsuariCrearUsrId',helpMap),   
			//blankText: 'Este campo es requerido',										
			id: 'cdUsuariCrearUsrId',
			//allowBlank: false,
			name: 'cdUsuariCrearUsrId',
			maskRe : /[A-N O-Z a-n o-z 0-9 _'.-?¿¡!*/+`^=]/,
			//anchor: '100%',
			//disabled: 'true',
			//value:CODIGO_PERSONA,
			width:160
			},
			{
			xtype: 'textfield',
			fieldLabel: getLabelFromMap('cntrsnhCrearUsrId', helpMap,'Contrase&ntilde;a'), 
			tooltip: getToolTipFromMap('cntrsnhCrearUsrId', helpMap,'Contrase&ntilde;a'),
			hasHelpIcon:getHelpIconFromMap('cntrsnhCrearUsrId',helpMap),
			Ayuda: getHelpTextFromMap('cntrsnhCrearUsrId',helpMap),    
			inputType: 'password',       
			blankText: 'Este campo es requerido',										
			id: 'cntrsnhCrearUsrId',
			name: 'cntrsnhCrearUsrId',
			allowBlank: false,
			//anchor: '100%',
			width:160
			}
	   		]
		});
	
		var windowsCrUsrNuevo = new Ext.Window({
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('wndwCrUsrNuevo', helpMap,'Crear Usuario')+'</span>',
			width: 500,
			height:215,
			layout: 'fit',
			plain:true,
			modal: true,
			bodyStyle:'padding:5px;',
			//bodyStyle:'background: white',
			labelAlign:'right',
			buttonAlign:'center',
			items: formAgregarNeoUsr,
			buttons:[
	       			{
					text:getLabelFromMap('wndwCrearUsrNeoButtonGuardar', helpMap,'Guardar'),
					tooltip:getToolTipFromMap('wndwCrearUsrNeoButtonGuardar', helpMap,'Guardar'),			        			
					//text : 'Guardar',
					//disabled : false,
					handler: function(){
							if (formAgregarNeoUsr.form.isValid()) {
									guardarCrearUsuario();
							}
	       				}
	       			},
					{
					text:getLabelFromMap('wndwCrearUsrNeoButtonRegresar', helpMap,'Regresar'),
					tooltip:getToolTipFromMap('wndwCrearUsrNeoButtonRegresar', helpMap,'Regresar a pesta&ntilde;a'),			        			
					//text : 'Cancelar',
					handler : function(){
			           		windowsCrUsrNuevo.close();
	        		   		windowUsrPrsns.close();
	           			}
	       			}
	       			]
		});
		windowsCrUsrNuevo.show();
		/*if (record!=null ||record!="")
		{
			Ext.getCmp('dsUsuariCrearUsrId').setValue(record.get('dsUsuari'));
		 	Ext.getCmp('cdUsuariCrearUsrId').setValue(record.get('cdUsuari'));
			Ext.getCmp('dsUsuariCrearUsrId').setDisabled(true);
		 	Ext.getCmp('cdUsuariCrearUsrId').setDisabled(true);
		}*/
		
		function guardarCrearUsuario()
		{
		        var params = {
						dsUsuari: Ext.getCmp('dsUsuariCrearUsrId').getValue(),
		                cdUsuari: Ext.getCmp('cdUsuariCrearUsrId').getValue(),
		                contrasenha: Ext.getCmp('cntrsnhCrearUsrId').getValue(),
		                cdperson: CODIGO_PERSONA
		             };
		        execConnection (_ACTION_GUARDAR_CREAR_USUARIOS, params, cbkGuardarCrearUsuario);
		}
		
		function cbkGuardarCrearUsuario (_success, _message, _response) {
		    if (!_success) {
		    	Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message);
				windowsCrUsrNuevo.close();
			    windowUsrPrsns.close();
			    //loadEl_formUsuarios();	
		    } else {
		          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message);
		          windowsCrUsrNuevo.close();
			      windowUsrPrsns.close();
			      loadEl_formUsuarios();		          
		    }
		
		}
			    	
	}
	/************* FIN VENTANA DE CREAR USUARIO ****************************/	

	
});


function resetPersona2 () {
		CODIGO_PERSONA = "";
		Ext.getCmp('tipoPersonaJ').setValue(TIPO_PERSONA);
		Ext.getCmp('el_formpanelId').getForm().reset();
		/*if (el_formDatosAdicionales.getForm().items.items != null && el_formDatosAdicionales.getForm().items.items != undefined) {
			Ext.each (el_formDatosAdicionales.getForm().items.items, function(campito) {
				campito.setValue('');
				if (campito.xtype == "combo" || campito.xtype == "datefield") {
					campito.setRawValue('');
					campito.clearValue();
				}
			});
		} */
		Ext.getCmp('gridDomicilios').store.removeAll();
		Ext.getCmp('gridCorporativo').store.removeAll();
		Ext.getCmp('gridTelefonos').store.removeAll();
		Ext.getCmp('gridRelaciones').store.removeAll();
		//Ext.getCmp('ttabs').activeTab = 0;
		Ext.getCmp('ttabs').setActiveTab('1');
		
}
function mostrarOcultarMascara (_mostrar) {
	if (_mostrar) {
		//startMask(Ext.getCmp('ttabs').id, 'Espere...');
		_the_Mask = new Ext.LoadMask(window.parent.Ext.getCmp('ttabs').id, {msg: 'Espere ...', disabled: false});
		the_Mask = _the_Mask;
		_the_Mask.show();
	} else {
		//endMask();
		the_Mask.hide();
		the_Mask = null;
	}
}
function desabilitarComboTipoPersona(){
		Ext.getCmp('tipoPersonaJ').setDisabled(true);
	}

	var readerDatosGenericos = new Ext.data.JsonReader({
			root: 'comboGenerico',
			totalProperty: 'totalCount',
			successProperty: 'success'
			}, [
				{name: 'codigo', type: 'string', mapping: 'codigo'},
				{name: 'descripcion', type: 'string', mapping: 'descripcion'}
			]
	);

	function crearStoreDatosAdicionales2 (_idTablaLogica, _comboId, _value) {
		var dsDatosAdicionales;
		dsDatosAdicionales = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({
						url: _ACTION_COMBO_GENERICO
			}),
			reader: readerDatosGenericos
		});
		try {
			dsDatosAdicionales.load({
				params: {
					idTablaLogica: _idTablaLogica
				},
				callback: function(r, opt, success) {
					Ext.getCmp(_comboId).setValue(_value);
						if (Ext.getCmp(_comboId).allowBlank == "false") {
							Ext.getCmp(_comboId).allowBlank = false;
							//Ext.getCmp(_comboId).clearInvalid();
						}
						if (Ext.getCmp(_comboId).allowBlank == "true") {
							Ext.getCmp(_comboId).allowBlank = true;
						}
						if (_value == null || _value == undefined || _value == "") {
							if (Ext.getCmp(_comboId).allowBlank == false) {
								//alert(Ext.getCmp(_comboId).blankText);
								Ext.getCmp(_comboId).markInvalid(Ext.getCmp(_comboId).blankText);
								//formDatosAdicionales.getForm().markInvalid({_comboId: 'lslsls'});
							}
						}
					Ext.getCmp(_comboId).on('blur', function() {
						var combito = Ext.getCmp(_comboId);
						if (combito.allowBlank == "false") {
							combito.allowBlank = false;
							//combito.clearInvalid();
						}
						if (combito.allowBlank == "true") {
							combito.allowBlank = true;
						}
						if (combito.getRawValue() == "") {
							combito.clearValue();
							combito.setValue("");
							if (combito.allowBlank == false) {
								//combito.markInvalid({id: _comboId, msg: Ext.getCmp(_comboId).blankText});
								//formDatosAdicionales.getForm().markInvalid({_comboId: 'lslsls'});
							}
						}
					});
				}
			});
		} catch (e) {
			alert("excepcion" + e);
		}
		return dsDatosAdicionales;
	}	

</script>
</head>
<body>

                <div id="formTab">
	               <div id="formDatosGrales"/>
	               <div id="formDatosAdicionales"></div>
	               <div id="gridDomicilios" />
	               <div id="gridTelefonos" />
	               <div id="gridCorporativo" />
	               <div id="gridRelaciones" />
	               <div id="formUsuarios" />
                </div>
			

</body>
</html>