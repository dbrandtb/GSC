<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
debug('###############################################');
debug('###### scriptMesaAutorizacionEndosos.jsp ######');
debug('###############################################');

///////////////////////
////// variables //////
_4_botones=
{
    xtype         : 'actioncolumn'
    ,hidden       : false
    ,width        : 30
    ,menuDisabled : true
    ,sortable     : false
    ,items        :
    [
        {
	        icon     : '${ctx}/resources/fam3icons/icons/key.png'
	        ,tooltip : 'Autorizar'
	        ,handler : _4_autorizarEndoso
        }
    ]
};

var _4_urlAutorizarEndoso = '<s:url namespace="/endosos" action="autorizarEndoso" />';
////// variables //////
///////////////////////

///////////////////////
////// funciones //////
function _4_autorizarEndoso(grid,rowIndex,colIndex)
{
	var store=grid.getStore();
	var record=store.getAt(rowIndex);
	debug('record',record);
	
	var status=record.get('status');
	
	var valido=true;
	
	if(valido)
	{
		valido=status=='8';
		if(!valido)
		{
			mensajeWarning('El endoso ya est&aacute; confirmado');
		}
	}
	
	if(valido)
	{
		mcdinGrid.setLoading(true);
		Ext.Ajax.request(
		{
			url       : _4_urlAutorizarEndoso
			,params   :
			{
				'smap1.ntramite'     : record.get('ntramite')
	            ,'smap1.cdunieco'    : record.get('cdunieco')
	            ,'smap1.cdramo'      : record.get('cdramo')
	            ,'smap1.estado'      : record.get('estado')
	            ,'smap1.nmpoliza'    : record.get('nmpoliza')
	            ,'smap1.nmsuplem'    : record.get('nmsuplem')
	            ,'smap1.nsuplogi'    : record.get('nmsolici')
	            ,'smap1.cdtipsup'    : record.get('nombre')
	            ,'smap1.status'      : '9'
	            ,'smap1.fechaEndoso' : Ext.Date.format(record.get('ferecepc'),'d/m/Y')
			}
			,success  : function(response)
			{
				mcdinGrid.setLoading(false);
				var json=Ext.decode(response.responseText);
				if(json.success==true)
				{
					Ext.Msg.show(
					{
                        title    : 'Endoso autorizado'
                        ,msg     : 'El endoso ha sido autorizado'
                        ,buttons : Ext.Msg.OK
                        ,fn      : function()
                        {
                        	Ext.create('Ext.form.Panel').submit({standardSubmit:true});
                        }
                    });
				}
				else
				{
					mensajeError(json.error);
				}
			}
		    ,failure  : function()
		    {
		    	mcdinGrid.setLoading(false);
		    }
		});
	}
	
}
////// funciones //////
///////////////////////

</script>