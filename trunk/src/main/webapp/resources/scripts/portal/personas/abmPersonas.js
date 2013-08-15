var helpMap = new Map();

Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

var agregarNuevoDomicilio = false;
var agregarNuevoTelefono = false;
var agregarNuevoCorporativo = false;

	/*************** Definición de Stores y sus correspondientes Readers ********************/
        var readerTipoPersona = new Ext.data.JsonReader( {
            root : 'personasJ',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'codigo', mapping : 'codigo', type : 'string'
        }, {
            name : 'descripcion', type : 'string', mapping : 'descripcion'
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
            name : 'codigo', mapping : 'codigo', type : 'string'
        }, {
            name : 'descripcion', type : 'string', mapping : 'descripcion'
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
            name : 'codigo', mapping : 'codigo', type : 'string'
        }, {
            name : 'descripcion', type : 'string', mapping : 'descripcion'
        }]);

       var dsTipoEmpresa = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_TIPO_EMPRESA
            }),
            reader: readerTipoEmpresa
        });

        var readerTipoIdentificador = new Ext.data.JsonReader( {
            root : 'comboTipoIdentificador',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'codigo', mapping : 'codigo', type : 'string'
        }, {
            name : 'descripcion', type : 'string', mapping : 'descripcion'
        }]);

       var dsTipoIdentificador = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_TIPO_IDENTIFICADOR
            }),
            reader: readerTipoIdentificador
        });
        
        var readerPersona = new Ext.data.JsonReader( {
            root : 'personaVO',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'codigoPersona', mapping : 'codigoPersona', type : 'string'
        }, {
            name : 'tipoPersonaJ', type : 'string', mapping : 'otFisJur'
        }, {
            name : 'nombre', type : 'string', mapping : 'dsNombre'
        }, {
            name : 'apellidoPaterno', type : 'string', mapping : 'dsApellido'
        }, {
            name : 'apellidoMaterno', type : 'string', mapping : 'dsApellido1'
        }, {
            name : 'sexo', type : 'string', mapping : 'otSexo'
        }, {
            name : 'estadoCivil', type : 'string', mapping : 'cdEstCiv'
        }, {
            name : 'fechaNacimiento', type : 'date', mapping : 'feNacimi', dateFormat: 'Y-m-d H:i:s.u'
        }, {
            name : 'nacionalidad', type : 'string', mapping : 'cdNacion'
        }, {
        	name: 'fechaIngreso', type: 'string', mapping: 'feIngreso'
        }, {
        	name: 'tipoIdentificador', type: 'string', mapping: 'cdTipIde'
        }, {
        	name: 'nroIdentificador',  type: 'string', mapping: 'cdIdePer'
        }, {
        	name: 'tipoEntidad', type: 'string', mapping: 'cdTipPer'
        }]);
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
            name : 'codigo', mapping : 'codigo', type : 'string'
        }, {
            name : 'descripcion', type : 'string', mapping : 'descripcion'
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
            name : 'codigo', mapping : 'codigo', type : 'string'
        }, {
            name : 'descripcion', type : 'string', mapping : 'descripcion'
        }]);
		var dsPaises = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_PAISES
            }),
            reader: readerPaises
        }); 

        var readerEstados = new Ext.data.JsonReader( {
            root : 'comboEstados',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'codigo', mapping : 'codigo', type : 'string'
        }, {
            name : 'descripcion', type : 'string', mapping : 'descripcion'
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
            name : 'codigo', mapping : 'codigo', type : 'string'
        }, {
            name : 'descripcion', type : 'string', mapping : 'descripcion'
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
            name : 'codigo', mapping : 'codigo', type : 'string'
        }, {
            name : 'descripcion', type : 'string', mapping : 'descripcion'
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
            name : 'codigo', mapping : 'codigo', type : 'string'
        }, {
            name : 'descripcion', type : 'string', mapping : 'descripcion'
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
            name : 'id', mapping : 'id', type : 'string'
        }, {
            name : 'texto', type : 'string', mapping : 'texto'
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
            name : 'codigo', mapping : 'codigo', type : 'string'
        }, {
            name : 'descripcion', type : 'string', mapping : 'descripcion'
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
            name : 'cdElemento', mapping : 'cdElemento', type : 'string'
        }, {
            name : 'dsElemen', type : 'string', mapping : 'dsElemen'
        }]);
		var dsClientesCorporativos = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_CLIENTES_CORP
            }),
            reader: readerClientesCorporativos
        });

        var readerSexo = new Ext.data.JsonReader( {
            root : 'comboSexo',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'codigo', mapping : 'codigo', type : 'string'
        }, {
            name : 'descripcion', type : 'string', mapping : 'descripcion'
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
            name : 'id', mapping : 'id', type : 'string'
        }, {
            name : 'texto', type : 'string', mapping : 'texto'
        }]);
		var dsEstadoCivil = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_ESTADO_CIVIL
            }),
            reader: readerEstadoCivil
        });	
    /**************Fin Definición de Stores y sus correspondientes Readers ******************/

	/**************Comienza Form Datos Generales ********************************************/
	var el_formpanel = new Ext.FormPanel ({
			renderTo: 'formDatosGrales',
            url : _ACTION_GET_PERSONA,
            frame : true,
            width : 600,
            height: 350,
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
            					},
								{
								xtype: 'combo', 
								tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
			                    store: dsTipoPersonaJ, 
				          		fieldLabel: getLabelFromMap('tipoPersonaJComboAbmPersonas',helpMap,'Tipo de Persona'),
    	                        tooltip: getToolTipFromMap('tipoPersonaJComboAbmPersonas',helpMap),			          		
			                    anchor:'100%', 
			                    displayField:'descripcion', 
			                    valueField: 'codigo', 
			                    hiddenName: 'codigoTipoPersona',
			                    typeAhead: true, 
			                    triggerAction: 'all', 
			                    lazyRender:   true, 
			                    emptyText:'Seleccione Tipo...', 
			                    selectOnFocus:true,
			                    forceSelection:true,
			                    //labelSeparator:'', 
			                    //fieldLabel: 'Tipo de Persona', 
			                    name: 'tipoPersonaJ', 
			                    id: 'tipoPersonaJ'
			                    },
								{
								xtype: 'textfield', 
     							fieldLabel: getLabelFromMap('txtFldNombreAbmPersonas', helpMap,'Nombre'), 
	     						tooltip: getToolTipFromMap('txtFldNombreAbmPersonas', helpMap),           
								//fieldLabel: 'Nombre', 
								name: 'nombre', 
								anchor: '95%', 
								name: 'nombre', 
								id: 'nombre'
								},
								{
								xtype: 'textfield', 
     							fieldLabel: getLabelFromMap('txtFldApellidoPaternoAbmPersonas', helpMap,'Apellido Paterno'), 
	     						tooltip: getToolTipFromMap('txtFldApellidoPaternoAbmPersonas', helpMap),           								
								//fieldLabel: 'Apellido Paterno', 
								name: 'apellidoPaterno', 
								anchor: '95%', 
								name: 'apellidoPaterno', 
								id: 'apellidoPaterno'
								},
								{
								xtype: 'textfield', 
     							fieldLabel: getLabelFromMap('txtFldApellidoMaternoAbmPersonas', helpMap,'Apellido Materno'), 
	     						tooltip: getToolTipFromMap('txtFldApellidoMaternoAbmPersonas', helpMap),
								//fieldLabel: 'Apellido Materno', 
								name: 'apellidoMaterno', 
								anchor: '95%', 
								name: 'apellidoMaterno', 
								id: 'apellidoMaterno'
								},
								{
								xtype: 'combo', 
								tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
			                    store: dsSexo, 
				          		fieldLabel: getLabelFromMap('sexoComboAbmPersonas',helpMap,'Sexo'),
    	                        tooltip: getToolTipFromMap('sexoComboAbmPersonas',helpMap),			          		
			                    anchor:'100%', 
			                    displayField:'descripcion', 
			                    valueField: 'codigo', 
			                    hiddenName: 'sexoH',
			                    typeAhead: true, 
			                    triggerAction: 'all', 
			                    lazyRender:   true, 
			                    emptyText:'Seleccione Sexo...', 
			                    selectOnFocus:true,
			                    forceSelection:true,
			                    //labelSeparator:'', 
			                    //fieldLabel: 'Sexo', 
			                    id: 'sexo'
			                    },
								{
								xtype: 'combo', 
								tpl: '<tpl for="."><div ext:qtip="{id}.{texto}" class="x-combo-list-item">{texto}</div></tpl>',
			                    store: dsEstadoCivil, 
				          		fieldLabel: getLabelFromMap('estadoCivilComboAbmPersonas',helpMap,'Estado Civil'),
    	                        tooltip: getToolTipFromMap('estadoCivilComboAbmPersonas',helpMap),			          		
			                    anchor:'100%', 
			                    displayField:'texto', 
			                    valueField: 'id', 
			                    hiddenName: 'estadoCivilH',
			                    typeAhead: true, 
			                    triggerAction: 'all', 
			                    lazyRender:   true, 
			                    emptyText:'Seleccione...', 
			                    selectOnFocus:true,
			                    //labelSeparator:'', 
			                    //fieldLabel: 'Estado Civil', 
			                    name: 'estadoCivil', 
			                    id: 'estadoCivil'
			                    },
								{
								xtype: 'datefield', 
     							fieldLabel: getLabelFromMap('dtFldFechaNacimientoAbmPersonas', helpMap,'Fecha de Nacimiento'), 
	     						tooltip: getToolTipFromMap('dtFldFechaNacimientoAbmPersonas', helpMap),           
								//fieldLabel: 'Fecha de Nacimiento', 
								format: 'd/m/Y', 
								anchor: '95%', 
								name: 'fechaNacimiento', 
								id: 'fechaNacimiento',
								renderer: Ext.util.Format.dateRenderer('d/m/Y')
								}, 
								{
								xtype: 'combo', 
								tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
			                    store: dsNacionalidad, 
				          		fieldLabel: getLabelFromMap('nacionalidadComboAbmPersonas',helpMap,'Nacionalidad'),
    	                        tooltip: getToolTipFromMap('nacionalidadComboAbmPersonas',helpMap),			          		
			                    anchor:'100%', 
			                    displayField:'descripcion', 
			                    valueField: 'codigo', 
			                    hiddenName: 'NacionalidadH',
			                    typeAhead: true, 
			                    triggerAction: 'all', 
			                    lazyRender:   true, 
			                    emptyText:'Seleccione Nacionalidad...', 
			                    selectOnFocus:true,
			                    forceSelection:true,
			                    //labelSeparator:'', 
			                    //fieldLabel: 'Nacionalidad', 
			                    name: 'nacionalidad', 
			                    id: 'nacionalidad'
			                    }
								]
            				   },
            				   {
            				   	columnWidth: .5,
            					layout: 'form',
            					items: [
								{
								xtype: 'combo', 
								tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
			                    store: dsTipoIdentificador, 
				          		fieldLabel: getLabelFromMap('tipoIdentificadorComboAbmPersonas',helpMap,'Tipo de Identificador'),
    	                        tooltip: getToolTipFromMap('tipoIdentificadorComboAbmPersonas',helpMap),			          		
			                    anchor:'100%', 
			                    displayField:'descripcion', 
			                    valueField: 'codigo', 
			                    hiddenName: 'tipoIdentificadorH',
			                    typeAhead: true, 
			                    triggerAction: 'all', 
			                    lazyRender:   true, 
			                    emptyText:'Seleccione Tipo...', 
			                    selectOnFocus:true,
			                    forceSelection:true,
			                    //labelSeparator:'', 
			                    //fieldLabel: 'Tipo de Identificador', 
			                    id: 'tipoIdentificador', 
			                    name: 'tipoIdentificador'
			                    },
								{
								xtype: 'textfield', 
     							fieldLabel: getLabelFromMap('txtFldNroIdentificadorAbmPersonas', helpMap,'Nro. de Identificador'), 
	     						tooltip: getToolTipFromMap('txtFldNroIdentificadorAbmPersonas', helpMap),           
								//fieldLabel: 'Nro. de Identificador', 
								id: 'nroIdentificador', 
								anchor: '95%'
								},
								{
								xtype: 'combo', 
								tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
			                    store: dsTipoEmpresa, 
				          		fieldLabel: getLabelFromMap('tipoEntidadComboAbmPersonas',helpMap,'Tipo de Entidad'),
    	                        tooltip: getToolTipFromMap('tipoEntidadComboAbmPersonas',helpMap),			          		
			                    anchor:'100%', 
			                    displayField:'descripcion', 
			                    valueField: 'codigo', 
			                    hiddenName: 'tipoEntidadHS',
			                    typeAhead: true, 
			                    triggerAction: 'all', 
			                    lazyRender:   true, 
			                    emptyText:'Seleccione Tipo...', 
			                    selectOnFocus:true,
			                    forceSelection:true,
			                    //labelSeparator:'', 
			                    //fieldLabel: 'Tipo de Entidad', 
			                    id: 'tipoEntidad', 
			                    name: 'tipoEntidad'
			                    },
								{
								xtype: 'datefield', 
     							fieldLabel: getLabelFromMap('dtFldFechaIngresoAbmPersonas', helpMap,'Fecha de Ingreso'), 
	     						tooltip: getToolTipFromMap('dtFldFechaIngresoAbmPersonas', helpMap),           
								//fieldLabel: 'Fecha de Ingreso', 
								id: 'fechaIngreso', 
								format: 'd/m/Y', 
								anchor: '95%',
								renderer: Ext.util.Format.dateRenderer('d/m/Y')
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
           						tooltip:getToolTipFromMap('abmPersonasDtGrlButtonGuardar', helpMap),	
            					//text: 'Guardar', 
            					handler: function () {
		            					GuardarPersona(false);
		            					}
            					},
            					{
      							text:getLabelFromMap('abmPersonasDtGrlButtonGuardarAgregar', helpMap,'Guardar y Agregar'),
           						tooltip:getToolTipFromMap('abmPersonasDtGrlButtonGuardarAgregar', helpMap),	
            					//text: 'Guardar y Agregar', 
            					handler: function () {
            							GuardarPersona(true);
            							}
            					},
            					{
      							text:getLabelFromMap('abmPersonasDtGrlButtonRegresar', helpMap,'Regresar'),
           						tooltip:getToolTipFromMap('abmPersonasDtGrlButtonRegresar', helpMap),	
            					//text: 'Regresar',
            					 handler: function() {
            					 				//window.location.href = _ACTION_REGRESAR;
            					 		}
            					 }
            				]

            //se definen los campos del formulario
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
				 "&datosGenerales[0].fechaIngreso=" + el_formpanel.findById('fechaIngreso').getRawValue() ;

		var conn = new Ext.data.Connection();
		conn.request({
			url: _ACTION_GUARDAR_DATOS_GENERALES,
			method: 'POST',
			successProperty : '@success',
			params : Params,
			waitMsg : getLabelFromMap('400021', helpMap,'Espere por favor ...'),
			//waitMsg: 'Espere por favor...',
                          	callback: function (options, success, response) {
                       					//if (CODIGO_PERSONA == "") CODIGO_PERSONA = Ext.util.JSON.decode(response.responseText).codigoPersona; 
                          				if (Ext.util.JSON.decode(response.responseText).success == false) {
                          					Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), Ext.util.JSON.decode(response.responseText).errorMessages[0]);
                          					band = false;
                          				} else {
                          					Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), Ext.util.JSON.decode(response.responseText).actionMessages[0], 
                          										function(){
                          											if (_agregarPersona) {
                          												resetPersona();
                          											}else {
							                          					if (CODIGO_PERSONA == "") {
							                          						if ((Ext.util.JSON.decode(response.responseText).codigoPersona != null) && (Ext.util.JSON.decode(response.responseText).codigoPersona != "")) {
							                          							CODIGO_PERSONA = Ext.util.JSON.decode(response.responseText).codigoPersona;
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
		alert(_value);
		return value.substring(8, 1) + "/" + value.substring(5, 1) + "/" + value.substring(0, 4);
	}
	/********* Comienzo del grid de Domicilios*****************************/
		//Crea el Store
		var recordDomicilios = new Ext.data.Record.create([ 
				{
		            name : 'codigoPersona', mapping : 'codigoPersona', type : 'string'
		        }, {
		        	name: 'numOrdenDomicilio', type: 'string',  mapping: 'numOrdenDomicilio'
		        }, {
		            name : 'tipoDomicilio', type : 'string', mapping : 'tipoDomicilio'
		        }, {
		            name : 'dsDomicilio', type : 'string', mapping : 'dsDomicilio'
		        }, {
		            name : 'cdPostal', type : 'string', mapping : 'cdPostal'
		        }, {
		            name : 'numero', type : 'string', mapping : 'numero'
		        }, {
		            name : 'numeroInterno', type : 'string', mapping : 'numeroInterno'
		        }, {
		            name : 'codigoPais', type : 'string', mapping : 'codigoPais'
		        }, {
		            name : 'codigoEstado', type : 'string', mapping : 'codigoEstado'
		        }, {
		            name : 'codigoMunicipio', type : 'string', mapping : 'codigoMunicipio'
		        }, {
		            name : 'codigoColonia', type : 'string', mapping : 'codigoColonia'
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
		//Fin Crea el Store
		//Creación de Combos
		var comboPais = new Ext.form.ComboBox(
				{
					tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
			        store: dsPaises, 
	          		//fieldLabel: getLabelFromMap('codigoPaisAbmPersonas',helpMap,'Tipo de Entidad'),
                    tooltip: getToolTipFromMap('codigoPaisComboAbmPersonas',helpMap),			          		
			        width: 100, 
			        displayField:'descripcion', 
			        valueField: 'codigo', 
			        hiddenName: 'CodigoPais', 
			        name: 'codigoPais',
			        typeAhead: true, 
			        triggerAction: 'all', 
			        lazyRender:   true, 
			        emptyText:'Seleccione Pais...', 
			        selectOnFocus:true,
			        forceSelection:true
			        /*labelSeparator:'',
			                    		onSelect: function (record) {
			                    						this.setValue(record.get('codigo'));
			                    						this.collapse();
			                    						comboEstados.store.reload({
			                    								params: {codigoPais: record.get('codigo')}
			                    						});
			                    		}*/
			                  });
		var comboEstados = new Ext.form.ComboBox(
			{
				tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
			    store: dsEstados, 
          		//fieldLabel: getLabelFromMap('codEstadoAbmPersonas',helpMap,'Tipo de Entidad'),
                //tooltip: getToolTipFromMap('codEstadoComboAbmPersonas',helpMap),			          		
			    width: 100, 
			    displayField:'descripcion', 
			    valueField: 'codigo', 
			    hiddenName: 'CodigoEstado', 
			    id: 'codEstado', 
			    name: 'codEstado',
			    typeAhead: true, 
			    triggerAction: 'all', 
			    lazyRender:   true, 
			    emptyText:'Seleccione Estado...', 
			    selectOnFocus:true,
			    forceSelection:true,
			    //labelSeparator:'',
			                    		/*onSelect: function(record) {
			                    				this.setValue(record.get('codigo'));
			                    				this.collapse();
			                    				comboMunicipio.store.reload({
			                    						params: {
	                    										codigoPais: grillaDomicilios.getSelectionModel().getSelected().get('codigoPais'),
	                    										codigoEstado: record.get('codigo')
			                    						}
			                    				});
			                    		},*/
                   		listeners: {
							focus: function () {
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
                   					}
                   		}
                  });
		var comboMunicipio = new Ext.form.ComboBox(
				{
					tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
			        store: dsMunicipios, 
	          		//fieldLabel: getLabelFromMap('codEstadoAbmPersonas',helpMap,'Tipo de Entidad'),
    	            //tooltip: getToolTipFromMap('codigoPaisComboAbmPersonas',helpMap),			          		
			        width: 100, 
			        displayField:'descripcion', 
			        valueField: 'codigo', 
			        hiddenName: 'CodigoPais', //name: 'codigoPais',
			        typeAhead: true, 
			        triggerAction: 'all', 
			        lazyRender:   true, 
			        emptyText:'Seleccione Municipio...', 
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
                   						}
                    		}
                  });
		var comboColonias = new Ext.form.ComboBox(
			{
				tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
			    store: dsColonias, 
          		//fieldLabel: getLabelFromMap('codEstadoAbmPersonas',helpMap,'Tipo de Entidad'),
   	            //tooltip: getToolTipFromMap('codigoColoniaComboAbmPersonas',helpMap),			          		
			    displayField:'descripcion', 
			    valueField: 'codigo', 
			    hiddenName: 'CodigColonia', 
			    name: 'codigoColonia',
			    typeAhead: true, 
			    triggerAction: 'all', 
			    lazyRender:   true, 
			    emptyText:'Seleccione Colonia...', 
			    selectOnFocus:true,
			    forceSelection:true,
			    //labelSeparator:'', 
			    width: 100, 
              		listeners: {
              					focus: function () {
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
              					}
              		}
             });
		var comboDomicilios = new Ext.form.ComboBox(
				{
					tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
			        store: dsTipoDomicilio, 
	          		fieldLabel: getLabelFromMap('tipoDomicilioComboAbmPersonas',helpMap,'Tipo de Entidad'),
    	            tooltip: getToolTipFromMap('tipoDomicilioComboAbmPersonas',helpMap),			          		
			        displayField:'descripcion', 
			        valueField: 'codigo', 
			        hiddenName: 'TipoDomicilio',
			        typeAhead: true, 
			        triggerAction: 'all', 
			        lazyRender:   true, 
			        emptyText:'Seleccione Tipo...', 
			        selectOnFocus:true,
			        forceSelection:true,
			        //labelSeparator:'', 
			        //fieldLabel: 'Tipo de Entidad', 
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
	    		},
				{
				   	header: getLabelFromMap('cmTipoDomicilioAbmPersonas',helpMap,'Tipo'),
        			tooltip: getToolTipFromMap('cmTipoDomicilioAbmPersonas', helpMap),	   	
		           	//header: "Tipo",
		           	dataIndex: 'tipoDomicilio',
	    			sortable: true,
		           	width: 80,
	           		resizable: true,
		           	editor: comboDomicilios,
			        renderer: renderComboEditor(comboDomicilios)
	        	},{
				   	header: getLabelFromMap('cmDsDomicilioAbmPersonas',helpMap,'Calle'),
        			tooltip: getToolTipFromMap('cmDsDomicilioAbmPersonas', helpMap),	   	
		           	//header: "Calle",
		           	dataIndex: 'dsDomicilio',
		           	width: 60,
	    			sortable: true,
	           		resizable: true,
		           	editor: new Ext.form.TextField(
		           		{
 		           			name: 'Calle', 
		           			width: 100, 
		           			maxLength: 50
		           		})
	           	},{
				   	header: getLabelFromMap('cmCdPostalAbmPersonas',helpMap,'C.P.'),
        			tooltip: getToolTipFromMap('cmTipoDomicilioAbmPersonas', helpMap),	   	
		           	//header: "C.P.",
		           	dataIndex: 'cdPostal',
		           	width: 40,
	    			sortable: true,
	           		resizable: true,
		           	editor: new Ext.form.NumberField(
		           		{
 		           			name: 'CodigoPostal', 
		           			width: 100, 
		           			maxLength: 10
		           		})
	           	},{
				   	header: getLabelFromMap('cmNumeroAbmPersonas',helpMap,'Numero'),
        			tooltip: getToolTipFromMap('cmNumeroAbmPersonas', helpMap),	   	
		           	//header: "N&uacute;mero",
		           	dataIndex: 'numero',
		           	width: 50,
	    			sortable: true,
	           		resizable: true,
		           	editor: new Ext.form.NumberField(
		           		{
		           			name: 'numeroExterno', 
		           			width: 100, 
		           			maxLength: 10
		           		})
	           	},{
				   	header: getLabelFromMap('cmNumeroInternoAbmPersonas',helpMap,'Numero Interno'),
        			tooltip: getToolTipFromMap('cmNumeroInternoAbmPersonas', helpMap),	   	
	           		//header: 'N&uacute;m. Interno',
	           		dataIndex: 'numeroInterno',
		           	width: 70,
	    			sortable: true,
	           		resizable: true,
	           		editor: new Ext.form.NumberField(
	           			{
	           				name: 'numeroInterno', 
	           				width: 100, 
	           				maxLength: 10
	           			})
	           	},
	           	{
				   	header: getLabelFromMap('cmCodigoPaisAbmPersonas',helpMap,'Pais'),
        			tooltip: getToolTipFromMap('cmCodigoPaisAbmPersonas', helpMap),	   	
	           		//header: 'Pa&iacute;s',
	           		dataIndex: 'codigoPais',
		           	width: 100,
	    			sortable: true,
	           		resizable: true,
		           	editor: comboPais,
		           	renderer: renderComboEditor(comboPais)
	           		
	           	},
	           	{
				   	header: getLabelFromMap('cmCodigoEstadoAbmPersonas',helpMap,'Estado'),
        			tooltip: getToolTipFromMap('cmCodigoEstadoAbmPersonas', helpMap),	   	
	           		//header: 'Estado',
	           		dataIndex: 'codigoEstado',
		           	width: 90,
	    			sortable: true,
	           		resizable: true,
		           	editor: comboEstados,
		           	renderer: renderComboEditor(comboEstados)
	           	},{
				   	header: getLabelFromMap('cmCodigoMunicipioAbmPersonas',helpMap,'Municipio'),
        			tooltip: getToolTipFromMap('cmCodigoMunicipioAbmPersonas', helpMap),	   	
	           		//header: 'Municipio',
	           		dataIndex: 'codigoMunicipio',
		           	width: 90,
		           	sortable: true,
	           		resizable: true,
		           	editor: comboMunicipio,
		           	renderer: renderComboEditor(comboMunicipio)
	           	},
	           	{
				   	header: getLabelFromMap('cmCodigoColoniaAbmPersonas',helpMap,'Colonia'),
        			tooltip: getToolTipFromMap('cmCodigoColoniaAbmPersonas', helpMap),	   	
	           		//header: 'Colonia',
	           		dataIndex: 'codigoColonia',
		           	width: 90,
		           	sortable: true,
	           		editor: comboColonias,
	           		resizable: true,
	           		renderer: renderComboEditor(comboColonias)
	           	}
	           	]);
	           	
		//Fin Definición Column Model
		grillaDomicilios = new Ext.grid.EditorGridPanel({
	        el:'gridDomicilios',
	        store: crearGridDomciliosStore(),
			border:true,
			frame: true,
			region: 'center',
			collapsible: true,
			stripeRows: true,
			//autoHeight : true,
            autoWidth : true,
            autoScroll: true,
			enableColumnResize: true,
			//viewConfig:{forceFit:true},
	        clicksToEdit:1,
	        cm: cm,
			buttons:[
		        		{
   							text:getLabelFromMap('abmPersonasGrDmcButtonAgregar', helpMap,'Agr'),
       						tooltip:getToolTipFromMap('abmPersonasGrDmcButtonAgregar', helpMap),	
		        			//text:'Agregar',
		        			handler: function () {addGridDomicilioNewRecord();}
		            	},
		            	{
   							text:getLabelFromMap('abmPersonasGrDmcButtonGuardar', helpMap,'Guardar'),
       						tooltip:getToolTipFromMap('abmPersonasGrDmcButtonGuardar', helpMap),	
		            		//text:'Guardar',
		            		handler: function () {
		            				agregarNuevoDomicilio = false;
		            				guardarDatosDomicilio();
		            		}
		            	},
		            	{
   							text:getLabelFromMap('abmPersonasGrDmcButtonGuardarAgregar', helpMap,'Guardar y Agregar'),
       						tooltip:getToolTipFromMap('abmPersonasGrDmcButtonGuardarAgregar', helpMap),	
		            		//text:'Guardar y Agregar',
		            		handler: function() {
		            				agregarNuevoDomicilio = true;
		            				guardarDatosDomicilio();
		            		}
		            	}, {
   							text:getLabelFromMap('abmPersonasGrDmcButtonBorrar', helpMap,'Eliminar'),
       						tooltip:getToolTipFromMap('abmPersonasGrDmcButtonBorrar', helpMap),	
		            		//text: 'Borrar',
		            		handler: function() {
		            					if (grillaDomicilios.getSelectionModel().getSelections().length > 0) {
		            						Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400050', helpMap,'Seguro desea eliminar el domicilio?'), 
		            									function(btn) {
		            											if (btn == "yes") {
		            												borrarDomicilio();
		            											}
		            									});
		            						
		            					}else {
		            						Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400047', helpMap,'Debe seleccionar un domicilio para eliminar'));
		            						//Ext.Msg.alert('Error', 'Debe seleccionar un domicilio para borrar');
		            					}
		            					
		            				}
		            	}, {
   							text:getLabelFromMap('abmPersonasGrDmcButtonRegresar', helpMap,'Regresar'),
       						tooltip:getToolTipFromMap('abmPersonasGrDmcButtonRegresar', helpMap),	
		            		//text: 'Regresar',
            				handler: function() {
            					 				window.location.href = _ACTION_REGRESAR;
            					 		}
		            	}
	            	],
			height:320,
			sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			bbar: new Ext.PagingToolbar({
					pageSize:20,
					store: crearGridDomciliosStore(),
					displayInfo: true,
					displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
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
		}
	}
	function cbkGuardarDomicilio (_success, _message) {
		if (_success) {
			grillaDomicilios.store.commitChanges();
			Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){
					var _params = {codigoPersona: CODIGO_PERSONA, start: 0, limit: itemsPerPage};
					reloadComponentStore(grillaDomicilios, _params, function(success) {if(agregarNuevoDomicilio) addGridDomicilioNewRecord();});
			});
		}else {
			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
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
			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400046', helpMap,'Debe seleccionar un teléfono'));
			//Ext.Msg.alert('Error', 'Debe seleccionar un teléfono');
		}
	}
	function cbkBorrarDomicilio (_success, _messages) {
		if (_success) {
			var _params = {codigoPersona: CODIGO_PERSONA, start: 0, limit: itemsPerPage};
			Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _messages, function(){reloadComponentStore(grillaDomicilios, _params);});
		}else {
			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _messages);
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
          		fieldLabel: getLabelFromMap('dsTipoTelefonoComboAbmPersonas',helpMap,'Tipo de Entidad'),
   	            tooltip: getToolTipFromMap('dsTipoTelefonoComboAbmPersonas',helpMap),
   	            anchor:'100%', 
			    displayField:'descripcion', 
			    valueField: 'codigo', 
			    hiddenName: 'TipoDomicilio',
			    typeAhead: true, 
			    triggerAction: 'all', 
			    lazyRender:   true, 
			    emptyText:'Seleccione Tipo...', 
			    selectOnFocus:true,
			    name: 'codTipTel' 
			    //labelSeparator:'', 
			    //fieldLabel: 'Tipo de Entidad'
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
				   	header: getLabelFromMap('cmCodTipTelAbmPersonas',helpMap,'Tipo de Telefono'),
        			tooltip: getToolTipFromMap('cmCodTipTelAbmPersonas', helpMap),	   	
		           	//header: "Tipo de Tel&eacute;fono",
		           	dataIndex: 'codTipTel',
		           	width: 100,
		           	sortable: true,
		           	editor: comboTipoTelefono,
		           	renderer: renderComboEditor(comboTipoTelefono)
	        	},{
				   	header: getLabelFromMap('cmCodAreaAbmPersonas',helpMap,'Codigo de Area'),
        			tooltip: getToolTipFromMap('cmCodAreaAbmPersonas', helpMap),	   	
		           	//header: "C&oacute;digo de Area",
		           	dataIndex: 'codArea',
		           	width: 100,
		           	sortable: true,
		           	editor: new Ext.form.NumberField({name: 'codArea', maxLength: 3})
	           	},{
				   	header: getLabelFromMap('cmNumTelefAbmPersonas',helpMap,'Numero'),
        			tooltip: getToolTipFromMap('cmNumTelefAbmPersonas', helpMap),	   	
		           	//header: "N&uacute;mero",
		           	dataIndex: 'numTelef',
		           	width: 100,
		           	sortable: true,
		           	editor: new Ext.form.NumberField({name: 'numTelef', maxLength: 15})
	           	},{
				   	header: getLabelFromMap('cmNumExtensAbmPersonas',helpMap,'Extension'),
        			tooltip: getToolTipFromMap('cmNumExtensAbmPersonas', helpMap),	   	
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
	        store: crearGridTelefonosStore(),
			border:true,
			autoWidth: true,
			buttonAlign:'center',
	        clicksToEdit:1,
	        cm: cm,
			buttons:[
		        		{
   							text:getLabelFromMap('abmPersonasGrTlfButtonAgregar', helpMap,'Agregar'),
       						tooltip:getToolTipFromMap('abmPersonasGrTlfButtonAgregar', helpMap),	
		        			//text:'Agregar',
		        			handler: function () {addNewRecordTelefono();}
		            	},
		            	{
   							text:getLabelFromMap('abmPersonasGrTlfButtonGuardar', helpMap,'Guardar'),
       						tooltip:getToolTipFromMap('abmPersonasGrTlfButtonGuardar', helpMap),	
		            		//text:'Guardar',
		            		handler: function() {
		            				agregarNuevoTelefono = false;
		            				guardarTelefonos();
		            		}
		            	},
		            	{
   							text:getLabelFromMap('abmPersonasGrTlfButtonGuardarAgregar', helpMap,'Guardar y Agregar'),
       						tooltip:getToolTipFromMap('abmPersonasGrTlfButtonGuardarAgregar', helpMap),	
		            		//text:'Guardar y Agregar',
		            		handler: function() {
		            				agregarNuevoTelefono = true;
		            				guardarTelefonos(true);
		            		}
		            	}, {
   							text:getLabelFromMap('abmPersonasGrTlfButtonBorrar', helpMap,'Eliminar'),
       						tooltip:getToolTipFromMap('abmPersonasGrTlfButtonBorrar', helpMap),	
		            		//text: 'Borrar',
		            		handler: function () {
		            						if (grillaTelefonos.getSelections().length > 0) {
		            							Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400051', helpMap,'Seguro desea eliminar el teléfono seleccionado?'),		            							
		            							//Ext.MessageBox.confirm('Confirmación', 'Seguro desea borrar el teléfono seleccionado?', 
		            											function(btn){
		            												if (btn == "yes") {
		            													borrarTelefono();
		            												}
		            											});
		            						
		            						}else {
		            							Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400053', helpMap,'Debe seleccionar un teléfono para eliminar'));		            							
		            							//Ext.Msg.alert('Error', 'Debe seleccionar un teléfono para borrar');
		            						}
		            				}
		            	}, {

   							text:getLabelFromMap('abmPersonasGrTlfButtonRegresar', helpMap,'Regresar'),
       						tooltip:getToolTipFromMap('abmPersonasGrTlfButtonRegresar', helpMap),	
		            		//text: 'Regresar',
            					 handler: function() {
            					 				window.location.href = _ACTION_REGRESAR;
            					 		}
		            	}
	            	],
	    	width:500,
	    	frame:true,
			height:320,
			sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			bbar: new Ext.PagingToolbar({
					pageSize:20,
					store: store,
					displayInfo: true,
					displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
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
		}
		
	}
	function cbkGuardarTelefonos(_success, _messages) {
		if (_success) {
			var _params = {codigoPersona: CODIGO_PERSONA, start: 0, limit: itemsPerPage};
			grillaTelefonos.store.commitChanges();
			Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _messages, reloadComponentStore(grillaTelefonos, _params, function(){if (agregarNuevoTelefono) addNewRecordTelefono();}));
		}else {
			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _messages);
		}
	}
	function borrarTelefono () {
		var recs = grillaTelefonos.getSelectionModel().getSelections();
		if (recs.length > 0) {
			var params = {codigoPersona: CODIGO_PERSONA, numOrden: recs[0].get('numeroOrdenTel')}			
			execConnection(_ACTION_BORRAR_TELEFONO, params, cbkBorrarTelefono);
		}else {
			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400046', helpMap,'Debe seleccionar un teléfono'));
			//Ext.Msg.alert('Error', 'Debe seleccionar un teléfono');
		}
	}
	function cbkBorrarTelefono (_success, _message) {
		if (_success) {
			var _params = {codigoPersona: CODIGO_PERSONA, start: 0, limit: itemsPerPage};
			Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function() {reloadComponentStore(grillaTelefonos, _params);});
		}else {
			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
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
			        {name: 'feFin',  type: 'date',  mapping:'feFin', dateFormat: 'd/m/Y'},
			        {name: 'feInicio',  type: 'date',  mapping:'feInicio', dateFormat: 'd/m/Y'}
					])
		function crearGridCorporativoStore(){
		 			store = new Ext.data.Store({
		    			proxy: new Ext.data.HttpProxy({
						url: _ACTION_OBTENER_CORPORATIVO,
						waitMsg : getLabelFromMap('400021', helpMap,'Espere por favor ...')
						//waitMsg: 'Espere por favor....'
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
          		fieldLabel: getLabelFromMap('codGrupoCorporativoComboAbmPersonas',helpMap,'Grupo'),
   	            tooltip: getToolTipFromMap('codGrupoCorporativoComboAbmPersonas',helpMap),		    
			    anchor:'100%', 
			    displayField:'texto', 
			    valueField: 'id', 
			    hiddenName: 'GrupoCorporativo',
			    typeAhead: true, 
			    triggerAction: 'all', 
			    lazyRender:   true, 
			    emptyText:'Seleccione Grupo...', 
			    selectOnFocus:true,forceSelection:true,
			    name: 'codGrupoCorporativo' 
			    //labelSeparator:'', 
			    //fieldLabel: 'Grupo'
			  });
		var comboClientesCorporativos = new Ext.form.ComboBox(
			{
				tpl: '<tpl for="."><div ext:qtip="{cdElemento}.{dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
			    store: dsClientesCorporativos, 
          		fieldLabel: getLabelFromMap('dsClientesCorporativosComboAbmPersonas',helpMap,'Corporativo'),
   	            tooltip: getToolTipFromMap('dsClientesCorporativosComboAbmPersonas',helpMap),
			    anchor:'100%', 
			    displayField:'dsElemen', 
			    valueField: 'cdElemento', 
			    hiddenName: 'clientesCorpo',
			    typeAhead: true, 
			    triggerAction: 'all', 
			    lazyRender:   true, 
			    emptyText:'Seleccione Corp...', 
			    selectOnFocus:true,
			    forceSelection:true,
			    name: 'codEstadoCorpo', 
			    //labelSeparator:'', 
			    //fieldLabel: 'Corporativo',
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
          		fieldLabel: getLabelFromMap('dsEstadoCorporativoComboAbmPersonas',helpMap,'Estado'),
   	            tooltip: getToolTipFromMap('dsEstadoCorporativoComboAbmPersonas',helpMap),
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
			    name: 'codEstadoCorpo' 
			    //labelSeparator:'', 
			    //fieldLabel: 'Estado'
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
				   	header: getLabelFromMap('cmCdElemenAbmPersonas',helpMap,'Corporativo'),
        			tooltip: getToolTipFromMap('cmCdElemenAbmPersonas', helpMap),	   	
		           	//header: "Corporativo",
		           	dataIndex: 'cdElemen',
		           	width: 100,
		           	sortable: true,
		           	editor: comboClientesCorporativos,
		           	renderer: renderComboEditor(comboClientesCorporativos)
	           	},
				{
				   	header: getLabelFromMap('cmCdGrupoPerAbmPersonas',helpMap,'Grupo'),
        			tooltip: getToolTipFromMap('cmCdGrupoPerAbmPersonas', helpMap),	   	
		           	//header: "Grupo",
		           	dataIndex: 'cdGrupoPer',
		           	width: 100,
		           	sortable: true,
		           	editor: comboGrupoCorporativo,
		           	renderer: renderComboEditor(comboGrupoCorporativo)
	        	},
	        	{
				   	header: getLabelFromMap('cmFeInicioAbmPersonas',helpMap,'Fecha Alta'),
        			tooltip: getToolTipFromMap('cmFeInicioAbmPersonas', helpMap),	   	
		           	dataIndex: 'feInicio',
		           	width: 100,
		           	sortable: true,
		           	renderer: formatDate(),
		           	editor: new Ext.form.DateField({name: 'feInicio', format: 'd/m/Y'})
	           	},{
				   	header: getLabelFromMap('cmFeFinAbmPersonas',helpMap,'Fecha Baja'),
        			tooltip: getToolTipFromMap('cmFeFinAbmPersonas', helpMap),	   	
		           	dataIndex: 'feFin',
		           	sortable: true,
		           	width: 100,
		           	editor: new Ext.form.DateField({name: 'feFin', format: 'd/m/Y'}),
		           	renderer: formatDate()
	           	},{
				   	header: getLabelFromMap('cmCdStatusAbmPersonas',helpMap,'Estado'),
        			tooltip: getToolTipFromMap('cmCdStatusAbmPersonas', helpMap),	   	
	           		//header: 'Estado',
	           		dataIndex: 'cdStatus',
	           		width: 80,
		           	sortable: true,
		           	editor: comboEstadoCorporativo,
		           	renderer: renderComboEditor(comboEstadoCorporativo)
	           	}
	           	]);
	           	
		//Fin Definición Column Model
		grillaCorporativo = new Ext.grid.EditorGridPanel({
	        el:'gridCorporativo',
	        store: crearGridCorporativoStore(),
			border:true,
			autoWidth: true,
	        clicksToEdit:1,
	        buttonAlign:'center',
	        cm: cm,
			buttons:[
		        		{
   							text:getLabelFromMap('abmPersonasGrCptButtonAgregar', helpMap,'Agregar'),
       						tooltip:getToolTipFromMap('abmPersonasGrCptButtonAgregar', helpMap),	
		        			//text:'Agregar',
		        			handler: function () {
		        						addNewRecordCorporativo();
		        			}
		            	},
		            	{
   							text:getLabelFromMap('abmPersonasGrCptButtonGuardar', helpMap,'Guardar'),
       						tooltip:getToolTipFromMap('abmPersonasGrCptButtonGuardar', helpMap),	
		            		//text:'Guardar',
		            		handler: function () {
		            					agregarNuevoCorporativo = false;
		            					guardarCorporativo ();
		            		}
		            	},
		            	{
   							text:getLabelFromMap('abmPersonasGrCptButtonGuardarAgregar', helpMap,'Guardar y Agregar'),
       						tooltip:getToolTipFromMap('abmPersonasGrCptButtonGuardarAgregar', helpMap),	
		            		//text:'Guardar y Agregar',
		            		handler: function () {
		            					agregarNuevoCorporativo = true;
		            					guardarCorporativo();
		            		}
		            	}, {
   							text:getLabelFromMap('abmPersonasGrCptButtonRegresar', helpMap,'Regresar'),
       						tooltip:getToolTipFromMap('abmPersonasGrCptButtonRegresar', helpMap),	
		            		//text: 'Regresar',
            					 handler: function() {
            					 				window.location.href = _ACTION_REGRESAR;
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
					pageSize:20,
					store: store,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
	                //displayMsg: 'Mostrando registros {0} - {1} of {2}',
	                //emptyMsg: "No hay registros para visualizar"
			    })
			});
	
	    //grilla.render()
	    grillaCorporativo.on('beforeedit', function(editEvent) {
	    		if (editEvent.field != "cdElemen") {
	    			if (editEvent.record.get("cdElemen") == "") {
	    				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400048', helpMap,'Primero seleccione el Corporativo'));
	    				//Ext.Msg.alert('Error', 'Primero seleccione el Corporativo');
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
			        feInicio: ''
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
						"datosCorporativo[" + i + "].cdStatus=" + recs[i].get("cdStatus") + "&" + 
						"datosCorporativo[" + i + "].feInicio=" + Ext.util.Format.date(recs[i].get("feInicio"), 'd/m/Y') + "&" + 
						"datosCorporativo[" + i + "].feFin=" + Ext.util.Format.date(recs[i].get("feFin"), 'd/m/Y') + "&";
		}
		if (recs.length > 0) {
			execConnection(_ACTION_GUARDAR_DATOS_CORPORATIVO, _params, cbkGuardarCorporativo);
		}
	}
	function cbkGuardarCorporativo (_success, _message) {
		if (_success) {
			grillaCorporativo.store.commitChanges();
			var _params = {codigoPersona: CODIGO_PERSONA, start: 0, limit: itemsPerPage};
			Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadComponentStore(grillaCorporativo, _params, function(){ if (agregarNuevoCorporativo)addNewRecordCorporativo(); } )});
		}else {
			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
		}
	}
	/************* Fin Grilla de Corporativo **************************/

	createGridDomcilios();
	createGridTelefonos();
	createGridCorporativo();


	/*********** Comienza el form principal ************************/
   var el_form = new Ext.Panel({
   		renderTo: 'formTab',
        //labelAlign: 'top',
	    title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('el_formPersonasTotalId', helpMap,'Personas')+'</span>',
        //title: 'Personas',
        //bodyStyle:'padding:5px',
        width: 700,
        items: [
        {
        listeners: {
        				beforetabchange : function(_this, newTab, currentTab) {
									if (newTab.id != '1') {
										if (CODIGO_PERSONA == "") {
	    									Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400049', helpMap,'Primero debe cargar los datos generales'));
											//Ext.Msg.alert('Error', 'Primero debe cargar los datos generales');
											return false;
										} else return true;
									}
						}
					},
        xtype:'tabpanel',
        id: 'ttabs',
	    resizeTabs:true,
	    minTabWidth: 115,
	    tabWidth:135,
	    enableTabScroll:true,
        //deferredRender: false,
        //renderHidden: true,
        //layoutOnTabChange:true,
	    width:700,
	    height:300,
            activeTab: 0,
            defaults: {labelWidth: 80},
            items:[{
                title: '<span style="color:black;font-size:10px;">'+getLabelFromMap('ttabsDatosGenerelesId', helpMap,'Datos Generales')+'</span>',
                tooltip: getToolTipFromMap('ttabsDatosGenerelesId',helpMap),	
                //title:'Datos Generales',
                layout:'fit',
                id: '1',
                items: [
                	el_formpanel
				]
            },{
                title: '<span style="color:black;font-size:10px;">'+getLabelFromMap('ttabsDatosAdicionalesId', helpMap,'Datos Adicionales')+'</span>',
                tooltip: getToolTipFromMap('ttabsDatosAdicionalesId',helpMap),	
                //title:'Datos Adicionales',
                layout:'fit',
                id: '2',
                items: [CrearDatosAdicionales()]
            },{
                title: '<span style="color:black;font-size:10px;">'+getLabelFromMap('ttabsDomiciliosId', helpMap,'Domicilios')+'</span>',
                tooltip: getToolTipFromMap('ttabsDomiciliosId',helpMap),	
                //title:'Domicilios',
                layout:'fit',
                id: '3',
                items: [grillaDomicilios] 
            },{
                title: '<span style="color:black;font-size:10px;">'+getLabelFromMap('ttabsTelefonosId', helpMap,'Tel&eacute;fonos')+'</span>',
            	tooltip: getToolTipFromMap('ttabsTelefonosId',helpMap),	
            	//title: 'Tel&eacute;fonos',
            	layout: 'fit',
                id: '4',
            	items: [grillaTelefonos]
            },{
                title: '<span style="color:black;font-size:10px;">'+getLabelFromMap('ttabsCorporativosId', helpMap,'Corporativo')+'</span>',
            	tooltip: getToolTipFromMap('ttabsCorporativosId',helpMap),	
            	//title: 'Corporativo',
            	layout: 'fit',
                id: '5',
            	items: [grillaCorporativo]
            }]
        }]
    });
    //el_form.height = el_formpanel.height;	
    el_form.render();
	/*********** Termina el form principal ************************/
	dsTipoPersonaJ.load();
	dsEstadoCivil.load();
	dsTipoIdentificador.load();
	dsSexo.load();

	dsClientesCorporativos.load();
	//dsGrupoCorporativo.load();
	dsEstadoCorporativo.load();
	dsTipoTelefono.load();
	dsTipoDomicilio.load();
	dsNacionalidad.load();
	dsTipoEmpresa.load();
	dsEstados.load();
	dsEstadoCorporativo.load();
	/*dsGrupoCorporativo.load({
		params: {codigoPersona: CODIGO_PERSONA}
	});*/
	dsClientesCorporativos.load();
	
	if (CODIGO_PERSONA != "") {
		el_formpanel.load({
				params: {codigoPersona: CODIGO_PERSONA, codigoTipoPersona: TIPO_PERSONA},
				failure: function() {
						Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400052', helpMap,'No se pudo leer los datos de la persona'));
						//Ext.Msg.alert('Error', 'No se pudo leer los datos de la persona');
						}
		});

		reloadGridDomicilio();

		reloadGridTelefono();

		reloadGridCorporativo();
	}
	
	function reloadGridTelefono() {
	    var _storeTelef = grillaTelefonos.store;
	    _storeTelef.baseParams = _storeTelef.baseParams || {};
	    _storeTelef.baseParams['codigoPersona'] = CODIGO_PERSONA;
	    _storeTelef.reload({
	    		params: {start: 0, limit: itemsPerPage},
	    		callback: function (r, options, success) {
	    				if (!success) {
	    					//Ext.Msg.alert('Error', 'No se encontraron telefonos');
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
	                         //Ext.Msg.alert('Error', 'No se encontraron domicilios');
	                         _storeDom.removeAll();
	                      }
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

	/**
	* Función usada para cambiar el texto del combo para mostrarlo en la grilla
	* en vez del código
	*/
	function renderComboEditor (combo) {
		return function (value) {
			var idx = combo.store.find(combo.valueField, value);
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
		grillaDomicilios.store.removeAll();
		grillaCorporativo.store.removeAll();
		grillaTelefonos.store.removeAll();
	}
});