////////////////////////////////////////////////////////////////////
// ESTE DOCUMENTO ES LA BASE DE TODOS LOS JSP QUE TENGAN EXT JS 4 //
////////////////////////////////////////////////////////////////////

///////////////////////
////// FUNCIONES //////
/*///////////////////*/
function debug(a,b,c,d)
{
	if(false)
	{
	    if(d!=undefined)
	        console.log(a,b,c,d);
	    else if(c!=undefined)
	        console.log(a,b,c);
	    else if(b!=undefined)
	        console.log(a,b);
	    else
	        console.log(a);
	}
}

function validarRFC(rfc,tper)
{
	debug('validarRFC',rfc,tper);
	var valido=rfc&&rfc.length>0&&tper&&tper.length>0;
	debug('validacion inicial:',valido);
	if(valido)
	{
		var regexLetras=/^[a-zA-Z]*$/;
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
		Ext.create('Ext.window.Window',
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
			    	,text : 'El RFC "'+rfc+'" no es válido para persona '+(tper=='F'?'Física':(tper=='M'?'Moral':'tipo régimen simplificado'))
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

function errorComunicacion()
{
	var tmpMensajeEmergente=Ext.Msg.show({
        title    : 'Error'
        ,icon    : Ext.Msg.ERROR
        ,msg     : 'Error de comunicaci&oacute;n'
        ,buttons : Ext.Msg.OK
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

function mensajeError(mensaje)
{
	var tmpMensajeEmergente=Ext.Msg.show({
		title    : 'Error'
        ,icon    : Ext.Msg.ERROR
        ,msg     : mensaje
        ,buttons : Ext.Msg.OK
    });
	centrarVentanaInterna(tmpMensajeEmergente);
}

function mensajeCorrecto(titulo,mensaje,funcion)
{
	
	if(funcion)
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
function heredarPanel(formPanel)
{
	debug('heredarPanel');
	for(var i=0;i<formPanel.items.items.length;i++)
	{
		if(formPanel.items.items[i].heredar)
		{
			debug('heredarPanel>heredar');
			formPanel.items.items[i].heredar(true);
		}
	}
}

/**
 * Centra un componente tipoWindow o Mensaje de Texto  en la pantalla principal 
 * @param ventana
 */
function centrarVentana(ventana)
{
	ventana.setPosition(ventana.getPosition()[0], $(window.parent).scrollTop() + 100);
}

function centrarVentanaInterna(ventana)
{
	var y = $(window.parent).scrollTop() + 100;
	debug('y:',y);
	ventana.setPosition(ventana.getPosition()[0],y);
}


////////////////////////////
////// INICIO MODELOS //////
////////////////////////////
Ext.define('Generic',
{
    extend:'Ext.data.Model',
    fields:
    [
        {name:'key',    type: 'string'},
        {name:'value',  type: 'string'}
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
        {type:'string',name:'cdCiaaseg'},
        {type:'string',name:'cdGarant'},
        {type:'string',name:'cdRamo'},
        {type:'string',name:'deducible'},
        {type:'string',name:'dsGarant'},
        {type:'string',name:'sumaAsegurada'}
    ]
});
/////////////////////////
////// FIN MODELOS //////
/////////////////////////

var extjs_custom_override_mayusculas=true;

Ext.onReady(function()
{
	Ext.tip.QuickTipManager.init();
    Ext.util.Format.thousandSeparator = ',';
    Ext.util.Format.decimalSeparator = '.';
    Ext.grid.RowEditor.prototype.saveBtnText =   "Actualizar";
    Ext.grid.RowEditor.prototype.cancelBtnText = "Cancelar";
});