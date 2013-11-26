<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
///////////////////////
////// variables //////
/*///////////////////*/
var panCanAutoUrlCancelar = '<s:url namespace="/cancelacion" action="cancelacionAutoManual" />';
var panCanAutoStorePolizas;
/*///////////////////*/
////// variables //////
///////////////////////

Ext.onReady(function()
{
	/////////////////////
	////// modelos //////
	/*/////////////////*/
	Ext.define('PanCanAutoPoliza',
    {
        extend  : 'Ext.data.Model'
        ,fields :
        [
            {
                name        : "FEMISION"
                ,type       : "date"
                ,dateFormat : "d/m/Y"
            }
            ,{
                name        : "FEINICOV"
                ,type       : "date"
                ,dateFormat : "d/m/Y"
            }
            ,{
                name        : "FEFINIV"
                ,type       : "date"
                ,dateFormat : "d/m/Y"
            }
            ,{
                name        : "PRITOTAL"
                ,type       : "float"
            }
            ,"NOMBRE"
            ,"DSRAMO"
            ,"CDRAMO"
            ,"DSTIPSIT"
            ,"CDTIPSIT"
            ,"DSUNIECO"
            ,"CDUNIECO"
            ,"NMPOLIZA"
            ,"NMPOLIEX"
            ,"NMSOLICI"
            ,"ESTADO"
            ,{
                name  : 'activo'
                ,type : 'boolean'
            }
            ,{
                name        : "FERECIBO"
                ,type       : "date"
                ,dateFormat : "d/m/Y"
            }
        ]
    });
    /*/////////////////*/
    ////// modelos //////
	/////////////////////
    
    ////////////////////
    ////// stores //////
    /*////////////////*/
	panCanAutoStorePolizas = Ext.create('Ext.data.Store',
    {
        pageSize  : 10
        ,autoLoad : true
        ,model    : 'PanCanAutoPoliza'
        ,proxy    :
        {
            enablePaging  : true
            ,reader       : 'json'
            ,type         : 'memory'
            ,data         :
            [
                <s:set name="contador" value="0" />
                <s:iterator value="slist1" var="mapa">
                <s:if test="#contador>0">
                ,
                </s:if>
                {
                	FEMISION  : '<s:property value='%{getSlist1().get(#contador).get("FEMISION")}' />'
                	,FEINICOV : '<s:property value='%{getSlist1().get(#contador).get("FEINICOV")}' />'
                	,FEFINIV  : '<s:property value='%{getSlist1().get(#contador).get("FEFINIV")}' />'
                	,PRITOTAL : '<s:property value='%{getSlist1().get(#contador).get("PRITOTAL")}' />'
                    ,NOMBRE   : '<s:property value='%{getSlist1().get(#contador).get("NOMBRE")}' />'
                    ,DSRAMO   : '<s:property value='%{getSlist1().get(#contador).get("DSRAMO")}' />'
                    ,CDRAMO   : '<s:property value='%{getSlist1().get(#contador).get("CDRAMO")}' />'
                    ,DSTIPSIT : '<s:property value='%{getSlist1().get(#contador).get("DSTIPSIT")}' />'
                    ,CDTIPSIT : '<s:property value='%{getSlist1().get(#contador).get("CDTIPSIT")}' />'
                    ,DSUNIECO : '<s:property value='%{getSlist1().get(#contador).get("DSUNIECO")}' />'
                    ,CDUNIECO : '<s:property value='%{getSlist1().get(#contador).get("CDUNIECO")}' />'
                    ,NMPOLIZA : '<s:property value='%{getSlist1().get(#contador).get("NMPOLIZA")}' />'
                    ,NMPOLIEX : '<s:property value='%{getSlist1().get(#contador).get("NMPOLIEX")}' />'
                    ,NMSOLICI : '<s:property value='%{getSlist1().get(#contador).get("NMSOLICI")}' />'
                    ,ESTADO   : '<s:property value='%{getSlist1().get(#contador).get("ESTADO")}' />'
                    ,activo   : false
                    ,FERECIBO : '<s:property value='%{getSlist1().get(#contador).get("FERECIBO")}' />'
                }
                <s:set name="contador" value="#contador+1" />
                </s:iterator>
            ]
        }
    });
    /*////////////////*/
    ////// stores //////
	////////////////////
	
	///////////////////////
	////// contenido //////
	/*///////////////////*/
	Ext.create('Ext.grid.Panel',
	{
		renderTo     : 'panCanAutoDivPri'
		,id          : 'panCanAutoGridPri'
		,title       : 'Confirmar cancelaciones'
		,store       : panCanAutoStorePolizas
		,buttonAlign : 'center'
		,buttons     :
		[
		    {
		    	text     : 'Confirmar'
		    	,icon    : '${ctx}/resources/fam3icons/icons/key.png'
		    	,handler : function()
		    	{
		    		var jsonObject       = {};
		    		jsonObject['slist1'] = [];
		    		var grid             = this.up().up();
		    		panCanAutoStorePolizas.each(function(record)
		    		{
		    			jsonObject['slist1'].push(record.raw);
		    		});
		    		grid.setLoading(true);
		    		Ext.Ajax.request(
		    		{
		    			url       : panCanAutoUrlCancelar
		    			,jsonData : jsonObject
		    			,success  : function(response,opts)
                        {
		    				grid.setLoading(false);
		    				var json=Ext.decode(response.responseText);
		    				if(json.success==true)
		    				{
		    					debug('ok');
		    				}
		    				else
		    				{
		    					Ext.Msg.show({
                                    title:'Error',
                                    msg: 'Error al cancelar',
                                    buttons: Ext.Msg.OK,
                                    icon: Ext.Msg.ERROR
                                });
		    				}
                        }
		    		    ,failure  : function()
		    		    {
		    		    	grid.setLoading(false);
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
		]
		,columns     :
		[
		    {
		    	header     : 'Sucursal'
		    	,dataIndex : 'DSUNIECO'
		    	,flex      : 1
		    }
		    ,{
                header     : 'Producto'
                ,dataIndex : 'DSRAMO'
                ,flex      : 1
            }
		    ,{
                header     : 'Modalidad'
                ,dataIndex : 'DSTIPSIT'
                ,flex      : 1
            }
		    ,{
                header     : 'P&oacute;liza'
                ,dataIndex : 'NMPOLIEX'
                ,flex      : 1
            }
		    ,{
                header     : 'Cliente'
                ,dataIndex : 'NOMBRE'
                ,flex      : 1
            }
		    ,{
                header     : 'Fecha de recibo'
                ,dataIndex : 'FERECIBO'
                ,flex      : 1
                ,xtype     : 'datecolumn'
                ,format    : 'd M Y'
            }
		]
	});
	
});
</script>
<div id="panCanAutoDivPri">
</div>