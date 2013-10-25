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
	    if(d)
	        console.log(a,b,c,d);
	    else if(c)
	        console.log(a,b,c);
	    else if(b)
	        console.log(a,b);
	    else
	        console.log(a);
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

Ext.onReady(function(){
	Ext.tip.QuickTipManager.init();
    Ext.util.Format.thousandSeparator = ',';
    Ext.util.Format.decimalSeparator = '.';
    Ext.grid.RowEditor.prototype.saveBtnText =   "Actualizar";
    Ext.grid.RowEditor.prototype.cancelBtnText = "Cancelar";
    //Se sobreescribe el componente TextField para que solo acepte may�sculas
    Ext.override(Ext.form.TextField, {
    	fieldStyle:'text-transform:uppercase'
    	,initComponent:function(){
    		try
    		{
    			if(this.column)
    			{
    				Ext.apply(this,
    				{
    					listeners:
    					{
    						'change':function()
    						{
    						    //debug(this);
    						    //debug(this.xtype);
    							if(this.getValue() && this.xtype=='textfield')
    							{
    								debug('mayus de '+this.getValue());
    								this.setValue(this.getValue().toUpperCase());
    							}
    						}
    					}
    				});
    			}
    		}
    		catch(e){}
    		return this.callParent();
    	}
	});
});