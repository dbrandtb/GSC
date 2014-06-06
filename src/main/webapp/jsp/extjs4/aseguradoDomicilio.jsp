<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
--%>
<script>
///////////////////////
////// variables //////
/*///////////////////*/
var inputCduniecop4        = '<s:property value="smap1.pv_cdunieco" />';
var inputCdramop4          = '<s:property value="smap1.pv_cdramo" />';
var inputCdtipsitp4        = '<s:property value="smap1.cdtipsit" />';
var inputEstadop4          = '<s:property value="smap1.pv_estado" />';
var inputNmpolizap4        = '<s:property value="smap1.pv_nmpoliza" />';
var inputNmsituacp4        = '<s:property value="smap1.pv_nmsituac" />';
var inputCdpersonp4        = '<s:property value="smap1.pv_cdperson" />';
var inputCdrolp4           = '<s:property value="smap1.pv_cdrol" />';
var inputNombreaseguradop4 = '<s:property value="smap1.nombreAsegurado" escapeHtml="false" />';
var inputCdrfcp4           = '<s:property value="smap1.cdrfc" escapeHtml="false" />';
var urlRegresarp4          = '<s:url namespace="/" action="editarAsegurados" />';
var urlCargarp4            = '<s:url namespace="/" action="cargarPantallaDomicilio" />';
var urlGuardarp4           = '<s:url namespace="/" action="guardarPantallaDomicilio" />';
var _ComboColoniasUrl      = '<s:url namespace="/catalogos" action="obtieneCatalogo" />';
var formPanelp4;
var contextop4             = '${ctx}';
<s:if test='smap1!=null&&smap1.botonCopiar!=null&&smap1.botonCopiar=="1"'>
var esElContratanteP4      = false;
</s:if>
<s:else>
var esElContratanteP4      = true;
</s:else>
/*///////////////////*/
////// variables //////
///////////////////////

///////////////////////
////// funciones //////
/*///////////////////*/

/*///////////////////*/
////// funciones //////
///////////////////////

Ext.onReady(function(){
	
	debug("esElContratanteP4",esElContratanteP4&&esElContratanteP4==true?'si':'no');

    /////////////////////
    ////// modelos //////
    /*/////////////////*/
    Ext.define('Modelo1p4',{
        extend     : 'Ext.data.Model',
        <s:property value="item1" />
    });
    /*/////////////////*/
    ////// modelos //////
    /////////////////////
    
    ////////////////////
    ////// stores //////
    /*////////////////*/

    /*////////////////*/
    ////// stores //////
    ////////////////////
    
    /////////////////////////
    ////// componentes //////
    /*/////////////////////*/
    
    /*/////////////////////*/
    ////// componentes //////
    /////////////////////////
    
    ///////////////////////
    ////// contenido //////
    /*///////////////////*/
    formPanelp4=Ext.create('Ext.form.Panel',
    {
        renderTo    : 'maindivp4',
        border      : 0,
        buttonAlign : 'center',
        url         : urlGuardarp4,
        items       :
        [
            Ext.create('Ext.panel.Panel',
            {
                title         : 'Domicilio',
                collapsible   : true,
                titleCollapse : true,
                style         : 'margin:5px;',
                defaults      :
                {
                    style : 'margin : 5px;'
                },
                layout        :
                {
                    type    : 'table',
                    columns : 3
                },
                items:
                [
                    {
                        fieldLabel : inputNmsituacp4>0?'Asegurado':'Contratante',
                        xtype      : 'textfield',
                        readOnly   : true,
                        name       : 'smap1.asegurado'
                    },
                    {
                        fieldLabel : 'RFC',
                        xtype      : 'textfield',
                        readOnly   : true,
                        name       : 'smap1.rfc'
                    }
                ]
            }),
            Ext.create('Ext.panel.Panel',{
                title           : 'Datos adicionales',
                collapsible     : true,
                titleCollapse   : true,
                style           : 'margin:5px;',
                maxHeight       : 200,
                autoScroll      : true,
                defaults        :
                {
                    style : 'margin:5px;'
                },
                layout          :
                {
                    type    : 'table',
                    columns : 2
                },
                <s:property value="item2" />
            }),
            Ext.create('Ext.panel.Panel',{
                title           : 'Direcci&oacute;n',
                collapsible     : true,
                titleCollapse   : true,
                style           : 'margin:5px;',
                defaults        :
                {
                    style : 'margin:5px;'
                },
                layout          :
                {
                    type    : 'table',
                    columns : 2
                },
                items           :
                /*
                map.put("CDPERSON" , rs.getString("CDPERSON"));
                map.put("NMORDDOM" , rs.getString(""));
                map.put("DSDOMICI" , rs.getString(""));
                map.put("NMTELEFO" , rs.getString(""));
                map.put("CDPOSTAL" , rs.getString(""));
                map.put("CDEDO"    , rs.getString(""));
                map.put("CDMUNICI" , rs.getString(""));
                map.put("CDCOLONI" , rs.getString(""));
                map.put("NMNUMERO" , rs.getString(""));
                map.put("NMNUMINT" , rs.getString(""));
                */
                [
                    <s:property value="item3" />
                    /*{
                    	fieldLabel     : 'Consecutivo',
                    	xtype          : 'numberfield',
                    	name           : 'smap1.NMORDDOM',
                    	readOnly       : true
                    },
                    {
                    	fieldLabel     : 'C&oacute;digo postal',
                    	xtype          : 'textfield',
                    	name           : 'smap1.CODPOSTAL',
                    	readOnly       : true
                    },
                    {
                        xtype          : 'textfield',
                        name           : 'smap1.CDEDO',
                        hidden         : false
                    },
                    {
                        fieldLabel     : 'Estado / Ciudad',
                        xtype          : 'textfield',
                        name           : 'smap1.estado',
                        readOnly       : true
                    },
                    {
                        xtype          : 'textfield',
                        name           : 'smap1.CDMUNICI',
                        hidden         : false
                    },
                    {
                        fieldLabel     : 'Delegaci&oacute;n / Municipio',
                        xtype          : 'textfield',
                        name           : 'smap1.Municipio',
                        readOnly       : true
                    },
                    {
                    	fieldLabel     : 'Tel&eacute;fono',
                    	xtype          : 'textfield',
                    	name           : 'smap1.NMTELEFO',
                    	allowBlank     : true,
                    	readOnly       : !esElContratanteP4
                    },{
                        xtype:'combo',
                        id:'coloniaId',//id
                        name:'smap1.CDCOLONI',
                        fieldLabel:'Colonia',
                        displayField: 'value',
                        valueField: 'key',
                        readOnly: !esElContratanteP4,
                        store:Ext.create('Ext.data.Store', {
                            model:'Generic',
                            autoLoad:false,
                            proxy:
                            {
                                type: 'ajax',
                                url: _ComboColoniasUrl,
                                reader:
                                {
                                    type: 'json',
                                    root: 'lista'
                                }
                            }
                        }),
                        editable:true,
                        queryMode:'local',
                        style:'margin:5px;',
                        allowBlank:false
                    },
                    {
                        fieldLabel     : 'Calle',
                        xtype          : 'textfield',
                        name           : 'smap1.DSDOMICI',
                        allowBlank     : false,
                        readOnly       : !esElContratanteP4
                    },
                    {
                        fieldLabel     : 'Exterior',
                        xtype          : 'textfield',
                        name           : 'smap1.NMNUMERO',
                        maxLength      : 10,
                        allowBlank     : false,
                        readOnly       : !esElContratanteP4
                    },
                    {
                        fieldLabel     : 'Interior',
                        xtype          : 'textfield',
                        name           : 'smap1.NMNUMINT',
                        maxLength      : 10,
                        allowBlank     : true,
                        readOnly       : !esElContratanteP4
                    }*/
                ]
            }),
        ],
        buttons:
        [
            <%--
            {
                text:'Regresar',
                icon: contexto+'/resources/extjs4/resources/ext-theme-neptune/images/toolbar/scroll-left.png',
                handler:function()
                {
                    Ext.create('Ext.form.Panel').submit(
                    {
                        url : urlRegresar,
                        standardSubmit:true,
                        params:
                        {
                            'map1.cdunieco' : inputCdunieco,
                            'map1.cdramo'   : inputCdramo,
                            'map1.estado'   : inputEstado,
                            'map1.nmpoliza' : inputNmpoliza
                        }
                    });
                }
            },
            --%>
            {
                text:'Guardar cambios',
                id       : 'idbotonguardardireccion',
                disabled : true,
                icon: contextop4+'/resources/fam3icons/icons/accept.png',
                handler:function()
                {
                    if(this.up().up().getForm().isValid())
                    {
                        this.up().up().setLoading(true);
                        this.up().up().getForm().submit(
                        {
                            params:
                            {
                                'smap1.pv_cdunieco' : inputCduniecop4,
                                'smap1.pv_cdramo'   : inputCdramop4,
                                'smap1.pv_estado'   : inputEstadop4,
                                'smap1.pv_nmpoliza' : inputNmpolizap4,
                                'smap1.pv_nmsituac' : inputNmsituacp4,
                                'smap1.pv_cdperson' : inputCdpersonp4,
                                'smap1.pv_cdrol'    : inputCdrolp4,
                                'smap1.cdtipsit'    : inputCdtipsitp4
                            },
                            success:function(response,opts)
                            {
                                formPanelp4.setLoading(false);
                                var json=Ext.decode(opts.response.responseText);
                                if(json.success==true)
                                {
                                    Ext.Msg.show({
                                        title:'Datos guardados',
                                        msg: 'Se han guardado los datos',
                                        buttons: Ext.Msg.OK
                                    });
                                    expande(2);
                                }
                                else
                                {
                                    Ext.Msg.show({
                                        title:'Error',
                                        msg: 'Error al guardar la informaci&oacute;n',
                                        buttons: Ext.Msg.OK,
                                        icon: Ext.Msg.ERROR
                                    });
                                }
                            },
                            failure:function(response,opts)
                            {
                                formPanelp4.setLoading(false);
                                Ext.Msg.show({
                                    title:'Error',
                                    msg: 'Error al guardar la informaci&oacute;n',
                                    buttons: Ext.Msg.OK,
                                    icon: Ext.Msg.ERROR
                                });
                            }
                        });
                    }
                    else
                    {
                        Ext.Msg.show({
                            title:'Datos incompletos',
                            msg: 'Favor de llenar los campos requeridos',
                            buttons: Ext.Msg.OK,
                            icon: Ext.Msg.WARNING
                        });
                    }
                }
            }
            ,{
            	text:'Cancelar',
            	icon : contextop2+ '/resources/fam3icons/icons/cancel.png',
            	handler:function()
            	{
            		expande(2);
            	}
            }
        ]
    });
    var combocoloni=formPanelp4.items.items[2].items.items[4];
    combocoloni.setEditable(true);
    //combocoloni.forceSelection=false;
    
    if((inputCdramop4+'x')=='2x'||(inputCdramop4+'x')=='4x')
    {
	    if(inputNmsituacp4>0)//si es asegurado solo puede leer cp, estado y municipio
	    {
	    	if(inputCdtipsitp4!='MS')
	    	{
	    		formPanelp4.items.items[2].items.items[1].setReadOnly(true);//cp
	    	}
	    	formPanelp4.items.items[2].items.items[2].setReadOnly(true);//estado
	    	formPanelp4.items.items[2].items.items[3].setReadOnly(true);//municipio
	    }
    }
    
    //establecer cargar colonia al cambiar cod pos
    formPanelp4.items.items[2].items.items[1].on('blur',function()
    {
        debug('cod pos change');
        formPanelp4.items.items[2].items.items[4].getStore().load(
        {
            params :
            {
                'params.cp' : formPanelp4.items.items[2].items.items[1].getValue()
            }
            ,callback : function()
            {
                var hay=false;
                formPanelp4.items.items[2].items.items[4].getStore().each(function(record)
                {
                    if(formPanelp4.items.items[2].items.items[4].getValue()==record.get('key'))
                    {
                        hay=true;
                    }
                });
                if(!hay)
                {
                	formPanelp4.items.items[2].items.items[4].setValue('');
                }
            }
        });
    });
    /*///////////////////*/
    ////// contenido //////
    ///////////////////////
    
    //////////////////////
    ////// cargador //////
    /*//////////////////*/
    Ext.define('LoaderFormp4',
    {
        extend:'Modelo1p4',
        proxy:
        {
            extraParams:
            {
                'smap1.pv_cdunieco_i'   : inputCduniecop4,
                'smap1.pv_cdramo_i'     : inputCdramop4,
                'smap1.pv_estado_i'     : inputEstadop4,
                'smap1.pv_nmpoliza_i'   : inputNmpolizap4,
                'smap1.pv_nmsituac_i'   : inputNmsituacp4,
                'smap1.pv_cdperson_i'   : inputCdpersonp4,
                'smap1.pv_cdrol_i'      : inputCdrolp4,
                'smap1.nombreAsegurado' : inputNombreaseguradop4,
                'smap1.cdrfc'           : inputCdrfcp4,
                'smap1.pv_cdtipsit_i'   : inputCdtipsit
            },
            type:'ajax',
            url : urlCargarp4,
            reader:
            {
                type:'json'
            }
        }
    });

    var loaderFormp4=Ext.ModelManager.getModel('LoaderFormp4');
    loaderFormp4.load(123, {
        success: function(resp) {
            //console.log(resp);
        	//console.log(resp);
            formPanelp4.loadRecord(resp);
            formPanelp4.down('[name=smap1.asegurado]').setValue(inputNombreaseguradop4);
            formPanelp4.down('[name=smap1.rfc]').setValue(inputCdrfcp4);
            
            debug('[name="smap1.CDCOLONI"]:',Ext.ComponentQuery.query('[name="smap1.CDCOLONI"]').length);
            Ext.ComponentQuery.query('[name="smap1.CDCOLONI"]')[Ext.ComponentQuery.query('[name="smap1.CDCOLONI"]').length-1].getStore().load(
            {
                params :
                {
                    'params.cp' : resp.data['smap1.CODPOSTAL']
                }
            });
        },
        failure:function()
        {
        	formPanelp4.down('[name=smap1.asegurado]').setValue(inputNombreaseguradop4);
        	formPanelp4.down('[name=smap1.rfc]').setValue(inputCdrfcp4);
            formPanelp4.down('[name=smap1.NMORDDOM]').setValue(1);
            Ext.Msg.show({
                title:'Aviso',
                icon: Ext.Msg.WARNING,
                msg: 'No se encotr&oacute; domicilio anteior',
                buttons: Ext.Msg.OK
            });
        }
    });
    
    //////usa valores del padre (editarAsegurados.jsp) //////
    if(!esElContratanteP4)
    {
	    //storePersonasp2.each(function(record,index)
	    //{
	    var record = storePersonasp2.getAt(0);
	        if(inputNmsituacp4>1)//si es asegurano no titular le pone la direccion del titular
	        {
	            Ext.define('LoaderFormp4',
	            {
	                extend:'Modelo1p4',
	                proxy:
	                {
	                    extraParams:
	                    {
	                        'smap1.pv_cdunieco_i'   : inputCduniecop4,
	                        'smap1.pv_cdramo_i'     : inputCdramop4,
	                        'smap1.pv_estado_i'     : inputEstadop4,
	                        'smap1.pv_nmpoliza_i'   : inputNmpolizap4,
	                        'smap1.pv_nmsituac_i'   : record.get('nmsituac'),
	                        'smap1.pv_cdperson_i'   : record.get('cdperson'),
	                        'smap1.pv_cdrol_i'      : '2',
	                        'smap1.nombreAsegurado' : record.get('nombre')+' '+(record.get('segundo_nombre')?record.get('segundo_nombre')+' ':' ')+record.get('Apellido_Paterno')+' '+record.get('Apellido_Materno'),
	                        'smap1.cdrfc'           : record.get('cdrfc'),
	                        'smap1.pv_cdtipsit_i'   : inputCdtipsit
	                    },
	                    type:'ajax',
	                    url : urlCargarp4,
	                    reader:
	                    {
	                        type:'json'
	                    }
	                }
	            });
	
	            var loaderFormp4=Ext.ModelManager.getModel('LoaderFormp4');
	            loaderFormp4.load(123, {
	                success: function(resp) {
	                	debug('se desbloquea el boton porque ya copio del cliente');
	                	Ext.getCmp('idbotonguardardireccion').setDisabled(false);
	                    formPanelp4.getForm().setValues(
	                    {
	                        'smap1.NMORDDOM':resp.data['smap1.NMORDDOM'],
	                        'smap1.CODPOSTAL':resp.data['smap1.CODPOSTAL'],
	                        'smap1.estado':resp.data['smap1.estado'],
	                        'smap1.Municipio':resp.data['smap1.Municipio'],
	                        'smap1.NMTELEFO':resp.data['smap1.NMTELEFO'],
	                        'smap1.CDCOLONI':resp.data['smap1.CDCOLONI'],
	                        'smap1.DSDOMICI':resp.data['smap1.DSDOMICI'],
	                        'smap1.NMNUMERO':resp.data['smap1.NMNUMERO'],
	                        'smap1.NMNUMINT':resp.data['smap1.NMNUMINT']
	                    });
	                    debug('[name="smap1.CDCOLONI"]:',Ext.ComponentQuery.query('[name="smap1.CDCOLONI"]').length);
	                    Ext.ComponentQuery.query('[name="smap1.CDCOLONI"]')[Ext.ComponentQuery.query('[name="smap1.CDCOLONI"]').length-1].getStore().load(
                        {
                            params :
                            {
                                'params.cp' : resp.data['smap1.CODPOSTAL']
                            }
                        });
	                },
	                failure:function()
	                {
	                    Ext.Msg.show({
	                        title:'Error',
	                        icon: Ext.Msg.ERROR,
	                        msg: 'No se encontr&oacute; domicilio anterior',
	                        buttons: Ext.Msg.OK
	                    });
	                }
	            });
	        }
	    //});
    }
    else
    {
    	debug('se desbloquea el boton porque es el cliente');
    	Ext.getCmp('idbotonguardardireccion').setDisabled(false);
    }
    ////// usa valores del padre //////
    
    /*//////////////////*/
    ////// cargador //////
    //////////////////////

});
</script>
<%--
    </head>
    <body>
--%>
        <div id="maindivp4" style="height:600px;"></div>
<%--
    </body>
</html>
--%>