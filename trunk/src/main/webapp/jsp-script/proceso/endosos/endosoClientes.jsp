<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	var _CONTEXT = '${ctx}';
	var clienteSeleccionado             = <s:property value="%{convertToJSON('smap1')}" escapeHtml="false" />;
	var _URL_OBTIENE_CLIENTE      		= '<s:url namespace="/persona" action="obtieneInformacionCliente" />';
	var _URL_CATALOGOS 					= '<s:url namespace="/catalogos" action="obtieneCatalogo" />';
	var _CATALOGO_SUCURSALES			= '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@MC_SUCURSALES_ADMIN"/>';
	var _35_urlGuardar     				= '<s:url namespace="/endosos" action="guardarEndosoNombreRFCFecha"       />';
	var _UrlImportaPersonaWS 			= '<s:url namespace="/catalogos" action="importaPersonaExtWSNoSicaps" />';
	debug('clienteSeleccionado  ===>:',clienteSeleccionado);
	var _35_panelPri;
	var cdpersonNuevo;
	var codigoCliExterno;
	var pantallaPrincipal = clienteSeleccionado.tipoPantalla;
	debug("pantallaPrincipal ===>", pantallaPrincipal);
	Ext.onReady(function() {
		
		Ext.Ajax.timeout = 1*60*60*1000; // 1 hora
		
		Ext.define('modelListadoAsegurado',{
			extend: 'Ext.data.Model',
			fields: [  	
				{type:'string',    name:'CVECLIENSIGS'		},			{type:'string',    name:'SUCEMISORA'		},			{type:'string',    name:'TIPERSONA'			},
				{type:'string',    name:'NOMCLIENTE'		},			{type:'string',    name:'NOMCLIENTE1'		},			{type:'string',    name:'NOMCLIENTE2'		},
				{type:'string',    name:'APPATERNO'			},			{type:'string',    name:'APMATERNO'			},			{type:'string',    name:'RAZONSOCIAL'		},
				{type:'string',    name:'ANECLI'			},			{type:'string',    name:'RFCCLIENTE'		},			{type:'string',    name:'CVEIFE'			},
				{type:'string',    name:'CURP'				},			{type:'string',    name:'SEXO'				},			{type:'string',    name:'CVEEDOCIVIL'		},
				{type:'string',    name:'CALLECLIENTE'		},			{type:'string',    name:'NUMCLIENTE'		},			{type:'string',    name:'CPCLIENTE'			},
				{type:'string',    name:'COLCLIENTE'		},			{type:'string',    name:'CVEMUNSIGS'		},			{type:'string',    name:'POBLACION'			},
				{type:'string',    name:'CVEEDOSIGS'		},			{type:'string',    name:'FECNACIMIENTO'		},			{type:'string',    name:'NACEXT'			},
				{type:'string',    name:'CVEOCUPACION'		},			{type:'string',    name:'CVEGIRO'			},			{type:'string',    name:'TELEFONO1'			},
				{type:'string',    name:'TELEFONO2'			},			{type:'string',    name:'TELEFONO3'			},			{type:'string',    name:'EMAIL'				},
				{type:'string',    name:'PAGINAWEB'			},			{type:'string',    name:'CANCON'			},			{type:'string',    name:'FUENTEINGRESO'		},
				{type:'string',    name:'TIPADMINISTRACION'	},			{type:'string',    name:'CARGOPUBL'			},			{type:'string',    name:'NOMCAR'			},
				{type:'string',    name:'PERCAR'			},			{type:'string',    name:'APOCLI'			},			{type:'string',    name:'DOMORI'			},
				{type:'string',    name:'NUMPAS'			},			{type:'string',    name:'CVEUSERCAPTURA'	},			{type:'string',    name:'CVEUSERAUTORIZA'	},
				{type:'string',    name:'FECALTA'			},			{type:'string',    name:'FECHACTUALIZACION'	},			{type:'string',    name:'ESTATUCLIENTE'		},
				{type:'string',    name:'DESTIPERSONA'		},			{type:'string',    name:'CVEEXTERNA'		}
			]
		});

		storeListadoAsegurado = new Ext.data.Store({
			autoDestroy: true,						model: 'modelListadoAsegurado'
		});
		
		sucursalCliente = Ext.create('Ext.data.Store', {
			model:'Generic',
			autoLoad:true,
			proxy: {
				type: 'ajax',
				url:_URL_CATALOGOS,
				extraParams : {
					catalogo:_CATALOGO_SUCURSALES,
					'params.idPadre' : '0'
				},
				reader: {
					type: 'json',
					root: 'lista'
				}
			}
		});
		
		sucursalCliente.load();
		
		var myMask = new Ext.LoadMask(Ext.getBody(), {msg:"loading..."});
		
		var cmbSucursal = Ext.create('Ext.form.field.ComboBox',{
			fieldLabel	: 'Sucursal',			name			:'cmbSucursal',				allowBlank		: false,
			editable	: true,					displayField	:'value',
			emptyText	:'Seleccione...',		valueField		:'key',						forceSelection	: true,
			queryMode	:'local',				store 			:sucursalCliente,			readOnly   : (pantallaPrincipal =="0")
		});
		
		var panelInicialPral = Ext.create('Ext.form.Panel', {
		    title: 'Datos de la P&oacute;liza',
		    id: 'panelInicialPral',
		    bodyPadding: 5,
		    defaultType: 'textfield',
		    layout      : {
				type    : 'table'
				,columns: 3
			},
			defaults 	: {
				style   : 'margin:5px;'
			},
		    items: [
				cmbSucursal,
		    	{       fieldLabel	: 'Ramo',		        name		: 'cveRamo',		        allowBlank	: false, 		type: 'numberfield',	readOnly   : (pantallaPrincipal =="0")	},
		    	{       fieldLabel	: 'P&oacute;liza',      name		: 'nmPoliza',		        allowBlank	: false,		readOnly   : (pantallaPrincipal =="0")	}
	    	]
			,buttonAlign:'center'
			//El boton unicante aparecera para polizas NO SICAPS
			,buttons: [{
				text: 'Buscar Contratante'
				,icon:'${ctx}/resources/fam3icons/icons/accept.png'
				,buttonAlign : 'center'
				, hidden : (pantallaPrincipal =="0")
				,handler: function() {
					storeListadoAsegurado.removeAll();
					datosContratante.down('[name=dsnombre]').setValue('');
					datosContratante.down('[name=dsnombre1]').setValue('');
					datosContratante.down('[name=dsapellido]').setValue('');
					datosContratante.down('[name=dsapellido1]').setValue('');
					datosContratante.down('[name=fenacini]').setValue('');
					datosContratante.down('[name=rfcContratante]').setValue('');
					datosContratante.down('[name=dsnomCompleto]').setValue('');
					
					var form = this.up('form').getForm();
					if (form.isValid()){
						Ext.Ajax.request({
                  			url     : _URL_OBTIENE_CLIENTE
                  			,params : {
                  				'params.sucursal'  : panelInicialPral.down('combo[name=cmbSucursal]').getValue()
                  				,'params.ramo'      : panelInicialPral.down('[name=cveRamo]').getValue()
                  				,'params.poliza'    : panelInicialPral.down('[name=nmPoliza]').getValue()
                  			}
                  			,success : function(response) {
                  				var json=Ext.decode(response.responseText);
                  				if(json.success!=true){
                  					centrarVentanaInterna(mensajeError(json.message));
                  				}else{
                  					var desTipoPersona = null;
                      				if(json.list[0].TIPERSONA =="1"){
                      					desTipoPersona = "FISICA"
                      				}else if(json.list[0].TIPERSONA =="2"){
                      					desTipoPersona = "MORAL"
                      				}else{
                      					desTipoPersona = "SIMPLIFICADO"
                      				}
                      				
                      				storeListadoAsegurado.removeAll();
                      				var rec = new modelListadoAsegurado({
                      					CVECLIENSIGS    : json.list[0].CVECLIENSIGS,
                      					SUCEMISORA    	: json.list[0].SUCEMISORA,
                      					TIPERSONA    	: json.list[0].TIPERSONA,
                      					DESTIPERSONA    : desTipoPersona,
                      					NOMCLIENTE    	: json.list[0].NOMCLIENTE,
                      					APPATERNO    	: json.list[0].APPATERNO,
                      					APMATERNO    	: json.list[0].APMATERNO,
                      					RAZONSOCIAL    	: json.list[0].RAZONSOCIAL,
                      					RFCCLIENTE    	: json.list[0].RFCCLIENTE,
                      					CVEIFE    		: json.list[0].CVEIFE,
                      					CURP    		: json.list[0].CURP,
                      					CALLECLIENTE    : json.list[0].CALLECLIENTE,
                      					NUMCLIENTE    	: json.list[0].NUMCLIENTE,
                      					CPCLIENTE    	: json.list[0].CPCLIENTE,
                      					FECNACIMIENTO   : json.list[0].FECNACIMIENTO
        							});
                      				storeListadoAsegurado.add(rec);
                      				
                      				importaPersonaWS("D", json.list[0].CVEEXTERNA);
                  				}
                  			}
                  			,failure : errorComunicacion
                  		});
					}
					else{
						Ext.Msg.show({
							title:'Datos incompletos',
							msg: 'Favor de introducir todos los campos requeridos',
							buttons: Ext.Msg.OK,
							icon: Ext.Msg.WARNING
						});
					}
				}
			}]
		});
		
		gridDatos = Ext.create('Ext.grid.Panel',{
			id             : 'clausulasGridId'
			,store         : storeListadoAsegurado
			,title		   : 'Datos del Contratante'
			,style         : 'margin:5px'
			,height		   : 150
			,columns       : [
				{	header	: 'Cliente<br/>SIGS',		dataIndex : 'CVECLIENSIGS',		flex:2 },
				{	header  : 'Suc. Emisora',			dataIndex : 'SUCEMISORA',		flex:2 },
				{	header  : 'Tipo Persona',			dataIndex : 'TIPERSONA',		flex:2,		hidden: true	 },
				{	header  : 'Tipo Persona',			dataIndex : 'DESTIPERSONA',		flex:2 },
				{	header  : 'Nombre Cliente',			dataIndex : 'NOMCLIENTE',		flex:2 },
				{	header  : 'Nombre Cliente',			dataIndex : 'NOMCLIENTE1',		flex:2,		hidden: true	 },
				{	header  : 'Nombre Cliente',			dataIndex : 'NOMCLIENTE1',		flex:2,		hidden: true	 },
				{	header  : 'Apellido<br/>paterno',	dataIndex : 'APPATERNO' ,		flex:2 },
				{	header  : 'Apellido<br/>materno' ,	dataIndex : 'APMATERNO'	 ,		flex:2 },
				{	header  : 'Raz&oacute;n social',	dataIndex : 'RAZONSOCIAL',		flex:2 },
				{	header  : 'RFC',					dataIndex : 'RFCCLIENTE',		flex:2 },
				{	header  : 'CURP',					dataIndex : 'CURP',				flex:2 },
				{	header  : 'Fecha nacimiento',		dataIndex : 'FECNACIMIENTO',	flex:2 }
			],
			listeners: {
				itemclick: function(dv, record, item, index, e) {
					datosContratante.down('[name=dsnombre]').setValue(record.get('NOMCLIENTE1'));
					datosContratante.down('[name=dsnombre1]').setValue(record.get('NOMCLIENTE2'));
					datosContratante.down('[name=dsnomCompleto]').setValue(record.get('NOMCLIENTE'));
					datosContratante.down('[name=dsapellido]').setValue(record.get('APPATERNO'));
					datosContratante.down('[name=dsapellido1]').setValue(record.get('APMATERNO'));
					datosContratante.down('[name=fenacini]').setValue(record.get('FECNACIMIENTO'));
					datosContratante.down('[name=rfcContratante]').setValue(record.get('RFCCLIENTE'));
				}
			}
		});
		
		var datosContratante = Ext.create('Ext.form.Panel',{
   	        id          : 'datosContratante',
   	        bodyPadding : 5,
   	        title: 'Cambio del contratante',
   	        defaults 	: {
				style   : 'margin:5px;'
			},
			items       :[
				{    xtype       : 'textfield',			labelWidth: 150,		fieldLabel : 'Primer nombre',				name       : 'dsnombre'
				    ,width		 : 400,					allowBlank	: (pantallaPrincipal !="0"),    						hidden : (pantallaPrincipal !="0")
				},
				{    xtype       : 'textfield',			labelWidth: 150,		fieldLabel : 'Segundo nombre',				name       : 'dsnombre1'
				    ,width		 : 400,					hidden      : (pantallaPrincipal !="0")
				},
				{    xtype       : 'textfield',			labelWidth: 150,		fieldLabel : 'Nombre Completo',				name       : 'dsnomCompleto'
				    ,width		 : 400,					allowBlank	: (pantallaPrincipal =="0"),							hidden : (pantallaPrincipal =="0")
				},
				{    xtype       : 'textfield',			labelWidth: 150,		fieldLabel : 'Apellido Paterno',			name       : 'dsapellido'
				    ,width		 : 400,					allowBlank	: false
				},
				{    xtype       : 'textfield',			labelWidth: 150,		fieldLabel : 'Apellido Materno',			name       : 'dsapellido1'
				    ,width		 : 400,					allowBlank	: false
				},
				{	xtype		: 'datefield',			labelWidth: 150,		fieldLabel	: 'Fecha nacimiento',			name	   : 'fenacini'
					,format		: 'd/m/Y',				allowBlank	: false
				},
				{    xtype       : 'textfield',			labelWidth: 150,		fieldLabel : 'RFC',							name       : 'rfcContratante'
				    ,width		 : 400,					allowBlank	: false
				}
   	        ]
   	    });
		
		
	    _35_panelPri=Ext.create('Ext.panel.Panel',{
   	        renderTo     : 'maindivHist'
   	        , id : '_35_panelPri'
   	        ,defaults    :
   	        {
   	            style : 'margin : 5px;'
   	        }
   	        ,items       :
   	        [
   	            //_35_formLectura
   	            panelInicialPral
   	            ,gridDatos
   	            ,datosContratante
   	        ]
   	        ,buttonAlign : 'center'
   	        ,buttons     :
   	        [
   	            {
   	                text      : 'Confirmar endoso'
   	                ,icon     : '${ctx}/resources/fam3icons/icons/key.png'
   	                ,handler: function() {
   	                	var formPanel = this.up().up();
   	                	debug(datosContratante.isValid());
   	                	
   	                	if(datosContratante.isValid()){
 							var submitValues={};
							params = {
								'cdperson'    : clienteSeleccionado.pv_cdperson,
								'cdtipide'    : clienteSeleccionado.CDTIPIDE,
								'cdideper'    : clienteSeleccionado.CDIDEPER,
								'dsnombre'    : datosContratante.down('[name=dsnombre]').getValue(),
								'cdtipper'    : clienteSeleccionado.CDTIPPER,
								'otfisjur'    : clienteSeleccionado.OTFISJUR,
								'otsexo'      : clienteSeleccionado.OTSEXO,
								'fenacimi'    : datosContratante.down('[name=fenacini]').getValue(),
								'cdrfc'       : datosContratante.down('[name=rfcContratante]').getValue(),
								'dsemail'     : clienteSeleccionado.DSEMAIL,
								'dsnombre1'   : datosContratante.down('[name=dsnombre1]').getValue(),
								'dsnombreComp': datosContratante.down('[name=dsnomCompleto]').getValue(),
								'dsapellido'  : datosContratante.down('[name=dsapellido]').getValue(),
								'dsapellido1' : datosContratante.down('[name=dsapellido1]').getValue(),
								'feingreso'   : clienteSeleccionado.FEINGRESO,
								'cdnacion'    : clienteSeleccionado.CDNACION,
								'canaling'    : clienteSeleccionado.CANALING,
								'conducto'    : clienteSeleccionado.CONDUCTO,
								'ptcumupr'    : clienteSeleccionado.PTCUMUPR,
								'residencia'  : clienteSeleccionado.RESIDENCIA,
								'nongrata'    : clienteSeleccionado.NONGRATA,
								'cdideext'    : clienteSeleccionado.CDIDEEXT,
								'cdestciv'    : clienteSeleccionado.CDESTCIV,
								'cdsucemi'    : clienteSeleccionado.CDSUCEMI,
								'cdunieco'    : clienteSeleccionado.pv_cdunieco,
								'cdramo'      : clienteSeleccionado.pv_cdramo,
								'estado'      : clienteSeleccionado.pv_estado,
								'nmpoliza'    : clienteSeleccionado.pv_nmpoliza,
								'nmpoliex'    : clienteSeleccionado.NMPOLIEX,
								'sucursalEntrada': panelInicialPral.down('combo[name=cmbSucursal]').getValue(),
                  				'ramoEntrada'   : panelInicialPral.down('[name=cveRamo]').getValue(),
                  				'polizaEntrada' : panelInicialPral.down('[name=nmPoliza]').getValue(),
                  				'tipoPantalla'  : clienteSeleccionado.tipoPantalla,
                  				'cdpersonNuevo' : cdpersonNuevo,
                  				'codigoCliExterno'  : codigoCliExterno
							}
							
							submitValues['smap1']= params;
							Ext.Ajax.request( {
	   						    url: _35_urlGuardar,
	   						    jsonData: Ext.encode(submitValues),
	   						    success:function(response,opts){
	   						    	 panelInicialPral.setLoading(false);
	   						         var jsonResp = Ext.decode(response.responseText);
	   						         
	   						         var callbackRemesa = function(){
	   						             marendNavegacion(2);
	   						         };
	   						         
	   						         mensajeCorrecto("Endoso",jsonResp.respuesta,function(){
	   						      		if(pantallaPrincipal =="1"){
		   						      		cdpersonNuevo = 0;
		   						     		codigoCliExterno = 0;
		   						     		panelInicialPral.down('combo[name=cmbSucursal]').setValue('');
		                  					panelInicialPral.down('[name=cveRamo]').setValue('');
		                  					panelInicialPral.down('[name=nmPoliza]').setValue('');
			   						     	datosContratante.down('[name=dsnombre]').setValue('');
			   								datosContratante.down('[name=dsnombre1]').setValue('');
			   								datosContratante.down('[name=dsnomCompleto]').setValue('');
			   								datosContratante.down('[name=dsapellido]').setValue('');
			   								datosContratante.down('[name=dsapellido1]').setValue('');
			   								datosContratante.down('[name=fenacini]').setValue('');
			   								datosContratante.down('[name=rfcContratante]').setValue('');
			   								storeListadoAsegurado.removeAll();
	   						      			_generarRemesaClic2(
			   						      	         true
			   						      	         ,panelInicialPral.down('combo[name=cmbSucursal]').getValue()
			   						      	         ,panelInicialPral.down('[name=cveRamo]').getValue()
			   						      	         ,'M'
			   						      	         ,panelInicialPral.down('[name=nmPoliza]').getValue()
			   						      	         ,callbackRemesa
		   						      	     );
	   						      		}else{
	   						      		debug("Entra a la opcion 2");
	   						      			_generarRemesaClic(
			   						      	         true
			   						      	         ,clienteSeleccionado.pv_cdunieco
			   						      	         ,clienteSeleccionado.pv_cdramo
			   						      	         ,clienteSeleccionado.pv_estado
			   						      	         ,clienteSeleccionado.pv_nmpoliza
			   						      	         ,callbackRemesa
		   						      	     );
	   						      		}
	   						      	 });
	   						    },
	   						    failure:function(response,opts){
	   						        panelInicialPral.setLoading(false);
	   						        Ext.Msg.show({
	   						            title:'Error',
	   						            msg: 'Error de comunicaci&oacute;n',
	   						            buttons: Ext.Msg.OK,
	   						            icon: Ext.Msg.ERROR
	   						        });
	   						    }
	   						});
   						}else {
   							myMask.hide();
   							Ext.Msg.show({
   								title: 'Aviso',
   								msg: 'Complete la informaci&oacute;n requerida',
   								buttons: Ext.Msg.OK,
   								icon: Ext.Msg.WARNING
   							});
   						}
   	            	}
   	            }
   	        ]
   	    });
	    
		
		////// loaders //////
		//VALORES DE ENTRADA PARA POLIZAS SICAPS
		if(clienteSeleccionado.tipoPantalla == "0"){
			debug("Entra al if");
			debug("VALOR DE ENTRADA ==> ",clienteSeleccionado.pv_cdunieco, clienteSeleccionado.RAMO, clienteSeleccionado.NMPOLIEX);
			panelInicialPral.down('combo[name=cmbSucursal]').setValue(clienteSeleccionado.pv_cdunieco);
			panelInicialPral.down('[name=cveRamo]').setValue(clienteSeleccionado.RAMO);
			panelInicialPral.down('[name=nmPoliza]').setValue(clienteSeleccionado.NMPOLIEX);
			var desTipoPersona = null;
				if(clienteSeleccionado.OTFISJUR =="F"){
					desTipoPersona = "FISICA"
				}else if(clienteSeleccionado.OTFISJUR =="M"){
					desTipoPersona = "MORAL"
				}else{
					desTipoPersona = "SIMPLIFICADO"
				}
			
			var rec = new modelListadoAsegurado({
					CVECLIENSIGS    : clienteSeleccionado.CDIDEPER,
					SUCEMISORA    	: clienteSeleccionado.pv_cdunieco,
					TIPERSONA    	: clienteSeleccionado.OTFISJUR,
					DESTIPERSONA    : desTipoPersona,
					NOMCLIENTE    	: clienteSeleccionado.DSNOMBRE+" "+clienteSeleccionado.DSNOMBRE1,
					NOMCLIENTE1    	: clienteSeleccionado.DSNOMBRE,
					NOMCLIENTE2    	: clienteSeleccionado.DSNOMBRE1,
					APPATERNO    	: clienteSeleccionado.DSAPELLIDO,
					APMATERNO    	: clienteSeleccionado.DSAPELLIDO1,
					RAZONSOCIAL    	: null,
					RFCCLIENTE    	: clienteSeleccionado.CDRFC,
					CVEIFE    		: null,
					CURP    		: null,
					CALLECLIENTE    : null,//clienteSeleccionado.OTFISJUR,
					NUMCLIENTE    	: null,//clienteSeleccionado.OTFISJUR,
					CPCLIENTE    	: null,//clienteSeleccionado.OTFISJUR,
					FECNACIMIENTO   : clienteSeleccionado.FENACIMI
			});
			storeListadoAsegurado.add(rec);
		}
    });
	
	function importaPersonaWS(esSaludD, codigoCliExt){
		Ext.Ajax.request({
			url       : _UrlImportaPersonaWS
			,params: {
				'params.esSalud':  esSaludD,
				'params.codigoCliExt':  codigoCliExt
			}
			,success  : function(response){
				//_p22_formDatosAdicionales().setLoading(false);
				var json = Ext.decode(response.responseText);
				debug('response text:',json);
				if(json.exito){
					debug("Valores de recuperacion ==> ",json.params.cdpersonNuevo, json.params.codigoCliExt)
					cdpersonNuevo = json.params.cdpersonNuevo;
					codigoCliExterno  = json.params.codigoCliExt;
				}else{
					mensajeError("Error al Editar Cliente, vuelva a intentarlo.");
				}
			}
			,failure  : function(){
				errorComunicacion(null,'En importar persona. Consulte a soporte.');
			}
		});
	}
//////funciones //////
///////////////////////
<%@ include file="/jsp-script/proceso/documentos/scriptImpresionRemesaEmisionEndoso.jsp"%>
</script>
<div id="maindivHist" style="height:500px;"></div>