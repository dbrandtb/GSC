function cargarPaginacion(ntramite,nfactura){
    debug("######ENTRA#####");
    storeAseguradoFactura.removeAll();
    var params = {
        'smap.ntramite'  : ntramite
        ,'smap.nfactura' : nfactura
    };
    cargaStorePaginadoLocal(storeAseguradoFactura, _URL_OBTENERSINIESTROSTRAMITE, 'slist1', params, function(options, success, response){
        if(success){
            var jsonResponse = Ext.decode(response.responseText);
        }else{
            centrarVentanaInterna(showMessage('Error', 'Error al obtener los datos',Ext.Msg.OK, Ext.Msg.ERROR));
        }
    });
}

// (EGS)
function calculaEdad(){
    var fecha = ''; 
    fecha = fecha.concat(_fenacimi.substr(6,4),_fenacimi.substr(2,4),_fenacimi.substr(0,2));
    _edad = calculaAniosTranscurridos(new Date(fecha),new Date());
}

//1.- Regresar a Mesa de Control
function _11_regresarMC() {
    debug('_11_regresarMC');
    Ext.create('Ext.form.Panel').submit({
        url             : _URL_MESACONTROL
        ,standardSubmit : true
        ,params         : {
            'smap1.gridTitle'       : 'Siniestros en espera'
            ,'smap2.pv_cdtiptra_i'  : _11_params.CDTIPTRA
        }
    });
}

//2.- Revision de Documentos del siniestro
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

//3.- Rechazar tramite
function _11_rechazarTramiteSiniestro() {
    var motivoRechazo= Ext.create('Ext.form.ComboBox',{
        id:'motivoRechazo',         name:'smap1.cdmotivo',      fieldLabel: 'Motivo',       store: storeRechazos,
        queryMode:'local',          displayField: 'value',      valueField: 'key',          allowBlank:false,
        blankText:'El motivo es un dato requerido',             editable:false,             labelWidth : 150,
        width: 600,                 emptyText:'Seleccione ...',
        listeners: {
            select: function(combo, records, eOpts){
                panelRechazarReclamaciones.down('[name=smap1.incisosRechazo]').setValue('');
                panelRechazarReclamaciones.down('[name=smap1.comments]').setValue('');
                storeIncisosRechazos.removeAll();
                storeIncisosRechazos.load({
                    params: {
                        'params.pv_cdmotivo_i' : records[0].get('key'),
                        'params.pv_cdramo_i'   : _11_params.CDRAMO,
                        'params.pv_cdtipsit_i' : _11_params.CDTIPSIT
                    }
                });
            }
        }
    });
    var textoRechazo = Ext.create('Ext.form.field.TextArea', {
        fieldLabel: 'Descripci&oacute;n modificado',            labelWidth: 150,            width: 600
        ,name:'smap1.comments',                                 height: 250,                allowBlank: false
        ,blankText:'La descripci&oacute;n es un dato requerido'
    });
    var incisosRechazo= Ext.create('Ext.form.ComboBox',{
        id:'incisosRechazo',                            name:'smap1.incisosRechazo',                        fieldLabel: 'Incisos Rechazo',
        store: storeIncisosRechazos,                    queryMode:'local',                                  displayField: 'value',
        valueField: 'key',                              blankText:'El motivo es un dato requerido',         editable:false,
        labelWidth : 150,                               width: 600,                                         emptyText:'Seleccione ...',
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
            motivoRechazo,incisosRechazo,textoRechazo,{
                xtype       : 'radiogroup'
                ,fieldLabel : 'Mostrar al agente'
                ,columns    : 2
                ,width      : 250
                ,style      : 'margin:5px;'
                ,hidden     : _GLOBAL_CDSISROL===RolSistema
                ,items      : [
                    {
                        boxLabel    : 'Si'
                        ,itemId     : 'SWAGENTE2'
                        ,name       : 'SWAGENTE2'
                        ,inputValue : 'S'
                        ,checked    : _GLOBAL_CDSISROL===RolSistema.Agente
                    },{
                        boxLabel    : 'No'
                        ,name       : 'SWAGENTE2'
                        ,inputValue : 'N'
                        ,checked    : _GLOBAL_CDSISROL!==RolSistema.Agente
                    }
                ]
            }
        ],
        buttonAlign:'center',
        buttons: [{
            text: 'Rechazar'
            ,icon:_CONTEXT+'/resources/fam3icons/icons/accept.png'
            ,buttonAlign : 'center'
            ,handler: function() {
                if (panelRechazarReclamaciones.form.isValid()) {
                    panelRechazarReclamaciones.form.submit({
                        waitMsg:'Procesando...',
                        params: {
                            'smap1.ntramite' : _11_params.NTRAMITE,
                            'smap1.status'   : _RECHAZADO
                            ,'smap1.swagente' : _fieldById('SWAGENTE2').getGroupValue()
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
                                        'paramsO.tipopago'      : _11_params.OTVALOR02
                                    },
                                    success: function(response, opt) {
                                        var jsonRes=Ext.decode(response.responseText);
                                        if(jsonRes.success == true){
                                            var numRand=Math.floor((Math.random()*100000)+1);
                                            var windowVerDocu=Ext.create('Ext.window.Window', {
                                                title          : 'Carta de Rechazo del Siniestro'
                                                ,width         : 700
                                                ,height        : 500
                                                ,collapsible   : true
                                                ,titleCollapse : true
                                                ,html          : '<iframe innerframe="'+numRand+'" frameborder="0" width="100" height="100"'
                                                                    +'src="'+panDocUrlViewDoc+'?subfolder=' + panelInicialPral.down('[name=idNumTramite]').getValue() + '&filename=' + nombreReporteRechazo +'">'
                                                                    +'</iframe>'
                                                ,listeners     : {
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
                                    _11_regresarMC();
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
        }]
    });
    windowLoader = Ext.create('Ext.window.Window', {
        title           : 'Rechazar Tr&aacute;mite'
        ,modal          : true
        ,buttonAlign    : 'center'
        ,items          : [
            panelRechazarReclamaciones
        ]
    }).show();
    centrarVentana(windowLoader);
}

//4.- Turnar al area medica
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
        height      : 430,
        autoScroll  : true,
        items       : [
            Ext.create('Ext.form.Panel', {
                title: 'Turnar al Area M&eacute;dica',
                width: 650,
                url: _URL_ACTSTATUS_TRAMITE,
                bodyPadding: 5,
                items: [comentariosText,{
                    xtype       : 'radiogroup'
                    ,fieldLabel : 'Mostrar al agente'
                    ,columns    : 2
                    ,width      : 250
                    ,style      : 'margin:5px;'
                    ,hidden     : _GLOBAL_CDSISROL===RolSistema.Agente
                    ,items      : [
                        {
                            boxLabel    : 'Si'
                            ,itemId     : 'SWAGENTE3'
                            ,name       : 'SWAGENTE3'
                            ,inputValue : 'S'
                            ,checked    : _GLOBAL_CDSISROL===RolSistema.Agente
                        },{
                            boxLabel    : 'No'
                            ,name       : 'SWAGENTE3'
                            ,inputValue : 'N'
                            ,checked    : _GLOBAL_CDSISROL!==RolSistema.Agente
                        }
                    ]
                }],
                buttonAlign:'center',
                buttons: [{
                    text: 'Turnar'
                    ,icon:_CONTEXT+'/resources/fam3icons/icons/accept.png'
                    ,buttonAlign : 'center'
                    ,handler: function() {
                        if (this.up().up().form.isValid()) {
                            this.up().up().form.submit({
                                waitMsg:'Procesando...',
                                params: {
                                    'smap1.ntramite' :  _11_params.NTRAMITE,
                                    'smap1.status'   : _STATUS_TRAMITE_EN_REVISION_MEDICA
                                    ,'smap1.rol_destino'     : 'medajustador'
                                    ,'smap1.usuario_destino' : ''
                                    ,'smap1.swagente' : _fieldById('SWAGENTE3').getGroupValue()
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
                                    Ext.Ajax.request( {
                                        url     :  _URL_NOMBRE_TURNADO
                                        ,params :  {
                                            'params.ntramite': _11_params.NTRAMITE,
                                            'params.rolDestino': 'medajustador'
                                        }
                                        ,success : function (response) {
                                            var usuarioTurnadoSiniestro = Ext.decode(response.responseText).usuarioTurnadoSiniestro;
                                            mensajeCorrecto('&Eacute;XITO','Se ha turnado correctamente a: '+usuarioTurnadoSiniestro,function(){
                                                windowLoader.close();
                                                Ext.Ajax.request({
                                                    url     : _URL_ACTUALIZA_TURNADOMC
                                                    ,params : {           
                                                        'params.ntramite': _11_params.NTRAMITE
                                                    }
                                                    ,success : function (response) {
                                                        _11_regresarMC();
                                                    },
                                                    failure : function (){
                                                        me.up().up().setLoading(false);
                                                        centrarVentanaInterna(Ext.Msg.show({
                                                            title:'Error',
                                                            msg: 'Error de comunicaci&oacute;n',
                                                            buttons: Ext.Msg.OK,
                                                            icon: Ext.Msg.ERROR
                                                        }));
                                                    }
                                                });
                                            });
                                        },
                                        failure : function () {
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
                }]
            })
        ]
    }).show();
    centrarVentana(windowLoader);
}

//5.- Solicitar Pago 
function _11_solicitarPago(){
    //1.- Verificamos que el tramite ya esta pagado
    if(_11_params.STATUS  == _STATUS_TRAMITE_CONFIRMADO){
        mensajeWarning('Ya se ha solicitado el pago para este tr&aacute;mite.');
        return;
    }else{
        Ext.Ajax.request({
            url  : _URL_EXISTE_COBERTURA
            ,params:{
                'params.ntramite'  : _11_params.NTRAMITE,
                'params.tipoPago'  : _11_params.OTVALOR02
            }
            ,success : function (response) {
                //Obtenemos los datos
                var valCobertura = Ext.decode(response.responseText).datosValidacion;
                var i = 0;
                var totalConsumido = 0;
                var banderaExisteCobertura = 0;
                var resultCobertura= "";
                if(valCobertura.length > 0){
                    //Mostramos el mensaje de Error y no podra continuar
                    for(var i = 0; i < valCobertura.length; i++){
                        banderaExisteCobertura = "1";
                        resultCobertura = resultCobertura + 'La Factura ' + valCobertura[i].NFACTURA + ' del siniestro '+ valCobertura[i].NMSINIES+ ' requiere actualizar la cobertura no amparada. <br/>';
                    }
                    if(banderaExisteCobertura == "1"){
                        centrarVentanaInterna(mensajeWarning(resultCobertura));
                    }
                }else{
                	
                	Ext.Ajax.request({
                        url : _UrlValidaDatosEstudiosFacturasTramite
                        ,params:{
                            'params.ntramite' : _11_params.NTRAMITE
                        }
                        ,success : function (responseDatos){
                            var jsonRespuestaDatos =Ext.decode(responseDatos.responseText);
                            
                            if(jsonRespuestaDatos.success == true){
                                if( !Ext.isEmpty(jsonRespuestaDatos.params) && !Ext.isEmpty(jsonRespuestaDatos.params.FALTAN_DATOS_ESTUDIOS)
                                		&& jsonRespuestaDatos.params.FALTAN_DATOS_ESTUDIOS == "S"){
                                	
                                	centrarVentanaInterna(mensajeWarning(jsonRespuestaDatos.mensaje));
                                	
                                }else{

                                	Ext.Ajax.request({
                                        url : _URL_MONTO_PAGO_SINIESTRO
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
                                                    //_11_mostrarSolicitudPago(); ...1
                                                    _11_validaProveedorPagoDirecto(); // (EGS) validamos solo un proveedor en reclamo pago directo
                                                    //_11_validaAseguroLimiteCoberturas(); // (EGS) se comenta aquí pero se agrega en funcion _11_validaProveedorPagoDirecto()
                                                }else{
                                                    //Verificamos si tiene la validacion del dictaminador medico
                                                    Ext.Ajax.request({
                                                        url  : _URL_VAL_AJUSTADOR_MEDICO
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
                                                                        //_11_mostrarSolicitudPago(); ..2
                                                                        _11_validaAseguroLimiteCoberturas();
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
            },
            failure : function () {
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

//5.1.- Mostrar solicitud de pago 
//(EGS) Validamos solo un proveedor en reclamo pago directo
function _11_validaProveedorPagoDirecto(){
    var myMask = new Ext.LoadMask(Ext.getBody(),{msg:"loading..."});
    myMask.show();
    Ext.Ajax.request({
        url     :   _URL_VALIDAR_PROVEEDOR_PD
        ,params :{
            'params.ntramite'   : _11_params.NTRAMITE
        }
        ,success : function(response,opts) {
            json = Ext.decode(response.responseText);
            var mensaje = json.mensaje;
            debug("success...",response.responseText);
            if(mensaje > 1){
                myMask.hide();
                centrarVentanaInterna(Ext.Msg.show({
                    title: 'No es posible solicitar el pago',
                    msg : 'Est&aacute; tratando de enviar un Pago Directo, para diferentes proveedores',
                    buttons: Ext.Msg.OK,
                    icon: Ext.Msg.ERROR
                }));
            }else{
                _11_validaAseguroLimiteCoberturas();
            }
        }
        ,failure : function(response,opts){
            var obj = Ext.decode(response.responseText);
            var mensaje = obj.mensaje;
            debug("failure...",obj.mensaje);
            centrarVentanaInterna(Ext.Msg.show({
                title: 'Error',
                msg: Ext.isEmpty(mensaje) ? 'Error de comunicaci&oacute;n' : mensaje,
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.ERROR
            }));
        }
    });
}


//(EGS) se crea como función para simular seleccion de poliza en grid que muestra lista de polizas
function eligePoliza(record,panelListadoAsegurado,modPolizasAsegurado){
	_11_fueraVigencia = "0";	// (EGS) reiniciamos valor
	//1.- Validamos que el asegurado este vigente
	if(record.desEstatusCliente=="Vigente") {
		var valorFechaOcurrencia;
		var valorFechaOcu = panelListadoAsegurado.query('datefield[name=dtfechaOcurrencias]')[0].rawValue;
		valorFechaOcurrencia = new Date(valorFechaOcu.substring(6,10)+"/"+valorFechaOcu.substring(3,5)+"/"+valorFechaOcu.substring(0,2));
		var valorFechaInicial = new Date(record.feinicio.substring(6,10)+"/"+record.feinicio.substring(3,5)+"/"+record.feinicio.substring(0,2));
		var valorFechaFinal =   new Date(record.fefinal.substring(6,10)+"/"+record.fefinal.substring(3,5)+"/"+record.fefinal.substring(0,2));
		var valorFechaAltaAsegurado = new Date(record.faltaAsegurado.substring(6,10)+"/"+record.faltaAsegurado.substring(3,5)+"/"+record.faltaAsegurado.substring(0,2));
		Ext.Ajax.request({
			url     : _URL_VALIDA_STATUSASEG
			,params:{
				'params.cdperson'  : panelListadoAsegurado.down('combo[name=cmbAseguradoAfect]').getValue(),
				'params.feoocurre' : valorFechaOcurrencia,
				'params.nmpoliza'  : record.nmpoliza
			}
			,success : function (response) {
				json = Ext.decode(response.responseText);
				if(Ext.decode(response.responseText).validacionGeneral =="V"){
					if((valorFechaOcurrencia <= valorFechaFinal) && (valorFechaOcurrencia >= valorFechaInicial)){
						if( valorFechaOcurrencia >= valorFechaAltaAsegurado ) {
							_11_fueraVigencia = "1";	// (EGS) indicamos que siniestro sale de vigencia de la poliza
							panelListadoAsegurado.down('[name="cdUniecoAsegurado"]').setValue(record.cdunieco);
							panelListadoAsegurado.down('[name="cdRamoAsegurado"]').setValue(record.cdramo);
							panelListadoAsegurado.down('[name="estadoAsegurado"]').setValue(record.estado);
							panelListadoAsegurado.down('[name="nmPolizaAsegurado"]').setValue(record.nmpoliza);
							panelListadoAsegurado.down('[name="nmSoliciAsegurado"]').setValue(record.nmsolici);
							panelListadoAsegurado.down('[name="nmSuplemAsegurado"]').setValue(record.nmsuplem);
							panelListadoAsegurado.down('[name="nmSituacAsegurado"]').setValue(record.nmsituac);
							panelListadoAsegurado.down('[name="cdTipsitAsegurado"]').setValue(record.cdtipsit);
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
				 }
			},
			failure : function (){
				me.up().up().setLoading(false);
				centrarVentanaInterna(Ext.Msg.show({
					title:'Error',
					msg: 'Error de comunicaci&oacute;n',
					buttons: Ext.Msg.OK,
					icon: Ext.Msg.ERROR
				}));
			}
		});
	}else{
		// El asegurado no se encuentra vigente
		centrarVentanaInterna(Ext.Msg.show({
			title:'Error',
			msg: 'El asegurado de la p&oacute;liza seleccionado no se encuentra vigente',
			buttons: Ext.Msg.OK,
			icon: Ext.Msg.ERROR
		}));
		panelListadoAsegurado.down('combo[name=cmbAseguradoAfect]').setValue("");
		modPolizasAsegurado.hide();
		//limpiarRegistros();
	}

}

//5.2.- Validamos si existe coberturas que no cubre para su validacion
function _11_validaAseguroLimiteCoberturas(){
    var myMask = new Ext.LoadMask(Ext.getBody(), {msg:"loading..."});
    myMask.show();
    Ext.Ajax.request({
        url     : _URL_VALIDA_COBASEGURADOS
        ,params:{
            'params.ntramite'  : _11_params.NTRAMITE
        }
        ,success : function (response) {
            json = Ext.decode(response.responseText);
            if(json.success==false){
                myMask.hide();
                centrarVentanaInterna(mensajeWarning(json.msgResult));
            }else{
                myMask.hide();
                _11_validaImporteAseguradoTramite();
            }
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

//5.3.- Validamos el monto total del importe de siniestro por asegurado 
function _11_validaImporteAseguradoTramite(){
	procesoContinuar = "1";
    validaGralImpTramFactura(_11_params.NTRAMITE,null,_tipoPago,procesoContinuar);
}

//5.4.- Mostrar solicitud de pago 
function _11_mostrarSolicitudPago(){
    var myMask = new Ext.LoadMask(Ext.getBody(), {msg:"loading..."});
    msgWindow = Ext.Msg.show({
        title: 'Aviso',
            msg: '&iquest;Esta seguro que desea solicitar el pago?',
            buttons: Ext.Msg.YESNO,
            icon: Ext.Msg.QUESTION,
            fn: function(buttonId, text, opt){
                if(buttonId == 'yes'){
                    
                    if(_tipoProducto =="11"){
                        Ext.Ajax.request({
                            url: _URL_SOLICITARPAGO,
                            params: {
                                'params.pv_ntramite_i' : _11_params.NTRAMITE,
                                'params.pv_tipmov_i'   : _11_params.OTVALOR02
                            },
                            success: function(response, opts) {
                                var respuesta = Ext.decode(response.responseText);
                                if(respuesta.success){
                                    myMask.hide();
                                    centrarVentanaInterna(mensajeCorrecto('&Eacute;XITO','El pago se ha solicitado con &eacute;xito.',function(){
                                        _11_regresarMC();
                                    }));
                                }else {
                                    myMask.hide();
                                    centrarVentanaInterna(mensajeError(respuesta.mensaje));
                                }
                            },
                            failure: function(){
                                myMask.hide();
                                centrarVentanaInterna(mensajeError('No se pudo solicitar el pago.'));
                            }
                        });
                    }else{
                        storeConceptoPago.load({
                            params : {
                                'params.cdramo': _tipoProducto
                            }
                        });
                        debug("Valor del Asegurado ===> ",_11_params);
                        storeAsegurados2.load({
                            params:{
                                'params.cdunieco': _11_params.CDUNIECO,
                                'params.cdramo': _tipoProducto,
                                'params.estado': _11_params.ESTADO,
                                'params.nmpoliza': _11_params.NMPOLIZA
                            }
                        });
                        
                        var pagocheque = Ext.create('Ext.form.field.ComboBox', {
                            colspan    :2,              fieldLabel      : 'Destino Pago',   name            :'destinoPago',
                            allowBlank : false,         editable        : true,         displayField    : 'value',
                            valueField:'key',           forceSelection  : true,         width           :350,
                            queryMode    :'local',      store           : storeDestinoPago
                        });

                        var concepPago = Ext.create('Ext.form.field.ComboBox', {
                            colspan    :2,              fieldLabel      : 'Concepto Pago',  name            :'concepPago',
                            allowBlank : false,         editable        : true,             displayField    : 'value',
                            valueField:'key',           forceSelection  : true,             width           :350,
                            queryMode    :'local',      store           : storeConceptoPago
                        });
                        
                        var idCveBeneficiario = Ext.create('Ext.form.field.Number', {
                            colspan    :2,              fieldLabel      : 'Id. Beneficiario',   name            :'idCveBeneficiario',
                            allowBlank : false,         editable        : true,             width           :350
                        });
                        
                        var cmbBeneficiario= Ext.create('Ext.form.ComboBox',{
                            name:'cmbBeneficiario',         fieldLabel: 'Beneficiario',         queryMode: 'local'/*'remote'*/,         displayField: 'value',
                            valueField: 'key',              editable:true,                      forceSelection : true,      matchFieldWidth: false,
                            queryParam: 'params.cdperson',  minChars  : 2,                      store : storeAsegurados2,   triggerAction: 'all',
                            width        : 350,
                            allowBlank  : _tipoPago == _TIPO_PAGO_DIRECTO,
                            hidden      : _tipoPago == _TIPO_PAGO_DIRECTO,
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
                        
                        Ext.Ajax.request({
                            url     : _URL_VAL_CAUSASINI
                            ,params : {
                                'params.cdramo'   : _11_params.CDRAMO,
                                'params.cdtipsit' : _cdtipsitProducto,
                                'params.causaSini': 'IDBENEFI',
                                'params.cveCausa' : _tipoPago
                            }
                            ,success : function (response){
                                var datosExtras = Ext.decode(response.responseText);
                                if(Ext.decode(response.responseText).datosInformacionAdicional != null){
                                    var cveCauSini=Ext.decode(response.responseText).datosInformacionAdicional[0];
                                    debug("Valor de Respuesta ==> ",_11_params.CDRAMO,_cdtipsitProducto,_tipoPago,cveCauSini);
                                    if(cveCauSini.REQVALIDACION =="S"){
                                        //Visualizamos el campo
                                        panelModificacion.down('[name=idCveBeneficiario]').show();
                                    }else{
                                        //ocultamos el campo
                                        panelModificacion.down('[name=idCveBeneficiario]').setValue('0');
                                        panelModificacion.down('[name=idCveBeneficiario]').hide();
                                    }
                                }
                            },
                            failure : function (){
                                me.up().up().setLoading(false);
                                centrarVentanaInterna(Ext.Msg.show({
                                    title:'Error',
                                    msg: 'Error de comunicaci&oacute;n',
                                    buttons: Ext.Msg.OK,
                                    icon: Ext.Msg.ERROR
                                }));
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
                            ,success : function (response) {
                                if(Ext.decode(response.responseText).listaMesaControl != null) {
                                    var json=Ext.decode(response.responseText).listaMesaControl[0];
                                    cdramoTramite = json.cdramomc;
                                    cdtipsitTramite = json.cdtipsitmc;
                                    panelModificacion.query('combo[name=cmbBeneficiario]')[0].setValue(json.otvalor04mc);
                                    if(json.otvalor18mc !=null) {
                                        panelModificacion.query('combo[name=destinoPago]')[0].setValue(json.otvalor18mc);
                                    }
                                    if(json.otvalor19mc !=null) {
                                        panelModificacion.query('combo[name=concepPago]')[0].setValue(json.otvalor19mc);
                                    }
                                    if(json.otvalor27mc !=null) {
                                        panelModificacion.query('[name=idCveBeneficiario]')[0].setValue(json.otvalor27mc);
                                    }
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
                                            cmbBeneficiario,
                                            idCveBeneficiario],
                                    buttonAlign:'center',
                                    buttons: [ {
                                        text: 'Solicitar'
                                        ,icon:_CONTEXT+'/resources/fam3icons/icons/accept.png'
                                        ,buttonAlign : 'center'
                                        ,handler: function() { 
                                            if (panelModificacion.form.isValid()) {
                                                var datos=panelModificacion.form.getValues();
                                                myMask.show();
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
                                                            cvebeneficiario : datos.idCveBeneficiario,
                                                            tipoPago : _11_params.OTVALOR02
                                                        }
                                                    }
                                                    ,success : function (response) {
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
                                                                    myMask.hide();
                                                                    centrarVentanaInterna(mensajeCorrecto('&Eacute;XITO','El pago se ha solicitado con &eacute;xito.',function(){
                                                                        _11_regresarMC();
                                                                    }));
                                                                }else {
                                                                    myMask.hide();
                                                                    centrarVentanaInterna(mensajeError(respuesta.mensaje));
                                                                }
                                                            },
                                                            failure: function(){
                                                                myMask.hide();
                                                                centrarVentanaInterna(mensajeError('No se pudo solicitar el pago.'));
                                                            }
                                                        });
                                                    },
                                                    failure : function () {
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
            }
    });
    centrarVentana(msgWindow);
}

//6.- Turnar del Medico ajustdor al operador
function _11_retornarMedAjustadorAOperador(grid,rowIndex,colIndex) {
    Ext.Ajax.request({
        url     : _URL_P_MOV_MAUTSINI
        ,params : {
            'params.ntramite': _11_params.NTRAMITE
        }
        ,success : function (response) {
            var respuestaMensaje = Ext.decode(response.responseText).mensaje;
            Ext.Ajax.request({
                url   : _URL_VALIDADOCCARGADOS,
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
                            height      : 430,
                            autoScroll  : true,
                            items       : [
                                Ext.create('Ext.form.Panel', {
                                    title: 'Turnar a Coordinador de Reclamaciones',
                                    width: 650,
                                    url: _URL_ACTSTATUS_TRAMITE,
                                    bodyPadding: 5,
                                    items: [comentariosText,
                                    {
                                        xtype       : 'radiogroup'
                                        ,fieldLabel : 'Mostrar al agente'
                                        ,columns    : 2
                                        ,width      : 250
                                        ,style      : 'margin:5px;'
                                        ,hidden     : _GLOBAL_CDSISROL===RolSistema.Agente
                                        ,items      : [
                                            {
                                                boxLabel    : 'Si'
                                                ,itemId     : 'SWAGENTE5'
                                                ,name       : 'SWAGENTE5'
                                                ,inputValue : 'S'
                                                ,checked    : _GLOBAL_CDSISROL===RolSistema.Agente
                                            }
                                            ,{
                                                boxLabel    : 'No'
                                                ,name       : 'SWAGENTE5'
                                                ,inputValue : 'N'
                                                ,checked    : _GLOBAL_CDSISROL!==RolSistema.Agente
                                            }
                                        ]
                                    }],
                                    buttonAlign:'center',
                                    buttons: [{
                                        text: 'Turnar'
                                        ,icon:_CONTEXT+'/resources/fam3icons/icons/accept.png'
                                        ,buttonAlign : 'center'
                                        ,handler: function() {
                                            var formPanel = this.up().up();
                                            if (formPanel.form.isValid()) {
                                                formPanel.form.submit({
                                                    waitMsg:'Procesando...',
                                                    params: {
                                                        'smap1.ntramite' : _11_params.NTRAMITE, 
                                                        'smap1.status'   : _STATUS_TRAMITE_EN_ESPERA_DE_ASIGNACION
                                                        ,'smap1.swagente' : _fieldById('SWAGENTE5').getGroupValue()
                                                    },
                                                    failure: function(form, action) {
                                                        mensajeError('Error al turnar al operador de reclamaciones');
                                                    },
                                                    success: function(form, action) {
                                                        Ext.Ajax.request( {
                                                            url     : _URL_NOMBRE_TURNADO
                                                            ,params : {
                                                                'params.ntramite': _11_params.NTRAMITE,
                                                                'params.rolDestino': 'operadorsini'
                                                            }
                                                            ,success : function (response) {
                                                                var usuarioTurnadoSiniestro = Ext.decode(response.responseText).usuarioTurnadoSiniestro;
                                                                debug("VALOR DE RESPUESTA -->",usuarioTurnadoSiniestro);
                                                                mensajeCorrecto('&Eacute;XITO','Se ha turnado correctamente a: '+usuarioTurnadoSiniestro,function(){
                                                                    windowLoader.close();
                                                                    Ext.Ajax.request({
                                                                        url     : _URL_ACTUALIZA_TURNADOMC
                                                                        ,params : {           
                                                                            'params.ntramite': _11_params.NTRAMITE
                                                                        }
                                                                        ,success : function (response) {
                                                                            _11_regresarMC();
                                                                        },
                                                                        failure : function () {
                                                                            me.up().up().setLoading(false);
                                                                            centrarVentanaInterna(Ext.Msg.show({
                                                                                title:'Error',
                                                                                msg: 'Error de comunicaci&oacute;n',
                                                                                buttons: Ext.Msg.OK,
                                                                                icon: Ext.Msg.ERROR
                                                                            }));
                                                                        }
                                                                    });
                                                                });
                                                            },
                                                            failure : function () {
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
                                    }]
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

//7.- Historial dem tramite
function _11_historialTramite() {
    var window=Ext.create('Ext.window.Window', {
        title        : 'Detalles del tr&aacute;mite '+_11_params.NTRAMITE
        ,modal       : true
        ,buttonAlign : 'center'
        ,width       : 700
        ,height      : 400
        ,items       : [
            Ext.create('Ext.grid.Panel',{
                height      : 190
                ,autoScroll : true
                ,store      : new Ext.data.Store( {
                    model     : 'DetalleMC'
                    ,autoLoad : true
                    ,proxy    : {
                        type         : 'ajax'
                        ,url         : _URL_DETALLEMC
                        ,extraParams : {
                            'smap1.pv_ntramite_i' : _11_params.NTRAMITE
                        }
                        ,reader      : {
                            type  : 'json'
                            ,root : 'slist1'
                        }
                    }
                })
                ,columns :[
                    {
                        header     : 'Tr&aacute;mite'
                        ,dataIndex : 'NTRAMITE'
                        ,width     : 60
                    }
                    ,{
                        header     : 'No.'
                        ,dataIndex : 'NMORDINA'
                        ,width     : 40
                    }
                    ,{
                        header     : 'Fecha de inicio'
                        ,xtype     : 'datecolumn'
                        ,dataIndex : 'FECHAINI'
                        ,format    : 'd M Y H:i'
                        ,width     : 130
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
                        ,format    : 'd M Y H:i'
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
                        ,renderer     : function(value) {
                            debug(value);
                            if(value&&value!=null){
                                value='';
                            }else{
                                value='<img src="${ctx}/resources/fam3icons/icons/accept.png" style="cursor:pointer;" data-qtip="Finalizar" />';
                            }
                            return value;
                        }
                    }
                ]
                ,listeners : {
                    cellclick : function(grid, td,cellIndex, record, tr, rowIndex, e, eOpts) {
                        if(cellIndex<6) {
                            Ext.getCmp('inputReadDetalleHtmlVisor').setValue(record.get('COMMENTS'));
                        }
                    }
                }
            })
            ,Ext.create('Ext.form.HtmlEditor', {
                id        : 'inputReadDetalleHtmlVisor'
                ,width    : 690
                ,height   : 200
                ,readOnly : true
            })
        ]
    });
    centrarVentanaInterna(window.show());
    Ext.getCmp('inputReadDetalleHtmlVisor').getToolbar().hide();
}

//8.- Devolver tramite
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
        height      : 430,
        autoScroll  : true,
        items       : [
            Ext.create('Ext.form.Panel', {
                title: 'Devolver Tr&aacute;mite',
                width: 650,
                url: _URL_ACTSTATUS_TRAMITE,
                bodyPadding: 5,
                items: [comentariosText,{
                    xtype       : 'radiogroup'
                    ,fieldLabel : 'Mostrar al agente'
                    ,columns    : 2
                    ,width      : 250
                    ,style      : 'margin:5px;'
                    ,hidden     : _GLOBAL_CDSISROL===RolSistema.Agente
                    ,items      : [
                        {
                            boxLabel    : 'Si'
                            ,itemId     : 'SWAGENTE4'
                            ,name       : 'SWAGENTE4'
                            ,inputValue : 'S'
                            ,checked    : _GLOBAL_CDSISROL===RolSistema.Agente
                        }
                        ,{
                            boxLabel    : 'No'
                            ,name       : 'SWAGENTE4'
                            ,inputValue : 'N'
                            ,checked    : _GLOBAL_CDSISROL!==RolSistema.Agente
                        }
                    ]
                }],
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
                                    ,'smap1.swagente' : _fieldById('SWAGENTE4').getGroupValue()
                                },
                                failure: function(){
                                    mensajeError('Error al devolver el tr&aacute;mite');
                                },
                                success: function(form, action) {
                                    centrarVentanaInterna(mensajeCorrecto('&Eacute;XITO','Se ha turnado correctamente.',function(){
                                        windowLoader.close();
                                        _11_regresarMC();
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
                }]
            })
        ]
    }).show();
    centrarVentana(windowLoader);
}

//9.- Turnar Operador Reclamacion
function _11_turnarAreclamaciones(grid,rowIndex,colIndex){
    //var record = grid.getStore().getAt(rowIndex);
    Ext.Ajax.request({
        url  : _URL_VALIDA_FACTMONTO
        ,params:{
            'params.ntramite'  : _11_params.NTRAMITE
        }
        ,success : function (response) {
            banderaAranceles ="0";
            var resultAranceles = 'Los siguientes C.R. no se procesaran : <br/>';
            var arancelesTra = Ext.decode(response.responseText).loadList;
            for(i = 0; i < arancelesTra.length; i++){
                banderaAranceles = "1";
                resultAranceles = resultAranceles + '   - El C.R.' + arancelesTra[i].NTRAMITE+ ' el n&uacute;mero de factura es:  ' + arancelesTra[i].NFACTURA + ' el importe de la factura es : '+ arancelesTra[i].PTIMPORT+'<br/>';
            }
            
            if(banderaAranceles == "1"){
                centrarVentanaInterna(Ext.Msg.show({
                    title:'Aviso del sistema',
                    msg: resultAranceles,
                    buttons: Ext.Msg.OK,
                    icon: Ext.Msg.WARNING
                }));
            }else{
                //Si es correcto entonces procedemos con el turnado al operador de reclamacioness
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
                    height      : 430,
                    autoScroll  : true,
                    items       : [
                                    Ext.create('Ext.form.Panel', {
                                    title: 'Turnar a Coordinador de Reclamaciones',
                                    width: 650,
                                    url: _URL_ActualizaStatusTramite,
                                    bodyPadding: 5,
                                    items: [comentariosText,{
                                        xtype       : 'radiogroup'
                                        ,fieldLabel : 'Mostrar al agente'
                                        ,columns    : 2
                                        ,width      : 250
                                        ,style      : 'margin:5px;'
                                        ,hidden     : _GLOBAL_CDSISROL===RolSistema.Agente
                                        ,items      :
                                        [
                                            {
                                                boxLabel    : 'Si'
                                                ,itemId     : 'SWAGENTE2'
                                                ,name       : 'SWAGENTE2'
                                                ,inputValue : 'S'
                                                ,checked    : _GLOBAL_CDSISROL===RolSistema.Agente
                                            }
                                            ,{
                                                boxLabel    : 'No'
                                                ,name       : 'SWAGENTE2'
                                                ,inputValue : 'N'
                                                ,checked    : _GLOBAL_CDSISROL!==RolSistema.Agente
                                            }
                                        ]
                                    }],
                                    buttonAlign:'center',
                                    buttons: [{
                                        text: 'Turnar'
                                        ,icon:_CONTEXT+'/resources/fam3icons/icons/accept.png'
                                        ,buttonAlign : 'center',
                                        handler: function() {
                                            var formPanel = this.up().up();
                                            if (formPanel.form.isValid()) {
                                                
                                                if(_tipoPago ==_TIPO_PAGO_REEMBOLSO || _tipoPago ==_TIPO_PAGO_INDEMNIZACION){
                                                    Ext.Ajax.request({
                                                        url: _UrlGeneraSiniestroTramite,
                                                        params: {
                                                            'params.pv_ntramite_i' : _11_params.NTRAMITE
                                                        },
                                                        success: function(response, opt) {
                                                            var jsonRes=Ext.decode(response.responseText);
                                                            if(jsonRes.success == true){
                                                                formPanel.form.submit({
                                                                    waitMsg:'Procesando...',
                                                                    params: {
                                                                        'smap1.ntramite' : _11_params.NTRAMITE, 
                                                                        'smap1.status'   : _STATUS_TRAMITE_EN_ESPERA_DE_ASIGNACION
                                                                        ,'smap1.swagente' : _fieldById('SWAGENTE2').getGroupValue()
                                                                    },
                                                                    failure: function(form, action) {
                                                                        debug(action);
                                                                        switch (action.failureType) {
                                                                            case Ext.form.action.Action.CONNECT_FAILURE:
                                                                                errorComunicacion();
                                                                                break;
                                                                            case Ext.form.action.Action.SERVER_INVALID:
                                                                                mensajeError(action.result.mensaje);
                                                                                break;
                                                                        }
                                                                    },
                                                                    success: function(form, action) {
                                                                        Ext.Ajax.request( {
                                                                            url: _URL_TurnarAOperadorReclamacion,
                                                                            params: {
                                                                                    'smap1.ntramite' : _11_params.NTRAMITE, 
                                                                                    'smap1.status'   : _STATUS_TRAMITE_EN_CAPTURA
                                                                                    ,'smap1.rol_destino'     : 'operadorsini'
                                                                                    ,'smap1.usuario_destino' : ''
                                                                            },
                                                                            success:function(response,opts){
                                                                                Ext.Ajax.request( {
                                                                                    url     : _URL_NOMBRE_TURNADO
                                                                                    ,params : {
                                                                                        'params.ntramite': _11_params.NTRAMITE,
                                                                                        'params.rolDestino': 'operadorsini'
                                                                                    }
                                                                                    ,success : function (response) {
                                                                                        var usuarioTurnadoSiniestro = Ext.decode(response.responseText).usuarioTurnadoSiniestro;
                                                                                        mensajeCorrecto('&Eacute;XITO','Se ha turnado con &eacute;xito a: '+usuarioTurnadoSiniestro,function(){
                                                                                            windowLoader.close();
                                                                                            //REALIZAMOS LA ACTUALIZACION DE LOS DEMAS
                                                                                            _11_regresarMC();
                                                                                        });
                                                                                    },
                                                                                    failure : function () {
                                                                                        me.up().up().setLoading(false);
                                                                                        centrarVentanaInterna(Ext.Msg.show({
                                                                                            title:'Error',
                                                                                            msg: 'Error de comunicaci&oacute;n',
                                                                                            buttons: Ext.Msg.OK,
                                                                                            icon: Ext.Msg.ERROR
                                                                                        }));
                                                                                    }
                                                                                });
                                                                            },
                                                                            failure:function(response,opts) {
                                                                                Ext.Msg.show({
                                                                                    title:'Error',
                                                                                    msg: 'Error de comunicaci&oacute;n',
                                                                                    buttons: Ext.Msg.OK,
                                                                                    icon: Ext.Msg.ERROR
                                                                                });
                                                                            }
                                                                        });
                                                                    }
                                                                });
                                                            }else{
                                                                mensajeError('Error al generar Siniestro para Area de Reclamaciones');
                                                            }
                                                        },
                                                        failure: function(){
                                                            mensajeError('Error al generar Siniestro para Area de Reclamaciones');
                                                        }
                                                    });
                                                }else{
                                                    formPanel.form.submit({
                                                        waitMsg:'Procesando...',
                                                        params: {
                                                            'smap1.ntramite' : _11_params.NTRAMITE, 
                                                            'smap1.status'   : _STATUS_TRAMITE_EN_ESPERA_DE_ASIGNACION
                                                        },
                                                        failure: function(form, action) {
                                                            debug(action);
                                                            switch (action.failureType) {
                                                                case Ext.form.action.Action.CONNECT_FAILURE:
                                                                    errorComunicacion();
                                                                    break;
                                                                case Ext.form.action.Action.SERVER_INVALID:
                                                                    mensajeError(action.result.mensaje);
                                                                    break;
                                                            }
                                                        },
                                                        success: function(form, action) {
                                                            Ext.Ajax.request( {
                                                                url: _URL_TurnarAOperadorReclamacion,
                                                                params: {
                                                                        'smap1.ntramite' : _11_params.NTRAMITE, 
                                                                        'smap1.status'   : _STATUS_TRAMITE_EN_CAPTURA
                                                                        ,'smap1.rol_destino'     : 'operadorsini'
                                                                        ,'smap1.usuario_destino' : ''
                                                                },
                                                                success:function(response,opts){
                                                                    Ext.Ajax.request( {
                                                                        url     :  _URL_NOMBRE_TURNADO
                                                                        ,params :  {
                                                                            'params.ntramite': _11_params.NTRAMITE,
                                                                            'params.rolDestino': 'operadorsini'
                                                                        }
                                                                        ,success : function (response) {
                                                                            var usuarioTurnadoSiniestro = Ext.decode(response.responseText).usuarioTurnadoSiniestro;
                                                                            mensajeCorrecto('&Eacute;XITO','Se ha turnado con &eacute;xito a: '+usuarioTurnadoSiniestro,function(){
                                                                                windowLoader.close();
                                                                                //Actualizamos los valores del tramite
                                                                                Ext.Ajax.request( {
                                                                                    url     :  _URL_ACTUALIZA_TURNADOMC
                                                                                    ,params :  {
                                                                                        'params.ntramite': _11_params.NTRAMITE
                                                                                    }
                                                                                    ,success : function (response) {
                                                                                        _11_regresarMC();
                                                                                    },
                                                                                    failure : function () {
                                                                                        me.up().up().setLoading(false);
                                                                                        centrarVentanaInterna(Ext.Msg.show({
                                                                                            title:'Error',
                                                                                            msg: 'Error de comunicaci&oacute;n',
                                                                                            buttons: Ext.Msg.OK,
                                                                                            icon: Ext.Msg.ERROR
                                                                                        }));
                                                                                    }
                                                                                });
                                                                            });
                                                                        },
                                                                        failure : function () {
                                                                            me.up().up().setLoading(false);
                                                                            centrarVentanaInterna(Ext.Msg.show({
                                                                                title:'Error',
                                                                                msg: 'Error de comunicaci&oacute;n',
                                                                                buttons: Ext.Msg.OK,
                                                                                icon: Ext.Msg.ERROR
                                                                            }));
                                                                        }
                                                                    });
                                                                },
                                                                failure:function(response,opts) {
                                                                    Ext.Msg.show({
                                                                        title:'Error',
                                                                        msg: 'Error de comunicaci&oacute;n',
                                                                        buttons: Ext.Msg.OK,
                                                                        icon: Ext.Msg.ERROR
                                                                    });
                                                                }
                                                            });
                                                        }
                                                    });
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
        },failure : function () {
            form.setLoading(false);
            Ext.Msg.show({
                title:'Error',
                msg: 'Error de comunicaci&oacute;n',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.ERROR
            });
        }
    });
}

//11.-Agregar Facturas
function _p11_agregarFacturas(){
	debug("Entra a _p11_agregarFacturas ============>>>>>>");
    windowLoader = Ext.create('Ext.window.Window',{
        title         : 'Alta de Facturas',         buttonAlign  : 'center',        width        : 800
        ,height       : 430
        ,autoScroll   : true
        ,loader       : {
            url       : _VER_ALTA_FACTURAS
            ,scripts  : true
            ,autoLoad : true
            ,params   : {
                'params.ntramite'       : _11_params.NTRAMITE,
                'params.cdTipoPago'     : _11_params.OTVALOR02,
                'params.cdTipoAtencion' : _11_params.OTVALOR07,
                'params.cdpresta'       : _11_params.OTVALOR11,
                'params.cdramo'         : _11_params.CDRAMO,
                'params.feOcurrencia'   : _11_params.OTVALOR10,
                'params.cdtipsit'       : _11_params.OTVALOR26
            }
        },
        listeners: {
             close:function() {
                 if(true){
                    Ext.create('Ext.form.Panel').submit( {
                        standardSubmit :true
                        ,params     : {
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

//13.- Agregar asegurados
function _p21_agregarAsegurado(){
    centrarVentanaInterna(ventanaAgregarAsegurado.show());
}

//14.- Generar Calculos
function _p21_generarCalculo(){
    gridFacturaDirecto.setLoading(true);
    var myMask = new Ext.LoadMask(Ext.getBody(),{msg:"loading..."});
    myMask.show();
    Ext.Ajax.request( {
        url  : _URL_GENERAR_CALCULO
        ,params:{
            'params.ntramite'  : panelInicialPral.down('[name=params.ntramite]').getValue()
        }
        ,success : function (response) {
            Ext.Ajax.request({
                url     : _URL_VALIDA_IMPASEGURADOSINIESTRO
                ,params:{
                    'params.tipopago'  : _tipoPago,
                    'params.ntramite'  : panelInicialPral.down('[name=params.ntramite]').getValue(),
                    'params.nfactura'  : panelInicialPral.down('[name=params.nfactura]').getValue()
                }
                ,success : function (response) {
                    json = Ext.decode(response.responseText);
                    if(json.success==false){
                        myMask.hide();
                        centrarVentanaInterna(mensajeWarning(json.msgResult));
                    }else{
                    	 myMask.hide();
                    }
                    gridFacturaDirecto.setLoading(false);
                    cargarPaginacion(panelInicialPral.down('[name=params.ntramite]').getValue(), panelInicialPral.down('[name=params.nfactura]').getValue());
                    
                    panelComplementos.down('[name=params.sumaAsegurada]').setValue("0.00");
                    panelComplementos.down('[name=params.sumaGastada]').setValue("0.00");
                    obtenerTotalPagos(panelInicialPral.down('[name=params.ntramite]').getValue() , panelInicialPral.down('[name=params.nfactura]').getValue());
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
        },
        failure : function () {
            gridFacturaDirecto.setLoading(false);
            Ext.Msg.show({
                title:'Error',
                msg: 'Error de comunicaci&oacute;n',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.ERROR
            });
        }
    });     
}


//15.- Eliminar asegurado seleccionado
function eliminarAsegurado(grid,rowIndex){
    var myMask = new Ext.LoadMask(Ext.getBody(), {msg:"loading..."});
    debug("Entra a eliminarAsegurado ============>>>>>>");
    myMask.show();
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
                            'params.nmsinies'   : record.get('NMSINIES'),
                            'params.accion'   : "0"
                        },
                        success: function(response) {
                            var res = Ext.decode(response.responseText);
                            if(res.success){
                                mensajeCorrecto('Aviso','Se ha eliminado con &eacute;xito.',function(){
                                    banderaAsegurado = 0;
                                    cargarPaginacion(panelInicialPral.down('[name=params.ntramite]').getValue(), panelInicialPral.down('[name=params.nfactura]').getValue());
                                    
                                    if(_cdtipoProceso =="1"){
                                        panelComplementos.down('[name=params.sumaAsegurada]').setValue("0.00");
                                        panelComplementos.down('[name=params.sumaGastada]').setValue("0.00");
                                        obtenerTotalPagos(panelInicialPral.down('[name=params.ntramite]').getValue() , panelInicialPral.down('[name=params.nfactura]').getValue());
                                        myMask.hide();
                                    }else{
                                        myMask.hide();
                                    }   
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

//16.- Agregar Conceptos del Asegurado
function _p21_agregarConcepto() {
    if(gridFacturaDirecto.getSelectionModel().hasSelection()){
        var recordFactura = gridFacturaDirecto.getSelectionModel().getSelection()[0];
        debug("recordFactura ===> ",recordFactura);
        
        if(_11_params.CDRAMO != _RECUPERA){
        	//(EGS) restringimos la validacion solo para pago directo _tipoPago == _TIPO_PAGO_DIRECTO &&
        	if(_tipoPago == _TIPO_PAGO_DIRECTO && recordFactura.get('FLAGREQAUT') == "SI" &&  (  recordFactura.get('NMAUTSER') =="N/A" || +recordFactura.get('NMAUTSER') <= '0' || recordFactura.get('NMAUTSER')== "")){
                debug("Entra a la configuración");
                 _11_obtieneDatosOpcionalesValor(recordFactura.get('CDRAMO'),recordFactura.get('CDTIPSIT'),recordFactura.get('CDGARANT'),recordFactura.get('CDCONVAL'),recordFactura,"0"); 
            }else{
                var idReclamacion = recordFactura.get('NMSINIES');
                valido = idReclamacion && idReclamacion>0;
                if(valido){
                    var idCobertura         = recordFactura.get('CDGARANT');
                    var idSubcobertura      = recordFactura.get('CDCONVAL');
                    var idcausaSiniestro    = recordFactura.get('CDCAUSA');
                    var idICDP              = recordFactura.get('CDICD');
                    
                    if(recordFactura.get('CDGARANT').length > 0 && recordFactura.get('CDCONVAL').length > 0 && 
                       recordFactura.get('CDCAUSA').length > 0 && recordFactura.get('CDICD').length > 0){
                            banderaConcepto = "1";
                            storeConceptos.add(new modelConceptos( {
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
                        mensajeWarning('Complemente la informaci&oacute;n del Asegurado');
                    }
                }else{
                    mensajeWarning('Debes generar una autorizaci&oacute;n para el asegurado');
                }
            }
        }else{
            storeRecupera.add(new modelRecupera( {
                'NTRAMITE'  : panelInicialPral.down('[name=params.ntramite]').getValue() ,
                'NFACTURA'  : panelInicialPral.down('[name=params.nfactura]').getValue()
            }));
        }
    }else{
        centrarVentanaInterna(mensajeWarning("Debe seleccionar un asegurado para agregar sus conceptos"));
    }
}

//17.- Guardar Conceptos del asegurado
function _guardarConceptosxFactura(){
	debug("_guardarConceptosxFactura =======================>>>>");
    var myMask = new Ext.LoadMask(Ext.getBody(), {msg:"loading..."});
    myMask.show();
    var obtener = [];
    if(_11_params.CDRAMO != _RECUPERA){
        storeConceptos.each(function(record) {
            obtener.push(record.data);
        });
        if(obtener.length <= 0){
             myMask.hide();
            centrarVentanaInterna(Ext.Msg.show({
                title:'Error',
                msg: 'Se requiere al menos un concepto',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.ERROR
            }));//.defer(100));
            storeConceptos.reload();
            myMask.hide();
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
                    }));//.defer(100));
                     myMask.hide();
                    return false;
                }
            }
            var submitValues={};
            var formulario=panelInicialPral.form.getValues();
            submitValues['params']=formulario;
            var datosTablas = [];
            var _11_aseguradoSeleccionado = gridFacturaDirecto.getView().getSelectionModel().getSelection()[0];
            
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
                    ,ptIVA     : record.get('PTIVA')
                });
            });
            submitValues['datosTablas']=datosTablas;
            panelInicialPral.setLoading(true);
            Ext.Ajax.request( {
                url: _URL_GUARDA_CONCEPTO_TRAMITE,
                jsonData:Ext.encode(submitValues),
                success:function(response,opts){
                    panelInicialPral.setLoading(false);
                    var jsonResp = Ext.decode(response.responseText);
                    if(jsonResp.success==true){
                        panelInicialPral.setLoading(false);
                        banderaConcepto = "0";
                        storeConceptos.reload();
                        
                        if(_cdtipoProceso =="1"){
                            cargarPaginacion(panelInicialPral.down('[name=params.ntramite]').getValue(), panelInicialPral.down('[name=params.nfactura]').getValue());
                            panelComplementos.down('[name=params.sumaAsegurada]').setValue("0.00");
                            panelComplementos.down('[name=params.sumaGastada]').setValue("0.00");
                            obtenerTotalPagos(panelInicialPral.down('[name=params.ntramite]').getValue() , panelInicialPral.down('[name=params.nfactura]').getValue());
                            myMask.hide();
                        }else{
                            myMask.hide();
                        }
                    }
                },
                failure:function(response,opts) {
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
    }else{
        storeRecupera.each(function(record) {
            obtener.push(record.data);
        });
        if(obtener.length <= 0){
            centrarVentanaInterna(Ext.Msg.show({
                title:'Error',
                msg: 'Se requiere al menos una cobertura',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.ERROR
            }).defer(100));
            storeRecupera.reload();
            return false;
        }else{
            for(i=0;i < obtener.length;i++){
                if(obtener[i].CANTPORC == null ||obtener[i].CANTPORC == "" ){
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
            
            storeRecupera.each(function(record,index){
                datosTablas.push({
                    cantporc     : record.get('CANTPORC')
                    ,cdconval    : record.get('CDCONVAL')
                    ,cdgarant    : record.get('CDGARANT')
                    ,esquemaaseg : record.get('ESQUEMAASEG')
                    ,nfactura    : record.get('NFACTURA')
                    ,ntramite    : record.get('NTRAMITE')
                    ,ptimport    : record.get('PTIMPORT')
                    ,sumaaseg    : record.get('SUMAASEG')
                    ,operacion   : "I"
                });
            });
            submitValues['datosTablas']=datosTablas;
            panelInicialPral.setLoading(true);
            Ext.Ajax.request( {
                url: _URL_GUARDA_CONCEPTO_RECUPERA,
                jsonData:Ext.encode(submitValues),
                success:function(response,opts){
                    panelInicialPral.setLoading(false);
                    var jsonResp = Ext.decode(response.responseText);
                    if(jsonResp.success==true){
                        panelInicialPral.setLoading(false);
                        //banderaConcepto = "0";
                        storeRecupera.reload();
                    }
                },
                failure:function(response,opts) {
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
}

//18.- Ventana para la visualizacion de los ajestes medicos
function _mostrarVentanaAjustes(grid,rowIndex,colIndex){
    var record = grid.getStore().getAt(rowIndex);
    var recordFactura = gridFacturaDirecto.getSelectionModel().getSelection()[0];
        windowLoader = Ext.create('Ext.window.Window',{
            modal       : true,
            buttonAlign : 'center',
            title       : 'Ajustes M&eacute;dico',
            width       : 800,
            height      : 450,
            //autoScroll  : true,
            loader      : {
                url     : _URL_AJUSTESMEDICOS,
                params  : {
                    'params.ntramite'       : panelInicialPral.down('[name=params.ntramite]').getValue()
                    ,'params.cdunieco'      : recordFactura.get('CDUNIECO')
                    ,'params.cdramo'        : recordFactura.get('CDRAMO')
                    ,'params.estado'        : recordFactura.get('ESTADO')
                    ,'params.nmpoliza'      : recordFactura.get('NMPOLIZA')
                    ,'params.nmsuplem'      : recordFactura.get('NMSUPLEM')
                    ,'params.nmsituac'      : recordFactura.get('NMSITUAC')
                    ,'params.aaapertu'      : recordFactura.get('AAAPERTU')
                    ,'params.status'        : recordFactura.get('STATUS')
                    ,'params.nmsinies'      : recordFactura.get('NMSINIES')
                    ,'params.nfactura'      : panelInicialPral.down('[name=params.nfactura]').getValue()
                    ,'params.cdgarant'      : record.get('CDGARANT')
                    ,'params.cdconval'      : record.get('CDCONVAL')
                    ,'params.cdconcep'      : record.get('CDCONCEP')
                    ,'params.idconcep'      : record.get('IDCONCEP')
                    ,'params.nmordina'      : record.get('NMORDINA')
                    ,'params.precio'        : record.get('PTPRECIO')
                    ,'params.cantidad'      : record.get('CANTIDAD')
                    ,'params.descuentoporc' : record.get('DESTOPOR')
                    ,'params.descuentonum'  : record.get('DESTOIMP')
                    ,'params.importe'       : record.get('PTIMPORT')
                    ,'params.ajusteMedi'    : record.get('TOTAJUSMED')
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

//19.-Revision de Documentos
function revisarDocumento(grid,rowIndex) {
    var record = grid.getStore().getAt(rowIndex);
    var valido = true;
    Ext.Ajax.request( {
        url : _11_URL_REQUIEREAUTSERV
        ,params:{
            'params.cobertura'   : null,
            'params.subcobertura': null,
            'params.cdramo'      : record.raw.CDRAMO,
            'params.cdtipsit'    : record.raw.CDTIPSIT
        }
        ,success : function (response) {
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
                            var json = {
                                'params.ntramite'       : panelInicialPral.down('[name=params.ntramite]').getValue(),
                                'params.cdunieco'       : record.raw.CDUNIECO,
                                'params.cdramo'         : record.raw.CDRAMO,
                                'params.estado'         : record.raw.ESTADO,
                                'params.nmpoliza'       : record.raw.NMPOLIZA,
                                'params.nmsuplem'       : record.raw.NMSUPLEM,
                                'params.nmsituac'       : record.raw.NMSITUAC,
                                'params.cdtipsit'       : record.raw.CDTIPSIT,
                                'params.dateOcurrencia' : record.raw.FEOCURRE,
                                'params.nfactura'       : panelInicialPral.down('[name=params.nfactura]').getValue(),
                                'params.secAsegurado'   : record.raw.SECTWORKSIN
                            };
                            Ext.Ajax.request( {
                                url   : _11_URL_INICIARSINIESTROSINAUTSERV
                                ,params  : json
                                ,success : function(response) {
                                    json = Ext.decode(response.responseText);
                                    if(json.success==true){
                                        _11_guardarInformacionAdicional();
                                        mensajeCorrecto('Autorizaci&oacute;n',json.mensaje,function(){
                                            storeAseguradoFactura.removeAll();
                                            cargarPaginacion(panelInicialPral.down('[name=params.ntramite]').getValue(), panelInicialPral.down('[name=params.nfactura]').getValue());
                                        });
                                    }else{
                                        mensajeError(json.mensaje);
                                    }
                                }
                                ,failure : function() {
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
            
            if(valido) {
                valido = record.get('VoBoAuto')!='n'&&record.get('VoBoAuto')!='N';
                if(!valido) {
                    mensajeError('El siniestro no se puede continuar porque no tiene el visto bueno autom&aacute;tico');
                }
            }
            
            if(valido) {
            windowLoader = Ext.create('Ext.window.Window',{
                    modal      : true,
                    buttonAlign : 'center',
                    title: 'Informaci&oacute;n general',
                    width      : 800,
                    height    : 450,
                    autoScroll  : true,
                    loader    : {
                        url  : _URL_TABBEDPANEL
                        ,params      :
                        {
                            'params.ntramite'   : panelInicialPral.down('[name=params.ntramite]').getValue()
                            ,'params.cdunieco'  : record.raw.CDUNIECO
                            ,'params.cdramo'    : record.raw.CDRAMO
                            ,'params.estado'    : record.raw.ESTADO
                            ,'params.nmpoliza'  : record.raw.NMPOLIZA
                            ,'params.nmsuplem'  : record.raw.NMSUPLEM
                            ,'params.nmsituac'  : record.raw.NMSITUAC
                            ,'params.aaapertu'  : record.raw.AAAPERTU
                            ,'params.status'    : record.raw.STATUS
                            ,'params.nmsinies'  : record.raw.NMSINIES
                            ,'params.cdtipsit'  : record.raw.CDTIPSIT
                            ,'params.cdrol'     : _CDROL
                            ,'params.tipopago'  : _tipoPago
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
    
//19.1.- Guarda la informacion Adicional - Siniestro sin autorizacion de servicio
//21.1.- Guardar siniestro con autorizacion de servicio
function _11_guardarInformacionAdicional(){
    panelInicialPral.form.submit({
        waitMsg:'Procesando...',    
        url: _URL_GUARDA_CAMBIOS_FACTURA,
        failure: function(form, action) {
            centrarVentanaInterna(mensajeError("Verifica los datos requeridos"));
        },
        success: function(form, action) {
            storeAseguradoFactura.removeAll();
            cargarPaginacion(panelInicialPral.down('[name=params.ntramite]').getValue(), panelInicialPral.down('[name=params.nfactura]').getValue());
        }
    });
}    

//20.-Muestra el listado de las autorizaciones disponibles para el asegurado
function _11_pedirAutorizacion(record) {
	debug("Valor de _11_pedirAutorizacion => record====> ", record);
    _11_recordActivo = record;
    _11_textfieldAsegurado.setValue(_11_recordActivo.get('NOMBRE'));
    var params = {
            'params.cdgarant'   :   _11_recordActivo.get('CDGARANT'),
            'params.cdconval'   :   _11_recordActivo.get('CDCONVAL'),
            'params.cdperson'   :   _11_recordActivo.get('CDPERSON')
    };
    cargaStorePaginadoLocal(storeListadoAutorizacion, _URL_LISTA_AUTSERVICIO, 'datosInformacionAdicional', params, function(options, success, response){
        if(success){
            var jsonResponse = Ext.decode(response.responseText);
            if(jsonResponse.datosInformacionAdicional.length <= 0) {
                storeConceptos.removeAll();
                banderaConcepto = 0;
                banderaAsegurado = 0;
                storeAseguradoFactura.removeAll();
                cargarPaginacion(panelInicialPral.down('[name=params.ntramite]').getValue(), panelInicialPral.down('[name=params.nfactura]').getValue());
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
    
//21.- Asociar autorizacion de Servicio (la primera vez que entra )
function _11_asociarAutorizacion() {
    var valido = _11_formPedirAuto.isValid();
    if(!valido) {
        datosIncompletos();
    }

    if(valido) {
        var json = {
            'params.nmautser'       : _11_textfieldNmautserv.getValue()
            ,'params.nmpoliza'      : _11_recordActivo.get('NMPOLIZA')
            ,'params.cdperson'      : _11_recordActivo.get('CDPERSON')
            ,'params.ntramite'      : panelInicialPral.down('[name=params.ntramite]').getValue()
            ,'params.nfactura'      : panelInicialPral.down('[name=params.nfactura]').getValue()
            ,'params.feocurrencia'  : _11_recordActivo.get('FEOCURRE')
            ,'params.cdunieco'      : _11_recordActivo.get('CDUNIECO')
            ,'params.cdramo'        : _11_recordActivo.get('CDRAMO')
            ,'params.estado'        : _11_recordActivo.get('ESTADO')
            ,'params.cdpresta'      : panelInicialPral.down('combo[name=params.cdpresta]').getValue()
            ,'params.secAsegurado'  : _11_recordActivo.get('SECTWORKSIN')
        };
        
        gridFacturaDirecto.setLoading(true);
        _11_windowPedirAut.close();
        Ext.Ajax.request( {
            url   : _11_URL_INICIARSINIESTROTWORKSIN
            ,params  : json
            ,success : function(response) {
                json = Ext.decode(response.responseText);
                debug("Valor de respuesta del guardado ===>>",json);
                if(json.success==true) {
                    _11_guardarInformacionAdicional();
                    gridFacturaDirecto.setLoading(false);
                    mensajeCorrecto('Autorizaci&oacute;n Servicio',json.mensaje,function(){
                        storeAseguradoFactura.removeAll();
                        cargarPaginacion(panelInicialPral.down('[name=params.ntramite]').getValue(), panelInicialPral.down('[name=params.nfactura]').getValue());
                    });
                }
                else {
                    gridFacturaDirecto.setLoading(false);
                    mensajeError(json.mensaje);
                }
            }
            ,failure : function() {
                gridFacturaDirecto.setLoading(false);
                errorComunicacion();
            }
        });
    }
}

//22.- Llamado para asociar una autorizacion o modificacion
function _11_modificarAutorizacion(record){
    _11_recordActivo = record;
    debug('_11_modificarAutorizacion :=: _11_recordActivo :',_11_recordActivo.data);
    
    _11_textfieldAseguradoMod.setValue(_11_recordActivo.get('NOMBRE'));
    var params = {
    	    'params.cdgarant'   :   _11_recordActivo.get('CDGARANT'),
            'params.cdconval'   :   _11_recordActivo.get('CDCONVAL'),
            'params.cdperson'   :   _11_recordActivo.get('CDPERSON')
    };
    cargaStorePaginadoLocal(storeListadoAutorizacion, _URL_LISTA_AUTSERVICIO, 'datosInformacionAdicional', params, function(options, success, response){
        if(success){
            var jsonResponse = Ext.decode(response.responseText);
            if(jsonResponse.datosInformacionAdicional.length <= 0) {
                storeConceptos.removeAll();
                storeAseguradoFactura.removeAll();
                banderaConcepto = 0;
                banderaAsegurado = 0;
                cargarPaginacion(panelInicialPral.down('[name=params.ntramite]').getValue(), panelInicialPral.down('[name=params.nfactura]').getValue());
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
    
//23.- Asociar autorizacion Nueva
function _11_asociarAutorizacionNueva(){
    var valido = _11_formModificarAuto.isValid();
    if(!valido) {
        datosIncompletos();
    }else{
        _11_windowModificarAut.close();
        guardaCambiosAutorizacionServ(_11_aseguradoSeleccionado, _11_textfieldNmautservMod.getValue(),"0","1");
    }
}

//23.1.-Guardar cambios autorizacion nueva
function guardaCambiosAutorizacionServ(record, numeroAutorizacion, tipoProceso, actMisiniper){
    debug("Valores de entrada para el guardado ",record);
    debug("Numero de Autorizacion : ",numeroAutorizacion);
    debug("Actualiza  actMisiniper : ",actMisiniper);
    gridFacturaDirecto.setLoading(true);
    Ext.Ajax.request({
        url     : _URL_CONSULTA_AUTORIZACION_ESP
        ,params : {
            'params.nmautser'  : numeroAutorizacion
        }
        ,success : function (response) {
            var jsonAutServ = Ext.decode(response.responseText).datosAutorizacionEsp;
            debug("jsonAutServ.feingres ==> "+jsonAutServ.feingres);
            debug("jsonAutServ.idTipoEvento ==> "+jsonAutServ.idTipoEvento);
            
            debug("VALOR DE RESPUESTA :: ",jsonAutServ);
            //4.- 
            _11_guardarDatosComplementario(
                record.data.CDUNIECO,
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
                record.data.COMPLEMENTO,
                record.data.NMSITUAC,
                null,
                null,
                record.data.NMCALLCENTER,
                actMisiniper,
                jsonAutServ.feingres,
                null,
                jsonAutServ.idTipoEvento,
                null,
                record.data.APLICFONDO
            );
            gridFacturaDirecto.setLoading(false);
        },
        failure : function (){
            gridFacturaDirecto.setLoading(false);
            centrarVentanaInterna(Ext.Msg.show({
                title:'Error',
                msg: 'Error de comunicaci&oacute;n',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.ERROR
            }));
        }
    });
}

//24.- Require autorizacion especial
function reqAutorizacionEspecial(ntramite, tipoPago, nfactura, cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmsituac, nmsinies, cdperson, cdtipsit){
    setTimeout(function(){
        windowAutEsp = Ext.create('Ext.window.Window',{
            modal       : true,
            buttonAlign : 'center',
            title: 'Autorizaci&oacute;n Especial',
            autoScroll  : true,
            items       : [
                panelModificacion = Ext.create('Ext.form.Panel', {
                    bodyPadding: 5,
                    items: [
                        {   xtype: 'numberfield'
                            ,fieldLabel: 'N&uacute;mero de autorizaci&oacute;n'
                            ,name       : 'txtAutEspecial'
                            ,allowBlank : false
                        }
                    ],
                    buttonAlign:'center',
                    buttons: [
                        {
                            text: 'Aceptar'
                            ,icon:_CONTEXT+'/resources/fam3icons/icons/accept.png'
                            ,buttonAlign : 'center',
                            handler: function() {
                                if (panelModificacion.form.isValid()) {
                                    var datos=panelModificacion.form.getValues();
                                    Ext.Ajax.request({
                                        url     : _URL_VALIDA_AUTESPECIFICA
                                        ,params:{
                                            'params.ntramite'  : ntramite,
                                            'params.tipoPago'  : tipoPago,
                                            'params.nfactura'  : nfactura,
                                            'params.cdunieco'  : cdunieco,
                                            'params.cdramo'    : cdramo,
                                            'params.estado'    : estado,
                                            'params.nmpoliza'  : nmpoliza,
                                            'params.nmsuplem'  : nmsuplem,
                                            'params.nmsituac'  : nmsituac,
                                            'params.nmautesp'  : datos.txtAutEspecial,
                                            'params.nmsinies'  : nmsinies
                                        }
                                        ,success : function (response){
                                            if(Ext.decode(response.responseText).validacionGeneral =="1"){
                                                //Exito y debe de dejar  pasar                                                  
                                                mensajeCorrecto('&Eacute;XITO','Se ha asociado correctamente.',function(){
                                                    windowAutEsp.close();
                                                    cargarPaginacion(panelInicialPral.down('[name=params.ntramite]').getValue(), panelInicialPral.down('[name=params.nfactura]').getValue());
                                                });
                                            }else{
                                                mensajeError("Autorizaci&oacute;n especial no valida para este tr&aacute;mite.");
                                            }
                                        },
                                        failure : function (){
                                            me.up().up().setLoading(false);
                                            centrarVentanaInterna(Ext.Msg.show({
                                                title:'Error',
                                                msg: 'Error de comunicaci&oacute;n',
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.Msg.ERROR
                                            }));
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
                        },{
                            text: 'Cancelar',
                            icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
                            buttonAlign : 'center',
                            handler: function() {
                                windowAutEsp.close();
                                cargarPaginacion(panelInicialPral.down('[name=params.ntramite]').getValue(), panelInicialPral.down('[name=params.nfactura]').getValue());
                            }
                        }
                    ]
                })  
            ],
            listeners:{
                 close:function(){
                     if(true){
                         cargarPaginacion(panelInicialPral.down('[name=params.ntramite]').getValue(), panelInicialPral.down('[name=params.nfactura]').getValue());
                     }
                 }
            }
        });
        centrarVentana(windowAutEsp.show());
    },100);
}

//25.1.- LLenamos el formulario de los detalles de la factura
function _11_llenaFormulario(){
    var valorRequerido = true;
    Ext.getCmp('historialCPT').disable();
    obtenerTotalPagos(_11_recordActivo.get('ntramite'), _11_recordActivo.get('factura'));

    if(_tipoPago ==_TIPO_PAGO_INDEMNIZACION){
        gridEditorConceptos.hide();                                         // Grid De los conceptos  Ocultos
        gridEditorCoberturaRecupera.hide();                                 // Grid De los conceptos  Recupera Ocultos
        panelInicialPral.down('[name="parametros.pv_otvalor01"]').hide();   // Aplica IVA oculto
        panelInicialPral.down('[name="parametros.pv_otvalor02"]').hide();   // Sec. de IVA oculto
        panelInicialPral.down('[name="parametros.pv_otvalor03"]').hide();   // Aplica IVA Retenido oculto
        panelInicialPral.down('combo[name=params.swAplicaisr]').hide();
        panelInicialPral.down('combo[name=params.swAplicaice]').hide();
        if(_11_params.CDRAMO == _RECUPERA){
            gridEditorCoberturaRecupera.show();                             // Grid De los conceptos  Recupera visibles
            panelInicialPral.down('[name=params.diasdedu]').hide();         // Dias deducible oculto
            valorRequerido = true;
        }else{
            panelInicialPral.down('[name=params.diasdedu]').show();         // Dias deducible visible
            valorRequerido = false;
        }
        
    }else if(_tipoPago ==_TIPO_PAGO_REEMBOLSO){
        gridEditorConceptos.show();                                         // Grid De los conceptos  visible
        gridEditorCoberturaRecupera.hide();                                 // Grid De los conceptos  Recupera Ocultos
        panelInicialPral.down('[name="parametros.pv_otvalor01"]').hide();   // Aplica IVA oculto
        panelInicialPral.down('[name="parametros.pv_otvalor02"]').hide();   // Sec. de IVA oculto
        panelInicialPral.down('[name="parametros.pv_otvalor03"]').hide();   // Aplica IVA Retenido oculto
        panelInicialPral.down('[name=params.diasdedu]').hide();             // Dias deducible oculto
        panelInicialPral.down('combo[name=params.swAplicaisr]').hide();
        panelInicialPral.down('combo[name=params.swAplicaice]').hide();
        valorRequerido = true;
    }else{
        gridEditorConceptos.show();                                         // Grid De los conceptos  visible
        gridEditorCoberturaRecupera.hide();                                 // Grid De los conceptos  Recupera Ocultos
        panelInicialPral.down('[name="parametros.pv_otvalor01"]').show();   // Aplica IVA
        panelInicialPral.down('[name="parametros.pv_otvalor02"]').show();   // Sec. de IVA
        panelInicialPral.down('[name="parametros.pv_otvalor03"]').show();   // Aplica IVA Retenido
        panelInicialPral.down('[name=params.diasdedu]').hide();             // Dias deducible oculto
        panelInicialPral.down('combo[name=params.swAplicaisr]').show();
        panelInicialPral.down('combo[name=params.swAplicaice]').show();
        valorRequerido = true;
    }
    
    panelInicialPral.down('[name=params.contrarecibo]').setValue(_11_recordActivo.get('contraRecibo'));     // ContraRecibo
    panelInicialPral.down('[name=params.ntramite]').setValue(_11_recordActivo.get('ntramite'));             // Tramite
    panelInicialPral.down('[name=params.nfactura]').setValue(_11_recordActivo.get('factura'));              // No. Factura
    panelInicialPral.down('[name=params.fefactura]').setValue(_11_recordActivo.get('fechaFactura'));        // Fecha de Factura
    panelInicialPral.down('[name=params.feegreso]').setValue(_11_recordActivo.get('fechaFactura'));         // Fecha Egreso
    panelInicialPral.down('[name=params.diasdedu]').setValue(_11_recordActivo.get('diasdedu'));             // Dias Deducible
    //De acuerdo al tipo de producto se valida si es o no requerido los dias de deducible
    panelInicialPral.down('[name=params.diasdedu]').allowBlank = valorRequerido;
    //Proveedor
    storeProveedor.load();
    panelInicialPral.down('combo[name=params.cdpresta]').setValue(_11_recordActivo.get('cdpresta'));
    
    panelInicialPral.down('combo[name=params.swAplicaisr]').setValue(_11_recordActivo.get('swisr'));
    panelInicialPral.down('combo[name=params.swAplicaice]').setValue(_11_recordActivo.get('swice'));
    
    storeTipoAtencion.load({
        params:{
            'params.cdramo':_11_params.CDRAMO,
            'params.cdtipsit':_cdtipAtencion,
            'params.tipoPago':_tipoPago
        }
    });
    panelInicialPral.down('combo[name=params.cdtipser]').setValue(_11_recordActivo.get('cdtipser'));        // Tipo de servicio u atencion
    panelInicialPral.down('[name=params.nfacturaOrig]').setValue(_11_recordActivo.get('factura'));          // No.de Factura original
    //Valores de los descuentos Numerico y Porcentaje
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
    
    panelInicialPral.down('combo[name=params.tipoMoneda]').setValue(_11_recordActivo.get('cdmoneda'));      // Tipo de moneda
    
    // Validamos si el tipo de moneda es pesos o diferente de ello
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
    cargarPaginacion(_11_recordActivo.get('ntramite'),_11_recordActivo.get('factura'));
    
    //Realizamos la consulta para validar la aplicacion de los IVA, Sec. del IVA y Aplicacion del IVA Retenido
    Ext.Ajax.request({
        url  : _URL_DATOS_VALIDACION
        ,params:{
            'params.ntramite'  : _11_recordActivo.get('ntramite')
            ,'params.nfactura' : _11_recordActivo.get('factura')
            ,'params.tipoPago' : _tipoPago
        }
        ,success : function (response) {
            if(Ext.decode(response.responseText).datosValidacion != null){
                var aplicaIVA       = null;
                var ivaRetenido     = null;
                var ivaAntesDespues = null;
                var autAR           = null;
                var autAM           = null;
                var commAR          = null;
                var commAM          = null;
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
    
    storeCobertura.proxy.extraParams= {
        'params.ntramite':_11_recordActivo.get('ntramite')
        ,'params.tipopago':_tipoPago
        ,catalogo       : _CATALOGO_COB_X_VALORES
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
}
    
//26.- Obtenemos la Suma Asegurada para Gastos Medicos Mayores
function obtenerSumaAseguradaMontoGastados (cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmsituac, cdgarant, cdconval, 
        cdperson, nmsinref, totalConsumido, nmsinies, valSesion, aplicaFondo, reclamo){
    
    debug("valSesion   ==========>>>>>"+valSesion+" totalConsumido==>"+totalConsumido);
    if(valSesion =="1"){
        //MULTISALUD INFONAVIT
        panelComplementos.down('[name=params.sumaAsegurada]').hide();
        panelComplementos.down('[name=params.sumaGastada]').hide();
        panelComplementos.down('[name=params.sublimite]').show();
        panelComplementos.down('[name=params.pagado]').show();
        panelComplementos.down('[name=params.disponibleCob]').show();
        Ext.Ajax.request({
            url     :   _URL_VALIDACION_CONSULTA
            ,params :   {
                'params.cdunieco'  : cdunieco,
                'params.cdramo'    : cdramo,
                'params.estado'    : estado,
                'params.nmpoliza'  : nmpoliza,
                'params.nmsuplem'  : nmsuplem,
                'params.nmsituac'  : nmsituac,
                'params.cdgarant'  : cdgarant,
                'params.cdconval'  : cdconval,
                'params.nmsinies'  : nmsinies,
                'params.swfonsin'  : aplicaFondo
            }
            ,success : function (response){
                var jsonResp = Ext.decode(response.responseText);
                debug("Valor de Respuesta ===>",jsonResp);
                if(jsonResp.success == true){
                    var infonavit = Ext.decode(response.responseText).datosInformacionAdicional[0];
                    var consultasTotales = infonavit.NO_CONSULTAS;
                    var limiteTotal      = infonavit.OTVALOR04;
                    var maxConsulta      = infonavit.OTVALOR07;
                    var diferenciador    = infonavit.OTVALOR15;
                    panelComplementos.down('[name=params.sublimite]').setValue(limiteTotal);
                    panelComplementos.down('[name=params.pagado]').setValue(infonavit.IMPGASTADOCOB);
                    panelComplementos.down('[name=params.disponibleCob]').setValue(+limiteTotal - +infonavit.IMPGASTADOCOB);
                }else{
                    maxconsultas = jsonResp.success;
                    centrarVentanaInterna(Ext.Msg.show({
                        title:'Error',
                        msg: jsonRes.mensaje,
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.ERROR
                    }));
                }
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
        
    }else if(valSesion =="2"){
        // GASTOS MEDICOS MAYORES
        if(cdramo =="11"){
            debug("Entra al caso 2.1");
            panelComplementos.down('[name=params.sumaAsegurada]').show();
            panelComplementos.down('[name=params.sumaGastada]').show();
            panelComplementos.down('[name=params.sublimite]').hide();
            panelComplementos.down('[name=params.pagado]').hide();
            panelComplementos.down('[name=params.disponibleCob]').hide();
            
            Ext.Ajax.request( {
                url  : _URL_OBTENER_SUMAASEGURADA
                ,params:{
                    'params.cdunieco'   : cdunieco
                    ,'params.cdramo'    : cdramo
                    ,'params.estado'    : estado
                    ,'params.nmpoliza'  : nmpoliza
                    ,'params.cdperson'  : cdperson
                    ,'params.nmsinref'  : reclamo
                }
                ,success : function (response){
                    var jsonResponse  = Ext.decode(response.responseText).datosValidacion[0];
                    debug("Obtenemos los valores de los datos de la Suma Asegurada ==>> "+jsonResponse);
                    var sumAsegurada  = jsonResponse.SUMA_ASEGURADA;
                    var sumDisponible = jsonResponse.RESERVA_DISPONIBLE;
                    
                    var sumaConceptos = (+sumDisponible) - (+ totalConsumido);
                    
                    panelComplementos.down('[name=params.sumaAsegurada]').setValue(sumAsegurada);
                    panelComplementos.down('[name=params.sumaGastada]').setValue(sumaConceptos);
                },
                failure : function () {
                    Ext.Msg.show({
                        title:'Error',
                        msg: 'Error de comunicaci&oacute;n',
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.ERROR
                    });
                }
            });
        }else{
            debug("Entra al caso 2.2 ");
            panelComplementos.down('[name=params.sumaAsegurada]').show();
            panelComplementos.down('[name=params.sumaGastada]').show();
            panelComplementos.down('[name=params.sublimite]').hide();
            panelComplementos.down('[name=params.pagado]').hide();
            panelComplementos.down('[name=params.disponibleCob]').hide();
            
            Ext.Ajax.request( {
                url  : _URL_OBTENER_SUMAASEGURADA
                ,params:{
                    'params.cdunieco'   : cdunieco
                    ,'params.cdramo'    : cdramo
                    ,'params.estado'    : estado
                    ,'params.nmpoliza'  : nmpoliza
                    ,'params.cdperson'  : cdperson
                    ,'params.nmsinref'  : nmsinref
                }
                ,success : function (response){
                    var jsonResponse  = Ext.decode(response.responseText).datosValidacion[0];
                    debug("Obtenemos los valores de los datos de la Suma Asegurada ==>> "+jsonResponse);
                    var sumAsegurada  = jsonResponse.SUMA_ASEGURADA;
                    var sumDisponible = jsonResponse.RESERVA_DISPONIBLE;
                    
                    var sumaConceptos = (+sumDisponible) - (+ totalConsumido);
                    
                    panelComplementos.down('[name=params.sumaAsegurada]').setValue(sumAsegurada);
                    panelComplementos.down('[name=params.sumaGastada]').setValue(sumaConceptos);
                },
                failure : function () {
                    Ext.Msg.show({
                        title:'Error',
                        msg: 'Error de comunicaci&oacute;n',
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.ERROR
                    });
                }
            });
        }
        

    }else{
        //TODOS DIFERENTES
        panelComplementos.down('[name=params.sumaAsegurada]').hide();
        panelComplementos.down('[name=params.sumaGastada]').hide();
        panelComplementos.down('[name=params.sublimite]').hide();
        panelComplementos.down('[name=params.pagado]').hide();
        panelComplementos.down('[name=params.disponibleCob]').hide();
    }
}

//14.1.- Funcion para obtener los totales pagados en el tramite-Factura
//15.1.- Funcion para obtener los totales pagados en el tramite-Factura despues de la eliminacion del asegurado
//17.1.- Funcion para obtener los totales al momento de guardar los conceptos 
function obtenerTotalPagos(ntramite, nfactura){
    var myMask = new Ext.LoadMask(Ext.getBody(), {msg:"loading..."});
    myMask.show();
    Ext.Ajax.request({
        url  : _URL_OBTENERSINIESTROSTRAMITE
        ,params:{
            'smap.ntramite'   : ntramite ,
            'smap.nfactura'   : nfactura
        }
        ,success : function (response) {
            var aseguradosTotales = Ext.decode(response.responseText).slist1;
            debug("Valores totales ==> ",aseguradosTotales);
            var totalPago = 0;
            var subtotalFactura=0;
            var ivaFactura=0;
            var ivaRetFactura=0;
            var isrFactura=0;
            var impCedFactura=0;
            var imporTotalFactura=0;
            var resultadoTope = "";
            var banderaValidacion = 0;
            var asegLimExced = []; //(EGS) arreglo guarda asegurados que exceden el limite
            debug("aseguradosTotales.length ===> ",aseguradosTotales.length);
            debug("aseguradosTotales ===> ", aseguradosTotales);
            
            for(var i = 0; i < aseguradosTotales.length; i++) {
                debug()
                totalPago = 0;
                var importeAseg = aseguradosTotales[i].IMPORTEASEG;
                var ivaAseg     = aseguradosTotales[i].PTIVAASEG;
                var ivaRete     = aseguradosTotales[i].PTIVARETASEG;
                var isrAse      = aseguradosTotales[i].PTISRASEG;
                var impCedul    = aseguradosTotales[i].PTIMPCEDASEG;
                var totalPagoVa = aseguradosTotales[i].IMPORTETOTALPAGO;
                
                if(+importeAseg > 0){
                    subtotalFactura = parseFloat(subtotalFactura) + parseFloat(importeAseg);
                }else{
                    subtotalFactura = parseFloat(subtotalFactura) + parseFloat(totalPago);
                }
                
                if(+ivaAseg > 0){
                    ivaFactura = parseFloat(ivaFactura) + parseFloat(ivaAseg);
                }else{
                    ivaFactura = parseFloat(ivaFactura) + parseFloat(totalPago);
                }
                
                if(+ivaRete > 0){
                    ivaRetFactura = parseFloat(ivaRetFactura) + parseFloat(ivaRete);
                }else{
                    ivaRetFactura = parseFloat(ivaRetFactura) + parseFloat(totalPago);
                }
                
                if(+isrAse > 0){
                    isrFactura = parseFloat(isrFactura) + parseFloat(isrAse);
                }else{
                    isrFactura = parseFloat(isrFactura) + parseFloat(totalPago);
                }
                
                if(+impCedul > 0){
                    impCedFactura = parseFloat(impCedFactura) + parseFloat(impCedul);
                }else{
                    impCedFactura = parseFloat(impCedFactura) + parseFloat(totalPago);
                }
                
                if(+totalPagoVa > 0){
                    imporTotalFactura = parseFloat(imporTotalFactura) + parseFloat(totalPagoVa);
                }else{
                    imporTotalFactura = parseFloat(imporTotalFactura) + parseFloat(totalPago);
                }
                
                var limite = aseguradosTotales[i].LIMITE;
                var importePagado = aseguradosTotales[i].IMPPAGCOB;
                var importeDisponible = (+limite - +importePagado);
                var importePagarAsegurado = aseguradosTotales[i].IMPORTETOTALPAGO; 

                var validaTope = aseguradosTotales[i].VALTOTALCOB;
                if( validaTope  == '1'){
                    debug("Limite ",limite, "importeDisponible",importeDisponible,"importePagarAsegurado",importePagarAsegurado);
                    if(+importeDisponible <=limite && +importePagarAsegurado <= importeDisponible){
                        //resultadoTope = resultadoTope + 'La Factura ' + nfactura + ' del siniestro '+ aseguradosTotales[i].NMSINIES+ ' Es éxitoso. <br/>';
                    }else{
                        banderaValidacion = 1;
                        resultadoTope = resultadoTope + 'El CR '+ntramite+' de Factura ' + nfactura + ' del siniestro '+ aseguradosTotales[i].NMSINIES+ ' Sobrepasa el límite permitido. <br/>';
                        aseguradosTotales[i].REQAUTES = "1";    //(EGS) marcamos el siniestro para autorización especial, porque sobrepasa el límite
                        
                        asegLimExced.push(aseguradosTotales[i]);    //(EGS) lo agregamos al arreglo a enviar
                    }
                }
            }
            
            if(banderaValidacion == "1"){
                centrarVentanaInterna(mensajeWarning(resultadoTope));
                // (EGS) Actualizamos registros que requieren autorizacion especial por excedente límite medicamentos
                var mapa = {ntramite : ntramite, nfactura : nfactura};
                var json = {smap : mapa, slist1 : asegLimExced};
                debug('datos a enviar:',json);
                Ext.Ajax.request({
                    url         : _URL_REQAUTES
                    ,jsonData   : json
                    ,success    : function(response){
                        debug("Habiendo actualizado", json);
                    }
                });
                // fin (EGS)
            }else{
            	procesoContinuar = "0";
            	validaGralImpTramFactura(ntramite,nfactura,_tipoPago,procesoContinuar);
            }
            
            panelComplementos.down('[name=params.subtotalFac]').setValue(subtotalFactura);
            panelComplementos.down('[name=params.ivaFac]').setValue(ivaFactura);
            panelComplementos.down('[name=params.ivaRetFac]').setValue(ivaRetFactura);
            panelComplementos.down('[name=params.isrFac]').setValue(isrFactura);
            panelComplementos.down('[name=params.impCedularFac]').setValue(impCedFactura);
            panelComplementos.down('[name=params.impPagarFac]').setValue(imporTotalFactura);
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
    myMask.hide();
    return true;
}

function validaGralImpTramFactura(ntramite,nfactura,tipopago,procesoContinuar){
    var myMask = new Ext.LoadMask(Ext.getBody(), {msg:"loading..."});
    myMask.show();
    Ext.Ajax.request({
        url     : _URL_VALIDA_IMPASEGURADOSINIESTRO
        ,params:{
            'params.ntramite'  : ntramite,
            'params.tipopago'  : tipopago,
            'params.nfactura'  : nfactura
        }
        ,success : function (response) {
            json = Ext.decode(response.responseText);
            if(json.success==false){
                myMask.hide();
                centrarVentanaInterna(mensajeWarning(json.msgResult));
            }else{
            	myMask.hide();
            	if(procesoContinuar =="1"){
            		_11_mostrarSolicitudPago();
            	}
            }
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


//27.- Guardamos los datos complementarios del Asegurado cuando realizamos cambios del asegurado Causa Siniestro, ICD,Cobertura,Subcobertura
function guardarDatosComplementarios(grid,rowIndex){
    var record = grid.getStore().getAt(rowIndex);
    banderaAsegurado = 0;
    guardaDatosComplementariosAsegurado(record, banderaAsegurado);
}

//27.1.- Realizamos la validacion con respecto al OTVALOR19 y otvalor20 del mapa de coberturas
function guardaDatosComplementariosAsegurado(record, banderaAsegurado){
    debug("VALOR DEL RECORD ==> ",record);
    var tipoEvento  = record.data.FLAGTIPEVE;
    var tipoAlta    = record.data.FLAGTIPALT;
    var fecha1      = Ext.Date.format(record.data.FEINGRESO, 'd/m/Y');
    var fecha2      = Ext.Date.format(record.data.FEEGRESO, 'd/m/Y');
    var maxconsultas= true;
    var procesoVal  = true;
    var mensajeGral = "";
    if(tipoEvento =="1"){
        if(record.data.CDTIPEVE.length <= 0){
            procesoVal = false;
            mensajeGral = "Selecciona el tipo de Evento. <br/>";
        }
    }
    
    if(tipoAlta =="1"){
        if(record.data.CDTIPALT.length <= 0){
            procesoVal = false;
            mensajeGral = mensajeGral + "Selecciona el tipo de Alta. <br/>";
        }
        if(fecha1.length <= 0){
            procesoVal = false;
            mensajeGral = mensajeGral + "La fecha de ingreso no puede ser vac&iacute;a. <br/>";
        }
        if(fecha2.length <= 0){
            procesoVal = false;
            mensajeGral = mensajeGral + "La fecha de egreso no puede ser vac&iacute;a. <br/>";
        }
        
        if((Date.parse(record.data.FEEGRESO)) < (Date.parse(record.data.FEINGRESO))){
        procesoVal = false;
            mensajeGral = mensajeGral + "La fecha de egreso no puede ser menor a la fecha ingreso. <br/>";
        }
    }
    
    if(procesoVal == true){
        if(fecha1.length > 0){
            if(fecha2 <= 0){
                procesoVal = false;
                mensajeGral = mensajeGral + "La fecha de egreso no puede ser vac&iacute;a. <br/>";
            }
            if((Date.parse(record.data.FEEGRESO)) < (Date.parse(record.data.FEINGRESO))){
            procesoVal = false;
                mensajeGral = mensajeGral + "La fecha de egreso no puede ser menor a la fecha ingreso. <br/>";
            }
        }
    }
    
    if(procesoVal == false){
        mensajeWarning(mensajeGral);
    }else{
        guardaDatosComplementariosValidacionAsegurado(record, banderaAsegurado);
    }

}

//27.2.-Guardamos los datos complementarios del Asegurado
function guardaDatosComplementariosValidacionAsegurado(record, banderaAsegurado){
    debug("VALOR DEL RECORD ==> ",record);
    
    var cdramo     = record.data.CDRAMO;
    var idICD      = record.data.CDICD;
    var idCdgarant = record.data.CDGARANT;
    var idConval   = record.data.CDCONVAL;
    var idcausa    = record.data.CDCAUSA;
    var cdtipsit   = record.data.CDTIPSIT;
    var maxconsultas= true;
    //Realizamos la validacion con la tabla de apoyo para saber si se realiza la validacion de las consultas
    if(idICD.length > 0 && idCdgarant.length > 0 && idConval.length > 0 && idcausa.length > 0){
        var valorRegistro = "1";
        if(banderaAsegurado == "1"){
            valorRegistro = "0";
        }
        
        Ext.Ajax.request({
            url     : _URL_VAL_CONDICIONGRAL
            ,params : {
                'params.cdramo'   : cdramo,
                'params.cdtipsit' : cdtipsit,
                'params.causaSini': 'VALGRAL',
                'params.cveCausa' : '1'
            }
            ,success : function (response){
                var datosExtras = Ext.decode(response.responseText);
                
                if(Ext.decode(response.responseText).datosInformacionAdicional != null){
                    var cveCauSini=Ext.decode(response.responseText).datosInformacionAdicional[0];
                    debug("cveCauSini.REQVALIDACION ==>",cveCauSini.REQVALIDACION,"cveCauSini.REQCONSULTAS ===>",cveCauSini.REQCONSULTAS);
                    if(cveCauSini.REQCONSULTAS =="S"){//2.- No. de Consultas
                        Ext.Ajax.request({
                            url     :   _URL_VALIDACION_CONSULTA
                            ,params :   {
                                'params.cdunieco'  : record.data.CDUNIECO,
                                'params.cdramo'    : record.data.CDRAMO,
                                'params.estado'    : record.data.ESTADO,
                                'params.nmpoliza'  : record.data.NMPOLIZA,
                                'params.nmsuplem'  : record.data.NMSUPLEM,
                                'params.nmsituac'  : record.data.NMSITUAC,
                                'params.cdgarant'  : record.data.CDGARANT,
                                'params.cdconval'  : record.data.CDCONVAL,
                                'params.nmsinies'  : record.data.NMSINIES,
                                'params.swfonsin'  : record.data.SWFONSIN
                            }
                            ,success : function (response){
                                var jsonRes = Ext.decode(response.responseText);
                                debug("Valor de Respuesta ===>",jsonRes);
                                if(jsonRes.success == true){
                                    var infonavit = Ext.decode(response.responseText).datosInformacionAdicional[0];
                                    debug("infonavit 2 ===>",infonavit);
                                    var consultasTotales = infonavit.NO_CONSULTAS;
                                    var maxConsulta      = infonavit.OTVALOR07;
                                    var diferenciador    = infonavit.OTVALOR15;
                                    debug("consultasTotales 2 ==>",consultasTotales,"maxConsulta =>",maxConsulta);
                                    debug("diferenciador 2 ==>",diferenciador);
                                    
                                    if(diferenciador == "MEI"){
                                        if(+consultasTotales > +maxConsulta){ //(EGS) se deja > en vez de >=
                                            maxconsultas = false;
                                            centrarVentanaInterna(Ext.Msg.show({
                                                   title: 'Aviso',
                                                   msg: 'Se sobrepas&oacute; el n&uacute;mero m&aacute;ximo de servicios para este Asegurado.',
                                                   buttons: Ext.Msg.OK,
                                                   icon: Ext.Msg.WARNING
                                            }));
                                        }
                                    }else{
                                        if(+consultasTotales <= +maxConsulta){
                                            maxconsultas = false;
                                            centrarVentanaInterna(Ext.Msg.show({
                                                   title: 'Aviso',
                                                   msg: 'Se sobrepas&oacute; el n&uacote;mero m&aacute;ximo de servicios para este Asegurado.',
                                                   buttons: Ext.Msg.OK,
                                                   icon: Ext.Msg.WARNING
                                            }));
                                        }
                                    }
                                    
                                    if(maxconsultas  == true){
                                        //1.-
                                        debug("Caso ===> 1 : ",record.data);
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
                                            record.data.COMPLEMENTO,
                                            record.data.NMSITUAC,
                                            record.data.DEDUCIBLE,
                                            record.data.COPAGO,
                                            record.data.NMCALLCENTER,
                                            "0",
                                            Ext.Date.format(record.data.FEINGRESO, 'd/m/Y'),
                                            Ext.Date.format(record.data.FEEGRESO, 'd/m/Y'),
                                            record.data.CDTIPEVE,
                                            record.data.CDTIPALT,
                                            record.data.SWFONSIN
                                        );
                                    }
                                }else{
                                    maxconsultas = jsonRes.success;
                                    centrarVentanaInterna(Ext.Msg.show({
                                        title:'Error',
                                        msg: jsonRes.mensaje,
                                        buttons: Ext.Msg.OK,
                                        icon: Ext.Msg.ERROR
                                    }));
                                }
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
                    }else{
                        //2.-
                        debug("Caso ===>2 : ",record.data);
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
                            record.data.COMPLEMENTO,
                            record.data.NMSITUAC,
                            record.data.DEDUCIBLE,
                            record.data.COPAGO,
                            record.data.NMCALLCENTER,
                            "0",
                            Ext.Date.format(record.data.FEINGRESO, 'd/m/Y'),
                            Ext.Date.format(record.data.FEEGRESO, 'd/m/Y'),
                            record.data.CDTIPEVE,
                            record.data.CDTIPALT,
                            record.data.SWFONSIN
                        );
                    }
                }
            },failure : function (){
                centrarVentanaInterna(Ext.Msg.show({
                    title:'Error',
                    msg: 'Error de comunicaci&oacute;n',
                    buttons: Ext.Msg.OK,
                    icon: Ext.Msg.ERROR
                }));
            }
        });
    }else{
        mensajeWarning(
            'Complemente la informaci&oacute;n del Asegurado');
    }
}

//27.3.- Guardar datos complementarios
function _11_guardarDatosComplementario(cdunieco,cdramo, estado, nmpoliza, nmsuplem,
                                    aaapertu, nmsinies,feocurre, nmreclamo, cdicd,
                                    cdicd2,cdcausa, cdgarant,cdconval, nmautser,
                                    cdperson, tipoProceso, complemento,nmsituac,
                                    deducible, copago,nmcallcenter, actMisiniper,
                                    fechaIngreso,fechaEgreso,cveEvento, cveAlta, aplicFondo){
    
    debug("Datos de guardado 1 ===> ","cdunieco :"+cdunieco,"cdramo :"+cdramo, "estado :"+estado, "nmpoliza :"+nmpoliza);
    debug("Datos de guardado 2 ===> ","nmsuplem :"+nmsuplem,"aaapertu :"+aaapertu, "nmsinies :"+nmsinies,"feocurre :"+feocurre);
    debug("Datos de guardado 3 ===> ","nmreclamo :"+nmreclamo, "cdicd:"+cdicd,"cdicd2:"+cdicd2,"cdcausa:"+cdcausa);
    debug("Datos de guardado 4 ===> ","cdgarant :"+cdgarant,"cdconval :"+cdconval, "nmautser :"+nmautser,"cdperson :"+cdperson);
    debug("Datos de guardado 5 ===> ","tipoProceso :"+tipoProceso, "complemento :"+complemento,"nmsituac :"+nmsituac);
    debug("Datos de guardado 6 ===> ","deducible :"+deducible, "copago :"+copago,"nmcallcenter :"+nmcallcenter, "actMisiniper :"+actMisiniper);
    debug("Datos de guardado 7 ===> ","fechaIngreso :"+fechaIngreso, "fechaEgreso :"+fechaEgreso,"cveEvento :"+cveEvento, "cveAlta :"+cveAlta);
    Ext.Ajax.request( {
        url  : _URL_ACTUALIZA_INFO_GRAL_SIN
        ,params:{
            'params.cdunieco'       : cdunieco,
            'params.cdramo'         : cdramo,
            'params.estado'         : estado,
            'params.nmpoliza'       : nmpoliza,
            'params.nmsuplem'       : nmsuplem,
            'params.aaapertu'       : aaapertu,
            'params.nmsinies'       : nmsinies,
            'params.feocurre'       : feocurre,
            'params.nmreclamo'      : nmreclamo,
            'params.cdicd'          : cdicd,
            'params.cdicd2'         : cdicd2,
            'params.cdcausa'        : cdcausa,
            'params.ntramite'       : panelInicialPral.down('[name=params.ntramite]').getValue(),
            'params.cdgarant'       : cdgarant,
            'params.cdconval'       : cdconval,
            'params.nmautser'       : nmautser,
            'params.tipoPago'       : _tipoPago,
            'params.nfactura'       : panelInicialPral.down('[name=params.nfactura]').getValue(),
            'params.fefactura'      : panelInicialPral.down('[name=params.fefactura]').getValue(),
            'params.cdtipser'       : panelInicialPral.down('combo[name=params.cdtipser]').getValue(),
            'params.cdpresta'       : panelInicialPral.down('combo[name=params.cdpresta]').getValue(),
            'params.ptimport'       : panelInicialPral.down('[name=params.ptimport]').getValue(),
            'params.descporc'       : panelInicialPral.down('[name=params.descporc]').getValue(),
            'params.descnume'       : panelInicialPral.down('[name=params.descnume]').getValue(),
            'params.tipoMoneda'     : panelInicialPral.down('combo[name=params.tipoMoneda]').getValue(),
            'params.tasacamb'       : panelInicialPral.down('[name=params.tasacamb]').getValue(),
            'params.ptimporta'      : panelInicialPral.down('[name=params.ptimporta]').getValue(),
            'params.feegreso'       : panelInicialPral.down('[name=params.feegreso]').getValue(),
            'params.diasdedu'       : panelInicialPral.down('[name=params.diasdedu]').getValue(),
            'params.dctonuex'       : null,
            'params.cdperson'       : cdperson,
            'params.tipoProceso'    : tipoProceso,
            'params.complemento'    : complemento,
            'params.actMisiniper'   : actMisiniper,
            'params.nmsituac'       : nmsituac,
            'params.deducible'      : deducible,
            'params.copago'         : copago,
            'params.nmcallcenter'   : nmcallcenter,
            'params.feingreso'      : fechaIngreso,
            'params.feegreso'       : fechaEgreso,
            'params.cveEvento'      : cveEvento,
            'params.cveAlta'        : cveAlta,
            'params.aplicFondo'     : aplicFondo
        }
        ,success : function(response, opts) {   //(EGS)
            banderaAsegurado = 0;
            storeConceptos.removeAll();
            cargarPaginacion(panelInicialPral.down('[name=params.ntramite]').getValue(), panelInicialPral.down('[name=params.nfactura]').getValue());
        },
        failure : function(response, opts) {    //(EGS)
            
            var obj = Ext.decode(response.responseText);    //(EGS)
            var mensaje = obj.mensaje;  //(EGS)
            debug(obj.mensaje); //(EGS)
            
            Ext.Msg.show({
                title:'Error',
                msg: Ext.isEmpty(mensaje) ? 'Error de comunicaci&oacute;n' : mensaje,   //(EGS)
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.ERROR
            });
        }
    });
}  
    
    
    
    
    
    
    
    
    
//(EGS) Codigo que se ejecuta cuando usuario da click en botón Aplicar Cambios Factura.
// Se hace función para re-utilizarlo
function _11_clickAplicarCambiosFactura(){
    debug("_11_clickAplicarCambiosFactura");
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
                'El tr&aacute;mite ser&aacute; cancelado debido a que no ha sido autorizado alguno de los siniestros'
                ,function(){
                    _11_rechazarTramiteSiniestro();
                });
        }else{
            //Guardamos la información de la factura
            panelInicialPral.form.submit({
                waitMsg:'Procesando...',    
                url: _URL_GUARDA_CAMBIOS_FACTURA,
                failure: function(form, action) {
                    centrarVentanaInterna(mensajeError("Verifica los datos requeridos"));
                },
                success: function(form, action) {
                    if(_11_params.CDTIPTRA == _TIPO_PAGO_AUTOMATICO){
                        Ext.create('Ext.form.Panel').submit({
                            standardSubmit :true
                            ,params     : {
                                'params.ntramite' : _11_params.NTRAMITE
                            }
                        });
                        panelInicialPral.getForm().reset();
                        storeAseguradoFactura.removeAll();
                        storeConceptos.removeAll();
                        }
                }
            });
        }
    }
}

function _11_obtieneDatosOpcionalesValor(cdramo,cdtipsit,cdgarant,cdconval,record,tipo){
    Ext.Ajax.request({
        url  : _URL_ALTA_EVENTO 
        ,params:{
            'params.cdramo'    : cdramo,
            'params.cdtipsit'  : cdtipsit,
            'params.cdgarant'  : cdgarant,
            'params.cdconval'  : cdconval
        }
        ,success : function (response) {
            //Obtenemos los datos
            if(Ext.decode(response.responseText).datosValidacion != null){
                var jsonValidacionCober =Ext.decode(response.responseText).datosValidacion;
                debug("Valor de los datos de Respuesta para validaciones de alta =>",jsonValidacionCober[0]);
                if(jsonValidacionCober[0].FLAGREQAUT == "SI" &&  (  record.get('NMAUTSER') =="N/A" || +record.get('NMAUTSER') <= '0' || record.get('NMAUTSER')== "")){
                    _11_modificarAutorizacion(record);
                	/*msgWindow = Ext.Msg.show({
                        title: 'Aviso',
                        msg: 'Se requiere una autorizaci&oacute;n de Servicio. <br/> &iquest;Desea realizar la asociaci&oacute;n ?',
                        buttons: Ext.Msg.YESNO,
                        icon: Ext.Msg.QUESTION,
                        fn: function(buttonId, text, opt){
                            if(buttonId == 'no'){
                                // actualizamos la información
                                if(tipo =="0"){
                                    banderaAsegurado = 0;
                                    storeConceptos.removeAll();
                                    cargarPaginacion(panelInicialPral.down('[name=params.ntramite]').getValue(), panelInicialPral.down('[name=params.nfactura]').getValue());
                                }                                
                            }
                            if(buttonId == 'yes'){
                                _11_modificarAutorizacion(record);
                            }
                        }
                    });
                    centrarVentanaInterna(msgWindow);*/
                }
            }
        },
        failure : function () {
            Ext.Msg.show({
                title:'Error',
                msg: 'Error de comunicaci&oacute;n',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.ERROR
            });
        }
    });
}

function validaCapturaResultadoEstudiosAsegurado(paramsResEstudios){
	
	Ext.Ajax.request( {
        url  : _UrlValidaCapturaEstudiosCobertura 
        ,params:{
            'params.pi_cdramo'    : paramsResEstudios.cdramo,
            'params.pi_cdtipsit'  : paramsResEstudios.cdtipsit,
            'params.pi_cdgarant'  : paramsResEstudios.cdgarant,
            'params.pi_cdconval'  : paramsResEstudios.cdconval
        }
        ,success : function (response) {
        	
            if(Ext.decode(response.responseText).params != null){
                var respuestaValidacionCober =Ext.decode(response.responseText).params.CAPTURA_RESULTADOS;
                
                if(respuestaValidacionCober == 'S'){
                	capturaResultadosInf(paramsResEstudios.cdunieco, paramsResEstudios.cdramo, paramsResEstudios.aaapertu,
            				paramsResEstudios.status, paramsResEstudios.nmsinies, paramsResEstudios.nmsituac, paramsResEstudios.cdtipsit,
            				paramsResEstudios.cdgarant, paramsResEstudios.cdconval, paramsResEstudios.dsconval, paramsResEstudios.dsasegurado);
                }else{
                	if(paramsResEstudios.accionBoton){
                		mensajeInfo('La Cobertura y Subcobertura de este asegurado no aplican para esta acci&oacute;n');
                	}
                }
            }
        },
        failure : function () {
            Ext.Msg.show({
                title:'Error',
                msg: 'Error de comunicaci&oacute;n',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.ERROR
            });
        }
    });	
	
}

function capturaResultadosInf(_cdunieco,_cdramo,_aaapertu,_status,_nmsinies,_nmsituac,_cdtipsit,_cdgarant,_cdconval,_dsconval,_dsasegurado){
	
	var bufferTiposResultadoEstudios = new Ext.util.HashMap();
	var windowResultEstudios;
	
	var recordSelCombo;
	
	var _TIPO_PROVEEDOR_MEDICO = '15';
	var _TIPO_PROVEEDOR_LAB    = '16';
	
	/////////////////////
	////// modelos //////
	/*/////////////////*/
	
	Ext.define('modeloResEstudios',
			{
				extend : 'Ext.data.Model'
				,fields :
				['CDCONCEP','DSCONCEP','CDEST','DSEST','CDRESEST','VALOR', 'OBSERV','SWOBLVAL','SWOBLRES', 'CDTABRES', 'SWOBLEST','CDTIPO']
	});
	
	/*/////////////////*/
	////// modelos //////
	/////////////////////
	
	////////////////////
	////// stores //////
	/*////////////////*/
	
	var editorPluginResultados = Ext.create('Ext.grid.plugin.CellEditing', {
    	//autoCancel : false,
    	//dirtyText  : 'Necesita Aceptar o Cancelar sus cambios',
        clicksToEdit: 1,
        listeners: {
        	beforeedit: function(editor, context, eOpts ){
        		
        		if(context.field == 'CDRESEST'){
        			var comboColumnaFila = context.column.getEditor(context.record);
            		debug('Combo CDRESEST:',comboColumnaFila);
            		if(Ext.isEmpty(context.record.get('CDRESEST'))){
            			comboColumnaFila.clearValue();
            		}
        			
            		comboColumnaFila.getStore().load({
            			params:{
            				'params.cdest': context.record.get('CDEST')
            			}
            		});
        		}       		
        	}
        }
    });
	
	Ext.define('tiposResEstudioStore', {
		extend : 'Ext.data.Store',
		constructor : function(cfg) {
			var me = this;
			cfg = cfg || {};
			me.callParent([ Ext.apply({
		        model   : 'Generic'
		        ,proxy   :
		        {
		            type        : 'ajax'
		                ,url        : _UrlCatTiposResultado
		                ,reader     :
		                {
		                    type  : 'json'
		                    ,root : 'cargaLista'
		                }
		        }
		    }, cfg) ]);
		}
	});

	Ext.define('tiposResEstudioStoreTodos', {
		extend : 'Ext.data.Store',
		constructor : function(cfg) {
			var me = this;
			cfg = cfg || {};
			me.callParent([ Ext.apply({
				model   : 'Generic'
					,proxy   :
					{
						type        : 'ajax'
							,url        : _UrlCatTiposResultadoTodos
							,reader     :
							{
								type  : 'json'
									,root : 'cargaLista'
							}
					}
			}, cfg) ]);
		}
	});
	
	var storeResEstudiosAux = new tiposResEstudioStoreTodos();
	storeResEstudiosAux.load({
		callback: function(recordsEstRes, operationEst, successEst){
			
			Ext.Array.each(recordsEstRes,function(recordsEstResIT, indexRecRes){
				bufferTiposResultadoEstudios.add(recordsEstResIT.get('aux')+'-'+recordsEstResIT.get('key'), recordsEstResIT.get('value'));
           	});
		}
	});

	
	var estudiosCobAsegStore = Ext.create('Ext.data.Store',
    {
		pageSize : 20
        ,model   : 'modeloResEstudios'
        ,groupField: 'CDCONCEP'
        ,proxy   :
        {
        	type        : 'ajax'
            ,url        : _UrlConsultaDatosEstudiosCobAsegurado
            ,reader     :
            {
                type  : 'json'
                ,root : 'loadList'
            }
        }
    });
	
	/*////////////////*/
	////// stores //////
	////////////////////
	
	///////////////////////
	////// contenido //////
	/*///////////////////*/
	
	
	var resultadosEstudiosGrid = Ext.create('Ext.grid.Panel',
	    {
    	title : "Capture los resultados del asegurado: " + _dsasegurado//"Capture los resultados de '"+ _dsconval + "' para: " + _dsasegurado
    	,height : 420
    	,store : estudiosCobAsegStore
    	,autoScroll: true
    	,columns :
    	[ { header     : 'Estudios por Concepto' , dataIndex : 'DSEST', flex: 3},
          { header     : 'Resultado', dataIndex : 'CDRESEST', flex: 1,
    			editor : {
	                xtype         : 'combobox',
	                name          : 'CDRESEST',
	                valueField    : 'key',
	                displayField  : 'value',
	                forceSelection: false,
	                anyMatch      : true,
                    queryMode     : 'local',
                    allowBlank    : true,
                    queryCaching  : false,
	                store         : new tiposResEstudioStore(),
	                listeners     : {
	                	change    : function(cmb, newValue){
	                		var value = cmb.getRawValue();
		                	var index = cmb.getStore().findExact('value', value);
		                	if(index == -1){
		                		cmb.setValue('');
		                		recordSelCombo.set('CDRESEST','');
		                		debug('REcord Seleccionado222:::',recordSelCombo);
		                	}
	                	}
	                }
	            },
	            renderer: function(value,metaData,record,rowIndex,colIndex,store){
	            	return rendererColumnUsandoEditorDep(value,record,colIndex,this); //this -> resultadosEstudiosGrid
	            }
          },
          { header     : 'Valor' , dataIndex : 'VALOR', flex: 1,
	  			editor : {
	                xtype         : 'textfield',
	                name          : 'VALOR',
	                maxLength     : 250 
	            }
	      },
	      { header     : 'Observaciones' , dataIndex : 'OBSERV', flex: 2,
	  			editor : {
	                xtype         : 'textfield',
	                name          : 'OBSERV',
	                maxLength     : 500
	            }
	      }//,
	      //{ header     : 'OblP' , dataIndex : 'SWOBLEST', flex: 1}
		],
		features: [{
			groupHeaderTpl: [
			    '{children:this.descConcept}',
			    {
			    	descConcept: function(children){
			    		//debug('Para GroupSummary, children',children.length);
			    		
			            if(children.length > 0){
			            	var tipoConcepto = '';
			            	
			            	if(children[0].get('CDTIPO') == _TIPO_PROVEEDOR_MEDICO){
				    			tipoConcepto = ' (MEDICO)';
				    		}else if(children[0].get('CDTIPO') == _TIPO_PROVEEDOR_LAB){
				    			tipoConcepto = ' (LABORATORIO)';
				    		}
			            	return children[0].get('DSCONCEP') + tipoConcepto;
			            }
			            
			            return 'Sin Concepto';
			           
			        }
			    }
			],
            ftype:'groupingsummary',
            startCollapsed : true
        }],
        //selType: 'rowmodel',
        plugins: [
            editorPluginResultados
        ],
        listeners     : {
        	select    : function(grd, record){
        		recordSelCombo = record;
        	}
        },
        buttonAlign: 'center',
        buttons: [{
            icon    : _CONTEXT + '/resources/fam3icons/icons/disk.png',
            text    : 'Guardar Datos',
            handler : function(btn, e){
            	
                var updateList = [];
                var estudioRequiereResVal    = false;
                var estudioRequiereResValDes = '';
                var mensajeRequiereCampo     = '';
                
                
                estudiosCobAsegStore.each(function(record) {
                	if( !Ext.isEmpty(record.get('CDRESEST')) || !Ext.isEmpty(record.get('VALOR')) || !Ext.isEmpty(record.get('OBSERV')) ){
                		
                		/* Valida para este estudio si el Resultado es obligatorio */
                		if(!Ext.isEmpty(record.get('SWOBLRES')) && record.get('SWOBLRES') == 'S'
                			&& Ext.isEmpty(record.get('CDRESEST'))){
                			estudioRequiereResVal = true;
                    		estudioRequiereResValDes = record.get('DSEST');
                    		mensajeRequiereCampo = 'Resultado';
                    		return false;
                		}
                		
                		/* Valida para este estudio si el Valor es obligatorio */
                		if(!Ext.isEmpty(record.get('SWOBLVAL')) && record.get('SWOBLVAL') == 'S'
                			&& Ext.isEmpty(record.get('VALOR'))){
                			estudioRequiereResVal = true;
                    		estudioRequiereResValDes = record.get('DSEST');
                    		mensajeRequiereCampo = 'Valor';
                    		return false;
                		}
                		
                	}
                });
                
                if(estudioRequiereResVal){
	            	mensajeWarning("Debe capturar un " + mensajeRequiereCampo + " para el estudio: ''"
	            			+estudioRequiereResValDes+"'' o borre toda la informaci&oacute;n del mismo.");
	            	return;
	            }
                
                estudiosCobAsegStore.getUpdatedRecords().forEach(function(record,index,arr){
                	
                    var datosResultado = {
                   		 'pi_cdunieco': _cdunieco,
                   		 'pi_cdramo': _cdramo,
                   		 'pi_aaapertu': _aaapertu,
                   		 'pi_status': _status,
                   		 'pi_nmsiniest': _nmsinies,
                   		 'pi_nmsituac': _nmsituac,
                   		 'pi_cdtipsit': _cdtipsit,
                   		 'pi_cdgarant': _cdgarant,
                   		 'pi_cdconval': _cdconval,
                   		 'pi_cdconcep' : record.get('CDCONCEP'),
                   		 'pi_cdest' : record.get('CDEST'),
                   		 'pi_cdresest' : record.get('CDRESEST'),
                   		 'pi_valor' : record.get('VALOR'),
                   		 'pi_observ' : record.get('OBSERV')
                    };
               	    
                    updateList.push(datosResultado);
                });
                
                debug('Lista de Resultados a guardar: ',updateList);

                var maskGuarda = _maskLocal('Guardando...');
                
                Ext.Ajax.request({
                    url: _UrlActualizaEliminaEstudiosCobertura,
                    jsonData : {
                        'saveList' : updateList
                    },
                    success  : function(response, options){
                   	 maskGuarda.close();
                        var json = Ext.decode(response.responseText);
                        if(json.success){
                       	 
//                            mensajeCorrecto('Aviso','Se ha guardado correctamente.', function(){
                           	 Ext.Ajax.request( {
                           	        url  : _UrlValidaDatosEstudiosReclamacion 
                           	        ,params:{
                           	        	'params.pi_cdunieco': _cdunieco,
                                  		 	'params.pi_cdramo': _cdramo,
                                  		 	'params.pi_aaapertu': _aaapertu,
                                  		 	'params.pi_status': _status,
                                  		 	'params.pi_nmsiniest': _nmsinies,
                                  		 	'params.pi_nmsituac': _nmsituac,
                                  		 	'params.pi_cdtipsit': _cdtipsit,
                                  		 	'params.pi_cdgarant': _cdgarant,
                                  		 	'params.pi_cdconval': _cdconval
                           	        }
                           	        ,success : function (response) {
                           	        	
                           	        	estudiosCobAsegStore.reload();
                           	        	
                           	            if(Ext.decode(response.responseText).params != null){
                           	                var respuestaValidacionResEst = Ext.decode(response.responseText).params.VALIDA_RES_RECL;
                           	                var respuestaTipoProvedor = Ext.decode(response.responseText).params.VALIDA_RES_TIPO_PROV;
                           	                
                           	                if(respuestaValidacionResEst == 'N'){
                           	                	var mensajeEstudios = 'Capture almenos un estudio de conceptos de LABORATORIO.';

                           	                	var mensajeEstudiosObligMedico = ''; 
                           	                	var mensajeEstudiosObligLab    = '';
                           	                	
                           	                	estudiosCobAsegStore.each(function(record) {
                           	                     	if( !Ext.isEmpty(record.get('CDTIPO')) && _TIPO_PROVEEDOR_MEDICO == record.get('CDTIPO')
                           	                     			&& !Ext.isEmpty(record.get('SWOBLEST')) && record.get('SWOBLEST') == "S"
                           	                     			&& Ext.isEmpty(record.get('CDRESEST'))  && Ext.isEmpty(record.get('VALOR'))){
                           	                     		mensajeEstudiosObligMedico += (record.get('DSEST')+", ");
                           	                     		
                           	                     	}else if( !Ext.isEmpty(record.get('CDTIPO')) && _TIPO_PROVEEDOR_LAB == record.get('CDTIPO')
                           	                     			&& !Ext.isEmpty(record.get('SWOBLEST')) && record.get('SWOBLEST') == "S"
                           	                     			&& Ext.isEmpty(record.get('CDRESEST'))  && Ext.isEmpty(record.get('VALOR'))){
                           	                     		mensajeEstudiosObligMedico += (record.get('DSEST')+", ");
                           	                     	}
                           	                     });
                           	                 
                           	                	if(respuestaTipoProvedor == _TIPO_PROVEEDOR_MEDICO){
                           	                		
                               	                	mensajeEstudios = 'Capture los estudios de: '+ mensajeEstudiosObligMedico;
                               	                }
                           	                	mensajeWarning('Datos Guardados. Aun faltan capturar estudios para este asegurado.<br/>' + mensajeEstudios);
                           	                }else{
                           	                	windowResultEstudios.close();
                           	                	mensajeCorrecto('Aviso','Se ha guardado correctamente.');
                           	                }
                           	            }
                           	        },
                           	        failure : function () {
                           	            Ext.Msg.show({
                           	                title:'Error',
                           	                msg: 'Error de comunicaci&oacute;n',
                           	                buttons: Ext.Msg.OK,
                           	                icon: Ext.Msg.ERROR
                           	            });
                           	        }
                           	    });	
//                            });
                        }else{
                            mensajeError(json.mensaje);
                        }
                    }
                    ,failure  : function(response, options){
                   	 maskGuarda.close();
                        var json = Ext.decode(response.responseText);
                        mensajeError(json.mensaje);
                    }
                });
            
                
            }
        }]
    });
		
	
	function rendererColumnUsandoEditorDep(value, record, columnIndex, grid){
		var nuevoValor = value;
//		debug('valor en render',value);
		var llave =  (Ext.isEmpty(record.get('CDTABRES'))? '' : record.get('CDTABRES')) + '-' + value;
		//alert(llave);
		var existe = bufferTiposResultadoEstudios.containsKey(llave);
		
		if(existe){
			nuevoValor=bufferTiposResultadoEstudios.get(llave);
        }else{
        	nuevoValor = '';
        }
		
		return nuevoValor;
	}

	estudiosCobAsegStore.load({
		params: {
			'params.cdunieco'  : _cdunieco,
			'params.cdramo'    : _cdramo,
			'params.aaapertu'  : _aaapertu,
			'params.status'    : _status,
			'params.nmsiniest' : _nmsinies,
			'params.nmsituac'  : _nmsituac,
			'params.cdtipsit'  : _cdtipsit,
			'params.cdgarant'  : _cdgarant,
			'params.cdconval'  : _cdconval
		}
	});
	
	/*///////////////////*/
	////// contenido //////
	///////////////////////
	
	windowResultEstudios = Ext.create('Ext.window.Window',{
		title: 'Consulta y actualizaci&oacute;n de Datos Informativos para estudios m&eacute;dicos'
		,modal  : true
		,width  : 900  
		,items : [resultadosEstudiosGrid]
	}).show();
	
	centrarVentanaInterna(windowResultEstudios);
	
}