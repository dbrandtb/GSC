function _cotcol_editarDatosBaseAsegurado(grid,rowIndex)
{
    var record=grid.getStore().getAt(rowIndex);
    debug('>_cotcol_editarDatosBaseAsegurado:',record.data);
    
    _p47_callback = function()
    {
        _cotcol_aseguradosClic(grid.getStore().gridSubgrupo,grid.getStore().rowIndexSubgrupo);
    };
    
    var ventana = Ext.create('Ext.window.Window',
    {
        title        : 'Editar persona '+record.get('NOMBRE')
        ,itemId      : '_p47_contenedor' //se pone para que la ventana interna pueda cerrar la ventana
        ,width       : 860
        ,height      : 340
        ,modal       : true
        ,autoScroll  : true
        ,closeAction : 'destroy'
        ,loader      :
        {
            url       : _cotcol_urlPantallaEspPersona
            ,params   :
            {
                'params.cdperson'  : record.get('CDPERSON')
                ,'params.origen'   : 'cotcol'
                ,'params.cdunieco' : _cotcol_smap1.cdunieco
                ,'params.cdramo'   : _cotcol_smap1.cdramo
                ,'params.estado'   : _cotcol_smap1.estado
                ,'params.nmpoliza' : _cotcol_smap1.nmpoliza
                ,'params.nmsituac' : record.get('NMSITUAC')
                ,'params.cdtipsit' : _cotcol_smap1.cdtipsit
            }
            ,scripts  : true
            ,autoLoad : true
        }
    }).show()
    centrarVentanaInterna(ventana);
    
    debug('<_cotcol_editarDatosBaseAsegurado');
}

function _cotcol_mostrarVentanaActTvalosit(grid,rowIndex)
{
    var record=grid.getStore().getAt(rowIndex);
    debug('>_cotcol_mostrarVentanaActTvalosit:',record.data,'grid.getStore():',grid.getStore());
    
    _p82_callback = function()
    {
        _cotcol_aseguradosClic(grid.getStore().gridSubgrupo,grid.getStore().rowIndexSubgrupo);
    };
    
    var ventana = Ext.create('Ext.window.Window',
    {
        title        : 'Editar inciso '+record.get('NOMBRE')
        ,itemId      : '_p82_contenedor' //se pone para que la ventana interna pueda cerrar la ventana
        ,width       : 860
        ,height      : 340
        ,modal       : true
        ,autoScroll  : true
        ,closeAction : 'destroy'
        ,loader      :
        {
            url       : _cotcol_urlPantallaActTvalosit
            ,params   :
            {
                'params.origen'    : 'cotcol'
                ,'params.cdunieco' : _cotcol_smap1.cdunieco
                ,'params.cdramo'   : _cotcol_smap1.cdramo
                ,'params.estado'   : _cotcol_smap1.estado
                ,'params.nmpoliza' : _cotcol_smap1.nmpoliza
                ,'params.nmsituac' : record.get('NMSITUAC')
                ,'params.cdtipsit' : _cotcol_smap1.cdtipsit
            }
            ,scripts  : true
            ,autoLoad : true
        }
    }).show()
    centrarVentanaInterna(ventana);
    
    debug('<_cotcol_mostrarVentanaActTvalosit');
}

function _p21_desbloqueoBotonRol(boton)
{
    var ck = 'Recuperando permisos de bot\u00f3n';
    try
    {
        boton.setLoading(true);
        Ext.Ajax.request(
        {
            url      : _p21_urlRecuperacion
            ,params  :
            {
                'params.consulta' : 'RECUPERAR_PERMISO_BOTON_GENERAR_COLECTIVO'
            }
            ,success : function(response)
            {
                boton.setLoading(false);
                var ck = 'Decodificando respuesta al recuperar permisos de bot\u00f3n';
                try
                {
                    var json = Ext.decode(response.responseText);
                    debug('### permisos boton:',json);
                    if(json.success==true)
                    {
                         if('S'==json.params.ACTIVAR_BOTON)
                         {
                             boton.show();
                             boton.enable();
                         }
                         else
                         {
                             mensajeWarning('Favor de revisar los errores de la carga');
                             boton.disable();
                             boton.hide();
                         }
                    }
                    else
                    {
                        mensajeError(json.message);
                    }
                }
                catch(e)
                {
                    manejaException(e,ck);
                }
            }
            ,failure : function()
            {
                boton.setLoading(false);
                errorComunicacion(null,'Error al recuperar permisos de bot\u00f3n');
            }
        });
    }
    catch(e)
    {
        manejaException(e,ck);
    }
}

function _cotcol_quitarAsegurado(grid,rowIndex)
{
    var record = grid.getStore().getAt(rowIndex);
    debug('>_cotcol_quitarAsegurado:',record.data,'grid.getStore():',grid.getStore());
    
    centrarVentanaInterna(Ext.MessageBox.confirm('Confirmar', 'Se borrará el asegurado y no podr\u00e1 recuperarse<br>¿Desea continuar?', function(btn)
    {
        if(btn === 'yes')
        {
            var ck = 'Borrando asegurado';
            try
            {
                _mask(ck);
                Ext.Ajax.request(
                {
                    url      : _cotcol_urlBorrarAsegurado
    	            ,params  :
    	            {
    	                'params.cdunieco'  : _cotcol_smap1.cdunieco
    	                ,'params.cdramo'   : _cotcol_smap1.cdramo
    	                ,'params.estado'   : _cotcol_smap1.estado
    	                ,'params.nmpoliza' : _cotcol_smap1.nmpoliza
    	                ,'params.nmsituac' : record.get('NMSITUAC')
    	            }
                    ,success : function(response)
                    {
                        _unmask();
                        var ck = 'Decodificando respuesta al borrar asegurado';
                        try
                        {
                            var json = Ext.decode(response.responseText);
                            debug('### eliminar:',json);
                            if(json.success==true)
                            {
                                mensajeCorrecto(
                                    'Asegurado borrado'
                                    ,'El asegurado ha sido eliminado de la p\u00f3liza'
                                    ,function()
                                    {
                                        _cotcol_aseguradosClic(grid.getStore().gridSubgrupo,grid.getStore().rowIndexSubgrupo);
                                    }
                                );
                            }
                            else
                            {
                                mensajeError(json.message);
                            }
                        }
                        catch(e)
                        {
                            manejaException(e,ck);                        
                        }
                    }
                    ,failure : function()
                    {
                        _unmask();
                        errorComunicacion(null,'Error al borrar asegurado');
                    }
                });
            }
            catch(e)
            {
                manejaException(e,ck);
            }
        }
    }));
    
    debug('<_cotcol_quitarAsegurado');
}

function _p21_subirArchivoCompleto(button,nombreCensoParaConfirmar)
{
    debug('_p21_subirArchivoCompleto button:',button,'nombreCensoParaConfirmar:',nombreCensoParaConfirmar,'.');
    var form=button.up().up();
    
    if(!Ext.isEmpty(nombreCensoParaConfirmar))
    {
        debug('se pone allowblank');
        form.down('filefield').allowBlank = true;
    }
    
    var valido=form.isValid();
    if(!valido)
    {
        datosIncompletos();
    }
    
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
            
                var conceptos = _p21_tabConcepto().down('[xtype=form]').getValues();
                
                conceptos['timestamp']             = timestamp;
                conceptos['clasif']                = _p21_clasif;
                conceptos['LINEA_EXTENDIDA']       = _p21_smap1.LINEA_EXTENDIDA;
                conceptos['cdunieco']              = _p21_smap1.cdunieco;
                conceptos['cdramo']                = _p21_smap1.cdramo;
                conceptos['cdtipsit']              = _p21_smap1.cdtipsit;
                conceptos['ntramiteVacio']         = _p21_ntramiteVacio ? _p21_ntramiteVacio : '';
                conceptos['nombreCensoConfirmado'] = nombreCensoParaConfirmar;
                var grupos = [];
                _p21_storeGrupos.each(function(record)
                {
                    var grupo = record.data;
                    grupo['tvalogars']=record.tvalogars;
                    grupos.push(grupo);
                });
                Ext.Ajax.request(
                {
                    url       : _p21_urlSubirCensoCompleto
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
                        
                            //var callback = function() { _p21_turnar(19,'Observaciones de la carga',false); };
                            var callback = function() { mensajeCorrecto('Aviso','Se ha turnado el tr\u00e1mite a mesa de control en estatus ' +
                            							'"En Tarifa" para procesar el censo. Una vez terminado podra encontrar su tr\u00e1mite ' +
                            							'en estatus "Carga completa".',function(){ _p21_mesacontrol(); } 
                            							);
                            						  };
                            
                            
                            if(Ext.isEmpty(nombreCensoParaConfirmar))
                            {
                                mensajeCorrecto('Datos guardados','Datos guardados<br/>Para revisar los datos presiona aceptar'
                                ,function()
                                {
                                    var ck = 'Recuperando asegurados para revision';
                                    try
                                    {
                                        _p21_tabpanel().setLoading(true);
                                        Ext.Ajax.request(
                                        {
                                            url      : _p21_urlRecuperacionSimpleLista
                                            ,params  :
                                            {
                                                'smap1.procedimiento' : 'RECUPERAR_REVISION_COLECTIVOS_FINAL'
                                                ,'smap1.cdunieco'     : _p21_smap1.cdunieco
                                                ,'smap1.cdramo'       : _p21_smap1.cdramo
                                                ,'smap1.estado'       : 'W'
                                                ,'smap1.nmpoliza'     : json.smap1.nmpoliza
                                            }
                                            ,success : function(response)
                                            {
                                                var ck = 'Decodificando datos de asegurados para revision';
                                                try
                                                {
                                                    _p21_tabpanel().setLoading(false);
                                                    var json2 = Ext.decode(response.responseText);
                                                    debug('### asegurados:',json2);
                                                    
                                                    var ngrupos = 0;
                                                    
                                                    var tabgrupos = _p21_tabGrupos; //Obtiene los grupos de Resumen Subgrupos
                                                    debug('tabgrupos cotizacion1',tabgrupos,'.');
                                                    
                                                    ngrupos = tabgrupos.items.items.items[0].store.getCount();
                                                    debug('ngrupos ',ngrupos,'.'); //Numero de Grupos en Resumen Subgrupos
                                                    
                                                    var gruposValidos = [];
                                                    for(var i=0; i < ngrupos;i++){
                                                    	gruposValidos[i+1]=false;
                                                    }
                                                    debug('gruposValidos',gruposValidos,'.');
                                                    
                                                    for(var i=0; i<json2.slist1.length;i++){
                                                    	debug('Gpo. ',json2.slist1[i].CDGRUPO);
                                                    	gruposValidos[Number(json2.slist1[i].CDGRUPO)]=true;//De los grupos asigna true al array cuando este disponible.
                                                    }
                                                    debug('iteracion completa gruposValidos',gruposValidos,'.');
                                                    
                                                    var enableBoton =true;
                                                    for(var i=0; i<ngrupos;i++){
                                                    	debug('boton ->',gruposValidos[i+1],'.');
                                                    	if(gruposValidos[i+1]===false){//Segun el valor en el array, se sabe si el boton esta o no habilitado.	
                                                    		enableBoton = false;
                                                    		break;
                                                    	}
                                                    }
                                                    debug('enableBoton ',enableBoton,'.');
                                                    
                                                    var store = Ext.create('Ext.data.Store',
                                                    {
                                                        model : '_p21_modeloRevisionAsegurado'
                                                        ,data : json2.slist1
                                                    });
                                                    debug('store.getRange():',store.getRange());
                                                    centrarVentanaInterna(Ext.create('Ext.window.Window',
                                                    {
                                                        width   : 600
                                                        ,height : 500
                                                        ,title  : 'Revisar asegurados del censo'
                                                        ,closable : false
                                                        ,listeners :
                                                        {
                                                            afterrender : function()
                                                            {
                                                                if(json2.slist1.length==0)
                                                                {
                                                                    setTimeout(function(){mensajeError('No se registraron asegurados, favor de revisar a detalle los errores');},1000);
                                                                }
                                                            }
                                                        }
                                                        ,items  :
                                                        [
                                                            Ext.create('Ext.panel.Panel',
                                                            {
                                                                layout    : 'hbox'
                                                                ,border   : 0
                                                                ,defaults : { style : 'margin:5px;' }
                                                                ,height   : 40
                                                                ,items    :
                                                                [
                                                                    {
                                                                        xtype       : 'displayfield'
                                                                        ,fieldLabel : 'Filas leidas'
                                                                        ,value      : json.smap1.filasLeidas
                                                                    }
                                                                    ,{
                                                                        xtype       : 'displayfield'
                                                                        ,fieldLabel : 'Filas procesadas'
                                                                        ,value      : json.smap1.filasProcesadas
                                                                    }
                                                                    ,{
                                                                        xtype       : 'displayfield'
                                                                        ,fieldLabel : 'Filas con error'
                                                                        ,value      : json.smap1.filasErrores
                                                                    }
                                                                    ,{
                                                                        xtype    : 'button'
                                                                        ,text    : 'Ver errores'
                                                                        ,hidden  : Number(json.smap1.filasErrores)==0
                                                                        ,handler : function()
                                                                        {
                                                                            centrarVentanaInterna(Ext.create('Ext.window.Window',
                                                                            {
                                                                                modal        : true
                                                                                ,closeAction : 'destroy'
                                                                                ,title       : 'Errores al procesar censo'
                                                                                ,width       : 800
                                                                                ,height      : 500
                                                                                ,items       :
                                                                                [
                                                                                    {
                                                                                        xtype       : 'textarea'
                                                                                        ,fieldStyle : 'font-family: monospace'
                                                                                        ,value      : json.smap1.erroresCenso
                                                                                        ,readOnly   : true
                                                                                        ,width      : 780
                                                                                        ,height     : 440
                                                                                    }
                                                                                ]
                                                                            }).show());
                                                                        }
                                                                    }
                                                                ]
                                                            })
                                                            ,Ext.create('Ext.grid.Panel',
                                                            {
                                                                height   : 350
                                                                ,columns :
                                                                [
                                                                    {
                                                                        text       : 'Grupo'
                                                                        ,dataIndex : 'CDGRUPO'
                                                                        ,width     : 60
                                                                    }
                                                                    ,{
                                                                        text       : 'No.'
                                                                        ,dataIndex : 'NMSITUAC'
                                                                        ,width     : 40
                                                                    }
                                                                    ,{
                                                                        text       : 'Parentesco'
                                                                        ,dataIndex : 'PARENTESCO'
                                                                        ,width     : 120
                                                                    }
                                                                    ,{
                                                                        text       : 'Nombre'
                                                                        ,dataIndex : 'NOMBRE'
                                                                        ,width     : 200
                                                                    }
                                                                    ,{
                                                                        text       : 'Sexo'
                                                                        ,dataIndex : 'SEXO'
                                                                        ,width     : 80
                                                                    }
                                                                    ,{
                                                                        text       : 'Edad'
                                                                        ,dataIndex : 'EDAD'
                                                                        ,width     : 60
                                                                    }
                                                                ]
                                                                ,store : store
                                                            })
                                                        ]
                                                        ,buttonAlign : 'center'
                                                        ,buttons     :
                                                        [
                                                            {
                                                                text       : 'Aceptar y continuar'
                                                                ,icon      : _GLOBAL_CONTEXTO+'/resources/fam3icons/icons/accept.png'
                                                                ,disabled  : !enableBoton
                                                                ,handler   : function(me)
                                                                {
                                                                    var ck = 'Borrando respaldo';
                                                                    try
                                                                    {
                                                                        _mask(ck);
                                                                        Ext.Ajax.request(
                                                                        {
                                                                            url      : _p21_urlBorrarRespaldoCenso
                                                                            ,params  :
                                                                            {
                                                                                'smap1.cdunieco'  : _p21_smap1.cdunieco
                                                                                ,'smap1.cdramo'   : _p21_smap1.cdramo
                                                                                ,'smap1.nmpoliza' : _p21_smap1.nmpoliza
                                                                            }
                                                                            ,success : function(response)
                                                                            {
                                                                                _unmask();
                                                                                var ck = 'Decodificando respuesta al borrar respaldo';
                                                                                try
                                                                                {
                                                                                    var jsonBorr = Ext.decode(response.responseText);
                                                                                    debug('### borrar resp:',jsonBorr);
                                                                                    if(jsonBorr.success===true)
                                                                                    {
                                                                                        me.up('window').destroy();
                                                                                        _p21_subirArchivoCompleto(button,json.smap1.nombreCensoParaConfirmar);
                                                                                    }
                                                                                    else
                                                                                    {
                                                                                        mensajeError(jsonBorr.respuesta);
                                                                                    }
                                                                                }
                                                                                catch(e)
                                                                                {
                                                                                    manejaException(e,ck);
                                                                                }
                                                                            }
                                                                            ,failure : function()
                                                                            {
                                                                                _unmask();
                                                                                errorComunicacion(null,'Error borrando respaldo');
                                                                            }
                                                                        });
                                                                    }
                                                                    catch(e)
                                                                    {
                                                                        manejaException(e,ck);
                                                                    }
                                                                }
                                                            }
                                                            ,{
                                                                text     : 'Restaurar datos e intentar de nuevo'
                                                                ,icon    : _GLOBAL_CONTEXTO+'/resources/fam3icons/icons/pencil.png'
                                                                ,handler : function(me)
                                                                {
                                                                    var ck = 'Restaurando respaldo';
                                                                    try
                                                                    {
                                                                        _mask(ck);
                                                                        Ext.Ajax.request(
                                                                        {
                                                                            url      : _p21_urlRestaurarRespaldoCenso
                                                                            ,params  :
                                                                            {
                                                                                'smap1.cdunieco'  : _p21_smap1.cdunieco
                                                                                ,'smap1.cdramo'   : _p21_smap1.cdramo
                                                                                ,'smap1.estado'   : _p21_smap1.estado
                                                                                ,'smap1.nmpoliza' : _p21_smap1.nmpoliza
                                                                            }
                                                                            ,success : function(response)
                                                                            {
                                                                                _unmask();
                                                                                var ck = 'Decodificando respuesta al restaurar respaldo';
                                                                                try
                                                                                {
                                                                                    var jsonRest = Ext.decode(response.responseText);
                                                                                    debug('### restaurar:',jsonRest);
                                                                                    if(jsonRest.success===true)
                                                                                    {
                                                                                        me.up('window').destroy();
                                                                                        _p21_resubirCenso = 'S';
                                                                                    }
                                                                                    else
                                                                                    {
                                                                                        mensajeError(jsonRest.respuesta);
                                                                                    }
                                                                                }
                                                                                catch(e)
                                                                                {
                                                                                    manejaException(e,ck);
                                                                                }
                                                                            }
                                                                            ,failure : function()
                                                                            {
                                                                                _unmask();
                                                                                errorComunicacion(null,'Error al restaurar respaldo');
                                                                            }
                                                                        });
                                                                    }
                                                                    catch(e)
                                                                    {
                                                                        manejaException(e,ck);
                                                                    }
                                                                 }
                                                             }
                                                         ]
                                                     }).show());
                                                 }
                                                 catch(e)
                                                 {
                                                     manejaException(e,ck);
                                                 }
                                             }
                                             ,failure : function()
                                             {
                                                 _p21_tabpanel().setLoading(false);
                                                 errorComunicacion(ck);
                                             }
                                         });
                                     }
                                     catch(e)
                                     {
                                         manejaException(e,ck);
                                     }        
                                 });
                             }
                             else
                             {
                                 callback();
                             }
                         }
                         else
                         {
                             centrarVentanaInterna(Ext.create('Ext.window.Window',
                             {
                                 modal  : true
                                 ,title : 'Error'
                                 ,items :
                                 [
                                     {
                                         xtype     : 'textarea'
                                         ,width    : 700
                                         ,height   : 400
                                         ,readOnly : true
                                         ,value    : json.respuesta
                                     }
                                 ]
                             }).show());
                         }
                     }
                     ,failure  : function()
                     {
                         form.setLoading(false);
                         errorComunicacion();
                     }
                 });
             }
             ,failure : function()
             {
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

function _p21_subirArchivoCompletoEndoso(button,nombreCensoParaConfirmar)
{
    debug('_p21_subirArchivoCompleto button:',button,'nombreCensoParaConfirmar:',nombreCensoParaConfirmar,'.');
    var form=button.up().up();
    
    if(!Ext.isEmpty(nombreCensoParaConfirmar))
    {
        debug('se pone allowblank');
        form.down('filefield').allowBlank = true;
    }
    
    var valido=form.isValid();
    if(!valido)
    {
        datosIncompletos();
    }
    
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
            
                var conceptos = _p21_tabConcepto().down('[xtype=form]').getValues();
                
                debug("_p21_smap1 ===>",_p21_smap1);
                conceptos['timestamp']             = timestamp;
                conceptos['clasif']                = _p21_clasif;
                conceptos['LINEA_EXTENDIDA']       = _p21_smap1.LINEA_EXTENDIDA;
                conceptos['cdunieco']              = _p21_smap1.cdunieco;
                conceptos['cdramo']                = _p21_smap1.cdramo;
                conceptos['cdtipsit']              = _p21_smap1.cdtipsit;
                conceptos['ntramiteVacio']         = _p21_ntramiteVacio ? _p21_ntramiteVacio : '';
                conceptos['nombreCensoConfirmado'] = nombreCensoParaConfirmar;
                conceptos['estado']                = _p21_smap1.estado;
                conceptos['nmsuplem']              = _p21_smap1.nmsuplem;
                var grupos = [];
                _p21_storeGrupos.each(function(record)
                {
                    var grupo = record.data;
                    grupo['tvalogars']=record.tvalogars;
                    grupos.push(grupo);
                });
                Ext.Ajax.request(
                {
                    url       : _p21_urlSubirCensoCompleto
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
                        
                            var callback = function() {
                            	
                            	_p21_recarga(19,'Observaciones de la carga',false); 
                           	};
                            
                            if(Ext.isEmpty(nombreCensoParaConfirmar))
                            {
	                            mensajeCorrecto('Datos guardados','Datos guardados<br/>Para revisar los datos presiona aceptar'
	                            ,function()
	                            {
	                                var ck = 'Recuperando asegurados para revision';
	                                try
	                                {
	                                    _p21_tabpanel().setLoading(true);
	                                    Ext.Ajax.request(
	                                    {
	                                        url      : _p21_urlRecuperacionSimpleLista
	                                        ,params  :
	                                        {
	                                            'smap1.procedimiento' : 'RECUPERAR_REVISION_COLECTIVOS_ENDOSOS'
	                                            ,'smap1.cdunieco'     : _p21_smap1.cdunieco
	                                            ,'smap1.cdramo'       : _p21_smap1.cdramo
	                                            ,'smap1.estado'       : _p21_smap1.estado
	                                            ,'smap1.nmpoliza'     : json.smap1.nmpoliza
	                                            ,'smap1.nmsuplem'     : _p21_smap1.nmsuplem
	                                        }
	                                        ,success : function(response)
	                                        {
	                                            var ck = 'Decodificando datos de asegurados para revision';
	                                            try
	                                            {
	                                                _p21_tabpanel().setLoading(false);
	                                                var json2 = Ext.decode(response.responseText);
	                                                
	                                                debug('### asegurados:',json2);
	                                                
	                                                var ngrupos = 0;
	                                                
                                                    var tabgrupos = _p21_tabGrupos; //Obtiene los grupos de Resumen Subgrupos
                                                    debug('tabgrupos cotizacion endoso',tabgrupos,'.');
                                                    
                                                    ngrupos = tabgrupos.items.items.items[0].store.getCount();
                                                    debug('ngrupos ',ngrupos,'.'); //Numero de Grupos en Resumen Subgrupos
                                                    
                                                    
                                                    var gruposValidos = [];
                                                    for(var i=0; i < ngrupos;i++){
                                                    	gruposValidos[i+1]=false;
                                                    }
                                                    debug('gruposValidos',gruposValidos,'.');
                                                    
                                                    
                                                    for(var i=0; i<json2.slist1.length;i++){
                                                    	debug('GPO.',json2.slist1[i].CDGRUPO,'.');
                                                    	gruposValidos[Number(json2.slist1[i].CDGRUPO)]=true;//De los grupos asigna true al array cuando este disponible.
                                                    }
                                                    debug('iteracion completa gruposValidos',gruposValidos,'.');
                                                    
                                                    
                                                    var enableBoton = true;
                                                    for(var i=0; i<ngrupos;i++){
                                                    	debug('boton ->',gruposValidos[i+1],'.');
                                                    	if(gruposValidos[i+1]===false){//Segun el valor en el array, se sabe si el boton esta o no habilitado.	
                                                    		enableBoton = false;
                                                    		break;
                                                    	}
                                                    }
                                                    debug('EnableBoton',enableBoton);
	                                                
	                                                var store = Ext.create('Ext.data.Store',
	                                                {
	                                                    model : '_p21_modeloRevisionAsegurado'
	                                                    ,data : json2.slist1
	                                                });
	                                                debug('store.getRange():',store.getRange());
	                                                centrarVentanaInterna(Ext.create('Ext.window.Window',
	                                                {
	                                                    width   : 600
	                                                    ,height : 500
	                                                    ,title  : 'Revisar asegurados del censo'
	                                                    ,closable : false
                                                        ,listeners :
                                                        {
                                                            afterrender : function()
                                                            {
                                                                if(json2.slist1.length==0)
                                                                {
                                                                    setTimeout(function(){mensajeError('No se registraron asegurados, favor de revisar a detalle los errores');},1000);
                                                                }
                                                            }
                                                        }
	                                                    ,items  :
	                                                    [
	                                                        Ext.create('Ext.panel.Panel',
	                                                        {
	                                                            layout    : 'hbox'
	                                                            ,border   : 0
	                                                            ,defaults : { style : 'margin:5px;' }
	                                                            ,height   : 40
	                                                            ,items    :
	                                                            [
	                                                                {
	                                                                    xtype       : 'displayfield'
	                                                                    ,fieldLabel : 'Filas leidas'
	                                                                    ,value      : json.smap1.filasLeidas
	                                                                }
	                                                                ,{
	                                                                    xtype       : 'displayfield'
	                                                                    ,fieldLabel : 'Filas procesadas'
	                                                                    ,value      : json.smap1.filasProcesadas
	                                                                }
	                                                                ,{
	                                                                    xtype       : 'displayfield'
	                                                                    ,fieldLabel : 'Filas con error'
	                                                                    ,value      : json.smap1.filasErrores
	                                                                }
	                                                                ,{
	                                                                    xtype    : 'button'
	                                                                    ,text    : 'Ver errores'
	                                                                    ,hidden  : Number(json.smap1.filasErrores)==0
	                                                                    ,handler : function()
	                                                                    {
	                                                                        centrarVentanaInterna(Ext.create('Ext.window.Window',
	                                                                        {
	                                                                            modal        : true
	                                                                            ,closeAction : 'destroy'
	                                                                            ,title       : 'Errores al procesar censo'
	                                                                            ,width       : 800
	                                                                            ,height      : 500
	                                                                            ,items       :
	                                                                            [
	                                                                                {
	                                                                                    xtype       : 'textarea'
	                                                                                    ,fieldStyle : 'font-family: monospace'
	                                                                                    ,value      : json.smap1.erroresCenso
	                                                                                    ,readOnly   : true
	                                                                                    ,width      : 780
	                                                                                    ,height     : 440
	                                                                                }
	                                                                            ]
	                                                                        }).show());
	                                                                    }
	                                                                }
	                                                            ]
	                                                        })
	                                                        ,Ext.create('Ext.grid.Panel',
	                                                        {
	                                                            height   : 350
	                                                            ,columns :
	                                                            [
	                                                                {
	                                                                    text       : 'Grupo'
	                                                                    ,dataIndex : 'CDGRUPO'
	                                                                    ,width     : 60
	                                                                }
	                                                                ,{
	                                                                    text       : 'No.'
	                                                                    ,dataIndex : 'NMSITUAC'
	                                                                    ,width     : 40
	                                                                }
	                                                                ,{
	                                                                    text       : 'Parentesco'
	                                                                    ,dataIndex : 'PARENTESCO'
	                                                                    ,width     : 120
	                                                                }
	                                                                ,{
	                                                                    text       : 'Nombre'
	                                                                    ,dataIndex : 'NOMBRE'
	                                                                    ,width     : 200
	                                                                }
	                                                                ,{
	                                                                    text       : 'Sexo'
	                                                                    ,dataIndex : 'SEXO'
	                                                                    ,width     : 80
	                                                                }
	                                                                ,{
	                                                                    text       : 'Edad'
	                                                                    ,dataIndex : 'EDAD'
	                                                                    ,width     : 60
	                                                                }
	                                                            ]
	                                                            ,store : store
	                                                        })
	                                                    ]
	                                                    ,buttonAlign : 'center'
                                                        ,buttons     :
                                                        [
                                                            {
                                                                text       : 'Aceptar y continuar'
                                                                ,icon      : _GLOBAL_CONTEXTO+'/resources/fam3icons/icons/accept.png'
                                                                //,disabled  : !enableBoton
                                                                ,handler   : function(me)
                                                                {
                                                                    var ck = 'Borrando respaldo';
                                                                    try
                                                                    {
                                                                        _mask(ck);
                                                                        Ext.Ajax.request(
                                                                        {
                                                                            url      : _p21_urlBorrarRespaldoCenso
                                                                            ,params  :
                                                                            {
                                                                                'smap1.cdunieco'  : _p21_smap1.cdunieco
                                                                                ,'smap1.cdramo'   : _p21_smap1.cdramo
                                                                                ,'smap1.nmpoliza' : _p21_smap1.nmpoliza
                                                                            }
                                                                            ,success : function(response)
                                                                            {
                                                                                _unmask();
                                                                                var ck = 'Decodificando respuesta al borrar respaldo';
                                                                                try
                                                                                {
                                                                                    var jsonBorr = Ext.decode(response.responseText);
                                                                                    debug('### borrar resp:',jsonBorr);
                                                                                    if(jsonBorr.success===true)
                                                                                    {
                                                                                        me.up('window').destroy();
                                                                                        _p21_subirArchivoCompletoEndoso(button,json.smap1.nombreCensoParaConfirmar);
                                                                                    }
                                                                                    else
                                                                                    {
                                                                                        mensajeError(jsonBorr.respuesta);
                                                                                    }
                                                                                }
                                                                                catch(e)
                                                                                {
                                                                                    manejaException(e,ck);
                                                                                }
                                                                            }
                                                                            ,failure : function()
                                                                            {
                                                                                _unmask();
                                                                                errorComunicacion(null,'Error borrando respaldo');
                                                                            }
                                                                        });
                                                                    }
                                                                    catch(e)
                                                                    {
                                                                        manejaException(e,ck);
                                                                    }
                                                                }
                                                            }
                                                            ,{
                                                                text     : 'Restaurar datos e intentar de nuevo'
                                                                ,icon    : _GLOBAL_CONTEXTO+'/resources/fam3icons/icons/pencil.png'
                                                                ,handler : function(me)
                                                                {
                                                                	me.up('window').destroy();
                                                                    _p21_resubirCenso = 'S';
                                                                	
                                                                	/*var ck = 'Restaurando respaldo';
                                                                    try
                                                                    {
                                                                        _mask(ck);
                                                                        Ext.Ajax.request(
                                                                        {
                                                                            url      : _p21_urlRestaurarRespaldoCenso
                                                                            ,params  :
                                                                            {
                                                                                'smap1.cdunieco'  : _p21_smap1.cdunieco
                                                                                ,'smap1.cdramo'   : _p21_smap1.cdramo
                                                                                ,'smap1.estado'   : _p21_smap1.estado
                                                                                ,'smap1.nmpoliza' : _p21_smap1.nmpoliza
                                                                            }
                                                                            ,success : function(response)
                                                                            {
                                                                                _unmask();
                                                                                var ck = 'Decodificando respuesta al restaurar respaldo';
                                                                                try
                                                                                {
                                                                                    var jsonRest = Ext.decode(response.responseText);
                                                                                    debug('### restaurar:',jsonRest);
                                                                                    if(jsonRest.success===true)
                                                                                    {
                                                                                        
                                                                                    }
                                                                                    else
                                                                                    {
                                                                                        mensajeError(jsonRest.respuesta);
                                                                                    }
                                                                                }
                                                                                catch(e)
                                                                                {
                                                                                    manejaException(e,ck);
                                                                                }
                                                                            }
                                                                            ,failure : function()
                                                                            {
                                                                                _unmask();
                                                                                errorComunicacion(null,'Error al restaurar respaldo');
                                                                            }
                                                                        });
                                                                    }
                                                                    catch(e)
                                                                    {
                                                                        manejaException(e,ck);
                                                                    }*/
                                                                 }
                                                             }
                                                         ]
                                                     }).show());
                                                 }
                                                 catch(e)
                                                 {
                                                     manejaException(e,ck);
                                                 }
                                             }
                                             ,failure : function()
                                             {
                                                 _p21_tabpanel().setLoading(false);
                                                 errorComunicacion(ck);
                                             }
                                         });
                                     }
                                     catch(e)
                                     {
                                         manejaException(e,ck);
                                     }        
                                 });
                             }
                             else
                             {
                                 callback();
                             }

                         }
                         else
                         {
                             centrarVentanaInterna(Ext.create('Ext.window.Window',
                             {
                                 modal  : true
                                 ,title : 'Error'
                                 ,items :
                                 [
                                     {
                                         xtype     : 'textarea'
                                         ,width    : 700
                                         ,height   : 400
                                         ,readOnly : true
                                         ,value    : json.respuesta
                                     }
                                 ]
                             }).show());
                         }
                     }
                     ,failure  : function()
                     {
                         form.setLoading(false);
                         errorComunicacion();
                     }
                 });
             }
             ,failure : function()
             {
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

function  checkEdit(editor, context, eOpts) {
	debug('entrando a checkEdit');
	debug('context',context.newValues);
	var gras = _fieldById('gridAseg'+editor.cdgrupo);
	gras.getSelectionModel().select(context.record, false);
	if(!compareObjects(context.newValues, context.originalValues)){
		gras.getSelectionModel().select(context.record, true);
	}
	_fieldById('btnguardar'+this.cdgrupo).enable();
//	else{
//		gras.getSelectionModel().select(context.record, false);	
//	}
//	for(var i in gras.getPlugin('pagingselect'+letra).selected){
//		debug('i',i);
//	}
//	debug('selecteds',gras.getPlugin('pagingselect'+letra).selected);
}
/* se agregan listener para que el plugin
 * pagingselectpersist y ROWEDITING funcionen
 * juntos
 */
function  beforedesel(sm, record) {
	debug('entrando a beforedesel ');
	return !_fieldById('gridAseg'+sm.cdgrupo).getPlugin('rowedit'+sm.cdgrupo);
}

function beforeed(editor, context) {
	debug('entrando a beforeed');
	_fieldById('btnguardar'+this.cdgrupo).disable();
    return context.colIdx !== 0;
}
/* terminan funciones para pagingselectpersist
 * y rowediting
 */
/*
 * Funcion que compara objetos pero excluye
 * campos de check
 */
function compareObjects(obj1, obj2){
	console.log('entrando a compareObjects');
	var keys1 = [],
	keys2     = [],
	isEqual   = true;
	//Se obtienen keys de objetos
	for(var k in obj1){
		//Se excluyen los campos que contengan check
		debug('llave obj1',k);		
		if(!k.includes('displayfield')){
			keys1.push(k);
		}
	}
	for(var k in obj2){
		//Se excluyen los campos que contengan check
		if(!k.includes('displayfield')){
			keys2.push(k);			
		}
	}
	//Se compara si tiene las mismas llaves
	if (keys1.length != keys2.length || keys1.length === 0 || keys2.length === 0){
		console.log('objectos no validos');
		isEqual = false;
	}
	else{
		//Se busca si tiene contenido diferente
		for (var i in keys1){
			console.log('key',keys1[i]);
			console.log('obj1',obj1[keys1[i]]);
			console.log('obj2',obj2[keys2[i]]);
			if(obj1[keys1[i]] != obj2[keys2[i]]){
				isEqual = false;
				break;
			}
		}
	}
	console.log('termina compareObjects',isEqual);
	return isEqual;
}

function _p25_guardarExtraprimasTitulares(){
    debug('>_p25_guardarExtraprimas:');
 	_mask('Actualizado valores');
 	var selection = _fieldById('gridAseg'+this.grupo).getPlugin('pagingselect'+this.grupo).selection;
    for (var i = 0; i < selection.length; i++){
    	if (selection[i].data['parentesco'] === 'TITULAR'){
    		//debug('metiendo llave ',selection[i].data['parametros.pv_otvalor'+selection[i].data['parametros.cdatexoc']);
    		selection[i].data['parametros.pv_otvalor'+selection[i].data['cdatexoc']] = this.up('grid').down('[name=extrtitu]').value;
    		debug('metiendo llave ',selection[i].data);
    	}
    }
    Ext.Ajax.request(
    		{
    	url       : _p25_urlGuardarSituacionesTitulares
    	,jsonData :
    	{
    		params  : {
    			cdunieco   : _p25_smap1.cdunieco
            	,cdramo    : _p25_smap1.cdramo
            	,estado    : _p25_smap1.estado
            	,nmpoliza  : _p25_smap1.nmpoliza
            	,nmsuplem  : '0'
            	,cdtipsit  : _p25_smap1.cdtipsit
            	,valor	   : this.up('grid').down('[name=extrtitu]').value
            	,cdgrupo   : this.grupo
            	}
    	}
    	,success  : function(response){
    		var json=Ext.decode(response.responseText);
            debug('respuesta del guardado de extraprimas:',json);
            if(json.exito){
            	_fieldById('gridAseg'+json.params.cdgrupo).getStore().reload();
                mensajeCorrecto('Datos guardados',json.respuesta);
                _unmask();
                //_mask('Actualizando pantalla');
                //window.location.reload();
            }
            else{
                mensajeError(json.respuesta);
                _unmask();
            }
        }
        ,failure  : function(){
            errorComunicacion();
            _unmask();
        }
    		});
}

function _p21_guardarExtraprimasTitulares(){
    debug('>_p21_guardarExtraprimas:');
 	_mask('Actualizado valores');
 	var selection = _fieldById('gridAseg'+this.grupo).getPlugin('pagingselect'+this.grupo).selection;
    var mapselection = {};
    for (var i = 0; i < selection.length; i++){
    	if (selection[i].data['PARENTESCO'] === 'T'){
    		selection[i].data['EXTPRI_OCUPACION'] = this.up('grid').down('[name=extrtitu]').value;
    		debug('metiendo llave ',selection[i].data);	
    	}
    }
    Ext.Ajax.request(
    		{
    	url       : _p21_urlGuardarSituacionesTitulares
    	,jsonData :
    	{
    		params  : {
    			cdunieco   : _p21_smap1.cdunieco
            	,cdramo    : _p21_smap1.cdramo
            	,estado    : _p21_smap1.estado
            	,nmpoliza  : _p21_smap1.nmpoliza
            	,nmsuplem  : '0'
            	,cdtipsit  : _p21_smap1.cdtipsit
            	,cdgrupo   : this.grupo
            	,valor	   : this.up('grid').down('[name=extrtitu]').value
            	}
    	}
    	,success  : function(response){
    		var json=Ext.decode(response.responseText);
            debug('respuesta del guardado de extraprimas:',json);
            if(json.exito){
            	_fieldById('gridAseg'+json.params.cdgrupo).getStore().reload();
                mensajeCorrecto('Datos guardados',json.respuesta);
                _unmask();
                //_mask('Actualizando pantalla');
                //window.location.reload();
            }
            else{
                mensajeError(json.respuesta);
                _unmask();
            }
        }
        ,failure  : function(){
            errorComunicacion();
            _unmask();
        }
    });
}

function _p25_guardarExtraprimas(letra)
{
    debug('>_p25_guardarExtraprimas:',letra);
    _mask('Guardando...');
    var records = [];
    var store = _fieldById('gridAseg'+letra).getStore();
    for(var i in _fieldById('gridAseg'+letra).getPlugin('pagingselect'+letra).selection){
    	records.push(_fieldById('gridAseg'+letra).getPlugin('pagingselect'+letra).selection[i].data);
    }
    debug('records',records);
    if(records.length==0)
    {
        mensajeWarning('No hay cambios');
        _unmask();
        _p25_setActiveResumen();
    }
    else
    {
    	var asegurados = [];
    	$.each(records,function(i,record)
    			{
    		var asegurado = { nmsituac  : record.nmsituac };
			for(var i = 1; i <= 99; i++){
				var valor = record['parametros.pv_otvalor'+(('00'+i).slice(-2))];
                debug('valor:',valor,'typeof valor:',typeof valor);
                if(typeof valor == 'object'){
                    valor = Ext.Date.format(valor,'d/m/Y');
                }
                if(valor == 'undefined'+(('00'+i).slice(-2))){
                	valor = '';
                } 
                asegurado['otvalor'+(('00'+i).slice(-2))] = valor;
            }
            asegurados.push(asegurado);
            debug('cdunieco:',_p25_smap1.cdunieco);
        	debug('cdunieco:',_p25_smap1.cdramo);
        	debug('cdunieco:',_p25_smap1.estado);
        	debug('cdunieco:',_p25_smap1.nmpoliza);
        	debug('nmsuplem:',0);
            debug('situaciones a guardar:',asegurados);
        });        
        Ext.Ajax.request(
        {
            url       : _p25_urlGuardarSituaciones
            ,jsonData :
            {
            	params  :
            	{
            		cdunieco   : _p25_smap1.cdunieco,
            		cdramo     : _p25_smap1.cdramo,
            		estado	   : _p25_smap1.estado,
            		nmpoliza   : _p25_smap1.nmpoliza,
            		nmsuplem   : 0,
            		cdtipsit   : _p25_smap1.cdtipsit,
            		guardarExt : 'S'
            	}
                ,slist1 : asegurados
            }
            ,success  : function(response)
            {
                var json = Ext.decode(response.responseText);
                debug('respuesta del guardado de extraprimas:',json);
                if(json.exito)
                {
                	_unmask();
                	mensajeCorrecto('Datos guardados',json.respuesta);   
                }
                else
                {
                	_unmask();
                    mensajeError(json.respuesta);
                }
            }
            ,failure  : function()
            {
            	_unmask();
                errorComunicacion();
            }
        });
    }
    debug('<_p25_guardarExtraprimas');
}

function _p21_guardarExtraprimas(letra)
{
    debug('>_p21_guardarExtraprimas:',letra);
    var records = [];
    var store = _fieldById('gridAseg'+letra).getStore();
    for(var i in _fieldById('gridAseg'+letra).getPlugin('pagingselect'+letra).selection){
    	records.push(_fieldById('gridAseg'+letra).getPlugin('pagingselect'+letra).selection[i].data);
    }
    debug('store a guardar:',store);
    debug('records a guardar:',records);
    if(records.length==0)
    {
        mensajeWarning('No hay cambios');
        _unmask();
        _p21_setActiveResumen();
    }
    else
    {
        var asegurados = [];
        $.each(records,function(i,record)
        {
            var asegurado =
            {
                nmsituac          : record['NMSITUAC']
                ,ocupacion        : record['OCUPACION']
                ,extpri_ocupacion : record['EXTPRI_OCUPACION']
                ,peso             : record['PESO']
                ,estatura         : record['ESTATURA']
                ,extpri_estatura  : record['EXTPRI_SOBREPESO']
                ,cdgrupo          : letra
            };
            asegurados.push(asegurado);
        });
        debug('asegurados ',asegurados);
        Ext.Ajax.request(
        {
            url       : _p21_urlGuardarExtraprimas
            ,jsonData :
            {
            	params  :
            	{
            		cdunieco   : _p21_smap1.cdunieco,
            		cdramo     : _p21_smap1.cdramo,
            		estado	   : _p21_smap1.estado,
            		nmpoliza   : _p21_smap1.nmpoliza,
            		nmsuplem   : 0,
            		cdtipsit   : _p21_smap1.cdtipsit,
            		guardarExt : 'S'
            	}
                ,slist1 : asegurados
            }
            ,success  : function(response)
            {
                var json=Ext.decode(response.responseText);
                debug('respuesta del guardado de extraprimas:',json);
                if(json.exito)
                {
                    mensajeCorrecto('Datos guardados',json.respuesta);
                }
                else
                {
                    mensajeError(json.respuesta);
                }
            }
            ,failure  : function()
            {
                errorComunicacion();
            }
        });
    }
    debug('<_p21_guardarExtraprimas');
}

function _cotcol_confirmarTurnado(callback)
{
    debug('>_cotcol_confirmarTurnado:');
    
    centrarVentanaInterna(Ext.MessageBox.confirm('Confirmar', 'No se guardar\u00e1n los cambios ni se generar\u00e1 tarifa <br>¿Desea continuar?', function(btn)
    {
        if(btn === 'yes')
        {
            var ck = 'Ejecutando turnado';
            try
            {
                callback();
            }
            catch(e)
            {
                manejaException(e,ck);
            }
        }
    }));
    
    debug('<_cotcol_quitarAsegurado');
}