<%@ include file="/taglibs.jsp"%>
<script>
///////////////////////
////// variables //////
/*///////////////////*/
var pantallaValositMainContent;
var pantallaValositUrlLoad       = '<s:url namespace="/" action="pantallaValositLoad" />';
var pantallaValositUrlSave       = '<s:url namespace="/" action="pantallaValositSave" />';
var pantallaValositInputCdunieco = '<s:property value="smap1.cdunieco" />';
var pantallaValositInputCdramo   = '<s:property value="smap1.cdramo" />';
var pantallaValositInputEstado   = '<s:property value="smap1.estado" />';
var pantallaValositInputNmpoliza = '<s:property value="smap1.nmpoliza" />';
var pantallaValositInputCdtipsit = '<s:property value="smap1.cdtipsit" />';
var pantallaValositInputAgrupado = '<s:property value="smap1.agrupado" />';
var pantallaValositInputNmsituac = '<s:property value="smap1.nmsituac" />';
debug('A. Variables declaradas:');
debug(pantallaValositUrlLoad);
debug(pantallaValositInputCdunieco);
debug(pantallaValositInputCdramo);
debug(pantallaValositInputEstado);
debug(pantallaValositInputNmpoliza);
debug(pantallaValositInputCdtipsit);
debug(pantallaValositInputAgrupado);
debug(pantallaValositInputNmsituac);
debug("timestam struts: <s:property value='smap1.timestamp' />");
/*///////////////////*/
////// variables //////
///////////////////////

///////////////////////
////// funciones //////
/*///////////////////*/
debug('B. Funciones declaradas');
/*///////////////////*/
////// funciones //////
///////////////////////
Ext.onReady(function()
{
	debug('0. Ready');
	
	/////////////////////
	////// modelos //////
	/*/////////////////*/
	Ext.define('ModeloValisit',
	{
        extend:'Ext.data.Model'
        ,<s:property value="item1" />
    });
	debug('1. Modelos definidos');
    /*/////////////////*/	
    ////// modelos //////
    /////////////////////
    
    ////////////////////
    ////// stores //////
    /*////////////////*/
    debug('2. Stores definidos');
    /*////////////////*/
    ////// stores //////
    ////////////////////
    
    /////////////////////////
    ////// componentes //////
    /*/////////////////////*/
    Ext.define('PanelValosit',
    {
    	extend       : 'Ext.form.Panel'
    	,url         : pantallaValositUrlSave
    	,border      : 0
    	,buttonAlign : 'center'
    	,layout      :
    	{
    		type     : 'table'
    		,columns : 2
    	}
        ,defaults    :
        {
        	style : 'margin:5px;'
        }
        ,<s:property value="item2" />
        ,buttons     :
        [
            {
            	text     : 'Guardar'
	            ,icon    : '${ctx}/resources/fam3icons/icons/disk.png'
	            ,handler : function(me)
	            {
	            	var form=pantallaValositMainContent;
	            	debug(me);
	            	if(form.isValid())
	            	{
	            		form.setLoading(true);
	            		form.submit({
                            params:
                            {
                            	'smap1.cdunieco'  : pantallaValositInputCdunieco
                            	,'smap1.cdramo'   : pantallaValositInputCdramo
                            	,'smap1.estado'   : pantallaValositInputEstado
                            	,'smap1.nmpoliza' : pantallaValositInputNmpoliza
                            	,'smap1.cdtipsit' : pantallaValositInputCdtipsit
                            	,'smap1.agrupado' : pantallaValositInputAgrupado
                            	,'smap1.nmsituac' : pantallaValositInputNmsituac
                            },
                            success:function()
                            {
                                form.setLoading(false);
                                Ext.Msg.show({
                                    title:'Cambios guardados',
                                    msg: 'Sus cambios han sido guardados',
                                    buttons: Ext.Msg.OK
                                });
                                expande(2);
                            },
                            failure:function(){
                                form.setLoading(false);
                                Ext.Msg.show({
                                    title:'Error',
                                    msg: 'Error de comunicaci&oacute;n',
                                    buttons: Ext.Msg.OK,
                                    icon: Ext.Msg.ERROR
                                });
                            }
                        });
	            	}
	            	else
	            	{
	            		Ext.Msg.show({
	                        title:'Error',
	                        icon: Ext.Msg.WARNING,
	                        msg: 'Favor de llenar los campos requeridos',
	                        buttons: Ext.Msg.OK
	                    });
	            	}
	            }
            }
        ]
    });
    debug('3. Componentes definidos');
    /*/////////////////////*/
    ////// componentes //////
    /////////////////////////
    
    ///////////////////////
    ////// contenido //////
    /*///////////////////*/
    pantallaValositMainContent=new PanelValosit();
    pantallaValositMainContent.render('maindivpantallavalosit<s:property value='smap1.timestamp' />');
    debug('4. Contenido cargado');
    /*///////////////////*/
    ////// contenido //////
    ///////////////////////
    
    //////////////////////
    ////// cargador //////
    /*//////////////////*/
    Ext.define('LoaderForm',
    {
        extend:'ModeloValisit',
        proxy:
        {
            extraParams:
            {
            	'smap1.pv_cdunieco_i'  : pantallaValositInputCdunieco
            	,'smap1.pv_nmpoliza_i' : pantallaValositInputNmpoliza
            	,'smap1.pv_cdramo_i'   : pantallaValositInputCdramo
            	,'smap1.pv_estado_i'   : pantallaValositInputEstado
            	,'smap1.pv_nmsituac_i' : pantallaValositInputAgrupado=='si'?'1':pantallaValositInputNmsituac
            },
            type:'ajax',
            url : pantallaValositUrlLoad,
            reader:{
                type:'json'
            }
        }
    });

    var loaderForm=Ext.ModelManager.getModel('LoaderForm');
    loaderForm.load(123, {
        success: function(resp) {
            //console.log(resp);
            pantallaValositMainContent.loadRecord(resp);
        },
        failure:function()
        {
            Ext.Msg.show({
                title:'Error',
                icon: Ext.Msg.ERROR,
                msg: 'Error al cargar datos de cotizaci&oacute;n',
                buttons: Ext.Msg.OK
            });
        }
    });
    /*//////////////////*/    
    ////// cargador //////
    //////////////////////
});
</script>
<div id="maindivpantallavalosit<s:property value='smap1.timestamp' />" style="min-height:150px;"></div>