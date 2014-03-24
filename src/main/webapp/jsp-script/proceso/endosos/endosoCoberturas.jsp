<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
    ///////////////////////
    //////variables //////
    /*///////////////////*/
    var storeCoberturasp3;
    var storeCoberturasBorrarp3;
    var storeCoberturasAgregarp3;
    var storeCoberturasDispp3;
    var panelCoberturasp3;
    <%--
    var urlRegresar = '<s:url namespace="/" action="editarAsegurados" />';
    --%>
    var urlCargarCoberturasp3 = '<s:url namespace="/" action="cargarPantallaCoberturas" />';
    var urlCargarCoberturasDispp3 = '<s:url namespace="/endosos" action="obtenerCoberturasDisponibles" />';
    var inputCduniecop3 = '<s:property value="smap1.pv_cdunieco" />';
    var inputCdramop3 = '<s:property value="smap1.pv_cdramo" />';
    var inputEstadop3 = '<s:property value="smap1.pv_estado" />';
    var inputNmpolizap3 = '<s:property value="smap1.pv_nmpoliza" />';
    var inputNmsituacp3 = '<s:property value="smap1.pv_nmsituac" />';
    var inputCdpersonap3 = '<s:property value="smap1.pv_cdperson" />';
    var inputNtramitep3  = '<s:property value="smap1.ntramite" />';
    var inputAltabajap3  = '<s:property value="smap1.altabaja" />';
    var inputCdtipsitp3  = '<s:property value="smap1.cdtipsit" />';
    var inputFenacimip3  = '<s:property value="smap1.fenacimi" />';
    var urlGuardarCoberturasp3 = '<s:url namespace="/" action="guardarCoberturasUsuario" />';
    var urlTatrip3 = '<s:url namespace="/" action="obtenerCamposTatrigar" />';
    var urlLoadTatrip3 = '<s:url namespace="/" action="obtenerValoresTatrigar" />';
    var urlSaveTatrip3 = '<s:url namespace="/" action="guardarValoresTatrigar" />';
    var endcobUrlDoc   = '<s:url namespace="/documentos" action="ventanaDocumentosPoliza" />';
    var endcobUrlGuardar = '<s:url namespace="/endosos" action="guardarEndosoCoberturas" />';
    debug('inputCduniecop3',inputCduniecop3);
    debug('inputCdramop3',inputCdramop3);
    debug('inputEstadop3',inputEstadop3);
    debug('inputNmpolizap3',inputNmpolizap3);
    debug('inputNmsituacp3',inputNmsituacp3);
    debug('inputCdpersonap3',inputCdpersonap3);
    debug('inputNtramitep3',inputNtramitep3);
    debug('inputAltabajap3',inputAltabajap3);
    debug('inputCdtipsitp3',inputCdtipsitp3);
    /*///////////////////*/
    //////variables //////
    ///////////////////////
    
    ///////////////////////
    ////// funciones //////
    /*///////////////////*/
    function endcobSumit(form,confirmar)
    {
        debug('generar endoso');
        //var form=this.up().up();
        if(form.isValid())
        {
            form.setLoading(true);
            var json={};
            json['omap1']=form.getValues();
            json['omap1']['pv_cdunieco_i'] = inputCduniecop3;
            json['omap1']['pv_cdramo_i']   = inputCdramop3;
            json['omap1']['pv_estado_i']   = inputEstadop3;
            json['omap1']['pv_nmpoliza_i'] = inputNmpolizap3;
            var slist1=[];
            storeCoberturasBorrarp3.each(function(record)
            {
                slist1.push(
                {
                    garantia  : record.get('GARANTIA')
                    ,cdcapita : record.get('CDCAPITA')
                    ,status   : record.get('status')
                    ,ptcapita : record.get('SUMA_ASEGURADA')
                    ,ptreduci : record.get('ptreduci')
                    ,fereduci : record.get('fereduci')
                    ,swrevalo : record.get('swrevalo')
                    ,cdagrupa : record.get('cdagrupa')
                    ,cdtipbca : record.get('cdtipbca')
                    ,ptvalbas : record.get('ptvalbas')
                    ,swmanual : record.get('swmanual')
                    ,swreas   : record.get('swreas')
                });
            });
            json['slist1']=slist1;
            var slist2=[];
            storeCoberturasAgregarp3.each(function(record)
            {
                slist2.push(
                {
                    garantia  : record.get('GARANTIA')
                    ,cdcapita : record.get('CDCAPITA')
                    ,status   : record.get('status')
                    ,ptcapita : record.get('SUMA_ASEGURADA')
                    ,ptreduci : record.get('ptreduci')
                    ,fereduci : record.get('fereduci')
                    ,swrevalo : record.get('swrevalo')
                    ,cdagrupa : record.get('cdagrupa')
                    ,cdtipbca : record.get('cdtipbca')
                    ,ptvalbas : record.get('ptvalbas')
                    ,swmanual : record.get('swmanual')
                    ,swreas   : record.get('swreas')
                });
            });
            json['slist2']=slist2;
            json['smap1']={};
            json['smap1']['nmsituac']  = inputNmsituacp3;
            json['smap1']['cdperson']  = inputCdpersonap3;
            json['smap1']['altabaja']  = inputAltabajap3;
            json['smap1']['cdtipsit']  = inputCdtipsitp3;
            json['smap1']['confirmar'] = confirmar;
            json['smap1']['fenacimi']  = inputFenacimip3;
            debug(json);
            Ext.Ajax.request(
            {
                url       : endcobUrlGuardar
                ,jsonData : json
                ,timeout  : 180000
                ,success  : function(response)
                {
                    form.setLoading(false);
                    json=Ext.decode(response.responseText);
                    debug(json);
                    if(json.success==true)
                    {
                    	Ext.Msg.show(
                        {
                            title   : 'Endoso generado',
                            msg     : json.mensaje,
                            buttons : Ext.Msg.OK
                        });
                    	if(confirmar=='si')
                    	{
                    		//////////////////////////////////
                            ////// usa codigo del padre //////
                            /*//////////////////////////////*/
                            marendNavegacion(2);
                            /*//////////////////////////////*/
                            ////// usa codigo del padre //////
                            //////////////////////////////////
                    	}
                    	else
                    	{
                    		//////////////////////////////////
                            ////// usa codigo del padre //////
                            /*//////////////////////////////*/
                            marendNavegacion(4);
                            /*//////////////////////////////*/
                            ////// usa codigo del padre //////
                            //////////////////////////////////
                    	}
                        //zxc
                        /*
                        form.setLoading(true);
                        Ext.Ajax.request(
                        {
                            url     : urlRecotizar
                            ,params :
                            {
                                'panel1.nmpoliza' : inputNmpoliza
                                ,cdunieco         : inputCdunieco
                                ,cdramo           : inputCdramo
                                ,cdtipsit         : inputCdtipsit
                            }
                            ,success : function(response)
                            {
                                form.setLoading(false);
                                var json=Ext.decode(response.responseText);
                                //console.log(json);
                                if(json.mensajeRespuesta&&json.mensajeRespuesta.length>0)
                                {
                                    Ext.Msg.show({
                                        title:'Verificar datos',
                                        msg: json.mensajeRespuesta,
                                        buttons: Ext.Msg.OK,
                                        icon: Ext.Msg.WARNING
                                    });
                                }
                                else
                                {
                                    var orden=0;
                                    var parentescoAnterior='qwerty';
                                    for(var i=0;i<json.slist1.length;i++)
                                    {
                                        if(json.slist1[i].parentesco!=parentescoAnterior)
                                        {
                                            orden++;
                                            parentescoAnterior=json.slist1[i].parentesco;
                                        }
                                        json.slist1[i].orden_parentesco=orden+'_'+json.slist1[i].parentesco;
                                    }
                                    Ext.create('Ext.window.Window',
                                    {
                                        title: 'Tarifa final',
                                        //maxHeight: 400,
                                        autoScroll:true,
                                        width: 660,
                                        height: 400,
                                        defaults: {
                                            width: 650
                                        },
                                        modal:false,
                                        closable:false,
                                        collapsible:true,
                                        titleCollapse:true,
                                        items:[  // Let's put an empty grid in just to illustrate fit layout
                                            Ext.create('Ext.grid.Panel',{
                                                store:Ext.create('Ext.data.Store',{
                                                    model:'ModeloDetalleCotizacion',
                                                    groupField: 'orden_parentesco',
                                                    sorters: [{
                                                        sorterFn: function(o1, o2){
                                                            if (o1.get('orden') === o2.get('orden'))
                                                            {
                                                                return 0;
                                                            }
                                                            return o1.get('orden') < o2.get('orden') ? -1 : 1;
                                                        }
                                                    }],
                                                    proxy: {
                                                        type: 'memory',
                                                        reader: 'json'
                                                    },
                                                    data:json.slist1
                                                }),
                                                columns:
                                                [
                                                    {
                                                        header    : 'Nombre de la cobertura',
                                                        dataIndex : 'Nombre_garantia',
                                                        flex      : 2,
                                                        summaryType: 'count',
                                                        summaryRenderer: function(value){
                                                            return Ext.String.format('Total de {0} cobertura{1}', value, value !== 1 ? 's' : '');
                                                        }
                                                    },
                                                    {
                                                        header      : 'Importe por cobertura',
                                                        dataIndex   : 'Importe',
                                                        flex        : 1,
                                                        renderer    : Ext.util.Format.usMoney,
                                                        align       : 'right',
                                                        summaryType : 'sum'
                                                    }
                                                ],
                                                features: [{
                                                    groupHeaderTpl:
                                                        [
                                                            '{name:this.formatName}',
                                                            {
                                                                formatName:function(name)
                                                                {
                                                                    return name.split("_")[1];
                                                                }
                                                            }
                                                        ],
                                                    ftype:'groupingsummary',
                                                    startCollapsed :true
                                                }]
                                            })
                                            ,Ext.create('Ext.toolbar.Toolbar',{
                                                buttonAlign:'right'
                                                ,items:
                                                [
                                                    '->'
                                                    ,Ext.create('Ext.form.Label',
                                                    {
                                                        style:'color:white;'
                                                        ,initComponent:function()
                                                        {
                                                            var sum=0;
                                                            for(var i=0;i<json.slist1.length;i++)
                                                            {
                                                                sum+=parseFloat(json.slist1[i].Importe);
                                                            }
                                                            this.setText('Total: '+Ext.util.Format.usMoney(sum));
                                                            this.callParent();
                                                        }
                                                    })
                                                ]
                                            })
                                        ]
                                    }).show();
                                }
                            }
                            ,failure : function()
                            {
                                form.setLoading(false);
                                Ext.Msg.show({
                                    title:'Error',
                                    msg: 'Error de comunicaci&oacute;n',
                                    buttons: Ext.Msg.OK,
                                    icon: Ext.Msg.ERROR
                                });
                            }
                        });
                        */
                        //zxc
                    }
                    else
                    {
                        mensajeError(json.error);
                    }
                }
                ,failure  : function()
                {
                    form.setLoading(false);
                    Ext.Msg.show(
                    {
                        title   : 'Error',
                        icon    : Ext.Msg.ERROR,
                        msg     : 'Error de comunicaci&oacute;n',
                        buttons : Ext.Msg.OK
                    });
                }
            });
        }
        else
        {
            Ext.Msg.show(
            {
                title   : 'Datos imcompletos',
                icon    : Ext.Msg.WARNING,
                msg     : 'Favor de llenar los campos requeridos',
                buttons : Ext.Msg.OK
            });
        }
    }
    /*///////////////////*/
    ////// funciones //////
    ///////////////////////
    
    Ext.onReady(function() {

                /////////////////////
                ////// Modelos //////
                /*/////////////////*/
                Ext.define('Modelo1p3', {
                    extend : 'Ext.data.Model',
                    fields : [ {
                        name : 'GARANTIA'
                    }, {
                        name : 'NOMBRE_GARANTIA'
                    }, {
                        name : 'SWOBLIGA'
                    }, {
                        name : 'SUMA_ASEGURADA'
                    }, {
                        name : 'CDCAPITA'
                    }, {
                        name : 'status'
                    }, {
                        name : 'cdtipbca'
                    }, {
                        name : 'ptvalbas'
                    }, {
                        name : 'swmanual'
                    }, {
                        name : 'swreas'
                    }, {
                        name : 'cdagrupa'
                    }, {
                        name : 'ptreduci'
                    }, {
                        name : 'fereduci',
                        type : 'date',
                        dateFormat : 'd/m/Y'
                    }, {
                        name : 'swrevalo'
                    } ]
                });

                Ext.define('ModeloAdicionalesp3', {
                    extend : 'Ext.data.Model'
                });
                /*/////////////////*/
                ////// Modelos //////
                /////////////////////
                ////////////////////
                ////// Stores //////
                /*////////////////*/
                storeCoberturasp3 = Ext.create('Ext.data.Store', {
                    storeId : 'storeCoberturasp3',
                    model : 'Modelo1p3',
                    proxy : {
                        type : 'ajax',
                        url : urlCargarCoberturasp3,
                        extraParams : {
                            'smap1.pv_cdunieco_i' : inputCduniecop3,
                            'smap1.pv_cdramo_i' : inputCdramop3,
                            'smap1.pv_estado_i' : inputEstadop3,
                            'smap1.pv_nmpoliza_i' : inputNmpolizap3,
                            'smap1.pv_nmsituac_i' : inputNmsituacp3
                        },
                        reader : {
                            type : 'json',
                            root : 'slist1'
                        }
                    },
                    autoLoad : true
                });

                storeCoberturasBorrarp3 = Ext.create('Ext.data.Store', {
                    storeId : 'storeCoberturasBorrarp3',
                    model : 'Modelo1p3'
                });
                
                storeCoberturasAgregarp3 = Ext.create('Ext.data.Store', {
                    storeId : 'storeCoberturasAgregarp3',
                    model : 'Modelo1p3'
                });
                
                storeCoberturasDispp3 = Ext.create('Ext.data.Store', {
                    storeId : 'storeCoberturasDispp3',
                    model : 'Modelo1p3',
                    proxy : {
                        type : 'ajax',
                        url : urlCargarCoberturasDispp3,
                        extraParams : {
                            'smap1.pv_cdunieco_i' : inputCduniecop3,
                            'smap1.pv_cdramo_i' : inputCdramop3,
                            'smap1.pv_estado_i' : inputEstadop3,
                            'smap1.pv_nmpoliza_i' : inputNmpolizap3,
                            'smap1.pv_nmsituac_i' : inputNmsituacp3
                        },
                        reader : {
                            type : 'json',
                            root : 'slist1'
                        }
                    },
                    autoLoad : true
                });
                /*////////////////*/
                ////// Stores //////
                ////////////////////
                
                /////////////////////////
                ////// Componentes //////
                /*/////////////////////*/
                /*/////////////////////*/
                ////// Componentes //////
                /////////////////////////
                
                ///////////////////////
                ////// Contenido //////
                /*///////////////////*/
                Ext.create('Ext.panel.Panel',
                {
                	defaults  :
                	{
                		style : 'margin : 5px;'
                	}
                    ,renderTo : 'pan_usu_cob_divgrid'
                    ,layout   :
                    {
                    	type     : 'table'
                    	,columns : 2
                    }
                    ,border   : 0
                    ,items    :
                    [
                        Ext.create('Ext.grid.Panel',
                        {
                        	title          : 'Coberturas actuales'
                        	,icon          : '${ctx}/resources/fam3icons/icons/accept.png'
                        	,store         : storeCoberturasp3
                        	,buttonAlign   : 'center'
                        	,titleCollapse : true
                        	,collapsible   : false
                        	,height        : 250
                        	,width         : 350
                        	,tools         :
                            [
                               {
                                   type     : 'help'
                                   ,tooltip : 'Coberturas que tiene actualmente el asegurado'
                               }
                            ]
                        	,columns       :
                        	[
								{
								    header     : 'Cobertura'
								    ,dataIndex : 'NOMBRE_GARANTIA'
								    ,flex      : 1
								}
								,{
								    header     : 'Suma asegurada'
								    ,dataIndex : 'SUMA_ASEGURADA'
								    ,width     : 110
								}
                                ,{
                                    menuDisabled : true
                                    ,width       : 30
                                    ,dataIndex   : 'SWOBLIGA'
                                    ,renderer    : function(value)
                                    {
                                        var rvalue = '';
                                        if (value == 'N'&&inputAltabajap3=='baja')
                                        {
                                            rvalue = '<img src="${ctx}/resources/fam3icons/icons/delete.png" data-qtip="Eliminar" style="cursor:pointer;">';
                                        }
                                        return rvalue;
                                    }
                                }
                        	]
                        	,listeners :
                        	{
                        		cellclick : function(grid, td, cellIndex, record)
                        		{
                        			debug('cellclick');
                        		    if(cellIndex==2&&record.get('SWOBLIGA')=='N'&&inputAltabajap3=='baja')
                        		    {
                        		    	storeCoberturasBorrarp3.add(record);
                        		    	storeCoberturasp3.remove(record)
                        		    }
                        		}
                        	}
                        })
                        ,Ext.create('Ext.grid.Panel',
                        {
                            title          : 'Coberturas eliminadas'
                            ,icon          : '${ctx}/resources/fam3icons/icons/delete.png'
                            ,store         : storeCoberturasBorrarp3
                            ,buttonAlign   : 'center'
                            ,hidden        : inputAltabajap3=='alta'
                            ,titleCollapse : true
                            ,collapsible   : false
                            ,tools         :
                            [
                                {
                                    type     : 'help'
                                    ,tooltip : 'Coberturas que ten&iacute;a el asegurado y ser&aacute;n eliminadas'
                                }
                            ]
                            ,height        : 250
                            ,width         : 350
                            ,columns       :
                            [
                                {
                                    header     : 'Cobertura'
                                    ,dataIndex : 'NOMBRE_GARANTIA'
                                    ,flex      : 1
                                }
                                ,{
                                    header     : 'Suma asegurada'
                                    ,dataIndex : 'SUMA_ASEGURADA'
                                    ,width     : 110
                                }
                                ,{
                                	menuDisabled : true
                                    ,width       : 30
                                    ,icon        : '${ctx}/resources/fam3icons/icons/cancel.png'
                                    ,renderer    : function(value)
                                    {
                                    	return '<img src="${ctx}/resources/fam3icons/icons/control_rewind_blue.png" data-qtip="Volver a agregar" style="cursor:pointer;">';
                                    }
                                }
                            ]
                            ,listeners :
                            {
                            	cellclick : function(grid, td, cellIndex, record)
                                {
                                    debug('cellclick');
                                    if(cellIndex==2)
                                    {
                                        storeCoberturasp3.add(record);
                                        storeCoberturasBorrarp3.remove(record)
                                    }
                                }
                            }
                        })
                        ,Ext.create('Ext.grid.Panel',
                        {
                        	title          : 'Coberturas disponibles'
                        	,icon          : '${ctx}/resources/fam3icons/icons/zoom.png'
                        	,titleCollapse : true
                        	,collapsible   : false
                        	,hidden        : inputAltabajap3=='baja'
                        	,tools         :
                            [
                               {
                                   type     : 'help'
                                   ,tooltip : 'Coberturas disponibles para agregar al asegurado'
                               }
                            ]
                        	,store         : storeCoberturasDispp3
                        	,height        : 250
                            ,width         : 350
                        	,columns       :
                            [
                                {
                                    header     : 'Cobertura'
                                    ,dataIndex : 'NOMBRE_GARANTIA'
                                    ,flex      : 1
                                }
                                ,{
                                    header     : 'Suma asegurada'
                                    ,dataIndex : 'SUMA_ASEGURADA'
                                    ,width     : 110
                                }
                                ,{
                                    menuDisabled : true
                                    ,width       : 30
                                    ,renderer    : function(value)
                                    {
                                    	return '<img src="${ctx}/resources/fam3icons/icons/add.png" data-qtip="Agregar" style="cursor:pointer;">';
                                    }
                                }
                             ]
                        	 ,listeners :
                             {
                                 cellclick : function(grid, td, cellIndex, record)
                                 {
                                     debug('cellclick');
                                     if(cellIndex==2)
                                     {
                                         storeCoberturasAgregarp3.add(record);
                                         storeCoberturasDispp3.remove(record);
                                     }
                                 }
                             }
                        })
                        ,Ext.create('Ext.grid.Panel',
                        {
                            title          : 'Coberturas agregadas'
                            ,icon          : '${ctx}/resources/fam3icons/icons/add.png'
                            ,store         : storeCoberturasAgregarp3
                            ,buttonAlign   : 'center'
                            ,colspan       : inputAltabajap3=='alta'?2:1
                            ,titleCollapse : true
                            ,collapsible   : false
                            ,hidden        : inputAltabajap3=='baja'
                            ,height        : 250
                            ,width         : 350
                            ,tools         :
                            [
                               {
                            	   type     : 'help'
                            	   ,tooltip : 'Nuevas coberturas que se van a agregar al asegurado'
                               }
                            ]
                            ,columns       :
                            [
                                {
                                    header     : 'Cobertura'
                                    ,dataIndex : 'NOMBRE_GARANTIA'
                                    ,flex      : 1
                                }
                                ,{
                                    header     : 'Suma asegurada'
                                    ,dataIndex : 'SUMA_ASEGURADA'
                                    ,width     : 110
                                }
                                ,{
                                	menuDisabled : true
                                    ,width       : 30
                                    ,renderer    : function(value)
                                    {
                                        return '<img src="${ctx}/resources/fam3icons/icons/delete.png" data-qtip="No agregar" style="cursor:pointer;">';
                                    }
                                }
                            ]
                            ,listeners :
                            {
                                cellclick : function(grid, td, cellIndex, record)
                                {
                                    debug('cellclick');
                                    if(cellIndex==2)
                                    {
                                    	storeCoberturasDispp3.add(record);
                                        storeCoberturasAgregarp3.remove(record);
                                    }
                                }
                            }
                        })
                        ,Ext.create('Ext.form.Panel',
                        {
                            title        : 'Informaci&oacute;n del endoso'
                            ,heigth      : 200
                            ,buttonAlign : 'center'
                            ,style       : 'margin : 5px; margin-bottom : 200px;'
                            ,colspan     : 2
                            ,layout      :
                            {
                                type     : 'table'
                                ,columns : 2
                            }
                            ,defaults    :
                            {
                                style : 'margin : 5px;'
                            }
                            ,items       :
                            [
                                {
                                    xtype       : 'datefield'
                                    ,fieldLabel : 'Fecha de inicio'
                                    ,format     : 'd/m/Y'
                                    ,value      : new Date()
                                    ,allowBlank : false
                                    ,name       : 'pv_fecha_i'
                                }
                            ]
                            ,buttons     :
                            [
								{
								    text     : 'Guardar endoso'
								    ,icon    : '${ctx}/resources/fam3icons/icons/disk.png'
								    ,handler : function(me)
								    {
								        var form=me.up().up();
								        endcobSumit(form,'no');
								    }
								}
								,{
								    text     : 'Confirmar endoso'
								    ,icon    : '${ctx}/resources/fam3icons/icons/key.png'
								    ,handler : function(me)
								    {
								        var form=me.up().up();
								        endcobSumit(form,'si');
								    }
								}
                                ,{
                                    text     : 'Documentos'
                                    ,icon    : '${ctx}/resources/fam3icons/icons/printer.png'
                                    ,handler : function()
                                    {
                                        Ext.create('Ext.window.Window',
                                        {
                                            title        : 'Documentos del tr&aacute;mite '+inputNtramitep3
                                            ,modal       : true
                                            ,buttonAlign : 'center'
                                            ,width       : 600
                                            ,height      : 400
                                            ,autoScroll  : true
                                            ,loader      :
                                            {
                                                url       : endcobUrlDoc
                                                ,params   :
                                                {
                                                    'smap1.nmpoliza'  : inputNmpolizap3
                                                    ,'smap1.cdunieco' : inputCduniecop3
                                                    ,'smap1.cdramo'   : inputCdramop3
                                                    ,'smap1.estado'   : inputEstadop3
                                                    ,'smap1.nmsuplem' : '0'
                                                    ,'smap1.ntramite' : inputNtramitep3
                                                    ,'smap1.nmsolici' : ''
                                                    ,'smap1.tipomov'  : '0'
                                                }
                                                ,scripts  : true
                                                ,autoLoad : true
                                            }
                                        }).show();
                                    }
                                }
                            ]
                        })
                    ]
                });
                
                /*///////////////////*/
                ////// Contenido //////
                ///////////////////////
            });
</script>
<div id="pan_usu_cob_divgrid"></div>