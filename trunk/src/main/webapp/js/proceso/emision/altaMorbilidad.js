Ext.require([ 'Ext.form.*', 'Ext.data.*', 'Ext.chart.*', 'Ext.grid.Panel','Ext.layout.container.Column', 'Ext.selection.CheckboxModel' ]);
Ext.onReady(function() {
	var panelProveedor;
	Ext.selection.CheckboxModel.override( {
		mode: 'SINGLE',
		allowDeselect: true
	});

	var panelInicialPral= Ext.create('Ext.form.Panel',{
		border    : 0
		,renderTo : 'div_clau'
		,items    : [
			Ext.create('Ext.form.Panel',{
				url          : _p21_urlSubirCensoMorbilidadArchivo
				,border      : 0
				,style       : 'margin:5px'
				,defaults : {
					style : 'margin:5px;'
				}
				,items : [
			        {    xtype       : 'textfield',         fieldLabel : 'Descripci&oacute;n Morbilidad'               ,name       : 'descMorbilidad',
                         maxLength  :20,                    width      : 450,       labelWidth : 150
                    },
                    {   xtype   : 'datefield',      width       : 450,          fieldLabel  : 'Fecha inicio vigencia', name        : 'dtFechaVigencia'    ,
                        value   : new Date(),       format      : 'd/m/Y',      allowBlank  : false,labelWidth : 150
                    },
                	{
                        xtype       : 'filefield'
                        ,fieldLabel : 'Archivo'
                        ,labelWidth : 150
                        ,width      : 450
                        ,buttonText : 'Examinar...'
                        ,buttonOnly : false
                        ,name       : 'censo'
                        ,allowBlank : false
                        ,msgTarget  : 'side'
                        ,cAccept    : ['xls','xlsx']
                        ,listeners  :
                        {
                            change : function(me)
                            {
                                var indexofPeriod = me.getValue().lastIndexOf("."),
                                uploadedExtension = me.getValue().substr(indexofPeriod + 1, me.getValue().length - indexofPeriod).toLowerCase();
                                if (!Ext.Array.contains(this.cAccept, uploadedExtension))
                                {
                                    centrarVentanaInterna(Ext.MessageBox.show(
                                    {
                                        title   : 'Error de tipo de archivo',
                                        msg     : 'Extensiones permitidas: ' + this.cAccept.join(),
                                        buttons : Ext.Msg.OK,
                                        icon    : Ext.Msg.WARNING
                                    }));
                                    me.reset();
                                }
                            }
                        }
                    }
				]
				,buttonAlign: 'center'
                ,buttons     :
                [
                    {
                        text     : 'Cargar archivo'
                        //,icon    : _CONTEXT+'/resources/fam3icons/icons/group_edit.png'
                        ,icon:_CONTEXT+'/resources/fam3icons/icons/group_edit.png'
                        ,handler : function(me){ 
                                    _p21_subirArchivoMorbilidad(me);
                        }
                    }
                ]
			})
		]
	});
	
function _p21_subirArchivoMorbilidad(button,nombreCensoParaConfirmar){
    debug('_p21_subirArchivoMorbilidad button:',button,'nombreCensoParaConfirmar:',nombreCensoParaConfirmar,'.');
    
    var form=button.up().up();
    var descMorbilidad  = form.down('[name=descMorbilidad]').getValue();
    debug("descMorbilidad ==>",descMorbilidad);
    var dtFechaVigencia = form.down('[name=dtFechaVigencia]').getValue();
    debug("dtFechaVigencia ==>",dtFechaVigencia);
    
    var valido=form.isValid();
    if(!valido)
    {
        datosIncompletos();
    }
    
    if(valido){
    	_mask('Espere un momento...');
        //Validamos si ya existe uno con el mismo nombre 
        Ext.Ajax.request( {
            url      : _p21_urlExisteMorbilidadExistente
            ,params  :
            {
                'params.morbilidad' : descMorbilidad
            }
            ,success : function(response)
            {
                var json = Ext.decode(response.responseText);
                
                if(json.respuesta > 0){
                    mensajeWarning('Ya existe la morbilidad '+descMorbilidad+" dado de alta.");
                    _unmask();
                }else{
                    valido = true;
                    if(valido)
                    {
                        form.setLoading(true);
                        var timestamp = new Date().getTime();
                        form.submit(
                        {
                            params   :
                            {
                                'smap1.timestamp' : timestamp
                                ,'smap1.ntramite' : ''
                            }
                            ,success : function()
                            {
                                if(!Ext.isEmpty(nombreCensoParaConfirmar))
                                {
                                    debug('se quita allowblank');
                                    form.down('filefield').allowBlank = false;
                                }
                            
                                var conceptos = {};
                                conceptos['timestamp']              = timestamp;
                                conceptos['descMorbilidad']         = descMorbilidad;
                                conceptos['dtFechaVigencia']        = dtFechaVigencia;
                                conceptos['nombreLayoutConfirmado'] = nombreCensoParaConfirmar;
                                var grupos = [];
                                
                                Ext.Ajax.request(
                                {
                                    url       : _p21_urlSubirCensoMorbilidad
                                    ,jsonData :
                                    {
                                        smap1   : conceptos
                                        ,olist1 : grupos
                                    }
                                    ,success  : function(response)
                                    {
                                        form.setLoading(false);
                                        var json=Ext.decode(response.responseText);
                                        debug('### subir censo completo response:',json);
                                        if(json.exito)
                                        {
                                           _unmask();
                                           centrarVentanaInterna(Ext.Msg.show({
                                                title    : 'Morbilidad'
                                                ,msg     : 'Se ha guardado la nueva morbilidad.'
                                                ,buttons : Ext.Msg.OK
                                                ,fn      : function()
                                                {
                                                	if(valorAction.morbilidadEx =='1'){
                                                		Ext.WindowMgr.getActive().close();
                                                	}else{
                                                		Ext.create('Ext.form.Panel').submit(
                                                    {
                                                        standardSubmit : true
                                                        ,url           : _p21_urlRefrescarPantalla
                                                    });
                                                	}
                                                    
                                                }
                                            }));
                                        }
                                         else
                                         {
                                         	 _unmask();
                                         	var mensajeRespuesta="";
                                         	var mensajeHoja   = json.respuesta+"";
                                         	var mensajeError  = json.smap1.erroresCenso+"";
                                         	if(mensajeHoja != 'undefined' && mensajeHoja != 'null'){
                                         		mensajeRespuesta= mensajeRespuesta+""+mensajeHoja;
                                         	}
                                         	if(mensajeError!= 'undefined'){
                                                mensajeRespuesta= mensajeRespuesta+""+mensajeError;
                                            }
                                         	
                                         	form.setLoading(false);
                                            centrarVentanaInterna(mensajeWarning('El archivo contiene errores de Datos.<br/>Favor de validarlo.',function(){
                                            centrarVentanaInterna(Ext.create('Ext.window.Window', {
                                            modal  : true
                                            ,title : 'Error'
                                            ,items : [
                                                {
                                                     xtype     : 'textarea'
                                                             ,width    : 700
                                                             ,height   : 400
                                                             ,readOnly : true
                                                             ,value    : mensajeRespuesta
                                                        }
                                                    ]
                                                }).show());
                                            }));
                                         }
                                     }
                                     ,failure  : function()
                                     {
                                     	 _unmask();
                                         form.setLoading(false);
                                         errorComunicacion();
                                     }
                                 });
                             }
                             ,failure : function()
                             {
                             	 _unmask();
                                 if(!Ext.isEmpty(nombreCensoParaConfirmar))
                                 {
                                     debug('se quita allowblank');
                                     form.down('filefield').allowBlank = false;
                                 }
                                 form.setLoading(false);
                                 errorComunicacion(null,'Error complementando datos');
                             }
                         });
                     }
                }
                
            }
            ,failure : function()
            {
                errorComunicacion(null,'Error en la validaci&oacute;n de existencia de Morbilidad ');
            }
        });
    }
}
});