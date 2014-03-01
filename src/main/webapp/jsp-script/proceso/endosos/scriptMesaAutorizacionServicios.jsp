<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<s:if test="false">
<script>
</s:if>
debug('#################################################');
debug('###### scriptMesaAutorizacionServicios.jsp ######');
debug('#################################################');

///////////////////////
////// variables //////
var _4_urlAutorizarEndoso = '<s:url namespace="/endosos" action="autorizarEndoso" />';
var _4_authEndUrlDoc      = '<s:url namespace="/documentos" action="ventanaDocumentosPolizaClon" />';

var _4_urlPantallaAutServ = '<s:url namespace="/siniestros" action="autorizacionServicios" />';

var _UrlGenerarAutoServicio     = '<s:url namespace="/siniestros" action="generarAutoriServicio"       />';

var _4_selectedRecordEndoso;
var _4_windowAutorizarEndoso;
var _4_fieldComentAuthEndoso;

_4_botonesGrid.push(
{
    text     : 'Alta de tr&aacute;mite'
    ,icon    : '${ctx}/resources/fam3icons/icons/add.png'
    ,handler : function altaTramiteWindow(){
    	Ext.create("Ext.form.Panel").submit({url     : _4_urlPantallaAutServ,standardSubmit:true});
        /*windowLoader = Ext.create('Ext.window.Window',{
            modal       : true,
            buttonAlign : 'center',
            width       : 800,
            height      : 730,
            autoScroll  : true,
            loader      : {
                url     : _4_urlPantallaAutServ,
                scripts  : true,
                loadMask : true,
                autoLoad : true,
                ajaxOptions: {
                    method: 'POST'
                }
            }
        }).show();
        centrarVentana(windowLoader);*/
    }
});
////// variables //////
///////////////////////

///////////////////////
////// funciones //////
function _4_authEndosoDocumentos(grid,rowIndex,colIndex)
{
	var store=grid.getStore();
    var record=store.getAt(rowIndex);
    debug('record seleccionado',record);
    
    Ext.create('Ext.window.Window',
    {
        title        : 'Documentos del tr&aacute;mite '+record.get('ntramite')
        ,modal       : true
        ,buttonAlign : 'center'
        ,width       : 600
        ,height      : 400
        ,autoScroll  : true
        ,loader      :
        {
            url       : _4_authEndUrlDoc
            ,params   :
            {
                'smap1.nmpoliza'  : record.get('nmpoliza')
                ,'smap1.cdunieco' : record.get('cdunieco')
                ,'smap1.cdramo'   : record.get('cdramo')
                ,'smap1.estado'   : record.get('estado')
                ,'smap1.nmsuplem' : record.get('nmsuplem')
                ,'smap1.ntramite' : record.get('cdsucdoc')
                ,'smap1.nmsolici' : ''
                ,'smap1.tipomov'  : record.get('nombre')
                ,'smap1.cdtiptra' : 14
                ,'smap1.readOnly' : 'si'
            }
            ,scripts  : true
            ,autoLoad : true
        }
    }).show();
}

function _4_preAutorizarEndoso(grid,rowIndex,colIndex)
{
	var store=grid.getStore();
    var record=store.getAt(rowIndex);
    debug('record',record);
    
    _4_selectedRecordEndoso=record;
    _4_fieldComentAuthEndoso.setValue('');
    _4_windowAutorizarEndoso.show();
}

function _4_autorizarEndoso()
{
	var record=_4_selectedRecordEndoso;
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
		_4_windowAutorizarEndoso.hide();
		Ext.Ajax.request(
		{
			url       : _4_urlAutorizarEndoso
			,params   :
			{
				'smap1.ntramiteemi'  : record.get('parametros.pv_otvalor01')
				,'smap1.ntramiteend' : record.get('ntramite')
	            ,'smap1.cdunieco'    : record.get('cdunieco')
	            ,'smap1.cdramo'      : record.get('cdramo')
	            ,'smap1.estado'      : record.get('estado')
	            ,'smap1.nmpoliza'    : record.get('nmpoliza')
	            ,'smap1.nmsuplem'    : record.get('nmsuplem')
	            ,'smap1.nsuplogi'    : record.get('parametros.pv_otvalor04')
	            ,'smap1.cdtipsup'    : record.get('parametros.pv_otvalor02')
	            ,'smap1.status'      : '9'
	            ,'smap1.fechaEndoso' : Ext.Date.format(record.get('ferecepc'),'d/m/Y')
	            ,'smap1.observacion' : _4_fieldComentAuthEndoso.getValue()
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
					_4_windowAutorizarEndoso.show();
					mensajeError(json.error);
				}
			}
		    ,failure  : function()
		    {
		    	mcdinGrid.setLoading(false);
		    	_4_windowAutorizarEndoso.show();
		    	errorComunicacion();
		    }
		});
	}
	
}

function generaAutoriServicioWindow(grid,rowIndex,colIndex){
	
	var record = grid.getStore().getAt(rowIndex);
	
	msgWindow = Ext.Msg.show({
        title: 'Aviso',
        msg: '&iquest;Esta seguro que desea generar la Autorizaci&oacute;n de Servicio ?',
        buttons: Ext.Msg.YESNO,
        icon: Ext.Msg.QUESTION,
        fn: function(buttonId, text, opt){
        	if(buttonId == 'yes'){
        		Ext.Ajax.request({
					url: _UrlGenerarAutoServicio,
					params: {
							'paramsO.pv_ntramite_i' : record.get('ntramite'),
				    		'paramsO.pv_cdunieco_i' : record.get('cdunieco'),
				    		'paramsO.pv_cdramo_i'   : record.raw.cdramo,
				    		'paramsO.pv_estado_i'   : record.raw.estado,
				    		'paramsO.pv_nmpoliza_i' : record.get('nmpoliza'),
				    		'paramsO.pv_nmAutSer_i' : record.get('parametros.pv_otvalor01'),
				    		'paramsO.pv_cdperson_i' : record.get('parametros.pv_otvalor05')
				    	
					},
					success: function(response, opt) {
						var jsonRes=Ext.decode(response.responseText);

						if(jsonRes.success == true){
							var numRand=Math.floor((Math.random()*100000)+1);
				        	debug('numRand a: ',numRand);
				        	var windowVerDocu=Ext.create('Ext.window.Window',
				        	{
				        		title          : 'Autorizaci&oacute;n de Sevicio Siniestro'
				        		,width         : 700
				        		,height        : 500
				        		,collapsible   : true
				        		,titleCollapse : true
				        		,html          : '<iframe innerframe="'+numRand+'" frameborder="0" width="100" height="100"'
				        		                 +'src="'+panDocUrlViewDoc+'?idPoliza=' + record.get('ntramite') + '&filename=' + '<s:text name="siniestro.autorizacionServicio.nombre"/>' +'">'
				        		                 +'</iframe>'
				        		,listeners     :
				        		{
				        			resize : function(win,width,height,opt){
				                        debug(width,height);
				                        $('[innerframe="'+numRand+'"]').attr({'width':width-20,'height':height-60});
				                    }
				        		}
				        	}).show();
				        	windowVerDocu.center();
   						}else {
   							mensajeError(jsonRes.msgResult);
   						}
					},
					failure: function(){
						mensajeError('No se pudo generar contrarecibo.');
					}
				});
        	}
        	
        }
    });
	centrarVentana(msgWindow);
	
}
function _4_generarAutorizacion(grid,rowIndex)
{
	var record = grid.getStore().getAt(rowIndex);
	debug('_4_generarAutorizacion:',record.raw);
}
////// funciones //////
///////////////////////

Ext.onReady(function()
{
	/////////////////////////
	////// componentes //////
	_4_fieldComentAuthEndoso=Ext.create('Ext.form.field.TextArea',
	{
		width   : 280
		,height : 160
	});
	
	Ext.define('_4_WindowAutorizarEndoso',
	{
		extend         : 'Ext.window.Window'
		,initComponent : function()
		{
			debug('_4_WindowAutorizarEndoso initComponent');
			Ext.apply(this,
			{
				title        : 'Observaciones'
				,items       : _4_fieldComentAuthEndoso
				,modal       : true
				,buttonAlign : 'center'
				,width       : 300
				,height      : 250
				,closeAction : 'hide'
				,buttons     :
				[
				    {
				    	text     : 'Autorizar'
				    	,icon    : '${ctx}/resources/fam3icons/icons/key.png'
				    	,handler : _4_autorizarEndoso
				    }
				]
			});
			this.callParent();
		}
	});
    ////// componentes //////
	/////////////////////////
	
	///////////////////////
	////// contenido //////
	_4_windowAutorizarEndoso=new _4_WindowAutorizarEndoso();
    ////// contenido //////
    ///////////////////////
});
<s:if test="false">
</script>
</s:if>