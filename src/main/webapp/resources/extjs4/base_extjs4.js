////////////////////////////////////////////////////////////////////
// ESTE DOCUMENTO ES LA BASE DE TODOS LOS JSP QUE TENGAN EXT JS 4 //
////////////////////////////////////////////////////////////////////

///////////////////////
////// FUNCIONES //////
/*///////////////////*/
var _ice_debug = false;

function debug(a,b,c,d,e)
{
	if(_ice_debug)
	{
	    if(e!=undefined)
            console.log(a,b,c,d,e);
	    else if(d!=undefined)
	        console.log(a,b,c,d);
	    else if(c!=undefined)
	        console.log(a,b,c);
	    else if(b!=undefined)
	        console.log(a,b);
	    else
	        console.log(a);
	}
}

function debugError(a,b,c,d)
{
    if(_ice_debug)
    {
        if(d!=undefined)
            console.error('error manejado:',a,b,c,d);
        else if(c!=undefined)
            console.error('error manejado:',a,b,c);
        else if(b!=undefined)
            console.error('error manejado:',a,b);
        else
            console.error('error manejado:',a);
    }
}

//se llama automatico cuando un grid tiene columnas con editor y el editor es combo
function rendererDinamico(value,combo,view)
{
    debug('>rendererDinamico value,combo,view:',value,combo.id,view.id);
    var store=combo.store;
    if(store.getCount()>0)
    {
        var record=combo.findRecordByValue(value);
        if(record)
        {
            value=record.get('value');
        }
    }
    else
    {
        value='Cargando...';
        if(Ext.isEmpty(store.padreView))
        {
            store.padreView=view;
            store.on(
            {
                load : function(me)
                {
                    me.padreView.refresh();
                }
            });
        }
    }
    return value;
}

function rendererColumnasDinamico(value,comboName)
{
    debug('>rendererColumnasDinamico',value,comboName);
    var combo = Ext.ComponentQuery.query('[name='+comboName+']')[0];
    if(combo&&combo.getStore)
    {
        var store = combo.getStore();
        var found = false;
        store.each(function(record)
        {
            if(record.get('key')==value&&!found)
            {
                found = true;
                value = record.get('value');
            }
        });
    }
    debug('<rendererColumnasDinamico value',value);
    return value;
}

function validarRFC(rfc,tper, silencioso)
{
	debug('validarRFC',rfc,tper);
	var valido=rfc&&rfc.length>0&&tper&&tper.length>0;
	debug('validacion inicial:',valido);
	if(valido)
	{
		var regexLetras=/^[a-zA-Z&]*$/;
		var regexNumeros=/^[0-9]*$/;
		var regexLetrasNumeros=/^[a-zA-Z0-9]*$/;
		if(tper=='F'||tper=='S')
		{
			valido=rfc.length==10||rfc.length==13;
			debug('validacion longitud:',valido);
			if(valido)
			{
				// M A V A 9 0 0 8 1 7 J 3 6
				//0 1 2 3 4 5 6 7 8 9 0 1 2 3
				var letras    = rfc.substring(0,4);
				var anio      = rfc.substring(4,6);
				var mes       = rfc.substring(6,8);
				var dia       = rfc.substring(8,10);
				var homoclave = rfc.length==13?rfc.substring(10,13):false;
				debug(letras,anio,mes,dia);
				valido=valido&&regexLetras.test(letras);
				debug('letras',valido);
				valido=valido&&regexNumeros.test(anio);
				debug('anio',valido);
				valido=valido&&regexNumeros.test(mes);
				debug('mes',valido);
				valido=valido&&regexNumeros.test(dia);
				debug('dia',valido);
				if(homoclave)
				{
					valido=valido&&regexLetrasNumeros.test(homoclave);
					debug('homoclave',valido);
				}
				if(valido)
				{
					valido=valido&&mes*1<13;
					debug('mes<13',valido);
					valido=valido&&dia*1<32;
					debug('dia<32',valido);
				}
			}
		}
		else if(tper=='M')
		{
			valido=rfc.length==9||rfc.length==12;
			debug('validacion longitud:',valido);
			if(valido)
			{
				// A B C 9 0 0 8 1 7 X Y Z
				//0 1 2 3 4 5 6 7 8 9 0 1 2
				var letras    = rfc.substring(0,3);
				var anio      = rfc.substring(3,5);
				var mes       = rfc.substring(5,7);
				var dia       = rfc.substring(7,9);
				var homoclave = rfc.length==12?rfc.substring(9,12):false;
				debug(letras,anio,mes,dia);
				valido=valido&&regexLetras.test(letras);
				debug('letras',valido);
				valido=valido&&regexNumeros.test(anio);
				debug('anio',valido);
				valido=valido&&regexNumeros.test(mes);
				debug('mes',valido);
				valido=valido&&regexNumeros.test(dia);
				debug('dia',valido);
				if(homoclave)
				{
					valido=valido&&regexLetrasNumeros.test(homoclave);
					debug('homoclave',valido);
				}
				if(valido)
				{
					valido=valido&&mes*1<13;
					debug('mes<13',valido);
					valido=valido&&dia*1<32;
					debug('dia<32',valido);
				}
			}
		}
	}
	if(!valido)
	{
		
		if(Ext.isEmpty(silencioso) || !silencioso){
			var ven=Ext.create('Ext.window.Window',
			{
				title        : 'Error'
				,height      : 130
				,width       : 300
				,modal       : true
				,padding     : 5
				,items       :
				[
				    {
				    	xtype : 'label'
				    	,text : 'El RFC "'+rfc+'" no es v\u00E1lido para persona '+(tper=='F'?'F\u00EDsica':(tper=='M'?'Moral':'tipo r\u00E9gimen simplificado'))
				    }
				]
			    ,buttonAlign : 'center'
			    ,buttons     :
			    [
			        {
			        	text     : 'Aceptar'
			        	,handler : function()
			        	{
			        		this.up().up().destroy();
			        	}
			        }
			    ]
			}).show();
			centrarVentanaInterna(ven);
		}else{
			debug('El RFC "'+rfc+'" no es v\u00E1lido para persona '+(tper=='F'?'F\u00EDsica':(tper=='M'?'Moral':'tipo r\u00E9gimen simplificado')));
		}
		
	}
	return valido;
}

function datosIncompletos()
{
	var tmpMensajeEmergente=Ext.Msg.show({
        title    : 'Datos incompletos'
        ,icon    : Ext.Msg.WARNING
        ,msg     : 'Favor de capturar todos los campos requeridos'
        ,buttons : Ext.Msg.OK
    });
	centrarVentanaInterna(tmpMensajeEmergente);
}

function errorComunicacion(callback, mensajeExtra)
{
	if(Ext.isEmpty(mensajeExtra)){
		mensajeExtra = ''; 
	}
	var tmpMensajeEmergente=Ext.Msg.show({
        title    : 'Error'
        ,icon    : Ext.Msg.ERROR
        ,msg     : 'Error de comunicaci&oacute;n. ' + mensajeExtra
        ,buttons : Ext.Msg.OK
        ,fn      : callback
    });
	centrarVentanaInterna(tmpMensajeEmergente);
}

function mensajeInfo(mensaje,callback){

	var tmpMensajeEmergente=Ext.Msg.show({
			title    : 'Aviso'
			,icon    : Ext.Msg.INFO
			,msg     : mensaje
			,buttons : Ext.Msg.OK
			,fn      : callback 
	});
	centrarVentanaInterna(tmpMensajeEmergente);
}

function mensajeWarning(mensaje,funcion)
{
	if(funcion)
	{
		var tmpMensajeEmergente=Ext.Msg.show({
			title    : 'Aviso'
	        ,icon    : Ext.Msg.WARNING
	        ,msg     : mensaje
	        ,buttons : Ext.Msg.OK
	        ,fn      : funcion 
	    });
	}
	else
	{
		var tmpMensajeEmergente=Ext.Msg.show({
			title    : 'Aviso'
	        ,icon    : Ext.Msg.WARNING
	        ,msg     : mensaje
	        ,buttons : Ext.Msg.OK
	    });
	}
	centrarVentanaInterna(tmpMensajeEmergente);
}

function mensajeError(mensaje, callback)
{
	var tmpMensajeEmergente=Ext.Msg.show({
		title    : 'Error'
        ,icon    : Ext.Msg.ERROR
        ,msg     : mensaje
        ,buttons : Ext.Msg.OK
        ,fn     : callback
    });
	centrarVentanaInterna(tmpMensajeEmergente);
}

function mensajeCorrecto(titulo,mensaje,funcion)
{
	
	if(!Ext.isEmpty(funcion))
	{
		var tmpMensajeEmergente=Ext.Msg.show({
			title    : titulo
			,icon: 'x-message-box-ok' 
	        ,msg     : mensaje
	        ,buttons : Ext.Msg.OK
	        ,fn      : funcion 
	    });
	}
	else
	{
		var tmpMensajeEmergente=Ext.Msg.show({
			title    : titulo
			,icon: 'x-message-box-ok' 
	        ,msg     : mensaje
	        ,buttons : Ext.Msg.OK 
	    });
    }
	centrarVentanaInterna(tmpMensajeEmergente);
}

/**
 * Busca todos los combos anidados que tengan la funcion heredar() y los carga
 * @param formPanel
 */
function heredarPanel(formPanel,ponerForceSelection)
{
	var cmps = Ext.ComponentQuery.query('[heredar]',formPanel);
	debug('>heredarPanel:',cmps,'.');
	for(var i in cmps)
	{
	    if(!Ext.isEmpty(ponerForceSelection)&&ponerForceSelection==true)
	    {
	        cmps[i].heredar(true,function(comp)
			{
			    comp.forceSelection=true;
			    debug('forceSelection de:',comp.getFieldLabel());
			});
		}
		else
		{
		    cmps[i].heredar(true);
		}
	}
	debug('<heredarPanel');
}

/**
 * Centra un componente tipoWindow o Mensaje de Texto  en la pantalla principal 
 * @param ventana
 */
function centrarVentana(ventana)
{
    try {
        ventana.setPosition(ventana.getPosition()[0], $(window.parent).scrollTop() + 50);
    } catch(e) {
        debug(e);
    }
}

function centrarVentanaInterna(ventana)
{
    try {
        var y = $(window.parent).scrollTop() + 50;
        //debug('y:',y);
        ventana.setPosition(ventana.getPosition()[0],y);    
    } catch(e) {
        debug(e);
    }
    return ventana;
}

/** 
 * Realiza una consulta de acuerdo a la accion indicada
 * @param accion   Accion a ejecutar
 * @param inParams Parametros de entrada de la accion
 * @param form     Elemento para setLoading(true);
 * @param callback Funcion que recibe el array
 */
function consultaDinamica(accion,inParams,form,callback)
{
	debug('>consultaDinamica',accion,inParams);
	var jsonData =
	{
		stringMap :
		{
			accion : accion			
		}
	    ,linkedObjectMap : inParams
	};
	debug('datos a enviar:',jsonData);
	if(form)
	{
		form.setLoading(true);
	}
	Ext.Ajax.request(
	{
		url       : _global_urlConsultaDinamica
		,jsonData : jsonData
		,success  : function(response)
		{
			if(form)
			{
				form.setLoading(false);
			}
			jsonData = Ext.decode(response.responseText);
			if(jsonData.success)
			{
				if(jsonData.stringList.length>0)
				{
					callback(jsonData.stringList);
				}
				else
				{
					mensajeWarning('No hay resultados');
				}
			}
			else
			{
				mensajeError(jsonData.mensaje);
			}
		}
	    ,failure  : function()
	    {
	    	if(form)
	    	{
	    		form.setLoading(false);
	    	}
	    	errorComunicacion();
	    }
	});
	debug('<consultaDinamica');
}

function _fieldById(id,ocultarErrores)
{
    //debug('_fieldById:',id);
    var comp;
    var arr = Ext.ComponentQuery.query('#'+id);
    if(arr.length==0)
    {
        debugError('No se encuentra el campo con id "'+id+'"');
        if(Ext.isEmpty(ocultarErrores)||ocultarErrores==false)
        {
            mensajeError('No se encuentra el campo con id "'+id+'"');
        }
    }
    else
    {
        comp = arr[arr.length-1];
    }
    //debug('_fieldById comp:',comp);
    return comp;
}

function _fieldByName(name,parent,ocultarErrores)
{
    //debug('_fieldByName:',name,parent,'DUMMY');
    //debug('ocultarErrores:',ocultarErrores,'DUMMY');
    var comp;
    var arr = [];
    if(!Ext.isEmpty(parent))
    {
        arr = Ext.ComponentQuery.query('[name='+name+']',parent);
    }
    else
    {
        arr = Ext.ComponentQuery.query('[name='+name+']');
    }
    if(arr.length==0&&(Ext.isEmpty(ocultarErrores)||ocultarErrores==false))
    {
        mensajeError('No se encuentra el campo con name "'+name+'"');
    }
    else
    {
        comp = arr[arr.length-1];
    }
    //debug('_fieldByName comp:',comp);
    return comp;
}

/**
 * Funcion que obtiene un campo debajo de una forma por medio de la funcion Down
 * @param {} name (Obligatorio) Name del campo
 * @param {} parent (Obligatorio) Componente ExtJS con funcion down
 * @param {} ocultarErrores (Opcional) Ocultar mensaje si no se encuentra el campo.
 * @return {} componente Campo encontrado
 */
function _fieldByNameDown(name,parent,ocultarErrores){
    var comp; 
    try{
    	comp = parent.down('[name='+name+']');
    	if((Ext.isEmpty(ocultarErrores)||ocultarErrores==false) && (comp==undefined || comp == null) ){
    		mensajeError('No se encuentra el campo con name "'+name+'"');
    	}
    }catch(e){
    	mensajeError('Mal uso de fieldByNameDown, No se encuentra el campo con name "'+name+'"');
    	debugError('Exception, mal uso de _fieldByNameDown:',e);
    }
    return comp;
}

function _fieldByLabel(label,parent,ocultarErrores)
{
    //debug('_fieldByLabel:',label);
    //debug('ocultarErrores:',ocultarErrores,'DUMMY');
    
    var comp;
    var arr = [];
    if(parent)
    {
        arr = Ext.ComponentQuery.query('[fieldLabel='+label+']',parent);
    }
    else
    {
        arr = Ext.ComponentQuery.query('[fieldLabel='+label+']');
    }
    if(arr.length==0&&(Ext.isEmpty(ocultarErrores)||ocultarErrores==false))
    {
        mensajeError('No se encuentra el campo "'+label+'"');
    }
    else
    {
        comp = arr[arr.length-1];
    }
    //debug('_fieldByLabel comp:',comp);
    return comp;
}

function _fieldLikeLabel(label,parent,ocultarErrores)
{
    //debug('_fieldLikeLabel:',label);
    //debug('ocultarErrores:',ocultarErrores,'DUMMY');
    var comp;
    var arr = [];
    if(parent)
    {
        arr = Ext.ComponentQuery.query('[fieldLabel*='+label+']',parent);
    }
    else
    {
        arr = Ext.ComponentQuery.query('[fieldLabel*='+label+']');
    }
    if(arr.length==0&&(Ext.isEmpty(ocultarErrores)||ocultarErrores==false))
    {
        mensajeError('No se encuentra el campo "'+label+'"');
    }
    else
    {
        comp = arr[arr.length-1];
    }
    //debug('_fieldLikeLabel comp:',comp);
    return comp;
}

function checkEmpty(valor,mensaje)
{
    if(Ext.isEmpty(valor)||valor+'x'=='x')
    {
        throw mensaje;
    }
}

function checkBool(valor,mensaje)
{
    checkEmpty(valor,mensaje);
    if(valor==false)
    {
        throw mensaje;
    }
}

function manejaException(e,ck,compLoading)
{
    if(typeof e == 'string')
    {
        mensajeWarning(e);
    }
    else
    {
        debugError('!exception:',e);
        
        try
        {
            compLoading.setLoading(false);
        }
        catch(e){}
        
        try
        {
            if(compLoading.maskLocal === true)
            {
                compLoading.close(); // para cuando es un mask del metodo _maskLocal
            }
        }
        catch(e){}
        
        mensajeError('Error '+(ck.toLowerCase()));
    }
}

/**
 * Funcion que calcula los Anios Transcurridos entre dos fechas.
 * @param {Date} fechaInicial
 * @param {Date} fechaFinal
 * @return {number} Numero de Anios Transcurridos o null en caso de error.
 */
function calculaAniosTranscurridos(fechaInicial, fechaFinal){
	
	var anios = null;
	
	var milisegundosPorSegundo = 1000;
	var milisegundosPorMinuto  = milisegundosPorSegundo * 60;
	var milisegundosPorHora    = milisegundosPorMinuto * 60;
	var milisegundosPorDia     = milisegundosPorHora * 24;
	var milisegundosPorAnio    = milisegundosPorDia * 365.26;
	
	try{
		anios = (fechaFinal - fechaInicial) / milisegundosPorAnio;	
	}catch(e){
		debug('Error al calcular edad entre dos fechas', e);
	}

	return anios;
}

function parseaFechas(recordData)
{
    debug('>parseaFechas entrada:',recordData);
    var datos={};
    for(var key in recordData)
    {
        var value=recordData[key];
        if((typeof value=='object')&&value&&value.getDate)
        {
            var fecha='';
            fecha+=value.getDate();
            if((fecha+'x').length==2)//1x
            {
                fecha = ('x'+fecha).replace('x','0');//x1=01
            }
            fecha+='/';
            fecha+=value.getMonth()+1<10?
                (('x'+(value.getMonth()+1)).replace('x','0'))
                :(value.getMonth()+1);
            fecha+='/';
            fecha+=value.getFullYear();
            value=fecha;
        }
        datos[key]=value;
    }
    debug('<parseaFechas salida:',datos);
    return datos;
}

function _grabarEvento(cdmodulo,cdevento,ntramite,cdunieco,cdramo,estado,nmpoliza,nmsolici,cdagente,parentCdagente)
{
    var micdagente = cdagente;
    if('buscar'==micdagente)
    {
        try
        {
            micdagente = _fieldLikeLabel('AGENTE',parentCdagente,true).getValue();
        }
        catch(e)
        {
            micdagente='';
            debugError('Error al buscar agente para grabar evento:',e);
        }
    }
    try
    {
        Ext.Ajax.request(
        {
            url     : _GLOBAL_URL_GRABAR_EVENTO
            ,params :
            {
                'params.cdmodulo'  : cdmodulo
                ,'params.cdevento' : cdevento
                ,'params.ntramite' : ntramite
                ,'params.cdunieco' : cdunieco
                ,'params.cdramo'   : cdramo
                ,'params.estado'   : estado
                ,'params.nmpoliza' : nmpoliza
                ,'params.nmsolici' : nmsolici
                ,'params.cdagente' : micdagente
            }
        });
    }
    catch(e)
    {
        debugError('Error al grabar evento:',cdmodulo,cdevento,e);
    }
}

/**
 * Funcion que carga un formulario aunque tenga combos anidados y autocompleters
 * @param {Component} form  => formulario ext js
 * @param {Object}    datos => (objeto javascript llave:valor, se mapea "llave" contra los "name" del formulario)
 */
function _cargarForm(form,datos)
{
    debug('>_cargarForm form:',form,',datos:',datos);
    var ck = 'Cargando formulario';
    try
    {
        ck = 'Cargando '+((''+form.title).toLowerCase());
        for(var prop in datos)
        {
            var value = datos[prop];
            var item  = form.down('[name='+prop+']');
            if(Ext.isEmpty(item))
            {
                continue;
            }
            if(!Ext.isEmpty(item.store))//cuando es combo
            {
                if(item.store.getCount()==0)//aun no ha cargado
                {
                    item.valorPendiente = value;
                    item.store.padre    = item;
                    item.store.on(
                    {
                        load : function(me,records,success)
                        {
                            if(success&&!Ext.isEmpty(me.padre.valorPendiente)&&me.padre.valorPendiente!=false)
                            {
                                me.padre.setValue(me.padre.valorPendiente);
                                me.padre.valorPendiente = false;
                            }
                        }
                    });
                }
                else//ya esta cargado
                {
                    item.setValue(value);
                }
            }
            else//cuando no es combo
            {
                item.setValue(value);
            }
        }
    }
    catch(e)
    {
        manejaException(e,ck);
    }
}

/**
 * Funcion que le pone mask a un componente
 * @param load (boolean) activa o desactiva
 * @param cmp (Component) componente al que se aplica/quita la mascara
 */
function _setLoading(load,cmp)
{
    debug('_setLoading load:',load,'cmp:',cmp);
    var ck = 'Manejando m\u00E1scara';
    try
    {
        if(Ext.isEmpty(load))
        {
            throw 'No se recibi\u00F3 par\u00E1metro 1 de m\u00E1scara';
        }
        if(Ext.isEmpty(cmp))
        {
            throw 'No se recibi\u00F3 par\u00E1metro 2 de m\u00E1scara';
        }
        if(typeof cmp == 'string')
        {
            var cmp2 = _fieldById(cmp);
            if(Ext.isEmpty(cmp2))
            {
                throw 'No se encuentra el componente con id '+cmp;
            }
            cmp = cmp2;
        }
        if(true==load)
        {
            ck = 'Poniendo m\u00E1scara';
            cmp.mascara = new Ext.LoadMask(cmp.el,{ msg:"Cargando..." });
            cmp.mascara.show();
        }
        else
        {
            ck = 'Quitando m\u00E1scara';
            cmp.mascara.hide();
        }
    }
    catch(e)
    {
        manejaException(e,ck);
    }
}

/**
 * RECORTA UNA CADENA HASTA SU PRIMER COMA, SIN INCLUIR LA PRIMER COMA
 * SINO TIENE COMAS O VIENE VACIA LA REGRESA TAL CUAL LA RECIBE
 *
 */
function _substringComa(cadena)
{
    //debug('_substringComa cadena:',cadena,'.');
    if(!Ext.isEmpty(cadena)&&cadena.indexOf(',')!=-1)
    {
        cadena = cadena.substring(0,cadena.indexOf(','));
    }
    return cadena;
}

/**
 * RECIBE SPLIT COMO UN DECODE
 * [1|UNO|2|DOS|3|TRES]
 * viene en pares clave|valor
 *
 */
function rendererSplits(value,splits)
{
    try
    {
        var tokens = splits.replace('[','').replace(']','').split('|');
        for(var i=0;i<tokens.length;i=i+2)
        {
            if(tokens[i]==value)
            {
                value = tokens[i+1];
                break;
            }
        }
    }
    catch(e)
    {
        debugError(e);
        mensajeError('Split incorrecto ('+value+','+splits+')');
    }
    return value;
}

/**
 * FUNCION PARA RECUPRAR VALOR DE UN COMPONENTE
 * PUEDE NO EXISTIR
 */
function _getValueByName(compName,showError,parent)
{
    debug('_getValueByName(compName,showError,parent)[',compName,showError,parent,']');
    if(Ext.isEmpty(showError)
        ||(showError!=true && showError!=false)
    )
    {
        showError = true;
    }
    
    var cmp = _fieldByName(compName,parent,!showError);
    var val;
    
    if(!Ext.isEmpty(cmp))
    {
        val = cmp.getValue();
    }
    
    return val;
}

//TRANSFORMA UN OBJETO, PONIENDO params. ANTES DE TODOS SUS ATRIBUTOS
function _formValuesToParams(formValues)
{
    var params = {};
    for(var att in formValues)
    {
        params['params.'+att] = formValues[att];
    }
    return params;
}

function _cargarBotonesEntidad(
    cdtipflu
    ,cdflujomc
    ,tipoent
    ,cdentidad
    ,webid
    ,callback
    ,ntramite
    ,status
    ,cdunieco
    ,cdramo
    ,estado
    ,nmpoliza
    ,nmsituac
    ,nmsuplem
    ,callbackDespuesProceso
    )
{
    debug('_cargarBotonesEntidad cdtipflu:'  , cdtipflu  , '.');
    debug('_cargarBotonesEntidad cdflujomc:' , cdflujomc , '.');
    debug('_cargarBotonesEntidad tipoent:'   , tipoent   , '.');
    debug('_cargarBotonesEntidad cdentidad:' , cdentidad , '.');
    debug('_cargarBotonesEntidad webid:'     , webid     , '.');
    debug('_cargarBotonesEntidad callback:'  , callback  , '.');
    debug('_cargarBotonesEntidad ntramite:'  , ntramite  , '.');
    debug('_cargarBotonesEntidad status:'    , status    , '.');
    debug('_cargarBotonesEntidad cdunieco:'  , cdunieco  , '.');
    debug('_cargarBotonesEntidad cdramo:'    , cdramo    , '.');
    debug('_cargarBotonesEntidad estado:'    , estado    , '.');
    debug('_cargarBotonesEntidad nmpoliza:'  , nmpoliza  , '.');
    debug('_cargarBotonesEntidad nmsituac:'  , nmsituac  , '.');
    debug('_cargarBotonesEntidad nmsuplem:'  , nmsuplem  , '.');
    
    debug('_cargarBotonesEntidad callbackDespuesProceso?:'  , !Ext.isEmpty(callbackDespuesProceso)  , '.');
    
    var ck = 'Cargando acciones del proceso';
    try
    {
        _mask(ck);
        Ext.Ajax.request(
        {
            url      : _GLOBAL_URL_CARGAR_ACCIONES_ENTIDAD
            ,params  :
            {
                'params.cdtipflu'   : cdtipflu
                ,'params.cdflujomc' : cdflujomc
                ,'params.tipoent'   : tipoent
                ,'params.cdentidad' : cdentidad
                ,'params.webid'     : webid
            }
            ,success : function(response)
            {
                _unmask();
                var ck = 'Decodificando respuesta al cargar acciones de proceso';
                try
                {
                    var json = Ext.decode(response.responseText);
                    debug('### acciones:',json);
                    if(json.success==true)
                    {
                        var botones = [];
                        for(var i in json.list)
                        {
                            var datos = json.list[i];
                            botones.push(Ext.create('Ext.Button',
                            {
                                text       : _NVL(datos.DSACCION,'(-SIN NOMBRE-)')
                                ,icon      : _GLOBAL_DIRECTORIO_ICONOS+_NVL(datos.CDICONO,'application_xp_terminal')+'.png'
                                ,cdtipflu  : cdtipflu
                                ,cdflujomc : cdflujomc
                                ,tipodest  : ''+_NVL(datos.TIPODEST,'')
                                ,clavedest : ''+_NVL(datos.CLAVEDEST,'')
                                ,webiddest : ''+_NVL(datos.WEBIDDEST,'')
                                ,aux       : ''+_NVL(datos.AUX,'')
                                ,ntramite  : ntramite
                                ,status    : status
                                ,cdunieco  : cdunieco
                                ,cdramo    : cdramo
                                ,estado    : estado
                                ,nmpoliza  : nmpoliza
                                ,nmsituac  : nmsituac
                                ,nmsuplem  : nmsuplem
                                ,cdusuari  : ''+_NVL(json.params.cdusuari,'')
                                ,cdsisrol  : ''+_NVL(json.params.cdsisrol,'')
                                ,handler   : function(me){ _botonEntidadClic(me,callbackDespuesProceso); }
                            }));
                        }
                        if(typeof callback == 'string')
                        {
                            var comp = _fieldById(callback);
                            comp.down('toolbar').add(botones);
                        }
                        else
                        {
                            callback(botones);
                        }
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
                errorComunicacion(null,'Error al cargar acciones de proceso');
            }
        });
    }
    catch(e)
    {
        manejaException(e,ck);
    }
    return [];
}

/**
 * Equivalente a NVL base de datos
 */
function _NVL(origen,valor)
{
    if(Ext.isEmpty(origen))
    {
        return !Ext.isEmpty(valor) ? valor : '';
    }
    return origen;
}

function _botonEntidadClic(bot,callbackDespuesProceso)
{
    debug('_botonEntidadClic bot,callbackDespuesProceso?:',bot,!Ext.isEmpty(callbackDespuesProceso),'.');
    var ck = 'Recuperando datos de acci\u00f3n';
    try
    {
        var cdtipflu   = bot.cdtipflu
            ,cdflujomc = bot.cdflujomc
            ,tipodest  = bot.tipodest
            ,clavedest = bot.clavedest
            ,webiddest = bot.webiddest
            ,aux       = bot.aux
            ,ntramite  = bot.ntramite
            ,status    = bot.status
            ,cdunieco  = bot.cdunieco
            ,cdramo    = bot.cdramo
            ,estado    = bot.estado
            ,nmpoliza  = bot.nmpoliza
            ,nmsituac  = bot.nmsituac
            ,nmsuplem  = bot.nmsuplem
            ,cdusuari  = bot.cdusuari
            ,cdsisrol  = bot.cdsisrol;
        
        debug('_botonEntidadClic cdtipflu:'  , cdtipflu  , '.');
        debug('_botonEntidadClic cdflujomc:' , cdflujomc , '.');
        debug('_botonEntidadClic tipodest:'  , tipodest  , '.');
        debug('_botonEntidadClic clavedest:' , clavedest , '.');
        debug('_botonEntidadClic webiddest:' , webiddest , '.');
        debug('_botonEntidadClic aux:'       , aux       , '.');
        debug('_botonEntidadClic ntramite:'  , ntramite  , '.');
        debug('_botonEntidadClic status:'    , status    , '.');
        debug('_botonEntidadClic cdunieco:'  , cdunieco  , '.');
        debug('_botonEntidadClic cdramo:'    , cdramo    , '.');
        debug('_botonEntidadClic estado:'    , estado    , '.');
        debug('_botonEntidadClic nmpoliza:'  , nmpoliza  , '.');
        debug('_botonEntidadClic nmsituac:'  , nmsituac  , '.');
        debug('_botonEntidadClic nmsuplem:'  , nmsuplem  , '.');
        debug('_botonEntidadClic cdusuari:'  , cdusuari  , '.');
        debug('_botonEntidadClic cdsisrol:'  , cdsisrol  , '.');
        
        var callback;
        
        if(Ext.isEmpty(callbackDespuesProceso))
        {
            callback = function()
            {
                bot.up('window').destroy();
            };
        }
        else
        {
            callback = function()
            {
                bot.up('window').destroy();
                callbackDespuesProceso();
            };
        }
        
        _procesaAccion(
            cdtipflu
            ,cdflujomc
            ,tipodest
            ,clavedest
            ,webiddest
            ,aux
            ,ntramite
            ,status
            ,cdunieco
            ,cdramo
            ,estado
            ,nmpoliza
            ,nmsituac
            ,nmsuplem
            ,cdusuari
            ,cdsisrol
            ,callback
        );
    }
    catch(e)
    {
        manejaException(e,ck);
    }
}

function _procesaAccion(
    cdtipflu
    ,cdflujomc
    ,tipodest
    ,clavedest
    ,webiddest
    ,aux
    ,ntramite
    ,status
    ,cdunieco
    ,cdramo
    ,estado
    ,nmpoliza
    ,nmsituac
    ,nmsuplem
    ,cdusuari
    ,cdsisrol
    ,callback
    )
{
    debug('_procesaAccion cdtipflu:'  , cdtipflu  , '.');
    debug('_procesaAccion cdflujomc:' , cdflujomc , '.');
    debug('_procesaAccion tipodest:'  , tipodest  , '.');
    debug('_procesaAccion clavedest:' , clavedest , '.');
    debug('_procesaAccion webiddest:' , webiddest , '.');
    debug('_procesaAccion aux:'       , aux       , '.');
    debug('_procesaAccion ntramite:'  , ntramite  , '.');
    debug('_procesaAccion status:'    , status    , '.');
    debug('_procesaAccion cdunieco:'  , cdunieco  , '.');
    debug('_procesaAccion cdramo:'    , cdramo    , '.');
    debug('_procesaAccion estado:'    , estado    , '.');
    debug('_procesaAccion nmpoliza:'  , nmpoliza  , '.');
    debug('_procesaAccion nmsituac:'  , nmsituac  , '.');
    debug('_procesaAccion nmsuplem:'  , nmsuplem  , '.');
    debug('_procesaAccion cdusuari:'  , cdusuari  , '.');
    debug('_procesaAccion cdsisrol:'  , cdsisrol  , '.');
    debug('_procesaAccion callback?:' , !Ext.isEmpty(callback) , '.');
    var ck = 'Procesando acci\u00f3n';
    try
    {
        if(tipodest=='E')
        {
            ck = 'Recuperando valores de status';
            _mask(ck);
            Ext.Ajax.request(
            {
                url      : _GLOBAL_URL_RECUPERACION
                ,params  :
                {
                    'params.consulta'    : 'RECUPERAR_TFLUEST'
                    ,'params.cdtipflu'   : cdtipflu
                    ,'params.cdflujomc'  : cdflujomc
                    //,'params.cdestadomc' : clavedest
                }
                ,success : function(response)
                {
                    _unmask();
                    var ck = 'Decodificando respuesta al recuperar valores de status';
                    try
                    {
                        var json = Ext.decode(response.responseText);
                        debug('### tfluest:',json);
                        if(json.success==true)
                        {
                            var estadoOld, estadoNew;
                            for(var i=0 ; i<json.list.length ; i++)
                            {
                                var estadoIte = json.list[i];
                                if(Number(estadoIte.CDESTADOMC)==Number(status))
                                {
                                    estadoOld =
                                    {
                                        CDTIPASIG : ''+estadoIte.CDTIPASIG
                                    };
                                }
                                if(Number(estadoIte.CDESTADOMC)==Number(clavedest))
                                {
                                    estadoNew =
                                    {
                                        CDTIPASIG : ''+estadoIte.CDTIPASIG
                                    };
                                }
                            }
                            
                            if(Ext.isEmpty(estadoOld)||Ext.isEmpty(estadoNew))
                            {
                                throw 'No se encuentran los status de turnado';
                            }
                            
                            ck = 'Turnando tr\u00e1mite';
                            _mask(ck);
                            Ext.Ajax.request(
                            {
                                url      : _GLOBAL_URL_TURNAR
                                ,params  :
                                {
                                    'params.ntramite'      : ntramite
                                    ,'params.statusOld'    : status
                                    ,'params.cdtipasigOld' : estadoOld.CDTIPASIG
                                    ,'params.statusNew'    : clavedest
                                    ,'params.cdtipasigNew' : estadoNew.CDTIPASIG
                                }
                                ,success : function(response)
                                {
                                    _unmask();
                                    var ck = 'Decodificando respuesta al turnar tr\u00e1mite';
                                    try
                                    {
                                        var json = Ext.decode(response.responseText);
                                        debug('### turnar:',json);
                                        if(json.success==true)
                                        {
                                            mensajeCorrecto('Tr\u00e1mite turnado',json.message,callback);
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
                    errorComunicacion(null,'Error al recuperar valores de status');
                }
            });
        }
        else if(tipodest=='P')
        {
            ck = 'Recuperando valores de pantalla';
            _mask(ck);
            Ext.Ajax.request(
            {
                url      : _GLOBAL_URL_RECUPERACION
                ,params  :
                {
                    'params.consulta'  : 'RECUPERAR_TPANTMC'
                    ,'params.CDPANTMC' : clavedest
                }
                ,success : function(response)
                {
                    _unmask();
                    var ck = 'Decodificando respuesta al recuperar valores de pantalla';
                    try
                    {
                        var json = Ext.decode(response.responseText);
                        debug('### tpantmc:',json);
                        if(json.success==true)
                        {
                            if(json.list.length==0)
                            {
                                throw 'La pantalla no existe';
                            }
                            else if(json.list.length>1)
                            {
                                throw 'Pantalla duplicada';
                            }
                            var pantalla = json.list[0];
                            debug('pantalla:',pantalla);
                            ck = 'Enviando petici\u00f3n';
                            _mask('Redireccionando');
                            Ext.create('Ext.form.Panel').submit(
                            {
                                url             : _NVL(pantalla.SWEXTERNA,'N')=='N' ?
                                                      _GLOBAL_CONTEXTO+pantalla.URLPANTMC :
                                                      _GLOBAL_URL_PANTALLA_EXTERNA
                                ,standardSubmit : true
                                //,target         : '_top'
                                ,params         :
                                {
                                    'flujo.cdtipflu'   : cdtipflu
                                    ,'flujo.cdflujomc' : cdflujomc
                                    ,'flujo.tipoent'   : tipodest
                                    ,'flujo.claveent'  : clavedest
                                    ,'flujo.webid'     : webiddest
                                    ,'flujo.ntramite'  : ntramite
                                    ,'flujo.status'    : status
                                    ,'flujo.cdunieco'  : cdunieco
                                    ,'flujo.cdramo'    : cdramo
                                    ,'flujo.estado'    : estado
                                    ,'flujo.nmpoliza'  : nmpoliza
                                    ,'flujo.nmsituac'  : nmsituac
                                    ,'flujo.nmsuplem'  : nmsuplem
                                    ,'flujo.aux'       : aux
                                    ,'params.url'      : _NVL(pantalla.SWEXTERNA,'N')=='N' ?
                                                             '' :
                                                             pantalla.URLPANTMC
                                }
                            });
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
                    errorComunicacion(null,'Error al recuperar valores de pantalla');
                }
            });
        }
        else if(tipodest=='C')
        {
            ck = 'Recuperando valores de componente';
            _mask(ck);
            Ext.Ajax.request(
            {
                url      : _GLOBAL_URL_RECUPERACION
                ,params  :
                {
                    'params.consulta'  : 'RECUPERAR_TCOMPMC'
                    ,'params.CDCOMPMC' : clavedest
                }
                ,success : function(response)
                {
                    _unmask();
                    var ck = 'Decodificando respuesta al recuperar valores de componente';
                    try
                    {
                        var json = Ext.decode(response.responseText);
                        debug('### tcompmc:',json);
                        if(json.success==true)
                        {
                            if(json.list.length==0)
                            {
                                throw 'El componente no existe';
                            }
                            else if(json.list.length>1)
                            {
                                throw 'Componente duplicado';
                            }
                            var compData = json.list[0];
                            debug('compData:',compData);
                            
                            ck = 'Creando componente';
                            Ext.syncRequire(_GLOBAL_DIRECTORIO_DEFINES+compData.NOMCOMP);
                            new window[compData.NOMCOMP](
                            {
                                cdtipflu   : cdtipflu
                                ,cdflujomc : cdflujomc
                                ,tipoent   : tipodest
                                ,claveent  : clavedest
                                ,webid     : webiddest
                                ,aux       : aux
                                ,ntramite  : ntramite
                                ,status    : status
                                ,cdunieco  : cdunieco
                                ,cdramo    : cdramo
                                ,estado    : estado
                                ,nmpoliza  : nmpoliza
                                ,nmsituac  : nmsituac
                                ,nmsuplem  : nmsuplem
                                ,cdusuari  : cdusuari
                                ,cdsisrol  : cdsisrol
                            }).mostrar();
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
                    errorComunicacion(null,'Error al recuperar valores de componente');
                }
            });
        }
        else if(tipodest=='O')
        {
            ck = 'Recuperando valores de proceso';
            _mask(ck);
            Ext.Ajax.request(
            {
                url      : _GLOBAL_URL_RECUPERACION
                ,params  :
                {
                    'params.consulta'  : 'RECUPERAR_TPROCMC'
                    ,'params.CDPROCMC' : clavedest
                }
                ,success : function(response)
                {
                    _unmask();
                    var ck = 'Decodificando respuesta al recuperar valores de proceso';
                    try
                    {
                        var json = Ext.decode(response.responseText);
                        debug('### tprocmc:',json);
                        if(json.success==true)
                        {
                            if(json.list.length==0)
                            {
                                throw 'El proceso no existe';
                            }
                            else if(json.list.length>1)
                            {
                                throw 'Proceso duplicado';
                            }
                            var data = json.list[0];
                            debug('data:',data);
                            
                            ck = 'Recuperando acciones posteriores al proceso';
                            _mask(ck);
                            Ext.Ajax.request(
                            {
                                url      : _GLOBAL_URL_CARGAR_ACCIONES_ENTIDAD
                                ,params  :
                                {
                                    'params.cdtipflu'   : cdtipflu
                                    ,'params.cdflujomc' : cdflujomc
                                    ,'params.tipoent'   : tipodest
                                    ,'params.cdentidad' : clavedest
                                    ,'params.webid'     : webiddest
                                }
                                ,success : function(response)
                                {
                                    _unmask();
                                    var ck = 'Decodificando respuesta al recuperar acciones posteriores al proceso';
                                    try
                                    {
                                        var json = Ext.decode(response.responseText);
                                        debug('### acciones:',json);
                                        var numSalidas = 0
                                            ,accion1   = ''
                                            ,accion2   = ''
                                            ,accExito  = ''
                                            ,accError  = '';
                                        if(json.success==true)
                                        {
                                            /*
                                            CDACCION: "138"
                                            CDCOMPMC: null
                                            CDESTADOMC: null
                                            CDFLUJOMC: "12"
                                            CDICONO: null
                                            CDPANTMC: null
                                            CDPROCMC: null
                                            CDREVISI: null
                                            CDTIPFLU: "1"
                                            CDVALIDA: "20"
                                            CDVALOR: null
                                            CLAVEDEST: "20"
                                            DSACCION: "next"
                                            IDDESTIN: "1450139785755_1072"
                                            IDORIGEN: "1450121226109_9387"
                                            TIPODEST: "V"
                                            WEBIDCOMP: null
                                            WEBIDDEST: "1450139785755_1072"
                                            WEBIDESTADO: null
                                            WEBIDPANT: null
                                            WEBIDPROC: null
                                            WEBIDREVISI: null
                                            WEBIDVALIDA: "1450139785755_1072"
                                            */
                                            if(json.list.length==1)
                                            {
                                                numSalidas = 1;
                                                accion1    = json.list[0];
                                            }
                                            else if(json.list.length==2)
                                            {
                                                numSalidas = 2;
                                                accion1    = json.list[0];
                                                accion2    = json.list[1];
                                                
                                                if(_NVL(accion1.CDVALOR,'')=='EXITO')
                                                {
                                                    accExito = accion1;
                                                }
                                                else if(_NVL(accion2.CDVALOR,'')=='EXITO')
                                                {
                                                    accExito = accion2;
                                                }
                                                if(_NVL(accion1.CDVALOR,'')=='ERROR')
                                                {
                                                    accError = accion1;
                                                }
                                                else if(_NVL(accion2.CDVALOR,'')=='ERROR')
                                                {
                                                    accError = accion2;
                                                }
                                                
                                                if(Ext.isEmpty(accExito)||Ext.isEmpty(accError))
                                                {
                                                    throw 'Las acciones relacionadas al proceso no tienen el valor EXITO/ERROR adecuado';
                                                }
                                            }
                                            else if(json.list.length>2)
                                            {
                                                throw 'El proceso tiene demasiadas acciones relacionadas';
                                            }
                                            
                                            ck = 'Ejecutando proceso';
                                            _mask(ck);
                                            Ext.Ajax.request(
                                            {
                                                url      : _GLOBAL_CONTEXTO+data.URLPROCMC
                                                ,params  :
                                                {
                                                    'flujo.cdtipflu'   : cdtipflu
                                                    ,'flujo.cdflujomc' : cdflujomc
                                                    ,'flujo.tipoent'   : tipodest
                                                    ,'flujo.claveent'  : clavedest
                                                    ,'flujo.webid'     : webiddest
                                                    ,'flujo.ntramite'  : ntramite
                                                    ,'flujo.status'    : status
                                                    ,'flujo.cdunieco'  : cdunieco
                                                    ,'flujo.cdramo'    : cdramo
                                                    ,'flujo.estado'    : estado
                                                    ,'flujo.nmpoliza'  : nmpoliza
                                                    ,'flujo.nmsituac'  : nmsituac
                                                    ,'flujo.nmsuplem'  : nmsuplem
                                                }
                                                ,success : function(response)
                                                {
                                                    _unmask();
                                                    var ck = 'Decodificando respuesta al ejecutar proceso';
                                                    try
                                                    {
                                                        var json = Ext.decode(response.responseText);
                                                        debug('### proceso:',json);
                                                        if(json.success==true)
                                                        {
                                                            if(numSalidas==0)
                                                            {
                                                                mensajeCorrecto('AVISO',json.message,callback);
                                                            }
                                                            else if(numSalidas==1)
                                                            {
                                                                mensajeCorrecto
                                                                (
                                                                    'AVISO'
                                                                    ,json.message
                                                                    ,function()
                                                                    {
                                                                        _procesaAccion
                                                                        (
                                                                            cdtipflu
                                                                            ,cdflujomc
                                                                            ,accion1.TIPODEST
                                                                            ,accion1.CLAVEDEST
                                                                            ,accion1.WEBIDDEST
                                                                            ,aux
                                                                            ,ntramite
                                                                            ,status
                                                                            ,cdunieco
                                                                            ,cdramo
                                                                            ,estado
                                                                            ,nmpoliza
                                                                            ,nmsituac
                                                                            ,nmsuplem
                                                                            ,cdusuari
                                                                            ,cdsisrol
                                                                            ,callback
                                                                        );
                                                                    }
                                                                );
                                                            }
                                                            else if(numSalidas==2)
                                                            {
                                                                mensajeCorrecto
                                                                (
                                                                    'AVISO'
                                                                    ,json.message
                                                                    ,function()
                                                                    {
                                                                        _procesaAccion
                                                                        (
                                                                            cdtipflu
                                                                            ,cdflujomc
                                                                            ,accExito.TIPODEST
                                                                            ,accExito.CLAVEDEST
                                                                            ,accExito.WEBIDDEST
                                                                            ,aux
                                                                            ,ntramite
                                                                            ,status
                                                                            ,cdunieco
                                                                            ,cdramo
                                                                            ,estado
                                                                            ,nmpoliza
                                                                            ,nmsituac
                                                                            ,nmsuplem
                                                                            ,cdusuari
                                                                            ,cdsisrol
                                                                            ,callback
                                                                        );
                                                                    }
                                                                );
                                                            }
                                                        }
                                                        else
                                                        {
                                                            if(numSalidas<2)
                                                            {
                                                                mensajeError(json.message);
                                                            }
                                                            else
                                                            {
                                                                mensajeError
                                                                (
                                                                    json.message
                                                                    ,function()
                                                                    {
                                                                        _procesaAccion
                                                                        (
                                                                            cdtipflu
                                                                            ,cdflujomc
                                                                            ,accError.TIPODEST
                                                                            ,accError.CLAVEDEST
                                                                            ,accError.WEBIDDEST
                                                                            ,aux
                                                                            ,ntramite
                                                                            ,status
                                                                            ,cdunieco
                                                                            ,cdramo
                                                                            ,estado
                                                                            ,nmpoliza
                                                                            ,nmsituac
                                                                            ,nmsuplem
                                                                            ,cdusuari
                                                                            ,cdsisrol
                                                                            ,callback
                                                                        );
                                                                    }
                                                                );
                                                            }
                                                        }
                                                    }
                                                    catch(e)
                                                    {
                                                        if(numSalidas<2)
                                                        {
                                                            manejaException(e,ck);
                                                        }
                                                        else
                                                        {
                                                            debugError(e);
                                                            mensajeError
                                                            (
                                                                'Error al manejar respuesta de proceso'
                                                                ,function()
                                                                {
                                                                    _procesaAccion
                                                                    (
                                                                        cdtipflu
                                                                        ,cdflujomc
                                                                        ,accError.TIPODEST
                                                                        ,accError.CLAVEDEST
                                                                        ,accError.WEBIDDEST
                                                                        ,aux
                                                                        ,ntramite
                                                                        ,status
                                                                        ,cdunieco
                                                                        ,cdramo
                                                                        ,estado
                                                                        ,nmpoliza
                                                                        ,nmsituac
                                                                        ,nmsuplem
                                                                        ,cdusuari
                                                                        ,cdsisrol
                                                                        ,callback
                                                                    );
                                                                }
                                                            );
                                                        }
                                                    }
                                                }
                                                ,failure : function(response)
                                                {
                                                    _unmask();
                                                    if(numSalidas<2)
                                                    {
                                                        errorComunicacion(null,'Error al ejecutar proceso');
                                                    }
                                                    else
                                                    {
                                                        mensajeError(
                                                            'Error de comunicaci\u00f3n al ejecutar proceso'
                                                            ,function()
                                                            {
                                                                _procesaAccion
                                                                (
                                                                    cdtipflu
                                                                    ,cdflujomc
                                                                    ,accError.TIPODEST
                                                                    ,accError.CLAVEDEST
                                                                    ,accError.WEBIDDEST
                                                                    ,aux
                                                                    ,ntramite
                                                                    ,status
                                                                    ,cdunieco
                                                                    ,cdramo
                                                                    ,estado
                                                                    ,nmpoliza
                                                                    ,nmsituac
                                                                    ,nmsuplem
                                                                    ,cdusuari
                                                                    ,cdsisrol
                                                                    ,callback
                                                                );
                                                            }
                                                        );
                                                    }
                                                }
                                            });
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
                                    errorComunicacion(null,'Error al recuperar acciones posteriores al proceso');
                                }
                            });
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
                    errorComunicacion(null,'Error al recuperar valores de proceso');
                }
            });
        }
        else if(tipodest=='V')
        {
            ck = 'Recuperando valores de validaci\u00f3n';
            _mask(ck);
            Ext.Ajax.request(
            {
                url      : _GLOBAL_URL_RECUPERACION
                ,params  :
                {
                    'params.consulta'   : 'RECUPERAR_TFLUVAL'
                    ,'params.cdtipflu'  : cdtipflu
                    ,'params.cdflujomc' : cdflujomc
                    ,'params.cdvalida'  : clavedest
                }
                ,success : function(response)
                {
                    _unmask();
                    var ck = 'Decodificando respuesta al recuperar valores de validaci\u00f3n';
                    try
                    {
                        var json = Ext.decode(response.responseText);
                        debug('### tfluval:',json);
                        if(json.success==true)
                        {
                            if(json.list.length==0)
                            {
                                throw 'La validaci\u00f3n no existe';
                            }
                            else if(json.list.length>1)
                            {
                                throw 'Validaci\u00f3n duplicada';
                            }
                            var data = json.list[0];
                            debug('data:',data);
                            
                            var cliente = false;
                            if('x'+data.CDVALIDAFK=='x-1')
                            {
                                if(Ext.isEmpty(data.JSVALIDA))
                                {
                                    throw 'La validaci\u00f3n no cuenta con una clave ni validaci\u00f3n cliente';
                                }
                                else
                                {
                                    cliente = true;
                                }
                            }
                            
                            ck = 'Recuperando acciones posteriores a la validaci\u00f3n';
                            _mask(ck);
                            Ext.Ajax.request(
                            {
                                url      : _GLOBAL_URL_CARGAR_ACCIONES_ENTIDAD
                                ,params  :
                                {
                                    'params.cdtipflu'   : cdtipflu
                                    ,'params.cdflujomc' : cdflujomc
                                    ,'params.tipoent'   : tipodest
                                    ,'params.cdentidad' : clavedest
                                    ,'params.webid'     : webiddest
                                }
                                ,success : function(response)
                                {
                                    _unmask();
                                    var ck = 'Decodificando respuesta al recuperar acciones posteriores a la validaci\u00f3n';
                                    try
                                    {
                                        var json = Ext.decode(response.responseText);
                                        debug('### acciones:',json);
                                        var numSalidas = json.list.length
                                            ,acciones  = json.list;
                                        if(json.success==true)
                                        {
                                            /*
                                            CDACCION: "138"
                                            CDCOMPMC: null
                                            CDESTADOMC: null
                                            CDFLUJOMC: "12"
                                            CDICONO: null
                                            CDPANTMC: null
                                            CDPROCMC: null
                                            CDREVISI: null
                                            CDTIPFLU: "1"
                                            CDVALIDA: "20"
                                            CDVALOR: null
                                            CLAVEDEST: "20"
                                            DSACCION: "next"
                                            IDDESTIN: "1450139785755_1072"
                                            IDORIGEN: "1450121226109_9387"
                                            TIPODEST: "V"
                                            WEBIDCOMP: null
                                            WEBIDDEST: "1450139785755_1072"
                                            WEBIDESTADO: null
                                            WEBIDPANT: null
                                            WEBIDPROC: null
                                            WEBIDREVISI: null
                                            WEBIDVALIDA: "1450139785755_1072"
                                            */
                                            if(json.list.length==0)
                                            {
                                                throw 'No hay acciones relacionadas a la validaci\u00f3n';
                                            }
                                            for(var i=0 ; i<numSalidas ; i++)
                                            {
                                                if(Ext.isEmpty(acciones[i].CDVALOR))
                                                {
                                                    throw 'Las acciones relacionadas a la validaci\u00f3n no tienen valor';
                                                }
                                            }
                                            
                                            if(!cliente)
                                            {
                                                ck = 'Ejecutando validaci\u00f3n';
                                                _mask(ck);
                                                Ext.Ajax.request(
                                                {
                                                    url      : _GLOBAL_URL_VALIDACION
                                                    ,params  :
                                                    {
                                                        'flujo.cdtipflu'     : cdtipflu
                                                        ,'flujo.cdflujomc'   : cdflujomc
                                                        ,'flujo.tipoent'     : tipodest
                                                        ,'flujo.claveent'    : clavedest
                                                        ,'flujo.webid'       : webiddest
                                                        ,'flujo.ntramite'    : ntramite
                                                        ,'flujo.status'      : status
                                                        ,'flujo.cdunieco'    : cdunieco
                                                        ,'flujo.cdramo'      : cdramo
                                                        ,'flujo.estado'      : estado
                                                        ,'flujo.nmpoliza'    : nmpoliza
                                                        ,'flujo.nmsituac'    : nmsituac
                                                        ,'flujo.nmsuplem'    : nmsuplem
                                                        ,'params.cdvalidafk' : data.CDVALIDAFK
                                                    }
                                                    ,success : function(response)
                                                    {
                                                        _unmask();
                                                        var ck = 'Decodificando respuesta al ejecutar validaci\u00f3n';
                                                        try
                                                        {
                                                            var json = Ext.decode(response.responseText);
                                                            debug('### validacion:',json);
                                                            if(json.success==true)
                                                            {
                                                                var valorRespValid = json.params.salida
                                                                    ,salida        = '';
                                                                for(var i=0 ; i<numSalidas ; i++)
                                                                {
                                                                    if('x'+acciones[i].CDVALOR=='x'+valorRespValid)
                                                                    {
                                                                        salida = acciones[i];
                                                                        break;
                                                                    }
                                                                }
                                                                if(Ext.isEmpty(salida))
                                                                {
                                                                    throw 'La validaci\u00f3n regres\u00f3 un valor que no tiene acci\u00f3n relacionada';
                                                                }
                                                                _procesaAccion
                                                                (
                                                                    cdtipflu
                                                                    ,cdflujomc
                                                                    ,salida.TIPODEST
                                                                    ,salida.CLAVEDEST
                                                                    ,salida.WEBIDDEST
                                                                    ,salida.AUX
                                                                    ,ntramite
                                                                    ,status
                                                                    ,cdunieco
                                                                    ,cdramo
                                                                    ,estado
                                                                    ,nmpoliza
                                                                    ,nmsituac
                                                                    ,nmsuplem
                                                                    ,cdusuari
                                                                    ,cdsisrol
                                                                    ,callback
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
                                                    ,failure : function(response)
                                                    {
                                                        _unmask();
                                                        errorComunicacion(null,'Error al ejecutar validaci\u00f3n');
                                                    }
                                                });
                                            }
                                            else//evaluacion cliente
                                            {
                                                ck = 'Recuperando datos para validaci\u00f3n cliente';
                                                _mask(ck);
                                                Ext.Ajax.request(
                                                {
                                                    url      : _GLOBAL_URL_VALIDACION_MONTAR_DATOS
                                                    ,params  :
                                                    {
                                                        'flujo.cdtipflu'   : cdtipflu
                                                        ,'flujo.cdflujomc' : cdflujomc
                                                        ,'flujo.tipoent'   : tipodest
                                                        ,'flujo.claveent'  : clavedest
                                                        ,'flujo.webid'     : webiddest
                                                        ,'flujo.ntramite'  : ntramite
                                                        ,'flujo.status'    : status
                                                        ,'flujo.cdunieco'  : cdunieco
                                                        ,'flujo.cdramo'    : cdramo
                                                        ,'flujo.estado'    : estado
                                                        ,'flujo.nmpoliza'  : nmpoliza
                                                        ,'flujo.nmsituac'  : nmsituac
                                                        ,'flujo.nmsuplem'  : nmsuplem
                                                    }
                                                    ,success : function(response)
                                                    {
                                                        _unmask();
                                                        var ck = 'Decodificando respuesta al recuperar datos para validaci\u00f3n cliente';
                                                        try
                                                        {
                                                            var json = Ext.decode(response.responseText);
                                                            debug('### datos memoria validacion javascript:',json);
                                                            if(json.success==true)
                                                            {
                                                                ck = 'Construyendo validaci\u00f3n cliente';
                                                                eval('var validacionCliente=function(DATOS){debug("DATOS:",DATOS);'+data.JSVALIDA+'};');
                                                                debug('validacionCliente:',validacionCliente);
                                                                
                                                                ck = 'Invocando validaci\u00f3n cliente';
                                                                var valorRespValid = validacionCliente(json.datosTramite)
                                                                    ,salida        = '';
                                                                for(var i=0 ; i<numSalidas ; i++)
                                                                {
                                                                    if('x'+acciones[i].CDVALOR=='x'+valorRespValid)
                                                                    {
                                                                        salida = acciones[i];
                                                                        break;
                                                                    }
                                                                }
                                                                if(Ext.isEmpty(salida))
                                                                {
                                                                    throw 'La validaci\u00f3n cliente regres\u00f3 un valor que no tiene acci\u00f3n relacionada';
                                                                }
                                                                _procesaAccion
                                                                (
                                                                    cdtipflu
                                                                    ,cdflujomc
                                                                    ,salida.TIPODEST
                                                                    ,salida.CLAVEDEST
                                                                    ,salida.WEBIDDEST
                                                                    ,salida.AUX
                                                                    ,ntramite
                                                                    ,status
                                                                    ,cdunieco
                                                                    ,cdramo
                                                                    ,estado
                                                                    ,nmpoliza
                                                                    ,nmsituac
                                                                    ,nmsuplem
                                                                    ,cdusuari
                                                                    ,cdsisrol
                                                                    ,callback
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
                                                        errorComunicacion(null,'Error al ejecutar validaci\u00f3n cliente');
                                                    }
                                                });
                                            }
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
                                    errorComunicacion(null,'Error al recuperar acciones posteriores a la validaci\u00f3n');
                                }
                            });
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
                    errorComunicacion(null,'Error al recuperar valores de validaci\u00f3n');
                }
            });
        }
        else if(tipodest=='R')
        {
            ck = 'Recuperando acciones posteriores a la revisi\u00f3n';
            _mask(ck);
            Ext.Ajax.request(
            {
                url      : _GLOBAL_URL_CARGAR_ACCIONES_ENTIDAD
                ,params  :
                {
                    'params.cdtipflu'   : cdtipflu
                    ,'params.cdflujomc' : cdflujomc
                    ,'params.tipoent'   : tipodest
                    ,'params.cdentidad' : clavedest
                    ,'params.webid'     : webiddest
                }
                ,success : function(response)
                {
                    _unmask();
                    var ck = 'Decodificando respuesta al recuperar acciones posteriores a la revisi\u00f3n';
                    try
                    {
                        var json = Ext.decode(response.responseText);
                        debug('### acciones:',json);
                        var numSalidas = 0
                            ,accion1   = ''
                            ,accion2   = ''
                            ,accExito  = ''
                            ,accError  = '';
                        if(json.success==true)
                        {
                            /*
                            CDACCION: "138"
                            CDCOMPMC: null
                            CDESTADOMC: null
                            CDFLUJOMC: "12"
                            CDICONO: null
                            CDPANTMC: null
                            CDPROCMC: null
                            CDREVISI: null
                            CDTIPFLU: "1"
                            CDVALIDA: "20"
                            CDVALOR: null
                            CLAVEDEST: "20"
                            DSACCION: "next"
                            IDDESTIN: "1450139785755_1072"
                            IDORIGEN: "1450121226109_9387"
                            TIPODEST: "V"
                            WEBIDCOMP: null
                            WEBIDDEST: "1450139785755_1072"
                            WEBIDESTADO: null
                            WEBIDPANT: null
                            WEBIDPROC: null
                            WEBIDREVISI: null
                            WEBIDVALIDA: "1450139785755_1072"
                            */
                            if(json.list.length==0)
                            {
                                if (aux !== 'INICIAL') { // Solo avienta exception si no es INICIAL
                                    throw 'No hay acciones relacionadas a la revisi\u00f3n';
                                } 
                            }
                            else if(json.list.length==1)
                            {
                                numSalidas = 1;
                                accion1    = json.list[0];
                            }
                            else if(json.list.length==2)
                            {
                                numSalidas = 2;
                                accion1    = json.list[0];
                                accion2    = json.list[1];
                                
                                if(_NVL(accion1.CDVALOR,'')=='EXITO')
                                {
                                    accExito = accion1;
                                }
                                else if(_NVL(accion2.CDVALOR,'')=='EXITO')
                                {
                                    accExito = accion2;
                                }
                                if(_NVL(accion1.CDVALOR,'')=='ERROR')
                                {
                                    accError = accion1;
                                }
                                else if(_NVL(accion2.CDVALOR,'')=='ERROR')
                                {
                                    accError = accion2;
                                }
                                
                                if(Ext.isEmpty(accExito)||Ext.isEmpty(accError))
                                {
                                    throw 'Las acciones relacionadas a la revisi\u00f3n no tienen el valor EXITO/ERROR adecuado';
                                }
                            }
                            else
                            {
                                if (aux !== 'INICIAL') { // Solo avienta exception si no es INICIAL
                                    throw 'La revisi\u00f3n tiene demasiadas acciones relacionadas';
                                }
                            }
                            
                            ck = 'Ejecutando revisi\u00f3n';
                            _mask(ck);
                            Ext.Ajax.request(
                            {
                                url      : _GLOBAL_URL_REVISION
                                ,params  :
                                {
                                    'flujo.cdtipflu'   : cdtipflu
                                    ,'flujo.cdflujomc' : cdflujomc
                                    ,'flujo.tipoent'   : tipodest
                                    ,'flujo.claveent'  : clavedest
                                    ,'flujo.webid'     : webiddest
                                    ,'flujo.ntramite'  : ntramite
                                    ,'flujo.status'    : status
                                    ,'flujo.cdunieco'  : cdunieco
                                    ,'flujo.cdramo'    : cdramo
                                    ,'flujo.estado'    : estado
                                    ,'flujo.nmpoliza'  : nmpoliza
                                    ,'flujo.nmsituac'  : nmsituac
                                    ,'flujo.nmsuplem'  : nmsuplem
                                }
                                ,success : function(response)
                                {
                                    _unmask();
                                    var ck = 'Decodificando respuesta al ejecutar revisi\u00f3n';
                                    try
                                    {
                                        var json = Ext.decode(response.responseText);
                                        debug('### revision:',json);
                                        if(json.success==true)
                                        {
                                            var faltanDocs = false;
                                            
                                            for (var i = 0; i < json.list.length; i++) {
                                                if (json.list[i].SWOBLIGA === 'S' && json.list[i].SUBIDO !== 'S') {
                                                    faltanDocs = true;
                                                    break;
                                                }
                                            }
                                            
                                            if (aux === 'INICIAL') {
                                                faltanDocs = true;
                                            }
                                            
                                            if (!faltanDocs) {
	                                            if(numSalidas==1)
	                                            {
                                                    _procesaAccion
                                                    (
                                                        cdtipflu
                                                        ,cdflujomc
                                                        ,accion1.TIPODEST
                                                        ,accion1.CLAVEDEST
                                                        ,accion1.WEBIDDEST
                                                        ,accion1.AUX
                                                        ,ntramite
                                                        ,status
                                                        ,cdunieco
                                                        ,cdramo
                                                        ,estado
                                                        ,nmpoliza
                                                        ,nmsituac
                                                        ,nmsuplem
                                                        ,cdusuari
                                                        ,cdsisrol
                                                        ,callback
                                                    );
	                                            }
	                                            else if(numSalidas==2)
	                                            {
                                                    _procesaAccion
                                                    (
                                                        cdtipflu
                                                        ,cdflujomc
                                                        ,accExito.TIPODEST
                                                        ,accExito.CLAVEDEST
                                                        ,accExito.WEBIDDEST
                                                        ,accExito.AUX
                                                        ,ntramite
                                                        ,status
                                                        ,cdunieco
                                                        ,cdramo
                                                        ,estado
                                                        ,nmpoliza
                                                        ,nmsituac
                                                        ,nmsuplem
                                                        ,cdusuari
                                                        ,cdsisrol
                                                        ,callback
                                                    );
	                                            }
	                                        }
	                                        else
	                                        {
	                                            centrarVentanaInterna(Ext.create('Ext.window.Window', {
	                                                title    : 'REVISI\u00D3N DE DOCUMENTOS REQUERIDOS',
	                                                itemId   : 'WINDOW_REVISION_DOCUMENTOS',
	                                                flujo    : {
	                                                    cdtipflu  : cdtipflu,
	                                                    cdflujomc : cdflujomc,
	                                                    tipodest  : tipodest,
	                                                    clavedest : clavedest,
	                                                    webiddest : webiddest,
	                                                    aux       : aux,
	                                                    ntramite  : ntramite,
	                                                    status    : status,
	                                                    cdunieco  : cdunieco,
	                                                    cdramo    : cdramo,
	                                                    estado    : estado,
	                                                    nmpoliza  : nmpoliza,
	                                                    nmsituac  : nmsituac,
	                                                    nmsuplem  : nmsuplem,
	                                                    cdusuari  : cdusuari,
	                                                    cdsisrol  : cdsisrol,
	                                                    callback  : callback
	                                                },
	                                                modal    : true,
	                                                closable : false,
	                                                border   : 0,
	                                                defaults : {
	                                                    style : 'margin : 5px;'
	                                                },
	                                                items    : [
	                                                    {
	                                                        xtype : 'displayfield',
	                                                        value : 'Favor de revisar los documentos requeridos:'
	                                                    }, {
	                                                        xtype      : 'grid',
	                                                        width      : 500,
	                                                        height     : 300,
	                                                        autoScroll : true,
	                                                        border     : 0,
	                                                        columns    : [
	                                                            {
	                                                                text      : 'DOCUMENTO',
	                                                                dataIndex : 'DSDOCUME',
	                                                                flex      : 1
	                                                            }, {
	                                                                text      : 'OBLIGATORIO',
	                                                                dataIndex : 'SWOBLIGA',
	                                                                width     : 100,
	                                                                renderer  : function (v)
	                                                                {
	                                                                    var r = '';
	                                                                    if (v === 'S') {
	                                                                        r = '<img src="'+_GLOBAL_DIRECTORIO_ICONOS+'lock.png" />';
	                                                                    }
	                                                                    return r;
	                                                                }
                                                                }, {
                                                                    text      : 'CARGADO',
                                                                    dataIndex : 'SUBIDO',
                                                                    width     : 100,
                                                                    renderer  : function (v, md, rec)
                                                                    {
                                                                        var r = '';
                                                                        if (v === 'S') {
                                                                            r = '<img src="'+_GLOBAL_DIRECTORIO_ICONOS+'accept.png" />';
                                                                        }
                                                                        else if (rec.get('SWOBLIGA')  === 'S') {
                                                                            r = '<img src="'+_GLOBAL_DIRECTORIO_ICONOS+'cancel.png" />';
                                                                        }
                                                                        return r;
                                                                    }
                                                                }, {
                                                                    width     : 30,
                                                                    dataIndex : 'SUBIDO',
                                                                    renderer  : function (v, md, rec, row)
                                                                    {
                                                                        var r = '';
                                                                        if (v !== 'S') {
                                                                            r = '<a href="#" onclick="subirArchivoDesdeRevision(' + row + '); return false;">' +
                                                                                    '<img src="' + _GLOBAL_DIRECTORIO_ICONOS + 'page_add.png" ' +
                                                                                    'data-qtip="Subir archivo" /></a>';
                                                                        }
                                                                        return r;
                                                                    }
                                                                }
	                                                        ], store : Ext.create('Ext.data.Store', {
	                                                            fields : [
	                                                                'CDDOCUME', 'DSDOCUME', 'SWOBLIGA', 'SUBIDO'
	                                                            ],
	                                                            data   : json.list
	                                                        })
	                                                    }
	                                                ],
	                                                buttonAlign : 'center',
	                                                buttons     : [
	                                                    {
	                                                        text    : 'Aceptar',
	                                                        handler : function (me) {
	                                                           me.up('window').destroy();
	                                                           
	                                                           if (numSalidas === 2 && aux != 'INICIAL') { // Cuando sean 2 salidas y no sea inicial ejecuta error
	                                                                _procesaAccion(
                                                                        cdtipflu
                                                                        ,cdflujomc
                                                                        ,accError.TIPODEST
                                                                        ,accError.CLAVEDEST
                                                                        ,accError.WEBIDDEST
                                                                        ,accError.AUX
                                                                        ,ntramite
                                                                        ,status
                                                                        ,cdunieco
                                                                        ,cdramo
                                                                        ,estado
                                                                        ,nmpoliza
                                                                        ,nmsituac
                                                                        ,nmsuplem
                                                                        ,cdusuari
                                                                        ,cdsisrol
                                                                        ,callback
                                                                    );
                                                                }
	                                                        }
	                                                    }
	                                                ],
	                                                recargar : function () {
	                                                    debug('>WINDOW_REVISION_DOCUMENTOS recargar');
	                                                    var me = this;
	                                                    
	                                                    _procesaAccion(
                                                            me.flujo.cdtipflu
                                                            ,me.flujo.cdflujomc
                                                            ,me.flujo.tipodest
                                                            ,me.flujo.clavedest
                                                            ,me.flujo.webiddest
                                                            ,me.flujo.aux
                                                            ,me.flujo.ntramite
                                                            ,me.flujo.status
                                                            ,me.flujo.cdunieco
                                                            ,me.flujo.cdramo
                                                            ,me.flujo.estado
                                                            ,me.flujo.nmpoliza
                                                            ,me.flujo.nmsituac
                                                            ,me.flujo.nmsuplem
                                                            ,me.flujo.cdusuari
                                                            ,me.flujo.cdsisrol
                                                            ,me.flujo.callback
                                                        );
	                                                    
	                                                    me.destroy();
	                                                }
	                                            }).show());
	                                        }
                                        }
                                        else
                                        {
                                            if(numSalidas<2)
                                            {
                                                mensajeError(json.message);
                                            }
                                            else
                                            {
                                                mensajeError
                                                (
                                                    json.message
                                                    ,function()
                                                    {
                                                        _procesaAccion
                                                        (
                                                            cdtipflu
                                                            ,cdflujomc
                                                            ,accError.TIPODEST
                                                            ,accError.CLAVEDEST
                                                            ,accError.WEBIDDEST
                                                            ,accError.AUX
                                                            ,ntramite
                                                            ,status
                                                            ,cdunieco
                                                            ,cdramo
                                                            ,estado
                                                            ,nmpoliza
                                                            ,nmsituac
                                                            ,nmsuplem
                                                            ,cdusuari
                                                            ,cdsisrol
                                                            ,callback
                                                        );
                                                    }
                                                );
                                            }
                                        }
                                    }
                                    catch(e)
                                    {
                                        if(numSalidas<2)
                                        {
                                            manejaException(e,ck);
                                        }
                                        else
                                        {
                                            debugError(e);
                                            mensajeError
                                            (
                                                'Error al manejar respuesta de proceso'
                                                ,function()
                                                {
                                                    _procesaAccion
                                                    (
                                                        cdtipflu
                                                        ,cdflujomc
                                                        ,accError.TIPODEST
                                                        ,accError.CLAVEDEST
                                                        ,accError.WEBIDDEST
                                                        ,accError.AUX
                                                        ,ntramite
                                                        ,status
                                                        ,cdunieco
                                                        ,cdramo
                                                        ,estado
                                                        ,nmpoliza
                                                        ,nmsituac
                                                        ,nmsuplem
                                                        ,cdusuari
                                                        ,cdsisrol
                                                        ,callback
                                                    );
                                                }
                                            );
                                        }
                                    }
                                }
                                ,failure : function(response)
                                {
                                    _unmask();
                                    if(numSalidas<2)
                                    {
                                        errorComunicacion(null,'Error al ejecutar revisi\u00f3n');
                                    }
                                    else
                                    {
                                        mensajeError(
                                            'Error de comunicaci\u00f3n al ejecutar revisi\u00f3n'
                                            ,function()
                                            {
                                                _procesaAccion
                                                (
                                                    cdtipflu
                                                    ,cdflujomc
                                                    ,accError.TIPODEST
                                                    ,accError.CLAVEDEST
                                                    ,accError.WEBIDDEST
                                                    ,accError.AUX
                                                    ,ntramite
                                                    ,status
                                                    ,cdunieco
                                                    ,cdramo
                                                    ,estado
                                                    ,nmpoliza
                                                    ,nmsituac
                                                    ,nmsuplem
                                                    ,cdusuari
                                                    ,cdsisrol
                                                    ,callback
                                                );
                                            }
                                        );
                                    }
                                }
                            });
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
                    errorComunicacion(null,'Error al recuperar acciones posteriores a la revisi\u00f3n');
                }
            });
        }
        else if(tipodest=='M')
        {
            ck = 'Recuperando valores de correo';
            _mask(ck);
            Ext.Ajax.request(
            {
                url      : _GLOBAL_URL_RECUPERACION
                ,params  :
                {
                    'params.consulta'   : 'RECUPERAR_TFLUMAIL'
                    ,'params.cdtipflu'  : cdtipflu
                    ,'params.cdflujomc' : cdflujomc
                    ,'params.cdmail'    : clavedest
                }
                ,success : function(response)
                {
                    _unmask();
                    var ck = 'Decodificando respuesta al recuperar valores de correo';
                    try
                    {
                        var json = Ext.decode(response.responseText);
                        debug('### tflumail:',json);
                        if(json.success==true)
                        {
                            if(json.list.length==0)
                            {
                                throw 'El correo no existe';
                            }
                            else if(json.list.length>1)
                            {
                                throw 'Correo duplicado';
                            }
                            var data = json.list[0];
                            debug('data:',data);
                            
                            ck = 'Recuperando acciones posteriores a la validaci\u00f3n';
                            _mask(ck);
                            Ext.Ajax.request(
                            {
                                url      : _GLOBAL_URL_CARGAR_ACCIONES_ENTIDAD
                                ,params  :
                                {
                                    'params.cdtipflu'   : cdtipflu
                                    ,'params.cdflujomc' : cdflujomc
                                    ,'params.tipoent'   : tipodest
                                    ,'params.cdentidad' : clavedest
                                    ,'params.webid'     : webiddest
                                }
                                ,success : function(response)
                                {
                                    _unmask();
                                    var ck = 'Decodificando respuesta al recuperar acciones posteriores a la validaci\u00f3n';
                                    try
                                    {
                                        var jsonAcc = Ext.decode(response.responseText);
                                        debug('### acciones:',jsonAcc);
                                        var numSalidas = jsonAcc.list.length
                                            ,acciones  = jsonAcc.list;
                                        if(jsonAcc.success==true)
                                        {
                                            if(jsonAcc.list.length < 2){
												ck = 'Enviando correo';
                                                _mask(ck);
                                                Ext.Ajax.request(
                                                {
                                                    url      : _GLOBAL_URL_ENVIAR_CORREO_FLUJO
                                                    ,params  :
                                                    {
                                                        'flujo.cdtipflu'     : cdtipflu
                                                        ,'flujo.cdflujomc'   : cdflujomc
                                                        ,'flujo.tipoent'     : tipodest
                                                        ,'flujo.claveent'    : clavedest
                                                        ,'flujo.webid'       : webiddest
                                                        ,'flujo.ntramite'    : ntramite
                                                        ,'flujo.status'      : status
                                                        ,'flujo.cdunieco'    : cdunieco
                                                        ,'flujo.cdramo'      : cdramo
                                                        ,'flujo.estado'      : estado
                                                        ,'flujo.nmpoliza'    : nmpoliza
                                                        ,'flujo.nmsituac'    : nmsituac
                                                        ,'flujo.nmsuplem'    : nmsuplem
                                                        ,'flujo.aux'         : aux
														,'params.dsdestino'  : data.DSDESTINO
														,'params.dsasunto'   : data.DSASUNTO
														,'params.dsmensaje'  : data.DSMENSAJE
														,'params.vardestino' : data.VARDESTINO
														,'params.varmensaje' : data.VARMENSAJE
														,'params.varasunto'  : data.VARASUNTO
                                                    }
                                                    ,success : function(response)
                                                    {
                                                        _unmask();
                                                        var ck = 'decod Enviando correo';
                                                         try
                                                         {
                                                             var json = Ext.decode(response.responseText);
                                                             debug('### validacion:',json);
                                                             if(json.success==true)
                                                             {
                                                            	if(numSalidas == 0){
                                                            		mensajeCorrecto('Correo enviado','Correo enviado');
                                                            	}else{
                                                            		_procesaAccion
                                                            		(
                                                            				cdtipflu
		                                                                    ,cdflujomc
		                                                                    ,acciones[0].TIPODEST
		                                                                    ,acciones[0].CLAVEDEST
		                                                                    ,acciones[0].WEBIDDEST
		                                                                    ,acciones[0].AUX
		                                                                    ,ntramite
		                                                                    ,status
		                                                                    ,cdunieco
		                                                                    ,cdramo
		                                                                    ,estado
		                                                                    ,nmpoliza
		                                                                    ,nmsituac
		                                                                    ,nmsuplem
		                                                                    ,cdusuari
		                                                                    ,cdsisrol
		                                                                    ,callback
                                                            		);
                                                            	}                                                               
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
                                                    ,failure : function(response)
                                                    {
                                                        _unmask();
                                                        errorComunicacion(null,'Error al enviar correo');
                                                    }
                                                });
											}else{
												throw 'Hay demasiadas acciones relacionadas al correo';
											}
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
                                    errorComunicacion(null,'Error al recuperar acciones posteriores al correo');
                                }
                            });
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
                    errorComunicacion(null,'Error al recuperar valores de correo');
                }
            });
        }
        else
        {
            throw 'Entidad inv\u00e1lida';
        }
    }
    catch(e)
    {
        manejaException(e,ck);
    }
}

function _mask(text)
{
    _unmask();
    centrarVentanaInterna(Ext.create('Ext.window.Window',
    {
        title        : 'Cargando...'
        ,itemId      : '_global_loadingWindow'
        ,modal       : true
        ,border      : 0
        ,style       : 'padding:5px;'
        ,closeAction : 'destroy'
        ,closable    : false
        ,items       :
        [{
            xtype  : 'displayfield'
            ,value : _NVL(text,'Cargando...')
        }]
    }).show());
}

/*
 * Esta funcion hace un mask y te regresa la variable (Ext.window)
 * tu tienes que hacer el variable.close() porque
 * el _unmask() no la destruye
 */
function _maskLocal(text)
{
    return centrarVentanaInterna(Ext.create('Ext.window.Window',
    {
        title        : 'Cargando...'
        ,modal       : true
        ,border      : 0
        ,style       : 'padding:5px;'
        ,closeAction : 'destroy'
        ,closable    : false
        ,maskLocal   : true
        ,items       :
        [{
            xtype  : 'displayfield'
            ,value : _NVL(text,'Cargando...')
        }]
    }).show());
}

function _maskSession(text)
{
    _unmask();
    centrarVentanaInterna(Ext.create('Ext.window.Window',
    {
        title        : 'En espera...'
        ,itemId      : '_global_loadingWindow'
        ,modal       : true
        ,border      : 0
        ,style       : 'padding:5px;'
        ,closeAction : 'destroy'
        ,closable    : false
        ,items       :
        [{
            xtype  : 'displayfield'
            ,value : _NVL(text,'Cargando...')
        }]
    }).show());
}

function _unmask()
{
    var masks = Ext.ComponentQuery.query('[itemId=_global_loadingWindow]');
    for(var i=0;i<masks.length;i++)
    {
        masks[i].destroy();
    }
}

/**
 * Regresa el numero que se repite un substring en un string 
 * Ej. Str(strasdstr) Substr(str) resul = 2
 * @param subStr substring
 * @param str	 string	
 * @returns {Number} numero que se repite en la cadena
 */
function _numberContainsSubstrInStr(subStr, str){
	var lastIndex = 0,
 		count     = 0;
 	while(lastIndex != -1){
 		lastIndex = str.indexOf(subStr,lastIndex);
 		if(lastIndex != -1){
        	count ++;
        	lastIndex += subStr.length;
    	}
 	}
 	return count;
}

/**
 * Prueba de timeout
 * @param {int} min Duracion del timeout en minutos
 */
function testTimeout(min) {
	
	console.log('Inicio de timeout de ' + min + ' min(s) ...');
    
    Ext.Ajax.request({
    	url    : _GLOBAL_URL_TEST_SLEEP,
    	params : {
            'params.m'  : min
        },
        success : function(response) {
        	console.log('Fin de timeout de ' + min + ' min(s).', response);
        },
        failure: function(response) {
        	console.error('Error en Timeout de ' + min + ' min(s)', response);
        }
    });
}

/**
 *
 * ESTA FUNCIONA CONVIERTE UN OBJETO FLUJO EN UN OBJETO CUYAS PROPIEDADES SON FLUJO.X, FLUJO.Y, FLUJO.Z ...
 * SIRVE PARA ENVIAR UN FLUJO JAVASCRIPT A LA VARIABLE FLUJO DE LOS ACTION
 * Sirve para mandarlo dentro de un form.submit como params
 * o dentro de un Ext.Ajax.request como params
 * Para enviarlo como jsonData no hace falta usar esta funcion
 * El segundo parametro es un mapa de parametros (objeto) ya existente, es opcional
 * si viene el segundo parametro entonces solo se le agregan a ese los
 * datos del flujo
 *
 * Ejemplo: flujo  = { cdtipflu : 1 , cdflujomc : 2 , status : 10 ... }
 *          salida = { 'flujo.cdtipflu' : 1 , 'flujo.cdflujomc' : 2 , 'flujo.status' : 10 ... }
 */
function _flujoToParams(flujo,paramsEntrada)
{
    debug('>_flujoToParams args:',arguments,'.');
    
    var params = {};
    
    if(!Ext.isEmpty(paramsEntrada)) //cuando vienen parametros de entrada se usan esos para agregar los nuevos
    {
        params = paramsEntrada;
    }
    
    params['flujo.ntramite']  = flujo.ntramite;
    params['flujo.status']    = flujo.status;
    params['flujo.cdtipflu']  = flujo.cdtipflu;
    params['flujo.cdflujomc'] = flujo.cdflujomc;
    params['flujo.webid']     = flujo.webid;
    params['flujo.tipoent']   = flujo.tipoent;
    params['flujo.claveent']  = flujo.claveent;
    params['flujo.cdunieco']  = flujo.cdunieco;
    params['flujo.cdramo']    = flujo.cdramo;
    params['flujo.estado']    = flujo.estado;
    params['flujo.nmpoliza']  = flujo.nmpoliza;
    params['flujo.nmsituac']  = flujo.nmsituac;
    params['flujo.nmsuplem']  = flujo.nmsuplem;
    params['flujo.aux']       = flujo.aux;
    
    debug('<_flujoToParams salida:',params,'.');
    return params;
}

/**
 *
 * ESTA FUNCION CARGA LAS ACCIONES RELACIONADAS A UNA ENTIDAD
 * Y EJECUTA EL CALLBACK ENVIANDO LA LISTA DE ACCIONES
 *
 */
function _cargarAccionesEntidad(cdtipflu,cdflujomc,tipoent,cdentidad,webid,callback)
{
    debug('_cargarAccionesEntidad args:',arguments,'.');
    var mask, ck = 'Recuperando acciones de entidad';
    try
    {
        _validate(
            cdtipflu   , 'Falta flujo para recuperar acciones de entidad'
            ,cdflujomc , 'Falta proceso para recuperar acciones de entidad'
            ,tipoent   , 'Falta tipo de entidad para recuperar acciones de entidad'
            ,cdentidad , 'Falta clave de entidad para recuperar acciones de entidad'
            ,webid     , 'Falta id web de entidad para recuperar acciones de entidad'
            ,callback  , 'Falta callback para recuperar acciones de entidad'
        );
        
        mask = _maskLocal(ck);
        Ext.Ajax.request(
        {
            url      : _GLOBAL_URL_CARGAR_ACCIONES_ENTIDAD
            ,params  :
            {
                'params.cdtipflu'   : cdtipflu
                ,'params.cdflujomc' : cdflujomc
                ,'params.tipoent'   : tipoent
                ,'params.cdentidad' : cdentidad
                ,'params.webid'     : webid
            }
            ,success : function(response)
            {
                mask.close();
                var ck = 'Decodificando respuesta al recuperar acciones de entidad';
                try
                {
                    var json = Ext.decode(response.responseText);
                    debug('### acciones entidad:',json,'.');
                    if(json.success === true)
                    {
                        ck = 'Ejecutando callback al recuperar acciones de entidad';
                        callback(json.list);
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
                mask.close();
                errorComunicacion(null,'Error al recuperar acciones de entidad');
            }
        });
    }
    catch(e)
    {
        manejaException(e,ck,mask);
    }
}

/**
 *
 * ESTA FUNCION REVISA ARGUMENTOS EN PARES, SI EL PRIMERO ES EMPTY
 * LANZA EL SEGUNDO COMO EXCEPTION,
 * ES SIMILAR AL UTILS.VALIDATE DE JAVA
 *
 */
function _validate()
{
    debug('_validate args:',arguments,'.');
    
    if(arguments.length % 2 === 1) // si el numero de argumentos no es par
    {
        throw 'Argumentos inv\u00e1lidos para funci\u00f3n _validate';
    }
    
    for(var i = 0 ; i < arguments.length ; i=i+2)
    {
        if(Ext.isEmpty(arguments[i]))
        {
            throw arguments[i+1];
        }
    }
}

/**
 *
 * ESTA FUNCION SETEA UN CAMPO DE CDAGENTE
 * SI ES TEXTIELD HACE UN SETVALUE NORMAL
 * SI ES COMBO HACE UN SELECT
 * SI ES AUTOCOMPLETER LO CARGA Y LUEGO LE HACE EL SELECT
 *
 */
function _setValueCampoAgente(campo,cdagente)
{
    debug('>_setValueCampoAgente:',arguments,'.');
    
    var ck;
    
    try
    {
        _validate(
            campo     , 'No se recibi\u00f3 el campo agente para setear'
            ,cdagente , 'No se recibi\u00f3 el valor de agente para setear'
        );
        
        ck = 'Identificando tipo de campo de agente';
        debug(ck);
        
        if(campo.xtype === 'textfield')
        {
            ck = 'Seteando valor normal de agente';
            
            debug(ck);
            
            campo.setValue(cdagente);
        }
        else if(campo.xtype === 'combobox')
        {
            if(campo.hideTrigger === true)
            {
                ck = 'Seteando autocompleter de agente';
                
                debug(ck);
                
                campo.getStore().load(
                {
                    params :
                    {
                        'params.agente' : cdagente
                    }
                    ,callback : function()
                    {
                        campo.setValue(campo.findRecord('key',cdagente));
                    }
                });
                
            }
            else
            {
                ck = 'Seteando combo de agente'
                
                var rec = campo.findRecord('key',cdagente);
                
                debug(ck,'rec:',rec,'.');
                
                if(rec)
                {
                    campo.select(rec);
                }
            }
        }
        else
        {
            throw 'El campo de agente es tipo desconocido';
        }
        
    }
    catch(e)
    {
        manejaException(e,ck);
    }
}

function subirArchivoDesdeRevision (row) {
    debug('>subirArchivoDesdeRevision args:', arguments);
    
    var ck = 'Invocanco ventana de documentos';
    try {
        var win    = _fieldById('WINDOW_REVISION_DOCUMENTOS'),
            flujo  = win.flujo,
            record = win.down('grid').getStore().getAt(row);
        
        debug('win:'    , win    , '.');
        debug('flujo:'  , flujo  , '.');
        debug('record:' , record , '.');
        
        Ext.syncRequire(_GLOBAL_DIRECTORIO_DEFINES+'VentanaDocumentos');
        new window['VentanaDocumentos']({
            cdtipflu           : flujo.cdtipflu
            ,cdflujomc         : flujo.cdflujomc
            ,tipoent           : flujo.tipodest
            ,claveent          : flujo.clavedest
            ,webid             : flujo.webiddest
            ,aux               : 'INICIAL' === flujo.aux
                                     ? ''
                                     : flujo.aux
            ,ntramite          : flujo.ntramite
            ,status            : flujo.status
            ,cdunieco          : flujo.cdunieco
            ,cdramo            : flujo.cdramo
            ,estado            : flujo.estado
            ,nmpoliza          : flujo.nmpoliza
            ,nmsituac          : flujo.nmsituac
            ,nmsuplem          : flujo.nmsuplem
            ,cdusuari          : flujo.cdusuari
            ,cdsisrol          : flujo.cdsisrol
            ,cddocumeParasubir : record.get('CDDOCUME')
            ,dsdocumeParasubir : record.get('DSDOCUME')
            ,windowRevisi      : win
       }).mostrar();
    } catch(e) {
        manejaException(e, ck);
    }
}

////////////////////////////
////// INICIO MODELOS //////
////////////////////////////
Ext.define('Generic',
{
    extend:'Ext.data.Model',
    fields:
    [
        { name:'key'   , type: 'string'},
        { name:'value' , type: 'string'},
        { name:'aux'   , type: 'string'},
        { name:'aux2'  , type: 'string'},
        { name:'aux3'  , type: 'string'}
    ]
});

//catalogos tatrisit
Ext.define('GeSexo',                                    {extend:'Generic'});//1
//fecha nacimiento                                                            2
Ext.define('GeEstado',                                  {extend:'Generic'});//3
Ext.define('GeCiudad',                                  {extend:'Generic'});//4
//deducible                                                                   5
Ext.define('GeCopago',                                  {extend:'Generic'});//6
Ext.define('GeSumaAsegurada',                           {extend:'Generic'});//7
Ext.define('GeCirculoHospitalario',                     {extend:'Generic'});//8
Ext.define('GeCoberturaVacunas',                        {extend:'Generic'});//9
Ext.define('GeCoberturaPrevencionEnfermedadesAdultos',  {extend:'Generic'});//10
Ext.define('GeMaternidad',                              {extend:'Generic'});//11
Ext.define('GeSumaAseguradaMaternidad',                 {extend:'Generic'});//12
Ext.define('GeBaseTabuladorReembolso',                  {extend:'Generic'});//13
Ext.define('GeCostoEmergenciaExtranjero',               {extend:'Generic'});//14
Ext.define('GeCobElimPenCambioZona',                    {extend:'Generic'});//15
Ext.define('GeRol',                                     {extend:'Generic'});//16
Ext.define('GeMunicipio',                               {extend:'Generic'});//17

Ext.define('IncisoSalud',
{
    extend:'Ext.data.Model',
    fields:
    [
        {name:'id',                 type:'numeric'},
        {name:'rol',                type:'GeRol'},
        {name:'fechaNacimiento',    type:'date'},
        {name:'sexo',               type:'GeSexo'},
        {name:'nombre',             type:'string'},
        {name:'segundoNombre',      type:'string'},
        {name:'apellidoPaterno',    type:'string'},
        {name:'apellidoMaterno',    type:'string'}
    ]
});

Ext.define('CotizacionSalud',
{
    extend:'Ext.data.Model',
    fields:
    [
        {name:'id',                                         type:'numeric'},                                    //0
        //sexo (inciso)                                                                                           1
        //fecha nacimiento (inciso)                                                                               2
        {name:'estado',                                     type:'GeEstado'},                                   //3
        {name:'ciudad',                                     type:'GeCiudad'},                                   //4
        {name:'deducible',                                  type:'numeric'},                                    //5
        {name:'copago',                                     type:'GeCopago'},                                   //6
        {name:'sumaSegurada',                               type:'GeSumaAsegurada'},                            //7
        {name:'circuloHospitalario',                        type:'GeCirculoHospitalario'},                      //8
        {name:'coberturaVacunas',                           type:'GeCoberturaVacunas'},                         //9
        {name:'coberturaPrevencionEnfermedadesAdultos',     type:'GeCoberturaPrevencionEnfermedadesAdultos'},   //10
        {name:'maternidad',                                 type:'GeMaternidad'},                               //11
        {name:'sumaAseguradaMaternidad',                    type:'GeSumaAseguradaMaternidad'},                  //12
        {name:'baseTabuladorReembolso',                     type:'GeBaseTabuladorReembolso'},                   //13
        {name:'costoEmergenciaExtranjero',                  type:'GeCostoEmergenciaExtranjero'},                //14
        {name:'coberturaEliminacionPenalizacionCambioZona', type:'GeCobElimPenCambioZona'}                      //15
        //rol (inciso)                                                                                            16
    ],
    hasMany:
    [{
        name:           'incisos',
        model:          'IncisoSalud',
        foreignKey:     'incisos',
        associationKey: 'incisos'
    }]
});

Ext.define('RowCotizacion', {
    extend: 'Ext.data.Model',
    fields: [
        {type:'string',name:'cdIdentifica'      },
        {type:'string',name:'cdUnieco'          },
        {type:'string',name:'cdRamo'            },
        {type:'string',name:'estado'            },
        {type:'string',name:'nmPoliza'          },
        {type:'string',name:'nmSuplem'          },
        {type:'string',name:'status'            },
        {type:'string',name:'cdPlan'            },
        {type:'string',name:'dsPlan'            },
        {type:'string',name:'mnPrima'           },
        {type:'string',name:'cdCiaaseg'         },
        {type:'string',name:'dsUnieco'          },
        {type:'string',name:'cdPerpag'          },
        {type:'string',name:'dsPerpag'          },
        {type:'string',name:'cdTipsit'          },
        {type:'string',name:'dsTipsit'          },
        {type:'string',name:'numeroSituacion'   },
        {type:'string',name:'cdGarant'          },
        {type:'string',name:'dsGarant'          },
        {type:'string',name:'swOblig'           },
        {type:'string',name:'sumaAseg'          },
        {type:'string',name:'nMimpfpg'          },
        {type:'string',name:'primaFormap'       },
        {type:'string',name:'feEmisio'          },
        {type:'string',name:'feVencim'          },
        {type:'string',name:'Plus1000'          },
        {type:'string',name:'CDPlus1000'        },
        {type:'string',name:'DSPlus1000'        },
        {type:'string',name:'NMPlus1000'        },
        {type:'string',name:'Plus100'           },
        {type:'string',name:'CDPlus100'         },
        {type:'string',name:'DSPlus100'         },
        {type:'string',name:'NMPlus100'         },
        {type:'string',name:'Plus500'           },
        {type:'string',name:'CDPlus500'         },
        {type:'string',name:'DSPlus500'         },
        {type:'string',name:'NMPlus500'         }
    ]
});

Ext.define('RowCobertura',{
    extend:'Ext.data.Model',
    fields:
    [
        "orden",
        {type:'string',name:'cdCiaaseg'},
        {type:'string',name:'cdGarant'},
        {type:'string',name:'cdRamo'},
        {type:'string',name:'deducible'},
        {type:'string',name:'dsGarant'},
        {type:'string',name:'sumaAsegurada'}
    ]
});

var _g_storeSino = Ext.create('Ext.data.Store',
{
    model : 'Generic'
    ,data :
    [
        {
            key    : 'S'
            ,value : 'Si'
        }
        ,{
            key    : 'N'
            ,value : 'No'
        }
    ]
});
/////////////////////////
////// FIN MODELOS //////
/////////////////////////

var extjs_custom_override_mayusculas=true;
var defaultComboListeners =
{
	load : function(store,records,success)
	{
		debug('success:',success);
		debug('store:',store,'records:',records);
	}
};
var viewConfigAutoSize =
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
};

Ext.onReady(function()
{
	Ext.tip.QuickTipManager.init();
    Ext.util.Format.thousandSeparator = ',';
    Ext.util.Format.decimalSeparator = '.';
    Ext.grid.RowEditor.prototype.saveBtnText =   "Actualizar";
    Ext.grid.RowEditor.prototype.cancelBtnText = "Cancelar";
});

