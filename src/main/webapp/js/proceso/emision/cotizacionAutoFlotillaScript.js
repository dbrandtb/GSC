function transformaSigsEnIce (sigs) {
    debug('transformaSigsEnIce() args:', arguments);
    var ice = {
        smap1: {
            CDIDEPER: sigs.smap1.cdper,
            CDPERSON: sigs.smap1.cdperson,
            CDUNIECO: sigs.smap1.CDUNIECO,
            ESTADO: 'W',
            FEFIN: sigs.smap1.fefin,
            FEINI: sigs.smap1.feini,
            FESOLICI: sigs.smap1.fesolici,
            NMPOLIZA: '',
            TRAMITE: _p30_flujo.ntramite,
            TIPOFLOT: _p30_smap1.tipoflot,
            //aux...
            cdramo: sigs.smap1.cdramo,
            diasValidos: '20',
            nmpoliza: '',
            ntramiteIn: _p30_flujo.ntramite,
            'parametros.pv_cdagrupa': null,
            'parametros.pv_cdasegur': null,
            'parametros.pv_cdestado': null,
            'parametros.pv_cdgrupo': null,
            'parametros.pv_cdplan': null,
            'parametros.pv_cdramo': sigs.smap1.cdramo,
            'parametros.pv_cdtipsit': 'XPOLX',
            'parametros.pv_cdunieco': sigs.smap1.CDUNIECO,
            'parametros.pv_dsgrupo': null,
            'parametros.pv_estado': 'W',
            'parametros.pv_fecharef': 'null',
            'parametros.pv_fefecsit': 'null',
            'parametros.pv_nmpoliza': '',
            'parametros.pv_nmsbsitext': null,
            'parametros.pv_nmsitaux': null,
            'parametros.pv_nmsituac': '-1',
            'parametros.pv_nmsituaext': null,
            'parametros.pv_nmsuplem': '0',
            //parametros.pv_otvalorXY
            'parametros.pv_status': 'V',
            'parametros.pv_swreduci': null
        },
        slist2: [],
        success: true,
        exito: true
    };
    
    //ice.smap1.aux...
    for (var att in sigs.smap1) {
        // a u x . ...
        //0 1 2 3 4
        if (att.slice(0, 4) === 'aux.') {
            ice.smap1[att] = sigs.smap1[att];
        }
    }
    
    //ice.smap1.parametros.pv_otvalor...
    for (var att in sigs.slist1[0]) {
        // p a r a m e t r o s . p v _ o t v a l o r ...
        //0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
        if (att.slice(0, 21) === 'parametros.pv_otvalor') {
            ice.smap1[att] = sigs.slist1[0][att];
        }
    }
    
    for (var i = 0; i < sigs.slist1.length; i++) {
        var autoSigs = sigs.slist1[i];
        var autoIce  = {
            CDAGRUPA: '1',
            CDASEGUR: '30',
            CDESTADO: '0',
            CDGRUPO: '',
            CDPLAN: autoSigs.cdplan,
            CDRAMO: autoSigs.cdramo,
            CDTIPSIT: autoSigs.cdtipsit,
            CDUNIECO: autoSigs.cdunieco,
            DSGRUPO: '',
            ESTADO: 'W',
            FECHAREF: autoSigs.feini,
            FEFECSIT: autoSigs.feini,
            NMPOLIZA: '',
            NMSBSITEXT: '',
            NMSITAUX: '1',
            NMSITUAC: autoSigs.nmsituac,
            NMSITUAEXT: '',
            NMSUPLEM: '0',
            STATUS: 'V',
            SWREDUCI: '',
            cdplan: autoSigs.cdplan,
            cdtipsit: autoSigs.cdtipsit,
            nmsituac: autoSigs.nmsituac
            //parametros.pv_otvalor...
        };
        for (var att in autoSigs) {
            // p a r a m e t r o s . p v _ o t v a l o r ...
            //0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
            if (att.slice(0, 21) === 'parametros.pv_otvalor') {
                autoIce[att] = autoSigs[att];
            }
        }
        ice.slist2.push(autoIce);
    }
    
    debug('transformaSigsEnIce ice:', ice);
    return ice;
}

function _p30_cotizar(sinTarificar)
{
    debug('>_p30_cotizar sinTarificar:',sinTarificar,'.');
    
    var valido = _fieldById('_p30_form').isValid();
    if(!valido)
    {
        datosIncompletos();
    }
    
    if(valido)
    {
        var error  = '';
        for(var i=0;i<_f1_botones.length;i++)
        {
            var boton    = _f1_botones[i];
            var cdtipsit = boton.cdtipsit;
            var panel    = _p30_paneles[cdtipsit];
            debug('buscando en panel:',panel);
            if(panel.valores==false)
            {
                valido = false;
                error  = error+'FALTA DEFINIR: '+boton.text+'<br/>';
            }
        }
        if(!valido)
        {
            mensajeWarning(error);
        }
    }
    
    if(valido)
    {
    	if(RolSistema.puedeSuscribirAutos(_p30_smap1.cdsisrol))
//     	(rolesSuscriptores.lastIndexOf('|'+_p30_smap1.cdsisrol+'|')!=-1)
        {
            valido = _p30_store.getCount()>=1;
            if(!valido)
            {
                mensajeWarning('Debe capturar al menos un inciso');
            }
        }
        else
        {
            valido = _p30_store.getCount()>=5;
            if(!valido)
            {
                mensajeWarning('Debe capturar al menos cinco incisos');
            }
        }
    }
    
    if(valido)
    {
        var error        = '';
        var agregarError = function(cadena,nmsituac)
        {
            valido = false;
            error  = error + 'Inciso ' + nmsituac + ': ' + cadena +'</br>';
        };
        _p30_store.each(function(record)
        {
            var cdtipsit = record.get('cdtipsit');
            var nmsituac = record.get('nmsituac');
            
            if(!Ext.isEmpty(cdtipsit))
            {
                if(Ext.isEmpty(record.get('cdplan')))
                {
                    agregarError('Debe seleccionar el paquete',nmsituac);
                }
                var itemsObliga = Ext.ComponentQuery.query('[swobligaflot=true]',_p30_tatrisitFullForms[cdtipsit]);
                for(var i in itemsObliga)
                {
                    if(Ext.isEmpty(record.get(itemsObliga[i].getName())))
                    {
                        agregarError('Falta definir "'+itemsObliga[i].getFieldLabel()+'"',nmsituac);
                    }
                }
            }
            else
            {
                agregarError('Debe seleccionar el tipo de veh&iacute;culo',nmsituac);
            }
        });
        if(!valido)
        {
            mensajeWarning(error);
        }
    }
    
    if(valido)
    {
    	if(_p30_smap1.tipoflot=='P' 
    	   &&
    	   !RolSistema.puedeSuscribirAutos(_p30_smap1.cdsisrol)
//     	   (rolesSuscriptores.lastIndexOf('|'+_p30_smap1.cdsisrol+'|')==-1)
    	   )
    	{
    		
    		var ncamiones = 0;
            var ntractocamiones = 0;
            var nsemiremolques = 0;
        	
        	var nTipo2  = 0;
        	var nTipo4  = 0;
            var nTipo13 = 0;
            _p30_store.each(function(record)
            {
                if(record.get('cdtipsit')+'x'=='CRx')
                {
            		var tipoVehiName = _p30_tatrisitFullForms['CR'].down('[fieldLabel*=TIPO DE VEH]').name;
            		if(record.get(tipoVehiName)-0==2){
            			ncamiones = ncamiones+1;
            		}
            		else if(record.get(tipoVehiName)-0==4)
                    {
                        ncamiones = ncamiones+1;
                        ntractocamiones = ntractocamiones+1;
                    }else if(record.get(tipoVehiName)-0==13)
                    {
                    	nsemiremolques = nsemiremolques+1;
                    }
                    
                }else if(record.get('cdtipsit')+'x'=='TCx'){
                	ncamiones = ncamiones+1;
                	ntractocamiones = ntractocamiones+1;
                	
                }else if(record.get('cdtipsit')+'x'=='RQx'){
                	nsemiremolques = nsemiremolques+1;
                }
            });
            
            if(ncamiones > _p30_smap1.totalCamiones){
            	valido = false;
            	mensajeWarning("&Uacute;nicamente se puede amparar "+_p30_smap1.totalCamiones+" Camiones en la autorización. <br/>Intente nuevamente.");
            }else{
            	if(ntractocamiones > _p30_smap1.totalTractocamion){
                	valido = false;
                	mensajeWarning("&Uacute;nicamente se puede amparar "+_p30_smap1.totalTractocamion+" Tractocamiones en la autorización. <br/>Intente nuevamente.");
                }else{
                	if(nsemiremolques > (+ntractocamiones * +_p30_smap1.remolquesPorTracto)){
                		valido = false;
                		mensajeWarning('Se permiten '+_p30_smap1.remolquesPorTracto+' remolques por cada tractocami&oacute;n<br/>'
                                +'Y en la cotizaci&oacute;n hay '+nsemiremolques+' remolques y '+ntractocamiones+' tractocamiones');
                	}
                }
            }
    	}
    }
    
    if(valido)
    {
        valido = _fieldByName('nmpoliza',_fieldById('_p30_form')).sucio==false;
        if(!valido)
        {
            _fieldByName('nmpoliza',_fieldById('_p30_form')).semaforo = true;
            _fieldByName('nmpoliza',_fieldById('_p30_form')).setValue('');
            _fieldByName('nmpoliza',_fieldById('_p30_form')).semaforo = false;
            valido = true;
        }
    }
    
    debug('_p30_paneles:',_p30_paneles,'valido:',valido);
    if(valido)
    {
        var form = _fieldById('_p30_form');
        
        //copiar paneles a oculto
        var arr = Ext.ComponentQuery.query('#_p30_gridTarifas');
        if(arr.length>0)
        {
            var formDescuentoActual = _fieldById('_p30_formDescuento');
            var formCesion          = _fieldById('_p30_formCesion');
            var recordPaneles       = new _p30_modelo(formDescuentoActual.getValues());
            var itemsCesion         = Ext.ComponentQuery.query('[fieldLabel]',formCesion);
            for(var i=0;i<itemsCesion.length;i++)
            {
                recordPaneles.set(itemsCesion[i].getName(),itemsCesion[i].getValue());
            }
            form.formOculto.loadRecord(recordPaneles);
            debug('form.formOculto.getValues():',form.formOculto.getValues());
        }
    
        debug('length:',_p30_paneles.length,'type:',typeof _p30_paneles);
        var recordsCdtipsit = [];
        for(var cdtipsitPanel in _p30_paneles)
        {
            var panel      = _p30_paneles[cdtipsitPanel];
            var recordBase = new _p30_modelo(panel.valores);
            recordBase.set('cdtipsit',cdtipsitPanel);
            debug('cdtipsitPanel:',cdtipsitPanel,'recordBase:',recordBase);
            recordsCdtipsit[cdtipsitPanel] = recordBase;
        }
        debug('recordsCdtipsit:',recordsCdtipsit);
        var storeTvalosit = Ext.create('Ext.data.Store',
        {
            model : '_p30_modelo'
        });
        var formValuesAux = form.getValues();
        var formValues    = {};
        for(var prop in formValuesAux)
        {
            if(prop+'x'!='x'
                &&prop.slice(0,5)=='param')
            {
                formValues[prop]=formValuesAux[prop];
            }
        }
        debug('formValues:',formValues);
        var valuesFormOculto = form.formOculto.getValues();
        debug('valuesFormOculto:',valuesFormOculto);
        _p30_store.each(function(record)
        {
            var cdtipsit       = record.get('cdtipsit');
            var cdtipsitPanel  = _p30_smap1['destino_'+cdtipsit];
            var recordBase     = recordsCdtipsit[cdtipsitPanel];
            var recordTvalosit = new _p30_modelo(record.data);
            
            //---
            if(cdtipsitPanel==cdtipsit)
            {
                for(var prop in recordTvalosit.getData())
                {
                    var valor = recordTvalosit.get(prop);
                    var base  = recordBase.get(prop);
                    if(Ext.isEmpty(valor)&&!Ext.isEmpty(base))
                    {
                        recordTvalosit.set(prop,base);
                    }
                }
            }
            else
            {
                for(var prop in recordTvalosit.getData())
                {
                    var base = recordBase.get(prop);
                    debug('base:',base);
                    
                    var valorValosit = recordTvalosit.get(prop);
                    debug('valorValosit:',valorValosit);
                    
                    var cmpPanel = _p30_paneles[cdtipsitPanel].down('[name='+prop+']');
                    debug('cmpPanel:',cmpPanel,'.');
                    if(!Ext.isEmpty(cmpPanel))
                    {
                        if(cmpPanel.auxiliar=='adicional'
                            &&Ext.isEmpty(valorValosit)
                            &&!Ext.isEmpty(base)
                        )
                        {
                            debug('set normal, porque es adicional');
                            //alert('ADIC!-'+fieldLabel+'-'+prop);
                            recordTvalosit.set(prop,base);
                        }
                        else
                        {
                            var cmpPanelLabel = cmpPanel.fieldLabel;
                            debug('cmpPanelLabel:',cmpPanelLabel,'.');
                            var cmpByLabel = _p30_tatrisitFullForms[cdtipsit].down('[fieldLabel*='+_substringComa(cmpPanelLabel)+']');
                            if(!Ext.isEmpty(cmpByLabel))
                            {
                                var nameByLabel = cmpByLabel.name;
                                var valor       = recordTvalosit.get(nameByLabel);
                                debug('valor:',valor);
                                if(Ext.isEmpty(valor)&&!Ext.isEmpty(base))
                                {
                                    recordTvalosit.set(nameByLabel,base);
                                }
                            }
                        }
                    }
                }
            }
            //---
            
            /*
            for(var prop in recordTvalosit.data)
            {
                var valor = recordTvalosit.get(prop);
                var base  = recordBase.get(prop);
                if(Ext.isEmpty(valor)&&!Ext.isEmpty(base))
                {
                    if(cdtipsitPanel==cdtipsit)
                    {
                        recordTvalosit.set(prop,base);
                    }
                    else
                    {
                        var cmpOriginal = _p30_paneles[cdtipsitPanel].down('[name='+prop+']');
                        debug('cmpOriginal:',cmpOriginal);
                        if(!Ext.isEmpty(cmpOriginal))
                        {
	                        debug('cmpOriginal.auxiliar:',cmpOriginal.auxiliar,'.');
	                        var fieldLabel = cmpOriginal.fieldLabel;
	                        debug('fieldLabel:',fieldLabel);
	                        if(cmpOriginal.auxiliar=='adicional')
	                        {
	                            debug('set normal, porque es adicional');
	                            //alert('ADIC!-'+fieldLabel+'-'+prop);
	                            recordTvalosit.set(prop,base);
	                        }
	                        else
	                        {
	                            var cmpByLabel  = _p30_tatrisitFullForms[cdtipsit].down('[fieldLabel*='+_substringComa(fieldLabel)+']');
	                            if(!Ext.isEmpty(cmpByLabel))
	                            {
	                                var nameByLabel = cmpByLabel.name;
	                                debug('set en nameByLabel para cdtipsit:',nameByLabel,cdtipsit,'.');
	                                recordTvalosit.set(nameByLabel,base);
	                                //alert('SI!-'+fieldLabel+'-'+nameByLabel);
	                            }
	                            else
	                            {
	                                //alert('NO!-'+fieldLabel+'-'+cdtipsit);
	                                debug('No existe el dsatribu en el cdtipsit:',fieldLabel,cdtipsit,'.');
	                            }
	                        }
                        }
                    }
                }
            }
            */
            
            if(_p30_smap1.mapeo=='DIRECTO')
            {
                for(var prop in formValues)
                {
                    recordTvalosit.set(prop,formValues[prop]);
                }
                for(var att in valuesFormOculto)
                {
                    var valorOculto  = valuesFormOculto[att];
                    var valorValosit = recordTvalosit.get(att);
                    if(valorValosit+'x'=='x'&&valorOculto+'x'!='x')
                    {
                        recordTvalosit.set(att,valorOculto);
                    }
                }
            }
            else
            {
                var mapeos = _p30_smap1.mapeo.split('#');
                debug('mapeos:',mapeos);
                for(var i in mapeos)
                {
                    var cdtipsitsMapeo = mapeos[i].split('|')[0];
                    var mapeo          = mapeos[i].split('|')[1];
                    debug('cdtipsit:',cdtipsit,'cdtipsitsMapeo:',cdtipsitsMapeo);
                    if((','+cdtipsitsMapeo+',').lastIndexOf(','+cdtipsit+',')!=-1)
                    {
                        debug('coincidente:',cdtipsitsMapeo,'cdtipsit:',cdtipsit)
                        debug('mapeo:',mapeo);
                        if(mapeo=='DIRECTO')
                        {
                            debug('directo');
                            for(var prop in formValues)
                            {
                                recordTvalosit.set(prop,formValues[prop]);
                            }
                            for(var att in valuesFormOculto)
                            {
                                var valorOculto  = valuesFormOculto[att];
                                var valorValosit = recordTvalosit.get(att);
                                if(valorValosit+'x'=='x'&&valorOculto+'x'!='x')
                                {
                                    recordTvalosit.set(att,valorOculto);
                                }
                            }
                        }
                        else
                        {
                            var atributos = mapeo.split('@');
                            debug('atributos:',atributos);
                            for(var i in atributos)
                            {
                                var atributoIte = atributos[i];
                                var modelo      = atributoIte.split(',')[0];
                                var origen      = atributoIte.split(',')[1];
                                var pantalla    = atributoIte.split(',')[2];
                                
                                modelo   = 'parametros.pv_otvalor'+(('x00'+modelo)  .slice(-2));
                                pantalla = 'parametros.pv_otvalor'+(('x00'+pantalla).slice(-2));
                                
                                debug('modelo:'   , modelo   , '.');
                                debug('origen:'   , origen   , '.');
                                debug('pantalla:' , pantalla , '.');
                                
                                if(origen=='F')
                                {
                                    recordTvalosit.set(modelo,formValues[pantalla]);
                                }
                                else if(origen=='O')
                                {
                                    var valorOculto  = valuesFormOculto[pantalla];
                                    var valorValosit = recordTvalosit.get(modelo);
                                    if(valorValosit+'x'=='x'&&valorOculto+'x'!='x')
                                    {
                                        recordTvalosit.set(modelo,valorOculto);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            storeTvalosit.add(recordTvalosit);
            debug('record:',record.data,'tvalosit:',recordTvalosit.data);
        });
        debug('_p30_store:',_p30_store);
        debug('storeTvalosit:',storeTvalosit);
        
        var json =
        {
            smap1 :
            {
                cdunieco     : _p30_smap1.cdunieco
                ,cdramo      : _p30_smap1.cdramo
                ,estado      : 'W'
                ,nmpoliza    : _fieldByName('nmpoliza',_fieldById('_p30_form')).getValue()
                ,cdtipsit    : _p30_smap1.cdtipsit
                ,cdpersonCli : Ext.isEmpty(_p30_recordClienteRecuperado) ? '' : _p30_recordClienteRecuperado.raw.CLAVECLI
                ,nmorddomCli : Ext.isEmpty(_p30_recordClienteRecuperado) ? '' : _p30_recordClienteRecuperado.raw.NMORDDOM
                ,cdideperCli : Ext.isEmpty(_p30_recordClienteRecuperado) ? '' : _p30_recordClienteRecuperado.raw.CDIDEPER
                ,feini       : Ext.Date.format(_fieldByName('feini').getValue(),'d/m/Y')
                ,fefin       : Ext.Date.format(_fieldByName('fefin').getValue(),'d/m/Y')
                ,cdagente    : _fieldByLabel('AGENTE',_fieldById('_p30_form')).getValue()
                ,notarificar : sinTarificar ? 'si' : ''
                ,tipoflot    : _p30_smap1.tipoflot
            }
            ,slist1 : []
            ,slist2 : []
            ,slist3 : []
        };
        
        for(var cdtipsitPanel in recordsCdtipsit)
        {
            json.slist3.push(recordsCdtipsit[cdtipsitPanel].data);
        }
        
        var itemsTatripol = Ext.ComponentQuery.query('[name]',_fieldById('_p30_fieldsetTatripol'));
        debug('itemsTatripol:',itemsTatripol);
        for(var i in itemsTatripol)
        {
            var tatri=itemsTatripol[i];
            json.smap1['tvalopol_'+tatri.cdatribu]=tatri.getValue();
        }
        
        _p30_store.each(function(record)
        {
            json.slist2.push(record.data);
        });
        
        storeTvalosit.each(function(record)
        {
            json.slist1.push(record.data);
        });
        
        //crear record con los valores del formulario y el formulario oculto
        debug('storeTvalosit.getAt(0).data:',storeTvalosit.getAt(0).data);
        var recordTvalositPoliza=new _p30_modelo(storeTvalosit.getAt(0).data);
        debug('recordTvalositPoliza:',recordTvalositPoliza.data);
        recordTvalositPoliza.set('cdtipsit','XPOLX');
        recordTvalositPoliza.set('nmsituac',-1);
        for(var prop in formValues)
        {
            recordTvalositPoliza.set(prop,formValues[prop]);
        }
        for(var att in valuesFormOculto)
        {
            recordTvalositPoliza.set(att,valuesFormOculto[att]);
        }
        debug('recordTvalositPoliza final:',recordTvalositPoliza.data);
        json.slist2.push(recordTvalositPoliza.data);
        //crear record con los valores del formulario y el formulario oculto
        
        debug('>>> json a enviar:',json);
        
        var panelpri = _fieldById('_p30_panelpri');
        panelpri.setLoading(true);
        Ext.Ajax.request(
        {
            url       : _p30_urlCotizar
            ,jsonData : json
            ,success  : function(response)
            {
                panelpri.setLoading(false);
                
                _p30_bloquear(true);
                
                json = Ext.decode(response.responseText);
                debug('### cotizar:',json);
                if(json.exito)
                {
                	
                	if(!Ext.isEmpty(json.smap1.msnPantalla))
                    {
                           mensajeError(json.smap1.msnPantalla);
                    }
                	
                    _fieldByName('nmpoliza',_fieldById('_p30_form')).semaforo=true;
                    _fieldByName('nmpoliza',_fieldById('_p30_form')).setValue(json.smap1.nmpoliza);
                    _fieldByName('nmpoliza',_fieldById('_p30_form')).semaforo=false;
                    
                    if(Ext.isEmpty(sinTarificar)||false==sinTarificar)
                    {
                        _grabarEvento('COTIZACION'
                                      ,'COTIZA'
                                      ,_p30_smap1.ntramite
                                      ,_p30_smap1.cdunieco
                                      ,_p30_smap1.cdramo
                                      ,'W'
                                      ,json.smap1.nmpoliza
                                      ,json.smap1.nmpoliza
                                      ,'buscar'
                                      ,_fieldById('_p30_form')
                                      );
                    }
                    
                    var itemsDescuento =
                    [
                        {
                            xtype  : 'displayfield'
                            ,value : '¿Desea usar su descuento de agente?'
                        }
                        ,{
                            xtype  : 'displayfield'
                            ,value : 'Si desea aplicar un DESCUENTO seleccione el porcentaje mayor a 0%'
                        }
                        ,{
                            xtype  : 'displayfield'
                            ,value : 'Si desea aplicar un RECARGO seleccione el porcentaje menor a 0%'
                        }
                    ];
                    
                    <s:if test='%{getImap().get("panel5Items")!=null}'>
                        var itemsaux = [<s:property value="imap.panel5Items" />];
                        for(var ii=0;ii<itemsaux.length;ii++)
                        {
                            debug('itemsaux[ii]:',itemsaux[ii]);
                            itemsDescuento.push(itemsaux[ii]);
                        }
                    </s:if>
                    debug('itemsDescuento:',itemsDescuento);
                    
                    var itemsComision =
                    [
                        {
                            xtype  : 'displayfield'
                            ,value : 'Indique el porcentaje de comisi&oacute;n que desea ceder'
                        }
                    ];
                    
                    <s:if test='%{getImap().get("panel6Items")!=null}'>
                        var itemsaux = [<s:property value="imap.panel6Items" />];
                        for(var ii=0;ii<itemsaux.length;ii++)
                        {
                            itemsaux[ii].minValue=0;
                            itemsaux[ii].maxValue=100;
                            itemsComision.push(itemsaux[ii]);
                        }
                    </s:if>
                    debug('itemsComision:',itemsComision);
                    
                    var arr = Ext.ComponentQuery.query('#_p30_gridTarifas');
                    if(arr.length>0)
                    {
                        _fieldById('_p30_formCesion').destroy();
                        panelpri.remove(arr[arr.length-1],true);
                    }
                    
                    var _p30_formDescuento = Ext.create('Ext.form.Panel',
                    {
                        itemId        : '_p30_formDescuento'
                        ,border       : 0
                        ,defaults     : { style : 'margin:5px;' }
                        ,style        : 'margin-left:535px;'
                        ,width        : 450
                        ,windowCesion : Ext.create('Ext.window.Window',
                        {
                            title        : 'CESI&Oacute;N DE COMISI&Oacute;N'
                            ,autoScroll  : true
                            ,closeAction : 'hide'
                            ,modal       : true
                            ,items       :
                            [
                                Ext.create('Ext.form.Panel',
                                {
                                    itemId       : '_p30_formCesion'
                                    ,border      : 0
                                    ,defaults    : { style : 'margin:5px;' }
                                    ,items       : itemsComision
                                    ,buttonAlign : 'center'
                                    ,buttons     :
                                    [
                                        {
                                            itemId   : '_p30_botonAplicarCesion'
                                            ,text    : 'Aplicar'
                                            ,icon    : '${ctx}/resources/fam3icons/icons/accept.png'
                                            ,handler : function(me)
                                            {
                                                if(me.up('form').getForm().isValid())
                                                {
                                                    me.up('window').hide();
                                                    _p30_cotizar(true);
                                                }
                                                else
                                                {
                                                    mensajeWarning('Favor de verificar los datos');
                                                }
                                            }
                                        }
                                    ]
                                })
                            ]
                        })
                        ,items       :
                        [
                            {
                                xtype  : 'fieldset'
                                ,title : '<span style="font:bold 14px Calibri;">DESCUENTO DE AGENTE</span>'
                                ,items : itemsDescuento
                            }
                        ]
                        ,buttonAlign : 'right'
                        ,buttons     :
                        [
                            {
                                itemId   : '_p30_botonAplicarDescuento'
                                ,text    : 'Aplicar'
                                ,icon    : '${ctx}/resources/fam3icons/icons/accept.png'
                                ,handler : function(me)
                                {
                                    if(me.up('form').getForm().isValid())
                                    {
                                        _p30_cotizar();
                                    }
                                    else
                                    {
                                        mensajeWarning('Favor de verificar los datos');
                                    }
                                }
                            }
                        ]
                    });
                    
                    _p30_formDescuento.loadRecord(new _p30_modelo(form.formOculto.getValues()));
                    _fieldById('_p30_formCesion').loadRecord(new _p30_modelo(form.formOculto.getValues()));
                    
                    //bloquear descuento
                    var arrDesc = Ext.ComponentQuery.query('[fieldLabel]',_p30_formDescuento);
                    var disabledDesc = false;
                    for(var i=0;i<arrDesc.length;i++)
                    {
                        if(arrDesc[i].getValue()-0!=0)
                        {
                            arrDesc[i].setReadOnly(true);
                            disabledDesc = true;
                        }
                    }
                    _fieldById('_p30_botonAplicarDescuento').setDisabled(disabledDesc);
                    
                    //bloquear comision
                    var arrComi      = Ext.ComponentQuery.query('[fieldLabel]',_fieldById('_p30_formCesion'));
                    var disabledComi = false;
                    for(var i=0;i<arrComi.length;i++)
                    {
                        if(arrComi[i].getValue()-0!=0)
                        {
                            arrComi[i].setReadOnly(true);
                            disabledComi = true;
                        }
                    }
                    _fieldById('_p30_botonAplicarCesion').setDisabled(disabledComi);
                    
                    var gridTarifas=Ext.create('Ext.panel.Panel',
                    {
                        itemId : '_p30_gridTarifas'
                        ,items :
                        [
                            Ext.create('Ext.grid.Panel',
                            {
                                title             : 'Resultados'
                                ,border           : 0
                                ,store            : Ext.create('Ext.data.Store',
                                {
                                    model : '_p30_modeloTarifa'
                                    ,data : json.slist1
                                })
                                ,columns          :
                                [
                                    {
                                        text       : 'FORMA DE PAGO'
                                        ,dataIndex : 'DSPERPAG'
                                        ,flex      : 1
                                    }
                                    ,{
                                        text       : 'PRIMA'
                                        ,dataIndex : 'PRIMA'
                                        ,renderer  : Ext.util.Format.usMoney
                                        ,flex      : 1
                                    }
                                ]
                                ,selType          : 'cellmodel'
                                ,minHeight        : 100
                                ,enableColumnMove : false
                                ,listeners        :
                                {
                                    select       : _p30_tarifaSelect
                                    ,afterrender : function(me)
                                    {
                                        if(!Ext.isEmpty(_p30_flujo)) // && !sinTarificar===true)
                                        {
                                            _p30_actualizarCotizacionTramite(_p30_actualizarSwexiperTramite);
                                        }
                                    }
                                }
                            })
                            ,_p30_formDescuento
                            ,Ext.create('Ext.panel.Panel',
                            {
                                defaults : { style : 'margin:5px;' }
                                ,border  : 0
                                ,tbar    :
                                [
                                    '->'
                                    ,{
                                        itemId    : '_p30_botonDetalles'
                                        ,text     : 'Detalles'
                                        ,icon     : '${ctx}/resources/fam3icons/icons/text_list_numbers.png'
                                        ,disabled : true
                                        ,handler  : _p30_detalles
                                       ,hidden    : !RolSistema.puedeSuscribirAutos(_p30_smap1.cdsisrol)//(rolesSuscriptores.lastIndexOf('|'+_p30_smap1.cdsisrol+'|')==-1)
                                    }
                                    ,{
                                        itemId    : '_p30_botonCoberturas'
                                        ,text     : 'Coberturas'
                                        ,icon     : '${ctx}/resources/fam3icons/icons/table.png'
                                        ,disabled : true
                                        ,handler  : _p30_coberturas
                                    }
                                    ,{
                                        itemId   : '_p30_botonEditar'
                                        ,text    : 'Editar'
                                        ,icon    : '${ctx}/resources/fam3icons/icons/pencil.png'
                                        ,handler : _p30_editar
                                    }
                                    ,{
                                        itemId   : '_p30_botonClonar'
                                        ,text    : 'Duplicar'
                                        ,icon    : '${ctx}/resources/fam3icons/icons/control_repeat_blue.png'
                                        ,handler : _p30_clonar
                                    }
                                    ,{
                                        itemId   : '_p30_botonNueva'
                                        ,text    : 'Nueva'
                                        ,icon    : '${ctx}/resources/fam3icons/icons/arrow_refresh.png'
                                        ,handler : _p30_nueva
                                    }
                                ]
                                ,bbar    :
                                [
                                    '->'
                                    ,{
                                        itemId    : '_p30_botonEnviar'
                                        ,xtype    : 'button'
                                        ,text     : 'Enviar'
                                        ,icon     : '${ctx}/resources/fam3icons/icons/email.png'
                                        ,disabled : true
                                        ,handler  : _p30_enviar
                                    }
                                    ,{
                                        itemId    : '_p30_botonImprimir'
                                        ,xtype    : 'button'
                                        ,text     : 'Imprimir'
                                        ,icon     : '${ctx}/resources/fam3icons/icons/printer.png'
                                        ,disabled : true
                                        ,handler  : _p30_imprimir
                                    }
                                    ,{
                                        itemId   : '_p30_botonCesion'
                                        ,xtype   : 'button'
                                        ,icon    : '${ctx}/resources/fam3icons/icons/page_white_star.png'
                                        ,text    : 'Cesi&oacute;n de comisi&oacute;n'
                                        ,handler : _p30_cesionClic
                                    }
                                    ,{
                                        itemId    : '_p30_botonComprar'
                                        ,xtype    : 'button'
                                        ,text     : 'Confirmar cotizaci&oacute;n'
                                        ,icon     : '${ctx}/resources/fam3icons/icons/book_next.png'
                                        ,disabled : true
                                        ,handler  : _p30_comprar
                                    }
                                ]
                            })
                        ]
                    });
                    
                    panelpri.add(gridTarifas);
                    panelpri.doLayout();
                    
                    if(_p30_smap1.cdramo+'x'=='5x'&&arrDesc.length>0)
                    {
                        _p30_formDescuento.setLoading(true);
                        Ext.Ajax.request(
                        {
                            url     : _p30_urlRecuperacionSimple
                            ,params :
                            {
                                'smap1.procedimiento' : 'RECUPERAR_DESCUENTO_RECARGO_RAMO_5'
                                ,'smap1.cdtipsit'     : _p30_smap1.cdtipsit
                                ,'smap1.cdagente'     : _fieldByLabel('AGENTE',_fieldById('_p30_form')).getValue()
                                ,'smap1.negocio'      : _fieldByLabel('NEGOCIO',_fieldById('_p30_form')).getValue()
                                ,'smap1.tipocot'      : _p30_smap1.tipoflot
                                ,'smap1.cdsisrol'     : _p30_smap1.cdsisrol
                                ,'smap1.cdusuari'     : _p30_smap1.cdusuari
                            }
                            ,success : function(response)
                            {
                                _p30_formDescuento.setLoading(false);
                                var json = Ext.decode(response.responseText);
                                debug('### cargar rango descuento ramo 5:',json);
                                if(json.exito)
                                {
                                    for(var i=0;i<arrDesc.length;i++)
                                    {
                                        arrDesc[i].minValue=100*(json.smap1.min-0);
                                        arrDesc[i].maxValue=100*(json.smap1.max-0);
                                        arrDesc[i].isValid();
                                        debug('min:',arrDesc[i].minValue);
                                        debug('max:',arrDesc[i].maxValue);
                                        arrDesc[i].setReadOnly(false);
                                    }
                                }
                                else
                                {
                                    for(var i=0;i<arrDesc.length;i++)
                                    {
                                        arrDesc[i].minValue=0;
                                        arrDesc[i].maxValue=0;
                                        arrDesc[i].setValue(0);
                                        arrDesc[i].isValid();
                                        arrDesc[i].setReadOnly(true);
                                    }
                                    mensajeError(json.respuesta);
                                }
                            }
                            ,failure : function()
                            {
                                _p30_formDescuento.setLoading(false);
                                for(var i=0;i<arrDesc.length;i++)
                                {
                                    arrDesc[i].minValue=0;
                                    arrDesc[i].maxValue=0;
                                    arrDesc[i].setValue(0);
                                    arrDesc[i].isValid();
                                    arrDesc[i].setReadOnly(true);
                                }
                                errorComunicacion();
                            }
                        });
                    }
                    
                    try {
                       gridTarifas.down('button[disabled=false]').focus(false, 1000);
                    } catch(e) {
                        debug(e);
                    }
                }
                else
                {
                    _p30_bloquear(false);
                    mensajeError(json.respuesta);
                }
            }
            ,failure  : function()
            {
                panelpri.setLoading(false);
                errorComunicacion();
            }
        });
    }
    
    debug('<_p30_cotizar');
}