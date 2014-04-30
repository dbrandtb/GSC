<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>
    <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Coberturas</title>
        <script type="text/javascript">
            ////// variables //////
            // Obtenemos el contenido en formato JSON de la propiedad solicitada:
            var _selCobParams = <s:property value="%{convertToJSON('params')}" escapeHtml="false"/>;
            var _CONTEXT = '${ctx}';
            var _selCobForm;
            var gridFacturasTramite;
            var ventanaDetalleCobertura;
            var _selCobUrlSave                = '<s:url namespace="/siniestros" action="guardarSeleccionCobertura"/>';
            var _selCobUrlSavexTramite        = '<s:url namespace="/siniestros" action="guardarSeleccionCoberturaxTramite"/>';
            var _selCobUrlAvanza              = '<s:url namespace="/siniestros" action="afiliadosAfectados"/>';
            var _selCobUrlAvanzaReembolso     = '<s:url namespace="/siniestros" action="detalleSiniestro"/>';
            //var _selDetalleSiniestoDatos    = '<s:url namespace="/siniestros" action="detalleSiniestroDatos"/>';
            var _URL_LoadFacturasxTramite     = '<s:url namespace="/siniestros" action="obtenerFacturasTramite"/>';
            var _URL_guardarCoberturaxFactura = '<s:url namespace="/siniestros" action="guardarCoberturaxFactura"/>';
            var ntramite= null;
            var nfactura = null;
            var ffactura = null;
            var cdtipser = null;
            var cdpresta = null;
            var ptimport = null;
            var descporc = null;
            var descnume = null;
            var tasacamb = null;
            var ptimporta = null;
            var dctonuex = null;
            var cdmoneda = null;
            var tipoAccion = null;

            debug('_selCobParams:',_selCobParams);
            ////// variables //////
            Ext.onReady(function()
            {
                ////// modelos //////
                Ext.define('modeloFactura',
                {
                    extend  : 'Ext.data.Model'
                    ,fields : [ <s:property value="imap.modelFacturas" /> ]
                });
                ////// stores //////
                storeFacturasTramite = new Ext.data.Store(
                {
                    autoDestroy: true,
                    model: 'modeloFactura',
                    proxy: {
                        type: 'ajax',
                        url: _URL_LoadFacturasxTramite,
                        reader: {
                            type: 'json',
                            root: 'slist1'
                        }
                    }
                });
                ////// contenido //////
                _selCobForm = Ext.create('Ext.form.Panel',
                {
                    title        : 'Detalle de cobertura'
                    ,buttonAlign : 'center'
                    ,items       : [ <s:property value="imap.item" /> ]
                    ,listeners   : { afterrender : heredarPanel }
                    ,buttons     :
                        [
                            {
                                icon     : '${ctx}/resources/fam3icons/icons/disk.png'
                                ,text    : 'Guardar'
                                ,handler : _selCobGuardar
                                ,hidden	 : _selCobParams.tipopago == "1" ? false: true
                            }
                        ]
                });

                _selCobForm.items.items[2].on('blur',function()
                {
                    var comboCoberturas = _selCobForm.items.items[3];
                    comboCoberturas.getStore().load(
                    {
                        params :
                        {
                            'params.cdramo'  : _selCobForm.items.items[1].getValue()
                            ,'params.cdtipsit' : _selCobForm.items.items[2].getValue()
                            ,'params.ntramite'  : _selCobParams.ntramite
                            ,'params.tipopago'  :_selCobParams.tipopago
                        }
                    });
                });

                _selCobForm.items.items[3].on('focus',function()
                {
                    var comboCoberturas = _selCobForm.items.items[3];
                    comboCoberturas.getStore().load(
                    {
                        params :
                        {
                            'params.cdramo'  : _selCobForm.items.items[1].getValue()
                            ,'params.cdtipsit' : _selCobForm.items.items[2].getValue()
                            ,'params.ntramite'  : _selCobParams.ntramite
                            ,'params.tipopago'  :_selCobParams.tipopago
                        }
                    });
                });

                /*/////////////////DECLARACION DE GRID FACTURAS ///////////////*/
                Ext.define('EditorFacturasTramite', {
                    extend: 'Ext.grid.Panel',
                    requires: [
                        'Ext.selection.CellModel',
                        'Ext.grid.*',
                        'Ext.data.*',
                        'Ext.util.*',
                        'Ext.form.*'
                    ],
                    selType: 'checkboxmodel',
                    title: 'Facturas en Tr&aacute;mite',
                    frame: false,
                    initComponent: function(){
                        this.cellEditing = new Ext.grid.plugin.CellEditing({
                            clicksToEdit: 1
                        });
                        Ext.apply(this, {
                            height: 250,
                            plugins: [this.cellEditing],
                            store: storeFacturasTramite,
                            columns:[
                                        {
                                            xtype : 'actioncolumn',
                                            width : 50,
                                            sortable : false,
                                            menuDisabled : true,
                                            items : [{
                                                        icon : '${ctx}/resources/fam3icons/icons/pencil.png',
                                                        tooltip : 'Editar Factura',
                                                        scope : this,
                                                        handler : this.onEditClick
                                                    }]
                                        },
                                        <s:property value="imap.columnas" />
                                    ],
                            viewConfig :
                            {
                                listeners :
                                {
                                    refresh : function(dataview)
                                    {
                                        Ext.each(dataview.panel.columns, function(column)
                                        {
                                            column.autoSize();
                                        });
                                    }
                                }
                            }
                        });
                        this.callParent();
                    },
                    getColumnIndexes: function (){
                        var me, columnIndexes;
                        me = this;
                        columnIndexes = [];
                        Ext.Array.each(me.columns, function (column)
                        {
                            if (column.getEditor&&Ext.isDefined(column.getEditor())&&column.getEditor().allowBlank==false) {
                                columnIndexes.push(column.dataIndex);
                            } else {
                                columnIndexes.push(undefined);
                            }
                        });
                        return columnIndexes;
                    },
                    validateRow: function (columnIndexes,record, y)
                        //hace que una celda de columna con allowblank=false tenga el estilo rojito
                    {
                        var view = this.getView();
                        Ext.each(columnIndexes, function (columnIndex, x)
                        {
                            if(columnIndex)
                            {
                                var cell=view.getCellByPosition({row: y, column: x});
                                cellValue=record.get(columnIndex);
                                if(cell.addCls&&((!cellValue)||(cellValue.lenght==0))){
                                    cell.addCls("custom-x-form-invalid-field");
                                }
                            }
                        });
                        return false;
                    },
                    onEditClick: function(grid, rowIndex){
                        var record=grid.getStore().getAt(rowIndex);
                        //_selCobForm.hiden(false);
                        if(record.data.CDGARANT == null ||record.data.CDGARANT == "")
                        {
                            tipoAccion = "I";
                        }else{
                            tipoAccion = "U";
                        }
                        ntramite = record.data.NTRAMITE;
                        nfactura = record.data.NFACTURA;
                        ffactura = record.data.FFACTURA;
                        cdtipser = record.data.CDTIPSER;
                        cdpresta = record.data.CDPRESTA;
                        ptimport = record.data.PTIMPORT;
                        descporc = record.data.DESCPORC;
                        descnume = record.data.DESCNUME;
                        tasacamb = record.data.TASACAMB;
                        ptimporta = record.data.PTIMPORTA;
                        dctonuex = record.data.DCTONUEX;
                        cdmoneda = record.data.CDMONEDA;
                        ventanaDetalleCobertura.show();
                    },
                    buttonAlign : 'center',
                    buttons:[
                    {
                        text: 'Continuar',
                        icon:_CONTEXT+'/resources/fam3icons/icons/accept.png',
                        handler: function() {
                        // Verificamos que todas las facturas tengan coberturas y subcoberturas
                            var obtener = [];
                            storeFacturasTramite.each(function(record) {
                                obtener.push(record.data);
                            });
                            var  bande=true;
                            var coberturaInt = null;
                            var subcoberInt = null;
                            for(var i=0;i < obtener.length;i++)
                            {
                                if(obtener[i].CDGARANT == null ||obtener[i].CDGARANT.length <= 0  )
                                {
                                    bande=false;
                                    break;
                                }else{
                                    coberturaInt= obtener[i].CDGARANT;
                                    subcoberInt = obtener[i].CDCONVAL;
                                }
                            }
                            if(bande)
                            {
                                _selCobForm.setLoading(true);
                                var json = _selCobForm.getValues();
                                json['ntramite'] = _selCobParams.ntramite;
                                json['cdgarant'] = coberturaInt;
                                json['cdconval'] = subcoberInt;
                                Ext.Ajax.request(
                                {
                                    url       : _selCobUrlSavexTramite
                                    ,jsonData :
                                    {
                                        params : json
                                    }
                                    ,success  : function(response)
                                    {
                                        _selCobForm.setLoading(false);
                                        json = Ext.decode(response.responseText);
                                        debug('respuesta:',json);
                                        centrarVentanaInterna(mensajeCorrecto(json.mensaje,json.mensaje,_selCobAvanza));
                                    }
                                    ,failure  : function(response)
                                    {
                                        _selCobForm.setLoading(false);
                                        json = Ext.decode(response.responseText);
                                        debug('respuesta:',json);
                                        centrarVentanaInterna(mensajeError(json.mensaje));
                                    }
                                });
                            }else{
                                centrarVentanaInterna(mensajeError("Verifica la cobertura y subcobertura de las facturas"));
                            }
                        }
                    }]
                });
                gridFacturasTramite = new EditorFacturasTramite();
                
                ventanaDetalleCobertura = Ext.create('Ext.window.Window', {
                    closeAction: 'hide',
                    modal: true, 
                    resizable: false,
                    items:[_selCobForm],
                    buttonAlign : 'center',
                    buttons:[
                        {   
                            text: 'Aceptar',
                            icon:_CONTEXT+'/resources/fam3icons/icons/accept.png',
                            handler: function() {
                                //var recordFactura = gridFacturasTramite.getSelectionModel().getSelection()[0];
                                if (_selCobForm.form.isValid()) {
                                    _selCobForm.form.submit({
                                        waitMsg:'Procesando...',	
                                        url: _URL_guardarCoberturaxFactura,
                                        params: {
                                            'params.ntramite'    : ntramite,
                                            'params.nfactura'    : nfactura,
                                            'params.ffactura'    : ffactura,
                                            'params.cdtipser'    : cdtipser,
                                            'params.cdpresta'    : cdpresta,
                                            'params.ptimport'    : ptimport,
                                            'params.descporc'    : descporc,
                                            'params.descnume'    : descnume,
                                            'params.tasacamb'    : tasacamb,
                                            'params.ptimporta'   : ptimporta,
                                            'params.dctonuex'    : dctonuex,
                                            'params.cdmoneda'    : cdmoneda,
                                            'params.cdgarant'    : _selCobForm.items.items[3].getValue(),
                                            'params.cdconval'    : _selCobForm.items.items[4].getValue(),
                                            'params.tipoAccion'  : tipoAccion
                                        },
                                        failure: function(form, action) {
                                            centrarVentanaInterna(mensajeError("Error al guardar la cobertura y subcobertura "));
                                        },
                                        success: function(form, action) {
                                            storeFacturasTramite.reload();
                                            _selCobForm.getForm().reset();
                                            ventanaDetalleCobertura.close();
                                            centrarVentanaInterna(mensajeCorrecto("Aviso","Se ha guardado la cobertura y subcobertura"));	
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
                        },
                        {
                            text: 'Cancelar',
                            icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
                            handler: function() {
                                _selCobForm.getForm().reset();
                                ventanaDetalleCobertura.close();
                            }
                        }
                    ]
                });
                
                if(_selCobParams.tipopago == TipoPago.Reembolso){
                    var params = {
                        'smap.ntramite' : _selCobParams.ntramite
                    };
                    storeFacturasTramite.load({
                        params: params,
                        callback: function(records, operation, success){
                            if(success){
                                if(records.length <= 0){
                                    centrarVentanaInterna(Ext.Msg.show({
                                        title: 'Aviso',
                                        msg: 'No se encontraron datos',
                                        buttons: Ext.Msg.OK,
                                        icon: Ext.Msg.ERROR
                                    }));
                                }
                            }else{
                                centrarVentanaInterna(Ext.Msg.show({
                                    title: 'Error',
                                    msg: 'Error al obtener los datos',
                                    buttons: Ext.Msg.OK,
                                    icon: Ext.Msg.ERROR
                                }));
                            }
                        }
                    });

                    panelPrincipal = Ext.create('Ext.form.Panel',{
                        renderTo: 'selcobdivpri',
                        border     : false
                        ,bodyStyle:'padding:5px;'
                        ,items      : [
                            gridFacturasTramite
                        ]
                    });
                }else{
                    panelPrincipal = Ext.create('Ext.form.Panel',{
                        renderTo: 'selcobdivpri',
                        border     : false
                        ,bodyStyle:'padding:5px;'
                        ,items      : [
                            _selCobForm
                        ]
                    });
                }
            });

            ////// funciones //////
            function _selCobGuardar()
            {
                var valido = true;
                if(valido)
                {
                    valido = _selCobForm.isValid();
                    if(!valido)
                    {
                        datosIncompletos();
                    }
                }
                if(valido)
                {
                    _selCobForm.setLoading(true);
                    var json = _selCobForm.getValues();
                    json['ntramite'] = _selCobParams.ntramite;
                    Ext.Ajax.request(
                    {
                        url       : _selCobUrlSave
                        ,jsonData :
                        {
                            params : json
                        }
                        ,success  : function(response)
                        {
                            _selCobForm.setLoading(false);
                            json = Ext.decode(response.responseText);
                            debug('respuesta:',json);
                            centrarVentanaInterna(mensajeCorrecto(json.mensaje,json.mensaje,_selCobAvanza));
                        }
                        ,failure  : function(response)
                        {
                            _selCobForm.setLoading(false);
                            json = Ext.decode(response.responseText);
                            debug('respuesta:',json);
                            centrarVentanaInterna(mensajeError(json.mensaje));
                        }
                    });
                }
            }
            function _selCobAvanza()
            {
            	console.log("_selCobParams");
            	console.log(_selCobParams);
                var params =
                {
                    'params.ntramite' : _selCobParams.ntramite,
                    'params.tipopago' : _selCobParams.tipopago
                }
                
                alert("ENTRA"+_selCobParams);
                /*Ext.create('Ext.form.Panel').submit(
                {
                    url             : _selCobParams.otvalor02==TipoPago.Directo ? _selCobUrlAvanza : _selCobUrlAvanzaReembolso
                    ,standardSubmit : true
                    ,params         : params
                });*/
            }
        </script>
    </head>
    <body>
        <div id="selcobdivpri" style="height:600px;"></div>
    </body>
</html>