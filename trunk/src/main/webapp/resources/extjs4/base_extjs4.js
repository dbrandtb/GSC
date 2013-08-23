////////////////////////////////////////////////////////////////////
// ESTE DOCUMENTO ES LA BASE DE TODOS LOS JSP QUE TENGAN EXT JS 4 //
////////////////////////////////////////////////////////////////////

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

Ext.define('GeCiudad',                                  {extend:'Generic'});
Ext.define('GeSexo',                                    {extend:'Generic'});
Ext.define('GeRol',                                     {extend:'Generic'});
Ext.define('GeCopago',                                  {extend:'Generic'});
Ext.define('GeSumaAsegurada',                           {extend:'Generic'});
Ext.define('GeCirculoHospitalario',                     {extend:'Generic'});
Ext.define('GeCoberturaVacunas',                        {extend:'Generic'});
Ext.define('GeCoberturaPrevencionEnfermedadesAdultos',  {extend:'Generic'});
Ext.define('GeMaternidad',                              {extend:'Generic'});
Ext.define('GeSumaAseguradaMaternidad',                 {extend:'Generic'});
Ext.define('GeBaseTabuladorReembolso',                  {extend:'Generic'});
Ext.define('GeCostoEmergenciaExtranjero',               {extend:'Generic'});

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
        {name:'id',                                     type:'numeric'},
        {name:'ciudad',                                 type:'GeCiudad'},
        {name:'deducible',                              type:'numeric'},
        {name:'copago',                                 type:'GeCopago'},
        {name:'sumaSegurada',                           type:'GeSumaAsegurada'},
        {name:'circuloHospitalario',                    type:'GeCirculoHospitalario'},
        {name:'coberturaVacunas',                       type:'GeCoberturaVacunas'},
        {name:'coberturaPrevencionEnfermedadesAdultos', type:'GeCoberturaPrevencionEnfermedadesAdultos'},
        {name:'maternidad',                             type:'GeMaternidad'},
        {name:'sumaAseguradaMaternidad',                type:'GeSumaAseguradaMaternidad'},
        {name:'baseTabuladorReembolso',                 type:'GeBaseTabuladorReembolso'},
        {name:'costoEmergenciaExtranjero',              type:'GeCostoEmergenciaExtranjero'}
    ],
    hasMany:
    [{
        name:           'incisos',
        model:          'IncisoSalud',
        foreignKey:     'incisos',
        associationKey: 'incisos'
    }]
});
/////////////////////////
////// FIN MODELOS //////
/////////////////////////