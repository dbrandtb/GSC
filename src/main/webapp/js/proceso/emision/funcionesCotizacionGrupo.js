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
                                        grid.getStore().remove(record);
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