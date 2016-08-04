<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
////// urls //////


var _p29_urlRecotizar                      = '<s:url namespace="/endosos"           action="retarificarEndosos" />';


////// urls //////

////// variables //////


////// variables //////
Ext.onReady(function()
{	
	// Se aumenta el timeout para todas las peticiones:
	Ext.Ajax.timeout = 485000; // 8 min
	Ext.override(Ext.form.Basic, { timeout: Ext.Ajax.timeout / 1000 });
	Ext.override(Ext.data.proxy.Server, { timeout: Ext.Ajax.timeout });
	Ext.override(Ext.data.Connection, { timeout: Ext.Ajax.timeout });
	
	var nmpoliza = '<s:property value="smap4.nmpoliza" />';
	var cdunieco = '<s:property value="smap4.cdunieco" />';
	var cdramo   = '<s:property value="smap4.cdramo"   />';	
	var estado   = '<s:property value="smap4.estado"   />';
	var nmsuplem = '<s:property value="smap4.nmsuplem" />';
	
	debug('***** smap4.nmpoliza=', nmpoliza);
	debug('***** smap4.cdunieco=', cdunieco);
	debug('***** smap4.cdramo=', cdramo);
	debug('***** smap4.estado=', estado);
	debug('***** smap4.nmsuplem=', nmsuplem);
	
	
	////// modelos //////
	Ext.define('ModeloDetalleCotizacion',
	{
	    extend  : 'Ext.data.Model'
	    ,fields :
	    [
	        {name  : 'Codigo_Garantia'}
	        ,{name : 'IMPORTE',type : 'float'}
	        ,{name : 'NOMBRE_GARANTIA'}
	        ,{name : 'cdtipcon'}
	        ,{name : 'nmsituac'}
	        ,{name : 'orden'}
	        ,{name : 'PARENTESCO'}
	        ,{name : 'ORDEN_PARENTESCO'}
	    ]
	});
	
	////// modelos //////
	////// stores //////
	
	////// stores //////
	////// componentes //////
	
	////// componentes //////
	
	////// contenido //////
	
	Ext.Ajax.request(
    {
        url     : _p29_urlRecotizar
        ,params :
        {
        	'smap4.cdunieco'          : cdunieco
            ,'smap4.cdramo'           : cdramo
            ,'smap4.estado'           : estado
            ,'smap4.nmpoliza'         : nmpoliza
            ,'smap4.nmsuplem'         : nmsuplem
        }
        ,success : function(response)
        {
            var json = Ext.decode(response.responseText);
            debug('### retarificar:',json);
            if(json.mensajeRespuesta&&json.mensajeRespuesta.length>0)
            {
            	debug('if', json);
                centrarVentanaInterna(Ext.Msg.show(
                {
                    title    :'Verificar datos'
                    ,msg     : json.mensajeRespuesta
                    ,buttons : Ext.Msg.OK
                    ,icon    : Ext.Msg.WARNING
                }));
            } else if(Ext.isEmpty(json.slist1)) {
            	debug('else if', json);
            	Ext.Msg.show(
                {
                    title    :'Error no hay datos'
                    ,msg     : json.mensajeRespuesta
                    ,buttons : Ext.Msg.OK
                    ,icon    : Ext.Msg.WARNING
                });
            } else {
            	debug('else', json);
                var orden=0;
                var parentescoAnterior='qwerty';
                for(var i=0;i<json.slist1.length;i++)
                {
                    if(json.slist1[i].PARENTESCO!=parentescoAnterior)
                    {
                        orden++;
                        parentescoAnterior=json.slist1[i].PARENTESCO;
                    }
                    json.slist1[i].ORDEN_PARENTESCO=orden+'_'+json.slist1[i].PARENTESCO;
                }
                Ext.create('Ext.grid.Panel',
                        {
                        	renderTo: '_p29_divpri'
                            ,store : Ext.create('Ext.data.Store',
                            {
                                model       : 'ModeloDetalleCotizacion'
                                ,groupField : 'ORDEN_PARENTESCO'
                                ,sorters    :
                                [
                                    {
                                        sorterFn: function(o1, o2)
                                        {
                                            if (o1.get('orden') === o2.get('orden'))
                                            {
                                                return 0;
                                            }
                                            return o1.get('orden') < o2.get('orden') ? -1 : 1;
                                        }
                                    }
                                ]
                                ,proxy :
                                {
                                    type    : 'memory'
                                    ,reader : 'json'
                                }
                                ,data:json.slist1
                            })
                            ,columns :
                            [
                                {
                                    header           : 'Nombre de la cobertura'
                                    ,dataIndex       : 'NOMBRE_GARANTIA'
                                    ,flex            : 2
                                    ,summaryType     : 'count'
                                    ,summaryRenderer : function(value)
                                    {
                                        return Ext.String.format('Total de {0} cobertura{1}', value, value !== 1 ? 's' : '');
                                    }
                                }
                                ,{
                                    header       : 'Importe por cobertura'
                                    ,dataIndex   : 'IMPORTE'
                                    ,flex        : 1
                                    ,renderer    : Ext.util.Format.usMoney
                                    ,align       : 'right'
                                    ,summaryType : 'sum'
                                }
                            ]
                            ,features :
                            [
                                {
                                    groupHeaderTpl :
                                    [
                                        '{name:this.formatName}'
                                        ,{
                                            formatName:function(name)
                                            {
                                                return name.split("_")[1];
                                            }
                                        }
                                    ]
                                    ,ftype          : 'groupingsummary'
                                    ,startCollapsed : false
                                    
                                }
                            ]
                        ,bbar: Ext.create('Ext.toolbar.Toolbar',
                        {
                            buttonAlign : 'right'
                            ,items      :
                            [
                                '->'
                                ,Ext.create('Ext.form.Label',
                                {
                                    style          : 'color:white;'
                                    ,initComponent : function()
                                    {
                                        var sum=0;
                                        for(var i=0;i<json.slist1.length;i++)
                                        {
                                            sum+=parseFloat(json.slist1[i].IMPORTE);
                                        }
                                        this.setText('Total: '+Ext.util.Format.usMoney(sum));
                                        this.callParent();
                                    }
                                })
                            ]
                        })})
                        
            }
        }
        
    })
})


</script>
</head>
<body><div id="_p29_divpri" style="height:700px;"></div></body>
</html>