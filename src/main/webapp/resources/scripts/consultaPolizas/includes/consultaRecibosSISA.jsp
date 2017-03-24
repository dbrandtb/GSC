<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<style>
.pagado-row .x-grid-cell { 
    background-color: #80FF00; 
    color: #900; 
}
 
.generado-no-row .x-grid-cell { 
    background-color: #81DAF5; 
    color: #900; 
}

.generado-row .x-grid-cell { 
    background-color: #F3F781; 
    color: #900; 
}

.cancelado-row .x-grid-cell { 
    background-color: #FA8258; 
    color: #900; 
}

.blanco-row .x-grid-cell { 
    background-color: #FFFFFF; 
    color: #900; 
}
</style>
<script>
//////urls //////
//////urls //////

//////variables //////
    var _p25_storeRecibos;
    var _URL_CONSULTA_RECIBOS        = '<s:url namespace="/general" action= "obtenerDatosRecibosSISA" />';
    var _URL_CONSOLIDA_RECIBOS       = '<s:url namespace="/general" action= "consolidarRecibos"       />';
    var _URL_DESCONSOLIDA_RECIBOS    = '<s:url namespace="/general" action= "desconsolidarRecibos"    />';
    var _URL_CONSULTA_DETALLE_RECIBO = '<s:url namespace="/general" action= "obtieneDetalleReciboSISA"/>';
    var _URL_OBTENCION_REPORTE       = '<s:url namespace="/general" action= "procesoObtencionReporte" />';
    var _URL_REPORTE_RECIBOS         = '<s:url namespace="/general" action= "procesoReporteRecibos" />';
    //var _URL_CONSULTA_DETALLE_RECIBO = '<s:url namespace="/general" action="obtieneDetalleRecibo"     />';
    //var _URL_CONSULTA_DETALLE_RECIBO = '<s:url namespace="/general" action= "obtieneDetalleRecibo"    />';
    var p_cdunieco                   = '<s:property                 value = "params.cdunieco"         />';
    var p_cdramo                     = '<s:property                 value = "params.cdramo"           />';
    var p_estado                     = '<s:property                 value = "params.estado"           />';
    var p_nmpoliza                   = '<s:property                 value = "params.nmpoliza"         />';
    var p_nmsuplem                   = '<s:property                 value = "params.nmsuplem"         />';
    var pRcb_wndDetalleRecibo;
    var winSimbologia;
//////variables //////

//////overrides //////
//////overrides //////

//////componentes dinamicos //////
    var itemsReciboFields        = [<s:property value="imap.itemsReciboFields"   escapeHtml="false" />];
    var itemsReciboColumns       = [<s:property value="imap.itemsReciboColumns"  escapeHtml="false" />];
    var itemsDetalleFields       = [<s:property value="imap.itemsDetalleFields"  escapeHtml="false" />];
    var itemsDetalleColumns      = [<s:property value="imap.itemsDetalleColumns" escapeHtml="false" />];
    
    /*itemsReciboColumns.push({
        xtype      : 'actioncolumn',
        itemId     : 'btnReciboDoc',
        icon       : '${ctx}/resources/fam3icons/icons/eye.png',
        tooltip    : 'Ver recibo',
        arrowAlign : 'bottom',
        handler    : function(view, rowIndex, colIndex, item, e, record){
            debug('Ver recibo');            
        }
    });*/
//////componentes dinamicos //////

Ext.onReady(function(){
    ////// modelos //////
    Ext.define('_p25_modeloRecibo',{
        extend     : 'Ext.data.Model',
        fields     : itemsReciboFields,
        idProperty : 'consecutivo'
    });
    
    Ext.define('_p25_modeloDetalleRecibo',{
        extend  : 'Ext.data.Model',
        fields  : itemsDetalleFields
    });
    ////// modelos //////
    
    ////// stores //////
    _p25_storeRecibos = Ext.create('Ext.data.Store',{
        model    : '_p25_modeloRecibo',
        autoLoad : false,
        proxy    :
        {
            type   : 'ajax',
            url    : _URL_CONSULTA_RECIBOS,
            reader :
            {
                type            : 'json',
                root            : 'loadList',
                messageProperty : 'respuesta',
                successProperty : 'success'
            }
        }
    });
    
    _p25_storeDetalleRecibo = Ext.create('Ext.data.Store',{
        model    : '_p25_modeloDetalleRecibo',
        proxy    : {
            type   : 'ajax',
            url    : _URL_CONSULTA_DETALLE_RECIBO,
            reader: {
                type: 'json',
                root: 'detallesRecibo'
            }
        }
    });
    ////// stores //////
    
    ////// componentes //////
    ////// componentes //////
    
    ////// contenido //////
    Ext.create('Ext.panel.Panel',{
        //title    : 'Panel principal',
        panelPri : 'S',
        renderTo : 'dvRecibos',
        defaults : {
            style : 'margin:5px;'
        },
        items    : [
            Ext.create('Ext.panel.Panel',{
                title      : 'Resultados',
                items      : [
                    Ext.create('Ext.grid.Panel',{
                        itemId     : 'gridRecibos',
                        height     : 300,
                        width      : 800,
                        store      : _p25_storeRecibos,
                        columns    : itemsReciboColumns,
                        viewConfig : { 
                            stripeRows: false,
                            getRowClass: function(record) {
                                var style;
                                debug('EstadoRecibo.Pendiente',EstadoRecibo.Pendiente);
                                debug(record.data['status']);
                                if (EstadoRecibo.Pendiente === record.data['status']) {
                                    var feactual = new Date();                                  
                                    var feinicio = record.data['feinicio'];
                                    if(feactual > feinicio){
                                        style = 'generado-no-row';
                                    }
                                    else{
                                        style = 'blanco-row';                                        
                                    }
                                } 
                                else if (EstadoRecibo.Cancelado === record.data['status']) {
                                    style = 'cancelado-row';
                                }
                                else if (EstadoRecibo.Pagado === record.data['status']) {
                                    style = 'pagado-row';
                                }
                                else if (EstadoRecibo.Devuelto === record.data['status']) {
                                    style = 'generado-row';
                                }
                                return style;
                            }
                        },
                        selModel : Ext.create('Ext.selection.CheckboxModel', {
                            mode                : 'MULTI',
                            showHeaderCheckbox  : true
                        }),
                        tbar    : [
                            {
                                xtype    : 'button',
                                itemId   : 'btnDetalle',
                                text     : 'Ver detalle',
                                disabled : true,
                                handler  : function(me){
                                    debug(me.up('grid').getSelectionModel().getSelection());
                                    var gridRecibos = _fieldById('gridRecibos');
                                    var datos = gridRecibos.getSelectionModel().getSelection()[0].data;
                                    _p25_storeDetalleRecibo.load({
                                        params: {
                                            'params.cdunieco': p_cdunieco,
                                            'params.cdramo'  : p_cdramo,
                                            'params.estado'  : p_estado,
                                            'params.nmpoliza': p_nmpoliza,
                                            'params.nmrecibo': datos['nmrecibo'],
                                            'params.nmfolcon': datos['folio']
                                        },
                                        callback: function(records, operation, success){
                                            if(success){
                                                pRcb_wndDetalleRecibo.show();
                                            }
                                            else{
                                                showMessage('Error', 'Error al obtener los datos, intente m\u00E1s tarde', Ext.Msg.OK, Ext.Msg.ERROR);
                                            }                    
                                        }
                                    });
                                }
                            },
                            {
                                xtype    : 'button', 
                                itemId   : 'btnConsolidar',
                                text     : 'Consolidar',
                                disabled : true,
                                handler  : function(){
                                    var gridRecibos   = _fieldById('gridRecibos');                                    
                                    consolidarRecibos(obtenerDataSelected(gridRecibos));
                                    _p25_storeRecibos.reload();
                                }
                            },
                            {
                                xtype    : 'button', 
                                itemId   : 'btnDesconsolidar',
                                text     : 'Desconsolidar',
                                disabled : true,
                                handler  : function(){
                                    var gridRecibos   = _fieldById('gridRecibos');
                                    desconsolidarRecibos(obtenerDataSelected(gridRecibos));
                                    _p25_storeRecibos.reload();
                                }
                            },
                            {
                                xtype    : 'button',
                                itemId   : 'btnReporte',
                                text     : 'Reporte',
                                handler  : function(){
                                    Ext.create('Ext.form.Panel').submit({
                                        url             : _URL_REPORTE_RECIBOS
                                        ,standardSubmit : true
                                        ,target         : '_blank'
                                        ,params         : {
                                            'params.cdunieco' : p_cdunieco,
                                            'params.cdramo'   : p_cdramo,
                                            'params.estado'   : p_estado,
                                            'params.nmpoliza' : p_nmpoliza
                                        }
                                    });
                                }
                            },
                            {
                                xtype    : 'button',
                                itemId   : 'btnDesglose',
                                text     : 'Desglose',
                                disabled : true,
                                handler  : function(){
                                    var lista = obtenerDataSelected(_fieldById('gridRecibos'));
                                    debug('lista',lista);
                                    var arrRec = [];
                                    for(var i = 0; i < lista.length; i++){
                                        arrRec[i] = lista[i]['nmrecibo'];
                                    }
                                    debug('arrRec',arrRec);
                                    Ext.create('Ext.form.Panel').submit({
                                        url             : _URL_OBTENCION_REPORTE
                                        ,standardSubmit : true
                                        ,target         : '_blank'
                                        ,params         : {
                                            'params.cdunieco' : p_cdunieco,
                                            'params.cdramo'   : p_cdramo,
                                            'params.estado'   : p_estado,
                                            'params.nmpoliza' : p_nmpoliza,
                                            'arrRec'          : arrRec
                                        }
                                    });
                                }
                            },
                            {
                                xtype    : 'button', 
                                itemId   : 'btnSimbologia',
                                text     : 'Simbologia',
                                disabled : false,
                                handler  : function(){
                                    winSimbologia.show();
                                }
                            }
                        ]
                    })
                ]
            })
        ]
    });
    ////// contenido //////
    
    ////// custom //////
    _p25_storeRecibos.load({
        params: {
            'params.cdunieco' : p_cdunieco,
            'params.cdramo'   : p_cdramo,
            'params.estado'   : p_estado,
            'params.nmpoliza' : p_nmpoliza
        }
    });
    
    _p25_storeRecibos.on({
        load : function(me){
            if(me.data.length === 0){
                _habilitarBoton('btnReporte'   ,true);
            }
        }
    });
    
    _fieldById('gridRecibos').on({
       selectionchange : function(me, selected){
           debug(selected);
           var conso    = 0;
           var descon   = 0;
           var serie    = 0;
           var folio    = 0;
           deshabilitarBotones();
           if(selected.length > 0){
               debug('Antes de recorrer seleccionados');
               _habilitarBoton('btnDesglose'     ,true);
	           for(var i = 0; i < selected.length; i++){
	               if(i == 0){
                       serie = selected[i].data['codigo_serial'];  
                   } 
	               if(esConsolidado(selected[i].data)){
	                   conso++;
	               }
	               else{
	                   if(selected[i].data['codigo_serial'] === serie && selected[i].data['status'] === EstadoRecibo.Pendiente){
	                       descon++;
	                   }
	                   else{
	                       descon = 0;
	                       break;
	                   }
	               }
	               folio = selected[i].data['folio'];
	               serie = selected[i].data['codigo_serial'];
	           }
	           debug('termina de recorrer seleccionados',folio,serie);
	           debug('Antes de entrar en condiciones',conso, descon);
	           if(selected.length === 1){
	               _habilitarBoton('btnDetalle'      ,true);  //detalle
	           }
	           if(conso === 0 && descon > 1){
	               _habilitarBoton('btnConsolidar'   ,true);  //consolidar
	               _habilitarBoton('btnDesglose'     ,true);  //desglose
	           }
	           if(conso === 1 && descon === 0){
	               seleccionarConsolidados(folio);
	               _habilitarBoton('btnDesconsolidar',true);  //desconsolidar
	               _habilitarBoton('btnDesglose'     ,true);  //desglose
	           }
	           if(conso > 0 || descon > 0){
	               _habilitarBoton('btnDesglose'     ,true);  //desglose
	           }
           }
       } 
    });
    ////// custom //////
    
    ////// loaders //////
    ////// loaders //////
    ////// funciones //////
    function esConsolidado(data){
        debug('>esConsolidado', data);
        var consolidado = false;
        if(!Ext.isEmpty(data['folio'])){
            consolidado = true;
        }
        debug('<esConsolidado',consolidado);
        return consolidado;
    }
    
    function obtenerDataSelected(grid){
        debug('>obtenerDataSelected');
        var seleccionados = grid.getSelectionModel().getSelection();
        debug('grid',seleccionados);
        var listaRecibos = [];
        for(var i = 0; i < seleccionados.length; i++){
            var obj = seleccionados[i].data;
            obj['nmsuplem'] = seleccionados[i].raw['nmsuplem'];
            obj['ntramite'] = seleccionados[i].raw['ntramite'];
            listaRecibos.push(obj);
        }
        debug('listaRecibos',listaRecibos);
        debug('<obtenerDataSelected',listaRecibos);
        return listaRecibos;
    }
    
    function consolidarRecibos(listaRecibos){
        debug('>consolidarRecibos');
        var winMask = _maskLocal('Consolidando recibos');
        Ext.Ajax.request({
            url      : _URL_CONSOLIDA_RECIBOS,
            jsonData : {
                params    : {
                    'cdunieco' : p_cdunieco,
                    'cdramo'   : p_cdramo,
                    'estado'   : p_estado,
                    'nmpoliza' : p_nmpoliza
                },
                loadList  : listaRecibos
            },
            success  : function(response){
                debug('recibos consolidados con numero de folio');
                winMask.close();
            },
            failure  : function(){
                errorComunicacion();
                winMask.close();
            }
        });
        debug('<consolidarRecibos');
    }
    
    function desconsolidarRecibos(listaRecibos){
        debug('>consolidarRecibos');
        var winMask = _maskLocal('Desconsolidando recibos');        
        Ext.Ajax.request({
            url      : _URL_DESCONSOLIDA_RECIBOS,
            jsonData : {
                params    : {
                    'cdunieco' : p_cdunieco,
                    'cdramo'   : p_cdramo,
                    'estado'   : p_estado,
                    'nmpoliza' : p_nmpoliza
                },
                loadList  : listaRecibos
            },
            success  : function(response){
                debug('recibos consolidados con numero de folio');
                winMask.close();
            },
            failure  : function(){
                errorComunicacion();
                winMask.close();
            }
        });
        debug('<consolidarRecibos');
    }
    
    function seleccionarConsolidados(folio){
        debug('>seleccionarConsolidados',folio);
        gridRecibos = _fieldById('gridRecibos');
        try{
            for(var s in gridRecibos.store.data.items){
                var rec = gridRecibos.store.getAt(s).data;
                //debug('rec',rec);
                if(folio === rec['folio']){
                    debug('rec',rec);
                    //gridRecibos.getSelectionModel().deselectAll(true);
                    var modelRec = new _p25_modeloRecibo(rec);
                    debug('modelRec',modelRec);
                    gridRecibos.getSelectionModel().select(modelRec, true, true);
                }
            }
        }
        catch(err){
            debug("Error",err.message);
        }
        debug('<seleccionarConsolidados');
    }
    
    function deshabilitarBotones(){
        debug('>deshabilitarBotones');
        _habilitarBoton('btnConsolidar'   ,false);
        _habilitarBoton('btnDesconsolidar',false);
        _habilitarBoton('btnDetalle'      ,false);
        _habilitarBoton('btnDesglose'     ,false);
        debug('<deshabilitarBotones');
    }
    ////// funciones //////
    
    pRcb_wndDetalleRecibo = Ext.create('Ext.window.Window', {
        title       : 'Detalle de recibo',
        width       : 430,
        modal       : true,
        closeAction : 'hide',
        items       : [{
            xtype       : 'grid',
            store       : _p25_storeDetalleRecibo,
            height      : 285,
            autoScroll  : true,
            columns     : itemsDetalleColumns
        }],
        buttons: [{
            text    : 'Aceptar',
            handler : function(btn) {
                pRcb_wndDetalleRecibo.close();
            }
        }]
    });
    
    winSimbologia= Ext.create('Ext.window.Window',{
        title       : 'Simbologia',
        width       : 400,
        modal       : true,
        closeAction : 'hide',
        items       : [
            Ext.create('Ext.form.Panel', {
                bodyPadding : 15,
                defaults    : {
                    width      : 350,
                    readOnly   : true
                },
                items       : [
                    {
                        xtype       : 'textfield',
                        name        : 'fieldPagadoId',
                        fieldLabel  : 'Pagado',
                        labelWidth  : 160,
                        fieldStyle  : 'background-color: #80FF00; background-image: none; width:150px;'
                    },
                    {
                        xtype       : 'textfield',
                        name        : 'fieldGeneradoNoId',
                        fieldLabel  : 'Generado(No imp)',
                        labelWidth  : 160,
                        fieldStyle  : 'background-color: #81DAF5; background-image: none; width:150px;'
                    },
                    {
                        xtype       : 'textfield',
                        name        : 'fieldGeneradoId',
                        fieldLabel  : 'Generado',
                        labelWidth  : 160,
                        fieldStyle  : 'background-color: #F3F781; background-image: none; width:150px;'
                    },
                    {
                        xtype       : 'textfield',
                        name        : 'fieldCanceladoId',
                        fieldLabel  : 'Cancelado',
                        labelWidth  : 160,
                        fieldStyle  : 'background-color: #FA8258; background-image: none; width:150px;',
                    },
                    {
                        xtype       : 'textfield',
                        name        : 'fieldGeneradoNoId',
                        fieldLabel  : 'Generado(Fuera de vigencia)',
                        labelWidth  : 160,
                        fieldStyle  : 'background-color: #FFFFFF; background-image: none; width:150px;'
                    }
                ],
                buttons     : [{
                    text    : 'Aceptar',
                    handler : function(btn){
                        winSimbologia.close();
                    }
                }]
            })
        ]         
    });
});
</script>
</head>
<body>
<div id="dvRecibos" style="height:350px;"></div>
</body>
</html>