<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
////// urls //////
var url_PantallaPreview         = '<s:url namespace="/endosos"          action="includes/previewEndosos"         />';
var _p44_urlConfirmar           = '<s:url namespace="/endosos"          action="confirmarEndosoValositFormsAuto" />';
var _p44_urlRecuperacionSimple  = '<s:url namespace="/emision"          action="recuperacionSimple"              />';
var _p30_urlViewDoc             = '<s:url namespace="/documentos"       action="descargaDocInline"               />';
var _p28_urlCargarSumaAsegurada = '<s:url namespace="/emision"          action="cargarSumaAseguradaAuto"         />';
var _RUTA_DOCUMENTOS_TEMPORAL = '<s:text name="ruta.documentos.temporal" />';
var _p44_urlRecuperaValoresModelo  = '<s:url namespace="/emision"       action="recuperacionSimple"            />';
////// urls //////

////// variables //////
var _p44_smap1 = <s:property value="%{convertToJSON('smap1')}" escapeHtml="false" />;
debug('_p44_smap1:',_p44_smap1);

var _p44_slist1 = <s:property value="%{convertToJSON('slist1')}" escapeHtml="false" />;
debug('_p44_slist1:',_p44_slist1);

var _p44_flujo = <s:property value="%{convertToJSON('flujo')}" escapeHtml="false" />;
debug('_p44_flujo:',_p44_flujo);

////// variables //////

////// overrides //////
////// overrides //////

////// componentes dinamicos //////
var _p44_items      = [];
var _p44_itemsArray = [];
<s:set var="contador" value="0" />
<s:iterator value="slist1">
    _p44_itemsArray['i0000<s:property value="slist1[#contador].NMSITUAC" />']=[ <s:property value='imap.get("items"+slist1[#contador].NMSITUAC)' /> ];
    <s:set var="contador" value="#contador+1" />
</s:iterator>
////// componentes dinamicos //////

Ext.onReady(function()
{
	
	Ext.Ajax.timeout = 1*60*60*1000; // 1 hora
	
    ////// modelos //////
    ////// modelos //////
    
    ////// stores //////
    ////// stores //////
    
    ////// componentes //////
    for(var nmsituac in _p44_itemsArray)
    {
        var indice = Number(nmsituac.slice(-4));
        _p44_items.push(Ext.create('Ext.form.Panel',
        {
            title     : 'INCISO '+indice
            ,itemId   : '_p44_formInciso'+indice
            ,nmsituac : indice
            ,defaults : { style : 'margin:5px;' }
            ,layout   :
            {
                type     : 'table'
                ,columns : 3
            }
            ,items : _p44_itemsArray[nmsituac]
        }));
    }
    debug('_p44_items:',_p44_items);
    ////// componentes //////
    
    ////// contenido //////
    Ext.create('Ext.panel.Panel',
    {
        renderTo    : '_p44_divpri'
        ,itemId     : '_p44_panelpri'
        ,defaults   : { style : 'margin:5px;' }
        ,maxHeight  : 750
        ,autoScroll : true
        ,items      : _p44_items
        ,bbar       :
        [
            '->'
            ,{
                xtype     : 'form'
                ,layout   : 'hbox'
                ,defaults : { style : 'margin:5px;' }
                ,items    :
                [
                    {
		                xtype       : 'datefield'
		                ,itemId     : '_p44_fechaCmp'
		                ,format     : 'd/m/Y'
		                ,fieldLabel : 'Fecha de efecto'
		                ,value      : new Date()
		                ,allowBlank : false
                    }
                    ,{
                        xtype : 'button'
                        ,text     : 'Emitir'
		                ,itemId  : '_p44_botonConfirmar'
		                ,icon    : '${ctx}/resources/fam3icons/icons/key.png'
		                ,handler : function(me)
		                {
		                    var forms = Ext.ComponentQuery.query('form[nmsituac]',_fieldById('_p44_panelpri'));
		                    debug('forms:',forms);
		                    
		                    for(var i in forms)
		                    {
		                        if(!forms[i].getForm().isValid())
		                        {
		                            mensajeWarning('Verificar inciso '+forms[i].nmsituac);
		                            return;
		                        }
		                    }
		                    
		                    if(!_fieldById('_p44_fechaCmp').isValid())
		                    {
		                        mensajeWarning('Verificar fecha');
		                        return;
		                    }
		                    
		                    var json =
		                    {
		                        smap1   : _p44_smap1
		                        ,slist1 : []
		                    };
		                    
		                    json.smap1['feinival'] = Ext.Date.format(_fieldById('_p44_fechaCmp').getValue(),'d/m/Y');
		                    
		                    for(var i in forms)
		                    {
		                        var form   = forms[i];
		                        var inciso =
		                        {
		                            cdunieco  : form.down('[name=CDUNIECO]').getValue()
		                            ,cdramo   : form.down('[name=CDRAMO]').getValue()
		                            ,estado   : form.down('[name=ESTADO]').getValue()
		                            ,nmpoliza : form.down('[name=NMPOLIZA]').getValue()
		                            ,nmsituac : form.down('[name=NMSITUAC]').getValue()
		                            ,nmsuplem : form.down('[name=NMSUPLEM]').getValue()
		                            ,cdtipsit : form.down('[name=CDTIPSIT]').getValue()
		                        };
		                        var items = Ext.ComponentQuery.query('[name][hidden=false][readOnly=false]',form);
		                        debug('items:',items);
		                        for(var j in items)
		                        {
		                            var item = items[j];
		                            var dis  = false;
		                            if(item.isDisabled())
		                            {
		                                dis=true;
		                                item.enable();
		                            }
		                            inciso['OTVALOR'+item.orden] = item.getValue();
		                            if(dis)
		                            {
		                                item.disable();
		                            }
		                        }
		                        if(_p44_smap1.cdtipsup==TipoEndoso.EndosoCambioModelo){
     							 	debug('Entro a Cambio Modelo Vehiculo');
		                        	if(item.value!=item.valorInicial){
		                        		debug('Entro a insertar en slist1');
		                        		json.slist1.push(inciso);
		                        	}
		                        }else if(_p44_smap1.cdtipsup==TipoEndoso.EndosoCambioDescripcion
		                                 ||_p44_smap1.cdtipsup==TipoEndoso.EndosoCambioTipoCarga ){
     							 	debug('Entro a Cambio Descripcion Vehiculo');
		                        	if(item.value!=item.valorInicial){
		                        		debug('Entro a insertar en slist1');
		                        		json.slist1.push(inciso);
		                        	}
		                        }else{
		                        	//if(item.value!=item.valorInicial){
		                        		debug('Entro a insertar en slist1');
		                        		json.slist1.push(inciso);
		                        	//}
		                        }
		                        //json.slist1.push(inciso);
		                    }
		                    
		                    if(!Ext.isEmpty(_p44_flujo))
		                    {
		                        json.flujo = _p44_flujo;
		                    }
		                    
		                    debug('json a enviar:',json);
		                    
		                    me.disable();
		                    me.setText('Cargando...');
		                    json.smap1['confirmar'] = 'no';
		                    
		                    /*if(!Ext.isEmpty(_p44_flujo))
                            {
                                json.flujo = _p44_flujo;
                            }*/
                            
		                    Ext.Ajax.request(
		                    {
		                        url       : _p44_urlConfirmar
		                        ,jsonData : json
		                        ,success  : function(response)
		                        {
		                        	var json2 = Ext.decode(response.responseText);
		                        	debug('### response: ',response);
		                        	debug('### json2: ',json2);
		                        	if(json2.success==true){
		                        		debug('Entrando a respuesta success correcto');
		                        		if(!Ext.isEmpty(json2) && !Ext.isEmpty(json2.smap2) && !Ext.isEmpty(json2.smap2.pv_tarifica) && json2.smap2.pv_tarifica == 'SI'){
                                            var win = Ext.create('Ext.window.Window',
                                                        {
                                                            title        : 'Tarifa final'
                                                            ,id          : 'tarifa'
                                                            ,autoScroll  : true
                                                            ,modal       : true
                                                            ,buttonAlign : 'center'
                                                            ,width       : 600
                                                            ,height      : 550
                                                            ,defaults    : { width: 650 }
                                                            ,closable    : false
                                                            ,autoScroll  : true
                                                            ,loader      :
                                                                {
                                                                    url       : url_PantallaPreview
                                                                    ,params   :
                                                                        {
                                                                            'smap4.nmpoliza'  : _p44_smap1.NMPOLIZA
                                                                            ,'smap4.cdunieco' : _p44_smap1.CDUNIECO
                                                                            ,'smap4.cdramo'   : _p44_smap1.CDRAMO
                                                                            ,'smap4.estado'   : _p44_smap1.ESTADO
                                                                            ,'smap4.nmsuplem' : json2.smap2.pv_nmsuplem_o
                                                                            ,'smap4.nsuplogi' : json2.smap2.pv_nsuplogi_o
                                                                        }
                                                                    ,scripts  : true
                                                                    ,autoLoad : true
                                                                 }
                                                            ,buttons:[{
			                                                			itemId    : '_p3_botonEnviar'
								                                        ,xtype    : 'button'
								                                        ,text     : 'Enviar'
								                                        ,icon     : '${ctx}/resources/fam3icons/icons/email.png'
								                                        //,disabled : true
								                                        ,handler  : function(){
								                                        	
																			_p3_cargarCorreos(_p44_flujo.ntramite)
								                                        	
								                                        	_p3_enviar(_p44_flujo.ntramite
			                    														,json2.smap2.pdfEndosoNom_o);
								                                        } 
			                                                			
			                                                		},{
                                                                        text    : 'Confirmar endoso'
                                                                        ,name    : 'endosoButton'
                                                                        ,icon    : '${ctx}/resources/fam3icons/icons/award_star_gold_3.png'
                                                                        ,handler : 
                                                                            function (me){
                                                                                me.up('window').destroy();
                                                                                confirmar();
                                                                           }
                                                                           
                                                                       },
                                                                       {
                                                                        text    : 'Cancelar'
                                                                        ,icon    : '${ctx}/resources/fam3icons/icons/cancel.png'
                                                                        ,handler : function (me){
                                                                                        me.up('window').destroy();
                                                                                        marendNavegacion(2);
                                                                                        }
                                                                     },{
                                                                        text    : 'Documentos'
                                                                        ,name    : 'documentoButton'
                                                                        ,icon    : '${ctx}/resources/fam3icons/icons/printer.png'
                                                                        ,handler  :function(){
                                                                     var numRand=Math.floor((Math.random()*100000)+1);
                                                                     debug(numRand);
                                                                     centrarVentanaInterna(Ext.create('Ext.window.Window', {
                                                                        title          : 'Vista previa'
                                                                        ,width         : 700
                                                                        ,height        : 500
                                                                        ,collapsible   : true
                                                                        ,titleCollapse : true
                                                                        ,html          : '<iframe innerframe="'+numRand+'" frameborder="0" width="100" height="100"'
                                                                                         +'src="'+_p30_urlViewDoc+"?&path="+_RUTA_DOCUMENTOS_TEMPORAL+"&filename="+json2.smap2.pdfEndosoNom_o+"\">"
                                                                                         +'</iframe>'
                                                                        ,listeners     : {
                                                                            resize : function(win,width,height,opt){
                                                                                debug(width,height);
                                                                                $('[innerframe="'+numRand+'"]').attr({'width':width-20,'height':height-60});
                                                                        }
                                                                      }}).show());
                                                                }
                                                                        ,hidden   : _p44_smap1.TIPOFLOT!= TipoFlotilla.Flotilla? false :true
                                                                        } ]
                                                         });
                                            win.show();
                                        } else {
                                            debug('confirmando directamente');
                                            confirmar();
                                        }
		                        	} else {
		                        		debug('Entrando a respuesta de error: ',json2.respuesta);
		                        		mensajeError(json2.respuesta);
		                        	}
								}
		                        ,failure  : function()
		                        {
		                            me.setText('Confirmar');
		                            me.enable();
		                            errorComunicacion();
		                        }
		                        
		                    }); 
		                    
		                    function confirmar(){
		                    	
		                    	// Se crea variable para turnar cuando sea un endoso con autorizacion
				            	var _p44_flujoAux = {};
				            	
				            	if(!Ext.isEmpty(_p44_flujo)
								    &&!Ext.isEmpty(_p44_flujo.aux)){
								    	//
									    try{
									    	//
									        _p44_flujoAux = Ext.decode(_p44_flujo.aux);
									    }
									    catch(e) {
									    	//
									        manejaException(e);
									    }
								}
					            
					            
					            //Validacion para cuando es un endsoso con autorizacion.
					            if(Ext.isEmpty(_p44_flujo)
				                    ||Ext.isEmpty(_p44_flujo.aux)
				                    ||_p44_flujo.aux.indexOf('onComprar')==-1){
					            	
					            	json.smap1['confirmar'] = 'si';  
					            }
					            	    
                                                        
                                   
                                                        
                                if(!Ext.isEmpty(_p44_flujo))
                                {
                                    json.flujo = _p44_flujo;
                                }
                                
                                Ext.Ajax.request(
                                    {
                                        url       : _p44_urlConfirmar
                                        ,jsonData : json
                                        ,success  : function(response){
                                        	
                                            var json3 = Ext.decode(response.responseText);
                                            debug('### confirmar json3:',json3);
                                            if(json3.success){
                                            	//Se agrega condicion para Emitir endoso DIrectamente
                                            	
                                            	if(Ext.isEmpty(_p44_flujo)
								                    ||Ext.isEmpty(_p44_flujo.aux)
								                    ||_p44_flujo.aux.indexOf('onComprar')==-1){ 
                                            		//
                                            		var callbackRemesa = function(){
	                                                    marendNavegacion(2);
	                                                };
	                                                mensajeCorrecto('Endoso generado','Endoso generado',function(){
	                                                    _generarRemesaClic(
	                                                        true
	                                                        ,_p44_smap1.CDUNIECO
	                                                        ,_p44_smap1.CDRAMO
	                                                        ,_p44_smap1.ESTADO
	                                                        ,_p44_smap1.NMPOLIZA
	                                                        ,callbackRemesa
	                                                    );
	                                                });
                                            	}else{// if(_p3_flujoAux.endosoAutorizar==='onComprar_160'){
								                    //si el flujo tiene este comodin ejecutaremos un turnado con el status indicado
							                    	debug('_p44_flujoAux.endosoAutorizar: ',_p44_flujoAux.endosoAutorizar);
								                    var ck = 'Turnando tr\u00e1mite';
								                    try
								                    {
								                        var status = _p44_flujoAux.endosoAutorizar.split('_')[1];
								                        debug('status para turnar onComprar:',status,'.');
								                        
								                        _mask(ck);
								                        Ext.Ajax.request(
								                        {
								                            url      : _GLOBAL_COMP_URL_TURNAR
								                            ,params  :
								                            {
								                                'params.CDTIPFLU'   : _p44_flujo.cdtipflu
								                                ,'params.CDFLUJOMC' : _p44_flujo.cdflujomc
								                                ,'params.NTRAMITE'  : _p44_flujo.ntramite
								                                ,'params.STATUSOLD' : _p44_flujo.status
								                                ,'params.STATUSNEW' : status
								                                ,'params.COMMENTS'  : 'Tr\u00e1mite cotizado'
								                                ,'params.SWAGENTE'  : 'S'
								                            }
								                            ,success : function(response)
								                            {
								                                _unmask();
								                                var ck = '';
								                                try
								                                {
								                                    var json = Ext.decode(response.responseText);
								                                    debug('### turnar:',json);
								                                    if(json.success)
								                                    {
								                                        mensajeCorrecto
								                                        (
								                                            'Tr\u00e1mite turnado'
								                                            //,json.message
								                                            ,'El tr\u00e1mite fue turnado para aprobaci\u00f3n del agente/promotor'
								                                            ,function()
								                                            {
								                                                _mask('Redireccionando');
								                                                Ext.create('Ext.form.Panel').submit(
								                                                {
								                                                    url             : _GLOBAL_COMP_URL_MCFLUJO
								                                                    ,standardSubmit : true
								                                                });
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
								                                errorComunicacion(null,'Error al turnar tr\u00e1mite');
								                            }
								                        });
								                    }
								                    catch(e)
								                    {
								                        manejaException(e,ck);
								                    }
								               
								               //Sacaendoso
								                    
							                    sacaEndoso(_p44_smap1.CDUNIECO,
							                               _p44_smap1.CDRAMO,
							                               _p44_smap1.ESTADO,
							                               _p44_smap1.NMPOLIZA,
							                               json3.smap2.pv_nmsuplem_o,
							                               json3.smap2.pv_nsuplogi_o);
						                
						                    }
                                                
                                            }
                                            else
                                            {
                                                mensajeError(json3.respuesta);
                                            }
                                        }
                                        ,failure  : function()
                                        {
                                            //me.setText('Confirmar');
                                            //me.enable();
                                            errorComunicacion();
                                        }
                                    });
		                    }
		                    /**/
		                   
		                }
                    }
                ]
            }
            ,'->'
        ]
    });
    ////// contenido //////
    
    ////// custom //////
    ////// custom //////
    
    ////// loaders //////
    if(_p44_smap1.cdtipsup==TipoEndoso.EndosoCambioModelo 
     ||_p44_smap1.cdtipsup==TipoEndoso.EndosoCambioDescripcion){
     	//codigo cuando es un endoso de Cambio de Modelo o Endoso de Camdio de Descripcion
        debug('Entrando a Endoso Cambio Descripcion y Modelo Vehiculo');  
        
     	var forms = Ext.ComponentQuery.query('form[nmsituac]',_fieldById('_p44_panelpri'));
    	debug('forms:',forms);
    	for(var i in _p44_slist1)
		    {
		        var inciso   = _p44_slist1[i];
		        var nmsituac = inciso.NMSITUAC;
		        var form     = _fieldById('_p44_formInciso'+nmsituac);
		        debug('form:',form);
		        var items    = Ext.ComponentQuery.query('[name]',form);
		        debug('items:',items);
		        
		        if(items.length==0)
		        {
		            mensajeError('El endoso no aplica para el inciso '+form.nmsituac);
		            _fieldById('_p44_botonConfirmar').disable();
		            _fieldById('_p44_botonConfirmar').setText('No aplica');
		        }
		        
		        for(var j in items)
		        {
		            var item = items[j];
		            item.setValue(inciso[item.name]);
		            item.valorInicial = inciso[item.name];
		            
		            if(!Ext.isEmpty(item.store))
		            {
		                item.store.proxy.extraParams['params.cdunieco'] = inciso.CDUNIECO;
		                item.store.proxy.extraParams['params.cdramo']   = inciso.CDRAMO;
		                item.store.proxy.extraParams['params.estado']   = inciso.ESTADO;
		                item.store.proxy.extraParams['params.nmpoliza'] = inciso.NMPOLIZA;
		                item.store.proxy.extraParams['params.nmsituac'] = inciso.NMSITUAC;
		                item.store.proxy.extraParams['params.nmsuplem'] = inciso.NMSUPLEM;
		                item.store.proxy.extraParams['params.cdtipsit'] = inciso.CDTIPSIT;
		                debug('item con store:',item);
		                
		                    item.store.load();
		            }
		        }
		    }
		    if(_p44_smap1.cdtipsup==TipoEndoso.EndosoCambioModelo){
		    	//
		    	var  maxModelo = 0;
				var  minModelo = 0;
		    	   Ext.Ajax.request(
					    {
					        url      : _p44_urlRecuperaValoresModelo
					        ,params  :
					        {
					            'smap1.procedimiento' : 'RECUPERAR_VALORES_MODELO'
					            ,'smap1.cdunieco'     : _p44_smap1.CDUNIECO
					        }
					        ,success : function(response)
					        {
					            var json = Ext.decode(response.responseText);
					            debug('### RECUPERAR_VALORES_MODELO:',json);
					            if(json.exito)
					            {
					            	debug('json.smap1.MaxModelo',json.smap1.MaxModelo);
					                maxModelo = Number(json.smap1.MaxModelo);
			    	                minModelo = Number(json.smap1.MinModelo);
			    	                mensajeCorrecto('Aviso','El Endoso Cambio de Modelo aplica para menor a: '+minModelo+' años y mayor a: '+maxModelo+' años..   ');
					            }
					            else
					            {
					                mensajeError(json.respuesta);
					            }
					        }
					        ,failure : function()
					        {
					            errorComunicacion();
					        }
					    });
		    	//var xxx = Ext.ComponentQuery.query('[fieldLabel=MODELO]');
		    	var actual = form.down('[fieldLabel=MODELO]').rawValue;
		    	debug('xxx ',form.down('[fieldLabel=MODELO]').rawValue);
		    	debug('Entro a cambio de Modelo');
		    //	mensajeCorrecto('Aviso','El Endoso Cambio de Modelo aplica para menor de: '+(+actual + +minModelo)+' años o mayor a: '+(maxModelo)+' años   ');
		    	Ext.ComponentQuery.query('[fieldLabel=MODELO]',_fieldById('_p44_panelpri')).forEach(function(it){ 
			                                                                                            it.on({change:function( me, newValue, oldValue){
			                                                                                            	          
			                                                                                            	            me.validator=function(val){
			                                                                                            	            	
	                                                                                            	            				if(val>Number(me.valorInicial)+Number(maxModelo) || val<Number(me.valorInicial)-Number(minModelo)){
			                                                                                            	            			debug('invalido');
			                                                                                            	            			return "Invalido, Valor Original: "+ me.valorInicial;
			                                                                                            	            		}
			                                                                                            	            		debug('Validos');
			                                                                                            	            		
			                                                                                            	            		return true;
			                                                                                            	            }
			                                                                                                           }
			                                                                                                   })
                                                                                                   });
		    }
     }else{
     	var forms = Ext.ComponentQuery.query('form[nmsituac]',_fieldById('_p44_panelpri'));
    	debug('forms:',forms);
    	for(var i in _p44_slist1)
		    {
		        var inciso   = _p44_slist1[i];
		        var nmsituac = inciso.NMSITUAC;
		        var form     = _fieldById('_p44_formInciso'+nmsituac);
		        debug('form:',form);
		        var items    = Ext.ComponentQuery.query('[name]',form);
		        debug('items:',items);
		        
		        if(items.length==0)
		        {
		            mensajeError('El endoso no aplica para el inciso '+form.nmsituac);
		            _fieldById('_p44_botonConfirmar').disable();
		            _fieldById('_p44_botonConfirmar').setText('No aplica');
		        }
		        
		        for(var j in items)
		        {
		            var item = items[j];
		            item.setValue(inciso[item.name]);
		            item.valorInicial = inciso[item.name];
		            
		            if(item.hidden==false&&item.readOnly==false)
		            {
			            if(_p44_smap1.TIPO_VALIDACION=='MENOR')
			            {
			                item.validator = function()
			                {
			                    var val = this.getValue();
			                    debug('validator-:',val);
			                    if(Number(this.valorInicial)<Number(val))
			                    {
			                        return 'Valor m&aacute;ximo '+this.valorInicial;
			                    }
			                    return true;
			                };
			            }
			            else if(_p44_smap1.TIPO_VALIDACION=='MAYOR')
			            {
			                item.validator = function()
			                {
			                    var val = this.getValue();
			                    debug('validator+:',val);
			                    if(Number(this.valorInicial)>Number(val))
			                    {
			                        return 'Valor m&iacute;nimo '+this.valorInicial;
			                    }
			                    return true;
			                };
			            }
		            }
		            
		            item.extraParams =
		            {
		                'cdunieco'  : inciso.CDUNIECO
		                ,'cdramo'   : inciso.CDRAMO
		                ,'estado'   : inciso.ESTADO
		                ,'nmpoliza' : inciso.NMPOLIZA
		                ,'nmsituac' : inciso.NMSITUAC
		                ,'nmsuplem' : inciso.NMSUPLEM
		                ,'cdtipsit' : inciso.CDTIPSIT
		            };
		            
		            if(item.param1=='amparado')
		            {
		                item.disable();
		                try
		                {
		                    var params =
		                    {
		                        'smap1.procedimiento' : 'RECUPERAR_TATRISIT_AMPARADO'
		                        ,'smap1.cdatribu'     : item.value1
		                        ,'smap1.compId'       : item.id
		                    };
		                    for (var i in item.extraParams)
		                    {
		                        params['smap1.'+i] = item.extraParams[i];
		                    } 
		                    Ext.Ajax.request(
		                    {
		                        url      : _p44_urlRecuperacionSimple
		                        ,params  : params
		                        ,success : function(response)
		                        {
		                            try
		                            {
		                                var json = Ext.decode(response.responseText);
		                                debug('### amparada:',json);
		                                if(json.exito)
		                                {
		                                    if(json.smap1.CONTEO>0)
		                                    {
		                                        Ext.getCmp(json.smap1.compId).enable();
		                                    }
		                                }
		                                else
		                                {
		                                    mensajeError(json.respuesta);
		                                    _fieldById('_p44_botonConfirmar').disable();
		                                    _fieldById('_p44_botonConfirmar').setText('Verificacion de cobertura no exitosa');
		                                }
		                            }
		                            catch(e)
		                            {
		                                manejaException(e,'descodificando cobertura amparada');
		                                _fieldById('_p44_botonConfirmar').disable();
		                                _fieldById('_p44_botonConfirmar').setText('Error al decodificar verificacion de coberturas');
		                            }
		                        }
		                        ,failure : function()
		                        {
		                            errorComunicacion();
		                            _fieldById('_p44_botonConfirmar').disable();
		                            _fieldById('_p44_botonConfirmar').setText('Error al solicitar verificacion de coberturas');
		                        }
		                    });
		                }
		                catch(e)
		                {
		                    manejaException(e,'Verificando cobertura amparada');
		                    _fieldById('_p44_botonConfirmar').disable();
		                    _fieldById('_p44_botonConfirmar').setText('Error al verificar coberturas');
		                }
		            }
		            
		            if(!Ext.isEmpty(item.store))
		            {
		                item.store.proxy.extraParams['params.cdunieco'] = inciso.CDUNIECO;
		                item.store.proxy.extraParams['params.cdramo']   = inciso.CDRAMO;
		                item.store.proxy.extraParams['params.estado']   = inciso.ESTADO;
		                item.store.proxy.extraParams['params.nmpoliza'] = inciso.NMPOLIZA;
		                item.store.proxy.extraParams['params.nmsituac'] = inciso.NMSITUAC;
		                item.store.proxy.extraParams['params.nmsuplem'] = inciso.NMSUPLEM;
		                item.store.proxy.extraParams['params.cdtipsit'] = inciso.CDTIPSIT;
		                debug('item con store:',item);
		                
		                if(Number(_p44_smap1.cdtipsup)==43
		                    &&'|AR|CR|PC|PP|'.lastIndexOf('|'+inciso.CDTIPSIT+'|')!=-1
		                    &&item.name=='CVE_TIPO_USO'
		                    )
		                {
		                    debug('@CUSTOM TIPO USO:',item);
		                    item.anidado                                     = true;
		                    item.store.proxy.extraParams['params.cdnegocio'] = inciso.CVE_NEGOCIO+'';
		                    item.store.padre                                 = item;
		                    item.padre                                       = items[j-1];
		                    item.heredar                                     = function()
		                    {
		                        this.store.load(
		                        {
		                            params :
		                            {
		                                'params.servicio' : this.padre.getValue()
		                            }
		                        });
		                    };
		                    
		                    item.store.on(
		                    {
		                        load : function(me)
		                        {
		                            var padre = me.padre;
		                            var value = padre.getValue();
		                            if(!Ext.isEmpty(value))
		                            {
		                                var dentro = false;
		                                me.each(function(record)
		                                {
		                                    if(value==record.get('key'))
		                                    {
		                                        dentro=true;
		                                    }
		                                });
		                                if(!dentro)
		                                {
		                                    padre.clearValue();
		                                }
		                            }
		                        }
		                    });
		                    
		                    item.padre.hijo = item;
		                    item.padre.on(
		                    {
		                        select : function(me)
		                        {
		                            me.hijo.heredar();
		                        }
		                    });
		                    debug('item:',item);
		                    
		                    item.heredar(true);
		                }
		                //para todos los demas que requieren los atributos extras puestos arriba (deberan venir con autoload=false)
		                else if(item.store.autoLoad==false)
		                {
		                    item.store.load();
		                }
		            }
		            
		            try{
		            	
		            	// FILTRO DE VALORES PARA SERVICIO PUBLICO
			            if(_p44_smap1.CDTIPSIT==TipoSituacion.ServicioPublicoAuto && 
			            		item.fieldLabel=='VALOR COMERCIAL' &&
			            		(_p44_smap1.cdtipsup == TipoEndoso.SumaAseguradaDecremento ||
			            		_p44_smap1.cdtipsup == TipoEndoso.SumaAseguradaIncremento )
			               ){
			            	item.enable();
			            	_0_obtenerSumaAseguradaRamo6(inciso.CVE_MODELO.substr(inciso.CVE_MODELO.length-4,4)
			            								,inciso.CVE_VERSION
			                                            ,inciso.CDRAMO
			                                            ,inciso.CDTIPSIT
			                                            ,item);
			            	item.setMaxValue(2400000);
			            }
			            
			            if(_p44_smap1.CDRAMO==Ramo.ServicioPublico ){
			                if(_p44_smap1.cdtipsup == TipoEndoso.SumaAseguradaDecremento ||
				   	           _p44_smap1.cdtipsup == TipoEndoso.SumaAseguradaIncremento ){
				            	if(item.store ){
				            			if(_p44_smap1.CDTIPSIT==TipoSituacion.ServicioPublicoAuto)
					            			switch(item.cdatribu){
						            			case 'CVE_DEDUCIBLE_RESP__CIVIL_': 
						            				item.getStore().filter([{filterFn: function(item) {
						            					return item.get("key")>=500000 && item.get("key")<=5000000;
						            				}}])
								            		break;
						            			case 'CVE_DEDUCIBLE_RESP__CIVIL_VIAJERO':
						            				item.getStore().filter([{filterFn: function(item) {
						            					return item.get("key")>=1500 && item.get("key")<=20000;
						            				}}])
						            				break;
						            			case 'CVE_SUMA_ASEGURADA_GASTOS_MEDICOS':
						            				item.getStore().filter([{filterFn: function(item) {
						            					return item.get("key")>=10000 && item.get("key")<=200000;
						            				}}])
						            				break;
						            			case 'CVE_MUERTE_ACCIDENTAL_CONDUCTOR':
						            				item.getStore().filter([{filterFn: function(item) {
						            					return item.get("key")>=30000 && item.get("key")<=100000;
						            				}}])
						            				break;
					            			}
				            			else if(_p44_smap1.CDTIPSIT==TipoSituacion.ServicioPublicoMicro)
				            				switch(item.cdatribu){
				            				case 'CVE_VALOR_COMERCIAL':
				            					item.setMaxValue(4200000);
				            					item.setMinValue(50000);
				            					break;
					            			case 'CVE_DEDUCIBLE_RESP__CIVIL_': 
					            				item.getStore().filter([{filterFn: function(item) {
					            					return item.get("key")>=500000 && item.get("key")<=5000000;
					            				}}])
							            		break;
					            			case 'CVE_DEDUCIBLE_RESP__CIVIL_VIAJERO':
					            				item.getStore().filter([{filterFn: function(item) {
					            					return item.get("key")>=1500 && item.get("key")<=20000;
					            				}}])
					            				break;
					            			case 'CVE_SUMA_ASEGURADA_GASTOS_MEDICOS':
					            				item.getStore().filter([{filterFn: function(item) {
					            					return item.get("key")>=10000 && item.get("key")<=200000;
					            				}}])
					            				break;
					            			case 'CVE_MUERTE_ACCIDENTAL_CONDUCTOR':
					            				item.getStore().filter([{filterFn: function(item) {
					            					return item.get("key")>=30000 && item.get("key")<=100000;
					            				}}])
					            				break;
				            			}
				            	}
			                }
			                if(_p44_smap1.cdtipsup == TipoEndoso.DeducibleMas ||
			 		   	       _p44_smap1.cdtipsup == TipoEndoso.DeducibleMenos ){
			                	//alert()
			                	if(item.store ){
				                	switch(item.cdatribu){
				                		case 'CVE_DEDUCIBLE_DANOS_MATERIALES':
				                			item.getStore().filter([{filterFn: function(item) {
				            					return item.get("key")>=5 && item.get("key")<=20;
				            				}}])
				                			break;
				                		case 'CVE_DEDUCIBLE_ROBO_TOTAL':
				                			item.getStore().filter([{filterFn: function(item) {
				            					return item.get("key")>=10 && item.get("key")<=20;
				            				}}])
				                			break;
				                		case 'CVE_DEDUCIBLE_RESP__CIVIL':
				                			item.getStore().filter([{filterFn: function(item) {
				            					return item.get("key")>=25 && item.get("key")<=300;
				            				}}])
				                			break;
				                		case 'CVE_DEDUCIBLE_RESP__CIVIL_VIAJERO':
				                			item.getStore().filter([{filterFn: function(item) {
				            					return item.get("key")>=0 && item.get("key")<=8;
				            				}}])
				                			break;
				                	}
			                	}
			                }
			            	
			            }
			            
		            }catch(e){
		            	debugError(e);
		            }
		            
		        }
		    }
     	
     }
    
    
    Ext.Ajax.request(
    {
        url      : _p44_urlRecuperacionSimple
        ,params  :
        {
            'smap1.procedimiento' : 'RECUPERAR_FECHAS_LIMITE_ENDOSO'
            ,'smap1.cdunieco'     : _p44_smap1.CDUNIECO
            ,'smap1.cdramo'       : _p44_smap1.CDRAMO
            ,'smap1.estado'       : _p44_smap1.ESTADO
            ,'smap1.nmpoliza'     : _p44_smap1.NMPOLIZA
            ,'smap1.cdtipsup'     : _p44_smap1.cdtipsup
        }
        ,success : function(response)
        {
            var json = Ext.decode(response.responseText);
            debug('### fechas:',json);
            if(json.exito)
            {
                _fieldById('_p44_fechaCmp').setMinValue(json.smap1.FECHA_MINIMA);
                _fieldById('_p44_fechaCmp').setMaxValue(json.smap1.FECHA_MAXIMA);
                _fieldById('_p44_fechaCmp').setReadOnly(json.smap1.EDITABLE=='N');
                _fieldById('_p44_fechaCmp').isValid();
            }
            else
            {
                mensajeError(json.respuesta);
            }
        }
        ,failure : function()
        {
            errorComunicacion();
        }
    });
    
    
 
  
    
    ////// loaders //////
});

////// funciones //////

function _0_obtenerSumaAseguradaRamo6(modelo,version,cdramo,cdtipsit,valorComercial)
{
//     _0_panelPri.setLoading(true);
   // var loading_0_obtenerSumaAseguradaRamo6 = _maskLocal();
    Ext.Ajax.request(
    {
        url      : _p28_urlCargarSumaAsegurada
        ,params  :
        {
            'smap1.modelo'    : modelo
            ,'smap1.version'  : version
            ,'smap1.cdsisrol' : _GLOBAL_CDSISROL
            ,'smap1.cdramo'   : cdramo
            ,'smap1.cdtipsit' : cdtipsit
        }
        ,success : function(response)
        {

//           _0_panelPri.setLoading(false); 
            
            var json=Ext.decode(response.responseText);
            debug('### json response obtener suma asegurada:',json);
            if(json.exito)
            {
                	valorComercial.setMinValue(json.smap1.SUMASEG);            
            }
            else 
            {
            	
            	valorComercial.setMinValue(1);  
                //mensajeWarning(json.respuesta);
            }
        }
        ,failure : function()
        {
//             _0_panelPri.setLoading(false);
         
            errorComunicacion();
        }
    });
}

////// funciones //////}


function _p3_cargarCorreos(ntramite)
{
    debug('>_p3_idInputCorreo');
    Ext.Ajax.request(
    {
        url     : _GLOBAL_RECUPERA_CORREO
        ,params :
        {
            'smap1.ntramite'    : ntramite
        }
        ,success : function(response) {
            var json = Ext.decode(response.responseText);
            debug('### json cargarCorreos:',json);
            
            if(json.exito)
            {
            	  debug('>_p3_idInputCorreo 1 ', json.respuesta);
            	  _fieldById('_p3_idInputCorreo').setValue(json.respuesta);
            }
            else{
            	  debug('>_p3_idInputCorreo 2');
            }
         }
         ,failure : function(){
         	me.setLoading(false);
            errorComunicacion();
         }
    })
}

function _p3_enviar(ntramite
                    ,nomArchivo)
{
    debug('>_p28_enviar');
    
    centrarVentanaInterna(Ext.create('Ext.window.Window',
    {
        title        : 'Enviar cotizaci&oacute;n'
        ,width       : 550
        ,modal       : true
        ,height      : 150
        ,buttonAlign : 'center'
        ,bodyPadding : 5
        ,items       :
        [
            {
                xtype       : 'textfield'
                ,itemId     : '_p3_idInputCorreo'
                ,id         : '_p3_idInputCorreos'
                ,fieldLabel : 'Correo(s)'
                ,emptyText  : 'Correo(s) separados por ;'
                ,labelWidth : 100
                ,allowBlank : false
                ,blankText  : 'Introducir correo(s) separados por ;'
                ,width      : 500
                ,listeners  : {
                	boxready : function(){
                		
                		debug('Saliendo de la funcion ', _fieldById('_p3_idInputCorreo').getValue());
                	}
                }		
                	
            }
        ]
        ,buttons :
        [
            {
                text     : 'Enviar'
                ,icon    : '${ctx}/resources/fam3icons/icons/accept.png'
                ,handler : function()
                {
                    var me = this;
                    if (_fieldById('_p3_idInputCorreo').getValue().length > 0
                            &&_fieldById('_p3_idInputCorreo').getValue() != 'Correo(s) separados por ;')
                    {
                        debug('Se va a enviar cotizacion');
                        //me.up().up().setLoading(true);
                        
                        envioCorreo(_RUTA_DOCUMENTOS_TEMPORAL
				                    ,ntramite
				                    ,nomArchivo
				                    ,_fieldById('_p3_idInputCorreo').getValue());
				                    
				        //
				        this.up().up().destroy();
                        
                    }
                    else
                    {
                        mensajeWarning('Introduzca al menos un correo');
                    }
                }
            }
            ,{
                text     : 'Cancelar'
                ,icon    : '${ctx}/resources/fam3icons/icons/cancel.png'
                ,handler : function()
                {
                    this.up().up().destroy();
                }
            }
        ]
    }).show());
    _fieldById('_p3_idInputCorreo').focus();
    debug('<_p3_enviar');
}    
////// funciones //////
<%@ include file="/jsp-script/proceso/documentos/scriptImpresionRemesaEmisionEndoso.jsp"%>
</script>
</head>
<body><div id="_p44_divpri" style="height:800px;border:1px solid #999999;"></div></body>
</html>